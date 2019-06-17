/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.picker.filters;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.ui.picker.PickerItemImpl;

/**
 * Filters out Base Primitive Types that cannot be applied to BOM
 * attributes/Primitive Type Definitions.
 * 
 * @author sajain
 * @since 12 June 2019
 */
public class BOMBasePrimitiveTypesFilter implements IFilter {

    /**
     * The base primitive types that aren't supported in ACE.
     */
    private static final String[] UNSUPPORTED_BASE_PRIMITIVE_TYPE_NAMES = {
            PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
            PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME };

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        if (toTest instanceof PickerItemImpl) {
            PickerItemImpl item = (PickerItemImpl) toTest;
            if (item.getType().equals(BOMTypeQuery.BASE_PRIMITIVE_TYPE)) {
                return !isUnsupportedBasePrimitiveType(item.getName());
            }
        }
        return true;
    }

    /**
     * Return <code>true</code> if supplied type and names are not supported,
     * <code>false</code> otherwise.
     * 
     * @param typeName
     * @return <code>true</code> if supplied type and names are not supported,
     *         <code>false</code> otherwise.
     */
    private boolean isUnsupportedBasePrimitiveType(String name) {
        HashSet<String> unsupportedBasePrimitiveType =
                new HashSet<String>(
                        Arrays.asList(UNSUPPORTED_BASE_PRIMITIVE_TYPE_NAMES));

        return unsupportedBasePrimitiveType.contains(name)
                || unsupportedBasePrimitiveType
                        .contains(name.replaceAll("\\s+", "")); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
