package com.greenorange.myuicontantsbackup.Bean;

import com.greenorange.myuicontantsbackup.Task.TaskManager;

/**
 * Created by JasWorkSpace on 15/11/18.
 */
public class BackupOrRestoreDBBean {

    private int tasktype ;
    private long lasttime ;
    private int runtasktype;
    public BackupOrRestoreDBBean(){
        this(null);
    }
    public BackupOrRestoreDBBean(BackupOrRestoreDBBean backupOrRestoreDBBean){
        if(backupOrRestoreDBBean != null){
            tasktype    = backupOrRestoreDBBean.tasktype;
            lasttime    = backupOrRestoreDBBean.lasttime;
            runtasktype = backupOrRestoreDBBean.runtasktype;
        }
    }
    public void addRuntasktype(int type){
        runtasktype |= TaskManager.tranformTaskTypeToTaskPrimatyType(type);
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BackupOrRestoreDBBean{")
                .append("tasktype="+tasktype)
                .append(", lasttime="+lasttime)
                .append(", runtasktype="+runtasktype)
                .append(", runtasktype="+TaskManager.getTaskPrimatyTypeLib(runtasktype))
                .append("}");
        return sb.toString();
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

    public int getRuntasktype() {
        return runtasktype;
    }

    public void setRuntasktype(int runtasktype) {
        this.runtasktype = runtasktype;
    }
}
