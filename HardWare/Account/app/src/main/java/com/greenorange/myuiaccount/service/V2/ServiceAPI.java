package com.greenorange.myuiaccount.service.V2;

import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.service.RequestCallBack;
import com.greenorange.myuiaccount.service.RequestHelper;
import com.greenorange.myuiaccount.service.V2.Request.BaseRequestParam;
import com.greenorange.myuiaccount.service.V2.Request.BindLegencyAccountParam;
import com.greenorange.myuiaccount.service.V2.Request.ChangePasswordParam;
import com.greenorange.myuiaccount.service.V2.Request.CheckOldAccountStateParam;
import com.greenorange.myuiaccount.service.V2.Request.GetUserParam;
import com.greenorange.myuiaccount.service.V2.Request.LogainParam;
import com.greenorange.myuiaccount.service.V2.Request.RegisterParam;
import com.greenorange.myuiaccount.service.V2.Request.SendMessageParam;
import com.greenorange.myuiaccount.service.V2.Response.BaseResponse;
import com.loopj.android.http.RequestParams;

/**
 * Created by JasWorkSpace on 15/10/15.
 */
public class ServiceAPI {

    public static String API_MYUI_Logain(String username, String password) throws Exception {
        LogainParam logainParam = new LogainParam();
        logainParam.setUsername(username);
        logainParam.setPassword(password);
        return API_MYUI_Logain(logainParam);
    }
    public static String API_MYUI_Logain(LogainParam logainParam) throws Exception {
        return API_MYUI_Request(Config.getMethodLogin(), logainParam);
    }

    public static String API_MYUI_Register(String account, String realname, String password,
                                           String mobileno, String email, String validatedBy) throws Exception {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setAccount(account);
        registerParam.setRealname(realname);
        registerParam.setPassword(password);
        registerParam.setMobileno(mobileno);
        registerParam.setEmail(email);
        registerParam.setValidatedBy(validatedBy);
        return API_MYUI_Register(registerParam);
    }
    public static String API_MYUI_Register(RegisterParam registerParam) throws Exception {
        return API_MYUI_Request(Config.getMethodRegister(), registerParam);
    }

    public static String API_MYUI_ChangePassword(String account, String oldPassword, String newPassword) throws Exception {
        ChangePasswordParam changePassword = new ChangePasswordParam();
        changePassword.setAccount(account);
        changePassword.setOldPassword(oldPassword);
        changePassword.setNewPassword(newPassword);
        return API_MYUI_ChangePassword(changePassword);
    }
    public static String API_MYUI_ChangePassword(ChangePasswordParam changePassword) throws Exception {
        return API_MYUI_Request(Config.getMethodChangepassword(), changePassword);
    }

    public static String API_MYUI_GetUser(String  ticket) throws Exception {
        GetUserParam getUserParam = new GetUserParam();
        getUserParam.setTicket(ticket);
        return API_MYUI_GetUser(getUserParam);
    }
    public static String API_MYUI_GetUser(GetUserParam getUserParam) throws Exception {
        return API_MYUI_Request(Config.getMethodGetuser(), getUserParam);
    }

    public static String API_MYUI_SendSMS(String mobileno, String message) throws Exception {
        SendMessageParam sendMessageParam = new SendMessageParam();
        sendMessageParam.setMobileno(mobileno);
        sendMessageParam.setSmsBody(message);
        return API_MYUI_SendSMS(sendMessageParam);
    }
    public static String API_MYUI_SendSMS(SendMessageParam param) throws Exception {
        return API_MYUI_Request(Config.getMethodSendmessage(), param);
    }

    public static String API_MYUI_CheckLegencyAccountState(String uuid) throws Exception {
        CheckOldAccountStateParam checkOldAccountStateParam = new CheckOldAccountStateParam();
        checkOldAccountStateParam.setUuid(uuid);
        return API_MYUI_CheckLegencyAccountState(checkOldAccountStateParam);
    }
    public static String API_MYUI_CheckLegencyAccountState(CheckOldAccountStateParam checkOldAccountStateParam) throws Exception {
        return API_MYUI_Request(Config.getMethodChecklegencyaccountstate(), checkOldAccountStateParam);
    }

