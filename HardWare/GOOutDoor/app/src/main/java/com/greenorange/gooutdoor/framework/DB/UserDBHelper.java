package com.greenorange.gooutdoor.framework.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.provider.ScheduleContract;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/4/16.
 */
public class UserDBHelper {

    public static List<UserDBData> getAllUserData(Context context){
        List<UserDBData> mUserDatas = new ArrayList<UserDBData>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ScheduleContract.User.CONTENT_URI, null,null,null,null);
            while (cursor != null && cursor.moveToNext()){
                mUserDatas.add(getUserDataFromCursor(cursor));
            }
        }catch (Throwable e){
            Log.d(e.toString());
        }finally {
            if(cursor != null)cursor.close();
        }
        return mUserDatas;
    }

    public static boolean delAlluserData(Context context){
        try {
            return context.getContentResolver().delete(
                    ScheduleContract.User.CONTENT_URI,
                    null ,
                    null
            ) > 0;
        }catch (Throwable e){Log.d(e.toString());}
        return false;
    }

    public static UserDBData getUserDataByUserId(Context context, String userId){
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    ScheduleContract.User.CONTENT_URI, UserQuery.PROJECTION,
                    ScheduleContract.User.USER_ID+" =? ",
                    new String[]{userId},
                    null
            );
            if(cursor != null && cursor.moveToLast()) {
                return getUserDataFromCursor(cursor);
            }
        }catch (Throwable e){
            Log.d(e.toString());
        }finally {
            if(cursor != null)cursor.close();
        }
        return null;
    }

    public static Uri insertUserData(Context context, UserDBData userData){
        ContentValues contentValues = getUserContentValues(userData);
        if(contentValues != null){
            try {
                return context.getContentResolver().insert(
                        ScheduleContract.User.CONTENT_URI,
                        contentValues
                );
            }catch (Throwable e){Log.d(e.toString());}
        }
        return null;
    }

    public static boolean updateUserData(Context context, UserDBData userData){
        ContentValues contentValues = getUserContentValues(userData);
        if(contentValues != null){
            try {
                return context.getContentResolver().update(
                        ScheduleContract.User.CONTENT_URI, contentValues,
                        ScheduleContract.User.USER_ID + " =? ",
                        new String[]{userData.getUserid()}
                ) > 0;
            }catch (Throwable e){Log.d(e.toString());}
        }
        return false;
    }

    public static boolean deluserData(Context context,String userId){
        try {
            return context.getContentResolver().delete(
                    ScheduleContract.User.CONTENT_URI,
                    ScheduleContract.User.USER_ID + " =? ",
                    new String[]{userId}
            ) > 0;
        }catch (Throwable e){Log.d(e.toString());}
        return false;
    }

    public static UserDBData getUserDataFromCursor(Cursor cursor){
        if(cursor != null){
            UserDBData userData = new UserDBData();
            userData.setUserid(cursor.getString(cursor.getColumnIndex(ScheduleContract.User.USER_ID)));
            userData.setUsername(cursor.getString(cursor.getColumnIndex(ScheduleContract.User.USER_NAME)));
            userData.setUserpassword(cursor.getString(cursor.getColumnIndex(ScheduleContract.User.USER_PASSWORD)));
            userData.setLastlogintime(cursor.getLong(cursor.getColumnIndex(ScheduleContract.User.USER_LASTLOGINTIME)));
            userData.setTotal_count(cursor.getInt(cursor.getColumnIndex(ScheduleContract.User.USER_SPORTS_TOTAL_COUNT)));
            userData.setTotal_typecount(cursor.getString(cursor.getColumnIndex(ScheduleContract.User.USER_SPORTS_TOTAL_TYPECOUNT)));
            userData.setTotal_distance(cursor.getDouble(cursor.getColumnIndex(ScheduleContract.User.USER_SPORTS_TOTAL_DISTANCE)));
            userData.setTotal_time(cursor.getLong(cursor.getColumnIndex(ScheduleContract.User.USER_SPORTS_TOTAL_TIME)));
            userData.setTotal_calorie(cursor.getDouble(cursor.getColumnIndex(ScheduleContract.User.USER_SPORTS_TOTLE_CALORIE)));
            return userData;
        }
        return null;
    }

    public static ContentValues getUserContentValues(UserDBData userData){
        if(userData != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ScheduleContract.User.USER_ID, userData.getUserid());
            contentValues.put(ScheduleContract.User.USER_NAME, userData.getUsername());
            contentValues.put(ScheduleContract.User.USER_PASSWORD, userData.getUserpassword());
            contentValues.put(ScheduleContract.User.USER_LASTLOGINTIME, userData.getLastlogintime());
            contentValues.put(ScheduleContract.User.USER_SPORTS_TOTAL_COUNT, userData.getTotal_count());
            contentValues.put(ScheduleContract.User.USER_SPORTS_TOTAL_TYPECOUNT, userData.getTotal_typecount());
            contentValues.put(ScheduleContract.User.USER_SPORTS_TOTAL_DISTANCE, userData.getTotal_distance());
            contentValues.put(ScheduleContract.User.USER_SPORTS_TOTAL_TIME, userData.getTotal_time());
            contentValues.put(ScheduleContract.User.USER_SPORTS_TOTLE_CALORIE, userData.getTotal_calorie());
            return contentValues;
        }
        return null;
    }

    public interface UserQuery{
        String[] PROJECTION = {
                ScheduleContract.User.USER_ID,
                ScheduleContract.User.USER_NAME,
                ScheduleContract.User.USER_PASSWORD,
                ScheduleContract.User.USER_LASTLOGINTIME,
                ScheduleContract.User.USER_SPORTS_TOTAL_COUNT,
                ScheduleContract.User.USER_SPORTS_TOTAL_TYPECOUNT,
                ScheduleContract.User.USER_SPORTS_TOTAL_DISTANCE,
                ScheduleContract.User.USER_SPORTS_TOTAL_TIME,
                ScheduleContract.User.USER_SPORTS_TOTLE_CALORIE,
        };
        public int USER_ID = 0;
        public int USER_NAME = 1;
        public int USER_PASSWORD = 2;
        public int USER_LASTLOGINTIME = 3;
        public int USER_SPORTS_TOTAL_COUNT = 4;
        public int USER_SPORTS_TOTAL_TYPECOUNT = 5;
        public int USER_SPORTS_TOTAL_DISTANCE = 6;
        public int USER_SPORTS_TOTAL_TIME = 7;
        public int USER_SPORTS_TOTLE_CALORIE = 8;
    }
}
