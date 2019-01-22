/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.daa;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.daa.internal.BaseProjectDAAGenerator;
import com.tibco.xpd.daa.internal.wizards.AbstractMultiProjectDAAGenerationWithProgress;
import com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;

/**
 * Deployment wizard for Openspace Gadget Development Projects.
 * 
 * @author aallway
 * @since 25 Feb 2013
 */
public class OpenspaceProjectDeployWizard extends AbstractProjectDeployWizard {

    private OpenspaceProjectDAAGenerationOperation generationOperation;

    /**
     * Deployment wizard for Openspace Gadget Development Projects.
     */
    public OpenspaceProjectDeployWizard() {
    }

    /**
     * @see com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard#getProjectTypeName()
     * 
     * @return
     */
    @Override
    protected String getProjectTypeName() {
        return Messages.OpenspaceProjectDeployWizard_OpenspaceGadgetProjectType_Label;
    }

    /**
     * @see com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard#isApplicableProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean isApplicableProject(IProject project) {
        return OpenspaceGadgetNature.isOpenspaceGadgetDevProject(project);
    }

    /**
     * @see com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard#createDAAGenerationOperation(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @SuppressWarnings("restriction")
    @Override
    protected AbstractMultiProjectDAAGenerationWithProgress createDAAGenerationOperation(
            IProject project) {

        /*
         * Sid: There's a new facility to tell DAA gen operation to preserve the
         * DAA during cleanup phase. So we'll use that rather than overriding
         * the whole of clean up.
         */
        generationOperation =
                new OpenspaceProjectDAAGenerationOperation(
                        Collections.singletonList(project), true);

        return generationOperation;
    }

    /**
     * @see com.tibco.xpd.daa.wizards.AbstractProjectDeployWizard#getGeneratedDAAFromProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @SuppressWarnings("restriction")
    @Override
    protected IFile getGeneratedDAAFromProject(IProject project) {
        if (generationOperation != null) {

            BaseProjectDAAGenerator projectDaaGenerator =
                    generationOperation.getProjectDaaGenerator();

            if (projectDaaGenerator != null) {
                return projectDaaGenerator.getDAAFile(project);
            }
        }
        return null;
    }

}
