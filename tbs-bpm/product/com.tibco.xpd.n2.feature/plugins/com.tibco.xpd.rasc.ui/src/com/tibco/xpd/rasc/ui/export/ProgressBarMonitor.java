/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.ProgressBar;

/**
 *
 *
 * @author nwilson
 * @since 8 Mar 2019
 */
public class ProgressBarMonitor implements IProgressMonitor {

    private ProgressBar bar;

    private int totalWork;

    private int current = 0;

    private boolean cancelled = false;

    public ProgressBarMonitor(ProgressBar bar) {
        this.bar = bar;
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#beginTask(java.lang.String,
     *      int)
     *
     * @param name
     * @param totalWork
     */
    @Override
    public void beginTask(String name, int totalWork) {
        this.totalWork = totalWork;
        bar.getDisplay().syncExec(() -> bar.setMaximum(totalWork));
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#done()
     *
     */
    @Override
    public void done() {
        bar.getDisplay().syncExec(() -> bar.setSelection(totalWork));
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#internalWorked(double)
     *
     * @param work
     */
    @Override
    public void internalWorked(double work) {
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#isCanceled()
     *
     * @return
     */
    @Override
    public boolean isCanceled() {
        return cancelled;
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#setCanceled(boolean)
     *
     * @param value
     */
    @Override
    public void setCanceled(boolean value) {
        cancelled = value;
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#setTaskName(java.lang.String)
     *
     * @param name
     */
    @Override
    public void setTaskName(String name) {
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#subTask(java.lang.String)
     *
     * @param name
     */
    @Override
    public void subTask(String name) {
    }

    /**
     * @see org.eclipse.core.runtime.IProgressMonitor#worked(int)
     *
     * @param work
     */
    @Override
    public void worked(int work) {
        current += work;
        bar.getDisplay().syncExec(() -> bar.setSelection(current));
    }

}
