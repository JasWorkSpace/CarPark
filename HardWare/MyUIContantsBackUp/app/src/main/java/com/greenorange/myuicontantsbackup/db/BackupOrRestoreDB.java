package com.greenorange.myuicontantsbackup.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greenorange.myuicontantsbackup.Bean.BackupOrRestoreDBBean;
import com.greenorange.myuicontantsbackup.Log;

/**
 * Created by JasWorkSpace on 15/11/18.
 */
public class BackupOrRestoreDB {
    public Context mContext;
    private final static boolean DEBUG = true;
    private SQLiteDatabase getConnection() {
        SQLiteDatabase sqliteDatabase = null;
        try {
            sqliteDatabase = new DBHelper(mContext).getReadableDatabase();
        } catch (Exception e) {
        }
        return sqliteDatabase;
    }
    private static BackupOrRestoreDB mTaskDB;
    public  static BackupOrRestoreDB getInstace(Context context){
        if(mTaskDB == null){
            mTaskDB = new BackupOrRestoreDB(context);
        }
        return mTaskDB;
    }
    public BackupOrRestoreDB(Context context){
        mContext = context;
    }

    /*db.execSQL("create table if not exists backorrestoretask_info(_id integer PRIMARY KEY AUTOINCREMENT,"
                +"tasktype int,lasttime long,runtasktype int,create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))");*/

    ///////////////////////////////////
    public boolean addBackupOrRestore(BackupOrRestoreDBBean taskDBBean){
        if(DEBUG)Log.d("addBackupOrRestore "+(taskDBBean==null? " why is null !!! " : taskDBBean.toString()));
        SQLiteDatabase sqLiteDatabase = null;
        try {
            ContentValues contentValues = getContentValues(taskDBBean);
            if(contentValues != null) {
                sqLiteDatabase = getConnection();
                return sqLiteDatabase.insertOrThrow("backorrestoretask_info", null, contentValues) > 0;
            }
        }catch(Throwable e){
            Log.i("addBackupOrRestore fail -->"+e.toString());
        }finally {
            if(sqLiteDatabase != null)sqLiteDatabase.close();
        }
        return false;
    }
    public BackupOrRestoreDBBean getLastBackupOrRestore(int tasktype){
        if(DEBUG) Log.d("getLastBackupOrRestoreDBBean("  + tasktype + ")");
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        try{
            sqLiteDatabase = getConnection();
            cursor = sqLiteDatabase.rawQuery("select * from backorrestoretask_info where tasktype=? order by _id desc limit 0,1"
                    ,new String[]{String.valueOf(tasktype)});
            if(cursor != null && cursor.moveToFirst()){
                return getBackupOrRestoreDBBean(cursor);
            }
        }catch (Throwable e){
            Log.i(" fail -->"+e.toString());
        }finally {
            if(sqLiteDatabase != null)sqLiteDatabase.close();
            if(cursor != null)cursor.close();
        }
        return null;
    }

    ////////////////////////////
    private static BackupOrRestoreDBBean getBackupOrRestoreDBBean(Cursor cursor){
        if(cursor == null)return null;
        BackupOrRestoreDBBean backupOrRestoreDBBean = new BackupOrRestoreDBBean();
        backupOrRestoreDBBean.setTasktype(cursor.getInt(cursor.getColumnIndex("tasktype")));
        backupOrRestoreDBBean.setLasttime(cursor.getLong(cursor.getColumnIndex("lasttime")));
        backupOrRestoreDBBean.addRuntasktype(cursor.getInt(cursor.getColumnIndex("runtasktype")));
        return backupOrRestoreDBBean;
    }
    private static ContentValues getContentValues(BackupOrRestoreDBBean backupOrRestoreDBBean){
        if(backupOrRestoreDBBean == null)return null;
        ContentValues contentValues = new ContentValues();
        contentValues.put("tasktype", backupOrRestoreDBBean.getTasktype());
        contentValues.put("lasttime", backupOrRestoreDBBean.getLasttime());
        contentValues.put("runtasktype", backupOrRestoreDBBean.getRuntasktype());
        return contentValues;
    }
}
