package com.greenorange.myuiaccount.service.V2.Request;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;

/**
 * Created by JasWorkSpace on 15/11/2.
 */
public class CheckOldAccountStateParam extends BaseRequestParam{
    public String uuid = "";

    public CheckOldAccountStateParam(){
        this(null);
    }
    public CheckOldAccountStateParam(CheckOldAccountStateParam param){
        if(param != null){
            uuid  = param.uuid;
        }
    }
    @Override
    public boolean checkValid() {
        if(TextUtils.isEmpty(uuid))return false;
        return true;
    }
    @Override
    public String getRequestParam() {
        try{
            return new Gson().toJson(CheckOldAccountStateParam.this);
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("CheckOldAccountStateParam getRequestParam " + e.toString());
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("CheckOldAccountStateParam{")
                .append("uuid="+uuid)
                .append("}");
        return sb.toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
