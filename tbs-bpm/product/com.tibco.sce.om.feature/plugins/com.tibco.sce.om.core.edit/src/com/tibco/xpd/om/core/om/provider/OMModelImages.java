/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

/**
 * Image constants for the icons from this plug-in. Please use
 * {@link #getImageObject(String)} with one of the images path constant as a
 * parameter to obtain image object (valid URI to the image resource). To obtain
 * Image or ImageDescriptor use:
 * <code>ExtendedImageRegistry.INSTANCE.getImage(Object)</code>
 * <code>ExtendedImageRegistry.INSTANCE.getImageDescriptor(Object)</code>
 * passing image object as a parameter.
 * 
 * <pre>
 * Example usage:
 *  Get Image:
 *  Image img = ExtendedImageRegistry.INSTANCE.getImage(OMModelImages.getImageObject(OMModelImages.IMAGE_CAPABILITY));
 *  
 *  Get ImageDescriptor:
 *  ImageDescriptor imgDesc = ExtendedImageRegistry.INSTANCE.getImageDescriptor(OMModelImages.getImageObject(OMModelImages.IMAGE_CAPABILITY));
 * </pre>
 * 
 * @author Jan Arciuchiewicz
 */
public class OMModelImages {

    public static final String IMAGE_ATTRIBUTE = "full/obj16/Attribute.gif"; //$NON-NLS-1$

    public static final String IMAGE_ATTRIBUTE_VALUE =
            "full/obj16/AttributeValue.gif"; //$NON-NLS-1$

    public static final String IMAGE_CAPABILITIES_GROUP =
            "full/obj16/CapabilitiesGroup.gif"; //$NON-NLS-1$

    public static final String IMAGE_CAPABILITY = "full/obj16/Capability.gif"; //$NON-NLS-1$

    public static final String IMAGE_CAPABILITY_ASSOCIATION =
            "full/obj16/CapabilityAssociation.gif"; //$NON-NLS-1$

    public static final String IMAGE_CAPABILITY_CATEGORY =
            "full/obj16/CapabilityCategory.gif"; //$NON-NLS-1$

    public static final String IMAGE_ENUM_VALUE = "full/obj16/EnumValue.gif"; //$NON-NLS-1$

    public static final String IMAGE_GROUP = "full/obj16/Group.gif"; //$NON-NLS-1$

    public static final String IMAGE_GROUPS_GROUP =
            "full/obj16/GroupsGroup.gif"; //$NON-NLS-1$

    public static final String IMAGE_LOCATION = "full/obj16/Location.gif"; //$NON-NLS-1$

    public static final String IMAGE_LOCATION_GROUP =
            "full/obj16/LocationGroup.gif"; //$NON-NLS-1$

    public static final String IMAGE_LOCATION_TYPE =
            "full/obj16/LocationType.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORGANISATION =
            "full/obj16/Organization.gif"; //$NON-NLS-1$

    public static final String IMAGE_DYNAMIC_ORGANISATION =
            "full/obj16/DynamicOrganization.png"; //$NON-NLS-1$

    public static final String IMAGE_ORGANISATION_TYPE =
            "full/obj16/OrganizationType.gif"; //$NON-NLS-1$

    public static final String IMAGE_DYNAMIC_ORGANISATION_TYPE =
            "full/obj16/DynamicOrganizationType.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_META_MODEL =
            "full/obj16/OrgMetaModel.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_MODEL = "full/obj16/OrgModel.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_UNIT = "full/obj16/OrgUnit.gif"; //$NON-NLS-1$

    public static final String IMAGE_DYNAMIC_ORG_UNIT =
            "full/obj16/DynamicOrgUnit.png"; //$NON-NLS-1$

    public static final String IMAGE_ORG_UNIT_FEATURE =
            "full/obj16/OrgUnitFeature.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_UNIT_RELATIONSHIP =
            "full/obj16/OrgUnitRelationship.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_UNIT_RELATIONSHIP_TYPE =
            "full/obj16/OrgUnitRelationshipType.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_UNIT_TYPE =
            "full/obj16/OrgUnitType.gif"; //$NON-NLS-1$

    public static final String IMAGE_PARAMETER = "full/obj16/Parameter.gif"; //$NON-NLS-1$

    public static final String IMAGE_PARAMETER_FEATURE =
            "full/obj16/ParameterFeature.gif"; //$NON-NLS-1$

    public static final String IMAGE_POSITION = "full/obj16/Position.gif"; //$NON-NLS-1$

    public static final String IMAGE_POSITION_FEATURE =
            "full/obj16/PositionFeature.gif"; //$NON-NLS-1$

    public static final String IMAGE_POSITION_TYPE =
            "full/obj16/PositionType.gif"; //$NON-NLS-1$

    public static final String IMAGE_PRIVILEGE = "full/obj16/Privilege.gif"; //$NON-NLS-1$

    public static final String IMAGE_PRIVILEGE_CATEGORY =
            "full/obj16/PrivilegeCategory.gif"; //$NON-NLS-1$

    public static final String IMAGE_RESOURCE_FEATURES_GROUP =
            "full/obj16/ResourcefeaturesGroup.gif"; //$NON-NLS-1$

    public static final String IMAGE_RESOURCE = "full/obj16/Resource.gif"; //$NON-NLS-1$

    public static final String IMAGE_RESOURCE_TYPE =
            "full/obj16/ResourceType.gif"; //$NON-NLS-1$

    public static final String IMAGE_HIERARCHY_EXPAND = "full/obj16/plus.png"; //$NON-NLS-1$

    public static final String IMAGE_HIERARCHY_COLLAPSE =
            "full/obj16/minus.png"; //$NON-NLS-1$

    public static final String IMAGE_ORG_MODEL_SHORTCUT2 =
            "full/obj16/shortcut2.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_MODEL64 = "wizban/OMWizard.png"; //$NON-NLS-1$

    public static final String IMAGE_ORG_MODEL_SHORTCUT =
            "full/obj16/shortcut.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_MODEL_SHORTCUTINV =
            "full/obj16/shortcutInverse.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_UNIT_TYPES_GROUP =
            "full/obj16/OrgUnitTypesGroup.gif"; //$NON-NLS-1$

    public static final String IMAGE_ORG_QUERY = "full/obj16/OrgQuery.gif"; //$NON-NLS-1$

    /**
     * Returns image object in form of URI. To obtain Image or ImageDescriptor
     * use: <code>ExtendedImageRegistry.INSTANCE.getImage(Object)</code>
     * <code>ExtendedImageRegistry.INSTANCE.getImageDescriptor(Object)</code>
     * 
     * @param path
     *            path to the icon om edit plug-in relative (please use
     *            constants defined in this class)
     * @return the valid URI of the image.
     */
    public static Object getImageObject(String path) {
        return OrganisationModelEditPlugin.INSTANCE.getImage(path);
    }

}
