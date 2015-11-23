package com.greenorange.myuiaccount.service.V2;

import android.content.Context;
import android.text.TextUtils;
import android.util.LruCache;

import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.Util.SharePreferenceUtil;


/**
 * Created by JasWorkSpace on 15/10/21.
 */
public class ServiceCache {
    private static ServiceCache mInstance;
    public  static final long MAX_REQUEST_TIME = 30*60*1000;
    private Context mContext;
    public  static ServiceCache getInstance(Context context){
        if(mInstance == null){
            mInstance = new ServiceCache(context);
        }
        return mInstance;
    }
    private LruCache<String, String> mResponseCache  ;
    private LruCache<String, Long>   mResponseCaheTime ;

    public ServiceCache(Context context){
        mContext = context.getApplicationContext();
        int me = (int)Runtime.getRuntime().maxMemory() / 1024;
        mResponseCache = new LruCache<String, String>(me){
            @Override
            protected int sizeOf(String key, String value) {
                return value.length();
            }
        };
        mResponseCaheTime = new LruCache<String, Long>(me);
    }

    public synchronized String getCache(String key){
        if(TextUtils.isEmpty(key) || isCacheTimeout(key))return null;
        return getRealCache(key);
    }

    public synchronized boolean setCache(String key, String value){
        if(TextUtils.isEmpty(key))return false;
        Log.d("setCache-->" + key + ", "+value);
        if(SharePreferenceUtil.setMyUIValue(mContext, key, value)){
            mResponseCaheTime.put(key, System.currentTimeMillis());
            mResponseCache.put(key, value);
        }
        return true;
    }

    public synchronized String getRealCache(String key){
        if(TextUtils.isEmpty(key))return null;
        Log.d("getRealCache-->" + key);
        String value =  mResponseCache.get(key);
        if(TextUtils.isEmpty(value)){//load from file.
            Log.i("getRealCache new we load from preference ");
            value = SharePreferenceUtil.getMyUIValue(mContext, key, value);
        }
        return value;
    }

    public synchronized boolean isCacheTimeout(String key){
        if(TextUtils.isEmpty(key))return true;
        try {
            long lasttime = mResponseCaheTime.get(key);//maybe nullpoint.if no exsit.
            return(System.currentTimeMillis() - lasttime > MAX_REQUEST_TIME);
        }catch (Throwable e){e.printStackTrace(); return true;}
    }
}
