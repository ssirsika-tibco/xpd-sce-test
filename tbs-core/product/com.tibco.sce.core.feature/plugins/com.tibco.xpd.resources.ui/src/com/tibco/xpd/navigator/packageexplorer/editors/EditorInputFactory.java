/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.navigator.packageexplorer.editors;

import org.eclipse.ui.IEditorInput;

/**
 * Interface that have to be implemented by clients that want to provide editor
 * for particular model element. See 'objectEditor' extension point.
 * 
 * @author wzurek
 */
public interface EditorInputFactory {

    /**
     * Produce EditorInput for editor of provided object. Should return null if
     * the factory cannot produce the input.
     * 
     * @param obj
     * @return
     */
    IEditorInput getEditorInputFor(Object obj);
}
