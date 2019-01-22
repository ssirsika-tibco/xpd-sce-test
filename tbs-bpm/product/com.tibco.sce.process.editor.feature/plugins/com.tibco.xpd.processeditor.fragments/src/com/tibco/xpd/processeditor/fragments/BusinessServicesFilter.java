/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.fragments;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorInput;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Business Services Filter that binds the specific Business Services fragment
 * provider to the content of the editor
 * 
 * @author bharge
 * @since 30 Jul 2014
 */
public class BusinessServicesFilter implements IFilter {

    private static final String BUSINESS_SERVICE_TEMPLATE_PROVIDER =
            "BusinessServiceTemplateProvider"; //$NON-NLS-1$

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
                        Xpdl2ModelUtil.isPageflowBusinessService(processInput
                                .getProcess());
                return select;
            }
        } else if (toTest instanceof String) {

            return toTest.equals(BUSINESS_SERVICE_TEMPLATE_PROVIDER);
        }
        return false;
    }

}
