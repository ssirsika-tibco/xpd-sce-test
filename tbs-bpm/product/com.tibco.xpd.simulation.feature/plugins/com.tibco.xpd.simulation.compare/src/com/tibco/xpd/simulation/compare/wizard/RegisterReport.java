/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.wizard;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.resources.ExtendedFileFieldEditor;
import com.tibco.xpd.simulation.compare.ComparePlugin;
import com.tibco.xpd.simulation.compare.Messages;
import com.tibco.xpd.simulation.compare.editor.SimulationReport;

/**
 * Wizard for registering a new report type or editing an existing one.
 * 
 * @author nwilson
 */
public class RegisterReport extends Wizard implements IImportWizard {
    /** The wizard page for the report details. */
    private ReportDetailsPage reportDetailsPage;

    /** The report descriptor. */
    private SimulationReport report;

    /**
     * Constructor for registering a new report.
     */
    public RegisterReport() {
        super();
        setWindowTitle(Messages.RegisterReport_NewReportTitle);
        reportDetailsPage = new ReportDetailsPage();
    }

    /**
     * Constructor for editing an existing report.
     * 
     * @param report The report descriptor to edit.
     */
    public RegisterReport(final SimulationReport report) {
        this();
        this.report = report;
    }

    /**
     * @param workbench The workbench for this wizard. Not used.
     * @param selection ignored.
     * @see org.eclipse.ui.IWorkbenchWizard#init( org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    public void addPages() {
        addPage(reportDetailsPage);
    }

    /**
     * @return true if all of the details are valid, otherwise false.
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public boolean performFinish() {
        String name = reportDetailsPage.getName();
        String file = reportDetailsPage.getFile();
        if (report == null) {
            report = new SimulationReport();
            report.setName(name);
            report.setReportFile(new Path(file));
            report.setReportTypeId("birtReport"); //$NON-NLS-1$
            ComparePlugin.getDefault().addReport(report);
        } else {
            ComparePlugin.getDefault().removeReport(report);
            report.setName(name);
            report.setReportFile(new Path(file));
            ComparePlugin.getDefault().addReport(report);
        }
        return true;
    }

    /**
     * The report details wizard page.
     * 
     * @author nwilson
     */
    class ReportDetailsPage extends AbstractXpdWizardPage implements ModifyListener {
        /** The number of columns on this page. */
        private static final int REPORT_DETAILS_COLUMN_COUNT = 4;

        /** The name of the report. */
        private Text name;

        /** The file path of the report template. */
        private ExtendedFileFieldEditor file;

        /**
         * Constructor.
         */
        protected ReportDetailsPage() {
            super(Messages.RegisterReport_ReportDetails);
            setTitle(Messages.RegisterReport_ReportDetailsTitle);
            setDescription(Messages.RegisterReport_ReportDetailsMessage);
        }

        /**
         * @param parent The parent composite for this page.
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(
         *      org.eclipse.swt.widgets.Composite)
         */
        public void createControl(final Composite parent) {
            Composite control = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout();
            layout.numColumns = REPORT_DETAILS_COLUMN_COUNT;
            control.setLayout(layout);

            Label aliasLabel = new Label(control, SWT.NONE);
            aliasLabel.setText(Messages.RegisterReport_NameLabel);

            name = new Text(control, SWT.SINGLE | SWT.BORDER);
            GridData nameGridData = new GridData();
            nameGridData.grabExcessHorizontalSpace = true;
            nameGridData.horizontalAlignment = SWT.FILL;
            nameGridData.horizontalSpan = REPORT_DETAILS_COLUMN_COUNT - 1;
            name.setLayoutData(nameGridData);
            name.addModifyListener(this);

            file =
                    new ExtendedFileFieldEditor("ReportFile", Messages.RegisterReport_ReportFileLabel, //$NON-NLS-1$
                            control);
            file.getTextControl(control).addModifyListener(this);

            if (report != null) {
                name.setText(report.getName());
                file.setStringValue(report.getReportFile().toString());
            }
            updatePageComplete();
            setControl(control);
        }

        /**
         * @return The file path for the report template.
         */
        public String getFile() {
            return file.getStringValue();
        }

        /**
         * @return The name of the report.
         * @see org.eclipse.jface.wizard.WizardPage#getName()
         */
        public String getName() {
            return name.getText();
        }

        /**
         * Sets the page complete status.
         */
        private void updatePageComplete() {
            setPageComplete(name.getText().length() > 0
                    && file.getStringValue().length() != 0);
        }

        /**
         * @param e The modify event.
         * @see org.eclipse.swt.events.ModifyListener#modifyText(
         *      org.eclipse.swt.events.ModifyEvent)
         */
        public void modifyText(final ModifyEvent e) {
            updatePageComplete();
        }
    }
}
