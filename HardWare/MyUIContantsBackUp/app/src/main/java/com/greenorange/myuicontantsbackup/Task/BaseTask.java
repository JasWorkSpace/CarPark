package com.greenorange.myuicontantsbackup.Task;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.greenorange.myuicontantsbackup.Account.UserInfo;
import com.greenorange.myuicontantsbackup.BackUpApplication;
import com.greenorange.myuicontantsbackup.Bean.TaskDBBean;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.Service.Util.AndroidUtil;
import com.greenorange.myuicontantsbackup.Service.V2.Request.BackupGetParam;
import com.greenorange.myuicontantsbackup.Service.V2.Request.BackupUpdateParam;
import com.greenorange.myuicontantsbackup.Service.V2.RequestCallBack;
import com.greenorange.myuicontantsbackup.Service.V2.Response.BackBackupResponse;
import com.greenorange.myuicontantsbackup.Service.V2.Response.BaseResponse;
import com.greenorange.myuicontantsbackup.Service.V2.ServiceAPI;
import com.greenorange.myuicontantsbackup.Service.V2.ServiceHelper;
import com.greenorange.myuicontantsbackup.Task.Utils.BackupDataUtil;
import com.greenorange.myuicontantsbackup.db.Taskdb;
import com.loopj.android.http.RequestParams;

/**
 * Created by JasWorkSpace on 15/11/17.
 */
public abstract class BaseTask extends Task {

    public final static int TASK_BASETYPE_BACKUP       = 1;
    public final static int TASK_BASETYPE_RESTORE      = 2;

    public final static int TASK_BASETYPE_AUTO_BACKUP  = 3;
    public final static int TASK_BASETYPE_AUTO_RESTORE = 4;
    public Gson mGson = new Gson();
    public int mBaseType = 0;
    public BaseTask(Context context, UserInfo userInfo, int type){
        super(context, userInfo);
        mBaseType = type;
    }
    @Override
    public void run() {
        if(getTaskState() != TASK_STATE_PENDING){
            return;
        }
        setTaskState(TASK_STATE_WORK);
        loadLastTaskDBBean();//we load it first.
        if(isNeedBackupOrRestore()) {
            addTaskDB();
            if(isBackupType(getTaskType())){
                backupData();
            }else if(isRestoreType(getTaskType())){
                restoreData();
            }
        }
        setTaskState(TASK_STATE_STOP);
    }

