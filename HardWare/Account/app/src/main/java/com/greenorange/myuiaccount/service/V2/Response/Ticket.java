package com.greenorange.myuiaccount.service.V2.Response;

import android.text.TextUtils;

/**
 * Created by JasWorkSpace on 15/10/29.
 */
public class Ticket {
    public final static String STATECODE_SUCCESS = "000";
    public String ticket    = "";
    public String stateCode = "";

    public boolean isSuccess(){
        return TextUtils.equals(stateCode, STATECODE_SUCCESS);
    }
}
