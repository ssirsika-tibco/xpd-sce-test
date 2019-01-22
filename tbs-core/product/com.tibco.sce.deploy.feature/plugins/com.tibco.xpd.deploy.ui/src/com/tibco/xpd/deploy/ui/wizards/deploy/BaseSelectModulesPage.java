/*
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.misc.AutoTreeCheckStateListener;

/**
 * Base wizard page for selecting modules to be deployed.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class BaseSelectModulesPage extends AbstractXpdWizardPage {

    private static final String MODULE_COLUMN = "Module"; //$NON-NLS-1$

    private static final String DEPLOYMENT_POLICY_COLUMN = "DeploymentPolicy"; //$NON-NLS-1$

    private final ArrayList<String> deployPolices;

    private CheckboxTreeViewer modulesViewer;

    private ComboBoxCellEditor deploymentPolicyEditor;

    private final Map<String, WorkspaceModule> workspaceModules;

    private List<IProject> contextProjects = Collections.emptyList();

    private final ArrayList<IProject> cachedSelectedProjects =
            new ArrayList<IProject>();

    private final ArrayList<String> filterKinds;

    private ViewerFilter[] moduleFilters;

    public BaseSelectModulesPage(ArrayList<String> filterKinds) {
        super("ModulesSelection"); //$NON-NLS-1$
        this.filterKinds = filterKinds;
        this.moduleFilters = null;
        setTitle(Messages.BaseSelectModulesPage_SelectModulesTitle);
        setDescription(Messages.BaseSelectModulesPage_SelectModulesMessage);
        deployPolices = new ArrayList<String>();
        deployPolices.add(DeployUIConstants.ON_REQUEST_DEPLOY_POLICY_LABEL);
        deployPolices.add(DeployUIConstants.ON_SAVE_DEPLOY_POLICY_LABEL);
        workspaceModules = new HashMap<String, WorkspaceModule>();
    }

    /**
     * Custom filters for determining what projects should be shown.
     * 
     * @param moduleFilters
     *            the viewer filters.
     */
    public BaseSelectModulesPage(ViewerFilter[] moduleFilters) {
        this((ArrayList<String>) null);
        this.moduleFilters = moduleFilters;
    }

    @Override
    public void setPageComplete(boolean complete) {
        super.setPageComplete(complete);
        // getContainer().updateButtons();
    }

    public void createControl(Composite parent) {
        initWorkspaceModules();
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(gridLayout);

        Group modulesGroup = new Group(composite, SWT.NULL);
        modulesGroup
                .setText(Messages.BaseSelectModulesPage_SupportedModulesLabel);
        GridData gridData2 = new GridData(GridData.FILL_BOTH);
        modulesGroup.setLayoutData(gridData2);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 2;
        modulesGroup.setLayout(paramGroupLayout);

        Tree modulesTable =
                new Tree(modulesGroup, SWT.SINGLE | SWT.BORDER
                        | SWT.FULL_SELECTION | SWT.CHECK);
        GridData gridData3 = new GridData(GridData.FILL_BOTH);
        gridData3.horizontalSpan = 2;
        modulesTable.setLayoutData(gridData3);

        modulesTable.setHeaderVisible(true);
        modulesTable.setLinesVisible(true);

        TreeColumn projectColumn = new TreeColumn(modulesTable, SWT.NONE);
        projectColumn.setText(Messages.BaseSelectModulesPage_ProjectLabel);
        projectColumn.setWidth(300);

        TreeColumn deploymentPolicyColumn =
                new TreeColumn(modulesTable, SWT.NONE);
        deploymentPolicyColumn
                .setText(Messages.BaseSelectModulesPage_DeploymentPolicyLabel);
        deploymentPolicyColumn.setWidth(110);

        modulesViewer = new ContainerCheckedTreeViewer(modulesTable);
        modulesViewer.setColumnProperties(new String[] { MODULE_COLUMN,
                DEPLOYMENT_POLICY_COLUMN });
        deploymentPolicyEditor =
                new ComboBoxCellEditor(modulesTable, deployPolices
                        .toArray(new String[deployPolices.size()]),
                        SWT.READ_ONLY);

        WorkbenchContentProvider contentProvider = getContentProvider();

        CellEditor[] cellEditors =
                new CellEditor[] { null, deploymentPolicyEditor };
        modulesViewer.setCellEditors(cellEditors);
        modulesViewer.setCellModifier(new DeploymentModulesCellModifier());
        modulesViewer.setContentProvider(contentProvider);
        modulesViewer.setLabelProvider(getModulesLabelProvider());
        modulesViewer.addCheckStateListener(new AutoTreeCheckStateListener(
                modulesViewer));
        modulesViewer.addCheckStateListener(new ICheckStateListener() {
            public void checkStateChanged(CheckStateChangedEvent event) {
                modulesViewer.refresh();
                setPageComplete(validatePage(true));
            }
        });
        if (moduleFilters != null) {
            modulesViewer.setFilters(moduleFilters);
        }

        // Sorts using text returned by getText() method from the LabelProvider
        // using default collator.
        modulesViewer.setSorter(new ViewerSorter());

        modulesViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());

        // Select All button
        final Button btnSelectAll = new Button(modulesGroup, SWT.PUSH);
        btnSelectAll.setText("Select All");
        btnSelectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                modulesViewer.setAllChecked(true);
                modulesViewer.refresh();
                setPageComplete(validatePage(true));
            }
        });

        // Deselect All button
        final Button btnUnselectAll = new Button(modulesGroup, SWT.PUSH);
        btnUnselectAll.setText("Deselect All");
        btnUnselectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                modulesViewer.setAllChecked(false);
                modulesViewer.refresh();
                setPageComplete(validatePage(true));
            }
        });

        initializePageDefaults();
        setPageComplete(validatePage(false));
        setControl(composite);
    }

    protected WorkbenchContentProvider getContentProvider() {
        WorkbenchContentProvider contentProvider;
        if (filterKinds != null) {
            contentProvider = new BaseDeployModuleContentProvider(filterKinds);
        } else {
            // This content provider will show only project, but will also
            // prevent showing "+" sign in front of the project.
            contentProvider = new WorkbenchContentProvider() {
                private final Object[] EMPTY_ARRAY = new Object[0];

                @Override
                public boolean hasChildren(Object element) {
                    return (element instanceof IProject) ? false : super
                            .hasChildren(element);
                }

                @Override
                public Object[] getChildren(Object element) {
                    return (element instanceof IProject) ? EMPTY_ARRAY : super
                            .getChildren(element);
                }
            };
        }

        // if (((IpeXpdlModuleWizard) getWizard()).getServer().getConnection()
        // instanceof IpeConnection) {
        // double serverVersion = ((IpeConnection) ((IpeXpdlModuleWizard)
        // getWizard())
        // .getServer().getConnection()).getServerVersion();
        // contentProvider.setServerVersion(serverVersion);
        // }
        return contentProvider;
    }

    protected BaseModulesLabelProvider getModulesLabelProvider() {
        return new BaseModulesLabelProvider();
    }

    @SuppressWarnings("unchecked")
    private void initWorkspaceModules() {
        Server server = ((IDeployWizard) getWizard()).getServer();
        List<WorkspaceModule> modules = server.getWorkspaceModules();
        for (WorkspaceModule module : modules) {
            String srcPath = module.getWorkspaceSrcPath();
            workspaceModules.put(srcPath, module);
        }
    }

    private void initializePageDefaults() {

    }

    public Collection<WorkspaceModule> getWorkspaceModules() {
        return workspaceModules.values();
    }

    protected boolean validatePage(boolean showMessages) {
        setMessage(null);
        setErrorMessage(null);
        // errors
        cachedSelectedProjects.clear();
        if (modulesViewer.getCheckedElements().length == 0) {
            if (showMessages) {
                setErrorMessage(Messages.BaseSelectModulesPage_SelectModulesMessage);
            }
            return false;
        } else {
            Object[] checkedElements = modulesViewer.getCheckedElements();
            for (int i = 0; i < checkedElements.length; i++) {
                if (checkedElements[i] instanceof IProject) {
                    cachedSelectedProjects.add((IProject) checkedElements[i]);
                }
            }
        }
        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        return true;
    }

    // protected abstract IFile getCorrespondingModuleSourceFile(Object
    // element);

    private class DeploymentModulesCellModifier implements ICellModifier {

        public boolean canModify(Object element, String property) {
            return DEPLOYMENT_POLICY_COLUMN.equals(property)
                    && element instanceof IProject
                    && modulesViewer.getChecked(element);
        }

        public Object getValue(Object element, String property) {
            if (DEPLOYMENT_POLICY_COLUMN.equals(property)
                    && element instanceof IProject) {
                if (workspaceModules.keySet().contains(((IProject) element)
                        .getFullPath().toString())) {
                    return Integer.valueOf(1);
                }
                return Integer.valueOf(0);
            }
            return null;
        }

        public void modify(Object element, String property, Object value) {
            if (DEPLOYMENT_POLICY_COLUMN.equals(property)) {
                if (element instanceof TreeItem) {
                    Object data = ((TreeItem) element).getData();
                    if (data instanceof IProject) {
                        IProject project = (IProject) data;
                        String srcModulePath = project.getFullPath().toString();
                        if (value.equals(Integer.valueOf(0))) {
                            workspaceModules.remove(srcModulePath);
                        } else if (value.equals(Integer.valueOf(1))) {
                            if (!workspaceModules.keySet()
                                    .contains(srcModulePath)) {
                                // create WorkspaceModule
                                WorkspaceModule module =
                                        DeployFactory.eINSTANCE
                                                .createWorkspaceModule();
                                module.setName(project.getName());
                                module.setWorkspaceSrcPath(srcModulePath);
                                module.setDirty(false);
                                workspaceModules.put(srcModulePath, module);

                            }
                        } else {
                            throw new IllegalStateException();
                        }
                        modulesViewer.refresh();
                    }
                }
            }
        }
    }

    public class BaseModulesLabelProvider extends WorkbenchLabelProvider
            implements ITableLabelProvider {

        public Image getColumnImage(Object element, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return getImage(element);
            default:
                break;
            }
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return getText(element);
            case 1:
                if (element instanceof IResource) {
                    IResource resourceModule = (IResource) element;
                    if (resourceModule != null) {
                        if (workspaceModules.keySet().contains(resourceModule
                                .getFullPath().toString())) {
                            return DeployUIConstants.ON_SAVE_DEPLOY_POLICY_LABEL;
                        }
                    }
                    return DeployUIConstants.ON_REQUEST_DEPLOY_POLICY_LABEL;
                } else {
                    return "";
                }
            default:
                break;
            }
            return "";
        }
    }

    public List<IProject> getSelectedProjects() {
        // Find selected projects
        List<IProject> selectedProjects = new ArrayList<IProject>();
        if (modulesViewer != null) {
            selectedProjects = cachedSelectedProjects;
        } else {
            selectedProjects = contextProjects;
        }
        return selectedProjects;
    }

    public void setContextProjects(List<IProject> projects) {
        contextProjects = new ArrayList<IProject>(projects);
        if (modulesViewer != null) {
            modulesViewer.setCheckedElements(projects.toArray());
        }
    }
}
