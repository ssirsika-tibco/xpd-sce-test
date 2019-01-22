/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties;

import java.util.Collection;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table.DBTabSectionParameterTable;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * The database tab section. This includes the stored procedure data mapping
 * table.
 * 
 * @author njpatel
 */
public class DatabaseTabSection extends AbstractDatabaseSection implements
        ISelectionChangedListener, IPluginContribution {

    private DBTabSectionParameterTable parameterTable;

    private ISelection lastSelection;

    private boolean listenForSelectionChange = true;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite container =
                (Composite) super.doCreateControls(parent, toolkit);

        // Parameter table
        createLabel(toolkit,
                container,
                Messages.DatabaseTabSection_ParametersLabel);
        createTableControls(container, toolkit);

        return container;
    }

    /**
     * Create the parameter table - this will be created in the parent composite
     * using the same layout - <code>GridLayout</code>.
     * 
     * @param parent
     * @param toolkit
     */
    private void createTableControls(Composite parent, XpdFormToolkit toolkit) {

        parameterTable =
                new DBTabSectionParameterTable(parent, toolkit,
                        getEditingDomain());
        parameterTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        parameterTable.getTableViewer().addSelectionChangedListener(this);

    }

    @Override
    protected void doRefresh() {
        super.doRefresh();
        
        // Update the table viewer
        if (parameterTable != null) {
            // Don't listen for selection change
            listenForSelectionChange = false;
            parameterTable.getTableViewer().refresh();
            // Select the last selection
            if (lastSelection != null) {
                parameterTable.getTableViewer().setSelection(lastSelection);
            }

            // Start listening for selection change
            listenForSelectionChange = true;
        }
    }

    @Override
    public boolean select(Object toTest) {
        boolean ret = super.select(toTest);

        if (ret) {
            if (toTest instanceof TaskEditPart) {
                TaskEditPart editPart = (TaskEditPart) toTest;
                TaskAdapter taskAdapter =
                        (TaskAdapter) editPart.getModelAdapter();

                if (taskAdapter != null) {
                    String extensionName =
                            taskAdapter.getTaskImplementationExtensionId();

                    ret =
                            (extensionName != null && extensionName
                                    .equals(NativeServicesConsts.DB_SERVICE_ID));
                } else {
                    ret = false;
                }
            } else {
                ret = false;
            }
        }

        return ret;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        if (parameterTable != null) {
            parameterTable.getViewer().setInput(getTaskServiceInput());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
     * org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event) {
        if (listenForSelectionChange) {

            IStructuredSelection selection =
                    (IStructuredSelection) event.getSelection();

            // Store the current selection
            lastSelection = selection;

        }

    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "DatabaseSection"; //$NON-NLS-1$
    }

    /**
     * Contributing plug-in identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     */
    public String getPluginId() {
        return NativeServicesActivator.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#getPluginContributon()
     */
    @Override
    public IPluginContribution getPluginContributon() {
        return this;
    }
}
