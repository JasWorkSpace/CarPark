package com.greenorange.myuicontantsbackup.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/**
 * Created by JasWorkSpace on 15/11/17.
 */
public class Utils {

    public static String getString(String string){
        if(TextUtils.isEmpty(string)){
            return "";
        }
        return string;
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
