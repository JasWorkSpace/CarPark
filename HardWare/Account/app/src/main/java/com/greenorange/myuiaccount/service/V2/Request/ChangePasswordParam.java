package com.greenorange.myuiaccount.service.V2.Request;


import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.service.V2.ServiceHelper;

/**
 * Created by JasWorkSpace on 15/10/16.
 */
public class ChangePasswordParam extends BaseRequestParam {
    public String account     = "";
    public String oldPassword = "";
    public String newPassword = "";
    @Override
    public boolean checkValid() {
        if(ServiceHelper.isAccountValid(account)
                && ServiceHelper.isPasswordValid(oldPassword)
                && ServiceHelper.isPasswordValid(newPassword))return true;
        return false;
    }
    public ChangePasswordParam(){this(null);}
    public ChangePasswordParam(ChangePasswordParam changePassword){
        if(changePassword != null){
            account     = changePassword.account;
            oldPassword = changePassword.oldPassword;
            newPassword = changePassword.newPassword;
        }
    }
    @Override
    public String getRequestParam() {
        try {
            ChangePasswordParam changePassword = new ChangePasswordParam(ChangePasswordParam.this);
            changePassword.setOldPassword(ServiceHelper.getEncrypt(changePassword.oldPassword));
            changePassword.setNewPassword(ServiceHelper.getEncrypt(changePassword.newPassword));
            return new Gson().toJson(changePassword);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ChangePassword getRequestParam " + e.toString());
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ChangePassword{")
                .append("account="+account)
                .append(", oldPassword="+oldPassword)
                .append(", newPassword="+newPassword)
                .append("}");
        return sb.toString();
    }
    ///////////

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
