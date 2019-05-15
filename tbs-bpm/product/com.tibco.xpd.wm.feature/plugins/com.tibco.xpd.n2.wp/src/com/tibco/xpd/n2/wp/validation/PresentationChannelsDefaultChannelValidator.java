/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.wp.validation;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class PresentationChannelsDefaultChannelValidator implements
        WorkspaceResourceValidator {

    // XPD-4789:Raise warning message for GI channels to Error level.


    private static final String ISSUE_CONTAINS_INVALID_CHANNEL =
            "bx.InvalidChannel"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void setProject(IProject project) {
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     */
    @Override
    public void validate(IValidationScope scope, IResource resource) {
        if (resource instanceof IProject && resource.isAccessible()) {

            IProject project = (IProject) resource;

            /* if the project does not have any user activities, don't validate */
            if (!hasManualActivities(project)) {
                return;
            }

            Channels channels =
                    PresentationManager.getInstance().getChannels(project);

            // XPD-3657 Workspace EMail type should not be part of default
            // channel & some refactoring

            for (Channel channel : channels.getChannels()) {

                /*
                 * get all associated channel types for a default channel
                 */
                for (TypeAssociation ta : channel.getTypeAssociations()) {
                    /*
                     * check if the associated channel types has Workspace
                     * General Interface channel type enabled
                     */
                    if (ta.getChannelType() != null) {

                        String channelName = null;
                        String issueId = null;

                        /*
                         * Sid ACE-1132 Various validation
                         * rules/issues/resolutions removed as unsupported
                         * channels are now removed on import of AMX BPM
                         * projects into ACE.
                         * 
                         * Workspace Email and Workspace general interface in
                         * this case.
                         */

                        if (ta.getChannelType().getId() == null) {
                            // XPD-4003: channel id will be null for Invalid
                            // channel
                            // Workspace Email GWT Channel for which the
                            // extension is removed.

                            /*
                             * Workspace Email GWT is Invalid [ERROR]
                             */
                            issueId = ISSUE_CONTAINS_INVALID_CHANNEL;
                            channelName = ta.getChannelType().getDisplayName();

                        }

                        if (issueId != null) {
                            if (channelName == null) {
                                scope.createIssue(issueId,
                                        project.getName(),
                                        project.getProjectRelativePath()
                                                .toString());
                            } else {
                                scope.createIssue(issueId,
                                        project.getName(),
                                        project.getProjectRelativePath()
                                                .toString(),
                                        Arrays.asList(channelName));
                            }
                        }

                    }
                }
            }

        }
    }

    /**
     * Checks if the project has manual activities.
     */
    private boolean hasManualActivities(IProject project) {
        for (Package xpdlPackage : BRMUtils.getN2ProcessPackages(project)) {
            if (!BRMUtils.getN2ManualActivities(Arrays.asList(xpdlPackage))
                    .isEmpty()) {
                return true;
            }
            for (com.tibco.xpd.xpdl2.Process xpdlProcess : xpdlPackage
                    .getProcesses()) {
                if (Xpdl2ModelUtil.isPageflow(xpdlProcess)
                        && !BRMUtils.getManualPageFlowActivities(xpdlProcess)
                                .isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

}
