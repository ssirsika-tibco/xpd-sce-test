/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.types;

import java.util.Map;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.resources.ui.internal.indexing.OMResourceIndexProvider;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.ui.types.TypeInfo;
import com.tibco.xpd.resources.ui.types.TypedItem;

/**
 * This class provides all TypeInfo's that are defined by the types provider. It
 * contains also all mappings from other classes like the IndexerItem of the
 * resource db.
 * 
 * @author rassisi
 */
public class OMTypesFactory {

    // ----------- These fields should not be used in the application

    final static private String TYPE_ID_LOCATION_TYPE =
            "com.tibco.xpd.om.resources.ui.types.LocationType"; //$NON-NLS-1$

    final static private String TYPE_ID_ORGANIZATION_TYPE =
            "com.tibco.xpd.om.resources.ui.types.OrganizationType"; //$NON-NLS-1$

    final static private String TYPE_ID_ORG_UNIT_TYPE =
            "com.tibco.xpd.om.resources.ui.types.OrgUnitType"; //$NON-NLS-1$

    final static private String TYPE_ID_ORG_UNIT_RELATIONSHIP_TYPE =
            "com.tibco.xpd.om.resources.ui.types.OrgUnitRelationshipType"; //$NON-NLS-1$

    final static private String TYPE_ID_POSITION_TYPE =
            "com.tibco.xpd.om.resources.ui.types.PositionType"; //$NON-NLS-1$

    private static final String TYPE_ID_RESOURCE_TYPE =
            "com.tibco.xpd.om.resources.ui.types.ResourceType"; //$NON-NLS-1$

    final static private String TYPE_ID_ABSTRACT_CAPABILITY =
            "com.tibco.xpd.om.resources.ui.types.Capability"; //$NON-NLS-1$

    final static private String TYPE_ID_ATTRIBUTE =
            "com.tibco.xpd.om.resources.ui.types.Attribute"; //$NON-NLS-1$

    final static private String TYPE_ID_ATTRIBUTE_TYPE =
            "com.tibco.xpd.om.resources.ui.types.AttributeType"; //$NON-NLS-1$

    final static private String TYPE_ID_ATTRIBUTE_VALUE =
            "com.tibco.xpd.om.resources.ui.types.AttributeValue"; //$NON-NLS-1$

    final static private String TYPE_ID_AUTHORIZABLE =
            "com.tibco.xpd.om.resources.ui.types.Authorizable"; //$NON-NLS-1$

    final static private String TYPE_ID_BASE_ORG_MODEL =
            "com.tibco.xpd.om.resources.ui.types.BaseOrgModel"; //$NON-NLS-1$

    final static private String TYPE_ID_ORG_MODEL =
            "com.tibco.xpd.om.resources.ui.types.OrgModel"; //$NON-NLS-1$

    final static private String TYPE_ID_ORG_META_MODEL =
            "com.tibco.xpd.om.resources.ui.types.OrgMetaModel"; //$NON-NLS-1$

    final static private String TYPE_ID_CAPABILITY =
            "com.tibco.xpd.om.resources.ui.types.Capability"; //$NON-NLS-1$

    final static private String TYPE_ID_CAPABILITY_CATEGORY =
            "com.tibco.xpd.om.resources.ui.types.CapabilityCategory"; //$NON-NLS-1$

    final static private String TYPE_ID_PRIVILEGE_CATEGORY =
            "com.tibco.xpd.om.resources.ui.types.PrivilegeCategory"; //$NON-NLS-1$

    final static private String TYPE_ID_ENUM_VALUE =
            "com.tibco.xpd.om.resources.ui.types.EnumValue"; //$NON-NLS-1$

    final static private String TYPE_ID_FEATURE =
            "com.tibco.xpd.om.resources.ui.types.Feature"; //$NON-NLS-1$

    final static private String TYPE_ID_GROUP =
            "com.tibco.xpd.om.resources.ui.types.Group"; //$NON-NLS-1$

