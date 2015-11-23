package com.greenorange.myuiaccount.authenticator;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.R;
import com.greenorange.myuiaccount.Util.AndroidUtil;
import com.greenorange.myuiaccount.Util.SharePreferenceUtil;
import com.greenorange.myuiaccount.Util.Utils;
import com.greenorange.myuiaccount.Util.ViewUtils;
import com.greenorange.myuiaccount.service.RequestCallBack;
import com.greenorange.myuiaccount.service.V1.Response.LogainResponse;
import com.greenorange.myuiaccount.service.V1.ServiceV1API;
import com.greenorange.myuiaccount.service.V2.Config;
import com.greenorange.myuiaccount.service.V2.NetworkUtilities;
import com.greenorange.myuiaccount.service.V2.Request.LogainParam;
import com.greenorange.myuiaccount.service.V2.Response.BaseResponse;
import com.greenorange.myuiaccount.service.V2.Response.LegencyAccountState;
import com.greenorange.myuiaccount.service.V2.Response.UserInfo;
import com.greenorange.myuiaccount.service.V2.SecureUtil;
import com.greenorange.myuiaccount.service.V2.ServiceAPI;
import com.greenorange.myuiaccount.service.V2.ServiceCache;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;
import java.util.concurrent.atomic.AtomicInteger;


public class AuthenticatorActivity extends AccountAuthenticatorActivity {
    public final static String KEY_ACTIVITY_MODE = "KEY_ACTIVITY_MODE";
    public final static int ACTIVITY_MODE_LOGAIN      = 1;
    public final static int ACTIVITY_MODE_BINDACCOUNT = 2;
    private int  ACTIVITY_MODE = 0;
    public final static String KEY_LOGIN_PARAMS   = "KEY_LOGIN_PARAMS";
    private boolean isSupportActivityMode(int mode){
        return mode >= ACTIVITY_MODE_LOGAIN && mode <= ACTIVITY_MODE_BINDACCOUNT;
    }
    /** The Intent flag to confirm credentials. */
    public static final String PARAM_CONFIRM_CREDENTIALS = "confirmCredentials";

    /** The Intent extra to store password. */
    public static final String PARAM_PASSWORD = "password";

    /** The Intent extra to store username. */
    public static final String PARAM_USERNAME = "username";

    /** The Intent extra to store username. */
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";


    private AccountManager mAccountManager;

    /** Keep track of the login task so can cancel it if requested */
//    private UserLoginTask mAuthTask = null;

