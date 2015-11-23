package com.greenorange.myuicontantsbackup.ui.layout;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.greenorange.myuicontantsbackup.Account.MyUIAccount;
import com.greenorange.myuicontantsbackup.Account.UserInfo;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.R;

/**
 * Created by JasWorkSpace on 15/10/30.
 */
public class MainUserInfo extends RelativeLayout implements MyUIAccount.MyUIAccountChangeListener {
    public MainUserInfo(Context context) {
        super(context);
    }
    public MainUserInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MainUserInfo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private MyUIAccount myUIAccount;
    private MyUIAccount getMyUIAccount(){
        if(myUIAccount == null){
            myUIAccount = MyUIAccount.getInstance(getContext());
        }
        return myUIAccount;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mUserInfo_name   = (TextView) findViewById(R.id.user_name);
        mUserInfo_mobile = (TextView) findViewById(R.id.user_mobile);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility == VISIBLE){
            getMyUIAccount().addMyUIAccountChangeListener(this);
            mhandler.sendEmptyMessage(MSG_RELOAD_USERINFO);
        }else{
            getMyUIAccount().removeMyUIAccountChangeListener(this);
        }
    }

    private TextView mUserInfo_name;
    private TextView mUserInfo_mobile;

    private final static int MSG_RELOAD_USERINFO = 1;
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_RELOAD_USERINFO:{
                    loadUserInfo(getMyUIAccount().getMyUIUserInfo());
                }break;
            }
        }
    };

    private synchronized void loadUserInfo(UserInfo userInfo){
        Log.d("loadUserInfo-->"+(userInfo==null ? "" : userInfo.toString()));
        String username = "";
        String mobile   = "";
        if(userInfo != null){
            if(!TextUtils.isEmpty(userInfo.getRealname())){
                username = userInfo.getRealname();
            }else{
                username = userInfo.getUsername();
            }
            mobile = userInfo.getMobile();
        }
        if(mUserInfo_name!=null)mUserInfo_name.setText(username);
        if(mUserInfo_mobile!=null)mUserInfo_mobile.setText(mobile);
    }
    @Override
    public void onMyUIAccountChange(Account account, UserInfo userInfo) {
        mhandler.sendEmptyMessage(MSG_RELOAD_USERINFO);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