    final static private String TYPE_ID_LOCATION =
            "com.tibco.xpd.om.resources.ui.types.Location"; //$NON-NLS-1$

    final static private String TYPE_ID_MULTIPLE_FEATURE =
            "com.tibco.xpd.om.resources.ui.types.Feature"; //$NON-NLS-1$

    final static private String TYPE_ID_ORGANIZATION =
            "com.tibco.xpd.om.resources.ui.types.Organization"; //$NON-NLS-1$

    final static private String TYPE_ID_DYNAMIC_ORGANIZATION =
            "com.tibco.xpd.om.resources.ui.types.DynamicOrganization"; //$NON-NLS-1$

    final static private String TYPE_ID_ORG_UNIT =
            "com.tibco.xpd.om.resources.ui.types.OrgUnit"; //$NON-NLS-1$

    final static private String TYPE_ID_DYNAMIC_ORG_UNIT =
            "com.tibco.xpd.om.resources.ui.types.DynamicOrgUnit"; //$NON-NLS-1$

    final static private String TYPE_ID_ORG_UNIT_FEATURE =
            "com.tibco.xpd.om.resources.ui.types.OrgUnitFeature"; //$NON-NLS-1$

    final static private String TYPE_ID_ORG_UNIT_RELATIONSHIP =
            "com.tibco.xpd.om.resources.ui.types.OrgUnitRelationship"; //$NON-NLS-1$

    final static private String TYPE_ID_POSITION =
            "com.tibco.xpd.om.resources.ui.types.Position"; //$NON-NLS-1$

    final static private String TYPE_ID_POSITION_FEATURE =
            "com.tibco.xpd.om.resources.ui.types.PositionFeature"; //$NON-NLS-1$

    final static public String TYPE_ID_PRIVILEGE =
            "com.tibco.xpd.om.resources.ui.types.Privilege"; //$NON-NLS-1$

    private static final String TYPE_ID_RESOURCE =
            "com.tibco.xpd.om.resources.ui.types.Resource"; //$NON-NLS-1$

    private static final String TYPE_ID_ORG_QUERY =
            "com.tibco.xpd.om.resources.ui.types.orgQueryType"; //$NON-NLS-1$

    // ---------- Use only these fields in your application --------------------

    /**
     * group id for element types
     */
    final static public String GROUP_ID_ORG_ELEMENT_TYPE =
            "com.tibco.xpd.om.resources.ui.types.OrgElementType"; //$NON-NLS-1$

    final static public TypeInfo TYPE_LOCATION_TYPE = new TypeInfo(
            TYPE_ID_LOCATION_TYPE, GROUP_ID_ORG_ELEMENT_TYPE);

    final static public TypeInfo TYPE_ORGANIZATION_TYPE = new TypeInfo(
            TYPE_ID_ORGANIZATION_TYPE, GROUP_ID_ORG_ELEMENT_TYPE);

    final static public TypeInfo TYPE_ORG_UNIT_RELATIONSHIP_TYPE =
            new TypeInfo(TYPE_ID_ORG_UNIT_RELATIONSHIP_TYPE,
                    GROUP_ID_ORG_ELEMENT_TYPE);

    final static public TypeInfo TYPE_ORG_UNIT_TYPE = new TypeInfo(
            TYPE_ID_ORG_UNIT_TYPE, GROUP_ID_ORG_ELEMENT_TYPE);

    final static public TypeInfo TYPE_POSITION_TYPE = new TypeInfo(
            TYPE_ID_POSITION_TYPE, GROUP_ID_ORG_ELEMENT_TYPE);

    final static public TypeInfo TYPE_RESOURCE_TYPE = new TypeInfo(
            TYPE_ID_RESOURCE_TYPE, GROUP_ID_ORG_ELEMENT_TYPE);

