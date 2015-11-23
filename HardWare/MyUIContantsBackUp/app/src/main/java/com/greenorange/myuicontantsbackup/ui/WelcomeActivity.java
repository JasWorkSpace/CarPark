package com.greenorange.myuicontantsbackup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.greenorange.myuicontantsbackup.Account.MyUIAccount;
import com.greenorange.myuicontantsbackup.R;

/**
 * Created by JasWorkSpace on 15/11/18.
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!mMyUIAccount.isLoaded()
                && !mMyUIAccount.isHasMyUIAccountLogained(this))
            return;
        setContentView(R.layout.activity_welcome);
        if(mMyUIAccount.getMyUIUserInfo() == null){//we should logain
            loadMyUIAccount();
        }else{
            mHandler.sendEmptyMessageDelayed(MSG_CHECK_TIMEOUT, getTimeOutTime());
        }
    }
    private long mStartTime = System.currentTimeMillis();
    private final static long MAX_TIME = 4000;//4s
    private long getTimeOutTime(){
        long delay = System.currentTimeMillis() - mStartTime;
        if(delay < MAX_TIME){
            return MAX_TIME-delay;
        }
        return 0;
    }

    private final static int MSG_CHECK_TIMEOUT  = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CHECK_TIMEOUT:{
                    if(isLoadedLogianAccount()){
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    }
                }break;
            }
        }
    };

    private void loadMyUIAccount(){
        final MyUIAccount myUIAccount = MyUIAccount.getInstance(this);
        new Thread(new Runnable(){
            @Override
            public void run() {
                myUIAccount.logainMyUIAccount();
                mHandler.sendEmptyMessageDelayed(MSG_CHECK_TIMEOUT, getTimeOutTime());
            }
        }).start();
    }

}
