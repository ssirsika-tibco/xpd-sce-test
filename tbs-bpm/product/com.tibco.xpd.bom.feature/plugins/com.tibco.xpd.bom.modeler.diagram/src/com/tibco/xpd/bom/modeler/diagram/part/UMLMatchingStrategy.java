/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;

/**
 * @generated
 */
public class UMLMatchingStrategy implements IEditorMatchingStrategy {

    /**
     * @generated
     */
    @Override
    public boolean matches(IEditorReference editorRef, IEditorInput input) {
        IEditorInput editorInput;
        try {
            editorInput = editorRef.getEditorInput();
        } catch (PartInitException e) {
            return false;
        }

        if (editorInput != null) {
            if (editorInput.equals(input)) {
                return true;
            }
            if (editorInput instanceof URIEditorInput && input instanceof URIEditorInput) {
                return ((URIEditorInput) editorInput).getURI().equals(((URIEditorInput) input).getURI());
            }
        }
        return false;
    }

}