    /**
     * group id for typed elements
     */
    final static public String GROUP_ID_TYPED_ELEMENTS =
            "com.tibco.xpd.om.resources.ui.types.TypedElements"; //$NON-NLS-1$

    final static public TypeInfo TYPE_LOCATION = new TypeInfo(TYPE_ID_LOCATION,
            GROUP_ID_TYPED_ELEMENTS);

    final static public TypeInfo TYPE_ORG_UNIT = new TypeInfo(TYPE_ID_ORG_UNIT,
            GROUP_ID_TYPED_ELEMENTS);

    final static public TypeInfo TYPE_DYNAMIC_ORG_UNIT = new TypeInfo(
            TYPE_ID_DYNAMIC_ORG_UNIT, GROUP_ID_TYPED_ELEMENTS);

    final static public TypeInfo TYPE_ORG_UNIT_RELATIONSHIP = new TypeInfo(
            TYPE_ID_ORG_UNIT_RELATIONSHIP, GROUP_ID_TYPED_ELEMENTS);

    final static public TypeInfo TYPE_POSITION = new TypeInfo(TYPE_ID_POSITION,
            GROUP_ID_TYPED_ELEMENTS);

    final static public TypeInfo TYPE_ORGANIZATION = new TypeInfo(
            TYPE_ID_ORGANIZATION, GROUP_ID_TYPED_ELEMENTS);

    final static public TypeInfo TYPE_DYNAMIC_ORGANIZATION = new TypeInfo(
            TYPE_ID_DYNAMIC_ORGANIZATION, GROUP_ID_TYPED_ELEMENTS);

    final static public TypeInfo TYPE_RESOURCE = new TypeInfo(TYPE_ID_RESOURCE,
            GROUP_ID_TYPED_ELEMENTS);

    /**
     * group id for typed features
     */
    final static public String GROUP_ID_FEATURES =
            "com.tibco.xpd.om.resources.ui.types.Features"; //$NON-NLS-1$

    final static public TypeInfo TYPE_ATTRIBUTE = new TypeInfo(
            TYPE_ID_ATTRIBUTE, GROUP_ID_FEATURES);

    final static public TypeInfo TYPE_ATTRIBUTE_TYPE = new TypeInfo(
            TYPE_ID_ATTRIBUTE_TYPE, GROUP_ID_FEATURES);

    final static public TypeInfo TYPE_ORG_UNIT_FEATURE = new TypeInfo(
            TYPE_ID_ORG_UNIT_FEATURE, GROUP_ID_FEATURES);

    final static public TypeInfo TYPE_POSITION_FEATURE = new TypeInfo(
            TYPE_ID_POSITION_FEATURE, GROUP_ID_FEATURES);

    final static public TypeInfo TYPE_ATTRIBUTE_VALUE = new TypeInfo(
            TYPE_ID_ATTRIBUTE_VALUE, GROUP_ID_TYPED_ELEMENTS);

    final static public TypeInfo TYPE_ENUM_VALUE = new TypeInfo(
            TYPE_ID_ENUM_VALUE, GROUP_ID_TYPED_ELEMENTS);

    // ---------- BASE ORG MODEL -----------------------------------------------

    final static public String GROUP_ID_BASE_ORG_MODEL =
            "com.tibco.xpd.om.resources.ui.types.BaseOrgModel"; //$NON-NLS-1$

    final static public TypeInfo TYPE_ORG_MODEL = new TypeInfo(
            TYPE_ID_ORG_MODEL, GROUP_ID_BASE_ORG_MODEL);

    final static public TypeInfo TYPE_ORG_META_MODEL = new TypeInfo(
            TYPE_ID_ORG_META_MODEL, GROUP_ID_BASE_ORG_MODEL);

    // ---------- ORG ELEMENTS -------------------------------------------------

    final static public String GROUP_ID_ORG_ELEMENTS =
            "com.tibco.xpd.om.resources.ui.types.OrgElements"; //$NON-NLS-1$

