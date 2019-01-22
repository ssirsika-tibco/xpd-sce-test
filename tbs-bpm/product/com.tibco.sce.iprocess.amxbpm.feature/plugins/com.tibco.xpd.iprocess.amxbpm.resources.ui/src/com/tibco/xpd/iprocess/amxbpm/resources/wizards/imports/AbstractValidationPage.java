/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.resources.wizards.imports;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;

import com.tibco.xpd.iprocess.amxbpm.converter.external.IProcessToStudioAMXBPMConverterFramework;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IBpmConversionValidator;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.ImportValidationError;
import com.tibco.xpd.iprocess.amxbpm.resources.IProcessAMXBPMResourcePlugin;
import com.tibco.xpd.iprocess.amxbpm.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;

/**
 * Copied from IPM com.tibco.xpd.ipm plugin. Page to validate import files and
 * report any potential errors. The finish button is only enabled if all import
 * files pass the validation. Currently this checks 1.) to ensure that any WSDL
 * operations refer to a WSDL file that is either embedded in the import file or
 * already available in the workspace.
 * 
 * Additional Checks to be implemented for Import and Convert iProcess to AMX
 * BPM XPDL includes 1) Check the given XPDL is a valid iProcess XPDL.- Error 2)
 * A referenced Subprocess is not imported as part of the same XPDL.- Warning 3)
 * If all processes being imported already exists in the workspace, raise error
 * message
 * "There are no new processes to import (all processes in source package already exist in workspace)"
 * 
 * @author aprasad
 * 
 */
public abstract class AbstractValidationPage extends WizardPage {

    private boolean validationComplete = false;

    private TableViewer valMsgTableViewer;

    /**
     * Keeps track if the validation has started.
     */
    private boolean hasValidationStarted = false;

    @Override
    public boolean isPageComplete() {

        if (isCurrentPage()) {
            /* Start Validation if not already started or complete */
            if (!hasValidationStarted && !validationComplete) {
                startValidation();
                hasValidationStarted = true;

            }
        } else {
            /* reset data if not current page. */
            setPageComplete(false);
            /*
             * Set validation is not Started or Complete, so that when next time
             * this page comes up, it triggers the validation
             */
            hasValidationStarted = false;
            validationComplete = false;
        }
        return super.isPageComplete();
    }

    /**
     * Constructor
     */
    protected AbstractValidationPage() {

        super(Messages.ValidateImportPage_WizardTitle);
        setTitle(Messages.ValidateImportPage_PageTitle);
        setPageComplete(false);
        hasValidationStarted = false;
        validationComplete = false;

    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {

        Composite containerComposite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        containerComposite.setLayout(layout);

        Label errorsLabel = new Label(containerComposite, SWT.NONE);

        errorsLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false, 2, 1));

        errorsLabel.setText(Messages.ValidateImportPage_ValidationErrorsLabel);

