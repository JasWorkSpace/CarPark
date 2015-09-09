package com.greenorange.gooutdoor.mode;

import android.os.AsyncTask;

import com.greenorange.gooutdoor.UI.activity.BaseActionBarActivity;

/**
 * Created by JasWorkSpace on 15/8/27.
 */
public abstract class ActivityAsyncTask extends AsyncTask {

    public BaseActionBarActivity mBaseActionBarActivity;

    public ActivityAsyncTask(BaseActionBarActivity activity){
        mBaseActionBarActivity = activity;
    }

    public boolean isActivityUseAble(){
        return mBaseActionBarActivity!=null
                && mBaseActionBarActivity.getBaseActivityState() == BaseActionBarActivity.STATE_RESUMED;
    }
}
