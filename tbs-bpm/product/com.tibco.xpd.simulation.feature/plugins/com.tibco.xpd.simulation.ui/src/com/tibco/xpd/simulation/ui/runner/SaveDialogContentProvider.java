/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.runner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * Save dialog content provider.
 */
public class SaveDialogContentProvider implements ITreeContentProvider {

    private StructuredViewer viewer;

    /**
     * Default constructor.
     */
    public SaveDialogContentProvider() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object dirtyMap) {
        if (dirtyMap instanceof Map) {
            Map inputMap = (Map) dirtyMap;
            List toReturn = new ArrayList(inputMap.size());
            Collection collection = inputMap.keySet();
            for (Iterator iter = collection.iterator(); iter.hasNext();) {
                IResource element = (IResource) iter.next();
                toReturn.add(element);
            }
            return toReturn.toArray();
        }
        return new Object[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        if (element instanceof Map) {
            Map inputMap = (Map) element;
            return inputMap.size() > 0;
        }
        return false;
    }

    /**
     * @return
     */
    public StructuredViewer getViewer() {
        return viewer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    }
}
