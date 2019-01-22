package com.tibco.xpd.analyst.resources.xpdl2;

import java.io.IOException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The main plugin class to be used in the desktop.
 */
public class Xpdl2ResourcesPlugin extends AbstractUIPlugin {

    public static final String PLUGIN_ID =
            "com.tibco.xpd.analyst.resources.xpdl2"; //$NON-NLS-1$

    public static final String PROCESS_INDEXER_ID =
            "com.tibco.xpd.analyst.resources.xpdl2.indexing.processIndexer"; //$NON-NLS-1$

    public static final String DECISIONFLOW_INDEXER_ID =
            "com.tibco.xpd.analyst.resources.xpdl2.indexing.decisionFlowIndexer"; //$NON-NLS-1$

    public static final String PROCESS_TO_BOM_INDEXER_ID =
            "com.tibco.xpd.analyst.resources.xpdl2.indexing.processToBOMIndexer"; //$NON-NLS-1$

    public static final String TASK_LIBRARY_INDEX_TYPE =
            "TASK_LIBRARY_INDEX_ITEM"; //$NON-NLS-1$

    public static final String TASK_LIBRARY_TASK_INDEX_TYPE =
            "TASK_LIBRARY_TASK_INDEX_ITEM"; //$NON-NLS-1$

    public static final String TASK_LIBRARY_TASKSET_INDEX_TYPE =
            "TASK_LIBRARY_TASKSET_INDEX_ITEM"; //$NON-NLS-1$

    public static final String TASK_LIBRARY_INDEXER_ID =
            "com.tibco.xpd.wm.tasklibrary.editor.taskLibraryIndexer"; //$NON-NLS-1$

    public static final String ATTRIBUTE_IMAGE_URI = "image_uri"; //$NON-NLS-1$

    public static final String ATTRIBUTE_ITEM_ID = "item_id"; //$NON-NLS-1$

    public static final String ATTRIBUTE_PROCESS_TO_BOM_TYPE =
            "process_to_bom_type"; //$NON-NLS-1$

    public static final String ATTRIBUTE_DISPLAY_NAME = "display_name"; //$NON-NLS-1$

    public static final String ATTRIBUTE_NAME = "name"; //$NON-NLS-1$

    public static final String ATTRIBUTE_PROCESS_URI = "process_uri"; //$NON-NLS-1$

    public static final String ATTRIBUTE_PROCESS_LOCATION = "process_location"; //$NON-NLS-1$

    public static final String ATTRIBUTE_CATEGORY = "category"; //$NON-NLS-1$

    public static final String ATTRIBUTE_BOM_REFID = "bom_refid"; //$NON-NLS-1$

    public static final String ATTRIBUTE_BOM_LOCATION = "bom_location"; //$NON-NLS-1$

    public static final String ATTRIBUTE_BOM_NAMESPACE = "bom_namespace"; //$NON-NLS-1$

    /**
     * XPD-4355 Marker added to the preference store to indicate whether the
     * Eclipse preference to limit the number of open editors has already been
     * set. This is to avoid resetting the value every time the workspace is
     * opened (which will allow the user to override our setting).
     */
    private static final String EDITOR_LIMIT_ALREADY_SET =
            "EclipseEditorLimitPref_alreadySet"; //$NON-NLS-1$

    private Logger logger;

    private static Boolean isTaskLibraryPluginAvailable;

    private static Boolean isWmFeaturePluginAvailable;

    private static Boolean isPageflowAvailable;

    private static Boolean decisionsFeatureAvailable;

    /**
     * Added for XDP-3499, true if mapper array fields should be expanded.
     */
    private static Boolean expandMapperFields;

    // The shared instance.
    private static Xpdl2ResourcesPlugin plugin;

