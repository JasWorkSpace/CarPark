package com.greenorange.gooutdoor.framework.Model.Interface;

/**
 * Created by JasWorkSpace on 15/8/24.
 */
public interface iWindowStateChangeLinstener {

    public boolean onWindowVisibilityChanged(int visibility);

    public boolean onAttachedToWindow();

    public boolean onDetachedFromWindow();
}
