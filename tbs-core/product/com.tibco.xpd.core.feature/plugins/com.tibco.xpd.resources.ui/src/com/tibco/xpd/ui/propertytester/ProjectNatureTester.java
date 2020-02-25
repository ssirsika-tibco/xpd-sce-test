/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.ui.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Tests whether the project has the nature given as expected value.
 * 
 * 
 * @author agondal
 * @since 6 Mar 2014
 */
public class ProjectNatureTester extends PropertyTester {

    /**
     * Property to check if the currently selected project has a specific
     * nature.
     */
    public static final String PROP_HAS_NATURE = "hasNature"; //$NON-NLS-1$

    /**
     * Property to check if the currently selected artifact is contained inside
     * a project that has a specific nature.
     */
    public static final String PROP_CONTAINING_PROJECT_HAS_NATURE = "containingProjectHasNature"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        if (PROP_HAS_NATURE.trim().equals(property.trim())) {
            if (receiver instanceof IProject && expectedValue instanceof String) {
                String stringExpectedValue = (String) expectedValue;

                IProject project = (IProject) receiver;
                if (project.isAccessible()) {
                    try {
                        if (project.getNature(stringExpectedValue) != null) {
                            return true;
                        }
                    } catch (CoreException e) {
                        XpdResourcesUIActivator.getDefault().getLogger()
                                .log(e.getStatus());
                    }
                }
            }
        } else if (PROP_CONTAINING_PROJECT_HAS_NATURE.trim().equals(property.trim())) {
            if (expectedValue instanceof String) {
                String stringExpectedValue = (String) expectedValue;
                IProject project = null;
                if (receiver instanceof IProject) {
                    project = (IProject) receiver;

                } else if (receiver instanceof IResource) {
                    IResource resource = (IResource) receiver;
                    project = resource.getProject();

                } else if (receiver instanceof EObject) {
                    EObject eObj = (EObject) receiver;
                    project = WorkingCopyUtil.getProjectFor(eObj);

                } else if (receiver instanceof INavigatorGroup) {
                    /* Recurs with nav groups parent and use that result. */
                    return test(((INavigatorGroup) receiver).getParent(), property, args, expectedValue);

                }

                if (null != project) {
                    if (project.isAccessible()) {
                        try {
                            if (project.getNature(stringExpectedValue) != null) {
                                return true;
                            }
                        } catch (CoreException e) {
                            XpdResourcesUIActivator.getDefault().getLogger().log(e.getStatus());
                        }
                    }
                }
            }
        }
        return false;
    }

}
