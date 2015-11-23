package com.greenorange.myuicontantsbackup.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JasWorkSpace on 15/11/12.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME    = "accountbackup.db";
    private final static int    DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTaskDB(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //////////////////////////////////////////////////////////////////////////////////
    private void createTaskDB(SQLiteDatabase db){
        //// taskdb
        db.execSQL("create table if not exists task_info(_id integer PRIMARY KEY AUTOINCREMENT,"
                +"uid char not null,bbsid char,tasktype int,lasttime long,md5 char,create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))");

        //// backuporrestoreDB
        db.execSQL("create table if not exists backorrestoretask_info(_id integer PRIMARY KEY AUTOINCREMENT,"
                +"tasktype int,lasttime long,runtasktype int,create_date TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))");
    }
}
