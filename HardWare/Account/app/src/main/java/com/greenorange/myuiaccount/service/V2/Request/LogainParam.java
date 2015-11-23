package com.greenorange.myuiaccount.service.V2.Request;

import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;

import java.io.Serializable;

/**
 * Created by JasWorkSpace on 15/10/16.
 */
public class LogainParam extends BaseRequestParam{
    public String username = "";
    public String password = "";
    public String env      = "";
    //public HashMap<String,String> env = new HashMap<String, String>();
    public LogainParam(){this(null);}
    public LogainParam(LogainParam logainParam){
        if(logainParam != null){
            username  = logainParam.username;
            password  = logainParam.password;
            //env       = (HashMap<String, String>) logainParam.env.clone();
            env       = logainParam.env;
        }
    }
    @Override
    public boolean checkValid() {
        if(ServiceHelper.isAccountValid(username)
                && ServiceHelper.isPasswordValid(password))return true;
        return false;
    }
    @Override
    public String getRequestParam() {
        try {
            LogainParam logainParam = new LogainParam(LogainParam.this);
            logainParam.password = ServiceHelper.getEncrypt(logainParam.password);
            return new Gson().toJson(logainParam);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LogainParam getRequestParam " + e.toString());
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LogainParam{")
                .append("username="+username)
                .append(", password="+password)
                .append(", env="+(env==null?"":env.toString()))
                .append("}");
        return sb.toString();
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

//    public HashMap<String, String> getEnv() {
//        return env;
//    }
//
//    public void setEnv(HashMap<String, String> env) {
//        this.env = env;
//    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
