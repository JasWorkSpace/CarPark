package com.greenorange.gooutdoor.framework.Dao;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Event.EventDialogFragmentCLick;
import com.greenorange.gooutdoor.framework.Model.Event.EventMSG;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.greenorange.gooutdoor.framework.Model.Event.Report;

/**
 * Created by JasWorkSpace on 15/4/20.
 */
public interface EventDao {

    public void register(Object object);

    public void unregister(Object object);

    public boolean postEvent(Object event);

    public EventClick produceEventClick(int ID, View view, int viewID);

    public EventStateChange produceEventStateChange(int ID, int viewID,Object object, int newState, int lastState);

    public EventDialogFragmentCLick produceEventDialogFragmentCLick(int ID, DialogFragment dialogFragment, int viewID);

    public EventMSG produceEventMSG(int ID, int msg, Object object);

    public void onResume(Context context);

    public void onPause(Context context);

    public Report produceReport(String reportid, String... reports);

    public boolean postReport(Object object);
}
