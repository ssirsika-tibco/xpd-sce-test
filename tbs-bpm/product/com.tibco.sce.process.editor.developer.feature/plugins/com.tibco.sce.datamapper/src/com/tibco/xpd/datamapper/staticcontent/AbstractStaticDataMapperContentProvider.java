/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.staticcontent;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * Base class for Content providers for that provide static content to data
 * mapper LHS/RHS trees (such as elements representing JavaScript class
 * objects).
 * <p>
 * <b>Note</b> that all content should be derived from {@link ConceptPath}, but
 * you will find the following helper classes useful also...
 * <li>{@link VirtualDataMapperFolder}</li>
 * <li>{@link StaticContentDataMapperElement}</li>
 * <li>Derivatives of {@link AbstractStaticContentDataMapperElement}</li>
 * <li>{@link ConceptPath}</li>
 * 
 */
public abstract class AbstractStaticDataMapperContentProvider implements
        ITreeContentProvider {

    private ConceptPath contentCache[] = null;

    /**
     * Sub-class should Create the content cache (if not already created). This
     * will be called only once on demand.
     */
    protected abstract ConceptPath[] createContentCache();

    /**
     * 
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
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        ensureContentCached();

        return contentCache;
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        ensureContentCached();

        /*
         * All of of content is now based upon ConcepPath, so now life is
         * easy....
         */
        if (parentElement instanceof ConceptPath) {
            return ((ConceptPath) parentElement).getChildren().toArray();
        }

        return new Object[0];
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Object getParent(Object element) {
        ensureContentCached();

        /*
         * All of of content is now based upon ConcepPath, so now life is
         * easy....
         */
        if (element instanceof ConceptPath) {
            return ((ConceptPath) element).getParent();

        }
        return null;
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean hasChildren(Object element) {
        ensureContentCached();

        /*
         * All of of content is now based upon ConcepPath, so now life is
         * easy....
         */
        if (element instanceof ConceptPath) {
            return !((ConceptPath) element).getChildren().isEmpty();
        }
        return false;
    }

    /**
     * Create the content cache (if not already created).
     */
    private void ensureContentCached() {
        if (contentCache == null) {
            contentCache = createContentCache();

            if (contentCache == null) {
                contentCache = new ConceptPath[0];
            }
        }
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }
}