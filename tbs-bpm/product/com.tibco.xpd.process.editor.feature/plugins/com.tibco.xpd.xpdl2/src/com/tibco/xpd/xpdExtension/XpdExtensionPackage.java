/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Xpdl2Package;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

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
 * @see com.tibco.xpd.xpdExtension.XpdExtensionFactory
 * @model kind="package"
 *        extendedMetaData="qualified='true'"
 * @generated
 */
public interface XpdExtensionPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "xpdExtension"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/XPD/xpdExtension1.0.0"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "xpdExt"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    XpdExtensionPackage eINSTANCE = com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ActivityRefImpl <em>Activity Ref</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ActivityRefImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getActivityRef()
     * @generated
     */
    int ACTIVITY_REF = 0;

    /**
     * The feature id for the '<em><b>Id Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_REF__ID_REF = 0;

    /**
     * The number of structural features of the '<em>Activity Ref</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_REF_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ActivityResourcePatternsImpl <em>Activity Resource Patterns</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ActivityResourcePatternsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getActivityResourcePatterns()
     * @generated
     */
    int ACTIVITY_RESOURCE_PATTERNS = 1;

    /**
     * The feature id for the '<em><b>Allocation Strategy</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY = 0;

    /**
     * The feature id for the '<em><b>Piling</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_RESOURCE_PATTERNS__PILING = 1;

    /**
     * The feature id for the '<em><b>Work Item Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY = 2;

    /**
     * The number of structural features of the '<em>Activity Resource Patterns</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_RESOURCE_PATTERNS_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl <em>Allocation Strategy</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAllocationStrategy()
     * @generated
     */
    int ALLOCATION_STRATEGY = 2;

    /**
     * The feature id for the '<em><b>Offer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_STRATEGY__OFFER = 0;

    /**
     * The feature id for the '<em><b>Strategy</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_STRATEGY__STRATEGY = 1;

    /**
     * The feature id for the '<em><b>Re Offer On Close</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE = 2;

    /**
     * The feature id for the '<em><b>Re Offer On Cancel</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL = 3;

    /**
     * The feature id for the '<em><b>Allocate To Offer Set Member Identifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER = 4;

    /**
     * The number of structural features of the '<em>Allocation Strategy</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_STRATEGY_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldsImpl <em>Associated Correlation Fields</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedCorrelationFields()
     * @generated
     */
    int ASSOCIATED_CORRELATION_FIELDS = 3;

    /**
     * The feature id for the '<em><b>Associated Correlation Field</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD = 0;

    /**
     * The feature id for the '<em><b>Disable Implicit Association</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION = 1;

    /**
     * The number of structural features of the '<em>Associated Correlation Fields</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_CORRELATION_FIELDS_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldImpl <em>Associated Correlation Field</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedCorrelationField()
     * @generated
     */
    int ASSOCIATED_CORRELATION_FIELD = 4;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_CORRELATION_FIELD__DESCRIPTION = Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_CORRELATION_FIELD__NAME = Xpdl2Package.DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Correlation Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE = Xpdl2Package.DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Associated Correlation Field</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_CORRELATION_FIELD_FEATURE_COUNT = Xpdl2Package.DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AssociatedParameterImpl <em>Associated Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AssociatedParameterImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedParameter()
     * @generated
     */
    int ASSOCIATED_PARAMETER = 5;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETER__DESCRIPTION = Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Formal Param</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETER__FORMAL_PARAM = Xpdl2Package.DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETER__MODE = Xpdl2Package.DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Mandatory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETER__MANDATORY = Xpdl2Package.DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Associated Parameter</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETER_FEATURE_COUNT = Xpdl2Package.DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AssociatedParametersImpl <em>Associated Parameters</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AssociatedParametersImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedParameters()
     * @generated
     */
    int ASSOCIATED_PARAMETERS = 6;

    /**
     * The feature id for the '<em><b>Associated Parameter</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETERS__ASSOCIATED_PARAMETER = 0;

    /**
     * The feature id for the '<em><b>Disable Implicit Association</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETERS__DISABLE_IMPLICIT_ASSOCIATION = 1;

    /**
     * The number of structural features of the '<em>Associated Parameters</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETERS_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer <em>Associated Parameters Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.AssociatedParametersContainer
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedParametersContainer()
     * @generated
     */
    int ASSOCIATED_PARAMETERS_CONTAINER = 7;

    /**
     * The feature id for the '<em><b>Associated Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS = 0;

    /**
     * The feature id for the '<em><b>Disable Implicit Association</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETERS_CONTAINER__DISABLE_IMPLICIT_ASSOCIATION = 1;

    /**
     * The number of structural features of the '<em>Associated Parameters Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATED_PARAMETERS_CONTAINER_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AuditImpl <em>Audit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AuditImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAudit()
     * @generated
     */
    int AUDIT = 8;

    /**
     * The feature id for the '<em><b>Audit Event</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUDIT__AUDIT_EVENT = 0;

    /**
     * The feature id for the '<em><b>Any</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUDIT__ANY = 1;

    /**
     * The number of structural features of the '<em>Audit</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUDIT_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AuditEventImpl <em>Audit Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AuditEventImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAuditEvent()
     * @generated
     */
    int AUDIT_EVENT = 9;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUDIT_EVENT__TYPE = 0;

    /**
     * The feature id for the '<em><b>Information</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUDIT_EVENT__INFORMATION = 1;

    /**
     * The number of structural features of the '<em>Audit Event</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUDIT_EVENT_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.BusinessProcessImpl <em>Business Process</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.BusinessProcessImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getBusinessProcess()
     * @generated
     */
    int BUSINESS_PROCESS = 10;

    /**
     * The feature id for the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_PROCESS__PROCESS_ID = 0;

    /**
     * The feature id for the '<em><b>Package Ref Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_PROCESS__PACKAGE_REF_ID = 1;

    /**
     * The feature id for the '<em><b>Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_PROCESS__ACTIVITY_ID = 2;

    /**
     * The number of structural features of the '<em>Business Process</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_PROCESS_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CalendarReferenceImpl <em>Calendar Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CalendarReferenceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCalendarReference()
     * @generated
     */
    int CALENDAR_REFERENCE = 11;

    /**
     * The feature id for the '<em><b>Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALENDAR_REFERENCE__ALIAS = 0;

    /**
     * The feature id for the '<em><b>Data Field Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALENDAR_REFERENCE__DATA_FIELD_ID = 1;

    /**
     * The number of structural features of the '<em>Calendar Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALENDAR_REFERENCE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CatchErrorMappingsImpl <em>Catch Error Mappings</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CatchErrorMappingsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCatchErrorMappings()
     * @generated
     */
    int CATCH_ERROR_MAPPINGS = 12;

    /**
     * The feature id for the '<em><b>Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CATCH_ERROR_MAPPINGS__MESSAGE = 0;

    /**
     * The number of structural features of the '<em>Catch Error Mappings</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CATCH_ERROR_MAPPINGS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl <em>Constant Period</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getConstantPeriod()
     * @generated
     */
    int CONSTANT_PERIOD = 13;

    /**
     * The feature id for the '<em><b>Days</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD__DAYS = 0;

    /**
     * The feature id for the '<em><b>Hours</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD__HOURS = 1;

    /**
     * The feature id for the '<em><b>Micro Seconds</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD__MICRO_SECONDS = 2;

    /**
     * The feature id for the '<em><b>Minutes</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD__MINUTES = 3;

    /**
     * The feature id for the '<em><b>Months</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD__MONTHS = 4;

    /**
     * The feature id for the '<em><b>Seconds</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD__SECONDS = 5;

    /**
     * The feature id for the '<em><b>Weeks</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD__WEEKS = 6;

    /**
     * The feature id for the '<em><b>Years</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD__YEARS = 7;

    /**
     * The number of structural features of the '<em>Constant Period</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_PERIOD_FEATURE_COUNT = 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ConditionalParticipantImpl <em>Conditional Participant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ConditionalParticipantImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getConditionalParticipant()
     * @generated
     */
    int CONDITIONAL_PARTICIPANT = 14;

    /**
     * The feature id for the '<em><b>Performer Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT = 0;

    /**
     * The number of structural features of the '<em>Conditional Participant</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONDITIONAL_PARTICIPANT_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ReplyImmediateDataMappingsImpl <em>Reply Immediate Data Mappings</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ReplyImmediateDataMappingsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getReplyImmediateDataMappings()
     * @generated
     */
    int REPLY_IMMEDIATE_DATA_MAPPINGS = 15;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLY_IMMEDIATE_DATA_MAPPINGS__DATA_MAPPINGS = 0;

    /**
     * The number of structural features of the '<em>Reply Immediate Data Mappings</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPLY_IMMEDIATE_DATA_MAPPINGS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CorrelationDataMappingsImpl <em>Correlation Data Mappings</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CorrelationDataMappingsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCorrelationDataMappings()
     * @generated
     */
    int CORRELATION_DATA_MAPPINGS = 16;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CORRELATION_DATA_MAPPINGS__DATA_MAPPINGS = 0;

    /**
     * The number of structural features of the '<em>Correlation Data Mappings</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CORRELATION_DATA_MAPPINGS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DiscriminatorImpl <em>Discriminator</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DiscriminatorImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDiscriminator()
     * @generated
     */
    int DISCRIMINATOR = 17;

    /**
     * The feature id for the '<em><b>Discriminator Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DISCRIMINATOR__DISCRIMINATOR_TYPE = 0;

    /**
     * The feature id for the '<em><b>Structured Discriminator</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DISCRIMINATOR__STRUCTURED_DISCRIMINATOR = 1;

    /**
     * The number of structural features of the '<em>Discriminator</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DISCRIMINATOR_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DocumentRootImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 18;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 0;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 1;

    /**
     * The feature id for the '<em><b>Implementation Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__IMPLEMENTATION_TYPE = 2;

    /**
     * The feature id for the '<em><b>Data Object Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES = 3;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EXTENDED_ATTRIBUTES = 4;

    /**
     * The feature id for the '<em><b>Continue On Timeout</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT = 5;

    /**
     * The feature id for the '<em><b>Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ALIAS = 6;

    /**
     * The feature id for the '<em><b>Constant Period</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CONSTANT_PERIOD = 7;

    /**
     * The feature id for the '<em><b>User Task Scripts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__USER_TASK_SCRIPTS = 8;

    /**
     * The feature id for the '<em><b>Audit</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__AUDIT = 9;

    /**
     * The feature id for the '<em><b>Script</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SCRIPT = 10;

    /**
     * The feature id for the '<em><b>Reply Immediately</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REPLY_IMMEDIATELY = 11;

    /**
     * The feature id for the '<em><b>Initial Values</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__INITIAL_VALUES = 12;

    /**
     * The feature id for the '<em><b>Associated Correlation Fields</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS = 13;

    /**
     * The feature id for the '<em><b>Associated Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASSOCIATED_PARAMETERS = 14;

    /**
     * The feature id for the '<em><b>Implemented Interface</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__IMPLEMENTED_INTERFACE = 15;

    /**
     * The feature id for the '<em><b>Process Interfaces</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PROCESS_INTERFACES = 16;

    /**
     * The feature id for the '<em><b>Wsdl Event Association</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION = 17;

    /**
     * The feature id for the '<em><b>Inline Sub Process</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__INLINE_SUB_PROCESS = 18;

    /**
     * The feature id for the '<em><b>Documentation URL</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DOCUMENTATION_URL = 19;

    /**
     * The feature id for the '<em><b>Implements</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__IMPLEMENTS = 20;

    /**
     * The feature id for the '<em><b>Multi Instance Scripts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS = 21;

    /**
     * The feature id for the '<em><b>Process Identifier Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD = 22;

    /**
     * The feature id for the '<em><b>Expression</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EXPRESSION = 23;

    /**
     * The feature id for the '<em><b>Visibility</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__VISIBILITY = 24;

    /**
     * The feature id for the '<em><b>Security Profile</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SECURITY_PROFILE = 25;

    /**
     * The feature id for the '<em><b>Language</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__LANGUAGE = 26;

    /**
     * The feature id for the '<em><b>Initial Parameter Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE = 27;

    /**
     * The feature id for the '<em><b>Initial Value Mapping</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__INITIAL_VALUE_MAPPING = 28;

    /**
     * The feature id for the '<em><b>Port Type Operation</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PORT_TYPE_OPERATION = 29;

    /**
     * The feature id for the '<em><b>Transport</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__TRANSPORT = 30;

    /**
     * The feature id for the '<em><b>Is Chained</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__IS_CHAINED = 31;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EXTERNAL_REFERENCE = 32;

    /**
     * The feature id for the '<em><b>Process Resource Patterns</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS = 33;

    /**
     * The feature id for the '<em><b>Event Handler Initialisers</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS = 34;

    /**
     * The feature id for the '<em><b>Activity Resource Patterns</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS = 35;

    /**
     * The feature id for the '<em><b>Require New Transaction</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION = 36;

    /**
     * The feature id for the '<em><b>Document Operation</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DOCUMENT_OPERATION = 37;

    /**
     * The feature id for the '<em><b>Duration Calculation</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DURATION_CALCULATION = 38;

    /**
     * The feature id for the '<em><b>Discriminator</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DISCRIMINATOR = 39;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DISPLAY_NAME = 40;

    /**
     * The feature id for the '<em><b>Catch Throw</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CATCH_THROW = 41;

    /**
     * The feature id for the '<em><b>Is Remote</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__IS_REMOTE = 42;

    /**
     * The feature id for the '<em><b>Correlation Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS = 43;

    /**
     * The feature id for the '<em><b>Transform Script</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__TRANSFORM_SCRIPT = 44;

    /**
     * The feature id for the '<em><b>Publish As Business Service</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE = 45;

    /**
     * The feature id for the '<em><b>Business Service Category</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY = 46;

    /**
     * The feature id for the '<em><b>Error Thrower Info</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ERROR_THROWER_INFO = 47;

    /**
     * The feature id for the '<em><b>Catch Error Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS = 48;

    /**
     * The feature id for the '<em><b>Conditional Participant</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT = 49;

    /**
     * The feature id for the '<em><b>Generated</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GENERATED = 50;

    /**
     * The feature id for the '<em><b>Reply To Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID = 51;

    /**
     * The feature id for the '<em><b>Task Library Reference</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE = 52;

    /**
     * The feature id for the '<em><b>Set Performer In Process</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS = 53;

    /**
     * The feature id for the '<em><b>Emb Subproc Other State Height</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT = 54;

    /**
     * The feature id for the '<em><b>Emb Subproc Other State Width</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH = 55;

    /**
     * The feature id for the '<em><b>Form Implementation</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__FORM_IMPLEMENTATION = 56;

    /**
     * The feature id for the '<em><b>Participant Query</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PARTICIPANT_QUERY = 57;

    /**
     * The feature id for the '<em><b>Api End Point Participant</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__API_END_POINT_PARTICIPANT = 58;

    /**
     * The feature id for the '<em><b>Fault Message</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__FAULT_MESSAGE = 59;

    /**
     * The feature id for the '<em><b>Request Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REQUEST_ACTIVITY_ID = 60;

    /**
     * The feature id for the '<em><b>Business Process</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__BUSINESS_PROCESS = 61;

    /**
     * The feature id for the '<em><b>Wsdl Generation</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__WSDL_GENERATION = 62;

    /**
     * The feature id for the '<em><b>Target Primitive Property</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY = 63;

    /**
     * The feature id for the '<em><b>Source Primitive Property</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY = 64;

    /**
     * The feature id for the '<em><b>Decision Service</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DECISION_SERVICE = 65;

    /**
     * The feature id for the '<em><b>Participant Shared Resource</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE = 66;

    /**
     * The feature id for the '<em><b>Xpd Model Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XPD_MODEL_TYPE = 67;

    /**
     * The feature id for the '<em><b>Flow Routing Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__FLOW_ROUTING_STYLE = 68;

    /**
     * The feature id for the '<em><b>Calendar Reference</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CALENDAR_REFERENCE = 69;

    /**
     * The feature id for the '<em><b>Non Cancelling</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__NON_CANCELLING = 70;

    /**
     * The feature id for the '<em><b>Signal Data</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SIGNAL_DATA = 71;

    /**
     * The feature id for the '<em><b>Retry</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__RETRY = 72;

    /**
     * The feature id for the '<em><b>Activity Deadline Event Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID = 73;

    /**
     * The feature id for the '<em><b>Start Strategy</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__START_STRATEGY = 74;

    /**
     * The feature id for the '<em><b>Overwrite Already Modified Task Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA = 75;

    /**
     * The feature id for the '<em><b>Event Handler Flow Strategy</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY = 76;

    /**
     * The feature id for the '<em><b>Namespace Prefix Map</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP = 77;

    /**
     * The feature id for the '<em><b>Suspend Resume With Parent</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT = 78;

    /**
     * The feature id for the '<em><b>System Error Action</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SYSTEM_ERROR_ACTION = 79;

    /**
     * The feature id for the '<em><b>Correlation Timeout</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CORRELATION_TIMEOUT = 80;

    /**
     * The feature id for the '<em><b>Validation Control</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__VALIDATION_CONTROL = 81;

    /**
     * The feature id for the '<em><b>Reply Immediate Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS = 82;

    /**
     * The feature id for the '<em><b>Bx Use Unqualified Property Names</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES = 83;

    /**
     * The feature id for the '<em><b>Case Ref Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CASE_REF_TYPE = 84;

    /**
     * The feature id for the '<em><b>REST Services</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REST_SERVICES = 85;

    /**
     * The feature id for the '<em><b>Publish As Rest Service</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE = 86;

    /**
     * The feature id for the '<em><b>Rest Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REST_ACTIVITY_ID = 87;

    /**
     * The feature id for the '<em><b>Reschedule Timer Script</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT = 88;

    /**
     * The feature id for the '<em><b>Dynamic Organization Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS = 89;

    /**
     * The feature id for the '<em><b>Signal Handler Asynchronous</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS = 90;

    /**
     * The feature id for the '<em><b>Global Data Operation</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GLOBAL_DATA_OPERATION = 91;

    /**
     * The feature id for the '<em><b>Process Data Work Item Attribute Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS = 92;

    /**
     * The feature id for the '<em><b>Allow Unqualified Sub Process Identification</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION = 93;

    /**
     * The feature id for the '<em><b>Bpm Runtime Configuration</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION = 94;

    /**
     * The feature id for the '<em><b>Is Case Service</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__IS_CASE_SERVICE = 95;

    /**
     * The feature id for the '<em><b>Required Access Privileges</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES = 96;

    /**
     * The feature id for the '<em><b>Case Service</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CASE_SERVICE = 97;

    /**
     * The feature id for the '<em><b>Ad Hoc Task Configuration</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION = 98;

    /**
     * The feature id for the '<em><b>Is Event Sub Process</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS = 99;

    /**
     * The feature id for the '<em><b>Non Interrupting Event</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__NON_INTERRUPTING_EVENT = 100;

    /**
     * The feature id for the '<em><b>Correlate Immediately</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CORRELATE_IMMEDIATELY = 101;

    /**
     * The feature id for the '<em><b>Async Execution Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_EXECUTION_MODE = 102;

    /**
     * The feature id for the '<em><b>Signal Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SIGNAL_TYPE = 103;

    /**
     * The feature id for the '<em><b>Service Process Configuration</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION = 104;

    /**
     * The feature id for the '<em><b>Like Mapping</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__LIKE_MAPPING = 105;

    /**
     * The feature id for the '<em><b>Script Data Mapper</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SCRIPT_DATA_MAPPER = 106;

    /**
     * The feature id for the '<em><b>Like Mapping Exclusions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS = 107;

    /**
     * The feature id for the '<em><b>Source Contributor Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID = 108;

    /**
     * The feature id for the '<em><b>Target Contributor Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID = 109;

    /**
     * The feature id for the '<em><b>Rest Service Operation</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REST_SERVICE_OPERATION = 110;

    /**
     * The feature id for the '<em><b>Input Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__INPUT_MAPPINGS = 111;

    /**
     * The feature id for the '<em><b>Output Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__OUTPUT_MAPPINGS = 112;

    /**
     * The feature id for the '<em><b>Business Service Publish Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE = 113;

    /**
     * The feature id for the '<em><b>Suppress Max Mappings Error</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR = 114;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 115;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl <em>Duration Calculation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDurationCalculation()
     * @generated
     */
    int DURATION_CALCULATION = 19;

    /**
     * The feature id for the '<em><b>Years</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION__YEARS = 0;

    /**
     * The feature id for the '<em><b>Months</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION__MONTHS = 1;

    /**
     * The feature id for the '<em><b>Weeks</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION__WEEKS = 2;

    /**
     * The feature id for the '<em><b>Days</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION__DAYS = 3;

    /**
     * The feature id for the '<em><b>Hours</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION__HOURS = 4;

    /**
     * The feature id for the '<em><b>Minutes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION__MINUTES = 5;

    /**
     * The feature id for the '<em><b>Seconds</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION__SECONDS = 6;

    /**
     * The feature id for the '<em><b>Microseconds</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION__MICROSECONDS = 7;

    /**
     * The number of structural features of the '<em>Duration Calculation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_CALCULATION_FEATURE_COUNT = 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingsImpl <em>Dynamic Organization Mappings</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDynamicOrganizationMappings()
     * @generated
     */
    int DYNAMIC_ORGANIZATION_MAPPINGS = 20;

    /**
     * The feature id for the '<em><b>Dynamic Organization Mapping</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING = 0;

    /**
     * The number of structural features of the '<em>Dynamic Organization Mappings</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORGANIZATION_MAPPINGS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingImpl <em>Dynamic Organization Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDynamicOrganizationMapping()
     * @generated
     */
    int DYNAMIC_ORGANIZATION_MAPPING = 21;

    /**
     * The feature id for the '<em><b>Source Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORGANIZATION_MAPPING__SOURCE_PATH = 0;

    /**
     * The feature id for the '<em><b>Dynamic Org Identifier Ref</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF = 1;

    /**
     * The number of structural features of the '<em>Dynamic Organization Mapping</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORGANIZATION_MAPPING_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DynamicOrgIdentifierRefImpl <em>Dynamic Org Identifier Ref</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DynamicOrgIdentifierRefImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDynamicOrgIdentifierRef()
     * @generated
     */
    int DYNAMIC_ORG_IDENTIFIER_REF = 22;

    /**
     * The feature id for the '<em><b>Identifier Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER_REF__IDENTIFIER_NAME = 0;

    /**
     * The feature id for the '<em><b>Dynamic Org Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER_REF__DYNAMIC_ORG_ID = 1;

    /**
     * The feature id for the '<em><b>Org Model Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER_REF__ORG_MODEL_PATH = 2;

    /**
     * The number of structural features of the '<em>Dynamic Org Identifier Ref</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER_REF_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.EmailResourceImpl <em>Email Resource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.EmailResourceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getEmailResource()
     * @generated
     */
    int EMAIL_RESOURCE = 23;

    /**
     * The feature id for the '<em><b>Instance Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EMAIL_RESOURCE__INSTANCE_NAME = 0;

    /**
     * The number of structural features of the '<em>Email Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EMAIL_RESOURCE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ErrorMethodImpl <em>Error Method</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ErrorMethodImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getErrorMethod()
     * @generated
     */
    int ERROR_METHOD = 24;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_METHOD__ID = Xpdl2Package.UNIQUE_ID_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Associated Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_METHOD__ASSOCIATED_PARAMETERS = Xpdl2Package.UNIQUE_ID_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Disable Implicit Association</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION = Xpdl2Package.UNIQUE_ID_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Error Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_METHOD__ERROR_CODE = Xpdl2Package.UNIQUE_ID_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Error Method</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_METHOD_FEATURE_COUNT = Xpdl2Package.UNIQUE_ID_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ErrorThrowerInfoImpl <em>Error Thrower Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ErrorThrowerInfoImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getErrorThrowerInfo()
     * @generated
     */
    int ERROR_THROWER_INFO = 25;

    /**
     * The feature id for the '<em><b>Thrower Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_THROWER_INFO__THROWER_ID = 0;

    /**
     * The feature id for the '<em><b>Thrower Container Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_THROWER_INFO__THROWER_CONTAINER_ID = 1;

    /**
     * The feature id for the '<em><b>Thrower Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_THROWER_INFO__THROWER_TYPE = 2;

    /**
     * The number of structural features of the '<em>Error Thrower Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_THROWER_INFO_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.EventHandlerInitialisersImpl <em>Event Handler Initialisers</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.EventHandlerInitialisersImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getEventHandlerInitialisers()
     * @generated
     */
    int EVENT_HANDLER_INITIALISERS = 26;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_HANDLER_INITIALISERS__ID = Xpdl2Package.NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_HANDLER_INITIALISERS__OTHER_ATTRIBUTES = Xpdl2Package.NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_HANDLER_INITIALISERS__NAME = Xpdl2Package.NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Activity Ref</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_HANDLER_INITIALISERS__ACTIVITY_REF = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Event Handler Initialisers</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_HANDLER_INITIALISERS_FEATURE_COUNT = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.FaultMessageImpl <em>Fault Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.FaultMessageImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFaultMessage()
     * @generated
     */
    int FAULT_MESSAGE = 27;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__ID = Xpdl2Package.MESSAGE__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__OTHER_ATTRIBUTES = Xpdl2Package.MESSAGE__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__NAME = Xpdl2Package.MESSAGE__NAME;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__OTHER_ELEMENTS = Xpdl2Package.MESSAGE__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Actual Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__ACTUAL_PARAMETERS = Xpdl2Package.MESSAGE__ACTUAL_PARAMETERS;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__DATA_MAPPINGS = Xpdl2Package.MESSAGE__DATA_MAPPINGS;

    /**
     * The feature id for the '<em><b>Fault Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__FAULT_NAME = Xpdl2Package.MESSAGE__FAULT_NAME;

    /**
     * The feature id for the '<em><b>From</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__FROM = Xpdl2Package.MESSAGE__FROM;

    /**
     * The feature id for the '<em><b>To</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE__TO = Xpdl2Package.MESSAGE__TO;

    /**
     * The number of structural features of the '<em>Fault Message</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FAULT_MESSAGE_FEATURE_COUNT = Xpdl2Package.MESSAGE_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.FormImplementationImpl <em>Form Implementation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.FormImplementationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFormImplementation()
     * @generated
     */
    int FORM_IMPLEMENTATION = 28;

    /**
     * The feature id for the '<em><b>Form Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_IMPLEMENTATION__FORM_TYPE = 0;

    /**
     * The feature id for the '<em><b>Form URI</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_IMPLEMENTATION__FORM_URI = 1;

    /**
     * The number of structural features of the '<em>Form Implementation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_IMPLEMENTATION_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ImplementedInterfaceImpl <em>Implemented Interface</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ImplementedInterfaceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getImplementedInterface()
     * @generated
     */
    int IMPLEMENTED_INTERFACE = 29;

    /**
     * The feature id for the '<em><b>Package Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTED_INTERFACE__PACKAGE_REF = 0;

    /**
     * The feature id for the '<em><b>Process Interface Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTED_INTERFACE__PROCESS_INTERFACE_ID = 1;

    /**
     * The number of structural features of the '<em>Implemented Interface</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTED_INTERFACE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.InitialValuesImpl <em>Initial Values</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.InitialValuesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getInitialValues()
     * @generated
     */
    int INITIAL_VALUES = 30;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INITIAL_VALUES__VALUE = 0;

    /**
     * The number of structural features of the '<em>Initial Values</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INITIAL_VALUES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.InitialParameterValueImpl <em>Initial Parameter Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.InitialParameterValueImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getInitialParameterValue()
     * @generated
     */
    int INITIAL_PARAMETER_VALUE = 31;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INITIAL_PARAMETER_VALUE__NAME = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INITIAL_PARAMETER_VALUE__VALUE = 1;

    /**
     * The number of structural features of the '<em>Initial Parameter Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INITIAL_PARAMETER_VALUE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.InterfaceMethod <em>Interface Method</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.InterfaceMethod
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getInterfaceMethod()
     * @generated
     */
    int INTERFACE_METHOD = 32;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__ID = Xpdl2Package.NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__OTHER_ATTRIBUTES = Xpdl2Package.NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__NAME = Xpdl2Package.NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__DESCRIPTION = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Associated Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__ASSOCIATED_PARAMETERS = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Disable Implicit Association</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__DISABLE_IMPLICIT_ASSOCIATION = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Trigger</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__TRIGGER = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__TRIGGER_RESULT_MESSAGE = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Visibility</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__VISIBILITY = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Error Methods</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD__ERROR_METHODS = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Interface Method</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_METHOD_FEATURE_COUNT = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.IntermediateMethodImpl <em>Intermediate Method</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.IntermediateMethodImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getIntermediateMethod()
     * @generated
     */
    int INTERMEDIATE_METHOD = 33;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__ID = INTERFACE_METHOD__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__OTHER_ATTRIBUTES = INTERFACE_METHOD__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__NAME = INTERFACE_METHOD__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__DESCRIPTION = INTERFACE_METHOD__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Associated Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__ASSOCIATED_PARAMETERS = INTERFACE_METHOD__ASSOCIATED_PARAMETERS;

    /**
     * The feature id for the '<em><b>Disable Implicit Association</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__DISABLE_IMPLICIT_ASSOCIATION = INTERFACE_METHOD__DISABLE_IMPLICIT_ASSOCIATION;

    /**
     * The feature id for the '<em><b>Trigger</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__TRIGGER = INTERFACE_METHOD__TRIGGER;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__TRIGGER_RESULT_MESSAGE = INTERFACE_METHOD__TRIGGER_RESULT_MESSAGE;

    /**
     * The feature id for the '<em><b>Visibility</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__VISIBILITY = INTERFACE_METHOD__VISIBILITY;

    /**
     * The feature id for the '<em><b>Error Methods</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD__ERROR_METHODS = INTERFACE_METHOD__ERROR_METHODS;

    /**
     * The number of structural features of the '<em>Intermediate Method</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_METHOD_FEATURE_COUNT = INTERFACE_METHOD_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.JdbcResourceImpl <em>Jdbc Resource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.JdbcResourceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getJdbcResource()
     * @generated
     */
    int JDBC_RESOURCE = 34;

    /**
     * The feature id for the '<em><b>Instance Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JDBC_RESOURCE__INSTANCE_NAME = 0;

    /**
     * The feature id for the '<em><b>Jdbc Profile Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JDBC_RESOURCE__JDBC_PROFILE_NAME = 1;

    /**
     * The number of structural features of the '<em>Jdbc Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JDBC_RESOURCE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.MultiInstanceScriptsImpl <em>Multi Instance Scripts</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.MultiInstanceScriptsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getMultiInstanceScripts()
     * @generated
     */
    int MULTI_INSTANCE_SCRIPTS = 35;

    /**
     * The feature id for the '<em><b>Additional Instances</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES = 0;

    /**
     * The number of structural features of the '<em>Multi Instance Scripts</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTI_INSTANCE_SCRIPTS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.NamespacePrefixMapImpl <em>Namespace Prefix Map</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.NamespacePrefixMapImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getNamespacePrefixMap()
     * @generated
     */
    int NAMESPACE_PREFIX_MAP = 36;

    /**
     * The feature id for the '<em><b>Namespace Entries</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES = 0;

    /**
     * The feature id for the '<em><b>Prefix Mapping Disabled</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE_PREFIX_MAP__PREFIX_MAPPING_DISABLED = 1;

    /**
     * The number of structural features of the '<em>Namespace Prefix Map</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE_PREFIX_MAP_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.NamespaceMapEntryImpl <em>Namespace Map Entry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.NamespaceMapEntryImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getNamespaceMapEntry()
     * @generated
     */
    int NAMESPACE_MAP_ENTRY = 37;

    /**
     * The feature id for the '<em><b>Prefix</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE_MAP_ENTRY__PREFIX = 0;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE_MAP_ENTRY__NAMESPACE = 1;

    /**
     * The number of structural features of the '<em>Namespace Map Entry</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE_MAP_ENTRY_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ParticipantSharedResourceImpl <em>Participant Shared Resource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ParticipantSharedResourceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getParticipantSharedResource()
     * @generated
     */
    int PARTICIPANT_SHARED_RESOURCE = 38;

    /**
     * The feature id for the '<em><b>Email</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SHARED_RESOURCE__EMAIL = 0;

    /**
     * The feature id for the '<em><b>Jdbc</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SHARED_RESOURCE__JDBC = 1;

    /**
     * The feature id for the '<em><b>Web Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE = 2;

    /**
     * The feature id for the '<em><b>Rest Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SHARED_RESOURCE__REST_SERVICE = 3;

    /**
     * The number of structural features of the '<em>Participant Shared Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SHARED_RESOURCE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.PilingInfoImpl <em>Piling Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.PilingInfoImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getPilingInfo()
     * @generated
     */
    int PILING_INFO = 39;

    /**
     * The feature id for the '<em><b>Piling Allowed</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PILING_INFO__PILING_ALLOWED = 0;

    /**
     * The feature id for the '<em><b>Max Piliable Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PILING_INFO__MAX_PILIABLE_ITEMS = 1;

    /**
     * The number of structural features of the '<em>Piling Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PILING_INFO_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.PortTypeOperationImpl <em>Port Type Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.PortTypeOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getPortTypeOperation()
     * @generated
     */
    int PORT_TYPE_OPERATION = 40;

    /**
     * The feature id for the '<em><b>Port Type Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PORT_TYPE_OPERATION__PORT_TYPE_NAME = 0;

    /**
     * The feature id for the '<em><b>Operation Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PORT_TYPE_OPERATION__OPERATION_NAME = 1;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PORT_TYPE_OPERATION__EXTERNAL_REFERENCE = 2;

    /**
     * The feature id for the '<em><b>Transport</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PORT_TYPE_OPERATION__TRANSPORT = 3;

    /**
     * The number of structural features of the '<em>Port Type Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PORT_TYPE_OPERATION_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl <em>Process Interface</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getProcessInterface()
     * @generated
     */
    int PROCESS_INTERFACE = 41;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__ID = Xpdl2Package.NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__OTHER_ATTRIBUTES = Xpdl2Package.NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__NAME = Xpdl2Package.NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__DESCRIPTION = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__EXTENDED_ATTRIBUTES = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Formal Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__FORMAL_PARAMETERS = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__OTHER_ELEMENTS = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Start Methods</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__START_METHODS = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Intermediate Methods</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__INTERMEDIATE_METHODS = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Xpd Interface Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__XPD_INTERFACE_TYPE = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Service Process Configuration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Process Interface</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACE_FEATURE_COUNT = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfacesImpl <em>Process Interfaces</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ProcessInterfacesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getProcessInterfaces()
     * @generated
     */
    int PROCESS_INTERFACES = 42;

    /**
     * The feature id for the '<em><b>Process Interface</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACES__PROCESS_INTERFACE = 0;

    /**
     * The number of structural features of the '<em>Process Interfaces</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_INTERFACES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ProcessResourcePatternsImpl <em>Process Resource Patterns</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ProcessResourcePatternsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getProcessResourcePatterns()
     * @generated
     */
    int PROCESS_RESOURCE_PATTERNS = 43;

    /**
     * The feature id for the '<em><b>Separation Of Duties Activities</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES = 0;

    /**
     * The feature id for the '<em><b>Retain Familiar Activities</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES = 1;

    /**
     * The number of structural features of the '<em>Process Resource Patterns</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RESOURCE_PATTERNS_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RescheduleTimerScriptImpl <em>Reschedule Timer Script</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RescheduleTimerScriptImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRescheduleTimerScript()
     * @generated
     */
    int RESCHEDULE_TIMER_SCRIPT = 44;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMER_SCRIPT__MIXED = Xpdl2Package.EXPRESSION__MIXED;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMER_SCRIPT__GROUP = Xpdl2Package.EXPRESSION__GROUP;

    /**
     * The feature id for the '<em><b>Any</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMER_SCRIPT__ANY = Xpdl2Package.EXPRESSION__ANY;

    /**
     * The feature id for the '<em><b>Script Grammar</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMER_SCRIPT__SCRIPT_GRAMMAR = Xpdl2Package.EXPRESSION__SCRIPT_GRAMMAR;

    /**
     * The feature id for the '<em><b>Duration Relative To</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO = Xpdl2Package.EXPRESSION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Reschedule Timer Script</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMER_SCRIPT_FEATURE_COUNT = Xpdl2Package.EXPRESSION_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RescheduleTimersImpl <em>Reschedule Timers</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RescheduleTimersImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRescheduleTimers()
     * @generated
     */
    int RESCHEDULE_TIMERS = 45;

    /**
     * The feature id for the '<em><b>Timer Selection Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE = 0;

    /**
     * The feature id for the '<em><b>Timer Events</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMERS__TIMER_EVENTS = 1;

    /**
     * The number of structural features of the '<em>Reschedule Timers</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_TIMERS_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RESTServicesImpl <em>REST Services</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RESTServicesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRESTServices()
     * @generated
     */
    int REST_SERVICES = 46;

    /**
     * The feature id for the '<em><b>REST Services</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICES__REST_SERVICES = 0;

    /**
     * The number of structural features of the '<em>REST Services</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl <em>Rest Service Resource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRestServiceResource()
     * @generated
     */
    int REST_SERVICE_RESOURCE = 47;

    /**
     * The feature id for the '<em><b>Security Policy</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_RESOURCE__SECURITY_POLICY = 0;

    /**
     * The feature id for the '<em><b>Resource Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_RESOURCE__RESOURCE_NAME = 1;

    /**
     * The feature id for the '<em><b>Resource Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_RESOURCE__RESOURCE_TYPE = 2;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_RESOURCE__DESCRIPTION = 3;

    /**
     * The number of structural features of the '<em>Rest Service Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_RESOURCE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceSecurityImpl <em>Rest Service Resource Security</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RestServiceResourceSecurityImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRestServiceResourceSecurity()
     * @generated
     */
    int REST_SERVICE_RESOURCE_SECURITY = 48;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_RESOURCE_SECURITY__EXTENDED_ATTRIBUTES =
            Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Policy Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Rest Service Resource Security</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_RESOURCE_SECURITY_FEATURE_COUNT = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RetainFamiliarActivitiesImpl <em>Retain Familiar Activities</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RetainFamiliarActivitiesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRetainFamiliarActivities()
     * @generated
     */
    int RETAIN_FAMILIAR_ACTIVITIES = 49;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETAIN_FAMILIAR_ACTIVITIES__ID = Xpdl2Package.NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETAIN_FAMILIAR_ACTIVITIES__OTHER_ATTRIBUTES = Xpdl2Package.NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETAIN_FAMILIAR_ACTIVITIES__NAME = Xpdl2Package.NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Activity Ref</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETAIN_FAMILIAR_ACTIVITIES__ACTIVITY_REF = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Retain Familiar Activities</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETAIN_FAMILIAR_ACTIVITIES_FEATURE_COUNT = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RetryImpl <em>Retry</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RetryImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRetry()
     * @generated
     */
    int RETRY = 50;

    /**
     * The feature id for the '<em><b>Max</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETRY__MAX = 0;

    /**
     * The feature id for the '<em><b>Initial Period</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETRY__INITIAL_PERIOD = 1;

    /**
     * The feature id for the '<em><b>Period Increment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETRY__PERIOD_INCREMENT = 2;

    /**
     * The feature id for the '<em><b>Max Retry Action</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETRY__MAX_RETRY_ACTION = 3;

    /**
     * The number of structural features of the '<em>Retry</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RETRY_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ScriptInformationImpl <em>Script Information</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ScriptInformationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getScriptInformation()
     * @generated
     */
    int SCRIPT_INFORMATION = 51;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_INFORMATION__ID = Xpdl2Package.NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_INFORMATION__OTHER_ATTRIBUTES = Xpdl2Package.NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_INFORMATION__NAME = Xpdl2Package.NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_INFORMATION__EXPRESSION = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Direction</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_INFORMATION__DIRECTION = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Activity</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_INFORMATION__ACTIVITY = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Reference</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_INFORMATION__REFERENCE = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Script Information</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_INFORMATION_FEATURE_COUNT = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.SeparationOfDutiesActivitiesImpl <em>Separation Of Duties Activities</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.SeparationOfDutiesActivitiesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSeparationOfDutiesActivities()
     * @generated
     */
    int SEPARATION_OF_DUTIES_ACTIVITIES = 52;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEPARATION_OF_DUTIES_ACTIVITIES__ID = Xpdl2Package.NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEPARATION_OF_DUTIES_ACTIVITIES__OTHER_ATTRIBUTES = Xpdl2Package.NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEPARATION_OF_DUTIES_ACTIVITIES__NAME = Xpdl2Package.NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Activity Ref</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEPARATION_OF_DUTIES_ACTIVITIES__ACTIVITY_REF = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Separation Of Duties Activities</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEPARATION_OF_DUTIES_ACTIVITIES_FEATURE_COUNT = Xpdl2Package.NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.SignalDataImpl <em>Signal Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.SignalDataImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSignalData()
     * @generated
     */
    int SIGNAL_DATA = 53;

    /**
     * The feature id for the '<em><b>Correlation Mappings</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIGNAL_DATA__CORRELATION_MAPPINGS = 0;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIGNAL_DATA__DATA_MAPPINGS = 1;

    /**
     * The feature id for the '<em><b>Reschedule Timers</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIGNAL_DATA__RESCHEDULE_TIMERS = 2;

    /**
     * The feature id for the '<em><b>Input Script Data Mapper</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER = 3;

    /**
     * The feature id for the '<em><b>Output Script Data Mapper</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER = 4;

    /**
     * The number of structural features of the '<em>Signal Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIGNAL_DATA_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl <em>Start Method</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.StartMethodImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getStartMethod()
     * @generated
     */
    int START_METHOD = 54;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__ID = INTERFACE_METHOD__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__OTHER_ATTRIBUTES = INTERFACE_METHOD__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__NAME = INTERFACE_METHOD__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__DESCRIPTION = INTERFACE_METHOD__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Associated Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__ASSOCIATED_PARAMETERS = INTERFACE_METHOD__ASSOCIATED_PARAMETERS;

    /**
     * The feature id for the '<em><b>Disable Implicit Association</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__DISABLE_IMPLICIT_ASSOCIATION = INTERFACE_METHOD__DISABLE_IMPLICIT_ASSOCIATION;

    /**
     * The feature id for the '<em><b>Trigger</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__TRIGGER = INTERFACE_METHOD__TRIGGER;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__TRIGGER_RESULT_MESSAGE = INTERFACE_METHOD__TRIGGER_RESULT_MESSAGE;

    /**
     * The feature id for the '<em><b>Visibility</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__VISIBILITY = INTERFACE_METHOD__VISIBILITY;

    /**
     * The feature id for the '<em><b>Error Methods</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD__ERROR_METHODS = INTERFACE_METHOD__ERROR_METHODS;

    /**
     * The number of structural features of the '<em>Start Method</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_METHOD_FEATURE_COUNT = INTERFACE_METHOD_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.StructuredDiscriminatorImpl <em>Structured Discriminator</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.StructuredDiscriminatorImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getStructuredDiscriminator()
     * @generated
     */
    int STRUCTURED_DISCRIMINATOR = 55;

    /**
     * The feature id for the '<em><b>Wait For Incoming Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STRUCTURED_DISCRIMINATOR__WAIT_FOR_INCOMING_PATH = 0;

    /**
     * The feature id for the '<em><b>Up Stream Parallel Split</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STRUCTURED_DISCRIMINATOR__UP_STREAM_PARALLEL_SPLIT = 1;

    /**
     * The number of structural features of the '<em>Structured Discriminator</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STRUCTURED_DISCRIMINATOR_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.TaskLibraryReferenceImpl <em>Task Library Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.TaskLibraryReferenceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getTaskLibraryReference()
     * @generated
     */
    int TASK_LIBRARY_REFERENCE = 56;

    /**
     * The feature id for the '<em><b>Task Library Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_LIBRARY_REFERENCE__TASK_LIBRARY_ID = 0;

    /**
     * The feature id for the '<em><b>Package Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_LIBRARY_REFERENCE__PACKAGE_REF = 1;

    /**
     * The number of structural features of the '<em>Task Library Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_LIBRARY_REFERENCE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.TransformScriptImpl <em>Transform Script</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.TransformScriptImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getTransformScript()
     * @generated
     */
    int TRANSFORM_SCRIPT = 57;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSFORM_SCRIPT__EXTENDED_ATTRIBUTES = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSFORM_SCRIPT__DATA_MAPPINGS = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Input Dom</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSFORM_SCRIPT__INPUT_DOM = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Output Dom</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSFORM_SCRIPT__OUTPUT_DOM = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Transform Script</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSFORM_SCRIPT_FEATURE_COUNT = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl <em>User Task Scripts</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getUserTaskScripts()
     * @generated
     */
    int USER_TASK_SCRIPTS = 58;

    /**
     * The feature id for the '<em><b>Open Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_TASK_SCRIPTS__OPEN_SCRIPT = 0;

    /**
     * The feature id for the '<em><b>Close Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_TASK_SCRIPTS__CLOSE_SCRIPT = 1;

    /**
     * The feature id for the '<em><b>Submit Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_TASK_SCRIPTS__SUBMIT_SCRIPT = 2;

    /**
     * The feature id for the '<em><b>Schedule Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_TASK_SCRIPTS__SCHEDULE_SCRIPT = 3;

    /**
     * The feature id for the '<em><b>Reschedule Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT = 4;

    /**
     * The number of structural features of the '<em>User Task Scripts</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_TASK_SCRIPTS_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ValidationControlImpl <em>Validation Control</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ValidationControlImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getValidationControl()
     * @generated
     */
    int VALIDATION_CONTROL = 59;

    /**
     * The feature id for the '<em><b>Validation Issue Overrides</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES = 0;

    /**
     * The number of structural features of the '<em>Validation Control</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATION_CONTROL_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ValidationIssueOverrideImpl <em>Validation Issue Override</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ValidationIssueOverrideImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getValidationIssueOverride()
     * @generated
     */
    int VALIDATION_ISSUE_OVERRIDE = 60;

    /**
     * The feature id for the '<em><b>Validation Issue Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATION_ISSUE_OVERRIDE__VALIDATION_ISSUE_ID = 0;

    /**
     * The feature id for the '<em><b>Override Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE = 1;

    /**
     * The number of structural features of the '<em>Validation Issue Override</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATION_ISSUE_OVERRIDE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsdlEventAssociationImpl <em>Wsdl Event Association</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsdlEventAssociationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsdlEventAssociation()
     * @generated
     */
    int WSDL_EVENT_ASSOCIATION = 61;

    /**
     * The feature id for the '<em><b>Event Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WSDL_EVENT_ASSOCIATION__EVENT_ID = 0;

    /**
     * The number of structural features of the '<em>Wsdl Event Association</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WSDL_EVENT_ASSOCIATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WorkItemPriorityImpl <em>Work Item Priority</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WorkItemPriorityImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWorkItemPriority()
     * @generated
     */
    int WORK_ITEM_PRIORITY = 62;

    /**
     * The feature id for the '<em><b>Initial Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PRIORITY__INITIAL_PRIORITY = 0;

    /**
     * The number of structural features of the '<em>Work Item Priority</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PRIORITY_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsdlGenerationImpl <em>Wsdl Generation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsdlGenerationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsdlGeneration()
     * @generated
     */
    int WSDL_GENERATION = 63;

    /**
     * The feature id for the '<em><b>Soap Binding Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WSDL_GENERATION__SOAP_BINDING_STYLE = 0;

    /**
     * The number of structural features of the '<em>Wsdl Generation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WSDL_GENERATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsBindingImpl <em>Ws Binding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsBindingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsBinding()
     * @generated
     */
    int WS_BINDING = 64;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_BINDING__NAME = 0;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_BINDING__EXTENDED_PROPERTIES = 1;

    /**
     * The number of structural features of the '<em>Ws Binding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_BINDING_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsInboundImpl <em>Ws Inbound</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsInboundImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsInbound()
     * @generated
     */
    int WS_INBOUND = 65;

    /**
     * The feature id for the '<em><b>Virtual Binding</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_INBOUND__VIRTUAL_BINDING = 0;

    /**
     * The feature id for the '<em><b>Soap Http Binding</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_INBOUND__SOAP_HTTP_BINDING = 1;

    /**
     * The feature id for the '<em><b>Soap Jms Binding</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_INBOUND__SOAP_JMS_BINDING = 2;

    /**
     * The number of structural features of the '<em>Ws Inbound</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_INBOUND_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsOutboundImpl <em>Ws Outbound</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsOutboundImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsOutbound()
     * @generated
     */
    int WS_OUTBOUND = 66;

    /**
     * The feature id for the '<em><b>Virtual Binding</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_OUTBOUND__VIRTUAL_BINDING = 0;

    /**
     * The feature id for the '<em><b>Soap Http Binding</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_OUTBOUND__SOAP_HTTP_BINDING = 1;

    /**
     * The feature id for the '<em><b>Soap Jms Binding</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_OUTBOUND__SOAP_JMS_BINDING = 2;

    /**
     * The number of structural features of the '<em>Ws Outbound</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_OUTBOUND_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsResourceImpl <em>Ws Resource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsResourceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsResource()
     * @generated
     */
    int WS_RESOURCE = 67;

    /**
     * The feature id for the '<em><b>Inbound</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_RESOURCE__INBOUND = 0;

    /**
     * The feature id for the '<em><b>Outbound</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_RESOURCE__OUTBOUND = 1;

    /**
     * The number of structural features of the '<em>Ws Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_RESOURCE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsSecurityPolicyImpl <em>Ws Security Policy</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsSecurityPolicyImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSecurityPolicy()
     * @generated
     */
    int WS_SECURITY_POLICY = 68;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SECURITY_POLICY__EXTENDED_ATTRIBUTES = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Governance Application Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SECURITY_POLICY__GOVERNANCE_APPLICATION_NAME = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SECURITY_POLICY__TYPE = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Ws Security Policy</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SECURITY_POLICY_FEATURE_COUNT = Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl <em>Ws Soap Binding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapBinding()
     * @generated
     */
    int WS_SOAP_BINDING = 69;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_BINDING__NAME = WS_BINDING__NAME;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_BINDING__EXTENDED_PROPERTIES = WS_BINDING__EXTENDED_PROPERTIES;

    /**
     * The feature id for the '<em><b>Binding Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_BINDING__BINDING_STYLE = WS_BINDING_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Soap Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_BINDING__SOAP_VERSION = WS_BINDING_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Soap Security</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_BINDING__SOAP_SECURITY = WS_BINDING_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Ws Soap Binding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_BINDING_FEATURE_COUNT = WS_BINDING_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpInboundBindingImpl <em>Ws Soap Http Inbound Binding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapHttpInboundBindingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapHttpInboundBinding()
     * @generated
     */
    int WS_SOAP_HTTP_INBOUND_BINDING = 70;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING__NAME = WS_SOAP_BINDING__NAME;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING__EXTENDED_PROPERTIES = WS_SOAP_BINDING__EXTENDED_PROPERTIES;

    /**
     * The feature id for the '<em><b>Binding Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING__BINDING_STYLE = WS_SOAP_BINDING__BINDING_STYLE;

    /**
     * The feature id for the '<em><b>Soap Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING__SOAP_VERSION = WS_SOAP_BINDING__SOAP_VERSION;

    /**
     * The feature id for the '<em><b>Soap Security</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING__SOAP_SECURITY = WS_SOAP_BINDING__SOAP_SECURITY;

    /**
     * The feature id for the '<em><b>Inbound Security</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY = WS_SOAP_BINDING_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Endpoint Url Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING__ENDPOINT_URL_PATH = WS_SOAP_BINDING_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Http Connector Instance Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING__HTTP_CONNECTOR_INSTANCE_NAME = WS_SOAP_BINDING_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Ws Soap Http Inbound Binding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_INBOUND_BINDING_FEATURE_COUNT = WS_SOAP_BINDING_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpOutboundBindingImpl <em>Ws Soap Http Outbound Binding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapHttpOutboundBindingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapHttpOutboundBinding()
     * @generated
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING = 71;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING__NAME = WS_SOAP_BINDING__NAME;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING__EXTENDED_PROPERTIES = WS_SOAP_BINDING__EXTENDED_PROPERTIES;

    /**
     * The feature id for the '<em><b>Binding Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING__BINDING_STYLE = WS_SOAP_BINDING__BINDING_STYLE;

    /**
     * The feature id for the '<em><b>Soap Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING__SOAP_VERSION = WS_SOAP_BINDING__SOAP_VERSION;

    /**
     * The feature id for the '<em><b>Soap Security</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING__SOAP_SECURITY = WS_SOAP_BINDING__SOAP_SECURITY;

    /**
     * The feature id for the '<em><b>Outbound Security</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY = WS_SOAP_BINDING_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Http Client Instance Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING__HTTP_CLIENT_INSTANCE_NAME = WS_SOAP_BINDING_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Ws Soap Http Outbound Binding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_HTTP_OUTBOUND_BINDING_FEATURE_COUNT = WS_SOAP_BINDING_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsInboundBindingImpl <em>Ws Soap Jms Inbound Binding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapJmsInboundBindingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapJmsInboundBinding()
     * @generated
     */
    int WS_SOAP_JMS_INBOUND_BINDING = 72;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__NAME = WS_SOAP_BINDING__NAME;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__EXTENDED_PROPERTIES = WS_SOAP_BINDING__EXTENDED_PROPERTIES;

    /**
     * The feature id for the '<em><b>Binding Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__BINDING_STYLE = WS_SOAP_BINDING__BINDING_STYLE;

    /**
     * The feature id for the '<em><b>Soap Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__SOAP_VERSION = WS_SOAP_BINDING__SOAP_VERSION;

    /**
     * The feature id for the '<em><b>Soap Security</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__SOAP_SECURITY = WS_SOAP_BINDING__SOAP_SECURITY;

    /**
     * The feature id for the '<em><b>Outbound Connection Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY = WS_SOAP_BINDING_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Inbound Connection Factory Configuration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION = WS_SOAP_BINDING_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Inbound Destination</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION = WS_SOAP_BINDING_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Inbound Security</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY = WS_SOAP_BINDING_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Ws Soap Jms Inbound Binding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_INBOUND_BINDING_FEATURE_COUNT = WS_SOAP_BINDING_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl <em>Ws Soap Jms Outbound Binding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapJmsOutboundBinding()
     * @generated
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING = 73;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__NAME = WS_SOAP_BINDING__NAME;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__EXTENDED_PROPERTIES = WS_SOAP_BINDING__EXTENDED_PROPERTIES;

    /**
     * The feature id for the '<em><b>Binding Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__BINDING_STYLE = WS_SOAP_BINDING__BINDING_STYLE;

    /**
     * The feature id for the '<em><b>Soap Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__SOAP_VERSION = WS_SOAP_BINDING__SOAP_VERSION;

    /**
     * The feature id for the '<em><b>Soap Security</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__SOAP_SECURITY = WS_SOAP_BINDING__SOAP_SECURITY;

    /**
     * The feature id for the '<em><b>Outbound Connection Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY = WS_SOAP_BINDING_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Inbound Destination</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION = WS_SOAP_BINDING_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Outbound Destination</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION = WS_SOAP_BINDING_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Outbound Security</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY = WS_SOAP_BINDING_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Delivery Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE = WS_SOAP_BINDING_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY = WS_SOAP_BINDING_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Message Expiration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION = WS_SOAP_BINDING_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Invocation Timeout</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT = WS_SOAP_BINDING_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Ws Soap Jms Outbound Binding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_JMS_OUTBOUND_BINDING_FEATURE_COUNT = WS_SOAP_BINDING_FEATURE_COUNT + 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapSecurityImpl <em>Ws Soap Security</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapSecurityImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapSecurity()
     * @generated
     */
    int WS_SOAP_SECURITY = 74;

    /**
     * The feature id for the '<em><b>Security Policy</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_SECURITY__SECURITY_POLICY = 0;

    /**
     * The number of structural features of the '<em>Ws Soap Security</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_SOAP_SECURITY_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.WsVirtualBindingImpl <em>Ws Virtual Binding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.WsVirtualBindingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsVirtualBinding()
     * @generated
     */
    int WS_VIRTUAL_BINDING = 75;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_VIRTUAL_BINDING__NAME = WS_BINDING__NAME;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_VIRTUAL_BINDING__EXTENDED_PROPERTIES = WS_BINDING__EXTENDED_PROPERTIES;

    /**
     * The number of structural features of the '<em>Ws Virtual Binding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WS_VIRTUAL_BINDING_FEATURE_COUNT = WS_BINDING_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.XpdExtDataObjectAttributesImpl <em>Xpd Ext Data Object Attributes</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtDataObjectAttributesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdExtDataObjectAttributes()
     * @generated
     */
    int XPD_EXT_DATA_OBJECT_ATTRIBUTES = 76;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_DATA_OBJECT_ATTRIBUTES__DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_DATA_OBJECT_ATTRIBUTES__EXTERNAL_REFERENCE = 1;

    /**
     * The feature id for the '<em><b>Properties</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES = 2;

    /**
     * The number of structural features of the '<em>Xpd Ext Data Object Attributes</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_DATA_OBJECT_ATTRIBUTES_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.XpdExtPropertyImpl <em>Xpd Ext Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtPropertyImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdExtProperty()
     * @generated
     */
    int XPD_EXT_PROPERTY = 77;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_PROPERTY__NAME = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_PROPERTY__VALUE = 1;

    /**
     * The number of structural features of the '<em>Xpd Ext Property</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_PROPERTY_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl <em>Xpd Ext Attribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdExtAttribute()
     * @generated
     */
    int XPD_EXT_ATTRIBUTE = 78;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_ATTRIBUTE__MIXED = 0;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_ATTRIBUTE__GROUP = 1;

    /**
     * The feature id for the '<em><b>Any</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_ATTRIBUTE__ANY = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_ATTRIBUTE__NAME = 3;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_ATTRIBUTE__VALUE = 4;

    /**
     * The number of structural features of the '<em>Xpd Ext Attribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_ATTRIBUTE_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributesImpl <em>Xpd Ext Attributes</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtAttributesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdExtAttributes()
     * @generated
     */
    int XPD_EXT_ATTRIBUTES = 79;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_ATTRIBUTES__ATTRIBUTES = 0;

    /**
     * The number of structural features of the '<em>Xpd Ext Attributes</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XPD_EXT_ATTRIBUTES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.UpdateCaseOperationTypeImpl <em>Update Case Operation Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.UpdateCaseOperationTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getUpdateCaseOperationType()
     * @generated
     */
    int UPDATE_CASE_OPERATION_TYPE = 80;

    /**
     * The feature id for the '<em><b>From Field Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH = 0;

    /**
     * The number of structural features of the '<em>Update Case Operation Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPDATE_CASE_OPERATION_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AddLinkAssociationsTypeImpl <em>Add Link Associations Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AddLinkAssociationsTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAddLinkAssociationsType()
     * @generated
     */
    int ADD_LINK_ASSOCIATIONS_TYPE = 81;

    /**
     * The feature id for the '<em><b>Add Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADD_LINK_ASSOCIATIONS_TYPE__ADD_CASE_REF_FIELD = 0;

    /**
     * The feature id for the '<em><b>Association Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADD_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME = 1;

    /**
     * The number of structural features of the '<em>Add Link Associations Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADD_LINK_ASSOCIATIONS_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RemoveLinkAssociationsTypeImpl <em>Remove Link Associations Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RemoveLinkAssociationsTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRemoveLinkAssociationsType()
     * @generated
     */
    int REMOVE_LINK_ASSOCIATIONS_TYPE = 82;

    /**
     * The feature id for the '<em><b>Association Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME = 0;

    /**
     * The feature id for the '<em><b>Remove Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_LINK_ASSOCIATIONS_TYPE__REMOVE_CASE_REF_FIELD = 1;

    /**
     * The number of structural features of the '<em>Remove Link Associations Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REMOVE_LINK_ASSOCIATIONS_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl <em>Case Reference Operations Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseReferenceOperationsType()
     * @generated
     */
    int CASE_REFERENCE_OPERATIONS_TYPE = 83;

    /**
     * The feature id for the '<em><b>Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD = 0;

    /**
     * The feature id for the '<em><b>Update</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_REFERENCE_OPERATIONS_TYPE__UPDATE = 1;

    /**
     * The feature id for the '<em><b>Delete</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_REFERENCE_OPERATIONS_TYPE__DELETE = 2;

    /**
     * The feature id for the '<em><b>Add Link Associations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS = 3;

    /**
     * The feature id for the '<em><b>Remove Link Associations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS = 4;

    /**
     * The number of structural features of the '<em>Case Reference Operations Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_REFERENCE_OPERATIONS_TYPE_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.GlobalDataOperationImpl <em>Global Data Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.GlobalDataOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getGlobalDataOperation()
     * @generated
     */
    int GLOBAL_DATA_OPERATION = 84;

    /**
     * The feature id for the '<em><b>Case Access Operations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS = 0;

    /**
     * The feature id for the '<em><b>Case Reference Operations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS = 1;

    /**
     * The number of structural features of the '<em>Global Data Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GLOBAL_DATA_OPERATION_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DeleteByCaseIdentifierTypeImpl <em>Delete By Case Identifier Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DeleteByCaseIdentifierTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeleteByCaseIdentifierType()
     * @generated
     */
    int DELETE_BY_CASE_IDENTIFIER_TYPE = 85;

    /**
     * The feature id for the '<em><b>Field Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_BY_CASE_IDENTIFIER_TYPE__FIELD_PATH = 0;

    /**
     * The feature id for the '<em><b>Identifier Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_BY_CASE_IDENTIFIER_TYPE__IDENTIFIER_NAME = 1;

    /**
     * The number of structural features of the '<em>Delete By Case Identifier Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_BY_CASE_IDENTIFIER_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CompositeIdentifierTypeImpl <em>Composite Identifier Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CompositeIdentifierTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCompositeIdentifierType()
     * @generated
     */
    int COMPOSITE_IDENTIFIER_TYPE = 86;

    /**
     * The feature id for the '<em><b>Field Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPOSITE_IDENTIFIER_TYPE__FIELD_PATH = 0;

    /**
     * The feature id for the '<em><b>Identifiername</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPOSITE_IDENTIFIER_TYPE__IDENTIFIERNAME = 1;

    /**
     * The number of structural features of the '<em>Composite Identifier Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPOSITE_IDENTIFIER_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DeleteCaseReferenceOperationTypeImpl <em>Delete Case Reference Operation Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DeleteCaseReferenceOperationTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeleteCaseReferenceOperationType()
     * @generated
     */
    int DELETE_CASE_REFERENCE_OPERATION_TYPE = 87;

    /**
     * The number of structural features of the '<em>Delete Case Reference Operation Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_CASE_REFERENCE_OPERATION_TYPE_FEATURE_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DeleteByCompositeIdentifiersTypeImpl <em>Delete By Composite Identifiers Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DeleteByCompositeIdentifiersTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeleteByCompositeIdentifiersType()
     * @generated
     */
    int DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE = 88;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Composite Identifier</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER = 1;

    /**
     * The number of structural features of the '<em>Delete By Composite Identifiers Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CreateCaseOperationTypeImpl <em>Create Case Operation Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CreateCaseOperationTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCreateCaseOperationType()
     * @generated
     */
    int CREATE_CASE_OPERATION_TYPE = 89;

    /**
     * The feature id for the '<em><b>From Field Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH = 0;

    /**
     * The feature id for the '<em><b>To Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_CASE_OPERATION_TYPE__TO_CASE_REF_FIELD = 1;

    /**
     * The number of structural features of the '<em>Create Case Operation Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_CASE_OPERATION_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CaseAccessOperationsTypeImpl <em>Case Access Operations Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CaseAccessOperationsTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseAccessOperationsType()
     * @generated
     */
    int CASE_ACCESS_OPERATIONS_TYPE = 90;

    /**
     * The feature id for the '<em><b>Case Class External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE = 0;

    /**
     * The feature id for the '<em><b>Create</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_ACCESS_OPERATIONS_TYPE__CREATE = 1;

    /**
     * The feature id for the '<em><b>Delete By Case Identifier</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER = 2;

    /**
     * The feature id for the '<em><b>Delete By Composite Identifiers</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS = 3;

    /**
     * The number of structural features of the '<em>Case Access Operations Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_ACCESS_OPERATIONS_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DataWorkItemAttributeMappingImpl <em>Data Work Item Attribute Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DataWorkItemAttributeMappingImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDataWorkItemAttributeMapping()
     * @generated
     */
    int DATA_WORK_ITEM_ATTRIBUTE_MAPPING = 91;

    /**
     * The feature id for the '<em><b>Attribute</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_WORK_ITEM_ATTRIBUTE_MAPPING__ATTRIBUTE = 0;

    /**
     * The feature id for the '<em><b>Process Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_WORK_ITEM_ATTRIBUTE_MAPPING__PROCESS_DATA = 1;

    /**
     * The number of structural features of the '<em>Data Work Item Attribute Mapping</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_WORK_ITEM_ATTRIBUTE_MAPPING_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ProcessDataWorkItemAttributeMappingsImpl <em>Process Data Work Item Attribute Mappings</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ProcessDataWorkItemAttributeMappingsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getProcessDataWorkItemAttributeMappings()
     * @generated
     */
    int PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS = 92;

    /**
     * The feature id for the '<em><b>Data Work Item Attribute Mapping</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING = 0;

    /**
     * The number of structural features of the '<em>Process Data Work Item Attribute Mappings</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.BpmRuntimeConfigurationImpl <em>Bpm Runtime Configuration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.BpmRuntimeConfigurationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getBpmRuntimeConfiguration()
     * @generated
     */
    int BPM_RUNTIME_CONFIGURATION = 93;

    /**
     * The feature id for the '<em><b>Incoming Request Timeout</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPM_RUNTIME_CONFIGURATION__INCOMING_REQUEST_TIMEOUT = 0;

    /**
     * The number of structural features of the '<em>Bpm Runtime Configuration</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPM_RUNTIME_CONFIGURATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.EnablementTypeImpl <em>Enablement Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.EnablementTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getEnablementType()
     * @generated
     */
    int ENABLEMENT_TYPE = 94;

    /**
     * The feature id for the '<em><b>Initializer Activities</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES = 0;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENABLEMENT_TYPE__PRECONDITION_EXPRESSION = 1;

    /**
     * The number of structural features of the '<em>Enablement Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENABLEMENT_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.InitializerActivitiesTypeImpl <em>Initializer Activities Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.InitializerActivitiesTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getInitializerActivitiesType()
     * @generated
     */
    int INITIALIZER_ACTIVITIES_TYPE = 95;

    /**
     * The feature id for the '<em><b>Activity Ref</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INITIALIZER_ACTIVITIES_TYPE__ACTIVITY_REF = 0;

    /**
     * The number of structural features of the '<em>Initializer Activities Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INITIALIZER_ACTIVITIES_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl <em>Ad Hoc Task Configuration Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAdHocTaskConfigurationType()
     * @generated
     */
    int AD_HOC_TASK_CONFIGURATION_TYPE = 96;

    /**
     * The feature id for the '<em><b>Enablement</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT = 0;

    /**
     * The feature id for the '<em><b>Ad Hoc Execution Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE = 1;

    /**
     * The feature id for the '<em><b>Suspend Main Flow</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW = 2;

    /**
     * The feature id for the '<em><b>Allow Multiple Invocations</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS = 3;

    /**
     * The feature id for the '<em><b>Required Access Privileges</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES = 4;

    /**
     * The number of structural features of the '<em>Ad Hoc Task Configuration Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AD_HOC_TASK_CONFIGURATION_TYPE_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RequiredAccessPrivilegesImpl <em>Required Access Privileges</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RequiredAccessPrivilegesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRequiredAccessPrivileges()
     * @generated
     */
    int REQUIRED_ACCESS_PRIVILEGES = 97;

    /**
     * The feature id for the '<em><b>Privilege Reference</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE = 0;

    /**
     * The number of structural features of the '<em>Required Access Privileges</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REQUIRED_ACCESS_PRIVILEGES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.VisibleForCaseStatesImpl <em>Visible For Case States</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.VisibleForCaseStatesImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getVisibleForCaseStates()
     * @generated
     */
    int VISIBLE_FOR_CASE_STATES = 98;

    /**
     * The feature id for the '<em><b>Visible For Unset Case State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VISIBLE_FOR_CASE_STATES__VISIBLE_FOR_UNSET_CASE_STATE = 0;

    /**
     * The feature id for the '<em><b>Case State</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VISIBLE_FOR_CASE_STATES__CASE_STATE = 1;

    /**
     * The number of structural features of the '<em>Visible For Case States</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VISIBLE_FOR_CASE_STATES_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CaseServiceImpl <em>Case Service</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CaseServiceImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseService()
     * @generated
     */
    int CASE_SERVICE = 99;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_SERVICE__OTHER_ELEMENTS = Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Case Class Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_SERVICE__CASE_CLASS_TYPE = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Visible For Case States</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_SERVICE__VISIBLE_FOR_CASE_STATES = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Case Service</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_SERVICE_FEATURE_COUNT = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DocumentOperationImpl <em>Document Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DocumentOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDocumentOperation()
     * @generated
     */
    int DOCUMENT_OPERATION = 100;

    /**
     * The feature id for the '<em><b>Case Doc Ref Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION = 0;

    /**
     * The feature id for the '<em><b>Case Doc Find Operations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS = 1;

    /**
     * The feature id for the '<em><b>Link System Document Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION = 2;

    /**
     * The number of structural features of the '<em>Document Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_OPERATION_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl <em>Case Doc Ref Operations</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseDocRefOperations()
     * @generated
     */
    int CASE_DOC_REF_OPERATIONS = 101;

    /**
     * The feature id for the '<em><b>Move Case Doc Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION = 0;

    /**
     * The feature id for the '<em><b>Unlink Case Doc Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION = 1;

    /**
     * The feature id for the '<em><b>Link Case Doc Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION = 2;

    /**
     * The feature id for the '<em><b>Delete Case Doc Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION = 3;

    /**
     * The feature id for the '<em><b>Case Document Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD = 4;

    /**
     * The number of structural features of the '<em>Case Doc Ref Operations</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_REF_OPERATIONS_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CaseDocFindOperationsImpl <em>Case Doc Find Operations</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CaseDocFindOperationsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseDocFindOperations()
     * @generated
     */
    int CASE_DOC_FIND_OPERATIONS = 102;

    /**
     * The feature id for the '<em><b>Find By File Name Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION = 0;

    /**
     * The feature id for the '<em><b>Find By Query Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION = 1;

    /**
     * The feature id for the '<em><b>Return Case Doc Refs Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_FIND_OPERATIONS__RETURN_CASE_DOC_REFS_FIELD = 2;

    /**
     * The feature id for the '<em><b>Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_FIND_OPERATIONS__CASE_REF_FIELD = 3;

    /**
     * The number of structural features of the '<em>Case Doc Find Operations</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOC_FIND_OPERATIONS_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.MoveCaseDocOperationImpl <em>Move Case Doc Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.MoveCaseDocOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getMoveCaseDocOperation()
     * @generated
     */
    int MOVE_CASE_DOC_OPERATION = 103;

    /**
     * The feature id for the '<em><b>Source Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_CASE_DOC_OPERATION__SOURCE_CASE_REF_FIELD = 0;

    /**
     * The feature id for the '<em><b>Target Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD = 1;

    /**
     * The number of structural features of the '<em>Move Case Doc Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MOVE_CASE_DOC_OPERATION_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.UnlinkCaseDocOperationImpl <em>Unlink Case Doc Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.UnlinkCaseDocOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getUnlinkCaseDocOperation()
     * @generated
     */
    int UNLINK_CASE_DOC_OPERATION = 104;

    /**
     * The feature id for the '<em><b>Source Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNLINK_CASE_DOC_OPERATION__SOURCE_CASE_REF_FIELD = 0;

    /**
     * The number of structural features of the '<em>Unlink Case Doc Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNLINK_CASE_DOC_OPERATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.LinkCaseDocOperationImpl <em>Link Case Doc Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.LinkCaseDocOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getLinkCaseDocOperation()
     * @generated
     */
    int LINK_CASE_DOC_OPERATION = 105;

    /**
     * The feature id for the '<em><b>Target Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LINK_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD = 0;

    /**
     * The number of structural features of the '<em>Link Case Doc Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LINK_CASE_DOC_OPERATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.LinkSystemDocumentOperationImpl <em>Link System Document Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.LinkSystemDocumentOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getLinkSystemDocumentOperation()
     * @generated
     */
    int LINK_SYSTEM_DOCUMENT_OPERATION = 106;

    /**
     * The feature id for the '<em><b>Document Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LINK_SYSTEM_DOCUMENT_OPERATION__DOCUMENT_ID = 0;

    /**
     * The feature id for the '<em><b>Return Case Doc Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LINK_SYSTEM_DOCUMENT_OPERATION__RETURN_CASE_DOC_REF_FIELD = 1;

    /**
     * The feature id for the '<em><b>Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LINK_SYSTEM_DOCUMENT_OPERATION__CASE_REF_FIELD = 2;

    /**
     * The number of structural features of the '<em>Link System Document Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LINK_SYSTEM_DOCUMENT_OPERATION_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DeleteCaseDocOperationImpl <em>Delete Case Doc Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DeleteCaseDocOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeleteCaseDocOperation()
     * @generated
     */
    int DELETE_CASE_DOC_OPERATION = 107;

    /**
     * The number of structural features of the '<em>Delete Case Doc Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_CASE_DOC_OPERATION_FEATURE_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.FindByFileNameOperationImpl <em>Find By File Name Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.FindByFileNameOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFindByFileNameOperation()
     * @generated
     */
    int FIND_BY_FILE_NAME_OPERATION = 108;

    /**
     * The feature id for the '<em><b>File Name Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIND_BY_FILE_NAME_OPERATION__FILE_NAME_FIELD = 0;

    /**
     * The number of structural features of the '<em>Find By File Name Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIND_BY_FILE_NAME_OPERATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.FindByQueryOperationImpl <em>Find By Query Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.FindByQueryOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFindByQueryOperation()
     * @generated
     */
    int FIND_BY_QUERY_OPERATION = 109;

    /**
     * The feature id for the '<em><b>Case Document Query Expression</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION = 0;

    /**
     * The number of structural features of the '<em>Find By Query Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FIND_BY_QUERY_OPERATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl <em>Case Document Query Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseDocumentQueryExpression()
     * @generated
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION = 110;

    /**
     * The feature id for the '<em><b>Query Expression Join Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE = 0;

    /**
     * The feature id for the '<em><b>Open Bracket Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT = 1;

    /**
     * The feature id for the '<em><b>Cmis Property</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY = 2;

    /**
     * The feature id for the '<em><b>Operator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR = 3;

    /**
     * The feature id for the '<em><b>Process Data Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD = 4;

    /**
     * The feature id for the '<em><b>Close Bracket Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT = 5;

    /**
     * The feature id for the '<em><b>Cmis Document Property Selected</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED = 6;

    /**
     * The number of structural features of the '<em>Case Document Query Expression</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CASE_DOCUMENT_QUERY_EXPRESSION_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ServiceProcessConfigurationImpl <em>Service Process Configuration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ServiceProcessConfigurationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getServiceProcessConfiguration()
     * @generated
     */
    int SERVICE_PROCESS_CONFIGURATION = 111;

    /**
     * The feature id for the '<em><b>Deploy To Process Runtime</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME = 0;

    /**
     * The feature id for the '<em><b>Deploy To Pageflow Runtime</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME = 1;

    /**
     * The number of structural features of the '<em>Service Process Configuration</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_PROCESS_CONFIGURATION_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl <em>Script Data Mapper</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getScriptDataMapper()
     * @generated
     */
    int SCRIPT_DATA_MAPPER = 112;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DATA_MAPPER__OTHER_ELEMENTS = Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Mapper Context</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DATA_MAPPER__MAPPER_CONTEXT = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Mapping Direction</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DATA_MAPPER__MAPPING_DIRECTION = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DATA_MAPPER__DATA_MAPPINGS = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Unmapped Scripts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Array Inflation Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Script Data Mapper</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_DATA_MAPPER_FEATURE_COUNT = Xpdl2Package.OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 6;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.DataMapperArrayInflationImpl <em>Data Mapper Array Inflation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.DataMapperArrayInflationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDataMapperArrayInflation()
     * @generated
     */
    int DATA_MAPPER_ARRAY_INFLATION = 113;

    /**
     * The feature id for the '<em><b>Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPER_ARRAY_INFLATION__PATH = 0;

    /**
     * The feature id for the '<em><b>Mapping Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE = 1;

    /**
     * The feature id for the '<em><b>Contributor Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPER_ARRAY_INFLATION__CONTRIBUTOR_ID = 2;

    /**
     * The number of structural features of the '<em>Data Mapper Array Inflation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPER_ARRAY_INFLATION_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionImpl <em>Like Mapping Exclusion</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getLikeMappingExclusion()
     * @generated
     */
    int LIKE_MAPPING_EXCLUSION = 114;

    /**
     * The feature id for the '<em><b>Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIKE_MAPPING_EXCLUSION__PATH = 0;

    /**
     * The number of structural features of the '<em>Like Mapping Exclusion</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIKE_MAPPING_EXCLUSION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionsImpl <em>Like Mapping Exclusions</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionsImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getLikeMappingExclusions()
     * @generated
     */
    int LIKE_MAPPING_EXCLUSIONS = 115;

    /**
     * The feature id for the '<em><b>Exclusions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS = 0;

    /**
     * The number of structural features of the '<em>Like Mapping Exclusions</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIKE_MAPPING_EXCLUSIONS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.impl.RestServiceOperationImpl <em>Rest Service Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.impl.RestServiceOperationImpl
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRestServiceOperation()
     * @generated
     */
    int REST_SERVICE_OPERATION = 116;

    /**
     * The feature id for the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_OPERATION__LOCATION = 0;

    /**
     * The feature id for the '<em><b>Method Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_OPERATION__METHOD_ID = 1;

    /**
     * The number of structural features of the '<em>Rest Service Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REST_SERVICE_OPERATION_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.AllocationStrategyType <em>Allocation Strategy Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.AllocationStrategyType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAllocationStrategyType()
     * @generated
     */
    int ALLOCATION_STRATEGY_TYPE = 117;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.AllocationType <em>Allocation Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.AllocationType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAllocationType()
     * @generated
     */
    int ALLOCATION_TYPE = 118;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.AuditEventType <em>Audit Event Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.AuditEventType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAuditEventType()
     * @generated
     */
    int AUDIT_EVENT_TYPE = 119;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.CorrelationMode <em>Correlation Mode</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.CorrelationMode
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCorrelationMode()
     * @generated
     */
    int CORRELATION_MODE = 120;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerType <em>Error Thrower Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.ErrorThrowerType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getErrorThrowerType()
     * @generated
     */
    int ERROR_THROWER_TYPE = 121;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy <em>Event Handler Flow Strategy</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getEventHandlerFlowStrategy()
     * @generated
     */
    int EVENT_HANDLER_FLOW_STRATEGY = 122;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.FlowRoutingStyle <em>Flow Routing Style</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.FlowRoutingStyle
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFlowRoutingStyle()
     * @generated
     */
    int FLOW_ROUTING_STYLE = 123;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.FormImplementationType <em>Form Implementation Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.FormImplementationType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFormImplementationType()
     * @generated
     */
    int FORM_IMPLEMENTATION_TYPE = 124;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.MaxRetryActionType <em>Max Retry Action Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.MaxRetryActionType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getMaxRetryActionType()
     * @generated
     */
    int MAX_RETRY_ACTION_TYPE = 125;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.RescheduleDurationType <em>Reschedule Duration Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.RescheduleDurationType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRescheduleDurationType()
     * @generated
     */
    int RESCHEDULE_DURATION_TYPE = 126;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType <em>Reschedule Timer Selection Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRescheduleTimerSelectionType()
     * @generated
     */
    int RESCHEDULE_TIMER_SELECTION_TYPE = 127;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.SecurityPolicy <em>Security Policy</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.SecurityPolicy
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSecurityPolicy()
     * @generated
     */
    int SECURITY_POLICY = 128;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.SoapBindingStyle <em>Soap Binding Style</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSoapBindingStyle()
     * @generated
     */
    int SOAP_BINDING_STYLE = 129;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.SubProcessStartStrategy <em>Sub Process Start Strategy</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.SubProcessStartStrategy
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSubProcessStartStrategy()
     * @generated
     */
    int SUB_PROCESS_START_STRATEGY = 130;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.SystemErrorActionType <em>System Error Action Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.SystemErrorActionType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSystemErrorActionType()
     * @generated
     */
    int SYSTEM_ERROR_ACTION_TYPE = 131;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverrideType <em>Validation Issue Override Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.ValidationIssueOverrideType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getValidationIssueOverrideType()
     * @generated
     */
    int VALIDATION_ISSUE_OVERRIDE_TYPE = 132;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.Visibility <em>Visibility</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.Visibility
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getVisibility()
     * @generated
     */
    int VISIBILITY = 133;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.DeliveryMode <em>Delivery Mode</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.DeliveryMode
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeliveryMode()
     * @generated
     */
    int DELIVERY_MODE = 134;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.XpdModelType <em>Xpd Model Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.XpdModelType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdModelType()
     * @generated
     */
    int XPD_MODEL_TYPE = 135;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.AdHocExecutionTypeType <em>Ad Hoc Execution Type Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.AdHocExecutionTypeType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAdHocExecutionTypeType()
     * @generated
     */
    int AD_HOC_EXECUTION_TYPE_TYPE = 136;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.QueryExpressionJoinType <em>Query Expression Join Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.QueryExpressionJoinType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getQueryExpressionJoinType()
     * @generated
     */
    int QUERY_EXPRESSION_JOIN_TYPE = 137;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.CMISQueryOperator <em>CMIS Query Operator</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.CMISQueryOperator
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCMISQueryOperator()
     * @generated
     */
    int CMIS_QUERY_OPERATOR = 138;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.AsyncExecutionMode <em>Async Execution Mode</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.AsyncExecutionMode
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAsyncExecutionMode()
     * @generated
     */
    int ASYNC_EXECUTION_MODE = 139;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.SignalType <em>Signal Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.SignalType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSignalType()
     * @generated
     */
    int SIGNAL_TYPE = 140;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.XpdInterfaceType <em>Xpd Interface Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.XpdInterfaceType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdInterfaceType()
     * @generated
     */
    int XPD_INTERFACE_TYPE = 141;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflationType <em>Data Mapper Array Inflation Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflationType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDataMapperArrayInflationType()
     * @generated
     */
    int DATA_MAPPER_ARRAY_INFLATION_TYPE = 142;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdExtension.BusinessServicePublishType <em>Business Service Publish Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.BusinessServicePublishType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getBusinessServicePublishType()
     * @generated
     */
    int BUSINESS_SERVICE_PUBLISH_TYPE = 143;

    /**
     * The meta object id for the '<em>Audit Event Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.AuditEventType
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAuditEventTypeObject()
     * @generated
     */
    int AUDIT_EVENT_TYPE_OBJECT = 144;

    /**
     * The meta object id for the '<em>Security Policy Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.SecurityPolicy
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSecurityPolicyObject()
     * @generated
     */
    int SECURITY_POLICY_OBJECT = 145;

    /**
     * The meta object id for the '<em>Soap Binding Style Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
     * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSoapBindingStyleObject()
     * @generated
     */
    int SOAP_BINDING_STYLE_OBJECT = 146;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ActivityRef <em>Activity Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activity Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.ActivityRef
     * @generated
     */
    EClass getActivityRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ActivityRef#getIdRef <em>Id Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.ActivityRef#getIdRef()
     * @see #getActivityRef()
     * @generated
     */
    EAttribute getActivityRef_IdRef();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns <em>Activity Resource Patterns</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activity Resource Patterns</em>'.
     * @see com.tibco.xpd.xpdExtension.ActivityResourcePatterns
     * @generated
     */
    EClass getActivityResourcePatterns();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getAllocationStrategy <em>Allocation Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Allocation Strategy</em>'.
     * @see com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getAllocationStrategy()
     * @see #getActivityResourcePatterns()
     * @generated
     */
    EReference getActivityResourcePatterns_AllocationStrategy();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getPiling <em>Piling</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Piling</em>'.
     * @see com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getPiling()
     * @see #getActivityResourcePatterns()
     * @generated
     */
    EReference getActivityResourcePatterns_Piling();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getWorkItemPriority <em>Work Item Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item Priority</em>'.
     * @see com.tibco.xpd.xpdExtension.ActivityResourcePatterns#getWorkItemPriority()
     * @see #getActivityResourcePatterns()
     * @generated
     */
    EReference getActivityResourcePatterns_WorkItemPriority();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AllocationStrategy <em>Allocation Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocation Strategy</em>'.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategy
     * @generated
     */
    EClass getAllocationStrategy();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getOffer <em>Offer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Offer</em>'.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategy#getOffer()
     * @see #getAllocationStrategy()
     * @generated
     */
    EAttribute getAllocationStrategy_Offer();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getStrategy <em>Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Strategy</em>'.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategy#getStrategy()
     * @see #getAllocationStrategy()
     * @generated
     */
    EAttribute getAllocationStrategy_Strategy();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnClose <em>Re Offer On Close</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Re Offer On Close</em>'.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnClose()
     * @see #getAllocationStrategy()
     * @generated
     */
    EAttribute getAllocationStrategy_ReOfferOnClose();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnCancel <em>Re Offer On Cancel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Re Offer On Cancel</em>'.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnCancel()
     * @see #getAllocationStrategy()
     * @generated
     */
    EAttribute getAllocationStrategy_ReOfferOnCancel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getAllocateToOfferSetMemberIdentifier <em>Allocate To Offer Set Member Identifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Allocate To Offer Set Member Identifier</em>'.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategy#getAllocateToOfferSetMemberIdentifier()
     * @see #getAllocationStrategy()
     * @generated
     */
    EAttribute getAllocationStrategy_AllocateToOfferSetMemberIdentifier();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationFields <em>Associated Correlation Fields</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Associated Correlation Fields</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedCorrelationFields
     * @generated
     */
    EClass getAssociatedCorrelationFields();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationFields#getAssociatedCorrelationField <em>Associated Correlation Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Associated Correlation Field</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedCorrelationFields#getAssociatedCorrelationField()
     * @see #getAssociatedCorrelationFields()
     * @generated
     */
    EReference getAssociatedCorrelationFields_AssociatedCorrelationField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationFields#isDisableImplicitAssociation <em>Disable Implicit Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Disable Implicit Association</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedCorrelationFields#isDisableImplicitAssociation()
     * @see #getAssociatedCorrelationFields()
     * @generated
     */
    EAttribute getAssociatedCorrelationFields_DisableImplicitAssociation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField <em>Associated Correlation Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Associated Correlation Field</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedCorrelationField
     * @generated
     */
    EClass getAssociatedCorrelationField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getName()
     * @see #getAssociatedCorrelationField()
     * @generated
     */
    EAttribute getAssociatedCorrelationField_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getCorrelationMode <em>Correlation Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Correlation Mode</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedCorrelationField#getCorrelationMode()
     * @see #getAssociatedCorrelationField()
     * @generated
     */
    EAttribute getAssociatedCorrelationField_CorrelationMode();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AssociatedParameter <em>Associated Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Associated Parameter</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParameter
     * @generated
     */
    EClass getAssociatedParameter();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#getFormalParam <em>Formal Param</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Formal Param</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParameter#getFormalParam()
     * @see #getAssociatedParameter()
     * @generated
     */
    EAttribute getAssociatedParameter_FormalParam();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#getMode <em>Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mode</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParameter#getMode()
     * @see #getAssociatedParameter()
     * @generated
     */
    EAttribute getAssociatedParameter_Mode();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#isMandatory <em>Mandatory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mandatory</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParameter#isMandatory()
     * @see #getAssociatedParameter()
     * @generated
     */
    EAttribute getAssociatedParameter_Mandatory();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AssociatedParameters <em>Associated Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Associated Parameters</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParameters
     * @generated
     */
    EClass getAssociatedParameters();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.AssociatedParameters#getAssociatedParameter <em>Associated Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Associated Parameter</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParameters#getAssociatedParameter()
     * @see #getAssociatedParameters()
     * @generated
     */
    EReference getAssociatedParameters_AssociatedParameter();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AssociatedParameters#isDisableImplicitAssociation <em>Disable Implicit Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Disable Implicit Association</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParameters#isDisableImplicitAssociation()
     * @see #getAssociatedParameters()
     * @generated
     */
    EAttribute getAssociatedParameters_DisableImplicitAssociation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer <em>Associated Parameters Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Associated Parameters Container</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParametersContainer
     * @generated
     */
    EClass getAssociatedParametersContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer#getAssociatedParameters <em>Associated Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Associated Parameters</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParametersContainer#getAssociatedParameters()
     * @see #getAssociatedParametersContainer()
     * @generated
     */
    EReference getAssociatedParametersContainer_AssociatedParameters();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer#isDisableImplicitAssociation <em>Disable Implicit Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Disable Implicit Association</em>'.
     * @see com.tibco.xpd.xpdExtension.AssociatedParametersContainer#isDisableImplicitAssociation()
     * @see #getAssociatedParametersContainer()
     * @generated
     */
    EAttribute getAssociatedParametersContainer_DisableImplicitAssociation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.Audit <em>Audit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Audit</em>'.
     * @see com.tibco.xpd.xpdExtension.Audit
     * @generated
     */
    EClass getAudit();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.Audit#getAuditEvent <em>Audit Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Audit Event</em>'.
     * @see com.tibco.xpd.xpdExtension.Audit#getAuditEvent()
     * @see #getAudit()
     * @generated
     */
    EReference getAudit_AuditEvent();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdExtension.Audit#getAny <em>Any</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any</em>'.
     * @see com.tibco.xpd.xpdExtension.Audit#getAny()
     * @see #getAudit()
     * @generated
     */
    EAttribute getAudit_Any();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AuditEvent <em>Audit Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Audit Event</em>'.
     * @see com.tibco.xpd.xpdExtension.AuditEvent
     * @generated
     */
    EClass getAuditEvent();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AuditEvent#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.xpdExtension.AuditEvent#getType()
     * @see #getAuditEvent()
     * @generated
     */
    EAttribute getAuditEvent_Type();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.AuditEvent#getInformation <em>Information</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Information</em>'.
     * @see com.tibco.xpd.xpdExtension.AuditEvent#getInformation()
     * @see #getAuditEvent()
     * @generated
     */
    EReference getAuditEvent_Information();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.BusinessProcess <em>Business Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Business Process</em>'.
     * @see com.tibco.xpd.xpdExtension.BusinessProcess
     * @generated
     */
    EClass getBusinessProcess();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.BusinessProcess#getProcessId <em>Process Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Id</em>'.
     * @see com.tibco.xpd.xpdExtension.BusinessProcess#getProcessId()
     * @see #getBusinessProcess()
     * @generated
     */
    EAttribute getBusinessProcess_ProcessId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.BusinessProcess#getPackageRefId <em>Package Ref Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Package Ref Id</em>'.
     * @see com.tibco.xpd.xpdExtension.BusinessProcess#getPackageRefId()
     * @see #getBusinessProcess()
     * @generated
     */
    EAttribute getBusinessProcess_PackageRefId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.BusinessProcess#getActivityId <em>Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Id</em>'.
     * @see com.tibco.xpd.xpdExtension.BusinessProcess#getActivityId()
     * @see #getBusinessProcess()
     * @generated
     */
    EAttribute getBusinessProcess_ActivityId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CalendarReference <em>Calendar Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Calendar Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.CalendarReference
     * @generated
     */
    EClass getCalendarReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CalendarReference#getAlias <em>Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alias</em>'.
     * @see com.tibco.xpd.xpdExtension.CalendarReference#getAlias()
     * @see #getCalendarReference()
     * @generated
     */
    EAttribute getCalendarReference_Alias();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CalendarReference#getDataFieldId <em>Data Field Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Data Field Id</em>'.
     * @see com.tibco.xpd.xpdExtension.CalendarReference#getDataFieldId()
     * @see #getCalendarReference()
     * @generated
     */
    EAttribute getCalendarReference_DataFieldId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CatchErrorMappings <em>Catch Error Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Catch Error Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.CatchErrorMappings
     * @generated
     */
    EClass getCatchErrorMappings();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CatchErrorMappings#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message</em>'.
     * @see com.tibco.xpd.xpdExtension.CatchErrorMappings#getMessage()
     * @see #getCatchErrorMappings()
     * @generated
     */
    EReference getCatchErrorMappings_Message();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ConstantPeriod <em>Constant Period</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Constant Period</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod
     * @generated
     */
    EClass getConstantPeriod();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getDays <em>Days</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Days</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod#getDays()
     * @see #getConstantPeriod()
     * @generated
     */
    EAttribute getConstantPeriod_Days();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getHours <em>Hours</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Hours</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod#getHours()
     * @see #getConstantPeriod()
     * @generated
     */
    EAttribute getConstantPeriod_Hours();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMicroSeconds <em>Micro Seconds</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Micro Seconds</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod#getMicroSeconds()
     * @see #getConstantPeriod()
     * @generated
     */
    EAttribute getConstantPeriod_MicroSeconds();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMinutes <em>Minutes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Minutes</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod#getMinutes()
     * @see #getConstantPeriod()
     * @generated
     */
    EAttribute getConstantPeriod_Minutes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getMonths <em>Months</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Months</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod#getMonths()
     * @see #getConstantPeriod()
     * @generated
     */
    EAttribute getConstantPeriod_Months();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getSeconds <em>Seconds</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Seconds</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod#getSeconds()
     * @see #getConstantPeriod()
     * @generated
     */
    EAttribute getConstantPeriod_Seconds();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getWeeks <em>Weeks</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Weeks</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod#getWeeks()
     * @see #getConstantPeriod()
     * @generated
     */
    EAttribute getConstantPeriod_Weeks();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ConstantPeriod#getYears <em>Years</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Years</em>'.
     * @see com.tibco.xpd.xpdExtension.ConstantPeriod#getYears()
     * @see #getConstantPeriod()
     * @generated
     */
    EAttribute getConstantPeriod_Years();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ConditionalParticipant <em>Conditional Participant</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Conditional Participant</em>'.
     * @see com.tibco.xpd.xpdExtension.ConditionalParticipant
     * @generated
     */
    EClass getConditionalParticipant();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ConditionalParticipant#getPerformerScript <em>Performer Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Performer Script</em>'.
     * @see com.tibco.xpd.xpdExtension.ConditionalParticipant#getPerformerScript()
     * @see #getConditionalParticipant()
     * @generated
     */
    EReference getConditionalParticipant_PerformerScript();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings <em>Reply Immediate Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reply Immediate Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings
     * @generated
     */
    EClass getReplyImmediateDataMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings#getDataMappings <em>Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings#getDataMappings()
     * @see #getReplyImmediateDataMappings()
     * @generated
     */
    EReference getReplyImmediateDataMappings_DataMappings();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CorrelationDataMappings <em>Correlation Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Correlation Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.CorrelationDataMappings
     * @generated
     */
    EClass getCorrelationDataMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.CorrelationDataMappings#getDataMappings <em>Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.CorrelationDataMappings#getDataMappings()
     * @see #getCorrelationDataMappings()
     * @generated
     */
    EReference getCorrelationDataMappings_DataMappings();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.Discriminator <em>Discriminator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Discriminator</em>'.
     * @see com.tibco.xpd.xpdExtension.Discriminator
     * @generated
     */
    EClass getDiscriminator();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.Discriminator#getDiscriminatorType <em>Discriminator Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Discriminator Type</em>'.
     * @see com.tibco.xpd.xpdExtension.Discriminator#getDiscriminatorType()
     * @see #getDiscriminator()
     * @generated
     */
    EAttribute getDiscriminator_DiscriminatorType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.Discriminator#getStructuredDiscriminator <em>Structured Discriminator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Structured Discriminator</em>'.
     * @see com.tibco.xpd.xpdExtension.Discriminator#getStructuredDiscriminator()
     * @see #getDiscriminator()
     * @generated
     */
    EReference getDiscriminator_StructuredDiscriminator();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getImplementationType <em>Implementation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getImplementationType()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_ImplementationType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDataObjectAttributes <em>Data Object Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Object Attributes</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getDataObjectAttributes()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DataObjectAttributes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getExtendedAttributes <em>Extended Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Extended Attributes</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getExtendedAttributes()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ExtendedAttributes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isContinueOnTimeout <em>Continue On Timeout</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Continue On Timeout</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isContinueOnTimeout()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_ContinueOnTimeout();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAlias <em>Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alias</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getAlias()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Alias();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getConstantPeriod <em>Constant Period</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Constant Period</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getConstantPeriod()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ConstantPeriod();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getUserTaskScripts <em>User Task Scripts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>User Task Scripts</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getUserTaskScripts()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_UserTaskScripts();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAudit <em>Audit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Audit</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getAudit()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Audit();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getScript <em>Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Script</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getScript()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Script();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isReplyImmediately <em>Reply Immediately</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reply Immediately</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isReplyImmediately()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_ReplyImmediately();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getInitialValues <em>Initial Values</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Initial Values</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getInitialValues()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_InitialValues();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAssociatedCorrelationFields <em>Associated Correlation Fields</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Associated Correlation Fields</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getAssociatedCorrelationFields()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AssociatedCorrelationFields();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAssociatedParameters <em>Associated Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Associated Parameters</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getAssociatedParameters()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AssociatedParameters();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getImplementedInterface <em>Implemented Interface</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Implemented Interface</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getImplementedInterface()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ImplementedInterface();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessInterfaces <em>Process Interfaces</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Process Interfaces</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getProcessInterfaces()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ProcessInterfaces();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getWsdlEventAssociation <em>Wsdl Event Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Wsdl Event Association</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getWsdlEventAssociation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_WsdlEventAssociation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isInlineSubProcess <em>Inline Sub Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Inline Sub Process</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isInlineSubProcess()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_InlineSubProcess();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDocumentationURL <em>Documentation URL</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Documentation URL</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getDocumentationURL()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_DocumentationURL();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getImplements <em>Implements</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implements</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getImplements()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Implements();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getMultiInstanceScripts <em>Multi Instance Scripts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Multi Instance Scripts</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getMultiInstanceScripts()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_MultiInstanceScripts();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessIdentifierField <em>Process Identifier Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Identifier Field</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getProcessIdentifierField()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_ProcessIdentifierField();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Expression</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getExpression()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Expression();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getVisibility <em>Visibility</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Visibility</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getVisibility()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Visibility();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSecurityProfile <em>Security Profile</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Security Profile</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getSecurityProfile()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SecurityProfile();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getLanguage <em>Language</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Language</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getLanguage()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Language();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getInitialParameterValue <em>Initial Parameter Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Initial Parameter Value</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getInitialParameterValue()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_InitialParameterValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isInitialValueMapping <em>Initial Value Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Initial Value Mapping</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isInitialValueMapping()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_InitialValueMapping();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getPortTypeOperation <em>Port Type Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Port Type Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getPortTypeOperation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_PortTypeOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTransport <em>Transport</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Transport</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getTransport()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Transport();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsChained <em>Is Chained</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Chained</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isIsChained()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_IsChained();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getExternalReference()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ExternalReference();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessResourcePatterns <em>Process Resource Patterns</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Process Resource Patterns</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getProcessResourcePatterns()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ProcessResourcePatterns();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEventHandlerInitialisers <em>Event Handler Initialisers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Event Handler Initialisers</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getEventHandlerInitialisers()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_EventHandlerInitialisers();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getActivityResourcePatterns <em>Activity Resource Patterns</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Activity Resource Patterns</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getActivityResourcePatterns()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ActivityResourcePatterns();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isRequireNewTransaction <em>Require New Transaction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Require New Transaction</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isRequireNewTransaction()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_RequireNewTransaction();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDocumentOperation <em>Document Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Document Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getDocumentOperation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DocumentOperation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDurationCalculation <em>Duration Calculation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Duration Calculation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getDurationCalculation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DurationCalculation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDiscriminator <em>Discriminator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Discriminator</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getDiscriminator()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Discriminator();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDisplayName <em>Display Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Display Name</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getDisplayName()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_DisplayName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCatchThrow <em>Catch Throw</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Catch Throw</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getCatchThrow()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_CatchThrow();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsRemote <em>Is Remote</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Remote</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isIsRemote()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_IsRemote();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCorrelationDataMappings <em>Correlation Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Correlation Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getCorrelationDataMappings()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CorrelationDataMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTransformScript <em>Transform Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Transform Script</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getTransformScript()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_TransformScript();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsBusinessService <em>Publish As Business Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Publish As Business Service</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsBusinessService()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_PublishAsBusinessService();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServiceCategory <em>Business Service Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Business Service Category</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServiceCategory()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_BusinessServiceCategory();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getErrorThrowerInfo <em>Error Thrower Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Error Thrower Info</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getErrorThrowerInfo()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ErrorThrowerInfo();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCatchErrorMappings <em>Catch Error Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Catch Error Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getCatchErrorMappings()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CatchErrorMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getConditionalParticipant <em>Conditional Participant</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Conditional Participant</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getConditionalParticipant()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ConditionalParticipant();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isGenerated <em>Generated</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Generated</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isGenerated()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Generated();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getReplyToActivityId <em>Reply To Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reply To Activity Id</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getReplyToActivityId()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_ReplyToActivityId();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTaskLibraryReference <em>Task Library Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Task Library Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getTaskLibraryReference()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_TaskLibraryReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSetPerformerInProcess <em>Set Performer In Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Set Performer In Process</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isSetPerformerInProcess()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SetPerformerInProcess();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateHeight <em>Emb Subproc Other State Height</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Emb Subproc Other State Height</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateHeight()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_EmbSubprocOtherStateHeight();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateWidth <em>Emb Subproc Other State Width</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Emb Subproc Other State Width</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getEmbSubprocOtherStateWidth()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_EmbSubprocOtherStateWidth();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFormImplementation <em>Form Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Form Implementation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getFormImplementation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_FormImplementation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getParticipantQuery <em>Participant Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Participant Query</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getParticipantQuery()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ParticipantQuery();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getApiEndPointParticipant <em>Api End Point Participant</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Api End Point Participant</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getApiEndPointParticipant()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_ApiEndPointParticipant();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFaultMessage <em>Fault Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Fault Message</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getFaultMessage()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_FaultMessage();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRequestActivityId <em>Request Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Request Activity Id</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getRequestActivityId()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_RequestActivityId();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessProcess <em>Business Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Business Process</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessProcess()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_BusinessProcess();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getWsdlGeneration <em>Wsdl Generation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Wsdl Generation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getWsdlGeneration()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_WsdlGeneration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isTargetPrimitiveProperty <em>Target Primitive Property</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target Primitive Property</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isTargetPrimitiveProperty()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_TargetPrimitiveProperty();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSourcePrimitiveProperty <em>Source Primitive Property</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Source Primitive Property</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isSourcePrimitiveProperty()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SourcePrimitiveProperty();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDecisionService <em>Decision Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Decision Service</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getDecisionService()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DecisionService();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getParticipantSharedResource <em>Participant Shared Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Participant Shared Resource</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getParticipantSharedResource()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ParticipantSharedResource();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getXpdModelType <em>Xpd Model Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Xpd Model Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getXpdModelType()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_XpdModelType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getFlowRoutingStyle <em>Flow Routing Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Flow Routing Style</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getFlowRoutingStyle()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_FlowRoutingStyle();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCalendarReference <em>Calendar Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Calendar Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getCalendarReference()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CalendarReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isNonCancelling <em>Non Cancelling</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Non Cancelling</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isNonCancelling()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_NonCancelling();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSignalData <em>Signal Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Signal Data</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getSignalData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SignalData();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRetry <em>Retry</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Retry</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getRetry()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Retry();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getActivityDeadlineEventId <em>Activity Deadline Event Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Deadline Event Id</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getActivityDeadlineEventId()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_ActivityDeadlineEventId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getStartStrategy <em>Start Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Strategy</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getStartStrategy()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_StartStrategy();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isOverwriteAlreadyModifiedTaskData <em>Overwrite Already Modified Task Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Overwrite Already Modified Task Data</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isOverwriteAlreadyModifiedTaskData()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_OverwriteAlreadyModifiedTaskData();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getEventHandlerFlowStrategy <em>Event Handler Flow Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Event Handler Flow Strategy</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getEventHandlerFlowStrategy()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_EventHandlerFlowStrategy();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getNamespacePrefixMap <em>Namespace Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Namespace Prefix Map</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getNamespacePrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_NamespacePrefixMap();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSuspendResumeWithParent <em>Suspend Resume With Parent</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Suspend Resume With Parent</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isSuspendResumeWithParent()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SuspendResumeWithParent();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSystemErrorAction <em>System Error Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>System Error Action</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getSystemErrorAction()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SystemErrorAction();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCorrelationTimeout <em>Correlation Timeout</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Correlation Timeout</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getCorrelationTimeout()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CorrelationTimeout();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getValidationControl <em>Validation Control</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Validation Control</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getValidationControl()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ValidationControl();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getReplyImmediateDataMappings <em>Reply Immediate Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Reply Immediate Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getReplyImmediateDataMappings()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ReplyImmediateDataMappings();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isBxUseUnqualifiedPropertyNames <em>Bx Use Unqualified Property Names</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Bx Use Unqualified Property Names</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isBxUseUnqualifiedPropertyNames()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_BxUseUnqualifiedPropertyNames();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCaseRefType <em>Case Ref Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Case Ref Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getCaseRefType()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CaseRefType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRESTServices <em>REST Services</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>REST Services</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getRESTServices()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_RESTServices();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsRestService <em>Publish As Rest Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Publish As Rest Service</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isPublishAsRestService()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_PublishAsRestService();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRestActivityId <em>Rest Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Rest Activity Id</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getRestActivityId()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_RestActivityId();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRescheduleTimerScript <em>Reschedule Timer Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Reschedule Timer Script</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getRescheduleTimerScript()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_RescheduleTimerScript();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getDynamicOrganizationMappings <em>Dynamic Organization Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Dynamic Organization Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getDynamicOrganizationMappings()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DynamicOrganizationMappings();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSignalHandlerAsynchronous <em>Signal Handler Asynchronous</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Signal Handler Asynchronous</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isSignalHandlerAsynchronous()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SignalHandlerAsynchronous();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getGlobalDataOperation <em>Global Data Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Global Data Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getGlobalDataOperation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GlobalDataOperation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getProcessDataWorkItemAttributeMappings <em>Process Data Work Item Attribute Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Process Data Work Item Attribute Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getProcessDataWorkItemAttributeMappings()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ProcessDataWorkItemAttributeMappings();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isAllowUnqualifiedSubProcessIdentification <em>Allow Unqualified Sub Process Identification</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Allow Unqualified Sub Process Identification</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isAllowUnqualifiedSubProcessIdentification()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_AllowUnqualifiedSubProcessIdentification();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBpmRuntimeConfiguration <em>Bpm Runtime Configuration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Bpm Runtime Configuration</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getBpmRuntimeConfiguration()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_BpmRuntimeConfiguration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsCaseService <em>Is Case Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Case Service</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isIsCaseService()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_IsCaseService();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRequiredAccessPrivileges <em>Required Access Privileges</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Required Access Privileges</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getRequiredAccessPrivileges()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_RequiredAccessPrivileges();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getCaseService <em>Case Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Case Service</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getCaseService()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CaseService();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAdHocTaskConfiguration <em>Ad Hoc Task Configuration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Ad Hoc Task Configuration</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getAdHocTaskConfiguration()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AdHocTaskConfiguration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isIsEventSubProcess <em>Is Event Sub Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Event Sub Process</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isIsEventSubProcess()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_IsEventSubProcess();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isNonInterruptingEvent <em>Non Interrupting Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Non Interrupting Event</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isNonInterruptingEvent()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_NonInterruptingEvent();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isCorrelateImmediately <em>Correlate Immediately</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Correlate Immediately</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isCorrelateImmediately()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_CorrelateImmediately();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getAsyncExecutionMode <em>Async Execution Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Async Execution Mode</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getAsyncExecutionMode()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_AsyncExecutionMode();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSignalType <em>Signal Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Signal Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getSignalType()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SignalType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getServiceProcessConfiguration <em>Service Process Configuration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Service Process Configuration</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getServiceProcessConfiguration()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ServiceProcessConfiguration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isLikeMapping <em>Like Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Like Mapping</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isLikeMapping()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_LikeMapping();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getScriptDataMapper <em>Script Data Mapper</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Script Data Mapper</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getScriptDataMapper()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ScriptDataMapper();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getLikeMappingExclusions <em>Like Mapping Exclusions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Like Mapping Exclusions</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getLikeMappingExclusions()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_LikeMappingExclusions();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getSourceContributorId <em>Source Contributor Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Source Contributor Id</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getSourceContributorId()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SourceContributorId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getTargetContributorId <em>Target Contributor Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target Contributor Id</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getTargetContributorId()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_TargetContributorId();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getRestServiceOperation <em>Rest Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Rest Service Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getRestServiceOperation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_RestServiceOperation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getInputMappings <em>Input Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Input Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getInputMappings()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_InputMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getOutputMappings <em>Output Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Output Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getOutputMappings()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_OutputMappings();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServicePublishType <em>Business Service Publish Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Business Service Publish Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#getBusinessServicePublishType()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_BusinessServicePublishType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DocumentRoot#isSuppressMaxMappingsError <em>Suppress Max Mappings Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Suppress Max Mappings Error</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentRoot#isSuppressMaxMappingsError()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_SuppressMaxMappingsError();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DurationCalculation <em>Duration Calculation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Duration Calculation</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation
     * @generated
     */
    EClass getDurationCalculation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getYears <em>Years</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Years</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation#getYears()
     * @see #getDurationCalculation()
     * @generated
     */
    EReference getDurationCalculation_Years();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMonths <em>Months</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Months</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation#getMonths()
     * @see #getDurationCalculation()
     * @generated
     */
    EReference getDurationCalculation_Months();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getWeeks <em>Weeks</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Weeks</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation#getWeeks()
     * @see #getDurationCalculation()
     * @generated
     */
    EReference getDurationCalculation_Weeks();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getDays <em>Days</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Days</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation#getDays()
     * @see #getDurationCalculation()
     * @generated
     */
    EReference getDurationCalculation_Days();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getHours <em>Hours</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Hours</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation#getHours()
     * @see #getDurationCalculation()
     * @generated
     */
    EReference getDurationCalculation_Hours();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMinutes <em>Minutes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Minutes</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation#getMinutes()
     * @see #getDurationCalculation()
     * @generated
     */
    EReference getDurationCalculation_Minutes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getSeconds <em>Seconds</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Seconds</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation#getSeconds()
     * @see #getDurationCalculation()
     * @generated
     */
    EReference getDurationCalculation_Seconds();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DurationCalculation#getMicroseconds <em>Microseconds</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Microseconds</em>'.
     * @see com.tibco.xpd.xpdExtension.DurationCalculation#getMicroseconds()
     * @see #getDurationCalculation()
     * @generated
     */
    EReference getDurationCalculation_Microseconds();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMappings <em>Dynamic Organization Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Dynamic Organization Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrganizationMappings
     * @generated
     */
    EClass getDynamicOrganizationMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMappings#getDynamicOrganizationMapping <em>Dynamic Organization Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Dynamic Organization Mapping</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrganizationMappings#getDynamicOrganizationMapping()
     * @see #getDynamicOrganizationMappings()
     * @generated
     */
    EReference getDynamicOrganizationMappings_DynamicOrganizationMapping();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping <em>Dynamic Organization Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Dynamic Organization Mapping</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrganizationMapping
     * @generated
     */
    EClass getDynamicOrganizationMapping();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping#getSourcePath <em>Source Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Source Path</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrganizationMapping#getSourcePath()
     * @see #getDynamicOrganizationMapping()
     * @generated
     */
    EAttribute getDynamicOrganizationMapping_SourcePath();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping#getDynamicOrgIdentifierRef <em>Dynamic Org Identifier Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Dynamic Org Identifier Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrganizationMapping#getDynamicOrgIdentifierRef()
     * @see #getDynamicOrganizationMapping()
     * @generated
     */
    EReference getDynamicOrganizationMapping_DynamicOrgIdentifierRef();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef <em>Dynamic Org Identifier Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Dynamic Org Identifier Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef
     * @generated
     */
    EClass getDynamicOrgIdentifierRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getIdentifierName <em>Identifier Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Identifier Name</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getIdentifierName()
     * @see #getDynamicOrgIdentifierRef()
     * @generated
     */
    EAttribute getDynamicOrgIdentifierRef_IdentifierName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getDynamicOrgId <em>Dynamic Org Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Dynamic Org Id</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getDynamicOrgId()
     * @see #getDynamicOrgIdentifierRef()
     * @generated
     */
    EAttribute getDynamicOrgIdentifierRef_DynamicOrgId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getOrgModelPath <em>Org Model Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Org Model Path</em>'.
     * @see com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getOrgModelPath()
     * @see #getDynamicOrgIdentifierRef()
     * @generated
     */
    EAttribute getDynamicOrgIdentifierRef_OrgModelPath();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.EmailResource <em>Email Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Email Resource</em>'.
     * @see com.tibco.xpd.xpdExtension.EmailResource
     * @generated
     */
    EClass getEmailResource();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.EmailResource#getInstanceName <em>Instance Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Instance Name</em>'.
     * @see com.tibco.xpd.xpdExtension.EmailResource#getInstanceName()
     * @see #getEmailResource()
     * @generated
     */
    EAttribute getEmailResource_InstanceName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ErrorMethod <em>Error Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Error Method</em>'.
     * @see com.tibco.xpd.xpdExtension.ErrorMethod
     * @generated
     */
    EClass getErrorMethod();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ErrorMethod#getErrorCode <em>Error Code</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Error Code</em>'.
     * @see com.tibco.xpd.xpdExtension.ErrorMethod#getErrorCode()
     * @see #getErrorMethod()
     * @generated
     */
    EAttribute getErrorMethod_ErrorCode();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo <em>Error Thrower Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Error Thrower Info</em>'.
     * @see com.tibco.xpd.xpdExtension.ErrorThrowerInfo
     * @generated
     */
    EClass getErrorThrowerInfo();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerId <em>Thrower Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Thrower Id</em>'.
     * @see com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerId()
     * @see #getErrorThrowerInfo()
     * @generated
     */
    EAttribute getErrorThrowerInfo_ThrowerId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerContainerId <em>Thrower Container Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Thrower Container Id</em>'.
     * @see com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerContainerId()
     * @see #getErrorThrowerInfo()
     * @generated
     */
    EAttribute getErrorThrowerInfo_ThrowerContainerId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerType <em>Thrower Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Thrower Type</em>'.
     * @see com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerType()
     * @see #getErrorThrowerInfo()
     * @generated
     */
    EAttribute getErrorThrowerInfo_ThrowerType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.EventHandlerInitialisers <em>Event Handler Initialisers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Event Handler Initialisers</em>'.
     * @see com.tibco.xpd.xpdExtension.EventHandlerInitialisers
     * @generated
     */
    EClass getEventHandlerInitialisers();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.EventHandlerInitialisers#getActivityRef <em>Activity Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Activity Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.EventHandlerInitialisers#getActivityRef()
     * @see #getEventHandlerInitialisers()
     * @generated
     */
    EReference getEventHandlerInitialisers_ActivityRef();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.FaultMessage <em>Fault Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Fault Message</em>'.
     * @see com.tibco.xpd.xpdExtension.FaultMessage
     * @generated
     */
    EClass getFaultMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.FormImplementation <em>Form Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Form Implementation</em>'.
     * @see com.tibco.xpd.xpdExtension.FormImplementation
     * @generated
     */
    EClass getFormImplementation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.FormImplementation#getFormType <em>Form Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Form Type</em>'.
     * @see com.tibco.xpd.xpdExtension.FormImplementation#getFormType()
     * @see #getFormImplementation()
     * @generated
     */
    EAttribute getFormImplementation_FormType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.FormImplementation#getFormURI <em>Form URI</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Form URI</em>'.
     * @see com.tibco.xpd.xpdExtension.FormImplementation#getFormURI()
     * @see #getFormImplementation()
     * @generated
     */
    EAttribute getFormImplementation_FormURI();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ImplementedInterface <em>Implemented Interface</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Implemented Interface</em>'.
     * @see com.tibco.xpd.xpdExtension.ImplementedInterface
     * @generated
     */
    EClass getImplementedInterface();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ImplementedInterface#getPackageRef <em>Package Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Package Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.ImplementedInterface#getPackageRef()
     * @see #getImplementedInterface()
     * @generated
     */
    EAttribute getImplementedInterface_PackageRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ImplementedInterface#getProcessInterfaceId <em>Process Interface Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Interface Id</em>'.
     * @see com.tibco.xpd.xpdExtension.ImplementedInterface#getProcessInterfaceId()
     * @see #getImplementedInterface()
     * @generated
     */
    EAttribute getImplementedInterface_ProcessInterfaceId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.InitialValues <em>Initial Values</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Initial Values</em>'.
     * @see com.tibco.xpd.xpdExtension.InitialValues
     * @generated
     */
    EClass getInitialValues();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdExtension.InitialValues#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Value</em>'.
     * @see com.tibco.xpd.xpdExtension.InitialValues#getValue()
     * @see #getInitialValues()
     * @generated
     */
    EAttribute getInitialValues_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.InitialParameterValue <em>Initial Parameter Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Initial Parameter Value</em>'.
     * @see com.tibco.xpd.xpdExtension.InitialParameterValue
     * @generated
     */
    EClass getInitialParameterValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.InitialParameterValue#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdExtension.InitialParameterValue#getName()
     * @see #getInitialParameterValue()
     * @generated
     */
    EAttribute getInitialParameterValue_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.InitialParameterValue#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdExtension.InitialParameterValue#getValue()
     * @see #getInitialParameterValue()
     * @generated
     */
    EAttribute getInitialParameterValue_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.InterfaceMethod <em>Interface Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Interface Method</em>'.
     * @see com.tibco.xpd.xpdExtension.InterfaceMethod
     * @generated
     */
    EClass getInterfaceMethod();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getTrigger <em>Trigger</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Trigger</em>'.
     * @see com.tibco.xpd.xpdExtension.InterfaceMethod#getTrigger()
     * @see #getInterfaceMethod()
     * @generated
     */
    EAttribute getInterfaceMethod_Trigger();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getTriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Message</em>'.
     * @see com.tibco.xpd.xpdExtension.InterfaceMethod#getTriggerResultMessage()
     * @see #getInterfaceMethod()
     * @generated
     */
    EReference getInterfaceMethod_TriggerResultMessage();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getVisibility <em>Visibility</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Visibility</em>'.
     * @see com.tibco.xpd.xpdExtension.InterfaceMethod#getVisibility()
     * @see #getInterfaceMethod()
     * @generated
     */
    EAttribute getInterfaceMethod_Visibility();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getErrorMethods <em>Error Methods</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Error Methods</em>'.
     * @see com.tibco.xpd.xpdExtension.InterfaceMethod#getErrorMethods()
     * @see #getInterfaceMethod()
     * @generated
     */
    EReference getInterfaceMethod_ErrorMethods();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.IntermediateMethod <em>Intermediate Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Intermediate Method</em>'.
     * @see com.tibco.xpd.xpdExtension.IntermediateMethod
     * @generated
     */
    EClass getIntermediateMethod();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.JdbcResource <em>Jdbc Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Jdbc Resource</em>'.
     * @see com.tibco.xpd.xpdExtension.JdbcResource
     * @generated
     */
    EClass getJdbcResource();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.JdbcResource#getInstanceName <em>Instance Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Instance Name</em>'.
     * @see com.tibco.xpd.xpdExtension.JdbcResource#getInstanceName()
     * @see #getJdbcResource()
     * @generated
     */
    EAttribute getJdbcResource_InstanceName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.JdbcResource#getJdbcProfileName <em>Jdbc Profile Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Jdbc Profile Name</em>'.
     * @see com.tibco.xpd.xpdExtension.JdbcResource#getJdbcProfileName()
     * @see #getJdbcResource()
     * @generated
     */
    EAttribute getJdbcResource_JdbcProfileName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.MultiInstanceScripts <em>Multi Instance Scripts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Multi Instance Scripts</em>'.
     * @see com.tibco.xpd.xpdExtension.MultiInstanceScripts
     * @generated
     */
    EClass getMultiInstanceScripts();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.MultiInstanceScripts#getAdditionalInstances <em>Additional Instances</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Additional Instances</em>'.
     * @see com.tibco.xpd.xpdExtension.MultiInstanceScripts#getAdditionalInstances()
     * @see #getMultiInstanceScripts()
     * @generated
     */
    EReference getMultiInstanceScripts_AdditionalInstances();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.NamespacePrefixMap <em>Namespace Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Namespace Prefix Map</em>'.
     * @see com.tibco.xpd.xpdExtension.NamespacePrefixMap
     * @generated
     */
    EClass getNamespacePrefixMap();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.NamespacePrefixMap#getNamespaceEntries <em>Namespace Entries</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Namespace Entries</em>'.
     * @see com.tibco.xpd.xpdExtension.NamespacePrefixMap#getNamespaceEntries()
     * @see #getNamespacePrefixMap()
     * @generated
     */
    EReference getNamespacePrefixMap_NamespaceEntries();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.NamespacePrefixMap#isPrefixMappingDisabled <em>Prefix Mapping Disabled</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Prefix Mapping Disabled</em>'.
     * @see com.tibco.xpd.xpdExtension.NamespacePrefixMap#isPrefixMappingDisabled()
     * @see #getNamespacePrefixMap()
     * @generated
     */
    EAttribute getNamespacePrefixMap_PrefixMappingDisabled();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.NamespaceMapEntry <em>Namespace Map Entry</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Namespace Map Entry</em>'.
     * @see com.tibco.xpd.xpdExtension.NamespaceMapEntry
     * @generated
     */
    EClass getNamespaceMapEntry();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.NamespaceMapEntry#getPrefix <em>Prefix</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Prefix</em>'.
     * @see com.tibco.xpd.xpdExtension.NamespaceMapEntry#getPrefix()
     * @see #getNamespaceMapEntry()
     * @generated
     */
    EAttribute getNamespaceMapEntry_Prefix();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.NamespaceMapEntry#getNamespace <em>Namespace</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Namespace</em>'.
     * @see com.tibco.xpd.xpdExtension.NamespaceMapEntry#getNamespace()
     * @see #getNamespaceMapEntry()
     * @generated
     */
    EAttribute getNamespaceMapEntry_Namespace();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource <em>Participant Shared Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Participant Shared Resource</em>'.
     * @see com.tibco.xpd.xpdExtension.ParticipantSharedResource
     * @generated
     */
    EClass getParticipantSharedResource();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getEmail <em>Email</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Email</em>'.
     * @see com.tibco.xpd.xpdExtension.ParticipantSharedResource#getEmail()
     * @see #getParticipantSharedResource()
     * @generated
     */
    EReference getParticipantSharedResource_Email();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getJdbc <em>Jdbc</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Jdbc</em>'.
     * @see com.tibco.xpd.xpdExtension.ParticipantSharedResource#getJdbc()
     * @see #getParticipantSharedResource()
     * @generated
     */
    EReference getParticipantSharedResource_Jdbc();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getWebService <em>Web Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Web Service</em>'.
     * @see com.tibco.xpd.xpdExtension.ParticipantSharedResource#getWebService()
     * @see #getParticipantSharedResource()
     * @generated
     */
    EReference getParticipantSharedResource_WebService();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ParticipantSharedResource#getRestService <em>Rest Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Rest Service</em>'.
     * @see com.tibco.xpd.xpdExtension.ParticipantSharedResource#getRestService()
     * @see #getParticipantSharedResource()
     * @generated
     */
    EReference getParticipantSharedResource_RestService();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.PilingInfo <em>Piling Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Piling Info</em>'.
     * @see com.tibco.xpd.xpdExtension.PilingInfo
     * @generated
     */
    EClass getPilingInfo();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.PilingInfo#isPilingAllowed <em>Piling Allowed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Piling Allowed</em>'.
     * @see com.tibco.xpd.xpdExtension.PilingInfo#isPilingAllowed()
     * @see #getPilingInfo()
     * @generated
     */
    EAttribute getPilingInfo_PilingAllowed();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.PilingInfo#getMaxPiliableItems <em>Max Piliable Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Max Piliable Items</em>'.
     * @see com.tibco.xpd.xpdExtension.PilingInfo#getMaxPiliableItems()
     * @see #getPilingInfo()
     * @generated
     */
    EAttribute getPilingInfo_MaxPiliableItems();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.PortTypeOperation <em>Port Type Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Port Type Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.PortTypeOperation
     * @generated
     */
    EClass getPortTypeOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getPortTypeName <em>Port Type Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Port Type Name</em>'.
     * @see com.tibco.xpd.xpdExtension.PortTypeOperation#getPortTypeName()
     * @see #getPortTypeOperation()
     * @generated
     */
    EAttribute getPortTypeOperation_PortTypeName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getOperationName <em>Operation Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Operation Name</em>'.
     * @see com.tibco.xpd.xpdExtension.PortTypeOperation#getOperationName()
     * @see #getPortTypeOperation()
     * @generated
     */
    EAttribute getPortTypeOperation_OperationName();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.PortTypeOperation#getExternalReference()
     * @see #getPortTypeOperation()
     * @generated
     */
    EReference getPortTypeOperation_ExternalReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getTransport <em>Transport</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Transport</em>'.
     * @see com.tibco.xpd.xpdExtension.PortTypeOperation#getTransport()
     * @see #getPortTypeOperation()
     * @generated
     */
    EAttribute getPortTypeOperation_Transport();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ProcessInterface <em>Process Interface</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Process Interface</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessInterface
     * @generated
     */
    EClass getProcessInterface();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ProcessInterface#getStartMethods <em>Start Methods</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Start Methods</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessInterface#getStartMethods()
     * @see #getProcessInterface()
     * @generated
     */
    EReference getProcessInterface_StartMethods();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ProcessInterface#getIntermediateMethods <em>Intermediate Methods</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Intermediate Methods</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessInterface#getIntermediateMethods()
     * @see #getProcessInterface()
     * @generated
     */
    EReference getProcessInterface_IntermediateMethods();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ProcessInterface#getXpdInterfaceType <em>Xpd Interface Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Xpd Interface Type</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessInterface#getXpdInterfaceType()
     * @see #getProcessInterface()
     * @generated
     */
    EAttribute getProcessInterface_XpdInterfaceType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ProcessInterface#getServiceProcessConfiguration <em>Service Process Configuration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Service Process Configuration</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessInterface#getServiceProcessConfiguration()
     * @see #getProcessInterface()
     * @generated
     */
    EReference getProcessInterface_ServiceProcessConfiguration();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ProcessInterfaces <em>Process Interfaces</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Process Interfaces</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessInterfaces
     * @generated
     */
    EClass getProcessInterfaces();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ProcessInterfaces#getProcessInterface <em>Process Interface</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Process Interface</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessInterfaces#getProcessInterface()
     * @see #getProcessInterfaces()
     * @generated
     */
    EReference getProcessInterfaces_ProcessInterface();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ProcessResourcePatterns <em>Process Resource Patterns</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Process Resource Patterns</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessResourcePatterns
     * @generated
     */
    EClass getProcessResourcePatterns();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ProcessResourcePatterns#getSeparationOfDutiesActivities <em>Separation Of Duties Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Separation Of Duties Activities</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessResourcePatterns#getSeparationOfDutiesActivities()
     * @see #getProcessResourcePatterns()
     * @generated
     */
    EReference getProcessResourcePatterns_SeparationOfDutiesActivities();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ProcessResourcePatterns#getRetainFamiliarActivities <em>Retain Familiar Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Retain Familiar Activities</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessResourcePatterns#getRetainFamiliarActivities()
     * @see #getProcessResourcePatterns()
     * @generated
     */
    EReference getProcessResourcePatterns_RetainFamiliarActivities();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RescheduleTimerScript <em>Reschedule Timer Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reschedule Timer Script</em>'.
     * @see com.tibco.xpd.xpdExtension.RescheduleTimerScript
     * @generated
     */
    EClass getRescheduleTimerScript();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RescheduleTimerScript#getDurationRelativeTo <em>Duration Relative To</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Duration Relative To</em>'.
     * @see com.tibco.xpd.xpdExtension.RescheduleTimerScript#getDurationRelativeTo()
     * @see #getRescheduleTimerScript()
     * @generated
     */
    EAttribute getRescheduleTimerScript_DurationRelativeTo();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RescheduleTimers <em>Reschedule Timers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reschedule Timers</em>'.
     * @see com.tibco.xpd.xpdExtension.RescheduleTimers
     * @generated
     */
    EClass getRescheduleTimers();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerSelectionType <em>Timer Selection Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timer Selection Type</em>'.
     * @see com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerSelectionType()
     * @see #getRescheduleTimers()
     * @generated
     */
    EAttribute getRescheduleTimers_TimerSelectionType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerEvents <em>Timer Events</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Timer Events</em>'.
     * @see com.tibco.xpd.xpdExtension.RescheduleTimers#getTimerEvents()
     * @see #getRescheduleTimers()
     * @generated
     */
    EReference getRescheduleTimers_TimerEvents();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RESTServices <em>REST Services</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>REST Services</em>'.
     * @see com.tibco.xpd.xpdExtension.RESTServices
     * @generated
     */
    EClass getRESTServices();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.RESTServices#getRESTServices <em>REST Services</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>REST Services</em>'.
     * @see com.tibco.xpd.xpdExtension.RESTServices#getRESTServices()
     * @see #getRESTServices()
     * @generated
     */
    EReference getRESTServices_RESTServices();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RestServiceResource <em>Rest Service Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Rest Service Resource</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceResource
     * @generated
     */
    EClass getRestServiceResource();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.RestServiceResource#getSecurityPolicy <em>Security Policy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Security Policy</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceResource#getSecurityPolicy()
     * @see #getRestServiceResource()
     * @generated
     */
    EReference getRestServiceResource_SecurityPolicy();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RestServiceResource#getResourceName <em>Resource Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource Name</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceResource#getResourceName()
     * @see #getRestServiceResource()
     * @generated
     */
    EAttribute getRestServiceResource_ResourceName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RestServiceResource#getResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource Type</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceResource#getResourceType()
     * @see #getRestServiceResource()
     * @generated
     */
    EAttribute getRestServiceResource_ResourceType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RestServiceResource#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceResource#getDescription()
     * @see #getRestServiceResource()
     * @generated
     */
    EAttribute getRestServiceResource_Description();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RestServiceResourceSecurity <em>Rest Service Resource Security</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Rest Service Resource Security</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceResourceSecurity
     * @generated
     */
    EClass getRestServiceResourceSecurity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RestServiceResourceSecurity#getPolicyType <em>Policy Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Policy Type</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceResourceSecurity#getPolicyType()
     * @see #getRestServiceResourceSecurity()
     * @generated
     */
    EAttribute getRestServiceResourceSecurity_PolicyType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RetainFamiliarActivities <em>Retain Familiar Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Retain Familiar Activities</em>'.
     * @see com.tibco.xpd.xpdExtension.RetainFamiliarActivities
     * @generated
     */
    EClass getRetainFamiliarActivities();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.RetainFamiliarActivities#getActivityRef <em>Activity Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Activity Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.RetainFamiliarActivities#getActivityRef()
     * @see #getRetainFamiliarActivities()
     * @generated
     */
    EReference getRetainFamiliarActivities_ActivityRef();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.Retry <em>Retry</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Retry</em>'.
     * @see com.tibco.xpd.xpdExtension.Retry
     * @generated
     */
    EClass getRetry();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.Retry#getMax <em>Max</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Max</em>'.
     * @see com.tibco.xpd.xpdExtension.Retry#getMax()
     * @see #getRetry()
     * @generated
     */
    EAttribute getRetry_Max();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.Retry#getInitialPeriod <em>Initial Period</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Initial Period</em>'.
     * @see com.tibco.xpd.xpdExtension.Retry#getInitialPeriod()
     * @see #getRetry()
     * @generated
     */
    EAttribute getRetry_InitialPeriod();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.Retry#getPeriodIncrement <em>Period Increment</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Period Increment</em>'.
     * @see com.tibco.xpd.xpdExtension.Retry#getPeriodIncrement()
     * @see #getRetry()
     * @generated
     */
    EAttribute getRetry_PeriodIncrement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.Retry#getMaxRetryAction <em>Max Retry Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Max Retry Action</em>'.
     * @see com.tibco.xpd.xpdExtension.Retry#getMaxRetryAction()
     * @see #getRetry()
     * @generated
     */
    EAttribute getRetry_MaxRetryAction();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ScriptInformation <em>Script Information</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Script Information</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptInformation
     * @generated
     */
    EClass getScriptInformation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.ScriptInformation#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Expression</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptInformation#getExpression()
     * @see #getScriptInformation()
     * @generated
     */
    EReference getScriptInformation_Expression();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ScriptInformation#getDirection <em>Direction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Direction</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptInformation#getDirection()
     * @see #getScriptInformation()
     * @generated
     */
    EAttribute getScriptInformation_Direction();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.xpdExtension.ScriptInformation#getActivity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Activity</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptInformation#getActivity()
     * @see #getScriptInformation()
     * @generated
     */
    EReference getScriptInformation_Activity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ScriptInformation#isReference <em>Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptInformation#isReference()
     * @see #getScriptInformation()
     * @generated
     */
    EAttribute getScriptInformation_Reference();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities <em>Separation Of Duties Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Separation Of Duties Activities</em>'.
     * @see com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities
     * @generated
     */
    EClass getSeparationOfDutiesActivities();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities#getActivityRef <em>Activity Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Activity Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities#getActivityRef()
     * @see #getSeparationOfDutiesActivities()
     * @generated
     */
    EReference getSeparationOfDutiesActivities_ActivityRef();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.SignalData <em>Signal Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Signal Data</em>'.
     * @see com.tibco.xpd.xpdExtension.SignalData
     * @generated
     */
    EClass getSignalData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.SignalData#getCorrelationMappings <em>Correlation Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Correlation Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.SignalData#getCorrelationMappings()
     * @see #getSignalData()
     * @generated
     */
    EReference getSignalData_CorrelationMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.SignalData#getDataMappings <em>Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.SignalData#getDataMappings()
     * @see #getSignalData()
     * @generated
     */
    EReference getSignalData_DataMappings();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.SignalData#getRescheduleTimers <em>Reschedule Timers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reschedule Timers</em>'.
     * @see com.tibco.xpd.xpdExtension.SignalData#getRescheduleTimers()
     * @see #getSignalData()
     * @generated
     */
    EReference getSignalData_RescheduleTimers();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.SignalData#getInputScriptDataMapper <em>Input Script Data Mapper</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Input Script Data Mapper</em>'.
     * @see com.tibco.xpd.xpdExtension.SignalData#getInputScriptDataMapper()
     * @see #getSignalData()
     * @generated
     */
    EReference getSignalData_InputScriptDataMapper();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.SignalData#getOutputScriptDataMapper <em>Output Script Data Mapper</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Output Script Data Mapper</em>'.
     * @see com.tibco.xpd.xpdExtension.SignalData#getOutputScriptDataMapper()
     * @see #getSignalData()
     * @generated
     */
    EReference getSignalData_OutputScriptDataMapper();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.StartMethod <em>Start Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Start Method</em>'.
     * @see com.tibco.xpd.xpdExtension.StartMethod
     * @generated
     */
    EClass getStartMethod();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.StructuredDiscriminator <em>Structured Discriminator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Structured Discriminator</em>'.
     * @see com.tibco.xpd.xpdExtension.StructuredDiscriminator
     * @generated
     */
    EClass getStructuredDiscriminator();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.StructuredDiscriminator#getWaitForIncomingPath <em>Wait For Incoming Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Wait For Incoming Path</em>'.
     * @see com.tibco.xpd.xpdExtension.StructuredDiscriminator#getWaitForIncomingPath()
     * @see #getStructuredDiscriminator()
     * @generated
     */
    EAttribute getStructuredDiscriminator_WaitForIncomingPath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.StructuredDiscriminator#getUpStreamParallelSplit <em>Up Stream Parallel Split</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Up Stream Parallel Split</em>'.
     * @see com.tibco.xpd.xpdExtension.StructuredDiscriminator#getUpStreamParallelSplit()
     * @see #getStructuredDiscriminator()
     * @generated
     */
    EAttribute getStructuredDiscriminator_UpStreamParallelSplit();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.TaskLibraryReference <em>Task Library Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task Library Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.TaskLibraryReference
     * @generated
     */
    EClass getTaskLibraryReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.TaskLibraryReference#getTaskLibraryId <em>Task Library Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Task Library Id</em>'.
     * @see com.tibco.xpd.xpdExtension.TaskLibraryReference#getTaskLibraryId()
     * @see #getTaskLibraryReference()
     * @generated
     */
    EAttribute getTaskLibraryReference_TaskLibraryId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.TaskLibraryReference#getPackageRef <em>Package Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Package Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.TaskLibraryReference#getPackageRef()
     * @see #getTaskLibraryReference()
     * @generated
     */
    EAttribute getTaskLibraryReference_PackageRef();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.TransformScript <em>Transform Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Transform Script</em>'.
     * @see com.tibco.xpd.xpdExtension.TransformScript
     * @generated
     */
    EClass getTransformScript();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.TransformScript#getDataMappings <em>Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.TransformScript#getDataMappings()
     * @see #getTransformScript()
     * @generated
     */
    EReference getTransformScript_DataMappings();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdExtension.TransformScript#getInputDom <em>Input Dom</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Input Dom</em>'.
     * @see com.tibco.xpd.xpdExtension.TransformScript#getInputDom()
     * @see #getTransformScript()
     * @generated
     */
    EAttribute getTransformScript_InputDom();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdExtension.TransformScript#getOutputDom <em>Output Dom</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Output Dom</em>'.
     * @see com.tibco.xpd.xpdExtension.TransformScript#getOutputDom()
     * @see #getTransformScript()
     * @generated
     */
    EAttribute getTransformScript_OutputDom();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.UserTaskScripts <em>User Task Scripts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>User Task Scripts</em>'.
     * @see com.tibco.xpd.xpdExtension.UserTaskScripts
     * @generated
     */
    EClass getUserTaskScripts();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getOpenScript <em>Open Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Open Script</em>'.
     * @see com.tibco.xpd.xpdExtension.UserTaskScripts#getOpenScript()
     * @see #getUserTaskScripts()
     * @generated
     */
    EReference getUserTaskScripts_OpenScript();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getCloseScript <em>Close Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Close Script</em>'.
     * @see com.tibco.xpd.xpdExtension.UserTaskScripts#getCloseScript()
     * @see #getUserTaskScripts()
     * @generated
     */
    EReference getUserTaskScripts_CloseScript();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getSubmitScript <em>Submit Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Submit Script</em>'.
     * @see com.tibco.xpd.xpdExtension.UserTaskScripts#getSubmitScript()
     * @see #getUserTaskScripts()
     * @generated
     */
    EReference getUserTaskScripts_SubmitScript();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getScheduleScript <em>Schedule Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schedule Script</em>'.
     * @see com.tibco.xpd.xpdExtension.UserTaskScripts#getScheduleScript()
     * @see #getUserTaskScripts()
     * @generated
     */
    EReference getUserTaskScripts_ScheduleScript();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.UserTaskScripts#getRescheduleScript <em>Reschedule Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reschedule Script</em>'.
     * @see com.tibco.xpd.xpdExtension.UserTaskScripts#getRescheduleScript()
     * @see #getUserTaskScripts()
     * @generated
     */
    EReference getUserTaskScripts_RescheduleScript();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ValidationControl <em>Validation Control</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Validation Control</em>'.
     * @see com.tibco.xpd.xpdExtension.ValidationControl
     * @generated
     */
    EClass getValidationControl();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ValidationControl#getValidationIssueOverrides <em>Validation Issue Overrides</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Validation Issue Overrides</em>'.
     * @see com.tibco.xpd.xpdExtension.ValidationControl#getValidationIssueOverrides()
     * @see #getValidationControl()
     * @generated
     */
    EReference getValidationControl_ValidationIssueOverrides();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride <em>Validation Issue Override</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Validation Issue Override</em>'.
     * @see com.tibco.xpd.xpdExtension.ValidationIssueOverride
     * @generated
     */
    EClass getValidationIssueOverride();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride#getValidationIssueId <em>Validation Issue Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Validation Issue Id</em>'.
     * @see com.tibco.xpd.xpdExtension.ValidationIssueOverride#getValidationIssueId()
     * @see #getValidationIssueOverride()
     * @generated
     */
    EAttribute getValidationIssueOverride_ValidationIssueId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride#getOverrideType <em>Override Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Override Type</em>'.
     * @see com.tibco.xpd.xpdExtension.ValidationIssueOverride#getOverrideType()
     * @see #getValidationIssueOverride()
     * @generated
     */
    EAttribute getValidationIssueOverride_OverrideType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsdlEventAssociation <em>Wsdl Event Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Wsdl Event Association</em>'.
     * @see com.tibco.xpd.xpdExtension.WsdlEventAssociation
     * @generated
     */
    EClass getWsdlEventAssociation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsdlEventAssociation#getEventId <em>Event Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Event Id</em>'.
     * @see com.tibco.xpd.xpdExtension.WsdlEventAssociation#getEventId()
     * @see #getWsdlEventAssociation()
     * @generated
     */
    EAttribute getWsdlEventAssociation_EventId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WorkItemPriority <em>Work Item Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Priority</em>'.
     * @see com.tibco.xpd.xpdExtension.WorkItemPriority
     * @generated
     */
    EClass getWorkItemPriority();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WorkItemPriority#getInitialPriority <em>Initial Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Initial Priority</em>'.
     * @see com.tibco.xpd.xpdExtension.WorkItemPriority#getInitialPriority()
     * @see #getWorkItemPriority()
     * @generated
     */
    EAttribute getWorkItemPriority_InitialPriority();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsdlGeneration <em>Wsdl Generation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Wsdl Generation</em>'.
     * @see com.tibco.xpd.xpdExtension.WsdlGeneration
     * @generated
     */
    EClass getWsdlGeneration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsdlGeneration#getSoapBindingStyle <em>Soap Binding Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Soap Binding Style</em>'.
     * @see com.tibco.xpd.xpdExtension.WsdlGeneration#getSoapBindingStyle()
     * @see #getWsdlGeneration()
     * @generated
     */
    EAttribute getWsdlGeneration_SoapBindingStyle();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsBinding <em>Ws Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsBinding
     * @generated
     */
    EClass getWsBinding();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsBinding#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdExtension.WsBinding#getName()
     * @see #getWsBinding()
     * @generated
     */
    EAttribute getWsBinding_Name();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.xpdExtension.WsBinding#getExtendedProperties <em>Extended Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Extended Properties</em>'.
     * @see com.tibco.xpd.xpdExtension.WsBinding#getExtendedProperties()
     * @see #getWsBinding()
     * @generated
     */
    EReference getWsBinding_ExtendedProperties();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsInbound <em>Ws Inbound</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Inbound</em>'.
     * @see com.tibco.xpd.xpdExtension.WsInbound
     * @generated
     */
    EClass getWsInbound();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsInbound#getVirtualBinding <em>Virtual Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Virtual Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsInbound#getVirtualBinding()
     * @see #getWsInbound()
     * @generated
     */
    EReference getWsInbound_VirtualBinding();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.WsInbound#getSoapHttpBinding <em>Soap Http Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Soap Http Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsInbound#getSoapHttpBinding()
     * @see #getWsInbound()
     * @generated
     */
    EReference getWsInbound_SoapHttpBinding();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.WsInbound#getSoapJmsBinding <em>Soap Jms Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Soap Jms Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsInbound#getSoapJmsBinding()
     * @see #getWsInbound()
     * @generated
     */
    EReference getWsInbound_SoapJmsBinding();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsOutbound <em>Ws Outbound</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Outbound</em>'.
     * @see com.tibco.xpd.xpdExtension.WsOutbound
     * @generated
     */
    EClass getWsOutbound();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsOutbound#getVirtualBinding <em>Virtual Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Virtual Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsOutbound#getVirtualBinding()
     * @see #getWsOutbound()
     * @generated
     */
    EReference getWsOutbound_VirtualBinding();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsOutbound#getSoapHttpBinding <em>Soap Http Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Soap Http Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsOutbound#getSoapHttpBinding()
     * @see #getWsOutbound()
     * @generated
     */
    EReference getWsOutbound_SoapHttpBinding();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsOutbound#getSoapJmsBinding <em>Soap Jms Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Soap Jms Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsOutbound#getSoapJmsBinding()
     * @see #getWsOutbound()
     * @generated
     */
    EReference getWsOutbound_SoapJmsBinding();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsResource <em>Ws Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Resource</em>'.
     * @see com.tibco.xpd.xpdExtension.WsResource
     * @generated
     */
    EClass getWsResource();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsResource#getInbound <em>Inbound</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Inbound</em>'.
     * @see com.tibco.xpd.xpdExtension.WsResource#getInbound()
     * @see #getWsResource()
     * @generated
     */
    EReference getWsResource_Inbound();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsResource#getOutbound <em>Outbound</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Outbound</em>'.
     * @see com.tibco.xpd.xpdExtension.WsResource#getOutbound()
     * @see #getWsResource()
     * @generated
     */
    EReference getWsResource_Outbound();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy <em>Ws Security Policy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Security Policy</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSecurityPolicy
     * @generated
     */
    EClass getWsSecurityPolicy();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy#getGovernanceApplicationName <em>Governance Application Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Governance Application Name</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSecurityPolicy#getGovernanceApplicationName()
     * @see #getWsSecurityPolicy()
     * @generated
     */
    EAttribute getWsSecurityPolicy_GovernanceApplicationName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSecurityPolicy#getType()
     * @see #getWsSecurityPolicy()
     * @generated
     */
    EAttribute getWsSecurityPolicy_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsSoapBinding <em>Ws Soap Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Soap Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapBinding
     * @generated
     */
    EClass getWsSoapBinding();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getBindingStyle <em>Binding Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Binding Style</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapBinding#getBindingStyle()
     * @see #getWsSoapBinding()
     * @generated
     */
    EAttribute getWsSoapBinding_BindingStyle();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapVersion <em>Soap Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Soap Version</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapVersion()
     * @see #getWsSoapBinding()
     * @generated
     */
    EAttribute getWsSoapBinding_SoapVersion();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapSecurity <em>Soap Security</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Soap Security</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapBinding#getSoapSecurity()
     * @see #getWsSoapBinding()
     * @generated
     */
    EReference getWsSoapBinding_SoapSecurity();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding <em>Ws Soap Http Inbound Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Soap Http Inbound Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding
     * @generated
     */
    EClass getWsSoapHttpInboundBinding();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getInboundSecurity <em>Inbound Security</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Inbound Security</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getInboundSecurity()
     * @see #getWsSoapHttpInboundBinding()
     * @generated
     */
    EReference getWsSoapHttpInboundBinding_InboundSecurity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getEndpointUrlPath <em>Endpoint Url Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Endpoint Url Path</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getEndpointUrlPath()
     * @see #getWsSoapHttpInboundBinding()
     * @generated
     */
    EAttribute getWsSoapHttpInboundBinding_EndpointUrlPath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getHttpConnectorInstanceName <em>Http Connector Instance Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Http Connector Instance Name</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getHttpConnectorInstanceName()
     * @see #getWsSoapHttpInboundBinding()
     * @generated
     */
    EAttribute getWsSoapHttpInboundBinding_HttpConnectorInstanceName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding <em>Ws Soap Http Outbound Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Soap Http Outbound Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding
     * @generated
     */
    EClass getWsSoapHttpOutboundBinding();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding#getOutboundSecurity <em>Outbound Security</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Outbound Security</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding#getOutboundSecurity()
     * @see #getWsSoapHttpOutboundBinding()
     * @generated
     */
    EReference getWsSoapHttpOutboundBinding_OutboundSecurity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding#getHttpClientInstanceName <em>Http Client Instance Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Http Client Instance Name</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding#getHttpClientInstanceName()
     * @see #getWsSoapHttpOutboundBinding()
     * @generated
     */
    EAttribute getWsSoapHttpOutboundBinding_HttpClientInstanceName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding <em>Ws Soap Jms Inbound Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Soap Jms Inbound Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding
     * @generated
     */
    EClass getWsSoapJmsInboundBinding();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getOutboundConnectionFactory <em>Outbound Connection Factory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Outbound Connection Factory</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getOutboundConnectionFactory()
     * @see #getWsSoapJmsInboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsInboundBinding_OutboundConnectionFactory();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundConnectionFactoryConfiguration <em>Inbound Connection Factory Configuration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Inbound Connection Factory Configuration</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundConnectionFactoryConfiguration()
     * @see #getWsSoapJmsInboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsInboundBinding_InboundConnectionFactoryConfiguration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundDestination <em>Inbound Destination</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Inbound Destination</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundDestination()
     * @see #getWsSoapJmsInboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsInboundBinding_InboundDestination();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundSecurity <em>Inbound Security</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Inbound Security</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundSecurity()
     * @see #getWsSoapJmsInboundBinding()
     * @generated
     */
    EReference getWsSoapJmsInboundBinding_InboundSecurity();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding <em>Ws Soap Jms Outbound Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Soap Jms Outbound Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding
     * @generated
     */
    EClass getWsSoapJmsOutboundBinding();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundConnectionFactory <em>Outbound Connection Factory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Outbound Connection Factory</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundConnectionFactory()
     * @see #getWsSoapJmsOutboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsOutboundBinding_OutboundConnectionFactory();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInboundDestination <em>Inbound Destination</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Inbound Destination</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInboundDestination()
     * @see #getWsSoapJmsOutboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsOutboundBinding_InboundDestination();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundDestination <em>Outbound Destination</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Outbound Destination</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundDestination()
     * @see #getWsSoapJmsOutboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsOutboundBinding_OutboundDestination();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundSecurity <em>Outbound Security</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Outbound Security</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundSecurity()
     * @see #getWsSoapJmsOutboundBinding()
     * @generated
     */
    EReference getWsSoapJmsOutboundBinding_OutboundSecurity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getDeliveryMode <em>Delivery Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Delivery Mode</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getDeliveryMode()
     * @see #getWsSoapJmsOutboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsOutboundBinding_DeliveryMode();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getPriority <em>Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Priority</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getPriority()
     * @see #getWsSoapJmsOutboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsOutboundBinding_Priority();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getMessageExpiration <em>Message Expiration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Message Expiration</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getMessageExpiration()
     * @see #getWsSoapJmsOutboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsOutboundBinding_MessageExpiration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInvocationTimeout <em>Invocation Timeout</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Invocation Timeout</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInvocationTimeout()
     * @see #getWsSoapJmsOutboundBinding()
     * @generated
     */
    EAttribute getWsSoapJmsOutboundBinding_InvocationTimeout();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsSoapSecurity <em>Ws Soap Security</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Soap Security</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapSecurity
     * @generated
     */
    EClass getWsSoapSecurity();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.WsSoapSecurity#getSecurityPolicy <em>Security Policy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Security Policy</em>'.
     * @see com.tibco.xpd.xpdExtension.WsSoapSecurity#getSecurityPolicy()
     * @see #getWsSoapSecurity()
     * @generated
     */
    EReference getWsSoapSecurity_SecurityPolicy();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.WsVirtualBinding <em>Ws Virtual Binding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ws Virtual Binding</em>'.
     * @see com.tibco.xpd.xpdExtension.WsVirtualBinding
     * @generated
     */
    EClass getWsVirtualBinding();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes <em>Xpd Ext Data Object Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xpd Ext Data Object Attributes</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes
     * @generated
     */
    EClass getXpdExtDataObjectAttributes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getDescription()
     * @see #getXpdExtDataObjectAttributes()
     * @generated
     */
    EAttribute getXpdExtDataObjectAttributes_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getExternalReference()
     * @see #getXpdExtDataObjectAttributes()
     * @generated
     */
    EAttribute getXpdExtDataObjectAttributes_ExternalReference();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getProperties <em>Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Properties</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes#getProperties()
     * @see #getXpdExtDataObjectAttributes()
     * @generated
     */
    EReference getXpdExtDataObjectAttributes_Properties();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.XpdExtProperty <em>Xpd Ext Property</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xpd Ext Property</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtProperty
     * @generated
     */
    EClass getXpdExtProperty();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.XpdExtProperty#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtProperty#getName()
     * @see #getXpdExtProperty()
     * @generated
     */
    EAttribute getXpdExtProperty_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.XpdExtProperty#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtProperty#getValue()
     * @see #getXpdExtProperty()
     * @generated
     */
    EAttribute getXpdExtProperty_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.XpdExtAttribute <em>Xpd Ext Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xpd Ext Attribute</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtAttribute
     * @generated
     */
    EClass getXpdExtAttribute();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdExtension.XpdExtAttribute#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtAttribute#getMixed()
     * @see #getXpdExtAttribute()
     * @generated
     */
    EAttribute getXpdExtAttribute_Mixed();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdExtension.XpdExtAttribute#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtAttribute#getGroup()
     * @see #getXpdExtAttribute()
     * @generated
     */
    EAttribute getXpdExtAttribute_Group();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdExtension.XpdExtAttribute#getAny <em>Any</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtAttribute#getAny()
     * @see #getXpdExtAttribute()
     * @generated
     */
    EAttribute getXpdExtAttribute_Any();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.XpdExtAttribute#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtAttribute#getName()
     * @see #getXpdExtAttribute()
     * @generated
     */
    EAttribute getXpdExtAttribute_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.XpdExtAttribute#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtAttribute#getValue()
     * @see #getXpdExtAttribute()
     * @generated
     */
    EAttribute getXpdExtAttribute_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.XpdExtAttributes <em>Xpd Ext Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xpd Ext Attributes</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtAttributes
     * @generated
     */
    EClass getXpdExtAttributes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.XpdExtAttributes#getAttributes <em>Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attributes</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdExtAttributes#getAttributes()
     * @see #getXpdExtAttributes()
     * @generated
     */
    EReference getXpdExtAttributes_Attributes();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.UpdateCaseOperationType <em>Update Case Operation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Update Case Operation Type</em>'.
     * @see com.tibco.xpd.xpdExtension.UpdateCaseOperationType
     * @generated
     */
    EClass getUpdateCaseOperationType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.UpdateCaseOperationType#getFromFieldPath <em>From Field Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>From Field Path</em>'.
     * @see com.tibco.xpd.xpdExtension.UpdateCaseOperationType#getFromFieldPath()
     * @see #getUpdateCaseOperationType()
     * @generated
     */
    EAttribute getUpdateCaseOperationType_FromFieldPath();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AddLinkAssociationsType <em>Add Link Associations Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Add Link Associations Type</em>'.
     * @see com.tibco.xpd.xpdExtension.AddLinkAssociationsType
     * @generated
     */
    EClass getAddLinkAssociationsType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AddLinkAssociationsType#getAddCaseRefField <em>Add Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Add Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.AddLinkAssociationsType#getAddCaseRefField()
     * @see #getAddLinkAssociationsType()
     * @generated
     */
    EAttribute getAddLinkAssociationsType_AddCaseRefField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AddLinkAssociationsType#getAssociationName <em>Association Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Association Name</em>'.
     * @see com.tibco.xpd.xpdExtension.AddLinkAssociationsType#getAssociationName()
     * @see #getAddLinkAssociationsType()
     * @generated
     */
    EAttribute getAddLinkAssociationsType_AssociationName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType <em>Remove Link Associations Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Remove Link Associations Type</em>'.
     * @see com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType
     * @generated
     */
    EClass getRemoveLinkAssociationsType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType#getAssociationName <em>Association Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Association Name</em>'.
     * @see com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType#getAssociationName()
     * @see #getRemoveLinkAssociationsType()
     * @generated
     */
    EAttribute getRemoveLinkAssociationsType_AssociationName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType#getRemoveCaseRefField <em>Remove Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Remove Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType#getRemoveCaseRefField()
     * @see #getRemoveLinkAssociationsType()
     * @generated
     */
    EAttribute getRemoveLinkAssociationsType_RemoveCaseRefField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType <em>Case Reference Operations Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Case Reference Operations Type</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseReferenceOperationsType
     * @generated
     */
    EClass getCaseReferenceOperationsType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getCaseRefField <em>Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getCaseRefField()
     * @see #getCaseReferenceOperationsType()
     * @generated
     */
    EAttribute getCaseReferenceOperationsType_CaseRefField();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getUpdate <em>Update</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Update</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getUpdate()
     * @see #getCaseReferenceOperationsType()
     * @generated
     */
    EReference getCaseReferenceOperationsType_Update();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getDelete <em>Delete</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getDelete()
     * @see #getCaseReferenceOperationsType()
     * @generated
     */
    EReference getCaseReferenceOperationsType_Delete();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getAddLinkAssociations <em>Add Link Associations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Add Link Associations</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getAddLinkAssociations()
     * @see #getCaseReferenceOperationsType()
     * @generated
     */
    EReference getCaseReferenceOperationsType_AddLinkAssociations();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getRemoveLinkAssociations <em>Remove Link Associations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Remove Link Associations</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseReferenceOperationsType#getRemoveLinkAssociations()
     * @see #getCaseReferenceOperationsType()
     * @generated
     */
    EReference getCaseReferenceOperationsType_RemoveLinkAssociations();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.GlobalDataOperation <em>Global Data Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Global Data Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.GlobalDataOperation
     * @generated
     */
    EClass getGlobalDataOperation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.GlobalDataOperation#getCaseAccessOperations <em>Case Access Operations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Case Access Operations</em>'.
     * @see com.tibco.xpd.xpdExtension.GlobalDataOperation#getCaseAccessOperations()
     * @see #getGlobalDataOperation()
     * @generated
     */
    EReference getGlobalDataOperation_CaseAccessOperations();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.GlobalDataOperation#getCaseReferenceOperations <em>Case Reference Operations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Case Reference Operations</em>'.
     * @see com.tibco.xpd.xpdExtension.GlobalDataOperation#getCaseReferenceOperations()
     * @see #getGlobalDataOperation()
     * @generated
     */
    EReference getGlobalDataOperation_CaseReferenceOperations();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType <em>Delete By Case Identifier Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete By Case Identifier Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType
     * @generated
     */
    EClass getDeleteByCaseIdentifierType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType#getFieldPath <em>Field Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Field Path</em>'.
     * @see com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType#getFieldPath()
     * @see #getDeleteByCaseIdentifierType()
     * @generated
     */
    EAttribute getDeleteByCaseIdentifierType_FieldPath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType#getIdentifierName <em>Identifier Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Identifier Name</em>'.
     * @see com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType#getIdentifierName()
     * @see #getDeleteByCaseIdentifierType()
     * @generated
     */
    EAttribute getDeleteByCaseIdentifierType_IdentifierName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CompositeIdentifierType <em>Composite Identifier Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Composite Identifier Type</em>'.
     * @see com.tibco.xpd.xpdExtension.CompositeIdentifierType
     * @generated
     */
    EClass getCompositeIdentifierType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CompositeIdentifierType#getFieldPath <em>Field Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Field Path</em>'.
     * @see com.tibco.xpd.xpdExtension.CompositeIdentifierType#getFieldPath()
     * @see #getCompositeIdentifierType()
     * @generated
     */
    EAttribute getCompositeIdentifierType_FieldPath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CompositeIdentifierType#getIdentifiername <em>Identifiername</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Identifiername</em>'.
     * @see com.tibco.xpd.xpdExtension.CompositeIdentifierType#getIdentifiername()
     * @see #getCompositeIdentifierType()
     * @generated
     */
    EAttribute getCompositeIdentifierType_Identifiername();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DeleteCaseReferenceOperationType <em>Delete Case Reference Operation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Case Reference Operation Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DeleteCaseReferenceOperationType
     * @generated
     */
    EClass getDeleteCaseReferenceOperationType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType <em>Delete By Composite Identifiers Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete By Composite Identifiers Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType
     * @generated
     */
    EClass getDeleteByCompositeIdentifiersType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType#getGroup()
     * @see #getDeleteByCompositeIdentifiersType()
     * @generated
     */
    EAttribute getDeleteByCompositeIdentifiersType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType#getCompositeIdentifier <em>Composite Identifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Composite Identifier</em>'.
     * @see com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType#getCompositeIdentifier()
     * @see #getDeleteByCompositeIdentifiersType()
     * @generated
     */
    EReference getDeleteByCompositeIdentifiersType_CompositeIdentifier();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CreateCaseOperationType <em>Create Case Operation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create Case Operation Type</em>'.
     * @see com.tibco.xpd.xpdExtension.CreateCaseOperationType
     * @generated
     */
    EClass getCreateCaseOperationType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CreateCaseOperationType#getFromFieldPath <em>From Field Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>From Field Path</em>'.
     * @see com.tibco.xpd.xpdExtension.CreateCaseOperationType#getFromFieldPath()
     * @see #getCreateCaseOperationType()
     * @generated
     */
    EAttribute getCreateCaseOperationType_FromFieldPath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CreateCaseOperationType#getToCaseRefField <em>To Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>To Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.CreateCaseOperationType#getToCaseRefField()
     * @see #getCreateCaseOperationType()
     * @generated
     */
    EAttribute getCreateCaseOperationType_ToCaseRefField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType <em>Case Access Operations Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Case Access Operations Type</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseAccessOperationsType
     * @generated
     */
    EClass getCaseAccessOperationsType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getCaseClassExternalReference <em>Case Class External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Case Class External Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getCaseClassExternalReference()
     * @see #getCaseAccessOperationsType()
     * @generated
     */
    EReference getCaseAccessOperationsType_CaseClassExternalReference();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getCreate <em>Create</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Create</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getCreate()
     * @see #getCaseAccessOperationsType()
     * @generated
     */
    EReference getCaseAccessOperationsType_Create();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getDeleteByCaseIdentifier <em>Delete By Case Identifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete By Case Identifier</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getDeleteByCaseIdentifier()
     * @see #getCaseAccessOperationsType()
     * @generated
     */
    EReference getCaseAccessOperationsType_DeleteByCaseIdentifier();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getDeleteByCompositeIdentifiers <em>Delete By Composite Identifiers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete By Composite Identifiers</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseAccessOperationsType#getDeleteByCompositeIdentifiers()
     * @see #getCaseAccessOperationsType()
     * @generated
     */
    EReference getCaseAccessOperationsType_DeleteByCompositeIdentifiers();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping <em>Data Work Item Attribute Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Work Item Attribute Mapping</em>'.
     * @see com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping
     * @generated
     */
    EClass getDataWorkItemAttributeMapping();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping#getAttribute <em>Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute</em>'.
     * @see com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping#getAttribute()
     * @see #getDataWorkItemAttributeMapping()
     * @generated
     */
    EAttribute getDataWorkItemAttributeMapping_Attribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping#getProcessData <em>Process Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Data</em>'.
     * @see com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping#getProcessData()
     * @see #getDataWorkItemAttributeMapping()
     * @generated
     */
    EAttribute getDataWorkItemAttributeMapping_ProcessData();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings <em>Process Data Work Item Attribute Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Process Data Work Item Attribute Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings
     * @generated
     */
    EClass getProcessDataWorkItemAttributeMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings#getDataWorkItemAttributeMapping <em>Data Work Item Attribute Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Work Item Attribute Mapping</em>'.
     * @see com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings#getDataWorkItemAttributeMapping()
     * @see #getProcessDataWorkItemAttributeMappings()
     * @generated
     */
    EReference getProcessDataWorkItemAttributeMappings_DataWorkItemAttributeMapping();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration <em>Bpm Runtime Configuration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Bpm Runtime Configuration</em>'.
     * @see com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration
     * @generated
     */
    EClass getBpmRuntimeConfiguration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration#getIncomingRequestTimeout <em>Incoming Request Timeout</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Incoming Request Timeout</em>'.
     * @see com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration#getIncomingRequestTimeout()
     * @see #getBpmRuntimeConfiguration()
     * @generated
     */
    EAttribute getBpmRuntimeConfiguration_IncomingRequestTimeout();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.EnablementType <em>Enablement Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enablement Type</em>'.
     * @see com.tibco.xpd.xpdExtension.EnablementType
     * @generated
     */
    EClass getEnablementType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.EnablementType#getInitializerActivities <em>Initializer Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Initializer Activities</em>'.
     * @see com.tibco.xpd.xpdExtension.EnablementType#getInitializerActivities()
     * @see #getEnablementType()
     * @generated
     */
    EReference getEnablementType_InitializerActivities();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.EnablementType#getPreconditionExpression <em>Precondition Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Precondition Expression</em>'.
     * @see com.tibco.xpd.xpdExtension.EnablementType#getPreconditionExpression()
     * @see #getEnablementType()
     * @generated
     */
    EReference getEnablementType_PreconditionExpression();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.InitializerActivitiesType <em>Initializer Activities Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Initializer Activities Type</em>'.
     * @see com.tibco.xpd.xpdExtension.InitializerActivitiesType
     * @generated
     */
    EClass getInitializerActivitiesType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.InitializerActivitiesType#getActivityRef <em>Activity Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Activity Ref</em>'.
     * @see com.tibco.xpd.xpdExtension.InitializerActivitiesType#getActivityRef()
     * @see #getInitializerActivitiesType()
     * @generated
     */
    EReference getInitializerActivitiesType_ActivityRef();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType <em>Ad Hoc Task Configuration Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ad Hoc Task Configuration Type</em>'.
     * @see com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType
     * @generated
     */
    EClass getAdHocTaskConfigurationType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getEnablement <em>Enablement</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Enablement</em>'.
     * @see com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getEnablement()
     * @see #getAdHocTaskConfigurationType()
     * @generated
     */
    EReference getAdHocTaskConfigurationType_Enablement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getAdHocExecutionType <em>Ad Hoc Execution Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ad Hoc Execution Type</em>'.
     * @see com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getAdHocExecutionType()
     * @see #getAdHocTaskConfigurationType()
     * @generated
     */
    EAttribute getAdHocTaskConfigurationType_AdHocExecutionType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isSuspendMainFlow <em>Suspend Main Flow</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Suspend Main Flow</em>'.
     * @see com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isSuspendMainFlow()
     * @see #getAdHocTaskConfigurationType()
     * @generated
     */
    EAttribute getAdHocTaskConfigurationType_SuspendMainFlow();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isAllowMultipleInvocations <em>Allow Multiple Invocations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Allow Multiple Invocations</em>'.
     * @see com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isAllowMultipleInvocations()
     * @see #getAdHocTaskConfigurationType()
     * @generated
     */
    EAttribute getAdHocTaskConfigurationType_AllowMultipleInvocations();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getRequiredAccessPrivileges <em>Required Access Privileges</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Required Access Privileges</em>'.
     * @see com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getRequiredAccessPrivileges()
     * @see #getAdHocTaskConfigurationType()
     * @generated
     */
    EReference getAdHocTaskConfigurationType_RequiredAccessPrivileges();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RequiredAccessPrivileges <em>Required Access Privileges</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Required Access Privileges</em>'.
     * @see com.tibco.xpd.xpdExtension.RequiredAccessPrivileges
     * @generated
     */
    EClass getRequiredAccessPrivileges();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.RequiredAccessPrivileges#getPrivilegeReference <em>Privilege Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege Reference</em>'.
     * @see com.tibco.xpd.xpdExtension.RequiredAccessPrivileges#getPrivilegeReference()
     * @see #getRequiredAccessPrivileges()
     * @generated
     */
    EReference getRequiredAccessPrivileges_PrivilegeReference();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.VisibleForCaseStates <em>Visible For Case States</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Visible For Case States</em>'.
     * @see com.tibco.xpd.xpdExtension.VisibleForCaseStates
     * @generated
     */
    EClass getVisibleForCaseStates();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.VisibleForCaseStates#isVisibleForUnsetCaseState <em>Visible For Unset Case State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Visible For Unset Case State</em>'.
     * @see com.tibco.xpd.xpdExtension.VisibleForCaseStates#isVisibleForUnsetCaseState()
     * @see #getVisibleForCaseStates()
     * @generated
     */
    EAttribute getVisibleForCaseStates_VisibleForUnsetCaseState();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.VisibleForCaseStates#getCaseState <em>Case State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Case State</em>'.
     * @see com.tibco.xpd.xpdExtension.VisibleForCaseStates#getCaseState()
     * @see #getVisibleForCaseStates()
     * @generated
     */
    EReference getVisibleForCaseStates_CaseState();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CaseService <em>Case Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Case Service</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseService
     * @generated
     */
    EClass getCaseService();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseService#getCaseClassType <em>Case Class Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Case Class Type</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseService#getCaseClassType()
     * @see #getCaseService()
     * @generated
     */
    EReference getCaseService_CaseClassType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseService#getVisibleForCaseStates <em>Visible For Case States</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Visible For Case States</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseService#getVisibleForCaseStates()
     * @see #getCaseService()
     * @generated
     */
    EReference getCaseService_VisibleForCaseStates();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DocumentOperation <em>Document Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentOperation
     * @generated
     */
    EClass getDocumentOperation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DocumentOperation#getCaseDocRefOperation <em>Case Doc Ref Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Case Doc Ref Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentOperation#getCaseDocRefOperation()
     * @see #getDocumentOperation()
     * @generated
     */
    EReference getDocumentOperation_CaseDocRefOperation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DocumentOperation#getCaseDocFindOperations <em>Case Doc Find Operations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Case Doc Find Operations</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentOperation#getCaseDocFindOperations()
     * @see #getDocumentOperation()
     * @generated
     */
    EReference getDocumentOperation_CaseDocFindOperations();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.DocumentOperation#getLinkSystemDocumentOperation <em>Link System Document Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Link System Document Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.DocumentOperation#getLinkSystemDocumentOperation()
     * @see #getDocumentOperation()
     * @generated
     */
    EReference getDocumentOperation_LinkSystemDocumentOperation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations <em>Case Doc Ref Operations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Case Doc Ref Operations</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocRefOperations
     * @generated
     */
    EClass getCaseDocRefOperations();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getMoveCaseDocOperation <em>Move Case Doc Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Move Case Doc Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocRefOperations#getMoveCaseDocOperation()
     * @see #getCaseDocRefOperations()
     * @generated
     */
    EReference getCaseDocRefOperations_MoveCaseDocOperation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getUnlinkCaseDocOperation <em>Unlink Case Doc Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Unlink Case Doc Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocRefOperations#getUnlinkCaseDocOperation()
     * @see #getCaseDocRefOperations()
     * @generated
     */
    EReference getCaseDocRefOperations_UnlinkCaseDocOperation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getLinkCaseDocOperation <em>Link Case Doc Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Link Case Doc Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocRefOperations#getLinkCaseDocOperation()
     * @see #getCaseDocRefOperations()
     * @generated
     */
    EReference getCaseDocRefOperations_LinkCaseDocOperation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getDeleteCaseDocOperation <em>Delete Case Doc Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Case Doc Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocRefOperations#getDeleteCaseDocOperation()
     * @see #getCaseDocRefOperations()
     * @generated
     */
    EReference getCaseDocRefOperations_DeleteCaseDocOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocRefOperations#getCaseDocumentRefField <em>Case Document Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Case Document Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocRefOperations#getCaseDocumentRefField()
     * @see #getCaseDocRefOperations()
     * @generated
     */
    EAttribute getCaseDocRefOperations_CaseDocumentRefField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations <em>Case Doc Find Operations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Case Doc Find Operations</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocFindOperations
     * @generated
     */
    EClass getCaseDocFindOperations();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getFindByFileNameOperation <em>Find By File Name Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Find By File Name Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocFindOperations#getFindByFileNameOperation()
     * @see #getCaseDocFindOperations()
     * @generated
     */
    EReference getCaseDocFindOperations_FindByFileNameOperation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getFindByQueryOperation <em>Find By Query Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Find By Query Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocFindOperations#getFindByQueryOperation()
     * @see #getCaseDocFindOperations()
     * @generated
     */
    EReference getCaseDocFindOperations_FindByQueryOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getReturnCaseDocRefsField <em>Return Case Doc Refs Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Return Case Doc Refs Field</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocFindOperations#getReturnCaseDocRefsField()
     * @see #getCaseDocFindOperations()
     * @generated
     */
    EAttribute getCaseDocFindOperations_ReturnCaseDocRefsField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getCaseRefField <em>Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocFindOperations#getCaseRefField()
     * @see #getCaseDocFindOperations()
     * @generated
     */
    EAttribute getCaseDocFindOperations_CaseRefField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.MoveCaseDocOperation <em>Move Case Doc Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Move Case Doc Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.MoveCaseDocOperation
     * @generated
     */
    EClass getMoveCaseDocOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.MoveCaseDocOperation#getSourceCaseRefField <em>Source Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Source Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.MoveCaseDocOperation#getSourceCaseRefField()
     * @see #getMoveCaseDocOperation()
     * @generated
     */
    EAttribute getMoveCaseDocOperation_SourceCaseRefField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.MoveCaseDocOperation#getTargetCaseRefField <em>Target Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.MoveCaseDocOperation#getTargetCaseRefField()
     * @see #getMoveCaseDocOperation()
     * @generated
     */
    EAttribute getMoveCaseDocOperation_TargetCaseRefField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation <em>Unlink Case Doc Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unlink Case Doc Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation
     * @generated
     */
    EClass getUnlinkCaseDocOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation#getSourceCaseRefField <em>Source Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Source Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation#getSourceCaseRefField()
     * @see #getUnlinkCaseDocOperation()
     * @generated
     */
    EAttribute getUnlinkCaseDocOperation_SourceCaseRefField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.LinkCaseDocOperation <em>Link Case Doc Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Link Case Doc Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.LinkCaseDocOperation
     * @generated
     */
    EClass getLinkCaseDocOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.LinkCaseDocOperation#getTargetCaseRefField <em>Target Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.LinkCaseDocOperation#getTargetCaseRefField()
     * @see #getLinkCaseDocOperation()
     * @generated
     */
    EAttribute getLinkCaseDocOperation_TargetCaseRefField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation <em>Link System Document Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Link System Document Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation
     * @generated
     */
    EClass getLinkSystemDocumentOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getDocumentId <em>Document Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Document Id</em>'.
     * @see com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getDocumentId()
     * @see #getLinkSystemDocumentOperation()
     * @generated
     */
    EAttribute getLinkSystemDocumentOperation_DocumentId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getReturnCaseDocRefField <em>Return Case Doc Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Return Case Doc Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getReturnCaseDocRefField()
     * @see #getLinkSystemDocumentOperation()
     * @generated
     */
    EAttribute getLinkSystemDocumentOperation_ReturnCaseDocRefField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getCaseRefField <em>Case Ref Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Case Ref Field</em>'.
     * @see com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getCaseRefField()
     * @see #getLinkSystemDocumentOperation()
     * @generated
     */
    EAttribute getLinkSystemDocumentOperation_CaseRefField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DeleteCaseDocOperation <em>Delete Case Doc Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Case Doc Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.DeleteCaseDocOperation
     * @generated
     */
    EClass getDeleteCaseDocOperation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.FindByFileNameOperation <em>Find By File Name Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Find By File Name Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.FindByFileNameOperation
     * @generated
     */
    EClass getFindByFileNameOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.FindByFileNameOperation#getFileNameField <em>File Name Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>File Name Field</em>'.
     * @see com.tibco.xpd.xpdExtension.FindByFileNameOperation#getFileNameField()
     * @see #getFindByFileNameOperation()
     * @generated
     */
    EAttribute getFindByFileNameOperation_FileNameField();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.FindByQueryOperation <em>Find By Query Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Find By Query Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.FindByQueryOperation
     * @generated
     */
    EClass getFindByQueryOperation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.FindByQueryOperation#getCaseDocumentQueryExpression <em>Case Document Query Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Case Document Query Expression</em>'.
     * @see com.tibco.xpd.xpdExtension.FindByQueryOperation#getCaseDocumentQueryExpression()
     * @see #getFindByQueryOperation()
     * @generated
     */
    EReference getFindByQueryOperation_CaseDocumentQueryExpression();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression <em>Case Document Query Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Case Document Query Expression</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression
     * @generated
     */
    EClass getCaseDocumentQueryExpression();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getQueryExpressionJoinType <em>Query Expression Join Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Query Expression Join Type</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getQueryExpressionJoinType()
     * @see #getCaseDocumentQueryExpression()
     * @generated
     */
    EAttribute getCaseDocumentQueryExpression_QueryExpressionJoinType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOpenBracketCount <em>Open Bracket Count</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Open Bracket Count</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOpenBracketCount()
     * @see #getCaseDocumentQueryExpression()
     * @generated
     */
    EAttribute getCaseDocumentQueryExpression_OpenBracketCount();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCmisProperty <em>Cmis Property</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cmis Property</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCmisProperty()
     * @see #getCaseDocumentQueryExpression()
     * @generated
     */
    EAttribute getCaseDocumentQueryExpression_CmisProperty();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOperator <em>Operator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Operator</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOperator()
     * @see #getCaseDocumentQueryExpression()
     * @generated
     */
    EAttribute getCaseDocumentQueryExpression_Operator();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getProcessDataField <em>Process Data Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Data Field</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getProcessDataField()
     * @see #getCaseDocumentQueryExpression()
     * @generated
     */
    EAttribute getCaseDocumentQueryExpression_ProcessDataField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCloseBracketCount <em>Close Bracket Count</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Close Bracket Count</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCloseBracketCount()
     * @see #getCaseDocumentQueryExpression()
     * @generated
     */
    EAttribute getCaseDocumentQueryExpression_CloseBracketCount();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#isCmisDocumentPropertySelected <em>Cmis Document Property Selected</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cmis Document Property Selected</em>'.
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#isCmisDocumentPropertySelected()
     * @see #getCaseDocumentQueryExpression()
     * @generated
     */
    EAttribute getCaseDocumentQueryExpression_CmisDocumentPropertySelected();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration <em>Service Process Configuration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Service Process Configuration</em>'.
     * @see com.tibco.xpd.xpdExtension.ServiceProcessConfiguration
     * @generated
     */
    EClass getServiceProcessConfiguration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToProcessRuntime <em>Deploy To Process Runtime</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deploy To Process Runtime</em>'.
     * @see com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToProcessRuntime()
     * @see #getServiceProcessConfiguration()
     * @generated
     */
    EAttribute getServiceProcessConfiguration_DeployToProcessRuntime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToPageflowRuntime <em>Deploy To Pageflow Runtime</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deploy To Pageflow Runtime</em>'.
     * @see com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToPageflowRuntime()
     * @see #getServiceProcessConfiguration()
     * @generated
     */
    EAttribute getServiceProcessConfiguration_DeployToPageflowRuntime();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper <em>Script Data Mapper</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Script Data Mapper</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptDataMapper
     * @generated
     */
    EClass getScriptDataMapper();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMapperContext <em>Mapper Context</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapper Context</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptDataMapper#getMapperContext()
     * @see #getScriptDataMapper()
     * @generated
     */
    EAttribute getScriptDataMapper_MapperContext();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMappingDirection <em>Mapping Direction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapping Direction</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptDataMapper#getMappingDirection()
     * @see #getScriptDataMapper()
     * @generated
     */
    EAttribute getScriptDataMapper_MappingDirection();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getDataMappings <em>Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Mappings</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptDataMapper#getDataMappings()
     * @see #getScriptDataMapper()
     * @generated
     */
    EReference getScriptDataMapper_DataMappings();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getUnmappedScripts <em>Unmapped Scripts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Unmapped Scripts</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptDataMapper#getUnmappedScripts()
     * @see #getScriptDataMapper()
     * @generated
     */
    EReference getScriptDataMapper_UnmappedScripts();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getArrayInflationType <em>Array Inflation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Array Inflation Type</em>'.
     * @see com.tibco.xpd.xpdExtension.ScriptDataMapper#getArrayInflationType()
     * @see #getScriptDataMapper()
     * @generated
     */
    EReference getScriptDataMapper_ArrayInflationType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation <em>Data Mapper Array Inflation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Mapper Array Inflation</em>'.
     * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflation
     * @generated
     */
    EClass getDataMapperArrayInflation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getPath <em>Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Path</em>'.
     * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getPath()
     * @see #getDataMapperArrayInflation()
     * @generated
     */
    EAttribute getDataMapperArrayInflation_Path();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getMappingType <em>Mapping Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapping Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getMappingType()
     * @see #getDataMapperArrayInflation()
     * @generated
     */
    EAttribute getDataMapperArrayInflation_MappingType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getContributorId <em>Contributor Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Contributor Id</em>'.
     * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflation#getContributorId()
     * @see #getDataMapperArrayInflation()
     * @generated
     */
    EAttribute getDataMapperArrayInflation_ContributorId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.LikeMappingExclusion <em>Like Mapping Exclusion</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Like Mapping Exclusion</em>'.
     * @see com.tibco.xpd.xpdExtension.LikeMappingExclusion
     * @generated
     */
    EClass getLikeMappingExclusion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.LikeMappingExclusion#getPath <em>Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Path</em>'.
     * @see com.tibco.xpd.xpdExtension.LikeMappingExclusion#getPath()
     * @see #getLikeMappingExclusion()
     * @generated
     */
    EAttribute getLikeMappingExclusion_Path();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.LikeMappingExclusions <em>Like Mapping Exclusions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Like Mapping Exclusions</em>'.
     * @see com.tibco.xpd.xpdExtension.LikeMappingExclusions
     * @generated
     */
    EClass getLikeMappingExclusions();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdExtension.LikeMappingExclusions#getExclusions <em>Exclusions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Exclusions</em>'.
     * @see com.tibco.xpd.xpdExtension.LikeMappingExclusions#getExclusions()
     * @see #getLikeMappingExclusions()
     * @generated
     */
    EReference getLikeMappingExclusions_Exclusions();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdExtension.RestServiceOperation <em>Rest Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Rest Service Operation</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceOperation
     * @generated
     */
    EClass getRestServiceOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RestServiceOperation#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Location</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceOperation#getLocation()
     * @see #getRestServiceOperation()
     * @generated
     */
    EAttribute getRestServiceOperation_Location();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdExtension.RestServiceOperation#getMethodId <em>Method Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Method Id</em>'.
     * @see com.tibco.xpd.xpdExtension.RestServiceOperation#getMethodId()
     * @see #getRestServiceOperation()
     * @generated
     */
    EAttribute getRestServiceOperation_MethodId();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.AllocationStrategyType <em>Allocation Strategy Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Allocation Strategy Type</em>'.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategyType
     * @generated
     */
    EEnum getAllocationStrategyType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.AllocationType <em>Allocation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Allocation Type</em>'.
     * @see com.tibco.xpd.xpdExtension.AllocationType
     * @generated
     */
    EEnum getAllocationType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.AuditEventType <em>Audit Event Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Audit Event Type</em>'.
     * @see com.tibco.xpd.xpdExtension.AuditEventType
     * @generated
     */
    EEnum getAuditEventType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.CorrelationMode <em>Correlation Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Correlation Mode</em>'.
     * @see com.tibco.xpd.xpdExtension.CorrelationMode
     * @generated
     */
    EEnum getCorrelationMode();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.ErrorThrowerType <em>Error Thrower Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Error Thrower Type</em>'.
     * @see com.tibco.xpd.xpdExtension.ErrorThrowerType
     * @generated
     */
    EEnum getErrorThrowerType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy <em>Event Handler Flow Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Event Handler Flow Strategy</em>'.
     * @see com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy
     * @generated
     */
    EEnum getEventHandlerFlowStrategy();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.FlowRoutingStyle <em>Flow Routing Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Flow Routing Style</em>'.
     * @see com.tibco.xpd.xpdExtension.FlowRoutingStyle
     * @generated
     */
    EEnum getFlowRoutingStyle();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.FormImplementationType <em>Form Implementation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Form Implementation Type</em>'.
     * @see com.tibco.xpd.xpdExtension.FormImplementationType
     * @generated
     */
    EEnum getFormImplementationType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.MaxRetryActionType <em>Max Retry Action Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Max Retry Action Type</em>'.
     * @see com.tibco.xpd.xpdExtension.MaxRetryActionType
     * @generated
     */
    EEnum getMaxRetryActionType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.RescheduleDurationType <em>Reschedule Duration Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Reschedule Duration Type</em>'.
     * @see com.tibco.xpd.xpdExtension.RescheduleDurationType
     * @generated
     */
    EEnum getRescheduleDurationType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType <em>Reschedule Timer Selection Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Reschedule Timer Selection Type</em>'.
     * @see com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType
     * @generated
     */
    EEnum getRescheduleTimerSelectionType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.SecurityPolicy <em>Security Policy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Security Policy</em>'.
     * @see com.tibco.xpd.xpdExtension.SecurityPolicy
     * @generated
     */
    EEnum getSecurityPolicy();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.SoapBindingStyle <em>Soap Binding Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Soap Binding Style</em>'.
     * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
     * @generated
     */
    EEnum getSoapBindingStyle();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.SubProcessStartStrategy <em>Sub Process Start Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Sub Process Start Strategy</em>'.
     * @see com.tibco.xpd.xpdExtension.SubProcessStartStrategy
     * @generated
     */
    EEnum getSubProcessStartStrategy();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.SystemErrorActionType <em>System Error Action Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>System Error Action Type</em>'.
     * @see com.tibco.xpd.xpdExtension.SystemErrorActionType
     * @generated
     */
    EEnum getSystemErrorActionType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverrideType <em>Validation Issue Override Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Validation Issue Override Type</em>'.
     * @see com.tibco.xpd.xpdExtension.ValidationIssueOverrideType
     * @generated
     */
    EEnum getValidationIssueOverrideType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.Visibility <em>Visibility</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Visibility</em>'.
     * @see com.tibco.xpd.xpdExtension.Visibility
     * @generated
     */
    EEnum getVisibility();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.DeliveryMode <em>Delivery Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Delivery Mode</em>'.
     * @see com.tibco.xpd.xpdExtension.DeliveryMode
     * @generated
     */
    EEnum getDeliveryMode();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.XpdModelType <em>Xpd Model Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Xpd Model Type</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdModelType
     * @generated
     */
    EEnum getXpdModelType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.AdHocExecutionTypeType <em>Ad Hoc Execution Type Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Ad Hoc Execution Type Type</em>'.
     * @see com.tibco.xpd.xpdExtension.AdHocExecutionTypeType
     * @generated
     */
    EEnum getAdHocExecutionTypeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.QueryExpressionJoinType <em>Query Expression Join Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Query Expression Join Type</em>'.
     * @see com.tibco.xpd.xpdExtension.QueryExpressionJoinType
     * @generated
     */
    EEnum getQueryExpressionJoinType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.CMISQueryOperator <em>CMIS Query Operator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>CMIS Query Operator</em>'.
     * @see com.tibco.xpd.xpdExtension.CMISQueryOperator
     * @generated
     */
    EEnum getCMISQueryOperator();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.AsyncExecutionMode <em>Async Execution Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Async Execution Mode</em>'.
     * @see com.tibco.xpd.xpdExtension.AsyncExecutionMode
     * @generated
     */
    EEnum getAsyncExecutionMode();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.SignalType <em>Signal Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Signal Type</em>'.
     * @see com.tibco.xpd.xpdExtension.SignalType
     * @generated
     */
    EEnum getSignalType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.XpdInterfaceType <em>Xpd Interface Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Xpd Interface Type</em>'.
     * @see com.tibco.xpd.xpdExtension.XpdInterfaceType
     * @generated
     */
    EEnum getXpdInterfaceType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflationType <em>Data Mapper Array Inflation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Data Mapper Array Inflation Type</em>'.
     * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflationType
     * @generated
     */
    EEnum getDataMapperArrayInflationType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdExtension.BusinessServicePublishType <em>Business Service Publish Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Business Service Publish Type</em>'.
     * @see com.tibco.xpd.xpdExtension.BusinessServicePublishType
     * @generated
     */
    EEnum getBusinessServicePublishType();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdExtension.AuditEventType <em>Audit Event Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Audit Event Type Object</em>'.
     * @see com.tibco.xpd.xpdExtension.AuditEventType
     * @model instanceClass="com.tibco.xpd.xpdExtension.AuditEventType"
     *        extendedMetaData="name='Type_._type:Object' baseType='Type_._type'"
     * @generated
     */
    EDataType getAuditEventTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdExtension.SecurityPolicy <em>Security Policy Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Security Policy Object</em>'.
     * @see com.tibco.xpd.xpdExtension.SecurityPolicy
     * @model instanceClass="com.tibco.xpd.xpdExtension.SecurityPolicy"
     *        extendedMetaData="name='SecurityPolicy:Object' baseType='SecurityPolicy'"
     * @generated
     */
    EDataType getSecurityPolicyObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdExtension.SoapBindingStyle <em>Soap Binding Style Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Soap Binding Style Object</em>'.
     * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
     * @model instanceClass="com.tibco.xpd.xpdExtension.SoapBindingStyle"
     *        extendedMetaData="name='SoapBindingStyle:Object' baseType='SoapBindingStyle'"
     * @generated
     */
    EDataType getSoapBindingStyleObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    XpdExtensionFactory getXpdExtensionFactory();

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
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ActivityRefImpl <em>Activity Ref</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ActivityRefImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getActivityRef()
         * @generated
         */
        EClass ACTIVITY_REF = eINSTANCE.getActivityRef();

        /**
         * The meta object literal for the '<em><b>Id Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY_REF__ID_REF = eINSTANCE.getActivityRef_IdRef();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ActivityResourcePatternsImpl <em>Activity Resource Patterns</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ActivityResourcePatternsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getActivityResourcePatterns()
         * @generated
         */
        EClass ACTIVITY_RESOURCE_PATTERNS = eINSTANCE.getActivityResourcePatterns();

        /**
         * The meta object literal for the '<em><b>Allocation Strategy</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY_RESOURCE_PATTERNS__ALLOCATION_STRATEGY =
                eINSTANCE.getActivityResourcePatterns_AllocationStrategy();

        /**
         * The meta object literal for the '<em><b>Piling</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY_RESOURCE_PATTERNS__PILING = eINSTANCE.getActivityResourcePatterns_Piling();

        /**
         * The meta object literal for the '<em><b>Work Item Priority</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY_RESOURCE_PATTERNS__WORK_ITEM_PRIORITY =
                eINSTANCE.getActivityResourcePatterns_WorkItemPriority();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl <em>Allocation Strategy</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAllocationStrategy()
         * @generated
         */
        EClass ALLOCATION_STRATEGY = eINSTANCE.getAllocationStrategy();

        /**
         * The meta object literal for the '<em><b>Offer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATION_STRATEGY__OFFER = eINSTANCE.getAllocationStrategy_Offer();

        /**
         * The meta object literal for the '<em><b>Strategy</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATION_STRATEGY__STRATEGY = eINSTANCE.getAllocationStrategy_Strategy();

        /**
         * The meta object literal for the '<em><b>Re Offer On Close</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE = eINSTANCE.getAllocationStrategy_ReOfferOnClose();

        /**
         * The meta object literal for the '<em><b>Re Offer On Cancel</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL = eINSTANCE.getAllocationStrategy_ReOfferOnCancel();

        /**
         * The meta object literal for the '<em><b>Allocate To Offer Set Member Identifier</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER =
                eINSTANCE.getAllocationStrategy_AllocateToOfferSetMemberIdentifier();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldsImpl <em>Associated Correlation Fields</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedCorrelationFields()
         * @generated
         */
        EClass ASSOCIATED_CORRELATION_FIELDS = eINSTANCE.getAssociatedCorrelationFields();

        /**
         * The meta object literal for the '<em><b>Associated Correlation Field</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD =
                eINSTANCE.getAssociatedCorrelationFields_AssociatedCorrelationField();

        /**
         * The meta object literal for the '<em><b>Disable Implicit Association</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION =
                eINSTANCE.getAssociatedCorrelationFields_DisableImplicitAssociation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldImpl <em>Associated Correlation Field</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedCorrelationField()
         * @generated
         */
        EClass ASSOCIATED_CORRELATION_FIELD = eINSTANCE.getAssociatedCorrelationField();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATED_CORRELATION_FIELD__NAME = eINSTANCE.getAssociatedCorrelationField_Name();

        /**
         * The meta object literal for the '<em><b>Correlation Mode</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATED_CORRELATION_FIELD__CORRELATION_MODE =
                eINSTANCE.getAssociatedCorrelationField_CorrelationMode();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AssociatedParameterImpl <em>Associated Parameter</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AssociatedParameterImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedParameter()
         * @generated
         */
        EClass ASSOCIATED_PARAMETER = eINSTANCE.getAssociatedParameter();

        /**
         * The meta object literal for the '<em><b>Formal Param</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATED_PARAMETER__FORMAL_PARAM = eINSTANCE.getAssociatedParameter_FormalParam();

        /**
         * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATED_PARAMETER__MODE = eINSTANCE.getAssociatedParameter_Mode();

        /**
         * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATED_PARAMETER__MANDATORY = eINSTANCE.getAssociatedParameter_Mandatory();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AssociatedParametersImpl <em>Associated Parameters</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AssociatedParametersImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedParameters()
         * @generated
         */
        EClass ASSOCIATED_PARAMETERS = eINSTANCE.getAssociatedParameters();

        /**
         * The meta object literal for the '<em><b>Associated Parameter</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSOCIATED_PARAMETERS__ASSOCIATED_PARAMETER =
                eINSTANCE.getAssociatedParameters_AssociatedParameter();

        /**
         * The meta object literal for the '<em><b>Disable Implicit Association</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATED_PARAMETERS__DISABLE_IMPLICIT_ASSOCIATION =
                eINSTANCE.getAssociatedParameters_DisableImplicitAssociation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer <em>Associated Parameters Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.AssociatedParametersContainer
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAssociatedParametersContainer()
         * @generated
         */
        EClass ASSOCIATED_PARAMETERS_CONTAINER = eINSTANCE.getAssociatedParametersContainer();

        /**
         * The meta object literal for the '<em><b>Associated Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS =
                eINSTANCE.getAssociatedParametersContainer_AssociatedParameters();

        /**
         * The meta object literal for the '<em><b>Disable Implicit Association</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATED_PARAMETERS_CONTAINER__DISABLE_IMPLICIT_ASSOCIATION =
                eINSTANCE.getAssociatedParametersContainer_DisableImplicitAssociation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AuditImpl <em>Audit</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AuditImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAudit()
         * @generated
         */
        EClass AUDIT = eINSTANCE.getAudit();

        /**
         * The meta object literal for the '<em><b>Audit Event</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference AUDIT__AUDIT_EVENT = eINSTANCE.getAudit_AuditEvent();

        /**
         * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute AUDIT__ANY = eINSTANCE.getAudit_Any();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AuditEventImpl <em>Audit Event</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AuditEventImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAuditEvent()
         * @generated
         */
        EClass AUDIT_EVENT = eINSTANCE.getAuditEvent();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute AUDIT_EVENT__TYPE = eINSTANCE.getAuditEvent_Type();

        /**
         * The meta object literal for the '<em><b>Information</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference AUDIT_EVENT__INFORMATION = eINSTANCE.getAuditEvent_Information();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.BusinessProcessImpl <em>Business Process</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.BusinessProcessImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getBusinessProcess()
         * @generated
         */
        EClass BUSINESS_PROCESS = eINSTANCE.getBusinessProcess();

        /**
         * The meta object literal for the '<em><b>Process Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BUSINESS_PROCESS__PROCESS_ID = eINSTANCE.getBusinessProcess_ProcessId();

        /**
         * The meta object literal for the '<em><b>Package Ref Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BUSINESS_PROCESS__PACKAGE_REF_ID = eINSTANCE.getBusinessProcess_PackageRefId();

        /**
         * The meta object literal for the '<em><b>Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BUSINESS_PROCESS__ACTIVITY_ID = eINSTANCE.getBusinessProcess_ActivityId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CalendarReferenceImpl <em>Calendar Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CalendarReferenceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCalendarReference()
         * @generated
         */
        EClass CALENDAR_REFERENCE = eINSTANCE.getCalendarReference();

        /**
         * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CALENDAR_REFERENCE__ALIAS = eINSTANCE.getCalendarReference_Alias();

        /**
         * The meta object literal for the '<em><b>Data Field Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CALENDAR_REFERENCE__DATA_FIELD_ID = eINSTANCE.getCalendarReference_DataFieldId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CatchErrorMappingsImpl <em>Catch Error Mappings</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CatchErrorMappingsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCatchErrorMappings()
         * @generated
         */
        EClass CATCH_ERROR_MAPPINGS = eINSTANCE.getCatchErrorMappings();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CATCH_ERROR_MAPPINGS__MESSAGE = eINSTANCE.getCatchErrorMappings_Message();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl <em>Constant Period</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getConstantPeriod()
         * @generated
         */
        EClass CONSTANT_PERIOD = eINSTANCE.getConstantPeriod();

        /**
         * The meta object literal for the '<em><b>Days</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_PERIOD__DAYS = eINSTANCE.getConstantPeriod_Days();

        /**
         * The meta object literal for the '<em><b>Hours</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_PERIOD__HOURS = eINSTANCE.getConstantPeriod_Hours();

        /**
         * The meta object literal for the '<em><b>Micro Seconds</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_PERIOD__MICRO_SECONDS = eINSTANCE.getConstantPeriod_MicroSeconds();

        /**
         * The meta object literal for the '<em><b>Minutes</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_PERIOD__MINUTES = eINSTANCE.getConstantPeriod_Minutes();

        /**
         * The meta object literal for the '<em><b>Months</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_PERIOD__MONTHS = eINSTANCE.getConstantPeriod_Months();

        /**
         * The meta object literal for the '<em><b>Seconds</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_PERIOD__SECONDS = eINSTANCE.getConstantPeriod_Seconds();

        /**
         * The meta object literal for the '<em><b>Weeks</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_PERIOD__WEEKS = eINSTANCE.getConstantPeriod_Weeks();

        /**
         * The meta object literal for the '<em><b>Years</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_PERIOD__YEARS = eINSTANCE.getConstantPeriod_Years();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ConditionalParticipantImpl <em>Conditional Participant</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ConditionalParticipantImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getConditionalParticipant()
         * @generated
         */
        EClass CONDITIONAL_PARTICIPANT = eINSTANCE.getConditionalParticipant();

        /**
         * The meta object literal for the '<em><b>Performer Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CONDITIONAL_PARTICIPANT__PERFORMER_SCRIPT = eINSTANCE.getConditionalParticipant_PerformerScript();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ReplyImmediateDataMappingsImpl <em>Reply Immediate Data Mappings</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ReplyImmediateDataMappingsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getReplyImmediateDataMappings()
         * @generated
         */
        EClass REPLY_IMMEDIATE_DATA_MAPPINGS = eINSTANCE.getReplyImmediateDataMappings();

        /**
         * The meta object literal for the '<em><b>Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REPLY_IMMEDIATE_DATA_MAPPINGS__DATA_MAPPINGS =
                eINSTANCE.getReplyImmediateDataMappings_DataMappings();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CorrelationDataMappingsImpl <em>Correlation Data Mappings</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CorrelationDataMappingsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCorrelationDataMappings()
         * @generated
         */
        EClass CORRELATION_DATA_MAPPINGS = eINSTANCE.getCorrelationDataMappings();

        /**
         * The meta object literal for the '<em><b>Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CORRELATION_DATA_MAPPINGS__DATA_MAPPINGS = eINSTANCE.getCorrelationDataMappings_DataMappings();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DiscriminatorImpl <em>Discriminator</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DiscriminatorImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDiscriminator()
         * @generated
         */
        EClass DISCRIMINATOR = eINSTANCE.getDiscriminator();

        /**
         * The meta object literal for the '<em><b>Discriminator Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DISCRIMINATOR__DISCRIMINATOR_TYPE = eINSTANCE.getDiscriminator_DiscriminatorType();

        /**
         * The meta object literal for the '<em><b>Structured Discriminator</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DISCRIMINATOR__STRUCTURED_DISCRIMINATOR = eINSTANCE.getDiscriminator_StructuredDiscriminator();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DocumentRootImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Implementation Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__IMPLEMENTATION_TYPE = eINSTANCE.getDocumentRoot_ImplementationType();

        /**
         * The meta object literal for the '<em><b>Data Object Attributes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES = eINSTANCE.getDocumentRoot_DataObjectAttributes();

        /**
         * The meta object literal for the '<em><b>Extended Attributes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__EXTENDED_ATTRIBUTES = eINSTANCE.getDocumentRoot_ExtendedAttributes();

        /**
         * The meta object literal for the '<em><b>Continue On Timeout</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT = eINSTANCE.getDocumentRoot_ContinueOnTimeout();

        /**
         * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__ALIAS = eINSTANCE.getDocumentRoot_Alias();

        /**
         * The meta object literal for the '<em><b>Constant Period</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CONSTANT_PERIOD = eINSTANCE.getDocumentRoot_ConstantPeriod();

        /**
         * The meta object literal for the '<em><b>User Task Scripts</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__USER_TASK_SCRIPTS = eINSTANCE.getDocumentRoot_UserTaskScripts();

        /**
         * The meta object literal for the '<em><b>Audit</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__AUDIT = eINSTANCE.getDocumentRoot_Audit();

        /**
         * The meta object literal for the '<em><b>Script</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SCRIPT = eINSTANCE.getDocumentRoot_Script();

        /**
         * The meta object literal for the '<em><b>Reply Immediately</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__REPLY_IMMEDIATELY = eINSTANCE.getDocumentRoot_ReplyImmediately();

        /**
         * The meta object literal for the '<em><b>Initial Values</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__INITIAL_VALUES = eINSTANCE.getDocumentRoot_InitialValues();

        /**
         * The meta object literal for the '<em><b>Associated Correlation Fields</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS =
                eINSTANCE.getDocumentRoot_AssociatedCorrelationFields();

        /**
         * The meta object literal for the '<em><b>Associated Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASSOCIATED_PARAMETERS = eINSTANCE.getDocumentRoot_AssociatedParameters();

        /**
         * The meta object literal for the '<em><b>Implemented Interface</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__IMPLEMENTED_INTERFACE = eINSTANCE.getDocumentRoot_ImplementedInterface();

        /**
         * The meta object literal for the '<em><b>Process Interfaces</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PROCESS_INTERFACES = eINSTANCE.getDocumentRoot_ProcessInterfaces();

        /**
         * The meta object literal for the '<em><b>Wsdl Event Association</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION = eINSTANCE.getDocumentRoot_WsdlEventAssociation();

        /**
         * The meta object literal for the '<em><b>Inline Sub Process</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__INLINE_SUB_PROCESS = eINSTANCE.getDocumentRoot_InlineSubProcess();

        /**
         * The meta object literal for the '<em><b>Documentation URL</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__DOCUMENTATION_URL = eINSTANCE.getDocumentRoot_DocumentationURL();

        /**
         * The meta object literal for the '<em><b>Implements</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__IMPLEMENTS = eINSTANCE.getDocumentRoot_Implements();

        /**
         * The meta object literal for the '<em><b>Multi Instance Scripts</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS = eINSTANCE.getDocumentRoot_MultiInstanceScripts();

        /**
         * The meta object literal for the '<em><b>Process Identifier Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD = eINSTANCE.getDocumentRoot_ProcessIdentifierField();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__EXPRESSION = eINSTANCE.getDocumentRoot_Expression();

        /**
         * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__VISIBILITY = eINSTANCE.getDocumentRoot_Visibility();

        /**
         * The meta object literal for the '<em><b>Security Profile</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SECURITY_PROFILE = eINSTANCE.getDocumentRoot_SecurityProfile();

        /**
         * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__LANGUAGE = eINSTANCE.getDocumentRoot_Language();

        /**
         * The meta object literal for the '<em><b>Initial Parameter Value</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE = eINSTANCE.getDocumentRoot_InitialParameterValue();

        /**
         * The meta object literal for the '<em><b>Initial Value Mapping</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__INITIAL_VALUE_MAPPING = eINSTANCE.getDocumentRoot_InitialValueMapping();

        /**
         * The meta object literal for the '<em><b>Port Type Operation</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PORT_TYPE_OPERATION = eINSTANCE.getDocumentRoot_PortTypeOperation();

        /**
         * The meta object literal for the '<em><b>Transport</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__TRANSPORT = eINSTANCE.getDocumentRoot_Transport();

        /**
         * The meta object literal for the '<em><b>Is Chained</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__IS_CHAINED = eINSTANCE.getDocumentRoot_IsChained();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__EXTERNAL_REFERENCE = eINSTANCE.getDocumentRoot_ExternalReference();

        /**
         * The meta object literal for the '<em><b>Process Resource Patterns</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS = eINSTANCE.getDocumentRoot_ProcessResourcePatterns();

        /**
         * The meta object literal for the '<em><b>Event Handler Initialisers</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS = eINSTANCE.getDocumentRoot_EventHandlerInitialisers();

        /**
         * The meta object literal for the '<em><b>Activity Resource Patterns</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS = eINSTANCE.getDocumentRoot_ActivityResourcePatterns();

        /**
         * The meta object literal for the '<em><b>Require New Transaction</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION = eINSTANCE.getDocumentRoot_RequireNewTransaction();

        /**
         * The meta object literal for the '<em><b>Document Operation</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DOCUMENT_OPERATION = eINSTANCE.getDocumentRoot_DocumentOperation();

        /**
         * The meta object literal for the '<em><b>Duration Calculation</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DURATION_CALCULATION = eINSTANCE.getDocumentRoot_DurationCalculation();

        /**
         * The meta object literal for the '<em><b>Discriminator</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DISCRIMINATOR = eINSTANCE.getDocumentRoot_Discriminator();

        /**
         * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__DISPLAY_NAME = eINSTANCE.getDocumentRoot_DisplayName();

        /**
         * The meta object literal for the '<em><b>Catch Throw</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__CATCH_THROW = eINSTANCE.getDocumentRoot_CatchThrow();

        /**
         * The meta object literal for the '<em><b>Is Remote</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__IS_REMOTE = eINSTANCE.getDocumentRoot_IsRemote();

        /**
         * The meta object literal for the '<em><b>Correlation Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS = eINSTANCE.getDocumentRoot_CorrelationDataMappings();

        /**
         * The meta object literal for the '<em><b>Transform Script</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__TRANSFORM_SCRIPT = eINSTANCE.getDocumentRoot_TransformScript();

        /**
         * The meta object literal for the '<em><b>Publish As Business Service</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE = eINSTANCE.getDocumentRoot_PublishAsBusinessService();

        /**
         * The meta object literal for the '<em><b>Business Service Category</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY = eINSTANCE.getDocumentRoot_BusinessServiceCategory();

        /**
         * The meta object literal for the '<em><b>Error Thrower Info</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ERROR_THROWER_INFO = eINSTANCE.getDocumentRoot_ErrorThrowerInfo();

        /**
         * The meta object literal for the '<em><b>Catch Error Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS = eINSTANCE.getDocumentRoot_CatchErrorMappings();

        /**
         * The meta object literal for the '<em><b>Conditional Participant</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT = eINSTANCE.getDocumentRoot_ConditionalParticipant();

        /**
         * The meta object literal for the '<em><b>Generated</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__GENERATED = eINSTANCE.getDocumentRoot_Generated();

        /**
         * The meta object literal for the '<em><b>Reply To Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID = eINSTANCE.getDocumentRoot_ReplyToActivityId();

        /**
         * The meta object literal for the '<em><b>Task Library Reference</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE = eINSTANCE.getDocumentRoot_TaskLibraryReference();

        /**
         * The meta object literal for the '<em><b>Set Performer In Process</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS = eINSTANCE.getDocumentRoot_SetPerformerInProcess();

        /**
         * The meta object literal for the '<em><b>Emb Subproc Other State Height</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT =
                eINSTANCE.getDocumentRoot_EmbSubprocOtherStateHeight();

        /**
         * The meta object literal for the '<em><b>Emb Subproc Other State Width</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH = eINSTANCE.getDocumentRoot_EmbSubprocOtherStateWidth();

        /**
         * The meta object literal for the '<em><b>Form Implementation</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__FORM_IMPLEMENTATION = eINSTANCE.getDocumentRoot_FormImplementation();

        /**
         * The meta object literal for the '<em><b>Participant Query</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PARTICIPANT_QUERY = eINSTANCE.getDocumentRoot_ParticipantQuery();

        /**
         * The meta object literal for the '<em><b>Api End Point Participant</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__API_END_POINT_PARTICIPANT = eINSTANCE.getDocumentRoot_ApiEndPointParticipant();

        /**
         * The meta object literal for the '<em><b>Fault Message</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__FAULT_MESSAGE = eINSTANCE.getDocumentRoot_FaultMessage();

        /**
         * The meta object literal for the '<em><b>Request Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__REQUEST_ACTIVITY_ID = eINSTANCE.getDocumentRoot_RequestActivityId();

        /**
         * The meta object literal for the '<em><b>Business Process</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__BUSINESS_PROCESS = eINSTANCE.getDocumentRoot_BusinessProcess();

        /**
         * The meta object literal for the '<em><b>Wsdl Generation</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__WSDL_GENERATION = eINSTANCE.getDocumentRoot_WsdlGeneration();

        /**
         * The meta object literal for the '<em><b>Target Primitive Property</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY = eINSTANCE.getDocumentRoot_TargetPrimitiveProperty();

        /**
         * The meta object literal for the '<em><b>Source Primitive Property</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY = eINSTANCE.getDocumentRoot_SourcePrimitiveProperty();

        /**
         * The meta object literal for the '<em><b>Decision Service</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DECISION_SERVICE = eINSTANCE.getDocumentRoot_DecisionService();

        /**
         * The meta object literal for the '<em><b>Participant Shared Resource</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE = eINSTANCE.getDocumentRoot_ParticipantSharedResource();

        /**
         * The meta object literal for the '<em><b>Xpd Model Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__XPD_MODEL_TYPE = eINSTANCE.getDocumentRoot_XpdModelType();

        /**
         * The meta object literal for the '<em><b>Flow Routing Style</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__FLOW_ROUTING_STYLE = eINSTANCE.getDocumentRoot_FlowRoutingStyle();

        /**
         * The meta object literal for the '<em><b>Calendar Reference</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CALENDAR_REFERENCE = eINSTANCE.getDocumentRoot_CalendarReference();

        /**
         * The meta object literal for the '<em><b>Non Cancelling</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__NON_CANCELLING = eINSTANCE.getDocumentRoot_NonCancelling();

        /**
         * The meta object literal for the '<em><b>Signal Data</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SIGNAL_DATA = eINSTANCE.getDocumentRoot_SignalData();

        /**
         * The meta object literal for the '<em><b>Retry</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__RETRY = eINSTANCE.getDocumentRoot_Retry();

        /**
         * The meta object literal for the '<em><b>Activity Deadline Event Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID = eINSTANCE.getDocumentRoot_ActivityDeadlineEventId();

        /**
         * The meta object literal for the '<em><b>Start Strategy</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__START_STRATEGY = eINSTANCE.getDocumentRoot_StartStrategy();

        /**
         * The meta object literal for the '<em><b>Overwrite Already Modified Task Data</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA =
                eINSTANCE.getDocumentRoot_OverwriteAlreadyModifiedTaskData();

        /**
         * The meta object literal for the '<em><b>Event Handler Flow Strategy</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY = eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy();

        /**
         * The meta object literal for the '<em><b>Namespace Prefix Map</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP = eINSTANCE.getDocumentRoot_NamespacePrefixMap();

        /**
         * The meta object literal for the '<em><b>Suspend Resume With Parent</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT = eINSTANCE.getDocumentRoot_SuspendResumeWithParent();

        /**
         * The meta object literal for the '<em><b>System Error Action</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SYSTEM_ERROR_ACTION = eINSTANCE.getDocumentRoot_SystemErrorAction();

        /**
         * The meta object literal for the '<em><b>Correlation Timeout</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CORRELATION_TIMEOUT = eINSTANCE.getDocumentRoot_CorrelationTimeout();

        /**
         * The meta object literal for the '<em><b>Validation Control</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__VALIDATION_CONTROL = eINSTANCE.getDocumentRoot_ValidationControl();

        /**
         * The meta object literal for the '<em><b>Reply Immediate Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS =
                eINSTANCE.getDocumentRoot_ReplyImmediateDataMappings();

        /**
         * The meta object literal for the '<em><b>Bx Use Unqualified Property Names</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES =
                eINSTANCE.getDocumentRoot_BxUseUnqualifiedPropertyNames();

        /**
         * The meta object literal for the '<em><b>Case Ref Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CASE_REF_TYPE = eINSTANCE.getDocumentRoot_CaseRefType();

        /**
         * The meta object literal for the '<em><b>REST Services</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__REST_SERVICES = eINSTANCE.getDocumentRoot_RESTServices();

        /**
         * The meta object literal for the '<em><b>Publish As Rest Service</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE = eINSTANCE.getDocumentRoot_PublishAsRestService();

        /**
         * The meta object literal for the '<em><b>Rest Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__REST_ACTIVITY_ID = eINSTANCE.getDocumentRoot_RestActivityId();

        /**
         * The meta object literal for the '<em><b>Reschedule Timer Script</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT = eINSTANCE.getDocumentRoot_RescheduleTimerScript();

        /**
         * The meta object literal for the '<em><b>Dynamic Organization Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS =
                eINSTANCE.getDocumentRoot_DynamicOrganizationMappings();

        /**
         * The meta object literal for the '<em><b>Signal Handler Asynchronous</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS = eINSTANCE.getDocumentRoot_SignalHandlerAsynchronous();

        /**
         * The meta object literal for the '<em><b>Global Data Operation</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GLOBAL_DATA_OPERATION = eINSTANCE.getDocumentRoot_GlobalDataOperation();

        /**
         * The meta object literal for the '<em><b>Process Data Work Item Attribute Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS =
                eINSTANCE.getDocumentRoot_ProcessDataWorkItemAttributeMappings();

        /**
         * The meta object literal for the '<em><b>Allow Unqualified Sub Process Identification</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION =
                eINSTANCE.getDocumentRoot_AllowUnqualifiedSubProcessIdentification();

        /**
         * The meta object literal for the '<em><b>Bpm Runtime Configuration</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION = eINSTANCE.getDocumentRoot_BpmRuntimeConfiguration();

        /**
         * The meta object literal for the '<em><b>Is Case Service</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__IS_CASE_SERVICE = eINSTANCE.getDocumentRoot_IsCaseService();

        /**
         * The meta object literal for the '<em><b>Required Access Privileges</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES = eINSTANCE.getDocumentRoot_RequiredAccessPrivileges();

        /**
         * The meta object literal for the '<em><b>Case Service</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CASE_SERVICE = eINSTANCE.getDocumentRoot_CaseService();

        /**
         * The meta object literal for the '<em><b>Ad Hoc Task Configuration</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION = eINSTANCE.getDocumentRoot_AdHocTaskConfiguration();

        /**
         * The meta object literal for the '<em><b>Is Event Sub Process</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS = eINSTANCE.getDocumentRoot_IsEventSubProcess();

        /**
         * The meta object literal for the '<em><b>Non Interrupting Event</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__NON_INTERRUPTING_EVENT = eINSTANCE.getDocumentRoot_NonInterruptingEvent();

        /**
         * The meta object literal for the '<em><b>Correlate Immediately</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__CORRELATE_IMMEDIATELY = eINSTANCE.getDocumentRoot_CorrelateImmediately();

        /**
         * The meta object literal for the '<em><b>Async Execution Mode</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__ASYNC_EXECUTION_MODE = eINSTANCE.getDocumentRoot_AsyncExecutionMode();

        /**
         * The meta object literal for the '<em><b>Signal Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SIGNAL_TYPE = eINSTANCE.getDocumentRoot_SignalType();

        /**
         * The meta object literal for the '<em><b>Service Process Configuration</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION =
                eINSTANCE.getDocumentRoot_ServiceProcessConfiguration();

        /**
         * The meta object literal for the '<em><b>Like Mapping</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__LIKE_MAPPING = eINSTANCE.getDocumentRoot_LikeMapping();

        /**
         * The meta object literal for the '<em><b>Script Data Mapper</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SCRIPT_DATA_MAPPER = eINSTANCE.getDocumentRoot_ScriptDataMapper();

        /**
         * The meta object literal for the '<em><b>Like Mapping Exclusions</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS = eINSTANCE.getDocumentRoot_LikeMappingExclusions();

        /**
         * The meta object literal for the '<em><b>Source Contributor Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID = eINSTANCE.getDocumentRoot_SourceContributorId();

        /**
         * The meta object literal for the '<em><b>Target Contributor Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID = eINSTANCE.getDocumentRoot_TargetContributorId();

        /**
         * The meta object literal for the '<em><b>Rest Service Operation</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__REST_SERVICE_OPERATION = eINSTANCE.getDocumentRoot_RestServiceOperation();

        /**
         * The meta object literal for the '<em><b>Input Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__INPUT_MAPPINGS = eINSTANCE.getDocumentRoot_InputMappings();

        /**
         * The meta object literal for the '<em><b>Output Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__OUTPUT_MAPPINGS = eINSTANCE.getDocumentRoot_OutputMappings();

        /**
         * The meta object literal for the '<em><b>Business Service Publish Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE =
                eINSTANCE.getDocumentRoot_BusinessServicePublishType();

        /**
         * The meta object literal for the '<em><b>Suppress Max Mappings Error</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR = eINSTANCE.getDocumentRoot_SuppressMaxMappingsError();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl <em>Duration Calculation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDurationCalculation()
         * @generated
         */
        EClass DURATION_CALCULATION = eINSTANCE.getDurationCalculation();

        /**
         * The meta object literal for the '<em><b>Years</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DURATION_CALCULATION__YEARS = eINSTANCE.getDurationCalculation_Years();

        /**
         * The meta object literal for the '<em><b>Months</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DURATION_CALCULATION__MONTHS = eINSTANCE.getDurationCalculation_Months();

        /**
         * The meta object literal for the '<em><b>Weeks</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DURATION_CALCULATION__WEEKS = eINSTANCE.getDurationCalculation_Weeks();

        /**
         * The meta object literal for the '<em><b>Days</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DURATION_CALCULATION__DAYS = eINSTANCE.getDurationCalculation_Days();

        /**
         * The meta object literal for the '<em><b>Hours</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DURATION_CALCULATION__HOURS = eINSTANCE.getDurationCalculation_Hours();

        /**
         * The meta object literal for the '<em><b>Minutes</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DURATION_CALCULATION__MINUTES = eINSTANCE.getDurationCalculation_Minutes();

        /**
         * The meta object literal for the '<em><b>Seconds</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DURATION_CALCULATION__SECONDS = eINSTANCE.getDurationCalculation_Seconds();

        /**
         * The meta object literal for the '<em><b>Microseconds</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DURATION_CALCULATION__MICROSECONDS = eINSTANCE.getDurationCalculation_Microseconds();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingsImpl <em>Dynamic Organization Mappings</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDynamicOrganizationMappings()
         * @generated
         */
        EClass DYNAMIC_ORGANIZATION_MAPPINGS = eINSTANCE.getDynamicOrganizationMappings();

        /**
         * The meta object literal for the '<em><b>Dynamic Organization Mapping</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DYNAMIC_ORGANIZATION_MAPPINGS__DYNAMIC_ORGANIZATION_MAPPING =
                eINSTANCE.getDynamicOrganizationMappings_DynamicOrganizationMapping();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingImpl <em>Dynamic Organization Mapping</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DynamicOrganizationMappingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDynamicOrganizationMapping()
         * @generated
         */
        EClass DYNAMIC_ORGANIZATION_MAPPING = eINSTANCE.getDynamicOrganizationMapping();

        /**
         * The meta object literal for the '<em><b>Source Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DYNAMIC_ORGANIZATION_MAPPING__SOURCE_PATH = eINSTANCE.getDynamicOrganizationMapping_SourcePath();

        /**
         * The meta object literal for the '<em><b>Dynamic Org Identifier Ref</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DYNAMIC_ORGANIZATION_MAPPING__DYNAMIC_ORG_IDENTIFIER_REF =
                eINSTANCE.getDynamicOrganizationMapping_DynamicOrgIdentifierRef();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DynamicOrgIdentifierRefImpl <em>Dynamic Org Identifier Ref</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DynamicOrgIdentifierRefImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDynamicOrgIdentifierRef()
         * @generated
         */
        EClass DYNAMIC_ORG_IDENTIFIER_REF = eINSTANCE.getDynamicOrgIdentifierRef();

        /**
         * The meta object literal for the '<em><b>Identifier Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DYNAMIC_ORG_IDENTIFIER_REF__IDENTIFIER_NAME = eINSTANCE.getDynamicOrgIdentifierRef_IdentifierName();

        /**
         * The meta object literal for the '<em><b>Dynamic Org Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DYNAMIC_ORG_IDENTIFIER_REF__DYNAMIC_ORG_ID = eINSTANCE.getDynamicOrgIdentifierRef_DynamicOrgId();

        /**
         * The meta object literal for the '<em><b>Org Model Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DYNAMIC_ORG_IDENTIFIER_REF__ORG_MODEL_PATH = eINSTANCE.getDynamicOrgIdentifierRef_OrgModelPath();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.EmailResourceImpl <em>Email Resource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.EmailResourceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getEmailResource()
         * @generated
         */
        EClass EMAIL_RESOURCE = eINSTANCE.getEmailResource();

        /**
         * The meta object literal for the '<em><b>Instance Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EMAIL_RESOURCE__INSTANCE_NAME = eINSTANCE.getEmailResource_InstanceName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ErrorMethodImpl <em>Error Method</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ErrorMethodImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getErrorMethod()
         * @generated
         */
        EClass ERROR_METHOD = eINSTANCE.getErrorMethod();

        /**
         * The meta object literal for the '<em><b>Error Code</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_METHOD__ERROR_CODE = eINSTANCE.getErrorMethod_ErrorCode();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ErrorThrowerInfoImpl <em>Error Thrower Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ErrorThrowerInfoImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getErrorThrowerInfo()
         * @generated
         */
        EClass ERROR_THROWER_INFO = eINSTANCE.getErrorThrowerInfo();

        /**
         * The meta object literal for the '<em><b>Thrower Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_THROWER_INFO__THROWER_ID = eINSTANCE.getErrorThrowerInfo_ThrowerId();

        /**
         * The meta object literal for the '<em><b>Thrower Container Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_THROWER_INFO__THROWER_CONTAINER_ID = eINSTANCE.getErrorThrowerInfo_ThrowerContainerId();

        /**
         * The meta object literal for the '<em><b>Thrower Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_THROWER_INFO__THROWER_TYPE = eINSTANCE.getErrorThrowerInfo_ThrowerType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.EventHandlerInitialisersImpl <em>Event Handler Initialisers</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.EventHandlerInitialisersImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getEventHandlerInitialisers()
         * @generated
         */
        EClass EVENT_HANDLER_INITIALISERS = eINSTANCE.getEventHandlerInitialisers();

        /**
         * The meta object literal for the '<em><b>Activity Ref</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EVENT_HANDLER_INITIALISERS__ACTIVITY_REF = eINSTANCE.getEventHandlerInitialisers_ActivityRef();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.FaultMessageImpl <em>Fault Message</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.FaultMessageImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFaultMessage()
         * @generated
         */
        EClass FAULT_MESSAGE = eINSTANCE.getFaultMessage();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.FormImplementationImpl <em>Form Implementation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.FormImplementationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFormImplementation()
         * @generated
         */
        EClass FORM_IMPLEMENTATION = eINSTANCE.getFormImplementation();

        /**
         * The meta object literal for the '<em><b>Form Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_IMPLEMENTATION__FORM_TYPE = eINSTANCE.getFormImplementation_FormType();

        /**
         * The meta object literal for the '<em><b>Form URI</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_IMPLEMENTATION__FORM_URI = eINSTANCE.getFormImplementation_FormURI();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ImplementedInterfaceImpl <em>Implemented Interface</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ImplementedInterfaceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getImplementedInterface()
         * @generated
         */
        EClass IMPLEMENTED_INTERFACE = eINSTANCE.getImplementedInterface();

        /**
         * The meta object literal for the '<em><b>Package Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute IMPLEMENTED_INTERFACE__PACKAGE_REF = eINSTANCE.getImplementedInterface_PackageRef();

        /**
         * The meta object literal for the '<em><b>Process Interface Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute IMPLEMENTED_INTERFACE__PROCESS_INTERFACE_ID = eINSTANCE.getImplementedInterface_ProcessInterfaceId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.InitialValuesImpl <em>Initial Values</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.InitialValuesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getInitialValues()
         * @generated
         */
        EClass INITIAL_VALUES = eINSTANCE.getInitialValues();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INITIAL_VALUES__VALUE = eINSTANCE.getInitialValues_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.InitialParameterValueImpl <em>Initial Parameter Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.InitialParameterValueImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getInitialParameterValue()
         * @generated
         */
        EClass INITIAL_PARAMETER_VALUE = eINSTANCE.getInitialParameterValue();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INITIAL_PARAMETER_VALUE__NAME = eINSTANCE.getInitialParameterValue_Name();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INITIAL_PARAMETER_VALUE__VALUE = eINSTANCE.getInitialParameterValue_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.InterfaceMethod <em>Interface Method</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.InterfaceMethod
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getInterfaceMethod()
         * @generated
         */
        EClass INTERFACE_METHOD = eINSTANCE.getInterfaceMethod();

        /**
         * The meta object literal for the '<em><b>Trigger</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INTERFACE_METHOD__TRIGGER = eINSTANCE.getInterfaceMethod_Trigger();

        /**
         * The meta object literal for the '<em><b>Trigger Result Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERFACE_METHOD__TRIGGER_RESULT_MESSAGE = eINSTANCE.getInterfaceMethod_TriggerResultMessage();

        /**
         * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INTERFACE_METHOD__VISIBILITY = eINSTANCE.getInterfaceMethod_Visibility();

        /**
         * The meta object literal for the '<em><b>Error Methods</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERFACE_METHOD__ERROR_METHODS = eINSTANCE.getInterfaceMethod_ErrorMethods();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.IntermediateMethodImpl <em>Intermediate Method</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.IntermediateMethodImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getIntermediateMethod()
         * @generated
         */
        EClass INTERMEDIATE_METHOD = eINSTANCE.getIntermediateMethod();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.JdbcResourceImpl <em>Jdbc Resource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.JdbcResourceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getJdbcResource()
         * @generated
         */
        EClass JDBC_RESOURCE = eINSTANCE.getJdbcResource();

        /**
         * The meta object literal for the '<em><b>Instance Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute JDBC_RESOURCE__INSTANCE_NAME = eINSTANCE.getJdbcResource_InstanceName();

        /**
         * The meta object literal for the '<em><b>Jdbc Profile Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute JDBC_RESOURCE__JDBC_PROFILE_NAME = eINSTANCE.getJdbcResource_JdbcProfileName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.MultiInstanceScriptsImpl <em>Multi Instance Scripts</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.MultiInstanceScriptsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getMultiInstanceScripts()
         * @generated
         */
        EClass MULTI_INSTANCE_SCRIPTS = eINSTANCE.getMultiInstanceScripts();

        /**
         * The meta object literal for the '<em><b>Additional Instances</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MULTI_INSTANCE_SCRIPTS__ADDITIONAL_INSTANCES =
                eINSTANCE.getMultiInstanceScripts_AdditionalInstances();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.NamespacePrefixMapImpl <em>Namespace Prefix Map</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.NamespacePrefixMapImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getNamespacePrefixMap()
         * @generated
         */
        EClass NAMESPACE_PREFIX_MAP = eINSTANCE.getNamespacePrefixMap();

        /**
         * The meta object literal for the '<em><b>Namespace Entries</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES = eINSTANCE.getNamespacePrefixMap_NamespaceEntries();

        /**
         * The meta object literal for the '<em><b>Prefix Mapping Disabled</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMESPACE_PREFIX_MAP__PREFIX_MAPPING_DISABLED =
                eINSTANCE.getNamespacePrefixMap_PrefixMappingDisabled();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.NamespaceMapEntryImpl <em>Namespace Map Entry</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.NamespaceMapEntryImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getNamespaceMapEntry()
         * @generated
         */
        EClass NAMESPACE_MAP_ENTRY = eINSTANCE.getNamespaceMapEntry();

        /**
         * The meta object literal for the '<em><b>Prefix</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMESPACE_MAP_ENTRY__PREFIX = eINSTANCE.getNamespaceMapEntry_Prefix();

        /**
         * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMESPACE_MAP_ENTRY__NAMESPACE = eINSTANCE.getNamespaceMapEntry_Namespace();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ParticipantSharedResourceImpl <em>Participant Shared Resource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ParticipantSharedResourceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getParticipantSharedResource()
         * @generated
         */
        EClass PARTICIPANT_SHARED_RESOURCE = eINSTANCE.getParticipantSharedResource();

        /**
         * The meta object literal for the '<em><b>Email</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTICIPANT_SHARED_RESOURCE__EMAIL = eINSTANCE.getParticipantSharedResource_Email();

        /**
         * The meta object literal for the '<em><b>Jdbc</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTICIPANT_SHARED_RESOURCE__JDBC = eINSTANCE.getParticipantSharedResource_Jdbc();

        /**
         * The meta object literal for the '<em><b>Web Service</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTICIPANT_SHARED_RESOURCE__WEB_SERVICE = eINSTANCE.getParticipantSharedResource_WebService();

        /**
         * The meta object literal for the '<em><b>Rest Service</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTICIPANT_SHARED_RESOURCE__REST_SERVICE = eINSTANCE.getParticipantSharedResource_RestService();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.PilingInfoImpl <em>Piling Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.PilingInfoImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getPilingInfo()
         * @generated
         */
        EClass PILING_INFO = eINSTANCE.getPilingInfo();

        /**
         * The meta object literal for the '<em><b>Piling Allowed</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PILING_INFO__PILING_ALLOWED = eINSTANCE.getPilingInfo_PilingAllowed();

        /**
         * The meta object literal for the '<em><b>Max Piliable Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PILING_INFO__MAX_PILIABLE_ITEMS = eINSTANCE.getPilingInfo_MaxPiliableItems();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.PortTypeOperationImpl <em>Port Type Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.PortTypeOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getPortTypeOperation()
         * @generated
         */
        EClass PORT_TYPE_OPERATION = eINSTANCE.getPortTypeOperation();

        /**
         * The meta object literal for the '<em><b>Port Type Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PORT_TYPE_OPERATION__PORT_TYPE_NAME = eINSTANCE.getPortTypeOperation_PortTypeName();

        /**
         * The meta object literal for the '<em><b>Operation Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PORT_TYPE_OPERATION__OPERATION_NAME = eINSTANCE.getPortTypeOperation_OperationName();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PORT_TYPE_OPERATION__EXTERNAL_REFERENCE = eINSTANCE.getPortTypeOperation_ExternalReference();

        /**
         * The meta object literal for the '<em><b>Transport</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PORT_TYPE_OPERATION__TRANSPORT = eINSTANCE.getPortTypeOperation_Transport();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl <em>Process Interface</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ProcessInterfaceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getProcessInterface()
         * @generated
         */
        EClass PROCESS_INTERFACE = eINSTANCE.getProcessInterface();

        /**
         * The meta object literal for the '<em><b>Start Methods</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_INTERFACE__START_METHODS = eINSTANCE.getProcessInterface_StartMethods();

        /**
         * The meta object literal for the '<em><b>Intermediate Methods</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_INTERFACE__INTERMEDIATE_METHODS = eINSTANCE.getProcessInterface_IntermediateMethods();

        /**
         * The meta object literal for the '<em><b>Xpd Interface Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS_INTERFACE__XPD_INTERFACE_TYPE = eINSTANCE.getProcessInterface_XpdInterfaceType();

        /**
         * The meta object literal for the '<em><b>Service Process Configuration</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION =
                eINSTANCE.getProcessInterface_ServiceProcessConfiguration();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ProcessInterfacesImpl <em>Process Interfaces</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ProcessInterfacesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getProcessInterfaces()
         * @generated
         */
        EClass PROCESS_INTERFACES = eINSTANCE.getProcessInterfaces();

        /**
         * The meta object literal for the '<em><b>Process Interface</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_INTERFACES__PROCESS_INTERFACE = eINSTANCE.getProcessInterfaces_ProcessInterface();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ProcessResourcePatternsImpl <em>Process Resource Patterns</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ProcessResourcePatternsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getProcessResourcePatterns()
         * @generated
         */
        EClass PROCESS_RESOURCE_PATTERNS = eINSTANCE.getProcessResourcePatterns();

        /**
         * The meta object literal for the '<em><b>Separation Of Duties Activities</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES =
                eINSTANCE.getProcessResourcePatterns_SeparationOfDutiesActivities();

        /**
         * The meta object literal for the '<em><b>Retain Familiar Activities</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES =
                eINSTANCE.getProcessResourcePatterns_RetainFamiliarActivities();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RescheduleTimerScriptImpl <em>Reschedule Timer Script</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RescheduleTimerScriptImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRescheduleTimerScript()
         * @generated
         */
        EClass RESCHEDULE_TIMER_SCRIPT = eINSTANCE.getRescheduleTimerScript();

        /**
         * The meta object literal for the '<em><b>Duration Relative To</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO =
                eINSTANCE.getRescheduleTimerScript_DurationRelativeTo();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RescheduleTimersImpl <em>Reschedule Timers</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RescheduleTimersImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRescheduleTimers()
         * @generated
         */
        EClass RESCHEDULE_TIMERS = eINSTANCE.getRescheduleTimers();

        /**
         * The meta object literal for the '<em><b>Timer Selection Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE = eINSTANCE.getRescheduleTimers_TimerSelectionType();

        /**
         * The meta object literal for the '<em><b>Timer Events</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESCHEDULE_TIMERS__TIMER_EVENTS = eINSTANCE.getRescheduleTimers_TimerEvents();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RESTServicesImpl <em>REST Services</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RESTServicesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRESTServices()
         * @generated
         */
        EClass REST_SERVICES = eINSTANCE.getRESTServices();

        /**
         * The meta object literal for the '<em><b>REST Services</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REST_SERVICES__REST_SERVICES = eINSTANCE.getRESTServices_RESTServices();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl <em>Rest Service Resource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRestServiceResource()
         * @generated
         */
        EClass REST_SERVICE_RESOURCE = eINSTANCE.getRestServiceResource();

        /**
         * The meta object literal for the '<em><b>Security Policy</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REST_SERVICE_RESOURCE__SECURITY_POLICY = eINSTANCE.getRestServiceResource_SecurityPolicy();

        /**
         * The meta object literal for the '<em><b>Resource Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REST_SERVICE_RESOURCE__RESOURCE_NAME = eINSTANCE.getRestServiceResource_ResourceName();

        /**
         * The meta object literal for the '<em><b>Resource Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REST_SERVICE_RESOURCE__RESOURCE_TYPE = eINSTANCE.getRestServiceResource_ResourceType();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REST_SERVICE_RESOURCE__DESCRIPTION = eINSTANCE.getRestServiceResource_Description();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceSecurityImpl <em>Rest Service Resource Security</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RestServiceResourceSecurityImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRestServiceResourceSecurity()
         * @generated
         */
        EClass REST_SERVICE_RESOURCE_SECURITY = eINSTANCE.getRestServiceResourceSecurity();

        /**
         * The meta object literal for the '<em><b>Policy Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REST_SERVICE_RESOURCE_SECURITY__POLICY_TYPE = eINSTANCE.getRestServiceResourceSecurity_PolicyType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RetainFamiliarActivitiesImpl <em>Retain Familiar Activities</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RetainFamiliarActivitiesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRetainFamiliarActivities()
         * @generated
         */
        EClass RETAIN_FAMILIAR_ACTIVITIES = eINSTANCE.getRetainFamiliarActivities();

        /**
         * The meta object literal for the '<em><b>Activity Ref</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RETAIN_FAMILIAR_ACTIVITIES__ACTIVITY_REF = eINSTANCE.getRetainFamiliarActivities_ActivityRef();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RetryImpl <em>Retry</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RetryImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRetry()
         * @generated
         */
        EClass RETRY = eINSTANCE.getRetry();

        /**
         * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RETRY__MAX = eINSTANCE.getRetry_Max();

        /**
         * The meta object literal for the '<em><b>Initial Period</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RETRY__INITIAL_PERIOD = eINSTANCE.getRetry_InitialPeriod();

        /**
         * The meta object literal for the '<em><b>Period Increment</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RETRY__PERIOD_INCREMENT = eINSTANCE.getRetry_PeriodIncrement();

        /**
         * The meta object literal for the '<em><b>Max Retry Action</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RETRY__MAX_RETRY_ACTION = eINSTANCE.getRetry_MaxRetryAction();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ScriptInformationImpl <em>Script Information</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ScriptInformationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getScriptInformation()
         * @generated
         */
        EClass SCRIPT_INFORMATION = eINSTANCE.getScriptInformation();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_INFORMATION__EXPRESSION = eINSTANCE.getScriptInformation_Expression();

        /**
         * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT_INFORMATION__DIRECTION = eINSTANCE.getScriptInformation_Direction();

        /**
         * The meta object literal for the '<em><b>Activity</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_INFORMATION__ACTIVITY = eINSTANCE.getScriptInformation_Activity();

        /**
         * The meta object literal for the '<em><b>Reference</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT_INFORMATION__REFERENCE = eINSTANCE.getScriptInformation_Reference();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.SeparationOfDutiesActivitiesImpl <em>Separation Of Duties Activities</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.SeparationOfDutiesActivitiesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSeparationOfDutiesActivities()
         * @generated
         */
        EClass SEPARATION_OF_DUTIES_ACTIVITIES = eINSTANCE.getSeparationOfDutiesActivities();

        /**
         * The meta object literal for the '<em><b>Activity Ref</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SEPARATION_OF_DUTIES_ACTIVITIES__ACTIVITY_REF =
                eINSTANCE.getSeparationOfDutiesActivities_ActivityRef();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.SignalDataImpl <em>Signal Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.SignalDataImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSignalData()
         * @generated
         */
        EClass SIGNAL_DATA = eINSTANCE.getSignalData();

        /**
         * The meta object literal for the '<em><b>Correlation Mappings</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIGNAL_DATA__CORRELATION_MAPPINGS = eINSTANCE.getSignalData_CorrelationMappings();

        /**
         * The meta object literal for the '<em><b>Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIGNAL_DATA__DATA_MAPPINGS = eINSTANCE.getSignalData_DataMappings();

        /**
         * The meta object literal for the '<em><b>Reschedule Timers</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIGNAL_DATA__RESCHEDULE_TIMERS = eINSTANCE.getSignalData_RescheduleTimers();

        /**
         * The meta object literal for the '<em><b>Input Script Data Mapper</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER = eINSTANCE.getSignalData_InputScriptDataMapper();

        /**
         * The meta object literal for the '<em><b>Output Script Data Mapper</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER = eINSTANCE.getSignalData_OutputScriptDataMapper();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl <em>Start Method</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.StartMethodImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getStartMethod()
         * @generated
         */
        EClass START_METHOD = eINSTANCE.getStartMethod();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.StructuredDiscriminatorImpl <em>Structured Discriminator</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.StructuredDiscriminatorImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getStructuredDiscriminator()
         * @generated
         */
        EClass STRUCTURED_DISCRIMINATOR = eINSTANCE.getStructuredDiscriminator();

        /**
         * The meta object literal for the '<em><b>Wait For Incoming Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute STRUCTURED_DISCRIMINATOR__WAIT_FOR_INCOMING_PATH =
                eINSTANCE.getStructuredDiscriminator_WaitForIncomingPath();

        /**
         * The meta object literal for the '<em><b>Up Stream Parallel Split</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute STRUCTURED_DISCRIMINATOR__UP_STREAM_PARALLEL_SPLIT =
                eINSTANCE.getStructuredDiscriminator_UpStreamParallelSplit();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.TaskLibraryReferenceImpl <em>Task Library Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.TaskLibraryReferenceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getTaskLibraryReference()
         * @generated
         */
        EClass TASK_LIBRARY_REFERENCE = eINSTANCE.getTaskLibraryReference();

        /**
         * The meta object literal for the '<em><b>Task Library Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_LIBRARY_REFERENCE__TASK_LIBRARY_ID = eINSTANCE.getTaskLibraryReference_TaskLibraryId();

        /**
         * The meta object literal for the '<em><b>Package Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_LIBRARY_REFERENCE__PACKAGE_REF = eINSTANCE.getTaskLibraryReference_PackageRef();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.TransformScriptImpl <em>Transform Script</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.TransformScriptImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getTransformScript()
         * @generated
         */
        EClass TRANSFORM_SCRIPT = eINSTANCE.getTransformScript();

        /**
         * The meta object literal for the '<em><b>Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRANSFORM_SCRIPT__DATA_MAPPINGS = eINSTANCE.getTransformScript_DataMappings();

        /**
         * The meta object literal for the '<em><b>Input Dom</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSFORM_SCRIPT__INPUT_DOM = eINSTANCE.getTransformScript_InputDom();

        /**
         * The meta object literal for the '<em><b>Output Dom</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSFORM_SCRIPT__OUTPUT_DOM = eINSTANCE.getTransformScript_OutputDom();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl <em>User Task Scripts</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.UserTaskScriptsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getUserTaskScripts()
         * @generated
         */
        EClass USER_TASK_SCRIPTS = eINSTANCE.getUserTaskScripts();

        /**
         * The meta object literal for the '<em><b>Open Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER_TASK_SCRIPTS__OPEN_SCRIPT = eINSTANCE.getUserTaskScripts_OpenScript();

        /**
         * The meta object literal for the '<em><b>Close Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER_TASK_SCRIPTS__CLOSE_SCRIPT = eINSTANCE.getUserTaskScripts_CloseScript();

        /**
         * The meta object literal for the '<em><b>Submit Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER_TASK_SCRIPTS__SUBMIT_SCRIPT = eINSTANCE.getUserTaskScripts_SubmitScript();

        /**
         * The meta object literal for the '<em><b>Schedule Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER_TASK_SCRIPTS__SCHEDULE_SCRIPT = eINSTANCE.getUserTaskScripts_ScheduleScript();

        /**
         * The meta object literal for the '<em><b>Reschedule Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT = eINSTANCE.getUserTaskScripts_RescheduleScript();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ValidationControlImpl <em>Validation Control</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ValidationControlImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getValidationControl()
         * @generated
         */
        EClass VALIDATION_CONTROL = eINSTANCE.getValidationControl();

        /**
         * The meta object literal for the '<em><b>Validation Issue Overrides</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VALIDATION_CONTROL__VALIDATION_ISSUE_OVERRIDES =
                eINSTANCE.getValidationControl_ValidationIssueOverrides();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ValidationIssueOverrideImpl <em>Validation Issue Override</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ValidationIssueOverrideImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getValidationIssueOverride()
         * @generated
         */
        EClass VALIDATION_ISSUE_OVERRIDE = eINSTANCE.getValidationIssueOverride();

        /**
         * The meta object literal for the '<em><b>Validation Issue Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALIDATION_ISSUE_OVERRIDE__VALIDATION_ISSUE_ID =
                eINSTANCE.getValidationIssueOverride_ValidationIssueId();

        /**
         * The meta object literal for the '<em><b>Override Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALIDATION_ISSUE_OVERRIDE__OVERRIDE_TYPE = eINSTANCE.getValidationIssueOverride_OverrideType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsdlEventAssociationImpl <em>Wsdl Event Association</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsdlEventAssociationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsdlEventAssociation()
         * @generated
         */
        EClass WSDL_EVENT_ASSOCIATION = eINSTANCE.getWsdlEventAssociation();

        /**
         * The meta object literal for the '<em><b>Event Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WSDL_EVENT_ASSOCIATION__EVENT_ID = eINSTANCE.getWsdlEventAssociation_EventId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WorkItemPriorityImpl <em>Work Item Priority</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WorkItemPriorityImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWorkItemPriority()
         * @generated
         */
        EClass WORK_ITEM_PRIORITY = eINSTANCE.getWorkItemPriority();

        /**
         * The meta object literal for the '<em><b>Initial Priority</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_PRIORITY__INITIAL_PRIORITY = eINSTANCE.getWorkItemPriority_InitialPriority();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsdlGenerationImpl <em>Wsdl Generation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsdlGenerationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsdlGeneration()
         * @generated
         */
        EClass WSDL_GENERATION = eINSTANCE.getWsdlGeneration();

        /**
         * The meta object literal for the '<em><b>Soap Binding Style</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WSDL_GENERATION__SOAP_BINDING_STYLE = eINSTANCE.getWsdlGeneration_SoapBindingStyle();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsBindingImpl <em>Ws Binding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsBindingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsBinding()
         * @generated
         */
        EClass WS_BINDING = eINSTANCE.getWsBinding();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_BINDING__NAME = eINSTANCE.getWsBinding_Name();

        /**
         * The meta object literal for the '<em><b>Extended Properties</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_BINDING__EXTENDED_PROPERTIES = eINSTANCE.getWsBinding_ExtendedProperties();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsInboundImpl <em>Ws Inbound</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsInboundImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsInbound()
         * @generated
         */
        EClass WS_INBOUND = eINSTANCE.getWsInbound();

        /**
         * The meta object literal for the '<em><b>Virtual Binding</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_INBOUND__VIRTUAL_BINDING = eINSTANCE.getWsInbound_VirtualBinding();

        /**
         * The meta object literal for the '<em><b>Soap Http Binding</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_INBOUND__SOAP_HTTP_BINDING = eINSTANCE.getWsInbound_SoapHttpBinding();

        /**
         * The meta object literal for the '<em><b>Soap Jms Binding</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_INBOUND__SOAP_JMS_BINDING = eINSTANCE.getWsInbound_SoapJmsBinding();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsOutboundImpl <em>Ws Outbound</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsOutboundImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsOutbound()
         * @generated
         */
        EClass WS_OUTBOUND = eINSTANCE.getWsOutbound();

        /**
         * The meta object literal for the '<em><b>Virtual Binding</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_OUTBOUND__VIRTUAL_BINDING = eINSTANCE.getWsOutbound_VirtualBinding();

        /**
         * The meta object literal for the '<em><b>Soap Http Binding</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_OUTBOUND__SOAP_HTTP_BINDING = eINSTANCE.getWsOutbound_SoapHttpBinding();

        /**
         * The meta object literal for the '<em><b>Soap Jms Binding</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_OUTBOUND__SOAP_JMS_BINDING = eINSTANCE.getWsOutbound_SoapJmsBinding();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsResourceImpl <em>Ws Resource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsResourceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsResource()
         * @generated
         */
        EClass WS_RESOURCE = eINSTANCE.getWsResource();

        /**
         * The meta object literal for the '<em><b>Inbound</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_RESOURCE__INBOUND = eINSTANCE.getWsResource_Inbound();

        /**
         * The meta object literal for the '<em><b>Outbound</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_RESOURCE__OUTBOUND = eINSTANCE.getWsResource_Outbound();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsSecurityPolicyImpl <em>Ws Security Policy</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsSecurityPolicyImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSecurityPolicy()
         * @generated
         */
        EClass WS_SECURITY_POLICY = eINSTANCE.getWsSecurityPolicy();

        /**
         * The meta object literal for the '<em><b>Governance Application Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SECURITY_POLICY__GOVERNANCE_APPLICATION_NAME =
                eINSTANCE.getWsSecurityPolicy_GovernanceApplicationName();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SECURITY_POLICY__TYPE = eINSTANCE.getWsSecurityPolicy_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl <em>Ws Soap Binding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapBinding()
         * @generated
         */
        EClass WS_SOAP_BINDING = eINSTANCE.getWsSoapBinding();

        /**
         * The meta object literal for the '<em><b>Binding Style</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_BINDING__BINDING_STYLE = eINSTANCE.getWsSoapBinding_BindingStyle();

        /**
         * The meta object literal for the '<em><b>Soap Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_BINDING__SOAP_VERSION = eINSTANCE.getWsSoapBinding_SoapVersion();

        /**
         * The meta object literal for the '<em><b>Soap Security</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_SOAP_BINDING__SOAP_SECURITY = eINSTANCE.getWsSoapBinding_SoapSecurity();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpInboundBindingImpl <em>Ws Soap Http Inbound Binding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsSoapHttpInboundBindingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapHttpInboundBinding()
         * @generated
         */
        EClass WS_SOAP_HTTP_INBOUND_BINDING = eINSTANCE.getWsSoapHttpInboundBinding();

        /**
         * The meta object literal for the '<em><b>Inbound Security</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY =
                eINSTANCE.getWsSoapHttpInboundBinding_InboundSecurity();

        /**
         * The meta object literal for the '<em><b>Endpoint Url Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_HTTP_INBOUND_BINDING__ENDPOINT_URL_PATH =
                eINSTANCE.getWsSoapHttpInboundBinding_EndpointUrlPath();

        /**
         * The meta object literal for the '<em><b>Http Connector Instance Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_HTTP_INBOUND_BINDING__HTTP_CONNECTOR_INSTANCE_NAME =
                eINSTANCE.getWsSoapHttpInboundBinding_HttpConnectorInstanceName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpOutboundBindingImpl <em>Ws Soap Http Outbound Binding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsSoapHttpOutboundBindingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapHttpOutboundBinding()
         * @generated
         */
        EClass WS_SOAP_HTTP_OUTBOUND_BINDING = eINSTANCE.getWsSoapHttpOutboundBinding();

        /**
         * The meta object literal for the '<em><b>Outbound Security</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY =
                eINSTANCE.getWsSoapHttpOutboundBinding_OutboundSecurity();

        /**
         * The meta object literal for the '<em><b>Http Client Instance Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_HTTP_OUTBOUND_BINDING__HTTP_CLIENT_INSTANCE_NAME =
                eINSTANCE.getWsSoapHttpOutboundBinding_HttpClientInstanceName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsInboundBindingImpl <em>Ws Soap Jms Inbound Binding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsSoapJmsInboundBindingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapJmsInboundBinding()
         * @generated
         */
        EClass WS_SOAP_JMS_INBOUND_BINDING = eINSTANCE.getWsSoapJmsInboundBinding();

        /**
         * The meta object literal for the '<em><b>Outbound Connection Factory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY =
                eINSTANCE.getWsSoapJmsInboundBinding_OutboundConnectionFactory();

        /**
         * The meta object literal for the '<em><b>Inbound Connection Factory Configuration</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION =
                eINSTANCE.getWsSoapJmsInboundBinding_InboundConnectionFactoryConfiguration();

        /**
         * The meta object literal for the '<em><b>Inbound Destination</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION =
                eINSTANCE.getWsSoapJmsInboundBinding_InboundDestination();

        /**
         * The meta object literal for the '<em><b>Inbound Security</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY =
                eINSTANCE.getWsSoapJmsInboundBinding_InboundSecurity();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl <em>Ws Soap Jms Outbound Binding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapJmsOutboundBinding()
         * @generated
         */
        EClass WS_SOAP_JMS_OUTBOUND_BINDING = eINSTANCE.getWsSoapJmsOutboundBinding();

        /**
         * The meta object literal for the '<em><b>Outbound Connection Factory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY =
                eINSTANCE.getWsSoapJmsOutboundBinding_OutboundConnectionFactory();

        /**
         * The meta object literal for the '<em><b>Inbound Destination</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION =
                eINSTANCE.getWsSoapJmsOutboundBinding_InboundDestination();

        /**
         * The meta object literal for the '<em><b>Outbound Destination</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION =
                eINSTANCE.getWsSoapJmsOutboundBinding_OutboundDestination();

        /**
         * The meta object literal for the '<em><b>Outbound Security</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY =
                eINSTANCE.getWsSoapJmsOutboundBinding_OutboundSecurity();

        /**
         * The meta object literal for the '<em><b>Delivery Mode</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE = eINSTANCE.getWsSoapJmsOutboundBinding_DeliveryMode();

        /**
         * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY = eINSTANCE.getWsSoapJmsOutboundBinding_Priority();

        /**
         * The meta object literal for the '<em><b>Message Expiration</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION =
                eINSTANCE.getWsSoapJmsOutboundBinding_MessageExpiration();

        /**
         * The meta object literal for the '<em><b>Invocation Timeout</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT =
                eINSTANCE.getWsSoapJmsOutboundBinding_InvocationTimeout();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsSoapSecurityImpl <em>Ws Soap Security</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsSoapSecurityImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsSoapSecurity()
         * @generated
         */
        EClass WS_SOAP_SECURITY = eINSTANCE.getWsSoapSecurity();

        /**
         * The meta object literal for the '<em><b>Security Policy</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WS_SOAP_SECURITY__SECURITY_POLICY = eINSTANCE.getWsSoapSecurity_SecurityPolicy();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.WsVirtualBindingImpl <em>Ws Virtual Binding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.WsVirtualBindingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getWsVirtualBinding()
         * @generated
         */
        EClass WS_VIRTUAL_BINDING = eINSTANCE.getWsVirtualBinding();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.XpdExtDataObjectAttributesImpl <em>Xpd Ext Data Object Attributes</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtDataObjectAttributesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdExtDataObjectAttributes()
         * @generated
         */
        EClass XPD_EXT_DATA_OBJECT_ATTRIBUTES = eINSTANCE.getXpdExtDataObjectAttributes();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_DATA_OBJECT_ATTRIBUTES__DESCRIPTION = eINSTANCE.getXpdExtDataObjectAttributes_Description();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_DATA_OBJECT_ATTRIBUTES__EXTERNAL_REFERENCE =
                eINSTANCE.getXpdExtDataObjectAttributes_ExternalReference();

        /**
         * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES = eINSTANCE.getXpdExtDataObjectAttributes_Properties();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.XpdExtPropertyImpl <em>Xpd Ext Property</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtPropertyImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdExtProperty()
         * @generated
         */
        EClass XPD_EXT_PROPERTY = eINSTANCE.getXpdExtProperty();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_PROPERTY__NAME = eINSTANCE.getXpdExtProperty_Name();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_PROPERTY__VALUE = eINSTANCE.getXpdExtProperty_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl <em>Xpd Ext Attribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdExtAttribute()
         * @generated
         */
        EClass XPD_EXT_ATTRIBUTE = eINSTANCE.getXpdExtAttribute();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_ATTRIBUTE__MIXED = eINSTANCE.getXpdExtAttribute_Mixed();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_ATTRIBUTE__GROUP = eINSTANCE.getXpdExtAttribute_Group();

        /**
         * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_ATTRIBUTE__ANY = eINSTANCE.getXpdExtAttribute_Any();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_ATTRIBUTE__NAME = eINSTANCE.getXpdExtAttribute_Name();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XPD_EXT_ATTRIBUTE__VALUE = eINSTANCE.getXpdExtAttribute_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributesImpl <em>Xpd Ext Attributes</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtAttributesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdExtAttributes()
         * @generated
         */
        EClass XPD_EXT_ATTRIBUTES = eINSTANCE.getXpdExtAttributes();

        /**
         * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference XPD_EXT_ATTRIBUTES__ATTRIBUTES = eINSTANCE.getXpdExtAttributes_Attributes();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.UpdateCaseOperationTypeImpl <em>Update Case Operation Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.UpdateCaseOperationTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getUpdateCaseOperationType()
         * @generated
         */
        EClass UPDATE_CASE_OPERATION_TYPE = eINSTANCE.getUpdateCaseOperationType();

        /**
         * The meta object literal for the '<em><b>From Field Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UPDATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH = eINSTANCE.getUpdateCaseOperationType_FromFieldPath();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AddLinkAssociationsTypeImpl <em>Add Link Associations Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AddLinkAssociationsTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAddLinkAssociationsType()
         * @generated
         */
        EClass ADD_LINK_ASSOCIATIONS_TYPE = eINSTANCE.getAddLinkAssociationsType();

        /**
         * The meta object literal for the '<em><b>Add Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ADD_LINK_ASSOCIATIONS_TYPE__ADD_CASE_REF_FIELD =
                eINSTANCE.getAddLinkAssociationsType_AddCaseRefField();

        /**
         * The meta object literal for the '<em><b>Association Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ADD_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME =
                eINSTANCE.getAddLinkAssociationsType_AssociationName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RemoveLinkAssociationsTypeImpl <em>Remove Link Associations Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RemoveLinkAssociationsTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRemoveLinkAssociationsType()
         * @generated
         */
        EClass REMOVE_LINK_ASSOCIATIONS_TYPE = eINSTANCE.getRemoveLinkAssociationsType();

        /**
         * The meta object literal for the '<em><b>Association Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REMOVE_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME =
                eINSTANCE.getRemoveLinkAssociationsType_AssociationName();

        /**
         * The meta object literal for the '<em><b>Remove Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REMOVE_LINK_ASSOCIATIONS_TYPE__REMOVE_CASE_REF_FIELD =
                eINSTANCE.getRemoveLinkAssociationsType_RemoveCaseRefField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl <em>Case Reference Operations Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseReferenceOperationsType()
         * @generated
         */
        EClass CASE_REFERENCE_OPERATIONS_TYPE = eINSTANCE.getCaseReferenceOperationsType();

        /**
         * The meta object literal for the '<em><b>Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD =
                eINSTANCE.getCaseReferenceOperationsType_CaseRefField();

        /**
         * The meta object literal for the '<em><b>Update</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_REFERENCE_OPERATIONS_TYPE__UPDATE = eINSTANCE.getCaseReferenceOperationsType_Update();

        /**
         * The meta object literal for the '<em><b>Delete</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_REFERENCE_OPERATIONS_TYPE__DELETE = eINSTANCE.getCaseReferenceOperationsType_Delete();

        /**
         * The meta object literal for the '<em><b>Add Link Associations</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS =
                eINSTANCE.getCaseReferenceOperationsType_AddLinkAssociations();

        /**
         * The meta object literal for the '<em><b>Remove Link Associations</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS =
                eINSTANCE.getCaseReferenceOperationsType_RemoveLinkAssociations();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.GlobalDataOperationImpl <em>Global Data Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.GlobalDataOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getGlobalDataOperation()
         * @generated
         */
        EClass GLOBAL_DATA_OPERATION = eINSTANCE.getGlobalDataOperation();

        /**
         * The meta object literal for the '<em><b>Case Access Operations</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GLOBAL_DATA_OPERATION__CASE_ACCESS_OPERATIONS =
                eINSTANCE.getGlobalDataOperation_CaseAccessOperations();

        /**
         * The meta object literal for the '<em><b>Case Reference Operations</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GLOBAL_DATA_OPERATION__CASE_REFERENCE_OPERATIONS =
                eINSTANCE.getGlobalDataOperation_CaseReferenceOperations();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DeleteByCaseIdentifierTypeImpl <em>Delete By Case Identifier Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DeleteByCaseIdentifierTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeleteByCaseIdentifierType()
         * @generated
         */
        EClass DELETE_BY_CASE_IDENTIFIER_TYPE = eINSTANCE.getDeleteByCaseIdentifierType();

        /**
         * The meta object literal for the '<em><b>Field Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_BY_CASE_IDENTIFIER_TYPE__FIELD_PATH = eINSTANCE.getDeleteByCaseIdentifierType_FieldPath();

        /**
         * The meta object literal for the '<em><b>Identifier Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_BY_CASE_IDENTIFIER_TYPE__IDENTIFIER_NAME =
                eINSTANCE.getDeleteByCaseIdentifierType_IdentifierName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CompositeIdentifierTypeImpl <em>Composite Identifier Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CompositeIdentifierTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCompositeIdentifierType()
         * @generated
         */
        EClass COMPOSITE_IDENTIFIER_TYPE = eINSTANCE.getCompositeIdentifierType();

        /**
         * The meta object literal for the '<em><b>Field Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPOSITE_IDENTIFIER_TYPE__FIELD_PATH = eINSTANCE.getCompositeIdentifierType_FieldPath();

        /**
         * The meta object literal for the '<em><b>Identifiername</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPOSITE_IDENTIFIER_TYPE__IDENTIFIERNAME = eINSTANCE.getCompositeIdentifierType_Identifiername();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DeleteCaseReferenceOperationTypeImpl <em>Delete Case Reference Operation Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DeleteCaseReferenceOperationTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeleteCaseReferenceOperationType()
         * @generated
         */
        EClass DELETE_CASE_REFERENCE_OPERATION_TYPE = eINSTANCE.getDeleteCaseReferenceOperationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DeleteByCompositeIdentifiersTypeImpl <em>Delete By Composite Identifiers Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DeleteByCompositeIdentifiersTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeleteByCompositeIdentifiersType()
         * @generated
         */
        EClass DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE = eINSTANCE.getDeleteByCompositeIdentifiersType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__GROUP = eINSTANCE.getDeleteByCompositeIdentifiersType_Group();

        /**
         * The meta object literal for the '<em><b>Composite Identifier</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DELETE_BY_COMPOSITE_IDENTIFIERS_TYPE__COMPOSITE_IDENTIFIER =
                eINSTANCE.getDeleteByCompositeIdentifiersType_CompositeIdentifier();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CreateCaseOperationTypeImpl <em>Create Case Operation Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CreateCaseOperationTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCreateCaseOperationType()
         * @generated
         */
        EClass CREATE_CASE_OPERATION_TYPE = eINSTANCE.getCreateCaseOperationType();

        /**
         * The meta object literal for the '<em><b>From Field Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CREATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH = eINSTANCE.getCreateCaseOperationType_FromFieldPath();

        /**
         * The meta object literal for the '<em><b>To Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CREATE_CASE_OPERATION_TYPE__TO_CASE_REF_FIELD =
                eINSTANCE.getCreateCaseOperationType_ToCaseRefField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CaseAccessOperationsTypeImpl <em>Case Access Operations Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CaseAccessOperationsTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseAccessOperationsType()
         * @generated
         */
        EClass CASE_ACCESS_OPERATIONS_TYPE = eINSTANCE.getCaseAccessOperationsType();

        /**
         * The meta object literal for the '<em><b>Case Class External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE =
                eINSTANCE.getCaseAccessOperationsType_CaseClassExternalReference();

        /**
         * The meta object literal for the '<em><b>Create</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_ACCESS_OPERATIONS_TYPE__CREATE = eINSTANCE.getCaseAccessOperationsType_Create();

        /**
         * The meta object literal for the '<em><b>Delete By Case Identifier</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER =
                eINSTANCE.getCaseAccessOperationsType_DeleteByCaseIdentifier();

        /**
         * The meta object literal for the '<em><b>Delete By Composite Identifiers</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS =
                eINSTANCE.getCaseAccessOperationsType_DeleteByCompositeIdentifiers();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DataWorkItemAttributeMappingImpl <em>Data Work Item Attribute Mapping</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DataWorkItemAttributeMappingImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDataWorkItemAttributeMapping()
         * @generated
         */
        EClass DATA_WORK_ITEM_ATTRIBUTE_MAPPING = eINSTANCE.getDataWorkItemAttributeMapping();

        /**
         * The meta object literal for the '<em><b>Attribute</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_WORK_ITEM_ATTRIBUTE_MAPPING__ATTRIBUTE = eINSTANCE.getDataWorkItemAttributeMapping_Attribute();

        /**
         * The meta object literal for the '<em><b>Process Data</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_WORK_ITEM_ATTRIBUTE_MAPPING__PROCESS_DATA =
                eINSTANCE.getDataWorkItemAttributeMapping_ProcessData();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ProcessDataWorkItemAttributeMappingsImpl <em>Process Data Work Item Attribute Mappings</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ProcessDataWorkItemAttributeMappingsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getProcessDataWorkItemAttributeMappings()
         * @generated
         */
        EClass PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS = eINSTANCE.getProcessDataWorkItemAttributeMappings();

        /**
         * The meta object literal for the '<em><b>Data Work Item Attribute Mapping</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING =
                eINSTANCE.getProcessDataWorkItemAttributeMappings_DataWorkItemAttributeMapping();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.BpmRuntimeConfigurationImpl <em>Bpm Runtime Configuration</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.BpmRuntimeConfigurationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getBpmRuntimeConfiguration()
         * @generated
         */
        EClass BPM_RUNTIME_CONFIGURATION = eINSTANCE.getBpmRuntimeConfiguration();

        /**
         * The meta object literal for the '<em><b>Incoming Request Timeout</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BPM_RUNTIME_CONFIGURATION__INCOMING_REQUEST_TIMEOUT =
                eINSTANCE.getBpmRuntimeConfiguration_IncomingRequestTimeout();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.EnablementTypeImpl <em>Enablement Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.EnablementTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getEnablementType()
         * @generated
         */
        EClass ENABLEMENT_TYPE = eINSTANCE.getEnablementType();

        /**
         * The meta object literal for the '<em><b>Initializer Activities</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ENABLEMENT_TYPE__INITIALIZER_ACTIVITIES = eINSTANCE.getEnablementType_InitializerActivities();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ENABLEMENT_TYPE__PRECONDITION_EXPRESSION = eINSTANCE.getEnablementType_PreconditionExpression();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.InitializerActivitiesTypeImpl <em>Initializer Activities Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.InitializerActivitiesTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getInitializerActivitiesType()
         * @generated
         */
        EClass INITIALIZER_ACTIVITIES_TYPE = eINSTANCE.getInitializerActivitiesType();

        /**
         * The meta object literal for the '<em><b>Activity Ref</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INITIALIZER_ACTIVITIES_TYPE__ACTIVITY_REF = eINSTANCE.getInitializerActivitiesType_ActivityRef();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl <em>Ad Hoc Task Configuration Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAdHocTaskConfigurationType()
         * @generated
         */
        EClass AD_HOC_TASK_CONFIGURATION_TYPE = eINSTANCE.getAdHocTaskConfigurationType();

        /**
         * The meta object literal for the '<em><b>Enablement</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT = eINSTANCE.getAdHocTaskConfigurationType_Enablement();

        /**
         * The meta object literal for the '<em><b>Ad Hoc Execution Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE =
                eINSTANCE.getAdHocTaskConfigurationType_AdHocExecutionType();

        /**
         * The meta object literal for the '<em><b>Suspend Main Flow</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW =
                eINSTANCE.getAdHocTaskConfigurationType_SuspendMainFlow();

        /**
         * The meta object literal for the '<em><b>Allow Multiple Invocations</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS =
                eINSTANCE.getAdHocTaskConfigurationType_AllowMultipleInvocations();

        /**
         * The meta object literal for the '<em><b>Required Access Privileges</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES =
                eINSTANCE.getAdHocTaskConfigurationType_RequiredAccessPrivileges();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RequiredAccessPrivilegesImpl <em>Required Access Privileges</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RequiredAccessPrivilegesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRequiredAccessPrivileges()
         * @generated
         */
        EClass REQUIRED_ACCESS_PRIVILEGES = eINSTANCE.getRequiredAccessPrivileges();

        /**
         * The meta object literal for the '<em><b>Privilege Reference</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REQUIRED_ACCESS_PRIVILEGES__PRIVILEGE_REFERENCE =
                eINSTANCE.getRequiredAccessPrivileges_PrivilegeReference();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.VisibleForCaseStatesImpl <em>Visible For Case States</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.VisibleForCaseStatesImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getVisibleForCaseStates()
         * @generated
         */
        EClass VISIBLE_FOR_CASE_STATES = eINSTANCE.getVisibleForCaseStates();

        /**
         * The meta object literal for the '<em><b>Visible For Unset Case State</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VISIBLE_FOR_CASE_STATES__VISIBLE_FOR_UNSET_CASE_STATE =
                eINSTANCE.getVisibleForCaseStates_VisibleForUnsetCaseState();

        /**
         * The meta object literal for the '<em><b>Case State</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VISIBLE_FOR_CASE_STATES__CASE_STATE = eINSTANCE.getVisibleForCaseStates_CaseState();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CaseServiceImpl <em>Case Service</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CaseServiceImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseService()
         * @generated
         */
        EClass CASE_SERVICE = eINSTANCE.getCaseService();

        /**
         * The meta object literal for the '<em><b>Case Class Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_SERVICE__CASE_CLASS_TYPE = eINSTANCE.getCaseService_CaseClassType();

        /**
         * The meta object literal for the '<em><b>Visible For Case States</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_SERVICE__VISIBLE_FOR_CASE_STATES = eINSTANCE.getCaseService_VisibleForCaseStates();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DocumentOperationImpl <em>Document Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DocumentOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDocumentOperation()
         * @generated
         */
        EClass DOCUMENT_OPERATION = eINSTANCE.getDocumentOperation();

        /**
         * The meta object literal for the '<em><b>Case Doc Ref Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION = eINSTANCE.getDocumentOperation_CaseDocRefOperation();

        /**
         * The meta object literal for the '<em><b>Case Doc Find Operations</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS =
                eINSTANCE.getDocumentOperation_CaseDocFindOperations();

        /**
         * The meta object literal for the '<em><b>Link System Document Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION =
                eINSTANCE.getDocumentOperation_LinkSystemDocumentOperation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl <em>Case Doc Ref Operations</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseDocRefOperations()
         * @generated
         */
        EClass CASE_DOC_REF_OPERATIONS = eINSTANCE.getCaseDocRefOperations();

        /**
         * The meta object literal for the '<em><b>Move Case Doc Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION =
                eINSTANCE.getCaseDocRefOperations_MoveCaseDocOperation();

        /**
         * The meta object literal for the '<em><b>Unlink Case Doc Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION =
                eINSTANCE.getCaseDocRefOperations_UnlinkCaseDocOperation();

        /**
         * The meta object literal for the '<em><b>Link Case Doc Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION =
                eINSTANCE.getCaseDocRefOperations_LinkCaseDocOperation();

        /**
         * The meta object literal for the '<em><b>Delete Case Doc Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION =
                eINSTANCE.getCaseDocRefOperations_DeleteCaseDocOperation();

        /**
         * The meta object literal for the '<em><b>Case Document Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD =
                eINSTANCE.getCaseDocRefOperations_CaseDocumentRefField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CaseDocFindOperationsImpl <em>Case Doc Find Operations</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CaseDocFindOperationsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseDocFindOperations()
         * @generated
         */
        EClass CASE_DOC_FIND_OPERATIONS = eINSTANCE.getCaseDocFindOperations();

        /**
         * The meta object literal for the '<em><b>Find By File Name Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_DOC_FIND_OPERATIONS__FIND_BY_FILE_NAME_OPERATION =
                eINSTANCE.getCaseDocFindOperations_FindByFileNameOperation();

        /**
         * The meta object literal for the '<em><b>Find By Query Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CASE_DOC_FIND_OPERATIONS__FIND_BY_QUERY_OPERATION =
                eINSTANCE.getCaseDocFindOperations_FindByQueryOperation();

        /**
         * The meta object literal for the '<em><b>Return Case Doc Refs Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOC_FIND_OPERATIONS__RETURN_CASE_DOC_REFS_FIELD =
                eINSTANCE.getCaseDocFindOperations_ReturnCaseDocRefsField();

        /**
         * The meta object literal for the '<em><b>Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOC_FIND_OPERATIONS__CASE_REF_FIELD = eINSTANCE.getCaseDocFindOperations_CaseRefField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.MoveCaseDocOperationImpl <em>Move Case Doc Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.MoveCaseDocOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getMoveCaseDocOperation()
         * @generated
         */
        EClass MOVE_CASE_DOC_OPERATION = eINSTANCE.getMoveCaseDocOperation();

        /**
         * The meta object literal for the '<em><b>Source Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MOVE_CASE_DOC_OPERATION__SOURCE_CASE_REF_FIELD =
                eINSTANCE.getMoveCaseDocOperation_SourceCaseRefField();

        /**
         * The meta object literal for the '<em><b>Target Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MOVE_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD =
                eINSTANCE.getMoveCaseDocOperation_TargetCaseRefField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.UnlinkCaseDocOperationImpl <em>Unlink Case Doc Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.UnlinkCaseDocOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getUnlinkCaseDocOperation()
         * @generated
         */
        EClass UNLINK_CASE_DOC_OPERATION = eINSTANCE.getUnlinkCaseDocOperation();

        /**
         * The meta object literal for the '<em><b>Source Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNLINK_CASE_DOC_OPERATION__SOURCE_CASE_REF_FIELD =
                eINSTANCE.getUnlinkCaseDocOperation_SourceCaseRefField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.LinkCaseDocOperationImpl <em>Link Case Doc Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.LinkCaseDocOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getLinkCaseDocOperation()
         * @generated
         */
        EClass LINK_CASE_DOC_OPERATION = eINSTANCE.getLinkCaseDocOperation();

        /**
         * The meta object literal for the '<em><b>Target Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LINK_CASE_DOC_OPERATION__TARGET_CASE_REF_FIELD =
                eINSTANCE.getLinkCaseDocOperation_TargetCaseRefField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.LinkSystemDocumentOperationImpl <em>Link System Document Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.LinkSystemDocumentOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getLinkSystemDocumentOperation()
         * @generated
         */
        EClass LINK_SYSTEM_DOCUMENT_OPERATION = eINSTANCE.getLinkSystemDocumentOperation();

        /**
         * The meta object literal for the '<em><b>Document Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LINK_SYSTEM_DOCUMENT_OPERATION__DOCUMENT_ID = eINSTANCE.getLinkSystemDocumentOperation_DocumentId();

        /**
         * The meta object literal for the '<em><b>Return Case Doc Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LINK_SYSTEM_DOCUMENT_OPERATION__RETURN_CASE_DOC_REF_FIELD =
                eINSTANCE.getLinkSystemDocumentOperation_ReturnCaseDocRefField();

        /**
         * The meta object literal for the '<em><b>Case Ref Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LINK_SYSTEM_DOCUMENT_OPERATION__CASE_REF_FIELD =
                eINSTANCE.getLinkSystemDocumentOperation_CaseRefField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DeleteCaseDocOperationImpl <em>Delete Case Doc Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DeleteCaseDocOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeleteCaseDocOperation()
         * @generated
         */
        EClass DELETE_CASE_DOC_OPERATION = eINSTANCE.getDeleteCaseDocOperation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.FindByFileNameOperationImpl <em>Find By File Name Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.FindByFileNameOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFindByFileNameOperation()
         * @generated
         */
        EClass FIND_BY_FILE_NAME_OPERATION = eINSTANCE.getFindByFileNameOperation();

        /**
         * The meta object literal for the '<em><b>File Name Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FIND_BY_FILE_NAME_OPERATION__FILE_NAME_FIELD = eINSTANCE.getFindByFileNameOperation_FileNameField();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.FindByQueryOperationImpl <em>Find By Query Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.FindByQueryOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFindByQueryOperation()
         * @generated
         */
        EClass FIND_BY_QUERY_OPERATION = eINSTANCE.getFindByQueryOperation();

        /**
         * The meta object literal for the '<em><b>Case Document Query Expression</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FIND_BY_QUERY_OPERATION__CASE_DOCUMENT_QUERY_EXPRESSION =
                eINSTANCE.getFindByQueryOperation_CaseDocumentQueryExpression();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl <em>Case Document Query Expression</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCaseDocumentQueryExpression()
         * @generated
         */
        EClass CASE_DOCUMENT_QUERY_EXPRESSION = eINSTANCE.getCaseDocumentQueryExpression();

        /**
         * The meta object literal for the '<em><b>Query Expression Join Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE =
                eINSTANCE.getCaseDocumentQueryExpression_QueryExpressionJoinType();

        /**
         * The meta object literal for the '<em><b>Open Bracket Count</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT =
                eINSTANCE.getCaseDocumentQueryExpression_OpenBracketCount();

        /**
         * The meta object literal for the '<em><b>Cmis Property</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY =
                eINSTANCE.getCaseDocumentQueryExpression_CmisProperty();

        /**
         * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR = eINSTANCE.getCaseDocumentQueryExpression_Operator();

        /**
         * The meta object literal for the '<em><b>Process Data Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD =
                eINSTANCE.getCaseDocumentQueryExpression_ProcessDataField();

        /**
         * The meta object literal for the '<em><b>Close Bracket Count</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT =
                eINSTANCE.getCaseDocumentQueryExpression_CloseBracketCount();

        /**
         * The meta object literal for the '<em><b>Cmis Document Property Selected</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED =
                eINSTANCE.getCaseDocumentQueryExpression_CmisDocumentPropertySelected();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ServiceProcessConfigurationImpl <em>Service Process Configuration</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ServiceProcessConfigurationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getServiceProcessConfiguration()
         * @generated
         */
        EClass SERVICE_PROCESS_CONFIGURATION = eINSTANCE.getServiceProcessConfiguration();

        /**
         * The meta object literal for the '<em><b>Deploy To Process Runtime</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PROCESS_RUNTIME =
                eINSTANCE.getServiceProcessConfiguration_DeployToProcessRuntime();

        /**
         * The meta object literal for the '<em><b>Deploy To Pageflow Runtime</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVICE_PROCESS_CONFIGURATION__DEPLOY_TO_PAGEFLOW_RUNTIME =
                eINSTANCE.getServiceProcessConfiguration_DeployToPageflowRuntime();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl <em>Script Data Mapper</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.ScriptDataMapperImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getScriptDataMapper()
         * @generated
         */
        EClass SCRIPT_DATA_MAPPER = eINSTANCE.getScriptDataMapper();

        /**
         * The meta object literal for the '<em><b>Mapper Context</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT_DATA_MAPPER__MAPPER_CONTEXT = eINSTANCE.getScriptDataMapper_MapperContext();

        /**
         * The meta object literal for the '<em><b>Mapping Direction</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT_DATA_MAPPER__MAPPING_DIRECTION = eINSTANCE.getScriptDataMapper_MappingDirection();

        /**
         * The meta object literal for the '<em><b>Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_DATA_MAPPER__DATA_MAPPINGS = eINSTANCE.getScriptDataMapper_DataMappings();

        /**
         * The meta object literal for the '<em><b>Unmapped Scripts</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS = eINSTANCE.getScriptDataMapper_UnmappedScripts();

        /**
         * The meta object literal for the '<em><b>Array Inflation Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE = eINSTANCE.getScriptDataMapper_ArrayInflationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.DataMapperArrayInflationImpl <em>Data Mapper Array Inflation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.DataMapperArrayInflationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDataMapperArrayInflation()
         * @generated
         */
        EClass DATA_MAPPER_ARRAY_INFLATION = eINSTANCE.getDataMapperArrayInflation();

        /**
         * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_MAPPER_ARRAY_INFLATION__PATH = eINSTANCE.getDataMapperArrayInflation_Path();

        /**
         * The meta object literal for the '<em><b>Mapping Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_MAPPER_ARRAY_INFLATION__MAPPING_TYPE = eINSTANCE.getDataMapperArrayInflation_MappingType();

        /**
         * The meta object literal for the '<em><b>Contributor Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_MAPPER_ARRAY_INFLATION__CONTRIBUTOR_ID = eINSTANCE.getDataMapperArrayInflation_ContributorId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionImpl <em>Like Mapping Exclusion</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getLikeMappingExclusion()
         * @generated
         */
        EClass LIKE_MAPPING_EXCLUSION = eINSTANCE.getLikeMappingExclusion();

        /**
         * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LIKE_MAPPING_EXCLUSION__PATH = eINSTANCE.getLikeMappingExclusion_Path();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionsImpl <em>Like Mapping Exclusions</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionsImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getLikeMappingExclusions()
         * @generated
         */
        EClass LIKE_MAPPING_EXCLUSIONS = eINSTANCE.getLikeMappingExclusions();

        /**
         * The meta object literal for the '<em><b>Exclusions</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS = eINSTANCE.getLikeMappingExclusions_Exclusions();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.impl.RestServiceOperationImpl <em>Rest Service Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.impl.RestServiceOperationImpl
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRestServiceOperation()
         * @generated
         */
        EClass REST_SERVICE_OPERATION = eINSTANCE.getRestServiceOperation();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REST_SERVICE_OPERATION__LOCATION = eINSTANCE.getRestServiceOperation_Location();

        /**
         * The meta object literal for the '<em><b>Method Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REST_SERVICE_OPERATION__METHOD_ID = eINSTANCE.getRestServiceOperation_MethodId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.AllocationStrategyType <em>Allocation Strategy Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.AllocationStrategyType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAllocationStrategyType()
         * @generated
         */
        EEnum ALLOCATION_STRATEGY_TYPE = eINSTANCE.getAllocationStrategyType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.AllocationType <em>Allocation Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.AllocationType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAllocationType()
         * @generated
         */
        EEnum ALLOCATION_TYPE = eINSTANCE.getAllocationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.AuditEventType <em>Audit Event Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.AuditEventType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAuditEventType()
         * @generated
         */
        EEnum AUDIT_EVENT_TYPE = eINSTANCE.getAuditEventType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.CorrelationMode <em>Correlation Mode</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.CorrelationMode
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCorrelationMode()
         * @generated
         */
        EEnum CORRELATION_MODE = eINSTANCE.getCorrelationMode();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerType <em>Error Thrower Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.ErrorThrowerType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getErrorThrowerType()
         * @generated
         */
        EEnum ERROR_THROWER_TYPE = eINSTANCE.getErrorThrowerType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy <em>Event Handler Flow Strategy</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getEventHandlerFlowStrategy()
         * @generated
         */
        EEnum EVENT_HANDLER_FLOW_STRATEGY = eINSTANCE.getEventHandlerFlowStrategy();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.FlowRoutingStyle <em>Flow Routing Style</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.FlowRoutingStyle
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFlowRoutingStyle()
         * @generated
         */
        EEnum FLOW_ROUTING_STYLE = eINSTANCE.getFlowRoutingStyle();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.FormImplementationType <em>Form Implementation Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.FormImplementationType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getFormImplementationType()
         * @generated
         */
        EEnum FORM_IMPLEMENTATION_TYPE = eINSTANCE.getFormImplementationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.MaxRetryActionType <em>Max Retry Action Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.MaxRetryActionType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getMaxRetryActionType()
         * @generated
         */
        EEnum MAX_RETRY_ACTION_TYPE = eINSTANCE.getMaxRetryActionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.RescheduleDurationType <em>Reschedule Duration Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.RescheduleDurationType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRescheduleDurationType()
         * @generated
         */
        EEnum RESCHEDULE_DURATION_TYPE = eINSTANCE.getRescheduleDurationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType <em>Reschedule Timer Selection Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getRescheduleTimerSelectionType()
         * @generated
         */
        EEnum RESCHEDULE_TIMER_SELECTION_TYPE = eINSTANCE.getRescheduleTimerSelectionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.SecurityPolicy <em>Security Policy</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.SecurityPolicy
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSecurityPolicy()
         * @generated
         */
        EEnum SECURITY_POLICY = eINSTANCE.getSecurityPolicy();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.SoapBindingStyle <em>Soap Binding Style</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSoapBindingStyle()
         * @generated
         */
        EEnum SOAP_BINDING_STYLE = eINSTANCE.getSoapBindingStyle();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.SubProcessStartStrategy <em>Sub Process Start Strategy</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.SubProcessStartStrategy
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSubProcessStartStrategy()
         * @generated
         */
        EEnum SUB_PROCESS_START_STRATEGY = eINSTANCE.getSubProcessStartStrategy();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.SystemErrorActionType <em>System Error Action Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.SystemErrorActionType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSystemErrorActionType()
         * @generated
         */
        EEnum SYSTEM_ERROR_ACTION_TYPE = eINSTANCE.getSystemErrorActionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverrideType <em>Validation Issue Override Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.ValidationIssueOverrideType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getValidationIssueOverrideType()
         * @generated
         */
        EEnum VALIDATION_ISSUE_OVERRIDE_TYPE = eINSTANCE.getValidationIssueOverrideType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.Visibility <em>Visibility</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.Visibility
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getVisibility()
         * @generated
         */
        EEnum VISIBILITY = eINSTANCE.getVisibility();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.DeliveryMode <em>Delivery Mode</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.DeliveryMode
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDeliveryMode()
         * @generated
         */
        EEnum DELIVERY_MODE = eINSTANCE.getDeliveryMode();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.XpdModelType <em>Xpd Model Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.XpdModelType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdModelType()
         * @generated
         */
        EEnum XPD_MODEL_TYPE = eINSTANCE.getXpdModelType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.AdHocExecutionTypeType <em>Ad Hoc Execution Type Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.AdHocExecutionTypeType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAdHocExecutionTypeType()
         * @generated
         */
        EEnum AD_HOC_EXECUTION_TYPE_TYPE = eINSTANCE.getAdHocExecutionTypeType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.QueryExpressionJoinType <em>Query Expression Join Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.QueryExpressionJoinType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getQueryExpressionJoinType()
         * @generated
         */
        EEnum QUERY_EXPRESSION_JOIN_TYPE = eINSTANCE.getQueryExpressionJoinType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.CMISQueryOperator <em>CMIS Query Operator</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.CMISQueryOperator
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getCMISQueryOperator()
         * @generated
         */
        EEnum CMIS_QUERY_OPERATOR = eINSTANCE.getCMISQueryOperator();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.AsyncExecutionMode <em>Async Execution Mode</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.AsyncExecutionMode
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAsyncExecutionMode()
         * @generated
         */
        EEnum ASYNC_EXECUTION_MODE = eINSTANCE.getAsyncExecutionMode();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.SignalType <em>Signal Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.SignalType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSignalType()
         * @generated
         */
        EEnum SIGNAL_TYPE = eINSTANCE.getSignalType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.XpdInterfaceType <em>Xpd Interface Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.XpdInterfaceType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getXpdInterfaceType()
         * @generated
         */
        EEnum XPD_INTERFACE_TYPE = eINSTANCE.getXpdInterfaceType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.DataMapperArrayInflationType <em>Data Mapper Array Inflation Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.DataMapperArrayInflationType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getDataMapperArrayInflationType()
         * @generated
         */
        EEnum DATA_MAPPER_ARRAY_INFLATION_TYPE = eINSTANCE.getDataMapperArrayInflationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdExtension.BusinessServicePublishType <em>Business Service Publish Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.BusinessServicePublishType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getBusinessServicePublishType()
         * @generated
         */
        EEnum BUSINESS_SERVICE_PUBLISH_TYPE = eINSTANCE.getBusinessServicePublishType();

        /**
         * The meta object literal for the '<em>Audit Event Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.AuditEventType
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getAuditEventTypeObject()
         * @generated
         */
        EDataType AUDIT_EVENT_TYPE_OBJECT = eINSTANCE.getAuditEventTypeObject();

        /**
         * The meta object literal for the '<em>Security Policy Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.SecurityPolicy
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSecurityPolicyObject()
         * @generated
         */
        EDataType SECURITY_POLICY_OBJECT = eINSTANCE.getSecurityPolicyObject();

        /**
         * The meta object literal for the '<em>Soap Binding Style Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
         * @see com.tibco.xpd.xpdExtension.impl.XpdExtensionPackageImpl#getSoapBindingStyleObject()
         * @generated
         */
        EDataType SOAP_BINDING_STYLE_OBJECT = eINSTANCE.getSoapBindingStyleObject();

    }

} //XpdExtensionPackage
