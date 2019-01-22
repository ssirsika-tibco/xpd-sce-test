/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;

/**
 * This class represents the workspace resource validator extension.
 * 
 * @author njpatel
 * 
 */
public class WorkspaceResourceValidatorExt {

    private static final String ATT_ID = "id"; //$NON-NLS-1$

    private static final String ATT_CLASS = "class"; //$NON-NLS-1$

    private static final String ATT_TYPE = "validateType"; //$NON-NLS-1$

    private static final String ATT_FILTER = "filter"; //$NON-NLS-1$

    /**
     * Resource type to validate.
     * 
     * @author njpatel
     */
    private enum ResourceType {
        PROJECT, FOLDER, FILE, ALL;
    }

    private final IConfigurationElement element;

    private String id;

    private Class<?> type;

    private ResourceType resType;

    private Pattern filter;

    private WorkspaceResourceValidator validator;

    private boolean noFilterSet = false;

    /**
     * Workspace resource validator extension. This represents the extensions of
     * the extension point <code>workspaceResourceValidatioProvider</code>'s
     * validator.
     * 
     * @param element
     */
    public WorkspaceResourceValidatorExt(IConfigurationElement element) {
        this.element = element;
    }

    /**
     * Check if this {@link #getValidator() validator} is interested in
     * validating the given resource. This will check if the resource is of the
     * given {@link #getType() type} and matches the {@link #getFilter() filter}
     * if provided. If the type and filter are not specified then this will
     * return <code>true</code>.
     * 
     * @param resource
     * @return <code>true</code> if this validator should validate the resource,
     *         <code>false</code> otherwise.
     */
    public boolean isInterested(IResource resource) {
        boolean validate = true;

        Class<?> validateType = getType();
        Pattern resFilter = getFilter();

        if (validateType != null) {
            validate = validateType.isInstance(resource);
        }

        if (validate && resFilter != null) {
            validate = resFilter.matcher(resource.getName()).matches();
        }

        return validate;
    }

    /**
     * Get the validator.
     * 
     * @return <code>WorkspaceResourceValidator</code>
     * @throws CoreException
     */
    public WorkspaceResourceValidator getValidator() throws CoreException {
        if (validator == null) {
            validator =
                    (WorkspaceResourceValidator) element
                            .createExecutableExtension(ATT_CLASS);
        }
        return validator;
    }

    /**
     * Get id of this extension.
     * 
     * @return id
     */
    public String getId() {
        if (id == null) {
            id = element.getAttribute(ATT_ID);
        }
        return id;
    }

    /**
     * Get type of object to validate using this {@link #getValidator()
     * validator}.
     * 
     * @return <code>Class</code> if specified or <code>null</code>.
     */
    private Class<?> getType() {
        if (resType == null) {
            String attType = element.getAttribute(ATT_TYPE);

            if (attType != null) {
                resType = ResourceType.valueOf(attType);
            } else {
                resType = ResourceType.ALL;
            }

            switch (resType) {
            case PROJECT:
                type = IProject.class;
                break;

            case FOLDER:
                type = IFolder.class;
                break;

            case FILE:
                type = IFile.class;
                break;
            }
        }

        return type;
    }

    /**
     * Get the filter (pattern matching). This will be used to determine of the
     * given resource should be validated.
     * 
     * @return filter pattern if specified, <code>null</code> otherwise.
     */
    private Pattern getFilter() {
        if (filter == null && !noFilterSet) {
            String str = element.getAttribute(ATT_FILTER);
            if (str != null && !str.isEmpty()) {
                filter = Pattern.compile(str);
            } else {
                noFilterSet = true;
            }
        }

        return filter;
    }
}
