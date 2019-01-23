package com.tibco.bx.debug.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.osgi.framework.BundleContext;

import com.tibco.bx.debug.core.invoke.launcher.EmulationService;
import com.tibco.bx.debug.core.invoke.launcher.ILauncherService;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.runtime.internal.ConnectionFactoryManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class DebugCoreActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.design.debug.core";//$NON-NLS-1$

	public static final String BPEL_ENGINE_PROJECT = ".BPELEngine"; //$NON-NLS-1$

	public static final String BPEL_LAUNCH_CONFIGURATION_TYPE = "com.tibco.bx.debug.launchType";//$NON-NLS-1$

	public static final String ID_BX_DEBUG_MODEL = PLUGIN_ID + ".debugModel";//$NON-NLS-1$

	private final static ExecutorService threadPool = Executors.newCachedThreadPool();
	// The shared instance
	private static DebugCoreActivator plugin;

	// IResourceChangeListener listener = new XpdProjectChangeListener();

	private BxLaunchListener bxLaunchListener = new BxLaunchListener();

	/**
	 * The constructor
	 */
	public DebugCoreActivator() {
		plugin = this;
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
		support = new PropertyChangeSupport(getDefault());
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(bxLaunchListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
		for (int i = 0; i < launches.length; i++) {
			IDebugTarget target = launches[i].getDebugTarget();
			if (target instanceof IBxDebugTarget) {
				target.terminate();
			}
		}
		DebugPlugin.getDefault().getLaunchManager().removeLaunchListener(bxLaunchListener);
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static DebugCoreActivator getDefault() {
		return plugin;
	}

	public static final String getUniqueIdentifier() {
		return getDefault().getBundle().getSymbolicName();
	}

	public static IStatus newStatus(Throwable exception, String message) {
		// modify by liugang,judge message is null or not
		return new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, message == null ? "" : message, exception); //$NON-NLS-1$
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

	private static PropertyChangeSupport support = null;
	public static final String CURRENT_STACKFRAME_P = "current_stackFrame_p";//$NON-NLS-1$
	public static final String LINK_BACK_P = "link_back_p";//$NON-NLS-1$

	public static void fireCurrentStackFrameChange(String propertyName, Object oldObject, Object newObject) {
		synchronized (support) {
			support.firePropertyChange(propertyName, oldObject, newObject);
		}
	}

	public static void addCurrentStackFrameChangeListener(PropertyChangeListener listener) {
		synchronized (support) {
			support.addPropertyChangeListener(listener);
		}
	}

	public static void removeCurrentStackFrameChangeListener(PropertyChangeListener listener) {
		synchronized (support) {
			support.removePropertyChangeListener(listener);
		}
	}

	public String[] getConnectionTypes() {
		return ConnectionFactoryManager.getConnectionFactoryTypes();
	}

	public BxLaunchListener getBxLaunchListener() {
		return bxLaunchListener;
	}

	public static ExecutorService getThreadPool() {
		return threadPool;
	}

	public static ILauncherService createILaunchService() {
		if (!XpdResourcesPlugin.isInHeadlessMode()) {
			IConfigurationElement[] extensionPoint = Platform
					.getExtensionRegistry().getConfigurationElementsFor(
							"com.tibco.bx.design.debug.core.launcherService");
			try {
				for (int i = 0; i < extensionPoint.length; i++) {
					IConfigurationElement element = extensionPoint[i];
					Object serviceClass = element
							.createExecutableExtension("class");
					if (serviceClass instanceof ILauncherService) {
						return (ILauncherService) serviceClass;
					}
				}
			} catch (CoreException e) {
				log(e);
			}
		}
		return null;
	}

	public static EmulationService createEmulationService() {
		if (!XpdResourcesPlugin.isInHeadlessMode()) {
		IConfigurationElement[] extensionPoint = Platform.getExtensionRegistry().getConfigurationElementsFor(
				"com.tibco.bx.design.debug.core.emulationService");
		try {
			for (int i = 0; i < extensionPoint.length; i++) {
				IConfigurationElement element = extensionPoint[i];
				Object serviceClass = element.createExecutableExtension("class");
				if (serviceClass instanceof EmulationService) {
					return (EmulationService) serviceClass;
				}
			}
		} catch (CoreException e) {
			log(e);
		}
		}
		return null;
	}
	
}
