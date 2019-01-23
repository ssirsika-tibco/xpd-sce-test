/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Provides content for a picker (as a collection of {@link PickerItem}) and
 * also resolves provided picker's items. The implementations of this interface
 * are referenced from <code>com.tibco.xpd.resources.ui.pickerContent</code>
 * extension.
 * 
 * @see CommonPickerDialog
 * @see IPickerLabelProvider
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public interface IPickerItemProvider {

    /**
     * Provides content for the picker (as a collection of {@link PickerItem}).
     * PickerTypeQuery determine the subset of types of items. These types are
     * strings specific to the picker content extension (see:
     * <code>com.tibco.xpd.resources.ui.pickerContent</code> extension).
     * 
     * @param typeQuery
     *            specify the subset of types specific to the picker content
     *            extension. Only items of the types specified in query should
     *            be returned.
     * @param monitor
     *            progress monitor.
     * @return collection of {@link PickerItem} items to be aggregated in
     *         {@link CommonPickerDialog}.
     */
    Collection<PickerItem> getContent(PickerTypeQuery typeQuery,
            IProgressMonitor monitor);

    /**
     * Resolves picker item to the object it represents.
     * 
     * @param item
     *            item to be resolved.
     * @return the object representation of an item.
     */
    Object resolvePickerItem(PickerItem item);
}