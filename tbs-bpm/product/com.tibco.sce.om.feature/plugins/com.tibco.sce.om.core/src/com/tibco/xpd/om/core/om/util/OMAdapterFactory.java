/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.util;

import com.tibco.xpd.om.core.om.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.om.core.om.OMPackage
 * @generated
 */
public class OMAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static OMPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OMAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = OMPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OMSwitch<Adapter> modelSwitch = new OMSwitch<Adapter>() {
        @Override
        public Adapter caseOrgUnit(OrgUnit object) {
            return createOrgUnitAdapter();
        }

        @Override
        public Adapter caseDynamicOrgUnit(DynamicOrgUnit object) {
            return createDynamicOrgUnitAdapter();
        }

        @Override
        public Adapter caseDynamicOrgReference(DynamicOrgReference object) {
            return createDynamicOrgReferenceAdapter();
        }

        @Override
        public Adapter casePosition(Position object) {
            return createPositionAdapter();
        }

        @Override
        public Adapter caseLocation(Location object) {
            return createLocationAdapter();
        }

        @Override
        public Adapter caseOrgNode(OrgNode object) {
            return createOrgNodeAdapter();
        }

        @Override
        public Adapter caseOrgElement(OrgElement object) {
            return createOrgElementAdapter();
        }

        @Override
        public Adapter caseCapability(Capability object) {
            return createCapabilityAdapter();
        }

        @Override
        public Adapter caseGroup(Group object) {
            return createGroupAdapter();
        }

        @Override
        public Adapter caseOrganization(Organization object) {
            return createOrganizationAdapter();
        }

        @Override
        public Adapter caseDynamicOrgIdentifier(DynamicOrgIdentifier object) {
            return createDynamicOrgIdentifierAdapter();
        }

        @Override
        public Adapter caseAttributeValue(AttributeValue object) {
            return createAttributeValueAdapter();
        }

        @Override
        public Adapter caseAttribute(Attribute object) {
            return createAttributeAdapter();
        }

        @Override
        public Adapter caseOrganizationType(OrganizationType object) {
            return createOrganizationTypeAdapter();
        }

        @Override
        public Adapter caseOrgUnitType(OrgUnitType object) {
            return createOrgUnitTypeAdapter();
        }

        @Override
        public Adapter casePositionType(PositionType object) {
            return createPositionTypeAdapter();
        }

        @Override
        public Adapter caseOrgUnitFeature(OrgUnitFeature object) {
            return createOrgUnitFeatureAdapter();
        }

        @Override
        public Adapter caseMultipleFeature(MultipleFeature object) {
            return createMultipleFeatureAdapter();
        }

        @Override
        public Adapter casePositionFeature(PositionFeature object) {
            return createPositionFeatureAdapter();
        }

        @Override
        public Adapter caseOrgElementType(OrgElementType object) {
            return createOrgElementTypeAdapter();
        }

        @Override
        public Adapter caseOrgUnitRelationship(OrgUnitRelationship object) {
            return createOrgUnitRelationshipAdapter();
        }

        @Override
        public Adapter caseOrgUnitRelationshipType(
                OrgUnitRelationshipType object) {
            return createOrgUnitRelationshipTypeAdapter();
        }

        @Override
        public Adapter caseCapabilityCategory(CapabilityCategory object) {
            return createCapabilityCategoryAdapter();
        }

        @Override
        public Adapter caseLocationType(LocationType object) {
            return createLocationTypeAdapter();
        }

        @Override
        public Adapter caseNamedElement(NamedElement object) {
            return createNamedElementAdapter();
        }

        @Override
        public Adapter caseOrgModel(OrgModel object) {
            return createOrgModelAdapter();
        }

        @Override
        public Adapter caseEnumValue(EnumValue object) {
            return createEnumValueAdapter();
        }

        @Override
        public Adapter casePrivilege(Privilege object) {
            return createPrivilegeAdapter();
        }

        @Override
        public Adapter caseQualifiedOrgElement(QualifiedOrgElement object) {
            return createQualifiedOrgElementAdapter();
        }

