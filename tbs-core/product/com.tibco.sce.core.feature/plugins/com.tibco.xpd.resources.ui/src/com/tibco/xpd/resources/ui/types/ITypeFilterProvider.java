/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.types;

import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.resources.ui.picker.IPickerItemProvider;

/**
 * This interface must be implemented by the types provider, when the element
 * filter is specified in the typeProviders extension. It's purpose is to query
 * content, which is offered to users for selection by the
 * <code>PickerDialog</code> by providing different criteria.
 * 
 * @author rassisi
 * @deprecated Use {@link IPickerItemProvider} and instead.
 */
@Deprecated
public interface ITypeFilterProvider {

    /**
     * Get the content as a Set of TypedItem to be displayed by the
     * PickerDialog.
     * 
     * @param monitor
     *            progress monitor.
     * @param queryResources
     *            get content only for the given resources.
     * @param queryTypes
     *            types of data required.
     * @param excludeContent
     *            exclude this array of instances from the selection list in the
     *            PickerDialog.
     * 
     * @return Set of <code>TypedItem</code> objects.
     */
    Set<TypedItem> getContent(IProgressMonitor monitor,
            IResource[] queryResources, TypeInfo queryTypes,
            Object[] excludeContent);

    /**
     * If more than one provider exists for a certain type the search can be
     * narrowed by setting the provider id.
     * 
     * @param providerId
     */
    public void setProviderId(String providerId);

    /**
     * This method specifies if this filter provider supports the given item.
     * 
     * @param item
     * @return
     */
    public boolean matchItem(Object item);

}
