package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.entity.SmileData;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.SmileDao;
import com.greenorange.gooutdoor.framework.widget.HorizontalListView;
import com.greenorange.outdoorhelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/11.
 */
public class FragmentSmileSettings extends BaseFragment {

    private HorizontalListView mHorizontalListView;
    private SmileDao           mSmileDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSmileDao = (SmileDao) GOApplication.getDaoManager().getManager(Dao.SmileDao);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_settings_smile, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHorizontalListView = (HorizontalListView) view.findViewById(R.id.smilelistview);
        mHorizontalListView.setAdapter(new SmileAdapter(mSmileDao.getAllSmileData()));
    }
    private class SmileAdapter extends BaseAdapter{
        private List<SmileData> mSmileDataList;
        private LayoutInflater  mLayoutInflater;
        public SmileAdapter(List<SmileData> smileDataList){
            mSmileDataList = smileDataList;
            if(mSmileDataList == null){
                mSmileDataList = new ArrayList<SmileData>();
            }
            mLayoutInflater = LayoutInflater.from(getActivity());
        }
        @Override
        public int getCount() {
            return mSmileDataList.size();
        }
        @Override
        public Object getItem(int position) {
            return mSmileDataList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.layout_smilesettings_smileitem, parent, false);
                holder = new ViewHolder();
                holder.mImageView = (ImageView) convertView.findViewById(R.id.smile_icon);
                holder.mTextView = (TextView) convertView.findViewById(R.id.smile_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            bindview((SmileData) getItem(position), holder);
            return convertView;
        }
        private void bindview(SmileData smileData, ViewHolder holder){
            mPicasso.load(smileData.getThumbnail())
                    .placeholder(R.drawable.colorsmile_normal_selector)
                    .error(R.drawable.colorsmile_normal_selector)
                    .resizeDimen(R.dimen.fragment_settings_smile_smile_icon_layout_width, R.dimen.fragment_settings_smile_smile_icon_layout_height)
                    .into(holder.mImageView);
            holder.mTextView.setText(smileData.getName());
        }
    }
    private class ViewHolder{
        public ImageView mImageView;
        public TextView  mTextView;
    }

}
