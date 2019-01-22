/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.commonpicker;

import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;

/**
 * Helper class to create BOM Picker type queries. This class also provide
 * constants for all BOM picker item types.
 * 
 * @author Jan Arciuchiewicz
 */
public class BOMTypeQuery extends PickerTypeQuery {
    public static String BOM_PICKER_EXTENSION_ID =
            "com.tibco.xpd.bom.bomPickerContentProvider"; //$NON-NLS-1$

    public static String PACKAGE_TYPE = ResourceItemType.PACKAGE.toString();

    public static String CLASS_TYPE = ResourceItemType.CLASS.toString();

    public static String CASE_CLASS_TYPE = ResourceItemType.CASE_CLASS
            .toString();

    public static String GLOBAL_CLASS_TYPE = ResourceItemType.GLOBAL_CLASS
            .toString();

    public static String PRIMITIVE_TYPE = ResourceItemType.PRIMITIVE_TYPE
            .toString();

    public static String ENUMERATION_TYPE = ResourceItemType.ENUMERATION
            .toString();

    public static String ASSOC_CLASS_TYPE = ResourceItemType.ASSOC_CLASS
            .toString();

    public static String BASE_PRIMITIVE_TYPE = "BASE_PRIMITIVE"; //$NON-NLS-1$

    public static String PROFILE_TYPE = "PROFILE"; //$NON-NLS-1$

    public static String STEREOTYPE_TYPE = "STEREOTYPE"; //$NON-NLS-1$

    public static String MODEL_TYPE = "STEREOTYPE"; //$NON-NLS-1$

    public static String PROPERTY_TYPE = "PROPERTY"; //$NON-NLS-1$

    public static String ASSOCIATION_TYPE = "ASSOCIATION"; //$NON-NLS-1$

    public static String GENERALIZATION_TYPE = "GENERALIZATION"; //$NON-NLS-1$

    public static String ENUMERATION_LITERAL_TYPE = "ENUMERATION_LITERAL"; //$NON-NLS-1$

    /**
     * Creates BOM type query.
     */
    public BOMTypeQuery(String... types) {
        super(BOM_PICKER_EXTENSION_ID, types);
    }

    /**
     * Creates BOM type query with a context object passed. It's up to
     * {@link BOMPickerItemProvider} to make use of the passed context object.
     */
    public BOMTypeQuery(Object context, String... types) {
        super(PickerService.getInstance()
                .getPickerContentExtension(BOM_PICKER_EXTENSION_ID), context,
                types);
    }
}
