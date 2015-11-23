package com.greenorange.myuiaccount.service.V2.Request;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;

/**
 * Created by JasWorkSpace on 15/10/16.
 */
public class GetUserParam extends BaseRequestParam {
    public String ticket = "";
    public GetUserParam(){this(null);}
    public GetUserParam(GetUserParam getUserParam){
        if(getUserParam != null){
            ticket = getUserParam.ticket;
        }
    }
    @Override
    public boolean checkValid() {
        if(TextUtils.isEmpty(ticket))return false;
        return true;
    }
    @Override
    public String getRequestParam() {
        try {
            return new Gson().toJson(GetUserParam.this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("GetUserParam getRequestParam " + e.toString());
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GetUserParam{")
                .append("ticket="+ticket)
                .append("}");
        return sb.toString();
    }
    ////////////////////////////
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