        @Override
        public Adapter caseAuthorizable(Authorizable object) {
            return createAuthorizableAdapter();
        }

        @Override
        public Adapter caseFeature(Feature object) {
            return createFeatureAdapter();
        }

        @Override
        public Adapter caseOrgTypedElement(OrgTypedElement object) {
            return createOrgTypedElementAdapter();
        }

        @Override
        public Adapter caseNamespace(Namespace object) {
            return createNamespaceAdapter();
        }

        @Override
        public Adapter caseCapabilityAssociation(CapabilityAssociation object) {
            return createCapabilityAssociationAdapter();
        }

        @Override
        public Adapter caseOrgMetaModel(OrgMetaModel object) {
            return createOrgMetaModelAdapter();
        }

        @Override
        public Adapter caseBaseOrgModel(BaseOrgModel object) {
            return createBaseOrgModelAdapter();
        }

        @Override
        public Adapter caseModelElement(ModelElement object) {
            return createModelElementAdapter();
        }

        @Override
        public Adapter casePrivilegeAssociation(PrivilegeAssociation object) {
            return createPrivilegeAssociationAdapter();
        }

        @Override
        public Adapter caseCapable(Capable object) {
            return createCapableAdapter();
        }

        @Override
        public Adapter caseQualifiedAssociation(QualifiedAssociation object) {
            return createQualifiedAssociationAdapter();
        }

        @Override
        public Adapter casePrivilegeCategory(PrivilegeCategory object) {
            return createPrivilegeCategoryAdapter();
        }

        @Override
        public Adapter caseAllocable(Allocable object) {
            return createAllocableAdapter();
        }

        @Override
        public Adapter caseResourceType(ResourceType object) {
            return createResourceTypeAdapter();
        }

        @Override
        public Adapter caseResource(Resource object) {
            return createResourceAdapter();
        }

        @Override
        public Adapter caseResourceAssociation(ResourceAssociation object) {
            return createResourceAssociationAdapter();
        }

        @Override
        public Adapter caseAssociableWithResources(
                AssociableWithResources object) {
            return createAssociableWithResourcesAdapter();
        }

        @Override
        public Adapter caseOrgQuery(OrgQuery object) {
            return createOrgQueryAdapter();
        }

        @Override
        public Adapter caseLocatable(Locatable object) {
            return createLocatableAdapter();
        }

        @Override
        public Adapter caseSystemAction(SystemAction object) {
            return createSystemActionAdapter();
        }

