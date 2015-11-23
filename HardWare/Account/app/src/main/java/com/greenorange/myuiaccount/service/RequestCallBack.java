package com.greenorange.myuiaccount.service;

/**
 * Created by JasWorkSpace on 15/10/29.
 */
public interface RequestCallBack {
    public void onStart();
    public void onSuccess(String s);
    public void onFailure(Throwable throwable, String s);
    public void onFinish();
}
