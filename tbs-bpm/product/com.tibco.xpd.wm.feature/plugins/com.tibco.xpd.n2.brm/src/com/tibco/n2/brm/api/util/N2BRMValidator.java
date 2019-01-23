/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.util;

import com.tibco.n2.brm.api.*;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage
 * @generated
 */
public class N2BRMValidator extends EObjectValidator {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final N2BRMValidator INSTANCE = new N2BRMValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "com.tibco.n2.brm.api";

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written constants.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

    /**
     * The cached base package validator.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected XMLTypeValidator xmlTypeValidator;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public N2BRMValidator() {
        super();
        xmlTypeValidator = XMLTypeValidator.INSTANCE;
    }

    /**
     * Returns the package of this validator switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
      return N2BRMPackage.eINSTANCE;
    }

    /**
     * Calls <code>validateXXX</code> for the corresponding classifier of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
        switch (classifierID) {
            case N2BRMPackage.ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE:
                return validateAddCurrentResourceToViewResponseType((AddCurrentResourceToViewResponseType)value, diagnostics, context);
            case N2BRMPackage.ADD_CURRENT_RESOURCE_TO_VIEW_TYPE:
                return validateAddCurrentResourceToViewType((AddCurrentResourceToViewType)value, diagnostics, context);
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE:
                return validateAllocateAndOpenNextWorkItemResponseType((AllocateAndOpenNextWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE:
                return validateAllocateAndOpenNextWorkItemType((AllocateAndOpenNextWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE:
                return validateAllocateAndOpenWorkItemResponseType((AllocateAndOpenWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ALLOCATE_AND_OPEN_WORK_ITEM_TYPE:
                return validateAllocateAndOpenWorkItemType((AllocateAndOpenWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ALLOCATE_WORK_ITEM_RESPONSE_TYPE:
                return validateAllocateWorkItemResponseType((AllocateWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ALLOCATE_WORK_ITEM_TYPE:
                return validateAllocateWorkItemType((AllocateWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ALLOCATION_HISTORY:
                return validateAllocationHistory((AllocationHistory)value, diagnostics, context);
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE:
                return validateAsyncCancelWorkItemResponseType((AsyncCancelWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_TYPE:
                return validateAsyncCancelWorkItemType((AsyncCancelWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_END_GROUP_RESPONSE_TYPE:
                return validateAsyncEndGroupResponseType((AsyncEndGroupResponseType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_END_GROUP_TYPE:
                return validateAsyncEndGroupType((AsyncEndGroupType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE:
                return validateAsyncRescheduleWorkItemResponseType((AsyncRescheduleWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_RESCHEDULE_WORK_ITEM_TYPE:
                return validateAsyncRescheduleWorkItemType((AsyncRescheduleWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE:
                return validateAsyncResumeWorkItemResponseType((AsyncResumeWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE:
                return validateAsyncResumeWorkItemType((AsyncResumeWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE:
                return validateAsyncScheduleWorkItemResponseType((AsyncScheduleWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE:
                return validateAsyncScheduleWorkItemType((AsyncScheduleWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE:
                return validateAsyncScheduleWorkItemWithModelResponseType((AsyncScheduleWorkItemWithModelResponseType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE:
                return validateAsyncScheduleWorkItemWithModelType((AsyncScheduleWorkItemWithModelType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_START_GROUP_RESPONSE_TYPE:
                return validateAsyncStartGroupResponseType((AsyncStartGroupResponseType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_START_GROUP_TYPE:
                return validateAsyncStartGroupType((AsyncStartGroupType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE:
                return validateAsyncSuspendWorkItemResponseType((AsyncSuspendWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_SUSPEND_WORK_ITEM_TYPE:
                return validateAsyncSuspendWorkItemType((AsyncSuspendWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS:
                return validateAsyncWorkItemDetails((AsyncWorkItemDetails)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE:
                return validateAttributeAliasListType((AttributeAliasListType)value, diagnostics, context);
            case N2BRMPackage.BASE_ITEM_INFO:
                return validateBaseItemInfo((BaseItemInfo)value, diagnostics, context);
            case N2BRMPackage.BASE_MODEL_INFO:
                return validateBaseModelInfo((BaseModelInfo)value, diagnostics, context);
            case N2BRMPackage.CANCEL_WORK_ITEM_RESPONSE_TYPE:
                return validateCancelWorkItemResponseType((CancelWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.CANCEL_WORK_ITEM_TYPE:
                return validateCancelWorkItemType((CancelWorkItemType)value, diagnostics, context);
            case N2BRMPackage.CHAINED_WORK_ITEM_NOTIFICATION_TYPE:
                return validateChainedWorkItemNotificationType((ChainedWorkItemNotificationType)value, diagnostics, context);
            case N2BRMPackage.CLOSE_WORK_ITEM_RESPONSE_TYPE:
                return validateCloseWorkItemResponseType((CloseWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.CLOSE_WORK_ITEM_TYPE:
                return validateCloseWorkItemType((CloseWorkItemType)value, diagnostics, context);
            case N2BRMPackage.COLUMN_DEFINITION:
                return validateColumnDefinition((ColumnDefinition)value, diagnostics, context);
            case N2BRMPackage.COLUMN_ORDER:
                return validateColumnOrder((ColumnOrder)value, diagnostics, context);
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE:
                return validateCompleteWorkItemResponseType((CompleteWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE:
                return validateCompleteWorkItemType((CompleteWorkItemType)value, diagnostics, context);
            case N2BRMPackage.CREATE_WORK_LIST_VIEW_RESPONSE_TYPE:
                return validateCreateWorkListViewResponseType((CreateWorkListViewResponseType)value, diagnostics, context);
            case N2BRMPackage.DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE:
                return validateDeleteCurrentResourceFromViewResponseType((DeleteCurrentResourceFromViewResponseType)value, diagnostics, context);
            case N2BRMPackage.DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE:
                return validateDeleteCurrentResourceFromViewType((DeleteCurrentResourceFromViewType)value, diagnostics, context);
            case N2BRMPackage.DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE:
                return validateDeleteOrgEntityConfigAttributesResponseType((DeleteOrgEntityConfigAttributesResponseType)value, diagnostics, context);
            case N2BRMPackage.DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE:
                return validateDeleteOrgEntityConfigAttributesType((DeleteOrgEntityConfigAttributesType)value, diagnostics, context);
            case N2BRMPackage.DELETE_WORK_LIST_VIEW_RESPONSE_TYPE:
                return validateDeleteWorkListViewResponseType((DeleteWorkListViewResponseType)value, diagnostics, context);
            case N2BRMPackage.DELETE_WORK_LIST_VIEW_TYPE:
                return validateDeleteWorkListViewType((DeleteWorkListViewType)value, diagnostics, context);
            case N2BRMPackage.DOCUMENT_ROOT:
                return validateDocumentRoot((DocumentRoot)value, diagnostics, context);
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE:
                return validateEditWorkListViewResponseType((EditWorkListViewResponseType)value, diagnostics, context);
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_TYPE:
                return validateEditWorkListViewType((EditWorkListViewType)value, diagnostics, context);
            case N2BRMPackage.ENABLE_WORK_ITEM_RESPONSE_TYPE:
                return validateEnableWorkItemResponseType((EnableWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.ENABLE_WORK_ITEM_TYPE:
                return validateEnableWorkItemType((EnableWorkItemType)value, diagnostics, context);
            case N2BRMPackage.END_GROUP_RESPONSE_TYPE:
                return validateEndGroupResponseType((EndGroupResponseType)value, diagnostics, context);
            case N2BRMPackage.END_GROUP_TYPE:
                return validateEndGroupType((EndGroupType)value, diagnostics, context);
            case N2BRMPackage.GET_ALLOCATED_WORK_LIST_ITEMS_TYPE:
                return validateGetAllocatedWorkListItemsType((GetAllocatedWorkListItemsType)value, diagnostics, context);
            case N2BRMPackage.GET_BATCH_GROUP_IDS_RESPONSE_TYPE:
                return validateGetBatchGroupIdsResponseType((GetBatchGroupIdsResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE:
                return validateGetBatchWorkItemIdsResponseType((GetBatchWorkItemIdsResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE:
                return validateGetEditableWorkListViewsResponseType((GetEditableWorkListViewsResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_TYPE:
                return validateGetEditableWorkListViewsType((GetEditableWorkListViewsType)value, diagnostics, context);
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE:
                return validateGetOfferSetResponseType((GetOfferSetResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_OFFER_SET_TYPE:
                return validateGetOfferSetType((GetOfferSetType)value, diagnostics, context);
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE:
                return validateGetOrgEntityConfigAttributesAvailableResponseType((GetOrgEntityConfigAttributesAvailableResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE:
                return validateGetOrgEntityConfigAttributesAvailableType((GetOrgEntityConfigAttributesAvailableType)value, diagnostics, context);
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE:
                return validateGetOrgEntityConfigAttributesResponseType((GetOrgEntityConfigAttributesResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE:
                return validateGetOrgEntityConfigAttributesType((GetOrgEntityConfigAttributesType)value, diagnostics, context);
            case N2BRMPackage.GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE:
                return validateGetPublicWorkListViewsResponseType((GetPublicWorkListViewsResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_PUBLIC_WORK_LIST_VIEWS_TYPE:
                return validateGetPublicWorkListViewsType((GetPublicWorkListViewsType)value, diagnostics, context);
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE:
                return validateGetResourceOrderFilterCriteriaResponseType((GetResourceOrderFilterCriteriaResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE:
                return validateGetResourceOrderFilterCriteriaType((GetResourceOrderFilterCriteriaType)value, diagnostics, context);
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE:
                return validateGetViewsForResourceResponseType((GetViewsForResourceResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE:
                return validateGetViewsForResourceType((GetViewsForResourceType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_ITEM_HEADER_RESPONSE_TYPE:
                return validateGetWorkItemHeaderResponseType((GetWorkItemHeaderResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_ITEM_HEADER_TYPE:
                return validateGetWorkItemHeaderType((GetWorkItemHeaderType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE:
                return validateGetWorkItemOrderFilterResponseType((GetWorkItemOrderFilterResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE:
                return validateGetWorkItemOrderFilterType((GetWorkItemOrderFilterType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE:
                return validateGetWorkListItemsForGlobalDataResponseType((GetWorkListItemsForGlobalDataResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE:
                return validateGetWorkListItemsForGlobalDataType((GetWorkListItemsForGlobalDataType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE:
                return validateGetWorkListItemsForViewResponseType((GetWorkListItemsForViewResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE:
                return validateGetWorkListItemsForViewType((GetWorkListItemsForViewType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_LIST_ITEMS_RESPONSE_TYPE:
                return validateGetWorkListItemsResponseType((GetWorkListItemsResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_LIST_ITEMS_RESPONSE_TYPE1:
                return validateGetWorkListItemsResponseType1((GetWorkListItemsResponseType1)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_LIST_ITEMS_TYPE:
                return validateGetWorkListItemsType((GetWorkListItemsType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE:
                return validateGetWorkListViewDetailsType((GetWorkListViewDetailsType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE:
                return validateGetWorkModelResponseType((GetWorkModelResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE:
                return validateGetWorkModelsResponseType((GetWorkModelsResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_MODELS_TYPE:
                return validateGetWorkModelsType((GetWorkModelsType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_MODEL_TYPE:
                return validateGetWorkModelType((GetWorkModelType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE:
                return validateGetWorkTypeResponseType((GetWorkTypeResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE:
                return validateGetWorkTypesResponseType((GetWorkTypesResponseType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_TYPES_TYPE:
                return validateGetWorkTypesType((GetWorkTypesType)value, diagnostics, context);
            case N2BRMPackage.GET_WORK_TYPE_TYPE:
                return validateGetWorkTypeType((GetWorkTypeType)value, diagnostics, context);
            case N2BRMPackage.HIDDEN_PERIOD:
                return validateHiddenPeriod((HiddenPeriod)value, diagnostics, context);
            case N2BRMPackage.ITEM:
                return validateItem((Item)value, diagnostics, context);
            case N2BRMPackage.ITEM_BODY:
                return validateItemBody((ItemBody)value, diagnostics, context);
            case N2BRMPackage.ITEM_CONTEXT:
                return validateItemContext((ItemContext)value, diagnostics, context);
            case N2BRMPackage.ITEM_DURATION:
                return validateItemDuration((ItemDuration)value, diagnostics, context);
            case N2BRMPackage.ITEM_PRIVILEGE:
                return validateItemPrivilege((ItemPrivilege)value, diagnostics, context);
            case N2BRMPackage.ITEM_SCHEDULE:
                return validateItemSchedule((ItemSchedule)value, diagnostics, context);
            case N2BRMPackage.MANAGED_OBJECT_ID:
                return validateManagedObjectID((ManagedObjectID)value, diagnostics, context);
            case N2BRMPackage.NEXT_WORK_ITEM:
                return validateNextWorkItem((NextWorkItem)value, diagnostics, context);
            case N2BRMPackage.OBJECT_ID:
                return validateObjectID((ObjectID)value, diagnostics, context);
            case N2BRMPackage.ON_NOTIFICATION_RESPONSE_TYPE:
                return validateOnNotificationResponseType((OnNotificationResponseType)value, diagnostics, context);
            case N2BRMPackage.ON_NOTIFICATION_TYPE:
                return validateOnNotificationType((OnNotificationType)value, diagnostics, context);
            case N2BRMPackage.OPEN_WORK_ITEM_RESPONSE_TYPE:
                return validateOpenWorkItemResponseType((OpenWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.OPEN_WORK_ITEM_TYPE:
                return validateOpenWorkItemType((OpenWorkItemType)value, diagnostics, context);
            case N2BRMPackage.ORDER_FILTER_CRITERIA:
                return validateOrderFilterCriteria((OrderFilterCriteria)value, diagnostics, context);
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTE:
                return validateOrgEntityConfigAttribute((OrgEntityConfigAttribute)value, diagnostics, context);
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE:
                return validateOrgEntityConfigAttributesAvailable((OrgEntityConfigAttributesAvailable)value, diagnostics, context);
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTE_SET:
                return validateOrgEntityConfigAttributeSet((OrgEntityConfigAttributeSet)value, diagnostics, context);
            case N2BRMPackage.PARAMETER_TYPE:
                return validateParameterType((ParameterType)value, diagnostics, context);
            case N2BRMPackage.PEND_WORK_ITEM:
                return validatePendWorkItem((PendWorkItem)value, diagnostics, context);
            case N2BRMPackage.PEND_WORK_ITEM_RESPONSE_TYPE:
                return validatePendWorkItemResponseType((PendWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE:
                return validatePreviewWorkItemFromListResponseType((PreviewWorkItemFromListResponseType)value, diagnostics, context);
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE:
                return validatePreviewWorkItemFromListType((PreviewWorkItemFromListType)value, diagnostics, context);
            case N2BRMPackage.PRIVILEGE:
                return validatePrivilege((Privilege)value, diagnostics, context);
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE:
                return validatePushNotificationType((PushNotificationType)value, diagnostics, context);
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA:
                return validateReallocateWorkItemData((ReallocateWorkItemData)value, diagnostics, context);
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE:
                return validateReallocateWorkItemDataResponseType((ReallocateWorkItemDataResponseType)value, diagnostics, context);
            case N2BRMPackage.REALLOCATE_WORK_ITEM_RESPONSE_TYPE:
                return validateReallocateWorkItemResponseType((ReallocateWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.REALLOCATE_WORK_ITEM_TYPE:
                return validateReallocateWorkItemType((ReallocateWorkItemType)value, diagnostics, context);
            case N2BRMPackage.RESCHEDULE_WORK_ITEM:
                return validateRescheduleWorkItem((RescheduleWorkItem)value, diagnostics, context);
            case N2BRMPackage.RESCHEDULE_WORK_ITEM_RESPONSE_TYPE:
                return validateRescheduleWorkItemResponseType((RescheduleWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.RESUME_WORK_ITEM_RESPONSE_TYPE:
                return validateResumeWorkItemResponseType((ResumeWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.RESUME_WORK_ITEM_TYPE:
                return validateResumeWorkItemType((ResumeWorkItemType)value, diagnostics, context);
            case N2BRMPackage.SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE:
                return validateSaveOpenWorkItemResponseType((SaveOpenWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.SAVE_OPEN_WORK_ITEM_TYPE:
                return validateSaveOpenWorkItemType((SaveOpenWorkItemType)value, diagnostics, context);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_RESPONSE_TYPE:
                return validateScheduleWorkItemResponseType((ScheduleWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE:
                return validateScheduleWorkItemType((ScheduleWorkItemType)value, diagnostics, context);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE:
                return validateScheduleWorkItemWithModelResponseType((ScheduleWorkItemWithModelResponseType)value, diagnostics, context);
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE:
                return validateScheduleWorkItemWithModelType((ScheduleWorkItemWithModelType)value, diagnostics, context);
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE:
                return validateSetOrgEntityConfigAttributesResponseType((SetOrgEntityConfigAttributesResponseType)value, diagnostics, context);
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE:
                return validateSetOrgEntityConfigAttributesType((SetOrgEntityConfigAttributesType)value, diagnostics, context);
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE:
                return validateSetResourceOrderFilterCriteriaResponseType((SetResourceOrderFilterCriteriaResponseType)value, diagnostics, context);
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE:
                return validateSetResourceOrderFilterCriteriaType((SetResourceOrderFilterCriteriaType)value, diagnostics, context);
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY:
                return validateSetWorkItemPriority((SetWorkItemPriority)value, diagnostics, context);
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE:
                return validateSetWorkItemPriorityResponseType((SetWorkItemPriorityResponseType)value, diagnostics, context);
            case N2BRMPackage.SKIP_WORK_ITEM_RESPONSE_TYPE:
                return validateSkipWorkItemResponseType((SkipWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.SKIP_WORK_ITEM_TYPE:
                return validateSkipWorkItemType((SkipWorkItemType)value, diagnostics, context);
            case N2BRMPackage.START_GROUP_RESPONSE_TYPE:
                return validateStartGroupResponseType((StartGroupResponseType)value, diagnostics, context);
            case N2BRMPackage.START_GROUP_TYPE:
                return validateStartGroupType((StartGroupType)value, diagnostics, context);
            case N2BRMPackage.SUSPEND_WORK_ITEM_RESPONSE_TYPE:
                return validateSuspendWorkItemResponseType((SuspendWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE:
                return validateSuspendWorkItemType((SuspendWorkItemType)value, diagnostics, context);
            case N2BRMPackage.UNALLOCATE_WORK_ITEM_RESPONSE_TYPE:
                return validateUnallocateWorkItemResponseType((UnallocateWorkItemResponseType)value, diagnostics, context);
            case N2BRMPackage.UNALLOCATE_WORK_ITEM_TYPE:
                return validateUnallocateWorkItemType((UnallocateWorkItemType)value, diagnostics, context);
            case N2BRMPackage.UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE:
                return validateUnlockWorkListViewResponseType((UnlockWorkListViewResponseType)value, diagnostics, context);
            case N2BRMPackage.UNLOCK_WORK_LIST_VIEW_TYPE:
                return validateUnlockWorkListViewType((UnlockWorkListViewType)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM:
                return validateWorkItem((WorkItem)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES:
                return validateWorkItemAttributes((WorkItemAttributes)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_BODY:
                return validateWorkItemBody((WorkItemBody)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_FLAGS:
                return validateWorkItemFlags((WorkItemFlags)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_HEADER:
                return validateWorkItemHeader((WorkItemHeader)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE:
                return validateWorkItemIDandPriorityType((WorkItemIDandPriorityType)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_PREVIEW:
                return validateWorkItemPreview((WorkItemPreview)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE:
                return validateWorkItemPriorityType((WorkItemPriorityType)value, diagnostics, context);
            case N2BRMPackage.WORK_LIST_VIEW:
                return validateWorkListView((WorkListView)value, diagnostics, context);
            case N2BRMPackage.WORK_LIST_VIEW_COMMON:
                return validateWorkListViewCommon((WorkListViewCommon)value, diagnostics, context);
            case N2BRMPackage.WORK_LIST_VIEW_EDIT:
                return validateWorkListViewEdit((WorkListViewEdit)value, diagnostics, context);
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM:
                return validateWorkListViewPageItem((WorkListViewPageItem)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL:
                return validateWorkModel((WorkModel)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_ENTITIES:
                return validateWorkModelEntities((WorkModelEntities)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_ENTITY:
                return validateWorkModelEntity((WorkModelEntity)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_LIST:
                return validateWorkModelList((WorkModelList)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_MAPPING:
                return validateWorkModelMapping((WorkModelMapping)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_SCRIPT:
                return validateWorkModelScript((WorkModelScript)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_SCRIPTS:
                return validateWorkModelScripts((WorkModelScripts)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_SPECIFICATION:
                return validateWorkModelSpecification((WorkModelSpecification)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_TYPE:
                return validateWorkModelType((WorkModelType)value, diagnostics, context);
            case N2BRMPackage.WORK_MODEL_TYPES:
                return validateWorkModelTypes((WorkModelTypes)value, diagnostics, context);
            case N2BRMPackage.WORK_TYPE_LIST:
                return validateWorkTypeList((WorkTypeList)value, diagnostics, context);
            case N2BRMPackage.COLUMN_CAPABILITY:
                return validateColumnCapability((ColumnCapability)value, diagnostics, context);
            case N2BRMPackage.COLUMN_TYPE:
                return validateColumnType((ColumnType)value, diagnostics, context);
            case N2BRMPackage.DISTRIBUTION_STRATEGY:
                return validateDistributionStrategy((DistributionStrategy)value, diagnostics, context);
            case N2BRMPackage.METHOD_AUTHORISATION_ACTION:
                return validateMethodAuthorisationAction((MethodAuthorisationAction)value, diagnostics, context);
            case N2BRMPackage.METHOD_AUTHORISATION_COMPONENT:
                return validateMethodAuthorisationComponent((MethodAuthorisationComponent)value, diagnostics, context);
            case N2BRMPackage.RESOURCES_REQUIRED_TYPE:
                return validateResourcesRequiredType((ResourcesRequiredType)value, diagnostics, context);
            case N2BRMPackage.SCHEDULE_STATUS:
                return validateScheduleStatus((ScheduleStatus)value, diagnostics, context);
            case N2BRMPackage.WORK_GROUP_TYPE:
                return validateWorkGroupType((WorkGroupType)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_SCRIPT_OPERATION:
                return validateWorkItemScriptOperation((WorkItemScriptOperation)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_SCRIPT_TYPE:
                return validateWorkItemScriptType((WorkItemScriptType)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_STATE:
                return validateWorkItemState((WorkItemState)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE10_TYPE:
                return validateAttribute10Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE11_TYPE:
                return validateAttribute11Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE12_TYPE:
                return validateAttribute12Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE13_TYPE:
                return validateAttribute13Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE14_TYPE:
                return validateAttribute14Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE21_TYPE:
                return validateAttribute21Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE22_TYPE:
                return validateAttribute22Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE23_TYPE:
                return validateAttribute23Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE24_TYPE:
                return validateAttribute24Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE25_TYPE:
                return validateAttribute25Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE26_TYPE:
                return validateAttribute26Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE27_TYPE:
                return validateAttribute27Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE28_TYPE:
                return validateAttribute28Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE29_TYPE:
                return validateAttribute29Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE2_TYPE:
                return validateAttribute2Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE30_TYPE:
                return validateAttribute30Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE31_TYPE:
                return validateAttribute31Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE32_TYPE:
                return validateAttribute32Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE33_TYPE:
                return validateAttribute33Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE34_TYPE:
                return validateAttribute34Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE35_TYPE:
                return validateAttribute35Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE36_TYPE:
                return validateAttribute36Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE37_TYPE:
                return validateAttribute37Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE38_TYPE:
                return validateAttribute38Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE39_TYPE:
                return validateAttribute39Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE3_TYPE:
                return validateAttribute3Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE40_TYPE:
                return validateAttribute40Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE4_TYPE:
                return validateAttribute4Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE8_TYPE:
                return validateAttribute8Type((String)value, diagnostics, context);
            case N2BRMPackage.ATTRIBUTE9_TYPE:
                return validateAttribute9Type((String)value, diagnostics, context);
            case N2BRMPackage.COLUMN_CAPABILITY_OBJECT:
                return validateColumnCapabilityObject((ColumnCapability)value, diagnostics, context);
            case N2BRMPackage.COLUMN_TYPE_OBJECT:
                return validateColumnTypeObject((ColumnType)value, diagnostics, context);
            case N2BRMPackage.DESCRIPTION_TYPE:
                return validateDescriptionType((String)value, diagnostics, context);
            case N2BRMPackage.DISTRIBUTION_STRATEGY_OBJECT:
                return validateDistributionStrategyObject((DistributionStrategy)value, diagnostics, context);
            case N2BRMPackage.LOCKER_TYPE:
                return validateLockerType((String)value, diagnostics, context);
            case N2BRMPackage.METHOD_AUTHORISATION_ACTION_OBJECT:
                return validateMethodAuthorisationActionObject((MethodAuthorisationAction)value, diagnostics, context);
            case N2BRMPackage.METHOD_AUTHORISATION_COMPONENT_OBJECT:
                return validateMethodAuthorisationComponentObject((MethodAuthorisationComponent)value, diagnostics, context);
            case N2BRMPackage.NAME_TYPE:
                return validateNameType((String)value, diagnostics, context);
            case N2BRMPackage.OWNER_TYPE:
                return validateOwnerType((String)value, diagnostics, context);
            case N2BRMPackage.RESOURCES_REQUIRED_TYPE_OBJECT:
                return validateResourcesRequiredTypeObject((ResourcesRequiredType)value, diagnostics, context);
            case N2BRMPackage.SCHEDULE_STATUS_OBJECT:
                return validateScheduleStatusObject((ScheduleStatus)value, diagnostics, context);
            case N2BRMPackage.SCRIPT_OPERATION_TYPE:
                return validateScriptOperationType((WorkItemScriptOperation)value, diagnostics, context);
            case N2BRMPackage.WORK_GROUP_TYPE_OBJECT:
                return validateWorkGroupTypeObject((WorkGroupType)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_SCRIPT_OPERATION_OBJECT:
                return validateWorkItemScriptOperationObject((WorkItemScriptOperation)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_SCRIPT_TYPE_OBJECT:
                return validateWorkItemScriptTypeObject((WorkItemScriptType)value, diagnostics, context);
            case N2BRMPackage.WORK_ITEM_STATE_OBJECT:
                return validateWorkItemStateObject((WorkItemState)value, diagnostics, context);
            default:
                return true;
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAddCurrentResourceToViewResponseType(AddCurrentResourceToViewResponseType addCurrentResourceToViewResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(addCurrentResourceToViewResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAddCurrentResourceToViewType(AddCurrentResourceToViewType addCurrentResourceToViewType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(addCurrentResourceToViewType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAllocateAndOpenNextWorkItemResponseType(AllocateAndOpenNextWorkItemResponseType allocateAndOpenNextWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(allocateAndOpenNextWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAllocateAndOpenNextWorkItemType(AllocateAndOpenNextWorkItemType allocateAndOpenNextWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(allocateAndOpenNextWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAllocateAndOpenWorkItemResponseType(AllocateAndOpenWorkItemResponseType allocateAndOpenWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(allocateAndOpenWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAllocateAndOpenWorkItemType(AllocateAndOpenWorkItemType allocateAndOpenWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(allocateAndOpenWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAllocateWorkItemResponseType(AllocateWorkItemResponseType allocateWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(allocateWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAllocateWorkItemType(AllocateWorkItemType allocateWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(allocateWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAllocationHistory(AllocationHistory allocationHistory, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(allocationHistory, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncCancelWorkItemResponseType(AsyncCancelWorkItemResponseType asyncCancelWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncCancelWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncCancelWorkItemType(AsyncCancelWorkItemType asyncCancelWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncCancelWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncEndGroupResponseType(AsyncEndGroupResponseType asyncEndGroupResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncEndGroupResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncEndGroupType(AsyncEndGroupType asyncEndGroupType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncEndGroupType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncRescheduleWorkItemResponseType(AsyncRescheduleWorkItemResponseType asyncRescheduleWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncRescheduleWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncRescheduleWorkItemType(AsyncRescheduleWorkItemType asyncRescheduleWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncRescheduleWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncResumeWorkItemResponseType(AsyncResumeWorkItemResponseType asyncResumeWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncResumeWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncResumeWorkItemType(AsyncResumeWorkItemType asyncResumeWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncResumeWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncScheduleWorkItemResponseType(AsyncScheduleWorkItemResponseType asyncScheduleWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncScheduleWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncScheduleWorkItemType(AsyncScheduleWorkItemType asyncScheduleWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncScheduleWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncScheduleWorkItemWithModelResponseType(AsyncScheduleWorkItemWithModelResponseType asyncScheduleWorkItemWithModelResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncScheduleWorkItemWithModelResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncScheduleWorkItemWithModelType(AsyncScheduleWorkItemWithModelType asyncScheduleWorkItemWithModelType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncScheduleWorkItemWithModelType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncStartGroupResponseType(AsyncStartGroupResponseType asyncStartGroupResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncStartGroupResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncStartGroupType(AsyncStartGroupType asyncStartGroupType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncStartGroupType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncSuspendWorkItemResponseType(AsyncSuspendWorkItemResponseType asyncSuspendWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncSuspendWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncSuspendWorkItemType(AsyncSuspendWorkItemType asyncSuspendWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncSuspendWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAsyncWorkItemDetails(AsyncWorkItemDetails asyncWorkItemDetails, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(asyncWorkItemDetails, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttributeAliasListType(AttributeAliasListType attributeAliasListType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(attributeAliasListType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateBaseItemInfo(BaseItemInfo baseItemInfo, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(baseItemInfo, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateBaseModelInfo(BaseModelInfo baseModelInfo, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(baseModelInfo, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateCancelWorkItemResponseType(CancelWorkItemResponseType cancelWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(cancelWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateCancelWorkItemType(CancelWorkItemType cancelWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(cancelWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateChainedWorkItemNotificationType(ChainedWorkItemNotificationType chainedWorkItemNotificationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(chainedWorkItemNotificationType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateCloseWorkItemResponseType(CloseWorkItemResponseType closeWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(closeWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateCloseWorkItemType(CloseWorkItemType closeWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(closeWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateColumnDefinition(ColumnDefinition columnDefinition, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(columnDefinition, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateColumnOrder(ColumnOrder columnOrder, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(columnOrder, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateCompleteWorkItemResponseType(CompleteWorkItemResponseType completeWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(completeWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateCompleteWorkItemType(CompleteWorkItemType completeWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(completeWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateCreateWorkListViewResponseType(CreateWorkListViewResponseType createWorkListViewResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(createWorkListViewResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDeleteCurrentResourceFromViewResponseType(DeleteCurrentResourceFromViewResponseType deleteCurrentResourceFromViewResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(deleteCurrentResourceFromViewResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDeleteCurrentResourceFromViewType(DeleteCurrentResourceFromViewType deleteCurrentResourceFromViewType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(deleteCurrentResourceFromViewType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDeleteOrgEntityConfigAttributesResponseType(DeleteOrgEntityConfigAttributesResponseType deleteOrgEntityConfigAttributesResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(deleteOrgEntityConfigAttributesResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDeleteOrgEntityConfigAttributesType(DeleteOrgEntityConfigAttributesType deleteOrgEntityConfigAttributesType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(deleteOrgEntityConfigAttributesType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDeleteWorkListViewResponseType(DeleteWorkListViewResponseType deleteWorkListViewResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(deleteWorkListViewResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDeleteWorkListViewType(DeleteWorkListViewType deleteWorkListViewType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(deleteWorkListViewType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDocumentRoot(DocumentRoot documentRoot, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(documentRoot, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateEditWorkListViewResponseType(EditWorkListViewResponseType editWorkListViewResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(editWorkListViewResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateEditWorkListViewType(EditWorkListViewType editWorkListViewType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(editWorkListViewType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateEnableWorkItemResponseType(EnableWorkItemResponseType enableWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(enableWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateEnableWorkItemType(EnableWorkItemType enableWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(enableWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateEndGroupResponseType(EndGroupResponseType endGroupResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(endGroupResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateEndGroupType(EndGroupType endGroupType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(endGroupType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetAllocatedWorkListItemsType(GetAllocatedWorkListItemsType getAllocatedWorkListItemsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getAllocatedWorkListItemsType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetBatchGroupIdsResponseType(GetBatchGroupIdsResponseType getBatchGroupIdsResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getBatchGroupIdsResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetBatchWorkItemIdsResponseType(GetBatchWorkItemIdsResponseType getBatchWorkItemIdsResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getBatchWorkItemIdsResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetEditableWorkListViewsResponseType(GetEditableWorkListViewsResponseType getEditableWorkListViewsResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getEditableWorkListViewsResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetEditableWorkListViewsType(GetEditableWorkListViewsType getEditableWorkListViewsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getEditableWorkListViewsType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetOfferSetResponseType(GetOfferSetResponseType getOfferSetResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getOfferSetResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetOfferSetType(GetOfferSetType getOfferSetType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getOfferSetType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetOrgEntityConfigAttributesAvailableResponseType(GetOrgEntityConfigAttributesAvailableResponseType getOrgEntityConfigAttributesAvailableResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getOrgEntityConfigAttributesAvailableResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetOrgEntityConfigAttributesAvailableType(GetOrgEntityConfigAttributesAvailableType getOrgEntityConfigAttributesAvailableType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getOrgEntityConfigAttributesAvailableType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetOrgEntityConfigAttributesResponseType(GetOrgEntityConfigAttributesResponseType getOrgEntityConfigAttributesResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getOrgEntityConfigAttributesResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetOrgEntityConfigAttributesType(GetOrgEntityConfigAttributesType getOrgEntityConfigAttributesType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getOrgEntityConfigAttributesType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetPublicWorkListViewsResponseType(GetPublicWorkListViewsResponseType getPublicWorkListViewsResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getPublicWorkListViewsResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetPublicWorkListViewsType(GetPublicWorkListViewsType getPublicWorkListViewsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getPublicWorkListViewsType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetResourceOrderFilterCriteriaResponseType(GetResourceOrderFilterCriteriaResponseType getResourceOrderFilterCriteriaResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getResourceOrderFilterCriteriaResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetResourceOrderFilterCriteriaType(GetResourceOrderFilterCriteriaType getResourceOrderFilterCriteriaType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getResourceOrderFilterCriteriaType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetViewsForResourceResponseType(GetViewsForResourceResponseType getViewsForResourceResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getViewsForResourceResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetViewsForResourceType(GetViewsForResourceType getViewsForResourceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getViewsForResourceType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkItemHeaderResponseType(GetWorkItemHeaderResponseType getWorkItemHeaderResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkItemHeaderResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkItemHeaderType(GetWorkItemHeaderType getWorkItemHeaderType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkItemHeaderType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkItemOrderFilterResponseType(GetWorkItemOrderFilterResponseType getWorkItemOrderFilterResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkItemOrderFilterResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkItemOrderFilterType(GetWorkItemOrderFilterType getWorkItemOrderFilterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkItemOrderFilterType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkListItemsForGlobalDataResponseType(GetWorkListItemsForGlobalDataResponseType getWorkListItemsForGlobalDataResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkListItemsForGlobalDataResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkListItemsForGlobalDataType(GetWorkListItemsForGlobalDataType getWorkListItemsForGlobalDataType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkListItemsForGlobalDataType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkListItemsForViewResponseType(GetWorkListItemsForViewResponseType getWorkListItemsForViewResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkListItemsForViewResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkListItemsForViewType(GetWorkListItemsForViewType getWorkListItemsForViewType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkListItemsForViewType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkListItemsResponseType(GetWorkListItemsResponseType getWorkListItemsResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkListItemsResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkListItemsResponseType1(GetWorkListItemsResponseType1 getWorkListItemsResponseType1, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkListItemsResponseType1, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkListItemsType(GetWorkListItemsType getWorkListItemsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkListItemsType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkListViewDetailsType(GetWorkListViewDetailsType getWorkListViewDetailsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkListViewDetailsType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkModelResponseType(GetWorkModelResponseType getWorkModelResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkModelResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkModelsResponseType(GetWorkModelsResponseType getWorkModelsResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkModelsResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkModelsType(GetWorkModelsType getWorkModelsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkModelsType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkModelType(GetWorkModelType getWorkModelType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkModelType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkTypeResponseType(GetWorkTypeResponseType getWorkTypeResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkTypeResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkTypesResponseType(GetWorkTypesResponseType getWorkTypesResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkTypesResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkTypesType(GetWorkTypesType getWorkTypesType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkTypesType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateGetWorkTypeType(GetWorkTypeType getWorkTypeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(getWorkTypeType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateHiddenPeriod(HiddenPeriod hiddenPeriod, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(hiddenPeriod, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateItem(Item item, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(item, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateItemBody(ItemBody itemBody, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(itemBody, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateItemContext(ItemContext itemContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(itemContext, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateItemDuration(ItemDuration itemDuration, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(itemDuration, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateItemPrivilege(ItemPrivilege itemPrivilege, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(itemPrivilege, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateItemSchedule(ItemSchedule itemSchedule, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(itemSchedule, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateManagedObjectID(ManagedObjectID managedObjectID, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(managedObjectID, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateNextWorkItem(NextWorkItem nextWorkItem, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(nextWorkItem, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateObjectID(ObjectID objectID, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(objectID, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOnNotificationResponseType(OnNotificationResponseType onNotificationResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(onNotificationResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOnNotificationType(OnNotificationType onNotificationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(onNotificationType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOpenWorkItemResponseType(OpenWorkItemResponseType openWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(openWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOpenWorkItemType(OpenWorkItemType openWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(openWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOrderFilterCriteria(OrderFilterCriteria orderFilterCriteria, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(orderFilterCriteria, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOrgEntityConfigAttribute(OrgEntityConfigAttribute orgEntityConfigAttribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(orgEntityConfigAttribute, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOrgEntityConfigAttributesAvailable(OrgEntityConfigAttributesAvailable orgEntityConfigAttributesAvailable, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(orgEntityConfigAttributesAvailable, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOrgEntityConfigAttributeSet(OrgEntityConfigAttributeSet orgEntityConfigAttributeSet, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(orgEntityConfigAttributeSet, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateParameterType(ParameterType parameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(parameterType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validatePendWorkItem(PendWorkItem pendWorkItem, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(pendWorkItem, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validatePendWorkItemResponseType(PendWorkItemResponseType pendWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(pendWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validatePreviewWorkItemFromListResponseType(PreviewWorkItemFromListResponseType previewWorkItemFromListResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(previewWorkItemFromListResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validatePreviewWorkItemFromListType(PreviewWorkItemFromListType previewWorkItemFromListType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(previewWorkItemFromListType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validatePrivilege(Privilege privilege, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(privilege, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validatePushNotificationType(PushNotificationType pushNotificationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(pushNotificationType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateReallocateWorkItemData(ReallocateWorkItemData reallocateWorkItemData, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(reallocateWorkItemData, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateReallocateWorkItemDataResponseType(ReallocateWorkItemDataResponseType reallocateWorkItemDataResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(reallocateWorkItemDataResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateReallocateWorkItemResponseType(ReallocateWorkItemResponseType reallocateWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(reallocateWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateReallocateWorkItemType(ReallocateWorkItemType reallocateWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(reallocateWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateRescheduleWorkItem(RescheduleWorkItem rescheduleWorkItem, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(rescheduleWorkItem, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateRescheduleWorkItemResponseType(RescheduleWorkItemResponseType rescheduleWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(rescheduleWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateResumeWorkItemResponseType(ResumeWorkItemResponseType resumeWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(resumeWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateResumeWorkItemType(ResumeWorkItemType resumeWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(resumeWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSaveOpenWorkItemResponseType(SaveOpenWorkItemResponseType saveOpenWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(saveOpenWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSaveOpenWorkItemType(SaveOpenWorkItemType saveOpenWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(saveOpenWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateScheduleWorkItemResponseType(ScheduleWorkItemResponseType scheduleWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(scheduleWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateScheduleWorkItemType(ScheduleWorkItemType scheduleWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(scheduleWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateScheduleWorkItemWithModelResponseType(ScheduleWorkItemWithModelResponseType scheduleWorkItemWithModelResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(scheduleWorkItemWithModelResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateScheduleWorkItemWithModelType(ScheduleWorkItemWithModelType scheduleWorkItemWithModelType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(scheduleWorkItemWithModelType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSetOrgEntityConfigAttributesResponseType(SetOrgEntityConfigAttributesResponseType setOrgEntityConfigAttributesResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(setOrgEntityConfigAttributesResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSetOrgEntityConfigAttributesType(SetOrgEntityConfigAttributesType setOrgEntityConfigAttributesType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(setOrgEntityConfigAttributesType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSetResourceOrderFilterCriteriaResponseType(SetResourceOrderFilterCriteriaResponseType setResourceOrderFilterCriteriaResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(setResourceOrderFilterCriteriaResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSetResourceOrderFilterCriteriaType(SetResourceOrderFilterCriteriaType setResourceOrderFilterCriteriaType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(setResourceOrderFilterCriteriaType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSetWorkItemPriority(SetWorkItemPriority setWorkItemPriority, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(setWorkItemPriority, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSetWorkItemPriorityResponseType(SetWorkItemPriorityResponseType setWorkItemPriorityResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(setWorkItemPriorityResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSkipWorkItemResponseType(SkipWorkItemResponseType skipWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(skipWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSkipWorkItemType(SkipWorkItemType skipWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(skipWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateStartGroupResponseType(StartGroupResponseType startGroupResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(startGroupResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateStartGroupType(StartGroupType startGroupType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(startGroupType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSuspendWorkItemResponseType(SuspendWorkItemResponseType suspendWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(suspendWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSuspendWorkItemType(SuspendWorkItemType suspendWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(suspendWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateUnallocateWorkItemResponseType(UnallocateWorkItemResponseType unallocateWorkItemResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(unallocateWorkItemResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateUnallocateWorkItemType(UnallocateWorkItemType unallocateWorkItemType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(unallocateWorkItemType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateUnlockWorkListViewResponseType(UnlockWorkListViewResponseType unlockWorkListViewResponseType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(unlockWorkListViewResponseType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateUnlockWorkListViewType(UnlockWorkListViewType unlockWorkListViewType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(unlockWorkListViewType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItem(WorkItem workItem, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workItem, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemAttributes(WorkItemAttributes workItemAttributes, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workItemAttributes, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemBody(WorkItemBody workItemBody, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workItemBody, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemFlags(WorkItemFlags workItemFlags, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workItemFlags, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemHeader(WorkItemHeader workItemHeader, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workItemHeader, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemIDandPriorityType(WorkItemIDandPriorityType workItemIDandPriorityType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workItemIDandPriorityType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemPreview(WorkItemPreview workItemPreview, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workItemPreview, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemPriorityType(WorkItemPriorityType workItemPriorityType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workItemPriorityType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkListView(WorkListView workListView, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workListView, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkListViewCommon(WorkListViewCommon workListViewCommon, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workListViewCommon, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkListViewEdit(WorkListViewEdit workListViewEdit, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workListViewEdit, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkListViewPageItem(WorkListViewPageItem workListViewPageItem, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workListViewPageItem, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModel(WorkModel workModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModel, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelEntities(WorkModelEntities workModelEntities, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelEntities, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelEntity(WorkModelEntity workModelEntity, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelEntity, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelList(WorkModelList workModelList, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelList, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelMapping(WorkModelMapping workModelMapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelMapping, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelScript(WorkModelScript workModelScript, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelScript, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelScripts(WorkModelScripts workModelScripts, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelScripts, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelSpecification(WorkModelSpecification workModelSpecification, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelSpecification, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelType(WorkModelType workModelType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelType, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkModelTypes(WorkModelTypes workModelTypes, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workModelTypes, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkTypeList(WorkTypeList workTypeList, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workTypeList, diagnostics, context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateColumnCapability(ColumnCapability columnCapability, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateColumnType(ColumnType columnType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDistributionStrategy(DistributionStrategy distributionStrategy, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateMethodAuthorisationAction(MethodAuthorisationAction methodAuthorisationAction, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateMethodAuthorisationComponent(MethodAuthorisationComponent methodAuthorisationComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateResourcesRequiredType(ResourcesRequiredType resourcesRequiredType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateScheduleStatus(ScheduleStatus scheduleStatus, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkGroupType(WorkGroupType workGroupType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemScriptOperation(WorkItemScriptOperation workItemScriptOperation, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemScriptType(WorkItemScriptType workItemScriptType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemState(WorkItemState workItemState, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute10Type(String attribute10Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute10Type_MaxLength(attribute10Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute10 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute10Type_MaxLength(String attribute10Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute10Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE10_TYPE, attribute10Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute11Type(String attribute11Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute11Type_MaxLength(attribute11Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute11 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute11Type_MaxLength(String attribute11Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute11Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE11_TYPE, attribute11Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute12Type(String attribute12Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute12Type_MaxLength(attribute12Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute12 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute12Type_MaxLength(String attribute12Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute12Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE12_TYPE, attribute12Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute13Type(String attribute13Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute13Type_MaxLength(attribute13Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute13 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute13Type_MaxLength(String attribute13Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute13Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE13_TYPE, attribute13Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute14Type(String attribute14Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute14Type_MaxLength(attribute14Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute14 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute14Type_MaxLength(String attribute14Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute14Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE14_TYPE, attribute14Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute21Type(String attribute21Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute21Type_MaxLength(attribute21Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute21 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute21Type_MaxLength(String attribute21Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute21Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE21_TYPE, attribute21Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute22Type(String attribute22Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute22Type_MaxLength(attribute22Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute22 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute22Type_MaxLength(String attribute22Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute22Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE22_TYPE, attribute22Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute23Type(String attribute23Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute23Type_MaxLength(attribute23Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute23 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute23Type_MaxLength(String attribute23Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute23Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE23_TYPE, attribute23Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute24Type(String attribute24Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute24Type_MaxLength(attribute24Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute24 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute24Type_MaxLength(String attribute24Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute24Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE24_TYPE, attribute24Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute25Type(String attribute25Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute25Type_MaxLength(attribute25Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute25 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute25Type_MaxLength(String attribute25Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute25Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE25_TYPE, attribute25Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute26Type(String attribute26Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute26Type_MaxLength(attribute26Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute26 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute26Type_MaxLength(String attribute26Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute26Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE26_TYPE, attribute26Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute27Type(String attribute27Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute27Type_MaxLength(attribute27Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute27 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute27Type_MaxLength(String attribute27Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute27Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE27_TYPE, attribute27Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute28Type(String attribute28Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute28Type_MaxLength(attribute28Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute28 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute28Type_MaxLength(String attribute28Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute28Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE28_TYPE, attribute28Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute29Type(String attribute29Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute29Type_MaxLength(attribute29Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute29 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute29Type_MaxLength(String attribute29Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute29Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE29_TYPE, attribute29Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute2Type(String attribute2Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute2Type_MaxLength(attribute2Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute2 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute2Type_MaxLength(String attribute2Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute2Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE2_TYPE, attribute2Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute30Type(String attribute30Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute30Type_MaxLength(attribute30Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute30 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute30Type_MaxLength(String attribute30Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute30Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE30_TYPE, attribute30Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute31Type(String attribute31Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute31Type_MaxLength(attribute31Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute31 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute31Type_MaxLength(String attribute31Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute31Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE31_TYPE, attribute31Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute32Type(String attribute32Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute32Type_MaxLength(attribute32Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute32 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute32Type_MaxLength(String attribute32Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute32Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE32_TYPE, attribute32Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute33Type(String attribute33Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute33Type_MaxLength(attribute33Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute33 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute33Type_MaxLength(String attribute33Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute33Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE33_TYPE, attribute33Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute34Type(String attribute34Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute34Type_MaxLength(attribute34Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute34 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute34Type_MaxLength(String attribute34Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute34Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE34_TYPE, attribute34Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute35Type(String attribute35Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute35Type_MaxLength(attribute35Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute35 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute35Type_MaxLength(String attribute35Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute35Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE35_TYPE, attribute35Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute36Type(String attribute36Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute36Type_MaxLength(attribute36Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute36 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute36Type_MaxLength(String attribute36Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute36Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE36_TYPE, attribute36Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute37Type(String attribute37Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute37Type_MaxLength(attribute37Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute37 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute37Type_MaxLength(String attribute37Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute37Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE37_TYPE, attribute37Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute38Type(String attribute38Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute38Type_MaxLength(attribute38Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute38 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute38Type_MaxLength(String attribute38Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute38Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE38_TYPE, attribute38Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute39Type(String attribute39Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute39Type_MaxLength(attribute39Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute39 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute39Type_MaxLength(String attribute39Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute39Type.length();
        boolean result = length <= 255;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE39_TYPE, attribute39Type, length, 255, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute3Type(String attribute3Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute3Type_MaxLength(attribute3Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute3 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute3Type_MaxLength(String attribute3Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute3Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE3_TYPE, attribute3Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute40Type(String attribute40Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute40Type_MaxLength(attribute40Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute40 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute40Type_MaxLength(String attribute40Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute40Type.length();
        boolean result = length <= 255;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE40_TYPE, attribute40Type, length, 255, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute4Type(String attribute4Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute4Type_MaxLength(attribute4Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute4 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute4Type_MaxLength(String attribute4Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute4Type.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE4_TYPE, attribute4Type, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute8Type(String attribute8Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute8Type_MaxLength(attribute8Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute8 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute8Type_MaxLength(String attribute8Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute8Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE8_TYPE, attribute8Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute9Type(String attribute9Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateAttribute9Type_MaxLength(attribute9Type, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Attribute9 Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAttribute9Type_MaxLength(String attribute9Type, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = attribute9Type.length();
        boolean result = length <= 20;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.ATTRIBUTE9_TYPE, attribute9Type, length, 20, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateColumnCapabilityObject(ColumnCapability columnCapabilityObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateColumnTypeObject(ColumnType columnTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDescriptionType(String descriptionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateDescriptionType_MaxLength(descriptionType, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Description Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDescriptionType_MaxLength(String descriptionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = descriptionType.length();
        boolean result = length <= 255;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.DESCRIPTION_TYPE, descriptionType, length, 255, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDistributionStrategyObject(DistributionStrategy distributionStrategyObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateLockerType(String lockerType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateLockerType_MaxLength(lockerType, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Locker Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateLockerType_MaxLength(String lockerType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = lockerType.length();
        boolean result = length <= 36;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.LOCKER_TYPE, lockerType, length, 36, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateMethodAuthorisationActionObject(MethodAuthorisationAction methodAuthorisationActionObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateMethodAuthorisationComponentObject(MethodAuthorisationComponent methodAuthorisationComponentObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateNameType(String nameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateNameType_MaxLength(nameType, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Name Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateNameType_MaxLength(String nameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = nameType.length();
        boolean result = length <= 64;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.NAME_TYPE, nameType, length, 64, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOwnerType(String ownerType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result = validateOwnerType_MaxLength(ownerType, diagnostics, context);
        return result;
    }

    /**
     * Validates the MaxLength constraint of '<em>Owner Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOwnerType_MaxLength(String ownerType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        int length = ownerType.length();
        boolean result = length <= 36;
        if (!result && diagnostics != null)
            reportMaxLengthViolation(N2BRMPackage.Literals.OWNER_TYPE, ownerType, length, 36, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateResourcesRequiredTypeObject(ResourcesRequiredType resourcesRequiredTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateScheduleStatusObject(ScheduleStatus scheduleStatusObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateScriptOperationType(WorkItemScriptOperation scriptOperationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkGroupTypeObject(WorkGroupType workGroupTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemScriptOperationObject(WorkItemScriptOperation workItemScriptOperationObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemScriptTypeObject(WorkItemScriptType workItemScriptTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkItemStateObject(WorkItemState workItemStateObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        // TODO
        // Specialize this to return a resource locator for messages specific to this validator.
        // Ensure that you remove @generated or mark it @generated NOT
        return super.getResourceLocator();
    }

} //N2BRMValidator
