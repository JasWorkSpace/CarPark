package com.greenorange.myuicontantsbackup.Task.CallLog;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;

import com.greenorange.myuicontantsbackup.BackUpApplication;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalllogInfoUtil {

    public static final String[] ORIGINAL_PROJECTION = new String[] {
        "_id",                          // 0
        "number",                       // 1
        "date",                         // 2
        "duration",                     // 3
        "type",                         // 4
        "countryiso",               // 5
        "voicemail_uri",              // 6
        "geocoded_location",            // 7
        "name",                  // 8
        "numbertype",           // 9
        "numberlabel",          // 10
        "lookup_uri",            // 11
        "matched_number",        // 12
        "normalized_number",     // 13
        "photo_id",              // 14
        "formatted_number",      // 15
        "is_read",                      // 16
        "presentation",          // 17
        "subscription_component_name", // 18
        "subscription_id",             // 19
        "features",                     // 20
        "data_usage",                   // 21
        "transcription"                // 22
    };
    public static ArrayList<CalllogData> getPhoneCalllog(Context context){
        ArrayList <CalllogData> callList = new ArrayList<CalllogData>();
        Cursor cusr = context.getContentResolver().query(Calls.CONTENT_URI, ORIGINAL_PROJECTION, null, null, null);
        try {
            while (cusr!=null && cusr.moveToNext()) {
                CalllogData calllogData = getCalllogData(cusr);
                if (calllogData != null) callList.add(calllogData);
            }
        }catch (Throwable e){
            Log.d("getPhoneCalllog fail -->"+e.toString());
        }finally {
            if(cusr != null)cusr.close();
        }
        return callList;
    }
    public static CalllogData getCalllogData(Cursor cusr){
        if(cusr == null)return null;
        CalllogData data = new CalllogData();
        data.set_id(cusr.getString(0));
        data.setNumber(cusr.getString(1));
        data.setDate(cusr.getString(2));
        data.setDuration(cusr.getString(3));
        data.setType(cusr.getString(4));
        data.setCountryiso(cusr.getString(5));
        data.setVoicemail_uri(cusr.getString(6));
        data.setGeocoded_location(cusr.getString(7));
        data.setName(cusr.getString(8));
        data.setNumbertype(cusr.getString(9));
        data.setNumberlabel(cusr.getString(10));
        data.setLookup_uri(cusr.getString(11));
        data.setMatched_number(cusr.getString(12));
        data.setNormalized_number(cusr.getString(13));
        data.setPhoto_id(cusr.getString(14));
        data.setFormatted_number(cusr.getString(15));
        data.setIs_read(cusr.getString(16));
        data.setPresentation(cusr.getString(17));
        data.setSubscription_component_name(cusr.getString(18));
        data.setSubscription_id(cusr.getString(19));
        data.setFeatures(cusr.getString(20));
        data.setData_usage(cusr.getString(21));
        data.setTranscription(cusr.getString(22));
        return data;
    }
    ////////////////////////////////////////////////////////////////
    public static void insertPhoneCalllog(ArrayList<CalllogData> datas){
        if(datas == null || datas.size() <=0)return;
        Uri uri = Uri.parse("content://call_log/calls");
        ContentResolver resolver = BackUpApplication.getInstance().getContentResolver();
        for(Iterator<CalllogData> iterator = datas.iterator(); iterator.hasNext();){
            ContentValues contentValues = getContentValues(iterator.next());
            if(contentValues != null){
                resolver.insert(uri, contentValues);
            }
        }
    }
    public static ContentValues getContentValues(CalllogData data){
        if(data == null)return null;
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", Utils.getString(data.get_id()));
        contentValues.put("number", Utils.getString(data.getNumber()));
        contentValues.put("date" , Utils.getString(data.getDate()));
        contentValues.put("duration", Utils.getString(data.getDuration()));
        contentValues.put("type", Utils.getString(data.getType()));
        contentValues.put("countryiso", Utils.getString(data.getCountryiso()));
        contentValues.put("voicemail_uri", Utils.getString(data.getVoicemail_uri()));
        contentValues.put("geocoded_location", Utils.getString(data.getGeocoded_location()));
        contentValues.put("name", Utils.getString(data.getName()));
        contentValues.put("numbertype", Utils.getString(data.getNumbertype()));
        contentValues.put("numberlabel", Utils.getString(data.getNumberlabel()));
        contentValues.put("lookup_uri", Utils.getString(data.getLookup_uri()));
        contentValues.put("matched_number", Utils.getString(data.getMatched_number()));
        contentValues.put("normalized_number", Utils.getString(data.getNormalized_number()));
        contentValues.put("photo_id", Utils.getString(data.getPhoto_id()));//this should no restore
        contentValues.put("formatted_number", Utils.getString(data.getFormatted_number()));
        contentValues.put("is_read", Utils.getString(data.getIs_read()));
        contentValues.put("presentation", Utils.getString(data.getPresentation()));
        contentValues.put("subscription_component_name", Utils.getString(data.getSubscription_component_name()));
        contentValues.put("subscription_id", Utils.getString(data.getSubscription_id()));
        contentValues.put("features", Utils.getString(data.getFeatures()));
        contentValues.put("data_usage", Utils.getString(data.getData_usage()));
        contentValues.put("transcription", Utils.getString(data.getTranscription()));
        return contentValues;
    }
}