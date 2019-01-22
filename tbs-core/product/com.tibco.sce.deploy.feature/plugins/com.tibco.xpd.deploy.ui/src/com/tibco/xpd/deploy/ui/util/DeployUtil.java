/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.IProgressConstants;

import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.TestableConnection;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.api.IDeploymentResolver;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.navigator.DeployResolver;
import com.tibco.xpd.deploy.ui.preferences.PreferenceStoreKeys;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.util.MessageDialogUtil;

/**
 * @author mtorres
 * 
 */
public class DeployUtil {

    public static final String DEPLOY_JOB_FAMILY_ID = "deploy_job_family_id"; //$NON-NLS-1$

    private static final String DEPLOY_RESOLVER_EXTENSION = "deployResolver"; //$NON-NLS-1$

    private static final String SERVER_TYPE_ID = "serverTypeId"; //$NON-NLS-1$

    private static final String RESOLVER = "resolver";//$NON-NLS-1$

    private static final String RESOLVER_CLASS = "resolverClass";//$NON-NLS-1$

    private static final String VALID_EXTENSIONS = "validExtensions";//$NON-NLS-1$

    private static Map<String, List<DeployResolver>> deployResolverMap = null;

    public static List<DeployResolver> getDeployResolvers(String serverTypeId) {
        List<DeployResolver> deployResolverList = null;
        if (deployResolverMap == null) {
            DeployUtil.loadDeploymentResolvers();
        }
        if (serverTypeId != null && deployResolverMap != null) {
            deployResolverList = deployResolverMap.get(serverTypeId);
        }
        if (deployResolverMap == null) {
            deployResolverList = Collections.emptyList();
        }
        return deployResolverList;
    }

