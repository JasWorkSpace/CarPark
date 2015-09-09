package com.greenorange.gooutdoor.UI.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.framework.Dao.EventDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.widget.calligraphy.CalligraphyContextWrapper;
import com.greenorange.gooutdoor.framework.Dao.DaoManager;

/**
 * Created by JasWorkSpace on 15/4/10.
 */
public class BaseActionBarActivity extends ActionBarActivity{
    private TextView   mToolBarTileView;
    public  DaoManager mDaoManager;
    public  EventDao   mEventDao;
    public  Toolbar    mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(isSystemUIHide()){
//            requestWindowFeature(Window.FEATURE_ACTION_BAR);
//            requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
//        }
        mDaoManager = GOApplication.getDaoManager();
        mEventDao   = (EventDao) mDaoManager.getManager(Dao.EventDao);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public boolean isHasActionBar(){
        return true;
    }
    public boolean isSystemUIHide(){return false;}
    public View initActionBar(boolean showhome){
        return initActionBar(showhome, R.layout.toolbar_title);
    }
    public View initActionBar(boolean showhome,int layoutId) {
        mToolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if (!isHasActionBar()) {
            if (mToolBar != null) mToolBar.setVisibility(View.GONE);
            return null;
        }
        if (mToolBar != null) setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(showhome);
        actionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = getLayoutInflater().inflate(layoutId, null);
        mToolBarTileView = (TextView) mCustomView.findViewById(R.id.toolbar_title);
        actionBar.setCustomView(mCustomView, getActionLayoutParams());
        return mCustomView;
    }
    public ActionBar.LayoutParams getActionLayoutParams(){
        return new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
    }
    public void setTitle(int titleID){
        setTitle(getString(titleID));
    }
    public void setTitle(String title){
        if(mToolBarTileView != null){
            mToolBarTileView.setText(title);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mEventDao.register(this);
        mEventDao.onResume(this);
        BaseActivityState = STATE_RESUMED;
    }
    @Override
    protected void onPause() {
        mEventDao.unregister(this);
        mEventDao.onPause(this);
        super.onPause();
        BaseActivityState = STATE_PAUSED;
    }
    public int getBaseActivityState(){
        return BaseActivityState;
    }

    public final static int STATE_UNKNOW  = 0;
    public final static int STATE_RESUMED = 1;
    public final static int STATE_PAUSED  = 2;

    private int BaseActivityState = STATE_UNKNOW;
}
