/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.util;

import com.tibco.n2.brm.api.*;

import com.tibco.n2.common.datamodel.DataModel;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage
 * @generated
 */
public class N2BRMAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static N2BRMPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public N2BRMAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = N2BRMPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected N2BRMSwitch<Adapter> modelSwitch =
        new N2BRMSwitch<Adapter>() {
            @Override
            public Adapter caseAddCurrentResourceToViewResponseType(AddCurrentResourceToViewResponseType object) {
                return createAddCurrentResourceToViewResponseTypeAdapter();
            }
            @Override
            public Adapter caseAddCurrentResourceToViewType(AddCurrentResourceToViewType object) {
                return createAddCurrentResourceToViewTypeAdapter();
            }
            @Override
            public Adapter caseAllocateAndOpenNextWorkItemResponseType(AllocateAndOpenNextWorkItemResponseType object) {
                return createAllocateAndOpenNextWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseAllocateAndOpenNextWorkItemType(AllocateAndOpenNextWorkItemType object) {
                return createAllocateAndOpenNextWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseAllocateAndOpenWorkItemResponseType(AllocateAndOpenWorkItemResponseType object) {
                return createAllocateAndOpenWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseAllocateAndOpenWorkItemType(AllocateAndOpenWorkItemType object) {
                return createAllocateAndOpenWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseAllocateWorkItemResponseType(AllocateWorkItemResponseType object) {
                return createAllocateWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseAllocateWorkItemType(AllocateWorkItemType object) {
                return createAllocateWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseAllocationHistory(AllocationHistory object) {
                return createAllocationHistoryAdapter();
            }
            @Override
            public Adapter caseAsyncCancelWorkItemResponseType(AsyncCancelWorkItemResponseType object) {
                return createAsyncCancelWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseAsyncCancelWorkItemType(AsyncCancelWorkItemType object) {
                return createAsyncCancelWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseAsyncEndGroupResponseType(AsyncEndGroupResponseType object) {
                return createAsyncEndGroupResponseTypeAdapter();
            }
            @Override
            public Adapter caseAsyncEndGroupType(AsyncEndGroupType object) {
                return createAsyncEndGroupTypeAdapter();
            }
            @Override
            public Adapter caseAsyncRescheduleWorkItemResponseType(AsyncRescheduleWorkItemResponseType object) {
                return createAsyncRescheduleWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseAsyncRescheduleWorkItemType(AsyncRescheduleWorkItemType object) {
                return createAsyncRescheduleWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseAsyncResumeWorkItemResponseType(AsyncResumeWorkItemResponseType object) {
                return createAsyncResumeWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseAsyncResumeWorkItemType(AsyncResumeWorkItemType object) {
                return createAsyncResumeWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseAsyncScheduleWorkItemResponseType(AsyncScheduleWorkItemResponseType object) {
                return createAsyncScheduleWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseAsyncScheduleWorkItemType(AsyncScheduleWorkItemType object) {
                return createAsyncScheduleWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseAsyncScheduleWorkItemWithModelResponseType(AsyncScheduleWorkItemWithModelResponseType object) {
                return createAsyncScheduleWorkItemWithModelResponseTypeAdapter();
            }
            @Override
            public Adapter caseAsyncScheduleWorkItemWithModelType(AsyncScheduleWorkItemWithModelType object) {
                return createAsyncScheduleWorkItemWithModelTypeAdapter();
            }
            @Override
            public Adapter caseAsyncStartGroupResponseType(AsyncStartGroupResponseType object) {
                return createAsyncStartGroupResponseTypeAdapter();
            }
            @Override
            public Adapter caseAsyncStartGroupType(AsyncStartGroupType object) {
                return createAsyncStartGroupTypeAdapter();
            }
            @Override
            public Adapter caseAsyncSuspendWorkItemResponseType(AsyncSuspendWorkItemResponseType object) {
                return createAsyncSuspendWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseAsyncSuspendWorkItemType(AsyncSuspendWorkItemType object) {
                return createAsyncSuspendWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseAsyncWorkItemDetails(AsyncWorkItemDetails object) {
                return createAsyncWorkItemDetailsAdapter();
            }
            @Override
            public Adapter caseAttributeAliasListType(AttributeAliasListType object) {
                return createAttributeAliasListTypeAdapter();
            }
            @Override
            public Adapter caseBaseItemInfo(BaseItemInfo object) {
                return createBaseItemInfoAdapter();
            }
            @Override
            public Adapter caseBaseModelInfo(BaseModelInfo object) {
                return createBaseModelInfoAdapter();
            }
            @Override
            public Adapter caseCancelWorkItemResponseType(CancelWorkItemResponseType object) {
                return createCancelWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseCancelWorkItemType(CancelWorkItemType object) {
                return createCancelWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseChainedWorkItemNotificationType(ChainedWorkItemNotificationType object) {
                return createChainedWorkItemNotificationTypeAdapter();
            }
            @Override
            public Adapter caseCloseWorkItemResponseType(CloseWorkItemResponseType object) {
                return createCloseWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseCloseWorkItemType(CloseWorkItemType object) {
                return createCloseWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseColumnDefinition(ColumnDefinition object) {
                return createColumnDefinitionAdapter();
            }
            @Override
            public Adapter caseColumnOrder(ColumnOrder object) {
                return createColumnOrderAdapter();
            }
            @Override
            public Adapter caseCompleteWorkItemResponseType(CompleteWorkItemResponseType object) {
                return createCompleteWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseCompleteWorkItemType(CompleteWorkItemType object) {
                return createCompleteWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseCreateWorkListViewResponseType(CreateWorkListViewResponseType object) {
                return createCreateWorkListViewResponseTypeAdapter();
            }
            @Override
            public Adapter caseDeleteCurrentResourceFromViewResponseType(DeleteCurrentResourceFromViewResponseType object) {
                return createDeleteCurrentResourceFromViewResponseTypeAdapter();
            }
            @Override
            public Adapter caseDeleteCurrentResourceFromViewType(DeleteCurrentResourceFromViewType object) {
                return createDeleteCurrentResourceFromViewTypeAdapter();
            }
            @Override
            public Adapter caseDeleteOrgEntityConfigAttributesResponseType(DeleteOrgEntityConfigAttributesResponseType object) {
                return createDeleteOrgEntityConfigAttributesResponseTypeAdapter();
            }
            @Override
            public Adapter caseDeleteOrgEntityConfigAttributesType(DeleteOrgEntityConfigAttributesType object) {
                return createDeleteOrgEntityConfigAttributesTypeAdapter();
            }
            @Override
            public Adapter caseDeleteWorkListViewResponseType(DeleteWorkListViewResponseType object) {
                return createDeleteWorkListViewResponseTypeAdapter();
            }
            @Override
            public Adapter caseDeleteWorkListViewType(DeleteWorkListViewType object) {
                return createDeleteWorkListViewTypeAdapter();
            }
            @Override
            public Adapter caseDocumentRoot(DocumentRoot object) {
                return createDocumentRootAdapter();
            }
            @Override
            public Adapter caseEditWorkListViewResponseType(EditWorkListViewResponseType object) {
                return createEditWorkListViewResponseTypeAdapter();
            }
            @Override
            public Adapter caseEditWorkListViewType(EditWorkListViewType object) {
                return createEditWorkListViewTypeAdapter();
            }
            @Override
            public Adapter caseEnableWorkItemResponseType(EnableWorkItemResponseType object) {
                return createEnableWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseEnableWorkItemType(EnableWorkItemType object) {
                return createEnableWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseEndGroupResponseType(EndGroupResponseType object) {
                return createEndGroupResponseTypeAdapter();
            }
            @Override
            public Adapter caseEndGroupType(EndGroupType object) {
                return createEndGroupTypeAdapter();
            }
            @Override
            public Adapter caseGetAllocatedWorkListItemsType(GetAllocatedWorkListItemsType object) {
                return createGetAllocatedWorkListItemsTypeAdapter();
            }
            @Override
            public Adapter caseGetBatchGroupIdsResponseType(GetBatchGroupIdsResponseType object) {
                return createGetBatchGroupIdsResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetBatchWorkItemIdsResponseType(GetBatchWorkItemIdsResponseType object) {
                return createGetBatchWorkItemIdsResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetEditableWorkListViewsResponseType(GetEditableWorkListViewsResponseType object) {
                return createGetEditableWorkListViewsResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetEditableWorkListViewsType(GetEditableWorkListViewsType object) {
                return createGetEditableWorkListViewsTypeAdapter();
            }
            @Override
            public Adapter caseGetOfferSetResponseType(GetOfferSetResponseType object) {
                return createGetOfferSetResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetOfferSetType(GetOfferSetType object) {
                return createGetOfferSetTypeAdapter();
            }
            @Override
            public Adapter caseGetOrgEntityConfigAttributesAvailableResponseType(GetOrgEntityConfigAttributesAvailableResponseType object) {
                return createGetOrgEntityConfigAttributesAvailableResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetOrgEntityConfigAttributesAvailableType(GetOrgEntityConfigAttributesAvailableType object) {
                return createGetOrgEntityConfigAttributesAvailableTypeAdapter();
            }
            @Override
            public Adapter caseGetOrgEntityConfigAttributesResponseType(GetOrgEntityConfigAttributesResponseType object) {
                return createGetOrgEntityConfigAttributesResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetOrgEntityConfigAttributesType(GetOrgEntityConfigAttributesType object) {
                return createGetOrgEntityConfigAttributesTypeAdapter();
            }
            @Override
            public Adapter caseGetPublicWorkListViewsResponseType(GetPublicWorkListViewsResponseType object) {
                return createGetPublicWorkListViewsResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetPublicWorkListViewsType(GetPublicWorkListViewsType object) {
                return createGetPublicWorkListViewsTypeAdapter();
            }
            @Override
            public Adapter caseGetResourceOrderFilterCriteriaResponseType(GetResourceOrderFilterCriteriaResponseType object) {
                return createGetResourceOrderFilterCriteriaResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetResourceOrderFilterCriteriaType(GetResourceOrderFilterCriteriaType object) {
                return createGetResourceOrderFilterCriteriaTypeAdapter();
            }
            @Override
            public Adapter caseGetViewsForResourceResponseType(GetViewsForResourceResponseType object) {
                return createGetViewsForResourceResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetViewsForResourceType(GetViewsForResourceType object) {
                return createGetViewsForResourceTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkItemHeaderResponseType(GetWorkItemHeaderResponseType object) {
                return createGetWorkItemHeaderResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkItemHeaderType(GetWorkItemHeaderType object) {
                return createGetWorkItemHeaderTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkItemOrderFilterResponseType(GetWorkItemOrderFilterResponseType object) {
                return createGetWorkItemOrderFilterResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkItemOrderFilterType(GetWorkItemOrderFilterType object) {
                return createGetWorkItemOrderFilterTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkListItemsForGlobalDataResponseType(GetWorkListItemsForGlobalDataResponseType object) {
                return createGetWorkListItemsForGlobalDataResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkListItemsForGlobalDataType(GetWorkListItemsForGlobalDataType object) {
                return createGetWorkListItemsForGlobalDataTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkListItemsForViewResponseType(GetWorkListItemsForViewResponseType object) {
                return createGetWorkListItemsForViewResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkListItemsForViewType(GetWorkListItemsForViewType object) {
                return createGetWorkListItemsForViewTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkListItemsResponseType(GetWorkListItemsResponseType object) {
                return createGetWorkListItemsResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkListItemsResponseType1(GetWorkListItemsResponseType1 object) {
                return createGetWorkListItemsResponseType1Adapter();
            }
            @Override
            public Adapter caseGetWorkListItemsType(GetWorkListItemsType object) {
                return createGetWorkListItemsTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkListViewDetailsType(GetWorkListViewDetailsType object) {
                return createGetWorkListViewDetailsTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkModelResponseType(GetWorkModelResponseType object) {
                return createGetWorkModelResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkModelsResponseType(GetWorkModelsResponseType object) {
                return createGetWorkModelsResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkModelsType(GetWorkModelsType object) {
                return createGetWorkModelsTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkModelType(GetWorkModelType object) {
                return createGetWorkModelTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkTypeResponseType(GetWorkTypeResponseType object) {
                return createGetWorkTypeResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkTypesResponseType(GetWorkTypesResponseType object) {
                return createGetWorkTypesResponseTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkTypesType(GetWorkTypesType object) {
                return createGetWorkTypesTypeAdapter();
            }
            @Override
            public Adapter caseGetWorkTypeType(GetWorkTypeType object) {
                return createGetWorkTypeTypeAdapter();
            }
            @Override
            public Adapter caseHiddenPeriod(HiddenPeriod object) {
                return createHiddenPeriodAdapter();
            }
            @Override
            public Adapter caseItem(Item object) {
                return createItemAdapter();
            }
            @Override
            public Adapter caseItemBody(ItemBody object) {
                return createItemBodyAdapter();
            }
            @Override
            public Adapter caseItemContext(ItemContext object) {
                return createItemContextAdapter();
            }
            @Override
            public Adapter caseItemDuration(ItemDuration object) {
                return createItemDurationAdapter();
            }
            @Override
            public Adapter caseItemPrivilege(ItemPrivilege object) {
                return createItemPrivilegeAdapter();
            }
            @Override
            public Adapter caseItemSchedule(ItemSchedule object) {
                return createItemScheduleAdapter();
            }
            @Override
            public Adapter caseManagedObjectID(ManagedObjectID object) {
                return createManagedObjectIDAdapter();
            }
            @Override
            public Adapter caseNextWorkItem(NextWorkItem object) {
                return createNextWorkItemAdapter();
            }
            @Override
            public Adapter caseObjectID(ObjectID object) {
                return createObjectIDAdapter();
            }
            @Override
            public Adapter caseOnNotificationResponseType(OnNotificationResponseType object) {
                return createOnNotificationResponseTypeAdapter();
            }
            @Override
            public Adapter caseOnNotificationType(OnNotificationType object) {
                return createOnNotificationTypeAdapter();
            }
            @Override
            public Adapter caseOpenWorkItemResponseType(OpenWorkItemResponseType object) {
                return createOpenWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseOpenWorkItemType(OpenWorkItemType object) {
                return createOpenWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseOrderFilterCriteria(OrderFilterCriteria object) {
                return createOrderFilterCriteriaAdapter();
            }
            @Override
            public Adapter caseOrgEntityConfigAttribute(OrgEntityConfigAttribute object) {
                return createOrgEntityConfigAttributeAdapter();
            }
            @Override
            public Adapter caseOrgEntityConfigAttributesAvailable(OrgEntityConfigAttributesAvailable object) {
                return createOrgEntityConfigAttributesAvailableAdapter();
            }
            @Override
            public Adapter caseOrgEntityConfigAttributeSet(OrgEntityConfigAttributeSet object) {
                return createOrgEntityConfigAttributeSetAdapter();
            }
            @Override
            public Adapter caseParameterType(ParameterType object) {
                return createParameterTypeAdapter();
            }
            @Override
            public Adapter casePendWorkItem(PendWorkItem object) {
                return createPendWorkItemAdapter();
            }
            @Override
            public Adapter casePendWorkItemResponseType(PendWorkItemResponseType object) {
                return createPendWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter casePreviewWorkItemFromListResponseType(PreviewWorkItemFromListResponseType object) {
                return createPreviewWorkItemFromListResponseTypeAdapter();
            }
            @Override
            public Adapter casePreviewWorkItemFromListType(PreviewWorkItemFromListType object) {
                return createPreviewWorkItemFromListTypeAdapter();
            }
            @Override
            public Adapter casePrivilege(Privilege object) {
                return createPrivilegeAdapter();
            }
            @Override
            public Adapter casePushNotificationType(PushNotificationType object) {
                return createPushNotificationTypeAdapter();
            }
            @Override
            public Adapter caseReallocateWorkItemData(ReallocateWorkItemData object) {
                return createReallocateWorkItemDataAdapter();
            }
            @Override
            public Adapter caseReallocateWorkItemDataResponseType(ReallocateWorkItemDataResponseType object) {
                return createReallocateWorkItemDataResponseTypeAdapter();
            }
            @Override
            public Adapter caseReallocateWorkItemResponseType(ReallocateWorkItemResponseType object) {
                return createReallocateWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseReallocateWorkItemType(ReallocateWorkItemType object) {
                return createReallocateWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseRescheduleWorkItem(RescheduleWorkItem object) {
                return createRescheduleWorkItemAdapter();
            }
            @Override
            public Adapter caseRescheduleWorkItemResponseType(RescheduleWorkItemResponseType object) {
                return createRescheduleWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseResumeWorkItemResponseType(ResumeWorkItemResponseType object) {
                return createResumeWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseResumeWorkItemType(ResumeWorkItemType object) {
                return createResumeWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseSaveOpenWorkItemResponseType(SaveOpenWorkItemResponseType object) {
                return createSaveOpenWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseSaveOpenWorkItemType(SaveOpenWorkItemType object) {
                return createSaveOpenWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseScheduleWorkItemResponseType(ScheduleWorkItemResponseType object) {
                return createScheduleWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseScheduleWorkItemType(ScheduleWorkItemType object) {
                return createScheduleWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseScheduleWorkItemWithModelResponseType(ScheduleWorkItemWithModelResponseType object) {
                return createScheduleWorkItemWithModelResponseTypeAdapter();
            }
            @Override
            public Adapter caseScheduleWorkItemWithModelType(ScheduleWorkItemWithModelType object) {
                return createScheduleWorkItemWithModelTypeAdapter();
            }
            @Override
            public Adapter caseSetOrgEntityConfigAttributesResponseType(SetOrgEntityConfigAttributesResponseType object) {
                return createSetOrgEntityConfigAttributesResponseTypeAdapter();
            }
            @Override
            public Adapter caseSetOrgEntityConfigAttributesType(SetOrgEntityConfigAttributesType object) {
                return createSetOrgEntityConfigAttributesTypeAdapter();
            }
            @Override
            public Adapter caseSetResourceOrderFilterCriteriaResponseType(SetResourceOrderFilterCriteriaResponseType object) {
                return createSetResourceOrderFilterCriteriaResponseTypeAdapter();
            }
            @Override
            public Adapter caseSetResourceOrderFilterCriteriaType(SetResourceOrderFilterCriteriaType object) {
                return createSetResourceOrderFilterCriteriaTypeAdapter();
            }
            @Override
            public Adapter caseSetWorkItemPriority(SetWorkItemPriority object) {
                return createSetWorkItemPriorityAdapter();
            }
            @Override
            public Adapter caseSetWorkItemPriorityResponseType(SetWorkItemPriorityResponseType object) {
                return createSetWorkItemPriorityResponseTypeAdapter();
            }
            @Override
            public Adapter caseSkipWorkItemResponseType(SkipWorkItemResponseType object) {
                return createSkipWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseSkipWorkItemType(SkipWorkItemType object) {
                return createSkipWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseStartGroupResponseType(StartGroupResponseType object) {
                return createStartGroupResponseTypeAdapter();
            }
            @Override
            public Adapter caseStartGroupType(StartGroupType object) {
                return createStartGroupTypeAdapter();
            }
            @Override
            public Adapter caseSuspendWorkItemResponseType(SuspendWorkItemResponseType object) {
                return createSuspendWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseSuspendWorkItemType(SuspendWorkItemType object) {
                return createSuspendWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseUnallocateWorkItemResponseType(UnallocateWorkItemResponseType object) {
                return createUnallocateWorkItemResponseTypeAdapter();
            }
            @Override
            public Adapter caseUnallocateWorkItemType(UnallocateWorkItemType object) {
                return createUnallocateWorkItemTypeAdapter();
            }
            @Override
            public Adapter caseUnlockWorkListViewResponseType(UnlockWorkListViewResponseType object) {
                return createUnlockWorkListViewResponseTypeAdapter();
            }
            @Override
            public Adapter caseUnlockWorkListViewType(UnlockWorkListViewType object) {
                return createUnlockWorkListViewTypeAdapter();
            }
            @Override
            public Adapter caseWorkItem(WorkItem object) {
                return createWorkItemAdapter();
            }
            @Override
            public Adapter caseWorkItemAttributes(WorkItemAttributes object) {
                return createWorkItemAttributesAdapter();
            }
            @Override
            public Adapter caseWorkItemBody(WorkItemBody object) {
                return createWorkItemBodyAdapter();
            }
            @Override
            public Adapter caseWorkItemFlags(WorkItemFlags object) {
                return createWorkItemFlagsAdapter();
            }
            @Override
            public Adapter caseWorkItemHeader(WorkItemHeader object) {
                return createWorkItemHeaderAdapter();
            }
            @Override
            public Adapter caseWorkItemIDandPriorityType(WorkItemIDandPriorityType object) {
                return createWorkItemIDandPriorityTypeAdapter();
            }
            @Override
            public Adapter caseWorkItemPreview(WorkItemPreview object) {
                return createWorkItemPreviewAdapter();
            }
            @Override
            public Adapter caseWorkItemPriorityType(WorkItemPriorityType object) {
                return createWorkItemPriorityTypeAdapter();
            }
            @Override
            public Adapter caseWorkListView(WorkListView object) {
                return createWorkListViewAdapter();
            }
            @Override
            public Adapter caseWorkListViewCommon(WorkListViewCommon object) {
                return createWorkListViewCommonAdapter();
            }
            @Override
            public Adapter caseWorkListViewEdit(WorkListViewEdit object) {
                return createWorkListViewEditAdapter();
            }
            @Override
            public Adapter caseWorkListViewPageItem(WorkListViewPageItem object) {
                return createWorkListViewPageItemAdapter();
            }
            @Override
            public Adapter caseWorkModel(WorkModel object) {
                return createWorkModelAdapter();
            }
            @Override
            public Adapter caseWorkModelEntities(WorkModelEntities object) {
                return createWorkModelEntitiesAdapter();
            }
            @Override
            public Adapter caseWorkModelEntity(WorkModelEntity object) {
                return createWorkModelEntityAdapter();
            }
            @Override
            public Adapter caseWorkModelList(WorkModelList object) {
                return createWorkModelListAdapter();
            }
            @Override
            public Adapter caseWorkModelMapping(WorkModelMapping object) {
                return createWorkModelMappingAdapter();
            }
            @Override
            public Adapter caseWorkModelScript(WorkModelScript object) {
                return createWorkModelScriptAdapter();
            }
            @Override
            public Adapter caseWorkModelScripts(WorkModelScripts object) {
                return createWorkModelScriptsAdapter();
            }
            @Override
            public Adapter caseWorkModelSpecification(WorkModelSpecification object) {
                return createWorkModelSpecificationAdapter();
            }
            @Override
            public Adapter caseWorkModelType(WorkModelType object) {
                return createWorkModelTypeAdapter();
            }
            @Override
            public Adapter caseWorkModelTypes(WorkModelTypes object) {
                return createWorkModelTypesAdapter();
            }
            @Override
            public Adapter caseWorkTypeList(WorkTypeList object) {
                return createWorkTypeListAdapter();
            }
            @Override
            public Adapter caseDataModel(DataModel object) {
                return createDataModelAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AddCurrentResourceToViewResponseType <em>Add Current Resource To View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AddCurrentResourceToViewResponseType
     * @generated
     */
    public Adapter createAddCurrentResourceToViewResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AddCurrentResourceToViewType <em>Add Current Resource To View Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AddCurrentResourceToViewType
     * @generated
     */
    public Adapter createAddCurrentResourceToViewTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType <em>Allocate And Open Next Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType
     * @generated
     */
    public Adapter createAllocateAndOpenNextWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType <em>Allocate And Open Next Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType
     * @generated
     */
    public Adapter createAllocateAndOpenNextWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType <em>Allocate And Open Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType
     * @generated
     */
    public Adapter createAllocateAndOpenWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemType <em>Allocate And Open Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AllocateAndOpenWorkItemType
     * @generated
     */
    public Adapter createAllocateAndOpenWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AllocateWorkItemResponseType <em>Allocate Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AllocateWorkItemResponseType
     * @generated
     */
    public Adapter createAllocateWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AllocateWorkItemType <em>Allocate Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AllocateWorkItemType
     * @generated
     */
    public Adapter createAllocateWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AllocationHistory <em>Allocation History</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AllocationHistory
     * @generated
     */
    public Adapter createAllocationHistoryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType <em>Async Cancel Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType
     * @generated
     */
    public Adapter createAsyncCancelWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncCancelWorkItemType <em>Async Cancel Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncCancelWorkItemType
     * @generated
     */
    public Adapter createAsyncCancelWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType <em>Async End Group Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncEndGroupResponseType
     * @generated
     */
    public Adapter createAsyncEndGroupResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncEndGroupType <em>Async End Group Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncEndGroupType
     * @generated
     */
    public Adapter createAsyncEndGroupTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType <em>Async Reschedule Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType
     * @generated
     */
    public Adapter createAsyncRescheduleWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType <em>Async Reschedule Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncRescheduleWorkItemType
     * @generated
     */
    public Adapter createAsyncRescheduleWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType <em>Async Resume Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType
     * @generated
     */
    public Adapter createAsyncResumeWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemType <em>Async Resume Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncResumeWorkItemType
     * @generated
     */
    public Adapter createAsyncResumeWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType <em>Async Schedule Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType
     * @generated
     */
    public Adapter createAsyncScheduleWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemType <em>Async Schedule Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemType
     * @generated
     */
    public Adapter createAsyncScheduleWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType <em>Async Schedule Work Item With Model Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType
     * @generated
     */
    public Adapter createAsyncScheduleWorkItemWithModelResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType <em>Async Schedule Work Item With Model Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType
     * @generated
     */
    public Adapter createAsyncScheduleWorkItemWithModelTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncStartGroupResponseType <em>Async Start Group Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncStartGroupResponseType
     * @generated
     */
    public Adapter createAsyncStartGroupResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncStartGroupType <em>Async Start Group Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncStartGroupType
     * @generated
     */
    public Adapter createAsyncStartGroupTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType <em>Async Suspend Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType
     * @generated
     */
    public Adapter createAsyncSuspendWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType <em>Async Suspend Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncSuspendWorkItemType
     * @generated
     */
    public Adapter createAsyncSuspendWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails <em>Async Work Item Details</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AsyncWorkItemDetails
     * @generated
     */
    public Adapter createAsyncWorkItemDetailsAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.AttributeAliasListType <em>Attribute Alias List Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.AttributeAliasListType
     * @generated
     */
    public Adapter createAttributeAliasListTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.BaseItemInfo <em>Base Item Info</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.BaseItemInfo
     * @generated
     */
    public Adapter createBaseItemInfoAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.BaseModelInfo <em>Base Model Info</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.BaseModelInfo
     * @generated
     */
    public Adapter createBaseModelInfoAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.CancelWorkItemResponseType <em>Cancel Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.CancelWorkItemResponseType
     * @generated
     */
    public Adapter createCancelWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.CancelWorkItemType <em>Cancel Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.CancelWorkItemType
     * @generated
     */
    public Adapter createCancelWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType <em>Chained Work Item Notification Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ChainedWorkItemNotificationType
     * @generated
     */
    public Adapter createChainedWorkItemNotificationTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.CloseWorkItemResponseType <em>Close Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.CloseWorkItemResponseType
     * @generated
     */
    public Adapter createCloseWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.CloseWorkItemType <em>Close Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.CloseWorkItemType
     * @generated
     */
    public Adapter createCloseWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ColumnDefinition <em>Column Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ColumnDefinition
     * @generated
     */
    public Adapter createColumnDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ColumnOrder <em>Column Order</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ColumnOrder
     * @generated
     */
    public Adapter createColumnOrderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType <em>Complete Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.CompleteWorkItemResponseType
     * @generated
     */
    public Adapter createCompleteWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.CompleteWorkItemType <em>Complete Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.CompleteWorkItemType
     * @generated
     */
    public Adapter createCompleteWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.CreateWorkListViewResponseType <em>Create Work List View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.CreateWorkListViewResponseType
     * @generated
     */
    public Adapter createCreateWorkListViewResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewResponseType <em>Delete Current Resource From View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.DeleteCurrentResourceFromViewResponseType
     * @generated
     */
    public Adapter createDeleteCurrentResourceFromViewResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType <em>Delete Current Resource From View Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType
     * @generated
     */
    public Adapter createDeleteCurrentResourceFromViewTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType <em>Delete Org Entity Config Attributes Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType
     * @generated
     */
    public Adapter createDeleteOrgEntityConfigAttributesResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType <em>Delete Org Entity Config Attributes Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType
     * @generated
     */
    public Adapter createDeleteOrgEntityConfigAttributesTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.DeleteWorkListViewResponseType <em>Delete Work List View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.DeleteWorkListViewResponseType
     * @generated
     */
    public Adapter createDeleteWorkListViewResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.DeleteWorkListViewType <em>Delete Work List View Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.DeleteWorkListViewType
     * @generated
     */
    public Adapter createDeleteWorkListViewTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.DocumentRoot
     * @generated
     */
    public Adapter createDocumentRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.EditWorkListViewResponseType <em>Edit Work List View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.EditWorkListViewResponseType
     * @generated
     */
    public Adapter createEditWorkListViewResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.EditWorkListViewType <em>Edit Work List View Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.EditWorkListViewType
     * @generated
     */
    public Adapter createEditWorkListViewTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.EnableWorkItemResponseType <em>Enable Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.EnableWorkItemResponseType
     * @generated
     */
    public Adapter createEnableWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.EnableWorkItemType <em>Enable Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.EnableWorkItemType
     * @generated
     */
    public Adapter createEnableWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.EndGroupResponseType <em>End Group Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.EndGroupResponseType
     * @generated
     */
    public Adapter createEndGroupResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.EndGroupType <em>End Group Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.EndGroupType
     * @generated
     */
    public Adapter createEndGroupTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType <em>Get Allocated Work List Items Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetAllocatedWorkListItemsType
     * @generated
     */
    public Adapter createGetAllocatedWorkListItemsTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetBatchGroupIdsResponseType <em>Get Batch Group Ids Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetBatchGroupIdsResponseType
     * @generated
     */
    public Adapter createGetBatchGroupIdsResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType <em>Get Batch Work Item Ids Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType
     * @generated
     */
    public Adapter createGetBatchWorkItemIdsResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType <em>Get Editable Work List Views Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType
     * @generated
     */
    public Adapter createGetEditableWorkListViewsResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetEditableWorkListViewsType <em>Get Editable Work List Views Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetEditableWorkListViewsType
     * @generated
     */
    public Adapter createGetEditableWorkListViewsTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetOfferSetResponseType <em>Get Offer Set Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetOfferSetResponseType
     * @generated
     */
    public Adapter createGetOfferSetResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetOfferSetType <em>Get Offer Set Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetOfferSetType
     * @generated
     */
    public Adapter createGetOfferSetTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType <em>Get Org Entity Config Attributes Available Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType
     * @generated
     */
    public Adapter createGetOrgEntityConfigAttributesAvailableResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType <em>Get Org Entity Config Attributes Available Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType
     * @generated
     */
    public Adapter createGetOrgEntityConfigAttributesAvailableTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType <em>Get Org Entity Config Attributes Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType
     * @generated
     */
    public Adapter createGetOrgEntityConfigAttributesResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType <em>Get Org Entity Config Attributes Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType
     * @generated
     */
    public Adapter createGetOrgEntityConfigAttributesTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetPublicWorkListViewsResponseType <em>Get Public Work List Views Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetPublicWorkListViewsResponseType
     * @generated
     */
    public Adapter createGetPublicWorkListViewsResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetPublicWorkListViewsType <em>Get Public Work List Views Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetPublicWorkListViewsType
     * @generated
     */
    public Adapter createGetPublicWorkListViewsTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType <em>Get Resource Order Filter Criteria Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType
     * @generated
     */
    public Adapter createGetResourceOrderFilterCriteriaResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType <em>Get Resource Order Filter Criteria Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType
     * @generated
     */
    public Adapter createGetResourceOrderFilterCriteriaTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetViewsForResourceResponseType <em>Get Views For Resource Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetViewsForResourceResponseType
     * @generated
     */
    public Adapter createGetViewsForResourceResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetViewsForResourceType <em>Get Views For Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetViewsForResourceType
     * @generated
     */
    public Adapter createGetViewsForResourceTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkItemHeaderResponseType <em>Get Work Item Header Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkItemHeaderResponseType
     * @generated
     */
    public Adapter createGetWorkItemHeaderResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkItemHeaderType <em>Get Work Item Header Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkItemHeaderType
     * @generated
     */
    public Adapter createGetWorkItemHeaderTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType <em>Get Work Item Order Filter Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType
     * @generated
     */
    public Adapter createGetWorkItemOrderFilterResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterType <em>Get Work Item Order Filter Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkItemOrderFilterType
     * @generated
     */
    public Adapter createGetWorkItemOrderFilterTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType <em>Get Work List Items For Global Data Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType
     * @generated
     */
    public Adapter createGetWorkListItemsForGlobalDataResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType <em>Get Work List Items For Global Data Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType
     * @generated
     */
    public Adapter createGetWorkListItemsForGlobalDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType <em>Get Work List Items For View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType
     * @generated
     */
    public Adapter createGetWorkListItemsForViewResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType <em>Get Work List Items For View Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkListItemsForViewType
     * @generated
     */
    public Adapter createGetWorkListItemsForViewTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType <em>Get Work List Items Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType
     * @generated
     */
    public Adapter createGetWorkListItemsResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkListItemsResponseType1 <em>Get Work List Items Response Type1</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkListItemsResponseType1
     * @generated
     */
    public Adapter createGetWorkListItemsResponseType1Adapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkListItemsType <em>Get Work List Items Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkListItemsType
     * @generated
     */
    public Adapter createGetWorkListItemsTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkListViewDetailsType <em>Get Work List View Details Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkListViewDetailsType
     * @generated
     */
    public Adapter createGetWorkListViewDetailsTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkModelResponseType <em>Get Work Model Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkModelResponseType
     * @generated
     */
    public Adapter createGetWorkModelResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkModelsResponseType <em>Get Work Models Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkModelsResponseType
     * @generated
     */
    public Adapter createGetWorkModelsResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkModelsType <em>Get Work Models Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkModelsType
     * @generated
     */
    public Adapter createGetWorkModelsTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkModelType <em>Get Work Model Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkModelType
     * @generated
     */
    public Adapter createGetWorkModelTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkTypeResponseType <em>Get Work Type Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkTypeResponseType
     * @generated
     */
    public Adapter createGetWorkTypeResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkTypesResponseType <em>Get Work Types Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkTypesResponseType
     * @generated
     */
    public Adapter createGetWorkTypesResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkTypesType <em>Get Work Types Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkTypesType
     * @generated
     */
    public Adapter createGetWorkTypesTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.GetWorkTypeType <em>Get Work Type Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.GetWorkTypeType
     * @generated
     */
    public Adapter createGetWorkTypeTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.HiddenPeriod <em>Hidden Period</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.HiddenPeriod
     * @generated
     */
    public Adapter createHiddenPeriodAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.Item <em>Item</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.Item
     * @generated
     */
    public Adapter createItemAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ItemBody <em>Item Body</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ItemBody
     * @generated
     */
    public Adapter createItemBodyAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ItemContext <em>Item Context</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ItemContext
     * @generated
     */
    public Adapter createItemContextAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ItemDuration <em>Item Duration</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ItemDuration
     * @generated
     */
    public Adapter createItemDurationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ItemPrivilege <em>Item Privilege</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ItemPrivilege
     * @generated
     */
    public Adapter createItemPrivilegeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ItemSchedule <em>Item Schedule</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ItemSchedule
     * @generated
     */
    public Adapter createItemScheduleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ManagedObjectID <em>Managed Object ID</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ManagedObjectID
     * @generated
     */
    public Adapter createManagedObjectIDAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.NextWorkItem <em>Next Work Item</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.NextWorkItem
     * @generated
     */
    public Adapter createNextWorkItemAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ObjectID <em>Object ID</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ObjectID
     * @generated
     */
    public Adapter createObjectIDAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.OnNotificationResponseType <em>On Notification Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.OnNotificationResponseType
     * @generated
     */
    public Adapter createOnNotificationResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.OnNotificationType <em>On Notification Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.OnNotificationType
     * @generated
     */
    public Adapter createOnNotificationTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.OpenWorkItemResponseType <em>Open Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.OpenWorkItemResponseType
     * @generated
     */
    public Adapter createOpenWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.OpenWorkItemType <em>Open Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.OpenWorkItemType
     * @generated
     */
    public Adapter createOpenWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.OrderFilterCriteria <em>Order Filter Criteria</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.OrderFilterCriteria
     * @generated
     */
    public Adapter createOrderFilterCriteriaAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.OrgEntityConfigAttribute <em>Org Entity Config Attribute</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttribute
     * @generated
     */
    public Adapter createOrgEntityConfigAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable <em>Org Entity Config Attributes Available</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable
     * @generated
     */
    public Adapter createOrgEntityConfigAttributesAvailableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet <em>Org Entity Config Attribute Set</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.OrgEntityConfigAttributeSet
     * @generated
     */
    public Adapter createOrgEntityConfigAttributeSetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ParameterType <em>Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ParameterType
     * @generated
     */
    public Adapter createParameterTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.PendWorkItem <em>Pend Work Item</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.PendWorkItem
     * @generated
     */
    public Adapter createPendWorkItemAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.PendWorkItemResponseType <em>Pend Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.PendWorkItemResponseType
     * @generated
     */
    public Adapter createPendWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType <em>Preview Work Item From List Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType
     * @generated
     */
    public Adapter createPreviewWorkItemFromListResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType <em>Preview Work Item From List Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.PreviewWorkItemFromListType
     * @generated
     */
    public Adapter createPreviewWorkItemFromListTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.Privilege <em>Privilege</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.Privilege
     * @generated
     */
    public Adapter createPrivilegeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.PushNotificationType <em>Push Notification Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.PushNotificationType
     * @generated
     */
    public Adapter createPushNotificationTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ReallocateWorkItemData <em>Reallocate Work Item Data</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemData
     * @generated
     */
    public Adapter createReallocateWorkItemDataAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType <em>Reallocate Work Item Data Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType
     * @generated
     */
    public Adapter createReallocateWorkItemDataResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ReallocateWorkItemResponseType <em>Reallocate Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemResponseType
     * @generated
     */
    public Adapter createReallocateWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ReallocateWorkItemType <em>Reallocate Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ReallocateWorkItemType
     * @generated
     */
    public Adapter createReallocateWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.RescheduleWorkItem <em>Reschedule Work Item</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.RescheduleWorkItem
     * @generated
     */
    public Adapter createRescheduleWorkItemAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.RescheduleWorkItemResponseType <em>Reschedule Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.RescheduleWorkItemResponseType
     * @generated
     */
    public Adapter createRescheduleWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ResumeWorkItemResponseType <em>Resume Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ResumeWorkItemResponseType
     * @generated
     */
    public Adapter createResumeWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ResumeWorkItemType <em>Resume Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ResumeWorkItemType
     * @generated
     */
    public Adapter createResumeWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SaveOpenWorkItemResponseType <em>Save Open Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SaveOpenWorkItemResponseType
     * @generated
     */
    public Adapter createSaveOpenWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SaveOpenWorkItemType <em>Save Open Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SaveOpenWorkItemType
     * @generated
     */
    public Adapter createSaveOpenWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ScheduleWorkItemResponseType <em>Schedule Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemResponseType
     * @generated
     */
    public Adapter createScheduleWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ScheduleWorkItemType <em>Schedule Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemType
     * @generated
     */
    public Adapter createScheduleWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType <em>Schedule Work Item With Model Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType
     * @generated
     */
    public Adapter createScheduleWorkItemWithModelResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType <em>Schedule Work Item With Model Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.ScheduleWorkItemWithModelType
     * @generated
     */
    public Adapter createScheduleWorkItemWithModelTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesResponseType <em>Set Org Entity Config Attributes Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SetOrgEntityConfigAttributesResponseType
     * @generated
     */
    public Adapter createSetOrgEntityConfigAttributesResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType <em>Set Org Entity Config Attributes Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType
     * @generated
     */
    public Adapter createSetOrgEntityConfigAttributesTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaResponseType <em>Set Resource Order Filter Criteria Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaResponseType
     * @generated
     */
    public Adapter createSetResourceOrderFilterCriteriaResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType <em>Set Resource Order Filter Criteria Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType
     * @generated
     */
    public Adapter createSetResourceOrderFilterCriteriaTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SetWorkItemPriority <em>Set Work Item Priority</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SetWorkItemPriority
     * @generated
     */
    public Adapter createSetWorkItemPriorityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SetWorkItemPriorityResponseType <em>Set Work Item Priority Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SetWorkItemPriorityResponseType
     * @generated
     */
    public Adapter createSetWorkItemPriorityResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SkipWorkItemResponseType <em>Skip Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SkipWorkItemResponseType
     * @generated
     */
    public Adapter createSkipWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SkipWorkItemType <em>Skip Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SkipWorkItemType
     * @generated
     */
    public Adapter createSkipWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.StartGroupResponseType <em>Start Group Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.StartGroupResponseType
     * @generated
     */
    public Adapter createStartGroupResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.StartGroupType <em>Start Group Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.StartGroupType
     * @generated
     */
    public Adapter createStartGroupTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SuspendWorkItemResponseType <em>Suspend Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SuspendWorkItemResponseType
     * @generated
     */
    public Adapter createSuspendWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.SuspendWorkItemType <em>Suspend Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.SuspendWorkItemType
     * @generated
     */
    public Adapter createSuspendWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.UnallocateWorkItemResponseType <em>Unallocate Work Item Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.UnallocateWorkItemResponseType
     * @generated
     */
    public Adapter createUnallocateWorkItemResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.UnallocateWorkItemType <em>Unallocate Work Item Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.UnallocateWorkItemType
     * @generated
     */
    public Adapter createUnallocateWorkItemTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.UnlockWorkListViewResponseType <em>Unlock Work List View Response Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.UnlockWorkListViewResponseType
     * @generated
     */
    public Adapter createUnlockWorkListViewResponseTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.UnlockWorkListViewType <em>Unlock Work List View Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.UnlockWorkListViewType
     * @generated
     */
    public Adapter createUnlockWorkListViewTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkItem <em>Work Item</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkItem
     * @generated
     */
    public Adapter createWorkItemAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkItemAttributes <em>Work Item Attributes</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkItemAttributes
     * @generated
     */
    public Adapter createWorkItemAttributesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkItemBody <em>Work Item Body</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkItemBody
     * @generated
     */
    public Adapter createWorkItemBodyAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkItemFlags <em>Work Item Flags</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkItemFlags
     * @generated
     */
    public Adapter createWorkItemFlagsAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkItemHeader <em>Work Item Header</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkItemHeader
     * @generated
     */
    public Adapter createWorkItemHeaderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkItemIDandPriorityType <em>Work Item IDand Priority Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkItemIDandPriorityType
     * @generated
     */
    public Adapter createWorkItemIDandPriorityTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkItemPreview <em>Work Item Preview</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkItemPreview
     * @generated
     */
    public Adapter createWorkItemPreviewAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkItemPriorityType <em>Work Item Priority Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkItemPriorityType
     * @generated
     */
    public Adapter createWorkItemPriorityTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkListView <em>Work List View</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkListView
     * @generated
     */
    public Adapter createWorkListViewAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkListViewCommon <em>Work List View Common</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkListViewCommon
     * @generated
     */
    public Adapter createWorkListViewCommonAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkListViewEdit <em>Work List View Edit</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkListViewEdit
     * @generated
     */
    public Adapter createWorkListViewEditAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkListViewPageItem <em>Work List View Page Item</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkListViewPageItem
     * @generated
     */
    public Adapter createWorkListViewPageItemAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModel <em>Work Model</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModel
     * @generated
     */
    public Adapter createWorkModelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelEntities <em>Work Model Entities</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelEntities
     * @generated
     */
    public Adapter createWorkModelEntitiesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelEntity <em>Work Model Entity</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelEntity
     * @generated
     */
    public Adapter createWorkModelEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelList <em>Work Model List</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelList
     * @generated
     */
    public Adapter createWorkModelListAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelMapping <em>Work Model Mapping</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelMapping
     * @generated
     */
    public Adapter createWorkModelMappingAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelScript <em>Work Model Script</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelScript
     * @generated
     */
    public Adapter createWorkModelScriptAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelScripts <em>Work Model Scripts</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelScripts
     * @generated
     */
    public Adapter createWorkModelScriptsAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelSpecification <em>Work Model Specification</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelSpecification
     * @generated
     */
    public Adapter createWorkModelSpecificationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelType <em>Work Model Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelType
     * @generated
     */
    public Adapter createWorkModelTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkModelTypes <em>Work Model Types</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkModelTypes
     * @generated
     */
    public Adapter createWorkModelTypesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.brm.api.WorkTypeList <em>Work Type List</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.brm.api.WorkTypeList
     * @generated
     */
    public Adapter createWorkTypeListAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.common.datamodel.DataModel <em>Data Model</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.common.datamodel.DataModel
     * @generated
     */
    public Adapter createDataModelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //N2BRMAdapterFactory
