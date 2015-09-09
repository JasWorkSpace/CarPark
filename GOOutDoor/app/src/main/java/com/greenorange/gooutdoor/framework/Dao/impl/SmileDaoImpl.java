package com.greenorange.gooutdoor.framework.Dao.impl;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;

import com.greenorange.gooutdoor.entity.SmileData;
import com.greenorange.gooutdoor.framework.Dao.Interface.SharePreference;
import com.greenorange.gooutdoor.framework.Dao.SmileDao;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.gooutdoor.framework.Utils.AndroidUtils;
import com.greenorange.gooutdoor.framework.Utils.SharePerferenceUtils;
import com.greenorange.outdoorhelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/11.
 */
public class SmileDaoImpl implements SmileDao {
    private Context         mContext;
    private List<SmileData> mSmileDatas;
    public SmileDaoImpl(Context context){
        mContext = context;
        initSmile();
    }
    private void initSmile() {
        String[] name = mContext.getResources().getStringArray(R.array.config_smile_file_name);
        String[] file = mContext.getResources().getStringArray(R.array.config_smile_file);
        String[] fileThumbnail = mContext.getResources().getStringArray(R.array.config_smile_thumbnail);
        int[]    fileid        = AndroidUtils.getResourceId(mContext, R.array.config_smile_fileid);
        if(name.length != file.length || name.length != fileThumbnail.length || name.length != fileid.length){
            throw new ApplicationException("config_smile_file_name config_smile_file config_smile_thumbnail why length is no same !!!!");
        }
        mSmileDatas = new ArrayList<SmileData>();
        for(int i =0; i<name.length; i++){
            SmileData smileData = new SmileData();
            smileData.setName(name[i]);
            smileData.setUrl(file[i]);
            smileData.setThumbnail(fileThumbnail[i]);
            smileData.setId(fileid[i]);
            mSmileDatas.add(smileData);
        }
    }
    @Override
    public List<SmileData> getAllSmileData() {
        return mSmileDatas;
    }
    @Override
    public boolean isSmileDataInited() {
        String data = (String)SharePerferenceUtils.getValue(SharePreference.KEY_SMILE_CURRENTDATA, "");
        return !TextUtils.isEmpty(data);
    }
    @Override
    public SmileData getSelectionSmileData() {
        String data = (String)SharePerferenceUtils.getValue(SharePreference.KEY_SMILE_CURRENTDATA, "");
        if(!TextUtils.isEmpty(data)){
            List<SmileData> datas = getAllSmileData();
            for(SmileData smileData : datas){
                if(TextUtils.equals(smileData.getUrl(), data)){
                    return smileData;
                }
            }
        }
        return null;
    }
    @Override
    public boolean setSelectionSmileData(SmileData smileData) {
        if(smileData != null){
            return SharePerferenceUtils.setValue(SharePreference.KEY_SMILE_CURRENTDATA, smileData.getUrl());
        }
        return false;
    }
}
