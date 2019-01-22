package com.tibco.bx.debug.ui;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.bx.debug.ui.views.internal.TargetManager;
/**
 * The activator class controls the plug-in life cycle
 */
public class DebugUIActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.design.debug.ui"; //$NON-NLS-1$
	
	private final static String EXTENSION_POINT_MODEL_TYPE_IMAGES = "modelTypeImages";//$NON-NLS-1$
	private final static String ATTR_MODEL_TYPE_ICON = "icon";//$NON-NLS-1$
	private final static String ATTR_MODEL_TYPE_ID = "id";//$NON-NLS-1$
    private final static String ICONS_PATH = "icons/";//$NON-NLS-1$
    public final static String IMG_SERVER = "server.png";//$NON-NLS-1$
    public final static String IMG_VARIABLE_GLOBAL = "variable_global.gif";//$NON-NLS-1$
    public final static String IMG_VARIABLE_LOCAL = "variable_local.gif";//$NON-NLS-1$
    public final static String IMG_VARIABLE_SUB = "variable_sub.gif";//$NON-NLS-1$
    public final static String IMG_VARIABLE_SUB_COMPLEX = "variable_sub_complex.gif";//$NON-NLS-1$
    
    public final static String IMG_LAUNCHER_SOAP_VIEW="soap_view.gif"; //$NON-NLS-1$
    public final static String IMG_LAUNCHER_PARAM_VIEW="properties_view.gif"; //$NON-NLS-1$
    
    public final static String IMG_RUN_EN="run_en.gif"; //$NON-NLS-1$
    public final static String IMG_RUN_DIS="run_dis.gif"; //$NON-NLS-1$
    public final static String IMG_REFRESH="refresh.gif"; //$NON-NLS-1$
    public final static String IMG_SELECTED="selected.gif"; //$NON-NLS-1$
    public final static String IMG_DESELECTED="deselected.gif"; //$NON-NLS-1$
    public final static String IMG_RECREATE="recreate.gif"; //$NON-NLS-1$
    
    public final static String IMG_EMULATION_COMPLETED = "emulation/testok.gif"; //$NON-NLS-1$
    public final static String IMG_EMULATION_TERMINATED = "emulation/testfail.gif"; //$NON-NLS-1$
    public final static String IMG_EMULATION_FAULT ="emulation/testerr.gif"; //$NON-NLS-1$
    public final static String IMG_EMULATION_NODE="emulation/test.gif";   //$NON-NLS-1$
    // The shared instance
	private static DebugUIActivator plugin;
	
	/**
	 * The constructor
	 */
	public DebugUIActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		DebugUITools.getDebugContextManager().addDebugContextListener(TargetManager.getDefault());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		DebugUITools.getDebugContextManager().removeDebugContextListener(TargetManager.getDefault());
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DebugUIActivator getDefault() {
		return plugin;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		IExtensionPoint extensionPoint= Platform.getExtensionRegistry().getExtensionPoint(PLUGIN_ID, EXTENSION_POINT_MODEL_TYPE_IMAGES);
		IConfigurationElement[] configElements= extensionPoint.getConfigurationElements();
		for (int i = 0; i < configElements.length; i++) {
			IConfigurationElement configElement = configElements[i];
			String typeId = configElement.getAttribute(ATTR_MODEL_TYPE_ID);
			ImageDescriptor desc = DebugUIPlugin.getImageDescriptor(configElement, ATTR_MODEL_TYPE_ICON);
			reg.put(typeId, desc);
		}
		ImageDescriptor desc = imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + IMG_SERVER);
		reg.put(IMG_SERVER, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + IMG_VARIABLE_GLOBAL);
		reg.put(IMG_VARIABLE_GLOBAL, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + IMG_VARIABLE_LOCAL);
		reg.put(IMG_VARIABLE_LOCAL, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + IMG_VARIABLE_SUB);
		reg.put(IMG_VARIABLE_SUB, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + IMG_VARIABLE_SUB_COMPLEX);
		reg.put(IMG_VARIABLE_SUB_COMPLEX, desc);
		
		desc = imageDescriptorFromPlugin(PLUGIN_ID , ICONS_PATH + IMG_LAUNCHER_SOAP_VIEW);
		reg.put(IMG_LAUNCHER_SOAP_VIEW, desc);
		
		desc = imageDescriptorFromPlugin(PLUGIN_ID , ICONS_PATH + IMG_LAUNCHER_PARAM_VIEW);
		reg.put(IMG_LAUNCHER_PARAM_VIEW, desc);
		
		desc = imageDescriptorFromPlugin(PLUGIN_ID , ICONS_PATH + IMG_RUN_EN);
		reg.put(IMG_RUN_EN, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID , ICONS_PATH + IMG_RUN_DIS);
		reg.put(IMG_RUN_DIS, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID , ICONS_PATH + IMG_REFRESH);
		reg.put(IMG_REFRESH, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID , ICONS_PATH + IMG_SELECTED);
		reg.put(IMG_SELECTED, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID , ICONS_PATH + IMG_DESELECTED);
		reg.put(IMG_DESELECTED, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID , ICONS_PATH + IMG_RECREATE);
		reg.put(IMG_RECREATE, desc);
		
		desc = imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + IMG_EMULATION_COMPLETED);
		reg.put(IMG_EMULATION_COMPLETED, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + IMG_EMULATION_FAULT);
		reg.put(IMG_EMULATION_FAULT, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + IMG_EMULATION_TERMINATED);
		reg.put(IMG_EMULATION_TERMINATED, desc);
		desc = imageDescriptorFromPlugin(PLUGIN_ID,ICONS_PATH + IMG_EMULATION_NODE);
		reg.put(IMG_EMULATION_NODE, desc);
	}
	
	public static Image getRegisteredImage(String key){
    	return getDefault().getImageRegistry().get(key);
    }
    
    public static ImageDescriptor getRegisteredImageDescriptor(String key){
    	return getDefault().getImageRegistry().getDescriptor(key);
    }
    
    public static IWorkbenchWindow getActiveWorkbenchWindow() {
   	 	return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
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
