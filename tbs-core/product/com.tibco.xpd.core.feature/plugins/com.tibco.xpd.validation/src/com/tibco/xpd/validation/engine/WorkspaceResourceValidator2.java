/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.engine;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * An extension of the standard {@link WorkspaceResourceValidator} class whereby
 * the new {@link #validate(IValidationScope, IResource, boolean)} method is
 * given an indication of whether it is being called <b>for the resource (or
 * container whose content) that has actually changed, or whether the resource
 * is being validated simply because it referenced the resource that has
 * actually changed.</b>
 * <p>
 * This is especially useful when considering performance of workspace
 * validations for project content. Often, the validation only requires checking
 * of content within the changed project and that change does not affect
 * referencing projects, therefore it is inefficient to re-validate the
 * referencing projects.
 * 
 * @author bharge
 * @since 15 May 2015
 */
public abstract class WorkspaceResourceValidator2 implements
        WorkspaceResourceValidator {

    /**
     * Validate the given resource. isReferencingResourceValidation indicates
     * whether the resource is being validated only because it references a
     * changed resource (but is not changed itself). This can be useful to
     * improve performance where a validation is not affected by changes in
     * referenced projects).
     * 
     * @param scope
     * @param resource
     * @param isReferencingResourceValidation
     *            <code>true</code> if this is a validation being done on a
     *            resource that references a changed resource (but is not
     *            necessarily changed itself).
     */
    public abstract void validate(IValidationScope scope, IResource resource,
            boolean isReferencingResourceValidation);

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     * @deprecated CLasses implementing {@link WorkspaceResourceValidator2} will
     *             never have this method called, instead
     *             {@link #validate(IValidationScope, IResource, boolean)} is
     *             used.
     */
    @Deprecated
    public final void validate(IValidationScope scope, IResource resource) {

    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    public abstract void setProject(IProject project);
}
