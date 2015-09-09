package com.greenorange.gooutdoor.framework.Dao.impl;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.GOConfig;
import com.greenorange.gooutdoor.framework.Dao.EventDao;
import com.greenorange.gooutdoor.framework.Feature;
import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Event.EventDialogFragmentCLick;
import com.greenorange.gooutdoor.framework.Model.Event.EventMSG;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.greenorange.gooutdoor.framework.Model.Event.Report;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by JasWorkSpace on 15/4/20.
 */
public class EventDaoImpl implements EventDao {
    private static final Bus BUS = new Bus();
    private Context mContext;
    public EventDaoImpl(Context context){
        mContext = context;
        if(Feature.isUmengSupport()) {
            AnalyticsConfig.setAppkey("54fe8b69fd98c5a997000479");
            AnalyticsConfig.setChannel(GOConfig.CHANNEL);
        }
    }
    @Override
    public void register(Object object) {
        BUS.register(object);
    }
    @Override
    public void unregister(Object object) {
        BUS.unregister(object);
    }
    @Override
    public boolean postEvent(Object event) {
        if(event != null){
            BUS.post(event);
            return true;
        }
        return false;
    }
    @Override
    @Produce
    public EventClick produceEventClick(int ID, View view, int viewID) {
        return new EventClick(ID, view, viewID);
    }
    @Override
    @Produce
    public EventStateChange produceEventStateChange(int ID, int viewID,Object object, int newState, int lastState) {
        return new EventStateChange(ID, viewID, object, newState, lastState);
    }
    @Override
    @Produce
    public EventDialogFragmentCLick produceEventDialogFragmentCLick(int ID, DialogFragment dialogFragment, int viewID) {
        return new EventDialogFragmentCLick(ID, dialogFragment, viewID);
    }
    @Override
    public EventMSG produceEventMSG(int ID, int msg, Object object) {
        return new EventMSG(ID, msg, object);
    }
    @Override
    public void onResume(Context context) {
        if(Feature.isUmengSupport()){
            MobclickAgent.onResume(context);
        }
    }
    @Override
    public void onPause(Context context) {
        if(Feature.isUmengSupport()){
            MobclickAgent.onPause(context);
        }
    }
    @Override
    public Report produceReport(String reportid, String... reports) {
        return new Report(reportid, reports);
    }
    @Override
    public boolean postReport(Object object) {
        if(object instanceof Report){
            if(Feature.isUmengSupport()){
                Report report = (Report) object;
                MobclickAgent.onEvent(mContext, report.reportid, report.eventvalue);
            }
        }
        return false;
    }
}
