/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.mapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Label provider for Process data to Dynamic Organization Indentifier Mapper
 * section.
 * 
 * @author kthombar
 * @since 01-Oct-2013
 */
public class DynamicOrgIdLabelProvider implements ILabelProvider {

    public DynamicOrgIdLabelProvider() {

    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    public void addListener(ILabelProviderListener listener) {

    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     * 
     */
    public void dispose() {

    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     * 
     * @param element
     * @param property
     * @return
     */
    public boolean isLabelProperty(Object element, String property) {

        return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    public void removeListener(ILabelProviderListener listener) {

    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    public Image getImage(Object element) {
        Image image = null;
        if (element instanceof Organization) {
            Organization organization = (Organization) element;
            image = WorkingCopyUtil.getImage(organization);
        }

        else if (element instanceof DynamicOrgIdentiferPath) {
            DynamicOrgIdentiferPath details = (DynamicOrgIdentiferPath) element;
            DynamicOrgIdentifier dynamicOrgIdentifier =
                    details.getDynamicOrgIdentifier();

            image = WorkingCopyUtil.getImage(dynamicOrgIdentifier);

        } else if (element instanceof String) {
            image =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(ProcessWidgetConstants.IMG_INFO_ICON);
        }
        return image;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    public String getText(Object element) {
        String text = ""; //$NON-NLS-1$
        if (element instanceof Organization) {
            text = ((Organization) element).getLabel();
        }

        else if (element instanceof DynamicOrgIdentiferPath) {
            DynamicOrgIdentiferPath dynamicOrgIdentiferPath =
                    (DynamicOrgIdentiferPath) element;
            text = dynamicOrgIdentiferPath.getLabel();
        } else if (element instanceof String) {
            text = (String) element;
        }
        return text;
    }

}
