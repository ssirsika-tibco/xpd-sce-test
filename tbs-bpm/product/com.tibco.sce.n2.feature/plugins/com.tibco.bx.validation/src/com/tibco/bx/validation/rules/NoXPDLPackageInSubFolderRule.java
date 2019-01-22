/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Package;

/**
 * NoXPDLPackageInSubFolderRule throws an error marker if there is any .xpdl
 * file under a sub folder under Process Packages special folder.
 * 
 * @author sajain
 * @since Feb 20, 2013
 */
public class NoXPDLPackageInSubFolderRule implements WorkspaceResourceValidator {
    private static final String ISSUE_ID = "bx.noXPDLPackageInSubFolderRule"; //$NON-NLS-1$

    public void validate(IValidationScope scope, IResource resource) {

        // check if the resource is a .xpdl file
        if ((resource instanceof IFile)
                && (resource.getFileExtension() != null)
                && (resource.getFileExtension()
                        .endsWith(Xpdl2ResourcesConsts.XPDL_EXTENSION))) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(resource);

            if (workingCopy != null
                    && workingCopy.getRootElement() instanceof Package) {

                /*
                 * Make sure that an error marker is thrown if the user has a
                 * .xpdl file under a subfolder under a Process packages folder.
                 * The error marker should be thrown even if the user creates a
                 * subfolder inside another subfolder and then tries to have a
                 * .xpdl file under it (or tries a further long hierarchy of
                 * subfolders and then put a .xpdl file under it).
                 */
                if (resource.getParent().getParent() instanceof IFolder) {
                    scope.createIssue(ISSUE_ID,
                            resource.getName(),
                            resource.getProjectRelativePath().toString(),
                            Arrays.asList(new String[] {
                                    resource.getName(),
                                    resource.getParent().getFullPath()
                                            .toString() }));

                }
            }
        }

    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    public void setProject(IProject project) {
        // TODO Auto-generated method stub

    }
}