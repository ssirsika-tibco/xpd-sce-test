/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.resources.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.BuildJob;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.types.TypeUtilInternal;
import com.tibco.xpd.ui.internal.actions.ActivitiesUtil;
import com.tibco.xpd.ui.perspective.PerspectiveLifecycleManager;
import com.tibco.xpd.ui.preferences.PreferenceStoreKeys;
import com.tibco.xpd.ui.util.MessageDialogUtil;

/**
 * @author wzurek
 * 
 */
public class XpdResourcesUIActivator extends AbstractUIPlugin {

    /** ID of the plugin */
    public static final String ID = "com.tibco.xpd.resources.ui"; //$NON-NLS-1$

    private IActivityManagerListener activityManagerListener;

    private static XpdResourcesUIActivator plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(ID);

    /**
     * 
     */
    public XpdResourcesUIActivator() {
        plugin = this;
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        TypeUtilInternal.loadTypesProviders();
        // JA: Synchronization was added to avoid deadlock as
        // initializeCategories() invokes preferences framework, for which, one
        // of the contributions synchronize to UI thread.
        if (PlatformUI.isWorkbenchRunning()) {
            XpdResourcesPlugin.getStandardDisplay().asyncExec(new Runnable() {
                @Override
                public void run() {
                    initializeCategories();
                    // Important to add the listener after initialize categories
                    activityManagerListener = new ActivityManagerListener();

                    IActivityManager activityManager =
                            PlatformUI.getWorkbench().getActivitySupport()
                                    .getActivityManager();

                    activityManager.addActivityManagerListener(activityManagerListener);
                }
            });
        }
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
        PerspectiveLifecycleManager.getInstance().dispose();
        if (activityManagerListener != null) {
            if (PlatformUI.isWorkbenchRunning()) {
                if (PlatformUI.getWorkbench() != null
                        && PlatformUI.getWorkbench().getActivitySupport() != null
                        && PlatformUI.getWorkbench().getActivitySupport()
                                .getActivityManager() != null) {
                    PlatformUI
                            .getWorkbench()
                            .getActivitySupport()
                            .getActivityManager()
                            .removeActivityManagerListener(activityManagerListener);
                }
            }
        }
        plugin = null;

        XpdColorRegistry.getDefault().dispose();

        super.stop(context);
    }

