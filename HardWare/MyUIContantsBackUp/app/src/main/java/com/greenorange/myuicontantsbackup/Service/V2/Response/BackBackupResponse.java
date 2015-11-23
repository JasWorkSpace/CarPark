package com.greenorange.myuicontantsbackup.Service.V2.Response;

/**
 * Created by JasWorkSpace on 15/11/18.
 */
public class BackBackupResponse {
    /*{\"_id\":\"56497e6c3739aa7cc3c4ddee\",\"createdDate\":\"2015-11-16\",\"createdOn\":1447657068310,\"data\":\"call log text\",\"imei\":\"86yyyy010xx190\",\"updatedDate\":\"2015-11-16\",\"updatedOn\":1447657068310,\"uuid\":\"2\"}*/
    private String _id = "";
    private String createdDate = "";
    private String createdOn   = "";
    private String data        = "";
    private String imei        = "";
    private String updatedDate = "";
    private String updatedOn   = "";
    private String uuid        = "";
    private String clientVersion = "";
    private String dataVersion   = "";

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }
}
