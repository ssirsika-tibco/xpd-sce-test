/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.tibco.xpd.resources.util.SubProgressMonitorEx.MonitorTaskNameStyle;

/**
 * This test example shows how to use the {@link SubProgressMonitorEx} class and
 * what the different {@link MonitorTaskNameStyle}'s do.
 * 
 * 
 * @author aallway
 * @since v3.5.3
 */
public class SubProgressMonitorExTestExample {

    private MonitorTaskNameStyle monitorTaskNameStyleToUse;

    private boolean grandChildSetsSubTasks;

    /**
     * @param styleToUse
     */
    public SubProgressMonitorExTestExample(
            MonitorTaskNameStyle monitorTaskNameStyleToUse,
            boolean grandChildSetsSubTasks) {
        super();
        this.monitorTaskNameStyleToUse = monitorTaskNameStyleToUse;
        this.grandChildSetsSubTasks = grandChildSetsSubTasks;
    }

    public void runTest(IProgressMonitor monitor) {

        /*
         * For the top level main job we always create a Main progress monitor
         * (so that any existing task name label is wiped out. (in this example
         * we're telling the child class
         */
        try {
            monitor.beginTask("", 3);
            monitor.subTask("");

            for (int i = 0; i < 3; i++) {
                sleep(500);

                /**
                 * can use SubProgressMonitorEx.createMainProgressMonitor() as a
                 * shortcut.
                 */
                doMainStuff(new SubProgressMonitorEx(monitor, 1,
                        MonitorTaskNameStyle.MAIN), i);

                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }
        } finally {
            monitor.done();
        }

    }

    private void doMainStuff(IProgressMonitor monitor, int jobNum) {

        try {
            /* Do 5 child jobs. */
            monitor.beginTask(String.format("Main Job", jobNum), 3);

            for (int i = 0; i < 3; i++) {
                /**
                 * Create a sub monitor in the style requested when test class
                 * constructed. SubProgressMonitorEx.createXXXXX() can be used
                 * as a shortcut.
                 */
                sleep(300);

                doChildJob(new SubProgressMonitorEx(monitor, 1,
                        monitorTaskNameStyleToUse), i);

                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }

        } finally {
            monitor.done();
        }
    }

    /**
     * @param subProgressMonitorEx
     */
    private void doChildJob(IProgressMonitor monitor, int jobNum) {
        try {
            /* Do 10 grand child jobs. */
            monitor.beginTask(String.format("Child Job %d", jobNum), 10);

            for (int i = 0; i < 10; i++) {
                /**
                 * Create a sub monitor in the style requested when test class
                 * constructed. SubProgressMonitorEx.createXXXXX() can be used
                 * as a shortcut.
                 */
                sleep(300);

                doGrandChildJob(new SubProgressMonitorEx(monitor, 1,
                        monitorTaskNameStyleToUse), i);

                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }

        } finally {
            monitor.done();
        }
    }

    /**
     * @param subProgressMonitorEx
     */
    private void doGrandChildJob(IProgressMonitor monitor, int jobNum) {
        try {
            /* Do 20 quick ticks for grand child sub tasks. */
            monitor.beginTask(String.format("GrandChild Job %d", jobNum), 20);

            for (int i = 0; i < 20; i++) {
                sleep(10);

                if (grandChildSetsSubTasks) {
                    monitor.subTask(String.format("GrandChild SubTask %d", i));
                }
                monitor.worked(1);

                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }

        } finally {
            monitor.done();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
