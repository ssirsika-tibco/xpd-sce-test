/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.util;

import com.tibco.xpd.om.core.om.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.om.core.om.OMPackage
 * @generated
 */
public class OMSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static OMPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OMSwitch() {
        if (modelPackage == null) {
            modelPackage = OMPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @parameter ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case OMPackage.ORG_UNIT: {
            OrgUnit orgUnit = (OrgUnit) theEObject;
            T result = caseOrgUnit(orgUnit);
            if (result == null)
                result = caseOrgTypedElement(orgUnit);
            if (result == null)
                result = caseAuthorizable(orgUnit);
            if (result == null)
                result = caseAllocable(orgUnit);
            if (result == null)
                result = caseLocatable(orgUnit);
            if (result == null)
                result = caseOrgNode(orgUnit);
            if (result == null)
                result = caseOrgElement(orgUnit);
            if (result == null)
                result = caseAssociableWithPrivileges(orgUnit);
            if (result == null)
                result = caseNamespace(orgUnit);
            if (result == null)
                result = caseNamedElement(orgUnit);
            if (result == null)
                result = caseModelElement(orgUnit);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.DYNAMIC_ORG_UNIT: {
            DynamicOrgUnit dynamicOrgUnit = (DynamicOrgUnit) theEObject;
            T result = caseDynamicOrgUnit(dynamicOrgUnit);
            if (result == null)
                result = caseOrgUnit(dynamicOrgUnit);
            if (result == null)
                result = caseOrgTypedElement(dynamicOrgUnit);
            if (result == null)
                result = caseAuthorizable(dynamicOrgUnit);
            if (result == null)
                result = caseAllocable(dynamicOrgUnit);
            if (result == null)
                result = caseLocatable(dynamicOrgUnit);
            if (result == null)
                result = caseOrgNode(dynamicOrgUnit);
            if (result == null)
                result = caseOrgElement(dynamicOrgUnit);
            if (result == null)
                result = caseAssociableWithPrivileges(dynamicOrgUnit);
            if (result == null)
                result = caseNamespace(dynamicOrgUnit);
            if (result == null)
                result = caseNamedElement(dynamicOrgUnit);
            if (result == null)
                result = caseModelElement(dynamicOrgUnit);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.DYNAMIC_ORG_REFERENCE: {
            DynamicOrgReference dynamicOrgReference =
                    (DynamicOrgReference) theEObject;
            T result = caseDynamicOrgReference(dynamicOrgReference);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.POSITION: {
            Position position = (Position) theEObject;
            T result = casePosition(position);
            if (result == null)
                result = caseOrgTypedElement(position);
            if (result == null)
                result = caseAuthorizable(position);
            if (result == null)
                result = caseCapable(position);
            if (result == null)
                result = caseAllocable(position);
            if (result == null)
                result = caseAssociableWithResources(position);
            if (result == null)
                result = caseLocatable(position);
            if (result == null)
                result = caseOrgNode(position);
            if (result == null)
                result = caseOrgElement(position);
            if (result == null)
                result = caseAssociableWithPrivileges(position);
            if (result == null)
                result = caseNamespace(position);
            if (result == null)
                result = caseNamedElement(position);
            if (result == null)
                result = caseModelElement(position);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.LOCATION: {
            Location location = (Location) theEObject;
            T result = caseLocation(location);
            if (result == null)
                result = caseOrgTypedElement(location);
            if (result == null)
                result = caseAllocable(location);
            if (result == null)
                result = caseOrgElement(location);
            if (result == null)
                result = caseNamespace(location);
            if (result == null)
                result = caseNamedElement(location);
            if (result == null)
                result = caseModelElement(location);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_NODE: {
            OrgNode orgNode = (OrgNode) theEObject;
            T result = caseOrgNode(orgNode);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_ELEMENT: {
            OrgElement orgElement = (OrgElement) theEObject;
            T result = caseOrgElement(orgElement);
            if (result == null)
                result = caseNamespace(orgElement);
            if (result == null)
                result = caseNamedElement(orgElement);
            if (result == null)
                result = caseModelElement(orgElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.CAPABILITY: {
            Capability capability = (Capability) theEObject;
            T result = caseCapability(capability);
            if (result == null)
                result = caseQualifiedOrgElement(capability);
            if (result == null)
                result = caseOrgElement(capability);
            if (result == null)
                result = caseNamespace(capability);
            if (result == null)
                result = caseNamedElement(capability);
            if (result == null)
                result = caseModelElement(capability);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.GROUP: {
            Group group = (Group) theEObject;
            T result = caseGroup(group);
            if (result == null)
                result = caseOrgElement(group);
            if (result == null)
                result = caseCapable(group);
            if (result == null)
                result = caseAllocable(group);
            if (result == null)
                result = caseAuthorizable(group);
            if (result == null)
                result = caseAssociableWithResources(group);
            if (result == null)
                result = caseNamespace(group);
            if (result == null)
                result = caseAssociableWithPrivileges(group);
            if (result == null)
                result = caseNamedElement(group);
            if (result == null)
                result = caseModelElement(group);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORGANIZATION: {
            Organization organization = (Organization) theEObject;
            T result = caseOrganization(organization);
            if (result == null)
                result = caseOrgTypedElement(organization);
            if (result == null)
                result = caseAllocable(organization);
            if (result == null)
                result = caseLocatable(organization);
            if (result == null)
                result = caseOrgNode(organization);
            if (result == null)
                result = caseOrgElement(organization);
            if (result == null)
                result = caseNamespace(organization);
            if (result == null)
                result = caseNamedElement(organization);
            if (result == null)
                result = caseModelElement(organization);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.DYNAMIC_ORG_IDENTIFIER: {
            DynamicOrgIdentifier dynamicOrgIdentifier =
                    (DynamicOrgIdentifier) theEObject;
            T result = caseDynamicOrgIdentifier(dynamicOrgIdentifier);
            if (result == null)
                result = caseNamespace(dynamicOrgIdentifier);
            if (result == null)
                result = caseNamedElement(dynamicOrgIdentifier);
            if (result == null)
                result = caseModelElement(dynamicOrgIdentifier);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ATTRIBUTE_VALUE: {
            AttributeValue attributeValue = (AttributeValue) theEObject;
            T result = caseAttributeValue(attributeValue);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ATTRIBUTE: {
            Attribute attribute = (Attribute) theEObject;
            T result = caseAttribute(attribute);
            if (result == null)
                result = caseFeature(attribute);
            if (result == null)
                result = caseNamespace(attribute);
            if (result == null)
                result = caseNamedElement(attribute);
            if (result == null)
                result = caseModelElement(attribute);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORGANIZATION_TYPE: {
            OrganizationType organizationType = (OrganizationType) theEObject;
            T result = caseOrganizationType(organizationType);
            if (result == null)
                result = caseOrgElementType(organizationType);
            if (result == null)
                result = caseNamespace(organizationType);
            if (result == null)
                result = caseNamedElement(organizationType);
            if (result == null)
                result = caseModelElement(organizationType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_UNIT_TYPE: {
            OrgUnitType orgUnitType = (OrgUnitType) theEObject;
            T result = caseOrgUnitType(orgUnitType);
            if (result == null)
                result = caseOrgElementType(orgUnitType);
            if (result == null)
                result = caseNamespace(orgUnitType);
            if (result == null)
                result = caseNamedElement(orgUnitType);
            if (result == null)
                result = caseModelElement(orgUnitType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.POSITION_TYPE: {
            PositionType positionType = (PositionType) theEObject;
            T result = casePositionType(positionType);
            if (result == null)
                result = caseOrgElementType(positionType);
            if (result == null)
                result = caseNamespace(positionType);
            if (result == null)
                result = caseNamedElement(positionType);
            if (result == null)
                result = caseModelElement(positionType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_UNIT_FEATURE: {
            OrgUnitFeature orgUnitFeature = (OrgUnitFeature) theEObject;
            T result = caseOrgUnitFeature(orgUnitFeature);
            if (result == null)
                result = caseMultipleFeature(orgUnitFeature);
            if (result == null)
                result = caseFeature(orgUnitFeature);
            if (result == null)
                result = caseOrgElement(orgUnitFeature);
            if (result == null)
                result = caseNamespace(orgUnitFeature);
            if (result == null)
                result = caseNamedElement(orgUnitFeature);
            if (result == null)
                result = caseModelElement(orgUnitFeature);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.MULTIPLE_FEATURE: {
            MultipleFeature multipleFeature = (MultipleFeature) theEObject;
            T result = caseMultipleFeature(multipleFeature);
            if (result == null)
                result = caseFeature(multipleFeature);
            if (result == null)
                result = caseOrgElement(multipleFeature);
            if (result == null)
                result = caseNamespace(multipleFeature);
            if (result == null)
                result = caseNamedElement(multipleFeature);
            if (result == null)
                result = caseModelElement(multipleFeature);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.POSITION_FEATURE: {
            PositionFeature positionFeature = (PositionFeature) theEObject;
            T result = casePositionFeature(positionFeature);
            if (result == null)
                result = caseMultipleFeature(positionFeature);
            if (result == null)
                result = caseFeature(positionFeature);
            if (result == null)
                result = caseOrgElement(positionFeature);
            if (result == null)
                result = caseNamespace(positionFeature);
            if (result == null)
                result = caseNamedElement(positionFeature);
            if (result == null)
                result = caseModelElement(positionFeature);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_ELEMENT_TYPE: {
            OrgElementType orgElementType = (OrgElementType) theEObject;
            T result = caseOrgElementType(orgElementType);
            if (result == null)
                result = caseNamespace(orgElementType);
            if (result == null)
                result = caseNamedElement(orgElementType);
            if (result == null)
                result = caseModelElement(orgElementType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_UNIT_RELATIONSHIP: {
            OrgUnitRelationship orgUnitRelationship =
                    (OrgUnitRelationship) theEObject;
            T result = caseOrgUnitRelationship(orgUnitRelationship);
            if (result == null)
                result = caseOrgTypedElement(orgUnitRelationship);
            if (result == null)
                result = caseOrgElement(orgUnitRelationship);
            if (result == null)
                result = caseNamespace(orgUnitRelationship);
            if (result == null)
                result = caseNamedElement(orgUnitRelationship);
            if (result == null)
                result = caseModelElement(orgUnitRelationship);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_UNIT_RELATIONSHIP_TYPE: {
            OrgUnitRelationshipType orgUnitRelationshipType =
                    (OrgUnitRelationshipType) theEObject;
            T result = caseOrgUnitRelationshipType(orgUnitRelationshipType);
            if (result == null)
                result = caseOrgElementType(orgUnitRelationshipType);
            if (result == null)
                result = caseNamespace(orgUnitRelationshipType);
            if (result == null)
                result = caseNamedElement(orgUnitRelationshipType);
            if (result == null)
                result = caseModelElement(orgUnitRelationshipType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.CAPABILITY_CATEGORY: {
            CapabilityCategory capabilityCategory =
                    (CapabilityCategory) theEObject;
            T result = caseCapabilityCategory(capabilityCategory);
            if (result == null)
                result = caseNamespace(capabilityCategory);
            if (result == null)
                result = caseNamedElement(capabilityCategory);
            if (result == null)
                result = caseModelElement(capabilityCategory);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.LOCATION_TYPE: {
            LocationType locationType = (LocationType) theEObject;
            T result = caseLocationType(locationType);
            if (result == null)
                result = caseOrgElementType(locationType);
            if (result == null)
                result = caseNamespace(locationType);
            if (result == null)
                result = caseNamedElement(locationType);
            if (result == null)
                result = caseModelElement(locationType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.NAMED_ELEMENT: {
            NamedElement namedElement = (NamedElement) theEObject;
            T result = caseNamedElement(namedElement);
            if (result == null)
                result = caseModelElement(namedElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_MODEL: {
            OrgModel orgModel = (OrgModel) theEObject;
            T result = caseOrgModel(orgModel);
            if (result == null)
                result = caseBaseOrgModel(orgModel);
            if (result == null)
                result = caseAuthorizable(orgModel);
            if (result == null)
                result = caseNamespace(orgModel);
            if (result == null)
                result = caseAssociableWithPrivileges(orgModel);
            if (result == null)
                result = caseNamedElement(orgModel);
            if (result == null)
                result = caseModelElement(orgModel);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ENUM_VALUE: {
            EnumValue enumValue = (EnumValue) theEObject;
            T result = caseEnumValue(enumValue);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.PRIVILEGE: {
            Privilege privilege = (Privilege) theEObject;
            T result = casePrivilege(privilege);
            if (result == null)
                result = caseQualifiedOrgElement(privilege);
            if (result == null)
                result = caseOrgElement(privilege);
            if (result == null)
                result = caseNamespace(privilege);
            if (result == null)
                result = caseNamedElement(privilege);
            if (result == null)
                result = caseModelElement(privilege);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.QUALIFIED_ORG_ELEMENT: {
            QualifiedOrgElement qualifiedOrgElement =
                    (QualifiedOrgElement) theEObject;
            T result = caseQualifiedOrgElement(qualifiedOrgElement);
            if (result == null)
                result = caseOrgElement(qualifiedOrgElement);
            if (result == null)
                result = caseNamespace(qualifiedOrgElement);
            if (result == null)
                result = caseNamedElement(qualifiedOrgElement);
            if (result == null)
                result = caseModelElement(qualifiedOrgElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.AUTHORIZABLE: {
            Authorizable authorizable = (Authorizable) theEObject;
            T result = caseAuthorizable(authorizable);
            if (result == null)
                result = caseAssociableWithPrivileges(authorizable);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.FEATURE: {
            Feature feature = (Feature) theEObject;
            T result = caseFeature(feature);
            if (result == null)
                result = caseNamespace(feature);
            if (result == null)
                result = caseNamedElement(feature);
            if (result == null)
                result = caseModelElement(feature);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_TYPED_ELEMENT: {
            OrgTypedElement orgTypedElement = (OrgTypedElement) theEObject;
            T result = caseOrgTypedElement(orgTypedElement);
            if (result == null)
                result = caseOrgElement(orgTypedElement);
            if (result == null)
                result = caseNamespace(orgTypedElement);
            if (result == null)
                result = caseNamedElement(orgTypedElement);
            if (result == null)
                result = caseModelElement(orgTypedElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.NAMESPACE: {
            Namespace namespace = (Namespace) theEObject;
            T result = caseNamespace(namespace);
            if (result == null)
                result = caseNamedElement(namespace);
            if (result == null)
                result = caseModelElement(namespace);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.CAPABILITY_ASSOCIATION: {
            CapabilityAssociation capabilityAssociation =
                    (CapabilityAssociation) theEObject;
            T result = caseCapabilityAssociation(capabilityAssociation);
            if (result == null)
                result = caseQualifiedAssociation(capabilityAssociation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_META_MODEL: {
            OrgMetaModel orgMetaModel = (OrgMetaModel) theEObject;
            T result = caseOrgMetaModel(orgMetaModel);
            if (result == null)
                result = caseBaseOrgModel(orgMetaModel);
            if (result == null)
                result = caseNamespace(orgMetaModel);
            if (result == null)
                result = caseNamedElement(orgMetaModel);
            if (result == null)
                result = caseModelElement(orgMetaModel);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.BASE_ORG_MODEL: {
            BaseOrgModel baseOrgModel = (BaseOrgModel) theEObject;
            T result = caseBaseOrgModel(baseOrgModel);
            if (result == null)
                result = caseNamespace(baseOrgModel);
            if (result == null)
                result = caseNamedElement(baseOrgModel);
            if (result == null)
                result = caseModelElement(baseOrgModel);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.MODEL_ELEMENT: {
            ModelElement modelElement = (ModelElement) theEObject;
            T result = caseModelElement(modelElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.PRIVILEGE_ASSOCIATION: {
            PrivilegeAssociation privilegeAssociation =
                    (PrivilegeAssociation) theEObject;
            T result = casePrivilegeAssociation(privilegeAssociation);
            if (result == null)
                result = caseQualifiedAssociation(privilegeAssociation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.CAPABLE: {
            Capable capable = (Capable) theEObject;
            T result = caseCapable(capable);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.QUALIFIED_ASSOCIATION: {
            QualifiedAssociation qualifiedAssociation =
                    (QualifiedAssociation) theEObject;
            T result = caseQualifiedAssociation(qualifiedAssociation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.PRIVILEGE_CATEGORY: {
            PrivilegeCategory privilegeCategory =
                    (PrivilegeCategory) theEObject;
            T result = casePrivilegeCategory(privilegeCategory);
            if (result == null)
                result = caseNamespace(privilegeCategory);
            if (result == null)
                result = caseNamedElement(privilegeCategory);
            if (result == null)
                result = caseModelElement(privilegeCategory);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ALLOCABLE: {
            Allocable allocable = (Allocable) theEObject;
            T result = caseAllocable(allocable);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.RESOURCE_TYPE: {
            ResourceType resourceType = (ResourceType) theEObject;
            T result = caseResourceType(resourceType);
            if (result == null)
                result = caseOrgElementType(resourceType);
            if (result == null)
                result = caseNamespace(resourceType);
            if (result == null)
                result = caseNamedElement(resourceType);
            if (result == null)
                result = caseModelElement(resourceType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.RESOURCE: {
            Resource resource = (Resource) theEObject;
            T result = caseResource(resource);
            if (result == null)
                result = caseOrgTypedElement(resource);
            if (result == null)
                result = caseOrgElement(resource);
            if (result == null)
                result = caseNamespace(resource);
            if (result == null)
                result = caseNamedElement(resource);
            if (result == null)
                result = caseModelElement(resource);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.RESOURCE_ASSOCIATION: {
            ResourceAssociation resourceAssociation =
                    (ResourceAssociation) theEObject;
            T result = caseResourceAssociation(resourceAssociation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ASSOCIABLE_WITH_RESOURCES: {
            AssociableWithResources associableWithResources =
                    (AssociableWithResources) theEObject;
            T result = caseAssociableWithResources(associableWithResources);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ORG_QUERY: {
            OrgQuery orgQuery = (OrgQuery) theEObject;
            T result = caseOrgQuery(orgQuery);
            if (result == null)
                result = caseNamespace(orgQuery);
            if (result == null)
                result = caseNamedElement(orgQuery);
            if (result == null)
                result = caseModelElement(orgQuery);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.LOCATABLE: {
            Locatable locatable = (Locatable) theEObject;
            T result = caseLocatable(locatable);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.SYSTEM_ACTION: {
            SystemAction systemAction = (SystemAction) theEObject;
            T result = caseSystemAction(systemAction);
            if (result == null)
                result = caseAssociableWithPrivileges(systemAction);
            if (result == null)
                result = caseModelElement(systemAction);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case OMPackage.ASSOCIABLE_WITH_PRIVILEGES: {
            AssociableWithPrivileges associableWithPrivileges =
                    (AssociableWithPrivileges) theEObject;
            T result = caseAssociableWithPrivileges(associableWithPrivileges);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        default:
            return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Unit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Unit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgUnit(OrgUnit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dynamic Org Unit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dynamic Org Unit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDynamicOrgUnit(DynamicOrgUnit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dynamic Org Reference</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dynamic Org Reference</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDynamicOrgReference(DynamicOrgReference object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Position</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Position</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePosition(Position object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Location</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Location</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLocation(Location object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Node</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Node</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgNode(OrgNode object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgElement(OrgElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Capability</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Capability</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCapability(Capability object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Group</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Group</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGroup(Group object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Organization</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Organization</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrganization(Organization object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Dynamic Org Identifier</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Dynamic Org Identifier</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDynamicOrgIdentifier(DynamicOrgIdentifier object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribute Value</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribute Value</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttributeValue(AttributeValue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttribute(Attribute object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Organization Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Organization Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrganizationType(OrganizationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Unit Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Unit Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgUnitType(OrgUnitType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Position Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Position Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePositionType(PositionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Unit Feature</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Unit Feature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgUnitFeature(OrgUnitFeature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multiple Feature</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multiple Feature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultipleFeature(MultipleFeature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Position Feature</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Position Feature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePositionFeature(PositionFeature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Element Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Element Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgElementType(OrgElementType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Unit Relationship</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Unit Relationship</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgUnitRelationship(OrgUnitRelationship object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Unit Relationship Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Unit Relationship Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgUnitRelationshipType(OrgUnitRelationshipType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Capability Category</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Capability Category</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCapabilityCategory(CapabilityCategory object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Location Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Location Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLocationType(LocationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Model</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgModel(OrgModel object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enum Value</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enum Value</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnumValue(EnumValue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Privilege</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Privilege</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrivilege(Privilege object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Qualified Org Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Qualified Org Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseQualifiedOrgElement(QualifiedOrgElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Authorizable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Authorizable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAuthorizable(Authorizable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Feature</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Feature</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFeature(Feature object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Typed Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Typed Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgTypedElement(OrgTypedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Namespace</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Namespace</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamespace(Namespace object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Capability Association</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Capability Association</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCapabilityAssociation(CapabilityAssociation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Meta Model</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Meta Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgMetaModel(OrgMetaModel object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Base Org Model</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Base Org Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBaseOrgModel(BaseOrgModel object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModelElement(ModelElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Privilege Association</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Privilege Association</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrivilegeAssociation(PrivilegeAssociation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Capable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Capable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCapable(Capable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Qualified Association</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Qualified Association</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseQualifiedAssociation(QualifiedAssociation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Privilege Category</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Privilege Category</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrivilegeCategory(PrivilegeCategory object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocable(Allocable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceType(ResourceType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResource(Resource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resource Association</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resource Association</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceAssociation(ResourceAssociation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Associable With Resources</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Associable With Resources</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssociableWithResources(AssociableWithResources object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Query</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Query</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgQuery(OrgQuery object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Locatable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Locatable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLocatable(Locatable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>System Action</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>System Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSystemAction(SystemAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Associable With Privileges</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Associable With Privileges</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAssociableWithPrivileges(AssociableWithPrivileges object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} //OMSwitch
