/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.destinations;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.destinations.internal.Messages;
import com.tibco.xpd.destinations.internal.validation.FixProjectNaturesAndBuildersResolution;
import com.tibco.xpd.destinations.internal.validation.RequiredNaturesRule;
import com.tibco.xpd.resources.ui.imports.IPostImportProjectTask;

/**
 * Post import task to check and fix project nature and builder order .
 * 
 * @author aallway
 * @since 13 Jan 2012
 */
public class FixProjectNaturesPostImportTask implements IPostImportProjectTask {

    /**
     * @see com.tibco.xpd.resources.ui.imports.IPostImportProjectTask#runPostImportTask(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void runPostImportTask(IProject project, IProgressMonitor monitor)
            throws CoreException {
        try {
            monitor.beginTask(Messages.FixProjectNaturesPostImportTask_CheckProjectNatures_message, 2);

            Set<String> missingNatures =
                    RequiredNaturesRule.getMissingNatures(project);

            if (!missingNatures.isEmpty()) {
                monitor.subTask(Messages.FixProjectNaturesPostImportTask_AddMissingNatures_message);

                FixProjectNaturesAndBuildersResolution
                        .fixMissingNatures(project, missingNatures);
            }

            monitor.worked(1);

            /*
             * fix for missing nautures fixes nature/builder order etc so only
             * need to check and fix natures/builder order if we didn't do that
             */
            if (missingNatures.isEmpty()) {

                if (!RequiredNaturesRule.checkNatureAndBuilderSequence(project)) {
                    monitor.subTask(Messages.FixProjectNaturesPostImportTask_UpdatingnaturesSequence_message);
                    FixProjectNaturesAndBuildersResolution
                            .fixProjectNaturesAndBuilders(project);
                }
            }

            monitor.worked(1);

        } finally {
            monitor.done();
        }
    }

}
