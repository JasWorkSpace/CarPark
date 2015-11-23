package com.greenorange.myuicontantsbackup.Task;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.greenorange.myuicontantsbackup.Account.UserInfo;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.Task.Contacts.ContactsInfoUtil;
import com.greenorange.myuicontantsbackup.Task.Contacts.UserContact;
import com.greenorange.myuicontantsbackup.Task.Contacts.UserContactData;

import java.util.ArrayList;

/**
 * Created by JasWorkSpace on 15/11/18.
 */
public class BackupContactsTask extends BaseTask {

    public BackupContactsTask(Context context, UserInfo userInfo, int type){
        super(context, userInfo, type);
    }

    private ArrayList<UserContactData> getUserContactDataByContractID(Context context, int contractID){
        ArrayList<UserContactData> userContactList = new ArrayList<UserContactData>();
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + contractID + "/data");
            cursor = context.getContentResolver().query(uri, new String[]{"mimetype", "data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8", "data9", "data10", "data11", "data12", "data13", "data14", "data15"}, null, null, ContactsContract.Data.RAW_CONTACT_ID);
            while (cursor!=null && cursor.moveToNext()) {
                if(getTaskState() != Task.TASK_STATE_WORK)return null;
                UserContactData data = ContactsInfoUtil.getUserContactData(cursor);
                if (data == null) continue;
                userContactList.add(data);
            }
        }catch (Throwable e){}finally {
            if(cursor != null)cursor.close();
        }
        return userContactList;
    }
    @Override
    public String getBackupData() {
        ArrayList<UserContact> resutsList = new ArrayList<UserContact>();
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        ContentResolver resolver = getApplicationContext().getContentResolver();
        Cursor cursor = null;
        try {
            cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
            while (cursor.moveToNext()) {
                if(getTaskState() != Task.TASK_STATE_WORK)return "";
                int contractID = cursor.getInt(0);//
                ArrayList<UserContactData> datas = getUserContactDataByContractID(getApplicationContext(), contractID);
                if (datas != null && datas.size() > 0) {
                    UserContact userContact = new UserContact();
                    userContact.setContact(datas);
                    resutsList.add(userContact);
                }
                if(getTaskState() != Task.TASK_STATE_WORK)return "";
            }
        }catch (Throwable e){}finally {
            if(cursor != null)cursor.close();
        }
        if(resutsList != null && resutsList.size() > 0){
            if(getTaskState() != Task.TASK_STATE_WORK)return "";
            return mGson.toJson(resutsList);
        }
        return "";
    }
    private long getNewcontractID(){
        Uri uri1 = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = getApplicationContext().getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.RawContacts.AGGREGATION_MODE, ContactsContract.RawContacts.AGGREGATION_MODE_SUSPENDED);
        contentValues.put(ContactsContract.RawContacts.ACCOUNT_TYPE, "");
        contentValues.put(ContactsContract.RawContacts.ACCOUNT_NAME, "");
        Uri result = resolver.insert(uri1, contentValues);
        //Log.d("getNewcontractID-->" + (result==null?"":result.toString()));
        return ContentUris.parseId(result);
    }
    private boolean insertUserContact(UserContact userContact){
        if(userContact == null)return false;
        ContentResolver resolver = getApplicationContext().getContentResolver();
        Uri uri2 = Uri.parse("content://com.android.contacts/data");
        ArrayList<UserContactData> restoredata = userContact.getContact();
        if(restoredata != null && restoredata.size() > 0){
            long id = getNewcontractID();
            for(UserContactData userContactData : restoredata) {
                if (getTaskState() != TASK_STATE_WORK) return false;
                ContentValues contentValues = ContactsInfoUtil.getContentValues(userContactData);
                if(contentValues != null){
                    contentValues.put(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, id);
                    resolver.insert(uri2, contentValues);
                }
            }
        }
        return true;
    }
    @Override
    public boolean RestoreData(String data) {
        if(!TextUtils.isEmpty(data)){
            try{
                ArrayList<UserContact> datas = mGson.fromJson(data,
                        new TypeToken<ArrayList<UserContact>>(){}.getType());
                if(datas != null && datas.size() >0){
                    for(UserContact userContact : datas){
                        if(getTaskState() != TASK_STATE_WORK)return false;
                        Log.d("BackupContactsTask RestoreData still alive");
                        insertUserContact(userContact);
                    }
                }
            }catch (Throwable e){
                e.printStackTrace();
                Log.i("BackupContactsTask RestoreData fail-->" + e.toString());
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
            return TASK_TYPE_BACKUP_CONTACTS;
        }else if(mBaseType == TASK_BASETYPE_RESTORE || mBaseType == TASK_BASETYPE_AUTO_RESTORE){
            return TASK_TYPE_RESTORE_CONTACTS;
        }
        throw new RuntimeException("unknow task base type");
    }
}
