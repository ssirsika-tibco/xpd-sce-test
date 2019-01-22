/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.daa;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.daa.internal.BaseProjectDAAGenerator;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * The DAA generation operation for Openspace Gadget Development projects.
 * 
 * @author aallway
 * @since 25 Jan 2013
 */
public class OpenspaceProjectDAAGenerationOperation extends
        AbstractMultiProjectDAAGenerationWithProgress {

    private static final Logger LOG = OpenspaceGadgetPlugin.getDefault()
            .getLogger();

    private OpenspaceProjectDAAGenerator projectDAAGenerator =
            new OpenspaceProjectDAAGenerator();

    /**
     * Creates operation instance.
     * 
     * @param projects
     *            the list of project for which the DAA should be generated.
     */
    public OpenspaceProjectDAAGenerationOperation(List<IProject> projects,
            boolean preservDaaAfterGeneration) {
        super(preservDaaAfterGeneration);
        this.selectedProjects = new ArrayList<IProject>(projects);
    }

    /**
     * @see com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress#getProjectDaaGenerator()
     * 
     * @return
     */
    @Override
    protected BaseProjectDAAGenerator getProjectDaaGenerator() {
        return projectDAAGenerator;
    }

    /**
     * @see com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress#hasErrorLevelProblemMarkers(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean hasErrorLevelProblemMarkers(IProject project) {
        try {
            return CompositeUtil.hasErrorLevelProblemMarkers(project);

        } catch (CoreException e) {
            LOG.error(e);
            return false;
        }
    }

    /**
     * @see com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress#getCompositeContributorContextId()
     * 
     * @return
     */
    @Override
    protected String getCompositeContributorContextId() {
        return projectDAAGenerator.getCompositeContributorContext();
    }

}