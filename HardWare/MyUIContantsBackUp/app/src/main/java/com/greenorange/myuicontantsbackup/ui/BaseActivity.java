package com.greenorange.myuicontantsbackup.ui;

import android.app.Activity;
import android.os.Bundle;

import com.greenorange.myuicontantsbackup.Account.MyUIAccount;

/**
 * Created by JasWorkSpace on 15/11/2.
 */
public class BaseActivity extends Activity {
    protected MyUIAccount mMyUIAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mMyUIAccount == null){
            mMyUIAccount = MyUIAccount.getInstance(BaseActivity.this);
        }
        isLoadedLogianAccount();
    }
    public boolean isLoadedLogianAccount(){
        return isLoadedLogianAccount(true);
    }
    public boolean isLoadedLogianAccount(boolean finish){
        if(!mMyUIAccount.isLoaded()
                && !mMyUIAccount.isHasMyUIAccountLogained(BaseActivity.this)){
            mMyUIAccount.startDefaultLogainAccount(BaseActivity.this);
            if(finish
                    && !BaseActivity.this.isFinishing()
                    && !BaseActivity.this.isDestroyed()){
                finish();
            }
            return false;
        }
        return true;
    }

}
