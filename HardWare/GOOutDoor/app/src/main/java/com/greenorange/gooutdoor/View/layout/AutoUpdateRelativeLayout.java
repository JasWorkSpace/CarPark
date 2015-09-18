package com.greenorange.gooutdoor.View.layout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.greenorange.gooutdoor.framework.Config;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public class AutoUpdateRelativeLayout extends RelativeLayout {

    protected boolean hasAttachedToWindow = false;
    protected boolean isWindowVisiblility = false;
    public AutoUpdateRelativeLayout(Context context) {
        super(context);
    }
    public AutoUpdateRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AutoUpdateRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        hasAttachedToWindow = true;
        notifyAutoUpdateTimeOut();
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hasAttachedToWindow = false;
    }
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        isWindowVisiblility = (visibility==VISIBLE);
        notifyAutoUpdateTimeOut();
    }
    protected boolean isReallyToUpdateData(){
        return hasAttachedToWindow && isWindowVisiblility;
    }
    public void UpdateViewData(){
        //do nothing here. it should be abstract
    }

    private final static int MSG_STATE_CHANGE = 0;
    private final static int MSG_TIMEOUT      = 1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_TIMEOUT:{
                    UpdateViewData();
                    notifyAutoUpdateTimeOut();
                }break;
                case MSG_STATE_CHANGE:{
                    notifyAutoUpdateTimeOut();
                }break;
            }
        }
    };
    private synchronized void notifyAutoUpdateTimeOut(){
        mHandler.removeMessages(MSG_TIMEOUT);
        if(isReallyToUpdateData()){
            mHandler.sendEmptyMessageDelayed(MSG_TIMEOUT, getTimeOutTime());
        }
    }
    public int getTimeOutTime(){
        return Config.TIMEOUT_AUTO_UPDATE_VIEW_DATA;
    }
}
