package com.greenorange.myuicontantsbackup.Task.SMS;

import android.text.TextUtils;

public class SmsData {
	private String _id = "";
	private String thread_id = "";
	private String address = "";
	private String m_size = "";
	private String person = "";
	private String date = "";
	private String date_sent = "";
	private String protocol = "";
	private String read = "";
	private String status = "";
	private String type = "";
	private String reply_path_present = "";
	private String subject = "";
	private String body = "";
	private String service_center = "";
	private String locked = "";
	private String sub_id = "";
	private String error_code = "";
	private String creator = "";
	private String seen = "";
	private String ipmsg_id = "";
	private String ref_id = "";
	private String total_len = "";
	private String rec_len = "";
	private String messagecount = "";
      private String readcount = "";

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getM_size() {
        return m_size;
    }

    public void setM_size(String m_size) {
        this.m_size = m_size;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReply_path_present() {
        return reply_path_present;
    }

    public void setReply_path_present(String reply_path_present) {
        this.reply_path_present = reply_path_present;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getService_center() {
        return service_center;
    }

    public void setService_center(String service_center) {
        this.service_center = service_center;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getIpmsg_id() {
        return ipmsg_id;
    }

    public void setIpmsg_id(String ipmsg_id) {
        this.ipmsg_id = ipmsg_id;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public String getTotal_len() {
        return total_len;
    }

    public void setTotal_len(String total_len) {
        this.total_len = total_len;
    }

    public String getRec_len() {
        return rec_len;
    }

    public void setRec_len(String rec_len) {
        this.rec_len = rec_len;
    }

    public String getMessagecount() {
        return messagecount;
    }

    public void setMessagecount(String messagecount) {
        this.messagecount = messagecount;
    }

    public String getReadcount() {
        return readcount;
    }

    public void setReadcount(String readcount) {
        this.readcount = readcount;
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof SmsData){
            SmsData smsData = (SmsData) o;
            if(!TextUtils.equals(address, smsData.address)
                    || !TextUtils.equals(body, smsData.body))
                return false;
            return true;
        }
        return super.equals(o);
    }

}
