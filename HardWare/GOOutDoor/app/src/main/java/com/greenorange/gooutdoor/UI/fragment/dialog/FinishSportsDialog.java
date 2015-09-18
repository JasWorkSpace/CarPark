package com.greenorange.gooutdoor.UI.fragment.dialog;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Utils.Util;
import com.greenorange.gooutdoor.framework.widget.dialogs.fragment.SimpleDialogFragment;

/**
 * Created by JasWorkSpace on 15/4/21.
 */
public class FinishSportsDialog{

    public final static int CLICK_ID_OK     = 1;
    public final static int CLICK_ID_CANCEL = 2;

    public final static String TAG_FRAGMENT = "FinishSportsDialog";

    public static DialogFragment show(FragmentActivity activity){
        DialogFragment dialogFragment = SimpleDialogFragment.createBuilder(activity, activity.getSupportFragmentManager())
                .setTag(TAG_FRAGMENT)
                .setRequestCode(EventID.ID_CLICK_FinishSportsDialog)
                .setCancelable(false)
                .setCancelableOnTouchOutside(false)
                .setMessage(R.string.dialog_finishsports_message)
                .setTitle(R.string.dialog_finishsports_title)
                .setNegativeButtonText(R.string.dialog_finishsports_cancel)
                .setPositiveButtonText(R.string.dialog_finishsports_ok)
                .show();
        activity.getSupportFragmentManager().executePendingTransactions();
        return dialogFragment;
    }

    public static void dispatcherClick(int id){
        if(id == CLICK_ID_CANCEL || id == CLICK_ID_OK) {
            Util.postEvent(Util.produceEventDialogFragmentCLick(EventID.ID_CLICK_FinishSportsDialog, null, id));
        }
    }
}
