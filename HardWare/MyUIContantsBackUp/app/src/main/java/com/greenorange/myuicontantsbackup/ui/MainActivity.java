package com.greenorange.myuicontantsbackup.ui;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.greenorange.myuicontantsbackup.BackUpApplication;
import com.greenorange.myuicontantsbackup.Log;
import com.greenorange.myuicontantsbackup.R;
import com.greenorange.myuicontantsbackup.Service.V2.ServiceHelper;
import com.greenorange.myuicontantsbackup.Task.TaskController;
import com.greenorange.myuicontantsbackup.ui.fragment.MainFragment;
import com.greenorange.myuicontantsbackup.utils.PREFERENCE;



public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(
            R.id.content, new MainFragment(), "MainFragment"
        ).commit();
        initActionBar();
        findViewById(R.id.restore).setOnClickListener(this);
        findViewById(R.id.backup).setOnClickListener(this);
    }
    private void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View view = View.inflate(MainActivity.this, R.layout.toolbar, null);
        TextView title = (TextView) view.findViewById(R.id.toolbar_title);
        title.setText(R.string.main_activity_title);
        actionBar.setCustomView(view, getActionLayoutParams());
    }
    public ActionBar.LayoutParams getActionLayoutParams(){
        return new ActionBar.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                .registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    ///////////////////////////////////////////////////////
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("onSharedPreferenceChanged-->"+ PREFERENCE.toString(MainActivity.this));
        if(TextUtils.equals(key, PREFERENCE.KEY_AUTO_BACKUP_ENABLE)){
            BackUpApplication.getInstance().resetAutoBackupAlerm();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backup:{
                if(!showProgressDialog(BackUpApplication.getInstance().checkBackupTaskAndStart()
                        , MainActivity.this.getString(R.string.message_backuping))){
                    Toast.makeText(MainActivity.this, "backup is running", Toast.LENGTH_SHORT).show();
                }
            }break;
            case R.id.restore:{
                if(!showProgressDialog(BackUpApplication.getInstance().checkRestoreTaskAndStart()
                        , MainActivity.this.getString(R.string.message_restoreing))){
                    Toast.makeText(MainActivity.this, "restore is running", Toast.LENGTH_SHORT).show();
                }
            }break;
        }
    }
    private boolean showProgressDialog(final TaskController taskController, String message){
        if(taskController == null){
            return false;
        }
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
            @Override
            public void onCancel(DialogInterface dialog) {
                if(taskController != null){
                    taskController.stop();
                }
            }
        });
        progressDialog.setCancelable(true);
        progressDialog.setMessage(message);
        taskController.setTaskControllerStepChangeListener(new TaskController.onTaskControllerStepChangeListener() {
            @Override
            public void onTaskControllerStepChange() {

            }
            @Override
            public void onTaskPostExecute() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
        progressDialog.getWindow().setGravity(Gravity.BOTTOM);
        progressDialog.show();
        return true;
    }
}
