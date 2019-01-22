/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.IThreadListener;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

/**
 * WorkspaceModifyOperation in Eclipse 3.7 causes {@link IDEWorkbenchPlugin} to
 * load, which activates UI effectively killing any non-ui Eclipse application.
 * This implementation removes any references to IDEWorkbenchPlugin.
 * 
 * @see WorkspaceModifyOperation
 */
public abstract class SafeWorkspaceModifyOperation implements
        IRunnableWithProgress, IThreadListener {
    private ISchedulingRule rule;

    /**
     * @see WorkspaceModifyOperation
     */
    protected SafeWorkspaceModifyOperation() {
        this(ResourcesPlugin.getWorkspace().getRoot());
    }

    /**
     * @see WorkspaceModifyOperation
     */
    public SafeWorkspaceModifyOperation(ISchedulingRule rule) {
        this.rule = rule;
    }

    /**
     * @see WorkspaceModifyOperation
     */
    protected abstract void execute(IProgressMonitor mon) throws CoreException,
            InvocationTargetException, InterruptedException;

    @Override
    public synchronized final void run(IProgressMonitor mon)
            throws InvocationTargetException, InterruptedException {
        final InvocationTargetException[] lastException =
                new InvocationTargetException[1];
        try {
            IWorkspaceRunnable r = new IWorkspaceRunnable() {
                @Override
                public void run(IProgressMonitor mon) throws CoreException {
                    try {
                        execute(mon);
                    } catch (InterruptedException ex) {
                        throw new OperationCanceledException(ex.getMessage());
                    } catch (InvocationTargetException ex) {
                        lastException[0] = ex;
                    }
                }
            };

            ResourcesPlugin.getWorkspace().run(r, rule, IResource.NONE, mon);
        } catch (CoreException ex) {
            throw new InvocationTargetException(ex);
        } catch (OperationCanceledException ex) {
            throw new InterruptedException(ex.getLocalizedMessage());
        }

        if (lastException[0] != null) {
            throw lastException[0];
        }
    }

    /**
     * @see WorkspaceModifyOperation
     */
    @Override
    public void threadChange(Thread t) {
        if (rule == null)
            return;

        Job currentJob = Job.getJobManager().currentJob();
        if (currentJob == null)
            return;

        ISchedulingRule currentRule = currentJob.getRule();
        if (currentRule == null)
            return;

        throw new IllegalStateException(
                "Cannot fork thread from a thread that is owning the rule"); //$NON-NLS-1$
    }

    /**
     * @see WorkspaceModifyOperation
     */
    public ISchedulingRule getRule() {
        return rule;
    }

}
