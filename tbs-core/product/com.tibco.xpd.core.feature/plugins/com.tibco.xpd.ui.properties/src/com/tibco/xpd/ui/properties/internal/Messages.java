package com.tibco.xpd.ui.properties.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.ui.properties.internal.messages"; //$NON-NLS-1$

    public static String AbstractPickerControl_clear_button;

    public static String AbstractPickerControl_noValueSet_label;

    public static String EmptySection_label;

    public static String PropertyLabelProvider_ReadOnly_label;

    public static String SashDividedEObjectSection_horizLayoutAction_tooltip;

    public static String SashDividedEObjectSection_vertLayoutAction_tooltip;
    
    public static String TableWithButtonsDeleteRowAction_text;
    
    public static String TableWithButtonsNewRowAction_text;
    
    public static String TableWithButtonsMoveRowUpAction_text;
    
    public static String TableWithButtonsMoveRowDownAction_text;
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
