package com.tibco.bx.emulation.bpm.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.bx.emulation.bpm.ui.annotations.EMAnnotationFactory4Editing;

/**
 * The activator class controls the plug-in life cycle
 */
public class EmulationBPMUIActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.emulation.bpm.ui";

	// The shared instance
	private static EmulationBPMUIActivator plugin;
	
    private EMAnnotationFactory4Editing annotationFactory4Editing;
	/**
	 * The constructor
	 */
	public EmulationBPMUIActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
	public static EmulationBPMUIActivator getDefault() {
		return plugin;
	}


    
    /**
     * @return The emulation annotation factory for editing.
     */
    public EMAnnotationFactory4Editing getAnnotationFactoryEdit4Editing() {
		//if (annotationFactory4Editing == null) {
			annotationFactory4Editing = new EMAnnotationFactory4Editing();
		//}
		return annotationFactory4Editing;
    }
}
