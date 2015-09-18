package com.greenorange.gooutdoor.framework.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.Toast;

import com.autonavi.amap.mapcore.ConnectionManager;

import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/6.
 */
public class AndroidUtils {

    public static boolean hasSeriice(Context context, Intent intent){
        return hasService(context, intent, 0);
    }
    public static boolean hasService(Context context, Intent intent, int flog){
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> infoList = packageManager.queryIntentServices(intent, flog);
        return infoList!=null && infoList.size()>0;
    }

    public static int[] getResourceId(Context contex , int arrsyid){
        TypedArray typedArray = contex.getResources().obtainTypedArray(arrsyid);
        int len = typedArray.length();
        int[] ids = new int[len];
        for(int i=0; i< len; i++){
            ids[i] = typedArray.getResourceId(i, 0);
        }
        return ids;
    }
    public static void Toast(Context context, int msg, int time){
        Toast.makeText(context, msg, time).show();
    }
    public static void Toast(Context context, String msg, int time){
        Toast.makeText(context, msg, time).show();
    }
    public static String getOrientationString(int orientation){
        switch (orientation){
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:return "SCREEN_ORIENTATION_REVERSE_PORTRAIT";
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:return "SCREEN_ORIENTATION_LANDSCAPE";
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:return "SCREEN_ORIENTATION_PORTRAIT";
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:return "SCREEN_ORIENTATION_REVERSE_LANDSCAPE";
        }
        return "UNKNOW";
    }
    public static void setSystemUIVisibility(View view){
        if(ApiHelper.HAS_VIEW_SYSTEM_UI_FLAG_LAYOUT_STABLE){
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                   |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                   |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }
    public static boolean isWifiConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            return info.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }
    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info == null ? false : info.isConnected());
    }
}
