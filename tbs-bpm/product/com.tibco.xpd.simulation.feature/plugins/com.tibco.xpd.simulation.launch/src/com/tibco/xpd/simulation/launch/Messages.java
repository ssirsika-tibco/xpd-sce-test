/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.launch;

import org.eclipse.osgi.util.NLS;

/**
 * @author nwilson
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.simulation.launch.messages"; //$NON-NLS-1$

    public static String ExperimentRunner_Finished1;

    public static String ExperimentRunner_Finished2;

    public static String ExperimentRunner_Started1;

    public static String ExperimentRunner_Started2;

    public static String ExperimentRunner_Stop;

    public static String ReportManagerImpl_Distribution1;

    public static String ReportManagerImpl_ExperimentFor;

    public static String SimulationLaunchConfigurationDelegate_File;

    public static String SimulationLaunchConfigurationDelegate_Invalid;

    public static String SimulationLaunchConfigurationDelegate_Process;

    public static String SimulationLaunchConfigurationDelegate_Project;

    public static String ExperimentRunner_Simulation_label;

    public static String ExperimentRunner_ProcessLastName_label;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
