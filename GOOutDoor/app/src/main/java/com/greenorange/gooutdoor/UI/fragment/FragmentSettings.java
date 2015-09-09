package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.greenorange.gooutdoor.UI.activity.GOSettingsActivity;
import com.greenorange.gooutdoor.UI.activity.GOSettingsOffLineMapActivity;
import com.greenorange.gooutdoor.Util.ActivityUtils;
import com.greenorange.gooutdoor.View.layout.SettingsPreference;
import com.greenorange.gooutdoor.framework.Dao.Interface.SharePreference;
import com.greenorange.gooutdoor.framework.Utils.SharePerferenceUtils;
import com.greenorange.outdoorhelper.R;


/**
 * Created by JasWorkSpace on 15/8/7.
 */
public class FragmentSettings extends BaseFragment implements SettingsPreference.ChangedListener, SettingsPreference.TreeClickListener {

    private SettingsPreference mKeepScreenOn;
    private SettingsPreference mOfflineMap;
    private SettingsPreference mCheckUpdate;
    private SettingsPreference mAboutAs;
    private SettingsPreference mSmile;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        (mKeepScreenOn = (SettingsPreference) view.findViewById(R.id.settings_keepscreenon)).setonChangedListener(this);
        (mOfflineMap   = (SettingsPreference) view.findViewById(R.id.settings_offlinemap)).setonTreeClickListener(this);
        (mCheckUpdate  = (SettingsPreference) view.findViewById(R.id.settings_checkupdate)).setonTreeClickListener(this);
        (mAboutAs      = (SettingsPreference) view.findViewById(R.id.settings_about)).setonTreeClickListener(this);
        (mSmile        = (SettingsPreference) view.findViewById(R.id.settings_smile)).setonTreeClickListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void updateUI(){
        mKeepScreenOn.setSwitchChecked(
                (Boolean)SharePerferenceUtils.getValue(SharePreference.KEY_SETTINGS_KEEPSCREENON, mKeepScreenOn.getSwitchChecked())
        );
    }
    @Override
    public boolean onCheckedChanged(SettingsPreference settingsPreference, CompoundButton buttonView, boolean isChecked) {
        if(settingsPreference == mKeepScreenOn) {
            SharePerferenceUtils.setValue(SharePreference.KEY_SETTINGS_KEEPSCREENON, isChecked);
            return true;
        }
        return false;
    }
    @Override
    public boolean onTreeClick(SettingsPreference settingsPreference) {
        if(settingsPreference == mOfflineMap) {
            ActivityUtils.startActivityWithAnimation(getActivity(), GOSettingsOffLineMapActivity.class);
        }else if(settingsPreference == mCheckUpdate){
            Toast.makeText(getActivity(), R.string.layout_settings_update_islast, Toast.LENGTH_SHORT).show();
        }else if(settingsPreference == mAboutAs){

        }else if(settingsPreference == mSmile){
            ActivityUtils.startActivityWithAnimation(getActivity(), GOSettingsActivity.GOSettingsSmileActivity.class);
        }
        return true;
    }
}
