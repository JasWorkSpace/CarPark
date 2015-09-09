package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import com.amap.api.maps.offlinemap.OfflineMapStatus;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.outdoorhelper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/25.
 */
public class FragmentUI extends BaseFragment implements OfflineMapManager.OfflineMapDownloadListener {
    private OfflineMapManager amapManager = null;// 离线地图下载控制器
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amapManager  = new OfflineMapManager(getActivity(), this);
        provinceList = amapManager.getOfflineMapProvinceList();
        initOfflineMapResource(provinceList, cityMap);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tesr, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandable);
    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
    }
    ////////////////////////////////////////////////////
    private ExpandableListView expandableListView;
    private void initAdapter(){
        isOpen = new boolean[provinceList.size()];
        // 为列表绑定数据源
        expandableListView.setAdapter(new adapter());
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ;
                isOpen[groupPosition] = false;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                isOpen[groupPosition] = true;
            }
        });
        // 设置二级item点击的监听器
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                try {
                    // 下载全国概要图、直辖市、港澳离线地图数据
                    if (groupPosition == 0 || groupPosition == 1 || groupPosition == 2) {
                        isStart = amapManager.downloadByProvinceName(cityMap.get(groupPosition).get(childPosition).getCity());
                    }
                    // 下载各省的离线地图数据
                    else {
                        // 下载各省列表中的省份离线地图数据
                        if (childPosition == 0) {
                            isStart = amapManager.downloadByProvinceName(provinceList.get(groupPosition).getProvinceName());
                        }
                        // 下载各省列表中的城市离线地图数据
                        else if (childPosition > 0) {
                            isStart = amapManager.downloadByCityName(cityMap.get(groupPosition).get(childPosition).getCity());
                        }
                    }
                } catch (AMapException e) {
                    e.printStackTrace();
                    Log.e("离线地图下载抛出异常" + e.getErrorMessage());
                }
                // 保存当前正在正在下载省份或者城市的position位置
                if (isStart) {
                    mGroupPosition = groupPosition;
                    mChildPosition = childPosition;
                }
                return false;
            }
        });
    }
    private class adapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return provinceList.size();
        }
        /**
         * 获取一级标签内容
         */
        @Override
        public Object getGroup(int groupPosition) {
            return provinceList.get(groupPosition).getProvinceName();
        }
        /**
         * 获取一级标签的ID
         */
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        /**
         * 获取一级标签下二级标签的总数
         */
        @Override
        public int getChildrenCount(int groupPosition) {
            return cityMap.get(groupPosition).size();
        }
        /**
         * 获取一级标签下二级标签的内容
         */
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return cityMap.get(groupPosition).get(childPosition).getCity();
        }
        /**
         * 获取二级标签的ID
         */
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        /**
         * 指定位置相应的组视图
         */
        @Override
        public boolean hasStableIds() {
            return true;
        }
        /**
         * 对一级标签进行设置
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.adapter_item_gaodeofflinemap_level_group, null);
            }
            ((TextView)convertView.findViewById(R.id.name)).setText(provinceList.get(groupPosition).getProvinceName());
            ((ImageView)convertView.findViewById(R.id.arr)).setImageDrawable(getResources().getDrawable(
                    isOpen[groupPosition] ? R.drawable.arr_down : R.drawable.arr_right));
            return convertView;
        }
        /**
         * 对一级标签下的二级标签进行设置
         */
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.adapter_item_gaodeoffinemap_level_1, null);
                holder      = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            holder.cityName.setText(cityMap.get(groupPosition).get(childPosition).getCity());
            holder.citySize.setText((cityMap.get(groupPosition).get(childPosition).getSize())/ (1024 * 1024f) + "MB");
            if (cityMap.get(groupPosition).get(childPosition).getState() == OfflineMapStatus.SUCCESS) {
                holder.cityDown.setText("安装完成");
            } else if (cityMap.get(groupPosition).get(childPosition).getState() == OfflineMapStatus.LOADING) {
                if (groupPosition == groupPosition && childPosition == childPosition) {
                    holder.cityDown.setText("正在下载" + mCompleteCode + "%");
                }
            } else if (cityMap.get(groupPosition).get(childPosition).getState() == OfflineMapStatus.UNZIP) {
                holder.cityDown.setText("正在解压" + mCompleteCode + "%");
            } else if (cityMap.get(groupPosition).get(childPosition).getState() == OfflineMapStatus.LOADING) {
                holder.cityDown.setText("下载");
            }
            return convertView;
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
        /**
         * 当选择子节点的时候，调用该方法
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<OfflineMapProvince> provinceList = new ArrayList<OfflineMapProvince>();// 保存一级目录的省直辖市
    private HashMap<Object, List<OfflineMapCity>> cityMap = new HashMap<Object, List<OfflineMapCity>>();// 保存二级目录的市
    private void initOfflineMapResource(List<OfflineMapProvince> provinceList, HashMap<Object, List<OfflineMapCity>> cityMap){
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
        provinceList.add(0, getSpecialProvince("全国概要图"));
        cityMap.put(0, gaiyao);
        provinceList.add(1, getSpecialProvince("直辖市"));
        cityMap.put(1,zhixia);
        provinceList.add(2, getSpecialProvince("港澳"));
        cityMap.put(2,gangao);
    }
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
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private int mGroupPosition;// 记录一级目录的position
    private int mChildPosition;// 记录二级目录的position
    private int mCompleteCode;// 记录下载比例
    private boolean isStart = false;// 判断是否开始下载,true表示开始下载，false表示下载失败
    private boolean[] isOpen;// 记录一级目录是否打开

    @Override
    public void onDownload(int status, int completeCode, String downName) {
        switch (status) {
            case OfflineMapStatus.SUCCESS:
                changeOfflineMapTitle(OfflineMapStatus.SUCCESS);
                break;
            case OfflineMapStatus.LOADING:
                mCompleteCode = completeCode;
                break;
            case OfflineMapStatus.UNZIP:
                mCompleteCode = completeCode;
                changeOfflineMapTitle(OfflineMapStatus.UNZIP);
                break;
            case OfflineMapStatus.WAITING:
                break;
            case OfflineMapStatus.PAUSE:
                break;
            case OfflineMapStatus.STOP:
                break;
            case OfflineMapStatus.ERROR:
                break;
            default:
                break;
        }
        ((adapter)expandableListView.getAdapter()).notifyDataSetChanged();
    }
    private void changeOfflineMapTitle(int status) {
        if (mGroupPosition == 0 || mGroupPosition == 1 || mGroupPosition == 2) {
            cityMap.get(mGroupPosition).get(mChildPosition).setState(status);
        } else {
            if (mChildPosition == 0) {
                for (int i = 0; i < cityMap.get(mGroupPosition).size(); i++) {
                    cityMap.get(mGroupPosition).get(i).setState(status);//
                }
            } else {
                cityMap.get(mGroupPosition).get(mChildPosition).setState(status);
            }
        }
    }
}
