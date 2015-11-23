package com.greenorange.myuiaccount.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.greenorange.myuiaccount.authenticator.AuthenticationRegisterActivity;
import com.greenorange.myuiaccount.authenticator.AuthenticatorActivity;
import com.greenorange.myuiaccount.authenticator.AuthenticatorUserInfo;
import com.greenorange.myuiaccount.service.V2.Config;

/**
 * Created by JasWorkSpace on 15/10/22.
 */
public class Utils {

    public static Intent createLogainIntent(Context context){
        Intent intent = new Intent(context, AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.KEY_ACTIVITY_MODE, AuthenticatorActivity.ACTIVITY_MODE_LOGAIN);
        return intent;
    }
    public static Intent createBindLegencyAccountIntent(Context context){
        Intent intent = new Intent(context, AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.KEY_ACTIVITY_MODE, AuthenticatorActivity.ACTIVITY_MODE_BINDACCOUNT);
        return intent;
    }
    public static Intent createFindPassWordIntent(Context context){
        return new Intent(Intent.ACTION_VIEW, Uri.parse(Config.getFindpsaawordUrl()));
    }
    public static Intent createRegisterIntent(Context context){
        Intent intent = new Intent(context, AuthenticationRegisterActivity.class);
        intent.putExtra(AuthenticationRegisterActivity.KEY_ACTIVITY_MODE, AuthenticationRegisterActivity.ACTIVITY_MODE_REGISTER);
        return intent;
    }
    public static Intent createUserInfoIntent(Context context){
        return new Intent(context, AuthenticatorUserInfo.class);
    }
    public static Intent createUserChangePasswordIntent(Context context){
        Intent intent = new Intent(context, AuthenticationRegisterActivity.class);
        intent.putExtra(AuthenticationRegisterActivity.KEY_ACTIVITY_MODE, AuthenticationRegisterActivity.ACTIVITY_MODE_CHANGEPASSWORD);
        return intent;
    }
    ///////////////////////////
    public static String getString(String string){
        return TextUtils.isEmpty(string) ? "" : string;
    }
}
