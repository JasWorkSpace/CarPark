package com.greenorange.gooutdoor;

import android.app.Application;
import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.framework.Dao.ApplicationDao;
import com.greenorange.gooutdoor.framework.Dao.DaoManager;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Dao.impl.DaoManagerImpl;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.widget.calligraphy.CalligraphyConfig;
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/4/10.
 */
public class GOApplication extends Application {
    private static DaoManager    mDaoManager;
    private static GOApplication mGOApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("GOApplication onCreate ************** " + System.currentTimeMillis());
        //init front
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(getString(R.string.config_default_fontpath))
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        mDaoManager = new DaoManagerImpl();
        mDaoManager.init(this);
        mGOApplication = this;
        imitateLoginUser();
        Log.d("GOApplication onCreate finish ************** " + System.currentTimeMillis());
    }

    public static DaoManager getDaoManager(){
        return mDaoManager;
    }
    public static GOApplication getInstance(){ return mGOApplication; }

    ///////// imitate user login
    private final static String GreenOrange = "GreenOrange";
    private final static String USERID      = "1";
    private void imitateLoginUser(){
        UserDBData userData = new UserDBData();
        userData.setUserid(USERID);
        userData.setUsername(GreenOrange);
        userData.setUserpassword(GreenOrange);
        //////////////////////////////////////////////////
        ApplicationDao applicationDao = (ApplicationDao) mDaoManager.getManager(Dao.ApplicationDao);
        if(applicationDao.checkUser(userData)) {
            applicationDao.LoginUser(userData);
            Log.d("Applictaion Login " + applicationDao.getUser().toString());
        }else{
            throw new IllegalArgumentException("Login fail !!!!");
        }
        /////////////////////////////////////////////////
        applicationDao.setSportsSTATE(SportsState.STATE_UNKNOW);
    }
}
