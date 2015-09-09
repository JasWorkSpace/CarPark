package com.greenorange.gooutdoor.framework.Dao;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public interface SharePreferenceDao {

    public Object getValue(String key, Object defaultValue);

    public boolean setValue(String key, Object value);

    public boolean reStore();
}
