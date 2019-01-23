/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import java.util.ArrayList;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Resource;

/**
 * Content provider for RSD editor main section. This content provider is
 * expected to adapt and delegate to
 * {@link TransactionalAdapterFactoryContentProvider}.
 *
 * @author jarciuch
 * @since 30 Jan 2015
 */
public class RsdContentProvider implements ITreeContentProvider {

    private Object[] EMPTY = new Object[0];

    private ITreeContentProvider delegate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        /*
         * Do nothing. Delegate is not created by this class so it should not be
         * disposed either.
         */
    }

    /**
     * Creates a new content provider which in most cases delegate to the global
     * editing domain {@link AdapterFactoryContentProvider}.
     */
    public RsdContentProvider(ITreeContentProvider delegate) {
        this.delegate = delegate;

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
        delegate.inputChanged(viewer, oldInput, newInput);

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     *
     * @param inputElement
     * @return
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return delegate.getElements(inputElement);
    }

    /**
     * Behaves like {@link AdapterFactoryContentProvider}, but Resource will
     * only have Methods as his children, and Method will not have children.
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     *
     * @param parentElement
     *            the parent element to retrieve children for.
     * @return children of the parent element.
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        Object[] children = delegate.getChildren(parentElement);
        if (parentElement instanceof Resource) {
            ArrayList<Object> filtered = new ArrayList<>();
            for (Object c : children) {
                if (c instanceof Method) {
                    filtered.add(c);
                }
            }
            return filtered.toArray();
        } else if (parentElement instanceof Method) {
            return EMPTY;
        }
        return children;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getParent(Object element) {
        return delegate.getParent(element);
    }

    /**
     * {@inheritDoc}
     * 
     * @see {@link #getChildren(Object)}
     */
    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof Resource) {
            return getChildren(element).length != 0;
        } else if (element instanceof Method) {
            return false;
        }
        return delegate.hasChildren(element);
    }

}
