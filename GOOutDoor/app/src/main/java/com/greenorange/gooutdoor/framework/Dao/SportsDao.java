package com.greenorange.gooutdoor.framework.Dao;

import com.greenorange.gooutdoor.Bean.UserSportsData;
import com.greenorange.gooutdoor.Service.LocationControl;
import com.greenorange.gooutdoor.Service.LocationService;
import com.greenorange.gooutdoor.Service.MapUIControl;
import com.greenorange.gooutdoor.Service.SportsControl;

/**
 * Created by JasWorkSpace on 15/4/18.
 */
public interface SportsDao {

    public LocationService getLocationService();

    public void bindLocationService();

    public void unBindLocationService();

    public SportsControl getSportsControl();

    public void startNewSports(int sportsType);

    public int  getCurrentSportsState();

    public int  getCurrentSportsType();

    public UserSportsData getCurrentUserSportsData();

    public MapUIControl   getMapUIControl();
    ///////////// location
    public LocationControl getLocationControl();

    public int getCurrentMapType();

    public boolean reStartLocation();

    public boolean closeLocation();
}
