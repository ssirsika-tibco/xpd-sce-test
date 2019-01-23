/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui;

import java.util.Collection;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaElement;

import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;

/**
 * Ext Point Contribution Property tester for Openspace GWT Gadget
 * contributions.
 * 
 * @author aallway
 * @since 5 Dec 2012
 */
public class OpenspaceGadgetDevPropertyTester extends PropertyTester {

    /**
     * Test property to check if the selected object is a project with the
     * openspace gadget development nature set on OR is in one.
     */
    public static final String IS_IN_PROJECT_WITH_OPENSPACE_GADGET_NATURE =
            "isInProjectWithOpenspaceGadgetNature"; //$NON-NLS-1$

    /**
     * Test property to check if the selected object is a project with the GWT
     * gadget nature set on OR is in one.
     */
    public static final String IS_IN_PROJECT_WITH_GWT_GADGET_NATURE =
            "isInProjectWithGWTGadgetNature"; //$NON-NLS-1$

    public OpenspaceGadgetDevPropertyTester() {
    }

    /**
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     * 
     * @param receiver
     * @param property
     * @param args
     * @param expectedValue
     * @return
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (receiver instanceof Collection
                && ((Collection) receiver).size() == 1) {
            receiver = ((Collection) receiver).iterator().next();
        }

        if (IS_IN_PROJECT_WITH_GWT_GADGET_NATURE.equalsIgnoreCase(property)) { //$NON-NLS-1$
            IProject project = getProject(receiver);
            if (OpenspaceGadgetNature.isGWTGadgetProject(project)) {
                return true;
            }

        } else if (IS_IN_PROJECT_WITH_OPENSPACE_GADGET_NATURE
                .equalsIgnoreCase(property)) { //$NON-NLS-1$
            IProject project = getProject(receiver);
            if (OpenspaceGadgetNature.isOpenspaceGadgetDevProject(project)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Derive the project from the receiver which can be a project, resource in
     * a project or Java source {@link IJavaElement} (package / src / class)
     * 
     * @param receiver
     * @return project if receiver is project or adaptable to one or is resource
     *         / JAva element child of a project or <code>null</code> if cannot
     *         be derived.
     */
    public static IProject getProject(Object receiver) {
        IProject project = null;

        /* See if it is actually a project. */
        project = (IProject) getOrAdapt(receiver, IProject.class);

        if (project == null) {
            IResource resource =
                    (IResource) getOrAdapt(receiver, IResource.class);
            if (resource != null) {
                project = resource.getProject();
            }
        }

        if (project == null) {
            IJavaElement javaElement =
                    (IJavaElement) getOrAdapt(receiver, IJavaElement.class);

            if (javaElement != null && javaElement.getResource() != null) {
                project = javaElement.getResource().getProject();
            }
        }

        if (project != null && project.isOpen()) {
            return project;
        }

        return null;
    }

    /**
     * @param receiver
     * @param clazz
     * 
     * @return Return receiver if it is instance of clazz or is adaptable to it.
     */
    private static Object getOrAdapt(Object receiver, Class clazz) {
        if (clazz.isInstance(receiver)) {
            return receiver;
        } else if (receiver instanceof IAdaptable) {
            return ((IAdaptable) receiver).getAdapter(clazz);
        }
        return null;
    }

}
