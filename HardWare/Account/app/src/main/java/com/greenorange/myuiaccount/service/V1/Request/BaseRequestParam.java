package com.greenorange.myuiaccount.service.V1.Request;


import com.loopj.android.http.RequestParams;

/**
 * Created by JasWorkSpace on 15/10/27.
 */
public abstract class BaseRequestParam {
    public final static int PARAM_TYPE_UNKNOW = 0;
    public final static int PARAM_TYPE_LOGAIN = 1;
    public final static int PARAM_TYPE_MAX    = 2;
    public abstract int getParamType();
    public abstract boolean checkValid();
    public abstract RequestParams getRequestParam();
    public boolean isValidParamType(){
        int type = getParamType();
        return PARAM_TYPE_UNKNOW < type && type < PARAM_TYPE_MAX;
    }
}
