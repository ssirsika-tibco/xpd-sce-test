package com.tibco.xpd.bom.resources.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    /**
     * The plug-in ID
     */
    public static final String PLUGIN_ID = "com.tibco.xpd.bom.resources.ui"; //$NON-NLS-1$

    /**
     * The BOM asset ID
     */
    public static final String BOM_ASSET_ID = "com.tibco.xpd.asset.bom"; //$NON-NLS-1$

    /**
     * The Solution Designer Acivity ID
     */
    public static final String BOM_SOLUTION_DESIGN = "com.tibco.xpd.bom.solutiondesign"; //$NON-NLS-1$

    /**
     * The id of the BOM editor.
     */
    public static final String BOM_EDITOR_ID = "com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorID"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    // private IActivityManagerListener activityManagerListener;

    /**
     * The constructor
     */
    public Activator() {
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

        /*
         * activityManagerListener = new bomActivityListener();
         * 
         * IActivityManager activityManager = PlatformUI.getWorkbench()
         * .getActivitySupport().getActivityManager();
         * 
         * activityManager.addActivityManagerListener(activityManagerListener);
         */

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
        /*
         * if (activityManagerListener != null) {
         * PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
         * .removeActivityManagerListener(activityManagerListener); }
         */
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        super.initializeImageRegistry(reg);

        for (String img : BOMImages.IMAGES) {
            reg.put(img, ImageDescriptor.createFromURL(getBundle()
                    .getEntry(img)));
        }
    }

    /**
     * Access to eclipse log.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    // MOVED TO com.tibco.xpd.resources.ui.XpdResourcesUIActivator
    /*
     * class bomActivityListener implements IActivityManagerListener {
     * 
     * public void activityManagerChanged( ActivityManagerEvent
     * activityManagerEvent) {
     * 
     * Set enabledActivityIds = activityManagerEvent.getActivityManager()
     * .getEnabledActivityIds();
     * 
     * boolean solutionDesignIsEnabled = enabledActivityIds
     * .contains(BOM_SOLUTION_DESIGN);
     * 
     * boolean solutionDesignPrevEnabled = activityManagerEvent
     * .getPreviouslyEnabledActivityIds().contains( BOM_SOLUTION_DESIGN);
     * 
     * if ((solutionDesignIsEnabled && !solutionDesignPrevEnabled) ||
     * (!solutionDesignIsEnabled && solutionDesignPrevEnabled)) {
     * 
     * 
     * MessageDialog dialog = new MessageDialog(
     * PlatformUI.getWorkbench().getActiveWorkbenchWindow() .getShell(),
     * Messages.Activator_ActivityChangesDialog_title, null,
     * Messages.Activator_ActivityChangesDialog_message, MessageDialog.QUESTION,
     * new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL,
     * IDialogConstants.CANCEL_LABEL }, 3);
     * 
     * int dlgRet = dialog.open();
     * 
     * if (dlgRet != 2 ) {
     * 
     * // Do build if requested if (dlgRet == 0 ) { // rebuild(container,
     * project); Job job = getBuildJob(null); job.schedule(); } }
     * 
     * }
     * 
     * } }
     */

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // Do nothing
    }

}
