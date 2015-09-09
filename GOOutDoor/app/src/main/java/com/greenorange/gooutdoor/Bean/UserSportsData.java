package com.greenorange.gooutdoor.Bean;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.GOConfig;
import com.greenorange.gooutdoor.Service.MapUIBaidu;
import com.greenorange.gooutdoor.Service.MapUIControl;
import com.greenorange.gooutdoor.Service.MapUIGaode;
import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.entity.SportsDetailDBData;
import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.framework.DB.LocationDBHelper;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsTYPE;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.greenorange.gooutdoor.framework.Utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/4/19.
 */
public class UserSportsData {
    //here should be write to DB
    public long mSportStartTime     = System.currentTimeMillis();
    public int  mSportState         = SportsState.STATE_UNKNOW;
    public int  mLastSportState     = SportsState.STATE_UNKNOW;
    public int  mSportTYPE          = SportsTYPE.SPORT_TYPE_UNKNOW;
    public int  mLocationType       = MAPTYPE.MAPTYPE_UNKNOW;

    public UserDBData      userData;//for user info
    public SportsDBData    sportsData;//for current sports

    //only for UI show.
    public MapUIControl    mapUIControl;

    public UserSportsData(int maptype){
        mLocationType = maptype;
    }
    public void reSet(){
        mSportStartTime     = System.currentTimeMillis();
        mSportState         = SportsState.STATE_UNKNOW;
        mLastSportState     = SportsState.STATE_UNKNOW;
        mSportTYPE          = SportsTYPE.SPORT_TYPE_UNKNOW;
        userData            = null;
        sportsData          = null;
        //del detail item
        if(sportsDetailDBDataList != null) {
            sportsDetailDBDataList.clear();
        }
        sportsDetailDBDataList  = new ArrayList<SportsDetailDBData>();
        mTempSportsDetailDBData = null;
        /////////
        if(mLocationType == MAPTYPE.MAPTYPE_GAODE){
            mapUIControl    = new MapUIGaode();
        } else if(mLocationType == MAPTYPE.MAPTYPE_BAIDU){
            mapUIControl    = new MapUIBaidu();
        } else {
            throw new ApplicationException("mLocationType is unaviable !!!! ");
        }
    }
    public boolean updateLocationData(LocationDBData locationDBData, boolean valid){
        boolean result = (locationDBData!=null);
        if(result){
            //others data has set by Location. we only set user and sport data.
            int state = getCurrentSportsState();
            if(valid && SportUtil.isSportingState(state)) { //here we should save to DB
                locationDBData.setUserid(userData.getUserid());
                locationDBData.setSportid(sportsData.getSports_id());
                locationDBData.setSportstype(getCurrentSportsTYPE());
                locationDBData.setSportstate(getCurrentSportsState());
                LocationDBHelper.insertLocationDBData(GOApplication.getInstance(), locationDBData);
                //////// add data here
                if(state == SportsState.STATE_RECORD) {
                    recordData(locationDBData);
                }
            }
            mapUIControl.addLocationDB(locationDBData, state);
        }
        return result;
    }

    private void recordData(LocationDBData locationDBData){
        if(sportsData == null)return;
        long distance  = (long) locationDBData.getDistance();
        double calorie = SportUtil.getCalorie(distance, locationDBData.getSportstype());
        if(updateSportsDetailDBData(distance, calorie, locationDBData.getTime())){
            sportsData.setSports_total_distance( // add total distance
                    sportsData.getSports_total_distance() + distance
            );
            sportsData.setSports_totle_calorie( // add totle calorie
                    sportsData.getSports_totle_calorie() + calorie
            );
            //record detail.
            if(mTempSportsDetailDBData.getSports_distance() >= GOConfig.SPORTS_DETAIL_DISTANCE_FRQ){
                if(mTempSportsDetailDBData.getSports_time() > 0){//set speed for item.
                    mTempSportsDetailDBData.setSports_speed(
                        mTempSportsDetailDBData.getSports_distance()/mTempSportsDetailDBData.getSports_time()*1.0f
                    );
                }
                sportsDetailDBDataList.add(mTempSportsDetailDBData);
                mTempSportsDetailDBData = null;
            }
        }
        Log.d("UserSportsData recordData -->" + sportsData.toString());
    }

    public boolean checkUseAble(){
        return ((userData != null && userData.checkoutuserData())
                    && (sportsData != null && sportsData.checkSportsData())
                    && mSportStartTime >= 0);
    }

    public int getLastSportsState(){
        return mLastSportState;
    }

    public int getCurrentSportsState() {
        return mSportState;
    }

    public int getCurrentSportsTYPE() {
        return mSportTYPE;
    }
    public int getLocationTYPE(){
        return mLocationType;
    }
    public synchronized int setCurrentSportsState(int newSportsState){
        if(mSportState == newSportsState)return mSportState;
        mLastSportState = mSportState;
        mSportState     = newSportsState;
        if(mLastSportState == SportsState.STATE_RECORD
                && mSportState == SportsState.STATE_PAUSE){// add time.
            sportsData.setSports_total_time(sportsData.getSports_total_time() + (System.currentTimeMillis() - mSportStartTime));
        }
        mSportStartTime = System.currentTimeMillis();
        Util.notifySportsSTATEChange(mLastSportState, mSportState);
        Log.d("setCurrentSportsState -->mSportState="+SportUtil.getSportStateString(mSportState)
                + ", mLastSportState="+SportUtil.getSportStateString(mLastSportState));
        return mSportState;
    }
    public LocationDBData getCurrentLocationDBData(){
        return mapUIControl.mCurrentLocationData;
    }

