/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.util;

import com.tibco.n2.directory.model.de.*;

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
 * @see com.tibco.n2.directory.model.de.DePackage
 * @generated
 */
public class DeSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static DePackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeSwitch() {
        if (modelPackage == null) {
            modelPackage = DePackage.eINSTANCE;
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
            case DePackage.ATTRIBUTE: {
                Attribute attribute = (Attribute)theEObject;
                T result = caseAttribute(attribute);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.ATTRIBUTE_TYPE: {
                AttributeType attributeType = (AttributeType)theEObject;
                T result = caseAttributeType(attributeType);
                if (result == null) result = caseNamedEntity(attributeType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.CAPABILITY: {
                Capability capability = (Capability)theEObject;
                T result = caseCapability(capability);
                if (result == null) result = caseNamedEntity(capability);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.CAPABILITY_CATEGORY: {
                CapabilityCategory capabilityCategory = (CapabilityCategory)theEObject;
                T result = caseCapabilityCategory(capabilityCategory);
                if (result == null) result = caseNamedEntity(capabilityCategory);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.CAPABILITY_HOLDING: {
                CapabilityHolding capabilityHolding = (CapabilityHolding)theEObject;
                T result = caseCapabilityHolding(capabilityHolding);
                if (result == null) result = caseQualifiedHolding(capabilityHolding);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.DOCUMENT_ROOT: {
                DocumentRoot documentRoot = (DocumentRoot)theEObject;
                T result = caseDocumentRoot(documentRoot);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.ENTITY_TYPE: {
                EntityType entityType = (EntityType)theEObject;
                T result = caseEntityType(entityType);
                if (result == null) result = caseNamedEntity(entityType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.FEATURE: {
                Feature feature = (Feature)theEObject;
                T result = caseFeature(feature);
                if (result == null) result = caseNamedEntity(feature);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.GROUP: {
                Group group = (Group)theEObject;
                T result = caseGroup(group);
                if (result == null) result = caseNamedEntity(group);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.GROUP_HOLDING: {
                GroupHolding groupHolding = (GroupHolding)theEObject;
                T result = caseGroupHolding(groupHolding);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.LOCATION: {
                Location location = (Location)theEObject;
                T result = caseLocation(location);
                if (result == null) result = caseTypedEntity(location);
                if (result == null) result = caseNamedEntity(location);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.LOCATION_TYPE: {
                LocationType locationType = (LocationType)theEObject;
                T result = caseLocationType(locationType);
                if (result == null) result = caseEntityType(locationType);
                if (result == null) result = caseNamedEntity(locationType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.META_MODEL: {
                MetaModel metaModel = (MetaModel)theEObject;
                T result = caseMetaModel(metaModel);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.MODEL_ORG_UNIT: {
                ModelOrgUnit modelOrgUnit = (ModelOrgUnit)theEObject;
                T result = caseModelOrgUnit(modelOrgUnit);
                if (result == null) result = caseOrgUnitBase(modelOrgUnit);
                if (result == null) result = caseTypedEntity(modelOrgUnit);
                if (result == null) result = caseNamedEntity(modelOrgUnit);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.MODEL_TEMPLATE: {
                ModelTemplate modelTemplate = (ModelTemplate)theEObject;
                T result = caseModelTemplate(modelTemplate);
                if (result == null) result = caseModelOrgUnit(modelTemplate);
                if (result == null) result = caseOrgUnitBase(modelTemplate);
                if (result == null) result = caseTypedEntity(modelTemplate);
                if (result == null) result = caseNamedEntity(modelTemplate);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.MODEL_TYPE: {
                ModelType modelType = (ModelType)theEObject;
                T result = caseModelType(modelType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.NAMED_ENTITY: {
                NamedEntity namedEntity = (NamedEntity)theEObject;
                T result = caseNamedEntity(namedEntity);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.ORGANIZATION: {
                Organization organization = (Organization)theEObject;
                T result = caseOrganization(organization);
                if (result == null) result = caseTypedEntity(organization);
                if (result == null) result = caseNamedEntity(organization);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.ORGANIZATION_TYPE: {
                OrganizationType organizationType = (OrganizationType)theEObject;
                T result = caseOrganizationType(organizationType);
                if (result == null) result = caseEntityType(organizationType);
                if (result == null) result = caseNamedEntity(organizationType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.ORG_UNIT: {
                OrgUnit orgUnit = (OrgUnit)theEObject;
                T result = caseOrgUnit(orgUnit);
                if (result == null) result = caseOrgUnitBase(orgUnit);
                if (result == null) result = caseTypedEntity(orgUnit);
                if (result == null) result = caseNamedEntity(orgUnit);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.ORG_UNIT_BASE: {
                OrgUnitBase orgUnitBase = (OrgUnitBase)theEObject;
                T result = caseOrgUnitBase(orgUnitBase);
                if (result == null) result = caseTypedEntity(orgUnitBase);
                if (result == null) result = caseNamedEntity(orgUnitBase);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.ORG_UNIT_TYPE: {
                OrgUnitType orgUnitType = (OrgUnitType)theEObject;
                T result = caseOrgUnitType(orgUnitType);
                if (result == null) result = caseEntityType(orgUnitType);
                if (result == null) result = caseNamedEntity(orgUnitType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.POSITION: {
                Position position = (Position)theEObject;
                T result = casePosition(position);
                if (result == null) result = caseTypedEntity(position);
                if (result == null) result = caseNamedEntity(position);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.POSITION_HOLDING: {
                PositionHolding positionHolding = (PositionHolding)theEObject;
                T result = casePositionHolding(positionHolding);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.POSITION_TYPE: {
                PositionType positionType = (PositionType)theEObject;
                T result = casePositionType(positionType);
                if (result == null) result = caseEntityType(positionType);
                if (result == null) result = caseNamedEntity(positionType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.PRIVILEGE: {
                Privilege privilege = (Privilege)theEObject;
                T result = casePrivilege(privilege);
                if (result == null) result = caseNamedEntity(privilege);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.PRIVILEGE_CATEGORY: {
                PrivilegeCategory privilegeCategory = (PrivilegeCategory)theEObject;
                T result = casePrivilegeCategory(privilegeCategory);
                if (result == null) result = caseNamedEntity(privilegeCategory);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.PRIVILEGE_HOLDING: {
                PrivilegeHolding privilegeHolding = (PrivilegeHolding)theEObject;
                T result = casePrivilegeHolding(privilegeHolding);
                if (result == null) result = caseQualifiedHolding(privilegeHolding);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.QUALIFIED_HOLDING: {
                QualifiedHolding qualifiedHolding = (QualifiedHolding)theEObject;
                T result = caseQualifiedHolding(qualifiedHolding);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.QUALIFIER: {
                Qualifier qualifier = (Qualifier)theEObject;
                T result = caseQualifier(qualifier);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.RESOURCE: {
                Resource resource = (Resource)theEObject;
                T result = caseResource(resource);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.SYSTEM_ACTION: {
                SystemAction systemAction = (SystemAction)theEObject;
                T result = caseSystemAction(systemAction);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case DePackage.TYPED_ENTITY: {
                TypedEntity typedEntity = (TypedEntity)theEObject;
                T result = caseTypedEntity(typedEntity);
                if (result == null) result = caseNamedEntity(typedEntity);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
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
     * Returns the result of interpreting the object as an instance of '<em>Attribute Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribute Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttributeType(AttributeType object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Capability Holding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Capability Holding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCapabilityHolding(CapabilityHolding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentRoot(DocumentRoot object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Entity Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Entity Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEntityType(EntityType object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Group Holding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Group Holding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGroupHolding(GroupHolding object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Meta Model</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Meta Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMetaModel(MetaModel object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Model Org Unit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Model Org Unit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModelOrgUnit(ModelOrgUnit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Model Template</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Model Template</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModelTemplate(ModelTemplate object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Model Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Model Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModelType(ModelType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Entity</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedEntity(NamedEntity object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Org Unit Base</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Unit Base</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgUnitBase(OrgUnitBase object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Position Holding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Position Holding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePositionHolding(PositionHolding object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Privilege Holding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Privilege Holding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePrivilegeHolding(PrivilegeHolding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Qualified Holding</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Qualified Holding</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseQualifiedHolding(QualifiedHolding object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Qualifier</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Qualifier</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseQualifier(Qualifier object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Typed Entity</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Typed Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTypedEntity(TypedEntity object) {
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

} //DeSwitch
