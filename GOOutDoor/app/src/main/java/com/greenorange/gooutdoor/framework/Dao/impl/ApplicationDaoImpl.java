package com.greenorange.gooutdoor.framework.Dao.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Application.Contants;
import com.greenorange.gooutdoor.framework.DB.SportsDBHelper;
import com.greenorange.gooutdoor.framework.DB.UserDBHelper;
import com.greenorange.gooutdoor.framework.Dao.ApplicationDao;
import com.greenorange.gooutdoor.framework.Dao.FlashDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.greenorange.gooutdoor.framework.Utils.Util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/4/17.
 */
public class ApplicationDaoImpl extends BroadcastReceiver implements ApplicationDao{

    public ApplicationDaoImpl(Context context){
        initApplication(context);
    }
    /////////////////////////////////////////////
    // for application user
    private UserDBData mUserData = null;
    @Override
    public UserDBData getUser() {
        return mUserData;
    }
    @Override
    public boolean LoginUser(UserDBData userData) {
        if(userData == null || ! userData.checkoutuserData())
            throw new ApplicationException(" unaviable UserDBData " + (userData == null ? "is null !!!" : userData.toString()));
        if(checkUser(userData)) {
            UserDBData userDatainDB = UserDBHelper.getUserDataByUserId(GOApplication.getInstance(), userData.getUserid());
            mUserData = new UserDBData(userDatainDB == null ? userData : userDatainDB);
            mUserData.setLastlogintime(System.currentTimeMillis());
            return saveUserDBDataToDB();
        }
        return false;
    }
    public boolean checkUser(UserDBData userData){
        if(userData == null || !userData.checkoutuserData()) return false;
        UserDBData userDatainDB = UserDBHelper.getUserDataByUserId(GOApplication.getInstance(), userData.getUserid());
        if(userDatainDB != null){
            return (TextUtils.equals(userData.getUsername(), userDatainDB.getUsername())
                    && TextUtils.equals(userData.getUserpassword(), userDatainDB.getUserpassword()));
        }
        return true;
    }
    public boolean saveUserDBDataToDB(){
        if(mUserData != null){
            Context context = GOApplication.getInstance();
            UserDBData userDBDataDB = UserDBHelper.getUserDataByUserId(context, mUserData.getUserid());
            if(userDBDataDB != null){
                return UserDBHelper.updateUserData(context, mUserData);
            }else{
                return UserDBHelper.insertUserData(context, mUserData) != null;
            }
        }
        throw new IllegalArgumentException("unavilabe UserData !!!!!!");
    }
    @Override
    public synchronized SportsDBData getNewSports(Context context, int type) {
//        SportsDBData sportsData = SportsDBHelper.getLastSportsDataByUserId(context, mUserData.getUserid());
//        int sportsId = 1;
//        if(sportsData != null){
//           sportsId += (Integer.parseInt(sportsData.getSports_id()));
//        }
//        sportsData = getSportsData(mUserData.getUserid(), String.valueOf(sportsId));
//        sportsData.setSports_time(System.currentTimeMillis());
//        return sportsData;
        if(!SportUtil.isSportsType(type)){
            throw new ApplicationException("getNewSports fail .why use unknow type " + type);
        }
        long time = System.currentTimeMillis();
        SportsDBData sportsDBData = new SportsDBData();
        sportsDBData.setSports_userid(mUserData.getUserid());//no null
        sportsDBData.setSports_id(String.valueOf(time));//no null
        sportsDBData.setSports_time(time);
        sportsDBData.setSports_type(type);//no null
        //for special time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        sportsDBData.setSports_time_year(calendar.get(Calendar.YEAR));
        sportsDBData.setSports_time_month(calendar.get(Calendar.MONTH));
        sportsDBData.setSports_time_dayofmonth(calendar.get(Calendar.DAY_OF_MONTH));
        sportsDBData.setSports_time_dayofweek(calendar.get(Calendar.DAY_OF_WEEK));
        sportsDBData.setSports_time_weekofyear(calendar.get(Calendar.WEEK_OF_YEAR));
        sportsDBData.setSports_time_weekofmonth(calendar.get(Calendar.WEEK_OF_MONTH));
        sportsDBData.setSports_time_hourofday(calendar.get(Calendar.HOUR_OF_DAY));
        sportsDBData.setSports_time_minute(calendar.get(Calendar.MINUTE));
        //end for special time
        SportsDBHelper.insertSportsData(context, sportsDBData);//maybe is insert fail.
        return sportsDBData;
    }

    ////////////////////////////
    // for sports state
    private int mSportsState;
    @Override
    public int getSportsSTATE() {
        return mSportsState;
    }

    @Override
    public int setSportsSTATE(int state) {
        if(state >= SportsState.STATE_UNKNOW && state <= SportsState.STATE_STOP){
            if(mSportsState != state){
                int last = mSportsState;
                mSportsState = state;
                Util.notifySportsSTATEChange(last, mSportsState);
            }
        }
        return mSportsState;
    }

    @Override
    public File getBaseDir() {
        File file = new File(Environment.getExternalStorageDirectory(), "GOOoutdoorHelper");
        if(!file.exists()){
            file.mkdirs();
        }
        if(file.exists())return file;
        return GOApplication.getInstance().getCacheDir();
    }

    @Override
    public File getScreenShotDir() {
        File dir = new File(getBaseDir(), "ScreenShot");
        if(!dir.exists())dir.mkdirs();
        return dir;
    }

    @Override
    public String getScreenShotFileName(String sportId) {
        File file = new File(getScreenShotDir(), sportId + ".jpg");
        return file.toString();
    }

    ////////////////////////////////////////////////////////////////////////////////
    private void initApplication(Context context){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.BROADCAST.ACTION_APPLICATION_CHANGE);
//        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        context.registerReceiver(this, intentFilter);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(TextUtils.equals(Contants.BROADCAST.ACTION_APPLICATION_CHANGE, action)){
            FlashDao flashDao = (FlashDao) GOApplication.getDaoManager().getManager(Dao.FlashDao);
            if(flashDao != null)flashDao.close();
        }
    }
}
