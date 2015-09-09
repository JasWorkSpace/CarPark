package com.greenorange.gooutdoor.framework.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.gooutdoor.framework.provider.ScheduleContract;

/**
 * Created by JasWorkSpace on 15/4/17.
 */
public class LocationDBHelper {

    public static Uri insertLocationDBData(Context context, LocationDBData locationDBData){
        ContentValues contentValues = getLocationDataContentValues(locationDBData);
        if(contentValues != null){
            try {
                return context.getContentResolver().insert(
                        ScheduleContract.Location.CONTENT_URI,
                        contentValues
                );
            }catch (Throwable e){
                Log.d(e.toString());}
        }
        return null;
    }

    public static boolean delLocationDBDataByUserIDAndSportsID(Context context, String sportsId , String userId){
        if(TextUtils.isEmpty(sportsId) || TextUtils.isEmpty(userId)){
            throw new ApplicationException("delLocationDBDataByUserIDAndSportsID fail param unaviable sportsId "+ sportsId + ", userId="+userId);
        }
        try{
            return context.getContentResolver().delete(
                    ScheduleContract.Location.CONTENT_URI,
                    ScheduleContract.Location.LOCATION_USERID +" =? AND " + ScheduleContract.Location.LOCATION_SPORTSID + " =?",
                    new String[]{userId, sportsId}
            ) > 0;
        }catch (Throwable e){Log.d(e.toString());}
        return false;
    }

    public static ContentValues getLocationDataContentValues(LocationDBData locationData){
        if(locationData != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_USERID, locationData.getUserid());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_SPORTSID, locationData.getSportid());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_TYPE, locationData.getMaptype());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_LATITUDE, locationData.getLatitude());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_LONGITUDE, locationData.getLongitude());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_CURRENTSPEED, locationData.getCurrentspeed());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_ADDRESS, locationData.getAddress());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_ALTITUDE, locationData.getAltitude());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_PROVIDER, locationData.getProvider());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_CREATETIME, locationData.getTime());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_SPORTSTATE, locationData.getSportstate());
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_SPORTTYPE, locationData.getSportstype());
            //for define
            contentValues.put(ScheduleContract.LocationColumns.LOCATION_ID, "UNUSECOLUMN");
            return contentValues;
        }
        return null;
    }

    public static LocationDBData getLocationDataFromCursor(Cursor cursor){
        if(cursor != null){
            LocationDBData locationData = new LocationDBData();
            locationData.setUserid(cursor.getString(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_USERID)));
            locationData.setSportid(cursor.getString(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_SPORTSID)));
            locationData.setMaptype(cursor.getInt(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_TYPE)));
            locationData.setLatitude(cursor.getDouble(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_LATITUDE)));
            locationData.setLongitude(cursor.getDouble(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_LONGITUDE)));
            locationData.setCurrentspeed(cursor.getFloat(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_CURRENTSPEED)));
            locationData.setAddress(cursor.getString(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_ADDRESS)));
            locationData.setAltitude(cursor.getDouble(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_ALTITUDE)));
            locationData.setProvider(cursor.getString(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_PROVIDER)));
            locationData.setTime(cursor.getLong(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_CREATETIME)));
            locationData.setSportstate(cursor.getInt(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_SPORTSTATE)));
            locationData.setSportstype(cursor.getInt(cursor.getColumnIndex(ScheduleContract.LocationColumns.LOCATION_SPORTTYPE)));
            return locationData;
        }
        return null;
    }
    public interface LocationQuery{
        String[] PROJECTION = {
                ScheduleContract.Location.LOCATION_ID,
                ScheduleContract.Location.LOCATION_USERID,
                ScheduleContract.Location.LOCATION_SPORTSID,
                ScheduleContract.Location.LOCATION_TYPE,
                ScheduleContract.Location.LOCATION_LATITUDE,
                ScheduleContract.Location.LOCATION_LONGITUDE,
                ScheduleContract.Location.LOCATION_CURRENTSPEED,
                ScheduleContract.Location.LOCATION_ADDRESS,
                ScheduleContract.Location.LOCATION_ALTITUDE,
                ScheduleContract.Location.LOCATION_PROVIDER,
                ScheduleContract.Location.LOCATION_CREATETIME,
                ScheduleContract.Location.LOCATION_SPORTSTATE,
                ScheduleContract.Location.LOCATION_SPORTTYPE
        };
        public int LOCATION_ID = 0;
        public int LOCATION_USERID = 1;
        public int LOCATION_SPORTSID = 2;
        public int LOCATION_TYPE = 3;
        public int LOCATION_LATITUDE = 4;
        public int LOCATION_LONGITUDE = 5;
        public int LOCATION_CURRENTSPEED = 6;
        public int LOCATION_ADDRESS = 7;
        public int LOCATION_ALTITUDE = 8;
        public int LOCATION_PROVIDER = 9;
        public int LOCATION_CREATETIME = 10;
        public int LOCATION_SPORTSTATE = 11;
        public int LOCATION_SPORTTYPE  = 12;
    }
}
