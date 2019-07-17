/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author aprasad
 */
public class WorkListFacadeResourcePlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.worklistfacade.resource"; //$NON-NLS-1$

    /**
     * Image used in all Wizards and wizard pages of Work List facade
     */
    public static final String WLF_WIZARD_IMAGE =
            "icons/obj16/workListFacadeWizard.png"; //$NON-NLS-1$

    /**
     * Work List Facade File Extension
     */
    public static final String WLF_FILE_EXTENSION = "wlf"; //$NON-NLS-1$

    /**
     * Work List Facade Special Folder Kind
     */
    public static final String WLF_SPECIAL_FOLDER_KIND = "wlf"; //$NON-NLS-1$

    /**
     * Colors to be shared between Editors.
     */
    private FormColors formColors;

    /**
     * Image used in Form Header
     */
    public static final String IMG_FORM_BG = "formBg"; //$NON-NLS-1$

    /**
     * Work List Facade Format Version value.
     * 
     * <li>1000 - Studio Container Edition 5.0.0 (V95) (marks the transition
     * between BPM and SCE Studio - and leaves a gap between this and AMX BPM -
     * therefore future AMX BPM releases with incremented formatversion numbers
     * will still migrate to ACE).</i>
     * 
     */
    public static final String FORMAT_VERSION = "1000"; //$NON-NLS-1$

    // The shared instance
    private static WorkListFacadeResourcePlugin plugin;

    /**
     * The constructor
     */
    public WorkListFacadeResourcePlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
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
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance
     */
    public static WorkListFacadeResourcePlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @generated
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getBundledImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Returns an image for the image file at the given plug-in relative path.
     * Tries to retrieve the image from the {@link ImageRegistry}, if not found
     * in the registry, creates one , adds it to the registry and returns.If the
     * image could not be found at the given path , returns null.
     * 
     * @generated
     * @param path
     *            the path
     * @return {@link Image} , for the given path, null if could not be found.
     */
    public Image getImageFromRegistry(String path) {
        Image img = null;
        ImageDescriptor descriptor;
        ImageRegistry imageRegistry = getImageRegistry();
        // Check for Valid Image Registry.
        if (imageRegistry != null) {
            img = imageRegistry.get(path);
            // create and add to registry if , does not exist
            if (img == null) {
                descriptor = getBundledImageDescriptor(path);
                if (descriptor != null) {
                    img = descriptor.createImage();
                    imageRegistry.put(path, img);
                }
            }
        }

        return img;
    }

    /**
     * Logs the Error Message.
     * 
     * @generated
     */
    public void logError(String error) {
        logError(error, null);
    }

    /**
     * Logs the Error Message.
     * 
     * @generated
     */
    public void logError(Throwable throwable) {
        logError(null, throwable);
    }

    /**
     * Logs the Error Message.
     * 
     * @generated
     */
    public void logError(String error, Throwable throwable) {
        if (error == null && throwable != null) {
            error = throwable.getMessage();
        }
        getLog().log(new Status(IStatus.ERROR,
                WorkListFacadeResourcePlugin.PLUGIN_ID, IStatus.OK, error,
                throwable));
        debug(error, throwable);
    }

    /**
     * Logs the Info Message.
     * 
     * @generated
     */
    public void logInfo(String message) {
        logInfo(message, null);
    }

    /**
     * Logs the Info Message.
     * 
     * @generated
     */
    public void logInfo(String message, Throwable throwable) {
        if (message == null && throwable != null) {
            message = throwable.getMessage();
        }
        getLog().log(new Status(IStatus.INFO,
                WorkListFacadeResourcePlugin.PLUGIN_ID, IStatus.OK, message,
                throwable));
        debug(message, throwable);
    }

    /**
     * Logs the Debug Message.
     * 
     * @generated
     */
    private void debug(String message, Throwable throwable) {
        if (!isDebugging()) {
            return;
        }
        if (message != null) {
            System.err.println(message);
        }
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }

    /**
     * Returns the forms Colors, shared betwen Editors. Only one instance of
     * FomrColors is created.
     * 
     * @param display
     * @return FormColors.
     */
    public FormColors getFormColors(Display display) {
        if (formColors == null) {
            formColors = new FormColors(display);
            formColors.markShared();
        }
        return formColors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse
     * .jface.resource.ImageRegistry)
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry registry) {
        registerImage(registry, IMG_FORM_BG, "icons/form_banner.gif"); //$NON-NLS-1$
    }

    /**
     * Register the image the Image Registry
     * 
     * @param registry
     *            , {@link ImageRegistry} to which the image should be
     *            registered.
     * @param key
     *            , {@link String} to register the image with.
     * @param fileName
     *            , {@link String} Name of the image file.
     */
    private void registerImage(ImageRegistry registry, String key,
            String fileName) {

        try {
            if (registry.get(fileName) == null) {
                IPath path = new Path(fileName); //$NON-NLS-1$
                URL url = FileLocator.find(this.getBundle(), path, null);
                if (url != null) {
                    ImageDescriptor desc = ImageDescriptor.createFromURL(url);
                    registry.put(key, desc);
                }
            }
        } catch (Exception e) {
            logError(String.format("Image file {0} is not found." + fileName));
        }
    }

}
