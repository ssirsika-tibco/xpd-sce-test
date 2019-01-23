/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.n2.wp.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.presentation.channels.AttributeValue;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validates if resources specified as channel properties are available for the
 * project.
 */
public class ProjectChannelResourceValidator implements
        WorkspaceResourceValidator {

    private static final String INVALID_ISSUE_ID =
            "bx.referencedResourceDoesntExist"; //$NON-NLS-1$

    //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject
     * (org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject project) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com
     * .tibco.xpd.validation.provider.IValidationScope,
     * org.eclipse.core.resources.IResource)
     */
    public void validate(IValidationScope scope, IResource resource) {
        if (resource instanceof IProject && resource.isAccessible()) {
            IProject project = (IProject) resource;
            if (!hasManualActivities(project)) {
                return;
            }
            Channels channels =
                    PresentationManager.getInstance().getChannels(project);

            List<SpecialFolder> presentationSFs =
                    SpecialFolderUtil
                            .getAllSpecialFoldersOfKind(project,
                                    PresentationManager.PRESENTATION_SPECIAL_FOLDER_KIND);
            List<IPath> sfPaths = new ArrayList<IPath>();
            for (SpecialFolder sf : presentationSFs) {
                IFolder f = sf.getFolder();
                if (f != null) {
                    sfPaths.add(f.getFullPath());
                }
            }

            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            for (Channel channel : channels.getChannels()) {
                for (TypeAssociation ta : channel.getTypeAssociations()) {
                    for (AttributeValue attrValue : ta.getAttributeValues()) {
                        String value = attrValue.getValue();
                        if (AttributeType.RESOURCE == attrValue.getAttribute()
                                .getType()
                                && value != null && !value.isEmpty()) {
                            boolean exist = false;
                            for (IPath path : sfPaths) {
                                if (root.findMember(path.append(value)) != null) {
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist) {
                                scope.createIssue(INVALID_ISSUE_ID,
                                        project.getName(),
                                        project.getProjectRelativePath()
                                                .toString(),
                                        Arrays.asList(attrValue.getAttribute()
                                                .getName(), value));
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
