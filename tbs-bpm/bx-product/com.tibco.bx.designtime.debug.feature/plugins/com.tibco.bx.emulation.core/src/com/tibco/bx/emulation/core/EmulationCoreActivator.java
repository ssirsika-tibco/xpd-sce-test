package com.tibco.bx.emulation.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.bx.emulation.core.service.IBxTestDelegateService;


/**
 * The activator class controls the plug-in life cycle
 */
public class EmulationCoreActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.emulation.core";//$NON-NLS-1$

	public static final String EMULATION_FILE_EXTENSION = "em";//$NON-NLS-1$
	public static final String EMULATION_SPECIAL_FOLDER_KIND = "em";//$NON-NLS-1$
	// The shared instance
	private static EmulationCoreActivator plugin;
	/**
	 * The constructor
	 */
	public EmulationCoreActivator() {
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
	public static EmulationCoreActivator getDefault() {
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
	
	public static IBxTestDelegateService createTestDelegateService(){
		IConfigurationElement[] extensionPoint = Platform.getExtensionRegistry().getConfigurationElementsFor("com.tibco.bx.emulation.core.bxTestDelegateService");
		try {
		for(int i=0;i<extensionPoint.length;i++){
			IConfigurationElement element = extensionPoint[i];
			Object serviceClass = element.createExecutableExtension("class");
			if(serviceClass instanceof IBxTestDelegateService){
				return (IBxTestDelegateService) serviceClass;
			}
		}
		} catch (CoreException e) {
		}
		return null;
	}
}
