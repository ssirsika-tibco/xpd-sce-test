package com.tibco.xpd.fragments.internal.dialog;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.operations.DeleteOperation;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Delete dialog that will be shown to the user to confirm delete of a fragment
 * element. This will also show progress feedback to the user during the delete.
 * <p>
 * Use {@link #open(Shell, String, String, DeleteOperation)} to display this
 * dialog.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class DeleteDialog extends MessageDialog {

    private static final int WIDTH_HINT = 250;
    private static final int MIN_WIDTH = 150;

    private static int BAR_DLUS = 9;

    private ProgressIndicator progress;
    private final OperationProgressMonitor progressMonitor;
    private final DeleteOperation operation;
    private Label progressLbl;

    /**
     * Delete dialog that will run the given operation if the user selects OK.
     * 
     * @param shell
     *            parent shell
     * @param dialogTitle
     *            title
     * @param dialogMessage
     *            message to display in the dialog
     * @param operation
     *            operation to run when user selects OK.
     */
    public static void open(Shell shell, String dialogTitle,
            String dialogMessage, DeleteOperation operation) {
        DeleteDialog dlg = new DeleteDialog(shell, dialogTitle, dialogMessage,
                operation);
        dlg.open();
    }

    /**
     * Delete dialog.
     * 
     * @param shell
     *            parent shell
     * @param dialogTitle
     *            title
     * @param dialogMessage
     *            message to display in the dialog
     * @param operation
     *            operation to run when user selects OK
     */
    protected DeleteDialog(Shell shell, String dialogTitle,
            String dialogMessage, DeleteOperation operation) {

        // Cancel is the default selected button
        super(shell, dialogTitle, null, dialogMessage, QUESTION, new String[] {
                IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 1);

        Assert.isNotNull(operation, "Delete operation"); //$NON-NLS-1$

        this.operation = operation;
        progressMonitor = new OperationProgressMonitor();
    }

    @Override
    protected Control createCustomArea(Composite parent) {

        progressLbl = new Label(parent, SWT.NONE);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
        data.widthHint = WIDTH_HINT;
        data.minimumWidth = MIN_WIDTH;
        progressLbl.setLayoutData(data);

        progress = new ProgressIndicator(parent);
        GridData gd = new GridData();
        gd.heightHint = convertVerticalDLUsToPixels(BAR_DLUS);
        gd.horizontalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.horizontalSpan = 2;
        progress.setLayoutData(gd);

        return parent;
    }

    @Override
    protected void buttonPressed(int buttonId) {
        if (buttonId == 0 /* ok */) {
            // Disable the ok button
            Button button = getButton(0);
            if (button != null && !button.isDisposed()) {
                button.setEnabled(false);
            }
            try {
                ModalContext.run(new IRunnableWithProgress() {

                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {
                        if (operation != null) {
                            try {
                                FragmentsUtil.execute(operation, monitor);
                            } catch (ExecutionException e) {
                                throw new InvocationTargetException(e);
                            }
                        }
                    }

                }, true, progressMonitor, getShell().getDisplay());
            } catch (InvocationTargetException e) {
                ErrorDialog.openError(getShell(), Messages.DeleteDialog_title,
                        Messages.DeleteDialog_message, new Status(
                                IStatus.ERROR, FragmentsActivator.PLUGIN_ID, e
                                        .getLocalizedMessage(), e.getCause()));
            } catch (InterruptedException e) {
                // Do nothing
            }
        } else if (buttonId == 1) /* Cancelled pressed */{
            if (progressMonitor != null && !progress.isDisposed()) {
                progressMonitor.setCanceled(true);
            }
        }

        super.buttonPressed(buttonId);
    }

    /**
     * Set the given progress message
     * 
     * @param message
     */
    private void setProgressMessage(String message) {
        if (progressLbl != null && !progressLbl.isDisposed() && message != null) {
            progressLbl.setText(shortenText(message, progressLbl));
            progressLbl.update();
        }
    }

    /**
     * The operation progress monitor.
     * 
     * @author njpatel
     * 
     */
    private class OperationProgressMonitor implements IProgressMonitor {
        private final String DEFAULT_MSG = Messages.DeleteDialog_deleting_monitor_shortdesc;
        private boolean isCancelled = false;
        private String taskLabel;

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.runtime.IProgressMonitor#beginTask(java.lang.String,
         * int)
         */
        public void beginTask(String name, int totalWork) {
            if (progress != null && !progress.isDisposed()) {
                setTaskName(name);

                if (totalWork == UNKNOWN) {
                    progress.beginAnimatedTask();
                } else {
                    progress.beginTask(totalWork);
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.IProgressMonitor#done()
         */
        public void done() {
            if (progress != null && !progress.isDisposed()) {
                progress.sendRemainingWork();
                progress.done();
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.IProgressMonitor#internalWorked(double)
         */
        public void internalWorked(double work) {
            if (progress != null && !progress.isDisposed()) {
                progress.worked(work);
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.IProgressMonitor#isCanceled()
         */
        public boolean isCanceled() {
            return isCancelled;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.IProgressMonitor#setCanceled(boolean)
         */
        public void setCanceled(boolean value) {
            this.isCancelled = value;

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.runtime.IProgressMonitor#setTaskName(java.lang.String
         * )
         */
        public void setTaskName(String name) {
            if (progress != null && !progress.isDisposed()) {
                if (name != null && name.length() > 0) {
                    taskLabel = name;
                } else {
                    taskLabel = DEFAULT_MSG;
                }

                setProgressMessage(taskLabel);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.runtime.IProgressMonitor#subTask(java.lang.String)
         */
        public void subTask(String name) {
            if (progress != null && !progress.isDisposed() && name != null
                    && name.length() > 0) {
                setProgressMessage(String
                        .format("%1$s - %2$s", taskLabel, name)); //$NON-NLS-1$
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.IProgressMonitor#worked(int)
         */
        public void worked(int work) {
            internalWorked(work);
        }
    }
}
