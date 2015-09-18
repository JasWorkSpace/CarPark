package com.greenorange.gooutdoor.framework.Dao.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.framework.Config;
import com.greenorange.gooutdoor.framework.Dao.FlashDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.FlashTYPE;
import com.greenorange.gooutdoor.framework.Dao.SharePreferenceDao;
import com.greenorange.gooutdoor.framework.Utils.Util;

/**
 * Created by JasWorkSpace on 15/4/12.
 */
public class FlashDaoImpl implements FlashDao {
    private Camera  mCamera;
    private Object  mCameraObject = new Object();
    private boolean isCameraSupport = false;
    private int[][] mConfigs;
    public FlashDaoImpl(Context context){
        isCameraSupport = hasCamera(context);
        initConfig(context);
    }
    private void initConfig(Context context){
        String[] configs = context.getResources().getStringArray(R.array.config_flash_frq);
        mConfigs = new int[configs.length][4];
        for(int mode = 0; mode < configs.length ; mode++){
            String[] config = configs[mode].split(";");
            for(int params = 0; (params < config.length && params < 4); params++){
                mConfigs[mode][params] = Integer.parseInt(config[params]);
            }
        }
    }
    private int[] getConfigFrq(int type){
        for(int index = 0; index < mConfigs.length; index++){
            if(type == mConfigs[index][0]){
                return mConfigs[index];
            }
        }
        throw new IllegalArgumentException("pls config it config.xml");
    }
    @Override
    public void open() {
        if(isCameraSupport){
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessage(MSG_FLASH_STATECHANGE);
        }
    }
    @Override
    public void close() {
        if(isCameraSupport){
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessage(MSG_FLASH_CLOSE);
        }
    }
    private void tearDown() {
        if(isCameraSupport){
            synchronized (mCameraObject){
                if(mCamera != null){
                    mCamera.release();
                    mCamera = null;
                }
                isOpen  = false;
                isFlash = false;
            }
        }
        mHandler.removeCallbacksAndMessages(null);
    }
    @Override
    public boolean isFlash() {
        return isFlash;
    }
    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public int getCurrentFlashMode() {
        SharePreferenceDao sharePreferenceDao = (SharePreferenceDao) GOApplication.getDaoManager().getManager(Dao.SharePerferenceDao);
        return (Integer)sharePreferenceDao.getValue(FlashTYPE.KEY_Current_FlashTYPE, FlashTYPE.FLASH_TYPE_CLOSED);
    }
    @Override
    public boolean setFlashMode(int type) {
        SharePreferenceDao sharePreferenceDao = (SharePreferenceDao) GOApplication.getDaoManager().getManager(Dao.SharePerferenceDao);
        int lasttype = (Integer)sharePreferenceDao.getValue(FlashTYPE.KEY_Current_FlashTYPE, FlashTYPE.FLASH_TYPE_CLOSED);
        boolean result = sharePreferenceDao.setValue(FlashTYPE.KEY_Current_FlashTYPE, type);
        if(result){
            Util.notifyFlashmodeChanged(lasttype, type);
        }
        return result;
    }
    @Override
    public int getNextFlashMode() {
        return getConfigFrq(getCurrentFlashMode())[3];
    }

    private boolean hasCamera(Context context) {
        final PackageManager pm = context.getPackageManager();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                    || pm.hasSystemFeature("android.hardware.camera.front");
        }
        return pm.hasSystemFeature("android.hardware.camera.any");
    }
    private void initCamera(){
        synchronized (mCameraObject) {
            if (mCamera == null) {
                try {
                    mCamera = Camera.open();
                    isOpen = true;
                    return;
                } catch (Throwable e) {
                }
                notifyOpenFlashFail();
            }
        }
    }
    private void notifyOpenFlashFail(){
        Util.produceEventMSG(EventID.ID_MSG_FlashDao, FlashDao.MSG_FLASH_OPENFAIL, null);
        setFlashMode(FlashTYPE.FLASH_TYPE_CLOSED);
    }
    ///////////////////////////
    private boolean isFlash = false;
    private boolean isOpen  = false;

    private int flash_opentime  = Config.FLASH_TIMEOUT_OPEN;
    private int flash_closetime = Config.FLASH_TIMEOUT_CLOSE;

    private void openFlash(){
        synchronized (mCameraObject){
            if(isOpen && mCamera != null){
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
                isFlash = true;
            }
        }
    }
    private void closeFlash(){
        synchronized (mCameraObject){
            if(mCamera != null){
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
                isFlash = false;
            }
        }
    }
    private final static int MSG_FLASH_STATECHANGE = 0;
    private final static int MSG_FLASH_TIMEOUT     = 1;
    private final static int MSG_FLASH_CLOSE       = 2;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_FLASH_STATECHANGE:{
                    updateStateChange();
                }break;
                case MSG_FLASH_TIMEOUT:{
                    troggle();
                }break;
                case MSG_FLASH_CLOSE:{
                    setFlashMode(FlashTYPE.FLASH_TYPE_CLOSED);
                    tearDown();
                }break;
            }
        }
    };
    private synchronized void updateStateChange(){
        int mode = getCurrentFlashMode();
        if (mode == FlashTYPE.FLASH_TYPE_CLOSED) {
            close();
        } else if (mode == FlashTYPE.FLASH_TYPE_FLASH
                || mode == FlashTYPE.FLASH_TYPE_OPENALWAY) {
            initCamera();
            int[] config = getConfigFrq(getCurrentFlashMode());
            flash_opentime = config[1];
            flash_closetime = config[2];
            troggle();
        }
    }
    private void troggle(){
        mHandler.removeCallbacksAndMessages(null);
        if(isFlash){
            closeFlash();
            if(flash_closetime > 0)mHandler.sendEmptyMessageDelayed(MSG_FLASH_TIMEOUT, flash_closetime);
        } else {
            if(flash_opentime >= 0){
                openFlash();
                if(flash_opentime > 0)mHandler.sendEmptyMessageDelayed(MSG_FLASH_TIMEOUT, flash_opentime);
            } else {
                closeFlash();
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("FlashDaoImpl{")
                .append("isCameraSupport="+isCameraSupport)
                .append(", isOpen="+isOpen)
                .append(", isFlash="+isFlash)
                .append(", opentime="+flash_opentime)
                .append(", closetime="+flash_closetime)
                .append("}");
        return sb.toString();
    }
}
