package com.greenorange.gooutdoor.framework.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.entity.SportsTypeDBData;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.provider.ScheduleContract;

/**
 * Created by JasWorkSpace on 15/8/28.
 */
public class SportsTYPEDBHelper  {

    public static boolean addSports(Context context, SportsDBData sportsDBData){
        try{
            if(!sportsDBData.checkSportsData())return false;
            boolean insert = false;
            SportsTypeDBData sportsTypeDBData = getSportsTYPEDBByUserIdAndSportsType(context, sportsDBData.getSports_userid(), sportsDBData.getSports_type());
            if(sportsTypeDBData == null){
                sportsTypeDBData = new SportsTypeDBData();
                sportsTypeDBData.setSports_userid(sportsDBData.getSports_userid());
                sportsTypeDBData.setSports_type(sportsDBData.getSports_type());
                insert = true;
            }
            sportsTypeDBData.setSports_total_count(sportsTypeDBData.getSports_total_count() + 1);//add one
            sportsTypeDBData.setSports_total_time(sportsTypeDBData.getSports_total_time() + sportsDBData.getSports_total_time());
            sportsTypeDBData.setSports_total_distance(sportsTypeDBData.getSports_total_distance() + sportsDBData.getSports_total_distance());
            sportsTypeDBData.setSports_totle_calorie(sportsTypeDBData.getSports_totle_calorie() + sportsDBData.getSports_totle_calorie());
            if(insert){
                insertSportsTYPEDB(context, sportsTypeDBData);
            }else{
                updateSportsTYPEDB(context, sportsTypeDBData);
            }
        }catch (Throwable e){Log.d(e.toString());}
        return false;
    }

