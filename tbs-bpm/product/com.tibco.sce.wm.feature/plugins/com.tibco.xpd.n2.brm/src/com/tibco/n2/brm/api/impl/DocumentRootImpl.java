/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AddCurrentResourceToViewResponseType;
import com.tibco.n2.brm.api.AddCurrentResourceToViewType;
import com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType;
import com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemType;
import com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType;
import com.tibco.n2.brm.api.AllocateAndOpenWorkItemType;
import com.tibco.n2.brm.api.AllocateWorkItemResponseType;
import com.tibco.n2.brm.api.AllocateWorkItemType;
import com.tibco.n2.brm.api.AsyncCancelWorkItemResponseType;
import com.tibco.n2.brm.api.AsyncCancelWorkItemType;
import com.tibco.n2.brm.api.AsyncEndGroupResponseType;
import com.tibco.n2.brm.api.AsyncEndGroupType;
import com.tibco.n2.brm.api.AsyncRescheduleWorkItemResponseType;
import com.tibco.n2.brm.api.AsyncRescheduleWorkItemType;
import com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType;
import com.tibco.n2.brm.api.AsyncResumeWorkItemType;
import com.tibco.n2.brm.api.AsyncScheduleWorkItemResponseType;
import com.tibco.n2.brm.api.AsyncScheduleWorkItemType;
import com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelResponseType;
import com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType;
import com.tibco.n2.brm.api.AsyncStartGroupResponseType;
import com.tibco.n2.brm.api.AsyncStartGroupType;
import com.tibco.n2.brm.api.AsyncSuspendWorkItemResponseType;
import com.tibco.n2.brm.api.AsyncSuspendWorkItemType;
import com.tibco.n2.brm.api.CancelWorkItemResponseType;
import com.tibco.n2.brm.api.CancelWorkItemType;
import com.tibco.n2.brm.api.ChainedWorkItemNotificationType;
import com.tibco.n2.brm.api.CloseWorkItemResponseType;
import com.tibco.n2.brm.api.CloseWorkItemType;
import com.tibco.n2.brm.api.CompleteWorkItemResponseType;
import com.tibco.n2.brm.api.CompleteWorkItemType;
import com.tibco.n2.brm.api.CreateWorkListViewResponseType;
import com.tibco.n2.brm.api.DeleteCurrentResourceFromViewResponseType;
import com.tibco.n2.brm.api.DeleteCurrentResourceFromViewType;
import com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesResponseType;
import com.tibco.n2.brm.api.DeleteOrgEntityConfigAttributesType;
import com.tibco.n2.brm.api.DeleteWorkListViewResponseType;
import com.tibco.n2.brm.api.DeleteWorkListViewType;
import com.tibco.n2.brm.api.DocumentRoot;
import com.tibco.n2.brm.api.EditWorkListViewResponseType;
import com.tibco.n2.brm.api.EditWorkListViewType;
import com.tibco.n2.brm.api.EnableWorkItemResponseType;
import com.tibco.n2.brm.api.EnableWorkItemType;
import com.tibco.n2.brm.api.EndGroupResponseType;
import com.tibco.n2.brm.api.EndGroupType;
import com.tibco.n2.brm.api.GetAllocatedWorkListItemsType;
import com.tibco.n2.brm.api.GetBatchGroupIdsResponseType;
import com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType;
import com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType;
import com.tibco.n2.brm.api.GetEditableWorkListViewsType;
import com.tibco.n2.brm.api.GetOfferSetResponseType;
import com.tibco.n2.brm.api.GetOfferSetType;
import com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType;
import com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableType;
import com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType;
import com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType;
import com.tibco.n2.brm.api.GetPublicWorkListViewsResponseType;
import com.tibco.n2.brm.api.GetPublicWorkListViewsType;
import com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType;
import com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType;
import com.tibco.n2.brm.api.GetViewsForResourceResponseType;
import com.tibco.n2.brm.api.GetViewsForResourceType;
import com.tibco.n2.brm.api.GetWorkItemHeaderResponseType;
import com.tibco.n2.brm.api.GetWorkItemHeaderType;
import com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType;
import com.tibco.n2.brm.api.GetWorkItemOrderFilterType;
import com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataResponseType;
import com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType;
import com.tibco.n2.brm.api.GetWorkListItemsForViewResponseType;
import com.tibco.n2.brm.api.GetWorkListItemsForViewType;
import com.tibco.n2.brm.api.GetWorkListItemsResponseType1;
import com.tibco.n2.brm.api.GetWorkListItemsType;
import com.tibco.n2.brm.api.GetWorkListViewDetailsType;
import com.tibco.n2.brm.api.GetWorkModelResponseType;
import com.tibco.n2.brm.api.GetWorkModelType;
import com.tibco.n2.brm.api.GetWorkModelsResponseType;
import com.tibco.n2.brm.api.GetWorkModelsType;
import com.tibco.n2.brm.api.GetWorkTypeResponseType;
import com.tibco.n2.brm.api.GetWorkTypeType;
import com.tibco.n2.brm.api.GetWorkTypesResponseType;
import com.tibco.n2.brm.api.GetWorkTypesType;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.OnNotificationResponseType;
import com.tibco.n2.brm.api.OnNotificationType;
import com.tibco.n2.brm.api.OpenWorkItemResponseType;
import com.tibco.n2.brm.api.OpenWorkItemType;
import com.tibco.n2.brm.api.PendWorkItem;
import com.tibco.n2.brm.api.PendWorkItemResponseType;
import com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType;
import com.tibco.n2.brm.api.PreviewWorkItemFromListType;
import com.tibco.n2.brm.api.PushNotificationType;
import com.tibco.n2.brm.api.ReallocateWorkItemData;
import com.tibco.n2.brm.api.ReallocateWorkItemDataResponseType;
import com.tibco.n2.brm.api.ReallocateWorkItemResponseType;
import com.tibco.n2.brm.api.ReallocateWorkItemType;
import com.tibco.n2.brm.api.RescheduleWorkItem;
import com.tibco.n2.brm.api.RescheduleWorkItemResponseType;
import com.tibco.n2.brm.api.ResumeWorkItemResponseType;
import com.tibco.n2.brm.api.ResumeWorkItemType;
import com.tibco.n2.brm.api.SaveOpenWorkItemResponseType;
import com.tibco.n2.brm.api.SaveOpenWorkItemType;
import com.tibco.n2.brm.api.ScheduleWorkItemResponseType;
import com.tibco.n2.brm.api.ScheduleWorkItemType;
import com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType;
import com.tibco.n2.brm.api.ScheduleWorkItemWithModelType;
import com.tibco.n2.brm.api.SetOrgEntityConfigAttributesResponseType;
import com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType;
import com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaResponseType;
import com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType;
import com.tibco.n2.brm.api.SetWorkItemPriority;
import com.tibco.n2.brm.api.SetWorkItemPriorityResponseType;
import com.tibco.n2.brm.api.SkipWorkItemResponseType;
import com.tibco.n2.brm.api.SkipWorkItemType;
import com.tibco.n2.brm.api.StartGroupResponseType;
import com.tibco.n2.brm.api.StartGroupType;
import com.tibco.n2.brm.api.SuspendWorkItemResponseType;
import com.tibco.n2.brm.api.SuspendWorkItemType;
import com.tibco.n2.brm.api.UnallocateWorkItemResponseType;
import com.tibco.n2.brm.api.UnallocateWorkItemType;
import com.tibco.n2.brm.api.UnlockWorkListViewResponseType;
import com.tibco.n2.brm.api.UnlockWorkListViewType;
import com.tibco.n2.brm.api.WorkListView;
import com.tibco.n2.brm.api.WorkListViewEdit;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAddCurrentResourceToView <em>Add Current Resource To View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAddCurrentResourceToViewResponse <em>Add Current Resource To View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAllocateAndOpenNextWorkItem <em>Allocate And Open Next Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAllocateAndOpenNextWorkItemResponse <em>Allocate And Open Next Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAllocateAndOpenWorkItem <em>Allocate And Open Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAllocateAndOpenWorkItemResponse <em>Allocate And Open Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAllocateWorkItem <em>Allocate Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAllocateWorkItemResponse <em>Allocate Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncCancelWorkItem <em>Async Cancel Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncCancelWorkItemResponse <em>Async Cancel Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncEndGroup <em>Async End Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncEndGroupResponse <em>Async End Group Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncRescheduleWorkItem <em>Async Reschedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncRescheduleWorkItemResponse <em>Async Reschedule Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncResumeWorkItem <em>Async Resume Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncResumeWorkItemResponse <em>Async Resume Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncScheduleWorkItem <em>Async Schedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncScheduleWorkItemResponse <em>Async Schedule Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncScheduleWorkItemWithModel <em>Async Schedule Work Item With Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncScheduleWorkItemWithModelResponse <em>Async Schedule Work Item With Model Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncStartGroup <em>Async Start Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncStartGroupResponse <em>Async Start Group Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncSuspendWorkItem <em>Async Suspend Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getAsyncSuspendWorkItemResponse <em>Async Suspend Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getCancelWorkItem <em>Cancel Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getCancelWorkItemResponse <em>Cancel Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getChainedWorkItemNotification <em>Chained Work Item Notification</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getCloseWorkItem <em>Close Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getCloseWorkItemResponse <em>Close Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getCompleteWorkItem <em>Complete Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getCompleteWorkItemResponse <em>Complete Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getCreateWorkListView <em>Create Work List View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getCreateWorkListViewResponse <em>Create Work List View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getDeleteCurrentResourceFromView <em>Delete Current Resource From View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getDeleteCurrentResourceFromViewResponse <em>Delete Current Resource From View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getDeleteOrgEntityConfigAttributes <em>Delete Org Entity Config Attributes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getDeleteOrgEntityConfigAttributesResponse <em>Delete Org Entity Config Attributes Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getDeleteWorkListView <em>Delete Work List View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getDeleteWorkListViewResponse <em>Delete Work List View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getEditWorkListView <em>Edit Work List View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getEditWorkListViewResponse <em>Edit Work List View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getEnableWorkItem <em>Enable Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getEnableWorkItemResponse <em>Enable Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getEndGroup <em>End Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getEndGroupResponse <em>End Group Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetAllocatedWorkListItems <em>Get Allocated Work List Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetBatchGroupIds <em>Get Batch Group Ids</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetBatchGroupIdsResponse <em>Get Batch Group Ids Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetBatchWorkItemIds <em>Get Batch Work Item Ids</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetBatchWorkItemIdsResponse <em>Get Batch Work Item Ids Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetEditableWorkListViews <em>Get Editable Work List Views</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetEditableWorkListViewsResponse <em>Get Editable Work List Views Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetOfferSet <em>Get Offer Set</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetOfferSetResponse <em>Get Offer Set Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetOrgEntityConfigAttributes <em>Get Org Entity Config Attributes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetOrgEntityConfigAttributesAvailable <em>Get Org Entity Config Attributes Available</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetOrgEntityConfigAttributesAvailableResponse <em>Get Org Entity Config Attributes Available Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetOrgEntityConfigAttributesResponse <em>Get Org Entity Config Attributes Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetPublicWorkListViews <em>Get Public Work List Views</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetPublicWorkListViewsResponse <em>Get Public Work List Views Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetResourceOrderFilterCriteria <em>Get Resource Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetResourceOrderFilterCriteriaResponse <em>Get Resource Order Filter Criteria Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetViewsForResource <em>Get Views For Resource</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetViewsForResourceResponse <em>Get Views For Resource Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkItemHeader <em>Get Work Item Header</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkItemHeaderResponse <em>Get Work Item Header Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkItemOrderFilter <em>Get Work Item Order Filter</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkItemOrderFilterResponse <em>Get Work Item Order Filter Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkListItems <em>Get Work List Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkListItemsForGlobalData <em>Get Work List Items For Global Data</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkListItemsForGlobalDataResponse <em>Get Work List Items For Global Data Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkListItemsForView <em>Get Work List Items For View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkListItemsForViewResponse <em>Get Work List Items For View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkListItemsResponse <em>Get Work List Items Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkListViewDetails <em>Get Work List View Details</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkListViewDetailsResponse <em>Get Work List View Details Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkModel <em>Get Work Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkModelResponse <em>Get Work Model Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkModels <em>Get Work Models</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkModelsResponse <em>Get Work Models Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkType <em>Get Work Type</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkTypeResponse <em>Get Work Type Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkTypes <em>Get Work Types</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getGetWorkTypesResponse <em>Get Work Types Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getOnNotification <em>On Notification</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getOnNotificationResponse <em>On Notification Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getOpenWorkItem <em>Open Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getOpenWorkItemResponse <em>Open Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getPendWorkItem <em>Pend Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getPendWorkItemResponse <em>Pend Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getPreviewWorkItemFromList <em>Preview Work Item From List</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getPreviewWorkItemFromListResponse <em>Preview Work Item From List Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getPushNotification <em>Push Notification</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getReallocateWorkItem <em>Reallocate Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getReallocateWorkItemData <em>Reallocate Work Item Data</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getReallocateWorkItemDataResponse <em>Reallocate Work Item Data Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getReallocateWorkItemResponse <em>Reallocate Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getRescheduleWorkItem <em>Reschedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getRescheduleWorkItemResponse <em>Reschedule Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getResumeWorkItem <em>Resume Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getResumeWorkItemResponse <em>Resume Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSaveOpenWorkItem <em>Save Open Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSaveOpenWorkItemResponse <em>Save Open Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getScheduleWorkItem <em>Schedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getScheduleWorkItemResponse <em>Schedule Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getScheduleWorkItemWithModel <em>Schedule Work Item With Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getScheduleWorkItemWithModelResponse <em>Schedule Work Item With Model Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSetOrgEntityConfigAttributes <em>Set Org Entity Config Attributes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSetOrgEntityConfigAttributesResponse <em>Set Org Entity Config Attributes Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSetResourceOrderFilterCriteria <em>Set Resource Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSetResourceOrderFilterCriteriaResponse <em>Set Resource Order Filter Criteria Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSetWorkItemPriority <em>Set Work Item Priority</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSetWorkItemPriorityResponse <em>Set Work Item Priority Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSkipWorkItem <em>Skip Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSkipWorkItemResponse <em>Skip Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getStartGroup <em>Start Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getStartGroupResponse <em>Start Group Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSuspendWorkItem <em>Suspend Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getSuspendWorkItemResponse <em>Suspend Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getUnallocateWorkItem <em>Unallocate Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getUnallocateWorkItemResponse <em>Unallocate Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getUnlockWorkListView <em>Unlock Work List View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.DocumentRootImpl#getUnlockWorkListViewResponse <em>Unlock Work List View Response</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
    /**
     * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMixed()
     * @generated
     * @ordered
     */
    protected FeatureMap mixed;

    /**
     * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXMLNSPrefixMap()
     * @generated
     * @ordered
     */
    protected EMap<String, String> xMLNSPrefixMap;

    /**
     * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXSISchemaLocation()
     * @generated
     * @ordered
     */
    protected EMap<String, String> xSISchemaLocation;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DocumentRootImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.DOCUMENT_ROOT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getMixed() {
        if (mixed == null) {
            mixed = new BasicFeatureMap(this, N2BRMPackage.DOCUMENT_ROOT__MIXED);
        }
        return mixed;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getXMLNSPrefixMap() {
        if (xMLNSPrefixMap == null) {
            xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, N2BRMPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        }
        return xMLNSPrefixMap;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getXSISchemaLocation() {
        if (xSISchemaLocation == null) {
            xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, N2BRMPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        }
        return xSISchemaLocation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AddCurrentResourceToViewType getAddCurrentResourceToView() {
        return (AddCurrentResourceToViewType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAddCurrentResourceToView(AddCurrentResourceToViewType newAddCurrentResourceToView, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW, newAddCurrentResourceToView, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAddCurrentResourceToView(AddCurrentResourceToViewType newAddCurrentResourceToView) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW, newAddCurrentResourceToView);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AddCurrentResourceToViewResponseType getAddCurrentResourceToViewResponse() {
        return (AddCurrentResourceToViewResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAddCurrentResourceToViewResponse(AddCurrentResourceToViewResponseType newAddCurrentResourceToViewResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE, newAddCurrentResourceToViewResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAddCurrentResourceToViewResponse(AddCurrentResourceToViewResponseType newAddCurrentResourceToViewResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE, newAddCurrentResourceToViewResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateAndOpenNextWorkItemType getAllocateAndOpenNextWorkItem() {
        return (AllocateAndOpenNextWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAllocateAndOpenNextWorkItem(AllocateAndOpenNextWorkItemType newAllocateAndOpenNextWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM, newAllocateAndOpenNextWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocateAndOpenNextWorkItem(AllocateAndOpenNextWorkItemType newAllocateAndOpenNextWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM, newAllocateAndOpenNextWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateAndOpenNextWorkItemResponseType getAllocateAndOpenNextWorkItemResponse() {
        return (AllocateAndOpenNextWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAllocateAndOpenNextWorkItemResponse(AllocateAndOpenNextWorkItemResponseType newAllocateAndOpenNextWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE, newAllocateAndOpenNextWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocateAndOpenNextWorkItemResponse(AllocateAndOpenNextWorkItemResponseType newAllocateAndOpenNextWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE, newAllocateAndOpenNextWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateAndOpenWorkItemType getAllocateAndOpenWorkItem() {
        return (AllocateAndOpenWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAllocateAndOpenWorkItem(AllocateAndOpenWorkItemType newAllocateAndOpenWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM, newAllocateAndOpenWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocateAndOpenWorkItem(AllocateAndOpenWorkItemType newAllocateAndOpenWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM, newAllocateAndOpenWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateAndOpenWorkItemResponseType getAllocateAndOpenWorkItemResponse() {
        return (AllocateAndOpenWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAllocateAndOpenWorkItemResponse(AllocateAndOpenWorkItemResponseType newAllocateAndOpenWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE, newAllocateAndOpenWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocateAndOpenWorkItemResponse(AllocateAndOpenWorkItemResponseType newAllocateAndOpenWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE, newAllocateAndOpenWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateWorkItemType getAllocateWorkItem() {
        return (AllocateWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAllocateWorkItem(AllocateWorkItemType newAllocateWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM, newAllocateWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocateWorkItem(AllocateWorkItemType newAllocateWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM, newAllocateWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocateWorkItemResponseType getAllocateWorkItemResponse() {
        return (AllocateWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAllocateWorkItemResponse(AllocateWorkItemResponseType newAllocateWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE, newAllocateWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocateWorkItemResponse(AllocateWorkItemResponseType newAllocateWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE, newAllocateWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncCancelWorkItemType getAsyncCancelWorkItem() {
        return (AsyncCancelWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncCancelWorkItem(AsyncCancelWorkItemType newAsyncCancelWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM, newAsyncCancelWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncCancelWorkItem(AsyncCancelWorkItemType newAsyncCancelWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM, newAsyncCancelWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncCancelWorkItemResponseType getAsyncCancelWorkItemResponse() {
        return (AsyncCancelWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncCancelWorkItemResponse(AsyncCancelWorkItemResponseType newAsyncCancelWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE, newAsyncCancelWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncCancelWorkItemResponse(AsyncCancelWorkItemResponseType newAsyncCancelWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE, newAsyncCancelWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncEndGroupType getAsyncEndGroup() {
        return (AsyncEndGroupType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_END_GROUP, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncEndGroup(AsyncEndGroupType newAsyncEndGroup, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_END_GROUP, newAsyncEndGroup, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncEndGroup(AsyncEndGroupType newAsyncEndGroup) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_END_GROUP, newAsyncEndGroup);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncEndGroupResponseType getAsyncEndGroupResponse() {
        return (AsyncEndGroupResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncEndGroupResponse(AsyncEndGroupResponseType newAsyncEndGroupResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE, newAsyncEndGroupResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncEndGroupResponse(AsyncEndGroupResponseType newAsyncEndGroupResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE, newAsyncEndGroupResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncRescheduleWorkItemType getAsyncRescheduleWorkItem() {
        return (AsyncRescheduleWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncRescheduleWorkItem(AsyncRescheduleWorkItemType newAsyncRescheduleWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM, newAsyncRescheduleWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncRescheduleWorkItem(AsyncRescheduleWorkItemType newAsyncRescheduleWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM, newAsyncRescheduleWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncRescheduleWorkItemResponseType getAsyncRescheduleWorkItemResponse() {
        return (AsyncRescheduleWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncRescheduleWorkItemResponse(AsyncRescheduleWorkItemResponseType newAsyncRescheduleWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE, newAsyncRescheduleWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncRescheduleWorkItemResponse(AsyncRescheduleWorkItemResponseType newAsyncRescheduleWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE, newAsyncRescheduleWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncResumeWorkItemType getAsyncResumeWorkItem() {
        return (AsyncResumeWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncResumeWorkItem(AsyncResumeWorkItemType newAsyncResumeWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM, newAsyncResumeWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncResumeWorkItem(AsyncResumeWorkItemType newAsyncResumeWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM, newAsyncResumeWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncResumeWorkItemResponseType getAsyncResumeWorkItemResponse() {
        return (AsyncResumeWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncResumeWorkItemResponse(AsyncResumeWorkItemResponseType newAsyncResumeWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE, newAsyncResumeWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncResumeWorkItemResponse(AsyncResumeWorkItemResponseType newAsyncResumeWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE, newAsyncResumeWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncScheduleWorkItemType getAsyncScheduleWorkItem() {
        return (AsyncScheduleWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncScheduleWorkItem(AsyncScheduleWorkItemType newAsyncScheduleWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM, newAsyncScheduleWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncScheduleWorkItem(AsyncScheduleWorkItemType newAsyncScheduleWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM, newAsyncScheduleWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncScheduleWorkItemResponseType getAsyncScheduleWorkItemResponse() {
        return (AsyncScheduleWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncScheduleWorkItemResponse(AsyncScheduleWorkItemResponseType newAsyncScheduleWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE, newAsyncScheduleWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncScheduleWorkItemResponse(AsyncScheduleWorkItemResponseType newAsyncScheduleWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE, newAsyncScheduleWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncScheduleWorkItemWithModelType getAsyncScheduleWorkItemWithModel() {
        return (AsyncScheduleWorkItemWithModelType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncScheduleWorkItemWithModel(AsyncScheduleWorkItemWithModelType newAsyncScheduleWorkItemWithModel, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL, newAsyncScheduleWorkItemWithModel, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncScheduleWorkItemWithModel(AsyncScheduleWorkItemWithModelType newAsyncScheduleWorkItemWithModel) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL, newAsyncScheduleWorkItemWithModel);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncScheduleWorkItemWithModelResponseType getAsyncScheduleWorkItemWithModelResponse() {
        return (AsyncScheduleWorkItemWithModelResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncScheduleWorkItemWithModelResponse(AsyncScheduleWorkItemWithModelResponseType newAsyncScheduleWorkItemWithModelResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE, newAsyncScheduleWorkItemWithModelResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncScheduleWorkItemWithModelResponse(AsyncScheduleWorkItemWithModelResponseType newAsyncScheduleWorkItemWithModelResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE, newAsyncScheduleWorkItemWithModelResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncStartGroupType getAsyncStartGroup() {
        return (AsyncStartGroupType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_START_GROUP, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncStartGroup(AsyncStartGroupType newAsyncStartGroup, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_START_GROUP, newAsyncStartGroup, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncStartGroup(AsyncStartGroupType newAsyncStartGroup) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_START_GROUP, newAsyncStartGroup);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncStartGroupResponseType getAsyncStartGroupResponse() {
        return (AsyncStartGroupResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncStartGroupResponse(AsyncStartGroupResponseType newAsyncStartGroupResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE, newAsyncStartGroupResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncStartGroupResponse(AsyncStartGroupResponseType newAsyncStartGroupResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE, newAsyncStartGroupResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncSuspendWorkItemType getAsyncSuspendWorkItem() {
        return (AsyncSuspendWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncSuspendWorkItem(AsyncSuspendWorkItemType newAsyncSuspendWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM, newAsyncSuspendWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncSuspendWorkItem(AsyncSuspendWorkItemType newAsyncSuspendWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM, newAsyncSuspendWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AsyncSuspendWorkItemResponseType getAsyncSuspendWorkItemResponse() {
        return (AsyncSuspendWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAsyncSuspendWorkItemResponse(AsyncSuspendWorkItemResponseType newAsyncSuspendWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE, newAsyncSuspendWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAsyncSuspendWorkItemResponse(AsyncSuspendWorkItemResponseType newAsyncSuspendWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE, newAsyncSuspendWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CancelWorkItemType getCancelWorkItem() {
        return (CancelWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__CANCEL_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCancelWorkItem(CancelWorkItemType newCancelWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__CANCEL_WORK_ITEM, newCancelWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCancelWorkItem(CancelWorkItemType newCancelWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__CANCEL_WORK_ITEM, newCancelWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CancelWorkItemResponseType getCancelWorkItemResponse() {
        return (CancelWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCancelWorkItemResponse(CancelWorkItemResponseType newCancelWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE, newCancelWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCancelWorkItemResponse(CancelWorkItemResponseType newCancelWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE, newCancelWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChainedWorkItemNotificationType getChainedWorkItemNotification() {
        return (ChainedWorkItemNotificationType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetChainedWorkItemNotification(ChainedWorkItemNotificationType newChainedWorkItemNotification, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION, newChainedWorkItemNotification, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setChainedWorkItemNotification(ChainedWorkItemNotificationType newChainedWorkItemNotification) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION, newChainedWorkItemNotification);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CloseWorkItemType getCloseWorkItem() {
        return (CloseWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__CLOSE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCloseWorkItem(CloseWorkItemType newCloseWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__CLOSE_WORK_ITEM, newCloseWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCloseWorkItem(CloseWorkItemType newCloseWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__CLOSE_WORK_ITEM, newCloseWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CloseWorkItemResponseType getCloseWorkItemResponse() {
        return (CloseWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCloseWorkItemResponse(CloseWorkItemResponseType newCloseWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE, newCloseWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCloseWorkItemResponse(CloseWorkItemResponseType newCloseWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE, newCloseWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompleteWorkItemType getCompleteWorkItem() {
        return (CompleteWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__COMPLETE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCompleteWorkItem(CompleteWorkItemType newCompleteWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__COMPLETE_WORK_ITEM, newCompleteWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCompleteWorkItem(CompleteWorkItemType newCompleteWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__COMPLETE_WORK_ITEM, newCompleteWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompleteWorkItemResponseType getCompleteWorkItemResponse() {
        return (CompleteWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCompleteWorkItemResponse(CompleteWorkItemResponseType newCompleteWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE, newCompleteWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCompleteWorkItemResponse(CompleteWorkItemResponseType newCompleteWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE, newCompleteWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkListViewEdit getCreateWorkListView() {
        return (WorkListViewEdit)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCreateWorkListView(WorkListViewEdit newCreateWorkListView, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW, newCreateWorkListView, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCreateWorkListView(WorkListViewEdit newCreateWorkListView) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW, newCreateWorkListView);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CreateWorkListViewResponseType getCreateWorkListViewResponse() {
        return (CreateWorkListViewResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCreateWorkListViewResponse(CreateWorkListViewResponseType newCreateWorkListViewResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE, newCreateWorkListViewResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCreateWorkListViewResponse(CreateWorkListViewResponseType newCreateWorkListViewResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE, newCreateWorkListViewResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteCurrentResourceFromViewType getDeleteCurrentResourceFromView() {
        return (DeleteCurrentResourceFromViewType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteCurrentResourceFromView(DeleteCurrentResourceFromViewType newDeleteCurrentResourceFromView, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW, newDeleteCurrentResourceFromView, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeleteCurrentResourceFromView(DeleteCurrentResourceFromViewType newDeleteCurrentResourceFromView) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW, newDeleteCurrentResourceFromView);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteCurrentResourceFromViewResponseType getDeleteCurrentResourceFromViewResponse() {
        return (DeleteCurrentResourceFromViewResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteCurrentResourceFromViewResponse(DeleteCurrentResourceFromViewResponseType newDeleteCurrentResourceFromViewResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE, newDeleteCurrentResourceFromViewResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeleteCurrentResourceFromViewResponse(DeleteCurrentResourceFromViewResponseType newDeleteCurrentResourceFromViewResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE, newDeleteCurrentResourceFromViewResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteOrgEntityConfigAttributesType getDeleteOrgEntityConfigAttributes() {
        return (DeleteOrgEntityConfigAttributesType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteOrgEntityConfigAttributes(DeleteOrgEntityConfigAttributesType newDeleteOrgEntityConfigAttributes, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES, newDeleteOrgEntityConfigAttributes, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeleteOrgEntityConfigAttributes(DeleteOrgEntityConfigAttributesType newDeleteOrgEntityConfigAttributes) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES, newDeleteOrgEntityConfigAttributes);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteOrgEntityConfigAttributesResponseType getDeleteOrgEntityConfigAttributesResponse() {
        return (DeleteOrgEntityConfigAttributesResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteOrgEntityConfigAttributesResponse(DeleteOrgEntityConfigAttributesResponseType newDeleteOrgEntityConfigAttributesResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, newDeleteOrgEntityConfigAttributesResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeleteOrgEntityConfigAttributesResponse(DeleteOrgEntityConfigAttributesResponseType newDeleteOrgEntityConfigAttributesResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, newDeleteOrgEntityConfigAttributesResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteWorkListViewType getDeleteWorkListView() {
        return (DeleteWorkListViewType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteWorkListView(DeleteWorkListViewType newDeleteWorkListView, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW, newDeleteWorkListView, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeleteWorkListView(DeleteWorkListViewType newDeleteWorkListView) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW, newDeleteWorkListView);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteWorkListViewResponseType getDeleteWorkListViewResponse() {
        return (DeleteWorkListViewResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteWorkListViewResponse(DeleteWorkListViewResponseType newDeleteWorkListViewResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE, newDeleteWorkListViewResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeleteWorkListViewResponse(DeleteWorkListViewResponseType newDeleteWorkListViewResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE, newDeleteWorkListViewResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EditWorkListViewType getEditWorkListView() {
        return (EditWorkListViewType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEditWorkListView(EditWorkListViewType newEditWorkListView, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW, newEditWorkListView, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEditWorkListView(EditWorkListViewType newEditWorkListView) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW, newEditWorkListView);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EditWorkListViewResponseType getEditWorkListViewResponse() {
        return (EditWorkListViewResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEditWorkListViewResponse(EditWorkListViewResponseType newEditWorkListViewResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE, newEditWorkListViewResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEditWorkListViewResponse(EditWorkListViewResponseType newEditWorkListViewResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE, newEditWorkListViewResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnableWorkItemType getEnableWorkItem() {
        return (EnableWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ENABLE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEnableWorkItem(EnableWorkItemType newEnableWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ENABLE_WORK_ITEM, newEnableWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnableWorkItem(EnableWorkItemType newEnableWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ENABLE_WORK_ITEM, newEnableWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnableWorkItemResponseType getEnableWorkItemResponse() {
        return (EnableWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEnableWorkItemResponse(EnableWorkItemResponseType newEnableWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE, newEnableWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnableWorkItemResponse(EnableWorkItemResponseType newEnableWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE, newEnableWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EndGroupType getEndGroup() {
        return (EndGroupType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__END_GROUP, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEndGroup(EndGroupType newEndGroup, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__END_GROUP, newEndGroup, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndGroup(EndGroupType newEndGroup) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__END_GROUP, newEndGroup);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EndGroupResponseType getEndGroupResponse() {
        return (EndGroupResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__END_GROUP_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEndGroupResponse(EndGroupResponseType newEndGroupResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__END_GROUP_RESPONSE, newEndGroupResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndGroupResponse(EndGroupResponseType newEndGroupResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__END_GROUP_RESPONSE, newEndGroupResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetAllocatedWorkListItemsType getGetAllocatedWorkListItems() {
        return (GetAllocatedWorkListItemsType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetAllocatedWorkListItems(GetAllocatedWorkListItemsType newGetAllocatedWorkListItems, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS, newGetAllocatedWorkListItems, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetAllocatedWorkListItems(GetAllocatedWorkListItemsType newGetAllocatedWorkListItems) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS, newGetAllocatedWorkListItems);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getGetBatchGroupIds() {
        return (EObject)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetBatchGroupIds(EObject newGetBatchGroupIds, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS, newGetBatchGroupIds, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetBatchGroupIds(EObject newGetBatchGroupIds) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS, newGetBatchGroupIds);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetBatchGroupIdsResponseType getGetBatchGroupIdsResponse() {
        return (GetBatchGroupIdsResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetBatchGroupIdsResponse(GetBatchGroupIdsResponseType newGetBatchGroupIdsResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE, newGetBatchGroupIdsResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetBatchGroupIdsResponse(GetBatchGroupIdsResponseType newGetBatchGroupIdsResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE, newGetBatchGroupIdsResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getGetBatchWorkItemIds() {
        return (EObject)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetBatchWorkItemIds(EObject newGetBatchWorkItemIds, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS, newGetBatchWorkItemIds, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetBatchWorkItemIds(EObject newGetBatchWorkItemIds) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS, newGetBatchWorkItemIds);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetBatchWorkItemIdsResponseType getGetBatchWorkItemIdsResponse() {
        return (GetBatchWorkItemIdsResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetBatchWorkItemIdsResponse(GetBatchWorkItemIdsResponseType newGetBatchWorkItemIdsResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE, newGetBatchWorkItemIdsResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetBatchWorkItemIdsResponse(GetBatchWorkItemIdsResponseType newGetBatchWorkItemIdsResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE, newGetBatchWorkItemIdsResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetEditableWorkListViewsType getGetEditableWorkListViews() {
        return (GetEditableWorkListViewsType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetEditableWorkListViews(GetEditableWorkListViewsType newGetEditableWorkListViews, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS, newGetEditableWorkListViews, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetEditableWorkListViews(GetEditableWorkListViewsType newGetEditableWorkListViews) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS, newGetEditableWorkListViews);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetEditableWorkListViewsResponseType getGetEditableWorkListViewsResponse() {
        return (GetEditableWorkListViewsResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetEditableWorkListViewsResponse(GetEditableWorkListViewsResponseType newGetEditableWorkListViewsResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE, newGetEditableWorkListViewsResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetEditableWorkListViewsResponse(GetEditableWorkListViewsResponseType newGetEditableWorkListViewsResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE, newGetEditableWorkListViewsResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOfferSetType getGetOfferSet() {
        return (GetOfferSetType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_OFFER_SET, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetOfferSet(GetOfferSetType newGetOfferSet, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_OFFER_SET, newGetOfferSet, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetOfferSet(GetOfferSetType newGetOfferSet) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_OFFER_SET, newGetOfferSet);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOfferSetResponseType getGetOfferSetResponse() {
        return (GetOfferSetResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetOfferSetResponse(GetOfferSetResponseType newGetOfferSetResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE, newGetOfferSetResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetOfferSetResponse(GetOfferSetResponseType newGetOfferSetResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE, newGetOfferSetResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOrgEntityConfigAttributesType getGetOrgEntityConfigAttributes() {
        return (GetOrgEntityConfigAttributesType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetOrgEntityConfigAttributes(GetOrgEntityConfigAttributesType newGetOrgEntityConfigAttributes, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES, newGetOrgEntityConfigAttributes, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetOrgEntityConfigAttributes(GetOrgEntityConfigAttributesType newGetOrgEntityConfigAttributes) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES, newGetOrgEntityConfigAttributes);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOrgEntityConfigAttributesAvailableType getGetOrgEntityConfigAttributesAvailable() {
        return (GetOrgEntityConfigAttributesAvailableType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetOrgEntityConfigAttributesAvailable(GetOrgEntityConfigAttributesAvailableType newGetOrgEntityConfigAttributesAvailable, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE, newGetOrgEntityConfigAttributesAvailable, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetOrgEntityConfigAttributesAvailable(GetOrgEntityConfigAttributesAvailableType newGetOrgEntityConfigAttributesAvailable) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE, newGetOrgEntityConfigAttributesAvailable);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOrgEntityConfigAttributesAvailableResponseType getGetOrgEntityConfigAttributesAvailableResponse() {
        return (GetOrgEntityConfigAttributesAvailableResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetOrgEntityConfigAttributesAvailableResponse(GetOrgEntityConfigAttributesAvailableResponseType newGetOrgEntityConfigAttributesAvailableResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE, newGetOrgEntityConfigAttributesAvailableResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetOrgEntityConfigAttributesAvailableResponse(GetOrgEntityConfigAttributesAvailableResponseType newGetOrgEntityConfigAttributesAvailableResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE, newGetOrgEntityConfigAttributesAvailableResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetOrgEntityConfigAttributesResponseType getGetOrgEntityConfigAttributesResponse() {
        return (GetOrgEntityConfigAttributesResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetOrgEntityConfigAttributesResponse(GetOrgEntityConfigAttributesResponseType newGetOrgEntityConfigAttributesResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, newGetOrgEntityConfigAttributesResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetOrgEntityConfigAttributesResponse(GetOrgEntityConfigAttributesResponseType newGetOrgEntityConfigAttributesResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, newGetOrgEntityConfigAttributesResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetPublicWorkListViewsType getGetPublicWorkListViews() {
        return (GetPublicWorkListViewsType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetPublicWorkListViews(GetPublicWorkListViewsType newGetPublicWorkListViews, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS, newGetPublicWorkListViews, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetPublicWorkListViews(GetPublicWorkListViewsType newGetPublicWorkListViews) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS, newGetPublicWorkListViews);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetPublicWorkListViewsResponseType getGetPublicWorkListViewsResponse() {
        return (GetPublicWorkListViewsResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetPublicWorkListViewsResponse(GetPublicWorkListViewsResponseType newGetPublicWorkListViewsResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE, newGetPublicWorkListViewsResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetPublicWorkListViewsResponse(GetPublicWorkListViewsResponseType newGetPublicWorkListViewsResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE, newGetPublicWorkListViewsResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetResourceOrderFilterCriteriaType getGetResourceOrderFilterCriteria() {
        return (GetResourceOrderFilterCriteriaType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetResourceOrderFilterCriteria(GetResourceOrderFilterCriteriaType newGetResourceOrderFilterCriteria, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA, newGetResourceOrderFilterCriteria, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetResourceOrderFilterCriteria(GetResourceOrderFilterCriteriaType newGetResourceOrderFilterCriteria) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA, newGetResourceOrderFilterCriteria);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetResourceOrderFilterCriteriaResponseType getGetResourceOrderFilterCriteriaResponse() {
        return (GetResourceOrderFilterCriteriaResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetResourceOrderFilterCriteriaResponse(GetResourceOrderFilterCriteriaResponseType newGetResourceOrderFilterCriteriaResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE, newGetResourceOrderFilterCriteriaResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetResourceOrderFilterCriteriaResponse(GetResourceOrderFilterCriteriaResponseType newGetResourceOrderFilterCriteriaResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE, newGetResourceOrderFilterCriteriaResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetViewsForResourceType getGetViewsForResource() {
        return (GetViewsForResourceType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetViewsForResource(GetViewsForResourceType newGetViewsForResource, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE, newGetViewsForResource, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetViewsForResource(GetViewsForResourceType newGetViewsForResource) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE, newGetViewsForResource);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetViewsForResourceResponseType getGetViewsForResourceResponse() {
        return (GetViewsForResourceResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetViewsForResourceResponse(GetViewsForResourceResponseType newGetViewsForResourceResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE, newGetViewsForResourceResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetViewsForResourceResponse(GetViewsForResourceResponseType newGetViewsForResourceResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE, newGetViewsForResourceResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkItemHeaderType getGetWorkItemHeader() {
        return (GetWorkItemHeaderType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkItemHeader(GetWorkItemHeaderType newGetWorkItemHeader, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER, newGetWorkItemHeader, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkItemHeader(GetWorkItemHeaderType newGetWorkItemHeader) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER, newGetWorkItemHeader);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkItemHeaderResponseType getGetWorkItemHeaderResponse() {
        return (GetWorkItemHeaderResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkItemHeaderResponse(GetWorkItemHeaderResponseType newGetWorkItemHeaderResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE, newGetWorkItemHeaderResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkItemHeaderResponse(GetWorkItemHeaderResponseType newGetWorkItemHeaderResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE, newGetWorkItemHeaderResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkItemOrderFilterType getGetWorkItemOrderFilter() {
        return (GetWorkItemOrderFilterType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkItemOrderFilter(GetWorkItemOrderFilterType newGetWorkItemOrderFilter, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER, newGetWorkItemOrderFilter, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkItemOrderFilter(GetWorkItemOrderFilterType newGetWorkItemOrderFilter) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER, newGetWorkItemOrderFilter);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkItemOrderFilterResponseType getGetWorkItemOrderFilterResponse() {
        return (GetWorkItemOrderFilterResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkItemOrderFilterResponse(GetWorkItemOrderFilterResponseType newGetWorkItemOrderFilterResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE, newGetWorkItemOrderFilterResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkItemOrderFilterResponse(GetWorkItemOrderFilterResponseType newGetWorkItemOrderFilterResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE, newGetWorkItemOrderFilterResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsType getGetWorkListItems() {
        return (GetWorkListItemsType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkListItems(GetWorkListItemsType newGetWorkListItems, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS, newGetWorkListItems, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkListItems(GetWorkListItemsType newGetWorkListItems) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS, newGetWorkListItems);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsForGlobalDataType getGetWorkListItemsForGlobalData() {
        return (GetWorkListItemsForGlobalDataType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkListItemsForGlobalData(GetWorkListItemsForGlobalDataType newGetWorkListItemsForGlobalData, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA, newGetWorkListItemsForGlobalData, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkListItemsForGlobalData(GetWorkListItemsForGlobalDataType newGetWorkListItemsForGlobalData) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA, newGetWorkListItemsForGlobalData);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsForGlobalDataResponseType getGetWorkListItemsForGlobalDataResponse() {
        return (GetWorkListItemsForGlobalDataResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkListItemsForGlobalDataResponse(GetWorkListItemsForGlobalDataResponseType newGetWorkListItemsForGlobalDataResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE, newGetWorkListItemsForGlobalDataResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkListItemsForGlobalDataResponse(GetWorkListItemsForGlobalDataResponseType newGetWorkListItemsForGlobalDataResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE, newGetWorkListItemsForGlobalDataResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsForViewType getGetWorkListItemsForView() {
        return (GetWorkListItemsForViewType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkListItemsForView(GetWorkListItemsForViewType newGetWorkListItemsForView, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW, newGetWorkListItemsForView, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkListItemsForView(GetWorkListItemsForViewType newGetWorkListItemsForView) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW, newGetWorkListItemsForView);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsForViewResponseType getGetWorkListItemsForViewResponse() {
        return (GetWorkListItemsForViewResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkListItemsForViewResponse(GetWorkListItemsForViewResponseType newGetWorkListItemsForViewResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE, newGetWorkListItemsForViewResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkListItemsForViewResponse(GetWorkListItemsForViewResponseType newGetWorkListItemsForViewResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE, newGetWorkListItemsForViewResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListItemsResponseType1 getGetWorkListItemsResponse() {
        return (GetWorkListItemsResponseType1)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkListItemsResponse(GetWorkListItemsResponseType1 newGetWorkListItemsResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE, newGetWorkListItemsResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkListItemsResponse(GetWorkListItemsResponseType1 newGetWorkListItemsResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE, newGetWorkListItemsResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkListViewDetailsType getGetWorkListViewDetails() {
        return (GetWorkListViewDetailsType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkListViewDetails(GetWorkListViewDetailsType newGetWorkListViewDetails, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS, newGetWorkListViewDetails, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkListViewDetails(GetWorkListViewDetailsType newGetWorkListViewDetails) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS, newGetWorkListViewDetails);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkListView getGetWorkListViewDetailsResponse() {
        return (WorkListView)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkListViewDetailsResponse(WorkListView newGetWorkListViewDetailsResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE, newGetWorkListViewDetailsResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkListViewDetailsResponse(WorkListView newGetWorkListViewDetailsResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE, newGetWorkListViewDetailsResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkModelType getGetWorkModel() {
        return (GetWorkModelType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODEL, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkModel(GetWorkModelType newGetWorkModel, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODEL, newGetWorkModel, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkModel(GetWorkModelType newGetWorkModel) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODEL, newGetWorkModel);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkModelResponseType getGetWorkModelResponse() {
        return (GetWorkModelResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkModelResponse(GetWorkModelResponseType newGetWorkModelResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE, newGetWorkModelResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkModelResponse(GetWorkModelResponseType newGetWorkModelResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE, newGetWorkModelResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkModelsType getGetWorkModels() {
        return (GetWorkModelsType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODELS, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkModels(GetWorkModelsType newGetWorkModels, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODELS, newGetWorkModels, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkModels(GetWorkModelsType newGetWorkModels) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODELS, newGetWorkModels);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkModelsResponseType getGetWorkModelsResponse() {
        return (GetWorkModelsResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkModelsResponse(GetWorkModelsResponseType newGetWorkModelsResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE, newGetWorkModelsResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkModelsResponse(GetWorkModelsResponseType newGetWorkModelsResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE, newGetWorkModelsResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkTypeType getGetWorkType() {
        return (GetWorkTypeType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkType(GetWorkTypeType newGetWorkType, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPE, newGetWorkType, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkType(GetWorkTypeType newGetWorkType) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPE, newGetWorkType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkTypeResponseType getGetWorkTypeResponse() {
        return (GetWorkTypeResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkTypeResponse(GetWorkTypeResponseType newGetWorkTypeResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE, newGetWorkTypeResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkTypeResponse(GetWorkTypeResponseType newGetWorkTypeResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE, newGetWorkTypeResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkTypesType getGetWorkTypes() {
        return (GetWorkTypesType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPES, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkTypes(GetWorkTypesType newGetWorkTypes, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPES, newGetWorkTypes, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkTypes(GetWorkTypesType newGetWorkTypes) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPES, newGetWorkTypes);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GetWorkTypesResponseType getGetWorkTypesResponse() {
        return (GetWorkTypesResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGetWorkTypesResponse(GetWorkTypesResponseType newGetWorkTypesResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE, newGetWorkTypesResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGetWorkTypesResponse(GetWorkTypesResponseType newGetWorkTypesResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE, newGetWorkTypesResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OnNotificationType getOnNotification() {
        return (OnNotificationType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ON_NOTIFICATION, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOnNotification(OnNotificationType newOnNotification, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ON_NOTIFICATION, newOnNotification, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOnNotification(OnNotificationType newOnNotification) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ON_NOTIFICATION, newOnNotification);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OnNotificationResponseType getOnNotificationResponse() {
        return (OnNotificationResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOnNotificationResponse(OnNotificationResponseType newOnNotificationResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE, newOnNotificationResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOnNotificationResponse(OnNotificationResponseType newOnNotificationResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE, newOnNotificationResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OpenWorkItemType getOpenWorkItem() {
        return (OpenWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__OPEN_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOpenWorkItem(OpenWorkItemType newOpenWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__OPEN_WORK_ITEM, newOpenWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOpenWorkItem(OpenWorkItemType newOpenWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__OPEN_WORK_ITEM, newOpenWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OpenWorkItemResponseType getOpenWorkItemResponse() {
        return (OpenWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOpenWorkItemResponse(OpenWorkItemResponseType newOpenWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE, newOpenWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOpenWorkItemResponse(OpenWorkItemResponseType newOpenWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE, newOpenWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PendWorkItem getPendWorkItem() {
        return (PendWorkItem)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__PEND_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPendWorkItem(PendWorkItem newPendWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__PEND_WORK_ITEM, newPendWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPendWorkItem(PendWorkItem newPendWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__PEND_WORK_ITEM, newPendWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PendWorkItemResponseType getPendWorkItemResponse() {
        return (PendWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPendWorkItemResponse(PendWorkItemResponseType newPendWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE, newPendWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPendWorkItemResponse(PendWorkItemResponseType newPendWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE, newPendWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PreviewWorkItemFromListType getPreviewWorkItemFromList() {
        return (PreviewWorkItemFromListType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPreviewWorkItemFromList(PreviewWorkItemFromListType newPreviewWorkItemFromList, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST, newPreviewWorkItemFromList, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPreviewWorkItemFromList(PreviewWorkItemFromListType newPreviewWorkItemFromList) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST, newPreviewWorkItemFromList);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PreviewWorkItemFromListResponseType getPreviewWorkItemFromListResponse() {
        return (PreviewWorkItemFromListResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPreviewWorkItemFromListResponse(PreviewWorkItemFromListResponseType newPreviewWorkItemFromListResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE, newPreviewWorkItemFromListResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPreviewWorkItemFromListResponse(PreviewWorkItemFromListResponseType newPreviewWorkItemFromListResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE, newPreviewWorkItemFromListResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PushNotificationType getPushNotification() {
        return (PushNotificationType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__PUSH_NOTIFICATION, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPushNotification(PushNotificationType newPushNotification, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__PUSH_NOTIFICATION, newPushNotification, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPushNotification(PushNotificationType newPushNotification) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__PUSH_NOTIFICATION, newPushNotification);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReallocateWorkItemType getReallocateWorkItem() {
        return (ReallocateWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReallocateWorkItem(ReallocateWorkItemType newReallocateWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM, newReallocateWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReallocateWorkItem(ReallocateWorkItemType newReallocateWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM, newReallocateWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReallocateWorkItemData getReallocateWorkItemData() {
        return (ReallocateWorkItemData)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReallocateWorkItemData(ReallocateWorkItemData newReallocateWorkItemData, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA, newReallocateWorkItemData, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReallocateWorkItemData(ReallocateWorkItemData newReallocateWorkItemData) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA, newReallocateWorkItemData);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReallocateWorkItemDataResponseType getReallocateWorkItemDataResponse() {
        return (ReallocateWorkItemDataResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReallocateWorkItemDataResponse(ReallocateWorkItemDataResponseType newReallocateWorkItemDataResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE, newReallocateWorkItemDataResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReallocateWorkItemDataResponse(ReallocateWorkItemDataResponseType newReallocateWorkItemDataResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE, newReallocateWorkItemDataResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReallocateWorkItemResponseType getReallocateWorkItemResponse() {
        return (ReallocateWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReallocateWorkItemResponse(ReallocateWorkItemResponseType newReallocateWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE, newReallocateWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReallocateWorkItemResponse(ReallocateWorkItemResponseType newReallocateWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE, newReallocateWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RescheduleWorkItem getRescheduleWorkItem() {
        return (RescheduleWorkItem)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRescheduleWorkItem(RescheduleWorkItem newRescheduleWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM, newRescheduleWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRescheduleWorkItem(RescheduleWorkItem newRescheduleWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM, newRescheduleWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RescheduleWorkItemResponseType getRescheduleWorkItemResponse() {
        return (RescheduleWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRescheduleWorkItemResponse(RescheduleWorkItemResponseType newRescheduleWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE, newRescheduleWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRescheduleWorkItemResponse(RescheduleWorkItemResponseType newRescheduleWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE, newRescheduleWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResumeWorkItemType getResumeWorkItem() {
        return (ResumeWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__RESUME_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResumeWorkItem(ResumeWorkItemType newResumeWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__RESUME_WORK_ITEM, newResumeWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResumeWorkItem(ResumeWorkItemType newResumeWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__RESUME_WORK_ITEM, newResumeWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResumeWorkItemResponseType getResumeWorkItemResponse() {
        return (ResumeWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResumeWorkItemResponse(ResumeWorkItemResponseType newResumeWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE, newResumeWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResumeWorkItemResponse(ResumeWorkItemResponseType newResumeWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE, newResumeWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SaveOpenWorkItemType getSaveOpenWorkItem() {
        return (SaveOpenWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSaveOpenWorkItem(SaveOpenWorkItemType newSaveOpenWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM, newSaveOpenWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSaveOpenWorkItem(SaveOpenWorkItemType newSaveOpenWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM, newSaveOpenWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SaveOpenWorkItemResponseType getSaveOpenWorkItemResponse() {
        return (SaveOpenWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSaveOpenWorkItemResponse(SaveOpenWorkItemResponseType newSaveOpenWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE, newSaveOpenWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSaveOpenWorkItemResponse(SaveOpenWorkItemResponseType newSaveOpenWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE, newSaveOpenWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemType getScheduleWorkItem() {
        return (ScheduleWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScheduleWorkItem(ScheduleWorkItemType newScheduleWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM, newScheduleWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScheduleWorkItem(ScheduleWorkItemType newScheduleWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM, newScheduleWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemResponseType getScheduleWorkItemResponse() {
        return (ScheduleWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScheduleWorkItemResponse(ScheduleWorkItemResponseType newScheduleWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE, newScheduleWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScheduleWorkItemResponse(ScheduleWorkItemResponseType newScheduleWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE, newScheduleWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemWithModelType getScheduleWorkItemWithModel() {
        return (ScheduleWorkItemWithModelType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScheduleWorkItemWithModel(ScheduleWorkItemWithModelType newScheduleWorkItemWithModel, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL, newScheduleWorkItemWithModel, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScheduleWorkItemWithModel(ScheduleWorkItemWithModelType newScheduleWorkItemWithModel) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL, newScheduleWorkItemWithModel);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleWorkItemWithModelResponseType getScheduleWorkItemWithModelResponse() {
        return (ScheduleWorkItemWithModelResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScheduleWorkItemWithModelResponse(ScheduleWorkItemWithModelResponseType newScheduleWorkItemWithModelResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE, newScheduleWorkItemWithModelResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScheduleWorkItemWithModelResponse(ScheduleWorkItemWithModelResponseType newScheduleWorkItemWithModelResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE, newScheduleWorkItemWithModelResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetOrgEntityConfigAttributesType getSetOrgEntityConfigAttributes() {
        return (SetOrgEntityConfigAttributesType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSetOrgEntityConfigAttributes(SetOrgEntityConfigAttributesType newSetOrgEntityConfigAttributes, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES, newSetOrgEntityConfigAttributes, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSetOrgEntityConfigAttributes(SetOrgEntityConfigAttributesType newSetOrgEntityConfigAttributes) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES, newSetOrgEntityConfigAttributes);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetOrgEntityConfigAttributesResponseType getSetOrgEntityConfigAttributesResponse() {
        return (SetOrgEntityConfigAttributesResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSetOrgEntityConfigAttributesResponse(SetOrgEntityConfigAttributesResponseType newSetOrgEntityConfigAttributesResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, newSetOrgEntityConfigAttributesResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSetOrgEntityConfigAttributesResponse(SetOrgEntityConfigAttributesResponseType newSetOrgEntityConfigAttributesResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE, newSetOrgEntityConfigAttributesResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetResourceOrderFilterCriteriaType getSetResourceOrderFilterCriteria() {
        return (SetResourceOrderFilterCriteriaType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSetResourceOrderFilterCriteria(SetResourceOrderFilterCriteriaType newSetResourceOrderFilterCriteria, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA, newSetResourceOrderFilterCriteria, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSetResourceOrderFilterCriteria(SetResourceOrderFilterCriteriaType newSetResourceOrderFilterCriteria) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA, newSetResourceOrderFilterCriteria);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetResourceOrderFilterCriteriaResponseType getSetResourceOrderFilterCriteriaResponse() {
        return (SetResourceOrderFilterCriteriaResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSetResourceOrderFilterCriteriaResponse(SetResourceOrderFilterCriteriaResponseType newSetResourceOrderFilterCriteriaResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE, newSetResourceOrderFilterCriteriaResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSetResourceOrderFilterCriteriaResponse(SetResourceOrderFilterCriteriaResponseType newSetResourceOrderFilterCriteriaResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE, newSetResourceOrderFilterCriteriaResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetWorkItemPriority getSetWorkItemPriority() {
        return (SetWorkItemPriority)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSetWorkItemPriority(SetWorkItemPriority newSetWorkItemPriority, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY, newSetWorkItemPriority, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSetWorkItemPriority(SetWorkItemPriority newSetWorkItemPriority) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY, newSetWorkItemPriority);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SetWorkItemPriorityResponseType getSetWorkItemPriorityResponse() {
        return (SetWorkItemPriorityResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSetWorkItemPriorityResponse(SetWorkItemPriorityResponseType newSetWorkItemPriorityResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE, newSetWorkItemPriorityResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSetWorkItemPriorityResponse(SetWorkItemPriorityResponseType newSetWorkItemPriorityResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE, newSetWorkItemPriorityResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SkipWorkItemType getSkipWorkItem() {
        return (SkipWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SKIP_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSkipWorkItem(SkipWorkItemType newSkipWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SKIP_WORK_ITEM, newSkipWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSkipWorkItem(SkipWorkItemType newSkipWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SKIP_WORK_ITEM, newSkipWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SkipWorkItemResponseType getSkipWorkItemResponse() {
        return (SkipWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSkipWorkItemResponse(SkipWorkItemResponseType newSkipWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE, newSkipWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSkipWorkItemResponse(SkipWorkItemResponseType newSkipWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE, newSkipWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public StartGroupType getStartGroup() {
        return (StartGroupType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__START_GROUP, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetStartGroup(StartGroupType newStartGroup, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__START_GROUP, newStartGroup, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartGroup(StartGroupType newStartGroup) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__START_GROUP, newStartGroup);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public StartGroupResponseType getStartGroupResponse() {
        return (StartGroupResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__START_GROUP_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetStartGroupResponse(StartGroupResponseType newStartGroupResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__START_GROUP_RESPONSE, newStartGroupResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartGroupResponse(StartGroupResponseType newStartGroupResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__START_GROUP_RESPONSE, newStartGroupResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SuspendWorkItemType getSuspendWorkItem() {
        return (SuspendWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SUSPEND_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSuspendWorkItem(SuspendWorkItemType newSuspendWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SUSPEND_WORK_ITEM, newSuspendWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSuspendWorkItem(SuspendWorkItemType newSuspendWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SUSPEND_WORK_ITEM, newSuspendWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SuspendWorkItemResponseType getSuspendWorkItemResponse() {
        return (SuspendWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSuspendWorkItemResponse(SuspendWorkItemResponseType newSuspendWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE, newSuspendWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSuspendWorkItemResponse(SuspendWorkItemResponseType newSuspendWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE, newSuspendWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnallocateWorkItemType getUnallocateWorkItem() {
        return (UnallocateWorkItemType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetUnallocateWorkItem(UnallocateWorkItemType newUnallocateWorkItem, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM, newUnallocateWorkItem, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUnallocateWorkItem(UnallocateWorkItemType newUnallocateWorkItem) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM, newUnallocateWorkItem);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnallocateWorkItemResponseType getUnallocateWorkItemResponse() {
        return (UnallocateWorkItemResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetUnallocateWorkItemResponse(UnallocateWorkItemResponseType newUnallocateWorkItemResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE, newUnallocateWorkItemResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUnallocateWorkItemResponse(UnallocateWorkItemResponseType newUnallocateWorkItemResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE, newUnallocateWorkItemResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnlockWorkListViewType getUnlockWorkListView() {
        return (UnlockWorkListViewType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetUnlockWorkListView(UnlockWorkListViewType newUnlockWorkListView, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW, newUnlockWorkListView, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUnlockWorkListView(UnlockWorkListViewType newUnlockWorkListView) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW, newUnlockWorkListView);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnlockWorkListViewResponseType getUnlockWorkListViewResponse() {
        return (UnlockWorkListViewResponseType)getMixed().get(N2BRMPackage.Literals.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetUnlockWorkListViewResponse(UnlockWorkListViewResponseType newUnlockWorkListViewResponse, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(N2BRMPackage.Literals.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE, newUnlockWorkListViewResponse, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUnlockWorkListViewResponse(UnlockWorkListViewResponseType newUnlockWorkListViewResponse) {
        ((FeatureMap.Internal)getMixed()).set(N2BRMPackage.Literals.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE, newUnlockWorkListViewResponse);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.DOCUMENT_ROOT__MIXED:
                return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW:
                return basicSetAddCurrentResourceToView(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE:
                return basicSetAddCurrentResourceToViewResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM:
                return basicSetAllocateAndOpenNextWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE:
                return basicSetAllocateAndOpenNextWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM:
                return basicSetAllocateAndOpenWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE:
                return basicSetAllocateAndOpenWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM:
                return basicSetAllocateWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE:
                return basicSetAllocateWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM:
                return basicSetAsyncCancelWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE:
                return basicSetAsyncCancelWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP:
                return basicSetAsyncEndGroup(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE:
                return basicSetAsyncEndGroupResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM:
                return basicSetAsyncRescheduleWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE:
                return basicSetAsyncRescheduleWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM:
                return basicSetAsyncResumeWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE:
                return basicSetAsyncResumeWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM:
                return basicSetAsyncScheduleWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE:
                return basicSetAsyncScheduleWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL:
                return basicSetAsyncScheduleWorkItemWithModel(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                return basicSetAsyncScheduleWorkItemWithModelResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP:
                return basicSetAsyncStartGroup(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE:
                return basicSetAsyncStartGroupResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM:
                return basicSetAsyncSuspendWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE:
                return basicSetAsyncSuspendWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM:
                return basicSetCancelWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE:
                return basicSetCancelWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION:
                return basicSetChainedWorkItemNotification(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM:
                return basicSetCloseWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE:
                return basicSetCloseWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM:
                return basicSetCompleteWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE:
                return basicSetCompleteWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW:
                return basicSetCreateWorkListView(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE:
                return basicSetCreateWorkListViewResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW:
                return basicSetDeleteCurrentResourceFromView(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE:
                return basicSetDeleteCurrentResourceFromViewResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return basicSetDeleteOrgEntityConfigAttributes(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return basicSetDeleteOrgEntityConfigAttributesResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW:
                return basicSetDeleteWorkListView(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE:
                return basicSetDeleteWorkListViewResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW:
                return basicSetEditWorkListView(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE:
                return basicSetEditWorkListViewResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM:
                return basicSetEnableWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE:
                return basicSetEnableWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP:
                return basicSetEndGroup(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP_RESPONSE:
                return basicSetEndGroupResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS:
                return basicSetGetAllocatedWorkListItems(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS:
                return basicSetGetBatchGroupIds(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE:
                return basicSetGetBatchGroupIdsResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS:
                return basicSetGetBatchWorkItemIds(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE:
                return basicSetGetBatchWorkItemIdsResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS:
                return basicSetGetEditableWorkListViews(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE:
                return basicSetGetEditableWorkListViewsResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET:
                return basicSetGetOfferSet(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE:
                return basicSetGetOfferSetResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return basicSetGetOrgEntityConfigAttributes(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE:
                return basicSetGetOrgEntityConfigAttributesAvailable(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE:
                return basicSetGetOrgEntityConfigAttributesAvailableResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return basicSetGetOrgEntityConfigAttributesResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS:
                return basicSetGetPublicWorkListViews(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE:
                return basicSetGetPublicWorkListViewsResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA:
                return basicSetGetResourceOrderFilterCriteria(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                return basicSetGetResourceOrderFilterCriteriaResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE:
                return basicSetGetViewsForResource(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE:
                return basicSetGetViewsForResourceResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER:
                return basicSetGetWorkItemHeader(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE:
                return basicSetGetWorkItemHeaderResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER:
                return basicSetGetWorkItemOrderFilter(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE:
                return basicSetGetWorkItemOrderFilterResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS:
                return basicSetGetWorkListItems(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA:
                return basicSetGetWorkListItemsForGlobalData(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE:
                return basicSetGetWorkListItemsForGlobalDataResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW:
                return basicSetGetWorkListItemsForView(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE:
                return basicSetGetWorkListItemsForViewResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE:
                return basicSetGetWorkListItemsResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS:
                return basicSetGetWorkListViewDetails(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE:
                return basicSetGetWorkListViewDetailsResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL:
                return basicSetGetWorkModel(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE:
                return basicSetGetWorkModelResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS:
                return basicSetGetWorkModels(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE:
                return basicSetGetWorkModelsResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE:
                return basicSetGetWorkType(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE:
                return basicSetGetWorkTypeResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES:
                return basicSetGetWorkTypes(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE:
                return basicSetGetWorkTypesResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION:
                return basicSetOnNotification(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE:
                return basicSetOnNotificationResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM:
                return basicSetOpenWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE:
                return basicSetOpenWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM:
                return basicSetPendWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE:
                return basicSetPendWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST:
                return basicSetPreviewWorkItemFromList(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE:
                return basicSetPreviewWorkItemFromListResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__PUSH_NOTIFICATION:
                return basicSetPushNotification(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM:
                return basicSetReallocateWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA:
                return basicSetReallocateWorkItemData(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE:
                return basicSetReallocateWorkItemDataResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE:
                return basicSetReallocateWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM:
                return basicSetRescheduleWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE:
                return basicSetRescheduleWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM:
                return basicSetResumeWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE:
                return basicSetResumeWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM:
                return basicSetSaveOpenWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE:
                return basicSetSaveOpenWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM:
                return basicSetScheduleWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE:
                return basicSetScheduleWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL:
                return basicSetScheduleWorkItemWithModel(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                return basicSetScheduleWorkItemWithModelResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return basicSetSetOrgEntityConfigAttributes(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return basicSetSetOrgEntityConfigAttributesResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA:
                return basicSetSetResourceOrderFilterCriteria(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                return basicSetSetResourceOrderFilterCriteriaResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY:
                return basicSetSetWorkItemPriority(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE:
                return basicSetSetWorkItemPriorityResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM:
                return basicSetSkipWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE:
                return basicSetSkipWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP:
                return basicSetStartGroup(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP_RESPONSE:
                return basicSetStartGroupResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM:
                return basicSetSuspendWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE:
                return basicSetSuspendWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM:
                return basicSetUnallocateWorkItem(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE:
                return basicSetUnallocateWorkItemResponse(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW:
                return basicSetUnlockWorkListView(null, msgs);
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE:
                return basicSetUnlockWorkListViewResponse(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.DOCUMENT_ROOT__MIXED:
                if (coreType) return getMixed();
                return ((FeatureMap.Internal)getMixed()).getWrapper();
            case N2BRMPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                if (coreType) return getXMLNSPrefixMap();
                else return getXMLNSPrefixMap().map();
            case N2BRMPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                if (coreType) return getXSISchemaLocation();
                else return getXSISchemaLocation().map();
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW:
                return getAddCurrentResourceToView();
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE:
                return getAddCurrentResourceToViewResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM:
                return getAllocateAndOpenNextWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE:
                return getAllocateAndOpenNextWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM:
                return getAllocateAndOpenWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE:
                return getAllocateAndOpenWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM:
                return getAllocateWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE:
                return getAllocateWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM:
                return getAsyncCancelWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE:
                return getAsyncCancelWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP:
                return getAsyncEndGroup();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE:
                return getAsyncEndGroupResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM:
                return getAsyncRescheduleWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE:
                return getAsyncRescheduleWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM:
                return getAsyncResumeWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE:
                return getAsyncResumeWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM:
                return getAsyncScheduleWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE:
                return getAsyncScheduleWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL:
                return getAsyncScheduleWorkItemWithModel();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                return getAsyncScheduleWorkItemWithModelResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP:
                return getAsyncStartGroup();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE:
                return getAsyncStartGroupResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM:
                return getAsyncSuspendWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE:
                return getAsyncSuspendWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM:
                return getCancelWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE:
                return getCancelWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION:
                return getChainedWorkItemNotification();
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM:
                return getCloseWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE:
                return getCloseWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM:
                return getCompleteWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE:
                return getCompleteWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW:
                return getCreateWorkListView();
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE:
                return getCreateWorkListViewResponse();
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW:
                return getDeleteCurrentResourceFromView();
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE:
                return getDeleteCurrentResourceFromViewResponse();
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return getDeleteOrgEntityConfigAttributes();
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return getDeleteOrgEntityConfigAttributesResponse();
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW:
                return getDeleteWorkListView();
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE:
                return getDeleteWorkListViewResponse();
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW:
                return getEditWorkListView();
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE:
                return getEditWorkListViewResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM:
                return getEnableWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE:
                return getEnableWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP:
                return getEndGroup();
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP_RESPONSE:
                return getEndGroupResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS:
                return getGetAllocatedWorkListItems();
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS:
                return getGetBatchGroupIds();
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE:
                return getGetBatchGroupIdsResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS:
                return getGetBatchWorkItemIds();
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE:
                return getGetBatchWorkItemIdsResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS:
                return getGetEditableWorkListViews();
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE:
                return getGetEditableWorkListViewsResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET:
                return getGetOfferSet();
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE:
                return getGetOfferSetResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return getGetOrgEntityConfigAttributes();
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE:
                return getGetOrgEntityConfigAttributesAvailable();
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE:
                return getGetOrgEntityConfigAttributesAvailableResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return getGetOrgEntityConfigAttributesResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS:
                return getGetPublicWorkListViews();
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE:
                return getGetPublicWorkListViewsResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA:
                return getGetResourceOrderFilterCriteria();
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                return getGetResourceOrderFilterCriteriaResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE:
                return getGetViewsForResource();
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE:
                return getGetViewsForResourceResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER:
                return getGetWorkItemHeader();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE:
                return getGetWorkItemHeaderResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER:
                return getGetWorkItemOrderFilter();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE:
                return getGetWorkItemOrderFilterResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS:
                return getGetWorkListItems();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA:
                return getGetWorkListItemsForGlobalData();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE:
                return getGetWorkListItemsForGlobalDataResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW:
                return getGetWorkListItemsForView();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE:
                return getGetWorkListItemsForViewResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE:
                return getGetWorkListItemsResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS:
                return getGetWorkListViewDetails();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE:
                return getGetWorkListViewDetailsResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL:
                return getGetWorkModel();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE:
                return getGetWorkModelResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS:
                return getGetWorkModels();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE:
                return getGetWorkModelsResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE:
                return getGetWorkType();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE:
                return getGetWorkTypeResponse();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES:
                return getGetWorkTypes();
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE:
                return getGetWorkTypesResponse();
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION:
                return getOnNotification();
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE:
                return getOnNotificationResponse();
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM:
                return getOpenWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE:
                return getOpenWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM:
                return getPendWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE:
                return getPendWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST:
                return getPreviewWorkItemFromList();
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE:
                return getPreviewWorkItemFromListResponse();
            case N2BRMPackage.DOCUMENT_ROOT__PUSH_NOTIFICATION:
                return getPushNotification();
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM:
                return getReallocateWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA:
                return getReallocateWorkItemData();
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE:
                return getReallocateWorkItemDataResponse();
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE:
                return getReallocateWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM:
                return getRescheduleWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE:
                return getRescheduleWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM:
                return getResumeWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE:
                return getResumeWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM:
                return getSaveOpenWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE:
                return getSaveOpenWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM:
                return getScheduleWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE:
                return getScheduleWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL:
                return getScheduleWorkItemWithModel();
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                return getScheduleWorkItemWithModelResponse();
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return getSetOrgEntityConfigAttributes();
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return getSetOrgEntityConfigAttributesResponse();
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA:
                return getSetResourceOrderFilterCriteria();
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                return getSetResourceOrderFilterCriteriaResponse();
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY:
                return getSetWorkItemPriority();
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE:
                return getSetWorkItemPriorityResponse();
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM:
                return getSkipWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE:
                return getSkipWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP:
                return getStartGroup();
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP_RESPONSE:
                return getStartGroupResponse();
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM:
                return getSuspendWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE:
                return getSuspendWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM:
                return getUnallocateWorkItem();
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE:
                return getUnallocateWorkItemResponse();
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW:
                return getUnlockWorkListView();
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE:
                return getUnlockWorkListViewResponse();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case N2BRMPackage.DOCUMENT_ROOT__MIXED:
                ((FeatureMap.Internal)getMixed()).set(newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                ((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                ((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW:
                setAddCurrentResourceToView((AddCurrentResourceToViewType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE:
                setAddCurrentResourceToViewResponse((AddCurrentResourceToViewResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM:
                setAllocateAndOpenNextWorkItem((AllocateAndOpenNextWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE:
                setAllocateAndOpenNextWorkItemResponse((AllocateAndOpenNextWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM:
                setAllocateAndOpenWorkItem((AllocateAndOpenWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE:
                setAllocateAndOpenWorkItemResponse((AllocateAndOpenWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM:
                setAllocateWorkItem((AllocateWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE:
                setAllocateWorkItemResponse((AllocateWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM:
                setAsyncCancelWorkItem((AsyncCancelWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE:
                setAsyncCancelWorkItemResponse((AsyncCancelWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP:
                setAsyncEndGroup((AsyncEndGroupType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE:
                setAsyncEndGroupResponse((AsyncEndGroupResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM:
                setAsyncRescheduleWorkItem((AsyncRescheduleWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE:
                setAsyncRescheduleWorkItemResponse((AsyncRescheduleWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM:
                setAsyncResumeWorkItem((AsyncResumeWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE:
                setAsyncResumeWorkItemResponse((AsyncResumeWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM:
                setAsyncScheduleWorkItem((AsyncScheduleWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE:
                setAsyncScheduleWorkItemResponse((AsyncScheduleWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL:
                setAsyncScheduleWorkItemWithModel((AsyncScheduleWorkItemWithModelType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                setAsyncScheduleWorkItemWithModelResponse((AsyncScheduleWorkItemWithModelResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP:
                setAsyncStartGroup((AsyncStartGroupType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE:
                setAsyncStartGroupResponse((AsyncStartGroupResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM:
                setAsyncSuspendWorkItem((AsyncSuspendWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE:
                setAsyncSuspendWorkItemResponse((AsyncSuspendWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM:
                setCancelWorkItem((CancelWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE:
                setCancelWorkItemResponse((CancelWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION:
                setChainedWorkItemNotification((ChainedWorkItemNotificationType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM:
                setCloseWorkItem((CloseWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE:
                setCloseWorkItemResponse((CloseWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM:
                setCompleteWorkItem((CompleteWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE:
                setCompleteWorkItemResponse((CompleteWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW:
                setCreateWorkListView((WorkListViewEdit)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE:
                setCreateWorkListViewResponse((CreateWorkListViewResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW:
                setDeleteCurrentResourceFromView((DeleteCurrentResourceFromViewType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE:
                setDeleteCurrentResourceFromViewResponse((DeleteCurrentResourceFromViewResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES:
                setDeleteOrgEntityConfigAttributes((DeleteOrgEntityConfigAttributesType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                setDeleteOrgEntityConfigAttributesResponse((DeleteOrgEntityConfigAttributesResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW:
                setDeleteWorkListView((DeleteWorkListViewType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE:
                setDeleteWorkListViewResponse((DeleteWorkListViewResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW:
                setEditWorkListView((EditWorkListViewType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE:
                setEditWorkListViewResponse((EditWorkListViewResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM:
                setEnableWorkItem((EnableWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE:
                setEnableWorkItemResponse((EnableWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP:
                setEndGroup((EndGroupType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP_RESPONSE:
                setEndGroupResponse((EndGroupResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS:
                setGetAllocatedWorkListItems((GetAllocatedWorkListItemsType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS:
                setGetBatchGroupIds((EObject)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE:
                setGetBatchGroupIdsResponse((GetBatchGroupIdsResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS:
                setGetBatchWorkItemIds((EObject)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE:
                setGetBatchWorkItemIdsResponse((GetBatchWorkItemIdsResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS:
                setGetEditableWorkListViews((GetEditableWorkListViewsType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE:
                setGetEditableWorkListViewsResponse((GetEditableWorkListViewsResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET:
                setGetOfferSet((GetOfferSetType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE:
                setGetOfferSetResponse((GetOfferSetResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                setGetOrgEntityConfigAttributes((GetOrgEntityConfigAttributesType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE:
                setGetOrgEntityConfigAttributesAvailable((GetOrgEntityConfigAttributesAvailableType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE:
                setGetOrgEntityConfigAttributesAvailableResponse((GetOrgEntityConfigAttributesAvailableResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                setGetOrgEntityConfigAttributesResponse((GetOrgEntityConfigAttributesResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS:
                setGetPublicWorkListViews((GetPublicWorkListViewsType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE:
                setGetPublicWorkListViewsResponse((GetPublicWorkListViewsResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA:
                setGetResourceOrderFilterCriteria((GetResourceOrderFilterCriteriaType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                setGetResourceOrderFilterCriteriaResponse((GetResourceOrderFilterCriteriaResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE:
                setGetViewsForResource((GetViewsForResourceType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE:
                setGetViewsForResourceResponse((GetViewsForResourceResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER:
                setGetWorkItemHeader((GetWorkItemHeaderType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE:
                setGetWorkItemHeaderResponse((GetWorkItemHeaderResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER:
                setGetWorkItemOrderFilter((GetWorkItemOrderFilterType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE:
                setGetWorkItemOrderFilterResponse((GetWorkItemOrderFilterResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS:
                setGetWorkListItems((GetWorkListItemsType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA:
                setGetWorkListItemsForGlobalData((GetWorkListItemsForGlobalDataType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE:
                setGetWorkListItemsForGlobalDataResponse((GetWorkListItemsForGlobalDataResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW:
                setGetWorkListItemsForView((GetWorkListItemsForViewType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE:
                setGetWorkListItemsForViewResponse((GetWorkListItemsForViewResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE:
                setGetWorkListItemsResponse((GetWorkListItemsResponseType1)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS:
                setGetWorkListViewDetails((GetWorkListViewDetailsType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE:
                setGetWorkListViewDetailsResponse((WorkListView)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL:
                setGetWorkModel((GetWorkModelType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE:
                setGetWorkModelResponse((GetWorkModelResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS:
                setGetWorkModels((GetWorkModelsType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE:
                setGetWorkModelsResponse((GetWorkModelsResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE:
                setGetWorkType((GetWorkTypeType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE:
                setGetWorkTypeResponse((GetWorkTypeResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES:
                setGetWorkTypes((GetWorkTypesType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE:
                setGetWorkTypesResponse((GetWorkTypesResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION:
                setOnNotification((OnNotificationType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE:
                setOnNotificationResponse((OnNotificationResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM:
                setOpenWorkItem((OpenWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE:
                setOpenWorkItemResponse((OpenWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM:
                setPendWorkItem((PendWorkItem)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE:
                setPendWorkItemResponse((PendWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST:
                setPreviewWorkItemFromList((PreviewWorkItemFromListType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE:
                setPreviewWorkItemFromListResponse((PreviewWorkItemFromListResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PUSH_NOTIFICATION:
                setPushNotification((PushNotificationType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM:
                setReallocateWorkItem((ReallocateWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA:
                setReallocateWorkItemData((ReallocateWorkItemData)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE:
                setReallocateWorkItemDataResponse((ReallocateWorkItemDataResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE:
                setReallocateWorkItemResponse((ReallocateWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM:
                setRescheduleWorkItem((RescheduleWorkItem)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE:
                setRescheduleWorkItemResponse((RescheduleWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM:
                setResumeWorkItem((ResumeWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE:
                setResumeWorkItemResponse((ResumeWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM:
                setSaveOpenWorkItem((SaveOpenWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE:
                setSaveOpenWorkItemResponse((SaveOpenWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM:
                setScheduleWorkItem((ScheduleWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE:
                setScheduleWorkItemResponse((ScheduleWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL:
                setScheduleWorkItemWithModel((ScheduleWorkItemWithModelType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                setScheduleWorkItemWithModelResponse((ScheduleWorkItemWithModelResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                setSetOrgEntityConfigAttributes((SetOrgEntityConfigAttributesType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                setSetOrgEntityConfigAttributesResponse((SetOrgEntityConfigAttributesResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA:
                setSetResourceOrderFilterCriteria((SetResourceOrderFilterCriteriaType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                setSetResourceOrderFilterCriteriaResponse((SetResourceOrderFilterCriteriaResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY:
                setSetWorkItemPriority((SetWorkItemPriority)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE:
                setSetWorkItemPriorityResponse((SetWorkItemPriorityResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM:
                setSkipWorkItem((SkipWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE:
                setSkipWorkItemResponse((SkipWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP:
                setStartGroup((StartGroupType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP_RESPONSE:
                setStartGroupResponse((StartGroupResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM:
                setSuspendWorkItem((SuspendWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE:
                setSuspendWorkItemResponse((SuspendWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM:
                setUnallocateWorkItem((UnallocateWorkItemType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE:
                setUnallocateWorkItemResponse((UnallocateWorkItemResponseType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW:
                setUnlockWorkListView((UnlockWorkListViewType)newValue);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE:
                setUnlockWorkListViewResponse((UnlockWorkListViewResponseType)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case N2BRMPackage.DOCUMENT_ROOT__MIXED:
                getMixed().clear();
                return;
            case N2BRMPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                getXMLNSPrefixMap().clear();
                return;
            case N2BRMPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                getXSISchemaLocation().clear();
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW:
                setAddCurrentResourceToView((AddCurrentResourceToViewType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE:
                setAddCurrentResourceToViewResponse((AddCurrentResourceToViewResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM:
                setAllocateAndOpenNextWorkItem((AllocateAndOpenNextWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE:
                setAllocateAndOpenNextWorkItemResponse((AllocateAndOpenNextWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM:
                setAllocateAndOpenWorkItem((AllocateAndOpenWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE:
                setAllocateAndOpenWorkItemResponse((AllocateAndOpenWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM:
                setAllocateWorkItem((AllocateWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE:
                setAllocateWorkItemResponse((AllocateWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM:
                setAsyncCancelWorkItem((AsyncCancelWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE:
                setAsyncCancelWorkItemResponse((AsyncCancelWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP:
                setAsyncEndGroup((AsyncEndGroupType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE:
                setAsyncEndGroupResponse((AsyncEndGroupResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM:
                setAsyncRescheduleWorkItem((AsyncRescheduleWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE:
                setAsyncRescheduleWorkItemResponse((AsyncRescheduleWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM:
                setAsyncResumeWorkItem((AsyncResumeWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE:
                setAsyncResumeWorkItemResponse((AsyncResumeWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM:
                setAsyncScheduleWorkItem((AsyncScheduleWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE:
                setAsyncScheduleWorkItemResponse((AsyncScheduleWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL:
                setAsyncScheduleWorkItemWithModel((AsyncScheduleWorkItemWithModelType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                setAsyncScheduleWorkItemWithModelResponse((AsyncScheduleWorkItemWithModelResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP:
                setAsyncStartGroup((AsyncStartGroupType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE:
                setAsyncStartGroupResponse((AsyncStartGroupResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM:
                setAsyncSuspendWorkItem((AsyncSuspendWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE:
                setAsyncSuspendWorkItemResponse((AsyncSuspendWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM:
                setCancelWorkItem((CancelWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE:
                setCancelWorkItemResponse((CancelWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION:
                setChainedWorkItemNotification((ChainedWorkItemNotificationType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM:
                setCloseWorkItem((CloseWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE:
                setCloseWorkItemResponse((CloseWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM:
                setCompleteWorkItem((CompleteWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE:
                setCompleteWorkItemResponse((CompleteWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW:
                setCreateWorkListView((WorkListViewEdit)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE:
                setCreateWorkListViewResponse((CreateWorkListViewResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW:
                setDeleteCurrentResourceFromView((DeleteCurrentResourceFromViewType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE:
                setDeleteCurrentResourceFromViewResponse((DeleteCurrentResourceFromViewResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES:
                setDeleteOrgEntityConfigAttributes((DeleteOrgEntityConfigAttributesType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                setDeleteOrgEntityConfigAttributesResponse((DeleteOrgEntityConfigAttributesResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW:
                setDeleteWorkListView((DeleteWorkListViewType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE:
                setDeleteWorkListViewResponse((DeleteWorkListViewResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW:
                setEditWorkListView((EditWorkListViewType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE:
                setEditWorkListViewResponse((EditWorkListViewResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM:
                setEnableWorkItem((EnableWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE:
                setEnableWorkItemResponse((EnableWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP:
                setEndGroup((EndGroupType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP_RESPONSE:
                setEndGroupResponse((EndGroupResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS:
                setGetAllocatedWorkListItems((GetAllocatedWorkListItemsType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS:
                setGetBatchGroupIds((EObject)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE:
                setGetBatchGroupIdsResponse((GetBatchGroupIdsResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS:
                setGetBatchWorkItemIds((EObject)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE:
                setGetBatchWorkItemIdsResponse((GetBatchWorkItemIdsResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS:
                setGetEditableWorkListViews((GetEditableWorkListViewsType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE:
                setGetEditableWorkListViewsResponse((GetEditableWorkListViewsResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET:
                setGetOfferSet((GetOfferSetType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE:
                setGetOfferSetResponse((GetOfferSetResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                setGetOrgEntityConfigAttributes((GetOrgEntityConfigAttributesType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE:
                setGetOrgEntityConfigAttributesAvailable((GetOrgEntityConfigAttributesAvailableType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE:
                setGetOrgEntityConfigAttributesAvailableResponse((GetOrgEntityConfigAttributesAvailableResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                setGetOrgEntityConfigAttributesResponse((GetOrgEntityConfigAttributesResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS:
                setGetPublicWorkListViews((GetPublicWorkListViewsType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE:
                setGetPublicWorkListViewsResponse((GetPublicWorkListViewsResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA:
                setGetResourceOrderFilterCriteria((GetResourceOrderFilterCriteriaType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                setGetResourceOrderFilterCriteriaResponse((GetResourceOrderFilterCriteriaResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE:
                setGetViewsForResource((GetViewsForResourceType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE:
                setGetViewsForResourceResponse((GetViewsForResourceResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER:
                setGetWorkItemHeader((GetWorkItemHeaderType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE:
                setGetWorkItemHeaderResponse((GetWorkItemHeaderResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER:
                setGetWorkItemOrderFilter((GetWorkItemOrderFilterType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE:
                setGetWorkItemOrderFilterResponse((GetWorkItemOrderFilterResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS:
                setGetWorkListItems((GetWorkListItemsType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA:
                setGetWorkListItemsForGlobalData((GetWorkListItemsForGlobalDataType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE:
                setGetWorkListItemsForGlobalDataResponse((GetWorkListItemsForGlobalDataResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW:
                setGetWorkListItemsForView((GetWorkListItemsForViewType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE:
                setGetWorkListItemsForViewResponse((GetWorkListItemsForViewResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE:
                setGetWorkListItemsResponse((GetWorkListItemsResponseType1)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS:
                setGetWorkListViewDetails((GetWorkListViewDetailsType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE:
                setGetWorkListViewDetailsResponse((WorkListView)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL:
                setGetWorkModel((GetWorkModelType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE:
                setGetWorkModelResponse((GetWorkModelResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS:
                setGetWorkModels((GetWorkModelsType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE:
                setGetWorkModelsResponse((GetWorkModelsResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE:
                setGetWorkType((GetWorkTypeType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE:
                setGetWorkTypeResponse((GetWorkTypeResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES:
                setGetWorkTypes((GetWorkTypesType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE:
                setGetWorkTypesResponse((GetWorkTypesResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION:
                setOnNotification((OnNotificationType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE:
                setOnNotificationResponse((OnNotificationResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM:
                setOpenWorkItem((OpenWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE:
                setOpenWorkItemResponse((OpenWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM:
                setPendWorkItem((PendWorkItem)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE:
                setPendWorkItemResponse((PendWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST:
                setPreviewWorkItemFromList((PreviewWorkItemFromListType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE:
                setPreviewWorkItemFromListResponse((PreviewWorkItemFromListResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__PUSH_NOTIFICATION:
                setPushNotification((PushNotificationType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM:
                setReallocateWorkItem((ReallocateWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA:
                setReallocateWorkItemData((ReallocateWorkItemData)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE:
                setReallocateWorkItemDataResponse((ReallocateWorkItemDataResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE:
                setReallocateWorkItemResponse((ReallocateWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM:
                setRescheduleWorkItem((RescheduleWorkItem)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE:
                setRescheduleWorkItemResponse((RescheduleWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM:
                setResumeWorkItem((ResumeWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE:
                setResumeWorkItemResponse((ResumeWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM:
                setSaveOpenWorkItem((SaveOpenWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE:
                setSaveOpenWorkItemResponse((SaveOpenWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM:
                setScheduleWorkItem((ScheduleWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE:
                setScheduleWorkItemResponse((ScheduleWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL:
                setScheduleWorkItemWithModel((ScheduleWorkItemWithModelType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                setScheduleWorkItemWithModelResponse((ScheduleWorkItemWithModelResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                setSetOrgEntityConfigAttributes((SetOrgEntityConfigAttributesType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                setSetOrgEntityConfigAttributesResponse((SetOrgEntityConfigAttributesResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA:
                setSetResourceOrderFilterCriteria((SetResourceOrderFilterCriteriaType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                setSetResourceOrderFilterCriteriaResponse((SetResourceOrderFilterCriteriaResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY:
                setSetWorkItemPriority((SetWorkItemPriority)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE:
                setSetWorkItemPriorityResponse((SetWorkItemPriorityResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM:
                setSkipWorkItem((SkipWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE:
                setSkipWorkItemResponse((SkipWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP:
                setStartGroup((StartGroupType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP_RESPONSE:
                setStartGroupResponse((StartGroupResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM:
                setSuspendWorkItem((SuspendWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE:
                setSuspendWorkItemResponse((SuspendWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM:
                setUnallocateWorkItem((UnallocateWorkItemType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE:
                setUnallocateWorkItemResponse((UnallocateWorkItemResponseType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW:
                setUnlockWorkListView((UnlockWorkListViewType)null);
                return;
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE:
                setUnlockWorkListViewResponse((UnlockWorkListViewResponseType)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case N2BRMPackage.DOCUMENT_ROOT__MIXED:
                return mixed != null && !mixed.isEmpty();
            case N2BRMPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
            case N2BRMPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW:
                return getAddCurrentResourceToView() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE:
                return getAddCurrentResourceToViewResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM:
                return getAllocateAndOpenNextWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE:
                return getAllocateAndOpenNextWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM:
                return getAllocateAndOpenWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE:
                return getAllocateAndOpenWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM:
                return getAllocateWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE:
                return getAllocateWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM:
                return getAsyncCancelWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE:
                return getAsyncCancelWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP:
                return getAsyncEndGroup() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE:
                return getAsyncEndGroupResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM:
                return getAsyncRescheduleWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE:
                return getAsyncRescheduleWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM:
                return getAsyncResumeWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE:
                return getAsyncResumeWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM:
                return getAsyncScheduleWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE:
                return getAsyncScheduleWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL:
                return getAsyncScheduleWorkItemWithModel() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                return getAsyncScheduleWorkItemWithModelResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP:
                return getAsyncStartGroup() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE:
                return getAsyncStartGroupResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM:
                return getAsyncSuspendWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE:
                return getAsyncSuspendWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM:
                return getCancelWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE:
                return getCancelWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION:
                return getChainedWorkItemNotification() != null;
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM:
                return getCloseWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE:
                return getCloseWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM:
                return getCompleteWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE:
                return getCompleteWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW:
                return getCreateWorkListView() != null;
            case N2BRMPackage.DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE:
                return getCreateWorkListViewResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW:
                return getDeleteCurrentResourceFromView() != null;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE:
                return getDeleteCurrentResourceFromViewResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return getDeleteOrgEntityConfigAttributes() != null;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return getDeleteOrgEntityConfigAttributesResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW:
                return getDeleteWorkListView() != null;
            case N2BRMPackage.DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE:
                return getDeleteWorkListViewResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW:
                return getEditWorkListView() != null;
            case N2BRMPackage.DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE:
                return getEditWorkListViewResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM:
                return getEnableWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE:
                return getEnableWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP:
                return getEndGroup() != null;
            case N2BRMPackage.DOCUMENT_ROOT__END_GROUP_RESPONSE:
                return getEndGroupResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS:
                return getGetAllocatedWorkListItems() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS:
                return getGetBatchGroupIds() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE:
                return getGetBatchGroupIdsResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS:
                return getGetBatchWorkItemIds() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE:
                return getGetBatchWorkItemIdsResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS:
                return getGetEditableWorkListViews() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE:
                return getGetEditableWorkListViewsResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET:
                return getGetOfferSet() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE:
                return getGetOfferSetResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return getGetOrgEntityConfigAttributes() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE:
                return getGetOrgEntityConfigAttributesAvailable() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE:
                return getGetOrgEntityConfigAttributesAvailableResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return getGetOrgEntityConfigAttributesResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS:
                return getGetPublicWorkListViews() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE:
                return getGetPublicWorkListViewsResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA:
                return getGetResourceOrderFilterCriteria() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                return getGetResourceOrderFilterCriteriaResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE:
                return getGetViewsForResource() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE:
                return getGetViewsForResourceResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER:
                return getGetWorkItemHeader() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE:
                return getGetWorkItemHeaderResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER:
                return getGetWorkItemOrderFilter() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE:
                return getGetWorkItemOrderFilterResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS:
                return getGetWorkListItems() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA:
                return getGetWorkListItemsForGlobalData() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE:
                return getGetWorkListItemsForGlobalDataResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW:
                return getGetWorkListItemsForView() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE:
                return getGetWorkListItemsForViewResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE:
                return getGetWorkListItemsResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS:
                return getGetWorkListViewDetails() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE:
                return getGetWorkListViewDetailsResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL:
                return getGetWorkModel() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE:
                return getGetWorkModelResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS:
                return getGetWorkModels() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE:
                return getGetWorkModelsResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE:
                return getGetWorkType() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE:
                return getGetWorkTypeResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES:
                return getGetWorkTypes() != null;
            case N2BRMPackage.DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE:
                return getGetWorkTypesResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION:
                return getOnNotification() != null;
            case N2BRMPackage.DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE:
                return getOnNotificationResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM:
                return getOpenWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE:
                return getOpenWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM:
                return getPendWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE:
                return getPendWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST:
                return getPreviewWorkItemFromList() != null;
            case N2BRMPackage.DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE:
                return getPreviewWorkItemFromListResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__PUSH_NOTIFICATION:
                return getPushNotification() != null;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM:
                return getReallocateWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA:
                return getReallocateWorkItemData() != null;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE:
                return getReallocateWorkItemDataResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE:
                return getReallocateWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM:
                return getRescheduleWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE:
                return getRescheduleWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM:
                return getResumeWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE:
                return getResumeWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM:
                return getSaveOpenWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE:
                return getSaveOpenWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM:
                return getScheduleWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE:
                return getScheduleWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL:
                return getScheduleWorkItemWithModel() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE:
                return getScheduleWorkItemWithModelResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES:
                return getSetOrgEntityConfigAttributes() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE:
                return getSetOrgEntityConfigAttributesResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA:
                return getSetResourceOrderFilterCriteria() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE:
                return getSetResourceOrderFilterCriteriaResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY:
                return getSetWorkItemPriority() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE:
                return getSetWorkItemPriorityResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM:
                return getSkipWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE:
                return getSkipWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP:
                return getStartGroup() != null;
            case N2BRMPackage.DOCUMENT_ROOT__START_GROUP_RESPONSE:
                return getStartGroupResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM:
                return getSuspendWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE:
                return getSuspendWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM:
                return getUnallocateWorkItem() != null;
            case N2BRMPackage.DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE:
                return getUnallocateWorkItemResponse() != null;
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW:
                return getUnlockWorkListView() != null;
            case N2BRMPackage.DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE:
                return getUnlockWorkListViewResponse() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (mixed: ");
        result.append(mixed);
        result.append(')');
        return result.toString();
    }

} //DocumentRootImpl
