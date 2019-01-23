/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.simulation.compare.ComparePlugin;
import com.tibco.xpd.simulation.compare.IReportListener;
import com.tibco.xpd.simulation.compare.Messages;
import com.tibco.xpd.simulation.compare.editor.SimulationReport;
import com.tibco.xpd.simulation.compare.editor.SimulationReportsContentProvider;
import com.tibco.xpd.simulation.compare.editor.SimulationReportsLabelProvider;
import com.tibco.xpd.simulation.compare.wizard.RegisterReport;

/**
 * Preference page for managing the list of available reports.
 * 
 * @author nwilson
 */
public class ReportManagementPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage, SelectionListener, IReportListener {

    /** Delete confirmation message. */
    private static final String CONFIRM_DELETE =
            Messages.ReportManagementPreferencePage_ConfirmDeleteMessage;

    /** Default width for the reports table columns. */
    private static final int DEFAULT_TABLE_COLUMN_WIDTH = 200;

    /** width hint for the reports table. */
    private static final int REPORTS_TABLE_WIDTH = 200;

    /** Height hint for the reports table. */
    private static final int REPORTS_TABLE_HEIGHT = 100;

    /** Number of columns in the page layout. */
    private static final int COLUMN_COUNT = 4;

    /** The table viewer used to display the list of reports. */
    private TableViewer reportTable;

    /** The table used to display the list of reports. */
    private Table table;

    /** Button to import a new report. */
    private Button importButton;

    /** Button to edit a selected report. */
    private Button editButton;

    /** Button to delete a selected report. */
    private Button deleteButton;

    /** Button to restore the default reports. */
    private Button restoreButton;

    /** The currently selected report. */
    private SimulationReport selectedReport;

    /**
     * Constructor.
     */
    public ReportManagementPreferencePage() {
        super();
        noDefaultAndApplyButton();
    }

    /**
     * @param parent The parent composite for this page.
     * @return The preference page control.
     * @see org.eclipse.jface.preference.PreferencePage#createContents(
     *      org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(final Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout grid = new GridLayout();
        grid.numColumns = COLUMN_COUNT;
        grid.marginWidth = 0;
        grid.marginHeight = 0;
        composite.setLayout(grid);

        table =
                new Table(composite, SWT.SINGLE | SWT.BORDER
                        | SWT.FULL_SELECTION);
        table.setHeaderVisible(true);
        GridData reportsGridData = new GridData();
        reportsGridData.heightHint = REPORTS_TABLE_HEIGHT;
        reportsGridData.widthHint = REPORTS_TABLE_WIDTH;
        reportsGridData.grabExcessHorizontalSpace = true;
        reportsGridData.verticalAlignment = SWT.FILL;
        reportsGridData.horizontalAlignment = SWT.FILL;
        reportsGridData.horizontalSpan = COLUMN_COUNT;
        table.setLayoutData(reportsGridData);
        table.addSelectionListener(this);

        reportTable = new TableViewer(table);
        reportTable.setContentProvider(new SimulationReportsContentProvider());
        reportTable.setLabelProvider(new SimulationReportsLabelProvider());

        TableColumn nameColumn = new TableColumn(table, SWT.NONE);
        nameColumn.setText(Messages.ReportManagementPreferencePage_NameColumnLabel);
        nameColumn.setWidth(DEFAULT_TABLE_COLUMN_WIDTH);
        TableColumn typeColumn = new TableColumn(table, SWT.NONE);
        typeColumn.setText(Messages.ReportManagementPreferencePage_TypeColumnLabel);
        typeColumn.setWidth(DEFAULT_TABLE_COLUMN_WIDTH);

        importButton = new Button(composite, SWT.PUSH);
        importButton.setText(Messages.ReportManagementPreferencePage_ImportLabel);
        importButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(final SelectionEvent e) {
                importReport();
            }

            public void widgetDefaultSelected(final SelectionEvent e) {
            }
        });

        editButton = new Button(composite, SWT.PUSH);
        editButton.setText(Messages.ReportManagementPreferencePage_EditLabel);
        editButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(final SelectionEvent e) {
                editReport();
            }

            public void widgetDefaultSelected(final SelectionEvent e) {
            }
        });

        deleteButton = new Button(composite, SWT.PUSH);
        deleteButton.setText(Messages.ReportManagementPreferencePage_DeleteLabel);
        deleteButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(final SelectionEvent e) {
                deleteReport();
            }

            public void widgetDefaultSelected(final SelectionEvent e) {
            }
        });

        restoreButton = new Button(composite, SWT.PUSH);
        restoreButton.setText(Messages.ReportManagementPreferencePage_RestoreDefaultsLabel);
        restoreButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(final SelectionEvent e) {
                restoreDefaultReports();
            }

            public void widgetDefaultSelected(final SelectionEvent e) {
            }
        });

        updateReports();

        return composite;
    }

    /**
     * @param workbench The workbench that this page is associated with.
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(final IWorkbench workbench) {
        ComparePlugin.getDefault().addReportListener(this);
    }

    /**
     * @param e The selection event.
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(
     *      org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(final SelectionEvent e) {
        updateReportSelection();
    }

    /**
     * @param e The selection event.
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(
     *      org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(final SelectionEvent e) {
    }

    /**
     * Refreshes the list of reports.
     */
    private void updateReports() {
        reportTable.setInput(ComparePlugin.getDefault().getReports());
    }

    /**
     * Updates the currently selected report.
     */
    private void updateReportSelection() {
        IStructuredSelection selection =
                (IStructuredSelection) reportTable.getSelection();
        if (selection.size() == 1) {
            Object object = selection.getFirstElement();
            if (object instanceof SimulationReport) {
                selectedReport = (SimulationReport) object;
            }
        } else {
            selectedReport = null;
        }
    }

    /**
     * Imports a new report.
     */
    private void importReport() {
        IWizard wizard = new RegisterReport();
        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.open();
    }

    /**
     * Edits the selected report.
     */
    private void editReport() {
        if (selectedReport != null) {
            IWizard wizard = new RegisterReport(selectedReport);
            WizardDialog dialog = new WizardDialog(getShell(), wizard);
            dialog.open();
        }
    }

    /**
     * Deletes the selected report.
     */
    private void deleteReport() {
        if (selectedReport != null) {
            if (MessageDialog.openConfirm(getShell(), Messages.ReportManagementPreferencePage_DeleteReportLabel,
                    CONFIRM_DELETE)) {
                ComparePlugin.getDefault().removeReport(selectedReport);
            }
        }
    }

    /**
     * Restores the default reports to the list.
     */
    private void restoreDefaultReports() {
        ComparePlugin.getDefault().addDefaultReports();
        ComparePlugin.getDefault().loadReports();
    }

    /**
     * @see com.tibco.xpd.simulation.compare.IReportListener#reportChange()
     */
    public void reportChange() {
        updateReports();
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#dispose()
     */
    public void dispose() {
        ComparePlugin.getDefault().removeReportListener(this);
        super.dispose();
    }
}
