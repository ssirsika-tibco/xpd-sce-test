/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.util;

/**
 * Commonly used constants.
 */
public class XpdConsts {

    /* Sid ACE-444 The Global Destination name for the ACE runtime. */
    public static final String ACE_DESTINATION_NAME = "CE"; //$NON-NLS-1$

    /** TODO: move to analyst?. */
    public static final String ANALYST_CAPABILITY = //
            "com.tibco.xpd.analyst.resources.xpdl2.activity.process.analyst"; //$NON-NLS-1$

    /** TODO: move to analyst?. */
    public static final String PROCESS_INTERNAL_CAPABILITY = //
            "com.tibco.xpd.analyst.resources.xpdl2.activity.process.internal"; //$NON-NLS-1$

    /**
     * XPD Project nature ID.
     */
    public static final String PROJECT_NATURE_ID =
            "com.tibco.xpd.resources.xpdNature"; //$NON-NLS-1$

    /** The base validation marker type. */
    public static final String VALIDATION_MARKER_TYPE =
            "com.tibco.xpd.validation.ValidationMarker"; //$NON-NLS-1$

    /**
     * Validation marker type indicating project migration issue.
     * 
     * @since 3.5
     */
    public static final String PROJECT_MIGRATION_MARKER_TYPE =
            "com.tibco.xpd.validation.migrateProjectMarker"; //$NON-NLS-1$

    /**
     * Validation marker type indicating project is from newer version of studio
     * 
     * @since 3.5
     */
    public static final String PROJECT_FROM_NEWER_STUDIO_MARKER_TYPE =
            "com.tibco.xpd.validation.projectFromNewerStudioMarker"; //$NON-NLS-1$

    /**
     * Sid ACE-444 - Validation marker type indicating project migration issue.
     */
    public static final String PROJECT_NOT_ACE_DESTINATION_MARKER_TYPE =
            "com.tibco.xpd.validation.notAceDestinationProjectMarker"; //$NON-NLS-1$

    public static final String PROJECT_NOT_ACE_DESTINATION_ISSUE_ID =
            "notAceDestinationProject.issue"; //$NON-NLS-1$

    /** additional Attribute on the validation Marker */
    public static final String VALIDATION_MARKER_ADDITIONAL_INFO =
            "com.tibco.xpd.validation.additionalInfo"; //$NON-NLS-1$

    /** Additional information key used to store path to the broken reference */
    public static final String VALIDATION_BROKEN_REF_INFO_KEY =
            "BrokenReference"; //$NON-NLS-1$

    /** ID of the InvalidFileMarker (MarkerType). */
    public static final String INVALID_FILE_MARKER = //
            "com.tibco.xpd.resources.invalidFileProblem"; //$NON-NLS-1$

    /** ID of the DependecyCacheMarker (MarkerType). */
    public static final String WORKING_COPY_CACHE_MARKER = //
            "com.tibco.xpd.resources.workingCopyCache"; //$NON-NLS-1$

    /** ID of the attribute with list of resources on DependencyCacheMarker. */
    public static final String DEPENDENCY_CACHE_LIST_ATT = //
            "com.tibco.xpd.resources.dependecyListChache"; //$NON-NLS-1$

    /** ID of the attribute with map of custom attributes. */
    public static final String DEPENDENCY_CACHE_ATT = //
            "com.tibco.xpd.resources.customAttributes"; //$NON-NLS-1$

    /** Name of the attribute of InvalidFileMarker that holds project name. */
    public static final String MARKER_ATT_PROJECTNAME = //
            "com.tibco.xpd.resources.ProjectName"; //$NON-NLS-1$

    /**
     * Shared transactional editing domain id
     * 
     * @since 3.0
     */
    public static final String EDITING_DOMAIN_ID =
            "com.tibco.xpd.resources.editingDomain"; //$NON-NLS-1$

    /**
     * Don't ask to set project references preference key.
     */
    public static final String PREF_DONT_ASK_AGAIN_FOR_PROJECT_REFERENCE =
            "dont_ask_project_reference_always"; //$NON-NLS-1$
    
    /**
     * It is the id of the browser instance that can be shared by xpd components that want to use the same instance.
     * @see org.eclipse.ui.browser.IWorkbenchBrowserSupport#createBrowser(String)
     */
    public static String XPD_SHARRED_BROWSER_ID = "xpdSharedBrowserId"; //$NON-NLS-1$

}
