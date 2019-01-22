/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.overview;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;

/**
 * Label provider for the project viewer.
 * 
 * @author njpatel
 */
public class WorkspaceResourceLabelProvider extends LabelProvider {

    private ILabelDecorator decorator = PlatformUI.getWorkbench()
            .getDecoratorManager().getLabelDecorator();

    /**
     * 
     */
    public WorkspaceResourceLabelProvider() {
    }

    /**
     * @see org.eclipse.jface.viewers.BaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
        if (listener != null) {
            decorator.addListener(listener);
        }

        super.addListener(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.BaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
        if (listener != null) {
            decorator.removeListener(listener);
        }
        super.removeListener(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {
        if (!(element instanceof IResource) && element instanceof IAdaptable) {
            element = ((IAdaptable) element).getAdapter(IProject.class);
        }

        if (element instanceof IResource) {
            String text = ((IResource) element).getName();
            if (text != null) {
                return decorator.decorateText(text, element);
            }
        }
        return super.getText(element);
    }

    /**
     * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {

        if (!(element instanceof IResource) && element instanceof IAdaptable) {
            element = ((IAdaptable) element).getAdapter(IResource.class);
        }

        if (element instanceof IProject) {
            Image img =
                    RCPActivator.getDefault().getImageRegistry()
                            .get(RCPImages.PROJECT.getPath());
            if (img != null) {
                return decorator.decorateImage(img, element);
            }

        }
        return super.getImage(element);
    }
}
