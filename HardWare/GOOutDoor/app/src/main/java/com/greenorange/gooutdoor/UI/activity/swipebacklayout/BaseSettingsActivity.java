package com.greenorange.gooutdoor.UI.activity.swipebacklayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import com.greenorange.gooutdoor.UI.activity.BaseSwapBackActionBarActivity;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Utils.AndroidUtils;
import com.greenorange.gooutdoor.framework.Utils.ViewUtils;
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/8/10.
 */
public class BaseSettingsActivity extends BaseSwapBackActionBarActivity {
    public final static String EXTRA_TITLE_ID   = "BaseSettingsActivity_EXTRA_TITLE_ID";
    public final static String EXTRA_TITLE_TEXT = "BaseSettingsActivity_EXTRA_TITLE_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        if(isSystemUIHide()){
            AndroidUtils.setSystemUIVisibility(findViewById(R.id.viewroot));
            ViewUtils.hideWindow(this);
        }
        loadActionBar();
        setTitleFromIntent();
        switchFragment();
    }
    public int getContentViewId(){
        return R.layout.activity_gosettings;
    }
    public void loadActionBar(){
        initActionBar(true, R.layout.toolbar_title);
    }
    private void setTitleFromIntent(){
        Intent intent = getIntent();
        if(intent != null){
            int  titleid = intent.getIntExtra(EXTRA_TITLE_ID, -1);
            if(titleid > 0){
                setTitle(titleid);
                return;
            }
            String title = intent.getStringExtra("baseactivity_title");
            if (title != null) {
                setTitle(title);
                return;
            }
        }
        String titlein = getSettingsTitle();
        if(!TextUtils.isEmpty(titlein)){
            setTitle(titlein);
        }
    }
    public String getSettingsTitle(){
        return null;
    }
    public String getSettingsFragmentName(){
        return null;//nothing return here.
    }
    public Fragment switchFragment(){
        String fragment = getSettingsFragmentName();
        try{
            if(TextUtils.isEmpty(fragment))return null;
            Fragment f = Fragment.instantiate(BaseSettingsActivity.this, fragment);
            return showFragment(f, false);
        }catch (Throwable e){Log.e(e.toString());}
        return null;
    }
    public Fragment showFragment(Fragment fragment, boolean addtoback){
        try{
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            if(addtoback){
                transaction.addToBackStack("backStack");
            }
            transaction.commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
        }catch (Throwable e){Log.e(e.toString());}
        return fragment;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isSystemUIHide()){
            ViewUtils.showWindow(this);
        }
    }
}