/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Version;

import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.RedefinableHeader;

/**
 * Validation rule to check {@link Package} version - this has to be the same as
 * the project lifecycle version.
 * 
 * @author njpatel
 * 
 */
public class PackageVersionRule extends Xpdl2ValidationRule {

    private static final String ISSUE_ID =
            "bx.packageVersionNotSameAsProjectVersion"; //$NON-NLS-1$

    private static final String PACKAGE_VERSION_MISSING_ISSUE_ID =
            "bx.packageVersionNotSet"; //$NON-NLS-1$

    public Class<?> getTargetClass() {
        return Package.class;
    }

    @Override
    protected void validate(Object obj) {
        if (obj instanceof Package) {
            Package pkg = (Package) obj;
            IProject project = WorkingCopyUtil.getProjectFor((EObject) obj);
            RedefinableHeader header = ((Package) obj).getRedefinableHeader();
            if (project != null) {

                if (null == header) {
                    // Version is not set
                    Version projectVersion =
                            Version.parseVersion(ProjectUtil
                                    .getProjectVersion(project));
                    List<String> msgs = new ArrayList<String>();
                    msgs.add(projectVersion.toString());
                    addIssue(PACKAGE_VERSION_MISSING_ISSUE_ID, pkg, msgs);
                } else {

                    Version projectVersion = null;
                    try {
                        projectVersion =
                                Version.parseVersion(ProjectUtil
                                        .getProjectVersion(project));
                    } catch (IllegalArgumentException e) {
                        // Invalid version so ignore.
                    }

                    if (projectVersion != null) {

                        Version version = Version.emptyVersion;
                        try {
                            version = Version.parseVersion(header.getVersion());
                        } catch (IllegalArgumentException e) {
                            // Do nothing
                        }

                        if (projectVersion.compareTo(version) != 0) {
                            List<String> msgs = new ArrayList<String>();
                            msgs.add(version.toString());
                            msgs.add(projectVersion.toString());
                            // Versions don't match
                            addIssue(ISSUE_ID, pkg, msgs);
                        }
                    }
                }
            }
        }
    }
}
