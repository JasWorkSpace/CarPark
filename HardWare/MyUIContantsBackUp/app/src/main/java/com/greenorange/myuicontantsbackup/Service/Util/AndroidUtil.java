package com.greenorange.myuicontantsbackup.Service.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by JasWorkSpace on 15/10/27.
 */
public class AndroidUtil {

    private static String mClientVersion;
    private static int    mClientVersionCode = -1;
    public  static int getClientVersionCode(Context context){
        if(mClientVersionCode <= 0){
            try {
                PackageInfo PkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return mClientVersionCode = PkgInfo.versionCode;
            } catch (Exception e) {
                e.printStackTrace();
                mClientVersionCode = 2;//set default.
            }
        }
        return mClientVersionCode;
    }
    public  static String getClientVersionName(Context context) {
        if(TextUtils.isEmpty(mClientVersion)){
            try {
                PackageInfo PkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return mClientVersion = PkgInfo.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "unknow";
    }
    private static String mImei;
    public  static String getIMEI(Context context){
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
