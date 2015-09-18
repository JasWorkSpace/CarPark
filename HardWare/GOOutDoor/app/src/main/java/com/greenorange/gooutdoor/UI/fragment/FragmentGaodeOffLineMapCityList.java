package com.greenorange.gooutdoor.UI.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.greenorange.gooutdoor.Util.FormatUtils;
import com.greenorange.gooutdoor.View.layout.OfflineMapExpandableList;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Utils.AndroidUtils;
import com.greenorange.gooutdoor.framework.Utils.Utils;
import com.greenorange.gooutdoor.framework.widget.AnimatedExpandableListView;
import com.greenorange.outdoorhelper.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/9/2.
 */
public class FragmentGaodeOffLineMapCityList extends BaseFragment implements ExpandableListView.OnGroupClickListener, OfflineMapManager.OfflineMapDownloadListener {
    private OfflineMapManager mOfflineMapManager;
    private GaodeOffLineMapExpandableListAdapter  mGaodeOffLineMapExpandableListAdapter;
    private OfflineMapExpandableList mAnimatedExpandableListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOfflineMapManager = new OfflineMapManager(getActivity(), this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offlinemap_gaode_listcity, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAnimatedExpandableListView = (OfflineMapExpandableList) view.findViewById(R.id.animateexpandablelistview);
    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAnimatedExpandableListView.setOnGroupClickListener(this);
        mAnimatedExpandableListView.setAdapter(mGaodeOffLineMapExpandableListAdapter = getAdapter());
        mAnimatedExpandableListView.setOnChildClickListener(mGaodeOffLineMapExpandableListAdapter);
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
    public void onDownload(int status, int completeCode, String downName) {
        Log.d("onDownload status="+status+" ,completeCode="+completeCode+" ,downName="+downName);
        mGaodeOffLineMapExpandableListAdapter.notifyDataSetChanged();
    }
    @Override
    public void onCheckUpdate(boolean b, String s) {
        Log.d("onCheckUpdate b="+b+" ,"+s);
        mGaodeOffLineMapExpandableListAdapter.notifyDataSetChanged();
    }
    @Override
    public void onRemove(boolean b, String s, String s2) {
        Log.d("onRemove b="+b+" ,"+s+" ,"+s2);
        mGaodeOffLineMapExpandableListAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //it will case offlinemanager stop.
        //if(mOfflineMapManager != null)mOfflineMapManager.destroy();
    }
    private class GaodeOffLineMapExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter implements ExpandableListView.OnChildClickListener {
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
            bindchildview(holder, offlineMapCity, childPosition == 0);
            return convertView;
        }
        private boolean isDownLoaded(OfflineMapCity offlineMapCity, boolean isprovince){
            try {
                if(isprovince){
                    List<OfflineMapProvince> download = mOfflineMapManager.getDownloadOfflineMapProvinceList();
                    for(OfflineMapProvince offlineMapProvince : download){
                        if(TextUtils.equals(offlineMapCity.getCity(), offlineMapProvince.getProvinceName())){
                            return true;
                        }
                    }
                }else {
                    List<OfflineMapCity> downloaded = mOfflineMapManager.getDownloadOfflineMapCityList();
                    for (OfflineMapCity offlineMap : downloaded) {
                        if (TextUtils.equals(offlineMapCity.getCity(), offlineMap.getCity())) {
                            return true;
                        }
                    }
                }
            }catch (Throwable e){Log.d("get isDownLoaded fail "+e.toString());}
            return false;
        }
        private boolean isDownLoading(OfflineMapCity offlineMapCity, boolean ispro){
            try {
                if(ispro){
                    List<OfflineMapProvince> download = mOfflineMapManager.getDownloadingProvinceList();
                    for(OfflineMapProvince offlineMapProvince : download){
                        if(TextUtils.equals(offlineMapProvince.getProvinceName(), offlineMapCity.getCity())){
                            return OfflineMapStatus.LOADING == offlineMapProvince.getState();
                        }
                    }
                }else {
                    List<OfflineMapCity> downloading = mOfflineMapManager.getDownloadingCityList();
                    for (OfflineMapCity offlineMap : downloading) {
                        if (TextUtils.equals(offlineMapCity.getCity(), offlineMap.getCity())) {
                            return OfflineMapStatus.LOADING == offlineMap.getState();
                        }
                    }
                }
            }catch (Throwable e){Log.d("get isDownLoading fail "+e.toString());}
            return false;
        }
        private void bindchildview(ViewHolder viewHolder, OfflineMapCity offlineMapCity, boolean isprovince) {
            viewHolder.cityName.setText(offlineMapCity.getCity());
            viewHolder.citySize.setText(Utils.getMBFromSize(mContext, offlineMapCity.getSize()));
            if (isprovince) {
                OfflineMapProvince realOfflineMapProvince = mOfflineMapManager.getItemByProvinceName(offlineMapCity.getCity());
                bindChildView(viewHolder, realOfflineMapProvince.getState(), realOfflineMapProvince.getcompleteCode());
            } else {
                OfflineMapCity realOfflineMapCity = mOfflineMapManager.getItemByCityName(offlineMapCity.getCity());
                bindChildView(viewHolder, realOfflineMapCity.getState(), realOfflineMapCity.getcompleteCode());
            }
        }
        private void bindChildView(ViewHolder viewHolder, int state, int com){
            switch(state){
                case OfflineMapStatus.SUCCESS:{
                    setStateIcon(viewHolder, R.drawable.maps_done);
                }break;
                case OfflineMapStatus.LOADING:{
                    setStateText(viewHolder, FormatUtils.getPre(mContext, com));
                }break;
                case OfflineMapStatus.PAUSE:{
                    setStateText(viewHolder, R.string.gaode_offline_map_download_pauseing);
                }break;
                case OfflineMapStatus.WAITING:{
                    setStateText(viewHolder, R.string.gaode_offline_map_download_waiting);
                }break;
                case OfflineMapStatus.UNZIP:{
                    setStateText(viewHolder, R.string.gaode_offline_map_download_uzip);
                }break;
                default:{
                    setStateIcon(viewHolder, R.drawable.maps_download);
                }
            }
        }
        private void setStateIcon(ViewHolder viewHolder, int id){
            mPicasso.load(id).into(viewHolder.cityDownIcon);
            viewHolder.cityDownIcon.setVisibility(View.VISIBLE);
            viewHolder.cityDown.setVisibility(View.GONE);
        }
        private void setStateText(ViewHolder viewHolder, int id){
            setStateText(viewHolder, mContext.getString(id));
        }
        private void setStateText(ViewHolder viewHolder, String text){
            viewHolder.cityDownIcon.setVisibility(View.GONE);
            viewHolder.cityDown.setText(text);
            viewHolder.cityDown.setVisibility(View.VISIBLE);
        }
        private void checknetWorkAndDownloadOfflineMapCity(final OfflineMapCity offlineMapCity, final boolean pro){
            if(AndroidUtils.isWifiConnected(getActivity())){
                downloadOfflineMapCity(offlineMapCity, pro);
            }else {
                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.offline_map_download_dialog_message)
                        .setNegativeButton(R.string.offline_map_download_dialog_cancel, null)
                        .setPositiveButton(R.string.offline_map_download_dialog_download, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadOfflineMapCity(offlineMapCity, pro);
                            }
                        })
                        .create()
                        .show();
            }
        }
        private void downloadOfflineMapCity(OfflineMapCity offlineMapCity, boolean pro){
            try {
                Log.d("downloadOfflineMapCity "+ offlineMapCity.getCity() + " ,pro="+pro);
                if(pro){
                    mOfflineMapManager.downloadByProvinceName(offlineMapCity.getCity());
                }else {
                    mOfflineMapManager.downloadByCityName(offlineMapCity.getCity());
                }
            } catch (Throwable e) {Log.d("downloadOfflineMapCity fail " + e.toString());}
        }
        private void pausedownloadOfflineMapCity(OfflineMapCity offlineMapCity, boolean pro){
            try {
                Log.d("pausedownloadOfflineMapCity "+ offlineMapCity.getCity() + " ,pro="+pro);
                if(pro){
                    mOfflineMapManager.getDownloadingProvinceList().remove(
                            mOfflineMapManager.getItemByProvinceName(offlineMapCity.getCity())
                    );
                }else {
                    mOfflineMapManager.getDownloadingCityList().remove(offlineMapCity);
                }
            } catch (Throwable e) {
                Log.d("pausedownloadOfflineMapCity fail "+e.toString());
                e.printStackTrace();
            }
        }
        private void debugdownling(){
            List<OfflineMapCity> citydownint = mOfflineMapManager.getDownloadingCityList();
            Log.d("citydownint " + (citydownint == null ? "is null" : citydownint.size()));
            if(citydownint != null){
                for(OfflineMapCity offlineMapCity : citydownint){
                    Log.d("downlingCity="+offlineMapCity.getCity() +" ,state="+offlineMapCity.getState() +
                              " ,com="+offlineMapCity.getcompleteCode());
                }
            }
            List<OfflineMapProvince> province = mOfflineMapManager.getDownloadingProvinceList();
            Log.d("province " + (province == null ? "is null" : province.size()));
            if(province != null){
                for(OfflineMapProvince offlineMapProvince : province){
                    Log.d("downingProvince=" + offlineMapProvince.getProvinceName()+
                            " ,state="+offlineMapProvince.getState()
                            + " ,com="+offlineMapProvince.getcompleteCode());
                }
            }
            List<OfflineMapCity> downloaded = mOfflineMapManager.getDownloadOfflineMapCityList();
            Log.d("downloaded " + (citydownint == null ? "is null" : citydownint.size()));
            if(citydownint != null){
                for(OfflineMapCity offlineMapCity : downloaded){
                    Log.d("downloaded="+offlineMapCity.getCity() +" ,state="+offlineMapCity.getState() +
                            " ,com="+offlineMapCity.getcompleteCode());
                }
            }
            List<OfflineMapProvince> provinceed = mOfflineMapManager.getDownloadOfflineMapProvinceList();
            Log.d("downloadedProvince " + (province == null ? "is null" : province.size()));
            if(province != null){
                for(OfflineMapProvince offlineMapProvince : provinceed){
                    Log.d("downloadedProvince=" + offlineMapProvince.getProvinceName()+
                            " ,state="+offlineMapProvince.getState()
                            + " ,com="+offlineMapProvince.getcompleteCode());
                }
            }
        }
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            OfflineMapCity offlineMapCity = (OfflineMapCity) getChild(groupPosition, childPosition);
            Log.d("onChildClick--> groupPosition="+groupPosition+" ,childPosition="+childPosition
                    +" ,city="+offlineMapCity.getCity()+" ,state="+offlineMapCity.getState());
            boolean isPro = (childPosition ==0);
            if(isDownLoaded(offlineMapCity, isPro) || isDownLoading(offlineMapCity, isPro)){
                return true;
            }
            switch(offlineMapCity.getState()){
                case OfflineMapStatus.LOADING:{
                    pausedownloadOfflineMapCity(offlineMapCity, isPro);
                }break;
                case OfflineMapStatus.CHECKUPDATES://un start
                case OfflineMapStatus.STOP:
                case OfflineMapStatus.WAITING:
                case OfflineMapStatus.PAUSE:{
                    checknetWorkAndDownloadOfflineMapCity(offlineMapCity, isPro);
                }break;
                case OfflineMapStatus.UNZIP:
                case OfflineMapStatus.ERROR:
                case OfflineMapStatus.SUCCESS:
                case OfflineMapStatus.EXCEPTION_AMAP:
                case OfflineMapStatus.EXCEPTION_NETWORK_LOADING:
                case OfflineMapStatus.EXCEPTION_SDCARD:
                case OfflineMapStatus.START_DOWNLOAD_FAILD:{

                }break;
            }
            debugdownling();
            return true;
        }

        class ViewHolder {
            TextView cityName;
            TextView citySize;
            TextView cityDown;
            ImageView cityDownIcon;
            public ViewHolder(View view) {
                cityName = (TextView) view.findViewById(R.id.name);
                citySize = (TextView) view.findViewById(R.id.size);
                cityDown = (TextView) view.findViewById(R.id.down);
                cityDownIcon = (ImageView) view.findViewById(R.id.downicon);
            }
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
        private long lastnotifyDataSetChanged = 0;
        @Override
        public void notifyDataSetChanged() {
            long now = System.currentTimeMillis();
            if(now - lastnotifyDataSetChanged < 700)return;
            lastnotifyDataSetChanged = now;
            super.notifyDataSetChanged();
        }
    }
}
