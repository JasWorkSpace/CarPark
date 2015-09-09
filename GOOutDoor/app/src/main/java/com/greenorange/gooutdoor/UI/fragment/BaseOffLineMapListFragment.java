package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenorange.gooutdoor.View.layout.OfflineMapExpandableList;
import com.greenorange.gooutdoor.framework.widget.AnimatedExpandableListView;
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/9/2.
 */
public class BaseOffLineMapListFragment extends BaseFragment {
    protected OfflineMapExpandableList mAnimatedExpandableListView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_baseofflinemap, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAnimatedExpandableListView = (OfflineMapExpandableList) view.findViewById(R.id.animateexpandablelistview);
    }
}
