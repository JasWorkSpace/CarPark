package com.greenorange.myuiaccount.service.V2.Request;

/**
 * Created by JasWorkSpace on 15/10/16.
 */
public abstract class BaseRequestParam {
    public abstract boolean checkValid();
    public abstract String  getRequestParam();
}
