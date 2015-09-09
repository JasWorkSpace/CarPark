package com.greenorange.gooutdoor.UI.activity.swipebacklayout.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import com.greenorange.gooutdoor.UI.activity.BaseActionBarActivity;
import com.greenorange.gooutdoor.UI.activity.swipebacklayout.SwipeBackLayout;
import com.greenorange.gooutdoor.UI.activity.swipebacklayout.Utils;
import com.greenorange.gooutdoor.UI.fragment.BaseMapFragment;
import com.greenorange.gooutdoor.UI.fragment.FragmentGaodeMap;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Dao.MapDao;
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/3/26.
 */
public class SwipeBackActionBarActivity extends BaseActionBarActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }
    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }
    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }
    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(! this.isFinishing()) {
            //super.onBackPressed();
            scrollToFinishActivity();
            //ActivityUtils.setActivityAnimation(this);
        }
    }
}