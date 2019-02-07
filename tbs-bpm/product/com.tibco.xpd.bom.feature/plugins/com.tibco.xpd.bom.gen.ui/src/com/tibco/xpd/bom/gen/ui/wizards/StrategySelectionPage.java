/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.bom.gen.api.BOMGenerator2;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.gen.ui.Activator;
import com.tibco.xpd.bom.gen.ui.internal.Messages;
import com.tibco.xpd.bom.gen.util.DependencyAnalyzer;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * BOM Generator strategy selection page.
 * 
 * @author njpatel
 * 
 */
public class StrategySelectionPage extends AbstractXpdWizardPage {

    private final BOMGenerator2ExtensionHelper extensionHelper;

    private final StrategySelectionListener selectionListener;

    /**
     * Available strategy radio buttons
     */
    private Button[] strategyOptions;

    /**
     * Currently selected resource
     */
    private BOMGenerator2Extension selectedStrategy;

    /**
     * The selected resources including any dependencies.
     */
    private List<IFile> selectedResources;

    /**
     * Cache for storing the result of validation. This is kept to avoid
     * re-validating the same resource selection against a given strategy.
     */
    private final Map<BOMGenerator2Extension, IStatus> validationResults;

    protected StrategySelectionPage(String pageName) {
        super(pageName);
        extensionHelper = BOMGenerator2ExtensionHelper.getInstance();
        selectionListener = new StrategySelectionListener();
        validationResults = new HashMap<BOMGenerator2Extension, IStatus>();
    }

    /**
     * Get the selected strategy.
     * 
     * @return
     */
    public BOMGenerator2Extension getSelectedStrategy() {
        return selectedStrategy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {
        setPageComplete(false);
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());
        Group grp = new Group(root, SWT.NONE);
        grp.setText(Messages.StrategySelectionPage_strategies_group_title);
        grp.setLayout(new GridLayout());
        grp.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

        Composite btnContainer = new Composite(grp, SWT.NONE);
        btnContainer.setLayout(new RowLayout(SWT.VERTICAL));
        btnContainer
                .setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

        BOMGenerator2Extension[] extensions = extensionHelper.getExtensions();
        if (extensions.length > 0) {
            strategyOptions = new Button[extensions.length];
            int idx = 0;
            for (BOMGenerator2Extension ext : extensions) {
                Button btn = new Button(btnContainer, SWT.RADIO);
                btn.setText(ext.getLabel());
                btn.setData(ext);
                btn.addSelectionListener(selectionListener);
                strategyOptions[idx++] = btn;
            }
        } else {
            Label lbl = new Label(btnContainer, SWT.NONE);
            lbl
                    .setText(Messages.StrategySelectionPage_noStrategiesAvailable_label);
        }

        setControl(root);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (visible) {
            try {
                initialize();
            } catch (InvocationTargetException e) {
                setErrorMessage(e.getLocalizedMessage());
                setPageComplete(false);
            } catch (InterruptedException e) {
                // Do nothing
            }
        } else {
            // Force the user on to this page to complete so that the latest
            // selection can be validated
            setPageComplete(false);
        }
    }

    /**
     * Initialize the strategies. This will: 1. run the dependency analyzer, 2.
     * Check if the available strategies support the selected resources, 3.
     * validate the currently selected strategy.
     * 
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    private void initialize() throws InvocationTargetException,
            InterruptedException {

        /*
         * Clear all validation and re-validate as the BOM resource selection
         * may have changed.
         */
        validationResults.clear();
        selectedResources = null;

