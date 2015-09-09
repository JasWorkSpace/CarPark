package com.greenorange.gooutdoor.UI.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import com.amap.api.maps.offlinemap.OfflineMapStatus;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Utils.Utils;
import com.greenorange.gooutdoor.framework.widget.AnimatedExpandableListView;
import com.greenorange.gooutdoor.framework.widget.swipemenulist.SwipeMenu;
import com.greenorange.gooutdoor.framework.widget.swipemenulist.SwipeMenuItem;
import com.greenorange.gooutdoor.framework.widget.swipemenulist.SwipeMenuLayout;
import com.greenorange.gooutdoor.framework.widget.swipemenulist.SwipeMenuView;
import com.greenorange.outdoorhelper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/9/2.
 */
public class FragmentGaodeOffLineMapCityList extends BaseOffLineMapListFragment implements ExpandableListView.OnGroupClickListener, OfflineMapManager.OfflineMapDownloadListener {
    private OfflineMapManager mOfflineMapManager;
    private GaodeOffLineMapExpandableListAdapter  mGaodeOffLineMapExpandableListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOfflineMapManager = new OfflineMapManager(getActivity(), this);
    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAnimatedExpandableListView.setOnGroupClickListener(this);
        mAnimatedExpandableListView.setAdapter(mGaodeOffLineMapExpandableListAdapter = getAdapter());
    }
    private GaodeOffLineMapExpandableListAdapter getAdapter(){
        List<OfflineMapProvince> provinceList = mOfflineMapManager.getOfflineMapProvinceList();// 保存一级目录的省直辖市
        HashMap<Object, List<OfflineMapCity>> cityMap = new HashMap<Object, List<OfflineMapCity>>();// 保存二级目录的市
        List<OfflineMapProvince> specialprovinceList  = new ArrayList<OfflineMapProvince>();//需要合并的省份
        List<OfflineMapCity>  gangao                  = new ArrayList<OfflineMapCity>();
        List<OfflineMapCity>  gaiyao                  = new ArrayList<OfflineMapCity>();
        List<OfflineMapCity>  zhixia                  = new ArrayList<OfflineMapCity>();
        for (int i = 0; i < provinceList.size(); i++) {
            OfflineMapProvince offlineMapProvince = provinceList.get(i);
            List<OfflineMapCity> city = new ArrayList<OfflineMapCity>();
            city.add(getCity(offlineMapProvince));
            if (offlineMapProvince.getCityList().size() != 1) {
                city.addAll(offlineMapProvince.getCityList());
                cityMap.put(i+3 , city);
            } else {
                specialprovinceList.add(offlineMapProvince);//需要合并的省份
                if (offlineMapProvince.getProvinceName().contains("香港")
                        || offlineMapProvince.getProvinceName().contains("澳门")) {
                    gangao.addAll(city);
                } else if (offlineMapProvince.getProvinceName().contains("概要图")) {
                    gaiyao.addAll(city);
                } else{
                    zhixia.addAll(city);
                }
            }
        }
        //remove it first.
        provinceList.removeAll(specialprovinceList);
        //add special province
        provinceList.add(INDEX_GAIYAO, getSpecialProvince("全国概要图"));
        cityMap.put(INDEX_GAIYAO, gaiyao);
        provinceList.add(INDEX_ZHIXIA, getSpecialProvince("直辖市"));
        cityMap.put(INDEX_ZHIXIA,zhixia);
        provinceList.add(INDEX_GANGAO, getSpecialProvince("港澳"));
        cityMap.put(INDEX_GANGAO,gangao);
        return new GaodeOffLineMapExpandableListAdapter(getActivity(), LayoutInflater.from(getActivity()), provinceList, cityMap);
    }
    private final static int INDEX_GAIYAO = 0;
    private final static int INDEX_ZHIXIA = 1;
    private final static int INDEX_GANGAO = 2;

    private OfflineMapProvince getSpecialProvince(String province){
        OfflineMapProvince offlineMapProvince = new OfflineMapProvince();
        offlineMapProvince.setProvinceName(province);
        return offlineMapProvince;
    }
    public OfflineMapCity getCity(OfflineMapProvince aMapProvince) {
        OfflineMapCity aMapCity = new OfflineMapCity();
        aMapCity.setCity(aMapProvince.getProvinceName());
        aMapCity.setSize(aMapProvince.getSize());
        aMapCity.setCompleteCode(aMapProvince.getcompleteCode());
        aMapCity.setState(aMapProvince.getState());
        aMapCity.setUrl(aMapProvince.getUrl());
        return aMapCity;
    }
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if(mAnimatedExpandableListView.isGroupExpanded(groupPosition)){
            mAnimatedExpandableListView.collapseGroupWithAnimation(groupPosition);
        }else{
            mAnimatedExpandableListView.expandGroupWithAnimation(groupPosition);
        }
        return true;
    }
    @Override
    public void onDownload(int i, int i2, String s) {

    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s2) {

    }

    private class GaodeOffLineMapExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter{
        private Context mContext;
        private List<OfflineMapProvince> provinceList = new ArrayList<OfflineMapProvince>();// 保存一级目录的省直辖市
        private HashMap<Object, List<OfflineMapCity>> cityMap = new HashMap<Object, List<OfflineMapCity>>();// 保存二级目录的市
        private LayoutInflater layoutInflater;
        public GaodeOffLineMapExpandableListAdapter(Context context, LayoutInflater layoutInflater
                ,List<OfflineMapProvince> provinceList, HashMap<Object, List<OfflineMapCity>> cityMap){
            mContext = context;
            this.layoutInflater    = layoutInflater;
            this.provinceList      = provinceList;
            this.cityMap           = cityMap;
        }
        @Override
        public int getGroupCount() {
            return provinceList.size();
        }
        @Override
        public Object getGroup(int groupPosition) {
            return provinceList.get(groupPosition);
        }
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return cityMap.get(groupPosition).get(childPosition);
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        @Override
        public boolean hasStableIds() {
            return true;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) convertView = layoutInflater.inflate(R.layout.adapter_item_gaodeofflinemap_level_group, null);
            ((TextView)convertView.findViewById(R.id.name)).setText(
                    ((OfflineMapProvince) getGroup(groupPosition)).getProvinceName()
            );
            ((ImageView)convertView.findViewById(R.id.arr)).setImageResource(
                    isExpanded ? R.drawable.arr_down : R.drawable.arr_right
            );
            return convertView;
        }
        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            return getGOChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        }
        @Override
        public int getRealChildrenCount(int groupPosition) {
            return cityMap.get(groupPosition).size();
        }
        //for we don't swipe item.
