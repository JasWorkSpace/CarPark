package com.greenorange.myuicontantsbackup.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.greenorange.myuicontantsbackup.Account.MyUIAccount;

/**
 * Created by JasWorkSpace on 15/10/29.
 */
public class BasePreferenceFragment extends PreferenceFragment {

    public   MyUIAccount myUIAccount;

    private  int mFragmentState = FRAGMENT_STATE_UNKNOW;
    public   final static int FRAGMENT_STATE_UNKNOW  = 0;
    public   final static int FRAGMENT_STATE_RESUMED = 1;
    public   final static int FRAGMENT_STATE_PAUSEED = 2;//we only want to know this 2 state

    public   String getFragmentTag(){
        return "BasePreferenceFragment";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(myUIAccount == null){
            myUIAccount = MyUIAccount.getInstance(getActivity());
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mFragmentState = FRAGMENT_STATE_RESUMED;
    }
    @Override
    public void onPause() {
        super.onPause();
        mFragmentState = FRAGMENT_STATE_PAUSEED;
    }
    public int getFragmentState(){
        return mFragmentState;
    }
}
