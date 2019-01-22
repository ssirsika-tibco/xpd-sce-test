/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.editor;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface to be implemented by any editor that can reveal/select a given
 * object.
 * 
 * @author njpatel
 * 
 */
public interface IGotoObject {

    /**
     * Reveal/Select the given object in the editor.
     * 
     * @param eObject
     *            object to reveal.
     */
    void reveal(EObject eObject);
}
