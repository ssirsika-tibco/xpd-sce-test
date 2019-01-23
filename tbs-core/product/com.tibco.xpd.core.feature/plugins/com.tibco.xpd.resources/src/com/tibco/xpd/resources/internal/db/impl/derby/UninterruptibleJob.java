/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.internal.db.impl.derby;

import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.resources.internal.db.ResourceDbException;

/**
 * Class used to wrap the execution of DB calls to prevent the thread making the
 * call from being interrupted. Interruptions would cause the DB call to be
 * aborted leaving the database out of sync with the workspace content.
 * 
 * @author nwilson
 * @since 13 Feb 2015
 */
public abstract class UninterruptibleJob<T> {

    public T start() throws ResourceDbException {
        T result = null;
        if (Display.getCurrent() == null) {
            result = run();
        } else {
            boolean complete = false;
            JobWithResult job = new JobWithResult(this);
            Thread t = new Thread(job);
            t.start();
            while (!complete) {
                try {
                    t.join();
                    if (job.e != null) {
                        throw job.e;
                    }
                    result = job.result;
                    complete = true;
                } catch (InterruptedException e) {
                    // Ignore, will retry join.
                }
            }
        }
        return result;
    }

    protected abstract T run() throws ResourceDbException;

    class JobWithResult implements Runnable {
        private T result;

        private ResourceDbException e;

        private UninterruptibleJob<T> job;

        /**
         * @param name
         */
        public JobWithResult(UninterruptibleJob<T> job) {
            this.job = job;
        }

        /**
         * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param monitor
         * @return
         */
        @Override
        public void run() {
            try {
                result = job.run();
            } catch (ResourceDbException e) {
                this.e = e;
            }
        }

    }
}
