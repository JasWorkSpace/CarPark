package com.greenorange.myuiaccount.authenticator;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.R;
import com.greenorange.myuiaccount.Util.SharePreferenceUtil;
import com.greenorange.myuiaccount.Util.Utils;
import com.greenorange.myuiaccount.service.V2.Response.UserInfo;
import com.greenorange.myuiaccount.service.V2.ServiceCache;

/**
 * Created by JasWorkSpace on 15/10/22.
 */
public class AuthenticatorUserInfo extends PreferenceActivity {
    private UserInfo mUserinfo;
    private final static String KEY_PREFERENCE_NAME   = "userinfo_username";
    private final static String KEY_PREFERENCE_MOBILE = "userinfo_mobile";
    private final static String KEY_PREFERENCE_EMAIL  = "userinfo_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserinfo = getLocationUserInfo();
        if(mUserinfo == null){
            finish();
            return;
        }
        initActionBar();
        loadInfo();
    }

    private void loadInfo(){
        addPreferencesFromResource(R.xml.userinfo);
        getPreferenceScreen().findPreference(KEY_PREFERENCE_NAME).setSummary(
                TextUtils.isEmpty(mUserinfo.getRealname()) ? mUserinfo.getUsername() : mUserinfo.getRealname());
        getPreferenceScreen().findPreference(KEY_PREFERENCE_MOBILE).setSummary(
                mUserinfo.getMobile()
        );
        getPreferenceScreen().findPreference(KEY_PREFERENCE_EMAIL).setSummary(
                mUserinfo.getEmail()
        );
    }

    private UserInfo getLocationUserInfo(){
        ServiceCache serviceCache = ServiceCache.getInstance(this);
        String userinfo = serviceCache.getRealCache(SharePreferenceUtil.KEY_USERINFO);//
        Log.d("getLocationUserInfo userinfo="+userinfo);
        if(TextUtils.isEmpty(userinfo))return null;
        try{
            return new Gson().fromJson(userinfo, UserInfo.class);
        }catch (Throwable e){e.printStackTrace();
            Log.i("getLocationUserInfo fail-->"+e.toString());}
        return null;
    }
    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.userinfoactivity_userinfo1);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authenticatoruserinfo, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private final static int REQUEST_CHANGEPASSWORD = 100;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }else if(id == R.id.action_changepassword){
            startActivityForResult(Utils.createUserChangePasswordIntent(AuthenticatorUserInfo.this), REQUEST_CHANGEPASSWORD);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