        @Override
        public Adapter caseAssociableWithPrivileges(
                AssociableWithPrivileges object) {
            return createAssociableWithPrivilegesAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgUnit <em>Org Unit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgUnit
     * @generated
     */
    public Adapter createOrgUnitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.DynamicOrgUnit <em>Dynamic Org Unit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.DynamicOrgUnit
     * @generated
     */
    public Adapter createDynamicOrgUnitAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.DynamicOrgReference <em>Dynamic Org Reference</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.DynamicOrgReference
     * @generated
     */
    public Adapter createDynamicOrgReferenceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Position <em>Position</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Position
     * @generated
     */
    public Adapter createPositionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Location <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Location
     * @generated
     */
    public Adapter createLocationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgNode <em>Org Node</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgNode
     * @generated
     */
    public Adapter createOrgNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgElement <em>Org Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgElement
     * @generated
     */
    public Adapter createOrgElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Capability <em>Capability</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Capability
     * @generated
     */
    public Adapter createCapabilityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Group <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Group
     * @generated
     */
    public Adapter createGroupAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Organization <em>Organization</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Organization
     * @generated
     */
    public Adapter createOrganizationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.DynamicOrgIdentifier <em>Dynamic Org Identifier</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.DynamicOrgIdentifier
     * @generated
     */
    public Adapter createDynamicOrgIdentifierAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.AttributeValue <em>Attribute Value</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.AttributeValue
     * @generated
     */
    public Adapter createAttributeValueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Attribute <em>Attribute</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Attribute
     * @generated
     */
    public Adapter createAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrganizationType <em>Organization Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrganizationType
     * @generated
     */
    public Adapter createOrganizationTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgUnitType <em>Org Unit Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgUnitType
     * @generated
     */
    public Adapter createOrgUnitTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.PositionType <em>Position Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.PositionType
     * @generated
     */
    public Adapter createPositionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgUnitFeature <em>Org Unit Feature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgUnitFeature
     * @generated
     */
    public Adapter createOrgUnitFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.MultipleFeature <em>Multiple Feature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.MultipleFeature
     * @generated
     */
    public Adapter createMultipleFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.PositionFeature <em>Position Feature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.PositionFeature
     * @generated
     */
    public Adapter createPositionFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgElementType <em>Org Element Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgElementType
     * @generated
     */
    public Adapter createOrgElementTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship <em>Org Unit Relationship</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationship
     * @generated
     */
    public Adapter createOrgUnitRelationshipAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgUnitRelationshipType <em>Org Unit Relationship Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationshipType
     * @generated
     */
    public Adapter createOrgUnitRelationshipTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.CapabilityCategory <em>Capability Category</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.CapabilityCategory
     * @generated
     */
    public Adapter createCapabilityCategoryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.LocationType <em>Location Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.LocationType
     * @generated
     */
    public Adapter createLocationTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgModel <em>Org Model</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgModel
     * @generated
     */
    public Adapter createOrgModelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.EnumValue <em>Enum Value</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.EnumValue
     * @generated
     */
    public Adapter createEnumValueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Privilege <em>Privilege</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Privilege
     * @generated
     */
    public Adapter createPrivilegeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.QualifiedOrgElement <em>Qualified Org Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.QualifiedOrgElement
     * @generated
     */
    public Adapter createQualifiedOrgElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Authorizable <em>Authorizable</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Authorizable
     * @generated
     */
    public Adapter createAuthorizableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Feature <em>Feature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Feature
     * @generated
     */
    public Adapter createFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgTypedElement <em>Org Typed Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgTypedElement
     * @generated
     */
    public Adapter createOrgTypedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Namespace <em>Namespace</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Namespace
     * @generated
     */
    public Adapter createNamespaceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.CapabilityAssociation <em>Capability Association</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.CapabilityAssociation
     * @generated
     */
    public Adapter createCapabilityAssociationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgMetaModel <em>Org Meta Model</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel
     * @generated
     */
    public Adapter createOrgMetaModelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.BaseOrgModel <em>Base Org Model</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.BaseOrgModel
     * @generated
     */
    public Adapter createBaseOrgModelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.ModelElement <em>Model Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.ModelElement
     * @generated
     */
    public Adapter createModelElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.PrivilegeAssociation <em>Privilege Association</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.PrivilegeAssociation
     * @generated
     */
    public Adapter createPrivilegeAssociationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Capable <em>Capable</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Capable
     * @generated
     */
    public Adapter createCapableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.QualifiedAssociation <em>Qualified Association</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.QualifiedAssociation
     * @generated
     */
    public Adapter createQualifiedAssociationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.PrivilegeCategory <em>Privilege Category</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.PrivilegeCategory
     * @generated
     */
    public Adapter createPrivilegeCategoryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Allocable <em>Allocable</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Allocable
     * @generated
     */
    public Adapter createAllocableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.ResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.ResourceType
     * @generated
     */
    public Adapter createResourceTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Resource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Resource
     * @generated
     */
    public Adapter createResourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.ResourceAssociation <em>Resource Association</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.ResourceAssociation
     * @generated
     */
    public Adapter createResourceAssociationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.AssociableWithResources <em>Associable With Resources</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.AssociableWithResources
     * @generated
     */
    public Adapter createAssociableWithResourcesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.OrgQuery <em>Org Query</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.OrgQuery
     * @generated
     */
    public Adapter createOrgQueryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.Locatable <em>Locatable</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.Locatable
     * @generated
     */
    public Adapter createLocatableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.SystemAction <em>System Action</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.SystemAction
     * @generated
     */
    public Adapter createSystemActionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.om.core.om.AssociableWithPrivileges <em>Associable With Privileges</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.om.core.om.AssociableWithPrivileges
     * @generated
     */
    public Adapter createAssociableWithPrivilegesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //OMAdapterFactory
