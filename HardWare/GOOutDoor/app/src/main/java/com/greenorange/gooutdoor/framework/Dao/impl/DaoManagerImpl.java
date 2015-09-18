package com.greenorange.gooutdoor.framework.Dao.impl;

import android.content.Context;
import com.greenorange.gooutdoor.framework.Dao.DaoManager;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import java.util.HashMap;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public class DaoManagerImpl implements DaoManager{
    private HashMap<String,Object> mDaos = new HashMap<String, Object>();
    @Override
    public boolean init(Context context) {
        mDaos.put(Dao.ApplicationDao,     new ApplicationDaoImpl(context));
        mDaos.put(Dao.MapDao,    new MapDaoImpl(context));
        mDaos.put(Dao.SharePerferenceDao, new SharePreferenceDaoImpl(context));
        mDaos.put(Dao.FlashDao,  new FlashDaoImpl(context));
        mDaos.put(Dao.SportsDao, new SportsDaoImpl(context));
        mDaos.put(Dao.EventDao,  new EventDaoImpl(context));
        mDaos.put(Dao.SmileDao,  new SmileDaoImpl(context));
        return false;
    }
    @Override
    public Object getManager(String key) {
        return mDaos.get(key);
    }

    @Override
    public Object createManager(String key) {
        return null;
    }

}
