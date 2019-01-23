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

    private static final String INVALID_ISSUE_ID =
            "bx.workspaceGICannotExistInDefaultChannel"; //$NON-NLS-1$

    private static final String ISSUE_DEFAULT_CONTAINS_WORKSPACE_GI_EMAIL =
            "bx.workspaceEmailGICannotExistInDefaultChannel"; //$NON-NLS-1$

    // XPD-4789:Raise warning message for GI channels to Error level.

    private static final String ISSUE_WORKSPACE_GI_NOT_SUPPORTED =
            "bx.workspaceGIIsDeprecated"; //$NON-NLS-1$

    private static final String ISSUE_WORKSPACE_GI_EMAIL_NOT_SUPPORTED =
            "bx.workspaceEmailIsDeprecated"; //$NON-NLS-1$

    private static final String ISSUE_CONTAINS_INVALID_CHANNEL =
            "bx.InvalidChannel"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    public void setProject(IProject project) {
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     */
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
                        if (PresentationManager.GI_GI_PULL.equals(ta
                                .getChannelType().getId())) {
                            // XPD-4789: Change level to Error, as GI channel
                            // support is removed
                            issueId = ISSUE_WORKSPACE_GI_NOT_SUPPORTED;
                            channelName = ta.getChannelType().getDisplayName();

                        } else if (PresentationManager.EMAIL_GI_PUSH.equals(ta
                                .getChannelType().getId())) {
                            /*
                             * Workspace GI Email not supported[ERROR]
                             */// XPD-5309 Show different message for Default
                               // and User Defined channels
                            if (channel.isDefault()) {
                                issueId =
                                        ISSUE_DEFAULT_CONTAINS_WORKSPACE_GI_EMAIL;
                            } else {
                                issueId =
                                        ISSUE_WORKSPACE_GI_EMAIL_NOT_SUPPORTED;

                            }
                            channelName = ta.getChannelType().getDisplayName();
                        } else if (ta.getChannelType().getId() == null) {
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
