package com.greenorange.gooutdoor.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.Settings;
import android.view.Surface;
import android.widget.Toast;

import com.greenorange.gooutdoor.Bean.UserSportsData;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.Service.SportsControl;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.SportsDao;
import com.greenorange.gooutdoor.framework.Model.Event.ReportID;
import com.greenorange.gooutdoor.framework.Utils.AndroidUtils;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.greenorange.outdoorhelper.R;

import java.io.File;

/**
 * Created by JasWorkSpace on 15/4/18.
 */
public class Utils {

    public static void ChangeScreenRotatiton(Activity activity){
        if(Settings.System.getInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) <= 0){
            AndroidUtils.Toast(activity, R.string.accelerometer_rotation_un_open , Toast.LENGTH_SHORT);
            return;
        }
        if(getDisplayRotation(activity) >= 180){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Util.notifyScreenOrientationChange(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            Util.notifyScreenOrientationChange(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        }
    }
    public static int getDisplayRotation(Activity activity){
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation){
            case Surface.ROTATION_0: return 0;
            case Surface.ROTATION_90: return 90;
            case Surface.ROTATION_180: return 180;
            case Surface.ROTATION_270: return 270;
        }
        return 0;
    }
    public static UserSportsData getUserSportsData(Context context){
        SportsDao     sportsDao     = (SportsDao) GOApplication.getDaoManager().getManager(Dao.SportsDao);
        SportsControl sportsControl = sportsDao.getSportsControl();
        if(sportsControl != null){
            return sportsControl.getUserSportsData();
        }
        return null;
    }

    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(),PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {}
        return pi;
    }

    public static String getShareFile(){//maybe no exsit.
        return Environment.getExternalStorageDirectory().getPath() + File.separator + "screenshot.jpg";
    }
}