    final static public TypeInfo TYPE_PRIVILEGE = new TypeInfo(
            TYPE_ID_PRIVILEGE, GROUP_ID_ORG_ELEMENTS);

    final static public TypeInfo TYPE_CAPABILITY = new TypeInfo(
            TYPE_ID_CAPABILITY, GROUP_ID_ORG_ELEMENTS);

    final static public TypeInfo TYPE_GROUP = new TypeInfo(TYPE_ID_GROUP,
            GROUP_ID_ORG_ELEMENTS);

    // ---------- NAMESPACE ELEMENTS -------------------------------------------

    final static public String GROUP_ID_NAMESPACE_ELEMENTS =
            "com.tibco.xpd.om.resources.ui.types.NamespaceElements"; //$NON-NLS-1$

    final static public TypeInfo TYPE_CAPABILITY_CATEGORY = new TypeInfo(
            TYPE_ID_CAPABILITY_CATEGORY, GROUP_ID_NAMESPACE_ELEMENTS);

    final static public TypeInfo TYPE_PRIVILEGE_CATEGORY = new TypeInfo(
            TYPE_ID_PRIVILEGE_CATEGORY, GROUP_ID_NAMESPACE_ELEMENTS);

    // ---------- ABSTRACT -----------------------------------------------------

    final static public String GROUP_ID_ABSTRACT_ORG_ELEMENTS =
            "com.tibco.xpd.om.resources.ui.types.AbstractOrgElements"; //$NON-NLS-1$

    final static public TypeInfo TYPE_ABSTRACT_CAPABILITY = new TypeInfo(
            TYPE_ID_ABSTRACT_CAPABILITY);

    final static public TypeInfo TYPE_AUTHORIZABLE = new TypeInfo(
            TYPE_ID_AUTHORIZABLE);

    final static public TypeInfo TYPE_BASE_ORG_MODEL = new TypeInfo(
            TYPE_ID_BASE_ORG_MODEL);

    final static public TypeInfo TYPE_FEATURE = new TypeInfo(TYPE_ID_FEATURE);

    final static public TypeInfo TYPE_MULTIPLE_FEATURE = new TypeInfo(
            TYPE_ID_MULTIPLE_FEATURE);

    private static final TypeInfo TYPE_ORG_QUERY = new TypeInfo(
            TYPE_ID_ORG_QUERY);

