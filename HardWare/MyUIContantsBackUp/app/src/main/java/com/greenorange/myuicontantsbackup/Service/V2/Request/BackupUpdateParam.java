package com.greenorange.myuicontantsbackup.Service.V2.Request;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.Service.Util.ZIPUtils;
import com.greenorange.myuicontantsbackup.Service.V2.ServiceHelper;
import com.greenorange.myuicontantsbackup.Task.Utils.BackupDataUtil;

/**
 * Created by JasWorkSpace on 15/11/16.
 */
public class BackupUpdateParam extends BaseRequestParam {

    private String uuid = "";
    private String imei = "";
    private String data = "";
    private String dataVersion   = "";
    private String clientVersion = "";

    public BackupUpdateParam(){
        this(null);
    }

    public BackupUpdateParam(BackupUpdateParam backupUpdateParam){
        if(backupUpdateParam != null){
            uuid  = backupUpdateParam.uuid;
            imei  = backupUpdateParam.imei;
            data  = backupUpdateParam.data;
            dataVersion   = backupUpdateParam.dataVersion;
            clientVersion = backupUpdateParam.clientVersion;
        }
    }
    //uuid clientVersion dataVersion must no null.
    @Override
    public boolean checkValid() {
        if(TextUtils.isEmpty(uuid)
                ||TextUtils.isEmpty(clientVersion)
                ||TextUtils.isEmpty(dataVersion))return false;
        return true;
    }
    @Override
    public String getRequestParam() {
        try{
            BackupUpdateParam backupUpdateParam = new BackupUpdateParam(BackupUpdateParam.this);
            if(!TextUtils.isEmpty(backupUpdateParam.data)){//des encrypt data.
                backupUpdateParam.data = BackupDataUtil.getEncrypt(backupUpdateParam.data);
            }
            return new Gson().toJson(backupUpdateParam);
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("BackupUpdateParam getRequestParam " + e.toString());
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BackupGetParam{")
                .append("uuid="+uuid)
                .append(", imei="+imei)
                .append(", data="+data)
                .append(", dataVersion="+dataVersion)
                .append(", clientVersion="+clientVersion)
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}
