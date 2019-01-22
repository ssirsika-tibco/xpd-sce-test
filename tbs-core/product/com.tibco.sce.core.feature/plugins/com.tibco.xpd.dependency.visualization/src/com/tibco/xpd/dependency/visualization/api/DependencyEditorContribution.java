/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.api;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.dependency.visualization.internal.views.DependencyVisualizationEditor;

/**
 * This abstract class defines the contract for extender, who are providing
 * information (contents) to Dependency Editor for showing the contents.
 * 
 * @author Ali, ssirsika
 * @since 17 Nov 2014
 */
/**
 *
 *
 * @author ssirsika
 * @since 26-Nov-2015
 */
public abstract class DependencyEditorContribution {

	/**
	 * Return the array of related resources of passed inputElement. 
	 * @param inputElement element whose related resources need to be returned. 
	 * @param filterUnrelatedItems specifies whether to filter out unrelated resources or not. If value is '<code>true</code>' then 
	 * filter unrelated resources otherwise do not filter.
	 * @param showAllInWorkspace specify if everything in workspace should be shown or not.
	 * <code>true</code> if everything of passed inputElement type form the workspace need to shown. 
	 * @return Array of elements which are in relationship with the passed inputElement, 
	 * if not relationship found then return empty array or null.
	 * 
	 */
	public abstract Object[] getElements(Object inputElement, boolean filterUnrelatedItems, boolean showAllInWorkspace);

	/**
	 * Return Array of dependencies with passed obj.
	 * @param obj 
	 * @return Array of dependencies on passed obj
	 * If not dependencies found then return empty array or null.
	 */
	public abstract Object[] getDependencies(Object obj);

	/**
	 * This is not being used right now. Reserved for future use.
	 * @param obj
	 * @return
	 */
	public abstract Object[] getDependents(Object obj);

	/**
	 * Return the text representation of passed 'obj'. 
	 * @param obj
	 * @return text or name in {@link String} type
	 * Null in case, passed 'obj' does not have string representation. 
	 */
	public abstract String getText(Object obj);

	/**
	 *  Return the image object for passed 'obj'.
	 * @param obj
	 * @return {@link Image} or null if no image
	 */
	public abstract Image getImage(Object obj);

	/**
	 * This method will receive callback when user triggers particular event on passed 'selectedElement' on Dependency Editor( 
	 * {@link DependencyVisualizationEditor}).
	 * Implementor need to provide functionality what should happen on particular passed event type 'type'. For example, client may want to
	 * select particular double clicked element in the 'Project Explorer' view.  
	 * @type type defines event that need to be transfered (for example double click on graph node) 
	 * @param selectedElement element on which user double clicked.
	 */
	public abstract void handleElementEvent(EventTypeEnum type, Object selectedElement);

	/**
	 * Dispose call to clear out all the allocated resources such as images, file handles, etc 
	 */
	public abstract void dispose();

}
