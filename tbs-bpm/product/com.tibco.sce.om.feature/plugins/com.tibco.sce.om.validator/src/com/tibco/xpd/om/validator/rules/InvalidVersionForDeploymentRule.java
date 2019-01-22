/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Version;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule to check for the {@link OrgModel}s version. The version has
 * to be atleast "1.0.0" to allow the model to be deployed. The version also has
 * to match the project version.
 * 
 * @author njpatel
 * 
 */
public class InvalidVersionForDeploymentRule implements IValidationRule {

    private static final String ISSUE_ID =
            "om.invalidOMVersionForDeployment.issue"; //$NON-NLS-1$

    private static final String PROJECT_VER_ISSUE_ID =
            "om.doesntMatchProjectVersion.issue"; //$NON-NLS-1$

    private final Version minVersion;

    /**
     * Validation rule to check the {@link OrgModel} version for deployment.
     */
    public InvalidVersionForDeploymentRule() {
        minVersion = new Version(1, 0, 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return OrgModel.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof OrgModel && ((EObject) obj).eResource() != null) {
            OrgModel model = (OrgModel) obj;
            boolean doCreateIssue = false;
            String versionStr = model.getVersion();
            Version version = null;

            if (versionStr != null) {
                try {
                    version = Version.parseVersion(versionStr);
                    doCreateIssue =
                            version == null || version == Version.emptyVersion
                                    || minVersion.compareTo(version) > 0;
                } catch (IllegalArgumentException e) {
                    // Invalid version
                    doCreateIssue = true;
                }
            } else {
                doCreateIssue = true;
            }

            if (doCreateIssue) {
                scope.createIssue(ISSUE_ID, model.getDisplayName(), model
                        .eResource().getURIFragment(model));
            }

            /*
             * The version number should match the project version.
             */
            validateProjectVersion(model, version, scope);
        }
    }

    /**
     * Validate the OM version against its project version.
     * 
     * @param model
     * @param scope
     */
    private void validateProjectVersion(OrgModel model, Version modelVersion,
            IValidationScope scope) {
        if (model != null && modelVersion != null && scope != null) {
            IProject project = WorkingCopyUtil.getProjectFor(model);
            if (project != null) {
                String projectVersion = ProjectUtil.getProjectVersion(project);
                if (projectVersion != null) {
                    try {
                        Version pVersion = Version.parseVersion(projectVersion);

                        if (pVersion != null
                                && pVersion != Version.emptyVersion) {
                            if (modelVersion.compareTo(pVersion) != 0) {
                                List<String> msgs = new ArrayList<String>();
                                msgs.add(modelVersion.toString());
                                msgs.add(projectVersion);
                                scope.createIssue(PROJECT_VER_ISSUE_ID, model
                                        .getDisplayName(), model.eResource()
                                        .getURIFragment(model), msgs);
                            }
                        }

                    } catch (IllegalArgumentException e) {
                        // Do nothing if we cannot read the project version.
                    }
                }
            }
        }
    }

}
