/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.daa.internal.validation.rules;

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.daa.internal.preferences.DAAGenPreferences;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Rule that checks if a local bom references a bom from business data project
 * and the Bom jar caching is disabled in preferences.
 * 
 * @author kthombar
 * @since 19-Feb-2014
 */
public class BomJarCachingDisabledRule implements IValidationRule {

    /**
     * BOM caching disabled rule id.
     */
    final static String BOM_JAR_CACHING_DISABLED_ISSUE_ID =
            "bom_jar_caching_disabled_invalid_issue"; //$NON-NLS-1$

    @Override
    final public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Model.class;
    }

    @Override
    final public void validate(IValidationScope scope, Object o) {
        if (o instanceof Model) {
            validateModel(scope, (Model) o);
        }
    }

    /**
     * @see com.tibco.bds.designtime.validator.rules.AbstractBomModelRule#validateModel(org.eclipse.uml2.uml.Model)
     * 
     * @param model
     */
    protected void validateModel(IValidationScope scope, Model model) {
        /*
         * First check if BOM jar caching is disabled.
         */
        if (!DAAGenPreferences.cacheBomJars()) {
            Resource resource = model.eResource();
            IFile bomSourceFile = WorkspaceSynchronizer.getFile(resource);

            /*
             * check if BOM is not in a business data project
             */
            if (!BOMUtils.isBusinessDataProject(bomSourceFile.getProject())) {
                Set<IResource> deepDependencies =
                        WorkingCopyUtil.getDeepDependencies(bomSourceFile);

                for (IResource eachDependentResource : deepDependencies) {
                    if (XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(eachDependentResource) instanceof BOMWorkingCopy) {
                        if (BOMUtils
                                .isBusinessDataProject(eachDependentResource
                                        .getProject())) {
                            addIssue(scope,
                                    BOM_JAR_CACHING_DISABLED_ISSUE_ID,
                                    model);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds the issue.
     * 
     * @param issueId
     *            The issue id. Issue id should be passed through the helper
     *            method {@link AbstractBomModelRule#getIssueId()}
     * @param object
     *            The {@link EObject} on which the issue is to be raised.
     */
    @SuppressWarnings("unchecked")
    protected void addIssue(IValidationScope scope, String issueId,
            EObject object) {
        Resource resource = object.eResource();
        if (resource != null) {
            String uri = resource.getURIFragment(object);
            scope.createIssue(issueId, getUmlElementName(object), uri,
            /* message */Collections.EMPTY_LIST,
            /* additionalInfo */Collections.EMPTY_MAP);
        }
    }

    /**
     * Gets name of the NamedElement of the {@link EObject}
     * 
     * @param object
     * @return Location of the {@link EObject}
     */
    private static String getUmlElementName(EObject object) {
        return (object instanceof NamedElement) ? ((NamedElement) object)
                .getName() : ""; //$NON-NLS-1$
    }
}
