/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.brm.api.impl.N2BRMPackageImpl;

import com.tibco.n2.brm.workmodel.WorkmodelPackage;

import com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl;

import com.tibco.n2.common.api.exception.ExceptionPackage;

import com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl;

import com.tibco.n2.common.datamodel.DatamodelPackage;

import com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl;

import com.tibco.n2.common.organisation.api.OrganisationFactory;
import com.tibco.n2.common.organisation.api.OrganisationPackage;
import com.tibco.n2.common.organisation.api.OrganisationalEntityType;
import com.tibco.n2.common.organisation.api.QualifierSetType;
import com.tibco.n2.common.organisation.api.XmlCalendarAssignment;
import com.tibco.n2.common.organisation.api.XmlCalendarRef;
import com.tibco.n2.common.organisation.api.XmlExecuteQuery;
import com.tibco.n2.common.organisation.api.XmlModelEntityId;
import com.tibco.n2.common.organisation.api.XmlOrgModelVersion;
import com.tibco.n2.common.organisation.api.XmlParticipantId;
import com.tibco.n2.common.organisation.api.XmlResourceQuery;

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

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OrganisationPackageImpl extends EPackageImpl implements OrganisationPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass qualifierSetTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass xmlCalendarAssignmentEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass xmlCalendarRefEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass xmlExecuteQueryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass xmlModelEntityIdEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass xmlOrgModelVersionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass xmlParticipantIdEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass xmlResourceQueryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum organisationalEntityTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType organisationalEntityTypeObjectEDataType = null;

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
     * @see com.tibco.n2.common.organisation.api.OrganisationPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private OrganisationPackageImpl() {
        super(eNS_URI, OrganisationFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link OrganisationPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static OrganisationPackage init() {
        if (isInited) return (OrganisationPackage)EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI);

        // Obtain or create and register package
        OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new OrganisationPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        N2BRMPackageImpl theN2BRMPackage = (N2BRMPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI) instanceof N2BRMPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI) : N2BRMPackage.eINSTANCE);
        DatamodelPackageImpl theDatamodelPackage = (DatamodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) instanceof DatamodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) : DatamodelPackage.eINSTANCE);
        ExceptionPackageImpl theExceptionPackage = (ExceptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI) instanceof ExceptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI) : ExceptionPackage.eINSTANCE);
        DescriptorPackageImpl theDescriptorPackage = (DescriptorPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI) instanceof DescriptorPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI) : DescriptorPackage.eINSTANCE);
        WorkmodelPackageImpl theWorkmodelPackage = (WorkmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI) instanceof WorkmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI) : WorkmodelPackage.eINSTANCE);
        WorktypePackageImpl theWorktypePackage = (WorktypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI) instanceof WorktypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI) : WorktypePackage.eINSTANCE);
        PageactivitymodelPackageImpl thePageactivitymodelPackage = (PageactivitymodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) instanceof PageactivitymodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) : PageactivitymodelPackage.eINSTANCE);

        // Create package meta-data objects
        theOrganisationPackage.createPackageContents();
        theN2BRMPackage.createPackageContents();
        theDatamodelPackage.createPackageContents();
        theExceptionPackage.createPackageContents();
        theDescriptorPackage.createPackageContents();
        theWorkmodelPackage.createPackageContents();
        theWorktypePackage.createPackageContents();
        thePageactivitymodelPackage.createPackageContents();

        // Initialize created meta-data
        theOrganisationPackage.initializePackageContents();
        theN2BRMPackage.initializePackageContents();
        theDatamodelPackage.initializePackageContents();
        theExceptionPackage.initializePackageContents();
        theDescriptorPackage.initializePackageContents();
        theWorkmodelPackage.initializePackageContents();
        theWorktypePackage.initializePackageContents();
        thePageactivitymodelPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theOrganisationPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(OrganisationPackage.eNS_URI, theOrganisationPackage);
        return theOrganisationPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getQualifierSetType() {
        return qualifierSetTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getQualifierSetType_Value() {
        return (EAttribute)qualifierSetTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getXmlCalendarAssignment() {
        return xmlCalendarAssignmentEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlCalendarAssignment_CalendarAlias() {
        return (EAttribute)xmlCalendarAssignmentEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlCalendarAssignment_EntityGuid() {
        return (EAttribute)xmlCalendarAssignmentEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getXmlCalendarRef() {
        return xmlCalendarRefEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlCalendarRef_CalendarAlias() {
        return (EAttribute)xmlCalendarRefEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlCalendarRef_EntityGuid() {
        return (EAttribute)xmlCalendarRefEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlCalendarRef_EntityLabel() {
        return (EAttribute)xmlCalendarRefEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlCalendarRef_EntityName() {
        return (EAttribute)xmlCalendarRefEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlCalendarRef_EntityType() {
        return (EAttribute)xmlCalendarRefEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getXmlExecuteQuery() {
        return xmlExecuteQueryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlExecuteQuery_Implementation() {
        return (EAttribute)xmlExecuteQueryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlExecuteQuery_SingleRandomResult() {
        return (EAttribute)xmlExecuteQueryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getXmlModelEntityId() {
        return xmlModelEntityIdEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getXmlModelEntityId_QualifierSet() {
        return (EReference)xmlModelEntityIdEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlModelEntityId_EntityType() {
        return (EAttribute)xmlModelEntityIdEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlModelEntityId_Guid() {
        return (EAttribute)xmlModelEntityIdEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlModelEntityId_Qualifier() {
        return (EAttribute)xmlModelEntityIdEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getXmlOrgModelVersion() {
        return xmlOrgModelVersionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlOrgModelVersion_ModelVersion() {
        return (EAttribute)xmlOrgModelVersionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getXmlParticipantId() {
        return xmlParticipantIdEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlParticipantId_Guid() {
        return (EAttribute)xmlParticipantIdEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlParticipantId_Name() {
        return (EAttribute)xmlParticipantIdEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlParticipantId_EntityType() {
        return (EAttribute)xmlParticipantIdEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlParticipantId_Qualifier() {
        return (EAttribute)xmlParticipantIdEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getXmlResourceQuery() {
        return xmlResourceQueryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getXmlResourceQuery_Query() {
        return (EAttribute)xmlResourceQueryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getOrganisationalEntityType() {
        return organisationalEntityTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getOrganisationalEntityTypeObject() {
        return organisationalEntityTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationFactory getOrganisationFactory() {
        return (OrganisationFactory)getEFactoryInstance();
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
        qualifierSetTypeEClass = createEClass(QUALIFIER_SET_TYPE);
        createEAttribute(qualifierSetTypeEClass, QUALIFIER_SET_TYPE__VALUE);

        xmlCalendarAssignmentEClass = createEClass(XML_CALENDAR_ASSIGNMENT);
        createEAttribute(xmlCalendarAssignmentEClass, XML_CALENDAR_ASSIGNMENT__CALENDAR_ALIAS);
        createEAttribute(xmlCalendarAssignmentEClass, XML_CALENDAR_ASSIGNMENT__ENTITY_GUID);

        xmlCalendarRefEClass = createEClass(XML_CALENDAR_REF);
        createEAttribute(xmlCalendarRefEClass, XML_CALENDAR_REF__CALENDAR_ALIAS);
        createEAttribute(xmlCalendarRefEClass, XML_CALENDAR_REF__ENTITY_GUID);
        createEAttribute(xmlCalendarRefEClass, XML_CALENDAR_REF__ENTITY_LABEL);
        createEAttribute(xmlCalendarRefEClass, XML_CALENDAR_REF__ENTITY_NAME);
        createEAttribute(xmlCalendarRefEClass, XML_CALENDAR_REF__ENTITY_TYPE);

        xmlExecuteQueryEClass = createEClass(XML_EXECUTE_QUERY);
        createEAttribute(xmlExecuteQueryEClass, XML_EXECUTE_QUERY__IMPLEMENTATION);
        createEAttribute(xmlExecuteQueryEClass, XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT);

        xmlModelEntityIdEClass = createEClass(XML_MODEL_ENTITY_ID);
        createEReference(xmlModelEntityIdEClass, XML_MODEL_ENTITY_ID__QUALIFIER_SET);
        createEAttribute(xmlModelEntityIdEClass, XML_MODEL_ENTITY_ID__ENTITY_TYPE);
        createEAttribute(xmlModelEntityIdEClass, XML_MODEL_ENTITY_ID__GUID);
        createEAttribute(xmlModelEntityIdEClass, XML_MODEL_ENTITY_ID__QUALIFIER);

        xmlOrgModelVersionEClass = createEClass(XML_ORG_MODEL_VERSION);
        createEAttribute(xmlOrgModelVersionEClass, XML_ORG_MODEL_VERSION__MODEL_VERSION);

        xmlParticipantIdEClass = createEClass(XML_PARTICIPANT_ID);
        createEAttribute(xmlParticipantIdEClass, XML_PARTICIPANT_ID__GUID);
        createEAttribute(xmlParticipantIdEClass, XML_PARTICIPANT_ID__NAME);
        createEAttribute(xmlParticipantIdEClass, XML_PARTICIPANT_ID__ENTITY_TYPE);
        createEAttribute(xmlParticipantIdEClass, XML_PARTICIPANT_ID__QUALIFIER);

        xmlResourceQueryEClass = createEClass(XML_RESOURCE_QUERY);
        createEAttribute(xmlResourceQueryEClass, XML_RESOURCE_QUERY__QUERY);

        // Create enums
        organisationalEntityTypeEEnum = createEEnum(ORGANISATIONAL_ENTITY_TYPE);

        // Create data types
        organisationalEntityTypeObjectEDataType = createEDataType(ORGANISATIONAL_ENTITY_TYPE_OBJECT);
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
        xmlCalendarAssignmentEClass.getESuperTypes().add(this.getXmlOrgModelVersion());
        xmlCalendarRefEClass.getESuperTypes().add(this.getXmlOrgModelVersion());
        xmlExecuteQueryEClass.getESuperTypes().add(this.getXmlResourceQuery());
        xmlModelEntityIdEClass.getESuperTypes().add(this.getXmlOrgModelVersion());
        xmlParticipantIdEClass.getESuperTypes().add(this.getXmlOrgModelVersion());
        xmlResourceQueryEClass.getESuperTypes().add(this.getXmlOrgModelVersion());

        // Initialize classes and features; add operations and parameters
        initEClass(qualifierSetTypeEClass, QualifierSetType.class, "QualifierSetType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getQualifierSetType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, QualifierSetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(xmlCalendarAssignmentEClass, XmlCalendarAssignment.class, "XmlCalendarAssignment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXmlCalendarAssignment_CalendarAlias(), theXMLTypePackage.getString(), "calendarAlias", null, 0, 1, XmlCalendarAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlCalendarAssignment_EntityGuid(), theXMLTypePackage.getString(), "entityGuid", null, 1, 1, XmlCalendarAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(xmlCalendarRefEClass, XmlCalendarRef.class, "XmlCalendarRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXmlCalendarRef_CalendarAlias(), theXMLTypePackage.getString(), "calendarAlias", null, 1, 1, XmlCalendarRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlCalendarRef_EntityGuid(), theXMLTypePackage.getString(), "entityGuid", null, 1, 1, XmlCalendarRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlCalendarRef_EntityLabel(), theXMLTypePackage.getString(), "entityLabel", null, 0, 1, XmlCalendarRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlCalendarRef_EntityName(), theXMLTypePackage.getString(), "entityName", null, 0, 1, XmlCalendarRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlCalendarRef_EntityType(), this.getOrganisationalEntityType(), "entityType", null, 1, 1, XmlCalendarRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(xmlExecuteQueryEClass, XmlExecuteQuery.class, "XmlExecuteQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXmlExecuteQuery_Implementation(), theXMLTypePackage.getInt(), "implementation", "1", 0, 1, XmlExecuteQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlExecuteQuery_SingleRandomResult(), theXMLTypePackage.getBoolean(), "singleRandomResult", null, 0, 1, XmlExecuteQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(xmlModelEntityIdEClass, XmlModelEntityId.class, "XmlModelEntityId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getXmlModelEntityId_QualifierSet(), this.getQualifierSetType(), null, "qualifierSet", null, 0, -1, XmlModelEntityId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlModelEntityId_EntityType(), this.getOrganisationalEntityType(), "entityType", null, 1, 1, XmlModelEntityId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlModelEntityId_Guid(), theXMLTypePackage.getString(), "guid", null, 1, 1, XmlModelEntityId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlModelEntityId_Qualifier(), theXMLTypePackage.getString(), "qualifier", null, 0, 1, XmlModelEntityId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(xmlOrgModelVersionEClass, XmlOrgModelVersion.class, "XmlOrgModelVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXmlOrgModelVersion_ModelVersion(), theXMLTypePackage.getInt(), "modelVersion", "-1", 0, 1, XmlOrgModelVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(xmlParticipantIdEClass, XmlParticipantId.class, "XmlParticipantId", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXmlParticipantId_Guid(), theXMLTypePackage.getString(), "guid", null, 0, 1, XmlParticipantId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlParticipantId_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, XmlParticipantId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlParticipantId_EntityType(), this.getOrganisationalEntityType(), "entityType", null, 0, 1, XmlParticipantId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getXmlParticipantId_Qualifier(), theXMLTypePackage.getString(), "qualifier", null, 0, 1, XmlParticipantId.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(xmlResourceQueryEClass, XmlResourceQuery.class, "XmlResourceQuery", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getXmlResourceQuery_Query(), theXMLTypePackage.getString(), "query", null, 1, 1, XmlResourceQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(organisationalEntityTypeEEnum, OrganisationalEntityType.class, "OrganisationalEntityType");
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.ORGANIZATION);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.ORGANIZATIONALUNIT);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.GROUP);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.POSITION);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.PRIVILEGE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.CAPABILITY);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.RESOURCE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.LOCATION);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.ORGANIZATIONTYPE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.ORGANIZATIONALUNITTYPE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.POSITIONTYPE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.LOCATIONTYPE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.ORGUNITRELATIONSHIPTYPE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.POSITIONHELD);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.ORGUNITRELATIONSHIP);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.ORGUNITFEATURE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.POSITIONFEATURE);
        addEEnumLiteral(organisationalEntityTypeEEnum, OrganisationalEntityType.PARAMETERDESCRIPTOR);

        // Initialize data types
        initEDataType(organisationalEntityTypeObjectEDataType, OrganisationalEntityType.class, "OrganisationalEntityTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

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
          (organisationalEntityTypeEEnum, 
           source, 
           new String[] {
             "name", "OrganisationalEntityType"
           });		
        addAnnotation
          (organisationalEntityTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "OrganisationalEntityType:Object",
             "baseType", "OrganisationalEntityType"
           });		
        addAnnotation
          (qualifierSetTypeEClass, 
           source, 
           new String[] {
             "name", "qualifierSet_._type",
             "kind", "empty"
           });		
        addAnnotation
          (getQualifierSetType_Value(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "value"
           });			
        addAnnotation
          (xmlCalendarAssignmentEClass, 
           source, 
           new String[] {
             "name", "XmlCalendarAssignment",
             "kind", "empty"
           });			
        addAnnotation
          (getXmlCalendarAssignment_CalendarAlias(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "calendar-alias"
           });			
        addAnnotation
          (getXmlCalendarAssignment_EntityGuid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "entity-guid"
           });			
        addAnnotation
          (xmlCalendarRefEClass, 
           source, 
           new String[] {
             "name", "XmlCalendarRef",
             "kind", "empty"
           });			
        addAnnotation
          (getXmlCalendarRef_CalendarAlias(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "calendar-alias"
           });			
        addAnnotation
          (getXmlCalendarRef_EntityGuid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "entity-guid"
           });			
        addAnnotation
          (getXmlCalendarRef_EntityLabel(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "entity-label"
           });			
        addAnnotation
          (getXmlCalendarRef_EntityName(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "entity-name"
           });			
        addAnnotation
          (getXmlCalendarRef_EntityType(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "entity-type"
           });			
        addAnnotation
          (xmlExecuteQueryEClass, 
           source, 
           new String[] {
             "name", "XmlExecuteQuery",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getXmlExecuteQuery_Implementation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "implementation"
           });			
        addAnnotation
          (getXmlExecuteQuery_SingleRandomResult(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "singleRandomResult"
           });			
        addAnnotation
          (xmlModelEntityIdEClass, 
           source, 
           new String[] {
             "name", "XmlModelEntityId",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getXmlModelEntityId_QualifierSet(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "qualifierSet"
           });			
        addAnnotation
          (getXmlModelEntityId_EntityType(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "entity-type"
           });			
        addAnnotation
          (getXmlModelEntityId_Guid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "guid"
           });			
        addAnnotation
          (getXmlModelEntityId_Qualifier(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "qualifier"
           });			
        addAnnotation
          (xmlOrgModelVersionEClass, 
           source, 
           new String[] {
             "name", "XmlOrgModelVersion",
             "kind", "empty"
           });			
        addAnnotation
          (getXmlOrgModelVersion_ModelVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "model-version"
           });			
        addAnnotation
          (xmlParticipantIdEClass, 
           source, 
           new String[] {
             "name", "XmlParticipantId",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getXmlParticipantId_Guid(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "guid"
           });			
        addAnnotation
          (getXmlParticipantId_Name(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "name"
           });			
        addAnnotation
          (getXmlParticipantId_EntityType(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "entity-type"
           });			
        addAnnotation
          (getXmlParticipantId_Qualifier(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "qualifier"
           });			
        addAnnotation
          (xmlResourceQueryEClass, 
           source, 
           new String[] {
             "name", "XmlResourceQuery",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getXmlResourceQuery_Query(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "query"
           });
    }

} //OrganisationPackageImpl
