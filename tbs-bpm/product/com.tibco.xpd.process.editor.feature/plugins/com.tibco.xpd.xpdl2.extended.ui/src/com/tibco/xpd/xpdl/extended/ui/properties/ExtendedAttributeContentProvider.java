package com.tibco.xpd.xpdl.extended.ui.properties;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.xpdExtension.XpdExtAttributes;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;

public class ExtendedAttributeContentProvider implements
        IStructuredContentProvider {

    public Object[] getElements(Object element) {
        List<?> children = null;
        if (element instanceof ExtendedAttributesContainer) {
            ExtendedAttributesContainer container = (ExtendedAttributesContainer) element;
            children = container.getExtendedAttributes();
        } else if (element instanceof XpdExtAttributes) {
            XpdExtAttributes  container = (XpdExtAttributes) element;
            children = container.getAttributes();
        }
        return children.toArray();
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
