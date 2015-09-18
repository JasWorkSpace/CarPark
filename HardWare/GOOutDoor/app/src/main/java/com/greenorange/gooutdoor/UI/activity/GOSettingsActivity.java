package com.greenorange.gooutdoor.UI.activity;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.UI.activity.swipebacklayout.BaseSettingsActivity;
import com.greenorange.gooutdoor.UI.fragment.FragmentSettings;
import com.greenorange.gooutdoor.UI.fragment.FragmentSmile;
import com.greenorange.gooutdoor.UI.fragment.FragmentSmileSettings;
import com.greenorange.gooutdoor.UI.fragment.FragmentUI;
import com.greenorange.gooutdoor.Util.FormatUtils;
import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.framework.Dao.ApplicationDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Utils.ViewUtils;
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/7/29.
 */
public class GOSettingsActivity{

    public static class GOSettingsMainActivity extends BaseSettingsActivity{
        @Override
        public String getSettingsFragmentName() {
            return FragmentSettings.class.getName();
        }
        @Override
        public String getSettingsTitle() {
            return getString(R.string.toolbar_setting);
        }
    }
    public static class GOSettingsSmileActivity extends BaseSettingsActivity{
        @Override
        public String getSettingsFragmentName() {
            return FragmentSmileSettings.class.getName();
        }
        @Override
        public String getSettingsTitle() {
            return getString(R.string.layout_settings_preference_smile);
        }
    }
    public static class GOActivity extends BaseSettingsActivity{
        @Override
        public String getSettingsFragmentName() {return FragmentUI.class.getName();}
    }
    public static class GORecordActivity extends BaseSettingsActivity{
        @Override
        protected void onResume() {
            super.onResume();
            ((CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar)).setTitle(getString(R.string.toolbar_record));
            setData(this);
            findViewById(R.id.sharebutton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        share();
                    }catch (Throwable e){}
                }
            });
        }
        private synchronized void share(){
            ApplicationDao applicationDao = (ApplicationDao) GOApplication.getDaoManager().getManager(Dao.ApplicationDao);
            UserDBData userDBData = applicationDao.getUser();
            String share = null;
            if(userDBData != null && userDBData.getTotal_calorie()>0
                    && userDBData.getTotal_count() >0 && userDBData.getTotal_time()>0){
                share = getString(R.string.share_string_alldata,
                        FormatUtils.getFloat3to2(this, (float) (userDBData.getTotal_distance() / 1000.0f)),
                        FormatUtils.getFloat3to2(this, (float) (userDBData.getTotal_calorie() / 1000.0f)));
            }
            if(TextUtils.isEmpty(share)){
                share = getString(R.string.share_string_nodata);
            }
            ViewUtils.shareString(this, share);
        }
        private void setData(Context context){
            ApplicationDao applicationDao = (ApplicationDao) GOApplication.getDaoManager().getManager(Dao.ApplicationDao);
            UserDBData userDBData = applicationDao.getUser();
            ((TextView) findViewById(R.id.distance)).setText(
                            FormatUtils.getFloat3to2(context, (float) (userDBData.getTotal_distance() / 1000.0f)));
            ((TextView) findViewById(R.id.count)).setText(
                    String.valueOf(userDBData.getTotal_count()));
            ((TextView) findViewById(R.id.calorie)).setText(
                            FormatUtils.getFloat3to2(context, (float) (userDBData.getTotal_calorie() / 1000.0f)));
        }
        @Override
        public int getContentViewId() {
            return R.layout.activity_gorecordsportsactivity;
        }
    }
    public static class GOSmileActivity extends BaseSettingsActivity{
        @Override
        public String getSettingsFragmentName() {
            return FragmentSmile.class.getName();
        }
        @Override
        public boolean isHasActionBar() {
            return false;
        }
        @Override
        public boolean isSystemUIHide() {
            return true;
        }
    }
}
