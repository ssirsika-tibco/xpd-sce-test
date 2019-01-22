/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * 
 * 
 * @author kupadhya
 * @since 7 Dec 2012
 */
public class ProjectWorkbenchAdapter implements IWorkbenchAdapter {

    private IResource resource;

    public ProjectWorkbenchAdapter(IResource resource) {
        this.resource = resource;
    }

    /**
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
     * 
     * @param o
     * @return
     */
    @Override
    public Object[] getChildren(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    public ImageDescriptor getImageDescriptor(Object object) {
        if (object instanceof IProject) {
            return PlatformUI
                    .getWorkbench()
                    .getSharedImages()
                    .getImageDescriptor(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT);
        } else if (object instanceof IFolder) {
            return PlatformUI.getWorkbench().getSharedImages()
                    .getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
        } else if (object instanceof IFile) {
            return PlatformUI.getWorkbench().getSharedImages()
                    .getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
        }
        return PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
    }

    /**
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
     * 
     * @param o
     * @return
     */
    @Override
    public String getLabel(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
     * 
     * @param o
     * @return
     */
    @Override
    public Object getParent(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

}
