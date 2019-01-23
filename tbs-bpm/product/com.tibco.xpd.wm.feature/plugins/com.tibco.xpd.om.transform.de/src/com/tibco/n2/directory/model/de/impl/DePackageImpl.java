/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.AllocationMethod;
import com.tibco.n2.directory.model.de.Attribute;
import com.tibco.n2.directory.model.de.AttributeType;
import com.tibco.n2.directory.model.de.Capability;
import com.tibco.n2.directory.model.de.CapabilityCategory;
import com.tibco.n2.directory.model.de.CapabilityHolding;
import com.tibco.n2.directory.model.de.DataType;
import com.tibco.n2.directory.model.de.DeFactory;
import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.DocumentRoot;
import com.tibco.n2.directory.model.de.EntityType;
import com.tibco.n2.directory.model.de.Feature;
import com.tibco.n2.directory.model.de.Group;
import com.tibco.n2.directory.model.de.GroupHolding;
import com.tibco.n2.directory.model.de.Location;
import com.tibco.n2.directory.model.de.LocationType;
import com.tibco.n2.directory.model.de.MetaModel;
import com.tibco.n2.directory.model.de.ModelOrgUnit;
import com.tibco.n2.directory.model.de.ModelTemplate;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.NamedEntity;
import com.tibco.n2.directory.model.de.OrgUnit;
import com.tibco.n2.directory.model.de.OrgUnitBase;
import com.tibco.n2.directory.model.de.OrgUnitType;
import com.tibco.n2.directory.model.de.Organization;
import com.tibco.n2.directory.model.de.OrganizationType;
import com.tibco.n2.directory.model.de.Position;
import com.tibco.n2.directory.model.de.PositionHolding;
import com.tibco.n2.directory.model.de.PositionType;
import com.tibco.n2.directory.model.de.Privilege;
import com.tibco.n2.directory.model.de.PrivilegeCategory;
import com.tibco.n2.directory.model.de.PrivilegeHolding;
import com.tibco.n2.directory.model.de.QualifiedHolding;
import com.tibco.n2.directory.model.de.Qualifier;
import com.tibco.n2.directory.model.de.Resource;
import com.tibco.n2.directory.model.de.ResourceType;
import com.tibco.n2.directory.model.de.SystemAction;
import com.tibco.n2.directory.model.de.TypedEntity;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DePackageImpl extends EPackageImpl implements DePackage {
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
    private EClass attributeTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass capabilityEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass capabilityCategoryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass capabilityHoldingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass entityTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass featureEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass groupEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass groupHoldingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass locationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass locationTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass metaModelEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass modelOrgUnitEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass modelTemplateEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass modelTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass namedEntityEClass = null;

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
    private EClass organizationTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orgUnitEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orgUnitBaseEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orgUnitTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass positionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass positionHoldingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass positionTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass privilegeEClass = null;

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
    private EClass privilegeHoldingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass qualifiedHoldingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass qualifierEClass = null;

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
    private EClass systemActionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass typedEntityEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum allocationMethodEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum dataTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum resourceTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType allocationMethodObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType dataTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType resourceTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType versionNumberEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.n2.directory.model.de.DePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private DePackageImpl() {
        super(eNS_URI, DeFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link DePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static DePackage init() {
        if (isInited) return (DePackage)EPackage.Registry.INSTANCE.getEPackage(DePackage.eNS_URI);

        // Obtain or create and register package
        DePackageImpl theDePackage = (DePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DePackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theDePackage.createPackageContents();

        // Initialize created meta-data
        theDePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theDePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(DePackage.eNS_URI, theDePackage);
        return theDePackage;
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
    public EAttribute getAttribute_String() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Integer() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Decimal() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Date() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Time() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_DateTime() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Boolean() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Enum() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_EnumSet() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttribute_Element() {
        return (EAttribute)attributeEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttribute_Definition() {
        return (EReference)attributeEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAttributeType() {
        return attributeTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeType_Enum() {
        return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeType_DataType() {
        return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
    public EReference getCapability_Qualifier() {
        return (EReference)capabilityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCapabilityCategory() {
        return capabilityCategoryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCapabilityCategory_Group() {
        return (EAttribute)capabilityCategoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCapabilityCategory_CapabilityCategory() {
        return (EReference)capabilityCategoryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCapabilityCategory_Capability() {
        return (EReference)capabilityCategoryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCapabilityHolding() {
        return capabilityHoldingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCapabilityHolding_Capability() {
        return (EReference)capabilityHoldingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_Model() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEntityType() {
        return entityTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEntityType_Attribute() {
        return (EReference)entityTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getFeature() {
        return featureEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getFeature_Definition() {
        return (EReference)featureEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFeature_LowerBound() {
        return (EAttribute)featureEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFeature_UpperBound() {
        return (EAttribute)featureEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGroup() {
        return groupEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGroup_ChoiceGroup() {
        return (EAttribute)groupEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroup_ReqCapability() {
        return (EReference)groupEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroup_PrivilegeHeld() {
        return (EReference)groupEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroup_Group() {
        return (EReference)groupEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroup_SystemAction() {
        return (EReference)groupEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGroup_AllocMethod() {
        return (EAttribute)groupEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGroup_Plugin() {
        return (EAttribute)groupEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGroup_UndeliveredQueue() {
        return (EAttribute)groupEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGroupHolding() {
        return groupHoldingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroupHolding_Group() {
        return (EReference)groupHoldingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
    public EAttribute getLocation_Locale() {
        return (EAttribute)locationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLocation_Timezone() {
        return (EAttribute)locationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLocationType() {
        return locationTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLocationType_Locale() {
        return (EAttribute)locationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLocationType_Timezone() {
        return (EAttribute)locationTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMetaModel() {
        return metaModelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMetaModel_ChoiceGroup() {
        return (EAttribute)metaModelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMetaModel_LocationType() {
        return (EReference)metaModelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMetaModel_PositionType() {
        return (EReference)metaModelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMetaModel_OrgUnitType() {
        return (EReference)metaModelEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMetaModel_OrganizationType() {
        return (EReference)metaModelEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getModelOrgUnit() {
        return modelOrgUnitEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelOrgUnit_ModelPosition() {
        return (EReference)modelOrgUnitEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelOrgUnit_ModelOrgUnit() {
        return (EReference)modelOrgUnitEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getModelTemplate() {
        return modelTemplateEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getModelTemplate_InstanceIdAttribute() {
        return (EAttribute)modelTemplateEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getModelType() {
        return modelTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_MetaModel() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getModelType_ChoiceGroup() {
        return (EAttribute)modelTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_ModelTemplate() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_Capability() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_CapabilityCategory() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_Privilege() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_PrivilegeCategory() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_Location() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_Group() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_Organization() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_ResourceAttribute() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_SystemAction() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getModelType_Resource() {
        return (EReference)modelTypeEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getModelType_Name() {
        return (EAttribute)modelTypeEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getModelType_Version() {
        return (EAttribute)modelTypeEClass.getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getNamedEntity() {
        return namedEntityEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedEntity_Description() {
        return (EAttribute)namedEntityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedEntity_Id() {
        return (EAttribute)namedEntityEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedEntity_Label() {
        return (EAttribute)namedEntityEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedEntity_Name() {
        return (EAttribute)namedEntityEClass.getEStructuralFeatures().get(3);
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
    public EAttribute getOrganization_ChoiceGroup() {
        return (EAttribute)organizationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrganization_OrgUnit() {
        return (EReference)organizationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrganization_SystemAction() {
        return (EReference)organizationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrganization_AllocMethod() {
        return (EAttribute)organizationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrganization_Location() {
        return (EReference)organizationEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrganization_Plugin() {
        return (EAttribute)organizationEClass.getEStructuralFeatures().get(5);
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
    public EReference getOrganizationType_OrgUnitFeature() {
        return (EReference)organizationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgUnit() {
        return orgUnitEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnit_ModelTemplateRef() {
        return (EReference)orgUnitEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnit_Position() {
        return (EReference)orgUnitEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnit_OrgUnit() {
        return (EReference)orgUnitEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgUnitBase() {
        return orgUnitBaseEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgUnitBase_ChoiceGroup() {
        return (EAttribute)orgUnitBaseEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitBase_PrivilegeHeld() {
        return (EReference)orgUnitBaseEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitBase_SystemAction() {
        return (EReference)orgUnitBaseEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgUnitBase_AllocMethod() {
        return (EAttribute)orgUnitBaseEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitBase_Feature() {
        return (EReference)orgUnitBaseEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitBase_Location() {
        return (EReference)orgUnitBaseEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgUnitBase_Plugin() {
        return (EAttribute)orgUnitBaseEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
    public EAttribute getOrgUnitType_ChoiceGroup() {
        return (EAttribute)orgUnitTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitType_PositionFeature() {
        return (EReference)orgUnitTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOrgUnitType_OrgUnitFeature() {
        return (EReference)orgUnitTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPosition() {
        return positionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPosition_ChoiceGroup() {
        return (EAttribute)positionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPosition_ReqCapability() {
        return (EReference)positionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPosition_PrivilegeHeld() {
        return (EReference)positionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPosition_SystemAction() {
        return (EReference)positionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPosition_AllocMethod() {
        return (EAttribute)positionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPosition_Feature() {
        return (EReference)positionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPosition_IdealNumber() {
        return (EAttribute)positionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPosition_Location() {
        return (EReference)positionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPosition_Plugin() {
        return (EAttribute)positionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPositionHolding() {
        return positionHoldingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPositionHolding_Position() {
        return (EReference)positionHoldingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPositionType() {
        return positionTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
    public EReference getPrivilege_Qualifier() {
        return (EReference)privilegeEClass.getEStructuralFeatures().get(0);
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
    public EAttribute getPrivilegeCategory_Group() {
        return (EAttribute)privilegeCategoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPrivilegeCategory_PrivilegeCategory() {
        return (EReference)privilegeCategoryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPrivilegeCategory_Privilege() {
        return (EReference)privilegeCategoryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPrivilegeHolding() {
        return privilegeHoldingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPrivilegeHolding_Privilege() {
        return (EReference)privilegeHoldingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getQualifiedHolding() {
        return qualifiedHoldingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_String() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_Integer() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_Decimal() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_Date() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_Time() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_DateTime() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_Boolean() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_Enum() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_EnumSet() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifiedHolding_Element() {
        return (EAttribute)qualifiedHoldingEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getQualifier() {
        return qualifierEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifier_Enum() {
        return (EAttribute)qualifierEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifier_DataType() {
        return (EAttribute)qualifierEClass.getEStructuralFeatures().get(1);
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
    public EAttribute getResource_ChoiceGroup() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResource_CapabilityHeld() {
        return (EReference)resourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResource_PositionHeld() {
        return (EReference)resourceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResource_GroupHeld() {
        return (EReference)resourceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResource_AttributeValue() {
        return (EReference)resourceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_EndDate() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_Id() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_Label() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_LdapAlias() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_LdapDn() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResource_Location() {
        return (EReference)resourceEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_Name() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_ResourceType() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResource_StartDate() {
        return (EAttribute)resourceEClass.getEStructuralFeatures().get(13);
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
    public EReference getSystemAction_ReqPrivilege() {
        return (EReference)systemActionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSystemAction_Component() {
        return (EAttribute)systemActionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSystemAction_Name() {
        return (EAttribute)systemActionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTypedEntity() {
        return typedEntityEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypedEntity_AttributeValue() {
        return (EReference)typedEntityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTypedEntity_Definition() {
        return (EReference)typedEntityEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAllocationMethod() {
        return allocationMethodEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDataType() {
        return dataTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getResourceType() {
        return resourceTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAllocationMethodObject() {
        return allocationMethodObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getDataTypeObject() {
        return dataTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getResourceTypeObject() {
        return resourceTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getVersionNumber() {
        return versionNumberEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeFactory getDeFactory() {
        return (DeFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        attributeEClass = createEClass(ATTRIBUTE);
        createEAttribute(attributeEClass, ATTRIBUTE__STRING);
        createEAttribute(attributeEClass, ATTRIBUTE__INTEGER);
        createEAttribute(attributeEClass, ATTRIBUTE__DECIMAL);
        createEAttribute(attributeEClass, ATTRIBUTE__DATE);
        createEAttribute(attributeEClass, ATTRIBUTE__TIME);
        createEAttribute(attributeEClass, ATTRIBUTE__DATE_TIME);
        createEAttribute(attributeEClass, ATTRIBUTE__BOOLEAN);
        createEAttribute(attributeEClass, ATTRIBUTE__ENUM);
        createEAttribute(attributeEClass, ATTRIBUTE__ENUM_SET);
        createEAttribute(attributeEClass, ATTRIBUTE__ELEMENT);
        createEReference(attributeEClass, ATTRIBUTE__DEFINITION);

        attributeTypeEClass = createEClass(ATTRIBUTE_TYPE);
        createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__ENUM);
        createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__DATA_TYPE);

        capabilityEClass = createEClass(CAPABILITY);
        createEReference(capabilityEClass, CAPABILITY__QUALIFIER);

        capabilityCategoryEClass = createEClass(CAPABILITY_CATEGORY);
        createEAttribute(capabilityCategoryEClass, CAPABILITY_CATEGORY__GROUP);
        createEReference(capabilityCategoryEClass, CAPABILITY_CATEGORY__CAPABILITY_CATEGORY);
        createEReference(capabilityCategoryEClass, CAPABILITY_CATEGORY__CAPABILITY);

        capabilityHoldingEClass = createEClass(CAPABILITY_HOLDING);
        createEReference(capabilityHoldingEClass, CAPABILITY_HOLDING__CAPABILITY);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__MODEL);

        entityTypeEClass = createEClass(ENTITY_TYPE);
        createEReference(entityTypeEClass, ENTITY_TYPE__ATTRIBUTE);

        featureEClass = createEClass(FEATURE);
        createEReference(featureEClass, FEATURE__DEFINITION);
        createEAttribute(featureEClass, FEATURE__LOWER_BOUND);
        createEAttribute(featureEClass, FEATURE__UPPER_BOUND);

        groupEClass = createEClass(GROUP);
        createEAttribute(groupEClass, GROUP__CHOICE_GROUP);
        createEReference(groupEClass, GROUP__REQ_CAPABILITY);
        createEReference(groupEClass, GROUP__PRIVILEGE_HELD);
        createEReference(groupEClass, GROUP__GROUP);
        createEReference(groupEClass, GROUP__SYSTEM_ACTION);
        createEAttribute(groupEClass, GROUP__ALLOC_METHOD);
        createEAttribute(groupEClass, GROUP__PLUGIN);
        createEAttribute(groupEClass, GROUP__UNDELIVERED_QUEUE);

        groupHoldingEClass = createEClass(GROUP_HOLDING);
        createEReference(groupHoldingEClass, GROUP_HOLDING__GROUP);

        locationEClass = createEClass(LOCATION);
        createEAttribute(locationEClass, LOCATION__LOCALE);
        createEAttribute(locationEClass, LOCATION__TIMEZONE);

        locationTypeEClass = createEClass(LOCATION_TYPE);
        createEAttribute(locationTypeEClass, LOCATION_TYPE__LOCALE);
        createEAttribute(locationTypeEClass, LOCATION_TYPE__TIMEZONE);

        metaModelEClass = createEClass(META_MODEL);
        createEAttribute(metaModelEClass, META_MODEL__CHOICE_GROUP);
        createEReference(metaModelEClass, META_MODEL__LOCATION_TYPE);
        createEReference(metaModelEClass, META_MODEL__POSITION_TYPE);
        createEReference(metaModelEClass, META_MODEL__ORG_UNIT_TYPE);
        createEReference(metaModelEClass, META_MODEL__ORGANIZATION_TYPE);

        modelOrgUnitEClass = createEClass(MODEL_ORG_UNIT);
        createEReference(modelOrgUnitEClass, MODEL_ORG_UNIT__MODEL_POSITION);
        createEReference(modelOrgUnitEClass, MODEL_ORG_UNIT__MODEL_ORG_UNIT);

        modelTemplateEClass = createEClass(MODEL_TEMPLATE);
        createEAttribute(modelTemplateEClass, MODEL_TEMPLATE__INSTANCE_ID_ATTRIBUTE);

        modelTypeEClass = createEClass(MODEL_TYPE);
        createEReference(modelTypeEClass, MODEL_TYPE__META_MODEL);
        createEAttribute(modelTypeEClass, MODEL_TYPE__CHOICE_GROUP);
        createEReference(modelTypeEClass, MODEL_TYPE__MODEL_TEMPLATE);
        createEReference(modelTypeEClass, MODEL_TYPE__CAPABILITY);
        createEReference(modelTypeEClass, MODEL_TYPE__CAPABILITY_CATEGORY);
        createEReference(modelTypeEClass, MODEL_TYPE__PRIVILEGE);
        createEReference(modelTypeEClass, MODEL_TYPE__PRIVILEGE_CATEGORY);
        createEReference(modelTypeEClass, MODEL_TYPE__LOCATION);
        createEReference(modelTypeEClass, MODEL_TYPE__GROUP);
        createEReference(modelTypeEClass, MODEL_TYPE__ORGANIZATION);
        createEReference(modelTypeEClass, MODEL_TYPE__RESOURCE_ATTRIBUTE);
        createEReference(modelTypeEClass, MODEL_TYPE__SYSTEM_ACTION);
        createEReference(modelTypeEClass, MODEL_TYPE__RESOURCE);
        createEAttribute(modelTypeEClass, MODEL_TYPE__NAME);
        createEAttribute(modelTypeEClass, MODEL_TYPE__VERSION);

        namedEntityEClass = createEClass(NAMED_ENTITY);
        createEAttribute(namedEntityEClass, NAMED_ENTITY__DESCRIPTION);
        createEAttribute(namedEntityEClass, NAMED_ENTITY__ID);
        createEAttribute(namedEntityEClass, NAMED_ENTITY__LABEL);
        createEAttribute(namedEntityEClass, NAMED_ENTITY__NAME);

        organizationEClass = createEClass(ORGANIZATION);
        createEAttribute(organizationEClass, ORGANIZATION__CHOICE_GROUP);
        createEReference(organizationEClass, ORGANIZATION__ORG_UNIT);
        createEReference(organizationEClass, ORGANIZATION__SYSTEM_ACTION);
        createEAttribute(organizationEClass, ORGANIZATION__ALLOC_METHOD);
        createEReference(organizationEClass, ORGANIZATION__LOCATION);
        createEAttribute(organizationEClass, ORGANIZATION__PLUGIN);

        organizationTypeEClass = createEClass(ORGANIZATION_TYPE);
        createEReference(organizationTypeEClass, ORGANIZATION_TYPE__ORG_UNIT_FEATURE);

        orgUnitEClass = createEClass(ORG_UNIT);
        createEReference(orgUnitEClass, ORG_UNIT__MODEL_TEMPLATE_REF);
        createEReference(orgUnitEClass, ORG_UNIT__POSITION);
        createEReference(orgUnitEClass, ORG_UNIT__ORG_UNIT);

        orgUnitBaseEClass = createEClass(ORG_UNIT_BASE);
        createEAttribute(orgUnitBaseEClass, ORG_UNIT_BASE__CHOICE_GROUP);
        createEReference(orgUnitBaseEClass, ORG_UNIT_BASE__PRIVILEGE_HELD);
        createEReference(orgUnitBaseEClass, ORG_UNIT_BASE__SYSTEM_ACTION);
        createEAttribute(orgUnitBaseEClass, ORG_UNIT_BASE__ALLOC_METHOD);
        createEReference(orgUnitBaseEClass, ORG_UNIT_BASE__FEATURE);
        createEReference(orgUnitBaseEClass, ORG_UNIT_BASE__LOCATION);
        createEAttribute(orgUnitBaseEClass, ORG_UNIT_BASE__PLUGIN);

        orgUnitTypeEClass = createEClass(ORG_UNIT_TYPE);
        createEAttribute(orgUnitTypeEClass, ORG_UNIT_TYPE__CHOICE_GROUP);
        createEReference(orgUnitTypeEClass, ORG_UNIT_TYPE__POSITION_FEATURE);
        createEReference(orgUnitTypeEClass, ORG_UNIT_TYPE__ORG_UNIT_FEATURE);

        positionEClass = createEClass(POSITION);
        createEAttribute(positionEClass, POSITION__CHOICE_GROUP);
        createEReference(positionEClass, POSITION__REQ_CAPABILITY);
        createEReference(positionEClass, POSITION__PRIVILEGE_HELD);
        createEReference(positionEClass, POSITION__SYSTEM_ACTION);
        createEAttribute(positionEClass, POSITION__ALLOC_METHOD);
        createEReference(positionEClass, POSITION__FEATURE);
        createEAttribute(positionEClass, POSITION__IDEAL_NUMBER);
        createEReference(positionEClass, POSITION__LOCATION);
        createEAttribute(positionEClass, POSITION__PLUGIN);

        positionHoldingEClass = createEClass(POSITION_HOLDING);
        createEReference(positionHoldingEClass, POSITION_HOLDING__POSITION);

        positionTypeEClass = createEClass(POSITION_TYPE);

        privilegeEClass = createEClass(PRIVILEGE);
        createEReference(privilegeEClass, PRIVILEGE__QUALIFIER);

        privilegeCategoryEClass = createEClass(PRIVILEGE_CATEGORY);
        createEAttribute(privilegeCategoryEClass, PRIVILEGE_CATEGORY__GROUP);
        createEReference(privilegeCategoryEClass, PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY);
        createEReference(privilegeCategoryEClass, PRIVILEGE_CATEGORY__PRIVILEGE);

        privilegeHoldingEClass = createEClass(PRIVILEGE_HOLDING);
        createEReference(privilegeHoldingEClass, PRIVILEGE_HOLDING__PRIVILEGE);

        qualifiedHoldingEClass = createEClass(QUALIFIED_HOLDING);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__STRING);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__INTEGER);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__DECIMAL);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__DATE);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__TIME);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__DATE_TIME);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__BOOLEAN);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__ENUM);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__ENUM_SET);
        createEAttribute(qualifiedHoldingEClass, QUALIFIED_HOLDING__ELEMENT);

        qualifierEClass = createEClass(QUALIFIER);
        createEAttribute(qualifierEClass, QUALIFIER__ENUM);
        createEAttribute(qualifierEClass, QUALIFIER__DATA_TYPE);

        resourceEClass = createEClass(RESOURCE);
        createEAttribute(resourceEClass, RESOURCE__CHOICE_GROUP);
        createEReference(resourceEClass, RESOURCE__CAPABILITY_HELD);
        createEReference(resourceEClass, RESOURCE__POSITION_HELD);
        createEReference(resourceEClass, RESOURCE__GROUP_HELD);
        createEReference(resourceEClass, RESOURCE__ATTRIBUTE_VALUE);
        createEAttribute(resourceEClass, RESOURCE__END_DATE);
        createEAttribute(resourceEClass, RESOURCE__ID);
        createEAttribute(resourceEClass, RESOURCE__LABEL);
        createEAttribute(resourceEClass, RESOURCE__LDAP_ALIAS);
        createEAttribute(resourceEClass, RESOURCE__LDAP_DN);
        createEReference(resourceEClass, RESOURCE__LOCATION);
        createEAttribute(resourceEClass, RESOURCE__NAME);
        createEAttribute(resourceEClass, RESOURCE__RESOURCE_TYPE);
        createEAttribute(resourceEClass, RESOURCE__START_DATE);

        systemActionEClass = createEClass(SYSTEM_ACTION);
        createEReference(systemActionEClass, SYSTEM_ACTION__REQ_PRIVILEGE);
        createEAttribute(systemActionEClass, SYSTEM_ACTION__COMPONENT);
        createEAttribute(systemActionEClass, SYSTEM_ACTION__NAME);

        typedEntityEClass = createEClass(TYPED_ENTITY);
        createEReference(typedEntityEClass, TYPED_ENTITY__ATTRIBUTE_VALUE);
        createEReference(typedEntityEClass, TYPED_ENTITY__DEFINITION);

        // Create enums
        allocationMethodEEnum = createEEnum(ALLOCATION_METHOD);
        dataTypeEEnum = createEEnum(DATA_TYPE);
        resourceTypeEEnum = createEEnum(RESOURCE_TYPE);

        // Create data types
        allocationMethodObjectEDataType = createEDataType(ALLOCATION_METHOD_OBJECT);
        dataTypeObjectEDataType = createEDataType(DATA_TYPE_OBJECT);
        resourceTypeObjectEDataType = createEDataType(RESOURCE_TYPE_OBJECT);
        versionNumberEDataType = createEDataType(VERSION_NUMBER);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        attributeTypeEClass.getESuperTypes().add(this.getNamedEntity());
        capabilityEClass.getESuperTypes().add(this.getNamedEntity());
        capabilityCategoryEClass.getESuperTypes().add(this.getNamedEntity());
        capabilityHoldingEClass.getESuperTypes().add(this.getQualifiedHolding());
        entityTypeEClass.getESuperTypes().add(this.getNamedEntity());
        featureEClass.getESuperTypes().add(this.getNamedEntity());
        groupEClass.getESuperTypes().add(this.getNamedEntity());
        locationEClass.getESuperTypes().add(this.getTypedEntity());
        locationTypeEClass.getESuperTypes().add(this.getEntityType());
        modelOrgUnitEClass.getESuperTypes().add(this.getOrgUnitBase());
        modelTemplateEClass.getESuperTypes().add(this.getModelOrgUnit());
        organizationEClass.getESuperTypes().add(this.getTypedEntity());
        organizationTypeEClass.getESuperTypes().add(this.getEntityType());
        orgUnitEClass.getESuperTypes().add(this.getOrgUnitBase());
        orgUnitBaseEClass.getESuperTypes().add(this.getTypedEntity());
        orgUnitTypeEClass.getESuperTypes().add(this.getEntityType());
        positionEClass.getESuperTypes().add(this.getTypedEntity());
        positionTypeEClass.getESuperTypes().add(this.getEntityType());
        privilegeEClass.getESuperTypes().add(this.getNamedEntity());
        privilegeCategoryEClass.getESuperTypes().add(this.getNamedEntity());
        privilegeHoldingEClass.getESuperTypes().add(this.getQualifiedHolding());
        typedEntityEClass.getESuperTypes().add(this.getNamedEntity());

        // Initialize classes and features; add operations and parameters
        initEClass(attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAttribute_String(), theXMLTypePackage.getString(), "string", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_Integer(), theXMLTypePackage.getLong(), "integer", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_Decimal(), theXMLTypePackage.getDecimal(), "decimal", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_Date(), theXMLTypePackage.getDate(), "date", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_Time(), theXMLTypePackage.getTime(), "time", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_DateTime(), theXMLTypePackage.getDateTime(), "dateTime", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_Boolean(), theXMLTypePackage.getBoolean(), "boolean", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_Enum(), theXMLTypePackage.getString(), "enum", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_EnumSet(), theXMLTypePackage.getString(), "enumSet", null, 0, -1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttribute_Element(), theXMLTypePackage.getString(), "element", null, 0, -1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAttribute_Definition(), this.getAttributeType(), null, "definition", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(attributeTypeEClass, AttributeType.class, "AttributeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAttributeType_Enum(), theXMLTypePackage.getString(), "enum", null, 0, -1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAttributeType_DataType(), this.getDataType(), "dataType", null, 1, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(capabilityEClass, Capability.class, "Capability", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCapability_Qualifier(), this.getQualifier(), null, "qualifier", null, 0, 1, Capability.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(capabilityCategoryEClass, CapabilityCategory.class, "CapabilityCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCapabilityCategory_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, CapabilityCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCapabilityCategory_CapabilityCategory(), this.getCapabilityCategory(), null, "capabilityCategory", null, 0, -1, CapabilityCategory.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getCapabilityCategory_Capability(), this.getCapability(), null, "capability", null, 0, -1, CapabilityCategory.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(capabilityHoldingEClass, CapabilityHolding.class, "CapabilityHolding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCapabilityHolding_Capability(), this.getCapability(), null, "capability", null, 1, 1, CapabilityHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_Model(), this.getModelType(), null, "model", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(entityTypeEClass, EntityType.class, "EntityType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getEntityType_Attribute(), this.getAttributeType(), null, "attribute", null, 0, -1, EntityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(featureEClass, Feature.class, "Feature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getFeature_Definition(), this.getEntityType(), null, "definition", null, 1, 1, Feature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFeature_LowerBound(), theXMLTypePackage.getInt(), "lowerBound", "0", 0, 1, Feature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFeature_UpperBound(), theXMLTypePackage.getInt(), "upperBound", "1", 0, 1, Feature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(groupEClass, Group.class, "Group", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGroup_ChoiceGroup(), ecorePackage.getEFeatureMapEntry(), "choiceGroup", null, 0, -1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGroup_ReqCapability(), this.getCapabilityHolding(), null, "reqCapability", null, 0, -1, Group.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getGroup_PrivilegeHeld(), this.getPrivilegeHolding(), null, "privilegeHeld", null, 0, -1, Group.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getGroup_Group(), this.getGroup(), null, "group", null, 0, -1, Group.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getGroup_SystemAction(), this.getSystemAction(), null, "systemAction", null, 0, -1, Group.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getGroup_AllocMethod(), this.getAllocationMethod(), "allocMethod", "ANY", 0, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGroup_Plugin(), theXMLTypePackage.getString(), "plugin", null, 0, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGroup_UndeliveredQueue(), theXMLTypePackage.getBoolean(), "undeliveredQueue", "false", 0, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(groupHoldingEClass, GroupHolding.class, "GroupHolding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGroupHolding_Group(), this.getGroup(), null, "group", null, 1, 1, GroupHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(locationEClass, Location.class, "Location", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getLocation_Locale(), theXMLTypePackage.getString(), "locale", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getLocation_Timezone(), theXMLTypePackage.getString(), "timezone", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(locationTypeEClass, LocationType.class, "LocationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getLocationType_Locale(), theXMLTypePackage.getString(), "locale", null, 0, 1, LocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getLocationType_Timezone(), theXMLTypePackage.getString(), "timezone", null, 0, 1, LocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(metaModelEClass, MetaModel.class, "MetaModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getMetaModel_ChoiceGroup(), ecorePackage.getEFeatureMapEntry(), "choiceGroup", null, 0, -1, MetaModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMetaModel_LocationType(), this.getLocationType(), null, "locationType", null, 0, -1, MetaModel.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getMetaModel_PositionType(), this.getPositionType(), null, "positionType", null, 0, -1, MetaModel.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getMetaModel_OrgUnitType(), this.getOrgUnitType(), null, "orgUnitType", null, 0, -1, MetaModel.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getMetaModel_OrganizationType(), this.getOrganizationType(), null, "organizationType", null, 0, -1, MetaModel.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(modelOrgUnitEClass, ModelOrgUnit.class, "ModelOrgUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getModelOrgUnit_ModelPosition(), this.getPosition(), null, "modelPosition", null, 0, -1, ModelOrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getModelOrgUnit_ModelOrgUnit(), this.getModelOrgUnit(), null, "modelOrgUnit", null, 0, -1, ModelOrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(modelTemplateEClass, ModelTemplate.class, "ModelTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getModelTemplate_InstanceIdAttribute(), theXMLTypePackage.getToken(), "instanceIdAttribute", null, 1, -1, ModelTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(modelTypeEClass, ModelType.class, "ModelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getModelType_MetaModel(), this.getMetaModel(), null, "metaModel", null, 0, 1, ModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getModelType_ChoiceGroup(), ecorePackage.getEFeatureMapEntry(), "choiceGroup", null, 0, -1, ModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_ModelTemplate(), this.getModelTemplate(), null, "modelTemplate", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_Capability(), this.getCapability(), null, "capability", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_CapabilityCategory(), this.getCapabilityCategory(), null, "capabilityCategory", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_Privilege(), this.getPrivilege(), null, "privilege", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_PrivilegeCategory(), this.getPrivilegeCategory(), null, "privilegeCategory", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_Location(), this.getLocation(), null, "location", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_Group(), this.getGroup(), null, "group", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_Organization(), this.getOrganization(), null, "organization", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_ResourceAttribute(), this.getAttributeType(), null, "resourceAttribute", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_SystemAction(), this.getSystemAction(), null, "systemAction", null, 0, -1, ModelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getModelType_Resource(), this.getResource(), null, "resource", null, 0, -1, ModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getModelType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getModelType_Version(), this.getVersionNumber(), "version", null, 1, 1, ModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(namedEntityEClass, NamedEntity.class, "NamedEntity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getNamedEntity_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, NamedEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNamedEntity_Id(), theXMLTypePackage.getNCName(), "id", null, 1, 1, NamedEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNamedEntity_Label(), theXMLTypePackage.getString(), "label", null, 0, 1, NamedEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNamedEntity_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, NamedEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(organizationEClass, Organization.class, "Organization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOrganization_ChoiceGroup(), ecorePackage.getEFeatureMapEntry(), "choiceGroup", null, 0, -1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOrganization_OrgUnit(), this.getOrgUnit(), null, "orgUnit", null, 0, -1, Organization.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getOrganization_SystemAction(), this.getSystemAction(), null, "systemAction", null, 0, -1, Organization.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrganization_AllocMethod(), this.getAllocationMethod(), "allocMethod", "ANY", 0, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOrganization_Location(), this.getLocation(), null, "location", null, 0, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrganization_Plugin(), theXMLTypePackage.getString(), "plugin", null, 0, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(organizationTypeEClass, OrganizationType.class, "OrganizationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getOrganizationType_OrgUnitFeature(), this.getFeature(), null, "orgUnitFeature", null, 0, -1, OrganizationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(orgUnitEClass, OrgUnit.class, "OrgUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getOrgUnit_ModelTemplateRef(), this.getModelTemplate(), null, "modelTemplateRef", null, 0, 1, OrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOrgUnit_Position(), this.getPosition(), null, "position", null, 0, -1, OrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOrgUnit_OrgUnit(), this.getOrgUnit(), null, "orgUnit", null, 0, -1, OrgUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(orgUnitBaseEClass, OrgUnitBase.class, "OrgUnitBase", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOrgUnitBase_ChoiceGroup(), ecorePackage.getEFeatureMapEntry(), "choiceGroup", null, 0, -1, OrgUnitBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOrgUnitBase_PrivilegeHeld(), this.getPrivilegeHolding(), null, "privilegeHeld", null, 0, -1, OrgUnitBase.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getOrgUnitBase_SystemAction(), this.getSystemAction(), null, "systemAction", null, 0, -1, OrgUnitBase.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrgUnitBase_AllocMethod(), this.getAllocationMethod(), "allocMethod", "ANY", 0, 1, OrgUnitBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOrgUnitBase_Feature(), this.getFeature(), null, "feature", null, 0, 1, OrgUnitBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOrgUnitBase_Location(), this.getLocation(), null, "location", null, 0, 1, OrgUnitBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrgUnitBase_Plugin(), theXMLTypePackage.getString(), "plugin", null, 0, 1, OrgUnitBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(orgUnitTypeEClass, OrgUnitType.class, "OrgUnitType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOrgUnitType_ChoiceGroup(), ecorePackage.getEFeatureMapEntry(), "choiceGroup", null, 0, -1, OrgUnitType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOrgUnitType_PositionFeature(), this.getFeature(), null, "positionFeature", null, 0, -1, OrgUnitType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getOrgUnitType_OrgUnitFeature(), this.getFeature(), null, "orgUnitFeature", null, 0, -1, OrgUnitType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPosition_ChoiceGroup(), ecorePackage.getEFeatureMapEntry(), "choiceGroup", null, 0, -1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPosition_ReqCapability(), this.getCapabilityHolding(), null, "reqCapability", null, 0, -1, Position.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getPosition_PrivilegeHeld(), this.getPrivilegeHolding(), null, "privilegeHeld", null, 0, -1, Position.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getPosition_SystemAction(), this.getSystemAction(), null, "systemAction", null, 0, -1, Position.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getPosition_AllocMethod(), this.getAllocationMethod(), "allocMethod", "ANY", 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPosition_Feature(), this.getFeature(), null, "feature", null, 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPosition_IdealNumber(), theXMLTypePackage.getUnsignedInt(), "idealNumber", null, 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPosition_Location(), this.getLocation(), null, "location", null, 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPosition_Plugin(), theXMLTypePackage.getString(), "plugin", null, 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(positionHoldingEClass, PositionHolding.class, "PositionHolding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPositionHolding_Position(), this.getPosition(), null, "position", null, 1, 1, PositionHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(positionTypeEClass, PositionType.class, "PositionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(privilegeEClass, Privilege.class, "Privilege", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPrivilege_Qualifier(), this.getQualifier(), null, "qualifier", null, 0, 1, Privilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(privilegeCategoryEClass, PrivilegeCategory.class, "PrivilegeCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPrivilegeCategory_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, PrivilegeCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPrivilegeCategory_PrivilegeCategory(), this.getPrivilegeCategory(), null, "privilegeCategory", null, 0, -1, PrivilegeCategory.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getPrivilegeCategory_Privilege(), this.getPrivilege(), null, "privilege", null, 0, -1, PrivilegeCategory.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(privilegeHoldingEClass, PrivilegeHolding.class, "PrivilegeHolding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPrivilegeHolding_Privilege(), this.getPrivilege(), null, "privilege", null, 1, 1, PrivilegeHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(qualifiedHoldingEClass, QualifiedHolding.class, "QualifiedHolding", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getQualifiedHolding_String(), theXMLTypePackage.getString(), "string", null, 0, 1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_Integer(), theXMLTypePackage.getLong(), "integer", null, 0, 1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_Decimal(), theXMLTypePackage.getDecimal(), "decimal", null, 0, 1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_Date(), theXMLTypePackage.getDate(), "date", null, 0, 1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_Time(), theXMLTypePackage.getTime(), "time", null, 0, 1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_DateTime(), theXMLTypePackage.getDateTime(), "dateTime", null, 0, 1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_Boolean(), theXMLTypePackage.getBoolean(), "boolean", null, 0, 1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_Enum(), theXMLTypePackage.getString(), "enum", null, 0, 1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_EnumSet(), theXMLTypePackage.getString(), "enumSet", null, 0, -1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifiedHolding_Element(), theXMLTypePackage.getString(), "element", null, 0, -1, QualifiedHolding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(qualifierEClass, Qualifier.class, "Qualifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getQualifier_Enum(), theXMLTypePackage.getString(), "enum", null, 0, -1, Qualifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getQualifier_DataType(), this.getDataType(), "dataType", null, 1, 1, Qualifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(resourceEClass, Resource.class, "Resource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getResource_ChoiceGroup(), ecorePackage.getEFeatureMapEntry(), "choiceGroup", null, 0, -1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getResource_CapabilityHeld(), this.getCapabilityHolding(), null, "capabilityHeld", null, 0, -1, Resource.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getResource_PositionHeld(), this.getPositionHolding(), null, "positionHeld", null, 0, -1, Resource.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getResource_GroupHeld(), this.getGroupHolding(), null, "groupHeld", null, 0, -1, Resource.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getResource_AttributeValue(), this.getAttribute(), null, "attributeValue", null, 0, -1, Resource.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_EndDate(), theXMLTypePackage.getDateTime(), "endDate", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_Id(), theXMLTypePackage.getID(), "id", null, 1, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_Label(), theXMLTypePackage.getString(), "label", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_LdapAlias(), theXMLTypePackage.getString(), "ldapAlias", null, 1, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_LdapDn(), theXMLTypePackage.getString(), "ldapDn", null, 1, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getResource_Location(), this.getLocation(), null, "location", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_ResourceType(), this.getResourceType(), "resourceType", "human", 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getResource_StartDate(), theXMLTypePackage.getDateTime(), "startDate", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(systemActionEClass, SystemAction.class, "SystemAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSystemAction_ReqPrivilege(), this.getPrivilegeHolding(), null, "reqPrivilege", null, 1, -1, SystemAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSystemAction_Component(), theXMLTypePackage.getString(), "component", null, 0, 1, SystemAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSystemAction_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SystemAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(typedEntityEClass, TypedEntity.class, "TypedEntity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getTypedEntity_AttributeValue(), this.getAttribute(), null, "attributeValue", null, 0, -1, TypedEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTypedEntity_Definition(), this.getEntityType(), null, "definition", null, 0, 1, TypedEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(allocationMethodEEnum, AllocationMethod.class, "AllocationMethod");
        addEEnumLiteral(allocationMethodEEnum, AllocationMethod.ANY);
        addEEnumLiteral(allocationMethodEEnum, AllocationMethod.NEXT);
        addEEnumLiteral(allocationMethodEEnum, AllocationMethod.THIS);
        addEEnumLiteral(allocationMethodEEnum, AllocationMethod.PLUGIN);

        initEEnum(dataTypeEEnum, DataType.class, "DataType");
        addEEnumLiteral(dataTypeEEnum, DataType.STRING);
        addEEnumLiteral(dataTypeEEnum, DataType.INTEGER);
        addEEnumLiteral(dataTypeEEnum, DataType.DECIMAL);
        addEEnumLiteral(dataTypeEEnum, DataType.DATE);
        addEEnumLiteral(dataTypeEEnum, DataType.TIME);
        addEEnumLiteral(dataTypeEEnum, DataType.DATE_TIME);
        addEEnumLiteral(dataTypeEEnum, DataType.BOOLEAN);
        addEEnumLiteral(dataTypeEEnum, DataType.ENUM);
        addEEnumLiteral(dataTypeEEnum, DataType.ENUM_SET);
        addEEnumLiteral(dataTypeEEnum, DataType.SET);

        initEEnum(resourceTypeEEnum, ResourceType.class, "ResourceType");
        addEEnumLiteral(resourceTypeEEnum, ResourceType.DURABLE);
        addEEnumLiteral(resourceTypeEEnum, ResourceType.CONSUMABLE);
        addEEnumLiteral(resourceTypeEEnum, ResourceType.HUMAN);

        // Initialize data types
        initEDataType(allocationMethodObjectEDataType, AllocationMethod.class, "AllocationMethodObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(dataTypeObjectEDataType, DataType.class, "DataTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(resourceTypeObjectEDataType, ResourceType.class, "ResourceTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(versionNumberEDataType, String.class, "VersionNumber", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";			
        addAnnotation
          (allocationMethodEEnum, 
           source, 
           new String[] {
             "name", "AllocationMethod"
           });						
        addAnnotation
          (allocationMethodObjectEDataType, 
           source, 
           new String[] {
             "name", "AllocationMethod:Object",
             "baseType", "AllocationMethod"
           });			
        addAnnotation
          (attributeEClass, 
           source, 
           new String[] {
             "name", "Attribute",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAttribute_String(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "string",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_Integer(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "integer",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_Decimal(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "decimal",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_Date(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "date",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_Time(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "time",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_DateTime(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "date-time",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_Boolean(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "boolean",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_Enum(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enum",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_EnumSet(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enum-set",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getAttribute_Element(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "element",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getAttribute_Definition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "definition"
           });			
        addAnnotation
          (attributeTypeEClass, 
           source, 
           new String[] {
             "name", "AttributeType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAttributeType_Enum(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enum",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getAttributeType_DataType(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "data-type"
           });			
        addAnnotation
          (capabilityEClass, 
           source, 
           new String[] {
             "name", "Capability",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getCapability_Qualifier(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "qualifier",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (capabilityCategoryEClass, 
           source, 
           new String[] {
             "name", "CapabilityCategory",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getCapabilityCategory_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:4"
           });			
        addAnnotation
          (getCapabilityCategory_CapabilityCategory(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "capability-category",
             "namespace", "##targetNamespace",
             "group", "#group:4"
           });			
        addAnnotation
          (getCapabilityCategory_Capability(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "capability",
             "namespace", "##targetNamespace",
             "group", "#group:4"
           });			
        addAnnotation
          (capabilityHoldingEClass, 
           source, 
           new String[] {
             "name", "CapabilityHolding",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getCapabilityHolding_Capability(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "capability"
           });			
        addAnnotation
          (dataTypeEEnum, 
           source, 
           new String[] {
             "name", "DataType"
           });		
        addAnnotation
          (dataTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "DataType:Object",
             "baseType", "DataType"
           });		
        addAnnotation
          (documentRootEClass, 
           source, 
           new String[] {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getDocumentRoot_Mixed(), 
           source, 
           new String[] {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getDocumentRoot_XMLNSPrefixMap(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getDocumentRoot_XSISchemaLocation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });			
        addAnnotation
          (getDocumentRoot_Model(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "model",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (entityTypeEClass, 
           source, 
           new String[] {
             "name", "EntityType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getEntityType_Attribute(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attribute",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (featureEClass, 
           source, 
           new String[] {
             "name", "Feature",
             "kind", "empty"
           });			
        addAnnotation
          (getFeature_Definition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "definition"
           });			
        addAnnotation
          (getFeature_LowerBound(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "lower-bound"
           });			
        addAnnotation
          (getFeature_UpperBound(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "upper-bound"
           });			
        addAnnotation
          (groupEClass, 
           source, 
           new String[] {
             "name", "Group",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGroup_ChoiceGroup(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "choiceGroup:4"
           });			
        addAnnotation
          (getGroup_ReqCapability(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "req-capability",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:4"
           });			
        addAnnotation
          (getGroup_PrivilegeHeld(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "privilege-held",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:4"
           });			
        addAnnotation
          (getGroup_Group(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "group",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:4"
           });			
        addAnnotation
          (getGroup_SystemAction(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "system-action",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:4"
           });		
        addAnnotation
          (getGroup_AllocMethod(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "alloc-method"
           });		
        addAnnotation
          (getGroup_Plugin(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "plugin"
           });			
        addAnnotation
          (getGroup_UndeliveredQueue(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "undelivered-queue"
           });			
        addAnnotation
          (groupHoldingEClass, 
           source, 
           new String[] {
             "name", "GroupHolding",
             "kind", "empty"
           });		
        addAnnotation
          (getGroupHolding_Group(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "group"
           });			
        addAnnotation
          (locationEClass, 
           source, 
           new String[] {
             "name", "Location",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getLocation_Locale(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "locale"
           });		
        addAnnotation
          (getLocation_Timezone(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "timezone"
           });			
        addAnnotation
          (locationTypeEClass, 
           source, 
           new String[] {
             "name", "LocationType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getLocationType_Locale(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "locale"
           });		
        addAnnotation
          (getLocationType_Timezone(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "timezone"
           });			
        addAnnotation
          (metaModelEClass, 
           source, 
           new String[] {
             "name", "MetaModel",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getMetaModel_ChoiceGroup(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "choiceGroup:0"
           });		
        addAnnotation
          (getMetaModel_LocationType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "location-type",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:0"
           });		
        addAnnotation
          (getMetaModel_PositionType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "position-type",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:0"
           });		
        addAnnotation
          (getMetaModel_OrgUnitType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "org-unit-type",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:0"
           });		
        addAnnotation
          (getMetaModel_OrganizationType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "organization-type",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:0"
           });			
        addAnnotation
          (modelOrgUnitEClass, 
           source, 
           new String[] {
             "name", "ModelOrgUnit",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getModelOrgUnit_ModelPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "model-position",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getModelOrgUnit_ModelOrgUnit(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "model-org-unit",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (modelTemplateEClass, 
           source, 
           new String[] {
             "name", "ModelTemplate",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getModelTemplate_InstanceIdAttribute(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "instance-id-attribute",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (modelTypeEClass, 
           source, 
           new String[] {
             "name", "model_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getModelType_MetaModel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "meta-model",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getModelType_ChoiceGroup(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "choiceGroup:1"
           });		
        addAnnotation
          (getModelType_ModelTemplate(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "model-template",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_Capability(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "capability",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_CapabilityCategory(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "capability-category",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_Privilege(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "privilege",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_PrivilegeCategory(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "privilege-category",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_Location(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "location",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_Group(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "group",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_Organization(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "organization",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_ResourceAttribute(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resource-attribute",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_SystemAction(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "system-action",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:1"
           });		
        addAnnotation
          (getModelType_Resource(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resource",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getModelType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getModelType_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });			
        addAnnotation
          (namedEntityEClass, 
           source, 
           new String[] {
             "name", "NamedEntity",
             "kind", "empty"
           });			
        addAnnotation
          (getNamedEntity_Description(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "description"
           });			
        addAnnotation
          (getNamedEntity_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });			
        addAnnotation
          (getNamedEntity_Label(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "label"
           });			
        addAnnotation
          (getNamedEntity_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });			
        addAnnotation
          (organizationEClass, 
           source, 
           new String[] {
             "name", "Organization",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOrganization_ChoiceGroup(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "choiceGroup:6"
           });			
        addAnnotation
          (getOrganization_OrgUnit(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "org-unit",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:6"
           });			
        addAnnotation
          (getOrganization_SystemAction(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "system-action",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:6"
           });		
        addAnnotation
          (getOrganization_AllocMethod(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "alloc-method"
           });			
        addAnnotation
          (getOrganization_Location(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "location"
           });		
        addAnnotation
          (getOrganization_Plugin(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "plugin"
           });			
        addAnnotation
          (organizationTypeEClass, 
           source, 
           new String[] {
             "name", "OrganizationType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOrganizationType_OrgUnitFeature(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "org-unit-feature",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (orgUnitEClass, 
           source, 
           new String[] {
             "name", "OrgUnit",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getOrgUnit_ModelTemplateRef(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "model-template-ref",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getOrgUnit_Position(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "position",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getOrgUnit_OrgUnit(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "org-unit",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (orgUnitBaseEClass, 
           source, 
           new String[] {
             "name", "OrgUnitBase",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOrgUnitBase_ChoiceGroup(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "choiceGroup:6"
           });			
        addAnnotation
          (getOrgUnitBase_PrivilegeHeld(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "privilege-held",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:6"
           });			
        addAnnotation
          (getOrgUnitBase_SystemAction(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "system-action",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:6"
           });		
        addAnnotation
          (getOrgUnitBase_AllocMethod(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "alloc-method"
           });			
        addAnnotation
          (getOrgUnitBase_Feature(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "feature"
           });			
        addAnnotation
          (getOrgUnitBase_Location(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "location"
           });		
        addAnnotation
          (getOrgUnitBase_Plugin(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "plugin"
           });			
        addAnnotation
          (orgUnitTypeEClass, 
           source, 
           new String[] {
             "name", "OrgUnitType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOrgUnitType_ChoiceGroup(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "choiceGroup:5"
           });		
        addAnnotation
          (getOrgUnitType_PositionFeature(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "position-feature",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:5"
           });		
        addAnnotation
          (getOrgUnitType_OrgUnitFeature(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "org-unit-feature",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:5"
           });			
        addAnnotation
          (positionEClass, 
           source, 
           new String[] {
             "name", "Position",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPosition_ChoiceGroup(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "choiceGroup:6"
           });			
        addAnnotation
          (getPosition_ReqCapability(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "req-capability",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:6"
           });			
        addAnnotation
          (getPosition_PrivilegeHeld(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "privilege-held",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:6"
           });			
        addAnnotation
          (getPosition_SystemAction(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "system-action",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:6"
           });		
        addAnnotation
          (getPosition_AllocMethod(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "alloc-method"
           });			
        addAnnotation
          (getPosition_Feature(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "feature"
           });		
        addAnnotation
          (getPosition_IdealNumber(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "ideal-number"
           });		
        addAnnotation
          (getPosition_Location(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "location"
           });		
        addAnnotation
          (getPosition_Plugin(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "plugin"
           });			
        addAnnotation
          (positionHoldingEClass, 
           source, 
           new String[] {
             "name", "PositionHolding",
             "kind", "empty"
           });		
        addAnnotation
          (getPositionHolding_Position(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "position"
           });			
        addAnnotation
          (positionTypeEClass, 
           source, 
           new String[] {
             "name", "PositionType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (privilegeEClass, 
           source, 
           new String[] {
             "name", "Privilege",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getPrivilege_Qualifier(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "qualifier",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (privilegeCategoryEClass, 
           source, 
           new String[] {
             "name", "PrivilegeCategory",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPrivilegeCategory_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:4"
           });			
        addAnnotation
          (getPrivilegeCategory_PrivilegeCategory(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "privilege-category",
             "namespace", "##targetNamespace",
             "group", "#group:4"
           });			
        addAnnotation
          (getPrivilegeCategory_Privilege(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "privilege",
             "namespace", "##targetNamespace",
             "group", "#group:4"
           });			
        addAnnotation
          (privilegeHoldingEClass, 
           source, 
           new String[] {
             "name", "PrivilegeHolding",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getPrivilegeHolding_Privilege(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "privilege"
           });			
        addAnnotation
          (qualifiedHoldingEClass, 
           source, 
           new String[] {
             "name", "QualifiedHolding",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getQualifiedHolding_String(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "string",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_Integer(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "integer",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_Decimal(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "decimal",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_Date(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "date",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_Time(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "time",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_DateTime(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "date-time",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_Boolean(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "boolean",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_Enum(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enum",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_EnumSet(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enum-set",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getQualifiedHolding_Element(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "element",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (qualifierEClass, 
           source, 
           new String[] {
             "name", "Qualifier",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getQualifier_Enum(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enum",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getQualifier_DataType(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "data-type"
           });			
        addAnnotation
          (resourceEClass, 
           source, 
           new String[] {
             "name", "Resource",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getResource_ChoiceGroup(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "choiceGroup:0"
           });		
        addAnnotation
          (getResource_CapabilityHeld(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "capability-held",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:0"
           });		
        addAnnotation
          (getResource_PositionHeld(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "position-held",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:0"
           });		
        addAnnotation
          (getResource_GroupHeld(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "group-held",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:0"
           });		
        addAnnotation
          (getResource_AttributeValue(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attribute-value",
             "namespace", "##targetNamespace",
             "group", "#choiceGroup:0"
           });		
        addAnnotation
          (getResource_EndDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "end-date"
           });		
        addAnnotation
          (getResource_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getResource_Label(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "label"
           });		
        addAnnotation
          (getResource_LdapAlias(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "ldap-alias"
           });		
        addAnnotation
          (getResource_LdapDn(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "ldap-dn"
           });		
        addAnnotation
          (getResource_Location(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "location"
           });		
        addAnnotation
          (getResource_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getResource_ResourceType(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "resource-type"
           });		
        addAnnotation
          (getResource_StartDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "start-date"
           });			
        addAnnotation
          (resourceTypeEEnum, 
           source, 
           new String[] {
             "name", "ResourceType"
           });		
        addAnnotation
          (resourceTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "ResourceType:Object",
             "baseType", "ResourceType"
           });			
        addAnnotation
          (systemActionEClass, 
           source, 
           new String[] {
             "name", "SystemAction",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getSystemAction_ReqPrivilege(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "req-privilege",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getSystemAction_Component(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "component"
           });		
        addAnnotation
          (getSystemAction_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });			
        addAnnotation
          (typedEntityEClass, 
           source, 
           new String[] {
             "name", "TypedEntity",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getTypedEntity_AttributeValue(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attribute-value",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getTypedEntity_Definition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "definition"
           });			
        addAnnotation
          (versionNumberEDataType, 
           source, 
           new String[] {
             "name", "VersionNumber",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#token"
           });
    }

} //DePackageImpl