        selectedResources = runDependencyAnalyzer();
        if (selectedResources != null) {

            /*
             * If there is only strategy available then select it by default
             */
            if (selectedStrategy == null && strategyOptions != null
                    && strategyOptions.length == 1) {
                selectedStrategy =
                        (BOMGenerator2Extension) strategyOptions[0].getData();
            }
            updateStrategies(strategyOptions, selectedResources);
            validate(selectedStrategy, selectedResources);
        }

    }

    /**
     * Run the dependency analyzer to get a list of all the files to process.
     * 
     * @throws InvocationTargetException
     * @throws InterruptedException
     * @return list of selected files including dependencies.
     */
    private List<IFile> runDependencyAnalyzer()
            throws InvocationTargetException, InterruptedException {
        final List<IFile> resources = new ArrayList<IFile>();
        // Run dependency analysis
        getContainer().run(false, false, new IRunnableWithProgress() {

            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                monitor
                        .beginTask(Messages.StrategySelectionPage_runningDependencyAnalysis_monitor_shortdesc,
                                IProgressMonitor.UNKNOWN);

                DependencyAnalyzer analyzer =
                        ((BOMGeneratorWizard) getWizard())
                                .runDependencyAnalyzer();
                if (analyzer != null) {
                    List<IFile> result = analyzer.getResult();
                    if (result != null) {
                        resources.addAll(result);
                    }
                }
            }
        });
        return resources;
    }

    /**
     * This will check if the available strategies can support the selected
     * resources. Any strategy that cannot support the selection will be
     * disabled.
     * 
     * @param strategyOptions
     * @param selectedResources
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    private void updateStrategies(final Button[] strategyOptions,
            final List<IFile> selectedResources)
            throws InvocationTargetException, InterruptedException {
        final List<Button> btnsToDisable = new ArrayList<Button>();
        getContainer().run(false, false, new IRunnableWithProgress() {

            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                for (Button strategyOpt : strategyOptions) {
                    Object data = strategyOpt.getData();
                    if (data instanceof BOMGenerator2Extension) {
                        BOMGenerator2Extension ext =
                                (BOMGenerator2Extension) data;
                        try {
                            BOMGenerator2 generator = ext.getGenerator();
                            if (!generator.supports(selectedResources, monitor)) {
                                // Disable the button
                                btnsToDisable.add(strategyOpt);
                                if (selectedStrategy == ext) {
                                    // Reset the strategy if it was previously
                                    // selected
                                    selectedStrategy = null;
                                }
                            }
                        } catch (CoreException e) {
                            Activator
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            String
                                                    .format(Messages.StrategySelectionPage_generatedReportedErrors_error_message,
                                                            ext.getLabel()));
                            btnsToDisable.add(strategyOpt);
                        }
                    }
                }
            }
        });

        for (Button btn : strategyOptions) {
            boolean enable = !btnsToDisable.contains(btn);
            btn.setEnabled(enable);
            btn.setSelection(enable && btn.getData() == selectedStrategy);
        }
    }

    /**
     * Run the validation and report to user if applicable. This will also
     * update the page complete status.
     */
    private void validate(final BOMGenerator2Extension strategy,
            final List<IFile> selectedFiles) {

        final IStatus[] status = new IStatus[] { null };
        String errMsg = null;

        if (strategy != null) {
            try {
                getContainer().run(false, false, new IRunnableWithProgress() {
                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {

                        try {
                            status[0] =
                                    strategy.getGenerator()
                                            .validate(selectedFiles, monitor);
                        } catch (CoreException e) {
                            status[0] =
                                    new Status(IStatus.ERROR,
                                            Activator.PLUGIN_ID, e
                                                    .getLocalizedMessage(), e);
                        }
                    }
                });
            } catch (InvocationTargetException e) {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String
                                        .format(Messages.StrategySelectionPage_validationErrorReported_error_shortdesc,
                                                selectedStrategy.getLabel()));

                errMsg =
                        Messages.StrategySelectionPage_validationErrorReportedSeeErrorLog_error_message;
            } catch (InterruptedException e) {
                errMsg = e.getLocalizedMessage();
            }
        }

        if (errMsg == null) {
            if (status[0] != null && !status[0].isOK()) {
                switch (status[0].getSeverity()) {
                case IStatus.ERROR:
                    errMsg = status[0].getMessage();
                    break;
                case IStatus.WARNING:
                    setMessage(status[0].getMessage(), WARNING);
                    break;
                case IStatus.INFO:
                    setMessage(status[0].getMessage(), INFORMATION);
                    break;
                case IStatus.CANCEL:
                    throw new OperationCanceledException(status[0].getMessage());
                }
            } else {
                // If some or all strategy options are disabled then inform user
                int disabledCount = 0;
                for (Button btn : strategyOptions) {
                    if (!btn.isEnabled()) {
                        ++disabledCount;
                    }
                }

                if (disabledCount > 0) {
                    String msg = null;
                    if (disabledCount == strategyOptions.length) {
                        // All buttons are disabled
                        msg =
                                Messages.StrategySelectionPage_noStrategySupportsSelection_shortdesc;
                    } else {
                        // Some buttons are disabled
                        msg =
                                Messages.StrategySelectionPage_someStrategiesDoNoSupportSelection_shortdesc;
                    }
                    setMessage(msg, WARNING);
                } else {
                    // Clear all messages
                    setMessage(null);
                }
            }
        }

        setErrorMessage(errMsg);
        setPageComplete(strategy != null && errMsg == null);
    }

    /**
     * Strategy button selection listener.
     * 
     * @author njpatel
     * 
     */
    private class StrategySelectionListener extends SelectionAdapter {
        @Override
        public void widgetSelected(SelectionEvent e) {
            Object data = e.widget.getData();
            if (data instanceof BOMGenerator2Extension) {
                selectedStrategy = (BOMGenerator2Extension) data;
                validate(selectedStrategy, selectedResources);
            }
        }
    }
}
