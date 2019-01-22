/*

 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.

 */

package com.tibco.xpd.xsd.ui.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xsd.ui.internal.Messages;
import com.tibco.xpd.xsd.ui.labelproviders.ElementsLabelProvider;
import com.tibco.xpd.xsd.ui.models.ElementModel;

/**
 * Wizard page to select the incoming and outgoing elements that are contained
 * inside the xsd that has been provided on initialisation. User needs to select
 * at least 1 of the elements for each incoming / outgoing lists.
 * 
 * @author glewis
 */
public class DocumentMappingPage extends AbstractXpdWizardPage {

    private Group incomingGroup;

    private TableViewer incomingElementsViewer = null;

    private Group outgoingGroup;

    private TableViewer outgoingElementsViewer = null;

    private List<ElementModel> incomingModelList;
    private List<ElementModel> outgoingModelList;

    /**
     * Constructor.
     */
    public DocumentMappingPage() {
        super(Messages.DocumentMappingPage_Title);
        setTitle(Messages.DocumentMappingPage_Title);
        setDescription(Messages.DocumentMappingPage_Description);
    }

    /**
     * @param parent
     *            The parent to add the controls to.
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        control.setLayout(layout);

        // create incoming group and its controls
        incomingGroup = new Group(control, SWT.NONE);
        incomingGroup.setText(Messages.DocumentMappingPage_Incoming_label);
        incomingGroup.setLayout(new GridLayout(3, false));
        incomingGroup.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_HORIZONTAL));
        createIncomingGroupControls();

        // create outgoing group and its controls
        outgoingGroup = new Group(control, SWT.NONE);
        outgoingGroup.setText(Messages.DocumentMappingPage_Outgoing_label);
        outgoingGroup.setLayout(new GridLayout(3, false));
        outgoingGroup.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_HORIZONTAL));
        createOutgoingGroupControls();

        setControl(control);
        updatePageComplete();
    }

    /**
     * Creates the incoming list control
     */
    private void createIncomingGroupControls() {
        // create a list containing the incoming elements
        Table table = new Table(incomingGroup, SWT.MULTI | SWT.BORDER
                | SWT.FULL_SELECTION);
        table.setHeaderVisible(true);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        table.setLayoutData(gridData);

        TableColumn column = new TableColumn(table, SWT.NONE);
        column.setText(Messages.DocumentMappingPage_Column_Name_label);
        column.setWidth(150);

        column = new TableColumn(table, SWT.NONE);
        column.setText(Messages.DocumentMappingPage_Column_Type_label);
        column.setWidth(150);

        // create the incoming elements viewer
        incomingElementsViewer = new TableViewer(table);
        incomingElementsViewer
                .setContentProvider(new IStructuredContentProvider() {
                    public void inputChanged(Viewer viewer, Object oldInput,
                            Object newInput) {
                    }

                    public void dispose() {
                    }

                    public Object[] getElements(Object inputElement) {
                        if (inputElement instanceof Collection<?>) {
                            return ((Collection<?>) inputElement).toArray();
                        }
                        return null;
                    }
                });

        incomingElementsViewer.setLabelProvider(new ElementsLabelProvider());

        incomingElementsViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    public void selectionChanged(SelectionChangedEvent event) {
                        // always keep up to date the latest selection so wizard
                        // can access it when we finish
                        IStructuredSelection tempSelection = (IStructuredSelection) event
                                .getSelection();
                        if (tempSelection.getFirstElement() != null
                                && tempSelection.getFirstElement() instanceof ElementModel) {
                            incomingModelList = tempSelection.toList();
                        }
                        updatePageComplete();
                    }
                });

        incomingElementsViewer.setInput(new ArrayList<ElementModel>());
    }

    /**
     * Creates the outgoing list control
     */
    private void createOutgoingGroupControls() {
        // create a list containing the outgoing elements
        Table table = new Table(outgoingGroup, SWT.MULTI | SWT.BORDER
                | SWT.FULL_SELECTION);
        table.setHeaderVisible(true);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        table.setLayoutData(gridData);

        TableColumn column = new TableColumn(table, SWT.NONE);
        column.setText(Messages.DocumentMappingPage_Column_Name_label);
        column.setWidth(150);

        column = new TableColumn(table, SWT.NONE);
        column.setText(Messages.DocumentMappingPage_Column_Type_label);
        column.setWidth(150);

        // create the outgoing elements viewer
        outgoingElementsViewer = new TableViewer(table);

        outgoingElementsViewer
                .setContentProvider(new IStructuredContentProvider() {
                    public void inputChanged(Viewer viewer, Object oldInput,
                            Object newInput) {
                    }

                    public void dispose() {
                    }

                    public Object[] getElements(Object inputElement) {
                        if (inputElement instanceof Collection<?>) {
                            return ((Collection<?>) inputElement).toArray();
                        }
                        return null;
                    }
                });

        outgoingElementsViewer.setLabelProvider(new ElementsLabelProvider());

        outgoingElementsViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        // always keep up to date the latest selection so wizard
                        // can access it when we finish
                        IStructuredSelection tempSelection = (IStructuredSelection) event
                                .getSelection();
                        if (tempSelection.getFirstElement() != null
                                && tempSelection.getFirstElement() instanceof ElementModel) {
                            outgoingModelList = tempSelection.toList();
                        }
                        updatePageComplete();
                    }
                });

        outgoingElementsViewer.setInput(new ArrayList<ElementModel>());
    }

    /**
     * Sets the page complete status based on user input.
     */
    private void updatePageComplete() {
        boolean complete = false;

        setErrorMessage(null);

        if (incomingElementsViewer.getTable().getItemCount() > 0
                && incomingElementsViewer.getTable().getSelectionCount() > 0) {
            if (outgoingElementsViewer.getTable().getItemCount() > 0
                    && outgoingElementsViewer.getTable().getSelectionCount() > 0) {
                complete = true;
            }
        }
        if (incomingElementsViewer.getTable().getItemCount() == 0) {
            if (outgoingElementsViewer.getTable().getItemCount() > 0
                    && outgoingElementsViewer.getTable().getSelectionCount() > 0) {
                complete = true;
            }
        }
        if (outgoingElementsViewer.getTable().getItemCount() > 0
                && outgoingElementsViewer.getTable().getSelectionCount() > 0) {
            if (incomingElementsViewer.getTable().getItemCount() > 0
                    && incomingElementsViewer.getTable().getSelectionCount() > 0) {
                complete = true;
            }
        }
        if (outgoingElementsViewer.getTable().getItemCount() == 0) {
            if (incomingElementsViewer.getTable().getItemCount() > 0
                    && incomingElementsViewer.getTable().getSelectionCount() > 0) {
                complete = true;
            }
        }

        setPageComplete(complete);
    }

    /**
     * Refresh the page.
     * 
     * @param incomingElements
     *            incoming schema elements
     * @param outgoingElements
     *            outgoing schema elements
     */
    public void doRefresh(List<ElementModel> incomingElements,
            List<ElementModel> outgoingElements) {
        if (incomingElements != null) {
            incomingElementsViewer.setInput(incomingElements);
        }

        if (outgoingElements != null) {
            outgoingElementsViewer.setInput(outgoingElements);
        }
    }

    /**
     * Get the incoming selection.
     * 
     * @return
     */
    public List<ElementModel> getIncomingSelection() {
        return incomingModelList;
    }

    /**
     * Get the outgoing selection.
     * 
     * @return
     */
    public List<ElementModel> getOutgoingSelection() {
        return outgoingModelList;
    }
}
