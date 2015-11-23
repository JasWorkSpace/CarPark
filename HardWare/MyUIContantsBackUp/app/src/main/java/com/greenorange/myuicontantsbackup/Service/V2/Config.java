package com.greenorange.myuicontantsbackup.Service.V2;

/**
 * Created by JasWorkSpace on 15/10/14.
 */
public class Config {

    /////////////////////////////////////////////////
    private final static boolean DEBUG = true;
    //域名
    private final static String SERVICE_URL      = "http://gateway.api.kindui.com/call";
    private final static String SERVICE_URL_TEST = "http://test.kindui.com/gateway/call";
    //channel
    private final static String CHANNEL    = "myui_cloud";
    private final static String CHANNELKEY = "VAkx2nhVjfka5noblFEBnF4mM3n4n61T";
    //key
    private final static String KEY = "(!@#$^*)";

    public static String getServiceUrl(){
        return DEBUG ? SERVICE_URL_TEST : SERVICE_URL;
    }
    public static String getChannel(){
        return CHANNEL;
    }
    public static String getChannelkey(){
        return CHANNELKEY;
    }
    public static String getKey() {
        return KEY;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private final static String METHOD_BACKUP_CONTACTS_GET    = "cloud.storage.addressBook.get";
    private final static String METHOD_BACKUP_CONTACTS_UPDATE = "cloud.storage.addressBook.update";
    private final static String METHOD_BACKUP_CALLLOG_GET     = "cloud.storage.callLog.get";
    private final static String METHOD_BACKUP_CALLLOG_UPDATE  = "cloud.storage.callLog.update";
    private final static String METHOD_BACKUP_SMS_GET         = "cloud.storage.smsLog.get";
    private final static String METHOD_BACKUP_SMS_UPDATE      = "cloud.storage.smsLog.update";

    public static String getMethodBackupContactsGet() {
        return METHOD_BACKUP_CONTACTS_GET;
    }
    public static String getMethodBackupContactsUpdate() {
        return METHOD_BACKUP_CONTACTS_UPDATE;
    }
    public static String getMethodBackupCalllogGet() {
        return METHOD_BACKUP_CALLLOG_GET;
    }
    public static String getMethodBackupCalllogUpdate() {
        return METHOD_BACKUP_CALLLOG_UPDATE;
    }
    public static String getMethodBackupSmsGet() {
        return METHOD_BACKUP_SMS_GET;
    }
    public static String getMethodBackupSmsUpdate() {
        return METHOD_BACKUP_SMS_UPDATE;
    }
}
