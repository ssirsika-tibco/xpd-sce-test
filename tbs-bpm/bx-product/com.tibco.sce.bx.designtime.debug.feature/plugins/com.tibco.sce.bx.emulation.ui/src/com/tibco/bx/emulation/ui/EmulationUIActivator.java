package com.tibco.bx.emulation.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class EmulationUIActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.emulation.ui"; //$NON-NLS-1$

	// The shared instance
	private static EmulationUIActivator plugin;
	
	private final static String ICONS_PATH = "icons/";//$NON-NLS-1$
    public final static String IMG_EMULATION = "Emulation File 16 n p.png";//$NON-NLS-1$
    public final static String IMG_TESTPOINT = "testpoint.gif";//$NON-NLS-1$
    public final static String IMG_ASSERTION_DIS = "assertion_dis.png";//$NON-NLS-1$
    public final static String IMG_ASSERTION_EN = "assertion_en.png";//$NON-NLS-1$
    public final static String IMG_PROCESSNODE = "processnode.gif";//$NON-NLS-1$
    public final static String IMG_EM_FOLDER = "Emulation Folder 16 n p.png";//$NON-NLS-1$
    public final static String IMG_EM_ADD_EN = "add_en.gif";//$NON-NLS-1$
    public final static String IMG_INPUT = "input.gif";//$NON-NLS-1$
    public final static String IMG_INTERMEDIATEINPUT = "IntermediateInput.gif";//$NON-NLS-1$
    public final static String IMG_OUTPUT = "output.gif";//$NON-NLS-1$
    public final static String DEFAULT_SPECICAL_FOLDER = "Emulations";//$NON-NLS-1$
	/**
	 * The constructor
	 */
	public EmulationUIActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		//annotationFactory4Debugging = new EMAnnotationFactory4Debugging();
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
	public static EmulationUIActivator getDefault() {
		return plugin;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		declareImage(IMG_EMULATION, ICONS_PATH + IMG_EMULATION);
		declareImage(IMG_EM_FOLDER, ICONS_PATH + IMG_EM_FOLDER);
		declareImage(IMG_TESTPOINT, ICONS_PATH + IMG_TESTPOINT);
		declareImage(IMG_PROCESSNODE, ICONS_PATH + IMG_PROCESSNODE);
		declareImage(IMG_ASSERTION_DIS, ICONS_PATH + IMG_ASSERTION_DIS);
		declareImage(IMG_ASSERTION_EN, ICONS_PATH + IMG_ASSERTION_EN);
		declareImage(IMG_EM_ADD_EN, ICONS_PATH + IMG_EM_ADD_EN);
		declareImage(IMG_INPUT, ICONS_PATH + IMG_INPUT);
		declareImage(IMG_OUTPUT, ICONS_PATH + IMG_OUTPUT);
		declareImage(IMG_INTERMEDIATEINPUT, ICONS_PATH + IMG_INTERMEDIATEINPUT);
	}
	
	private void declareImage(String key, String path) {
        ImageDescriptor desc = imageDescriptorFromPlugin(PLUGIN_ID, path);
        getImageRegistry().put(key, desc);
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

    public static IWorkbenchWindow getActiveWorkbenchWindow() {
    	 return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    }
    
}
