package com.greenorange.gooutdoor.entity;

import com.greenorange.gooutdoor.framework.Utils.SportUtil;

/**
 * Created by JasWorkSpace on 15/4/17.
 */
public class SportsDBData {

    private String sports_id;
    private String sports_userid;
    private int    sports_type;
    private long   sports_time;
    private long   sports_total_time;
    private long   sports_total_distance;
    private double sports_totle_calorie;
    private int    sports_record_state;
    //for special data
    private int    sports_time_year;
    private int    sports_time_month;
    private int    sports_time_dayofmonth;
    private int    sports_time_dayofweek;
    private int    sports_time_weekofyear;
    private int    sports_time_weekofmonth;
    private int    sports_time_hourofday;
    private int    sports_time_minute;
    public String getSports_id() {
        return sports_id;
    }

    public void setSports_id(String sports_id) {
        this.sports_id = sports_id;
    }

    public String getSports_userid() {
        return sports_userid;
    }

    public void setSports_userid(String sports_userid) {
        this.sports_userid = sports_userid;
    }

    public int getSports_type() {
        return sports_type;
    }

    public void setSports_type(int sports_type) {
        this.sports_type = sports_type;
    }

    public long getSports_time() {
        return sports_time;
    }

    public void setSports_time(long sports_time) {
        this.sports_time = sports_time;
    }

    public long getSports_total_time() {
        return sports_total_time;
    }

    public void setSports_total_time(long sports_total_time) {
        this.sports_total_time = sports_total_time;
    }

    public long getSports_total_distance() {
        return sports_total_distance;
    }

    public void setSports_total_distance(long sports_total_distance) {
        this.sports_total_distance = sports_total_distance;
    }

    public double getSports_totle_calorie() {
        return sports_totle_calorie;
    }

    public void setSports_totle_calorie(double sports_totle_calorie) {
        this.sports_totle_calorie = sports_totle_calorie;
    }

    public int getSports_record_state() {
        return sports_record_state;
    }

    public void setSports_record_state(int sports_record_state) {
        this.sports_record_state = sports_record_state;
    }

    public int getSports_time_year() {
        return sports_time_year;
    }

    public void setSports_time_year(int sports_time_year) {
        this.sports_time_year = sports_time_year;
    }

    public int getSports_time_month() {
        return sports_time_month;
    }

    public void setSports_time_month(int sports_time_month) {
        this.sports_time_month = sports_time_month;
    }

    public int getSports_time_dayofmonth() {
        return sports_time_dayofmonth;
    }

    public void setSports_time_dayofmonth(int sports_time_dayofmonth) {
        this.sports_time_dayofmonth = sports_time_dayofmonth;
    }

    public int getSports_time_dayofweek() {
        return sports_time_dayofweek;
    }

    public void setSports_time_dayofweek(int sports_time_dayofweek) {
        this.sports_time_dayofweek = sports_time_dayofweek;
    }

    public int getSports_time_weekofyear() {
        return sports_time_weekofyear;
    }

    public void setSports_time_weekofyear(int sports_time_weekofyear) {
        this.sports_time_weekofyear = sports_time_weekofyear;
    }

    public int getSports_time_weekofmonth() {
        return sports_time_weekofmonth;
    }

    public void setSports_time_weekofmonth(int sports_time_weekofmonth) {
        this.sports_time_weekofmonth = sports_time_weekofmonth;
    }

    public int getSports_time_hourofday() {
        return sports_time_hourofday;
    }

    public void setSports_time_hourofday(int sports_time_hourofday) {
        this.sports_time_hourofday = sports_time_hourofday;
    }

    public int getSports_time_minute() {
        return sports_time_minute;
    }

    public void setSports_time_minute(int sports_time_minute) {
        this.sports_time_minute = sports_time_minute;
    }

    public SportsDBData(){
        this(null);
    }
    public SportsDBData(SportsDBData sportsDBData){
        if(sportsDBData != null){
            sports_id               = sportsDBData.sports_id;
            sports_userid           = sportsDBData.sports_userid;
            sports_type             = sportsDBData.sports_type;
            sports_time             = sportsDBData.sports_time;
            sports_total_time       = sportsDBData.sports_total_time;
            sports_total_distance   = sportsDBData.sports_total_distance;
            sports_totle_calorie    = sportsDBData.sports_totle_calorie;
            sports_record_state     = sportsDBData.sports_record_state;
            sports_time_year        = sportsDBData.sports_time_year;
            sports_time_month       = sportsDBData.sports_time_month;
            sports_time_dayofmonth  = sportsDBData.sports_time_dayofmonth;
            sports_time_dayofweek   = sportsDBData.sports_time_dayofweek;
            sports_time_weekofyear  = sportsDBData.sports_time_weekofyear;
            sports_time_weekofmonth = sportsDBData.sports_time_weekofmonth;
            sports_time_hourofday   = sportsDBData.sports_time_hourofday;
            sports_time_minute      = sportsDBData.sports_time_minute;
        }
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SportsData{")
                .append("sports_id="+sports_id)
                .append(", sports_userid="+sports_userid)
                .append(", sports_type="+sports_type)
                .append(", sports_time＝"+sports_time)
                .append(", sports_total_time="+sports_total_time)
                .append(", sports_total_distance="+sports_total_distance)
                .append(", sports_totle_calorie="+sports_totle_calorie)
                .append(", sports_record_state="+sports_record_state)
                .append(", sports_time_year="+sports_time_year)
                .append(", sports_time_month="+sports_time_month)
                .append(", sports_time_dayofmonth="+sports_time_dayofmonth)
                .append(", sports_time_dayofweek="+sports_time_dayofweek)
                .append(", sports_time_weekofyear="+sports_time_weekofyear)
                .append(", sports_time_weekofmonth="+sports_time_weekofmonth)
                .append(", sports_time_hourofday="+sports_time_hourofday)
                .append(", sports_time_minute="+sports_time_minute)
                .append("}");
        return sb.toString();
    }

    public boolean checkSportsData(){
        if(Integer.parseInt(sports_userid) <= 0)return false;
        if(Long.parseLong(sports_id) <= 0)return false;
        if(!SportUtil.isSportsType(sports_type))return false;
        if(sports_time <0 || sports_total_time <0 || sports_total_distance <0 || sports_totle_calorie <0 )return false;
        return true;
    }
    public boolean checkspecialtime(){
        if(sports_time_year<0 || sports_time_month<0 || sports_time_dayofmonth<0 || sports_time_dayofweek<0
                || sports_time_weekofyear<0 || sports_time_weekofmonth<0 || sports_time_hourofday<0 || sports_time_minute<0)
            return false;
        return true;
    }
    public boolean isNeedSave(){
        if(checkSportsData() && sports_total_distance > 100){
            return true;
        }
        return false;
    }
}
