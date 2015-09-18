package com.greenorange.gooutdoor.framework.Model.Event;

/**
 * Created by JasWorkSpace on 15/4/20.
 */
public class EventStateChange {
    public int ID;
    public Object object;
    public int    viewID;
    public int    newState;
    public int    lastState;
    public EventStateChange(int ID, int viewID, Object object, int newState, int lastState){
        this.ID        = ID;
        this.viewID    = viewID;
        this.object    = object;
        this.newState  = newState;
        this.lastState = lastState;
    }
    @Override
    public String toString() {
        return String.format("EventStateChange{ID=%d, viewID=%d, newState=%d, lastState=%d}", ID, viewID, newState, lastState);
    }
}
