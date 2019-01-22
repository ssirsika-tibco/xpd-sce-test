/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author nwilson
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.destinations.internal.messages"; //$NON-NLS-1$

    public static String DestinationComponentsViewer_ComponentColumnLabel;

    public static String DestinationComponentsViewer_destinationComponentsTableLabel;

    public static String DestinationComponentsViewer_notApplicableVerisonLabel;

    public static String DestinationComponentsViewer_VersionColumnLabel;

    public static String DestinationEnvironmentsViewer_destinationEnvironmentsTableLabel;

    public static String DestinationEnvironmentsViewer_EnvironmentNameColumnLabel;

    public static String DestinationEnvironmentsViewer_EnvironmentTypeColumnLabel;

    public static String DestinationEnvironmentsViewer_RemoveDestinationDialogMessage;

    public static String DestinationEnvironmentsViewer_RemoveDestinationDialogTitle;

    public static String DestinationPreferences_NewDestinationDefault;

    public static String DestinationsEditor_RebuildDialogMessage;

    public static String DestinationsEditor_RebuildDialogTitle;

    public static String DestinationsEditor_RebuildDialog_BuildWithoutAsking_Label;

    public static String DestinationsEditor_RebuildJobName;

    public static String RequiredNaturesRule_MultipleItemListFormat_message;

    public static String AddProjectDestResolution_RequiredDest_confirmation_dialog_message;

    public static String AddProjectDestResolution_RequiredDest_title;

    public static String FixProjectNaturesPostImportTask_AddMissingNatures_message;

    public static String FixProjectNaturesPostImportTask_CheckProjectNatures_message;

    public static String FixProjectNaturesPostImportTask_UpdatingnaturesSequence_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
