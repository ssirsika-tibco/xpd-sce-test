package com.tibco.xpd.deploy.ui.navigator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.internal.expressions.Expressions;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.progress.IProgressConstants;

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus.Severity;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.actions.DeployAction;
import com.tibco.xpd.deploy.ui.actions.DeploymentActionStatus;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.util.ConnectionHelper;
import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.deploy.ui.util.DragAndDropDeploymentUtil;
import com.tibco.xpd.deploy.ui.wizards.deploy.DeploymentResultsDialog;
import com.tibco.xpd.deploy.util.DeploymentStatusUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

public class DeployServerDropAdapterAssistant extends
        CommonDropAdapterAssistant {

    private static Logger LOG = DeployUIActivator.getDefault().getLogger();

    private Server selectedServer;

    private Connection connection;

    private List<DragAndDropDeployment> validDragAndDropDeploymentList;

    private List<DeployableModulesContainer> deployableModulesContainerList;

    private LinkedHashMap<String, List<IConfigurationElement>> delegates;

    private IStructuredSelection selection;

    private Job preDeploymentJob = null;

    private Job deploymentJob = null;

    private DeploymentActionStatus deploymentActionStatus;

    private DropAssistantDelegate handlingDropAssistant;

    /**
     * This method only happens once and creates the needed jobs
     **/
    @Override
    protected void doInit() {
        delegates = new LinkedHashMap<String, List<IConfigurationElement>>();
        for (IConfigurationElement ce : Platform.getExtensionRegistry()
                .getConfigurationElementsFor(DeployUIActivator.PLUGIN_ID,
                        "dragAndDropDeployment")) { //$NON-NLS-1$
            if ("delegateServerDropAssistant".equals(ce.getName())) { //$NON-NLS-1$
                String serverTypeId = ce.getAttribute("serverTypeId"); //$NON-NLS-1$
                if (serverTypeId != null) {
                    List<IConfigurationElement> delegatesForType =
                            delegates.get(serverTypeId);
                    if (delegatesForType == null) {
                        delegates.put(serverTypeId,
                                new ArrayList<IConfigurationElement>(Arrays
                                        .asList(ce)));
                    } else {
                        delegatesForType.add(ce);
                    }
                }
            }
        }

        preDeploymentJob =
                new Job(Messages.XpdlDropAdapterAssistant_Perform_Predeployment) {
                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        return performPreDeployment(monitor);
                    }
                };
        deploymentJob =
                new Job(Messages.XpdlDropAdapterAssistant_Perform_Deployment) {
                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        return performDeployment(monitor);
                    }
                };
        super.doInit();
    }

    @Override
    public IStatus handleDrop(final CommonDropAdapter dropAdapter,
            final DropTargetEvent dropTargetEvent, final Object target) {
        if (handlingDropAssistant != null) {
            DropAssistantDelegate assistant = handlingDropAssistant;
            handlingDropAssistant = null;
            return assistant.handleDrop(dropAdapter, dropTargetEvent, target);
        }
        deploymentActionStatus = new DeploymentActionStatus();
        selection =
                (IStructuredSelection) LocalSelectionTransfer.getTransfer()
                        .getSelection();
        if (selection != null
                && DeployUtil
                        .saveDirtyResources(selection.toList(), getShell())) {
            Job job =
                    new Job(
                            Messages.XpdlDropAdapterAssistant_Handle_DND_Deployment) {
                        @SuppressWarnings("unchecked")
                        @Override
                        protected IStatus run(IProgressMonitor monitor) {
                            IStatus status = Status.OK_STATUS;
                            if (target instanceof Server) {
                                selectedServer = (Server) target;
                                connection = selectedServer.getConnection();
                                if (connection != null) {
                                    // Retrieve the DragAndDropDeployment
                                    // contributions list
                                    // For the selected server
                                    List<DragAndDropDeployment> allDragAndDropDeploymentList =
                                            getAllDragAndDropDeployment(selectedServer);
                                    if (allDragAndDropDeploymentList != null
                                            && !allDragAndDropDeploymentList
                                                    .isEmpty()) {
                                        validDragAndDropDeploymentList =
                                                getValidDragAndDropDeploymentsForDraggedResources(selection
                                                        .toList(),
                                                        connection,
                                                        allDragAndDropDeploymentList);
                                        if (validDragAndDropDeploymentList != null
                                                && !validDragAndDropDeploymentList
                                                        .isEmpty()) {
                                            boolean okToDeploy = true;
                                            preDeploymentJob.schedule();
                                            try {
                                                preDeploymentJob.join();
                                            } catch (InterruptedException e) {
                                                okToDeploy = false;
                                            }
                                            if (okToDeploy) {
                                                deploymentJob.schedule();
                                            } else {

                                            }
                                        } else {
                                            deploymentActionStatus
                                                    .add(new DeploymentSimpleStatus(
                                                            Severity.ERROR,
                                                            Messages.XpdlDropAdapterAssistant_Errors_NoDeploymentArtifactsFound,
                                                            null));
                                        }
                                    } else {
                                        deploymentActionStatus
                                                .add(new DeploymentSimpleStatus(
                                                        Severity.ERROR,
                                                        Messages.XpdlDropAdapterAssistant_Errors_NoDeploymentArtifactsFound,
                                                        null));
                                    }
                                } else {
                                    deploymentActionStatus
                                            .add(new DeploymentSimpleStatus(
                                                    Severity.ERROR,
                                                    Messages.XpdlDropAdapterAssistant_Errors_NullConnection,
                                                    null));
                                }
                            } else {
                                deploymentActionStatus
                                        .add(new DeploymentSimpleStatus(
                                                Severity.ERROR,
                                                Messages.XpdlDropAdapterAssistant_Errors_InvalidTarget,
                                                null));
                            }
                            if (deploymentActionStatus.getChildren() != null
                                    && deploymentActionStatus.getChildren()
                                            .size() > 0) {
                                if (DeployUtil.isModal(this)) {

                                    // If not hiding the deployment result
                                    // dialog then show it
                                    if (DeployUIActivator.getDefault()
                                            .showDeployResultDialog()) {
                                        // The progress dialog is still open so
                                        // just open the message
                                        showDNDResults(deploymentActionStatus);
                                    } else {
                                        setProperty(IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY,
                                                true);
                                        setProperty(IProgressConstants.KEEPONE_PROPERTY,
                                                Boolean.TRUE);
                                        setProperty(IProgressConstants.ACTION_PROPERTY,
                                                getDNDCompletedAction(deploymentActionStatus));

                                        status =
                                                DeploymentStatusUtil
                                                        .adaptToIStatus(deploymentActionStatus,
                                                                DeployUIActivator.PLUGIN_ID,
                                                                IStatus.OK);
                                    }

                                } else {
                                    setProperty(IProgressConstants.KEEPONE_PROPERTY,
                                            Boolean.TRUE);
                                    setProperty(IProgressConstants.ACTION_PROPERTY,
                                            getDNDCompletedAction(deploymentActionStatus));

                                }
                            }
                            return status;
                        }
                    };
            job.schedule();
        } else {
            return Status.CANCEL_STATUS;
        }
        return Status.OK_STATUS;
    }

    private Action getDNDCompletedAction(final DeploymentActionStatus status) {
        return new Action(Messages.DeployAction_ViewDeploymentStatus) {
            @Override
            public void run() {
                Shell shell =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell();
                DeploymentResultsDialog dialog =
                        new DeploymentResultsDialog(shell, status);
                dialog.open();
            }
        };
    }

    private void showDNDResults(final DeploymentActionStatus status) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                getDNDCompletedAction(status).run();
            }
        });
    }

    /**
     * This method prepares the files for deployment it also takes care of the
     * synchronisation when needed
     * 
     * @return IStatus
     **/
    @SuppressWarnings("unchecked")
    private IStatus performPreDeployment(IProgressMonitor monitor) {
        IStatus status = Status.OK_STATUS;
        if (selection != null && selectedServer != null && connection != null
                && validDragAndDropDeploymentList != null
                && !validDragAndDropDeploymentList.isEmpty()) {

            deployableModulesContainerList =
                    new ArrayList<DeployableModulesContainer>();
            for (DragAndDropDeployment dragAndDropDeployment : validDragAndDropDeploymentList) {
                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                if (dragAndDropDeployment != null
                        && dragAndDropDeployment.getDragAndDropSupport() != null) {
                    BaseDragAndDropDeploymentSupport dragAndDropSupport =
                            dragAndDropDeployment.getDragAndDropSupport();
                    DeployableModulesContainer deployableModulesContainer =
                            new DeployableModulesContainer(dragAndDropSupport);
                    List<IResource> allSourceModules =
                            dragAndDropSupport.getSourceModules(selection
                                    .toList());
                    boolean synchronisationOk = true;
                    // We need to synchronise the creation of deployable modules
                    // container
                    if (dragAndDropDeployment
                            .isDeployOnlySynchonizedArtifacts()) {
                        BaseDeploymentSynchronizer synchronizer =
                                dragAndDropDeployment
                                        .getBaseDeploymentSynchronizer();
                        if (synchronizer != null) {
                            Map<IResource, List<IResource>> outputResourcesMap =
                                    createOutputResourcesMap(allSourceModules,
                                            dragAndDropSupport);
                            if (outputResourcesMap != null
                                    && !outputResourcesMap.isEmpty()) {
                                synchronisationOk =
                                        synchronizer
                                                .synchronize(outputResourcesMap,
                                                        monitor);
                            } else {
                                synchronisationOk = false;
                                dragAndDropDeployment.getDragAndDropSupport()
                                        .handleEmptyResources();
                            }
                        }
                    }
                    if (synchronisationOk) {
                        if (dragAndDropDeployment.getDragAndDropSupport()
                                .validateDraggedModules(selection.toList())) {
                            for (IResource resource : allSourceModules) {
                                addWorspaceModulesAndUrls(connection,
                                        selectedServer,
                                        resource,
                                        deployableModulesContainer,
                                        dragAndDropSupport);
                            }
                            deployableModulesContainerList
                                    .add(deployableModulesContainer);
                        } else {
                            deploymentActionStatus
                                    .add(new DeploymentSimpleStatus(
                                            Severity.ERROR,
                                            Messages.XpdlDropAdapterAssistant_Errors_NoDeploymentArtifactsFound,
                                            null));
                        }
                    }
                }
            }

        }
        if (monitor.isCanceled()) {
            status = Status.CANCEL_STATUS;
        }
        return status;
    }

    private IStatus performDeployment(IProgressMonitor monitor) {
        IStatus status = Status.OK_STATUS;
        try {
            deployModules(connection,
                    deployableModulesContainerList,
                    selectedServer,
                    monitor);
        } catch (ConnectionException e) {
            ConnectionHelper.handleConnectionExeption(selectedServer, e);
        } catch (OperationCanceledException e) {
            status = Status.CANCEL_STATUS;
        }
        return status;
    }

    /**
     * @param specialKindList
     * @param targetSpecialKind
     * @return
     */
    private boolean isSpecialKindInList(String[] specialKindList,
            String targetSpecialKind) {
        for (int index = 0; index < specialKindList.length; index++) {
            String tmpSpecialKind = specialKindList[index].trim();
            if (tmpSpecialKind.length() > 0
                    && targetSpecialKind.equals(tmpSpecialKind)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns the list of DragAndDropDeployments for the dragged
     * resources
     * 
     * Only DragAndDropDeployments that
     * 
     * If the resource dragged is a special folder, all the
     * DragAndDropDeployments with that source special folder kind are returned.
     * 
     * If the resource is a file, all the DragAndDropDeployments with that valid
     * extension are returned.
     * 
     * Otherwise return all DragAndDropDeployments
     * 
     * @param draggedResources
     *            the resources that have been dragged.
     * 
     * @return list of DragAndDropDeployments
     */
    private List<DragAndDropDeployment> getValidDragAndDropDeploymentsForDraggedResources(
            List<Object> draggedResources, Connection connection,
            List<DragAndDropDeployment> allDragAndDropDeploymentList) {
        Set<DragAndDropDeployment> validDragAndDropDeploymentSet =
                new HashSet<DragAndDropDeployment>();
        List<DragAndDropDeployment> filteredValidSelections =
                filterInvalidSelections(draggedResources,
                        connection,
                        allDragAndDropDeploymentList);
        if (draggedResources != null && filteredValidSelections != null) {
            for (Object draggedResource : draggedResources) {
                if (draggedResource instanceof IFolder) {
                    IFolder draggedFolder = (IFolder) draggedResource;
                    String specialFolderKind =
                            SpecialFolderUtil
                                    .getSpecialFolderKind(draggedFolder);
                    if (specialFolderKind != null) {
                        for (DragAndDropDeployment dragAndDropDeployment : filteredValidSelections) {
                            if (dragAndDropDeployment != null) {
                                String[] dndDeploymentSpecialKind =
                                        dragAndDropDeployment
                                                .getSourceSpecialFolderKinds();
                                if (dndDeploymentSpecialKind != null
                                        && isSpecialKindInList(dndDeploymentSpecialKind,
                                                specialFolderKind)) {
                                    validDragAndDropDeploymentSet
                                            .add(dragAndDropDeployment);
                                }
                            }
                        }
                    } else {
                        // Return all drag and drop deployments
                        validDragAndDropDeploymentSet =
                                new HashSet<DragAndDropDeployment>(
                                        filteredValidSelections);
                        break;
                    }
                } else if (draggedResource instanceof IFile) {
                    IFile draggedFile = (IFile) draggedResource;
                    String fileExtension = draggedFile.getFileExtension();
                    if (fileExtension != null) {
                        for (DragAndDropDeployment dragAndDropDeployment : filteredValidSelections) {
                            if (dragAndDropDeployment != null
                                    && dragAndDropDeployment
                                            .getDragAndDropSupport() != null) {
                                List<String> dndDeploymentValidSourceExtensions =
                                        dragAndDropDeployment
                                                .getDragAndDropSupport()
                                                .getValidSourceModulesExtensions();
                                // If no extensions are specified then allow
                                // all.
                                if (dndDeploymentValidSourceExtensions == null
                                        || (dndDeploymentValidSourceExtensions != null && dndDeploymentValidSourceExtensions
                                                .contains(fileExtension))) {
                                    validDragAndDropDeploymentSet
                                            .add(dragAndDropDeployment);
                                }
                            }
                        }
                    }
                } else {
                    // Return all drag and drop deployments
                    validDragAndDropDeploymentSet =
                            new HashSet<DragAndDropDeployment>(
                                    filteredValidSelections);
                    break;
                }
            }
        }
        return new ArrayList<DragAndDropDeployment>(
                validDragAndDropDeploymentSet);
    }

    private List<DragAndDropDeployment> getAllDragAndDropDeployment(
            Server selectedServer) {
        List<DragAndDropDeployment> allDragAndDropDeploymentList = null;
        if (selectedServer != null && selectedServer.getServerType() != null) {
            allDragAndDropDeploymentList =
                    DragAndDropDeploymentUtil
                            .getDragAndDropDeployments(selectedServer
                                    .getServerType().getId());
            if (allDragAndDropDeploymentList != null) {
                // Set the server
                for (DragAndDropDeployment dragAndDropDeployment : allDragAndDropDeploymentList) {
                    if (dragAndDropDeployment != null
                            && dragAndDropDeployment.getDragAndDropSupport() != null) {
                        dragAndDropDeployment.getDragAndDropSupport()
                                .setServer(selectedServer);
                    }
                }
            }
            if (allDragAndDropDeploymentList == null) {
                allDragAndDropDeploymentList =
                        new ArrayList<DragAndDropDeployment>();
            }
        }
        return allDragAndDropDeploymentList;
    }

    private List<DragAndDropDeployment> filterInvalidSelections(
            List<Object> draggedResources, Connection connection,
            List<DragAndDropDeployment> allDragAndDropDeploymentList) {
        List<DragAndDropDeployment> onlyValidSelections =
                new ArrayList<DragAndDropDeployment>();
        for (DragAndDropDeployment dragAndDropDeployment : allDragAndDropDeploymentList) {
            if (isValidSelection(draggedResources,
                    connection,
                    dragAndDropDeployment)) {
                onlyValidSelections.add(dragAndDropDeployment);
            }
        }
        return onlyValidSelections;
    }

    private void addWorspaceModulesAndUrls(Connection connection,
            Server server, IResource resourceToDeploy,
            DeployableModulesContainer deployableModulesContainer,
            BaseDragAndDropDeploymentSupport deploymentSupport) {
        if (connection != null && server != null && resourceToDeploy != null
                && deployableModulesContainer != null) {
            String path = resourceToDeploy.getFullPath().toString();
            WorkspaceModule workspaceModule = getWorkspaceModule(path, server);
            if (workspaceModule != null) {
                deployableModulesContainer.addModule(workspaceModule);
            } else {
                // create WorkspaceModule
                WorkspaceModule module =
                        DeployFactory.eINSTANCE.createWorkspaceModule();
                module.setName(resourceToDeploy.getName());
                module.setWorkspaceSrcPath(path);
                module.setDirty(false);
                deployableModulesContainer.addModule(module);
            }
            deployableModulesContainer
                    .addModuleURLs(getWorkspaceModuleDeploymentURLs(deployableModulesContainer
                            .getModulesList(),
                            deploymentSupport));
        }
    }

    private void deployModules(Connection connection,
            List<DeployableModulesContainer> deployableModulesContainerList,
            Server server, IProgressMonitor monitor) throws ConnectionException {
        if (connection != null && server != null) {
            Set<URL> mergedModuleUrls = new HashSet<URL>();
            Set<WorkspaceModule> mergedModules = new HashSet<WorkspaceModule>();

            // Do before deployment
            for (DeployableModulesContainer deployableModulesContainer : deployableModulesContainerList) {
                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
                if (deployableModulesContainer != null
                        && deployableModulesContainer.getDeploymentSupport() != null) {
                    List<URL> moduleUrls =
                            deployableModulesContainer.getModulesUrls();
                    List<WorkspaceModule> modulesList =
                            deployableModulesContainer.getModulesList();
                    mergedModules.addAll(modulesList);
                    List<Object> draggedObjects = new ArrayList<Object>();
                    if (selection != null && selection.size() > 0) {
                        Iterator<?> iter = selection.iterator();
                        while (iter.hasNext()) {
                            draggedObjects.add(iter.next());
                        }
                    }

                    deployableModulesContainer.getDeploymentSupport()
                            .setMonitor(monitor);
                    deployableModulesContainer.getDeploymentSupport()
                            .beforeDeployment(server,
                                    moduleUrls,
                                    modulesList,
                                    draggedObjects);

                    mergedModuleUrls.addAll(moduleUrls);

                }
            }

            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }

            if (mergedModuleUrls != null && !mergedModuleUrls.isEmpty()) {
                deployModule(connection,
                        server,
                        mergedModules,
                        mergedModuleUrls);
            }

            // Do after deployment
            for (DeployableModulesContainer deployableModulesContainer : deployableModulesContainerList) {
                if (deployableModulesContainer != null
                        && deployableModulesContainer.getDeploymentSupport() != null) {
                    List<URL> moduleUrls =
                            deployableModulesContainer.getModulesUrls();
                    List<WorkspaceModule> modulesList =
                            deployableModulesContainer.getModulesList();
                    deployableModulesContainer.getDeploymentSupport()
                            .afterDeployment(server, moduleUrls, modulesList);
                }
            }

            if (connection.isConnected()) {
                for (WorkspaceModule module : mergedModules) {
                    module.setDirty(false);
                }
            } else {
                for (WorkspaceModule module : mergedModules) {
                    module.setDirty(true);
                }
            }
        }
    }

    private void deployModule(Connection connection, Server server,
            Set<WorkspaceModule> modulesList, Set<URL> modulesUrls)
            throws ConnectionException {
        if (connection.isConnected()) {

            new DeployAction(server, new ArrayList<URL>(modulesUrls), true)
                    .run();

        } else {
            for (WorkspaceModule module : modulesList) {
                module.setDirty(true);
            }
        }
    }

    private List<URL> getWorkspaceModuleDeploymentURLs(
            List<WorkspaceModule> modules,
            BaseDragAndDropDeploymentSupport deploymentSupport) {
        Set<URL> moduleDeploymentUrls = new HashSet<URL>();
        List<Object> workspaceModulesResources = new ArrayList<Object>();
        for (WorkspaceModule workspaceModule : modules) {
            IResource resource =
                    ResourcesPlugin
                            .getWorkspace()
                            .getRoot()
                            .findMember(new Path(
                                    workspaceModule.getWorkspaceSrcPath()));
            if (resource != null) {
                workspaceModulesResources.add(resource);
            }
        }
        if (!workspaceModulesResources.isEmpty() && deploymentSupport != null) {
            List<IResource> outputModules =
                    deploymentSupport
                            .getOutputModules(workspaceModulesResources);
            if (outputModules != null) {
                for (IResource outputModule : outputModules) {
                    try {
                        moduleDeploymentUrls.add(outputModule.getLocationURI()
                                .toURL());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                // Add Dependency modules
                if (deploymentSupport.getServer() != null
                        && deploymentSupport.getServer().getServerType() != null
                        && deploymentSupport.getServer().getServerType()
                                .getId() != null) {
                    // Add dependencies
                    List<IResource> dependencyModules =
                            DeployUtil.getDependencyModules(outputModules,
                                    deploymentSupport.getServer()
                                            .getServerType().getId());
                    if (dependencyModules != null
                            && !dependencyModules.isEmpty()) {
                        for (IResource dependencyModule : dependencyModules) {
                            if (dependencyModule != null
                                    && dependencyModule.getLocationURI() != null) {
                                try {
                                    moduleDeploymentUrls.add(dependencyModule
                                            .getLocationURI().toURL());
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<URL>(moduleDeploymentUrls);
    }

    private Map<IResource, List<IResource>> createOutputResourcesMap(
            List<IResource> sourceModules,
            BaseDragAndDropDeploymentSupport baseDragAndDropDeploymentSupport) {
        Map<IResource, List<IResource>> outputResourcesMap =
                new HashMap<IResource, List<IResource>>();
        if (sourceModules != null) {
            for (Object sourceModule : sourceModules) {
                if (sourceModule instanceof IResource) {
                    List<Object> sourceResources =
                            Collections.singletonList(sourceModule);
                    List<IResource> outputResources =
                            baseDragAndDropDeploymentSupport
                                    .getOutputModules(sourceResources);
                    outputResourcesMap.put((IResource) sourceModule,
                            outputResources);
                }
            }
        }
        return outputResourcesMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IStatus validateDrop(Object target, int operation,
            TransferData transferType) {

        IStatus status = Status.CANCEL_STATUS;
        if (target instanceof Server) {
            Server targetServer = (Server) target;
            ServerType serverType = targetServer.getServerType();
            if (serverType != null) {
                boolean isConnected;
                try {
                    isConnected =
                            targetServer.getConnection() != null
                                    && targetServer.getConnection()
                                            .isConnected();
                } catch (ConnectionException e1) {
                    isConnected = false;
                    LOG.error(e1);
                }
                // Try delegates first.
                List<IConfigurationElement> delegateList =
                        delegates.get(serverType.getId());
                if (delegateList != null) {
                    List<DropAssistantDelegate> applicableDelegates =
                            new ArrayList<DropAssistantDelegate>();
                    for (IConfigurationElement delegateEl : delegateList) {
                        boolean mustBeConnected =
                                !"false".equalsIgnoreCase(delegateEl.getAttribute("mustBeConnected")); //$NON-NLS-1$//$NON-NLS-2$
                        if (isConnected == mustBeConnected) {
                            DropAssistantDelegate assistant;
                            try {
                                assistant =
                                        (DropAssistantDelegate) delegateEl
                                                .createExecutableExtension("dropAssistant"); //$NON-NLS-1$
                                IStatus validStatus =
                                        assistant.validateDrop(target,
                                                operation,
                                                transferType);
                                if (validStatus.isOK()) {
                                    applicableDelegates.add(assistant);
                                }
                            } catch (CoreException e) {
                                LOG.error(e);
                            }
                        }
                    }

                    /*
                     * If there is a single drop delegate then run it, otherwise
                     * if there is more than one then let user know that they
                     * cannot drop as the project has multiple target
                     * destinations and therefore don't know which one to run...
                     */
                    if (!applicableDelegates.isEmpty()) {
                        if (applicableDelegates.size() == 1) {
                            handlingDropAssistant = applicableDelegates.get(0);
                            return Status.OK_STATUS;
                        } else {
                            MultipleDropHandlerDelegate multiDropHandler =
                                    new MultipleDropHandlerDelegate(
                                            applicableDelegates);

                            if (multiDropHandler.validateDrop(target,
                                    operation,
                                    transferType).isOK()) {
                                handlingDropAssistant = multiDropHandler;
                                return Status.OK_STATUS;
                            }
                        }
                    }
                }

                if (isConnected) {
                    List<DragAndDropDeployment> allDragAndDropDeploymentList =
                            getAllDragAndDropDeployment(targetServer);
                    if (allDragAndDropDeploymentList != null
                            && !allDragAndDropDeploymentList.isEmpty()) {
                        IStructuredSelection selection =
                                (IStructuredSelection) LocalSelectionTransfer
                                        .getTransfer().getSelection();
                        if (selection != null) {
                            List<Object> draggedResources = selection.toList();
                            List<DragAndDropDeployment> validDragAndDropDeploymentList =
                                    getValidDragAndDropDeploymentsForDraggedResources(draggedResources,
                                            targetServer.getConnection(),
                                            allDragAndDropDeploymentList);

                            if (validDragAndDropDeploymentList != null
                                    && !validDragAndDropDeploymentList
                                            .isEmpty()) {
                                status = Status.OK_STATUS;
                            }
                        }
                    }
                }
            }
        }
        return status;
    }

    private boolean isValidSelection(List<Object> selection,
            Connection connection, DragAndDropDeployment dragAndDropDeployment) {
        boolean isValid = false;
        if (selection != null && dragAndDropDeployment != null) {
            if (isMultiselectCorrect(selection,
                    dragAndDropDeployment.isMultiselect())
                    && isSelectionValidType(selection,
                            dragAndDropDeployment.getValidDropElementList())) {
                return true;
            }
        }
        return isValid;
    }

    private boolean isMultiselectCorrect(List<Object> selection,
            boolean isMultiselectAllowed) {
        if (!isMultiselectAllowed) {
            if (selection.size() > 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isSelectionValidType(List<Object> selection,
            List<String> validTypes) {
        if (selection != null && validTypes != null) {
            boolean areAllValid = false;
            for (Object object : selection) {
                boolean isValidType = false;
                for (String validType : validTypes) {
                    if (Expressions.isInstanceOf(object, validType)) {
                        isValidType = true;
                        break;
                    }
                }
                if (isValidType) {
                    areAllValid = true;
                } else {
                    return false;
                }
            }
            if (areAllValid) {
                return true;
            }
        }
        return false;
    }

    private WorkspaceModule getWorkspaceModule(String path, Server server) {
        for (Iterator<?> iter = server.getWorkspaceModules().iterator(); iter
                .hasNext();) {
            WorkspaceModule module = (WorkspaceModule) iter.next();
            if (path.equals(module.getWorkspaceSrcPath())) {
                return module;
            }
        }
        return null;
    }

    class DeployableModulesContainer {
        BaseDragAndDropDeploymentSupport deploymentSupport;

        List<WorkspaceModule> modulesList;

        List<URL> modulesUrls;

        public DeployableModulesContainer(
                BaseDragAndDropDeploymentSupport deploymentSupport) {
            this.deploymentSupport = deploymentSupport;
        }

        public DeployableModulesContainer(
                BaseDragAndDropDeploymentSupport deploymentSupport,
                List<WorkspaceModule> modulesList, List<URL> modulesUrls) {
            this.deploymentSupport = deploymentSupport;
            this.modulesList = modulesList;
            this.modulesUrls = modulesUrls;
        }

        public BaseDragAndDropDeploymentSupport getDeploymentSupport() {
            return deploymentSupport;
        }

        public void setDeploymentSupport(
                BaseDragAndDropDeploymentSupport deploymentSupport) {
            this.deploymentSupport = deploymentSupport;
        }

        public void addModule(WorkspaceModule module) {
            getModulesList().add(module);
        }

        public void addModules(List<WorkspaceModule> modules) {
            getModulesList().addAll(modules);
        }

        public List<WorkspaceModule> getModulesList() {
            if (modulesList == null) {
                modulesList = new ArrayList<WorkspaceModule>();
            }
            return modulesList;
        }

        public void setModulesList(List<WorkspaceModule> modulesList) {
            this.modulesList = modulesList;
        }

        public void addModuleURL(URL moduleURL) {
            getModulesUrls().add(moduleURL);
        }

        public void addModuleURLs(List<URL> moduleURLs) {
            getModulesUrls().addAll(moduleURLs);
        }

        public List<URL> getModulesUrls() {
            if (modulesUrls == null) {
                modulesUrls = new ArrayList<URL>();
            }
            return modulesUrls;
        }

        public void setModulesUrls(List<URL> modulesUrls) {
            this.modulesUrls = modulesUrls;
        }
    }

    /**
     * A drop handler delegate that will be invoked during project dnd when two
     * or more drop handler delegates can handle the drop (therefore we can't
     * decide which drop handler should be invoked). This handler will display a
     * message to the user.
     * 
     * @author njpatel
     */
    private static class MultipleDropHandlerDelegate extends
            DropAssistantDelegate {

        private final List<DropAssistantDelegate> delegates;

        public MultipleDropHandlerDelegate(List<DropAssistantDelegate> delegates) {
            this.delegates = delegates;
        }

        @Override
        public IStatus validateDrop(Object target, int operation,
                TransferData transferType) {
            return !delegates.isEmpty() ? Status.OK_STATUS
                    : Status.CANCEL_STATUS;
        }

        @Override
        public IStatus handleDrop(CommonDropAdapter aDropAdapter,
                DropTargetEvent aDropTargetEvent, Object aTarget) {

            MessageDialog
                    .openInformation(XpdResourcesPlugin.getStandardDisplay()
                            .getActiveShell(),
                            Messages.DeployServerDropAdapterAssistant_multipleDeployOptions_dialog_title,
                            Messages.DeployServerDropAdapterAssistant_multipleDeployOptions_dialog_longdesc);

            return Status.OK_STATUS;
        }

    }

}
