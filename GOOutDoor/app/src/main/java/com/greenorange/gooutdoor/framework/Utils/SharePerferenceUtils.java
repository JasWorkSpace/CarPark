package com.greenorange.gooutdoor.framework.Utils;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.SharePreferenceDao;

/**
 * Created by JasWorkSpace on 15/7/28.
 */
public class SharePerferenceUtils {

    public static boolean setValue(String key, Object value){
        return getSharePreferenceDao().setValue(key, value);
    }
    public static Object getValue(String key, Object defaultValue){
        return getSharePreferenceDao().getValue(key, defaultValue);
    }
    public static SharePreferenceDao getSharePreferenceDao(){
        return (SharePreferenceDao) GOApplication.getDaoManager().getManager(Dao.SharePerferenceDao);
    }
}
