/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.simulation.compare.ComparePlugin;
import com.tibco.xpd.simulation.compare.ComparisonReportEngine;
import com.tibco.xpd.simulation.compare.ComparisonReportFactory;
import com.tibco.xpd.simulation.compare.IReportListener;
import com.tibco.xpd.simulation.compare.Messages;
import com.tibco.xpd.simulation.compare.ReportType;
import com.tibco.xpd.simulation.compare.ReportTypeManager;
import com.tibco.xpd.simulation.compare.UrlReportCanvas;

/**
 * @author nwilson
 */
public class SimulationCompareEditor extends EditorPart implements
        SelectionListener, IReportListener {
    /** Default report name. */
    private static final String REPORT_NAME =
            Messages.SimulationCompareEditor_defaultReportFileName;

    /** Adjustment for the sash position after dragging. */
    private static final int SASH_POSITIONING_FIX = 16;

    /** Default width for report table columns. */
    private static final int REPORT_TABLE_COLUMN_WIDTH = 200;

    /** Initial width for the resources tree. */
    private static final int RESOURCES_TREE_WIDTH = 200;

    /** Initial height for the resources tree. */
    private static final int RESOURCES_TREE_HEIGHT = 100;

    /**
     * Output file selection class.
     * 
     * @author nwilson
     */
    private final class SelectFile implements Runnable {

        /** The output file type as defined in the ReportType class. */
        private final int type;

        /** The parent shell for the file dialog. */
        private final Shell shell;

        /** The file name chosen by the user. */
        private String filename;

        /**
         * @param type
         *            The output file type as defined in the ReportType class.
         * @param shell
         *            The parent shell for the file dialog.
         */
        private SelectFile(final int type, final Shell shell) {
            super();
            this.type = type;
            this.shell = shell;
        }

        /**
         * Launches the file selection dialog.
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            FileDialog dialog = new FileDialog(shell, SWT.SAVE);
            dialog.setText(Messages.SimulationCompareEditor_SaveReportTitle);
            if (type == ReportType.OUTPUT_HTML) {
                dialog.setFilterExtensions(new String[] { "html", "htm" }); //$NON-NLS-1$ //$NON-NLS-2$
                dialog.setFileName(REPORT_NAME + ".html"); //$NON-NLS-1$
            } else if (type == ReportType.OUTPUT_PDF) {
                dialog.setFilterExtensions(new String[] { "pdf" }); //$NON-NLS-1$
                dialog.setFileName(REPORT_NAME + ".pdf"); //$NON-NLS-1$
            }
            filename = dialog.open();
            if (filename != null) {
                // What if user enter filename whithout extension?
                // No problem, we can handle that... (defect 27975)
                switch (this.type) {
                case ReportType.OUTPUT_HTML:
                    if (!filename.endsWith(".html") & !filename.endsWith(".htm")) { //$NON-NLS-1$ //$NON-NLS-2$
                        filename = filename + ".html"; //$NON-NLS-1$
                    }
                    break;
                case ReportType.OUTPUT_PDF:
                    if (!filename.endsWith(".pdf")) { //$NON-NLS-1$
                        filename = filename + ".pdf"; //$NON-NLS-1$
                    }
                    break;
                default:
                    // Oh no! Unsupported report type!
                    LoggerFactory.createLogger(ComparePlugin.PLUGIN_ID)
                            .error("Unsupported report type: " + this.type); //$NON-NLS-1$
                    return;
                }

                File file = new File(filename);
                if (file.exists()) {
                    if (!MessageDialog.openConfirm(shell,
                            Messages.SimulationCompareEditor_OverwriteLabel,
                            Messages.SimulationCompareEditor_FileExistsError)) {
                        filename = null;
                    } else {
                        if (!file.delete()) {
                            MessageDialog
                                    .openError(shell,
                                            Messages.SimulationCompareEditor_DeleteFailed,
                                            Messages.SimulationCompareEditor_DeleteFailedMessage);
                            filename = null;
                        }
                    }
                }
            }
        }

        /**
         * @return The file name chosen by the user.
         */
        public String getFile() {
            return filename;
        }
    }

    /** The tree viewer for the selected resources. */
    private TreeViewer resourcesViewer;

    /** The tree used to display the selected resources. */
    private Tree resourcesTree;

    /** The table viewer for the available report types. */
    private TableViewer reportViewer;

    /** The table used to display the available report types. */
    private Table reportTable;

    /** The sash used to split the resources tree and report table. */
    private Sash sash;

    /** The composite used to display the report. */
    private Composite comparison;

    /** The toolbar for the report generation buttons. */
    private ToolBar toolBar;

    /** A list of checked resources. */
    private HashSet<IResource> checked;

    /** The report type currently selected. */
    private SimulationReport selectedReport;

    /** Tool button to display the report on screen. */
    private ToolItem displayReport;

    /** Tool button to save the report as an HTML file. */
    private ToolItem saveHtmlReport;

    /** Tool button to save the report as a PDF file. */
    private ToolItem savePdfReport;

    /** Image for the display report tool button. */
    private Image displayImage;

    /** Image for the html report tool button. */
    private Image htmlImage;

    /** Image for the pdf report tool button. */
    private Image pdfImage;

    /** Flag to indicate if a report is currently being run. */
    private boolean isBusy;

    /**
     * Constructor.
     */
    public SimulationCompareEditor() {
        super();
        checked = new HashSet<IResource>();
        isBusy = false;
    }

    /**
     * Saving is not required for this editor.
     * 
     * @param monitor
     *            n/a
     * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void doSave(final IProgressMonitor monitor) {
    }

    /**
     * Saving is not required for this editor.
     * 
     * @see org.eclipse.ui.part.EditorPart#doSaveAs()
     */
    @Override
    public void doSaveAs() {
    }

    /**
     * @param site
     *            The editor site for this editor.
     * @param input
     *            The editor input containing the selected resources.
     * @throws PartInitException
     *             If the editor could not be initialised.
     * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     */
    @Override
    public void init(final IEditorSite site, final IEditorInput input)
            throws PartInitException {
        setSite(site);
        setInput(input);
        ComparePlugin.getDefault().addReportListener(this);
    }

    /**
     * Read only editor, it cannot be dirty.
     * 
     * @return false.
     * @see org.eclipse.ui.part.EditorPart#isDirty()
     */
    @Override
    public boolean isDirty() {
        return false;
    }

    /**
     * Saving is not required.
     * 
     * @return false.
     * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * @param parent
     *            The parent composite for the editor.
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl(final Composite parent) {
        GridLayout grid = new GridLayout();
        grid.numColumns = 2;
        grid.marginWidth = 0;
        grid.horizontalSpacing = 0;
        grid.verticalSpacing = 0;
        grid.marginHeight = 0;
        parent.setLayout(grid);

        createResourceViewer(parent);

        reportTable =
                new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
        reportTable.setHeaderVisible(true);
        GridData reportsGridData = new GridData();
        reportsGridData.heightHint = RESOURCES_TREE_HEIGHT;
        reportsGridData.grabExcessHorizontalSpace = true;
        reportsGridData.verticalAlignment = SWT.FILL;
        reportsGridData.horizontalAlignment = SWT.FILL;
        reportTable.setLayoutData(reportsGridData);
        reportTable.addSelectionListener(this);

        TableColumn nameColumn = new TableColumn(reportTable, SWT.NONE);
        nameColumn.setText(Messages.SimulationCompareEditor_NameColumnLabel);
        nameColumn.setWidth(REPORT_TABLE_COLUMN_WIDTH);
        TableColumn typeColumn = new TableColumn(reportTable, SWT.NONE);
        typeColumn.setText(Messages.SimulationCompareEditor_TypeColumnLabel);
        typeColumn.setWidth(REPORT_TABLE_COLUMN_WIDTH);

        toolBar = new ToolBar(parent, SWT.HORIZONTAL);
        GridData toolBarGridData = new GridData();
        toolBarGridData.horizontalAlignment = SWT.FILL;
        toolBarGridData.grabExcessHorizontalSpace = true;
        toolBarGridData.horizontalSpan = 2;
        toolBar.setLayoutData(toolBarGridData);

        displayImage = ComparePlugin.getImageDescriptor("icons/console.gif") //$NON-NLS-1$
                .createImage();

        displayReport = new ToolItem(toolBar, SWT.PUSH);
        displayReport
                .setText(Messages.SimulationCompareEditor_DisplayReportLabel);
        displayReport.setImage(displayImage);
        displayReport.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                updateReport();
            }

            @Override
            public void widgetDefaultSelected(final SelectionEvent e) {
            }
        });

        htmlImage = ComparePlugin.getImageDescriptor("icons/html.gif") //$NON-NLS-1$
                .createImage();

        saveHtmlReport = new ToolItem(toolBar, SWT.PUSH);
        saveHtmlReport
                .setText(Messages.SimulationCompareEditor_SaveHtmlReportLabel);
        saveHtmlReport.setImage(htmlImage);
        saveHtmlReport.setEnabled(false);
        saveHtmlReport.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                saveReport(ReportType.OUTPUT_HTML);
            }

            @Override
            public void widgetDefaultSelected(final SelectionEvent e) {
            }
        });

        pdfImage =
                ComparePlugin.getImageDescriptor("icons/pdf.gif").createImage(); //$NON-NLS-1$

        savePdfReport = new ToolItem(toolBar, SWT.PUSH);
        savePdfReport
                .setText(Messages.SimulationCompareEditor_SavePdfReportLabel);
        savePdfReport.setImage(pdfImage);
        savePdfReport.setEnabled(false);
        savePdfReport.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                saveReport(ReportType.OUTPUT_PDF);
            }

            @Override
            public void widgetDefaultSelected(final SelectionEvent e) {
            }
        });

        sash = new Sash(parent, SWT.HORIZONTAL);
        GridData sashGridData = new GridData();
        sashGridData.grabExcessHorizontalSpace = true;
        sashGridData.horizontalAlignment = SWT.FILL;
        sashGridData.horizontalSpan = 2;
        sash.setLayoutData(sashGridData);
        sash.addSelectionListener(this);

        comparison = new Composite(parent, SWT.NONE);
        GridData comparisonGridData = new GridData();
        comparisonGridData.grabExcessHorizontalSpace = true;
        comparisonGridData.grabExcessVerticalSpace = true;
        comparisonGridData.horizontalAlignment = SWT.FILL;
        comparisonGridData.verticalAlignment = SWT.FILL;
        comparisonGridData.horizontalSpan = 2;
        comparison.setLayoutData(comparisonGridData);
        GridLayout comparisonLayout = new GridLayout();
        comparisonLayout.marginWidth = 0;
        comparisonLayout.horizontalSpacing = 0;
        comparisonLayout.verticalSpacing = 0;
        comparisonLayout.marginHeight = 0;
        comparison.setLayout(comparisonLayout);
        comparison.setBackground(resourcesTree.getBackground());

        showEmptyReport();

        reportViewer = new TableViewer(reportTable);
        reportViewer.setContentProvider(new SimulationReportsContentProvider());
        reportViewer.setLabelProvider(new SimulationReportsLabelProvider());
        updateReports();
        updateReportSelection();
    }

    /**
     * Create the simulation data resource viewer.
     * 
     * @param parent
     * @return
     */
    private Control createResourceViewer(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        resourcesTree = new Tree(root, SWT.MULTI | SWT.CHECK | SWT.BORDER);
        GridData resourcesGridData = new GridData();
        resourcesGridData.heightHint = RESOURCES_TREE_HEIGHT;
        resourcesGridData.widthHint = RESOURCES_TREE_WIDTH;
        resourcesGridData.verticalAlignment = SWT.FILL;
        resourcesGridData.horizontalAlignment = SWT.FILL;
        resourcesTree.setLayoutData(resourcesGridData);
        resourcesTree.addSelectionListener(this);

        resourcesViewer = new TreeViewer(resourcesTree);
        resourcesViewer
                .setContentProvider(new SimulationResultsContentProvider());
        resourcesViewer.setLabelProvider(new SimulationResultsLabelProvider());
        resourcesViewer.setInput(getEditorInput());

        Menu menu = new Menu(resourcesTree);
        resourcesTree.setMenu(menu);

        // Add the delete action to the context menu
        final MenuItem deleteItem = new MenuItem(menu, SWT.NONE);
        deleteItem
                .setText(Messages.SimulationCompareEditor_deleteSimulationData_menu_label);
        deleteItem.setImage(PlatformUI.getWorkbench().getSharedImages()
                .getImage(ISharedImages.IMG_TOOL_DELETE));
        deleteItem.setEnabled(false);

        /*
         * Run the delete action when context menu is selected
         */
        deleteItem.addSelectionListener(new SelectionAdapter() {
            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Delete the selected resources
                deleteSelectedSimulationResults(resourcesViewer);

            }
        });

        /*
         * Register delete key to delete the selected items
         */
        resourcesTree.addKeyListener(new KeyAdapter() {
            /**
             * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
             * 
             * @param e
             */
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.character == SWT.DEL) {
                    deleteSelectedSimulationResults(resourcesViewer);
                }
            }
        });

        /*
         * Enable the delete action in the context menu when a selection is made
         * in the viewer.
         */
        resourcesTree.addSelectionListener(new SelectionAdapter() {
            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             * 
             * @param e
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                TreeItem[] selection = resourcesTree.getSelection();
                deleteItem
                        .setEnabled(selection != null && selection.length > 0);
            }
        });

        /*
         * Dispose the menu when the resource tree is disposed
         */
        resourcesTree.addDisposeListener(new DisposeListener() {

            @Override
            public void widgetDisposed(DisposeEvent e) {
                Menu menu = resourcesTree.getMenu();
                if (menu != null) {
                    menu.dispose();
                }
            }
        });

        return root;
    }

    /**
     * @param selection
     * @throws CoreException
     */
    private void deleteSelectedSimulationResults(TreeViewer resourceViewer) {
        IStructuredSelection selection =
                (IStructuredSelection) resourcesViewer.getSelection();
        if (!selection.isEmpty()) {
            boolean deleted = false;
            boolean continueDeleting = false;
            List<IStatus> problems = new ArrayList<IStatus>();
            try {
                for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                    Object next = iter.next();

                    if (next instanceof IFile) {
                        IFile file = (IFile) next;
                        if (!continueDeleting) {
                            /*
                             * Ask the user whether they want to delete the data
                             * file. (If they say Yes To All then stop asking
                             * and delete all.)
                             */
                            MessageDialog dlg =
                                    new MessageDialog(
                                            getSite().getShell(),
                                            Messages.SimulationCompareEditor_deleteSimulationData_dialog_title,
                                            null,
                                            String.format(Messages.SimulationCompareEditor_deleteSimulationData_dialog_message,
                                                    file.getName()),
                                            MessageDialog.QUESTION,
                                            new String[] {
                                                    IDialogConstants.YES_LABEL,
                                                    IDialogConstants.YES_TO_ALL_LABEL,
                                                    IDialogConstants.NO_LABEL,
                                                    IDialogConstants.CANCEL_LABEL },
                                            0);

                            switch (dlg.open()) {
                            case 0: /* YES */
                                // Continue to delete file
                                break;
                            case 1: /* YES TO ALL */
                                // Delete and don't ask again...
                                continueDeleting = true;
                                break;
                            case 2: /* NO */
                                // Skip the delete of this file
                                continue;
                            default: /* CANCEL */
                                return;

                            }
                        }

                        try {
                            file.delete(true, new NullProgressMonitor());
                            deleted = true;
                        } catch (CoreException e) {
                            // Collect all errors and report later
                            problems.add(e.getStatus());
                        }
                    }
                }
            } finally {

                if (!problems.isEmpty()) {
                    IStatus status = null;
                    String msg = null;
                    if (problems.size() > 1) {
                        msg =
                                Messages.SimulationCompareEditor_errorDeletingMultipleDataFiles_longdesc;
                        status =
                                new MultiStatus(
                                        ComparePlugin.PLUGIN_ID,
                                        0,
                                        problems.toArray(new IStatus[problems
                                                .size()]),
                                        Messages.SimulationCompareEditor_seeDetails_shortdesc,
                                        null);
                    } else {
                        msg =
                                Messages.SimulationCompareEditor_errorDeletingDataFile_longdesc;
                        status = problems.get(0);
                    }
                    // Report the errors
                    ErrorDialog
                            .openError(resourceViewer.getTree().getShell(),
                                    Messages.SimulationCompareEditor_errorDeletingData_errorDialog_title,
                                    msg,
                                    status);
                }
                /*
                 * If any files were deleted then refresh the viewer
                 */
                if (deleted) {
                    resourcesViewer.refresh();
                }
            }
        }
    }

    /**
     * Creates a default empty report that will be shown until a report is
     * generated.
     */
    private void showEmptyReport() {
        Label empty = new Label(comparison, SWT.LEFT);
        GridData emptyGridData = new GridData();
        emptyGridData.grabExcessHorizontalSpace = true;
        emptyGridData.grabExcessVerticalSpace = true;
        emptyGridData.horizontalAlignment = SWT.FILL;
        emptyGridData.verticalAlignment = SWT.FILL;
        empty.setLayoutData(emptyGridData);
        empty.setBackground(resourcesTree.getBackground());
        empty.setText(Messages.SimulationCompareEditor_SelectReportMessage);
        comparison.layout();
    }

    /**
     * Refreshes the list of available reports.
     */
    private void updateReports() {
        reportViewer.setInput(ComparePlugin.getDefault().getReports());
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {
    }

    /**
     * Handler for selection events on the resources tree and report table.
     * 
     * @param e
     *            The selection event.
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public void widgetSelected(final SelectionEvent e) {
        if (e.getSource() == sash) {
            if (e.detail != SWT.DRAG) {
                GridData data = (GridData) resourcesTree.getLayoutData();
                data.heightHint = e.y - SASH_POSITIONING_FIX;
                resourcesTree.getParent().layout();
            }
        } else if (e.getSource() == resourcesTree) {
            if (e.detail == SWT.CHECK) {
                TreeItem item = (TreeItem) e.item;
                IResource resource = (IResource) item.getData();
                if (item.getChecked()) {
                    checked.add(resource);
                } else {
                    checked.remove(resource);
                }
                updateButtonState();
            }
        } else if (e.getSource() == reportTable) {
            updateReportSelection();
        }
    }

    /**
     * Not used.
     * 
     * @param e
     *            Selection event.
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public void widgetDefaultSelected(final SelectionEvent e) {
    }

    /**
     * Updates the currently selected report type and refreshes the button
     * states.
     */
    private void updateReportSelection() {
        IStructuredSelection selection =
                (IStructuredSelection) reportViewer.getSelection();
        if (selection.size() == 1) {
            Object object = selection.getFirstElement();
            if (object instanceof SimulationReport) {
                selectedReport = (SimulationReport) object;
            }
        } else {
            selectedReport = null;
        }
        updateButtonState();
    }

    /**
     * Refreshes the button states.
     */
    private void updateButtonState() {
        if (selectedReport != null && checked.size() != 0 && !isBusy) {
            ReportTypeManager reportTypeManager =
                    ComparePlugin.getDefault().getReportTypeManager();
            ReportType reportType =
                    reportTypeManager.getReportType(selectedReport
                            .getReportTypeId());
            ComparisonReportFactory factory = reportType.getFactory();
            boolean inputOk =
                    factory.supportsInputType(checked.size() == 1 ? ReportType.INPUT_SINGLE
                            : ReportType.INPUT_MULTIPLE);
            displayReport.setEnabled(inputOk);
            saveHtmlReport.setEnabled(inputOk
                    && factory.supportsOutputType(ReportType.OUTPUT_HTML));
            savePdfReport.setEnabled(inputOk
                    && factory.supportsOutputType(ReportType.OUTPUT_PDF));
        } else {
            displayReport.setEnabled(false);
            saveHtmlReport.setEnabled(false);
            savePdfReport.setEnabled(false);
        }
    }

    /**
     * Generates a report to the screen.
     */
    private void updateReport() {
        final SimulationReport reportToUpdate = selectedReport;
        Control[] children = comparison.getChildren();
        for (int i = 0; i < children.length; i++) {
            children[i].dispose();
        }
        if (reportToUpdate == null || checked.size() == 0) {
            showEmptyReport();
        } else {
            isBusy = true;
            updateButtonState();
            final Widget wait = showGenerationWait();
            Display.getCurrent().asyncExec(new Runnable() {
                @Override
                public void run() {
                    ReportTypeManager reportTypeManager =
                            ComparePlugin.getDefault().getReportTypeManager();
                    ReportType reportType =
                            reportTypeManager.getReportType(reportToUpdate
                                    .getReportTypeId());
                    ComparisonReportFactory factory = reportType.getFactory();
                    ComparisonReportEngine engine = factory.createEngine();
                    engine.initialise(reportToUpdate);
                    final UrlReportCanvas report =
                            new UrlReportCanvas(comparison, engine);
                    GridData chartGridData = new GridData();
                    chartGridData.grabExcessHorizontalSpace = true;
                    chartGridData.grabExcessVerticalSpace = true;
                    chartGridData.horizontalAlignment = SWT.FILL;
                    chartGridData.verticalAlignment = SWT.FILL;
                    report.setLayoutData(chartGridData);
                    Job job =
                            new Job(
                                    Messages.SimulationCompareEditor_GenerateReportLabel) {
                                @Override
                                protected IStatus run(
                                        final IProgressMonitor monitor) {
                                    report.setInput(getEditorInput(), checked);
                                    Display.getDefault()
                                            .asyncExec(new Runnable() {
                                                @Override
                                                public void run() {
                                                    wait.dispose();
                                                    comparison.layout();
                                                    isBusy = false;
                                                    updateButtonState();
                                                }
                                            });
                                    monitor.done();
                                    return Status.OK_STATUS;
                                }
                            };
                    job.schedule();
                }
            });
        }
    }

    /**
     * Generates a report and saves it to file.
     * 
     * @param outputType
     *            The type of report to generate.
     */
    private void saveReport(final int outputType) {
        final SimulationReport reportToSave = selectedReport;
        final Shell shell = this.getSite().getShell();
        isBusy = true;
        updateButtonState();
        Job job =
                new Job(
                        Messages.SimulationCompareEditor_GenerateSavedReportLabel) {
                    @Override
                    protected IStatus run(final IProgressMonitor monitor) {
                        // Get the output file name
                        SelectFile selectFile =
                                new SelectFile(outputType, shell);
                        Display.getDefault().syncExec(selectFile);
                        String file = selectFile.getFile();
                        if (file != null) {
                            ReportTypeManager reportTypeManager =
                                    ComparePlugin.getDefault()
                                            .getReportTypeManager();
                            ReportType reportType =
                                    reportTypeManager
                                            .getReportType(reportToSave
                                                    .getReportTypeId());
                            ComparisonReportFactory factory =
                                    reportType.getFactory();
                            ComparisonReportEngine engine =
                                    factory.createEngine();
                            engine.initialise(reportToSave, file);
                            IResource[] inputs =
                                    getInputs(getEditorInput(), checked);
                            engine.generate(inputs, outputType);
                            engine.dispose();
                        }
                        Display.getDefault().syncExec(new Runnable() {
                            @Override
                            public void run() {
                                isBusy = false;
                                updateButtonState();
                            }
                        });
                        monitor.done();
                        return Status.OK_STATUS;
                    }
                };
        job.schedule();
    }

    /**
     * Displays a message inthe report canvas asking the user to wait.
     * 
     * @return The wait widget.
     */
    private Widget showGenerationWait() {
        Label empty = new Label(comparison, SWT.LEFT);
        GridData emptyGridData = new GridData();
        emptyGridData.grabExcessHorizontalSpace = true;
        emptyGridData.grabExcessVerticalSpace = true;
        emptyGridData.horizontalAlignment = SWT.FILL;
        emptyGridData.verticalAlignment = SWT.FILL;
        empty.setLayoutData(emptyGridData);
        empty.setBackground(resourcesTree.getBackground());
        empty.setText(Messages.SimulationCompareEditor_PleaseWaitLabel);
        comparison.layout();
        return empty;
    }

    /**
     * @param input
     *            The editor input containing the resources.
     * @param filter
     *            The collection of resources to filter by.
     * @return A list of resources in both the input and filter.
     */
    public IResource[] getInputs(final IEditorInput input,
            final Collection filter) {
        IResource[] inputs = null;
        if (input != null) {
            IResource[] tempInputs =
                    (IResource[]) input.getAdapter(IResource[].class);
            if (tempInputs != null) {
                if (filter != null) {
                    ArrayList<IResource> filteredList =
                            new ArrayList<IResource>();
                    for (int i = 0; i < tempInputs.length; i++) {
                        if (filter.contains(tempInputs[i])) {
                            filteredList.add(tempInputs[i]);
                        }
                    }
                    inputs = new IResource[filteredList.size()];
                    filteredList.toArray(inputs);
                }
            }
        }
        return inputs;
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose() {
        if (displayImage != null) {
            displayImage.dispose();
            displayImage = null;
        }
        if (htmlImage != null) {
            htmlImage.dispose();
            htmlImage = null;
        }
        if (pdfImage != null) {
            pdfImage.dispose();
            pdfImage = null;
        }
        ComparePlugin.getDefault().removeReportListener(this);
        super.dispose();
    }

    /**
     * @see com.tibco.xpd.simulation.compare.IReportListener#reportChange()
     */
    @Override
    public void reportChange() {
        reportViewer.refresh();
        updateReportSelection();
    }
}
