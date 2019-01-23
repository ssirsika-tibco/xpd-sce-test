/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.progress.IProgressConstants;

import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.model.extension.DeploymentSimpleStatus;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus;
import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;
import com.tibco.xpd.deploy.model.extension.WrappedDeploymentException;
import com.tibco.xpd.deploy.model.extension.DeploymentStatus.Severity;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.wizards.deploy.DeploymentResultsDialog;
import com.tibco.xpd.deploy.util.DeploymentStatusUtil;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Deploys modules on server.
 * <p>
 * <i>Created: 28 Jan 2007</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployAction extends Action {

    /** Project Explorer view id. */
    private static final String PROJECT_EXPLORER_VIEW_ID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    private static final MutexRule MUTEX_RULE = new MutexRule();

    private static final Logger log =
            DeployUIActivator.getDefault().getLogger();

    private final Server server;

    private final List<URL> urls;

    private final boolean userJob;

    /**
     * Creates deployment action.
     * 
     * @param server
     *            the server to deploy.
     * @param modules
     *            the list of modules url's.
     * @param userJob
     *            if <code>true</code> then the job will be started as a user
     *            job displaying dialog to the user, otherwise it will be
     *            treated as a system job.
     */
    public DeployAction(Server server, List<URL> modules, boolean userJob) {
        super(Messages.DeployAction_ModulesDeployment_action);
        this.server = server;
        this.urls = modules;
        this.userJob = userJob;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        log.debug(String
                .format("Deployment on %1$s of modules: %2$s", server, urls)); //$NON-NLS-1$
        final DeploymentActionStatus status = new DeploymentActionStatus();
        Job job = new Job(getText()) {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                // ...
                // setProperty(IProgressConstants.ICON_PROPERTY,
                // getImage());
                IStatus returnStatus = Status.OK_STATUS;
                int scale = 100;
                int totalWork = (urls.size() * 2 + 2) * scale;
                monitor.beginTask(getText(), totalWork);
                try {
                    // preparation
                    monitor.worked(1 * scale);
                    for (URL url : urls) {
                        if (monitor.isCanceled()) {
                            status
                                    .add(new DeploymentSimpleStatus(
                                            Severity.CANCEL,
                                            Messages.DeployAction_OperationCancelled_shortdedc,
                                            null));
                            throw new OperationCanceledException();
                        }

                        URL publishedUrl;
                        try {
                            publishedUrl = publish(server, url);
                        } catch (WrappedDeploymentException e) {
                            String message =
                                    MessageFormat
                                            .format(Messages.DeployAction_CannotPublishModule_message,
                                                    url);
                            status.add(new DeploymentSimpleStatus(
                                    Severity.ERROR, message, e, true));
                            Throwable passedException =
                                    (e.getCause() != null) ? e.getCause() : e;
                            throw passedException;
                        } catch (Exception e) {
                            String message =
                                    MessageFormat
                                            .format(Messages.DeployAction_CannotPublishModule_message,
                                                    url);
                            status.add(new DeploymentSimpleStatus(
                                    Severity.ERROR, message, e, true));
                            throw e;
                        }
                        monitor.worked(1 * scale);

                        if (monitor.isCanceled()) {
                            throw new OperationCanceledException();
                        }
                        Connection connection = server.getConnection();
                        if (connection != null && connection.isConnected()) {
                            String decodedURL =
                                    URLDecoder.decode(publishedUrl.getPath());
                            boolean proceedDeployment =
                                    connection.validateModule(decodedURL);
                            if (proceedDeployment) {
                                try {
                                    DeploymentStatus result =
                                            connection
                                                    .deployModule(publishedUrl
                                                            .toString());
                                    status.add(result);
                                } catch (WrappedDeploymentException e) {
                                    String message =
                                            MessageFormat
                                                    .format(Messages.DeployAction_DeploymentException_message,
                                                            url);
                                    status.add(new DeploymentSimpleStatus(
                                            Severity.ERROR, message, e, true));
                                    Throwable passedException =
                                            (e.getCause() != null) ? e
                                                    .getCause() : e;
                                    throw passedException;
                                } catch (Exception e) {
                                    String message =
                                            MessageFormat
                                                    .format(Messages.DeployAction_DeploymentException_message,
                                                            url);
                                    status.add(new DeploymentSimpleStatus(
                                            Severity.ERROR, message, e, true));
                                    throw e;
                                }
                            }
                        }
                        monitor.worked(1 * scale);
                    }
                    // finishing
                    monitor.worked(1 * scale);
                } catch (OperationCanceledException e) {
                    returnStatus = Status.CANCEL_STATUS;
                } catch (Throwable e) {
                    String message =
                            Messages.DeployAction_ExceptionDuringDeployment_message;
                    returnStatus =
                            new Status(IStatus.OK, DeployUIActivator.PLUGIN_ID,
                                    message, e);
                } finally {
                    monitor.done();
                    Connection connection = server.getConnection();
                    if (connection != null) {
                        try {
                            connection.refreshServerContent();
                        } catch (ConnectionException e) {
                            handleConnectionExeption(e);
                        }
                    }
                }

                if (returnStatus.getSeverity() != IStatus.CANCEL
                        && status.getChildren().size() > 0) {
                    if (isModal(this)
                            || status.getSeverity()
                                    .compareTo(DeploymentStatus.Severity.INFO) > 0) {

                        // If not hiding the deployment result dialog then show
                        // it
                        if (DeployUIActivator.getDefault()
                                .showDeployResultDialog()) {
                            // The progress dialog is still open so
                            // just open the message
                            showResults(server, status);
                        } else {
                            setProperty(IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY,
                                    true);
                            setProperty(IProgressConstants.KEEPONE_PROPERTY,
                                    Boolean.TRUE);
                            setProperty(IProgressConstants.ACTION_PROPERTY,
                                    getDeploymentCompletedAction(server, status));

                            returnStatus =
                                    DeploymentStatusUtil.adaptToIStatus(status,
                                            DeployUIActivator.PLUGIN_ID,
                                            IStatus.OK);
                        }

                    } else {
                        setProperty(IProgressConstants.KEEPONE_PROPERTY,
                                Boolean.TRUE);
                        setProperty(IProgressConstants.ACTION_PROPERTY,
                                getDeploymentCompletedAction(server, status));

                    }

                }
                return returnStatus;
            }

            public boolean isModal(Job job) {
                Boolean isModal =
                        (Boolean) job
                                .getProperty(IProgressConstants.PROPERTY_IN_DIALOG);
                if (isModal == null) {
                    // it will make us sure that dialog will not be
                    // displayed if backgroud job.
                    return false;
                }
                return isModal.booleanValue();
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.core.runtime.jobs.Job#belongsTo(java.lang.Object)
             */
            @Override
            public boolean belongsTo(Object family) {
                return true;
            }
        };
        job.setRule(getMutexRule());
        job.setUser(userJob);
        job.schedule();

    }

    private URL publish(Server server, URL url) {
        URL returnUrl = url;
        Repository repo = server.getRepository();
        if (repo != null) {
            // Send module to repository.
            // Obtain repository publisher and config form server.
            RepositoryPublisher repoPublisher =
                    repo.getRepositoryType().getRepositoryPublisher();
            File file = new File(url.getPath());
            repoPublisher.publish(repo.getRepositoryConfig(), file);
            // Determine module repository inquiry url.
            returnUrl =
                    repoPublisher.getInquiryUrl(repo.getRepositoryConfig(),
                            file);
        }
        if (returnUrl == null) {
            throw new RuntimeException(
                    Messages.DeployAction_CannotObtainInquiryURL_message);
        }
        return returnUrl;
    }

    private Action getDeploymentCompletedAction(final Server server,
            final DeploymentActionStatus status) {
        return new Action(Messages.DeployAction_ViewDeploymentStatus) {
            @Override
            public void run() {
                Shell shell =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell();
                DeploymentResultsDialog dialog =
                        new DeploymentResultsDialog(shell, status);
                dialog.open();
            }
        };
    }

    private void showResults(final Server server,
            final DeploymentActionStatus status) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                getDeploymentCompletedAction(server, status).run();
            }
        });
    }

    private void handleConnectionExeption(final ConnectionException e) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                Shell s =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell();
                String title =
                        String
                                .format(Messages.DeployAction_ServerConnectionProblem_title);
                String message = String.format(e.getMessage());

                Throwable cause = e.getCause();
                if (cause != null) {
                    if (cause.getMessage() != null
                            && cause.getMessage().trim().length() > 0) {
                        message +=
                                String
                                        .format(Messages.DeployAction_ServerConnectionProblemReason_message,
                                                cause.getMessage());
                    }
                }
                MessageDialog.openError(s, title, message);
                // Reset selection to the disconnected server to refresh
                // properties.
                try {
                    CommonNavigator navigator =
                            (CommonNavigator) PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage()
                                    .showView(PROJECT_EXPLORER_VIEW_ID);
                    if (navigator != null) {
                        CommonViewer commonViewer = navigator.getCommonViewer();
                        commonViewer.setSelection(StructuredSelection.EMPTY);
                        commonViewer.setSelection(new StructuredSelection(
                                server));
                    }
                } catch (PartInitException e1) {
                    // intentionally do nothing except logging.
                    DeployUIActivator.getDefault().getLog().log(e1.getStatus());
                }
            }
        });
    }

    public static MutexRule getMutexRule() {
        return MUTEX_RULE;
    }

    private static class MutexRule implements ISchedulingRule {
        public boolean isConflicting(ISchedulingRule rule) {
            return rule == this;
        }

        public boolean contains(ISchedulingRule rule) {
            return rule == this;
        }
    }
}
