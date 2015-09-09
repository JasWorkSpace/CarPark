package com.greenorange.gooutdoor.UI.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greenorange.gooutdoor.UI.fragment.BaseFragment;
import com.greenorange.gooutdoor.UI.fragment.FragmentGaodeOffLineMapCityList;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Dao.MapDao;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.outdoorhelper.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/10.
 */
public class GOSettingsOffLineMapActivity extends BaseSwapBackActionBarActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goofflinemap);
        loadActionBar();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOnPageChangeListener(this);
        initViewPagerResource();
    }
    public void loadActionBar() {
        View toolbar = initActionBar(true, R.layout.layout_settings_offlinemap_toolbar);
        (mCityList = (TextView) toolbar.findViewById(R.id.citylist)).setOnClickListener(this);
        (mDownloadList = (TextView) toolbar.findViewById(R.id.downloadlist)).setOnClickListener(this);
    }
    @Override
    public android.support.v7.app.ActionBar.LayoutParams getActionLayoutParams(){
        return new android.support.v7.app.ActionBar.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.citylist:
            case R.id.downloadlist:{
                setSelected(v.getId());
            }break;
        }
    }
    private TextView mCityList;
    private TextView mDownloadList;
    private int      mSelectionId;

    private void setSelected(View view){
        if(mSelectionId == view.getId())return;
        mSelectionId = view.getId();
        mCityList.setSelected( view == mCityList);
        mDownloadList.setSelected( view == mDownloadList);
    }
    private void setSelected(int id){
        switch(id){
            case R.id.citylist:{
                mViewPager.setCurrentItem(INDEX_CITYLIST);
                setSelected(mCityList);
            }break;
            case R.id.downloadlist:{
                mViewPager.setCurrentItem(INDEX_DOWNLOADED);
                setSelected(mDownloadList);
            }break;
        }
    }
    private final static int INDEX_CITYLIST   = 0;
    private final static int INDEX_DOWNLOADED = 1;
    private void changeSelected(int index){
        switch (index){
            case INDEX_CITYLIST: setSelected(mCityList);break;
            case INDEX_DOWNLOADED: setSelected(mDownloadList);break;
            default: throw new ApplicationException("which do you selected " + index);
        }
    }
    ////////////////////////////////////////////////////
    private void initViewPagerResource(){
        MapDao mapDao = (MapDao) mDaoManager.getManager(Dao.MapDao);
        int maptype = mapDao.getCurrentMAPTYPE();
        switch (maptype){
            case MAPTYPE.MAPTYPE_GAODE:{
                mViewPager.setAdapter(new Adapter(getSupportFragmentManager(), getGaodeOfflineMapFragment()));
            }break;
            default:
                throw new ApplicationException("why get a unknow maptype here " + mapDao.getMAPTYPEString(maptype));
        }
        changeSelected(mViewPager.getCurrentItem());
    }
    private List<Fragment> getGaodeOfflineMapFragment(){
        return Arrays.asList(
                (Fragment)new FragmentGaodeOffLineMapCityList(),
                new BaseFragment()
        );
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    private class Adapter extends FragmentPagerAdapter {
        private List<Fragment> mList;
        public Adapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mList = fragments;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
        @Override
        public Fragment getItem(int i) {
            if(mList == null)return null;
            return mList.get(i);
        }
        @Override
        public int getCount() {
            if(mList == null) return 0;
            return mList.size();
        }
    }
    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }
    @Override
    public void onPageSelected(int i) {
        changeSelected(i);
    }
    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