        Composite tableContainer = new Composite(containerComposite, SWT.NONE);
        tableContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true, 2, 1));
        layout = new GridLayout(2, false);
        containerComposite.setLayout(layout);

        valMsgTableViewer = new TableViewer(tableContainer);

        valMsgTableViewer.getControl().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true, 2, 1));

        valMsgTableViewer.setContentProvider(new ErrorContentProvider());
        valMsgTableViewer.getTable().setHeaderVisible(true);

        TableViewerColumn messageColumn =
                new TableViewerColumn(valMsgTableViewer, SWT.LEFT);

        messageColumn.setLabelProvider(new MessageCellLabelProvider());

        messageColumn.getColumn()
                .setText(Messages.ValidateImportPage_MessageColumnLabel);

        TableViewerColumn fileColumn =
                new TableViewerColumn(valMsgTableViewer, SWT.LEFT);

        fileColumn.getColumn()
                .setText(Messages.ValidateImportPage_FileColumnLabel);

        fileColumn.setLabelProvider(new FileCellLabelProvider());

        /* Set table Column width percentage Proportions to Message:File 70:30 */
        TableColumnLayout tableColumnLayout = new TableColumnLayout();
        int[] columnProportions = new int[] { 70, 30 };

        TableColumn[] columns = valMsgTableViewer.getTable().getColumns();

        for (int i = 0; i < columns.length; i++) {
            TableColumn column = columns[i];

            ColumnLayoutData cld =
                    new ColumnWeightData(columnProportions[i] * 100, 10, true);

            tableColumnLayout.setColumnData(column, cld);
        }

        tableContainer.setLayout(tableColumnLayout);
        tableContainer.layout(true, true);

        setControl(containerComposite);

        validationComplete = false;
    }

    /**
     * Gets the import/convert validator. Extending Classes should provide the
     * Validator instance which will implement {@link IBpmConversionValidator}
     * 
     * @return the Import/Convert validator
     */
    protected abstract IBpmConversionValidator getValidator();

    /**
     * 
     * @return the message to display on Wizard after the validation was
     *         successful.
     */
    protected abstract String getValidationSuccessMessage();

    /**
     * 
     * @return the message to display on Wizard after the validation completed
     *         with warnings.
     */
    protected abstract String getValidationWarningMessage();

    /**
     * Starts the Validation of the XPDLs.
     */
    private void startValidation() {

        /*
         * XPD-6361: Saket: Adding pre-validation timestamp.
         */
        IProcessToStudioAMXBPMConverterFramework
                .logTimeStampMsg(Messages.AbstractValidationPage_ValidationTimestampInfo,
                        Messages.LogInfoStatus_Started);

        /* get the validator for the conversion */
        final IBpmConversionValidator validator = getValidator();

        if (validator != null) {

            /*
             * XPD-6204: changes to run the validation as wizard operation and
             * not a Job
             */
            IRunnableWithProgress validateJob = new IRunnableWithProgress() {

                @Override
                public void run(IProgressMonitor monitor) {
                    try {

                        monitor.beginTask(Messages.ValidateImportPage_ValidatingTaskName,
                                1);
                        monitor.setTaskName(Messages.ValidateImportPage_ValidatingTaskName);

                        Display.getDefault().asyncExec(new Runnable() {

                            @Override
                            public void run() {
                                // validationProgressBar.setSelection(0);
                                // reset Errors Table and Error message.
                                valMsgTableViewer.setInput(null);
                                setErrorMessage(null);
                                setMessage(null);
                            }

                        });

                        final List<ImportValidationError> errorList =
                                new ArrayList<ImportValidationError>();

                        errorList.addAll(validator
                                .validate(new SubProgressMonitor(monitor, 1)));

                        Display.getDefault().asyncExec(new Runnable() {
                            @Override
                            public void run() {

                                validationComplete = true;
                                if (errorList == null || errorList.isEmpty()) {

                                    setPageComplete(true);
                                    setErrorMessage(null);
                                    setMessage(getValidationSuccessMessage());
                                    /*
                                     * XPD-6204, add success message in the
                                     * Message table
                                     */
                                    errorList.add(new ImportValidationError(
                                            null,
                                            getValidationSuccessMessage(),
                                            IStatus.INFO));

                                } else {

                                    if (!errorList.isEmpty()) {

                                        if (doesListContainErrorLevelMarkers(errorList)) {

                                            setErrorMessage(Messages.ValidateImportPage_ValidationErrorMessage);
                                        } else {

                                            setMessage(getValidationWarningMessage(),
                                                    IMessageProvider.WARNING);

                                            setPageComplete(true);
                                        }
                                    }
                                }
                                valMsgTableViewer.setInput(errorList);
                            }

                        });

                    } catch (OperationCanceledException e) {

                        Display.getDefault().syncExec(new Runnable() {

                            @Override
                            public void run() {

                                MessageDialog.open(MessageDialog.INFORMATION,
                                        getShell(),
                                        Messages.ConvertIProcessToAMXBPMWizard_UserCancelledOperationDialog_title,
                                        Messages.AbstractValidationPage_UserCancelledOperationDialog_desc,
                                        SWT.NONE);
                            }
                        });

                    } finally {

                        /*
                         * XPD-6361: Saket: Adding post-validation timestamp.
                         */
                        IProcessToStudioAMXBPMConverterFramework
                                .logTimeStampMsg(Messages.AbstractValidationPage_ValidationTimestampInfo,
                                        Messages.LogInfoStatus_Completed);

                        monitor.done();
                    }
                }

            };
            try {
                getContainer().run(true, true, validateJob);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof CoreException) {
                    CoreException exception = (CoreException) e.getCause();

                    ErrorDialog
                            .openError(getShell(),
                                    Messages.AbstractValidationPage_FailedToValMsgTitle,
                                    Messages.AbstractValidationPage_FailedToValMsgDesc,
                                    exception.getStatus());
                } else {
                    XpdResourcesUIActivator.getDefault().getLogger()
                            .error(e.getCause());
                }
            } catch (InterruptedException e) {

                ErrorDialog
                        .openError(getShell(),
                                Messages.AbstractValidationPage_FailedToValMsgTitle1,
                                Messages.AbstractValidationPage_FailedToValMsgDesc1,
                                new Status(
                                        IStatus.ERROR,
                                        IProcessAMXBPMResourcePlugin.IMG_WARNING_MARKER,
                                        e.getLocalizedMessage()));
            }
        }

    }

    /**
     * @param errorList
     * @return true if any of the <code>ImportValidationError</code>s' is of
     *         severity <code>IStatus.ERROR</code>
     */
    private boolean doesListContainErrorLevelMarkers(
            List<ImportValidationError> errorList) {

        for (ImportValidationError importValidationError : errorList) {

            if (IStatus.ERROR == importValidationError.getSeverity()) {
                return true;
            }

        }
        return false;
    }

    /**
     * ErrorContentProvider
     * 
     * @author rsomayaj
     * @since 3.3 (9 Sep 2010)
     */
    class ErrorContentProvider implements IStructuredContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            List<Object> elements = new ArrayList<Object>();

            if (inputElement instanceof List) {
                elements = (List<Object>) inputElement;

                /*
                 * XPD-6204: Sort Validation Messages entries in sequence to
                 * show Errors first followed by Warnings and then Errors and
                 * Warnings internally sorted alphabeticall and then on
                 * originating files
                 */
                Collections
                        .sort(elements,
                                new ImportValidationError.ValidationMessageComparator());
            }

            return elements.toArray();
        }

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    class MessageCellLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {

            Image img = null;
            String text = ""; //$NON-NLS-1$
            if (cell.getElement() instanceof ImportValidationError) {

                ImportValidationError error =
                        (ImportValidationError) cell.getElement();

                text = error.getMessage();
                if (IStatus.ERROR == error.getSeverity()) {

                    img =
                            IProcessAMXBPMResourcePlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(IProcessAMXBPMResourcePlugin.IMG_ERROR_MARKER);

                } else if (IStatus.WARNING == error.getSeverity()) {

                    img =
                            IProcessAMXBPMResourcePlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(IProcessAMXBPMResourcePlugin.IMG_WARNING_MARKER);

                } else if (IStatus.INFO == error.getSeverity()) {

                    img =
                            IProcessAMXBPMResourcePlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(IProcessAMXBPMResourcePlugin.IMG_INFO_MARKER);
                }

            }
            cell.setText(text);
            cell.setImage(img);
        }
    }

    class FileCellLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {
            String text = ""; //$NON-NLS-1$

            if (cell.getElement() instanceof ImportValidationError) {

                ImportValidationError error =
                        (ImportValidationError) cell.getElement();

                text = error.getXpdl();
            }
            cell.setText(text);
        }
    }

    /**
     * Returns specific help page context ID. Extending class can override this
     * method to provide specific context id.
     * 
     * @Note If not overridden, default implementation returns <code>null</code>
     * 
     * @return Help context id if available, otherwise <code>null</code>
     */
    protected String getHelpContextId() {
        return null;
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#performHelp() Extednig classes
     */
    @SuppressWarnings("restriction")
    @Override
    public void performHelp() {

        /*
         * XPD-6493: Saket: A wizard page needs to override
         * DialogPage.performHelp() method in order to make the 'Help' button
         * function correctly.
         */

        String contextId = getHelpContextId();

        if (contextId == null) {

            contextId = IWorkbenchHelpContextIds.MISSING;
        }

        PlatformUI.getWorkbench().getHelpSystem().displayHelp(contextId);

    }

}
