package com.greenorange.gooutdoor.View.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Utils.Util;

/**
 * Created by JasWorkSpace on 15/4/19.
 */
public class FloatControlSports extends ClickListenerRelativeLayout implements View.OnClickListener {

    public final static int CLICK_ID_PAUSE = 10;
    public final static int CLICK_ID_CONTINUE = 11;
    public final static int CLICK_ID_STOP  = 12;

    private View mPauseLayout;
    private View mControlLayout;
    private Animation[] mAnimation;
    private View   mPause;
    private View   mContinue;
    private View   mStop;

    public FloatControlSports(Context context) {
        this(context, null);
    }

    public FloatControlSports(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatControlSports(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_floatcontrol_sportscontrol1, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPause = findViewById(R.id.button_pause);
        mPause.setOnClickListener(this);
        mContinue = findViewById(R.id.button_continue);
        mContinue.setOnClickListener(this);
        mStop = findViewById(R.id.button_stop);
        mStop.setOnClickListener(this);
        mPauseLayout   = findViewById(R.id.layout_pause);
        mControlLayout = findViewById(R.id.layout_control);
        mAnimation = new Animation[2];
        mAnimation[0] = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_in_alpha);
        mAnimation[1] = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_out_alpha);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        mPauseLayout.setVisibility(GONE);
        mControlLayout.setVisibility(GONE);
    }

    public void setPauseState(final boolean show, final int animationtime){
        mControlLayout.setVisibility(show ? GONE : VISIBLE);
        //mControlLayout.setClickable(!show);

        mPauseLayout.setVisibility(show ? VISIBLE : GONE);
        //mPauseLayout.setClickable(show);

        new Runnable() {
            @Override
            public void run() {
                mControlLayout.startAnimation(mAnimation[show ? 1 : 0]);
                mPauseLayout.startAnimation(mAnimation[show ? 0 : 1]);
            }
        }.run();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_pause:{
                Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_FloatControlSports,
                        v, CLICK_ID_PAUSE));
            }break;
            case R.id.button_continue:{
                Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_FloatControlSports,
                        v, CLICK_ID_CONTINUE));
            }break;
            case R.id.button_stop:{
                Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_FloatControlSports,
                        v, CLICK_ID_STOP));
            }break;
        }
    }
    public void dispatcherEvent(int id){
        Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_FloatControlSports, null, id));
    }
    public void init(int state){
        boolean showpause = !(state == SportsState.STATE_PAUSE || state == SportsState.STATE_STOP);
        mControlLayout.setVisibility(showpause ? GONE : VISIBLE);
        mPauseLayout.setVisibility(showpause ? VISIBLE : GONE);
    }
}
