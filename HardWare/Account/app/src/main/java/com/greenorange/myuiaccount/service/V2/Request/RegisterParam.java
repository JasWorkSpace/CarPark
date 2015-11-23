package com.greenorange.myuiaccount.service.V2.Request;

import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;


/**
 * Created by JasWorkSpace on 15/10/16.
 */
public class RegisterParam extends BaseRequestParam {
    public final static String VALIDATEBY_MOBILE = "mobile";
    public final static String VALIDATEBY_EMAIL  = "email";
    public String account     = "";
    public String realname    = "";
    public String password    = "";
    public String mobileno    = "";
    public String email       = "";
    public String validatedBy = "";
    public RegisterParam(){this(null);}
    public RegisterParam(RegisterParam registerParam){
        if(registerParam != null){
            account     = registerParam.account;
            realname    = registerParam.realname;
            password    = registerParam.password;
            mobileno    = registerParam.mobileno;
            email       = registerParam.email;
            validatedBy = registerParam.validatedBy;
        }
    }
    @Override
    public boolean checkValid() {
        if(!ServiceHelper.isAccountValid(account))return false;
        if(!ServiceHelper.isPasswordValid(password))return false;
        if(!(ServiceHelper.isMebliePhoneNumberValid(mobileno)
                || ServiceHelper.isEmailNumberValid(email)))return false;
        return true;
    }
    @Override
    public String getRequestParam() {
        try {
            RegisterParam registerParam = new RegisterParam(RegisterParam.this);
            registerParam.password = ServiceHelper.getEncrypt(registerParam.password);
            registerParam.setValidatedBy(
                    ServiceHelper.isMebliePhoneNumberValid(registerParam.mobileno)
                            ? VALIDATEBY_MOBILE : VALIDATEBY_EMAIL);
            return new Gson().toJson(registerParam);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("RegisterParam getRequestParam " + e.toString());
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("RegisterParam{")
                .append("account="+account)
                .append(", realname="+realname)
                .append(", password="+password)
                .append(", mobileno="+mobileno)
                .append(", email="+email)
                .append(", validatedBy="+validatedBy)
                .append("}");
        return sb.toString();
    }
    ////////////

    public static String getValidatebyMobile() {
        return VALIDATEBY_MOBILE;
    }

    public static String getValidatebyEmail() {
        return VALIDATEBY_EMAIL;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }
}
