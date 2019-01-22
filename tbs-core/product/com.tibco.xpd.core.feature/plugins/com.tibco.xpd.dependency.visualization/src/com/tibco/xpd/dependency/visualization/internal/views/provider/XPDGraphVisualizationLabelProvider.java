/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.dependency.visualization.internal.views.provider;

import org.eclipse.jface.viewers.ILabelProvider;

/**
 *
 * This interface specifies methods which are related to graph viewer shows relationships between resources present in the workspaces.
 * @author ssirsika
 * @since 30-Nov-2015
 */
public interface XPDGraphVisualizationLabelProvider extends ILabelProvider {

	/**
	 * Sets the currently selected Object
	 * @param root The root node in this dependency
	 * @param currentSelection The currently selected node
	 */
	public void setCurrentSelection(Object root, Object currentSelection);

	/**
	 * Setter for specifying if referencing resource dependencies should be consider or not. 
	 * If set to 'true' then referencing resource dependencies will be highlighted.
	 * @param option boolean option to specify highlight referencing dependencies.
	 */
	public void setReferencingResourcesDependencies(boolean option);

	/**
	 * Setter for specifying if referenced resource dependencies should be consider or not. 
	 * If set to 'true' then referenced resource dependencies will be highlighted.
	 * @param option boolean option to specify highlight referenced dependencies.
	 */
	public void setReferencedResourcesDependencies(boolean option);

	/**
	 *  Set it 'true' if want to filter (remove) unrelated graph nodes (items)
	 *  otherwise false
	 * @param filteredUnrelatedItems {@link Boolean}
	 */
	public void setFilterUnrelatedItems(boolean filteredUnrelatedItems);

	/**
	 *  Set it 'true' if want to show all the elements in workspace
	 *  otherwise false
	 * @param showAllInWorkspace {@link Boolean}
	 */
	public void setShowAllInWorkspace(boolean showAllInWorkspace);

	/**
	 * Handle double click action 
	 * @param selectedElement graph item on which double click is performed.
	 */
	public void handleDoubleClick(Object selectedElement);

	public Object getRootNode();
}
