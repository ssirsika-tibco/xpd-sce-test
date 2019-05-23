/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.wp;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;

import com.tibco.bpm.dt.rasc.Version;
import com.tibco.xpd.n2.wp.internal.Messages;
import com.tibco.xpd.rasc.core.RascContext;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.rasc.core.RascWriter;

/**
 * The RascContributor implementation to add the exported BRM-Models to the
 * RASC, the work-model and work-type models (wm.xml, wt.xml)
 * 
 * @author aallway
 * @since 02 May 2019
 */
public class WpModelsRascContributor implements RascContributor {
    /**
     * The unique identifier for this RascContribution implementation.
     */
    private static final String ID =
            "com.tibco.xpd.n2.wp.rasc.model.contributor"; //$NON-NLS-1$



    /**
     * The transformer to generate the RASC WP models.
     */
    private WPGenerator TRANSFORMER = WPGenerator.getInstance();


    /**
     * @see com.tibco.xpd.rasc.core.RascContributor#getId()
     */
    @Override
    public String getId() {
        return WpModelsRascContributor.ID;
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascContributor#hasContributionsFor(org.eclipse.core.resources.IProject)
     */
    @Override
    public boolean hasContributionsFor(IProject aProject) {
        return TRANSFORMER.projectHasRelevantContent(aProject);
    }

    /**
     * @see com.tibco.xpd.rasc.core.RascContributor#process(org.eclipse.core.resources.IProject,
     *      com.tibco.xpd.rasc.core.RascContext,
     *      org.eclipse.core.runtime.IProgressMonitor,
     *      com.tibco.xpd.rasc.core.RascWriter)
     *
     * @param aProject
     * @param aContext
     * @param aProgressMonitor
     * @param aWriter
     * @throws Exception
     */
    @Override
    public void process(IProject aProject, RascContext aContext,
            IProgressMonitor aProgressMonitor, RascWriter aWriter)
            throws Exception {

        SubMonitor monitor = SubMonitor.convert(aProgressMonitor,
                Messages.WpModelsRascContributor_Export_status,
                1);
        monitor.subTask(
                Messages.WpModelsRascContributor_Export_status);

        try {
            // Generate the BRM relevant models
            Version version = aContext.getVersion();

            long start = System.currentTimeMillis();

            /*
             * The WPGenerator will add the models directly to RASC
             */
            IStatus status = TRANSFORMER
                    .generateN2Modules(aProject,
                            aWriter,
                            version.toString());

            // System.out.println("** TOOK this long to generate WP models: "
            // //$NON-NLS-1$
            // // $NON-NLS-1$
            // + (System.currentTimeMillis() - start));

            if (!status.isOK()) {
                throw new Exception(
                        "WPGenerator failed: " + status.getMessage()); //$NON-NLS-1$
            }

            // has the job been cancelled by the user
            if (monitor.isCanceled()) {
                return;
            }

        } finally {
            monitor.worked(1);
            monitor.subTask(""); //$NON-NLS-1$
            monitor.done();
        }
    }

}
