/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.util;

import com.tibco.n2.brm.api.*;

import com.tibco.n2.common.datamodel.DataModel;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

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
 * @see com.tibco.n2.brm.api.N2BRMPackage
 * @generated
 */
public class N2BRMSwitch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static N2BRMPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public N2BRMSwitch() {
        if (modelPackage == null) {
            modelPackage = N2BRMPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        }
        else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return
                eSuperTypes.isEmpty() ?
                    defaultCase(theEObject) :
                    doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case N2BRMPackage.ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE: {
                AddCurrentResourceToViewResponseType addCurrentResourceToViewResponseType = (AddCurrentResourceToViewResponseType)theEObject;
                T result = caseAddCurrentResourceToViewResponseType(addCurrentResourceToViewResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ADD_CURRENT_RESOURCE_TO_VIEW_TYPE: {
                AddCurrentResourceToViewType addCurrentResourceToViewType = (AddCurrentResourceToViewType)theEObject;
                T result = caseAddCurrentResourceToViewType(addCurrentResourceToViewType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE: {
                AllocateAndOpenNextWorkItemResponseType allocateAndOpenNextWorkItemResponseType = (AllocateAndOpenNextWorkItemResponseType)theEObject;
                T result = caseAllocateAndOpenNextWorkItemResponseType(allocateAndOpenNextWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE: {
                AllocateAndOpenNextWorkItemType allocateAndOpenNextWorkItemType = (AllocateAndOpenNextWorkItemType)theEObject;
                T result = caseAllocateAndOpenNextWorkItemType(allocateAndOpenNextWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE: {
                AllocateAndOpenWorkItemResponseType allocateAndOpenWorkItemResponseType = (AllocateAndOpenWorkItemResponseType)theEObject;
                T result = caseAllocateAndOpenWorkItemResponseType(allocateAndOpenWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ALLOCATE_AND_OPEN_WORK_ITEM_TYPE: {
                AllocateAndOpenWorkItemType allocateAndOpenWorkItemType = (AllocateAndOpenWorkItemType)theEObject;
                T result = caseAllocateAndOpenWorkItemType(allocateAndOpenWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ALLOCATE_WORK_ITEM_RESPONSE_TYPE: {
                AllocateWorkItemResponseType allocateWorkItemResponseType = (AllocateWorkItemResponseType)theEObject;
                T result = caseAllocateWorkItemResponseType(allocateWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ALLOCATE_WORK_ITEM_TYPE: {
                AllocateWorkItemType allocateWorkItemType = (AllocateWorkItemType)theEObject;
                T result = caseAllocateWorkItemType(allocateWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ALLOCATION_HISTORY: {
                AllocationHistory allocationHistory = (AllocationHistory)theEObject;
                T result = caseAllocationHistory(allocationHistory);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE: {
                AsyncCancelWorkItemResponseType asyncCancelWorkItemResponseType = (AsyncCancelWorkItemResponseType)theEObject;
                T result = caseAsyncCancelWorkItemResponseType(asyncCancelWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_TYPE: {
                AsyncCancelWorkItemType asyncCancelWorkItemType = (AsyncCancelWorkItemType)theEObject;
                T result = caseAsyncCancelWorkItemType(asyncCancelWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_END_GROUP_RESPONSE_TYPE: {
                AsyncEndGroupResponseType asyncEndGroupResponseType = (AsyncEndGroupResponseType)theEObject;
                T result = caseAsyncEndGroupResponseType(asyncEndGroupResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_END_GROUP_TYPE: {
                AsyncEndGroupType asyncEndGroupType = (AsyncEndGroupType)theEObject;
                T result = caseAsyncEndGroupType(asyncEndGroupType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE: {
                AsyncRescheduleWorkItemResponseType asyncRescheduleWorkItemResponseType = (AsyncRescheduleWorkItemResponseType)theEObject;
                T result = caseAsyncRescheduleWorkItemResponseType(asyncRescheduleWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_RESCHEDULE_WORK_ITEM_TYPE: {
                AsyncRescheduleWorkItemType asyncRescheduleWorkItemType = (AsyncRescheduleWorkItemType)theEObject;
                T result = caseAsyncRescheduleWorkItemType(asyncRescheduleWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE: {
                AsyncResumeWorkItemResponseType asyncResumeWorkItemResponseType = (AsyncResumeWorkItemResponseType)theEObject;
                T result = caseAsyncResumeWorkItemResponseType(asyncResumeWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE: {
                AsyncResumeWorkItemType asyncResumeWorkItemType = (AsyncResumeWorkItemType)theEObject;
                T result = caseAsyncResumeWorkItemType(asyncResumeWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE: {
                AsyncScheduleWorkItemResponseType asyncScheduleWorkItemResponseType = (AsyncScheduleWorkItemResponseType)theEObject;
                T result = caseAsyncScheduleWorkItemResponseType(asyncScheduleWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE: {
                AsyncScheduleWorkItemType asyncScheduleWorkItemType = (AsyncScheduleWorkItemType)theEObject;
                T result = caseAsyncScheduleWorkItemType(asyncScheduleWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE: {
                AsyncScheduleWorkItemWithModelResponseType asyncScheduleWorkItemWithModelResponseType = (AsyncScheduleWorkItemWithModelResponseType)theEObject;
                T result = caseAsyncScheduleWorkItemWithModelResponseType(asyncScheduleWorkItemWithModelResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE: {
                AsyncScheduleWorkItemWithModelType asyncScheduleWorkItemWithModelType = (AsyncScheduleWorkItemWithModelType)theEObject;
                T result = caseAsyncScheduleWorkItemWithModelType(asyncScheduleWorkItemWithModelType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_START_GROUP_RESPONSE_TYPE: {
                AsyncStartGroupResponseType asyncStartGroupResponseType = (AsyncStartGroupResponseType)theEObject;
                T result = caseAsyncStartGroupResponseType(asyncStartGroupResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_START_GROUP_TYPE: {
                AsyncStartGroupType asyncStartGroupType = (AsyncStartGroupType)theEObject;
                T result = caseAsyncStartGroupType(asyncStartGroupType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE: {
                AsyncSuspendWorkItemResponseType asyncSuspendWorkItemResponseType = (AsyncSuspendWorkItemResponseType)theEObject;
                T result = caseAsyncSuspendWorkItemResponseType(asyncSuspendWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_SUSPEND_WORK_ITEM_TYPE: {
                AsyncSuspendWorkItemType asyncSuspendWorkItemType = (AsyncSuspendWorkItemType)theEObject;
                T result = caseAsyncSuspendWorkItemType(asyncSuspendWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS: {
                AsyncWorkItemDetails asyncWorkItemDetails = (AsyncWorkItemDetails)theEObject;
                T result = caseAsyncWorkItemDetails(asyncWorkItemDetails);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE: {
                AttributeAliasListType attributeAliasListType = (AttributeAliasListType)theEObject;
                T result = caseAttributeAliasListType(attributeAliasListType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.BASE_ITEM_INFO: {
                BaseItemInfo baseItemInfo = (BaseItemInfo)theEObject;
                T result = caseBaseItemInfo(baseItemInfo);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.BASE_MODEL_INFO: {
                BaseModelInfo baseModelInfo = (BaseModelInfo)theEObject;
                T result = caseBaseModelInfo(baseModelInfo);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.CANCEL_WORK_ITEM_RESPONSE_TYPE: {
                CancelWorkItemResponseType cancelWorkItemResponseType = (CancelWorkItemResponseType)theEObject;
                T result = caseCancelWorkItemResponseType(cancelWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.CANCEL_WORK_ITEM_TYPE: {
                CancelWorkItemType cancelWorkItemType = (CancelWorkItemType)theEObject;
                T result = caseCancelWorkItemType(cancelWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.CHAINED_WORK_ITEM_NOTIFICATION_TYPE: {
                ChainedWorkItemNotificationType chainedWorkItemNotificationType = (ChainedWorkItemNotificationType)theEObject;
                T result = caseChainedWorkItemNotificationType(chainedWorkItemNotificationType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.CLOSE_WORK_ITEM_RESPONSE_TYPE: {
                CloseWorkItemResponseType closeWorkItemResponseType = (CloseWorkItemResponseType)theEObject;
                T result = caseCloseWorkItemResponseType(closeWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.CLOSE_WORK_ITEM_TYPE: {
                CloseWorkItemType closeWorkItemType = (CloseWorkItemType)theEObject;
                T result = caseCloseWorkItemType(closeWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.COLUMN_DEFINITION: {
                ColumnDefinition columnDefinition = (ColumnDefinition)theEObject;
                T result = caseColumnDefinition(columnDefinition);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.COLUMN_ORDER: {
                ColumnOrder columnOrder = (ColumnOrder)theEObject;
                T result = caseColumnOrder(columnOrder);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE: {
                CompleteWorkItemResponseType completeWorkItemResponseType = (CompleteWorkItemResponseType)theEObject;
                T result = caseCompleteWorkItemResponseType(completeWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE: {
                CompleteWorkItemType completeWorkItemType = (CompleteWorkItemType)theEObject;
                T result = caseCompleteWorkItemType(completeWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.CREATE_WORK_LIST_VIEW_RESPONSE_TYPE: {
                CreateWorkListViewResponseType createWorkListViewResponseType = (CreateWorkListViewResponseType)theEObject;
                T result = caseCreateWorkListViewResponseType(createWorkListViewResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE: {
                DeleteCurrentResourceFromViewResponseType deleteCurrentResourceFromViewResponseType = (DeleteCurrentResourceFromViewResponseType)theEObject;
                T result = caseDeleteCurrentResourceFromViewResponseType(deleteCurrentResourceFromViewResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE: {
                DeleteCurrentResourceFromViewType deleteCurrentResourceFromViewType = (DeleteCurrentResourceFromViewType)theEObject;
                T result = caseDeleteCurrentResourceFromViewType(deleteCurrentResourceFromViewType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE: {
                DeleteOrgEntityConfigAttributesResponseType deleteOrgEntityConfigAttributesResponseType = (DeleteOrgEntityConfigAttributesResponseType)theEObject;
                T result = caseDeleteOrgEntityConfigAttributesResponseType(deleteOrgEntityConfigAttributesResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE: {
                DeleteOrgEntityConfigAttributesType deleteOrgEntityConfigAttributesType = (DeleteOrgEntityConfigAttributesType)theEObject;
                T result = caseDeleteOrgEntityConfigAttributesType(deleteOrgEntityConfigAttributesType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.DELETE_WORK_LIST_VIEW_RESPONSE_TYPE: {
                DeleteWorkListViewResponseType deleteWorkListViewResponseType = (DeleteWorkListViewResponseType)theEObject;
                T result = caseDeleteWorkListViewResponseType(deleteWorkListViewResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.DELETE_WORK_LIST_VIEW_TYPE: {
                DeleteWorkListViewType deleteWorkListViewType = (DeleteWorkListViewType)theEObject;
                T result = caseDeleteWorkListViewType(deleteWorkListViewType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.DOCUMENT_ROOT: {
                DocumentRoot documentRoot = (DocumentRoot)theEObject;
                T result = caseDocumentRoot(documentRoot);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE: {
                EditWorkListViewResponseType editWorkListViewResponseType = (EditWorkListViewResponseType)theEObject;
                T result = caseEditWorkListViewResponseType(editWorkListViewResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_TYPE: {
                EditWorkListViewType editWorkListViewType = (EditWorkListViewType)theEObject;
                T result = caseEditWorkListViewType(editWorkListViewType);
                if (result == null) result = caseWorkListViewEdit(editWorkListViewType);
                if (result == null) result = caseWorkListViewCommon(editWorkListViewType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ENABLE_WORK_ITEM_RESPONSE_TYPE: {
                EnableWorkItemResponseType enableWorkItemResponseType = (EnableWorkItemResponseType)theEObject;
                T result = caseEnableWorkItemResponseType(enableWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ENABLE_WORK_ITEM_TYPE: {
                EnableWorkItemType enableWorkItemType = (EnableWorkItemType)theEObject;
                T result = caseEnableWorkItemType(enableWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.END_GROUP_RESPONSE_TYPE: {
                EndGroupResponseType endGroupResponseType = (EndGroupResponseType)theEObject;
                T result = caseEndGroupResponseType(endGroupResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.END_GROUP_TYPE: {
                EndGroupType endGroupType = (EndGroupType)theEObject;
                T result = caseEndGroupType(endGroupType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_ALLOCATED_WORK_LIST_ITEMS_TYPE: {
                GetAllocatedWorkListItemsType getAllocatedWorkListItemsType = (GetAllocatedWorkListItemsType)theEObject;
                T result = caseGetAllocatedWorkListItemsType(getAllocatedWorkListItemsType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_BATCH_GROUP_IDS_RESPONSE_TYPE: {
                GetBatchGroupIdsResponseType getBatchGroupIdsResponseType = (GetBatchGroupIdsResponseType)theEObject;
                T result = caseGetBatchGroupIdsResponseType(getBatchGroupIdsResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE: {
                GetBatchWorkItemIdsResponseType getBatchWorkItemIdsResponseType = (GetBatchWorkItemIdsResponseType)theEObject;
                T result = caseGetBatchWorkItemIdsResponseType(getBatchWorkItemIdsResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE: {
                GetEditableWorkListViewsResponseType getEditableWorkListViewsResponseType = (GetEditableWorkListViewsResponseType)theEObject;
                T result = caseGetEditableWorkListViewsResponseType(getEditableWorkListViewsResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_TYPE: {
                GetEditableWorkListViewsType getEditableWorkListViewsType = (GetEditableWorkListViewsType)theEObject;
                T result = caseGetEditableWorkListViewsType(getEditableWorkListViewsType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE: {
                GetOfferSetResponseType getOfferSetResponseType = (GetOfferSetResponseType)theEObject;
                T result = caseGetOfferSetResponseType(getOfferSetResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_OFFER_SET_TYPE: {
                GetOfferSetType getOfferSetType = (GetOfferSetType)theEObject;
                T result = caseGetOfferSetType(getOfferSetType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE: {
                GetOrgEntityConfigAttributesAvailableResponseType getOrgEntityConfigAttributesAvailableResponseType = (GetOrgEntityConfigAttributesAvailableResponseType)theEObject;
                T result = caseGetOrgEntityConfigAttributesAvailableResponseType(getOrgEntityConfigAttributesAvailableResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE: {
                GetOrgEntityConfigAttributesAvailableType getOrgEntityConfigAttributesAvailableType = (GetOrgEntityConfigAttributesAvailableType)theEObject;
                T result = caseGetOrgEntityConfigAttributesAvailableType(getOrgEntityConfigAttributesAvailableType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE: {
                GetOrgEntityConfigAttributesResponseType getOrgEntityConfigAttributesResponseType = (GetOrgEntityConfigAttributesResponseType)theEObject;
                T result = caseGetOrgEntityConfigAttributesResponseType(getOrgEntityConfigAttributesResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE: {
                GetOrgEntityConfigAttributesType getOrgEntityConfigAttributesType = (GetOrgEntityConfigAttributesType)theEObject;
                T result = caseGetOrgEntityConfigAttributesType(getOrgEntityConfigAttributesType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE: {
                GetPublicWorkListViewsResponseType getPublicWorkListViewsResponseType = (GetPublicWorkListViewsResponseType)theEObject;
                T result = caseGetPublicWorkListViewsResponseType(getPublicWorkListViewsResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_PUBLIC_WORK_LIST_VIEWS_TYPE: {
                GetPublicWorkListViewsType getPublicWorkListViewsType = (GetPublicWorkListViewsType)theEObject;
                T result = caseGetPublicWorkListViewsType(getPublicWorkListViewsType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE: {
                GetResourceOrderFilterCriteriaResponseType getResourceOrderFilterCriteriaResponseType = (GetResourceOrderFilterCriteriaResponseType)theEObject;
                T result = caseGetResourceOrderFilterCriteriaResponseType(getResourceOrderFilterCriteriaResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE: {
                GetResourceOrderFilterCriteriaType getResourceOrderFilterCriteriaType = (GetResourceOrderFilterCriteriaType)theEObject;
                T result = caseGetResourceOrderFilterCriteriaType(getResourceOrderFilterCriteriaType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE: {
                GetViewsForResourceResponseType getViewsForResourceResponseType = (GetViewsForResourceResponseType)theEObject;
                T result = caseGetViewsForResourceResponseType(getViewsForResourceResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE: {
                GetViewsForResourceType getViewsForResourceType = (GetViewsForResourceType)theEObject;
                T result = caseGetViewsForResourceType(getViewsForResourceType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_ITEM_HEADER_RESPONSE_TYPE: {
                GetWorkItemHeaderResponseType getWorkItemHeaderResponseType = (GetWorkItemHeaderResponseType)theEObject;
                T result = caseGetWorkItemHeaderResponseType(getWorkItemHeaderResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_ITEM_HEADER_TYPE: {
                GetWorkItemHeaderType getWorkItemHeaderType = (GetWorkItemHeaderType)theEObject;
                T result = caseGetWorkItemHeaderType(getWorkItemHeaderType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE: {
                GetWorkItemOrderFilterResponseType getWorkItemOrderFilterResponseType = (GetWorkItemOrderFilterResponseType)theEObject;
                T result = caseGetWorkItemOrderFilterResponseType(getWorkItemOrderFilterResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE: {
                GetWorkItemOrderFilterType getWorkItemOrderFilterType = (GetWorkItemOrderFilterType)theEObject;
                T result = caseGetWorkItemOrderFilterType(getWorkItemOrderFilterType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE: {
                GetWorkListItemsForGlobalDataResponseType getWorkListItemsForGlobalDataResponseType = (GetWorkListItemsForGlobalDataResponseType)theEObject;
                T result = caseGetWorkListItemsForGlobalDataResponseType(getWorkListItemsForGlobalDataResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE: {
                GetWorkListItemsForGlobalDataType getWorkListItemsForGlobalDataType = (GetWorkListItemsForGlobalDataType)theEObject;
                T result = caseGetWorkListItemsForGlobalDataType(getWorkListItemsForGlobalDataType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE: {
                GetWorkListItemsForViewResponseType getWorkListItemsForViewResponseType = (GetWorkListItemsForViewResponseType)theEObject;
                T result = caseGetWorkListItemsForViewResponseType(getWorkListItemsForViewResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE: {
                GetWorkListItemsForViewType getWorkListItemsForViewType = (GetWorkListItemsForViewType)theEObject;
                T result = caseGetWorkListItemsForViewType(getWorkListItemsForViewType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_LIST_ITEMS_RESPONSE_TYPE: {
                GetWorkListItemsResponseType getWorkListItemsResponseType = (GetWorkListItemsResponseType)theEObject;
                T result = caseGetWorkListItemsResponseType(getWorkListItemsResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_LIST_ITEMS_RESPONSE_TYPE1: {
                GetWorkListItemsResponseType1 getWorkListItemsResponseType1 = (GetWorkListItemsResponseType1)theEObject;
                T result = caseGetWorkListItemsResponseType1(getWorkListItemsResponseType1);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_LIST_ITEMS_TYPE: {
                GetWorkListItemsType getWorkListItemsType = (GetWorkListItemsType)theEObject;
                T result = caseGetWorkListItemsType(getWorkListItemsType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE: {
                GetWorkListViewDetailsType getWorkListViewDetailsType = (GetWorkListViewDetailsType)theEObject;
                T result = caseGetWorkListViewDetailsType(getWorkListViewDetailsType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE: {
                GetWorkModelResponseType getWorkModelResponseType = (GetWorkModelResponseType)theEObject;
                T result = caseGetWorkModelResponseType(getWorkModelResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE: {
                GetWorkModelsResponseType getWorkModelsResponseType = (GetWorkModelsResponseType)theEObject;
                T result = caseGetWorkModelsResponseType(getWorkModelsResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_MODELS_TYPE: {
                GetWorkModelsType getWorkModelsType = (GetWorkModelsType)theEObject;
                T result = caseGetWorkModelsType(getWorkModelsType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_MODEL_TYPE: {
                GetWorkModelType getWorkModelType = (GetWorkModelType)theEObject;
                T result = caseGetWorkModelType(getWorkModelType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE: {
                GetWorkTypeResponseType getWorkTypeResponseType = (GetWorkTypeResponseType)theEObject;
                T result = caseGetWorkTypeResponseType(getWorkTypeResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE: {
                GetWorkTypesResponseType getWorkTypesResponseType = (GetWorkTypesResponseType)theEObject;
                T result = caseGetWorkTypesResponseType(getWorkTypesResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_TYPES_TYPE: {
                GetWorkTypesType getWorkTypesType = (GetWorkTypesType)theEObject;
                T result = caseGetWorkTypesType(getWorkTypesType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.GET_WORK_TYPE_TYPE: {
                GetWorkTypeType getWorkTypeType = (GetWorkTypeType)theEObject;
                T result = caseGetWorkTypeType(getWorkTypeType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.HIDDEN_PERIOD: {
                HiddenPeriod hiddenPeriod = (HiddenPeriod)theEObject;
                T result = caseHiddenPeriod(hiddenPeriod);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ITEM: {
                Item item = (Item)theEObject;
                T result = caseItem(item);
                if (result == null) result = caseBaseItemInfo(item);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ITEM_BODY: {
                ItemBody itemBody = (ItemBody)theEObject;
                T result = caseItemBody(itemBody);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ITEM_CONTEXT: {
                ItemContext itemContext = (ItemContext)theEObject;
                T result = caseItemContext(itemContext);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ITEM_DURATION: {
                ItemDuration itemDuration = (ItemDuration)theEObject;
                T result = caseItemDuration(itemDuration);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ITEM_PRIVILEGE: {
                ItemPrivilege itemPrivilege = (ItemPrivilege)theEObject;
                T result = caseItemPrivilege(itemPrivilege);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ITEM_SCHEDULE: {
                ItemSchedule itemSchedule = (ItemSchedule)theEObject;
                T result = caseItemSchedule(itemSchedule);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.MANAGED_OBJECT_ID: {
                ManagedObjectID managedObjectID = (ManagedObjectID)theEObject;
                T result = caseManagedObjectID(managedObjectID);
                if (result == null) result = caseObjectID(managedObjectID);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.NEXT_WORK_ITEM: {
                NextWorkItem nextWorkItem = (NextWorkItem)theEObject;
                T result = caseNextWorkItem(nextWorkItem);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.OBJECT_ID: {
                ObjectID objectID = (ObjectID)theEObject;
                T result = caseObjectID(objectID);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ON_NOTIFICATION_RESPONSE_TYPE: {
                OnNotificationResponseType onNotificationResponseType = (OnNotificationResponseType)theEObject;
                T result = caseOnNotificationResponseType(onNotificationResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ON_NOTIFICATION_TYPE: {
                OnNotificationType onNotificationType = (OnNotificationType)theEObject;
                T result = caseOnNotificationType(onNotificationType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.OPEN_WORK_ITEM_RESPONSE_TYPE: {
                OpenWorkItemResponseType openWorkItemResponseType = (OpenWorkItemResponseType)theEObject;
                T result = caseOpenWorkItemResponseType(openWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.OPEN_WORK_ITEM_TYPE: {
                OpenWorkItemType openWorkItemType = (OpenWorkItemType)theEObject;
                T result = caseOpenWorkItemType(openWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ORDER_FILTER_CRITERIA: {
                OrderFilterCriteria orderFilterCriteria = (OrderFilterCriteria)theEObject;
                T result = caseOrderFilterCriteria(orderFilterCriteria);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTE: {
                OrgEntityConfigAttribute orgEntityConfigAttribute = (OrgEntityConfigAttribute)theEObject;
                T result = caseOrgEntityConfigAttribute(orgEntityConfigAttribute);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE: {
                OrgEntityConfigAttributesAvailable orgEntityConfigAttributesAvailable = (OrgEntityConfigAttributesAvailable)theEObject;
                T result = caseOrgEntityConfigAttributesAvailable(orgEntityConfigAttributesAvailable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTE_SET: {
                OrgEntityConfigAttributeSet orgEntityConfigAttributeSet = (OrgEntityConfigAttributeSet)theEObject;
                T result = caseOrgEntityConfigAttributeSet(orgEntityConfigAttributeSet);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.PARAMETER_TYPE: {
                ParameterType parameterType = (ParameterType)theEObject;
                T result = caseParameterType(parameterType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.PEND_WORK_ITEM: {
                PendWorkItem pendWorkItem = (PendWorkItem)theEObject;
                T result = casePendWorkItem(pendWorkItem);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.PEND_WORK_ITEM_RESPONSE_TYPE: {
                PendWorkItemResponseType pendWorkItemResponseType = (PendWorkItemResponseType)theEObject;
                T result = casePendWorkItemResponseType(pendWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE: {
                PreviewWorkItemFromListResponseType previewWorkItemFromListResponseType = (PreviewWorkItemFromListResponseType)theEObject;
                T result = casePreviewWorkItemFromListResponseType(previewWorkItemFromListResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE: {
                PreviewWorkItemFromListType previewWorkItemFromListType = (PreviewWorkItemFromListType)theEObject;
                T result = casePreviewWorkItemFromListType(previewWorkItemFromListType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.PRIVILEGE: {
                Privilege privilege = (Privilege)theEObject;
                T result = casePrivilege(privilege);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE: {
                PushNotificationType pushNotificationType = (PushNotificationType)theEObject;
                T result = casePushNotificationType(pushNotificationType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA: {
                ReallocateWorkItemData reallocateWorkItemData = (ReallocateWorkItemData)theEObject;
                T result = caseReallocateWorkItemData(reallocateWorkItemData);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE: {
                ReallocateWorkItemDataResponseType reallocateWorkItemDataResponseType = (ReallocateWorkItemDataResponseType)theEObject;
                T result = caseReallocateWorkItemDataResponseType(reallocateWorkItemDataResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.REALLOCATE_WORK_ITEM_RESPONSE_TYPE: {
                ReallocateWorkItemResponseType reallocateWorkItemResponseType = (ReallocateWorkItemResponseType)theEObject;
                T result = caseReallocateWorkItemResponseType(reallocateWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.REALLOCATE_WORK_ITEM_TYPE: {
                ReallocateWorkItemType reallocateWorkItemType = (ReallocateWorkItemType)theEObject;
                T result = caseReallocateWorkItemType(reallocateWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.RESCHEDULE_WORK_ITEM: {
                RescheduleWorkItem rescheduleWorkItem = (RescheduleWorkItem)theEObject;
                T result = caseRescheduleWorkItem(rescheduleWorkItem);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.RESCHEDULE_WORK_ITEM_RESPONSE_TYPE: {
                RescheduleWorkItemResponseType rescheduleWorkItemResponseType = (RescheduleWorkItemResponseType)theEObject;
                T result = caseRescheduleWorkItemResponseType(rescheduleWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.RESUME_WORK_ITEM_RESPONSE_TYPE: {
                ResumeWorkItemResponseType resumeWorkItemResponseType = (ResumeWorkItemResponseType)theEObject;
                T result = caseResumeWorkItemResponseType(resumeWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.RESUME_WORK_ITEM_TYPE: {
                ResumeWorkItemType resumeWorkItemType = (ResumeWorkItemType)theEObject;
                T result = caseResumeWorkItemType(resumeWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE: {
                SaveOpenWorkItemResponseType saveOpenWorkItemResponseType = (SaveOpenWorkItemResponseType)theEObject;
                T result = caseSaveOpenWorkItemResponseType(saveOpenWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SAVE_OPEN_WORK_ITEM_TYPE: {
                SaveOpenWorkItemType saveOpenWorkItemType = (SaveOpenWorkItemType)theEObject;
                T result = caseSaveOpenWorkItemType(saveOpenWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SCHEDULE_WORK_ITEM_RESPONSE_TYPE: {
                ScheduleWorkItemResponseType scheduleWorkItemResponseType = (ScheduleWorkItemResponseType)theEObject;
                T result = caseScheduleWorkItemResponseType(scheduleWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE: {
                ScheduleWorkItemType scheduleWorkItemType = (ScheduleWorkItemType)theEObject;
                T result = caseScheduleWorkItemType(scheduleWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE: {
                ScheduleWorkItemWithModelResponseType scheduleWorkItemWithModelResponseType = (ScheduleWorkItemWithModelResponseType)theEObject;
                T result = caseScheduleWorkItemWithModelResponseType(scheduleWorkItemWithModelResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE: {
                ScheduleWorkItemWithModelType scheduleWorkItemWithModelType = (ScheduleWorkItemWithModelType)theEObject;
                T result = caseScheduleWorkItemWithModelType(scheduleWorkItemWithModelType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE: {
                SetOrgEntityConfigAttributesResponseType setOrgEntityConfigAttributesResponseType = (SetOrgEntityConfigAttributesResponseType)theEObject;
                T result = caseSetOrgEntityConfigAttributesResponseType(setOrgEntityConfigAttributesResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE: {
                SetOrgEntityConfigAttributesType setOrgEntityConfigAttributesType = (SetOrgEntityConfigAttributesType)theEObject;
                T result = caseSetOrgEntityConfigAttributesType(setOrgEntityConfigAttributesType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE: {
                SetResourceOrderFilterCriteriaResponseType setResourceOrderFilterCriteriaResponseType = (SetResourceOrderFilterCriteriaResponseType)theEObject;
                T result = caseSetResourceOrderFilterCriteriaResponseType(setResourceOrderFilterCriteriaResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE: {
                SetResourceOrderFilterCriteriaType setResourceOrderFilterCriteriaType = (SetResourceOrderFilterCriteriaType)theEObject;
                T result = caseSetResourceOrderFilterCriteriaType(setResourceOrderFilterCriteriaType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY: {
                SetWorkItemPriority setWorkItemPriority = (SetWorkItemPriority)theEObject;
                T result = caseSetWorkItemPriority(setWorkItemPriority);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE: {
                SetWorkItemPriorityResponseType setWorkItemPriorityResponseType = (SetWorkItemPriorityResponseType)theEObject;
                T result = caseSetWorkItemPriorityResponseType(setWorkItemPriorityResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SKIP_WORK_ITEM_RESPONSE_TYPE: {
                SkipWorkItemResponseType skipWorkItemResponseType = (SkipWorkItemResponseType)theEObject;
                T result = caseSkipWorkItemResponseType(skipWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SKIP_WORK_ITEM_TYPE: {
                SkipWorkItemType skipWorkItemType = (SkipWorkItemType)theEObject;
                T result = caseSkipWorkItemType(skipWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.START_GROUP_RESPONSE_TYPE: {
                StartGroupResponseType startGroupResponseType = (StartGroupResponseType)theEObject;
                T result = caseStartGroupResponseType(startGroupResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.START_GROUP_TYPE: {
                StartGroupType startGroupType = (StartGroupType)theEObject;
                T result = caseStartGroupType(startGroupType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SUSPEND_WORK_ITEM_RESPONSE_TYPE: {
                SuspendWorkItemResponseType suspendWorkItemResponseType = (SuspendWorkItemResponseType)theEObject;
                T result = caseSuspendWorkItemResponseType(suspendWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE: {
                SuspendWorkItemType suspendWorkItemType = (SuspendWorkItemType)theEObject;
                T result = caseSuspendWorkItemType(suspendWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.UNALLOCATE_WORK_ITEM_RESPONSE_TYPE: {
                UnallocateWorkItemResponseType unallocateWorkItemResponseType = (UnallocateWorkItemResponseType)theEObject;
                T result = caseUnallocateWorkItemResponseType(unallocateWorkItemResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.UNALLOCATE_WORK_ITEM_TYPE: {
                UnallocateWorkItemType unallocateWorkItemType = (UnallocateWorkItemType)theEObject;
                T result = caseUnallocateWorkItemType(unallocateWorkItemType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE: {
                UnlockWorkListViewResponseType unlockWorkListViewResponseType = (UnlockWorkListViewResponseType)theEObject;
                T result = caseUnlockWorkListViewResponseType(unlockWorkListViewResponseType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.UNLOCK_WORK_LIST_VIEW_TYPE: {
                UnlockWorkListViewType unlockWorkListViewType = (UnlockWorkListViewType)theEObject;
                T result = caseUnlockWorkListViewType(unlockWorkListViewType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_ITEM: {
                WorkItem workItem = (WorkItem)theEObject;
                T result = caseWorkItem(workItem);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES: {
                WorkItemAttributes workItemAttributes = (WorkItemAttributes)theEObject;
                T result = caseWorkItemAttributes(workItemAttributes);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_ITEM_BODY: {
                WorkItemBody workItemBody = (WorkItemBody)theEObject;
                T result = caseWorkItemBody(workItemBody);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_ITEM_FLAGS: {
                WorkItemFlags workItemFlags = (WorkItemFlags)theEObject;
                T result = caseWorkItemFlags(workItemFlags);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_ITEM_HEADER: {
                WorkItemHeader workItemHeader = (WorkItemHeader)theEObject;
                T result = caseWorkItemHeader(workItemHeader);
                if (result == null) result = caseBaseItemInfo(workItemHeader);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE: {
                WorkItemIDandPriorityType workItemIDandPriorityType = (WorkItemIDandPriorityType)theEObject;
                T result = caseWorkItemIDandPriorityType(workItemIDandPriorityType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_ITEM_PREVIEW: {
                WorkItemPreview workItemPreview = (WorkItemPreview)theEObject;
                T result = caseWorkItemPreview(workItemPreview);
                if (result == null) result = caseManagedObjectID(workItemPreview);
                if (result == null) result = caseObjectID(workItemPreview);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE: {
                WorkItemPriorityType workItemPriorityType = (WorkItemPriorityType)theEObject;
                T result = caseWorkItemPriorityType(workItemPriorityType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_LIST_VIEW: {
                WorkListView workListView = (WorkListView)theEObject;
                T result = caseWorkListView(workListView);
                if (result == null) result = caseWorkListViewEdit(workListView);
                if (result == null) result = caseWorkListViewCommon(workListView);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_LIST_VIEW_COMMON: {
                WorkListViewCommon workListViewCommon = (WorkListViewCommon)theEObject;
                T result = caseWorkListViewCommon(workListViewCommon);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_LIST_VIEW_EDIT: {
                WorkListViewEdit workListViewEdit = (WorkListViewEdit)theEObject;
                T result = caseWorkListViewEdit(workListViewEdit);
                if (result == null) result = caseWorkListViewCommon(workListViewEdit);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM: {
                WorkListViewPageItem workListViewPageItem = (WorkListViewPageItem)theEObject;
                T result = caseWorkListViewPageItem(workListViewPageItem);
                if (result == null) result = caseWorkListViewCommon(workListViewPageItem);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL: {
                WorkModel workModel = (WorkModel)theEObject;
                T result = caseWorkModel(workModel);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_ENTITIES: {
                WorkModelEntities workModelEntities = (WorkModelEntities)theEObject;
                T result = caseWorkModelEntities(workModelEntities);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_ENTITY: {
                WorkModelEntity workModelEntity = (WorkModelEntity)theEObject;
                T result = caseWorkModelEntity(workModelEntity);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_LIST: {
                WorkModelList workModelList = (WorkModelList)theEObject;
                T result = caseWorkModelList(workModelList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_MAPPING: {
                WorkModelMapping workModelMapping = (WorkModelMapping)theEObject;
                T result = caseWorkModelMapping(workModelMapping);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_SCRIPT: {
                WorkModelScript workModelScript = (WorkModelScript)theEObject;
                T result = caseWorkModelScript(workModelScript);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_SCRIPTS: {
                WorkModelScripts workModelScripts = (WorkModelScripts)theEObject;
                T result = caseWorkModelScripts(workModelScripts);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_SPECIFICATION: {
                WorkModelSpecification workModelSpecification = (WorkModelSpecification)theEObject;
                T result = caseWorkModelSpecification(workModelSpecification);
                if (result == null) result = caseDataModel(workModelSpecification);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_TYPE: {
                WorkModelType workModelType = (WorkModelType)theEObject;
                T result = caseWorkModelType(workModelType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_MODEL_TYPES: {
                WorkModelTypes workModelTypes = (WorkModelTypes)theEObject;
                T result = caseWorkModelTypes(workModelTypes);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case N2BRMPackage.WORK_TYPE_LIST: {
                WorkTypeList workTypeList = (WorkTypeList)theEObject;
                T result = caseWorkTypeList(workTypeList);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Add Current Resource To View Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Add Current Resource To View Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAddCurrentResourceToViewResponseType(AddCurrentResourceToViewResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Add Current Resource To View Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Add Current Resource To View Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAddCurrentResourceToViewType(AddCurrentResourceToViewType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocate And Open Next Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocate And Open Next Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocateAndOpenNextWorkItemResponseType(AllocateAndOpenNextWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocate And Open Next Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocate And Open Next Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocateAndOpenNextWorkItemType(AllocateAndOpenNextWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocate And Open Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocate And Open Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocateAndOpenWorkItemResponseType(AllocateAndOpenWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocate And Open Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocate And Open Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocateAndOpenWorkItemType(AllocateAndOpenWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocate Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocate Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocateWorkItemResponseType(AllocateWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocate Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocate Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocateWorkItemType(AllocateWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Allocation History</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Allocation History</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAllocationHistory(AllocationHistory object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Cancel Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Cancel Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncCancelWorkItemResponseType(AsyncCancelWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Cancel Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Cancel Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncCancelWorkItemType(AsyncCancelWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async End Group Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async End Group Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncEndGroupResponseType(AsyncEndGroupResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async End Group Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async End Group Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncEndGroupType(AsyncEndGroupType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Reschedule Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Reschedule Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncRescheduleWorkItemResponseType(AsyncRescheduleWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Reschedule Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Reschedule Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncRescheduleWorkItemType(AsyncRescheduleWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Resume Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Resume Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncResumeWorkItemResponseType(AsyncResumeWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Resume Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Resume Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncResumeWorkItemType(AsyncResumeWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Schedule Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Schedule Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncScheduleWorkItemResponseType(AsyncScheduleWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Schedule Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Schedule Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncScheduleWorkItemType(AsyncScheduleWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Schedule Work Item With Model Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Schedule Work Item With Model Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncScheduleWorkItemWithModelResponseType(AsyncScheduleWorkItemWithModelResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Schedule Work Item With Model Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Schedule Work Item With Model Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncScheduleWorkItemWithModelType(AsyncScheduleWorkItemWithModelType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Start Group Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Start Group Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncStartGroupResponseType(AsyncStartGroupResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Start Group Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Start Group Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncStartGroupType(AsyncStartGroupType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Suspend Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Suspend Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncSuspendWorkItemResponseType(AsyncSuspendWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Suspend Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Suspend Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncSuspendWorkItemType(AsyncSuspendWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Async Work Item Details</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Async Work Item Details</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAsyncWorkItemDetails(AsyncWorkItemDetails object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Attribute Alias List Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Attribute Alias List Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAttributeAliasListType(AttributeAliasListType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Base Item Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Base Item Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBaseItemInfo(BaseItemInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Base Model Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Base Model Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBaseModelInfo(BaseModelInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cancel Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cancel Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCancelWorkItemResponseType(CancelWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cancel Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cancel Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCancelWorkItemType(CancelWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Chained Work Item Notification Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Chained Work Item Notification Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChainedWorkItemNotificationType(ChainedWorkItemNotificationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Close Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Close Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCloseWorkItemResponseType(CloseWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Close Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Close Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCloseWorkItemType(CloseWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Column Definition</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Column Definition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseColumnDefinition(ColumnDefinition object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Column Order</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Column Order</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseColumnOrder(ColumnOrder object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Complete Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Complete Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCompleteWorkItemResponseType(CompleteWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Complete Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Complete Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCompleteWorkItemType(CompleteWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create Work List View Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Work List View Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCreateWorkListViewResponseType(CreateWorkListViewResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Current Resource From View Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Current Resource From View Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteCurrentResourceFromViewResponseType(DeleteCurrentResourceFromViewResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Current Resource From View Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Current Resource From View Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteCurrentResourceFromViewType(DeleteCurrentResourceFromViewType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Org Entity Config Attributes Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Org Entity Config Attributes Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteOrgEntityConfigAttributesResponseType(DeleteOrgEntityConfigAttributesResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Org Entity Config Attributes Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Org Entity Config Attributes Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteOrgEntityConfigAttributesType(DeleteOrgEntityConfigAttributesType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Work List View Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Work List View Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteWorkListViewResponseType(DeleteWorkListViewResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Work List View Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Work List View Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteWorkListViewType(DeleteWorkListViewType object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Edit Work List View Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edit Work List View Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEditWorkListViewResponseType(EditWorkListViewResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Edit Work List View Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Edit Work List View Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEditWorkListViewType(EditWorkListViewType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enable Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enable Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnableWorkItemResponseType(EnableWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enable Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enable Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnableWorkItemType(EnableWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>End Group Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>End Group Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEndGroupResponseType(EndGroupResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>End Group Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>End Group Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEndGroupType(EndGroupType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Allocated Work List Items Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Allocated Work List Items Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetAllocatedWorkListItemsType(GetAllocatedWorkListItemsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Batch Group Ids Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Batch Group Ids Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetBatchGroupIdsResponseType(GetBatchGroupIdsResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Batch Work Item Ids Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Batch Work Item Ids Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetBatchWorkItemIdsResponseType(GetBatchWorkItemIdsResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Editable Work List Views Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Editable Work List Views Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetEditableWorkListViewsResponseType(GetEditableWorkListViewsResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Editable Work List Views Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Editable Work List Views Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetEditableWorkListViewsType(GetEditableWorkListViewsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Offer Set Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Offer Set Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetOfferSetResponseType(GetOfferSetResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Offer Set Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Offer Set Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetOfferSetType(GetOfferSetType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Org Entity Config Attributes Available Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Org Entity Config Attributes Available Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetOrgEntityConfigAttributesAvailableResponseType(GetOrgEntityConfigAttributesAvailableResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Org Entity Config Attributes Available Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Org Entity Config Attributes Available Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetOrgEntityConfigAttributesAvailableType(GetOrgEntityConfigAttributesAvailableType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Org Entity Config Attributes Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Org Entity Config Attributes Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetOrgEntityConfigAttributesResponseType(GetOrgEntityConfigAttributesResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Org Entity Config Attributes Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Org Entity Config Attributes Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetOrgEntityConfigAttributesType(GetOrgEntityConfigAttributesType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Public Work List Views Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Public Work List Views Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetPublicWorkListViewsResponseType(GetPublicWorkListViewsResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Public Work List Views Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Public Work List Views Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetPublicWorkListViewsType(GetPublicWorkListViewsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Resource Order Filter Criteria Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Resource Order Filter Criteria Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetResourceOrderFilterCriteriaResponseType(GetResourceOrderFilterCriteriaResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Resource Order Filter Criteria Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Resource Order Filter Criteria Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetResourceOrderFilterCriteriaType(GetResourceOrderFilterCriteriaType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Views For Resource Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Views For Resource Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetViewsForResourceResponseType(GetViewsForResourceResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Views For Resource Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Views For Resource Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetViewsForResourceType(GetViewsForResourceType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Item Header Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Item Header Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkItemHeaderResponseType(GetWorkItemHeaderResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Item Header Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Item Header Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkItemHeaderType(GetWorkItemHeaderType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Item Order Filter Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Item Order Filter Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkItemOrderFilterResponseType(GetWorkItemOrderFilterResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Item Order Filter Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Item Order Filter Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkItemOrderFilterType(GetWorkItemOrderFilterType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work List Items For Global Data Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work List Items For Global Data Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkListItemsForGlobalDataResponseType(GetWorkListItemsForGlobalDataResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work List Items For Global Data Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work List Items For Global Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkListItemsForGlobalDataType(GetWorkListItemsForGlobalDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work List Items For View Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work List Items For View Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkListItemsForViewResponseType(GetWorkListItemsForViewResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work List Items For View Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work List Items For View Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkListItemsForViewType(GetWorkListItemsForViewType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work List Items Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work List Items Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkListItemsResponseType(GetWorkListItemsResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work List Items Response Type1</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work List Items Response Type1</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkListItemsResponseType1(GetWorkListItemsResponseType1 object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work List Items Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work List Items Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkListItemsType(GetWorkListItemsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work List View Details Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work List View Details Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkListViewDetailsType(GetWorkListViewDetailsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Model Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Model Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkModelResponseType(GetWorkModelResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Models Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Models Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkModelsResponseType(GetWorkModelsResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Models Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Models Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkModelsType(GetWorkModelsType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Model Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Model Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkModelType(GetWorkModelType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Type Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Type Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkTypeResponseType(GetWorkTypeResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Types Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Types Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkTypesResponseType(GetWorkTypesResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Types Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Types Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkTypesType(GetWorkTypesType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Get Work Type Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Get Work Type Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGetWorkTypeType(GetWorkTypeType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Hidden Period</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Hidden Period</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseHiddenPeriod(HiddenPeriod object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseItem(Item object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Body</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Body</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseItemBody(ItemBody object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Context</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Context</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseItemContext(ItemContext object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Duration</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Duration</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseItemDuration(ItemDuration object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Privilege</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Privilege</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseItemPrivilege(ItemPrivilege object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Schedule</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Schedule</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseItemSchedule(ItemSchedule object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Managed Object ID</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Managed Object ID</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseManagedObjectID(ManagedObjectID object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Next Work Item</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Next Work Item</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNextWorkItem(NextWorkItem object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Object ID</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Object ID</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseObjectID(ObjectID object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>On Notification Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>On Notification Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOnNotificationResponseType(OnNotificationResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>On Notification Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>On Notification Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOnNotificationType(OnNotificationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Open Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Open Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOpenWorkItemResponseType(OpenWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Open Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Open Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOpenWorkItemType(OpenWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Order Filter Criteria</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Order Filter Criteria</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrderFilterCriteria(OrderFilterCriteria object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Entity Config Attribute</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Entity Config Attribute</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgEntityConfigAttribute(OrgEntityConfigAttribute object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Entity Config Attributes Available</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Entity Config Attributes Available</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgEntityConfigAttributesAvailable(OrgEntityConfigAttributesAvailable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Org Entity Config Attribute Set</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Org Entity Config Attribute Set</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOrgEntityConfigAttributeSet(OrgEntityConfigAttributeSet object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameterType(ParameterType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pend Work Item</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pend Work Item</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePendWorkItem(PendWorkItem object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pend Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pend Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePendWorkItemResponseType(PendWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Preview Work Item From List Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Preview Work Item From List Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePreviewWorkItemFromListResponseType(PreviewWorkItemFromListResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Preview Work Item From List Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Preview Work Item From List Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePreviewWorkItemFromListType(PreviewWorkItemFromListType object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Push Notification Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Push Notification Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePushNotificationType(PushNotificationType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reallocate Work Item Data</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reallocate Work Item Data</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReallocateWorkItemData(ReallocateWorkItemData object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reallocate Work Item Data Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reallocate Work Item Data Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReallocateWorkItemDataResponseType(ReallocateWorkItemDataResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reallocate Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reallocate Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReallocateWorkItemResponseType(ReallocateWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reallocate Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reallocate Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReallocateWorkItemType(ReallocateWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reschedule Work Item</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reschedule Work Item</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRescheduleWorkItem(RescheduleWorkItem object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Reschedule Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Reschedule Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRescheduleWorkItemResponseType(RescheduleWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resume Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resume Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResumeWorkItemResponseType(ResumeWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Resume Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Resume Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResumeWorkItemType(ResumeWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Save Open Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Save Open Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSaveOpenWorkItemResponseType(SaveOpenWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Save Open Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Save Open Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSaveOpenWorkItemType(SaveOpenWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schedule Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schedule Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScheduleWorkItemResponseType(ScheduleWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schedule Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schedule Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScheduleWorkItemType(ScheduleWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schedule Work Item With Model Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schedule Work Item With Model Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScheduleWorkItemWithModelResponseType(ScheduleWorkItemWithModelResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schedule Work Item With Model Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schedule Work Item With Model Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScheduleWorkItemWithModelType(ScheduleWorkItemWithModelType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Org Entity Config Attributes Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Org Entity Config Attributes Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetOrgEntityConfigAttributesResponseType(SetOrgEntityConfigAttributesResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Org Entity Config Attributes Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Org Entity Config Attributes Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetOrgEntityConfigAttributesType(SetOrgEntityConfigAttributesType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Resource Order Filter Criteria Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Resource Order Filter Criteria Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetResourceOrderFilterCriteriaResponseType(SetResourceOrderFilterCriteriaResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Resource Order Filter Criteria Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Resource Order Filter Criteria Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetResourceOrderFilterCriteriaType(SetResourceOrderFilterCriteriaType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Work Item Priority</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Work Item Priority</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetWorkItemPriority(SetWorkItemPriority object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Work Item Priority Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Work Item Priority Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetWorkItemPriorityResponseType(SetWorkItemPriorityResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Skip Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Skip Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSkipWorkItemResponseType(SkipWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Skip Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Skip Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSkipWorkItemType(SkipWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Start Group Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Start Group Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStartGroupResponseType(StartGroupResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Start Group Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Start Group Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStartGroupType(StartGroupType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Suspend Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Suspend Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSuspendWorkItemResponseType(SuspendWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Suspend Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Suspend Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSuspendWorkItemType(SuspendWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unallocate Work Item Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unallocate Work Item Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnallocateWorkItemResponseType(UnallocateWorkItemResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unallocate Work Item Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unallocate Work Item Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnallocateWorkItemType(UnallocateWorkItemType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unlock Work List View Response Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unlock Work List View Response Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnlockWorkListViewResponseType(UnlockWorkListViewResponseType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unlock Work List View Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unlock Work List View Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnlockWorkListViewType(UnlockWorkListViewType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItem(WorkItem object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item Attributes</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item Attributes</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemAttributes(WorkItemAttributes object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item Body</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item Body</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemBody(WorkItemBody object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item Flags</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item Flags</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemFlags(WorkItemFlags object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item Header</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item Header</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemHeader(WorkItemHeader object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item IDand Priority Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item IDand Priority Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemIDandPriorityType(WorkItemIDandPriorityType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item Preview</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item Preview</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemPreview(WorkItemPreview object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Item Priority Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Item Priority Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkItemPriorityType(WorkItemPriorityType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work List View</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work List View</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkListView(WorkListView object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work List View Common</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work List View Common</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkListViewCommon(WorkListViewCommon object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work List View Edit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work List View Edit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkListViewEdit(WorkListViewEdit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work List View Page Item</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work List View Page Item</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkListViewPageItem(WorkListViewPageItem object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModel(WorkModel object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model Entities</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model Entities</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelEntities(WorkModelEntities object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model Entity</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelEntity(WorkModelEntity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model List</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model List</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelList(WorkModelList object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model Mapping</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model Mapping</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelMapping(WorkModelMapping object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model Script</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model Script</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelScript(WorkModelScript object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model Scripts</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model Scripts</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelScripts(WorkModelScripts object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model Specification</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model Specification</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelSpecification(WorkModelSpecification object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelType(WorkModelType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Model Types</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Model Types</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkModelTypes(WorkModelTypes object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Work Type List</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Work Type List</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkTypeList(WorkTypeList object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Data Model</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Data Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDataModel(DataModel object) {
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
    public T defaultCase(EObject object) {
        return null;
    }

} //N2BRMSwitch
