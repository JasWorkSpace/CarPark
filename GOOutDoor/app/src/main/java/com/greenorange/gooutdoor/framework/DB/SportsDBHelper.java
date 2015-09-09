package com.greenorange.gooutdoor.framework.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.framework.Dao.SportsDao;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.provider.ScheduleContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/4/17.
 */
public class SportsDBHelper {

    public final static int SPORTS_RECORD_STATE_DEFAULT = 0;
    public final static int SPORTS_RECORD_STATE_SUCCESS = 1;


    public static Uri insertSportsData(Context context, SportsDBData sportsData){
        ContentValues contentValues = getSportsContentValues(sportsData);
        if(contentValues != null){
            try {
                return context.getContentResolver().insert(
                        ScheduleContract.Sports.CONTENT_URI,
                        contentValues
                );
            }catch (Throwable e){Log.d(e.toString());}
        }
        return null;
    }
    public static boolean updateSportsData(Context context, SportsDBData sportsData){
        ContentValues contentValues = getSportsContentValues(sportsData);
        if(contentValues != null){
            try {
                return context.getContentResolver().update(
                        ScheduleContract.Sports.CONTENT_URI, contentValues,
                        ScheduleContract.Sports.SPORTS_USERID + " =? AND " + ScheduleContract.Sports.SPORTS_ID + " =?",
                        new String[]{sportsData.getSports_userid(), String.valueOf(sportsData.getSports_id())}
                ) > 0;
            }catch (Throwable e){Log.d(e.toString());}
        }
        return false;
    }
    public static boolean deleteSportsData(Context context, SportsDBData sportsData){
        try{
            return deleteSportsDataBySportsIdAndUserId(context, sportsData.getSports_id(), sportsData.getSports_userid());
        }catch (Throwable e){Log.d(e.toString());}
        return false;
    }
    public static boolean deleteSportsDataBySportsIdAndUserId(Context context, String sportid, String userid){
        try{
            return context.getContentResolver().delete(
                    ScheduleContract.Sports.CONTENT_URI,
                    ScheduleContract.Sports.SPORTS_ID +" =? AND " + ScheduleContract.Sports.SPORTS_USERID + " =?",
                    new String[]{sportid, userid}
            ) > 0;
        }catch (Throwable e){Log.d(e.toString());}
        return false;
    }
    public static SportsDBData getSuccessSportsDataBySportsIdAndUserId(Context context, String sportsId, String userId){
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(
                    ScheduleContract.Sports.CONTENT_URI, SportsQuery.PROJECTION,
                    ScheduleContract.Sports.SPORTS_ID + " =? AND " + ScheduleContract.Sports.SPORTS_USERID + " =? AND " + ScheduleContract.Sports.SPORTS_RECORD_STATE + " =?",
                    new String[]{sportsId, userId, Integer.toString(SPORTS_RECORD_STATE_SUCCESS)},
                    null
            );
            if(cursor != null && cursor.moveToLast()){
                return getSportsDataFromCursor(cursor);
            }
        }catch (Throwable e){Log.d(e.toString());}finally {
            if(cursor != null)cursor.close();
        }
        return null;
    }
    public static SportsDBData getSportsDataBySportsIdAndUserId(Context context, String sportsId, String userId){
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(
                    ScheduleContract.Sports.CONTENT_URI, SportsQuery.PROJECTION,
                    ScheduleContract.Sports.SPORTS_ID + " =? AND " + ScheduleContract.Sports.SPORTS_USERID + " =?",
                    new String[]{sportsId, userId},
                    null
            );
            if(cursor != null && cursor.moveToLast()){
                return getSportsDataFromCursor(cursor);
            }
        }catch (Throwable e){Log.d(e.toString());}finally {
            if(cursor != null)cursor.close();
        }
        return null;
    }
    public static SportsDBData getLastSportsDataByUserId(Context context, String userId){
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(
                    ScheduleContract.Sports.CONTENT_URI, SportsQuery.PROJECTION,
                    ScheduleContract.Sports.SPORTS_USERID + " =?",
                    new String[]{userId},
                    ScheduleContract.Sports._ID + " desc limit 0,1 "
            );
            if(cursor != null && cursor.moveToLast()){
                return getSportsDataFromCursor(cursor);
            }
        }catch (Throwable e){Log.d(e.toString());}finally {
            if(cursor != null)cursor.close();
        }
        return null;
    }
    public List<SportsDBData> getSuccessSportsdataByUserId(Context context, String userId, int start, int limit){
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(
                    ScheduleContract.Sports.CONTENT_URI, SportsQuery.PROJECTION,
                    ScheduleContract.Sports.SPORTS_USERID + " =? AND " + ScheduleContract.Sports.SPORTS_RECORD_STATE + " =?"
                    + " order by " + ScheduleContract.Sports._ID +" limit " + start +"," + limit,
                    new String[]{userId, Integer.toString(SPORTS_RECORD_STATE_SUCCESS)},
                    null
            );
            List<SportsDBData> list = new ArrayList<SportsDBData>();
            while(cursor.moveToNext()){
                list.add(getSportsDataFromCursor(cursor));
            }
            return list;
        }catch (Throwable e){Log.d(e.toString());}finally {
            if(cursor != null)cursor.close();
        }
        return new ArrayList<SportsDBData>();
    }
    public static ContentValues getSportsContentValues(SportsDBData sportsData){
        if(sportsData != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ScheduleContract.Sports.SPORTS_ID, sportsData.getSports_id());
            contentValues.put(ScheduleContract.Sports.SPORTS_USERID, sportsData.getSports_userid());
            contentValues.put(ScheduleContract.Sports.SPORTS_TYPE, sportsData.getSports_type());
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME, sportsData.getSports_time());
            contentValues.put(ScheduleContract.Sports.SPORTS_TOTAL_TIME, sportsData.getSports_total_time());
            contentValues.put(ScheduleContract.Sports.SPORTS_TOTAL_DISTANCE, sportsData.getSports_total_distance());
            contentValues.put(ScheduleContract.Sports.SPORTS_TOTAL_CALORIE, sportsData.getSports_totle_calorie());
            contentValues.put(ScheduleContract.Sports.SPORTS_RECORD_STATE, sportsData.getSports_record_state());
            //for special time
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME_YEAR, sportsData.getSports_time_year());
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME_MONTH, sportsData.getSports_time_month());
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME_DAYOFMONTH, sportsData.getSports_time_dayofmonth());
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME_DAYOFWEEK, sportsData.getSports_time_dayofweek());
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME_WEEKOFYEAR, sportsData.getSports_time_weekofyear());
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME_WEEKOFMONTH, sportsData.getSports_time_weekofmonth());
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME_HOUROFDAY, sportsData.getSports_time_hourofday());
            contentValues.put(ScheduleContract.Sports.SPORTS_TIME_MINUTE, sportsData.getSports_time_minute());
            return contentValues;
        }
        return null;
    }
    public static SportsDBData getSportsDataFromCursor(Cursor cursor){
        if(cursor != null){
            SportsDBData sportsData = new SportsDBData();
            sportsData.setSports_id(cursor.getString(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_ID)));
            sportsData.setSports_userid(cursor.getString(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_USERID)));
            sportsData.setSports_type(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TYPE)));
            sportsData.setSports_time(cursor.getLong(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME)));
            sportsData.setSports_total_time(cursor.getLong(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TOTAL_TIME)));
            sportsData.setSports_total_distance(cursor.getLong(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TOTAL_DISTANCE)));
            sportsData.setSports_totle_calorie(cursor.getDouble(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TOTAL_CALORIE)));
            sportsData.setSports_record_state(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_RECORD_STATE)));
            //for special time.
            sportsData.setSports_time_year(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME_YEAR)));
            sportsData.setSports_time_month(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME_MONTH)));
            sportsData.setSports_time_dayofmonth(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME_DAYOFMONTH)));
            sportsData.setSports_time_dayofweek(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME_DAYOFWEEK)));
            sportsData.setSports_time_weekofyear(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME_WEEKOFYEAR)));
            sportsData.setSports_time_weekofmonth(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME_WEEKOFMONTH)));
            sportsData.setSports_time_hourofday(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME_HOUROFDAY)));
            sportsData.setSports_time_minute(cursor.getInt(cursor.getColumnIndex(ScheduleContract.Sports.SPORTS_TIME_MINUTE)));
            return sportsData;
        }
        return null;
    }
    public interface SportsQuery{
        String[] PROJECTION = {
                ScheduleContract.Sports.SPORTS_ID,
                ScheduleContract.Sports.SPORTS_USERID,
                ScheduleContract.Sports.SPORTS_TYPE,
                ScheduleContract.Sports.SPORTS_TIME,
                ScheduleContract.Sports.SPORTS_TOTAL_TIME,
                ScheduleContract.Sports.SPORTS_TOTAL_DISTANCE,
                ScheduleContract.Sports.SPORTS_TOTAL_CALORIE,
                ScheduleContract.Sports.SPORTS_RECORD_STATE,
                ScheduleContract.Sports.SPORTS_TIME_YEAR,
                ScheduleContract.Sports.SPORTS_TIME_MONTH,
                ScheduleContract.Sports.SPORTS_TIME_DAYOFMONTH,
                ScheduleContract.Sports.SPORTS_TIME_DAYOFWEEK,
                ScheduleContract.Sports.SPORTS_TIME_WEEKOFYEAR,
                ScheduleContract.Sports.SPORTS_TIME_WEEKOFMONTH,
                ScheduleContract.Sports.SPORTS_TIME_HOUROFDAY,
                ScheduleContract.Sports.SPORTS_TIME_MINUTE,
        };
        public int SPORTS_ID = 0;
        public int SPORTS_USERID = 1;
        public int SPORTS_TYPE = 2;
        public int SPORTS_TIME = 3;
        public int SPORTS_TOTAL_TIME = 4;
        public int SPORTS_TOTAL_DISTANCE = 5;
        public int SPORTS_TOTAL_CALORIE = 6;
        public int SPORTS_RECORD_STATE = 7;
        public int SPORTS_TIME_YEAR = 8;
        public int SPORTS_TIME_MONTH = 9;
        public int SPORTS_TIME_DAYOFMONTH = 10;
        public int SPORTS_TIME_DAYOFWEEK = 11;
        public int SPORTS_TIME_WEEKOFYEAR = 12;
        public int SPORTS_TIME_WEEKOFMONTH = 13;
        public int SPORTS_TIME_HOUROFDAY = 14;
        public int SPORTS_TIME_MINUTE = 15;
    }
}
