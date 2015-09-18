package com.greenorange.gooutdoor.framework.Model.Event;

import android.text.TextUtils;

import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;

import java.util.HashMap;

/**
 * Created by JasWorkSpace on 15/9/1.
 */
public class Report {

    public String reportid;

    public HashMap<String, String> eventvalue;

    public Report(String id){
        reportid = id;
    }
    public Report(String id, String... value){
        reportid   = id;
        eventvalue = null;
        if(value != null && value.length >0 ){
            if((value.length%2) != 0){
                throw new ApplicationException("why value length is " + value.length);
            }
            eventvalue = new HashMap<String, String>();
            for(int index=0; index < value.length; index++){
                eventvalue.put(value[index++], value[index]);
            }
        }
        if(TextUtils.isEmpty(reportid)){
            throw new ApplicationException("why reportid is null !!!");
        }
    }
    @Override
    public String toString() {
        return String.format("Report{ID=%d, msg=%s}", (eventvalue==null ? "object is null" : eventvalue.toString()));
    }
}
