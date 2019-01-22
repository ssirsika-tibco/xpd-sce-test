/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.quickSearch;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.zest.core.viewers.GraphViewer;

import com.tibco.xpd.dependency.visualization.internal.views.DependencyVisualizationEditor;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider;

/**
 * LabelProvide for DependencyEditor
 *
 * @author ssirsika
 * @since 20-Nov-2015
 */
public class DependencyEditorQuickSearchLabelProvider extends AbstractQuickSearchLabelProvider {

	private ILabelProvider editorLabelProvider;

	/**
	 * @param partRef
	 */
	public DependencyEditorQuickSearchLabelProvider(IWorkbenchPartReference partRef) {
		super(partRef);
		IWorkbenchPart part = getWorkbenchPartRef().getPart(false);
		if (part instanceof DependencyVisualizationEditor) {
			GraphViewer graphViewer = ((DependencyVisualizationEditor) part).getGraphViewer();
			IBaseLabelProvider lp = graphViewer.getLabelProvider();
			if (lp instanceof ILabelProvider) {
				editorLabelProvider = (ILabelProvider) lp;
			}
		}
	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object obj) {
		if (editorLabelProvider != null) {
			return editorLabelProvider.getText(obj);
		}
		return super.getText(obj);
	}

	/**
	 * @see com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchLabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(Object resource) {
		if (editorLabelProvider != null) {
			return editorLabelProvider.getImage(resource);
		}
		return super.getImage(resource);
	}
}
