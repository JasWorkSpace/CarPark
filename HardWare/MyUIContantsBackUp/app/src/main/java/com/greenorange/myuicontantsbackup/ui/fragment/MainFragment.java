package com.greenorange.myuicontantsbackup.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.text.TextUtils;

import com.greenorange.myuicontantsbackup.Account.UserInfo;
import com.greenorange.myuicontantsbackup.Bean.TaskDBBean;
import com.greenorange.myuicontantsbackup.R;
import com.greenorange.myuicontantsbackup.Task.Task;
import com.greenorange.myuicontantsbackup.db.Taskdb;
import com.greenorange.myuicontantsbackup.utils.PREFERENCE;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

/**
 * Created by JasWorkSpace on 15/10/30.
 */
public class MainFragment extends BasePreferenceFragment {

    private CheckBoxPreference restore_contacts;
    private CheckBoxPreference restore_sms;
    private CheckBoxPreference restore_calllog;
    private Taskdb mTaskDB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_activity_main);
        PreferenceScreen restore = (PreferenceScreen) findPreference("restore_setting");
        restore_contacts = (CheckBoxPreference) restore.findPreference(PREFERENCE.KEY_RESTORE_CONTACTS);
        restore_sms      = (CheckBoxPreference) restore.findPreference(PREFERENCE.KEY_RESTORE_SMS);
        restore_calllog  = (CheckBoxPreference) restore.findPreference(PREFERENCE.KEY_RESTORE_CALLLOG);
        mTaskDB = Taskdb.getInstace(getActivity());
    }

    BroadcastReceiver  receiver = new  BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(TextUtils.equals(action, "com.greenorange.myuicontantsbackup.taskchange")){
                loadLastTime();
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver
                , new IntentFilter("com.greenorange.myuicontantsbackup.taskchange"));
        loadLastTime();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    private synchronized void loadLastTime(){
        UserInfo userInfo = myUIAccount.getMyUIUserInfo();
        if(userInfo != null) {
            loadLastTime(restore_contacts, userInfo.getUid(), Task.TASK_TYPE_RESTORE_CONTACTS);
            loadLastTime(restore_sms, userInfo.getUid(), Task.TASK_TYPE_RESTORE_SMS);
            loadLastTime(restore_calllog, userInfo.getUid(), Task.TASK_TYPE_RESTORE_CALLLOG);
        }else{
            clear();
        }
    }
    private synchronized void clear(){
        restore_contacts.setSummary("");
        restore_sms.setSummary("");
        restore_calllog.setSummary("");
    }
    private void loadLastTime(CheckBoxPreference preference, String uid, int type){
        if(!TextUtils.isEmpty(uid)){
            TaskDBBean taskDBBean = mTaskDB.getTask(uid, type);
            if(taskDBBean != null){
                preference.setSummary(getTime(taskDBBean.getLasttime()));
            }
        }
    }

    private String getTime(long timestap){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timestap);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if(!myUIAccount.isLoaded()){
            myUIAccount.startDefaultLogainAccount(getActivity());
            return false;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
