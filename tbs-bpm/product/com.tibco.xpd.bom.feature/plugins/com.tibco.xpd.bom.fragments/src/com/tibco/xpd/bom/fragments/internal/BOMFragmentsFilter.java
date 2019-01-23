/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.fragments.internal;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;

/**
 * BOM fragment filter that will show the fragments view for BOM models only.
 * This is done by checking for the {@link Model}'s viewpoint, which should be
 * set to <code>null</code> for BOM models.
 * 
 * @author njpatel
 * 
 */
public class BOMFragmentsFilter implements IFilter {

    public BOMFragmentsFilter() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        boolean select = false;
        if (toTest instanceof IEditorPart) {
            IEditorInput editorInput = ((IEditorPart) toTest).getEditorInput();

            /*
             * BOM models should have the model viewpoint set to null
             */
            if (editorInput != null) {
                BOMWorkingCopy wc = getWorkingCopy(editorInput);

                if (wc != null) {
                    EObject rootElement = wc.getRootElement();

                    if (rootElement instanceof Model) {
                        select = ((Model) rootElement).getViewpoint() == null;
                    }
                }
            }
        }
        return select;
    }

    /**
     * Get the working copy of the model associated with the editor input.
     * 
     * @param editorInput
     * @return Working copy of the model being edited or <code>null</code> if
     *         working copy not found.
     */
    private BOMWorkingCopy getWorkingCopy(IEditorInput editorInput) {
        WorkingCopy wc = (WorkingCopy) editorInput
                .getAdapter(WorkingCopy.class);

        if (wc == null) {
            wc = (WorkingCopy) Platform.getAdapterManager().getAdapter(
                    editorInput, WorkingCopy.class);
        }

        return (BOMWorkingCopy) (wc instanceof BOMWorkingCopy ? wc : null);
    }

}
