package com.greenorange.myuicontantsbackup.Service.V2;


import android.net.Uri;
import android.text.TextUtils;

import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.Service.Util.ZIPUtils;
import com.greenorange.myuicontantsbackup.Service.V2.Response.BaseResponse;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by JasWorkSpace on 15/10/14.
 */
public class ServiceHelper {

    public static final String ENCODING = "utf-8";
    public static String getZipString(String string) throws IOException {return ZIPUtils.compress(string);}
    public static String getUnZipString(String string) throws IOException {return ZIPUtils.uncompress(string);}
    public static String getEncodeParam(String param) throws UnsupportedEncodingException {
        return URLEncoder.encode(param, ENCODING);
    }
    public static String getDecodeParam(String param) throws UnsupportedEncodingException{
        return URLDecoder.decode(param, ENCODING);
    }
    public static String getEncrypt(String string) throws Exception {
        return SecureUtil.encryptDES(string, Config.getKey());
    }
    public static String getDecrypt(String string) throws Exception {
        return SecureUtil.decryptDES(string, Config.getKey());
    }
    public static String getClientEncrypt(String string) throws Exception {
        return SecureUtil.encryptClientDES(string, Config.getKey());
    }
    public static String getClientDecrypt(String string) throws Exception {
        return SecureUtil.decryptClientDES(string, Config.getKey());
    }
    public static String getToken(String method, String param,String timestamp) throws Exception {
        return SecureUtil.md5(Config.getChannel() + method + param + timestamp + Config.getChannelkey());
    }
    public static String getServiceURL(){
        return Config.getServiceUrl();
    }
    public static String getServiceURL(String method, String param) throws Exception {
        if(TextUtils.isEmpty(method))throw new Exception("invalid method");
        if(TextUtils.isEmpty(param))throw new Exception("invalid param");
        String encodeparam = param;
        try{
            encodeparam = getEncodeParam(encodeparam);
        }catch (Throwable e){e.printStackTrace();throw new Exception("invalid param");}
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            Uri.Builder url = Uri.parse(getServiceURL()).buildUpon();
            url.appendQueryParameter("channel", Config.getChannel());
            url.appendQueryParameter("method", method);
            url.appendQueryParameter("params", encodeparam);
            url.appendQueryParameter("timestamp", timestamp);
            url.appendQueryParameter("token", getToken(method, encodeparam, timestamp));
            return url.build().toString();
        }catch (Throwable e){
            e.printStackTrace();
            throw new Exception("unkonw exception");
        }
    }
    public static RequestParams getServiceRequestParams(String method, String param)throws Exception{
        if(TextUtils.isEmpty(method))throw new Exception("invalid method");
        if(TextUtils.isEmpty(param))throw new Exception("invalid param");
        String encodeparam = param;
        try{
            encodeparam = getEncodeParam(encodeparam);
        }catch (Throwable e){e.printStackTrace();throw new Exception("invalid param");}
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            RequestParams requestParams = new RequestParams();
            requestParams.put("channel", Config.getChannel());
            requestParams.put("method", method);
            requestParams.put("params", encodeparam);
            requestParams.put("timestamp", timestamp);
            requestParams.put("token", getToken(method, encodeparam, timestamp));
            return requestParams;
        }catch (Throwable e){
            e.printStackTrace();
            throw new Exception("unkonw exception");
        }
    }
    public static String paraseResponse(String response) throws Exception {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(TextUtils.equals("success", jsonObject.getString("gwResult"))){
                if(TextUtils.equals("success", jsonObject.getString("businessResult"))){
                    return jsonObject.getString("businessData");
                }
                throw new Exception("business fail " + jsonObject.getString("businessData"));
            }
            throw new Exception("gwfail " + jsonObject.getString("gwMessage"));
        }catch (Throwable e){
            throw new Exception("unknow response " + response);
        }
    }
    public static String ParserBaseResponse(BaseResponse baseResponse) throws Exception {
        if(baseResponse != null) {
            if (baseResponse.checkValid()) {
                return ServiceHelper.getDecodeParam(baseResponse.businessData);
            }else {
                throw new Exception(baseResponse.getFailMessage());
            }
        }
        throw new Exception("unknow fail");
    }

}
