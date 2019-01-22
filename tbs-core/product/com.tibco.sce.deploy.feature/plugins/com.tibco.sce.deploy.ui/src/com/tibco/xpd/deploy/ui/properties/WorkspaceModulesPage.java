/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.components.ViewerAction;
import com.tibco.xpd.deploy.ui.components.ViewerWithButtonsEditor;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Page showing WorkspaceModules associated with server.
 * <p>
 * <i>Created: 28 August 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class WorkspaceModulesPage extends PropertyPage {

    private static final String COLUMN_1 = "Column1"; //$NON-NLS-1$

    private static final String COLUMN_2 = "Column2"; //$NON-NLS-1$

    private TableViewer modulesViewer;

    private Server selectedServer;

    private List<WorkspaceModule> workspaceModulesList;

    @SuppressWarnings("unchecked")
    @Override
    protected Control createContents(Composite parent) {
        selectedServer = (Server) (getElement()).getAdapter(Server.class);
        workspaceModulesList = new ArrayList<WorkspaceModule>(selectedServer
                .getWorkspaceModules());
        // Main Composite
        Composite mainComposite = new Composite(parent, SWT.NONE);

        // Viewer & viewer control
        Table runtimesViewerTable = new Table(mainComposite, SWT.SINGLE
                | SWT.BORDER | SWT.FULL_SELECTION);
        runtimesViewerTable.setHeaderVisible(true);
        runtimesViewerTable.setLinesVisible(true);

        TableColumn firstColumn = new TableColumn(runtimesViewerTable, SWT.NONE);
        firstColumn.setText(Messages.WorkspaceModulesPage_NameColumn_label);
        firstColumn.setWidth(200);

        TableColumn secondColumn = new TableColumn(runtimesViewerTable,
                SWT.NONE);
        secondColumn.setText(Messages.WorkspaceModulesPage_PathColumn_label);
        secondColumn.setWidth(200);

        modulesViewer = new TableViewer(runtimesViewerTable);
        modulesViewer.setColumnProperties(new String[] { COLUMN_1, COLUMN_2 });
        modulesViewer.setContentProvider(new ArrayContentProvider());
        modulesViewer.setLabelProvider(new WorkspaceModulesLabelProvider(
                DeployUIActivator.getServerManager().getAdapterFactory()));
        modulesViewer.setInput(workspaceModulesList);

        ViewerWithButtonsEditor viewerEditor = new ViewerWithButtonsEditor(
                mainComposite, modulesViewer);

        // Actions
        IContributionManager buttonsManager = viewerEditor.getButtonsManager();

        Action removeAction = new RemoveModulesViewerAction(modulesViewer,
                Messages.WorkspaceModulesPage_Remove_label);
        buttonsManager.add(removeAction);

        buttonsManager.update(true);
        viewerEditor.selectionChanged((IStructuredSelection) modulesViewer
                .getSelection());
        return mainComposite;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void performApply() {
        selectedServer.getWorkspaceModules().clear();
        selectedServer.getWorkspaceModules().addAll(workspaceModulesList);
        DeployUIActivator.getServerManager().saveServerContainer();
    }

    @Override
    public boolean performOk() {
        performApply();
        return true;
    }

    private class WorkspaceModulesLabelProvider extends
            AdapterFactoryLabelProvider implements ITableLabelProvider {

        public WorkspaceModulesLabelProvider(AdapterFactory adapterFactory) {
            super(adapterFactory);
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return ((WorkspaceModule) element).getName();
            case 1:
                return ((WorkspaceModule) element).getWorkspaceSrcPath();
            default:
                break;
            }
            return ""; //$NON-NLS-1$
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            if (columnIndex == 0) {
                return getImage(element);
            }
            return null;
        }
    }

    private class RemoveModulesViewerAction extends ViewerAction {

        public RemoveModulesViewerAction(StructuredViewer viewer, String text,
                ImageDescriptor image) {
            super(viewer, text, image);
        }

        public RemoveModulesViewerAction(StructuredViewer viewer, String text,
                int style) {
            super(viewer, text, style);
        }

        public RemoveModulesViewerAction(StructuredViewer viewer, String text) {
            super(viewer, text);
        }

        public RemoveModulesViewerAction(StructuredViewer viewer) {
            super(viewer);
        }

        @Override
        public void run() {
            IStructuredSelection selection = (IStructuredSelection) getViewer()
                    .getSelection();
            if (canRemoveSelection(selection)) {
                String message = String
                        .format(Messages.WorkspaceModulesPage_RemoveConfirmation_message);
                boolean positveDecision = MessageDialog.openQuestion(
                        getShell(),
                        Messages.WorkspaceModulesPage_RemoveConfirmation_title,
                        message);
                if (positveDecision) {
                    workspaceModulesList.removeAll(selection.toList());
                    getViewer().refresh();
                }
            }
        }

        @Override
        public void selectionChanged(IStructuredSelection selection) {
            setEnabled(canRemoveSelection(selection));
        }

        private boolean canRemoveSelection(IStructuredSelection selection) {
            if (selection.isEmpty())
                return false;
            boolean containsModulesOnly = true;
            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                if (!(iter.next() instanceof WorkspaceModule)) {
                    return false;
                }
            }
            return containsModulesOnly;
        }
    }

}
