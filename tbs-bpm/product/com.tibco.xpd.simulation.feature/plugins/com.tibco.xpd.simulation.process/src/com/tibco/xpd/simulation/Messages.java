/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation;

import org.eclipse.osgi.util.NLS;

/**
 * @author nwilson
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.simulation.messages"; //$NON-NLS-1$
    public static String ProcessManagerImpl_GenerateCommand;
    public static String ProcessManagerImpl_GenerateCommandDesc;
    public static String TaskActivityThreadQueue_Queue;
    public static String WorkflowModel_CaseGeneration;
    public static String WorkflowModel_Description;
    public static String WorkflowModel_Idle;
    public static String WorkflowModel_NonWorking;
    public static String WorkflowModel_Normal;
    public static String WorkflowModel_StartedCases_label;
    public static String WorkflowModel_FinishedCases_label;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
