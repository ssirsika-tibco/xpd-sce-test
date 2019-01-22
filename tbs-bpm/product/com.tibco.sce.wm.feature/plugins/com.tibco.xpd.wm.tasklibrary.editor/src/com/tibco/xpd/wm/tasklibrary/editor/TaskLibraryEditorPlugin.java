package com.tibco.xpd.wm.tasklibrary.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.wm.resources.ui.WMResourcesUIActivator;

/**
 * The activator class controls the Task Library plug-in life cycle
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryEditorPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.wm.tasklibrary.editor"; //$NON-NLS-1$

    private Logger logger;

    /**
     * extension of the xpdl file.
     */
    public static final String TASKLIBRARY_FILE_EXTENSION = "tasks";//$NON-NLS-1$

    /**
     * For now, the assumption is that the task library will go in the workforce
     * management special folder.
     * <p>
     * But we'll declare our own just in case that changes later.
     */
    public static final String TASK_LIBRARY_SPECIAL_FOLDER_KIND =
            WMResourcesUIActivator.WM_SPECIAL_FOLDER_KIND;

    // The shared instance
    private static TaskLibraryEditorPlugin plugin;

    /**
     * The constructor
     */
    public TaskLibraryEditorPlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
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
    public static TaskLibraryEditorPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    public synchronized Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     */
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] images = TaskLibraryEditorContstants.IMAGES;

        for (int x = 0; x < images.length; x++) {
            reg.put(images[x], getImageDescriptor(images[x]));
        }
    }
}
