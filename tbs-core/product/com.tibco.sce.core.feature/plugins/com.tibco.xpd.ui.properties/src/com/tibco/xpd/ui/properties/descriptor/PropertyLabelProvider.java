/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties.descriptor;

import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.internal.Messages;

/**
 * Property contribution label provider. This uses the label provider adapter of
 * the selected model to get the label and image of the selection.
 * 
 * @author wzurek
 */
public class PropertyLabelProvider extends LabelProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object sel) {
        boolean isReadOnly = false;
        String text = null;

        Object element = sel;
        if (element instanceof IStructuredSelection) {
            element = ((IStructuredSelection) element).getFirstElement();
        }

        if (element instanceof IAdaptable) {
            ILabelProvider lp =
                    (ILabelProvider) ((IAdaptable) element)
                            .getAdapter(ILabelProvider.class);
            if (lp != null) {
                text = lp.getText(sel);
            }

            EObject eObject =
                    (EObject) ((IAdaptable) element).getAdapter(EObject.class);
            if (eObject != null) {
                isReadOnly = WorkingCopyUtil.isReadOnly(eObject);
            }
        }

        if (text == null) {
            EObject eo = null;
            if (element instanceof EObject) {
                eo = (EObject) element;
            } else if (element instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) element).getAdapter(EObject.class);
            }
            if (eo != null) {
                EList list = eo.eAdapters();
                ItemProviderAdapter itemProvider = null;
                for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof ItemProviderAdapter) {
                        itemProvider = (ItemProviderAdapter) obj;
                    }
                }
                if (itemProvider != null) {
                    text = itemProvider.getText(eo);
                }

                isReadOnly = WorkingCopyUtil.isReadOnly(eo);
            }
        }

        if (text != null) {
            if (isReadOnly) {
                return text
                        + " " + Messages.PropertyLabelProvider_ReadOnly_label; //$NON-NLS-1$
            } else {
                return text;
            }
        }

        return super.getText(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object sel) {
        Object element = sel;
        if (element instanceof IStructuredSelection) {
            element = ((IStructuredSelection) element).getFirstElement();
        }
        if (element instanceof IAdaptable) {
            ILabelProvider lp =
                    (ILabelProvider) ((IAdaptable) element)
                            .getAdapter(ILabelProvider.class);
            if (lp != null) {
                return lp.getImage(element);
            }
        } else if (element instanceof EObject) {
            EObject eo = (EObject) element;

            return WorkingCopyUtil.getImage(eo);
        }
        return super.getImage(element);
    }

}
