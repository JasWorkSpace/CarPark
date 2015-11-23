package com.greenorange.myuiaccount.service.V2.Request;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;

/**
 * Created by JasWorkSpace on 15/10/20.
 */
public class SendMessageParam extends BaseRequestParam{

    public String mobileno = "";
    public String smsBody  = "";

    public SendMessageParam(){
        this(null);
    }
    public SendMessageParam(SendMessageParam param){
        if(param != null){
            mobileno = param.mobileno;
            smsBody  = param.smsBody;
        }
    }
    @Override
    public boolean checkValid() {
        if(ServiceHelper.isMebliePhoneNumberValid(mobileno)
                && !TextUtils.isEmpty(smsBody))return true;
        return false;
    }

    @Override
    public String getRequestParam() {
        try {
            return new Gson().toJson(SendMessageParam.this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SendMessageParam getRequestParam " + e.toString());
        }
        return null;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }
}
