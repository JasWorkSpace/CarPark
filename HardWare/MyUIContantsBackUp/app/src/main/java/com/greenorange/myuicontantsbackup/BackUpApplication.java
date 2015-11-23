package com.greenorange.myuicontantsbackup;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.greenorange.myuicontantsbackup.Bean.BackupOrRestoreDBBean;
import com.greenorange.myuicontantsbackup.Task.Task;
import com.greenorange.myuicontantsbackup.Task.TaskController;
import com.greenorange.myuicontantsbackup.Task.TaskManager;
import com.greenorange.myuicontantsbackup.db.BackupOrRestoreDB;
import com.greenorange.myuicontantsbackup.utils.PREFERENCE;
import com.greenorange.myuicontantsbackup.utils.Utils;

import java.util.Calendar;

/**
 * Created by JasWorkSpace on 15/10/30.
 */
public class BackUpApplication extends Application {
    private static BackUpApplication mInstance;
    public  static BackUpApplication getInstance(){
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        resetAutoBackupAlerm();
    }

    public synchronized void resetAutoBackupAlerm(){
        Intent intent = new Intent("com.greenorange.myuicontantsbackup.autotimeout");
        PendingIntent sender = PendingIntent.getBroadcast(BackUpApplication.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        manager.cancel(sender);
        long delay = getTimeoutTime();
        Log.d("resetAutoBackupAlerm delay="+delay);
        if(delay > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis() + delay);
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }else if(delay == 0){
            BackUpApplication.this.sendBroadcast(intent);
        }
    }

    public long getTimeoutTime(){
        if(PREFERENCE.getAutoBackupEnable(BackUpApplication.this)){
            String data = PREFERENCE.getAutoBackupFrq(BackUpApplication.this);
            BackupOrRestoreDBBean backupOrRestoreDBBean = BackupOrRestoreDB.getInstace(BackUpApplication.this).getLastBackupOrRestore(TaskManager.TASK_MASTER_TYPE_BACKUP_AUTO);
            Log.d("backupOrRestoreDBBean-->"+(backupOrRestoreDBBean==null?"":backupOrRestoreDBBean.toString()));
            long lastautobackuptime = 0;
            long needdelay = Integer.parseInt(data)*(24*60*60*1000);
            if(backupOrRestoreDBBean != null){
                lastautobackuptime = backupOrRestoreDBBean.getLasttime();
            }else{
                return needdelay;
            }
            long hasdelay  = (System.currentTimeMillis() - lastautobackuptime);
            if(needdelay > hasdelay){
                return needdelay - hasdelay;
            }
            return 0;
        }
        return -1;
    }

    public synchronized TaskController checkBackupTaskAndStart(){
        TaskManager taskManager = TaskManager.getInstance();
        if(!taskManager.isBackupTaskRunning()){
            return taskManager.startBackupTask(
                    PREFERENCE.getBackupCallLogEnable(getInstance()) ? Task.TASK_TYPE_BACKUP_CELLLOG : Task.TASK_TYPE_UNKNOW
                   ,PREFERENCE.getBackupContactsEnable(getInstance()) ? Task.TASK_TYPE_BACKUP_CONTACTS : Task.TASK_TYPE_UNKNOW
                   ,PREFERENCE.getBackupSMSEnable(getInstance()) ? Task.TASK_TYPE_BACKUP_SMS : Task.TASK_TYPE_UNKNOW
            );
        }
        return null;
    }
    public synchronized TaskController checkBackupAutoTaskAndStart(){
        Log.d("checkBackupAutoTaskAndStart");
        Context context = getInstance();
        if(PREFERENCE.getAutoBackupEnable(context) && Utils.isConnected(context)){
            if(!PREFERENCE.getAutoBackupWifiEnable(context)
                    && !Utils.isWifiConnected(context)){
                return null;
            }
            TaskManager taskManager = TaskManager.getInstance();
            if(!taskManager.isBackupTaskRunning()){
                long delay = getTimeoutTime();
                if(delay >=0 ) {
                    return taskManager.startBackupAutoTask(
                            PREFERENCE.getBackupCallLogEnable(getInstance()) ? Task.TASK_TYPE_BACKUP_CELLLOG : Task.TASK_TYPE_UNKNOW
                            , PREFERENCE.getBackupContactsEnable(getInstance()) ? Task.TASK_TYPE_BACKUP_CONTACTS : Task.TASK_TYPE_UNKNOW
                            , PREFERENCE.getBackupSMSEnable(getInstance()) ? Task.TASK_TYPE_BACKUP_SMS : Task.TASK_TYPE_UNKNOW
                    );
                }
            }
        }
        return null;
    }
    public synchronized TaskController checkRestoreTaskAndStart(){
        TaskManager taskManager = TaskManager.getInstance();
        if(!taskManager.isBackupTaskRunning()){
            return taskManager.startBackupTask(
                    PREFERENCE.getBackupCallLogEnable(getInstance()) ? Task.TASK_TYPE_RESTORE_CALLLOG : Task.TASK_TYPE_UNKNOW
                    ,PREFERENCE.getBackupContactsEnable(getInstance()) ? Task.TASK_TYPE_RESTORE_CONTACTS : Task.TASK_TYPE_UNKNOW
                    ,PREFERENCE.getBackupSMSEnable(getInstance()) ? Task.TASK_TYPE_RESTORE_SMS : Task.TASK_TYPE_UNKNOW
            );
        }
        return null;
    }

}
