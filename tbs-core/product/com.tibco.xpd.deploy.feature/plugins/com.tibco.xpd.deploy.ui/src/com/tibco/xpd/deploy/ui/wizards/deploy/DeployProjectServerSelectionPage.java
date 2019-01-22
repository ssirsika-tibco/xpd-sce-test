/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.progress.IProgressService;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.ConfigToolkit;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.providers.DeployLabelProvider;
import com.tibco.xpd.deploy.ui.util.DeploymentExtensionManager;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Page used for selecting the module type and associated deployment wizard.
 * <p>
 * <i>Created: 30 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployProjectServerSelectionPage extends AbstractXpdWizardPage {

    /** */
    private static final String SERVERS_COLUMN = "Servers"; //$NON-NLS-1$

    /** */
    private static final String AVAILABLE_MODULES_COLUMN = "AvailableModules"; //$NON-NLS-1$

    /**
     * 
     * <p>
     * <i>Created: 18 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    private final class ServersViewerLabelProvider extends DeployLabelProvider {

        /** {@inheritDoc} */
        @Override
        public Image getColumnImage(Object object, int columnIndex) {
            if (object instanceof ModuleDeploymentWizardNode
                    && columnIndex == 0) {
                ModuleDeploymentWizardNode node = (ModuleDeploymentWizardNode) object;
                return super.getImage(node.getServer());
            }
            return null;
        }

        /** {@inheritDoc} */
        @Override
        public String getColumnText(Object object, int columnIndex) {
            if (object instanceof ModuleDeploymentWizardNode) {
                ModuleDeploymentWizardNode node = (ModuleDeploymentWizardNode) object;
                switch (columnIndex) {
                case 0:
                    return node.getServer().getName();
                case 1:
                    if (node.getWizard() instanceof IProjectBasedDeployWizard) {
                        IProjectBasedDeployWizard projectBasedWizard = (IProjectBasedDeployWizard) node
                                .getWizard();
                        return Integer.toString(projectBasedWizard
                                .getModulesUrls().size());
                    }
                default:
                    return ""; //$NON-NLS-1$
                }
            }
            return super.getColumnText(object, columnIndex);
        }

    }

    /**
     * 
     * <p>
     * <i>Created: 17 Dec 2008</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     */
    private class ServersViewerContentProvider extends ArrayContentProvider
            implements ITreeContentProvider {
        ModuleDeploymentWizardNode[] wizardNodes = new ModuleDeploymentWizardNode[0];

        /** {@inheritDoc} */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            if (newInput instanceof ServerGroup) {
                ArrayList<ModuleDeploymentWizardNode> entries = new ArrayList<ModuleDeploymentWizardNode>();
                for (Server server : ((ServerGroup) newInput).getMembers()) {
                    ModuleDeploymentWizardNode[] deploymentWizardNodes = DeploymentExtensionManager
                            .getInstance().getDeploymentWizardNodes(server);
                    if (deploymentWizardNodes.length > 0
                            && deploymentWizardNodes[0] != null) {
                        entries.add(deploymentWizardNodes[0]);
                    }
                }
                wizardNodes = entries
                        .toArray(new ModuleDeploymentWizardNode[entries.size()]);
            } else if (newInput != null) {
                Assert.isLegal(false, "Invalid ServerViewer input."); //$NON-NLS-1$
            }
        }

        /** {@inheritDoc} */
        @Override
        public Object[] getElements(Object inputElement) {
            return wizardNodes;
        }

        /** {@inheritDoc} */
        public Object[] getChildren(Object parentElement) {
            return new Object[0];
        }

        /** {@inheritDoc} */
        public Object getParent(Object element) {
            return null;
        }

        /** {@inheritDoc} */
        public boolean hasChildren(Object element) {
            return false;
        }

        /**
         * @return the wizardNodes
         */
        public ModuleDeploymentWizardNode[] getWizardNodes() {
            return wizardNodes;
        }

    }

    private CheckboxTreeViewer projectsTreeViewer;

    private CheckboxTreeViewer serversTreeViewer;

    private ServerGroup contextServerGroup;

    private List<IProject> selectedProjects = Collections.emptyList();

    public DeployProjectServerSelectionPage() {
        super("ProjectServerSelection"); //$NON-NLS-1$
        setTitle(Messages.DeployProjectServerSelectionPage_title);
        setDescription(Messages.DeployProjectServerSelectionPage_longdesc);
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        final ConfigToolkit toolkit = ConfigToolkit.INSTANCE;
        Composite composite = toolkit.createComposite(parent);
        composite.setLayout(new GridLayout());
        SashForm sashForm = new SashForm(composite, SWT.VERTICAL);
        GridDataFactory fillDefaultsWithGrab = GridDataFactory.fillDefaults()
                .grab(true, true);
        GridLayoutFactory marginsLayout = GridLayoutFactory.fillDefaults()
                .margins(5, 5);
        fillDefaultsWithGrab.applyTo(sashForm);
        Group projectGroup = toolkit.createGroup(sashForm,
                Messages.DeployProjectServerSelectionPage_projects_label);
        marginsLayout.applyTo(projectGroup);
        Tree projectsTree = toolkit.createCheckedTree(projectGroup);
        fillDefaultsWithGrab.applyTo(projectsTree);
        projectsTreeViewer = new CheckboxTreeViewer(projectsTree);
        projectsTreeViewer.setContentProvider(new WorkbenchContentProvider() {
            /** {@inheritDoc} */
            @Override
            public boolean hasChildren(Object element) {
                return false;
            }
        });
        projectsTreeViewer.setLabelProvider(new WorkbenchLabelProvider());
        projectsTreeViewer.addCheckStateListener(new ICheckStateListener() {
            public void checkStateChanged(CheckStateChangedEvent event) {
                Object[] checkedElements = projectsTreeViewer
                        .getCheckedElements();
                ArrayList<IProject> checkedProjects = new ArrayList<IProject>();
                for (Object e : checkedElements) {
                    if (e instanceof IProject) {
                        checkedProjects.add((IProject) e);
                    }
                }
                selectedProjects = checkedProjects;
                if (serversTreeViewer != null
                        && serversTreeViewer.getContentProvider() instanceof ServersViewerContentProvider) {
                    IProgressService progressService = PlatformUI
                            .getWorkbench().getProgressService();
                    try {
                        progressService
                                .busyCursorWhile(new IRunnableWithProgress() {

                                    public void run(IProgressMonitor monitor)
                                            throws InvocationTargetException,
                                            InterruptedException {
                                        ModuleDeploymentWizardNode[] nodes = ((ServersViewerContentProvider) serversTreeViewer
                                                .getContentProvider())
                                                .getWizardNodes();
                                        for (ModuleDeploymentWizardNode node : nodes) {
                                            if (node.getWizard() instanceof IProjectBasedDeployWizard) {
                                                ((IProjectBasedDeployWizard) node
                                                        .getWizard())
                                                        .setContextProjects(selectedProjects);

                                            }
                                            if (node.getWizard() instanceof IBindingSupportWizard) {
                                                IBindingSupportWizard wizard = (IBindingSupportWizard) node
                                                        .getWizard();
                                                wizard.setContextModules(wizard
                                                        .getModulesUrls());

                                            }
                                        }
                                    }

                                });
                    } catch (InvocationTargetException e1) {
                        // Log only.
                        DeployUIActivator.getDefault().getLogger().error(e1);
                    } catch (InterruptedException e1) {
                        // Log only.
                        DeployUIActivator.getDefault().getLogger().error(e1);
                    }

                    serversTreeViewer.refresh();
                }

                setPageComplete(validatePage(true));
            }

        });
        projectsTreeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
        projectsTreeViewer.setFilters(new ViewerFilter[] { new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof IProject) {
                    IProject proj = (IProject) element;
                    if (proj.isAccessible()) {
                        try {
                            return proj.hasNature(XpdConsts.PROJECT_NATURE_ID);
                        } catch (CoreException e) {
                            // DO NOTHING EXCEPT LOG
                            DeployUIActivator.getDefault().getLogger().error(e);
                        }
                    }
                }
                return false;
            }
        } });

        Group serversGroup = toolkit.createGroup(sashForm,
                Messages.DeployProjectServerSelectionPage_serversInGroup_label);
        marginsLayout.applyTo(serversGroup);
        Tree serversTree = toolkit.createCheckedTree(serversGroup);
        fillDefaultsWithGrab.applyTo(serversTree);

        serversTree.setHeaderVisible(true);
        serversTree.setLinesVisible(true);

        TreeColumn serverColumn = new TreeColumn(serversTree, SWT.NONE);
        serverColumn
                .setText(Messages.DeployProjectServerSelectionPage_serversColumn_label);
        serverColumn.setWidth(200);

        TreeColumn availableModulesColumn = new TreeColumn(serversTree,
                SWT.NONE);
        availableModulesColumn
                .setText(Messages.DeployProjectServerSelectionPage_selectedModulesColumn_label);
        availableModulesColumn.setWidth(100);

        serversTreeViewer = new CheckboxTreeViewer(serversTree);
        serversTreeViewer.setColumnProperties(new String[] { SERVERS_COLUMN,
                AVAILABLE_MODULES_COLUMN });

        serversTreeViewer
                .setContentProvider(new ServersViewerContentProvider());
        serversTreeViewer.setLabelProvider(new ServersViewerLabelProvider());
        serversTreeViewer.setInput(getContextServerGroup());
        serversTreeViewer.addCheckStateListener(new ICheckStateListener() {
            public void checkStateChanged(CheckStateChangedEvent event) {
                Object element = event.getElement();
                if (element instanceof ModuleDeploymentWizardNode) {
                    ModuleDeploymentWizardNode node = (ModuleDeploymentWizardNode) element;
                    try {
                        if (!node.getServer().getConnection().isConnected()
                                && event.getChecked()) {
                            serversTreeViewer.setChecked(element, false);
                            MessageDialog
                                    .openInformation(
                                            getShell(),
                                            Messages.DeployProjectServerSelectionPage_unselectableElementMessage_title,
                                            Messages.DeployProjectServerSelectionPage_unselectableElementMessage_message);
                        }
                    } catch (ConnectionException e) {
                        DeployUIActivator.getDefault().getLogger().error(e);
                    }
                }
                setPageComplete(validatePage(true));
            }
        });
        setPageComplete(validatePage(false));
        setControl(composite);

    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

    /**
     * @param contextServerGroup
     *            the contextServerGroup to set
     */
    public void setContextServerGroup(ServerGroup contextServerGroup) {
        this.contextServerGroup = contextServerGroup;
    }

    /**
     * @return the contextServerGroup
     */
    public ServerGroup getContextServerGroup() {
        return contextServerGroup;
    }

    /* package */List<ModuleDeploymentWizardNode> getSelectedWizardNodes() {
        List<ModuleDeploymentWizardNode> nodes = new ArrayList<ModuleDeploymentWizardNode>();
        Object[] checkedServerNodes = serversTreeViewer.getCheckedElements();
        for (Object object : checkedServerNodes) {
            if (object instanceof ModuleDeploymentWizardNode) {
                nodes.add((ModuleDeploymentWizardNode) object);
            }
        }
        return nodes;
    }

    private boolean validatePage(boolean showMessages) {
        setMessage(null);
        setErrorMessage(null);
        // errors
        if (projectsTreeViewer.getCheckedElements().length == 0) {
            if (showMessages) {
                setErrorMessage(Messages.DeployProjectServerSelectionPage_noProjectSelected_message);
            }
            return false;
        }
        if (serversTreeViewer.getCheckedElements().length == 0) {
            if (showMessages) {
                setErrorMessage(Messages.DeployProjectServerSelectionPage_noServerChosen_message);
            }
            return false;
        }

        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        return true;
    }

}
