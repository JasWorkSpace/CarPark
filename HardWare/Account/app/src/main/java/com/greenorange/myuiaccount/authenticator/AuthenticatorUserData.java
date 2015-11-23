package com.greenorange.myuiaccount.authenticator;

import android.text.TextUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by JasWorkSpace on 15/10/30.
 */
public class AuthenticatorUserData {

    public String MyUIName;

    public String MyUIPassWord;

    public AuthenticatorUserData(String myUIName, String myUIPassWord) throws Exception {
        if (TextUtils.isEmpty(myUIName) || TextUtils.isEmpty(myUIPassWord))
            throw new Exception("invalid param !!!");
        MyUIName = myUIName;
        MyUIPassWord = myUIPassWord;
    }
    public boolean isViable(){
        return !(TextUtils.isEmpty(MyUIName) || TextUtils.isEmpty(MyUIPassWord));
    }

    public String getUserData() throws UnsupportedEncodingException {
        return new Gson().toJson(AuthenticatorUserData.this);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("AuthenticatorUserData{")
                .append("MyUIName="+MyUIName)
                .append(", MyUIPassWord="+MyUIPassWord)
                .append("}");
        return sb.toString();
    }

    public String getMyUIName() {
        return MyUIName;
    }

    public void setMyUIName(String myUIName) {
        MyUIName = myUIName;
    }

    public String getMyUIPassWord() {
        return MyUIPassWord;
    }

    public void setMyUIPassWord(String myUIPassWord) {
        MyUIPassWord = myUIPassWord;
    }
}
