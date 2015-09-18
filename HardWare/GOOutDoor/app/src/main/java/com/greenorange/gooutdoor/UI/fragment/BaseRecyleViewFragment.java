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
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/9/15.
 */
public abstract class BaseRecyleViewFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Object> {
    protected RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_baserecyleview, null);
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        initResource();
        initLoadManager();
    }
    public void initLoadManager() {
        if(isLoadManagerSupport()){
            getLoaderManager().initLoader(0, null, this);
        }
    }
    public abstract RecyclerView.Adapter getAdapter();
    public abstract RecyclerView.LayoutManager getLayoutManager();
    public boolean isLoadManagerSupport(){
        return false;
    }
    public void initResource(){
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(getAdapter());
    }
    ////////////////////////////////////////////////////////////
    // for loader
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {//do nothing
        return null;
    }
    @Override
    public void onLoadFinished(Loader loader, Object o) {
        //do nothing
    }
    @Override
    public void onLoaderReset(Loader loader) {
        //do nothing
    }
    /////////////////////////////////////////////////////////////////
    public LinearLayoutManager getLinearLayoutManager(){
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }
    public GridLayoutManager getGridLayoutManager(int count){
        return new GridLayoutManager(getActivity(), count, LinearLayoutManager.VERTICAL, false);
    }

}
