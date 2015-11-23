package com.greenorange.myuiaccount.service.V1.Request;

import android.text.TextUtils;

import com.greenorange.myuiaccount.Util.Utils;
import com.loopj.android.http.RequestParams;

/**
 * Created by JasWorkSpace on 15/10/27.
 */
public class LogainParam extends BaseRequestParam {
    private String username  = "";
    private String password  = "";
    private String imei      = "";
    @Override
    public int getParamType() {
        return PARAM_TYPE_LOGAIN;
    }
    @Override
    public boolean checkValid() {
        if(TextUtils.isEmpty(username)
                ||TextUtils.isEmpty(password))return false;
        return true;
    }
    @Override
    public RequestParams getRequestParam() {
        try {
            RequestParams requestparams = new RequestParams();
            requestparams.put("username", Utils.getString(username));
            requestparams.put("password", Utils.getString(password));
            requestparams.put("imei", Utils.getString(imei));
            return requestparams;
        }catch (Throwable e){e.printStackTrace();}
        return null;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getImei() {
        return imei;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }
}
