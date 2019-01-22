/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * ProjectNameValidator - validates if the project name has any invalid
 * characters when BPM destination is selected
 * 
 * 
 * @author bharge
 * @since 3.3 (29 Apr 2010)
 */
public class ProjectNameValidator implements WorkspaceResourceValidator {

    private static final String INVALID_ISSUE_ID = "bx.invalidProjectName"; //$NON-NLS-1$

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
            String projectName = project.getName();

            // TODO: uncomment the below code if daa generation or deployment
            // fails for a project name having only numbers. i could generate
            // daa and deploy project having name as 2222 as on 29th April 2010

            // String projectNameAfterNumericsRemoved =
            // NameUtil.getInternalName(projectName, true);
            // matcher = pattern.matcher(projectNameAfterNumericsRemoved);
            // boolean whiteSpaceFoundAfterNumRemoved = matcher.find();

            if (GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                    N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                if (!ProjectUtil.isValidProjectName(projectName)) {
                    scope.createIssue(INVALID_ISSUE_ID,
                            project.getName(),
                            project.getProjectRelativePath().toString());
                }
            }
        }
    }

}
