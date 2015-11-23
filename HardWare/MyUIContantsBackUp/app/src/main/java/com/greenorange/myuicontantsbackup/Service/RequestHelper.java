package com.greenorange.myuicontantsbackup.Service;

import android.text.TextUtils;

import com.greenorange.myuicontantsbackup.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by JasWorkSpace on 15/11/2.
 */
public class RequestHelper {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static boolean Request_Sync(String url, RequestParams baseRequestParam, final IRequestCallBack callBack) throws Exception {
        return Request_Async(new SyncHttpClient() {
            @Override
            public String onRequestFailed(Throwable throwable, String s) {
                if (callBack != null) {
                    callBack.onFailure(throwable, null);
                }
                return null;
            }
        }, url, baseRequestParam, callBack, true);
    }

    public static boolean Request_Async(String url, RequestParams baseRequestParam, final IRequestCallBack callBack) throws Exception{
        return Request_Async(client, url, baseRequestParam, callBack, true);
    }

    public static boolean Request_Async(AsyncHttpClient asyncHttpClient, String url, RequestParams baseRequestParam, final IRequestCallBack callBack, boolean alway) throws Exception {
        try{
            if(TextUtils.isEmpty(url)){
                //if(!URLUtil.isNetworkUrl(url))throw new Exception("invalid url !!!");//only judge it none.
                throw new Exception("invalid url !!!");
            }
            if(!alway && baseRequestParam == null){
                throw new Exception("RequestParams url !!!");
            }
            Log.d("Request_Async-->" + url + (baseRequestParam == null ? "" : ("?" + baseRequestParam.toString())));
            asyncHttpClient.post(url, baseRequestParam, new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    if(callBack != null){
                        callBack.onSuccess(s);
                    }
                }
                @Override
                public void onStart() {
                    super.onStart();
                    if(callBack != null){
                        callBack.onStart();
                    }
                }
                @Override
                public void onFinish() {
                    super.onFinish();
                    if(callBack != null){
                        callBack.onFinish();
                    }
                }
                @Override
                public void onFailure(Throwable throwable, String s) {
                    super.onFailure(throwable, s);
                    if(callBack != null){
                        callBack.onFailure(throwable, s);
                    }
                }
            });
            return true;
        }catch (Throwable e){e.printStackTrace();
            Log.d("RequestHelper  Request_Async fail -->" + e.toString());
        }
        throw new Exception("unknow fail");
    }

}
