package com.greenorange.myuicontantsbackup.Task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.greenorange.myuicontantsbackup.Account.MyUIAccount;
import com.greenorange.myuicontantsbackup.Account.UserInfo;
import com.greenorange.myuicontantsbackup.BackUpApplication;
import com.greenorange.myuicontantsbackup.Bean.BackupOrRestoreDBBean;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.db.BackupOrRestoreDB;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by JasWorkSpace on 15/11/10.
 */
public class TaskController  extends AsyncTask<Integer,Void,Void>{

    private int mMasterTaskType = TaskManager.TASK_MASTER_TYPE_UNKNOW;
    private Context mContext;
    public TaskController(Context context, int masterTaskType){
        mContext = context;
        mMasterTaskType = masterTaskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mTasks.clear();
    }
    private Task mRunningTask  = null;
    private ArrayList<Task> mTasks = new ArrayList<Task>();
    @Override
    protected Void doInBackground(Integer... params) {
        Log.d("TaskController doInBackground");
        if(params != null && params.length>0 && !isCancelled()){
            UserInfo userInfo = getUserInfoIfNeed();
            if(userInfo == null)return null;
            mTasks.clear();
            /////////////////////////////////////////////
            BackupOrRestoreDBBean backupOrRestoreDBBean = new BackupOrRestoreDBBean();
            backupOrRestoreDBBean.setTasktype(mMasterTaskType);
            backupOrRestoreDBBean.setLasttime(System.currentTimeMillis());
            for(int index = 0; index < params.length; ++index){
                Task task = getTask(params[index], userInfo);
                backupOrRestoreDBBean.addRuntasktype(params[index]);
                if(task != null)mTasks.add(task);
            }
            if(isCancelled())return null;
            //record it.
            BackupOrRestoreDB.getInstace(BackUpApplication.getInstance()).addBackupOrRestore(backupOrRestoreDBBean);
            if(mMasterTaskType == TaskManager.TASK_MASTER_TYPE_BACKUP
                    || mMasterTaskType == TaskManager.TASK_MASTER_TYPE_BACKUP_AUTO){
                BackUpApplication.getInstance().resetAutoBackupAlerm();//reset alerm.
            }
            ////////////////////////////////////////////
            notifyTaskControllerStepChange();
            for(Iterator<Task> iterator = mTasks.iterator(); iterator.hasNext();){
                if(!isCancelled()){
                    Task task = iterator.next();
                    if(task.getTaskState() == Task.TASK_STATE_IDLE){
                        mRunningTask = task;
                        mRunningTask.setTaskState(Task.TASK_STATE_PENDING);
                        mRunningTask.run();
                    }
                }
            }
        }
        return null;
    }

    private UserInfo getUserInfoIfNeed(){
        MyUIAccount myUIAccount = MyUIAccount.getInstance(BackUpApplication.getInstance());
        UserInfo userInfo = myUIAccount.getMyUIUserInfo();
        if(userInfo == null){
            myUIAccount.logainMyUIAccount();
        }
        return myUIAccount.getMyUIUserInfo();
    }
    //////////////// if Task no support. here should return null.
    //eg. TASK_TYPE_BACKUP_CELLLOG is not support . when tasktype = Task.TASK_TYPE_BACKUP_CELLLOG
    // then return null here.
    private Task getTask(int tasktype, UserInfo userInfo){
        switch(tasktype){
            case Task.TASK_TYPE_BACKUP_CELLLOG:
                return new BackupCallLogTask(mContext, userInfo, BaseTask.TASK_BASETYPE_BACKUP);
            case Task.TASK_TYPE_RESTORE_CALLLOG:
                return new BackupCallLogTask(mContext, userInfo, BaseTask.TASK_BASETYPE_RESTORE);
            case Task.TASK_TYPE_BACKUP_CONTACTS:
                return new BackupContactsTask(mContext, userInfo, BaseTask.TASK_BASETYPE_BACKUP);
            case Task.TASK_TYPE_RESTORE_CONTACTS:
                return new BackupContactsTask(mContext, userInfo, BaseTask.TASK_BASETYPE_RESTORE);
            case Task.TASK_TYPE_BACKUP_SMS:
                return new BackupSmsTask(mContext, userInfo, BaseTask.TASK_BASETYPE_BACKUP);
            case Task.TASK_TYPE_RESTORE_SMS:
                return new BackupSmsTask(mContext, userInfo, BaseTask.TASK_BASETYPE_RESTORE);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("TaskController onPostExecute");
        notifyTaskControllerStepChange();
        notifyTaskPostExecute();
        BackUpApplication.getInstance().sendBroadcast(new Intent("com.greenorange.myuicontantsbackup.taskchange"));
    }
    /////////////////////////////////////////////
    public interface onTaskControllerStepChangeListener{
        public void onTaskControllerStepChange();
        public void onTaskPostExecute();
    }
    private synchronized void notifyTaskControllerStepChange(){
        if(mListener != null)mListener.onTaskControllerStepChange();
    }
    private synchronized void notifyTaskPostExecute(){
        if(mListener != null)mListener.onTaskPostExecute();
    }
    private onTaskControllerStepChangeListener mListener;
    public void setTaskControllerStepChangeListener(onTaskControllerStepChangeListener listener){
        mListener = listener;
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
        interruptAllTask();
    }

    ///////////////////////////////////////////////////
    private void interruptAllTask(){
        synchronized (mTasks){
            for(Iterator<Task> iterator = mTasks.iterator();iterator.hasNext();){
                iterator.next().setTaskState(Task.TASK_STATE_INTERRUPT);
            }
        }
    }
    /////////////////////////////////////////////////////////
    public synchronized void stop(){
        interruptAllTask();
    }
}
