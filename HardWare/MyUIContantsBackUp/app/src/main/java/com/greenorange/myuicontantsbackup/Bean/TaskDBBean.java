package com.greenorange.myuicontantsbackup.Bean;

/**
 * Created by JasWorkSpace on 15/11/13.
 */
public class TaskDBBean {

    private String uid     = "";
    private String bbsid   = "";
    private int    tasktype;
    private long   lasttime;
    private String md5     = "";

    public TaskDBBean(){
        this(null);
    }
    public TaskDBBean(TaskDBBean taskDBBean){
        if(taskDBBean != null){
            uid   = taskDBBean.uid;
            bbsid = taskDBBean.bbsid;
            tasktype = taskDBBean.tasktype;
            lasttime = taskDBBean.lasttime;
            md5      = taskDBBean.md5;
        }
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TaskDBBean={")
                .append("uid="+uid)
                .append(", bbsid="+bbsid)
                .append(", tasktype="+tasktype)
                .append(", lasttime="+lasttime)
                .append(", md5="+md5)
                .append("}");
        return sb.toString();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBbsid() {
        return bbsid;
    }

    public void setBbsid(String bbsid) {
        this.bbsid = bbsid;
    }

    public int getTasktype() {
        return tasktype;
    }

    public void setTasktype(int tasktype) {
        this.tasktype = tasktype;
    }

    public long getLasttime() {
        return lasttime;
    }

    public void setLasttime(long lasttime) {
        this.lasttime = lasttime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
