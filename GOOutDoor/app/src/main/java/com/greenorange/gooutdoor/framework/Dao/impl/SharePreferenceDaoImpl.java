package com.greenorange.gooutdoor.framework.Dao.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.greenorange.gooutdoor.framework.Dao.SharePreferenceDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public class SharePreferenceDaoImpl implements SharePreferenceDao {
    private Context mContext;
    public SharePreferenceDaoImpl(Context context){
        mContext = context;
    }
    @Override
    public Object getValue(String key, Object defaultValue) {
        if(defaultValue instanceof Integer){
            return getSharedPreferences().getInt(key,(Integer) defaultValue);
        }else if(defaultValue instanceof String){
            return getSharedPreferences().getString(key, defaultValue == null ? null : (String) defaultValue);
        }else if(defaultValue instanceof Float){
            return getSharedPreferences().getFloat(key, (Float) defaultValue);
        }else if(defaultValue instanceof Boolean){
            return getSharedPreferences().getBoolean(key, (Boolean)defaultValue);
        }
        return null;
    }
    @Override
    public boolean setValue(String key, Object value) {
        if(value instanceof Integer){
            return getSharedPreferences().edit().putInt(key, (Integer) value).commit();
        }else if(value instanceof String){
            return getSharedPreferences().edit().putString(key, (String) value).commit();
        }else if(value instanceof Float){
            return getSharedPreferences().edit().putFloat(key, (Float) value).commit();
        }else if(value instanceof Boolean){
            return getSharedPreferences().edit().putBoolean(key, (Boolean)value).commit();
        }
        return false;
    }

    @Override
    public boolean reStore() {
        getSharedPreferences().edit().apply();
        return true;
    }

    private synchronized SharedPreferences getSharedPreferences(){
        return mContext.getSharedPreferences(Dao.SharePerferenceDao, Context.MODE_PRIVATE);
    }
}
