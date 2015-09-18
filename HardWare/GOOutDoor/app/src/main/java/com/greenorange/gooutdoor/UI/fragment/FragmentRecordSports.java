package com.greenorange.gooutdoor.UI.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.UI.adapter.RecyclerArrayAdapter;
import com.greenorange.gooutdoor.UI.adapter.entity.RecoreSportsCategory;
import com.greenorange.gooutdoor.Util.FormatUtils;
import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.framework.Config;
import com.greenorange.gooutdoor.framework.DB.SportsDBHelper;
import com.greenorange.gooutdoor.framework.Dao.ApplicationDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.greenorange.gooutdoor.framework.widget.recyclerview.stickyheaders.StickyHeadersAdapter;
import com.greenorange.gooutdoor.framework.widget.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.greenorange.gooutdoor.framework.widget.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.greenorange.outdoorhelper.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/28.
 */
public class FragmentRecordSports extends BaseRecyleViewFragment implements View.OnClickListener {
    @Override
    public RecyclerView.Adapter getAdapter() {
        return mRecordAdapter = new RecordAdapter();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recordsports, null);
    }
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return getLinearLayoutManager();
    }
    @Override
    public void initResource() {
        super.initResource();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmptyView = view.findViewById(R.id.emptyview);
        mEmptyView.setOnClickListener(this);
    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindAdapter(mRecyclerView, mRecordAdapter);
        mRecyclerView.setOnScrollListener(new OnScrollListener());
        checkLoadRecordSportsData(mRecordAdapter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelLoadRecordSportsData();
    }
    private void bindAdapter(RecyclerView recyclerView, RecordAdapter recordAdapter){
        StickyHeadersItemDecoration  stickyHeadersItemDecoration
                = new StickyHeadersBuilder()
                        .setAdapter(recordAdapter)
                        .setStickyHeadersAdapter(recordAdapter)
                        .setRecyclerView(recyclerView)
                        .build();
        recyclerView.removeItemDecoration(stickyHeadersItemDecoration);
        recyclerView.addItemDecoration(stickyHeadersItemDecoration);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.emptyview:{
                getActivity().onBackPressed();
            }break;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////
    private class OnScrollListener extends RecyclerView.OnScrollListener{
        private int lastVisibleItem = -1;
        private boolean pulldown    = false;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && pulldown
                    && (lastVisibleItem + 1) == mRecordAdapter.getItemCount()) {
                checkLoadRecordSportsData(mRecordAdapter);
            }
        }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            pulldown        = (dy > 0);
        }
    }
    //////////////////
    /////////////////////////////////////////////////////////////
    private void checkEmptyViewVisable(){
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        boolean visable = true;
        if(adapter != null && adapter.getItemCount() > 0){
            visable = false;
        }
        mEmptyView.setVisibility(visable ? View.VISIBLE : View.GONE);
    }
    private View mEmptyView;
    private RecordAdapter mRecordAdapter;
    private class RecordAdapter extends RecyclerArrayAdapter<RecoreSportsCategory, RecordAdapter.ItemViewHolder>
            implements StickyHeadersAdapter<RecordAdapter.HeaderViewHolder>{
        private int mLastLoadRecordIndex = 0;
        public int getmLastLoadRecordIndex() {
            return mLastLoadRecordIndex;
        }
        public void setmLastLoadRecordIndex(int mLastLoadRecordIndex) {
            this.mLastLoadRecordIndex = mLastLoadRecordIndex;
        }
        private LayoutInflater mLayoutInflater;
        public RecordAdapter(){
            mLayoutInflater = LayoutInflater.from(getActivity());
            setHasStableIds(true);
        }
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ItemViewHolder(mLayoutInflater.inflate(R.layout.layout_recordesports_itemview, viewGroup, false));
        }
        @Override
        public void onBindViewHolder(ItemViewHolder viewHolder, int i) {
            SportsDBData sportsDBData = getItem(i).getItem();
            viewHolder.mItem_day.setText(getString(R.string.fragment_recordsports_item_day, sportsDBData.getSports_time_dayofmonth()));
            viewHolder.mItem_time.setText(getString(R.string.fragment_recordsports_item_time, sportsDBData.getSports_time_hourofday(), sportsDBData.getSports_time_minute()));
            mPicasso.load(SportUtil.getSportsTypeColorIcon(sportsDBData.getSports_type())).into(viewHolder.mItem_sportstype);
            viewHolder.mItem_sportstime.setText(FormatUtils.getSportsTime(getActivity(), sportsDBData.getSports_total_time() / 1000));
            viewHolder.mItem_sportsdistance.setText(FormatUtils.getFloat3to2(getActivity(), sportsDBData.getSports_total_distance() / 1000.0f));
            viewHolder.mItem_sportcalorie.setText(FormatUtils.getDouble3to1(getActivity(), sportsDBData.getSports_totle_calorie() / 1000.0f));
        }
        @Override
        public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.layout_recordesports_header, parent, false));
        }
        @Override
        public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int position) {
            viewHolder.mHeaderText.setText(getItem(position).getHeader());
        }
        @Override
        public long getHeaderId(int position) {
            if(position <0 )return -1;
            return getItem(position).getKey();
        }
        public class HeaderViewHolder extends RecyclerView.ViewHolder{
            public TextView mHeaderText;
            public HeaderViewHolder(View headerview) {
                super(headerview);
                mHeaderText = (TextView) headerview.findViewById(R.id.headertext);
            }
        }
        public class ItemViewHolder extends RecyclerView.ViewHolder{
            public TextView  mItem_day;
            public TextView  mItem_time;
            public ImageView mItem_sportstype;
            public TextView  mItem_sportstime;
            public TextView  mItem_sportsdistance;
            public TextView  mItem_sportcalorie;
            public ItemViewHolder(View itemView) {
                super(itemView);
                mItem_day = (TextView) itemView.findViewById(R.id.item_day);
                mItem_time = (TextView) itemView.findViewById(R.id.item_time);
                mItem_sportstype = (ImageView) itemView.findViewById(R.id.item_sportstype);
                mItem_sportstime = (TextView)  itemView.findViewById(R.id.item_sportstime);
                mItem_sportsdistance = (TextView) itemView.findViewById(R.id.item_sportsdistance);
                mItem_sportcalorie = (TextView) itemView.findViewById(R.id.item_sportscalorie);
            }
        }
        @Override
        public void addAll(Collection<? extends RecoreSportsCategory> collection) {
            super.addAll(collection);
            checkEmptyViewVisable();
        }
    }
    ////////////////////////////////////////////////////////////
    //for load data form database
    private synchronized void checkLoadRecordSportsData(RecordAdapter recordAdapter){
        if(mLoadRecordSportData == null || mLoadRecordSportData.isCancelled()
                || mLoadRecordSportData.getStatus() == AsyncTask.Status.FINISHED){
            mLoadRecordSportData = new LoadRecordSportData(recordAdapter);
            mLoadRecordSportData.execute(recordAdapter.getmLastLoadRecordIndex());
        }
    }
    private synchronized void cancelLoadRecordSportsData(){
        if(mLoadRecordSportData != null || mLoadRecordSportData.getStatus() != AsyncTask.Status.FINISHED){
            mLoadRecordSportData.cancel(false);
        }
        mLoadRecordSportData = null;
    }
    private LoadRecordSportData mLoadRecordSportData;
    private class LoadRecordSportData extends AsyncTask<Integer, List<RecoreSportsCategory>, Integer> {
        private final static int RESULT_SUCCESS = 1;
        private final static int RESULT_FAIL    = 2;
        private final static int RESULT_ERROR   = 3;
        private final static int RESULT_SUCCESS_NEXT = 4;
        private final static int RESULT_SUCCESS_NONEXT = 5;
        //////////////////////////////
        private RecordAdapter mRecordAdapter;
        public LoadRecordSportData(RecordAdapter recordAdapter){
            mRecordAdapter = recordAdapter;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(Integer... params) {
            int limitstart = params[0];
            if(limitstart != mRecordAdapter.getmLastLoadRecordIndex())return RESULT_ERROR;
            ApplicationDao applicationDao = (ApplicationDao) GOApplication.getDaoManager().getManager(Dao.ApplicationDao);
            UserDBData user = applicationDao.getUser();
            if(user != null){
                 try{//add index first
                     mRecordAdapter.setmLastLoadRecordIndex(mRecordAdapter.getmLastLoadRecordIndex() + Config.LIMIT_SIZE_LOAD_RECORD_SPORTS);
                     return loaddata(getActivity(), user.getUserid(), limitstart);
                 }catch (Throwable e){}
            }
            return RESULT_FAIL;
        }
        private int loaddata(Context context, String userid, int limitstart){
            List<SportsDBData> record = SportsDBHelper.getSuccessSportsLimitDataByUserId(context, userid, limitstart, Config.LIMIT_SIZE_LOAD_RECORD_SPORTS);
            if (record != null) {
                List<RecoreSportsCategory> recoreSportsCategories = new ArrayList<RecoreSportsCategory>();
                for (SportsDBData sportsDBData : record) {
                    try {
                        recoreSportsCategories.add(new RecoreSportsCategory(context, sportsDBData));
                    }catch(Throwable e){}
                }
                int size = recoreSportsCategories.size();
                if (size > 0) {
                    publishProgress(recoreSportsCategories);
                    return (size < Config.LIMIT_SIZE_LOAD_RECORD_SPORTS) ? RESULT_SUCCESS_NEXT : RESULT_SUCCESS_NONEXT;
                }
                return RESULT_SUCCESS_NONEXT;
            }
            return RESULT_FAIL;
        }
        @Override
        protected void onProgressUpdate(List<RecoreSportsCategory>... values) {
            super.onProgressUpdate(values);
            if(!isCancelled() && values != null){
                mRecordAdapter.addAll(values[0]);
            }
        }
        @Override
        protected void onPostExecute(Integer aBoolean) {
            super.onPostExecute(aBoolean);
            checkEmptyViewVisable();
        }
    }
}
