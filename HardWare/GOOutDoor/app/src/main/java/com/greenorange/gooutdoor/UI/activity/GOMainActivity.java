package com.greenorange.gooutdoor.UI.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.GOConfig;
import com.greenorange.gooutdoor.UI.fragment.BaseMapFragment;
import com.greenorange.gooutdoor.UI.fragment.FragmentFloatControl;
import com.greenorange.gooutdoor.UI.fragment.FragmentGaodeMap;
import com.greenorange.gooutdoor.UI.fragment.FragmentMapData;
import com.greenorange.gooutdoor.Util.Utils;
import com.greenorange.gooutdoor.View.layout.FloatControlSports;
import com.greenorange.gooutdoor.View.layout.MapDataInfo;
import com.greenorange.gooutdoor.framework.Dao.ApplicationDao;
import com.greenorange.gooutdoor.framework.Utils.AndroidUtils;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.greenorange.gooutdoor.mode.ShareExt;
import com.greenorange.gooutdoor.mode.SportsDataSaveAsyncTask;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.Service.SportsControl;
import com.greenorange.gooutdoor.UI.fragment.dialog.FinishSportsDialog;
import com.greenorange.gooutdoor.Util.ActivityUtils;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Dao.SportsDao;
import com.greenorange.gooutdoor.framework.Dao.MapDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Event.EventDialogFragmentCLick;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Model.Event.EventMSG;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.greenorange.gooutdoor.framework.widget.dialogs.iface.ISimpleDialogListener;
import com.squareup.otto.Subscribe;


/**
 * Created by JasWorkSpace on 15/4/10.
 */
public class GOMainActivity extends BaseActionBarActivity implements ISimpleDialogListener {
    private SportsDao        mSportsDao;
    //private FragmentMapData mFragmentMapData;
    private FragmentMapData mFragmentMapData;
    private BaseMapFragment  mBaseMapFragment;
    //private FragmentFloatControl mFragmentFloatControl;
    private FragmentFloatControl mFragmentFloatControl;
    private ShareExt         mSharePicture;

