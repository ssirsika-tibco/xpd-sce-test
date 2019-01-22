/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.commonpicker;

import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;

/**
 * OMSpecific type query.
 * 
 * @author Jan Arciuchiewicz
 */
public class OMTypeQuery extends PickerTypeQuery {

    public static String OM_PICKER_EXTENSION_ID =
            "com.tibco.xpd.om.omPickerContentProvider"; //$NON-NLS-1$

    public static final String TYPE_ID_LOCATION_TYPE =
            "com.tibco.xpd.om.resources.ui.types.LocationType"; //$NON-NLS-1$

    public static final String TYPE_ID_ORGANIZATION_TYPE =
            "com.tibco.xpd.om.resources.ui.types.OrganizationType"; //$NON-NLS-1$

    public static final String TYPE_ID_ORG_UNIT_TYPE =
            "com.tibco.xpd.om.resources.ui.types.OrgUnitType"; //$NON-NLS-1$

    public static final String TYPE_ID_ORG_UNIT_RELATIONSHIP_TYPE =
            "com.tibco.xpd.om.resources.ui.types.OrgUnitRelationshipType"; //$NON-NLS-1$

    public static final String TYPE_ID_POSITION_TYPE =
            "com.tibco.xpd.om.resources.ui.types.PositionType"; //$NON-NLS-1$

    public static final String TYPE_ID_RESOURCE_TYPE =
            "com.tibco.xpd.om.resources.ui.types.ResourceType"; //$NON-NLS-1$

    public static final String TYPE_ID_ABSTRACT_CAPABILITY =
            "com.tibco.xpd.om.resources.ui.types.Capability"; //$NON-NLS-1$

    public static final String TYPE_ID_ATTRIBUTE =
            "com.tibco.xpd.om.resources.ui.types.Attribute"; //$NON-NLS-1$

    public static final String TYPE_ID_ATTRIBUTE_TYPE =
            "com.tibco.xpd.om.resources.ui.types.AttributeType"; //$NON-NLS-1$

    public static final String TYPE_ID_ATTRIBUTE_VALUE =
            "com.tibco.xpd.om.resources.ui.types.AttributeValue"; //$NON-NLS-1$

    public static final String TYPE_ID_AUTHORIZABLE =
            "com.tibco.xpd.om.resources.ui.types.Authorizable"; //$NON-NLS-1$

    public static final String TYPE_ID_BASE_ORG_MODEL =
            "com.tibco.xpd.om.resources.ui.types.BaseOrgModel"; //$NON-NLS-1$

    public static final String TYPE_ID_ORG_MODEL =
            "com.tibco.xpd.om.resources.ui.types.OrgModel"; //$NON-NLS-1$

    public static final String TYPE_ID_ORG_META_MODEL =
            "com.tibco.xpd.om.resources.ui.types.OrgMetaModel"; //$NON-NLS-1$

    public static final String TYPE_ID_CAPABILITY =
            "com.tibco.xpd.om.resources.ui.types.Capability"; //$NON-NLS-1$

    public static final String TYPE_ID_CAPABILITY_CATEGORY =
            "com.tibco.xpd.om.resources.ui.types.CapabilityCategory"; //$NON-NLS-1$

    public static final String TYPE_ID_PRIVILEGE_CATEGORY =
            "com.tibco.xpd.om.resources.ui.types.PrivilegeCategory"; //$NON-NLS-1$

    public static final String TYPE_ID_ENUM_VALUE =
            "com.tibco.xpd.om.resources.ui.types.EnumValue"; //$NON-NLS-1$

    public static final String TYPE_ID_FEATURE =
            "com.tibco.xpd.om.resources.ui.types.Feature"; //$NON-NLS-1$

    public static final String TYPE_ID_GROUP =
            "com.tibco.xpd.om.resources.ui.types.Group"; //$NON-NLS-1$

    public static final String TYPE_ID_LOCATION =
            "com.tibco.xpd.om.resources.ui.types.Location"; //$NON-NLS-1$

    public static final String TYPE_ID_MULTIPLE_FEATURE =
            "com.tibco.xpd.om.resources.ui.types.Feature"; //$NON-NLS-1$

    public static final String TYPE_ID_ORGANIZATION =
            "com.tibco.xpd.om.resources.ui.types.Organization"; //$NON-NLS-1$

    /*
     * XPD-5300 New Dynamic elements.
     */
    public static final String TYPE_ID_DYNAMIC_ORGANIZATION =
            "com.tibco.xpd.om.resources.ui.types.DynamicOrganization"; //$NON-NLS-1$

    public static final String TYPE_ID_DYNAMIC_ORGANIZATION_TYPE =
            "com.tibco.xpd.om.resources.ui.types.DynamicOrganizationType"; //$NON-NLS-1$

    public static final String TYPE_ID_DYNAMIC_ORG_UNIT =
            "com.tibco.xpd.om.resources.ui.types.DynamicOrgUnit"; //$NON-NLS-1$

    public static final String TYPE_ID_ORG_UNIT =
            "com.tibco.xpd.om.resources.ui.types.OrgUnit"; //$NON-NLS-1$

    public static final String TYPE_ID_ORG_UNIT_FEATURE =
            "com.tibco.xpd.om.resources.ui.types.OrgUnitFeature"; //$NON-NLS-1$

    public static final String TYPE_ID_ORG_UNIT_RELATIONSHIP =
            "com.tibco.xpd.om.resources.ui.types.OrgUnitRelationship"; //$NON-NLS-1$

    public static final String TYPE_ID_POSITION =
            "com.tibco.xpd.om.resources.ui.types.Position"; //$NON-NLS-1$

    public static final String TYPE_ID_POSITION_FEATURE =
            "com.tibco.xpd.om.resources.ui.types.PositionFeature"; //$NON-NLS-1$

    public static final String TYPE_ID_PRIVILEGE =
            "com.tibco.xpd.om.resources.ui.types.Privilege"; //$NON-NLS-1$

    public static final String TYPE_ID_RESOURCE =
            "com.tibco.xpd.om.resources.ui.types.Resource"; //$NON-NLS-1$

    public static final String TYPE_ID_ORG_QUERY =
            "com.tibco.xpd.om.resources.ui.types.orgQueryType"; //$NON-NLS-1$

    /**
     */
    public OMTypeQuery(String... types) {
        super(OM_PICKER_EXTENSION_ID, types);
    }

    /**
     */
    public OMTypeQuery(Object context, String... types) {
        super(PickerService.getInstance()
                .getPickerContentExtension(OM_PICKER_EXTENSION_ID), context,
                types);
    }
}
