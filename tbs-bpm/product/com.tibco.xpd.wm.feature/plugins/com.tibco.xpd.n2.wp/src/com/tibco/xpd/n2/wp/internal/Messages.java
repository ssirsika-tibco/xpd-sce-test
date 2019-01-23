/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.wp.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author Jan Arciuchiewicz
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.wp.internal.messages"; //$NON-NLS-1$

    public static String WPComponentImplConstraint_descriptorNotPresent_message;

    public static String WPComponentImplConstraint_loadingProblem_message;

    public static String WPComponentImplConstraint_versionNeeded_message;

    public static String WPDescriptorRefConstraint_missingReferencedResource;

    public static String WPGenerator_FormNoExist_message;

    public static String WPGenerator_GenWPArtifacts_message;

    public static String WPGenerator_InvalidFormRef_message;

    public static String WPGenerator_PageFlowFromOtherProject_message;

    public static String WPGenerator_ProblemsWithWP_message2;

    public static String WPModelConstraint_descriptorNotProvided_message;

    public static String WPModelConstraint_validationProblem_message;

    public static String WPModelConstraint_versionNeeded_message;

    public static String WPModelConstraint_wpDescLoadingProblem_message2;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
