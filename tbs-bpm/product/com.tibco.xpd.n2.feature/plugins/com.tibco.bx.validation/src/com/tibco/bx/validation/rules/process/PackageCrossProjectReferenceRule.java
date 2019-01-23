/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.process;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * Validation rule to check for cross-project references in a {@link Package}.
 * This will check the Package's Type Declaration and Data Fields.
 * 
 * @author njpatel
 * 
 */
public class PackageCrossProjectReferenceRule extends PackageValidationRule {

    private static final String ISSUE_ID = "bx.crossProjectReference.issue"; //$NON-NLS-1$

    @Override
    public void validate(Package pckg) {
        if (pckg != null) {
            IProject srcProject = WorkingCopyUtil.getProjectFor(pckg);
            if (srcProject != null) {
                // Validate all type declarations
                validateTypeDeclarations(srcProject, pckg);
            }
        }
    }

    /**
     * Validate all the Package's Type Declarations.
     * 
     * @param srcProject
     * @param pkg
     */
    private void validateTypeDeclarations(IProject srcProject, Package pkg) {
        for (TypeDeclaration declaration : pkg.getTypeDeclarations()) {

            ExternalReference reference = declaration.getExternalReference();
            if (reference != null
                    && isCrossProjectReference(srcProject, reference)) {

                addIssue(ISSUE_ID, declaration);
            } else if (null != declaration.getRecordType()) {
                /* Check if type declaration refers Case Class */
                EList<Member> member = declaration.getRecordType().getMember();
                if (null != member && !member.isEmpty()) {

                    ExternalReference externalReference =
                            member.get(0).getExternalReference();
                    if (null != externalReference
                            && isCrossProjectReference(srcProject,
                                    externalReference)) {

                        addIssue(ISSUE_ID, declaration);
                    }
                }
            }
        }
    }

    /**
     * Check if this external reference is to another project.
     * 
     * @param srcProject
     * @param reference
     * @return
     */
    private boolean isCrossProjectReference(IProject srcProject,
            ExternalReference reference) {
        String location = reference.getLocation();
        if (location != null && !location.isEmpty()) {
            IFile file =
                    SpecialFolderUtil
                            .resolveSpecialFolderRelativePath(srcProject,
                                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                                    location,
                                    true);

            return file != null && !srcProject.equals(file.getProject());
        }

        return false;
    }
}