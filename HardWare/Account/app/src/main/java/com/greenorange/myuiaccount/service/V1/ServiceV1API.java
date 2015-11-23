package com.greenorange.myuiaccount.service.V1;


import com.greenorange.myuiaccount.service.RequestHelper;
import com.greenorange.myuiaccount.service.V1.Request.BaseRequestParam;
import com.greenorange.myuiaccount.service.V1.Request.LogainParam;
import com.greenorange.myuiaccount.service.RequestCallBack;


/**
 * Created by JasWorkSpace on 15/10/27.
 */
public class ServiceV1API {

    public static boolean API_MYUI_Logain_sync(LogainParam logainParam, RequestCallBack callBack) throws Exception {
        return API_MYUI_Request_Sync(Config.getURL_UserLogin(), logainParam, callBack);
    }
    public static boolean API_MYUI_Logain_async(LogainParam logainParam, RequestCallBack callBack) throws Exception {
        return API_MYUI_Request_Async(Config.getURL_UserLogin(), logainParam, callBack);
    }
    ///////////////////////////////////////////////////
    //public static BaseResponse
    public static boolean API_MYUI_Request_Sync(String url, BaseRequestParam baseRequestParam, final RequestCallBack callBack) throws Exception {
        return RequestHelper.Request_Sync(url, baseRequestParam, callBack);
    }
    public static boolean API_MYUI_Request_Async(String url, BaseRequestParam baseRequestParam, final RequestCallBack callBack) throws Exception {
        return RequestHelper.Request_Async(url, baseRequestParam, callBack);
    }
}
