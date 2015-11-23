package com.greenorange.myuiaccount.service.V2.Response;

import android.text.TextUtils;

/**
 * Created by JasWorkSpace on 15/11/2.
 */
public class LegencyAccountState {
    public final static String FORBIDDEN = "forbidden";
    public final static String ALLOW     = "allow";

    public String state = "";

    public boolean isNeedBind(){
        return TextUtils.equals(ALLOW, state);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
