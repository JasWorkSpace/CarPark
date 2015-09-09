package com.greenorange.gooutdoor.framework.Model.Interface;

/**
 * Created by JasWorkSpace on 15/4/22.
 */
public interface iSportsControl {

    public int getSportsTYPE();

    public boolean startNewSports(int sportsType);

    public boolean stopSports();

    public boolean finishSports();

    public boolean pauseSports();

    public boolean resumeSports();

    public int getSportsState();

    public int getLocationTYPE();

    public boolean initSports();

    public String getSportsID();

    public String getUserID();

    public boolean isNeedToSave();
}
