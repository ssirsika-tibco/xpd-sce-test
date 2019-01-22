/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author aallway
 * @since 2 Dec 2010
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.wm.tasklibrary.editor.compare.messages"; //$NON-NLS-1$

    public static String TaskLibraryCompareStructureCreator_TaskLibraryCompare_title;

    public static String TaskLibraryPackageCompareNode_DataFields_label;

    public static String TaskLibraryPackageCompareNode_Participants_label;

    public static String TaskLibraryPackageCompareNode_TaskSets_label;

    public static String TaskSetCompareNode_Activities_label;

    public static String TaskSetCompareNode_Artifacts_label;

    public static String TaskSetCompareNode_Tasks_label;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
