package com.greenorange.myuicontantsbackup.Task.SMS;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.net.Uri;
import android.provider.CallLog.Calls;
import android.provider.Telephony;
import android.provider.Telephony.Threads;

import com.greenorange.myuicontantsbackup.utils.Utils;

public class SmsInfoUtil {
    public static final String[] ORIGINAL_PROJECTION = new String[] {
    	"_id",
    	"thread_id",
    	"address",
    	"m_size",
    	"person",
    	"date",
    	"date_sent",
    	"protocol",
    	"read",
    	"status",
    	"type",
    	"reply_path_present",
    	"subject",
    	"body",
    	"service_center",
    	"locked",
    	"sub_id",
    	"error_code",
    	"creator",
    	"seen",
    	"ipmsg_id",
    	"ref_id",
    	"total_len",
    	"rec_len"
    };

    public static ArrayList<SmsData> getPhoneSms(Context context){
        ArrayList<SmsData> smsList = new ArrayList<SmsData>();
        Uri uri = Uri.parse("content://sms/");
        Cursor cusr = null;
        try {
            ContentResolver resolver = context.getContentResolver();
            cusr = resolver.query(uri, ORIGINAL_PROJECTION, null, null, null);
            while (cusr!=null && cusr.moveToNext()) {
                SmsData smsData = getSmsData(cusr);
                if(smsData != null)smsList.add(smsData);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            if(cusr != null)cusr.close();
        }
        return smsList;
    }
    public static SmsData getSmsData(Cursor cusr){
        if(cusr == null)return null;
        SmsData data = new SmsData();
        data.set_id(cusr.getString(0));
        data.setThread_id(cusr.getString(1));
        data.setAddress(cusr.getString(2));
        data.setM_size(cusr.getString(3));
        data.setPerson(cusr.getString(4));
        data.setDate(cusr.getString(5));
        data.setDate_sent(cusr.getString(6));
        data.setProtocol(cusr.getString(7));
        data.setRead(cusr.getString(8));
        data.setStatus(cusr.getString(9));
        data.setType(cusr.getString(10));
        data.setReply_path_present(cusr.getString(11));
        data.setSubject(cusr.getString(12));
        data.setBody(cusr.getString(13));
        data.setService_center(cusr.getString(14));
        data.setLocked(cusr.getString(15));
        data.setSub_id(cusr.getString(16));
        data.setError_code(cusr.getString(17));
        data.setCreator(cusr.getString(18));
        data.setSeen(cusr.getString(19));
        data.setIpmsg_id(cusr.getString(20));
        data.setRef_id(cusr.getString(21));
        data.setTotal_len(cusr.getString(22));
        data.setRec_len(cusr.getString(23));
        return data;
    }
    public static ContentValues getContentValues(SmsData smsData){
        if(smsData == null)return null;
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id" , Utils.getString(smsData.get_id()));
        //contentValues.put("thread_id" , Utils.getString(smsData.getThread_id()));
        contentValues.put("address" , Utils.getString(smsData.getAddress()));
        contentValues.put("m_size" , Utils.getString(smsData.getM_size()));
        contentValues.put("person" , Utils.getString(smsData.getPerson()));
        contentValues.put("date" , Utils.getString(smsData.getDate()));
        contentValues.put("date_sent" , Utils.getString(smsData.getDate_sent()));
        contentValues.put("protocol" , Utils.getString(smsData.getProtocol()));
        contentValues.put("read" , Utils.getString(smsData.getRead()));
        contentValues.put("status" , Utils.getString(smsData.getStatus()));
        contentValues.put("type" , Utils.getString(smsData.getType()));
        contentValues.put("reply_path_present" , Utils.getString(smsData.getReply_path_present()));
        contentValues.put("subject" , Utils.getString(smsData.getSubject()));
        contentValues.put("body" , Utils.getString(smsData.getBody()));
        contentValues.put("service_center" , Utils.getString(smsData.getService_center()));
        contentValues.put("locked" , Utils.getString(smsData.getLocked()));
        contentValues.put("sub_id" , Utils.getString(smsData.getSub_id()));
        contentValues.put("error_code" , Utils.getString(smsData.getError_code()));
        contentValues.put("creator" , Utils.getString(smsData.getCreator()));
        contentValues.put("seen" , Utils.getString(smsData.getSeen()));
        contentValues.put("ipmsg_id" , Utils.getString(smsData.getIpmsg_id()));
        contentValues.put("ref_id" , Utils.getString(smsData.getRef_id()));
        contentValues.put("total_len" , Utils.getString(smsData.getTotal_len()));
        contentValues.put("rec_len" , Utils.getString(smsData.getRec_len()));
        return contentValues;
    }
}
