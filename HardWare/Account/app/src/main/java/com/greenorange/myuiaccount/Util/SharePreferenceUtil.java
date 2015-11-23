package com.greenorange.myuiaccount.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.greenorange.myuiaccount.Config;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.service.V2.SecureUtil;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;

/**
 * Created by JasWorkSpace on 15/10/20.
 */
public class SharePreferenceUtil {

    public static Object getValue(Context context, String key, Object defaultValue){
        try {
            Log.d("getValue "+key+" -->"+defaultValue);
            return getValueWitchException(context, key, defaultValue);
        }catch (Throwable e){e.printStackTrace();Log.d("getValue fail -->"+e.toString());}
        return defaultValue;
    }
    public static Object getValueWitchException(Context context, String key, Object defaultValue) throws Exception {
        if(TextUtils.isEmpty(key) || defaultValue == null)throw new Exception("invalid param");
        String filekey = SecureUtil.md5(key);
        if(defaultValue instanceof String){
            return getSharedPreferences(context).getString(filekey, (String)defaultValue);
        }else if(defaultValue instanceof Integer){
            return getSharedPreferences(context).getInt(filekey, (Integer)defaultValue);
        }else if(defaultValue instanceof Long){
            return getSharedPreferences(context).getLong(filekey, (Long)defaultValue);
        }
        return defaultValue;
    }
    public static boolean setValue(Context context, String key, Object value){
        try{
            Log.d("setValue "+key+" -->"+value);
            return setValueWitchException(context, key, value);
        }catch (Throwable e){e.printStackTrace();Log.d("setValue fail "+e.toString());}
        return false;
    }
    public static boolean setValueWitchException(Context context, String key, Object value) throws Exception {
        if(TextUtils.isEmpty(key) || value == null)throw new Exception("invalid param");
        String filekey = SecureUtil.md5(key);
        if(value instanceof String){
            return getSharedPreferences(context).edit().putString(filekey, (String) value).commit();
        }else if(value instanceof Integer){
            return getSharedPreferences(context).edit().putInt(filekey, (Integer) value).commit();
        }else if(value instanceof Long){
            return getSharedPreferences(context).edit().putLong(filekey, (Long) value).commit();
        }
        return false;
    }
    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(Config.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }
    ////////////////////////////////////////////////////////
    public static final String KEY_TICKET   = "KEY_TICKET";
    public static final String KEY_USERINFO = "KEY_USERINFO";

    public static boolean setMyUIValue(Context context, String key, String value){
        try{
            Log.d("setMyUIValue "+key+", "+value);
            String savevalue = value;
            if(!TextUtils.isEmpty(value)){
                savevalue = ServiceHelper.getEncodeParam(ServiceHelper.getEncrypt(savevalue));
            }
            setValue(context, key, savevalue);
        }catch (Throwable e){e.printStackTrace();
            Log.d("setMyUIValue fail "+e.toString());
        }
        return false;
    }
    public static String getMyUIValue(Context context, String key, String defaultvalue){
        try{
            Log.d("getMyUIValue  "+key+", "+defaultvalue);
            String value = (String)getValue(context, key, defaultvalue == null ? "" : defaultvalue);
            if(!TextUtils.isEmpty(value)){
                Log.d("getMyUIValue-->"+value);
                return ServiceHelper.getDecrypt(ServiceHelper.getDecodeParam(value));
            }
            return null;
        }catch (Throwable e){e.printStackTrace();
            Log.d("getMyUIValue fail "+e.toString());
        }
        return defaultvalue;
    }
}
