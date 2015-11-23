package com.greenorange.myuicontantsbackup.Task;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.greenorange.myuicontantsbackup.Account.UserInfo;
import com.greenorange.myuicontantsbackup.BackUpApplication;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.Task.SMS.SmsData;
import com.greenorange.myuicontantsbackup.Task.SMS.SmsInfoUtil;

import java.util.ArrayList;

/**
 * Created by JasWorkSpace on 15/11/18.
 */
public class BackupSmsTask extends BaseTask {
    public BackupSmsTask(Context context, UserInfo userInfo, int type){
        super(context, userInfo, type);
    }
    @Override
    public String getBackupData() {
        ArrayList<SmsData> smsList = new ArrayList<SmsData>();
        Uri uri = Uri.parse("content://sms/");
        Cursor cusr = null;
        try {
            ContentResolver resolver = BackUpApplication.getInstance().getContentResolver();
            cusr = resolver.query(uri, SmsInfoUtil.ORIGINAL_PROJECTION, null, null, null);
            while (cusr!=null && cusr.moveToNext()) {
                if(getTaskState() != TASK_STATE_WORK)return "";
                SmsData smsData = SmsInfoUtil.getSmsData(cusr);
                if(smsData != null)smsList.add(smsData);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            if(cusr != null)cusr.close();
        }
        if(smsList!=null && smsList.size() > 0){
            if(getTaskState() != Task.TASK_STATE_WORK)return "";
            return mGson.toJson(smsList);
        }
        return "";
    }
    @Override
    public boolean RestoreData(String data) {
        if(!TextUtils.isEmpty(data)) {
            try {
                ArrayList<SmsData> datas = mGson.fromJson(data,
                        new TypeToken<ArrayList<SmsData>>(){}.getType());
                if(datas != null && datas.size() > 0){
                    Uri uri = Uri.parse("content://sms/");
                    ContentResolver resolver = BackUpApplication.getInstance().getContentResolver();
                    for(SmsData smsData : datas){
                        if(getTaskState() != Task.TASK_STATE_WORK)return false;
                        ContentValues contentValues = SmsInfoUtil.getContentValues(smsData);
                        if(smsData != null){
                            Uri result = resolver.insert(uri, contentValues);
                            Log.d("smsData insert result-->"+result);
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                Log.i("BackupSmsTask RestoreData fail-->" + e.toString());
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean isNeedBackupOrRestore() {
        return true;
    }
    @Override
    public void onTaskStateChange() {

    }
    @Override
    public int getTaskType() {
        if(mBaseType == TASK_BASETYPE_BACKUP || mBaseType == TASK_BASETYPE_AUTO_BACKUP) {
            return TASK_TYPE_BACKUP_SMS;
        }else if(mBaseType == TASK_BASETYPE_RESTORE || mBaseType == TASK_BASETYPE_AUTO_RESTORE){
            return TASK_TYPE_RESTORE_SMS;
        }
        throw new RuntimeException("unknow task base type");
    }
}
