/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import com.tibco.xpd.rasc.ui.Messages;

/**
 * Progress monitor that can be linked to a ProgressBar and Label.
 *
 * @author nwilson
 * @since 8 Mar 2019
 */
public class ProgressBarMonitor implements IProgressMonitor {

    private ProgressBar bar;

    private Label label;

    private int totalWork;

    private int current = 0;

    private boolean cancelled = false;

    public ProgressBarMonitor(ProgressBar bar, Label label) {
        this.bar = bar;
        this.label = label;
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#beginTask(java.lang.String,
     *      int)
     */
    @Override
    public void beginTask(String name, int totalWork) {
        this.totalWork = totalWork;
        bar.getDisplay().syncExec(() -> {
            bar.setMaximum(totalWork);
            label.setText(name);
        });
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#done()
     *
     */
    @Override
    public void done() {
        bar.getDisplay().syncExec(() -> {
            bar.setSelection(totalWork);
            label.setText(Messages.ProgressBarMonitor_ExportComplete0);
        });
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#internalWorked(double)
     */
    @Override
    public void internalWorked(double work) {
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#isCanceled()
     */
    @Override
    public boolean isCanceled() {
        return cancelled;
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#setCanceled(boolean)
     */
    @Override
    public void setCanceled(boolean value) {
        cancelled = value;
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#setTaskName(java.lang.String)
     */
    @Override
    public void setTaskName(String name) {
        updateText(name);
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#subTask(java.lang.String)
     */
    @Override
    public void subTask(String name) {
        updateText(name);
    }

    /**
     * @param name
     */
    private void updateText(String name) {
        Display display = bar.getDisplay();
        if (display != null && !display.isDisposed()) {
            display.syncExec(() -> label.setText(name));
        }
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#worked(int)
     */
    @Override
    public void worked(int work) {
        current += work;
        Display display = bar.getDisplay();
        if (display != null && !display.isDisposed()) {
            display.syncExec(() -> bar.setSelection(current));
        }
    }

}
