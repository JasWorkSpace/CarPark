package com.greenorange.myuicontantsbackup.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.greenorange.myuicontantsbackup.BackUpApplication;
import com.greenorange.myuicontantsbackup.Log;

/**
 * Created by JasWorkSpace on 15/11/18.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("AlarmReceiver onReceive action="+action);
        if(TextUtils.equals(action, Intent.ACTION_BOOT_COMPLETED)){
                //do nothing here .
        }else if(TextUtils.equals(action, "com.greenorange.myuicontantsbackup.autotimeout")
                 || TextUtils.equals(action, "android.net.conn.CONNECTIVITY_CHANGE")){
            BackUpApplication.getInstance().checkBackupAutoTaskAndStart();
        }
    }

}
