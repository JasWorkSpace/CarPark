package com.greenorange.myuiaccount.service.V1;

/**
 * Created by JasWorkSpace on 15/10/27.
 */
public class Config {
    /////////////////////////////////////////////////
    private final static boolean DEBUG = true;

    private static final String API_SERVER_DOMAIN_test = "http://test.kindui.com";
    private static final String API_SERVER_DOMAIN_2_test = "http://test.kindui.com";
    private static final String API_SERVER_DOMAIN = "http://myui.qingcheng.com";
    private static final String API_SERVER_DOMAIN_2 = "http://api.qingcheng.com";

    private static final String API_SMS_VERIFICATION = "http://public.qingcheng.com/action.aspx";
    private static final String API_UC_USER_REGISTER_2 = "http://api1.qingcheng.com/contactsbackup/register";
    private static final String API_UC_USER_LOGIN_2 = "http://api1.qingcheng.com/contactsbackup/login";
    private static final String API_UPDATE_USER_IMEI = "http://api1.qingcheng.com/contactsbackup/updateimei";
    private static final String API_UC_USER_EDIT_2 = "http://api1.qingcheng.com/contactsbackup/useredit";
    private static final String API_UC_GET_USER = "http://api1.qingcheng.com/contactsbackup/getuser";

    private static final String API_UC_USER_LOGIN = "/uc_user_login.php";
    private static final String API_UC_USER_REGISTER = "/uc_user_register.php";
    private static final String API_UC_USER_EDIT = "/uc_user_edit.php";
    private static final String API_MOBILE_BOOK_UPDATE = "/api/mobile/book/update";
    private static final String API_MOBILE_BOOK_GET = "/api/mobile/book/get";
    private static final String API_PACK_BOOK_NEW_GET = "/api/pack/book/new/get";
    private static final String API_LOST_PASSWORD_FOR_MOBILE = "/member.php?mod=lostpasswdformobile";
    private static final String API_CALLLOG_BOOK_UPDATE = "/api/mobile/callLog/updateOrAddCallLog";
    private static final String API_CALLLOG_BOOK_GET = "/api/mobile/callLog/recoveryCallLog";
    private static final String API_SMS_BOOK_UPDATE = "/api/mobile/sms/updateOrAddSMS";
    private static final String API_SMS_BOOK_GET = "/api/mobile/sms/recoverySMS";
    private static final String API_MMS_BOOK_UPDATE = "/api/mobile/mms/updateOrAddMMS";
    private static final String API_MMS_BOOK_GET = "/api/mobile/mms/recoveryMMS";
    private static final String API_MMS_BOOK_DELETE = "/api/mobile/mms/deleteAllMMS";
    private static final String API_MMS_TOTAL_TIME = "/api/mobile/mms/getMMSTotalAndTimestamp";
    ///////////////////////////////////////////////////////////////////////////
    /// for provision url which APK use.
    public  static String getURL_UserLogin(){
        return API_UC_USER_LOGIN_2;
    }
}
