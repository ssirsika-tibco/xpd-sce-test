/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.brm.api.impl.N2BRMPackageImpl;

import com.tibco.n2.brm.workmodel.WorkmodelPackage;

import com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl;

import com.tibco.n2.common.api.exception.ExceptionPackage;

import com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl;

import com.tibco.n2.common.attributefacade.AttributefacadePackage;
import com.tibco.n2.common.attributefacade.impl.AttributefacadePackageImpl;
import com.tibco.n2.common.datamodel.AliasType;
import com.tibco.n2.common.datamodel.AliasTypeType;
import com.tibco.n2.common.datamodel.ComplexSpecType;
import com.tibco.n2.common.datamodel.DataModel;
import com.tibco.n2.common.datamodel.DatamodelFactory;
import com.tibco.n2.common.datamodel.DatamodelPackage;
import com.tibco.n2.common.datamodel.FieldType;
import com.tibco.n2.common.datamodel.SimpleSpecType;
import com.tibco.n2.common.datamodel.TypeType;
import com.tibco.n2.common.datamodel.WorkType;
import com.tibco.n2.common.datamodel.WorkTypeSpec;

import com.tibco.n2.common.datamodel.util.DatamodelValidator;
import com.tibco.n2.common.organisation.api.OrganisationPackage;

import com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl;

import com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage;

import com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl;

import com.tibco.n2.common.worktype.WorktypePackage;

import com.tibco.n2.common.worktype.impl.WorktypePackageImpl;