    /** Keep track of the progress dialog so we can dismiss it */
    private ProgressDialog mProgressDialog = null;
    private ProgressDialog getProgressDialog(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.ui_activity_authenticating));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                 //releaseLogainStateMachine();
                releaseLoginStateMachine();
            }
        });
        return dialog;
    }
    /**
     * If set we are just checking that the user knows their credentials; this
     * doesn't cause the user's password or authToken to be changed on the
     * device.
     */
    private Boolean mConfirmCredentials = false;

    //private String mPassword;

    private EditText mPasswordEdit;

    /** Was the original caller asking for an entirely new account? */
    protected boolean mRequestNewAccount = false;

    //private String mUsername;

    private EditText mUsernameEdit;

    private final static int REQUEST_ACTIVITY_REGISTER     = 100;
    private final static int REQUEST_ACTIVITY_FINDPASSWORD = 101;
    private final static int REQUEST_ACTIVITY_BINDACCOUNT  = 102;

    private boolean checkIntent(Intent intent){
        boolean issupportActivityMode = isSupportActivityMode(ACTIVITY_MODE);
        if(!issupportActivityMode)return false;
        if(ACTIVITY_MODE == ACTIVITY_MODE_BINDACCOUNT){
            //LOGINPARAMS loginparams = (LOGINPARAMS) intent.getSerializableExtra(KEY_LOGIN_PARAMS);
            LOGINPARAMS loginparams = new Gson().fromJson(intent.getStringExtra(KEY_LOGIN_PARAMS), LOGINPARAMS.class);
            Log.d("checkIntent-->" + loginparams.toString());
            if(!loginparams.isV1LogainFinish()){
                return false;
            }
            mLoginParams = loginparams;
        }
        return true;
    }
    @Override
    public void onCreate(Bundle icicle) {
        Log.i( "onCreate(" + icicle + ")");
        super.onCreate(icicle);

        mAccountManager = AccountManager.get(this);
        Log.i("loading data from Intent");
        final Intent intent = getIntent();
        ACTIVITY_MODE   = intent.getIntExtra(KEY_ACTIVITY_MODE, ACTIVITY_MODE);
        String username = intent.getStringExtra(PARAM_USERNAME);
        String password = intent.getStringExtra(PARAM_PASSWORD);
        if(!checkIntent(intent)){//we don,t support now.
            Log.i("data not aviable ");
            finish();return;
        }
        initActionBar();
        mRequestNewAccount  = TextUtils.isEmpty(username);
        mConfirmCredentials = intent.getBooleanExtra(PARAM_CONFIRM_CREDENTIALS, false);
        Log.i("    request new: " + mRequestNewAccount);
        setContentView(R.layout.activity_authenticatoractivity);
        mUsernameEdit = (EditText) findViewById(R.id.username_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        if (!TextUtils.isEmpty(username)) mUsernameEdit.setText(username);
        if(!TextUtils.isEmpty(password))  mPasswordEdit.setText(password);
        findViewById(R.id.register).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(Utils.createRegisterIntent(AuthenticatorActivity.this)
                                , REQUEST_ACTIVITY_REGISTER);
                    }
                }
        );
    }
    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            if(ACTIVITY_MODE == ACTIVITY_MODE_LOGAIN) {
                actionBar.setTitle(R.string.loginactivity_actionbar_title);
            }else{
                actionBar.setTitle(R.string.loginactivity_actionbar_bindtitle);
            }
        }
    }

    private final static int LOGIN_STATE_IDLE     = 0;
    private final static int LOGIN_STATE_PRE      = 1;
    private final static int LOGIN_STATE_LOGINV1  = 2;
    private final static int LOGIN_STATE_LOGINV2  = 3;
    private final static int LOGIN_STATE_FINISH   = 4;

    private final static int MSG_LOGIN_RELEASE    = 1;
    private final static int MSG_LOGIN_START      = 2;
    private final static int MSG_LOGIN_LOGINV1    = 3;
    private final static int MSG_LOGIN_LOGINV2    = 4;
    private final static int MSG_LOGIN_FINISH     = 5;
    private AtomicInteger mCurrentLoginState = new AtomicInteger(LOGIN_STATE_IDLE);
    private void releaseLoginStateMachine(){
        mCurrentLoginState.set(LOGIN_STATE_IDLE);
        mLogainHandler.sendEmptyMessage(MSG_LOGIN_RELEASE);
    }
    private void finishLoginStateMachine(){
        mLogainHandler.obtainMessage(MSG_LOGIN_FINISH).sendToTarget();
    }
    private void tranformLoginState(int state){
        mCurrentLoginState.set(state);
    }

    private synchronized void startLoginByUI(int version){
        Message m = mLogainHandler.obtainMessage(MSG_LOGIN_START);
        m.obj  = version;
        m.sendToTarget();
    }

    private synchronized boolean preLogain(int version){
        if( mProgressDialog != null){
            Log.d("preLogain fail for it has run !!!");
            return false;
        }
        LogainParam logainParam = checkLoginInput();
        if(logainParam != null && logainParam.checkValid()){
            int message = MSG_LOGIN_LOGINV1;
            if(version == LOGIN_STATE_LOGINV1){
                message = MSG_LOGIN_LOGINV1;
                mLoginParams = new LOGINPARAMS();
                mLoginParams.setmLogainParam(new LogainParam(logainParam));
                mLoginParams.setmV2LogainParam(new LogainParam(logainParam));
                mLogainHandler.obtainMessage(message).sendToTarget();
                return true;
            }else if(version == LOGIN_STATE_LOGINV2){
                message = MSG_LOGIN_LOGINV2;
//                if(ACTIVITY_MODE == ACTIVITY_MODE_BINDACCOUNT //we don,t judge it for progress.
//                        && mLoginParams != null && mLoginParams.isV1LogainFinish()){
                    mLoginParams.setmV2LogainParam(new LogainParam(logainParam));
                    mLogainHandler.obtainMessage(message).sendToTarget();
                    return true;
//                }
            }else{
                return false;
            }
        }
        return false;
    }
    private LOGINPARAMS mLoginParams;
    private class LOGINPARAMS{
        private LogainParam mLogainParam;
        private LogainParam mV2LogainParam;
        private String      uuid;
        private UserInfo    mUserInfo;
        private boolean     isV1LoginSuccess = false;
        private boolean     isV2LoginSuccess = false;
        private boolean     isBindSuccess    = false;

        private boolean isV1LogainFinish(){
            if(!isV1LoginSuccess
                    ||!isLogainParamValid()
                    ||TextUtils.isEmpty(uuid)){
                return false;
            }
            return true;
        }
        private boolean isLogainParamValid(){
            return (mLogainParam != null && mLogainParam.checkValid());
        }
        private boolean isV2ParamValid(){
            return (mV2LogainParam != null && mV2LogainParam.checkValid());
        }
        private boolean isV2LogainFinish(){
            return isV2ParamValid();
        }
        public LogainParam getmLogainParam() {
            return mLogainParam;
        }
        public void setmLogainParam(LogainParam mLogainParam) {
            this.mLogainParam = mLogainParam;
        }
        public UserInfo getmUserInfo() {
            return mUserInfo;
        }
        public void setmUserInfo(UserInfo mUserInfo) {
            this.mUserInfo = mUserInfo;
        }
        public String getUuid() {
            return uuid;
        }
        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public boolean isV1LoginSuccess() {
            return isV1LoginSuccess;
        }

        public void setV1LoginSuccess(boolean isV1LoginSuccess) {
            this.isV1LoginSuccess = isV1LoginSuccess;
        }

        public boolean isV2LoginSuccess() {
            return isV2LoginSuccess;
        }

        public void setV2LoginSuccess(boolean isV2LoginSuccess) {
            this.isV2LoginSuccess = isV2LoginSuccess;
        }

        public LogainParam getmV2LogainParam() {
            return mV2LogainParam;
        }

        public void setmV2LogainParam(LogainParam mV2LogainParam) {
            this.mV2LogainParam = mV2LogainParam;
        }

        public boolean isBindSuccess() {
            return isBindSuccess;
        }

        public void setBindSuccess(boolean isBindSuccess) {
            this.isBindSuccess = isBindSuccess;
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("LOGINPARAMS{")
                    .append("isV1LoginSuccess="+isV1LoginSuccess)
                    .append(", isV2LoginSuccess="+isV2LoginSuccess)
                    .append(", mLogainParam=" + (mLogainParam == null ? "" : mLogainParam.toString()))
                    .append(", mV2LogainParam="+(mV2LogainParam == null ? "" : mV2LogainParam.toString()))
                    .append(", mUserInfo=" + (mUserInfo == null ? "" : mUserInfo.toString()))
                    .append("}");
            return sb.toString();
        }
    }
    private Handler mLogainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("mLogainHandler mCurrentLoginState="+mCurrentLoginState.get() + ", msg="+msg.what);
            switch(msg.what){
                case MSG_LOGIN_RELEASE:{
                    mCurrentLoginState.set(LOGIN_STATE_IDLE);
//                    if(ACTIVITY_MODE == ACTIVITY_MODE_BINDACCOUNT) {
//                        mLoginParams.isV2LoginSuccess = false;
//                    }else{
//                        mLoginParams = null;
//                    }
                    hideProgress();
                }break;
                case MSG_LOGIN_START:{
                    if(mCurrentLoginState.get() == LOGIN_STATE_IDLE){
                        if(preLogain((Integer)msg.obj)){
                            tranformLoginState(LOGIN_STATE_PRE);
                            showProgress();
                        }
                    }
                }break;
                case MSG_LOGIN_LOGINV1:{
                    if(mCurrentLoginState.get() == LOGIN_STATE_PRE){
                        try{
                            if(logainV1AndGetUUID(mLoginParams)){
                                tranformLoginState(LOGIN_STATE_LOGINV1);
                                return;
                            }
                        }catch (Throwable e){
                            e.printStackTrace();
                            Log.d("MSG_LOGIN_LOGINV1 fail -->" + e.toString());
                        }
                        //we try to load use V2.
                        mLogainHandler.obtainMessage(MSG_LOGIN_LOGINV2).sendToTarget();
                    }
                }break;
                case MSG_LOGIN_LOGINV2:{
                    if(mCurrentLoginState.get() == LOGIN_STATE_PRE
                            || mCurrentLoginState.get() == LOGIN_STATE_LOGINV1){
                        try{
                            if(logainV2(mLoginParams)){
                                tranformLoginState(LOGIN_STATE_LOGINV2);
                                return;
                            }
                        }catch (Throwable e){
                            e.printStackTrace();
                            Log.d("MSG_LOGIN_LOGINV2 fail -->" + e.toString());
                        }
                        finishLoginStateMachine();
                    }
                }break;
                case MSG_LOGIN_FINISH:{
                    if(mCurrentLoginState.get() == LOGIN_STATE_PRE
                        || mCurrentLoginState.get() == LOGIN_STATE_LOGINV1
                        || mCurrentLoginState.get() == LOGIN_STATE_LOGINV2){
                        Log.d("MSG_LOGIN_FINISH-->mLoginParams=" + (mLoginParams==null?"":mLoginParams.toString()));
                        releaseLoginStateMachine();
                        if(!handlerLoginFinish()){
                            showLogainFail();
                        }
                    }
                }break;
            }
        }
    };
    private boolean handlerLoginFinish(){
        if(mLoginParams == null)return false;
        if(ACTIVITY_MODE == ACTIVITY_MODE_LOGAIN){//maybe have something wrong
            if(mLoginParams.isV1LogainFinish()){
                if(mLoginParams.isBindSuccess){
                    if (mLoginParams.isV2LogainFinish()) {//success
                        return finishLogin(mLoginParams);
                    }
                }
            }else if(mLoginParams.isV2LogainFinish()){//success
                return finishLogin(mLoginParams);
            }
        }else if(ACTIVITY_MODE == ACTIVITY_MODE_BINDACCOUNT){
            if(mLoginParams.isV2LogainFinish()){
                return finishBind(mLoginParams);
            }
        }else {
            return false;
        }
        return false;
    }
    private boolean finishBind(LOGINPARAMS loginparams){
        try{
            if(loginparams != null && loginparams.isV2LogainFinish()) {
                Intent intent = new Intent();
                intent.putExtra(KEY_LOGIN_PARAMS, new Gson().toJson(loginparams));
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("finishBind fail-->"+e.toString());
        }
        return false;
    }
    private boolean finishLogin(LOGINPARAMS loginparams){
        try{
            String authToken = new Gson().toJson(loginparams.getmUserInfo());
            finishLogin(authToken, loginparams.getmV2LogainParam());
            return true;
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("finishLogin fail-->"+e.toString());
        }
        return false;
    }
    private void finishLogin(String authToken, LogainParam logainParam) {
        Log.i( "finishLogin()");
        if(ACTIVITY_MODE == ACTIVITY_MODE_LOGAIN) {
            if (mRequestNewAccount) {//add new account.
                AccountHelper.removeAllAccount(AuthenticatorActivity.this);
                AccountHelper.addAccount(AuthenticatorActivity.this, logainParam);
                ServiceCache.getInstance(AuthenticatorActivity.this).setCache(SharePreferenceUtil.KEY_USERINFO, authToken);
            } else {//only update it
                AccountHelper.updateAccount(AuthenticatorActivity.this, logainParam);
            }
        }
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, logainParam.getUsername());
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AuthenticatorConfig.ACCOUNT_TYPE);
        try {
            intent.putExtra(AccountManager.KEY_AUTHTOKEN,//encypt and encoding
                    ServiceHelper.getEncodeParam(ServiceHelper.getClientEncrypt(authToken)));
        }catch (Throwable e){e.printStackTrace();
            Log.d("finishLogin KEY_AUTHTOKEN fail-->"+e.toString());
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
    //////////////////////////////////////////////////////
    private  boolean bindV1AndV2(final LOGINPARAMS param){
        try{
            Log.d("bindV1AndV2 uuid=" + param.getUuid() + ", userInfo=" + param.getmUserInfo().toString());
            return ServiceAPI.API_MYUI_Request_Async(
                    ServiceAPI.API_MYUI_getBindLegencyAccountRequestParams(param.getUuid(), param.getmUserInfo().uid)
                    ,new RequestCallBack() {
                        String response = null;
                        @Override
                        public void onStart() {}
                        @Override
                        public void onSuccess(String s) {
                            Log.d("bindV1AndV2 onSuccess-->"+s);
                            if(!TextUtils.isEmpty(s)) {
                                response = s;
                            }
                        }
                        @Override
                        public void onFailure(Throwable throwable, String s) {
                            Log.d("bindV1AndV2 onFailure " + throwable.toString() + ", "+s);
                        }
                        @Override
                        public void onFinish() {
                            try {
                                if (!TextUtils.isEmpty(response)) {
                                    BaseResponse baseResponse = new Gson().fromJson(response, BaseResponse.class);
                                    if(baseResponse.checkValid()){
                                        param.isBindSuccess = true;
                                    }
                                }
                            }catch (Throwable e){
                                e.printStackTrace();
                                Log.d("bindV1AndV2 fail-->"+e.toString());
                            }
                            finishLoginStateMachine();
                        }
                    });
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("bindAccount fail -->"+e.toString());
        }
        return false;
    }
    private synchronized boolean logainV2(final LOGINPARAMS param){
        try{
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LogainParam logainParam = param.getmV2LogainParam();
                        UserInfo userInfo = NetworkUtilities.getUserInfoWithNoCache(AuthenticatorActivity.this,
                                logainParam.getUsername(), logainParam.getPassword());
                        Log.d("logainV2 -->" + (userInfo == null ? "" : userInfo.toString()));
                        if(userInfo != null) {
                            param.setmUserInfo(userInfo);
                            param.isV2LoginSuccess = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("logainV2 fail1 -->" + e.toString());
                        param.isV2LoginSuccess = false;
                    }
                    finishLoginStateMachine();
                }
            });
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Log.d("logainV2 fail2 -->" + ex.toString());
                    finishLoginStateMachine();
                }
            });
            thread.start();
            return true;
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("logainV2 fail -->"+e.toString());
        }
        return false;
    }
    /////////////////////////////////////////////////////////////////////
    private boolean logainV1AndGetUUID(final LOGINPARAMS param){
        try {
            com.greenorange.myuiaccount.service.V1.Request.LogainParam logainParam1 = new com.greenorange.myuiaccount.service.V1.Request.LogainParam();
            LogainParam logainParam = param.getmLogainParam();
            logainParam1.setUsername(logainParam.getUsername());
            logainParam1.setPassword(logainParam.getPassword());
            logainParam1.setImei(AndroidUtil.getIMEI(AuthenticatorActivity.this));
            ServiceV1API.API_MYUI_Logain_async(logainParam1, new RequestCallBack() {
                private String response = null;
                @Override
                public void onStart() {}
                @Override
                public void onSuccess(String s) {
                    Log.d("logainV1AndGetUUID onSuccess-->" + s);
                    response = s;
                }
                @Override
                public void onFailure(Throwable throwable, String s) {
                    Log.d("logainV1AndGetUUID onFailure fail2-->"+throwable.toString()+", "+s);
                }
                @Override
                public void onFinish() {
                    try{
                        if(!TextUtils.isEmpty(response)){
                            Gson gson = new Gson();
                            LogainResponse logainResponse = gson.fromJson(response, LogainResponse.class);
                            if(logainResponse.checkValid()){
                                param.setUuid(logainResponse.getAppSecret());
                                param.isV1LoginSuccess = true;
                                if(checkV1Binded(param)) {
                                    return;
                                }
                            }
                        }
                    }catch (Throwable e){
                        e.printStackTrace();
                        Log.d("logainV1AndGetUUID fail2-->"+e.toString());
                    }
                    //we try to load use V2.
                    param.isV1LoginSuccess = false;//skep v1 chaek
                    mLogainHandler.obtainMessage(MSG_LOGIN_LOGINV2).sendToTarget();
                }
            });
            return true;
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("logainV1AndGetUUID fail -->"+e.toString());
        }
        return false;
    }
    private boolean checkV1Binded(final LOGINPARAMS param){
        try{
            Log.d("checkV1Binded");
            return ServiceAPI.API_MYUI_Request_Async(
                    ServiceAPI.API_MYUI_getCheckLegencyAccountStateRequestParams(param.getUuid())
                    , new RequestCallBack() {
                        LegencyAccountState legencyAccountState = null;
                        @Override
                        public void onStart() {}
                        @Override
                        public void onSuccess(String s) {
                            try{
                                Log.d("checkV1Binded onSuccess-->" + s);
                                String response = ServiceAPI.API_MYUI_ParserBaseResponse(new Gson().fromJson(s, BaseResponse.class));
                                legencyAccountState = new LegencyAccountState();
                                legencyAccountState.setState(response);
                            }catch (Throwable e){e.printStackTrace();
                                Log.d("checkV1Binded onSuccess fail1-->"+e.toString());
                            }
                        }
                        @Override
                        public void onFailure(Throwable throwable, String s) {}
                        @Override
                        public void onFinish() {
                            if(legencyAccountState != null
                                    && legencyAccountState.isNeedBind()){
                                Toast.makeText(AuthenticatorActivity.this, R.string.loginactivity_notify_bindaccount, Toast.LENGTH_SHORT).show();
                                Intent intent = Utils.createBindLegencyAccountIntent(AuthenticatorActivity.this);
                                intent.putExtra(KEY_LOGIN_PARAMS, new Gson().toJson(param));
                                startActivityForResult(intent, REQUEST_ACTIVITY_BINDACCOUNT);
                                return;
                            }
                            //finishLoginStateMachine();
                            //we try to load use V2.
                            param.isV1LoginSuccess = false;
                            mLogainHandler.obtainMessage(MSG_LOGIN_LOGINV2).sendToTarget();
                        }
                    }
            );
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("checkV1Binded fail -->"+e.toString());
        }
        return false;
    }

    private synchronized void handlerCheckAndLogain(){
        if(!(ACTIVITY_MODE == ACTIVITY_MODE_LOGAIN
                || ACTIVITY_MODE == ACTIVITY_MODE_BINDACCOUNT))return;
        if(mProgressDialog != null){
            Log.d("handlerCheckAndLogain fail for mProgressDialog != null");
            return;
        }
        if(ACTIVITY_MODE == ACTIVITY_MODE_LOGAIN) {
            startLoginByUI(LOGIN_STATE_LOGINV1);
        }else if(ACTIVITY_MODE == ACTIVITY_MODE_BINDACCOUNT){
            startLoginByUI(LOGIN_STATE_LOGINV2);
        }
    }

    private LogainParam checkLoginInput(){
        String username = mUsernameEdit.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            showErrorOnEditView(mUsernameEdit, AuthenticatorActivity.this.getString(R.string.loginactivity_invalid_loginname));
            return null;
        }
        String password = mPasswordEdit.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            showErrorOnEditView(mPasswordEdit, AuthenticatorActivity.this.getString(R.string.loginactivity_invalid_password));
            return null;
        }
        // Show a progress dialog, and kick off a background task to perform
        // the user login attempt.
        LogainParam logainParam = new LogainParam();
        logainParam.setUsername(username);
        logainParam.setPassword(password);
        logainParam.setEnv(ServiceHelper.getEnvParam(AuthenticatorActivity.this));
        return logainParam;
    }
    private void showErrorOnEditView(EditText view, String message){
        ViewUtils.showErrorOnEditView(AuthenticatorActivity.this, view, message);
    }
    private void finishConfirmCredentials(boolean result, LogainParam logainParam) {
        Log.i("finishConfirmCredentials()");
        if(ACTIVITY_MODE == ACTIVITY_MODE_LOGAIN) {
            AccountHelper.updateAccount(AuthenticatorActivity.this, logainParam);
        }
        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Shows the progress UI for a lengthy operation.
     */
    private void showProgress() {
        if(mProgressDialog == null){
            mProgressDialog = getProgressDialog();
        }
        if(mProgressDialog!= null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    /**
     * Hides the progress UI for a lengthy operation.
     */
    private void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }


    /////////////////////////////////////
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_ACTIVITY_REGISTER:
            case REQUEST_ACTIVITY_FINDPASSWORD:{
                if(resultCode != RESULT_OK)return;
                String account  = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                String password = data.getStringExtra(AccountManager.KEY_PASSWORD);
                String type     = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
                if(TextUtils.equals(type, AuthenticatorConfig.ACCOUNT_TYPE)){
                    if(!TextUtils.isEmpty(account)){
                        mUsernameEdit.setText(account);
                    }
                    if(!TextUtils.isEmpty(password)){
                        try {
                            mPasswordEdit.setText(SecureUtil.decryptDES(password, Config.getKey()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }break;
            case REQUEST_ACTIVITY_BINDACCOUNT:{
                if(resultCode == RESULT_OK && mLoginParams!= null && mLoginParams.isV1LogainFinish()){
                    LOGINPARAMS loginparams = new Gson().fromJson(data.getStringExtra(KEY_LOGIN_PARAMS), LOGINPARAMS.class);
                    Log.d("REQUEST_ACTIVITY_BINDACCOUNT-->"+loginparams.toString());
                    if(loginparams.isV2LogainFinish()){
                        mLoginParams = loginparams;
                        if(bindV1AndV2(mLoginParams)){
                            return;
                        }
                    }
                }
                finishLoginStateMachine();
            }break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authenticatoractivity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logain) {
            handlerCheckAndLogain();
            return true;
        }else if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////
    private Dialog mLogainFailDialog;
    private void showLogainFail(){
        if(mLogainFailDialog == null){
            mLogainFailDialog = getBackPasswordDialog();
            mLogainFailDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mLogainFailDialog = null;
                }
            });
        }
        if(mLogainFailDialog != null && !mLogainFailDialog.isShowing()){
            mLogainFailDialog.show();
        }
    }
    private void hideLogainFail(){
        if(mLogainFailDialog != null && mLogainFailDialog.isShowing()){
            mLogainFailDialog.dismiss();
        }
        mLogainFailDialog = null;
    }
    private Dialog getBackPasswordDialog(){
        return new AlertDialog.Builder(AuthenticatorActivity.this)
                .setMessage(R.string.registeractivity_logainfail)
                .setNegativeButton(R.string.registeractivity_logainfail_backpassword, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          Intent intent = Utils.createFindPassWordIntent(AuthenticatorActivity.this);
                          startActivityForResult(intent, REQUEST_ACTIVITY_FINDPASSWORD);
                    }
                })
                .create();
    }
}
