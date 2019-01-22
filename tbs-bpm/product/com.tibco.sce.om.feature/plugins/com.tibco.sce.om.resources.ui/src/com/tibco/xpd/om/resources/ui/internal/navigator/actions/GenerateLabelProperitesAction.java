/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Generates .properties file with externalized labels. The base name (without
 * extension) of the created file is the same as the generation source file.
 * <p>
 * <i>Created: 12 Feb 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class GenerateLabelProperitesAction extends BaseSelectionListenerAction {

    /** Properties file extension. */
    private static final String PROPERTIES_FILE_EXTENSION = "properties"; //$NON-NLS-1$

    /** Unique action identifier. */
    private static final String ID = OMResourcesUIActivator.PLUGIN_ID
            + ".generateLabelProperties"; //$NON-NLS-1$

    /**
     * The constructor
     */
    protected GenerateLabelProperitesAction() {
        super(Messages.GenerateLabelProperitesAction_generateLabels_menu);
        setId(ID);
        /*
         * XPD-5627: 'actionDefinitionId' should not be set otherwise the
         * undefined command is created on first access what results with
         * exception being thrown to the log.
         */

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        IStructuredSelection selection = getStructuredSelection();
        if (selection.isEmpty()
                || !(selection.getFirstElement() instanceof IFile)) {
            return;
        }
        final IFile modelFile = (IFile) selection.getFirstElement();
        final IFile propertiesFile =
                ResourcesPlugin
                        .getWorkspace()
                        .getRoot()
                        .getFile(modelFile.getFullPath().removeFileExtension()
                                .addFileExtension(PROPERTIES_FILE_EXTENSION));

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(modelFile);
        Properties resultProperties = null;
        if (wc instanceof OMWorkingCopy
                && ((OMWorkingCopy) wc).getRootElement() != null
                && ((OMWorkingCopy) wc).getRootElement().eResource() != null) {
            final Resource resource =
                    ((OMWorkingCopy) wc).getRootElement().eResource();
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();
            try {
                resultProperties =
                        (Properties) ed
                                .runExclusive(new RunnableWithResult.Impl() {
                                    /*
                                     * (non-Javadoc)
                                     * 
                                     * @see java.lang.Runnable#run()
                                     */
                                    @Override
                                    public void run() {
                                        setResult(OMUtil
                                                .generateLabels(resource));
                                    }
                                });
            } catch (InterruptedException e) {
                // TODO log it later
                e.printStackTrace();
            }
        }
        final Properties properties = resultProperties;

        final String jobDesc =
                Messages.GenerateLabelProperitesAction_generateJob_shortdesc;
        Job job = new WorkspaceJob(jobDesc) {
            @Override
            public IStatus runInWorkspace(IProgressMonitor monitor) {
                monitor.beginTask(jobDesc, IProgressMonitor.UNKNOWN);
                try {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    properties.store(os, null);
                    if (!propertiesFile.exists()) {
                        propertiesFile.create(new ByteArrayInputStream(os
                                .toByteArray()),
                                true,
                                new NullProgressMonitor());
                    } else {
                        if (overwriteFile(propertiesFile)) {
                            propertiesFile
                                    .setContents(new ByteArrayInputStream(os
                                            .toByteArray()),
                                            true,
                                            true,
                                            new NullProgressMonitor());
                        }
                    }
                } catch (final Exception e) {
                    OMResourcesUIActivator
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    "Exception thrown while generating labels."); //$NON-NLS-1$
                    return Status.CANCEL_STATUS;
                } finally {
                    monitor.done();
                }
                return Status.OK_STATUS;
            }

            private boolean overwriteFile(final IFile propertiesFile) {
                final boolean[] tempResult = new boolean[1];
                Display.getDefault().syncExec(new Runnable() {
                    @Override
                    public void run() {
                        Shell shell =
                                PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow().getShell();
                        String title =
                                Messages.GenerateLabelProperitesAction_overwriteMessageBox_title;
                        String message =
                                String.format(Messages.GenerateLabelProperitesAction_overwriteMessageBox_message,
                                        propertiesFile.getName());
                        boolean answer =
                                MessageDialog.openQuestion(shell,
                                        title,
                                        message);
                        synchronized (tempResult) {
                            tempResult[0] = answer;
                        }
                    }
                });
                boolean result = false;
                synchronized (tempResult) {
                    result = tempResult[0];
                }
                return result;
            }
        };
        if (properties != null) {
            IResourceRuleFactory ruleFactory =
                    ResourcesPlugin.getWorkspace().getRuleFactory();
            ISchedulingRule creatingRule =
                    ruleFactory.createRule(propertiesFile);
            ISchedulingRule modifyRule = ruleFactory.modifyRule(modelFile);
            job.setRule(MultiRule.combine(creatingRule, modifyRule));
            job.schedule();
        }
    }
}
