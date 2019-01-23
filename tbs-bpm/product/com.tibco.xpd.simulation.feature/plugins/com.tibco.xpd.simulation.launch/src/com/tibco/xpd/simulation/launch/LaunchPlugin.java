package com.tibco.xpd.simulation.launch;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.simulation.launch.runner.SimulationControlerImpl;

/**
 * The main plugin class to be used in the desktop.
 */
public class LaunchPlugin extends Plugin {
    
    /** ID of the plugin. */
    public static final String ID = "com.tibco.xpd.simulation.launch"; //$NON-NLS-1$
    
    public static final String NUMBER_OF_ERRORS_KEY = "NumberOfErrors"; //$NON-NLS-1$
    
    public static final String WORKFLOW_PROCESS_KEY = "WorkflowProcess"; //$NON-NLS-1$
    
    public static final String LAUNCH_MODE_KEY = "LaunchMode"; //$NON-NLS-1$
    
    public static final String LAUNCH_CONFIGURATION_KEY = "LaunchConfiguration"; //$NON-NLS-1$

    public static final String PACKAGE_WORKING_COPY_KEY = "PackageWorkingCopy"; //$NON-NLS-1$

    public static final Object PROBLEM_MESSAGE_KEY = "ProblemMessage";  //$NON-NLS-1$
    
    private static SimulationControler simulationControler;

	//The shared instance.
	private static LaunchPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public LaunchPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static LaunchPlugin getDefault() {
		return plugin;
	}

    public static String getUniqueIdentifier() {
        return LaunchPlugin.ID;
    }

    public static synchronized SimulationControler getSimulationControler() {
        if (simulationControler == null) {
            simulationControler = new SimulationControlerImpl();
        }
        return simulationControler;
    }
}
