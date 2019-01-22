/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * Sub-progress monitor that enforces update of the task-name-label when the
 * according to a given {@link MonitorTaskNameStyle} when the
 * {@link #beginTask(String, int)} method is called.
 * <p>
 * This allows (a) the creator of the {@link SubProgressMonitorEx} to decide
 * what is done with the sub-monitors labels and (b) saves the method passed the
 * monitor having to know whether it needs to call {@link #setTaskName(String)}
 * or {@link #subTask(String)}. Also calling #done on an
 * {@link SubProgressMonitorEx} will wipe the task name (so that that your task
 * will not get the blame for time taken by subsequent tasks that do not set the
 * task name!
 * <p>
 * The static helper functions
 * {@link #createMainProgressMonitor(IProgressMonitor, int)},
 * {@link #createNestedSubProgressMonitor(SubProgressMonitorEx, int)} and
 * {@link #createSubTaskProgressMonitor(IProgressMonitor, int)} can help in
 * setting up various styles of progress monitoring.
 * <p>
 * For certain use cases there is a problem with the sub progress monitor
 * strategy. The standard {@link SubProgressMonitor#beginTask(String, int)}
 * explicitly does not set the task name label or subTask label. However,
 * nominally, it is a calling method that creates the monitor and the
 * called-method that performs the begin task. So therefore there is a situation
 * that the calling-method cannot specify how the task label should be treated
 * and the called method does not know whether it has to call
 * setTaskName/subTask (because it may not know whether it is being called as
 * "the main thing to do" or the "a sub task".
 * <p>
 * {@link SubProgressMonitorEx} allows the creator to decide what happens when
 * the sub-method calls {@link #beginTask(String, int)}. It can decide that the
 * task-name parameter is used to...
 * <li>Replace the whole task name/subtask name:
 * {@link MonitorTaskNameStyle#MAIN}</li>
 * <li>Append the task to the parent's task-name:
 * {@link MonitorTaskNameStyle#NESTED}</li>
 * <li>Use the task label to set the sub-task name instead (there is only one
 * subtask name irrespective of how many monitors set the name - so the ui will
 * normally just append thesubTask name to the main task name).
 * {@link MonitorTaskNameStyle#SUBTASK}</li>
 * 
 * @author aallway
 * @since v3.5.3
 */
public class SubProgressMonitorEx extends SubProgressMonitor {

    /**
     * Create a {@link SubProgressMonitorEx} that will act as the top-level of a
     * set of monitor task names i.e. one whose {@link #beginTask(String, int)}
     * method will replace the existing task name.
     * 
     * 
     * @param monitor
     * @param ticks
     * @return A {@link SubProgressMonitorEx} that will act as the top-level of
     *         a set of monitor task names.
     */
    public static SubProgressMonitorEx createMainProgressMonitor(
            IProgressMonitor monitor, int ticks) {
        return new SubProgressMonitorEx(monitor, ticks,
                MonitorTaskNameStyle.MAIN);
    }

    /**
     * Create a {@link SubProgressMonitorEx} that will act as a nested child of
     * the parent {@link SubProgressMonitorEx} i.e. one whose
     * {@link #beginTask(String, int)} method will be appended to the parent
     * task name.
     * <p>
     * e.g. If the parent's task name is "Main Job1" then a child's is
     * beginTask("Child Job"), then a grandchild's is
     * beginTask("GrandChild Job") then this will result in task name of
     * "Main Job1... Child Job... GrandChildJob...".
     * <p>
     * All of monitors must be instanceof {@link SubProgressMonitorEx} and at
     * least the child/grandchild (i.e. all the nested) must be created with
     * this method OR constructed with the {@link MonitorTaskNameStyle#NESTED}
     * 
     * @param monitor
     * @param ticks
     * @param nestingDelimiter
     *            String to suffix parent label with.
     * 
     * @return A {@link SubProgressMonitorEx} that will act as the top-level of
     *         a set of monitor task names.
     */
    public static SubProgressMonitorEx createNestedSubProgressMonitor(
            IProgressMonitor monitor, int ticks) {
        return new SubProgressMonitorEx(monitor, ticks,
                MonitorTaskNameStyle.NESTED);
    }

    /**
     * Create a {@link SubProgressMonitorEx} that will simply set the subTask
     * from the task name passed to {@link #beginTask(String, int)}
     * <p>
     * i.e. Most monitor ui's will simply append
     * "the last subtask name to be set" to the end of the main task name. So if
     * you have done {@link #createMainProgressMonitor(IProgressMonitor, int)}
     * with "Main Job", and then created a nested {@link SubProgressMonitor}
     * with {@link #createNestedSubProgressMonitor(SubProgressMonitorEx, int)}
     * as "Nested Job" and then from that use
     * {@link #createSubTaskProgressMonitor(SubProgressMonitorEx, int)} with
     * "SubTask Name" then it will result in a task name label in the monitor ui
     * of "Main Job... Nested Job: SubTask Name"
     * <p>
     * When you call {@link #done()} on the SubTaskProgressMonitor the
     * ": SubTask Name" will be removed - but the previous part of label will
     * remain there. Create another SubTaskProgressMonitor and it's label will
     * be appended instead and so on.
     * 
     * @param monitor
     * @param ticks
     * @return A {@link SubProgressMonitorEx} that will act as the top-level of
     *         a set of monitor task names.
     */
    public static SubProgressMonitorEx createSubTaskProgressMonitor(
            IProgressMonitor monitor, int ticks) {
        return new SubProgressMonitorEx(monitor, ticks,
                MonitorTaskNameStyle.SUBTASK);
    }

    /**
     * 
     * @param monitor
     * @param ticks
     * @return A progress monitor that does not update the task name label on
     *         {@link #beginTask(String, int)}
     */
    public static SubProgressMonitorEx createUnlabelledProgressMonitor(
            IProgressMonitor monitor, int ticks) {
        return new SubProgressMonitorEx(monitor, ticks,
                MonitorTaskNameStyle.NO_LABEL);
    }

    /**
     * How to handle setting of task name label when
     * {@link SubProgressMonitorEx#beginTask(String, int)} is called.
     * 
     * 
     * @author aallway
     * @since 13 Jan 2012
     */
    public enum MonitorTaskNameStyle {
        /**
         * The label given to
         * {@link SubProgressMonitorEx#beginTask(String, int)} replaces the
         * entire label in monitor.
         */
        MAIN,

        /**
         * The label given to
         * {@link SubProgressMonitorEx#beginTask(String, int)} is prefixed with
         * the main task name of the nearest ancestor that is a
         * {@link SubProgressMonitorEx} (if no {@link SubProgressMonitorEx}
         * ancestor monitor is found then this behaves as
         * {@link MonitorTaskNameStyle#MAIN}
         */
        NESTED,

        /**
         * The label given to
         * {@link SubProgressMonitorEx#beginTask(String, int)} sets the subTask
         * label (which should leave the main label untouched)
         */
        SUBTASK,

        /**
         * Does not automatically set label on
         * {@link SubProgressMonitorEx#beginTask(String, int)} - use this when
         * you want a sub-progress monitor that will keep things "ticking" but
         * leaves the label as is.
         */
        NO_LABEL
    }

    private IProgressMonitor parentMonitor;

    private MonitorTaskNameStyle monitorTaskNameStyle;

    private String nestingDelimiter = ""; //$NON-NLS-1$

    private String mainTaskName = ""; //$NON-NLS-1$;

    /**
     * @param monitor
     * @param ticks
     * @param style
     * @param nestingDelimiter
     *            Only applies to {@link MonitorTaskNameStyle#NESTED}
     */
    public SubProgressMonitorEx(IProgressMonitor monitor, int ticks,
            MonitorTaskNameStyle labelStyle, String nestingDelimiter) {
        super(monitor, ticks, 0);

        parentMonitor = monitor;

        this.monitorTaskNameStyle = labelStyle;

        this.nestingDelimiter = nestingDelimiter;
    }

    /**
     * @param monitor
     * @param ticks
     * @param style
     */
    public SubProgressMonitorEx(IProgressMonitor monitor, int ticks,
            MonitorTaskNameStyle labelStyle) {
        this(monitor, ticks, labelStyle, " - "); //$NON-NLS-1$
    }

    public SubProgressMonitorEx(IProgressMonitor monitor, int ticks) {
        this(monitor, ticks, MonitorTaskNameStyle.SUBTASK);
    }

    /**
     * @see org.eclipse.core.runtime.SubProgressMonitor#beginTask(java.lang.String,
     *      int)
     * 
     * @param name
     * @param totalWork
     */
    @Override
    public void beginTask(String name, int totalWork) {
        if (MonitorTaskNameStyle.NESTED.equals(monitorTaskNameStyle)) {
            mainTaskName = name;

            String parentTaskName = getParentTaskName();

            if (parentTaskName != null && parentTaskName.length() > 0) {
                if (mainTaskName != null && mainTaskName.length() > 0) {
                    mainTaskName = parentTaskName + nestingDelimiter + name;
                } else {
                    mainTaskName = parentTaskName;
                }
            }

            super.beginTask(mainTaskName, totalWork);

            super.setTaskName(mainTaskName);

            /* Force initial unset of the sub-task label. */
            super.subTask(""); //$NON-NLS-1$

        } else if (MonitorTaskNameStyle.MAIN.equals(monitorTaskNameStyle)) {
            mainTaskName = name;

            super.beginTask(mainTaskName, totalWork);

            super.setTaskName(mainTaskName);

            /* Force initial unset of the sub-task label. */
            super.subTask(""); //$NON-NLS-1$

        } else if (MonitorTaskNameStyle.SUBTASK.equals(monitorTaskNameStyle)) {
            super.beginTask(mainTaskName, totalWork);

            /* Only output subtask if we were given a name. */
            if (name != null && name.length() > 0) {
                super.subTask(name);
            }

        } else {
            super.beginTask("", totalWork); //$NON-NLS-1$

        }
    }

    /**
     * @return The main task name of the nearest {@link SubProgressMonitorEx}
     *         monitor or <code>null</code> if no {@link SubProgressMonitorEx}
     *         ancestor found.
     */
    private String getParentTaskName() {
        String parentTaskName = null;

        IProgressMonitor wrappedProgressMonitor = parentMonitor;

        /* Find the first SubProgressMonitorEx ancestor with a task name set. */
        while ((wrappedProgressMonitor instanceof ProgressMonitorWrapper)) {
            if (wrappedProgressMonitor instanceof SubProgressMonitorEx) {
                if (((SubProgressMonitorEx) wrappedProgressMonitor).mainTaskName != null
                        && ((SubProgressMonitorEx) wrappedProgressMonitor).mainTaskName
                                .length() > 0) {

                    parentTaskName =
                            ((SubProgressMonitorEx) wrappedProgressMonitor).mainTaskName;
                    break;
                }
            }

            IProgressMonitor ancestor =
                    ((ProgressMonitorWrapper) wrappedProgressMonitor)
                            .getWrappedProgressMonitor();

            if (ancestor == wrappedProgressMonitor) {
                // URK!
                break;
            }
            wrappedProgressMonitor = ancestor;
        }

        return parentTaskName;
    }

    /**
     * @see org.eclipse.core.runtime.ProgressMonitorWrapper#setTaskName(java.lang.String)
     * 
     * @param name
     */
    @Override
    public void setTaskName(String name) {
        if (MonitorTaskNameStyle.SUBTASK.equals(monitorTaskNameStyle)) {
            super.subTask(name);

        } else {
            super.setTaskName(name);
        }
    }

    /**
     * @see org.eclipse.core.runtime.SubProgressMonitor#done()
     * 
     */
    @Override
    public void done() {
        if (MonitorTaskNameStyle.NESTED.equals(monitorTaskNameStyle)) {
            String parentTaskName = getParentTaskName();

            setTaskName(parentTaskName != null ? parentTaskName : ""); //$NON-NLS-1$

        } else if (MonitorTaskNameStyle.MAIN.equals(monitorTaskNameStyle)) {
            setTaskName(""); //$NON-NLS-1$
        }

        super.done();

    }
}
