package com.tibco.xpd.n2.scriptdescriptor;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.n2.scriptdescriptor.messages"; //$NON-NLS-1$

    public static String ScriptDescriptorBuilder_processArtifactCreationProblem;

    public static String ScriptDescriptorBuilder_processArtifactDeletionProblem;

    public static String ScriptDescriptorBuilder_processArtifactCleanProblem;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
