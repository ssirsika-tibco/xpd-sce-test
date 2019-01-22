/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.view;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.impl.FragmentRootCategory;

/**
 * Fragments view tree content provider
 * 
 * @author njpatel
 * 
 */
public class FragmentsViewContentProvider implements ITreeContentProvider {

	private Object inputElement;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	public Object[] getChildren(Object parentElement) {
		Object[] children = null;

		if (parentElement instanceof IFragmentCategory) {
			Collection<IFragmentElement> childrenCollection = ((IFragmentCategory) parentElement)
					.getChildren();
			if (childrenCollection != null) {
				children = childrenCollection.toArray();
			}
		}

		return children != null ? children : new Object[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	public Object getParent(Object element) {
		Object parent = null;

		if (element instanceof IContainedFragmentElement) {
			parent = ((IContainedFragmentElement) element).getParent();
		}

		return parent != null ? parent : inputElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	public boolean hasChildren(Object element) {
		boolean hasChildren = false;

		if (element instanceof FragmentRootCategory) {
			// Always return true for the root category
			hasChildren = true;
		} else if (element instanceof IFragmentCategory) {
			Collection<IFragmentElement> children = ((IFragmentCategory) element)
					.getChildren();

			hasChildren = children != null && !children.isEmpty();
		}

		return hasChildren;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		this.inputElement = inputElement;

		Object[] elements = FragmentsManager.getInstance().getRootCategories();

		return elements != null ? elements : new Object[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Nothing to do
	}

}
