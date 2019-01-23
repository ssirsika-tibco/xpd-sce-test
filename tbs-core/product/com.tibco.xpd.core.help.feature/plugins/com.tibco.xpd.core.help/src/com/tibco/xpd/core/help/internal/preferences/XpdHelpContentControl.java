/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.core.help.internal.preferences;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.tibco.xpd.core.help.ProductHelpContent;
import com.tibco.xpd.core.help.XpdHelpContribManager;
import com.tibco.xpd.core.help.XpdHelpService;
import com.tibco.xpd.core.help.internal.Messages;

/**
 * Control for showing help contributions and downloading offline help.
 * 
 * @author jarciuch
 * @since 5 Aug 2014
 */
public class XpdHelpContentControl extends Composite {

    /**
     * Empty field representation in the control.
     */
    private static final String EMPTY = "-"; //$NON-NLS-1$

    private static class HelpContentLabelProvider extends BaseLabelProvider
            implements ITableLabelProvider {

        @Override
        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof ProductHelpContent) {
                ProductHelpContent c = (ProductHelpContent) element;
                switch (columnIndex) {
                case 0:
                    return c.getProductName();
                case 1:
                    return c.getVersion();
                case 2:
                    return XpdHelpService.getInstance()
                            .isOfflineHelpAccessible(c) ? Messages.XpdHelpContentControl_available_message
                            : Messages.XpdHelpContentControl_notAvailable_message;
                }
            }
            return ""; //$NON-NLS-1$
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }
    }

    private TableViewer viewer;

    private ContentDetailsComposite details;

    /**
     * Creates this control.
     * 
     * @param parent
     *            the parent control.
     * @param style
     *            use SWT.NONE
     */
    public XpdHelpContentControl(Composite parent, int style) {
        super(parent, style);
        createContents(this);
    }

    private Composite createContents(Composite parent) {

        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parent);
        SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        GridDataFactory.fillDefaults().grab(true, true).hint(200, 300)
                .applyTo(sashForm);
        viewer = createColumnViewer(sashForm);

        Table table = viewer.getTable();
        TableColumn productNameColumn = new TableColumn(table, SWT.LEFT);
        productNameColumn
                .setText(Messages.XpdHelpContentControl_nameColumn_header);
        productNameColumn.setWidth(210);

        TableColumn versionColumn = new TableColumn(table, SWT.LEFT);
        versionColumn
                .setText(Messages.XpdHelpContentControl_versionColumn_header);
        versionColumn.setWidth(60);

        TableColumn offlineFolder = new TableColumn(table, SWT.LEFT);
        offlineFolder.setText(Messages.XpdHelpContentControl_offline_header);
        offlineFolder.setWidth(70);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new HelpContentLabelProvider());
        viewer.setInput(XpdHelpContribManager.getInstance()
                .getProductHelpContents());

        details = new ContentDetailsComposite(sashForm, SWT.NONE);
        viewer.addPostSelectionChangedListener(details);
        viewer.add(details);
        Composite sideBar = new Composite(parent, SWT.NONE);
        GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
                .applyTo(sideBar);
        GridLayoutFactory.fillDefaults().applyTo(sideBar);

        final Button refreshButton = new Button(sideBar, SWT.PUSH);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING)
                .applyTo(refreshButton);
        refreshButton.setText(Messages.XpdHelpContentControl_Refresh_button);

        final Button downloadButton = new Button(sideBar, SWT.PUSH);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING)
                .applyTo(downloadButton);
        downloadButton.setText(Messages.XpdHelpContentControl_download_button);
        downloadButton.setEnabled(false);

        viewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                if (event.getSelection() instanceof IStructuredSelection) {
                    Object first =
                            ((IStructuredSelection) event.getSelection())
                                    .getFirstElement();
                    if (first instanceof ProductHelpContent) {
                        ProductHelpContent c = (ProductHelpContent) first;
                        if (c.isDownloadEnabled() && downloadButton != null
                                && !downloadButton.isDisposed()) {
                            downloadButton.setData(c);
                            downloadButton.setEnabled(true);
                            return;
                        }
                    }
                }
                downloadButton.setEnabled(false);
            }
        });

        // Select first element if possible.
        ISelection sel = viewer.getSelection();
        if (sel.isEmpty()) {
            // Select first element if possible.
            Object first =
                    XpdHelpContribManager.getInstance()
                            .getProductHelpContents().iterator().next();
            if (first != null) {
                viewer.setSelection(new StructuredSelection(first));
            }
        }

        downloadButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (downloadButton.getData() instanceof ProductHelpContent) {
                    ProductHelpContent c =
                            (ProductHelpContent) downloadButton.getData();
                    if (c.isDownloadEnabled()) {
                        XpdHelpService srv = XpdHelpService.getInstance();
                        final File offlineFolder =
                                srv.getDefaulOfflineFolder(c);
                        if (!shouldDownloadProceed(offlineFolder,
                                downloadButton.getShell())) {
                            return;
                        }
                        final String downloadUrl = c.getDownloadUrl();
                        Job downloadJob =
                                new Job(
                                        Messages.XpdHelpContentControl_docDownload_label) {
                                    @Override
                                    protected IStatus run(
                                            IProgressMonitor monitor) {
                                        IStatus status =
                                                XpdHelpService
                                                        .getInstance()
                                                        .downloadOfflineDocs(downloadUrl,
                                                                offlineFolder,
                                                                monitor);
                                        return status;
                                    }
                                };
                        /*
                         * Use safe (mutex) rule for now (only one download at
                         * the time).Possibly change later for mutex per doc
                         * content rule so many contents can be downloaded at
                         * the same time.
                         */
                        downloadJob.setRule(MUTEX_RULE);
                        downloadJob
                                .addJobChangeListener(new JobChangeAdapter() {
                                    @Override
                                    public void done(final IJobChangeEvent event) {
                                        Display.getDefault()
                                                .asyncExec(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        refreshHelpContentsViewer();
                                                    }
                                                });
                                    }
                                });
                        IProgressService progressService =
                                PlatformUI.getWorkbench().getProgressService();
                        progressService.showInDialog(getShell(), downloadJob);
                        downloadJob.schedule();
                    }
                }
            }
        });

        refreshButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                refreshHelpContentsViewer();
            }
        });

        return parent;
    }

    public void refreshHelpContentsViewer() {
        if (viewer != null && viewer.getControl() != null
                && !viewer.getControl().isDisposed()) {
            viewer.refresh();
        }
    }

    private boolean shouldDownloadProceed(File f, Shell shell) {
        if (f.exists()) {
            MessageBox messageBox =
                    new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
            messageBox.setText(Messages.XpdHelpContentControl_warning_title);
            messageBox.setMessage(String
                    .format(Messages.XpdHelpContentControl_warning_message,
                            f.toString()));
            return messageBox.open() == SWT.YES;
        }
        return true;
    }

    private TableViewer createColumnViewer(Composite parent) {
        Table table =
                new Table(parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL
                        | SWT.H_SCROLL | SWT.BORDER);
        table.setHeaderVisible(true);
        table.setLinesVisible(false);
        TableViewer tableViewer = new TableViewer(table);
        return tableViewer;
    }

    private static class ContentDetailsComposite extends Composite implements
            ISelectionChangedListener {

        private Text productName;

        private Text productVersion;

        private Text helpContentUrl;

        private Text productHelpPageUrl;

        private Text downloadUrl;

        private Text offlineFolder;

        private ScrolledComposite sc;

        private Group dg;

        ContentDetailsComposite(Composite parent, int style) {
            super(parent, style);
            this.setLayout(new FillLayout());
            sc = new ScrolledComposite(this, SWT.V_SCROLL);
            GridLayoutFactory.fillDefaults().applyTo(sc);
            dg = new Group(sc, SWT.NONE);
            dg.setText(Messages.XpdHelpContentControl_Details_label);

            sc.setContent(dg);
            sc.setExpandVertical(true);
            sc.setExpandHorizontal(true);

            sc.addControlListener(new ControlAdapter() {
                @Override
                public void controlResized(ControlEvent e) {
                    Rectangle r = sc.getClientArea();
                    sc.setMinSize(dg.computeSize(r.width - 5, SWT.DEFAULT));
                }
            });

            GridDataFactory.fillDefaults().grab(true, true).applyTo(dg);

            GridLayoutFactory.fillDefaults().margins(5, 5).numColumns(2)
                    .applyTo(dg);
            GridDataFactory gdText =
                    GridDataFactory.fillDefaults().grab(true, false);
            GridDataFactory gdLabel = GridDataFactory.fillDefaults();

            Label productNameLabel = new Label(dg, SWT.NONE);
            productNameLabel
                    .setText(Messages.XpdHelpContentControl_productName_label);
            gdLabel.applyTo(productNameLabel);
            productName = new Text(dg, SWT.WRAP | SWT.READ_ONLY);
            gdText.applyTo(productName);

            Label productVersionLabel = new Label(dg, SWT.NONE);
            productVersionLabel
                    .setText(Messages.XpdHelpContentControl_version_label);
            gdLabel.applyTo(productVersionLabel);
            productVersion = new Text(dg, SWT.WRAP | SWT.READ_ONLY);
            gdText.applyTo(productVersion);

            Label helpContentUrlLabel = new Label(dg, SWT.NONE);
            helpContentUrlLabel
                    .setText(Messages.XpdHelpContentControl_helpContentUrl_label);
            gdLabel.applyTo(helpContentUrlLabel);
            helpContentUrl = new Text(dg, SWT.WRAP | SWT.READ_ONLY);
            gdText.applyTo(helpContentUrl);

            Label productHelpPageLabel = new Label(dg, SWT.NONE);
            productHelpPageLabel
                    .setText(Messages.XpdHelpContentControl_productHelpPage_label);
            gdLabel.applyTo(productHelpPageLabel);
            productHelpPageUrl = new Text(dg, SWT.WRAP | SWT.READ_ONLY);
            gdText.applyTo(productHelpPageUrl);

            Label downloadUrlLabel = new Label(dg, SWT.NONE);
            downloadUrlLabel
                    .setText(Messages.XpdHelpContentControl_downloadUrl_label);
            gdLabel.applyTo(downloadUrlLabel);
            downloadUrl = new Text(dg, SWT.WRAP | SWT.READ_ONLY);
            gdText.applyTo(downloadUrl);

            Label offlineFolderLabel = new Label(dg, SWT.NONE);
            offlineFolderLabel
                    .setText(Messages.XpdHelpContentControl_offlineFolder_label);
            gdLabel.applyTo(offlineFolderLabel);
            offlineFolder = new Text(dg, SWT.WRAP | SWT.READ_ONLY);
            gdText.applyTo(offlineFolder);

            updateDetails(null);
        }

        /**
         * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
         * 
         * @param event
         */
        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            if (event.getSelection() instanceof IStructuredSelection) {
                Object first =
                        ((IStructuredSelection) event.getSelection())
                                .getFirstElement();
                if (first instanceof ProductHelpContent) {
                    updateDetails((ProductHelpContent) first);
                    return;
                }
            }
            updateDetails(null);
        }

        private void updateDetails(ProductHelpContent helpContent) {
            if (helpContent != null) {
                productName.setText(nullSafe(helpContent.getProductName()));
                productVersion.setText(nullSafe(helpContent.getVersion()));
                helpContentUrl.setText(nullSafe(helpContent.getWebHelpUrl()));
                productHelpPageUrl.setText(nullSafe(helpContent
                        .getProductHelpUrl()));
                downloadUrl.setText(nullSafe(helpContent.getDownloadUrl()));
                offlineFolder.setText(nullSafe(helpContent
                        .getOfflineFolderName()));
            } else {
                productName.setText(EMPTY);
                productVersion.setText(EMPTY);
                helpContentUrl.setText(EMPTY);
                productHelpPageUrl.setText(EMPTY);
                downloadUrl.setText(EMPTY);
                offlineFolder.setText(EMPTY);
            }
            dg.layout();
            Rectangle r = sc.getClientArea();
            sc.setMinSize(dg.computeSize(r.width - 5, SWT.DEFAULT));
        }
    }

    private static String nullSafe(String s) {
        return (s != null) ? s : EMPTY;
    }

    private static final ISchedulingRule MUTEX_RULE = new ISchedulingRule() {
        @Override
        public boolean isConflicting(ISchedulingRule rule) {
            return rule == this;
        }

        @Override
        public boolean contains(ISchedulingRule rule) {
            return rule == this;
        }
    };

}
