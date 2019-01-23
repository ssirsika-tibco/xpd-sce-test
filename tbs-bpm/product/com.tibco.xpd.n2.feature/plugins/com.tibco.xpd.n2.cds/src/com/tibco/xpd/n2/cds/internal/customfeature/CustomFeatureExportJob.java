/*******************************************************************************
 * (c) Copyright 2010, TIBCO Software Inc.  All rights reserved.
 * LEGAL NOTICE:  This source code is provided to specific authorized end
 * users pursuant to a separate license agreement.  You MAY NOT use this
 * source code if you do not have a separate license from TIBCO Software Inc.  
 * Except as expressly set forth in such license agreement, this source code, 
 * or any portion thereof, may not be used, modified, reproduced, transmitted,
 * or distributed in any form or by any means, electronic or mechanical, 
 * without written permission from  TIBCO Software Inc.
 *  
 *******************************************************************************/
package com.tibco.xpd.n2.cds.internal.customfeature;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.pde.internal.core.exports.FeatureExportInfo;
import org.eclipse.pde.internal.core.exports.FeatureExportOperation;
import org.eclipse.pde.internal.core.exports.PluginExportOperation;

import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.internal.Messages;

/**
 * This is a clone of FeatureExportJob. FeatureExportJob wants to put a dialog
 * up, the raising of the dialog can cause a deadlock as we invoke this from
 * wizards and commandline tools etc.
 * 
 * The basic change involves just making sure the feature/plugin export simply
 * generates an IStatus.
 */
@SuppressWarnings("restriction")
public class CustomFeatureExportJob extends Job {

    public static class MutexRule implements ISchedulingRule {
        public boolean isConflicting(ISchedulingRule rule) {
            return rule == this;
        }

        public boolean contains(ISchedulingRule rule) {
            return rule == this;
        }
    }

    private static final MutexRule MUTEX_RULE = new MutexRule();

    protected FeatureExportInfo info;

    private boolean isPluginJob;

    private boolean hasAntErrors;

    public CustomFeatureExportJob(FeatureExportInfo info, boolean isPluginJob) {
        super(Messages.CustomFeatureExportJob_ExportPlugins_message);
        this.info = info;
        this.isPluginJob = isPluginJob;
        setRule(MUTEX_RULE);
    }

    @Override
    protected IStatus run(final IProgressMonitor monitor) {
        /*
         * Operations need to be inherited to bump their run methods visibility
         * to public.
         */
        if (isPluginJob) {
            PluginExportOperation op =
                    new PluginExportOperation(
                            info,
                            Messages.CustomFeatureExportJob_ExportPlugins_message) {
                    };
            try {
                op.setUser(true);
                op.schedule();
                op.join();
                /*
                 * use setHasAntError() to report back the problems rather than
                 * the status in order to avoid showing the dialog
                 */
                if (op.hasAntErrors()) {
                    setHasAntErrors(op.hasAntErrors());
                }
                return Status.OK_STATUS;
            } catch (InterruptedException e) {
                return new Status(Status.ERROR, CDSActivator.PLUGIN_ID,
                        e.getMessage());
            }

        } else {
            return new FeatureExportOperation(info,
                    Messages.CustomFeatureExportJob_ExportPlugins_message) {
                @Override
                public IStatus run(IProgressMonitor monitor) {
                    return super.run(monitor);
                }
            }.run(monitor);
        }
    }

    /**
     * @return the hasAntErrors
     */
    public boolean hasAntErrors() {
        return hasAntErrors;
    }

    /**
     * @param hasAntErrors
     *            the hasAntErrors to set
     */
    public void setHasAntErrors(boolean hasAntErrors) {
        this.hasAntErrors = hasAntErrors;
    }
}
