/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.MappingDirection;

/**
 * Abstract class for Item Provider for actual parameters. Can be used as source
 * or target, but you have to say which. This does not alter the content based
 * on the mappings so it is quite straight forward.
 * 
 * @author kthombar
 * @since 27-Sep-2013
 */
public abstract class AbstractProcessDataConceptPathProvider implements
        ITreeContentProvider {

    protected ConceptContentProvider umlContentProvider;

    public AbstractProcessDataConceptPathProvider(MappingDirection direction) {
        umlContentProvider = new ConceptContentProvider(direction);
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {

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

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getConceptPaths(inputElement);
    }

    /**
     * Gets the Concept Path for the input.
     * 
     * @param inputElement
     * @return
     */
    protected abstract ConceptPath[] getConceptPaths(Object inputElement);

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        return umlContentProvider.getChildren(parentElement);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        return umlContentProvider.getParent(element);
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        return umlContentProvider.hasChildren(element);
    }

}
