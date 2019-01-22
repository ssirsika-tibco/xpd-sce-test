package com.tibco.xpd.xpdl.extended.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.xpdl.extended.ui.messages"; //$NON-NLS-1$

    public static String eaLabel;

    public static String add;

    public static String remove;

    public static String up;

    public static String down;

    public static String nameLabel;

    public static String valueLabel;

    public static String escapeLabel;

    public static String bodyLabel;
    
    public static String attributeName;

    static {
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

}
