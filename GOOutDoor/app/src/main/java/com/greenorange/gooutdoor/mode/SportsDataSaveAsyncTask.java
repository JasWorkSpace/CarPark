package com.greenorange.gooutdoor.mode;


import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.greenorange.gooutdoor.Bean.UserSportsData;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.Service.SportsControl;
import com.greenorange.gooutdoor.UI.activity.GOMainActivity;
import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.entity.SportsDetailDBData;
import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.framework.DB.SportsDBHelper;
import com.greenorange.gooutdoor.framework.DB.SportsDetailDBHelper;
import com.greenorange.gooutdoor.framework.DB.SportsTYPEDBHelper;
import com.greenorange.gooutdoor.framework.DB.UserDBHelper;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Dao.SportsDao;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.greenorange.outdoorhelper.R;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/27.
 */
public class SportsDataSaveAsyncTask extends ShareExt {

    public SportsDataSaveAsyncTask(GOMainActivity goMainActivity) {
        super(goMainActivity);
    }
    public final static int STEP_READY      = 1;
    public final static int STEP_SCREENSHOT = 2;
    public final static int STEP_FINISH     = 3;
    @Override
    protected String doInBackground(Object... params) {
        UserSportsData userSportsData = getUserSportsData();
        if(userSportsData != null){
            long savetime = System.currentTimeMillis();
            publishProgress(STEP_READY);
            if(saveDB(userSportsData)) {
                Log.d("SportsDataSaveAsyncTask save db success !!!");
                while (System.currentTimeMillis() - savetime < 1000) {
                    try {//make sure UI is ready
                        Thread.sleep(100);
                    } catch (Throwable e) {
                    }
                }
                publishProgress(STEP_SCREENSHOT);
                return super.doInBackground(params);
            }
        }
        return null;
    }
    private ProgressDialog mProgressDialog;
    private synchronized void show(String msg){
        if(isActivityUseAble() && !TextUtils.isEmpty(msg)) {
            if (mProgressDialog == null) {//we don,t want use can stop it.
                mProgressDialog = new ProgressDialog(mGOMainActivity);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setCanceledOnTouchOutside(false);
            }
            mProgressDialog.setMessage(msg);
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }
    private synchronized void hide(){
        if(mProgressDialog != null){
            if(mProgressDialog.isShowing())mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        switch (values[0]){
            case STEP_READY:{
                notifyStepChange(STEP_READY);
                show(mGOMainActivity.getString(R.string.sports_data_save_asynctack_savedata));
            }break;
            case STEP_SCREENSHOT:{
                notifyStepChange(STEP_SCREENSHOT);
                show(mGOMainActivity.getString(R.string.sports_data_save_asynctack_screenshot));
            }break;
        }
    }
    @Override
    protected void onPostExecute(String s) {
        hide();
        notifyStepChange(STEP_FINISH);
        changeToFinishState();
        super.onPostExecute(s);//share it.
    }
    private void changeToFinishState(){
        SportsDao sportsDao = (SportsDao) GOApplication.getDaoManager().getManager(Dao.SportsDao);
        if(sportsDao != null){
            SportsControl sportsControl = sportsDao.getSportsControl();
            if(sportsControl != null && sportsControl.getSportsState() == SportsState.STATE_STOP) {
                sportsControl.finishSports();
            }
        }
    }
    private UserSportsData getUserSportsData(){
        SportsDao sportsDao = (SportsDao) GOApplication.getDaoManager().getManager(Dao.SportsDao);
        if(sportsDao != null){
            SportsControl sportsControl = sportsDao.getSportsControl();
            if(sportsControl != null && sportsControl.getSportsState()== SportsState.STATE_STOP){
                return sportsControl.getUserSportsData();
            }
        }
        return null;
    }
    private void notifyStepChange(int step){
        Util.postEvent(Util.produceEventMSG(EventID.ID_MSG_SaveStepChange, step, null));
    }
    private boolean saveDB(UserSportsData userSportsData){
        if(userSportsData != null){
            Context context = GOApplication.getInstance();
            UserDBData userDBData = userSportsData.createUserDBDataForSave();
            SportsDBData sportsDBData = userSportsData.createSportsDBDataForSave();
            List<SportsDetailDBData> sportsDetailDBDatas = userSportsData.createSportsDetailDBDataForSave();
            Log.d("************************************************************************");
            Log.d("sportsDetailDBDatas-->" + (sportsDetailDBDatas==null?"why is null???" : sportsDetailDBDatas.size()));
            //add SportsDetailDBData
            if(sportsDetailDBDatas != null && sportsDetailDBDatas.size() > 0){
                for(SportsDetailDBData sportsDetailDBData : sportsDetailDBDatas){
                    SportsDetailDBHelper.insertSportsDetailDB(context, sportsDetailDBData);
                }
            }
            Log.d("****************************");
            Log.d("sportsDBData-->"+(sportsDBData==null?"why sportsDBData is null ????" : sportsDBData.toString()));
            //add sportstype
            SportsTYPEDBHelper.addSports(context, sportsDBData);
            Log.d("****************************");
            Log.d("userDBData-->"+(userDBData==null?"why userDBData is null ????" : userDBData.toString()));
            //add user
            UserDBHelper.updateUserData(context, userDBData);
            Log.d("****************************");
            //add sports
            sportsDBData.setSports_record_state(1);
            Log.d("updateSportsData--> sportsDBData="+sportsDBData.toString());
            SportsDBHelper.updateSportsData(context, sportsDBData);
            return true;
        }
        return false;
    }
}
