package com.greenorange.gooutdoor.framework.Model.Event;

/**
 * Created by JasWorkSpace on 15/4/21.
 */
public class EventMSG {
    public int ID;
    public int msg;
    public Object object;
    public EventMSG(int ID, int msg, Object object){
        this.ID     = ID;
        this.msg    = msg;
        this.object = object;
    }
    @Override
    public String toString() {
        return String.format("EventMSG{ID=%d, msg=%d, %s}", ID, msg, (object==null ? "object is null" : " has object"));
    }
}
