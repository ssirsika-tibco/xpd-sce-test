/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources.ui.editorHandler;

/**
 * Interface to be implemented by the contributor to
 * <code>com.tibco.xpd.resources.ui.openEditorHandler</code> extension point.
 * <p>
 * <strong><u>NOTE: THIS IS FOR INTERNAL USE ONLY.</u></strong>
 * </p>
 * 
 * @author njpatel
 */
public interface IOpenEditorHandler {

    /**
     * Check if this contribution understands, and can open an editor, for the
     * given object.
     * 
     * @param object
     *            object to show in an editor
     * @return <code>true</code> if this contribution knows how to open and show
     *         the object in its editor (if it has representation in an editor),
     *         <code>false</code> otherwise.
     */
    boolean canHandle(Object object);

    /**
     * Open and select the object in its editor.
     * 
     * @param object
     * @return <code>true</code> if the editor was opened, <code>false</code> if
     *         no editor is opened, or the object is not representated in an
     *         editor.
     * 
     */
    boolean openEditor(Object object);

}
