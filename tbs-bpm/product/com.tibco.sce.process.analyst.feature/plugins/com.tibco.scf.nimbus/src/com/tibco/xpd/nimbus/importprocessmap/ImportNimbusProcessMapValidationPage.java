/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.nimbus.importprocessmap;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.nimbus.XpdNimbusImages;
import com.tibco.xpd.nimbus.XpdNimbusPlugin;
import com.tibco.xpd.nimbus.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.importexport.importwizard.pages.FilteredFSElement;

/**
 * Page to validate import Nimbus Process Map files and report any potential
 * errors.
 * <p>
 * The finish button is only enabled if all import files pass the validation.
 * <p>
 * Currently this checks to ensure
 * <li>That all import files appear to have been exported from Nimbus Control as
 * 'Simplified' export format</li>
 * 
 * <li>That there is at least one process diagram in each</li>
 * 
 * <li>that all the diagrams are Simple Diagram (not Nimbus' version of BPMN
 * diagram).</li>
 * 
 * 
 * @author aallway
 * @since 16 Oct 2012
 */
class ImportNimbusProcessMapValidationPage extends AbstractXpdWizardPage {

    private List<File> toValidate;

    private boolean allFilesValid = false;

    private TableViewer validationStatuses;

    protected ImportNimbusProcessMapValidationPage() {
        super("com.tibco.xpd.nimbus.import.validationpage"); //$NON-NLS-1$
        setTitle(Messages.ValidateImportPage_ValidateImportFiles_title);

        setPageComplete(false);

        toValidate = new ArrayList<File>();
    }

    @Override
    public boolean isPageComplete() {
        /*
         * Only ever allow Wizard to Finish when validation page is current
         * page.
         */
        if (isCurrentPage()) {
            IWizard wizard = getWizard();

            if (wizard instanceof ImportNimbusProcessMapWizard) {

                ImportNimbusProcessMapWizard importWizard =
                        (ImportNimbusProcessMapWizard) wizard;

                List<FilteredFSElement> elements =
                        importWizard.getImportResources();

                if (elements.size() > 0) {
                    if (toValidate.size() == 0) {
                        allFilesValid = false;

                        /*
                         * When we've just stepped onto page then initiate a
                         * validation.
                         */
                        for (FilteredFSElement element : elements) {
                            Object fso = element.getFileSystemObject();
                            if (fso instanceof File) {
                                File file = (File) fso;
                                if (!toValidate.contains(file)) {
                                    toValidate.add(file);
                                }
                            }
                        }

                        startValidation();

                        return allFilesValid;

                    } else {
                        /*
                         * If we've finished the validation then we can return
                         * the result of the validation
                         */
                        return allFilesValid;
                    }
                }
            }

        } else {
            /*
             * Not current page, clear stuff for next time we step onto it.
             */
            toValidate.clear();
            allFilesValid = false;
        }

        return false;
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        composite.setLayout(layout);

        /* Table and it's container. */
        Composite tableContainer = new Composite(composite, SWT.NONE);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        tableContainer.setLayoutData(gd);

        validationStatuses = new TableViewer(tableContainer);

        validationStatuses.setContentProvider(new StatusContentProvider());
        validationStatuses.getTable().setHeaderVisible(true);

        TableViewerColumn messageColumn =
                new TableViewerColumn(validationStatuses, SWT.LEFT);
        messageColumn.setLabelProvider(new MessageCellLabelProvider());
        messageColumn.getColumn()
                .setText(Messages.ValidateImportPage_Message_label);

        TableViewerColumn fileColumn =
                new TableViewerColumn(validationStatuses, SWT.LEFT);
        fileColumn.getColumn().setText(Messages.ValidateImportPage_File_label);
        fileColumn.setLabelProvider(new FileCellLabelProvider());

        /* Setup column layout */
        TableColumnLayout tableColumnLayout = new TableColumnLayout();

        tableColumnLayout.setColumnData(messageColumn.getColumn(),
                new ColumnWeightData(50, 30, true));

        tableColumnLayout.setColumnData(fileColumn.getColumn(),
                new ColumnWeightData(50, 30, true));
        tableContainer.setLayout(tableColumnLayout);

        setControl(composite);
    }

