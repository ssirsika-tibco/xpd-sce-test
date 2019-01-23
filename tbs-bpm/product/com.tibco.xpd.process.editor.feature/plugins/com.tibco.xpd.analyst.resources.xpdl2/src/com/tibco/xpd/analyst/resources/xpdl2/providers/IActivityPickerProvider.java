/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.providers;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.IActivityPickerProxyItem;

/**
 * This interface will be implemented by the provider of the content of the Activity
 * picker.
 * 
 * @author Miguel Torres
 * 
 */
public interface IActivityPickerProvider {

    /**
     * Type of items required for the picker.
     * 
     * @author Miguel Torres
     * 
     */
    enum ActivityType {
        ACTIVITY;
    }

    /**
     * Get the content of the given type for the Activity picker to display.
     * 
     * @param monitor
     *            progress monitor.
     * @param type
     *            type of data required.
     * @param scope
     *            the scope of the data.
     * @return array of <code>IActivityPickerProxyItem</code> objects to display in
     *         the picker.
     */
    IActivityPickerProxyItem[] getContent(ActivityType type, EObject scope);

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
    IActivityPickerProxyItem getItem(URI uri, String name, EObject scope);
}
