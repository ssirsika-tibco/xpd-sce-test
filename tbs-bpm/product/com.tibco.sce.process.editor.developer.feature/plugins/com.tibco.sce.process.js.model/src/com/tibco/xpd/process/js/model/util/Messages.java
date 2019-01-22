package com.tibco.xpd.process.js.model.util;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.process.js.model.util.messages"; //$NON-NLS-1$

    public static String ProcessData;
    
    public static String ProcessAdditionalInfo_processData_template;
    
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
