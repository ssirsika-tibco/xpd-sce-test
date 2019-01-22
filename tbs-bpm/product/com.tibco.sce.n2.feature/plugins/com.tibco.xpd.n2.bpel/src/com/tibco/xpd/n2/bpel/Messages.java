package com.tibco.xpd.n2.bpel;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.n2.bpel.messages"; //$NON-NLS-1$

    public static String BPELBuilder_processArtifactCreationProblem;

    public static String BPELBuilder_processArtifactDeletionProblem;

    public static String BPELBuilder_processArtifactCleanProblem;

    public static String BPELOnDemandBuilder_BPELConversionException_message;

    public static String BPELOnDemandBuilder_CantBuildBpelBecauseOfProblemMarkers_message;

    public static String BPELOnDemandBuilder_CouldNotAccessPackage_message;

    public static String BPELOnDemandBuilder_CouldNotAccessWorkingCopy_message;

    public static String BPELOnDemandBuilder_CreatingRuntimeProcesses_message;

    public static String BPELOnDemandBuilder_ErrorRemoveTargetFolder_message;

    public static String BPELOnDemandBuilder_ErrorEmptyBPELFile_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
