package com.greenorange.gooutdoor.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsTYPE;
import com.greenorange.gooutdoor.framework.Dao.MapDao;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationCallBack;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationFilterConfig;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.greenorange.gooutdoor.framework.Utils.Util;

/**
 * Created by JasWorkSpace on 15/4/18.
 */
public class LocationService extends Service implements LocationCallBack{
    public final static int EVENTID_MSG_LOCATIONCHANGE = 1;
    private boolean LocationServiceEnable = false;
    private Object mObject  = new Object();
    private LocationHandler mLocationHandler = null;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("LocationService onCreate");
        initLocationHandler();//init handler.
        LocationServiceEnable = true;
        mLocationHandler.sendEmptyMessageDelayed(MSG_INIT_LOCATION, 100);//for init location.
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LocationService onDestroy");
        LocationServiceEnable = false;
        releaseLocationControl();//close GPS location.
    }
    ///////////////// for service binder ///////////////////
    private LocationBinder mLocationBinder = new LocationBinder();

    public class LocationBinder extends Binder{
        public LocationService getService(){
            return LocationService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("LocationService onBind");
        return mLocationBinder;
    }
    ////////////////////////////////////////////////////
    private SportsControl  mSportsControl;
    public  SportsControl  getSportsControl(){
        return mSportsControl;
    }
    public void initSportsControl(){
        synchronized (mObject) {
            MapDao mapDao = (MapDao) GOApplication.getDaoManager().getManager(Dao.MapDao);
            int mapType = mapDao.getCurrentMAPTYPE();
            if (mSportsControl == null || (mSportsControl.getLocationTYPE() != mapType)){
                mSportsControl = new SportsControl(LocationService.this, mapType);
            }
            mSportsControl.initSports();
        }
    }
    ////////////////////////////////////////////////////
    private LocationControl mLocationControl;
    public void initLocationControl(){
        releaseLocationControl();
        if(!LocationServiceEnable) return;
        synchronized (mObject) {
            MapDao mapDao = (MapDao) GOApplication.getDaoManager().getManager(Dao.MapDao);
            int mapType = mapDao.getCurrentMAPTYPE();
            if (MAPTYPE.MAPTYPE_GAODE == mapType) {
                mLocationControl = new LocationGaode(LocationService.this);
            } else if (MAPTYPE.MAPTYPE_BAIDU == mapType) {
                mLocationControl = new LocationBaidu(LocationService.this);
            } else {
                throw new ApplicationException(" initLocationControl fail !! unknow maptype " + mapType);
            }
            mLocationControl.setLocationCallBack(LocationService.this);
        }
    }
    public void releaseLocationControl(){
        synchronized (mObject) {
            if (mLocationControl != null) {
                mLocationControl.setLocationCallBack(null);
                mLocationControl.closeLocation();
            }
        }
        mLocationControl = null;
    }
    public LocationControl getLocationControl(){
        return mLocationControl;
    }
    @Override
    public boolean onLocationChange(LocationDBData locationData) {
        mLocationHandler.sendUnRepeatMeaasge(MSG_SYC_NEWLOCATION_RECEIVER, locationData);
        return true;
    }
    private int getCurrentSportsType(){
        if(mSportsControl != null){
            return mSportsControl.getSportsTYPE();
        }
        return SportsTYPE.SPORT_TYPE_UNKNOW;
    }
    private boolean updateUserSportsData(LocationDBData locationDBData) {
        if(mSportsControl != null && mLocationControl != null){
            LocationFilterConfig locationFilterConfig = mLocationControl.getLocationFilterConfig(mSportsControl.getSportsTYPE());
            if(locationFilterConfig == null){
                locationFilterConfig = mLocationControl.getdefaultFilterConfig();
            }
            return mSportsControl.updateCurrentLocationData(locationDBData, locationFilterConfig);
        }
        return false;
    }
    /////////////////////////////////////////////////////////////////////////
    // syc thread message handler. for dispatch something syc.
    private final static int MSG_SYC_NEWLOCATION_RECEIVER = 1;
    private final static int MSG_INIT_LOCATION            = 2;
    private void initLocationHandler(){
        if(mLocationHandler == null){
            HandlerThread ht = new HandlerThread(LocationService.class.getSimpleName());
            ht.start();
            mLocationHandler = new LocationHandler(ht.getLooper());
        }
        mLocationHandler.removeCallbacksAndMessages(null);//clear all message.
    }
    private class LocationHandler extends Handler{
        public LocationHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case MSG_SYC_NEWLOCATION_RECEIVER:{
                    LocationDBData locationDBData = (LocationDBData) msg.obj;
                    if(locationDBData == null)return;//make last location is aviable
                    if(updateUserSportsData(locationDBData)){//we notify UI.
                        //we save it first
                        SportUtil.setLastLocation(locationDBData.getLatitude(), locationDBData.getLongitude());
                        mMainHandler.sendEmptyMessage(MSG_MAIN_NOTIFY_LOCATIONCHANGE);
                    }
                }break;
                case MSG_INIT_LOCATION:{
                    initLocationControl();
                    initSportsControl();//here we should init sports data.
                }break;
            }
        }
        public void sendUnRepeatMeaasge(int messageId,Object object){
            sendUnrepeatMessageDelayed(messageId, object, 0);
        }
        public void sendUnrepeatMessageDelayed(int messageId,Object object, int delay){
            mLocationHandler.removeMessages(messageId);
            Message message = mLocationHandler.obtainMessage(messageId);
            message.obj     = object;
            if(delay <=0 ) {
                mLocationHandler.sendMessage(message);
            }else{
                mLocationHandler.sendMessageDelayed(message, delay);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    // main thead handler. its in UI-Thread. so only dispatch something UI message here.
    private final static int MSG_MAIN_NOTIFY_LOCATIONCHANGE = 1;
    private Handler mMainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case MSG_MAIN_NOTIFY_LOCATIONCHANGE:{
                    Util.postEvent(Util.produceEventMSG(EventID.ID_MSG_LocationControl, EVENTID_MSG_LOCATIONCHANGE, null));
                }break;
            }
        }
    };
}
