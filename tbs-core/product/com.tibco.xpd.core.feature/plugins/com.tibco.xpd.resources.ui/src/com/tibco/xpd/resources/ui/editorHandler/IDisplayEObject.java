/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.ui.editorHandler;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface used to display or select EObjects in a UI component. This is
 * intended to be used as a replacement for IGotoEObject particularly for
 * EditorPart classes to assist in with opening editors and selecting a specific
 * EObject in them.
 * 
 * @author nwilson
 * @since 27 Feb 2015
 */
public interface IDisplayEObject {
    /**
     * Open editor for eObject, reveal the display item for it and optionally
     * select it.
     * 
     * @param selectObject
     * @param eObjects
     * @return success or failure (editor not found or no visual part for
     *         object)
     */
    boolean gotoEObject(boolean selectObjects, EObject... eObjects);

}
