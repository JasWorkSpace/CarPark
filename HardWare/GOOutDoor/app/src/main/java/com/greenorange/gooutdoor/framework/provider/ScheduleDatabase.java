package com.greenorange.gooutdoor.framework.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Utils.SqlUtils;

/**
 * Created by JasWorkSpace on 15/4/15.
 */
public class ScheduleDatabase extends SQLiteOpenHelper {

    public ScheduleDatabase(Context context){
        super(context, ScheduleContract.DB_NAME, null, ScheduleContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate");
        db.execSQL("CREATE TABLE if not exists " + ScheduleContract.Tables.USER + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScheduleContract.UserColumns.USER_ID + " TEXT NOT NULL,"
                + ScheduleContract.UserColumns.USER_NAME + " TEXT NOT NULL,"
                + ScheduleContract.UserColumns.USER_PASSWORD + " TEXT NOT NULL,"
                + ScheduleContract.UserColumns.USER_LASTLOGINTIME + " LONG,"
                + ScheduleContract.UserColumns.USER_SPORTS_TOTAL_COUNT + " INTEGER,"
                + ScheduleContract.UserColumns.USER_SPORTS_TOTAL_TYPECOUNT + " TEXT,"
                + ScheduleContract.UserColumns.USER_SPORTS_TOTAL_DISTANCE + " DOUBLE,"
                + ScheduleContract.UserColumns.USER_SPORTS_TOTAL_TIME + " LONG,"
                + ScheduleContract.UserColumns.USER_SPORTS_TOTLE_CALORIE + " DOUBLE,"
                + "create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))"
                + " )"
        );
        db.execSQL("CREATE TABLE if not exists " + ScheduleContract.Tables.SPORTS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScheduleContract.SportsColumns.SPORTS_ID + " TEXT NOT NULL,"
                + ScheduleContract.SportsColumns.SPORTS_USERID + " TEXT NOT NULL,"
                + ScheduleContract.SportsColumns.SPORTS_TYPE + " INTEGER NOT NULL,"
                + ScheduleContract.SportsColumns.SPORTS_TIME + " LONG,"
                + ScheduleContract.SportsColumns.SPORTS_TOTAL_TIME + " LONG,"
                + ScheduleContract.SportsColumns.SPORTS_TOTAL_DISTANCE + " LONG,"
                + ScheduleContract.SportsColumns.SPORTS_TOTAL_CALORIE + " DOUBLE,"
                + ScheduleContract.SportsColumns.SPORTS_RECORD_STATE + " INTEGER NOT NULL DEFAULT 0,"
                //special data
                + ScheduleContract.SportsColumns.SPORTS_TIME_YEAR + " INTEGER,"
                + ScheduleContract.SportsColumns.SPORTS_TIME_MONTH + " INTEGER,"
                + ScheduleContract.SportsColumns.SPORTS_TIME_DAYOFMONTH + " INTEGER,"
                + ScheduleContract.SportsColumns.SPORTS_TIME_DAYOFWEEK + " INTEGER,"
                + ScheduleContract.SportsColumns.SPORTS_TIME_WEEKOFYEAR + " INTEGER,"
                + ScheduleContract.SportsColumns.SPORTS_TIME_WEEKOFMONTH + " INTEGER,"
                + ScheduleContract.SportsColumns.SPORTS_TIME_HOUROFDAY + " INTEGER,"
                + ScheduleContract.SportsColumns.SPORTS_TIME_MINUTE + " INTEGER,"
                + "create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))"
                + " )"
        );
        db.execSQL("CREATE TABLE if not exists " + ScheduleContract.Tables.LOCATION + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScheduleContract.LocationColumns.LOCATION_ID + " TEXT NOT NULL,"
                + ScheduleContract.LocationColumns.LOCATION_USERID + " TEXT NOT NULL,"
                + ScheduleContract.LocationColumns.LOCATION_SPORTSID + " TEXT NOT NULL,"
                + ScheduleContract.LocationColumns.LOCATION_TYPE + " INTEGER,"
                + ScheduleContract.LocationColumns.LOCATION_LATITUDE + " DOUBLE,"
                + ScheduleContract.LocationColumns.LOCATION_LONGITUDE + " DOUBLE,"
                + ScheduleContract.LocationColumns.LOCATION_CURRENTSPEED + " FLOAT,"
                + ScheduleContract.LocationColumns.LOCATION_ADDRESS + " TEXT,"
                + ScheduleContract.LocationColumns.LOCATION_ALTITUDE + " DOUBLE,"
                + ScheduleContract.LocationColumns.LOCATION_PROVIDER + " TEXT,"
                + ScheduleContract.LocationColumns.LOCATION_CREATETIME + " LONG,"
                + ScheduleContract.LocationColumns.LOCATION_SPORTSTATE + " INTEGER,"
                + ScheduleContract.LocationColumns.LOCATION_SPORTTYPE + " INTEGER,"
                + "create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))"
                + " )"
        );
        db.execSQL("CREATE TABLE if not exists " + ScheduleContract.Tables.SPORTSTYPE + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_ID + " TEXT NOT NULL,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_USERID + " TEXT NOT NULL,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TYPE + " INTEGER NOT NULL,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TOTAL_COUNT + " INTEGER,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TOTAL_TIME + " LONG,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TOTAL_DISTANCE + " LONG,"
                + ScheduleContract.SportsTYPEColumns.SPORTS_TOTAL_CALORIE + " DOUBLE,"
                + "create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))"
                + " )"
        );
        db.execSQL("CREATE TABLE if not exists " + ScheduleContract.Tables.SPORTSTYPEDETAIL + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScheduleContract.SportsTYPEDetailColumns.SPORTS_ID + " TEXT NOT NULL,"
                + ScheduleContract.SportsTYPEDetailColumns.SPORTS_USERID + " TEXT NOT NULL,"
                + ScheduleContract.SportsTYPEDetailColumns.SPORTS_TYPE + " INTEGER NOT NULL,"
                + ScheduleContract.SportsTYPEDetailColumns.SPORTS_TIME + " LONG,"
                + ScheduleContract.SportsTYPEDetailColumns.SPORTS_SPEED + " LONG,"
                + ScheduleContract.SportsTYPEDetailColumns.SPORTS_CALORIE + " DOUBLE,"
                + ScheduleContract.SportsTYPEDetailColumns.SPORTS_DISTANCE + " LONG,"
                + "create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))"
                + " )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade oldVersion=" + oldVersion + ", newVersion="+newVersion);
        int version = oldVersion;
        if(version == 1 && updatedatebaseFromV1toV2(db)) {
            version = 2;
        }
        if(version == 2 && updatedatebaseFromV2toV3(db)){
            version = 3;
        }
        if(version != newVersion){
            dropAllDataBase(db);
            onCreate(db);//recreate db.
        }
        Log.d("onUpgrade version=" + version + ", newVersion="+newVersion);
    }

    private void dropAllDataBase(SQLiteDatabase db) {
        Log.d("dropAllDataBase");
        try {
            //db.beginTransaction();
            dropDataBase(db, ScheduleContract.Tables.USER);
            dropDataBase(db, ScheduleContract.Tables.SPORTS);
            dropDataBase(db, ScheduleContract.Tables.LOCATION);
            dropDataBase(db, ScheduleContract.Tables.SPORTSTYPE);
            dropDataBase(db, ScheduleContract.Tables.SPORTSTYPEDETAIL);
        }catch (Throwable e){Log.d("dropAllDataBase fail " + e.toString());}finally {
            //db.endTransaction();
        }
    }
    private void dropDataBase(SQLiteDatabase db, String table){
        Log.d("dropDataBase table="+table);
        try{
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }catch (Throwable e){Log.d("dropDataBase fail " + e.toString());}
    }
    private boolean updatedatebaseFromV1toV2(SQLiteDatabase db) {
        try{
            Log.d("updatedatebaseFromV1toV2");
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                        + ScheduleContract.SportsColumns.SPORTS_RECORD_STATE
                        + " INTEGER NOT NULL DEFAULT 0;");//add sports_record_state column. default is 0
            return true;
        }catch (Throwable e){Log.d("updatedatebaseFromV1toV2 fail " + e.toString());}
        return false;
    }
    private boolean updatedatebaseFromV2toV3(SQLiteDatabase db) {
        try{
            Log.d("updatedatebaseFromV2toV3");
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                    + ScheduleContract.SportsColumns.SPORTS_TIME_YEAR
                    + " INTEGER ;");//add sports_time_year column.
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                    + ScheduleContract.SportsColumns.SPORTS_TIME_MONTH
                    + " INTEGER ;");//add sports_time_month column.
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                    + ScheduleContract.SportsColumns.SPORTS_TIME_DAYOFMONTH
                    + " INTEGER ;");//add sports_time_dayofmonth column.
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                    + ScheduleContract.SportsColumns.SPORTS_TIME_DAYOFWEEK
                    + " INTEGER ;");//add sports_time_dayofweek column.
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                    + ScheduleContract.SportsColumns.SPORTS_TIME_WEEKOFYEAR
                    + " INTEGER ;");//add sports_time_weekofyear column.
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                    + ScheduleContract.SportsColumns.SPORTS_TIME_WEEKOFMONTH
                    + " INTEGER ;");//add sports_time_weekofmonth column.
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                    + ScheduleContract.SportsColumns.SPORTS_TIME_HOUROFDAY
                    + " INTEGER ;");//add sports_time_hourofday column.
            db.execSQL("ALTER TABLE " + ScheduleContract.Tables.SPORTS + " ADD COLUMN "
                    + ScheduleContract.SportsColumns.SPORTS_TIME_MINUTE
                    + " INTEGER ;");//add sports_time_minute column.
            return true;
        }catch (Throwable e){Log.d("updatedatebaseFromV2toV3 fail " + e.toString());}
        return false;
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        dropAllDataBase(db);
        onCreate(db);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(ScheduleContract.DB_NAME);
    }
}
