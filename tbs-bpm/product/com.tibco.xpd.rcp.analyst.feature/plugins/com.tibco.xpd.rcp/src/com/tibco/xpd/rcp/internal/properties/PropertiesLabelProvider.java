/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.ui.properties.descriptor.PropertyLabelProvider;

/**
 * Label provider for the properties section (for projects in Overview page).
 * 
 * @author njpatel
 */
public class PropertiesLabelProvider extends PropertyLabelProvider {

    /**
     * @see com.tibco.xpd.ui.properties.descriptor.PropertyLabelProvider#getText(java.lang.Object)
     * 
     * @param sel
     * @return
     */
    @Override
    public String getText(Object sel) {
        Object item = sel;
        if (item instanceof IStructuredSelection) {
            item = ((IStructuredSelection) item).getFirstElement();
        }

        if (item instanceof IProject) {
            return ((IProject) item).getName();
        }

        return super.getText(sel);
    }

    /**
     * @see com.tibco.xpd.ui.properties.descriptor.PropertyLabelProvider#getImage(java.lang.Object)
     * 
     * @param sel
     * @return
     */
    @Override
    public Image getImage(Object sel) {
        Object item = sel;
        if (item instanceof IStructuredSelection) {
            item = ((IStructuredSelection) item).getFirstElement();
        }

        if (item instanceof IProject) {
            return RCPActivator.getDefault().getImageRegistry()
                    .get(RCPImages.PROJECT.getPath());
        }
        return super.getImage(sel);
    }
}
