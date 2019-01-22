/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.quickSearch;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationUtils;
import com.tibco.xpd.dependency.visualization.internal.views.DependencyVisualizationEditor;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution;

/**
 *
 *
 * @author ssirsika
 * @since 09-Nov-2015
 */
public class DependencyEditorQuickSearchContributor extends AbstractQuickSearchPopupContribution {

	/**
	 * 
	 */
	public DependencyEditorQuickSearchContributor() {
	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution#createContentProvider(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 * @return
	 */
	@Override
	public AbstractQuickSearchContentProvider createContentProvider(IWorkbenchPartReference partRef) {
		return new DependencyEditorQuickSearchContentProvider(partRef);
	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution#createLabelProvider(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 * @return
	 */
	@Override
	public AbstractQuickSearchLabelProvider createLabelProvider(IWorkbenchPartReference partRef) {
		return new DependencyEditorQuickSearchLabelProvider(partRef);
	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchPopupContribution#selectAndReveal(org.eclipse.ui.IWorkbenchPartReference, java.lang.Object)
	 *
	 * @param partRef
	 * @param element
	 * @return
	 */
	@Override
	public Rectangle selectAndReveal(IWorkbenchPartReference partRef, Object element) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof DependencyVisualizationEditor) {
			((DependencyVisualizationEditor) part).updateInput(element, true);
			// select the element in the project explorer
			DependencyVisualizationUtils.showInProjectExplorerView(element, 0);
		}
		return null;
	}

}
