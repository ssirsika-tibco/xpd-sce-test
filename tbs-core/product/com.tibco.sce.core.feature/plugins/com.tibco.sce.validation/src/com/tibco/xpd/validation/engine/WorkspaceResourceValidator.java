/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.engine;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Interface implemented by the contributor to the extension point
 * <code>com.tibco.xpd.validation.workspaceResourceValidationProvider</code>.
 * This will allow clients to validate workspace resources during the validation
 * build process.
 * 
 * @author njpatel
 * 
 */
public interface WorkspaceResourceValidator {

    /**
     * Validate the resource. Problems should be raised through the
     * <code>createIssue</code> method of the scope.
     * 
     * @param scope
     *            validation scope to be used to create issues
     * @param resource
     *            resource to validate
     */
    void validate(IValidationScope scope, IResource resource);

    /**
     * Set the current project being built.
     * 
     * @param project
     */
    void setProject(IProject project);
}
