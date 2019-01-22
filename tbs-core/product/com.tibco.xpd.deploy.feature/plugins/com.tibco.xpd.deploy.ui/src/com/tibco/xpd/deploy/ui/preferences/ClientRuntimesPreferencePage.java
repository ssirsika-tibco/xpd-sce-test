/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.preferences;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.components.ViewerWithButtonsEditor;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Preference page with defined server Client Runtimes.
 * <p>
 * <i>Created: 30 November 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ClientRuntimesPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {

    private static final String COLUMN_1 = "Column1"; //$NON-NLS-1$

    private static final String COLUMN_2 = "Column2"; //$NON-NLS-1$

    private TableViewer paramsViewer;

    public ClientRuntimesPreferencePage() {
        super();
        // setPreferenceStore(DeployUIActivator.getDefault().getPreferenceStore());
        noDefaultAndApplyButton();
        setDescription(Messages.ClientRuntimesPreferencePage_longdesc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent) {

        // Main Composite
        Composite runtimeControl = new Composite(parent, SWT.NONE);

        // Viewer & viewer control
        Table runtimesViewerTable = new Table(runtimeControl, SWT.SINGLE
                | SWT.BORDER | SWT.FULL_SELECTION);
        runtimesViewerTable.setHeaderVisible(true);
        runtimesViewerTable.setLinesVisible(true);

        org.eclipse.swt.widgets.TableColumn sourceParametersColumn = new TableColumn(
                runtimesViewerTable, SWT.NONE);
        sourceParametersColumn
                .setText(Messages.ClientRuntimesPreferencePage_NameColumn_label);
        sourceParametersColumn.setWidth(200);

        TableColumn processParametersColumn = new TableColumn(
                runtimesViewerTable, SWT.NONE);
        processParametersColumn
                .setText(Messages.ClientRuntimesPreferencePage_TypeColumn_label);
        processParametersColumn.setWidth(200);

        paramsViewer = new TableViewer(runtimesViewerTable);
        paramsViewer.setColumnProperties(new String[] { COLUMN_1, COLUMN_2 });
        paramsViewer.setContentProvider(new ArrayContentProvider());
        paramsViewer.setLabelProvider(new ClientRuntimeLabelProvider());
        paramsViewer.setInput(DeployUIActivator.getServerManager()
                .getServerContainer().getRuntimes());

        ViewerWithButtonsEditor viewerEditor = new ViewerWithButtonsEditor(
                runtimeControl, paramsViewer);

        // Actions
        IContributionManager buttonsManager = viewerEditor.getButtonsManager();

        Action newAction = new NewViewerElementAction(paramsViewer,
                Messages.ClientRuntimesPreferencePage_New_action);
        Action editAction = new EditViewerElementAction(paramsViewer,
                Messages.ClientRuntimesPreferencePage_Edit_action);
        Action removeAction = new RemoveViewerElementAction(paramsViewer,
                Messages.ClientRuntimesPreferencePage_Remove_action);

        buttonsManager.add(newAction);
        buttonsManager.add(editAction);
        buttonsManager.add(removeAction);
        buttonsManager.update(true);
        viewerEditor.selectionChanged((IStructuredSelection) paramsViewer
                .getSelection());
        return runtimeControl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // do nothing
    }

    private class ClientRuntimeLabelProvider extends LabelProvider implements
            ITableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return ((com.tibco.xpd.deploy.Runtime) element).getName();
            case 1:
                return ((com.tibco.xpd.deploy.Runtime) element)
                        .getRuntimeType().getName();
            default:
                break;
            }
            return ""; //$NON-NLS-1$
        }

        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

    }

}