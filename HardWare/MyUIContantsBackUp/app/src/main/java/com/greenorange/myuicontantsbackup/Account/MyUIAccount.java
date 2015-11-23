package com.greenorange.myuicontantsbackup.Account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.greenorange.myuicontantsbackup.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by JasWorkSpace on 15/10/29.
 */
public class MyUIAccount implements OnAccountsUpdateListener {
    private final static boolean isSupportListenerAccountChange = false;
    //config by MyUIAccount.
    public static final String ACCOUNT_TYPE      = "com.greenorange.android.myuiaccount";
    public static final String AUTHTOKEN_TYPE    = "com.greenorange.android.myuiaccount.userinfo";
    public static final String USERDATA_KEY      = "com.greenorange.android.myuiaccount.userdata_key";
    public static final String ACTION_ADDACCOUNT = "com.greenorange.android.addmyuiaccount";
    private static MyUIAccount myUIAccount;
    public  synchronized static MyUIAccount getInstance(Context context){
        if(myUIAccount == null){
            myUIAccount = new MyUIAccount(context);
        }
        return myUIAccount;
    }
    private Handler mHandler = new Handler(){};
    private AccountManager mAccountManager;
    private Context mContext;
    public MyUIAccount(Context context){
        mContext        = context.getApplicationContext();
        mAccountManager = AccountManager.get(mContext);
        mMyUIAccount    = getBestAccountFromManager();
        try {
            if(isSupportListenerAccountChange) {
                mAccountManager.addOnAccountsUpdatedListener(MyUIAccount.this, mHandler, true);
            }
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("why it has already added " + e.toString());
        }
    }
    public String getAccountData(){
        Account account = getBestAccountFromManager();
        if(account != null) {
            return mAccountManager.getUserData(account, "");
        }
        return "";
    }
    public Account[] getAccountFromManager(){
        return mAccountManager.getAccountsByType(ACCOUNT_TYPE);
    }
    public Account getBestAccountFromManager(){
        Account[] accounts = getAccountFromManager();
        if(accounts != null && accounts.length > 0){
            for(int i=0; i<accounts.length; i++) {
                try {
                    if (isMyUIAccount(mContext, accounts[i])){
                        return accounts[i];
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    @Override
    public void onAccountsUpdated(Account[] accounts) {
        if(accounts != null && accounts.length >0){
            boolean needupdate = false;
            for(int i=0; i<accounts.length; i++){
                if(isMyUIAccount(mContext, accounts[i])){
                    needupdate = true;
                    break;
                }
            }
            if(needupdate){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        reloadAccount();
                    }
                }).start();
            }
        }
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("MyUIAccount{")
                .append("mMyUIAccount="+(mMyUIAccount==null?"":mMyUIAccount.toString()))
                .append(", mMyUIUserInfo="+(mMyUIUserInfo==null?"":mMyUIUserInfo.toString()))
                .append("}");
        return sb.toString();
    }

    /////////////////////////////////////////////
    //for myUI account
    private Account  mMyUIAccount;
    private UserInfo mMyUIUserInfo;
    private boolean reloadAccount = false;
    private void reloadAccount(){//
        if(reloadAccount)return;
        reloadAccount = true;
        mMyUIAccount  = getBestAccountFromManager();
        logainMyUIAccount();
        reloadAccount = false;
    }
    public Account  getMyUIAccount(){
        return mMyUIAccount;
    }
    public UserInfo getMyUIUserInfo(){
        return mMyUIUserInfo;
    }
    public boolean isLoaded(){
        return getMyUIAccount() != null;
    }
    public boolean logainMyUIAccount(Account account){
        try {
            String authtoken = mAccountManager.blockingGetAuthToken(account, AUTHTOKEN_TYPE, true);
            UserInfo userInfo = new Gson().fromJson(getClientDecrypt(getDecodeParam(authtoken)), UserInfo.class);
            mMyUIAccount      = account;
            mMyUIUserInfo     = userInfo;
            Log.d("logainMyUIAccount success!!! "+System.currentTimeMillis()+", mMyUIAccount＝"+mMyUIAccount.toString()+", mMyUIUserInfo="+mMyUIUserInfo.toString());
            notifyMyUIAccountChange(account, userInfo);
            return true;
        }catch (Throwable e){e.printStackTrace();
            Log.d("logainMyUIAccount fail-->"+account.toString()
                    +", "+e.toString());}
        return false;
    }
    public synchronized boolean logainMyUIAccount(){
        Log.d("logainMyUIAccount");
        Account[] accounts = getAccountFromManager();
        if(accounts != null){
            for(Account account : accounts){
                if(isMyUIAccount(mContext, account)
                        && logainMyUIAccount(account)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isHasMyUIAccountLogained(Context context){
        try {
            AccountManager accountManager = AccountManager.get(context);
            Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
            if(accounts != null){
                for(Account account : accounts) {
                    try {
                        if (isMyUIAccount(context, account)) return true;
                    }catch (Throwable e){Log.d("isHasMyUIAccountLogained fail1-->"+e.toString());}
                }
            }
        }catch (Throwable e){e.printStackTrace();Log.d("isHasMyUIAccountLogained fail2-->"+e.toString());}
        return false;
    }
    public static boolean isMyUIAccount(Context context, Account account){
        try {
            if (account != null) {
                AccountManager accountManager = AccountManager.get(context);
                // app should be same UID with Authentication. so this is null.
                String userdata = accountManager.getUserData(account, USERDATA_KEY);
                Log.d("isMyUIAccount-->"+userdata);
                AuthenticatorUserData authenticatorUserData = new Gson().fromJson(
                        getClientDecrypt(getDecodeParam(userdata)), AuthenticatorUserData.class
                );
                Log.d("isMyUIAccount-->" + authenticatorUserData.isViable()
                        + ", " + TextUtils.equals(authenticatorUserData.getMyUIName(), account.name)
                        + ", authenticatorUserData="+authenticatorUserData.toString());
                return authenticatorUserData.isViable()
                        && TextUtils.equals(authenticatorUserData.getMyUIName(), account.name);
            }
        }catch (Throwable e){e.printStackTrace();Log.d("AccountHelper isMyUIAccount fail --> " + e.toString());}
        return false;
    }
    public synchronized void startDefaultLogainAccount(Activity activity){
        Intent intent = new Intent(ACTION_ADDACCOUNT);
        intent.putExtra("KEY_ACTIVITY_MODE" , 1);
        activity.startActivity(intent);
    }
    ///////////////////////////////////////
    // myui Secure
    public  static final String ENCODING = "utf-8";
    private static final String Key = "(!@#$^*)";
    public static String getEncodeParam(String param) throws UnsupportedEncodingException {
        return URLEncoder.encode(param, ENCODING);
    }
    public static String getDecodeParam(String param) throws UnsupportedEncodingException{
        return URLDecoder.decode(param, ENCODING);
    }
    public static String getClientEncrypt(String string) throws Exception {
        return SecureUtil.encryptClientDES(string, Key);
    }
    public static String getClientDecrypt(String string) throws Exception {
        return SecureUtil.decryptClientDES(string, Key);
    }
    ////////////////////////////////////////
    //
    public interface MyUIAccountChangeListener{
        public void onMyUIAccountChange(Account account, UserInfo userInfo);
    }
    private ArrayList<MyUIAccountChangeListener> myUIAccountChangeListeners = new ArrayList<MyUIAccountChangeListener>();
    public boolean addMyUIAccountChangeListener(MyUIAccountChangeListener myUIAccountChangeListener){
        if(myUIAccountChangeListener != null
                && !myUIAccountChangeListeners.contains(myUIAccountChangeListener)){
            synchronized (myUIAccountChangeListeners) {
                return myUIAccountChangeListeners.add(myUIAccountChangeListener);
            }
        }
        return false;
    }
    public boolean removeMyUIAccountChangeListener(MyUIAccountChangeListener myUIAccountChangeListener){
        if(myUIAccountChangeListener != null){
            synchronized (myUIAccountChangeListeners) {
                return myUIAccountChangeListeners.remove(myUIAccountChangeListener);
            }
        }
        return false;
    }
    public void notifyMyUIAccountChange(Account account, UserInfo userInfo){
        Log.d("notifyMyUIAccountChange "+(myUIAccountChangeListeners==null?"" : myUIAccountChangeListeners.size()));
        if(myUIAccountChangeListeners != null && myUIAccountChangeListeners.size()>0){
            synchronized (myUIAccountChangeListeners){
                for(Iterator<MyUIAccountChangeListener> myUIAccountChangeListenerIterator = myUIAccountChangeListeners.iterator();
                        myUIAccountChangeListenerIterator.hasNext();){
                    MyUIAccountChangeListener myUIAccountChangeListener = myUIAccountChangeListenerIterator.next();
                    myUIAccountChangeListener.onMyUIAccountChange(account, userInfo);
                }
            }
        }
    }
    //////////////////////
    private static boolean isSafeIntent(Context context, Intent intent, String accountType){
        PackageManager pm = context.getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
        if(resolveInfo == null)return false;
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        AuthenticatorDescription[] authDescs = AccountManager.get(context).getAuthenticatorTypes();
        if(authDescs != null && authDescs.length > 0){
            for(int i = 0; i<authDescs.length; i++){
                AuthenticatorDescription authDesc = authDescs[i];
                if(!TextUtils.equals(accountType, authDesc.type))continue;
                try {
                    ApplicationInfo applicationInfo1  = pm.getApplicationInfo(authDesc.packageName, 0);
                    return activityInfo.exported || applicationInfo.uid == applicationInfo1.uid;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
