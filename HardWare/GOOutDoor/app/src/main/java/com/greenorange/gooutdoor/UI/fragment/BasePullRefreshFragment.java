package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenorange.gooutdoor.framework.widget.pulltorefresh.library.PtrRecyclerView;
import com.greenorange.gooutdoor.framework.widget.pulltorefresh.library.PullToRefreshBase;
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/9/14.
 */
public abstract class BasePullRefreshFragment extends BaseRecyleViewFragment{
    protected PtrRecyclerView mPtrRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basepullrefresh, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPtrRecyclerView = (PtrRecyclerView) view.findViewById(R.id.pull_refresh_recycler_view);
        initResource();
        initLoadManager();
    }
    public void initResource(){
        mPtrRecyclerView.setLayoutManager(getLayoutManager());
        mPtrRecyclerView.setAdapter(getAdapter());
        mPtrRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
    }
    //////////////
    public synchronized PtrRecyclerView getPtrRecyclerView(){
        return mPtrRecyclerView;
    }
    public LinearLayoutManager getLinearLayoutManager(){
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }
    public GridLayoutManager getGridLayoutManager(int count){
        return new GridLayoutManager(getActivity(), count, LinearLayoutManager.VERTICAL, false);
    }
    /////////////////////////////////////////////

}
