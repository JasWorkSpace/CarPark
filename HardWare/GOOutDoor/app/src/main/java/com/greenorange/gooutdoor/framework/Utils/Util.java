package com.greenorange.gooutdoor.framework.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.Application.Contants;
import com.greenorange.gooutdoor.framework.Dao.EventDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Event.EventDialogFragmentCLick;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Model.Event.EventMSG;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.greenorange.gooutdoor.framework.Model.Event.Report;
import com.greenorange.gooutdoor.framework.Model.Event.ReportID;

/**
 * Created by JasWorkSpace on 15/4/19.
 */
public class Util {

    public static void notifySportsSTATEChange(int laststate, int newstate){
        Intent intent = new Intent(Contants.BROADCAST.ACTION_SPORTS_STATE_CHANGE);
        intent.putExtra(Contants.BROADCAST.SPORTS_STATE_LAST, laststate);
        intent.putExtra(Contants.BROADCAST.SPORTS_STATE_NEW, newstate);
        GOApplication.getInstance().sendBroadcast(intent);
        Util.postEvent(Util.produceEventStateChange(EventID.ID_STATE_APPLICATION_SPORTSSTATE,
                -1 , null , newstate ,laststate));
        Util.postReport(Util.produceReport(ReportID.ID_REPORT_SPORTS_STATE_CHANGE, // report state change
                SportUtil.getSportStateString(newstate), SportUtil.getSportStateString(laststate)));
    }
    public static void notifySportsTypeChange(Context context, int type){
        Util.postReport(Util.produceReport(ReportID.ID_REPORT_SPORTS_TYPE_START, // report new type
                context.getString(SportUtil.getSportsTypeLab(type)), "UNUSE"));
    }
    public static void notifyFlashmodeChanged(int lasttype, int newtype){
        Intent intent = new Intent(Contants.BROADCAST.ACTION_FLASH_MODE_CHANGE);
        intent.putExtra(Contants.BROADCAST.FLASH_MODE_LAST, lasttype);
        intent.putExtra(Contants.BROADCAST.FLASH_MODENEW, newtype);
        GOApplication.getInstance().sendBroadcast(intent);
        Util.postReport(Util.produceReport(ReportID.ID_REPORT_FLASH_MODE_CHANGE, // report new mode
                FlashUtil.getFlashModeString(newtype), FlashUtil.getFlashModeString(lasttype)));
    }
    public static void notifyScreenOrientationChange(int orientation){
        Util.postReport(Util.produceReport(ReportID.ID_REPORT_SCREEN_ORIENTATION_CHANGE, // report new orientation
                AndroidUtils.getOrientationString(orientation), "UNUSE"));
    }
    public static void notifySmileModeChange(String name){
        Util.postReport(Util.produceReport(ReportID.ID_REPORT_SMILE_MODE_CHANGE, // report new smile mode
                name, "UNUSE"));
    }
    public static void notifyLocationServiceConnectionState(boolean connection){
        Intent intent = new Intent(Contants.BROADCAST.ACTION_LOCATIONSERVICE_CONNECTION);
        intent.putExtra(Contants.BROADCAST.LOCATIONSERVICE_CONNECTION_STATE, connection);
        GOApplication.getInstance().sendBroadcast(intent);
    }
    public static void notifyApplicationFinish(){
        Intent intent = new Intent(Contants.BROADCAST.ACTION_APPLICATION_CHANGE);
        GOApplication.getInstance().sendBroadcast(intent);
    }
    //////////////////////////////////////
    public static EventDao getEventDao(){
        return (EventDao) GOApplication.getDaoManager().getManager(Dao.EventDao);
    }
    public static boolean postEvent(Object object){
        return getEventDao().postEvent(object);
    }
    public static boolean postReport(Object object){return getEventDao().postReport(object);}
    public static EventClick produceEventClick(int ID, View view, int viewId){
        return getEventDao().produceEventClick(ID, view, viewId);
    }
    public static EventStateChange produceEventStateChange(int ID, int viewID,Object object, int newState, int lastState){
        return getEventDao().produceEventStateChange(ID, viewID, object, newState, lastState);
    }
    public static EventDialogFragmentCLick produceEventDialogFragmentCLick(int ID, DialogFragment dialogFragment, int viewID){
        return getEventDao().produceEventDialogFragmentCLick(ID, dialogFragment, viewID);
    }
    public static EventMSG produceEventMSG(int ID, int msg, Object object){
        return getEventDao().produceEventMSG(ID, msg, object);
    }
    public static Report produceReport(String reportid, String... reports){
        return getEventDao().produceReport(reportid, reports);
    }
}
