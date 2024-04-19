/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.properties.general;

import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownEMFAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpEMFAction;


/**
 * Class for Psl Function Return Type Table.
 *
 *
 * @author cbabar
 * @since Feb 15, 2024
 */
public class PslFunctionReturnTypeTable extends DataFieldTable
{
	private final EditingDomain	editingDomain;

	protected IContentProvider	contentProvider;


	public PslFunctionReturnTypeTable(Composite parent, XpdToolkit toolkit, EditingDomain editingDomain,
			int options)
	{
		super(parent, toolkit, editingDomain, options);
		this.editingDomain = editingDomain;

		ViewerFilter filter = new PslFunctionDataFieldFilter(true);
		ViewerFilter[] filters = new ViewerFilter[]{filter};
		getViewer().setFilters(filters);
	}


	public PslFunctionReturnTypeTable(Composite parent, XpdToolkit toolkit, EditingDomain editingDomain)
	{
		this(parent, toolkit, editingDomain, DataFieldTable.HIDE_LABEL_COLUMN | DataFieldTable.DISABLE_NAME_COLUMN
				| DataFieldTable.HIDE_READONLY_COLUMN | DataFieldTable.ADD_VOID_TYPE
				| DataFieldTable.HIDE_TYPE_DECLARATION_COLUMN | DataFieldTable.ADD_DESCRIPTION_COLUMN);
	}





	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
	 *
	 * @param viewer
	 * @return
	 */
	@Override
	protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer)
	{
		return null;
	}

	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
	 *
	 * @param viewer
	 * @return
	 */
	@Override
	protected ViewerAddAction createAddAction(ColumnViewer viewer)
	{
		return null;
	}


	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#getMovableFeatures()
	 *
	 * @return
	 */
	@Override
	protected Set<EStructuralFeature> getMovableFeatures()
	{
		Set<EStructuralFeature> movableFeatures = null;
		return movableFeatures;
	}

	/**
	 * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
	 *
	 * @param viewer
	 * @return
	 */
	@Override
	protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer)
	{
		ViewerMoveDownEMFAction movedown = null;
		return movedown;
	}

	/**
	 * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
	 *
	 * @param viewer
	 * @return
	 */
	@Override
	protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer)
	{
		ViewerMoveUpEMFAction moveup = null;
		return moveup;
	}

}