    public View getWaterMakerView(){
        return GOMainActivity.this.findViewById(R.id.screenshot);
    }
    public BaseMapFragment getMapFragment(){
        return mBaseMapFragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gomain);
        mSportsDao = (SportsDao) mDaoManager.getManager(Dao.SportsDao);
        //mHandler.sendEmptyMessage(MSG_LOAD_MAP);
        loadMap();
        loadActionBar();
        loadFloatControl();
        loadMapData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(MSG_START_LOCATION);//re start location.
        mHandler.sendEmptyMessage(MSG_ONRESUME);
    }
    private void loadActionBar(){
        initActionBar(false);
        setTitle(R.string.goapplication_app_name);
        if(mToolBar != null)mToolBar.setBackgroundResource(R.color.toolbar_bg_color_1);
        //mToolBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.actionbar_top_in));
    }
    private void loadMap(){
        Fragment fragment = null;
        int currentmaptype = ((MapDao)mDaoManager.getManager(Dao.MapDao)).getCurrentMAPTYPE();
        if(currentmaptype == MAPTYPE.MAPTYPE_GAODE){
            fragment = new FragmentGaodeMap();
        }
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.maplayout,mBaseMapFragment= (BaseMapFragment) fragment).commit();
        }
    }
    private void loadFloatControl(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragmentfloatcontrol_in, R.anim.fragmentfloatcontrol_out);
        //fragmentTransaction.add(R.id.control, mFragmentFloatControl = new FragmentFloatControl()).commit();
        fragmentTransaction.replace(R.id.control, mFragmentFloatControl = new FragmentFloatControl()).commit();
    }
    private void loadMapData(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.actionbar_top_in, R.anim.actionbar_top_out);
        //fragmentTransaction.add(R.id.mapdata, mFragmentMapData = new FragmentMapData()).commit();
        fragmentTransaction.replace(R.id.mapdata, mFragmentMapData   = new FragmentMapData()).commit();
    }
    private final static int MSG_SHOW_MAPDATA           = 1;
    private final static int MSG_HIDE_MAPDATA           = 2;
    private final static int MSG_LOAD_MAPDATA           = 3;
    private final static int MSG_LOAD_ACTIONBAR_CONTROL = 4;
    private final static int MSG_LOAD_MAP               = 5;
    private final static int MSG_EXIT_APPLICATION       = 6;
    private final static int MSG_START_LOCATION         = 7;
    private final static int MSG_FINISH_SPORTS_CLICK    = 8;
    private final static int MSG_SHARE                  = 9;
    private final static int MSG_ONRESUME               = 10;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_SHOW_MAPDATA:{
                    mFragmentMapData.animationVisibility(View.VISIBLE);
                    setActionBarVisable(View.GONE);
                }break;
                case MSG_HIDE_MAPDATA:{
                    mFragmentMapData.animationVisibility(View.GONE);
                    setActionBarVisable(View.VISIBLE);
                }break;
                case MSG_LOAD_MAPDATA:{//unuse now
                    loadMapData();
                }break;
                case MSG_LOAD_ACTIONBAR_CONTROL:{//unuse now
                    loadActionBar();
                    loadFloatControl();
                    mHandler.sendEmptyMessageDelayed(MSG_LOAD_MAPDATA, 500);
                }break;
                case MSG_LOAD_MAP:{//unuse now
                    loadMap();
                }break;
                case MSG_EXIT_APPLICATION:{
                    mSportsDao.closeLocation();
                    Util.notifyApplicationFinish();
                    finish();
                }break;
                case MSG_START_LOCATION:{
                    mSportsDao.reStartLocation();
                }break;
                case MSG_FINISH_SPORTS_CLICK:{
                    FinishSportsDialog.dispatcherClick(msg.arg1);
                }break;
                case MSG_SHARE:{
                    if(mBaseMapFragment.isScreenShotEnable()) {
                        if (mSharePicture == null || mSharePicture.isCancelled()
                                || mSharePicture.getStatus()== AsyncTask.Status.FINISHED) {
                            mSharePicture = new ShareExt(GOMainActivity.this);
                            mSharePicture.execute(Utils.getShareFile());
                        }
                    }else{
                        AndroidUtils.Toast(GOMainActivity.this, R.string.share_error_nodata, Toast.LENGTH_SHORT);
                    }
                }break;
                case MSG_ONRESUME:{
                    onResumeRestoreState();
                }break;
            }
        }
    };
    private synchronized void onResumeRestoreState(){
        if(mSportsDao != null){
            int state = mSportsDao.getCurrentSportsState();
            checkMapdataState(state);
            mFragmentFloatControl.init(state);
            mBaseMapFragment.init(state);
        }
    }
    private void checkMapdataState(int state){
        if(state == SportsState.STATE_RECORD
                || state == SportsState.STATE_PAUSE
                || state == SportsState.STATE_STOP){//delay for animation.
            setMapDataisibility(true);
        }else{
            setMapDataisibility(false);
        }
    }
    private void setActionBarVisable(int vis){
        if(mToolBar != null){
            if(mToolBar.getVisibility() == vis)return;
            mToolBar.setVisibility(vis);
            mToolBar.clearAnimation();
            mToolBar.startAnimation(AnimationUtils.loadAnimation(GOMainActivity.this,
                    vis == View.VISIBLE ? R.anim.actionbar_top_in : R.anim.actionbar_top_out));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gomainactivity, menu);
        //hide "share","settings","record"
        if(!GOConfig.DEBUG) {
            menu.findItem(R.id.action_share).setVisible(false);
        }
        if(!GOConfig.DEBUG_UI){
            menu.findItem(R.id.action_ui).setVisible(false);
        }
        return true;
    }

    private long mlastonBackPressedTime;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(SportUtil.isSportingState(mSportsDao.getCurrentSportsState())){
            Toast.makeText(GOMainActivity.this, R.string.gomainactivity_sportsing_exitfail, Toast.LENGTH_SHORT).show();
        }else{
            if(System.currentTimeMillis() - mlastonBackPressedTime < 800){
                mHandler.sendEmptyMessage(MSG_EXIT_APPLICATION);
            }else{
                Toast.makeText(GOMainActivity.this, R.string.gomainactivity_exit_check, Toast.LENGTH_SHORT).show();
            }
        }
        mlastonBackPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSportsDataSaveAsyncTask != null && !mSportsDataSaveAsyncTask.isCancelled()){
            mSportsDataSaveAsyncTask.cancel(true);
        }
        mSportsDataSaveAsyncTask = null;
        mHandler.sendEmptyMessage(MSG_EXIT_APPLICATION);//make sure its exit.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_exit:{
                mHandler.sendEmptyMessage(MSG_EXIT_APPLICATION);
            }break;
            case R.id.action_settings:{
                ActivityUtils.startActivityWithAnimation(GOMainActivity.this, GOSettingsActivity.GOSettingsMainActivity.class);
            }break;
            case R.id.action_share:{
                mHandler.sendEmptyMessage(MSG_SHARE);
            }break;
            case R.id.action_ui:{
                ActivityUtils.startActivityWithAnimation(this, GOSettingsActivity.GOActivity.class);
            }break;
            case R.id.action_record:{
                ActivityUtils.startActivityWithAnimation(GOMainActivity.this, GOSettingsActivity.GORecordActivity.class);
            }break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mSharePicture != null && !mSharePicture.isCancelled()){
            mSharePicture.cancel(true);
        }
        mSharePicture = null;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Subscribe
    public void EventMSGListener(EventMSG eventMSG){
        switch(eventMSG.ID) {
            ///////////////////////////////////////////////////////////
            // FragmentFloatControl
            case EventID.ID_MSG_FragmentFloatControl:{
                if(FragmentFloatControl.EVENT_MSG_NEWSPORTS == eventMSG.msg){
                    int newSportsId = (Integer)eventMSG.object;
                    mSportsDao.startNewSports(newSportsId);
                }
            }break;
            case EventID.ID_MSG_SaveStepChange:{
                switch (eventMSG.msg){
                    case SportsDataSaveAsyncTask.STEP_FINISH:{
                        SportsControl sportsControl = mSportsDao.getSportsControl();
                        if(sportsControl != null) {
                             sportsControl.finishSports();
                        }
                    }break;
                }
            }break;
        }
    }
    @Subscribe
    public void EventDialogFragmentCLickListener(EventDialogFragmentCLick eventClick){
        switch (eventClick.ID) {
            /////////////////////////////////////////
            // FinishSportsDialog
            case EventID.ID_CLICK_FinishSportsDialog: {
                if(FinishSportsDialog.CLICK_ID_OK == eventClick.viewID){
                    SportsControl sportsControl = mSportsDao.getSportsControl();
                    if(sportsControl != null) {
                        sportsControl.stopSports();
                        if(!needsave(sportsControl)){
                            sportsControl.finishSports();
                        }
                    }
                }
            }
            break;
        }
    }
    private SportsDataSaveAsyncTask mSportsDataSaveAsyncTask;
    private boolean needsave(SportsControl sportsControl){
        if(mBaseMapFragment.isScreenShotEnable() && sportsControl.isNeedToSave()) {//sport data is useable. so save it
            if (mSportsDataSaveAsyncTask == null
                    || mSportsDataSaveAsyncTask.isCancelled() || mSportsDataSaveAsyncTask.getStatus()== AsyncTask.Status.FINISHED) {
                ApplicationDao applicationDao = (ApplicationDao) GOApplication.getDaoManager().getManager(Dao.ApplicationDao);
                String sportid = sportsControl.getSportsID();
                if (applicationDao != null && !TextUtils.isEmpty(sportid)) {
                    mSportsDataSaveAsyncTask = new SportsDataSaveAsyncTask(GOMainActivity.this);
                    mSportsDataSaveAsyncTask.execute(applicationDao.getScreenShotFileName(sportid));
                    return true;
                }
            }
        }
        return false;
    }
    @Subscribe
    public void EventStateChangeListener(EventStateChange eventStateChange){
        switch (eventStateChange.ID){
            /////////////////////////////////////////
            // Application Sports State
            case EventID.ID_STATE_APPLICATION_SPORTSSTATE:{
                int state = eventStateChange.newState;
                checkMapdataState(state);
            }break;
        }
    }
    @Subscribe
    public void EventClickListener(EventClick eventClick){
        switch(eventClick.ID){
            /////////////////////////////////////////////////
            //  FloatControlSports
            case EventID.ID_CLICK_FloatControlSports:{
                if(FloatControlSports.CLICK_ID_CONTINUE == eventClick.viewID){
                    SportsControl sportsControl = mSportsDao.getSportsControl();
                    if(sportsControl != null) {//resume sports
                        sportsControl.resumeSports();
                    }
                } else if(FloatControlSports.CLICK_ID_PAUSE == eventClick.viewID){
                    SportsControl sportsControl = mSportsDao.getSportsControl();
                    if(sportsControl != null) {//pause sports
                        sportsControl.pauseSports();
                    }
                }
            }break;
            case EventID.ID_CLICK_MapDataInfo:{
                if(MapDataInfo.ID_CLICK_CAMERA == eventClick.viewID){
                    mHandler.sendEmptyMessage(MSG_SHARE);
                }
            }break;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    // api
    private void setMapDataisibility(boolean show){
        mHandler.removeMessages(MSG_HIDE_MAPDATA);
        mHandler.removeMessages(MSG_SHOW_MAPDATA);
        mHandler.sendEmptyMessageDelayed(show ? MSG_SHOW_MAPDATA : MSG_HIDE_MAPDATA, 500);
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    // dialog event listener
    @Override
    public void onNegativeButtonClicked(int requestCode) {
        switch(requestCode){
            case EventID.ID_CLICK_FinishSportsDialog:{
                dispatcherFinishSportsDialogClickEvent(FinishSportsDialog.CLICK_ID_CANCEL);
            }break;
        }
    }
    @Override
    public void onNeutralButtonClicked(int requestCode) {

    }
    @Override
    public void onPositiveButtonClicked(int requestCode) {
        switch(requestCode){
            case EventID.ID_CLICK_FinishSportsDialog:{
                dispatcherFinishSportsDialogClickEvent(FinishSportsDialog.CLICK_ID_OK);
            }break;
        }
    }
    private void dispatcherFinishSportsDialogClickEvent(int id){
        Message message = mHandler.obtainMessage(MSG_FINISH_SPORTS_CLICK);
        message.arg1    = id;
        message.sendToTarget();
    }
}