    public static boolean updateSportsTYPEDB(Context context, SportsTypeDBData sportsTypeDBData){
        ContentValues contentValues = getSportsTypeDataContentValues(sportsTypeDBData);
        if(contentValues != null){
            try {
                return context.getContentResolver().update(
                        ScheduleContract.SportsTYPE.CONTENT_URI, contentValues,
                        ScheduleContract.SportsTYPE.SPORTS_USERID + " =? AND " + ScheduleContract.SportsTYPE.SPORTS_TYPE + " =?",
                        new String[]{sportsTypeDBData.getSports_userid(), String.valueOf(sportsTypeDBData.getSports_type())}
                ) > 0;
            }catch (Throwable e){Log.d(e.toString());}
        }
        return false;
    }
    public static Uri insertSportsTYPEDB(Context context, SportsTypeDBData sportsTypeDBData){
        ContentValues contentValues = getSportsTypeDataContentValues(sportsTypeDBData);
        if(contentValues != null){
            try {
                return context.getContentResolver().insert(
                        ScheduleContract.SportsTYPE.CONTENT_URI,
                        contentValues
                );
            }catch (Throwable e){
                Log.d(e.toString());}
        }
        return null;
    }
    public static SportsTypeDBData getSportsTYPEByUserId(Context context, String userid){
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(
                    ScheduleContract.SportsTYPE.CONTENT_URI, SportsTypeQuery.PROJECTION,
                    ScheduleContract.SportsTYPE.SPORTS_USERID + " =?",
                    new String[]{userid},
                    null
            );
            if(cursor != null && cursor.moveToLast()){
                return getSportsTYPEDataFromCursor(cursor);
            }
        }catch (Throwable e){Log.d(e.toString());}finally {
            if(cursor != null)cursor.close();
        }
        return null;
    }
    public static SportsTypeDBData getSportsTYPEDBByUserIdAndSportsType(Context context, String userid, int sportType){
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(
                    ScheduleContract.SportsTYPE.CONTENT_URI, SportsTypeQuery.PROJECTION,
                    ScheduleContract.SportsTYPE.SPORTS_USERID + " =? AND " + ScheduleContract.SportsTYPE.SPORTS_TYPE + " =?",
                    new String[]{userid, String.valueOf(sportType)},
                    null
            );
            if(cursor != null && cursor.moveToLast()){
                return getSportsTYPEDataFromCursor(cursor);
            }
        }catch (Throwable e){Log.d(e.toString());}finally {
            if(cursor != null)cursor.close();
        }
        return null;
    }
    public static SportsTypeDBData getSportsTYPEDataFromCursor(Cursor cursor){
        if(cursor != null){
            SportsTypeDBData sportsTypeDBData = new SportsTypeDBData();
            //default is un-use
            //sportsTypeDBData.setSports_id(cursor.getString(cursor.getColumnIndex(ScheduleContract.SportsTYPE.SPORTS_ID)));
            sportsTypeDBData.setSports_userid(cursor.getString(cursor.getColumnIndex(ScheduleContract.SportsTYPE.SPORTS_USERID)));
            sportsTypeDBData.setSports_type(cursor.getInt(cursor.getColumnIndex(ScheduleContract.SportsTYPE.SPORTS_TYPE)));
            sportsTypeDBData.setSports_total_count(cursor.getInt(cursor.getColumnIndex(ScheduleContract.SportsTYPE.SPORTS_TOTAL_COUNT)));
            sportsTypeDBData.setSports_total_time(cursor.getLong(cursor.getColumnIndex(ScheduleContract.SportsTYPE.SPORTS_TOTAL_TIME)));
            sportsTypeDBData.setSports_total_distance(cursor.getLong(cursor.getColumnIndex(ScheduleContract.SportsTYPE.SPORTS_TOTAL_DISTANCE)));
            sportsTypeDBData.setSports_totle_calorie(cursor.getDouble(cursor.getColumnIndex(ScheduleContract.SportsTYPE.SPORTS_TOTAL_CALORIE)));
            return sportsTypeDBData;
        }
        return null;
    }

    public static ContentValues getSportsTypeDataContentValues(SportsTypeDBData sportsTypeDBData){
        if(sportsTypeDBData != null){
            ContentValues contentValues = new ContentValues();
            //for default
            contentValues.put(ScheduleContract.SportsTYPE.SPORTS_ID, "UNUSECOLUMN");
            contentValues.put(ScheduleContract.SportsTYPE.SPORTS_USERID, sportsTypeDBData.getSports_userid());
            contentValues.put(ScheduleContract.SportsTYPE.SPORTS_TYPE, sportsTypeDBData.getSports_type());
            contentValues.put(ScheduleContract.SportsTYPE.SPORTS_TOTAL_COUNT, sportsTypeDBData.getSports_total_count());
            contentValues.put(ScheduleContract.SportsTYPE.SPORTS_TOTAL_TIME, sportsTypeDBData.getSports_total_time());
            contentValues.put(ScheduleContract.SportsTYPE.SPORTS_TOTAL_DISTANCE, sportsTypeDBData.getSports_total_distance());
            contentValues.put(ScheduleContract.SportsTYPE.SPORTS_TOTAL_CALORIE, sportsTypeDBData.getSports_totle_calorie());
            return contentValues;
        }
        return null;
    }

    public interface SportsTypeQuery{
        String[] PROJECTION = {
                ScheduleContract.SportsTYPE.SPORTS_ID,
                ScheduleContract.SportsTYPE.SPORTS_USERID,
                ScheduleContract.SportsTYPE.SPORTS_TYPE,
                ScheduleContract.SportsTYPE.SPORTS_TOTAL_COUNT,
                ScheduleContract.SportsTYPE.SPORTS_TOTAL_TIME,
                ScheduleContract.SportsTYPE.SPORTS_TOTAL_DISTANCE,
                ScheduleContract.SportsTYPE.SPORTS_TOTAL_CALORIE,
        };
        public int SPORTS_ID = 0;
        public int SPORTS_USERID = 1;
        public int SPORTS_TYPE = 2;
        public int SPORTS_TOTAL_COUNT = 3;
        public int SPORTS_TOTAL_TIME = 4;
        public int SPORTS_TOTAL_DISTANCE = 5;
        public int SPORTS_TOTAL_CALORIE = 6;
    }

}
