package com.greenorange.gooutdoor.Service;

import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.Bean.UserSportsData;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Dao.ApplicationDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationFilterConfig;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.gooutdoor.framework.Model.Interface.iSportsControl;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.greenorange.gooutdoor.framework.Utils.Util;

/**
 * Created by JasWorkSpace on 15/4/22.
 */
public class SportsControl implements iSportsControl {
    private UserSportsData  mUserSportsData;
    public  UserSportsData  getUserSportsData() {
        return mUserSportsData;
    }
    private LocationService mLocationService;
    public SportsControl(LocationService locationService, int mapType){
        mLocationService = locationService;
        mUserSportsData  = new UserSportsData(mapType);
        initSports();
    }
    @Override
    public int getSportsTYPE() {
        return mUserSportsData.getCurrentSportsTYPE();
    }
    @Override
    public String getUserID(){
        if(mUserSportsData != null && mUserSportsData.userData != null){
            return mUserSportsData.userData.getUserid();
        }
        return "-1";
    }
    @Override
    public String getSportsID(){
        if(mUserSportsData != null && mUserSportsData.sportsData != null){
            return mUserSportsData.sportsData.getSports_id();
        }
        return null;
    }
    @Override
    public boolean isNeedToSave(){
        if(mUserSportsData != null && mUserSportsData.sportsData != null){
            return mUserSportsData.sportsData.isNeedSave();
        }
        return false;
    }
    @Override
    public boolean startNewSports(int sportsType) {
        if(mUserSportsData.getCurrentSportsState() != SportsState.STATE_FINISH
                && mUserSportsData.getCurrentSportsState() != SportsState.STATE_UNKNOW)
            throw new ApplicationException("startNewSports fail for error state. mCurrentSportsState is "+mUserSportsData.getCurrentSportsState());
        mUserSportsData.reSet();//reset sports data.
        ApplicationDao applicationDao = (ApplicationDao) GOApplication.getDaoManager().getManager(Dao.ApplicationDao);
        mUserSportsData.userData      = new UserDBData(applicationDao.getUser());
        SportsDBData newSportsDBData  = applicationDao.getNewSports(mLocationService, sportsType);
        mUserSportsData.sportsData    = newSportsDBData;
        mUserSportsData.mSportTYPE    = sportsType;//back up
        if(!mUserSportsData.checkUseAble())
            throw new ApplicationException("unuse UserSportsData for startnewSports("+sportsType+") pls check it. mUserSportsData=" + mUserSportsData.toString());
        setCurrentSportsState(SportsState.STATE_RECORD);
        Util.notifySportsTypeChange(GOApplication.getInstance(), sportsType);
        return true;
    }
    @Override
    public boolean stopSports() {
        if(mUserSportsData.getCurrentSportsState() != SportsState.STATE_PAUSE
                && mUserSportsData.getCurrentSportsState() != SportsState.STATE_RECORD)
            throw new ApplicationException("stopSports fail for error state. mCurrentSportsState is "+mUserSportsData.getCurrentSportsState());
        setCurrentSportsState(SportsState.STATE_STOP);
        return true;
    }
    @Override
    public boolean finishSports() {
        if(mUserSportsData.getCurrentSportsState() != SportsState.STATE_STOP)
            throw new ApplicationException("stopSports fail for error state. mCurrentSportsState is "+mUserSportsData.getCurrentSportsState());
        mUserSportsData.reSet();//reset sports data.
        setCurrentSportsState(SportsState.STATE_FINISH);
        return true;
    }
    @Override
    public boolean pauseSports() {
        if(mUserSportsData.getCurrentSportsState() != SportsState.STATE_PAUSE
                && mUserSportsData.getCurrentSportsState() != SportsState.STATE_RECORD)
            throw new ApplicationException("pauseSports fail for error state. mCurrentSportsState is "+ mUserSportsData.getCurrentSportsState());
        setCurrentSportsState(SportsState.STATE_PAUSE);
        return true;
    }
    @Override
    public boolean resumeSports() {
        if(mUserSportsData.getCurrentSportsState() != SportsState.STATE_PAUSE
                && mUserSportsData.getCurrentSportsState() != SportsState.STATE_RECORD)
            throw new ApplicationException("pauseSports fail for error state. mCurrentSportsState is "+ mUserSportsData.getCurrentSportsState());
        setCurrentSportsState(SportsState.STATE_RECORD);
        return true;
    }
    @Override
    public int getSportsState() {
        return mUserSportsData.getCurrentSportsState();
    }

    @Override
    public int getLocationTYPE() {
        return mUserSportsData.getLocationTYPE();
    }

    @Override
    public boolean initSports() {
        mUserSportsData.reSet();
        return false;
    }

    private void setCurrentSportsState(int newState){
        mUserSportsData.setCurrentSportsState(newState);
    }
    /////////////////////////////////////////
    //
    public boolean updateCurrentLocationData(LocationDBData locationDBData, LocationFilterConfig locationFilterConfig){
        LocationDBData current = mUserSportsData.getCurrentLocationDBData();
        boolean valid   = false;
        double distance = 0;
        if(current != null){//for its the first locationData
            if(locationFilterConfig.maptype == MAPTYPE.MAPTYPE_GAODE) {
                locationDBData.setDistance(distance = SportUtil.getGaodedistance(current, locationDBData));
            } else {
                throw new ApplicationException("updateCurrentLocationData fail !!!!! locationFilterConfig=" + locationFilterConfig.toString());
            }
            if(distance >= locationFilterConfig.mindistance && distance <= locationFilterConfig.maxdistance){
                valid = true;
            }
        }
        Log.d("SportsControl updateCurrentLocationData -->distance="+ distance +", valid="+valid+", locationDBData="+locationDBData.toString());
        return mUserSportsData.updateLocationData(locationDBData, valid);
    }

}
