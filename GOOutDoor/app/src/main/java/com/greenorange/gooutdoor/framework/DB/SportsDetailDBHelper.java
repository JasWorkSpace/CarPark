package com.greenorange.gooutdoor.framework.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.entity.SportsDetailDBData;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.provider.ScheduleContract;

/**
 * Created by JasWorkSpace on 15/4/28.
 */
public class SportsDetailDBHelper {

    public static Uri insertSportsDetailDB(Context context, SportsDetailDBData sportsData){
        ContentValues contentValues = getSportsDetailContentValues(sportsData);
        if(contentValues != null){
            try {
                return context.getContentResolver().insert(
                        ScheduleContract.SportsTYPEDetail.CONTENT_URI,
                        contentValues
                );
            }catch (Throwable e){ Log.d(e.toString());}
        }
        return null;
    }

    public static boolean deleteSportsDetailDBBySportsIdAndUserId(Context context, String sportsId, String userId){
        try{
            return context.getContentResolver().delete(
                    ScheduleContract.SportsTYPEDetail.CONTENT_URI,
                    ScheduleContract.SportsTYPEDetail.SPORTS_ID +" =? AND " + ScheduleContract.SportsTYPEDetail.SPORTS_USERID + " =?",
                    new String[]{sportsId, userId}
            ) > 0;
        }catch (Throwable e){Log.d(e.toString());}
        return false;
    }

    /*
    * private String sports_id;
    private String sports_userid;
    private int    sports_type;
    private long   sports_time;
    private float  sports_speed;
    private double sports_calorie;
    private long   sports_distance;
    *
    * */

    public static ContentValues getSportsDetailContentValues(SportsDetailDBData sportsDetailDBData){
        if(sportsDetailDBData != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ScheduleContract.SportsTYPEDetail.SPORTS_ID, sportsDetailDBData.getSports_id());
            contentValues.put(ScheduleContract.SportsTYPEDetail.SPORTS_USERID, sportsDetailDBData.getSports_userid());
            contentValues.put(ScheduleContract.SportsTYPEDetail.SPORTS_TYPE, sportsDetailDBData.getSports_type());
            contentValues.put(ScheduleContract.SportsTYPEDetail.SPORTS_TIME, sportsDetailDBData.getSports_time());
            contentValues.put(ScheduleContract.SportsTYPEDetail.SPORTS_SPEED, sportsDetailDBData.getSports_speed());
            contentValues.put(ScheduleContract.SportsTYPEDetail.SPORTS_CALORIE, sportsDetailDBData.getSports_calorie());
            contentValues.put(ScheduleContract.SportsTYPEDetail.SPORTS_DISTANCE, sportsDetailDBData.getSports_distance());
            return contentValues;
        }
        return null;
    }

    public static SportsDetailDBData getSportsDetailDataFromCursor(Cursor cursor){
        if(cursor != null){
            SportsDetailDBData sportsDetailDBData = new SportsDetailDBData();
            sportsDetailDBData.setSports_id(cursor.getString(cursor.getColumnIndex(ScheduleContract.SportsTYPEDetail.SPORTS_ID)));
            sportsDetailDBData.setSports_userid(cursor.getString(cursor.getColumnIndex(ScheduleContract.SportsTYPEDetail.SPORTS_USERID)));
            sportsDetailDBData.setSports_type(cursor.getInt(cursor.getColumnIndex(ScheduleContract.SportsTYPEDetail.SPORTS_TYPE)));
            sportsDetailDBData.setSports_time(cursor.getLong(cursor.getColumnIndex(ScheduleContract.SportsTYPEDetail.SPORTS_TIME)));
            sportsDetailDBData.setSports_speed(cursor.getFloat(cursor.getColumnIndex(ScheduleContract.SportsTYPEDetail.SPORTS_SPEED)));
            sportsDetailDBData.setSports_calorie(cursor.getDouble(cursor.getColumnIndex(ScheduleContract.SportsTYPEDetail.SPORTS_CALORIE)));
            sportsDetailDBData.setSports_distance(cursor.getLong(cursor.getColumnIndex(ScheduleContract.SportsTYPEDetail.SPORTS_DISTANCE)));
            return sportsDetailDBData;
        }
        return null;
    }
    public interface SportsQuery{
        String[] PROJECTION = {
                ScheduleContract.SportsTYPEDetail.SPORTS_ID,
                ScheduleContract.SportsTYPEDetail.SPORTS_USERID,
                ScheduleContract.SportsTYPEDetail.SPORTS_TYPE,
                ScheduleContract.SportsTYPEDetail.SPORTS_TIME,
                ScheduleContract.SportsTYPEDetail.SPORTS_SPEED,
                ScheduleContract.SportsTYPEDetail.SPORTS_CALORIE,
                ScheduleContract.SportsTYPEDetail.SPORTS_DISTANCE,
        };
        public int SPORTS_ID = 0;
        public int SPORTS_USERID = 1;
        public int SPORTS_TYPE = 2;
        public int SPORTS_TIME = 3;
        public int SPORTS_SPEED = 4;
        public int SPORTS_CALORIE = 5;
        public int SPORTS_DISTANCE = 6;
    }
}
