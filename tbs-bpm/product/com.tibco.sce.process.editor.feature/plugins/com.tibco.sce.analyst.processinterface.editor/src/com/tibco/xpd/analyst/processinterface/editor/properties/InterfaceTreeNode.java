/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.StartMethod;

/**
 * Tree Node wrapper that contains {@link EObject}s. This data structure is used
 * while displaying trees within Listboxes for {@link StartMethod}s and
 * {@link IntermediateMethod}s on a Process Interface editor.
 * 
 * @author rsomayaj
 * 
 */
public class InterfaceTreeNode extends TreeNode implements IAdaptable {

    public InterfaceTreeNode(Object value) {
        super(value);
    }

    public Object getAdapter(Class adapter) {
        if (adapter == EObject.class) {
            return getEObjectFromTreeNode();
        } else if (adapter == ILabelProvider.class) {

            final EObject eo = getEObjectFromTreeNode();

            if (eo != null) {

                return new ILabelProvider() {

                    public Image getImage(Object element) {

                        return WorkingCopyUtil.getImage(eo);
                    }

                    public String getText(Object element) {
                        return WorkingCopyUtil.getMetaText(eo);
                    }

                    public void addListener(ILabelProviderListener listener) {
                    }

                    public void dispose() {
                    }

                    public boolean isLabelProperty(Object element,
                            String property) {
                        return false;
                    }

                    public void removeListener(ILabelProviderListener listener) {

                    }
                };
            }
        }
        return null;
    }

    /**
     * 
     */
    private EObject getEObjectFromTreeNode() {
        InterfaceTreeNode parentNode;
        Object parent = getParent();
        if (getValue() instanceof ErrorMethod) {
            return (EObject) this.getValue();
        }
        while (parent != null && parent instanceof InterfaceTreeNode) {
            parentNode = (InterfaceTreeNode) parent;
            if (parentNode.getValue() instanceof InterfaceMethod) {
                return (EObject) parentNode.getValue();
            }
            parent = parentNode.getParent();
        }

        if (this.getValue() != null && this.getValue() instanceof EObject)
            return (EObject) this.getValue();

        return null;
    }

}
