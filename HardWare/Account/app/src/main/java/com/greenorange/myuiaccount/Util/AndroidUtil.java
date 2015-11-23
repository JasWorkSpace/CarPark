package com.greenorange.myuiaccount.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by JasWorkSpace on 15/10/27.
 */
public class AndroidUtil {
    private static String mClientVersion;
    public  static String getClientVersion(Context context) {
        if(TextUtils.isEmpty(mClientVersion)){
            try {
                PackageInfo PkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return PkgInfo.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "unknow";
    }
    private static String mImei;
    public static String getIMEI(Context context){
        TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        //which is sub0
        String imei = telephonyManager.getDeviceId();
        if(!TextUtils.isEmpty(imei)){
            return mImei = imei;//update imei
        }
        //default imei use sub1
//        imei = telephonyManager.getDeviceId(1);
//        if(!TextUtils.isEmpty(imei)){
//            return mImei = imei;
//        }
        return TextUtils.isEmpty(mImei) ? "" : mImei;
    }
    private static String mIp;
    public static String getIp(){
        return IPUtil.getIpFromNetWork();
    }
}
