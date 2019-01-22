/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.xpd.om.core.om.Allocable;
import com.tibco.xpd.om.core.om.AssociableWithPrivileges;
import com.tibco.xpd.om.core.om.AssociableWithResources;
import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.BaseOrgModel;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.Capable;
import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.Feature;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Locatable;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.core.om.MultipleFeature;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.Namespace;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgNode;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.om.core.om.OrgTypedElement;
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
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.QualifiedAssociation;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.ResourceAssociation;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.SystemAction;
import com.tibco.xpd.om.core.om.SystemActionIdentifier;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class OMPackageImpl extends EPackageImpl implements OMPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgUnitEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dynamicOrgUnitEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dynamicOrgReferenceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass positionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass locationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgNodeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass capabilityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass groupEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass organizationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dynamicOrgIdentifierEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attributeValueEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attributeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass organizationTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgUnitTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass positionTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgUnitFeatureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass multipleFeatureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass positionFeatureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgElementTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgUnitRelationshipEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgUnitRelationshipTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass capabilityCategoryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass locationTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass namedElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgModelEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass enumValueEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass privilegeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass qualifiedOrgElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass authorizableEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass featureEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass orgTypedElementEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass namespaceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass capabilityAssociationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orgMetaModelEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass baseOrgModelEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass modelElementEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass privilegeAssociationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass capableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass qualifiedAssociationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass privilegeCategoryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass allocableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass resourceTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass resourceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass resourceAssociationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass associableWithResourcesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orgQueryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass locatableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass systemActionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass associableWithPrivilegesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum attributeTypeEEnum = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
     * package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory
     * method {@link #init init()}, which also performs initialization of the
     * package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.om.core.om.OMPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private OMPackageImpl() {
        super(eNS_URI, OMFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link OMPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static OMPackage init() {
        if (isInited)
            return (OMPackage) EPackage.Registry.INSTANCE
                    .getEPackage(OMPackage.eNS_URI);

        // Obtain or create and register package
        OMPackageImpl theOMPackage =
                (OMPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OMPackageImpl ? EPackage.Registry.INSTANCE
                        .get(eNS_URI) : new OMPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theOMPackage.createPackageContents();

        // Initialize created meta-data
        theOMPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theOMPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(OMPackage.eNS_URI, theOMPackage);
        return theOMPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgUnit() {
        return orgUnitEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnit_Feature() {
        return (EReference) orgUnitEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnit_OutgoingRelationships() {
        return (EReference) orgUnitEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnit_IncomingRelationships() {
        return (EReference) orgUnitEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDynamicOrgUnit() {
        return dynamicOrgUnitEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDynamicOrgUnit_DynamicOrganization() {
        return (EReference) dynamicOrgUnitEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDynamicOrgReference() {
        return dynamicOrgReferenceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDynamicOrgReference_From() {
        return (EReference) dynamicOrgReferenceEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDynamicOrgReference_To() {
        return (EReference) dynamicOrgReferenceEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnit_Positions() {
        return (EReference) orgUnitEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnit_OrgUnitType() {
        return (EReference) orgUnitEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPosition() {
        return positionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPosition_IdealNumber() {
        return (EAttribute) positionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getPosition_Feature() {
        return (EReference) positionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPosition_PositionType() {
        return (EReference) positionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLocation() {
        return locationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getLocation_LocationType() {
        return (EReference) locationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLocation_Locale() {
        return (EAttribute) locationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLocation_TimeZone() {
        return (EAttribute) locationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgNode() {
        return orgNodeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgElement() {
        return orgElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgElement_Purpose() {
        return (EAttribute) orgElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgElement_StartDate() {
        return (EAttribute) orgElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgElement_EndDate() {
        return (EAttribute) orgElementEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgElement_Description() {
        return (EAttribute) orgElementEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCapability() {
        return capabilityEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCapability_Category() {
        return (EReference) capabilityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getGroup() {
        return groupEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroup_SubGroups() {
        return (EReference) groupEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrganization() {
        return organizationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrganization_Units() {
        return (EReference) organizationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrganization_OrganizationType() {
        return (EReference) organizationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrganization_OrgUnitRelationships() {
        return (EReference) organizationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrganization_Dynamic() {
        return (EAttribute) organizationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrganization_DynamicOrgIdentifiers() {
        return (EReference) organizationEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDynamicOrgIdentifier() {
        return dynamicOrgIdentifierEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAttributeValue() {
        return attributeValueEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttributeValue_Attribute() {
        return (EReference) attributeValueEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeValue_Value() {
        return (EAttribute) attributeValueEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttributeValue_EnumSetValues() {
        return (EReference) attributeValueEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeValue_SetValues() {
        return (EAttribute) attributeValueEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeValue_Type() {
        return (EAttribute) attributeValueEClass.getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAttribute() {
        return attributeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Type() {
        return (EAttribute) attributeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttribute_Values() {
        return (EReference) attributeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttribute_DefaultEnumSetValues() {
        return (EReference) attributeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Description() {
        return (EAttribute) attributeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrganizationType() {
        return organizationTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrganizationType_OrgUnitFeatures() {
        return (EReference) organizationTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_DefaultValue() {
        return (EAttribute) attributeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgUnitType() {
        return orgUnitTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitType_OrgUnitFeatures() {
        return (EReference) orgUnitTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitType_PositionFeatures() {
        return (EReference) orgUnitTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPositionType() {
        return positionTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgUnitFeature() {
        return orgUnitFeatureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitFeature_FeatureType() {
        return (EReference) orgUnitFeatureEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitFeature_ContextRelationshipType() {
        return (EReference) orgUnitFeatureEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getMultipleFeature() {
        return multipleFeatureEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMultipleFeature_LowerBound() {
        return (EAttribute) multipleFeatureEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMultipleFeature_UpperBound() {
        return (EAttribute) multipleFeatureEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPositionFeature() {
        return positionFeatureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPositionFeature_FeatureType() {
        return (EReference) positionFeatureEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgElementType() {
        return orgElementTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgElementType_Attributes() {
        return (EReference) orgElementTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgUnitRelationship() {
        return orgUnitRelationshipEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitRelationship_From() {
        return (EReference) orgUnitRelationshipEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitRelationship_To() {
        return (EReference) orgUnitRelationshipEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitRelationship_RelationshipType() {
        return (EReference) orgUnitRelationshipEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgUnitRelationship_IsHierarchical() {
        return (EAttribute) orgUnitRelationshipEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgUnitRelationshipType() {
        return orgUnitRelationshipTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCapabilityCategory() {
        return capabilityCategoryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getCapabilityCategory_SubCategories() {
        return (EReference) capabilityCategoryEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCapabilityCategory_Members() {
        return (EReference) capabilityCategoryEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLocationType() {
        return locationTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getNamedElement() {
        return namedElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_Name() {
        return (EAttribute) namedElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getNamedElement_Namespace() {
        return (EReference) namedElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_Label() {
        return (EAttribute) namedElementEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_QualifiedName() {
        return (EAttribute) namedElementEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_LabelKey() {
        return (EAttribute) namedElementEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_DisplayName() {
        return (EAttribute) namedElementEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgModel() {
        return orgModelEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_Groups() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_CapabilityCategories() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_Capabilities() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_Organizations() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_Locations() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_Privileges() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_PrivilegeCategories() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_Metamodels() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_Resources() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_EmbeddedMetamodel() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_HumanResourceType() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_ConsumableResourceType() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_DurableResourceType() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_DynamicOrgReferences() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgModel_Queries() {
        return (EReference) orgModelEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnumValue() {
        return enumValueEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumValue_Value() {
        return (EAttribute) enumValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPrivilege() {
        return privilegeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPrivilege_Category() {
        return (EReference) privilegeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getQualifiedOrgElement() {
        return qualifiedOrgElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQualifiedOrgElement_QualifierAttribute() {
        return (EReference) qualifiedOrgElementEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getAuthorizable() {
        return authorizableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAuthorizable_SystemActions() {
        return (EReference) authorizableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getFeature() {
        return featureEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgTypedElement() {
        return orgTypedElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgTypedElement_AttributeValues() {
        return (EReference) orgTypedElementEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgTypedElement_Type() {
        return (EReference) orgTypedElementEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getNamespace() {
        return namespaceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCapabilityAssociation() {
        return capabilityAssociationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCapabilityAssociation_Capability() {
        return (EReference) capabilityAssociationEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgMetaModel() {
        return orgMetaModelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgMetaModel_LocationTypes() {
        return (EReference) orgMetaModelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgMetaModel_OrgUnitRelationshipTypes() {
        return (EReference) orgMetaModelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgMetaModel_OrganizationTypes() {
        return (EReference) orgMetaModelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgMetaModel_OrgUnitTypes() {
        return (EReference) orgMetaModelEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgMetaModel_PositionTypes() {
        return (EReference) orgMetaModelEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgMetaModel_ResourceTypes() {
        return (EReference) orgMetaModelEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgMetaModel_Embedded() {
        return (EAttribute) orgMetaModelEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getBaseOrgModel() {
        return baseOrgModelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseOrgModel_Version() {
        return (EAttribute) baseOrgModelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseOrgModel_Status() {
        return (EAttribute) baseOrgModelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseOrgModel_Author() {
        return (EAttribute) baseOrgModelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseOrgModel_DateCreated() {
        return (EAttribute) baseOrgModelEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getModelElement() {
        return modelElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getModelElement_Id() {
        return (EAttribute) modelElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPrivilegeAssociation() {
        return privilegeAssociationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPrivilegeAssociation_Privilege() {
        return (EReference) privilegeAssociationEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCapable() {
        return capableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCapable_CapabilitiesAssociations() {
        return (EReference) capableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getQualifiedAssociation() {
        return qualifiedAssociationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getQualifiedAssociation_QualifierValue() {
        return (EReference) qualifiedAssociationEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPrivilegeCategory() {
        return privilegeCategoryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPrivilegeCategory_SubCategories() {
        return (EReference) privilegeCategoryEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPrivilegeCategory_Members() {
        return (EReference) privilegeCategoryEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAllocable() {
        return allocableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocable_AllocationMethod() {
        return (EAttribute) allocableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResourceType() {
        return resourceTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_HumanResourceType() {
        return (EAttribute) resourceTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_DurableResourceType() {
        return (EAttribute) resourceTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResourceType_ConsumableResourceType() {
        return (EAttribute) resourceTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResource() {
        return resourceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResource_ResourceType() {
        return (EReference) resourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_Dn() {
        return (EAttribute) resourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResourceAssociation() {
        return resourceAssociationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResourceAssociation_Resource() {
        return (EReference) resourceAssociationEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAssociableWithResources() {
        return associableWithResourcesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssociableWithResources_ResourceAssociation() {
        return (EReference) associableWithResourcesEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgQuery() {
        return orgQueryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgQuery_Grammar() {
        return (EAttribute) orgQueryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgQuery_Query() {
        return (EAttribute) orgQueryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLocatable() {
        return locatableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getLocatable_Location() {
        return (EReference) locatableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSystemAction() {
        return systemActionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSystemAction_ActionId() {
        return (EAttribute) systemActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSystemAction_Component() {
        return (EAttribute) systemActionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAssociableWithPrivileges() {
        return associableWithPrivilegesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssociableWithPrivileges_PrivilegeAssociations() {
        return (EReference) associableWithPrivilegesEClass
                .getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAttributeType() {
        return attributeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OMFactory getOMFactory() {
        return (OMFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated)
            return;
        isCreated = true;

        // Create classes and their features
        orgUnitEClass = createEClass(ORG_UNIT);
        createEReference(orgUnitEClass, ORG_UNIT__FEATURE);
        createEReference(orgUnitEClass, ORG_UNIT__POSITIONS);
        createEReference(orgUnitEClass, ORG_UNIT__ORG_UNIT_TYPE);
        createEReference(orgUnitEClass, ORG_UNIT__OUTGOING_RELATIONSHIPS);
        createEReference(orgUnitEClass, ORG_UNIT__INCOMING_RELATIONSHIPS);

        dynamicOrgUnitEClass = createEClass(DYNAMIC_ORG_UNIT);
        createEReference(dynamicOrgUnitEClass,
                DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION);

        dynamicOrgReferenceEClass = createEClass(DYNAMIC_ORG_REFERENCE);
        createEReference(dynamicOrgReferenceEClass, DYNAMIC_ORG_REFERENCE__FROM);
        createEReference(dynamicOrgReferenceEClass, DYNAMIC_ORG_REFERENCE__TO);

        positionEClass = createEClass(POSITION);
        createEAttribute(positionEClass, POSITION__IDEAL_NUMBER);
        createEReference(positionEClass, POSITION__FEATURE);
        createEReference(positionEClass, POSITION__POSITION_TYPE);

        locationEClass = createEClass(LOCATION);
        createEReference(locationEClass, LOCATION__LOCATION_TYPE);
        createEAttribute(locationEClass, LOCATION__LOCALE);
        createEAttribute(locationEClass, LOCATION__TIME_ZONE);

        orgNodeEClass = createEClass(ORG_NODE);

        orgElementEClass = createEClass(ORG_ELEMENT);
        createEAttribute(orgElementEClass, ORG_ELEMENT__PURPOSE);
        createEAttribute(orgElementEClass, ORG_ELEMENT__START_DATE);
        createEAttribute(orgElementEClass, ORG_ELEMENT__END_DATE);
        createEAttribute(orgElementEClass, ORG_ELEMENT__DESCRIPTION);

        capabilityEClass = createEClass(CAPABILITY);
        createEReference(capabilityEClass, CAPABILITY__CATEGORY);

        groupEClass = createEClass(GROUP);
        createEReference(groupEClass, GROUP__SUB_GROUPS);

        organizationEClass = createEClass(ORGANIZATION);
        createEReference(organizationEClass, ORGANIZATION__UNITS);
        createEReference(organizationEClass, ORGANIZATION__ORGANIZATION_TYPE);
        createEReference(organizationEClass,
                ORGANIZATION__ORG_UNIT_RELATIONSHIPS);
        createEAttribute(organizationEClass, ORGANIZATION__DYNAMIC);
        createEReference(organizationEClass,
                ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS);

        dynamicOrgIdentifierEClass = createEClass(DYNAMIC_ORG_IDENTIFIER);

        attributeValueEClass = createEClass(ATTRIBUTE_VALUE);
        createEReference(attributeValueEClass, ATTRIBUTE_VALUE__ATTRIBUTE);
        createEAttribute(attributeValueEClass, ATTRIBUTE_VALUE__VALUE);
        createEReference(attributeValueEClass, ATTRIBUTE_VALUE__ENUM_SET_VALUES);
        createEAttribute(attributeValueEClass, ATTRIBUTE_VALUE__SET_VALUES);
        createEAttribute(attributeValueEClass, ATTRIBUTE_VALUE__TYPE);

        attributeEClass = createEClass(ATTRIBUTE);
        createEAttribute(attributeEClass, ATTRIBUTE__TYPE);
        createEReference(attributeEClass, ATTRIBUTE__VALUES);
        createEAttribute(attributeEClass, ATTRIBUTE__DEFAULT_VALUE);
        createEReference(attributeEClass, ATTRIBUTE__DEFAULT_ENUM_SET_VALUES);
        createEAttribute(attributeEClass, ATTRIBUTE__DESCRIPTION);

        organizationTypeEClass = createEClass(ORGANIZATION_TYPE);
        createEReference(organizationTypeEClass,
                ORGANIZATION_TYPE__ORG_UNIT_FEATURES);

        orgUnitTypeEClass = createEClass(ORG_UNIT_TYPE);
        createEReference(orgUnitTypeEClass, ORG_UNIT_TYPE__ORG_UNIT_FEATURES);
        createEReference(orgUnitTypeEClass, ORG_UNIT_TYPE__POSITION_FEATURES);

        positionTypeEClass = createEClass(POSITION_TYPE);

        orgUnitFeatureEClass = createEClass(ORG_UNIT_FEATURE);
        createEReference(orgUnitFeatureEClass, ORG_UNIT_FEATURE__FEATURE_TYPE);
        createEReference(orgUnitFeatureEClass,
                ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE);

        multipleFeatureEClass = createEClass(MULTIPLE_FEATURE);
        createEAttribute(multipleFeatureEClass, MULTIPLE_FEATURE__LOWER_BOUND);
        createEAttribute(multipleFeatureEClass, MULTIPLE_FEATURE__UPPER_BOUND);

        positionFeatureEClass = createEClass(POSITION_FEATURE);
        createEReference(positionFeatureEClass, POSITION_FEATURE__FEATURE_TYPE);

        orgElementTypeEClass = createEClass(ORG_ELEMENT_TYPE);
        createEReference(orgElementTypeEClass, ORG_ELEMENT_TYPE__ATTRIBUTES);

        orgUnitRelationshipEClass = createEClass(ORG_UNIT_RELATIONSHIP);
        createEReference(orgUnitRelationshipEClass, ORG_UNIT_RELATIONSHIP__FROM);
        createEReference(orgUnitRelationshipEClass, ORG_UNIT_RELATIONSHIP__TO);
        createEReference(orgUnitRelationshipEClass,
                ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE);
        createEAttribute(orgUnitRelationshipEClass,
                ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL);

        orgUnitRelationshipTypeEClass =
                createEClass(ORG_UNIT_RELATIONSHIP_TYPE);

        capabilityCategoryEClass = createEClass(CAPABILITY_CATEGORY);
        createEReference(capabilityCategoryEClass,
                CAPABILITY_CATEGORY__SUB_CATEGORIES);
        createEReference(capabilityCategoryEClass, CAPABILITY_CATEGORY__MEMBERS);

        locationTypeEClass = createEClass(LOCATION_TYPE);

        namedElementEClass = createEClass(NAMED_ELEMENT);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);
        createEReference(namedElementEClass, NAMED_ELEMENT__NAMESPACE);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__LABEL);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__QUALIFIED_NAME);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__LABEL_KEY);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__DISPLAY_NAME);

        orgModelEClass = createEClass(ORG_MODEL);
        createEReference(orgModelEClass, ORG_MODEL__GROUPS);
        createEReference(orgModelEClass, ORG_MODEL__CAPABILITY_CATEGORIES);
        createEReference(orgModelEClass, ORG_MODEL__CAPABILITIES);
        createEReference(orgModelEClass, ORG_MODEL__ORGANIZATIONS);
        createEReference(orgModelEClass, ORG_MODEL__LOCATIONS);
        createEReference(orgModelEClass, ORG_MODEL__PRIVILEGES);
        createEReference(orgModelEClass, ORG_MODEL__PRIVILEGE_CATEGORIES);
        createEReference(orgModelEClass, ORG_MODEL__METAMODELS);
        createEReference(orgModelEClass, ORG_MODEL__RESOURCES);
        createEReference(orgModelEClass, ORG_MODEL__EMBEDDED_METAMODEL);
        createEReference(orgModelEClass, ORG_MODEL__QUERIES);
        createEReference(orgModelEClass, ORG_MODEL__HUMAN_RESOURCE_TYPE);
        createEReference(orgModelEClass, ORG_MODEL__CONSUMABLE_RESOURCE_TYPE);
        createEReference(orgModelEClass, ORG_MODEL__DURABLE_RESOURCE_TYPE);
        createEReference(orgModelEClass, ORG_MODEL__DYNAMIC_ORG_REFERENCES);

        enumValueEClass = createEClass(ENUM_VALUE);
        createEAttribute(enumValueEClass, ENUM_VALUE__VALUE);

        privilegeEClass = createEClass(PRIVILEGE);
        createEReference(privilegeEClass, PRIVILEGE__CATEGORY);

        qualifiedOrgElementEClass = createEClass(QUALIFIED_ORG_ELEMENT);
        createEReference(qualifiedOrgElementEClass,
                QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE);

        authorizableEClass = createEClass(AUTHORIZABLE);
        createEReference(authorizableEClass, AUTHORIZABLE__SYSTEM_ACTIONS);

        featureEClass = createEClass(FEATURE);

        orgTypedElementEClass = createEClass(ORG_TYPED_ELEMENT);
        createEReference(orgTypedElementEClass,
                ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES);
        createEReference(orgTypedElementEClass, ORG_TYPED_ELEMENT__TYPE);

        namespaceEClass = createEClass(NAMESPACE);

        capabilityAssociationEClass = createEClass(CAPABILITY_ASSOCIATION);
        createEReference(capabilityAssociationEClass,
                CAPABILITY_ASSOCIATION__CAPABILITY);

        orgMetaModelEClass = createEClass(ORG_META_MODEL);
        createEReference(orgMetaModelEClass, ORG_META_MODEL__LOCATION_TYPES);
        createEReference(orgMetaModelEClass,
                ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES);
        createEReference(orgMetaModelEClass, ORG_META_MODEL__ORGANIZATION_TYPES);
        createEReference(orgMetaModelEClass, ORG_META_MODEL__ORG_UNIT_TYPES);
        createEReference(orgMetaModelEClass, ORG_META_MODEL__POSITION_TYPES);
        createEReference(orgMetaModelEClass, ORG_META_MODEL__RESOURCE_TYPES);
        createEAttribute(orgMetaModelEClass, ORG_META_MODEL__EMBEDDED);

        baseOrgModelEClass = createEClass(BASE_ORG_MODEL);
        createEAttribute(baseOrgModelEClass, BASE_ORG_MODEL__VERSION);
        createEAttribute(baseOrgModelEClass, BASE_ORG_MODEL__STATUS);
        createEAttribute(baseOrgModelEClass, BASE_ORG_MODEL__AUTHOR);
        createEAttribute(baseOrgModelEClass, BASE_ORG_MODEL__DATE_CREATED);

        modelElementEClass = createEClass(MODEL_ELEMENT);
        createEAttribute(modelElementEClass, MODEL_ELEMENT__ID);

        privilegeAssociationEClass = createEClass(PRIVILEGE_ASSOCIATION);
        createEReference(privilegeAssociationEClass,
                PRIVILEGE_ASSOCIATION__PRIVILEGE);

        capableEClass = createEClass(CAPABLE);
        createEReference(capableEClass, CAPABLE__CAPABILITIES_ASSOCIATIONS);

        qualifiedAssociationEClass = createEClass(QUALIFIED_ASSOCIATION);
        createEReference(qualifiedAssociationEClass,
                QUALIFIED_ASSOCIATION__QUALIFIER_VALUE);

        privilegeCategoryEClass = createEClass(PRIVILEGE_CATEGORY);
        createEReference(privilegeCategoryEClass,
                PRIVILEGE_CATEGORY__SUB_CATEGORIES);
        createEReference(privilegeCategoryEClass, PRIVILEGE_CATEGORY__MEMBERS);

        allocableEClass = createEClass(ALLOCABLE);
        createEAttribute(allocableEClass, ALLOCABLE__ALLOCATION_METHOD);

        resourceTypeEClass = createEClass(RESOURCE_TYPE);
        createEAttribute(resourceTypeEClass, RESOURCE_TYPE__HUMAN_RESOURCE_TYPE);
        createEAttribute(resourceTypeEClass,
                RESOURCE_TYPE__DURABLE_RESOURCE_TYPE);
        createEAttribute(resourceTypeEClass,
                RESOURCE_TYPE__CONSUMABLE_RESOURCE_TYPE);

        resourceEClass = createEClass(RESOURCE);
        createEReference(resourceEClass, RESOURCE__RESOURCE_TYPE);
        createEAttribute(resourceEClass, RESOURCE__DN);

        resourceAssociationEClass = createEClass(RESOURCE_ASSOCIATION);
        createEReference(resourceAssociationEClass,
                RESOURCE_ASSOCIATION__RESOURCE);

        associableWithResourcesEClass = createEClass(ASSOCIABLE_WITH_RESOURCES);
        createEReference(associableWithResourcesEClass,
                ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION);

        orgQueryEClass = createEClass(ORG_QUERY);
        createEAttribute(orgQueryEClass, ORG_QUERY__GRAMMAR);
        createEAttribute(orgQueryEClass, ORG_QUERY__QUERY);

        locatableEClass = createEClass(LOCATABLE);
        createEReference(locatableEClass, LOCATABLE__LOCATION);

        systemActionEClass = createEClass(SYSTEM_ACTION);
        createEAttribute(systemActionEClass, SYSTEM_ACTION__ACTION_ID);
        createEAttribute(systemActionEClass, SYSTEM_ACTION__COMPONENT);

        associableWithPrivilegesEClass =
                createEClass(ASSOCIABLE_WITH_PRIVILEGES);
        createEReference(associableWithPrivilegesEClass,
                ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS);

        // Create enums
        attributeTypeEEnum = createEEnum(ATTRIBUTE_TYPE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This
     * method is guarded to have no affect on any invocation but its first. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized)
            return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        orgUnitEClass.getESuperTypes().add(this.getOrgTypedElement());
        orgUnitEClass.getESuperTypes().add(this.getAuthorizable());
        orgUnitEClass.getESuperTypes().add(this.getAllocable());
        orgUnitEClass.getESuperTypes().add(this.getLocatable());
        orgUnitEClass.getESuperTypes().add(this.getOrgNode());
        dynamicOrgUnitEClass.getESuperTypes().add(this.getOrgUnit());
        positionEClass.getESuperTypes().add(this.getOrgTypedElement());
        positionEClass.getESuperTypes().add(this.getAuthorizable());
        positionEClass.getESuperTypes().add(this.getCapable());
        positionEClass.getESuperTypes().add(this.getAllocable());
        positionEClass.getESuperTypes().add(this.getAssociableWithResources());
        positionEClass.getESuperTypes().add(this.getLocatable());
        positionEClass.getESuperTypes().add(this.getOrgNode());
        locationEClass.getESuperTypes().add(this.getOrgTypedElement());
        locationEClass.getESuperTypes().add(this.getAllocable());
        orgElementEClass.getESuperTypes().add(this.getNamespace());
        capabilityEClass.getESuperTypes().add(this.getQualifiedOrgElement());
        groupEClass.getESuperTypes().add(this.getOrgElement());
        groupEClass.getESuperTypes().add(this.getCapable());
        groupEClass.getESuperTypes().add(this.getAllocable());
        groupEClass.getESuperTypes().add(this.getAuthorizable());
        groupEClass.getESuperTypes().add(this.getAssociableWithResources());
        organizationEClass.getESuperTypes().add(this.getOrgTypedElement());
        organizationEClass.getESuperTypes().add(this.getAllocable());
        organizationEClass.getESuperTypes().add(this.getLocatable());
        organizationEClass.getESuperTypes().add(this.getOrgNode());
        dynamicOrgIdentifierEClass.getESuperTypes().add(this.getNamespace());
        attributeEClass.getESuperTypes().add(this.getFeature());
        organizationTypeEClass.getESuperTypes().add(this.getOrgElementType());
        orgUnitTypeEClass.getESuperTypes().add(this.getOrgElementType());
        positionTypeEClass.getESuperTypes().add(this.getOrgElementType());
        orgUnitFeatureEClass.getESuperTypes().add(this.getMultipleFeature());
        multipleFeatureEClass.getESuperTypes().add(this.getFeature());
        multipleFeatureEClass.getESuperTypes().add(this.getOrgElement());
        positionFeatureEClass.getESuperTypes().add(this.getMultipleFeature());
        orgElementTypeEClass.getESuperTypes().add(this.getNamespace());
        orgUnitRelationshipEClass.getESuperTypes()
                .add(this.getOrgTypedElement());
        orgUnitRelationshipTypeEClass.getESuperTypes()
                .add(this.getOrgElementType());
        capabilityCategoryEClass.getESuperTypes().add(this.getNamespace());
        locationTypeEClass.getESuperTypes().add(this.getOrgElementType());
        namedElementEClass.getESuperTypes().add(this.getModelElement());
        orgModelEClass.getESuperTypes().add(this.getBaseOrgModel());
        orgModelEClass.getESuperTypes().add(this.getAuthorizable());
        privilegeEClass.getESuperTypes().add(this.getQualifiedOrgElement());
        qualifiedOrgElementEClass.getESuperTypes().add(this.getOrgElement());
        authorizableEClass.getESuperTypes()
                .add(this.getAssociableWithPrivileges());
        featureEClass.getESuperTypes().add(this.getNamespace());
        orgTypedElementEClass.getESuperTypes().add(this.getOrgElement());
        namespaceEClass.getESuperTypes().add(this.getNamedElement());
        capabilityAssociationEClass.getESuperTypes()
                .add(this.getQualifiedAssociation());
        orgMetaModelEClass.getESuperTypes().add(this.getBaseOrgModel());
        baseOrgModelEClass.getESuperTypes().add(this.getNamespace());
        privilegeAssociationEClass.getESuperTypes()
                .add(this.getQualifiedAssociation());
        privilegeCategoryEClass.getESuperTypes().add(this.getNamespace());
        resourceTypeEClass.getESuperTypes().add(this.getOrgElementType());
        resourceEClass.getESuperTypes().add(this.getOrgTypedElement());
        orgQueryEClass.getESuperTypes().add(this.getNamespace());
        systemActionEClass.getESuperTypes()
                .add(this.getAssociableWithPrivileges());
        systemActionEClass.getESuperTypes().add(this.getModelElement());

        // Initialize classes and features; add operations and parameters
        initEClass(orgUnitEClass,
                OrgUnit.class,
                "OrgUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrgUnit_Feature(),
                this.getOrgUnitFeature(),
                null,
                "feature", null, 0, 1, OrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgUnit_Positions(),
                this.getPosition(),
                null,
                "positions", null, 0, -1, OrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgUnit_OrgUnitType(),
                this.getOrgUnitType(),
                null,
                "orgUnitType", null, 0, 1, OrgUnit.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgUnit_OutgoingRelationships(),
                this.getOrgUnitRelationship(),
                this.getOrgUnitRelationship_From(),
                "outgoingRelationships", null, 0, -1, OrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgUnit_IncomingRelationships(),
                this.getOrgUnitRelationship(),
                this.getOrgUnitRelationship_To(),
                "incomingRelationships", null, 0, -1, OrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        addEOperation(orgUnitEClass,
                this.getOrgUnit(),
                "getSubUnits", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        addEOperation(orgUnitEClass,
                this.getOrgUnit(),
                "getParentOrgUnit", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        addEOperation(orgUnitEClass,
                this.getOrgUnitRelationship(),
                "getOutgoingHierachicalRelationships", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        addEOperation(orgUnitEClass,
                this.getOrgUnitRelationship(),
                "getOutgoingNonHierachicalRelationships", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        addEOperation(orgUnitEClass,
                this.getOrgUnitRelationship(),
                "getIncomingHierachicalRelationship", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        addEOperation(orgUnitEClass,
                this.getOrgUnitRelationship(),
                "getIncomingNonHierachicalRelationships", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        addEOperation(orgUnitEClass,
                this.getOrganization(),
                "getOrganization", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        initEClass(dynamicOrgUnitEClass,
                DynamicOrgUnit.class,
                "DynamicOrgUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getDynamicOrgUnit_DynamicOrganization(),
                this.getDynamicOrgReference(),
                null,
                "dynamicOrganization", null, 0, 1, DynamicOrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(dynamicOrgReferenceEClass,
                DynamicOrgReference.class,
                "DynamicOrgReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getDynamicOrgReference_From(),
                this.getDynamicOrgUnit(),
                null,
                "from", null, 0, 1, DynamicOrgReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDynamicOrgReference_To(),
                this.getOrganization(),
                null,
                "to", null, 1, 1, DynamicOrgReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(positionEClass,
                Position.class,
                "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getPosition_IdealNumber(),
                ecorePackage.getEInt(),
                "idealNumber", "1", 1, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEReference(getPosition_Feature(),
                this.getPositionFeature(),
                null,
                "feature", null, 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getPosition_PositionType(),
                this.getPositionType(),
                null,
                "positionType", null, 0, 1, Position.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(locationEClass,
                Location.class,
                "Location", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getLocation_LocationType(),
                this.getLocationType(),
                null,
                "locationType", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getLocation_Locale(),
                ecorePackage.getEString(),
                "locale", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getLocation_TimeZone(),
                ecorePackage.getEString(),
                "timeZone", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(orgNodeEClass,
                OrgNode.class,
                "OrgNode", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(orgElementEClass,
                OrgElement.class,
                "OrgElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getOrgElement_Purpose(),
                ecorePackage.getEString(),
                "purpose", null, 0, 1, OrgElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getOrgElement_StartDate(),
                ecorePackage.getEDate(),
                "startDate", null, 0, 1, OrgElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getOrgElement_EndDate(),
                ecorePackage.getEDate(),
                "endDate", null, 0, 1, OrgElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getOrgElement_Description(),
                ecorePackage.getEString(),
                "description", null, 0, 1, OrgElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(capabilityEClass,
                Capability.class,
                "Capability", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getCapability_Category(),
                this.getCapabilityCategory(),
                this.getCapabilityCategory_Members(),
                "category", null, 0, 1, Capability.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(groupEClass,
                Group.class,
                "Group", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getGroup_SubGroups(),
                this.getGroup(),
                null,
                "subGroups", null, 0, -1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(organizationEClass,
                Organization.class,
                "Organization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrganization_Units(),
                this.getOrgUnit(),
                null,
                "units", null, 0, -1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrganization_OrganizationType(),
                this.getOrganizationType(),
                null,
                "organizationType", null, 0, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrganization_OrgUnitRelationships(),
                this.getOrgUnitRelationship(),
                null,
                "orgUnitRelationships", null, 0, -1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getOrganization_Dynamic(),
                ecorePackage.getEBoolean(),
                "dynamic", null, 0, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrganization_DynamicOrgIdentifiers(),
                this.getDynamicOrgIdentifier(),
                null,
                "dynamicOrgIdentifiers", null, 0, -1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        addEOperation(organizationEClass,
                this.getOrgUnit(),
                "getSubUnits", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        initEClass(dynamicOrgIdentifierEClass,
                DynamicOrgIdentifier.class,
                "DynamicOrgIdentifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(attributeValueEClass,
                AttributeValue.class,
                "AttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getAttributeValue_Attribute(),
                this.getAttribute(),
                null,
                "attribute", null, 1, 1, AttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getAttributeValue_Value(),
                ecorePackage.getEString(),
                "value", null, 0, 1, AttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getAttributeValue_EnumSetValues(),
                this.getEnumValue(),
                null,
                "enumSetValues", null, 0, -1, AttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getAttributeValue_SetValues(),
                ecorePackage.getEString(),
                "setValues", null, 0, -1, AttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getAttributeValue_Type(),
                this.getAttributeType(),
                "type", "", 0, 1, AttributeValue.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(attributeEClass,
                Attribute.class,
                "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getAttribute_Type(),
                this.getAttributeType(),
                "type", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getAttribute_Values(),
                this.getEnumValue(),
                null,
                "values", null, 0, -1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getAttribute_DefaultValue(),
                ecorePackage.getEString(),
                "defaultValue", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getAttribute_DefaultEnumSetValues(),
                this.getEnumValue(),
                null,
                "defaultEnumSetValues", null, 0, -1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getAttribute_Description(),
                ecorePackage.getEString(),
                "description", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(organizationTypeEClass,
                OrganizationType.class,
                "OrganizationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrganizationType_OrgUnitFeatures(),
                this.getOrgUnitFeature(),
                null,
                "orgUnitFeatures", null, 0, -1, OrganizationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(orgUnitTypeEClass,
                OrgUnitType.class,
                "OrgUnitType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrgUnitType_OrgUnitFeatures(),
                this.getOrgUnitFeature(),
                null,
                "orgUnitFeatures", null, 0, -1, OrgUnitType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgUnitType_PositionFeatures(),
                this.getPositionFeature(),
                null,
                "positionFeatures", null, 0, -1, OrgUnitType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(positionTypeEClass,
                PositionType.class,
                "PositionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(orgUnitFeatureEClass,
                OrgUnitFeature.class,
                "OrgUnitFeature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrgUnitFeature_FeatureType(),
                this.getOrgUnitType(),
                null,
                "featureType", null, 1, 1, OrgUnitFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgUnitFeature_ContextRelationshipType(),
                this.getOrgUnitRelationshipType(),
                null,
                "contextRelationshipType", null, 0, 1, OrgUnitFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(multipleFeatureEClass,
                MultipleFeature.class,
                "MultipleFeature", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getMultipleFeature_LowerBound(),
                ecorePackage.getEInt(),
                "lowerBound", "0", 0, 1, MultipleFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getMultipleFeature_UpperBound(),
                ecorePackage.getEInt(),
                "upperBound", "1", 0, 1, MultipleFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(positionFeatureEClass,
                PositionFeature.class,
                "PositionFeature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getPositionFeature_FeatureType(),
                this.getPositionType(),
                null,
                "featureType", null, 1, 1, PositionFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(orgElementTypeEClass,
                OrgElementType.class,
                "OrgElementType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrgElementType_Attributes(),
                this.getAttribute(),
                null,
                "attributes", null, 0, -1, OrgElementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(orgUnitRelationshipEClass,
                OrgUnitRelationship.class,
                "OrgUnitRelationship", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrgUnitRelationship_From(),
                this.getOrgUnit(),
                this.getOrgUnit_OutgoingRelationships(),
                "from", null, 1, 1, OrgUnitRelationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgUnitRelationship_To(),
                this.getOrgUnit(),
                this.getOrgUnit_IncomingRelationships(),
                "to", null, 1, 1, OrgUnitRelationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgUnitRelationship_RelationshipType(),
                this.getOrgUnitRelationshipType(),
                null,
                "relationshipType", null, 0, 1, OrgUnitRelationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getOrgUnitRelationship_IsHierarchical(),
                ecorePackage.getEBoolean(),
                "isHierarchical", "false", 0, 1, OrgUnitRelationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(orgUnitRelationshipTypeEClass,
                OrgUnitRelationshipType.class,
                "OrgUnitRelationshipType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(capabilityCategoryEClass,
                CapabilityCategory.class,
                "CapabilityCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getCapabilityCategory_SubCategories(),
                this.getCapabilityCategory(),
                null,
                "subCategories", null, 0, -1, CapabilityCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getCapabilityCategory_Members(),
                this.getCapability(),
                this.getCapability_Category(),
                "members", null, 0, -1, CapabilityCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(locationTypeEClass,
                LocationType.class,
                "LocationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(namedElementEClass,
                NamedElement.class,
                "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getNamedElement_Name(),
                ecorePackage.getEString(),
                "name", "", 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEReference(getNamedElement_Namespace(),
                this.getNamespace(),
                null,
                "namespace", null, 0, 1, NamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getNamedElement_Label(),
                ecorePackage.getEString(),
                "label", null, 0, 1, NamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getNamedElement_QualifiedName(),
                ecorePackage.getEString(),
                "qualifiedName", "", 0, 1, NamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getNamedElement_LabelKey(),
                ecorePackage.getEString(),
                "labelKey", null, 0, 1, NamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getNamedElement_DisplayName(),
                ecorePackage.getEString(),
                "displayName", "", 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        EOperation op =
                addEOperation(namedElementEClass,
                        ecorePackage.getEString(),
                        "getLabel", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
        addEParameter(op,
                ecorePackage.getEBoolean(),
                "localize", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

        initEClass(orgModelEClass,
                OrgModel.class,
                "OrgModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrgModel_Groups(),
                this.getGroup(),
                null,
                "groups", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_CapabilityCategories(),
                this.getCapabilityCategory(),
                null,
                "capabilityCategories", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_Capabilities(),
                this.getCapability(),
                null,
                "capabilities", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_Organizations(),
                this.getOrganization(),
                null,
                "organizations", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_Locations(),
                this.getLocation(),
                null,
                "locations", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_Privileges(),
                this.getPrivilege(),
                null,
                "privileges", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_PrivilegeCategories(),
                this.getPrivilegeCategory(),
                null,
                "privilegeCategories", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_Metamodels(),
                this.getOrgMetaModel(),
                null,
                "metamodels", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_Resources(),
                this.getResource(),
                null,
                "resources", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_EmbeddedMetamodel(),
                this.getOrgMetaModel(),
                null,
                "embeddedMetamodel", null, 1, 1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_Queries(),
                this.getOrgQuery(),
                null,
                "queries", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_HumanResourceType(),
                this.getResourceType(),
                null,
                "humanResourceType", null, 0, 1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_ConsumableResourceType(),
                this.getResourceType(),
                null,
                "consumableResourceType", null, 0, 1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_DurableResourceType(),
                this.getResourceType(),
                null,
                "durableResourceType", null, 0, 1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgModel_DynamicOrgReferences(),
                this.getDynamicOrgReference(),
                null,
                "dynamicOrgReferences", null, 0, -1, OrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(enumValueEClass,
                EnumValue.class,
                "EnumValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getEnumValue_Value(),
                ecorePackage.getEString(),
                "value", "", 0, 1, EnumValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(privilegeEClass,
                Privilege.class,
                "Privilege", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getPrivilege_Category(),
                this.getPrivilegeCategory(),
                this.getPrivilegeCategory_Members(),
                "category", null, 0, 1, Privilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(qualifiedOrgElementEClass,
                QualifiedOrgElement.class,
                "QualifiedOrgElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getQualifiedOrgElement_QualifierAttribute(),
                this.getAttribute(),
                null,
                "qualifierAttribute", null, 0, 1, QualifiedOrgElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(authorizableEClass,
                Authorizable.class,
                "Authorizable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getAuthorizable_SystemActions(),
                this.getSystemAction(),
                null,
                "systemActions", null, 0, -1, Authorizable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(featureEClass,
                Feature.class,
                "Feature", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(orgTypedElementEClass,
                OrgTypedElement.class,
                "OrgTypedElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrgTypedElement_AttributeValues(),
                this.getAttributeValue(),
                null,
                "attributeValues", null, 0, -1, OrgTypedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgTypedElement_Type(),
                this.getOrgElementType(),
                null,
                "type", null, 0, 1, OrgTypedElement.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(namespaceEClass,
                Namespace.class,
                "Namespace", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(capabilityAssociationEClass,
                CapabilityAssociation.class,
                "CapabilityAssociation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getCapabilityAssociation_Capability(),
                this.getCapability(),
                null,
                "capability", null, 1, 1, CapabilityAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(orgMetaModelEClass,
                OrgMetaModel.class,
                "OrgMetaModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getOrgMetaModel_LocationTypes(),
                this.getLocationType(),
                null,
                "locationTypes", null, 0, -1, OrgMetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgMetaModel_OrgUnitRelationshipTypes(),
                this.getOrgUnitRelationshipType(),
                null,
                "orgUnitRelationshipTypes", null, 0, -1, OrgMetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgMetaModel_OrganizationTypes(),
                this.getOrganizationType(),
                null,
                "organizationTypes", null, 0, -1, OrgMetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgMetaModel_OrgUnitTypes(),
                this.getOrgUnitType(),
                null,
                "orgUnitTypes", null, 0, -1, OrgMetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgMetaModel_PositionTypes(),
                this.getPositionType(),
                null,
                "positionTypes", null, 0, -1, OrgMetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOrgMetaModel_ResourceTypes(),
                this.getResourceType(),
                null,
                "resourceTypes", null, 0, -1, OrgMetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getOrgMetaModel_Embedded(),
                ecorePackage.getEBoolean(),
                "embedded", "false", 1, 1, OrgMetaModel.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(baseOrgModelEClass,
                BaseOrgModel.class,
                "BaseOrgModel", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getBaseOrgModel_Version(),
                ecorePackage.getEString(),
                "version", null, 0, 1, BaseOrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getBaseOrgModel_Status(),
                ecorePackage.getEString(),
                "status", null, 0, 1, BaseOrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getBaseOrgModel_Author(),
                ecorePackage.getEString(),
                "author", null, 0, 1, BaseOrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getBaseOrgModel_DateCreated(),
                ecorePackage.getELong(),
                "dateCreated", null, 0, 1, BaseOrgModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(modelElementEClass,
                ModelElement.class,
                "ModelElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getModelElement_Id(),
                ecorePackage.getEString(),
                "id", "", 0, 1, ModelElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(privilegeAssociationEClass,
                PrivilegeAssociation.class,
                "PrivilegeAssociation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getPrivilegeAssociation_Privilege(),
                this.getPrivilege(),
                null,
                "privilege", null, 1, 1, PrivilegeAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(capableEClass,
                Capable.class,
                "Capable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getCapable_CapabilitiesAssociations(),
                this.getCapabilityAssociation(),
                null,
                "capabilitiesAssociations", null, 0, -1, Capable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(qualifiedAssociationEClass,
                QualifiedAssociation.class,
                "QualifiedAssociation", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getQualifiedAssociation_QualifierValue(),
                this.getAttributeValue(),
                null,
                "qualifierValue", null, 0, 1, QualifiedAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(privilegeCategoryEClass,
                PrivilegeCategory.class,
                "PrivilegeCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getPrivilegeCategory_SubCategories(),
                this.getPrivilegeCategory(),
                null,
                "subCategories", null, 0, -1, PrivilegeCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getPrivilegeCategory_Members(),
                this.getPrivilege(),
                this.getPrivilege_Category(),
                "members", null, 0, -1, PrivilegeCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(allocableEClass,
                Allocable.class,
                "Allocable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getAllocable_AllocationMethod(),
                ecorePackage.getEString(),
                "allocationMethod", "ANY", 1, 1, Allocable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(resourceTypeEClass,
                ResourceType.class,
                "ResourceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getResourceType_HumanResourceType(),
                ecorePackage.getEBoolean(),
                "humanResourceType", "false", 1, 1, ResourceType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getResourceType_DurableResourceType(),
                ecorePackage.getEBoolean(),
                "durableResourceType", "false", 1, 1, ResourceType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getResourceType_ConsumableResourceType(),
                ecorePackage.getEBoolean(),
                "consumableResourceType", "false", 1, 1, ResourceType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(resourceEClass,
                Resource.class,
                "Resource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getResource_ResourceType(),
                this.getResourceType(),
                null,
                "resourceType", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getResource_Dn(),
                ecorePackage.getEString(),
                "dn", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(resourceAssociationEClass,
                ResourceAssociation.class,
                "ResourceAssociation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getResourceAssociation_Resource(),
                this.getResource(),
                null,
                "resource", null, 1, 1, ResourceAssociation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(associableWithResourcesEClass,
                AssociableWithResources.class,
                "AssociableWithResources", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getAssociableWithResources_ResourceAssociation(),
                this.getResourceAssociation(),
                null,
                "resourceAssociation", null, 0, -1, AssociableWithResources.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(orgQueryEClass,
                OrgQuery.class,
                "OrgQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getOrgQuery_Grammar(),
                ecorePackage.getEString(),
                "grammar", null, 0, 1, OrgQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getOrgQuery_Query(),
                ecorePackage.getEString(),
                "query", null, 0, 1, OrgQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(locatableEClass,
                Locatable.class,
                "Locatable", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getLocatable_Location(),
                this.getLocation(),
                null,
                "location", null, 0, 1, Locatable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(systemActionEClass,
                SystemAction.class,
                "SystemAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getSystemAction_ActionId(),
                ecorePackage.getEString(),
                "actionId", null, 0, 1, SystemAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSystemAction_Component(),
                ecorePackage.getEString(),
                "component", null, 0, 1, SystemAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(associableWithPrivilegesEClass,
                AssociableWithPrivileges.class,
                "AssociableWithPrivileges", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getAssociableWithPrivileges_PrivilegeAssociations(),
                this.getPrivilegeAssociation(),
                null,
                "privilegeAssociations", null, 0, -1, AssociableWithPrivileges.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        // Initialize enums and add enum literals
        initEEnum(attributeTypeEEnum, AttributeType.class, "AttributeType"); //$NON-NLS-1$
        addEEnumLiteral(attributeTypeEEnum, AttributeType.TEXT);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.INTEGER);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.BOOLEAN);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.DECIMAL);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.DATE_TIME);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.DATE);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.TIME);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.ENUM);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.ENUM_SET);
        addEEnumLiteral(attributeTypeEEnum, AttributeType.SET);

        // Create resource
        createResource(eNS_URI);
    }

} // OMPackageImpl
