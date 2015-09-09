package com.greenorange.gooutdoor.framework.Model.Event;


import android.support.v4.app.DialogFragment;

/**
 * Created by JasWorkSpace on 15/4/21.
 */
public class EventDialogFragmentCLick {
    public int ID;
    public DialogFragment dialogFragment;
    public int viewID;

    public EventDialogFragmentCLick(int ID, DialogFragment dialogFragment, int viewID){
        this.ID = ID;
        this.dialogFragment = dialogFragment;
        this.viewID  = viewID;
    }
}
