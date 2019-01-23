/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.organisation.api.OrganisationFactory
 * @model kind="package"
 * @generated
 */
public interface OrganisationPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "api";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://api.organisation.common.n2.tibco.com";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "api";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    OrganisationPackage eINSTANCE = com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.impl.QualifierSetTypeImpl <em>Qualifier Set Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.impl.QualifierSetTypeImpl
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getQualifierSetType()
     * @generated
     */
    int QUALIFIER_SET_TYPE = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIER_SET_TYPE__VALUE = 0;

    /**
     * The number of structural features of the '<em>Qualifier Set Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIER_SET_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.impl.XmlOrgModelVersionImpl <em>Xml Org Model Version</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.impl.XmlOrgModelVersionImpl
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlOrgModelVersion()
     * @generated
     */
    int XML_ORG_MODEL_VERSION = 5;

    /**
     * The feature id for the '<em><b>Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_ORG_MODEL_VERSION__MODEL_VERSION = 0;

    /**
     * The number of structural features of the '<em>Xml Org Model Version</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_ORG_MODEL_VERSION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarAssignmentImpl <em>Xml Calendar Assignment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.impl.XmlCalendarAssignmentImpl
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlCalendarAssignment()
     * @generated
     */
    int XML_CALENDAR_ASSIGNMENT = 1;

    /**
     * The feature id for the '<em><b>Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_ASSIGNMENT__MODEL_VERSION = XML_ORG_MODEL_VERSION__MODEL_VERSION;

    /**
     * The feature id for the '<em><b>Calendar Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_ASSIGNMENT__CALENDAR_ALIAS = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Entity Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_ASSIGNMENT__ENTITY_GUID = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Xml Calendar Assignment</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_ASSIGNMENT_FEATURE_COUNT = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl <em>Xml Calendar Ref</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlCalendarRef()
     * @generated
     */
    int XML_CALENDAR_REF = 2;

    /**
     * The feature id for the '<em><b>Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_REF__MODEL_VERSION = XML_ORG_MODEL_VERSION__MODEL_VERSION;

    /**
     * The feature id for the '<em><b>Calendar Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_REF__CALENDAR_ALIAS = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Entity Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_REF__ENTITY_GUID = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Entity Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_REF__ENTITY_LABEL = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Entity Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_REF__ENTITY_NAME = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Entity Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_REF__ENTITY_TYPE = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Xml Calendar Ref</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_CALENDAR_REF_FEATURE_COUNT = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.impl.XmlResourceQueryImpl <em>Xml Resource Query</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.impl.XmlResourceQueryImpl
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlResourceQuery()
     * @generated
     */
    int XML_RESOURCE_QUERY = 7;

    /**
     * The feature id for the '<em><b>Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_RESOURCE_QUERY__MODEL_VERSION = XML_ORG_MODEL_VERSION__MODEL_VERSION;

    /**
     * The feature id for the '<em><b>Query</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_RESOURCE_QUERY__QUERY = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Xml Resource Query</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_RESOURCE_QUERY_FEATURE_COUNT = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.impl.XmlExecuteQueryImpl <em>Xml Execute Query</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.impl.XmlExecuteQueryImpl
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlExecuteQuery()
     * @generated
     */
    int XML_EXECUTE_QUERY = 3;

    /**
     * The feature id for the '<em><b>Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_EXECUTE_QUERY__MODEL_VERSION = XML_RESOURCE_QUERY__MODEL_VERSION;

    /**
     * The feature id for the '<em><b>Query</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_EXECUTE_QUERY__QUERY = XML_RESOURCE_QUERY__QUERY;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_EXECUTE_QUERY__IMPLEMENTATION = XML_RESOURCE_QUERY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Single Random Result</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT = XML_RESOURCE_QUERY_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Xml Execute Query</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_EXECUTE_QUERY_FEATURE_COUNT = XML_RESOURCE_QUERY_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.impl.XmlModelEntityIdImpl <em>Xml Model Entity Id</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.impl.XmlModelEntityIdImpl
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlModelEntityId()
     * @generated
     */
    int XML_MODEL_ENTITY_ID = 4;

    /**
     * The feature id for the '<em><b>Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MODEL_ENTITY_ID__MODEL_VERSION = XML_ORG_MODEL_VERSION__MODEL_VERSION;

    /**
     * The feature id for the '<em><b>Qualifier Set</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MODEL_ENTITY_ID__QUALIFIER_SET = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Entity Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MODEL_ENTITY_ID__ENTITY_TYPE = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MODEL_ENTITY_ID__GUID = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Qualifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MODEL_ENTITY_ID__QUALIFIER = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Xml Model Entity Id</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MODEL_ENTITY_ID_FEATURE_COUNT = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.impl.XmlParticipantIdImpl <em>Xml Participant Id</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.impl.XmlParticipantIdImpl
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlParticipantId()
     * @generated
     */
    int XML_PARTICIPANT_ID = 6;

    /**
     * The feature id for the '<em><b>Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_PARTICIPANT_ID__MODEL_VERSION = XML_ORG_MODEL_VERSION__MODEL_VERSION;

    /**
     * The feature id for the '<em><b>Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_PARTICIPANT_ID__GUID = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_PARTICIPANT_ID__NAME = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Entity Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_PARTICIPANT_ID__ENTITY_TYPE = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Qualifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_PARTICIPANT_ID__QUALIFIER = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Xml Participant Id</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_PARTICIPANT_ID_FEATURE_COUNT = XML_ORG_MODEL_VERSION_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.organisation.api.OrganisationalEntityType <em>Organisational Entity Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getOrganisationalEntityType()
     * @generated
     */
    int ORGANISATIONAL_ENTITY_TYPE = 8;

    /**
     * The meta object id for the '<em>Organisational Entity Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
     * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getOrganisationalEntityTypeObject()
     * @generated
     */
    int ORGANISATIONAL_ENTITY_TYPE_OBJECT = 9;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.organisation.api.QualifierSetType <em>Qualifier Set Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Qualifier Set Type</em>'.
     * @see com.tibco.n2.common.organisation.api.QualifierSetType
     * @generated
     */
    EClass getQualifierSetType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.QualifierSetType#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.n2.common.organisation.api.QualifierSetType#getValue()
     * @see #getQualifierSetType()
     * @generated
     */
    EAttribute getQualifierSetType_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.organisation.api.XmlCalendarAssignment <em>Xml Calendar Assignment</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xml Calendar Assignment</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarAssignment
     * @generated
     */
    EClass getXmlCalendarAssignment();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlCalendarAssignment#getCalendarAlias <em>Calendar Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Calendar Alias</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarAssignment#getCalendarAlias()
     * @see #getXmlCalendarAssignment()
     * @generated
     */
    EAttribute getXmlCalendarAssignment_CalendarAlias();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlCalendarAssignment#getEntityGuid <em>Entity Guid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Entity Guid</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarAssignment#getEntityGuid()
     * @see #getXmlCalendarAssignment()
     * @generated
     */
    EAttribute getXmlCalendarAssignment_EntityGuid();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef <em>Xml Calendar Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xml Calendar Ref</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarRef
     * @generated
     */
    EClass getXmlCalendarRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getCalendarAlias <em>Calendar Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Calendar Alias</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarRef#getCalendarAlias()
     * @see #getXmlCalendarRef()
     * @generated
     */
    EAttribute getXmlCalendarRef_CalendarAlias();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityGuid <em>Entity Guid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Entity Guid</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityGuid()
     * @see #getXmlCalendarRef()
     * @generated
     */
    EAttribute getXmlCalendarRef_EntityGuid();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityLabel <em>Entity Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Entity Label</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityLabel()
     * @see #getXmlCalendarRef()
     * @generated
     */
    EAttribute getXmlCalendarRef_EntityLabel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityName <em>Entity Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Entity Name</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityName()
     * @see #getXmlCalendarRef()
     * @generated
     */
    EAttribute getXmlCalendarRef_EntityName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityType <em>Entity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Entity Type</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlCalendarRef#getEntityType()
     * @see #getXmlCalendarRef()
     * @generated
     */
    EAttribute getXmlCalendarRef_EntityType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery <em>Xml Execute Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xml Execute Query</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlExecuteQuery
     * @generated
     */
    EClass getXmlExecuteQuery();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlExecuteQuery#getImplementation()
     * @see #getXmlExecuteQuery()
     * @generated
     */
    EAttribute getXmlExecuteQuery_Implementation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlExecuteQuery#isSingleRandomResult <em>Single Random Result</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Single Random Result</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlExecuteQuery#isSingleRandomResult()
     * @see #getXmlExecuteQuery()
     * @generated
     */
    EAttribute getXmlExecuteQuery_SingleRandomResult();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId <em>Xml Model Entity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xml Model Entity Id</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlModelEntityId
     * @generated
     */
    EClass getXmlModelEntityId();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getQualifierSet <em>Qualifier Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Qualifier Set</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlModelEntityId#getQualifierSet()
     * @see #getXmlModelEntityId()
     * @generated
     */
    EReference getXmlModelEntityId_QualifierSet();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getEntityType <em>Entity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Entity Type</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlModelEntityId#getEntityType()
     * @see #getXmlModelEntityId()
     * @generated
     */
    EAttribute getXmlModelEntityId_EntityType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getGuid <em>Guid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Guid</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlModelEntityId#getGuid()
     * @see #getXmlModelEntityId()
     * @generated
     */
    EAttribute getXmlModelEntityId_Guid();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlModelEntityId#getQualifier <em>Qualifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Qualifier</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlModelEntityId#getQualifier()
     * @see #getXmlModelEntityId()
     * @generated
     */
    EAttribute getXmlModelEntityId_Qualifier();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.organisation.api.XmlOrgModelVersion <em>Xml Org Model Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xml Org Model Version</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlOrgModelVersion
     * @generated
     */
    EClass getXmlOrgModelVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlOrgModelVersion#getModelVersion <em>Model Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Model Version</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlOrgModelVersion#getModelVersion()
     * @see #getXmlOrgModelVersion()
     * @generated
     */
    EAttribute getXmlOrgModelVersion_ModelVersion();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.organisation.api.XmlParticipantId <em>Xml Participant Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xml Participant Id</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlParticipantId
     * @generated
     */
    EClass getXmlParticipantId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getGuid <em>Guid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Guid</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlParticipantId#getGuid()
     * @see #getXmlParticipantId()
     * @generated
     */
    EAttribute getXmlParticipantId_Guid();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlParticipantId#getName()
     * @see #getXmlParticipantId()
     * @generated
     */
    EAttribute getXmlParticipantId_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getEntityType <em>Entity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Entity Type</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlParticipantId#getEntityType()
     * @see #getXmlParticipantId()
     * @generated
     */
    EAttribute getXmlParticipantId_EntityType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlParticipantId#getQualifier <em>Qualifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Qualifier</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlParticipantId#getQualifier()
     * @see #getXmlParticipantId()
     * @generated
     */
    EAttribute getXmlParticipantId_Qualifier();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.organisation.api.XmlResourceQuery <em>Xml Resource Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xml Resource Query</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlResourceQuery
     * @generated
     */
    EClass getXmlResourceQuery();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.organisation.api.XmlResourceQuery#getQuery <em>Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Query</em>'.
     * @see com.tibco.n2.common.organisation.api.XmlResourceQuery#getQuery()
     * @see #getXmlResourceQuery()
     * @generated
     */
    EAttribute getXmlResourceQuery_Query();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.common.organisation.api.OrganisationalEntityType <em>Organisational Entity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Organisational Entity Type</em>'.
     * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
     * @generated
     */
    EEnum getOrganisationalEntityType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.common.organisation.api.OrganisationalEntityType <em>Organisational Entity Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Organisational Entity Type Object</em>'.
     * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
     * @model instanceClass="com.tibco.n2.common.organisation.api.OrganisationalEntityType"
     *        extendedMetaData="name='OrganisationalEntityType:Object' baseType='OrganisationalEntityType'"
     * @generated
     */
    EDataType getOrganisationalEntityTypeObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    OrganisationFactory getOrganisationFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.impl.QualifierSetTypeImpl <em>Qualifier Set Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.impl.QualifierSetTypeImpl
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getQualifierSetType()
         * @generated
         */
        EClass QUALIFIER_SET_TYPE = eINSTANCE.getQualifierSetType();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIER_SET_TYPE__VALUE = eINSTANCE.getQualifierSetType_Value();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarAssignmentImpl <em>Xml Calendar Assignment</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.impl.XmlCalendarAssignmentImpl
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlCalendarAssignment()
         * @generated
         */
        EClass XML_CALENDAR_ASSIGNMENT = eINSTANCE.getXmlCalendarAssignment();

        /**
         * The meta object literal for the '<em><b>Calendar Alias</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_CALENDAR_ASSIGNMENT__CALENDAR_ALIAS = eINSTANCE.getXmlCalendarAssignment_CalendarAlias();

        /**
         * The meta object literal for the '<em><b>Entity Guid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_CALENDAR_ASSIGNMENT__ENTITY_GUID = eINSTANCE.getXmlCalendarAssignment_EntityGuid();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl <em>Xml Calendar Ref</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlCalendarRef()
         * @generated
         */
        EClass XML_CALENDAR_REF = eINSTANCE.getXmlCalendarRef();

        /**
         * The meta object literal for the '<em><b>Calendar Alias</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_CALENDAR_REF__CALENDAR_ALIAS = eINSTANCE.getXmlCalendarRef_CalendarAlias();

        /**
         * The meta object literal for the '<em><b>Entity Guid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_CALENDAR_REF__ENTITY_GUID = eINSTANCE.getXmlCalendarRef_EntityGuid();

        /**
         * The meta object literal for the '<em><b>Entity Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_CALENDAR_REF__ENTITY_LABEL = eINSTANCE.getXmlCalendarRef_EntityLabel();

        /**
         * The meta object literal for the '<em><b>Entity Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_CALENDAR_REF__ENTITY_NAME = eINSTANCE.getXmlCalendarRef_EntityName();

        /**
         * The meta object literal for the '<em><b>Entity Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_CALENDAR_REF__ENTITY_TYPE = eINSTANCE.getXmlCalendarRef_EntityType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.impl.XmlExecuteQueryImpl <em>Xml Execute Query</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.impl.XmlExecuteQueryImpl
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlExecuteQuery()
         * @generated
         */
        EClass XML_EXECUTE_QUERY = eINSTANCE.getXmlExecuteQuery();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_EXECUTE_QUERY__IMPLEMENTATION = eINSTANCE.getXmlExecuteQuery_Implementation();

        /**
         * The meta object literal for the '<em><b>Single Random Result</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT = eINSTANCE.getXmlExecuteQuery_SingleRandomResult();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.impl.XmlModelEntityIdImpl <em>Xml Model Entity Id</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.impl.XmlModelEntityIdImpl
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlModelEntityId()
         * @generated
         */
        EClass XML_MODEL_ENTITY_ID = eINSTANCE.getXmlModelEntityId();

        /**
         * The meta object literal for the '<em><b>Qualifier Set</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference XML_MODEL_ENTITY_ID__QUALIFIER_SET = eINSTANCE.getXmlModelEntityId_QualifierSet();

        /**
         * The meta object literal for the '<em><b>Entity Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_MODEL_ENTITY_ID__ENTITY_TYPE = eINSTANCE.getXmlModelEntityId_EntityType();

        /**
         * The meta object literal for the '<em><b>Guid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_MODEL_ENTITY_ID__GUID = eINSTANCE.getXmlModelEntityId_Guid();

        /**
         * The meta object literal for the '<em><b>Qualifier</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_MODEL_ENTITY_ID__QUALIFIER = eINSTANCE.getXmlModelEntityId_Qualifier();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.impl.XmlOrgModelVersionImpl <em>Xml Org Model Version</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.impl.XmlOrgModelVersionImpl
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlOrgModelVersion()
         * @generated
         */
        EClass XML_ORG_MODEL_VERSION = eINSTANCE.getXmlOrgModelVersion();

        /**
         * The meta object literal for the '<em><b>Model Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_ORG_MODEL_VERSION__MODEL_VERSION = eINSTANCE.getXmlOrgModelVersion_ModelVersion();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.impl.XmlParticipantIdImpl <em>Xml Participant Id</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.impl.XmlParticipantIdImpl
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlParticipantId()
         * @generated
         */
        EClass XML_PARTICIPANT_ID = eINSTANCE.getXmlParticipantId();

        /**
         * The meta object literal for the '<em><b>Guid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_PARTICIPANT_ID__GUID = eINSTANCE.getXmlParticipantId_Guid();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_PARTICIPANT_ID__NAME = eINSTANCE.getXmlParticipantId_Name();

        /**
         * The meta object literal for the '<em><b>Entity Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_PARTICIPANT_ID__ENTITY_TYPE = eINSTANCE.getXmlParticipantId_EntityType();

        /**
         * The meta object literal for the '<em><b>Qualifier</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_PARTICIPANT_ID__QUALIFIER = eINSTANCE.getXmlParticipantId_Qualifier();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.impl.XmlResourceQueryImpl <em>Xml Resource Query</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.impl.XmlResourceQueryImpl
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getXmlResourceQuery()
         * @generated
         */
        EClass XML_RESOURCE_QUERY = eINSTANCE.getXmlResourceQuery();

        /**
         * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XML_RESOURCE_QUERY__QUERY = eINSTANCE.getXmlResourceQuery_Query();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.organisation.api.OrganisationalEntityType <em>Organisational Entity Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getOrganisationalEntityType()
         * @generated
         */
        EEnum ORGANISATIONAL_ENTITY_TYPE = eINSTANCE.getOrganisationalEntityType();

        /**
         * The meta object literal for the '<em>Organisational Entity Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.organisation.api.OrganisationalEntityType
         * @see com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl#getOrganisationalEntityTypeObject()
         * @generated
         */
        EDataType ORGANISATIONAL_ENTITY_TYPE_OBJECT = eINSTANCE.getOrganisationalEntityTypeObject();

    }

} //OrganisationPackage
