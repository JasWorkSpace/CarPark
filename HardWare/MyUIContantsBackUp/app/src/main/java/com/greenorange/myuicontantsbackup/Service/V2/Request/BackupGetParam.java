package com.greenorange.myuicontantsbackup.Service.V2.Request;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.greenorange.myuicontantsbackup.Log;

/**
 * Created by JasWorkSpace on 15/11/16.
 */
public class BackupGetParam extends BaseRequestParam {

    private String uuid = "";
    private String imei = "";
    private String dataVersion = "";

    public BackupGetParam(){
        this(null);
    }
    public BackupGetParam(BackupGetParam backupGetParam){
        if(backupGetParam != null){
            uuid  = backupGetParam.uuid;
            imei  = backupGetParam.imei;
            dataVersion = backupGetParam.dataVersion;
        }
    }
    @Override
    public boolean checkValid() {
        if(TextUtils.isEmpty(uuid))return false;
        return true;
    }
    @Override
    public String getRequestParam() {
        try{
            return new Gson().toJson(BackupGetParam.this);
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("BackupGetParam getRequestParam " + e.toString());
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BackupGetParam{")
                .append("uuid="+uuid)
                .append(", imei="+imei)
                .append(", dataVersion="+dataVersion)
                .append("}");
        return sb.toString();
    }
    ///////////////////////////////////////////////////////////////////

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }
}
