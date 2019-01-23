/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

/**
 * <p>
 * <i>Created: Nov 10, 2006</i>.
 * 
 * @author mmaciuki
 */
class CustomModifier implements ICellModifier {

    private final ICellModifier modifier;

    private final InternalTableActionsListener defaultsProvider;

    private final TableViewer tableViewer;

    /**
     * Constructor.
     * 
     * @param modifier
     *            param
     * @param defaultsProvider
     *            param
     * @param tableViewer
     *            param
     */
    public CustomModifier(ICellModifier modifier,
            InternalTableActionsListener defaultsProvider, TableViewer tableViewer) {
        this.defaultsProvider = defaultsProvider;
        this.tableViewer = tableViewer;
        if (modifier == null) {
            throw new NullPointerException("modifier cannot be null!"); //$NON-NLS-1$
        }
        this.modifier = modifier;
    }

    /**
     * Check if the given property of the element can be modified.
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
     *      java.lang.String)
     * @param element
     *            param
     * @param property
     *            param
     * @return boolean
     */
    public boolean canModify(Object element, String property) {
        boolean result;
        if (element != PotentialNewRowData.INSTANCE) {
            result = this.modifier.canModify(element, property);
        } else {
        	Object[] columnProperties = this.tableViewer.getColumnProperties();
        	int columnIndex=0;
        	for (int i = 0; i < columnProperties.length; i++) {
        		String columnName=String.valueOf(columnProperties[i]);
        		if(columnName.equals(property)){
        			columnIndex=i;
        		}				
			}
			// can edit only first column.
        	if(columnIndex==0){
	            result = true;
        	} else {
        		result = false;
        	}
        }
        return result;
    }

    /**
     * Get the value from the element of the given property.
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
     *      java.lang.String)
     * @param element
     *            param
     * @param property
     *            param
     * @return Object
     */
    public Object getValue(Object element, String property) {
        Object result;
        if (element != PotentialNewRowData.INSTANCE) {
            result = this.modifier.getValue(element, property);
        } else {
            // even if default id is null it is guaranteed to return string
            result = String.valueOf(this.defaultsProvider.createDefaultId());
        }
        return result;
    }

    /**
     * Update the property of the given element with the new value.
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
     *      java.lang.String, java.lang.Object)
     * @param element
     *            param
     * @param property
     *            param
     * @param value
     *            param
     */
    public void modify(Object element, String property, Object value) {
        TableItem tableItem = (TableItem) element;
        Object data = tableItem.getData();
        if (data != PotentialNewRowData.INSTANCE) {
            this.modifier.modify(element, property, value);
        } else {
            String valueOf = String.valueOf(value);
            // empty string is not allowed
            if(valueOf.trim().length()!=0){
	            // trigger new data creation
	            this.defaultsProvider.createWithDefaults(valueOf);
            } else {
            	String defaultName=this.defaultsProvider.createDefaultId();
            	this.defaultsProvider.createWithDefaults(defaultName);
            }
            this.tableViewer.refresh();
        }
    }

}
