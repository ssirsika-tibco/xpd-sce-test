/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.picker.filters;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.ui.picker.IPickerItemProvider;
import com.tibco.xpd.resources.ui.picker.PickerContentExtension;
import com.tibco.xpd.resources.ui.picker.PickerItemImpl;

/**
 * Filters out types that cannot be applied to Case Identifiers
 * 
 * @author patkinso
 * @since 4 Oct 2013
 */
public class OnCaseIdentifierFilter implements IFilter {

    private static final String[] DISALLOWED_CID_PRIMITIVE_TYPE_NAMES = {
            "Attachment", //$NON-NLS-1$
            "Boolean", "Date", "Date Time", "Date Time and Time Zone", //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            // Need to also store the names with spaces in without the spaces
            // this is because when used in a Primitive type there are no spaces
            "DateTime", "DateTimeTZ", //$NON-NLS-1$//$NON-NLS-2$
            "Duration", "ID", "Object", "URI", "Time" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$

    private static final String[] DISALLOWED_AUTO_CID_PRIMITIVE_TYPE_NAMES = {
            "Text", "Decimal" }; //$NON-NLS-1$//$NON-NLS-2$

    // The object types that are not supported
    private static final String[] DISALLOWED_CID_TYPES = {
            BOMTypeQuery.CLASS_TYPE, BOMTypeQuery.GLOBAL_CLASS_TYPE,
            BOMTypeQuery.CASE_CLASS_TYPE, BOMTypeQuery.ENUMERATION_TYPE };

    private boolean isAutoCaseId = false;

    /**
     * Constructor that takes if it is looking for valid values for a normal
     * identifier of an auto case identifier
     * 
     * @param isAutoId
     */
    public OnCaseIdentifierFilter(boolean isAutoId) {
        isAutoCaseId = isAutoId;
    }

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

            if (isDisallowedType(item.getType())) {
                return false;
            }
            if (item.getType().equals(BOMTypeQuery.BASE_PRIMITIVE_TYPE)) {
                return !isDisallowedCaseIdTypeName(item.getName());
            } else if (item.getType().equals(BOMTypeQuery.PRIMITIVE_TYPE)) {
                // Work out what the base type of this primitive is and check to
                // ensure that it is an allowed type
                String baseTypeForPrimitive = getBaseTypeForPrimitive(item);
                if (baseTypeForPrimitive != null) {
                    return !isDisallowedCaseIdTypeName(baseTypeForPrimitive);
                }
            }
        }
        return true;
    }

    /**
     * @param typeName
     * @return <code>true</code> if supplied type and name are disallowed
     */
    private boolean isDisallowedCaseIdTypeName(String name) {
        HashSet<String> disallowedNames =
                new HashSet<String>(
                        Arrays.asList(DISALLOWED_CID_PRIMITIVE_TYPE_NAMES));
        // Check for auto case identifiers as well
        if (isAutoCaseId) {
            disallowedNames.addAll(Arrays
                    .asList(DISALLOWED_AUTO_CID_PRIMITIVE_TYPE_NAMES));
        }

        return disallowedNames.contains(name);
    }

    /**
     * Check if this type is not allowed to be used
     * 
     * @param typeName
     * @return <code>true</code> if supplied type is disallowed
     */
    private boolean isDisallowedType(String typeName) {

        for (String disallowedType : DISALLOWED_CID_TYPES) {
            if (disallowedType.compareTo(typeName) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Class to get the name of the Type of the Base Type of a given primitive
     * 
     * @param item
     * @return
     */
    private String getBaseTypeForPrimitive(PickerItemImpl item) {
        String primBaseTypeName = null;

        PickerContentExtension pickerExtension = item.getPickerExtension();
        if (pickerExtension != null) {
            IPickerItemProvider pickerItemProvider =
                    pickerExtension.getPickerItemProvider();
            if (pickerItemProvider != null) {
                Object resolvePickerItem =
                        pickerItemProvider.resolvePickerItem(item);
                if ((resolvePickerItem != null)
                        && (resolvePickerItem instanceof PrimitiveType)) {
                    PrimitiveType primType = (PrimitiveType) resolvePickerItem;

                    // Get the base type in case this is a BOM primitive type
                    // defined by the user, we want to get the type of that for
                    // checking
                    PrimitiveType primBaseType =
                            PrimitivesUtil.getBasePrimitiveType(primType);

                    if (primBaseType != null) {
                        // Get the name of the primitive type
                        primBaseTypeName = primBaseType.getName();
                    }
                }
            }
        }

        return primBaseTypeName;
    }
}
