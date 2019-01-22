/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.utils;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * A monitor dialog that shows the task message and a progress bar.
 * 
 * @author njpatel
 * 
 */
public class MonitorDialog extends Dialog {

    private Label lbl;

    /**
     * The progress monitor.
     */
    private ProgressMonitor progressMonitor = new ProgressMonitor();

    private ProgressIndicator progressIndicator;

    public MonitorDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(SWT.APPLICATION_MODAL | SWT.TOOL);
        setBlockOnOpen(false);
    }

    public void run(IRunnableWithProgress runnable)
            throws InvocationTargetException, InterruptedException {

        try {
            open();
            // Let the progress monitor know if they need to update in UI
            // Thread
            ModalContext.run(runnable, true, progressMonitor, getShell()
                    .getDisplay());
        } finally {
            close();
        }
    }

    /**
     * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
     * 
     */
    @Override
    protected void handleShellCloseEvent() {

        if (!progressMonitor.isCanceled()) {
            progressMonitor.setCanceled(true);
        }

        super.handleShellCloseEvent();
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());
        root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        root.setBackground(parent.getBackground());

        lbl = new Label(root, SWT.NONE);
        lbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        lbl.setBackground(root.getBackground());

        progressIndicator = new ProgressIndicator(root, SWT.BORDER);
        progressIndicator.setBackground(root.getBackground());
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        // gd.heightHint = convertVerticalDLUsToPixels(9);
        progressIndicator.setLayoutData(gd);

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#getInitialSize()
     */
    @Override
    protected Point getInitialSize() {
        Point calculatedSize = super.getInitialSize();
        if (calculatedSize.x < 450) {
            calculatedSize.x = 450;
        }
        return calculatedSize;
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setBackground(new Color(newShell.getDisplay(), 203, 225, 252));
    }

    /**
     * Internal progress monitor implementation.
     */
    private class ProgressMonitor implements IProgressMonitorWithBlocking {

        private boolean fIsCanceled;

        /**
         * is the process forked
         */
        protected boolean forked = false;

        /**
         * is locked
         */
        protected boolean locked = false;

        private String task;

        @Override
        public void beginTask(String name, int totalWork) {
            if (progressIndicator.isDisposed()) {
                return;
            }
            if (name != null && name.length() > 0) {
                task = name != null ? name : ""; //$NON-NLS-1$
                setMessage(task);
            }
            if (totalWork == UNKNOWN) {
                progressIndicator.beginAnimatedTask();
            } else {
                progressIndicator.beginTask(totalWork);
            }
        }

        @Override
        public void done() {
            if (!progressIndicator.isDisposed()) {
                progressIndicator.sendRemainingWork();
                progressIndicator.done();
            }
        }

        @Override
        public void setTaskName(String name) {
            if (name != null && name.length() > 0) {
                task = name != null ? name : ""; //$NON-NLS-1$
                setMessage(task);
            }
        }

        @Override
        public boolean isCanceled() {
            return fIsCanceled;
        }

        @Override
        public void setCanceled(boolean b) {
            fIsCanceled = b;
            if (locked) {
                clearBlocked();
            }
        }

        @Override
        public void subTask(String name) {
            String msg = task;
            if (msg != null) {
                msg += name;
            } else {
                msg = name;
            }
            setMessage(msg);
        }

        @Override
        public void worked(int work) {
            internalWorked(work);
        }

        @Override
        public void internalWorked(double work) {
            if (!progressIndicator.isDisposed()) {
                progressIndicator.worked(work);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.runtime.IProgressMonitorWithBlocking#clearBlocked ()
         */
        @Override
        public void clearBlocked() {
            if (getShell().isDisposed())
                return;
            locked = false;
            progressIndicator.showNormal();
            setMessage(task);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.runtime.IProgressMonitorWithBlocking#setBlocked
         * (org.eclipse.core.runtime.IStatus)
         */
        @Override
        public void setBlocked(IStatus reason) {
            if (getShell().isDisposed())
                return;
            locked = true;
            progressIndicator.showPaused();
            setMessage(reason.getMessage());
        }

        private void setMessage(final String msg) {
            Shell shell = getShell();

            if (shell != null && shell.getDisplay() != null) {
                shell.getDisplay().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        if (lbl != null && !lbl.isDisposed()) {
                            String shortMsg = shortenText(msg, lbl);
                            lbl.setText(shortMsg != null ? shortMsg : ""); //$NON-NLS-1$
                            lbl.update();
                        }
                    }
                });
            }
        }
    }

}