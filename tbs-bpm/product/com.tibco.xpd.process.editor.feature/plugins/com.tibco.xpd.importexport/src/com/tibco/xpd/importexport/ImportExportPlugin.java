package com.tibco.xpd.importexport;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class ImportExportPlugin extends AbstractUIPlugin {
    
    /*
     * Plugin ID
     */
    public static final String PLUGIN_ID = "com.tibco.xpd.importexport"; //$NON-NLS-1$

	//The shared instance.
	private static ImportExportPlugin plugin;
	
	//Bundle context
	private BundleContext pluginContext = null;
    
	
	/**
	 * The constructor.
	 */
	public ImportExportPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		//Store the bundle context
		pluginContext = context;
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
	public static ImportExportPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Get the plugin context
	 * @return BundleContext
	 */
	public BundleContext getPluginContext() {
		return pluginContext;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
    
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] images = ImportExportPluginConstants.IMAGES;
        
        //Register all images
        for (String img : images) {
            reg.put(img, getImageDescriptor(img));
        }
    }
}
