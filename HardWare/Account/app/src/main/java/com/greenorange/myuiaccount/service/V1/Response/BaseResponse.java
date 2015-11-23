package com.greenorange.myuiaccount.service.V1.Response;


import com.greenorange.myuiaccount.Util.Utils;

/**
 * Created by JasWorkSpace on 15/10/27.
 */
public abstract class BaseResponse {
    private String status   = "";
    private String message  = "";
    private String msg      = "";

    public BaseResponse(){
        this(null);
    }
    public BaseResponse(BaseResponse baseResponse){
        if(baseResponse != null){
            status  = baseResponse.status;
            message = baseResponse.message;
            msg     = baseResponse.msg;
        }
    }
    public boolean checkValid(){
        return true;
    }
    public String getFailMessage(){
       return message;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BaseResponse{")
                .append("status="+Utils.getString(status))
                .append(", message="+Utils.getString(message))
                .append(", msg="+Utils.getString(msg))
                .append("}");
        return sb.toString();
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public abstract boolean tranformResponse();
}

