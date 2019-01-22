/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.editors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;

/**
 * Label Provider for property sheets contributions from the process interface
 * editor.
 * 
 * @author rsomayaj
 * 
 */
public class ProcessIfcPropertyLabelProvider extends
        ProjectExplorerLabelProvider {

    @Override
    public String getText(Object element) {
        EObject eo = null;
        if (element instanceof IStructuredSelection) {
            element = ((IStructuredSelection) element).getFirstElement();
        }
        if (element instanceof IAdaptable) {
            Object eObjAdapter =
                    ((IAdaptable) element).getAdapter(EObject.class);
            if (eObjAdapter != null) {
                eo = (EObject) eObjAdapter;
            }
        } else if (element instanceof EObject) {
            eo = (EObject) element;
        }
        if (eo != null) {
            return WorkingCopyUtil.getMetaText(eo);
        }
        return super.getText(element);
    }

    @Override
    public Image getImage(Object element) {
        EObject eo = null;
        if (element instanceof IStructuredSelection) {
            element = ((IStructuredSelection) element).getFirstElement();
        }
        if (element instanceof IAdaptable) {
            Object eObjAdapter =
                    ((IAdaptable) element).getAdapter(EObject.class);
            if (eObjAdapter != null) {
                eo = (EObject) eObjAdapter;
            }
        } else if (element instanceof EObject) {
            eo = (EObject) element;
        }
        if (eo != null) {
            return WorkingCopyUtil.getImage(eo);
        }
        return super.getImage(element);
    }

}