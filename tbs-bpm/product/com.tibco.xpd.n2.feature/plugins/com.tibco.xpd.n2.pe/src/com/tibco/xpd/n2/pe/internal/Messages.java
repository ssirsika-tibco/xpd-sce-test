/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 13 Jun 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.pe.internal.messages"; //$NON-NLS-1$

    public static String ProcessDataMapperScriptFieldRefResolver_MappingScriptLabel;

    public static String ProcessDataMapperScriptFieldRefResolver_ProcessDataMapperInMappingScriptRefContext_label;

    public static String ProcessDataMapperScriptFieldRefResolver_ProcessDataMapperOutMappingScriptRefContext_label;

    public static String PEBuilder_0;

    public static String PEBuilder_peArtifactCreationProblem;

    public static String PEBuilder_peArtifactDeletionProblem;

    public static String PEBuilder_peArtifactCleanProblem;

    public static String PECompositeContributor_AddRuntimeProcesses_message;

    public static String PECompositeContributor_problemsDuringDaaGeneration;

    public static String ProcessRefExistsConstraint_cantOpenURI_message;

    public static String ProcessRefExistsConstraint_invalidRef_message;

    public static String PERascContributor_AddingRuntimeProcesses;

    public static String PERascContributor_BuildingProcesses_status;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
