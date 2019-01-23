package com.tibco.xpd.ui.documentation.rcp;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.ui.documentation.rcp.messages"; //$NON-NLS-1$

    public static String ApplicationWorkbenchWindowAdvisor_ClosedProject;

    public static String ApplicationWorkbenchWindowAdvisor_ProjectConfigNull;

    public static String ApplicationWorkbenchWindowAdvisor_ProjectNotExist;

    public static String ApplicationWorkbenchWindowAdvisor_ProjectNull;

    public static String ApplicationWorkbenchWindowAdvisor_ResourcesNotFound;

    public static String ApplicationWorkbenchWindowAdvisor_SpecialFolderNull;

    public static String Documentation_ErrorCreatingOutputPath;

    public static String Documentation_FinishedGeneration;

    public static String Documentation_InvalidFile;

    public static String Documentation_InvalidOutputPath;

    public static String Documentation_InvalidParameters;

    public static String Documentation_InvalidProject;

    public static String Documentation_InvalidProjectNameAndOutputPath;

    public static String Documentation_OutputPath;

    public static String Documentation_ProjectName;

    public static String Documentation_WrongNumberOfParameters;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
