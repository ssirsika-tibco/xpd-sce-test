/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.quickSearch;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.zest.core.viewers.GraphViewer;

import com.tibco.xpd.dependency.visualization.internal.views.DependencyVisualizationEditor;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 *
 * Content provider for quick search functionality. This returns the list of element currently available or visible in the editor. 
 * @author ssirsika
 * @since 20-Nov-2015
 */
public class DependencyEditorQuickSearchContentProvider extends AbstractQuickSearchContentProvider {

	/**
	 * @param partRef
	 */
	public DependencyEditorQuickSearchContentProvider(IWorkbenchPartReference partRef) {
		super(partRef);
	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#dispose()
	 *
	 */
	@Override
	public void dispose() {

	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#getCategories()
	 *
	 * @return
	 */
	@Override
	public Collection<QuickSearchPopupCategory> getCategories() {
		return null;
	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#getElements(java.util.Collection)
	 *
	 * @param categories
	 * @return
	 */
	@Override
	public Collection<?> getElements(Collection<QuickSearchPopupCategory> categories) {
		return null;
	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider#getElements()
	 *
	 * @return
	 */
	@Override
	public Collection<?> getElements() {
		IWorkbenchPart part = getWorkbenchPartRef().getPart(false);
		if (part instanceof DependencyVisualizationEditor) {
			GraphViewer graphViewer = ((DependencyVisualizationEditor) part).getGraphViewer();
			return graphViewer != null ? Arrays.asList(graphViewer.getNodeElements()) : Collections.EMPTY_LIST;
		}
		return Collections.EMPTY_LIST;
	}

}
