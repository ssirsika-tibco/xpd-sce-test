/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.resources.ui.types;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.ui.picker.IPickerItemProvider;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;

/**
 * This interface must be implemented by the types provider, when the element
 * 'resolvers' is specified in the typeProviders extension. It's purpose is to
 * retrieve the related instance of the proxy-object TypedItem. It is used by
 * the Picker Dialog to return the real objects of the selection made by the
 * user.
 * 
 * @author rassisi
 * @deprecated Use {@link IPickerItemProvider} instead.
 */
@Deprecated
public interface ITypeResolver {

    /**
     * Return true if the given object type (for instance, selected by user from
     * picker) is valid for this type.
     * 
     * @param dataType
     * 
     * @return true if the object is a type handled by this resolver.
     */
    boolean isValidDataType(Object dataType);

    /**
     * This method scans all types for which this resolver is responsible and
     * matches it with the given object. It is internally needed by the
     * PickerDialog.
     * 
     * @param element
     * 
     * @return Returns a <code>TypeInfo</code>, which is describes types defined
     *         by the types provider extension.
     */
    public TypeInfo toType(Object element);

    /**
     * 
     * @param item
     *            The proxy item which holds information about a content item.
     * @param queryResources
     *            Only objects inside the given array of resources will be
     *            considered. If null this parameter has no effect.
     * @param contentToExclude
     *            If this parameter is not null the given array of objects will
     *            be filtered out from the result.
     * 
     * @return The object referenced by the given
     *         {@link ComplexDataTypeReference}
     */
    public Object toObject(TypedItem item, IResource[] queryResources,
            Object[] contentToExclude);

    /**
     * Get the descriptive name for this particular instance of data type.
     * 
     * @param type
     * @return
     */
    public String getTypeName(Object type);

    /**
     * The id of the types provider contributor.
     * 
     * @param providerId
     */
    public void setProviderId(String providerId);

}
