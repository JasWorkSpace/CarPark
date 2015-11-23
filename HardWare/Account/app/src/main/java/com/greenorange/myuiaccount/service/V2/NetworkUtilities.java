package com.greenorange.myuiaccount.service.V2;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.Util.SharePreferenceUtil;
import com.greenorange.myuiaccount.service.V2.Request.LogainParam;
import com.greenorange.myuiaccount.service.V2.Response.Ticket;
import com.greenorange.myuiaccount.service.V2.Response.UserInfo;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JasWorkSpace on 15/10/14.
 */

public class NetworkUtilities {

    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;//30s

    public static HttpClient getHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();
        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        HttpConnectionParams.setSoTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        ConnManagerParams.setTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        return httpClient;
    }

    public static InputStream getHttpInputStream(String url) throws Throwable{
        Log.i("NetworkUtilities getHttpInputStream url=" + url);
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        connection.setConnectTimeout(HTTP_REQUEST_TIMEOUT_MS);//
        connection.setRequestMethod("GET");
        return connection.getInputStream();
    }

    public static Ticket getTicket(Context context, String username, String password){
        ServiceCache serviceCache = ServiceCache.getInstance(context);
        try{
            String ticket = serviceCache.getCache(SharePreferenceUtil.KEY_TICKET);
            if(TextUtils.isEmpty(ticket) || serviceCache.isCacheTimeout(SharePreferenceUtil.KEY_TICKET)){
                LogainParam logainParam = new LogainParam();
                logainParam.setUsername(username);
                logainParam.setPassword(password);
                logainParam.setEnv(ServiceHelper.getEnvParam(context));
                ticket = ServiceAPI.API_MYUI_Logain(logainParam);
//                ticket = ServiceAPI.API_MYUI_Logain(username, password);
            }
            if(!TextUtils.isEmpty(ticket)){
                Ticket ticket1 = new Gson().fromJson(ticket, Ticket.class);
                serviceCache.setCache(SharePreferenceUtil.KEY_TICKET, ticket);
                return ticket1;
            }
        }catch (Throwable e){e.printStackTrace();Log.i("getTicket fail1 -->"+e.toString());}
        try {
            return new Gson().fromJson(serviceCache.getRealCache(SharePreferenceUtil.KEY_TICKET), Ticket.class);
        }catch (Throwable e){e.printStackTrace();Log.d("getTicket fail2 -->"+e.toString());}
        return null;
    }
    public static UserInfo getUserInfoWithNoCache(Context context, String username, String password){
        Gson gson = new Gson();
        try{
            LogainParam logainParam = new LogainParam();
            logainParam.setUsername(username);
            logainParam.setPassword(password);
            logainParam.setEnv(ServiceHelper.getEnvParam(context));
            Ticket ticket = gson.fromJson(ServiceAPI.API_MYUI_Logain(logainParam), Ticket.class);
            if(ticket.isSuccess()){
                return gson.fromJson(ServiceAPI.API_MYUI_GetUser(ticket.ticket), UserInfo.class);
            }
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("getUserInfoWithNoCache fail 1-->"+e.toString());
        }
        return null;
    }
    public static UserInfo getUserInfo(Context context, String username, String password){
        ServiceCache serviceCache = ServiceCache.getInstance(context);
        Gson gson = new Gson();
        try{
            String userinfo = serviceCache.getCache(SharePreferenceUtil.KEY_USERINFO);
            if(TextUtils.isEmpty(userinfo)){
                Ticket ticket = getTicket(context, username, password);
                if(ticket != null && ticket.isSuccess()) {
                    userinfo = ServiceAPI.API_MYUI_GetUser(ticket.ticket);
                    if (!TextUtils.isEmpty(userinfo)) {
                        UserInfo userInfo = gson.fromJson(userinfo, UserInfo.class);
                        serviceCache.setCache(SharePreferenceUtil.KEY_USERINFO, userinfo);
                        return userInfo;
                    }
                }
            }
            return gson.fromJson(userinfo, UserInfo.class);
        }catch (Throwable e){e.printStackTrace();Log.i("getUserInfo fail1 -->"+e.toString());}
        try {
            return gson.fromJson(serviceCache.getRealCache(SharePreferenceUtil.KEY_USERINFO), UserInfo.class);
        }catch (Throwable e){e.printStackTrace();Log.i("getUserInfo fail2 -->"+e.toString());}
        return null;
    }
}
