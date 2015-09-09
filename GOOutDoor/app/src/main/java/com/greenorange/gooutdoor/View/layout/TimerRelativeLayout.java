package com.greenorange.gooutdoor.View.layout;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.greenorange.gooutdoor.framework.widget.RevealView.CircularRevealView;

/**
 * Created by JasWorkSpace on 15/4/17.
 */
public class TimerRelativeLayout extends RelativeLayout implements View.OnClickListener {
    private CircularRevealView mCircularRevealView;
    private TextView           mTextNumberView;
    public final static int ID_TIMER_STATE = 1;

    public final static int TIMER_STATE_OPEN       = 0;
    public final static int TIMER_STATE_CLOSE      = 1;
    public final static int TIMER_STATE_TIMEOUT    = 2;
    public final static int TIMER_STATE_INTERRUPT  = 3;
    private int  mState = TIMER_STATE_CLOSE;

    public TimerRelativeLayout(Context context) {
        this(context, null);
    }
    public TimerRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public TimerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_timer1, this);
    }
    private boolean notifyStateChange(int state){
        if(mState == state)return false;
        int last = mState;
        mTextNumberView.setClickable(state != TIMER_STATE_CLOSE);
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
        mTextNumberView.setOnClickListener(this);
        reSetTextNumberView();
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
                    mTextNumberView.setClickable(true);
                }
                case MSG_TIME_SEC: {
                    if (index >= 0 && mState == TIMER_STATE_OPEN) {
                        animationTextView(number[index--]);
                        mHandler.sendEmptyMessageDelayed(MSG_TIME_SEC, 1000);
                    }else{
                        reSetTextNumberView();
                        mHandler.sendEmptyMessage(MSG_TIME_TIMEOUT);
                    }
                }break;
                case MSG_TIME_TIMEOUT:{
                    reSetTextNumberView();
                    mHandler.removeCallbacksAndMessages(null);
                    notifyStateChange(TIMER_STATE_TIMEOUT);

                }break;
                case MSG_TIME_INTERRUPUT:{
                    reSetTextNumberView();
                    mHandler.removeCallbacksAndMessages(null);
                    notifyStateChange(TIMER_STATE_INTERRUPT);

                }break;
                case MSG_TIME_CLOSE:{
                    reSetTextNumberView();
                    mHandler.removeCallbacksAndMessages(null);
                    notifyStateChange(TIMER_STATE_CLOSE);
                }break;
            }
        }
    };
    private synchronized void reSetTextNumberView(){
        if(mTextNumberView != null){
            mTextNumberView.setText("");
            cancelTextNumberViewAnimation();
            mTextNumberView.setVisibility(GONE);
            mTextNumberView.setClickable(false);
        }
    }
    private void animationTextView(String text){
        Animation animation = mTextNumberView.getAnimation();
        if(animation == null){
            mTextNumberView.setAnimation(
                    animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_timer_scale));
        }
        mTextNumberView.setText(text);
        animation.start();
    }
}