    public long getSportTotalTime(){
        if(sportsData == null)return 0;
        int currentState = getCurrentSportsState();
        if(currentState == SportsState.STATE_RECORD) {
            return (System.currentTimeMillis() - mSportStartTime) + sportsData.getSports_total_time();
        }else if(currentState == SportsState.STATE_PAUSE){
            return sportsData.getSports_total_time();
        }
        return sportsData.getSports_total_time();
    }
    public long getSportTotleDistance(){
        if(sportsData == null)return 0;
        return sportsData.getSports_total_distance();
    }
    public double getSportTotleCalorie(){
        if(sportsData == null)return 0;
        return sportsData.getSports_totle_calorie();
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UserSportsData{")
                .append("mSportStartTime=" + mSportStartTime)
                .append(", mSportState=" + mSportState)
                .append(", mLastSportState=" + mLastSportState)
                .append(", mSportTYPE=" + mSportTYPE)
                .append(", mLocationType=" + mLocationType)
                .append(", userData=" + (userData == null ? "null" : userData.toString()))
                .append(", sportsData=" + (sportsData == null ? "null" : sportsData.toString()))
                .append(", mTempSportsDetailDBData=" + (mTempSportsDetailDBData != null))
                .append(", sportsDetailDBDataList size = " + (sportsDetailDBDataList == null ? "null" : sportsDetailDBDataList.size()))
                .append("}");
        return sb.toString();
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    // for SportsDetailDBData
    public  List<SportsDetailDBData> sportsDetailDBDataList;
    private SportsDetailDBData       mTempSportsDetailDBData;
    private boolean updateSportsDetailDBData(long distance, double calorie, long time){
        if(! SportUtil.isSportingState(getCurrentSportsState()))return false;//make sure its sports state.
        if(mTempSportsDetailDBData == null){
            if(sportsData == null)return false;
            mTempSportsDetailDBData = new SportsDetailDBData();
            mTempSportsDetailDBData.setSports_id(sportsData.getSports_id());
            mTempSportsDetailDBData.setSports_userid(sportsData.getSports_userid());
            mTempSportsDetailDBData.setSports_type(sportsData.getSports_type());
        }
        if(getCurrentSportsState() == SportsState.STATE_RECORD){
            mTempSportsDetailDBData.setSports_time(
                    mTempSportsDetailDBData.getSports_time() + getIntervalTime(time)
            );
            mTempSportsDetailDBData.setSports_distance(
                    mTempSportsDetailDBData.getSports_distance() + distance
            );
            mTempSportsDetailDBData.setSports_calorie(
                    mTempSportsDetailDBData.getSports_calorie() + calorie
            );
            return true;
        }
        return false;
    }
    private long getIntervalTime(long time){
        if(mapUIControl != null){
            LocationDBData last = getCurrentLocationDBData();
            if(last != null){
                long lastTime = last.getTime();
                return (lastTime > time ? 0 : (time - lastTime));
            }
        }
        return 0;
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    public synchronized List<SportsDetailDBData> createSportsDetailDBDataForSave(){
        List<SportsDetailDBData> list = new ArrayList<SportsDetailDBData>();
        if(sportsDetailDBDataList != null && sportsDetailDBDataList.size()>0){
            for(SportsDetailDBData sportsDetailDBData : sportsDetailDBDataList){
                list.add(new SportsDetailDBData(sportsDetailDBData));
            }
        }
        if(mTempSportsDetailDBData != null){
            SportsDetailDBData sportsDetailDBData = new SportsDetailDBData(mTempSportsDetailDBData);
            if(sportsDetailDBData.getSports_time() > 0){//set speed for item.
                sportsDetailDBData.setSports_speed(
                        sportsDetailDBData.getSports_distance()/sportsDetailDBData.getSports_time()*1.0f
                );
            }
            list.add(sportsDetailDBData);
        }
        return list;
    }
    public synchronized SportsDBData createSportsDBDataForSave(){
        SportsDBData sportsDBData = new SportsDBData(sportsData);
        sportsDBData.setSports_total_time(getSportTotalTime());//add totle time.
        return sportsDBData;
    }
    public synchronized UserDBData createUserDBDataForSave(){
        UserDBData userDBData = new UserDBData(userData);
        userDBData.setTotal_calorie(userDBData.getTotal_calorie() + getSportTotleCalorie());
        userDBData.setTotal_count(userDBData.getTotal_count() + 1);
        userDBData.setTotal_time(userDBData.getTotal_time() + getSportTotalTime());
        userDBData.setTotal_distance(userDBData.getTotal_distance() + getSportTotleDistance());
//        userDBData.setTotal_typecount("");//un use now
        return userDBData;
    }
}
