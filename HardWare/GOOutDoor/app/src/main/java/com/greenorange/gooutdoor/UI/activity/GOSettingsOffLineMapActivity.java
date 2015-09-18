package com.greenorange.gooutdoor.UI.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.greenorange.gooutdoor.UI.fragment.BaseFragment;
import com.greenorange.gooutdoor.UI.fragment.FragmentGaodeOffLineMapCityList;
import com.greenorange.gooutdoor.UI.fragment.FragmentGaodeOffLineMapDownManager;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Dao.MapDao;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.gooutdoor.framework.widget.SlidingTabs.SlidingTabLayout;
import com.greenorange.outdoorhelper.R;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/10.
 */
public class GOSettingsOffLineMapActivity extends BaseSwapBackActionBarActivity {
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goofflinemap);
        initActionBar(true, R.layout.layout_settings_offlinemap_toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initViewPagerResource();
        initSlideTab();
    }
    private void initSlideTab() {
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.goapplication_main_color));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
    }
    @Override
    public android.support.v7.app.ActionBar.LayoutParams getActionLayoutParams(){
        return new android.support.v7.app.ActionBar.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }
    ////////////////////////////////////////////////////
    private void initViewPagerResource(){
        MapDao mapDao = (MapDao) mDaoManager.getManager(Dao.MapDao);
        int maptype = mapDao.getCurrentMAPTYPE();
        switch (maptype){
            case MAPTYPE.MAPTYPE_GAODE:{
                mViewPager.setAdapter(new Adapter(getSupportFragmentManager(), getGaodeOfflineMapFragment(), getDefaultTitles()));
            }break;
            default:
                throw new ApplicationException("why get a unknow maptype here " + mapDao.getMAPTYPEString(maptype));
        }
    }
    private List<Fragment> getGaodeOfflineMapFragment(){
        return Arrays.asList(
                (Fragment)new FragmentGaodeOffLineMapCityList(),
                new FragmentGaodeOffLineMapDownManager()
        );
    }
    private List<String> getDefaultTitles(){
        return Arrays.asList(
                getString(R.string.fragment_settings_offlinemap_title_listcity),
                getString(R.string.fragment_settings_offlinemap_title_down)
        );
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    private class Adapter extends FragmentPagerAdapter {
        private List<Fragment> mList;
        private List<String>   mTabTitles;
        public Adapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mList = fragments;
            mTabTitles = titles;
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
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles.get(position);
        }
    }
}
