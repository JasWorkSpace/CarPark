package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenorange.gooutdoor.UI.activity.GOSettingsActivity;
import com.greenorange.gooutdoor.Util.ActivityUtils;
import com.greenorange.gooutdoor.View.layout.FloatControlMenu;
import com.greenorange.gooutdoor.View.layout.FloatControlSmileFlash;
import com.greenorange.gooutdoor.View.layout.FloatControlSports;
import com.greenorange.gooutdoor.View.layout.TimerRelativeLayout;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.UI.fragment.dialog.FinishSportsDialog;
import com.greenorange.gooutdoor.framework.Dao.FlashDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Event.EventDialogFragmentCLick;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.squareup.otto.Subscribe;

/**
 * Created by JasWorkSpace on 15/4/12.
 */
public class FragmentFloatControl extends BaseFragment {

    private FloatControlMenu mFloatControlMenu;
    private FloatControlSmileFlash mFloatControlSmileFlash;
    private TimerRelativeLayout mTimerRelativeLayout;
    private FloatControlSports mFloatControlSports;

    public final static int AnimationDuration = 200;

    public final static int EVENT_MSG_NEWSPORTS = 1;

    private int newSportsID = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_floatcontrol1, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFloatControlMenu       = (FloatControlMenu) view.findViewById(R.id.FloatControlMenu);
        mFloatControlSmileFlash = (FloatControlSmileFlash) view.findViewById(R.id.FloatControlSmileFlash);
        mTimerRelativeLayout    = (TimerRelativeLayout) view.findViewById(R.id.FloatControlTimer);
        mFloatControlSports     = (FloatControlSports) view.findViewById(R.id.FloatControlSports);
    }
    @Subscribe
    public void EventClickListener(EventClick eventClick){
        switch(eventClick.ID){
            /////////////////////////////////////////////////
            // FloatControlSmileFlash
            case EventID.ID_CLICK_FloatControlSmileFlash:{
                if(eventClick.viewID == FloatControlSmileFlash.ID_BUTTON_FLASH) {
                    FlashDao flashDao = (FlashDao) mDaoManager.getManager(Dao.FlashDao);
                    flashDao.setFlashMode(flashDao.getNextFlashMode());
                    flashDao.open();
                }else if(eventClick.viewID == FloatControlSmileFlash.ID_BUTTON_SMILE){
                    ActivityUtils.startActivityWithAnimation(getActivity(), GOSettingsActivity.GOSmileActivity.class);
                }
            }break;
            //////////////////////////////////////////////////
            // FloatControlMenu
            case EventID.ID_CLICK_FloatControlMenu:{
                newSportsID = eventClick.viewID;
                mFloatControlMenu.setFloatingActionMenuEnable(false);
                mTimerRelativeLayout.animationshow(SportUtil.getSportsColor(getActivity(), newSportsID),
                        eventClick.view , AnimationDuration * 2);
            }break;
            /////////////////////////////////////////////////
            //  FloatControlSports
            case EventID.ID_CLICK_FloatControlSports:{
                if(FloatControlSports.CLICK_ID_STOP == eventClick.viewID){
                    DialogFragment dialogFragment = (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag(FinishSportsDialog.TAG_FRAGMENT);
                    if(dialogFragment == null) {//show dialog.
                        FinishSportsDialog.show(getActivity());
                    }
                } else if(FloatControlSports.CLICK_ID_CONTINUE == eventClick.viewID){
                    mFloatControlSports.setPauseState(true, FloatControlMenu.AnimationDuration);
                    mFloatControlSmileFlash.ScaleHideOrShow(true, FloatControlMenu.AnimationDuration);
                } else if(FloatControlSports.CLICK_ID_PAUSE == eventClick.viewID){
                    mFloatControlSports.setPauseState(false, FloatControlMenu.AnimationDuration);
                    mFloatControlSmileFlash.ScaleHideOrShow(false, FloatControlMenu.AnimationDuration);
                }
            }break;
        }
    }
    @Subscribe
    public void EventStateChangeListener(EventStateChange eventStateChange){
        switch (eventStateChange.ID){
            //////////////////////////////////////////////////
            // FloatControlMenu
            case EventID.ID_STATE_FloatControlMenu:{
                if(eventStateChange.viewID == FloatControlMenu.ID_STATE_MENU) {
                    if (FloatControlMenu.MENU_STATE_CLOSE == eventStateChange.newState) {
                        mFloatControlSmileFlash.ScaleHideOrShow(true, AnimationDuration);
                    } else if (FloatControlMenu.MENU_STATE_OPEN == eventStateChange.newState) {
                        mFloatControlSmileFlash.ScaleHideOrShow(false, AnimationDuration);
                    }
                }
            }break;
            //////////////////////////////////////////////
            // TimerRelativeLayout
            case EventID.ID_STATE_TimerRelativeLayout:{
                if(TimerRelativeLayout.ID_TIMER_STATE == eventStateChange.viewID){
                    if(TimerRelativeLayout.TIMER_STATE_TIMEOUT == eventStateChange.newState){
                        mTimerRelativeLayout.animationHide(mFloatControlMenu.mButtonMain, FloatControlMenu.AnimationDuration * 2);
                        mFloatControlMenu.setVisibility(View.GONE);
                        mFloatControlMenu.setFloatingActionMenuEnable(true);
                        mFloatControlSports.setVisibility(View.VISIBLE);
                        mFloatControlSports.setPauseState(true, FloatControlMenu.AnimationDuration);
                        Util.postEvent(Util.produceEventMSG(EventID.ID_MSG_FragmentFloatControl,
                                EVENT_MSG_NEWSPORTS, newSportsID));
                    } else if(TimerRelativeLayout.TIMER_STATE_INTERRUPT == eventStateChange.newState){
                        mTimerRelativeLayout.animationHide(mFloatControlMenu.mButtonMain, FloatControlMenu.AnimationDuration * 2);
                        mFloatControlMenu.setFloatingActionMenuEnable(true);
                    }
                }
            }break;
            /////////////////////////////////////////
            // Application Sports State
            case EventID.ID_STATE_APPLICATION_SPORTSSTATE:{
                int state = eventStateChange.newState;
                if(state == SportsState.STATE_FINISH){//delay for animation.
                    if(mFloatControlSports.getVisibility() == View.VISIBLE){
                        mFloatControlMenu.setVisibility(View.VISIBLE);
                        mFloatControlSports.setVisibility(View.GONE);
                    }
                    mFloatControlSmileFlash.ScaleHideOrShow(true, AnimationDuration);
                }
            }break;
        }
    }
    @Subscribe
    public void EventDialogFragmentCLickListener(EventDialogFragmentCLick eventClick){
        switch (eventClick.ID) {
            /////////////////////////////////////////
            // FinishSportsDialog
            case EventID.ID_CLICK_FinishSportsDialog: {
                if(FinishSportsDialog.CLICK_ID_CANCEL == eventClick.viewID){//notify continue sports.
                    mFloatControlSports.dispatcherEvent(FloatControlSports.CLICK_ID_CONTINUE);
                }
            }break;
        }
    }
    public void init(int state){
        boolean showmenu = !(SportUtil.isSportingState(state) || state == SportsState.STATE_STOP);
        mFloatControlMenu.setVisibility( showmenu ? View.VISIBLE : View.GONE);
        mFloatControlSports.setVisibility(showmenu ? View.GONE : View.VISIBLE);
        mFloatControlSports.init(state);
        boolean showpause = !(state == SportsState.STATE_PAUSE || state == SportsState.STATE_STOP);
        mFloatControlSmileFlash.ScaleHideOrShow(showpause, AnimationDuration);
    }
}
