/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorInput;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class BusinessProcessFilter implements IFilter {

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    public boolean select(Object toTest) {
        boolean select = false;
        if (toTest instanceof AbstractProcessDiagramEditor) {
            AbstractProcessDiagramEditor editor = (AbstractProcessDiagramEditor) toTest;
            IEditorInput input = editor.getEditorInput();
            if (input instanceof ProcessEditorInput) {
                ProcessEditorInput processInput = (ProcessEditorInput) input;
                select = !Xpdl2ModelUtil.isPageflow(processInput.getProcess());
            }
        }
        return select;
    }

}
