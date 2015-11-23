package com.greenorange.myuicontantsbackup.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greenorange.myuicontantsbackup.Bean.TaskDBBean;
import com.greenorange.myuicontantsbackup.Log;

/**
 * Created by JasWorkSpace on 15/11/12.
 */
public class Taskdb {
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
    private static Taskdb mTaskDB;
    public  static Taskdb getInstace(Context context){
        if(mTaskDB == null){
            mTaskDB = new Taskdb(context);
        }
        return mTaskDB;
    }
    public Taskdb(Context context){
        mContext = context;
    }
    /*
    * db.execSQL("create table if not exists task_info(_id integer PRIMARY KEY AUTOINCREMENT,"
                +"uid char not null,bbsid char,tasktype int,lasttime long,md5 char,
                create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))");
    * */
    //////////////////////////////////////////////////////////////////////
    public boolean addTask(TaskDBBean taskDBBean){
        if(DEBUG)Log.d("addTask "+(taskDBBean==null? " why is null !!! " : taskDBBean.toString()));
        SQLiteDatabase sqLiteDatabase = null;
        try {
            ContentValues contentValues = getContentValues(taskDBBean);
            if(contentValues != null) {
                sqLiteDatabase = getConnection();
                //del it first
                //sqLiteDatabase.delete("task_info", "uid=? and tasktype=?",
                //        new String[]{taskDBBean.getUid(), String.valueOf(taskDBBean.getTasktype())});
                return sqLiteDatabase.insertOrThrow("task_info", null, contentValues) > 0;
            }
        }catch(Throwable e){
            Log.i("addTask fail -->"+e.toString());
        }finally {
            if(sqLiteDatabase != null)sqLiteDatabase.close();
        }
        return false;
    }
    public TaskDBBean getTask(String uid, int tasktype){
        if(DEBUG)Log.d("getTask(" + uid + ", "+tasktype+")");
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        try{
            sqLiteDatabase = getConnection();
            cursor = sqLiteDatabase.rawQuery("select * from task_info where uid=? and tasktype=? order by _id desc limit 0,1"
                    ,new String[]{uid, String.valueOf(tasktype)});
            if(cursor != null && cursor.moveToFirst()){
                return getTaskDBBean(cursor);
            }
        }catch (Throwable e){
            Log.i("getTask fail -->"+e.toString());
        }finally {
            if(sqLiteDatabase != null)sqLiteDatabase.close();
            if(cursor != null)cursor.close();
        }
        return null;
    }
    //////////////////////////////////////////////////////////
    private static TaskDBBean getTaskDBBean(Cursor cursor){
        if(cursor == null)return null;
        TaskDBBean taskDBBean = new TaskDBBean();
        taskDBBean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
        taskDBBean.setBbsid(cursor.getString(cursor.getColumnIndex("bbsid")));
        taskDBBean.setTasktype(cursor.getInt(cursor.getColumnIndex("tasktype")));
        taskDBBean.setLasttime(cursor.getLong(cursor.getColumnIndex("lasttime")));
        taskDBBean.setMd5(cursor.getString(cursor.getColumnIndex("md5")));
        return taskDBBean;
    }
    private static ContentValues getContentValues(TaskDBBean taskDBBean){
        if(taskDBBean == null)return null;
        ContentValues contentValues = new ContentValues();
        contentValues.put("uid",      taskDBBean.getUid());
        contentValues.put("bbsid",    taskDBBean.getBbsid());
        contentValues.put("tasktype", taskDBBean.getTasktype());
        contentValues.put("lasttime", taskDBBean.getLasttime());
        contentValues.put("md5",      taskDBBean.getMd5());
        return contentValues;
    }

}
