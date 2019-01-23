package com.tibco.bx.debug.bpm.ui;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.bx.debug.bpm.ui.annotations.BxDiagramAnnotationFactory;
import com.tibco.bx.debug.bpm.ui.annotations.EMAnnotationFactory4Debugging;
import com.tibco.bx.debug.bpm.ui.annotations.XpdlFileChangeListener;

/**
 * The activator class controls the plug-in life cycle
 */
public class DebugBPMUIActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.bx.design.debug.bpm.ui";//$NON-NLS-1$

	// The shared instance
	private static DebugBPMUIActivator plugin;

	private final static String ICONS_PATH = "icons/";//$NON-NLS-1$
	/**
	 * BPEL file image
	 */
	public final static String IMG_BPM_PROCESS = "bpm_type.png";//$NON-NLS-1$

	public final static String IMG_ERRORVARIABLE = "errorValue.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT = "breakpoint.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_SKIP = "breakpoint_skip.gif";//$NON-NLS-1$

	public final static String IMG_HIGHLIGHT = "iconPointer.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_DISABLED = "breakpoint_disabled.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_DISABLED_SKIP = "breakpoint_disabled_skip.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_AFTER = "breakpoint_after.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_AFTER_DISABLED = "breakpoint_after_disabled.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_BEFORE = "breakpoint_before.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_BEFORE_DISABLED = "breakpoint_before_disabled.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_ALL = "breakpoint_all.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_ALL_DISABLED = "breakpoint_all_disabled.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_ACTION_ADD = "breakpoint_action_add.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_ACTION_REMOVE = "breakpoint_action_remove.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_ACTION_PROP = "breakpoint_action_prop.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_ACTION_ENABLED = "breakpoint_action_enabled.gif";//$NON-NLS-1$

	public final static String IMG_BREAKPOINT_ACTION_DISABLED = "breakpoint_action_disabled.gif";//$NON-NLS-1$

	public final static String IMG_ENGINE = "engine.gif";//$NON-NLS-1$

	public final static String IMG_ERROR = "error.gif";//$NON-NLS-1$

	public final static String IMG_EVENT_BPM = "event_start_16.png";//$NON-NLS-1$
	public final static String IMG_EVENT_PAGEFLOW = "event_start_16_pageflow.png";//$NON-NLS-1$
	public final static String IMG_EVENT_GRAY = "event_start_16_gray.png";//$NON-NLS-1$
	public final static String IMG_EVENT_RED = "event_start_16_red.png";//$NON-NLS-1$
	public final static String IMG_EVENT_GREEN = "event_start_16_green.png";//$NON-NLS-1$
	public final static String IMG_EVENT_BLUE = "event_start_16_blue.png";//$NON-NLS-1$

	public final static String IMG_TASK_BPM = "task_16.png";//$NON-NLS-1$
	public final static String IMG_TASK_PAGEFLOW = "task_16_pageflow.png";//$NON-NLS-1$
	public final static String IMG_TASK_GRAY = "task_16_gray.png";//$NON-NLS-1$
	public final static String IMG_TASK_RED = "task_16_red.png";//$NON-NLS-1$
	public final static String IMG_TASK_GREEN = "task_16_green.png";//$NON-NLS-1$
	public final static String IMG_TASK_BLUE = "task_16_blue.png";//$NON-NLS-1$

	public final static String IMG_SUBPROCESS_BPM = "subProcess_16.png";//$NON-NLS-1$
	public final static String IMG_SUBPROCESS_PAGEFLOW = "subProcess_16_pageflow.png";//$NON-NLS-1$
	public final static String IMG_SUBPROCESS_GRAY = "subProcess_16_gray.png";//$NON-NLS-1$
	public final static String IMG_SUBPROCESS_RED = "subProcess_16_red.png";//$NON-NLS-1$
	public final static String IMG_SUBPROCESS_GREEN = "subProcess_16_green.png";//$NON-NLS-1$
	public final static String IMG_SUBPROCESS_BLUE = "subProcess_16_blue.png";//$NON-NLS-1$

	public final static String IMG_INDEPENDENT_BPM = "independent_16.png";//$NON-NLS-1$
	public final static String IMG_INDEPENDENT_PAGEFLOW = "independent_16_pageflow.png";//$NON-NLS-1$
	public final static String IMG_INDEPENDENT_GRAY = "independent_16_gray.png";//$NON-NLS-1$
	public final static String IMG_INDEPENDENT_RED = "independent_16_red.png";//$NON-NLS-1$
	public final static String IMG_INDEPENDENT_GREEN = "independent_16_green.png";//$NON-NLS-1$
	public final static String IMG_INDEPENDENT_BLUE = "independent_16_blue.png";//$NON-NLS-1$

	public final static String IMG_TRACK = "track_16.png";//$NON-NLS-1$

	public final static String IMG_GATEWAY_BPM = "gateway_generic_16.png";//$NON-NLS-1$
	public final static String IMG_GATEWAY_PAGEFLOW = "gateway_generic_16_pageflow.png";//$NON-NLS-1$

	public final static String IMG_REPORT = "report.gif";//$NON-NLS-1$
	
	public final static String IMG_PAGEFLOW_PROCESS = "pageflow_type.png";//$NON-NLS-1$

	public final static String IMG_PAGEFLOW_BREAKPOINT_AFTER = "pageflow_breakpoint_after.gif";//$NON-NLS-1$

	public final static String IMG_PAGEFLOW_BREAKPOINT_AFTER_DISABLED = "pageflow_breakpoint_after_disabled.gif";//$NON-NLS-1$

	public final static String IMG_PAGEFLOW_BREAKPOINT_BEFORE = "pageflow_breakpoint_before.gif";//$NON-NLS-1$

	public final static String IMG_PAGEFLOW_BREAKPOINT_BEFORE_DISABLED = "pageflow_breakpoint_before_disabled.gif";//$NON-NLS-1$

	public final static String IMG_PAGEFLOW_BREAKPOINT_ALL = "pageflow_breakpoint_all.gif";//$NON-NLS-1$

	public final static String IMG_PAGEFLOW_BREAKPOINT_ALL_DISABLED = "pageflow_breakpoint_all_disabled.gif";//$NON-NLS-1$

	private BxDiagramAnnotationFactory annotationFactory;
	private EMAnnotationFactory4Debugging annotationFactory4Debugging;

	IResourceChangeListener listener = new XpdlFileChangeListener();

	/**
	 * The constructor
	 */
	public DebugBPMUIActivator() {
		plugin = this;
		annotationFactory = new BxDiagramAnnotationFactory();
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
		annotationFactory4Debugging = new EMAnnotationFactory4Debugging();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		annotationFactory.dispose();
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
		plugin = null;
		super.stop(context);
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		declareImage(IMG_BPM_PROCESS, ICONS_PATH + IMG_BPM_PROCESS);
		declareImage(IMG_ENGINE, ICONS_PATH + IMG_ENGINE);
		declareImage(IMG_BREAKPOINT, ICONS_PATH + IMG_BREAKPOINT);
		declareImage(IMG_BREAKPOINT_SKIP, ICONS_PATH + IMG_BREAKPOINT_SKIP);
		declareImage(IMG_HIGHLIGHT, ICONS_PATH + IMG_HIGHLIGHT);
		declareImage(IMG_BREAKPOINT_DISABLED, ICONS_PATH + IMG_BREAKPOINT_DISABLED);
		declareImage(IMG_BREAKPOINT_DISABLED_SKIP, ICONS_PATH + IMG_BREAKPOINT_DISABLED_SKIP);
		declareImage(IMG_BREAKPOINT_AFTER, ICONS_PATH + IMG_BREAKPOINT_AFTER);
		declareImage(IMG_BREAKPOINT_AFTER_DISABLED, ICONS_PATH + IMG_BREAKPOINT_AFTER_DISABLED);
		declareImage(IMG_BREAKPOINT_BEFORE, ICONS_PATH + IMG_BREAKPOINT_BEFORE);
		declareImage(IMG_BREAKPOINT_BEFORE_DISABLED, ICONS_PATH + IMG_BREAKPOINT_BEFORE_DISABLED);
		declareImage(IMG_BREAKPOINT_ALL, ICONS_PATH + IMG_BREAKPOINT_ALL);
		declareImage(IMG_BREAKPOINT_ALL_DISABLED, ICONS_PATH + IMG_BREAKPOINT_ALL_DISABLED);
		declareImage(IMG_BREAKPOINT_ACTION_ADD, ICONS_PATH + IMG_BREAKPOINT_ACTION_ADD);
		declareImage(IMG_BREAKPOINT_ACTION_REMOVE, ICONS_PATH + IMG_BREAKPOINT_ACTION_REMOVE);
		declareImage(IMG_BREAKPOINT_ACTION_PROP, ICONS_PATH + IMG_BREAKPOINT_ACTION_PROP);
		declareImage(IMG_BREAKPOINT_ACTION_ENABLED, ICONS_PATH + IMG_BREAKPOINT_ACTION_ENABLED);
		declareImage(IMG_BREAKPOINT_ACTION_DISABLED, ICONS_PATH + IMG_BREAKPOINT_ACTION_DISABLED);

		declareImage(IMG_ERRORVARIABLE, ICONS_PATH + IMG_ERRORVARIABLE);
		declareImage(IMG_ERROR, ICONS_PATH + IMG_ERROR);

		declareImage(IMG_EVENT_BPM, ICONS_PATH + IMG_EVENT_BPM);
		declareImage(IMG_EVENT_PAGEFLOW, ICONS_PATH + IMG_EVENT_PAGEFLOW);
		declareImage(IMG_EVENT_BLUE, ICONS_PATH + IMG_EVENT_BLUE);
		declareImage(IMG_EVENT_GRAY, ICONS_PATH + IMG_EVENT_GRAY);
		declareImage(IMG_EVENT_GREEN, ICONS_PATH + IMG_EVENT_GREEN);
		declareImage(IMG_EVENT_RED, ICONS_PATH + IMG_EVENT_RED);

		declareImage(IMG_TASK_BPM, ICONS_PATH + IMG_TASK_BPM);
		declareImage(IMG_TASK_PAGEFLOW, ICONS_PATH + IMG_TASK_PAGEFLOW);
		declareImage(IMG_TASK_BLUE, ICONS_PATH + IMG_TASK_BLUE);
		declareImage(IMG_TASK_GRAY, ICONS_PATH + IMG_TASK_GRAY);
		declareImage(IMG_TASK_GREEN, ICONS_PATH + IMG_TASK_GREEN);
		declareImage(IMG_TASK_RED, ICONS_PATH + IMG_TASK_RED);

		declareImage(IMG_GATEWAY_BPM, ICONS_PATH + IMG_GATEWAY_BPM);
		declareImage(IMG_GATEWAY_PAGEFLOW, ICONS_PATH + IMG_GATEWAY_PAGEFLOW);

		declareImage(IMG_SUBPROCESS_BPM, ICONS_PATH + IMG_SUBPROCESS_BPM);
		declareImage(IMG_SUBPROCESS_PAGEFLOW, ICONS_PATH + IMG_SUBPROCESS_PAGEFLOW);
		declareImage(IMG_SUBPROCESS_GRAY, ICONS_PATH + IMG_SUBPROCESS_GRAY);
		declareImage(IMG_SUBPROCESS_RED, ICONS_PATH + IMG_SUBPROCESS_RED);
		declareImage(IMG_SUBPROCESS_GREEN, ICONS_PATH + IMG_SUBPROCESS_GREEN);
		declareImage(IMG_SUBPROCESS_BLUE, ICONS_PATH + IMG_SUBPROCESS_BLUE);

		declareImage(IMG_INDEPENDENT_BPM, ICONS_PATH + IMG_INDEPENDENT_BPM);
		declareImage(IMG_INDEPENDENT_PAGEFLOW, ICONS_PATH + IMG_INDEPENDENT_PAGEFLOW);
		declareImage(IMG_INDEPENDENT_GRAY, ICONS_PATH + IMG_INDEPENDENT_GRAY);
		declareImage(IMG_INDEPENDENT_RED, ICONS_PATH + IMG_INDEPENDENT_RED);
		declareImage(IMG_INDEPENDENT_GREEN, ICONS_PATH + IMG_INDEPENDENT_GREEN);
		declareImage(IMG_INDEPENDENT_BLUE, ICONS_PATH + IMG_INDEPENDENT_BLUE);

		declareImage(IMG_TRACK, ICONS_PATH + IMG_TRACK);

		declareImage(IMG_REPORT, ICONS_PATH + IMG_REPORT);
		
		declareImage(IMG_PAGEFLOW_PROCESS, ICONS_PATH + IMG_PAGEFLOW_PROCESS);
		declareImage(IMG_PAGEFLOW_BREAKPOINT_AFTER, ICONS_PATH + IMG_PAGEFLOW_BREAKPOINT_AFTER);
		declareImage(IMG_PAGEFLOW_BREAKPOINT_AFTER_DISABLED, ICONS_PATH + IMG_PAGEFLOW_BREAKPOINT_AFTER_DISABLED);
		declareImage(IMG_PAGEFLOW_BREAKPOINT_BEFORE, ICONS_PATH + IMG_PAGEFLOW_BREAKPOINT_BEFORE);
		declareImage(IMG_PAGEFLOW_BREAKPOINT_BEFORE_DISABLED, ICONS_PATH + IMG_PAGEFLOW_BREAKPOINT_BEFORE_DISABLED);
		declareImage(IMG_PAGEFLOW_BREAKPOINT_ALL, ICONS_PATH + IMG_PAGEFLOW_BREAKPOINT_ALL);
		declareImage(IMG_PAGEFLOW_BREAKPOINT_ALL_DISABLED, ICONS_PATH + IMG_PAGEFLOW_BREAKPOINT_ALL_DISABLED);
	}

	/**
	 * Declares a workbench image given the path of the image file (relative to
	 * the workbench plug-in). This is a helper method that creates the image
	 * descriptor and passes it to the main <code>declareImage</code> method.
	 * 
	 * @param key
	 *            the symbolic name of the image
	 * @param path
	 *            the path of the image file relative to the base of the
	 *            workbench plug-ins install directory
	 */
	private void declareImage(String key, String path) {
		ImageDescriptor desc = imageDescriptorFromPlugin(PLUGIN_ID, path);
		getImageRegistry().put(key, desc);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static DebugBPMUIActivator getDefault() {
		return plugin;
	}

	public static IStatus newStatus(Throwable exception, String message) {
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

	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	/**
	 * @return The simulation annotation factory.
	 */
	public BxDiagramAnnotationFactory getAnnotationFactory() {
		return annotationFactory;
	}

	public static Image getRegisteredImage(String key) {
		return getDefault().getImageRegistry().get(key);
	}

	public static ImageDescriptor getRegisteredImageDescriptor(String key) {
		return getDefault().getImageRegistry().getDescriptor(key);
	}

	/**
	 * @return The emulation annotation factory for debugging.
	 */
	public EMAnnotationFactory4Debugging getAnnotationFactoryEdit4Debugging() {
		return new EMAnnotationFactory4Debugging();
	}
}
