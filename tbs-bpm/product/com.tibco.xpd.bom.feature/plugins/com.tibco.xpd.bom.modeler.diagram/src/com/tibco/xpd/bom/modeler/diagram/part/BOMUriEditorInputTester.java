/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.common.ui.URIEditorInput;

/**
 * @generated
 */
public class BOMUriEditorInputTester extends PropertyTester {

    /**
     * @generated
     */
    public boolean test(Object receiver, String method, Object[] args,
            Object expectedValue) {
        if (false == receiver instanceof URIEditorInput) {
            return false;
        }
        URIEditorInput editorInput = (URIEditorInput) receiver;
        return "bom_diagram".equals(editorInput.getURI().fileExtension()); //$NON-NLS-1$
    }

}
