package com.greenorange.gooutdoor.UI.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Dao.DaoManager;
import com.greenorange.gooutdoor.framework.Dao.EventDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.squareup.picasso.Picasso;

/**
 * Created by JasWorkSpace on 15/3/12.
 */
public class BaseFragment extends Fragment {
    public   DaoManager mDaoManager;
    public   EventDao   mEventDao;
    public   Picasso    mPicasso;
    private  int mFragmentState = FRAGMENT_STATE_UNKNOW;
    public   final static int FRAGMENT_STATE_UNKNOW  = 0;
    public   final static int FRAGMENT_STATE_RESUMED = 1;
    public   final static int FRAGMENT_STATE_PAUSEED = 2;//we only want to know this 2 state

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDaoManager = GOApplication.getDaoManager();
        mEventDao   = (EventDao) mDaoManager.getManager(Dao.EventDao);
        mPicasso    = Picasso.with(getActivity());
    }
    @Override
    public void onResume() {
        super.onResume();
        mEventDao.register(this);
        mFragmentState = FRAGMENT_STATE_RESUMED;
    }
    @Override
    public void onPause() {
        mEventDao.unregister(this);
        super.onPause();
        mFragmentState = FRAGMENT_STATE_PAUSEED;
    }
    public int getFragmentState(){
        return mFragmentState;
    }

}
