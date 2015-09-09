package com.greenorange.gooutdoor.UI.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import com.greenorange.gooutdoor.Bean.UserSportsData;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.GOConfig;
import com.greenorange.gooutdoor.Util.AnimatorUtils;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Interface.iMapScreenShotListener;
import com.greenorange.gooutdoor.mode.SportsDataSaveAsyncTask;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.Service.LocationService;
import com.greenorange.gooutdoor.Service.MapUIControl;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.SportsDao;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Model.Event.EventMSG;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.squareup.otto.Subscribe;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by JasWorkSpace on 15/7/23.
 */
public abstract class BaseMapFragment extends BaseFragment implements iMapScreenShotListener, View.OnClickListener {

    public SportsDao mSportsDao;

    private final static int MSG_LOCATION_CHANGE_RECEIVER    = 0;
    private final static int MSG_SPORT_STATE_CHANGE_RECEIVER = 1;
    private final static int MSG_MOVE_TO_LASTLOCATION        = 2;
    private final static int MSG_FRAGMENT_RESUME             = 3;
    private final static int MSG_SHOW_CURRENT_LOCATION       = 4;
    private final static int MSG_SHOW_CURRENT_LOCATION_ICON  = 5;
    private final static int MSG_ADAPTIONMAPVIEW             = 6;

