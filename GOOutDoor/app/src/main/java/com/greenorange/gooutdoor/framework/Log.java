package com.greenorange.gooutdoor.framework;

import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by JasWorkSpace on 14/12/31.
 */
public class Log {
    private static final String  TAG       = "Jas";
    private static final boolean DEBUG     = true;
    private static final boolean WriteFile = false;
    private static final String  LogFile   = "./mnt/sdcard/GOOutdoorHelperLog.txt";
    private static boolean       WriteLogFileEn = true;

    public static void e(String msg){
        e(TAG,msg);
    }
    public static void e(String tag,String msg){
        android.util.Log.d(tag,msg);
        WriteDebugInfo(msg);
    }
    public static void d(String msg){
        d(TAG,msg);
    }
    public static void d(String tag,String msg){
        if(DEBUG){
            android.util.Log.d(tag,msg);
            WriteDebugInfo(msg);
        }
    }
    private static void WriteDebugInfo(String info){
        if(WriteFile&&WriteLogFileEn&&!TextUtils.isEmpty(info)){
            RandomAccessFile file = null;
            try {
                file = new RandomAccessFile(new File(LogFile),"rw");
                file.seek(file.length());
                file.writeBytes(info + "\n");
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(file!=null){
                    try {
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            WriteLogFileEn = false;
        }
    }

}
