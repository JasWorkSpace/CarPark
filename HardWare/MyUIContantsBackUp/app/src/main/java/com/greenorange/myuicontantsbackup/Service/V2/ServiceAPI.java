package com.greenorange.myuicontantsbackup.Service.V2;


import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.Service.IRequestCallBack;
import com.greenorange.myuicontantsbackup.Service.RequestHelper;
import com.greenorange.myuicontantsbackup.Service.V2.Request.BackupGetParam;
import com.greenorange.myuicontantsbackup.Service.V2.Request.BackupUpdateParam;
import com.greenorange.myuicontantsbackup.Service.V2.Request.BaseRequestParam;
import com.loopj.android.http.RequestParams;


/**
 * Created by JasWorkSpace on 15/11/16.
 */
public class ServiceAPI {

    ////////////////////////////////////
    public static RequestParams API_MYUI_getBackupSMSUpdateParam(BackupUpdateParam backupUpdateParam){
        return API_MYUI_getParam(Config.getMethodBackupSmsUpdate(), backupUpdateParam);
    }
    public static RequestParams API_MYUI_getBackupSMSGetParams(BackupGetParam backupGetParam){
        return API_MYUI_getParam(Config.getMethodBackupSmsGet(), backupGetParam);
    }
    public static RequestParams API_MYUI_getBackupCalllogUpdateParam(BackupUpdateParam backupUpdateParam){
        return API_MYUI_getParam(Config.getMethodBackupCalllogUpdate(), backupUpdateParam);
    }
    public static RequestParams API_MYUI_getBackupCalllogGetParams(BackupGetParam backupGetParam){
        return API_MYUI_getParam(Config.getMethodBackupCalllogGet(), backupGetParam);
    }
    public static RequestParams API_MYUI_getBackupContactsUpdateParam(BackupUpdateParam backupUpdateParam){
        return API_MYUI_getParam(Config.getMethodBackupContactsUpdate(), backupUpdateParam);
    }
    public static RequestParams API_MYUI_getBackupContactsGetParams(BackupGetParam backupGetParam){
        return API_MYUI_getParam(Config.getMethodBackupContactsGet(), backupGetParam);
    }
    public static RequestParams API_MYUI_getParam(String method, BaseRequestParam param){
        try{
            if(param.checkValid()){//maybe param is null.
                return ServiceHelper.getServiceRequestParams(method, param.getRequestParam());
            }else{
                Log.i("API_MYUI_getParam param is invalid!!! " + param.toString());
            }
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("RequestParamsAPI_MYUI_getParam fail-->"+e.toString());
        }
        return null;
    }
    ///////////////////////////////////
    public static boolean API_MYUI_Request_Async(RequestParams requestParams, IRequestCallBack callBack) throws Exception {
        return RequestHelper.Request_Async(Config.getServiceUrl(), requestParams, callBack);
    }
    public static boolean API_MYUI_Request_Sync(RequestParams requestParams, IRequestCallBack callBack) throws Exception {
        return RequestHelper.Request_Sync(Config.getServiceUrl(), requestParams, callBack);
    }
}
