package com.greenorange.gooutdoor.entity;

/**
 * Created by JasWorkSpace on 15/4/28.
 */
public class SportsDetailDBData {

    private String sports_id;
    private String sports_userid;
    private int    sports_type;
    private long   sports_time;
    private float  sports_speed;
    private double sports_calorie;
    private long   sports_distance;


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

    public float getSports_speed() {
        return sports_speed;
    }

    public void setSports_speed(float sports_speed) {
        this.sports_speed = sports_speed;
    }

    public double getSports_calorie() {
        return sports_calorie;
    }

    public void setSports_calorie(double sports_calorie) {
        this.sports_calorie = sports_calorie;
    }

    public long getSports_distance() {
        return sports_distance;
    }

    public void setSports_distance(long sports_distance) {
        this.sports_distance = sports_distance;
    }

    public SportsDetailDBData(){
        this(null);
    }
    public SportsDetailDBData(SportsDetailDBData sportsDetailDBData){
        if(sportsDetailDBData != null){
            this.sports_id       = sportsDetailDBData.sports_id;
            this.sports_userid   = sportsDetailDBData.sports_userid;
            this.sports_type     = sportsDetailDBData.sports_type;
            this.sports_time     = sportsDetailDBData.sports_time;
            this.sports_speed    = sportsDetailDBData.sports_speed;
            this.sports_calorie  = sportsDetailDBData.sports_calorie;
            this.sports_distance = sportsDetailDBData.sports_distance;
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SportsDetailDBData{")
                .append("sports_id="+sports_id)
                .append(", sports_userid="+sports_userid)
                .append(", sports_type="+sports_type)
                .append(", sports_time="+sports_time)
                .append(", sports_speed="+sports_speed)
                .append(", sports_calorie="+sports_calorie)
                .append(", sports_distance="+sports_distance)
                .append("}");
        return sb.toString();
    }
}
