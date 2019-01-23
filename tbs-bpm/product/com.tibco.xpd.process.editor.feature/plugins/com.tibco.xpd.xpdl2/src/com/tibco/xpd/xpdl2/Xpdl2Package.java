/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

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
 * @see com.tibco.xpd.xpdl2.Xpdl2Factory
 * @model kind="package"
 * @generated
 */
public interface Xpdl2Package extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "xpdl2"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.wfmc.org/2008/XPDL2.1"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "xpdl2"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    Xpdl2Package eINSTANCE = com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.UniqueIdElementImpl <em>Unique Id Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.UniqueIdElementImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getUniqueIdElement()
     * @generated
     */
    int UNIQUE_ID_ELEMENT = 157;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIQUE_ID_ELEMENT__ID = 0;

    /**
     * The number of structural features of the '<em>Unique Id Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIQUE_ID_ELEMENT_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.FlowContainer <em>Flow Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.FlowContainer
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFlowContainer()
     * @generated
     */
    int FLOW_CONTAINER = 51;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl <em>Activity Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ActivitySetImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getActivitySet()
     * @generated
     */
    int ACTIVITY_SET = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.NamedElementImpl <em>Named Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.NamedElementImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getNamedElement()
     * @generated
     */
    int NAMED_ELEMENT = 82;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__ID = UNIQUE_ID_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__OTHER_ATTRIBUTES = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__NAME = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Named Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT_FEATURE_COUNT = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Activities</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__ACTIVITIES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__TRANSITIONS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ad Hoc</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__AD_HOC = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Ad Hoc Completion Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION =
            NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Ad Hoc Ordering</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__AD_HOC_ORDERING = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Default Start Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID =
            NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Process</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET__PROCESS = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Activity Set</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SET_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ActivityImpl <em>Activity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ActivityImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getActivity()
     * @generated
     */
    int ACTIVITY = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__EXTENDED_ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Node Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__NODE_GRAPHICS_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__DESCRIPTION = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Data Fields</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__DATA_FIELDS = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Limit</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__LIMIT = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Route</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__ROUTE = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__IMPLEMENTATION = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Block Activity</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__BLOCK_ACTIVITY = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Event</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__EVENT = NAMED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Transaction</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__TRANSACTION = NAMED_ELEMENT_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Performer</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__PERFORMER = NAMED_ELEMENT_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Performers</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__PERFORMERS = NAMED_ELEMENT_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__PRIORITY = NAMED_ELEMENT_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Deadline</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__DEADLINE = NAMED_ELEMENT_FEATURE_COUNT + 14;

    /**
     * The feature id for the '<em><b>Simulation Information</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__SIMULATION_INFORMATION = NAMED_ELEMENT_FEATURE_COUNT + 15;

    /**
     * The feature id for the '<em><b>Icon</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__ICON = NAMED_ELEMENT_FEATURE_COUNT + 16;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__DOCUMENTATION = NAMED_ELEMENT_FEATURE_COUNT + 17;

    /**
     * The feature id for the '<em><b>Transition Restrictions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__TRANSITION_RESTRICTIONS = NAMED_ELEMENT_FEATURE_COUNT + 18;

    /**
     * The feature id for the '<em><b>Input Sets</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__INPUT_SETS = NAMED_ELEMENT_FEATURE_COUNT + 19;

    /**
     * The feature id for the '<em><b>Output Sets</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__OUTPUT_SETS = NAMED_ELEMENT_FEATURE_COUNT + 20;

    /**
     * The feature id for the '<em><b>Io Rules</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__IO_RULES = NAMED_ELEMENT_FEATURE_COUNT + 21;

    /**
     * The feature id for the '<em><b>Loop</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__LOOP = NAMED_ELEMENT_FEATURE_COUNT + 22;

    /**
     * The feature id for the '<em><b>Assignments</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__ASSIGNMENTS = NAMED_ELEMENT_FEATURE_COUNT + 23;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 24;

    /**
     * The feature id for the '<em><b>Extensions</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__EXTENSIONS = NAMED_ELEMENT_FEATURE_COUNT + 25;

    /**
     * The feature id for the '<em><b>Finish Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__FINISH_MODE = NAMED_ELEMENT_FEATURE_COUNT + 26;

    /**
     * The feature id for the '<em><b>Is ATransaction</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__IS_ATRANSACTION = NAMED_ELEMENT_FEATURE_COUNT + 27;

    /**
     * The feature id for the '<em><b>Start Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__START_ACTIVITY = NAMED_ELEMENT_FEATURE_COUNT + 28;

    /**
     * The feature id for the '<em><b>Start Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__START_MODE = NAMED_ELEMENT_FEATURE_COUNT + 29;

    /**
     * The feature id for the '<em><b>Start Quantity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__START_QUANTITY = NAMED_ELEMENT_FEATURE_COUNT + 30;

    /**
     * The feature id for the '<em><b>Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__STATUS = NAMED_ELEMENT_FEATURE_COUNT + 31;

    /**
     * The feature id for the '<em><b>Flow Container</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__FLOW_CONTAINER = NAMED_ELEMENT_FEATURE_COUNT + 32;

    /**
     * The feature id for the '<em><b>Process</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__PROCESS = NAMED_ELEMENT_FEATURE_COUNT + 33;

    /**
     * The feature id for the '<em><b>Is For Compensation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__IS_FOR_COMPENSATION = NAMED_ELEMENT_FEATURE_COUNT + 34;

    /**
     * The feature id for the '<em><b>Completion Quantity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__COMPLETION_QUANTITY = NAMED_ELEMENT_FEATURE_COUNT + 35;

    /**
     * The number of structural features of the '<em>Activity</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 36;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl <em>Application Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getApplicationType()
     * @generated
     */
    int APPLICATION_TYPE = 2;

    /**
     * The feature id for the '<em><b>Ejb</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE__EJB = 0;

    /**
     * The feature id for the '<em><b>Pojo</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE__POJO = 1;

    /**
     * The feature id for the '<em><b>Xslt</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE__XSLT = 2;

    /**
     * The feature id for the '<em><b>Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE__SCRIPT = 3;

    /**
     * The feature id for the '<em><b>Web Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE__WEB_SERVICE = 4;

    /**
     * The feature id for the '<em><b>Business Rule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE__BUSINESS_RULE = 5;

    /**
     * The feature id for the '<em><b>Form</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE__FORM = 6;

    /**
     * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE__ANY_ATTRIBUTE = 7;

    /**
     * The number of structural features of the '<em>Application Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_TYPE_FEATURE_COUNT = 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ApplicationImpl <em>Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ApplicationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getApplication()
     * @generated
     */
    int APPLICATION = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION__EXTENDED_ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Formal Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION__FORMAL_PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION__DESCRIPTION = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION__EXTERNAL_REFERENCE = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Application</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ExtendedAttributesContainer <em>Extended Attributes Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ExtendedAttributesContainer
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExtendedAttributesContainer()
     * @generated
     */
    int EXTENDED_ATTRIBUTES_CONTAINER = 48;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ArrayTypeImpl <em>Array Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ArrayTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArrayType()
     * @generated
     */
    int ARRAY_TYPE = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl <em>Artifact</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ArtifactImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArtifact()
     * @generated
     */
    int ARTIFACT = 6;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.GraphicalNode <em>Graphical Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.GraphicalNode
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGraphicalNode()
     * @generated
     */
    int GRAPHICAL_NODE = 55;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.GraphicalConnector <em>Graphical Connector</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.GraphicalConnector
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGraphicalConnector()
     * @generated
     */
    int GRAPHICAL_CONNECTOR = 56;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.AssignmentImpl <em>Assignment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.AssignmentImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssignment()
     * @generated
     */
    int ASSIGNMENT = 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.AssociationImpl <em>Association</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.AssociationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssociation()
     * @generated
     */
    int ASSOCIATION = 10;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.BasicTypeImpl <em>Basic Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.BasicTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBasicType()
     * @generated
     */
    int BASIC_TYPE = 11;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.BlockActivityImpl <em>Block Activity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.BlockActivityImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBlockActivity()
     * @generated
     */
    int BLOCK_ACTIVITY = 12;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.BusinessRuleApplicationImpl <em>Business Rule Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.BusinessRuleApplicationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBusinessRuleApplication()
     * @generated
     */
    int BUSINESS_RULE_APPLICATION = 13;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.CategoryImpl <em>Category</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.CategoryImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCategory()
     * @generated
     */
    int CATEGORY = 14;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ClassImpl <em>Class</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ClassImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getClass_()
     * @generated
     */
    int CLASS = 15;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.CodepageImpl <em>Codepage</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.CodepageImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCodepage()
     * @generated
     */
    int CODEPAGE = 16;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ConditionImpl <em>Condition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ConditionImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCondition()
     * @generated
     */
    int CONDITION = 17;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ConformanceClassImpl <em>Conformance Class</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ConformanceClassImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getConformanceClass()
     * @generated
     */
    int CONFORMANCE_CLASS = 18;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl <em>Connector Graphics Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getConnectorGraphicsInfo()
     * @generated
     */
    int CONNECTOR_GRAPHICS_INFO = 19;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.CoordinatesImpl <em>Coordinates</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.CoordinatesImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCoordinates()
     * @generated
     */
    int COORDINATES = 20;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.CostImpl <em>Cost</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.CostImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCost()
     * @generated
     */
    int COST = 21;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.CostUnitImpl <em>Cost Unit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.CostUnitImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCostUnit()
     * @generated
     */
    int COST_UNIT = 23;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.CountryKeyImpl <em>Country Key</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.CountryKeyImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCountryKey()
     * @generated
     */
    int COUNTRY_KEY = 24;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl <em>Data Field</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DataFieldImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataField()
     * @generated
     */
    int DATA_FIELD = 25;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DataMappingImpl <em>Data Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DataMappingImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataMapping()
     * @generated
     */
    int DATA_MAPPING = 27;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DataObjectImpl <em>Data Object</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DataObjectImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataObject()
     * @generated
     */
    int DATA_OBJECT = 28;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DataTypeImpl <em>Data Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DataTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataType()
     * @generated
     */
    int DATA_TYPE = 29;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.Group <em>Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.Group
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGroup()
     * @generated
     */
    int GROUP = 57;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.OtherAttributesContainer <em>Other Attributes Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.OtherAttributesContainer
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOtherAttributesContainer()
     * @generated
     */
    int OTHER_ATTRIBUTES_CONTAINER = 86;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DeadlineImpl <em>Deadline</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DeadlineImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeadline()
     * @generated
     */
    int DEADLINE = 30;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DeclaredTypeImpl <em>Declared Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DeclaredTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeclaredType()
     * @generated
     */
    int DECLARED_TYPE = 31;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DescriptionImpl <em>Description</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DescriptionImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDescription()
     * @generated
     */
    int DESCRIPTION = 35;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DocumentationImpl <em>Documentation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DocumentationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDocumentation()
     * @generated
     */
    int DOCUMENTATION = 36;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DocumentRootImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 37;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DurationImpl <em>Duration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DurationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDuration()
     * @generated
     */
    int DURATION = 38;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.EjbApplicationImpl <em>Ejb Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.EjbApplicationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEjbApplication()
     * @generated
     */
    int EJB_APPLICATION = 39;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.EndEventImpl <em>End Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.EndEventImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEndEvent()
     * @generated
     */
    int END_EVENT = 40;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.EndPointImpl <em>End Point</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.EndPointImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEndPoint()
     * @generated
     */
    int END_POINT = 41;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.EnumerationTypeImpl <em>Enumeration Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.EnumerationTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEnumerationType()
     * @generated
     */
    int ENUMERATION_TYPE = 42;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.EnumerationValueImpl <em>Enumeration Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.EnumerationValueImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEnumerationValue()
     * @generated
     */
    int ENUMERATION_VALUE = 43;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.EventImpl <em>Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.EventImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEvent()
     * @generated
     */
    int EVENT = 44;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ExceptionNameImpl <em>Exception Name</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ExceptionNameImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExceptionName()
     * @generated
     */
    int EXCEPTION_NAME = 45;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ExpressionImpl <em>Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ExpressionImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExpression()
     * @generated
     */
    int EXPRESSION = 46;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ExtendedAttributeImpl <em>Extended Attribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ExtendedAttributeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExtendedAttribute()
     * @generated
     */
    int EXTENDED_ATTRIBUTE = 47;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ExternalPackageImpl <em>External Package</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ExternalPackageImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExternalPackage()
     * @generated
     */
    int EXTERNAL_PACKAGE = 49;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ExternalReferenceImpl <em>External Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ExternalReferenceImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExternalReference()
     * @generated
     */
    int EXTERNAL_REFERENCE = 50;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData <em>Process Relevant Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ProcessRelevantData
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcessRelevantData()
     * @generated
     */
    int PROCESS_RELEVANT_DATA = 111;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl <em>Formal Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.FormalParameterImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFormalParameter()
     * @generated
     */
    int FORMAL_PARAMETER = 52;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.FormLayoutImpl <em>Form Layout</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.FormLayoutImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFormLayout()
     * @generated
     */
    int FORM_LAYOUT = 53;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.FormApplicationImpl <em>Form Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.FormApplicationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFormApplication()
     * @generated
     */
    int FORM_APPLICATION = 54;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.HomeClassImpl <em>Home Class</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.HomeClassImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getHomeClass()
     * @generated
     */
    int HOME_CLASS = 58;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.IconImpl <em>Icon</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.IconImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIcon()
     * @generated
     */
    int ICON = 59;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ImplementationImpl <em>Implementation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ImplementationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getImplementation()
     * @generated
     */
    int IMPLEMENTATION = 60;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.InputSetImpl <em>Input Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.InputSetImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getInputSet()
     * @generated
     */
    int INPUT_SET = 61;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.InputImpl <em>Input</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.InputImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getInput()
     * @generated
     */
    int INPUT = 62;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl <em>Intermediate Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.IntermediateEventImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIntermediateEvent()
     * @generated
     */
    int INTERMEDIATE_EVENT = 63;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.IORulesImpl <em>IO Rules</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.IORulesImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIORules()
     * @generated
     */
    int IO_RULES = 64;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.JndiNameImpl <em>Jndi Name</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.JndiNameImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getJndiName()
     * @generated
     */
    int JNDI_NAME = 65;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.JoinImpl <em>Join</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.JoinImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getJoin()
     * @generated
     */
    int JOIN = 66;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.LaneImpl <em>Lane</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.LaneImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLane()
     * @generated
     */
    int LANE = 68;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.LengthImpl <em>Length</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.LengthImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLength()
     * @generated
     */
    int LENGTH = 69;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.LimitImpl <em>Limit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.LimitImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLimit()
     * @generated
     */
    int LIMIT = 70;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ListTypeImpl <em>List Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ListTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getListType()
     * @generated
     */
    int LIST_TYPE = 71;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.LocationImpl <em>Location</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.LocationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLocation()
     * @generated
     */
    int LOCATION = 72;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl <em>Loop Multi Instance</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoopMultiInstance()
     * @generated
     */
    int LOOP_MULTI_INSTANCE = 73;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.LoopStandardImpl <em>Loop Standard</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.LoopStandardImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoopStandard()
     * @generated
     */
    int LOOP_STANDARD = 74;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.LoopImpl <em>Loop</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.LoopImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoop()
     * @generated
     */
    int LOOP = 75;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.MemberImpl <em>Member</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.MemberImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMember()
     * @generated
     */
    int MEMBER = 76;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl <em>Message Flow</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.MessageFlowImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMessageFlow()
     * @generated
     */
    int MESSAGE_FLOW = 77;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.MessageImpl <em>Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.MessageImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMessage()
     * @generated
     */
    int MESSAGE = 78;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.MethodImpl <em>Method</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.MethodImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMethod()
     * @generated
     */
    int METHOD = 79;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.MyRoleImpl <em>My Role</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.MyRoleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMyRole()
     * @generated
     */
    int MY_ROLE = 81;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl <em>Node Graphics Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getNodeGraphicsInfo()
     * @generated
     */
    int NODE_GRAPHICS_INFO = 83;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.NoImpl <em>No</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.NoImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getNo()
     * @generated
     */
    int NO = 84;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ObjectImpl <em>Object</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ObjectImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getObject()
     * @generated
     */
    int OBJECT = 85;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.OutputSetImpl <em>Output Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.OutputSetImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOutputSet()
     * @generated
     */
    int OUTPUT_SET = 88;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.OutputImpl <em>Output</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.OutputImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOutput()
     * @generated
     */
    int OUTPUT = 89;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.DescribedElement <em>Described Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.DescribedElement
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDescribedElement()
     * @generated
     */
    int DESCRIBED_ELEMENT = 34;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl <em>Package Header</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PackageHeaderImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPackageHeader()
     * @generated
     */
    int PACKAGE_HEADER = 90;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PackageImpl <em>Package</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PackageImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPackage()
     * @generated
     */
    int PACKAGE = 91;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ApplicationsContainer <em>Applications Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ApplicationsContainer
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getApplicationsContainer()
     * @generated
     */
    int APPLICATIONS_CONTAINER = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ParticipantsContainer <em>Participants Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ParticipantsContainer
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getParticipantsContainer()
     * @generated
     */
    int PARTICIPANTS_CONTAINER = 94;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DataFieldsContainerImpl <em>Data Fields Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DataFieldsContainerImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataFieldsContainer()
     * @generated
     */
    int DATA_FIELDS_CONTAINER = 26;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ParticipantImpl <em>Participant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ParticipantImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getParticipant()
     * @generated
     */
    int PARTICIPANT = 95;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ParticipantTypeElemImpl <em>Participant Type Elem</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ParticipantTypeElemImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getParticipantTypeElem()
     * @generated
     */
    int PARTICIPANT_TYPE_ELEM = 96;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PartnerLinkImpl <em>Partner Link</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PartnerLinkImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPartnerLink()
     * @generated
     */
    int PARTNER_LINK = 97;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PartnerLinkTypeImpl <em>Partner Link Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PartnerLinkTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPartnerLinkType()
     * @generated
     */
    int PARTNER_LINK_TYPE = 98;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PartnerRoleImpl <em>Partner Role</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PartnerRoleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPartnerRole()
     * @generated
     */
    int PARTNER_ROLE = 99;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PartnerImpl <em>Partner</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PartnerImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPartner()
     * @generated
     */
    int PARTNER = 100;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PerformerImpl <em>Performer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PerformerImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPerformer()
     * @generated
     */
    int PERFORMER = 101;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PojoApplicationImpl <em>Pojo Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PojoApplicationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPojoApplication()
     * @generated
     */
    int POJO_APPLICATION = 103;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PoolImpl <em>Pool</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PoolImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPool()
     * @generated
     */
    int POOL = 104;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PrecisionImpl <em>Precision</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PrecisionImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPrecision()
     * @generated
     */
    int PRECISION = 105;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PriorityImpl <em>Priority</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PriorityImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPriority()
     * @generated
     */
    int PRIORITY = 106;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PriorityUnitImpl <em>Priority Unit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PriorityUnitImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPriorityUnit()
     * @generated
     */
    int PRIORITY_UNIT = 107;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl <em>Process Header</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcessHeader()
     * @generated
     */
    int PROCESS_HEADER = 108;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ProcessImpl <em>Process</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ProcessImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcess()
     * @generated
     */
    int PROCESS = 109;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.FormalParametersContainer <em>Formal Parameters Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.FormalParametersContainer
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFormalParametersContainer()
     * @generated
     */
    int FORMAL_PARAMETERS_CONTAINER = 110;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.AssigmentsContainer <em>Assigments Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AssigmentsContainer
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssigmentsContainer()
     * @generated
     */
    int ASSIGMENTS_CONTAINER = 9;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.RecordTypeImpl <em>Record Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.RecordTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRecordType()
     * @generated
     */
    int RECORD_TYPE = 113;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl <em>Redefinable Header</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRedefinableHeader()
     * @generated
     */
    int REDEFINABLE_HEADER = 114;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ReferenceImpl <em>Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ReferenceImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getReference()
     * @generated
     */
    int REFERENCE = 115;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ResponsibleImpl <em>Responsible</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ResponsibleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResponsible()
     * @generated
     */
    int RESPONSIBLE = 117;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ResultErrorImpl <em>Result Error</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ResultErrorImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResultError()
     * @generated
     */
    int RESULT_ERROR = 118;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ResultMultipleImpl <em>Result Multiple</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ResultMultipleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResultMultiple()
     * @generated
     */
    int RESULT_MULTIPLE = 119;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.RoleImpl <em>Role</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.RoleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRole()
     * @generated
     */
    int ROLE = 120;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.RouteImpl <em>Route</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.RouteImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRoute()
     * @generated
     */
    int ROUTE = 121;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.RuleNameImpl <em>Rule Name</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.RuleNameImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRuleName()
     * @generated
     */
    int RULE_NAME = 122;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.RuleImpl <em>Rule</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.RuleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRule()
     * @generated
     */
    int RULE = 123;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ScaleImpl <em>Scale</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ScaleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getScale()
     * @generated
     */
    int SCALE = 124;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.SchemaImpl <em>Schema</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.SchemaImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSchema()
     * @generated
     */
    int SCHEMA = 125;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ScriptImpl <em>Script</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ScriptImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getScript()
     * @generated
     */
    int SCRIPT = 126;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ServiceImpl <em>Service</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ServiceImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getService()
     * @generated
     */
    int SERVICE = 127;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.SimulationInformationImpl <em>Simulation Information</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.SimulationInformationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSimulationInformation()
     * @generated
     */
    int SIMULATION_INFORMATION = 128;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.SplitImpl <em>Split</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.SplitImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSplit()
     * @generated
     */
    int SPLIT = 129;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.StartEventImpl <em>Start Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.StartEventImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStartEvent()
     * @generated
     */
    int START_EVENT = 130;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl <em>Sub Flow</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.SubFlowImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSubFlow()
     * @generated
     */
    int SUB_FLOW = 131;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskApplicationImpl <em>Task Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskApplicationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskApplication()
     * @generated
     */
    int TASK_APPLICATION = 132;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskManualImpl <em>Task Manual</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskManualImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskManual()
     * @generated
     */
    int TASK_MANUAL = 133;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskReceiveImpl <em>Task Receive</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskReceiveImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskReceive()
     * @generated
     */
    int TASK_RECEIVE = 134;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskReferenceImpl <em>Task Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskReferenceImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskReference()
     * @generated
     */
    int TASK_REFERENCE = 135;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskScriptImpl <em>Task Script</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskScriptImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskScript()
     * @generated
     */
    int TASK_SCRIPT = 136;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskSendImpl <em>Task Send</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskSendImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskSend()
     * @generated
     */
    int TASK_SEND = 137;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskServiceImpl <em>Task Service</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskServiceImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskService()
     * @generated
     */
    int TASK_SERVICE = 138;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskImpl <em>Task</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTask()
     * @generated
     */
    int TASK = 139;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl <em>Task User</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TaskUserImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskUser()
     * @generated
     */
    int TASK_USER = 140;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TimeEstimationImpl <em>Time Estimation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TimeEstimationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTimeEstimation()
     * @generated
     */
    int TIME_ESTIMATION = 141;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TransactionImpl <em>Transaction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TransactionImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransaction()
     * @generated
     */
    int TRANSACTION = 142;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TransitionRefImpl <em>Transition Ref</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TransitionRefImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransitionRef()
     * @generated
     */
    int TRANSITION_REF = 143;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TransitionRestrictionImpl <em>Transition Restriction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TransitionRestrictionImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransitionRestriction()
     * @generated
     */
    int TRANSITION_RESTRICTION = 144;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TransitionImpl <em>Transition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TransitionImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransition()
     * @generated
     */
    int TRANSITION = 145;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl <em>Trigger Intermediate Multiple</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerIntermediateMultiple()
     * @generated
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE = 146;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerMultipleImpl <em>Trigger Multiple</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerMultipleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerMultiple()
     * @generated
     */
    int TRIGGER_MULTIPLE = 147;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultLinkImpl <em>Trigger Result Link</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerResultLinkImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultLink()
     * @generated
     */
    int TRIGGER_RESULT_LINK = 151;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl <em>Trigger Result Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultMessage()
     * @generated
     */
    int TRIGGER_RESULT_MESSAGE = 152;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerTimerImpl <em>Trigger Timer</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerTimerImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerTimer()
     * @generated
     */
    int TRIGGER_TIMER = 154;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl <em>Type Declaration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTypeDeclaration()
     * @generated
     */
    int TYPE_DECLARATION = 155;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.UnionTypeImpl <em>Union Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.UnionTypeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getUnionType()
     * @generated
     */
    int UNION_TYPE = 156;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ValidFromImpl <em>Valid From</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ValidFromImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getValidFrom()
     * @generated
     */
    int VALID_FROM = 158;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ValidToImpl <em>Valid To</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ValidToImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getValidTo()
     * @generated
     */
    int VALID_TO = 159;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.VendorExtensionsImpl <em>Vendor Extensions</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.VendorExtensionsImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getVendorExtensions()
     * @generated
     */
    int VENDOR_EXTENSIONS = 160;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.VendorExtensionImpl <em>Vendor Extension</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.VendorExtensionImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getVendorExtension()
     * @generated
     */
    int VENDOR_EXTENSION = 161;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.WaitingTimeImpl <em>Waiting Time</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.WaitingTimeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWaitingTime()
     * @generated
     */
    int WAITING_TIME = 162;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.WebServiceFaultCatchImpl <em>Web Service Fault Catch</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.WebServiceFaultCatchImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWebServiceFaultCatch()
     * @generated
     */
    int WEB_SERVICE_FAULT_CATCH = 163;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.WebServiceOperationImpl <em>Web Service Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.WebServiceOperationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWebServiceOperation()
     * @generated
     */
    int WEB_SERVICE_OPERATION = 164;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.WebServiceApplicationImpl <em>Web Service Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.WebServiceApplicationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWebServiceApplication()
     * @generated
     */
    int WEB_SERVICE_APPLICATION = 165;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.WorkingTimeImpl <em>Working Time</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.WorkingTimeImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWorkingTime()
     * @generated
     */
    int WORKING_TIME = 166;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.XsltApplicationImpl <em>Xslt Application</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.XsltApplicationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getXsltApplication()
     * @generated
     */
    int XSLT_APPLICATION = 167;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.OtherElementsContainer <em>Other Elements Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.OtherElementsContainer
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOtherElementsContainer()
     * @generated
     */
    int OTHER_ELEMENTS_CONTAINER = 87;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ArtifactInputImpl <em>Artifact Input</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ArtifactInputImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArtifactInput()
     * @generated
     */
    int ARTIFACT_INPUT = 7;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.CostStructureImpl <em>Cost Structure</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.CostStructureImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCostStructure()
     * @generated
     */
    int COST_STRUCTURE = 22;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.LayoutInfoImpl <em>Layout Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.LayoutInfoImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLayoutInfo()
     * @generated
     */
    int LAYOUT_INFO = 67;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ModificationDateImpl <em>Modification Date</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ModificationDateImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getModificationDate()
     * @generated
     */
    int MODIFICATION_DATE = 80;

    /**
     * The feature id for the '<em><b>Applications</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATIONS_CONTAINER__APPLICATIONS = 0;

    /**
     * The number of structural features of the '<em>Applications Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int APPLICATIONS_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The number of structural features of the '<em>Data Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_TYPE_FEATURE_COUNT = 0;

    /**
     * The feature id for the '<em><b>Basic Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__BASIC_TYPE = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Declared Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__DECLARED_TYPE = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Schema Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__SCHEMA_TYPE = DATA_TYPE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__EXTERNAL_REFERENCE = DATA_TYPE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Record Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__RECORD_TYPE = DATA_TYPE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Union Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__UNION_TYPE = DATA_TYPE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Enumeration Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__ENUMERATION_TYPE = DATA_TYPE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Array Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__ARRAY_TYPE = DATA_TYPE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>List Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__LIST_TYPE = DATA_TYPE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Lower Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__LOWER_INDEX = DATA_TYPE_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Upper Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE__UPPER_INDEX = DATA_TYPE_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Array Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARRAY_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Node Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__NODE_GRAPHICS_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Data Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__DATA_OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Artifact Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__ARTIFACT_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Text Annotation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__TEXT_ANNOTATION = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Package</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__GROUP = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Artifact</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS = 0;

    /**
     * The number of structural features of the '<em>Other Elements Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT_INPUT__OTHER_ELEMENTS =
            OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Artifact Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT_INPUT__ARTIFACT_ID =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Required For Start</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT_INPUT__REQUIRED_FOR_START =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Artifact Input</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT_INPUT_FEATURE_COUNT =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSIGNMENT__OTHER_ELEMENTS = OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Target</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSIGNMENT__TARGET = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSIGNMENT__EXPRESSION = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Assign Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSIGNMENT__ASSIGN_TIME = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Assignment</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSIGNMENT_FEATURE_COUNT = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PropertyInputImpl <em>Property Input</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PropertyInputImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPropertyInput()
     * @generated
     */
    int PROPERTY_INPUT = 112;

    /**
     * The feature id for the '<em><b>Assignments</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSIGMENTS_CONTAINER__ASSIGNMENTS = 0;

    /**
     * The number of structural features of the '<em>Assigments Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSIGMENTS_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Connector Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__CONNECTOR_GRAPHICS_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Association Direction</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__ASSOCIATION_DIRECTION = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__SOURCE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Target</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__TARGET = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Package</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION__PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Association</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIC_TYPE__OTHER_ELEMENTS = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Length</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIC_TYPE__LENGTH = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Precision</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIC_TYPE__PRECISION = DATA_TYPE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Scale</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIC_TYPE__SCALE = DATA_TYPE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIC_TYPE__TYPE = DATA_TYPE_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Basic Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASIC_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES = 0;

    /**
     * The number of structural features of the '<em>Other Attributes Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BLOCK_ACTIVITY__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Activity Set Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BLOCK_ACTIVITY__ACTIVITY_SET_ID =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Start Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BLOCK_ACTIVITY__START_ACTIVITY_ID =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>View</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BLOCK_ACTIVITY__VIEW = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Block Activity</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BLOCK_ACTIVITY_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Rule Name</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_RULE_APPLICATION__RULE_NAME = 0;

    /**
     * The feature id for the '<em><b>Location</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_RULE_APPLICATION__LOCATION = 1;

    /**
     * The number of structural features of the '<em>Business Rule Application</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_RULE_APPLICATION_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CATEGORY__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CATEGORY__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CATEGORY__NAME = NAMED_ELEMENT__NAME;

    /**
     * The number of structural features of the '<em>Category</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CATEGORY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLASS__VALUE = 0;

    /**
     * The number of structural features of the '<em>Class</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLASS_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CODEPAGE__VALUE = 0;

    /**
     * The number of structural features of the '<em>Codepage</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CODEPAGE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONDITION__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONDITION__MIXED = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONDITION__EXPRESSION = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONDITION__TYPE = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Condition</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONDITION_FEATURE_COUNT = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Graph Conformance</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFORMANCE_CLASS__GRAPH_CONFORMANCE = 0;

    /**
     * The feature id for the '<em><b>Bpmn Model Portability Conformance</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFORMANCE_CLASS__BPMN_MODEL_PORTABILITY_CONFORMANCE = 1;

    /**
     * The number of structural features of the '<em>Conformance Class</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFORMANCE_CLASS_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Coordinates</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTOR_GRAPHICS_INFO__COORDINATES = 0;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTOR_GRAPHICS_INFO__BORDER_COLOR = 1;

    /**
     * The feature id for the '<em><b>Fill Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTOR_GRAPHICS_INFO__FILL_COLOR = 2;

    /**
     * The feature id for the '<em><b>Is Visible</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTOR_GRAPHICS_INFO__IS_VISIBLE = 3;

    /**
     * The feature id for the '<em><b>Page Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTOR_GRAPHICS_INFO__PAGE_ID = 4;

    /**
     * The feature id for the '<em><b>Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTOR_GRAPHICS_INFO__STYLE = 5;

    /**
     * The feature id for the '<em><b>Tool Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTOR_GRAPHICS_INFO__TOOL_ID = 6;

    /**
     * The number of structural features of the '<em>Connector Graphics Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTOR_GRAPHICS_INFO_FEATURE_COUNT = 7;

    /**
     * The feature id for the '<em><b>XCoordinate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COORDINATES__XCOORDINATE = 0;

    /**
     * The feature id for the '<em><b>YCoordinate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COORDINATES__YCOORDINATE = 1;

    /**
     * The number of structural features of the '<em>Coordinates</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COORDINATES_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COST__VALUE = 0;

    /**
     * The number of structural features of the '<em>Cost</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COST_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Fixed Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COST_STRUCTURE__FIXED_COST = 0;

    /**
     * The feature id for the '<em><b>Resource Costs</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COST_STRUCTURE__RESOURCE_COSTS = 1;

    /**
     * The number of structural features of the '<em>Cost Structure</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COST_STRUCTURE_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COST_UNIT__VALUE = 0;

    /**
     * The number of structural features of the '<em>Cost Unit</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COST_UNIT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COUNTRY_KEY__VALUE = 0;

    /**
     * The number of structural features of the '<em>Country Key</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COUNTRY_KEY_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__OTHER_ATTRIBUTES =
            NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__DESCRIPTION = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Data Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__DATA_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Length</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__LENGTH = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__IS_ARRAY = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA__READ_ONLY = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Process Relevant Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_RELEVANT_DATA_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__ID = PROCESS_RELEVANT_DATA__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__OTHER_ATTRIBUTES = PROCESS_RELEVANT_DATA__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__NAME = PROCESS_RELEVANT_DATA__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__DESCRIPTION = PROCESS_RELEVANT_DATA__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__OTHER_ELEMENTS = PROCESS_RELEVANT_DATA__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Data Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__DATA_TYPE = PROCESS_RELEVANT_DATA__DATA_TYPE;

    /**
     * The feature id for the '<em><b>Length</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__LENGTH = PROCESS_RELEVANT_DATA__LENGTH;

    /**
     * The feature id for the '<em><b>Is Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__IS_ARRAY = PROCESS_RELEVANT_DATA__IS_ARRAY;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__READ_ONLY = PROCESS_RELEVANT_DATA__READ_ONLY;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__EXTENDED_ATTRIBUTES =
            PROCESS_RELEVANT_DATA_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Initial Value</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__INITIAL_VALUE = PROCESS_RELEVANT_DATA_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Correlation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__CORRELATION = PROCESS_RELEVANT_DATA_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Deprecated Data Is Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD__DEPRECATED_DATA_IS_ARRAY =
            PROCESS_RELEVANT_DATA_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Data Field</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELD_FEATURE_COUNT = PROCESS_RELEVANT_DATA_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Data Fields</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELDS_CONTAINER__DATA_FIELDS = 0;

    /**
     * The number of structural features of the '<em>Data Fields Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_FIELDS_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPING__OTHER_ELEMENTS = OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPING__OTHER_ATTRIBUTES =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Actual</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPING__ACTUAL = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Direction</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPING__DIRECTION = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Formal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPING__FORMAL = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Test Value</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPING__TEST_VALUE = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Data Mapping</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_MAPPING_FEATURE_COUNT = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Data Fields</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__DATA_FIELDS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Deprecated Produced At Completion</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__DEPRECATED_PRODUCED_AT_COMPLETION =
            NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Deprecated Required For Start</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__DEPRECATED_REQUIRED_FOR_START =
            NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__STATE = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Data Object</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Deadline Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEADLINE__DEADLINE_DURATION = 0;

    /**
     * The feature id for the '<em><b>Exception Name</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEADLINE__EXCEPTION_NAME = 1;

    /**
     * The feature id for the '<em><b>Execution</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEADLINE__EXECUTION = 2;

    /**
     * The number of structural features of the '<em>Deadline</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEADLINE_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Type Declaration Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DECLARED_TYPE__TYPE_DECLARATION_ID = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DECLARED_TYPE__NAME = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Declared Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DECLARED_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DeprecatedResultCompensationImpl <em>Deprecated Result Compensation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DeprecatedResultCompensationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeprecatedResultCompensation()
     * @generated
     */
    int DEPRECATED_RESULT_COMPENSATION = 32;

    /**
     * The feature id for the '<em><b>Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEPRECATED_RESULT_COMPENSATION__ACTIVITY_ID = 0;

    /**
     * The number of structural features of the '<em>Deprecated Result Compensation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEPRECATED_RESULT_COMPENSATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.DeprecatedTriggerRuleImpl <em>Deprecated Trigger Rule</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.DeprecatedTriggerRuleImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeprecatedTriggerRule()
     * @generated
     */
    int DEPRECATED_TRIGGER_RULE = 33;

    /**
     * The feature id for the '<em><b>Rule Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEPRECATED_TRIGGER_RULE__RULE_NAME = 0;

    /**
     * The number of structural features of the '<em>Deprecated Trigger Rule</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEPRECATED_TRIGGER_RULE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESCRIBED_ELEMENT__DESCRIPTION = 0;

    /**
     * The number of structural features of the '<em>Described Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESCRIBED_ELEMENT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESCRIPTION__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESCRIPTION__VALUE = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Description</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESCRIPTION_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENTATION__VALUE = 0;

    /**
     * The number of structural features of the '<em>Documentation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENTATION_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MIXED = 0;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
     * The feature id for the '<em><b>Package</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PACKAGE = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION__VALUE = 0;

    /**
     * The number of structural features of the '<em>Duration</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DURATION_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Jndi Name</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EJB_APPLICATION__JNDI_NAME = 0;

    /**
     * The feature id for the '<em><b>Home Class</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EJB_APPLICATION__HOME_CLASS = 1;

    /**
     * The feature id for the '<em><b>Method</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EJB_APPLICATION__METHOD = 2;

    /**
     * The number of structural features of the '<em>Ejb Application</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EJB_APPLICATION_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT__OTHER_ATTRIBUTES = OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The number of structural features of the '<em>Event</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_FEATURE_COUNT = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__OTHER_ATTRIBUTES = EVENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__TRIGGER_RESULT_MESSAGE = EVENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Result Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__RESULT_ERROR = EVENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Trigger Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__TRIGGER_RESULT_COMPENSATION = EVENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Trigger Result Signal</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__TRIGGER_RESULT_SIGNAL = EVENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Result Multiple</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__RESULT_MULTIPLE = EVENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__IMPLEMENTATION = EVENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Result</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__RESULT = EVENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Deprecated Trigger Result Link</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK = EVENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Deprecated Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT__DEPRECATED_RESULT_COMPENSATION = EVENT_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>End Event</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_POINT__EXTERNAL_REFERENCE = 0;

    /**
     * The feature id for the '<em><b>End Point Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_POINT__END_POINT_TYPE = 1;

    /**
     * The number of structural features of the '<em>End Point</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_POINT_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Enumeration Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUMERATION_TYPE__ENUMERATION_VALUE = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Enumeration Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUMERATION_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUMERATION_VALUE__NAME = 0;

    /**
     * The number of structural features of the '<em>Enumeration Value</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUMERATION_VALUE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXCEPTION_NAME__VALUE = 0;

    /**
     * The number of structural features of the '<em>Exception Name</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXCEPTION_NAME_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION__MIXED = 0;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION__GROUP = 1;

    /**
     * The feature id for the '<em><b>Any</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION__ANY = 2;

    /**
     * The feature id for the '<em><b>Script Grammar</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION__SCRIPT_GRAMMAR = 3;

    /**
     * The number of structural features of the '<em>Expression</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE__MIXED = 0;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE__GROUP = 1;

    /**
     * The feature id for the '<em><b>Any</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE__ANY = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE__NAME = 3;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE__VALUE = 4;

    /**
     * The number of structural features of the '<em>Extended Attribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTE_FEATURE_COUNT = 5;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES = 0;

    /**
     * The number of structural features of the '<em>Extended Attributes Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_ATTRIBUTES_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_PACKAGE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_PACKAGE__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_PACKAGE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_PACKAGE__EXTENDED_ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Href</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_PACKAGE__HREF = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>External Package</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_PACKAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_REFERENCE__LOCATION = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_REFERENCE__NAMESPACE = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Xref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_REFERENCE__XREF = DATA_TYPE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>External Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_REFERENCE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.ResourceCostsImpl <em>Resource Costs</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.ResourceCostsImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResourceCosts()
     * @generated
     */
    int RESOURCE_COSTS = 116;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultCancelImpl <em>Trigger Result Cancel</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerResultCancelImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultCancel()
     * @generated
     */
    int TRIGGER_RESULT_CANCEL = 148;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultCompensationImpl <em>Trigger Result Compensation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerResultCompensationImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultCompensation()
     * @generated
     */
    int TRIGGER_RESULT_COMPENSATION = 149;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl <em>Trigger Result Signal</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultSignal()
     * @generated
     */
    int TRIGGER_RESULT_SIGNAL = 150;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.TriggerConditionalImpl <em>Trigger Conditional</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.TriggerConditionalImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerConditional()
     * @generated
     */
    int TRIGGER_CONDITIONAL = 153;

    /**
     * The feature id for the '<em><b>Activities</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FLOW_CONTAINER__ACTIVITIES = 0;

    /**
     * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FLOW_CONTAINER__TRANSITIONS = 1;

    /**
     * The feature id for the '<em><b>Ad Hoc</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FLOW_CONTAINER__AD_HOC = 2;

    /**
     * The feature id for the '<em><b>Ad Hoc Completion Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FLOW_CONTAINER__AD_HOC_COMPLETION_CONDITION = 3;

    /**
     * The feature id for the '<em><b>Ad Hoc Ordering</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FLOW_CONTAINER__AD_HOC_ORDERING = 4;

    /**
     * The feature id for the '<em><b>Default Start Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FLOW_CONTAINER__DEFAULT_START_ACTIVITY_ID = 5;

    /**
     * The number of structural features of the '<em>Flow Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FLOW_CONTAINER_FEATURE_COUNT = 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__ID = PROCESS_RELEVANT_DATA__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__OTHER_ATTRIBUTES =
            PROCESS_RELEVANT_DATA__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__NAME = PROCESS_RELEVANT_DATA__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__DESCRIPTION = PROCESS_RELEVANT_DATA__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__OTHER_ELEMENTS =
            PROCESS_RELEVANT_DATA__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Data Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__DATA_TYPE = PROCESS_RELEVANT_DATA__DATA_TYPE;

    /**
     * The feature id for the '<em><b>Length</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__LENGTH = PROCESS_RELEVANT_DATA__LENGTH;

    /**
     * The feature id for the '<em><b>Is Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__IS_ARRAY = PROCESS_RELEVANT_DATA__IS_ARRAY;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__READ_ONLY = PROCESS_RELEVANT_DATA__READ_ONLY;

    /**
     * The feature id for the '<em><b>Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__MODE = PROCESS_RELEVANT_DATA_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER__REQUIRED = PROCESS_RELEVANT_DATA_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Formal Parameter</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETER_FEATURE_COUNT =
            PROCESS_RELEVANT_DATA_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_LAYOUT__MIXED = 0;

    /**
     * The number of structural features of the '<em>Form Layout</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_LAYOUT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Form Layout</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_APPLICATION__FORM_LAYOUT = 0;

    /**
     * The number of structural features of the '<em>Form Application</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_APPLICATION_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Node Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPHICAL_NODE__NODE_GRAPHICS_INFOS = 0;

    /**
     * The number of structural features of the '<em>Graphical Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPHICAL_NODE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Connector Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPHICAL_CONNECTOR__CONNECTOR_GRAPHICS_INFOS = 0;

    /**
     * The number of structural features of the '<em>Graphical Connector</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPHICAL_CONNECTOR_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Category</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__CATEGORY = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Group</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HOME_CLASS__VALUE = 0;

    /**
     * The number of structural features of the '<em>Home Class</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HOME_CLASS_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ICON__VALUE = 0;

    /**
     * The feature id for the '<em><b>Height</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ICON__HEIGHT = 1;

    /**
     * The feature id for the '<em><b>Shape</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ICON__SHAPE = 2;

    /**
     * The feature id for the '<em><b>Width</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ICON__WIDTH = 3;

    /**
     * The feature id for the '<em><b>XCoord</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ICON__XCOORD = 4;

    /**
     * The feature id for the '<em><b>YCoord</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ICON__YCOORD = 5;

    /**
     * The number of structural features of the '<em>Icon</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ICON_FEATURE_COUNT = 6;

    /**
     * The feature id for the '<em><b>Activity</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTATION__ACTIVITY = 0;

    /**
     * The number of structural features of the '<em>Implementation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IMPLEMENTATION_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Input</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_SET__INPUT = 0;

    /**
     * The feature id for the '<em><b>Artifact Input</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_SET__ARTIFACT_INPUT = 1;

    /**
     * The feature id for the '<em><b>Property Input</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_SET__PROPERTY_INPUT = 2;

    /**
     * The number of structural features of the '<em>Input Set</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_SET_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Artifact Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT__ARTIFACT_ID = 0;

    /**
     * The number of structural features of the '<em>Input</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__OTHER_ATTRIBUTES = EVENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE = EVENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Trigger Timer</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER_TIMER = EVENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Result Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__RESULT_ERROR = EVENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Trigger Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION =
            EVENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Trigger Conditional</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL = EVENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Trigger Result Link</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK = EVENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Trigger Intermediate Multiple</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE =
            EVENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__IMPLEMENTATION = EVENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Target</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TARGET = EVENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Trigger</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER = EVENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__ANY_ATTRIBUTE = EVENT_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Trigger Result Cancel</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL = EVENT_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Trigger Result Signal</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL = EVENT_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Deprecated Trigger Rule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE = EVENT_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Deprecated Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION =
            EVENT_FEATURE_COUNT + 14;

    /**
     * The number of structural features of the '<em>Intermediate Event</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERMEDIATE_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 15;

    /**
     * The feature id for the '<em><b>Expression</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IO_RULES__EXPRESSION = 0;

    /**
     * The number of structural features of the '<em>IO Rules</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IO_RULES_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JNDI_NAME__VALUE = 0;

    /**
     * The number of structural features of the '<em>Jndi Name</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JNDI_NAME_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Incoming Condtion</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JOIN__INCOMING_CONDTION = 0;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JOIN__TYPE = 1;

    /**
     * The feature id for the '<em><b>Exclusive Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JOIN__EXCLUSIVE_TYPE = 2;

    /**
     * The number of structural features of the '<em>Join</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int JOIN_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAYOUT_INFO__OTHER_ELEMENTS = OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Pixels Per Millimeter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAYOUT_INFO__PIXELS_PER_MILLIMETER =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Layout Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LAYOUT_INFO_FEATURE_COUNT = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Node Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__NODE_GRAPHICS_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Deprecated Parent Lane</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__DEPRECATED_PARENT_LANE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Deprecated Parent Pool Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__DEPRECATED_PARENT_POOL_ID = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Parent Pool</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__PARENT_POOL = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Performers</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__PERFORMERS = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Nested Lane</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__NESTED_LANE = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Lane</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LENGTH__VALUE = 0;

    /**
     * The number of structural features of the '<em>Length</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LENGTH_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIMIT__VALUE = 0;

    /**
     * The number of structural features of the '<em>Limit</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIMIT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Basic Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__BASIC_TYPE = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Declared Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__DECLARED_TYPE = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Schema Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__SCHEMA_TYPE = DATA_TYPE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__EXTERNAL_REFERENCE = DATA_TYPE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Record Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__RECORD_TYPE = DATA_TYPE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Union Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__UNION_TYPE = DATA_TYPE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Enumeration Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__ENUMERATION_TYPE = DATA_TYPE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Array Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__ARRAY_TYPE = DATA_TYPE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>List Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE__LIST_TYPE = DATA_TYPE_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>List Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LIST_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__VALUE = 0;

    /**
     * The number of structural features of the '<em>Location</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE__OTHER_ELEMENTS =
            OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Loop Counter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE__LOOP_COUNTER =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>MI Flow Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE__MI_FLOW_CONDITION =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>MI Ordering</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE__MI_ORDERING =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>MI Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE__MI_CONDITION =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Complex MI Flow Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Attribute Complex MI Flow Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE__ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Attribute MI Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE__ATTRIBUTE_MI_CONDITION =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Loop Multi Instance</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_MULTI_INSTANCE_FEATURE_COUNT =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_STANDARD__OTHER_ELEMENTS =
            OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Loop Counter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_STANDARD__LOOP_COUNTER =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Loop Maximum</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_STANDARD__LOOP_MAXIMUM =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Test Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_STANDARD__TEST_TIME = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Attribute Loop Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_STANDARD__ATTRIBUTE_LOOP_CONDITION =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Loop Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_STANDARD__LOOP_CONDITION =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Loop Standard</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_STANDARD_FEATURE_COUNT =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Loop Standard</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP__LOOP_STANDARD = 0;

    /**
     * The feature id for the '<em><b>Loop Multi Instance</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP__LOOP_MULTI_INSTANCE = 1;

    /**
     * The feature id for the '<em><b>Loop Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP__LOOP_TYPE = 2;

    /**
     * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP__ANY_ATTRIBUTE = 3;

    /**
     * The number of structural features of the '<em>Loop</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Basic Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__BASIC_TYPE = 0;

    /**
     * The feature id for the '<em><b>Declared Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__DECLARED_TYPE = 1;

    /**
     * The feature id for the '<em><b>Schema Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__SCHEMA_TYPE = 2;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__EXTERNAL_REFERENCE = 3;

    /**
     * The feature id for the '<em><b>Record Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__RECORD_TYPE = 4;

    /**
     * The feature id for the '<em><b>Union Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__UNION_TYPE = 5;

    /**
     * The feature id for the '<em><b>Enumeration Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__ENUMERATION_TYPE = 6;

    /**
     * The feature id for the '<em><b>Array Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__ARRAY_TYPE = 7;

    /**
     * The feature id for the '<em><b>List Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__LIST_TYPE = 8;

    /**
     * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER__ANY_ATTRIBUTE = 9;

    /**
     * The number of structural features of the '<em>Member</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEMBER_FEATURE_COUNT = 10;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Connector Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS =
            NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__MESSAGE = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__SOURCE = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Target</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__TARGET = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Package</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW__PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Message Flow</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FLOW_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Actual Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__ACTUAL_PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__DATA_MAPPINGS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Fault Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__FAULT_NAME = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>From</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__FROM = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>To</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE__TO = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Message</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD__VALUE = 0;

    /**
     * The number of structural features of the '<em>Method</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODIFICATION_DATE__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The number of structural features of the '<em>Modification Date</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODIFICATION_DATE_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Role Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MY_ROLE__ROLE_NAME = 0;

    /**
     * The number of structural features of the '<em>My Role</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MY_ROLE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Coordinates</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__COORDINATES = 0;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__BORDER_COLOR = 1;

    /**
     * The feature id for the '<em><b>Fill Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__FILL_COLOR = 2;

    /**
     * The feature id for the '<em><b>Height</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__HEIGHT = 3;

    /**
     * The feature id for the '<em><b>Is Visible</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__IS_VISIBLE = 4;

    /**
     * The feature id for the '<em><b>Lane Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__LANE_ID = 5;

    /**
     * The feature id for the '<em><b>Page</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__PAGE = 6;

    /**
     * The feature id for the '<em><b>Shape</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__SHAPE = 7;

    /**
     * The feature id for the '<em><b>Tool Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__TOOL_ID = 8;

    /**
     * The feature id for the '<em><b>Width</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__WIDTH = 9;

    /**
     * The feature id for the '<em><b>Page Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO__PAGE_ID = 10;

    /**
     * The number of structural features of the '<em>Node Graphics Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NODE_GRAPHICS_INFO_FEATURE_COUNT = 11;

    /**
     * The feature id for the '<em><b>Activity</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NO__ACTIVITY = IMPLEMENTATION__ACTIVITY;

    /**
     * The number of structural features of the '<em>No</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NO_FEATURE_COUNT = IMPLEMENTATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Categories</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT__CATEGORIES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT__DOCUMENTATION = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Object</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Output</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_SET__OUTPUT = 0;

    /**
     * The number of structural features of the '<em>Output Set</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_SET_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Artifact Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT__ARTIFACT_ID = 0;

    /**
     * The number of structural features of the '<em>Output</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__OTHER_ATTRIBUTES = DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Xpdl Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__XPDL_VERSION = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Vendor</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__VENDOR = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Created</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__CREATED = DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__DOCUMENTATION = DESCRIBED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Priority Unit</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__PRIORITY_UNIT = DESCRIBED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Cost Unit</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__COST_UNIT = DESCRIBED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Vendor Extensions</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__VENDOR_EXTENSIONS = DESCRIBED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Layout Info</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__LAYOUT_INFO = DESCRIBED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Modification Date</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER__MODIFICATION_DATE = DESCRIBED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Package Header</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_HEADER_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__EXTENDED_ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Applications</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__APPLICATIONS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Participants</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__PARTICIPANTS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Data Fields</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__DATA_FIELDS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Package Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__PACKAGE_HEADER = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Redefinable Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__REDEFINABLE_HEADER = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Conformance Class</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__CONFORMANCE_CLASS = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__SCRIPT = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>External Packages</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__EXTERNAL_PACKAGES = NAMED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Type Declarations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__TYPE_DECLARATIONS = NAMED_ELEMENT_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Partner Link Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__PARTNER_LINK_TYPES = NAMED_ELEMENT_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Pools</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__POOLS = NAMED_ELEMENT_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Message Flows</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__MESSAGE_FLOWS = NAMED_ELEMENT_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__ASSOCIATIONS = NAMED_ELEMENT_FEATURE_COUNT + 14;

    /**
     * The feature id for the '<em><b>Artifacts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__ARTIFACTS = NAMED_ELEMENT_FEATURE_COUNT + 15;

    /**
     * The feature id for the '<em><b>Processes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__PROCESSES = NAMED_ELEMENT_FEATURE_COUNT + 16;

    /**
     * The feature id for the '<em><b>Language</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__LANGUAGE = NAMED_ELEMENT_FEATURE_COUNT + 17;

    /**
     * The feature id for the '<em><b>Query Language</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE__QUERY_LANGUAGE = NAMED_ELEMENT_FEATURE_COUNT + 18;

    /**
     * The number of structural features of the '<em>Package</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PACKAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 19;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PageImpl <em>Page</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PageImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPage()
     * @generated
     */
    int PAGE = 92;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Height</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE__HEIGHT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Width</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE__WIDTH = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Page</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PagesImpl <em>Pages</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PagesImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPages()
     * @generated
     */
    int PAGES = 93;

    /**
     * The feature id for the '<em><b>Page</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGES__PAGE = 0;

    /**
     * The number of structural features of the '<em>Pages</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGES_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Participants</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANTS_CONTAINER__PARTICIPANTS = 0;

    /**
     * The number of structural features of the '<em>Participants Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANTS_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT__EXTENDED_ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT__DESCRIPTION = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Participant Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT__PARTICIPANT_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT__EXTERNAL_REFERENCE = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Participant</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_TYPE_ELEM__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_TYPE_ELEM__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_TYPE_ELEM__TYPE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Participant Type Elem</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_TYPE_ELEM_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK__ID = UNIQUE_ID_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>My Role</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK__MY_ROLE = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Partner Role</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK__PARTNER_ROLE = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Partner Link Type Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK__PARTNER_LINK_TYPE_ID =
            UNIQUE_ID_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK__NAME = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Partner Link</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK_FEATURE_COUNT = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK_TYPE__ID = UNIQUE_ID_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Role</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK_TYPE__ROLE = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK_TYPE__NAME = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Partner Link Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_LINK_TYPE_FEATURE_COUNT = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>End Point</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_ROLE__END_POINT = 0;

    /**
     * The feature id for the '<em><b>Port Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_ROLE__PORT_NAME = 1;

    /**
     * The feature id for the '<em><b>Role Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_ROLE__ROLE_NAME = 2;

    /**
     * The feature id for the '<em><b>Service Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_ROLE__SERVICE_NAME = 3;

    /**
     * The number of structural features of the '<em>Partner Role</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_ROLE_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Partner Link Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER__PARTNER_LINK_ID = 0;

    /**
     * The feature id for the '<em><b>Role Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER__ROLE_TYPE = 1;

    /**
     * The number of structural features of the '<em>Partner</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTNER_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERFORMER__VALUE = 0;

    /**
     * The number of structural features of the '<em>Performer</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERFORMER_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.impl.PerformersImpl <em>Performers</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.impl.PerformersImpl
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPerformers()
     * @generated
     */
    int PERFORMERS = 102;

    /**
     * The feature id for the '<em><b>Performers</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERFORMERS__PERFORMERS = 0;

    /**
     * The number of structural features of the '<em>Performers</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERFORMERS_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Class</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POJO_APPLICATION__CLASS = 0;

    /**
     * The feature id for the '<em><b>Method</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POJO_APPLICATION__METHOD = 1;

    /**
     * The number of structural features of the '<em>Pojo Application</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POJO_APPLICATION_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Node Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__NODE_GRAPHICS_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Lanes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__LANES = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Boundary Visible</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__BOUNDARY_VISIBLE = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Orientation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__ORIENTATION = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Participant Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__PARTICIPANT_ID = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__PROCESS_ID = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Parent Package</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__PARENT_PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Main Pool</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__MAIN_POOL = NAMED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Pool</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRECISION__VALUE = 0;

    /**
     * The number of structural features of the '<em>Precision</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRECISION_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIORITY__VALUE = 0;

    /**
     * The number of structural features of the '<em>Priority</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIORITY_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIORITY_UNIT__VALUE = 0;

    /**
     * The number of structural features of the '<em>Priority Unit</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIORITY_UNIT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Created</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER__CREATED = DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER__PRIORITY = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Limit</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER__LIMIT = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Valid From</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER__VALID_FROM = DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Valid To</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER__VALID_TO = DESCRIBED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Time Estimation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER__TIME_ESTIMATION = DESCRIBED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Duration Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER__DURATION_UNIT = DESCRIBED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Process Header</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_HEADER_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Activities</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__ACTIVITIES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__TRANSITIONS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ad Hoc</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__AD_HOC = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Ad Hoc Completion Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__AD_HOC_COMPLETION_CONDITION = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Ad Hoc Ordering</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__AD_HOC_ORDERING = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Default Start Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__DEFAULT_START_ACTIVITY_ID = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__EXTENDED_ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Formal Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__FORMAL_PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Assignments</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__ASSIGNMENTS = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Data Fields</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__DATA_FIELDS = NAMED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Participants</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__PARTICIPANTS = NAMED_ELEMENT_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Applications</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__APPLICATIONS = NAMED_ELEMENT_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__OTHER_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Process Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__PROCESS_HEADER = NAMED_ELEMENT_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Redefinable Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__REDEFINABLE_HEADER = NAMED_ELEMENT_FEATURE_COUNT + 14;

    /**
     * The feature id for the '<em><b>Partner Links</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__PARTNER_LINKS = NAMED_ELEMENT_FEATURE_COUNT + 15;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 16;

    /**
     * The feature id for the '<em><b>Extensions</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__EXTENSIONS = NAMED_ELEMENT_FEATURE_COUNT + 17;

    /**
     * The feature id for the '<em><b>Access Level</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__ACCESS_LEVEL = NAMED_ELEMENT_FEATURE_COUNT + 18;

    /**
     * The feature id for the '<em><b>Default Start Activity Set Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__DEFAULT_START_ACTIVITY_SET_ID =
            NAMED_ELEMENT_FEATURE_COUNT + 19;

    /**
     * The feature id for the '<em><b>Enable Instance Compensation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__ENABLE_INSTANCE_COMPENSATION =
            NAMED_ELEMENT_FEATURE_COUNT + 20;

    /**
     * The feature id for the '<em><b>Process Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__PROCESS_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 21;

    /**
     * The feature id for the '<em><b>Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__STATUS = NAMED_ELEMENT_FEATURE_COUNT + 22;

    /**
     * The feature id for the '<em><b>Suppress Join Failure</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__SUPPRESS_JOIN_FAILURE = NAMED_ELEMENT_FEATURE_COUNT + 23;

    /**
     * The feature id for the '<em><b>Package</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 24;

    /**
     * The feature id for the '<em><b>Activity Sets</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS__ACTIVITY_SETS = NAMED_ELEMENT_FEATURE_COUNT + 25;

    /**
     * The number of structural features of the '<em>Process</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROCESS_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 26;

    /**
     * The feature id for the '<em><b>Formal Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS = 0;

    /**
     * The number of structural features of the '<em>Formal Parameters Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORMAL_PARAMETERS_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROPERTY_INPUT__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROPERTY_INPUT__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Property Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROPERTY_INPUT__PROPERTY_ID =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Property Input</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROPERTY_INPUT_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Member</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RECORD_TYPE__MEMBER = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Record Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RECORD_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REDEFINABLE_HEADER__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REDEFINABLE_HEADER__AUTHOR =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REDEFINABLE_HEADER__VERSION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Codepage</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REDEFINABLE_HEADER__CODEPAGE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Countrykey</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REDEFINABLE_HEADER__COUNTRYKEY =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Responsibles</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REDEFINABLE_HEADER__RESPONSIBLES =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Publication Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REDEFINABLE_HEADER__PUBLICATION_STATUS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Redefinable Header</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REDEFINABLE_HEADER_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Activity</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE__ACTIVITY = IMPLEMENTATION__ACTIVITY;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE__OTHER_ATTRIBUTES = IMPLEMENTATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE__OTHER_ELEMENTS = IMPLEMENTATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE__ACTIVITY_ID = IMPLEMENTATION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_FEATURE_COUNT = IMPLEMENTATION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_COSTS__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_COSTS__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_COSTS__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Resource Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_COSTS__RESOURCE_COST = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Cost Unit Of Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_COSTS__COST_UNIT_OF_TIME = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Resource Costs</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_COSTS_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESPONSIBLE__VALUE = 0;

    /**
     * The number of structural features of the '<em>Responsible</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESPONSIBLE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_ERROR__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_ERROR__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Error Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_ERROR__ERROR_CODE = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Result Error</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_ERROR_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_MULTIPLE__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Trigger Result Link</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_MULTIPLE__TRIGGER_RESULT_LINK =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_MULTIPLE__RESULT_COMPENSATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Result Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_MULTIPLE__RESULT_ERROR =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Result Multiple</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESULT_MULTIPLE_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROLE__NAME = 0;

    /**
     * The feature id for the '<em><b>Port Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROLE__PORT_TYPE = 1;

    /**
     * The number of structural features of the '<em>Role</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROLE_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE__OTHER_ELEMENTS = OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;

    /**
     * The feature id for the '<em><b>Gateway Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE__GATEWAY_TYPE = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Deprecated Xor Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE__DEPRECATED_XOR_TYPE = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Deprecated Instantiate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE__DEPRECATED_INSTANTIATE =
            OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Marker Visible</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE__MARKER_VISIBLE = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Incoming Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE__INCOMING_CONDITION = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Outgoing Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE__OUTGOING_CONDITION = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Exclusive Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE__EXCLUSIVE_TYPE = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Route</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROUTE_FEATURE_COUNT = OTHER_ELEMENTS_CONTAINER_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RULE_NAME__VALUE = 0;

    /**
     * The number of structural features of the '<em>Rule Name</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RULE_NAME_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RULE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RULE__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RULE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RULE__EXPRESSION = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Rule</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RULE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCALE__VALUE = 0;

    /**
     * The number of structural features of the '<em>Scale</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCALE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Any</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA__ANY = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Schema</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEMA_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Any</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT__ANY = 0;

    /**
     * The feature id for the '<em><b>Grammar</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT__GRAMMAR = 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT__TYPE = 2;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT__VERSION = 3;

    /**
     * The number of structural features of the '<em>Script</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCRIPT_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>End Point</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__END_POINT = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Port Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__PORT_NAME = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Service Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE__SERVICE_NAME = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Service</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_FEATURE_COUNT = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Cost</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMULATION_INFORMATION__COST = 0;

    /**
     * The feature id for the '<em><b>Time Estimation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMULATION_INFORMATION__TIME_ESTIMATION = 1;

    /**
     * The feature id for the '<em><b>Instantiation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMULATION_INFORMATION__INSTANTIATION = 2;

    /**
     * The number of structural features of the '<em>Simulation Information</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMULATION_INFORMATION_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Transition Refs</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT__TRANSITION_REFS = 0;

    /**
     * The feature id for the '<em><b>Outgoing Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT__OUTGOING_CONDITION = 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT__TYPE = 2;

    /**
     * The feature id for the '<em><b>Exclusive Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT__EXCLUSIVE_TYPE = 3;

    /**
     * The number of structural features of the '<em>Split</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__OTHER_ATTRIBUTES = EVENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__TRIGGER_RESULT_MESSAGE = EVENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Trigger Timer</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__TRIGGER_TIMER = EVENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Trigger Conditional</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__TRIGGER_CONDITIONAL = EVENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Trigger Result Signal</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__TRIGGER_RESULT_SIGNAL = EVENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Trigger Multiple</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__TRIGGER_MULTIPLE = EVENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__IMPLEMENTATION = EVENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Trigger</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__TRIGGER = EVENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Deprecated Trigger Rule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__DEPRECATED_TRIGGER_RULE = EVENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Deprecated Trigger Result Link</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK = EVENT_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>Start Event</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Activity</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__ACTIVITY = IMPLEMENTATION__ACTIVITY;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__OTHER_ATTRIBUTES = IMPLEMENTATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__OTHER_ELEMENTS = IMPLEMENTATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Actual Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__ACTUAL_PARAMETERS = IMPLEMENTATION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__DATA_MAPPINGS = IMPLEMENTATION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Execution</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__EXECUTION = IMPLEMENTATION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Instance Data Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__INSTANCE_DATA_FIELD = IMPLEMENTATION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__PROCESS_ID = IMPLEMENTATION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Package Ref Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__PACKAGE_REF_ID = IMPLEMENTATION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Start Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__START_ACTIVITY_ID = IMPLEMENTATION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Start Activity Set Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__START_ACTIVITY_SET_ID = IMPLEMENTATION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>End Point</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW__END_POINT = IMPLEMENTATION_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Sub Flow</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_FLOW_FEATURE_COUNT = IMPLEMENTATION_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_APPLICATION__DESCRIPTION = DESCRIBED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Actual Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_APPLICATION__ACTUAL_PARAMETERS =
            DESCRIBED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Data Mappings</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_APPLICATION__DATA_MAPPINGS = DESCRIBED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Package Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_APPLICATION__PACKAGE_REF = DESCRIBED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Application Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_APPLICATION__APPLICATION_ID = DESCRIBED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Task Application</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_APPLICATION_FEATURE_COUNT = DESCRIBED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Performers</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_MANUAL__PERFORMERS = 0;

    /**
     * The number of structural features of the '<em>Task Manual</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_MANUAL_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_RECEIVE__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_RECEIVE__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_RECEIVE__MESSAGE = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Web Service Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_RECEIVE__WEB_SERVICE_OPERATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_RECEIVE__IMPLEMENTATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Instantiate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_RECEIVE__INSTANTIATE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Task Receive</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_RECEIVE_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Task Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_REFERENCE__TASK_REF = 0;

    /**
     * The number of structural features of the '<em>Task Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_REFERENCE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SCRIPT__SCRIPT = 0;

    /**
     * The number of structural features of the '<em>Task Script</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SCRIPT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SEND__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SEND__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SEND__MESSAGE = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Web Service Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SEND__WEB_SERVICE_OPERATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Web Service Fault Catch</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SEND__WEB_SERVICE_FAULT_CATCH =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SEND__IMPLEMENTATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Task Send</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SEND_FEATURE_COUNT = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SERVICE__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SERVICE__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Message In</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SERVICE__MESSAGE_IN = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Message Out</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SERVICE__MESSAGE_OUT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Web Service Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SERVICE__WEB_SERVICE_OPERATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Web Service Fault Catch</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SERVICE__WEB_SERVICE_FAULT_CATCH =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SERVICE__IMPLEMENTATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Task Service</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_SERVICE_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Activity</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__ACTIVITY = IMPLEMENTATION__ACTIVITY;

    /**
     * The feature id for the '<em><b>Task Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__TASK_SERVICE = IMPLEMENTATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Task Receive</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__TASK_RECEIVE = IMPLEMENTATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Task Manual</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__TASK_MANUAL = IMPLEMENTATION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Task Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__TASK_REFERENCE = IMPLEMENTATION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Task Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__TASK_SCRIPT = IMPLEMENTATION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Task Send</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__TASK_SEND = IMPLEMENTATION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Task User</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__TASK_USER = IMPLEMENTATION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Task Application</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK__TASK_APPLICATION = IMPLEMENTATION_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Task</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_FEATURE_COUNT = IMPLEMENTATION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_USER__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_USER__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Performers</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_USER__PERFORMERS = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Message In</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_USER__MESSAGE_IN = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Message Out</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_USER__MESSAGE_OUT = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Web Service Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_USER__WEB_SERVICE_OPERATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Implementation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_USER__IMPLEMENTATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Task User</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TASK_USER_FEATURE_COUNT = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Waiting Time</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_ESTIMATION__WAITING_TIME = 0;

    /**
     * The feature id for the '<em><b>Working Time</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_ESTIMATION__WORKING_TIME = 1;

    /**
     * The feature id for the '<em><b>Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_ESTIMATION__DURATION = 2;

    /**
     * The number of structural features of the '<em>Time Estimation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_ESTIMATION_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Transaction Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSACTION__TRANSACTION_ID = 0;

    /**
     * The feature id for the '<em><b>Transaction Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSACTION__TRANSACTION_METHOD = 1;

    /**
     * The feature id for the '<em><b>Transaction Protocol</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSACTION__TRANSACTION_PROTOCOL = 2;

    /**
     * The number of structural features of the '<em>Transaction</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSACTION_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_REF__ID = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_REF__NAME = 1;

    /**
     * The number of structural features of the '<em>Transition Ref</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_REF_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Join</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_RESTRICTION__JOIN = 0;

    /**
     * The feature id for the '<em><b>Split</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_RESTRICTION__SPLIT = 1;

    /**
     * The number of structural features of the '<em>Transition Restriction</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_RESTRICTION_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__EXTENDED_ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Connector Graphics Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__CONNECTOR_GRAPHICS_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__DESCRIPTION = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__CONDITION = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Assignments</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__ASSIGNMENTS = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__OBJECT = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>From</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__FROM = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Quantity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__QUANTITY = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>To</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__TO = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Flow Container</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION__FLOW_CONTAINER = NAMED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Transition</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Trigger Timer</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Result Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Trigger Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Deprecated Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Trigger Conditional</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Trigger Result Link</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Trigger Intermediate Multiple</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_INTERMEDIATE_MULTIPLE_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_MULTIPLE__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_MULTIPLE__TRIGGER_RESULT_MESSAGE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Trigger Timer</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_MULTIPLE__TRIGGER_TIMER =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Trigger Conditional</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_MULTIPLE__TRIGGER_CONDITIONAL =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Trigger Result Link</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_MULTIPLE__TRIGGER_RESULT_LINK =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Deprecated Trigger Rule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_MULTIPLE__DEPRECATED_TRIGGER_RULE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Trigger Multiple</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_MULTIPLE_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Trigger Result Cancel</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_CANCEL_FEATURE_COUNT = 0;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_COMPENSATION__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_COMPENSATION__ACTIVITY_ID =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Trigger Result Compensation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_COMPENSATION_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_SIGNAL__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Catch Throw</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_SIGNAL__CATCH_THROW =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_SIGNAL__NAME =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Properties</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_SIGNAL__PROPERTIES =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Trigger Result Signal</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_SIGNAL_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Deprecated Link Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_LINK__DEPRECATED_LINK_ID = 0;

    /**
     * The feature id for the '<em><b>Deprecated Process Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_LINK__DEPRECATED_PROCESS_REF = 1;

    /**
     * The feature id for the '<em><b>Catch Throw</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_LINK__CATCH_THROW = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_LINK__NAME = 3;

    /**
     * The number of structural features of the '<em>Trigger Result Link</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_LINK_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_MESSAGE__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_MESSAGE__MESSAGE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Web Service Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Catch Throw</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_MESSAGE__CATCH_THROW =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Trigger Result Message</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_RESULT_MESSAGE_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Condition Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_CONDITIONAL__CONDITION_NAME = 0;

    /**
     * The feature id for the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_CONDITIONAL__EXPRESSION = 1;

    /**
     * The number of structural features of the '<em>Trigger Conditional</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_CONDITIONAL_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_TIMER__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Other Elements</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_TIMER__OTHER_ELEMENTS =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Deprecated Time Cycle</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_TIMER__DEPRECATED_TIME_CYCLE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Deprecated Time Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_TIMER__DEPRECATED_TIME_DATE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Time Date</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_TIMER__TIME_DATE = OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Time Cycle</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_TIMER__TIME_CYCLE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Trigger Timer</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRIGGER_TIMER_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__OTHER_ATTRIBUTES = NAMED_ELEMENT__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Extended Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__EXTENDED_ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__DESCRIPTION = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Basic Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__BASIC_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Declared Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__DECLARED_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Schema Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__SCHEMA_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__EXTERNAL_REFERENCE = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Record Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__RECORD_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Union Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__UNION_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Enumeration Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__ENUMERATION_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Array Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__ARRAY_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>List Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION__LIST_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Type Declaration</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPE_DECLARATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Member</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNION_TYPE__MEMBER = DATA_TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Union Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNION_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALID_FROM__VALUE = 0;

    /**
     * The number of structural features of the '<em>Valid From</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALID_FROM_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALID_TO__VALUE = 0;

    /**
     * The number of structural features of the '<em>Valid To</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALID_TO_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Vendor Extension</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VENDOR_EXTENSIONS__VENDOR_EXTENSION = 0;

    /**
     * The number of structural features of the '<em>Vendor Extensions</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VENDOR_EXTENSIONS_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Extension Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VENDOR_EXTENSION__EXTENSION_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Schema Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VENDOR_EXTENSION__SCHEMA_LOCATION = 1;

    /**
     * The feature id for the '<em><b>Tool Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VENDOR_EXTENSION__TOOL_ID = 2;

    /**
     * The number of structural features of the '<em>Vendor Extension</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VENDOR_EXTENSION_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAITING_TIME__VALUE = 0;

    /**
     * The number of structural features of the '<em>Waiting Time</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WAITING_TIME_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_FAULT_CATCH__MESSAGE = 0;

    /**
     * The feature id for the '<em><b>Block Activity</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY = 1;

    /**
     * The feature id for the '<em><b>Transition Ref</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_FAULT_CATCH__TRANSITION_REF = 2;

    /**
     * The feature id for the '<em><b>Fault Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_FAULT_CATCH__FAULT_NAME = 3;

    /**
     * The number of structural features of the '<em>Web Service Fault Catch</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_FAULT_CATCH_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Other Attributes</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_OPERATION__OTHER_ATTRIBUTES =
            OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Partner</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_OPERATION__PARTNER =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_OPERATION__SERVICE =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Operation Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_OPERATION__OPERATION_NAME =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Web Service Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */

    int WEB_SERVICE_OPERATION_FEATURE_COUNT =
            OTHER_ATTRIBUTES_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Web Service Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION = 0;

    /**
     * The feature id for the '<em><b>Web Service Fault Catch</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_APPLICATION__WEB_SERVICE_FAULT_CATCH = 1;

    /**
     * The feature id for the '<em><b>Input Msg Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_APPLICATION__INPUT_MSG_NAME = 2;

    /**
     * The feature id for the '<em><b>Output Msg Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_APPLICATION__OUTPUT_MSG_NAME = 3;

    /**
     * The number of structural features of the '<em>Web Service Application</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WEB_SERVICE_APPLICATION_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_TIME__VALUE = 0;

    /**
     * The number of structural features of the '<em>Working Time</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_TIME_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XSLT_APPLICATION__LOCATION = 0;

    /**
     * The number of structural features of the '<em>Xslt Application</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XSLT_APPLICATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.AccessLevelType <em>Access Level Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AccessLevelType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAccessLevelType()
     * @generated
     */
    int ACCESS_LEVEL_TYPE = 168;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.AdHocOrderingType <em>Ad Hoc Ordering Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AdHocOrderingType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAdHocOrderingType()
     * @generated
     */
    int AD_HOC_ORDERING_TYPE = 169;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ArtifactType <em>Artifact Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ArtifactType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArtifactType()
     * @generated
     */
    int ARTIFACT_TYPE = 170;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.AssignTimeType <em>Assign Time Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AssignTimeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssignTimeType()
     * @generated
     */
    int ASSIGN_TIME_TYPE = 171;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.AssociationDirectionType <em>Association Direction Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AssociationDirectionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssociationDirectionType()
     * @generated
     */
    int ASSOCIATION_DIRECTION_TYPE = 172;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.DirectionType <em>Direction Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.DirectionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDirectionType()
     * @generated
     */
    int DIRECTION_TYPE = 177;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.DurationUnitType <em>Duration Unit Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.DurationUnitType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDurationUnitType()
     * @generated
     */
    int DURATION_UNIT_TYPE = 178;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.EndPointTypeType <em>End Point Type Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.EndPointTypeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEndPointTypeType()
     * @generated
     */
    int END_POINT_TYPE_TYPE = 179;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ExclusiveType <em>Exclusive Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ExclusiveType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExclusiveType()
     * @generated
     */
    int EXCLUSIVE_TYPE = 180;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ExecutionType <em>Execution Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ExecutionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExecutionType()
     * @generated
     */
    int EXECUTION_TYPE = 181;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.FinishModeType <em>Finish Mode Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.FinishModeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFinishModeType()
     * @generated
     */
    int FINISH_MODE_TYPE = 182;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.JoinSplitType <em>Join Split Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.JoinSplitType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getJoinSplitType()
     * @generated
     */
    int JOIN_SPLIT_TYPE = 183;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.GraphConformanceType <em>Graph Conformance Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.GraphConformanceType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGraphConformanceType()
     * @generated
     */
    int GRAPH_CONFORMANCE_TYPE = 185;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ImplementationType <em>Implementation Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ImplementationType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getImplementationType()
     * @generated
     */
    int IMPLEMENTATION_TYPE = 186;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.InstantiationType <em>Instantiation Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.InstantiationType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getInstantiationType()
     * @generated
     */
    int INSTANTIATION_TYPE = 187;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.IsArrayType <em>Is Array Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.IsArrayType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIsArrayType()
     * @generated
     */
    int IS_ARRAY_TYPE = 188;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.LoopType <em>Loop Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.LoopType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoopType()
     * @generated
     */
    int LOOP_TYPE = 189;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.MIFlowConditionType <em>MI Flow Condition Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.MIFlowConditionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMIFlowConditionType()
     * @generated
     */
    int MI_FLOW_CONDITION_TYPE = 190;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.MIOrderingType <em>MI Ordering Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.MIOrderingType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMIOrderingType()
     * @generated
     */
    int MI_ORDERING_TYPE = 191;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ModeType <em>Mode Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ModeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getModeType()
     * @generated
     */
    int MODE_TYPE = 192;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.OrientationType <em>Orientation Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.OrientationType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOrientationType()
     * @generated
     */
    int ORIENTATION_TYPE = 193;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ProcessType <em>Process Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ProcessType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcessType()
     * @generated
     */
    int PROCESS_TYPE = 195;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.PublicationStatusType <em>Publication Status Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.PublicationStatusType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPublicationStatusType()
     * @generated
     */
    int PUBLICATION_STATUS_TYPE = 196;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ResultType <em>Result Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ResultType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResultType()
     * @generated
     */
    int RESULT_TYPE = 197;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.RoleType <em>Role Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.RoleType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRoleType()
     * @generated
     */
    int ROLE_TYPE = 198;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.SHAPEType <em>SHAPE Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.SHAPEType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSHAPEType()
     * @generated
     */
    int SHAPE_TYPE = 199;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.StartModeType <em>Start Mode Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.StartModeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStartModeType()
     * @generated
     */
    int START_MODE_TYPE = 200;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.StatusType <em>Status Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.StatusType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStatusType()
     * @generated
     */
    int STATUS_TYPE = 201;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.TestTimeType <em>Test Time Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.TestTimeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTestTimeType()
     * @generated
     */
    int TEST_TIME_TYPE = 202;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.TransactionMethodType <em>Transaction Method Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.TransactionMethodType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransactionMethodType()
     * @generated
     */
    int TRANSACTION_METHOD_TYPE = 203;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.TriggerType <em>Trigger Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.TriggerType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerType()
     * @generated
     */
    int TRIGGER_TYPE = 204;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ViewType <em>View Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ViewType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getViewType()
     * @generated
     */
    int VIEW_TYPE = 205;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.DeprecatedXorType <em>Deprecated Xor Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.DeprecatedXorType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeprecatedXorType()
     * @generated
     */
    int DEPRECATED_XOR_TYPE = 206;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ConditionType <em>Condition Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ConditionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getConditionType()
     * @generated
     */
    int CONDITION_TYPE = 176;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.ParticipantType <em>Participant Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ParticipantType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getParticipantType()
     * @generated
     */
    int PARTICIPANT_TYPE = 194;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.BasicTypeType <em>Basic Type Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.BasicTypeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBasicTypeType()
     * @generated
     */
    int BASIC_TYPE_TYPE = 173;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance <em>BPMN Model Portability Conformance</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBPMNModelPortabilityConformance()
     * @generated
     */
    int BPMN_MODEL_PORTABILITY_CONFORMANCE = 174;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.CatchThrow <em>Catch Throw</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.CatchThrow
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCatchThrow()
     * @generated
     */
    int CATCH_THROW = 175;

    /**
     * The meta object id for the '{@link com.tibco.xpd.xpdl2.GatewayType <em>Gateway Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.GatewayType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGatewayType()
     * @generated
     */
    int GATEWAY_TYPE = 184;

    /**
     * The meta object id for the '<em>Access Level Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AccessLevelType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAccessLevelTypeObject()
     * @generated
     */
    int ACCESS_LEVEL_TYPE_OBJECT = 207;

    /**
     * The meta object id for the '<em>Ad Hoc Ordering Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AdHocOrderingType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAdHocOrderingTypeObject()
     * @generated
     */
    int AD_HOC_ORDERING_TYPE_OBJECT = 208;

    /**
     * The meta object id for the '<em>Artifact Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ArtifactType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArtifactTypeObject()
     * @generated
     */
    int ARTIFACT_TYPE_OBJECT = 209;

    /**
     * The meta object id for the '<em>Assign Time Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AssignTimeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssignTimeTypeObject()
     * @generated
     */
    int ASSIGN_TIME_TYPE_OBJECT = 210;

    /**
     * The meta object id for the '<em>Association Direction Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.AssociationDirectionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssociationDirectionTypeObject()
     * @generated
     */
    int ASSOCIATION_DIRECTION_TYPE_OBJECT = 211;

    /**
     * The meta object id for the '<em>Direction Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.DirectionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDirectionTypeObject()
     * @generated
     */
    int DIRECTION_TYPE_OBJECT = 212;

    /**
     * The meta object id for the '<em>Duration Unit Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.DurationUnitType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDurationUnitTypeObject()
     * @generated
     */
    int DURATION_UNIT_TYPE_OBJECT = 213;

    /**
     * The meta object id for the '<em>End Point Type Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.EndPointTypeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEndPointTypeTypeObject()
     * @generated
     */
    int END_POINT_TYPE_TYPE_OBJECT = 214;

    /**
     * The meta object id for the '<em>Execution Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ExecutionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExecutionTypeObject()
     * @generated
     */
    int EXECUTION_TYPE_OBJECT = 215;

    /**
     * The meta object id for the '<em>Finish Mode Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.FinishModeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFinishModeTypeObject()
     * @generated
     */
    int FINISH_MODE_TYPE_OBJECT = 216;

    /**
     * The meta object id for the '<em>Gateway Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.JoinSplitType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGatewayTypeObject()
     * @generated
     */
    int GATEWAY_TYPE_OBJECT = 217;

    /**
     * The meta object id for the '<em>Graph Conformance Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.GraphConformanceType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGraphConformanceTypeObject()
     * @generated
     */
    int GRAPH_CONFORMANCE_TYPE_OBJECT = 218;

    /**
     * The meta object id for the '<em>Implementation Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.common.util.Enumerator
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getImplementationTypeObject()
     * @generated
     */
    int IMPLEMENTATION_TYPE_OBJECT = 220;

    /**
     * The meta object id for the '<em>Instantiation Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.InstantiationType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getInstantiationTypeObject()
     * @generated
     */
    int INSTANTIATION_TYPE_OBJECT = 221;

    /**
     * The meta object id for the '<em>Is Array Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.IsArrayType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIsArrayTypeObject()
     * @generated
     */
    int IS_ARRAY_TYPE_OBJECT = 222;

    /**
     * The meta object id for the '<em>Loop Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.LoopType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoopTypeObject()
     * @generated
     */
    int LOOP_TYPE_OBJECT = 223;

    /**
     * The meta object id for the '<em>MI Flow Condition Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.MIFlowConditionType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMIFlowConditionTypeObject()
     * @generated
     */
    int MI_FLOW_CONDITION_TYPE_OBJECT = 224;

    /**
     * The meta object id for the '<em>MI Ordering Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.MIOrderingType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMIOrderingTypeObject()
     * @generated
     */
    int MI_ORDERING_TYPE_OBJECT = 225;

    /**
     * The meta object id for the '<em>Mode Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ModeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getModeTypeObject()
     * @generated
     */
    int MODE_TYPE_OBJECT = 226;

    /**
     * The meta object id for the '<em>Orientation Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.OrientationType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOrientationTypeObject()
     * @generated
     */
    int ORIENTATION_TYPE_OBJECT = 227;

    /**
     * The meta object id for the '<em>Process Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ProcessType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcessTypeObject()
     * @generated
     */
    int PROCESS_TYPE_OBJECT = 228;

    /**
     * The meta object id for the '<em>Publication Status Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.PublicationStatusType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPublicationStatusTypeObject()
     * @generated
     */
    int PUBLICATION_STATUS_TYPE_OBJECT = 229;

    /**
     * The meta object id for the '<em>Result Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.ResultType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResultTypeObject()
     * @generated
     */
    int RESULT_TYPE_OBJECT = 230;

    /**
     * The meta object id for the '<em>Role Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.RoleType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRoleTypeObject()
     * @generated
     */
    int ROLE_TYPE_OBJECT = 231;

    /**
     * The meta object id for the '<em>SHAPE Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.SHAPEType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSHAPETypeObject()
     * @generated
     */
    int SHAPE_TYPE_OBJECT = 232;

    /**
     * The meta object id for the '<em>Start Mode Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.StartModeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStartModeTypeObject()
     * @generated
     */
    int START_MODE_TYPE_OBJECT = 233;

    /**
     * The meta object id for the '<em>Status Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.StatusType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStatusTypeObject()
     * @generated
     */
    int STATUS_TYPE_OBJECT = 234;

    /**
     * The meta object id for the '<em>Test Time Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.TestTimeType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTestTimeTypeObject()
     * @generated
     */
    int TEST_TIME_TYPE_OBJECT = 235;

    /**
     * The meta object id for the '<em>Transaction Method Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.TransactionMethodType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransactionMethodTypeObject()
     * @generated
     */
    int TRANSACTION_METHOD_TYPE_OBJECT = 236;

    /**
     * The meta object id for the '<em>Trigger Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.xpdl2.TriggerType
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerTypeObject()
     * @generated
     */
    int TRIGGER_TYPE_OBJECT = 237;

    /**
     * The meta object id for the '<em>Id Reference String</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIdReferenceString()
     * @generated
     */
    int ID_REFERENCE_STRING = 219;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ActivitySet <em>Activity Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activity Set</em>'.
     * @see com.tibco.xpd.xpdl2.ActivitySet
     * @generated
     */
    EClass getActivitySet();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ActivitySet#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.ActivitySet#getObject()
     * @see #getActivitySet()
     * @generated
     */
    EReference getActivitySet_Object();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.ActivitySet#getProcess <em>Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Process</em>'.
     * @see com.tibco.xpd.xpdl2.ActivitySet#getProcess()
     * @see #getActivitySet()
     * @generated
     */
    EReference getActivitySet_Process();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Activity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activity</em>'.
     * @see com.tibco.xpd.xpdl2.Activity
     * @generated
     */
    EClass getActivity();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getLimit <em>Limit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Limit</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getLimit()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Limit();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getRoute <em>Route</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Route</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getRoute()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Route();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getImplementation()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Implementation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getBlockActivity <em>Block Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Block Activity</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getBlockActivity()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_BlockActivity();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getEvent <em>Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Event</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getEvent()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Event();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getTransaction <em>Transaction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Transaction</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getTransaction()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Transaction();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getPerformer <em>Performer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Performer</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getPerformer()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Performer();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getPerformers <em>Performers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Performers</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getPerformers()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Performers();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getPriority <em>Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Priority</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getPriority()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Priority();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Activity#getDeadline <em>Deadline</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Deadline</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getDeadline()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Deadline();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getSimulationInformation <em>Simulation Information</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Simulation Information</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getSimulationInformation()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_SimulationInformation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getIcon <em>Icon</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Icon</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getIcon()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Icon();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getDocumentation <em>Documentation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Documentation</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getDocumentation()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Documentation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Activity#getTransitionRestrictions <em>Transition Restrictions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Transition Restrictions</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getTransitionRestrictions()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_TransitionRestrictions();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Activity#getInputSets <em>Input Sets</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Input Sets</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getInputSets()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_InputSets();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getOutputSets <em>Output Sets</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Output Sets</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getOutputSets()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_OutputSets();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getIoRules <em>Io Rules</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Io Rules</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getIoRules()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_IoRules();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getLoop <em>Loop</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Loop</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getLoop()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Loop();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Activity#getAssignments <em>Assignments</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Assignments</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getAssignments()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Assignments();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getObject()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Object();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Activity#getExtensions <em>Extensions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Extensions</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getExtensions()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Extensions();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Activity#getFinishMode <em>Finish Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Finish Mode</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getFinishMode()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_FinishMode();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Activity#isIsATransaction <em>Is ATransaction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is ATransaction</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#isIsATransaction()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_IsATransaction();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Activity#isStartActivity <em>Start Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Activity</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#isStartActivity()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_StartActivity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Activity#getStartMode <em>Start Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Mode</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getStartMode()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_StartMode();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Activity#getStartQuantity <em>Start Quantity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Quantity</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getStartQuantity()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_StartQuantity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Activity#getStatus <em>Status</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Status</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getStatus()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_Status();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.Activity#getFlowContainer <em>Flow Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Flow Container</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getFlowContainer()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_FlowContainer();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.xpdl2.Activity#getProcess <em>Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Process</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getProcess()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Process();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Activity#isIsForCompensation <em>Is For Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is For Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#isIsForCompensation()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_IsForCompensation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Activity#getCompletionQuantity <em>Completion Quantity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Completion Quantity</em>'.
     * @see com.tibco.xpd.xpdl2.Activity#getCompletionQuantity()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_CompletionQuantity();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ApplicationType <em>Application Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Application Type</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType
     * @generated
     */
    EClass getApplicationType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ApplicationType#getEjb <em>Ejb</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Ejb</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType#getEjb()
     * @see #getApplicationType()
     * @generated
     */
    EReference getApplicationType_Ejb();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ApplicationType#getPojo <em>Pojo</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Pojo</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType#getPojo()
     * @see #getApplicationType()
     * @generated
     */
    EReference getApplicationType_Pojo();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ApplicationType#getXslt <em>Xslt</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Xslt</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType#getXslt()
     * @see #getApplicationType()
     * @generated
     */
    EReference getApplicationType_Xslt();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ApplicationType#getScript <em>Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Script</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType#getScript()
     * @see #getApplicationType()
     * @generated
     */
    EReference getApplicationType_Script();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ApplicationType#getWebService <em>Web Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Web Service</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType#getWebService()
     * @see #getApplicationType()
     * @generated
     */
    EReference getApplicationType_WebService();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ApplicationType#getBusinessRule <em>Business Rule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Business Rule</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType#getBusinessRule()
     * @see #getApplicationType()
     * @generated
     */
    EReference getApplicationType_BusinessRule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ApplicationType#getForm <em>Form</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Form</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType#getForm()
     * @see #getApplicationType()
     * @generated
     */
    EReference getApplicationType_Form();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.ApplicationType#getAnyAttribute <em>Any Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any Attribute</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationType#getAnyAttribute()
     * @see #getApplicationType()
     * @generated
     */
    EAttribute getApplicationType_AnyAttribute();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Application <em>Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Application</em>'.
     * @see com.tibco.xpd.xpdl2.Application
     * @generated
     */
    EClass getApplication();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Application#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Type</em>'.
     * @see com.tibco.xpd.xpdl2.Application#getType()
     * @see #getApplication()
     * @generated
     */
    EReference getApplication_Type();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Application#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdl2.Application#getExternalReference()
     * @see #getApplication()
     * @generated
     */
    EReference getApplication_ExternalReference();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ExtendedAttributesContainer <em>Extended Attributes Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Extended Attributes Container</em>'.
     * @see com.tibco.xpd.xpdl2.ExtendedAttributesContainer
     * @generated
     */
    EClass getExtendedAttributesContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.ExtendedAttributesContainer#getExtendedAttributes <em>Extended Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Extended Attributes</em>'.
     * @see com.tibco.xpd.xpdl2.ExtendedAttributesContainer#getExtendedAttributes()
     * @see #getExtendedAttributesContainer()
     * @generated
     */
    EReference getExtendedAttributesContainer_ExtendedAttributes();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ArrayType <em>Array Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Array Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType
     * @generated
     */
    EClass getArrayType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getBasicType <em>Basic Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Basic Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getBasicType()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_BasicType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getDeclaredType <em>Declared Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Declared Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getDeclaredType()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_DeclaredType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getSchemaType <em>Schema Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schema Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getSchemaType()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_SchemaType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getExternalReference()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_ExternalReference();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getRecordType <em>Record Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Record Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getRecordType()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_RecordType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getUnionType <em>Union Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Union Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getUnionType()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_UnionType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getEnumerationType <em>Enumeration Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Enumeration Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getEnumerationType()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_EnumerationType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getArrayType <em>Array Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Array Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getArrayType()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_ArrayType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ArrayType#getListType <em>List Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>List Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getListType()
     * @see #getArrayType()
     * @generated
     */
    EReference getArrayType_ListType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ArrayType#getLowerIndex <em>Lower Index</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lower Index</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getLowerIndex()
     * @see #getArrayType()
     * @generated
     */
    EAttribute getArrayType_LowerIndex();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ArrayType#getUpperIndex <em>Upper Index</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Upper Index</em>'.
     * @see com.tibco.xpd.xpdl2.ArrayType#getUpperIndex()
     * @see #getArrayType()
     * @generated
     */
    EAttribute getArrayType_UpperIndex();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Artifact <em>Artifact</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Artifact</em>'.
     * @see com.tibco.xpd.xpdl2.Artifact
     * @generated
     */
    EClass getArtifact();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Artifact#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Artifact#getObject()
     * @see #getArtifact()
     * @generated
     */
    EReference getArtifact_Object();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Artifact#getDataObject <em>Data Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Data Object</em>'.
     * @see com.tibco.xpd.xpdl2.Artifact#getDataObject()
     * @see #getArtifact()
     * @generated
     */
    EReference getArtifact_DataObject();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Artifact#getArtifactType <em>Artifact Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Artifact Type</em>'.
     * @see com.tibco.xpd.xpdl2.Artifact#getArtifactType()
     * @see #getArtifact()
     * @generated
     */
    EAttribute getArtifact_ArtifactType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Artifact#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Group</em>'.
     * @see com.tibco.xpd.xpdl2.Artifact#getGroup()
     * @see #getArtifact()
     * @generated
     */
    EReference getArtifact_Group();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ArtifactInput <em>Artifact Input</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Artifact Input</em>'.
     * @see com.tibco.xpd.xpdl2.ArtifactInput
     * @generated
     */
    EClass getArtifactInput();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ArtifactInput#getArtifactId <em>Artifact Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Artifact Id</em>'.
     * @see com.tibco.xpd.xpdl2.ArtifactInput#getArtifactId()
     * @see #getArtifactInput()
     * @generated
     */
    EAttribute getArtifactInput_ArtifactId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ArtifactInput#isRequiredForStart <em>Required For Start</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Required For Start</em>'.
     * @see com.tibco.xpd.xpdl2.ArtifactInput#isRequiredForStart()
     * @see #getArtifactInput()
     * @generated
     */
    EAttribute getArtifactInput_RequiredForStart();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Artifact#getTextAnnotation <em>Text Annotation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Text Annotation</em>'.
     * @see com.tibco.xpd.xpdl2.Artifact#getTextAnnotation()
     * @see #getArtifact()
     * @generated
     */
    EAttribute getArtifact_TextAnnotation();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.Artifact#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Package</em>'.
     * @see com.tibco.xpd.xpdl2.Artifact#getPackage()
     * @see #getArtifact()
     * @generated
     */
    EReference getArtifact_Package();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.GraphicalNode <em>Graphical Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Graphical Node</em>'.
     * @see com.tibco.xpd.xpdl2.GraphicalNode
     * @generated
     */
    EClass getGraphicalNode();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.GraphicalNode#getNodeGraphicsInfos <em>Node Graphics Infos</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Node Graphics Infos</em>'.
     * @see com.tibco.xpd.xpdl2.GraphicalNode#getNodeGraphicsInfos()
     * @see #getGraphicalNode()
     * @generated
     */
    EReference getGraphicalNode_NodeGraphicsInfos();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.GraphicalConnector <em>Graphical Connector</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Graphical Connector</em>'.
     * @see com.tibco.xpd.xpdl2.GraphicalConnector
     * @generated
     */
    EClass getGraphicalConnector();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.GraphicalConnector#getConnectorGraphicsInfos <em>Connector Graphics Infos</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Connector Graphics Infos</em>'.
     * @see com.tibco.xpd.xpdl2.GraphicalConnector#getConnectorGraphicsInfos()
     * @see #getGraphicalConnector()
     * @generated
     */
    EReference getGraphicalConnector_ConnectorGraphicsInfos();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Group <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Group</em>'.
     * @see com.tibco.xpd.xpdl2.Group
     * @generated
     */
    EClass getGroup();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Group#getCategory <em>Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Category</em>'.
     * @see com.tibco.xpd.xpdl2.Group#getCategory()
     * @see #getGroup()
     * @generated
     */
    EReference getGroup_Category();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Group#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Group#getObject()
     * @see #getGroup()
     * @generated
     */
    EReference getGroup_Object();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Assignment <em>Assignment</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Assignment</em>'.
     * @see com.tibco.xpd.xpdl2.Assignment
     * @generated
     */
    EClass getAssignment();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Assignment#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Target</em>'.
     * @see com.tibco.xpd.xpdl2.Assignment#getTarget()
     * @see #getAssignment()
     * @generated
     */
    EReference getAssignment_Target();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Assignment#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Expression</em>'.
     * @see com.tibco.xpd.xpdl2.Assignment#getExpression()
     * @see #getAssignment()
     * @generated
     */
    EReference getAssignment_Expression();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Assignment#getAssignTime <em>Assign Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Assign Time</em>'.
     * @see com.tibco.xpd.xpdl2.Assignment#getAssignTime()
     * @see #getAssignment()
     * @generated
     */
    EAttribute getAssignment_AssignTime();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Association <em>Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Association</em>'.
     * @see com.tibco.xpd.xpdl2.Association
     * @generated
     */
    EClass getAssociation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Association#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Association#getObject()
     * @see #getAssociation()
     * @generated
     */
    EReference getAssociation_Object();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Association#getAssociationDirection <em>Association Direction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Association Direction</em>'.
     * @see com.tibco.xpd.xpdl2.Association#getAssociationDirection()
     * @see #getAssociation()
     * @generated
     */
    EAttribute getAssociation_AssociationDirection();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Association#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Source</em>'.
     * @see com.tibco.xpd.xpdl2.Association#getSource()
     * @see #getAssociation()
     * @generated
     */
    EAttribute getAssociation_Source();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Association#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target</em>'.
     * @see com.tibco.xpd.xpdl2.Association#getTarget()
     * @see #getAssociation()
     * @generated
     */
    EAttribute getAssociation_Target();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.Association#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Package</em>'.
     * @see com.tibco.xpd.xpdl2.Association#getPackage()
     * @see #getAssociation()
     * @generated
     */
    EReference getAssociation_Package();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.BasicType <em>Basic Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Basic Type</em>'.
     * @see com.tibco.xpd.xpdl2.BasicType
     * @generated
     */
    EClass getBasicType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.BasicType#getLength <em>Length</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Length</em>'.
     * @see com.tibco.xpd.xpdl2.BasicType#getLength()
     * @see #getBasicType()
     * @generated
     */
    EReference getBasicType_Length();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.BasicType#getPrecision <em>Precision</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Precision</em>'.
     * @see com.tibco.xpd.xpdl2.BasicType#getPrecision()
     * @see #getBasicType()
     * @generated
     */
    EReference getBasicType_Precision();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.BasicType#getScale <em>Scale</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Scale</em>'.
     * @see com.tibco.xpd.xpdl2.BasicType#getScale()
     * @see #getBasicType()
     * @generated
     */
    EReference getBasicType_Scale();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.BasicType#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.xpdl2.BasicType#getType()
     * @see #getBasicType()
     * @generated
     */
    EAttribute getBasicType_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.BlockActivity <em>Block Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Block Activity</em>'.
     * @see com.tibco.xpd.xpdl2.BlockActivity
     * @generated
     */
    EClass getBlockActivity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.BlockActivity#getActivitySetId <em>Activity Set Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Set Id</em>'.
     * @see com.tibco.xpd.xpdl2.BlockActivity#getActivitySetId()
     * @see #getBlockActivity()
     * @generated
     */
    EAttribute getBlockActivity_ActivitySetId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.BlockActivity#getStartActivityId <em>Start Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Activity Id</em>'.
     * @see com.tibco.xpd.xpdl2.BlockActivity#getStartActivityId()
     * @see #getBlockActivity()
     * @generated
     */
    EAttribute getBlockActivity_StartActivityId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.BlockActivity#getView <em>View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>View</em>'.
     * @see com.tibco.xpd.xpdl2.BlockActivity#getView()
     * @see #getBlockActivity()
     * @generated
     */
    EAttribute getBlockActivity_View();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.BusinessRuleApplication <em>Business Rule Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Business Rule Application</em>'.
     * @see com.tibco.xpd.xpdl2.BusinessRuleApplication
     * @generated
     */
    EClass getBusinessRuleApplication();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.BusinessRuleApplication#getRuleName <em>Rule Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Rule Name</em>'.
     * @see com.tibco.xpd.xpdl2.BusinessRuleApplication#getRuleName()
     * @see #getBusinessRuleApplication()
     * @generated
     */
    EReference getBusinessRuleApplication_RuleName();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.BusinessRuleApplication#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Location</em>'.
     * @see com.tibco.xpd.xpdl2.BusinessRuleApplication#getLocation()
     * @see #getBusinessRuleApplication()
     * @generated
     */
    EReference getBusinessRuleApplication_Location();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Category <em>Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Category</em>'.
     * @see com.tibco.xpd.xpdl2.Category
     * @generated
     */
    EClass getCategory();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Class <em>Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Class</em>'.
     * @see com.tibco.xpd.xpdl2.Class
     * @generated
     */
    EClass getClass_();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Class#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Class#getValue()
     * @see #getClass_()
     * @generated
     */
    EAttribute getClass_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Codepage <em>Codepage</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Codepage</em>'.
     * @see com.tibco.xpd.xpdl2.Codepage
     * @generated
     */
    EClass getCodepage();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Codepage#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Codepage#getValue()
     * @see #getCodepage()
     * @generated
     */
    EAttribute getCodepage_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Condition <em>Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Condition</em>'.
     * @see com.tibco.xpd.xpdl2.Condition
     * @generated
     */
    EClass getCondition();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.Condition#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.xpdl2.Condition#getMixed()
     * @see #getCondition()
     * @generated
     */
    EAttribute getCondition_Mixed();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Condition#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Expression</em>'.
     * @see com.tibco.xpd.xpdl2.Condition#getExpression()
     * @see #getCondition()
     * @generated
     */
    EReference getCondition_Expression();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Condition#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.xpdl2.Condition#getType()
     * @see #getCondition()
     * @generated
     */
    EAttribute getCondition_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ConformanceClass <em>Conformance Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Conformance Class</em>'.
     * @see com.tibco.xpd.xpdl2.ConformanceClass
     * @generated
     */
    EClass getConformanceClass();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ConformanceClass#getGraphConformance <em>Graph Conformance</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Graph Conformance</em>'.
     * @see com.tibco.xpd.xpdl2.ConformanceClass#getGraphConformance()
     * @see #getConformanceClass()
     * @generated
     */
    EAttribute getConformanceClass_GraphConformance();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ConformanceClass#getBpmnModelPortabilityConformance <em>Bpmn Model Portability Conformance</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Bpmn Model Portability Conformance</em>'.
     * @see com.tibco.xpd.xpdl2.ConformanceClass#getBpmnModelPortabilityConformance()
     * @see #getConformanceClass()
     * @generated
     */
    EAttribute getConformanceClass_BpmnModelPortabilityConformance();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo <em>Connector Graphics Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connector Graphics Info</em>'.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo
     * @generated
     */
    EClass getConnectorGraphicsInfo();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getCoordinates <em>Coordinates</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Coordinates</em>'.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getCoordinates()
     * @see #getConnectorGraphicsInfo()
     * @generated
     */
    EReference getConnectorGraphicsInfo_Coordinates();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getBorderColor <em>Border Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Border Color</em>'.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getBorderColor()
     * @see #getConnectorGraphicsInfo()
     * @generated
     */
    EAttribute getConnectorGraphicsInfo_BorderColor();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getFillColor <em>Fill Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Fill Color</em>'.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getFillColor()
     * @see #getConnectorGraphicsInfo()
     * @generated
     */
    EAttribute getConnectorGraphicsInfo_FillColor();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#isIsVisible <em>Is Visible</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Visible</em>'.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#isIsVisible()
     * @see #getConnectorGraphicsInfo()
     * @generated
     */
    EAttribute getConnectorGraphicsInfo_IsVisible();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getPageId <em>Page Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Page Id</em>'.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getPageId()
     * @see #getConnectorGraphicsInfo()
     * @generated
     */
    EAttribute getConnectorGraphicsInfo_PageId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getStyle <em>Style</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Style</em>'.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getStyle()
     * @see #getConnectorGraphicsInfo()
     * @generated
     */
    EAttribute getConnectorGraphicsInfo_Style();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getToolId <em>Tool Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Tool Id</em>'.
     * @see com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getToolId()
     * @see #getConnectorGraphicsInfo()
     * @generated
     */
    EAttribute getConnectorGraphicsInfo_ToolId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Coordinates <em>Coordinates</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Coordinates</em>'.
     * @see com.tibco.xpd.xpdl2.Coordinates
     * @generated
     */
    EClass getCoordinates();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Coordinates#getXCoordinate <em>XCoordinate</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>XCoordinate</em>'.
     * @see com.tibco.xpd.xpdl2.Coordinates#getXCoordinate()
     * @see #getCoordinates()
     * @generated
     */
    EAttribute getCoordinates_XCoordinate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Coordinates#getYCoordinate <em>YCoordinate</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>YCoordinate</em>'.
     * @see com.tibco.xpd.xpdl2.Coordinates#getYCoordinate()
     * @see #getCoordinates()
     * @generated
     */
    EAttribute getCoordinates_YCoordinate();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Cost <em>Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cost</em>'.
     * @see com.tibco.xpd.xpdl2.Cost
     * @generated
     */
    EClass getCost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Cost#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Cost#getValue()
     * @see #getCost()
     * @generated
     */
    EAttribute getCost_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.CostStructure <em>Cost Structure</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cost Structure</em>'.
     * @see com.tibco.xpd.xpdl2.CostStructure
     * @generated
     */
    EClass getCostStructure();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.CostStructure#getFixedCost <em>Fixed Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Fixed Cost</em>'.
     * @see com.tibco.xpd.xpdl2.CostStructure#getFixedCost()
     * @see #getCostStructure()
     * @generated
     */
    EAttribute getCostStructure_FixedCost();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.CostStructure#getResourceCosts <em>Resource Costs</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Resource Costs</em>'.
     * @see com.tibco.xpd.xpdl2.CostStructure#getResourceCosts()
     * @see #getCostStructure()
     * @generated
     */
    EReference getCostStructure_ResourceCosts();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.CostUnit <em>Cost Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cost Unit</em>'.
     * @see com.tibco.xpd.xpdl2.CostUnit
     * @generated
     */
    EClass getCostUnit();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.CostUnit#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.CostUnit#getValue()
     * @see #getCostUnit()
     * @generated
     */
    EAttribute getCostUnit_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.CountryKey <em>Country Key</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Country Key</em>'.
     * @see com.tibco.xpd.xpdl2.CountryKey
     * @generated
     */
    EClass getCountryKey();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.CountryKey#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.CountryKey#getValue()
     * @see #getCountryKey()
     * @generated
     */
    EAttribute getCountryKey_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DataField <em>Data Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Field</em>'.
     * @see com.tibco.xpd.xpdl2.DataField
     * @generated
     */
    EClass getDataField();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.DataField#getInitialValue <em>Initial Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Initial Value</em>'.
     * @see com.tibco.xpd.xpdl2.DataField#getInitialValue()
     * @see #getDataField()
     * @generated
     */
    EReference getDataField_InitialValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DataField#isCorrelation <em>Correlation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Correlation</em>'.
     * @see com.tibco.xpd.xpdl2.DataField#isCorrelation()
     * @see #getDataField()
     * @generated
     */
    EAttribute getDataField_Correlation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DataField#getDeprecatedDataIsArray <em>Deprecated Data Is Array</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Data Is Array</em>'.
     * @see com.tibco.xpd.xpdl2.DataField#getDeprecatedDataIsArray()
     * @see #getDataField()
     * @generated
     */
    EAttribute getDataField_DeprecatedDataIsArray();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DataMapping <em>Data Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Mapping</em>'.
     * @see com.tibco.xpd.xpdl2.DataMapping
     * @generated
     */
    EClass getDataMapping();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.DataMapping#getActual <em>Actual</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Actual</em>'.
     * @see com.tibco.xpd.xpdl2.DataMapping#getActual()
     * @see #getDataMapping()
     * @generated
     */
    EReference getDataMapping_Actual();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DataMapping#getDirection <em>Direction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Direction</em>'.
     * @see com.tibco.xpd.xpdl2.DataMapping#getDirection()
     * @see #getDataMapping()
     * @generated
     */
    EAttribute getDataMapping_Direction();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DataMapping#getFormal <em>Formal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Formal</em>'.
     * @see com.tibco.xpd.xpdl2.DataMapping#getFormal()
     * @see #getDataMapping()
     * @generated
     */
    EAttribute getDataMapping_Formal();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.DataMapping#getTestValue <em>Test Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Test Value</em>'.
     * @see com.tibco.xpd.xpdl2.DataMapping#getTestValue()
     * @see #getDataMapping()
     * @generated
     */
    EReference getDataMapping_TestValue();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DataObject <em>Data Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Object</em>'.
     * @see com.tibco.xpd.xpdl2.DataObject
     * @generated
     */
    EClass getDataObject();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedProducedAtCompletion <em>Deprecated Produced At Completion</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Produced At Completion</em>'.
     * @see com.tibco.xpd.xpdl2.DataObject#isDeprecatedProducedAtCompletion()
     * @see #getDataObject()
     * @generated
     */
    EAttribute getDataObject_DeprecatedProducedAtCompletion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DataObject#isDeprecatedRequiredForStart <em>Deprecated Required For Start</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Required For Start</em>'.
     * @see com.tibco.xpd.xpdl2.DataObject#isDeprecatedRequiredForStart()
     * @see #getDataObject()
     * @generated
     */
    EAttribute getDataObject_DeprecatedRequiredForStart();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DataObject#getState <em>State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>State</em>'.
     * @see com.tibco.xpd.xpdl2.DataObject#getState()
     * @see #getDataObject()
     * @generated
     */
    EAttribute getDataObject_State();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DataType <em>Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Type</em>'.
     * @see com.tibco.xpd.xpdl2.DataType
     * @generated
     */
    EClass getDataType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Deadline <em>Deadline</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Deadline</em>'.
     * @see com.tibco.xpd.xpdl2.Deadline
     * @generated
     */
    EClass getDeadline();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Deadline#getDeadlineDuration <em>Deadline Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deadline Duration</em>'.
     * @see com.tibco.xpd.xpdl2.Deadline#getDeadlineDuration()
     * @see #getDeadline()
     * @generated
     */
    EReference getDeadline_DeadlineDuration();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Deadline#getExceptionName <em>Exception Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Exception Name</em>'.
     * @see com.tibco.xpd.xpdl2.Deadline#getExceptionName()
     * @see #getDeadline()
     * @generated
     */
    EReference getDeadline_ExceptionName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Deadline#getExecution <em>Execution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Execution</em>'.
     * @see com.tibco.xpd.xpdl2.Deadline#getExecution()
     * @see #getDeadline()
     * @generated
     */
    EAttribute getDeadline_Execution();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DeclaredType <em>Declared Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Declared Type</em>'.
     * @see com.tibco.xpd.xpdl2.DeclaredType
     * @generated
     */
    EClass getDeclaredType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DeclaredType#getTypeDeclarationId <em>Type Declaration Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type Declaration Id</em>'.
     * @see com.tibco.xpd.xpdl2.DeclaredType#getTypeDeclarationId()
     * @see #getDeclaredType()
     * @generated
     */
    EAttribute getDeclaredType_TypeDeclarationId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DeclaredType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.DeclaredType#getName()
     * @see #getDeclaredType()
     * @generated
     */
    EAttribute getDeclaredType_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DeprecatedResultCompensation <em>Deprecated Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Deprecated Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.DeprecatedResultCompensation
     * @generated
     */
    EClass getDeprecatedResultCompensation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DeprecatedResultCompensation#getActivityId <em>Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Id</em>'.
     * @see com.tibco.xpd.xpdl2.DeprecatedResultCompensation#getActivityId()
     * @see #getDeprecatedResultCompensation()
     * @generated
     */
    EAttribute getDeprecatedResultCompensation_ActivityId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Deprecated Trigger Rule</em>'.
     * @see com.tibco.xpd.xpdl2.DeprecatedTriggerRule
     * @generated
     */
    EClass getDeprecatedTriggerRule();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.DeprecatedTriggerRule#getRuleName <em>Rule Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Rule Name</em>'.
     * @see com.tibco.xpd.xpdl2.DeprecatedTriggerRule#getRuleName()
     * @see #getDeprecatedTriggerRule()
     * @generated
     */
    EAttribute getDeprecatedTriggerRule_RuleName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Description <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Description</em>'.
     * @see com.tibco.xpd.xpdl2.Description
     * @generated
     */
    EClass getDescription();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Description#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Description#getValue()
     * @see #getDescription()
     * @generated
     */
    EAttribute getDescription_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Documentation <em>Documentation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Documentation</em>'.
     * @see com.tibco.xpd.xpdl2.Documentation
     * @generated
     */
    EClass getDocumentation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Documentation#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Documentation#getValue()
     * @see #getDocumentation()
     * @generated
     */
    EAttribute getDocumentation_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.xpdl2.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.xpdl2.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.xpdl2.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.xpdl2.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.xpdl2.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.xpdl2.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.DocumentRoot#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Package</em>'.
     * @see com.tibco.xpd.xpdl2.DocumentRoot#getPackage()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Package();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Duration <em>Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Duration</em>'.
     * @see com.tibco.xpd.xpdl2.Duration
     * @generated
     */
    EClass getDuration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Duration#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Duration#getValue()
     * @see #getDuration()
     * @generated
     */
    EAttribute getDuration_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.EjbApplication <em>Ejb Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Ejb Application</em>'.
     * @see com.tibco.xpd.xpdl2.EjbApplication
     * @generated
     */
    EClass getEjbApplication();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EjbApplication#getJndiName <em>Jndi Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Jndi Name</em>'.
     * @see com.tibco.xpd.xpdl2.EjbApplication#getJndiName()
     * @see #getEjbApplication()
     * @generated
     */
    EReference getEjbApplication_JndiName();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EjbApplication#getHomeClass <em>Home Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Home Class</em>'.
     * @see com.tibco.xpd.xpdl2.EjbApplication#getHomeClass()
     * @see #getEjbApplication()
     * @generated
     */
    EReference getEjbApplication_HomeClass();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EjbApplication#getMethod <em>Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Method</em>'.
     * @see com.tibco.xpd.xpdl2.EjbApplication#getMethod()
     * @see #getEjbApplication()
     * @generated
     */
    EReference getEjbApplication_Method();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.EndEvent <em>End Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>End Event</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent
     * @generated
     */
    EClass getEndEvent();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Message</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getTriggerResultMessage()
     * @see #getEndEvent()
     * @generated
     */
    EReference getEndEvent_TriggerResultMessage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EndEvent#getResultError <em>Result Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Result Error</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getResultError()
     * @see #getEndEvent()
     * @generated
     */
    EReference getEndEvent_ResultError();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultCompensation <em>Trigger Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getTriggerResultCompensation()
     * @see #getEndEvent()
     * @generated
     */
    EReference getEndEvent_TriggerResultCompensation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Signal</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getTriggerResultSignal()
     * @see #getEndEvent()
     * @generated
     */
    EReference getEndEvent_TriggerResultSignal();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EndEvent#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deprecated Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getDeprecatedResultCompensation()
     * @see #getEndEvent()
     * @generated
     */
    EReference getEndEvent_DeprecatedResultCompensation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EndEvent#getResultMultiple <em>Result Multiple</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Result Multiple</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getResultMultiple()
     * @see #getEndEvent()
     * @generated
     */
    EReference getEndEvent_ResultMultiple();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.EndEvent#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getImplementation()
     * @see #getEndEvent()
     * @generated
     */
    EAttribute getEndEvent_Implementation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.EndEvent#getResult <em>Result</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Result</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getResult()
     * @see #getEndEvent()
     * @generated
     */
    EAttribute getEndEvent_Result();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EndEvent#getDeprecatedTriggerResultLink <em>Deprecated Trigger Result Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deprecated Trigger Result Link</em>'.
     * @see com.tibco.xpd.xpdl2.EndEvent#getDeprecatedTriggerResultLink()
     * @see #getEndEvent()
     * @generated
     */
    EReference getEndEvent_DeprecatedTriggerResultLink();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.EndPoint <em>End Point</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>End Point</em>'.
     * @see com.tibco.xpd.xpdl2.EndPoint
     * @generated
     */
    EClass getEndPoint();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.EndPoint#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdl2.EndPoint#getExternalReference()
     * @see #getEndPoint()
     * @generated
     */
    EReference getEndPoint_ExternalReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.EndPoint#getEndPointType <em>End Point Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Point Type</em>'.
     * @see com.tibco.xpd.xpdl2.EndPoint#getEndPointType()
     * @see #getEndPoint()
     * @generated
     */
    EAttribute getEndPoint_EndPointType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.EnumerationType <em>Enumeration Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enumeration Type</em>'.
     * @see com.tibco.xpd.xpdl2.EnumerationType
     * @generated
     */
    EClass getEnumerationType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.EnumerationType#getEnumerationValue <em>Enumeration Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Enumeration Value</em>'.
     * @see com.tibco.xpd.xpdl2.EnumerationType#getEnumerationValue()
     * @see #getEnumerationType()
     * @generated
     */
    EReference getEnumerationType_EnumerationValue();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.EnumerationValue <em>Enumeration Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enumeration Value</em>'.
     * @see com.tibco.xpd.xpdl2.EnumerationValue
     * @generated
     */
    EClass getEnumerationValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.EnumerationValue#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.EnumerationValue#getName()
     * @see #getEnumerationValue()
     * @generated
     */
    EAttribute getEnumerationValue_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Event <em>Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Event</em>'.
     * @see com.tibco.xpd.xpdl2.Event
     * @generated
     */
    EClass getEvent();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ExceptionName <em>Exception Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Exception Name</em>'.
     * @see com.tibco.xpd.xpdl2.ExceptionName
     * @generated
     */
    EClass getExceptionName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ExceptionName#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.ExceptionName#getValue()
     * @see #getExceptionName()
     * @generated
     */
    EAttribute getExceptionName_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Expression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Expression</em>'.
     * @see com.tibco.xpd.xpdl2.Expression
     * @generated
     */
    EClass getExpression();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.Expression#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.xpdl2.Expression#getMixed()
     * @see #getExpression()
     * @generated
     */
    EAttribute getExpression_Mixed();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.Expression#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.xpd.xpdl2.Expression#getGroup()
     * @see #getExpression()
     * @generated
     */
    EAttribute getExpression_Group();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.Expression#getAny <em>Any</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any</em>'.
     * @see com.tibco.xpd.xpdl2.Expression#getAny()
     * @see #getExpression()
     * @generated
     */
    EAttribute getExpression_Any();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Expression#getScriptGrammar <em>Script Grammar</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script Grammar</em>'.
     * @see com.tibco.xpd.xpdl2.Expression#getScriptGrammar()
     * @see #getExpression()
     * @generated
     */
    EAttribute getExpression_ScriptGrammar();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ExtendedAttribute <em>Extended Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Extended Attribute</em>'.
     * @see com.tibco.xpd.xpdl2.ExtendedAttribute
     * @generated
     */
    EClass getExtendedAttribute();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.ExtendedAttribute#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.xpdl2.ExtendedAttribute#getMixed()
     * @see #getExtendedAttribute()
     * @generated
     */
    EAttribute getExtendedAttribute_Mixed();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.ExtendedAttribute#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.xpd.xpdl2.ExtendedAttribute#getGroup()
     * @see #getExtendedAttribute()
     * @generated
     */
    EAttribute getExtendedAttribute_Group();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.ExtendedAttribute#getAny <em>Any</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any</em>'.
     * @see com.tibco.xpd.xpdl2.ExtendedAttribute#getAny()
     * @see #getExtendedAttribute()
     * @generated
     */
    EAttribute getExtendedAttribute_Any();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ExtendedAttribute#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.ExtendedAttribute#getName()
     * @see #getExtendedAttribute()
     * @generated
     */
    EAttribute getExtendedAttribute_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ExtendedAttribute#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.ExtendedAttribute#getValue()
     * @see #getExtendedAttribute()
     * @generated
     */
    EAttribute getExtendedAttribute_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ExternalPackage <em>External Package</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>External Package</em>'.
     * @see com.tibco.xpd.xpdl2.ExternalPackage
     * @generated
     */
    EClass getExternalPackage();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ExternalPackage#getHref <em>Href</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Href</em>'.
     * @see com.tibco.xpd.xpdl2.ExternalPackage#getHref()
     * @see #getExternalPackage()
     * @generated
     */
    EAttribute getExternalPackage_Href();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdl2.ExternalReference
     * @generated
     */
    EClass getExternalReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ExternalReference#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Location</em>'.
     * @see com.tibco.xpd.xpdl2.ExternalReference#getLocation()
     * @see #getExternalReference()
     * @generated
     */
    EAttribute getExternalReference_Location();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ExternalReference#getNamespace <em>Namespace</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Namespace</em>'.
     * @see com.tibco.xpd.xpdl2.ExternalReference#getNamespace()
     * @see #getExternalReference()
     * @generated
     */
    EAttribute getExternalReference_Namespace();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ExternalReference#getXref <em>Xref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Xref</em>'.
     * @see com.tibco.xpd.xpdl2.ExternalReference#getXref()
     * @see #getExternalReference()
     * @generated
     */
    EAttribute getExternalReference_Xref();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.FormalParameter <em>Formal Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Formal Parameter</em>'.
     * @see com.tibco.xpd.xpdl2.FormalParameter
     * @generated
     */
    EClass getFormalParameter();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.FormalParameter#getMode <em>Mode</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mode</em>'.
     * @see com.tibco.xpd.xpdl2.FormalParameter#getMode()
     * @see #getFormalParameter()
     * @generated
     */
    EAttribute getFormalParameter_Mode();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.FormalParameter#isRequired <em>Required</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Required</em>'.
     * @see com.tibco.xpd.xpdl2.FormalParameter#isRequired()
     * @see #getFormalParameter()
     * @generated
     */
    EAttribute getFormalParameter_Required();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.FormLayout <em>Form Layout</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Form Layout</em>'.
     * @see com.tibco.xpd.xpdl2.FormLayout
     * @generated
     */
    EClass getFormLayout();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.FormLayout#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.xpdl2.FormLayout#getMixed()
     * @see #getFormLayout()
     * @generated
     */
    EAttribute getFormLayout_Mixed();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.FormApplication <em>Form Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Form Application</em>'.
     * @see com.tibco.xpd.xpdl2.FormApplication
     * @generated
     */
    EClass getFormApplication();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.FormApplication#getFormLayout <em>Form Layout</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Form Layout</em>'.
     * @see com.tibco.xpd.xpdl2.FormApplication#getFormLayout()
     * @see #getFormApplication()
     * @generated
     */
    EReference getFormApplication_FormLayout();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.HomeClass <em>Home Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Home Class</em>'.
     * @see com.tibco.xpd.xpdl2.HomeClass
     * @generated
     */
    EClass getHomeClass();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.HomeClass#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.HomeClass#getValue()
     * @see #getHomeClass()
     * @generated
     */
    EAttribute getHomeClass_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Icon <em>Icon</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Icon</em>'.
     * @see com.tibco.xpd.xpdl2.Icon
     * @generated
     */
    EClass getIcon();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Icon#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Icon#getValue()
     * @see #getIcon()
     * @generated
     */
    EAttribute getIcon_Value();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Icon#getHeight <em>Height</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Height</em>'.
     * @see com.tibco.xpd.xpdl2.Icon#getHeight()
     * @see #getIcon()
     * @generated
     */
    EAttribute getIcon_Height();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Icon#getShape <em>Shape</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Shape</em>'.
     * @see com.tibco.xpd.xpdl2.Icon#getShape()
     * @see #getIcon()
     * @generated
     */
    EAttribute getIcon_Shape();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Icon#getWidth <em>Width</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Width</em>'.
     * @see com.tibco.xpd.xpdl2.Icon#getWidth()
     * @see #getIcon()
     * @generated
     */
    EAttribute getIcon_Width();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Icon#getXCoord <em>XCoord</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>XCoord</em>'.
     * @see com.tibco.xpd.xpdl2.Icon#getXCoord()
     * @see #getIcon()
     * @generated
     */
    EAttribute getIcon_XCoord();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Icon#getYCoord <em>YCoord</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>YCoord</em>'.
     * @see com.tibco.xpd.xpdl2.Icon#getYCoord()
     * @see #getIcon()
     * @generated
     */
    EAttribute getIcon_YCoord();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Implementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.Implementation
     * @generated
     */
    EClass getImplementation();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.Implementation#getActivity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Activity</em>'.
     * @see com.tibco.xpd.xpdl2.Implementation#getActivity()
     * @see #getImplementation()
     * @generated
     */
    EReference getImplementation_Activity();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.InputSet <em>Input Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Input Set</em>'.
     * @see com.tibco.xpd.xpdl2.InputSet
     * @generated
     */
    EClass getInputSet();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.InputSet#getInput <em>Input</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Input</em>'.
     * @see com.tibco.xpd.xpdl2.InputSet#getInput()
     * @see #getInputSet()
     * @generated
     */
    EReference getInputSet_Input();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.InputSet#getArtifactInput <em>Artifact Input</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Artifact Input</em>'.
     * @see com.tibco.xpd.xpdl2.InputSet#getArtifactInput()
     * @see #getInputSet()
     * @generated
     */
    EReference getInputSet_ArtifactInput();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.InputSet#getPropertyInput <em>Property Input</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Property Input</em>'.
     * @see com.tibco.xpd.xpdl2.InputSet#getPropertyInput()
     * @see #getInputSet()
     * @generated
     */
    EReference getInputSet_PropertyInput();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Input <em>Input</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Input</em>'.
     * @see com.tibco.xpd.xpdl2.Input
     * @generated
     */
    EClass getInput();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Input#getArtifactId <em>Artifact Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Artifact Id</em>'.
     * @see com.tibco.xpd.xpdl2.Input#getArtifactId()
     * @see #getInput()
     * @generated
     */
    EAttribute getInput_ArtifactId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.IntermediateEvent <em>Intermediate Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Intermediate Event</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent
     * @generated
     */
    EClass getIntermediateEvent();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Message</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultMessage()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_TriggerResultMessage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerTimer <em>Trigger Timer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Timer</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerTimer()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_TriggerTimer();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getResultError <em>Result Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Result Error</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getResultError()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_ResultError();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultCompensation <em>Trigger Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultCompensation()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_TriggerResultCompensation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerConditional <em>Trigger Conditional</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Conditional</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerConditional()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_TriggerConditional();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultLink <em>Trigger Result Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Link</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultLink()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_TriggerResultLink();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerIntermediateMultiple <em>Trigger Intermediate Multiple</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Intermediate Multiple</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerIntermediateMultiple()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_TriggerIntermediateMultiple();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getImplementation()
     * @see #getIntermediateEvent()
     * @generated
     */
    EAttribute getIntermediateEvent_Implementation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTarget()
     * @see #getIntermediateEvent()
     * @generated
     */
    EAttribute getIntermediateEvent_Target();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTrigger <em>Trigger</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Trigger</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTrigger()
     * @see #getIntermediateEvent()
     * @generated
     */
    EAttribute getIntermediateEvent_Trigger();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getAnyAttribute <em>Any Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any Attribute</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getAnyAttribute()
     * @see #getIntermediateEvent()
     * @generated
     */
    EAttribute getIntermediateEvent_AnyAttribute();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultCancel <em>Trigger Result Cancel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Cancel</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultCancel()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_TriggerResultCancel();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Signal</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultSignal()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_TriggerResultSignal();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deprecated Trigger Rule</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getDeprecatedTriggerRule()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_DeprecatedTriggerRule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deprecated Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.IntermediateEvent#getDeprecatedResultCompensation()
     * @see #getIntermediateEvent()
     * @generated
     */
    EReference getIntermediateEvent_DeprecatedResultCompensation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.IORules <em>IO Rules</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>IO Rules</em>'.
     * @see com.tibco.xpd.xpdl2.IORules
     * @generated
     */
    EClass getIORules();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.IORules#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Expression</em>'.
     * @see com.tibco.xpd.xpdl2.IORules#getExpression()
     * @see #getIORules()
     * @generated
     */
    EReference getIORules_Expression();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.JndiName <em>Jndi Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Jndi Name</em>'.
     * @see com.tibco.xpd.xpdl2.JndiName
     * @generated
     */
    EClass getJndiName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.JndiName#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.JndiName#getValue()
     * @see #getJndiName()
     * @generated
     */
    EAttribute getJndiName_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Join <em>Join</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Join</em>'.
     * @see com.tibco.xpd.xpdl2.Join
     * @generated
     */
    EClass getJoin();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Join#getIncomingCondtion <em>Incoming Condtion</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Incoming Condtion</em>'.
     * @see com.tibco.xpd.xpdl2.Join#getIncomingCondtion()
     * @see #getJoin()
     * @generated
     */
    EAttribute getJoin_IncomingCondtion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Join#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.xpdl2.Join#getType()
     * @see #getJoin()
     * @generated
     */
    EAttribute getJoin_Type();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Join#getExclusiveType <em>Exclusive Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Exclusive Type</em>'.
     * @see com.tibco.xpd.xpdl2.Join#getExclusiveType()
     * @see #getJoin()
     * @generated
     */
    EAttribute getJoin_ExclusiveType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.LayoutInfo <em>Layout Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Layout Info</em>'.
     * @see com.tibco.xpd.xpdl2.LayoutInfo
     * @generated
     */
    EClass getLayoutInfo();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LayoutInfo#getPixelsPerMillimeter <em>Pixels Per Millimeter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Pixels Per Millimeter</em>'.
     * @see com.tibco.xpd.xpdl2.LayoutInfo#getPixelsPerMillimeter()
     * @see #getLayoutInfo()
     * @generated
     */
    EAttribute getLayoutInfo_PixelsPerMillimeter();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Lane <em>Lane</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Lane</em>'.
     * @see com.tibco.xpd.xpdl2.Lane
     * @generated
     */
    EClass getLane();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Lane#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Lane#getObject()
     * @see #getLane()
     * @generated
     */
    EReference getLane_Object();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Lane#getDeprecatedParentLane <em>Deprecated Parent Lane</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Parent Lane</em>'.
     * @see com.tibco.xpd.xpdl2.Lane#getDeprecatedParentLane()
     * @see #getLane()
     * @generated
     */
    EAttribute getLane_DeprecatedParentLane();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Lane#getDeprecatedParentPoolId <em>Deprecated Parent Pool Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Parent Pool Id</em>'.
     * @see com.tibco.xpd.xpdl2.Lane#getDeprecatedParentPoolId()
     * @see #getLane()
     * @generated
     */
    EAttribute getLane_DeprecatedParentPoolId();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.Lane#getParentPool <em>Parent Pool</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Parent Pool</em>'.
     * @see com.tibco.xpd.xpdl2.Lane#getParentPool()
     * @see #getLane()
     * @generated
     */
    EReference getLane_ParentPool();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Lane#getPerformers <em>Performers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Performers</em>'.
     * @see com.tibco.xpd.xpdl2.Lane#getPerformers()
     * @see #getLane()
     * @generated
     */
    EReference getLane_Performers();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Lane#getNestedLane <em>Nested Lane</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Nested Lane</em>'.
     * @see com.tibco.xpd.xpdl2.Lane#getNestedLane()
     * @see #getLane()
     * @generated
     */
    EReference getLane_NestedLane();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Length <em>Length</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Length</em>'.
     * @see com.tibco.xpd.xpdl2.Length
     * @generated
     */
    EClass getLength();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Length#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Length#getValue()
     * @see #getLength()
     * @generated
     */
    EAttribute getLength_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Limit <em>Limit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Limit</em>'.
     * @see com.tibco.xpd.xpdl2.Limit
     * @generated
     */
    EClass getLimit();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Limit#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Limit#getValue()
     * @see #getLimit()
     * @generated
     */
    EAttribute getLimit_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ListType <em>List Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>List Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType
     * @generated
     */
    EClass getListType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getBasicType <em>Basic Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Basic Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getBasicType()
     * @see #getListType()
     * @generated
     */
    EReference getListType_BasicType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getDeclaredType <em>Declared Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Declared Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getDeclaredType()
     * @see #getListType()
     * @generated
     */
    EReference getListType_DeclaredType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getSchemaType <em>Schema Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schema Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getSchemaType()
     * @see #getListType()
     * @generated
     */
    EReference getListType_SchemaType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getExternalReference()
     * @see #getListType()
     * @generated
     */
    EReference getListType_ExternalReference();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getRecordType <em>Record Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Record Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getRecordType()
     * @see #getListType()
     * @generated
     */
    EReference getListType_RecordType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getUnionType <em>Union Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Union Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getUnionType()
     * @see #getListType()
     * @generated
     */
    EReference getListType_UnionType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getEnumerationType <em>Enumeration Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Enumeration Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getEnumerationType()
     * @see #getListType()
     * @generated
     */
    EReference getListType_EnumerationType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getArrayType <em>Array Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Array Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getArrayType()
     * @see #getListType()
     * @generated
     */
    EReference getListType_ArrayType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ListType#getListType <em>List Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>List Type</em>'.
     * @see com.tibco.xpd.xpdl2.ListType#getListType()
     * @see #getListType()
     * @generated
     */
    EReference getListType_ListType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Location <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Location</em>'.
     * @see com.tibco.xpd.xpdl2.Location
     * @generated
     */
    EClass getLocation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Location#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Location#getValue()
     * @see #getLocation()
     * @generated
     */
    EAttribute getLocation_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.LoopMultiInstance <em>Loop Multi Instance</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Loop Multi Instance</em>'.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance
     * @generated
     */
    EClass getLoopMultiInstance();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getComplexMIFlowCondition <em>Complex MI Flow Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Complex MI Flow Condition</em>'.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance#getComplexMIFlowCondition()
     * @see #getLoopMultiInstance()
     * @generated
     */
    EReference getLoopMultiInstance_ComplexMIFlowCondition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getAttributeComplexMI_FlowCondition <em>Attribute Complex MI Flow Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Complex MI Flow Condition</em>'.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance#getAttributeComplexMI_FlowCondition()
     * @see #getLoopMultiInstance()
     * @generated
     */
    EAttribute getLoopMultiInstance_AttributeComplexMI_FlowCondition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getAttributeMI_Condition <em>Attribute MI Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute MI Condition</em>'.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance#getAttributeMI_Condition()
     * @see #getLoopMultiInstance()
     * @generated
     */
    EAttribute getLoopMultiInstance_AttributeMI_Condition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getLoopCounter <em>Loop Counter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Loop Counter</em>'.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance#getLoopCounter()
     * @see #getLoopMultiInstance()
     * @generated
     */
    EAttribute getLoopMultiInstance_LoopCounter();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMICondition <em>MI Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>MI Condition</em>'.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance#getMICondition()
     * @see #getLoopMultiInstance()
     * @generated
     */
    EReference getLoopMultiInstance_MICondition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIFlowCondition <em>MI Flow Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>MI Flow Condition</em>'.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance#getMIFlowCondition()
     * @see #getLoopMultiInstance()
     * @generated
     */
    EAttribute getLoopMultiInstance_MIFlowCondition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopMultiInstance#getMIOrdering <em>MI Ordering</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>MI Ordering</em>'.
     * @see com.tibco.xpd.xpdl2.LoopMultiInstance#getMIOrdering()
     * @see #getLoopMultiInstance()
     * @generated
     */
    EAttribute getLoopMultiInstance_MIOrdering();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.LoopStandard <em>Loop Standard</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Loop Standard</em>'.
     * @see com.tibco.xpd.xpdl2.LoopStandard
     * @generated
     */
    EClass getLoopStandard();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopCondition <em>Loop Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Loop Condition</em>'.
     * @see com.tibco.xpd.xpdl2.LoopStandard#getLoopCondition()
     * @see #getLoopStandard()
     * @generated
     */
    EReference getLoopStandard_LoopCondition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopCounter <em>Loop Counter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Loop Counter</em>'.
     * @see com.tibco.xpd.xpdl2.LoopStandard#getLoopCounter()
     * @see #getLoopStandard()
     * @generated
     */
    EAttribute getLoopStandard_LoopCounter();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopStandard#getLoopMaximum <em>Loop Maximum</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Loop Maximum</em>'.
     * @see com.tibco.xpd.xpdl2.LoopStandard#getLoopMaximum()
     * @see #getLoopStandard()
     * @generated
     */
    EAttribute getLoopStandard_LoopMaximum();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopStandard#getTestTime <em>Test Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Test Time</em>'.
     * @see com.tibco.xpd.xpdl2.LoopStandard#getTestTime()
     * @see #getLoopStandard()
     * @generated
     */
    EAttribute getLoopStandard_TestTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.LoopStandard#getAttributeLoopCondition <em>Attribute Loop Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Loop Condition</em>'.
     * @see com.tibco.xpd.xpdl2.LoopStandard#getAttributeLoopCondition()
     * @see #getLoopStandard()
     * @generated
     */
    EAttribute getLoopStandard_AttributeLoopCondition();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Loop <em>Loop</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Loop</em>'.
     * @see com.tibco.xpd.xpdl2.Loop
     * @generated
     */
    EClass getLoop();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Loop#getLoopStandard <em>Loop Standard</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Loop Standard</em>'.
     * @see com.tibco.xpd.xpdl2.Loop#getLoopStandard()
     * @see #getLoop()
     * @generated
     */
    EReference getLoop_LoopStandard();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Loop#getLoopMultiInstance <em>Loop Multi Instance</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Loop Multi Instance</em>'.
     * @see com.tibco.xpd.xpdl2.Loop#getLoopMultiInstance()
     * @see #getLoop()
     * @generated
     */
    EReference getLoop_LoopMultiInstance();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Loop#getLoopType <em>Loop Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Loop Type</em>'.
     * @see com.tibco.xpd.xpdl2.Loop#getLoopType()
     * @see #getLoop()
     * @generated
     */
    EAttribute getLoop_LoopType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.Loop#getAnyAttribute <em>Any Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any Attribute</em>'.
     * @see com.tibco.xpd.xpdl2.Loop#getAnyAttribute()
     * @see #getLoop()
     * @generated
     */
    EAttribute getLoop_AnyAttribute();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Member <em>Member</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Member</em>'.
     * @see com.tibco.xpd.xpdl2.Member
     * @generated
     */
    EClass getMember();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getBasicType <em>Basic Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Basic Type</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getBasicType()
     * @see #getMember()
     * @generated
     */
    EReference getMember_BasicType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getDeclaredType <em>Declared Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Declared Type</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getDeclaredType()
     * @see #getMember()
     * @generated
     */
    EReference getMember_DeclaredType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getSchemaType <em>Schema Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schema Type</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getSchemaType()
     * @see #getMember()
     * @generated
     */
    EReference getMember_SchemaType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getExternalReference()
     * @see #getMember()
     * @generated
     */
    EReference getMember_ExternalReference();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getRecordType <em>Record Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Record Type</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getRecordType()
     * @see #getMember()
     * @generated
     */
    EReference getMember_RecordType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getUnionType <em>Union Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Union Type</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getUnionType()
     * @see #getMember()
     * @generated
     */
    EReference getMember_UnionType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getEnumerationType <em>Enumeration Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Enumeration Type</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getEnumerationType()
     * @see #getMember()
     * @generated
     */
    EReference getMember_EnumerationType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getArrayType <em>Array Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Array Type</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getArrayType()
     * @see #getMember()
     * @generated
     */
    EReference getMember_ArrayType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Member#getListType <em>List Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>List Type</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getListType()
     * @see #getMember()
     * @generated
     */
    EReference getMember_ListType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.Member#getAnyAttribute <em>Any Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any Attribute</em>'.
     * @see com.tibco.xpd.xpdl2.Member#getAnyAttribute()
     * @see #getMember()
     * @generated
     */
    EAttribute getMember_AnyAttribute();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.MessageFlow <em>Message Flow</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Message Flow</em>'.
     * @see com.tibco.xpd.xpdl2.MessageFlow
     * @generated
     */
    EClass getMessageFlow();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.MessageFlow#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message</em>'.
     * @see com.tibco.xpd.xpdl2.MessageFlow#getMessage()
     * @see #getMessageFlow()
     * @generated
     */
    EReference getMessageFlow_Message();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.MessageFlow#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.MessageFlow#getObject()
     * @see #getMessageFlow()
     * @generated
     */
    EReference getMessageFlow_Object();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.MessageFlow#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Source</em>'.
     * @see com.tibco.xpd.xpdl2.MessageFlow#getSource()
     * @see #getMessageFlow()
     * @generated
     */
    EAttribute getMessageFlow_Source();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.MessageFlow#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target</em>'.
     * @see com.tibco.xpd.xpdl2.MessageFlow#getTarget()
     * @see #getMessageFlow()
     * @generated
     */
    EAttribute getMessageFlow_Target();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.MessageFlow#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Package</em>'.
     * @see com.tibco.xpd.xpdl2.MessageFlow#getPackage()
     * @see #getMessageFlow()
     * @generated
     */
    EReference getMessageFlow_Package();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Message <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Message</em>'.
     * @see com.tibco.xpd.xpdl2.Message
     * @generated
     */
    EClass getMessage();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Message#getActualParameters <em>Actual Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Actual Parameters</em>'.
     * @see com.tibco.xpd.xpdl2.Message#getActualParameters()
     * @see #getMessage()
     * @generated
     */
    EReference getMessage_ActualParameters();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Message#getDataMappings <em>Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Mappings</em>'.
     * @see com.tibco.xpd.xpdl2.Message#getDataMappings()
     * @see #getMessage()
     * @generated
     */
    EReference getMessage_DataMappings();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Message#getFaultName <em>Fault Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Fault Name</em>'.
     * @see com.tibco.xpd.xpdl2.Message#getFaultName()
     * @see #getMessage()
     * @generated
     */
    EAttribute getMessage_FaultName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Message#getFrom <em>From</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>From</em>'.
     * @see com.tibco.xpd.xpdl2.Message#getFrom()
     * @see #getMessage()
     * @generated
     */
    EAttribute getMessage_From();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Message#getTo <em>To</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>To</em>'.
     * @see com.tibco.xpd.xpdl2.Message#getTo()
     * @see #getMessage()
     * @generated
     */
    EAttribute getMessage_To();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Method <em>Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Method</em>'.
     * @see com.tibco.xpd.xpdl2.Method
     * @generated
     */
    EClass getMethod();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Method#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Method#getValue()
     * @see #getMethod()
     * @generated
     */
    EAttribute getMethod_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ModificationDate <em>Modification Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Modification Date</em>'.
     * @see com.tibco.xpd.xpdl2.ModificationDate
     * @generated
     */
    EClass getModificationDate();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.MyRole <em>My Role</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>My Role</em>'.
     * @see com.tibco.xpd.xpdl2.MyRole
     * @generated
     */
    EClass getMyRole();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.MyRole#getRoleName <em>Role Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Role Name</em>'.
     * @see com.tibco.xpd.xpdl2.MyRole#getRoleName()
     * @see #getMyRole()
     * @generated
     */
    EAttribute getMyRole_RoleName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo <em>Node Graphics Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Node Graphics Info</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo
     * @generated
     */
    EClass getNodeGraphicsInfo();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getCoordinates <em>Coordinates</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Coordinates</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getCoordinates()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EReference getNodeGraphicsInfo_Coordinates();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getBorderColor <em>Border Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Border Color</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getBorderColor()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_BorderColor();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getFillColor <em>Fill Color</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Fill Color</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getFillColor()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_FillColor();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getHeight <em>Height</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Height</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getHeight()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_Height();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#isIsVisible <em>Is Visible</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Visible</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#isIsVisible()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_IsVisible();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getLaneId <em>Lane Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lane Id</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getLaneId()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_LaneId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getPage <em>Page</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Page</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getPage()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_Page();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getShape <em>Shape</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Shape</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getShape()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_Shape();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getToolId <em>Tool Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Tool Id</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getToolId()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_ToolId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getWidth <em>Width</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Width</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getWidth()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_Width();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getPageId <em>Page Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Page Id</em>'.
     * @see com.tibco.xpd.xpdl2.NodeGraphicsInfo#getPageId()
     * @see #getNodeGraphicsInfo()
     * @generated
     */
    EAttribute getNodeGraphicsInfo_PageId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.No <em>No</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>No</em>'.
     * @see com.tibco.xpd.xpdl2.No
     * @generated
     */
    EClass getNo();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Object <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Object
     * @generated
     */
    EClass getObject();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Object#getCategories <em>Categories</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Categories</em>'.
     * @see com.tibco.xpd.xpdl2.Object#getCategories()
     * @see #getObject()
     * @generated
     */
    EReference getObject_Categories();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Object#getDocumentation <em>Documentation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Documentation</em>'.
     * @see com.tibco.xpd.xpdl2.Object#getDocumentation()
     * @see #getObject()
     * @generated
     */
    EReference getObject_Documentation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.OutputSet <em>Output Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Output Set</em>'.
     * @see com.tibco.xpd.xpdl2.OutputSet
     * @generated
     */
    EClass getOutputSet();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.OutputSet#getOutput <em>Output</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Output</em>'.
     * @see com.tibco.xpd.xpdl2.OutputSet#getOutput()
     * @see #getOutputSet()
     * @generated
     */
    EReference getOutputSet_Output();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Output <em>Output</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Output</em>'.
     * @see com.tibco.xpd.xpdl2.Output
     * @generated
     */
    EClass getOutput();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Output#getArtifactId <em>Artifact Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Artifact Id</em>'.
     * @see com.tibco.xpd.xpdl2.Output#getArtifactId()
     * @see #getOutput()
     * @generated
     */
    EAttribute getOutput_ArtifactId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.PackageHeader <em>Package Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Package Header</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader
     * @generated
     */
    EClass getPackageHeader();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PackageHeader#getXpdlVersion <em>Xpdl Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Xpdl Version</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getXpdlVersion()
     * @see #getPackageHeader()
     * @generated
     */
    EAttribute getPackageHeader_XpdlVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PackageHeader#getVendor <em>Vendor</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Vendor</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getVendor()
     * @see #getPackageHeader()
     * @generated
     */
    EAttribute getPackageHeader_Vendor();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PackageHeader#getCreated <em>Created</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Created</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getCreated()
     * @see #getPackageHeader()
     * @generated
     */
    EAttribute getPackageHeader_Created();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PackageHeader#getDocumentation <em>Documentation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Documentation</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getDocumentation()
     * @see #getPackageHeader()
     * @generated
     */
    EReference getPackageHeader_Documentation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PackageHeader#getPriorityUnit <em>Priority Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Priority Unit</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getPriorityUnit()
     * @see #getPackageHeader()
     * @generated
     */
    EReference getPackageHeader_PriorityUnit();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PackageHeader#getCostUnit <em>Cost Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Cost Unit</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getCostUnit()
     * @see #getPackageHeader()
     * @generated
     */
    EReference getPackageHeader_CostUnit();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PackageHeader#getVendorExtensions <em>Vendor Extensions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Vendor Extensions</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getVendorExtensions()
     * @see #getPackageHeader()
     * @generated
     */
    EReference getPackageHeader_VendorExtensions();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PackageHeader#getLayoutInfo <em>Layout Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Layout Info</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getLayoutInfo()
     * @see #getPackageHeader()
     * @generated
     */
    EReference getPackageHeader_LayoutInfo();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PackageHeader#getModificationDate <em>Modification Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Modification Date</em>'.
     * @see com.tibco.xpd.xpdl2.PackageHeader#getModificationDate()
     * @see #getPackageHeader()
     * @generated
     */
    EReference getPackageHeader_ModificationDate();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Package <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Package</em>'.
     * @see com.tibco.xpd.xpdl2.Package
     * @generated
     */
    EClass getPackage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Package#getPackageHeader <em>Package Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Package Header</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getPackageHeader()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_PackageHeader();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Package#getRedefinableHeader <em>Redefinable Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Redefinable Header</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getRedefinableHeader()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_RedefinableHeader();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Package#getConformanceClass <em>Conformance Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Conformance Class</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getConformanceClass()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_ConformanceClass();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Package#getScript <em>Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Script</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getScript()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_Script();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Package#getExternalPackages <em>External Packages</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>External Packages</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getExternalPackages()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_ExternalPackages();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Package#getTypeDeclarations <em>Type Declarations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Type Declarations</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getTypeDeclarations()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_TypeDeclarations();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Package#getPartnerLinkTypes <em>Partner Link Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Partner Link Types</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getPartnerLinkTypes()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_PartnerLinkTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Package#getPools <em>Pools</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Pools</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getPools()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_Pools();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Package#getMessageFlows <em>Message Flows</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Message Flows</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getMessageFlows()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_MessageFlows();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Package#getAssociations <em>Associations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Associations</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getAssociations()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_Associations();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Package#getArtifacts <em>Artifacts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Artifacts</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getArtifacts()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_Artifacts();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Package#getProcesses <em>Processes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Processes</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getProcesses()
     * @see #getPackage()
     * @generated
     */
    EReference getPackage_Processes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Package#getLanguage <em>Language</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Language</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getLanguage()
     * @see #getPackage()
     * @generated
     */
    EAttribute getPackage_Language();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Package#getQueryLanguage <em>Query Language</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Query Language</em>'.
     * @see com.tibco.xpd.xpdl2.Package#getQueryLanguage()
     * @see #getPackage()
     * @generated
     */
    EAttribute getPackage_QueryLanguage();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Page <em>Page</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Page</em>'.
     * @see com.tibco.xpd.xpdl2.Page
     * @generated
     */
    EClass getPage();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Page#getHeight <em>Height</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Height</em>'.
     * @see com.tibco.xpd.xpdl2.Page#getHeight()
     * @see #getPage()
     * @generated
     */
    EAttribute getPage_Height();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Page#getWidth <em>Width</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Width</em>'.
     * @see com.tibco.xpd.xpdl2.Page#getWidth()
     * @see #getPage()
     * @generated
     */
    EAttribute getPage_Width();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Pages <em>Pages</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Pages</em>'.
     * @see com.tibco.xpd.xpdl2.Pages
     * @generated
     */
    EClass getPages();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Pages#getPage <em>Page</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Page</em>'.
     * @see com.tibco.xpd.xpdl2.Pages#getPage()
     * @see #getPages()
     * @generated
     */
    EReference getPages_Page();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ApplicationsContainer <em>Applications Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Applications Container</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationsContainer
     * @generated
     */
    EClass getApplicationsContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.ApplicationsContainer#getApplications <em>Applications</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Applications</em>'.
     * @see com.tibco.xpd.xpdl2.ApplicationsContainer#getApplications()
     * @see #getApplicationsContainer()
     * @generated
     */
    EReference getApplicationsContainer_Applications();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ParticipantsContainer <em>Participants Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Participants Container</em>'.
     * @see com.tibco.xpd.xpdl2.ParticipantsContainer
     * @generated
     */
    EClass getParticipantsContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.ParticipantsContainer#getParticipants <em>Participants</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Participants</em>'.
     * @see com.tibco.xpd.xpdl2.ParticipantsContainer#getParticipants()
     * @see #getParticipantsContainer()
     * @generated
     */
    EReference getParticipantsContainer_Participants();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DataFieldsContainer <em>Data Fields Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Fields Container</em>'.
     * @see com.tibco.xpd.xpdl2.DataFieldsContainer
     * @generated
     */
    EClass getDataFieldsContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.DataFieldsContainer#getDataFields <em>Data Fields</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Fields</em>'.
     * @see com.tibco.xpd.xpdl2.DataFieldsContainer#getDataFields()
     * @see #getDataFieldsContainer()
     * @generated
     */
    EReference getDataFieldsContainer_DataFields();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Participant <em>Participant</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Participant</em>'.
     * @see com.tibco.xpd.xpdl2.Participant
     * @generated
     */
    EClass getParticipant();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Participant#getParticipantType <em>Participant Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Participant Type</em>'.
     * @see com.tibco.xpd.xpdl2.Participant#getParticipantType()
     * @see #getParticipant()
     * @generated
     */
    EReference getParticipant_ParticipantType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Participant#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdl2.Participant#getExternalReference()
     * @see #getParticipant()
     * @generated
     */
    EReference getParticipant_ExternalReference();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ParticipantTypeElem <em>Participant Type Elem</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Participant Type Elem</em>'.
     * @see com.tibco.xpd.xpdl2.ParticipantTypeElem
     * @generated
     */
    EClass getParticipantTypeElem();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ParticipantTypeElem#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.xpdl2.ParticipantTypeElem#getType()
     * @see #getParticipantTypeElem()
     * @generated
     */
    EAttribute getParticipantTypeElem_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.PartnerLink <em>Partner Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Partner Link</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerLink
     * @generated
     */
    EClass getPartnerLink();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PartnerLink#getMyRole <em>My Role</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>My Role</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerLink#getMyRole()
     * @see #getPartnerLink()
     * @generated
     */
    EReference getPartnerLink_MyRole();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PartnerLink#getPartnerRole <em>Partner Role</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Partner Role</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerLink#getPartnerRole()
     * @see #getPartnerLink()
     * @generated
     */
    EReference getPartnerLink_PartnerRole();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PartnerLink#getPartnerLinkTypeId <em>Partner Link Type Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Partner Link Type Id</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerLink#getPartnerLinkTypeId()
     * @see #getPartnerLink()
     * @generated
     */
    EAttribute getPartnerLink_PartnerLinkTypeId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PartnerLink#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerLink#getName()
     * @see #getPartnerLink()
     * @generated
     */
    EAttribute getPartnerLink_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.PartnerLinkType <em>Partner Link Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Partner Link Type</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerLinkType
     * @generated
     */
    EClass getPartnerLinkType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.PartnerLinkType#getRole <em>Role</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Role</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerLinkType#getRole()
     * @see #getPartnerLinkType()
     * @generated
     */
    EReference getPartnerLinkType_Role();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PartnerLinkType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerLinkType#getName()
     * @see #getPartnerLinkType()
     * @generated
     */
    EAttribute getPartnerLinkType_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.PartnerRole <em>Partner Role</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Partner Role</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerRole
     * @generated
     */
    EClass getPartnerRole();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PartnerRole#getEndPoint <em>End Point</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>End Point</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerRole#getEndPoint()
     * @see #getPartnerRole()
     * @generated
     */
    EReference getPartnerRole_EndPoint();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PartnerRole#getPortName <em>Port Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Port Name</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerRole#getPortName()
     * @see #getPartnerRole()
     * @generated
     */
    EAttribute getPartnerRole_PortName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PartnerRole#getRoleName <em>Role Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Role Name</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerRole#getRoleName()
     * @see #getPartnerRole()
     * @generated
     */
    EAttribute getPartnerRole_RoleName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PartnerRole#getServiceName <em>Service Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Service Name</em>'.
     * @see com.tibco.xpd.xpdl2.PartnerRole#getServiceName()
     * @see #getPartnerRole()
     * @generated
     */
    EAttribute getPartnerRole_ServiceName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Partner <em>Partner</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Partner</em>'.
     * @see com.tibco.xpd.xpdl2.Partner
     * @generated
     */
    EClass getPartner();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Partner#getPartnerLinkId <em>Partner Link Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Partner Link Id</em>'.
     * @see com.tibco.xpd.xpdl2.Partner#getPartnerLinkId()
     * @see #getPartner()
     * @generated
     */
    EAttribute getPartner_PartnerLinkId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Partner#getRoleType <em>Role Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Role Type</em>'.
     * @see com.tibco.xpd.xpdl2.Partner#getRoleType()
     * @see #getPartner()
     * @generated
     */
    EAttribute getPartner_RoleType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Performer <em>Performer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Performer</em>'.
     * @see com.tibco.xpd.xpdl2.Performer
     * @generated
     */
    EClass getPerformer();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Performer#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Performer#getValue()
     * @see #getPerformer()
     * @generated
     */
    EAttribute getPerformer_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.PojoApplication <em>Pojo Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Pojo Application</em>'.
     * @see com.tibco.xpd.xpdl2.PojoApplication
     * @generated
     */
    EClass getPojoApplication();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PojoApplication#getClass_ <em>Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Class</em>'.
     * @see com.tibco.xpd.xpdl2.PojoApplication#getClass_()
     * @see #getPojoApplication()
     * @generated
     */
    EReference getPojoApplication_Class();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.PojoApplication#getMethod <em>Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Method</em>'.
     * @see com.tibco.xpd.xpdl2.PojoApplication#getMethod()
     * @see #getPojoApplication()
     * @generated
     */
    EReference getPojoApplication_Method();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Pool <em>Pool</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Pool</em>'.
     * @see com.tibco.xpd.xpdl2.Pool
     * @generated
     */
    EClass getPool();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Pool#getLanes <em>Lanes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Lanes</em>'.
     * @see com.tibco.xpd.xpdl2.Pool#getLanes()
     * @see #getPool()
     * @generated
     */
    EReference getPool_Lanes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Pool#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Pool#getObject()
     * @see #getPool()
     * @generated
     */
    EReference getPool_Object();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Pool#isBoundaryVisible <em>Boundary Visible</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Boundary Visible</em>'.
     * @see com.tibco.xpd.xpdl2.Pool#isBoundaryVisible()
     * @see #getPool()
     * @generated
     */
    EAttribute getPool_BoundaryVisible();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Pool#getOrientation <em>Orientation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Orientation</em>'.
     * @see com.tibco.xpd.xpdl2.Pool#getOrientation()
     * @see #getPool()
     * @generated
     */
    EAttribute getPool_Orientation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Pool#getParticipantId <em>Participant Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Participant Id</em>'.
     * @see com.tibco.xpd.xpdl2.Pool#getParticipantId()
     * @see #getPool()
     * @generated
     */
    EAttribute getPool_ParticipantId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Pool#getProcessId <em>Process Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Id</em>'.
     * @see com.tibco.xpd.xpdl2.Pool#getProcessId()
     * @see #getPool()
     * @generated
     */
    EAttribute getPool_ProcessId();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.Pool#getParentPackage <em>Parent Package</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Parent Package</em>'.
     * @see com.tibco.xpd.xpdl2.Pool#getParentPackage()
     * @see #getPool()
     * @generated
     */
    EReference getPool_ParentPackage();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Pool#isMainPool <em>Main Pool</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Main Pool</em>'.
     * @see com.tibco.xpd.xpdl2.Pool#isMainPool()
     * @see #getPool()
     * @generated
     */
    EAttribute getPool_MainPool();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Precision <em>Precision</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Precision</em>'.
     * @see com.tibco.xpd.xpdl2.Precision
     * @generated
     */
    EClass getPrecision();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Precision#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Precision#getValue()
     * @see #getPrecision()
     * @generated
     */
    EAttribute getPrecision_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Priority <em>Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Priority</em>'.
     * @see com.tibco.xpd.xpdl2.Priority
     * @generated
     */
    EClass getPriority();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Priority#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Priority#getValue()
     * @see #getPriority()
     * @generated
     */
    EAttribute getPriority_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.PriorityUnit <em>Priority Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Priority Unit</em>'.
     * @see com.tibco.xpd.xpdl2.PriorityUnit
     * @generated
     */
    EClass getPriorityUnit();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PriorityUnit#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.PriorityUnit#getValue()
     * @see #getPriorityUnit()
     * @generated
     */
    EAttribute getPriorityUnit_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ProcessHeader <em>Process Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Process Header</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessHeader
     * @generated
     */
    EClass getProcessHeader();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ProcessHeader#getCreated <em>Created</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Created</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessHeader#getCreated()
     * @see #getProcessHeader()
     * @generated
     */
    EAttribute getProcessHeader_Created();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ProcessHeader#getPriority <em>Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Priority</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessHeader#getPriority()
     * @see #getProcessHeader()
     * @generated
     */
    EReference getProcessHeader_Priority();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ProcessHeader#getLimit <em>Limit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Limit</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessHeader#getLimit()
     * @see #getProcessHeader()
     * @generated
     */
    EReference getProcessHeader_Limit();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ProcessHeader#getValidFrom <em>Valid From</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Valid From</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessHeader#getValidFrom()
     * @see #getProcessHeader()
     * @generated
     */
    EReference getProcessHeader_ValidFrom();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ProcessHeader#getValidTo <em>Valid To</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Valid To</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessHeader#getValidTo()
     * @see #getProcessHeader()
     * @generated
     */
    EReference getProcessHeader_ValidTo();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ProcessHeader#getTimeEstimation <em>Time Estimation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Time Estimation</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessHeader#getTimeEstimation()
     * @see #getProcessHeader()
     * @generated
     */
    EReference getProcessHeader_TimeEstimation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ProcessHeader#getDurationUnit <em>Duration Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Duration Unit</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessHeader#getDurationUnit()
     * @see #getProcessHeader()
     * @generated
     */
    EAttribute getProcessHeader_DurationUnit();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Process <em>Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Process</em>'.
     * @see com.tibco.xpd.xpdl2.Process
     * @generated
     */
    EClass getProcess();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Process#getProcessHeader <em>Process Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Process Header</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getProcessHeader()
     * @see #getProcess()
     * @generated
     */
    EReference getProcess_ProcessHeader();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Process#getRedefinableHeader <em>Redefinable Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Redefinable Header</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getRedefinableHeader()
     * @see #getProcess()
     * @generated
     */
    EReference getProcess_RedefinableHeader();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Process#getPartnerLinks <em>Partner Links</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Partner Links</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getPartnerLinks()
     * @see #getProcess()
     * @generated
     */
    EReference getProcess_PartnerLinks();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Process#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getObject()
     * @see #getProcess()
     * @generated
     */
    EReference getProcess_Object();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Process#getExtensions <em>Extensions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Extensions</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getExtensions()
     * @see #getProcess()
     * @generated
     */
    EReference getProcess_Extensions();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Process#getAccessLevel <em>Access Level</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Access Level</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getAccessLevel()
     * @see #getProcess()
     * @generated
     */
    EAttribute getProcess_AccessLevel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Process#getDefaultStartActivitySetId <em>Default Start Activity Set Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Start Activity Set Id</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getDefaultStartActivitySetId()
     * @see #getProcess()
     * @generated
     */
    EAttribute getProcess_DefaultStartActivitySetId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Process#isEnableInstanceCompensation <em>Enable Instance Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enable Instance Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.Process#isEnableInstanceCompensation()
     * @see #getProcess()
     * @generated
     */
    EAttribute getProcess_EnableInstanceCompensation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Process#getProcessType <em>Process Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Type</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getProcessType()
     * @see #getProcess()
     * @generated
     */
    EAttribute getProcess_ProcessType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Process#getStatus <em>Status</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Status</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getStatus()
     * @see #getProcess()
     * @generated
     */
    EAttribute getProcess_Status();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Process#isSuppressJoinFailure <em>Suppress Join Failure</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Suppress Join Failure</em>'.
     * @see com.tibco.xpd.xpdl2.Process#isSuppressJoinFailure()
     * @see #getProcess()
     * @generated
     */
    EAttribute getProcess_SuppressJoinFailure();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.Process#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Package</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getPackage()
     * @see #getProcess()
     * @generated
     */
    EReference getProcess_Package();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Process#getActivitySets <em>Activity Sets</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Activity Sets</em>'.
     * @see com.tibco.xpd.xpdl2.Process#getActivitySets()
     * @see #getProcess()
     * @generated
     */
    EReference getProcess_ActivitySets();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.FormalParametersContainer <em>Formal Parameters Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Formal Parameters Container</em>'.
     * @see com.tibco.xpd.xpdl2.FormalParametersContainer
     * @generated
     */
    EClass getFormalParametersContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.FormalParametersContainer#getFormalParameters <em>Formal Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Formal Parameters</em>'.
     * @see com.tibco.xpd.xpdl2.FormalParametersContainer#getFormalParameters()
     * @see #getFormalParametersContainer()
     * @generated
     */
    EReference getFormalParametersContainer_FormalParameters();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.AssigmentsContainer <em>Assigments Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Assigments Container</em>'.
     * @see com.tibco.xpd.xpdl2.AssigmentsContainer
     * @generated
     */
    EClass getAssigmentsContainer();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.AssigmentsContainer#getAssignments <em>Assignments</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Assignments</em>'.
     * @see com.tibco.xpd.xpdl2.AssigmentsContainer#getAssignments()
     * @see #getAssigmentsContainer()
     * @generated
     */
    EReference getAssigmentsContainer_Assignments();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.RecordType <em>Record Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Record Type</em>'.
     * @see com.tibco.xpd.xpdl2.RecordType
     * @generated
     */
    EClass getRecordType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.RecordType#getMember <em>Member</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Member</em>'.
     * @see com.tibco.xpd.xpdl2.RecordType#getMember()
     * @see #getRecordType()
     * @generated
     */
    EReference getRecordType_Member();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.RedefinableHeader <em>Redefinable Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Redefinable Header</em>'.
     * @see com.tibco.xpd.xpdl2.RedefinableHeader
     * @generated
     */
    EClass getRedefinableHeader();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getAuthor <em>Author</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Author</em>'.
     * @see com.tibco.xpd.xpdl2.RedefinableHeader#getAuthor()
     * @see #getRedefinableHeader()
     * @generated
     */
    EAttribute getRedefinableHeader_Author();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.xpd.xpdl2.RedefinableHeader#getVersion()
     * @see #getRedefinableHeader()
     * @generated
     */
    EAttribute getRedefinableHeader_Version();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getCodepage <em>Codepage</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Codepage</em>'.
     * @see com.tibco.xpd.xpdl2.RedefinableHeader#getCodepage()
     * @see #getRedefinableHeader()
     * @generated
     */
    EReference getRedefinableHeader_Codepage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getCountrykey <em>Countrykey</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Countrykey</em>'.
     * @see com.tibco.xpd.xpdl2.RedefinableHeader#getCountrykey()
     * @see #getRedefinableHeader()
     * @generated
     */
    EReference getRedefinableHeader_Countrykey();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getResponsibles <em>Responsibles</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Responsibles</em>'.
     * @see com.tibco.xpd.xpdl2.RedefinableHeader#getResponsibles()
     * @see #getRedefinableHeader()
     * @generated
     */
    EReference getRedefinableHeader_Responsibles();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getPublicationStatus <em>Publication Status</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Publication Status</em>'.
     * @see com.tibco.xpd.xpdl2.RedefinableHeader#getPublicationStatus()
     * @see #getRedefinableHeader()
     * @generated
     */
    EAttribute getRedefinableHeader_PublicationStatus();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Reference <em>Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reference</em>'.
     * @see com.tibco.xpd.xpdl2.Reference
     * @generated
     */
    EClass getReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Reference#getActivityId <em>Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Id</em>'.
     * @see com.tibco.xpd.xpdl2.Reference#getActivityId()
     * @see #getReference()
     * @generated
     */
    EAttribute getReference_ActivityId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ResourceCosts <em>Resource Costs</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Resource Costs</em>'.
     * @see com.tibco.xpd.xpdl2.ResourceCosts
     * @generated
     */
    EClass getResourceCosts();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ResourceCosts#getResourceCost <em>Resource Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource Cost</em>'.
     * @see com.tibco.xpd.xpdl2.ResourceCosts#getResourceCost()
     * @see #getResourceCosts()
     * @generated
     */
    EAttribute getResourceCosts_ResourceCost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ResourceCosts#getCostUnitOfTime <em>Cost Unit Of Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cost Unit Of Time</em>'.
     * @see com.tibco.xpd.xpdl2.ResourceCosts#getCostUnitOfTime()
     * @see #getResourceCosts()
     * @generated
     */
    EAttribute getResourceCosts_CostUnitOfTime();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Responsible <em>Responsible</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Responsible</em>'.
     * @see com.tibco.xpd.xpdl2.Responsible
     * @generated
     */
    EClass getResponsible();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Responsible#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Responsible#getValue()
     * @see #getResponsible()
     * @generated
     */
    EAttribute getResponsible_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ResultError <em>Result Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Result Error</em>'.
     * @see com.tibco.xpd.xpdl2.ResultError
     * @generated
     */
    EClass getResultError();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ResultError#getErrorCode <em>Error Code</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Error Code</em>'.
     * @see com.tibco.xpd.xpdl2.ResultError#getErrorCode()
     * @see #getResultError()
     * @generated
     */
    EAttribute getResultError_ErrorCode();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ResultMultiple <em>Result Multiple</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Result Multiple</em>'.
     * @see com.tibco.xpd.xpdl2.ResultMultiple
     * @generated
     */
    EClass getResultMultiple();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ResultMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Message</em>'.
     * @see com.tibco.xpd.xpdl2.ResultMultiple#getTriggerResultMessage()
     * @see #getResultMultiple()
     * @generated
     */
    EReference getResultMultiple_TriggerResultMessage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ResultMultiple#getTriggerResultLink <em>Trigger Result Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Link</em>'.
     * @see com.tibco.xpd.xpdl2.ResultMultiple#getTriggerResultLink()
     * @see #getResultMultiple()
     * @generated
     */
    EReference getResultMultiple_TriggerResultLink();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ResultMultiple#getResultCompensation <em>Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.ResultMultiple#getResultCompensation()
     * @see #getResultMultiple()
     * @generated
     */
    EReference getResultMultiple_ResultCompensation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ResultMultiple#getResultError <em>Result Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Result Error</em>'.
     * @see com.tibco.xpd.xpdl2.ResultMultiple#getResultError()
     * @see #getResultMultiple()
     * @generated
     */
    EReference getResultMultiple_ResultError();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Role <em>Role</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Role</em>'.
     * @see com.tibco.xpd.xpdl2.Role
     * @generated
     */
    EClass getRole();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Role#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.Role#getName()
     * @see #getRole()
     * @generated
     */
    EAttribute getRole_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Role#getPortType <em>Port Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Port Type</em>'.
     * @see com.tibco.xpd.xpdl2.Role#getPortType()
     * @see #getRole()
     * @generated
     */
    EAttribute getRole_PortType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Route <em>Route</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Route</em>'.
     * @see com.tibco.xpd.xpdl2.Route
     * @generated
     */
    EClass getRoute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Route#getGatewayType <em>Gateway Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Gateway Type</em>'.
     * @see com.tibco.xpd.xpdl2.Route#getGatewayType()
     * @see #getRoute()
     * @generated
     */
    EAttribute getRoute_GatewayType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Route#getDeprecatedXorType <em>Deprecated Xor Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Xor Type</em>'.
     * @see com.tibco.xpd.xpdl2.Route#getDeprecatedXorType()
     * @see #getRoute()
     * @generated
     */
    EAttribute getRoute_DeprecatedXorType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Route#isDeprecatedInstantiate <em>Deprecated Instantiate</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Instantiate</em>'.
     * @see com.tibco.xpd.xpdl2.Route#isDeprecatedInstantiate()
     * @see #getRoute()
     * @generated
     */
    EAttribute getRoute_DeprecatedInstantiate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Route#getExclusiveType <em>Exclusive Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Exclusive Type</em>'.
     * @see com.tibco.xpd.xpdl2.Route#getExclusiveType()
     * @see #getRoute()
     * @generated
     */
    EAttribute getRoute_ExclusiveType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Route#isMarkerVisible <em>Marker Visible</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Marker Visible</em>'.
     * @see com.tibco.xpd.xpdl2.Route#isMarkerVisible()
     * @see #getRoute()
     * @generated
     */
    EAttribute getRoute_MarkerVisible();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Route#getIncomingCondition <em>Incoming Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Incoming Condition</em>'.
     * @see com.tibco.xpd.xpdl2.Route#getIncomingCondition()
     * @see #getRoute()
     * @generated
     */
    EAttribute getRoute_IncomingCondition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Route#getOutgoingCondition <em>Outgoing Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Outgoing Condition</em>'.
     * @see com.tibco.xpd.xpdl2.Route#getOutgoingCondition()
     * @see #getRoute()
     * @generated
     */
    EAttribute getRoute_OutgoingCondition();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.RuleName <em>Rule Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Rule Name</em>'.
     * @see com.tibco.xpd.xpdl2.RuleName
     * @generated
     */
    EClass getRuleName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.RuleName#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.RuleName#getValue()
     * @see #getRuleName()
     * @generated
     */
    EAttribute getRuleName_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Rule <em>Rule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Rule</em>'.
     * @see com.tibco.xpd.xpdl2.Rule
     * @generated
     */
    EClass getRule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Rule#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Expression</em>'.
     * @see com.tibco.xpd.xpdl2.Rule#getExpression()
     * @see #getRule()
     * @generated
     */
    EReference getRule_Expression();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Scale <em>Scale</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Scale</em>'.
     * @see com.tibco.xpd.xpdl2.Scale
     * @generated
     */
    EClass getScale();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Scale#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.Scale#getValue()
     * @see #getScale()
     * @generated
     */
    EAttribute getScale_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Schema <em>Schema</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Schema</em>'.
     * @see com.tibco.xpd.xpdl2.Schema
     * @generated
     */
    EClass getSchema();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.Schema#getAny <em>Any</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any</em>'.
     * @see com.tibco.xpd.xpdl2.Schema#getAny()
     * @see #getSchema()
     * @generated
     */
    EAttribute getSchema_Any();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Script <em>Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Script</em>'.
     * @see com.tibco.xpd.xpdl2.Script
     * @generated
     */
    EClass getScript();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.Script#getAny <em>Any</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Any</em>'.
     * @see com.tibco.xpd.xpdl2.Script#getAny()
     * @see #getScript()
     * @generated
     */
    EAttribute getScript_Any();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Script#getGrammar <em>Grammar</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Grammar</em>'.
     * @see com.tibco.xpd.xpdl2.Script#getGrammar()
     * @see #getScript()
     * @generated
     */
    EAttribute getScript_Grammar();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Script#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.xpdl2.Script#getType()
     * @see #getScript()
     * @generated
     */
    EAttribute getScript_Type();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Script#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.xpd.xpdl2.Script#getVersion()
     * @see #getScript()
     * @generated
     */
    EAttribute getScript_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Service <em>Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Service</em>'.
     * @see com.tibco.xpd.xpdl2.Service
     * @generated
     */
    EClass getService();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Service#getEndPoint <em>End Point</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>End Point</em>'.
     * @see com.tibco.xpd.xpdl2.Service#getEndPoint()
     * @see #getService()
     * @generated
     */
    EReference getService_EndPoint();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Service#getPortName <em>Port Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Port Name</em>'.
     * @see com.tibco.xpd.xpdl2.Service#getPortName()
     * @see #getService()
     * @generated
     */
    EAttribute getService_PortName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Service#getServiceName <em>Service Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Service Name</em>'.
     * @see com.tibco.xpd.xpdl2.Service#getServiceName()
     * @see #getService()
     * @generated
     */
    EAttribute getService_ServiceName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.SimulationInformation <em>Simulation Information</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Simulation Information</em>'.
     * @see com.tibco.xpd.xpdl2.SimulationInformation
     * @generated
     */
    EClass getSimulationInformation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.SimulationInformation#getCost <em>Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Cost</em>'.
     * @see com.tibco.xpd.xpdl2.SimulationInformation#getCost()
     * @see #getSimulationInformation()
     * @generated
     */
    EReference getSimulationInformation_Cost();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.SimulationInformation#getTimeEstimation <em>Time Estimation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Time Estimation</em>'.
     * @see com.tibco.xpd.xpdl2.SimulationInformation#getTimeEstimation()
     * @see #getSimulationInformation()
     * @generated
     */
    EReference getSimulationInformation_TimeEstimation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.SimulationInformation#getInstantiation <em>Instantiation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Instantiation</em>'.
     * @see com.tibco.xpd.xpdl2.SimulationInformation#getInstantiation()
     * @see #getSimulationInformation()
     * @generated
     */
    EAttribute getSimulationInformation_Instantiation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Split <em>Split</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Split</em>'.
     * @see com.tibco.xpd.xpdl2.Split
     * @generated
     */
    EClass getSplit();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Split#getTransitionRefs <em>Transition Refs</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Transition Refs</em>'.
     * @see com.tibco.xpd.xpdl2.Split#getTransitionRefs()
     * @see #getSplit()
     * @generated
     */
    EReference getSplit_TransitionRefs();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Split#getOutgoingCondition <em>Outgoing Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Outgoing Condition</em>'.
     * @see com.tibco.xpd.xpdl2.Split#getOutgoingCondition()
     * @see #getSplit()
     * @generated
     */
    EAttribute getSplit_OutgoingCondition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Split#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.xpdl2.Split#getType()
     * @see #getSplit()
     * @generated
     */
    EAttribute getSplit_Type();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Split#getExclusiveType <em>Exclusive Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Exclusive Type</em>'.
     * @see com.tibco.xpd.xpdl2.Split#getExclusiveType()
     * @see #getSplit()
     * @generated
     */
    EAttribute getSplit_ExclusiveType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.StartEvent <em>Start Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Start Event</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent
     * @generated
     */
    EClass getStartEvent();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Message</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getTriggerResultMessage()
     * @see #getStartEvent()
     * @generated
     */
    EReference getStartEvent_TriggerResultMessage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerTimer <em>Trigger Timer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Timer</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getTriggerTimer()
     * @see #getStartEvent()
     * @generated
     */
    EReference getStartEvent_TriggerTimer();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerConditional <em>Trigger Conditional</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Conditional</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getTriggerConditional()
     * @see #getStartEvent()
     * @generated
     */
    EReference getStartEvent_TriggerConditional();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Signal</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getTriggerResultSignal()
     * @see #getStartEvent()
     * @generated
     */
    EReference getStartEvent_TriggerResultSignal();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerMultiple <em>Trigger Multiple</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Multiple</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getTriggerMultiple()
     * @see #getStartEvent()
     * @generated
     */
    EReference getStartEvent_TriggerMultiple();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.StartEvent#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getImplementation()
     * @see #getStartEvent()
     * @generated
     */
    EAttribute getStartEvent_Implementation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.StartEvent#getTrigger <em>Trigger</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Trigger</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getTrigger()
     * @see #getStartEvent()
     * @generated
     */
    EAttribute getStartEvent_Trigger();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.StartEvent#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deprecated Trigger Rule</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getDeprecatedTriggerRule()
     * @see #getStartEvent()
     * @generated
     */
    EReference getStartEvent_DeprecatedTriggerRule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.StartEvent#getDeprecatedTriggerResultLink <em>Deprecated Trigger Result Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deprecated Trigger Result Link</em>'.
     * @see com.tibco.xpd.xpdl2.StartEvent#getDeprecatedTriggerResultLink()
     * @see #getStartEvent()
     * @generated
     */
    EReference getStartEvent_DeprecatedTriggerResultLink();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.SubFlow <em>Sub Flow</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Sub Flow</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow
     * @generated
     */
    EClass getSubFlow();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.SubFlow#getActualParameters <em>Actual Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Actual Parameters</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getActualParameters()
     * @see #getSubFlow()
     * @generated
     */
    EReference getSubFlow_ActualParameters();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.SubFlow#getDataMappings <em>Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Mappings</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getDataMappings()
     * @see #getSubFlow()
     * @generated
     */
    EReference getSubFlow_DataMappings();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.SubFlow#getExecution <em>Execution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Execution</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getExecution()
     * @see #getSubFlow()
     * @generated
     */
    EAttribute getSubFlow_Execution();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.SubFlow#getInstanceDataField <em>Instance Data Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Instance Data Field</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getInstanceDataField()
     * @see #getSubFlow()
     * @generated
     */
    EAttribute getSubFlow_InstanceDataField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.SubFlow#getProcessId <em>Process Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Id</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getProcessId()
     * @see #getSubFlow()
     * @generated
     */
    EAttribute getSubFlow_ProcessId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.SubFlow#getPackageRefId <em>Package Ref Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Package Ref Id</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getPackageRefId()
     * @see #getSubFlow()
     * @generated
     */
    EAttribute getSubFlow_PackageRefId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.SubFlow#getStartActivityId <em>Start Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Activity Id</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getStartActivityId()
     * @see #getSubFlow()
     * @generated
     */
    EAttribute getSubFlow_StartActivityId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.SubFlow#getStartActivitySetId <em>Start Activity Set Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Activity Set Id</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getStartActivitySetId()
     * @see #getSubFlow()
     * @generated
     */
    EAttribute getSubFlow_StartActivitySetId();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.SubFlow#getEndPoint <em>End Point</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>End Point</em>'.
     * @see com.tibco.xpd.xpdl2.SubFlow#getEndPoint()
     * @see #getSubFlow()
     * @generated
     */
    EReference getSubFlow_EndPoint();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TaskApplication <em>Task Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task Application</em>'.
     * @see com.tibco.xpd.xpdl2.TaskApplication
     * @generated
     */
    EClass getTaskApplication();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.TaskApplication#getActualParameters <em>Actual Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Actual Parameters</em>'.
     * @see com.tibco.xpd.xpdl2.TaskApplication#getActualParameters()
     * @see #getTaskApplication()
     * @generated
     */
    EReference getTaskApplication_ActualParameters();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.TaskApplication#getDataMappings <em>Data Mappings</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Data Mappings</em>'.
     * @see com.tibco.xpd.xpdl2.TaskApplication#getDataMappings()
     * @see #getTaskApplication()
     * @generated
     */
    EReference getTaskApplication_DataMappings();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TaskApplication#getPackageRef <em>Package Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Package Ref</em>'.
     * @see com.tibco.xpd.xpdl2.TaskApplication#getPackageRef()
     * @see #getTaskApplication()
     * @generated
     */
    EAttribute getTaskApplication_PackageRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TaskApplication#getApplicationId <em>Application Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Application Id</em>'.
     * @see com.tibco.xpd.xpdl2.TaskApplication#getApplicationId()
     * @see #getTaskApplication()
     * @generated
     */
    EAttribute getTaskApplication_ApplicationId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TaskManual <em>Task Manual</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task Manual</em>'.
     * @see com.tibco.xpd.xpdl2.TaskManual
     * @generated
     */
    EClass getTaskManual();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.TaskManual#getPerformers <em>Performers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Performers</em>'.
     * @see com.tibco.xpd.xpdl2.TaskManual#getPerformers()
     * @see #getTaskManual()
     * @generated
     */
    EReference getTaskManual_Performers();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TaskReceive <em>Task Receive</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task Receive</em>'.
     * @see com.tibco.xpd.xpdl2.TaskReceive
     * @generated
     */
    EClass getTaskReceive();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskReceive#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message</em>'.
     * @see com.tibco.xpd.xpdl2.TaskReceive#getMessage()
     * @see #getTaskReceive()
     * @generated
     */
    EReference getTaskReceive_Message();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskReceive#getWebServiceOperation <em>Web Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Web Service Operation</em>'.
     * @see com.tibco.xpd.xpdl2.TaskReceive#getWebServiceOperation()
     * @see #getTaskReceive()
     * @generated
     */
    EReference getTaskReceive_WebServiceOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TaskReceive#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.TaskReceive#getImplementation()
     * @see #getTaskReceive()
     * @generated
     */
    EAttribute getTaskReceive_Implementation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TaskReceive#isInstantiate <em>Instantiate</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Instantiate</em>'.
     * @see com.tibco.xpd.xpdl2.TaskReceive#isInstantiate()
     * @see #getTaskReceive()
     * @generated
     */
    EAttribute getTaskReceive_Instantiate();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TaskReference <em>Task Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task Reference</em>'.
     * @see com.tibco.xpd.xpdl2.TaskReference
     * @generated
     */
    EClass getTaskReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TaskReference#getTaskRef <em>Task Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Task Ref</em>'.
     * @see com.tibco.xpd.xpdl2.TaskReference#getTaskRef()
     * @see #getTaskReference()
     * @generated
     */
    EAttribute getTaskReference_TaskRef();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TaskScript <em>Task Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task Script</em>'.
     * @see com.tibco.xpd.xpdl2.TaskScript
     * @generated
     */
    EClass getTaskScript();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskScript#getScript <em>Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Script</em>'.
     * @see com.tibco.xpd.xpdl2.TaskScript#getScript()
     * @see #getTaskScript()
     * @generated
     */
    EReference getTaskScript_Script();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TaskSend <em>Task Send</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task Send</em>'.
     * @see com.tibco.xpd.xpdl2.TaskSend
     * @generated
     */
    EClass getTaskSend();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskSend#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message</em>'.
     * @see com.tibco.xpd.xpdl2.TaskSend#getMessage()
     * @see #getTaskSend()
     * @generated
     */
    EReference getTaskSend_Message();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskSend#getWebServiceOperation <em>Web Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Web Service Operation</em>'.
     * @see com.tibco.xpd.xpdl2.TaskSend#getWebServiceOperation()
     * @see #getTaskSend()
     * @generated
     */
    EReference getTaskSend_WebServiceOperation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.TaskSend#getWebServiceFaultCatch <em>Web Service Fault Catch</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Web Service Fault Catch</em>'.
     * @see com.tibco.xpd.xpdl2.TaskSend#getWebServiceFaultCatch()
     * @see #getTaskSend()
     * @generated
     */
    EReference getTaskSend_WebServiceFaultCatch();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TaskSend#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.TaskSend#getImplementation()
     * @see #getTaskSend()
     * @generated
     */
    EAttribute getTaskSend_Implementation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TaskService <em>Task Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task Service</em>'.
     * @see com.tibco.xpd.xpdl2.TaskService
     * @generated
     */
    EClass getTaskService();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskService#getMessageIn <em>Message In</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message In</em>'.
     * @see com.tibco.xpd.xpdl2.TaskService#getMessageIn()
     * @see #getTaskService()
     * @generated
     */
    EReference getTaskService_MessageIn();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskService#getMessageOut <em>Message Out</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Out</em>'.
     * @see com.tibco.xpd.xpdl2.TaskService#getMessageOut()
     * @see #getTaskService()
     * @generated
     */
    EReference getTaskService_MessageOut();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskService#getWebServiceOperation <em>Web Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Web Service Operation</em>'.
     * @see com.tibco.xpd.xpdl2.TaskService#getWebServiceOperation()
     * @see #getTaskService()
     * @generated
     */
    EReference getTaskService_WebServiceOperation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.TaskService#getWebServiceFaultCatch <em>Web Service Fault Catch</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Web Service Fault Catch</em>'.
     * @see com.tibco.xpd.xpdl2.TaskService#getWebServiceFaultCatch()
     * @see #getTaskService()
     * @generated
     */
    EReference getTaskService_WebServiceFaultCatch();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TaskService#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.TaskService#getImplementation()
     * @see #getTaskService()
     * @generated
     */
    EAttribute getTaskService_Implementation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Task <em>Task</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task</em>'.
     * @see com.tibco.xpd.xpdl2.Task
     * @generated
     */
    EClass getTask();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Task#getTaskService <em>Task Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Task Service</em>'.
     * @see com.tibco.xpd.xpdl2.Task#getTaskService()
     * @see #getTask()
     * @generated
     */
    EReference getTask_TaskService();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Task#getTaskReceive <em>Task Receive</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Task Receive</em>'.
     * @see com.tibco.xpd.xpdl2.Task#getTaskReceive()
     * @see #getTask()
     * @generated
     */
    EReference getTask_TaskReceive();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Task#getTaskManual <em>Task Manual</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Task Manual</em>'.
     * @see com.tibco.xpd.xpdl2.Task#getTaskManual()
     * @see #getTask()
     * @generated
     */
    EReference getTask_TaskManual();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Task#getTaskReference <em>Task Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Task Reference</em>'.
     * @see com.tibco.xpd.xpdl2.Task#getTaskReference()
     * @see #getTask()
     * @generated
     */
    EReference getTask_TaskReference();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Task#getTaskScript <em>Task Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Task Script</em>'.
     * @see com.tibco.xpd.xpdl2.Task#getTaskScript()
     * @see #getTask()
     * @generated
     */
    EReference getTask_TaskScript();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Task#getTaskSend <em>Task Send</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Task Send</em>'.
     * @see com.tibco.xpd.xpdl2.Task#getTaskSend()
     * @see #getTask()
     * @generated
     */
    EReference getTask_TaskSend();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Task#getTaskUser <em>Task User</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Task User</em>'.
     * @see com.tibco.xpd.xpdl2.Task#getTaskUser()
     * @see #getTask()
     * @generated
     */
    EReference getTask_TaskUser();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Task#getTaskApplication <em>Task Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Task Application</em>'.
     * @see com.tibco.xpd.xpdl2.Task#getTaskApplication()
     * @see #getTask()
     * @generated
     */
    EReference getTask_TaskApplication();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TaskUser <em>Task User</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Task User</em>'.
     * @see com.tibco.xpd.xpdl2.TaskUser
     * @generated
     */
    EClass getTaskUser();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.TaskUser#getPerformers <em>Performers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Performers</em>'.
     * @see com.tibco.xpd.xpdl2.TaskUser#getPerformers()
     * @see #getTaskUser()
     * @generated
     */
    EReference getTaskUser_Performers();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskUser#getMessageIn <em>Message In</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message In</em>'.
     * @see com.tibco.xpd.xpdl2.TaskUser#getMessageIn()
     * @see #getTaskUser()
     * @generated
     */
    EReference getTaskUser_MessageIn();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskUser#getMessageOut <em>Message Out</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Out</em>'.
     * @see com.tibco.xpd.xpdl2.TaskUser#getMessageOut()
     * @see #getTaskUser()
     * @generated
     */
    EReference getTaskUser_MessageOut();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TaskUser#getWebServiceOperation <em>Web Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Web Service Operation</em>'.
     * @see com.tibco.xpd.xpdl2.TaskUser#getWebServiceOperation()
     * @see #getTaskUser()
     * @generated
     */
    EReference getTaskUser_WebServiceOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TaskUser#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation</em>'.
     * @see com.tibco.xpd.xpdl2.TaskUser#getImplementation()
     * @see #getTaskUser()
     * @generated
     */
    EAttribute getTaskUser_Implementation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TimeEstimation <em>Time Estimation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Time Estimation</em>'.
     * @see com.tibco.xpd.xpdl2.TimeEstimation
     * @generated
     */
    EClass getTimeEstimation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TimeEstimation#getWaitingTime <em>Waiting Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Waiting Time</em>'.
     * @see com.tibco.xpd.xpdl2.TimeEstimation#getWaitingTime()
     * @see #getTimeEstimation()
     * @generated
     */
    EReference getTimeEstimation_WaitingTime();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TimeEstimation#getWorkingTime <em>Working Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Working Time</em>'.
     * @see com.tibco.xpd.xpdl2.TimeEstimation#getWorkingTime()
     * @see #getTimeEstimation()
     * @generated
     */
    EReference getTimeEstimation_WorkingTime();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TimeEstimation#getDuration <em>Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Duration</em>'.
     * @see com.tibco.xpd.xpdl2.TimeEstimation#getDuration()
     * @see #getTimeEstimation()
     * @generated
     */
    EReference getTimeEstimation_Duration();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Transaction <em>Transaction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Transaction</em>'.
     * @see com.tibco.xpd.xpdl2.Transaction
     * @generated
     */
    EClass getTransaction();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Transaction#getTransactionId <em>Transaction Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Transaction Id</em>'.
     * @see com.tibco.xpd.xpdl2.Transaction#getTransactionId()
     * @see #getTransaction()
     * @generated
     */
    EAttribute getTransaction_TransactionId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Transaction#getTransactionMethod <em>Transaction Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Transaction Method</em>'.
     * @see com.tibco.xpd.xpdl2.Transaction#getTransactionMethod()
     * @see #getTransaction()
     * @generated
     */
    EAttribute getTransaction_TransactionMethod();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Transaction#getTransactionProtocol <em>Transaction Protocol</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Transaction Protocol</em>'.
     * @see com.tibco.xpd.xpdl2.Transaction#getTransactionProtocol()
     * @see #getTransaction()
     * @generated
     */
    EAttribute getTransaction_TransactionProtocol();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TransitionRef <em>Transition Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Transition Ref</em>'.
     * @see com.tibco.xpd.xpdl2.TransitionRef
     * @generated
     */
    EClass getTransitionRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TransitionRef#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.xpdl2.TransitionRef#getId()
     * @see #getTransitionRef()
     * @generated
     */
    EAttribute getTransitionRef_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TransitionRef#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.TransitionRef#getName()
     * @see #getTransitionRef()
     * @generated
     */
    EAttribute getTransitionRef_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TransitionRestriction <em>Transition Restriction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Transition Restriction</em>'.
     * @see com.tibco.xpd.xpdl2.TransitionRestriction
     * @generated
     */
    EClass getTransitionRestriction();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TransitionRestriction#getJoin <em>Join</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Join</em>'.
     * @see com.tibco.xpd.xpdl2.TransitionRestriction#getJoin()
     * @see #getTransitionRestriction()
     * @generated
     */
    EReference getTransitionRestriction_Join();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TransitionRestriction#getSplit <em>Split</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Split</em>'.
     * @see com.tibco.xpd.xpdl2.TransitionRestriction#getSplit()
     * @see #getTransitionRestriction()
     * @generated
     */
    EReference getTransitionRestriction_Split();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Transition <em>Transition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Transition</em>'.
     * @see com.tibco.xpd.xpdl2.Transition
     * @generated
     */
    EClass getTransition();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Transition#getCondition <em>Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Condition</em>'.
     * @see com.tibco.xpd.xpdl2.Transition#getCondition()
     * @see #getTransition()
     * @generated
     */
    EReference getTransition_Condition();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Transition#getAssignments <em>Assignments</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Assignments</em>'.
     * @see com.tibco.xpd.xpdl2.Transition#getAssignments()
     * @see #getTransition()
     * @generated
     */
    EReference getTransition_Assignments();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.Transition#getObject <em>Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Object</em>'.
     * @see com.tibco.xpd.xpdl2.Transition#getObject()
     * @see #getTransition()
     * @generated
     */
    EReference getTransition_Object();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Transition#getFrom <em>From</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>From</em>'.
     * @see com.tibco.xpd.xpdl2.Transition#getFrom()
     * @see #getTransition()
     * @generated
     */
    EAttribute getTransition_From();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Transition#getQuantity <em>Quantity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Quantity</em>'.
     * @see com.tibco.xpd.xpdl2.Transition#getQuantity()
     * @see #getTransition()
     * @generated
     */
    EAttribute getTransition_Quantity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.Transition#getTo <em>To</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>To</em>'.
     * @see com.tibco.xpd.xpdl2.Transition#getTo()
     * @see #getTransition()
     * @generated
     */
    EAttribute getTransition_To();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.xpdl2.Transition#getFlowContainer <em>Flow Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Flow Container</em>'.
     * @see com.tibco.xpd.xpdl2.Transition#getFlowContainer()
     * @see #getTransition()
     * @generated
     */
    EReference getTransition_FlowContainer();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple <em>Trigger Intermediate Multiple</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Intermediate Multiple</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple
     * @generated
     */
    EClass getTriggerIntermediateMultiple();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Message</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultMessage()
     * @see #getTriggerIntermediateMultiple()
     * @generated
     */
    EReference getTriggerIntermediateMultiple_TriggerResultMessage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerTimer <em>Trigger Timer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Timer</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerTimer()
     * @see #getTriggerIntermediateMultiple()
     * @generated
     */
    EReference getTriggerIntermediateMultiple_TriggerTimer();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getResultError <em>Result Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Result Error</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getResultError()
     * @see #getTriggerIntermediateMultiple()
     * @generated
     */
    EReference getTriggerIntermediateMultiple_ResultError();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultCompensation <em>Trigger Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultCompensation()
     * @see #getTriggerIntermediateMultiple()
     * @generated
     */
    EReference getTriggerIntermediateMultiple_TriggerResultCompensation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deprecated Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getDeprecatedResultCompensation()
     * @see #getTriggerIntermediateMultiple()
     * @generated
     */
    EReference getTriggerIntermediateMultiple_DeprecatedResultCompensation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerConditional <em>Trigger Conditional</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Conditional</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerConditional()
     * @see #getTriggerIntermediateMultiple()
     * @generated
     */
    EReference getTriggerIntermediateMultiple_TriggerConditional();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultLink <em>Trigger Result Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Link</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultLink()
     * @see #getTriggerIntermediateMultiple()
     * @generated
     */
    EReference getTriggerIntermediateMultiple_TriggerResultLink();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerMultiple <em>Trigger Multiple</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Multiple</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerMultiple
     * @generated
     */
    EClass getTriggerMultiple();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Message</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerResultMessage()
     * @see #getTriggerMultiple()
     * @generated
     */
    EReference getTriggerMultiple_TriggerResultMessage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerTimer <em>Trigger Timer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Timer</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerTimer()
     * @see #getTriggerMultiple()
     * @generated
     */
    EReference getTriggerMultiple_TriggerTimer();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerConditional <em>Trigger Conditional</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Conditional</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerConditional()
     * @see #getTriggerMultiple()
     * @generated
     */
    EReference getTriggerMultiple_TriggerConditional();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerResultLink <em>Trigger Result Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Trigger Result Link</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerResultLink()
     * @see #getTriggerMultiple()
     * @generated
     */
    EReference getTriggerMultiple_TriggerResultLink();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deprecated Trigger Rule</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerMultiple#getDeprecatedTriggerRule()
     * @see #getTriggerMultiple()
     * @generated
     */
    EReference getTriggerMultiple_DeprecatedTriggerRule();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerResultCancel <em>Trigger Result Cancel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Result Cancel</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultCancel
     * @generated
     */
    EClass getTriggerResultCancel();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerResultCompensation <em>Trigger Result Compensation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Result Compensation</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultCompensation
     * @generated
     */
    EClass getTriggerResultCompensation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerResultCompensation#getActivityId <em>Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Id</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultCompensation#getActivityId()
     * @see #getTriggerResultCompensation()
     * @generated
     */
    EAttribute getTriggerResultCompensation_ActivityId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerResultSignal <em>Trigger Result Signal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Result Signal</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultSignal
     * @generated
     */
    EClass getTriggerResultSignal();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getProperties <em>Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Properties</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultSignal#getProperties()
     * @see #getTriggerResultSignal()
     * @generated
     */
    EReference getTriggerResultSignal_Properties();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getCatchThrow <em>Catch Throw</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Catch Throw</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultSignal#getCatchThrow()
     * @see #getTriggerResultSignal()
     * @generated
     */
    EAttribute getTriggerResultSignal_CatchThrow();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultSignal#getName()
     * @see #getTriggerResultSignal()
     * @generated
     */
    EAttribute getTriggerResultSignal_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerResultLink <em>Trigger Result Link</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Result Link</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultLink
     * @generated
     */
    EClass getTriggerResultLink();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getDeprecatedLinkId <em>Deprecated Link Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Link Id</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultLink#getDeprecatedLinkId()
     * @see #getTriggerResultLink()
     * @generated
     */
    EAttribute getTriggerResultLink_DeprecatedLinkId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getDeprecatedProcessRef <em>Deprecated Process Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Process Ref</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultLink#getDeprecatedProcessRef()
     * @see #getTriggerResultLink()
     * @generated
     */
    EAttribute getTriggerResultLink_DeprecatedProcessRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getCatchThrow <em>Catch Throw</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Catch Throw</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultLink#getCatchThrow()
     * @see #getTriggerResultLink()
     * @generated
     */
    EAttribute getTriggerResultLink_CatchThrow();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultLink#getName()
     * @see #getTriggerResultLink()
     * @generated
     */
    EAttribute getTriggerResultLink_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerResultMessage <em>Trigger Result Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Result Message</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultMessage
     * @generated
     */
    EClass getTriggerResultMessage();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultMessage#getMessage()
     * @see #getTriggerResultMessage()
     * @generated
     */
    EReference getTriggerResultMessage_Message();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getWebServiceOperation <em>Web Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Web Service Operation</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultMessage#getWebServiceOperation()
     * @see #getTriggerResultMessage()
     * @generated
     */
    EReference getTriggerResultMessage_WebServiceOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getCatchThrow <em>Catch Throw</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Catch Throw</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerResultMessage#getCatchThrow()
     * @see #getTriggerResultMessage()
     * @generated
     */
    EAttribute getTriggerResultMessage_CatchThrow();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerConditional <em>Trigger Conditional</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Conditional</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerConditional
     * @generated
     */
    EClass getTriggerConditional();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerConditional#getConditionName <em>Condition Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Condition Name</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerConditional#getConditionName()
     * @see #getTriggerConditional()
     * @generated
     */
    EAttribute getTriggerConditional_ConditionName();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerConditional#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Expression</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerConditional#getExpression()
     * @see #getTriggerConditional()
     * @generated
     */
    EReference getTriggerConditional_Expression();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TriggerTimer <em>Trigger Timer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Trigger Timer</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerTimer
     * @generated
     */
    EClass getTriggerTimer();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerTimer#getDeprecatedTimeCycle <em>Deprecated Time Cycle</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Time Cycle</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerTimer#getDeprecatedTimeCycle()
     * @see #getTriggerTimer()
     * @generated
     */
    EAttribute getTriggerTimer_DeprecatedTimeCycle();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.TriggerTimer#getDeprecatedTimeDate <em>Deprecated Time Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Deprecated Time Date</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerTimer#getDeprecatedTimeDate()
     * @see #getTriggerTimer()
     * @generated
     */
    EAttribute getTriggerTimer_DeprecatedTimeDate();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerTimer#getTimeCycle <em>Time Cycle</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Time Cycle</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerTimer#getTimeCycle()
     * @see #getTriggerTimer()
     * @generated
     */
    EReference getTriggerTimer_TimeCycle();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TriggerTimer#getTimeDate <em>Time Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Time Date</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerTimer#getTimeDate()
     * @see #getTriggerTimer()
     * @generated
     */
    EReference getTriggerTimer_TimeDate();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.TypeDeclaration <em>Type Declaration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Type Declaration</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration
     * @generated
     */
    EClass getTypeDeclaration();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getBasicType <em>Basic Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Basic Type</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getBasicType()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_BasicType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getDeclaredType <em>Declared Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Declared Type</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getDeclaredType()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_DeclaredType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getSchemaType <em>Schema Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schema Type</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getSchemaType()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_SchemaType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getExternalReference <em>External Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>External Reference</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getExternalReference()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_ExternalReference();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getRecordType <em>Record Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Record Type</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getRecordType()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_RecordType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getUnionType <em>Union Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Union Type</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getUnionType()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_UnionType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getEnumerationType <em>Enumeration Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Enumeration Type</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getEnumerationType()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_EnumerationType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getArrayType <em>Array Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Array Type</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getArrayType()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_ArrayType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.TypeDeclaration#getListType <em>List Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>List Type</em>'.
     * @see com.tibco.xpd.xpdl2.TypeDeclaration#getListType()
     * @see #getTypeDeclaration()
     * @generated
     */
    EReference getTypeDeclaration_ListType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.UnionType <em>Union Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Union Type</em>'.
     * @see com.tibco.xpd.xpdl2.UnionType
     * @generated
     */
    EClass getUnionType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.UnionType#getMember <em>Member</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Member</em>'.
     * @see com.tibco.xpd.xpdl2.UnionType#getMember()
     * @see #getUnionType()
     * @generated
     */
    EReference getUnionType_Member();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ValidFrom <em>Valid From</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Valid From</em>'.
     * @see com.tibco.xpd.xpdl2.ValidFrom
     * @generated
     */
    EClass getValidFrom();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ValidFrom#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.ValidFrom#getValue()
     * @see #getValidFrom()
     * @generated
     */
    EAttribute getValidFrom_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ValidTo <em>Valid To</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Valid To</em>'.
     * @see com.tibco.xpd.xpdl2.ValidTo
     * @generated
     */
    EClass getValidTo();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ValidTo#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.ValidTo#getValue()
     * @see #getValidTo()
     * @generated
     */
    EAttribute getValidTo_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.VendorExtensions <em>Vendor Extensions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Vendor Extensions</em>'.
     * @see com.tibco.xpd.xpdl2.VendorExtensions
     * @generated
     */
    EClass getVendorExtensions();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.VendorExtensions#getVendorExtension <em>Vendor Extension</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Vendor Extension</em>'.
     * @see com.tibco.xpd.xpdl2.VendorExtensions#getVendorExtension()
     * @see #getVendorExtensions()
     * @generated
     */
    EReference getVendorExtensions_VendorExtension();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.VendorExtension <em>Vendor Extension</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Vendor Extension</em>'.
     * @see com.tibco.xpd.xpdl2.VendorExtension
     * @generated
     */
    EClass getVendorExtension();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.VendorExtension#getExtensionDescription <em>Extension Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Extension Description</em>'.
     * @see com.tibco.xpd.xpdl2.VendorExtension#getExtensionDescription()
     * @see #getVendorExtension()
     * @generated
     */
    EAttribute getVendorExtension_ExtensionDescription();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.VendorExtension#getSchemaLocation <em>Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Schema Location</em>'.
     * @see com.tibco.xpd.xpdl2.VendorExtension#getSchemaLocation()
     * @see #getVendorExtension()
     * @generated
     */
    EAttribute getVendorExtension_SchemaLocation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.VendorExtension#getToolId <em>Tool Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Tool Id</em>'.
     * @see com.tibco.xpd.xpdl2.VendorExtension#getToolId()
     * @see #getVendorExtension()
     * @generated
     */
    EAttribute getVendorExtension_ToolId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.WaitingTime <em>Waiting Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Waiting Time</em>'.
     * @see com.tibco.xpd.xpdl2.WaitingTime
     * @generated
     */
    EClass getWaitingTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.WaitingTime#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.WaitingTime#getValue()
     * @see #getWaitingTime()
     * @generated
     */
    EAttribute getWaitingTime_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch <em>Web Service Fault Catch</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Web Service Fault Catch</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceFaultCatch
     * @generated
     */
    EClass getWebServiceFaultCatch();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceFaultCatch#getMessage()
     * @see #getWebServiceFaultCatch()
     * @generated
     */
    EReference getWebServiceFaultCatch_Message();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getBlockActivity <em>Block Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Block Activity</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceFaultCatch#getBlockActivity()
     * @see #getWebServiceFaultCatch()
     * @generated
     */
    EReference getWebServiceFaultCatch_BlockActivity();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getTransitionRef <em>Transition Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Transition Ref</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceFaultCatch#getTransitionRef()
     * @see #getWebServiceFaultCatch()
     * @generated
     */
    EReference getWebServiceFaultCatch_TransitionRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getFaultName <em>Fault Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Fault Name</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceFaultCatch#getFaultName()
     * @see #getWebServiceFaultCatch()
     * @generated
     */
    EAttribute getWebServiceFaultCatch_FaultName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.WebServiceOperation <em>Web Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Web Service Operation</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceOperation
     * @generated
     */
    EClass getWebServiceOperation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.WebServiceOperation#getPartner <em>Partner</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Partner</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceOperation#getPartner()
     * @see #getWebServiceOperation()
     * @generated
     */
    EReference getWebServiceOperation_Partner();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.WebServiceOperation#getService <em>Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Service</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceOperation#getService()
     * @see #getWebServiceOperation()
     * @generated
     */
    EReference getWebServiceOperation_Service();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.WebServiceOperation#getOperationName <em>Operation Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Operation Name</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceOperation#getOperationName()
     * @see #getWebServiceOperation()
     * @generated
     */
    EAttribute getWebServiceOperation_OperationName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.WebServiceApplication <em>Web Service Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Web Service Application</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceApplication
     * @generated
     */
    EClass getWebServiceApplication();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.WebServiceApplication#getWebServiceOperation <em>Web Service Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Web Service Operation</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceApplication#getWebServiceOperation()
     * @see #getWebServiceApplication()
     * @generated
     */
    EReference getWebServiceApplication_WebServiceOperation();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.WebServiceApplication#getWebServiceFaultCatch <em>Web Service Fault Catch</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Web Service Fault Catch</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceApplication#getWebServiceFaultCatch()
     * @see #getWebServiceApplication()
     * @generated
     */
    EReference getWebServiceApplication_WebServiceFaultCatch();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.WebServiceApplication#getInputMsgName <em>Input Msg Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Input Msg Name</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceApplication#getInputMsgName()
     * @see #getWebServiceApplication()
     * @generated
     */
    EAttribute getWebServiceApplication_InputMsgName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.WebServiceApplication#getOutputMsgName <em>Output Msg Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Output Msg Name</em>'.
     * @see com.tibco.xpd.xpdl2.WebServiceApplication#getOutputMsgName()
     * @see #getWebServiceApplication()
     * @generated
     */
    EAttribute getWebServiceApplication_OutputMsgName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.WorkingTime <em>Working Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Working Time</em>'.
     * @see com.tibco.xpd.xpdl2.WorkingTime
     * @generated
     */
    EClass getWorkingTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.WorkingTime#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.xpdl2.WorkingTime#getValue()
     * @see #getWorkingTime()
     * @generated
     */
    EAttribute getWorkingTime_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.XsltApplication <em>Xslt Application</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xslt Application</em>'.
     * @see com.tibco.xpd.xpdl2.XsltApplication
     * @generated
     */
    EClass getXsltApplication();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.XsltApplication#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Location</em>'.
     * @see com.tibco.xpd.xpdl2.XsltApplication#getLocation()
     * @see #getXsltApplication()
     * @generated
     */
    EAttribute getXsltApplication_Location();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Named Element</em>'.
     * @see com.tibco.xpd.xpdl2.NamedElement
     * @generated
     */
    EClass getNamedElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.NamedElement#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.xpdl2.NamedElement#getName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.FlowContainer <em>Flow Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Flow Container</em>'.
     * @see com.tibco.xpd.xpdl2.FlowContainer
     * @generated
     */
    EClass getFlowContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.FlowContainer#getActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Activities</em>'.
     * @see com.tibco.xpd.xpdl2.FlowContainer#getActivities()
     * @see #getFlowContainer()
     * @generated
     */
    EReference getFlowContainer_Activities();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.FlowContainer#getTransitions <em>Transitions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Transitions</em>'.
     * @see com.tibco.xpd.xpdl2.FlowContainer#getTransitions()
     * @see #getFlowContainer()
     * @generated
     */
    EReference getFlowContainer_Transitions();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.FlowContainer#isAdHoc <em>Ad Hoc</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ad Hoc</em>'.
     * @see com.tibco.xpd.xpdl2.FlowContainer#isAdHoc()
     * @see #getFlowContainer()
     * @generated
     */
    EAttribute getFlowContainer_AdHoc();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.FlowContainer#getAdHocCompletionCondition <em>Ad Hoc Completion Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ad Hoc Completion Condition</em>'.
     * @see com.tibco.xpd.xpdl2.FlowContainer#getAdHocCompletionCondition()
     * @see #getFlowContainer()
     * @generated
     */
    EAttribute getFlowContainer_AdHocCompletionCondition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.FlowContainer#getAdHocOrdering <em>Ad Hoc Ordering</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ad Hoc Ordering</em>'.
     * @see com.tibco.xpd.xpdl2.FlowContainer#getAdHocOrdering()
     * @see #getFlowContainer()
     * @generated
     */
    EAttribute getFlowContainer_AdHocOrdering();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.FlowContainer#getDefaultStartActivityId <em>Default Start Activity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Start Activity Id</em>'.
     * @see com.tibco.xpd.xpdl2.FlowContainer#getDefaultStartActivityId()
     * @see #getFlowContainer()
     * @generated
     */
    EAttribute getFlowContainer_DefaultStartActivityId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.DescribedElement <em>Described Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Described Element</em>'.
     * @see com.tibco.xpd.xpdl2.DescribedElement
     * @generated
     */
    EClass getDescribedElement();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.DescribedElement#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Description</em>'.
     * @see com.tibco.xpd.xpdl2.DescribedElement#getDescription()
     * @see #getDescribedElement()
     * @generated
     */
    EReference getDescribedElement_Description();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.ProcessRelevantData <em>Process Relevant Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Process Relevant Data</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessRelevantData
     * @generated
     */
    EClass getProcessRelevantData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#getDataType <em>Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Data Type</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessRelevantData#getDataType()
     * @see #getProcessRelevantData()
     * @generated
     */
    EReference getProcessRelevantData_DataType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#getLength <em>Length</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Length</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessRelevantData#getLength()
     * @see #getProcessRelevantData()
     * @generated
     */
    EReference getProcessRelevantData_Length();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isIsArray <em>Is Array</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Array</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessRelevantData#isIsArray()
     * @see #getProcessRelevantData()
     * @generated
     */
    EAttribute getProcessRelevantData_IsArray();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isReadOnly <em>Read Only</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Read Only</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessRelevantData#isReadOnly()
     * @see #getProcessRelevantData()
     * @generated
     */
    EAttribute getProcessRelevantData_ReadOnly();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.PropertyInput <em>Property Input</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Property Input</em>'.
     * @see com.tibco.xpd.xpdl2.PropertyInput
     * @generated
     */
    EClass getPropertyInput();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.PropertyInput#getPropertyId <em>Property Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Property Id</em>'.
     * @see com.tibco.xpd.xpdl2.PropertyInput#getPropertyId()
     * @see #getPropertyInput()
     * @generated
     */
    EAttribute getPropertyInput_PropertyId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.UniqueIdElement <em>Unique Id Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unique Id Element</em>'.
     * @see com.tibco.xpd.xpdl2.UniqueIdElement
     * @generated
     */
    EClass getUniqueIdElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.xpdl2.UniqueIdElement#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.xpdl2.UniqueIdElement#getId()
     * @see #getUniqueIdElement()
     * @generated
     */
    EAttribute getUniqueIdElement_Id();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.OtherAttributesContainer <em>Other Attributes Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Other Attributes Container</em>'.
     * @see com.tibco.xpd.xpdl2.OtherAttributesContainer
     * @generated
     */
    EClass getOtherAttributesContainer();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.OtherAttributesContainer#getOtherAttributes <em>Other Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Other Attributes</em>'.
     * @see com.tibco.xpd.xpdl2.OtherAttributesContainer#getOtherAttributes()
     * @see #getOtherAttributesContainer()
     * @generated
     */
    EAttribute getOtherAttributesContainer_OtherAttributes();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.OtherElementsContainer <em>Other Elements Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Other Elements Container</em>'.
     * @see com.tibco.xpd.xpdl2.OtherElementsContainer
     * @generated
     */
    EClass getOtherElementsContainer();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.xpdl2.OtherElementsContainer#getOtherElements <em>Other Elements</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Other Elements</em>'.
     * @see com.tibco.xpd.xpdl2.OtherElementsContainer#getOtherElements()
     * @see #getOtherElementsContainer()
     * @generated
     */
    EAttribute getOtherElementsContainer_OtherElements();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.xpdl2.Performers <em>Performers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Performers</em>'.
     * @see com.tibco.xpd.xpdl2.Performers
     * @generated
     */
    EClass getPerformers();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.xpdl2.Performers#getPerformers <em>Performers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Performers</em>'.
     * @see com.tibco.xpd.xpdl2.Performers#getPerformers()
     * @see #getPerformers()
     * @generated
     */
    EReference getPerformers_Performers();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.AccessLevelType <em>Access Level Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Access Level Type</em>'.
     * @see com.tibco.xpd.xpdl2.AccessLevelType
     * @generated
     */
    EEnum getAccessLevelType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.AdHocOrderingType <em>Ad Hoc Ordering Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Ad Hoc Ordering Type</em>'.
     * @see com.tibco.xpd.xpdl2.AdHocOrderingType
     * @generated
     */
    EEnum getAdHocOrderingType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ArtifactType <em>Artifact Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Artifact Type</em>'.
     * @see com.tibco.xpd.xpdl2.ArtifactType
     * @generated
     */
    EEnum getArtifactType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.AssignTimeType <em>Assign Time Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Assign Time Type</em>'.
     * @see com.tibco.xpd.xpdl2.AssignTimeType
     * @generated
     */
    EEnum getAssignTimeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.AssociationDirectionType <em>Association Direction Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Association Direction Type</em>'.
     * @see com.tibco.xpd.xpdl2.AssociationDirectionType
     * @generated
     */
    EEnum getAssociationDirectionType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.DirectionType <em>Direction Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Direction Type</em>'.
     * @see com.tibco.xpd.xpdl2.DirectionType
     * @generated
     */
    EEnum getDirectionType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.DurationUnitType <em>Duration Unit Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Duration Unit Type</em>'.
     * @see com.tibco.xpd.xpdl2.DurationUnitType
     * @generated
     */
    EEnum getDurationUnitType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.EndPointTypeType <em>End Point Type Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>End Point Type Type</em>'.
     * @see com.tibco.xpd.xpdl2.EndPointTypeType
     * @generated
     */
    EEnum getEndPointTypeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ExclusiveType <em>Exclusive Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Exclusive Type</em>'.
     * @see com.tibco.xpd.xpdl2.ExclusiveType
     * @generated
     */
    EEnum getExclusiveType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ExecutionType <em>Execution Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Execution Type</em>'.
     * @see com.tibco.xpd.xpdl2.ExecutionType
     * @generated
     */
    EEnum getExecutionType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.FinishModeType <em>Finish Mode Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Finish Mode Type</em>'.
     * @see com.tibco.xpd.xpdl2.FinishModeType
     * @generated
     */
    EEnum getFinishModeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.JoinSplitType <em>Join Split Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Join Split Type</em>'.
     * @see com.tibco.xpd.xpdl2.JoinSplitType
     * @generated
     */
    EEnum getJoinSplitType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.GraphConformanceType <em>Graph Conformance Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Graph Conformance Type</em>'.
     * @see com.tibco.xpd.xpdl2.GraphConformanceType
     * @generated
     */
    EEnum getGraphConformanceType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ImplementationType <em>Implementation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Implementation Type</em>'.
     * @see com.tibco.xpd.xpdl2.ImplementationType
     * @generated
     */
    EEnum getImplementationType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.InstantiationType <em>Instantiation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Instantiation Type</em>'.
     * @see com.tibco.xpd.xpdl2.InstantiationType
     * @generated
     */
    EEnum getInstantiationType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.IsArrayType <em>Is Array Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Is Array Type</em>'.
     * @see com.tibco.xpd.xpdl2.IsArrayType
     * @generated
     */
    EEnum getIsArrayType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.LoopType <em>Loop Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Loop Type</em>'.
     * @see com.tibco.xpd.xpdl2.LoopType
     * @generated
     */
    EEnum getLoopType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.MIFlowConditionType <em>MI Flow Condition Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>MI Flow Condition Type</em>'.
     * @see com.tibco.xpd.xpdl2.MIFlowConditionType
     * @generated
     */
    EEnum getMIFlowConditionType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.MIOrderingType <em>MI Ordering Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>MI Ordering Type</em>'.
     * @see com.tibco.xpd.xpdl2.MIOrderingType
     * @generated
     */
    EEnum getMIOrderingType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ModeType <em>Mode Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Mode Type</em>'.
     * @see com.tibco.xpd.xpdl2.ModeType
     * @generated
     */
    EEnum getModeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.OrientationType <em>Orientation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Orientation Type</em>'.
     * @see com.tibco.xpd.xpdl2.OrientationType
     * @generated
     */
    EEnum getOrientationType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ProcessType <em>Process Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Process Type</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessType
     * @generated
     */
    EEnum getProcessType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.PublicationStatusType <em>Publication Status Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Publication Status Type</em>'.
     * @see com.tibco.xpd.xpdl2.PublicationStatusType
     * @generated
     */
    EEnum getPublicationStatusType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ResultType <em>Result Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Result Type</em>'.
     * @see com.tibco.xpd.xpdl2.ResultType
     * @generated
     */
    EEnum getResultType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.RoleType <em>Role Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Role Type</em>'.
     * @see com.tibco.xpd.xpdl2.RoleType
     * @generated
     */
    EEnum getRoleType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.SHAPEType <em>SHAPE Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>SHAPE Type</em>'.
     * @see com.tibco.xpd.xpdl2.SHAPEType
     * @generated
     */
    EEnum getSHAPEType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.StartModeType <em>Start Mode Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Start Mode Type</em>'.
     * @see com.tibco.xpd.xpdl2.StartModeType
     * @generated
     */
    EEnum getStartModeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.StatusType <em>Status Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Status Type</em>'.
     * @see com.tibco.xpd.xpdl2.StatusType
     * @generated
     */
    EEnum getStatusType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.TestTimeType <em>Test Time Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Test Time Type</em>'.
     * @see com.tibco.xpd.xpdl2.TestTimeType
     * @generated
     */
    EEnum getTestTimeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.TransactionMethodType <em>Transaction Method Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Transaction Method Type</em>'.
     * @see com.tibco.xpd.xpdl2.TransactionMethodType
     * @generated
     */
    EEnum getTransactionMethodType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.TriggerType <em>Trigger Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Trigger Type</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerType
     * @generated
     */
    EEnum getTriggerType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ViewType <em>View Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>View Type</em>'.
     * @see com.tibco.xpd.xpdl2.ViewType
     * @generated
     */
    EEnum getViewType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.DeprecatedXorType <em>Deprecated Xor Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Deprecated Xor Type</em>'.
     * @see com.tibco.xpd.xpdl2.DeprecatedXorType
     * @generated
     */
    EEnum getDeprecatedXorType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ConditionType <em>Condition Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Condition Type</em>'.
     * @see com.tibco.xpd.xpdl2.ConditionType
     * @generated
     */
    EEnum getConditionType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.ParticipantType <em>Participant Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Participant Type</em>'.
     * @see com.tibco.xpd.xpdl2.ParticipantType
     * @generated
     */
    EEnum getParticipantType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.BasicTypeType <em>Basic Type Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Basic Type Type</em>'.
     * @see com.tibco.xpd.xpdl2.BasicTypeType
     * @generated
     */
    EEnum getBasicTypeType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance <em>BPMN Model Portability Conformance</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>BPMN Model Portability Conformance</em>'.
     * @see com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance
     * @generated
     */
    EEnum getBPMNModelPortabilityConformance();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.CatchThrow <em>Catch Throw</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Catch Throw</em>'.
     * @see com.tibco.xpd.xpdl2.CatchThrow
     * @generated
     */
    EEnum getCatchThrow();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.xpdl2.GatewayType <em>Gateway Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Gateway Type</em>'.
     * @see com.tibco.xpd.xpdl2.GatewayType
     * @generated
     */
    EEnum getGatewayType();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.AccessLevelType <em>Access Level Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Access Level Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.AccessLevelType
     * @model instanceClass="com.tibco.xpd.xpdl2.AccessLevelType"
     *        extendedMetaData="name='AccessLevel_._type:Object' baseType='AccessLevel_._type'"
     * @generated
     */
    EDataType getAccessLevelTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.AdHocOrderingType <em>Ad Hoc Ordering Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Ad Hoc Ordering Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.AdHocOrderingType
     * @model instanceClass="com.tibco.xpd.xpdl2.AdHocOrderingType"
     *        extendedMetaData="name='AdHocOrdering_._type:Object' baseType='AdHocOrdering_._type'"
     * @generated
     */
    EDataType getAdHocOrderingTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.ArtifactType <em>Artifact Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Artifact Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.ArtifactType
     * @model instanceClass="com.tibco.xpd.xpdl2.ArtifactType"
     *        extendedMetaData="name='ArtifactType_._type:Object' baseType='ArtifactType_._type'"
     * @generated
     */
    EDataType getArtifactTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.AssignTimeType <em>Assign Time Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Assign Time Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.AssignTimeType
     * @model instanceClass="com.tibco.xpd.xpdl2.AssignTimeType"
     *        extendedMetaData="name='AssignTime_._type:Object' baseType='AssignTime_._type'"
     * @generated
     */
    EDataType getAssignTimeTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.AssociationDirectionType <em>Association Direction Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Association Direction Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.AssociationDirectionType
     * @model instanceClass="com.tibco.xpd.xpdl2.AssociationDirectionType"
     *        extendedMetaData="name='AssociationDirection_._type:Object' baseType='AssociationDirection_._type'"
     * @generated
     */
    EDataType getAssociationDirectionTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.DirectionType <em>Direction Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Direction Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.DirectionType
     * @model instanceClass="com.tibco.xpd.xpdl2.DirectionType"
     *        extendedMetaData="name='Direction_._type:Object' baseType='Direction_._type'"
     * @generated
     */
    EDataType getDirectionTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.DurationUnitType <em>Duration Unit Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Duration Unit Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.DurationUnitType
     * @model instanceClass="com.tibco.xpd.xpdl2.DurationUnitType"
     *        extendedMetaData="name='DurationUnit_._type:Object' baseType='DurationUnit_._type'"
     * @generated
     */
    EDataType getDurationUnitTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.EndPointTypeType <em>End Point Type Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>End Point Type Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.EndPointTypeType
     * @model instanceClass="com.tibco.xpd.xpdl2.EndPointTypeType"
     *        extendedMetaData="name='EndPointType_._type:Object' baseType='EndPointType_._type'"
     * @generated
     */
    EDataType getEndPointTypeTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.ExecutionType <em>Execution Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Execution Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.ExecutionType
     * @model instanceClass="com.tibco.xpd.xpdl2.ExecutionType"
     *        extendedMetaData="name='Execution_._type:Object' baseType='Execution_._type'"
     * @generated
     */
    EDataType getExecutionTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.FinishModeType <em>Finish Mode Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Finish Mode Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.FinishModeType
     * @model instanceClass="com.tibco.xpd.xpdl2.FinishModeType"
     *        extendedMetaData="name='FinishMode_._type:Object' baseType='FinishMode_._type'"
     * @generated
     */
    EDataType getFinishModeTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.JoinSplitType <em>Gateway Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Gateway Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.JoinSplitType
     * @model instanceClass="com.tibco.xpd.xpdl2.JoinSplitType"
     *        extendedMetaData="name='GatewayType_._type:Object' baseType='GatewayType_._type'"
     * @generated
     */
    EDataType getGatewayTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.GraphConformanceType <em>Graph Conformance Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Graph Conformance Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.GraphConformanceType
     * @model instanceClass="com.tibco.xpd.xpdl2.GraphConformanceType"
     *        extendedMetaData="name='GraphConformance_._type:Object' baseType='GraphConformance_._type'"
     * @generated
     */
    EDataType getGraphConformanceTypeObject();

    /**
     * Returns the meta object for data type '{@link org.eclipse.emf.common.util.Enumerator <em>Implementation Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Implementation Type Object</em>'.
     * @see org.eclipse.emf.common.util.Enumerator
     * @model instanceClass="org.eclipse.emf.common.util.Enumerator"
     *        extendedMetaData="name='Implementation_._3_._type:Object' baseType='Implementation_._3_._type'"
     * @generated
     */
    EDataType getImplementationTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.InstantiationType <em>Instantiation Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Instantiation Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.InstantiationType
     * @model instanceClass="com.tibco.xpd.xpdl2.InstantiationType"
     *        extendedMetaData="name='Instantiation_._type:Object' baseType='Instantiation_._type'"
     * @generated
     */
    EDataType getInstantiationTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.IsArrayType <em>Is Array Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Is Array Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.IsArrayType
     * @model instanceClass="com.tibco.xpd.xpdl2.IsArrayType"
     *        extendedMetaData="name='IsArray_._type:Object' baseType='IsArray_._type'"
     * @generated
     */
    EDataType getIsArrayTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.LoopType <em>Loop Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Loop Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.LoopType
     * @model instanceClass="com.tibco.xpd.xpdl2.LoopType"
     *        extendedMetaData="name='LoopType_._type:Object' baseType='LoopType_._type'"
     * @generated
     */
    EDataType getLoopTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.MIFlowConditionType <em>MI Flow Condition Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>MI Flow Condition Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.MIFlowConditionType
     * @model instanceClass="com.tibco.xpd.xpdl2.MIFlowConditionType"
     *        extendedMetaData="name='MI_FlowCondition_._type:Object' baseType='MI_FlowCondition_._type'"
     * @generated
     */
    EDataType getMIFlowConditionTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.MIOrderingType <em>MI Ordering Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>MI Ordering Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.MIOrderingType
     * @model instanceClass="com.tibco.xpd.xpdl2.MIOrderingType"
     *        extendedMetaData="name='MI_Ordering_._type:Object' baseType='MI_Ordering_._type'"
     * @generated
     */
    EDataType getMIOrderingTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.ModeType <em>Mode Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Mode Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.ModeType
     * @model instanceClass="com.tibco.xpd.xpdl2.ModeType"
     *        extendedMetaData="name='Mode_._type:Object' baseType='Mode_._type'"
     * @generated
     */
    EDataType getModeTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.OrientationType <em>Orientation Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Orientation Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.OrientationType
     * @model instanceClass="com.tibco.xpd.xpdl2.OrientationType"
     *        extendedMetaData="name='Orientation_._type:Object' baseType='Orientation_._type'"
     * @generated
     */
    EDataType getOrientationTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.ProcessType <em>Process Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Process Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.ProcessType
     * @model instanceClass="com.tibco.xpd.xpdl2.ProcessType"
     *        extendedMetaData="name='ProcessType_._type:Object' baseType='ProcessType_._type'"
     * @generated
     */
    EDataType getProcessTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.PublicationStatusType <em>Publication Status Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Publication Status Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.PublicationStatusType
     * @model instanceClass="com.tibco.xpd.xpdl2.PublicationStatusType"
     *        extendedMetaData="name='PublicationStatus_._type:Object' baseType='PublicationStatus_._type'"
     * @generated
     */
    EDataType getPublicationStatusTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.ResultType <em>Result Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Result Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.ResultType
     * @model instanceClass="com.tibco.xpd.xpdl2.ResultType"
     *        extendedMetaData="name='Result_._type:Object' baseType='Result_._type'"
     * @generated
     */
    EDataType getResultTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.RoleType <em>Role Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Role Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.RoleType
     * @model instanceClass="com.tibco.xpd.xpdl2.RoleType"
     *        extendedMetaData="name='RoleType_._type:Object' baseType='RoleType_._type'"
     * @generated
     */
    EDataType getRoleTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.SHAPEType <em>SHAPE Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>SHAPE Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.SHAPEType
     * @model instanceClass="com.tibco.xpd.xpdl2.SHAPEType"
     *        extendedMetaData="name='SHAPE_._type:Object' baseType='SHAPE_._type'"
     * @generated
     */
    EDataType getSHAPETypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.StartModeType <em>Start Mode Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Start Mode Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.StartModeType
     * @model instanceClass="com.tibco.xpd.xpdl2.StartModeType"
     *        extendedMetaData="name='StartMode_._type:Object' baseType='StartMode_._type'"
     * @generated
     */
    EDataType getStartModeTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.StatusType <em>Status Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Status Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.StatusType
     * @model instanceClass="com.tibco.xpd.xpdl2.StatusType"
     *        extendedMetaData="name='Status_._type:Object' baseType='Status_._type'"
     * @generated
     */
    EDataType getStatusTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.TestTimeType <em>Test Time Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Test Time Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.TestTimeType
     * @model instanceClass="com.tibco.xpd.xpdl2.TestTimeType"
     *        extendedMetaData="name='TestTime_._type:Object' baseType='TestTime_._type'"
     * @generated
     */
    EDataType getTestTimeTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.TransactionMethodType <em>Transaction Method Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Transaction Method Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.TransactionMethodType
     * @model instanceClass="com.tibco.xpd.xpdl2.TransactionMethodType"
     *        extendedMetaData="name='TransactionMethod_._type:Object' baseType='TransactionMethod_._type'"
     * @generated
     */
    EDataType getTransactionMethodTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.xpdl2.TriggerType <em>Trigger Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Trigger Type Object</em>'.
     * @see com.tibco.xpd.xpdl2.TriggerType
     * @model instanceClass="com.tibco.xpd.xpdl2.TriggerType"
     *        extendedMetaData="name='Trigger_._type:Object' baseType='Trigger_._type'"
     * @generated
     */
    EDataType getTriggerTypeObject();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Id Reference String</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Id Reference String</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getIdReferenceString();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    Xpdl2Factory getXpdl2Factory();

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
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ActivitySetImpl <em>Activity Set</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ActivitySetImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getActivitySet()
         * @generated
         */
        EClass ACTIVITY_SET = eINSTANCE.getActivitySet();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY_SET__OBJECT = eINSTANCE.getActivitySet_Object();

        /**
         * The meta object literal for the '<em><b>Process</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY_SET__PROCESS = eINSTANCE.getActivitySet_Process();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ActivityImpl <em>Activity</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ActivityImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getActivity()
         * @generated
         */
        EClass ACTIVITY = eINSTANCE.getActivity();

        /**
         * The meta object literal for the '<em><b>Limit</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__LIMIT = eINSTANCE.getActivity_Limit();

        /**
         * The meta object literal for the '<em><b>Route</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__ROUTE = eINSTANCE.getActivity_Route();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__IMPLEMENTATION = eINSTANCE
                .getActivity_Implementation();

        /**
         * The meta object literal for the '<em><b>Block Activity</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__BLOCK_ACTIVITY = eINSTANCE
                .getActivity_BlockActivity();

        /**
         * The meta object literal for the '<em><b>Event</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__EVENT = eINSTANCE.getActivity_Event();

        /**
         * The meta object literal for the '<em><b>Transaction</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__TRANSACTION = eINSTANCE.getActivity_Transaction();

        /**
         * The meta object literal for the '<em><b>Performer</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__PERFORMER = eINSTANCE.getActivity_Performer();

        /**
         * The meta object literal for the '<em><b>Performers</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__PERFORMERS = eINSTANCE.getActivity_Performers();

        /**
         * The meta object literal for the '<em><b>Priority</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__PRIORITY = eINSTANCE.getActivity_Priority();

        /**
         * The meta object literal for the '<em><b>Deadline</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__DEADLINE = eINSTANCE.getActivity_Deadline();

        /**
         * The meta object literal for the '<em><b>Simulation Information</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__SIMULATION_INFORMATION = eINSTANCE
                .getActivity_SimulationInformation();

        /**
         * The meta object literal for the '<em><b>Icon</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__ICON = eINSTANCE.getActivity_Icon();

        /**
         * The meta object literal for the '<em><b>Documentation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__DOCUMENTATION = eINSTANCE
                .getActivity_Documentation();

        /**
         * The meta object literal for the '<em><b>Transition Restrictions</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__TRANSITION_RESTRICTIONS = eINSTANCE
                .getActivity_TransitionRestrictions();

        /**
         * The meta object literal for the '<em><b>Input Sets</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__INPUT_SETS = eINSTANCE.getActivity_InputSets();

        /**
         * The meta object literal for the '<em><b>Output Sets</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__OUTPUT_SETS = eINSTANCE.getActivity_OutputSets();

        /**
         * The meta object literal for the '<em><b>Io Rules</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__IO_RULES = eINSTANCE.getActivity_IoRules();

        /**
         * The meta object literal for the '<em><b>Loop</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__LOOP = eINSTANCE.getActivity_Loop();

        /**
         * The meta object literal for the '<em><b>Assignments</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__ASSIGNMENTS = eINSTANCE.getActivity_Assignments();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__OBJECT = eINSTANCE.getActivity_Object();

        /**
         * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__EXTENSIONS = eINSTANCE.getActivity_Extensions();

        /**
         * The meta object literal for the '<em><b>Finish Mode</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__FINISH_MODE = eINSTANCE.getActivity_FinishMode();

        /**
         * The meta object literal for the '<em><b>Is ATransaction</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__IS_ATRANSACTION = eINSTANCE
                .getActivity_IsATransaction();

        /**
         * The meta object literal for the '<em><b>Start Activity</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__START_ACTIVITY = eINSTANCE
                .getActivity_StartActivity();

        /**
         * The meta object literal for the '<em><b>Start Mode</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__START_MODE = eINSTANCE.getActivity_StartMode();

        /**
         * The meta object literal for the '<em><b>Start Quantity</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__START_QUANTITY = eINSTANCE
                .getActivity_StartQuantity();

        /**
         * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__STATUS = eINSTANCE.getActivity_Status();

        /**
         * The meta object literal for the '<em><b>Flow Container</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__FLOW_CONTAINER = eINSTANCE
                .getActivity_FlowContainer();

        /**
         * The meta object literal for the '<em><b>Process</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__PROCESS = eINSTANCE.getActivity_Process();

        /**
         * The meta object literal for the '<em><b>Is For Compensation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__IS_FOR_COMPENSATION = eINSTANCE
                .getActivity_IsForCompensation();

        /**
         * The meta object literal for the '<em><b>Completion Quantity</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__COMPLETION_QUANTITY = eINSTANCE
                .getActivity_CompletionQuantity();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl <em>Application Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getApplicationType()
         * @generated
         */
        EClass APPLICATION_TYPE = eINSTANCE.getApplicationType();

        /**
         * The meta object literal for the '<em><b>Ejb</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION_TYPE__EJB = eINSTANCE.getApplicationType_Ejb();

        /**
         * The meta object literal for the '<em><b>Pojo</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION_TYPE__POJO = eINSTANCE.getApplicationType_Pojo();

        /**
         * The meta object literal for the '<em><b>Xslt</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION_TYPE__XSLT = eINSTANCE.getApplicationType_Xslt();

        /**
         * The meta object literal for the '<em><b>Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION_TYPE__SCRIPT = eINSTANCE
                .getApplicationType_Script();

        /**
         * The meta object literal for the '<em><b>Web Service</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION_TYPE__WEB_SERVICE = eINSTANCE
                .getApplicationType_WebService();

        /**
         * The meta object literal for the '<em><b>Business Rule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION_TYPE__BUSINESS_RULE = eINSTANCE
                .getApplicationType_BusinessRule();

        /**
         * The meta object literal for the '<em><b>Form</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION_TYPE__FORM = eINSTANCE.getApplicationType_Form();

        /**
         * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute APPLICATION_TYPE__ANY_ATTRIBUTE = eINSTANCE
                .getApplicationType_AnyAttribute();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ApplicationImpl <em>Application</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ApplicationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getApplication()
         * @generated
         */
        EClass APPLICATION = eINSTANCE.getApplication();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION__TYPE = eINSTANCE.getApplication_Type();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATION__EXTERNAL_REFERENCE = eINSTANCE
                .getApplication_ExternalReference();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ExtendedAttributesContainer <em>Extended Attributes Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ExtendedAttributesContainer
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExtendedAttributesContainer()
         * @generated
         */
        EClass EXTENDED_ATTRIBUTES_CONTAINER = eINSTANCE
                .getExtendedAttributesContainer();

        /**
         * The meta object literal for the '<em><b>Extended Attributes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES =
                eINSTANCE.getExtendedAttributesContainer_ExtendedAttributes();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ArrayTypeImpl <em>Array Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ArrayTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArrayType()
         * @generated
         */
        EClass ARRAY_TYPE = eINSTANCE.getArrayType();

        /**
         * The meta object literal for the '<em><b>Basic Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__BASIC_TYPE = eINSTANCE.getArrayType_BasicType();

        /**
         * The meta object literal for the '<em><b>Declared Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__DECLARED_TYPE = eINSTANCE
                .getArrayType_DeclaredType();

        /**
         * The meta object literal for the '<em><b>Schema Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__SCHEMA_TYPE = eINSTANCE
                .getArrayType_SchemaType();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__EXTERNAL_REFERENCE = eINSTANCE
                .getArrayType_ExternalReference();

        /**
         * The meta object literal for the '<em><b>Record Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__RECORD_TYPE = eINSTANCE
                .getArrayType_RecordType();

        /**
         * The meta object literal for the '<em><b>Union Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__UNION_TYPE = eINSTANCE.getArrayType_UnionType();

        /**
         * The meta object literal for the '<em><b>Enumeration Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__ENUMERATION_TYPE = eINSTANCE
                .getArrayType_EnumerationType();

        /**
         * The meta object literal for the '<em><b>Array Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__ARRAY_TYPE = eINSTANCE.getArrayType_ArrayType();

        /**
         * The meta object literal for the '<em><b>List Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARRAY_TYPE__LIST_TYPE = eINSTANCE.getArrayType_ListType();

        /**
         * The meta object literal for the '<em><b>Lower Index</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ARRAY_TYPE__LOWER_INDEX = eINSTANCE
                .getArrayType_LowerIndex();

        /**
         * The meta object literal for the '<em><b>Upper Index</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ARRAY_TYPE__UPPER_INDEX = eINSTANCE
                .getArrayType_UpperIndex();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ArtifactImpl <em>Artifact</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ArtifactImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArtifact()
         * @generated
         */
        EClass ARTIFACT = eINSTANCE.getArtifact();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARTIFACT__OBJECT = eINSTANCE.getArtifact_Object();

        /**
         * The meta object literal for the '<em><b>Data Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARTIFACT__DATA_OBJECT = eINSTANCE.getArtifact_DataObject();

        /**
         * The meta object literal for the '<em><b>Artifact Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ARTIFACT__ARTIFACT_TYPE = eINSTANCE
                .getArtifact_ArtifactType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARTIFACT__GROUP = eINSTANCE.getArtifact_Group();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ArtifactInputImpl <em>Artifact Input</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ArtifactInputImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArtifactInput()
         * @generated
         */
        EClass ARTIFACT_INPUT = eINSTANCE.getArtifactInput();

        /**
         * The meta object literal for the '<em><b>Artifact Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ARTIFACT_INPUT__ARTIFACT_ID = eINSTANCE
                .getArtifactInput_ArtifactId();

        /**
         * The meta object literal for the '<em><b>Required For Start</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ARTIFACT_INPUT__REQUIRED_FOR_START = eINSTANCE
                .getArtifactInput_RequiredForStart();

        /**
         * The meta object literal for the '<em><b>Text Annotation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ARTIFACT__TEXT_ANNOTATION = eINSTANCE
                .getArtifact_TextAnnotation();

        /**
         * The meta object literal for the '<em><b>Package</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARTIFACT__PACKAGE = eINSTANCE.getArtifact_Package();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.GraphicalNode <em>Graphical Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.GraphicalNode
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGraphicalNode()
         * @generated
         */
        EClass GRAPHICAL_NODE = eINSTANCE.getGraphicalNode();

        /**
         * The meta object literal for the '<em><b>Node Graphics Infos</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GRAPHICAL_NODE__NODE_GRAPHICS_INFOS = eINSTANCE
                .getGraphicalNode_NodeGraphicsInfos();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.GraphicalConnector <em>Graphical Connector</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.GraphicalConnector
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGraphicalConnector()
         * @generated
         */
        EClass GRAPHICAL_CONNECTOR = eINSTANCE.getGraphicalConnector();

        /**
         * The meta object literal for the '<em><b>Connector Graphics Infos</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GRAPHICAL_CONNECTOR__CONNECTOR_GRAPHICS_INFOS = eINSTANCE
                .getGraphicalConnector_ConnectorGraphicsInfos();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.Group <em>Group</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.Group
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGroup()
         * @generated
         */
        EClass GROUP = eINSTANCE.getGroup();

        /**
         * The meta object literal for the '<em><b>Category</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROUP__CATEGORY = eINSTANCE.getGroup_Category();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROUP__OBJECT = eINSTANCE.getGroup_Object();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.AssignmentImpl <em>Assignment</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.AssignmentImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssignment()
         * @generated
         */
        EClass ASSIGNMENT = eINSTANCE.getAssignment();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSIGNMENT__TARGET = eINSTANCE.getAssignment_Target();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSIGNMENT__EXPRESSION = eINSTANCE
                .getAssignment_Expression();

        /**
         * The meta object literal for the '<em><b>Assign Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSIGNMENT__ASSIGN_TIME = eINSTANCE
                .getAssignment_AssignTime();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.AssociationImpl <em>Association</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.AssociationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssociation()
         * @generated
         */
        EClass ASSOCIATION = eINSTANCE.getAssociation();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSOCIATION__OBJECT = eINSTANCE.getAssociation_Object();

        /**
         * The meta object literal for the '<em><b>Association Direction</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATION__ASSOCIATION_DIRECTION = eINSTANCE
                .getAssociation_AssociationDirection();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATION__SOURCE = eINSTANCE.getAssociation_Source();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSOCIATION__TARGET = eINSTANCE.getAssociation_Target();

        /**
         * The meta object literal for the '<em><b>Package</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSOCIATION__PACKAGE = eINSTANCE.getAssociation_Package();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.BasicTypeImpl <em>Basic Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.BasicTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBasicType()
         * @generated
         */
        EClass BASIC_TYPE = eINSTANCE.getBasicType();

        /**
         * The meta object literal for the '<em><b>Length</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BASIC_TYPE__LENGTH = eINSTANCE.getBasicType_Length();

        /**
         * The meta object literal for the '<em><b>Precision</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BASIC_TYPE__PRECISION = eINSTANCE.getBasicType_Precision();

        /**
         * The meta object literal for the '<em><b>Scale</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BASIC_TYPE__SCALE = eINSTANCE.getBasicType_Scale();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASIC_TYPE__TYPE = eINSTANCE.getBasicType_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.BlockActivityImpl <em>Block Activity</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.BlockActivityImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBlockActivity()
         * @generated
         */
        EClass BLOCK_ACTIVITY = eINSTANCE.getBlockActivity();

        /**
         * The meta object literal for the '<em><b>Activity Set Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BLOCK_ACTIVITY__ACTIVITY_SET_ID = eINSTANCE
                .getBlockActivity_ActivitySetId();

        /**
         * The meta object literal for the '<em><b>Start Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BLOCK_ACTIVITY__START_ACTIVITY_ID = eINSTANCE
                .getBlockActivity_StartActivityId();

        /**
         * The meta object literal for the '<em><b>View</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BLOCK_ACTIVITY__VIEW = eINSTANCE.getBlockActivity_View();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.BusinessRuleApplicationImpl <em>Business Rule Application</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.BusinessRuleApplicationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBusinessRuleApplication()
         * @generated
         */
        EClass BUSINESS_RULE_APPLICATION = eINSTANCE
                .getBusinessRuleApplication();

        /**
         * The meta object literal for the '<em><b>Rule Name</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BUSINESS_RULE_APPLICATION__RULE_NAME = eINSTANCE
                .getBusinessRuleApplication_RuleName();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BUSINESS_RULE_APPLICATION__LOCATION = eINSTANCE
                .getBusinessRuleApplication_Location();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.CategoryImpl <em>Category</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.CategoryImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCategory()
         * @generated
         */
        EClass CATEGORY = eINSTANCE.getCategory();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ClassImpl <em>Class</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ClassImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getClass_()
         * @generated
         */
        EClass CLASS = eINSTANCE.getClass_();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CLASS__VALUE = eINSTANCE.getClass_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.CodepageImpl <em>Codepage</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.CodepageImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCodepage()
         * @generated
         */
        EClass CODEPAGE = eINSTANCE.getCodepage();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CODEPAGE__VALUE = eINSTANCE.getCodepage_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ConditionImpl <em>Condition</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ConditionImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCondition()
         * @generated
         */
        EClass CONDITION = eINSTANCE.getCondition();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONDITION__MIXED = eINSTANCE.getCondition_Mixed();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CONDITION__EXPRESSION = eINSTANCE.getCondition_Expression();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONDITION__TYPE = eINSTANCE.getCondition_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ConformanceClassImpl <em>Conformance Class</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ConformanceClassImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getConformanceClass()
         * @generated
         */
        EClass CONFORMANCE_CLASS = eINSTANCE.getConformanceClass();

        /**
         * The meta object literal for the '<em><b>Graph Conformance</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFORMANCE_CLASS__GRAPH_CONFORMANCE = eINSTANCE
                .getConformanceClass_GraphConformance();

        /**
         * The meta object literal for the '<em><b>Bpmn Model Portability Conformance</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFORMANCE_CLASS__BPMN_MODEL_PORTABILITY_CONFORMANCE =
                eINSTANCE.getConformanceClass_BpmnModelPortabilityConformance();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl <em>Connector Graphics Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ConnectorGraphicsInfoImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getConnectorGraphicsInfo()
         * @generated
         */
        EClass CONNECTOR_GRAPHICS_INFO = eINSTANCE.getConnectorGraphicsInfo();

        /**
         * The meta object literal for the '<em><b>Coordinates</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CONNECTOR_GRAPHICS_INFO__COORDINATES = eINSTANCE
                .getConnectorGraphicsInfo_Coordinates();

        /**
         * The meta object literal for the '<em><b>Border Color</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONNECTOR_GRAPHICS_INFO__BORDER_COLOR = eINSTANCE
                .getConnectorGraphicsInfo_BorderColor();

        /**
         * The meta object literal for the '<em><b>Fill Color</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONNECTOR_GRAPHICS_INFO__FILL_COLOR = eINSTANCE
                .getConnectorGraphicsInfo_FillColor();

        /**
         * The meta object literal for the '<em><b>Is Visible</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONNECTOR_GRAPHICS_INFO__IS_VISIBLE = eINSTANCE
                .getConnectorGraphicsInfo_IsVisible();

        /**
         * The meta object literal for the '<em><b>Page Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONNECTOR_GRAPHICS_INFO__PAGE_ID = eINSTANCE
                .getConnectorGraphicsInfo_PageId();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONNECTOR_GRAPHICS_INFO__STYLE = eINSTANCE
                .getConnectorGraphicsInfo_Style();

        /**
         * The meta object literal for the '<em><b>Tool Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONNECTOR_GRAPHICS_INFO__TOOL_ID = eINSTANCE
                .getConnectorGraphicsInfo_ToolId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.CoordinatesImpl <em>Coordinates</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.CoordinatesImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCoordinates()
         * @generated
         */
        EClass COORDINATES = eINSTANCE.getCoordinates();

        /**
         * The meta object literal for the '<em><b>XCoordinate</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COORDINATES__XCOORDINATE = eINSTANCE
                .getCoordinates_XCoordinate();

        /**
         * The meta object literal for the '<em><b>YCoordinate</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COORDINATES__YCOORDINATE = eINSTANCE
                .getCoordinates_YCoordinate();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.CostImpl <em>Cost</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.CostImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCost()
         * @generated
         */
        EClass COST = eINSTANCE.getCost();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COST__VALUE = eINSTANCE.getCost_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.CostStructureImpl <em>Cost Structure</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.CostStructureImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCostStructure()
         * @generated
         */
        EClass COST_STRUCTURE = eINSTANCE.getCostStructure();

        /**
         * The meta object literal for the '<em><b>Fixed Cost</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COST_STRUCTURE__FIXED_COST = eINSTANCE
                .getCostStructure_FixedCost();

        /**
         * The meta object literal for the '<em><b>Resource Costs</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COST_STRUCTURE__RESOURCE_COSTS = eINSTANCE
                .getCostStructure_ResourceCosts();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.CostUnitImpl <em>Cost Unit</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.CostUnitImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCostUnit()
         * @generated
         */
        EClass COST_UNIT = eINSTANCE.getCostUnit();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COST_UNIT__VALUE = eINSTANCE.getCostUnit_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.CountryKeyImpl <em>Country Key</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.CountryKeyImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCountryKey()
         * @generated
         */
        EClass COUNTRY_KEY = eINSTANCE.getCountryKey();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COUNTRY_KEY__VALUE = eINSTANCE.getCountryKey_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DataFieldImpl <em>Data Field</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DataFieldImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataField()
         * @generated
         */
        EClass DATA_FIELD = eINSTANCE.getDataField();

        /**
         * The meta object literal for the '<em><b>Initial Value</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATA_FIELD__INITIAL_VALUE = eINSTANCE
                .getDataField_InitialValue();

        /**
         * The meta object literal for the '<em><b>Correlation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_FIELD__CORRELATION = eINSTANCE
                .getDataField_Correlation();

        /**
         * The meta object literal for the '<em><b>Deprecated Data Is Array</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_FIELD__DEPRECATED_DATA_IS_ARRAY = eINSTANCE
                .getDataField_DeprecatedDataIsArray();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DataMappingImpl <em>Data Mapping</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DataMappingImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataMapping()
         * @generated
         */
        EClass DATA_MAPPING = eINSTANCE.getDataMapping();

        /**
         * The meta object literal for the '<em><b>Actual</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATA_MAPPING__ACTUAL = eINSTANCE.getDataMapping_Actual();

        /**
         * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_MAPPING__DIRECTION = eINSTANCE
                .getDataMapping_Direction();

        /**
         * The meta object literal for the '<em><b>Formal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_MAPPING__FORMAL = eINSTANCE.getDataMapping_Formal();

        /**
         * The meta object literal for the '<em><b>Test Value</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATA_MAPPING__TEST_VALUE = eINSTANCE
                .getDataMapping_TestValue();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DataObjectImpl <em>Data Object</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DataObjectImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataObject()
         * @generated
         */
        EClass DATA_OBJECT = eINSTANCE.getDataObject();

        /**
         * The meta object literal for the '<em><b>Deprecated Produced At Completion</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_OBJECT__DEPRECATED_PRODUCED_AT_COMPLETION = eINSTANCE
                .getDataObject_DeprecatedProducedAtCompletion();

        /**
         * The meta object literal for the '<em><b>Deprecated Required For Start</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_OBJECT__DEPRECATED_REQUIRED_FOR_START = eINSTANCE
                .getDataObject_DeprecatedRequiredForStart();

        /**
         * The meta object literal for the '<em><b>State</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATA_OBJECT__STATE = eINSTANCE.getDataObject_State();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DataTypeImpl <em>Data Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DataTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataType()
         * @generated
         */
        EClass DATA_TYPE = eINSTANCE.getDataType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DeadlineImpl <em>Deadline</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DeadlineImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeadline()
         * @generated
         */
        EClass DEADLINE = eINSTANCE.getDeadline();

        /**
         * The meta object literal for the '<em><b>Deadline Duration</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DEADLINE__DEADLINE_DURATION = eINSTANCE
                .getDeadline_DeadlineDuration();

        /**
         * The meta object literal for the '<em><b>Exception Name</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DEADLINE__EXCEPTION_NAME = eINSTANCE
                .getDeadline_ExceptionName();

        /**
         * The meta object literal for the '<em><b>Execution</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DEADLINE__EXECUTION = eINSTANCE.getDeadline_Execution();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DeclaredTypeImpl <em>Declared Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DeclaredTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeclaredType()
         * @generated
         */
        EClass DECLARED_TYPE = eINSTANCE.getDeclaredType();

        /**
         * The meta object literal for the '<em><b>Type Declaration Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DECLARED_TYPE__TYPE_DECLARATION_ID = eINSTANCE
                .getDeclaredType_TypeDeclarationId();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DECLARED_TYPE__NAME = eINSTANCE.getDeclaredType_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DeprecatedResultCompensationImpl <em>Deprecated Result Compensation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DeprecatedResultCompensationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeprecatedResultCompensation()
         * @generated
         */
        EClass DEPRECATED_RESULT_COMPENSATION = eINSTANCE
                .getDeprecatedResultCompensation();

        /**
         * The meta object literal for the '<em><b>Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DEPRECATED_RESULT_COMPENSATION__ACTIVITY_ID = eINSTANCE
                .getDeprecatedResultCompensation_ActivityId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DeprecatedTriggerRuleImpl <em>Deprecated Trigger Rule</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DeprecatedTriggerRuleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeprecatedTriggerRule()
         * @generated
         */
        EClass DEPRECATED_TRIGGER_RULE = eINSTANCE.getDeprecatedTriggerRule();

        /**
         * The meta object literal for the '<em><b>Rule Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DEPRECATED_TRIGGER_RULE__RULE_NAME = eINSTANCE
                .getDeprecatedTriggerRule_RuleName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DescriptionImpl <em>Description</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DescriptionImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDescription()
         * @generated
         */
        EClass DESCRIPTION = eINSTANCE.getDescription();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DESCRIPTION__VALUE = eINSTANCE.getDescription_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DocumentationImpl <em>Documentation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DocumentationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDocumentation()
         * @generated
         */
        EClass DOCUMENTATION = eINSTANCE.getDocumentation();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENTATION__VALUE = eINSTANCE.getDocumentation_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DocumentRootImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE
                .getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE
                .getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Package</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PACKAGE = eINSTANCE.getDocumentRoot_Package();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DurationImpl <em>Duration</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DurationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDuration()
         * @generated
         */
        EClass DURATION = eINSTANCE.getDuration();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DURATION__VALUE = eINSTANCE.getDuration_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.EjbApplicationImpl <em>Ejb Application</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.EjbApplicationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEjbApplication()
         * @generated
         */
        EClass EJB_APPLICATION = eINSTANCE.getEjbApplication();

        /**
         * The meta object literal for the '<em><b>Jndi Name</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EJB_APPLICATION__JNDI_NAME = eINSTANCE
                .getEjbApplication_JndiName();

        /**
         * The meta object literal for the '<em><b>Home Class</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EJB_APPLICATION__HOME_CLASS = eINSTANCE
                .getEjbApplication_HomeClass();

        /**
         * The meta object literal for the '<em><b>Method</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EJB_APPLICATION__METHOD = eINSTANCE
                .getEjbApplication_Method();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.EndEventImpl <em>End Event</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.EndEventImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEndEvent()
         * @generated
         */
        EClass END_EVENT = eINSTANCE.getEndEvent();

        /**
         * The meta object literal for the '<em><b>Trigger Result Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference END_EVENT__TRIGGER_RESULT_MESSAGE = eINSTANCE
                .getEndEvent_TriggerResultMessage();

        /**
         * The meta object literal for the '<em><b>Result Error</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference END_EVENT__RESULT_ERROR = eINSTANCE
                .getEndEvent_ResultError();

        /**
         * The meta object literal for the '<em><b>Trigger Result Compensation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference END_EVENT__TRIGGER_RESULT_COMPENSATION = eINSTANCE
                .getEndEvent_TriggerResultCompensation();

        /**
         * The meta object literal for the '<em><b>Trigger Result Signal</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference END_EVENT__TRIGGER_RESULT_SIGNAL = eINSTANCE
                .getEndEvent_TriggerResultSignal();

        /**
         * The meta object literal for the '<em><b>Deprecated Result Compensation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference END_EVENT__DEPRECATED_RESULT_COMPENSATION = eINSTANCE
                .getEndEvent_DeprecatedResultCompensation();

        /**
         * The meta object literal for the '<em><b>Result Multiple</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference END_EVENT__RESULT_MULTIPLE = eINSTANCE
                .getEndEvent_ResultMultiple();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute END_EVENT__IMPLEMENTATION = eINSTANCE
                .getEndEvent_Implementation();

        /**
         * The meta object literal for the '<em><b>Result</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute END_EVENT__RESULT = eINSTANCE.getEndEvent_Result();

        /**
         * The meta object literal for the '<em><b>Deprecated Trigger Result Link</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK = eINSTANCE
                .getEndEvent_DeprecatedTriggerResultLink();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.EndPointImpl <em>End Point</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.EndPointImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEndPoint()
         * @generated
         */
        EClass END_POINT = eINSTANCE.getEndPoint();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference END_POINT__EXTERNAL_REFERENCE = eINSTANCE
                .getEndPoint_ExternalReference();

        /**
         * The meta object literal for the '<em><b>End Point Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute END_POINT__END_POINT_TYPE = eINSTANCE
                .getEndPoint_EndPointType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.EnumerationTypeImpl <em>Enumeration Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.EnumerationTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEnumerationType()
         * @generated
         */
        EClass ENUMERATION_TYPE = eINSTANCE.getEnumerationType();

        /**
         * The meta object literal for the '<em><b>Enumeration Value</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ENUMERATION_TYPE__ENUMERATION_VALUE = eINSTANCE
                .getEnumerationType_EnumerationValue();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.EnumerationValueImpl <em>Enumeration Value</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.EnumerationValueImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEnumerationValue()
         * @generated
         */
        EClass ENUMERATION_VALUE = eINSTANCE.getEnumerationValue();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUMERATION_VALUE__NAME = eINSTANCE
                .getEnumerationValue_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.EventImpl <em>Event</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.EventImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEvent()
         * @generated
         */
        EClass EVENT = eINSTANCE.getEvent();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ExceptionNameImpl <em>Exception Name</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ExceptionNameImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExceptionName()
         * @generated
         */
        EClass EXCEPTION_NAME = eINSTANCE.getExceptionName();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXCEPTION_NAME__VALUE = eINSTANCE.getExceptionName_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ExpressionImpl <em>Expression</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ExpressionImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExpression()
         * @generated
         */
        EClass EXPRESSION = eINSTANCE.getExpression();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXPRESSION__MIXED = eINSTANCE.getExpression_Mixed();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXPRESSION__GROUP = eINSTANCE.getExpression_Group();

        /**
         * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXPRESSION__ANY = eINSTANCE.getExpression_Any();

        /**
         * The meta object literal for the '<em><b>Script Grammar</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXPRESSION__SCRIPT_GRAMMAR = eINSTANCE
                .getExpression_ScriptGrammar();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ExtendedAttributeImpl <em>Extended Attribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ExtendedAttributeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExtendedAttribute()
         * @generated
         */
        EClass EXTENDED_ATTRIBUTE = eINSTANCE.getExtendedAttribute();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTENDED_ATTRIBUTE__MIXED = eINSTANCE
                .getExtendedAttribute_Mixed();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTENDED_ATTRIBUTE__GROUP = eINSTANCE
                .getExtendedAttribute_Group();

        /**
         * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTENDED_ATTRIBUTE__ANY = eINSTANCE
                .getExtendedAttribute_Any();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTENDED_ATTRIBUTE__NAME = eINSTANCE
                .getExtendedAttribute_Name();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTENDED_ATTRIBUTE__VALUE = eINSTANCE
                .getExtendedAttribute_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ExternalPackageImpl <em>External Package</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ExternalPackageImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExternalPackage()
         * @generated
         */
        EClass EXTERNAL_PACKAGE = eINSTANCE.getExternalPackage();

        /**
         * The meta object literal for the '<em><b>Href</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTERNAL_PACKAGE__HREF = eINSTANCE.getExternalPackage_Href();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ExternalReferenceImpl <em>External Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ExternalReferenceImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExternalReference()
         * @generated
         */
        EClass EXTERNAL_REFERENCE = eINSTANCE.getExternalReference();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTERNAL_REFERENCE__LOCATION = eINSTANCE
                .getExternalReference_Location();

        /**
         * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTERNAL_REFERENCE__NAMESPACE = eINSTANCE
                .getExternalReference_Namespace();

        /**
         * The meta object literal for the '<em><b>Xref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTERNAL_REFERENCE__XREF = eINSTANCE
                .getExternalReference_Xref();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.FormalParameterImpl <em>Formal Parameter</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.FormalParameterImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFormalParameter()
         * @generated
         */
        EClass FORMAL_PARAMETER = eINSTANCE.getFormalParameter();

        /**
         * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORMAL_PARAMETER__MODE = eINSTANCE.getFormalParameter_Mode();

        /**
         * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORMAL_PARAMETER__REQUIRED = eINSTANCE
                .getFormalParameter_Required();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.FormLayoutImpl <em>Form Layout</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.FormLayoutImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFormLayout()
         * @generated
         */
        EClass FORM_LAYOUT = eINSTANCE.getFormLayout();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_LAYOUT__MIXED = eINSTANCE.getFormLayout_Mixed();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.FormApplicationImpl <em>Form Application</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.FormApplicationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFormApplication()
         * @generated
         */
        EClass FORM_APPLICATION = eINSTANCE.getFormApplication();

        /**
         * The meta object literal for the '<em><b>Form Layout</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FORM_APPLICATION__FORM_LAYOUT = eINSTANCE
                .getFormApplication_FormLayout();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.HomeClassImpl <em>Home Class</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.HomeClassImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getHomeClass()
         * @generated
         */
        EClass HOME_CLASS = eINSTANCE.getHomeClass();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HOME_CLASS__VALUE = eINSTANCE.getHomeClass_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.IconImpl <em>Icon</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.IconImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIcon()
         * @generated
         */
        EClass ICON = eINSTANCE.getIcon();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ICON__VALUE = eINSTANCE.getIcon_Value();

        /**
         * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ICON__HEIGHT = eINSTANCE.getIcon_Height();

        /**
         * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ICON__SHAPE = eINSTANCE.getIcon_Shape();

        /**
         * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ICON__WIDTH = eINSTANCE.getIcon_Width();

        /**
         * The meta object literal for the '<em><b>XCoord</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ICON__XCOORD = eINSTANCE.getIcon_XCoord();

        /**
         * The meta object literal for the '<em><b>YCoord</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ICON__YCOORD = eINSTANCE.getIcon_YCoord();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ImplementationImpl <em>Implementation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ImplementationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getImplementation()
         * @generated
         */
        EClass IMPLEMENTATION = eINSTANCE.getImplementation();

        /**
         * The meta object literal for the '<em><b>Activity</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference IMPLEMENTATION__ACTIVITY = eINSTANCE
                .getImplementation_Activity();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.InputSetImpl <em>Input Set</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.InputSetImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getInputSet()
         * @generated
         */
        EClass INPUT_SET = eINSTANCE.getInputSet();

        /**
         * The meta object literal for the '<em><b>Input</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INPUT_SET__INPUT = eINSTANCE.getInputSet_Input();

        /**
         * The meta object literal for the '<em><b>Artifact Input</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INPUT_SET__ARTIFACT_INPUT = eINSTANCE
                .getInputSet_ArtifactInput();

        /**
         * The meta object literal for the '<em><b>Property Input</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INPUT_SET__PROPERTY_INPUT = eINSTANCE
                .getInputSet_PropertyInput();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.InputImpl <em>Input</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.InputImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getInput()
         * @generated
         */
        EClass INPUT = eINSTANCE.getInput();

        /**
         * The meta object literal for the '<em><b>Artifact Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INPUT__ARTIFACT_ID = eINSTANCE.getInput_ArtifactId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.IntermediateEventImpl <em>Intermediate Event</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.IntermediateEventImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIntermediateEvent()
         * @generated
         */
        EClass INTERMEDIATE_EVENT = eINSTANCE.getIntermediateEvent();

        /**
         * The meta object literal for the '<em><b>Trigger Result Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__TRIGGER_RESULT_MESSAGE = eINSTANCE
                .getIntermediateEvent_TriggerResultMessage();

        /**
         * The meta object literal for the '<em><b>Trigger Timer</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__TRIGGER_TIMER = eINSTANCE
                .getIntermediateEvent_TriggerTimer();

        /**
         * The meta object literal for the '<em><b>Result Error</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__RESULT_ERROR = eINSTANCE
                .getIntermediateEvent_ResultError();

        /**
         * The meta object literal for the '<em><b>Trigger Result Compensation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__TRIGGER_RESULT_COMPENSATION = eINSTANCE
                .getIntermediateEvent_TriggerResultCompensation();

        /**
         * The meta object literal for the '<em><b>Trigger Conditional</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__TRIGGER_CONDITIONAL = eINSTANCE
                .getIntermediateEvent_TriggerConditional();

        /**
         * The meta object literal for the '<em><b>Trigger Result Link</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__TRIGGER_RESULT_LINK = eINSTANCE
                .getIntermediateEvent_TriggerResultLink();

        /**
         * The meta object literal for the '<em><b>Trigger Intermediate Multiple</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__TRIGGER_INTERMEDIATE_MULTIPLE =
                eINSTANCE.getIntermediateEvent_TriggerIntermediateMultiple();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INTERMEDIATE_EVENT__IMPLEMENTATION = eINSTANCE
                .getIntermediateEvent_Implementation();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INTERMEDIATE_EVENT__TARGET = eINSTANCE
                .getIntermediateEvent_Target();

        /**
         * The meta object literal for the '<em><b>Trigger</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INTERMEDIATE_EVENT__TRIGGER = eINSTANCE
                .getIntermediateEvent_Trigger();

        /**
         * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INTERMEDIATE_EVENT__ANY_ATTRIBUTE = eINSTANCE
                .getIntermediateEvent_AnyAttribute();

        /**
         * The meta object literal for the '<em><b>Trigger Result Cancel</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__TRIGGER_RESULT_CANCEL = eINSTANCE
                .getIntermediateEvent_TriggerResultCancel();

        /**
         * The meta object literal for the '<em><b>Trigger Result Signal</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__TRIGGER_RESULT_SIGNAL = eINSTANCE
                .getIntermediateEvent_TriggerResultSignal();

        /**
         * The meta object literal for the '<em><b>Deprecated Trigger Rule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__DEPRECATED_TRIGGER_RULE = eINSTANCE
                .getIntermediateEvent_DeprecatedTriggerRule();

        /**
         * The meta object literal for the '<em><b>Deprecated Result Compensation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERMEDIATE_EVENT__DEPRECATED_RESULT_COMPENSATION =
                eINSTANCE.getIntermediateEvent_DeprecatedResultCompensation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.IORulesImpl <em>IO Rules</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.IORulesImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIORules()
         * @generated
         */
        EClass IO_RULES = eINSTANCE.getIORules();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference IO_RULES__EXPRESSION = eINSTANCE.getIORules_Expression();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.JndiNameImpl <em>Jndi Name</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.JndiNameImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getJndiName()
         * @generated
         */
        EClass JNDI_NAME = eINSTANCE.getJndiName();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute JNDI_NAME__VALUE = eINSTANCE.getJndiName_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.JoinImpl <em>Join</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.JoinImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getJoin()
         * @generated
         */
        EClass JOIN = eINSTANCE.getJoin();

        /**
         * The meta object literal for the '<em><b>Incoming Condtion</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute JOIN__INCOMING_CONDTION = eINSTANCE
                .getJoin_IncomingCondtion();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute JOIN__TYPE = eINSTANCE.getJoin_Type();

        /**
         * The meta object literal for the '<em><b>Exclusive Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute JOIN__EXCLUSIVE_TYPE = eINSTANCE.getJoin_ExclusiveType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.LayoutInfoImpl <em>Layout Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.LayoutInfoImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLayoutInfo()
         * @generated
         */
        EClass LAYOUT_INFO = eINSTANCE.getLayoutInfo();

        /**
         * The meta object literal for the '<em><b>Pixels Per Millimeter</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LAYOUT_INFO__PIXELS_PER_MILLIMETER = eINSTANCE
                .getLayoutInfo_PixelsPerMillimeter();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.LaneImpl <em>Lane</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.LaneImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLane()
         * @generated
         */
        EClass LANE = eINSTANCE.getLane();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LANE__OBJECT = eINSTANCE.getLane_Object();

        /**
         * The meta object literal for the '<em><b>Deprecated Parent Lane</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LANE__DEPRECATED_PARENT_LANE = eINSTANCE
                .getLane_DeprecatedParentLane();

        /**
         * The meta object literal for the '<em><b>Deprecated Parent Pool Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LANE__DEPRECATED_PARENT_POOL_ID = eINSTANCE
                .getLane_DeprecatedParentPoolId();

        /**
         * The meta object literal for the '<em><b>Parent Pool</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LANE__PARENT_POOL = eINSTANCE.getLane_ParentPool();

        /**
         * The meta object literal for the '<em><b>Performers</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LANE__PERFORMERS = eINSTANCE.getLane_Performers();

        /**
         * The meta object literal for the '<em><b>Nested Lane</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LANE__NESTED_LANE = eINSTANCE.getLane_NestedLane();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.LengthImpl <em>Length</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.LengthImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLength()
         * @generated
         */
        EClass LENGTH = eINSTANCE.getLength();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LENGTH__VALUE = eINSTANCE.getLength_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.LimitImpl <em>Limit</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.LimitImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLimit()
         * @generated
         */
        EClass LIMIT = eINSTANCE.getLimit();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LIMIT__VALUE = eINSTANCE.getLimit_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ListTypeImpl <em>List Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ListTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getListType()
         * @generated
         */
        EClass LIST_TYPE = eINSTANCE.getListType();

        /**
         * The meta object literal for the '<em><b>Basic Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__BASIC_TYPE = eINSTANCE.getListType_BasicType();

        /**
         * The meta object literal for the '<em><b>Declared Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__DECLARED_TYPE = eINSTANCE
                .getListType_DeclaredType();

        /**
         * The meta object literal for the '<em><b>Schema Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__SCHEMA_TYPE = eINSTANCE.getListType_SchemaType();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__EXTERNAL_REFERENCE = eINSTANCE
                .getListType_ExternalReference();

        /**
         * The meta object literal for the '<em><b>Record Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__RECORD_TYPE = eINSTANCE.getListType_RecordType();

        /**
         * The meta object literal for the '<em><b>Union Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__UNION_TYPE = eINSTANCE.getListType_UnionType();

        /**
         * The meta object literal for the '<em><b>Enumeration Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__ENUMERATION_TYPE = eINSTANCE
                .getListType_EnumerationType();

        /**
         * The meta object literal for the '<em><b>Array Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__ARRAY_TYPE = eINSTANCE.getListType_ArrayType();

        /**
         * The meta object literal for the '<em><b>List Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LIST_TYPE__LIST_TYPE = eINSTANCE.getListType_ListType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.LocationImpl <em>Location</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.LocationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLocation()
         * @generated
         */
        EClass LOCATION = eINSTANCE.getLocation();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOCATION__VALUE = eINSTANCE.getLocation_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl <em>Loop Multi Instance</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.LoopMultiInstanceImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoopMultiInstance()
         * @generated
         */
        EClass LOOP_MULTI_INSTANCE = eINSTANCE.getLoopMultiInstance();

        /**
         * The meta object literal for the '<em><b>Complex MI Flow Condition</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOOP_MULTI_INSTANCE__COMPLEX_MI_FLOW_CONDITION = eINSTANCE
                .getLoopMultiInstance_ComplexMIFlowCondition();

        /**
         * The meta object literal for the '<em><b>Attribute Complex MI Flow Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_MULTI_INSTANCE__ATTRIBUTE_COMPLEX_MI_FLOW_CONDITION =
                eINSTANCE
                        .getLoopMultiInstance_AttributeComplexMI_FlowCondition();

        /**
         * The meta object literal for the '<em><b>Attribute MI Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_MULTI_INSTANCE__ATTRIBUTE_MI_CONDITION = eINSTANCE
                .getLoopMultiInstance_AttributeMI_Condition();

        /**
         * The meta object literal for the '<em><b>Loop Counter</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_MULTI_INSTANCE__LOOP_COUNTER = eINSTANCE
                .getLoopMultiInstance_LoopCounter();

        /**
         * The meta object literal for the '<em><b>MI Condition</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOOP_MULTI_INSTANCE__MI_CONDITION = eINSTANCE
                .getLoopMultiInstance_MICondition();

        /**
         * The meta object literal for the '<em><b>MI Flow Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_MULTI_INSTANCE__MI_FLOW_CONDITION = eINSTANCE
                .getLoopMultiInstance_MIFlowCondition();

        /**
         * The meta object literal for the '<em><b>MI Ordering</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_MULTI_INSTANCE__MI_ORDERING = eINSTANCE
                .getLoopMultiInstance_MIOrdering();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.LoopStandardImpl <em>Loop Standard</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.LoopStandardImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoopStandard()
         * @generated
         */
        EClass LOOP_STANDARD = eINSTANCE.getLoopStandard();

        /**
         * The meta object literal for the '<em><b>Loop Condition</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOOP_STANDARD__LOOP_CONDITION = eINSTANCE
                .getLoopStandard_LoopCondition();

        /**
         * The meta object literal for the '<em><b>Loop Counter</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_STANDARD__LOOP_COUNTER = eINSTANCE
                .getLoopStandard_LoopCounter();

        /**
         * The meta object literal for the '<em><b>Loop Maximum</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_STANDARD__LOOP_MAXIMUM = eINSTANCE
                .getLoopStandard_LoopMaximum();

        /**
         * The meta object literal for the '<em><b>Test Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_STANDARD__TEST_TIME = eINSTANCE
                .getLoopStandard_TestTime();

        /**
         * The meta object literal for the '<em><b>Attribute Loop Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_STANDARD__ATTRIBUTE_LOOP_CONDITION = eINSTANCE
                .getLoopStandard_AttributeLoopCondition();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.LoopImpl <em>Loop</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.LoopImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoop()
         * @generated
         */
        EClass LOOP = eINSTANCE.getLoop();

        /**
         * The meta object literal for the '<em><b>Loop Standard</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOOP__LOOP_STANDARD = eINSTANCE.getLoop_LoopStandard();

        /**
         * The meta object literal for the '<em><b>Loop Multi Instance</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOOP__LOOP_MULTI_INSTANCE = eINSTANCE
                .getLoop_LoopMultiInstance();

        /**
         * The meta object literal for the '<em><b>Loop Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP__LOOP_TYPE = eINSTANCE.getLoop_LoopType();

        /**
         * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP__ANY_ATTRIBUTE = eINSTANCE.getLoop_AnyAttribute();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.MemberImpl <em>Member</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.MemberImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMember()
         * @generated
         */
        EClass MEMBER = eINSTANCE.getMember();

        /**
         * The meta object literal for the '<em><b>Basic Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__BASIC_TYPE = eINSTANCE.getMember_BasicType();

        /**
         * The meta object literal for the '<em><b>Declared Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__DECLARED_TYPE = eINSTANCE.getMember_DeclaredType();

        /**
         * The meta object literal for the '<em><b>Schema Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__SCHEMA_TYPE = eINSTANCE.getMember_SchemaType();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__EXTERNAL_REFERENCE = eINSTANCE
                .getMember_ExternalReference();

        /**
         * The meta object literal for the '<em><b>Record Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__RECORD_TYPE = eINSTANCE.getMember_RecordType();

        /**
         * The meta object literal for the '<em><b>Union Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__UNION_TYPE = eINSTANCE.getMember_UnionType();

        /**
         * The meta object literal for the '<em><b>Enumeration Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__ENUMERATION_TYPE = eINSTANCE
                .getMember_EnumerationType();

        /**
         * The meta object literal for the '<em><b>Array Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__ARRAY_TYPE = eINSTANCE.getMember_ArrayType();

        /**
         * The meta object literal for the '<em><b>List Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MEMBER__LIST_TYPE = eINSTANCE.getMember_ListType();

        /**
         * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MEMBER__ANY_ATTRIBUTE = eINSTANCE.getMember_AnyAttribute();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl <em>Message Flow</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.MessageFlowImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMessageFlow()
         * @generated
         */
        EClass MESSAGE_FLOW = eINSTANCE.getMessageFlow();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGE_FLOW__MESSAGE = eINSTANCE.getMessageFlow_Message();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGE_FLOW__OBJECT = eINSTANCE.getMessageFlow_Object();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MESSAGE_FLOW__SOURCE = eINSTANCE.getMessageFlow_Source();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MESSAGE_FLOW__TARGET = eINSTANCE.getMessageFlow_Target();

        /**
         * The meta object literal for the '<em><b>Package</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGE_FLOW__PACKAGE = eINSTANCE.getMessageFlow_Package();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.MessageImpl <em>Message</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.MessageImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMessage()
         * @generated
         */
        EClass MESSAGE = eINSTANCE.getMessage();

        /**
         * The meta object literal for the '<em><b>Actual Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGE__ACTUAL_PARAMETERS = eINSTANCE
                .getMessage_ActualParameters();

        /**
         * The meta object literal for the '<em><b>Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGE__DATA_MAPPINGS = eINSTANCE.getMessage_DataMappings();

        /**
         * The meta object literal for the '<em><b>Fault Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MESSAGE__FAULT_NAME = eINSTANCE.getMessage_FaultName();

        /**
         * The meta object literal for the '<em><b>From</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MESSAGE__FROM = eINSTANCE.getMessage_From();

        /**
         * The meta object literal for the '<em><b>To</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MESSAGE__TO = eINSTANCE.getMessage_To();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.MethodImpl <em>Method</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.MethodImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMethod()
         * @generated
         */
        EClass METHOD = eINSTANCE.getMethod();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute METHOD__VALUE = eINSTANCE.getMethod_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ModificationDateImpl <em>Modification Date</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ModificationDateImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getModificationDate()
         * @generated
         */
        EClass MODIFICATION_DATE = eINSTANCE.getModificationDate();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.MyRoleImpl <em>My Role</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.MyRoleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMyRole()
         * @generated
         */
        EClass MY_ROLE = eINSTANCE.getMyRole();

        /**
         * The meta object literal for the '<em><b>Role Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MY_ROLE__ROLE_NAME = eINSTANCE.getMyRole_RoleName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl <em>Node Graphics Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.NodeGraphicsInfoImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getNodeGraphicsInfo()
         * @generated
         */
        EClass NODE_GRAPHICS_INFO = eINSTANCE.getNodeGraphicsInfo();

        /**
         * The meta object literal for the '<em><b>Coordinates</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference NODE_GRAPHICS_INFO__COORDINATES = eINSTANCE
                .getNodeGraphicsInfo_Coordinates();

        /**
         * The meta object literal for the '<em><b>Border Color</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__BORDER_COLOR = eINSTANCE
                .getNodeGraphicsInfo_BorderColor();

        /**
         * The meta object literal for the '<em><b>Fill Color</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__FILL_COLOR = eINSTANCE
                .getNodeGraphicsInfo_FillColor();

        /**
         * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__HEIGHT = eINSTANCE
                .getNodeGraphicsInfo_Height();

        /**
         * The meta object literal for the '<em><b>Is Visible</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__IS_VISIBLE = eINSTANCE
                .getNodeGraphicsInfo_IsVisible();

        /**
         * The meta object literal for the '<em><b>Lane Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__LANE_ID = eINSTANCE
                .getNodeGraphicsInfo_LaneId();

        /**
         * The meta object literal for the '<em><b>Page</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__PAGE = eINSTANCE
                .getNodeGraphicsInfo_Page();

        /**
         * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__SHAPE = eINSTANCE
                .getNodeGraphicsInfo_Shape();

        /**
         * The meta object literal for the '<em><b>Tool Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__TOOL_ID = eINSTANCE
                .getNodeGraphicsInfo_ToolId();

        /**
         * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__WIDTH = eINSTANCE
                .getNodeGraphicsInfo_Width();

        /**
         * The meta object literal for the '<em><b>Page Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NODE_GRAPHICS_INFO__PAGE_ID = eINSTANCE
                .getNodeGraphicsInfo_PageId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.NoImpl <em>No</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.NoImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getNo()
         * @generated
         */
        EClass NO = eINSTANCE.getNo();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ObjectImpl <em>Object</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ObjectImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getObject()
         * @generated
         */
        EClass OBJECT = eINSTANCE.getObject();

        /**
         * The meta object literal for the '<em><b>Categories</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OBJECT__CATEGORIES = eINSTANCE.getObject_Categories();

        /**
         * The meta object literal for the '<em><b>Documentation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OBJECT__DOCUMENTATION = eINSTANCE.getObject_Documentation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.OutputSetImpl <em>Output Set</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.OutputSetImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOutputSet()
         * @generated
         */
        EClass OUTPUT_SET = eINSTANCE.getOutputSet();

        /**
         * The meta object literal for the '<em><b>Output</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OUTPUT_SET__OUTPUT = eINSTANCE.getOutputSet_Output();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.OutputImpl <em>Output</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.OutputImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOutput()
         * @generated
         */
        EClass OUTPUT = eINSTANCE.getOutput();

        /**
         * The meta object literal for the '<em><b>Artifact Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT__ARTIFACT_ID = eINSTANCE.getOutput_ArtifactId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PackageHeaderImpl <em>Package Header</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PackageHeaderImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPackageHeader()
         * @generated
         */
        EClass PACKAGE_HEADER = eINSTANCE.getPackageHeader();

        /**
         * The meta object literal for the '<em><b>Xpdl Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PACKAGE_HEADER__XPDL_VERSION = eINSTANCE
                .getPackageHeader_XpdlVersion();

        /**
         * The meta object literal for the '<em><b>Vendor</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PACKAGE_HEADER__VENDOR = eINSTANCE.getPackageHeader_Vendor();

        /**
         * The meta object literal for the '<em><b>Created</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PACKAGE_HEADER__CREATED = eINSTANCE
                .getPackageHeader_Created();

        /**
         * The meta object literal for the '<em><b>Documentation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE_HEADER__DOCUMENTATION = eINSTANCE
                .getPackageHeader_Documentation();

        /**
         * The meta object literal for the '<em><b>Priority Unit</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE_HEADER__PRIORITY_UNIT = eINSTANCE
                .getPackageHeader_PriorityUnit();

        /**
         * The meta object literal for the '<em><b>Cost Unit</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE_HEADER__COST_UNIT = eINSTANCE
                .getPackageHeader_CostUnit();

        /**
         * The meta object literal for the '<em><b>Vendor Extensions</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE_HEADER__VENDOR_EXTENSIONS = eINSTANCE
                .getPackageHeader_VendorExtensions();

        /**
         * The meta object literal for the '<em><b>Layout Info</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE_HEADER__LAYOUT_INFO = eINSTANCE
                .getPackageHeader_LayoutInfo();

        /**
         * The meta object literal for the '<em><b>Modification Date</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE_HEADER__MODIFICATION_DATE = eINSTANCE
                .getPackageHeader_ModificationDate();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PackageImpl <em>Package</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PackageImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPackage()
         * @generated
         */
        EClass PACKAGE = eINSTANCE.getPackage();

        /**
         * The meta object literal for the '<em><b>Package Header</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__PACKAGE_HEADER = eINSTANCE
                .getPackage_PackageHeader();

        /**
         * The meta object literal for the '<em><b>Redefinable Header</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__REDEFINABLE_HEADER = eINSTANCE
                .getPackage_RedefinableHeader();

        /**
         * The meta object literal for the '<em><b>Conformance Class</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__CONFORMANCE_CLASS = eINSTANCE
                .getPackage_ConformanceClass();

        /**
         * The meta object literal for the '<em><b>Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__SCRIPT = eINSTANCE.getPackage_Script();

        /**
         * The meta object literal for the '<em><b>External Packages</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__EXTERNAL_PACKAGES = eINSTANCE
                .getPackage_ExternalPackages();

        /**
         * The meta object literal for the '<em><b>Type Declarations</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__TYPE_DECLARATIONS = eINSTANCE
                .getPackage_TypeDeclarations();

        /**
         * The meta object literal for the '<em><b>Partner Link Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__PARTNER_LINK_TYPES = eINSTANCE
                .getPackage_PartnerLinkTypes();

        /**
         * The meta object literal for the '<em><b>Pools</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__POOLS = eINSTANCE.getPackage_Pools();

        /**
         * The meta object literal for the '<em><b>Message Flows</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__MESSAGE_FLOWS = eINSTANCE.getPackage_MessageFlows();

        /**
         * The meta object literal for the '<em><b>Associations</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__ASSOCIATIONS = eINSTANCE.getPackage_Associations();

        /**
         * The meta object literal for the '<em><b>Artifacts</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__ARTIFACTS = eINSTANCE.getPackage_Artifacts();

        /**
         * The meta object literal for the '<em><b>Processes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PACKAGE__PROCESSES = eINSTANCE.getPackage_Processes();

        /**
         * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PACKAGE__LANGUAGE = eINSTANCE.getPackage_Language();

        /**
         * The meta object literal for the '<em><b>Query Language</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PACKAGE__QUERY_LANGUAGE = eINSTANCE
                .getPackage_QueryLanguage();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PageImpl <em>Page</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PageImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPage()
         * @generated
         */
        EClass PAGE = eINSTANCE.getPage();

        /**
         * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE__HEIGHT = eINSTANCE.getPage_Height();

        /**
         * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE__WIDTH = eINSTANCE.getPage_Width();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PagesImpl <em>Pages</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PagesImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPages()
         * @generated
         */
        EClass PAGES = eINSTANCE.getPages();

        /**
         * The meta object literal for the '<em><b>Page</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PAGES__PAGE = eINSTANCE.getPages_Page();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ApplicationsContainer <em>Applications Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ApplicationsContainer
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getApplicationsContainer()
         * @generated
         */
        EClass APPLICATIONS_CONTAINER = eINSTANCE.getApplicationsContainer();

        /**
         * The meta object literal for the '<em><b>Applications</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference APPLICATIONS_CONTAINER__APPLICATIONS = eINSTANCE
                .getApplicationsContainer_Applications();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ParticipantsContainer <em>Participants Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ParticipantsContainer
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getParticipantsContainer()
         * @generated
         */
        EClass PARTICIPANTS_CONTAINER = eINSTANCE.getParticipantsContainer();

        /**
         * The meta object literal for the '<em><b>Participants</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTICIPANTS_CONTAINER__PARTICIPANTS = eINSTANCE
                .getParticipantsContainer_Participants();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.DataFieldsContainerImpl <em>Data Fields Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.DataFieldsContainerImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDataFieldsContainer()
         * @generated
         */
        EClass DATA_FIELDS_CONTAINER = eINSTANCE.getDataFieldsContainer();

        /**
         * The meta object literal for the '<em><b>Data Fields</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATA_FIELDS_CONTAINER__DATA_FIELDS = eINSTANCE
                .getDataFieldsContainer_DataFields();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ParticipantImpl <em>Participant</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ParticipantImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getParticipant()
         * @generated
         */
        EClass PARTICIPANT = eINSTANCE.getParticipant();

        /**
         * The meta object literal for the '<em><b>Participant Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTICIPANT__PARTICIPANT_TYPE = eINSTANCE
                .getParticipant_ParticipantType();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTICIPANT__EXTERNAL_REFERENCE = eINSTANCE
                .getParticipant_ExternalReference();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ParticipantTypeElemImpl <em>Participant Type Elem</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ParticipantTypeElemImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getParticipantTypeElem()
         * @generated
         */
        EClass PARTICIPANT_TYPE_ELEM = eINSTANCE.getParticipantTypeElem();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTICIPANT_TYPE_ELEM__TYPE = eINSTANCE
                .getParticipantTypeElem_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PartnerLinkImpl <em>Partner Link</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PartnerLinkImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPartnerLink()
         * @generated
         */
        EClass PARTNER_LINK = eINSTANCE.getPartnerLink();

        /**
         * The meta object literal for the '<em><b>My Role</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTNER_LINK__MY_ROLE = eINSTANCE.getPartnerLink_MyRole();

        /**
         * The meta object literal for the '<em><b>Partner Role</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTNER_LINK__PARTNER_ROLE = eINSTANCE
                .getPartnerLink_PartnerRole();

        /**
         * The meta object literal for the '<em><b>Partner Link Type Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTNER_LINK__PARTNER_LINK_TYPE_ID = eINSTANCE
                .getPartnerLink_PartnerLinkTypeId();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTNER_LINK__NAME = eINSTANCE.getPartnerLink_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PartnerLinkTypeImpl <em>Partner Link Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PartnerLinkTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPartnerLinkType()
         * @generated
         */
        EClass PARTNER_LINK_TYPE = eINSTANCE.getPartnerLinkType();

        /**
         * The meta object literal for the '<em><b>Role</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTNER_LINK_TYPE__ROLE = eINSTANCE
                .getPartnerLinkType_Role();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTNER_LINK_TYPE__NAME = eINSTANCE
                .getPartnerLinkType_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PartnerRoleImpl <em>Partner Role</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PartnerRoleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPartnerRole()
         * @generated
         */
        EClass PARTNER_ROLE = eINSTANCE.getPartnerRole();

        /**
         * The meta object literal for the '<em><b>End Point</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTNER_ROLE__END_POINT = eINSTANCE
                .getPartnerRole_EndPoint();

        /**
         * The meta object literal for the '<em><b>Port Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTNER_ROLE__PORT_NAME = eINSTANCE
                .getPartnerRole_PortName();

        /**
         * The meta object literal for the '<em><b>Role Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTNER_ROLE__ROLE_NAME = eINSTANCE
                .getPartnerRole_RoleName();

        /**
         * The meta object literal for the '<em><b>Service Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTNER_ROLE__SERVICE_NAME = eINSTANCE
                .getPartnerRole_ServiceName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PartnerImpl <em>Partner</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PartnerImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPartner()
         * @generated
         */
        EClass PARTNER = eINSTANCE.getPartner();

        /**
         * The meta object literal for the '<em><b>Partner Link Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTNER__PARTNER_LINK_ID = eINSTANCE
                .getPartner_PartnerLinkId();

        /**
         * The meta object literal for the '<em><b>Role Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTNER__ROLE_TYPE = eINSTANCE.getPartner_RoleType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PerformerImpl <em>Performer</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PerformerImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPerformer()
         * @generated
         */
        EClass PERFORMER = eINSTANCE.getPerformer();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PERFORMER__VALUE = eINSTANCE.getPerformer_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PojoApplicationImpl <em>Pojo Application</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PojoApplicationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPojoApplication()
         * @generated
         */
        EClass POJO_APPLICATION = eINSTANCE.getPojoApplication();

        /**
         * The meta object literal for the '<em><b>Class</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POJO_APPLICATION__CLASS = eINSTANCE
                .getPojoApplication_Class();

        /**
         * The meta object literal for the '<em><b>Method</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POJO_APPLICATION__METHOD = eINSTANCE
                .getPojoApplication_Method();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PoolImpl <em>Pool</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PoolImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPool()
         * @generated
         */
        EClass POOL = eINSTANCE.getPool();

        /**
         * The meta object literal for the '<em><b>Lanes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POOL__LANES = eINSTANCE.getPool_Lanes();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POOL__OBJECT = eINSTANCE.getPool_Object();

        /**
         * The meta object literal for the '<em><b>Boundary Visible</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POOL__BOUNDARY_VISIBLE = eINSTANCE.getPool_BoundaryVisible();

        /**
         * The meta object literal for the '<em><b>Orientation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POOL__ORIENTATION = eINSTANCE.getPool_Orientation();

        /**
         * The meta object literal for the '<em><b>Participant Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POOL__PARTICIPANT_ID = eINSTANCE.getPool_ParticipantId();

        /**
         * The meta object literal for the '<em><b>Process Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POOL__PROCESS_ID = eINSTANCE.getPool_ProcessId();

        /**
         * The meta object literal for the '<em><b>Parent Package</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POOL__PARENT_PACKAGE = eINSTANCE.getPool_ParentPackage();

        /**
         * The meta object literal for the '<em><b>Main Pool</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POOL__MAIN_POOL = eINSTANCE.getPool_MainPool();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PrecisionImpl <em>Precision</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PrecisionImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPrecision()
         * @generated
         */
        EClass PRECISION = eINSTANCE.getPrecision();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PRECISION__VALUE = eINSTANCE.getPrecision_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PriorityImpl <em>Priority</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PriorityImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPriority()
         * @generated
         */
        EClass PRIORITY = eINSTANCE.getPriority();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PRIORITY__VALUE = eINSTANCE.getPriority_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PriorityUnitImpl <em>Priority Unit</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PriorityUnitImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPriorityUnit()
         * @generated
         */
        EClass PRIORITY_UNIT = eINSTANCE.getPriorityUnit();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PRIORITY_UNIT__VALUE = eINSTANCE.getPriorityUnit_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl <em>Process Header</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcessHeader()
         * @generated
         */
        EClass PROCESS_HEADER = eINSTANCE.getProcessHeader();

        /**
         * The meta object literal for the '<em><b>Created</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS_HEADER__CREATED = eINSTANCE
                .getProcessHeader_Created();

        /**
         * The meta object literal for the '<em><b>Priority</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_HEADER__PRIORITY = eINSTANCE
                .getProcessHeader_Priority();

        /**
         * The meta object literal for the '<em><b>Limit</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_HEADER__LIMIT = eINSTANCE.getProcessHeader_Limit();

        /**
         * The meta object literal for the '<em><b>Valid From</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_HEADER__VALID_FROM = eINSTANCE
                .getProcessHeader_ValidFrom();

        /**
         * The meta object literal for the '<em><b>Valid To</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_HEADER__VALID_TO = eINSTANCE
                .getProcessHeader_ValidTo();

        /**
         * The meta object literal for the '<em><b>Time Estimation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_HEADER__TIME_ESTIMATION = eINSTANCE
                .getProcessHeader_TimeEstimation();

        /**
         * The meta object literal for the '<em><b>Duration Unit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS_HEADER__DURATION_UNIT = eINSTANCE
                .getProcessHeader_DurationUnit();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ProcessImpl <em>Process</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ProcessImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcess()
         * @generated
         */
        EClass PROCESS = eINSTANCE.getProcess();

        /**
         * The meta object literal for the '<em><b>Process Header</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS__PROCESS_HEADER = eINSTANCE
                .getProcess_ProcessHeader();

        /**
         * The meta object literal for the '<em><b>Redefinable Header</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS__REDEFINABLE_HEADER = eINSTANCE
                .getProcess_RedefinableHeader();

        /**
         * The meta object literal for the '<em><b>Partner Links</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS__PARTNER_LINKS = eINSTANCE.getProcess_PartnerLinks();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS__OBJECT = eINSTANCE.getProcess_Object();

        /**
         * The meta object literal for the '<em><b>Extensions</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS__EXTENSIONS = eINSTANCE.getProcess_Extensions();

        /**
         * The meta object literal for the '<em><b>Access Level</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS__ACCESS_LEVEL = eINSTANCE.getProcess_AccessLevel();

        /**
         * The meta object literal for the '<em><b>Default Start Activity Set Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS__DEFAULT_START_ACTIVITY_SET_ID = eINSTANCE
                .getProcess_DefaultStartActivitySetId();

        /**
         * The meta object literal for the '<em><b>Enable Instance Compensation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS__ENABLE_INSTANCE_COMPENSATION = eINSTANCE
                .getProcess_EnableInstanceCompensation();

        /**
         * The meta object literal for the '<em><b>Process Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS__PROCESS_TYPE = eINSTANCE.getProcess_ProcessType();

        /**
         * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS__STATUS = eINSTANCE.getProcess_Status();

        /**
         * The meta object literal for the '<em><b>Suppress Join Failure</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS__SUPPRESS_JOIN_FAILURE = eINSTANCE
                .getProcess_SuppressJoinFailure();

        /**
         * The meta object literal for the '<em><b>Package</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS__PACKAGE = eINSTANCE.getProcess_Package();

        /**
         * The meta object literal for the '<em><b>Activity Sets</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS__ACTIVITY_SETS = eINSTANCE.getProcess_ActivitySets();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.FormalParametersContainer <em>Formal Parameters Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.FormalParametersContainer
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFormalParametersContainer()
         * @generated
         */
        EClass FORMAL_PARAMETERS_CONTAINER = eINSTANCE
                .getFormalParametersContainer();

        /**
         * The meta object literal for the '<em><b>Formal Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS = eINSTANCE
                .getFormalParametersContainer_FormalParameters();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.AssigmentsContainer <em>Assigments Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AssigmentsContainer
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssigmentsContainer()
         * @generated
         */
        EClass ASSIGMENTS_CONTAINER = eINSTANCE.getAssigmentsContainer();

        /**
         * The meta object literal for the '<em><b>Assignments</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSIGMENTS_CONTAINER__ASSIGNMENTS = eINSTANCE
                .getAssigmentsContainer_Assignments();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.RecordTypeImpl <em>Record Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.RecordTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRecordType()
         * @generated
         */
        EClass RECORD_TYPE = eINSTANCE.getRecordType();

        /**
         * The meta object literal for the '<em><b>Member</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RECORD_TYPE__MEMBER = eINSTANCE.getRecordType_Member();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl <em>Redefinable Header</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.RedefinableHeaderImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRedefinableHeader()
         * @generated
         */
        EClass REDEFINABLE_HEADER = eINSTANCE.getRedefinableHeader();

        /**
         * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REDEFINABLE_HEADER__AUTHOR = eINSTANCE
                .getRedefinableHeader_Author();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REDEFINABLE_HEADER__VERSION = eINSTANCE
                .getRedefinableHeader_Version();

        /**
         * The meta object literal for the '<em><b>Codepage</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REDEFINABLE_HEADER__CODEPAGE = eINSTANCE
                .getRedefinableHeader_Codepage();

        /**
         * The meta object literal for the '<em><b>Countrykey</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REDEFINABLE_HEADER__COUNTRYKEY = eINSTANCE
                .getRedefinableHeader_Countrykey();

        /**
         * The meta object literal for the '<em><b>Responsibles</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REDEFINABLE_HEADER__RESPONSIBLES = eINSTANCE
                .getRedefinableHeader_Responsibles();

        /**
         * The meta object literal for the '<em><b>Publication Status</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REDEFINABLE_HEADER__PUBLICATION_STATUS = eINSTANCE
                .getRedefinableHeader_PublicationStatus();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ReferenceImpl <em>Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ReferenceImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getReference()
         * @generated
         */
        EClass REFERENCE = eINSTANCE.getReference();

        /**
         * The meta object literal for the '<em><b>Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REFERENCE__ACTIVITY_ID = eINSTANCE.getReference_ActivityId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ResourceCostsImpl <em>Resource Costs</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ResourceCostsImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResourceCosts()
         * @generated
         */
        EClass RESOURCE_COSTS = eINSTANCE.getResourceCosts();

        /**
         * The meta object literal for the '<em><b>Resource Cost</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE_COSTS__RESOURCE_COST = eINSTANCE
                .getResourceCosts_ResourceCost();

        /**
         * The meta object literal for the '<em><b>Cost Unit Of Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE_COSTS__COST_UNIT_OF_TIME = eINSTANCE
                .getResourceCosts_CostUnitOfTime();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ResponsibleImpl <em>Responsible</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ResponsibleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResponsible()
         * @generated
         */
        EClass RESPONSIBLE = eINSTANCE.getResponsible();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESPONSIBLE__VALUE = eINSTANCE.getResponsible_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ResultErrorImpl <em>Result Error</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ResultErrorImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResultError()
         * @generated
         */
        EClass RESULT_ERROR = eINSTANCE.getResultError();

        /**
         * The meta object literal for the '<em><b>Error Code</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESULT_ERROR__ERROR_CODE = eINSTANCE
                .getResultError_ErrorCode();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ResultMultipleImpl <em>Result Multiple</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ResultMultipleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResultMultiple()
         * @generated
         */
        EClass RESULT_MULTIPLE = eINSTANCE.getResultMultiple();

        /**
         * The meta object literal for the '<em><b>Trigger Result Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESULT_MULTIPLE__TRIGGER_RESULT_MESSAGE = eINSTANCE
                .getResultMultiple_TriggerResultMessage();

        /**
         * The meta object literal for the '<em><b>Trigger Result Link</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESULT_MULTIPLE__TRIGGER_RESULT_LINK = eINSTANCE
                .getResultMultiple_TriggerResultLink();

        /**
         * The meta object literal for the '<em><b>Result Compensation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESULT_MULTIPLE__RESULT_COMPENSATION = eINSTANCE
                .getResultMultiple_ResultCompensation();

        /**
         * The meta object literal for the '<em><b>Result Error</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESULT_MULTIPLE__RESULT_ERROR = eINSTANCE
                .getResultMultiple_ResultError();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.RoleImpl <em>Role</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.RoleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRole()
         * @generated
         */
        EClass ROLE = eINSTANCE.getRole();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROLE__NAME = eINSTANCE.getRole_Name();

        /**
         * The meta object literal for the '<em><b>Port Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROLE__PORT_TYPE = eINSTANCE.getRole_PortType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.RouteImpl <em>Route</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.RouteImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRoute()
         * @generated
         */
        EClass ROUTE = eINSTANCE.getRoute();

        /**
         * The meta object literal for the '<em><b>Gateway Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROUTE__GATEWAY_TYPE = eINSTANCE.getRoute_GatewayType();

        /**
         * The meta object literal for the '<em><b>Deprecated Xor Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROUTE__DEPRECATED_XOR_TYPE = eINSTANCE
                .getRoute_DeprecatedXorType();

        /**
         * The meta object literal for the '<em><b>Deprecated Instantiate</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROUTE__DEPRECATED_INSTANTIATE = eINSTANCE
                .getRoute_DeprecatedInstantiate();

        /**
         * The meta object literal for the '<em><b>Exclusive Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROUTE__EXCLUSIVE_TYPE = eINSTANCE.getRoute_ExclusiveType();

        /**
         * The meta object literal for the '<em><b>Marker Visible</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROUTE__MARKER_VISIBLE = eINSTANCE.getRoute_MarkerVisible();

        /**
         * The meta object literal for the '<em><b>Incoming Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROUTE__INCOMING_CONDITION = eINSTANCE
                .getRoute_IncomingCondition();

        /**
         * The meta object literal for the '<em><b>Outgoing Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROUTE__OUTGOING_CONDITION = eINSTANCE
                .getRoute_OutgoingCondition();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.RuleNameImpl <em>Rule Name</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.RuleNameImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRuleName()
         * @generated
         */
        EClass RULE_NAME = eINSTANCE.getRuleName();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RULE_NAME__VALUE = eINSTANCE.getRuleName_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.RuleImpl <em>Rule</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.RuleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRule()
         * @generated
         */
        EClass RULE = eINSTANCE.getRule();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RULE__EXPRESSION = eINSTANCE.getRule_Expression();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ScaleImpl <em>Scale</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ScaleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getScale()
         * @generated
         */
        EClass SCALE = eINSTANCE.getScale();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCALE__VALUE = eINSTANCE.getScale_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.SchemaImpl <em>Schema</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.SchemaImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSchema()
         * @generated
         */
        EClass SCHEMA = eINSTANCE.getSchema();

        /**
         * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCHEMA__ANY = eINSTANCE.getSchema_Any();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ScriptImpl <em>Script</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ScriptImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getScript()
         * @generated
         */
        EClass SCRIPT = eINSTANCE.getScript();

        /**
         * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT__ANY = eINSTANCE.getScript_Any();

        /**
         * The meta object literal for the '<em><b>Grammar</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT__GRAMMAR = eINSTANCE.getScript_Grammar();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT__TYPE = eINSTANCE.getScript_Type();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCRIPT__VERSION = eINSTANCE.getScript_Version();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ServiceImpl <em>Service</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ServiceImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getService()
         * @generated
         */
        EClass SERVICE = eINSTANCE.getService();

        /**
         * The meta object literal for the '<em><b>End Point</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVICE__END_POINT = eINSTANCE.getService_EndPoint();

        /**
         * The meta object literal for the '<em><b>Port Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVICE__PORT_NAME = eINSTANCE.getService_PortName();

        /**
         * The meta object literal for the '<em><b>Service Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVICE__SERVICE_NAME = eINSTANCE.getService_ServiceName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.SimulationInformationImpl <em>Simulation Information</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.SimulationInformationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSimulationInformation()
         * @generated
         */
        EClass SIMULATION_INFORMATION = eINSTANCE.getSimulationInformation();

        /**
         * The meta object literal for the '<em><b>Cost</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIMULATION_INFORMATION__COST = eINSTANCE
                .getSimulationInformation_Cost();

        /**
         * The meta object literal for the '<em><b>Time Estimation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIMULATION_INFORMATION__TIME_ESTIMATION = eINSTANCE
                .getSimulationInformation_TimeEstimation();

        /**
         * The meta object literal for the '<em><b>Instantiation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIMULATION_INFORMATION__INSTANTIATION = eINSTANCE
                .getSimulationInformation_Instantiation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.SplitImpl <em>Split</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.SplitImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSplit()
         * @generated
         */
        EClass SPLIT = eINSTANCE.getSplit();

        /**
         * The meta object literal for the '<em><b>Transition Refs</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SPLIT__TRANSITION_REFS = eINSTANCE.getSplit_TransitionRefs();

        /**
         * The meta object literal for the '<em><b>Outgoing Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SPLIT__OUTGOING_CONDITION = eINSTANCE
                .getSplit_OutgoingCondition();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SPLIT__TYPE = eINSTANCE.getSplit_Type();

        /**
         * The meta object literal for the '<em><b>Exclusive Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SPLIT__EXCLUSIVE_TYPE = eINSTANCE.getSplit_ExclusiveType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.StartEventImpl <em>Start Event</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.StartEventImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStartEvent()
         * @generated
         */
        EClass START_EVENT = eINSTANCE.getStartEvent();

        /**
         * The meta object literal for the '<em><b>Trigger Result Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference START_EVENT__TRIGGER_RESULT_MESSAGE = eINSTANCE
                .getStartEvent_TriggerResultMessage();

        /**
         * The meta object literal for the '<em><b>Trigger Timer</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference START_EVENT__TRIGGER_TIMER = eINSTANCE
                .getStartEvent_TriggerTimer();

        /**
         * The meta object literal for the '<em><b>Trigger Conditional</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference START_EVENT__TRIGGER_CONDITIONAL = eINSTANCE
                .getStartEvent_TriggerConditional();

        /**
         * The meta object literal for the '<em><b>Trigger Result Signal</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference START_EVENT__TRIGGER_RESULT_SIGNAL = eINSTANCE
                .getStartEvent_TriggerResultSignal();

        /**
         * The meta object literal for the '<em><b>Trigger Multiple</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference START_EVENT__TRIGGER_MULTIPLE = eINSTANCE
                .getStartEvent_TriggerMultiple();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute START_EVENT__IMPLEMENTATION = eINSTANCE
                .getStartEvent_Implementation();

        /**
         * The meta object literal for the '<em><b>Trigger</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute START_EVENT__TRIGGER = eINSTANCE.getStartEvent_Trigger();

        /**
         * The meta object literal for the '<em><b>Deprecated Trigger Rule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference START_EVENT__DEPRECATED_TRIGGER_RULE = eINSTANCE
                .getStartEvent_DeprecatedTriggerRule();

        /**
         * The meta object literal for the '<em><b>Deprecated Trigger Result Link</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference START_EVENT__DEPRECATED_TRIGGER_RESULT_LINK = eINSTANCE
                .getStartEvent_DeprecatedTriggerResultLink();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.SubFlowImpl <em>Sub Flow</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.SubFlowImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSubFlow()
         * @generated
         */
        EClass SUB_FLOW = eINSTANCE.getSubFlow();

        /**
         * The meta object literal for the '<em><b>Actual Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SUB_FLOW__ACTUAL_PARAMETERS = eINSTANCE
                .getSubFlow_ActualParameters();

        /**
         * The meta object literal for the '<em><b>Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SUB_FLOW__DATA_MAPPINGS = eINSTANCE
                .getSubFlow_DataMappings();

        /**
         * The meta object literal for the '<em><b>Execution</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUB_FLOW__EXECUTION = eINSTANCE.getSubFlow_Execution();

        /**
         * The meta object literal for the '<em><b>Instance Data Field</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUB_FLOW__INSTANCE_DATA_FIELD = eINSTANCE
                .getSubFlow_InstanceDataField();

        /**
         * The meta object literal for the '<em><b>Process Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUB_FLOW__PROCESS_ID = eINSTANCE.getSubFlow_ProcessId();

        /**
         * The meta object literal for the '<em><b>Package Ref Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUB_FLOW__PACKAGE_REF_ID = eINSTANCE
                .getSubFlow_PackageRefId();

        /**
         * The meta object literal for the '<em><b>Start Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUB_FLOW__START_ACTIVITY_ID = eINSTANCE
                .getSubFlow_StartActivityId();

        /**
         * The meta object literal for the '<em><b>Start Activity Set Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUB_FLOW__START_ACTIVITY_SET_ID = eINSTANCE
                .getSubFlow_StartActivitySetId();

        /**
         * The meta object literal for the '<em><b>End Point</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SUB_FLOW__END_POINT = eINSTANCE.getSubFlow_EndPoint();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskApplicationImpl <em>Task Application</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskApplicationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskApplication()
         * @generated
         */
        EClass TASK_APPLICATION = eINSTANCE.getTaskApplication();

        /**
         * The meta object literal for the '<em><b>Actual Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_APPLICATION__ACTUAL_PARAMETERS = eINSTANCE
                .getTaskApplication_ActualParameters();

        /**
         * The meta object literal for the '<em><b>Data Mappings</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_APPLICATION__DATA_MAPPINGS = eINSTANCE
                .getTaskApplication_DataMappings();

        /**
         * The meta object literal for the '<em><b>Package Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_APPLICATION__PACKAGE_REF = eINSTANCE
                .getTaskApplication_PackageRef();

        /**
         * The meta object literal for the '<em><b>Application Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_APPLICATION__APPLICATION_ID = eINSTANCE
                .getTaskApplication_ApplicationId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskManualImpl <em>Task Manual</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskManualImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskManual()
         * @generated
         */
        EClass TASK_MANUAL = eINSTANCE.getTaskManual();

        /**
         * The meta object literal for the '<em><b>Performers</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_MANUAL__PERFORMERS = eINSTANCE
                .getTaskManual_Performers();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskReceiveImpl <em>Task Receive</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskReceiveImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskReceive()
         * @generated
         */
        EClass TASK_RECEIVE = eINSTANCE.getTaskReceive();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_RECEIVE__MESSAGE = eINSTANCE.getTaskReceive_Message();

        /**
         * The meta object literal for the '<em><b>Web Service Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_RECEIVE__WEB_SERVICE_OPERATION = eINSTANCE
                .getTaskReceive_WebServiceOperation();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_RECEIVE__IMPLEMENTATION = eINSTANCE
                .getTaskReceive_Implementation();

        /**
         * The meta object literal for the '<em><b>Instantiate</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_RECEIVE__INSTANTIATE = eINSTANCE
                .getTaskReceive_Instantiate();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskReferenceImpl <em>Task Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskReferenceImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskReference()
         * @generated
         */
        EClass TASK_REFERENCE = eINSTANCE.getTaskReference();

        /**
         * The meta object literal for the '<em><b>Task Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_REFERENCE__TASK_REF = eINSTANCE
                .getTaskReference_TaskRef();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskScriptImpl <em>Task Script</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskScriptImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskScript()
         * @generated
         */
        EClass TASK_SCRIPT = eINSTANCE.getTaskScript();

        /**
         * The meta object literal for the '<em><b>Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_SCRIPT__SCRIPT = eINSTANCE.getTaskScript_Script();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskSendImpl <em>Task Send</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskSendImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskSend()
         * @generated
         */
        EClass TASK_SEND = eINSTANCE.getTaskSend();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_SEND__MESSAGE = eINSTANCE.getTaskSend_Message();

        /**
         * The meta object literal for the '<em><b>Web Service Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_SEND__WEB_SERVICE_OPERATION = eINSTANCE
                .getTaskSend_WebServiceOperation();

        /**
         * The meta object literal for the '<em><b>Web Service Fault Catch</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_SEND__WEB_SERVICE_FAULT_CATCH = eINSTANCE
                .getTaskSend_WebServiceFaultCatch();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_SEND__IMPLEMENTATION = eINSTANCE
                .getTaskSend_Implementation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskServiceImpl <em>Task Service</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskServiceImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskService()
         * @generated
         */
        EClass TASK_SERVICE = eINSTANCE.getTaskService();

        /**
         * The meta object literal for the '<em><b>Message In</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_SERVICE__MESSAGE_IN = eINSTANCE
                .getTaskService_MessageIn();

        /**
         * The meta object literal for the '<em><b>Message Out</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_SERVICE__MESSAGE_OUT = eINSTANCE
                .getTaskService_MessageOut();

        /**
         * The meta object literal for the '<em><b>Web Service Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_SERVICE__WEB_SERVICE_OPERATION = eINSTANCE
                .getTaskService_WebServiceOperation();

        /**
         * The meta object literal for the '<em><b>Web Service Fault Catch</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_SERVICE__WEB_SERVICE_FAULT_CATCH = eINSTANCE
                .getTaskService_WebServiceFaultCatch();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_SERVICE__IMPLEMENTATION = eINSTANCE
                .getTaskService_Implementation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskImpl <em>Task</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTask()
         * @generated
         */
        EClass TASK = eINSTANCE.getTask();

        /**
         * The meta object literal for the '<em><b>Task Service</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK__TASK_SERVICE = eINSTANCE.getTask_TaskService();

        /**
         * The meta object literal for the '<em><b>Task Receive</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK__TASK_RECEIVE = eINSTANCE.getTask_TaskReceive();

        /**
         * The meta object literal for the '<em><b>Task Manual</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK__TASK_MANUAL = eINSTANCE.getTask_TaskManual();

        /**
         * The meta object literal for the '<em><b>Task Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK__TASK_REFERENCE = eINSTANCE.getTask_TaskReference();

        /**
         * The meta object literal for the '<em><b>Task Script</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK__TASK_SCRIPT = eINSTANCE.getTask_TaskScript();

        /**
         * The meta object literal for the '<em><b>Task Send</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK__TASK_SEND = eINSTANCE.getTask_TaskSend();

        /**
         * The meta object literal for the '<em><b>Task User</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK__TASK_USER = eINSTANCE.getTask_TaskUser();

        /**
         * The meta object literal for the '<em><b>Task Application</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK__TASK_APPLICATION = eINSTANCE.getTask_TaskApplication();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl <em>Task User</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TaskUserImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTaskUser()
         * @generated
         */
        EClass TASK_USER = eINSTANCE.getTaskUser();

        /**
         * The meta object literal for the '<em><b>Performers</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_USER__PERFORMERS = eINSTANCE.getTaskUser_Performers();

        /**
         * The meta object literal for the '<em><b>Message In</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_USER__MESSAGE_IN = eINSTANCE.getTaskUser_MessageIn();

        /**
         * The meta object literal for the '<em><b>Message Out</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_USER__MESSAGE_OUT = eINSTANCE.getTaskUser_MessageOut();

        /**
         * The meta object literal for the '<em><b>Web Service Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TASK_USER__WEB_SERVICE_OPERATION = eINSTANCE
                .getTaskUser_WebServiceOperation();

        /**
         * The meta object literal for the '<em><b>Implementation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TASK_USER__IMPLEMENTATION = eINSTANCE
                .getTaskUser_Implementation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TimeEstimationImpl <em>Time Estimation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TimeEstimationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTimeEstimation()
         * @generated
         */
        EClass TIME_ESTIMATION = eINSTANCE.getTimeEstimation();

        /**
         * The meta object literal for the '<em><b>Waiting Time</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TIME_ESTIMATION__WAITING_TIME = eINSTANCE
                .getTimeEstimation_WaitingTime();

        /**
         * The meta object literal for the '<em><b>Working Time</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TIME_ESTIMATION__WORKING_TIME = eINSTANCE
                .getTimeEstimation_WorkingTime();

        /**
         * The meta object literal for the '<em><b>Duration</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TIME_ESTIMATION__DURATION = eINSTANCE
                .getTimeEstimation_Duration();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TransactionImpl <em>Transaction</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TransactionImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransaction()
         * @generated
         */
        EClass TRANSACTION = eINSTANCE.getTransaction();

        /**
         * The meta object literal for the '<em><b>Transaction Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSACTION__TRANSACTION_ID = eINSTANCE
                .getTransaction_TransactionId();

        /**
         * The meta object literal for the '<em><b>Transaction Method</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSACTION__TRANSACTION_METHOD = eINSTANCE
                .getTransaction_TransactionMethod();

        /**
         * The meta object literal for the '<em><b>Transaction Protocol</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSACTION__TRANSACTION_PROTOCOL = eINSTANCE
                .getTransaction_TransactionProtocol();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TransitionRefImpl <em>Transition Ref</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TransitionRefImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransitionRef()
         * @generated
         */
        EClass TRANSITION_REF = eINSTANCE.getTransitionRef();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSITION_REF__ID = eINSTANCE.getTransitionRef_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSITION_REF__NAME = eINSTANCE.getTransitionRef_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TransitionRestrictionImpl <em>Transition Restriction</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TransitionRestrictionImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransitionRestriction()
         * @generated
         */
        EClass TRANSITION_RESTRICTION = eINSTANCE.getTransitionRestriction();

        /**
         * The meta object literal for the '<em><b>Join</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRANSITION_RESTRICTION__JOIN = eINSTANCE
                .getTransitionRestriction_Join();

        /**
         * The meta object literal for the '<em><b>Split</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRANSITION_RESTRICTION__SPLIT = eINSTANCE
                .getTransitionRestriction_Split();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TransitionImpl <em>Transition</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TransitionImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransition()
         * @generated
         */
        EClass TRANSITION = eINSTANCE.getTransition();

        /**
         * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRANSITION__CONDITION = eINSTANCE.getTransition_Condition();

        /**
         * The meta object literal for the '<em><b>Assignments</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRANSITION__ASSIGNMENTS = eINSTANCE
                .getTransition_Assignments();

        /**
         * The meta object literal for the '<em><b>Object</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRANSITION__OBJECT = eINSTANCE.getTransition_Object();

        /**
         * The meta object literal for the '<em><b>From</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSITION__FROM = eINSTANCE.getTransition_From();

        /**
         * The meta object literal for the '<em><b>Quantity</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSITION__QUANTITY = eINSTANCE.getTransition_Quantity();

        /**
         * The meta object literal for the '<em><b>To</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSITION__TO = eINSTANCE.getTransition_To();

        /**
         * The meta object literal for the '<em><b>Flow Container</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRANSITION__FLOW_CONTAINER = eINSTANCE
                .getTransition_FlowContainer();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl <em>Trigger Intermediate Multiple</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerIntermediateMultipleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerIntermediateMultiple()
         * @generated
         */
        EClass TRIGGER_INTERMEDIATE_MULTIPLE = eINSTANCE
                .getTriggerIntermediateMultiple();

        /**
         * The meta object literal for the '<em><b>Trigger Result Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_MESSAGE =
                eINSTANCE.getTriggerIntermediateMultiple_TriggerResultMessage();

        /**
         * The meta object literal for the '<em><b>Trigger Timer</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_TIMER = eINSTANCE
                .getTriggerIntermediateMultiple_TriggerTimer();

        /**
         * The meta object literal for the '<em><b>Result Error</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_INTERMEDIATE_MULTIPLE__RESULT_ERROR = eINSTANCE
                .getTriggerIntermediateMultiple_ResultError();

        /**
         * The meta object literal for the '<em><b>Trigger Result Compensation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_COMPENSATION =
                eINSTANCE
                        .getTriggerIntermediateMultiple_TriggerResultCompensation();

        /**
         * The meta object literal for the '<em><b>Deprecated Result Compensation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_INTERMEDIATE_MULTIPLE__DEPRECATED_RESULT_COMPENSATION =
                eINSTANCE
                        .getTriggerIntermediateMultiple_DeprecatedResultCompensation();

        /**
         * The meta object literal for the '<em><b>Trigger Conditional</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_CONDITIONAL =
                eINSTANCE.getTriggerIntermediateMultiple_TriggerConditional();

        /**
         * The meta object literal for the '<em><b>Trigger Result Link</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_INTERMEDIATE_MULTIPLE__TRIGGER_RESULT_LINK =
                eINSTANCE.getTriggerIntermediateMultiple_TriggerResultLink();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerMultipleImpl <em>Trigger Multiple</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerMultipleImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerMultiple()
         * @generated
         */
        EClass TRIGGER_MULTIPLE = eINSTANCE.getTriggerMultiple();

        /**
         * The meta object literal for the '<em><b>Trigger Result Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_MULTIPLE__TRIGGER_RESULT_MESSAGE = eINSTANCE
                .getTriggerMultiple_TriggerResultMessage();

        /**
         * The meta object literal for the '<em><b>Trigger Timer</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_MULTIPLE__TRIGGER_TIMER = eINSTANCE
                .getTriggerMultiple_TriggerTimer();

        /**
         * The meta object literal for the '<em><b>Trigger Conditional</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_MULTIPLE__TRIGGER_CONDITIONAL = eINSTANCE
                .getTriggerMultiple_TriggerConditional();

        /**
         * The meta object literal for the '<em><b>Trigger Result Link</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_MULTIPLE__TRIGGER_RESULT_LINK = eINSTANCE
                .getTriggerMultiple_TriggerResultLink();

        /**
         * The meta object literal for the '<em><b>Deprecated Trigger Rule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_MULTIPLE__DEPRECATED_TRIGGER_RULE = eINSTANCE
                .getTriggerMultiple_DeprecatedTriggerRule();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultCancelImpl <em>Trigger Result Cancel</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerResultCancelImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultCancel()
         * @generated
         */
        EClass TRIGGER_RESULT_CANCEL = eINSTANCE.getTriggerResultCancel();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultCompensationImpl <em>Trigger Result Compensation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerResultCompensationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultCompensation()
         * @generated
         */
        EClass TRIGGER_RESULT_COMPENSATION = eINSTANCE
                .getTriggerResultCompensation();

        /**
         * The meta object literal for the '<em><b>Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_RESULT_COMPENSATION__ACTIVITY_ID = eINSTANCE
                .getTriggerResultCompensation_ActivityId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl <em>Trigger Result Signal</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultSignal()
         * @generated
         */
        EClass TRIGGER_RESULT_SIGNAL = eINSTANCE.getTriggerResultSignal();

        /**
         * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_RESULT_SIGNAL__PROPERTIES = eINSTANCE
                .getTriggerResultSignal_Properties();

        /**
         * The meta object literal for the '<em><b>Catch Throw</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_RESULT_SIGNAL__CATCH_THROW = eINSTANCE
                .getTriggerResultSignal_CatchThrow();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_RESULT_SIGNAL__NAME = eINSTANCE
                .getTriggerResultSignal_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultLinkImpl <em>Trigger Result Link</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerResultLinkImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultLink()
         * @generated
         */
        EClass TRIGGER_RESULT_LINK = eINSTANCE.getTriggerResultLink();

        /**
         * The meta object literal for the '<em><b>Deprecated Link Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_RESULT_LINK__DEPRECATED_LINK_ID = eINSTANCE
                .getTriggerResultLink_DeprecatedLinkId();

        /**
         * The meta object literal for the '<em><b>Deprecated Process Ref</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_RESULT_LINK__DEPRECATED_PROCESS_REF = eINSTANCE
                .getTriggerResultLink_DeprecatedProcessRef();

        /**
         * The meta object literal for the '<em><b>Catch Throw</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_RESULT_LINK__CATCH_THROW = eINSTANCE
                .getTriggerResultLink_CatchThrow();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_RESULT_LINK__NAME = eINSTANCE
                .getTriggerResultLink_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl <em>Trigger Result Message</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerResultMessage()
         * @generated
         */
        EClass TRIGGER_RESULT_MESSAGE = eINSTANCE.getTriggerResultMessage();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_RESULT_MESSAGE__MESSAGE = eINSTANCE
                .getTriggerResultMessage_Message();

        /**
         * The meta object literal for the '<em><b>Web Service Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION = eINSTANCE
                .getTriggerResultMessage_WebServiceOperation();

        /**
         * The meta object literal for the '<em><b>Catch Throw</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_RESULT_MESSAGE__CATCH_THROW = eINSTANCE
                .getTriggerResultMessage_CatchThrow();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerConditionalImpl <em>Trigger Conditional</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerConditionalImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerConditional()
         * @generated
         */
        EClass TRIGGER_CONDITIONAL = eINSTANCE.getTriggerConditional();

        /**
         * The meta object literal for the '<em><b>Condition Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_CONDITIONAL__CONDITION_NAME = eINSTANCE
                .getTriggerConditional_ConditionName();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_CONDITIONAL__EXPRESSION = eINSTANCE
                .getTriggerConditional_Expression();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TriggerTimerImpl <em>Trigger Timer</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TriggerTimerImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerTimer()
         * @generated
         */
        EClass TRIGGER_TIMER = eINSTANCE.getTriggerTimer();

        /**
         * The meta object literal for the '<em><b>Deprecated Time Cycle</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_TIMER__DEPRECATED_TIME_CYCLE = eINSTANCE
                .getTriggerTimer_DeprecatedTimeCycle();

        /**
         * The meta object literal for the '<em><b>Deprecated Time Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRIGGER_TIMER__DEPRECATED_TIME_DATE = eINSTANCE
                .getTriggerTimer_DeprecatedTimeDate();

        /**
         * The meta object literal for the '<em><b>Time Cycle</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_TIMER__TIME_CYCLE = eINSTANCE
                .getTriggerTimer_TimeCycle();

        /**
         * The meta object literal for the '<em><b>Time Date</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRIGGER_TIMER__TIME_DATE = eINSTANCE
                .getTriggerTimer_TimeDate();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl <em>Type Declaration</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.TypeDeclarationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTypeDeclaration()
         * @generated
         */
        EClass TYPE_DECLARATION = eINSTANCE.getTypeDeclaration();

        /**
         * The meta object literal for the '<em><b>Basic Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__BASIC_TYPE = eINSTANCE
                .getTypeDeclaration_BasicType();

        /**
         * The meta object literal for the '<em><b>Declared Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__DECLARED_TYPE = eINSTANCE
                .getTypeDeclaration_DeclaredType();

        /**
         * The meta object literal for the '<em><b>Schema Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__SCHEMA_TYPE = eINSTANCE
                .getTypeDeclaration_SchemaType();

        /**
         * The meta object literal for the '<em><b>External Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__EXTERNAL_REFERENCE = eINSTANCE
                .getTypeDeclaration_ExternalReference();

        /**
         * The meta object literal for the '<em><b>Record Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__RECORD_TYPE = eINSTANCE
                .getTypeDeclaration_RecordType();

        /**
         * The meta object literal for the '<em><b>Union Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__UNION_TYPE = eINSTANCE
                .getTypeDeclaration_UnionType();

        /**
         * The meta object literal for the '<em><b>Enumeration Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__ENUMERATION_TYPE = eINSTANCE
                .getTypeDeclaration_EnumerationType();

        /**
         * The meta object literal for the '<em><b>Array Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__ARRAY_TYPE = eINSTANCE
                .getTypeDeclaration_ArrayType();

        /**
         * The meta object literal for the '<em><b>List Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPE_DECLARATION__LIST_TYPE = eINSTANCE
                .getTypeDeclaration_ListType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.UnionTypeImpl <em>Union Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.UnionTypeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getUnionType()
         * @generated
         */
        EClass UNION_TYPE = eINSTANCE.getUnionType();

        /**
         * The meta object literal for the '<em><b>Member</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference UNION_TYPE__MEMBER = eINSTANCE.getUnionType_Member();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ValidFromImpl <em>Valid From</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ValidFromImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getValidFrom()
         * @generated
         */
        EClass VALID_FROM = eINSTANCE.getValidFrom();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALID_FROM__VALUE = eINSTANCE.getValidFrom_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.ValidToImpl <em>Valid To</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.ValidToImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getValidTo()
         * @generated
         */
        EClass VALID_TO = eINSTANCE.getValidTo();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALID_TO__VALUE = eINSTANCE.getValidTo_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.VendorExtensionsImpl <em>Vendor Extensions</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.VendorExtensionsImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getVendorExtensions()
         * @generated
         */
        EClass VENDOR_EXTENSIONS = eINSTANCE.getVendorExtensions();

        /**
         * The meta object literal for the '<em><b>Vendor Extension</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VENDOR_EXTENSIONS__VENDOR_EXTENSION = eINSTANCE
                .getVendorExtensions_VendorExtension();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.VendorExtensionImpl <em>Vendor Extension</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.VendorExtensionImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getVendorExtension()
         * @generated
         */
        EClass VENDOR_EXTENSION = eINSTANCE.getVendorExtension();

        /**
         * The meta object literal for the '<em><b>Extension Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VENDOR_EXTENSION__EXTENSION_DESCRIPTION = eINSTANCE
                .getVendorExtension_ExtensionDescription();

        /**
         * The meta object literal for the '<em><b>Schema Location</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VENDOR_EXTENSION__SCHEMA_LOCATION = eINSTANCE
                .getVendorExtension_SchemaLocation();

        /**
         * The meta object literal for the '<em><b>Tool Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VENDOR_EXTENSION__TOOL_ID = eINSTANCE
                .getVendorExtension_ToolId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.WaitingTimeImpl <em>Waiting Time</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.WaitingTimeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWaitingTime()
         * @generated
         */
        EClass WAITING_TIME = eINSTANCE.getWaitingTime();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WAITING_TIME__VALUE = eINSTANCE.getWaitingTime_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.WebServiceFaultCatchImpl <em>Web Service Fault Catch</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.WebServiceFaultCatchImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWebServiceFaultCatch()
         * @generated
         */
        EClass WEB_SERVICE_FAULT_CATCH = eINSTANCE.getWebServiceFaultCatch();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WEB_SERVICE_FAULT_CATCH__MESSAGE = eINSTANCE
                .getWebServiceFaultCatch_Message();

        /**
         * The meta object literal for the '<em><b>Block Activity</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WEB_SERVICE_FAULT_CATCH__BLOCK_ACTIVITY = eINSTANCE
                .getWebServiceFaultCatch_BlockActivity();

        /**
         * The meta object literal for the '<em><b>Transition Ref</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WEB_SERVICE_FAULT_CATCH__TRANSITION_REF = eINSTANCE
                .getWebServiceFaultCatch_TransitionRef();

        /**
         * The meta object literal for the '<em><b>Fault Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WEB_SERVICE_FAULT_CATCH__FAULT_NAME = eINSTANCE
                .getWebServiceFaultCatch_FaultName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.WebServiceOperationImpl <em>Web Service Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.WebServiceOperationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWebServiceOperation()
         * @generated
         */
        EClass WEB_SERVICE_OPERATION = eINSTANCE.getWebServiceOperation();

        /**
         * The meta object literal for the '<em><b>Partner</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WEB_SERVICE_OPERATION__PARTNER = eINSTANCE
                .getWebServiceOperation_Partner();

        /**
         * The meta object literal for the '<em><b>Service</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WEB_SERVICE_OPERATION__SERVICE = eINSTANCE
                .getWebServiceOperation_Service();

        /**
         * The meta object literal for the '<em><b>Operation Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WEB_SERVICE_OPERATION__OPERATION_NAME = eINSTANCE
                .getWebServiceOperation_OperationName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.WebServiceApplicationImpl <em>Web Service Application</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.WebServiceApplicationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWebServiceApplication()
         * @generated
         */
        EClass WEB_SERVICE_APPLICATION = eINSTANCE.getWebServiceApplication();

        /**
         * The meta object literal for the '<em><b>Web Service Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION = eINSTANCE
                .getWebServiceApplication_WebServiceOperation();

        /**
         * The meta object literal for the '<em><b>Web Service Fault Catch</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WEB_SERVICE_APPLICATION__WEB_SERVICE_FAULT_CATCH = eINSTANCE
                .getWebServiceApplication_WebServiceFaultCatch();

        /**
         * The meta object literal for the '<em><b>Input Msg Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WEB_SERVICE_APPLICATION__INPUT_MSG_NAME = eINSTANCE
                .getWebServiceApplication_InputMsgName();

        /**
         * The meta object literal for the '<em><b>Output Msg Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WEB_SERVICE_APPLICATION__OUTPUT_MSG_NAME = eINSTANCE
                .getWebServiceApplication_OutputMsgName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.WorkingTimeImpl <em>Working Time</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.WorkingTimeImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getWorkingTime()
         * @generated
         */
        EClass WORKING_TIME = eINSTANCE.getWorkingTime();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORKING_TIME__VALUE = eINSTANCE.getWorkingTime_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.XsltApplicationImpl <em>Xslt Application</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.XsltApplicationImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getXsltApplication()
         * @generated
         */
        EClass XSLT_APPLICATION = eINSTANCE.getXsltApplication();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute XSLT_APPLICATION__LOCATION = eINSTANCE
                .getXsltApplication_Location();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.NamedElementImpl <em>Named Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.NamedElementImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getNamedElement()
         * @generated
         */
        EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.FlowContainer <em>Flow Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.FlowContainer
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFlowContainer()
         * @generated
         */
        EClass FLOW_CONTAINER = eINSTANCE.getFlowContainer();

        /**
         * The meta object literal for the '<em><b>Activities</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FLOW_CONTAINER__ACTIVITIES = eINSTANCE
                .getFlowContainer_Activities();

        /**
         * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FLOW_CONTAINER__TRANSITIONS = eINSTANCE
                .getFlowContainer_Transitions();

        /**
         * The meta object literal for the '<em><b>Ad Hoc</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FLOW_CONTAINER__AD_HOC = eINSTANCE.getFlowContainer_AdHoc();

        /**
         * The meta object literal for the '<em><b>Ad Hoc Completion Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FLOW_CONTAINER__AD_HOC_COMPLETION_CONDITION = eINSTANCE
                .getFlowContainer_AdHocCompletionCondition();

        /**
         * The meta object literal for the '<em><b>Ad Hoc Ordering</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FLOW_CONTAINER__AD_HOC_ORDERING = eINSTANCE
                .getFlowContainer_AdHocOrdering();

        /**
         * The meta object literal for the '<em><b>Default Start Activity Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FLOW_CONTAINER__DEFAULT_START_ACTIVITY_ID = eINSTANCE
                .getFlowContainer_DefaultStartActivityId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.DescribedElement <em>Described Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.DescribedElement
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDescribedElement()
         * @generated
         */
        EClass DESCRIBED_ELEMENT = eINSTANCE.getDescribedElement();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DESCRIBED_ELEMENT__DESCRIPTION = eINSTANCE
                .getDescribedElement_Description();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData <em>Process Relevant Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ProcessRelevantData
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcessRelevantData()
         * @generated
         */
        EClass PROCESS_RELEVANT_DATA = eINSTANCE.getProcessRelevantData();

        /**
         * The meta object literal for the '<em><b>Data Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_RELEVANT_DATA__DATA_TYPE = eINSTANCE
                .getProcessRelevantData_DataType();

        /**
         * The meta object literal for the '<em><b>Length</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROCESS_RELEVANT_DATA__LENGTH = eINSTANCE
                .getProcessRelevantData_Length();

        /**
         * The meta object literal for the '<em><b>Is Array</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS_RELEVANT_DATA__IS_ARRAY = eINSTANCE
                .getProcessRelevantData_IsArray();

        /**
         * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROCESS_RELEVANT_DATA__READ_ONLY = eINSTANCE
                .getProcessRelevantData_ReadOnly();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PropertyInputImpl <em>Property Input</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PropertyInputImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPropertyInput()
         * @generated
         */
        EClass PROPERTY_INPUT = eINSTANCE.getPropertyInput();

        /**
         * The meta object literal for the '<em><b>Property Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROPERTY_INPUT__PROPERTY_ID = eINSTANCE
                .getPropertyInput_PropertyId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.UniqueIdElementImpl <em>Unique Id Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.UniqueIdElementImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getUniqueIdElement()
         * @generated
         */
        EClass UNIQUE_ID_ELEMENT = eINSTANCE.getUniqueIdElement();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNIQUE_ID_ELEMENT__ID = eINSTANCE.getUniqueIdElement_Id();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.OtherAttributesContainer <em>Other Attributes Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.OtherAttributesContainer
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOtherAttributesContainer()
         * @generated
         */
        EClass OTHER_ATTRIBUTES_CONTAINER = eINSTANCE
                .getOtherAttributesContainer();

        /**
         * The meta object literal for the '<em><b>Other Attributes</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES = eINSTANCE
                .getOtherAttributesContainer_OtherAttributes();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.OtherElementsContainer <em>Other Elements Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.OtherElementsContainer
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOtherElementsContainer()
         * @generated
         */
        EClass OTHER_ELEMENTS_CONTAINER = eINSTANCE.getOtherElementsContainer();

        /**
         * The meta object literal for the '<em><b>Other Elements</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS = eINSTANCE
                .getOtherElementsContainer_OtherElements();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.impl.PerformersImpl <em>Performers</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.impl.PerformersImpl
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPerformers()
         * @generated
         */
        EClass PERFORMERS = eINSTANCE.getPerformers();

        /**
         * The meta object literal for the '<em><b>Performers</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PERFORMERS__PERFORMERS = eINSTANCE
                .getPerformers_Performers();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.AccessLevelType <em>Access Level Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AccessLevelType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAccessLevelType()
         * @generated
         */
        EEnum ACCESS_LEVEL_TYPE = eINSTANCE.getAccessLevelType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.AdHocOrderingType <em>Ad Hoc Ordering Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AdHocOrderingType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAdHocOrderingType()
         * @generated
         */
        EEnum AD_HOC_ORDERING_TYPE = eINSTANCE.getAdHocOrderingType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ArtifactType <em>Artifact Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ArtifactType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArtifactType()
         * @generated
         */
        EEnum ARTIFACT_TYPE = eINSTANCE.getArtifactType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.AssignTimeType <em>Assign Time Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AssignTimeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssignTimeType()
         * @generated
         */
        EEnum ASSIGN_TIME_TYPE = eINSTANCE.getAssignTimeType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.AssociationDirectionType <em>Association Direction Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AssociationDirectionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssociationDirectionType()
         * @generated
         */
        EEnum ASSOCIATION_DIRECTION_TYPE = eINSTANCE
                .getAssociationDirectionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.DirectionType <em>Direction Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.DirectionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDirectionType()
         * @generated
         */
        EEnum DIRECTION_TYPE = eINSTANCE.getDirectionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.DurationUnitType <em>Duration Unit Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.DurationUnitType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDurationUnitType()
         * @generated
         */
        EEnum DURATION_UNIT_TYPE = eINSTANCE.getDurationUnitType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.EndPointTypeType <em>End Point Type Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.EndPointTypeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEndPointTypeType()
         * @generated
         */
        EEnum END_POINT_TYPE_TYPE = eINSTANCE.getEndPointTypeType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ExclusiveType <em>Exclusive Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ExclusiveType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExclusiveType()
         * @generated
         */
        EEnum EXCLUSIVE_TYPE = eINSTANCE.getExclusiveType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ExecutionType <em>Execution Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ExecutionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExecutionType()
         * @generated
         */
        EEnum EXECUTION_TYPE = eINSTANCE.getExecutionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.FinishModeType <em>Finish Mode Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.FinishModeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFinishModeType()
         * @generated
         */
        EEnum FINISH_MODE_TYPE = eINSTANCE.getFinishModeType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.JoinSplitType <em>Join Split Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.JoinSplitType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getJoinSplitType()
         * @generated
         */
        EEnum JOIN_SPLIT_TYPE = eINSTANCE.getJoinSplitType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.GraphConformanceType <em>Graph Conformance Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.GraphConformanceType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGraphConformanceType()
         * @generated
         */
        EEnum GRAPH_CONFORMANCE_TYPE = eINSTANCE.getGraphConformanceType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ImplementationType <em>Implementation Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ImplementationType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getImplementationType()
         * @generated
         */
        EEnum IMPLEMENTATION_TYPE = eINSTANCE.getImplementationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.InstantiationType <em>Instantiation Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.InstantiationType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getInstantiationType()
         * @generated
         */
        EEnum INSTANTIATION_TYPE = eINSTANCE.getInstantiationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.IsArrayType <em>Is Array Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.IsArrayType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIsArrayType()
         * @generated
         */
        EEnum IS_ARRAY_TYPE = eINSTANCE.getIsArrayType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.LoopType <em>Loop Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.LoopType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoopType()
         * @generated
         */
        EEnum LOOP_TYPE = eINSTANCE.getLoopType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.MIFlowConditionType <em>MI Flow Condition Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.MIFlowConditionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMIFlowConditionType()
         * @generated
         */
        EEnum MI_FLOW_CONDITION_TYPE = eINSTANCE.getMIFlowConditionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.MIOrderingType <em>MI Ordering Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.MIOrderingType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMIOrderingType()
         * @generated
         */
        EEnum MI_ORDERING_TYPE = eINSTANCE.getMIOrderingType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ModeType <em>Mode Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ModeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getModeType()
         * @generated
         */
        EEnum MODE_TYPE = eINSTANCE.getModeType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.OrientationType <em>Orientation Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.OrientationType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOrientationType()
         * @generated
         */
        EEnum ORIENTATION_TYPE = eINSTANCE.getOrientationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ProcessType <em>Process Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ProcessType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcessType()
         * @generated
         */
        EEnum PROCESS_TYPE = eINSTANCE.getProcessType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.PublicationStatusType <em>Publication Status Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.PublicationStatusType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPublicationStatusType()
         * @generated
         */
        EEnum PUBLICATION_STATUS_TYPE = eINSTANCE.getPublicationStatusType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ResultType <em>Result Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ResultType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResultType()
         * @generated
         */
        EEnum RESULT_TYPE = eINSTANCE.getResultType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.RoleType <em>Role Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.RoleType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRoleType()
         * @generated
         */
        EEnum ROLE_TYPE = eINSTANCE.getRoleType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.SHAPEType <em>SHAPE Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.SHAPEType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSHAPEType()
         * @generated
         */
        EEnum SHAPE_TYPE = eINSTANCE.getSHAPEType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.StartModeType <em>Start Mode Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.StartModeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStartModeType()
         * @generated
         */
        EEnum START_MODE_TYPE = eINSTANCE.getStartModeType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.StatusType <em>Status Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.StatusType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStatusType()
         * @generated
         */
        EEnum STATUS_TYPE = eINSTANCE.getStatusType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.TestTimeType <em>Test Time Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.TestTimeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTestTimeType()
         * @generated
         */
        EEnum TEST_TIME_TYPE = eINSTANCE.getTestTimeType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.TransactionMethodType <em>Transaction Method Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.TransactionMethodType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransactionMethodType()
         * @generated
         */
        EEnum TRANSACTION_METHOD_TYPE = eINSTANCE.getTransactionMethodType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.TriggerType <em>Trigger Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.TriggerType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerType()
         * @generated
         */
        EEnum TRIGGER_TYPE = eINSTANCE.getTriggerType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ViewType <em>View Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ViewType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getViewType()
         * @generated
         */
        EEnum VIEW_TYPE = eINSTANCE.getViewType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.DeprecatedXorType <em>Deprecated Xor Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.DeprecatedXorType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDeprecatedXorType()
         * @generated
         */
        EEnum DEPRECATED_XOR_TYPE = eINSTANCE.getDeprecatedXorType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ConditionType <em>Condition Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ConditionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getConditionType()
         * @generated
         */
        EEnum CONDITION_TYPE = eINSTANCE.getConditionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.ParticipantType <em>Participant Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ParticipantType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getParticipantType()
         * @generated
         */
        EEnum PARTICIPANT_TYPE = eINSTANCE.getParticipantType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.BasicTypeType <em>Basic Type Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.BasicTypeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBasicTypeType()
         * @generated
         */
        EEnum BASIC_TYPE_TYPE = eINSTANCE.getBasicTypeType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance <em>BPMN Model Portability Conformance</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getBPMNModelPortabilityConformance()
         * @generated
         */
        EEnum BPMN_MODEL_PORTABILITY_CONFORMANCE = eINSTANCE
                .getBPMNModelPortabilityConformance();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.CatchThrow <em>Catch Throw</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.CatchThrow
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getCatchThrow()
         * @generated
         */
        EEnum CATCH_THROW = eINSTANCE.getCatchThrow();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.xpdl2.GatewayType <em>Gateway Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.GatewayType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGatewayType()
         * @generated
         */
        EEnum GATEWAY_TYPE = eINSTANCE.getGatewayType();

        /**
         * The meta object literal for the '<em>Access Level Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AccessLevelType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAccessLevelTypeObject()
         * @generated
         */
        EDataType ACCESS_LEVEL_TYPE_OBJECT = eINSTANCE
                .getAccessLevelTypeObject();

        /**
         * The meta object literal for the '<em>Ad Hoc Ordering Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AdHocOrderingType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAdHocOrderingTypeObject()
         * @generated
         */
        EDataType AD_HOC_ORDERING_TYPE_OBJECT = eINSTANCE
                .getAdHocOrderingTypeObject();

        /**
         * The meta object literal for the '<em>Artifact Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ArtifactType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getArtifactTypeObject()
         * @generated
         */
        EDataType ARTIFACT_TYPE_OBJECT = eINSTANCE.getArtifactTypeObject();

        /**
         * The meta object literal for the '<em>Assign Time Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AssignTimeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssignTimeTypeObject()
         * @generated
         */
        EDataType ASSIGN_TIME_TYPE_OBJECT = eINSTANCE.getAssignTimeTypeObject();

        /**
         * The meta object literal for the '<em>Association Direction Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.AssociationDirectionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getAssociationDirectionTypeObject()
         * @generated
         */
        EDataType ASSOCIATION_DIRECTION_TYPE_OBJECT = eINSTANCE
                .getAssociationDirectionTypeObject();

        /**
         * The meta object literal for the '<em>Direction Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.DirectionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDirectionTypeObject()
         * @generated
         */
        EDataType DIRECTION_TYPE_OBJECT = eINSTANCE.getDirectionTypeObject();

        /**
         * The meta object literal for the '<em>Duration Unit Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.DurationUnitType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getDurationUnitTypeObject()
         * @generated
         */
        EDataType DURATION_UNIT_TYPE_OBJECT = eINSTANCE
                .getDurationUnitTypeObject();

        /**
         * The meta object literal for the '<em>End Point Type Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.EndPointTypeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getEndPointTypeTypeObject()
         * @generated
         */
        EDataType END_POINT_TYPE_TYPE_OBJECT = eINSTANCE
                .getEndPointTypeTypeObject();

        /**
         * The meta object literal for the '<em>Execution Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ExecutionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getExecutionTypeObject()
         * @generated
         */
        EDataType EXECUTION_TYPE_OBJECT = eINSTANCE.getExecutionTypeObject();

        /**
         * The meta object literal for the '<em>Finish Mode Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.FinishModeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getFinishModeTypeObject()
         * @generated
         */
        EDataType FINISH_MODE_TYPE_OBJECT = eINSTANCE.getFinishModeTypeObject();

        /**
         * The meta object literal for the '<em>Gateway Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.JoinSplitType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGatewayTypeObject()
         * @generated
         */
        EDataType GATEWAY_TYPE_OBJECT = eINSTANCE.getGatewayTypeObject();

        /**
         * The meta object literal for the '<em>Graph Conformance Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.GraphConformanceType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getGraphConformanceTypeObject()
         * @generated
         */
        EDataType GRAPH_CONFORMANCE_TYPE_OBJECT = eINSTANCE
                .getGraphConformanceTypeObject();

        /**
         * The meta object literal for the '<em>Implementation Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.emf.common.util.Enumerator
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getImplementationTypeObject()
         * @generated
         */
        EDataType IMPLEMENTATION_TYPE_OBJECT = eINSTANCE
                .getImplementationTypeObject();

        /**
         * The meta object literal for the '<em>Instantiation Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.InstantiationType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getInstantiationTypeObject()
         * @generated
         */
        EDataType INSTANTIATION_TYPE_OBJECT = eINSTANCE
                .getInstantiationTypeObject();

        /**
         * The meta object literal for the '<em>Is Array Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.IsArrayType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIsArrayTypeObject()
         * @generated
         */
        EDataType IS_ARRAY_TYPE_OBJECT = eINSTANCE.getIsArrayTypeObject();

        /**
         * The meta object literal for the '<em>Loop Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.LoopType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getLoopTypeObject()
         * @generated
         */
        EDataType LOOP_TYPE_OBJECT = eINSTANCE.getLoopTypeObject();

        /**
         * The meta object literal for the '<em>MI Flow Condition Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.MIFlowConditionType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMIFlowConditionTypeObject()
         * @generated
         */
        EDataType MI_FLOW_CONDITION_TYPE_OBJECT = eINSTANCE
                .getMIFlowConditionTypeObject();

        /**
         * The meta object literal for the '<em>MI Ordering Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.MIOrderingType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getMIOrderingTypeObject()
         * @generated
         */
        EDataType MI_ORDERING_TYPE_OBJECT = eINSTANCE.getMIOrderingTypeObject();

        /**
         * The meta object literal for the '<em>Mode Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ModeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getModeTypeObject()
         * @generated
         */
        EDataType MODE_TYPE_OBJECT = eINSTANCE.getModeTypeObject();

        /**
         * The meta object literal for the '<em>Orientation Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.OrientationType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getOrientationTypeObject()
         * @generated
         */
        EDataType ORIENTATION_TYPE_OBJECT = eINSTANCE
                .getOrientationTypeObject();

        /**
         * The meta object literal for the '<em>Process Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ProcessType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getProcessTypeObject()
         * @generated
         */
        EDataType PROCESS_TYPE_OBJECT = eINSTANCE.getProcessTypeObject();

        /**
         * The meta object literal for the '<em>Publication Status Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.PublicationStatusType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getPublicationStatusTypeObject()
         * @generated
         */
        EDataType PUBLICATION_STATUS_TYPE_OBJECT = eINSTANCE
                .getPublicationStatusTypeObject();

        /**
         * The meta object literal for the '<em>Result Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.ResultType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getResultTypeObject()
         * @generated
         */
        EDataType RESULT_TYPE_OBJECT = eINSTANCE.getResultTypeObject();

        /**
         * The meta object literal for the '<em>Role Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.RoleType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getRoleTypeObject()
         * @generated
         */
        EDataType ROLE_TYPE_OBJECT = eINSTANCE.getRoleTypeObject();

        /**
         * The meta object literal for the '<em>SHAPE Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.SHAPEType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getSHAPETypeObject()
         * @generated
         */
        EDataType SHAPE_TYPE_OBJECT = eINSTANCE.getSHAPETypeObject();

        /**
         * The meta object literal for the '<em>Start Mode Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.StartModeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStartModeTypeObject()
         * @generated
         */
        EDataType START_MODE_TYPE_OBJECT = eINSTANCE.getStartModeTypeObject();

        /**
         * The meta object literal for the '<em>Status Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.StatusType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getStatusTypeObject()
         * @generated
         */
        EDataType STATUS_TYPE_OBJECT = eINSTANCE.getStatusTypeObject();

        /**
         * The meta object literal for the '<em>Test Time Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.TestTimeType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTestTimeTypeObject()
         * @generated
         */
        EDataType TEST_TIME_TYPE_OBJECT = eINSTANCE.getTestTimeTypeObject();

        /**
         * The meta object literal for the '<em>Transaction Method Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.TransactionMethodType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTransactionMethodTypeObject()
         * @generated
         */
        EDataType TRANSACTION_METHOD_TYPE_OBJECT = eINSTANCE
                .getTransactionMethodTypeObject();

        /**
         * The meta object literal for the '<em>Trigger Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.xpdl2.TriggerType
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getTriggerTypeObject()
         * @generated
         */
        EDataType TRIGGER_TYPE_OBJECT = eINSTANCE.getTriggerTypeObject();

        /**
         * The meta object literal for the '<em>Id Reference String</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.xpd.xpdl2.impl.Xpdl2PackageImpl#getIdReferenceString()
         * @generated
         */
        EDataType ID_REFERENCE_STRING = eINSTANCE.getIdReferenceString();

    }

} //Xpdl2Package