    private Handler mBaseHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(getFragmentState() != FRAGMENT_STATE_RESUMED) return;
            switch (msg.what){
                case MSG_LOCATION_CHANGE_RECEIVER:{
                    if(getFragmentState() == FRAGMENT_STATE_RESUMED
                            && !isInScreenShot.get()) {//we should update it.
                        MapUIControl mapUIControl = getMapUIControl();
                        if(mapUIControl != null) {
                            if(onLocationChange(mapUIControl) && isNeedAdapterMapView()){
                                onAdaptionMapView(mapUIControl);//we adapter map view here.
                                sendUnRepeatMessage(MSG_SHOW_CURRENT_LOCATION_ICON);//show icon
                                return;
                            }
                        }
                        if(msg.arg1 == 1){//its send from MSG_FRAGMENT_RESUME. it request move to last point.
                            moveToLastPoint(true);
                        }
                    }
                }break;
                case MSG_SPORT_STATE_CHANGE_RECEIVER:{
                    int newState  = msg.arg1;
                    int lastState = msg.arg2;
                    if(getFragmentState() == FRAGMENT_STATE_RESUMED) {//we should update it.
                        if (!SportUtil.isSportingState(newState)) {
                            onClear();
                        }
                    }
                }break;
                case MSG_MOVE_TO_LASTLOCATION:{
                    moveToLastPoint(true);
                }break;
                case MSG_FRAGMENT_RESUME:{
                    onClear();//remove it first.
                    sendUnRepeatMessage(MSG_LOCATION_CHANGE_RECEIVER, 1);//for reload Map Line.
                    sendUnRepeatMessage(MSG_SHOW_CURRENT_LOCATION_ICON);
                }break;
                case MSG_SHOW_CURRENT_LOCATION:{
                    moveToLastPoint();
                }break;
                case MSG_SHOW_CURRENT_LOCATION_ICON:{
                    if(icon_showCurrentLocation != null
                            && icon_showCurrentLocation.getVisibility() != View.VISIBLE){
                        AnimatorUtils.startScaleAnimation(true, 600, icon_showCurrentLocation);
                    }
                }break;
            }
        }
    };
    public void sendUnRepeatMessage(int message){
        mBaseHandler.removeMessages(message);
        mBaseHandler.sendEmptyMessage(message);
    }
    public void sendUnRepeatMessage(int message, int params){
        mBaseHandler.removeMessages(message);
        Message m = mBaseHandler.obtainMessage(message);
        m.arg1 = params;
        m.sendToTarget();
    }
    //notify update map ui
    protected abstract boolean onLocationChange(MapUIControl mapUIControl);
    //notify update map
    protected abstract boolean onAdaptionMapView(MapUIControl mapUIControl);
    //for clear
    protected abstract boolean onClear();
    //for move to last position
    protected abstract boolean onMove(double latitude, double longitude);
    //for maptype
    public int getMapType(){
        return MAPTYPE.MAPTYPE_UNKNOW;
    }
    //for screenshot
    public boolean getMaoScreenShot(iMapScreenShotListener listener){
        if(isInScreenShot.get()){
            if(listener != null){
                listener.onScreenShotFail(getMapType(),"is shotting. pls waiting.");
            }
            return false;
        }
        isInScreenShot.set(true);
        mapScreenShotListener = listener;
        return true;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Subscribe
    public void EventMSGListener(EventMSG eventMSG){
        switch(eventMSG.ID) {
            ///////////////////////////////////////////////////////////
            // LocationControl
            case EventID.ID_MSG_LocationControl:{
                switch(eventMSG.msg){
                    case LocationService.EVENTID_MSG_LOCATIONCHANGE:{
                        sendUnRepeatMessage(MSG_LOCATION_CHANGE_RECEIVER);
                    }break;
                }
            }break;
            case EventID.ID_MSG_SaveStepChange:{
                switch(eventMSG.msg){
                    case SportsDataSaveAsyncTask.STEP_READY:{
                        sendUnRepeatMessage(MSG_LOCATION_CHANGE_RECEIVER, 1);//for reload Map Line.
                    }break;
                }
            }break;
        }
    }
    @Subscribe
    public void EventClickListener(EventClick eventClick){}
    //////////////////////////////////////////////////////////////////////////////////
    // for state change
    @Subscribe
    public void EventStateChangeListener(EventStateChange eventStateChange){
        switch (eventStateChange.ID){
            /////////////////////////////////////////
            // Application Sports State
            case EventID.ID_STATE_APPLICATION_SPORTSSTATE:{
                mBaseHandler.removeMessages(MSG_SPORT_STATE_CHANGE_RECEIVER);
                Message message = mBaseHandler.obtainMessage(MSG_SPORT_STATE_CHANGE_RECEIVER);
                message.arg1    = eventStateChange.newState;
                message.arg2    = eventStateChange.lastState;
                message.sendToTarget();
            }break;
        }
    }
    public SportsDao getSportsDao(){
        if(mSportsDao == null){
            mSportsDao = (SportsDao) GOApplication.getDaoManager().getManager(Dao.SportsDao);
        }
        return mSportsDao;
    }
    public UserSportsData getUserSportsData(){
        return getSportsDao().getCurrentUserSportsData();
    }
    public MapUIControl getMapUIControl(){
        return getSportsDao().getMapUIControl();
    }
    public int getStartMarkerDrawableId(){
        return R.drawable.point_start;
    }
    public int getCurrentMarkerDrawableId(){
        return R.drawable.point_location;
    }
    ////////////////////////////////////////////////////////////////////////////
    // dispatch touch event.
    public void onTouchMap(MotionEvent motionEvent){
        mMapOnTouch = !(motionEvent.getAction() == MotionEvent.ACTION_UP);
    }
    protected  boolean mMapOnTouch = false;
    public boolean isNeedAdapterMapView(){
        return !mMapOnTouch;
    }
    ///////////////////////////////////////////////////////////////////////////
    // move to last point.
    protected void moveToLastPoint(){
        moveToLastPoint(false);
    }
    protected void moveToLastPoint(boolean lastLocation){
        MapUIControl mapUIControl = getMapUIControl();
        if(mapUIControl != null && mapUIControl.mCurrentLocationData != null) {
            onMove(mapUIControl.mCurrentLocationData.getLatitude(), mapUIControl.mCurrentLocationData.getLongitude());
        }else if(lastLocation){
            double[] lastlocation = SportUtil.getLastLocation();
            if(lastlocation != null){//read it first file.
                onMove(lastlocation[0], lastlocation[1]);
            }else {//default is QC Company.
                onMove(GOConfig.DEFAULT_LOCATION_LATITUDE, GOConfig.DEFAULT_LOCATION_LONGITUDE);
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    // for override
    @Override
    public void onResume() {
        super.onResume();
        //sendUnRepeatMessage(MSG_SHOW_CURRENT_LOCATION_ICON);
        //mBaseHandler.sendEmptyMessageDelayed(MSG_MOVE_TO_LASTLOCATION,50);
        sendUnRepeatMessage(MSG_FRAGMENT_RESUME);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ///////////////////////////////////////////////////////////////////////////
    protected iMapScreenShotListener mapScreenShotListener = null;
    protected AtomicBoolean isInScreenShot = new AtomicBoolean();
    @Override
    public boolean onScreenShotFail(int mapType, String result) {
        if(mapScreenShotListener != null){
            mapScreenShotListener.onScreenShotFail(mapType, result);
        }
        isInScreenShot.set(false);
        return true;
    }
    @Override
    public boolean onScreenShotSuccess(int mapType, Bitmap bitmap,String screenshotfile) {
        if(mapScreenShotListener != null){
            mapScreenShotListener.onScreenShotSuccess(mapType, bitmap, screenshotfile);
        }
        isInScreenShot.set(false);
        return false;
    }
    public boolean isScreenShotEnable(){
        MapUIControl mapUIControl = getMapUIControl();
        if(mapUIControl != null){
            return mapUIControl.hasLine();
        }
        return false;
    }
    /////////////////////////////////////////////
    private View icon_showCurrentLocation;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        icon_showCurrentLocation = view.findViewById(R.id.showcurrentlocation);
        icon_showCurrentLocation.setOnClickListener(this);
        mBaseHandler.sendEmptyMessageDelayed(MSG_MOVE_TO_LASTLOCATION,50);//move to last location.
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.showcurrentlocation:{
            sendUnRepeatMessage(MSG_SHOW_CURRENT_LOCATION);
        }break;
        }
    }
}
