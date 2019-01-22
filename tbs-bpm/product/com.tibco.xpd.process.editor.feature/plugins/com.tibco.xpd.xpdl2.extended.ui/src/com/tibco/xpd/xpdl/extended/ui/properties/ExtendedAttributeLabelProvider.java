package com.tibco.xpd.xpdl.extended.ui.properties;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttribute;

public class ExtendedAttributeLabelProvider implements ILabelProvider {

    public Image getImage(Object element) {
        return null;
    }

    public String getText(Object element) {
        String label = ""; //$NON-NLS-1$
        if (element instanceof ExtendedAttribute) {
            ExtendedAttribute attribute = (ExtendedAttribute) element;
            label = attribute.getName();
        } else if (element instanceof XpdExtAttribute) {
            XpdExtAttribute attribute = (XpdExtAttribute) element;
            label = attribute.getName();
        }
        return label;
    }

    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public boolean isLabelProperty(Object element, String property) {
        return true;
    }

    public void removeListener(ILabelProviderListener listener) {
    }

}
