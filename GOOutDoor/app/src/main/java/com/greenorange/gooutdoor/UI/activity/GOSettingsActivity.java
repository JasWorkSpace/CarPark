package com.greenorange.gooutdoor.UI.activity;

import com.greenorange.gooutdoor.UI.activity.swipebacklayout.BaseSettingsActivity;
import com.greenorange.gooutdoor.UI.fragment.FragmentRecordSports;
import com.greenorange.gooutdoor.UI.fragment.FragmentSettings;
import com.greenorange.gooutdoor.UI.fragment.FragmentSmile;
import com.greenorange.gooutdoor.UI.fragment.FragmentSmileSettings;
import com.greenorange.gooutdoor.UI.fragment.FragmentUI;
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
        public String getSettingsFragmentName() {
            return FragmentUI.class.getName();
        }

    }
    public static class GORecordActivity extends BaseSettingsActivity{
        @Override
        public String getSettingsFragmentName() {
            return FragmentRecordSports.class.getName();
        }
        @Override
        public String getSettingsTitle() {
            return getString(R.string.toolbar_record);
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
