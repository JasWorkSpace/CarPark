package com.greenorange.gooutdoor.View.layout;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.greenorange.gooutdoor.framework.widget.NumberAnimation;
import com.greenorange.gooutdoor.framework.widget.RevealView.CircularRevealView;

/**
 * Created by JasWorkSpace on 15/4/17.
 */
public class TimerRelativeLayout extends RelativeLayout implements View.OnClickListener, NumberAnimation.InterpolatedTimeListener {
    private CircularRevealView mCircularRevealView;
    private TextView           mTextNumberView;
    private RelativeLayout     mTouchRelativeLayout;

    public final static int ID_TIMER_STATE = 1;

    public final static int TIMER_STATE_OPEN       = 0;
    public final static int TIMER_STATE_CLOSE      = 1;
    public final static int TIMER_STATE_TIMEOUT    = 2;
    public final static int TIMER_STATE_INTERRUPT  = 3;
    private int  mState = TIMER_STATE_CLOSE;

    public TimerRelativeLayout(Context context) {
        super(context);
    }
    public TimerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TimerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private boolean notifyStateChange(int state){
        if(mState == state)return false;
        int last = mState;
        mTouchRelativeLayout.setClickable(state != TIMER_STATE_CLOSE);
        mState = state;
        Util.postEvent(Util.produceEventStateChange(EventID.ID_STATE_TimerRelativeLayout,
                ID_TIMER_STATE, null , mState, last));
        return true;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircularRevealView = (CircularRevealView) findViewById(R.id.circular);
        mTextNumberView     = (TextView) findViewById(R.id.txtNumber);
        mTouchRelativeLayout = (RelativeLayout) findViewById(R.id.touch);
        mTouchRelativeLayout.setOnClickListener(this);
        mTouchRelativeLayout.setClickable(false);
    }

    public void animationshow(int color, View src, int animationtime){
        if(notifyStateChange(TIMER_STATE_OPEN)) {
            cancelTextNumberViewAnimation();
            Point p = getLocationInView(mCircularRevealView, src);
            mCircularRevealView.reveal(p.x, p.y, color, src.getHeight() / 2, animationtime, new showAnimation());
        }
    }

    public void animationHide(View src, int animationtime){
        if(notifyStateChange(TIMER_STATE_CLOSE)) {
            cancelTextNumberViewAnimation();
            Point p = getLocationInView(mCircularRevealView, src);
            mCircularRevealView.hide(p.x, p.y, Color.TRANSPARENT, 0, animationtime, new hideAnimation());
        }
    }
    private synchronized void cancelTextNumberViewAnimation(){
        if(mTextNumberView != null){
            Animation animaton = mTextNumberView.getAnimation();
            if(animaton != null)animaton.cancel();
            mTextNumberView.setAlpha(0);//make sure its inviable
        }
    }
    public void interrupt(){
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessage(TIMER_STATE_INTERRUPT);
    }
    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);
        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);
        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;
        return new Point(l1[0], l1[1]);
    }

    @Override
    public void onClick(View v) {
        interrupt();
    }

    private class showAnimation implements Animator.AnimatorListener{
        @Override
        public void onAnimationStart(Animator animation) {

        }
        @Override
        public void onAnimationEnd(Animator animation) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessage(MSG_TIMER_START);
        }
        @Override
        public void onAnimationCancel(Animator animation) {

        }
        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
    private class hideAnimation implements   Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessage(MSG_TIME_CLOSE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
    private int index = 0;
    private String[] number = new String[]{"GO","1","2","3"};
    private final static int MSG_TIMER_START = 0;
    private final static int MSG_TIME_SEC = 1;
    private final static int MSG_TIME_TIMEOUT = 2;
    private final static int MSG_TIME_INTERRUPUT = 3;
    private final static int MSG_TIME_CLOSE = 4;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case MSG_TIMER_START:{
                    index = number.length -1;
                    reSetTextNumberView();
                    mTextNumberView.setVisibility(VISIBLE);
                    animationTextView();
                }break;
                case MSG_TIME_SEC: {
                    if (--index >= 0 && mState == TIMER_STATE_OPEN) {
                        animationTextView();
                    }else{
                        mHandler.sendEmptyMessage(MSG_TIME_TIMEOUT);
                    }
                }break;
                case MSG_TIME_TIMEOUT:{
                    mHandler.removeCallbacksAndMessages(null);
                    notifyStateChange(TIMER_STATE_TIMEOUT);
                    reSetTextNumberView();
                }break;
                case MSG_TIME_INTERRUPUT:{
                    mHandler.removeCallbacksAndMessages(null);
                    notifyStateChange(TIMER_STATE_INTERRUPT);
                    reSetTextNumberView();
                }break;
                case MSG_TIME_CLOSE:{
                    mHandler.removeCallbacksAndMessages(null);
                    notifyStateChange(TIMER_STATE_CLOSE);
                    reSetTextNumberView();
                }
            }
        }
    };
    private synchronized void reSetTextNumberView(){
        if(mTextNumberView != null){
            mTextNumberView.setText("");
            cancelTextNumberViewAnimation();
            mTextNumberView.setVisibility(INVISIBLE);
        }
    }
    private boolean enrefresh = false;

    private void animationTextView(){
        enrefresh = true;
        NumberAnimation rotateAnim = new NumberAnimation(
                mTextNumberView.getWidth() / 2.0f, mTextNumberView.getHeight() / 2.0f, NumberAnimation.ROTATE_DECREASE);
        if (rotateAnim != null) {
            rotateAnim.setInterpolatedTimeListener(this);
//            rotateAnim.setFillAfter(true);
            mTextNumberView.startAnimation(rotateAnim);
        }
    }
    @Override
    public boolean interpolatedTime(float interpolatedTime) {
        if (enrefresh && interpolatedTime > 0.5f) {
             mTextNumberView.setText(number[index]);
             enrefresh = false;
             if(index >= 0 && mState == TIMER_STATE_OPEN){
                 mHandler.removeMessages(MSG_TIME_SEC);
                 mHandler.sendEmptyMessageDelayed(MSG_TIME_SEC, 1000);
                 return true;
             }
        }
        //change the alpha
        if(mState == TIMER_STATE_OPEN) {
            if (interpolatedTime > 0.5f) {
                mTextNumberView.setAlpha((interpolatedTime - 0.5f) * 2);
            } else {
                mTextNumberView.setAlpha(1 - interpolatedTime * 2);
            }
        }else{
            reSetTextNumberView();//for reset
        }
        return false;
    }
}
