package com.greenorange.gooutdoor.View.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.Util.AnimatorUtils;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.greenorange.gooutdoor.framework.widget.ActionMenu.FloatingActionMenu;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsTYPE;
import com.greenorange.gooutdoor.framework.widget.FloatSubButton;


/**
 * Created by JasWorkSpace on 15/4/13.
 */
public class FloatControlMenu1 extends ClickListenerRelativeLayout implements View.OnClickListener, FloatingActionMenu.MenuStateChangeListener {
    private RelativeLayout mBackground;
    public  ImageView      mButtonMain;
    private ImageView      mPlus;
    private FloatingActionMenu mFloatingActionMenu;
    public final static int AnimationDuration = 300;

    //////////////////////////////////////////////////
    public  final static int ID_STATE_MENU = 1;
    public  int   mMenuState = MENU_STATE_CLOSE;
    public  final static int MENU_STATE_OPEN  = 1;
    public  final static int MENU_STATE_CLOSE = 2;
    private void setMenuState(int state){
        setMenuState(null, state);
    }
    private void setMenuState(FloatingActionMenu menu, int state){
        if(state != mMenuState) {
            int last = mMenuState;
            mMenuState = state;
            Util.postEvent(Util.produceEventStateChange(EventID.ID_STATE_FloatControlMenu,
                    ID_STATE_MENU, menu, mMenuState, last));
        }
    }
    public  FloatControlMenu1(Context context) {
        this(context, null);
    }
    public  FloatControlMenu1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public  FloatControlMenu1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_floatcontrolmenu1, this);
    }
    @Override
    public void onClick(View v) {
        if(v instanceof FloatSubButton){
            if(!mFloatingActionMenu.isAnimating()) {
                mFloatingActionMenu.toggle(true);
                Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_FloatControlMenu,
                        v, ((FloatSubButton) v).getFloatSubButtonID()));
            }
            return;
        }
        switch(v.getId()){
            case R.id.touchroot:{
                if(!mFloatingActionMenu.isAnimating()) {
                    mFloatingActionMenu.toggle(true);
                }
            }break;
        }
    }
    @Override
    public void onMenuOpened(FloatingActionMenu menu) {
        mBackground.setVisibility(VISIBLE);
        animationMenu(true);
        setMenuState(MENU_STATE_OPEN);
    }
    private void animationMenu(final boolean open){
        new Runnable(){
            @Override
            public void run() {
                ObjectAnimator plusAnimator = AnimatorUtils.getObjectAnimator(mPlus, AnimationDuration, AnimatorUtils.getRotationPropertyValuesHolder(mPlus, open ? 0 : 45, open ? 45 : 0));
                ObjectAnimator backgroundAnimator = AnimatorUtils.getObjectAnimator(mBackground, AnimationDuration, AnimatorUtils.getAlaphaPropertyValuesHolder(mBackground, open ? 0 : 1 , open ? 1 : 0));
                AnimatorUtils.startAnimation(null, plusAnimator, backgroundAnimator);
                animationMenuMain(open);
            }
        }.run();
    }
    private void animationMenuMain(final boolean open){
        new Runnable(){
            @Override
            public void run() {
                AnimatorUtils.startAnimation(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        new Runnable(){
                            @Override
                            public void run() {
                                int newId = open ? R.drawable.bg_cancel01 : R.drawable.bg_big_green;
                                mButtonMain.setBackground(getResources().getDrawable(newId));
                                AnimatorUtils.startAnimation(null, getMenuMainAnimator(true));
                            }
                        }.run();
                    }
                }, getMenuMainAnimator(false));
            }
        }.run();
    }
    private ObjectAnimator getMenuMainAnimator(boolean show){
        return AnimatorUtils.getObjectAnimator(mButtonMain, AnimationDuration/2,
                show ? AnimatorUtils.getOpenTimeInterpolator() : AnimatorUtils.getCloseTimeInterpolator(),
                AnimatorUtils.getAlaphaPropertyValuesHolder(mButtonMain, show ? 0.3f : 1, show ? 1 : 0.3f),
                AnimatorUtils.getScaleXPropertyValuesHolder(mButtonMain, show ? 0.1f : 1, show ? 1 : 0.1f),
                AnimatorUtils.getScaleYPropertyValuesHolder(mButtonMain, show ? 0.1f : 1, show ? 1 : 0.1f ));
    }

    @Override
    public void onMenuClosed(FloatingActionMenu menu) {
        mBackground.setVisibility(GONE);
        animationMenu(false);
        setMenuState(MENU_STATE_CLOSE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackground = (RelativeLayout) findViewById(R.id.touchroot);
        mButtonMain = (ImageView) findViewById(R.id.plus_background);
        mPlus       = (ImageView) findViewById(R.id.button_plus);
        mBackground.setOnClickListener(this);
        initSubButton();
    }
    private void initSubButton(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        mFloatingActionMenu  = new FloatingActionMenu.Builder(getContext())
                .setStartAngle(190) // A whole circle!
                .setEndAngle(350)
                .addSubActionView(getFloatSubButton(layoutInflater, SportsTYPE.SPORT_TYPE_BICYCLE, R.drawable.bicycle_normal_selector))
                .addSubActionView(getFloatSubButton(layoutInflater, SportsTYPE.SPORT_TYPE_WALK,    R.drawable.walk_normal_selector))
                .addSubActionView(getFloatSubButton(layoutInflater, SportsTYPE.SPORT_TYPE_RUN,     R.drawable.run_normal_selector))
                .addSubActionView(getFloatSubButton(layoutInflater, SportsTYPE.SPORT_TYPE_SKI,     R.drawable.ski_normal_selector))
                .attachTo(mButtonMain)
                .build();
        mFloatingActionMenu.setStateChangeListener(this);
    }
    private FloatSubButton getFloatSubButton(LayoutInflater layoutInflater, int id, int resourceId){
        int size = getResources().getDimensionPixelSize(R.dimen.floatcontrol_button_sub);
        FloatSubButton subview = (FloatSubButton) layoutInflater.inflate(R.layout.layout_floatcontrolmenu_subbutton, null);
        subview.setBackground(getResources().getDrawable(resourceId));
        subview.setFloatSubButtonID(id);
        subview.setLayoutParams(new FrameLayout.LayoutParams(size,size,Gravity.TOP | Gravity.LEFT));
        subview.setOnClickListener(this);
        return subview;
    }
    public void setFloatingActionMenuEnable(boolean enable){
        mButtonMain.setClickable(enable);
    }
}
