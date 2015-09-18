package com.greenorange.gooutdoor.framework.Model.Set;

import android.view.View;
import com.greenorange.gooutdoor.framework.Model.Interface.iDialogClickListener;
import com.greenorange.gooutdoor.framework.Model.ModelListener;

/**
 * Created by JasWorkSpace on 15/3/24.
 */
public class DialogClickSet extends ModelListener implements iDialogClickListener {

    private final static int FLAG_DIALOG_CLICK = 0;
    @Override
    public boolean notifyListener(int flag, Object listener, Object... objects) {
        switch(flag){
            case FLAG_DIALOG_CLICK:{
                return ((iDialogClickListener)listener).OnClick((View)objects[0],(Integer)objects[1]);
            }
        }
        return false;
    }

    @Override
    public boolean OnClick(View v, int viewId) {
        pendNotifyListener(FLAG_DIALOG_CLICK,v,viewId);
        return true;
    }
}
