package com.greenorange.gooutdoor.framework.widget.dialogs.iface;

/**
 * Implement this interface in Activity or Fragment to react to negative dialog buttons.
 *
 * @author Tomáš Kypta
 * @since 2.1.0
 */
public interface INegativeButtonDialogListener {

    public void onNegativeButtonClicked(int requestCode);
}
