package com.greenorange.gooutdoor.entity;

import com.amap.api.location.AMapLocation;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsTYPE;

/**
 * Created by JasWorkSpace on 15/4/12.
 */
public class LocationDBData {

    private String  userid;
    private String  sportid;
    private int     maptype;
    private double  latitude;
    private double  longitude;
    private float   currentspeed;
    private String  address;
    private double  altitude;
    private String  provider;
    private long    time;
    private int     sportstate;
    private int     sportstype;
    private double  distance;

    public String getSportid() {
        return sportid;
    }

    public void setSportid(String sportid) {
        this.sportid = sportid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getCurrentspeed() {
        return currentspeed;
    }

    public void setCurrentspeed(float currentspeed) {
        this.currentspeed = currentspeed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSportstate() {
        return sportstate;
    }

    public void setSportstate(int sportstate) {
        this.sportstate = sportstate;
    }

    public int getMaptype() {
        return maptype;
    }

    public void setMaptype(int maptype) {
        this.maptype = maptype;
    }

    public int getSportstype() {
        return sportstype;
    }

    public void setSportstype(int sportstype) {
        this.sportstype = sportstype;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocationDBData(){
        updateGPSData(null);
    }
    public LocationDBData(LocationDBData data){
        updateGPSData(data);
    }
    public void updateGPSData(LocationDBData data){
        if(data != null){
            userid       = data.userid;
            sportid      = data.sportid;
            maptype      = data.maptype;
            latitude     = data.latitude;
            longitude    = data.longitude;
            currentspeed = data.currentspeed;
            address      = data.address;
            altitude     = data.altitude;
            provider     = data.provider;
            time         = data.time;
            sportstate   = data.sportstate;
            sportstype   = data.sportstype;
            distance     = data.distance;
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LocationData{")
                .append("userid="+userid)
                .append(", sportid="+sportid)
                .append(", maptype="+maptype)
                .append(", latitude=" + latitude)
                .append(", longitude="+longitude)
                .append(", currentspeed="+currentspeed)
                .append(", address="+address)
                .append(", altitude="+altitude)
                .append(", provider="+provider)
                .append(", time="+time)
                .append(", sportstate="+sportstate)
                .append(", sportstype="+sportstype)
                .append(", distance="+distance)
                .append("}");
        return sb.toString();
    }

    ///////////////////////
    public LocationDBData(AMapLocation aMapLocation){
        if(aMapLocation != null){
            setMaptype(MAPTYPE.MAPTYPE_GAODE);
            setLatitude(aMapLocation.getLatitude());
            setLongitude(aMapLocation.getLongitude());
            setCurrentspeed(aMapLocation.getSpeed() * 3.6f);
            setAltitude(aMapLocation.getAltitude());
            setProvider(aMapLocation.getProvider());
            setTime(aMapLocation.getTime());
        }
    }

    public boolean checkLocationData(){
        if(Integer.parseInt(userid) <= 0)return false;
        if(Integer.parseInt(sportid) <= 0)return false;
        if(maptype <= MAPTYPE.MAPTYPE_UNKNOW || maptype >= MAPTYPE.MAPTYPE_MAX)return false;
        if(sportstate <= SportsState.STATE_UNKNOW || sportstate >= SportsState.STATE_MAX)return false;
        if(sportstype <= SportsTYPE.SPORT_TYPE_UNKNOW || sportstype >= SportsTYPE.SPORT_TYPE_MAX)return false;
        return true;
    }
}
