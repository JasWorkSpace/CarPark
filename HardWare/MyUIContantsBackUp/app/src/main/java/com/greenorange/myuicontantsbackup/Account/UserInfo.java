package com.greenorange.myuicontantsbackup.Account;

/**
 * Created by JasWorkSpace on 15/10/21.
 */
public class UserInfo {
    public String uid      = "";
    public String username = "";
    public String realname = "";
    public String shopid   = "";
    public String bbsid    = "";
    public String mobile   = "";
    public String email    = "";
    public String status   = "";

    public UserInfo(){
        this(null);
    }
    public UserInfo(UserInfo info){
        if(info != null){
            uid      = info.uid;
            username = info.username;
            realname = info.realname;
            shopid   = info.shopid;
            bbsid    = info.bbsid;
            mobile   = info.mobile;
            email    = info.email;
            status   = info.status;
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UserInfo{")
                .append("uid=" + uid)
                .append(", username="+username)
                .append(", realname="+realname)
                .append(", shopid="+shopid)
                .append(", bbsid="+bbsid)
                .append(", mobile="+mobile)
                .append(", email="+email)
                .append(", status="+status)
                .append("}");
        return sb.toString();
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getBbsid() {
        return bbsid;
    }

    public void setBbsid(String bbsid) {
        this.bbsid = bbsid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
