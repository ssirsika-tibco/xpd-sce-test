package com.tibco.xpd.n2.pe;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.n2.pe.messages"; //$NON-NLS-1$

    public static String PEBuilder_0;

    public static String PEBuilder_peArtifactCreationProblem;

    public static String PEBuilder_peArtifactDeletionProblem;

    public static String PEBuilder_peArtifactCleanProblem;

    public static String PECompositeContributor_AddRuntimeProcesses_message;

    public static String PECompositeContributor_problemsDuringDaaGeneration;

    public static String ProcessRefExistsConstraint_cantOpenURI_message;

    public static String ProcessRefExistsConstraint_invalidRef_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
