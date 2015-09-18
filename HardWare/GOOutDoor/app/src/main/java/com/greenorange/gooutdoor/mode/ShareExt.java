package com.greenorange.gooutdoor.mode;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;

import com.greenorange.gooutdoor.UI.activity.BaseActionBarActivity;
import com.greenorange.gooutdoor.UI.activity.GOMainActivity;
import com.greenorange.gooutdoor.UI.fragment.BaseMapFragment;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.Interface.iMapScreenShotListener;
import com.greenorange.gooutdoor.framework.Utils.ViewUtils;
import com.greenorange.outdoorhelper.R;

import java.io.File;

/**
 * Created by JasWorkSpace on 15/8/6.
 */
public class ShareExt extends AsyncTask<Object, Integer, String> implements iMapScreenShotListener {
    private BaseMapFragment mBaseMapFragment;
    public GOMainActivity  mGOMainActivity;
    public ShareExt(GOMainActivity goMainActivity){
        mGOMainActivity  = goMainActivity;
        mBaseMapFragment = mGOMainActivity.getMapFragment();
    }
    @Override
    protected String doInBackground(Object... params) {
        String filedir = (String)params[0];
        if((!TextUtils.isEmpty(filedir)) && isActivityUseAble()
                && mBaseMapFragment != null && !isCancelled()
                && isScreenReady()){
            Log.d("ShareExt start " + filedir);
            if(mBaseMapFragment.getMaoScreenShot(this)
                    && WaitMapBitmap()){
                if(isActivityUseAble() && !isCancelled()){
//                    ViewUtils.savePicture(ViewUtils.createWaterMakerBitmap(mMapBitmap,null,
//                            mGOMainActivity.getString(R.string.goapplication_app_name)), "./sdcard/3.jpg");
                    View view = mGOMainActivity.getWaterMakerView();
                    if(view != null && mMapBitmap != null){
                        if(ViewUtils.savePicture(ViewUtils.createWaterMakerBitmap(mMapBitmap,
                                   ViewUtils.getBitmapFromView(view)), filedir)){
                            return filedir;
                        }
                    }
                }
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        releaseBitmap();
        if(!TextUtils.isEmpty(s)){
            File file = new File(s);
            if(file.exists() && isActivityUseAble()){
                ViewUtils.sharePicture(mGOMainActivity, Uri.fromFile(file));
            }
        }
    }
    private void releaseBitmap(){
        try{
            if(mMapBitmap != null && !mMapBitmap.isRecycled()){
                mMapBitmap.recycle();
            }
        }catch (Throwable e){
            Log.e("releaseBitmap fail -->" + e.toString());}
        mMapBitmap = null;
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
        notifyWait();
    }
    public boolean isActivityUseAble(){
        return mGOMainActivity!=null
                && mGOMainActivity.getBaseActivityState() == BaseActionBarActivity.STATE_RESUMED;
    }
    private boolean isScreenReady(){
        if(!mBaseMapFragment.isScreenShotEnable()){
            //AndroidUtils.Toast(mGOMainActivity, R.string.toolbar_setting, Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
    private Bitmap mMapBitmap;
    @Override
    public boolean onScreenShotSuccess(int mapType, Bitmap bitmap, String screenshotfile) {
        Log.d("onScreenShotSuccess mapType="+mapType+", screenshotfile="+screenshotfile);
        mMapBitmap = bitmap;
        notifyWait();
        return false;
    }
    @Override
    public boolean onScreenShotFail(int mapType, String result) {
        Log.d("onScreenShotFail mapType="+mapType+", result="+result);
        releaseBitmap();
        notifyWait();
        return false;
    }
    private Object mWaitingObject = new Object();
    private boolean WaitMapBitmap(){
        synchronized (mWaitingObject){
            try {
                mWaitingObject.wait(2000);//wait is
            } catch (Throwable e) {
                return false;
            }
            return true;
        }
    }
    private void notifyWait(){
        synchronized (mWaitingObject){
            mWaitingObject.notifyAll();
        }
    }
}
