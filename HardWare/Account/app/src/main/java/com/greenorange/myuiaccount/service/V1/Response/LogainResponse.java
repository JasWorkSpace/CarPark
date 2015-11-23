package com.greenorange.myuiaccount.service.V1.Response;

import android.text.TextUtils;

/**
 * Created by JasWorkSpace on 15/11/2.
 */
public class LogainResponse extends BaseResponse {

    public String appSecret = "";

    public LogainResponse(){
        this(null);
    }
    public LogainResponse(LogainResponse logainResponse){
        super(logainResponse);
        if(logainResponse != null){
            appSecret  = logainResponse.appSecret;
        }
    }

    @Override
    public boolean checkValid() {
        return (TextUtils.equals("1", getStatus())
                    && !TextUtils.isEmpty(appSecret));
    }

    @Override
    public boolean tranformResponse() {
        return false;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LogainResponse{")
                .append("appSecret="+appSecret)
                .append("}");
        return sb.toString();
    }
    public String getAppSecret() {
        return appSecret;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
