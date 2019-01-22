/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.views.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution;
import com.tibco.xpd.dependency.visualization.api.EventTypeEnum;
import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationEditorPreferencesManager;
import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationUtils;

/**
 * This provider scans the extension point for any available extensions. This class delegates method to extender to provide 
 * required information like contents, text, images, etc   
 *
 * @author ssirsika
 * @since 30-Sep-2015
 */
public class BaseDependencyGraphViewerProvider extends HighlightArtifactsProvider implements IGraphEntityContentProvider {

	private Set<DependencyEditorContribution> viewerContentContributors = Collections.emptySet();

	private boolean filterUnrelatedItems = !DependencyVisualizationEditorPreferencesManager.getShowUnrelatedNodesPreferenceValue();

	private boolean showAllInWorkspace = DependencyVisualizationEditorPreferencesManager.getShowAllInWorkspacePreferenceValue();

	/**
	 * 
	 */
	public BaseDependencyGraphViewerProvider(GraphViewer viewer, AbstractVisualizationLabelProvider currentLabelProvider) {
		super(viewer, currentLabelProvider);
		viewerContentContributors = DependencyVisualizationUtils.getViewerContentContributors();
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IGraphEntityContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement input element
	 * @return Object array of all the related items with respect to inputElement
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		Collection<Object> result = new ArrayList<Object>();
		for (DependencyEditorContribution contentContributor : viewerContentContributors) {
			Collections.addAll(result, contentContributor.getElements(inputElement, isFilterUnrelatedItems(), shouldShowAllInWorkspace()));
		}
		return result.toArray();
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IGraphEntityContentProvider#getConnectedTo(java.lang.Object)
	 *
	 * @param entity
	 * @return Object array of all the connected objects to passed 'entry'
	 */
	@Override
	public Object[] getConnectedTo(Object entity) {
		Collection<Object> result = new ArrayList<Object>();
		for (DependencyEditorContribution contentContributor : viewerContentContributors) {
			Collections.addAll(result, contentContributor.getDependencies(entity));
		}
		return result.toArray();
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.internal.views.provider.AbstractVisualizationLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return {@link String} representation (name) of passed object
	 */
	@Override
	public String getText(Object element) {
		for (DependencyEditorContribution contentContributor : viewerContentContributors) {
			String text = contentContributor.getText(element);
			if (text != null && !text.isEmpty()) {
				return text;
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.internal.views.provider.AbstractVisualizationLabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return return {@link Image} representation of passed 'element' otherwise null. 
	 */
	@Override
	public Image getImage(Object element) {
		for (DependencyEditorContribution contentContributor : viewerContentContributors) {
			Image image = contentContributor.getImage(element);
			if (image != null) {
				return image;
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 *
	 */
	@Override
	public void dispose() {
		// call dispose of all the contributed content provider. This should clear all the allocated stale objects.
		for (DependencyEditorContribution contentContributor : viewerContentContributors) {
			contentContributor.dispose();
		}
	}

	public boolean isFilterUnrelatedItems() {
		return filterUnrelatedItems;
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.internal.views.provider.XPDGraphVisualizationLabelProvider#setFilterUnrelatedItems(boolean)
	 *
	 * @param filteredUnrelatedItems
	 */
	@Override
	public void setFilterUnrelatedItems(boolean filteredUnrelatedItems) {
		this.filterUnrelatedItems = filteredUnrelatedItems;
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.internal.views.provider.XPDGraphVisualizationLabelProvider#handleDoubleClick(java.lang.Object)
	 *
	 * @param selectedElement
	 */
	@Override
	public void handleDoubleClick(Object selectedElement) {
		for (DependencyEditorContribution contentContributor : viewerContentContributors) {
			contentContributor.handleElementEvent(EventTypeEnum.DOUBLECLICK, selectedElement);
		}
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.internal.views.provider.XPDGraphVisualizationLabelProvider#setShowAllInWorkspace(boolean)
	 *
	 * @param showAllInWorkspace
	 */
	@Override
	public void setShowAllInWorkspace(boolean showAllInWorkspace) {
		this.showAllInWorkspace = showAllInWorkspace;
	}

	/**
	 * @return the showAllInWorkspace
	 */
	public boolean shouldShowAllInWorkspace() {
		return showAllInWorkspace;
	}
}
