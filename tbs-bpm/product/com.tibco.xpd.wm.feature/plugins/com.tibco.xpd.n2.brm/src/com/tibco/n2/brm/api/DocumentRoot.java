/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAddCurrentResourceToView <em>Add Current Resource To View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAddCurrentResourceToViewResponse <em>Add Current Resource To View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenNextWorkItem <em>Allocate And Open Next Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenNextWorkItemResponse <em>Allocate And Open Next Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenWorkItem <em>Allocate And Open Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenWorkItemResponse <em>Allocate And Open Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateWorkItem <em>Allocate Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateWorkItemResponse <em>Allocate Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncCancelWorkItem <em>Async Cancel Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncCancelWorkItemResponse <em>Async Cancel Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncEndGroup <em>Async End Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncEndGroupResponse <em>Async End Group Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncRescheduleWorkItem <em>Async Reschedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncRescheduleWorkItemResponse <em>Async Reschedule Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncResumeWorkItem <em>Async Resume Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncResumeWorkItemResponse <em>Async Resume Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItem <em>Async Schedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemResponse <em>Async Schedule Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemWithModel <em>Async Schedule Work Item With Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemWithModelResponse <em>Async Schedule Work Item With Model Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncStartGroup <em>Async Start Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncStartGroupResponse <em>Async Start Group Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncSuspendWorkItem <em>Async Suspend Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncSuspendWorkItemResponse <em>Async Suspend Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getCancelWorkItem <em>Cancel Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getCancelWorkItemResponse <em>Cancel Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getChainedWorkItemNotification <em>Chained Work Item Notification</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getCloseWorkItem <em>Close Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getCloseWorkItemResponse <em>Close Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getCompleteWorkItem <em>Complete Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getCompleteWorkItemResponse <em>Complete Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getCreateWorkListView <em>Create Work List View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getCreateWorkListViewResponse <em>Create Work List View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteCurrentResourceFromView <em>Delete Current Resource From View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteCurrentResourceFromViewResponse <em>Delete Current Resource From View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteOrgEntityConfigAttributes <em>Delete Org Entity Config Attributes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteOrgEntityConfigAttributesResponse <em>Delete Org Entity Config Attributes Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteWorkListView <em>Delete Work List View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteWorkListViewResponse <em>Delete Work List View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getEditWorkListView <em>Edit Work List View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getEditWorkListViewResponse <em>Edit Work List View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getEnableWorkItem <em>Enable Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getEnableWorkItemResponse <em>Enable Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getEndGroup <em>End Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getEndGroupResponse <em>End Group Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetAllocatedWorkListItems <em>Get Allocated Work List Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchGroupIds <em>Get Batch Group Ids</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchGroupIdsResponse <em>Get Batch Group Ids Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchWorkItemIds <em>Get Batch Work Item Ids</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchWorkItemIdsResponse <em>Get Batch Work Item Ids Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetEditableWorkListViews <em>Get Editable Work List Views</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetEditableWorkListViewsResponse <em>Get Editable Work List Views Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetOfferSet <em>Get Offer Set</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetOfferSetResponse <em>Get Offer Set Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributes <em>Get Org Entity Config Attributes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesAvailable <em>Get Org Entity Config Attributes Available</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesAvailableResponse <em>Get Org Entity Config Attributes Available Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesResponse <em>Get Org Entity Config Attributes Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetPublicWorkListViews <em>Get Public Work List Views</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetPublicWorkListViewsResponse <em>Get Public Work List Views Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetResourceOrderFilterCriteria <em>Get Resource Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetResourceOrderFilterCriteriaResponse <em>Get Resource Order Filter Criteria Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetViewsForResource <em>Get Views For Resource</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetViewsForResourceResponse <em>Get Views For Resource Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemHeader <em>Get Work Item Header</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemHeaderResponse <em>Get Work Item Header Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemOrderFilter <em>Get Work Item Order Filter</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemOrderFilterResponse <em>Get Work Item Order Filter Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItems <em>Get Work List Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForGlobalData <em>Get Work List Items For Global Data</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForGlobalDataResponse <em>Get Work List Items For Global Data Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForView <em>Get Work List Items For View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForViewResponse <em>Get Work List Items For View Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsResponse <em>Get Work List Items Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListViewDetails <em>Get Work List View Details</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListViewDetailsResponse <em>Get Work List View Details Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModel <em>Get Work Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModelResponse <em>Get Work Model Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModels <em>Get Work Models</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModelsResponse <em>Get Work Models Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkType <em>Get Work Type</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypeResponse <em>Get Work Type Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypes <em>Get Work Types</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypesResponse <em>Get Work Types Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getOnNotification <em>On Notification</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getOnNotificationResponse <em>On Notification Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getOpenWorkItem <em>Open Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getOpenWorkItemResponse <em>Open Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getPendWorkItem <em>Pend Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getPendWorkItemResponse <em>Pend Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getPreviewWorkItemFromList <em>Preview Work Item From List</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getPreviewWorkItemFromListResponse <em>Preview Work Item From List Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getPushNotification <em>Push Notification</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItem <em>Reallocate Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemData <em>Reallocate Work Item Data</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemDataResponse <em>Reallocate Work Item Data Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemResponse <em>Reallocate Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getRescheduleWorkItem <em>Reschedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getRescheduleWorkItemResponse <em>Reschedule Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getResumeWorkItem <em>Resume Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getResumeWorkItemResponse <em>Resume Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSaveOpenWorkItem <em>Save Open Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSaveOpenWorkItemResponse <em>Save Open Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItem <em>Schedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemResponse <em>Schedule Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemWithModel <em>Schedule Work Item With Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemWithModelResponse <em>Schedule Work Item With Model Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSetOrgEntityConfigAttributes <em>Set Org Entity Config Attributes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSetOrgEntityConfigAttributesResponse <em>Set Org Entity Config Attributes Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSetResourceOrderFilterCriteria <em>Set Resource Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSetResourceOrderFilterCriteriaResponse <em>Set Resource Order Filter Criteria Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSetWorkItemPriority <em>Set Work Item Priority</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSetWorkItemPriorityResponse <em>Set Work Item Priority Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSkipWorkItem <em>Skip Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSkipWorkItemResponse <em>Skip Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getStartGroup <em>Start Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getStartGroupResponse <em>Start Group Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSuspendWorkItem <em>Suspend Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getSuspendWorkItemResponse <em>Suspend Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getUnallocateWorkItem <em>Unallocate Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getUnallocateWorkItemResponse <em>Unallocate Work Item Response</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getUnlockWorkListView <em>Unlock Work List View</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.DocumentRoot#getUnlockWorkListViewResponse <em>Unlock Work List View Response</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
    /**
     * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mixed</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_Mixed()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='elementWildcard' name=':mixed'"
     * @generated
     */
    FeatureMap getMixed();

    /**
     * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>XMLNS Prefix Map</em>' map.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_XMLNSPrefixMap()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
     *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
     * @generated
     */
    EMap<String, String> getXMLNSPrefixMap();

    /**
     * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>XSI Schema Location</em>' map.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_XSISchemaLocation()
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
     *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
     * @generated
     */
    EMap<String, String> getXSISchemaLocation();

    /**
     * Returns the value of the '<em><b>Add Current Resource To View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for addResourceToView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Add Current Resource To View</em>' containment reference.
     * @see #setAddCurrentResourceToView(AddCurrentResourceToViewType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AddCurrentResourceToView()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='addCurrentResourceToView' namespace='##targetNamespace'"
     * @generated
     */
    AddCurrentResourceToViewType getAddCurrentResourceToView();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAddCurrentResourceToView <em>Add Current Resource To View</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Add Current Resource To View</em>' containment reference.
     * @see #getAddCurrentResourceToView()
     * @generated
     */
    void setAddCurrentResourceToView(AddCurrentResourceToViewType value);

    /**
     * Returns the value of the '<em><b>Add Current Resource To View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the addResourceToView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Add Current Resource To View Response</em>' containment reference.
     * @see #setAddCurrentResourceToViewResponse(AddCurrentResourceToViewResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AddCurrentResourceToViewResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='addCurrentResourceToViewResponse' namespace='##targetNamespace'"
     * @generated
     */
    AddCurrentResourceToViewResponseType getAddCurrentResourceToViewResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAddCurrentResourceToViewResponse <em>Add Current Resource To View Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Add Current Resource To View Response</em>' containment reference.
     * @see #getAddCurrentResourceToViewResponse()
     * @generated
     */
    void setAddCurrentResourceToViewResponse(AddCurrentResourceToViewResponseType value);

    /**
     * Returns the value of the '<em><b>Allocate And Open Next Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for allocateAndOpenNextWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Allocate And Open Next Work Item</em>' containment reference.
     * @see #setAllocateAndOpenNextWorkItem(AllocateAndOpenNextWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AllocateAndOpenNextWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='allocateAndOpenNextWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    AllocateAndOpenNextWorkItemType getAllocateAndOpenNextWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenNextWorkItem <em>Allocate And Open Next Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocate And Open Next Work Item</em>' containment reference.
     * @see #getAllocateAndOpenNextWorkItem()
     * @generated
     */
    void setAllocateAndOpenNextWorkItem(AllocateAndOpenNextWorkItemType value);

    /**
     * Returns the value of the '<em><b>Allocate And Open Next Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for allocateAndOpenNextWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Allocate And Open Next Work Item Response</em>' containment reference.
     * @see #setAllocateAndOpenNextWorkItemResponse(AllocateAndOpenNextWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AllocateAndOpenNextWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='allocateAndOpenNextWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    AllocateAndOpenNextWorkItemResponseType getAllocateAndOpenNextWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenNextWorkItemResponse <em>Allocate And Open Next Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocate And Open Next Work Item Response</em>' containment reference.
     * @see #getAllocateAndOpenNextWorkItemResponse()
     * @generated
     */
    void setAllocateAndOpenNextWorkItemResponse(AllocateAndOpenNextWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Allocate And Open Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for allocateAndOpenWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Allocate And Open Work Item</em>' containment reference.
     * @see #setAllocateAndOpenWorkItem(AllocateAndOpenWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AllocateAndOpenWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='allocateAndOpenWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    AllocateAndOpenWorkItemType getAllocateAndOpenWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenWorkItem <em>Allocate And Open Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocate And Open Work Item</em>' containment reference.
     * @see #getAllocateAndOpenWorkItem()
     * @generated
     */
    void setAllocateAndOpenWorkItem(AllocateAndOpenWorkItemType value);

    /**
     * Returns the value of the '<em><b>Allocate And Open Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the allocateAndOpenWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Allocate And Open Work Item Response</em>' containment reference.
     * @see #setAllocateAndOpenWorkItemResponse(AllocateAndOpenWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AllocateAndOpenWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='allocateAndOpenWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    AllocateAndOpenWorkItemResponseType getAllocateAndOpenWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateAndOpenWorkItemResponse <em>Allocate And Open Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocate And Open Work Item Response</em>' containment reference.
     * @see #getAllocateAndOpenWorkItemResponse()
     * @generated
     */
    void setAllocateAndOpenWorkItemResponse(AllocateAndOpenWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Allocate Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for allocateWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Allocate Work Item</em>' containment reference.
     * @see #setAllocateWorkItem(AllocateWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AllocateWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='allocateWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    AllocateWorkItemType getAllocateWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateWorkItem <em>Allocate Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocate Work Item</em>' containment reference.
     * @see #getAllocateWorkItem()
     * @generated
     */
    void setAllocateWorkItem(AllocateWorkItemType value);

    /**
     * Returns the value of the '<em><b>Allocate Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the allocateWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Allocate Work Item Response</em>' containment reference.
     * @see #setAllocateWorkItemResponse(AllocateWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AllocateWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='allocateWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    AllocateWorkItemResponseType getAllocateWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAllocateWorkItemResponse <em>Allocate Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocate Work Item Response</em>' containment reference.
     * @see #getAllocateWorkItemResponse()
     * @generated
     */
    void setAllocateWorkItemResponse(AllocateWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Async Cancel Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Request element for cancelWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Cancel Work Item</em>' containment reference.
     * @see #setAsyncCancelWorkItem(AsyncCancelWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncCancelWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncCancelWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    AsyncCancelWorkItemType getAsyncCancelWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncCancelWorkItem <em>Async Cancel Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Cancel Work Item</em>' containment reference.
     * @see #getAsyncCancelWorkItem()
     * @generated
     */
    void setAsyncCancelWorkItem(AsyncCancelWorkItemType value);

    /**
     * Returns the value of the '<em><b>Async Cancel Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Reply element for the cancelWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Cancel Work Item Response</em>' containment reference.
     * @see #setAsyncCancelWorkItemResponse(AsyncCancelWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncCancelWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncCancelWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    AsyncCancelWorkItemResponseType getAsyncCancelWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncCancelWorkItemResponse <em>Async Cancel Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Cancel Work Item Response</em>' containment reference.
     * @see #getAsyncCancelWorkItemResponse()
     * @generated
     */
    void setAsyncCancelWorkItemResponse(AsyncCancelWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Async End Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Request element for endGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async End Group</em>' containment reference.
     * @see #setAsyncEndGroup(AsyncEndGroupType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncEndGroup()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncEndGroup' namespace='##targetNamespace'"
     * @generated
     */
    AsyncEndGroupType getAsyncEndGroup();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncEndGroup <em>Async End Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async End Group</em>' containment reference.
     * @see #getAsyncEndGroup()
     * @generated
     */
    void setAsyncEndGroup(AsyncEndGroupType value);

    /**
     * Returns the value of the '<em><b>Async End Group Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Reply element for the endGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async End Group Response</em>' containment reference.
     * @see #setAsyncEndGroupResponse(AsyncEndGroupResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncEndGroupResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncEndGroupResponse' namespace='##targetNamespace'"
     * @generated
     */
    AsyncEndGroupResponseType getAsyncEndGroupResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncEndGroupResponse <em>Async End Group Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async End Group Response</em>' containment reference.
     * @see #getAsyncEndGroupResponse()
     * @generated
     */
    void setAsyncEndGroupResponse(AsyncEndGroupResponseType value);

    /**
     * Returns the value of the '<em><b>Async Reschedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Request element for rescheduleWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Reschedule Work Item</em>' containment reference.
     * @see #setAsyncRescheduleWorkItem(AsyncRescheduleWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncRescheduleWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncRescheduleWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    AsyncRescheduleWorkItemType getAsyncRescheduleWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncRescheduleWorkItem <em>Async Reschedule Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Reschedule Work Item</em>' containment reference.
     * @see #getAsyncRescheduleWorkItem()
     * @generated
     */
    void setAsyncRescheduleWorkItem(AsyncRescheduleWorkItemType value);

    /**
     * Returns the value of the '<em><b>Async Reschedule Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Reply element for the rescheduleWorkItemResponse operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Reschedule Work Item Response</em>' containment reference.
     * @see #setAsyncRescheduleWorkItemResponse(AsyncRescheduleWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncRescheduleWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncRescheduleWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    AsyncRescheduleWorkItemResponseType getAsyncRescheduleWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncRescheduleWorkItemResponse <em>Async Reschedule Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Reschedule Work Item Response</em>' containment reference.
     * @see #getAsyncRescheduleWorkItemResponse()
     * @generated
     */
    void setAsyncRescheduleWorkItemResponse(AsyncRescheduleWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Async Resume Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Request element for the resumeWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Resume Work Item</em>' containment reference.
     * @see #setAsyncResumeWorkItem(AsyncResumeWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncResumeWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncResumeWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    AsyncResumeWorkItemType getAsyncResumeWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncResumeWorkItem <em>Async Resume Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Resume Work Item</em>' containment reference.
     * @see #getAsyncResumeWorkItem()
     * @generated
     */
    void setAsyncResumeWorkItem(AsyncResumeWorkItemType value);

    /**
     * Returns the value of the '<em><b>Async Resume Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Reply element for the resumeWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Resume Work Item Response</em>' containment reference.
     * @see #setAsyncResumeWorkItemResponse(AsyncResumeWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncResumeWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncResumeWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    AsyncResumeWorkItemResponseType getAsyncResumeWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncResumeWorkItemResponse <em>Async Resume Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Resume Work Item Response</em>' containment reference.
     * @see #getAsyncResumeWorkItemResponse()
     * @generated
     */
    void setAsyncResumeWorkItemResponse(AsyncResumeWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Async Schedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Request element for scheduleWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Schedule Work Item</em>' containment reference.
     * @see #setAsyncScheduleWorkItem(AsyncScheduleWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncScheduleWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncScheduleWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    AsyncScheduleWorkItemType getAsyncScheduleWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItem <em>Async Schedule Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Schedule Work Item</em>' containment reference.
     * @see #getAsyncScheduleWorkItem()
     * @generated
     */
    void setAsyncScheduleWorkItem(AsyncScheduleWorkItemType value);

    /**
     * Returns the value of the '<em><b>Async Schedule Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Reply element for the scheduleWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Schedule Work Item Response</em>' containment reference.
     * @see #setAsyncScheduleWorkItemResponse(AsyncScheduleWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncScheduleWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncScheduleWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    AsyncScheduleWorkItemResponseType getAsyncScheduleWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemResponse <em>Async Schedule Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Schedule Work Item Response</em>' containment reference.
     * @see #getAsyncScheduleWorkItemResponse()
     * @generated
     */
    void setAsyncScheduleWorkItemResponse(AsyncScheduleWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Async Schedule Work Item With Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Request element for scheduleWorkItemWithModel operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Schedule Work Item With Model</em>' containment reference.
     * @see #setAsyncScheduleWorkItemWithModel(AsyncScheduleWorkItemWithModelType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncScheduleWorkItemWithModel()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncScheduleWorkItemWithModel' namespace='##targetNamespace'"
     * @generated
     */
    AsyncScheduleWorkItemWithModelType getAsyncScheduleWorkItemWithModel();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemWithModel <em>Async Schedule Work Item With Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Schedule Work Item With Model</em>' containment reference.
     * @see #getAsyncScheduleWorkItemWithModel()
     * @generated
     */
    void setAsyncScheduleWorkItemWithModel(AsyncScheduleWorkItemWithModelType value);

    /**
     * Returns the value of the '<em><b>Async Schedule Work Item With Model Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Reply element for the scheduleWorkItemWithModel operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Schedule Work Item With Model Response</em>' containment reference.
     * @see #setAsyncScheduleWorkItemWithModelResponse(AsyncScheduleWorkItemWithModelResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncScheduleWorkItemWithModelResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncScheduleWorkItemWithModelResponse' namespace='##targetNamespace'"
     * @generated
     */
    AsyncScheduleWorkItemWithModelResponseType getAsyncScheduleWorkItemWithModelResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncScheduleWorkItemWithModelResponse <em>Async Schedule Work Item With Model Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Schedule Work Item With Model Response</em>' containment reference.
     * @see #getAsyncScheduleWorkItemWithModelResponse()
     * @generated
     */
    void setAsyncScheduleWorkItemWithModelResponse(AsyncScheduleWorkItemWithModelResponseType value);

    /**
     * Returns the value of the '<em><b>Async Start Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Request element for startGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Start Group</em>' containment reference.
     * @see #setAsyncStartGroup(AsyncStartGroupType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncStartGroup()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncStartGroup' namespace='##targetNamespace'"
     * @generated
     */
    AsyncStartGroupType getAsyncStartGroup();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncStartGroup <em>Async Start Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Start Group</em>' containment reference.
     * @see #getAsyncStartGroup()
     * @generated
     */
    void setAsyncStartGroup(AsyncStartGroupType value);

    /**
     * Returns the value of the '<em><b>Async Start Group Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Reply element for startGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Start Group Response</em>' containment reference.
     * @see #setAsyncStartGroupResponse(AsyncStartGroupResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncStartGroupResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncStartGroupResponse' namespace='##targetNamespace'"
     * @generated
     */
    AsyncStartGroupResponseType getAsyncStartGroupResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncStartGroupResponse <em>Async Start Group Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Start Group Response</em>' containment reference.
     * @see #getAsyncStartGroupResponse()
     * @generated
     */
    void setAsyncStartGroupResponse(AsyncStartGroupResponseType value);

    /**
     * Returns the value of the '<em><b>Async Suspend Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Request element for the suspendWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Suspend Work Item</em>' containment reference.
     * @see #setAsyncSuspendWorkItem(AsyncSuspendWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncSuspendWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncSuspendWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    AsyncSuspendWorkItemType getAsyncSuspendWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncSuspendWorkItem <em>Async Suspend Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Suspend Work Item</em>' containment reference.
     * @see #getAsyncSuspendWorkItem()
     * @generated
     */
    void setAsyncSuspendWorkItem(AsyncSuspendWorkItemType value);

    /**
     * Returns the value of the '<em><b>Async Suspend Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Async - Reply element for the suspendWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Async Suspend Work Item Response</em>' containment reference.
     * @see #setAsyncSuspendWorkItemResponse(AsyncSuspendWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_AsyncSuspendWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='asyncSuspendWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    AsyncSuspendWorkItemResponseType getAsyncSuspendWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getAsyncSuspendWorkItemResponse <em>Async Suspend Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Async Suspend Work Item Response</em>' containment reference.
     * @see #getAsyncSuspendWorkItemResponse()
     * @generated
     */
    void setAsyncSuspendWorkItemResponse(AsyncSuspendWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Cancel Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for cancelWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Cancel Work Item</em>' containment reference.
     * @see #setCancelWorkItem(CancelWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_CancelWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='cancelWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    CancelWorkItemType getCancelWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getCancelWorkItem <em>Cancel Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cancel Work Item</em>' containment reference.
     * @see #getCancelWorkItem()
     * @generated
     */
    void setCancelWorkItem(CancelWorkItemType value);

    /**
     * Returns the value of the '<em><b>Cancel Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the cancelWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Cancel Work Item Response</em>' containment reference.
     * @see #setCancelWorkItemResponse(CancelWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_CancelWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='cancelWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    CancelWorkItemResponseType getCancelWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getCancelWorkItemResponse <em>Cancel Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cancel Work Item Response</em>' containment reference.
     * @see #getCancelWorkItemResponse()
     * @generated
     */
    void setCancelWorkItemResponse(CancelWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Chained Work Item Notification</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for the chainedWorkItemNotification operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Chained Work Item Notification</em>' containment reference.
     * @see #setChainedWorkItemNotification(ChainedWorkItemNotificationType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ChainedWorkItemNotification()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='chainedWorkItemNotification' namespace='##targetNamespace'"
     * @generated
     */
    ChainedWorkItemNotificationType getChainedWorkItemNotification();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getChainedWorkItemNotification <em>Chained Work Item Notification</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Chained Work Item Notification</em>' containment reference.
     * @see #getChainedWorkItemNotification()
     * @generated
     */
    void setChainedWorkItemNotification(ChainedWorkItemNotificationType value);

    /**
     * Returns the value of the '<em><b>Close Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for closeWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Close Work Item</em>' containment reference.
     * @see #setCloseWorkItem(CloseWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_CloseWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='closeWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    CloseWorkItemType getCloseWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getCloseWorkItem <em>Close Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Close Work Item</em>' containment reference.
     * @see #getCloseWorkItem()
     * @generated
     */
    void setCloseWorkItem(CloseWorkItemType value);

    /**
     * Returns the value of the '<em><b>Close Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the closeWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Close Work Item Response</em>' containment reference.
     * @see #setCloseWorkItemResponse(CloseWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_CloseWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='closeWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    CloseWorkItemResponseType getCloseWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getCloseWorkItemResponse <em>Close Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Close Work Item Response</em>' containment reference.
     * @see #getCloseWorkItemResponse()
     * @generated
     */
    void setCloseWorkItemResponse(CloseWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Complete Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for completeWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Complete Work Item</em>' containment reference.
     * @see #setCompleteWorkItem(CompleteWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_CompleteWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='completeWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    CompleteWorkItemType getCompleteWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getCompleteWorkItem <em>Complete Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Complete Work Item</em>' containment reference.
     * @see #getCompleteWorkItem()
     * @generated
     */
    void setCompleteWorkItem(CompleteWorkItemType value);

    /**
     * Returns the value of the '<em><b>Complete Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the completeWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Complete Work Item Response</em>' containment reference.
     * @see #setCompleteWorkItemResponse(CompleteWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_CompleteWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='completeWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    CompleteWorkItemResponseType getCompleteWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getCompleteWorkItemResponse <em>Complete Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Complete Work Item Response</em>' containment reference.
     * @see #getCompleteWorkItemResponse()
     * @generated
     */
    void setCompleteWorkItemResponse(CompleteWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Create Work List View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for createWorkListView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Create Work List View</em>' containment reference.
     * @see #setCreateWorkListView(WorkListViewEdit)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_CreateWorkListView()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='createWorkListView' namespace='##targetNamespace'"
     * @generated
     */
    WorkListViewEdit getCreateWorkListView();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getCreateWorkListView <em>Create Work List View</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Create Work List View</em>' containment reference.
     * @see #getCreateWorkListView()
     * @generated
     */
    void setCreateWorkListView(WorkListViewEdit value);

    /**
     * Returns the value of the '<em><b>Create Work List View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the createWorkListView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Create Work List View Response</em>' containment reference.
     * @see #setCreateWorkListViewResponse(CreateWorkListViewResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_CreateWorkListViewResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='createWorkListViewResponse' namespace='##targetNamespace'"
     * @generated
     */
    CreateWorkListViewResponseType getCreateWorkListViewResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getCreateWorkListViewResponse <em>Create Work List View Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Create Work List View Response</em>' containment reference.
     * @see #getCreateWorkListViewResponse()
     * @generated
     */
    void setCreateWorkListViewResponse(CreateWorkListViewResponseType value);

    /**
     * Returns the value of the '<em><b>Delete Current Resource From View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for deleteResourceFromView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Delete Current Resource From View</em>' containment reference.
     * @see #setDeleteCurrentResourceFromView(DeleteCurrentResourceFromViewType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_DeleteCurrentResourceFromView()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='deleteCurrentResourceFromView' namespace='##targetNamespace'"
     * @generated
     */
    DeleteCurrentResourceFromViewType getDeleteCurrentResourceFromView();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteCurrentResourceFromView <em>Delete Current Resource From View</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete Current Resource From View</em>' containment reference.
     * @see #getDeleteCurrentResourceFromView()
     * @generated
     */
    void setDeleteCurrentResourceFromView(DeleteCurrentResourceFromViewType value);

    /**
     * Returns the value of the '<em><b>Delete Current Resource From View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the deleteResourceFromView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Delete Current Resource From View Response</em>' containment reference.
     * @see #setDeleteCurrentResourceFromViewResponse(DeleteCurrentResourceFromViewResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_DeleteCurrentResourceFromViewResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='deleteCurrentResourceFromViewResponse' namespace='##targetNamespace'"
     * @generated
     */
    DeleteCurrentResourceFromViewResponseType getDeleteCurrentResourceFromViewResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteCurrentResourceFromViewResponse <em>Delete Current Resource From View Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete Current Resource From View Response</em>' containment reference.
     * @see #getDeleteCurrentResourceFromViewResponse()
     * @generated
     */
    void setDeleteCurrentResourceFromViewResponse(DeleteCurrentResourceFromViewResponseType value);

    /**
     * Returns the value of the '<em><b>Delete Org Entity Config Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for deleteOrgEntityConfigAttributes operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Delete Org Entity Config Attributes</em>' containment reference.
     * @see #setDeleteOrgEntityConfigAttributes(DeleteOrgEntityConfigAttributesType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_DeleteOrgEntityConfigAttributes()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='deleteOrgEntityConfigAttributes' namespace='##targetNamespace'"
     * @generated
     */
    DeleteOrgEntityConfigAttributesType getDeleteOrgEntityConfigAttributes();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteOrgEntityConfigAttributes <em>Delete Org Entity Config Attributes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete Org Entity Config Attributes</em>' containment reference.
     * @see #getDeleteOrgEntityConfigAttributes()
     * @generated
     */
    void setDeleteOrgEntityConfigAttributes(DeleteOrgEntityConfigAttributesType value);

    /**
     * Returns the value of the '<em><b>Delete Org Entity Config Attributes Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for deleteOrgEntityConfigAttributes operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Delete Org Entity Config Attributes Response</em>' containment reference.
     * @see #setDeleteOrgEntityConfigAttributesResponse(DeleteOrgEntityConfigAttributesResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_DeleteOrgEntityConfigAttributesResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='deleteOrgEntityConfigAttributesResponse' namespace='##targetNamespace'"
     * @generated
     */
    DeleteOrgEntityConfigAttributesResponseType getDeleteOrgEntityConfigAttributesResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteOrgEntityConfigAttributesResponse <em>Delete Org Entity Config Attributes Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete Org Entity Config Attributes Response</em>' containment reference.
     * @see #getDeleteOrgEntityConfigAttributesResponse()
     * @generated
     */
    void setDeleteOrgEntityConfigAttributesResponse(DeleteOrgEntityConfigAttributesResponseType value);

    /**
     * Returns the value of the '<em><b>Delete Work List View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for deleteWorkListView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Delete Work List View</em>' containment reference.
     * @see #setDeleteWorkListView(DeleteWorkListViewType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_DeleteWorkListView()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='deleteWorkListView' namespace='##targetNamespace'"
     * @generated
     */
    DeleteWorkListViewType getDeleteWorkListView();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteWorkListView <em>Delete Work List View</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete Work List View</em>' containment reference.
     * @see #getDeleteWorkListView()
     * @generated
     */
    void setDeleteWorkListView(DeleteWorkListViewType value);

    /**
     * Returns the value of the '<em><b>Delete Work List View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the deleteWorkListView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Delete Work List View Response</em>' containment reference.
     * @see #setDeleteWorkListViewResponse(DeleteWorkListViewResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_DeleteWorkListViewResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='deleteWorkListViewResponse' namespace='##targetNamespace'"
     * @generated
     */
    DeleteWorkListViewResponseType getDeleteWorkListViewResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getDeleteWorkListViewResponse <em>Delete Work List View Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delete Work List View Response</em>' containment reference.
     * @see #getDeleteWorkListViewResponse()
     * @generated
     */
    void setDeleteWorkListViewResponse(DeleteWorkListViewResponseType value);

    /**
     * Returns the value of the '<em><b>Edit Work List View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for editWorkListView operation.   If any of the opional elements are passed in they will overwrite any existing conguration for this view.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Edit Work List View</em>' containment reference.
     * @see #setEditWorkListView(EditWorkListViewType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_EditWorkListView()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='editWorkListView' namespace='##targetNamespace'"
     * @generated
     */
    EditWorkListViewType getEditWorkListView();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getEditWorkListView <em>Edit Work List View</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Edit Work List View</em>' containment reference.
     * @see #getEditWorkListView()
     * @generated
     */
    void setEditWorkListView(EditWorkListViewType value);

    /**
     * Returns the value of the '<em><b>Edit Work List View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the editWorkListView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Edit Work List View Response</em>' containment reference.
     * @see #setEditWorkListViewResponse(EditWorkListViewResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_EditWorkListViewResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='editWorkListViewResponse' namespace='##targetNamespace'"
     * @generated
     */
    EditWorkListViewResponseType getEditWorkListViewResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getEditWorkListViewResponse <em>Edit Work List View Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Edit Work List View Response</em>' containment reference.
     * @see #getEditWorkListViewResponse()
     * @generated
     */
    void setEditWorkListViewResponse(EditWorkListViewResponseType value);

    /**
     * Returns the value of the '<em><b>Enable Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for enableWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Enable Work Item</em>' containment reference.
     * @see #setEnableWorkItem(EnableWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_EnableWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='enableWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    EnableWorkItemType getEnableWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getEnableWorkItem <em>Enable Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enable Work Item</em>' containment reference.
     * @see #getEnableWorkItem()
     * @generated
     */
    void setEnableWorkItem(EnableWorkItemType value);

    /**
     * Returns the value of the '<em><b>Enable Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the enableWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Enable Work Item Response</em>' containment reference.
     * @see #setEnableWorkItemResponse(EnableWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_EnableWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='enableWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    EnableWorkItemResponseType getEnableWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getEnableWorkItemResponse <em>Enable Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enable Work Item Response</em>' containment reference.
     * @see #getEnableWorkItemResponse()
     * @generated
     */
    void setEnableWorkItemResponse(EnableWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>End Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for endGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>End Group</em>' containment reference.
     * @see #setEndGroup(EndGroupType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_EndGroup()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='endGroup' namespace='##targetNamespace'"
     * @generated
     */
    EndGroupType getEndGroup();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getEndGroup <em>End Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Group</em>' containment reference.
     * @see #getEndGroup()
     * @generated
     */
    void setEndGroup(EndGroupType value);

    /**
     * Returns the value of the '<em><b>End Group Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the endGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>End Group Response</em>' containment reference.
     * @see #setEndGroupResponse(EndGroupResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_EndGroupResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='endGroupResponse' namespace='##targetNamespace'"
     * @generated
     */
    EndGroupResponseType getEndGroupResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getEndGroupResponse <em>End Group Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Group Response</em>' containment reference.
     * @see #getEndGroupResponse()
     * @generated
     */
    void setEndGroupResponse(EndGroupResponseType value);

    /**
     * Returns the value of the '<em><b>Get Allocated Work List Items</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getAllocatedWorkListItems operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Allocated Work List Items</em>' containment reference.
     * @see #setGetAllocatedWorkListItems(GetAllocatedWorkListItemsType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetAllocatedWorkListItems()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getAllocatedWorkListItems' namespace='##targetNamespace'"
     * @generated
     */
    GetAllocatedWorkListItemsType getGetAllocatedWorkListItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetAllocatedWorkListItems <em>Get Allocated Work List Items</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Allocated Work List Items</em>' containment reference.
     * @see #getGetAllocatedWorkListItems()
     * @generated
     */
    void setGetAllocatedWorkListItems(GetAllocatedWorkListItemsType value);

    /**
     * Returns the value of the '<em><b>Get Batch Group Ids</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getBatchGroupIds operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Batch Group Ids</em>' containment reference.
     * @see #setGetBatchGroupIds(EObject)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetBatchGroupIds()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getBatchGroupIds' namespace='##targetNamespace'"
     * @generated
     */
    EObject getGetBatchGroupIds();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchGroupIds <em>Get Batch Group Ids</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Batch Group Ids</em>' containment reference.
     * @see #getGetBatchGroupIds()
     * @generated
     */
    void setGetBatchGroupIds(EObject value);

    /**
     * Returns the value of the '<em><b>Get Batch Group Ids Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getBatchGroupIds operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Batch Group Ids Response</em>' containment reference.
     * @see #setGetBatchGroupIdsResponse(GetBatchGroupIdsResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetBatchGroupIdsResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getBatchGroupIdsResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetBatchGroupIdsResponseType getGetBatchGroupIdsResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchGroupIdsResponse <em>Get Batch Group Ids Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Batch Group Ids Response</em>' containment reference.
     * @see #getGetBatchGroupIdsResponse()
     * @generated
     */
    void setGetBatchGroupIdsResponse(GetBatchGroupIdsResponseType value);

    /**
     * Returns the value of the '<em><b>Get Batch Work Item Ids</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getBatchWorkItemIds operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Batch Work Item Ids</em>' containment reference.
     * @see #setGetBatchWorkItemIds(EObject)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetBatchWorkItemIds()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getBatchWorkItemIds' namespace='##targetNamespace'"
     * @generated
     */
    EObject getGetBatchWorkItemIds();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchWorkItemIds <em>Get Batch Work Item Ids</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Batch Work Item Ids</em>' containment reference.
     * @see #getGetBatchWorkItemIds()
     * @generated
     */
    void setGetBatchWorkItemIds(EObject value);

    /**
     * Returns the value of the '<em><b>Get Batch Work Item Ids Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getBatchWorkItemIds operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Batch Work Item Ids Response</em>' containment reference.
     * @see #setGetBatchWorkItemIdsResponse(GetBatchWorkItemIdsResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetBatchWorkItemIdsResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getBatchWorkItemIdsResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetBatchWorkItemIdsResponseType getGetBatchWorkItemIdsResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetBatchWorkItemIdsResponse <em>Get Batch Work Item Ids Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Batch Work Item Ids Response</em>' containment reference.
     * @see #getGetBatchWorkItemIdsResponse()
     * @generated
     */
    void setGetBatchWorkItemIdsResponse(GetBatchWorkItemIdsResponseType value);

    /**
     * Returns the value of the '<em><b>Get Editable Work List Views</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getEditableWorkListViews operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Editable Work List Views</em>' containment reference.
     * @see #setGetEditableWorkListViews(GetEditableWorkListViewsType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetEditableWorkListViews()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getEditableWorkListViews' namespace='##targetNamespace'"
     * @generated
     */
    GetEditableWorkListViewsType getGetEditableWorkListViews();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetEditableWorkListViews <em>Get Editable Work List Views</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Editable Work List Views</em>' containment reference.
     * @see #getGetEditableWorkListViews()
     * @generated
     */
    void setGetEditableWorkListViews(GetEditableWorkListViewsType value);

    /**
     * Returns the value of the '<em><b>Get Editable Work List Views Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getEditableWorkListViews operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Editable Work List Views Response</em>' containment reference.
     * @see #setGetEditableWorkListViewsResponse(GetEditableWorkListViewsResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetEditableWorkListViewsResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getEditableWorkListViewsResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetEditableWorkListViewsResponseType getGetEditableWorkListViewsResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetEditableWorkListViewsResponse <em>Get Editable Work List Views Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Editable Work List Views Response</em>' containment reference.
     * @see #getGetEditableWorkListViewsResponse()
     * @generated
     */
    void setGetEditableWorkListViewsResponse(GetEditableWorkListViewsResponseType value);

    /**
     * Returns the value of the '<em><b>Get Offer Set</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for the getOfferSet operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Offer Set</em>' containment reference.
     * @see #setGetOfferSet(GetOfferSetType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetOfferSet()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getOfferSet' namespace='##targetNamespace'"
     * @generated
     */
    GetOfferSetType getGetOfferSet();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOfferSet <em>Get Offer Set</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Offer Set</em>' containment reference.
     * @see #getGetOfferSet()
     * @generated
     */
    void setGetOfferSet(GetOfferSetType value);

    /**
     * Returns the value of the '<em><b>Get Offer Set Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getOfferSet operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Offer Set Response</em>' containment reference.
     * @see #setGetOfferSetResponse(GetOfferSetResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetOfferSetResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getOfferSetResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetOfferSetResponseType getGetOfferSetResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOfferSetResponse <em>Get Offer Set Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Offer Set Response</em>' containment reference.
     * @see #getGetOfferSetResponse()
     * @generated
     */
    void setGetOfferSetResponse(GetOfferSetResponseType value);

    /**
     * Returns the value of the '<em><b>Get Org Entity Config Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getOrgEntityConfigAttributes operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Org Entity Config Attributes</em>' containment reference.
     * @see #setGetOrgEntityConfigAttributes(GetOrgEntityConfigAttributesType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetOrgEntityConfigAttributes()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getOrgEntityConfigAttributes' namespace='##targetNamespace'"
     * @generated
     */
    GetOrgEntityConfigAttributesType getGetOrgEntityConfigAttributes();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributes <em>Get Org Entity Config Attributes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Org Entity Config Attributes</em>' containment reference.
     * @see #getGetOrgEntityConfigAttributes()
     * @generated
     */
    void setGetOrgEntityConfigAttributes(GetOrgEntityConfigAttributesType value);

    /**
     * Returns the value of the '<em><b>Get Org Entity Config Attributes Available</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getOrgEntityConfigAttributesAvailable operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Org Entity Config Attributes Available</em>' containment reference.
     * @see #setGetOrgEntityConfigAttributesAvailable(GetOrgEntityConfigAttributesAvailableType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetOrgEntityConfigAttributesAvailable()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getOrgEntityConfigAttributesAvailable' namespace='##targetNamespace'"
     * @generated
     */
    GetOrgEntityConfigAttributesAvailableType getGetOrgEntityConfigAttributesAvailable();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesAvailable <em>Get Org Entity Config Attributes Available</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Org Entity Config Attributes Available</em>' containment reference.
     * @see #getGetOrgEntityConfigAttributesAvailable()
     * @generated
     */
    void setGetOrgEntityConfigAttributesAvailable(GetOrgEntityConfigAttributesAvailableType value);

    /**
     * Returns the value of the '<em><b>Get Org Entity Config Attributes Available Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for getOrgEntityConfigAttributesAvailable operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Org Entity Config Attributes Available Response</em>' containment reference.
     * @see #setGetOrgEntityConfigAttributesAvailableResponse(GetOrgEntityConfigAttributesAvailableResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetOrgEntityConfigAttributesAvailableResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getOrgEntityConfigAttributesAvailableResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetOrgEntityConfigAttributesAvailableResponseType getGetOrgEntityConfigAttributesAvailableResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesAvailableResponse <em>Get Org Entity Config Attributes Available Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Org Entity Config Attributes Available Response</em>' containment reference.
     * @see #getGetOrgEntityConfigAttributesAvailableResponse()
     * @generated
     */
    void setGetOrgEntityConfigAttributesAvailableResponse(GetOrgEntityConfigAttributesAvailableResponseType value);

    /**
     * Returns the value of the '<em><b>Get Org Entity Config Attributes Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for getOrgEntityConfigAttributes operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Org Entity Config Attributes Response</em>' containment reference.
     * @see #setGetOrgEntityConfigAttributesResponse(GetOrgEntityConfigAttributesResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetOrgEntityConfigAttributesResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getOrgEntityConfigAttributesResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetOrgEntityConfigAttributesResponseType getGetOrgEntityConfigAttributesResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetOrgEntityConfigAttributesResponse <em>Get Org Entity Config Attributes Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Org Entity Config Attributes Response</em>' containment reference.
     * @see #getGetOrgEntityConfigAttributesResponse()
     * @generated
     */
    void setGetOrgEntityConfigAttributesResponse(GetOrgEntityConfigAttributesResponseType value);

    /**
     * Returns the value of the '<em><b>Get Public Work List Views</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getPublicWorkViews operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Public Work List Views</em>' containment reference.
     * @see #setGetPublicWorkListViews(GetPublicWorkListViewsType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetPublicWorkListViews()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getPublicWorkListViews' namespace='##targetNamespace'"
     * @generated
     */
    GetPublicWorkListViewsType getGetPublicWorkListViews();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetPublicWorkListViews <em>Get Public Work List Views</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Public Work List Views</em>' containment reference.
     * @see #getGetPublicWorkListViews()
     * @generated
     */
    void setGetPublicWorkListViews(GetPublicWorkListViewsType value);

    /**
     * Returns the value of the '<em><b>Get Public Work List Views Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getPublicWorkViews operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Public Work List Views Response</em>' containment reference.
     * @see #setGetPublicWorkListViewsResponse(GetPublicWorkListViewsResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetPublicWorkListViewsResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getPublicWorkListViewsResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetPublicWorkListViewsResponseType getGetPublicWorkListViewsResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetPublicWorkListViewsResponse <em>Get Public Work List Views Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Public Work List Views Response</em>' containment reference.
     * @see #getGetPublicWorkListViewsResponse()
     * @generated
     */
    void setGetPublicWorkListViewsResponse(GetPublicWorkListViewsResponseType value);

    /**
     * Returns the value of the '<em><b>Get Resource Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getResourceOrderFilterCriteria operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Resource Order Filter Criteria</em>' containment reference.
     * @see #setGetResourceOrderFilterCriteria(GetResourceOrderFilterCriteriaType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetResourceOrderFilterCriteria()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getResourceOrderFilterCriteria' namespace='##targetNamespace'"
     * @generated
     */
    GetResourceOrderFilterCriteriaType getGetResourceOrderFilterCriteria();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetResourceOrderFilterCriteria <em>Get Resource Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Resource Order Filter Criteria</em>' containment reference.
     * @see #getGetResourceOrderFilterCriteria()
     * @generated
     */
    void setGetResourceOrderFilterCriteria(GetResourceOrderFilterCriteriaType value);

    /**
     * Returns the value of the '<em><b>Get Resource Order Filter Criteria Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for getResourceOrderFilterCriteria operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Resource Order Filter Criteria Response</em>' containment reference.
     * @see #setGetResourceOrderFilterCriteriaResponse(GetResourceOrderFilterCriteriaResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetResourceOrderFilterCriteriaResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getResourceOrderFilterCriteriaResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetResourceOrderFilterCriteriaResponseType getGetResourceOrderFilterCriteriaResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetResourceOrderFilterCriteriaResponse <em>Get Resource Order Filter Criteria Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Resource Order Filter Criteria Response</em>' containment reference.
     * @see #getGetResourceOrderFilterCriteriaResponse()
     * @generated
     */
    void setGetResourceOrderFilterCriteriaResponse(GetResourceOrderFilterCriteriaResponseType value);

    /**
     * Returns the value of the '<em><b>Get Views For Resource</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getViewsForResource operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Views For Resource</em>' containment reference.
     * @see #setGetViewsForResource(GetViewsForResourceType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetViewsForResource()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getViewsForResource' namespace='##targetNamespace'"
     * @generated
     */
    GetViewsForResourceType getGetViewsForResource();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetViewsForResource <em>Get Views For Resource</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Views For Resource</em>' containment reference.
     * @see #getGetViewsForResource()
     * @generated
     */
    void setGetViewsForResource(GetViewsForResourceType value);

    /**
     * Returns the value of the '<em><b>Get Views For Resource Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getViewsForResource operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Views For Resource Response</em>' containment reference.
     * @see #setGetViewsForResourceResponse(GetViewsForResourceResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetViewsForResourceResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getViewsForResourceResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetViewsForResourceResponseType getGetViewsForResourceResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetViewsForResourceResponse <em>Get Views For Resource Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Views For Resource Response</em>' containment reference.
     * @see #getGetViewsForResourceResponse()
     * @generated
     */
    void setGetViewsForResourceResponse(GetViewsForResourceResponseType value);

    /**
     * Returns the value of the '<em><b>Get Work Item Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkItemHeader operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Item Header</em>' containment reference.
     * @see #setGetWorkItemHeader(GetWorkItemHeaderType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkItemHeader()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkItemHeader' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkItemHeaderType getGetWorkItemHeader();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemHeader <em>Get Work Item Header</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Item Header</em>' containment reference.
     * @see #getGetWorkItemHeader()
     * @generated
     */
    void setGetWorkItemHeader(GetWorkItemHeaderType value);

    /**
     * Returns the value of the '<em><b>Get Work Item Header Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkItemHeader operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Item Header Response</em>' containment reference.
     * @see #setGetWorkItemHeaderResponse(GetWorkItemHeaderResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkItemHeaderResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkItemHeaderResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkItemHeaderResponseType getGetWorkItemHeaderResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemHeaderResponse <em>Get Work Item Header Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Item Header Response</em>' containment reference.
     * @see #getGetWorkItemHeaderResponse()
     * @generated
     */
    void setGetWorkItemHeaderResponse(GetWorkItemHeaderResponseType value);

    /**
     * Returns the value of the '<em><b>Get Work Item Order Filter</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkItemOrderFilter operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Item Order Filter</em>' containment reference.
     * @see #setGetWorkItemOrderFilter(GetWorkItemOrderFilterType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkItemOrderFilter()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkItemOrderFilter' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkItemOrderFilterType getGetWorkItemOrderFilter();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemOrderFilter <em>Get Work Item Order Filter</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Item Order Filter</em>' containment reference.
     * @see #getGetWorkItemOrderFilter()
     * @generated
     */
    void setGetWorkItemOrderFilter(GetWorkItemOrderFilterType value);

    /**
     * Returns the value of the '<em><b>Get Work Item Order Filter Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkListOrderFilter operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Item Order Filter Response</em>' containment reference.
     * @see #setGetWorkItemOrderFilterResponse(GetWorkItemOrderFilterResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkItemOrderFilterResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkItemOrderFilterResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkItemOrderFilterResponseType getGetWorkItemOrderFilterResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkItemOrderFilterResponse <em>Get Work Item Order Filter Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Item Order Filter Response</em>' containment reference.
     * @see #getGetWorkItemOrderFilterResponse()
     * @generated
     */
    void setGetWorkItemOrderFilterResponse(GetWorkItemOrderFilterResponseType value);

    /**
     * Returns the value of the '<em><b>Get Work List Items</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkListItems operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work List Items</em>' containment reference.
     * @see #setGetWorkListItems(GetWorkListItemsType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkListItems()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkListItems' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkListItemsType getGetWorkListItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItems <em>Get Work List Items</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work List Items</em>' containment reference.
     * @see #getGetWorkListItems()
     * @generated
     */
    void setGetWorkListItems(GetWorkListItemsType value);

    /**
     * Returns the value of the '<em><b>Get Work List Items For Global Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkLitsItemsForGlobalData operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work List Items For Global Data</em>' containment reference.
     * @see #setGetWorkListItemsForGlobalData(GetWorkListItemsForGlobalDataType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkListItemsForGlobalData()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkListItemsForGlobalData' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkListItemsForGlobalDataType getGetWorkListItemsForGlobalData();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForGlobalData <em>Get Work List Items For Global Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work List Items For Global Data</em>' containment reference.
     * @see #getGetWorkListItemsForGlobalData()
     * @generated
     */
    void setGetWorkListItemsForGlobalData(GetWorkListItemsForGlobalDataType value);

    /**
     * Returns the value of the '<em><b>Get Work List Items For Global Data Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkLitsItemsForGlobalData operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work List Items For Global Data Response</em>' containment reference.
     * @see #setGetWorkListItemsForGlobalDataResponse(GetWorkListItemsForGlobalDataResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkListItemsForGlobalDataResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkListItemsForGlobalDataResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkListItemsForGlobalDataResponseType getGetWorkListItemsForGlobalDataResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForGlobalDataResponse <em>Get Work List Items For Global Data Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work List Items For Global Data Response</em>' containment reference.
     * @see #getGetWorkListItemsForGlobalDataResponse()
     * @generated
     */
    void setGetWorkListItemsForGlobalDataResponse(GetWorkListItemsForGlobalDataResponseType value);

    /**
     * Returns the value of the '<em><b>Get Work List Items For View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkListItemsForView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work List Items For View</em>' containment reference.
     * @see #setGetWorkListItemsForView(GetWorkListItemsForViewType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkListItemsForView()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkListItemsForView' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkListItemsForViewType getGetWorkListItemsForView();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForView <em>Get Work List Items For View</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work List Items For View</em>' containment reference.
     * @see #getGetWorkListItemsForView()
     * @generated
     */
    void setGetWorkListItemsForView(GetWorkListItemsForViewType value);

    /**
     * Returns the value of the '<em><b>Get Work List Items For View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkListItemsForView operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work List Items For View Response</em>' containment reference.
     * @see #setGetWorkListItemsForViewResponse(GetWorkListItemsForViewResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkListItemsForViewResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkListItemsForViewResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkListItemsForViewResponseType getGetWorkListItemsForViewResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsForViewResponse <em>Get Work List Items For View Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work List Items For View Response</em>' containment reference.
     * @see #getGetWorkListItemsForViewResponse()
     * @generated
     */
    void setGetWorkListItemsForViewResponse(GetWorkListItemsForViewResponseType value);

    /**
     * Returns the value of the '<em><b>Get Work List Items Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkListItems operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work List Items Response</em>' containment reference.
     * @see #setGetWorkListItemsResponse(GetWorkListItemsResponseType1)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkListItemsResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkListItemsResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkListItemsResponseType1 getGetWorkListItemsResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListItemsResponse <em>Get Work List Items Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work List Items Response</em>' containment reference.
     * @see #getGetWorkListItemsResponse()
     * @generated
     */
    void setGetWorkListItemsResponse(GetWorkListItemsResponseType1 value);

    /**
     * Returns the value of the '<em><b>Get Work List View Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for the getWorkViewListDetails operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work List View Details</em>' containment reference.
     * @see #setGetWorkListViewDetails(GetWorkListViewDetailsType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkListViewDetails()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkListViewDetails' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkListViewDetailsType getGetWorkListViewDetails();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListViewDetails <em>Get Work List View Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work List View Details</em>' containment reference.
     * @see #getGetWorkListViewDetails()
     * @generated
     */
    void setGetWorkListViewDetails(GetWorkListViewDetailsType value);

    /**
     * Returns the value of the '<em><b>Get Work List View Details Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkViewListDetails operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work List View Details Response</em>' containment reference.
     * @see #setGetWorkListViewDetailsResponse(WorkListView)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkListViewDetailsResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkListViewDetailsResponse' namespace='##targetNamespace'"
     * @generated
     */
    WorkListView getGetWorkListViewDetailsResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkListViewDetailsResponse <em>Get Work List View Details Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work List View Details Response</em>' containment reference.
     * @see #getGetWorkListViewDetailsResponse()
     * @generated
     */
    void setGetWorkListViewDetailsResponse(WorkListView value);

    /**
     * Returns the value of the '<em><b>Get Work Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkModel operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Model</em>' containment reference.
     * @see #setGetWorkModel(GetWorkModelType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkModel()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkModel' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkModelType getGetWorkModel();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModel <em>Get Work Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Model</em>' containment reference.
     * @see #getGetWorkModel()
     * @generated
     */
    void setGetWorkModel(GetWorkModelType value);

    /**
     * Returns the value of the '<em><b>Get Work Model Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkModel operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Model Response</em>' containment reference.
     * @see #setGetWorkModelResponse(GetWorkModelResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkModelResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkModelResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkModelResponseType getGetWorkModelResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModelResponse <em>Get Work Model Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Model Response</em>' containment reference.
     * @see #getGetWorkModelResponse()
     * @generated
     */
    void setGetWorkModelResponse(GetWorkModelResponseType value);

    /**
     * Returns the value of the '<em><b>Get Work Models</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkModels operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Models</em>' containment reference.
     * @see #setGetWorkModels(GetWorkModelsType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkModels()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkModels' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkModelsType getGetWorkModels();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModels <em>Get Work Models</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Models</em>' containment reference.
     * @see #getGetWorkModels()
     * @generated
     */
    void setGetWorkModels(GetWorkModelsType value);

    /**
     * Returns the value of the '<em><b>Get Work Models Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkModels operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Models Response</em>' containment reference.
     * @see #setGetWorkModelsResponse(GetWorkModelsResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkModelsResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkModelsResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkModelsResponseType getGetWorkModelsResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkModelsResponse <em>Get Work Models Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Models Response</em>' containment reference.
     * @see #getGetWorkModelsResponse()
     * @generated
     */
    void setGetWorkModelsResponse(GetWorkModelsResponseType value);

    /**
     * Returns the value of the '<em><b>Get Work Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkType operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Type</em>' containment reference.
     * @see #setGetWorkType(GetWorkTypeType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkType()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkType' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkTypeType getGetWorkType();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkType <em>Get Work Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Type</em>' containment reference.
     * @see #getGetWorkType()
     * @generated
     */
    void setGetWorkType(GetWorkTypeType value);

    /**
     * Returns the value of the '<em><b>Get Work Type Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkType operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Type Response</em>' containment reference.
     * @see #setGetWorkTypeResponse(GetWorkTypeResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkTypeResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkTypeResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkTypeResponseType getGetWorkTypeResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypeResponse <em>Get Work Type Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Type Response</em>' containment reference.
     * @see #getGetWorkTypeResponse()
     * @generated
     */
    void setGetWorkTypeResponse(GetWorkTypeResponseType value);

    /**
     * Returns the value of the '<em><b>Get Work Types</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for getWorkTypes operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Types</em>' containment reference.
     * @see #setGetWorkTypes(GetWorkTypesType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkTypes()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkTypes' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkTypesType getGetWorkTypes();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypes <em>Get Work Types</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Types</em>' containment reference.
     * @see #getGetWorkTypes()
     * @generated
     */
    void setGetWorkTypes(GetWorkTypesType value);

    /**
     * Returns the value of the '<em><b>Get Work Types Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the getWorkTypes operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Work Types Response</em>' containment reference.
     * @see #setGetWorkTypesResponse(GetWorkTypesResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_GetWorkTypesResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='getWorkTypesResponse' namespace='##targetNamespace'"
     * @generated
     */
    GetWorkTypesResponseType getGetWorkTypesResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getGetWorkTypesResponse <em>Get Work Types Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Work Types Response</em>' containment reference.
     * @see #getGetWorkTypesResponse()
     * @generated
     */
    void setGetWorkTypesResponse(GetWorkTypesResponseType value);

    /**
     * Returns the value of the '<em><b>On Notification</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for onNotification operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>On Notification</em>' containment reference.
     * @see #setOnNotification(OnNotificationType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_OnNotification()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='onNotification' namespace='##targetNamespace'"
     * @generated
     */
    OnNotificationType getOnNotification();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getOnNotification <em>On Notification</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>On Notification</em>' containment reference.
     * @see #getOnNotification()
     * @generated
     */
    void setOnNotification(OnNotificationType value);

    /**
     * Returns the value of the '<em><b>On Notification Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for onNotification operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>On Notification Response</em>' containment reference.
     * @see #setOnNotificationResponse(OnNotificationResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_OnNotificationResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='onNotificationResponse' namespace='##targetNamespace'"
     * @generated
     */
    OnNotificationResponseType getOnNotificationResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getOnNotificationResponse <em>On Notification Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>On Notification Response</em>' containment reference.
     * @see #getOnNotificationResponse()
     * @generated
     */
    void setOnNotificationResponse(OnNotificationResponseType value);

    /**
     * Returns the value of the '<em><b>Open Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for openWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Open Work Item</em>' containment reference.
     * @see #setOpenWorkItem(OpenWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_OpenWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='openWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    OpenWorkItemType getOpenWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getOpenWorkItem <em>Open Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Open Work Item</em>' containment reference.
     * @see #getOpenWorkItem()
     * @generated
     */
    void setOpenWorkItem(OpenWorkItemType value);

    /**
     * Returns the value of the '<em><b>Open Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the openWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Open Work Item Response</em>' containment reference.
     * @see #setOpenWorkItemResponse(OpenWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_OpenWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='openWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    OpenWorkItemResponseType getOpenWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getOpenWorkItemResponse <em>Open Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Open Work Item Response</em>' containment reference.
     * @see #getOpenWorkItemResponse()
     * @generated
     */
    void setOpenWorkItemResponse(OpenWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Pend Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for pendWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Pend Work Item</em>' containment reference.
     * @see #setPendWorkItem(PendWorkItem)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_PendWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='pendWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    PendWorkItem getPendWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getPendWorkItem <em>Pend Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Pend Work Item</em>' containment reference.
     * @see #getPendWorkItem()
     * @generated
     */
    void setPendWorkItem(PendWorkItem value);

    /**
     * Returns the value of the '<em><b>Pend Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the pendWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Pend Work Item Response</em>' containment reference.
     * @see #setPendWorkItemResponse(PendWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_PendWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='pendWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    PendWorkItemResponseType getPendWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getPendWorkItemResponse <em>Pend Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Pend Work Item Response</em>' containment reference.
     * @see #getPendWorkItemResponse()
     * @generated
     */
    void setPendWorkItemResponse(PendWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Preview Work Item From List</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for previewWorkItemFromList operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Preview Work Item From List</em>' containment reference.
     * @see #setPreviewWorkItemFromList(PreviewWorkItemFromListType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_PreviewWorkItemFromList()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='previewWorkItemFromList' namespace='##targetNamespace'"
     * @generated
     */
    PreviewWorkItemFromListType getPreviewWorkItemFromList();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getPreviewWorkItemFromList <em>Preview Work Item From List</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Preview Work Item From List</em>' containment reference.
     * @see #getPreviewWorkItemFromList()
     * @generated
     */
    void setPreviewWorkItemFromList(PreviewWorkItemFromListType value);

    /**
     * Returns the value of the '<em><b>Preview Work Item From List Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the previewWorkItemFromList operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Preview Work Item From List Response</em>' containment reference.
     * @see #setPreviewWorkItemFromListResponse(PreviewWorkItemFromListResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_PreviewWorkItemFromListResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='previewWorkItemFromListResponse' namespace='##targetNamespace'"
     * @generated
     */
    PreviewWorkItemFromListResponseType getPreviewWorkItemFromListResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getPreviewWorkItemFromListResponse <em>Preview Work Item From List Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Preview Work Item From List Response</em>' containment reference.
     * @see #getPreviewWorkItemFromListResponse()
     * @generated
     */
    void setPreviewWorkItemFromListResponse(PreviewWorkItemFromListResponseType value);

    /**
     * Returns the value of the '<em><b>Push Notification</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for the pushNotification operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Push Notification</em>' containment reference.
     * @see #setPushNotification(PushNotificationType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_PushNotification()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='pushNotification' namespace='##targetNamespace'"
     * @generated
     */
    PushNotificationType getPushNotification();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getPushNotification <em>Push Notification</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Push Notification</em>' containment reference.
     * @see #getPushNotification()
     * @generated
     */
    void setPushNotification(PushNotificationType value);

    /**
     * Returns the value of the '<em><b>Reallocate Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for reallocateWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reallocate Work Item</em>' containment reference.
     * @see #setReallocateWorkItem(ReallocateWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ReallocateWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='reallocateWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    ReallocateWorkItemType getReallocateWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItem <em>Reallocate Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reallocate Work Item</em>' containment reference.
     * @see #getReallocateWorkItem()
     * @generated
     */
    void setReallocateWorkItem(ReallocateWorkItemType value);

    /**
     * Returns the value of the '<em><b>Reallocate Work Item Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for reallocateWorkItemData operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reallocate Work Item Data</em>' containment reference.
     * @see #setReallocateWorkItemData(ReallocateWorkItemData)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ReallocateWorkItemData()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='reallocateWorkItemData' namespace='##targetNamespace'"
     * @generated
     */
    ReallocateWorkItemData getReallocateWorkItemData();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemData <em>Reallocate Work Item Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reallocate Work Item Data</em>' containment reference.
     * @see #getReallocateWorkItemData()
     * @generated
     */
    void setReallocateWorkItemData(ReallocateWorkItemData value);

    /**
     * Returns the value of the '<em><b>Reallocate Work Item Data Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the reallocateWorkItemData operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reallocate Work Item Data Response</em>' containment reference.
     * @see #setReallocateWorkItemDataResponse(ReallocateWorkItemDataResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ReallocateWorkItemDataResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='reallocateWorkItemDataResponse' namespace='##targetNamespace'"
     * @generated
     */
    ReallocateWorkItemDataResponseType getReallocateWorkItemDataResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemDataResponse <em>Reallocate Work Item Data Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reallocate Work Item Data Response</em>' containment reference.
     * @see #getReallocateWorkItemDataResponse()
     * @generated
     */
    void setReallocateWorkItemDataResponse(ReallocateWorkItemDataResponseType value);

    /**
     * Returns the value of the '<em><b>Reallocate Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the reallocateWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reallocate Work Item Response</em>' containment reference.
     * @see #setReallocateWorkItemResponse(ReallocateWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ReallocateWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='reallocateWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    ReallocateWorkItemResponseType getReallocateWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getReallocateWorkItemResponse <em>Reallocate Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reallocate Work Item Response</em>' containment reference.
     * @see #getReallocateWorkItemResponse()
     * @generated
     */
    void setReallocateWorkItemResponse(ReallocateWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Reschedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for rescheduleWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reschedule Work Item</em>' containment reference.
     * @see #setRescheduleWorkItem(RescheduleWorkItem)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_RescheduleWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='rescheduleWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    RescheduleWorkItem getRescheduleWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getRescheduleWorkItem <em>Reschedule Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reschedule Work Item</em>' containment reference.
     * @see #getRescheduleWorkItem()
     * @generated
     */
    void setRescheduleWorkItem(RescheduleWorkItem value);

    /**
     * Returns the value of the '<em><b>Reschedule Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the rescheduleWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reschedule Work Item Response</em>' containment reference.
     * @see #setRescheduleWorkItemResponse(RescheduleWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_RescheduleWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='rescheduleWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    RescheduleWorkItemResponseType getRescheduleWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getRescheduleWorkItemResponse <em>Reschedule Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reschedule Work Item Response</em>' containment reference.
     * @see #getRescheduleWorkItemResponse()
     * @generated
     */
    void setRescheduleWorkItemResponse(RescheduleWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Resume Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for the resumeWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resume Work Item</em>' containment reference.
     * @see #setResumeWorkItem(ResumeWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ResumeWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='resumeWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    ResumeWorkItemType getResumeWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getResumeWorkItem <em>Resume Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resume Work Item</em>' containment reference.
     * @see #getResumeWorkItem()
     * @generated
     */
    void setResumeWorkItem(ResumeWorkItemType value);

    /**
     * Returns the value of the '<em><b>Resume Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the resumeWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resume Work Item Response</em>' containment reference.
     * @see #setResumeWorkItemResponse(ResumeWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ResumeWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='resumeWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    ResumeWorkItemResponseType getResumeWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getResumeWorkItemResponse <em>Resume Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resume Work Item Response</em>' containment reference.
     * @see #getResumeWorkItemResponse()
     * @generated
     */
    void setResumeWorkItemResponse(ResumeWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Save Open Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for saveOpenWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Save Open Work Item</em>' containment reference.
     * @see #setSaveOpenWorkItem(SaveOpenWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SaveOpenWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='saveOpenWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    SaveOpenWorkItemType getSaveOpenWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSaveOpenWorkItem <em>Save Open Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Save Open Work Item</em>' containment reference.
     * @see #getSaveOpenWorkItem()
     * @generated
     */
    void setSaveOpenWorkItem(SaveOpenWorkItemType value);

    /**
     * Returns the value of the '<em><b>Save Open Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for saveOpenWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Save Open Work Item Response</em>' containment reference.
     * @see #setSaveOpenWorkItemResponse(SaveOpenWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SaveOpenWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='saveOpenWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    SaveOpenWorkItemResponseType getSaveOpenWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSaveOpenWorkItemResponse <em>Save Open Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Save Open Work Item Response</em>' containment reference.
     * @see #getSaveOpenWorkItemResponse()
     * @generated
     */
    void setSaveOpenWorkItemResponse(SaveOpenWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Schedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for scheduleWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Schedule Work Item</em>' containment reference.
     * @see #setScheduleWorkItem(ScheduleWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ScheduleWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='scheduleWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    ScheduleWorkItemType getScheduleWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItem <em>Schedule Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schedule Work Item</em>' containment reference.
     * @see #getScheduleWorkItem()
     * @generated
     */
    void setScheduleWorkItem(ScheduleWorkItemType value);

    /**
     * Returns the value of the '<em><b>Schedule Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the scheduleWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Schedule Work Item Response</em>' containment reference.
     * @see #setScheduleWorkItemResponse(ScheduleWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ScheduleWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='scheduleWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    ScheduleWorkItemResponseType getScheduleWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemResponse <em>Schedule Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schedule Work Item Response</em>' containment reference.
     * @see #getScheduleWorkItemResponse()
     * @generated
     */
    void setScheduleWorkItemResponse(ScheduleWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Schedule Work Item With Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for scheduleWorkItemWithModel operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Schedule Work Item With Model</em>' containment reference.
     * @see #setScheduleWorkItemWithModel(ScheduleWorkItemWithModelType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ScheduleWorkItemWithModel()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='scheduleWorkItemWithModel' namespace='##targetNamespace'"
     * @generated
     */
    ScheduleWorkItemWithModelType getScheduleWorkItemWithModel();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemWithModel <em>Schedule Work Item With Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schedule Work Item With Model</em>' containment reference.
     * @see #getScheduleWorkItemWithModel()
     * @generated
     */
    void setScheduleWorkItemWithModel(ScheduleWorkItemWithModelType value);

    /**
     * Returns the value of the '<em><b>Schedule Work Item With Model Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the scheduleWorkItemWithModel operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Schedule Work Item With Model Response</em>' containment reference.
     * @see #setScheduleWorkItemWithModelResponse(ScheduleWorkItemWithModelResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_ScheduleWorkItemWithModelResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='scheduleWorkItemWithModelResponse' namespace='##targetNamespace'"
     * @generated
     */
    ScheduleWorkItemWithModelResponseType getScheduleWorkItemWithModelResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getScheduleWorkItemWithModelResponse <em>Schedule Work Item With Model Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schedule Work Item With Model Response</em>' containment reference.
     * @see #getScheduleWorkItemWithModelResponse()
     * @generated
     */
    void setScheduleWorkItemWithModelResponse(ScheduleWorkItemWithModelResponseType value);

    /**
     * Returns the value of the '<em><b>Set Org Entity Config Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for setOrgEntityConfigAttributes operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Set Org Entity Config Attributes</em>' containment reference.
     * @see #setSetOrgEntityConfigAttributes(SetOrgEntityConfigAttributesType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SetOrgEntityConfigAttributes()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='setOrgEntityConfigAttributes' namespace='##targetNamespace'"
     * @generated
     */
    SetOrgEntityConfigAttributesType getSetOrgEntityConfigAttributes();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSetOrgEntityConfigAttributes <em>Set Org Entity Config Attributes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Org Entity Config Attributes</em>' containment reference.
     * @see #getSetOrgEntityConfigAttributes()
     * @generated
     */
    void setSetOrgEntityConfigAttributes(SetOrgEntityConfigAttributesType value);

    /**
     * Returns the value of the '<em><b>Set Org Entity Config Attributes Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for setOrgEntityConfigAttributes operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Set Org Entity Config Attributes Response</em>' containment reference.
     * @see #setSetOrgEntityConfigAttributesResponse(SetOrgEntityConfigAttributesResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SetOrgEntityConfigAttributesResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='setOrgEntityConfigAttributesResponse' namespace='##targetNamespace'"
     * @generated
     */
    SetOrgEntityConfigAttributesResponseType getSetOrgEntityConfigAttributesResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSetOrgEntityConfigAttributesResponse <em>Set Org Entity Config Attributes Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Org Entity Config Attributes Response</em>' containment reference.
     * @see #getSetOrgEntityConfigAttributesResponse()
     * @generated
     */
    void setSetOrgEntityConfigAttributesResponse(SetOrgEntityConfigAttributesResponseType value);

    /**
     * Returns the value of the '<em><b>Set Resource Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for setResourceOrderFilterCriteria operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Set Resource Order Filter Criteria</em>' containment reference.
     * @see #setSetResourceOrderFilterCriteria(SetResourceOrderFilterCriteriaType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SetResourceOrderFilterCriteria()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='setResourceOrderFilterCriteria' namespace='##targetNamespace'"
     * @generated
     */
    SetResourceOrderFilterCriteriaType getSetResourceOrderFilterCriteria();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSetResourceOrderFilterCriteria <em>Set Resource Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Resource Order Filter Criteria</em>' containment reference.
     * @see #getSetResourceOrderFilterCriteria()
     * @generated
     */
    void setSetResourceOrderFilterCriteria(SetResourceOrderFilterCriteriaType value);

    /**
     * Returns the value of the '<em><b>Set Resource Order Filter Criteria Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for setResourceOrderFilterCriteria operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Set Resource Order Filter Criteria Response</em>' containment reference.
     * @see #setSetResourceOrderFilterCriteriaResponse(SetResourceOrderFilterCriteriaResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SetResourceOrderFilterCriteriaResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='setResourceOrderFilterCriteriaResponse' namespace='##targetNamespace'"
     * @generated
     */
    SetResourceOrderFilterCriteriaResponseType getSetResourceOrderFilterCriteriaResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSetResourceOrderFilterCriteriaResponse <em>Set Resource Order Filter Criteria Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Resource Order Filter Criteria Response</em>' containment reference.
     * @see #getSetResourceOrderFilterCriteriaResponse()
     * @generated
     */
    void setSetResourceOrderFilterCriteriaResponse(SetResourceOrderFilterCriteriaResponseType value);

    /**
     * Returns the value of the '<em><b>Set Work Item Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for setWorkItemPriority operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Set Work Item Priority</em>' containment reference.
     * @see #setSetWorkItemPriority(SetWorkItemPriority)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SetWorkItemPriority()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='setWorkItemPriority' namespace='##targetNamespace'"
     * @generated
     */
    SetWorkItemPriority getSetWorkItemPriority();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSetWorkItemPriority <em>Set Work Item Priority</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Work Item Priority</em>' containment reference.
     * @see #getSetWorkItemPriority()
     * @generated
     */
    void setSetWorkItemPriority(SetWorkItemPriority value);

    /**
     * Returns the value of the '<em><b>Set Work Item Priority Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the setWorkItemPriority operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Set Work Item Priority Response</em>' containment reference.
     * @see #setSetWorkItemPriorityResponse(SetWorkItemPriorityResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SetWorkItemPriorityResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='setWorkItemPriorityResponse' namespace='##targetNamespace'"
     * @generated
     */
    SetWorkItemPriorityResponseType getSetWorkItemPriorityResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSetWorkItemPriorityResponse <em>Set Work Item Priority Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Set Work Item Priority Response</em>' containment reference.
     * @see #getSetWorkItemPriorityResponse()
     * @generated
     */
    void setSetWorkItemPriorityResponse(SetWorkItemPriorityResponseType value);

    /**
     * Returns the value of the '<em><b>Skip Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for skipWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Skip Work Item</em>' containment reference.
     * @see #setSkipWorkItem(SkipWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SkipWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='skipWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    SkipWorkItemType getSkipWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSkipWorkItem <em>Skip Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Skip Work Item</em>' containment reference.
     * @see #getSkipWorkItem()
     * @generated
     */
    void setSkipWorkItem(SkipWorkItemType value);

    /**
     * Returns the value of the '<em><b>Skip Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the skipWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Skip Work Item Response</em>' containment reference.
     * @see #setSkipWorkItemResponse(SkipWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SkipWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='skipWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    SkipWorkItemResponseType getSkipWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSkipWorkItemResponse <em>Skip Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Skip Work Item Response</em>' containment reference.
     * @see #getSkipWorkItemResponse()
     * @generated
     */
    void setSkipWorkItemResponse(SkipWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Start Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for startGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Group</em>' containment reference.
     * @see #setStartGroup(StartGroupType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_StartGroup()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='startGroup' namespace='##targetNamespace'"
     * @generated
     */
    StartGroupType getStartGroup();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getStartGroup <em>Start Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Group</em>' containment reference.
     * @see #getStartGroup()
     * @generated
     */
    void setStartGroup(StartGroupType value);

    /**
     * Returns the value of the '<em><b>Start Group Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for startGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Group Response</em>' containment reference.
     * @see #setStartGroupResponse(StartGroupResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_StartGroupResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='startGroupResponse' namespace='##targetNamespace'"
     * @generated
     */
    StartGroupResponseType getStartGroupResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getStartGroupResponse <em>Start Group Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Group Response</em>' containment reference.
     * @see #getStartGroupResponse()
     * @generated
     */
    void setStartGroupResponse(StartGroupResponseType value);

    /**
     * Returns the value of the '<em><b>Suspend Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for the suspendWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Suspend Work Item</em>' containment reference.
     * @see #setSuspendWorkItem(SuspendWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SuspendWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='suspendWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    SuspendWorkItemType getSuspendWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSuspendWorkItem <em>Suspend Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Suspend Work Item</em>' containment reference.
     * @see #getSuspendWorkItem()
     * @generated
     */
    void setSuspendWorkItem(SuspendWorkItemType value);

    /**
     * Returns the value of the '<em><b>Suspend Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the suspendWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Suspend Work Item Response</em>' containment reference.
     * @see #setSuspendWorkItemResponse(SuspendWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_SuspendWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='suspendWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    SuspendWorkItemResponseType getSuspendWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getSuspendWorkItemResponse <em>Suspend Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Suspend Work Item Response</em>' containment reference.
     * @see #getSuspendWorkItemResponse()
     * @generated
     */
    void setSuspendWorkItemResponse(SuspendWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Unallocate Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for unallocateWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Unallocate Work Item</em>' containment reference.
     * @see #setUnallocateWorkItem(UnallocateWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_UnallocateWorkItem()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='unallocateWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    UnallocateWorkItemType getUnallocateWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getUnallocateWorkItem <em>Unallocate Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Unallocate Work Item</em>' containment reference.
     * @see #getUnallocateWorkItem()
     * @generated
     */
    void setUnallocateWorkItem(UnallocateWorkItemType value);

    /**
     * Returns the value of the '<em><b>Unallocate Work Item Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the unallocateWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Unallocate Work Item Response</em>' containment reference.
     * @see #setUnallocateWorkItemResponse(UnallocateWorkItemResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_UnallocateWorkItemResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='unallocateWorkItemResponse' namespace='##targetNamespace'"
     * @generated
     */
    UnallocateWorkItemResponseType getUnallocateWorkItemResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getUnallocateWorkItemResponse <em>Unallocate Work Item Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Unallocate Work Item Response</em>' containment reference.
     * @see #getUnallocateWorkItemResponse()
     * @generated
     */
    void setUnallocateWorkItemResponse(UnallocateWorkItemResponseType value);

    /**
     * Returns the value of the '<em><b>Unlock Work List View</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for the unlockWorkViewList operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Unlock Work List View</em>' containment reference.
     * @see #setUnlockWorkListView(UnlockWorkListViewType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_UnlockWorkListView()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='unlockWorkListView' namespace='##targetNamespace'"
     * @generated
     */
    UnlockWorkListViewType getUnlockWorkListView();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getUnlockWorkListView <em>Unlock Work List View</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Unlock Work List View</em>' containment reference.
     * @see #getUnlockWorkListView()
     * @generated
     */
    void setUnlockWorkListView(UnlockWorkListViewType value);

    /**
     * Returns the value of the '<em><b>Unlock Work List View Response</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Response element for the unlockWorkViewList operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Unlock Work List View Response</em>' containment reference.
     * @see #setUnlockWorkListViewResponse(UnlockWorkListViewResponseType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getDocumentRoot_UnlockWorkListViewResponse()
     * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='unlockWorkListViewResponse' namespace='##targetNamespace'"
     * @generated
     */
    UnlockWorkListViewResponseType getUnlockWorkListViewResponse();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.DocumentRoot#getUnlockWorkListViewResponse <em>Unlock Work List View Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Unlock Work List View Response</em>' containment reference.
     * @see #getUnlockWorkListViewResponse()
     * @generated
     */
    void setUnlockWorkListViewResponse(UnlockWorkListViewResponseType value);

} // DocumentRoot
