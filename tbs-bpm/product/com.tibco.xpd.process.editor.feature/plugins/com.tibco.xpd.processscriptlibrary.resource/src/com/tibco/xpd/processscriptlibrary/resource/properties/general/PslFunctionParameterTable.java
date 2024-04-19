/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.properties.general;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.resources.ui.components.XpdToolkit;

/**
 *
 * Class for Psl Function Parameter Field Table.
 *
 * @author cbabar
 * @since Feb 6, 2024
 */
public class PslFunctionParameterTable extends DataFieldTable
{
	private final EditingDomain	editingDomain;

	protected IContentProvider	contentProvider;


	public PslFunctionParameterTable(Composite parent, XpdToolkit toolkit, EditingDomain editingDomain,
			int options)
	{
		super(parent, toolkit, editingDomain, options);
		this.editingDomain = editingDomain;

		ViewerFilter filter = new PslFunctionDataFieldFilter(false);
		ViewerFilter[] filters = new ViewerFilter[]{filter};
		getViewer().setFilters(filters);
	}


	public PslFunctionParameterTable(Composite parent, XpdToolkit toolkit, EditingDomain editingDomain)
	{
		this(parent, toolkit, editingDomain, DataFieldTable.HIDE_LABEL_COLUMN | DataFieldTable.HIDE_READONLY_COLUMN
				| DataFieldTable.HIDE_TYPE_DECLARATION_COLUMN | DataFieldTable.ADD_DESCRIPTION_COLUMN);
	}



	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#getAddTooltip()
	 *
	 * @return
	 */
	@Override
	protected String getAddTooltip()
	{
		return Messages.PSLSection_AddParameterButton_tooltip;
	}

	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#getAddLabel()
	 *
	 * @return
	 */
	@Override
	protected String getAddLabel()
	{
		return Messages.PSLPropertiesSection_AddLabel;
	}

	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#getAddFieldCommandLabel()
	 *
	 * @return
	 */
	@Override
	protected String getAddFieldCommandLabel()
	{
		return Messages.PSLSection_createParameter_menu;
	}

	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#getAddFieldNamePrefix()
	 *
	 * @return
	 */
	@Override
	protected String getAddFieldNamePrefix()
	{
		return Messages.PSLSection_ParameterName_value;
	}

	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#getDeleteToolTip()
	 *
	 * @return
	 */
	@Override
	protected String getDeleteToolTip()
	{
		return Messages.PSLSection_DeleteParameterButton_tooltip;
	}

	/**
	 * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#getDeleteLabel()
	 *
	 * @return
	 */
	@Override
	protected String getDeleteLabel()
	{
		return Messages.PSLPropertiesSection_DeleteLabel;
	}

	/**
	 * Get the input of this table.
	 * 
	 * @return
	 */
	private EObject getInput()
	{
		return (EObject) (getViewer() != null ? getViewer().getInput() : null);
	}


}