//        private View getSwipeGOChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
//            SwipeMenuLayout layout = null;
//            if (convertView == null) {
//                View contentView = getGOChildView(groupPosition, childPosition, isLastChild, convertView, parent);
//                SwipeMenu menu = new SwipeMenu(mContext);
//                createMenu(menu);
//                SwipeMenuView menuView = new SwipeMenuView(menu);
//                menuView.setOnSwipeItemClickListener(new SwipeMenuView.OnSwipeItemClickListener() {
//                    @Override
//                    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
//                        Log.d("setOnSwipeItemClickListener ->" + index);
//                    }
//                });
//                layout = new SwipeMenuLayout(contentView, menuView,null, null);
//            } else {
//                layout = (SwipeMenuLayout) convertView;
//                layout.closeMenu();
//            }
//            layout.setPosition(childPosition);
//            return layout;
//        }
//        public void createMenu(SwipeMenu menu) {
//            // Test Code
//            SwipeMenuItem item = new SwipeMenuItem(mContext);
//            item.setTitle("Item 1");
//            item.setBackground(new ColorDrawable(Color.GRAY));
//            item.setWidth(300);
//            menu.addMenuItem(item);
//
//            item = new SwipeMenuItem(mContext);
//            item.setTitle("Item 2");
//            item.setBackground(new ColorDrawable(Color.RED));
//            item.setWidth(300);
//            menu.addMenuItem(item);
//        }
        public View getGOChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.adapter_item_gaodeoffinemap_level_1, null);
                holder      = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            OfflineMapCity offlineMapCity = (OfflineMapCity) getChild(groupPosition, childPosition);
            bindchildview(holder, offlineMapCity);
            return convertView;
        }
        private void bindchildview(ViewHolder viewHolder, OfflineMapCity offlineMapCity){
            viewHolder.cityName.setText(offlineMapCity.getCity());
            viewHolder.citySize.setText(Utils.getMBFromSize(mContext, offlineMapCity.getSize()));
            int state = offlineMapCity.getState();
            switch(state){
                case OfflineMapStatus.SUCCESS:{
                    
                }break;
            }
        }
        class ViewHolder {
            TextView cityName;
            TextView citySize;
            TextView cityDown;
            public ViewHolder(View view) {
                cityName = (TextView) view.findViewById(R.id.name);
                citySize = (TextView) view.findViewById(R.id.size);
                cityDown = (TextView) view.findViewById(R.id.down);
            }
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
