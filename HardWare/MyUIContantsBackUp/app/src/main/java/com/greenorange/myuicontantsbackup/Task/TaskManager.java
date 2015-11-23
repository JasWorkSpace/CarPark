package com.greenorange.myuicontantsbackup.Task;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by JasWorkSpace on 15/11/10.
 */
public class TaskManager {

    private static TaskManager mInstance;
    public  static TaskManager getInstance(){
        if(mInstance == null){
            mInstance = new TaskManager();
        }
        return mInstance;
    }
    ///////////////////////////////////
    public static final int TASK_MASTER_TYPE_UNKNOW      = 0;
    public static final int TASK_MASTER_TYPE_BACKUP      = 1;
    public static final int TASK_MASTER_TYPE_RESTORE     = 2;
    public static final int TASK_MASTER_TYPE_BACKUP_AUTO = 3;

    public static final int TASK_PRIMATY_TYPE_UNKNOW           = 0;
    public static final int TASK_PRIMATY_TYPE_BACKUP_CONTACTS  = 1;
    public static final int TASK_PRIMATY_TYPE_RESTORE_CONTACTS = 1 << 1;
    public static final int TASK_PRIMATY_TYPE_BACKUP_SMS       = 1 << 2;
    public static final int TASK_PRIMATY_TYPE_RESTORE_SMS      = 1 << 3;
    public static final int TASK_PRIMATY_TYPE_BACKUP_CALLLOG   = 1 << 4;
    public static final int TASK_PRIMATY_TYPE_RESTORE_CALLLOG  = 1 << 5;

    public static int tranformTaskTypeToTaskPrimatyType(int type){
        switch(type){
            case Task.TASK_TYPE_BACKUP_CELLLOG: return TASK_PRIMATY_TYPE_BACKUP_CALLLOG;
            case Task.TASK_TYPE_RESTORE_CALLLOG: return TASK_PRIMATY_TYPE_RESTORE_CALLLOG;
            case Task.TASK_TYPE_RESTORE_SMS: return TASK_PRIMATY_TYPE_RESTORE_SMS;
            case Task.TASK_TYPE_BACKUP_SMS:  return TASK_PRIMATY_TYPE_BACKUP_SMS;
            case Task.TASK_TYPE_RESTORE_CONTACTS: return TASK_PRIMATY_TYPE_RESTORE_CONTACTS;
            case Task.TASK_TYPE_BACKUP_CONTACTS: return TASK_PRIMATY_TYPE_BACKUP_CONTACTS;
        }
        return TASK_PRIMATY_TYPE_UNKNOW;
    }

    public static String getTaskPrimatyTypeLib(int type){
        String libs = "";
        if((type & TASK_PRIMATY_TYPE_BACKUP_CALLLOG) != 0){
            libs += (Task.getTaskLib(Task.TASK_TYPE_BACKUP_CELLLOG) + ",");
        }
        if((type & TASK_PRIMATY_TYPE_RESTORE_CALLLOG) != 0){
            libs += (Task.getTaskLib(Task.TASK_TYPE_RESTORE_CALLLOG) + ",");
        }
        if((type & TASK_PRIMATY_TYPE_RESTORE_SMS) != 0){
            libs += (Task.getTaskLib(Task.TASK_TYPE_RESTORE_SMS) + ",");
        }
        if((type & TASK_PRIMATY_TYPE_BACKUP_SMS) != 0){
            libs += (Task.getTaskLib(Task.TASK_TYPE_BACKUP_SMS) + ",");
        }
        if((type & TASK_PRIMATY_TYPE_RESTORE_CONTACTS) != 0){
            libs += (Task.getTaskLib(Task.TASK_TYPE_RESTORE_CONTACTS) + ",");
        }
        if((type & TASK_PRIMATY_TYPE_BACKUP_CONTACTS) != 0){
            libs += (Task.getTaskLib(Task.TASK_TYPE_BACKUP_CONTACTS) + ",");
        }
        return libs;
    }

    private TaskController mBackupTaskController;
    private TaskController mRestoreTaskController;
    /////////////////////////////////////////////////////////////////////////
    public boolean isBackupTaskRunning(){
        return !isNeedRestart(mBackupTaskController);
    }
    public boolean isRestoreTaskRunning(){
        return !isNeedRestart(mRestoreTaskController);
    }
    public void interruptBackupTask(){
        interruptTask(mBackupTaskController);
    }
    public void interruptRestoreTask(){
        interruptTask(mRestoreTaskController);
    }
    public synchronized TaskController startBackupTask(Context context, Integer... params){
        if(!isBackupTaskRunning()){
            mBackupTaskController = new TaskController(context, TASK_MASTER_TYPE_BACKUP);
            mBackupTaskController.execute(params);
            return mBackupTaskController;
        }
        return null;
    }
    public synchronized TaskController startBackupAutoTask(Context context, Integer... params){
        if(!isBackupTaskRunning()){
            mBackupTaskController = new TaskController(context, TASK_MASTER_TYPE_BACKUP_AUTO);
            mBackupTaskController.execute(params);
            return mBackupTaskController;
        }
        return null;
    }
    public synchronized TaskController startRestoreTask(Context context, Integer... params){
        if(!isRestoreTaskRunning()){
            mRestoreTaskController = new TaskController(context, TASK_MASTER_TYPE_RESTORE);
            mRestoreTaskController.execute(params);
            return mRestoreTaskController;
        }
        return null;
    }
    ////////////////////////////////////////////////////////////////////////
    private void interruptTask(TaskController taskController){
        if(taskController != null){
            taskController.stop();
        }
    }
    private boolean isNeedRestart(TaskController taskController){
        if(taskController == null || taskController.isCancelled()
                || (taskController.getStatus() == AsyncTask.Status.FINISHED))return true;
        return false;
    }

}
