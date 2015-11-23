package com.greenorange.myuiaccount.authenticator;

import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.R;
import com.greenorange.myuiaccount.Util.ViewUtils;
import com.greenorange.myuiaccount.service.V2.Config;
import com.greenorange.myuiaccount.service.V2.Request.ChangePasswordParam;
import com.greenorange.myuiaccount.service.V2.Request.RegisterParam;
import com.greenorange.myuiaccount.service.V2.SecureUtil;
import com.greenorange.myuiaccount.service.V2.ServiceAPI;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;


/**
 * Created by JasWorkSpace on 15/10/16.
 */
public class AuthenticationRegisterActivity extends Activity implements TextWatcher {
    public final static String KEY_ACTIVITY_MODE = "KEY_ACTIVITY_MODE";
    public final static int ACTIVITY_MODE_REGISTER = 1;
    public final static int ACTIVITY_MODE_CHANGEPASSWORD = 2;
    private int ACTIVITY_MODE = 0;
    private EditText username;
    private EditText password;
    private EditText passwordconfirm;
    private EditText auth;
    private TextView authSend;
    private String   authValue;
    private boolean isAvilabeActivityMode(){
        return ACTIVITY_MODE_REGISTER<= ACTIVITY_MODE && ACTIVITY_MODE <= ACTIVITY_MODE_CHANGEPASSWORD;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent == null){finish();return;}
        ACTIVITY_MODE = intent.getIntExtra(KEY_ACTIVITY_MODE, ACTIVITY_MODE);
        if(!isAvilabeActivityMode()){finish();return;}
        setContentView(R.layout.activity_authenticationregisteractivity);
        initActionBar();
        initResource();
    }
    private void initResource(){
        username = (EditText) findViewById(R.id.username_edit);
        username.addTextChangedListener(this);
        password = (EditText) findViewById(R.id.password_edit);
        passwordconfirm = (EditText) findViewById(R.id.passwordconfirm_edit);
        if(ACTIVITY_MODE == ACTIVITY_MODE_CHANGEPASSWORD){
            TextView passwordconfirmtitle = (TextView) findViewById(R.id.checkpassword_title);
            passwordconfirmtitle.setText(R.string.registeractivity_checkpassword_new);
            passwordconfirm.setHint(R.string.registeractivity_hint_checkpassword_new);
        }
        ((CheckBox)findViewById(R.id.passwordconfirm_checkbox)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changepasswordinputmethod(password, isChecked);
                changepasswordinputmethod(passwordconfirm, isChecked);
            }
        });
        auth     = (EditText) findViewById(R.id.auth_edit);
        authSend = (TextView) findViewById(R.id.auth_take);
        authSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = username.getText().toString().trim();
                if(ServiceHelper.isMebliePhoneNumberValid(account) && !mCountDownTimer.isRunning){
                    mCountDownTimer.startCount();
                    sendMessage(account);
                }
            }
        });
        if(ACTIVITY_MODE == ACTIVITY_MODE_CHANGEPASSWORD){
            auth.setVisibility(View.GONE);
            authSend.setVisibility(View.GONE);
            findViewById(R.id.auth_title).setVisibility(View.GONE);
        }
    }
    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.registeractivity_actionbar_title);
        }
    }
    private void sendMessage(final String account){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {//send sms
                    ServiceAPI.API_MYUI_SendSMS(account,
                            AuthenticationRegisterActivity.this.getString(R.string.registeractivity_send_sms,
                                    authValue = ServiceHelper.getAuth()));
                }catch (Throwable e){e.printStackTrace();}
            }
        }).start();
    }
    private void changepasswordinputmethod(EditText editText, boolean showpassword){
        editText.setInputType(showpassword
                    ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    private void setSendSMSEnable(boolean enable){
        if(enable && !mCountDownTimer.isRunning){
            try {
                mCountDownTimer.cancel();
            }catch (Throwable e){e.printStackTrace();}
            authSend.setEnabled(true);
        }else{
            authSend.setEnabled(false);
        }
    }
    ////////////////////////////////////
    private final static int DIALOG_REGISTER = 0;
    private final static int DIALOG_CHANGEPASSWORD = 1;
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        switch(id){
            case DIALOG_REGISTER:{
                hideProgress();
                mProgressDialog = getRegisterDialog();
                return mProgressDialog;
            }
            case DIALOG_CHANGEPASSWORD:{
                hideProgress();
                mProgressDialog = getChangePassWordDialog();
                return mProgressDialog;
            }
        }
        return null;
    }
    private UserChangePassWord mChangetask = null;
    private ProgressDialog getChangePassWordDialog(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.registeractivity_changepasswording));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (mChangetask != null) {
                    mChangetask.cancel(true);
                }
            }
        });
        return dialog;
    }
    ///////////////////////////////////////////////////////////////
    /** Keep track of the login task so can cancel it if requested */
    private UserRegisterTask mAuthTask = null;
    /** Keep track of the progress dialog so we can dismiss it */
    private ProgressDialog mProgressDialog = null;
    private void showProgress(int id) {
        showDialog(id);
    }
    private ProgressDialog getRegisterDialog(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.registeractivity_registering));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (mAuthTask != null) {
                    mAuthTask.cancel(true);
                }
            }
        });
        return dialog;
    }
    private void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    private void handleRegister(){
        String account = username.getText().toString().trim();
        if(!ServiceHelper.isAccountValid(account)){
            ViewUtils.showErrorOnEditView(AuthenticationRegisterActivity.this, username,
                    AuthenticationRegisterActivity.this.getString(R.string.loginactivity_invalid_loginname));
            return;
        }
        String passwordinput = password.getText().toString().trim();
        String passwordcon   = passwordconfirm.getText().toString().trim();
        if(!ServiceHelper.isPasswordValid(passwordinput)){
            ViewUtils.showErrorOnEditView(AuthenticationRegisterActivity.this, password,
                    AuthenticationRegisterActivity.this.getString(R.string.loginactivity_invalid_password));
            return;
        }
        if(!ServiceHelper.isPasswordValid(passwordcon)){
            ViewUtils.showErrorOnEditView(AuthenticationRegisterActivity.this, passwordconfirm,
                    AuthenticationRegisterActivity.this.getString(R.string.loginactivity_invalid_password));
            return;
        }
        if(ACTIVITY_MODE == ACTIVITY_MODE_REGISTER) {
            if(!TextUtils.equals(passwordinput, passwordcon)){
                ViewUtils.showErrorOnEditView(AuthenticationRegisterActivity.this, passwordconfirm,
                        AuthenticationRegisterActivity.this.getString(R.string.loginactivity_invalid_password));
                return;
            }
            String authinput = auth.getText().toString().trim();
            if(!TextUtils.equals(authinput, authValue)){
                ViewUtils.showErrorOnEditView(AuthenticationRegisterActivity.this, auth,
                        AuthenticationRegisterActivity.this.getString(R.string.registeractivity_invalid_auth));
                return;
            }
        }
        if(ACTIVITY_MODE == ACTIVITY_MODE_REGISTER) {
            RegisterParam mRegisterParam = new RegisterParam();
            mRegisterParam.setAccount(account);
            mRegisterParam.setMobileno(account);
            mRegisterParam.setPassword(passwordinput);
            showProgress(DIALOG_REGISTER);
            mAuthTask = new UserRegisterTask();
            mAuthTask.execute(mRegisterParam);
        }else if(ACTIVITY_MODE == ACTIVITY_MODE_CHANGEPASSWORD){
            ChangePasswordParam changePasswordParam = new ChangePasswordParam();
            changePasswordParam.setAccount(account);
            changePasswordParam.setOldPassword(passwordinput);
            changePasswordParam.setNewPassword(passwordcon);
            showProgress(DIALOG_CHANGEPASSWORD);
            mChangetask = new UserChangePassWord();
            mChangetask.execute(changePasswordParam);
        }
    }
    public class UserChangePassWord extends AsyncTask<ChangePasswordParam, Void, String>{
        private ChangePasswordParam mChangePassword;
        @Override
        protected String doInBackground(ChangePasswordParam... params) {
            try{
                return ServiceAPI.API_MYUI_ChangePassword(mChangePassword = params[0]);
            }catch (Throwable e){
                Log.i("UserChangePassWord fail-->" + e.toString());
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!TextUtils.isEmpty(s) && !TextUtils.equals("null", s)){
                Toast.makeText(AuthenticationRegisterActivity.this,
                        R.string.registeractivity_changepasswordsuccess, Toast.LENGTH_SHORT).show();
                if(mChangePassword != null && !isCancelled()) {
                    AccountHelper.updateAccount(AuthenticationRegisterActivity.this, mChangePassword);
                    finish();
                }
            }else{
                Toast.makeText(AuthenticationRegisterActivity.this,
                        R.string.registeractivity_changepasswordfail, Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onCancelled() {
            // If the action was canceled (by the user clicking the cancel
            // button in the progress dialog), then call back into the
            // activity to let it know.
            mChangetask = null;
            hideProgress();
        }
    }
    public class UserRegisterTask extends AsyncTask<RegisterParam, Void, String> {
        private RegisterParam mRegisterParam;
        @Override
        protected String doInBackground(RegisterParam... params) {
            try {
                return ServiceAPI.API_MYUI_Register(mRegisterParam = params[0]);
            } catch (Exception ex) {
                Log.i("UserRegisterTask fail-->" + ex.toString());
                return "";
            }
        }
        @Override
        protected void onPostExecute(final String authToken) {
            // On a successful authentication, call back into the Activity to
            // communicate the authToken (or null for an error).
            mAuthTask = null;
            hideProgress();
            Log.d("register-->"+authToken);
            if(!TextUtils.isEmpty(authToken) && !TextUtils.equals("null", authToken)&& !isCancelled()){
                if(mRegisterParam != null){
                    //final Account account = new Account(mRegisterParam.getAccount(), Config.ACCOUNT_TYPE);
                    final Intent intent   = new Intent();
                    intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mRegisterParam.getAccount());
                    intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AuthenticatorConfig.ACCOUNT_TYPE);
                    try {
                        intent.putExtra(AccountManager.KEY_PASSWORD,
                                SecureUtil.encryptDES(mRegisterParam.getPassword(), Config.getKey()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //intent.putExtra(AccountManager.KEY_AUTHTOKEN, authToken);
                    //setAccountAuthenticatorResult(intent.getExtras());
                    Toast.makeText(AuthenticationRegisterActivity.this,
                            R.string.registeractivity_registersuccess, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, intent);
                    finish();
                    return;
                }
            }
            Toast.makeText(AuthenticationRegisterActivity.this,
                    R.string.registeractivity_changepasswordfail, Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onCancelled() {
            // If the action was canceled (by the user clicking the cancel
            // button in the progress dialog), then call back into the
            // activity to let it know.
            mAuthTask = null;
            hideProgress();
        }
    }


    ///////////////////////////////////////////////////////////////
    private SendCountDownTimer mCountDownTimer = new SendCountDownTimer(60000, 1000);
    private class SendCountDownTimer extends CountDownTimer {
        public boolean isRunning = false;
        public SendCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        public void startCount(){
            isRunning = true;
            start();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            long time = millisUntilFinished/1000;
            if(time>=1) {
                authSend.setText(AuthenticationRegisterActivity.this.getString(R.string.registeractivity_resend_auth_time, time));
            }else{
                authSend.setText(AuthenticationRegisterActivity.this.getString(R.string.registeractivity_resend_auth));
            }
        }
        @Override
        public void onFinish() {
            authSend.setText(AuthenticationRegisterActivity.this.getString(R.string.registeractivity_resend_auth));
            isRunning = false;
            setSendSMSEnable(true);
        }
    };
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
         try{
             setSendSMSEnable(ServiceHelper.isMebliePhoneNumberValid(s.toString()));
         }catch (Throwable e){e.printStackTrace();}
    }
    @Override
    public void afterTextChanged(Editable s) {}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authenticatorregisteractivity, menu);
        if(ACTIVITY_MODE == ACTIVITY_MODE_CHANGEPASSWORD){
            menu.removeItem(R.id.action_register);
        }else if(ACTIVITY_MODE == ACTIVITY_MODE_REGISTER){
            menu.removeItem(R.id.action_changepassword);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_register || id == R.id.action_changepassword) {
            handleRegister();
            return true;
        }else if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