    /**
     * @param eObject
     * @return
     */
    public static TypeInfo getType(Object object) {
        if (object instanceof IndexerItem) {
            return new TypeInfo(((IndexerItem) object).getType(),
                    ((IndexerItem) object)
                            .get(OMResourceIndexProvider.GROUP_ID));
        }
        if (object instanceof LocationType) {
            return TYPE_LOCATION_TYPE;
        }
        if (object instanceof OrganizationType) {
            return TYPE_ORGANIZATION_TYPE;
        }
        if (object instanceof OrgUnitRelationshipType) {
            return TYPE_ORG_UNIT_RELATIONSHIP_TYPE;
        }
        if (object instanceof OrgUnitType) {
            return TYPE_ORG_UNIT_TYPE;
        }
        if (object instanceof PositionType) {
            return TYPE_POSITION_TYPE;
        }
        if (object instanceof Attribute) {
            return TYPE_ATTRIBUTE;
        }
        if (object instanceof AttributeType) {
            return TYPE_ATTRIBUTE_TYPE;
        }
        if (object instanceof Capability) {
            return TYPE_CAPABILITY;
        }
        if (object instanceof CapabilityCategory) {
            return TYPE_CAPABILITY_CATEGORY;
        }
        if (object instanceof PrivilegeCategory) {
            return TYPE_PRIVILEGE_CATEGORY;
        }
        if (object instanceof Group) {
            return TYPE_GROUP;
        }
        if (object instanceof Location) {
            return TYPE_LOCATION;
        }
        if (object instanceof Organization) {
            /*
             * XPD-5300: Addition of Dynamic Organization
             */
            if (((Organization) object).isDynamic()) {
                return TYPE_DYNAMIC_ORGANIZATION;
            }
            return TYPE_ORGANIZATION;
        }
        /*
         * XPD-5300: Addition of Dynamic OrgUnit
         */
        if (object instanceof DynamicOrgUnit) {
            return TYPE_DYNAMIC_ORG_UNIT;
        }
        if (object instanceof OrgUnit) {
            return TYPE_ORG_UNIT;
        }
        if (object instanceof OrgUnitFeature) {
            return TYPE_ORG_UNIT_FEATURE;
        }
        if (object instanceof OrgUnitRelationship) {
            return TYPE_ORG_UNIT_RELATIONSHIP;
        }
        if (object instanceof Position) {
            return TYPE_POSITION;
        }
        if (object instanceof PositionFeature) {
            return TYPE_POSITION_FEATURE;
        }
        if (object instanceof Privilege) {
            return TYPE_PRIVILEGE;
        }

        if (object instanceof OrgModel) {
            return TYPE_ORG_MODEL;
        }
        if (object instanceof OrgMetaModel) {
            return TYPE_ORG_META_MODEL;
        }
        if (object instanceof Resource) {
            return TYPE_RESOURCE;
        }
        if (object instanceof ResourceType) {
            return TYPE_RESOURCE_TYPE;
        }
        if (object instanceof OrgQuery) {
            return TYPE_ORG_QUERY;
        }

        return null;
    }

    /**
     * Creates a typed item from the resource db item.
     * 
     * @param item
     * @return
     */
    public static TypedItem createTypedItem(IndexerItem item) {
        TypeInfo typeInfo = getType(item);

        // unknown type

        if (typeInfo == null) {
            return null;
        }

        TypedItem result = new TypedItem(typeInfo);
        String name = item.getName();

        int pos = name.lastIndexOf(OMWorkingCopy.JAVA_PACKAGE_SEPARATOR);
        if (pos != -1) {
            name =
                    name.substring(pos
                            + OMWorkingCopy.JAVA_PACKAGE_SEPARATOR.length(),
                            name.length());
        }
        result.setName(name);
        result.setQualifiedName(item.getName());
        result.setUriString(item.getURI());
        return result;
    }

    /**
     * @param typeInfo
     * @param name
     * @param uriString
     * @param additionalAttributes
     * @return
     */
    public static IndexerItem createIndexerItem(TypeInfo typeInfo, String name,
            String uriString, Map<String, String> additionalAttributes) {
        String type = typeInfo.getTypeId();
        IndexerItem indexerItem =
                new IndexerItemImpl(name, type, uriString, additionalAttributes);
        return indexerItem;
    }

    /**
     * return OM Picker types
     * 
     * @return
     */
    public static TypeInfo[] getOMPickerTypes() {
        return new TypeInfo[] { OMTypesFactory.TYPE_ORG_UNIT,
                OMTypesFactory.TYPE_POSITION, OMTypesFactory.TYPE_GROUP,
                OMTypesFactory.TYPE_CAPABILITY, OMTypesFactory.TYPE_PRIVILEGE,
                OMTypesFactory.TYPE_LOCATION, OMTypesFactory.TYPE_ATTRIBUTE,
                OMTypesFactory.TYPE_ORG_QUERY };
    }

    /**
     * Return OM Picker types which are relevant to be used as Participant
     * types.
     * 
     * @return OM Picker types which are relevant to be used as Participant
     *         types.
     */
    public static TypeInfo[] getParticipantRelevantTypes() {
        return new TypeInfo[] { OMTypesFactory.TYPE_ORG_UNIT,
                OMTypesFactory.TYPE_POSITION, OMTypesFactory.TYPE_GROUP };
    }
}
