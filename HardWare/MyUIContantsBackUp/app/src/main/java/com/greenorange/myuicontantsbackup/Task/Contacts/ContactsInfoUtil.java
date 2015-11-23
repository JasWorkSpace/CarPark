package com.greenorange.myuicontantsbackup.Task.Contacts;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Data;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.greenorange.myuicontantsbackup.R;
import com.greenorange.myuicontantsbackup.utils.Utils;

public class ContactsInfoUtil {


    public static ArrayList<UserContact> getPhoneContacts(Context mContext) {
        ArrayList<UserContact> resutsList = new ArrayList<UserContact>();
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = null;
        try {
            cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
            String qc_phonenumber = mContext.getString(R.string.contacts_qingcheng_number);
            String qc_phonename = mContext.getString(R.string.contacts_qingcheng);
            while (cursor.moveToNext()) {
                int contractID = cursor.getInt(0);//
                ArrayList<UserContactData> datas = getUserContactDataByContractID(mContext, contractID);
                if (datas != null && datas.size() > 0) {
                    UserContact userContact = new UserContact();
                    userContact.setContact(datas);
                    resutsList.add(userContact);
                }
            }
        }catch (Throwable e){}finally {
            if(cursor != null)cursor.close();
        }
        return resutsList;
    }
    private static ArrayList<UserContactData> getUserContactDataByContractID(Context context, int contractID){
        ArrayList<UserContactData> userContactList = new ArrayList<UserContactData>();
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + contractID + "/data");
            cursor = context.getContentResolver().query(uri, new String[]{"mimetype", "data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8", "data9", "data10", "data11", "data12", "data13", "data14", "data15"}, null, null, Data.RAW_CONTACT_ID);
            while (cursor!=null && cursor.moveToNext()) {
                UserContactData data = getUserContactData(cursor);
                if (data == null) continue;
                userContactList.add(data);
            }
        }catch (Throwable e){}finally {
            if(cursor != null)cursor.close();
        }
        return userContactList;
    }
    public static UserContactData getUserContactData(Cursor cursor1){
        if(cursor1 == null)return null;
        UserContactData data = new UserContactData();
        data.setData1(cursor1.getString(cursor1.getColumnIndex("data1")));
        data.setData2(cursor1.getString(cursor1.getColumnIndex("data2")));
        data.setData3(cursor1.getString(cursor1.getColumnIndex("data3")));
        data.setData4(cursor1.getString(cursor1.getColumnIndex("data4")));
        data.setData5(cursor1.getString(cursor1.getColumnIndex("data5")));
        data.setData6(cursor1.getString(cursor1.getColumnIndex("data6")));
        data.setData7(cursor1.getString(cursor1.getColumnIndex("data7")));
        data.setData8(cursor1.getString(cursor1.getColumnIndex("data8")));
        data.setData9(cursor1.getString(cursor1.getColumnIndex("data9")));
        data.setData10(cursor1.getString(cursor1.getColumnIndex("data10")));
        data.setData11(cursor1.getString(cursor1.getColumnIndex("data11")));
        data.setData12(cursor1.getString(cursor1.getColumnIndex("data12")));
        data.setData13(cursor1.getString(cursor1.getColumnIndex("data13")));
        data.setData14(cursor1.getString(cursor1.getColumnIndex("data14")));
        data.setMimeType(cursor1.getString(cursor1.getColumnIndex("mimetype")));
        return data;
    }

    public static ContentValues getContentValues(UserContactData data){
        if(data == null)return null;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Data.MIMETYPE, data.getMimeType());
        contentValues.put("data1",  Utils.getString(data.getData1()));
        contentValues.put("data2",  Utils.getString(data.getData2()));
        contentValues.put("data3",  Utils.getString(data.getData3()));
        contentValues.put("data4",  Utils.getString(data.getData4()));
        contentValues.put("data5",  Utils.getString(data.getData5()));
        contentValues.put("data6",  Utils.getString(data.getData6()));
        contentValues.put("data7",  Utils.getString(data.getData7()));
        contentValues.put("data8",  Utils.getString(data.getData8()));
        contentValues.put("data9",  Utils.getString(data.getData9()));
        contentValues.put("data10", Utils.getString(data.getData10()));
        contentValues.put("data11", Utils.getString(data.getData11()));
        contentValues.put("data12", Utils.getString(data.getData12()));
        contentValues.put("data13", Utils.getString(data.getData13()));
        contentValues.put("data14", Utils.getString(data.getData14()));
        return contentValues;
    }

}
