/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.viewer;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 3 Nov 2010
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.resources.ui.compare.viewer.messages"; //$NON-NLS-1$

    public static String EObjectInternalPropertiesNode_InternalProps_label;

    public static String MergeViewerCrossoverControl_AddedLeft_tooltip;

    public static String MergeViewerCrossoverControl_AddedRight_tooltip;

    public static String MergeViewerCrossoverControl_ChangedLeft_tooltip;

    public static String MergeViewerCrossoverControl_ChangedRight_tooltip;

    public static String MergeViewerCrossoverControl_ConflictingChange_tooltip;

    public static String MergeViewerCrossoverControl_RemovedLeft_tooltip;

    public static String MergeViewerCrossoverControl_RemovedRight_tooltip;

    public static String MergeViewerCrossoverControl_TwoWayAdded_tooltip;

    public static String MergeViewerCrossoverControl_TwoWayChanged_tooltip;

    public static String MergeViewerCrossoverControl_TwoWayRemoved_tooltip;

    public static String WorkingCopyCompareNode_CannotLoadFromPreviousVersion_message;

    public static String WorkingCopyCompareNode_FailedToLoadContent_message;

    public static String XpdCompareNodeContentMergeViewer_Merge_label;

    public static String XpdCompareNodeContentMergeViewer_NoElement_label;

    public static String XpdCompareNodeContentMergeViewer_ShowInternalProperties_button;

    public static String XpdCompareNodeContentMergeViewer_ShowInternalProperties_tooltip;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