    public static String API_MYUI_BindLegencyAccount(String uuid, String uid) throws Exception {
        BindLegencyAccountParam bindLegencyAccountParam = new BindLegencyAccountParam();
        bindLegencyAccountParam.setUuid(uuid);
        bindLegencyAccountParam.setUid(uid);
        return API_MYUI_BindLegencyAccount(bindLegencyAccountParam);
    }
    public static String API_MYUI_BindLegencyAccount(BindLegencyAccountParam param) throws Exception {
        return API_MYUI_Request(Config.getMethodBindlegencyaccount(), param);
    }
    ///////////////////////////////////////////////////////////////
    public static String API_MYUI_Request(String method, BaseRequestParam param) throws Exception {
        BaseResponse baseResponse = null;
        try{//first check param
            if(param == null || !param.checkValid())throw new Exception("invalid param !!!");
        }catch (Throwable e){e.printStackTrace();   throw new Exception("invalid param !!!");}
        try{
            baseResponse = ServiceHelper.getServiceBaseResponse(method, param.getRequestParam());
            if(baseResponse != null)Log.i("baseResponse-->"+baseResponse.toString());
        }catch (Throwable e){e.printStackTrace();}
        return API_MYUI_ParserBaseResponse(baseResponse);
    }
    public static boolean API_MYUI_Request_Async(RequestParams requestParams, RequestCallBack callBack) throws Exception {
        return RequestHelper.Request_Async(Config.getServiceUrl(), requestParams, callBack);
    }
    public static String API_MYUI_ParserBaseResponse(BaseResponse baseResponse) throws Exception {
        if(baseResponse != null) {
            if (baseResponse.checkValid()) {
                return ServiceHelper.getDecodeParam(baseResponse.businessData);
            }else {
                throw new Exception(baseResponse.getFailMessage());
            }
        }
        throw new Exception("unknow fail");
    }
    /////////////////////////////////////////////////////////////////
    public static RequestParams API_MYUI_getCheckLegencyAccountStateRequestParams(String uuid){
        try{
            CheckOldAccountStateParam checkOldAccountStateParam = new CheckOldAccountStateParam();
            checkOldAccountStateParam.setUuid(uuid);
            return ServiceHelper.getServiceRequestParams(Config.getMethodChecklegencyaccountstate(),
                        checkOldAccountStateParam.getRequestParam());
        }catch (Throwable e){e.printStackTrace();Log.d("API_MYUI_getCheckLegencyAccountStateRequestParams fail" + e.toString());}
        return null;
    }
    public static RequestParams API_MYUI_getBindLegencyAccountRequestParams(String uuid, String uid){
        try{
            BindLegencyAccountParam bindLegencyAccountParam = new BindLegencyAccountParam();
            bindLegencyAccountParam.setUid(uid);
            bindLegencyAccountParam.setUuid(uuid);
            return ServiceHelper.getServiceRequestParams(Config.getMethodBindlegencyaccount(),
                    bindLegencyAccountParam.getRequestParam());
        }catch (Throwable e){e.printStackTrace();Log.d("API_MYUI_getBindLegencyAccountRequestParams fail" + e.toString());}
        return null;
    }
    public static RequestParams API_MYUI_getUnBindLegencyAccountRequestParams(String uuid, String uid){
        try{
            BindLegencyAccountParam bindLegencyAccountParam = new BindLegencyAccountParam();
            bindLegencyAccountParam.setUid(uid);
            bindLegencyAccountParam.setUuid(uuid);
            return ServiceHelper.getServiceRequestParams(Config.getMethodUnbindlegencyaccount(),
                        bindLegencyAccountParam.getRequestParam());
        }catch (Throwable e){e.printStackTrace();Log.d("API_MYUI_getUnBindLegencyAccountRequestParams fail" + e.toString());}
        return null;
    }
    public static RequestParams API_MYUI_getLogainRequestParams(String username, String password, String env){
        try{
            LogainParam logainParam = new LogainParam();
            logainParam.setUsername(username);
            logainParam.setPassword(password);
            logainParam.setEnv(env);
            return ServiceHelper.getServiceRequestParams(Config.getMethodLogin(),
                    logainParam.getRequestParam());
        }catch (Throwable e){e.printStackTrace();Log.d("API_MYUI_getLogainRequestParams fail" + e.toString());}
        return null;
    }
}
