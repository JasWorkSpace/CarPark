package com.greenorange.gooutdoor.framework.Dao;

import android.content.Context;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public interface DaoManager {

    public boolean init(Context context);

    public Object  getManager(String key);

    public Object  createManager(String key);
}
