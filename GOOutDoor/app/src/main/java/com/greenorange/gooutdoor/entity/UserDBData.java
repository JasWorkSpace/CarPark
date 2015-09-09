package com.greenorange.gooutdoor.entity;

/**
 * Created by JasWorkSpace on 15/4/12.
 */
public class UserDBData {

    private String userid ;
    private String username;
    private String userpassword;
    private long   lastlogintime;
    private int    total_count;
    private String total_typecount;
    private double total_distance;
    private long   total_time;
    private double total_calorie;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public long getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(long lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public String getTotal_typecount() {
        return total_typecount;
    }

    public void setTotal_typecount(String total_typecount) {
        this.total_typecount = total_typecount;
    }

    public double getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(double total_distance) {
        this.total_distance = total_distance;
    }

    public long getTotal_time() {
        return total_time;
    }

    public void setTotal_time(long total_time) {
        this.total_time = total_time;
    }

    public double getTotal_calorie() {
        return total_calorie;
    }

    public void setTotal_calorie(double total_calorie) {
        this.total_calorie = total_calorie;
    }

    public UserDBData(){
        this(null);
    }
    public UserDBData(UserDBData userDBData){
        if(userDBData != null){
            userid          = userDBData.userid;
            username        = userDBData.username;
            userpassword    = userDBData.userpassword;
            lastlogintime   = userDBData.lastlogintime;
            total_count     = userDBData.total_count;
            total_typecount = userDBData.total_typecount;
            total_distance  = userDBData.total_distance;
            total_time      = userDBData.total_time;
            total_calorie   = userDBData.total_calorie;
        }
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UserData{")
                .append("userid="+userid)
                .append(" ,username="+username)
                .append(" ,userpassword="+userpassword)
                .append(" ,lastlogintime="+lastlogintime)
                .append(" ,total_count="+total_count)
                .append(" ,total_typecount="+total_typecount)
                .append(" ,total_distance="+total_distance)
                .append(" ,total_time="+total_time)
                .append(" ,total_calorie="+total_calorie)
                .append("}");
        return sb.toString();
    }

    public boolean checkoutuserData(){
        if(Integer.parseInt(userid) <= 0)return false;
        if(lastlogintime <0 || total_count <0 || total_distance <0 || total_time <0 || total_calorie <0)return false;
        return true;
    }
}
