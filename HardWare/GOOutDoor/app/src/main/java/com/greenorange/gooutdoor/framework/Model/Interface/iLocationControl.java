package com.greenorange.gooutdoor.framework.Model.Interface;

import com.greenorange.gooutdoor.framework.Model.DATA.LocationCallBack;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationFilterConfig;

/**
 * Created by JasWorkSpace on 15/4/19.
 */
public interface iLocationControl {

    public boolean openLocation();

    public boolean closeLocation();

    public int getLocationMapType();

    public boolean isOpen();

    public boolean setLocationCallBack(LocationCallBack callBack);

    public LocationFilterConfig getLocationFilterConfig(int sportsType);

    public LocationFilterConfig getdefaultFilterConfig();

}
