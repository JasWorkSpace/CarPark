package com.greenorange.myuiaccount;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.greenorange.myuiaccount.Util.IPUtil;
import com.greenorange.myuiaccount.authenticator.AuthenticatorConfig;
import com.greenorange.myuiaccount.service.V1.Request.LogainParam;
import com.greenorange.myuiaccount.service.V2.NetworkUtilities;
import com.greenorange.myuiaccount.service.V2.ServiceAPI;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


public class MainActivity extends Activity {
    private TextView ts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ts = (TextView) findViewById(R.id.text);
        ts.setText(getAccountManager());
        //getVolley();
        //getRequest();
        new ss().execute();
    }
    private class ss extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            return testToken() + " \n IP-->" + IPUtil.getIpFromNetWork() +"\n RESP-->"+getru();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ts.setText(ts.getText()+"\n"+ s);
        }
    }
    private String testToken(){
                AccountManager accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
                Account[] accounts = accountManager.getAccountsByType(AuthenticatorConfig.ACCOUNT_TYPE);
                if(accounts != null){
                    for(Account account : accounts){
                        try {
                            return "token=" + accountManager.blockingGetAuthToken(account, AuthenticatorConfig.AUTHTOKEN_TYPE, true);
                        } catch (OperationCanceledException e) {
                            e.printStackTrace();
                            Log.d("testToken OperationCanceledException -->"+e.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("testToken IOException -->"+e.toString());
                        } catch (AuthenticatorException e) {
                            e.printStackTrace();
                            Log.d("testToken OperationCanceledException -->"+e.toString());
                        }
                    }
                }else{
                    Log.d("testToken account is null");
                }
        return null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getAccountManager(){
        AccountManager accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccounts();
        if(accounts == null)return"get bull ";
        String result = "";
        for(Account account : accounts){
            result += "{";
            result += account.toString();
            result += "}";
        }
        return result;
    }

//    private String getAuthToken(){
//        AccountManager accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
//        Account[] accounts = accountManager.getAccountsByType(com.greenorange.myuiaccount.service.Config.ACCOUNT_TYPE);
//        if(accounts != null){
//            for(Account account : accounts){
//                try {
//                    Bundle bundle = accountManager.getAuthToken(account, Config.AUTHTOKEN_TYPE, true, null, null).getResult();
//                } catch (OperationCanceledException e) {
//                    Log.d("OperationCanceledException -->"+ e.toString());
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    Log.d("IOException -->"+ e.toString());
//                    e.printStackTrace();
//                } catch (AuthenticatorException e) {
//                    Log.d("AuthenticatorException -->"+ e.toString());
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private String getru(){
        String url = "http://api1.qingcheng.com/contactsbackup/login?username=15000790653&password=123456&imei=";
        InputStream inputStream = null;
        try{
            HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
            connection.setConnectTimeout(200);//
            connection.setRequestMethod("POST");
            inputStream = connection.getInputStream();
            String response = ServiceHelper.ConvertStream2String(inputStream);
            Log.d("response-->"+response);
            return response;
        }catch (Throwable e){e.printStackTrace();Log.d("geturl fail-->");}finally {
            try{inputStream.close();}catch (Throwable e){}
        }
        return "fail ";
    }
    private void getVolley(){
        VolleyLog.TAG = "MyUIAccount";
        String url = "http://api1.qingcheng.com/contactsbackup/login";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Log.d("getVolley onResponse-->"+s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("getVolley fail-->"+(volleyError==null ? "is null !!!" : volleyError.toString()));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("getVolley getParams-->");
                Map<String, String> requestParams = new HashMap<String, String>();
                requestParams.put("username", "15000790653");
                requestParams.put("password", "123456");
                requestParams.put("imei", "");
                return requestParams;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    private void getRequest(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = "http://api1.qingcheng.com/contactsbackup/login";
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", "15000790653");
        requestParams.put("password", "123456");
        requestParams.put("imei", "");
        asyncHttpClient.post(url, requestParams, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.d("asyncHttpClient onSuccess -->"+s);
            }
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Log.d("asyncHttpClient onSuccess2 -->"+i+", "+s);
            }
            @Override
            public void onFinish() {
                super.onFinish();
                Log.d("asyncHttpClient onFinish -->");
            }
            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                Log.d("asyncHttpClient onFailure -->"+s +", "+(throwable == null ? "" :throwable.toString()));
            }
            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Log.d("asyncHttpClient onFailure2 -->"+(throwable==null?"":throwable.toString()));
            }
            @Override
            public void onStart() {
                super.onStart();
                Log.d("asyncHttpClient onStart -->");
            }
        });
    }
    private void syRequest(){
        String url = "http://api1.qingcheng.com/contactsbackup/login";
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", "15000790653");
        requestParams.put("password", "123456");
        requestParams.put("imei", "");
        SyncHttpClient syncHttpClient = new SyncHttpClient() {
            @Override
            public String onRequestFailed(Throwable throwable, String s) {
                Log.d("onRequestFailed onRequestFailed -->"+s+", "+(throwable == null ? "" : throwable.toString()));
                return null;
            }
        };
        Log.d("syRequest start start start -->");
        syncHttpClient.post(url, requestParams, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.d("syRequest onSuccess -->"+s);
            }
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Log.d("syRequest onSuccess2 -->"+i+", "+s);
            }
            @Override
            public void onFinish() {
                super.onFinish();
                Log.d("syRequest onFinish -->");
            }
            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                Log.d("syRequest onFailure -->"+s +", "+(throwable == null ? "" :throwable.toString()));
            }
            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Log.d("syRequest onFailure2 -->"+(throwable==null?"":throwable.toString()));
            }
            @Override
            public void onStart() {
                super.onStart();
                Log.d("syRequest onStart -->");
            }
        });
        Log.d("syRequest end end end -->");
    }
}
