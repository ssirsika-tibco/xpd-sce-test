/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.clipboard;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteTarget;
import org.eclipse.gmf.runtime.emf.clipboard.core.PostPasteChildOperation;

/**
 * Paste child operation for <code>Stereotype</code> applications.
 * 
 * @author njpatel
 * 
 */
public class StereotypePasteOperation extends OverridePasteChildOperation {

    /**
     * Paste operation for <code>Stereotype</code> applications.
     * 
     * @param overriddenChildPasteOperation
     */
    public StereotypePasteOperation(
            PasteChildOperation overriddenChildPasteOperation) {
        super(overriddenChildPasteOperation);
    }

    @Override
    public void paste() throws Exception {
        // Do nothing, process in post-paste operation
    }

    @Override
    public PasteChildOperation getPostPasteOperation() {
        return new PostPasteChildOperation(this, EMPTY_ARRAY) {
            @Override
            public void paste() throws Exception {
                
                if(isCancelled()) {
                    throwCancelException();
                }
                
                EObject element = doPasteInto(getParentTarget());
                if (element != null) {
                    setPastedElement(element);
                    addPastedElement(getPastedElement());
                } else {
                    addPasteFailuresObject(getEObject());
                }
            }

            @Override
            public PasteTarget getParentTarget() {
                return new PasteTarget(getParentResource());
            }

            @Override
            protected boolean handleCollision(EReference reference,
                    EObject object, EObject object2, ObjectInfo objectInfo) {

                /*
                 * If this is a Stereotype then just copy as is - don't handle
                 * collision as, otherwise, any "name" attribute in the
                 * stereotype will have an altered name to avoid name collision
                 */
                if (object instanceof DynamicEObjectImpl) {
                    // Just copy the object
                    return true;
                }

                return super.handleCollision(reference, object, object2,
                        objectInfo);
            }

            @SuppressWarnings("unchecked")
            @Override
            protected boolean handleCollision(EReference reference, List list,
                    EObject object, ObjectInfo objectInfo) {
                /*
                 * If this is a Stereotype then just copy as is - don't handle
                 * collision as, otherwise, any "name" attribute in the
                 * stereotype will have an altered name to avoid name collision
                 */
                if (object instanceof DynamicEObjectImpl) {
                    // Just copy the object
                    return true;
                }

                return super.handleCollision(reference, list, object,
                        objectInfo);
            }
        };
    }
}
