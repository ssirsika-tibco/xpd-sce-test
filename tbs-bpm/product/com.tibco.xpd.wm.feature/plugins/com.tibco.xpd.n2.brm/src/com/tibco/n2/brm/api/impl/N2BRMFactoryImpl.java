/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class N2BRMFactoryImpl extends EFactoryImpl implements N2BRMFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static N2BRMFactory init() {
        try {
            N2BRMFactory theN2BRMFactory = (N2BRMFactory)EPackage.Registry.INSTANCE.getEFactory("http://api.brm.n2.tibco.com"); 
            if (theN2BRMFactory != null) {
                return theN2BRMFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new N2BRMFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public N2BRMFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case N2BRMPackage.ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE: return createAddCurrentResourceToViewResponseType();
            case N2BRMPackage.ADD_CURRENT_RESOURCE_TO_VIEW_TYPE: return createAddCurrentResourceToViewType();
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE: return createAllocateAndOpenNextWorkItemResponseType();
            case N2BRMPackage.ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE: return createAllocateAndOpenNextWorkItemType();
            case N2BRMPackage.ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE: return createAllocateAndOpenWorkItemResponseType();
            case N2BRMPackage.ALLOCATE_AND_OPEN_WORK_ITEM_TYPE: return createAllocateAndOpenWorkItemType();
            case N2BRMPackage.ALLOCATE_WORK_ITEM_RESPONSE_TYPE: return createAllocateWorkItemResponseType();
            case N2BRMPackage.ALLOCATE_WORK_ITEM_TYPE: return createAllocateWorkItemType();
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE: return createAsyncCancelWorkItemResponseType();
            case N2BRMPackage.ASYNC_CANCEL_WORK_ITEM_TYPE: return createAsyncCancelWorkItemType();
            case N2BRMPackage.ASYNC_END_GROUP_RESPONSE_TYPE: return createAsyncEndGroupResponseType();
            case N2BRMPackage.ASYNC_END_GROUP_TYPE: return createAsyncEndGroupType();
            case N2BRMPackage.ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE: return createAsyncRescheduleWorkItemResponseType();
            case N2BRMPackage.ASYNC_RESCHEDULE_WORK_ITEM_TYPE: return createAsyncRescheduleWorkItemType();
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE: return createAsyncResumeWorkItemResponseType();
            case N2BRMPackage.ASYNC_RESUME_WORK_ITEM_TYPE: return createAsyncResumeWorkItemType();
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE: return createAsyncScheduleWorkItemResponseType();
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_TYPE: return createAsyncScheduleWorkItemType();
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE: return createAsyncScheduleWorkItemWithModelResponseType();
            case N2BRMPackage.ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE: return createAsyncScheduleWorkItemWithModelType();
            case N2BRMPackage.ASYNC_START_GROUP_RESPONSE_TYPE: return createAsyncStartGroupResponseType();
            case N2BRMPackage.ASYNC_START_GROUP_TYPE: return createAsyncStartGroupType();
            case N2BRMPackage.ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE: return createAsyncSuspendWorkItemResponseType();
            case N2BRMPackage.ASYNC_SUSPEND_WORK_ITEM_TYPE: return createAsyncSuspendWorkItemType();
            case N2BRMPackage.ASYNC_WORK_ITEM_DETAILS: return createAsyncWorkItemDetails();
            case N2BRMPackage.ATTRIBUTE_ALIAS_LIST_TYPE: return createAttributeAliasListType();
            case N2BRMPackage.BASE_MODEL_INFO: return createBaseModelInfo();
            case N2BRMPackage.CANCEL_WORK_ITEM_RESPONSE_TYPE: return createCancelWorkItemResponseType();
            case N2BRMPackage.CANCEL_WORK_ITEM_TYPE: return createCancelWorkItemType();
            case N2BRMPackage.CHAINED_WORK_ITEM_NOTIFICATION_TYPE: return createChainedWorkItemNotificationType();
            case N2BRMPackage.CLOSE_WORK_ITEM_RESPONSE_TYPE: return createCloseWorkItemResponseType();
            case N2BRMPackage.CLOSE_WORK_ITEM_TYPE: return createCloseWorkItemType();
            case N2BRMPackage.COLUMN_DEFINITION: return createColumnDefinition();
            case N2BRMPackage.COLUMN_ORDER: return createColumnOrder();
            case N2BRMPackage.COMPLETE_WORK_ITEM_RESPONSE_TYPE: return createCompleteWorkItemResponseType();
            case N2BRMPackage.COMPLETE_WORK_ITEM_TYPE: return createCompleteWorkItemType();
            case N2BRMPackage.CREATE_WORK_LIST_VIEW_RESPONSE_TYPE: return createCreateWorkListViewResponseType();
            case N2BRMPackage.DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE: return createDeleteCurrentResourceFromViewResponseType();
            case N2BRMPackage.DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE: return createDeleteCurrentResourceFromViewType();
            case N2BRMPackage.DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE: return createDeleteOrgEntityConfigAttributesResponseType();
            case N2BRMPackage.DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE: return createDeleteOrgEntityConfigAttributesType();
            case N2BRMPackage.DELETE_WORK_LIST_VIEW_RESPONSE_TYPE: return createDeleteWorkListViewResponseType();
            case N2BRMPackage.DELETE_WORK_LIST_VIEW_TYPE: return createDeleteWorkListViewType();
            case N2BRMPackage.DOCUMENT_ROOT: return createDocumentRoot();
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE: return createEditWorkListViewResponseType();
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_TYPE: return createEditWorkListViewType();
            case N2BRMPackage.ENABLE_WORK_ITEM_RESPONSE_TYPE: return createEnableWorkItemResponseType();
            case N2BRMPackage.ENABLE_WORK_ITEM_TYPE: return createEnableWorkItemType();
            case N2BRMPackage.END_GROUP_RESPONSE_TYPE: return createEndGroupResponseType();
            case N2BRMPackage.END_GROUP_TYPE: return createEndGroupType();
            case N2BRMPackage.GET_ALLOCATED_WORK_LIST_ITEMS_TYPE: return createGetAllocatedWorkListItemsType();
            case N2BRMPackage.GET_BATCH_GROUP_IDS_RESPONSE_TYPE: return createGetBatchGroupIdsResponseType();
            case N2BRMPackage.GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE: return createGetBatchWorkItemIdsResponseType();
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE: return createGetEditableWorkListViewsResponseType();
            case N2BRMPackage.GET_EDITABLE_WORK_LIST_VIEWS_TYPE: return createGetEditableWorkListViewsType();
            case N2BRMPackage.GET_OFFER_SET_RESPONSE_TYPE: return createGetOfferSetResponseType();
            case N2BRMPackage.GET_OFFER_SET_TYPE: return createGetOfferSetType();
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE: return createGetOrgEntityConfigAttributesAvailableResponseType();
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE: return createGetOrgEntityConfigAttributesAvailableType();
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE: return createGetOrgEntityConfigAttributesResponseType();
            case N2BRMPackage.GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE: return createGetOrgEntityConfigAttributesType();
            case N2BRMPackage.GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE: return createGetPublicWorkListViewsResponseType();
            case N2BRMPackage.GET_PUBLIC_WORK_LIST_VIEWS_TYPE: return createGetPublicWorkListViewsType();
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE: return createGetResourceOrderFilterCriteriaResponseType();
            case N2BRMPackage.GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE: return createGetResourceOrderFilterCriteriaType();
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE: return createGetViewsForResourceResponseType();
            case N2BRMPackage.GET_VIEWS_FOR_RESOURCE_TYPE: return createGetViewsForResourceType();
            case N2BRMPackage.GET_WORK_ITEM_HEADER_RESPONSE_TYPE: return createGetWorkItemHeaderResponseType();
            case N2BRMPackage.GET_WORK_ITEM_HEADER_TYPE: return createGetWorkItemHeaderType();
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE: return createGetWorkItemOrderFilterResponseType();
            case N2BRMPackage.GET_WORK_ITEM_ORDER_FILTER_TYPE: return createGetWorkItemOrderFilterType();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE: return createGetWorkListItemsForGlobalDataResponseType();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE: return createGetWorkListItemsForGlobalDataType();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE: return createGetWorkListItemsForViewResponseType();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE: return createGetWorkListItemsForViewType();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_RESPONSE_TYPE: return createGetWorkListItemsResponseType();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_RESPONSE_TYPE1: return createGetWorkListItemsResponseType1();
            case N2BRMPackage.GET_WORK_LIST_ITEMS_TYPE: return createGetWorkListItemsType();
            case N2BRMPackage.GET_WORK_LIST_VIEW_DETAILS_TYPE: return createGetWorkListViewDetailsType();
            case N2BRMPackage.GET_WORK_MODEL_RESPONSE_TYPE: return createGetWorkModelResponseType();
            case N2BRMPackage.GET_WORK_MODELS_RESPONSE_TYPE: return createGetWorkModelsResponseType();
            case N2BRMPackage.GET_WORK_MODELS_TYPE: return createGetWorkModelsType();
            case N2BRMPackage.GET_WORK_MODEL_TYPE: return createGetWorkModelType();
            case N2BRMPackage.GET_WORK_TYPE_RESPONSE_TYPE: return createGetWorkTypeResponseType();
            case N2BRMPackage.GET_WORK_TYPES_RESPONSE_TYPE: return createGetWorkTypesResponseType();
            case N2BRMPackage.GET_WORK_TYPES_TYPE: return createGetWorkTypesType();
            case N2BRMPackage.GET_WORK_TYPE_TYPE: return createGetWorkTypeType();
            case N2BRMPackage.HIDDEN_PERIOD: return createHiddenPeriod();
            case N2BRMPackage.ITEM: return createItem();
            case N2BRMPackage.ITEM_BODY: return createItemBody();
            case N2BRMPackage.ITEM_CONTEXT: return createItemContext();
            case N2BRMPackage.ITEM_DURATION: return createItemDuration();
            case N2BRMPackage.ITEM_PRIVILEGE: return createItemPrivilege();
            case N2BRMPackage.ITEM_SCHEDULE: return createItemSchedule();
            case N2BRMPackage.MANAGED_OBJECT_ID: return createManagedObjectID();
            case N2BRMPackage.NEXT_WORK_ITEM: return createNextWorkItem();
            case N2BRMPackage.OBJECT_ID: return createObjectID();
            case N2BRMPackage.ON_NOTIFICATION_RESPONSE_TYPE: return createOnNotificationResponseType();
            case N2BRMPackage.ON_NOTIFICATION_TYPE: return createOnNotificationType();
            case N2BRMPackage.OPEN_WORK_ITEM_RESPONSE_TYPE: return createOpenWorkItemResponseType();
            case N2BRMPackage.OPEN_WORK_ITEM_TYPE: return createOpenWorkItemType();
            case N2BRMPackage.ORDER_FILTER_CRITERIA: return createOrderFilterCriteria();
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTE: return createOrgEntityConfigAttribute();
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE: return createOrgEntityConfigAttributesAvailable();
            case N2BRMPackage.ORG_ENTITY_CONFIG_ATTRIBUTE_SET: return createOrgEntityConfigAttributeSet();
            case N2BRMPackage.PARAMETER_TYPE: return createParameterType();
            case N2BRMPackage.PEND_WORK_ITEM: return createPendWorkItem();
            case N2BRMPackage.PEND_WORK_ITEM_RESPONSE_TYPE: return createPendWorkItemResponseType();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE: return createPreviewWorkItemFromListResponseType();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_TYPE: return createPreviewWorkItemFromListType();
            case N2BRMPackage.PRIVILEGE: return createPrivilege();
            case N2BRMPackage.PUSH_NOTIFICATION_TYPE: return createPushNotificationType();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA: return createReallocateWorkItemData();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE: return createReallocateWorkItemDataResponseType();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_RESPONSE_TYPE: return createReallocateWorkItemResponseType();
            case N2BRMPackage.REALLOCATE_WORK_ITEM_TYPE: return createReallocateWorkItemType();
            case N2BRMPackage.RESCHEDULE_WORK_ITEM: return createRescheduleWorkItem();
            case N2BRMPackage.RESCHEDULE_WORK_ITEM_RESPONSE_TYPE: return createRescheduleWorkItemResponseType();
            case N2BRMPackage.RESUME_WORK_ITEM_RESPONSE_TYPE: return createResumeWorkItemResponseType();
            case N2BRMPackage.RESUME_WORK_ITEM_TYPE: return createResumeWorkItemType();
            case N2BRMPackage.SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE: return createSaveOpenWorkItemResponseType();
            case N2BRMPackage.SAVE_OPEN_WORK_ITEM_TYPE: return createSaveOpenWorkItemType();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_RESPONSE_TYPE: return createScheduleWorkItemResponseType();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_TYPE: return createScheduleWorkItemType();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE: return createScheduleWorkItemWithModelResponseType();
            case N2BRMPackage.SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE: return createScheduleWorkItemWithModelType();
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE: return createSetOrgEntityConfigAttributesResponseType();
            case N2BRMPackage.SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE: return createSetOrgEntityConfigAttributesType();
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE: return createSetResourceOrderFilterCriteriaResponseType();
            case N2BRMPackage.SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE: return createSetResourceOrderFilterCriteriaType();
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY: return createSetWorkItemPriority();
            case N2BRMPackage.SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE: return createSetWorkItemPriorityResponseType();
            case N2BRMPackage.SKIP_WORK_ITEM_RESPONSE_TYPE: return createSkipWorkItemResponseType();
            case N2BRMPackage.SKIP_WORK_ITEM_TYPE: return createSkipWorkItemType();
            case N2BRMPackage.START_GROUP_RESPONSE_TYPE: return createStartGroupResponseType();
            case N2BRMPackage.START_GROUP_TYPE: return createStartGroupType();
            case N2BRMPackage.SUSPEND_WORK_ITEM_RESPONSE_TYPE: return createSuspendWorkItemResponseType();
            case N2BRMPackage.SUSPEND_WORK_ITEM_TYPE: return createSuspendWorkItemType();
            case N2BRMPackage.UNALLOCATE_WORK_ITEM_RESPONSE_TYPE: return createUnallocateWorkItemResponseType();
            case N2BRMPackage.UNALLOCATE_WORK_ITEM_TYPE: return createUnallocateWorkItemType();
            case N2BRMPackage.UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE: return createUnlockWorkListViewResponseType();
            case N2BRMPackage.UNLOCK_WORK_LIST_VIEW_TYPE: return createUnlockWorkListViewType();
            case N2BRMPackage.WORK_ITEM: return createWorkItem();
            case N2BRMPackage.WORK_ITEM_ATTRIBUTES: return createWorkItemAttributes();
            case N2BRMPackage.WORK_ITEM_BODY: return createWorkItemBody();
            case N2BRMPackage.WORK_ITEM_FLAGS: return createWorkItemFlags();
            case N2BRMPackage.WORK_ITEM_HEADER: return createWorkItemHeader();
            case N2BRMPackage.WORK_ITEM_IDAND_PRIORITY_TYPE: return createWorkItemIDandPriorityType();
            case N2BRMPackage.WORK_ITEM_PREVIEW: return createWorkItemPreview();
            case N2BRMPackage.WORK_ITEM_PRIORITY_TYPE: return createWorkItemPriorityType();
            case N2BRMPackage.WORK_LIST_VIEW: return createWorkListView();
            case N2BRMPackage.WORK_LIST_VIEW_COMMON: return createWorkListViewCommon();
            case N2BRMPackage.WORK_LIST_VIEW_EDIT: return createWorkListViewEdit();
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM: return createWorkListViewPageItem();
            case N2BRMPackage.WORK_MODEL: return createWorkModel();
            case N2BRMPackage.WORK_MODEL_ENTITIES: return createWorkModelEntities();
            case N2BRMPackage.WORK_MODEL_ENTITY: return createWorkModelEntity();
            case N2BRMPackage.WORK_MODEL_LIST: return createWorkModelList();
            case N2BRMPackage.WORK_MODEL_MAPPING: return createWorkModelMapping();
            case N2BRMPackage.WORK_MODEL_SCRIPT: return createWorkModelScript();
            case N2BRMPackage.WORK_MODEL_SCRIPTS: return createWorkModelScripts();
            case N2BRMPackage.WORK_MODEL_SPECIFICATION: return createWorkModelSpecification();
            case N2BRMPackage.WORK_MODEL_TYPE: return createWorkModelType();
            case N2BRMPackage.WORK_MODEL_TYPES: return createWorkModelTypes();
            case N2BRMPackage.WORK_TYPE_LIST: return createWorkTypeList();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case N2BRMPackage.COLUMN_CAPABILITY:
                return createColumnCapabilityFromString(eDataType, initialValue);
            case N2BRMPackage.COLUMN_TYPE:
                return createColumnTypeFromString(eDataType, initialValue);
            case N2BRMPackage.DISTRIBUTION_STRATEGY:
                return createDistributionStrategyFromString(eDataType, initialValue);
            case N2BRMPackage.METHOD_AUTHORISATION_ACTION:
                return createMethodAuthorisationActionFromString(eDataType, initialValue);
            case N2BRMPackage.METHOD_AUTHORISATION_COMPONENT:
                return createMethodAuthorisationComponentFromString(eDataType, initialValue);
            case N2BRMPackage.RESOURCES_REQUIRED_TYPE:
                return createResourcesRequiredTypeFromString(eDataType, initialValue);
            case N2BRMPackage.SCHEDULE_STATUS:
                return createScheduleStatusFromString(eDataType, initialValue);
            case N2BRMPackage.WORK_GROUP_TYPE:
                return createWorkGroupTypeFromString(eDataType, initialValue);
            case N2BRMPackage.WORK_ITEM_SCRIPT_OPERATION:
                return createWorkItemScriptOperationFromString(eDataType, initialValue);
            case N2BRMPackage.WORK_ITEM_SCRIPT_TYPE:
                return createWorkItemScriptTypeFromString(eDataType, initialValue);
            case N2BRMPackage.WORK_ITEM_STATE:
                return createWorkItemStateFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE10_TYPE:
                return createAttribute10TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE11_TYPE:
                return createAttribute11TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE12_TYPE:
                return createAttribute12TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE13_TYPE:
                return createAttribute13TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE14_TYPE:
                return createAttribute14TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE21_TYPE:
                return createAttribute21TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE22_TYPE:
                return createAttribute22TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE23_TYPE:
                return createAttribute23TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE24_TYPE:
                return createAttribute24TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE25_TYPE:
                return createAttribute25TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE26_TYPE:
                return createAttribute26TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE27_TYPE:
                return createAttribute27TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE28_TYPE:
                return createAttribute28TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE29_TYPE:
                return createAttribute29TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE2_TYPE:
                return createAttribute2TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE30_TYPE:
                return createAttribute30TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE31_TYPE:
                return createAttribute31TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE32_TYPE:
                return createAttribute32TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE33_TYPE:
                return createAttribute33TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE34_TYPE:
                return createAttribute34TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE35_TYPE:
                return createAttribute35TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE36_TYPE:
                return createAttribute36TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE37_TYPE:
                return createAttribute37TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE38_TYPE:
                return createAttribute38TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE39_TYPE:
                return createAttribute39TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE3_TYPE:
                return createAttribute3TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE40_TYPE:
                return createAttribute40TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE4_TYPE:
                return createAttribute4TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE8_TYPE:
                return createAttribute8TypeFromString(eDataType, initialValue);
            case N2BRMPackage.ATTRIBUTE9_TYPE:
                return createAttribute9TypeFromString(eDataType, initialValue);
            case N2BRMPackage.COLUMN_CAPABILITY_OBJECT:
                return createColumnCapabilityObjectFromString(eDataType, initialValue);
            case N2BRMPackage.COLUMN_TYPE_OBJECT:
                return createColumnTypeObjectFromString(eDataType, initialValue);
            case N2BRMPackage.DESCRIPTION_TYPE:
                return createDescriptionTypeFromString(eDataType, initialValue);
            case N2BRMPackage.DISTRIBUTION_STRATEGY_OBJECT:
                return createDistributionStrategyObjectFromString(eDataType, initialValue);
            case N2BRMPackage.LOCKER_TYPE:
                return createLockerTypeFromString(eDataType, initialValue);
            case N2BRMPackage.METHOD_AUTHORISATION_ACTION_OBJECT:
                return createMethodAuthorisationActionObjectFromString(eDataType, initialValue);
            case N2BRMPackage.METHOD_AUTHORISATION_COMPONENT_OBJECT:
                return createMethodAuthorisationComponentObjectFromString(eDataType, initialValue);
            case N2BRMPackage.NAME_TYPE:
                return createNameTypeFromString(eDataType, initialValue);
            case N2BRMPackage.OWNER_TYPE:
                return createOwnerTypeFromString(eDataType, initialValue);
            case N2BRMPackage.RESOURCES_REQUIRED_TYPE_OBJECT:
                return createResourcesRequiredTypeObjectFromString(eDataType, initialValue);
            case N2BRMPackage.SCHEDULE_STATUS_OBJECT:
                return createScheduleStatusObjectFromString(eDataType, initialValue);
            case N2BRMPackage.SCRIPT_OPERATION_TYPE:
                return createScriptOperationTypeFromString(eDataType, initialValue);
            case N2BRMPackage.WORK_GROUP_TYPE_OBJECT:
                return createWorkGroupTypeObjectFromString(eDataType, initialValue);
            case N2BRMPackage.WORK_ITEM_SCRIPT_OPERATION_OBJECT:
                return createWorkItemScriptOperationObjectFromString(eDataType, initialValue);
            case N2BRMPackage.WORK_ITEM_SCRIPT_TYPE_OBJECT:
                return createWorkItemScriptTypeObjectFromString(eDataType, initialValue);
            case N2BRMPackage.WORK_ITEM_STATE_OBJECT:
                return createWorkItemStateObjectFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case N2BRMPackage.COLUMN_CAPABILITY:
                return convertColumnCapabilityToString(eDataType, instanceValue);
            case N2BRMPackage.COLUMN_TYPE:
                return convertColumnTypeToString(eDataType, instanceValue);
            case N2BRMPackage.DISTRIBUTION_STRATEGY:
                return convertDistributionStrategyToString(eDataType, instanceValue);
            case N2BRMPackage.METHOD_AUTHORISATION_ACTION:
                return convertMethodAuthorisationActionToString(eDataType, instanceValue);
            case N2BRMPackage.METHOD_AUTHORISATION_COMPONENT:
                return convertMethodAuthorisationComponentToString(eDataType, instanceValue);
            case N2BRMPackage.RESOURCES_REQUIRED_TYPE:
                return convertResourcesRequiredTypeToString(eDataType, instanceValue);
            case N2BRMPackage.SCHEDULE_STATUS:
                return convertScheduleStatusToString(eDataType, instanceValue);
            case N2BRMPackage.WORK_GROUP_TYPE:
                return convertWorkGroupTypeToString(eDataType, instanceValue);
            case N2BRMPackage.WORK_ITEM_SCRIPT_OPERATION:
                return convertWorkItemScriptOperationToString(eDataType, instanceValue);
            case N2BRMPackage.WORK_ITEM_SCRIPT_TYPE:
                return convertWorkItemScriptTypeToString(eDataType, instanceValue);
            case N2BRMPackage.WORK_ITEM_STATE:
                return convertWorkItemStateToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE10_TYPE:
                return convertAttribute10TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE11_TYPE:
                return convertAttribute11TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE12_TYPE:
                return convertAttribute12TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE13_TYPE:
                return convertAttribute13TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE14_TYPE:
                return convertAttribute14TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE21_TYPE:
                return convertAttribute21TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE22_TYPE:
                return convertAttribute22TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE23_TYPE:
                return convertAttribute23TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE24_TYPE:
                return convertAttribute24TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE25_TYPE:
                return convertAttribute25TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE26_TYPE:
                return convertAttribute26TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE27_TYPE:
                return convertAttribute27TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE28_TYPE:
                return convertAttribute28TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE29_TYPE:
                return convertAttribute29TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE2_TYPE:
                return convertAttribute2TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE30_TYPE:
                return convertAttribute30TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE31_TYPE:
                return convertAttribute31TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE32_TYPE:
                return convertAttribute32TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE33_TYPE:
                return convertAttribute33TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE34_TYPE:
                return convertAttribute34TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE35_TYPE:
                return convertAttribute35TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE36_TYPE:
                return convertAttribute36TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE37_TYPE:
                return convertAttribute37TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE38_TYPE:
                return convertAttribute38TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE39_TYPE:
                return convertAttribute39TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE3_TYPE:
                return convertAttribute3TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE40_TYPE:
                return convertAttribute40TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE4_TYPE:
                return convertAttribute4TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE8_TYPE:
                return convertAttribute8TypeToString(eDataType, instanceValue);
            case N2BRMPackage.ATTRIBUTE9_TYPE:
                return convertAttribute9TypeToString(eDataType, instanceValue);
            case N2BRMPackage.COLUMN_CAPABILITY_OBJECT:
                return convertColumnCapabilityObjectToString(eDataType, instanceValue);
            case N2BRMPackage.COLUMN_TYPE_OBJECT:
                return convertColumnTypeObjectToString(eDataType, instanceValue);
            case N2BRMPackage.DESCRIPTION_TYPE:
                return convertDescriptionTypeToString(eDataType, instanceValue);
            case N2BRMPackage.DISTRIBUTION_STRATEGY_OBJECT:
                return convertDistributionStrategyObjectToString(eDataType, instanceValue);
            case N2BRMPackage.LOCKER_TYPE:
                return convertLockerTypeToString(eDataType, instanceValue);
            case N2BRMPackage.METHOD_AUTHORISATION_ACTION_OBJECT:
                return convertMethodAuthorisationActionObjectToString(eDataType, instanceValue);
            case N2BRMPackage.METHOD_AUTHORISATION_COMPONENT_OBJECT:
                return convertMethodAuthorisationComponentObjectToString(eDataType, instanceValue);
            case N2BRMPackage.NAME_TYPE:
                return convertNameTypeToString(eDataType, instanceValue);
            case N2BRMPackage.OWNER_TYPE:
                return convertOwnerTypeToString(eDataType, instanceValue);
            case N2BRMPackage.RESOURCES_REQUIRED_TYPE_OBJECT:
                return convertResourcesRequiredTypeObjectToString(eDataType, instanceValue);
            case N2BRMPackage.SCHEDULE_STATUS_OBJECT:
                return convertScheduleStatusObjectToString(eDataType, instanceValue);
            case N2BRMPackage.SCRIPT_OPERATION_TYPE:
                return convertScriptOperationTypeToString(eDataType, instanceValue);
            case N2BRMPackage.WORK_GROUP_TYPE_OBJECT:
                return convertWorkGroupTypeObjectToString(eDataType, instanceValue);
            case N2BRMPackage.WORK_ITEM_SCRIPT_OPERATION_OBJECT:
                return convertWorkItemScriptOperationObjectToString(eDataType, instanceValue);
            case N2BRMPackage.WORK_ITEM_SCRIPT_TYPE_OBJECT:
                return convertWorkItemScriptTypeObjectToString(eDataType, instanceValue);
            case N2BRMPackage.WORK_ITEM_STATE_OBJECT:
                return convertWorkItemStateObjectToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AddCurrentResourceToViewResponseType createAddCurrentResourceToViewResponseType() {
        AddCurrentResourceToViewResponseTypeImpl addCurrentResourceToViewResponseType = new AddCurrentResourceToViewResponseTypeImpl();
        return addCurrentResourceToViewResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AddCurrentResourceToViewType createAddCurrentResourceToViewType() {
        AddCurrentResourceToViewTypeImpl addCurrentResourceToViewType = new AddCurrentResourceToViewTypeImpl();
        return addCurrentResourceToViewType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateAndOpenNextWorkItemResponseType createAllocateAndOpenNextWorkItemResponseType() {
        AllocateAndOpenNextWorkItemResponseTypeImpl allocateAndOpenNextWorkItemResponseType = new AllocateAndOpenNextWorkItemResponseTypeImpl();
        return allocateAndOpenNextWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateAndOpenNextWorkItemType createAllocateAndOpenNextWorkItemType() {
        AllocateAndOpenNextWorkItemTypeImpl allocateAndOpenNextWorkItemType = new AllocateAndOpenNextWorkItemTypeImpl();
        return allocateAndOpenNextWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateAndOpenWorkItemResponseType createAllocateAndOpenWorkItemResponseType() {
        AllocateAndOpenWorkItemResponseTypeImpl allocateAndOpenWorkItemResponseType = new AllocateAndOpenWorkItemResponseTypeImpl();
        return allocateAndOpenWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateAndOpenWorkItemType createAllocateAndOpenWorkItemType() {
        AllocateAndOpenWorkItemTypeImpl allocateAndOpenWorkItemType = new AllocateAndOpenWorkItemTypeImpl();
        return allocateAndOpenWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateWorkItemResponseType createAllocateWorkItemResponseType() {
        AllocateWorkItemResponseTypeImpl allocateWorkItemResponseType = new AllocateWorkItemResponseTypeImpl();
        return allocateWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateWorkItemType createAllocateWorkItemType() {
        AllocateWorkItemTypeImpl allocateWorkItemType = new AllocateWorkItemTypeImpl();
        return allocateWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncCancelWorkItemResponseType createAsyncCancelWorkItemResponseType() {
        AsyncCancelWorkItemResponseTypeImpl asyncCancelWorkItemResponseType = new AsyncCancelWorkItemResponseTypeImpl();
        return asyncCancelWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncCancelWorkItemType createAsyncCancelWorkItemType() {
        AsyncCancelWorkItemTypeImpl asyncCancelWorkItemType = new AsyncCancelWorkItemTypeImpl();
        return asyncCancelWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncEndGroupResponseType createAsyncEndGroupResponseType() {
        AsyncEndGroupResponseTypeImpl asyncEndGroupResponseType = new AsyncEndGroupResponseTypeImpl();
        return asyncEndGroupResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncEndGroupType createAsyncEndGroupType() {
        AsyncEndGroupTypeImpl asyncEndGroupType = new AsyncEndGroupTypeImpl();
        return asyncEndGroupType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncRescheduleWorkItemResponseType createAsyncRescheduleWorkItemResponseType() {
        AsyncRescheduleWorkItemResponseTypeImpl asyncRescheduleWorkItemResponseType = new AsyncRescheduleWorkItemResponseTypeImpl();
        return asyncRescheduleWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncRescheduleWorkItemType createAsyncRescheduleWorkItemType() {
        AsyncRescheduleWorkItemTypeImpl asyncRescheduleWorkItemType = new AsyncRescheduleWorkItemTypeImpl();
        return asyncRescheduleWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncResumeWorkItemResponseType createAsyncResumeWorkItemResponseType() {
        AsyncResumeWorkItemResponseTypeImpl asyncResumeWorkItemResponseType = new AsyncResumeWorkItemResponseTypeImpl();
        return asyncResumeWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncResumeWorkItemType createAsyncResumeWorkItemType() {
        AsyncResumeWorkItemTypeImpl asyncResumeWorkItemType = new AsyncResumeWorkItemTypeImpl();
        return asyncResumeWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncScheduleWorkItemResponseType createAsyncScheduleWorkItemResponseType() {
        AsyncScheduleWorkItemResponseTypeImpl asyncScheduleWorkItemResponseType = new AsyncScheduleWorkItemResponseTypeImpl();
        return asyncScheduleWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncScheduleWorkItemType createAsyncScheduleWorkItemType() {
        AsyncScheduleWorkItemTypeImpl asyncScheduleWorkItemType = new AsyncScheduleWorkItemTypeImpl();
        return asyncScheduleWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncScheduleWorkItemWithModelResponseType createAsyncScheduleWorkItemWithModelResponseType() {
        AsyncScheduleWorkItemWithModelResponseTypeImpl asyncScheduleWorkItemWithModelResponseType = new AsyncScheduleWorkItemWithModelResponseTypeImpl();
        return asyncScheduleWorkItemWithModelResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncScheduleWorkItemWithModelType createAsyncScheduleWorkItemWithModelType() {
        AsyncScheduleWorkItemWithModelTypeImpl asyncScheduleWorkItemWithModelType = new AsyncScheduleWorkItemWithModelTypeImpl();
        return asyncScheduleWorkItemWithModelType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncStartGroupResponseType createAsyncStartGroupResponseType() {
        AsyncStartGroupResponseTypeImpl asyncStartGroupResponseType = new AsyncStartGroupResponseTypeImpl();
        return asyncStartGroupResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncStartGroupType createAsyncStartGroupType() {
        AsyncStartGroupTypeImpl asyncStartGroupType = new AsyncStartGroupTypeImpl();
        return asyncStartGroupType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncSuspendWorkItemResponseType createAsyncSuspendWorkItemResponseType() {
        AsyncSuspendWorkItemResponseTypeImpl asyncSuspendWorkItemResponseType = new AsyncSuspendWorkItemResponseTypeImpl();
        return asyncSuspendWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncSuspendWorkItemType createAsyncSuspendWorkItemType() {
        AsyncSuspendWorkItemTypeImpl asyncSuspendWorkItemType = new AsyncSuspendWorkItemTypeImpl();
        return asyncSuspendWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncWorkItemDetails createAsyncWorkItemDetails() {
        AsyncWorkItemDetailsImpl asyncWorkItemDetails = new AsyncWorkItemDetailsImpl();
        return asyncWorkItemDetails;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeAliasListType createAttributeAliasListType() {
        AttributeAliasListTypeImpl attributeAliasListType = new AttributeAliasListTypeImpl();
        return attributeAliasListType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BaseModelInfo createBaseModelInfo() {
        BaseModelInfoImpl baseModelInfo = new BaseModelInfoImpl();
        return baseModelInfo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CancelWorkItemResponseType createCancelWorkItemResponseType() {
        CancelWorkItemResponseTypeImpl cancelWorkItemResponseType = new CancelWorkItemResponseTypeImpl();
        return cancelWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CancelWorkItemType createCancelWorkItemType() {
        CancelWorkItemTypeImpl cancelWorkItemType = new CancelWorkItemTypeImpl();
        return cancelWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChainedWorkItemNotificationType createChainedWorkItemNotificationType() {
        ChainedWorkItemNotificationTypeImpl chainedWorkItemNotificationType = new ChainedWorkItemNotificationTypeImpl();
        return chainedWorkItemNotificationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CloseWorkItemResponseType createCloseWorkItemResponseType() {
        CloseWorkItemResponseTypeImpl closeWorkItemResponseType = new CloseWorkItemResponseTypeImpl();
        return closeWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CloseWorkItemType createCloseWorkItemType() {
        CloseWorkItemTypeImpl closeWorkItemType = new CloseWorkItemTypeImpl();
        return closeWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ColumnDefinition createColumnDefinition() {
        ColumnDefinitionImpl columnDefinition = new ColumnDefinitionImpl();
        return columnDefinition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ColumnOrder createColumnOrder() {
        ColumnOrderImpl columnOrder = new ColumnOrderImpl();
        return columnOrder;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompleteWorkItemResponseType createCompleteWorkItemResponseType() {
        CompleteWorkItemResponseTypeImpl completeWorkItemResponseType = new CompleteWorkItemResponseTypeImpl();
        return completeWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompleteWorkItemType createCompleteWorkItemType() {
        CompleteWorkItemTypeImpl completeWorkItemType = new CompleteWorkItemTypeImpl();
        return completeWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CreateWorkListViewResponseType createCreateWorkListViewResponseType() {
        CreateWorkListViewResponseTypeImpl createWorkListViewResponseType = new CreateWorkListViewResponseTypeImpl();
        return createWorkListViewResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteCurrentResourceFromViewResponseType createDeleteCurrentResourceFromViewResponseType() {
        DeleteCurrentResourceFromViewResponseTypeImpl deleteCurrentResourceFromViewResponseType = new DeleteCurrentResourceFromViewResponseTypeImpl();
        return deleteCurrentResourceFromViewResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteCurrentResourceFromViewType createDeleteCurrentResourceFromViewType() {
        DeleteCurrentResourceFromViewTypeImpl deleteCurrentResourceFromViewType = new DeleteCurrentResourceFromViewTypeImpl();
        return deleteCurrentResourceFromViewType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteOrgEntityConfigAttributesResponseType createDeleteOrgEntityConfigAttributesResponseType() {
        DeleteOrgEntityConfigAttributesResponseTypeImpl deleteOrgEntityConfigAttributesResponseType = new DeleteOrgEntityConfigAttributesResponseTypeImpl();
        return deleteOrgEntityConfigAttributesResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteOrgEntityConfigAttributesType createDeleteOrgEntityConfigAttributesType() {
        DeleteOrgEntityConfigAttributesTypeImpl deleteOrgEntityConfigAttributesType = new DeleteOrgEntityConfigAttributesTypeImpl();
        return deleteOrgEntityConfigAttributesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteWorkListViewResponseType createDeleteWorkListViewResponseType() {
        DeleteWorkListViewResponseTypeImpl deleteWorkListViewResponseType = new DeleteWorkListViewResponseTypeImpl();
        return deleteWorkListViewResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteWorkListViewType createDeleteWorkListViewType() {
        DeleteWorkListViewTypeImpl deleteWorkListViewType = new DeleteWorkListViewTypeImpl();
        return deleteWorkListViewType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EditWorkListViewResponseType createEditWorkListViewResponseType() {
        EditWorkListViewResponseTypeImpl editWorkListViewResponseType = new EditWorkListViewResponseTypeImpl();
        return editWorkListViewResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EditWorkListViewType createEditWorkListViewType() {
        EditWorkListViewTypeImpl editWorkListViewType = new EditWorkListViewTypeImpl();
        return editWorkListViewType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnableWorkItemResponseType createEnableWorkItemResponseType() {
        EnableWorkItemResponseTypeImpl enableWorkItemResponseType = new EnableWorkItemResponseTypeImpl();
        return enableWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnableWorkItemType createEnableWorkItemType() {
        EnableWorkItemTypeImpl enableWorkItemType = new EnableWorkItemTypeImpl();
        return enableWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EndGroupResponseType createEndGroupResponseType() {
        EndGroupResponseTypeImpl endGroupResponseType = new EndGroupResponseTypeImpl();
        return endGroupResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EndGroupType createEndGroupType() {
        EndGroupTypeImpl endGroupType = new EndGroupTypeImpl();
        return endGroupType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetAllocatedWorkListItemsType createGetAllocatedWorkListItemsType() {
        GetAllocatedWorkListItemsTypeImpl getAllocatedWorkListItemsType = new GetAllocatedWorkListItemsTypeImpl();
        return getAllocatedWorkListItemsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetBatchGroupIdsResponseType createGetBatchGroupIdsResponseType() {
        GetBatchGroupIdsResponseTypeImpl getBatchGroupIdsResponseType = new GetBatchGroupIdsResponseTypeImpl();
        return getBatchGroupIdsResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetBatchWorkItemIdsResponseType createGetBatchWorkItemIdsResponseType() {
        GetBatchWorkItemIdsResponseTypeImpl getBatchWorkItemIdsResponseType = new GetBatchWorkItemIdsResponseTypeImpl();
        return getBatchWorkItemIdsResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetEditableWorkListViewsResponseType createGetEditableWorkListViewsResponseType() {
        GetEditableWorkListViewsResponseTypeImpl getEditableWorkListViewsResponseType = new GetEditableWorkListViewsResponseTypeImpl();
        return getEditableWorkListViewsResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetEditableWorkListViewsType createGetEditableWorkListViewsType() {
        GetEditableWorkListViewsTypeImpl getEditableWorkListViewsType = new GetEditableWorkListViewsTypeImpl();
        return getEditableWorkListViewsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOfferSetResponseType createGetOfferSetResponseType() {
        GetOfferSetResponseTypeImpl getOfferSetResponseType = new GetOfferSetResponseTypeImpl();
        return getOfferSetResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOfferSetType createGetOfferSetType() {
        GetOfferSetTypeImpl getOfferSetType = new GetOfferSetTypeImpl();
        return getOfferSetType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOrgEntityConfigAttributesAvailableResponseType createGetOrgEntityConfigAttributesAvailableResponseType() {
        GetOrgEntityConfigAttributesAvailableResponseTypeImpl getOrgEntityConfigAttributesAvailableResponseType = new GetOrgEntityConfigAttributesAvailableResponseTypeImpl();
        return getOrgEntityConfigAttributesAvailableResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOrgEntityConfigAttributesAvailableType createGetOrgEntityConfigAttributesAvailableType() {
        GetOrgEntityConfigAttributesAvailableTypeImpl getOrgEntityConfigAttributesAvailableType = new GetOrgEntityConfigAttributesAvailableTypeImpl();
        return getOrgEntityConfigAttributesAvailableType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOrgEntityConfigAttributesResponseType createGetOrgEntityConfigAttributesResponseType() {
        GetOrgEntityConfigAttributesResponseTypeImpl getOrgEntityConfigAttributesResponseType = new GetOrgEntityConfigAttributesResponseTypeImpl();
        return getOrgEntityConfigAttributesResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOrgEntityConfigAttributesType createGetOrgEntityConfigAttributesType() {
        GetOrgEntityConfigAttributesTypeImpl getOrgEntityConfigAttributesType = new GetOrgEntityConfigAttributesTypeImpl();
        return getOrgEntityConfigAttributesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetPublicWorkListViewsResponseType createGetPublicWorkListViewsResponseType() {
        GetPublicWorkListViewsResponseTypeImpl getPublicWorkListViewsResponseType = new GetPublicWorkListViewsResponseTypeImpl();
        return getPublicWorkListViewsResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetPublicWorkListViewsType createGetPublicWorkListViewsType() {
        GetPublicWorkListViewsTypeImpl getPublicWorkListViewsType = new GetPublicWorkListViewsTypeImpl();
        return getPublicWorkListViewsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetResourceOrderFilterCriteriaResponseType createGetResourceOrderFilterCriteriaResponseType() {
        GetResourceOrderFilterCriteriaResponseTypeImpl getResourceOrderFilterCriteriaResponseType = new GetResourceOrderFilterCriteriaResponseTypeImpl();
        return getResourceOrderFilterCriteriaResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetResourceOrderFilterCriteriaType createGetResourceOrderFilterCriteriaType() {
        GetResourceOrderFilterCriteriaTypeImpl getResourceOrderFilterCriteriaType = new GetResourceOrderFilterCriteriaTypeImpl();
        return getResourceOrderFilterCriteriaType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetViewsForResourceResponseType createGetViewsForResourceResponseType() {
        GetViewsForResourceResponseTypeImpl getViewsForResourceResponseType = new GetViewsForResourceResponseTypeImpl();
        return getViewsForResourceResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetViewsForResourceType createGetViewsForResourceType() {
        GetViewsForResourceTypeImpl getViewsForResourceType = new GetViewsForResourceTypeImpl();
        return getViewsForResourceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkItemHeaderResponseType createGetWorkItemHeaderResponseType() {
        GetWorkItemHeaderResponseTypeImpl getWorkItemHeaderResponseType = new GetWorkItemHeaderResponseTypeImpl();
        return getWorkItemHeaderResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkItemHeaderType createGetWorkItemHeaderType() {
        GetWorkItemHeaderTypeImpl getWorkItemHeaderType = new GetWorkItemHeaderTypeImpl();
        return getWorkItemHeaderType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkItemOrderFilterResponseType createGetWorkItemOrderFilterResponseType() {
        GetWorkItemOrderFilterResponseTypeImpl getWorkItemOrderFilterResponseType = new GetWorkItemOrderFilterResponseTypeImpl();
        return getWorkItemOrderFilterResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkItemOrderFilterType createGetWorkItemOrderFilterType() {
        GetWorkItemOrderFilterTypeImpl getWorkItemOrderFilterType = new GetWorkItemOrderFilterTypeImpl();
        return getWorkItemOrderFilterType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsForGlobalDataResponseType createGetWorkListItemsForGlobalDataResponseType() {
        GetWorkListItemsForGlobalDataResponseTypeImpl getWorkListItemsForGlobalDataResponseType = new GetWorkListItemsForGlobalDataResponseTypeImpl();
        return getWorkListItemsForGlobalDataResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsForGlobalDataType createGetWorkListItemsForGlobalDataType() {
        GetWorkListItemsForGlobalDataTypeImpl getWorkListItemsForGlobalDataType = new GetWorkListItemsForGlobalDataTypeImpl();
        return getWorkListItemsForGlobalDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsForViewResponseType createGetWorkListItemsForViewResponseType() {
        GetWorkListItemsForViewResponseTypeImpl getWorkListItemsForViewResponseType = new GetWorkListItemsForViewResponseTypeImpl();
        return getWorkListItemsForViewResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsForViewType createGetWorkListItemsForViewType() {
        GetWorkListItemsForViewTypeImpl getWorkListItemsForViewType = new GetWorkListItemsForViewTypeImpl();
        return getWorkListItemsForViewType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsResponseType createGetWorkListItemsResponseType() {
        GetWorkListItemsResponseTypeImpl getWorkListItemsResponseType = new GetWorkListItemsResponseTypeImpl();
        return getWorkListItemsResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsResponseType1 createGetWorkListItemsResponseType1() {
        GetWorkListItemsResponseType1Impl getWorkListItemsResponseType1 = new GetWorkListItemsResponseType1Impl();
        return getWorkListItemsResponseType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsType createGetWorkListItemsType() {
        GetWorkListItemsTypeImpl getWorkListItemsType = new GetWorkListItemsTypeImpl();
        return getWorkListItemsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListViewDetailsType createGetWorkListViewDetailsType() {
        GetWorkListViewDetailsTypeImpl getWorkListViewDetailsType = new GetWorkListViewDetailsTypeImpl();
        return getWorkListViewDetailsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkModelResponseType createGetWorkModelResponseType() {
        GetWorkModelResponseTypeImpl getWorkModelResponseType = new GetWorkModelResponseTypeImpl();
        return getWorkModelResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkModelsResponseType createGetWorkModelsResponseType() {
        GetWorkModelsResponseTypeImpl getWorkModelsResponseType = new GetWorkModelsResponseTypeImpl();
        return getWorkModelsResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkModelsType createGetWorkModelsType() {
        GetWorkModelsTypeImpl getWorkModelsType = new GetWorkModelsTypeImpl();
        return getWorkModelsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkModelType createGetWorkModelType() {
        GetWorkModelTypeImpl getWorkModelType = new GetWorkModelTypeImpl();
        return getWorkModelType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkTypeResponseType createGetWorkTypeResponseType() {
        GetWorkTypeResponseTypeImpl getWorkTypeResponseType = new GetWorkTypeResponseTypeImpl();
        return getWorkTypeResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkTypesResponseType createGetWorkTypesResponseType() {
        GetWorkTypesResponseTypeImpl getWorkTypesResponseType = new GetWorkTypesResponseTypeImpl();
        return getWorkTypesResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkTypesType createGetWorkTypesType() {
        GetWorkTypesTypeImpl getWorkTypesType = new GetWorkTypesTypeImpl();
        return getWorkTypesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkTypeType createGetWorkTypeType() {
        GetWorkTypeTypeImpl getWorkTypeType = new GetWorkTypeTypeImpl();
        return getWorkTypeType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HiddenPeriod createHiddenPeriod() {
        HiddenPeriodImpl hiddenPeriod = new HiddenPeriodImpl();
        return hiddenPeriod;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Item createItem() {
        ItemImpl item = new ItemImpl();
        return item;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemBody createItemBody() {
        ItemBodyImpl itemBody = new ItemBodyImpl();
        return itemBody;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemContext createItemContext() {
        ItemContextImpl itemContext = new ItemContextImpl();
        return itemContext;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemDuration createItemDuration() {
        ItemDurationImpl itemDuration = new ItemDurationImpl();
        return itemDuration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemPrivilege createItemPrivilege() {
        ItemPrivilegeImpl itemPrivilege = new ItemPrivilegeImpl();
        return itemPrivilege;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemSchedule createItemSchedule() {
        ItemScheduleImpl itemSchedule = new ItemScheduleImpl();
        return itemSchedule;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ManagedObjectID createManagedObjectID() {
        ManagedObjectIDImpl managedObjectID = new ManagedObjectIDImpl();
        return managedObjectID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NextWorkItem createNextWorkItem() {
        NextWorkItemImpl nextWorkItem = new NextWorkItemImpl();
        return nextWorkItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ObjectID createObjectID() {
        ObjectIDImpl objectID = new ObjectIDImpl();
        return objectID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OnNotificationResponseType createOnNotificationResponseType() {
        OnNotificationResponseTypeImpl onNotificationResponseType = new OnNotificationResponseTypeImpl();
        return onNotificationResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OnNotificationType createOnNotificationType() {
        OnNotificationTypeImpl onNotificationType = new OnNotificationTypeImpl();
        return onNotificationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OpenWorkItemResponseType createOpenWorkItemResponseType() {
        OpenWorkItemResponseTypeImpl openWorkItemResponseType = new OpenWorkItemResponseTypeImpl();
        return openWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OpenWorkItemType createOpenWorkItemType() {
        OpenWorkItemTypeImpl openWorkItemType = new OpenWorkItemTypeImpl();
        return openWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrderFilterCriteria createOrderFilterCriteria() {
        OrderFilterCriteriaImpl orderFilterCriteria = new OrderFilterCriteriaImpl();
        return orderFilterCriteria;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrgEntityConfigAttribute createOrgEntityConfigAttribute() {
        OrgEntityConfigAttributeImpl orgEntityConfigAttribute = new OrgEntityConfigAttributeImpl();
        return orgEntityConfigAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrgEntityConfigAttributesAvailable createOrgEntityConfigAttributesAvailable() {
        OrgEntityConfigAttributesAvailableImpl orgEntityConfigAttributesAvailable = new OrgEntityConfigAttributesAvailableImpl();
        return orgEntityConfigAttributesAvailable;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrgEntityConfigAttributeSet createOrgEntityConfigAttributeSet() {
        OrgEntityConfigAttributeSetImpl orgEntityConfigAttributeSet = new OrgEntityConfigAttributeSetImpl();
        return orgEntityConfigAttributeSet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterType createParameterType() {
        ParameterTypeImpl parameterType = new ParameterTypeImpl();
        return parameterType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PendWorkItem createPendWorkItem() {
        PendWorkItemImpl pendWorkItem = new PendWorkItemImpl();
        return pendWorkItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PendWorkItemResponseType createPendWorkItemResponseType() {
        PendWorkItemResponseTypeImpl pendWorkItemResponseType = new PendWorkItemResponseTypeImpl();
        return pendWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PreviewWorkItemFromListResponseType createPreviewWorkItemFromListResponseType() {
        PreviewWorkItemFromListResponseTypeImpl previewWorkItemFromListResponseType = new PreviewWorkItemFromListResponseTypeImpl();
        return previewWorkItemFromListResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PreviewWorkItemFromListType createPreviewWorkItemFromListType() {
        PreviewWorkItemFromListTypeImpl previewWorkItemFromListType = new PreviewWorkItemFromListTypeImpl();
        return previewWorkItemFromListType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Privilege createPrivilege() {
        PrivilegeImpl privilege = new PrivilegeImpl();
        return privilege;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PushNotificationType createPushNotificationType() {
        PushNotificationTypeImpl pushNotificationType = new PushNotificationTypeImpl();
        return pushNotificationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReallocateWorkItemData createReallocateWorkItemData() {
        ReallocateWorkItemDataImpl reallocateWorkItemData = new ReallocateWorkItemDataImpl();
        return reallocateWorkItemData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReallocateWorkItemDataResponseType createReallocateWorkItemDataResponseType() {
        ReallocateWorkItemDataResponseTypeImpl reallocateWorkItemDataResponseType = new ReallocateWorkItemDataResponseTypeImpl();
        return reallocateWorkItemDataResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReallocateWorkItemResponseType createReallocateWorkItemResponseType() {
        ReallocateWorkItemResponseTypeImpl reallocateWorkItemResponseType = new ReallocateWorkItemResponseTypeImpl();
        return reallocateWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReallocateWorkItemType createReallocateWorkItemType() {
        ReallocateWorkItemTypeImpl reallocateWorkItemType = new ReallocateWorkItemTypeImpl();
        return reallocateWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RescheduleWorkItem createRescheduleWorkItem() {
        RescheduleWorkItemImpl rescheduleWorkItem = new RescheduleWorkItemImpl();
        return rescheduleWorkItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RescheduleWorkItemResponseType createRescheduleWorkItemResponseType() {
        RescheduleWorkItemResponseTypeImpl rescheduleWorkItemResponseType = new RescheduleWorkItemResponseTypeImpl();
        return rescheduleWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResumeWorkItemResponseType createResumeWorkItemResponseType() {
        ResumeWorkItemResponseTypeImpl resumeWorkItemResponseType = new ResumeWorkItemResponseTypeImpl();
        return resumeWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResumeWorkItemType createResumeWorkItemType() {
        ResumeWorkItemTypeImpl resumeWorkItemType = new ResumeWorkItemTypeImpl();
        return resumeWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SaveOpenWorkItemResponseType createSaveOpenWorkItemResponseType() {
        SaveOpenWorkItemResponseTypeImpl saveOpenWorkItemResponseType = new SaveOpenWorkItemResponseTypeImpl();
        return saveOpenWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SaveOpenWorkItemType createSaveOpenWorkItemType() {
        SaveOpenWorkItemTypeImpl saveOpenWorkItemType = new SaveOpenWorkItemTypeImpl();
        return saveOpenWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemResponseType createScheduleWorkItemResponseType() {
        ScheduleWorkItemResponseTypeImpl scheduleWorkItemResponseType = new ScheduleWorkItemResponseTypeImpl();
        return scheduleWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemType createScheduleWorkItemType() {
        ScheduleWorkItemTypeImpl scheduleWorkItemType = new ScheduleWorkItemTypeImpl();
        return scheduleWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemWithModelResponseType createScheduleWorkItemWithModelResponseType() {
        ScheduleWorkItemWithModelResponseTypeImpl scheduleWorkItemWithModelResponseType = new ScheduleWorkItemWithModelResponseTypeImpl();
        return scheduleWorkItemWithModelResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemWithModelType createScheduleWorkItemWithModelType() {
        ScheduleWorkItemWithModelTypeImpl scheduleWorkItemWithModelType = new ScheduleWorkItemWithModelTypeImpl();
        return scheduleWorkItemWithModelType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetOrgEntityConfigAttributesResponseType createSetOrgEntityConfigAttributesResponseType() {
        SetOrgEntityConfigAttributesResponseTypeImpl setOrgEntityConfigAttributesResponseType = new SetOrgEntityConfigAttributesResponseTypeImpl();
        return setOrgEntityConfigAttributesResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetOrgEntityConfigAttributesType createSetOrgEntityConfigAttributesType() {
        SetOrgEntityConfigAttributesTypeImpl setOrgEntityConfigAttributesType = new SetOrgEntityConfigAttributesTypeImpl();
        return setOrgEntityConfigAttributesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetResourceOrderFilterCriteriaResponseType createSetResourceOrderFilterCriteriaResponseType() {
        SetResourceOrderFilterCriteriaResponseTypeImpl setResourceOrderFilterCriteriaResponseType = new SetResourceOrderFilterCriteriaResponseTypeImpl();
        return setResourceOrderFilterCriteriaResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetResourceOrderFilterCriteriaType createSetResourceOrderFilterCriteriaType() {
        SetResourceOrderFilterCriteriaTypeImpl setResourceOrderFilterCriteriaType = new SetResourceOrderFilterCriteriaTypeImpl();
        return setResourceOrderFilterCriteriaType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetWorkItemPriority createSetWorkItemPriority() {
        SetWorkItemPriorityImpl setWorkItemPriority = new SetWorkItemPriorityImpl();
        return setWorkItemPriority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetWorkItemPriorityResponseType createSetWorkItemPriorityResponseType() {
        SetWorkItemPriorityResponseTypeImpl setWorkItemPriorityResponseType = new SetWorkItemPriorityResponseTypeImpl();
        return setWorkItemPriorityResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SkipWorkItemResponseType createSkipWorkItemResponseType() {
        SkipWorkItemResponseTypeImpl skipWorkItemResponseType = new SkipWorkItemResponseTypeImpl();
        return skipWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SkipWorkItemType createSkipWorkItemType() {
        SkipWorkItemTypeImpl skipWorkItemType = new SkipWorkItemTypeImpl();
        return skipWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public StartGroupResponseType createStartGroupResponseType() {
        StartGroupResponseTypeImpl startGroupResponseType = new StartGroupResponseTypeImpl();
        return startGroupResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public StartGroupType createStartGroupType() {
        StartGroupTypeImpl startGroupType = new StartGroupTypeImpl();
        return startGroupType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SuspendWorkItemResponseType createSuspendWorkItemResponseType() {
        SuspendWorkItemResponseTypeImpl suspendWorkItemResponseType = new SuspendWorkItemResponseTypeImpl();
        return suspendWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SuspendWorkItemType createSuspendWorkItemType() {
        SuspendWorkItemTypeImpl suspendWorkItemType = new SuspendWorkItemTypeImpl();
        return suspendWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnallocateWorkItemResponseType createUnallocateWorkItemResponseType() {
        UnallocateWorkItemResponseTypeImpl unallocateWorkItemResponseType = new UnallocateWorkItemResponseTypeImpl();
        return unallocateWorkItemResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnallocateWorkItemType createUnallocateWorkItemType() {
        UnallocateWorkItemTypeImpl unallocateWorkItemType = new UnallocateWorkItemTypeImpl();
        return unallocateWorkItemType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnlockWorkListViewResponseType createUnlockWorkListViewResponseType() {
        UnlockWorkListViewResponseTypeImpl unlockWorkListViewResponseType = new UnlockWorkListViewResponseTypeImpl();
        return unlockWorkListViewResponseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnlockWorkListViewType createUnlockWorkListViewType() {
        UnlockWorkListViewTypeImpl unlockWorkListViewType = new UnlockWorkListViewTypeImpl();
        return unlockWorkListViewType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItem createWorkItem() {
        WorkItemImpl workItem = new WorkItemImpl();
        return workItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemAttributes createWorkItemAttributes() {
        WorkItemAttributesImpl workItemAttributes = new WorkItemAttributesImpl();
        return workItemAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemBody createWorkItemBody() {
        WorkItemBodyImpl workItemBody = new WorkItemBodyImpl();
        return workItemBody;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemFlags createWorkItemFlags() {
        WorkItemFlagsImpl workItemFlags = new WorkItemFlagsImpl();
        return workItemFlags;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemHeader createWorkItemHeader() {
        WorkItemHeaderImpl workItemHeader = new WorkItemHeaderImpl();
        return workItemHeader;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemIDandPriorityType createWorkItemIDandPriorityType() {
        WorkItemIDandPriorityTypeImpl workItemIDandPriorityType = new WorkItemIDandPriorityTypeImpl();
        return workItemIDandPriorityType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemPreview createWorkItemPreview() {
        WorkItemPreviewImpl workItemPreview = new WorkItemPreviewImpl();
        return workItemPreview;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemPriorityType createWorkItemPriorityType() {
        WorkItemPriorityTypeImpl workItemPriorityType = new WorkItemPriorityTypeImpl();
        return workItemPriorityType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkListView createWorkListView() {
        WorkListViewImpl workListView = new WorkListViewImpl();
        return workListView;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkListViewCommon createWorkListViewCommon() {
        WorkListViewCommonImpl workListViewCommon = new WorkListViewCommonImpl();
        return workListViewCommon;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkListViewEdit createWorkListViewEdit() {
        WorkListViewEditImpl workListViewEdit = new WorkListViewEditImpl();
        return workListViewEdit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkListViewPageItem createWorkListViewPageItem() {
        WorkListViewPageItemImpl workListViewPageItem = new WorkListViewPageItemImpl();
        return workListViewPageItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModel createWorkModel() {
        WorkModelImpl workModel = new WorkModelImpl();
        return workModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelEntities createWorkModelEntities() {
        WorkModelEntitiesImpl workModelEntities = new WorkModelEntitiesImpl();
        return workModelEntities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelEntity createWorkModelEntity() {
        WorkModelEntityImpl workModelEntity = new WorkModelEntityImpl();
        return workModelEntity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelList createWorkModelList() {
        WorkModelListImpl workModelList = new WorkModelListImpl();
        return workModelList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelMapping createWorkModelMapping() {
        WorkModelMappingImpl workModelMapping = new WorkModelMappingImpl();
        return workModelMapping;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelScript createWorkModelScript() {
        WorkModelScriptImpl workModelScript = new WorkModelScriptImpl();
        return workModelScript;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelScripts createWorkModelScripts() {
        WorkModelScriptsImpl workModelScripts = new WorkModelScriptsImpl();
        return workModelScripts;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelSpecification createWorkModelSpecification() {
        WorkModelSpecificationImpl workModelSpecification = new WorkModelSpecificationImpl();
        return workModelSpecification;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelType createWorkModelType() {
        WorkModelTypeImpl workModelType = new WorkModelTypeImpl();
        return workModelType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelTypes createWorkModelTypes() {
        WorkModelTypesImpl workModelTypes = new WorkModelTypesImpl();
        return workModelTypes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeList createWorkTypeList() {
        WorkTypeListImpl workTypeList = new WorkTypeListImpl();
        return workTypeList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ColumnCapability createColumnCapabilityFromString(EDataType eDataType, String initialValue) {
        ColumnCapability result = ColumnCapability.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertColumnCapabilityToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ColumnType createColumnTypeFromString(EDataType eDataType, String initialValue) {
        ColumnType result = ColumnType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertColumnTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DistributionStrategy createDistributionStrategyFromString(EDataType eDataType, String initialValue) {
        DistributionStrategy result = DistributionStrategy.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDistributionStrategyToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MethodAuthorisationAction createMethodAuthorisationActionFromString(EDataType eDataType, String initialValue) {
        MethodAuthorisationAction result = MethodAuthorisationAction.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMethodAuthorisationActionToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MethodAuthorisationComponent createMethodAuthorisationComponentFromString(EDataType eDataType, String initialValue) {
        MethodAuthorisationComponent result = MethodAuthorisationComponent.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMethodAuthorisationComponentToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourcesRequiredType createResourcesRequiredTypeFromString(EDataType eDataType, String initialValue) {
        ResourcesRequiredType result = ResourcesRequiredType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertResourcesRequiredTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleStatus createScheduleStatusFromString(EDataType eDataType, String initialValue) {
        ScheduleStatus result = ScheduleStatus.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertScheduleStatusToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkGroupType createWorkGroupTypeFromString(EDataType eDataType, String initialValue) {
        WorkGroupType result = WorkGroupType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertWorkGroupTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemScriptOperation createWorkItemScriptOperationFromString(EDataType eDataType, String initialValue) {
        WorkItemScriptOperation result = WorkItemScriptOperation.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertWorkItemScriptOperationToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemScriptType createWorkItemScriptTypeFromString(EDataType eDataType, String initialValue) {
        WorkItemScriptType result = WorkItemScriptType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertWorkItemScriptTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemState createWorkItemStateFromString(EDataType eDataType, String initialValue) {
        WorkItemState result = WorkItemState.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertWorkItemStateToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute10TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute10TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute11TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute11TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute12TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute12TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute13TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute13TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute14TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute14TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute21TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute21TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute22TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute22TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute23TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute23TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute24TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute24TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute25TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute25TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute26TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute26TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute27TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute27TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute28TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute28TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute29TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute29TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute2TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute2TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute30TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute30TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute31TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute31TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute32TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute32TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute33TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute33TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute34TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute34TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute35TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute35TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute36TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute36TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute37TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute37TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute38TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute38TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute39TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute39TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute3TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute3TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute40TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute40TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute4TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute4TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute8TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute8TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createAttribute9TypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttribute9TypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ColumnCapability createColumnCapabilityObjectFromString(EDataType eDataType, String initialValue) {
        return createColumnCapabilityFromString(N2BRMPackage.Literals.COLUMN_CAPABILITY, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertColumnCapabilityObjectToString(EDataType eDataType, Object instanceValue) {
        return convertColumnCapabilityToString(N2BRMPackage.Literals.COLUMN_CAPABILITY, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ColumnType createColumnTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createColumnTypeFromString(N2BRMPackage.Literals.COLUMN_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertColumnTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertColumnTypeToString(N2BRMPackage.Literals.COLUMN_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createDescriptionTypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDescriptionTypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DistributionStrategy createDistributionStrategyObjectFromString(EDataType eDataType, String initialValue) {
        return createDistributionStrategyFromString(N2BRMPackage.Literals.DISTRIBUTION_STRATEGY, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDistributionStrategyObjectToString(EDataType eDataType, Object instanceValue) {
        return convertDistributionStrategyToString(N2BRMPackage.Literals.DISTRIBUTION_STRATEGY, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createLockerTypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertLockerTypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MethodAuthorisationAction createMethodAuthorisationActionObjectFromString(EDataType eDataType, String initialValue) {
        return createMethodAuthorisationActionFromString(N2BRMPackage.Literals.METHOD_AUTHORISATION_ACTION, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMethodAuthorisationActionObjectToString(EDataType eDataType, Object instanceValue) {
        return convertMethodAuthorisationActionToString(N2BRMPackage.Literals.METHOD_AUTHORISATION_ACTION, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MethodAuthorisationComponent createMethodAuthorisationComponentObjectFromString(EDataType eDataType, String initialValue) {
        return createMethodAuthorisationComponentFromString(N2BRMPackage.Literals.METHOD_AUTHORISATION_COMPONENT, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMethodAuthorisationComponentObjectToString(EDataType eDataType, Object instanceValue) {
        return convertMethodAuthorisationComponentToString(N2BRMPackage.Literals.METHOD_AUTHORISATION_COMPONENT, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createNameTypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertNameTypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createOwnerTypeFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertOwnerTypeToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourcesRequiredType createResourcesRequiredTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createResourcesRequiredTypeFromString(N2BRMPackage.Literals.RESOURCES_REQUIRED_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertResourcesRequiredTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertResourcesRequiredTypeToString(N2BRMPackage.Literals.RESOURCES_REQUIRED_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleStatus createScheduleStatusObjectFromString(EDataType eDataType, String initialValue) {
        return createScheduleStatusFromString(N2BRMPackage.Literals.SCHEDULE_STATUS, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertScheduleStatusObjectToString(EDataType eDataType, Object instanceValue) {
        return convertScheduleStatusToString(N2BRMPackage.Literals.SCHEDULE_STATUS, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemScriptOperation createScriptOperationTypeFromString(EDataType eDataType, String initialValue) {
        return createWorkItemScriptOperationFromString(N2BRMPackage.Literals.WORK_ITEM_SCRIPT_OPERATION, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertScriptOperationTypeToString(EDataType eDataType, Object instanceValue) {
        return convertWorkItemScriptOperationToString(N2BRMPackage.Literals.WORK_ITEM_SCRIPT_OPERATION, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkGroupType createWorkGroupTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createWorkGroupTypeFromString(N2BRMPackage.Literals.WORK_GROUP_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertWorkGroupTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertWorkGroupTypeToString(N2BRMPackage.Literals.WORK_GROUP_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemScriptOperation createWorkItemScriptOperationObjectFromString(EDataType eDataType, String initialValue) {
        return createWorkItemScriptOperationFromString(N2BRMPackage.Literals.WORK_ITEM_SCRIPT_OPERATION, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertWorkItemScriptOperationObjectToString(EDataType eDataType, Object instanceValue) {
        return convertWorkItemScriptOperationToString(N2BRMPackage.Literals.WORK_ITEM_SCRIPT_OPERATION, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemScriptType createWorkItemScriptTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createWorkItemScriptTypeFromString(N2BRMPackage.Literals.WORK_ITEM_SCRIPT_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertWorkItemScriptTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertWorkItemScriptTypeToString(N2BRMPackage.Literals.WORK_ITEM_SCRIPT_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemState createWorkItemStateObjectFromString(EDataType eDataType, String initialValue) {
        return createWorkItemStateFromString(N2BRMPackage.Literals.WORK_ITEM_STATE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertWorkItemStateObjectToString(EDataType eDataType, Object instanceValue) {
        return convertWorkItemStateToString(N2BRMPackage.Literals.WORK_ITEM_STATE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public N2BRMPackage getN2BRMPackage() {
        return (N2BRMPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static N2BRMPackage getPackage() {
        return N2BRMPackage.eINSTANCE;
    }

} //N2BRMFactoryImpl
