package com.greenorange.myuiaccount.authenticator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.ContactsContract;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.service.V2.Request.ChangePasswordParam;
import com.greenorange.myuiaccount.service.V2.Request.LogainParam;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;

/**
 * Created by JasWorkSpace on 15/10/22.
 */
public class AccountHelper {

    public static void updateAccount(Context context, LogainParam logainParam){
        if(logainParam != null && logainParam.checkValid()){
            try {
                Account account = new Account(logainParam.getUsername(), AuthenticatorConfig.ACCOUNT_TYPE);
                AccountManager accountManager = AccountManager.get(context);
                accountManager.setPassword(account, ServiceHelper.getEncrypt(logainParam.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("AccountHelper updateAccount fail --> " + e.toString());
            }
        }
    }
    public static boolean isHasMyUIAccountLogained(Context context){
        try {
            AccountManager accountManager = AccountManager.get(context);
            Account[] accounts = accountManager.getAccountsByType(AuthenticatorConfig.ACCOUNT_TYPE);
            if(accounts != null){
                for(Account account : accounts) {
                    try {
                        if (AccountHelper.isMyUIAccount(context, account)) return true;
                        AccountHelper.removeAccount(context, account.name);
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
                // app should be same UID with Authentication
                String userdata = accountManager.getUserData(account, AuthenticatorConfig.USERDATA_KEY);
                Log.d("isMyUIAccount-->"+userdata);
                AuthenticatorUserData authenticatorUserData = new Gson().fromJson(
                        ServiceHelper.getClientDecrypt(ServiceHelper.getDecodeParam(userdata)), AuthenticatorUserData.class
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
    public static void updateAccount(Context context, ChangePasswordParam changePassword){
        if(changePassword != null && changePassword.checkValid()){
            try {
                Account account = new Account(changePassword.getAccount(), AuthenticatorConfig.ACCOUNT_TYPE);
                AccountManager accountManager = AccountManager.get(context);
                accountManager.setPassword(account, ServiceHelper.getEncrypt(changePassword.getNewPassword()));
                setUserData(context, account);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("AccountHelper updateAccount fail --> " + e.toString());
            }
        }
    }
    public static void setUserData(Context context, Account account){
        try{
            AuthenticatorUserData authenticatorUserData = new AuthenticatorUserData(account.name, "12345678");
            AccountManager accountManager = AccountManager.get(context);
            String userdata = ServiceHelper.getEncodeParam(
                    ServiceHelper.getClientEncrypt(authenticatorUserData.getUserData()));
            Log.d("setUserData-->"+userdata);
            accountManager.setUserData(account, AuthenticatorConfig.USERDATA_KEY, userdata);
        }catch (Throwable e){e.printStackTrace();Log.d("AccountHelper setUserData fail --> " + e.toString());}
    }
    public static void addAccount(Context context, LogainParam logainParam){
        if(logainParam != null && logainParam.checkValid()){
            try {
                Account account = new Account(logainParam.getUsername(), AuthenticatorConfig.ACCOUNT_TYPE);
                AccountManager accountManager = AccountManager.get(context);
                accountManager.addAccountExplicitly(account, ServiceHelper.getEncrypt(logainParam.getPassword()), null);
                setUserData(context, account);
                // Set contacts sync for this account.
                ContentResolver.setSyncAutomatically(account, ContactsContract.AUTHORITY, true);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("AccountHelper addAccount fail --> " + e.toString());
            }
        }
    }

    public static void removeAllAccount(Context context){
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(AuthenticatorConfig.ACCOUNT_TYPE);
        if(accounts != null){
            for(Account account : accounts){//its aync
                try {
                    accountManager.removeAccount(account, null, null);
                }catch (Throwable e){
                    e.printStackTrace();
                    Log.d("AccountHelper removeAllAccount fail -->"+e.toString());
                }
            }
        }
    }

    public static boolean removeAccount(Context context, String usetName){
        try {
            if (!TextUtils.isEmpty(usetName)) {
                AccountManager accountManager = AccountManager.get(context);
                accountManager.removeAccount(new Account(usetName, AuthenticatorConfig.ACCOUNT_TYPE), null, null);
                return true;
            }
        }catch (Throwable e){e.printStackTrace();Log.d("AccountHelper removeAccount fail -->"+e.toString());}
        return false;
    }
}
