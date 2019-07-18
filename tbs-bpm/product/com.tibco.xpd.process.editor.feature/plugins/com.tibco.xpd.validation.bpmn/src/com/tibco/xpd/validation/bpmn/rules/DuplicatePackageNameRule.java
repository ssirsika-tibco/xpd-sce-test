/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Package;

/**
 * Validates that the name of the given package is not a duplicate of any other
 * package name within the same project.
 * 
 * @author pwatson
 */
public class DuplicatePackageNameRule extends PackageValidationRule {

    /** Issue ID. */
    public static final String ID = "bpmn.duplicatePackageName"; //$NON-NLS-1$

    /**
     * Validates that the name of the given package is not a duplicate of any
     * other package name within the same project.
     * 
     * @param aPackage
     *            The package to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package aPackage) {
        // if it doesn't have a name it will be captured by another rule
        String packageName = aPackage.getName();
        if ((packageName == null) || (packageName.isEmpty())) {
            return;
        }

        // find the enclosing project
        IProject srcProject = WorkingCopyUtil.getProjectFor(aPackage, true);
        if (srcProject == null) {
            return;
        }

        // iterate over all process packages within the project
        for (Package other : ProcessUIUtil.getProcessPackagesForProject(srcProject)) {
            if ((aPackage == other) || (aPackage.getId().equals(other.getId()))) {
                continue;
            }

            if (packageName.equals(other.getName())) {
                addIssue(DuplicatePackageNameRule.ID, aPackage);
                break;
            }
        }
    }
}
