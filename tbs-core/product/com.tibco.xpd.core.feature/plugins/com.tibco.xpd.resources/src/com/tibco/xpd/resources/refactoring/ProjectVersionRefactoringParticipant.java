/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;

/**
 * Abstract class to be implemented by a project lifecycle refactoring
 * participant (extension point
 * <code>com.tibco.xpd.resources.projectVersionRefactoringParticipant</code>).
 * 
 * @author njpatel
 * 
 */
public abstract class ProjectVersionRefactoringParticipant extends
        RefactoringParticipant implements IExecutableExtension {

    private String name;

    private ProjectVersionRefactoringArguments arg;

    @Override
    protected boolean initialize(Object element) {
        return true;
    }

    protected ProjectVersionRefactoringArguments getChangeArgument() {
        return arg;
    }

    @Override
    protected void initialize(RefactoringArguments arguments) {
        if (arguments instanceof ProjectVersionRefactoringArguments) {
            arg = (ProjectVersionRefactoringArguments) arguments;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    public final void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {

        if (config != null) {
            name = config.getAttribute("name"); //$NON-NLS-1$
        }
    }

}
