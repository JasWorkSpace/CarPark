package com.greenorange.gooutdoor.UI.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import com.greenorange.gooutdoor.Bean.UserSportsData;
import com.greenorange.gooutdoor.Util.FormatUtils;
import com.greenorange.gooutdoor.Util.Utils;
import com.greenorange.gooutdoor.View.Card.Card;
import com.greenorange.gooutdoor.View.layout.MapDataInfo;
import com.greenorange.gooutdoor.View.layout.MapDataInfo1;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.SharePreference;
import com.greenorange.gooutdoor.framework.Dao.SportsDao;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Model.Event.EventMSG;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.greenorange.gooutdoor.framework.Utils.SharePerferenceUtils;
import com.greenorange.gooutdoor.framework.widget.DragableGridview;
import com.greenorange.gooutdoor.mode.SportsDataSaveAsyncTask;
import com.greenorange.outdoorhelper.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/20.
 */
public class FragmentMapData1 extends BaseFragment {
    private DragableGridview mDragableGridview;
    private MapDataInfo1     mMapDataInfo;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return mView = inflater.inflate(R.layout.fragment_mapdata1, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDragableGridview = (DragableGridview) view.findViewById(R.id.gridview);
        mDragableGridview.setAdapter(mDragableGridviewAdapter = new DragableGridviewAdapter(getActivity()));
        mDragableGridview.setOnItemClick(mDragableGridviewAdapter);
        mDragableGridview.setOnSwappingListener(mDragableGridviewAdapter);
        mDragableGridview.setRecyclerListener(mDragableGridviewAdapter);
        mMapDataInfo = (MapDataInfo1) view.findViewById(R.id.mapdata);
        mMapDataInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDragableGridviewAdapter.getCount() <= 0) {
                    mDragableGridviewAdapter.build();
                } else {
                    mDragableGridviewAdapter.clear();
                }
                checkLooper();
            }
        });
    }
    private synchronized void checkLooper(){
        if(mDragableGridviewAdapter != null && mDragableGridviewAdapter.isNeedUpdateLooper()){
            startLooper();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        checkLooper();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private boolean isRunnableUpdateing = false;
    private Runnable mRunnbale = new Runnable() {
        @Override
        public void run() {
            if(isRunnableUpdateing)return;
            isRunnableUpdateing = true;
            if (getFragmentState() == FRAGMENT_STATE_RESUMED
                    && mView.getVisibility() == View.VISIBLE) {
                mDragableGridviewAdapter.UpdateViewData();
                mDragableGridviewAdapter.notifyDataSetChanged();
                mHandler.postDelayed(mRunnbale, 700);
            }
            isRunnableUpdateing = false;
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////
    // for mapdate
    private DragableGridviewAdapter mDragableGridviewAdapter;
    private class DragableGridviewAdapter extends BaseAdapter implements DragableGridview.OnItemClickListener, DragableGridview.OnSwappingListener
            , AbsListView.RecyclerListener {
        private LayoutInflater mLayoutInflater;
        private List<Item>     mMapDataList;

        public DragableGridviewAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
            mMapDataList = new ArrayList<Item>();
            build();//show it first
        }
        public void clear() {
            mMapDataList.clear();
            notifyDataSetChanged();
        }
        public void build() {
            mMapDataList.clear();
            mMapDataList.addAll(buildItem());
            notifyDataSetChanged();
        }
        private List<Item> buildItem() {
            String mapdataitem = (String) SharePerferenceUtils.getValue(SharePreference.KEY_MAPDATA, "");
            if (!TextUtils.isEmpty(mapdataitem)) {
                try {
                    String[] items = mapdataitem.split(";");
                    List<Item> result = new ArrayList<Item>();
                    for (String item : items) {
                        result.add(createItem(Integer.parseInt(item)));
                    }
                    return result;
                } catch (Throwable e) {
                    Log.e("buildItem fail " + e.toString());
                }
            }
            return createItem();
        }

        private List<Item> createItem() {
            List<Item> result = new ArrayList<Item>();
            result.add(new Item(ID_MAPDATA_TIME));
            result.add(new Item(ID_MAPDATA_DISTANCE));
            result.add(new Item(ID_MAPDATA_SPEED));
            result.add(new Item(ID_MAPDATA_CALORIE));
            return result;
        }
        private Item createItem(int id) {
            Item it = new Item(id);
            it.mCard = createCard(it.mId);
            return it;
        }
        private Card createCard(int id){
            int res = -1;
            switch (id) {
                case ID_MAPDATA_TIME:
                    res = R.layout.layout_sportcard_time;
                    break;
                case ID_MAPDATA_DISTANCE:
                    res = R.layout.layout_sportcard_distance;
                    break;
                case ID_MAPDATA_SPEED:
                    res = R.layout.layout_sportcard_speed;
                    break;
                case ID_MAPDATA_CALORIE:
                    res = R.layout.layout_sportcard_calorie;
                    break;
            }
            Card card = (Card) mLayoutInflater.inflate(res, null);
            if (id == ID_MAPDATA_SPEED) {
                setViewState(id, card, (Integer) SharePerferenceUtils.getValue(SharePreference.KEY_DATA_ITEM_SPEEDCARD, Card.STATE_V1));
            }
            return card;
        }
        @Override
        public int getCount() {
            return mMapDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMapDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item = (Item) getItem(position);
            if(item.mCard == null){
                item.mCard = createCard(item.mId);
            }
            return item.mCard;
        }

        private void setViewState(Item item, int state) {
            setViewState(item.mId, item.mCard, state);
        }
        private void setViewState(int id, Card card, int state){
            if(id == ID_MAPDATA_SPEED){
                if (card.setSTATE(state))
                    SharePerferenceUtils.setValue(SharePreference.KEY_DATA_ITEM_SPEEDCARD, card.getSTATE());
            }
        }
        private void setViewState(int position) {
            Item item = (Item) getItem(position);
            if (item.mId == ID_MAPDATA_SPEED) {
                setViewState(item, item.mCard.getSTATE() == Card.STATE_V1 ? Card.STATE_V2 : Card.STATE_V1);
            }
        }

        @Override
        public void click(int index) {
            setViewState(index);
        }

        @Override
        public void waspping(int oldIndex, int newIndex) {
            Item card = mMapDataList.get(oldIndex);
            mMapDataList.remove(oldIndex);
            mMapDataList.add(newIndex, card);
            savemMapDataList();
            notifyDataSetChanged();
        }

        private void savemMapDataList() {
            try {
                if (mMapDataList != null && mMapDataList.size() == ID_MAPDATA_MAX) {
                    String idsvalue = "";
                    int index = 0;
                    for (Item item : mMapDataList) {
                        idsvalue += String.valueOf(item.mId);
                        if (index++ < ID_MAPDATA_MAX) {
                            idsvalue += ";";
                        }
                    }
                    SharePerferenceUtils.setValue(SharePreference.KEY_MAPDATA, idsvalue);
                }
            } catch (Throwable e) {
                Log.d("savemMapDataList fail " + e.toString());
            }
        }

        @Override
        public void onMovedToScrapHeap(View view) {
            view = null;
        }

        private long lastUpdateTime;

        @Override
        public void notifyDataSetChanged() {
            long nowtime = System.currentTimeMillis();
            if (nowtime - lastUpdateTime < 700) return;
            lastUpdateTime = nowtime;
            super.notifyDataSetChanged();
        }

        public class Item {
            public int mId = -1;
            public Card mCard = null;

            public Item(int id) {
                mId = id;
            }
        }
        private boolean isUpdateViewData = false;
        public void UpdateViewData() {
            //do nothing here. it should be abstract
            if(isUpdateViewData || !isNeedUpdateLooper())return;
            isUpdateViewData = true;
            Activity context = getActivity();
            if (context == null || context.isFinishing()) return;
            UserSportsData userSportsData = Utils.getUserSportsData(context);
            if (userSportsData != null) {
                long   totleTime     = userSportsData.getSportTotalTime();
                long   totleDistance = userSportsData.getSportTotleDistance();
                double calorie       = userSportsData.getSportTotleCalorie();
                for (Item item : mMapDataList) {
                    if(item.mCard == null)continue;//it maybe recyle by onMovedToScrapHeap;
                    if (item.mId == ID_MAPDATA_CALORIE) {
                        item.mCard.setValue(FormatUtils.getDouble3to1(context, calorie / 1000.0f));
                    } else if (item.mId == ID_MAPDATA_DISTANCE) {
                        item.mCard.setValue(FormatUtils.getFloat3to1(context, totleDistance / 1000.0f));
                    } else if (item.mId == ID_MAPDATA_SPEED) {
                        if (Card.STATE_V1 == item.mCard.getSTATE()) {
                            float v1 = (totleTime > 0 ? (60.0f * totleDistance / totleTime) : 0.0f);
                            item.mCard.setValue(FormatUtils.getFloat3to1(context, v1));
                        } else {
                            float v2 = (totleDistance > 0 ? (totleTime / totleDistance / 60.0f) : 0.0f);
                            item.mCard.setValue(FormatUtils.getFloat3to1(context, v2));
                        }
                    } else if (item.mId == ID_MAPDATA_TIME) {
                        item.mCard.setValue(FormatUtils.getMillSecTime(context, totleTime));
                    }
                }
            }
            isUpdateViewData = false;
        }
        public boolean isNeedUpdateLooper(){
            return mMapDataList != null && mMapDataList.size()>0;
        }
    }

    public final static int ID_MAPDATA_TIME = 1;
    public final static int ID_MAPDATA_DISTANCE = 2;
    public final static int ID_MAPDATA_SPEED = 3;
    public final static int ID_MAPDATA_CALORIE = 4;
    public final static int ID_MAPDATA_MAX = 4;

    //////////////////////////////////////////////////////////////////////////////
    @Subscribe
    public void EventClickListener(EventClick eventClick) {
        switch (eventClick.ID) {
            //////////////////////////////////////////////////////
            // MapDataInfo
            case EventID.ID_CLICK_MapDataInfo: {
                switch (eventClick.viewID) {
                    case MapDataInfo.ID_CLICK_ROTATION: {
                        Utils.ChangeScreenRotatiton(getActivity());
                    }
                    break;
                    case MapDataInfo.ID_CLICK_CAMERA: {

                    }
                    break;
                }
            }
            break;
        }
    }
    @Subscribe
    public void EventStateChangeListener(EventStateChange eventStateChange) {
        switch (eventStateChange.ID) {
            /////////////////////////////////////////
            // Application Sports State
            case EventID.ID_STATE_APPLICATION_SPORTSSTATE: {
                sendSortsChangeMessage();
            }
            break;
        }
    }
    @Subscribe
    public void EventMSGListener(EventMSG eventMSG){
        switch(eventMSG.ID) {
            case EventID.ID_MSG_SaveStepChange:{
                switch(eventMSG.msg){
                    case SportsDataSaveAsyncTask.STEP_READY:{
                        if(mDragableGridviewAdapter != null) {
                            if (mDragableGridviewAdapter.getCount() <= 0) {
                                mDragableGridviewAdapter.build();
                            }
                            checkLooper();
                        }
                    }break;
                }
            }break;
        }
    }

    private final static int MSG_SPORTS_CHANGE = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SPORTS_CHANGE: {
                    changeSportIcon();
                }
                break;
            }
        }
    };
    private void changeSportIcon() {
        SportsDao sportsDao = (SportsDao) mDaoManager.getManager(Dao.SportsDao);
        mMapDataInfo.setSportTypeAndState(sportsDao.getCurrentSportsType(), sportsDao.getCurrentSportsType());
    }
    private synchronized void sendSortsChangeMessage() {
        mHandler.removeMessages(MSG_SPORTS_CHANGE);
        mHandler.sendEmptyMessage(MSG_SPORTS_CHANGE);
    }
    public synchronized void animationVisibility(int vis) {
        if (mView.getVisibility() == vis) return;
        mView.setVisibility(vis);
        mView.clearAnimation();
        mView.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                vis == View.VISIBLE ? R.anim.actionbar_top_in : R.anim.actionbar_top_out));
        checkLooper();
    }
    private synchronized void startLooper(){
        try {
            mHandler.post(mRunnbale);
        }catch (Throwable e){Log.e("startLooper fail="+ e.toString());}
    }
}
