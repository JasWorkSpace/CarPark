package com.greenorange.gooutdoor.View.layout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Dao.FlashDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.Util.AnimatorUtils;
import com.greenorange.gooutdoor.framework.Application.Contants;
import com.greenorange.gooutdoor.framework.Dao.Interface.FlashTYPE;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Utils.Util;


/**
 * Created by JasWorkSpace on 15/4/14.
 */
public class FloatControlSmileFlash1 extends ClickListenerRelativeLayout implements View.OnClickListener {

    private ImageView mSmileColorButton;
    private ImageView mFlashButton;
    private Receiver  mReceiver;
    public final static int ID_BUTTON_SMILE = 1;
    public final static int ID_BUTTON_FLASH = 2;
    public FloatControlSmileFlash1(Context context) {
        this(context, null);
    }
    public FloatControlSmileFlash1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public FloatControlSmileFlash1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_floatcontrolmenu_smileflash1, this);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSmileColorButton = (ImageView) findViewById(R.id.button_colorsmile);
        mFlashButton = (ImageView) findViewById(R.id.button_flash);
        mSmileColorButton.setOnClickListener(this);
        mFlashButton.setOnClickListener(this);
        loadFlashMode();
    }
    private void loadFlashMode(){
        FlashDao flashDao = (FlashDao) GOApplication.getDaoManager().getManager(Dao.FlashDao);
        if(flashDao != null){
            setFlashModeImage(flashDao.getCurrentFlashMode());
        }
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mReceiver == null){
            mReceiver = new Receiver();
            getContext().registerReceiver(mReceiver, new IntentFilter(Contants.BROADCAST.ACTION_FLASH_MODE_CHANGE));
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mReceiver != null){
            getContext().unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }
    @Override
    public void onClick(View v) {
        if(v == mSmileColorButton){
            Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_FloatControlSmileFlash,
                    v, ID_BUTTON_SMILE));
        } else if(v == mFlashButton){
            Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_FloatControlSmileFlash,
                    v, ID_BUTTON_FLASH));
        }
    }
    private class Receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(TextUtils.equals(Contants.BROADCAST.ACTION_FLASH_MODE_CHANGE, action)){
                setFlashModeImage(intent.getIntExtra(Contants.BROADCAST.FLASH_MODENEW, FlashTYPE.FLASH_TYPE_CLOSED));
            }
        }
    }
    public void setFlashModeImage(int flashtype){
        if(mFlashButton != null) {
            int flashviewId = R.drawable.light_off_normal_selector;
            switch (flashtype) {
                case FlashTYPE.FLASH_TYPE_FLASH:
                    flashviewId = R.drawable.light_flash_normal_selector;
                    break;
                case FlashTYPE.FLASH_TYPE_OPENALWAY:
                    flashviewId = R.drawable.light_on_normal_selector;
                    break;
                case FlashTYPE.FLASH_TYPE_CLOSED:
                    flashviewId = R.drawable.light_off_normal_selector;
                    break;
            }
            mFlashButton.setBackground(getResources().getDrawable(flashviewId));
        }
    }
    public void ScaleHideOrShow(final boolean show, final int AnimationDuration){
        if(show == (mSmileColorButton.getVisibility() == VISIBLE))return;
        new Runnable(){
            @Override
            public void run() {
                AnimatorUtils.startScaleAnimation(show, AnimationDuration, mSmileColorButton, mFlashButton);
            }
        }.run();
    }
}
