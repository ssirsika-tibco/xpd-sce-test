/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.providers;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.IDataPickerProxyItem;

/**
 * This interface will be implemented by the provider of the content of the Data
 * picker.
 * 
 * @author Miguel Torres
 * 
 */
public interface IDataPickerProvider {

    /**
     * Type of items required for the picker.
     * 
     * @author Miguel Torres
     * 
     */
    enum DataType {
        DATAFIELD, FORMALPARAMETER, DATAFIELD_FORMALPARAMETER, PARTICIPANTS;
    }

    /**
     * Get the content of the given type for the Data picker to display.
     * 
     * @param monitor
     *            progress monitor.
     * @param type
     *            type of data required.
     * @param scope
     *            the scope of the data.
     * @return array of <code>IDataPickerProxyItem</code> objects to display in
     *         the picker.
     */
    IDataPickerProxyItem[] getContent(DataType type, EObject scope);

    /**
     * Get the item with the given <code>URI</code>.
     * 
     * @param uri
     *            item uri
     * @param name
     *            fully qualified name
     * @param scope
     *            the scope of the data.
     * @return <code>IDataPickerProxyItem</code> for the given <code>URI</code>.
     */
    IDataPickerProxyItem getItem(URI uri, String name, EObject scope);
}
