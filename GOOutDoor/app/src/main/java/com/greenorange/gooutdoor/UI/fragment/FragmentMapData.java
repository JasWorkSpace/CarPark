package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.Util.Utils;
import com.greenorange.gooutdoor.View.layout.MapDataInfo;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.SportsDao;
import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.squareup.otto.Subscribe;

/**
 * Created by JasWorkSpace on 15/4/27.
 */
public class FragmentMapData extends BaseFragment {
    private MapDataInfo mMapDataInfo;
    private View        mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mapdata, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapDataInfo = (MapDataInfo) view.findViewById(R.id.mapinfo);
        mView        = view.findViewById(R.id.mapdata_root);
        sendSortsChangeMessage();// for init UI
    }
    @Subscribe
    public void EventClickListener(EventClick eventClick){
        switch(eventClick.ID){
            //////////////////////////////////////////////////////
            // MapDataInfo
            case EventID.ID_CLICK_MapDataInfo:{
                switch(eventClick.viewID){
                    case MapDataInfo.ID_CLICK_ROTATION:{
                        Utils.ChangeScreenRotatiton(getActivity());
                    }break;
                    case MapDataInfo.ID_CLICK_CAMERA:{

                    }break;
                }
            }break;
        }
    }
    @Subscribe
    public void EventStateChangeListener(EventStateChange eventStateChange){
        switch (eventStateChange.ID){
            /////////////////////////////////////////
            // Application Sports State
            case EventID.ID_STATE_APPLICATION_SPORTSSTATE:{
                sendSortsChangeMessage();
            }break;
        }
    }
    private final static int MSG_SPORTS_CHANGE = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case MSG_SPORTS_CHANGE:{
                    changeSportIcon();
                }break;
            }
        }
    };
    private void changeSportIcon(){
        SportsDao sportsDao = (SportsDao) mDaoManager.getManager(Dao.SportsDao);
        mMapDataInfo.setSportTypeAndState(sportsDao.getCurrentSportsType(), sportsDao.getCurrentSportsType());
    }
    private synchronized void sendSortsChangeMessage(){
        mHandler.removeMessages(MSG_SPORTS_CHANGE);
        mHandler.sendEmptyMessage(MSG_SPORTS_CHANGE);
    }
    public  synchronized void animationVisibility(int vis){
        if(mView.getVisibility() == vis)return;
        mView.setVisibility(vis);
        mView.clearAnimation();
        mView.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                vis == View.VISIBLE ? R.anim.actionbar_top_in : R.anim.actionbar_top_out));
    }
}
