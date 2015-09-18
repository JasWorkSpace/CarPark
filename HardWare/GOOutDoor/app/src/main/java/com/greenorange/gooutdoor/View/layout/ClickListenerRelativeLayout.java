package com.greenorange.gooutdoor.View.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.greenorange.gooutdoor.framework.Model.Interface.iListener;
import com.greenorange.gooutdoor.framework.Model.Set.DialogClickSet;

/**
 * Created by JasWorkSpace on 15/4/14.
 */
public class ClickListenerRelativeLayout extends RelativeLayout implements iListener {

    public DialogClickSet mDialogClickSet = new DialogClickSet();

    public ClickListenerRelativeLayout(Context context) {
        super(context);
    }

    public ClickListenerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickListenerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean addListener(Object listener) {
        return mDialogClickSet.addListener(listener);
    }

    @Override
    public boolean removeListener(Object listener) {
        return mDialogClickSet.removeListener(listener);
    }
}
