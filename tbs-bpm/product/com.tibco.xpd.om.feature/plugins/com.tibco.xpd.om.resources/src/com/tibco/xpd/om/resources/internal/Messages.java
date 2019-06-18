package com.tibco.xpd.om.resources.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.om.resources.internal.messages"; //$NON-NLS-1$

    public static String OMWorkingCopy_OrgModelVersionProblem_message;

    public static String SystemActionManager_cannotFindBundle_error_message;
    public static String SystemActionManager_errorLoadingAction_message;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
