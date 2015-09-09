package com.greenorange.gooutdoor.framework.Model.Event;

import android.view.View;

/**
 * Created by JasWorkSpace on 15/4/20.
 */
public class EventClick {
    public int  ID;
    public View view;
    public int  viewID;
    public EventClick(int ID, View view, int viewID){
        this.ID      = ID;
        this.view    = view;
        this.viewID  = viewID;
    }
    @Override
    public String toString() {
        return String.format("EventClick{ID=%d, viewID=%d}", ID, viewID);
    }
}
