package com.tibco.bx.debug.bpm.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DebugBPMActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.design.debug.bpm.core"; //$NON-NLS-1$

	public static final String BPM_MODEL_TYPE = "bpm"; //$NON-NLS-1$
	public static final String PAGEFLOW_MODEL_TYPE = "Pageflow"; //$NON-NLS-1$
	// The shared instance
	private static DebugBPMActivator plugin;
	
	/**
	 * The constructor
	 */
	public DebugBPMActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DebugBPMActivator getDefault() {
		return plugin;
	}


    public static IStatus newStatus(Throwable exception, String message) {
        return new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK,  message==null?"":message, exception); //$NON-NLS-1$
    }

    public static void log(IStatus status) {
        getDefault().getLog().log(status);
    }

    public static void log(Throwable exception, String message) {
        getDefault().getLog().log(newStatus(exception, message));
    }

    public static void log(Throwable exception) {
        log(exception, exception.getMessage());
    }

}