    public abstract String  getBackupData();
    public abstract boolean RestoreData(String data);
    public abstract boolean isNeedBackupOrRestore();
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void backupData(){
        Log.d("backupData "+getCurrentTaskLib()+" start ++++++ State=" + getTaskState());
        String data = getBackupData();
        if (TASK_STATE_WORK == getTaskState()) {
            if (!TextUtils.isEmpty(data)) {
                try {
                    ServiceAPI.API_MYUI_Request_Sync(getBackupRequestParams(getBackupUpdateParam(data))
                        , new RequestCallBack(){
                        @Override
                        public void onFinish() {
                            super.onFinish();
                            if(TASK_STATE_WORK != getTaskState())return;
                            Log.d("backupData onFinish response="+getResponse());
                            try {
                                if(TextUtils.isEmpty(getResponse())){
                                    setTaskState(TASK_STATE_FAIL);
                                } else {
                                    ServiceHelper.ParserBaseResponse(mGson.fromJson(getResponse(), BaseResponse.class));
                                    setTaskState(TASK_STATE_SUCCESS);
                                }
                            } catch (Throwable e) {
                                e.printStackTrace();
                                Log.d("backupData fail1-->" + e.toString());
                                setTaskState(TASK_STATE_FAIL);
                            }
                        }
                    });
                } catch (Throwable e) {
                    e.printStackTrace();
                    setTaskState(TASK_STATE_FAIL);
                    Log.d("backupData fail2-->" + e.toString());
                }
            }else{
                setTaskState(TASK_STATE_FAIL);
            }
        }
        Log.d("backupData "+getCurrentTaskLib()+" end ++++++ State=" + getTaskState());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private String parserBackupData(String response) throws Exception {
        String responseData = ServiceHelper.ParserBaseResponse(mGson.fromJson(response, BaseResponse.class));
        BackBackupResponse callLogResponse = mGson.fromJson(responseData, BackBackupResponse.class);
        Log.d("parserBackupData-->" + (callLogResponse == null ? "" : callLogResponse.toString()));
        int clientVersion = (TextUtils.isEmpty(callLogResponse.getClientVersion())
                ? 0 : Integer.parseInt(callLogResponse.getClientVersion()));
        return BackupDataUtil.getDecrypt(callLogResponse.getData(),clientVersion);
    }
    public void restoreData(){
        Log.d("restoreData "+ getCurrentTaskLib() +" start ++++++ State=" + getTaskState());
        if(TASK_STATE_WORK == getTaskState()){
            try{
                ServiceAPI.API_MYUI_Request_Sync(getRestoreRequestParams(getBackupGetParam())
                    ,new RequestCallBack(){
                        @Override
                        public void onFinish() {
                            super.onFinish();
                            Log.d("restoreData onFinish response-->"+getResponse());
                            if(TASK_STATE_WORK != getTaskState())return;
                            try {
                                if(RestoreData(parserBackupData(getResponse()))){
                                    setTaskState(TASK_STATE_SUCCESS);
                                }else{
                                    setTaskState(TASK_STATE_FAIL);
                                }
                            } catch (Throwable e) {
                                e.printStackTrace();
                                setTaskState(TASK_STATE_FAIL);
                                Log.d("restoreData fail1-->" + e.toString());
                            }
                        }
                    }
                );
            }catch (Throwable e){
                e.printStackTrace();
                setTaskState(TASK_STATE_FAIL);
                Log.d("restoreData fail2-->" + e.toString());
            }
        }
        Log.d("restoreData" + getCurrentTaskLib()+" end ++++++ State=" + getTaskState());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    private BackupUpdateParam getBackupUpdateParam(String data){
        UserInfo userInfo = getUserInfo();
        if(userInfo != null){
            BackupUpdateParam backupUpdateParam = new BackupUpdateParam();
            backupUpdateParam.setImei(AndroidUtil.getIMEI(BackUpApplication.getInstance()));
            backupUpdateParam.setUuid(userInfo.getBbsid());
            backupUpdateParam.setClientVersion(//set client version.
                    String.valueOf(AndroidUtil.getClientVersionCode(BackUpApplication.getInstance()))
            );
            backupUpdateParam.setDataVersion(String.valueOf(System.currentTimeMillis()));
            backupUpdateParam.setData(data);
            return backupUpdateParam;
        }
        return null;
    }
    private BackupGetParam getBackupGetParam(){
        UserInfo userInfo = getUserInfo();
        if(userInfo != null) {
            BackupGetParam backupGetParam = new BackupGetParam();
            backupGetParam.setImei(AndroidUtil.getIMEI(BackUpApplication.getInstance()));
            //backupGetParam.setDataVersion(""); // we don't set it for now.
            backupGetParam.setUuid(userInfo.getBbsid());
            return backupGetParam;
        }
        return null;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private RequestParams getBackupRequestParams(BackupUpdateParam backupUpdateParam){
        int type = getTaskType();
        if(isBackupType(type)){
            switch(type){
                case TASK_TYPE_BACKUP_CELLLOG:
                    return ServiceAPI.API_MYUI_getBackupCalllogUpdateParam(backupUpdateParam);
                case TASK_TYPE_BACKUP_CONTACTS:
                    return ServiceAPI.API_MYUI_getBackupContactsUpdateParam(backupUpdateParam);
                case TASK_TYPE_BACKUP_SMS:
                    return ServiceAPI.API_MYUI_getBackupSMSUpdateParam(backupUpdateParam);
            }
        }
        return null;
    }
    private RequestParams getRestoreRequestParams(BackupGetParam backupGetParam){
        int type = getTaskType();
        if(isRestoreType(type)){
            switch(type){
                case TASK_TYPE_RESTORE_CALLLOG:
                    return ServiceAPI.API_MYUI_getBackupCalllogGetParams(backupGetParam);
                case TASK_TYPE_RESTORE_CONTACTS:
                    return ServiceAPI.API_MYUI_getBackupContactsGetParams(backupGetParam);
                case TASK_TYPE_RESTORE_SMS:
                    return ServiceAPI.API_MYUI_getBackupSMSGetParams(backupGetParam);
            }
        }
        return null;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public  TaskDBBean mLastTaskDBBean;
    private void loadLastTaskDBBean(){
        UserInfo userInfo = getUserInfo();
        if(userInfo != null){
            mLastTaskDBBean = Taskdb.getInstace(BackUpApplication.getInstance())
                    .getTask(userInfo.getUid(), getTaskType());
        }
    }
    private void addTaskDB(){
        UserInfo userInfo = getUserInfo();
        if(userInfo != null){
            TaskDBBean taskDBBean = new TaskDBBean();
            taskDBBean.setUid(userInfo.getUid());
            taskDBBean.setBbsid(userInfo.getBbsid());
            taskDBBean.setTasktype(getTaskType());
            taskDBBean.setLasttime(System.currentTimeMillis());
            Taskdb.getInstace(BackUpApplication.getInstance())
                    .addTask(taskDBBean);
        }
    }
    ////////////////////////////////////////////////////
    private String getCurrentTaskLib(){
        return BackUpApplication.getInstance().getString(getTaskLib(getTaskType()));
    }
}
