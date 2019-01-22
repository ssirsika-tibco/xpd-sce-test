/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.fragments;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorInput;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Service Process Filter that binds the specific Service Process fragment
 * provider to the content of the editor
 * 
 * 
 * @author bharge
 * @since 21 Jan 2015
 */
public class ServiceProcessFilter implements IFilter {

    private static final String SERVICE_PROCESS_TEMPLATE_PROVIDER =
            "ServiceProcessTemplateProvider"; //$NON-NLS-1$

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    public boolean select(Object toTest) {

        if (toTest instanceof AbstractProcessDiagramEditor) {

            AbstractProcessDiagramEditor editor =
                    (AbstractProcessDiagramEditor) toTest;
            IEditorInput input = editor.getEditorInput();
            if (input instanceof ProcessEditorInput) {

                ProcessEditorInput processInput = (ProcessEditorInput) input;
                boolean select =
                        Xpdl2ModelUtil.isServiceProcess(processInput
                                .getProcess());
                return select;
            }
        } else if (toTest instanceof String) {

            return toTest.equals(SERVICE_PROCESS_TEMPLATE_PROVIDER);
        }
        return false;
    }

}
