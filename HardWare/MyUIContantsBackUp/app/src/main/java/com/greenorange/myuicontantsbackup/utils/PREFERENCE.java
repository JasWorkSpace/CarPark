package com.greenorange.myuicontantsbackup.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import com.greenorange.myuicontantsbackup.R;

/**
 * Created by JasWorkSpace on 15/11/18.
 */
public class PREFERENCE {

    public final static String KEY_BACKUP_CONTACTS = "key_backup_contacts";
    public final static String KEY_BACKUP_SMS      = "key_backup_sms";
    public final static String KEY_BACKUP_CALLLOG  = "key_backup_calllog";

    public final static String KEY_AUTO_BACKUP_ENABLE      = "key_backup_auto";
    public final static String KEY_AUTO_BACKUP_WIFI_ENABLE = "key_backup_auto_wifi";
    public final static String KEY_AUTO_BACKUP_FRQ         = "key_backup_auto_frq";

    public final static String KEY_RESTORE_CONTACTS = "key_restore_contacts";
    public final static String KEY_RESTORE_SMS      = "key_restore_sms";
    public final static String KEY_RESTORE_CALLLOG  = "key_restore_calllog";

    public static boolean getBackupContactsEnable(Context context){
        return getEnable(context, KEY_BACKUP_CONTACTS,
                context.getResources().getBoolean(R.bool.backupactivity_backup_value_contacts_defaultvalue));
    }
    public static boolean getBackupSMSEnable(Context context){
        return getEnable(context, KEY_BACKUP_SMS,
                context.getResources().getBoolean(R.bool.backupactivity_backup_value_sms_defaultvalue));
    }
    public static boolean getBackupCallLogEnable(Context context){
        return getEnable(context, KEY_BACKUP_CALLLOG,
                context.getResources().getBoolean(R.bool.backupactivity_backup_value_celllog_defaultvalue));
    }
    /////////////////////////
    public static boolean getAutoBackupEnable(Context context){
        return getEnable(context, KEY_AUTO_BACKUP_ENABLE,
                context.getResources().getBoolean(R.bool.backupdativity_autobackup_defaultvalue));
    }
    public static boolean getAutoBackupWifiEnable(Context context){
        return getEnable(context, KEY_AUTO_BACKUP_WIFI_ENABLE,
                context.getResources().getBoolean(R.bool.backupdativity_autobackup_wifi_defaultvalue));
    }
    public static String getAutoBackupFrq(Context context){
        return getString(context, KEY_AUTO_BACKUP_FRQ,
                context.getResources().getString(R.string.backupactivity_autobackup_defaultvalue));
    }
    ///////////////////
    public static boolean getRestoreContacesEnable(Context context){
        return getEnable(context, KEY_RESTORE_CONTACTS, true);
    }
    public static boolean getRestoreSMSEnable(Context context){
        return getEnable(context, KEY_RESTORE_SMS, true);
    }
    public static boolean getRestoreCallLogEnable(Context context){
        return getEnable(context, KEY_RESTORE_CALLLOG, true);
    }
    //////////////////////////
    public static boolean getEnable(Context context, String key, boolean defaultvalue){
        return PreferenceManager.getDefaultSharedPreferences(context)
                            .getBoolean(key, defaultvalue);
    }
    public static String getString(Context context, String key, String defaultvalue){
        return PreferenceManager.getDefaultSharedPreferences(context)
                            .getString(key, defaultvalue);
    }
    //////////////////////////////////////////////

    public static String toString(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("PREFERENCE{")
                .append("KEY_BACKUP_CONTACTS="+getBackupContactsEnable(context))
                .append(", KEY_BACKUP_SMS="+getBackupSMSEnable(context))
                .append(", KEY_BACKUP_CALLLOG="+getBackupCallLogEnable(context))
                .append(", KEY_AUTO_BACKUP_ENABLE="+getAutoBackupEnable(context))
                .append(", KEY_AUTO_BACKUP_WIFI_ENABLE="+getAutoBackupWifiEnable(context))
                .append(", KEY_AUTO_BACKUP_FRQ="+getAutoBackupFrq(context))
                .append(", KEY_RESTORE_CONTACTS="+getRestoreContacesEnable(context))
                .append(", KEY_RESTORE_SMS="+getRestoreSMSEnable(context))
                .append(", KEY_RESTORE_CALLLOG="+getRestoreCallLogEnable(context))
                .append("}");
        return sb.toString();
    }
}
