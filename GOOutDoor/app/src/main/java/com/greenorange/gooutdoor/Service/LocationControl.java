package com.greenorange.gooutdoor.Service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.greenorange.gooutdoor.GOConfig;
import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.MapDao;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationCallBack;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationConfig;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationFilterConfig;
import com.greenorange.gooutdoor.framework.Model.Interface.iLocationControl;

/**
 * Created by JasWorkSpace on 15/4/21.
 */
public abstract class LocationControl implements iLocationControl {
    public boolean isOpen = false;
    public LocationConfig mLocationConfig;
    public Context mContext;
    public MapDao  mMapDao;
    private DebugLocationControl mDebugLocationControl;
    @Override
    public boolean isOpen() {
        return isOpen;
    }
    public LocationControl(Context context){
        mContext = context;
        mMapDao = (MapDao) GOApplication.getDaoManager().getManager(Dao.MapDao);
        mLocationConfig = mMapDao.getMAPLocationConfig(getLocationMapType());
        if(mLocationConfig == null || !mLocationConfig.enable){
            throw new IllegalArgumentException("LocationControl mLocationConfig fail !!! mLocationConfig is " + (mLocationConfig == null ? "null" : mLocationConfig.toString()));
        }
        if(GOConfig.DEBUG_LOCATION){//for debug data.
            mDebugLocationControl = new DebugLocationControl(mLocationConfig.frq);
        }
        openLocation();
    }
    @Override
    public boolean openLocation() {
        Log.d("openLocation mLocationConfig=" + mLocationConfig.toString());
        if(mDebugLocationControl != null){
            mDebugLocationControl.setDebugState(true);
        }
        return false;
    }
    @Override
    public boolean closeLocation() {
        if(mDebugLocationControl != null){
            mDebugLocationControl.setDebugState(false);
        }
        return false;
    }
    @Override
    public LocationFilterConfig getLocationFilterConfig(int sportsType) {
        return mMapDao.getMAPFliterConfig(getLocationMapType(), sportsType);
    }
    @Override
    public LocationFilterConfig getdefaultFilterConfig() {
        return mMapDao.getDefaultFliterConfig();
    }
    public abstract LocationDBData getDebugLocationDBData();
    //////////////////////////////////////
    private LocationCallBack mLocationCallBack = null;
    @Override
    public boolean setLocationCallBack(LocationCallBack callBack) {
        mLocationCallBack = callBack;
        return true;
    }
    public void notifityLocationChange(LocationDBData locationData){
        Log.d("notifityLocationChange mLocationCallBack=" + (mLocationCallBack!=null)
                +", locationData="+(locationData != null));
        if(mLocationCallBack != null && locationData != null){
            mLocationCallBack.onLocationChange(locationData);
        }
    }
    private class DebugLocationControl{
        private final static int MSG_DEBUG_STATE_CHANGE = 1;
        private final static int MSG_DEBUG_TIMEOUT      = 2;

        private boolean isOpen = false;
        private int     timeout = 0;
        private LocationDBData mLastLocationDBData;

        public DebugLocationControl(int delay){
            timeout = delay;
        }
        public void setDebugState(boolean start){
            isOpen = start;
            sendMsg(MSG_DEBUG_STATE_CHANGE);
        }
        private Handler mDebugHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_DEBUG_STATE_CHANGE:{
                    handlerTimeout();
                }break;
                case MSG_DEBUG_TIMEOUT:{
                    if(isOpen){
                        if(mLastLocationDBData == null)mLastLocationDBData = getDebugLocationDBData();
                        if(mLastLocationDBData != null) {
                            double[] add = getAddDebug();
                            mLastLocationDBData.setLatitude(mLastLocationDBData.getLatitude() + add[0]);
                            mLastLocationDBData.setLongitude(mLastLocationDBData.getLongitude() + add[1]);
                            mLastLocationDBData.setTime(System.currentTimeMillis());
                            notifityLocationChange(new LocationDBData(mLastLocationDBData));
                        }
                    }
                    handlerTimeout();
                }break;
            }
            }
        };
        private void sendMsg(int msg){
            mDebugHandler.removeMessages(msg);
            mDebugHandler.sendEmptyMessage(msg);
        }
        private void handlerTimeout(){
            mDebugHandler.removeMessages(MSG_DEBUG_TIMEOUT);
            if(isOpen){
                mDebugHandler.sendEmptyMessageDelayed(MSG_DEBUG_TIMEOUT, timeout);
            }
        }
        private int debugindex = 1;
        private double[] getAddDebug(){
            return debugAdd[(debugindex++)%debugAdd.length];
        }
        private double[][] debugAdd = new double[][]{
                {0.0001, 0.0001},
                {0.0003, 0.0001},
                {0.0001, 0.0003},
                {0.0003, 0.0003},
                {0.0008, 0.0005}
        };
    }
}
