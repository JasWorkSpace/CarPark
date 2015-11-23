package com.greenorange.myuicontantsbackup.Task;

import android.app.Activity;
import android.content.Context;

import com.greenorange.myuicontantsbackup.Account.UserInfo;
import com.greenorange.myuicontantsbackup.BackUpApplication;
import com.greenorange.myuicontantsbackup.R;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by JasWorkSpace on 15/10/29.
 */
public abstract class Task implements Runnable{
    /////// Task state
    public final static int TASK_STATE_IDLE      = 0;
    public final static int TASK_STATE_PENDING   = 1;
    public final static int TASK_STATE_INTERRUPT = 2;
    public final static int TASK_STATE_WORK      = 3;
    public final static int TASK_STATE_SUCCESS   = 4;
    public final static int TASK_STATE_FAIL      = 5;
    public final static int TASK_STATE_STOP      = 6;
    private AtomicInteger TASK_CURRENT_STATE   = new AtomicInteger(TASK_STATE_IDLE);
    public int getTaskState(){
        return TASK_CURRENT_STATE.get();
    }
    public synchronized int setTaskState(int state){
        if(state == getTaskState())return getTaskState();
        TASK_CURRENT_STATE.set(state);
        onTaskStateChange();
        return getTaskState();
    }

    public abstract void onTaskStateChange();
    /////// task type
    public final static int TASK_TYPE_UNKNOW            = 0;
    public final static int TASK_TYPE_BACKUP_CONTACTS   = 1;
    public final static int TASK_TYPE_BACKUP_SMS        = 2;
    public final static int TASK_TYPE_BACKUP_MMS        = 3;
    public final static int TASK_TYPE_BACKUP_CELLLOG    = 4;
    public final static int TASK_TYPE_RESTORE_CONTACTS  = 5;
    public final static int TASK_TYPE_RESTORE_SMS       = 6;
    public final static int TASK_TYPE_RESTORE_MMS       = 7;
    public final static int TASK_TYPE_RESTORE_CALLLOG = 8;
    public final static int TASK_TYPE_MAX               = 9;

    public final static int TASK_TYPE_AUTOBACKUP        = 10;

    public abstract int getTaskType();

    public boolean isSupportTaskType(){
        return isSupportTaskType(getTaskType());
    }

    public final static boolean isSupportTaskType(int type){
        return TASK_TYPE_MAX > type && type > TASK_TYPE_UNKNOW;
    }
    public final static int getTaskLib(int type){
        switch (type){
            case TASK_TYPE_BACKUP_CONTACTS: return R.string.lib_backup_contacts;
            case TASK_TYPE_BACKUP_SMS:      return R.string.lib_backup_sms;
            case TASK_TYPE_BACKUP_MMS:      return R.string.lib_backup_mms;
            case TASK_TYPE_BACKUP_CELLLOG:  return R.string.lib_backup_celllog;
            case TASK_TYPE_RESTORE_CONTACTS:return R.string.lib_restore_contacts;
            case TASK_TYPE_RESTORE_SMS:     return R.string.lib_restore_sms;
            case TASK_TYPE_RESTORE_MMS:     return R.string.lib_restore_mms;
            case TASK_TYPE_RESTORE_CALLLOG: return R.string.lib_restore_celllog;
            default: return R.string.lib_unknow;
        }
    }
    public final static boolean isBackupType(int type){
        return TASK_TYPE_BACKUP_CONTACTS <= type && type <= TASK_TYPE_BACKUP_CELLLOG;
    }
    public final static boolean isRestoreType(int type){
        return TASK_TYPE_RESTORE_CONTACTS <= type && type <= TASK_TYPE_RESTORE_CALLLOG;
    }
    /////////////////////////////////
    private UserInfo mUserInfo;
    private Context  mContext;
    public  Task(Context context, UserInfo userInfo){
        mContext = context;
        mUserInfo = new UserInfo(userInfo);
    }
    public  UserInfo getUserInfo(){return mUserInfo;}
    ////////////////////////////////////////////////////////////////////////////////
    public  Context getContext(){
        return getContext(true);
    }
    public Context getContext(boolean must){
        if(mContext == null && must){
            return getApplicationContext();
        }
        return mContext;
    }
    public Context getApplicationContext(){
        return BackUpApplication.getInstance();
    }
}
