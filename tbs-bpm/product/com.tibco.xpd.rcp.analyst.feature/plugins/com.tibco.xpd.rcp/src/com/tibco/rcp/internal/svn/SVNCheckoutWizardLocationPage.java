/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.rcp.internal.svn;

import java.util.Arrays;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.tigris.subversion.subclipse.core.ISVNRepositoryLocation;
import org.tigris.subversion.subclipse.core.repo.RepositoryComparator;
import org.tigris.subversion.subclipse.ui.SVNUIPlugin;
import org.tigris.subversion.subclipse.ui.util.AdaptableList;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * copy of org.tigris.subversion.subclipse.ui.wizards.CheckoutWizardLocationPage
 * to make minor modifications
 * 
 * @author kupadhya
 * @since 21 Jan 2013
 */
public class SVNCheckoutWizardLocationPage extends WizardPage {
    private TableViewer table;

    private Button newButton;

    private Button existingButton;

    private ISVNRepositoryLocation result;

    public SVNCheckoutWizardLocationPage(String pageName, String title,
            ImageDescriptor titleImage) {
        super(pageName, title, titleImage);
        setPageComplete(false);
    }

    @Override
    public void createControl(Composite parent) {
        final SVNCheckOutWizard wizard = (SVNCheckOutWizard) getWizard();

        Composite outerContainer = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        outerContainer.setLayout(layout);
        outerContainer.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.HORIZONTAL_ALIGN_FILL));

        newButton = new Button(outerContainer, SWT.RADIO);
        newButton.setText(Messages.SVNCheckoutWizardLocationPage_new);

        existingButton = new Button(outerContainer, SWT.RADIO);
        existingButton.setText(Messages.SVNCheckoutWizardLocationPage_existing);

        table = createTable(outerContainer, 1);
        table.setContentProvider(new WorkbenchContentProvider());
        table.setLabelProvider(new WorkbenchLabelProvider());
        table.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                result =
                        (ISVNRepositoryLocation) ((IStructuredSelection) table
                                .getSelection()).getFirstElement();
                wizard.setLocation(result);
                setPageComplete(true);
            }
        });

        existingButton.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if (newButton.getSelection()) {
                    table.getTable().setEnabled(false);
                    result = null;
                } else {
                    table.getTable().setEnabled(true);
                    result =
                            (ISVNRepositoryLocation) ((IStructuredSelection) table
                                    .getSelection()).getFirstElement();
                    wizard.setLocation(result);
                }
                setPageComplete(newButton.getSelection()
                        || !table.getSelection().isEmpty());
            }
        });

        setMessage(Messages.SVNCheckoutWizardLocationPage_text);

        setControl(outerContainer);

        refreshLocations();
    }

    public void refreshLocations() {
        ISVNRepositoryLocation[] locations =
                SVNUIPlugin.getPlugin().getRepositoryManager()
                        .getKnownRepositoryLocations(null);
        Arrays.sort(locations, new RepositoryComparator());
        AdaptableList input = new AdaptableList(locations);
        table.setInput(input);
        if (locations.length == 0) {
            newButton.setSelection(true);
            existingButton.setSelection(false);
            table.getTable().setEnabled(false);
            setPageComplete(true);
        } else {
            existingButton.setSelection(true);
            newButton.setSelection(false);
            table.getTable().setEnabled(true);
        }
    }

    /**
     * Creates the table for the repositories
     */
    protected TableViewer createTable(Composite parent, int span) {
        Table table =
                new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER
                        | SWT.SINGLE | SWT.FULL_SELECTION);
        GridData data =
                new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL);
        data.horizontalSpan = span;
        table.setLayoutData(data);
        TableLayout layout = new TableLayout();
        layout.addColumnData(new ColumnWeightData(100, true));
        table.setLayout(layout);
        TableColumn col = new TableColumn(table, SWT.NONE);
        col.setResizable(true);

        return new TableViewer(table);
    }

    public ISVNRepositoryLocation getLocation() {
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    public boolean createNewLocation() {
        return newButton.getSelection();
    }

    @Override
    public boolean canFlipToNextPage() {
        SVNCheckOutWizard wizard = (SVNCheckOutWizard) getWizard();
        if (wizard != null) {
            return isPageComplete() && wizard.getNextPage(this, false) != null;
        }
        return super.canFlipToNextPage();
    }

}
