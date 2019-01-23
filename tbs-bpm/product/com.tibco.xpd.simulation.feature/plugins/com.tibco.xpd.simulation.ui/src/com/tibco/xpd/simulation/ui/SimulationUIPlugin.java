package com.tibco.xpd.simulation.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class SimulationUIPlugin extends AbstractUIPlugin {

    /** ID of the plugin. */
    public static final String ID = "com.tibco.xpd.simulation.ui"; //$NON-NLS-1$
    
	//The shared instance.
	private static SimulationUIPlugin plugin;
	
	/**
	 * The constructor.
	 */
	public SimulationUIPlugin() {
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
	public static SimulationUIPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.tibco.xpd.simulation.ui", path); //$NON-NLS-1$
	}
    

    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = SimulationUIConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        };
    }
    
    /**
     * Returns the currently active workbench window or <code>null</code>
     * if none.
     * 
     * @return the currently active workbench window or <code>null</code>
     */
    public static IWorkbenchWindow getActiveWorkbenchWindow() {
        return getDefault().getWorkbench().getActiveWorkbenchWindow();
    }
    
    /**
     * Returns the currently active workbench window shell or <code>null</code>
     * if none.
     * 
     * @return the currently active workbench window shell or <code>null</code>
     */
    public static Shell getShell() {
        if (getActiveWorkbenchWindow() != null) {
            return getActiveWorkbenchWindow().getShell();
        }
        return null;
    }
    
    /**
     * Returns the standard display to be used. The method first checks, if
     * the thread calling this method has an associated display. If so, this
     * display is returned. Otherwise the method returns the default display.
     */
    public static Display getStandardDisplay() {
        Display display= Display.getCurrent();
        if (display == null) {
            display= Display.getDefault();
        }
        return display;     
    }   
}
