package com.greenorange.gooutdoor.framework.Dao;


import com.greenorange.gooutdoor.framework.Model.DATA.LocationConfig;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationFilterConfig;

import java.util.ArrayList;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public interface MapDao {

    public int getDefaultMAPTYPE();

    public ArrayList<Integer> getSupportMAPTYPE();

    public boolean isSupportMAPTYPE(int maptype);

    public int getCurrentMAPTYPE();

    public String getMAPTYPEString(int maotype);

    public LocationConfig getMAPLocationConfig(int maptype);

    public LocationFilterConfig getMAPFliterConfig(int maptype, int sportstype);

    public LocationFilterConfig getDefaultFliterConfig();
    ////////////////////////////
    public final static int LOCATIONCONFIG_MAPTYPE   = 0;
    public final static int LOCATIONCONFIG_ENABLE = 1;
    public final static int LOCATIONCONFIG_FRQ    = 2;
    public final static int LOCATIONCONFIG_MINDISTANCE = 3;

    ////////////////////////////
    public final static int LOCATIONFILTER_MAPTYPE   = 0;
    public final static int LOCATIONFILTER_SPORTTYPE = 1;
    public final static int LOCATIONFILTER_MINDISTANCE = 2;
    public final static int LOCATIONFILTER_MAXDISTANCE = 3;
}
