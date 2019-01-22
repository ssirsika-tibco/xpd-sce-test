/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author nwilson
 */
public class SimulationResultsContentProvider implements ITreeContentProvider {
    /** The resources used as an input for this content provider. */
    private IResource[] resources;

    /**
     * @param parentElement
     *            The parent object for which to get the children.
     * @return An empty array.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(final Object parentElement) {
        ArrayList<Object> children = new ArrayList<Object>();
        Object[] childrenArray = new Object[children.size()];
        children.toArray(childrenArray);
        return childrenArray;
    }

    /**
     * Not supported.
     * 
     * @param element
     *            The element to get the parent for.
     * @return null.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    @Override
    public Object getParent(final Object element) {
        return null;
    }

    /**
     * @param element
     *            The element to check.
     * @return false.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(final Object element) {
        return false;
    }

    /**
     * @param input
     *            ignored.
     * @return The array of resources.
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(final Object input) {
        List<IResource> ret = new ArrayList<IResource>();
        if (resources != null) {
            // Only return resources that exist.
            for (IResource res : resources) {
                if (res.exists()) {
                    ret.add(res);
                }
            }
        }

        return ret.toArray();
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param viewer
     *            The viewer for this content.
     * @param oldInput
     *            The old input.
     * @param newInput
     *            The new input.
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(final Viewer viewer, final Object oldInput,
            final Object newInput) {
        if (newInput instanceof IAdaptable) {
            resources =
                    (IResource[]) ((IAdaptable) newInput)
                            .getAdapter(IResource[].class);
        }
    }
}
