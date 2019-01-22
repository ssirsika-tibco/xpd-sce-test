package com.tibco.bx.emulation.bpm.core;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EmulationBPMCoreActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.emulation.bpm.core"; //$NON-NLS-1$

	public static final String BPM_MODEL_TYPE = "bpm"; //$NON-NLS-1$
	
	// The shared instance
	private static EmulationBPMCoreActivator plugin;
	private BusinessModelChangeListener businessModelChangeListener = new BusinessModelChangeListener();
	
	/**
	 * The constructor
	 */
	public EmulationBPMCoreActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(businessModelChangeListener, IResourceChangeEvent.POST_CHANGE);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(businessModelChangeListener);
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EmulationBPMCoreActivator getDefault() {
		return plugin;
	}

}
