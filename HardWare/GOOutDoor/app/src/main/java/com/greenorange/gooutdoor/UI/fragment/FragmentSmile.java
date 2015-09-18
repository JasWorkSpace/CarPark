package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.entity.SmileData;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.SmileDao;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.greenorange.outdoorhelper.R;
import java.util.ArrayList;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by JasWorkSpace on 15/8/11.
 */
public class FragmentSmile extends BaseFragment implements ViewPager.OnPageChangeListener {
    private SmileDao mSmileDao;
    private viewpagerAdapter mViewpagerAdapter;
    private ViewPager mViewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSmileDao = (SmileDao) GOApplication.getDaoManager().getManager(Dao.SmileDao);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smile, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewpagerAdapter = new viewpagerAdapter(mSmileDao.getAllSmileData()));
        mViewPager.setOnPageChangeListener(this);
    }
    @Override
    public void onPageScrolled(int i, float v, int i2) {}
    @Override
    public void onPageSelected(int i) {
        Util.notifySmileModeChange(((SmileData)mViewpagerAdapter.getItemData(mViewPager.getCurrentItem())).getName());
    }
    @Override
    public void onPageScrollStateChanged(int i) {}
    @Override
    public void onPause() {
        super.onPause();
        mSmileDao.setSelectionSmileData(mViewpagerAdapter.getItemData(mViewPager.getCurrentItem()));
    }
    @Override
    public void onResume() {
        super.onResume();
        loadSelectionView();
    }
    private void loadSelectionView(){
        SmileData smileData = mSmileDao.getSelectionSmileData();
        if(smileData != null){
            int index = mViewpagerAdapter.getItemPosition(smileData);
            if(index >= 0){
                mViewPager.setCurrentItem(index);
            }
        }
    }
    private class viewpagerAdapter extends PagerAdapter{
        private List<View> mList;
        private List<SmileData> mSmileDatas;
        public viewpagerAdapter(List<SmileData> smileDataList){
            mSmileDatas = smileDataList;
            mList       = getListView(mSmileDatas);
        }
        @Override
        public int getCount() {
            return mList.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(mList.get(position));
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            container.addView(mList.get(position));
            return mList.get(position);
        }
        public List<View> getList() {
            return mList;
        }
        private List<View> getListView(List<SmileData> smileDataList){
            List<View> mSmileView      = new ArrayList<View>();
            for(SmileData smileData : smileDataList){
                mSmileView.add(getView(smileData));
            }
            return mSmileView;
        }
        private View getView(SmileData smileData){
            GifImageView gifImageView = new GifImageView(getActivity());
            gifImageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            gifImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            gifImageView.setBackgroundResource(smileData.getId());
            return gifImageView;
        }
        public SmileData getItemData(int position){
            if(position>=0 && position < mSmileDatas.size()){
                return mSmileDatas.get(position);
            }
            return null;
        }
    }
}