    /**
     * The constructor.
     */
    public Xpdl2ResourcesPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);

        /*
         * XPD-4355: Set the Eclipse preference to limit the number of open
         * editors
         */
        updateEclipsePreference();
    }

    /**
     * Update the Eclipse preference to limit the number of open editors.
     */
    private void updateEclipsePreference() {
        /*
         * XPD-4355: Enable Eclipse's preference to limit the number of editors
         * open at the same time. This is to avoid running out of windows
         * handles (this happens if too many process editors are open). Should
         * not apply to RCP Analyst. NOTE: Do not use XpdResourcesPlugin to get
         * the display as this will cause the plug-in to load prematurely so get
         * display through Eclipse's API and then run async to set this setting
         * once all plug-ins have been loaded and the UI thread is free.
         */
        if (PlatformUI.isWorkbenchRunning() && !hasEclipsePreferenceBeenSet()) {
            IWorkbench workbench = PlatformUI.getWorkbench();
            if (workbench != null) {
                Display display = workbench.getDisplay();
                if (display != null && !XpdResourcesPlugin.isRCP()) {
                    setEclipsePreferenceAlreadySet();
                    display.asyncExec(new Runnable() {
                        @SuppressWarnings("restriction")
                        @Override
                        public void run() {
                            IPreferenceStore store =
                                    WorkbenchPlugin.getDefault()
                                            .getPreferenceStore();
                            if (store != null) {
                                store.setValue(IPreferenceConstants.REUSE_EDITORS_BOOLEAN,
                                        true);

                                if (store instanceof IPersistentPreferenceStore) {
                                    try {
                                        ((IPersistentPreferenceStore) store)
                                                .save();
                                    } catch (IOException e) {
                                        getLogger()
                                                .error(e,
                                                        "Problem saving preference to limit number of open editors."); //$NON-NLS-1$
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * Check if the Eclipse preference to limit open editors has already been
     * set.
     * 
     * @return
     */
    private boolean hasEclipsePreferenceBeenSet() {
        IPreferenceStore store = getPreferenceStore();
        if (store != null) {
            return store.getBoolean(EDITOR_LIMIT_ALREADY_SET);
        }
        return false;
    }

    /**
     * Set marker to indicate that the Eclipse preference to limit open editors
     * has already been set.
     */
    private void setEclipsePreferenceAlreadySet() {
        IPreferenceStore store = getPreferenceStore();
        if (store != null) {
            store.setValue(EDITOR_LIMIT_ALREADY_SET, true);
            if (store instanceof IPersistentPreferenceStore) {
                try {
                    ((IPersistentPreferenceStore) store).save();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
    }

    /**
     * This method is called when the plug-in is stopped
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static Xpdl2ResourcesPlugin getDefault() {
        return plugin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse
     * .jface.resource.ImageRegistry)
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] images = Xpdl2ResourcesConsts.IMAGES;

        for (int x = 0; x < images.length; x++) {
            reg.put(images[x], getImageDescriptor(images[x]));
        }
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

    public static boolean isTaskLibraryPluginAvailable() {
        if (isTaskLibraryPluginAvailable == null) {
            isTaskLibraryPluginAvailable =
                    (null != Platform
                            .getBundle("com.tibco.xpd.wm.tasklibrary.editor")); //$NON-NLS-1$
        }
        return isTaskLibraryPluginAvailable;
    }

    public static boolean isWmFeatureAvailable() {
        if (isWmFeaturePluginAvailable == null) {
            isWmFeaturePluginAvailable =
                    (null != Platform
                            .getBundle("com.tibco.xpd.wm.resources.ui")); //$NON-NLS-1$
        }
        return isWmFeaturePluginAvailable;
    }

    public static boolean isDecisionsFeatureAvailable() {
        if (decisionsFeatureAvailable == null) {
            decisionsFeatureAvailable =
                    (null != Platform
                            .getBundle("com.tibco.xpd.decisions.resources.ui")); //$NON-NLS-1$
        }
        return decisionsFeatureAvailable;
    }

    public static boolean isPageflowAvailable() {
        if (isPageflowAvailable == null) {
            isPageflowAvailable =
                    Xpdl2ResourcesPlugin.isWmFeatureAvailable()
                            || XpdResourcesPlugin.isRCP();
        }
        return isPageflowAvailable;
    }

    /**
     * @return
     */
    public static boolean shouldExpandMapperFields() {
        if (expandMapperFields == null) {
            IPreferenceStore preferenceStore =
                    getDefault().getPreferenceStore();
            if (preferenceStore != null) {
                if (preferenceStore
                        .contains(Xpdl2ResourcesConsts.PREF_EXPAND_MAPPER_FIELDS)) {
                    expandMapperFields =
                            preferenceStore
                                    .getBoolean(Xpdl2ResourcesConsts.PREF_EXPAND_MAPPER_FIELDS);
                }
            }
        }
        return expandMapperFields == null ? false : expandMapperFields;
    }
}
