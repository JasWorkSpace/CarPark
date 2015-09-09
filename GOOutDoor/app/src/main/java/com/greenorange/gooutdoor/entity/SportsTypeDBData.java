package com.greenorange.gooutdoor.entity;

/**
 * Created by JasWorkSpace on 15/8/28.
 */
public class SportsTypeDBData {
    /* String SPORTS_ID             = "sports_id";
        String SPORTS_USERID         = "sports_userid";
        String SPORTS_TYPE           = "sports_type";
        String SPORTS_TOTAL_COUNT    = "sports_total_count";
        String SPORTS_TOTAL_TIME     = "sports_total_time";
        String SPORTS_TOTAL_DISTANCE = "sports_total_distance";
        String SPORTS_TOTAL_CALORIE  = "sports_totle_calorie"; */
    /*db.execSQL("CREATE TABLE if not exists " + ScheduleContract.Tables.SPORTSTYPE + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_ID + " TEXT NOT NULL,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_USERID + " TEXT NOT NULL,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TYPE + " INTEGER NOT NULL,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TOTAL_COUNT + " INTEGER,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TOTAL_TIME + " LONG,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TOTAL_DISTANCE + " LONG,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TOTAL_CALORIE + " DOUBLE,"
                + "create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))"
                + " )"
        );*/
    //private String sports_id;
    private String sports_userid;
    private int    sports_type;
    private int    sports_total_count;
    private long   sports_total_time;
    private long   sports_total_distance;
    private double sports_totle_calorie;

//    public String getSports_id() {
//        return sports_id;
//    }
//
//    public void setSports_id(String sports_id) {
//        this.sports_id = sports_id;
//    }

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

    public int getSports_total_count() {
        return sports_total_count;
    }

    public void setSports_total_count(int sports_total_count) {
        this.sports_total_count = sports_total_count;
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


    public SportsTypeDBData(){
        this(null);
    }
    public SportsTypeDBData(SportsTypeDBData sportsTypeDBData){
        if(sportsTypeDBData != null){
            this.sports_userid = sportsTypeDBData.sports_userid;
            this.sports_type   = sportsTypeDBData.sports_type;
            this.sports_total_count = sportsTypeDBData.sports_total_count;
            this.sports_total_time  = sportsTypeDBData.sports_total_time;
            this.sports_total_distance = sportsTypeDBData.sports_total_distance;
            this.sports_totle_calorie  = sportsTypeDBData.sports_totle_calorie;
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SportsTypeDBData{")
                .append("sports_userid="+sports_userid)
                .append(", sports_type="+sports_type)
                .append(", sports_total_count="+sports_total_count)
                .append(", sports_total_time="+sports_total_time)
                .append(", sports_total_distance="+sports_total_distance)
                .append(", sports_totle_calorie="+sports_totle_calorie)
                .append("}");
        return sb.toString();
    }
}