    @SuppressWarnings("unchecked")
    public static IConfigurationElement getEditorFactoryConfigFor(Object obj) {
        IExtensionPoint ep =
                Platform.getExtensionRegistry().getExtensionPoint(ID,
                        "objectEditor"); //$NON-NLS-1$
        if (ep == null) {
            return null;
        }
        IConfigurationElement[] elements = ep.getConfigurationElements();
        IConfigurationElement returnConfigElement = null;
        for (int i = 0; i < elements.length; i++) {
            IConfigurationElement el = elements[i];
            String att = el.getAttribute("objectClass"); //$NON-NLS-1$
            try {
                Class c =
                        Class.forName(att, false, obj.getClass()
                                .getClassLoader());
                if (c.isAssignableFrom(obj.getClass())) {
                    if (el.getAttribute("objectFilter") != null) {
                        // Match on object filter when provided
                        Object f = el.createExecutableExtension("objectFilter");
                        if (f instanceof IFilter) {
                            IFilter filter = (IFilter) f;

                            if (filter.select(obj)) {
                                returnConfigElement = el;
                            }
                        }
                    } else {
                        // Just match on object class.
                        returnConfigElement = el;
                    }

                    if (returnConfigElement != null) {
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                // ignore
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return returnConfigElement;
    }

    /**
     * loads all provider extensions rules as their configuration elements
     * 
     * @return
     */
    Map<String, List<IConfigurationElement>> loadProviderExtensionRules() {
        Map<String, List<IConfigurationElement>> result =
                new HashMap<String, List<IConfigurationElement>>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry().getExtensionPoint(ID,
                        "providerExtension"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();
        for (IConfigurationElement config : configs) {
            if ("providerExtension".equals(config.getName())) { //$NON-NLS-1$
                String parentId = config.getAttribute("providerId"); //$NON-NLS-1$
                IConfigurationElement[] rules = config.getChildren("rule"); //$NON-NLS-1$
                List<IConfigurationElement> rulesList = result.get(parentId);
                if (rulesList == null) {
                    rulesList = new ArrayList<IConfigurationElement>();
                    result.put(parentId, rulesList);
                }
                for (IConfigurationElement configurationElement : rules) {
                    rulesList.add(configurationElement);
                }
            }
        }
        return result;
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
        return AbstractUIPlugin.imageDescriptorFromPlugin(ID, path);
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = XpdResourcesUIConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }

    /**
     * Returns the shared instance.
     */
    public static XpdResourcesUIActivator getDefault() {
        return plugin;
    }

    /**
     * Access to eclipse log.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Initialises the categories at startup
     * 
     */
    private void initializeCategories() {
        // Get the contributed categories
        Set<ICategory> categories = ActivitiesUtil.getCategories();
        if (categories != null) {
            for (ICategory category : categories) {
                if (category != null) {
                    if (ActivitiesUtil.isCategoryEnablementDefined(category)) {
                        String categoryEnablement =
                                ActivitiesUtil
                                        .getCategoryEnablementDefined(category);
                        if (categoryEnablement != null) {
                            // Get the current status of the category and do
                            // nothing if there is no change
                            boolean isCategoryCurrentlyEnabled =
                                    ActivitiesUtil.isCategoryEnabled(category);
                            if (categoryEnablement
                                    .equals(ActivitiesUtil.CATEGORY_ENABLEMENT_ENABLED)
                                    && !isCategoryCurrentlyEnabled) {
                                // Enable category
                                ActivitiesUtil.setCategoryEnabled(category
                                        .getId(), true);
                            } else if (categoryEnablement
                                    .equals(ActivitiesUtil.CATEGORY_ENABLEMENT_NOT_ENABLED)
                                    && isCategoryCurrentlyEnabled) {
                                // Disable category
                                ActivitiesUtil.setCategoryEnabled(category
                                        .getId(), false);
                            }
                        }
                    } else {
                        // This means that no category enablement has been
                        // defined so apply the default
                        Set<String> categoryDefaultEnablementIds =
                                ActivitiesUtil
                                        .readCategoryDefaultEnablementIds();
                        if (categoryDefaultEnablementIds != null
                                && categoryDefaultEnablementIds
                                        .contains(category.getId())) {
                            // Get the current status of the category and do
                            // nothing if there is no change
                            boolean isCategoryCurrentlyEnabled =
                                    ActivitiesUtil.isCategoryEnabled(category);
                            if (!isCategoryCurrentlyEnabled) {
                                // Enable category
                                ActivitiesUtil.setCategoryEnabled(category
                                        .getId(), true);
                            }
                        }
                    }
                }
            }
        }
    }

    class ActivityManagerListener implements IActivityManagerListener {
        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.ui.activities.IActivityManagerListener#activityManagerChanged
         * (org.eclipse.ui.activities.ActivityManagerEvent)
         */
        @Override
        public void activityManagerChanged(
                ActivityManagerEvent activityManagerEvent) {
            updatePreferenceStore();
            doFullBuild();
        }

        private void updatePreferenceStore() {
            // Get the contributed categories
            Set<ICategory> categories = ActivitiesUtil.getCategories();
            if (categories != null) {
                for (ICategory category : categories) {
                    if (category != null) {
                        ActivitiesUtil.setCategoryEnablementInStore(category,
                                ActivitiesUtil.isCategoryEnabled(category));
                    }
                }
            }
        }

        private void doFullBuild() {
            boolean skipBuildOnActivityChange =
                    XpdResourcesPlugin
                            .getDefault()
                            .getPreferenceStore()
                            .getBoolean(com.tibco.xpd.resources.PreferenceStoreKeys.SKIP_BUILD_ON_ACTIVITY_CHANGE);
            if (skipBuildOnActivityChange) {
                return;
            }
            String title =
                    Messages.XpdResourcesUIActivator_CategoryChangesDialog_title;
            String message =
                    Messages.XpdResourcesUIActivator_CategoryChangesDialog_message;
            String toggleMessage =
                    Messages.XpdResourcesUIActivator_CategoryChanges_BuildWithoutAsking_Label;
            /*
             * Ask user if they wish to build
             */
            int returnCode =
                    MessageDialogUtil
                            .openYesNoQuestion(PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getShell(),
                                    title,
                                    message,
                                    toggleMessage,
                                    false,
                                    PreferenceStoreKeys.DONT_ASK_TO_BUILD_WHEN_CHANGING_CAPABILITIES);
            if (returnCode == 2) {
                // rebuild(container, project);
                Job job = getBuildJob(null);
                job.schedule();
            }
        }
    }

    /**
     * Get a full build job.
     * 
     * @param project
     *            project to build. If project is <code>null</code> then the
     *            whole workspace will be rebuilt.
     * @return build <code>Job</code>
     */
    private Job getBuildJob(final IProject project) {
        return new BuildJob(Messages.XpdResourcesUIActivator_buildJob_label,
                project);
    }
}
