package com.greenorange.gooutdoor.UI.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import com.amap.api.maps.offlinemap.OfflineMapStatus;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.widget.PinnedSectionListView;
import com.greenorange.outdoorhelper.R;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/25.
 */
public class FragmentGaodeOffLineMapDownManager extends BaseFragment implements OfflineMapManager.OfflineMapDownloadListener {
    private OfflineMapManager mOfflineMapManager = null;// 离线地图下载控制器
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOfflineMapManager  = new OfflineMapManager(getActivity(), this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offlinemap_gaode_downloadmanager, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = (PinnedSectionListView) view.findViewById(R.id.expandable);
    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reloadResource();
    }
    ////////////////////////////////////////////////////
    private class Item {

        public static final int TYPE_HEADER             = 0;
        public static final int TYPE_ITEM               = 1;

        public static final int TYPE_HEADER_DOWNLOADING = 0;
        public static final int TYPE_HEADER_DOWNLOADED  = 1;
        public static final int TYPE_ITEM_DOWNLOADING   = 0;
        public static final int TYPE_ITEM_DOWNLOADED    = 1;
        ////////////////////////////////////////////////////////
        private int TYPE;
        ////////////////////
        private int     itemtype;
        private boolean isProvince;
        private Object  mObject;
        ////////////////
        private int     headtype;
        private String  headText;
        public Item(int type, boolean isProvince,Object object){
            this.itemtype   = type;
            this.isProvince = isProvince;
            mObject         = object;
            TYPE            = TYPE_ITEM;
        }
        public Item(int type){
            TYPE = TYPE_HEADER;
            headtype = type;
            headText = getString(headtype == TYPE_HEADER_DOWNLOADING
                    ? R.string.offline_map_download_header_downloading : R.string.offline_map_download_header_downloaded);
        }
        public String toHeadString(){
            StringBuffer sb = new StringBuffer();
            sb.append("Item{")
                    .append("headtype="+headtype)
                    .append(" ,headText="+headText)
                    .append(" ,TYPE="+TYPE)
                    .append("}");
            return sb.toString();
        }
        public String toItemString(){
            StringBuffer sb = new StringBuffer();
            sb.append("Item{")
                    .append("itemtype="+itemtype)
                    .append(" ,isProvince="+isProvince)
                    .append(" ,TYPE=" + TYPE)
                    .append("}");
            return sb.toString();
        }
    }
    private PinnedSectionListView expandableListView;
    private Adapter initAdapter(){
        Adapter adapter = new Adapter(getActivity(), R.layout.layout_fragment_offlinemap_downloadmanager);
        //downloading
        List<OfflineMapProvince> mProvinces = mOfflineMapManager.getDownloadingProvinceList();
        List<OfflineMapCity>      mMapCitys = mOfflineMapManager.getDownloadingCityList();
        if ((mProvinces != null && mProvinces.size() > 0) || (mMapCitys != null && mMapCitys.size() > 0)) {
            adapter.add(new Item(Item.TYPE_HEADER_DOWNLOADING));//add for header
            if (mProvinces != null && mProvinces.size() > 0) {
                for (OfflineMapProvince offlineMapProvince : mProvinces) {
                    adapter.add(new Item(Item.TYPE_ITEM_DOWNLOADING, true, offlineMapProvince));
                }
            }
            if (mMapCitys != null && mMapCitys.size() > 0) {
                for (OfflineMapCity offlineMapCity : mMapCitys) {
                    adapter.add(new Item(Item.TYPE_ITEM_DOWNLOADING, false, offlineMapCity));
                }
            }
        }
        //downloaded
        mProvinces = mOfflineMapManager.getDownloadOfflineMapProvinceList();
        mMapCitys  = mOfflineMapManager.getDownloadOfflineMapCityList();
        if ((mProvinces != null && mProvinces.size() > 0) || (mMapCitys != null && mMapCitys.size() > 0)) {
            adapter.add(new Item(Item.TYPE_HEADER_DOWNLOADED));//add for header
            if (mProvinces != null && mProvinces.size() > 0) {
                for (OfflineMapProvince offlineMapProvince : mProvinces) {
                    adapter.add(new Item(Item.TYPE_ITEM_DOWNLOADED, true, offlineMapProvince));
                }
            }
            if (mMapCitys != null && mMapCitys.size() > 0) {
                for (OfflineMapCity offlineMapCity : mMapCitys) {
                    adapter.add(new Item(Item.TYPE_ITEM_DOWNLOADED, false, offlineMapCity));
                }
            }
        }
        return adapter;
    }
    private class Adapter extends ArrayAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter{
        public Adapter(Context context, int resource) {
            super(context, resource);
        }
        @Override
        public boolean isItemViewTypePinned(int viewType) {
            return viewType == Item.TYPE_HEADER;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = View.inflate(getActivity(), R.layout.layout_fragment_offlinemap_downloadmanager, null);
            }
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if(viewHolder == null){
                convertView.setTag(viewHolder = new ViewHolder(convertView));
            }
            bindView(viewHolder, getItem(position));
            return convertView;
        }
        private void bindView(ViewHolder viewHolder, Item item) {
            if(item.TYPE == Item.TYPE_HEADER){
                viewHolder.mItem.setVisibility(View.GONE);
                viewHolder.mHeader_name.setText(item.headText);
                viewHolder.mHeader.setVisibility(View.VISIBLE);
            }else if(item.TYPE == Item.TYPE_ITEM){
                viewHolder.mHeader.setVisibility(View.GONE);
                if(item.mObject instanceof OfflineMapProvince){
                    OfflineMapProvince offlineMapProvince = (OfflineMapProvince) item.mObject;
                    bindItemView(viewHolder, offlineMapProvince.getProvinceName(), offlineMapProvince.getState(), true, item.itemtype);
                }else if(item.mObject instanceof OfflineMapCity){
                    OfflineMapCity offlineMapCity = (OfflineMapCity) item.mObject;
                    bindItemView(viewHolder, offlineMapCity.getCity(), offlineMapCity.getState(), false, item.itemtype);
                }
            }
        }
        private void bindItemView(ViewHolder viewHolder, final String name, int state, final boolean isprovince, int itemtype){
            viewHolder.mItem_name.setText(name);
            viewHolder.mItem_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removedownloadOfflineMapCity(name, isprovince);
                }
            });
            switch (state){
                case OfflineMapStatus.LOADING:{
                    viewHolder.mItem_del.setVisibility(View.GONE);
                    viewHolder.mItem_state.setVisibility(View.VISIBLE);
                    mPicasso.load(R.drawable.maps_pause).into(viewHolder.mItem_state);
                    viewHolder.mItem_state.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pausedownloadOfflineMapCity(name, isprovince);
                        }
                    });
                }break;
                case OfflineMapStatus.CHECKUPDATES://un start
                case OfflineMapStatus.STOP:
                case OfflineMapStatus.WAITING:
                case OfflineMapStatus.PAUSE:{
                    viewHolder.mItem_del.setVisibility(View.GONE);
                    viewHolder.mItem_state.setVisibility(View.VISIBLE);
                    mPicasso.load(R.drawable.maps_download).into(viewHolder.mItem_state);
                    viewHolder.mItem_state.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            downloadOfflineMapCity(name, isprovince);
                        }
                    });
                }break;
                case OfflineMapStatus.UNZIP:
                case OfflineMapStatus.ERROR:
                case OfflineMapStatus.SUCCESS:
                case OfflineMapStatus.EXCEPTION_AMAP:
                case OfflineMapStatus.EXCEPTION_NETWORK_LOADING:
                case OfflineMapStatus.EXCEPTION_SDCARD:
                case OfflineMapStatus.START_DOWNLOAD_FAILD:
                default:{
                    viewHolder.mItem_del.setVisibility(View.VISIBLE);
                    viewHolder.mItem_state.setVisibility(View.GONE);
                }break;
            }
            viewHolder.mItem_del.setVisibility(itemtype == Item.TYPE_HEADER_DOWNLOADED ? View.VISIBLE : View.GONE);
        }
        private class ViewHolder{
            public View mHeader;
            public View mItem;
            public TextView mHeader_name;
            public TextView mItem_name;
            public ImageView mItem_del;
            public ImageView mItem_state;
            public ViewHolder(View view){
                //for header
                mHeader = view.findViewById(R.id.headerlayer);
                mHeader_name = (TextView) view.findViewById(R.id.header);
                //for item
                mItem       = view.findViewById(R.id.itemlayer);
                mItem_name  = (TextView) view.findViewById(R.id.name);
                mItem_del   = (ImageView) view.findViewById(R.id.delete);
                mItem_state = (ImageView) view.findViewById(R.id.control);
            }
        }
        @Override
        public int getViewTypeCount() {
            return 2;
        }
        @Override
        public int getItemViewType(int position) {
            return getItem(position).TYPE;
        }
    }
    private void downloadOfflineMapCity(String name, boolean pro){
        try {
            Log.d("downloadOfflineMapCity " + name+ " ,pro=" + pro);
            if(pro){
                mOfflineMapManager.downloadByProvinceName(name);
            }else {
                mOfflineMapManager.downloadByCityName(name);
            }
        } catch (Throwable e) {Log.d("downloadOfflineMapCity fail " + e.toString());}
    }
    private void pausedownloadOfflineMapCity(String name, boolean pro){
        try {
            Log.d("pausedownloadOfflineMapCity "+ name + " ,pro="+pro);
            mOfflineMapManager.remove(name);
        } catch (Throwable e) {
            Log.d("pausedownloadOfflineMapCity fail "+e.toString());
            e.printStackTrace();
        }
    }
    private void removedownloadOfflineMapCity(String name, boolean pro){
        try {
            Log.d("pausedownloadOfflineMapCity "+ name + " ,pro="+pro);
            mOfflineMapManager.remove(name);
        } catch (Throwable e) {
            Log.d("pausedownloadOfflineMapCity fail "+e.toString());
            e.printStackTrace();
        }
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

    private synchronized void reloadResource(){
        mHandler.removeMessages(MSG_RELOADRESOURCE);
        mHandler.sendEmptyMessage(MSG_RELOADRESOURCE);
    }
    private synchronized void loadResource(){
        if(expandableListView != null){
            expandableListView.setAdapter(initAdapter());
        }
    }
    private final static int MSG_RELOADRESOURCE = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case MSG_RELOADRESOURCE:{
                    loadResource();
                }break;
            }
        }
    };
    ////////////////////////////////////////////
    private int    laststatus;
    private String lastdownName;
    @Override
    public void onDownload(int status, int completeCode, String downName) {
        if(!(TextUtils.equals(downName, lastdownName) && status==laststatus)){
            reloadResource();
        }
    }

    @Override
    public void onCheckUpdate(boolean b, String s) {

    }

    @Override
    public void onRemove(boolean b, String s, String s2) {
        if(!(TextUtils.isEmpty(s2) && TextUtils.isEmpty(s))){
            reloadResource();
        }
    }
}