    /**
     * Start the validaiton of import files.
     */
    private void startValidation() {
        validationStatuses.setInput(null);

        setErrorMessage(null);
        setMessage(Messages.ImportNimbusProcessMapValidationPage_ValidatingCorrectType_message,
                IMessageProvider.INFORMATION);

        IRunnableWithProgress validate = new IRunnableWithProgress() {

            @Override
            public void run(final IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                final List<File> files = new ArrayList<File>(toValidate);
                monitor.beginTask(Messages.ValidateImportPage_Validating_label,
                        files.size());

                final List<ImportNimbusProcessMapValidationStatus> statusList =
                        new ArrayList<ImportNimbusProcessMapValidationStatus>();

                for (File file : files) {
                    if (monitor.isCanceled()) {
                        break;
                    }

                    monitor.subTask(Messages.ValidateImportPage_Validating_label
                            + file.getName() + "..."); //$NON-NLS-1$

                    // Validate
                    ImportNimbusProcessMapValidator validator =
                            new ImportNimbusProcessMapValidator(file);

                    statusList.addAll(validator.validate());

                    Display.getDefault().asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            validationStatuses.setInput(statusList);
                        }
                    });

                    monitor.worked(1);

                    /* Add a small sleep so we can see progress happening */
                    if (files.size() <= 4) {
                        Thread.sleep(250);
                    }
                }

                Display.getDefault().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        validationStatuses.setInput(statusList);

                        if (monitor.isCanceled()) {
                            setMessage(null);
                            setErrorMessage(Messages.ImportNimbusProcessMapValidationPage_ValidationCancelled_message);

                        } else if (hasSeverityLevel(statusList, IStatus.ERROR)) {
                            setMessage(null);
                            setErrorMessage(Messages.ValidateImportPage_ErorsValdiating_label);

                        } else if (hasSeverityLevel(statusList, IStatus.WARNING)) {
                            setErrorMessage(null);
                            setMessage(Messages.ValidateImportPage_ValidationWarnings_label,
                                    IMessageProvider.WARNING);
                            allFilesValid = true;

                        } else {
                            setErrorMessage(null);
                            setMessage(Messages.ValidateImportPage_AllFilesValid_message,
                                    IMessageProvider.INFORMATION);
                            allFilesValid = true;
                        }

                        getWizard().getContainer().updateButtons();
                    }

                });

            }
        };

        try {
            getContainer().run(true, true, validate);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param statusList
     * @return true if any of the <code>NimbusImportValidationStatus</code>s' is
     *         at least of given severity
     */
    private boolean hasSeverityLevel(
            List<ImportNimbusProcessMapValidationStatus> statusList,
            int severity) {
        for (ImportNimbusProcessMapValidationStatus importValidationStatus : statusList) {
            if (severity == importValidationStatus.getSeverity()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validation Status table content provider.
     * 
     * 
     * @author aallway
     * @since 16 Oct 2012
     */
    private static class StatusContentProvider implements
            IStructuredContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            List<?> elements = new ArrayList<Object>();
            if (inputElement instanceof List) {
                elements = (List<?>) inputElement;
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

    /**
     * Message column label provider.
     * 
     * @author aallway
     * @since 16 Oct 2012
     */
    private static class MessageCellLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {
            Image img = null;
            String text = ""; //$NON-NLS-1$
            if (cell.getElement() instanceof ImportNimbusProcessMapValidationStatus) {
                ImportNimbusProcessMapValidationStatus status =
                        (ImportNimbusProcessMapValidationStatus) cell
                                .getElement();
                text = status.getMessage();
                if (IStatus.ERROR == status.getSeverity()) {
                    img =
                            XpdNimbusPlugin.getDefault().getImageRegistry()
                                    .get(XpdNimbusImages.IMG_ERROR);
                } else if (IStatus.WARNING == status.getSeverity()) {
                    img =
                            XpdNimbusPlugin.getDefault().getImageRegistry()
                                    .get(XpdNimbusImages.IMG_WARNING);
                } else {
                    img =
                            XpdNimbusPlugin.getDefault().getImageRegistry()
                                    .get(XpdNimbusImages.IMG_TICK);
                }

            }
            cell.setText(text);
            cell.setImage(img);
        }
    }

    /**
     * Label provider for File column
     * 
     * @author aallway
     * @since 16 Oct 2012
     */
    private static class FileCellLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {
            String text = ""; //$NON-NLS-1$
            if (cell.getElement() instanceof ImportNimbusProcessMapValidationStatus) {
                ImportNimbusProcessMapValidationStatus error =
                        (ImportNimbusProcessMapValidationStatus) cell
                                .getElement();
                text = error.getFile();
            }
            cell.setText(text);
        }
    }

}
