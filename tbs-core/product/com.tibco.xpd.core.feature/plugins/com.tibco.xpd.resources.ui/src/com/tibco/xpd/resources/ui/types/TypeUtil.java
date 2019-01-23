/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.types;

import java.util.Set;

import com.tibco.xpd.resources.ui.internal.types.TypeUtilInternal;
import com.tibco.xpd.resources.ui.picker.PickerService;

/**
 * This Utility class provides access to the internals needed by implementors of
 * the types provider extension. It is recommended only to use this class for
 * this functionality, because it is linked to the implementation of the
 * <code>PickerDialog</code>.
 * 
 * @author rassisi
 * @depricated Use {@link PickerService} instead.
 */
public final class TypeUtil {

    /**
     * This method returns all types for the given types provider.
     * 
     * @return
     */
    public static Set<TypeInfo> getRegisteredTypes(String providerId) {
        return TypeUtilInternal.getRegisteredTypes(providerId);
    }

    /**
     * This method returns the TypeInfo which is described by a types provider.
     * If the type is not supported by any types provider the method returns
     * null.
     * 
     * @param element
     * @return
     */
    public static TypeInfo getTypeOfElement(Object element) {
        return TypeUtilInternal.getTypeOfElement(element);
    }

    /**
     * Returns the resolved element from the typeditem details
     * 
     * @param typedItem
     * @return
     */
    public static Object getTypedItemResolvedElement(TypedItem typedItem) {
        Object resolved =
                TypeUtilInternal.getResolver(TypeUtilInternal
                        .getTypeOfElement(typedItem)).toObject(typedItem,
                        null,
                        null);
        return resolved;
    }
}
