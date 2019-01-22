/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.datamodel.DatamodelPackage;

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
 * @see com.tibco.n2.brm.api.N2BRMFactory
 * @model kind="package"
 * @generated
 */
public interface N2BRMPackage extends EPackage {
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
    String eNS_URI = "http://api.brm.n2.tibco.com";

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
    N2BRMPackage eINSTANCE = com.tibco.n2.brm.api.impl.N2BRMPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AddCurrentResourceToViewResponseTypeImpl <em>Add Current Resource To View Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AddCurrentResourceToViewResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAddCurrentResourceToViewResponseType()
     * @generated
     */
    int ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE = 0;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Add Current Resource To View Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AddCurrentResourceToViewTypeImpl <em>Add Current Resource To View Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AddCurrentResourceToViewTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAddCurrentResourceToViewType()
     * @generated
     */
    int ADD_CURRENT_RESOURCE_TO_VIEW_TYPE = 1;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADD_CURRENT_RESOURCE_TO_VIEW_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Add Current Resource To View Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADD_CURRENT_RESOURCE_TO_VIEW_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemResponseTypeImpl <em>Allocate And Open Next Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateAndOpenNextWorkItemResponseType()
     * @generated
     */
    int ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE = 2;

    /**
     * The feature id for the '<em><b>Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = 0;

    /**
     * The number of structural features of the '<em>Allocate And Open Next Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemTypeImpl <em>Allocate And Open Next Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateAndOpenNextWorkItemType()
     * @generated
     */
    int ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE = 3;

    /**
     * The feature id for the '<em><b>Resource</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE__RESOURCE = 0;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE__WORK_LIST_VIEW_ID = 1;

    /**
     * The number of structural features of the '<em>Allocate And Open Next Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AllocateAndOpenWorkItemResponseTypeImpl <em>Allocate And Open Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AllocateAndOpenWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateAndOpenWorkItemResponseType()
     * @generated
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE = 4;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = 1;

    /**
     * The number of structural features of the '<em>Allocate And Open Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AllocateAndOpenWorkItemTypeImpl <em>Allocate And Open Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AllocateAndOpenWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateAndOpenWorkItemType()
     * @generated
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_TYPE = 5;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID = 1;

    /**
     * The feature id for the '<em><b>Resource</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__RESOURCE = 2;

    /**
     * The number of structural features of the '<em>Allocate And Open Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_AND_OPEN_WORK_ITEM_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AllocateWorkItemResponseTypeImpl <em>Allocate Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AllocateWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateWorkItemResponseType()
     * @generated
     */
    int ALLOCATE_WORK_ITEM_RESPONSE_TYPE = 6;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = 1;

    /**
     * The number of structural features of the '<em>Allocate Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AllocateWorkItemTypeImpl <em>Allocate Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AllocateWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateWorkItemType()
     * @generated
     */
    int ALLOCATE_WORK_ITEM_TYPE = 7;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_WORK_ITEM_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID = 1;

    /**
     * The feature id for the '<em><b>Resource</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_WORK_ITEM_TYPE__RESOURCE = 2;

    /**
     * The number of structural features of the '<em>Allocate Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATE_WORK_ITEM_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AllocationHistoryImpl <em>Allocation History</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AllocationHistoryImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocationHistory()
     * @generated
     */
    int ALLOCATION_HISTORY = 8;

    /**
     * The feature id for the '<em><b>Resource ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_HISTORY__RESOURCE_ID = 0;

    /**
     * The feature id for the '<em><b>Allocation Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_HISTORY__ALLOCATION_DATE = 1;

    /**
     * The feature id for the '<em><b>Allocation ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_HISTORY__ALLOCATION_ID = 2;

    /**
     * The number of structural features of the '<em>Allocation History</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCATION_HISTORY_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncCancelWorkItemResponseTypeImpl <em>Async Cancel Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncCancelWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncCancelWorkItemResponseType()
     * @generated
     */
    int ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE = 9;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The feature id for the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Async Cancel Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncCancelWorkItemTypeImpl <em>Async Cancel Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncCancelWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncCancelWorkItemType()
     * @generated
     */
    int ASYNC_CANCEL_WORK_ITEM_TYPE = 10;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_CANCEL_WORK_ITEM_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The number of structural features of the '<em>Async Cancel Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_CANCEL_WORK_ITEM_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncEndGroupResponseTypeImpl <em>Async End Group Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncEndGroupResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncEndGroupResponseType()
     * @generated
     */
    int ASYNC_END_GROUP_RESPONSE_TYPE = 11;

    /**
     * The feature id for the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_END_GROUP_RESPONSE_TYPE__ACTIVITY_ID = 0;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_END_GROUP_RESPONSE_TYPE__GROUP_ID = 1;

    /**
     * The feature id for the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_END_GROUP_RESPONSE_TYPE__ERROR_MESSAGE = 2;

    /**
     * The number of structural features of the '<em>Async End Group Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_END_GROUP_RESPONSE_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncEndGroupTypeImpl <em>Async End Group Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncEndGroupTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncEndGroupType()
     * @generated
     */
    int ASYNC_END_GROUP_TYPE = 12;

    /**
     * The feature id for the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_END_GROUP_TYPE__ACTIVITY_ID = 0;

    /**
     * The feature id for the '<em><b>End Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_END_GROUP_TYPE__END_GROUP = 1;

    /**
     * The number of structural features of the '<em>Async End Group Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_END_GROUP_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncRescheduleWorkItemResponseTypeImpl <em>Async Reschedule Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncRescheduleWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncRescheduleWorkItemResponseType()
     * @generated
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE = 13;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The feature id for the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Async Reschedule Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncRescheduleWorkItemTypeImpl <em>Async Reschedule Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncRescheduleWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncRescheduleWorkItemType()
     * @generated
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_TYPE = 14;

    /**
     * The feature id for the '<em><b>Item Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE = 0;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS = 1;

    /**
     * The feature id for the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_TYPE__ITEM_BODY = 2;

    /**
     * The number of structural features of the '<em>Async Reschedule Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESCHEDULE_WORK_ITEM_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncResumeWorkItemResponseTypeImpl <em>Async Resume Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncResumeWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncResumeWorkItemResponseType()
     * @generated
     */
    int ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE = 15;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The feature id for the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Async Resume Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncResumeWorkItemTypeImpl <em>Async Resume Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncResumeWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncResumeWorkItemType()
     * @generated
     */
    int ASYNC_RESUME_WORK_ITEM_TYPE = 16;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The number of structural features of the '<em>Async Resume Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_RESUME_WORK_ITEM_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemResponseTypeImpl <em>Async Schedule Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncScheduleWorkItemResponseType()
     * @generated
     */
    int ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE = 17;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The feature id for the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Async Schedule Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemTypeImpl <em>Async Schedule Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncScheduleWorkItemType()
     * @generated
     */
    int ASYNC_SCHEDULE_WORK_ITEM_TYPE = 18;

    /**
     * The feature id for the '<em><b>Schedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM = 0;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS = 1;

    /**
     * The number of structural features of the '<em>Async Schedule Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelResponseTypeImpl <em>Async Schedule Work Item With Model Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncScheduleWorkItemWithModelResponseType()
     * @generated
     */
    int ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE = 19;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The feature id for the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__ERROR_MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Async Schedule Work Item With Model Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelTypeImpl <em>Async Schedule Work Item With Model Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncScheduleWorkItemWithModelType()
     * @generated
     */
    int ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE = 20;

    /**
     * The feature id for the '<em><b>Schedule Work Item With Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL = 0;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS = 1;

    /**
     * The number of structural features of the '<em>Async Schedule Work Item With Model Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncStartGroupResponseTypeImpl <em>Async Start Group Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncStartGroupResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncStartGroupResponseType()
     * @generated
     */
    int ASYNC_START_GROUP_RESPONSE_TYPE = 21;

    /**
     * The feature id for the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_START_GROUP_RESPONSE_TYPE__ACTIVITY_ID = 0;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_START_GROUP_RESPONSE_TYPE__GROUP_ID = 1;

    /**
     * The feature id for the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_START_GROUP_RESPONSE_TYPE__ERROR_MESSAGE = 2;

    /**
     * The number of structural features of the '<em>Async Start Group Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_START_GROUP_RESPONSE_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncStartGroupTypeImpl <em>Async Start Group Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncStartGroupTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncStartGroupType()
     * @generated
     */
    int ASYNC_START_GROUP_TYPE = 22;

    /**
     * The feature id for the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_START_GROUP_TYPE__ACTIVITY_ID = 0;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_START_GROUP_TYPE__GROUP_ID = 1;

    /**
     * The feature id for the '<em><b>Start Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_START_GROUP_TYPE__START_GROUP = 2;

    /**
     * The number of structural features of the '<em>Async Start Group Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_START_GROUP_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncSuspendWorkItemResponseTypeImpl <em>Async Suspend Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncSuspendWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncSuspendWorkItemResponseType()
     * @generated
     */
    int ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE = 23;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The feature id for the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Async Suspend Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncSuspendWorkItemTypeImpl <em>Async Suspend Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncSuspendWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncSuspendWorkItemType()
     * @generated
     */
    int ASYNC_SUSPEND_WORK_ITEM_TYPE = 24;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SUSPEND_WORK_ITEM_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The feature id for the '<em><b>Force Suspend</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND = 1;

    /**
     * The number of structural features of the '<em>Async Suspend Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_SUSPEND_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AsyncWorkItemDetailsImpl <em>Async Work Item Details</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AsyncWorkItemDetailsImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncWorkItemDetails()
     * @generated
     */
    int ASYNC_WORK_ITEM_DETAILS = 25;

    /**
     * The feature id for the '<em><b>Work Item Id</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_WORK_ITEM_DETAILS__ACTIVITY_ID = 1;

    /**
     * The feature id for the '<em><b>Ua Sequence Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID = 2;

    /**
     * The feature id for the '<em><b>Brm Sequence Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID = 3;

    /**
     * The number of structural features of the '<em>Async Work Item Details</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASYNC_WORK_ITEM_DETAILS_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.AttributeAliasListTypeImpl <em>Attribute Alias List Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.AttributeAliasListTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttributeAliasListType()
     * @generated
     */
    int ATTRIBUTE_ALIAS_LIST_TYPE = 26;

    /**
     * The feature id for the '<em><b>Attribute Alias</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS = 0;

    /**
     * The number of structural features of the '<em>Attribute Alias List Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_ALIAS_LIST_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.BaseItemInfoImpl <em>Base Item Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.BaseItemInfoImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getBaseItemInfo()
     * @generated
     */
    int BASE_ITEM_INFO = 27;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ITEM_INFO__NAME = 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ITEM_INFO__DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Distribution Strategy</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ITEM_INFO__DISTRIBUTION_STRATEGY = 2;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ITEM_INFO__GROUP_ID = 3;

    /**
     * The feature id for the '<em><b>Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ITEM_INFO__PRIORITY = 4;

    /**
     * The number of structural features of the '<em>Base Item Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ITEM_INFO_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.BaseModelInfoImpl <em>Base Model Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.BaseModelInfoImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getBaseModelInfo()
     * @generated
     */
    int BASE_MODEL_INFO = 28;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_MODEL_INFO__DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_MODEL_INFO__NAME = 1;

    /**
     * The feature id for the '<em><b>Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_MODEL_INFO__PRIORITY = 2;

    /**
     * The number of structural features of the '<em>Base Model Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_MODEL_INFO_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.CancelWorkItemResponseTypeImpl <em>Cancel Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.CancelWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCancelWorkItemResponseType()
     * @generated
     */
    int CANCEL_WORK_ITEM_RESPONSE_TYPE = 29;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CANCEL_WORK_ITEM_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Cancel Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CANCEL_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.CancelWorkItemTypeImpl <em>Cancel Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.CancelWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCancelWorkItemType()
     * @generated
     */
    int CANCEL_WORK_ITEM_TYPE = 30;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CANCEL_WORK_ITEM_TYPE__WORK_ITEM_ID = 0;

    /**
     * The number of structural features of the '<em>Cancel Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CANCEL_WORK_ITEM_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ChainedWorkItemNotificationTypeImpl <em>Chained Work Item Notification Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ChainedWorkItemNotificationTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getChainedWorkItemNotificationType()
     * @generated
     */
    int CHAINED_WORK_ITEM_NOTIFICATION_TYPE = 31;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHAINED_WORK_ITEM_NOTIFICATION_TYPE__GROUP_ID = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHAINED_WORK_ITEM_NOTIFICATION_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Chained Work Item Notification Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHAINED_WORK_ITEM_NOTIFICATION_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.CloseWorkItemResponseTypeImpl <em>Close Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.CloseWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCloseWorkItemResponseType()
     * @generated
     */
    int CLOSE_WORK_ITEM_RESPONSE_TYPE = 32;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLOSE_WORK_ITEM_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLOSE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Close Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLOSE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.CloseWorkItemTypeImpl <em>Close Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.CloseWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCloseWorkItemType()
     * @generated
     */
    int CLOSE_WORK_ITEM_TYPE = 33;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLOSE_WORK_ITEM_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLOSE_WORK_ITEM_TYPE__WORK_ITEM_ID = 1;

    /**
     * The feature id for the '<em><b>Work Item Payload</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLOSE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD = 2;

    /**
     * The feature id for the '<em><b>Hidden Period</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLOSE_WORK_ITEM_TYPE__HIDDEN_PERIOD = 3;

    /**
     * The number of structural features of the '<em>Close Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLOSE_WORK_ITEM_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ColumnDefinitionImpl <em>Column Definition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ColumnDefinitionImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnDefinition()
     * @generated
     */
    int COLUMN_DEFINITION = 34;

    /**
     * The feature id for the '<em><b>Capability</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DEFINITION__CAPABILITY = 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DEFINITION__DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DEFINITION__ID = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DEFINITION__NAME = 3;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DEFINITION__TYPE = 4;

    /**
     * The number of structural features of the '<em>Column Definition</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DEFINITION_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ColumnOrderImpl <em>Column Order</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ColumnOrderImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnOrder()
     * @generated
     */
    int COLUMN_ORDER = 35;

    /**
     * The feature id for the '<em><b>Column Def</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_ORDER__COLUMN_DEF = 0;

    /**
     * The feature id for the '<em><b>Ascending</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_ORDER__ASCENDING = 1;

    /**
     * The number of structural features of the '<em>Column Order</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_ORDER_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.CompleteWorkItemResponseTypeImpl <em>Complete Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.CompleteWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCompleteWorkItemResponseType()
     * @generated
     */
    int COMPLETE_WORK_ITEM_RESPONSE_TYPE = 36;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID = 0;

    /**
     * The feature id for the '<em><b>Next Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM = 1;

    /**
     * The number of structural features of the '<em>Complete Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLETE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.CompleteWorkItemTypeImpl <em>Complete Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.CompleteWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCompleteWorkItemType()
     * @generated
     */
    int COMPLETE_WORK_ITEM_TYPE = 37;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Work Item Payload</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD = 1;

    /**
     * The feature id for the '<em><b>Get Next Piled Item</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM = 2;

    /**
     * The number of structural features of the '<em>Complete Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLETE_WORK_ITEM_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.CreateWorkListViewResponseTypeImpl <em>Create Work List View Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.CreateWorkListViewResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCreateWorkListViewResponseType()
     * @generated
     */
    int CREATE_WORK_LIST_VIEW_RESPONSE_TYPE = 38;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Create Work List View Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CREATE_WORK_LIST_VIEW_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.DeleteCurrentResourceFromViewResponseTypeImpl <em>Delete Current Resource From View Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.DeleteCurrentResourceFromViewResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteCurrentResourceFromViewResponseType()
     * @generated
     */
    int DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE = 39;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Delete Current Resource From View Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.DeleteCurrentResourceFromViewTypeImpl <em>Delete Current Resource From View Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.DeleteCurrentResourceFromViewTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteCurrentResourceFromViewType()
     * @generated
     */
    int DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE = 40;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Delete Current Resource From View Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.DeleteOrgEntityConfigAttributesResponseTypeImpl <em>Delete Org Entity Config Attributes Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.DeleteOrgEntityConfigAttributesResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteOrgEntityConfigAttributesResponseType()
     * @generated
     */
    int DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE = 41;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Delete Org Entity Config Attributes Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.DeleteOrgEntityConfigAttributesTypeImpl <em>Delete Org Entity Config Attributes Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.DeleteOrgEntityConfigAttributesTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteOrgEntityConfigAttributesType()
     * @generated
     */
    int DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE = 42;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Attribute Name</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ATTRIBUTE_NAME = 1;

    /**
     * The feature id for the '<em><b>Resource</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE = 2;

    /**
     * The number of structural features of the '<em>Delete Org Entity Config Attributes Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.DeleteWorkListViewResponseTypeImpl <em>Delete Work List View Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.DeleteWorkListViewResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteWorkListViewResponseType()
     * @generated
     */
    int DELETE_WORK_LIST_VIEW_RESPONSE_TYPE = 43;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Delete Work List View Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_WORK_LIST_VIEW_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.DeleteWorkListViewTypeImpl <em>Delete Work List View Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.DeleteWorkListViewTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteWorkListViewType()
     * @generated
     */
    int DELETE_WORK_LIST_VIEW_TYPE = 44;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Delete Work List View Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DELETE_WORK_LIST_VIEW_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.DocumentRootImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 45;

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
     * The feature id for the '<em><b>Add Current Resource To View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW = 3;

    /**
     * The feature id for the '<em><b>Add Current Resource To View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE = 4;

    /**
     * The feature id for the '<em><b>Allocate And Open Next Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM = 5;

    /**
     * The feature id for the '<em><b>Allocate And Open Next Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE = 6;

    /**
     * The feature id for the '<em><b>Allocate And Open Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM = 7;

    /**
     * The feature id for the '<em><b>Allocate And Open Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE = 8;

    /**
     * The feature id for the '<em><b>Allocate Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ALLOCATE_WORK_ITEM = 9;

    /**
     * The feature id for the '<em><b>Allocate Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE = 10;

    /**
     * The feature id for the '<em><b>Async Cancel Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM = 11;

    /**
     * The feature id for the '<em><b>Async Cancel Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE = 12;

    /**
     * The feature id for the '<em><b>Async End Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_END_GROUP = 13;

    /**
     * The feature id for the '<em><b>Async End Group Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE = 14;

    /**
     * The feature id for the '<em><b>Async Reschedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM = 15;

    /**
     * The feature id for the '<em><b>Async Reschedule Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE = 16;

    /**
     * The feature id for the '<em><b>Async Resume Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM = 17;

    /**
     * The feature id for the '<em><b>Async Resume Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE = 18;

    /**
     * The feature id for the '<em><b>Async Schedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM = 19;

    /**
     * The feature id for the '<em><b>Async Schedule Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE = 20;

    /**
     * The feature id for the '<em><b>Async Schedule Work Item With Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL = 21;

    /**
     * The feature id for the '<em><b>Async Schedule Work Item With Model Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE = 22;

    /**
     * The feature id for the '<em><b>Async Start Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_START_GROUP = 23;

    /**
     * The feature id for the '<em><b>Async Start Group Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE = 24;

    /**
     * The feature id for the '<em><b>Async Suspend Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM = 25;

    /**
     * The feature id for the '<em><b>Async Suspend Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE = 26;

    /**
     * The feature id for the '<em><b>Cancel Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CANCEL_WORK_ITEM = 27;

    /**
     * The feature id for the '<em><b>Cancel Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE = 28;

    /**
     * The feature id for the '<em><b>Chained Work Item Notification</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION = 29;

    /**
     * The feature id for the '<em><b>Close Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CLOSE_WORK_ITEM = 30;

    /**
     * The feature id for the '<em><b>Close Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE = 31;

    /**
     * The feature id for the '<em><b>Complete Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__COMPLETE_WORK_ITEM = 32;

    /**
     * The feature id for the '<em><b>Complete Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE = 33;

    /**
     * The feature id for the '<em><b>Create Work List View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW = 34;

    /**
     * The feature id for the '<em><b>Create Work List View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE = 35;

    /**
     * The feature id for the '<em><b>Delete Current Resource From View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW = 36;

    /**
     * The feature id for the '<em><b>Delete Current Resource From View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE = 37;

    /**
     * The feature id for the '<em><b>Delete Org Entity Config Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES = 38;

    /**
     * The feature id for the '<em><b>Delete Org Entity Config Attributes Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE = 39;

    /**
     * The feature id for the '<em><b>Delete Work List View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW = 40;

    /**
     * The feature id for the '<em><b>Delete Work List View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE = 41;

    /**
     * The feature id for the '<em><b>Edit Work List View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW = 42;

    /**
     * The feature id for the '<em><b>Edit Work List View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE = 43;

    /**
     * The feature id for the '<em><b>Enable Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ENABLE_WORK_ITEM = 44;

    /**
     * The feature id for the '<em><b>Enable Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE = 45;

    /**
     * The feature id for the '<em><b>End Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__END_GROUP = 46;

    /**
     * The feature id for the '<em><b>End Group Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__END_GROUP_RESPONSE = 47;

    /**
     * The feature id for the '<em><b>Get Allocated Work List Items</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS = 48;

    /**
     * The feature id for the '<em><b>Get Batch Group Ids</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_BATCH_GROUP_IDS = 49;

    /**
     * The feature id for the '<em><b>Get Batch Group Ids Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE = 50;

    /**
     * The feature id for the '<em><b>Get Batch Work Item Ids</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS = 51;

    /**
     * The feature id for the '<em><b>Get Batch Work Item Ids Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE = 52;

    /**
     * The feature id for the '<em><b>Get Editable Work List Views</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS = 53;

    /**
     * The feature id for the '<em><b>Get Editable Work List Views Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE = 54;

    /**
     * The feature id for the '<em><b>Get Offer Set</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_OFFER_SET = 55;

    /**
     * The feature id for the '<em><b>Get Offer Set Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE = 56;

    /**
     * The feature id for the '<em><b>Get Org Entity Config Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES = 57;

    /**
     * The feature id for the '<em><b>Get Org Entity Config Attributes Available</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE = 58;

    /**
     * The feature id for the '<em><b>Get Org Entity Config Attributes Available Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE = 59;

    /**
     * The feature id for the '<em><b>Get Org Entity Config Attributes Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE = 60;

    /**
     * The feature id for the '<em><b>Get Public Work List Views</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS = 61;

    /**
     * The feature id for the '<em><b>Get Public Work List Views Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE = 62;

    /**
     * The feature id for the '<em><b>Get Resource Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA = 63;

    /**
     * The feature id for the '<em><b>Get Resource Order Filter Criteria Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE = 64;

    /**
     * The feature id for the '<em><b>Get Views For Resource</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE = 65;

    /**
     * The feature id for the '<em><b>Get Views For Resource Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE = 66;

    /**
     * The feature id for the '<em><b>Get Work Item Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_ITEM_HEADER = 67;

    /**
     * The feature id for the '<em><b>Get Work Item Header Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE = 68;

    /**
     * The feature id for the '<em><b>Get Work Item Order Filter</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER = 69;

    /**
     * The feature id for the '<em><b>Get Work Item Order Filter Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE = 70;

    /**
     * The feature id for the '<em><b>Get Work List Items</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_LIST_ITEMS = 71;

    /**
     * The feature id for the '<em><b>Get Work List Items For Global Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA = 72;

    /**
     * The feature id for the '<em><b>Get Work List Items For Global Data Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE = 73;

    /**
     * The feature id for the '<em><b>Get Work List Items For View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW = 74;

    /**
     * The feature id for the '<em><b>Get Work List Items For View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE = 75;

    /**
     * The feature id for the '<em><b>Get Work List Items Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE = 76;

    /**
     * The feature id for the '<em><b>Get Work List View Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS = 77;

    /**
     * The feature id for the '<em><b>Get Work List View Details Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE = 78;

    /**
     * The feature id for the '<em><b>Get Work Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_MODEL = 79;

    /**
     * The feature id for the '<em><b>Get Work Model Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE = 80;

    /**
     * The feature id for the '<em><b>Get Work Models</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_MODELS = 81;

    /**
     * The feature id for the '<em><b>Get Work Models Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE = 82;

    /**
     * The feature id for the '<em><b>Get Work Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_TYPE = 83;

    /**
     * The feature id for the '<em><b>Get Work Type Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE = 84;

    /**
     * The feature id for the '<em><b>Get Work Types</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_TYPES = 85;

    /**
     * The feature id for the '<em><b>Get Work Types Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE = 86;

    /**
     * The feature id for the '<em><b>On Notification</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ON_NOTIFICATION = 87;

    /**
     * The feature id for the '<em><b>On Notification Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE = 88;

    /**
     * The feature id for the '<em><b>Open Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__OPEN_WORK_ITEM = 89;

    /**
     * The feature id for the '<em><b>Open Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE = 90;

    /**
     * The feature id for the '<em><b>Pend Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PEND_WORK_ITEM = 91;

    /**
     * The feature id for the '<em><b>Pend Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE = 92;

    /**
     * The feature id for the '<em><b>Preview Work Item From List</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST = 93;

    /**
     * The feature id for the '<em><b>Preview Work Item From List Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE = 94;

    /**
     * The feature id for the '<em><b>Push Notification</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PUSH_NOTIFICATION = 95;

    /**
     * The feature id for the '<em><b>Reallocate Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REALLOCATE_WORK_ITEM = 96;

    /**
     * The feature id for the '<em><b>Reallocate Work Item Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA = 97;

    /**
     * The feature id for the '<em><b>Reallocate Work Item Data Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE = 98;

    /**
     * The feature id for the '<em><b>Reallocate Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE = 99;

    /**
     * The feature id for the '<em><b>Reschedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM = 100;

    /**
     * The feature id for the '<em><b>Reschedule Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE = 101;

    /**
     * The feature id for the '<em><b>Resume Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__RESUME_WORK_ITEM = 102;

    /**
     * The feature id for the '<em><b>Resume Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE = 103;

    /**
     * The feature id for the '<em><b>Save Open Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM = 104;

    /**
     * The feature id for the '<em><b>Save Open Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE = 105;

    /**
     * The feature id for the '<em><b>Schedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SCHEDULE_WORK_ITEM = 106;

    /**
     * The feature id for the '<em><b>Schedule Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE = 107;

    /**
     * The feature id for the '<em><b>Schedule Work Item With Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL = 108;

    /**
     * The feature id for the '<em><b>Schedule Work Item With Model Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE = 109;

    /**
     * The feature id for the '<em><b>Set Org Entity Config Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES = 110;

    /**
     * The feature id for the '<em><b>Set Org Entity Config Attributes Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE = 111;

    /**
     * The feature id for the '<em><b>Set Resource Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA = 112;

    /**
     * The feature id for the '<em><b>Set Resource Order Filter Criteria Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE = 113;

    /**
     * The feature id for the '<em><b>Set Work Item Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY = 114;

    /**
     * The feature id for the '<em><b>Set Work Item Priority Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE = 115;

    /**
     * The feature id for the '<em><b>Skip Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SKIP_WORK_ITEM = 116;

    /**
     * The feature id for the '<em><b>Skip Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE = 117;

    /**
     * The feature id for the '<em><b>Start Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__START_GROUP = 118;

    /**
     * The feature id for the '<em><b>Start Group Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__START_GROUP_RESPONSE = 119;

    /**
     * The feature id for the '<em><b>Suspend Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SUSPEND_WORK_ITEM = 120;

    /**
     * The feature id for the '<em><b>Suspend Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE = 121;

    /**
     * The feature id for the '<em><b>Unallocate Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM = 122;

    /**
     * The feature id for the '<em><b>Unallocate Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE = 123;

    /**
     * The feature id for the '<em><b>Unlock Work List View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW = 124;

    /**
     * The feature id for the '<em><b>Unlock Work List View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE = 125;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 126;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.EditWorkListViewResponseTypeImpl <em>Edit Work List View Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.EditWorkListViewResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEditWorkListViewResponseType()
     * @generated
     */
    int EDIT_WORK_LIST_VIEW_RESPONSE_TYPE = 46;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Edit Work List View Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl <em>Work List View Common</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkListViewCommonImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkListViewCommon()
     * @generated
     */
    int WORK_LIST_VIEW_COMMON = 153;

    /**
     * The feature id for the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON__ENTITY_ID = 0;

    /**
     * The feature id for the '<em><b>Resources Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED = 1;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA = 2;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON__DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Get Allocated Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON__NAME = 5;

    /**
     * The feature id for the '<em><b>Owner</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON__OWNER = 6;

    /**
     * The feature id for the '<em><b>Public</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON__PUBLIC = 7;

    /**
     * The number of structural features of the '<em>Work List View Common</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_COMMON_FEATURE_COUNT = 8;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkListViewEditImpl <em>Work List View Edit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkListViewEditImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkListViewEdit()
     * @generated
     */
    int WORK_LIST_VIEW_EDIT = 154;

    /**
     * The feature id for the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__ENTITY_ID = WORK_LIST_VIEW_COMMON__ENTITY_ID;

    /**
     * The feature id for the '<em><b>Resources Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__RESOURCES_REQUIRED = WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__ORDER_FILTER_CRITERIA = WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__DESCRIPTION = WORK_LIST_VIEW_COMMON__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Get Allocated Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__GET_ALLOCATED_ITEMS = WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__NAME = WORK_LIST_VIEW_COMMON__NAME;

    /**
     * The feature id for the '<em><b>Owner</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__OWNER = WORK_LIST_VIEW_COMMON__OWNER;

    /**
     * The feature id for the '<em><b>Public</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__PUBLIC = WORK_LIST_VIEW_COMMON__PUBLIC;

    /**
     * The feature id for the '<em><b>Authors</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__AUTHORS = WORK_LIST_VIEW_COMMON_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Users</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__USERS = WORK_LIST_VIEW_COMMON_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Custom Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT__CUSTOM_DATA = WORK_LIST_VIEW_COMMON_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Work List View Edit</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_EDIT_FEATURE_COUNT = WORK_LIST_VIEW_COMMON_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.EditWorkListViewTypeImpl <em>Edit Work List View Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.EditWorkListViewTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEditWorkListViewType()
     * @generated
     */
    int EDIT_WORK_LIST_VIEW_TYPE = 47;

    /**
     * The feature id for the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__ENTITY_ID = WORK_LIST_VIEW_EDIT__ENTITY_ID;

    /**
     * The feature id for the '<em><b>Resources Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__RESOURCES_REQUIRED = WORK_LIST_VIEW_EDIT__RESOURCES_REQUIRED;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__ORDER_FILTER_CRITERIA = WORK_LIST_VIEW_EDIT__ORDER_FILTER_CRITERIA;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__DESCRIPTION = WORK_LIST_VIEW_EDIT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Get Allocated Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__GET_ALLOCATED_ITEMS = WORK_LIST_VIEW_EDIT__GET_ALLOCATED_ITEMS;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__NAME = WORK_LIST_VIEW_EDIT__NAME;

    /**
     * The feature id for the '<em><b>Owner</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__OWNER = WORK_LIST_VIEW_EDIT__OWNER;

    /**
     * The feature id for the '<em><b>Public</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__PUBLIC = WORK_LIST_VIEW_EDIT__PUBLIC;

    /**
     * The feature id for the '<em><b>Authors</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__AUTHORS = WORK_LIST_VIEW_EDIT__AUTHORS;

    /**
     * The feature id for the '<em><b>Users</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__USERS = WORK_LIST_VIEW_EDIT__USERS;

    /**
     * The feature id for the '<em><b>Custom Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__CUSTOM_DATA = WORK_LIST_VIEW_EDIT__CUSTOM_DATA;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID = WORK_LIST_VIEW_EDIT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Edit Work List View Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EDIT_WORK_LIST_VIEW_TYPE_FEATURE_COUNT = WORK_LIST_VIEW_EDIT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.EnableWorkItemResponseTypeImpl <em>Enable Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.EnableWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEnableWorkItemResponseType()
     * @generated
     */
    int ENABLE_WORK_ITEM_RESPONSE_TYPE = 48;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENABLE_WORK_ITEM_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Enable Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENABLE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.EnableWorkItemTypeImpl <em>Enable Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.EnableWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEnableWorkItemType()
     * @generated
     */
    int ENABLE_WORK_ITEM_TYPE = 49;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENABLE_WORK_ITEM_TYPE__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENABLE_WORK_ITEM_TYPE__ITEM_BODY = 1;

    /**
     * The number of structural features of the '<em>Enable Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENABLE_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.EndGroupResponseTypeImpl <em>End Group Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.EndGroupResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEndGroupResponseType()
     * @generated
     */
    int END_GROUP_RESPONSE_TYPE = 50;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_GROUP_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>End Group Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_GROUP_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.EndGroupTypeImpl <em>End Group Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.EndGroupTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEndGroupType()
     * @generated
     */
    int END_GROUP_TYPE = 51;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_GROUP_TYPE__GROUP_ID = 0;

    /**
     * The number of structural features of the '<em>End Group Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int END_GROUP_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetAllocatedWorkListItemsTypeImpl <em>Get Allocated Work List Items Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetAllocatedWorkListItemsTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetAllocatedWorkListItemsType()
     * @generated
     */
    int GET_ALLOCATED_WORK_LIST_ITEMS_TYPE = 52;

    /**
     * The feature id for the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__ENTITY_ID = 0;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__ORDER_FILTER_CRITERIA = 1;

    /**
     * The feature id for the '<em><b>Get Total Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__GET_TOTAL_COUNT = 2;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__NUMBER_OF_ITEMS = 3;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__START_POSITION = 4;

    /**
     * The number of structural features of the '<em>Get Allocated Work List Items Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ALLOCATED_WORK_LIST_ITEMS_TYPE_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetBatchGroupIdsResponseTypeImpl <em>Get Batch Group Ids Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetBatchGroupIdsResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetBatchGroupIdsResponseType()
     * @generated
     */
    int GET_BATCH_GROUP_IDS_RESPONSE_TYPE = 53;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_BATCH_GROUP_IDS_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_BATCH_GROUP_IDS_RESPONSE_TYPE__GROUP_ID = 1;

    /**
     * The number of structural features of the '<em>Get Batch Group Ids Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_BATCH_GROUP_IDS_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetBatchWorkItemIdsResponseTypeImpl <em>Get Batch Work Item Ids Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetBatchWorkItemIdsResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetBatchWorkItemIdsResponseType()
     * @generated
     */
    int GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE = 54;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Get Batch Work Item Ids Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetEditableWorkListViewsResponseTypeImpl <em>Get Editable Work List Views Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetEditableWorkListViewsResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetEditableWorkListViewsResponseType()
     * @generated
     */
    int GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE = 55;

    /**
     * The feature id for the '<em><b>Work List Views</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS = 0;

    /**
     * The number of structural features of the '<em>Get Editable Work List Views Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetEditableWorkListViewsTypeImpl <em>Get Editable Work List Views Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetEditableWorkListViewsTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetEditableWorkListViewsType()
     * @generated
     */
    int GET_EDITABLE_WORK_LIST_VIEWS_TYPE = 56;

    /**
     * The feature id for the '<em><b>Api Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_EDITABLE_WORK_LIST_VIEWS_TYPE__API_VERSION = 0;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_EDITABLE_WORK_LIST_VIEWS_TYPE__NUMBER_OF_ITEMS = 1;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_EDITABLE_WORK_LIST_VIEWS_TYPE__START_POSITION = 2;

    /**
     * The number of structural features of the '<em>Get Editable Work List Views Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_EDITABLE_WORK_LIST_VIEWS_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetOfferSetResponseTypeImpl <em>Get Offer Set Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetOfferSetResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOfferSetResponseType()
     * @generated
     */
    int GET_OFFER_SET_RESPONSE_TYPE = 57;

    /**
     * The feature id for the '<em><b>Entity Guid</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_OFFER_SET_RESPONSE_TYPE__ENTITY_GUID = 0;

    /**
     * The feature id for the '<em><b>Entity Id</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID = 1;

    /**
     * The number of structural features of the '<em>Get Offer Set Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_OFFER_SET_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetOfferSetTypeImpl <em>Get Offer Set Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetOfferSetTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOfferSetType()
     * @generated
     */
    int GET_OFFER_SET_TYPE = 58;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_OFFER_SET_TYPE__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Api Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_OFFER_SET_TYPE__API_VERSION = 1;

    /**
     * The number of structural features of the '<em>Get Offer Set Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_OFFER_SET_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableResponseTypeImpl <em>Get Org Entity Config Attributes Available Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOrgEntityConfigAttributesAvailableResponseType()
     * @generated
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE = 59;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Org Entity Config Attributes Available</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE = 1;

    /**
     * The number of structural features of the '<em>Get Org Entity Config Attributes Available Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableTypeImpl <em>Get Org Entity Config Attributes Available Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOrgEntityConfigAttributesAvailableType()
     * @generated
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE = 60;

    /**
     * The feature id for the '<em><b>Start At</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT = 0;

    /**
     * The feature id for the '<em><b>Num To Return</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN = 1;

    /**
     * The number of structural features of the '<em>Get Org Entity Config Attributes Available Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesResponseTypeImpl <em>Get Org Entity Config Attributes Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOrgEntityConfigAttributesResponseType()
     * @generated
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE = 61;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Org Entity Config Attribute</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE = 1;

    /**
     * The number of structural features of the '<em>Get Org Entity Config Attributes Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesTypeImpl <em>Get Org Entity Config Attributes Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOrgEntityConfigAttributesType()
     * @generated
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE = 62;

    /**
     * The feature id for the '<em><b>Resource</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE = 0;

    /**
     * The number of structural features of the '<em>Get Org Entity Config Attributes Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetPublicWorkListViewsResponseTypeImpl <em>Get Public Work List Views Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetPublicWorkListViewsResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetPublicWorkListViewsResponseType()
     * @generated
     */
    int GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE = 63;

    /**
     * The feature id for the '<em><b>Work List Views</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS = 0;

    /**
     * The number of structural features of the '<em>Get Public Work List Views Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetPublicWorkListViewsTypeImpl <em>Get Public Work List Views Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetPublicWorkListViewsTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetPublicWorkListViewsType()
     * @generated
     */
    int GET_PUBLIC_WORK_LIST_VIEWS_TYPE = 64;

    /**
     * The feature id for the '<em><b>Api Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_PUBLIC_WORK_LIST_VIEWS_TYPE__API_VERSION = 0;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_PUBLIC_WORK_LIST_VIEWS_TYPE__NUMBER_OF_ITEMS = 1;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_PUBLIC_WORK_LIST_VIEWS_TYPE__START_POSITION = 2;

    /**
     * The number of structural features of the '<em>Get Public Work List Views Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_PUBLIC_WORK_LIST_VIEWS_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaResponseTypeImpl <em>Get Resource Order Filter Criteria Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetResourceOrderFilterCriteriaResponseType()
     * @generated
     */
    int GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE = 65;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE__ORDER_FILTER_CRITERIA = 0;

    /**
     * The number of structural features of the '<em>Get Resource Order Filter Criteria Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaTypeImpl <em>Get Resource Order Filter Criteria Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetResourceOrderFilterCriteriaType()
     * @generated
     */
    int GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE = 66;

    /**
     * The feature id for the '<em><b>Resource ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID = 0;

    /**
     * The number of structural features of the '<em>Get Resource Order Filter Criteria Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetViewsForResourceResponseTypeImpl <em>Get Views For Resource Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetViewsForResourceResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetViewsForResourceResponseType()
     * @generated
     */
    int GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE = 67;

    /**
     * The feature id for the '<em><b>Work List Views</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE__WORK_LIST_VIEWS = 0;

    /**
     * The number of structural features of the '<em>Get Views For Resource Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetViewsForResourceTypeImpl <em>Get Views For Resource Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetViewsForResourceTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetViewsForResourceType()
     * @generated
     */
    int GET_VIEWS_FOR_RESOURCE_TYPE = 68;

    /**
     * The feature id for the '<em><b>Api Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION = 0;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS = 1;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION = 2;

    /**
     * The number of structural features of the '<em>Get Views For Resource Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_VIEWS_FOR_RESOURCE_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkItemHeaderResponseTypeImpl <em>Get Work Item Header Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkItemHeaderResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkItemHeaderResponseType()
     * @generated
     */
    int GET_WORK_ITEM_HEADER_RESPONSE_TYPE = 69;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_HEADER_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item Header</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_HEADER_RESPONSE_TYPE__WORK_ITEM_HEADER = 1;

    /**
     * The number of structural features of the '<em>Get Work Item Header Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_HEADER_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkItemHeaderTypeImpl <em>Get Work Item Header Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkItemHeaderTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkItemHeaderType()
     * @generated
     */
    int GET_WORK_ITEM_HEADER_TYPE = 70;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_HEADER_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_HEADER_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Get Work Item Header Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_HEADER_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterResponseTypeImpl <em>Get Work Item Order Filter Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkItemOrderFilterResponseType()
     * @generated
     */
    int GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE = 71;

    /**
     * The feature id for the '<em><b>Column Data</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA = 0;

    /**
     * The number of structural features of the '<em>Get Work Item Order Filter Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterTypeImpl <em>Get Work Item Order Filter Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkItemOrderFilterType()
     * @generated
     */
    int GET_WORK_ITEM_ORDER_FILTER_TYPE = 72;

    /**
     * The feature id for the '<em><b>Limit Columns</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS = 0;

    /**
     * The number of structural features of the '<em>Get Work Item Order Filter Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_ITEM_ORDER_FILTER_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataResponseTypeImpl <em>Get Work List Items For Global Data Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsForGlobalDataResponseType()
     * @generated
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE = 73;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__START_POSITION = 0;

    /**
     * The feature id for the '<em><b>End Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__END_POSITION = 1;

    /**
     * The feature id for the '<em><b>Work Items</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__WORK_ITEMS = 2;

    /**
     * The number of structural features of the '<em>Get Work List Items For Global Data Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataTypeImpl <em>Get Work List Items For Global Data Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsForGlobalDataType()
     * @generated
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE = 74;

    /**
     * The feature id for the '<em><b>Global Data Ref</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__GLOBAL_DATA_REF = 0;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA = 1;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS = 2;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION = 3;

    /**
     * The number of structural features of the '<em>Get Work List Items For Global Data Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewResponseTypeImpl <em>Get Work List Items For View Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkListItemsForViewResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsForViewResponseType()
     * @generated
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE = 75;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__START_POSITION = 0;

    /**
     * The feature id for the '<em><b>End Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__END_POSITION = 1;

    /**
     * The feature id for the '<em><b>Total Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__TOTAL_ITEMS = 2;

    /**
     * The feature id for the '<em><b>Work Items</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__WORK_ITEMS = 3;

    /**
     * The feature id for the '<em><b>Custom Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__CUSTOM_DATA = 4;

    /**
     * The number of structural features of the '<em>Get Work List Items For View Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl <em>Get Work List Items For View Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsForViewType()
     * @generated
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE = 76;

    /**
     * The feature id for the '<em><b>Get Allocated Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS = 0;

    /**
     * The feature id for the '<em><b>Get Total Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT = 1;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS = 2;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION = 3;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID = 4;

    /**
     * The number of structural features of the '<em>Get Work List Items For View Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsResponseTypeImpl <em>Get Work List Items Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkListItemsResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsResponseType()
     * @generated
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE = 77;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE__START_POSITION = 0;

    /**
     * The feature id for the '<em><b>End Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE__END_POSITION = 1;

    /**
     * The feature id for the '<em><b>Total Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE__TOTAL_ITEMS = 2;

    /**
     * The feature id for the '<em><b>Work Items</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE__WORK_ITEMS = 3;

    /**
     * The number of structural features of the '<em>Get Work List Items Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsResponseType1Impl <em>Get Work List Items Response Type1</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkListItemsResponseType1Impl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsResponseType1()
     * @generated
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE1 = 78;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__START_POSITION = 0;

    /**
     * The feature id for the '<em><b>End Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__END_POSITION = 1;

    /**
     * The feature id for the '<em><b>Total Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__TOTAL_ITEMS = 2;

    /**
     * The feature id for the '<em><b>Work Items</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__WORK_ITEMS = 3;

    /**
     * The number of structural features of the '<em>Get Work List Items Response Type1</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_RESPONSE_TYPE1_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsTypeImpl <em>Get Work List Items Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkListItemsTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsType()
     * @generated
     */
    int GET_WORK_LIST_ITEMS_TYPE = 79;

    /**
     * The feature id for the '<em><b>Resources Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_TYPE__RESOURCES_REQUIRED = 0;

    /**
     * The feature id for the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_TYPE__ENTITY_ID = 1;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_TYPE__ORDER_FILTER_CRITERIA = 2;

    /**
     * The feature id for the '<em><b>Get Total Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_TYPE__GET_TOTAL_COUNT = 3;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_TYPE__NUMBER_OF_ITEMS = 4;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_TYPE__START_POSITION = 5;

    /**
     * The number of structural features of the '<em>Get Work List Items Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_ITEMS_TYPE_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkListViewDetailsTypeImpl <em>Get Work List View Details Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkListViewDetailsTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListViewDetailsType()
     * @generated
     */
    int GET_WORK_LIST_VIEW_DETAILS_TYPE = 80;

    /**
     * The feature id for the '<em><b>Api Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION = 0;

    /**
     * The feature id for the '<em><b>Lock View</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW = 1;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID = 2;

    /**
     * The number of structural features of the '<em>Get Work List View Details Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_LIST_VIEW_DETAILS_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkModelResponseTypeImpl <em>Get Work Model Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkModelResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkModelResponseType()
     * @generated
     */
    int GET_WORK_MODEL_RESPONSE_TYPE = 81;

    /**
     * The feature id for the '<em><b>Work Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL = 0;

    /**
     * The number of structural features of the '<em>Get Work Model Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODEL_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkModelsResponseTypeImpl <em>Get Work Models Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkModelsResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkModelsResponseType()
     * @generated
     */
    int GET_WORK_MODELS_RESPONSE_TYPE = 82;

    /**
     * The feature id for the '<em><b>Work Model List</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST = 0;

    /**
     * The number of structural features of the '<em>Get Work Models Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODELS_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkModelsTypeImpl <em>Get Work Models Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkModelsTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkModelsType()
     * @generated
     */
    int GET_WORK_MODELS_TYPE = 83;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODELS_TYPE__START_POSITION = 0;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODELS_TYPE__NUMBER_OF_ITEMS = 1;

    /**
     * The number of structural features of the '<em>Get Work Models Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODELS_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkModelTypeImpl <em>Get Work Model Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkModelTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkModelType()
     * @generated
     */
    int GET_WORK_MODEL_TYPE = 84;

    /**
     * The feature id for the '<em><b>Work Model ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODEL_TYPE__WORK_MODEL_ID = 0;

    /**
     * The number of structural features of the '<em>Get Work Model Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_MODEL_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkTypeResponseTypeImpl <em>Get Work Type Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkTypeResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkTypeResponseType()
     * @generated
     */
    int GET_WORK_TYPE_RESPONSE_TYPE = 85;

    /**
     * The feature id for the '<em><b>Work Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE = 0;

    /**
     * The number of structural features of the '<em>Get Work Type Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPE_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkTypesResponseTypeImpl <em>Get Work Types Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkTypesResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkTypesResponseType()
     * @generated
     */
    int GET_WORK_TYPES_RESPONSE_TYPE = 86;

    /**
     * The feature id for the '<em><b>Work Typel List</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST = 0;

    /**
     * The number of structural features of the '<em>Get Work Types Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPES_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkTypesTypeImpl <em>Get Work Types Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkTypesTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkTypesType()
     * @generated
     */
    int GET_WORK_TYPES_TYPE = 87;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPES_TYPE__START_POSITION = 0;

    /**
     * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPES_TYPE__NUMBER_OF_ITEMS = 1;

    /**
     * The number of structural features of the '<em>Get Work Types Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPES_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.GetWorkTypeTypeImpl <em>Get Work Type Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.GetWorkTypeTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkTypeType()
     * @generated
     */
    int GET_WORK_TYPE_TYPE = 88;

    /**
     * The feature id for the '<em><b>Work Type ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPE_TYPE__WORK_TYPE_ID = 0;

    /**
     * The number of structural features of the '<em>Get Work Type Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GET_WORK_TYPE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.HiddenPeriodImpl <em>Hidden Period</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.HiddenPeriodImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getHiddenPeriod()
     * @generated
     */
    int HIDDEN_PERIOD = 89;

    /**
     * The feature id for the '<em><b>Hidden Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HIDDEN_PERIOD__HIDDEN_DURATION = 0;

    /**
     * The feature id for the '<em><b>Visible Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HIDDEN_PERIOD__VISIBLE_DATE = 1;

    /**
     * The number of structural features of the '<em>Hidden Period</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HIDDEN_PERIOD_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ItemImpl <em>Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ItemImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItem()
     * @generated
     */
    int ITEM = 90;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__NAME = BASE_ITEM_INFO__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__DESCRIPTION = BASE_ITEM_INFO__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Distribution Strategy</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__DISTRIBUTION_STRATEGY = BASE_ITEM_INFO__DISTRIBUTION_STRATEGY;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__GROUP_ID = BASE_ITEM_INFO__GROUP_ID;

    /**
     * The feature id for the '<em><b>Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__PRIORITY = BASE_ITEM_INFO__PRIORITY;

    /**
     * The feature id for the '<em><b>Entities</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__ENTITIES = BASE_ITEM_INFO_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Entity Query</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__ENTITY_QUERY = BASE_ITEM_INFO_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Work Type UID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__WORK_TYPE_UID = BASE_ITEM_INFO_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Work Type Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM__WORK_TYPE_VERSION = BASE_ITEM_INFO_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_FEATURE_COUNT = BASE_ITEM_INFO_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ItemBodyImpl <em>Item Body</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ItemBodyImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemBody()
     * @generated
     */
    int ITEM_BODY = 91;

    /**
     * The feature id for the '<em><b>Parameter</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_BODY__PARAMETER = 0;

    /**
     * The number of structural features of the '<em>Item Body</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_BODY_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ItemContextImpl <em>Item Context</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ItemContextImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemContext()
     * @generated
     */
    int ITEM_CONTEXT = 92;

    /**
     * The feature id for the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_CONTEXT__ACTIVITY_ID = 0;

    /**
     * The feature id for the '<em><b>Activity Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_CONTEXT__ACTIVITY_NAME = 1;

    /**
     * The feature id for the '<em><b>App Instance</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_CONTEXT__APP_INSTANCE = 2;

    /**
     * The feature id for the '<em><b>App Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_CONTEXT__APP_NAME = 3;

    /**
     * The feature id for the '<em><b>App ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_CONTEXT__APP_ID = 4;

    /**
     * The feature id for the '<em><b>App Instance Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_CONTEXT__APP_INSTANCE_DESCRIPTION = 5;

    /**
     * The number of structural features of the '<em>Item Context</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_CONTEXT_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ItemDurationImpl <em>Item Duration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ItemDurationImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemDuration()
     * @generated
     */
    int ITEM_DURATION = 93;

    /**
     * The feature id for the '<em><b>Days</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_DURATION__DAYS = 0;

    /**
     * The feature id for the '<em><b>Hours</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_DURATION__HOURS = 1;

    /**
     * The feature id for the '<em><b>Minutes</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_DURATION__MINUTES = 2;

    /**
     * The feature id for the '<em><b>Months</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_DURATION__MONTHS = 3;

    /**
     * The feature id for the '<em><b>Weeks</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_DURATION__WEEKS = 4;

    /**
     * The feature id for the '<em><b>Years</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_DURATION__YEARS = 5;

    /**
     * The number of structural features of the '<em>Item Duration</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_DURATION_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl <em>Item Privilege</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ItemPrivilegeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemPrivilege()
     * @generated
     */
    int ITEM_PRIVILEGE = 94;

    /**
     * The feature id for the '<em><b>Suspend</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_PRIVILEGE__SUSPEND = 0;

    /**
     * The feature id for the '<em><b>Stateless Realloc</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_PRIVILEGE__STATELESS_REALLOC = 1;

    /**
     * The feature id for the '<em><b>Stateful Realloc</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_PRIVILEGE__STATEFUL_REALLOC = 2;

    /**
     * The feature id for the '<em><b>Dellocate</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_PRIVILEGE__DELLOCATE = 3;

    /**
     * The feature id for the '<em><b>Delegate</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_PRIVILEGE__DELEGATE = 4;

    /**
     * The feature id for the '<em><b>Skip</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_PRIVILEGE__SKIP = 5;

    /**
     * The feature id for the '<em><b>Piling</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_PRIVILEGE__PILING = 6;

    /**
     * The number of structural features of the '<em>Item Privilege</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_PRIVILEGE_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ItemScheduleImpl <em>Item Schedule</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ItemScheduleImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemSchedule()
     * @generated
     */
    int ITEM_SCHEDULE = 95;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_SCHEDULE__START_DATE = 0;

    /**
     * The feature id for the '<em><b>Max Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_SCHEDULE__MAX_DURATION = 1;

    /**
     * The feature id for the '<em><b>Target Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_SCHEDULE__TARGET_DATE = 2;

    /**
     * The number of structural features of the '<em>Item Schedule</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ITEM_SCHEDULE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ObjectIDImpl <em>Object ID</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ObjectIDImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getObjectID()
     * @generated
     */
    int OBJECT_ID = 98;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_ID__ID = 0;

    /**
     * The number of structural features of the '<em>Object ID</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OBJECT_ID_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ManagedObjectIDImpl <em>Managed Object ID</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ManagedObjectIDImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getManagedObjectID()
     * @generated
     */
    int MANAGED_OBJECT_ID = 96;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MANAGED_OBJECT_ID__ID = OBJECT_ID__ID;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MANAGED_OBJECT_ID__VERSION = OBJECT_ID_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Managed Object ID</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MANAGED_OBJECT_ID_FEATURE_COUNT = OBJECT_ID_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.NextWorkItemImpl <em>Next Work Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.NextWorkItemImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getNextWorkItem()
     * @generated
     */
    int NEXT_WORK_ITEM = 97;

    /**
     * The feature id for the '<em><b>Next Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NEXT_WORK_ITEM__NEXT_ITEM = 0;

    /**
     * The number of structural features of the '<em>Next Work Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NEXT_WORK_ITEM_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.OnNotificationResponseTypeImpl <em>On Notification Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.OnNotificationResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOnNotificationResponseType()
     * @generated
     */
    int ON_NOTIFICATION_RESPONSE_TYPE = 99;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ON_NOTIFICATION_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>On Notification Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ON_NOTIFICATION_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.OnNotificationTypeImpl <em>On Notification Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.OnNotificationTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOnNotificationType()
     * @generated
     */
    int ON_NOTIFICATION_TYPE = 100;

    /**
     * The feature id for the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ON_NOTIFICATION_TYPE__MESSAGE_DETAILS = 0;

    /**
     * The feature id for the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ON_NOTIFICATION_TYPE__ITEM_BODY = 1;

    /**
     * The feature id for the '<em><b>Allocation History</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY = 2;

    /**
     * The number of structural features of the '<em>On Notification Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ON_NOTIFICATION_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.OpenWorkItemResponseTypeImpl <em>Open Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.OpenWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOpenWorkItemResponseType()
     * @generated
     */
    int OPEN_WORK_ITEM_RESPONSE_TYPE = 101;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPEN_WORK_ITEM_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item Body</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_BODY = 1;

    /**
     * The number of structural features of the '<em>Open Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPEN_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.OpenWorkItemTypeImpl <em>Open Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.OpenWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOpenWorkItemType()
     * @generated
     */
    int OPEN_WORK_ITEM_TYPE = 102;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPEN_WORK_ITEM_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Open Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPEN_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.OrderFilterCriteriaImpl <em>Order Filter Criteria</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.OrderFilterCriteriaImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOrderFilterCriteria()
     * @generated
     */
    int ORDER_FILTER_CRITERIA = 103;

    /**
     * The feature id for the '<em><b>Order</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORDER_FILTER_CRITERIA__ORDER = 0;

    /**
     * The feature id for the '<em><b>Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORDER_FILTER_CRITERIA__FILTER = 1;

    /**
     * The number of structural features of the '<em>Order Filter Criteria</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORDER_FILTER_CRITERIA_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.OrgEntityConfigAttributeImpl <em>Org Entity Config Attribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.OrgEntityConfigAttributeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOrgEntityConfigAttribute()
     * @generated
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE = 104;

    /**
     * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE__ATTRIBUTE_NAME = 0;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE__ATTRIBUTE_VALUE = 1;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE__READ_ONLY = 2;

    /**
     * The number of structural features of the '<em>Org Entity Config Attribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.OrgEntityConfigAttributesAvailableImpl <em>Org Entity Config Attributes Available</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.OrgEntityConfigAttributesAvailableImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOrgEntityConfigAttributesAvailable()
     * @generated
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE = 105;

    /**
     * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__ATTRIBUTE_NAME = 0;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY = 1;

    /**
     * The number of structural features of the '<em>Org Entity Config Attributes Available</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.OrgEntityConfigAttributeSetImpl <em>Org Entity Config Attribute Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.OrgEntityConfigAttributeSetImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOrgEntityConfigAttributeSet()
     * @generated
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE_SET = 106;

    /**
     * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE_SET__ATTRIBUTE_NAME = 0;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE_SET__ATTRIBUTE_VALUE = 1;

    /**
     * The number of structural features of the '<em>Org Entity Config Attribute Set</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ENTITY_CONFIG_ATTRIBUTE_SET_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ParameterTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getParameterType()
     * @generated
     */
    int PARAMETER_TYPE = 107;

    /**
     * The feature id for the '<em><b>Complex Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE__COMPLEX_VALUE = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE__VALUE = 1;

    /**
     * The feature id for the '<em><b>Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE__ARRAY = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE__NAME = 3;

    /**
     * The number of structural features of the '<em>Parameter Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.PendWorkItemImpl <em>Pend Work Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.PendWorkItemImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPendWorkItem()
     * @generated
     */
    int PEND_WORK_ITEM = 108;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PEND_WORK_ITEM__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PEND_WORK_ITEM__WORK_ITEM_ID = 1;

    /**
     * The feature id for the '<em><b>Hidden Period</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PEND_WORK_ITEM__HIDDEN_PERIOD = 2;

    /**
     * The number of structural features of the '<em>Pend Work Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PEND_WORK_ITEM_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.PendWorkItemResponseTypeImpl <em>Pend Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.PendWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPendWorkItemResponseType()
     * @generated
     */
    int PEND_WORK_ITEM_RESPONSE_TYPE = 109;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PEND_WORK_ITEM_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PEND_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Pend Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PEND_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListResponseTypeImpl <em>Preview Work Item From List Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.PreviewWorkItemFromListResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPreviewWorkItemFromListResponseType()
     * @generated
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE = 110;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item Preview</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW = 1;

    /**
     * The number of structural features of the '<em>Preview Work Item From List Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListTypeImpl <em>Preview Work Item From List Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.PreviewWorkItemFromListTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPreviewWorkItemFromListType()
     * @generated
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_TYPE = 111;

    /**
     * The feature id for the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID = 1;

    /**
     * The feature id for the '<em><b>Required Field</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRED_FIELD = 2;

    /**
     * The feature id for the '<em><b>Require Work Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE = 3;

    /**
     * The number of structural features of the '<em>Preview Work Item From List Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PREVIEW_WORK_ITEM_FROM_LIST_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.PrivilegeImpl <em>Privilege</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.PrivilegeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPrivilege()
     * @generated
     */
    int PRIVILEGE = 112;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__NAME = 0;

    /**
     * The feature id for the '<em><b>Qualifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__QUALIFIER = 1;

    /**
     * The number of structural features of the '<em>Privilege</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.PushNotificationTypeImpl <em>Push Notification Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.PushNotificationTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPushNotificationType()
     * @generated
     */
    int PUSH_NOTIFICATION_TYPE = 113;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Work Type ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID = 1;

    /**
     * The feature id for the '<em><b>Resource IDs</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PUSH_NOTIFICATION_TYPE__RESOURCE_IDS = 2;

    /**
     * The number of structural features of the '<em>Push Notification Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PUSH_NOTIFICATION_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemDataImpl <em>Reallocate Work Item Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ReallocateWorkItemDataImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getReallocateWorkItemData()
     * @generated
     */
    int REALLOCATE_WORK_ITEM_DATA = 114;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_DATA__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID = 1;

    /**
     * The feature id for the '<em><b>Resource</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_DATA__RESOURCE = 2;

    /**
     * The feature id for the '<em><b>Work Item Payload</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD = 3;

    /**
     * The number of structural features of the '<em>Reallocate Work Item Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_DATA_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemDataResponseTypeImpl <em>Reallocate Work Item Data Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ReallocateWorkItemDataResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getReallocateWorkItemDataResponseType()
     * @generated
     */
    int REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE = 115;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE__WORK_ITEM = 1;

    /**
     * The number of structural features of the '<em>Reallocate Work Item Data Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemResponseTypeImpl <em>Reallocate Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ReallocateWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getReallocateWorkItemResponseType()
     * @generated
     */
    int REALLOCATE_WORK_ITEM_RESPONSE_TYPE = 116;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = 1;

    /**
     * The number of structural features of the '<em>Reallocate Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemTypeImpl <em>Reallocate Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ReallocateWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getReallocateWorkItemType()
     * @generated
     */
    int REALLOCATE_WORK_ITEM_TYPE = 117;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID = 1;

    /**
     * The feature id for the '<em><b>Resource</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_TYPE__RESOURCE = 2;

    /**
     * The feature id for the '<em><b>Revert Data</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_TYPE__REVERT_DATA = 3;

    /**
     * The number of structural features of the '<em>Reallocate Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REALLOCATE_WORK_ITEM_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.RescheduleWorkItemImpl <em>Reschedule Work Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.RescheduleWorkItemImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getRescheduleWorkItem()
     * @generated
     */
    int RESCHEDULE_WORK_ITEM = 118;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_WORK_ITEM__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Item Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE = 1;

    /**
     * The feature id for the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_WORK_ITEM__ITEM_BODY = 2;

    /**
     * The number of structural features of the '<em>Reschedule Work Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_WORK_ITEM_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.RescheduleWorkItemResponseTypeImpl <em>Reschedule Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.RescheduleWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getRescheduleWorkItemResponseType()
     * @generated
     */
    int RESCHEDULE_WORK_ITEM_RESPONSE_TYPE = 119;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Reschedule Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESCHEDULE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ResumeWorkItemResponseTypeImpl <em>Resume Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ResumeWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getResumeWorkItemResponseType()
     * @generated
     */
    int RESUME_WORK_ITEM_RESPONSE_TYPE = 120;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESUME_WORK_ITEM_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Resume Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESUME_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ResumeWorkItemTypeImpl <em>Resume Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ResumeWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getResumeWorkItemType()
     * @generated
     */
    int RESUME_WORK_ITEM_TYPE = 121;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESUME_WORK_ITEM_TYPE__WORK_ITEM_ID = 0;

    /**
     * The number of structural features of the '<em>Resume Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESUME_WORK_ITEM_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SaveOpenWorkItemResponseTypeImpl <em>Save Open Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SaveOpenWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSaveOpenWorkItemResponseType()
     * @generated
     */
    int SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE = 122;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID = 0;

    /**
     * The number of structural features of the '<em>Save Open Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SaveOpenWorkItemTypeImpl <em>Save Open Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SaveOpenWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSaveOpenWorkItemType()
     * @generated
     */
    int SAVE_OPEN_WORK_ITEM_TYPE = 123;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Work Item Payload</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_OPEN_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD = 1;

    /**
     * The number of structural features of the '<em>Save Open Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SAVE_OPEN_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemResponseTypeImpl <em>Schedule Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ScheduleWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleWorkItemResponseType()
     * @generated
     */
    int SCHEDULE_WORK_ITEM_RESPONSE_TYPE = 124;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID = 0;

    /**
     * The number of structural features of the '<em>Schedule Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemTypeImpl <em>Schedule Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ScheduleWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleWorkItemType()
     * @generated
     */
    int SCHEDULE_WORK_ITEM_TYPE = 125;

    /**
     * The feature id for the '<em><b>Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_TYPE__ITEM = 0;

    /**
     * The feature id for the '<em><b>Item Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE = 1;

    /**
     * The feature id for the '<em><b>Item Context</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT = 2;

    /**
     * The feature id for the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY = 3;

    /**
     * The number of structural features of the '<em>Schedule Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelResponseTypeImpl <em>Schedule Work Item With Model Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleWorkItemWithModelResponseType()
     * @generated
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE = 126;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__WORK_ITEM_ID = 0;

    /**
     * The number of structural features of the '<em>Schedule Work Item With Model Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl <em>Schedule Work Item With Model Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleWorkItemWithModelType()
     * @generated
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE = 127;

    /**
     * The feature id for the '<em><b>Item Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE = 0;

    /**
     * The feature id for the '<em><b>Item Context</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT = 1;

    /**
     * The feature id for the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY = 2;

    /**
     * The feature id for the '<em><b>Entity Query</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY = 3;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID = 4;

    /**
     * The feature id for the '<em><b>Work Model UID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_UID = 5;

    /**
     * The feature id for the '<em><b>Work Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_VERSION = 6;

    /**
     * The number of structural features of the '<em>Schedule Work Item With Model Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesResponseTypeImpl <em>Set Org Entity Config Attributes Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetOrgEntityConfigAttributesResponseType()
     * @generated
     */
    int SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE = 128;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Set Org Entity Config Attributes Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesTypeImpl <em>Set Org Entity Config Attributes Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetOrgEntityConfigAttributesType()
     * @generated
     */
    int SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE = 129;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Org Entity Config Attribute Set</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET = 1;

    /**
     * The feature id for the '<em><b>Resource</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE = 2;

    /**
     * The number of structural features of the '<em>Set Org Entity Config Attributes Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaResponseTypeImpl <em>Set Resource Order Filter Criteria Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetResourceOrderFilterCriteriaResponseType()
     * @generated
     */
    int SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE = 130;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Set Resource Order Filter Criteria Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaTypeImpl <em>Set Resource Order Filter Criteria Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetResourceOrderFilterCriteriaType()
     * @generated
     */
    int SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE = 131;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA = 0;

    /**
     * The feature id for the '<em><b>Resource ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID = 1;

    /**
     * The number of structural features of the '<em>Set Resource Order Filter Criteria Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SetWorkItemPriorityImpl <em>Set Work Item Priority</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SetWorkItemPriorityImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetWorkItemPriority()
     * @generated
     */
    int SET_WORK_ITEM_PRIORITY = 132;

    /**
     * The feature id for the '<em><b>Work Item IDand Priority</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY = 0;

    /**
     * The number of structural features of the '<em>Set Work Item Priority</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_WORK_ITEM_PRIORITY_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SetWorkItemPriorityResponseTypeImpl <em>Set Work Item Priority Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SetWorkItemPriorityResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetWorkItemPriorityResponseType()
     * @generated
     */
    int SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE = 133;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Set Work Item Priority Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SkipWorkItemResponseTypeImpl <em>Skip Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SkipWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSkipWorkItemResponseType()
     * @generated
     */
    int SKIP_WORK_ITEM_RESPONSE_TYPE = 134;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SKIP_WORK_ITEM_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Skip Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SKIP_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SkipWorkItemTypeImpl <em>Skip Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SkipWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSkipWorkItemType()
     * @generated
     */
    int SKIP_WORK_ITEM_TYPE = 135;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SKIP_WORK_ITEM_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SKIP_WORK_ITEM_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Skip Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SKIP_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.StartGroupResponseTypeImpl <em>Start Group Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.StartGroupResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getStartGroupResponseType()
     * @generated
     */
    int START_GROUP_RESPONSE_TYPE = 136;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_GROUP_RESPONSE_TYPE__GROUP_ID = 0;

    /**
     * The number of structural features of the '<em>Start Group Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_GROUP_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.StartGroupTypeImpl <em>Start Group Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.StartGroupTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getStartGroupType()
     * @generated
     */
    int START_GROUP_TYPE = 137;

    /**
     * The feature id for the '<em><b>Group Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_GROUP_TYPE__GROUP_TYPE = 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_GROUP_TYPE__DESCRIPTION = 1;

    /**
     * The number of structural features of the '<em>Start Group Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_GROUP_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SuspendWorkItemResponseTypeImpl <em>Suspend Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SuspendWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSuspendWorkItemResponseType()
     * @generated
     */
    int SUSPEND_WORK_ITEM_RESPONSE_TYPE = 138;

    /**
     * The feature id for the '<em><b>Success</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUSPEND_WORK_ITEM_RESPONSE_TYPE__SUCCESS = 0;

    /**
     * The number of structural features of the '<em>Suspend Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUSPEND_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.SuspendWorkItemTypeImpl <em>Suspend Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.SuspendWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSuspendWorkItemType()
     * @generated
     */
    int SUSPEND_WORK_ITEM_TYPE = 139;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Force Suspend</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND = 1;

    /**
     * The number of structural features of the '<em>Suspend Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUSPEND_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.UnallocateWorkItemResponseTypeImpl <em>Unallocate Work Item Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.UnallocateWorkItemResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getUnallocateWorkItemResponseType()
     * @generated
     */
    int UNALLOCATE_WORK_ITEM_RESPONSE_TYPE = 140;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = 1;

    /**
     * The number of structural features of the '<em>Unallocate Work Item Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNALLOCATE_WORK_ITEM_RESPONSE_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.UnallocateWorkItemTypeImpl <em>Unallocate Work Item Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.UnallocateWorkItemTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getUnallocateWorkItemType()
     * @generated
     */
    int UNALLOCATE_WORK_ITEM_TYPE = 141;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNALLOCATE_WORK_ITEM_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID = 1;

    /**
     * The number of structural features of the '<em>Unallocate Work Item Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNALLOCATE_WORK_ITEM_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.UnlockWorkListViewResponseTypeImpl <em>Unlock Work List View Response Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.UnlockWorkListViewResponseTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getUnlockWorkListViewResponseType()
     * @generated
     */
    int UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE = 142;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Unlock Work List View Response Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.UnlockWorkListViewTypeImpl <em>Unlock Work List View Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.UnlockWorkListViewTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getUnlockWorkListViewType()
     * @generated
     */
    int UNLOCK_WORK_LIST_VIEW_TYPE = 143;

    /**
     * The feature id for the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNLOCK_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID = 0;

    /**
     * The number of structural features of the '<em>Unlock Work List View Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNLOCK_WORK_LIST_VIEW_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkItemImpl <em>Work Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkItemImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItem()
     * @generated
     */
    int WORK_ITEM = 144;

    /**
     * The feature id for the '<em><b>Id</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM__ID = 0;

    /**
     * The feature id for the '<em><b>Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM__HEADER = 1;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM__ATTRIBUTES = 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM__BODY = 3;

    /**
     * The feature id for the '<em><b>Work Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM__WORK_TYPE = 4;

    /**
     * The feature id for the '<em><b>State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM__STATE = 5;

    /**
     * The feature id for the '<em><b>Visible</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM__VISIBLE = 6;

    /**
     * The number of structural features of the '<em>Work Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl <em>Work Item Attributes</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkItemAttributesImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemAttributes()
     * @generated
     */
    int WORK_ITEM_ATTRIBUTES = 145;

    /**
     * The feature id for the '<em><b>Attribute1</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE1 = 0;

    /**
     * The feature id for the '<em><b>Attribute10</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE10 = 1;

    /**
     * The feature id for the '<em><b>Attribute11</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE11 = 2;

    /**
     * The feature id for the '<em><b>Attribute12</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE12 = 3;

    /**
     * The feature id for the '<em><b>Attribute13</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE13 = 4;

    /**
     * The feature id for the '<em><b>Attribute14</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE14 = 5;

    /**
     * The feature id for the '<em><b>Attribute15</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE15 = 6;

    /**
     * The feature id for the '<em><b>Attribute16</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE16 = 7;

    /**
     * The feature id for the '<em><b>Attribute17</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE17 = 8;

    /**
     * The feature id for the '<em><b>Attribute18</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE18 = 9;

    /**
     * The feature id for the '<em><b>Attribute19</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE19 = 10;

    /**
     * The feature id for the '<em><b>Attribute2</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE2 = 11;

    /**
     * The feature id for the '<em><b>Attribute20</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE20 = 12;

    /**
     * The feature id for the '<em><b>Attribute21</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE21 = 13;

    /**
     * The feature id for the '<em><b>Attribute22</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE22 = 14;

    /**
     * The feature id for the '<em><b>Attribute23</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE23 = 15;

    /**
     * The feature id for the '<em><b>Attribute24</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE24 = 16;

    /**
     * The feature id for the '<em><b>Attribute25</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE25 = 17;

    /**
     * The feature id for the '<em><b>Attribute26</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE26 = 18;

    /**
     * The feature id for the '<em><b>Attribute27</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE27 = 19;

    /**
     * The feature id for the '<em><b>Attribute28</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE28 = 20;

    /**
     * The feature id for the '<em><b>Attribute29</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE29 = 21;

    /**
     * The feature id for the '<em><b>Attribute3</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE3 = 22;

    /**
     * The feature id for the '<em><b>Attribute30</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE30 = 23;

    /**
     * The feature id for the '<em><b>Attribute31</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE31 = 24;

    /**
     * The feature id for the '<em><b>Attribute32</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE32 = 25;

    /**
     * The feature id for the '<em><b>Attribute33</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE33 = 26;

    /**
     * The feature id for the '<em><b>Attribute34</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE34 = 27;

    /**
     * The feature id for the '<em><b>Attribute35</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE35 = 28;

    /**
     * The feature id for the '<em><b>Attribute36</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE36 = 29;

    /**
     * The feature id for the '<em><b>Attribute37</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE37 = 30;

    /**
     * The feature id for the '<em><b>Attribute38</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE38 = 31;

    /**
     * The feature id for the '<em><b>Attribute39</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE39 = 32;

    /**
     * The feature id for the '<em><b>Attribute4</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE4 = 33;

    /**
     * The feature id for the '<em><b>Attribute40</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE40 = 34;

    /**
     * The feature id for the '<em><b>Attribute5</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE5 = 35;

    /**
     * The feature id for the '<em><b>Attribute6</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE6 = 36;

    /**
     * The feature id for the '<em><b>Attribute7</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE7 = 37;

    /**
     * The feature id for the '<em><b>Attribute8</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE8 = 38;

    /**
     * The feature id for the '<em><b>Attribute9</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__ATTRIBUTE9 = 39;

    /**
     * The number of structural features of the '<em>Work Item Attributes</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES_FEATURE_COUNT = 40;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkItemBodyImpl <em>Work Item Body</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkItemBodyImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemBody()
     * @generated
     */
    int WORK_ITEM_BODY = 146;

    /**
     * The feature id for the '<em><b>Data Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_BODY__DATA_MODEL = 0;

    /**
     * The number of structural features of the '<em>Work Item Body</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_BODY_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkItemFlagsImpl <em>Work Item Flags</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkItemFlagsImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemFlags()
     * @generated
     */
    int WORK_ITEM_FLAGS = 147;

    /**
     * The feature id for the '<em><b>Schedule Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_FLAGS__SCHEDULE_STATUS = 0;

    /**
     * The number of structural features of the '<em>Work Item Flags</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_FLAGS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkItemHeaderImpl <em>Work Item Header</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkItemHeaderImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemHeader()
     * @generated
     */
    int WORK_ITEM_HEADER = 148;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__NAME = BASE_ITEM_INFO__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__DESCRIPTION = BASE_ITEM_INFO__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Distribution Strategy</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__DISTRIBUTION_STRATEGY = BASE_ITEM_INFO__DISTRIBUTION_STRATEGY;

    /**
     * The feature id for the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__GROUP_ID = BASE_ITEM_INFO__GROUP_ID;

    /**
     * The feature id for the '<em><b>Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__PRIORITY = BASE_ITEM_INFO__PRIORITY;

    /**
     * The feature id for the '<em><b>Flags</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__FLAGS = BASE_ITEM_INFO_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Item Context</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__ITEM_CONTEXT = BASE_ITEM_INFO_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__END_DATE = BASE_ITEM_INFO_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER__START_DATE = BASE_ITEM_INFO_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Work Item Header</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_HEADER_FEATURE_COUNT = BASE_ITEM_INFO_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkItemIDandPriorityTypeImpl <em>Work Item IDand Priority Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkItemIDandPriorityTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemIDandPriorityType()
     * @generated
     */
    int WORK_ITEM_IDAND_PRIORITY_TYPE = 149;

    /**
     * The feature id for the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID = 0;

    /**
     * The feature id for the '<em><b>Work Item Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY = 1;

    /**
     * The number of structural features of the '<em>Work Item IDand Priority Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_IDAND_PRIORITY_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkItemPreviewImpl <em>Work Item Preview</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkItemPreviewImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemPreview()
     * @generated
     */
    int WORK_ITEM_PREVIEW = 150;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PREVIEW__ID = MANAGED_OBJECT_ID__ID;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PREVIEW__VERSION = MANAGED_OBJECT_ID__VERSION;

    /**
     * The feature id for the '<em><b>Field Preview</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PREVIEW__FIELD_PREVIEW = MANAGED_OBJECT_ID_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Work Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PREVIEW__WORK_TYPE = MANAGED_OBJECT_ID_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Work Item Preview</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PREVIEW_FEATURE_COUNT = MANAGED_OBJECT_ID_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkItemPriorityTypeImpl <em>Work Item Priority Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkItemPriorityTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemPriorityType()
     * @generated
     */
    int WORK_ITEM_PRIORITY_TYPE = 151;

    /**
     * The feature id for the '<em><b>Abs Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY = 0;

    /**
     * The feature id for the '<em><b>Offset Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY = 1;

    /**
     * The number of structural features of the '<em>Work Item Priority Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_PRIORITY_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkListViewImpl <em>Work List View</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkListViewImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkListView()
     * @generated
     */
    int WORK_LIST_VIEW = 152;

    /**
     * The feature id for the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__ENTITY_ID = WORK_LIST_VIEW_EDIT__ENTITY_ID;

    /**
     * The feature id for the '<em><b>Resources Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__RESOURCES_REQUIRED = WORK_LIST_VIEW_EDIT__RESOURCES_REQUIRED;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__ORDER_FILTER_CRITERIA = WORK_LIST_VIEW_EDIT__ORDER_FILTER_CRITERIA;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__DESCRIPTION = WORK_LIST_VIEW_EDIT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Get Allocated Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__GET_ALLOCATED_ITEMS = WORK_LIST_VIEW_EDIT__GET_ALLOCATED_ITEMS;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__NAME = WORK_LIST_VIEW_EDIT__NAME;

    /**
     * The feature id for the '<em><b>Owner</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__OWNER = WORK_LIST_VIEW_EDIT__OWNER;

    /**
     * The feature id for the '<em><b>Public</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__PUBLIC = WORK_LIST_VIEW_EDIT__PUBLIC;

    /**
     * The feature id for the '<em><b>Authors</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__AUTHORS = WORK_LIST_VIEW_EDIT__AUTHORS;

    /**
     * The feature id for the '<em><b>Users</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__USERS = WORK_LIST_VIEW_EDIT__USERS;

    /**
     * The feature id for the '<em><b>Custom Data</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__CUSTOM_DATA = WORK_LIST_VIEW_EDIT__CUSTOM_DATA;

    /**
     * The feature id for the '<em><b>Creation Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__CREATION_DATE = WORK_LIST_VIEW_EDIT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Locker</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__LOCKER = WORK_LIST_VIEW_EDIT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Modification Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__MODIFICATION_DATE = WORK_LIST_VIEW_EDIT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Work View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW__WORK_VIEW_ID = WORK_LIST_VIEW_EDIT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Work List View</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_FEATURE_COUNT = WORK_LIST_VIEW_EDIT_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkListViewPageItemImpl <em>Work List View Page Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkListViewPageItemImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkListViewPageItem()
     * @generated
     */
    int WORK_LIST_VIEW_PAGE_ITEM = 155;

    /**
     * The feature id for the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__ENTITY_ID = WORK_LIST_VIEW_COMMON__ENTITY_ID;

    /**
     * The feature id for the '<em><b>Resources Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__RESOURCES_REQUIRED = WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED;

    /**
     * The feature id for the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__ORDER_FILTER_CRITERIA = WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__DESCRIPTION = WORK_LIST_VIEW_COMMON__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Get Allocated Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__GET_ALLOCATED_ITEMS = WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__NAME = WORK_LIST_VIEW_COMMON__NAME;

    /**
     * The feature id for the '<em><b>Owner</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__OWNER = WORK_LIST_VIEW_COMMON__OWNER;

    /**
     * The feature id for the '<em><b>Public</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__PUBLIC = WORK_LIST_VIEW_COMMON__PUBLIC;

    /**
     * The feature id for the '<em><b>Creation Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__CREATION_DATE = WORK_LIST_VIEW_COMMON_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Modification Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__MODIFICATION_DATE = WORK_LIST_VIEW_COMMON_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Work View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID = WORK_LIST_VIEW_COMMON_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Work List View Page Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_VIEW_PAGE_ITEM_FEATURE_COUNT = WORK_LIST_VIEW_COMMON_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelImpl <em>Work Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModel()
     * @generated
     */
    int WORK_MODEL = 156;

    /**
     * The feature id for the '<em><b>Base Model Info</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__BASE_MODEL_INFO = 0;

    /**
     * The feature id for the '<em><b>Work Model Specification</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__WORK_MODEL_SPECIFICATION = 1;

    /**
     * The feature id for the '<em><b>Work Model Entities</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__WORK_MODEL_ENTITIES = 2;

    /**
     * The feature id for the '<em><b>Work Model Types</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__WORK_MODEL_TYPES = 3;

    /**
     * The feature id for the '<em><b>Item Privileges</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__ITEM_PRIVILEGES = 4;

    /**
     * The feature id for the '<em><b>Work Model Scripts</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__WORK_MODEL_SCRIPTS = 5;

    /**
     * The feature id for the '<em><b>Attribute Alias List</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__ATTRIBUTE_ALIAS_LIST = 6;

    /**
     * The feature id for the '<em><b>Work Model UID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__WORK_MODEL_UID = 7;

    /**
     * The number of structural features of the '<em>Work Model</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_FEATURE_COUNT = 8;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelEntitiesImpl <em>Work Model Entities</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelEntitiesImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelEntities()
     * @generated
     */
    int WORK_MODEL_ENTITIES = 157;

    /**
     * The feature id for the '<em><b>Work Model Entity</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_ENTITIES__WORK_MODEL_ENTITY = 0;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_ENTITIES__EXPRESSION = 1;

    /**
     * The number of structural features of the '<em>Work Model Entities</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_ENTITIES_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelEntityImpl <em>Work Model Entity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelEntityImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelEntity()
     * @generated
     */
    int WORK_MODEL_ENTITY = 158;

    /**
     * The feature id for the '<em><b>Entity Query</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_ENTITY__ENTITY_QUERY = 0;

    /**
     * The feature id for the '<em><b>Entities</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_ENTITY__ENTITIES = 1;

    /**
     * The feature id for the '<em><b>Distribution Strategy</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY = 2;

    /**
     * The number of structural features of the '<em>Work Model Entity</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_ENTITY_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelListImpl <em>Work Model List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelListImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelList()
     * @generated
     */
    int WORK_MODEL_LIST = 159;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_LIST__START_POSITION = 0;

    /**
     * The feature id for the '<em><b>End Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_LIST__END_POSITION = 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_LIST__TYPE = 2;

    /**
     * The number of structural features of the '<em>Work Model List</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_LIST_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelMappingImpl <em>Work Model Mapping</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelMappingImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelMapping()
     * @generated
     */
    int WORK_MODEL_MAPPING = 160;

    /**
     * The feature id for the '<em><b>Type Param Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_MAPPING__TYPE_PARAM_NAME = 0;

    /**
     * The feature id for the '<em><b>Model Param Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_MAPPING__MODEL_PARAM_NAME = 1;

    /**
     * The feature id for the '<em><b>Default Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_MAPPING__DEFAULT_VALUE = 2;

    /**
     * The number of structural features of the '<em>Work Model Mapping</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_MAPPING_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelScriptImpl <em>Work Model Script</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelScriptImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelScript()
     * @generated
     */
    int WORK_MODEL_SCRIPT = 161;

    /**
     * The feature id for the '<em><b>Script Body</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPT__SCRIPT_BODY = 0;

    /**
     * The feature id for the '<em><b>Script Language</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE = 1;

    /**
     * The feature id for the '<em><b>Script Language Extension</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_EXTENSION = 2;

    /**
     * The feature id for the '<em><b>Script Language Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_VERSION = 3;

    /**
     * The feature id for the '<em><b>Script Operation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPT__SCRIPT_OPERATION = 4;

    /**
     * The feature id for the '<em><b>Script Type ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPT__SCRIPT_TYPE_ID = 5;

    /**
     * The number of structural features of the '<em>Work Model Script</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPT_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelScriptsImpl <em>Work Model Scripts</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelScriptsImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelScripts()
     * @generated
     */
    int WORK_MODEL_SCRIPTS = 162;

    /**
     * The feature id for the '<em><b>Work Model Script</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT = 0;

    /**
     * The number of structural features of the '<em>Work Model Scripts</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SCRIPTS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelSpecificationImpl <em>Work Model Specification</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelSpecificationImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelSpecification()
     * @generated
     */
    int WORK_MODEL_SPECIFICATION = 163;

    /**
     * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SPECIFICATION__INPUTS = DatamodelPackage.DATA_MODEL__INPUTS;

    /**
     * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SPECIFICATION__OUTPUTS = DatamodelPackage.DATA_MODEL__OUTPUTS;

    /**
     * The feature id for the '<em><b>Inouts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SPECIFICATION__INOUTS = DatamodelPackage.DATA_MODEL__INOUTS;

    /**
     * The number of structural features of the '<em>Work Model Specification</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_SPECIFICATION_FEATURE_COUNT = DatamodelPackage.DATA_MODEL_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelTypeImpl <em>Work Model Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelTypeImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelType()
     * @generated
     */
    int WORK_MODEL_TYPE = 164;

    /**
     * The feature id for the '<em><b>Work Model Mapping</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_TYPE__WORK_MODEL_MAPPING = 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_TYPE__VERSION = 1;

    /**
     * The feature id for the '<em><b>Work Type ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_TYPE__WORK_TYPE_ID = 2;

    /**
     * The number of structural features of the '<em>Work Model Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkModelTypesImpl <em>Work Model Types</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkModelTypesImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelTypes()
     * @generated
     */
    int WORK_MODEL_TYPES = 165;

    /**
     * The feature id for the '<em><b>Work Model Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_TYPES__WORK_MODEL_TYPE = 0;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_TYPES__EXPRESSION = 1;

    /**
     * The number of structural features of the '<em>Work Model Types</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_TYPES_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.impl.WorkTypeListImpl <em>Work Type List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.impl.WorkTypeListImpl
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkTypeList()
     * @generated
     */
    int WORK_TYPE_LIST = 166;

    /**
     * The feature id for the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_LIST__START_POSITION = 0;

    /**
     * The feature id for the '<em><b>End Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_LIST__END_POSITION = 1;

    /**
     * The feature id for the '<em><b>Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_LIST__TYPES = 2;

    /**
     * The number of structural features of the '<em>Work Type List</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_LIST_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.ColumnCapability <em>Column Capability</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.ColumnCapability
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnCapability()
     * @generated
     */
    int COLUMN_CAPABILITY = 167;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.ColumnType <em>Column Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.ColumnType
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnType()
     * @generated
     */
    int COLUMN_TYPE = 168;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.DistributionStrategy <em>Distribution Strategy</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.DistributionStrategy
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDistributionStrategy()
     * @generated
     */
    int DISTRIBUTION_STRATEGY = 169;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.MethodAuthorisationAction <em>Method Authorisation Action</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.MethodAuthorisationAction
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getMethodAuthorisationAction()
     * @generated
     */
    int METHOD_AUTHORISATION_ACTION = 170;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.MethodAuthorisationComponent <em>Method Authorisation Component</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.MethodAuthorisationComponent
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getMethodAuthorisationComponent()
     * @generated
     */
    int METHOD_AUTHORISATION_COMPONENT = 171;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.ResourcesRequiredType <em>Resources Required Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.ResourcesRequiredType
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getResourcesRequiredType()
     * @generated
     */
    int RESOURCES_REQUIRED_TYPE = 172;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.ScheduleStatus <em>Schedule Status</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.ScheduleStatus
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleStatus()
     * @generated
     */
    int SCHEDULE_STATUS = 173;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.WorkGroupType <em>Work Group Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkGroupType
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkGroupType()
     * @generated
     */
    int WORK_GROUP_TYPE = 174;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.WorkItemScriptOperation <em>Work Item Script Operation</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkItemScriptOperation
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemScriptOperation()
     * @generated
     */
    int WORK_ITEM_SCRIPT_OPERATION = 175;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.WorkItemScriptType <em>Work Item Script Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkItemScriptType
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemScriptType()
     * @generated
     */
    int WORK_ITEM_SCRIPT_TYPE = 176;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.api.WorkItemState <em>Work Item State</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkItemState
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemState()
     * @generated
     */
    int WORK_ITEM_STATE = 177;

    /**
     * The meta object id for the '<em>Attribute10 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute10Type()
     * @generated
     */
    int ATTRIBUTE10_TYPE = 178;

    /**
     * The meta object id for the '<em>Attribute11 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute11Type()
     * @generated
     */
    int ATTRIBUTE11_TYPE = 179;

    /**
     * The meta object id for the '<em>Attribute12 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute12Type()
     * @generated
     */
    int ATTRIBUTE12_TYPE = 180;

    /**
     * The meta object id for the '<em>Attribute13 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute13Type()
     * @generated
     */
    int ATTRIBUTE13_TYPE = 181;

    /**
     * The meta object id for the '<em>Attribute14 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute14Type()
     * @generated
     */
    int ATTRIBUTE14_TYPE = 182;

    /**
     * The meta object id for the '<em>Attribute21 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute21Type()
     * @generated
     */
    int ATTRIBUTE21_TYPE = 183;

    /**
     * The meta object id for the '<em>Attribute22 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute22Type()
     * @generated
     */
    int ATTRIBUTE22_TYPE = 184;

    /**
     * The meta object id for the '<em>Attribute23 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute23Type()
     * @generated
     */
    int ATTRIBUTE23_TYPE = 185;

    /**
     * The meta object id for the '<em>Attribute24 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute24Type()
     * @generated
     */
    int ATTRIBUTE24_TYPE = 186;

    /**
     * The meta object id for the '<em>Attribute25 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute25Type()
     * @generated
     */
    int ATTRIBUTE25_TYPE = 187;

    /**
     * The meta object id for the '<em>Attribute26 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute26Type()
     * @generated
     */
    int ATTRIBUTE26_TYPE = 188;

    /**
     * The meta object id for the '<em>Attribute27 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute27Type()
     * @generated
     */
    int ATTRIBUTE27_TYPE = 189;

    /**
     * The meta object id for the '<em>Attribute28 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute28Type()
     * @generated
     */
    int ATTRIBUTE28_TYPE = 190;

    /**
     * The meta object id for the '<em>Attribute29 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute29Type()
     * @generated
     */
    int ATTRIBUTE29_TYPE = 191;

    /**
     * The meta object id for the '<em>Attribute2 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute2Type()
     * @generated
     */
    int ATTRIBUTE2_TYPE = 192;

    /**
     * The meta object id for the '<em>Attribute30 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute30Type()
     * @generated
     */
    int ATTRIBUTE30_TYPE = 193;

    /**
     * The meta object id for the '<em>Attribute31 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute31Type()
     * @generated
     */
    int ATTRIBUTE31_TYPE = 194;

    /**
     * The meta object id for the '<em>Attribute32 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute32Type()
     * @generated
     */
    int ATTRIBUTE32_TYPE = 195;

    /**
     * The meta object id for the '<em>Attribute33 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute33Type()
     * @generated
     */
    int ATTRIBUTE33_TYPE = 196;

    /**
     * The meta object id for the '<em>Attribute34 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute34Type()
     * @generated
     */
    int ATTRIBUTE34_TYPE = 197;

    /**
     * The meta object id for the '<em>Attribute35 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute35Type()
     * @generated
     */
    int ATTRIBUTE35_TYPE = 198;

    /**
     * The meta object id for the '<em>Attribute36 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute36Type()
     * @generated
     */
    int ATTRIBUTE36_TYPE = 199;

    /**
     * The meta object id for the '<em>Attribute37 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute37Type()
     * @generated
     */
    int ATTRIBUTE37_TYPE = 200;

    /**
     * The meta object id for the '<em>Attribute38 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute38Type()
     * @generated
     */
    int ATTRIBUTE38_TYPE = 201;

    /**
     * The meta object id for the '<em>Attribute39 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute39Type()
     * @generated
     */
    int ATTRIBUTE39_TYPE = 202;

    /**
     * The meta object id for the '<em>Attribute3 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute3Type()
     * @generated
     */
    int ATTRIBUTE3_TYPE = 203;

    /**
     * The meta object id for the '<em>Attribute40 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute40Type()
     * @generated
     */
    int ATTRIBUTE40_TYPE = 204;

    /**
     * The meta object id for the '<em>Attribute4 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute4Type()
     * @generated
     */
    int ATTRIBUTE4_TYPE = 205;

    /**
     * The meta object id for the '<em>Attribute8 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute8Type()
     * @generated
     */
    int ATTRIBUTE8_TYPE = 206;

    /**
     * The meta object id for the '<em>Attribute9 Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute9Type()
     * @generated
     */
    int ATTRIBUTE9_TYPE = 207;

    /**
     * The meta object id for the '<em>Column Capability Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.ColumnCapability
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnCapabilityObject()
     * @generated
     */
    int COLUMN_CAPABILITY_OBJECT = 208;

    /**
     * The meta object id for the '<em>Column Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.ColumnType
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnTypeObject()
     * @generated
     */
    int COLUMN_TYPE_OBJECT = 209;

    /**
     * The meta object id for the '<em>Description Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDescriptionType()
     * @generated
     */
    int DESCRIPTION_TYPE = 210;

    /**
     * The meta object id for the '<em>Distribution Strategy Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.DistributionStrategy
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDistributionStrategyObject()
     * @generated
     */
    int DISTRIBUTION_STRATEGY_OBJECT = 211;

    /**
     * The meta object id for the '<em>Locker Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getLockerType()
     * @generated
     */
    int LOCKER_TYPE = 212;

    /**
     * The meta object id for the '<em>Method Authorisation Action Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.MethodAuthorisationAction
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getMethodAuthorisationActionObject()
     * @generated
     */
    int METHOD_AUTHORISATION_ACTION_OBJECT = 213;

    /**
     * The meta object id for the '<em>Method Authorisation Component Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.MethodAuthorisationComponent
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getMethodAuthorisationComponentObject()
     * @generated
     */
    int METHOD_AUTHORISATION_COMPONENT_OBJECT = 214;

    /**
     * The meta object id for the '<em>Name Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getNameType()
     * @generated
     */
    int NAME_TYPE = 215;

    /**
     * The meta object id for the '<em>Owner Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOwnerType()
     * @generated
     */
    int OWNER_TYPE = 216;

    /**
     * The meta object id for the '<em>Resources Required Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.ResourcesRequiredType
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getResourcesRequiredTypeObject()
     * @generated
     */
    int RESOURCES_REQUIRED_TYPE_OBJECT = 217;

    /**
     * The meta object id for the '<em>Schedule Status Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.ScheduleStatus
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleStatusObject()
     * @generated
     */
    int SCHEDULE_STATUS_OBJECT = 218;

    /**
     * The meta object id for the '<em>Script Operation Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkItemScriptOperation
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScriptOperationType()
     * @generated
     */
    int SCRIPT_OPERATION_TYPE = 219;

    /**
     * The meta object id for the '<em>Work Group Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkGroupType
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkGroupTypeObject()
     * @generated
     */
    int WORK_GROUP_TYPE_OBJECT = 220;

    /**
     * The meta object id for the '<em>Work Item Script Operation Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkItemScriptOperation
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemScriptOperationObject()
     * @generated
     */
    int WORK_ITEM_SCRIPT_OPERATION_OBJECT = 221;

    /**
     * The meta object id for the '<em>Work Item Script Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkItemScriptType
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemScriptTypeObject()
     * @generated
     */
    int WORK_ITEM_SCRIPT_TYPE_OBJECT = 222;

    /**
     * The meta object id for the '<em>Work Item State Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.api.WorkItemState
     * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemStateObject()
     * @generated
     */
    int WORK_ITEM_STATE_OBJECT = 223;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AddCurrentResourceToViewResponseType <em>Add Current Resource To View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Add Current Resource To View Response Type</em>'.
     * @see com.tibco.n2.brm.api.AddCurrentResourceToViewResponseType
     * @generated
     */
    EClass getAddCurrentResourceToViewResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AddCurrentResourceToViewResponseType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.AddCurrentResourceToViewResponseType#getWorkListViewID()
     * @see #getAddCurrentResourceToViewResponseType()
     * @generated
     */
    EAttribute getAddCurrentResourceToViewResponseType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AddCurrentResourceToViewType <em>Add Current Resource To View Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Add Current Resource To View Type</em>'.
     * @see com.tibco.n2.brm.api.AddCurrentResourceToViewType
     * @generated
     */
    EClass getAddCurrentResourceToViewType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AddCurrentResourceToViewType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.AddCurrentResourceToViewType#getWorkListViewID()
     * @see #getAddCurrentResourceToViewType()
     * @generated
     */
    EAttribute getAddCurrentResourceToViewType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType <em>Allocate And Open Next Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocate And Open Next Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType
     * @generated
     */
    EClass getAllocateAndOpenNextWorkItemResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType#getWorkItem <em>Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType#getWorkItem()
     * @see #getAllocateAndOpenNextWorkItemResponseType()
     * @generated
     */
    EReference getAllocateAndOpenNextWorkItemResponseType_WorkItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType <em>Allocate And Open Next Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocate And Open Next Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType
     * @generated
     */
    EClass getAllocateAndOpenNextWorkItemType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getResource()
     * @see #getAllocateAndOpenNextWorkItemType()
     * @generated
     */
    EAttribute getAllocateAndOpenNextWorkItemType_Resource();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType#getWorkListViewID()
     * @see #getAllocateAndOpenNextWorkItemType()
     * @generated
     */
    EAttribute getAllocateAndOpenNextWorkItemType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType <em>Allocate And Open Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocate And Open Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType
     * @generated
     */
    EClass getAllocateAndOpenWorkItemResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType#getGroup()
     * @see #getAllocateAndOpenWorkItemResponseType()
     * @generated
     */
    EAttribute getAllocateAndOpenWorkItemResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType#getWorkItem <em>Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType#getWorkItem()
     * @see #getAllocateAndOpenWorkItemResponseType()
     * @generated
     */
    EReference getAllocateAndOpenWorkItemResponseType_WorkItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemType <em>Allocate And Open Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocate And Open Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemType
     * @generated
     */
    EClass getAllocateAndOpenWorkItemType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemType#getGroup()
     * @see #getAllocateAndOpenWorkItemType()
     * @generated
     */
    EAttribute getAllocateAndOpenWorkItemType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemType#getWorkItemID()
     * @see #getAllocateAndOpenWorkItemType()
     * @generated
     */
    EReference getAllocateAndOpenWorkItemType_WorkItemID();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemType#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Resource</em>'.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemType#getResource()
     * @see #getAllocateAndOpenWorkItemType()
     * @generated
     */
    EAttribute getAllocateAndOpenWorkItemType_Resource();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AllocateWorkItemResponseType <em>Allocate Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocate Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.AllocateWorkItemResponseType
     * @generated
     */
    EClass getAllocateWorkItemResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.AllocateWorkItemResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.AllocateWorkItemResponseType#getGroup()
     * @see #getAllocateWorkItemResponseType()
     * @generated
     */
    EAttribute getAllocateWorkItemResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.AllocateWorkItemResponseType#getWorkItem <em>Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item</em>'.
     * @see com.tibco.n2.brm.api.AllocateWorkItemResponseType#getWorkItem()
     * @see #getAllocateWorkItemResponseType()
     * @generated
     */
    EReference getAllocateWorkItemResponseType_WorkItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AllocateWorkItemType <em>Allocate Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocate Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.AllocateWorkItemType
     * @generated
     */
    EClass getAllocateWorkItemType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.AllocateWorkItemType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.AllocateWorkItemType#getGroup()
     * @see #getAllocateWorkItemType()
     * @generated
     */
    EAttribute getAllocateWorkItemType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.AllocateWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.AllocateWorkItemType#getWorkItemID()
     * @see #getAllocateWorkItemType()
     * @generated
     */
    EReference getAllocateWorkItemType_WorkItemID();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.AllocateWorkItemType#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Resource</em>'.
     * @see com.tibco.n2.brm.api.AllocateWorkItemType#getResource()
     * @see #getAllocateWorkItemType()
     * @generated
     */
    EAttribute getAllocateWorkItemType_Resource();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AllocationHistory <em>Allocation History</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocation History</em>'.
     * @see com.tibco.n2.brm.api.AllocationHistory
     * @generated
     */
    EClass getAllocationHistory();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AllocationHistory#getResourceID <em>Resource ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource ID</em>'.
     * @see com.tibco.n2.brm.api.AllocationHistory#getResourceID()
     * @see #getAllocationHistory()
     * @generated
     */
    EAttribute getAllocationHistory_ResourceID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AllocationHistory#getAllocationDate <em>Allocation Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Allocation Date</em>'.
     * @see com.tibco.n2.brm.api.AllocationHistory#getAllocationDate()
     * @see #getAllocationHistory()
     * @generated
     */
    EAttribute getAllocationHistory_AllocationDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AllocationHistory#getAllocationID <em>Allocation ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Allocation ID</em>'.
     * @see com.tibco.n2.brm.api.AllocationHistory#getAllocationID()
     * @see #getAllocationHistory()
     * @generated
     */
    EAttribute getAllocationHistory_AllocationID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType <em>Async Cancel Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Cancel Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType
     * @generated
     */
    EClass getAsyncCancelWorkItemResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType#getMessageDetails()
     * @see #getAsyncCancelWorkItemResponseType()
     * @generated
     */
    EReference getAsyncCancelWorkItemResponseType_MessageDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType#getErrorMessage <em>Error Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error Message</em>'.
     * @see com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType#getErrorMessage()
     * @see #getAsyncCancelWorkItemResponseType()
     * @generated
     */
    EReference getAsyncCancelWorkItemResponseType_ErrorMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncCancelWorkItemType <em>Async Cancel Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Cancel Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncCancelWorkItemType
     * @generated
     */
    EClass getAsyncCancelWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncCancelWorkItemType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncCancelWorkItemType#getMessageDetails()
     * @see #getAsyncCancelWorkItemType()
     * @generated
     */
    EReference getAsyncCancelWorkItemType_MessageDetails();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType <em>Async End Group Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async End Group Response Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncEndGroupResponseType
     * @generated
     */
    EClass getAsyncEndGroupResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getActivityID <em>Activity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity ID</em>'.
     * @see com.tibco.n2.brm.api.AsyncEndGroupResponseType#getActivityID()
     * @see #getAsyncEndGroupResponseType()
     * @generated
     */
    EAttribute getAsyncEndGroupResponseType_ActivityID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.AsyncEndGroupResponseType#getGroupID()
     * @see #getAsyncEndGroupResponseType()
     * @generated
     */
    EAttribute getAsyncEndGroupResponseType_GroupID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getErrorMessage <em>Error Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error Message</em>'.
     * @see com.tibco.n2.brm.api.AsyncEndGroupResponseType#getErrorMessage()
     * @see #getAsyncEndGroupResponseType()
     * @generated
     */
    EReference getAsyncEndGroupResponseType_ErrorMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncEndGroupType <em>Async End Group Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async End Group Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncEndGroupType
     * @generated
     */
    EClass getAsyncEndGroupType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncEndGroupType#getActivityID <em>Activity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity ID</em>'.
     * @see com.tibco.n2.brm.api.AsyncEndGroupType#getActivityID()
     * @see #getAsyncEndGroupType()
     * @generated
     */
    EAttribute getAsyncEndGroupType_ActivityID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncEndGroupType#getEndGroup <em>End Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>End Group</em>'.
     * @see com.tibco.n2.brm.api.AsyncEndGroupType#getEndGroup()
     * @see #getAsyncEndGroupType()
     * @generated
     */
    EReference getAsyncEndGroupType_EndGroup();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType <em>Async Reschedule Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Reschedule Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType
     * @generated
     */
    EClass getAsyncRescheduleWorkItemResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType#getMessageDetails()
     * @see #getAsyncRescheduleWorkItemResponseType()
     * @generated
     */
    EReference getAsyncRescheduleWorkItemResponseType_MessageDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType#getErrorMessage <em>Error Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error Message</em>'.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType#getErrorMessage()
     * @see #getAsyncRescheduleWorkItemResponseType()
     * @generated
     */
    EReference getAsyncRescheduleWorkItemResponseType_ErrorMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType <em>Async Reschedule Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Reschedule Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemType
     * @generated
     */
    EClass getAsyncRescheduleWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getItemSchedule <em>Item Schedule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Schedule</em>'.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getItemSchedule()
     * @see #getAsyncRescheduleWorkItemType()
     * @generated
     */
    EReference getAsyncRescheduleWorkItemType_ItemSchedule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getMessageDetails()
     * @see #getAsyncRescheduleWorkItemType()
     * @generated
     */
    EReference getAsyncRescheduleWorkItemType_MessageDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getItemBody <em>Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Body</em>'.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getItemBody()
     * @see #getAsyncRescheduleWorkItemType()
     * @generated
     */
    EReference getAsyncRescheduleWorkItemType_ItemBody();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType <em>Async Resume Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Resume Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType
     * @generated
     */
    EClass getAsyncResumeWorkItemResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType#getMessageDetails()
     * @see #getAsyncResumeWorkItemResponseType()
     * @generated
     */
    EReference getAsyncResumeWorkItemResponseType_MessageDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType#getErrorMessage <em>Error Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error Message</em>'.
     * @see com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType#getErrorMessage()
     * @see #getAsyncResumeWorkItemResponseType()
     * @generated
     */
    EReference getAsyncResumeWorkItemResponseType_ErrorMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemType <em>Async Resume Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Resume Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncResumeWorkItemType
     * @generated
     */
    EClass getAsyncResumeWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncResumeWorkItemType#getMessageDetails()
     * @see #getAsyncResumeWorkItemType()
     * @generated
     */
    EReference getAsyncResumeWorkItemType_MessageDetails();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType <em>Async Schedule Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Schedule Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType
     * @generated
     */
    EClass getAsyncScheduleWorkItemResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType#getMessageDetails()
     * @see #getAsyncScheduleWorkItemResponseType()
     * @generated
     */
    EReference getAsyncScheduleWorkItemResponseType_MessageDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType#getErrorMessage <em>Error Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error Message</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType#getErrorMessage()
     * @see #getAsyncScheduleWorkItemResponseType()
     * @generated
     */
    EReference getAsyncScheduleWorkItemResponseType_ErrorMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemType <em>Async Schedule Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Schedule Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemType
     * @generated
     */
    EClass getAsyncScheduleWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemType#getScheduleWorkItem <em>Schedule Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schedule Work Item</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemType#getScheduleWorkItem()
     * @see #getAsyncScheduleWorkItemType()
     * @generated
     */
    EReference getAsyncScheduleWorkItemType_ScheduleWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemType#getMessageDetails()
     * @see #getAsyncScheduleWorkItemType()
     * @generated
     */
    EReference getAsyncScheduleWorkItemType_MessageDetails();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType <em>Async Schedule Work Item With Model Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Schedule Work Item With Model Response Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType
     * @generated
     */
    EClass getAsyncScheduleWorkItemWithModelResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType#getMessageDetails()
     * @see #getAsyncScheduleWorkItemWithModelResponseType()
     * @generated
     */
    EReference getAsyncScheduleWorkItemWithModelResponseType_MessageDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType#getErrorMessage <em>Error Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error Message</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType#getErrorMessage()
     * @see #getAsyncScheduleWorkItemWithModelResponseType()
     * @generated
     */
    EReference getAsyncScheduleWorkItemWithModelResponseType_ErrorMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType <em>Async Schedule Work Item With Model Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Schedule Work Item With Model Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType
     * @generated
     */
    EClass getAsyncScheduleWorkItemWithModelType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType#getScheduleWorkItemWithModel <em>Schedule Work Item With Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schedule Work Item With Model</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType#getScheduleWorkItemWithModel()
     * @see #getAsyncScheduleWorkItemWithModelType()
     * @generated
     */
    EReference getAsyncScheduleWorkItemWithModelType_ScheduleWorkItemWithModel();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType#getMessageDetails()
     * @see #getAsyncScheduleWorkItemWithModelType()
     * @generated
     */
    EReference getAsyncScheduleWorkItemWithModelType_MessageDetails();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncStartGroupResponseType <em>Async Start Group Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Start Group Response Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncStartGroupResponseType
     * @generated
     */
    EClass getAsyncStartGroupResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncStartGroupResponseType#getActivityID <em>Activity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity ID</em>'.
     * @see com.tibco.n2.brm.api.AsyncStartGroupResponseType#getActivityID()
     * @see #getAsyncStartGroupResponseType()
     * @generated
     */
    EAttribute getAsyncStartGroupResponseType_ActivityID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncStartGroupResponseType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.AsyncStartGroupResponseType#getGroupID()
     * @see #getAsyncStartGroupResponseType()
     * @generated
     */
    EAttribute getAsyncStartGroupResponseType_GroupID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncStartGroupResponseType#getErrorMessage <em>Error Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error Message</em>'.
     * @see com.tibco.n2.brm.api.AsyncStartGroupResponseType#getErrorMessage()
     * @see #getAsyncStartGroupResponseType()
     * @generated
     */
    EReference getAsyncStartGroupResponseType_ErrorMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncStartGroupType <em>Async Start Group Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Start Group Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncStartGroupType
     * @generated
     */
    EClass getAsyncStartGroupType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncStartGroupType#getActivityID <em>Activity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity ID</em>'.
     * @see com.tibco.n2.brm.api.AsyncStartGroupType#getActivityID()
     * @see #getAsyncStartGroupType()
     * @generated
     */
    EAttribute getAsyncStartGroupType_ActivityID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncStartGroupType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.AsyncStartGroupType#getGroupID()
     * @see #getAsyncStartGroupType()
     * @generated
     */
    EAttribute getAsyncStartGroupType_GroupID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncStartGroupType#getStartGroup <em>Start Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Start Group</em>'.
     * @see com.tibco.n2.brm.api.AsyncStartGroupType#getStartGroup()
     * @see #getAsyncStartGroupType()
     * @generated
     */
    EReference getAsyncStartGroupType_StartGroup();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType <em>Async Suspend Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Suspend Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType
     * @generated
     */
    EClass getAsyncSuspendWorkItemResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType#getMessageDetails()
     * @see #getAsyncSuspendWorkItemResponseType()
     * @generated
     */
    EReference getAsyncSuspendWorkItemResponseType_MessageDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType#getErrorMessage <em>Error Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error Message</em>'.
     * @see com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType#getErrorMessage()
     * @see #getAsyncSuspendWorkItemResponseType()
     * @generated
     */
    EReference getAsyncSuspendWorkItemResponseType_ErrorMessage();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType <em>Async Suspend Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Suspend Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.AsyncSuspendWorkItemType
     * @generated
     */
    EClass getAsyncSuspendWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncSuspendWorkItemType#getMessageDetails()
     * @see #getAsyncSuspendWorkItemType()
     * @generated
     */
    EReference getAsyncSuspendWorkItemType_MessageDetails();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Force Suspend</em>'.
     * @see com.tibco.n2.brm.api.AsyncSuspendWorkItemType#isForceSuspend()
     * @see #getAsyncSuspendWorkItemType()
     * @generated
     */
    EAttribute getAsyncSuspendWorkItemType_ForceSuspend();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails <em>Async Work Item Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Async Work Item Details</em>'.
     * @see com.tibco.n2.brm.api.AsyncWorkItemDetails
     * @generated
     */
    EClass getAsyncWorkItemDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getWorkItemId <em>Work Item Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item Id</em>'.
     * @see com.tibco.n2.brm.api.AsyncWorkItemDetails#getWorkItemId()
     * @see #getAsyncWorkItemDetails()
     * @generated
     */
    EReference getAsyncWorkItemDetails_WorkItemId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getActivityID <em>Activity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity ID</em>'.
     * @see com.tibco.n2.brm.api.AsyncWorkItemDetails#getActivityID()
     * @see #getAsyncWorkItemDetails()
     * @generated
     */
    EAttribute getAsyncWorkItemDetails_ActivityID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getUaSequenceId <em>Ua Sequence Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ua Sequence Id</em>'.
     * @see com.tibco.n2.brm.api.AsyncWorkItemDetails#getUaSequenceId()
     * @see #getAsyncWorkItemDetails()
     * @generated
     */
    EAttribute getAsyncWorkItemDetails_UaSequenceId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getBrmSequenceId <em>Brm Sequence Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Brm Sequence Id</em>'.
     * @see com.tibco.n2.brm.api.AsyncWorkItemDetails#getBrmSequenceId()
     * @see #getAsyncWorkItemDetails()
     * @generated
     */
    EAttribute getAsyncWorkItemDetails_BrmSequenceId();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.AttributeAliasListType <em>Attribute Alias List Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute Alias List Type</em>'.
     * @see com.tibco.n2.brm.api.AttributeAliasListType
     * @generated
     */
    EClass getAttributeAliasListType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.AttributeAliasListType#getAttributeAlias <em>Attribute Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attribute Alias</em>'.
     * @see com.tibco.n2.brm.api.AttributeAliasListType#getAttributeAlias()
     * @see #getAttributeAliasListType()
     * @generated
     */
    EReference getAttributeAliasListType_AttributeAlias();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.BaseItemInfo <em>Base Item Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Base Item Info</em>'.
     * @see com.tibco.n2.brm.api.BaseItemInfo
     * @generated
     */
    EClass getBaseItemInfo();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.BaseItemInfo#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.brm.api.BaseItemInfo#getName()
     * @see #getBaseItemInfo()
     * @generated
     */
    EAttribute getBaseItemInfo_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.BaseItemInfo#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.n2.brm.api.BaseItemInfo#getDescription()
     * @see #getBaseItemInfo()
     * @generated
     */
    EAttribute getBaseItemInfo_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.BaseItemInfo#getDistributionStrategy <em>Distribution Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Distribution Strategy</em>'.
     * @see com.tibco.n2.brm.api.BaseItemInfo#getDistributionStrategy()
     * @see #getBaseItemInfo()
     * @generated
     */
    EAttribute getBaseItemInfo_DistributionStrategy();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.BaseItemInfo#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.BaseItemInfo#getGroupID()
     * @see #getBaseItemInfo()
     * @generated
     */
    EAttribute getBaseItemInfo_GroupID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.BaseItemInfo#getPriority <em>Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Priority</em>'.
     * @see com.tibco.n2.brm.api.BaseItemInfo#getPriority()
     * @see #getBaseItemInfo()
     * @generated
     */
    EAttribute getBaseItemInfo_Priority();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.BaseModelInfo <em>Base Model Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Base Model Info</em>'.
     * @see com.tibco.n2.brm.api.BaseModelInfo
     * @generated
     */
    EClass getBaseModelInfo();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.BaseModelInfo#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.n2.brm.api.BaseModelInfo#getDescription()
     * @see #getBaseModelInfo()
     * @generated
     */
    EAttribute getBaseModelInfo_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.BaseModelInfo#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.brm.api.BaseModelInfo#getName()
     * @see #getBaseModelInfo()
     * @generated
     */
    EAttribute getBaseModelInfo_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.BaseModelInfo#getPriority <em>Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Priority</em>'.
     * @see com.tibco.n2.brm.api.BaseModelInfo#getPriority()
     * @see #getBaseModelInfo()
     * @generated
     */
    EAttribute getBaseModelInfo_Priority();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.CancelWorkItemResponseType <em>Cancel Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cancel Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.CancelWorkItemResponseType
     * @generated
     */
    EClass getCancelWorkItemResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.CancelWorkItemResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.CancelWorkItemResponseType#isSuccess()
     * @see #getCancelWorkItemResponseType()
     * @generated
     */
    EAttribute getCancelWorkItemResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.CancelWorkItemType <em>Cancel Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cancel Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.CancelWorkItemType
     * @generated
     */
    EClass getCancelWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.CancelWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.CancelWorkItemType#getWorkItemID()
     * @see #getCancelWorkItemType()
     * @generated
     */
    EReference getCancelWorkItemType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType <em>Chained Work Item Notification Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Chained Work Item Notification Type</em>'.
     * @see com.tibco.n2.brm.api.ChainedWorkItemNotificationType
     * @generated
     */
    EClass getChainedWorkItemNotificationType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getGroupID()
     * @see #getChainedWorkItemNotificationType()
     * @generated
     */
    EAttribute getChainedWorkItemNotificationType_GroupID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getWorkItemID()
     * @see #getChainedWorkItemNotificationType()
     * @generated
     */
    EReference getChainedWorkItemNotificationType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.CloseWorkItemResponseType <em>Close Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Close Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.CloseWorkItemResponseType
     * @generated
     */
    EClass getCloseWorkItemResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.CloseWorkItemResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.CloseWorkItemResponseType#getGroup()
     * @see #getCloseWorkItemResponseType()
     * @generated
     */
    EAttribute getCloseWorkItemResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.CloseWorkItemResponseType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.CloseWorkItemResponseType#getWorkItemID()
     * @see #getCloseWorkItemResponseType()
     * @generated
     */
    EReference getCloseWorkItemResponseType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.CloseWorkItemType <em>Close Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Close Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.CloseWorkItemType
     * @generated
     */
    EClass getCloseWorkItemType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.CloseWorkItemType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.CloseWorkItemType#getGroup()
     * @see #getCloseWorkItemType()
     * @generated
     */
    EAttribute getCloseWorkItemType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.CloseWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.CloseWorkItemType#getWorkItemID()
     * @see #getCloseWorkItemType()
     * @generated
     */
    EReference getCloseWorkItemType_WorkItemID();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.CloseWorkItemType#getWorkItemPayload <em>Work Item Payload</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item Payload</em>'.
     * @see com.tibco.n2.brm.api.CloseWorkItemType#getWorkItemPayload()
     * @see #getCloseWorkItemType()
     * @generated
     */
    EReference getCloseWorkItemType_WorkItemPayload();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.CloseWorkItemType#getHiddenPeriod <em>Hidden Period</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Hidden Period</em>'.
     * @see com.tibco.n2.brm.api.CloseWorkItemType#getHiddenPeriod()
     * @see #getCloseWorkItemType()
     * @generated
     */
    EReference getCloseWorkItemType_HiddenPeriod();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ColumnDefinition <em>Column Definition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Column Definition</em>'.
     * @see com.tibco.n2.brm.api.ColumnDefinition
     * @generated
     */
    EClass getColumnDefinition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ColumnDefinition#getCapability <em>Capability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Capability</em>'.
     * @see com.tibco.n2.brm.api.ColumnDefinition#getCapability()
     * @see #getColumnDefinition()
     * @generated
     */
    EAttribute getColumnDefinition_Capability();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ColumnDefinition#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.n2.brm.api.ColumnDefinition#getDescription()
     * @see #getColumnDefinition()
     * @generated
     */
    EAttribute getColumnDefinition_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ColumnDefinition#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.n2.brm.api.ColumnDefinition#getId()
     * @see #getColumnDefinition()
     * @generated
     */
    EAttribute getColumnDefinition_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ColumnDefinition#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.brm.api.ColumnDefinition#getName()
     * @see #getColumnDefinition()
     * @generated
     */
    EAttribute getColumnDefinition_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ColumnDefinition#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.n2.brm.api.ColumnDefinition#getType()
     * @see #getColumnDefinition()
     * @generated
     */
    EAttribute getColumnDefinition_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ColumnOrder <em>Column Order</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Column Order</em>'.
     * @see com.tibco.n2.brm.api.ColumnOrder
     * @generated
     */
    EClass getColumnOrder();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ColumnOrder#getColumnDef <em>Column Def</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Column Def</em>'.
     * @see com.tibco.n2.brm.api.ColumnOrder#getColumnDef()
     * @see #getColumnOrder()
     * @generated
     */
    EReference getColumnOrder_ColumnDef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ColumnOrder#isAscending <em>Ascending</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ascending</em>'.
     * @see com.tibco.n2.brm.api.ColumnOrder#isAscending()
     * @see #getColumnOrder()
     * @generated
     */
    EAttribute getColumnOrder_Ascending();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType <em>Complete Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Complete Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.CompleteWorkItemResponseType
     * @generated
     */
    EClass getCompleteWorkItemResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.CompleteWorkItemResponseType#getGroupID()
     * @see #getCompleteWorkItemResponseType()
     * @generated
     */
    EAttribute getCompleteWorkItemResponseType_GroupID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType#getNextWorkItem <em>Next Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Next Work Item</em>'.
     * @see com.tibco.n2.brm.api.CompleteWorkItemResponseType#getNextWorkItem()
     * @see #getCompleteWorkItemResponseType()
     * @generated
     */
    EReference getCompleteWorkItemResponseType_NextWorkItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.CompleteWorkItemType <em>Complete Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Complete Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.CompleteWorkItemType
     * @generated
     */
    EClass getCompleteWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.CompleteWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.CompleteWorkItemType#getWorkItemID()
     * @see #getCompleteWorkItemType()
     * @generated
     */
    EReference getCompleteWorkItemType_WorkItemID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.CompleteWorkItemType#getWorkItemPayload <em>Work Item Payload</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item Payload</em>'.
     * @see com.tibco.n2.brm.api.CompleteWorkItemType#getWorkItemPayload()
     * @see #getCompleteWorkItemType()
     * @generated
     */
    EReference getCompleteWorkItemType_WorkItemPayload();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.CompleteWorkItemType#isGetNextPiledItem <em>Get Next Piled Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Get Next Piled Item</em>'.
     * @see com.tibco.n2.brm.api.CompleteWorkItemType#isGetNextPiledItem()
     * @see #getCompleteWorkItemType()
     * @generated
     */
    EAttribute getCompleteWorkItemType_GetNextPiledItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.CreateWorkListViewResponseType <em>Create Work List View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Create Work List View Response Type</em>'.
     * @see com.tibco.n2.brm.api.CreateWorkListViewResponseType
     * @generated
     */
    EClass getCreateWorkListViewResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.CreateWorkListViewResponseType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.CreateWorkListViewResponseType#getWorkListViewID()
     * @see #getCreateWorkListViewResponseType()
     * @generated
     */
    EAttribute getCreateWorkListViewResponseType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewResponseType <em>Delete Current Resource From View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Current Resource From View Response Type</em>'.
     * @see com.tibco.n2.brm.api.DeleteCurrentResourceFromViewResponseType
     * @generated
     */
    EClass getDeleteCurrentResourceFromViewResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewResponseType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.DeleteCurrentResourceFromViewResponseType#getWorkListViewID()
     * @see #getDeleteCurrentResourceFromViewResponseType()
     * @generated
     */
    EAttribute getDeleteCurrentResourceFromViewResponseType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType <em>Delete Current Resource From View Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Current Resource From View Type</em>'.
     * @see com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType
     * @generated
     */
    EClass getDeleteCurrentResourceFromViewType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType#getWorkListViewID()
     * @see #getDeleteCurrentResourceFromViewType()
     * @generated
     */
    EAttribute getDeleteCurrentResourceFromViewType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType <em>Delete Org Entity Config Attributes Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Org Entity Config Attributes Response Type</em>'.
     * @see com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType
     * @generated
     */
    EClass getDeleteOrgEntityConfigAttributesResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType#isSuccess()
     * @see #getDeleteOrgEntityConfigAttributesResponseType()
     * @generated
     */
    EAttribute getDeleteOrgEntityConfigAttributesResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType <em>Delete Org Entity Config Attributes Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Org Entity Config Attributes Type</em>'.
     * @see com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType
     * @generated
     */
    EClass getDeleteOrgEntityConfigAttributesType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType#getGroup()
     * @see #getDeleteOrgEntityConfigAttributesType()
     * @generated
     */
    EAttribute getDeleteOrgEntityConfigAttributesType_Group();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType#getAttributeName <em>Attribute Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Attribute Name</em>'.
     * @see com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType#getAttributeName()
     * @see #getDeleteOrgEntityConfigAttributesType()
     * @generated
     */
    EAttribute getDeleteOrgEntityConfigAttributesType_AttributeName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource</em>'.
     * @see com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType#getResource()
     * @see #getDeleteOrgEntityConfigAttributesType()
     * @generated
     */
    EAttribute getDeleteOrgEntityConfigAttributesType_Resource();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.DeleteWorkListViewResponseType <em>Delete Work List View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Work List View Response Type</em>'.
     * @see com.tibco.n2.brm.api.DeleteWorkListViewResponseType
     * @generated
     */
    EClass getDeleteWorkListViewResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.DeleteWorkListViewResponseType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.DeleteWorkListViewResponseType#getWorkListViewID()
     * @see #getDeleteWorkListViewResponseType()
     * @generated
     */
    EAttribute getDeleteWorkListViewResponseType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.DeleteWorkListViewType <em>Delete Work List View Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Delete Work List View Type</em>'.
     * @see com.tibco.n2.brm.api.DeleteWorkListViewType
     * @generated
     */
    EClass getDeleteWorkListViewType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.DeleteWorkListViewType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.DeleteWorkListViewType#getWorkListViewID()
     * @see #getDeleteWorkListViewType()
     * @generated
     */
    EAttribute getDeleteWorkListViewType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.brm.api.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.brm.api.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAddCurrentResourceToView <em>Add Current Resource To View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Add Current Resource To View</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAddCurrentResourceToView()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AddCurrentResourceToView();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAddCurrentResourceToViewResponse <em>Add Current Resource To View Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Add Current Resource To View Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAddCurrentResourceToViewResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AddCurrentResourceToViewResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenNextWorkItem <em>Allocate And Open Next Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Allocate And Open Next Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenNextWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AllocateAndOpenNextWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenNextWorkItemResponse <em>Allocate And Open Next Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Allocate And Open Next Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenNextWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AllocateAndOpenNextWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenWorkItem <em>Allocate And Open Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Allocate And Open Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AllocateAndOpenWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenWorkItemResponse <em>Allocate And Open Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Allocate And Open Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AllocateAndOpenWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateWorkItem <em>Allocate Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Allocate Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAllocateWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AllocateWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateWorkItemResponse <em>Allocate Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Allocate Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAllocateWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AllocateWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncCancelWorkItem <em>Async Cancel Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Cancel Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncCancelWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncCancelWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncCancelWorkItemResponse <em>Async Cancel Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Cancel Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncCancelWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncCancelWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncEndGroup <em>Async End Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async End Group</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncEndGroup()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncEndGroup();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncEndGroupResponse <em>Async End Group Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async End Group Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncEndGroupResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncEndGroupResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncRescheduleWorkItem <em>Async Reschedule Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Reschedule Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncRescheduleWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncRescheduleWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncRescheduleWorkItemResponse <em>Async Reschedule Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Reschedule Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncRescheduleWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncRescheduleWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncResumeWorkItem <em>Async Resume Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Resume Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncResumeWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncResumeWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncResumeWorkItemResponse <em>Async Resume Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Resume Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncResumeWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncResumeWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItem <em>Async Schedule Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Schedule Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncScheduleWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemResponse <em>Async Schedule Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Schedule Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncScheduleWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemWithModel <em>Async Schedule Work Item With Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Schedule Work Item With Model</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemWithModel()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncScheduleWorkItemWithModel();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemWithModelResponse <em>Async Schedule Work Item With Model Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Schedule Work Item With Model Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemWithModelResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncScheduleWorkItemWithModelResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncStartGroup <em>Async Start Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Start Group</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncStartGroup()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncStartGroup();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncStartGroupResponse <em>Async Start Group Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Start Group Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncStartGroupResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncStartGroupResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncSuspendWorkItem <em>Async Suspend Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Suspend Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncSuspendWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncSuspendWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncSuspendWorkItemResponse <em>Async Suspend Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Async Suspend Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getAsyncSuspendWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_AsyncSuspendWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getCancelWorkItem <em>Cancel Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Cancel Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getCancelWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CancelWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getCancelWorkItemResponse <em>Cancel Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Cancel Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getCancelWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CancelWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getChainedWorkItemNotification <em>Chained Work Item Notification</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Chained Work Item Notification</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getChainedWorkItemNotification()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ChainedWorkItemNotification();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getCloseWorkItem <em>Close Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Close Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getCloseWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CloseWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getCloseWorkItemResponse <em>Close Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Close Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getCloseWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CloseWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getCompleteWorkItem <em>Complete Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Complete Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getCompleteWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CompleteWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getCompleteWorkItemResponse <em>Complete Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Complete Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getCompleteWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CompleteWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getCreateWorkListView <em>Create Work List View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Create Work List View</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getCreateWorkListView()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CreateWorkListView();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getCreateWorkListViewResponse <em>Create Work List View Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Create Work List View Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getCreateWorkListViewResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_CreateWorkListViewResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteCurrentResourceFromView <em>Delete Current Resource From View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Current Resource From View</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getDeleteCurrentResourceFromView()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DeleteCurrentResourceFromView();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteCurrentResourceFromViewResponse <em>Delete Current Resource From View Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Current Resource From View Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getDeleteCurrentResourceFromViewResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DeleteCurrentResourceFromViewResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteOrgEntityConfigAttributes <em>Delete Org Entity Config Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Org Entity Config Attributes</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getDeleteOrgEntityConfigAttributes()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DeleteOrgEntityConfigAttributes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteOrgEntityConfigAttributesResponse <em>Delete Org Entity Config Attributes Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Org Entity Config Attributes Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getDeleteOrgEntityConfigAttributesResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DeleteOrgEntityConfigAttributesResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteWorkListView <em>Delete Work List View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Work List View</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getDeleteWorkListView()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DeleteWorkListView();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteWorkListViewResponse <em>Delete Work List View Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Delete Work List View Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getDeleteWorkListViewResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DeleteWorkListViewResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getEditWorkListView <em>Edit Work List View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Edit Work List View</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getEditWorkListView()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_EditWorkListView();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getEditWorkListViewResponse <em>Edit Work List View Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Edit Work List View Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getEditWorkListViewResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_EditWorkListViewResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getEnableWorkItem <em>Enable Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Enable Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getEnableWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_EnableWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getEnableWorkItemResponse <em>Enable Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Enable Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getEnableWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_EnableWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getEndGroup <em>End Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>End Group</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getEndGroup()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_EndGroup();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getEndGroupResponse <em>End Group Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>End Group Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getEndGroupResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_EndGroupResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetAllocatedWorkListItems <em>Get Allocated Work List Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Allocated Work List Items</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetAllocatedWorkListItems()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetAllocatedWorkListItems();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchGroupIds <em>Get Batch Group Ids</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Batch Group Ids</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetBatchGroupIds()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetBatchGroupIds();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchGroupIdsResponse <em>Get Batch Group Ids Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Batch Group Ids Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetBatchGroupIdsResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetBatchGroupIdsResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchWorkItemIds <em>Get Batch Work Item Ids</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Batch Work Item Ids</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetBatchWorkItemIds()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetBatchWorkItemIds();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchWorkItemIdsResponse <em>Get Batch Work Item Ids Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Batch Work Item Ids Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetBatchWorkItemIdsResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetBatchWorkItemIdsResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetEditableWorkListViews <em>Get Editable Work List Views</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Editable Work List Views</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetEditableWorkListViews()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetEditableWorkListViews();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetEditableWorkListViewsResponse <em>Get Editable Work List Views Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Editable Work List Views Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetEditableWorkListViewsResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetEditableWorkListViewsResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOfferSet <em>Get Offer Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Offer Set</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetOfferSet()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetOfferSet();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOfferSetResponse <em>Get Offer Set Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Offer Set Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetOfferSetResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetOfferSetResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributes <em>Get Org Entity Config Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Org Entity Config Attributes</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributes()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetOrgEntityConfigAttributes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesAvailable <em>Get Org Entity Config Attributes Available</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Org Entity Config Attributes Available</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesAvailable()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetOrgEntityConfigAttributesAvailable();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesAvailableResponse <em>Get Org Entity Config Attributes Available Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Org Entity Config Attributes Available Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesAvailableResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetOrgEntityConfigAttributesAvailableResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesResponse <em>Get Org Entity Config Attributes Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Org Entity Config Attributes Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetOrgEntityConfigAttributesResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetPublicWorkListViews <em>Get Public Work List Views</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Public Work List Views</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetPublicWorkListViews()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetPublicWorkListViews();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetPublicWorkListViewsResponse <em>Get Public Work List Views Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Public Work List Views Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetPublicWorkListViewsResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetPublicWorkListViewsResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetResourceOrderFilterCriteria <em>Get Resource Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Resource Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetResourceOrderFilterCriteria()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetResourceOrderFilterCriteria();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetResourceOrderFilterCriteriaResponse <em>Get Resource Order Filter Criteria Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Resource Order Filter Criteria Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetResourceOrderFilterCriteriaResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetResourceOrderFilterCriteriaResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetViewsForResource <em>Get Views For Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Views For Resource</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetViewsForResource()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetViewsForResource();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetViewsForResourceResponse <em>Get Views For Resource Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Views For Resource Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetViewsForResourceResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetViewsForResourceResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemHeader <em>Get Work Item Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Item Header</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemHeader()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkItemHeader();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemHeaderResponse <em>Get Work Item Header Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Item Header Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemHeaderResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkItemHeaderResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemOrderFilter <em>Get Work Item Order Filter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Item Order Filter</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemOrderFilter()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkItemOrderFilter();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemOrderFilterResponse <em>Get Work Item Order Filter Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Item Order Filter Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemOrderFilterResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkItemOrderFilterResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItems <em>Get Work List Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work List Items</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItems()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkListItems();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForGlobalData <em>Get Work List Items For Global Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work List Items For Global Data</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForGlobalData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkListItemsForGlobalData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForGlobalDataResponse <em>Get Work List Items For Global Data Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work List Items For Global Data Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForGlobalDataResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkListItemsForGlobalDataResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForView <em>Get Work List Items For View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work List Items For View</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForView()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkListItemsForView();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForViewResponse <em>Get Work List Items For View Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work List Items For View Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForViewResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkListItemsForViewResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsResponse <em>Get Work List Items Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work List Items Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkListItemsResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListViewDetails <em>Get Work List View Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work List View Details</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkListViewDetails()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkListViewDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListViewDetailsResponse <em>Get Work List View Details Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work List View Details Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkListViewDetailsResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkListViewDetailsResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModel <em>Get Work Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Model</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkModel()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkModel();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModelResponse <em>Get Work Model Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Model Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkModelResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkModelResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModels <em>Get Work Models</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Models</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkModels()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkModels();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModelsResponse <em>Get Work Models Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Models Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkModelsResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkModelsResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkType <em>Get Work Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Type</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkType()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypeResponse <em>Get Work Type Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Type Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypeResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkTypeResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypes <em>Get Work Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Types</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypes()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkTypes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypesResponse <em>Get Work Types Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Get Work Types Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypesResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_GetWorkTypesResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getOnNotification <em>On Notification</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>On Notification</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getOnNotification()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_OnNotification();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getOnNotificationResponse <em>On Notification Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>On Notification Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getOnNotificationResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_OnNotificationResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getOpenWorkItem <em>Open Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Open Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getOpenWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_OpenWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getOpenWorkItemResponse <em>Open Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Open Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getOpenWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_OpenWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getPendWorkItem <em>Pend Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Pend Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getPendWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_PendWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getPendWorkItemResponse <em>Pend Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Pend Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getPendWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_PendWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getPreviewWorkItemFromList <em>Preview Work Item From List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Preview Work Item From List</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getPreviewWorkItemFromList()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_PreviewWorkItemFromList();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getPreviewWorkItemFromListResponse <em>Preview Work Item From List Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Preview Work Item From List Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getPreviewWorkItemFromListResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_PreviewWorkItemFromListResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getPushNotification <em>Push Notification</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Push Notification</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getPushNotification()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_PushNotification();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItem <em>Reallocate Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reallocate Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ReallocateWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemData <em>Reallocate Work Item Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reallocate Work Item Data</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ReallocateWorkItemData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemDataResponse <em>Reallocate Work Item Data Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reallocate Work Item Data Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemDataResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ReallocateWorkItemDataResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemResponse <em>Reallocate Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reallocate Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ReallocateWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getRescheduleWorkItem <em>Reschedule Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reschedule Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getRescheduleWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_RescheduleWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getRescheduleWorkItemResponse <em>Reschedule Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Reschedule Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getRescheduleWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_RescheduleWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getResumeWorkItem <em>Resume Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Resume Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getResumeWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ResumeWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getResumeWorkItemResponse <em>Resume Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Resume Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getResumeWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ResumeWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSaveOpenWorkItem <em>Save Open Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Save Open Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSaveOpenWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SaveOpenWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSaveOpenWorkItemResponse <em>Save Open Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Save Open Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSaveOpenWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SaveOpenWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItem <em>Schedule Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schedule Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ScheduleWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemResponse <em>Schedule Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schedule Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ScheduleWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemWithModel <em>Schedule Work Item With Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schedule Work Item With Model</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemWithModel()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ScheduleWorkItemWithModel();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemWithModelResponse <em>Schedule Work Item With Model Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Schedule Work Item With Model Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemWithModelResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ScheduleWorkItemWithModelResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSetOrgEntityConfigAttributes <em>Set Org Entity Config Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Set Org Entity Config Attributes</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSetOrgEntityConfigAttributes()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SetOrgEntityConfigAttributes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSetOrgEntityConfigAttributesResponse <em>Set Org Entity Config Attributes Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Set Org Entity Config Attributes Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSetOrgEntityConfigAttributesResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SetOrgEntityConfigAttributesResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSetResourceOrderFilterCriteria <em>Set Resource Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Set Resource Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSetResourceOrderFilterCriteria()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SetResourceOrderFilterCriteria();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSetResourceOrderFilterCriteriaResponse <em>Set Resource Order Filter Criteria Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Set Resource Order Filter Criteria Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSetResourceOrderFilterCriteriaResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SetResourceOrderFilterCriteriaResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSetWorkItemPriority <em>Set Work Item Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Set Work Item Priority</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSetWorkItemPriority()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SetWorkItemPriority();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSetWorkItemPriorityResponse <em>Set Work Item Priority Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Set Work Item Priority Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSetWorkItemPriorityResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SetWorkItemPriorityResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSkipWorkItem <em>Skip Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Skip Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSkipWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SkipWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSkipWorkItemResponse <em>Skip Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Skip Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSkipWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SkipWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getStartGroup <em>Start Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Start Group</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getStartGroup()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_StartGroup();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getStartGroupResponse <em>Start Group Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Start Group Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getStartGroupResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_StartGroupResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSuspendWorkItem <em>Suspend Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Suspend Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSuspendWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SuspendWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getSuspendWorkItemResponse <em>Suspend Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Suspend Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getSuspendWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SuspendWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getUnallocateWorkItem <em>Unallocate Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Unallocate Work Item</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getUnallocateWorkItem()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_UnallocateWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getUnallocateWorkItemResponse <em>Unallocate Work Item Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Unallocate Work Item Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getUnallocateWorkItemResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_UnallocateWorkItemResponse();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getUnlockWorkListView <em>Unlock Work List View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Unlock Work List View</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getUnlockWorkListView()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_UnlockWorkListView();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.DocumentRoot#getUnlockWorkListViewResponse <em>Unlock Work List View Response</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Unlock Work List View Response</em>'.
     * @see com.tibco.n2.brm.api.DocumentRoot#getUnlockWorkListViewResponse()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_UnlockWorkListViewResponse();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.EditWorkListViewResponseType <em>Edit Work List View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Edit Work List View Response Type</em>'.
     * @see com.tibco.n2.brm.api.EditWorkListViewResponseType
     * @generated
     */
    EClass getEditWorkListViewResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.EditWorkListViewResponseType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.EditWorkListViewResponseType#getWorkListViewID()
     * @see #getEditWorkListViewResponseType()
     * @generated
     */
    EAttribute getEditWorkListViewResponseType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.EditWorkListViewType <em>Edit Work List View Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Edit Work List View Type</em>'.
     * @see com.tibco.n2.brm.api.EditWorkListViewType
     * @generated
     */
    EClass getEditWorkListViewType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.EditWorkListViewType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.EditWorkListViewType#getWorkListViewID()
     * @see #getEditWorkListViewType()
     * @generated
     */
    EAttribute getEditWorkListViewType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.EnableWorkItemResponseType <em>Enable Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enable Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.EnableWorkItemResponseType
     * @generated
     */
    EClass getEnableWorkItemResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.EnableWorkItemResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.EnableWorkItemResponseType#isSuccess()
     * @see #getEnableWorkItemResponseType()
     * @generated
     */
    EAttribute getEnableWorkItemResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.EnableWorkItemType <em>Enable Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enable Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.EnableWorkItemType
     * @generated
     */
    EClass getEnableWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.EnableWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.EnableWorkItemType#getWorkItemID()
     * @see #getEnableWorkItemType()
     * @generated
     */
    EReference getEnableWorkItemType_WorkItemID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.EnableWorkItemType#getItemBody <em>Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Body</em>'.
     * @see com.tibco.n2.brm.api.EnableWorkItemType#getItemBody()
     * @see #getEnableWorkItemType()
     * @generated
     */
    EReference getEnableWorkItemType_ItemBody();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.EndGroupResponseType <em>End Group Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>End Group Response Type</em>'.
     * @see com.tibco.n2.brm.api.EndGroupResponseType
     * @generated
     */
    EClass getEndGroupResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.EndGroupResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.EndGroupResponseType#isSuccess()
     * @see #getEndGroupResponseType()
     * @generated
     */
    EAttribute getEndGroupResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.EndGroupType <em>End Group Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>End Group Type</em>'.
     * @see com.tibco.n2.brm.api.EndGroupType
     * @generated
     */
    EClass getEndGroupType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.EndGroupType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.EndGroupType#getGroupID()
     * @see #getEndGroupType()
     * @generated
     */
    EAttribute getEndGroupType_GroupID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType <em>Get Allocated Work List Items Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Allocated Work List Items Type</em>'.
     * @see com.tibco.n2.brm.api.GetAllocatedWorkListItemsType
     * @generated
     */
    EClass getGetAllocatedWorkListItemsType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getEntityID <em>Entity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Entity ID</em>'.
     * @see com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getEntityID()
     * @see #getGetAllocatedWorkListItemsType()
     * @generated
     */
    EReference getGetAllocatedWorkListItemsType_EntityID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getOrderFilterCriteria <em>Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getOrderFilterCriteria()
     * @see #getGetAllocatedWorkListItemsType()
     * @generated
     */
    EReference getGetAllocatedWorkListItemsType_OrderFilterCriteria();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#isGetTotalCount <em>Get Total Count</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Get Total Count</em>'.
     * @see com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#isGetTotalCount()
     * @see #getGetAllocatedWorkListItemsType()
     * @generated
     */
    EAttribute getGetAllocatedWorkListItemsType_GetTotalCount();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getNumberOfItems()
     * @see #getGetAllocatedWorkListItemsType()
     * @generated
     */
    EAttribute getGetAllocatedWorkListItemsType_NumberOfItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getStartPosition()
     * @see #getGetAllocatedWorkListItemsType()
     * @generated
     */
    EAttribute getGetAllocatedWorkListItemsType_StartPosition();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetBatchGroupIdsResponseType <em>Get Batch Group Ids Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Batch Group Ids Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetBatchGroupIdsResponseType
     * @generated
     */
    EClass getGetBatchGroupIdsResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetBatchGroupIdsResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.GetBatchGroupIdsResponseType#getGroup()
     * @see #getGetBatchGroupIdsResponseType()
     * @generated
     */
    EAttribute getGetBatchGroupIdsResponseType_Group();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetBatchGroupIdsResponseType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.GetBatchGroupIdsResponseType#getGroupID()
     * @see #getGetBatchGroupIdsResponseType()
     * @generated
     */
    EAttribute getGetBatchGroupIdsResponseType_GroupID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType <em>Get Batch Work Item Ids Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Batch Work Item Ids Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType
     * @generated
     */
    EClass getGetBatchWorkItemIdsResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType#getGroup()
     * @see #getGetBatchWorkItemIdsResponseType()
     * @generated
     */
    EAttribute getGetBatchWorkItemIdsResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType#getWorkItemID()
     * @see #getGetBatchWorkItemIdsResponseType()
     * @generated
     */
    EReference getGetBatchWorkItemIdsResponseType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType <em>Get Editable Work List Views Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Editable Work List Views Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType
     * @generated
     */
    EClass getGetEditableWorkListViewsResponseType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType#getWorkListViews <em>Work List Views</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work List Views</em>'.
     * @see com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType#getWorkListViews()
     * @see #getGetEditableWorkListViewsResponseType()
     * @generated
     */
    EReference getGetEditableWorkListViewsResponseType_WorkListViews();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetEditableWorkListViewsType <em>Get Editable Work List Views Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Editable Work List Views Type</em>'.
     * @see com.tibco.n2.brm.api.GetEditableWorkListViewsType
     * @generated
     */
    EClass getGetEditableWorkListViewsType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetEditableWorkListViewsType#getApiVersion <em>Api Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Api Version</em>'.
     * @see com.tibco.n2.brm.api.GetEditableWorkListViewsType#getApiVersion()
     * @see #getGetEditableWorkListViewsType()
     * @generated
     */
    EAttribute getGetEditableWorkListViewsType_ApiVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetEditableWorkListViewsType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetEditableWorkListViewsType#getNumberOfItems()
     * @see #getGetEditableWorkListViewsType()
     * @generated
     */
    EAttribute getGetEditableWorkListViewsType_NumberOfItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetEditableWorkListViewsType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetEditableWorkListViewsType#getStartPosition()
     * @see #getGetEditableWorkListViewsType()
     * @generated
     */
    EAttribute getGetEditableWorkListViewsType_StartPosition();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetOfferSetResponseType <em>Get Offer Set Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Offer Set Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetOfferSetResponseType
     * @generated
     */
    EClass getGetOfferSetResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetOfferSetResponseType#getEntityGuid <em>Entity Guid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Entity Guid</em>'.
     * @see com.tibco.n2.brm.api.GetOfferSetResponseType#getEntityGuid()
     * @see #getGetOfferSetResponseType()
     * @generated
     */
    EAttribute getGetOfferSetResponseType_EntityGuid();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetOfferSetResponseType#getEntityId <em>Entity Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Entity Id</em>'.
     * @see com.tibco.n2.brm.api.GetOfferSetResponseType#getEntityId()
     * @see #getGetOfferSetResponseType()
     * @generated
     */
    EReference getGetOfferSetResponseType_EntityId();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetOfferSetType <em>Get Offer Set Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Offer Set Type</em>'.
     * @see com.tibco.n2.brm.api.GetOfferSetType
     * @generated
     */
    EClass getGetOfferSetType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetOfferSetType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.GetOfferSetType#getWorkItemID()
     * @see #getGetOfferSetType()
     * @generated
     */
    EReference getGetOfferSetType_WorkItemID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetOfferSetType#getApiVersion <em>Api Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Api Version</em>'.
     * @see com.tibco.n2.brm.api.GetOfferSetType#getApiVersion()
     * @see #getGetOfferSetType()
     * @generated
     */
    EAttribute getGetOfferSetType_ApiVersion();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType <em>Get Org Entity Config Attributes Available Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Org Entity Config Attributes Available Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType
     * @generated
     */
    EClass getGetOrgEntityConfigAttributesAvailableResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType#getGroup()
     * @see #getGetOrgEntityConfigAttributesAvailableResponseType()
     * @generated
     */
    EAttribute getGetOrgEntityConfigAttributesAvailableResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType#getOrgEntityConfigAttributesAvailable <em>Org Entity Config Attributes Available</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Entity Config Attributes Available</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType#getOrgEntityConfigAttributesAvailable()
     * @see #getGetOrgEntityConfigAttributesAvailableResponseType()
     * @generated
     */
    EReference getGetOrgEntityConfigAttributesAvailableResponseType_OrgEntityConfigAttributesAvailable();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType <em>Get Org Entity Config Attributes Available Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Org Entity Config Attributes Available Type</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType
     * @generated
     */
    EClass getGetOrgEntityConfigAttributesAvailableType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getStartAt <em>Start At</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start At</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getStartAt()
     * @see #getGetOrgEntityConfigAttributesAvailableType()
     * @generated
     */
    EAttribute getGetOrgEntityConfigAttributesAvailableType_StartAt();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getNumToReturn <em>Num To Return</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Num To Return</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType#getNumToReturn()
     * @see #getGetOrgEntityConfigAttributesAvailableType()
     * @generated
     */
    EAttribute getGetOrgEntityConfigAttributesAvailableType_NumToReturn();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType <em>Get Org Entity Config Attributes Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Org Entity Config Attributes Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType
     * @generated
     */
    EClass getGetOrgEntityConfigAttributesResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType#getGroup()
     * @see #getGetOrgEntityConfigAttributesResponseType()
     * @generated
     */
    EAttribute getGetOrgEntityConfigAttributesResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType#getOrgEntityConfigAttribute <em>Org Entity Config Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Entity Config Attribute</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType#getOrgEntityConfigAttribute()
     * @see #getGetOrgEntityConfigAttributesResponseType()
     * @generated
     */
    EReference getGetOrgEntityConfigAttributesResponseType_OrgEntityConfigAttribute();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType <em>Get Org Entity Config Attributes Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Org Entity Config Attributes Type</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType
     * @generated
     */
    EClass getGetOrgEntityConfigAttributesType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource</em>'.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType#getResource()
     * @see #getGetOrgEntityConfigAttributesType()
     * @generated
     */
    EAttribute getGetOrgEntityConfigAttributesType_Resource();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetPublicWorkListViewsResponseType <em>Get Public Work List Views Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Public Work List Views Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetPublicWorkListViewsResponseType
     * @generated
     */
    EClass getGetPublicWorkListViewsResponseType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetPublicWorkListViewsResponseType#getWorkListViews <em>Work List Views</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work List Views</em>'.
     * @see com.tibco.n2.brm.api.GetPublicWorkListViewsResponseType#getWorkListViews()
     * @see #getGetPublicWorkListViewsResponseType()
     * @generated
     */
    EReference getGetPublicWorkListViewsResponseType_WorkListViews();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetPublicWorkListViewsType <em>Get Public Work List Views Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Public Work List Views Type</em>'.
     * @see com.tibco.n2.brm.api.GetPublicWorkListViewsType
     * @generated
     */
    EClass getGetPublicWorkListViewsType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetPublicWorkListViewsType#getApiVersion <em>Api Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Api Version</em>'.
     * @see com.tibco.n2.brm.api.GetPublicWorkListViewsType#getApiVersion()
     * @see #getGetPublicWorkListViewsType()
     * @generated
     */
    EAttribute getGetPublicWorkListViewsType_ApiVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetPublicWorkListViewsType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetPublicWorkListViewsType#getNumberOfItems()
     * @see #getGetPublicWorkListViewsType()
     * @generated
     */
    EAttribute getGetPublicWorkListViewsType_NumberOfItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetPublicWorkListViewsType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetPublicWorkListViewsType#getStartPosition()
     * @see #getGetPublicWorkListViewsType()
     * @generated
     */
    EAttribute getGetPublicWorkListViewsType_StartPosition();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType <em>Get Resource Order Filter Criteria Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Resource Order Filter Criteria Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType
     * @generated
     */
    EClass getGetResourceOrderFilterCriteriaResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType#getOrderFilterCriteria <em>Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType#getOrderFilterCriteria()
     * @see #getGetResourceOrderFilterCriteriaResponseType()
     * @generated
     */
    EReference getGetResourceOrderFilterCriteriaResponseType_OrderFilterCriteria();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType <em>Get Resource Order Filter Criteria Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Resource Order Filter Criteria Type</em>'.
     * @see com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType
     * @generated
     */
    EClass getGetResourceOrderFilterCriteriaType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType#getResourceID <em>Resource ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource ID</em>'.
     * @see com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType#getResourceID()
     * @see #getGetResourceOrderFilterCriteriaType()
     * @generated
     */
    EAttribute getGetResourceOrderFilterCriteriaType_ResourceID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetViewsForResourceResponseType <em>Get Views For Resource Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Views For Resource Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetViewsForResourceResponseType
     * @generated
     */
    EClass getGetViewsForResourceResponseType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetViewsForResourceResponseType#getWorkListViews <em>Work List Views</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work List Views</em>'.
     * @see com.tibco.n2.brm.api.GetViewsForResourceResponseType#getWorkListViews()
     * @see #getGetViewsForResourceResponseType()
     * @generated
     */
    EReference getGetViewsForResourceResponseType_WorkListViews();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetViewsForResourceType <em>Get Views For Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Views For Resource Type</em>'.
     * @see com.tibco.n2.brm.api.GetViewsForResourceType
     * @generated
     */
    EClass getGetViewsForResourceType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getApiVersion <em>Api Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Api Version</em>'.
     * @see com.tibco.n2.brm.api.GetViewsForResourceType#getApiVersion()
     * @see #getGetViewsForResourceType()
     * @generated
     */
    EAttribute getGetViewsForResourceType_ApiVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetViewsForResourceType#getNumberOfItems()
     * @see #getGetViewsForResourceType()
     * @generated
     */
    EAttribute getGetViewsForResourceType_NumberOfItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetViewsForResourceType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetViewsForResourceType#getStartPosition()
     * @see #getGetViewsForResourceType()
     * @generated
     */
    EAttribute getGetViewsForResourceType_StartPosition();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkItemHeaderResponseType <em>Get Work Item Header Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Item Header Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemHeaderResponseType
     * @generated
     */
    EClass getGetWorkItemHeaderResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetWorkItemHeaderResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemHeaderResponseType#getGroup()
     * @see #getGetWorkItemHeaderResponseType()
     * @generated
     */
    EAttribute getGetWorkItemHeaderResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetWorkItemHeaderResponseType#getWorkItemHeader <em>Work Item Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item Header</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemHeaderResponseType#getWorkItemHeader()
     * @see #getGetWorkItemHeaderResponseType()
     * @generated
     */
    EReference getGetWorkItemHeaderResponseType_WorkItemHeader();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkItemHeaderType <em>Get Work Item Header Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Item Header Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemHeaderType
     * @generated
     */
    EClass getGetWorkItemHeaderType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetWorkItemHeaderType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemHeaderType#getGroup()
     * @see #getGetWorkItemHeaderType()
     * @generated
     */
    EAttribute getGetWorkItemHeaderType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetWorkItemHeaderType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemHeaderType#getWorkItemID()
     * @see #getGetWorkItemHeaderType()
     * @generated
     */
    EReference getGetWorkItemHeaderType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType <em>Get Work Item Order Filter Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Item Order Filter Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType
     * @generated
     */
    EClass getGetWorkItemOrderFilterResponseType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType#getColumnData <em>Column Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Column Data</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType#getColumnData()
     * @see #getGetWorkItemOrderFilterResponseType()
     * @generated
     */
    EReference getGetWorkItemOrderFilterResponseType_ColumnData();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterType <em>Get Work Item Order Filter Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Item Order Filter Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemOrderFilterType
     * @generated
     */
    EClass getGetWorkItemOrderFilterType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterType#getLimitColumns <em>Limit Columns</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Limit Columns</em>'.
     * @see com.tibco.n2.brm.api.GetWorkItemOrderFilterType#getLimitColumns()
     * @see #getGetWorkItemOrderFilterType()
     * @generated
     */
    EAttribute getGetWorkItemOrderFilterType_LimitColumns();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType <em>Get Work List Items For Global Data Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work List Items For Global Data Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType
     * @generated
     */
    EClass getGetWorkListItemsForGlobalDataResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType#getStartPosition()
     * @see #getGetWorkListItemsForGlobalDataResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsForGlobalDataResponseType_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType#getEndPosition <em>End Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType#getEndPosition()
     * @see #getGetWorkListItemsForGlobalDataResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsForGlobalDataResponseType_EndPosition();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType#getWorkItems <em>Work Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType#getWorkItems()
     * @see #getGetWorkListItemsForGlobalDataResponseType()
     * @generated
     */
    EReference getGetWorkListItemsForGlobalDataResponseType_WorkItems();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType <em>Get Work List Items For Global Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work List Items For Global Data Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType
     * @generated
     */
    EClass getGetWorkListItemsForGlobalDataType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getGlobalDataRef <em>Global Data Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Global Data Ref</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getGlobalDataRef()
     * @see #getGetWorkListItemsForGlobalDataType()
     * @generated
     */
    EAttribute getGetWorkListItemsForGlobalDataType_GlobalDataRef();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getOrderFilterCriteria <em>Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getOrderFilterCriteria()
     * @see #getGetWorkListItemsForGlobalDataType()
     * @generated
     */
    EReference getGetWorkListItemsForGlobalDataType_OrderFilterCriteria();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getNumberOfItems()
     * @see #getGetWorkListItemsForGlobalDataType()
     * @generated
     */
    EAttribute getGetWorkListItemsForGlobalDataType_NumberOfItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getStartPosition()
     * @see #getGetWorkListItemsForGlobalDataType()
     * @generated
     */
    EAttribute getGetWorkListItemsForGlobalDataType_StartPosition();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType <em>Get Work List Items For View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work List Items For View Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType
     * @generated
     */
    EClass getGetWorkListItemsForViewResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getStartPosition()
     * @see #getGetWorkListItemsForViewResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewResponseType_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getEndPosition <em>End Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getEndPosition()
     * @see #getGetWorkListItemsForViewResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewResponseType_EndPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getTotalItems <em>Total Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Total Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getTotalItems()
     * @see #getGetWorkListItemsForViewResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewResponseType_TotalItems();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getWorkItems <em>Work Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getWorkItems()
     * @see #getGetWorkListItemsForViewResponseType()
     * @generated
     */
    EReference getGetWorkListItemsForViewResponseType_WorkItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getCustomData <em>Custom Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Custom Data</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType#getCustomData()
     * @see #getGetWorkListItemsForViewResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewResponseType_CustomData();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType <em>Get Work List Items For View Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work List Items For View Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewType
     * @generated
     */
    EClass getGetWorkListItemsForViewType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetAllocatedItems <em>Get Allocated Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Get Allocated Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetAllocatedItems()
     * @see #getGetWorkListItemsForViewType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewType_GetAllocatedItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetTotalCount <em>Get Total Count</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Get Total Count</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetTotalCount()
     * @see #getGetWorkListItemsForViewType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewType_GetTotalCount();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewType#getNumberOfItems()
     * @see #getGetWorkListItemsForViewType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewType_NumberOfItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewType#getStartPosition()
     * @see #getGetWorkListItemsForViewType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewType_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewType#getWorkListViewID()
     * @see #getGetWorkListItemsForViewType()
     * @generated
     */
    EAttribute getGetWorkListItemsForViewType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType <em>Get Work List Items Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work List Items Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType
     * @generated
     */
    EClass getGetWorkListItemsResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType#getStartPosition()
     * @see #getGetWorkListItemsResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsResponseType_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType#getEndPosition <em>End Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType#getEndPosition()
     * @see #getGetWorkListItemsResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsResponseType_EndPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType#getTotalItems <em>Total Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Total Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType#getTotalItems()
     * @see #getGetWorkListItemsResponseType()
     * @generated
     */
    EAttribute getGetWorkListItemsResponseType_TotalItems();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType#getWorkItems <em>Work Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType#getWorkItems()
     * @see #getGetWorkListItemsResponseType()
     * @generated
     */
    EReference getGetWorkListItemsResponseType_WorkItems();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType1 <em>Get Work List Items Response Type1</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work List Items Response Type1</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType1
     * @generated
     */
    EClass getGetWorkListItemsResponseType1();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType1#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType1#getStartPosition()
     * @see #getGetWorkListItemsResponseType1()
     * @generated
     */
    EAttribute getGetWorkListItemsResponseType1_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType1#getEndPosition <em>End Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType1#getEndPosition()
     * @see #getGetWorkListItemsResponseType1()
     * @generated
     */
    EAttribute getGetWorkListItemsResponseType1_EndPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType1#getTotalItems <em>Total Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Total Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType1#getTotalItems()
     * @see #getGetWorkListItemsResponseType1()
     * @generated
     */
    EAttribute getGetWorkListItemsResponseType1_TotalItems();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType1#getWorkItems <em>Work Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType1#getWorkItems()
     * @see #getGetWorkListItemsResponseType1()
     * @generated
     */
    EReference getGetWorkListItemsResponseType1_WorkItems();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkListItemsType <em>Get Work List Items Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work List Items Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsType
     * @generated
     */
    EClass getGetWorkListItemsType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsType#getResourcesRequired <em>Resources Required</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resources Required</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsType#getResourcesRequired()
     * @see #getGetWorkListItemsType()
     * @generated
     */
    EAttribute getGetWorkListItemsType_ResourcesRequired();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetWorkListItemsType#getEntityID <em>Entity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Entity ID</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsType#getEntityID()
     * @see #getGetWorkListItemsType()
     * @generated
     */
    EReference getGetWorkListItemsType_EntityID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetWorkListItemsType#getOrderFilterCriteria <em>Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsType#getOrderFilterCriteria()
     * @see #getGetWorkListItemsType()
     * @generated
     */
    EReference getGetWorkListItemsType_OrderFilterCriteria();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsType#isGetTotalCount <em>Get Total Count</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Get Total Count</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsType#isGetTotalCount()
     * @see #getGetWorkListItemsType()
     * @generated
     */
    EAttribute getGetWorkListItemsType_GetTotalCount();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsType#getNumberOfItems()
     * @see #getGetWorkListItemsType()
     * @generated
     */
    EAttribute getGetWorkListItemsType_NumberOfItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListItemsType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListItemsType#getStartPosition()
     * @see #getGetWorkListItemsType()
     * @generated
     */
    EAttribute getGetWorkListItemsType_StartPosition();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType <em>Get Work List View Details Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work List View Details Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListViewDetailsType
     * @generated
     */
    EClass getGetWorkListViewDetailsType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getApiVersion <em>Api Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Api Version</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListViewDetailsType#getApiVersion()
     * @see #getGetWorkListViewDetailsType()
     * @generated
     */
    EAttribute getGetWorkListViewDetailsType_ApiVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#isLockView <em>Lock View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lock View</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListViewDetailsType#isLockView()
     * @see #getGetWorkListViewDetailsType()
     * @generated
     */
    EAttribute getGetWorkListViewDetailsType_LockView();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.GetWorkListViewDetailsType#getWorkListViewID()
     * @see #getGetWorkListViewDetailsType()
     * @generated
     */
    EAttribute getGetWorkListViewDetailsType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkModelResponseType <em>Get Work Model Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Model Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelResponseType
     * @generated
     */
    EClass getGetWorkModelResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetWorkModelResponseType#getWorkModel <em>Work Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Model</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelResponseType#getWorkModel()
     * @see #getGetWorkModelResponseType()
     * @generated
     */
    EReference getGetWorkModelResponseType_WorkModel();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkModelsResponseType <em>Get Work Models Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Models Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelsResponseType
     * @generated
     */
    EClass getGetWorkModelsResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetWorkModelsResponseType#getWorkModelList <em>Work Model List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Model List</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelsResponseType#getWorkModelList()
     * @see #getGetWorkModelsResponseType()
     * @generated
     */
    EReference getGetWorkModelsResponseType_WorkModelList();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkModelsType <em>Get Work Models Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Models Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelsType
     * @generated
     */
    EClass getGetWorkModelsType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkModelsType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelsType#getStartPosition()
     * @see #getGetWorkModelsType()
     * @generated
     */
    EAttribute getGetWorkModelsType_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkModelsType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelsType#getNumberOfItems()
     * @see #getGetWorkModelsType()
     * @generated
     */
    EAttribute getGetWorkModelsType_NumberOfItems();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkModelType <em>Get Work Model Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Model Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelType
     * @generated
     */
    EClass getGetWorkModelType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkModelType#getWorkModelID <em>Work Model ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Model ID</em>'.
     * @see com.tibco.n2.brm.api.GetWorkModelType#getWorkModelID()
     * @see #getGetWorkModelType()
     * @generated
     */
    EAttribute getGetWorkModelType_WorkModelID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkTypeResponseType <em>Get Work Type Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Type Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypeResponseType
     * @generated
     */
    EClass getGetWorkTypeResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetWorkTypeResponseType#getWorkType <em>Work Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypeResponseType#getWorkType()
     * @see #getGetWorkTypeResponseType()
     * @generated
     */
    EReference getGetWorkTypeResponseType_WorkType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkTypesResponseType <em>Get Work Types Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Types Response Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypesResponseType
     * @generated
     */
    EClass getGetWorkTypesResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.GetWorkTypesResponseType#getWorkTypelList <em>Work Typel List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Typel List</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypesResponseType#getWorkTypelList()
     * @see #getGetWorkTypesResponseType()
     * @generated
     */
    EReference getGetWorkTypesResponseType_WorkTypelList();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkTypesType <em>Get Work Types Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Types Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypesType
     * @generated
     */
    EClass getGetWorkTypesType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkTypesType#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypesType#getStartPosition()
     * @see #getGetWorkTypesType()
     * @generated
     */
    EAttribute getGetWorkTypesType_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkTypesType#getNumberOfItems <em>Number Of Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Items</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypesType#getNumberOfItems()
     * @see #getGetWorkTypesType()
     * @generated
     */
    EAttribute getGetWorkTypesType_NumberOfItems();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.GetWorkTypeType <em>Get Work Type Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Get Work Type Type</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypeType
     * @generated
     */
    EClass getGetWorkTypeType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.GetWorkTypeType#getWorkTypeID <em>Work Type ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type ID</em>'.
     * @see com.tibco.n2.brm.api.GetWorkTypeType#getWorkTypeID()
     * @see #getGetWorkTypeType()
     * @generated
     */
    EAttribute getGetWorkTypeType_WorkTypeID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.HiddenPeriod <em>Hidden Period</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Hidden Period</em>'.
     * @see com.tibco.n2.brm.api.HiddenPeriod
     * @generated
     */
    EClass getHiddenPeriod();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.HiddenPeriod#getHiddenDuration <em>Hidden Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Hidden Duration</em>'.
     * @see com.tibco.n2.brm.api.HiddenPeriod#getHiddenDuration()
     * @see #getHiddenPeriod()
     * @generated
     */
    EReference getHiddenPeriod_HiddenDuration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.HiddenPeriod#getVisibleDate <em>Visible Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Visible Date</em>'.
     * @see com.tibco.n2.brm.api.HiddenPeriod#getVisibleDate()
     * @see #getHiddenPeriod()
     * @generated
     */
    EAttribute getHiddenPeriod_VisibleDate();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.Item <em>Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Item</em>'.
     * @see com.tibco.n2.brm.api.Item
     * @generated
     */
    EClass getItem();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.Item#getEntities <em>Entities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Entities</em>'.
     * @see com.tibco.n2.brm.api.Item#getEntities()
     * @see #getItem()
     * @generated
     */
    EReference getItem_Entities();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.Item#getEntityQuery <em>Entity Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Entity Query</em>'.
     * @see com.tibco.n2.brm.api.Item#getEntityQuery()
     * @see #getItem()
     * @generated
     */
    EReference getItem_EntityQuery();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.Item#getWorkTypeUID <em>Work Type UID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type UID</em>'.
     * @see com.tibco.n2.brm.api.Item#getWorkTypeUID()
     * @see #getItem()
     * @generated
     */
    EAttribute getItem_WorkTypeUID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.Item#getWorkTypeVersion <em>Work Type Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type Version</em>'.
     * @see com.tibco.n2.brm.api.Item#getWorkTypeVersion()
     * @see #getItem()
     * @generated
     */
    EAttribute getItem_WorkTypeVersion();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ItemBody <em>Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Item Body</em>'.
     * @see com.tibco.n2.brm.api.ItemBody
     * @generated
     */
    EClass getItemBody();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ItemBody#getParameter <em>Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Parameter</em>'.
     * @see com.tibco.n2.brm.api.ItemBody#getParameter()
     * @see #getItemBody()
     * @generated
     */
    EReference getItemBody_Parameter();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ItemContext <em>Item Context</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Item Context</em>'.
     * @see com.tibco.n2.brm.api.ItemContext
     * @generated
     */
    EClass getItemContext();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemContext#getActivityID <em>Activity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity ID</em>'.
     * @see com.tibco.n2.brm.api.ItemContext#getActivityID()
     * @see #getItemContext()
     * @generated
     */
    EAttribute getItemContext_ActivityID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemContext#getActivityName <em>Activity Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Name</em>'.
     * @see com.tibco.n2.brm.api.ItemContext#getActivityName()
     * @see #getItemContext()
     * @generated
     */
    EAttribute getItemContext_ActivityName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemContext#getAppInstance <em>App Instance</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>App Instance</em>'.
     * @see com.tibco.n2.brm.api.ItemContext#getAppInstance()
     * @see #getItemContext()
     * @generated
     */
    EAttribute getItemContext_AppInstance();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemContext#getAppName <em>App Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>App Name</em>'.
     * @see com.tibco.n2.brm.api.ItemContext#getAppName()
     * @see #getItemContext()
     * @generated
     */
    EAttribute getItemContext_AppName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemContext#getAppID <em>App ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>App ID</em>'.
     * @see com.tibco.n2.brm.api.ItemContext#getAppID()
     * @see #getItemContext()
     * @generated
     */
    EAttribute getItemContext_AppID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemContext#getAppInstanceDescription <em>App Instance Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>App Instance Description</em>'.
     * @see com.tibco.n2.brm.api.ItemContext#getAppInstanceDescription()
     * @see #getItemContext()
     * @generated
     */
    EAttribute getItemContext_AppInstanceDescription();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ItemDuration <em>Item Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Item Duration</em>'.
     * @see com.tibco.n2.brm.api.ItemDuration
     * @generated
     */
    EClass getItemDuration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemDuration#getDays <em>Days</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Days</em>'.
     * @see com.tibco.n2.brm.api.ItemDuration#getDays()
     * @see #getItemDuration()
     * @generated
     */
    EAttribute getItemDuration_Days();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemDuration#getHours <em>Hours</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Hours</em>'.
     * @see com.tibco.n2.brm.api.ItemDuration#getHours()
     * @see #getItemDuration()
     * @generated
     */
    EAttribute getItemDuration_Hours();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemDuration#getMinutes <em>Minutes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Minutes</em>'.
     * @see com.tibco.n2.brm.api.ItemDuration#getMinutes()
     * @see #getItemDuration()
     * @generated
     */
    EAttribute getItemDuration_Minutes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemDuration#getMonths <em>Months</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Months</em>'.
     * @see com.tibco.n2.brm.api.ItemDuration#getMonths()
     * @see #getItemDuration()
     * @generated
     */
    EAttribute getItemDuration_Months();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemDuration#getWeeks <em>Weeks</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Weeks</em>'.
     * @see com.tibco.n2.brm.api.ItemDuration#getWeeks()
     * @see #getItemDuration()
     * @generated
     */
    EAttribute getItemDuration_Weeks();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemDuration#getYears <em>Years</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Years</em>'.
     * @see com.tibco.n2.brm.api.ItemDuration#getYears()
     * @see #getItemDuration()
     * @generated
     */
    EAttribute getItemDuration_Years();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ItemPrivilege <em>Item Privilege</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Item Privilege</em>'.
     * @see com.tibco.n2.brm.api.ItemPrivilege
     * @generated
     */
    EClass getItemPrivilege();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ItemPrivilege#getSuspend <em>Suspend</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Suspend</em>'.
     * @see com.tibco.n2.brm.api.ItemPrivilege#getSuspend()
     * @see #getItemPrivilege()
     * @generated
     */
    EReference getItemPrivilege_Suspend();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ItemPrivilege#getStatelessRealloc <em>Stateless Realloc</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Stateless Realloc</em>'.
     * @see com.tibco.n2.brm.api.ItemPrivilege#getStatelessRealloc()
     * @see #getItemPrivilege()
     * @generated
     */
    EReference getItemPrivilege_StatelessRealloc();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ItemPrivilege#getStatefulRealloc <em>Stateful Realloc</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Stateful Realloc</em>'.
     * @see com.tibco.n2.brm.api.ItemPrivilege#getStatefulRealloc()
     * @see #getItemPrivilege()
     * @generated
     */
    EReference getItemPrivilege_StatefulRealloc();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ItemPrivilege#getDellocate <em>Dellocate</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Dellocate</em>'.
     * @see com.tibco.n2.brm.api.ItemPrivilege#getDellocate()
     * @see #getItemPrivilege()
     * @generated
     */
    EReference getItemPrivilege_Dellocate();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ItemPrivilege#getDelegate <em>Delegate</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Delegate</em>'.
     * @see com.tibco.n2.brm.api.ItemPrivilege#getDelegate()
     * @see #getItemPrivilege()
     * @generated
     */
    EReference getItemPrivilege_Delegate();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ItemPrivilege#getSkip <em>Skip</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Skip</em>'.
     * @see com.tibco.n2.brm.api.ItemPrivilege#getSkip()
     * @see #getItemPrivilege()
     * @generated
     */
    EReference getItemPrivilege_Skip();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ItemPrivilege#getPiling <em>Piling</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Piling</em>'.
     * @see com.tibco.n2.brm.api.ItemPrivilege#getPiling()
     * @see #getItemPrivilege()
     * @generated
     */
    EReference getItemPrivilege_Piling();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ItemSchedule <em>Item Schedule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Item Schedule</em>'.
     * @see com.tibco.n2.brm.api.ItemSchedule
     * @generated
     */
    EClass getItemSchedule();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemSchedule#getStartDate <em>Start Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Date</em>'.
     * @see com.tibco.n2.brm.api.ItemSchedule#getStartDate()
     * @see #getItemSchedule()
     * @generated
     */
    EAttribute getItemSchedule_StartDate();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ItemSchedule#getMaxDuration <em>Max Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Max Duration</em>'.
     * @see com.tibco.n2.brm.api.ItemSchedule#getMaxDuration()
     * @see #getItemSchedule()
     * @generated
     */
    EReference getItemSchedule_MaxDuration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ItemSchedule#getTargetDate <em>Target Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target Date</em>'.
     * @see com.tibco.n2.brm.api.ItemSchedule#getTargetDate()
     * @see #getItemSchedule()
     * @generated
     */
    EAttribute getItemSchedule_TargetDate();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ManagedObjectID <em>Managed Object ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Managed Object ID</em>'.
     * @see com.tibco.n2.brm.api.ManagedObjectID
     * @generated
     */
    EClass getManagedObjectID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ManagedObjectID#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.brm.api.ManagedObjectID#getVersion()
     * @see #getManagedObjectID()
     * @generated
     */
    EAttribute getManagedObjectID_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.NextWorkItem <em>Next Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Next Work Item</em>'.
     * @see com.tibco.n2.brm.api.NextWorkItem
     * @generated
     */
    EClass getNextWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.NextWorkItem#getNextItem <em>Next Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Next Item</em>'.
     * @see com.tibco.n2.brm.api.NextWorkItem#getNextItem()
     * @see #getNextWorkItem()
     * @generated
     */
    EReference getNextWorkItem_NextItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ObjectID <em>Object ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Object ID</em>'.
     * @see com.tibco.n2.brm.api.ObjectID
     * @generated
     */
    EClass getObjectID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ObjectID#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.n2.brm.api.ObjectID#getId()
     * @see #getObjectID()
     * @generated
     */
    EAttribute getObjectID_Id();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.OnNotificationResponseType <em>On Notification Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>On Notification Response Type</em>'.
     * @see com.tibco.n2.brm.api.OnNotificationResponseType
     * @generated
     */
    EClass getOnNotificationResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OnNotificationResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.OnNotificationResponseType#isSuccess()
     * @see #getOnNotificationResponseType()
     * @generated
     */
    EAttribute getOnNotificationResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.OnNotificationType <em>On Notification Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>On Notification Type</em>'.
     * @see com.tibco.n2.brm.api.OnNotificationType
     * @generated
     */
    EClass getOnNotificationType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.OnNotificationType#getMessageDetails <em>Message Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Message Details</em>'.
     * @see com.tibco.n2.brm.api.OnNotificationType#getMessageDetails()
     * @see #getOnNotificationType()
     * @generated
     */
    EReference getOnNotificationType_MessageDetails();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.OnNotificationType#getItemBody <em>Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Body</em>'.
     * @see com.tibco.n2.brm.api.OnNotificationType#getItemBody()
     * @see #getOnNotificationType()
     * @generated
     */
    EReference getOnNotificationType_ItemBody();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.OnNotificationType#getAllocationHistory <em>Allocation History</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Allocation History</em>'.
     * @see com.tibco.n2.brm.api.OnNotificationType#getAllocationHistory()
     * @see #getOnNotificationType()
     * @generated
     */
    EReference getOnNotificationType_AllocationHistory();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.OpenWorkItemResponseType <em>Open Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Open Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.OpenWorkItemResponseType
     * @generated
     */
    EClass getOpenWorkItemResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.OpenWorkItemResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.OpenWorkItemResponseType#getGroup()
     * @see #getOpenWorkItemResponseType()
     * @generated
     */
    EAttribute getOpenWorkItemResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.OpenWorkItemResponseType#getWorkItemBody <em>Work Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item Body</em>'.
     * @see com.tibco.n2.brm.api.OpenWorkItemResponseType#getWorkItemBody()
     * @see #getOpenWorkItemResponseType()
     * @generated
     */
    EReference getOpenWorkItemResponseType_WorkItemBody();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.OpenWorkItemType <em>Open Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Open Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.OpenWorkItemType
     * @generated
     */
    EClass getOpenWorkItemType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.OpenWorkItemType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.OpenWorkItemType#getGroup()
     * @see #getOpenWorkItemType()
     * @generated
     */
    EAttribute getOpenWorkItemType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.OpenWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.OpenWorkItemType#getWorkItemID()
     * @see #getOpenWorkItemType()
     * @generated
     */
    EReference getOpenWorkItemType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.OrderFilterCriteria <em>Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.OrderFilterCriteria
     * @generated
     */
    EClass getOrderFilterCriteria();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrderFilterCriteria#getOrder <em>Order</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Order</em>'.
     * @see com.tibco.n2.brm.api.OrderFilterCriteria#getOrder()
     * @see #getOrderFilterCriteria()
     * @generated
     */
    EAttribute getOrderFilterCriteria_Order();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrderFilterCriteria#getFilter <em>Filter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Filter</em>'.
     * @see com.tibco.n2.brm.api.OrderFilterCriteria#getFilter()
     * @see #getOrderFilterCriteria()
     * @generated
     */
    EAttribute getOrderFilterCriteria_Filter();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.OrgEntityConfigAttribute <em>Org Entity Config Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Entity Config Attribute</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttribute
     * @generated
     */
    EClass getOrgEntityConfigAttribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrgEntityConfigAttribute#getAttributeName <em>Attribute Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Name</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttribute#getAttributeName()
     * @see #getOrgEntityConfigAttribute()
     * @generated
     */
    EAttribute getOrgEntityConfigAttribute_AttributeName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrgEntityConfigAttribute#getAttributeValue <em>Attribute Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Value</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttribute#getAttributeValue()
     * @see #getOrgEntityConfigAttribute()
     * @generated
     */
    EAttribute getOrgEntityConfigAttribute_AttributeValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrgEntityConfigAttribute#isReadOnly <em>Read Only</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Read Only</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttribute#isReadOnly()
     * @see #getOrgEntityConfigAttribute()
     * @generated
     */
    EAttribute getOrgEntityConfigAttribute_ReadOnly();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable <em>Org Entity Config Attributes Available</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Entity Config Attributes Available</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable
     * @generated
     */
    EClass getOrgEntityConfigAttributesAvailable();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#getAttributeName <em>Attribute Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Name</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#getAttributeName()
     * @see #getOrgEntityConfigAttributesAvailable()
     * @generated
     */
    EAttribute getOrgEntityConfigAttributesAvailable_AttributeName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#isReadOnly <em>Read Only</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Read Only</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#isReadOnly()
     * @see #getOrgEntityConfigAttributesAvailable()
     * @generated
     */
    EAttribute getOrgEntityConfigAttributesAvailable_ReadOnly();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet <em>Org Entity Config Attribute Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Entity Config Attribute Set</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttributeSet
     * @generated
     */
    EClass getOrgEntityConfigAttributeSet();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet#getAttributeName <em>Attribute Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Name</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttributeSet#getAttributeName()
     * @see #getOrgEntityConfigAttributeSet()
     * @generated
     */
    EAttribute getOrgEntityConfigAttributeSet_AttributeName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet#getAttributeValue <em>Attribute Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Value</em>'.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttributeSet#getAttributeValue()
     * @see #getOrgEntityConfigAttributeSet()
     * @generated
     */
    EAttribute getOrgEntityConfigAttributeSet_AttributeValue();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ParameterType <em>Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter Type</em>'.
     * @see com.tibco.n2.brm.api.ParameterType
     * @generated
     */
    EClass getParameterType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ParameterType#getComplexValue <em>Complex Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Complex Value</em>'.
     * @see com.tibco.n2.brm.api.ParameterType#getComplexValue()
     * @see #getParameterType()
     * @generated
     */
    EReference getParameterType_ComplexValue();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.ParameterType#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Value</em>'.
     * @see com.tibco.n2.brm.api.ParameterType#getValue()
     * @see #getParameterType()
     * @generated
     */
    EAttribute getParameterType_Value();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ParameterType#isArray <em>Array</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Array</em>'.
     * @see com.tibco.n2.brm.api.ParameterType#isArray()
     * @see #getParameterType()
     * @generated
     */
    EAttribute getParameterType_Array();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ParameterType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.brm.api.ParameterType#getName()
     * @see #getParameterType()
     * @generated
     */
    EAttribute getParameterType_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.PendWorkItem <em>Pend Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Pend Work Item</em>'.
     * @see com.tibco.n2.brm.api.PendWorkItem
     * @generated
     */
    EClass getPendWorkItem();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.PendWorkItem#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.PendWorkItem#getGroup()
     * @see #getPendWorkItem()
     * @generated
     */
    EAttribute getPendWorkItem_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.PendWorkItem#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.PendWorkItem#getWorkItemID()
     * @see #getPendWorkItem()
     * @generated
     */
    EReference getPendWorkItem_WorkItemID();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.PendWorkItem#getHiddenPeriod <em>Hidden Period</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Hidden Period</em>'.
     * @see com.tibco.n2.brm.api.PendWorkItem#getHiddenPeriod()
     * @see #getPendWorkItem()
     * @generated
     */
    EReference getPendWorkItem_HiddenPeriod();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.PendWorkItemResponseType <em>Pend Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Pend Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.PendWorkItemResponseType
     * @generated
     */
    EClass getPendWorkItemResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.PendWorkItemResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.PendWorkItemResponseType#getGroup()
     * @see #getPendWorkItemResponseType()
     * @generated
     */
    EAttribute getPendWorkItemResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.PendWorkItemResponseType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.PendWorkItemResponseType#getWorkItemID()
     * @see #getPendWorkItemResponseType()
     * @generated
     */
    EReference getPendWorkItemResponseType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType <em>Preview Work Item From List Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Preview Work Item From List Response Type</em>'.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType
     * @generated
     */
    EClass getPreviewWorkItemFromListResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType#getGroup()
     * @see #getPreviewWorkItemFromListResponseType()
     * @generated
     */
    EAttribute getPreviewWorkItemFromListResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType#getWorkItemPreview <em>Work Item Preview</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item Preview</em>'.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType#getWorkItemPreview()
     * @see #getPreviewWorkItemFromListResponseType()
     * @generated
     */
    EReference getPreviewWorkItemFromListResponseType_WorkItemPreview();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType <em>Preview Work Item From List Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Preview Work Item From List Type</em>'.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListType
     * @generated
     */
    EClass getPreviewWorkItemFromListType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#getEntityID <em>Entity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Entity ID</em>'.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListType#getEntityID()
     * @see #getPreviewWorkItemFromListType()
     * @generated
     */
    EReference getPreviewWorkItemFromListType_EntityID();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListType#getWorkItemID()
     * @see #getPreviewWorkItemFromListType()
     * @generated
     */
    EReference getPreviewWorkItemFromListType_WorkItemID();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#getRequiredField <em>Required Field</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Required Field</em>'.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListType#getRequiredField()
     * @see #getPreviewWorkItemFromListType()
     * @generated
     */
    EAttribute getPreviewWorkItemFromListType_RequiredField();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#isRequireWorkType <em>Require Work Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Require Work Type</em>'.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListType#isRequireWorkType()
     * @see #getPreviewWorkItemFromListType()
     * @generated
     */
    EAttribute getPreviewWorkItemFromListType_RequireWorkType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.Privilege <em>Privilege</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Privilege</em>'.
     * @see com.tibco.n2.brm.api.Privilege
     * @generated
     */
    EClass getPrivilege();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.Privilege#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.brm.api.Privilege#getName()
     * @see #getPrivilege()
     * @generated
     */
    EAttribute getPrivilege_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.Privilege#getQualifier <em>Qualifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Qualifier</em>'.
     * @see com.tibco.n2.brm.api.Privilege#getQualifier()
     * @see #getPrivilege()
     * @generated
     */
    EAttribute getPrivilege_Qualifier();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.PushNotificationType <em>Push Notification Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Push Notification Type</em>'.
     * @see com.tibco.n2.brm.api.PushNotificationType
     * @generated
     */
    EClass getPushNotificationType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.PushNotificationType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.PushNotificationType#getWorkItemID()
     * @see #getPushNotificationType()
     * @generated
     */
    EReference getPushNotificationType_WorkItemID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.PushNotificationType#getWorkTypeID <em>Work Type ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Type ID</em>'.
     * @see com.tibco.n2.brm.api.PushNotificationType#getWorkTypeID()
     * @see #getPushNotificationType()
     * @generated
     */
    EReference getPushNotificationType_WorkTypeID();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.PushNotificationType#getResourceIDs <em>Resource IDs</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Resource IDs</em>'.
     * @see com.tibco.n2.brm.api.PushNotificationType#getResourceIDs()
     * @see #getPushNotificationType()
     * @generated
     */
    EReference getPushNotificationType_ResourceIDs();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ReallocateWorkItemData <em>Reallocate Work Item Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reallocate Work Item Data</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemData
     * @generated
     */
    EClass getReallocateWorkItemData();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.ReallocateWorkItemData#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemData#getGroup()
     * @see #getReallocateWorkItemData()
     * @generated
     */
    EAttribute getReallocateWorkItemData_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ReallocateWorkItemData#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemData#getWorkItemID()
     * @see #getReallocateWorkItemData()
     * @generated
     */
    EReference getReallocateWorkItemData_WorkItemID();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.ReallocateWorkItemData#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Resource</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemData#getResource()
     * @see #getReallocateWorkItemData()
     * @generated
     */
    EAttribute getReallocateWorkItemData_Resource();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ReallocateWorkItemData#getWorkItemPayload <em>Work Item Payload</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item Payload</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemData#getWorkItemPayload()
     * @see #getReallocateWorkItemData()
     * @generated
     */
    EReference getReallocateWorkItemData_WorkItemPayload();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType <em>Reallocate Work Item Data Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reallocate Work Item Data Response Type</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType
     * @generated
     */
    EClass getReallocateWorkItemDataResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType#getGroup()
     * @see #getReallocateWorkItemDataResponseType()
     * @generated
     */
    EAttribute getReallocateWorkItemDataResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType#getWorkItem <em>Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType#getWorkItem()
     * @see #getReallocateWorkItemDataResponseType()
     * @generated
     */
    EReference getReallocateWorkItemDataResponseType_WorkItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ReallocateWorkItemResponseType <em>Reallocate Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reallocate Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemResponseType
     * @generated
     */
    EClass getReallocateWorkItemResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.ReallocateWorkItemResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemResponseType#getGroup()
     * @see #getReallocateWorkItemResponseType()
     * @generated
     */
    EAttribute getReallocateWorkItemResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ReallocateWorkItemResponseType#getWorkItem <em>Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemResponseType#getWorkItem()
     * @see #getReallocateWorkItemResponseType()
     * @generated
     */
    EReference getReallocateWorkItemResponseType_WorkItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ReallocateWorkItemType <em>Reallocate Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reallocate Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemType
     * @generated
     */
    EClass getReallocateWorkItemType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.ReallocateWorkItemType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemType#getGroup()
     * @see #getReallocateWorkItemType()
     * @generated
     */
    EAttribute getReallocateWorkItemType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.ReallocateWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemType#getWorkItemID()
     * @see #getReallocateWorkItemType()
     * @generated
     */
    EReference getReallocateWorkItemType_WorkItemID();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.ReallocateWorkItemType#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Resource</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemType#getResource()
     * @see #getReallocateWorkItemType()
     * @generated
     */
    EAttribute getReallocateWorkItemType_Resource();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.ReallocateWorkItemType#getRevertData <em>Revert Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Revert Data</em>'.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemType#getRevertData()
     * @see #getReallocateWorkItemType()
     * @generated
     */
    EAttribute getReallocateWorkItemType_RevertData();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.RescheduleWorkItem <em>Reschedule Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reschedule Work Item</em>'.
     * @see com.tibco.n2.brm.api.RescheduleWorkItem
     * @generated
     */
    EClass getRescheduleWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.RescheduleWorkItem#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.RescheduleWorkItem#getWorkItemID()
     * @see #getRescheduleWorkItem()
     * @generated
     */
    EReference getRescheduleWorkItem_WorkItemID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.RescheduleWorkItem#getItemSchedule <em>Item Schedule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Schedule</em>'.
     * @see com.tibco.n2.brm.api.RescheduleWorkItem#getItemSchedule()
     * @see #getRescheduleWorkItem()
     * @generated
     */
    EReference getRescheduleWorkItem_ItemSchedule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.RescheduleWorkItem#getItemBody <em>Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Body</em>'.
     * @see com.tibco.n2.brm.api.RescheduleWorkItem#getItemBody()
     * @see #getRescheduleWorkItem()
     * @generated
     */
    EReference getRescheduleWorkItem_ItemBody();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.RescheduleWorkItemResponseType <em>Reschedule Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Reschedule Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.RescheduleWorkItemResponseType
     * @generated
     */
    EClass getRescheduleWorkItemResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.RescheduleWorkItemResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.RescheduleWorkItemResponseType#isSuccess()
     * @see #getRescheduleWorkItemResponseType()
     * @generated
     */
    EAttribute getRescheduleWorkItemResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ResumeWorkItemResponseType <em>Resume Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Resume Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.ResumeWorkItemResponseType
     * @generated
     */
    EClass getResumeWorkItemResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ResumeWorkItemResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.ResumeWorkItemResponseType#isSuccess()
     * @see #getResumeWorkItemResponseType()
     * @generated
     */
    EAttribute getResumeWorkItemResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ResumeWorkItemType <em>Resume Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Resume Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.ResumeWorkItemType
     * @generated
     */
    EClass getResumeWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ResumeWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.ResumeWorkItemType#getWorkItemID()
     * @see #getResumeWorkItemType()
     * @generated
     */
    EReference getResumeWorkItemType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SaveOpenWorkItemResponseType <em>Save Open Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Save Open Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.SaveOpenWorkItemResponseType
     * @generated
     */
    EClass getSaveOpenWorkItemResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.SaveOpenWorkItemResponseType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.SaveOpenWorkItemResponseType#getWorkItemID()
     * @see #getSaveOpenWorkItemResponseType()
     * @generated
     */
    EReference getSaveOpenWorkItemResponseType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SaveOpenWorkItemType <em>Save Open Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Save Open Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.SaveOpenWorkItemType
     * @generated
     */
    EClass getSaveOpenWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.SaveOpenWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.SaveOpenWorkItemType#getWorkItemID()
     * @see #getSaveOpenWorkItemType()
     * @generated
     */
    EReference getSaveOpenWorkItemType_WorkItemID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.SaveOpenWorkItemType#getWorkItemPayload <em>Work Item Payload</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item Payload</em>'.
     * @see com.tibco.n2.brm.api.SaveOpenWorkItemType#getWorkItemPayload()
     * @see #getSaveOpenWorkItemType()
     * @generated
     */
    EReference getSaveOpenWorkItemType_WorkItemPayload();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ScheduleWorkItemResponseType <em>Schedule Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Schedule Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemResponseType
     * @generated
     */
    EClass getScheduleWorkItemResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemResponseType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemResponseType#getWorkItemID()
     * @see #getScheduleWorkItemResponseType()
     * @generated
     */
    EReference getScheduleWorkItemResponseType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ScheduleWorkItemType <em>Schedule Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Schedule Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemType
     * @generated
     */
    EClass getScheduleWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItem <em>Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemType#getItem()
     * @see #getScheduleWorkItemType()
     * @generated
     */
    EReference getScheduleWorkItemType_Item();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemSchedule <em>Item Schedule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Schedule</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemType#getItemSchedule()
     * @see #getScheduleWorkItemType()
     * @generated
     */
    EReference getScheduleWorkItemType_ItemSchedule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemContext <em>Item Context</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Context</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemType#getItemContext()
     * @see #getScheduleWorkItemType()
     * @generated
     */
    EReference getScheduleWorkItemType_ItemContext();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemBody <em>Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Body</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemType#getItemBody()
     * @see #getScheduleWorkItemType()
     * @generated
     */
    EReference getScheduleWorkItemType_ItemBody();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType <em>Schedule Work Item With Model Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Schedule Work Item With Model Response Type</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType
     * @generated
     */
    EClass getScheduleWorkItemWithModelResponseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType#getWorkItemID()
     * @see #getScheduleWorkItemWithModelResponseType()
     * @generated
     */
    EReference getScheduleWorkItemWithModelResponseType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType <em>Schedule Work Item With Model Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Schedule Work Item With Model Type</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType
     * @generated
     */
    EClass getScheduleWorkItemWithModelType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemSchedule <em>Item Schedule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Schedule</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemSchedule()
     * @see #getScheduleWorkItemWithModelType()
     * @generated
     */
    EReference getScheduleWorkItemWithModelType_ItemSchedule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemContext <em>Item Context</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Context</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemContext()
     * @see #getScheduleWorkItemWithModelType()
     * @generated
     */
    EReference getScheduleWorkItemWithModelType_ItemContext();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemBody <em>Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Body</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemBody()
     * @see #getScheduleWorkItemWithModelType()
     * @generated
     */
    EReference getScheduleWorkItemWithModelType_ItemBody();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getEntityQuery <em>Entity Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Entity Query</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getEntityQuery()
     * @see #getScheduleWorkItemWithModelType()
     * @generated
     */
    EReference getScheduleWorkItemWithModelType_EntityQuery();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getGroupID()
     * @see #getScheduleWorkItemWithModelType()
     * @generated
     */
    EAttribute getScheduleWorkItemWithModelType_GroupID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getWorkModelUID <em>Work Model UID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Model UID</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getWorkModelUID()
     * @see #getScheduleWorkItemWithModelType()
     * @generated
     */
    EAttribute getScheduleWorkItemWithModelType_WorkModelUID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getWorkModelVersion <em>Work Model Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Model Version</em>'.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getWorkModelVersion()
     * @see #getScheduleWorkItemWithModelType()
     * @generated
     */
    EAttribute getScheduleWorkItemWithModelType_WorkModelVersion();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesResponseType <em>Set Org Entity Config Attributes Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Set Org Entity Config Attributes Response Type</em>'.
     * @see com.tibco.n2.brm.api.SetOrgEntityConfigAttributesResponseType
     * @generated
     */
    EClass getSetOrgEntityConfigAttributesResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.SetOrgEntityConfigAttributesResponseType#isSuccess()
     * @see #getSetOrgEntityConfigAttributesResponseType()
     * @generated
     */
    EAttribute getSetOrgEntityConfigAttributesResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType <em>Set Org Entity Config Attributes Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Set Org Entity Config Attributes Type</em>'.
     * @see com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType
     * @generated
     */
    EClass getSetOrgEntityConfigAttributesType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getGroup()
     * @see #getSetOrgEntityConfigAttributesType()
     * @generated
     */
    EAttribute getSetOrgEntityConfigAttributesType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getOrgEntityConfigAttributeSet <em>Org Entity Config Attribute Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Entity Config Attribute Set</em>'.
     * @see com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getOrgEntityConfigAttributeSet()
     * @see #getSetOrgEntityConfigAttributesType()
     * @generated
     */
    EReference getSetOrgEntityConfigAttributesType_OrgEntityConfigAttributeSet();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource</em>'.
     * @see com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getResource()
     * @see #getSetOrgEntityConfigAttributesType()
     * @generated
     */
    EAttribute getSetOrgEntityConfigAttributesType_Resource();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaResponseType <em>Set Resource Order Filter Criteria Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Set Resource Order Filter Criteria Response Type</em>'.
     * @see com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaResponseType
     * @generated
     */
    EClass getSetResourceOrderFilterCriteriaResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaResponseType#isSuccess()
     * @see #getSetResourceOrderFilterCriteriaResponseType()
     * @generated
     */
    EAttribute getSetResourceOrderFilterCriteriaResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType <em>Set Resource Order Filter Criteria Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Set Resource Order Filter Criteria Type</em>'.
     * @see com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType
     * @generated
     */
    EClass getSetResourceOrderFilterCriteriaType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType#getOrderFilterCriteria <em>Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType#getOrderFilterCriteria()
     * @see #getSetResourceOrderFilterCriteriaType()
     * @generated
     */
    EReference getSetResourceOrderFilterCriteriaType_OrderFilterCriteria();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType#getResourceID <em>Resource ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource ID</em>'.
     * @see com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType#getResourceID()
     * @see #getSetResourceOrderFilterCriteriaType()
     * @generated
     */
    EAttribute getSetResourceOrderFilterCriteriaType_ResourceID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SetWorkItemPriority <em>Set Work Item Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Set Work Item Priority</em>'.
     * @see com.tibco.n2.brm.api.SetWorkItemPriority
     * @generated
     */
    EClass getSetWorkItemPriority();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.SetWorkItemPriority#getWorkItemIDandPriority <em>Work Item IDand Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item IDand Priority</em>'.
     * @see com.tibco.n2.brm.api.SetWorkItemPriority#getWorkItemIDandPriority()
     * @see #getSetWorkItemPriority()
     * @generated
     */
    EReference getSetWorkItemPriority_WorkItemIDandPriority();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SetWorkItemPriorityResponseType <em>Set Work Item Priority Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Set Work Item Priority Response Type</em>'.
     * @see com.tibco.n2.brm.api.SetWorkItemPriorityResponseType
     * @generated
     */
    EClass getSetWorkItemPriorityResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.SetWorkItemPriorityResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.SetWorkItemPriorityResponseType#getGroup()
     * @see #getSetWorkItemPriorityResponseType()
     * @generated
     */
    EAttribute getSetWorkItemPriorityResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.SetWorkItemPriorityResponseType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.SetWorkItemPriorityResponseType#getWorkItemID()
     * @see #getSetWorkItemPriorityResponseType()
     * @generated
     */
    EReference getSetWorkItemPriorityResponseType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SkipWorkItemResponseType <em>Skip Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Skip Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.SkipWorkItemResponseType
     * @generated
     */
    EClass getSkipWorkItemResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.SkipWorkItemResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.SkipWorkItemResponseType#isSuccess()
     * @see #getSkipWorkItemResponseType()
     * @generated
     */
    EAttribute getSkipWorkItemResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SkipWorkItemType <em>Skip Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Skip Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.SkipWorkItemType
     * @generated
     */
    EClass getSkipWorkItemType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.SkipWorkItemType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.SkipWorkItemType#getGroup()
     * @see #getSkipWorkItemType()
     * @generated
     */
    EAttribute getSkipWorkItemType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.SkipWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.SkipWorkItemType#getWorkItemID()
     * @see #getSkipWorkItemType()
     * @generated
     */
    EReference getSkipWorkItemType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.StartGroupResponseType <em>Start Group Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Start Group Response Type</em>'.
     * @see com.tibco.n2.brm.api.StartGroupResponseType
     * @generated
     */
    EClass getStartGroupResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.StartGroupResponseType#getGroupID <em>Group ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group ID</em>'.
     * @see com.tibco.n2.brm.api.StartGroupResponseType#getGroupID()
     * @see #getStartGroupResponseType()
     * @generated
     */
    EAttribute getStartGroupResponseType_GroupID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.StartGroupType <em>Start Group Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Start Group Type</em>'.
     * @see com.tibco.n2.brm.api.StartGroupType
     * @generated
     */
    EClass getStartGroupType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.StartGroupType#getGroupType <em>Group Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group Type</em>'.
     * @see com.tibco.n2.brm.api.StartGroupType#getGroupType()
     * @see #getStartGroupType()
     * @generated
     */
    EAttribute getStartGroupType_GroupType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.StartGroupType#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.n2.brm.api.StartGroupType#getDescription()
     * @see #getStartGroupType()
     * @generated
     */
    EAttribute getStartGroupType_Description();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SuspendWorkItemResponseType <em>Suspend Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Suspend Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.SuspendWorkItemResponseType
     * @generated
     */
    EClass getSuspendWorkItemResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.SuspendWorkItemResponseType#isSuccess <em>Success</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Success</em>'.
     * @see com.tibco.n2.brm.api.SuspendWorkItemResponseType#isSuccess()
     * @see #getSuspendWorkItemResponseType()
     * @generated
     */
    EAttribute getSuspendWorkItemResponseType_Success();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.SuspendWorkItemType <em>Suspend Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Suspend Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.SuspendWorkItemType
     * @generated
     */
    EClass getSuspendWorkItemType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.SuspendWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.SuspendWorkItemType#getWorkItemID()
     * @see #getSuspendWorkItemType()
     * @generated
     */
    EReference getSuspendWorkItemType_WorkItemID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.SuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Force Suspend</em>'.
     * @see com.tibco.n2.brm.api.SuspendWorkItemType#isForceSuspend()
     * @see #getSuspendWorkItemType()
     * @generated
     */
    EAttribute getSuspendWorkItemType_ForceSuspend();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.UnallocateWorkItemResponseType <em>Unallocate Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unallocate Work Item Response Type</em>'.
     * @see com.tibco.n2.brm.api.UnallocateWorkItemResponseType
     * @generated
     */
    EClass getUnallocateWorkItemResponseType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.UnallocateWorkItemResponseType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.UnallocateWorkItemResponseType#getGroup()
     * @see #getUnallocateWorkItemResponseType()
     * @generated
     */
    EAttribute getUnallocateWorkItemResponseType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.UnallocateWorkItemResponseType#getWorkItem <em>Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item</em>'.
     * @see com.tibco.n2.brm.api.UnallocateWorkItemResponseType#getWorkItem()
     * @see #getUnallocateWorkItemResponseType()
     * @generated
     */
    EReference getUnallocateWorkItemResponseType_WorkItem();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.UnallocateWorkItemType <em>Unallocate Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unallocate Work Item Type</em>'.
     * @see com.tibco.n2.brm.api.UnallocateWorkItemType
     * @generated
     */
    EClass getUnallocateWorkItemType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.api.UnallocateWorkItemType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.brm.api.UnallocateWorkItemType#getGroup()
     * @see #getUnallocateWorkItemType()
     * @generated
     */
    EAttribute getUnallocateWorkItemType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.UnallocateWorkItemType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.UnallocateWorkItemType#getWorkItemID()
     * @see #getUnallocateWorkItemType()
     * @generated
     */
    EReference getUnallocateWorkItemType_WorkItemID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.UnlockWorkListViewResponseType <em>Unlock Work List View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unlock Work List View Response Type</em>'.
     * @see com.tibco.n2.brm.api.UnlockWorkListViewResponseType
     * @generated
     */
    EClass getUnlockWorkListViewResponseType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.UnlockWorkListViewResponseType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.UnlockWorkListViewResponseType#getWorkListViewID()
     * @see #getUnlockWorkListViewResponseType()
     * @generated
     */
    EAttribute getUnlockWorkListViewResponseType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.UnlockWorkListViewType <em>Unlock Work List View Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unlock Work List View Type</em>'.
     * @see com.tibco.n2.brm.api.UnlockWorkListViewType
     * @generated
     */
    EClass getUnlockWorkListViewType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.UnlockWorkListViewType#getWorkListViewID <em>Work List View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work List View ID</em>'.
     * @see com.tibco.n2.brm.api.UnlockWorkListViewType#getWorkListViewID()
     * @see #getUnlockWorkListViewType()
     * @generated
     */
    EAttribute getUnlockWorkListViewType_WorkListViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkItem <em>Work Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item</em>'.
     * @see com.tibco.n2.brm.api.WorkItem
     * @generated
     */
    EClass getWorkItem();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItem#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Id</em>'.
     * @see com.tibco.n2.brm.api.WorkItem#getId()
     * @see #getWorkItem()
     * @generated
     */
    EReference getWorkItem_Id();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItem#getHeader <em>Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Header</em>'.
     * @see com.tibco.n2.brm.api.WorkItem#getHeader()
     * @see #getWorkItem()
     * @generated
     */
    EReference getWorkItem_Header();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItem#getAttributes <em>Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Attributes</em>'.
     * @see com.tibco.n2.brm.api.WorkItem#getAttributes()
     * @see #getWorkItem()
     * @generated
     */
    EReference getWorkItem_Attributes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItem#getBody <em>Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Body</em>'.
     * @see com.tibco.n2.brm.api.WorkItem#getBody()
     * @see #getWorkItem()
     * @generated
     */
    EReference getWorkItem_Body();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItem#getWorkType <em>Work Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Type</em>'.
     * @see com.tibco.n2.brm.api.WorkItem#getWorkType()
     * @see #getWorkItem()
     * @generated
     */
    EReference getWorkItem_WorkType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItem#getState <em>State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>State</em>'.
     * @see com.tibco.n2.brm.api.WorkItem#getState()
     * @see #getWorkItem()
     * @generated
     */
    EAttribute getWorkItem_State();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItem#isVisible <em>Visible</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Visible</em>'.
     * @see com.tibco.n2.brm.api.WorkItem#isVisible()
     * @see #getWorkItem()
     * @generated
     */
    EAttribute getWorkItem_Visible();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkItemAttributes <em>Work Item Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Attributes</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes
     * @generated
     */
    EClass getWorkItemAttributes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute1 <em>Attribute1</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute1</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute1()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute1();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute10 <em>Attribute10</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute10</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute10()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute10();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute11 <em>Attribute11</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute11</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute11()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute11();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute12 <em>Attribute12</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute12</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute12()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute12();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute13 <em>Attribute13</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute13</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute13()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute13();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute14 <em>Attribute14</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute14</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute14()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute14();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute15 <em>Attribute15</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute15</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute15()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute15();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute16 <em>Attribute16</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute16</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute16()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute16();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute17 <em>Attribute17</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute17</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute17()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute17();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute18 <em>Attribute18</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute18</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute18()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute18();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute19 <em>Attribute19</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute19</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute19()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute19();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute2 <em>Attribute2</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute2</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute2()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute2();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute20 <em>Attribute20</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute20</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute20()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute20();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute21 <em>Attribute21</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute21</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute21()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute21();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute22 <em>Attribute22</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute22</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute22()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute22();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute23 <em>Attribute23</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute23</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute23()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute23();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute24 <em>Attribute24</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute24</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute24()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute24();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute25 <em>Attribute25</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute25</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute25()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute25();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute26 <em>Attribute26</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute26</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute26()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute26();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute27 <em>Attribute27</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute27</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute27()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute27();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute28 <em>Attribute28</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute28</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute28()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute28();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute29 <em>Attribute29</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute29</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute29()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute29();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute3 <em>Attribute3</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute3</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute3()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute3();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute30 <em>Attribute30</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute30</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute30()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute30();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute31 <em>Attribute31</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute31</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute31()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute31();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute32 <em>Attribute32</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute32</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute32()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute32();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute33 <em>Attribute33</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute33</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute33()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute33();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute34 <em>Attribute34</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute34</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute34()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute34();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute35 <em>Attribute35</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute35</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute35()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute35();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute36 <em>Attribute36</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute36</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute36()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute36();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute37 <em>Attribute37</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute37</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute37()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute37();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute38 <em>Attribute38</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute38</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute38()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute38();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute39 <em>Attribute39</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute39</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute39()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute39();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute4 <em>Attribute4</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute4</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute4()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute4();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute40 <em>Attribute40</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute40</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute40()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute40();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute5 <em>Attribute5</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute5</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute5()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute5();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute6 <em>Attribute6</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute6</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute6()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute6();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute7 <em>Attribute7</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute7</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute7()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute7();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute8 <em>Attribute8</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute8</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute8()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute8();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemAttributes#getAttribute9 <em>Attribute9</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute9</em>'.
     * @see com.tibco.n2.brm.api.WorkItemAttributes#getAttribute9()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EAttribute getWorkItemAttributes_Attribute9();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkItemBody <em>Work Item Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Body</em>'.
     * @see com.tibco.n2.brm.api.WorkItemBody
     * @generated
     */
    EClass getWorkItemBody();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItemBody#getDataModel <em>Data Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Data Model</em>'.
     * @see com.tibco.n2.brm.api.WorkItemBody#getDataModel()
     * @see #getWorkItemBody()
     * @generated
     */
    EReference getWorkItemBody_DataModel();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkItemFlags <em>Work Item Flags</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Flags</em>'.
     * @see com.tibco.n2.brm.api.WorkItemFlags
     * @generated
     */
    EClass getWorkItemFlags();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemFlags#getScheduleStatus <em>Schedule Status</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Schedule Status</em>'.
     * @see com.tibco.n2.brm.api.WorkItemFlags#getScheduleStatus()
     * @see #getWorkItemFlags()
     * @generated
     */
    EAttribute getWorkItemFlags_ScheduleStatus();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkItemHeader <em>Work Item Header</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Header</em>'.
     * @see com.tibco.n2.brm.api.WorkItemHeader
     * @generated
     */
    EClass getWorkItemHeader();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItemHeader#getFlags <em>Flags</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Flags</em>'.
     * @see com.tibco.n2.brm.api.WorkItemHeader#getFlags()
     * @see #getWorkItemHeader()
     * @generated
     */
    EReference getWorkItemHeader_Flags();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItemHeader#getItemContext <em>Item Context</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Context</em>'.
     * @see com.tibco.n2.brm.api.WorkItemHeader#getItemContext()
     * @see #getWorkItemHeader()
     * @generated
     */
    EReference getWorkItemHeader_ItemContext();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemHeader#getEndDate <em>End Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Date</em>'.
     * @see com.tibco.n2.brm.api.WorkItemHeader#getEndDate()
     * @see #getWorkItemHeader()
     * @generated
     */
    EAttribute getWorkItemHeader_EndDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemHeader#getStartDate <em>Start Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Date</em>'.
     * @see com.tibco.n2.brm.api.WorkItemHeader#getStartDate()
     * @see #getWorkItemHeader()
     * @generated
     */
    EAttribute getWorkItemHeader_StartDate();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkItemIDandPriorityType <em>Work Item IDand Priority Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item IDand Priority Type</em>'.
     * @see com.tibco.n2.brm.api.WorkItemIDandPriorityType
     * @generated
     */
    EClass getWorkItemIDandPriorityType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItemIDandPriorityType#getWorkItemID <em>Work Item ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item ID</em>'.
     * @see com.tibco.n2.brm.api.WorkItemIDandPriorityType#getWorkItemID()
     * @see #getWorkItemIDandPriorityType()
     * @generated
     */
    EReference getWorkItemIDandPriorityType_WorkItemID();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItemIDandPriorityType#getWorkItemPriority <em>Work Item Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item Priority</em>'.
     * @see com.tibco.n2.brm.api.WorkItemIDandPriorityType#getWorkItemPriority()
     * @see #getWorkItemIDandPriorityType()
     * @generated
     */
    EReference getWorkItemIDandPriorityType_WorkItemPriority();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkItemPreview <em>Work Item Preview</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Preview</em>'.
     * @see com.tibco.n2.brm.api.WorkItemPreview
     * @generated
     */
    EClass getWorkItemPreview();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkItemPreview#getFieldPreview <em>Field Preview</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Field Preview</em>'.
     * @see com.tibco.n2.brm.api.WorkItemPreview#getFieldPreview()
     * @see #getWorkItemPreview()
     * @generated
     */
    EReference getWorkItemPreview_FieldPreview();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkItemPreview#getWorkType <em>Work Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Type</em>'.
     * @see com.tibco.n2.brm.api.WorkItemPreview#getWorkType()
     * @see #getWorkItemPreview()
     * @generated
     */
    EReference getWorkItemPreview_WorkType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkItemPriorityType <em>Work Item Priority Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Priority Type</em>'.
     * @see com.tibco.n2.brm.api.WorkItemPriorityType
     * @generated
     */
    EClass getWorkItemPriorityType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemPriorityType#getAbsPriority <em>Abs Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Abs Priority</em>'.
     * @see com.tibco.n2.brm.api.WorkItemPriorityType#getAbsPriority()
     * @see #getWorkItemPriorityType()
     * @generated
     */
    EAttribute getWorkItemPriorityType_AbsPriority();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkItemPriorityType#getOffsetPriority <em>Offset Priority</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Offset Priority</em>'.
     * @see com.tibco.n2.brm.api.WorkItemPriorityType#getOffsetPriority()
     * @see #getWorkItemPriorityType()
     * @generated
     */
    EAttribute getWorkItemPriorityType_OffsetPriority();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkListView <em>Work List View</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work List View</em>'.
     * @see com.tibco.n2.brm.api.WorkListView
     * @generated
     */
    EClass getWorkListView();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListView#getCreationDate <em>Creation Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Creation Date</em>'.
     * @see com.tibco.n2.brm.api.WorkListView#getCreationDate()
     * @see #getWorkListView()
     * @generated
     */
    EAttribute getWorkListView_CreationDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListView#getLocker <em>Locker</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Locker</em>'.
     * @see com.tibco.n2.brm.api.WorkListView#getLocker()
     * @see #getWorkListView()
     * @generated
     */
    EAttribute getWorkListView_Locker();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListView#getModificationDate <em>Modification Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Modification Date</em>'.
     * @see com.tibco.n2.brm.api.WorkListView#getModificationDate()
     * @see #getWorkListView()
     * @generated
     */
    EAttribute getWorkListView_ModificationDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListView#getWorkViewID <em>Work View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work View ID</em>'.
     * @see com.tibco.n2.brm.api.WorkListView#getWorkViewID()
     * @see #getWorkListView()
     * @generated
     */
    EAttribute getWorkListView_WorkViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkListViewCommon <em>Work List View Common</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work List View Common</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon
     * @generated
     */
    EClass getWorkListViewCommon();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkListViewCommon#getEntityID <em>Entity ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Entity ID</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon#getEntityID()
     * @see #getWorkListViewCommon()
     * @generated
     */
    EReference getWorkListViewCommon_EntityID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewCommon#getResourcesRequired <em>Resources Required</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resources Required</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon#getResourcesRequired()
     * @see #getWorkListViewCommon()
     * @generated
     */
    EAttribute getWorkListViewCommon_ResourcesRequired();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkListViewCommon#getOrderFilterCriteria <em>Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Order Filter Criteria</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon#getOrderFilterCriteria()
     * @see #getWorkListViewCommon()
     * @generated
     */
    EReference getWorkListViewCommon_OrderFilterCriteria();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewCommon#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon#getDescription()
     * @see #getWorkListViewCommon()
     * @generated
     */
    EAttribute getWorkListViewCommon_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewCommon#isGetAllocatedItems <em>Get Allocated Items</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Get Allocated Items</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon#isGetAllocatedItems()
     * @see #getWorkListViewCommon()
     * @generated
     */
    EAttribute getWorkListViewCommon_GetAllocatedItems();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewCommon#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon#getName()
     * @see #getWorkListViewCommon()
     * @generated
     */
    EAttribute getWorkListViewCommon_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewCommon#getOwner <em>Owner</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Owner</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon#getOwner()
     * @see #getWorkListViewCommon()
     * @generated
     */
    EAttribute getWorkListViewCommon_Owner();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewCommon#isPublic <em>Public</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Public</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewCommon#isPublic()
     * @see #getWorkListViewCommon()
     * @generated
     */
    EAttribute getWorkListViewCommon_Public();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkListViewEdit <em>Work List View Edit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work List View Edit</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewEdit
     * @generated
     */
    EClass getWorkListViewEdit();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkListViewEdit#getAuthors <em>Authors</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Authors</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewEdit#getAuthors()
     * @see #getWorkListViewEdit()
     * @generated
     */
    EReference getWorkListViewEdit_Authors();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkListViewEdit#getUsers <em>Users</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Users</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewEdit#getUsers()
     * @see #getWorkListViewEdit()
     * @generated
     */
    EReference getWorkListViewEdit_Users();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewEdit#getCustomData <em>Custom Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Custom Data</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewEdit#getCustomData()
     * @see #getWorkListViewEdit()
     * @generated
     */
    EAttribute getWorkListViewEdit_CustomData();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkListViewPageItem <em>Work List View Page Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work List View Page Item</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewPageItem
     * @generated
     */
    EClass getWorkListViewPageItem();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewPageItem#getCreationDate <em>Creation Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Creation Date</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewPageItem#getCreationDate()
     * @see #getWorkListViewPageItem()
     * @generated
     */
    EAttribute getWorkListViewPageItem_CreationDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewPageItem#getModificationDate <em>Modification Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Modification Date</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewPageItem#getModificationDate()
     * @see #getWorkListViewPageItem()
     * @generated
     */
    EAttribute getWorkListViewPageItem_ModificationDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkListViewPageItem#getWorkViewID <em>Work View ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work View ID</em>'.
     * @see com.tibco.n2.brm.api.WorkListViewPageItem#getWorkViewID()
     * @see #getWorkListViewPageItem()
     * @generated
     */
    EAttribute getWorkListViewPageItem_WorkViewID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModel <em>Work Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model</em>'.
     * @see com.tibco.n2.brm.api.WorkModel
     * @generated
     */
    EClass getWorkModel();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkModel#getBaseModelInfo <em>Base Model Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Base Model Info</em>'.
     * @see com.tibco.n2.brm.api.WorkModel#getBaseModelInfo()
     * @see #getWorkModel()
     * @generated
     */
    EReference getWorkModel_BaseModelInfo();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelSpecification <em>Work Model Specification</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Model Specification</em>'.
     * @see com.tibco.n2.brm.api.WorkModel#getWorkModelSpecification()
     * @see #getWorkModel()
     * @generated
     */
    EReference getWorkModel_WorkModelSpecification();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelEntities <em>Work Model Entities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Model Entities</em>'.
     * @see com.tibco.n2.brm.api.WorkModel#getWorkModelEntities()
     * @see #getWorkModel()
     * @generated
     */
    EReference getWorkModel_WorkModelEntities();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelTypes <em>Work Model Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Model Types</em>'.
     * @see com.tibco.n2.brm.api.WorkModel#getWorkModelTypes()
     * @see #getWorkModel()
     * @generated
     */
    EReference getWorkModel_WorkModelTypes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkModel#getItemPrivileges <em>Item Privileges</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Item Privileges</em>'.
     * @see com.tibco.n2.brm.api.WorkModel#getItemPrivileges()
     * @see #getWorkModel()
     * @generated
     */
    EReference getWorkModel_ItemPrivileges();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelScripts <em>Work Model Scripts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Model Scripts</em>'.
     * @see com.tibco.n2.brm.api.WorkModel#getWorkModelScripts()
     * @see #getWorkModel()
     * @generated
     */
    EReference getWorkModel_WorkModelScripts();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkModel#getAttributeAliasList <em>Attribute Alias List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Attribute Alias List</em>'.
     * @see com.tibco.n2.brm.api.WorkModel#getAttributeAliasList()
     * @see #getWorkModel()
     * @generated
     */
    EReference getWorkModel_AttributeAliasList();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModel#getWorkModelUID <em>Work Model UID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Model UID</em>'.
     * @see com.tibco.n2.brm.api.WorkModel#getWorkModelUID()
     * @see #getWorkModel()
     * @generated
     */
    EAttribute getWorkModel_WorkModelUID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelEntities <em>Work Model Entities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model Entities</em>'.
     * @see com.tibco.n2.brm.api.WorkModelEntities
     * @generated
     */
    EClass getWorkModelEntities();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkModelEntities#getWorkModelEntity <em>Work Model Entity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Model Entity</em>'.
     * @see com.tibco.n2.brm.api.WorkModelEntities#getWorkModelEntity()
     * @see #getWorkModelEntities()
     * @generated
     */
    EReference getWorkModelEntities_WorkModelEntity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelEntities#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Expression</em>'.
     * @see com.tibco.n2.brm.api.WorkModelEntities#getExpression()
     * @see #getWorkModelEntities()
     * @generated
     */
    EAttribute getWorkModelEntities_Expression();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelEntity <em>Work Model Entity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model Entity</em>'.
     * @see com.tibco.n2.brm.api.WorkModelEntity
     * @generated
     */
    EClass getWorkModelEntity();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.api.WorkModelEntity#getEntityQuery <em>Entity Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Entity Query</em>'.
     * @see com.tibco.n2.brm.api.WorkModelEntity#getEntityQuery()
     * @see #getWorkModelEntity()
     * @generated
     */
    EReference getWorkModelEntity_EntityQuery();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkModelEntity#getEntities <em>Entities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Entities</em>'.
     * @see com.tibco.n2.brm.api.WorkModelEntity#getEntities()
     * @see #getWorkModelEntity()
     * @generated
     */
    EReference getWorkModelEntity_Entities();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelEntity#getDistributionStrategy <em>Distribution Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Distribution Strategy</em>'.
     * @see com.tibco.n2.brm.api.WorkModelEntity#getDistributionStrategy()
     * @see #getWorkModelEntity()
     * @generated
     */
    EAttribute getWorkModelEntity_DistributionStrategy();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelList <em>Work Model List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model List</em>'.
     * @see com.tibco.n2.brm.api.WorkModelList
     * @generated
     */
    EClass getWorkModelList();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelList#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.WorkModelList#getStartPosition()
     * @see #getWorkModelList()
     * @generated
     */
    EAttribute getWorkModelList_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelList#getEndPosition <em>End Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Position</em>'.
     * @see com.tibco.n2.brm.api.WorkModelList#getEndPosition()
     * @see #getWorkModelList()
     * @generated
     */
    EAttribute getWorkModelList_EndPosition();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkModelList#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Type</em>'.
     * @see com.tibco.n2.brm.api.WorkModelList#getType()
     * @see #getWorkModelList()
     * @generated
     */
    EReference getWorkModelList_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelMapping <em>Work Model Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model Mapping</em>'.
     * @see com.tibco.n2.brm.api.WorkModelMapping
     * @generated
     */
    EClass getWorkModelMapping();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelMapping#getTypeParamName <em>Type Param Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type Param Name</em>'.
     * @see com.tibco.n2.brm.api.WorkModelMapping#getTypeParamName()
     * @see #getWorkModelMapping()
     * @generated
     */
    EAttribute getWorkModelMapping_TypeParamName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelMapping#getModelParamName <em>Model Param Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Model Param Name</em>'.
     * @see com.tibco.n2.brm.api.WorkModelMapping#getModelParamName()
     * @see #getWorkModelMapping()
     * @generated
     */
    EAttribute getWorkModelMapping_ModelParamName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelMapping#getDefaultValue <em>Default Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Value</em>'.
     * @see com.tibco.n2.brm.api.WorkModelMapping#getDefaultValue()
     * @see #getWorkModelMapping()
     * @generated
     */
    EAttribute getWorkModelMapping_DefaultValue();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelScript <em>Work Model Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model Script</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScript
     * @generated
     */
    EClass getWorkModelScript();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptBody <em>Script Body</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script Body</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScript#getScriptBody()
     * @see #getWorkModelScript()
     * @generated
     */
    EAttribute getWorkModelScript_ScriptBody();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguage <em>Script Language</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script Language</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScript#getScriptLanguage()
     * @see #getWorkModelScript()
     * @generated
     */
    EAttribute getWorkModelScript_ScriptLanguage();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguageExtension <em>Script Language Extension</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script Language Extension</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScript#getScriptLanguageExtension()
     * @see #getWorkModelScript()
     * @generated
     */
    EAttribute getWorkModelScript_ScriptLanguageExtension();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguageVersion <em>Script Language Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script Language Version</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScript#getScriptLanguageVersion()
     * @see #getWorkModelScript()
     * @generated
     */
    EAttribute getWorkModelScript_ScriptLanguageVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptOperation <em>Script Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script Operation</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScript#getScriptOperation()
     * @see #getWorkModelScript()
     * @generated
     */
    EAttribute getWorkModelScript_ScriptOperation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptTypeID <em>Script Type ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script Type ID</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScript#getScriptTypeID()
     * @see #getWorkModelScript()
     * @generated
     */
    EAttribute getWorkModelScript_ScriptTypeID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelScripts <em>Work Model Scripts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model Scripts</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScripts
     * @generated
     */
    EClass getWorkModelScripts();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkModelScripts#getWorkModelScript <em>Work Model Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Model Script</em>'.
     * @see com.tibco.n2.brm.api.WorkModelScripts#getWorkModelScript()
     * @see #getWorkModelScripts()
     * @generated
     */
    EReference getWorkModelScripts_WorkModelScript();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelSpecification <em>Work Model Specification</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model Specification</em>'.
     * @see com.tibco.n2.brm.api.WorkModelSpecification
     * @generated
     */
    EClass getWorkModelSpecification();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelType <em>Work Model Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model Type</em>'.
     * @see com.tibco.n2.brm.api.WorkModelType
     * @generated
     */
    EClass getWorkModelType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkModelType#getWorkModelMapping <em>Work Model Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Model Mapping</em>'.
     * @see com.tibco.n2.brm.api.WorkModelType#getWorkModelMapping()
     * @see #getWorkModelType()
     * @generated
     */
    EReference getWorkModelType_WorkModelMapping();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.brm.api.WorkModelType#getVersion()
     * @see #getWorkModelType()
     * @generated
     */
    EAttribute getWorkModelType_Version();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelType#getWorkTypeID <em>Work Type ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Work Type ID</em>'.
     * @see com.tibco.n2.brm.api.WorkModelType#getWorkTypeID()
     * @see #getWorkModelType()
     * @generated
     */
    EAttribute getWorkModelType_WorkTypeID();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkModelTypes <em>Work Model Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model Types</em>'.
     * @see com.tibco.n2.brm.api.WorkModelTypes
     * @generated
     */
    EClass getWorkModelTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkModelTypes#getWorkModelType <em>Work Model Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Model Type</em>'.
     * @see com.tibco.n2.brm.api.WorkModelTypes#getWorkModelType()
     * @see #getWorkModelTypes()
     * @generated
     */
    EReference getWorkModelTypes_WorkModelType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkModelTypes#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Expression</em>'.
     * @see com.tibco.n2.brm.api.WorkModelTypes#getExpression()
     * @see #getWorkModelTypes()
     * @generated
     */
    EAttribute getWorkModelTypes_Expression();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.api.WorkTypeList <em>Work Type List</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Type List</em>'.
     * @see com.tibco.n2.brm.api.WorkTypeList
     * @generated
     */
    EClass getWorkTypeList();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkTypeList#getStartPosition <em>Start Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Position</em>'.
     * @see com.tibco.n2.brm.api.WorkTypeList#getStartPosition()
     * @see #getWorkTypeList()
     * @generated
     */
    EAttribute getWorkTypeList_StartPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.api.WorkTypeList#getEndPosition <em>End Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Position</em>'.
     * @see com.tibco.n2.brm.api.WorkTypeList#getEndPosition()
     * @see #getWorkTypeList()
     * @generated
     */
    EAttribute getWorkTypeList_EndPosition();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.api.WorkTypeList#getTypes <em>Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Types</em>'.
     * @see com.tibco.n2.brm.api.WorkTypeList#getTypes()
     * @see #getWorkTypeList()
     * @generated
     */
    EReference getWorkTypeList_Types();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.ColumnCapability <em>Column Capability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Column Capability</em>'.
     * @see com.tibco.n2.brm.api.ColumnCapability
     * @generated
     */
    EEnum getColumnCapability();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.ColumnType <em>Column Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Column Type</em>'.
     * @see com.tibco.n2.brm.api.ColumnType
     * @generated
     */
    EEnum getColumnType();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.DistributionStrategy <em>Distribution Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Distribution Strategy</em>'.
     * @see com.tibco.n2.brm.api.DistributionStrategy
     * @generated
     */
    EEnum getDistributionStrategy();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.MethodAuthorisationAction <em>Method Authorisation Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Method Authorisation Action</em>'.
     * @see com.tibco.n2.brm.api.MethodAuthorisationAction
     * @generated
     */
    EEnum getMethodAuthorisationAction();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.MethodAuthorisationComponent <em>Method Authorisation Component</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Method Authorisation Component</em>'.
     * @see com.tibco.n2.brm.api.MethodAuthorisationComponent
     * @generated
     */
    EEnum getMethodAuthorisationComponent();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.ResourcesRequiredType <em>Resources Required Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Resources Required Type</em>'.
     * @see com.tibco.n2.brm.api.ResourcesRequiredType
     * @generated
     */
    EEnum getResourcesRequiredType();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.ScheduleStatus <em>Schedule Status</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Schedule Status</em>'.
     * @see com.tibco.n2.brm.api.ScheduleStatus
     * @generated
     */
    EEnum getScheduleStatus();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.WorkGroupType <em>Work Group Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Work Group Type</em>'.
     * @see com.tibco.n2.brm.api.WorkGroupType
     * @generated
     */
    EEnum getWorkGroupType();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.WorkItemScriptOperation <em>Work Item Script Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Work Item Script Operation</em>'.
     * @see com.tibco.n2.brm.api.WorkItemScriptOperation
     * @generated
     */
    EEnum getWorkItemScriptOperation();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.WorkItemScriptType <em>Work Item Script Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Work Item Script Type</em>'.
     * @see com.tibco.n2.brm.api.WorkItemScriptType
     * @generated
     */
    EEnum getWorkItemScriptType();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.brm.api.WorkItemState <em>Work Item State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Work Item State</em>'.
     * @see com.tibco.n2.brm.api.WorkItemState
     * @generated
     */
    EEnum getWorkItemState();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute10 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute10 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute10_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute10Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute11 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute11 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute11_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute11Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute12 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute12 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute12_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute12Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute13 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute13 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute13_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute13Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute14 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute14 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute14_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute14Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute21 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute21 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute21_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute21Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute22 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute22 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute22_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute22Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute23 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute23 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute23_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute23Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute24 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute24 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute24_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute24Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute25 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute25 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute25_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute25Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute26 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute26 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute26_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute26Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute27 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute27 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute27_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute27Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute28 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute28 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute28_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute28Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute29 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute29 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute29_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute29Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute2 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute2 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute2_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute2Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute30 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute30 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute30_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute30Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute31 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute31 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute31_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute31Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute32 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute32 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute32_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute32Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute33 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute33 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute33_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute33Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute34 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute34 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute34_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute34Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute35 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute35 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute35_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute35Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute36 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute36 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute36_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute36Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute37 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute37 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute37_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute37Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute38 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute38 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute38_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute38Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute39 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute39 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute39_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='255'"
     * @generated
     */
    EDataType getAttribute39Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute3 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute3 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute3_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute3Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute40 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute40 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute40_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='255'"
     * @generated
     */
    EDataType getAttribute40Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute4 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute4 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute4_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getAttribute4Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute8 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute8 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute8_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute8Type();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Attribute9 Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Attribute9 Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='attribute9_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='20'"
     * @generated
     */
    EDataType getAttribute9Type();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.ColumnCapability <em>Column Capability Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Column Capability Object</em>'.
     * @see com.tibco.n2.brm.api.ColumnCapability
     * @model instanceClass="com.tibco.n2.brm.api.ColumnCapability"
     *        extendedMetaData="name='ColumnCapability:Object' baseType='ColumnCapability'"
     * @generated
     */
    EDataType getColumnCapabilityObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.ColumnType <em>Column Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Column Type Object</em>'.
     * @see com.tibco.n2.brm.api.ColumnType
     * @model instanceClass="com.tibco.n2.brm.api.ColumnType"
     *        extendedMetaData="name='ColumnType:Object' baseType='ColumnType'"
     * @generated
     */
    EDataType getColumnTypeObject();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Description Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Description Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='description_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='255'"
     * @generated
     */
    EDataType getDescriptionType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.DistributionStrategy <em>Distribution Strategy Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Distribution Strategy Object</em>'.
     * @see com.tibco.n2.brm.api.DistributionStrategy
     * @model instanceClass="com.tibco.n2.brm.api.DistributionStrategy"
     *        extendedMetaData="name='DistributionStrategy:Object' baseType='DistributionStrategy'"
     * @generated
     */
    EDataType getDistributionStrategyObject();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Locker Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Locker Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='locker_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='36'"
     * @generated
     */
    EDataType getLockerType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.MethodAuthorisationAction <em>Method Authorisation Action Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Method Authorisation Action Object</em>'.
     * @see com.tibco.n2.brm.api.MethodAuthorisationAction
     * @model instanceClass="com.tibco.n2.brm.api.MethodAuthorisationAction"
     *        extendedMetaData="name='MethodAuthorisationAction:Object' baseType='MethodAuthorisationAction'"
     * @generated
     */
    EDataType getMethodAuthorisationActionObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.MethodAuthorisationComponent <em>Method Authorisation Component Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Method Authorisation Component Object</em>'.
     * @see com.tibco.n2.brm.api.MethodAuthorisationComponent
     * @model instanceClass="com.tibco.n2.brm.api.MethodAuthorisationComponent"
     *        extendedMetaData="name='MethodAuthorisationComponent:Object' baseType='MethodAuthorisationComponent'"
     * @generated
     */
    EDataType getMethodAuthorisationComponentObject();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Name Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Name Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='name_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='64'"
     * @generated
     */
    EDataType getNameType();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Owner Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Owner Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='owner_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' maxLength='36'"
     * @generated
     */
    EDataType getOwnerType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.ResourcesRequiredType <em>Resources Required Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Resources Required Type Object</em>'.
     * @see com.tibco.n2.brm.api.ResourcesRequiredType
     * @model instanceClass="com.tibco.n2.brm.api.ResourcesRequiredType"
     *        extendedMetaData="name='ResourcesRequiredType:Object' baseType='ResourcesRequiredType'"
     * @generated
     */
    EDataType getResourcesRequiredTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.ScheduleStatus <em>Schedule Status Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Schedule Status Object</em>'.
     * @see com.tibco.n2.brm.api.ScheduleStatus
     * @model instanceClass="com.tibco.n2.brm.api.ScheduleStatus"
     *        extendedMetaData="name='ScheduleStatus:Object' baseType='ScheduleStatus'"
     * @generated
     */
    EDataType getScheduleStatusObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.WorkItemScriptOperation <em>Script Operation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Script Operation Type</em>'.
     * @see com.tibco.n2.brm.api.WorkItemScriptOperation
     * @model instanceClass="com.tibco.n2.brm.api.WorkItemScriptOperation"
     *        extendedMetaData="name='scriptOperation_._type' baseType='WorkItemScriptOperation'"
     * @generated
     */
    EDataType getScriptOperationType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.WorkGroupType <em>Work Group Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Work Group Type Object</em>'.
     * @see com.tibco.n2.brm.api.WorkGroupType
     * @model instanceClass="com.tibco.n2.brm.api.WorkGroupType"
     *        extendedMetaData="name='WorkGroupType:Object' baseType='WorkGroupType'"
     * @generated
     */
    EDataType getWorkGroupTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.WorkItemScriptOperation <em>Work Item Script Operation Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Work Item Script Operation Object</em>'.
     * @see com.tibco.n2.brm.api.WorkItemScriptOperation
     * @model instanceClass="com.tibco.n2.brm.api.WorkItemScriptOperation"
     *        extendedMetaData="name='WorkItemScriptOperation:Object' baseType='WorkItemScriptOperation'"
     * @generated
     */
    EDataType getWorkItemScriptOperationObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.WorkItemScriptType <em>Work Item Script Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Work Item Script Type Object</em>'.
     * @see com.tibco.n2.brm.api.WorkItemScriptType
     * @model instanceClass="com.tibco.n2.brm.api.WorkItemScriptType"
     *        extendedMetaData="name='WorkItemScriptType:Object' baseType='WorkItemScriptType'"
     * @generated
     */
    EDataType getWorkItemScriptTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.brm.api.WorkItemState <em>Work Item State Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Work Item State Object</em>'.
     * @see com.tibco.n2.brm.api.WorkItemState
     * @model instanceClass="com.tibco.n2.brm.api.WorkItemState"
     *        extendedMetaData="name='WorkItemState:Object' baseType='WorkItemState'"
     * @generated
     */
    EDataType getWorkItemStateObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    N2BRMFactory getN2BRMFactory();

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
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AddCurrentResourceToViewResponseTypeImpl <em>Add Current Resource To View Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AddCurrentResourceToViewResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAddCurrentResourceToViewResponseType()
         * @generated
         */
        EClass ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE = eINSTANCE.getAddCurrentResourceToViewResponseType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getAddCurrentResourceToViewResponseType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AddCurrentResourceToViewTypeImpl <em>Add Current Resource To View Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AddCurrentResourceToViewTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAddCurrentResourceToViewType()
         * @generated
         */
        EClass ADD_CURRENT_RESOURCE_TO_VIEW_TYPE = eINSTANCE.getAddCurrentResourceToViewType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ADD_CURRENT_RESOURCE_TO_VIEW_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getAddCurrentResourceToViewType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemResponseTypeImpl <em>Allocate And Open Next Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateAndOpenNextWorkItemResponseType()
         * @generated
         */
        EClass ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getAllocateAndOpenNextWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = eINSTANCE.getAllocateAndOpenNextWorkItemResponseType_WorkItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemTypeImpl <em>Allocate And Open Next Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AllocateAndOpenNextWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateAndOpenNextWorkItemType()
         * @generated
         */
        EClass ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE = eINSTANCE.getAllocateAndOpenNextWorkItemType();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE__RESOURCE = eINSTANCE.getAllocateAndOpenNextWorkItemType_Resource();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getAllocateAndOpenNextWorkItemType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AllocateAndOpenWorkItemResponseTypeImpl <em>Allocate And Open Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AllocateAndOpenWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateAndOpenWorkItemResponseType()
         * @generated
         */
        EClass ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getAllocateAndOpenWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE__GROUP = eINSTANCE.getAllocateAndOpenWorkItemResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = eINSTANCE.getAllocateAndOpenWorkItemResponseType_WorkItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AllocateAndOpenWorkItemTypeImpl <em>Allocate And Open Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AllocateAndOpenWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateAndOpenWorkItemType()
         * @generated
         */
        EClass ALLOCATE_AND_OPEN_WORK_ITEM_TYPE = eINSTANCE.getAllocateAndOpenWorkItemType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__GROUP = eINSTANCE.getAllocateAndOpenWorkItemType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getAllocateAndOpenWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__RESOURCE = eINSTANCE.getAllocateAndOpenWorkItemType_Resource();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AllocateWorkItemResponseTypeImpl <em>Allocate Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AllocateWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateWorkItemResponseType()
         * @generated
         */
        EClass ALLOCATE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getAllocateWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP = eINSTANCE.getAllocateWorkItemResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = eINSTANCE.getAllocateWorkItemResponseType_WorkItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AllocateWorkItemTypeImpl <em>Allocate Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AllocateWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocateWorkItemType()
         * @generated
         */
        EClass ALLOCATE_WORK_ITEM_TYPE = eINSTANCE.getAllocateWorkItemType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATE_WORK_ITEM_TYPE__GROUP = eINSTANCE.getAllocateWorkItemType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getAllocateWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATE_WORK_ITEM_TYPE__RESOURCE = eINSTANCE.getAllocateWorkItemType_Resource();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AllocationHistoryImpl <em>Allocation History</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AllocationHistoryImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAllocationHistory()
         * @generated
         */
        EClass ALLOCATION_HISTORY = eINSTANCE.getAllocationHistory();

        /**
         * The meta object literal for the '<em><b>Resource ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATION_HISTORY__RESOURCE_ID = eINSTANCE.getAllocationHistory_ResourceID();

        /**
         * The meta object literal for the '<em><b>Allocation Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATION_HISTORY__ALLOCATION_DATE = eINSTANCE.getAllocationHistory_AllocationDate();

        /**
         * The meta object literal for the '<em><b>Allocation ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCATION_HISTORY__ALLOCATION_ID = eINSTANCE.getAllocationHistory_AllocationID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncCancelWorkItemResponseTypeImpl <em>Async Cancel Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncCancelWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncCancelWorkItemResponseType()
         * @generated
         */
        EClass ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getAsyncCancelWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncCancelWorkItemResponseType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Error Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = eINSTANCE.getAsyncCancelWorkItemResponseType_ErrorMessage();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncCancelWorkItemTypeImpl <em>Async Cancel Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncCancelWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncCancelWorkItemType()
         * @generated
         */
        EClass ASYNC_CANCEL_WORK_ITEM_TYPE = eINSTANCE.getAsyncCancelWorkItemType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_CANCEL_WORK_ITEM_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncCancelWorkItemType_MessageDetails();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncEndGroupResponseTypeImpl <em>Async End Group Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncEndGroupResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncEndGroupResponseType()
         * @generated
         */
        EClass ASYNC_END_GROUP_RESPONSE_TYPE = eINSTANCE.getAsyncEndGroupResponseType();

        /**
         * The meta object literal for the '<em><b>Activity ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_END_GROUP_RESPONSE_TYPE__ACTIVITY_ID = eINSTANCE.getAsyncEndGroupResponseType_ActivityID();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_END_GROUP_RESPONSE_TYPE__GROUP_ID = eINSTANCE.getAsyncEndGroupResponseType_GroupID();

        /**
         * The meta object literal for the '<em><b>Error Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_END_GROUP_RESPONSE_TYPE__ERROR_MESSAGE = eINSTANCE.getAsyncEndGroupResponseType_ErrorMessage();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncEndGroupTypeImpl <em>Async End Group Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncEndGroupTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncEndGroupType()
         * @generated
         */
        EClass ASYNC_END_GROUP_TYPE = eINSTANCE.getAsyncEndGroupType();

        /**
         * The meta object literal for the '<em><b>Activity ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_END_GROUP_TYPE__ACTIVITY_ID = eINSTANCE.getAsyncEndGroupType_ActivityID();

        /**
         * The meta object literal for the '<em><b>End Group</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_END_GROUP_TYPE__END_GROUP = eINSTANCE.getAsyncEndGroupType_EndGroup();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncRescheduleWorkItemResponseTypeImpl <em>Async Reschedule Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncRescheduleWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncRescheduleWorkItemResponseType()
         * @generated
         */
        EClass ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getAsyncRescheduleWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncRescheduleWorkItemResponseType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Error Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = eINSTANCE.getAsyncRescheduleWorkItemResponseType_ErrorMessage();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncRescheduleWorkItemTypeImpl <em>Async Reschedule Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncRescheduleWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncRescheduleWorkItemType()
         * @generated
         */
        EClass ASYNC_RESCHEDULE_WORK_ITEM_TYPE = eINSTANCE.getAsyncRescheduleWorkItemType();

        /**
         * The meta object literal for the '<em><b>Item Schedule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_RESCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE = eINSTANCE.getAsyncRescheduleWorkItemType_ItemSchedule();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_RESCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncRescheduleWorkItemType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Item Body</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_RESCHEDULE_WORK_ITEM_TYPE__ITEM_BODY = eINSTANCE.getAsyncRescheduleWorkItemType_ItemBody();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncResumeWorkItemResponseTypeImpl <em>Async Resume Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncResumeWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncResumeWorkItemResponseType()
         * @generated
         */
        EClass ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getAsyncResumeWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncResumeWorkItemResponseType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Error Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = eINSTANCE.getAsyncResumeWorkItemResponseType_ErrorMessage();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncResumeWorkItemTypeImpl <em>Async Resume Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncResumeWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncResumeWorkItemType()
         * @generated
         */
        EClass ASYNC_RESUME_WORK_ITEM_TYPE = eINSTANCE.getAsyncResumeWorkItemType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncResumeWorkItemType_MessageDetails();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemResponseTypeImpl <em>Async Schedule Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncScheduleWorkItemResponseType()
         * @generated
         */
        EClass ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getAsyncScheduleWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncScheduleWorkItemResponseType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Error Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = eINSTANCE.getAsyncScheduleWorkItemResponseType_ErrorMessage();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemTypeImpl <em>Async Schedule Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncScheduleWorkItemType()
         * @generated
         */
        EClass ASYNC_SCHEDULE_WORK_ITEM_TYPE = eINSTANCE.getAsyncScheduleWorkItemType();

        /**
         * The meta object literal for the '<em><b>Schedule Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM = eINSTANCE.getAsyncScheduleWorkItemType_ScheduleWorkItem();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncScheduleWorkItemType_MessageDetails();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelResponseTypeImpl <em>Async Schedule Work Item With Model Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncScheduleWorkItemWithModelResponseType()
         * @generated
         */
        EClass ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE = eINSTANCE.getAsyncScheduleWorkItemWithModelResponseType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncScheduleWorkItemWithModelResponseType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Error Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__ERROR_MESSAGE = eINSTANCE.getAsyncScheduleWorkItemWithModelResponseType_ErrorMessage();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelTypeImpl <em>Async Schedule Work Item With Model Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncScheduleWorkItemWithModelTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncScheduleWorkItemWithModelType()
         * @generated
         */
        EClass ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE = eINSTANCE.getAsyncScheduleWorkItemWithModelType();

        /**
         * The meta object literal for the '<em><b>Schedule Work Item With Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL = eINSTANCE.getAsyncScheduleWorkItemWithModelType_ScheduleWorkItemWithModel();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncScheduleWorkItemWithModelType_MessageDetails();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncStartGroupResponseTypeImpl <em>Async Start Group Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncStartGroupResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncStartGroupResponseType()
         * @generated
         */
        EClass ASYNC_START_GROUP_RESPONSE_TYPE = eINSTANCE.getAsyncStartGroupResponseType();

        /**
         * The meta object literal for the '<em><b>Activity ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_START_GROUP_RESPONSE_TYPE__ACTIVITY_ID = eINSTANCE.getAsyncStartGroupResponseType_ActivityID();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_START_GROUP_RESPONSE_TYPE__GROUP_ID = eINSTANCE.getAsyncStartGroupResponseType_GroupID();

        /**
         * The meta object literal for the '<em><b>Error Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_START_GROUP_RESPONSE_TYPE__ERROR_MESSAGE = eINSTANCE.getAsyncStartGroupResponseType_ErrorMessage();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncStartGroupTypeImpl <em>Async Start Group Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncStartGroupTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncStartGroupType()
         * @generated
         */
        EClass ASYNC_START_GROUP_TYPE = eINSTANCE.getAsyncStartGroupType();

        /**
         * The meta object literal for the '<em><b>Activity ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_START_GROUP_TYPE__ACTIVITY_ID = eINSTANCE.getAsyncStartGroupType_ActivityID();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_START_GROUP_TYPE__GROUP_ID = eINSTANCE.getAsyncStartGroupType_GroupID();

        /**
         * The meta object literal for the '<em><b>Start Group</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_START_GROUP_TYPE__START_GROUP = eINSTANCE.getAsyncStartGroupType_StartGroup();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncSuspendWorkItemResponseTypeImpl <em>Async Suspend Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncSuspendWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncSuspendWorkItemResponseType()
         * @generated
         */
        EClass ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getAsyncSuspendWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncSuspendWorkItemResponseType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Error Message</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE = eINSTANCE.getAsyncSuspendWorkItemResponseType_ErrorMessage();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncSuspendWorkItemTypeImpl <em>Async Suspend Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncSuspendWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncSuspendWorkItemType()
         * @generated
         */
        EClass ASYNC_SUSPEND_WORK_ITEM_TYPE = eINSTANCE.getAsyncSuspendWorkItemType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_SUSPEND_WORK_ITEM_TYPE__MESSAGE_DETAILS = eINSTANCE.getAsyncSuspendWorkItemType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Force Suspend</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND = eINSTANCE.getAsyncSuspendWorkItemType_ForceSuspend();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AsyncWorkItemDetailsImpl <em>Async Work Item Details</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AsyncWorkItemDetailsImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAsyncWorkItemDetails()
         * @generated
         */
        EClass ASYNC_WORK_ITEM_DETAILS = eINSTANCE.getAsyncWorkItemDetails();

        /**
         * The meta object literal for the '<em><b>Work Item Id</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID = eINSTANCE.getAsyncWorkItemDetails_WorkItemId();

        /**
         * The meta object literal for the '<em><b>Activity ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_WORK_ITEM_DETAILS__ACTIVITY_ID = eINSTANCE.getAsyncWorkItemDetails_ActivityID();

        /**
         * The meta object literal for the '<em><b>Ua Sequence Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID = eINSTANCE.getAsyncWorkItemDetails_UaSequenceId();

        /**
         * The meta object literal for the '<em><b>Brm Sequence Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID = eINSTANCE.getAsyncWorkItemDetails_BrmSequenceId();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.AttributeAliasListTypeImpl <em>Attribute Alias List Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.AttributeAliasListTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttributeAliasListType()
         * @generated
         */
        EClass ATTRIBUTE_ALIAS_LIST_TYPE = eINSTANCE.getAttributeAliasListType();

        /**
         * The meta object literal for the '<em><b>Attribute Alias</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS = eINSTANCE.getAttributeAliasListType_AttributeAlias();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.BaseItemInfoImpl <em>Base Item Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.BaseItemInfoImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getBaseItemInfo()
         * @generated
         */
        EClass BASE_ITEM_INFO = eINSTANCE.getBaseItemInfo();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ITEM_INFO__NAME = eINSTANCE.getBaseItemInfo_Name();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ITEM_INFO__DESCRIPTION = eINSTANCE.getBaseItemInfo_Description();

        /**
         * The meta object literal for the '<em><b>Distribution Strategy</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ITEM_INFO__DISTRIBUTION_STRATEGY = eINSTANCE.getBaseItemInfo_DistributionStrategy();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ITEM_INFO__GROUP_ID = eINSTANCE.getBaseItemInfo_GroupID();

        /**
         * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ITEM_INFO__PRIORITY = eINSTANCE.getBaseItemInfo_Priority();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.BaseModelInfoImpl <em>Base Model Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.BaseModelInfoImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getBaseModelInfo()
         * @generated
         */
        EClass BASE_MODEL_INFO = eINSTANCE.getBaseModelInfo();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_MODEL_INFO__DESCRIPTION = eINSTANCE.getBaseModelInfo_Description();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_MODEL_INFO__NAME = eINSTANCE.getBaseModelInfo_Name();

        /**
         * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_MODEL_INFO__PRIORITY = eINSTANCE.getBaseModelInfo_Priority();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.CancelWorkItemResponseTypeImpl <em>Cancel Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.CancelWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCancelWorkItemResponseType()
         * @generated
         */
        EClass CANCEL_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getCancelWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CANCEL_WORK_ITEM_RESPONSE_TYPE__SUCCESS = eINSTANCE.getCancelWorkItemResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.CancelWorkItemTypeImpl <em>Cancel Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.CancelWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCancelWorkItemType()
         * @generated
         */
        EClass CANCEL_WORK_ITEM_TYPE = eINSTANCE.getCancelWorkItemType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CANCEL_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getCancelWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ChainedWorkItemNotificationTypeImpl <em>Chained Work Item Notification Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ChainedWorkItemNotificationTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getChainedWorkItemNotificationType()
         * @generated
         */
        EClass CHAINED_WORK_ITEM_NOTIFICATION_TYPE = eINSTANCE.getChainedWorkItemNotificationType();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHAINED_WORK_ITEM_NOTIFICATION_TYPE__GROUP_ID = eINSTANCE.getChainedWorkItemNotificationType_GroupID();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHAINED_WORK_ITEM_NOTIFICATION_TYPE__WORK_ITEM_ID = eINSTANCE.getChainedWorkItemNotificationType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.CloseWorkItemResponseTypeImpl <em>Close Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.CloseWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCloseWorkItemResponseType()
         * @generated
         */
        EClass CLOSE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getCloseWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CLOSE_WORK_ITEM_RESPONSE_TYPE__GROUP = eINSTANCE.getCloseWorkItemResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CLOSE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID = eINSTANCE.getCloseWorkItemResponseType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.CloseWorkItemTypeImpl <em>Close Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.CloseWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCloseWorkItemType()
         * @generated
         */
        EClass CLOSE_WORK_ITEM_TYPE = eINSTANCE.getCloseWorkItemType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CLOSE_WORK_ITEM_TYPE__GROUP = eINSTANCE.getCloseWorkItemType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CLOSE_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getCloseWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Work Item Payload</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CLOSE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD = eINSTANCE.getCloseWorkItemType_WorkItemPayload();

        /**
         * The meta object literal for the '<em><b>Hidden Period</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CLOSE_WORK_ITEM_TYPE__HIDDEN_PERIOD = eINSTANCE.getCloseWorkItemType_HiddenPeriod();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ColumnDefinitionImpl <em>Column Definition</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ColumnDefinitionImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnDefinition()
         * @generated
         */
        EClass COLUMN_DEFINITION = eINSTANCE.getColumnDefinition();

        /**
         * The meta object literal for the '<em><b>Capability</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COLUMN_DEFINITION__CAPABILITY = eINSTANCE.getColumnDefinition_Capability();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COLUMN_DEFINITION__DESCRIPTION = eINSTANCE.getColumnDefinition_Description();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COLUMN_DEFINITION__ID = eINSTANCE.getColumnDefinition_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COLUMN_DEFINITION__NAME = eINSTANCE.getColumnDefinition_Name();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COLUMN_DEFINITION__TYPE = eINSTANCE.getColumnDefinition_Type();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ColumnOrderImpl <em>Column Order</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ColumnOrderImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnOrder()
         * @generated
         */
        EClass COLUMN_ORDER = eINSTANCE.getColumnOrder();

        /**
         * The meta object literal for the '<em><b>Column Def</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COLUMN_ORDER__COLUMN_DEF = eINSTANCE.getColumnOrder_ColumnDef();

        /**
         * The meta object literal for the '<em><b>Ascending</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COLUMN_ORDER__ASCENDING = eINSTANCE.getColumnOrder_Ascending();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.CompleteWorkItemResponseTypeImpl <em>Complete Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.CompleteWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCompleteWorkItemResponseType()
         * @generated
         */
        EClass COMPLETE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getCompleteWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID = eINSTANCE.getCompleteWorkItemResponseType_GroupID();

        /**
         * The meta object literal for the '<em><b>Next Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM = eINSTANCE.getCompleteWorkItemResponseType_NextWorkItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.CompleteWorkItemTypeImpl <em>Complete Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.CompleteWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCompleteWorkItemType()
         * @generated
         */
        EClass COMPLETE_WORK_ITEM_TYPE = eINSTANCE.getCompleteWorkItemType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getCompleteWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Work Item Payload</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD = eINSTANCE.getCompleteWorkItemType_WorkItemPayload();

        /**
         * The meta object literal for the '<em><b>Get Next Piled Item</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM = eINSTANCE.getCompleteWorkItemType_GetNextPiledItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.CreateWorkListViewResponseTypeImpl <em>Create Work List View Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.CreateWorkListViewResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getCreateWorkListViewResponseType()
         * @generated
         */
        EClass CREATE_WORK_LIST_VIEW_RESPONSE_TYPE = eINSTANCE.getCreateWorkListViewResponseType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CREATE_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getCreateWorkListViewResponseType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.DeleteCurrentResourceFromViewResponseTypeImpl <em>Delete Current Resource From View Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.DeleteCurrentResourceFromViewResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteCurrentResourceFromViewResponseType()
         * @generated
         */
        EClass DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE = eINSTANCE.getDeleteCurrentResourceFromViewResponseType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getDeleteCurrentResourceFromViewResponseType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.DeleteCurrentResourceFromViewTypeImpl <em>Delete Current Resource From View Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.DeleteCurrentResourceFromViewTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteCurrentResourceFromViewType()
         * @generated
         */
        EClass DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE = eINSTANCE.getDeleteCurrentResourceFromViewType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getDeleteCurrentResourceFromViewType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.DeleteOrgEntityConfigAttributesResponseTypeImpl <em>Delete Org Entity Config Attributes Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.DeleteOrgEntityConfigAttributesResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteOrgEntityConfigAttributesResponseType()
         * @generated
         */
        EClass DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE = eINSTANCE.getDeleteOrgEntityConfigAttributesResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS = eINSTANCE.getDeleteOrgEntityConfigAttributesResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.DeleteOrgEntityConfigAttributesTypeImpl <em>Delete Org Entity Config Attributes Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.DeleteOrgEntityConfigAttributesTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteOrgEntityConfigAttributesType()
         * @generated
         */
        EClass DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE = eINSTANCE.getDeleteOrgEntityConfigAttributesType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP = eINSTANCE.getDeleteOrgEntityConfigAttributesType_Group();

        /**
         * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ATTRIBUTE_NAME = eINSTANCE.getDeleteOrgEntityConfigAttributesType_AttributeName();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE = eINSTANCE.getDeleteOrgEntityConfigAttributesType_Resource();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.DeleteWorkListViewResponseTypeImpl <em>Delete Work List View Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.DeleteWorkListViewResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteWorkListViewResponseType()
         * @generated
         */
        EClass DELETE_WORK_LIST_VIEW_RESPONSE_TYPE = eINSTANCE.getDeleteWorkListViewResponseType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getDeleteWorkListViewResponseType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.DeleteWorkListViewTypeImpl <em>Delete Work List View Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.DeleteWorkListViewTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDeleteWorkListViewType()
         * @generated
         */
        EClass DELETE_WORK_LIST_VIEW_TYPE = eINSTANCE.getDeleteWorkListViewType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DELETE_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getDeleteWorkListViewType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.DocumentRootImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDocumentRoot()
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
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Add Current Resource To View</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW = eINSTANCE.getDocumentRoot_AddCurrentResourceToView();

        /**
         * The meta object literal for the '<em><b>Add Current Resource To View Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE = eINSTANCE.getDocumentRoot_AddCurrentResourceToViewResponse();

        /**
         * The meta object literal for the '<em><b>Allocate And Open Next Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM = eINSTANCE.getDocumentRoot_AllocateAndOpenNextWorkItem();

        /**
         * The meta object literal for the '<em><b>Allocate And Open Next Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_AllocateAndOpenNextWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Allocate And Open Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM = eINSTANCE.getDocumentRoot_AllocateAndOpenWorkItem();

        /**
         * The meta object literal for the '<em><b>Allocate And Open Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_AllocateAndOpenWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Allocate Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ALLOCATE_WORK_ITEM = eINSTANCE.getDocumentRoot_AllocateWorkItem();

        /**
         * The meta object literal for the '<em><b>Allocate Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_AllocateWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Async Cancel Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM = eINSTANCE.getDocumentRoot_AsyncCancelWorkItem();

        /**
         * The meta object literal for the '<em><b>Async Cancel Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_AsyncCancelWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Async End Group</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_END_GROUP = eINSTANCE.getDocumentRoot_AsyncEndGroup();

        /**
         * The meta object literal for the '<em><b>Async End Group Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE = eINSTANCE.getDocumentRoot_AsyncEndGroupResponse();

        /**
         * The meta object literal for the '<em><b>Async Reschedule Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM = eINSTANCE.getDocumentRoot_AsyncRescheduleWorkItem();

        /**
         * The meta object literal for the '<em><b>Async Reschedule Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_AsyncRescheduleWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Async Resume Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM = eINSTANCE.getDocumentRoot_AsyncResumeWorkItem();

        /**
         * The meta object literal for the '<em><b>Async Resume Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_AsyncResumeWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Async Schedule Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM = eINSTANCE.getDocumentRoot_AsyncScheduleWorkItem();

        /**
         * The meta object literal for the '<em><b>Async Schedule Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_AsyncScheduleWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Async Schedule Work Item With Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL = eINSTANCE.getDocumentRoot_AsyncScheduleWorkItemWithModel();

        /**
         * The meta object literal for the '<em><b>Async Schedule Work Item With Model Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE = eINSTANCE.getDocumentRoot_AsyncScheduleWorkItemWithModelResponse();

        /**
         * The meta object literal for the '<em><b>Async Start Group</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_START_GROUP = eINSTANCE.getDocumentRoot_AsyncStartGroup();

        /**
         * The meta object literal for the '<em><b>Async Start Group Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE = eINSTANCE.getDocumentRoot_AsyncStartGroupResponse();

        /**
         * The meta object literal for the '<em><b>Async Suspend Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM = eINSTANCE.getDocumentRoot_AsyncSuspendWorkItem();

        /**
         * The meta object literal for the '<em><b>Async Suspend Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_AsyncSuspendWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Cancel Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CANCEL_WORK_ITEM = eINSTANCE.getDocumentRoot_CancelWorkItem();

        /**
         * The meta object literal for the '<em><b>Cancel Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_CancelWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Chained Work Item Notification</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION = eINSTANCE.getDocumentRoot_ChainedWorkItemNotification();

        /**
         * The meta object literal for the '<em><b>Close Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CLOSE_WORK_ITEM = eINSTANCE.getDocumentRoot_CloseWorkItem();

        /**
         * The meta object literal for the '<em><b>Close Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_CloseWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Complete Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__COMPLETE_WORK_ITEM = eINSTANCE.getDocumentRoot_CompleteWorkItem();

        /**
         * The meta object literal for the '<em><b>Complete Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_CompleteWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Create Work List View</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW = eINSTANCE.getDocumentRoot_CreateWorkListView();

        /**
         * The meta object literal for the '<em><b>Create Work List View Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE = eINSTANCE.getDocumentRoot_CreateWorkListViewResponse();

        /**
         * The meta object literal for the '<em><b>Delete Current Resource From View</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW = eINSTANCE.getDocumentRoot_DeleteCurrentResourceFromView();

        /**
         * The meta object literal for the '<em><b>Delete Current Resource From View Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE = eINSTANCE.getDocumentRoot_DeleteCurrentResourceFromViewResponse();

        /**
         * The meta object literal for the '<em><b>Delete Org Entity Config Attributes</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES = eINSTANCE.getDocumentRoot_DeleteOrgEntityConfigAttributes();

        /**
         * The meta object literal for the '<em><b>Delete Org Entity Config Attributes Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE = eINSTANCE.getDocumentRoot_DeleteOrgEntityConfigAttributesResponse();

        /**
         * The meta object literal for the '<em><b>Delete Work List View</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW = eINSTANCE.getDocumentRoot_DeleteWorkListView();

        /**
         * The meta object literal for the '<em><b>Delete Work List View Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE = eINSTANCE.getDocumentRoot_DeleteWorkListViewResponse();

        /**
         * The meta object literal for the '<em><b>Edit Work List View</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW = eINSTANCE.getDocumentRoot_EditWorkListView();

        /**
         * The meta object literal for the '<em><b>Edit Work List View Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE = eINSTANCE.getDocumentRoot_EditWorkListViewResponse();

        /**
         * The meta object literal for the '<em><b>Enable Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ENABLE_WORK_ITEM = eINSTANCE.getDocumentRoot_EnableWorkItem();

        /**
         * The meta object literal for the '<em><b>Enable Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_EnableWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>End Group</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__END_GROUP = eINSTANCE.getDocumentRoot_EndGroup();

        /**
         * The meta object literal for the '<em><b>End Group Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__END_GROUP_RESPONSE = eINSTANCE.getDocumentRoot_EndGroupResponse();

        /**
         * The meta object literal for the '<em><b>Get Allocated Work List Items</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS = eINSTANCE.getDocumentRoot_GetAllocatedWorkListItems();

        /**
         * The meta object literal for the '<em><b>Get Batch Group Ids</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_BATCH_GROUP_IDS = eINSTANCE.getDocumentRoot_GetBatchGroupIds();

        /**
         * The meta object literal for the '<em><b>Get Batch Group Ids Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE = eINSTANCE.getDocumentRoot_GetBatchGroupIdsResponse();

        /**
         * The meta object literal for the '<em><b>Get Batch Work Item Ids</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS = eINSTANCE.getDocumentRoot_GetBatchWorkItemIds();

        /**
         * The meta object literal for the '<em><b>Get Batch Work Item Ids Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE = eINSTANCE.getDocumentRoot_GetBatchWorkItemIdsResponse();

        /**
         * The meta object literal for the '<em><b>Get Editable Work List Views</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS = eINSTANCE.getDocumentRoot_GetEditableWorkListViews();

        /**
         * The meta object literal for the '<em><b>Get Editable Work List Views Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE = eINSTANCE.getDocumentRoot_GetEditableWorkListViewsResponse();

        /**
         * The meta object literal for the '<em><b>Get Offer Set</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_OFFER_SET = eINSTANCE.getDocumentRoot_GetOfferSet();

        /**
         * The meta object literal for the '<em><b>Get Offer Set Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE = eINSTANCE.getDocumentRoot_GetOfferSetResponse();

        /**
         * The meta object literal for the '<em><b>Get Org Entity Config Attributes</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES = eINSTANCE.getDocumentRoot_GetOrgEntityConfigAttributes();

        /**
         * The meta object literal for the '<em><b>Get Org Entity Config Attributes Available</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE = eINSTANCE.getDocumentRoot_GetOrgEntityConfigAttributesAvailable();

        /**
         * The meta object literal for the '<em><b>Get Org Entity Config Attributes Available Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE = eINSTANCE.getDocumentRoot_GetOrgEntityConfigAttributesAvailableResponse();

        /**
         * The meta object literal for the '<em><b>Get Org Entity Config Attributes Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE = eINSTANCE.getDocumentRoot_GetOrgEntityConfigAttributesResponse();

        /**
         * The meta object literal for the '<em><b>Get Public Work List Views</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS = eINSTANCE.getDocumentRoot_GetPublicWorkListViews();

        /**
         * The meta object literal for the '<em><b>Get Public Work List Views Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE = eINSTANCE.getDocumentRoot_GetPublicWorkListViewsResponse();

        /**
         * The meta object literal for the '<em><b>Get Resource Order Filter Criteria</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA = eINSTANCE.getDocumentRoot_GetResourceOrderFilterCriteria();

        /**
         * The meta object literal for the '<em><b>Get Resource Order Filter Criteria Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE = eINSTANCE.getDocumentRoot_GetResourceOrderFilterCriteriaResponse();

        /**
         * The meta object literal for the '<em><b>Get Views For Resource</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE = eINSTANCE.getDocumentRoot_GetViewsForResource();

        /**
         * The meta object literal for the '<em><b>Get Views For Resource Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE = eINSTANCE.getDocumentRoot_GetViewsForResourceResponse();

        /**
         * The meta object literal for the '<em><b>Get Work Item Header</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_ITEM_HEADER = eINSTANCE.getDocumentRoot_GetWorkItemHeader();

        /**
         * The meta object literal for the '<em><b>Get Work Item Header Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkItemHeaderResponse();

        /**
         * The meta object literal for the '<em><b>Get Work Item Order Filter</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER = eINSTANCE.getDocumentRoot_GetWorkItemOrderFilter();

        /**
         * The meta object literal for the '<em><b>Get Work Item Order Filter Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkItemOrderFilterResponse();

        /**
         * The meta object literal for the '<em><b>Get Work List Items</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_LIST_ITEMS = eINSTANCE.getDocumentRoot_GetWorkListItems();

        /**
         * The meta object literal for the '<em><b>Get Work List Items For Global Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA = eINSTANCE.getDocumentRoot_GetWorkListItemsForGlobalData();

        /**
         * The meta object literal for the '<em><b>Get Work List Items For Global Data Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkListItemsForGlobalDataResponse();

        /**
         * The meta object literal for the '<em><b>Get Work List Items For View</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW = eINSTANCE.getDocumentRoot_GetWorkListItemsForView();

        /**
         * The meta object literal for the '<em><b>Get Work List Items For View Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkListItemsForViewResponse();

        /**
         * The meta object literal for the '<em><b>Get Work List Items Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkListItemsResponse();

        /**
         * The meta object literal for the '<em><b>Get Work List View Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS = eINSTANCE.getDocumentRoot_GetWorkListViewDetails();

        /**
         * The meta object literal for the '<em><b>Get Work List View Details Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkListViewDetailsResponse();

        /**
         * The meta object literal for the '<em><b>Get Work Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_MODEL = eINSTANCE.getDocumentRoot_GetWorkModel();

        /**
         * The meta object literal for the '<em><b>Get Work Model Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkModelResponse();

        /**
         * The meta object literal for the '<em><b>Get Work Models</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_MODELS = eINSTANCE.getDocumentRoot_GetWorkModels();

        /**
         * The meta object literal for the '<em><b>Get Work Models Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkModelsResponse();

        /**
         * The meta object literal for the '<em><b>Get Work Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_TYPE = eINSTANCE.getDocumentRoot_GetWorkType();

        /**
         * The meta object literal for the '<em><b>Get Work Type Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkTypeResponse();

        /**
         * The meta object literal for the '<em><b>Get Work Types</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_TYPES = eINSTANCE.getDocumentRoot_GetWorkTypes();

        /**
         * The meta object literal for the '<em><b>Get Work Types Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE = eINSTANCE.getDocumentRoot_GetWorkTypesResponse();

        /**
         * The meta object literal for the '<em><b>On Notification</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ON_NOTIFICATION = eINSTANCE.getDocumentRoot_OnNotification();

        /**
         * The meta object literal for the '<em><b>On Notification Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE = eINSTANCE.getDocumentRoot_OnNotificationResponse();

        /**
         * The meta object literal for the '<em><b>Open Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__OPEN_WORK_ITEM = eINSTANCE.getDocumentRoot_OpenWorkItem();

        /**
         * The meta object literal for the '<em><b>Open Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_OpenWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Pend Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PEND_WORK_ITEM = eINSTANCE.getDocumentRoot_PendWorkItem();

        /**
         * The meta object literal for the '<em><b>Pend Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_PendWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Preview Work Item From List</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST = eINSTANCE.getDocumentRoot_PreviewWorkItemFromList();

        /**
         * The meta object literal for the '<em><b>Preview Work Item From List Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE = eINSTANCE.getDocumentRoot_PreviewWorkItemFromListResponse();

        /**
         * The meta object literal for the '<em><b>Push Notification</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PUSH_NOTIFICATION = eINSTANCE.getDocumentRoot_PushNotification();

        /**
         * The meta object literal for the '<em><b>Reallocate Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__REALLOCATE_WORK_ITEM = eINSTANCE.getDocumentRoot_ReallocateWorkItem();

        /**
         * The meta object literal for the '<em><b>Reallocate Work Item Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA = eINSTANCE.getDocumentRoot_ReallocateWorkItemData();

        /**
         * The meta object literal for the '<em><b>Reallocate Work Item Data Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE = eINSTANCE.getDocumentRoot_ReallocateWorkItemDataResponse();

        /**
         * The meta object literal for the '<em><b>Reallocate Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_ReallocateWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Reschedule Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM = eINSTANCE.getDocumentRoot_RescheduleWorkItem();

        /**
         * The meta object literal for the '<em><b>Reschedule Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_RescheduleWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Resume Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__RESUME_WORK_ITEM = eINSTANCE.getDocumentRoot_ResumeWorkItem();

        /**
         * The meta object literal for the '<em><b>Resume Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_ResumeWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Save Open Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM = eINSTANCE.getDocumentRoot_SaveOpenWorkItem();

        /**
         * The meta object literal for the '<em><b>Save Open Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_SaveOpenWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Schedule Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SCHEDULE_WORK_ITEM = eINSTANCE.getDocumentRoot_ScheduleWorkItem();

        /**
         * The meta object literal for the '<em><b>Schedule Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_ScheduleWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Schedule Work Item With Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL = eINSTANCE.getDocumentRoot_ScheduleWorkItemWithModel();

        /**
         * The meta object literal for the '<em><b>Schedule Work Item With Model Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE = eINSTANCE.getDocumentRoot_ScheduleWorkItemWithModelResponse();

        /**
         * The meta object literal for the '<em><b>Set Org Entity Config Attributes</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES = eINSTANCE.getDocumentRoot_SetOrgEntityConfigAttributes();

        /**
         * The meta object literal for the '<em><b>Set Org Entity Config Attributes Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE = eINSTANCE.getDocumentRoot_SetOrgEntityConfigAttributesResponse();

        /**
         * The meta object literal for the '<em><b>Set Resource Order Filter Criteria</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA = eINSTANCE.getDocumentRoot_SetResourceOrderFilterCriteria();

        /**
         * The meta object literal for the '<em><b>Set Resource Order Filter Criteria Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE = eINSTANCE.getDocumentRoot_SetResourceOrderFilterCriteriaResponse();

        /**
         * The meta object literal for the '<em><b>Set Work Item Priority</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY = eINSTANCE.getDocumentRoot_SetWorkItemPriority();

        /**
         * The meta object literal for the '<em><b>Set Work Item Priority Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE = eINSTANCE.getDocumentRoot_SetWorkItemPriorityResponse();

        /**
         * The meta object literal for the '<em><b>Skip Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SKIP_WORK_ITEM = eINSTANCE.getDocumentRoot_SkipWorkItem();

        /**
         * The meta object literal for the '<em><b>Skip Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_SkipWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Start Group</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__START_GROUP = eINSTANCE.getDocumentRoot_StartGroup();

        /**
         * The meta object literal for the '<em><b>Start Group Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__START_GROUP_RESPONSE = eINSTANCE.getDocumentRoot_StartGroupResponse();

        /**
         * The meta object literal for the '<em><b>Suspend Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SUSPEND_WORK_ITEM = eINSTANCE.getDocumentRoot_SuspendWorkItem();

        /**
         * The meta object literal for the '<em><b>Suspend Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_SuspendWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Unallocate Work Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM = eINSTANCE.getDocumentRoot_UnallocateWorkItem();

        /**
         * The meta object literal for the '<em><b>Unallocate Work Item Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE = eINSTANCE.getDocumentRoot_UnallocateWorkItemResponse();

        /**
         * The meta object literal for the '<em><b>Unlock Work List View</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW = eINSTANCE.getDocumentRoot_UnlockWorkListView();

        /**
         * The meta object literal for the '<em><b>Unlock Work List View Response</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE = eINSTANCE.getDocumentRoot_UnlockWorkListViewResponse();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.EditWorkListViewResponseTypeImpl <em>Edit Work List View Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.EditWorkListViewResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEditWorkListViewResponseType()
         * @generated
         */
        EClass EDIT_WORK_LIST_VIEW_RESPONSE_TYPE = eINSTANCE.getEditWorkListViewResponseType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getEditWorkListViewResponseType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.EditWorkListViewTypeImpl <em>Edit Work List View Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.EditWorkListViewTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEditWorkListViewType()
         * @generated
         */
        EClass EDIT_WORK_LIST_VIEW_TYPE = eINSTANCE.getEditWorkListViewType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EDIT_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getEditWorkListViewType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.EnableWorkItemResponseTypeImpl <em>Enable Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.EnableWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEnableWorkItemResponseType()
         * @generated
         */
        EClass ENABLE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getEnableWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENABLE_WORK_ITEM_RESPONSE_TYPE__SUCCESS = eINSTANCE.getEnableWorkItemResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.EnableWorkItemTypeImpl <em>Enable Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.EnableWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEnableWorkItemType()
         * @generated
         */
        EClass ENABLE_WORK_ITEM_TYPE = eINSTANCE.getEnableWorkItemType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ENABLE_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getEnableWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Item Body</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ENABLE_WORK_ITEM_TYPE__ITEM_BODY = eINSTANCE.getEnableWorkItemType_ItemBody();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.EndGroupResponseTypeImpl <em>End Group Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.EndGroupResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEndGroupResponseType()
         * @generated
         */
        EClass END_GROUP_RESPONSE_TYPE = eINSTANCE.getEndGroupResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute END_GROUP_RESPONSE_TYPE__SUCCESS = eINSTANCE.getEndGroupResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.EndGroupTypeImpl <em>End Group Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.EndGroupTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getEndGroupType()
         * @generated
         */
        EClass END_GROUP_TYPE = eINSTANCE.getEndGroupType();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute END_GROUP_TYPE__GROUP_ID = eINSTANCE.getEndGroupType_GroupID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetAllocatedWorkListItemsTypeImpl <em>Get Allocated Work List Items Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetAllocatedWorkListItemsTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetAllocatedWorkListItemsType()
         * @generated
         */
        EClass GET_ALLOCATED_WORK_LIST_ITEMS_TYPE = eINSTANCE.getGetAllocatedWorkListItemsType();

        /**
         * The meta object literal for the '<em><b>Entity ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__ENTITY_ID = eINSTANCE.getGetAllocatedWorkListItemsType_EntityID();

        /**
         * The meta object literal for the '<em><b>Order Filter Criteria</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__ORDER_FILTER_CRITERIA = eINSTANCE.getGetAllocatedWorkListItemsType_OrderFilterCriteria();

        /**
         * The meta object literal for the '<em><b>Get Total Count</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__GET_TOTAL_COUNT = eINSTANCE.getGetAllocatedWorkListItemsType_GetTotalCount();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetAllocatedWorkListItemsType_NumberOfItems();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__START_POSITION = eINSTANCE.getGetAllocatedWorkListItemsType_StartPosition();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetBatchGroupIdsResponseTypeImpl <em>Get Batch Group Ids Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetBatchGroupIdsResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetBatchGroupIdsResponseType()
         * @generated
         */
        EClass GET_BATCH_GROUP_IDS_RESPONSE_TYPE = eINSTANCE.getGetBatchGroupIdsResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_BATCH_GROUP_IDS_RESPONSE_TYPE__GROUP = eINSTANCE.getGetBatchGroupIdsResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_BATCH_GROUP_IDS_RESPONSE_TYPE__GROUP_ID = eINSTANCE.getGetBatchGroupIdsResponseType_GroupID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetBatchWorkItemIdsResponseTypeImpl <em>Get Batch Work Item Ids Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetBatchWorkItemIdsResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetBatchWorkItemIdsResponseType()
         * @generated
         */
        EClass GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE = eINSTANCE.getGetBatchWorkItemIdsResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE__GROUP = eINSTANCE.getGetBatchWorkItemIdsResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE__WORK_ITEM_ID = eINSTANCE.getGetBatchWorkItemIdsResponseType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetEditableWorkListViewsResponseTypeImpl <em>Get Editable Work List Views Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetEditableWorkListViewsResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetEditableWorkListViewsResponseType()
         * @generated
         */
        EClass GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE = eINSTANCE.getGetEditableWorkListViewsResponseType();

        /**
         * The meta object literal for the '<em><b>Work List Views</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS = eINSTANCE.getGetEditableWorkListViewsResponseType_WorkListViews();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetEditableWorkListViewsTypeImpl <em>Get Editable Work List Views Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetEditableWorkListViewsTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetEditableWorkListViewsType()
         * @generated
         */
        EClass GET_EDITABLE_WORK_LIST_VIEWS_TYPE = eINSTANCE.getGetEditableWorkListViewsType();

        /**
         * The meta object literal for the '<em><b>Api Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_EDITABLE_WORK_LIST_VIEWS_TYPE__API_VERSION = eINSTANCE.getGetEditableWorkListViewsType_ApiVersion();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_EDITABLE_WORK_LIST_VIEWS_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetEditableWorkListViewsType_NumberOfItems();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_EDITABLE_WORK_LIST_VIEWS_TYPE__START_POSITION = eINSTANCE.getGetEditableWorkListViewsType_StartPosition();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetOfferSetResponseTypeImpl <em>Get Offer Set Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetOfferSetResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOfferSetResponseType()
         * @generated
         */
        EClass GET_OFFER_SET_RESPONSE_TYPE = eINSTANCE.getGetOfferSetResponseType();

        /**
         * The meta object literal for the '<em><b>Entity Guid</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_OFFER_SET_RESPONSE_TYPE__ENTITY_GUID = eINSTANCE.getGetOfferSetResponseType_EntityGuid();

        /**
         * The meta object literal for the '<em><b>Entity Id</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID = eINSTANCE.getGetOfferSetResponseType_EntityId();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetOfferSetTypeImpl <em>Get Offer Set Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetOfferSetTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOfferSetType()
         * @generated
         */
        EClass GET_OFFER_SET_TYPE = eINSTANCE.getGetOfferSetType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_OFFER_SET_TYPE__WORK_ITEM_ID = eINSTANCE.getGetOfferSetType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Api Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_OFFER_SET_TYPE__API_VERSION = eINSTANCE.getGetOfferSetType_ApiVersion();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableResponseTypeImpl <em>Get Org Entity Config Attributes Available Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOrgEntityConfigAttributesAvailableResponseType()
         * @generated
         */
        EClass GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE = eINSTANCE.getGetOrgEntityConfigAttributesAvailableResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE__GROUP = eINSTANCE.getGetOrgEntityConfigAttributesAvailableResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Org Entity Config Attributes Available</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE = eINSTANCE.getGetOrgEntityConfigAttributesAvailableResponseType_OrgEntityConfigAttributesAvailable();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableTypeImpl <em>Get Org Entity Config Attributes Available Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesAvailableTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOrgEntityConfigAttributesAvailableType()
         * @generated
         */
        EClass GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE = eINSTANCE.getGetOrgEntityConfigAttributesAvailableType();

        /**
         * The meta object literal for the '<em><b>Start At</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT = eINSTANCE.getGetOrgEntityConfigAttributesAvailableType_StartAt();

        /**
         * The meta object literal for the '<em><b>Num To Return</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN = eINSTANCE.getGetOrgEntityConfigAttributesAvailableType_NumToReturn();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesResponseTypeImpl <em>Get Org Entity Config Attributes Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOrgEntityConfigAttributesResponseType()
         * @generated
         */
        EClass GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE = eINSTANCE.getGetOrgEntityConfigAttributesResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__GROUP = eINSTANCE.getGetOrgEntityConfigAttributesResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Org Entity Config Attribute</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE = eINSTANCE.getGetOrgEntityConfigAttributesResponseType_OrgEntityConfigAttribute();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesTypeImpl <em>Get Org Entity Config Attributes Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetOrgEntityConfigAttributesTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetOrgEntityConfigAttributesType()
         * @generated
         */
        EClass GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE = eINSTANCE.getGetOrgEntityConfigAttributesType();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE = eINSTANCE.getGetOrgEntityConfigAttributesType_Resource();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetPublicWorkListViewsResponseTypeImpl <em>Get Public Work List Views Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetPublicWorkListViewsResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetPublicWorkListViewsResponseType()
         * @generated
         */
        EClass GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE = eINSTANCE.getGetPublicWorkListViewsResponseType();

        /**
         * The meta object literal for the '<em><b>Work List Views</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS = eINSTANCE.getGetPublicWorkListViewsResponseType_WorkListViews();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetPublicWorkListViewsTypeImpl <em>Get Public Work List Views Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetPublicWorkListViewsTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetPublicWorkListViewsType()
         * @generated
         */
        EClass GET_PUBLIC_WORK_LIST_VIEWS_TYPE = eINSTANCE.getGetPublicWorkListViewsType();

        /**
         * The meta object literal for the '<em><b>Api Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_PUBLIC_WORK_LIST_VIEWS_TYPE__API_VERSION = eINSTANCE.getGetPublicWorkListViewsType_ApiVersion();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_PUBLIC_WORK_LIST_VIEWS_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetPublicWorkListViewsType_NumberOfItems();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_PUBLIC_WORK_LIST_VIEWS_TYPE__START_POSITION = eINSTANCE.getGetPublicWorkListViewsType_StartPosition();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaResponseTypeImpl <em>Get Resource Order Filter Criteria Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetResourceOrderFilterCriteriaResponseType()
         * @generated
         */
        EClass GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE = eINSTANCE.getGetResourceOrderFilterCriteriaResponseType();

        /**
         * The meta object literal for the '<em><b>Order Filter Criteria</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE__ORDER_FILTER_CRITERIA = eINSTANCE.getGetResourceOrderFilterCriteriaResponseType_OrderFilterCriteria();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaTypeImpl <em>Get Resource Order Filter Criteria Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetResourceOrderFilterCriteriaTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetResourceOrderFilterCriteriaType()
         * @generated
         */
        EClass GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE = eINSTANCE.getGetResourceOrderFilterCriteriaType();

        /**
         * The meta object literal for the '<em><b>Resource ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID = eINSTANCE.getGetResourceOrderFilterCriteriaType_ResourceID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetViewsForResourceResponseTypeImpl <em>Get Views For Resource Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetViewsForResourceResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetViewsForResourceResponseType()
         * @generated
         */
        EClass GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE = eINSTANCE.getGetViewsForResourceResponseType();

        /**
         * The meta object literal for the '<em><b>Work List Views</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE__WORK_LIST_VIEWS = eINSTANCE.getGetViewsForResourceResponseType_WorkListViews();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetViewsForResourceTypeImpl <em>Get Views For Resource Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetViewsForResourceTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetViewsForResourceType()
         * @generated
         */
        EClass GET_VIEWS_FOR_RESOURCE_TYPE = eINSTANCE.getGetViewsForResourceType();

        /**
         * The meta object literal for the '<em><b>Api Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION = eINSTANCE.getGetViewsForResourceType_ApiVersion();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetViewsForResourceType_NumberOfItems();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION = eINSTANCE.getGetViewsForResourceType_StartPosition();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkItemHeaderResponseTypeImpl <em>Get Work Item Header Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkItemHeaderResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkItemHeaderResponseType()
         * @generated
         */
        EClass GET_WORK_ITEM_HEADER_RESPONSE_TYPE = eINSTANCE.getGetWorkItemHeaderResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_ITEM_HEADER_RESPONSE_TYPE__GROUP = eINSTANCE.getGetWorkItemHeaderResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item Header</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_ITEM_HEADER_RESPONSE_TYPE__WORK_ITEM_HEADER = eINSTANCE.getGetWorkItemHeaderResponseType_WorkItemHeader();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkItemHeaderTypeImpl <em>Get Work Item Header Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkItemHeaderTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkItemHeaderType()
         * @generated
         */
        EClass GET_WORK_ITEM_HEADER_TYPE = eINSTANCE.getGetWorkItemHeaderType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_ITEM_HEADER_TYPE__GROUP = eINSTANCE.getGetWorkItemHeaderType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_ITEM_HEADER_TYPE__WORK_ITEM_ID = eINSTANCE.getGetWorkItemHeaderType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterResponseTypeImpl <em>Get Work Item Order Filter Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkItemOrderFilterResponseType()
         * @generated
         */
        EClass GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE = eINSTANCE.getGetWorkItemOrderFilterResponseType();

        /**
         * The meta object literal for the '<em><b>Column Data</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA = eINSTANCE.getGetWorkItemOrderFilterResponseType_ColumnData();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterTypeImpl <em>Get Work Item Order Filter Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkItemOrderFilterTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkItemOrderFilterType()
         * @generated
         */
        EClass GET_WORK_ITEM_ORDER_FILTER_TYPE = eINSTANCE.getGetWorkItemOrderFilterType();

        /**
         * The meta object literal for the '<em><b>Limit Columns</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS = eINSTANCE.getGetWorkItemOrderFilterType_LimitColumns();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataResponseTypeImpl <em>Get Work List Items For Global Data Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsForGlobalDataResponseType()
         * @generated
         */
        EClass GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE = eINSTANCE.getGetWorkListItemsForGlobalDataResponseType();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__START_POSITION = eINSTANCE.getGetWorkListItemsForGlobalDataResponseType_StartPosition();

        /**
         * The meta object literal for the '<em><b>End Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__END_POSITION = eINSTANCE.getGetWorkListItemsForGlobalDataResponseType_EndPosition();

        /**
         * The meta object literal for the '<em><b>Work Items</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__WORK_ITEMS = eINSTANCE.getGetWorkListItemsForGlobalDataResponseType_WorkItems();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataTypeImpl <em>Get Work List Items For Global Data Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkListItemsForGlobalDataTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsForGlobalDataType()
         * @generated
         */
        EClass GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE = eINSTANCE.getGetWorkListItemsForGlobalDataType();

        /**
         * The meta object literal for the '<em><b>Global Data Ref</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__GLOBAL_DATA_REF = eINSTANCE.getGetWorkListItemsForGlobalDataType_GlobalDataRef();

        /**
         * The meta object literal for the '<em><b>Order Filter Criteria</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA = eINSTANCE.getGetWorkListItemsForGlobalDataType_OrderFilterCriteria();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetWorkListItemsForGlobalDataType_NumberOfItems();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION = eINSTANCE.getGetWorkListItemsForGlobalDataType_StartPosition();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewResponseTypeImpl <em>Get Work List Items For View Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkListItemsForViewResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsForViewResponseType()
         * @generated
         */
        EClass GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE = eINSTANCE.getGetWorkListItemsForViewResponseType();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__START_POSITION = eINSTANCE.getGetWorkListItemsForViewResponseType_StartPosition();

        /**
         * The meta object literal for the '<em><b>End Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__END_POSITION = eINSTANCE.getGetWorkListItemsForViewResponseType_EndPosition();

        /**
         * The meta object literal for the '<em><b>Total Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__TOTAL_ITEMS = eINSTANCE.getGetWorkListItemsForViewResponseType_TotalItems();

        /**
         * The meta object literal for the '<em><b>Work Items</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__WORK_ITEMS = eINSTANCE.getGetWorkListItemsForViewResponseType_WorkItems();

        /**
         * The meta object literal for the '<em><b>Custom Data</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__CUSTOM_DATA = eINSTANCE.getGetWorkListItemsForViewResponseType_CustomData();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl <em>Get Work List Items For View Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkListItemsForViewTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsForViewType()
         * @generated
         */
        EClass GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE = eINSTANCE.getGetWorkListItemsForViewType();

        /**
         * The meta object literal for the '<em><b>Get Allocated Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS = eINSTANCE.getGetWorkListItemsForViewType_GetAllocatedItems();

        /**
         * The meta object literal for the '<em><b>Get Total Count</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT = eINSTANCE.getGetWorkListItemsForViewType_GetTotalCount();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetWorkListItemsForViewType_NumberOfItems();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION = eINSTANCE.getGetWorkListItemsForViewType_StartPosition();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getGetWorkListItemsForViewType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsResponseTypeImpl <em>Get Work List Items Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkListItemsResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsResponseType()
         * @generated
         */
        EClass GET_WORK_LIST_ITEMS_RESPONSE_TYPE = eINSTANCE.getGetWorkListItemsResponseType();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_RESPONSE_TYPE__START_POSITION = eINSTANCE.getGetWorkListItemsResponseType_StartPosition();

        /**
         * The meta object literal for the '<em><b>End Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_RESPONSE_TYPE__END_POSITION = eINSTANCE.getGetWorkListItemsResponseType_EndPosition();

        /**
         * The meta object literal for the '<em><b>Total Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_RESPONSE_TYPE__TOTAL_ITEMS = eINSTANCE.getGetWorkListItemsResponseType_TotalItems();

        /**
         * The meta object literal for the '<em><b>Work Items</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_LIST_ITEMS_RESPONSE_TYPE__WORK_ITEMS = eINSTANCE.getGetWorkListItemsResponseType_WorkItems();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsResponseType1Impl <em>Get Work List Items Response Type1</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkListItemsResponseType1Impl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsResponseType1()
         * @generated
         */
        EClass GET_WORK_LIST_ITEMS_RESPONSE_TYPE1 = eINSTANCE.getGetWorkListItemsResponseType1();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__START_POSITION = eINSTANCE.getGetWorkListItemsResponseType1_StartPosition();

        /**
         * The meta object literal for the '<em><b>End Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__END_POSITION = eINSTANCE.getGetWorkListItemsResponseType1_EndPosition();

        /**
         * The meta object literal for the '<em><b>Total Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__TOTAL_ITEMS = eINSTANCE.getGetWorkListItemsResponseType1_TotalItems();

        /**
         * The meta object literal for the '<em><b>Work Items</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__WORK_ITEMS = eINSTANCE.getGetWorkListItemsResponseType1_WorkItems();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkListItemsTypeImpl <em>Get Work List Items Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkListItemsTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListItemsType()
         * @generated
         */
        EClass GET_WORK_LIST_ITEMS_TYPE = eINSTANCE.getGetWorkListItemsType();

        /**
         * The meta object literal for the '<em><b>Resources Required</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_TYPE__RESOURCES_REQUIRED = eINSTANCE.getGetWorkListItemsType_ResourcesRequired();

        /**
         * The meta object literal for the '<em><b>Entity ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_LIST_ITEMS_TYPE__ENTITY_ID = eINSTANCE.getGetWorkListItemsType_EntityID();

        /**
         * The meta object literal for the '<em><b>Order Filter Criteria</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_LIST_ITEMS_TYPE__ORDER_FILTER_CRITERIA = eINSTANCE.getGetWorkListItemsType_OrderFilterCriteria();

        /**
         * The meta object literal for the '<em><b>Get Total Count</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_TYPE__GET_TOTAL_COUNT = eINSTANCE.getGetWorkListItemsType_GetTotalCount();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetWorkListItemsType_NumberOfItems();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_ITEMS_TYPE__START_POSITION = eINSTANCE.getGetWorkListItemsType_StartPosition();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkListViewDetailsTypeImpl <em>Get Work List View Details Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkListViewDetailsTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkListViewDetailsType()
         * @generated
         */
        EClass GET_WORK_LIST_VIEW_DETAILS_TYPE = eINSTANCE.getGetWorkListViewDetailsType();

        /**
         * The meta object literal for the '<em><b>Api Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION = eINSTANCE.getGetWorkListViewDetailsType_ApiVersion();

        /**
         * The meta object literal for the '<em><b>Lock View</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW = eINSTANCE.getGetWorkListViewDetailsType_LockView();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getGetWorkListViewDetailsType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkModelResponseTypeImpl <em>Get Work Model Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkModelResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkModelResponseType()
         * @generated
         */
        EClass GET_WORK_MODEL_RESPONSE_TYPE = eINSTANCE.getGetWorkModelResponseType();

        /**
         * The meta object literal for the '<em><b>Work Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL = eINSTANCE.getGetWorkModelResponseType_WorkModel();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkModelsResponseTypeImpl <em>Get Work Models Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkModelsResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkModelsResponseType()
         * @generated
         */
        EClass GET_WORK_MODELS_RESPONSE_TYPE = eINSTANCE.getGetWorkModelsResponseType();

        /**
         * The meta object literal for the '<em><b>Work Model List</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST = eINSTANCE.getGetWorkModelsResponseType_WorkModelList();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkModelsTypeImpl <em>Get Work Models Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkModelsTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkModelsType()
         * @generated
         */
        EClass GET_WORK_MODELS_TYPE = eINSTANCE.getGetWorkModelsType();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_MODELS_TYPE__START_POSITION = eINSTANCE.getGetWorkModelsType_StartPosition();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_MODELS_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetWorkModelsType_NumberOfItems();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkModelTypeImpl <em>Get Work Model Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkModelTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkModelType()
         * @generated
         */
        EClass GET_WORK_MODEL_TYPE = eINSTANCE.getGetWorkModelType();

        /**
         * The meta object literal for the '<em><b>Work Model ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_MODEL_TYPE__WORK_MODEL_ID = eINSTANCE.getGetWorkModelType_WorkModelID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkTypeResponseTypeImpl <em>Get Work Type Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkTypeResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkTypeResponseType()
         * @generated
         */
        EClass GET_WORK_TYPE_RESPONSE_TYPE = eINSTANCE.getGetWorkTypeResponseType();

        /**
         * The meta object literal for the '<em><b>Work Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE = eINSTANCE.getGetWorkTypeResponseType_WorkType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkTypesResponseTypeImpl <em>Get Work Types Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkTypesResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkTypesResponseType()
         * @generated
         */
        EClass GET_WORK_TYPES_RESPONSE_TYPE = eINSTANCE.getGetWorkTypesResponseType();

        /**
         * The meta object literal for the '<em><b>Work Typel List</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST = eINSTANCE.getGetWorkTypesResponseType_WorkTypelList();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkTypesTypeImpl <em>Get Work Types Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkTypesTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkTypesType()
         * @generated
         */
        EClass GET_WORK_TYPES_TYPE = eINSTANCE.getGetWorkTypesType();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_TYPES_TYPE__START_POSITION = eINSTANCE.getGetWorkTypesType_StartPosition();

        /**
         * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_TYPES_TYPE__NUMBER_OF_ITEMS = eINSTANCE.getGetWorkTypesType_NumberOfItems();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.GetWorkTypeTypeImpl <em>Get Work Type Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.GetWorkTypeTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getGetWorkTypeType()
         * @generated
         */
        EClass GET_WORK_TYPE_TYPE = eINSTANCE.getGetWorkTypeType();

        /**
         * The meta object literal for the '<em><b>Work Type ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GET_WORK_TYPE_TYPE__WORK_TYPE_ID = eINSTANCE.getGetWorkTypeType_WorkTypeID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.HiddenPeriodImpl <em>Hidden Period</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.HiddenPeriodImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getHiddenPeriod()
         * @generated
         */
        EClass HIDDEN_PERIOD = eINSTANCE.getHiddenPeriod();

        /**
         * The meta object literal for the '<em><b>Hidden Duration</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference HIDDEN_PERIOD__HIDDEN_DURATION = eINSTANCE.getHiddenPeriod_HiddenDuration();

        /**
         * The meta object literal for the '<em><b>Visible Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HIDDEN_PERIOD__VISIBLE_DATE = eINSTANCE.getHiddenPeriod_VisibleDate();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ItemImpl <em>Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ItemImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItem()
         * @generated
         */
        EClass ITEM = eINSTANCE.getItem();

        /**
         * The meta object literal for the '<em><b>Entities</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM__ENTITIES = eINSTANCE.getItem_Entities();

        /**
         * The meta object literal for the '<em><b>Entity Query</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM__ENTITY_QUERY = eINSTANCE.getItem_EntityQuery();

        /**
         * The meta object literal for the '<em><b>Work Type UID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM__WORK_TYPE_UID = eINSTANCE.getItem_WorkTypeUID();

        /**
         * The meta object literal for the '<em><b>Work Type Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM__WORK_TYPE_VERSION = eINSTANCE.getItem_WorkTypeVersion();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ItemBodyImpl <em>Item Body</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ItemBodyImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemBody()
         * @generated
         */
        EClass ITEM_BODY = eINSTANCE.getItemBody();

        /**
         * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_BODY__PARAMETER = eINSTANCE.getItemBody_Parameter();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ItemContextImpl <em>Item Context</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ItemContextImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemContext()
         * @generated
         */
        EClass ITEM_CONTEXT = eINSTANCE.getItemContext();

        /**
         * The meta object literal for the '<em><b>Activity ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_CONTEXT__ACTIVITY_ID = eINSTANCE.getItemContext_ActivityID();

        /**
         * The meta object literal for the '<em><b>Activity Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_CONTEXT__ACTIVITY_NAME = eINSTANCE.getItemContext_ActivityName();

        /**
         * The meta object literal for the '<em><b>App Instance</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_CONTEXT__APP_INSTANCE = eINSTANCE.getItemContext_AppInstance();

        /**
         * The meta object literal for the '<em><b>App Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_CONTEXT__APP_NAME = eINSTANCE.getItemContext_AppName();

        /**
         * The meta object literal for the '<em><b>App ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_CONTEXT__APP_ID = eINSTANCE.getItemContext_AppID();

        /**
         * The meta object literal for the '<em><b>App Instance Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_CONTEXT__APP_INSTANCE_DESCRIPTION = eINSTANCE.getItemContext_AppInstanceDescription();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ItemDurationImpl <em>Item Duration</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ItemDurationImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemDuration()
         * @generated
         */
        EClass ITEM_DURATION = eINSTANCE.getItemDuration();

        /**
         * The meta object literal for the '<em><b>Days</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_DURATION__DAYS = eINSTANCE.getItemDuration_Days();

        /**
         * The meta object literal for the '<em><b>Hours</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_DURATION__HOURS = eINSTANCE.getItemDuration_Hours();

        /**
         * The meta object literal for the '<em><b>Minutes</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_DURATION__MINUTES = eINSTANCE.getItemDuration_Minutes();

        /**
         * The meta object literal for the '<em><b>Months</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_DURATION__MONTHS = eINSTANCE.getItemDuration_Months();

        /**
         * The meta object literal for the '<em><b>Weeks</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_DURATION__WEEKS = eINSTANCE.getItemDuration_Weeks();

        /**
         * The meta object literal for the '<em><b>Years</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_DURATION__YEARS = eINSTANCE.getItemDuration_Years();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl <em>Item Privilege</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ItemPrivilegeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemPrivilege()
         * @generated
         */
        EClass ITEM_PRIVILEGE = eINSTANCE.getItemPrivilege();

        /**
         * The meta object literal for the '<em><b>Suspend</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_PRIVILEGE__SUSPEND = eINSTANCE.getItemPrivilege_Suspend();

        /**
         * The meta object literal for the '<em><b>Stateless Realloc</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_PRIVILEGE__STATELESS_REALLOC = eINSTANCE.getItemPrivilege_StatelessRealloc();

        /**
         * The meta object literal for the '<em><b>Stateful Realloc</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_PRIVILEGE__STATEFUL_REALLOC = eINSTANCE.getItemPrivilege_StatefulRealloc();

        /**
         * The meta object literal for the '<em><b>Dellocate</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_PRIVILEGE__DELLOCATE = eINSTANCE.getItemPrivilege_Dellocate();

        /**
         * The meta object literal for the '<em><b>Delegate</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_PRIVILEGE__DELEGATE = eINSTANCE.getItemPrivilege_Delegate();

        /**
         * The meta object literal for the '<em><b>Skip</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_PRIVILEGE__SKIP = eINSTANCE.getItemPrivilege_Skip();

        /**
         * The meta object literal for the '<em><b>Piling</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_PRIVILEGE__PILING = eINSTANCE.getItemPrivilege_Piling();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ItemScheduleImpl <em>Item Schedule</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ItemScheduleImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getItemSchedule()
         * @generated
         */
        EClass ITEM_SCHEDULE = eINSTANCE.getItemSchedule();

        /**
         * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_SCHEDULE__START_DATE = eINSTANCE.getItemSchedule_StartDate();

        /**
         * The meta object literal for the '<em><b>Max Duration</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ITEM_SCHEDULE__MAX_DURATION = eINSTANCE.getItemSchedule_MaxDuration();

        /**
         * The meta object literal for the '<em><b>Target Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ITEM_SCHEDULE__TARGET_DATE = eINSTANCE.getItemSchedule_TargetDate();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ManagedObjectIDImpl <em>Managed Object ID</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ManagedObjectIDImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getManagedObjectID()
         * @generated
         */
        EClass MANAGED_OBJECT_ID = eINSTANCE.getManagedObjectID();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MANAGED_OBJECT_ID__VERSION = eINSTANCE.getManagedObjectID_Version();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.NextWorkItemImpl <em>Next Work Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.NextWorkItemImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getNextWorkItem()
         * @generated
         */
        EClass NEXT_WORK_ITEM = eINSTANCE.getNextWorkItem();

        /**
         * The meta object literal for the '<em><b>Next Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference NEXT_WORK_ITEM__NEXT_ITEM = eINSTANCE.getNextWorkItem_NextItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ObjectIDImpl <em>Object ID</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ObjectIDImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getObjectID()
         * @generated
         */
        EClass OBJECT_ID = eINSTANCE.getObjectID();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OBJECT_ID__ID = eINSTANCE.getObjectID_Id();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.OnNotificationResponseTypeImpl <em>On Notification Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.OnNotificationResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOnNotificationResponseType()
         * @generated
         */
        EClass ON_NOTIFICATION_RESPONSE_TYPE = eINSTANCE.getOnNotificationResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ON_NOTIFICATION_RESPONSE_TYPE__SUCCESS = eINSTANCE.getOnNotificationResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.OnNotificationTypeImpl <em>On Notification Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.OnNotificationTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOnNotificationType()
         * @generated
         */
        EClass ON_NOTIFICATION_TYPE = eINSTANCE.getOnNotificationType();

        /**
         * The meta object literal for the '<em><b>Message Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ON_NOTIFICATION_TYPE__MESSAGE_DETAILS = eINSTANCE.getOnNotificationType_MessageDetails();

        /**
         * The meta object literal for the '<em><b>Item Body</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ON_NOTIFICATION_TYPE__ITEM_BODY = eINSTANCE.getOnNotificationType_ItemBody();

        /**
         * The meta object literal for the '<em><b>Allocation History</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY = eINSTANCE.getOnNotificationType_AllocationHistory();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.OpenWorkItemResponseTypeImpl <em>Open Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.OpenWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOpenWorkItemResponseType()
         * @generated
         */
        EClass OPEN_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getOpenWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OPEN_WORK_ITEM_RESPONSE_TYPE__GROUP = eINSTANCE.getOpenWorkItemResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item Body</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_BODY = eINSTANCE.getOpenWorkItemResponseType_WorkItemBody();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.OpenWorkItemTypeImpl <em>Open Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.OpenWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOpenWorkItemType()
         * @generated
         */
        EClass OPEN_WORK_ITEM_TYPE = eINSTANCE.getOpenWorkItemType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OPEN_WORK_ITEM_TYPE__GROUP = eINSTANCE.getOpenWorkItemType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getOpenWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.OrderFilterCriteriaImpl <em>Order Filter Criteria</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.OrderFilterCriteriaImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOrderFilterCriteria()
         * @generated
         */
        EClass ORDER_FILTER_CRITERIA = eINSTANCE.getOrderFilterCriteria();

        /**
         * The meta object literal for the '<em><b>Order</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORDER_FILTER_CRITERIA__ORDER = eINSTANCE.getOrderFilterCriteria_Order();

        /**
         * The meta object literal for the '<em><b>Filter</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORDER_FILTER_CRITERIA__FILTER = eINSTANCE.getOrderFilterCriteria_Filter();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.OrgEntityConfigAttributeImpl <em>Org Entity Config Attribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.OrgEntityConfigAttributeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOrgEntityConfigAttribute()
         * @generated
         */
        EClass ORG_ENTITY_CONFIG_ATTRIBUTE = eINSTANCE.getOrgEntityConfigAttribute();

        /**
         * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ENTITY_CONFIG_ATTRIBUTE__ATTRIBUTE_NAME = eINSTANCE.getOrgEntityConfigAttribute_AttributeName();

        /**
         * The meta object literal for the '<em><b>Attribute Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ENTITY_CONFIG_ATTRIBUTE__ATTRIBUTE_VALUE = eINSTANCE.getOrgEntityConfigAttribute_AttributeValue();

        /**
         * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ENTITY_CONFIG_ATTRIBUTE__READ_ONLY = eINSTANCE.getOrgEntityConfigAttribute_ReadOnly();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.OrgEntityConfigAttributesAvailableImpl <em>Org Entity Config Attributes Available</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.OrgEntityConfigAttributesAvailableImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOrgEntityConfigAttributesAvailable()
         * @generated
         */
        EClass ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE = eINSTANCE.getOrgEntityConfigAttributesAvailable();

        /**
         * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__ATTRIBUTE_NAME = eINSTANCE.getOrgEntityConfigAttributesAvailable_AttributeName();

        /**
         * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY = eINSTANCE.getOrgEntityConfigAttributesAvailable_ReadOnly();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.OrgEntityConfigAttributeSetImpl <em>Org Entity Config Attribute Set</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.OrgEntityConfigAttributeSetImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOrgEntityConfigAttributeSet()
         * @generated
         */
        EClass ORG_ENTITY_CONFIG_ATTRIBUTE_SET = eINSTANCE.getOrgEntityConfigAttributeSet();

        /**
         * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ENTITY_CONFIG_ATTRIBUTE_SET__ATTRIBUTE_NAME = eINSTANCE.getOrgEntityConfigAttributeSet_AttributeName();

        /**
         * The meta object literal for the '<em><b>Attribute Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ENTITY_CONFIG_ATTRIBUTE_SET__ATTRIBUTE_VALUE = eINSTANCE.getOrgEntityConfigAttributeSet_AttributeValue();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ParameterTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getParameterType()
         * @generated
         */
        EClass PARAMETER_TYPE = eINSTANCE.getParameterType();

        /**
         * The meta object literal for the '<em><b>Complex Value</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARAMETER_TYPE__COMPLEX_VALUE = eINSTANCE.getParameterType_ComplexValue();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_TYPE__VALUE = eINSTANCE.getParameterType_Value();

        /**
         * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_TYPE__ARRAY = eINSTANCE.getParameterType_Array();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_TYPE__NAME = eINSTANCE.getParameterType_Name();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.PendWorkItemImpl <em>Pend Work Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.PendWorkItemImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPendWorkItem()
         * @generated
         */
        EClass PEND_WORK_ITEM = eINSTANCE.getPendWorkItem();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PEND_WORK_ITEM__GROUP = eINSTANCE.getPendWorkItem_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PEND_WORK_ITEM__WORK_ITEM_ID = eINSTANCE.getPendWorkItem_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Hidden Period</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PEND_WORK_ITEM__HIDDEN_PERIOD = eINSTANCE.getPendWorkItem_HiddenPeriod();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.PendWorkItemResponseTypeImpl <em>Pend Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.PendWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPendWorkItemResponseType()
         * @generated
         */
        EClass PEND_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getPendWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PEND_WORK_ITEM_RESPONSE_TYPE__GROUP = eINSTANCE.getPendWorkItemResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PEND_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID = eINSTANCE.getPendWorkItemResponseType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListResponseTypeImpl <em>Preview Work Item From List Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.PreviewWorkItemFromListResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPreviewWorkItemFromListResponseType()
         * @generated
         */
        EClass PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE = eINSTANCE.getPreviewWorkItemFromListResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP = eINSTANCE.getPreviewWorkItemFromListResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item Preview</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW = eINSTANCE.getPreviewWorkItemFromListResponseType_WorkItemPreview();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListTypeImpl <em>Preview Work Item From List Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.PreviewWorkItemFromListTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPreviewWorkItemFromListType()
         * @generated
         */
        EClass PREVIEW_WORK_ITEM_FROM_LIST_TYPE = eINSTANCE.getPreviewWorkItemFromListType();

        /**
         * The meta object literal for the '<em><b>Entity ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID = eINSTANCE.getPreviewWorkItemFromListType_EntityID();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID = eINSTANCE.getPreviewWorkItemFromListType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Required Field</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRED_FIELD = eINSTANCE.getPreviewWorkItemFromListType_RequiredField();

        /**
         * The meta object literal for the '<em><b>Require Work Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE = eINSTANCE.getPreviewWorkItemFromListType_RequireWorkType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.PrivilegeImpl <em>Privilege</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.PrivilegeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPrivilege()
         * @generated
         */
        EClass PRIVILEGE = eINSTANCE.getPrivilege();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PRIVILEGE__NAME = eINSTANCE.getPrivilege_Name();

        /**
         * The meta object literal for the '<em><b>Qualifier</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PRIVILEGE__QUALIFIER = eINSTANCE.getPrivilege_Qualifier();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.PushNotificationTypeImpl <em>Push Notification Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.PushNotificationTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getPushNotificationType()
         * @generated
         */
        EClass PUSH_NOTIFICATION_TYPE = eINSTANCE.getPushNotificationType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID = eINSTANCE.getPushNotificationType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Work Type ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID = eINSTANCE.getPushNotificationType_WorkTypeID();

        /**
         * The meta object literal for the '<em><b>Resource IDs</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PUSH_NOTIFICATION_TYPE__RESOURCE_IDS = eINSTANCE.getPushNotificationType_ResourceIDs();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemDataImpl <em>Reallocate Work Item Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ReallocateWorkItemDataImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getReallocateWorkItemData()
         * @generated
         */
        EClass REALLOCATE_WORK_ITEM_DATA = eINSTANCE.getReallocateWorkItemData();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REALLOCATE_WORK_ITEM_DATA__GROUP = eINSTANCE.getReallocateWorkItemData_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID = eINSTANCE.getReallocateWorkItemData_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REALLOCATE_WORK_ITEM_DATA__RESOURCE = eINSTANCE.getReallocateWorkItemData_Resource();

        /**
         * The meta object literal for the '<em><b>Work Item Payload</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD = eINSTANCE.getReallocateWorkItemData_WorkItemPayload();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemDataResponseTypeImpl <em>Reallocate Work Item Data Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ReallocateWorkItemDataResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getReallocateWorkItemDataResponseType()
         * @generated
         */
        EClass REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE = eINSTANCE.getReallocateWorkItemDataResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE__GROUP = eINSTANCE.getReallocateWorkItemDataResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE__WORK_ITEM = eINSTANCE.getReallocateWorkItemDataResponseType_WorkItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemResponseTypeImpl <em>Reallocate Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ReallocateWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getReallocateWorkItemResponseType()
         * @generated
         */
        EClass REALLOCATE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getReallocateWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP = eINSTANCE.getReallocateWorkItemResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = eINSTANCE.getReallocateWorkItemResponseType_WorkItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ReallocateWorkItemTypeImpl <em>Reallocate Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ReallocateWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getReallocateWorkItemType()
         * @generated
         */
        EClass REALLOCATE_WORK_ITEM_TYPE = eINSTANCE.getReallocateWorkItemType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REALLOCATE_WORK_ITEM_TYPE__GROUP = eINSTANCE.getReallocateWorkItemType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getReallocateWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REALLOCATE_WORK_ITEM_TYPE__RESOURCE = eINSTANCE.getReallocateWorkItemType_Resource();

        /**
         * The meta object literal for the '<em><b>Revert Data</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REALLOCATE_WORK_ITEM_TYPE__REVERT_DATA = eINSTANCE.getReallocateWorkItemType_RevertData();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.RescheduleWorkItemImpl <em>Reschedule Work Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.RescheduleWorkItemImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getRescheduleWorkItem()
         * @generated
         */
        EClass RESCHEDULE_WORK_ITEM = eINSTANCE.getRescheduleWorkItem();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESCHEDULE_WORK_ITEM__WORK_ITEM_ID = eINSTANCE.getRescheduleWorkItem_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Item Schedule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE = eINSTANCE.getRescheduleWorkItem_ItemSchedule();

        /**
         * The meta object literal for the '<em><b>Item Body</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESCHEDULE_WORK_ITEM__ITEM_BODY = eINSTANCE.getRescheduleWorkItem_ItemBody();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.RescheduleWorkItemResponseTypeImpl <em>Reschedule Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.RescheduleWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getRescheduleWorkItemResponseType()
         * @generated
         */
        EClass RESCHEDULE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getRescheduleWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__SUCCESS = eINSTANCE.getRescheduleWorkItemResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ResumeWorkItemResponseTypeImpl <em>Resume Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ResumeWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getResumeWorkItemResponseType()
         * @generated
         */
        EClass RESUME_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getResumeWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESUME_WORK_ITEM_RESPONSE_TYPE__SUCCESS = eINSTANCE.getResumeWorkItemResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ResumeWorkItemTypeImpl <em>Resume Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ResumeWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getResumeWorkItemType()
         * @generated
         */
        EClass RESUME_WORK_ITEM_TYPE = eINSTANCE.getResumeWorkItemType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESUME_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getResumeWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SaveOpenWorkItemResponseTypeImpl <em>Save Open Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SaveOpenWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSaveOpenWorkItemResponseType()
         * @generated
         */
        EClass SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getSaveOpenWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID = eINSTANCE.getSaveOpenWorkItemResponseType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SaveOpenWorkItemTypeImpl <em>Save Open Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SaveOpenWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSaveOpenWorkItemType()
         * @generated
         */
        EClass SAVE_OPEN_WORK_ITEM_TYPE = eINSTANCE.getSaveOpenWorkItemType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SAVE_OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getSaveOpenWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Work Item Payload</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SAVE_OPEN_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD = eINSTANCE.getSaveOpenWorkItemType_WorkItemPayload();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemResponseTypeImpl <em>Schedule Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ScheduleWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleWorkItemResponseType()
         * @generated
         */
        EClass SCHEDULE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getScheduleWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID = eINSTANCE.getScheduleWorkItemResponseType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemTypeImpl <em>Schedule Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ScheduleWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleWorkItemType()
         * @generated
         */
        EClass SCHEDULE_WORK_ITEM_TYPE = eINSTANCE.getScheduleWorkItemType();

        /**
         * The meta object literal for the '<em><b>Item</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_TYPE__ITEM = eINSTANCE.getScheduleWorkItemType_Item();

        /**
         * The meta object literal for the '<em><b>Item Schedule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE = eINSTANCE.getScheduleWorkItemType_ItemSchedule();

        /**
         * The meta object literal for the '<em><b>Item Context</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT = eINSTANCE.getScheduleWorkItemType_ItemContext();

        /**
         * The meta object literal for the '<em><b>Item Body</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY = eINSTANCE.getScheduleWorkItemType_ItemBody();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelResponseTypeImpl <em>Schedule Work Item With Model Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleWorkItemWithModelResponseType()
         * @generated
         */
        EClass SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE = eINSTANCE.getScheduleWorkItemWithModelResponseType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__WORK_ITEM_ID = eINSTANCE.getScheduleWorkItemWithModelResponseType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl <em>Schedule Work Item With Model Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.ScheduleWorkItemWithModelTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleWorkItemWithModelType()
         * @generated
         */
        EClass SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE = eINSTANCE.getScheduleWorkItemWithModelType();

        /**
         * The meta object literal for the '<em><b>Item Schedule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE = eINSTANCE.getScheduleWorkItemWithModelType_ItemSchedule();

        /**
         * The meta object literal for the '<em><b>Item Context</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT = eINSTANCE.getScheduleWorkItemWithModelType_ItemContext();

        /**
         * The meta object literal for the '<em><b>Item Body</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY = eINSTANCE.getScheduleWorkItemWithModelType_ItemBody();

        /**
         * The meta object literal for the '<em><b>Entity Query</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY = eINSTANCE.getScheduleWorkItemWithModelType_EntityQuery();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID = eINSTANCE.getScheduleWorkItemWithModelType_GroupID();

        /**
         * The meta object literal for the '<em><b>Work Model UID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_UID = eINSTANCE.getScheduleWorkItemWithModelType_WorkModelUID();

        /**
         * The meta object literal for the '<em><b>Work Model Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_VERSION = eINSTANCE.getScheduleWorkItemWithModelType_WorkModelVersion();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesResponseTypeImpl <em>Set Org Entity Config Attributes Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetOrgEntityConfigAttributesResponseType()
         * @generated
         */
        EClass SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE = eINSTANCE.getSetOrgEntityConfigAttributesResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS = eINSTANCE.getSetOrgEntityConfigAttributesResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesTypeImpl <em>Set Org Entity Config Attributes Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SetOrgEntityConfigAttributesTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetOrgEntityConfigAttributesType()
         * @generated
         */
        EClass SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE = eINSTANCE.getSetOrgEntityConfigAttributesType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP = eINSTANCE.getSetOrgEntityConfigAttributesType_Group();

        /**
         * The meta object literal for the '<em><b>Org Entity Config Attribute Set</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET = eINSTANCE.getSetOrgEntityConfigAttributesType_OrgEntityConfigAttributeSet();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE = eINSTANCE.getSetOrgEntityConfigAttributesType_Resource();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaResponseTypeImpl <em>Set Resource Order Filter Criteria Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetResourceOrderFilterCriteriaResponseType()
         * @generated
         */
        EClass SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE = eINSTANCE.getSetResourceOrderFilterCriteriaResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE__SUCCESS = eINSTANCE.getSetResourceOrderFilterCriteriaResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaTypeImpl <em>Set Resource Order Filter Criteria Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SetResourceOrderFilterCriteriaTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetResourceOrderFilterCriteriaType()
         * @generated
         */
        EClass SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE = eINSTANCE.getSetResourceOrderFilterCriteriaType();

        /**
         * The meta object literal for the '<em><b>Order Filter Criteria</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA = eINSTANCE.getSetResourceOrderFilterCriteriaType_OrderFilterCriteria();

        /**
         * The meta object literal for the '<em><b>Resource ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID = eINSTANCE.getSetResourceOrderFilterCriteriaType_ResourceID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SetWorkItemPriorityImpl <em>Set Work Item Priority</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SetWorkItemPriorityImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetWorkItemPriority()
         * @generated
         */
        EClass SET_WORK_ITEM_PRIORITY = eINSTANCE.getSetWorkItemPriority();

        /**
         * The meta object literal for the '<em><b>Work Item IDand Priority</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY = eINSTANCE.getSetWorkItemPriority_WorkItemIDandPriority();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SetWorkItemPriorityResponseTypeImpl <em>Set Work Item Priority Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SetWorkItemPriorityResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSetWorkItemPriorityResponseType()
         * @generated
         */
        EClass SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE = eINSTANCE.getSetWorkItemPriorityResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE__GROUP = eINSTANCE.getSetWorkItemPriorityResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE__WORK_ITEM_ID = eINSTANCE.getSetWorkItemPriorityResponseType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SkipWorkItemResponseTypeImpl <em>Skip Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SkipWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSkipWorkItemResponseType()
         * @generated
         */
        EClass SKIP_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getSkipWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SKIP_WORK_ITEM_RESPONSE_TYPE__SUCCESS = eINSTANCE.getSkipWorkItemResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SkipWorkItemTypeImpl <em>Skip Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SkipWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSkipWorkItemType()
         * @generated
         */
        EClass SKIP_WORK_ITEM_TYPE = eINSTANCE.getSkipWorkItemType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SKIP_WORK_ITEM_TYPE__GROUP = eINSTANCE.getSkipWorkItemType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SKIP_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getSkipWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.StartGroupResponseTypeImpl <em>Start Group Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.StartGroupResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getStartGroupResponseType()
         * @generated
         */
        EClass START_GROUP_RESPONSE_TYPE = eINSTANCE.getStartGroupResponseType();

        /**
         * The meta object literal for the '<em><b>Group ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute START_GROUP_RESPONSE_TYPE__GROUP_ID = eINSTANCE.getStartGroupResponseType_GroupID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.StartGroupTypeImpl <em>Start Group Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.StartGroupTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getStartGroupType()
         * @generated
         */
        EClass START_GROUP_TYPE = eINSTANCE.getStartGroupType();

        /**
         * The meta object literal for the '<em><b>Group Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute START_GROUP_TYPE__GROUP_TYPE = eINSTANCE.getStartGroupType_GroupType();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute START_GROUP_TYPE__DESCRIPTION = eINSTANCE.getStartGroupType_Description();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SuspendWorkItemResponseTypeImpl <em>Suspend Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SuspendWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSuspendWorkItemResponseType()
         * @generated
         */
        EClass SUSPEND_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getSuspendWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Success</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUSPEND_WORK_ITEM_RESPONSE_TYPE__SUCCESS = eINSTANCE.getSuspendWorkItemResponseType_Success();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.SuspendWorkItemTypeImpl <em>Suspend Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.SuspendWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getSuspendWorkItemType()
         * @generated
         */
        EClass SUSPEND_WORK_ITEM_TYPE = eINSTANCE.getSuspendWorkItemType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getSuspendWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Force Suspend</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND = eINSTANCE.getSuspendWorkItemType_ForceSuspend();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.UnallocateWorkItemResponseTypeImpl <em>Unallocate Work Item Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.UnallocateWorkItemResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getUnallocateWorkItemResponseType()
         * @generated
         */
        EClass UNALLOCATE_WORK_ITEM_RESPONSE_TYPE = eINSTANCE.getUnallocateWorkItemResponseType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP = eINSTANCE.getUnallocateWorkItemResponseType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference UNALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM = eINSTANCE.getUnallocateWorkItemResponseType_WorkItem();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.UnallocateWorkItemTypeImpl <em>Unallocate Work Item Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.UnallocateWorkItemTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getUnallocateWorkItemType()
         * @generated
         */
        EClass UNALLOCATE_WORK_ITEM_TYPE = eINSTANCE.getUnallocateWorkItemType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNALLOCATE_WORK_ITEM_TYPE__GROUP = eINSTANCE.getUnallocateWorkItemType_Group();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference UNALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID = eINSTANCE.getUnallocateWorkItemType_WorkItemID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.UnlockWorkListViewResponseTypeImpl <em>Unlock Work List View Response Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.UnlockWorkListViewResponseTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getUnlockWorkListViewResponseType()
         * @generated
         */
        EClass UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE = eINSTANCE.getUnlockWorkListViewResponseType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getUnlockWorkListViewResponseType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.UnlockWorkListViewTypeImpl <em>Unlock Work List View Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.UnlockWorkListViewTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getUnlockWorkListViewType()
         * @generated
         */
        EClass UNLOCK_WORK_LIST_VIEW_TYPE = eINSTANCE.getUnlockWorkListViewType();

        /**
         * The meta object literal for the '<em><b>Work List View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNLOCK_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID = eINSTANCE.getUnlockWorkListViewType_WorkListViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkItemImpl <em>Work Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkItemImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItem()
         * @generated
         */
        EClass WORK_ITEM = eINSTANCE.getWorkItem();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM__ID = eINSTANCE.getWorkItem_Id();

        /**
         * The meta object literal for the '<em><b>Header</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM__HEADER = eINSTANCE.getWorkItem_Header();

        /**
         * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM__ATTRIBUTES = eINSTANCE.getWorkItem_Attributes();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM__BODY = eINSTANCE.getWorkItem_Body();

        /**
         * The meta object literal for the '<em><b>Work Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM__WORK_TYPE = eINSTANCE.getWorkItem_WorkType();

        /**
         * The meta object literal for the '<em><b>State</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM__STATE = eINSTANCE.getWorkItem_State();

        /**
         * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM__VISIBLE = eINSTANCE.getWorkItem_Visible();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkItemAttributesImpl <em>Work Item Attributes</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkItemAttributesImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemAttributes()
         * @generated
         */
        EClass WORK_ITEM_ATTRIBUTES = eINSTANCE.getWorkItemAttributes();

        /**
         * The meta object literal for the '<em><b>Attribute1</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE1 = eINSTANCE.getWorkItemAttributes_Attribute1();

        /**
         * The meta object literal for the '<em><b>Attribute10</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE10 = eINSTANCE.getWorkItemAttributes_Attribute10();

        /**
         * The meta object literal for the '<em><b>Attribute11</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE11 = eINSTANCE.getWorkItemAttributes_Attribute11();

        /**
         * The meta object literal for the '<em><b>Attribute12</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE12 = eINSTANCE.getWorkItemAttributes_Attribute12();

        /**
         * The meta object literal for the '<em><b>Attribute13</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE13 = eINSTANCE.getWorkItemAttributes_Attribute13();

        /**
         * The meta object literal for the '<em><b>Attribute14</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE14 = eINSTANCE.getWorkItemAttributes_Attribute14();

        /**
         * The meta object literal for the '<em><b>Attribute15</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE15 = eINSTANCE.getWorkItemAttributes_Attribute15();

        /**
         * The meta object literal for the '<em><b>Attribute16</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE16 = eINSTANCE.getWorkItemAttributes_Attribute16();

        /**
         * The meta object literal for the '<em><b>Attribute17</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE17 = eINSTANCE.getWorkItemAttributes_Attribute17();

        /**
         * The meta object literal for the '<em><b>Attribute18</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE18 = eINSTANCE.getWorkItemAttributes_Attribute18();

        /**
         * The meta object literal for the '<em><b>Attribute19</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE19 = eINSTANCE.getWorkItemAttributes_Attribute19();

        /**
         * The meta object literal for the '<em><b>Attribute2</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE2 = eINSTANCE.getWorkItemAttributes_Attribute2();

        /**
         * The meta object literal for the '<em><b>Attribute20</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE20 = eINSTANCE.getWorkItemAttributes_Attribute20();

        /**
         * The meta object literal for the '<em><b>Attribute21</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE21 = eINSTANCE.getWorkItemAttributes_Attribute21();

        /**
         * The meta object literal for the '<em><b>Attribute22</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE22 = eINSTANCE.getWorkItemAttributes_Attribute22();

        /**
         * The meta object literal for the '<em><b>Attribute23</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE23 = eINSTANCE.getWorkItemAttributes_Attribute23();

        /**
         * The meta object literal for the '<em><b>Attribute24</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE24 = eINSTANCE.getWorkItemAttributes_Attribute24();

        /**
         * The meta object literal for the '<em><b>Attribute25</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE25 = eINSTANCE.getWorkItemAttributes_Attribute25();

        /**
         * The meta object literal for the '<em><b>Attribute26</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE26 = eINSTANCE.getWorkItemAttributes_Attribute26();

        /**
         * The meta object literal for the '<em><b>Attribute27</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE27 = eINSTANCE.getWorkItemAttributes_Attribute27();

        /**
         * The meta object literal for the '<em><b>Attribute28</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE28 = eINSTANCE.getWorkItemAttributes_Attribute28();

        /**
         * The meta object literal for the '<em><b>Attribute29</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE29 = eINSTANCE.getWorkItemAttributes_Attribute29();

        /**
         * The meta object literal for the '<em><b>Attribute3</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE3 = eINSTANCE.getWorkItemAttributes_Attribute3();

        /**
         * The meta object literal for the '<em><b>Attribute30</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE30 = eINSTANCE.getWorkItemAttributes_Attribute30();

        /**
         * The meta object literal for the '<em><b>Attribute31</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE31 = eINSTANCE.getWorkItemAttributes_Attribute31();

        /**
         * The meta object literal for the '<em><b>Attribute32</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE32 = eINSTANCE.getWorkItemAttributes_Attribute32();

        /**
         * The meta object literal for the '<em><b>Attribute33</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE33 = eINSTANCE.getWorkItemAttributes_Attribute33();

        /**
         * The meta object literal for the '<em><b>Attribute34</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE34 = eINSTANCE.getWorkItemAttributes_Attribute34();

        /**
         * The meta object literal for the '<em><b>Attribute35</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE35 = eINSTANCE.getWorkItemAttributes_Attribute35();

        /**
         * The meta object literal for the '<em><b>Attribute36</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE36 = eINSTANCE.getWorkItemAttributes_Attribute36();

        /**
         * The meta object literal for the '<em><b>Attribute37</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE37 = eINSTANCE.getWorkItemAttributes_Attribute37();

        /**
         * The meta object literal for the '<em><b>Attribute38</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE38 = eINSTANCE.getWorkItemAttributes_Attribute38();

        /**
         * The meta object literal for the '<em><b>Attribute39</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE39 = eINSTANCE.getWorkItemAttributes_Attribute39();

        /**
         * The meta object literal for the '<em><b>Attribute4</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE4 = eINSTANCE.getWorkItemAttributes_Attribute4();

        /**
         * The meta object literal for the '<em><b>Attribute40</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE40 = eINSTANCE.getWorkItemAttributes_Attribute40();

        /**
         * The meta object literal for the '<em><b>Attribute5</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE5 = eINSTANCE.getWorkItemAttributes_Attribute5();

        /**
         * The meta object literal for the '<em><b>Attribute6</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE6 = eINSTANCE.getWorkItemAttributes_Attribute6();

        /**
         * The meta object literal for the '<em><b>Attribute7</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE7 = eINSTANCE.getWorkItemAttributes_Attribute7();

        /**
         * The meta object literal for the '<em><b>Attribute8</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE8 = eINSTANCE.getWorkItemAttributes_Attribute8();

        /**
         * The meta object literal for the '<em><b>Attribute9</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTES__ATTRIBUTE9 = eINSTANCE.getWorkItemAttributes_Attribute9();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkItemBodyImpl <em>Work Item Body</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkItemBodyImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemBody()
         * @generated
         */
        EClass WORK_ITEM_BODY = eINSTANCE.getWorkItemBody();

        /**
         * The meta object literal for the '<em><b>Data Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM_BODY__DATA_MODEL = eINSTANCE.getWorkItemBody_DataModel();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkItemFlagsImpl <em>Work Item Flags</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkItemFlagsImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemFlags()
         * @generated
         */
        EClass WORK_ITEM_FLAGS = eINSTANCE.getWorkItemFlags();

        /**
         * The meta object literal for the '<em><b>Schedule Status</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_FLAGS__SCHEDULE_STATUS = eINSTANCE.getWorkItemFlags_ScheduleStatus();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkItemHeaderImpl <em>Work Item Header</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkItemHeaderImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemHeader()
         * @generated
         */
        EClass WORK_ITEM_HEADER = eINSTANCE.getWorkItemHeader();

        /**
         * The meta object literal for the '<em><b>Flags</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM_HEADER__FLAGS = eINSTANCE.getWorkItemHeader_Flags();

        /**
         * The meta object literal for the '<em><b>Item Context</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM_HEADER__ITEM_CONTEXT = eINSTANCE.getWorkItemHeader_ItemContext();

        /**
         * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_HEADER__END_DATE = eINSTANCE.getWorkItemHeader_EndDate();

        /**
         * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_HEADER__START_DATE = eINSTANCE.getWorkItemHeader_StartDate();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkItemIDandPriorityTypeImpl <em>Work Item IDand Priority Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkItemIDandPriorityTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemIDandPriorityType()
         * @generated
         */
        EClass WORK_ITEM_IDAND_PRIORITY_TYPE = eINSTANCE.getWorkItemIDandPriorityType();

        /**
         * The meta object literal for the '<em><b>Work Item ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID = eINSTANCE.getWorkItemIDandPriorityType_WorkItemID();

        /**
         * The meta object literal for the '<em><b>Work Item Priority</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY = eINSTANCE.getWorkItemIDandPriorityType_WorkItemPriority();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkItemPreviewImpl <em>Work Item Preview</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkItemPreviewImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemPreview()
         * @generated
         */
        EClass WORK_ITEM_PREVIEW = eINSTANCE.getWorkItemPreview();

        /**
         * The meta object literal for the '<em><b>Field Preview</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM_PREVIEW__FIELD_PREVIEW = eINSTANCE.getWorkItemPreview_FieldPreview();

        /**
         * The meta object literal for the '<em><b>Work Type</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM_PREVIEW__WORK_TYPE = eINSTANCE.getWorkItemPreview_WorkType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkItemPriorityTypeImpl <em>Work Item Priority Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkItemPriorityTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemPriorityType()
         * @generated
         */
        EClass WORK_ITEM_PRIORITY_TYPE = eINSTANCE.getWorkItemPriorityType();

        /**
         * The meta object literal for the '<em><b>Abs Priority</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY = eINSTANCE.getWorkItemPriorityType_AbsPriority();

        /**
         * The meta object literal for the '<em><b>Offset Priority</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY = eINSTANCE.getWorkItemPriorityType_OffsetPriority();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkListViewImpl <em>Work List View</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkListViewImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkListView()
         * @generated
         */
        EClass WORK_LIST_VIEW = eINSTANCE.getWorkListView();

        /**
         * The meta object literal for the '<em><b>Creation Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW__CREATION_DATE = eINSTANCE.getWorkListView_CreationDate();

        /**
         * The meta object literal for the '<em><b>Locker</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW__LOCKER = eINSTANCE.getWorkListView_Locker();

        /**
         * The meta object literal for the '<em><b>Modification Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW__MODIFICATION_DATE = eINSTANCE.getWorkListView_ModificationDate();

        /**
         * The meta object literal for the '<em><b>Work View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW__WORK_VIEW_ID = eINSTANCE.getWorkListView_WorkViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkListViewCommonImpl <em>Work List View Common</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkListViewCommonImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkListViewCommon()
         * @generated
         */
        EClass WORK_LIST_VIEW_COMMON = eINSTANCE.getWorkListViewCommon();

        /**
         * The meta object literal for the '<em><b>Entity ID</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_LIST_VIEW_COMMON__ENTITY_ID = eINSTANCE.getWorkListViewCommon_EntityID();

        /**
         * The meta object literal for the '<em><b>Resources Required</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED = eINSTANCE.getWorkListViewCommon_ResourcesRequired();

        /**
         * The meta object literal for the '<em><b>Order Filter Criteria</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA = eINSTANCE.getWorkListViewCommon_OrderFilterCriteria();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_COMMON__DESCRIPTION = eINSTANCE.getWorkListViewCommon_Description();

        /**
         * The meta object literal for the '<em><b>Get Allocated Items</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS = eINSTANCE.getWorkListViewCommon_GetAllocatedItems();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_COMMON__NAME = eINSTANCE.getWorkListViewCommon_Name();

        /**
         * The meta object literal for the '<em><b>Owner</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_COMMON__OWNER = eINSTANCE.getWorkListViewCommon_Owner();

        /**
         * The meta object literal for the '<em><b>Public</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_COMMON__PUBLIC = eINSTANCE.getWorkListViewCommon_Public();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkListViewEditImpl <em>Work List View Edit</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkListViewEditImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkListViewEdit()
         * @generated
         */
        EClass WORK_LIST_VIEW_EDIT = eINSTANCE.getWorkListViewEdit();

        /**
         * The meta object literal for the '<em><b>Authors</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_LIST_VIEW_EDIT__AUTHORS = eINSTANCE.getWorkListViewEdit_Authors();

        /**
         * The meta object literal for the '<em><b>Users</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_LIST_VIEW_EDIT__USERS = eINSTANCE.getWorkListViewEdit_Users();

        /**
         * The meta object literal for the '<em><b>Custom Data</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_EDIT__CUSTOM_DATA = eINSTANCE.getWorkListViewEdit_CustomData();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkListViewPageItemImpl <em>Work List View Page Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkListViewPageItemImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkListViewPageItem()
         * @generated
         */
        EClass WORK_LIST_VIEW_PAGE_ITEM = eINSTANCE.getWorkListViewPageItem();

        /**
         * The meta object literal for the '<em><b>Creation Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_PAGE_ITEM__CREATION_DATE = eINSTANCE.getWorkListViewPageItem_CreationDate();

        /**
         * The meta object literal for the '<em><b>Modification Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_PAGE_ITEM__MODIFICATION_DATE = eINSTANCE.getWorkListViewPageItem_ModificationDate();

        /**
         * The meta object literal for the '<em><b>Work View ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID = eINSTANCE.getWorkListViewPageItem_WorkViewID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelImpl <em>Work Model</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModel()
         * @generated
         */
        EClass WORK_MODEL = eINSTANCE.getWorkModel();

        /**
         * The meta object literal for the '<em><b>Base Model Info</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL__BASE_MODEL_INFO = eINSTANCE.getWorkModel_BaseModelInfo();

        /**
         * The meta object literal for the '<em><b>Work Model Specification</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL__WORK_MODEL_SPECIFICATION = eINSTANCE.getWorkModel_WorkModelSpecification();

        /**
         * The meta object literal for the '<em><b>Work Model Entities</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL__WORK_MODEL_ENTITIES = eINSTANCE.getWorkModel_WorkModelEntities();

        /**
         * The meta object literal for the '<em><b>Work Model Types</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL__WORK_MODEL_TYPES = eINSTANCE.getWorkModel_WorkModelTypes();

        /**
         * The meta object literal for the '<em><b>Item Privileges</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL__ITEM_PRIVILEGES = eINSTANCE.getWorkModel_ItemPrivileges();

        /**
         * The meta object literal for the '<em><b>Work Model Scripts</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL__WORK_MODEL_SCRIPTS = eINSTANCE.getWorkModel_WorkModelScripts();

        /**
         * The meta object literal for the '<em><b>Attribute Alias List</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL__ATTRIBUTE_ALIAS_LIST = eINSTANCE.getWorkModel_AttributeAliasList();

        /**
         * The meta object literal for the '<em><b>Work Model UID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL__WORK_MODEL_UID = eINSTANCE.getWorkModel_WorkModelUID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelEntitiesImpl <em>Work Model Entities</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelEntitiesImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelEntities()
         * @generated
         */
        EClass WORK_MODEL_ENTITIES = eINSTANCE.getWorkModelEntities();

        /**
         * The meta object literal for the '<em><b>Work Model Entity</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL_ENTITIES__WORK_MODEL_ENTITY = eINSTANCE.getWorkModelEntities_WorkModelEntity();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_ENTITIES__EXPRESSION = eINSTANCE.getWorkModelEntities_Expression();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelEntityImpl <em>Work Model Entity</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelEntityImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelEntity()
         * @generated
         */
        EClass WORK_MODEL_ENTITY = eINSTANCE.getWorkModelEntity();

        /**
         * The meta object literal for the '<em><b>Entity Query</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL_ENTITY__ENTITY_QUERY = eINSTANCE.getWorkModelEntity_EntityQuery();

        /**
         * The meta object literal for the '<em><b>Entities</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL_ENTITY__ENTITIES = eINSTANCE.getWorkModelEntity_Entities();

        /**
         * The meta object literal for the '<em><b>Distribution Strategy</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY = eINSTANCE.getWorkModelEntity_DistributionStrategy();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelListImpl <em>Work Model List</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelListImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelList()
         * @generated
         */
        EClass WORK_MODEL_LIST = eINSTANCE.getWorkModelList();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_LIST__START_POSITION = eINSTANCE.getWorkModelList_StartPosition();

        /**
         * The meta object literal for the '<em><b>End Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_LIST__END_POSITION = eINSTANCE.getWorkModelList_EndPosition();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL_LIST__TYPE = eINSTANCE.getWorkModelList_Type();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelMappingImpl <em>Work Model Mapping</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelMappingImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelMapping()
         * @generated
         */
        EClass WORK_MODEL_MAPPING = eINSTANCE.getWorkModelMapping();

        /**
         * The meta object literal for the '<em><b>Type Param Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_MAPPING__TYPE_PARAM_NAME = eINSTANCE.getWorkModelMapping_TypeParamName();

        /**
         * The meta object literal for the '<em><b>Model Param Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_MAPPING__MODEL_PARAM_NAME = eINSTANCE.getWorkModelMapping_ModelParamName();

        /**
         * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_MAPPING__DEFAULT_VALUE = eINSTANCE.getWorkModelMapping_DefaultValue();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelScriptImpl <em>Work Model Script</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelScriptImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelScript()
         * @generated
         */
        EClass WORK_MODEL_SCRIPT = eINSTANCE.getWorkModelScript();

        /**
         * The meta object literal for the '<em><b>Script Body</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_SCRIPT__SCRIPT_BODY = eINSTANCE.getWorkModelScript_ScriptBody();

        /**
         * The meta object literal for the '<em><b>Script Language</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE = eINSTANCE.getWorkModelScript_ScriptLanguage();

        /**
         * The meta object literal for the '<em><b>Script Language Extension</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_EXTENSION = eINSTANCE.getWorkModelScript_ScriptLanguageExtension();

        /**
         * The meta object literal for the '<em><b>Script Language Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_VERSION = eINSTANCE.getWorkModelScript_ScriptLanguageVersion();

        /**
         * The meta object literal for the '<em><b>Script Operation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_SCRIPT__SCRIPT_OPERATION = eINSTANCE.getWorkModelScript_ScriptOperation();

        /**
         * The meta object literal for the '<em><b>Script Type ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_SCRIPT__SCRIPT_TYPE_ID = eINSTANCE.getWorkModelScript_ScriptTypeID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelScriptsImpl <em>Work Model Scripts</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelScriptsImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelScripts()
         * @generated
         */
        EClass WORK_MODEL_SCRIPTS = eINSTANCE.getWorkModelScripts();

        /**
         * The meta object literal for the '<em><b>Work Model Script</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT = eINSTANCE.getWorkModelScripts_WorkModelScript();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelSpecificationImpl <em>Work Model Specification</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelSpecificationImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelSpecification()
         * @generated
         */
        EClass WORK_MODEL_SPECIFICATION = eINSTANCE.getWorkModelSpecification();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelTypeImpl <em>Work Model Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelTypeImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelType()
         * @generated
         */
        EClass WORK_MODEL_TYPE = eINSTANCE.getWorkModelType();

        /**
         * The meta object literal for the '<em><b>Work Model Mapping</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL_TYPE__WORK_MODEL_MAPPING = eINSTANCE.getWorkModelType_WorkModelMapping();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_TYPE__VERSION = eINSTANCE.getWorkModelType_Version();

        /**
         * The meta object literal for the '<em><b>Work Type ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_TYPE__WORK_TYPE_ID = eINSTANCE.getWorkModelType_WorkTypeID();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkModelTypesImpl <em>Work Model Types</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkModelTypesImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkModelTypes()
         * @generated
         */
        EClass WORK_MODEL_TYPES = eINSTANCE.getWorkModelTypes();

        /**
         * The meta object literal for the '<em><b>Work Model Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL_TYPES__WORK_MODEL_TYPE = eINSTANCE.getWorkModelTypes_WorkModelType();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL_TYPES__EXPRESSION = eINSTANCE.getWorkModelTypes_Expression();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.impl.WorkTypeListImpl <em>Work Type List</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.impl.WorkTypeListImpl
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkTypeList()
         * @generated
         */
        EClass WORK_TYPE_LIST = eINSTANCE.getWorkTypeList();

        /**
         * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE_LIST__START_POSITION = eINSTANCE.getWorkTypeList_StartPosition();

        /**
         * The meta object literal for the '<em><b>End Position</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE_LIST__END_POSITION = eINSTANCE.getWorkTypeList_EndPosition();

        /**
         * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_TYPE_LIST__TYPES = eINSTANCE.getWorkTypeList_Types();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.ColumnCapability <em>Column Capability</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.ColumnCapability
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnCapability()
         * @generated
         */
        EEnum COLUMN_CAPABILITY = eINSTANCE.getColumnCapability();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.ColumnType <em>Column Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.ColumnType
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnType()
         * @generated
         */
        EEnum COLUMN_TYPE = eINSTANCE.getColumnType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.DistributionStrategy <em>Distribution Strategy</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.DistributionStrategy
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDistributionStrategy()
         * @generated
         */
        EEnum DISTRIBUTION_STRATEGY = eINSTANCE.getDistributionStrategy();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.MethodAuthorisationAction <em>Method Authorisation Action</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.MethodAuthorisationAction
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getMethodAuthorisationAction()
         * @generated
         */
        EEnum METHOD_AUTHORISATION_ACTION = eINSTANCE.getMethodAuthorisationAction();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.MethodAuthorisationComponent <em>Method Authorisation Component</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.MethodAuthorisationComponent
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getMethodAuthorisationComponent()
         * @generated
         */
        EEnum METHOD_AUTHORISATION_COMPONENT = eINSTANCE.getMethodAuthorisationComponent();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.ResourcesRequiredType <em>Resources Required Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.ResourcesRequiredType
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getResourcesRequiredType()
         * @generated
         */
        EEnum RESOURCES_REQUIRED_TYPE = eINSTANCE.getResourcesRequiredType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.ScheduleStatus <em>Schedule Status</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.ScheduleStatus
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleStatus()
         * @generated
         */
        EEnum SCHEDULE_STATUS = eINSTANCE.getScheduleStatus();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.WorkGroupType <em>Work Group Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkGroupType
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkGroupType()
         * @generated
         */
        EEnum WORK_GROUP_TYPE = eINSTANCE.getWorkGroupType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.WorkItemScriptOperation <em>Work Item Script Operation</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkItemScriptOperation
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemScriptOperation()
         * @generated
         */
        EEnum WORK_ITEM_SCRIPT_OPERATION = eINSTANCE.getWorkItemScriptOperation();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.WorkItemScriptType <em>Work Item Script Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkItemScriptType
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemScriptType()
         * @generated
         */
        EEnum WORK_ITEM_SCRIPT_TYPE = eINSTANCE.getWorkItemScriptType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.api.WorkItemState <em>Work Item State</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkItemState
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemState()
         * @generated
         */
        EEnum WORK_ITEM_STATE = eINSTANCE.getWorkItemState();

        /**
         * The meta object literal for the '<em>Attribute10 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute10Type()
         * @generated
         */
        EDataType ATTRIBUTE10_TYPE = eINSTANCE.getAttribute10Type();

        /**
         * The meta object literal for the '<em>Attribute11 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute11Type()
         * @generated
         */
        EDataType ATTRIBUTE11_TYPE = eINSTANCE.getAttribute11Type();

        /**
         * The meta object literal for the '<em>Attribute12 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute12Type()
         * @generated
         */
        EDataType ATTRIBUTE12_TYPE = eINSTANCE.getAttribute12Type();

        /**
         * The meta object literal for the '<em>Attribute13 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute13Type()
         * @generated
         */
        EDataType ATTRIBUTE13_TYPE = eINSTANCE.getAttribute13Type();

        /**
         * The meta object literal for the '<em>Attribute14 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute14Type()
         * @generated
         */
        EDataType ATTRIBUTE14_TYPE = eINSTANCE.getAttribute14Type();

        /**
         * The meta object literal for the '<em>Attribute21 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute21Type()
         * @generated
         */
        EDataType ATTRIBUTE21_TYPE = eINSTANCE.getAttribute21Type();

        /**
         * The meta object literal for the '<em>Attribute22 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute22Type()
         * @generated
         */
        EDataType ATTRIBUTE22_TYPE = eINSTANCE.getAttribute22Type();

        /**
         * The meta object literal for the '<em>Attribute23 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute23Type()
         * @generated
         */
        EDataType ATTRIBUTE23_TYPE = eINSTANCE.getAttribute23Type();

        /**
         * The meta object literal for the '<em>Attribute24 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute24Type()
         * @generated
         */
        EDataType ATTRIBUTE24_TYPE = eINSTANCE.getAttribute24Type();

        /**
         * The meta object literal for the '<em>Attribute25 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute25Type()
         * @generated
         */
        EDataType ATTRIBUTE25_TYPE = eINSTANCE.getAttribute25Type();

        /**
         * The meta object literal for the '<em>Attribute26 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute26Type()
         * @generated
         */
        EDataType ATTRIBUTE26_TYPE = eINSTANCE.getAttribute26Type();

        /**
         * The meta object literal for the '<em>Attribute27 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute27Type()
         * @generated
         */
        EDataType ATTRIBUTE27_TYPE = eINSTANCE.getAttribute27Type();

        /**
         * The meta object literal for the '<em>Attribute28 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute28Type()
         * @generated
         */
        EDataType ATTRIBUTE28_TYPE = eINSTANCE.getAttribute28Type();

        /**
         * The meta object literal for the '<em>Attribute29 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute29Type()
         * @generated
         */
        EDataType ATTRIBUTE29_TYPE = eINSTANCE.getAttribute29Type();

        /**
         * The meta object literal for the '<em>Attribute2 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute2Type()
         * @generated
         */
        EDataType ATTRIBUTE2_TYPE = eINSTANCE.getAttribute2Type();

        /**
         * The meta object literal for the '<em>Attribute30 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute30Type()
         * @generated
         */
        EDataType ATTRIBUTE30_TYPE = eINSTANCE.getAttribute30Type();

        /**
         * The meta object literal for the '<em>Attribute31 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute31Type()
         * @generated
         */
        EDataType ATTRIBUTE31_TYPE = eINSTANCE.getAttribute31Type();

        /**
         * The meta object literal for the '<em>Attribute32 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute32Type()
         * @generated
         */
        EDataType ATTRIBUTE32_TYPE = eINSTANCE.getAttribute32Type();

        /**
         * The meta object literal for the '<em>Attribute33 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute33Type()
         * @generated
         */
        EDataType ATTRIBUTE33_TYPE = eINSTANCE.getAttribute33Type();

        /**
         * The meta object literal for the '<em>Attribute34 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute34Type()
         * @generated
         */
        EDataType ATTRIBUTE34_TYPE = eINSTANCE.getAttribute34Type();

        /**
         * The meta object literal for the '<em>Attribute35 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute35Type()
         * @generated
         */
        EDataType ATTRIBUTE35_TYPE = eINSTANCE.getAttribute35Type();

        /**
         * The meta object literal for the '<em>Attribute36 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute36Type()
         * @generated
         */
        EDataType ATTRIBUTE36_TYPE = eINSTANCE.getAttribute36Type();

        /**
         * The meta object literal for the '<em>Attribute37 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute37Type()
         * @generated
         */
        EDataType ATTRIBUTE37_TYPE = eINSTANCE.getAttribute37Type();

        /**
         * The meta object literal for the '<em>Attribute38 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute38Type()
         * @generated
         */
        EDataType ATTRIBUTE38_TYPE = eINSTANCE.getAttribute38Type();

        /**
         * The meta object literal for the '<em>Attribute39 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute39Type()
         * @generated
         */
        EDataType ATTRIBUTE39_TYPE = eINSTANCE.getAttribute39Type();

        /**
         * The meta object literal for the '<em>Attribute3 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute3Type()
         * @generated
         */
        EDataType ATTRIBUTE3_TYPE = eINSTANCE.getAttribute3Type();

        /**
         * The meta object literal for the '<em>Attribute40 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute40Type()
         * @generated
         */
        EDataType ATTRIBUTE40_TYPE = eINSTANCE.getAttribute40Type();

        /**
         * The meta object literal for the '<em>Attribute4 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute4Type()
         * @generated
         */
        EDataType ATTRIBUTE4_TYPE = eINSTANCE.getAttribute4Type();

        /**
         * The meta object literal for the '<em>Attribute8 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute8Type()
         * @generated
         */
        EDataType ATTRIBUTE8_TYPE = eINSTANCE.getAttribute8Type();

        /**
         * The meta object literal for the '<em>Attribute9 Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getAttribute9Type()
         * @generated
         */
        EDataType ATTRIBUTE9_TYPE = eINSTANCE.getAttribute9Type();

        /**
         * The meta object literal for the '<em>Column Capability Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.ColumnCapability
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnCapabilityObject()
         * @generated
         */
        EDataType COLUMN_CAPABILITY_OBJECT = eINSTANCE.getColumnCapabilityObject();

        /**
         * The meta object literal for the '<em>Column Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.ColumnType
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getColumnTypeObject()
         * @generated
         */
        EDataType COLUMN_TYPE_OBJECT = eINSTANCE.getColumnTypeObject();

        /**
         * The meta object literal for the '<em>Description Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDescriptionType()
         * @generated
         */
        EDataType DESCRIPTION_TYPE = eINSTANCE.getDescriptionType();

        /**
         * The meta object literal for the '<em>Distribution Strategy Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.DistributionStrategy
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getDistributionStrategyObject()
         * @generated
         */
        EDataType DISTRIBUTION_STRATEGY_OBJECT = eINSTANCE.getDistributionStrategyObject();

        /**
         * The meta object literal for the '<em>Locker Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getLockerType()
         * @generated
         */
        EDataType LOCKER_TYPE = eINSTANCE.getLockerType();

        /**
         * The meta object literal for the '<em>Method Authorisation Action Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.MethodAuthorisationAction
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getMethodAuthorisationActionObject()
         * @generated
         */
        EDataType METHOD_AUTHORISATION_ACTION_OBJECT = eINSTANCE.getMethodAuthorisationActionObject();

        /**
         * The meta object literal for the '<em>Method Authorisation Component Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.MethodAuthorisationComponent
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getMethodAuthorisationComponentObject()
         * @generated
         */
        EDataType METHOD_AUTHORISATION_COMPONENT_OBJECT = eINSTANCE.getMethodAuthorisationComponentObject();

        /**
         * The meta object literal for the '<em>Name Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getNameType()
         * @generated
         */
        EDataType NAME_TYPE = eINSTANCE.getNameType();

        /**
         * The meta object literal for the '<em>Owner Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getOwnerType()
         * @generated
         */
        EDataType OWNER_TYPE = eINSTANCE.getOwnerType();

        /**
         * The meta object literal for the '<em>Resources Required Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.ResourcesRequiredType
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getResourcesRequiredTypeObject()
         * @generated
         */
        EDataType RESOURCES_REQUIRED_TYPE_OBJECT = eINSTANCE.getResourcesRequiredTypeObject();

        /**
         * The meta object literal for the '<em>Schedule Status Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.ScheduleStatus
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScheduleStatusObject()
         * @generated
         */
        EDataType SCHEDULE_STATUS_OBJECT = eINSTANCE.getScheduleStatusObject();

        /**
         * The meta object literal for the '<em>Script Operation Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkItemScriptOperation
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getScriptOperationType()
         * @generated
         */
        EDataType SCRIPT_OPERATION_TYPE = eINSTANCE.getScriptOperationType();

        /**
         * The meta object literal for the '<em>Work Group Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkGroupType
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkGroupTypeObject()
         * @generated
         */
        EDataType WORK_GROUP_TYPE_OBJECT = eINSTANCE.getWorkGroupTypeObject();

        /**
         * The meta object literal for the '<em>Work Item Script Operation Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkItemScriptOperation
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemScriptOperationObject()
         * @generated
         */
        EDataType WORK_ITEM_SCRIPT_OPERATION_OBJECT = eINSTANCE.getWorkItemScriptOperationObject();

        /**
         * The meta object literal for the '<em>Work Item Script Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkItemScriptType
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemScriptTypeObject()
         * @generated
         */
        EDataType WORK_ITEM_SCRIPT_TYPE_OBJECT = eINSTANCE.getWorkItemScriptTypeObject();

        /**
         * The meta object literal for the '<em>Work Item State Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.api.WorkItemState
         * @see com.tibco.n2.brm.api.impl.N2BRMPackageImpl#getWorkItemStateObject()
         * @generated
         */
        EDataType WORK_ITEM_STATE_OBJECT = eINSTANCE.getWorkItemStateObject();

    }

} //N2BRMPackage
