package com.tibco.xpd.processeditor.fragments.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.processeditor.fragments.internal.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String ProcessFragmentContributor_NewFragmentDesc_shortdesc;
    public static String ProcessFragmentDropTargetAdapter_FragmentDesc_shortdesc;
    public static String ProcessFragmentDropTargetAdapter_NewFragment_label;
    public static String ProcessFragmentsImporter_FragmentDescription_shortdesc;
}
