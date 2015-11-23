package com.greenorange.myuiaccount.service.V2;

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
    private final static String CHANNEL    = "myui_account";
    private final static String CHANNELKEY = "IGSNVI8upAPnQBCIZhVFTaNmzUKTLzYC";
    //key
    private final static String KEY = "(!@#$^*)";
    //
    private final static String FINDPSAAWORD_URL = "http://passport.qingcheng.com/m/password/forgetpassword";

    private final static String METHOD_LOGIN          = "common.auth.account.login";
    private final static String METHOD_REGISTER       = "common.auth.account.register";
    private final static String METHOD_CHANGEPASSWORD = "common.auth.account.changePassword";
    private final static String METHOD_UPDATEPASSWORD = "common.auth.account.updatePassword";
    private final static String METHOD_GETUSER        = "common.auth.account.getUser";
    private final static String METHOD_SENDMESSAGE    = "common.message.sms.send";
    private final static String METHOD_CHECKLEGENCYACCOUNTSTATE = "common.auth.account.checkLegacyAccountState";
    private final static String METHOD_BINDLEGENCYACCOUNT       = "common.auth.account.bindLegacyAccount";
    private final static String METHOD_UNBINDLEGENCYACCOUNT     = "common.auth.account.unbindLegacyAccount";
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
    public static String getMethodLogin() {
        return METHOD_LOGIN;
    }
    public static String getMethodRegister() {
        return METHOD_REGISTER;
    }
    public static String getMethodChangepassword() {
        return METHOD_CHANGEPASSWORD;
    }
    public static String getMethodUpdatepassword() {
        return METHOD_UPDATEPASSWORD;
    }
    public static String getMethodGetuser() {
        return METHOD_GETUSER;
    }
    public static String getMethodSendmessage() {
        return METHOD_SENDMESSAGE;
    }
    public static String getFindpsaawordUrl() {
        return FINDPSAAWORD_URL;
    }
    public static String getMethodChecklegencyaccountstate() {
        return METHOD_CHECKLEGENCYACCOUNTSTATE;
    }
    public static String getMethodBindlegencyaccount() {
        return METHOD_BINDLEGENCYACCOUNT;
    }
    public static String getMethodUnbindlegencyaccount() {
        return METHOD_UNBINDLEGENCYACCOUNT;
    }
}
