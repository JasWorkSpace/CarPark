package com.greenorange.myuicontantsbackup.Service.V2;

import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.Service.IRequestCallBack;

/**
 * Created by JasWorkSpace on 15/11/16.
 */
public class RequestCallBack implements IRequestCallBack{
    private String response = "";
    private final static boolean DEBUG = false;
    @Override
    public void onStart() {
        if(DEBUG)Log.d("onStart");
    }
    @Override
    public void onSuccess(String s) {
        if(DEBUG)Log.d("onSuccess-->"+s);
        response = s;
    }
    @Override
    public void onFailure(Throwable throwable, String s) {
        throwable.printStackTrace();
        Log.i("RequestCallBack fail-->"+throwable.toString());
        if(DEBUG)Log.d("onFailure -->" + s);
    }
    @Override
    public void onFinish() {
        if(DEBUG)Log.d("onFinish");
    }
    public String getResponse(){
        return response;
    }
}
