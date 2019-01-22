/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;

/**
 * Source content provider for the Global Data Errors mapper.
 * 
 * @author njpatel
 */
public class GlobalDataErrorSourceContentProvider implements
        ITreeContentProvider {

    private final ActivityScriptContentProvider scriptContentProvider;

    /**
     * Source content provider for the Global Data Errors mapper.
     */
    public GlobalDataErrorSourceContentProvider() {
        scriptContentProvider =
                new ActivityScriptContentProvider(MappingDirection.OUT);
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
        scriptContentProvider.dispose();
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        scriptContentProvider.inputChanged(viewer, oldInput, newInput);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        List<Object> errorParams = new ArrayList<Object>();

        //
        // Always add the standard special inbuilt [Error Code] param.
        //

        errorParams
                .add(ConceptUtil
                        .getConceptPath(StandardMappingUtil.CATCH_ERRORCODE_FORMALPARAMETER));
        errorParams
                .add(ConceptUtil
                        .getConceptPath(StandardMappingUtil.CATCH_ERRORDETAIL_FORMALPARAMETER));

        //
        // Then add script content.
        //
        Object[] scriptEls = scriptContentProvider.getElements(inputElement);
        if (scriptEls != null) {
            for (int i = 0; i < scriptEls.length; i++) {
                errorParams.add(scriptEls[i]);
            }
        }

        return errorParams.toArray();
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        return new Object[0];
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        return false;
    }

}