    private static void loadDeploymentResolvers() {
        deployResolverMap = new HashMap<String, List<DeployResolver>>();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] elements =
                registry.getConfigurationElementsFor(DeployUIActivator.PLUGIN_ID,
                        DEPLOY_RESOLVER_EXTENSION);
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                IConfigurationElement element = elements[i];
                String serverTypeId = element.getAttribute(SERVER_TYPE_ID);
                if (serverTypeId != null) {
                    // Get the Deployment resolvers
                    IConfigurationElement[] deployResolvers =
                            element.getChildren(RESOLVER);
                    if (deployResolvers != null) {
                        for (IConfigurationElement deployResolverElement : deployResolvers) {
                            if (deployResolverElement != null) {
                                String validExtensions =
                                        deployResolverElement
                                                .getAttribute(VALID_EXTENSIONS);
                                try {
                                    Object resolverClass =
                                            deployResolverElement
                                                    .createExecutableExtension(RESOLVER_CLASS);
                                    if (resolverClass instanceof IDeploymentResolver) {
                                        DeployResolver deployResolver =
                                                new DeployResolver(
                                                        serverTypeId,
                                                        (IDeploymentResolver) resolverClass,
                                                        validExtensions);
                                        List<DeployResolver> deployResolverList =
                                                deployResolverMap
                                                        .get(serverTypeId);
                                        if (deployResolverList == null) {
                                            deployResolverList =
                                                    new ArrayList<DeployResolver>();
                                        }
                                        deployResolverList.add(deployResolver);
                                        deployResolverMap.put(serverTypeId,
                                                deployResolverList);
                                    }
                                } catch (CoreException e) {
                                    // Ignore
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public static List<IResource> getDependencyModules(
            List<IResource> outputModules, String serverId) {
        List<IResource> dependencyModules = null;
        if (outputModules != null && serverId != null
                && !outputModules.isEmpty()) {
            // Get deploy resolver list
            List<DeployResolver> deployResolvers =
                    DeployUtil.getDeployResolvers(serverId);
            if (deployResolvers != null && !deployResolvers.isEmpty()) {
                dependencyModules =
                        getDependencyModules(outputModules, deployResolvers);
            }
        }
        if (dependencyModules == null) {
            dependencyModules = Collections.emptyList();
        }
        return dependencyModules;
    }

    public static List<IResource> getDependencyModules(
            List<IResource> outputModules, List<DeployResolver> deployResolvers) {
        List<IResource> dependencyModules = null;
        if (outputModules != null && deployResolvers != null
                && !deployResolvers.isEmpty() && !outputModules.isEmpty()) {
            for (DeployResolver deployResolver : deployResolvers) {
                if (deployResolver != null) {
                    String validExtensionsStr =
                            deployResolver.getValidExtensions();
                    List<String> validExtensions =
                            DragAndDropDeploymentUtil
                                    .getValidModulesExtensions(validExtensionsStr);
                    IDeploymentResolver resolver =
                            deployResolver.getDeploymentResolver();
                    Set<IResource> filteredOutputModules =
                            DeployUtil
                                    .filterOutputModulesForDependency(validExtensions,
                                            outputModules);
                    if (filteredOutputModules != null && resolver != null) {
                        if (dependencyModules == null) {
                            dependencyModules = new ArrayList<IResource>();
                        }
                        dependencyModules.addAll(resolver
                                .getDependentModules(filteredOutputModules));
                    }
                }
            }
        }
        if (dependencyModules == null) {
            dependencyModules = Collections.emptyList();
        }
        return dependencyModules;
    }

    private static Set<IResource> filterOutputModulesForDependency(
            List<String> validExtensions, List<IResource> outputModules) {
        Set<IResource> filteredOutputModules = new HashSet<IResource>();
        if (validExtensions == null || validExtensions.isEmpty()) {
            filteredOutputModules.addAll(outputModules);
        } else {
            for (IResource outputModule : outputModules) {
                if (outputModule != null) {
                    String outputModuleExtension =
                            outputModule.getFileExtension();
                    if (outputModuleExtension != null
                            && validExtensions.contains(outputModuleExtension)) {
                        filteredOutputModules.add(outputModule);
                    }
                }
            }
        }
        return filteredOutputModules;
    }

    public static boolean saveDirtyResources(List<?> resources, Shell shell) {
        boolean resourcesSaved = false;
        List<IResource> dirtyResources = null;
        if (resources != null) {
            if (!resources.isEmpty()) {
                if (DeployUtil.containsDirtyFiles(resources)) {
                    String message = null;
                    if (resources.size() == 1 && resources.get(0) != null
                            && resources.get(0) instanceof IResource) {
                        message = Messages.DeployUtil_Save_File_BeforeDeploy;
                        message =
                                String.format(message, ((IResource) resources
                                        .get(0)).getName());
                    } else {
                        message = Messages.DeployUtil_Save_Files_BeforeDeploy;
                    }
                    // Ask The user to save
                    String title =
                            Messages.DeployUtil_Save_Files_BeforeDeploy_Title;
                    String toggleMessage =
                            Messages.DeploymentPreferencePage_Dont_Ask_Save_Before_Deployment;
                    int returnCode =
                            MessageDialogUtil
                                    .openYesNoCancelQuestion(shell,
                                            title,
                                            message,
                                            toggleMessage,
                                            false,
                                            PreferenceStoreKeys.DONT_ASK_TO_SAVE_BEFORE_DEPLOYMENT,
                                            DeployUIActivator.getDefault()
                                                    .getPreferenceStore());
                    if (returnCode == IDialogConstants.YES_ID) {
                        try {
                            DeployUtil.saveDirtyFilesWC(resources);
                            resourcesSaved = true;
                        } catch (IOException e) {
                            resourcesSaved = false;
                            MessageDialog.openError(shell,
                                    Messages.DeployUtil_Save_Files_Error_Title,
                                    Messages.DeployUtil_Save_Files_Error);
                        }
                    } else if (returnCode == IDialogConstants.NO_ID) {
                        resourcesSaved = false;
                    }
                } else {
                    resourcesSaved = true;
                }
            }
        }
        return resourcesSaved;
    }

    /**
     * Saves all the dirty resources in the workspace
     * 
     * @return <code>true</code> if all the dirty editors were saved, else
     *         return <code>false</code>
     */
    public static boolean saveAllDirtyResourcesInWS() {
        boolean resourcesSaved = false;
        if (DeployHelper.saveAllEditors(true)) {
            resourcesSaved = true;
        }
        return resourcesSaved;
    }

    /**
     * Saves all the dirty resources in the workspace and tracks and reports the
     * progress via {@link IProgressMonitor}
     * 
     * @param monitor
     * @return <code>true</code> if all the dirty editors were saved, else
     *         return <code>false</code>
     */
    public static boolean saveAllDirtyResourcesInWS(IProgressMonitor monitor) {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$   

            monitor.subTask(Messages.DeployUtil_SavingModifiedWsFilesMonitor_msg);
            boolean saveAllDirtyResourcesInWS = saveAllDirtyResourcesInWS();
            monitor.worked(1);

            return saveAllDirtyResourcesInWS;
        } finally {
            monitor.done();
        }
    }

    /**
     * Check if the selection contains a dirty file.
     * 
     * @param resources
     *            resources to check
     * @return <code>true</code> if selection contains a dirty file,
     *         <code>false</code> otherwise.
     */
    public static boolean containsDirtyFiles(List<?> resources) {
        final boolean[] isDirty = new boolean[] { false };

        if (resources != null) {
            for (Object next : resources) {
                if (next instanceof IContainer) {
                    try {
                        ((IContainer) next).accept(new IResourceVisitor() {

                            @Override
                            public boolean visit(IResource resource)
                                    throws CoreException {

                                if (!isDirty[0]) {
                                    if (resource instanceof IFile) {
                                        WorkingCopy wc =
                                                XpdResourcesPlugin
                                                        .getDefault()
                                                        .getWorkingCopy(resource);

                                        isDirty[0] =
                                                wc != null
                                                        && wc.isWorkingCopyDirty();
                                        return false;
                                    }
                                } else {
                                    // Dirty file already found so no point
                                    // searching further
                                    return false;
                                }
                                return true;
                            }

                        });
                    } catch (CoreException e) {
                        XpdResourcesUIActivator.getDefault().getLogger()
                                .error(e);
                    }
                } else if (next instanceof IFile) {
                    WorkingCopy wc =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy((IResource) next);

                    isDirty[0] = wc != null && wc.isWorkingCopyDirty();
                }

                if (isDirty[0]) {
                    // Dirty file found so no point searching further
                    break;
                }
            }
        }

        return isDirty[0];
    }

    /**
     * Get all dirty files.
     * 
     * @param resources
     *            resources selection
     * @return <code>true</code> if selection contains a dirty file,
     *         <code>false</code> otherwise.
     */
    private static void saveDirtyFilesWC(List<?> resources) throws IOException {
        final List<WorkingCopy> dirtyFilesWC = new ArrayList<WorkingCopy>();
        if (resources != null) {
            for (Object next : resources) {
                if (next instanceof IContainer) {
                    try {
                        ((IContainer) next).accept(new IResourceVisitor() {

                            @Override
                            public boolean visit(IResource resource)
                                    throws CoreException {
                                if (resource instanceof IFile) {
                                    WorkingCopy wc =
                                            XpdResourcesPlugin.getDefault()
                                                    .getWorkingCopy(resource);

                                    if (wc != null && wc.isWorkingCopyDirty()) {
                                        dirtyFilesWC.add(wc);
                                    }
                                    return false;
                                }
                                return true;
                            }

                        });
                    } catch (CoreException e) {
                        XpdResourcesUIActivator.getDefault().getLogger()
                                .error(e);
                    }
                } else if (next instanceof IFile) {
                    WorkingCopy wc =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy((IResource) next);

                    if (wc != null && wc.isWorkingCopyDirty()) {
                        dirtyFilesWC.add(wc);
                    }
                }
            }
            for (WorkingCopy wc : dirtyFilesWC) {
                if (wc != null && wc.isWorkingCopyDirty()) {
                    wc.save();
                }
            }
        }
    }

    public static boolean isModal(Job job) {
        Boolean isModal =
                (Boolean) job
                        .getProperty(IProgressConstants.PROPERTY_IN_DIALOG);
        if (isModal == null) {
            // it will make us sure that dialog will not be
            // displayed if backgroud job.
            return false;
        }
        return isModal.booleanValue();
    }

    /**
     * This method encapsulates the strategy for creating default names.
     * 
     * @param prefix
     * @param elements
     * @return
     */
    public static String getDefaultName(String prefix,
            Collection<String> elements) {
        if (!elements.contains(prefix)) {
            return prefix;
        }
        for (int i = 1;; i++) {
            if (!elements.contains(prefix + i)) {
                return prefix + i;
            }
        }
    }

    /**
     * Returns {@link TestableConnection} if connection implements or adapts to
     * it.
     * 
     * @param connection
     *            a connection to check.
     * @return {@link TestableConnection} if connection implements or adapts to
     *         it. 'null' will be returned otherwise.
     */
    public static TestableConnection getTestableConnection(Connection connection) {
        TestableConnection testableConnection = null;
        if (connection instanceof TestableConnection) {
            testableConnection = (TestableConnection) connection;
        }
        if (testableConnection == null) {
            Object adapter = connection.getAdapter(TestableConnection.class);
            if (adapter instanceof TestableConnection) {
                testableConnection = (TestableConnection) adapter;
            } else {
                Object platformAdapter =
                        Platform.getAdapterManager().getAdapter(connection,
                                TestableConnection.class);
                if (platformAdapter instanceof TestableConnection) {
                    testableConnection = (TestableConnection) platformAdapter;
                }
            }
        }
        return testableConnection;
    }
}