import com.tibco.xpd.script.descriptor.DescriptorPackage;
import com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DatamodelPackageImpl extends EPackageImpl implements DatamodelPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass aliasTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass complexSpecTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dataModelEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass fieldTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simpleSpecTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workTypeSpecEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum aliasTypeTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum typeTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType aliasDescriptionTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType aliasNameTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType aliasTypeTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType facadeNameTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType typeTypeObjectEDataType = null;

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
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private DatamodelPackageImpl() {
        super(eNS_URI, DatamodelFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link DatamodelPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static DatamodelPackage init() {
        if (isInited) return (DatamodelPackage)EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredDatamodelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        DatamodelPackageImpl theDatamodelPackage = registeredDatamodelPackage instanceof DatamodelPackageImpl ? (DatamodelPackageImpl)registeredDatamodelPackage : new DatamodelPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI);
        N2BRMPackageImpl theN2BRMPackage = (N2BRMPackageImpl)(registeredPackage instanceof N2BRMPackageImpl ? registeredPackage : N2BRMPackage.eINSTANCE);
        registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI);
        ExceptionPackageImpl theExceptionPackage = (ExceptionPackageImpl)(registeredPackage instanceof ExceptionPackageImpl ? registeredPackage : ExceptionPackage.eINSTANCE);
        registeredPackage = EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI);
        OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(registeredPackage instanceof OrganisationPackageImpl ? registeredPackage : OrganisationPackage.eINSTANCE);
        registeredPackage = EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI);
        WorkmodelPackageImpl theWorkmodelPackage = (WorkmodelPackageImpl)(registeredPackage instanceof WorkmodelPackageImpl ? registeredPackage : WorkmodelPackage.eINSTANCE);
        registeredPackage = EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI);
        WorktypePackageImpl theWorktypePackage = (WorktypePackageImpl)(registeredPackage instanceof WorktypePackageImpl ? registeredPackage : WorktypePackage.eINSTANCE);
        registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI);
        PageactivitymodelPackageImpl thePageactivitymodelPackage = (PageactivitymodelPackageImpl)(registeredPackage instanceof PageactivitymodelPackageImpl ? registeredPackage : PageactivitymodelPackage.eINSTANCE);
        registeredPackage = EPackage.Registry.INSTANCE.getEPackage(AttributefacadePackage.eNS_URI);
        AttributefacadePackageImpl theAttributefacadePackage = (AttributefacadePackageImpl)(registeredPackage instanceof AttributefacadePackageImpl ? registeredPackage : AttributefacadePackage.eINSTANCE);

        // Create package meta-data objects
        theDatamodelPackage.createPackageContents();
        theN2BRMPackage.createPackageContents();
        theExceptionPackage.createPackageContents();
        theOrganisationPackage.createPackageContents();
        theWorkmodelPackage.createPackageContents();
        theWorktypePackage.createPackageContents();
        thePageactivitymodelPackage.createPackageContents();
        theAttributefacadePackage.createPackageContents();

        // Initialize created meta-data
        theDatamodelPackage.initializePackageContents();
        theN2BRMPackage.initializePackageContents();
        theExceptionPackage.initializePackageContents();
        theOrganisationPackage.initializePackageContents();
        theWorkmodelPackage.initializePackageContents();
        theWorktypePackage.initializePackageContents();
        thePageactivitymodelPackage.initializePackageContents();
        theAttributefacadePackage.initializePackageContents();

        // Register package validator
        EValidator.Registry.INSTANCE.put
            (theDatamodelPackage,
             new EValidator.Descriptor() {
                 public EValidator getEValidator() {
                     return DatamodelValidator.INSTANCE;
                 }
             });

        // Mark meta-data to indicate it can't be changed
        theDatamodelPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(DatamodelPackage.eNS_URI, theDatamodelPackage);
        return theDatamodelPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAliasType() {
        return aliasTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAliasType_AliasDescription() {
        return (EAttribute)aliasTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAliasType_AliasName() {
        return (EAttribute)aliasTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAliasType_AliasType() {
        return (EAttribute)aliasTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAliasType_FacadeName() {
        return (EAttribute)aliasTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getComplexSpecType() {
        return complexSpecTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getComplexSpecType_Value() {
        return (EReference)complexSpecTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getComplexSpecType_ClassName() {
        return (EAttribute)complexSpecTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getComplexSpecType_GoRefId() {
        return (EAttribute)complexSpecTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getComplexSpecType_ClassVersion() {
        return (EAttribute)complexSpecTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDataModel() {
        return dataModelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDataModel_Inputs() {
        return (EReference)dataModelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDataModel_Outputs() {
        return (EReference)dataModelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDataModel_Inouts() {
        return (EReference)dataModelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getFieldType() {
        return fieldTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getFieldType_SimpleSpec() {
        return (EReference)fieldTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getFieldType_ComplexSpec() {
        return (EReference)fieldTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFieldType_Array() {
        return (EAttribute)fieldTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFieldType_Name() {
        return (EAttribute)fieldTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFieldType_Optional() {
        return (EAttribute)fieldTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFieldType_Type() {
        return (EAttribute)fieldTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimpleSpecType() {
        return simpleSpecTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimpleSpecType_Value() {
        return (EAttribute)simpleSpecTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimpleSpecType_Decimal() {
        return (EAttribute)simpleSpecTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimpleSpecType_Length() {
        return (EAttribute)simpleSpecTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkType() {
        return workTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_WorkTypeID() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_WorkTypeUID() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_WorkTypeDescription() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkType_DataModel() {
        return (EReference)workTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_TypePiled() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_PilingLimit() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_IgnoreIncomingData() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_ReofferOnClose() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_ReofferOnCancel() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkTypeSpec() {
        return workTypeSpecEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkTypeSpec_Version() {
        return (EAttribute)workTypeSpecEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkTypeSpec_WorkTypeDescription() {
        return (EAttribute)workTypeSpecEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkTypeSpec_WorkTypeID() {
        return (EAttribute)workTypeSpecEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAliasTypeType() {
        return aliasTypeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getTypeType() {
        return typeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAliasDescriptionType() {
        return aliasDescriptionTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAliasNameType() {
        return aliasNameTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAliasTypeTypeObject() {
        return aliasTypeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getFacadeNameType() {
        return facadeNameTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getTypeTypeObject() {
        return typeTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatamodelFactory getDatamodelFactory() {
        return (DatamodelFactory)getEFactoryInstance();
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
        aliasTypeEClass = createEClass(ALIAS_TYPE);
        createEAttribute(aliasTypeEClass, ALIAS_TYPE__ALIAS_DESCRIPTION);
        createEAttribute(aliasTypeEClass, ALIAS_TYPE__ALIAS_NAME);
        createEAttribute(aliasTypeEClass, ALIAS_TYPE__ALIAS_TYPE);
        createEAttribute(aliasTypeEClass, ALIAS_TYPE__FACADE_NAME);

        complexSpecTypeEClass = createEClass(COMPLEX_SPEC_TYPE);
        createEReference(complexSpecTypeEClass, COMPLEX_SPEC_TYPE__VALUE);
        createEAttribute(complexSpecTypeEClass, COMPLEX_SPEC_TYPE__CLASS_NAME);
        createEAttribute(complexSpecTypeEClass, COMPLEX_SPEC_TYPE__GO_REF_ID);
        createEAttribute(complexSpecTypeEClass, COMPLEX_SPEC_TYPE__CLASS_VERSION);

        dataModelEClass = createEClass(DATA_MODEL);
        createEReference(dataModelEClass, DATA_MODEL__INPUTS);
        createEReference(dataModelEClass, DATA_MODEL__OUTPUTS);
        createEReference(dataModelEClass, DATA_MODEL__INOUTS);

        fieldTypeEClass = createEClass(FIELD_TYPE);
        createEReference(fieldTypeEClass, FIELD_TYPE__SIMPLE_SPEC);
        createEReference(fieldTypeEClass, FIELD_TYPE__COMPLEX_SPEC);
        createEAttribute(fieldTypeEClass, FIELD_TYPE__ARRAY);
        createEAttribute(fieldTypeEClass, FIELD_TYPE__NAME);
        createEAttribute(fieldTypeEClass, FIELD_TYPE__OPTIONAL);
        createEAttribute(fieldTypeEClass, FIELD_TYPE__TYPE);

        simpleSpecTypeEClass = createEClass(SIMPLE_SPEC_TYPE);
        createEAttribute(simpleSpecTypeEClass, SIMPLE_SPEC_TYPE__VALUE);
        createEAttribute(simpleSpecTypeEClass, SIMPLE_SPEC_TYPE__DECIMAL);
        createEAttribute(simpleSpecTypeEClass, SIMPLE_SPEC_TYPE__LENGTH);

        workTypeEClass = createEClass(WORK_TYPE);
        createEAttribute(workTypeEClass, WORK_TYPE__WORK_TYPE_ID);
        createEAttribute(workTypeEClass, WORK_TYPE__WORK_TYPE_UID);
        createEAttribute(workTypeEClass, WORK_TYPE__WORK_TYPE_DESCRIPTION);
        createEReference(workTypeEClass, WORK_TYPE__DATA_MODEL);
        createEAttribute(workTypeEClass, WORK_TYPE__TYPE_PILED);
        createEAttribute(workTypeEClass, WORK_TYPE__PILING_LIMIT);
        createEAttribute(workTypeEClass, WORK_TYPE__IGNORE_INCOMING_DATA);
        createEAttribute(workTypeEClass, WORK_TYPE__REOFFER_ON_CLOSE);
        createEAttribute(workTypeEClass, WORK_TYPE__REOFFER_ON_CANCEL);

        workTypeSpecEClass = createEClass(WORK_TYPE_SPEC);
        createEAttribute(workTypeSpecEClass, WORK_TYPE_SPEC__VERSION);
        createEAttribute(workTypeSpecEClass, WORK_TYPE_SPEC__WORK_TYPE_DESCRIPTION);
        createEAttribute(workTypeSpecEClass, WORK_TYPE_SPEC__WORK_TYPE_ID);

        // Create enums
        aliasTypeTypeEEnum = createEEnum(ALIAS_TYPE_TYPE);
        typeTypeEEnum = createEEnum(TYPE_TYPE);

        // Create data types
        aliasDescriptionTypeEDataType = createEDataType(ALIAS_DESCRIPTION_TYPE);
        aliasNameTypeEDataType = createEDataType(ALIAS_NAME_TYPE);
        aliasTypeTypeObjectEDataType = createEDataType(ALIAS_TYPE_TYPE_OBJECT);
        facadeNameTypeEDataType = createEDataType(FACADE_NAME_TYPE);
        typeTypeObjectEDataType = createEDataType(TYPE_TYPE_OBJECT);
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

        // Initialize classes and features; add operations and parameters
        initEClass(aliasTypeEClass, AliasType.class, "AliasType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAliasType_AliasDescription(), this.getAliasDescriptionType(), "aliasDescription", null, 0, 1, AliasType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAliasType_AliasName(), this.getAliasNameType(), "aliasName", null, 1, 1, AliasType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAliasType_AliasType(), this.getAliasTypeType(), "aliasType", null, 1, 1, AliasType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAliasType_FacadeName(), this.getFacadeNameType(), "facadeName", null, 1, 1, AliasType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(complexSpecTypeEClass, ComplexSpecType.class, "ComplexSpecType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getComplexSpecType_Value(), ecorePackage.getEObject(), null, "value", null, 0, -1, ComplexSpecType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getComplexSpecType_ClassName(), theXMLTypePackage.getString(), "className", null, 0, 1, ComplexSpecType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getComplexSpecType_GoRefId(), theXMLTypePackage.getString(), "goRefId", null, 0, 1, ComplexSpecType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getComplexSpecType_ClassVersion(), theXMLTypePackage.getString(), "classVersion", null, 0, 1, ComplexSpecType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(dataModelEClass, DataModel.class, "DataModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDataModel_Inputs(), this.getFieldType(), null, "inputs", null, 0, -1, DataModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDataModel_Outputs(), this.getFieldType(), null, "outputs", null, 0, -1, DataModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDataModel_Inouts(), this.getFieldType(), null, "inouts", null, 0, -1, DataModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(fieldTypeEClass, FieldType.class, "FieldType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getFieldType_SimpleSpec(), this.getSimpleSpecType(), null, "simpleSpec", null, 0, 1, FieldType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getFieldType_ComplexSpec(), this.getComplexSpecType(), null, "complexSpec", null, 0, 1, FieldType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFieldType_Array(), theXMLTypePackage.getBoolean(), "array", null, 0, 1, FieldType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFieldType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, FieldType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFieldType_Optional(), theXMLTypePackage.getBoolean(), "optional", null, 0, 1, FieldType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFieldType_Type(), this.getTypeType(), "type", null, 1, 1, FieldType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(simpleSpecTypeEClass, SimpleSpecType.class, "SimpleSpecType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSimpleSpecType_Value(), theXMLTypePackage.getString(), "value", null, 0, -1, SimpleSpecType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSimpleSpecType_Decimal(), theXMLTypePackage.getInt(), "decimal", null, 0, 1, SimpleSpecType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSimpleSpecType_Length(), theXMLTypePackage.getInt(), "length", null, 0, 1, SimpleSpecType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workTypeEClass, WorkType.class, "WorkType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkType_WorkTypeID(), theXMLTypePackage.getLong(), "workTypeID", null, 0, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkType_WorkTypeUID(), theXMLTypePackage.getString(), "workTypeUID", null, 1, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkType_WorkTypeDescription(), theXMLTypePackage.getString(), "workTypeDescription", null, 1, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkType_DataModel(), this.getDataModel(), null, "dataModel", null, 1, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkType_TypePiled(), theXMLTypePackage.getBoolean(), "typePiled", null, 1, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkType_PilingLimit(), theXMLTypePackage.getInt(), "pilingLimit", null, 0, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkType_IgnoreIncomingData(), theXMLTypePackage.getBoolean(), "ignoreIncomingData", "true", 0, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkType_ReofferOnClose(), theXMLTypePackage.getBoolean(), "reofferOnClose", null, 0, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkType_ReofferOnCancel(), theXMLTypePackage.getBoolean(), "reofferOnCancel", null, 0, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workTypeSpecEClass, WorkTypeSpec.class, "WorkTypeSpec", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkTypeSpec_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, WorkTypeSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkTypeSpec_WorkTypeDescription(), theXMLTypePackage.getString(), "workTypeDescription", null, 1, 1, WorkTypeSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkTypeSpec_WorkTypeID(), theXMLTypePackage.getString(), "workTypeID", null, 1, 1, WorkTypeSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(aliasTypeTypeEEnum, AliasTypeType.class, "AliasTypeType");
        addEEnumLiteral(aliasTypeTypeEEnum, AliasTypeType.STRING);
        addEEnumLiteral(aliasTypeTypeEEnum, AliasTypeType.DECIMAL_NUMBER);
        addEEnumLiteral(aliasTypeTypeEEnum, AliasTypeType.INTEGER_NUMBER);
        addEEnumLiteral(aliasTypeTypeEEnum, AliasTypeType.DATE_TIME);

        initEEnum(typeTypeEEnum, TypeType.class, "TypeType");
        addEEnumLiteral(typeTypeEEnum, TypeType.STRING);
        addEEnumLiteral(typeTypeEEnum, TypeType.DECIMAL_NUMBER);
        addEEnumLiteral(typeTypeEEnum, TypeType.INTEGER_NUMBER);
        addEEnumLiteral(typeTypeEEnum, TypeType.BOOLEAN);
        addEEnumLiteral(typeTypeEEnum, TypeType.DATE);
        addEEnumLiteral(typeTypeEEnum, TypeType.TIME);
        addEEnumLiteral(typeTypeEEnum, TypeType.DATE_TIME);
        addEEnumLiteral(typeTypeEEnum, TypeType.PERFORMER);
        addEEnumLiteral(typeTypeEEnum, TypeType.COMPLEX);
        addEEnumLiteral(typeTypeEEnum, TypeType.DATA_REFERENCE);

        // Initialize data types
        initEDataType(aliasDescriptionTypeEDataType, String.class, "AliasDescriptionType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(aliasNameTypeEDataType, String.class, "AliasNameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(aliasTypeTypeObjectEDataType, AliasTypeType.class, "AliasTypeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(facadeNameTypeEDataType, String.class, "FacadeNameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(typeTypeObjectEDataType, TypeType.class, "TypeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

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
          (aliasDescriptionTypeEDataType,
           source,
           new String[] {
               "name", "aliasDescription_._type",
               "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
               "maxLength", "256"
           });
        addAnnotation
          (aliasNameTypeEDataType,
           source,
           new String[] {
               "name", "aliasName_._type",
               "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
               "maxLength", "64"
           });
        addAnnotation
          (aliasTypeEClass,
           source,
           new String[] {
               "name", "AliasType",
               "kind", "empty"
           });
        addAnnotation
          (getAliasType_AliasDescription(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "aliasDescription"
           });
        addAnnotation
          (getAliasType_AliasName(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "aliasName"
           });
        addAnnotation
          (getAliasType_AliasType(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "aliasType"
           });
        addAnnotation
          (getAliasType_FacadeName(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "facadeName"
           });
        addAnnotation
          (aliasTypeTypeEEnum,
           source,
           new String[] {
               "name", "aliasType_._type"
           });
        addAnnotation
          (aliasTypeTypeObjectEDataType,
           source,
           new String[] {
               "name", "aliasType_._type:Object",
               "baseType", "aliasType_._type"
           });
        addAnnotation
          (complexSpecTypeEClass,
           source,
           new String[] {
               "name", "complexSpec_._type",
               "kind", "elementOnly"
           });
        addAnnotation
          (getComplexSpecType_Value(),
           source,
           new String[] {
               "kind", "element",
               "name", "value"
           });
        addAnnotation
          (getComplexSpecType_ClassName(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "className"
           });
        addAnnotation
          (getComplexSpecType_GoRefId(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "goRefId"
           });
        addAnnotation
          (getComplexSpecType_ClassVersion(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "classVersion"
           });
        addAnnotation
          (dataModelEClass,
           source,
           new String[] {
               "name", "DataModel",
               "kind", "elementOnly"
           });
        addAnnotation
          (getDataModel_Inputs(),
           source,
           new String[] {
               "kind", "element",
               "name", "inputs"
           });
        addAnnotation
          (getDataModel_Outputs(),
           source,
           new String[] {
               "kind", "element",
               "name", "outputs"
           });
        addAnnotation
          (getDataModel_Inouts(),
           source,
           new String[] {
               "kind", "element",
               "name", "inouts"
           });
        addAnnotation
          (facadeNameTypeEDataType,
           source,
           new String[] {
               "name", "facadeName_._type",
               "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
               "maxLength", "64"
           });
        addAnnotation
          (fieldTypeEClass,
           source,
           new String[] {
               "name", "FieldType",
               "kind", "elementOnly"
           });
        addAnnotation
          (getFieldType_SimpleSpec(),
           source,
           new String[] {
               "kind", "element",
               "name", "simpleSpec"
           });
        addAnnotation
          (getFieldType_ComplexSpec(),
           source,
           new String[] {
               "kind", "element",
               "name", "complexSpec"
           });
        addAnnotation
          (getFieldType_Array(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "array"
           });
        addAnnotation
          (getFieldType_Name(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "name"
           });
        addAnnotation
          (getFieldType_Optional(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "optional"
           });
        addAnnotation
          (getFieldType_Type(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "type"
           });
        addAnnotation
          (simpleSpecTypeEClass,
           source,
           new String[] {
               "name", "simpleSpec_._type",
               "kind", "elementOnly"
           });
        addAnnotation
          (getSimpleSpecType_Value(),
           source,
           new String[] {
               "kind", "element",
               "name", "value"
           });
        addAnnotation
          (getSimpleSpecType_Decimal(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "decimal"
           });
        addAnnotation
          (getSimpleSpecType_Length(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "length"
           });
        addAnnotation
          (typeTypeEEnum,
           source,
           new String[] {
               "name", "type_._type"
           });
        addAnnotation
          (typeTypeObjectEDataType,
           source,
           new String[] {
               "name", "type_._type:Object",
               "baseType", "type_._type"
           });
        addAnnotation
          (workTypeEClass,
           source,
           new String[] {
               "name", "WorkType",
               "kind", "elementOnly"
           });
        addAnnotation
          (getWorkType_WorkTypeID(),
           source,
           new String[] {
               "kind", "element",
               "name", "workTypeID"
           });
        addAnnotation
          (getWorkType_WorkTypeUID(),
           source,
           new String[] {
               "kind", "element",
               "name", "workTypeUID"
           });
        addAnnotation
          (getWorkType_WorkTypeDescription(),
           source,
           new String[] {
               "kind", "element",
               "name", "workTypeDescription"
           });
        addAnnotation
          (getWorkType_DataModel(),
           source,
           new String[] {
               "kind", "element",
               "name", "dataModel"
           });
        addAnnotation
          (getWorkType_TypePiled(),
           source,
           new String[] {
               "kind", "element",
               "name", "typePiled"
           });
        addAnnotation
          (getWorkType_PilingLimit(),
           source,
           new String[] {
               "kind", "element",
               "name", "pilingLimit"
           });
        addAnnotation
          (getWorkType_IgnoreIncomingData(),
           source,
           new String[] {
               "kind", "element",
               "name", "ignoreIncomingData"
           });
        addAnnotation
          (getWorkType_ReofferOnClose(),
           source,
           new String[] {
               "kind", "element",
               "name", "reofferOnClose"
           });
        addAnnotation
          (getWorkType_ReofferOnCancel(),
           source,
           new String[] {
               "kind", "element",
               "name", "reofferOnCancel"
           });
        addAnnotation
          (workTypeSpecEClass,
           source,
           new String[] {
               "name", "WorkTypeSpec",
               "kind", "empty"
           });
        addAnnotation
          (getWorkTypeSpec_Version(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "version"
           });
        addAnnotation
          (getWorkTypeSpec_WorkTypeDescription(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "workTypeDescription"
           });
        addAnnotation
          (getWorkTypeSpec_WorkTypeID(),
           source,
           new String[] {
               "kind", "attribute",
               "name", "workTypeID"
           });
    }

} //DatamodelPackageImpl
