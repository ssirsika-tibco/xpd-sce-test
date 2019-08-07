/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IDecorationContext;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelDecorator;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.rasc.core.governance.GovernanceStateService;
import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.internal.Messages;

/**
 * Project explorer label decorator for projects that are Locked for Production.
 *
 * @author nwilson
 * @since 23 Jul 2019
 */
public class LockedForProductionLabelDecorator extends LabelDecorator {

    private GovernanceStateService gss;

    /**
     * Constructor.
     */
    public LockedForProductionLabelDecorator() {
        gss = new GovernanceStateService();
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse.swt.graphics.Image,
     *      java.lang.Object)
     */
    @Override
    public Image decorateImage(Image image, Object element) {
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.String,
     *      java.lang.Object)
     */
    @Override
    public String decorateText(String text, Object element) {
        if (element instanceof IProject) {
            IProject project = (IProject) element;
            try {
                if (gss.isLockedForProduction(project)) {
                    return text + " " + Messages.LockedForProductionLabelDecorator_DecoratorText; //$NON-NLS-1$
                }
            } catch (CoreException e) {
                RascUiActivator.getLogger()
                        .error("Could not check Locked for Production state for project " + project.getName()); //$NON-NLS-1$
            }
        } else if (element instanceof IResource) {
            IResource resource = (IResource) element;
            if (!resource.isDerived()) {
                IProject project = resource.getProject();
                if (project != null) {
                    try {
                        if (gss.isLockedForProduction(project)) {
                            return text + " " + Messages.LockedForProductionLabelDecorator_ReadOnlyDecoratorText; //$NON-NLS-1$
                        }
                    } catch (CoreException e) {
                        RascUiActivator.getLogger()
                                .error("Could not check Locked for Production state for project " + project.getName()); //$NON-NLS-1$
                    }
                }
            }
        }
        return text;
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     *
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.LabelDecorator#decorateImage(org.eclipse.swt.graphics.Image,
     *      java.lang.Object, org.eclipse.jface.viewers.IDecorationContext)
     */
    @Override
    public Image decorateImage(Image image, Object element, IDecorationContext context) {
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.LabelDecorator#decorateText(java.lang.String,
     *      java.lang.Object, org.eclipse.jface.viewers.IDecorationContext)
     */
    @Override
    public String decorateText(String text, Object element, IDecorationContext context) {
        return decorateText(text, element);
    }

    /**
     * @see org.eclipse.jface.viewers.LabelDecorator#prepareDecoration(java.lang.Object,
     *      java.lang.String, org.eclipse.jface.viewers.IDecorationContext)
     */
    @Override
    public boolean prepareDecoration(Object element, String originalText, IDecorationContext context) {
        return false;
    }

}
