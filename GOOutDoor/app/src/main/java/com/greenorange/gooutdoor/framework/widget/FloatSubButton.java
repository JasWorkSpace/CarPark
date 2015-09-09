package com.greenorange.gooutdoor.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by JasWorkSpace on 15/4/13.
 */
public class FloatSubButton extends ImageView{
    private int FloatSubButtonID ;
    public FloatSubButton(Context context) {
        super(context);
    }
    public FloatSubButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FloatSubButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public int getFloatSubButtonID() {
        return FloatSubButtonID;
    }
    public void setFloatSubButtonID(int ID) {
        this.FloatSubButtonID = ID;
    }
}
