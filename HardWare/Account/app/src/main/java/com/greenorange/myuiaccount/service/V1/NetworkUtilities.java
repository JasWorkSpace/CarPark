package com.greenorange.myuiaccount.service.V1;

import com.greenorange.myuiaccount.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JasWorkSpace on 15/10/14.
 */

public class NetworkUtilities {

    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;//30s

    public static HttpClient getHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();
        final HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        HttpConnectionParams.setSoTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        ConnManagerParams.setTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
        return httpClient;
    }

    public static InputStream getHttpInputStream(String url) throws Throwable{
        Log.i("NetworkUtilities getHttpInputStream url=" + url);
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        connection.setConnectTimeout(HTTP_REQUEST_TIMEOUT_MS);//
        connection.setRequestMethod("GET");
        return connection.getInputStream();
    }


}
