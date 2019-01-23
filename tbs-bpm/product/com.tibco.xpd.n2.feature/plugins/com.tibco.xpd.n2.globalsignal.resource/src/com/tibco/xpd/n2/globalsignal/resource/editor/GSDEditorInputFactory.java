/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;

/**
 * Input factory for Global Signal Definition editor.
 * 
 * @author sajain
 * @since Feb 11, 2015
 */
public class GSDEditorInputFactory implements EditorInputFactory {

    /**
     * @see com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory#getEditorInputFor(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public IEditorInput getEditorInputFor(Object obj) {

        if (obj instanceof IFile) {

            IFile gsdFile = (IFile) obj;

            if (gsdFile != null && gsdFile.isAccessible()) {

                return new FileEditorInput(gsdFile);
            }
        }
        return null;
    }

}
