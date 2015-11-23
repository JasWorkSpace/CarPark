package com.greenorange.myuiaccount.service.V2;

import android.content.Context;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.Util.AndroidUtil;
import com.greenorange.myuiaccount.Util.Utils;
import com.greenorange.myuiaccount.service.V2.Response.BaseResponse;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.Format;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JasWorkSpace on 15/10/14.
 */
public class ServiceHelper {

    public static final String ENCODING = "utf-8";

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
    public static String getServiceResponce(String method, String param) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = NetworkUtilities.getHttpInputStream(getServiceURL(method, param));
            return paraseResponse(inputStream.toString());//maybe inputstream is null. it case NullPointException.
        }catch (Throwable e){
            e.printStackTrace();
            throw new Exception("unkonw exception");
        }finally {
            if(inputStream != null)inputStream.close();
        }
    }
    public static BaseResponse getServiceBaseResponse(String method, String param) throws Exception {
        Log.d("ServiceHelper getServiceBaseResponse method="+method+", param="+param);
        InputStream inputStream = null;
        try {
            inputStream = NetworkUtilities.getHttpInputStream(getServiceURL(method, param));
            return (new Gson().fromJson(ConvertStream2String(inputStream), BaseResponse.class));
        }catch (Throwable e){
            e.printStackTrace();
            Log.i("getServiceBaseResponse exception -->" + e.toString());
            throw new Exception("unkonw exception");
        }finally {
            if(inputStream != null)try{inputStream.close();}catch(Throwable e){}
        }
    }
    public static String ConvertStream2String(InputStream inputStream){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            return buffer.toString();
        }catch (Throwable e){e.printStackTrace(); Log.d("ConvertStream2String fail-->" + e.toString());}finally {
            if(in !=null) try {in.close();} catch (IOException e) {e.printStackTrace();}
        }
        return null;
    }
    public static String getAuth(){
        int max    = 10000;
        int random = new Random(System.currentTimeMillis()).nextInt(max);
        return String.format("%04d", (random+max)%max);//4
    }
    public static String getEnvParam(Context context){
        HashMap<String,String> map = new HashMap<String, String>();
        String imei = null;
        String ip   = null;
        try {
             imei = AndroidUtil.getIMEI(context);
        }catch (Throwable e){e.printStackTrace();}
        try{
            ip = AndroidUtil.getIp();
        }catch (Throwable e){e.printStackTrace();}
        map.put("DEVICEID", Utils.getString(imei));
        map.put("IP", Utils.getString(ip));
        try {
            return ServiceHelper.getEncodeParam(new Gson().toJson(map));
        }catch (Throwable e){e.printStackTrace();Log.d("getEnvParam fail-->"+e.toString());}
        return "";
    }
    /////////////////////////////////////////////////
    public static boolean isAccountValid(String account){
        return isMebliePhoneNumberValid(account);//only aupport phone number re
    }
    public static boolean isPasswordValid(String password){
        try{
            if(!TextUtils.isEmpty(password)
                    && !TextUtils.isEmpty(getEncrypt(password))){
                int length = password.length();
                return 6 <= length && length <= 10;
            }
        }catch (Throwable e){Log.d("isPasswordValid fail. password="+password+", "+e.toString());}
        return false;
    }
    public static boolean isMebliePhoneNumberValid(String number){
        try{
            if(!TextUtils.isEmpty(number)
                    && PhoneNumberUtils.isGlobalPhoneNumber(number)
                    && isMobileNO(number)){
                return true;
            }
        }catch (Throwable e){Log.d("isMebliePhoneNumberValid fail. number="+number+", "+e.toString());}
        return false;
    }
    public static boolean isEmailNumberValid(String email){
        try{
            if(!TextUtils.isEmpty(email)
                    && isEmail(email)){
                return true;
            }
        }catch (Throwable e){Log.d("isMebliePhoneNumberValid fail. email="+email+", "+e.toString());}
        return false;
    }
    public static boolean isMobileNO(String mobiles){
        /*移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
          联通：130、131、132、152、155、156、185、186
          电信：133、153、180、189、（1349卫通）*/
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static boolean isZipNO(String zipString){//邮编
        /*中国邮政编码为6位数字，第一位不为0*/
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
