package com.greenorange.myuicontantsbackup.Task;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.greenorange.myuicontantsbackup.Account.UserInfo;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.Task.CallLog.CalllogData;
import com.greenorange.myuicontantsbackup.Task.CallLog.CalllogInfoUtil;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by JasWorkSpace on 15/11/10.
 */
public class BackupCallLogTask extends BaseTask {

    public BackupCallLogTask(Context context, UserInfo userInfo, int type){
        super(context, userInfo, type);
    }

    @Override
    public String getBackupData() {
        ArrayList <CalllogData> callList = new ArrayList<CalllogData>();
        Cursor cusr = null;
        try {
            cusr = getApplicationContext().getContentResolver()
                    .query(CallLog.Calls.CONTENT_URI, CalllogInfoUtil.ORIGINAL_PROJECTION, null, null, null);
            while (cusr!=null && cusr.moveToNext()) {
                if(getTaskState() != Task.TASK_STATE_WORK)return "";
                CalllogData calllogData = CalllogInfoUtil.getCalllogData(cusr);
                if (calllogData != null) callList.add(calllogData);
            }
        }catch (Throwable e){
            Log.d("getPhoneCalllog fail -->"+e.toString());
        }finally {
            if(cusr != null)cusr.close();
        }
        if(callList != null && callList.size() >0){
            if(getTaskState() != Task.TASK_STATE_WORK)return "";
            return mGson.toJson(callList);
        }
        return "";
    }
    private boolean instertPhoneCallLog(ArrayList<CalllogData> datas){
        if(datas != null && datas.size() >0){
            Uri uri = Uri.parse("content://call_log/calls");
            ContentResolver resolver = getApplicationContext().getContentResolver();
            for(Iterator<CalllogData> iterator = datas.iterator(); iterator.hasNext();){
                if(getTaskState() != TASK_STATE_WORK)return false;
                ContentValues contentValues = CalllogInfoUtil.getContentValues(iterator.next());
                if(contentValues != null){
                    resolver.insert(uri, contentValues);
                }
            }
        }
        return true;
    }

    @Override
    public boolean RestoreData(String data) {
        if(!TextUtils.isEmpty(data)){
            try {
                ArrayList<CalllogData> datas = mGson.fromJson(data, new TypeToken<ArrayList<CalllogData>>(){}.getType());
                return instertPhoneCallLog(datas);
            }catch (Throwable e){
                e.printStackTrace();
                Log.i("BackupCallLogTask RestoreData fail-->"+e.toString());
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
            return TASK_TYPE_BACKUP_CELLLOG;
        }else if(mBaseType == TASK_BASETYPE_RESTORE || mBaseType == TASK_BASETYPE_AUTO_RESTORE){
            return TASK_TYPE_RESTORE_CALLLOG;
        }
        throw new RuntimeException("unknow task base type");
    }

}
