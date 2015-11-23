package com.greenorange.myuiaccount.service.V2.Request;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;

/**
 * Created by JasWorkSpace on 15/11/2.
 */
public class BindLegencyAccountParam extends BaseRequestParam {
    public String uuid = "";
    public String uid  = "";
    public BindLegencyAccountParam(){
        this(null);
    }
    public BindLegencyAccountParam(BindLegencyAccountParam param){
        if(param != null){
            uuid  = param.uuid;
            uid   = param.uid;
        }
    }
    @Override
    public boolean checkValid() {
        if(TextUtils.isEmpty(uuid) || TextUtils.isEmpty(uid))return false;
        return true;
    }
    @Override
    public String getRequestParam() {
        try{
            return new Gson().toJson(BindLegencyAccountParam.this);
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("BindLegencyAccountParam getRequestParam " + e.toString());
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BindLegencyAccountParam{")
                .append("uuid="+uuid)
                .append(", uid="+uid)
                .append("}");
        return sb.toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
