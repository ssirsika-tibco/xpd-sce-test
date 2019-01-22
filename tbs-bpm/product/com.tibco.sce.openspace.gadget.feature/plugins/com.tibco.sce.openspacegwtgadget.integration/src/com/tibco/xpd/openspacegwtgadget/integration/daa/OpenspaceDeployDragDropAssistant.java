/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.daa;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.IWizard;

import com.tibco.xpd.daa.internal.util.AbstractDeployDragDropAssistant;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;

/**
 * Drag-drop deploy implementation for Openspace gadget dev projects.
 * 
 * @author aallway
 * @since 26 Feb 2013
 */
public class OpenspaceDeployDragDropAssistant extends
        AbstractDeployDragDropAssistant {

    /**
     * @see com.tibco.xpd.daa.internal.util.AbstractDeployDragDropAssistant#isApplicableProjectType(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean isApplicableProjectType(IProject project) {
        return OpenspaceGadgetNature.isOpenspaceGadgetDevProject(project);
    }

    /**
     * @see com.tibco.xpd.daa.internal.util.AbstractDeployDragDropAssistant#createDeployWizard(com.tibco.xpd.deploy.Server,
     *      org.eclipse.core.resources.IProject)
     * 
     * @param selectedServer
     * @param draggedProject
     * @return
     */
    @Override
    protected IWizard createDeployWizard(Server selectedServer,
            IProject draggedProject) {

        OpenspaceProjectDeployWizard wizard =
                new OpenspaceProjectDeployWizard();
        wizard.setServer(selectedServer);
        wizard.setSelectedProject(draggedProject);

        return wizard;
    }

}
