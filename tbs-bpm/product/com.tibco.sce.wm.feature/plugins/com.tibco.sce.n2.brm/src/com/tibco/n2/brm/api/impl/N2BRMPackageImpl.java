/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.n2.brm.api.*;
import com.tibco.n2.brm.api.util.N2BRMValidator;
import com.tibco.n2.brm.workmodel.WorkmodelPackage;
import com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl;
import com.tibco.n2.common.api.exception.ExceptionPackage;
import com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl;
import com.tibco.n2.common.datamodel.DatamodelPackage;
import com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl;
import com.tibco.n2.common.organisation.api.OrganisationPackage;
import com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl;
import com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage;
import com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl;
import com.tibco.n2.common.worktype.WorktypePackage;
import com.tibco.n2.common.worktype.impl.WorktypePackageImpl;
import com.tibco.xpd.script.descriptor.DescriptorPackage;
import com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class N2BRMPackageImpl extends EPackageImpl implements N2BRMPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass addCurrentResourceToViewResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass addCurrentResourceToViewTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass allocateAndOpenNextWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass allocateAndOpenNextWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass allocateAndOpenWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass allocateAndOpenWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass allocateWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass allocateWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass allocationHistoryEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncCancelWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncCancelWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncEndGroupResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncEndGroupTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncRescheduleWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncRescheduleWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncResumeWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncResumeWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncScheduleWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncScheduleWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncScheduleWorkItemWithModelResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncScheduleWorkItemWithModelTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncStartGroupResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncStartGroupTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncSuspendWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncSuspendWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass asyncWorkItemDetailsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attributeAliasListTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass baseItemInfoEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass baseModelInfoEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass cancelWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass cancelWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass chainedWorkItemNotificationTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass closeWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass closeWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass columnDefinitionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass columnOrderEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass completeWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass completeWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass createWorkListViewResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteCurrentResourceFromViewResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteCurrentResourceFromViewTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteOrgEntityConfigAttributesResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteOrgEntityConfigAttributesTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteWorkListViewResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deleteWorkListViewTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass editWorkListViewResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass editWorkListViewTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass enableWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass enableWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass endGroupResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass endGroupTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getAllocatedWorkListItemsTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getBatchGroupIdsResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getBatchWorkItemIdsResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getEditableWorkListViewsResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getEditableWorkListViewsTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getOfferSetResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getOfferSetTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getOrgEntityConfigAttributesAvailableResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getOrgEntityConfigAttributesAvailableTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getOrgEntityConfigAttributesResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getOrgEntityConfigAttributesTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getPublicWorkListViewsResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getPublicWorkListViewsTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getResourceOrderFilterCriteriaResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getResourceOrderFilterCriteriaTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getViewsForResourceResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getViewsForResourceTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkItemHeaderResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkItemHeaderTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkItemOrderFilterResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkItemOrderFilterTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkListItemsForGlobalDataResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkListItemsForGlobalDataTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkListItemsForViewResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkListItemsForViewTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkListItemsResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkListItemsResponseType1EClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkListItemsTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkListViewDetailsTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkModelResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkModelsResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkModelsTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkModelTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkTypeResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkTypesResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkTypesTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass getWorkTypeTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hiddenPeriodEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass itemEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass itemBodyEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass itemContextEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass itemDurationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass itemPrivilegeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass itemScheduleEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass managedObjectIDEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass nextWorkItemEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass objectIDEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass onNotificationResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass onNotificationTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass openWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass openWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orderFilterCriteriaEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orgEntityConfigAttributeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orgEntityConfigAttributesAvailableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass orgEntityConfigAttributeSetEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parameterTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pendWorkItemEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pendWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass previewWorkItemFromListResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass previewWorkItemFromListTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass privilegeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pushNotificationTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass reallocateWorkItemDataEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass reallocateWorkItemDataResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass reallocateWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass reallocateWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass rescheduleWorkItemEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass rescheduleWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass resumeWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass resumeWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass saveOpenWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass saveOpenWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass scheduleWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass scheduleWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass scheduleWorkItemWithModelResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass scheduleWorkItemWithModelTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass setOrgEntityConfigAttributesResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass setOrgEntityConfigAttributesTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass setResourceOrderFilterCriteriaResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass setResourceOrderFilterCriteriaTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass setWorkItemPriorityEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass setWorkItemPriorityResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass skipWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass skipWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass startGroupResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass startGroupTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass suspendWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass suspendWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass unallocateWorkItemResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass unallocateWorkItemTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass unlockWorkListViewResponseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass unlockWorkListViewTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemAttributesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemBodyEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemFlagsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemHeaderEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemIDandPriorityTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemPreviewEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workItemPriorityTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workListViewEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workListViewCommonEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workListViewEditEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workListViewPageItemEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelEntitiesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelEntityEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelListEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelMappingEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelScriptEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelScriptsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelSpecificationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workModelTypesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workTypeListEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum columnCapabilityEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum columnTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum distributionStrategyEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum methodAuthorisationActionEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum methodAuthorisationComponentEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum resourcesRequiredTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum scheduleStatusEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum workGroupTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum workItemScriptOperationEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum workItemScriptTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum workItemStateEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute10TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute11TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute12TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute13TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute14TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute21TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute22TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute23TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute24TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute25TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute26TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute27TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute28TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute29TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute2TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute30TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute31TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute32TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute33TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute34TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute35TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute36TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute37TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute38TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute39TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute3TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute40TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute4TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute8TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType attribute9TypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType columnCapabilityObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType columnTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType descriptionTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType distributionStrategyObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType lockerTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType methodAuthorisationActionObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType methodAuthorisationComponentObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType nameTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType ownerTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType resourcesRequiredTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType scheduleStatusObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType scriptOperationTypeEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType workGroupTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType workItemScriptOperationObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType workItemScriptTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType workItemStateObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.n2.brm.api.N2BRMPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private N2BRMPackageImpl() {
        super(eNS_URI, N2BRMFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link N2BRMPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static N2BRMPackage init() {
        if (isInited) return (N2BRMPackage)EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI);

        // Obtain or create and register package
        N2BRMPackageImpl theN2BRMPackage = (N2BRMPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof N2BRMPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new N2BRMPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        DatamodelPackageImpl theDatamodelPackage = (DatamodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) instanceof DatamodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) : DatamodelPackage.eINSTANCE);
        ExceptionPackageImpl theExceptionPackage = (ExceptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI) instanceof ExceptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI) : ExceptionPackage.eINSTANCE);
        OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
        DescriptorPackageImpl theDescriptorPackage =
                (DescriptorPackageImpl) (EPackage.Registry.INSTANCE
                        .getEPackage(DescriptorPackage.eNS_URI) instanceof DescriptorPackageImpl ? EPackage.Registry.INSTANCE
                        .getEPackage(DescriptorPackage.eNS_URI)
                        : DescriptorPackage.eINSTANCE);
        WorkmodelPackageImpl theWorkmodelPackage = (WorkmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI) instanceof WorkmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI) : WorkmodelPackage.eINSTANCE);
        WorktypePackageImpl theWorktypePackage = (WorktypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI) instanceof WorktypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI) : WorktypePackage.eINSTANCE);
        PageactivitymodelPackageImpl thePageactivitymodelPackage = (PageactivitymodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) instanceof PageactivitymodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) : PageactivitymodelPackage.eINSTANCE);

        // Create package meta-data objects
        theN2BRMPackage.createPackageContents();
        theDatamodelPackage.createPackageContents();
        theExceptionPackage.createPackageContents();
        theOrganisationPackage.createPackageContents();
        theDescriptorPackage.createPackageContents();
        theWorkmodelPackage.createPackageContents();
        theWorktypePackage.createPackageContents();
        thePageactivitymodelPackage.createPackageContents();

        // Initialize created meta-data
        theN2BRMPackage.initializePackageContents();
        theDatamodelPackage.initializePackageContents();
        theExceptionPackage.initializePackageContents();
        theOrganisationPackage.initializePackageContents();
        theDescriptorPackage.initializePackageContents();
        theWorkmodelPackage.initializePackageContents();
        theWorktypePackage.initializePackageContents();
        thePageactivitymodelPackage.initializePackageContents();

        // Register package validator
        EValidator.Registry.INSTANCE.put
            (theN2BRMPackage, 
             new EValidator.Descriptor() {
                 public EValidator getEValidator() {
                     return N2BRMValidator.INSTANCE;
                 }
             });

        // Mark meta-data to indicate it can't be changed
        theN2BRMPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(N2BRMPackage.eNS_URI, theN2BRMPackage);
        return theN2BRMPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAddCurrentResourceToViewResponseType() {
        return addCurrentResourceToViewResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAddCurrentResourceToViewResponseType_WorkListViewID() {
        return (EAttribute)addCurrentResourceToViewResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAddCurrentResourceToViewType() {
        return addCurrentResourceToViewTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAddCurrentResourceToViewType_WorkListViewID() {
        return (EAttribute)addCurrentResourceToViewTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAllocateAndOpenNextWorkItemResponseType() {
        return allocateAndOpenNextWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAllocateAndOpenNextWorkItemResponseType_WorkItem() {
        return (EReference)allocateAndOpenNextWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAllocateAndOpenNextWorkItemType() {
        return allocateAndOpenNextWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocateAndOpenNextWorkItemType_Resource() {
        return (EAttribute)allocateAndOpenNextWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocateAndOpenNextWorkItemType_WorkListViewID() {
        return (EAttribute)allocateAndOpenNextWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAllocateAndOpenWorkItemResponseType() {
        return allocateAndOpenWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocateAndOpenWorkItemResponseType_Group() {
        return (EAttribute)allocateAndOpenWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAllocateAndOpenWorkItemResponseType_WorkItem() {
        return (EReference)allocateAndOpenWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAllocateAndOpenWorkItemType() {
        return allocateAndOpenWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocateAndOpenWorkItemType_Group() {
        return (EAttribute)allocateAndOpenWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAllocateAndOpenWorkItemType_WorkItemID() {
        return (EReference)allocateAndOpenWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocateAndOpenWorkItemType_Resource() {
        return (EAttribute)allocateAndOpenWorkItemTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAllocateWorkItemResponseType() {
        return allocateWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocateWorkItemResponseType_Group() {
        return (EAttribute)allocateWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAllocateWorkItemResponseType_WorkItem() {
        return (EReference)allocateWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAllocateWorkItemType() {
        return allocateWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocateWorkItemType_Group() {
        return (EAttribute)allocateWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAllocateWorkItemType_WorkItemID() {
        return (EReference)allocateWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocateWorkItemType_Resource() {
        return (EAttribute)allocateWorkItemTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAllocationHistory() {
        return allocationHistoryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocationHistory_ResourceID() {
        return (EAttribute)allocationHistoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocationHistory_AllocationDate() {
        return (EAttribute)allocationHistoryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAllocationHistory_AllocationID() {
        return (EAttribute)allocationHistoryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncCancelWorkItemResponseType() {
        return asyncCancelWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncCancelWorkItemResponseType_MessageDetails() {
        return (EReference)asyncCancelWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncCancelWorkItemResponseType_ErrorMessage() {
        return (EReference)asyncCancelWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncCancelWorkItemType() {
        return asyncCancelWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncCancelWorkItemType_MessageDetails() {
        return (EReference)asyncCancelWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncEndGroupResponseType() {
        return asyncEndGroupResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncEndGroupResponseType_ActivityID() {
        return (EAttribute)asyncEndGroupResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncEndGroupResponseType_GroupID() {
        return (EAttribute)asyncEndGroupResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncEndGroupResponseType_ErrorMessage() {
        return (EReference)asyncEndGroupResponseTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncEndGroupType() {
        return asyncEndGroupTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncEndGroupType_ActivityID() {
        return (EAttribute)asyncEndGroupTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncEndGroupType_EndGroup() {
        return (EReference)asyncEndGroupTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncRescheduleWorkItemResponseType() {
        return asyncRescheduleWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncRescheduleWorkItemResponseType_MessageDetails() {
        return (EReference)asyncRescheduleWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncRescheduleWorkItemResponseType_ErrorMessage() {
        return (EReference)asyncRescheduleWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncRescheduleWorkItemType() {
        return asyncRescheduleWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncRescheduleWorkItemType_ItemSchedule() {
        return (EReference)asyncRescheduleWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncRescheduleWorkItemType_MessageDetails() {
        return (EReference)asyncRescheduleWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncRescheduleWorkItemType_ItemBody() {
        return (EReference)asyncRescheduleWorkItemTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncResumeWorkItemResponseType() {
        return asyncResumeWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncResumeWorkItemResponseType_MessageDetails() {
        return (EReference)asyncResumeWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncResumeWorkItemResponseType_ErrorMessage() {
        return (EReference)asyncResumeWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncResumeWorkItemType() {
        return asyncResumeWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncResumeWorkItemType_MessageDetails() {
        return (EReference)asyncResumeWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncScheduleWorkItemResponseType() {
        return asyncScheduleWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncScheduleWorkItemResponseType_MessageDetails() {
        return (EReference)asyncScheduleWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncScheduleWorkItemResponseType_ErrorMessage() {
        return (EReference)asyncScheduleWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncScheduleWorkItemType() {
        return asyncScheduleWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncScheduleWorkItemType_ScheduleWorkItem() {
        return (EReference)asyncScheduleWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncScheduleWorkItemType_MessageDetails() {
        return (EReference)asyncScheduleWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncScheduleWorkItemWithModelResponseType() {
        return asyncScheduleWorkItemWithModelResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncScheduleWorkItemWithModelResponseType_MessageDetails() {
        return (EReference)asyncScheduleWorkItemWithModelResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncScheduleWorkItemWithModelResponseType_ErrorMessage() {
        return (EReference)asyncScheduleWorkItemWithModelResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncScheduleWorkItemWithModelType() {
        return asyncScheduleWorkItemWithModelTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncScheduleWorkItemWithModelType_ScheduleWorkItemWithModel() {
        return (EReference)asyncScheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncScheduleWorkItemWithModelType_MessageDetails() {
        return (EReference)asyncScheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncStartGroupResponseType() {
        return asyncStartGroupResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncStartGroupResponseType_ActivityID() {
        return (EAttribute)asyncStartGroupResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncStartGroupResponseType_GroupID() {
        return (EAttribute)asyncStartGroupResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncStartGroupResponseType_ErrorMessage() {
        return (EReference)asyncStartGroupResponseTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncStartGroupType() {
        return asyncStartGroupTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncStartGroupType_ActivityID() {
        return (EAttribute)asyncStartGroupTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncStartGroupType_GroupID() {
        return (EAttribute)asyncStartGroupTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncStartGroupType_StartGroup() {
        return (EReference)asyncStartGroupTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncSuspendWorkItemResponseType() {
        return asyncSuspendWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncSuspendWorkItemResponseType_MessageDetails() {
        return (EReference)asyncSuspendWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncSuspendWorkItemResponseType_ErrorMessage() {
        return (EReference)asyncSuspendWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncSuspendWorkItemType() {
        return asyncSuspendWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncSuspendWorkItemType_MessageDetails() {
        return (EReference)asyncSuspendWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncSuspendWorkItemType_ForceSuspend() {
        return (EAttribute)asyncSuspendWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAsyncWorkItemDetails() {
        return asyncWorkItemDetailsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAsyncWorkItemDetails_WorkItemId() {
        return (EReference)asyncWorkItemDetailsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncWorkItemDetails_ActivityID() {
        return (EAttribute)asyncWorkItemDetailsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncWorkItemDetails_UaSequenceId() {
        return (EAttribute)asyncWorkItemDetailsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAsyncWorkItemDetails_BrmSequenceId() {
        return (EAttribute)asyncWorkItemDetailsEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAttributeAliasListType() {
        return attributeAliasListTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAttributeAliasListType_AttributeAlias() {
        return (EReference)attributeAliasListTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getBaseItemInfo() {
        return baseItemInfoEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseItemInfo_Name() {
        return (EAttribute)baseItemInfoEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseItemInfo_Description() {
        return (EAttribute)baseItemInfoEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseItemInfo_DistributionStrategy() {
        return (EAttribute)baseItemInfoEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseItemInfo_GroupID() {
        return (EAttribute)baseItemInfoEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseItemInfo_Priority() {
        return (EAttribute)baseItemInfoEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getBaseModelInfo() {
        return baseModelInfoEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseModelInfo_Description() {
        return (EAttribute)baseModelInfoEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseModelInfo_Name() {
        return (EAttribute)baseModelInfoEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBaseModelInfo_Priority() {
        return (EAttribute)baseModelInfoEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCancelWorkItemResponseType() {
        return cancelWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCancelWorkItemResponseType_Success() {
        return (EAttribute)cancelWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCancelWorkItemType() {
        return cancelWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCancelWorkItemType_WorkItemID() {
        return (EReference)cancelWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChainedWorkItemNotificationType() {
        return chainedWorkItemNotificationTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChainedWorkItemNotificationType_GroupID() {
        return (EAttribute)chainedWorkItemNotificationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChainedWorkItemNotificationType_WorkItemID() {
        return (EReference)chainedWorkItemNotificationTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCloseWorkItemResponseType() {
        return closeWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCloseWorkItemResponseType_Group() {
        return (EAttribute)closeWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCloseWorkItemResponseType_WorkItemID() {
        return (EReference)closeWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCloseWorkItemType() {
        return closeWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCloseWorkItemType_Group() {
        return (EAttribute)closeWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCloseWorkItemType_WorkItemID() {
        return (EReference)closeWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCloseWorkItemType_WorkItemPayload() {
        return (EReference)closeWorkItemTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCloseWorkItemType_HiddenPeriod() {
        return (EReference)closeWorkItemTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getColumnDefinition() {
        return columnDefinitionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getColumnDefinition_Capability() {
        return (EAttribute)columnDefinitionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getColumnDefinition_Description() {
        return (EAttribute)columnDefinitionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getColumnDefinition_Id() {
        return (EAttribute)columnDefinitionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getColumnDefinition_Name() {
        return (EAttribute)columnDefinitionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getColumnDefinition_Type() {
        return (EAttribute)columnDefinitionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getColumnOrder() {
        return columnOrderEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getColumnOrder_ColumnDef() {
        return (EReference)columnOrderEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getColumnOrder_Ascending() {
        return (EAttribute)columnOrderEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCompleteWorkItemResponseType() {
        return completeWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCompleteWorkItemResponseType_GroupID() {
        return (EAttribute)completeWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCompleteWorkItemResponseType_NextWorkItem() {
        return (EReference)completeWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCompleteWorkItemType() {
        return completeWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCompleteWorkItemType_WorkItemID() {
        return (EReference)completeWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCompleteWorkItemType_WorkItemPayload() {
        return (EReference)completeWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCompleteWorkItemType_GetNextPiledItem() {
        return (EAttribute)completeWorkItemTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCreateWorkListViewResponseType() {
        return createWorkListViewResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCreateWorkListViewResponseType_WorkListViewID() {
        return (EAttribute)createWorkListViewResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteCurrentResourceFromViewResponseType() {
        return deleteCurrentResourceFromViewResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeleteCurrentResourceFromViewResponseType_WorkListViewID() {
        return (EAttribute)deleteCurrentResourceFromViewResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteCurrentResourceFromViewType() {
        return deleteCurrentResourceFromViewTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeleteCurrentResourceFromViewType_WorkListViewID() {
        return (EAttribute)deleteCurrentResourceFromViewTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteOrgEntityConfigAttributesResponseType() {
        return deleteOrgEntityConfigAttributesResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeleteOrgEntityConfigAttributesResponseType_Success() {
        return (EAttribute)deleteOrgEntityConfigAttributesResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteOrgEntityConfigAttributesType() {
        return deleteOrgEntityConfigAttributesTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeleteOrgEntityConfigAttributesType_Group() {
        return (EAttribute)deleteOrgEntityConfigAttributesTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeleteOrgEntityConfigAttributesType_AttributeName() {
        return (EAttribute)deleteOrgEntityConfigAttributesTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeleteOrgEntityConfigAttributesType_Resource() {
        return (EAttribute)deleteOrgEntityConfigAttributesTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteWorkListViewResponseType() {
        return deleteWorkListViewResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeleteWorkListViewResponseType_WorkListViewID() {
        return (EAttribute)deleteWorkListViewResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeleteWorkListViewType() {
        return deleteWorkListViewTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDeleteWorkListViewType_WorkListViewID() {
        return (EAttribute)deleteWorkListViewTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AddCurrentResourceToView() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AddCurrentResourceToViewResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AllocateAndOpenNextWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AllocateAndOpenNextWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AllocateAndOpenWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AllocateAndOpenWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AllocateWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AllocateWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncCancelWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncCancelWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncEndGroup() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncEndGroupResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncRescheduleWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(15);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncRescheduleWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(16);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncResumeWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(17);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncResumeWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(18);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncScheduleWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(19);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncScheduleWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncScheduleWorkItemWithModel() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(21);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncScheduleWorkItemWithModelResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(22);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncStartGroup() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(23);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncStartGroupResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(24);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncSuspendWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(25);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_AsyncSuspendWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(26);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_CancelWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(27);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_CancelWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(28);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ChainedWorkItemNotification() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(29);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_CloseWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(30);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_CloseWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(31);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_CompleteWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(32);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_CompleteWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(33);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_CreateWorkListView() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(34);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_CreateWorkListViewResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(35);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_DeleteCurrentResourceFromView() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(36);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_DeleteCurrentResourceFromViewResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(37);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_DeleteOrgEntityConfigAttributes() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(38);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_DeleteOrgEntityConfigAttributesResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(39);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_DeleteWorkListView() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(40);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_DeleteWorkListViewResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(41);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_EditWorkListView() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(42);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_EditWorkListViewResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(43);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_EnableWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(44);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_EnableWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(45);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_EndGroup() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(46);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_EndGroupResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(47);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetAllocatedWorkListItems() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(48);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetBatchGroupIds() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(49);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetBatchGroupIdsResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(50);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetBatchWorkItemIds() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(51);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetBatchWorkItemIdsResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(52);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetEditableWorkListViews() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(53);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetEditableWorkListViewsResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(54);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetOfferSet() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(55);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetOfferSetResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(56);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetOrgEntityConfigAttributes() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(57);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetOrgEntityConfigAttributesAvailable() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(58);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetOrgEntityConfigAttributesAvailableResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(59);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetOrgEntityConfigAttributesResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(60);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetPublicWorkListViews() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(61);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetPublicWorkListViewsResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(62);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetResourceOrderFilterCriteria() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(63);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetResourceOrderFilterCriteriaResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(64);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetViewsForResource() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(65);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetViewsForResourceResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(66);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkItemHeader() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(67);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkItemHeaderResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(68);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkItemOrderFilter() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(69);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkItemOrderFilterResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(70);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkListItems() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(71);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkListItemsForGlobalData() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(72);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkListItemsForGlobalDataResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(73);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkListItemsForView() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(74);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkListItemsForViewResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(75);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkListItemsResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(76);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkListViewDetails() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(77);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkListViewDetailsResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(78);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkModel() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(79);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkModelResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(80);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkModels() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(81);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkModelsResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(82);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkType() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(83);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkTypeResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(84);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkTypes() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(85);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_GetWorkTypesResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(86);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_OnNotification() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(87);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_OnNotificationResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(88);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_OpenWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(89);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_OpenWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(90);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_PendWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(91);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_PendWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(92);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_PreviewWorkItemFromList() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(93);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_PreviewWorkItemFromListResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(94);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_PushNotification() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(95);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ReallocateWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(96);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ReallocateWorkItemData() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(97);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ReallocateWorkItemDataResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(98);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ReallocateWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(99);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_RescheduleWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(100);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_RescheduleWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(101);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ResumeWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(102);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ResumeWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(103);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SaveOpenWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(104);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SaveOpenWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(105);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ScheduleWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(106);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ScheduleWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(107);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ScheduleWorkItemWithModel() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(108);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ScheduleWorkItemWithModelResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(109);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SetOrgEntityConfigAttributes() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(110);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SetOrgEntityConfigAttributesResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(111);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SetResourceOrderFilterCriteria() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(112);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SetResourceOrderFilterCriteriaResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(113);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SetWorkItemPriority() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(114);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SetWorkItemPriorityResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(115);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SkipWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(116);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SkipWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(117);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_StartGroup() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(118);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_StartGroupResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(119);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SuspendWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(120);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_SuspendWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(121);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_UnallocateWorkItem() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(122);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_UnallocateWorkItemResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(123);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_UnlockWorkListView() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(124);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_UnlockWorkListViewResponse() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(125);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEditWorkListViewResponseType() {
        return editWorkListViewResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEditWorkListViewResponseType_WorkListViewID() {
        return (EAttribute)editWorkListViewResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEditWorkListViewType() {
        return editWorkListViewTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEditWorkListViewType_WorkListViewID() {
        return (EAttribute)editWorkListViewTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnableWorkItemResponseType() {
        return enableWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnableWorkItemResponseType_Success() {
        return (EAttribute)enableWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnableWorkItemType() {
        return enableWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEnableWorkItemType_WorkItemID() {
        return (EReference)enableWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEnableWorkItemType_ItemBody() {
        return (EReference)enableWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEndGroupResponseType() {
        return endGroupResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEndGroupResponseType_Success() {
        return (EAttribute)endGroupResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEndGroupType() {
        return endGroupTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEndGroupType_GroupID() {
        return (EAttribute)endGroupTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetAllocatedWorkListItemsType() {
        return getAllocatedWorkListItemsTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetAllocatedWorkListItemsType_EntityID() {
        return (EReference)getAllocatedWorkListItemsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetAllocatedWorkListItemsType_OrderFilterCriteria() {
        return (EReference)getAllocatedWorkListItemsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetAllocatedWorkListItemsType_GetTotalCount() {
        return (EAttribute)getAllocatedWorkListItemsTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetAllocatedWorkListItemsType_NumberOfItems() {
        return (EAttribute)getAllocatedWorkListItemsTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetAllocatedWorkListItemsType_StartPosition() {
        return (EAttribute)getAllocatedWorkListItemsTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetBatchGroupIdsResponseType() {
        return getBatchGroupIdsResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetBatchGroupIdsResponseType_Group() {
        return (EAttribute)getBatchGroupIdsResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetBatchGroupIdsResponseType_GroupID() {
        return (EAttribute)getBatchGroupIdsResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetBatchWorkItemIdsResponseType() {
        return getBatchWorkItemIdsResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetBatchWorkItemIdsResponseType_Group() {
        return (EAttribute)getBatchWorkItemIdsResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetBatchWorkItemIdsResponseType_WorkItemID() {
        return (EReference)getBatchWorkItemIdsResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetEditableWorkListViewsResponseType() {
        return getEditableWorkListViewsResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetEditableWorkListViewsResponseType_WorkListViews() {
        return (EReference)getEditableWorkListViewsResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetEditableWorkListViewsType() {
        return getEditableWorkListViewsTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetEditableWorkListViewsType_ApiVersion() {
        return (EAttribute)getEditableWorkListViewsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetEditableWorkListViewsType_NumberOfItems() {
        return (EAttribute)getEditableWorkListViewsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetEditableWorkListViewsType_StartPosition() {
        return (EAttribute)getEditableWorkListViewsTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetOfferSetResponseType() {
        return getOfferSetResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetOfferSetResponseType_EntityGuid() {
        return (EAttribute)getOfferSetResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetOfferSetResponseType_EntityId() {
        return (EReference)getOfferSetResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetOfferSetType() {
        return getOfferSetTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetOfferSetType_WorkItemID() {
        return (EReference)getOfferSetTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetOfferSetType_ApiVersion() {
        return (EAttribute)getOfferSetTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetOrgEntityConfigAttributesAvailableResponseType() {
        return getOrgEntityConfigAttributesAvailableResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetOrgEntityConfigAttributesAvailableResponseType_Group() {
        return (EAttribute)getOrgEntityConfigAttributesAvailableResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetOrgEntityConfigAttributesAvailableResponseType_OrgEntityConfigAttributesAvailable() {
        return (EReference)getOrgEntityConfigAttributesAvailableResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetOrgEntityConfigAttributesAvailableType() {
        return getOrgEntityConfigAttributesAvailableTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetOrgEntityConfigAttributesAvailableType_StartAt() {
        return (EAttribute)getOrgEntityConfigAttributesAvailableTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetOrgEntityConfigAttributesAvailableType_NumToReturn() {
        return (EAttribute)getOrgEntityConfigAttributesAvailableTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetOrgEntityConfigAttributesResponseType() {
        return getOrgEntityConfigAttributesResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetOrgEntityConfigAttributesResponseType_Group() {
        return (EAttribute)getOrgEntityConfigAttributesResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetOrgEntityConfigAttributesResponseType_OrgEntityConfigAttribute() {
        return (EReference)getOrgEntityConfigAttributesResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetOrgEntityConfigAttributesType() {
        return getOrgEntityConfigAttributesTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetOrgEntityConfigAttributesType_Resource() {
        return (EAttribute)getOrgEntityConfigAttributesTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetPublicWorkListViewsResponseType() {
        return getPublicWorkListViewsResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetPublicWorkListViewsResponseType_WorkListViews() {
        return (EReference)getPublicWorkListViewsResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetPublicWorkListViewsType() {
        return getPublicWorkListViewsTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetPublicWorkListViewsType_ApiVersion() {
        return (EAttribute)getPublicWorkListViewsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetPublicWorkListViewsType_NumberOfItems() {
        return (EAttribute)getPublicWorkListViewsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetPublicWorkListViewsType_StartPosition() {
        return (EAttribute)getPublicWorkListViewsTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetResourceOrderFilterCriteriaResponseType() {
        return getResourceOrderFilterCriteriaResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetResourceOrderFilterCriteriaResponseType_OrderFilterCriteria() {
        return (EReference)getResourceOrderFilterCriteriaResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetResourceOrderFilterCriteriaType() {
        return getResourceOrderFilterCriteriaTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetResourceOrderFilterCriteriaType_ResourceID() {
        return (EAttribute)getResourceOrderFilterCriteriaTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetViewsForResourceResponseType() {
        return getViewsForResourceResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetViewsForResourceResponseType_WorkListViews() {
        return (EReference)getViewsForResourceResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetViewsForResourceType() {
        return getViewsForResourceTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetViewsForResourceType_ApiVersion() {
        return (EAttribute)getViewsForResourceTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetViewsForResourceType_NumberOfItems() {
        return (EAttribute)getViewsForResourceTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetViewsForResourceType_StartPosition() {
        return (EAttribute)getViewsForResourceTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkItemHeaderResponseType() {
        return getWorkItemHeaderResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkItemHeaderResponseType_Group() {
        return (EAttribute)getWorkItemHeaderResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkItemHeaderResponseType_WorkItemHeader() {
        return (EReference)getWorkItemHeaderResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkItemHeaderType() {
        return getWorkItemHeaderTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkItemHeaderType_Group() {
        return (EAttribute)getWorkItemHeaderTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkItemHeaderType_WorkItemID() {
        return (EReference)getWorkItemHeaderTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkItemOrderFilterResponseType() {
        return getWorkItemOrderFilterResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkItemOrderFilterResponseType_ColumnData() {
        return (EReference)getWorkItemOrderFilterResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkItemOrderFilterType() {
        return getWorkItemOrderFilterTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkItemOrderFilterType_LimitColumns() {
        return (EAttribute)getWorkItemOrderFilterTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkListItemsForGlobalDataResponseType() {
        return getWorkListItemsForGlobalDataResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForGlobalDataResponseType_StartPosition() {
        return (EAttribute)getWorkListItemsForGlobalDataResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForGlobalDataResponseType_EndPosition() {
        return (EAttribute)getWorkListItemsForGlobalDataResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkListItemsForGlobalDataResponseType_WorkItems() {
        return (EReference)getWorkListItemsForGlobalDataResponseTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkListItemsForGlobalDataType() {
        return getWorkListItemsForGlobalDataTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForGlobalDataType_GlobalDataRef() {
        return (EAttribute)getWorkListItemsForGlobalDataTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkListItemsForGlobalDataType_OrderFilterCriteria() {
        return (EReference)getWorkListItemsForGlobalDataTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForGlobalDataType_NumberOfItems() {
        return (EAttribute)getWorkListItemsForGlobalDataTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForGlobalDataType_StartPosition() {
        return (EAttribute)getWorkListItemsForGlobalDataTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkListItemsForViewResponseType() {
        return getWorkListItemsForViewResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewResponseType_StartPosition() {
        return (EAttribute)getWorkListItemsForViewResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewResponseType_EndPosition() {
        return (EAttribute)getWorkListItemsForViewResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewResponseType_TotalItems() {
        return (EAttribute)getWorkListItemsForViewResponseTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkListItemsForViewResponseType_WorkItems() {
        return (EReference)getWorkListItemsForViewResponseTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewResponseType_CustomData() {
        return (EAttribute)getWorkListItemsForViewResponseTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkListItemsForViewType() {
        return getWorkListItemsForViewTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewType_GetAllocatedItems() {
        return (EAttribute)getWorkListItemsForViewTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewType_GetTotalCount() {
        return (EAttribute)getWorkListItemsForViewTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewType_NumberOfItems() {
        return (EAttribute)getWorkListItemsForViewTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewType_StartPosition() {
        return (EAttribute)getWorkListItemsForViewTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsForViewType_WorkListViewID() {
        return (EAttribute)getWorkListItemsForViewTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkListItemsResponseType() {
        return getWorkListItemsResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsResponseType_StartPosition() {
        return (EAttribute)getWorkListItemsResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsResponseType_EndPosition() {
        return (EAttribute)getWorkListItemsResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsResponseType_TotalItems() {
        return (EAttribute)getWorkListItemsResponseTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkListItemsResponseType_WorkItems() {
        return (EReference)getWorkListItemsResponseTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkListItemsResponseType1() {
        return getWorkListItemsResponseType1EClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsResponseType1_StartPosition() {
        return (EAttribute)getWorkListItemsResponseType1EClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsResponseType1_EndPosition() {
        return (EAttribute)getWorkListItemsResponseType1EClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsResponseType1_TotalItems() {
        return (EAttribute)getWorkListItemsResponseType1EClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkListItemsResponseType1_WorkItems() {
        return (EReference)getWorkListItemsResponseType1EClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkListItemsType() {
        return getWorkListItemsTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsType_ResourcesRequired() {
        return (EAttribute)getWorkListItemsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkListItemsType_EntityID() {
        return (EReference)getWorkListItemsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkListItemsType_OrderFilterCriteria() {
        return (EReference)getWorkListItemsTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsType_GetTotalCount() {
        return (EAttribute)getWorkListItemsTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsType_NumberOfItems() {
        return (EAttribute)getWorkListItemsTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListItemsType_StartPosition() {
        return (EAttribute)getWorkListItemsTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkListViewDetailsType() {
        return getWorkListViewDetailsTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListViewDetailsType_ApiVersion() {
        return (EAttribute)getWorkListViewDetailsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListViewDetailsType_LockView() {
        return (EAttribute)getWorkListViewDetailsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkListViewDetailsType_WorkListViewID() {
        return (EAttribute)getWorkListViewDetailsTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkModelResponseType() {
        return getWorkModelResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkModelResponseType_WorkModel() {
        return (EReference)getWorkModelResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkModelsResponseType() {
        return getWorkModelsResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkModelsResponseType_WorkModelList() {
        return (EReference)getWorkModelsResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkModelsType() {
        return getWorkModelsTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkModelsType_StartPosition() {
        return (EAttribute)getWorkModelsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkModelsType_NumberOfItems() {
        return (EAttribute)getWorkModelsTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkModelType() {
        return getWorkModelTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkModelType_WorkModelID() {
        return (EAttribute)getWorkModelTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkTypeResponseType() {
        return getWorkTypeResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkTypeResponseType_WorkType() {
        return (EReference)getWorkTypeResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkTypesResponseType() {
        return getWorkTypesResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGetWorkTypesResponseType_WorkTypelList() {
        return (EReference)getWorkTypesResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkTypesType() {
        return getWorkTypesTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkTypesType_StartPosition() {
        return (EAttribute)getWorkTypesTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkTypesType_NumberOfItems() {
        return (EAttribute)getWorkTypesTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGetWorkTypeType() {
        return getWorkTypeTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGetWorkTypeType_WorkTypeID() {
        return (EAttribute)getWorkTypeTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHiddenPeriod() {
        return hiddenPeriodEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getHiddenPeriod_HiddenDuration() {
        return (EReference)hiddenPeriodEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHiddenPeriod_VisibleDate() {
        return (EAttribute)hiddenPeriodEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getItem() {
        return itemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItem_Entities() {
        return (EReference)itemEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItem_EntityQuery() {
        return (EReference)itemEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItem_WorkTypeUID() {
        return (EAttribute)itemEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItem_WorkTypeVersion() {
        return (EAttribute)itemEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getItemBody() {
        return itemBodyEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemBody_Parameter() {
        return (EReference)itemBodyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getItemContext() {
        return itemContextEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemContext_ActivityID() {
        return (EAttribute)itemContextEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemContext_ActivityName() {
        return (EAttribute)itemContextEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemContext_AppInstance() {
        return (EAttribute)itemContextEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemContext_AppName() {
        return (EAttribute)itemContextEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemContext_AppID() {
        return (EAttribute)itemContextEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemContext_AppInstanceDescription() {
        return (EAttribute)itemContextEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getItemDuration() {
        return itemDurationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemDuration_Days() {
        return (EAttribute)itemDurationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemDuration_Hours() {
        return (EAttribute)itemDurationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemDuration_Minutes() {
        return (EAttribute)itemDurationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemDuration_Months() {
        return (EAttribute)itemDurationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemDuration_Weeks() {
        return (EAttribute)itemDurationEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemDuration_Years() {
        return (EAttribute)itemDurationEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getItemPrivilege() {
        return itemPrivilegeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemPrivilege_Suspend() {
        return (EReference)itemPrivilegeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemPrivilege_StatelessRealloc() {
        return (EReference)itemPrivilegeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemPrivilege_StatefulRealloc() {
        return (EReference)itemPrivilegeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemPrivilege_Dellocate() {
        return (EReference)itemPrivilegeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemPrivilege_Delegate() {
        return (EReference)itemPrivilegeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemPrivilege_Skip() {
        return (EReference)itemPrivilegeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemPrivilege_Piling() {
        return (EReference)itemPrivilegeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getItemSchedule() {
        return itemScheduleEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemSchedule_StartDate() {
        return (EAttribute)itemScheduleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getItemSchedule_MaxDuration() {
        return (EReference)itemScheduleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getItemSchedule_TargetDate() {
        return (EAttribute)itemScheduleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getManagedObjectID() {
        return managedObjectIDEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getManagedObjectID_Version() {
        return (EAttribute)managedObjectIDEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getNextWorkItem() {
        return nextWorkItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getNextWorkItem_NextItem() {
        return (EReference)nextWorkItemEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getObjectID() {
        return objectIDEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getObjectID_Id() {
        return (EAttribute)objectIDEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOnNotificationResponseType() {
        return onNotificationResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOnNotificationResponseType_Success() {
        return (EAttribute)onNotificationResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOnNotificationType() {
        return onNotificationTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOnNotificationType_MessageDetails() {
        return (EReference)onNotificationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOnNotificationType_ItemBody() {
        return (EReference)onNotificationTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOnNotificationType_AllocationHistory() {
        return (EReference)onNotificationTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOpenWorkItemResponseType() {
        return openWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOpenWorkItemResponseType_Group() {
        return (EAttribute)openWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOpenWorkItemResponseType_WorkItemBody() {
        return (EReference)openWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOpenWorkItemType() {
        return openWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOpenWorkItemType_Group() {
        return (EAttribute)openWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOpenWorkItemType_WorkItemID() {
        return (EReference)openWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrderFilterCriteria() {
        return orderFilterCriteriaEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrderFilterCriteria_Order() {
        return (EAttribute)orderFilterCriteriaEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrderFilterCriteria_Filter() {
        return (EAttribute)orderFilterCriteriaEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgEntityConfigAttribute() {
        return orgEntityConfigAttributeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgEntityConfigAttribute_AttributeName() {
        return (EAttribute)orgEntityConfigAttributeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgEntityConfigAttribute_AttributeValue() {
        return (EAttribute)orgEntityConfigAttributeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgEntityConfigAttribute_ReadOnly() {
        return (EAttribute)orgEntityConfigAttributeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgEntityConfigAttributesAvailable() {
        return orgEntityConfigAttributesAvailableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgEntityConfigAttributesAvailable_AttributeName() {
        return (EAttribute)orgEntityConfigAttributesAvailableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgEntityConfigAttributesAvailable_ReadOnly() {
        return (EAttribute)orgEntityConfigAttributesAvailableEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOrgEntityConfigAttributeSet() {
        return orgEntityConfigAttributeSetEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgEntityConfigAttributeSet_AttributeName() {
        return (EAttribute)orgEntityConfigAttributeSetEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOrgEntityConfigAttributeSet_AttributeValue() {
        return (EAttribute)orgEntityConfigAttributeSetEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterType() {
        return parameterTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParameterType_ComplexValue() {
        return (EReference)parameterTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterType_Value() {
        return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterType_Array() {
        return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterType_Name() {
        return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPendWorkItem() {
        return pendWorkItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPendWorkItem_Group() {
        return (EAttribute)pendWorkItemEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPendWorkItem_WorkItemID() {
        return (EReference)pendWorkItemEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPendWorkItem_HiddenPeriod() {
        return (EReference)pendWorkItemEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPendWorkItemResponseType() {
        return pendWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPendWorkItemResponseType_Group() {
        return (EAttribute)pendWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPendWorkItemResponseType_WorkItemID() {
        return (EReference)pendWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPreviewWorkItemFromListResponseType() {
        return previewWorkItemFromListResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPreviewWorkItemFromListResponseType_Group() {
        return (EAttribute)previewWorkItemFromListResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPreviewWorkItemFromListResponseType_WorkItemPreview() {
        return (EReference)previewWorkItemFromListResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPreviewWorkItemFromListType() {
        return previewWorkItemFromListTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPreviewWorkItemFromListType_EntityID() {
        return (EReference)previewWorkItemFromListTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPreviewWorkItemFromListType_WorkItemID() {
        return (EReference)previewWorkItemFromListTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPreviewWorkItemFromListType_RequiredField() {
        return (EAttribute)previewWorkItemFromListTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPreviewWorkItemFromListType_RequireWorkType() {
        return (EAttribute)previewWorkItemFromListTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPrivilege() {
        return privilegeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPrivilege_Name() {
        return (EAttribute)privilegeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPrivilege_Qualifier() {
        return (EAttribute)privilegeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPushNotificationType() {
        return pushNotificationTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPushNotificationType_WorkItemID() {
        return (EReference)pushNotificationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPushNotificationType_WorkTypeID() {
        return (EReference)pushNotificationTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPushNotificationType_ResourceIDs() {
        return (EReference)pushNotificationTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReallocateWorkItemData() {
        return reallocateWorkItemDataEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getReallocateWorkItemData_Group() {
        return (EAttribute)reallocateWorkItemDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getReallocateWorkItemData_WorkItemID() {
        return (EReference)reallocateWorkItemDataEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getReallocateWorkItemData_Resource() {
        return (EAttribute)reallocateWorkItemDataEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getReallocateWorkItemData_WorkItemPayload() {
        return (EReference)reallocateWorkItemDataEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReallocateWorkItemDataResponseType() {
        return reallocateWorkItemDataResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getReallocateWorkItemDataResponseType_Group() {
        return (EAttribute)reallocateWorkItemDataResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getReallocateWorkItemDataResponseType_WorkItem() {
        return (EReference)reallocateWorkItemDataResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReallocateWorkItemResponseType() {
        return reallocateWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getReallocateWorkItemResponseType_Group() {
        return (EAttribute)reallocateWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getReallocateWorkItemResponseType_WorkItem() {
        return (EReference)reallocateWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getReallocateWorkItemType() {
        return reallocateWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getReallocateWorkItemType_Group() {
        return (EAttribute)reallocateWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getReallocateWorkItemType_WorkItemID() {
        return (EReference)reallocateWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getReallocateWorkItemType_Resource() {
        return (EAttribute)reallocateWorkItemTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getReallocateWorkItemType_RevertData() {
        return (EAttribute)reallocateWorkItemTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRescheduleWorkItem() {
        return rescheduleWorkItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getRescheduleWorkItem_WorkItemID() {
        return (EReference)rescheduleWorkItemEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getRescheduleWorkItem_ItemSchedule() {
        return (EReference)rescheduleWorkItemEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getRescheduleWorkItem_ItemBody() {
        return (EReference)rescheduleWorkItemEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRescheduleWorkItemResponseType() {
        return rescheduleWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRescheduleWorkItemResponseType_Success() {
        return (EAttribute)rescheduleWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResumeWorkItemResponseType() {
        return resumeWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getResumeWorkItemResponseType_Success() {
        return (EAttribute)resumeWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getResumeWorkItemType() {
        return resumeWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getResumeWorkItemType_WorkItemID() {
        return (EReference)resumeWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSaveOpenWorkItemResponseType() {
        return saveOpenWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSaveOpenWorkItemResponseType_WorkItemID() {
        return (EReference)saveOpenWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSaveOpenWorkItemType() {
        return saveOpenWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSaveOpenWorkItemType_WorkItemID() {
        return (EReference)saveOpenWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSaveOpenWorkItemType_WorkItemPayload() {
        return (EReference)saveOpenWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getScheduleWorkItemResponseType() {
        return scheduleWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemResponseType_WorkItemID() {
        return (EReference)scheduleWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getScheduleWorkItemType() {
        return scheduleWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemType_Item() {
        return (EReference)scheduleWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemType_ItemSchedule() {
        return (EReference)scheduleWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemType_ItemContext() {
        return (EReference)scheduleWorkItemTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemType_ItemBody() {
        return (EReference)scheduleWorkItemTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getScheduleWorkItemWithModelResponseType() {
        return scheduleWorkItemWithModelResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemWithModelResponseType_WorkItemID() {
        return (EReference)scheduleWorkItemWithModelResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getScheduleWorkItemWithModelType() {
        return scheduleWorkItemWithModelTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemWithModelType_ItemSchedule() {
        return (EReference)scheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemWithModelType_ItemContext() {
        return (EReference)scheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemWithModelType_ItemBody() {
        return (EReference)scheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScheduleWorkItemWithModelType_EntityQuery() {
        return (EReference)scheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScheduleWorkItemWithModelType_GroupID() {
        return (EAttribute)scheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScheduleWorkItemWithModelType_WorkModelUID() {
        return (EAttribute)scheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScheduleWorkItemWithModelType_WorkModelVersion() {
        return (EAttribute)scheduleWorkItemWithModelTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSetOrgEntityConfigAttributesResponseType() {
        return setOrgEntityConfigAttributesResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSetOrgEntityConfigAttributesResponseType_Success() {
        return (EAttribute)setOrgEntityConfigAttributesResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSetOrgEntityConfigAttributesType() {
        return setOrgEntityConfigAttributesTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSetOrgEntityConfigAttributesType_Group() {
        return (EAttribute)setOrgEntityConfigAttributesTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSetOrgEntityConfigAttributesType_OrgEntityConfigAttributeSet() {
        return (EReference)setOrgEntityConfigAttributesTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSetOrgEntityConfigAttributesType_Resource() {
        return (EAttribute)setOrgEntityConfigAttributesTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSetResourceOrderFilterCriteriaResponseType() {
        return setResourceOrderFilterCriteriaResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSetResourceOrderFilterCriteriaResponseType_Success() {
        return (EAttribute)setResourceOrderFilterCriteriaResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSetResourceOrderFilterCriteriaType() {
        return setResourceOrderFilterCriteriaTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSetResourceOrderFilterCriteriaType_OrderFilterCriteria() {
        return (EReference)setResourceOrderFilterCriteriaTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSetResourceOrderFilterCriteriaType_ResourceID() {
        return (EAttribute)setResourceOrderFilterCriteriaTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSetWorkItemPriority() {
        return setWorkItemPriorityEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSetWorkItemPriority_WorkItemIDandPriority() {
        return (EReference)setWorkItemPriorityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSetWorkItemPriorityResponseType() {
        return setWorkItemPriorityResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSetWorkItemPriorityResponseType_Group() {
        return (EAttribute)setWorkItemPriorityResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSetWorkItemPriorityResponseType_WorkItemID() {
        return (EReference)setWorkItemPriorityResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSkipWorkItemResponseType() {
        return skipWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSkipWorkItemResponseType_Success() {
        return (EAttribute)skipWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSkipWorkItemType() {
        return skipWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSkipWorkItemType_Group() {
        return (EAttribute)skipWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSkipWorkItemType_WorkItemID() {
        return (EReference)skipWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getStartGroupResponseType() {
        return startGroupResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStartGroupResponseType_GroupID() {
        return (EAttribute)startGroupResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getStartGroupType() {
        return startGroupTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStartGroupType_GroupType() {
        return (EAttribute)startGroupTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStartGroupType_Description() {
        return (EAttribute)startGroupTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSuspendWorkItemResponseType() {
        return suspendWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSuspendWorkItemResponseType_Success() {
        return (EAttribute)suspendWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSuspendWorkItemType() {
        return suspendWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSuspendWorkItemType_WorkItemID() {
        return (EReference)suspendWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSuspendWorkItemType_ForceSuspend() {
        return (EAttribute)suspendWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUnallocateWorkItemResponseType() {
        return unallocateWorkItemResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUnallocateWorkItemResponseType_Group() {
        return (EAttribute)unallocateWorkItemResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getUnallocateWorkItemResponseType_WorkItem() {
        return (EReference)unallocateWorkItemResponseTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUnallocateWorkItemType() {
        return unallocateWorkItemTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUnallocateWorkItemType_Group() {
        return (EAttribute)unallocateWorkItemTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getUnallocateWorkItemType_WorkItemID() {
        return (EReference)unallocateWorkItemTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUnlockWorkListViewResponseType() {
        return unlockWorkListViewResponseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUnlockWorkListViewResponseType_WorkListViewID() {
        return (EAttribute)unlockWorkListViewResponseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUnlockWorkListViewType() {
        return unlockWorkListViewTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUnlockWorkListViewType_WorkListViewID() {
        return (EAttribute)unlockWorkListViewTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkItem() {
        return workItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItem_Id() {
        return (EReference)workItemEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItem_Header() {
        return (EReference)workItemEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItem_Attributes() {
        return (EReference)workItemEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItem_Body() {
        return (EReference)workItemEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItem_WorkType() {
        return (EReference)workItemEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItem_State() {
        return (EAttribute)workItemEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItem_Visible() {
        return (EAttribute)workItemEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkItemAttributes() {
        return workItemAttributesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute1() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute10() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute11() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute12() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute13() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute14() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute15() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute16() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute17() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute18() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute19() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute2() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute20() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute21() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute22() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute23() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(15);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute24() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(16);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute25() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(17);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute26() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(18);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute27() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(19);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute28() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(20);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute29() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(21);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute3() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(22);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute30() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(23);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute31() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(24);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute32() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(25);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute33() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(26);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute34() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(27);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute35() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(28);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute36() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(29);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute37() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(30);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute38() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(31);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute39() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(32);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute4() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(33);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute40() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(34);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute5() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(35);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute6() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(36);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute7() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(37);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute8() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(38);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemAttributes_Attribute9() {
        return (EAttribute)workItemAttributesEClass.getEStructuralFeatures().get(39);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkItemBody() {
        return workItemBodyEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItemBody_DataModel() {
        return (EReference)workItemBodyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkItemFlags() {
        return workItemFlagsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemFlags_ScheduleStatus() {
        return (EAttribute)workItemFlagsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkItemHeader() {
        return workItemHeaderEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItemHeader_Flags() {
        return (EReference)workItemHeaderEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItemHeader_ItemContext() {
        return (EReference)workItemHeaderEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemHeader_EndDate() {
        return (EAttribute)workItemHeaderEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemHeader_StartDate() {
        return (EAttribute)workItemHeaderEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkItemIDandPriorityType() {
        return workItemIDandPriorityTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItemIDandPriorityType_WorkItemID() {
        return (EReference)workItemIDandPriorityTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItemIDandPriorityType_WorkItemPriority() {
        return (EReference)workItemIDandPriorityTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkItemPreview() {
        return workItemPreviewEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItemPreview_FieldPreview() {
        return (EReference)workItemPreviewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkItemPreview_WorkType() {
        return (EReference)workItemPreviewEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkItemPriorityType() {
        return workItemPriorityTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemPriorityType_AbsPriority() {
        return (EAttribute)workItemPriorityTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkItemPriorityType_OffsetPriority() {
        return (EAttribute)workItemPriorityTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkListView() {
        return workListViewEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListView_CreationDate() {
        return (EAttribute)workListViewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListView_Locker() {
        return (EAttribute)workListViewEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListView_ModificationDate() {
        return (EAttribute)workListViewEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListView_WorkViewID() {
        return (EAttribute)workListViewEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkListViewCommon() {
        return workListViewCommonEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkListViewCommon_EntityID() {
        return (EReference)workListViewCommonEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewCommon_ResourcesRequired() {
        return (EAttribute)workListViewCommonEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkListViewCommon_OrderFilterCriteria() {
        return (EReference)workListViewCommonEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewCommon_Description() {
        return (EAttribute)workListViewCommonEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewCommon_GetAllocatedItems() {
        return (EAttribute)workListViewCommonEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewCommon_Name() {
        return (EAttribute)workListViewCommonEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewCommon_Owner() {
        return (EAttribute)workListViewCommonEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewCommon_Public() {
        return (EAttribute)workListViewCommonEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkListViewEdit() {
        return workListViewEditEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkListViewEdit_Authors() {
        return (EReference)workListViewEditEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkListViewEdit_Users() {
        return (EReference)workListViewEditEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewEdit_CustomData() {
        return (EAttribute)workListViewEditEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkListViewPageItem() {
        return workListViewPageItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewPageItem_CreationDate() {
        return (EAttribute)workListViewPageItemEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewPageItem_ModificationDate() {
        return (EAttribute)workListViewPageItemEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkListViewPageItem_WorkViewID() {
        return (EAttribute)workListViewPageItemEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModel() {
        return workModelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModel_BaseModelInfo() {
        return (EReference)workModelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModel_WorkModelSpecification() {
        return (EReference)workModelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModel_WorkModelEntities() {
        return (EReference)workModelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModel_WorkModelTypes() {
        return (EReference)workModelEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModel_ItemPrivileges() {
        return (EReference)workModelEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModel_WorkModelScripts() {
        return (EReference)workModelEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModel_AttributeAliasList() {
        return (EReference)workModelEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModel_WorkModelUID() {
        return (EAttribute)workModelEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelEntities() {
        return workModelEntitiesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModelEntities_WorkModelEntity() {
        return (EReference)workModelEntitiesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelEntities_Expression() {
        return (EAttribute)workModelEntitiesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelEntity() {
        return workModelEntityEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModelEntity_EntityQuery() {
        return (EReference)workModelEntityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModelEntity_Entities() {
        return (EReference)workModelEntityEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelEntity_DistributionStrategy() {
        return (EAttribute)workModelEntityEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelList() {
        return workModelListEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelList_StartPosition() {
        return (EAttribute)workModelListEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelList_EndPosition() {
        return (EAttribute)workModelListEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModelList_Type() {
        return (EReference)workModelListEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelMapping() {
        return workModelMappingEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelMapping_TypeParamName() {
        return (EAttribute)workModelMappingEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelMapping_ModelParamName() {
        return (EAttribute)workModelMappingEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelMapping_DefaultValue() {
        return (EAttribute)workModelMappingEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelScript() {
        return workModelScriptEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelScript_ScriptBody() {
        return (EAttribute)workModelScriptEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelScript_ScriptLanguage() {
        return (EAttribute)workModelScriptEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelScript_ScriptLanguageExtension() {
        return (EAttribute)workModelScriptEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelScript_ScriptLanguageVersion() {
        return (EAttribute)workModelScriptEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelScript_ScriptOperation() {
        return (EAttribute)workModelScriptEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelScript_ScriptTypeID() {
        return (EAttribute)workModelScriptEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelScripts() {
        return workModelScriptsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModelScripts_WorkModelScript() {
        return (EReference)workModelScriptsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelSpecification() {
        return workModelSpecificationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelType() {
        return workModelTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModelType_WorkModelMapping() {
        return (EReference)workModelTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelType_Version() {
        return (EAttribute)workModelTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelType_WorkTypeID() {
        return (EAttribute)workModelTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModelTypes() {
        return workModelTypesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModelTypes_WorkModelType() {
        return (EReference)workModelTypesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModelTypes_Expression() {
        return (EAttribute)workModelTypesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkTypeList() {
        return workTypeListEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkTypeList_StartPosition() {
        return (EAttribute)workTypeListEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkTypeList_EndPosition() {
        return (EAttribute)workTypeListEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkTypeList_Types() {
        return (EReference)workTypeListEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getColumnCapability() {
        return columnCapabilityEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getColumnType() {
        return columnTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDistributionStrategy() {
        return distributionStrategyEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getMethodAuthorisationAction() {
        return methodAuthorisationActionEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getMethodAuthorisationComponent() {
        return methodAuthorisationComponentEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getResourcesRequiredType() {
        return resourcesRequiredTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getScheduleStatus() {
        return scheduleStatusEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getWorkGroupType() {
        return workGroupTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getWorkItemScriptOperation() {
        return workItemScriptOperationEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getWorkItemScriptType() {
        return workItemScriptTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getWorkItemState() {
        return workItemStateEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute10Type() {
        return attribute10TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute11Type() {
        return attribute11TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute12Type() {
        return attribute12TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute13Type() {
        return attribute13TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute14Type() {
        return attribute14TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute21Type() {
        return attribute21TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute22Type() {
        return attribute22TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute23Type() {
        return attribute23TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute24Type() {
        return attribute24TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute25Type() {
        return attribute25TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute26Type() {
        return attribute26TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute27Type() {
        return attribute27TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute28Type() {
        return attribute28TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute29Type() {
        return attribute29TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute2Type() {
        return attribute2TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute30Type() {
        return attribute30TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute31Type() {
        return attribute31TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute32Type() {
        return attribute32TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute33Type() {
        return attribute33TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute34Type() {
        return attribute34TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute35Type() {
        return attribute35TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute36Type() {
        return attribute36TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute37Type() {
        return attribute37TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute38Type() {
        return attribute38TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute39Type() {
        return attribute39TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute3Type() {
        return attribute3TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute40Type() {
        return attribute40TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute4Type() {
        return attribute4TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute8Type() {
        return attribute8TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAttribute9Type() {
        return attribute9TypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getColumnCapabilityObject() {
        return columnCapabilityObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getColumnTypeObject() {
        return columnTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getDescriptionType() {
        return descriptionTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getDistributionStrategyObject() {
        return distributionStrategyObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getLockerType() {
        return lockerTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getMethodAuthorisationActionObject() {
        return methodAuthorisationActionObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getMethodAuthorisationComponentObject() {
        return methodAuthorisationComponentObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getNameType() {
        return nameTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getOwnerType() {
        return ownerTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getResourcesRequiredTypeObject() {
        return resourcesRequiredTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getScheduleStatusObject() {
        return scheduleStatusObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getScriptOperationType() {
        return scriptOperationTypeEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getWorkGroupTypeObject() {
        return workGroupTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getWorkItemScriptOperationObject() {
        return workItemScriptOperationObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getWorkItemScriptTypeObject() {
        return workItemScriptTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getWorkItemStateObject() {
        return workItemStateObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public N2BRMFactory getN2BRMFactory() {
        return (N2BRMFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        addCurrentResourceToViewResponseTypeEClass = createEClass(ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE);
        createEAttribute(addCurrentResourceToViewResponseTypeEClass, ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID);

        addCurrentResourceToViewTypeEClass = createEClass(ADD_CURRENT_RESOURCE_TO_VIEW_TYPE);
        createEAttribute(addCurrentResourceToViewTypeEClass, ADD_CURRENT_RESOURCE_TO_VIEW_TYPE__WORK_LIST_VIEW_ID);

        allocateAndOpenNextWorkItemResponseTypeEClass = createEClass(ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE);
        createEReference(allocateAndOpenNextWorkItemResponseTypeEClass, ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM);

        allocateAndOpenNextWorkItemTypeEClass = createEClass(ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE);
        createEAttribute(allocateAndOpenNextWorkItemTypeEClass, ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE__RESOURCE);
        createEAttribute(allocateAndOpenNextWorkItemTypeEClass, ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_TYPE__WORK_LIST_VIEW_ID);

        allocateAndOpenWorkItemResponseTypeEClass = createEClass(ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(allocateAndOpenWorkItemResponseTypeEClass, ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE__GROUP);
        createEReference(allocateAndOpenWorkItemResponseTypeEClass, ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM);

        allocateAndOpenWorkItemTypeEClass = createEClass(ALLOCATE_AND_OPEN_WORK_ITEM_TYPE);
        createEAttribute(allocateAndOpenWorkItemTypeEClass, ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__GROUP);
        createEReference(allocateAndOpenWorkItemTypeEClass, ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID);
        createEAttribute(allocateAndOpenWorkItemTypeEClass, ALLOCATE_AND_OPEN_WORK_ITEM_TYPE__RESOURCE);

        allocateWorkItemResponseTypeEClass = createEClass(ALLOCATE_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(allocateWorkItemResponseTypeEClass, ALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP);
        createEReference(allocateWorkItemResponseTypeEClass, ALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM);

        allocateWorkItemTypeEClass = createEClass(ALLOCATE_WORK_ITEM_TYPE);
        createEAttribute(allocateWorkItemTypeEClass, ALLOCATE_WORK_ITEM_TYPE__GROUP);
        createEReference(allocateWorkItemTypeEClass, ALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID);
        createEAttribute(allocateWorkItemTypeEClass, ALLOCATE_WORK_ITEM_TYPE__RESOURCE);

        allocationHistoryEClass = createEClass(ALLOCATION_HISTORY);
        createEAttribute(allocationHistoryEClass, ALLOCATION_HISTORY__RESOURCE_ID);
        createEAttribute(allocationHistoryEClass, ALLOCATION_HISTORY__ALLOCATION_DATE);
        createEAttribute(allocationHistoryEClass, ALLOCATION_HISTORY__ALLOCATION_ID);

        asyncCancelWorkItemResponseTypeEClass = createEClass(ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE);
        createEReference(asyncCancelWorkItemResponseTypeEClass, ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS);
        createEReference(asyncCancelWorkItemResponseTypeEClass, ASYNC_CANCEL_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE);

        asyncCancelWorkItemTypeEClass = createEClass(ASYNC_CANCEL_WORK_ITEM_TYPE);
        createEReference(asyncCancelWorkItemTypeEClass, ASYNC_CANCEL_WORK_ITEM_TYPE__MESSAGE_DETAILS);

        asyncEndGroupResponseTypeEClass = createEClass(ASYNC_END_GROUP_RESPONSE_TYPE);
        createEAttribute(asyncEndGroupResponseTypeEClass, ASYNC_END_GROUP_RESPONSE_TYPE__ACTIVITY_ID);
        createEAttribute(asyncEndGroupResponseTypeEClass, ASYNC_END_GROUP_RESPONSE_TYPE__GROUP_ID);
        createEReference(asyncEndGroupResponseTypeEClass, ASYNC_END_GROUP_RESPONSE_TYPE__ERROR_MESSAGE);

        asyncEndGroupTypeEClass = createEClass(ASYNC_END_GROUP_TYPE);
        createEAttribute(asyncEndGroupTypeEClass, ASYNC_END_GROUP_TYPE__ACTIVITY_ID);
        createEReference(asyncEndGroupTypeEClass, ASYNC_END_GROUP_TYPE__END_GROUP);

        asyncRescheduleWorkItemResponseTypeEClass = createEClass(ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE);
        createEReference(asyncRescheduleWorkItemResponseTypeEClass, ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS);
        createEReference(asyncRescheduleWorkItemResponseTypeEClass, ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE);

        asyncRescheduleWorkItemTypeEClass = createEClass(ASYNC_RESCHEDULE_WORK_ITEM_TYPE);
        createEReference(asyncRescheduleWorkItemTypeEClass, ASYNC_RESCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE);
        createEReference(asyncRescheduleWorkItemTypeEClass, ASYNC_RESCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS);
        createEReference(asyncRescheduleWorkItemTypeEClass, ASYNC_RESCHEDULE_WORK_ITEM_TYPE__ITEM_BODY);

        asyncResumeWorkItemResponseTypeEClass = createEClass(ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE);
        createEReference(asyncResumeWorkItemResponseTypeEClass, ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS);
        createEReference(asyncResumeWorkItemResponseTypeEClass, ASYNC_RESUME_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE);

        asyncResumeWorkItemTypeEClass = createEClass(ASYNC_RESUME_WORK_ITEM_TYPE);
        createEReference(asyncResumeWorkItemTypeEClass, ASYNC_RESUME_WORK_ITEM_TYPE__MESSAGE_DETAILS);

        asyncScheduleWorkItemResponseTypeEClass = createEClass(ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE);
        createEReference(asyncScheduleWorkItemResponseTypeEClass, ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS);
        createEReference(asyncScheduleWorkItemResponseTypeEClass, ASYNC_SCHEDULE_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE);

        asyncScheduleWorkItemTypeEClass = createEClass(ASYNC_SCHEDULE_WORK_ITEM_TYPE);
        createEReference(asyncScheduleWorkItemTypeEClass, ASYNC_SCHEDULE_WORK_ITEM_TYPE__SCHEDULE_WORK_ITEM);
        createEReference(asyncScheduleWorkItemTypeEClass, ASYNC_SCHEDULE_WORK_ITEM_TYPE__MESSAGE_DETAILS);

        asyncScheduleWorkItemWithModelResponseTypeEClass = createEClass(ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE);
        createEReference(asyncScheduleWorkItemWithModelResponseTypeEClass, ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__MESSAGE_DETAILS);
        createEReference(asyncScheduleWorkItemWithModelResponseTypeEClass, ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__ERROR_MESSAGE);

        asyncScheduleWorkItemWithModelTypeEClass = createEClass(ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE);
        createEReference(asyncScheduleWorkItemWithModelTypeEClass, ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__SCHEDULE_WORK_ITEM_WITH_MODEL);
        createEReference(asyncScheduleWorkItemWithModelTypeEClass, ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__MESSAGE_DETAILS);

        asyncStartGroupResponseTypeEClass = createEClass(ASYNC_START_GROUP_RESPONSE_TYPE);
        createEAttribute(asyncStartGroupResponseTypeEClass, ASYNC_START_GROUP_RESPONSE_TYPE__ACTIVITY_ID);
        createEAttribute(asyncStartGroupResponseTypeEClass, ASYNC_START_GROUP_RESPONSE_TYPE__GROUP_ID);
        createEReference(asyncStartGroupResponseTypeEClass, ASYNC_START_GROUP_RESPONSE_TYPE__ERROR_MESSAGE);

        asyncStartGroupTypeEClass = createEClass(ASYNC_START_GROUP_TYPE);
        createEAttribute(asyncStartGroupTypeEClass, ASYNC_START_GROUP_TYPE__ACTIVITY_ID);
        createEAttribute(asyncStartGroupTypeEClass, ASYNC_START_GROUP_TYPE__GROUP_ID);
        createEReference(asyncStartGroupTypeEClass, ASYNC_START_GROUP_TYPE__START_GROUP);

        asyncSuspendWorkItemResponseTypeEClass = createEClass(ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE);
        createEReference(asyncSuspendWorkItemResponseTypeEClass, ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE__MESSAGE_DETAILS);
        createEReference(asyncSuspendWorkItemResponseTypeEClass, ASYNC_SUSPEND_WORK_ITEM_RESPONSE_TYPE__ERROR_MESSAGE);

        asyncSuspendWorkItemTypeEClass = createEClass(ASYNC_SUSPEND_WORK_ITEM_TYPE);
        createEReference(asyncSuspendWorkItemTypeEClass, ASYNC_SUSPEND_WORK_ITEM_TYPE__MESSAGE_DETAILS);
        createEAttribute(asyncSuspendWorkItemTypeEClass, ASYNC_SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND);

        asyncWorkItemDetailsEClass = createEClass(ASYNC_WORK_ITEM_DETAILS);
        createEReference(asyncWorkItemDetailsEClass, ASYNC_WORK_ITEM_DETAILS__WORK_ITEM_ID);
        createEAttribute(asyncWorkItemDetailsEClass, ASYNC_WORK_ITEM_DETAILS__ACTIVITY_ID);
        createEAttribute(asyncWorkItemDetailsEClass, ASYNC_WORK_ITEM_DETAILS__UA_SEQUENCE_ID);
        createEAttribute(asyncWorkItemDetailsEClass, ASYNC_WORK_ITEM_DETAILS__BRM_SEQUENCE_ID);

        attributeAliasListTypeEClass = createEClass(ATTRIBUTE_ALIAS_LIST_TYPE);
        createEReference(attributeAliasListTypeEClass, ATTRIBUTE_ALIAS_LIST_TYPE__ATTRIBUTE_ALIAS);

        baseItemInfoEClass = createEClass(BASE_ITEM_INFO);
        createEAttribute(baseItemInfoEClass, BASE_ITEM_INFO__NAME);
        createEAttribute(baseItemInfoEClass, BASE_ITEM_INFO__DESCRIPTION);
        createEAttribute(baseItemInfoEClass, BASE_ITEM_INFO__DISTRIBUTION_STRATEGY);
        createEAttribute(baseItemInfoEClass, BASE_ITEM_INFO__GROUP_ID);
        createEAttribute(baseItemInfoEClass, BASE_ITEM_INFO__PRIORITY);

        baseModelInfoEClass = createEClass(BASE_MODEL_INFO);
        createEAttribute(baseModelInfoEClass, BASE_MODEL_INFO__DESCRIPTION);
        createEAttribute(baseModelInfoEClass, BASE_MODEL_INFO__NAME);
        createEAttribute(baseModelInfoEClass, BASE_MODEL_INFO__PRIORITY);

        cancelWorkItemResponseTypeEClass = createEClass(CANCEL_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(cancelWorkItemResponseTypeEClass, CANCEL_WORK_ITEM_RESPONSE_TYPE__SUCCESS);

        cancelWorkItemTypeEClass = createEClass(CANCEL_WORK_ITEM_TYPE);
        createEReference(cancelWorkItemTypeEClass, CANCEL_WORK_ITEM_TYPE__WORK_ITEM_ID);

        chainedWorkItemNotificationTypeEClass = createEClass(CHAINED_WORK_ITEM_NOTIFICATION_TYPE);
        createEAttribute(chainedWorkItemNotificationTypeEClass, CHAINED_WORK_ITEM_NOTIFICATION_TYPE__GROUP_ID);
        createEReference(chainedWorkItemNotificationTypeEClass, CHAINED_WORK_ITEM_NOTIFICATION_TYPE__WORK_ITEM_ID);

        closeWorkItemResponseTypeEClass = createEClass(CLOSE_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(closeWorkItemResponseTypeEClass, CLOSE_WORK_ITEM_RESPONSE_TYPE__GROUP);
        createEReference(closeWorkItemResponseTypeEClass, CLOSE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID);

        closeWorkItemTypeEClass = createEClass(CLOSE_WORK_ITEM_TYPE);
        createEAttribute(closeWorkItemTypeEClass, CLOSE_WORK_ITEM_TYPE__GROUP);
        createEReference(closeWorkItemTypeEClass, CLOSE_WORK_ITEM_TYPE__WORK_ITEM_ID);
        createEReference(closeWorkItemTypeEClass, CLOSE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD);
        createEReference(closeWorkItemTypeEClass, CLOSE_WORK_ITEM_TYPE__HIDDEN_PERIOD);

        columnDefinitionEClass = createEClass(COLUMN_DEFINITION);
        createEAttribute(columnDefinitionEClass, COLUMN_DEFINITION__CAPABILITY);
        createEAttribute(columnDefinitionEClass, COLUMN_DEFINITION__DESCRIPTION);
        createEAttribute(columnDefinitionEClass, COLUMN_DEFINITION__ID);
        createEAttribute(columnDefinitionEClass, COLUMN_DEFINITION__NAME);
        createEAttribute(columnDefinitionEClass, COLUMN_DEFINITION__TYPE);

        columnOrderEClass = createEClass(COLUMN_ORDER);
        createEReference(columnOrderEClass, COLUMN_ORDER__COLUMN_DEF);
        createEAttribute(columnOrderEClass, COLUMN_ORDER__ASCENDING);

        completeWorkItemResponseTypeEClass = createEClass(COMPLETE_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(completeWorkItemResponseTypeEClass, COMPLETE_WORK_ITEM_RESPONSE_TYPE__GROUP_ID);
        createEReference(completeWorkItemResponseTypeEClass, COMPLETE_WORK_ITEM_RESPONSE_TYPE__NEXT_WORK_ITEM);

        completeWorkItemTypeEClass = createEClass(COMPLETE_WORK_ITEM_TYPE);
        createEReference(completeWorkItemTypeEClass, COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_ID);
        createEReference(completeWorkItemTypeEClass, COMPLETE_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD);
        createEAttribute(completeWorkItemTypeEClass, COMPLETE_WORK_ITEM_TYPE__GET_NEXT_PILED_ITEM);

        createWorkListViewResponseTypeEClass = createEClass(CREATE_WORK_LIST_VIEW_RESPONSE_TYPE);
        createEAttribute(createWorkListViewResponseTypeEClass, CREATE_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID);

        deleteCurrentResourceFromViewResponseTypeEClass = createEClass(DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE);
        createEAttribute(deleteCurrentResourceFromViewResponseTypeEClass, DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID);

        deleteCurrentResourceFromViewTypeEClass = createEClass(DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE);
        createEAttribute(deleteCurrentResourceFromViewTypeEClass, DELETE_CURRENT_RESOURCE_FROM_VIEW_TYPE__WORK_LIST_VIEW_ID);

        deleteOrgEntityConfigAttributesResponseTypeEClass = createEClass(DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE);
        createEAttribute(deleteOrgEntityConfigAttributesResponseTypeEClass, DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS);

        deleteOrgEntityConfigAttributesTypeEClass = createEClass(DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE);
        createEAttribute(deleteOrgEntityConfigAttributesTypeEClass, DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP);
        createEAttribute(deleteOrgEntityConfigAttributesTypeEClass, DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ATTRIBUTE_NAME);
        createEAttribute(deleteOrgEntityConfigAttributesTypeEClass, DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE);

        deleteWorkListViewResponseTypeEClass = createEClass(DELETE_WORK_LIST_VIEW_RESPONSE_TYPE);
        createEAttribute(deleteWorkListViewResponseTypeEClass, DELETE_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID);

        deleteWorkListViewTypeEClass = createEClass(DELETE_WORK_LIST_VIEW_TYPE);
        createEAttribute(deleteWorkListViewTypeEClass, DELETE_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ADD_CURRENT_RESOURCE_TO_VIEW_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ALLOCATE_AND_OPEN_NEXT_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ALLOCATE_AND_OPEN_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ALLOCATE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ALLOCATE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_CANCEL_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_END_GROUP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_END_GROUP_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_RESCHEDULE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_RESUME_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_START_GROUP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_START_GROUP_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ASYNC_SUSPEND_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CANCEL_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CANCEL_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CHAINED_WORK_ITEM_NOTIFICATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CLOSE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CLOSE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__COMPLETE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__COMPLETE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CREATE_WORK_LIST_VIEW_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DELETE_CURRENT_RESOURCE_FROM_VIEW_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DELETE_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DELETE_WORK_LIST_VIEW_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW);
        createEReference(documentRootEClass, DOCUMENT_ROOT__EDIT_WORK_LIST_VIEW_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ENABLE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ENABLE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__END_GROUP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__END_GROUP_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_ALLOCATED_WORK_LIST_ITEMS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_BATCH_GROUP_IDS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_BATCH_GROUP_IDS_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_BATCH_WORK_ITEM_IDS_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_OFFER_SET);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_OFFER_SET_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_VIEWS_FOR_RESOURCE_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_ITEM_HEADER);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_ITEM_HEADER_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_ITEM_ORDER_FILTER_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_LIST_ITEMS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_LIST_ITEMS_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_LIST_VIEW_DETAILS_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_MODEL);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_MODEL_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_MODELS);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_MODELS_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_TYPE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_TYPE_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_TYPES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__GET_WORK_TYPES_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ON_NOTIFICATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__ON_NOTIFICATION_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__OPEN_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__OPEN_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PEND_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PEND_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PUSH_NOTIFICATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__REALLOCATE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA);
        createEReference(documentRootEClass, DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_DATA_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__REALLOCATE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__RESCHEDULE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__RESUME_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__RESUME_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SAVE_OPEN_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SCHEDULE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SET_WORK_ITEM_PRIORITY_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SKIP_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SKIP_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__START_GROUP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__START_GROUP_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SUSPEND_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SUSPEND_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM);
        createEReference(documentRootEClass, DOCUMENT_ROOT__UNALLOCATE_WORK_ITEM_RESPONSE);
        createEReference(documentRootEClass, DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW);
        createEReference(documentRootEClass, DOCUMENT_ROOT__UNLOCK_WORK_LIST_VIEW_RESPONSE);

        editWorkListViewResponseTypeEClass = createEClass(EDIT_WORK_LIST_VIEW_RESPONSE_TYPE);
        createEAttribute(editWorkListViewResponseTypeEClass, EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID);

        editWorkListViewTypeEClass = createEClass(EDIT_WORK_LIST_VIEW_TYPE);
        createEAttribute(editWorkListViewTypeEClass, EDIT_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID);

        enableWorkItemResponseTypeEClass = createEClass(ENABLE_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(enableWorkItemResponseTypeEClass, ENABLE_WORK_ITEM_RESPONSE_TYPE__SUCCESS);

        enableWorkItemTypeEClass = createEClass(ENABLE_WORK_ITEM_TYPE);
        createEReference(enableWorkItemTypeEClass, ENABLE_WORK_ITEM_TYPE__WORK_ITEM_ID);
        createEReference(enableWorkItemTypeEClass, ENABLE_WORK_ITEM_TYPE__ITEM_BODY);

        endGroupResponseTypeEClass = createEClass(END_GROUP_RESPONSE_TYPE);
        createEAttribute(endGroupResponseTypeEClass, END_GROUP_RESPONSE_TYPE__SUCCESS);

        endGroupTypeEClass = createEClass(END_GROUP_TYPE);
        createEAttribute(endGroupTypeEClass, END_GROUP_TYPE__GROUP_ID);

        getAllocatedWorkListItemsTypeEClass = createEClass(GET_ALLOCATED_WORK_LIST_ITEMS_TYPE);
        createEReference(getAllocatedWorkListItemsTypeEClass, GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__ENTITY_ID);
        createEReference(getAllocatedWorkListItemsTypeEClass, GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__ORDER_FILTER_CRITERIA);
        createEAttribute(getAllocatedWorkListItemsTypeEClass, GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__GET_TOTAL_COUNT);
        createEAttribute(getAllocatedWorkListItemsTypeEClass, GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__NUMBER_OF_ITEMS);
        createEAttribute(getAllocatedWorkListItemsTypeEClass, GET_ALLOCATED_WORK_LIST_ITEMS_TYPE__START_POSITION);

        getBatchGroupIdsResponseTypeEClass = createEClass(GET_BATCH_GROUP_IDS_RESPONSE_TYPE);
        createEAttribute(getBatchGroupIdsResponseTypeEClass, GET_BATCH_GROUP_IDS_RESPONSE_TYPE__GROUP);
        createEAttribute(getBatchGroupIdsResponseTypeEClass, GET_BATCH_GROUP_IDS_RESPONSE_TYPE__GROUP_ID);

        getBatchWorkItemIdsResponseTypeEClass = createEClass(GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE);
        createEAttribute(getBatchWorkItemIdsResponseTypeEClass, GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE__GROUP);
        createEReference(getBatchWorkItemIdsResponseTypeEClass, GET_BATCH_WORK_ITEM_IDS_RESPONSE_TYPE__WORK_ITEM_ID);

        getEditableWorkListViewsResponseTypeEClass = createEClass(GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE);
        createEReference(getEditableWorkListViewsResponseTypeEClass, GET_EDITABLE_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS);

        getEditableWorkListViewsTypeEClass = createEClass(GET_EDITABLE_WORK_LIST_VIEWS_TYPE);
        createEAttribute(getEditableWorkListViewsTypeEClass, GET_EDITABLE_WORK_LIST_VIEWS_TYPE__API_VERSION);
        createEAttribute(getEditableWorkListViewsTypeEClass, GET_EDITABLE_WORK_LIST_VIEWS_TYPE__NUMBER_OF_ITEMS);
        createEAttribute(getEditableWorkListViewsTypeEClass, GET_EDITABLE_WORK_LIST_VIEWS_TYPE__START_POSITION);

        getOfferSetResponseTypeEClass = createEClass(GET_OFFER_SET_RESPONSE_TYPE);
        createEAttribute(getOfferSetResponseTypeEClass, GET_OFFER_SET_RESPONSE_TYPE__ENTITY_GUID);
        createEReference(getOfferSetResponseTypeEClass, GET_OFFER_SET_RESPONSE_TYPE__ENTITY_ID);

        getOfferSetTypeEClass = createEClass(GET_OFFER_SET_TYPE);
        createEReference(getOfferSetTypeEClass, GET_OFFER_SET_TYPE__WORK_ITEM_ID);
        createEAttribute(getOfferSetTypeEClass, GET_OFFER_SET_TYPE__API_VERSION);

        getOrgEntityConfigAttributesAvailableResponseTypeEClass = createEClass(GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE);
        createEAttribute(getOrgEntityConfigAttributesAvailableResponseTypeEClass, GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE__GROUP);
        createEReference(getOrgEntityConfigAttributesAvailableResponseTypeEClass, GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_RESPONSE_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE);

        getOrgEntityConfigAttributesAvailableTypeEClass = createEClass(GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE);
        createEAttribute(getOrgEntityConfigAttributesAvailableTypeEClass, GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__START_AT);
        createEAttribute(getOrgEntityConfigAttributesAvailableTypeEClass, GET_ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE_TYPE__NUM_TO_RETURN);

        getOrgEntityConfigAttributesResponseTypeEClass = createEClass(GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE);
        createEAttribute(getOrgEntityConfigAttributesResponseTypeEClass, GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__GROUP);
        createEReference(getOrgEntityConfigAttributesResponseTypeEClass, GET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE);

        getOrgEntityConfigAttributesTypeEClass = createEClass(GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE);
        createEAttribute(getOrgEntityConfigAttributesTypeEClass, GET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE);

        getPublicWorkListViewsResponseTypeEClass = createEClass(GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE);
        createEReference(getPublicWorkListViewsResponseTypeEClass, GET_PUBLIC_WORK_LIST_VIEWS_RESPONSE_TYPE__WORK_LIST_VIEWS);

        getPublicWorkListViewsTypeEClass = createEClass(GET_PUBLIC_WORK_LIST_VIEWS_TYPE);
        createEAttribute(getPublicWorkListViewsTypeEClass, GET_PUBLIC_WORK_LIST_VIEWS_TYPE__API_VERSION);
        createEAttribute(getPublicWorkListViewsTypeEClass, GET_PUBLIC_WORK_LIST_VIEWS_TYPE__NUMBER_OF_ITEMS);
        createEAttribute(getPublicWorkListViewsTypeEClass, GET_PUBLIC_WORK_LIST_VIEWS_TYPE__START_POSITION);

        getResourceOrderFilterCriteriaResponseTypeEClass = createEClass(GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE);
        createEReference(getResourceOrderFilterCriteriaResponseTypeEClass, GET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE__ORDER_FILTER_CRITERIA);

        getResourceOrderFilterCriteriaTypeEClass = createEClass(GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE);
        createEAttribute(getResourceOrderFilterCriteriaTypeEClass, GET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID);

        getViewsForResourceResponseTypeEClass = createEClass(GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE);
        createEReference(getViewsForResourceResponseTypeEClass, GET_VIEWS_FOR_RESOURCE_RESPONSE_TYPE__WORK_LIST_VIEWS);

        getViewsForResourceTypeEClass = createEClass(GET_VIEWS_FOR_RESOURCE_TYPE);
        createEAttribute(getViewsForResourceTypeEClass, GET_VIEWS_FOR_RESOURCE_TYPE__API_VERSION);
        createEAttribute(getViewsForResourceTypeEClass, GET_VIEWS_FOR_RESOURCE_TYPE__NUMBER_OF_ITEMS);
        createEAttribute(getViewsForResourceTypeEClass, GET_VIEWS_FOR_RESOURCE_TYPE__START_POSITION);

        getWorkItemHeaderResponseTypeEClass = createEClass(GET_WORK_ITEM_HEADER_RESPONSE_TYPE);
        createEAttribute(getWorkItemHeaderResponseTypeEClass, GET_WORK_ITEM_HEADER_RESPONSE_TYPE__GROUP);
        createEReference(getWorkItemHeaderResponseTypeEClass, GET_WORK_ITEM_HEADER_RESPONSE_TYPE__WORK_ITEM_HEADER);

        getWorkItemHeaderTypeEClass = createEClass(GET_WORK_ITEM_HEADER_TYPE);
        createEAttribute(getWorkItemHeaderTypeEClass, GET_WORK_ITEM_HEADER_TYPE__GROUP);
        createEReference(getWorkItemHeaderTypeEClass, GET_WORK_ITEM_HEADER_TYPE__WORK_ITEM_ID);

        getWorkItemOrderFilterResponseTypeEClass = createEClass(GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE);
        createEReference(getWorkItemOrderFilterResponseTypeEClass, GET_WORK_ITEM_ORDER_FILTER_RESPONSE_TYPE__COLUMN_DATA);

        getWorkItemOrderFilterTypeEClass = createEClass(GET_WORK_ITEM_ORDER_FILTER_TYPE);
        createEAttribute(getWorkItemOrderFilterTypeEClass, GET_WORK_ITEM_ORDER_FILTER_TYPE__LIMIT_COLUMNS);

        getWorkListItemsForGlobalDataResponseTypeEClass = createEClass(GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE);
        createEAttribute(getWorkListItemsForGlobalDataResponseTypeEClass, GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__START_POSITION);
        createEAttribute(getWorkListItemsForGlobalDataResponseTypeEClass, GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__END_POSITION);
        createEReference(getWorkListItemsForGlobalDataResponseTypeEClass, GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_RESPONSE_TYPE__WORK_ITEMS);

        getWorkListItemsForGlobalDataTypeEClass = createEClass(GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE);
        createEAttribute(getWorkListItemsForGlobalDataTypeEClass, GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__GLOBAL_DATA_REF);
        createEReference(getWorkListItemsForGlobalDataTypeEClass, GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__ORDER_FILTER_CRITERIA);
        createEAttribute(getWorkListItemsForGlobalDataTypeEClass, GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__NUMBER_OF_ITEMS);
        createEAttribute(getWorkListItemsForGlobalDataTypeEClass, GET_WORK_LIST_ITEMS_FOR_GLOBAL_DATA_TYPE__START_POSITION);

        getWorkListItemsForViewResponseTypeEClass = createEClass(GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE);
        createEAttribute(getWorkListItemsForViewResponseTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__START_POSITION);
        createEAttribute(getWorkListItemsForViewResponseTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__END_POSITION);
        createEAttribute(getWorkListItemsForViewResponseTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__TOTAL_ITEMS);
        createEReference(getWorkListItemsForViewResponseTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__WORK_ITEMS);
        createEAttribute(getWorkListItemsForViewResponseTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_RESPONSE_TYPE__CUSTOM_DATA);

        getWorkListItemsForViewTypeEClass = createEClass(GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE);
        createEAttribute(getWorkListItemsForViewTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_ALLOCATED_ITEMS);
        createEAttribute(getWorkListItemsForViewTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__GET_TOTAL_COUNT);
        createEAttribute(getWorkListItemsForViewTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__NUMBER_OF_ITEMS);
        createEAttribute(getWorkListItemsForViewTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__START_POSITION);
        createEAttribute(getWorkListItemsForViewTypeEClass, GET_WORK_LIST_ITEMS_FOR_VIEW_TYPE__WORK_LIST_VIEW_ID);

        getWorkListItemsResponseTypeEClass = createEClass(GET_WORK_LIST_ITEMS_RESPONSE_TYPE);
        createEAttribute(getWorkListItemsResponseTypeEClass, GET_WORK_LIST_ITEMS_RESPONSE_TYPE__START_POSITION);
        createEAttribute(getWorkListItemsResponseTypeEClass, GET_WORK_LIST_ITEMS_RESPONSE_TYPE__END_POSITION);
        createEAttribute(getWorkListItemsResponseTypeEClass, GET_WORK_LIST_ITEMS_RESPONSE_TYPE__TOTAL_ITEMS);
        createEReference(getWorkListItemsResponseTypeEClass, GET_WORK_LIST_ITEMS_RESPONSE_TYPE__WORK_ITEMS);

        getWorkListItemsResponseType1EClass = createEClass(GET_WORK_LIST_ITEMS_RESPONSE_TYPE1);
        createEAttribute(getWorkListItemsResponseType1EClass, GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__START_POSITION);
        createEAttribute(getWorkListItemsResponseType1EClass, GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__END_POSITION);
        createEAttribute(getWorkListItemsResponseType1EClass, GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__TOTAL_ITEMS);
        createEReference(getWorkListItemsResponseType1EClass, GET_WORK_LIST_ITEMS_RESPONSE_TYPE1__WORK_ITEMS);

        getWorkListItemsTypeEClass = createEClass(GET_WORK_LIST_ITEMS_TYPE);
        createEAttribute(getWorkListItemsTypeEClass, GET_WORK_LIST_ITEMS_TYPE__RESOURCES_REQUIRED);
        createEReference(getWorkListItemsTypeEClass, GET_WORK_LIST_ITEMS_TYPE__ENTITY_ID);
        createEReference(getWorkListItemsTypeEClass, GET_WORK_LIST_ITEMS_TYPE__ORDER_FILTER_CRITERIA);
        createEAttribute(getWorkListItemsTypeEClass, GET_WORK_LIST_ITEMS_TYPE__GET_TOTAL_COUNT);
        createEAttribute(getWorkListItemsTypeEClass, GET_WORK_LIST_ITEMS_TYPE__NUMBER_OF_ITEMS);
        createEAttribute(getWorkListItemsTypeEClass, GET_WORK_LIST_ITEMS_TYPE__START_POSITION);

        getWorkListViewDetailsTypeEClass = createEClass(GET_WORK_LIST_VIEW_DETAILS_TYPE);
        createEAttribute(getWorkListViewDetailsTypeEClass, GET_WORK_LIST_VIEW_DETAILS_TYPE__API_VERSION);
        createEAttribute(getWorkListViewDetailsTypeEClass, GET_WORK_LIST_VIEW_DETAILS_TYPE__LOCK_VIEW);
        createEAttribute(getWorkListViewDetailsTypeEClass, GET_WORK_LIST_VIEW_DETAILS_TYPE__WORK_LIST_VIEW_ID);

        getWorkModelResponseTypeEClass = createEClass(GET_WORK_MODEL_RESPONSE_TYPE);
        createEReference(getWorkModelResponseTypeEClass, GET_WORK_MODEL_RESPONSE_TYPE__WORK_MODEL);

        getWorkModelsResponseTypeEClass = createEClass(GET_WORK_MODELS_RESPONSE_TYPE);
        createEReference(getWorkModelsResponseTypeEClass, GET_WORK_MODELS_RESPONSE_TYPE__WORK_MODEL_LIST);

        getWorkModelsTypeEClass = createEClass(GET_WORK_MODELS_TYPE);
        createEAttribute(getWorkModelsTypeEClass, GET_WORK_MODELS_TYPE__START_POSITION);
        createEAttribute(getWorkModelsTypeEClass, GET_WORK_MODELS_TYPE__NUMBER_OF_ITEMS);

        getWorkModelTypeEClass = createEClass(GET_WORK_MODEL_TYPE);
        createEAttribute(getWorkModelTypeEClass, GET_WORK_MODEL_TYPE__WORK_MODEL_ID);

        getWorkTypeResponseTypeEClass = createEClass(GET_WORK_TYPE_RESPONSE_TYPE);
        createEReference(getWorkTypeResponseTypeEClass, GET_WORK_TYPE_RESPONSE_TYPE__WORK_TYPE);

        getWorkTypesResponseTypeEClass = createEClass(GET_WORK_TYPES_RESPONSE_TYPE);
        createEReference(getWorkTypesResponseTypeEClass, GET_WORK_TYPES_RESPONSE_TYPE__WORK_TYPEL_LIST);

        getWorkTypesTypeEClass = createEClass(GET_WORK_TYPES_TYPE);
        createEAttribute(getWorkTypesTypeEClass, GET_WORK_TYPES_TYPE__START_POSITION);
        createEAttribute(getWorkTypesTypeEClass, GET_WORK_TYPES_TYPE__NUMBER_OF_ITEMS);

        getWorkTypeTypeEClass = createEClass(GET_WORK_TYPE_TYPE);
        createEAttribute(getWorkTypeTypeEClass, GET_WORK_TYPE_TYPE__WORK_TYPE_ID);

        hiddenPeriodEClass = createEClass(HIDDEN_PERIOD);
        createEReference(hiddenPeriodEClass, HIDDEN_PERIOD__HIDDEN_DURATION);
        createEAttribute(hiddenPeriodEClass, HIDDEN_PERIOD__VISIBLE_DATE);

        itemEClass = createEClass(ITEM);
        createEReference(itemEClass, ITEM__ENTITIES);
        createEReference(itemEClass, ITEM__ENTITY_QUERY);
        createEAttribute(itemEClass, ITEM__WORK_TYPE_UID);
        createEAttribute(itemEClass, ITEM__WORK_TYPE_VERSION);

        itemBodyEClass = createEClass(ITEM_BODY);
        createEReference(itemBodyEClass, ITEM_BODY__PARAMETER);

        itemContextEClass = createEClass(ITEM_CONTEXT);
        createEAttribute(itemContextEClass, ITEM_CONTEXT__ACTIVITY_ID);
        createEAttribute(itemContextEClass, ITEM_CONTEXT__ACTIVITY_NAME);
        createEAttribute(itemContextEClass, ITEM_CONTEXT__APP_INSTANCE);
        createEAttribute(itemContextEClass, ITEM_CONTEXT__APP_NAME);
        createEAttribute(itemContextEClass, ITEM_CONTEXT__APP_ID);
        createEAttribute(itemContextEClass, ITEM_CONTEXT__APP_INSTANCE_DESCRIPTION);

        itemDurationEClass = createEClass(ITEM_DURATION);
        createEAttribute(itemDurationEClass, ITEM_DURATION__DAYS);
        createEAttribute(itemDurationEClass, ITEM_DURATION__HOURS);
        createEAttribute(itemDurationEClass, ITEM_DURATION__MINUTES);
        createEAttribute(itemDurationEClass, ITEM_DURATION__MONTHS);
        createEAttribute(itemDurationEClass, ITEM_DURATION__WEEKS);
        createEAttribute(itemDurationEClass, ITEM_DURATION__YEARS);

        itemPrivilegeEClass = createEClass(ITEM_PRIVILEGE);
        createEReference(itemPrivilegeEClass, ITEM_PRIVILEGE__SUSPEND);
        createEReference(itemPrivilegeEClass, ITEM_PRIVILEGE__STATELESS_REALLOC);
        createEReference(itemPrivilegeEClass, ITEM_PRIVILEGE__STATEFUL_REALLOC);
        createEReference(itemPrivilegeEClass, ITEM_PRIVILEGE__DELLOCATE);
        createEReference(itemPrivilegeEClass, ITEM_PRIVILEGE__DELEGATE);
        createEReference(itemPrivilegeEClass, ITEM_PRIVILEGE__SKIP);
        createEReference(itemPrivilegeEClass, ITEM_PRIVILEGE__PILING);

        itemScheduleEClass = createEClass(ITEM_SCHEDULE);
        createEAttribute(itemScheduleEClass, ITEM_SCHEDULE__START_DATE);
        createEReference(itemScheduleEClass, ITEM_SCHEDULE__MAX_DURATION);
        createEAttribute(itemScheduleEClass, ITEM_SCHEDULE__TARGET_DATE);

        managedObjectIDEClass = createEClass(MANAGED_OBJECT_ID);
        createEAttribute(managedObjectIDEClass, MANAGED_OBJECT_ID__VERSION);

        nextWorkItemEClass = createEClass(NEXT_WORK_ITEM);
        createEReference(nextWorkItemEClass, NEXT_WORK_ITEM__NEXT_ITEM);

        objectIDEClass = createEClass(OBJECT_ID);
        createEAttribute(objectIDEClass, OBJECT_ID__ID);

        onNotificationResponseTypeEClass = createEClass(ON_NOTIFICATION_RESPONSE_TYPE);
        createEAttribute(onNotificationResponseTypeEClass, ON_NOTIFICATION_RESPONSE_TYPE__SUCCESS);

        onNotificationTypeEClass = createEClass(ON_NOTIFICATION_TYPE);
        createEReference(onNotificationTypeEClass, ON_NOTIFICATION_TYPE__MESSAGE_DETAILS);
        createEReference(onNotificationTypeEClass, ON_NOTIFICATION_TYPE__ITEM_BODY);
        createEReference(onNotificationTypeEClass, ON_NOTIFICATION_TYPE__ALLOCATION_HISTORY);

        openWorkItemResponseTypeEClass = createEClass(OPEN_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(openWorkItemResponseTypeEClass, OPEN_WORK_ITEM_RESPONSE_TYPE__GROUP);
        createEReference(openWorkItemResponseTypeEClass, OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_BODY);

        openWorkItemTypeEClass = createEClass(OPEN_WORK_ITEM_TYPE);
        createEAttribute(openWorkItemTypeEClass, OPEN_WORK_ITEM_TYPE__GROUP);
        createEReference(openWorkItemTypeEClass, OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID);

        orderFilterCriteriaEClass = createEClass(ORDER_FILTER_CRITERIA);
        createEAttribute(orderFilterCriteriaEClass, ORDER_FILTER_CRITERIA__ORDER);
        createEAttribute(orderFilterCriteriaEClass, ORDER_FILTER_CRITERIA__FILTER);

        orgEntityConfigAttributeEClass = createEClass(ORG_ENTITY_CONFIG_ATTRIBUTE);
        createEAttribute(orgEntityConfigAttributeEClass, ORG_ENTITY_CONFIG_ATTRIBUTE__ATTRIBUTE_NAME);
        createEAttribute(orgEntityConfigAttributeEClass, ORG_ENTITY_CONFIG_ATTRIBUTE__ATTRIBUTE_VALUE);
        createEAttribute(orgEntityConfigAttributeEClass, ORG_ENTITY_CONFIG_ATTRIBUTE__READ_ONLY);

        orgEntityConfigAttributesAvailableEClass = createEClass(ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE);
        createEAttribute(orgEntityConfigAttributesAvailableEClass, ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__ATTRIBUTE_NAME);
        createEAttribute(orgEntityConfigAttributesAvailableEClass, ORG_ENTITY_CONFIG_ATTRIBUTES_AVAILABLE__READ_ONLY);

        orgEntityConfigAttributeSetEClass = createEClass(ORG_ENTITY_CONFIG_ATTRIBUTE_SET);
        createEAttribute(orgEntityConfigAttributeSetEClass, ORG_ENTITY_CONFIG_ATTRIBUTE_SET__ATTRIBUTE_NAME);
        createEAttribute(orgEntityConfigAttributeSetEClass, ORG_ENTITY_CONFIG_ATTRIBUTE_SET__ATTRIBUTE_VALUE);

        parameterTypeEClass = createEClass(PARAMETER_TYPE);
        createEReference(parameterTypeEClass, PARAMETER_TYPE__COMPLEX_VALUE);
        createEAttribute(parameterTypeEClass, PARAMETER_TYPE__VALUE);
        createEAttribute(parameterTypeEClass, PARAMETER_TYPE__ARRAY);
        createEAttribute(parameterTypeEClass, PARAMETER_TYPE__NAME);

        pendWorkItemEClass = createEClass(PEND_WORK_ITEM);
        createEAttribute(pendWorkItemEClass, PEND_WORK_ITEM__GROUP);
        createEReference(pendWorkItemEClass, PEND_WORK_ITEM__WORK_ITEM_ID);
        createEReference(pendWorkItemEClass, PEND_WORK_ITEM__HIDDEN_PERIOD);

        pendWorkItemResponseTypeEClass = createEClass(PEND_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(pendWorkItemResponseTypeEClass, PEND_WORK_ITEM_RESPONSE_TYPE__GROUP);
        createEReference(pendWorkItemResponseTypeEClass, PEND_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID);

        previewWorkItemFromListResponseTypeEClass = createEClass(PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE);
        createEAttribute(previewWorkItemFromListResponseTypeEClass, PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP);
        createEReference(previewWorkItemFromListResponseTypeEClass, PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW);

        previewWorkItemFromListTypeEClass = createEClass(PREVIEW_WORK_ITEM_FROM_LIST_TYPE);
        createEReference(previewWorkItemFromListTypeEClass, PREVIEW_WORK_ITEM_FROM_LIST_TYPE__ENTITY_ID);
        createEReference(previewWorkItemFromListTypeEClass, PREVIEW_WORK_ITEM_FROM_LIST_TYPE__WORK_ITEM_ID);
        createEAttribute(previewWorkItemFromListTypeEClass, PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRED_FIELD);
        createEAttribute(previewWorkItemFromListTypeEClass, PREVIEW_WORK_ITEM_FROM_LIST_TYPE__REQUIRE_WORK_TYPE);

        privilegeEClass = createEClass(PRIVILEGE);
        createEAttribute(privilegeEClass, PRIVILEGE__NAME);
        createEAttribute(privilegeEClass, PRIVILEGE__QUALIFIER);

        pushNotificationTypeEClass = createEClass(PUSH_NOTIFICATION_TYPE);
        createEReference(pushNotificationTypeEClass, PUSH_NOTIFICATION_TYPE__WORK_ITEM_ID);
        createEReference(pushNotificationTypeEClass, PUSH_NOTIFICATION_TYPE__WORK_TYPE_ID);
        createEReference(pushNotificationTypeEClass, PUSH_NOTIFICATION_TYPE__RESOURCE_IDS);

        reallocateWorkItemDataEClass = createEClass(REALLOCATE_WORK_ITEM_DATA);
        createEAttribute(reallocateWorkItemDataEClass, REALLOCATE_WORK_ITEM_DATA__GROUP);
        createEReference(reallocateWorkItemDataEClass, REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_ID);
        createEAttribute(reallocateWorkItemDataEClass, REALLOCATE_WORK_ITEM_DATA__RESOURCE);
        createEReference(reallocateWorkItemDataEClass, REALLOCATE_WORK_ITEM_DATA__WORK_ITEM_PAYLOAD);

        reallocateWorkItemDataResponseTypeEClass = createEClass(REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE);
        createEAttribute(reallocateWorkItemDataResponseTypeEClass, REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE__GROUP);
        createEReference(reallocateWorkItemDataResponseTypeEClass, REALLOCATE_WORK_ITEM_DATA_RESPONSE_TYPE__WORK_ITEM);

        reallocateWorkItemResponseTypeEClass = createEClass(REALLOCATE_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(reallocateWorkItemResponseTypeEClass, REALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP);
        createEReference(reallocateWorkItemResponseTypeEClass, REALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM);

        reallocateWorkItemTypeEClass = createEClass(REALLOCATE_WORK_ITEM_TYPE);
        createEAttribute(reallocateWorkItemTypeEClass, REALLOCATE_WORK_ITEM_TYPE__GROUP);
        createEReference(reallocateWorkItemTypeEClass, REALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID);
        createEAttribute(reallocateWorkItemTypeEClass, REALLOCATE_WORK_ITEM_TYPE__RESOURCE);
        createEAttribute(reallocateWorkItemTypeEClass, REALLOCATE_WORK_ITEM_TYPE__REVERT_DATA);

        rescheduleWorkItemEClass = createEClass(RESCHEDULE_WORK_ITEM);
        createEReference(rescheduleWorkItemEClass, RESCHEDULE_WORK_ITEM__WORK_ITEM_ID);
        createEReference(rescheduleWorkItemEClass, RESCHEDULE_WORK_ITEM__ITEM_SCHEDULE);
        createEReference(rescheduleWorkItemEClass, RESCHEDULE_WORK_ITEM__ITEM_BODY);

        rescheduleWorkItemResponseTypeEClass = createEClass(RESCHEDULE_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(rescheduleWorkItemResponseTypeEClass, RESCHEDULE_WORK_ITEM_RESPONSE_TYPE__SUCCESS);

        resumeWorkItemResponseTypeEClass = createEClass(RESUME_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(resumeWorkItemResponseTypeEClass, RESUME_WORK_ITEM_RESPONSE_TYPE__SUCCESS);

        resumeWorkItemTypeEClass = createEClass(RESUME_WORK_ITEM_TYPE);
        createEReference(resumeWorkItemTypeEClass, RESUME_WORK_ITEM_TYPE__WORK_ITEM_ID);

        saveOpenWorkItemResponseTypeEClass = createEClass(SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE);
        createEReference(saveOpenWorkItemResponseTypeEClass, SAVE_OPEN_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID);

        saveOpenWorkItemTypeEClass = createEClass(SAVE_OPEN_WORK_ITEM_TYPE);
        createEReference(saveOpenWorkItemTypeEClass, SAVE_OPEN_WORK_ITEM_TYPE__WORK_ITEM_ID);
        createEReference(saveOpenWorkItemTypeEClass, SAVE_OPEN_WORK_ITEM_TYPE__WORK_ITEM_PAYLOAD);

        scheduleWorkItemResponseTypeEClass = createEClass(SCHEDULE_WORK_ITEM_RESPONSE_TYPE);
        createEReference(scheduleWorkItemResponseTypeEClass, SCHEDULE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM_ID);

        scheduleWorkItemTypeEClass = createEClass(SCHEDULE_WORK_ITEM_TYPE);
        createEReference(scheduleWorkItemTypeEClass, SCHEDULE_WORK_ITEM_TYPE__ITEM);
        createEReference(scheduleWorkItemTypeEClass, SCHEDULE_WORK_ITEM_TYPE__ITEM_SCHEDULE);
        createEReference(scheduleWorkItemTypeEClass, SCHEDULE_WORK_ITEM_TYPE__ITEM_CONTEXT);
        createEReference(scheduleWorkItemTypeEClass, SCHEDULE_WORK_ITEM_TYPE__ITEM_BODY);

        scheduleWorkItemWithModelResponseTypeEClass = createEClass(SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE);
        createEReference(scheduleWorkItemWithModelResponseTypeEClass, SCHEDULE_WORK_ITEM_WITH_MODEL_RESPONSE_TYPE__WORK_ITEM_ID);

        scheduleWorkItemWithModelTypeEClass = createEClass(SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE);
        createEReference(scheduleWorkItemWithModelTypeEClass, SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_SCHEDULE);
        createEReference(scheduleWorkItemWithModelTypeEClass, SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_CONTEXT);
        createEReference(scheduleWorkItemWithModelTypeEClass, SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ITEM_BODY);
        createEReference(scheduleWorkItemWithModelTypeEClass, SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__ENTITY_QUERY);
        createEAttribute(scheduleWorkItemWithModelTypeEClass, SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__GROUP_ID);
        createEAttribute(scheduleWorkItemWithModelTypeEClass, SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_UID);
        createEAttribute(scheduleWorkItemWithModelTypeEClass, SCHEDULE_WORK_ITEM_WITH_MODEL_TYPE__WORK_MODEL_VERSION);

        setOrgEntityConfigAttributesResponseTypeEClass = createEClass(SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE);
        createEAttribute(setOrgEntityConfigAttributesResponseTypeEClass, SET_ORG_ENTITY_CONFIG_ATTRIBUTES_RESPONSE_TYPE__SUCCESS);

        setOrgEntityConfigAttributesTypeEClass = createEClass(SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE);
        createEAttribute(setOrgEntityConfigAttributesTypeEClass, SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__GROUP);
        createEReference(setOrgEntityConfigAttributesTypeEClass, SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__ORG_ENTITY_CONFIG_ATTRIBUTE_SET);
        createEAttribute(setOrgEntityConfigAttributesTypeEClass, SET_ORG_ENTITY_CONFIG_ATTRIBUTES_TYPE__RESOURCE);

        setResourceOrderFilterCriteriaResponseTypeEClass = createEClass(SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE);
        createEAttribute(setResourceOrderFilterCriteriaResponseTypeEClass, SET_RESOURCE_ORDER_FILTER_CRITERIA_RESPONSE_TYPE__SUCCESS);

        setResourceOrderFilterCriteriaTypeEClass = createEClass(SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE);
        createEReference(setResourceOrderFilterCriteriaTypeEClass, SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__ORDER_FILTER_CRITERIA);
        createEAttribute(setResourceOrderFilterCriteriaTypeEClass, SET_RESOURCE_ORDER_FILTER_CRITERIA_TYPE__RESOURCE_ID);

        setWorkItemPriorityEClass = createEClass(SET_WORK_ITEM_PRIORITY);
        createEReference(setWorkItemPriorityEClass, SET_WORK_ITEM_PRIORITY__WORK_ITEM_IDAND_PRIORITY);

        setWorkItemPriorityResponseTypeEClass = createEClass(SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE);
        createEAttribute(setWorkItemPriorityResponseTypeEClass, SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE__GROUP);
        createEReference(setWorkItemPriorityResponseTypeEClass, SET_WORK_ITEM_PRIORITY_RESPONSE_TYPE__WORK_ITEM_ID);

        skipWorkItemResponseTypeEClass = createEClass(SKIP_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(skipWorkItemResponseTypeEClass, SKIP_WORK_ITEM_RESPONSE_TYPE__SUCCESS);

        skipWorkItemTypeEClass = createEClass(SKIP_WORK_ITEM_TYPE);
        createEAttribute(skipWorkItemTypeEClass, SKIP_WORK_ITEM_TYPE__GROUP);
        createEReference(skipWorkItemTypeEClass, SKIP_WORK_ITEM_TYPE__WORK_ITEM_ID);

        startGroupResponseTypeEClass = createEClass(START_GROUP_RESPONSE_TYPE);
        createEAttribute(startGroupResponseTypeEClass, START_GROUP_RESPONSE_TYPE__GROUP_ID);

        startGroupTypeEClass = createEClass(START_GROUP_TYPE);
        createEAttribute(startGroupTypeEClass, START_GROUP_TYPE__GROUP_TYPE);
        createEAttribute(startGroupTypeEClass, START_GROUP_TYPE__DESCRIPTION);

        suspendWorkItemResponseTypeEClass = createEClass(SUSPEND_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(suspendWorkItemResponseTypeEClass, SUSPEND_WORK_ITEM_RESPONSE_TYPE__SUCCESS);

        suspendWorkItemTypeEClass = createEClass(SUSPEND_WORK_ITEM_TYPE);
        createEReference(suspendWorkItemTypeEClass, SUSPEND_WORK_ITEM_TYPE__WORK_ITEM_ID);
        createEAttribute(suspendWorkItemTypeEClass, SUSPEND_WORK_ITEM_TYPE__FORCE_SUSPEND);

        unallocateWorkItemResponseTypeEClass = createEClass(UNALLOCATE_WORK_ITEM_RESPONSE_TYPE);
        createEAttribute(unallocateWorkItemResponseTypeEClass, UNALLOCATE_WORK_ITEM_RESPONSE_TYPE__GROUP);
        createEReference(unallocateWorkItemResponseTypeEClass, UNALLOCATE_WORK_ITEM_RESPONSE_TYPE__WORK_ITEM);

        unallocateWorkItemTypeEClass = createEClass(UNALLOCATE_WORK_ITEM_TYPE);
        createEAttribute(unallocateWorkItemTypeEClass, UNALLOCATE_WORK_ITEM_TYPE__GROUP);
        createEReference(unallocateWorkItemTypeEClass, UNALLOCATE_WORK_ITEM_TYPE__WORK_ITEM_ID);

        unlockWorkListViewResponseTypeEClass = createEClass(UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE);
        createEAttribute(unlockWorkListViewResponseTypeEClass, UNLOCK_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID);

        unlockWorkListViewTypeEClass = createEClass(UNLOCK_WORK_LIST_VIEW_TYPE);
        createEAttribute(unlockWorkListViewTypeEClass, UNLOCK_WORK_LIST_VIEW_TYPE__WORK_LIST_VIEW_ID);

        workItemEClass = createEClass(WORK_ITEM);
        createEReference(workItemEClass, WORK_ITEM__ID);
        createEReference(workItemEClass, WORK_ITEM__HEADER);
        createEReference(workItemEClass, WORK_ITEM__ATTRIBUTES);
        createEReference(workItemEClass, WORK_ITEM__BODY);
        createEReference(workItemEClass, WORK_ITEM__WORK_TYPE);
        createEAttribute(workItemEClass, WORK_ITEM__STATE);
        createEAttribute(workItemEClass, WORK_ITEM__VISIBLE);

        workItemAttributesEClass = createEClass(WORK_ITEM_ATTRIBUTES);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE1);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE10);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE11);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE12);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE13);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE14);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE15);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE16);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE17);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE18);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE19);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE2);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE20);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE21);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE22);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE23);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE24);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE25);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE26);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE27);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE28);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE29);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE3);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE30);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE31);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE32);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE33);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE34);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE35);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE36);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE37);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE38);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE39);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE4);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE40);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE5);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE6);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE7);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE8);
        createEAttribute(workItemAttributesEClass, WORK_ITEM_ATTRIBUTES__ATTRIBUTE9);

        workItemBodyEClass = createEClass(WORK_ITEM_BODY);
        createEReference(workItemBodyEClass, WORK_ITEM_BODY__DATA_MODEL);

        workItemFlagsEClass = createEClass(WORK_ITEM_FLAGS);
        createEAttribute(workItemFlagsEClass, WORK_ITEM_FLAGS__SCHEDULE_STATUS);

        workItemHeaderEClass = createEClass(WORK_ITEM_HEADER);
        createEReference(workItemHeaderEClass, WORK_ITEM_HEADER__FLAGS);
        createEReference(workItemHeaderEClass, WORK_ITEM_HEADER__ITEM_CONTEXT);
        createEAttribute(workItemHeaderEClass, WORK_ITEM_HEADER__END_DATE);
        createEAttribute(workItemHeaderEClass, WORK_ITEM_HEADER__START_DATE);

        workItemIDandPriorityTypeEClass = createEClass(WORK_ITEM_IDAND_PRIORITY_TYPE);
        createEReference(workItemIDandPriorityTypeEClass, WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_ID);
        createEReference(workItemIDandPriorityTypeEClass, WORK_ITEM_IDAND_PRIORITY_TYPE__WORK_ITEM_PRIORITY);

        workItemPreviewEClass = createEClass(WORK_ITEM_PREVIEW);
        createEReference(workItemPreviewEClass, WORK_ITEM_PREVIEW__FIELD_PREVIEW);
        createEReference(workItemPreviewEClass, WORK_ITEM_PREVIEW__WORK_TYPE);

        workItemPriorityTypeEClass = createEClass(WORK_ITEM_PRIORITY_TYPE);
        createEAttribute(workItemPriorityTypeEClass, WORK_ITEM_PRIORITY_TYPE__ABS_PRIORITY);
        createEAttribute(workItemPriorityTypeEClass, WORK_ITEM_PRIORITY_TYPE__OFFSET_PRIORITY);

        workListViewEClass = createEClass(WORK_LIST_VIEW);
        createEAttribute(workListViewEClass, WORK_LIST_VIEW__CREATION_DATE);
        createEAttribute(workListViewEClass, WORK_LIST_VIEW__LOCKER);
        createEAttribute(workListViewEClass, WORK_LIST_VIEW__MODIFICATION_DATE);
        createEAttribute(workListViewEClass, WORK_LIST_VIEW__WORK_VIEW_ID);

        workListViewCommonEClass = createEClass(WORK_LIST_VIEW_COMMON);
        createEReference(workListViewCommonEClass, WORK_LIST_VIEW_COMMON__ENTITY_ID);
        createEAttribute(workListViewCommonEClass, WORK_LIST_VIEW_COMMON__RESOURCES_REQUIRED);
        createEReference(workListViewCommonEClass, WORK_LIST_VIEW_COMMON__ORDER_FILTER_CRITERIA);
        createEAttribute(workListViewCommonEClass, WORK_LIST_VIEW_COMMON__DESCRIPTION);
        createEAttribute(workListViewCommonEClass, WORK_LIST_VIEW_COMMON__GET_ALLOCATED_ITEMS);
        createEAttribute(workListViewCommonEClass, WORK_LIST_VIEW_COMMON__NAME);
        createEAttribute(workListViewCommonEClass, WORK_LIST_VIEW_COMMON__OWNER);
        createEAttribute(workListViewCommonEClass, WORK_LIST_VIEW_COMMON__PUBLIC);

        workListViewEditEClass = createEClass(WORK_LIST_VIEW_EDIT);
        createEReference(workListViewEditEClass, WORK_LIST_VIEW_EDIT__AUTHORS);
        createEReference(workListViewEditEClass, WORK_LIST_VIEW_EDIT__USERS);
        createEAttribute(workListViewEditEClass, WORK_LIST_VIEW_EDIT__CUSTOM_DATA);

        workListViewPageItemEClass = createEClass(WORK_LIST_VIEW_PAGE_ITEM);
        createEAttribute(workListViewPageItemEClass, WORK_LIST_VIEW_PAGE_ITEM__CREATION_DATE);
        createEAttribute(workListViewPageItemEClass, WORK_LIST_VIEW_PAGE_ITEM__MODIFICATION_DATE);
        createEAttribute(workListViewPageItemEClass, WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID);

        workModelEClass = createEClass(WORK_MODEL);
        createEReference(workModelEClass, WORK_MODEL__BASE_MODEL_INFO);
        createEReference(workModelEClass, WORK_MODEL__WORK_MODEL_SPECIFICATION);
        createEReference(workModelEClass, WORK_MODEL__WORK_MODEL_ENTITIES);
        createEReference(workModelEClass, WORK_MODEL__WORK_MODEL_TYPES);
        createEReference(workModelEClass, WORK_MODEL__ITEM_PRIVILEGES);
        createEReference(workModelEClass, WORK_MODEL__WORK_MODEL_SCRIPTS);
        createEReference(workModelEClass, WORK_MODEL__ATTRIBUTE_ALIAS_LIST);
        createEAttribute(workModelEClass, WORK_MODEL__WORK_MODEL_UID);

        workModelEntitiesEClass = createEClass(WORK_MODEL_ENTITIES);
        createEReference(workModelEntitiesEClass, WORK_MODEL_ENTITIES__WORK_MODEL_ENTITY);
        createEAttribute(workModelEntitiesEClass, WORK_MODEL_ENTITIES__EXPRESSION);

        workModelEntityEClass = createEClass(WORK_MODEL_ENTITY);
        createEReference(workModelEntityEClass, WORK_MODEL_ENTITY__ENTITY_QUERY);
        createEReference(workModelEntityEClass, WORK_MODEL_ENTITY__ENTITIES);
        createEAttribute(workModelEntityEClass, WORK_MODEL_ENTITY__DISTRIBUTION_STRATEGY);

        workModelListEClass = createEClass(WORK_MODEL_LIST);
        createEAttribute(workModelListEClass, WORK_MODEL_LIST__START_POSITION);
        createEAttribute(workModelListEClass, WORK_MODEL_LIST__END_POSITION);
        createEReference(workModelListEClass, WORK_MODEL_LIST__TYPE);

        workModelMappingEClass = createEClass(WORK_MODEL_MAPPING);
        createEAttribute(workModelMappingEClass, WORK_MODEL_MAPPING__TYPE_PARAM_NAME);
        createEAttribute(workModelMappingEClass, WORK_MODEL_MAPPING__MODEL_PARAM_NAME);
        createEAttribute(workModelMappingEClass, WORK_MODEL_MAPPING__DEFAULT_VALUE);

        workModelScriptEClass = createEClass(WORK_MODEL_SCRIPT);
        createEAttribute(workModelScriptEClass, WORK_MODEL_SCRIPT__SCRIPT_BODY);
        createEAttribute(workModelScriptEClass, WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE);
        createEAttribute(workModelScriptEClass, WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_EXTENSION);
        createEAttribute(workModelScriptEClass, WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_VERSION);
        createEAttribute(workModelScriptEClass, WORK_MODEL_SCRIPT__SCRIPT_OPERATION);
        createEAttribute(workModelScriptEClass, WORK_MODEL_SCRIPT__SCRIPT_TYPE_ID);

        workModelScriptsEClass = createEClass(WORK_MODEL_SCRIPTS);
        createEReference(workModelScriptsEClass, WORK_MODEL_SCRIPTS__WORK_MODEL_SCRIPT);

        workModelSpecificationEClass = createEClass(WORK_MODEL_SPECIFICATION);

        workModelTypeEClass = createEClass(WORK_MODEL_TYPE);
        createEReference(workModelTypeEClass, WORK_MODEL_TYPE__WORK_MODEL_MAPPING);
        createEAttribute(workModelTypeEClass, WORK_MODEL_TYPE__VERSION);
        createEAttribute(workModelTypeEClass, WORK_MODEL_TYPE__WORK_TYPE_ID);

        workModelTypesEClass = createEClass(WORK_MODEL_TYPES);
        createEReference(workModelTypesEClass, WORK_MODEL_TYPES__WORK_MODEL_TYPE);
        createEAttribute(workModelTypesEClass, WORK_MODEL_TYPES__EXPRESSION);

        workTypeListEClass = createEClass(WORK_TYPE_LIST);
        createEAttribute(workTypeListEClass, WORK_TYPE_LIST__START_POSITION);
        createEAttribute(workTypeListEClass, WORK_TYPE_LIST__END_POSITION);
        createEReference(workTypeListEClass, WORK_TYPE_LIST__TYPES);

        // Create enums
        columnCapabilityEEnum = createEEnum(COLUMN_CAPABILITY);
        columnTypeEEnum = createEEnum(COLUMN_TYPE);
        distributionStrategyEEnum = createEEnum(DISTRIBUTION_STRATEGY);
        methodAuthorisationActionEEnum = createEEnum(METHOD_AUTHORISATION_ACTION);
        methodAuthorisationComponentEEnum = createEEnum(METHOD_AUTHORISATION_COMPONENT);
        resourcesRequiredTypeEEnum = createEEnum(RESOURCES_REQUIRED_TYPE);
        scheduleStatusEEnum = createEEnum(SCHEDULE_STATUS);
        workGroupTypeEEnum = createEEnum(WORK_GROUP_TYPE);
        workItemScriptOperationEEnum = createEEnum(WORK_ITEM_SCRIPT_OPERATION);
        workItemScriptTypeEEnum = createEEnum(WORK_ITEM_SCRIPT_TYPE);
        workItemStateEEnum = createEEnum(WORK_ITEM_STATE);

        // Create data types
        attribute10TypeEDataType = createEDataType(ATTRIBUTE10_TYPE);
        attribute11TypeEDataType = createEDataType(ATTRIBUTE11_TYPE);
        attribute12TypeEDataType = createEDataType(ATTRIBUTE12_TYPE);
        attribute13TypeEDataType = createEDataType(ATTRIBUTE13_TYPE);
        attribute14TypeEDataType = createEDataType(ATTRIBUTE14_TYPE);
        attribute21TypeEDataType = createEDataType(ATTRIBUTE21_TYPE);
        attribute22TypeEDataType = createEDataType(ATTRIBUTE22_TYPE);
        attribute23TypeEDataType = createEDataType(ATTRIBUTE23_TYPE);
        attribute24TypeEDataType = createEDataType(ATTRIBUTE24_TYPE);
        attribute25TypeEDataType = createEDataType(ATTRIBUTE25_TYPE);
        attribute26TypeEDataType = createEDataType(ATTRIBUTE26_TYPE);
        attribute27TypeEDataType = createEDataType(ATTRIBUTE27_TYPE);
        attribute28TypeEDataType = createEDataType(ATTRIBUTE28_TYPE);
        attribute29TypeEDataType = createEDataType(ATTRIBUTE29_TYPE);
        attribute2TypeEDataType = createEDataType(ATTRIBUTE2_TYPE);
        attribute30TypeEDataType = createEDataType(ATTRIBUTE30_TYPE);
        attribute31TypeEDataType = createEDataType(ATTRIBUTE31_TYPE);
        attribute32TypeEDataType = createEDataType(ATTRIBUTE32_TYPE);
        attribute33TypeEDataType = createEDataType(ATTRIBUTE33_TYPE);
        attribute34TypeEDataType = createEDataType(ATTRIBUTE34_TYPE);
        attribute35TypeEDataType = createEDataType(ATTRIBUTE35_TYPE);
        attribute36TypeEDataType = createEDataType(ATTRIBUTE36_TYPE);
        attribute37TypeEDataType = createEDataType(ATTRIBUTE37_TYPE);
        attribute38TypeEDataType = createEDataType(ATTRIBUTE38_TYPE);
        attribute39TypeEDataType = createEDataType(ATTRIBUTE39_TYPE);
        attribute3TypeEDataType = createEDataType(ATTRIBUTE3_TYPE);
        attribute40TypeEDataType = createEDataType(ATTRIBUTE40_TYPE);
        attribute4TypeEDataType = createEDataType(ATTRIBUTE4_TYPE);
        attribute8TypeEDataType = createEDataType(ATTRIBUTE8_TYPE);
        attribute9TypeEDataType = createEDataType(ATTRIBUTE9_TYPE);
        columnCapabilityObjectEDataType = createEDataType(COLUMN_CAPABILITY_OBJECT);
        columnTypeObjectEDataType = createEDataType(COLUMN_TYPE_OBJECT);
        descriptionTypeEDataType = createEDataType(DESCRIPTION_TYPE);
        distributionStrategyObjectEDataType = createEDataType(DISTRIBUTION_STRATEGY_OBJECT);
        lockerTypeEDataType = createEDataType(LOCKER_TYPE);
        methodAuthorisationActionObjectEDataType = createEDataType(METHOD_AUTHORISATION_ACTION_OBJECT);
        methodAuthorisationComponentObjectEDataType = createEDataType(METHOD_AUTHORISATION_COMPONENT_OBJECT);
        nameTypeEDataType = createEDataType(NAME_TYPE);
        ownerTypeEDataType = createEDataType(OWNER_TYPE);
        resourcesRequiredTypeObjectEDataType = createEDataType(RESOURCES_REQUIRED_TYPE_OBJECT);
        scheduleStatusObjectEDataType = createEDataType(SCHEDULE_STATUS_OBJECT);
        scriptOperationTypeEDataType = createEDataType(SCRIPT_OPERATION_TYPE);
        workGroupTypeObjectEDataType = createEDataType(WORK_GROUP_TYPE_OBJECT);
        workItemScriptOperationObjectEDataType = createEDataType(WORK_ITEM_SCRIPT_OPERATION_OBJECT);
        workItemScriptTypeObjectEDataType = createEDataType(WORK_ITEM_SCRIPT_TYPE_OBJECT);
        workItemStateObjectEDataType = createEDataType(WORK_ITEM_STATE_OBJECT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
        ExceptionPackage theExceptionPackage = (ExceptionPackage)EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI);
        DatamodelPackage theDatamodelPackage = (DatamodelPackage)EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI);
        OrganisationPackage theOrganisationPackage = (OrganisationPackage)EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        editWorkListViewTypeEClass.getESuperTypes().add(this.getWorkListViewEdit());
        itemEClass.getESuperTypes().add(this.getBaseItemInfo());
        managedObjectIDEClass.getESuperTypes().add(this.getObjectID());
        workItemHeaderEClass.getESuperTypes().add(this.getBaseItemInfo());
        workItemPreviewEClass.getESuperTypes().add(this.getManagedObjectID());
        workListViewEClass.getESuperTypes().add(this.getWorkListViewEdit());
        workListViewEditEClass.getESuperTypes().add(this.getWorkListViewCommon());
        workListViewPageItemEClass.getESuperTypes().add(this.getWorkListViewCommon());
        workModelSpecificationEClass.getESuperTypes().add(theDatamodelPackage.getDataModel());

        // Initialize classes and features; add operations and parameters
        initEClass(addCurrentResourceToViewResponseTypeEClass, AddCurrentResourceToViewResponseType.class, "AddCurrentResourceToViewResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAddCurrentResourceToViewResponseType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, AddCurrentResourceToViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(addCurrentResourceToViewTypeEClass, AddCurrentResourceToViewType.class, "AddCurrentResourceToViewType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAddCurrentResourceToViewType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, AddCurrentResourceToViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(allocateAndOpenNextWorkItemResponseTypeEClass, AllocateAndOpenNextWorkItemResponseType.class, "AllocateAndOpenNextWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAllocateAndOpenNextWorkItemResponseType_WorkItem(), this.getWorkItem(), null, "workItem", null, 1, 1, AllocateAndOpenNextWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(allocateAndOpenNextWorkItemTypeEClass, AllocateAndOpenNextWorkItemType.class, "AllocateAndOpenNextWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAllocateAndOpenNextWorkItemType_Resource(), theXMLTypePackage.getString(), "resource", null, 1, 1, AllocateAndOpenNextWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAllocateAndOpenNextWorkItemType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 0, 1, AllocateAndOpenNextWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(allocateAndOpenWorkItemResponseTypeEClass, AllocateAndOpenWorkItemResponseType.class, "AllocateAndOpenWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAllocateAndOpenWorkItemResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, AllocateAndOpenWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAllocateAndOpenWorkItemResponseType_WorkItem(), this.getWorkItem(), null, "workItem", null, 1, -1, AllocateAndOpenWorkItemResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(allocateAndOpenWorkItemTypeEClass, AllocateAndOpenWorkItemType.class, "AllocateAndOpenWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAllocateAndOpenWorkItemType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, AllocateAndOpenWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAllocateAndOpenWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, AllocateAndOpenWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getAllocateAndOpenWorkItemType_Resource(), theXMLTypePackage.getString(), "resource", null, 1, -1, AllocateAndOpenWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(allocateWorkItemResponseTypeEClass, AllocateWorkItemResponseType.class, "AllocateWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAllocateWorkItemResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, AllocateWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAllocateWorkItemResponseType_WorkItem(), this.getWorkItem(), null, "workItem", null, 1, -1, AllocateWorkItemResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(allocateWorkItemTypeEClass, AllocateWorkItemType.class, "AllocateWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAllocateWorkItemType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, AllocateWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAllocateWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, AllocateWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getAllocateWorkItemType_Resource(), theXMLTypePackage.getString(), "resource", null, 1, -1, AllocateWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(allocationHistoryEClass, AllocationHistory.class, "AllocationHistory", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAllocationHistory_ResourceID(), theXMLTypePackage.getString(), "resourceID", null, 1, 1, AllocationHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAllocationHistory_AllocationDate(), theXMLTypePackage.getDateTime(), "allocationDate", null, 1, 1, AllocationHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAllocationHistory_AllocationID(), theXMLTypePackage.getLong(), "allocationID", null, 1, 1, AllocationHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncCancelWorkItemResponseTypeEClass, AsyncCancelWorkItemResponseType.class, "AsyncCancelWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncCancelWorkItemResponseType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncCancelWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncCancelWorkItemResponseType_ErrorMessage(), theExceptionPackage.getErrorLine(), null, "errorMessage", null, 0, 1, AsyncCancelWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncCancelWorkItemTypeEClass, AsyncCancelWorkItemType.class, "AsyncCancelWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncCancelWorkItemType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncCancelWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncEndGroupResponseTypeEClass, AsyncEndGroupResponseType.class, "AsyncEndGroupResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAsyncEndGroupResponseType_ActivityID(), theXMLTypePackage.getString(), "activityID", null, 1, 1, AsyncEndGroupResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAsyncEndGroupResponseType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 1, 1, AsyncEndGroupResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncEndGroupResponseType_ErrorMessage(), theExceptionPackage.getErrorLine(), null, "errorMessage", null, 0, 1, AsyncEndGroupResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncEndGroupTypeEClass, AsyncEndGroupType.class, "AsyncEndGroupType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAsyncEndGroupType_ActivityID(), theXMLTypePackage.getString(), "activityID", null, 1, 1, AsyncEndGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncEndGroupType_EndGroup(), this.getEndGroupType(), null, "endGroup", null, 1, 1, AsyncEndGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncRescheduleWorkItemResponseTypeEClass, AsyncRescheduleWorkItemResponseType.class, "AsyncRescheduleWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncRescheduleWorkItemResponseType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncRescheduleWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncRescheduleWorkItemResponseType_ErrorMessage(), theExceptionPackage.getErrorLine(), null, "errorMessage", null, 0, 1, AsyncRescheduleWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncRescheduleWorkItemTypeEClass, AsyncRescheduleWorkItemType.class, "AsyncRescheduleWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncRescheduleWorkItemType_ItemSchedule(), this.getItemSchedule(), null, "itemSchedule", null, 0, 1, AsyncRescheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncRescheduleWorkItemType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncRescheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncRescheduleWorkItemType_ItemBody(), this.getItemBody(), null, "itemBody", null, 0, 1, AsyncRescheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncResumeWorkItemResponseTypeEClass, AsyncResumeWorkItemResponseType.class, "AsyncResumeWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncResumeWorkItemResponseType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncResumeWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncResumeWorkItemResponseType_ErrorMessage(), theExceptionPackage.getErrorLine(), null, "errorMessage", null, 0, 1, AsyncResumeWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncResumeWorkItemTypeEClass, AsyncResumeWorkItemType.class, "AsyncResumeWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncResumeWorkItemType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncResumeWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncScheduleWorkItemResponseTypeEClass, AsyncScheduleWorkItemResponseType.class, "AsyncScheduleWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncScheduleWorkItemResponseType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncScheduleWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncScheduleWorkItemResponseType_ErrorMessage(), theExceptionPackage.getErrorLine(), null, "errorMessage", null, 0, 1, AsyncScheduleWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncScheduleWorkItemTypeEClass, AsyncScheduleWorkItemType.class, "AsyncScheduleWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncScheduleWorkItemType_ScheduleWorkItem(), this.getScheduleWorkItemType(), null, "scheduleWorkItem", null, 1, 1, AsyncScheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncScheduleWorkItemType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncScheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncScheduleWorkItemWithModelResponseTypeEClass, AsyncScheduleWorkItemWithModelResponseType.class, "AsyncScheduleWorkItemWithModelResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncScheduleWorkItemWithModelResponseType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncScheduleWorkItemWithModelResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncScheduleWorkItemWithModelResponseType_ErrorMessage(), theExceptionPackage.getErrorLine(), null, "errorMessage", null, 0, 1, AsyncScheduleWorkItemWithModelResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncScheduleWorkItemWithModelTypeEClass, AsyncScheduleWorkItemWithModelType.class, "AsyncScheduleWorkItemWithModelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncScheduleWorkItemWithModelType_ScheduleWorkItemWithModel(), this.getScheduleWorkItemWithModelType(), null, "scheduleWorkItemWithModel", null, 1, 1, AsyncScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncScheduleWorkItemWithModelType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncStartGroupResponseTypeEClass, AsyncStartGroupResponseType.class, "AsyncStartGroupResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAsyncStartGroupResponseType_ActivityID(), theXMLTypePackage.getString(), "activityID", null, 1, 1, AsyncStartGroupResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAsyncStartGroupResponseType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 1, 1, AsyncStartGroupResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncStartGroupResponseType_ErrorMessage(), theExceptionPackage.getErrorLine(), null, "errorMessage", null, 0, 1, AsyncStartGroupResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncStartGroupTypeEClass, AsyncStartGroupType.class, "AsyncStartGroupType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAsyncStartGroupType_ActivityID(), theXMLTypePackage.getString(), "activityID", null, 1, 1, AsyncStartGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAsyncStartGroupType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 1, 1, AsyncStartGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncStartGroupType_StartGroup(), this.getStartGroupType(), null, "startGroup", null, 1, 1, AsyncStartGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncSuspendWorkItemResponseTypeEClass, AsyncSuspendWorkItemResponseType.class, "AsyncSuspendWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncSuspendWorkItemResponseType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncSuspendWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAsyncSuspendWorkItemResponseType_ErrorMessage(), theExceptionPackage.getErrorLine(), null, "errorMessage", null, 0, 1, AsyncSuspendWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncSuspendWorkItemTypeEClass, AsyncSuspendWorkItemType.class, "AsyncSuspendWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncSuspendWorkItemType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, AsyncSuspendWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAsyncSuspendWorkItemType_ForceSuspend(), theXMLTypePackage.getBoolean(), "forceSuspend", "false", 0, 1, AsyncSuspendWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(asyncWorkItemDetailsEClass, AsyncWorkItemDetails.class, "AsyncWorkItemDetails", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAsyncWorkItemDetails_WorkItemId(), this.getObjectID(), null, "workItemId", null, 1, 1, AsyncWorkItemDetails.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAsyncWorkItemDetails_ActivityID(), theXMLTypePackage.getString(), "activityID", null, 1, 1, AsyncWorkItemDetails.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAsyncWorkItemDetails_UaSequenceId(), theXMLTypePackage.getInt(), "uaSequenceId", null, 1, 1, AsyncWorkItemDetails.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAsyncWorkItemDetails_BrmSequenceId(), theXMLTypePackage.getInt(), "brmSequenceId", null, 1, 1, AsyncWorkItemDetails.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(attributeAliasListTypeEClass, AttributeAliasListType.class, "AttributeAliasListType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAttributeAliasListType_AttributeAlias(), theDatamodelPackage.getAliasType(), null, "attributeAlias", null, 1, -1, AttributeAliasListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(baseItemInfoEClass, BaseItemInfo.class, "BaseItemInfo", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getBaseItemInfo_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, BaseItemInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBaseItemInfo_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, BaseItemInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBaseItemInfo_DistributionStrategy(), this.getDistributionStrategy(), "distributionStrategy", null, 1, 1, BaseItemInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBaseItemInfo_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 0, 1, BaseItemInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBaseItemInfo_Priority(), theXMLTypePackage.getInt(), "priority", null, 0, 1, BaseItemInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(baseModelInfoEClass, BaseModelInfo.class, "BaseModelInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getBaseModelInfo_Description(), theXMLTypePackage.getString(), "description", null, 1, 1, BaseModelInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBaseModelInfo_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, BaseModelInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBaseModelInfo_Priority(), theXMLTypePackage.getInt(), "priority", "50", 0, 1, BaseModelInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(cancelWorkItemResponseTypeEClass, CancelWorkItemResponseType.class, "CancelWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCancelWorkItemResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, CancelWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(cancelWorkItemTypeEClass, CancelWorkItemType.class, "CancelWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCancelWorkItemType_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, 1, CancelWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(chainedWorkItemNotificationTypeEClass, ChainedWorkItemNotificationType.class, "ChainedWorkItemNotificationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getChainedWorkItemNotificationType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 1, 1, ChainedWorkItemNotificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChainedWorkItemNotificationType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 0, 1, ChainedWorkItemNotificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(closeWorkItemResponseTypeEClass, CloseWorkItemResponseType.class, "CloseWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCloseWorkItemResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, CloseWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCloseWorkItemResponseType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, CloseWorkItemResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(closeWorkItemTypeEClass, CloseWorkItemType.class, "CloseWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCloseWorkItemType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, CloseWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCloseWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, CloseWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getCloseWorkItemType_WorkItemPayload(), this.getWorkItemBody(), null, "workItemPayload", null, 1, -1, CloseWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getCloseWorkItemType_HiddenPeriod(), this.getHiddenPeriod(), null, "hiddenPeriod", null, 1, -1, CloseWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(columnDefinitionEClass, ColumnDefinition.class, "ColumnDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getColumnDefinition_Capability(), this.getColumnCapability(), "capability", null, 0, 1, ColumnDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getColumnDefinition_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, ColumnDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getColumnDefinition_Id(), theXMLTypePackage.getShort(), "id", null, 1, 1, ColumnDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getColumnDefinition_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, ColumnDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getColumnDefinition_Type(), this.getColumnType(), "type", null, 0, 1, ColumnDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(columnOrderEClass, ColumnOrder.class, "ColumnOrder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getColumnOrder_ColumnDef(), this.getColumnDefinition(), null, "columnDef", null, 1, 1, ColumnOrder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getColumnOrder_Ascending(), theXMLTypePackage.getBoolean(), "ascending", null, 1, 1, ColumnOrder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(completeWorkItemResponseTypeEClass, CompleteWorkItemResponseType.class, "CompleteWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCompleteWorkItemResponseType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 0, 1, CompleteWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCompleteWorkItemResponseType_NextWorkItem(), this.getManagedObjectID(), null, "nextWorkItem", null, 0, 1, CompleteWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(completeWorkItemTypeEClass, CompleteWorkItemType.class, "CompleteWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCompleteWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, 1, CompleteWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCompleteWorkItemType_WorkItemPayload(), this.getWorkItemBody(), null, "workItemPayload", null, 1, 1, CompleteWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCompleteWorkItemType_GetNextPiledItem(), theXMLTypePackage.getBoolean(), "getNextPiledItem", null, 0, 1, CompleteWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(createWorkListViewResponseTypeEClass, CreateWorkListViewResponseType.class, "CreateWorkListViewResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCreateWorkListViewResponseType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, CreateWorkListViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(deleteCurrentResourceFromViewResponseTypeEClass, DeleteCurrentResourceFromViewResponseType.class, "DeleteCurrentResourceFromViewResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDeleteCurrentResourceFromViewResponseType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, DeleteCurrentResourceFromViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(deleteCurrentResourceFromViewTypeEClass, DeleteCurrentResourceFromViewType.class, "DeleteCurrentResourceFromViewType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDeleteCurrentResourceFromViewType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, DeleteCurrentResourceFromViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(deleteOrgEntityConfigAttributesResponseTypeEClass, DeleteOrgEntityConfigAttributesResponseType.class, "DeleteOrgEntityConfigAttributesResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDeleteOrgEntityConfigAttributesResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, DeleteOrgEntityConfigAttributesResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(deleteOrgEntityConfigAttributesTypeEClass, DeleteOrgEntityConfigAttributesType.class, "DeleteOrgEntityConfigAttributesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDeleteOrgEntityConfigAttributesType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, DeleteOrgEntityConfigAttributesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDeleteOrgEntityConfigAttributesType_AttributeName(), theXMLTypePackage.getString(), "attributeName", null, 1, -1, DeleteOrgEntityConfigAttributesType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getDeleteOrgEntityConfigAttributesType_Resource(), theXMLTypePackage.getString(), "resource", null, 1, 1, DeleteOrgEntityConfigAttributesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(deleteWorkListViewResponseTypeEClass, DeleteWorkListViewResponseType.class, "DeleteWorkListViewResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDeleteWorkListViewResponseType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, DeleteWorkListViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(deleteWorkListViewTypeEClass, DeleteWorkListViewType.class, "DeleteWorkListViewType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDeleteWorkListViewType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, DeleteWorkListViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AddCurrentResourceToView(), this.getAddCurrentResourceToViewType(), null, "addCurrentResourceToView", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AddCurrentResourceToViewResponse(), this.getAddCurrentResourceToViewResponseType(), null, "addCurrentResourceToViewResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AllocateAndOpenNextWorkItem(), this.getAllocateAndOpenNextWorkItemType(), null, "allocateAndOpenNextWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AllocateAndOpenNextWorkItemResponse(), this.getAllocateAndOpenNextWorkItemResponseType(), null, "allocateAndOpenNextWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AllocateAndOpenWorkItem(), this.getAllocateAndOpenWorkItemType(), null, "allocateAndOpenWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AllocateAndOpenWorkItemResponse(), this.getAllocateAndOpenWorkItemResponseType(), null, "allocateAndOpenWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AllocateWorkItem(), this.getAllocateWorkItemType(), null, "allocateWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AllocateWorkItemResponse(), this.getAllocateWorkItemResponseType(), null, "allocateWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncCancelWorkItem(), this.getAsyncCancelWorkItemType(), null, "asyncCancelWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncCancelWorkItemResponse(), this.getAsyncCancelWorkItemResponseType(), null, "asyncCancelWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncEndGroup(), this.getAsyncEndGroupType(), null, "asyncEndGroup", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncEndGroupResponse(), this.getAsyncEndGroupResponseType(), null, "asyncEndGroupResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncRescheduleWorkItem(), this.getAsyncRescheduleWorkItemType(), null, "asyncRescheduleWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncRescheduleWorkItemResponse(), this.getAsyncRescheduleWorkItemResponseType(), null, "asyncRescheduleWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncResumeWorkItem(), this.getAsyncResumeWorkItemType(), null, "asyncResumeWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncResumeWorkItemResponse(), this.getAsyncResumeWorkItemResponseType(), null, "asyncResumeWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncScheduleWorkItem(), this.getAsyncScheduleWorkItemType(), null, "asyncScheduleWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncScheduleWorkItemResponse(), this.getAsyncScheduleWorkItemResponseType(), null, "asyncScheduleWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncScheduleWorkItemWithModel(), this.getAsyncScheduleWorkItemWithModelType(), null, "asyncScheduleWorkItemWithModel", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncScheduleWorkItemWithModelResponse(), this.getAsyncScheduleWorkItemWithModelResponseType(), null, "asyncScheduleWorkItemWithModelResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncStartGroup(), this.getAsyncStartGroupType(), null, "asyncStartGroup", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncStartGroupResponse(), this.getAsyncStartGroupResponseType(), null, "asyncStartGroupResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncSuspendWorkItem(), this.getAsyncSuspendWorkItemType(), null, "asyncSuspendWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_AsyncSuspendWorkItemResponse(), this.getAsyncSuspendWorkItemResponseType(), null, "asyncSuspendWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_CancelWorkItem(), this.getCancelWorkItemType(), null, "cancelWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_CancelWorkItemResponse(), this.getCancelWorkItemResponseType(), null, "cancelWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ChainedWorkItemNotification(), this.getChainedWorkItemNotificationType(), null, "chainedWorkItemNotification", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_CloseWorkItem(), this.getCloseWorkItemType(), null, "closeWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_CloseWorkItemResponse(), this.getCloseWorkItemResponseType(), null, "closeWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_CompleteWorkItem(), this.getCompleteWorkItemType(), null, "completeWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_CompleteWorkItemResponse(), this.getCompleteWorkItemResponseType(), null, "completeWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_CreateWorkListView(), this.getWorkListViewEdit(), null, "createWorkListView", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_CreateWorkListViewResponse(), this.getCreateWorkListViewResponseType(), null, "createWorkListViewResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_DeleteCurrentResourceFromView(), this.getDeleteCurrentResourceFromViewType(), null, "deleteCurrentResourceFromView", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_DeleteCurrentResourceFromViewResponse(), this.getDeleteCurrentResourceFromViewResponseType(), null, "deleteCurrentResourceFromViewResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_DeleteOrgEntityConfigAttributes(), this.getDeleteOrgEntityConfigAttributesType(), null, "deleteOrgEntityConfigAttributes", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_DeleteOrgEntityConfigAttributesResponse(), this.getDeleteOrgEntityConfigAttributesResponseType(), null, "deleteOrgEntityConfigAttributesResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_DeleteWorkListView(), this.getDeleteWorkListViewType(), null, "deleteWorkListView", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_DeleteWorkListViewResponse(), this.getDeleteWorkListViewResponseType(), null, "deleteWorkListViewResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_EditWorkListView(), this.getEditWorkListViewType(), null, "editWorkListView", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_EditWorkListViewResponse(), this.getEditWorkListViewResponseType(), null, "editWorkListViewResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_EnableWorkItem(), this.getEnableWorkItemType(), null, "enableWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_EnableWorkItemResponse(), this.getEnableWorkItemResponseType(), null, "enableWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_EndGroup(), this.getEndGroupType(), null, "endGroup", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_EndGroupResponse(), this.getEndGroupResponseType(), null, "endGroupResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetAllocatedWorkListItems(), this.getGetAllocatedWorkListItemsType(), null, "getAllocatedWorkListItems", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetBatchGroupIds(), ecorePackage.getEObject(), null, "getBatchGroupIds", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetBatchGroupIdsResponse(), this.getGetBatchGroupIdsResponseType(), null, "getBatchGroupIdsResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetBatchWorkItemIds(), ecorePackage.getEObject(), null, "getBatchWorkItemIds", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetBatchWorkItemIdsResponse(), this.getGetBatchWorkItemIdsResponseType(), null, "getBatchWorkItemIdsResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetEditableWorkListViews(), this.getGetEditableWorkListViewsType(), null, "getEditableWorkListViews", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetEditableWorkListViewsResponse(), this.getGetEditableWorkListViewsResponseType(), null, "getEditableWorkListViewsResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetOfferSet(), this.getGetOfferSetType(), null, "getOfferSet", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetOfferSetResponse(), this.getGetOfferSetResponseType(), null, "getOfferSetResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetOrgEntityConfigAttributes(), this.getGetOrgEntityConfigAttributesType(), null, "getOrgEntityConfigAttributes", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetOrgEntityConfigAttributesAvailable(), this.getGetOrgEntityConfigAttributesAvailableType(), null, "getOrgEntityConfigAttributesAvailable", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetOrgEntityConfigAttributesAvailableResponse(), this.getGetOrgEntityConfigAttributesAvailableResponseType(), null, "getOrgEntityConfigAttributesAvailableResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetOrgEntityConfigAttributesResponse(), this.getGetOrgEntityConfigAttributesResponseType(), null, "getOrgEntityConfigAttributesResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetPublicWorkListViews(), this.getGetPublicWorkListViewsType(), null, "getPublicWorkListViews", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetPublicWorkListViewsResponse(), this.getGetPublicWorkListViewsResponseType(), null, "getPublicWorkListViewsResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetResourceOrderFilterCriteria(), this.getGetResourceOrderFilterCriteriaType(), null, "getResourceOrderFilterCriteria", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetResourceOrderFilterCriteriaResponse(), this.getGetResourceOrderFilterCriteriaResponseType(), null, "getResourceOrderFilterCriteriaResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetViewsForResource(), this.getGetViewsForResourceType(), null, "getViewsForResource", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetViewsForResourceResponse(), this.getGetViewsForResourceResponseType(), null, "getViewsForResourceResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkItemHeader(), this.getGetWorkItemHeaderType(), null, "getWorkItemHeader", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkItemHeaderResponse(), this.getGetWorkItemHeaderResponseType(), null, "getWorkItemHeaderResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkItemOrderFilter(), this.getGetWorkItemOrderFilterType(), null, "getWorkItemOrderFilter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkItemOrderFilterResponse(), this.getGetWorkItemOrderFilterResponseType(), null, "getWorkItemOrderFilterResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkListItems(), this.getGetWorkListItemsType(), null, "getWorkListItems", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkListItemsForGlobalData(), this.getGetWorkListItemsForGlobalDataType(), null, "getWorkListItemsForGlobalData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkListItemsForGlobalDataResponse(), this.getGetWorkListItemsForGlobalDataResponseType(), null, "getWorkListItemsForGlobalDataResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkListItemsForView(), this.getGetWorkListItemsForViewType(), null, "getWorkListItemsForView", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkListItemsForViewResponse(), this.getGetWorkListItemsForViewResponseType(), null, "getWorkListItemsForViewResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkListItemsResponse(), this.getGetWorkListItemsResponseType1(), null, "getWorkListItemsResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkListViewDetails(), this.getGetWorkListViewDetailsType(), null, "getWorkListViewDetails", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkListViewDetailsResponse(), this.getWorkListView(), null, "getWorkListViewDetailsResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkModel(), this.getGetWorkModelType(), null, "getWorkModel", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkModelResponse(), this.getGetWorkModelResponseType(), null, "getWorkModelResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkModels(), this.getGetWorkModelsType(), null, "getWorkModels", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkModelsResponse(), this.getGetWorkModelsResponseType(), null, "getWorkModelsResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkType(), this.getGetWorkTypeType(), null, "getWorkType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkTypeResponse(), this.getGetWorkTypeResponseType(), null, "getWorkTypeResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkTypes(), this.getGetWorkTypesType(), null, "getWorkTypes", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_GetWorkTypesResponse(), this.getGetWorkTypesResponseType(), null, "getWorkTypesResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_OnNotification(), this.getOnNotificationType(), null, "onNotification", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_OnNotificationResponse(), this.getOnNotificationResponseType(), null, "onNotificationResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_OpenWorkItem(), this.getOpenWorkItemType(), null, "openWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_OpenWorkItemResponse(), this.getOpenWorkItemResponseType(), null, "openWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_PendWorkItem(), this.getPendWorkItem(), null, "pendWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_PendWorkItemResponse(), this.getPendWorkItemResponseType(), null, "pendWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_PreviewWorkItemFromList(), this.getPreviewWorkItemFromListType(), null, "previewWorkItemFromList", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_PreviewWorkItemFromListResponse(), this.getPreviewWorkItemFromListResponseType(), null, "previewWorkItemFromListResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_PushNotification(), this.getPushNotificationType(), null, "pushNotification", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ReallocateWorkItem(), this.getReallocateWorkItemType(), null, "reallocateWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ReallocateWorkItemData(), this.getReallocateWorkItemData(), null, "reallocateWorkItemData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ReallocateWorkItemDataResponse(), this.getReallocateWorkItemDataResponseType(), null, "reallocateWorkItemDataResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ReallocateWorkItemResponse(), this.getReallocateWorkItemResponseType(), null, "reallocateWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_RescheduleWorkItem(), this.getRescheduleWorkItem(), null, "rescheduleWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_RescheduleWorkItemResponse(), this.getRescheduleWorkItemResponseType(), null, "rescheduleWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ResumeWorkItem(), this.getResumeWorkItemType(), null, "resumeWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ResumeWorkItemResponse(), this.getResumeWorkItemResponseType(), null, "resumeWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SaveOpenWorkItem(), this.getSaveOpenWorkItemType(), null, "saveOpenWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SaveOpenWorkItemResponse(), this.getSaveOpenWorkItemResponseType(), null, "saveOpenWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ScheduleWorkItem(), this.getScheduleWorkItemType(), null, "scheduleWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ScheduleWorkItemResponse(), this.getScheduleWorkItemResponseType(), null, "scheduleWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ScheduleWorkItemWithModel(), this.getScheduleWorkItemWithModelType(), null, "scheduleWorkItemWithModel", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ScheduleWorkItemWithModelResponse(), this.getScheduleWorkItemWithModelResponseType(), null, "scheduleWorkItemWithModelResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SetOrgEntityConfigAttributes(), this.getSetOrgEntityConfigAttributesType(), null, "setOrgEntityConfigAttributes", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SetOrgEntityConfigAttributesResponse(), this.getSetOrgEntityConfigAttributesResponseType(), null, "setOrgEntityConfigAttributesResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SetResourceOrderFilterCriteria(), this.getSetResourceOrderFilterCriteriaType(), null, "setResourceOrderFilterCriteria", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SetResourceOrderFilterCriteriaResponse(), this.getSetResourceOrderFilterCriteriaResponseType(), null, "setResourceOrderFilterCriteriaResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SetWorkItemPriority(), this.getSetWorkItemPriority(), null, "setWorkItemPriority", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SetWorkItemPriorityResponse(), this.getSetWorkItemPriorityResponseType(), null, "setWorkItemPriorityResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SkipWorkItem(), this.getSkipWorkItemType(), null, "skipWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SkipWorkItemResponse(), this.getSkipWorkItemResponseType(), null, "skipWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_StartGroup(), this.getStartGroupType(), null, "startGroup", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_StartGroupResponse(), this.getStartGroupResponseType(), null, "startGroupResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SuspendWorkItem(), this.getSuspendWorkItemType(), null, "suspendWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_SuspendWorkItemResponse(), this.getSuspendWorkItemResponseType(), null, "suspendWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_UnallocateWorkItem(), this.getUnallocateWorkItemType(), null, "unallocateWorkItem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_UnallocateWorkItemResponse(), this.getUnallocateWorkItemResponseType(), null, "unallocateWorkItemResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_UnlockWorkListView(), this.getUnlockWorkListViewType(), null, "unlockWorkListView", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_UnlockWorkListViewResponse(), this.getUnlockWorkListViewResponseType(), null, "unlockWorkListViewResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(editWorkListViewResponseTypeEClass, EditWorkListViewResponseType.class, "EditWorkListViewResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getEditWorkListViewResponseType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, EditWorkListViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(editWorkListViewTypeEClass, EditWorkListViewType.class, "EditWorkListViewType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getEditWorkListViewType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, EditWorkListViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(enableWorkItemResponseTypeEClass, EnableWorkItemResponseType.class, "EnableWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getEnableWorkItemResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, EnableWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(enableWorkItemTypeEClass, EnableWorkItemType.class, "EnableWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getEnableWorkItemType_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, 1, EnableWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getEnableWorkItemType_ItemBody(), this.getItemBody(), null, "itemBody", null, 1, 1, EnableWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(endGroupResponseTypeEClass, EndGroupResponseType.class, "EndGroupResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getEndGroupResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, EndGroupResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(endGroupTypeEClass, EndGroupType.class, "EndGroupType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getEndGroupType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 1, 1, EndGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getAllocatedWorkListItemsTypeEClass, GetAllocatedWorkListItemsType.class, "GetAllocatedWorkListItemsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetAllocatedWorkListItemsType_EntityID(), theOrganisationPackage.getXmlModelEntityId(), null, "entityID", null, 1, 1, GetAllocatedWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetAllocatedWorkListItemsType_OrderFilterCriteria(), this.getOrderFilterCriteria(), null, "orderFilterCriteria", null, 0, 1, GetAllocatedWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetAllocatedWorkListItemsType_GetTotalCount(), theXMLTypePackage.getBoolean(), "getTotalCount", "true", 0, 1, GetAllocatedWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetAllocatedWorkListItemsType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetAllocatedWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetAllocatedWorkListItemsType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetAllocatedWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getBatchGroupIdsResponseTypeEClass, GetBatchGroupIdsResponseType.class, "GetBatchGroupIdsResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetBatchGroupIdsResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, GetBatchGroupIdsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetBatchGroupIdsResponseType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 1, -1, GetBatchGroupIdsResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(getBatchWorkItemIdsResponseTypeEClass, GetBatchWorkItemIdsResponseType.class, "GetBatchWorkItemIdsResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetBatchWorkItemIdsResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, GetBatchWorkItemIdsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetBatchWorkItemIdsResponseType_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, -1, GetBatchWorkItemIdsResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(getEditableWorkListViewsResponseTypeEClass, GetEditableWorkListViewsResponseType.class, "GetEditableWorkListViewsResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetEditableWorkListViewsResponseType_WorkListViews(), this.getWorkListViewPageItem(), null, "workListViews", null, 0, -1, GetEditableWorkListViewsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getEditableWorkListViewsTypeEClass, GetEditableWorkListViewsType.class, "GetEditableWorkListViewsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetEditableWorkListViewsType_ApiVersion(), theXMLTypePackage.getInt(), "apiVersion", null, 0, 1, GetEditableWorkListViewsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetEditableWorkListViewsType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetEditableWorkListViewsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetEditableWorkListViewsType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetEditableWorkListViewsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getOfferSetResponseTypeEClass, GetOfferSetResponseType.class, "GetOfferSetResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetOfferSetResponseType_EntityGuid(), theXMLTypePackage.getString(), "entityGuid", null, 0, -1, GetOfferSetResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetOfferSetResponseType_EntityId(), theOrganisationPackage.getXmlModelEntityId(), null, "entityId", null, 0, -1, GetOfferSetResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getOfferSetTypeEClass, GetOfferSetType.class, "GetOfferSetType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetOfferSetType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, 1, GetOfferSetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetOfferSetType_ApiVersion(), theXMLTypePackage.getInt(), "apiVersion", null, 0, 1, GetOfferSetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getOrgEntityConfigAttributesAvailableResponseTypeEClass, GetOrgEntityConfigAttributesAvailableResponseType.class, "GetOrgEntityConfigAttributesAvailableResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetOrgEntityConfigAttributesAvailableResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, GetOrgEntityConfigAttributesAvailableResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetOrgEntityConfigAttributesAvailableResponseType_OrgEntityConfigAttributesAvailable(), this.getOrgEntityConfigAttributesAvailable(), null, "orgEntityConfigAttributesAvailable", null, 1, -1, GetOrgEntityConfigAttributesAvailableResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(getOrgEntityConfigAttributesAvailableTypeEClass, GetOrgEntityConfigAttributesAvailableType.class, "GetOrgEntityConfigAttributesAvailableType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetOrgEntityConfigAttributesAvailableType_StartAt(), theXMLTypePackage.getInt(), "startAt", null, 1, 1, GetOrgEntityConfigAttributesAvailableType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetOrgEntityConfigAttributesAvailableType_NumToReturn(), theXMLTypePackage.getInt(), "numToReturn", null, 1, 1, GetOrgEntityConfigAttributesAvailableType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getOrgEntityConfigAttributesResponseTypeEClass, GetOrgEntityConfigAttributesResponseType.class, "GetOrgEntityConfigAttributesResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetOrgEntityConfigAttributesResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, GetOrgEntityConfigAttributesResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetOrgEntityConfigAttributesResponseType_OrgEntityConfigAttribute(), this.getOrgEntityConfigAttribute(), null, "orgEntityConfigAttribute", null, 1, -1, GetOrgEntityConfigAttributesResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(getOrgEntityConfigAttributesTypeEClass, GetOrgEntityConfigAttributesType.class, "GetOrgEntityConfigAttributesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetOrgEntityConfigAttributesType_Resource(), theXMLTypePackage.getString(), "resource", null, 1, 1, GetOrgEntityConfigAttributesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getPublicWorkListViewsResponseTypeEClass, GetPublicWorkListViewsResponseType.class, "GetPublicWorkListViewsResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetPublicWorkListViewsResponseType_WorkListViews(), this.getWorkListViewPageItem(), null, "workListViews", null, 0, -1, GetPublicWorkListViewsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getPublicWorkListViewsTypeEClass, GetPublicWorkListViewsType.class, "GetPublicWorkListViewsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetPublicWorkListViewsType_ApiVersion(), theXMLTypePackage.getInt(), "apiVersion", null, 0, 1, GetPublicWorkListViewsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetPublicWorkListViewsType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetPublicWorkListViewsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetPublicWorkListViewsType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetPublicWorkListViewsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getResourceOrderFilterCriteriaResponseTypeEClass, GetResourceOrderFilterCriteriaResponseType.class, "GetResourceOrderFilterCriteriaResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetResourceOrderFilterCriteriaResponseType_OrderFilterCriteria(), this.getOrderFilterCriteria(), null, "orderFilterCriteria", null, 1, 1, GetResourceOrderFilterCriteriaResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getResourceOrderFilterCriteriaTypeEClass, GetResourceOrderFilterCriteriaType.class, "GetResourceOrderFilterCriteriaType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetResourceOrderFilterCriteriaType_ResourceID(), theXMLTypePackage.getString(), "resourceID", null, 1, 1, GetResourceOrderFilterCriteriaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getViewsForResourceResponseTypeEClass, GetViewsForResourceResponseType.class, "GetViewsForResourceResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetViewsForResourceResponseType_WorkListViews(), this.getWorkListViewPageItem(), null, "workListViews", null, 0, -1, GetViewsForResourceResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getViewsForResourceTypeEClass, GetViewsForResourceType.class, "GetViewsForResourceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetViewsForResourceType_ApiVersion(), theXMLTypePackage.getInt(), "apiVersion", null, 0, 1, GetViewsForResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetViewsForResourceType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetViewsForResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetViewsForResourceType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetViewsForResourceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkItemHeaderResponseTypeEClass, GetWorkItemHeaderResponseType.class, "GetWorkItemHeaderResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkItemHeaderResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, GetWorkItemHeaderResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkItemHeaderResponseType_WorkItemHeader(), this.getWorkItemHeader(), null, "workItemHeader", null, 1, -1, GetWorkItemHeaderResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(getWorkItemHeaderTypeEClass, GetWorkItemHeaderType.class, "GetWorkItemHeaderType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkItemHeaderType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, GetWorkItemHeaderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkItemHeaderType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, GetWorkItemHeaderType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(getWorkItemOrderFilterResponseTypeEClass, GetWorkItemOrderFilterResponseType.class, "GetWorkItemOrderFilterResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetWorkItemOrderFilterResponseType_ColumnData(), this.getColumnDefinition(), null, "columnData", null, 0, -1, GetWorkItemOrderFilterResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkItemOrderFilterTypeEClass, GetWorkItemOrderFilterType.class, "GetWorkItemOrderFilterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkItemOrderFilterType_LimitColumns(), theXMLTypePackage.getShort(), "limitColumns", null, 1, 1, GetWorkItemOrderFilterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkListItemsForGlobalDataResponseTypeEClass, GetWorkListItemsForGlobalDataResponseType.class, "GetWorkListItemsForGlobalDataResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkListItemsForGlobalDataResponseType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkListItemsForGlobalDataResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForGlobalDataResponseType_EndPosition(), theXMLTypePackage.getLong(), "endPosition", null, 1, 1, GetWorkListItemsForGlobalDataResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkListItemsForGlobalDataResponseType_WorkItems(), this.getWorkItem(), null, "workItems", null, 0, -1, GetWorkListItemsForGlobalDataResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkListItemsForGlobalDataTypeEClass, GetWorkListItemsForGlobalDataType.class, "GetWorkListItemsForGlobalDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkListItemsForGlobalDataType_GlobalDataRef(), theXMLTypePackage.getString(), "globalDataRef", null, 1, -1, GetWorkListItemsForGlobalDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkListItemsForGlobalDataType_OrderFilterCriteria(), this.getOrderFilterCriteria(), null, "orderFilterCriteria", null, 0, 1, GetWorkListItemsForGlobalDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForGlobalDataType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetWorkListItemsForGlobalDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForGlobalDataType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkListItemsForGlobalDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkListItemsForViewResponseTypeEClass, GetWorkListItemsForViewResponseType.class, "GetWorkListItemsForViewResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkListItemsForViewResponseType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkListItemsForViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForViewResponseType_EndPosition(), theXMLTypePackage.getLong(), "endPosition", null, 1, 1, GetWorkListItemsForViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForViewResponseType_TotalItems(), theXMLTypePackage.getLong(), "totalItems", null, 1, 1, GetWorkListItemsForViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkListItemsForViewResponseType_WorkItems(), this.getWorkItem(), null, "workItems", null, 0, -1, GetWorkListItemsForViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForViewResponseType_CustomData(), theXMLTypePackage.getString(), "customData", null, 0, 1, GetWorkListItemsForViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkListItemsForViewTypeEClass, GetWorkListItemsForViewType.class, "GetWorkListItemsForViewType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkListItemsForViewType_GetAllocatedItems(), theXMLTypePackage.getBoolean(), "getAllocatedItems", null, 0, 1, GetWorkListItemsForViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForViewType_GetTotalCount(), theXMLTypePackage.getBoolean(), "getTotalCount", "true", 0, 1, GetWorkListItemsForViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForViewType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetWorkListItemsForViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForViewType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkListItemsForViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsForViewType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, GetWorkListItemsForViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkListItemsResponseTypeEClass, GetWorkListItemsResponseType.class, "GetWorkListItemsResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkListItemsResponseType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkListItemsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsResponseType_EndPosition(), theXMLTypePackage.getLong(), "endPosition", null, 1, 1, GetWorkListItemsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsResponseType_TotalItems(), theXMLTypePackage.getLong(), "totalItems", null, 1, 1, GetWorkListItemsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkListItemsResponseType_WorkItems(), this.getWorkItem(), null, "workItems", null, 0, -1, GetWorkListItemsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkListItemsResponseType1EClass, GetWorkListItemsResponseType1.class, "GetWorkListItemsResponseType1", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkListItemsResponseType1_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkListItemsResponseType1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsResponseType1_EndPosition(), theXMLTypePackage.getLong(), "endPosition", null, 1, 1, GetWorkListItemsResponseType1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsResponseType1_TotalItems(), theXMLTypePackage.getLong(), "totalItems", null, 1, 1, GetWorkListItemsResponseType1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkListItemsResponseType1_WorkItems(), this.getWorkItem(), null, "workItems", null, 0, -1, GetWorkListItemsResponseType1.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkListItemsTypeEClass, GetWorkListItemsType.class, "GetWorkListItemsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkListItemsType_ResourcesRequired(), this.getResourcesRequiredType(), "resourcesRequired", null, 0, 1, GetWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkListItemsType_EntityID(), theOrganisationPackage.getXmlModelEntityId(), null, "entityID", null, 0, 1, GetWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGetWorkListItemsType_OrderFilterCriteria(), this.getOrderFilterCriteria(), null, "orderFilterCriteria", null, 0, 1, GetWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsType_GetTotalCount(), theXMLTypePackage.getBoolean(), "getTotalCount", "true", 0, 1, GetWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListItemsType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkListItemsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkListViewDetailsTypeEClass, GetWorkListViewDetailsType.class, "GetWorkListViewDetailsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkListViewDetailsType_ApiVersion(), theXMLTypePackage.getInt(), "apiVersion", null, 0, 1, GetWorkListViewDetailsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListViewDetailsType_LockView(), theXMLTypePackage.getBoolean(), "lockView", null, 0, 1, GetWorkListViewDetailsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkListViewDetailsType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, GetWorkListViewDetailsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkModelResponseTypeEClass, GetWorkModelResponseType.class, "GetWorkModelResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetWorkModelResponseType_WorkModel(), this.getWorkModel(), null, "workModel", null, 1, 1, GetWorkModelResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkModelsResponseTypeEClass, GetWorkModelsResponseType.class, "GetWorkModelsResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetWorkModelsResponseType_WorkModelList(), this.getWorkModelList(), null, "workModelList", null, 1, 1, GetWorkModelsResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkModelsTypeEClass, GetWorkModelsType.class, "GetWorkModelsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkModelsType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkModelsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkModelsType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetWorkModelsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkModelTypeEClass, GetWorkModelType.class, "GetWorkModelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkModelType_WorkModelID(), theXMLTypePackage.getString(), "workModelID", null, 1, 1, GetWorkModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkTypeResponseTypeEClass, GetWorkTypeResponseType.class, "GetWorkTypeResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetWorkTypeResponseType_WorkType(), theDatamodelPackage.getWorkType(), null, "workType", null, 1, 1, GetWorkTypeResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkTypesResponseTypeEClass, GetWorkTypesResponseType.class, "GetWorkTypesResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGetWorkTypesResponseType_WorkTypelList(), this.getWorkTypeList(), null, "workTypelList", null, 1, 1, GetWorkTypesResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkTypesTypeEClass, GetWorkTypesType.class, "GetWorkTypesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkTypesType_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, GetWorkTypesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGetWorkTypesType_NumberOfItems(), theXMLTypePackage.getLong(), "numberOfItems", null, 1, 1, GetWorkTypesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(getWorkTypeTypeEClass, GetWorkTypeType.class, "GetWorkTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getGetWorkTypeType_WorkTypeID(), theXMLTypePackage.getString(), "workTypeID", null, 1, 1, GetWorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(hiddenPeriodEClass, HiddenPeriod.class, "HiddenPeriod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getHiddenPeriod_HiddenDuration(), this.getItemDuration(), null, "hiddenDuration", null, 0, 1, HiddenPeriod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHiddenPeriod_VisibleDate(), theXMLTypePackage.getDateTime(), "visibleDate", null, 0, 1, HiddenPeriod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(itemEClass, Item.class, "Item", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getItem_Entities(), theOrganisationPackage.getXmlModelEntityId(), null, "entities", null, 0, -1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getItem_EntityQuery(), theOrganisationPackage.getXmlResourceQuery(), null, "entityQuery", null, 0, 1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItem_WorkTypeUID(), theXMLTypePackage.getString(), "workTypeUID", null, 1, 1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItem_WorkTypeVersion(), theXMLTypePackage.getString(), "workTypeVersion", null, 0, 1, Item.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(itemBodyEClass, ItemBody.class, "ItemBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getItemBody_Parameter(), this.getParameterType(), null, "parameter", null, 0, -1, ItemBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(itemContextEClass, ItemContext.class, "ItemContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getItemContext_ActivityID(), theXMLTypePackage.getString(), "activityID", null, 1, 1, ItemContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemContext_ActivityName(), theXMLTypePackage.getString(), "activityName", null, 1, 1, ItemContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemContext_AppInstance(), theXMLTypePackage.getString(), "appInstance", null, 1, 1, ItemContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemContext_AppName(), theXMLTypePackage.getString(), "appName", null, 1, 1, ItemContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemContext_AppID(), theXMLTypePackage.getString(), "appID", null, 1, 1, ItemContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemContext_AppInstanceDescription(), theXMLTypePackage.getString(), "appInstanceDescription", null, 0, 1, ItemContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(itemDurationEClass, ItemDuration.class, "ItemDuration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getItemDuration_Days(), theXMLTypePackage.getInt(), "days", "0", 0, 1, ItemDuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemDuration_Hours(), theXMLTypePackage.getInt(), "hours", "0", 0, 1, ItemDuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemDuration_Minutes(), theXMLTypePackage.getInt(), "minutes", "0", 0, 1, ItemDuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemDuration_Months(), theXMLTypePackage.getInt(), "months", "0", 0, 1, ItemDuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemDuration_Weeks(), theXMLTypePackage.getInt(), "weeks", "0", 0, 1, ItemDuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemDuration_Years(), theXMLTypePackage.getInt(), "years", "0", 0, 1, ItemDuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(itemPrivilegeEClass, ItemPrivilege.class, "ItemPrivilege", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getItemPrivilege_Suspend(), this.getPrivilege(), null, "suspend", null, 0, -1, ItemPrivilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getItemPrivilege_StatelessRealloc(), this.getPrivilege(), null, "statelessRealloc", null, 0, -1, ItemPrivilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getItemPrivilege_StatefulRealloc(), this.getPrivilege(), null, "statefulRealloc", null, 0, -1, ItemPrivilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getItemPrivilege_Dellocate(), this.getPrivilege(), null, "dellocate", null, 0, -1, ItemPrivilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getItemPrivilege_Delegate(), this.getPrivilege(), null, "delegate", null, 0, -1, ItemPrivilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getItemPrivilege_Skip(), this.getPrivilege(), null, "skip", null, 0, -1, ItemPrivilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getItemPrivilege_Piling(), this.getPrivilege(), null, "piling", null, 0, -1, ItemPrivilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(itemScheduleEClass, ItemSchedule.class, "ItemSchedule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getItemSchedule_StartDate(), theXMLTypePackage.getDateTime(), "startDate", null, 0, 1, ItemSchedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getItemSchedule_MaxDuration(), this.getItemDuration(), null, "maxDuration", null, 0, 1, ItemSchedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getItemSchedule_TargetDate(), theXMLTypePackage.getDateTime(), "targetDate", null, 0, 1, ItemSchedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(managedObjectIDEClass, ManagedObjectID.class, "ManagedObjectID", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getManagedObjectID_Version(), theXMLTypePackage.getLong(), "version", null, 0, 1, ManagedObjectID.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(nextWorkItemEClass, NextWorkItem.class, "NextWorkItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getNextWorkItem_NextItem(), this.getManagedObjectID(), null, "nextItem", null, 0, 1, NextWorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(objectIDEClass, ObjectID.class, "ObjectID", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getObjectID_Id(), theXMLTypePackage.getLong(), "id", null, 1, 1, ObjectID.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(onNotificationResponseTypeEClass, OnNotificationResponseType.class, "OnNotificationResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOnNotificationResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, OnNotificationResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(onNotificationTypeEClass, OnNotificationType.class, "OnNotificationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getOnNotificationType_MessageDetails(), this.getAsyncWorkItemDetails(), null, "messageDetails", null, 1, 1, OnNotificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOnNotificationType_ItemBody(), this.getItemBody(), null, "itemBody", null, 1, 1, OnNotificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOnNotificationType_AllocationHistory(), this.getAllocationHistory(), null, "allocationHistory", null, 1, -1, OnNotificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(openWorkItemResponseTypeEClass, OpenWorkItemResponseType.class, "OpenWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOpenWorkItemResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, OpenWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOpenWorkItemResponseType_WorkItemBody(), this.getWorkItemBody(), null, "workItemBody", null, 1, -1, OpenWorkItemResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(openWorkItemTypeEClass, OpenWorkItemType.class, "OpenWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOpenWorkItemType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, OpenWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getOpenWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, OpenWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(orderFilterCriteriaEClass, OrderFilterCriteria.class, "OrderFilterCriteria", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOrderFilterCriteria_Order(), theXMLTypePackage.getString(), "order", null, 0, 1, OrderFilterCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrderFilterCriteria_Filter(), theXMLTypePackage.getString(), "filter", null, 0, 1, OrderFilterCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(orgEntityConfigAttributeEClass, OrgEntityConfigAttribute.class, "OrgEntityConfigAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOrgEntityConfigAttribute_AttributeName(), theXMLTypePackage.getString(), "attributeName", null, 1, 1, OrgEntityConfigAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrgEntityConfigAttribute_AttributeValue(), theXMLTypePackage.getString(), "attributeValue", null, 1, 1, OrgEntityConfigAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrgEntityConfigAttribute_ReadOnly(), theXMLTypePackage.getBoolean(), "readOnly", null, 1, 1, OrgEntityConfigAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(orgEntityConfigAttributesAvailableEClass, OrgEntityConfigAttributesAvailable.class, "OrgEntityConfigAttributesAvailable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOrgEntityConfigAttributesAvailable_AttributeName(), theXMLTypePackage.getString(), "attributeName", null, 1, 1, OrgEntityConfigAttributesAvailable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrgEntityConfigAttributesAvailable_ReadOnly(), theXMLTypePackage.getBoolean(), "readOnly", null, 1, 1, OrgEntityConfigAttributesAvailable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(orgEntityConfigAttributeSetEClass, OrgEntityConfigAttributeSet.class, "OrgEntityConfigAttributeSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOrgEntityConfigAttributeSet_AttributeName(), theXMLTypePackage.getString(), "attributeName", null, 1, 1, OrgEntityConfigAttributeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOrgEntityConfigAttributeSet_AttributeValue(), theXMLTypePackage.getString(), "attributeValue", null, 1, 1, OrgEntityConfigAttributeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(parameterTypeEClass, ParameterType.class, "ParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getParameterType_ComplexValue(), ecorePackage.getEObject(), null, "complexValue", null, 0, -1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameterType_Value(), theXMLTypePackage.getString(), "value", null, 0, -1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameterType_Array(), theXMLTypePackage.getBoolean(), "array", null, 0, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameterType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(pendWorkItemEClass, PendWorkItem.class, "PendWorkItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPendWorkItem_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, PendWorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPendWorkItem_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, PendWorkItem.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getPendWorkItem_HiddenPeriod(), this.getHiddenPeriod(), null, "hiddenPeriod", null, 1, -1, PendWorkItem.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(pendWorkItemResponseTypeEClass, PendWorkItemResponseType.class, "PendWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPendWorkItemResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, PendWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPendWorkItemResponseType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, PendWorkItemResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(previewWorkItemFromListResponseTypeEClass, PreviewWorkItemFromListResponseType.class, "PreviewWorkItemFromListResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPreviewWorkItemFromListResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, PreviewWorkItemFromListResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPreviewWorkItemFromListResponseType_WorkItemPreview(), this.getWorkItemPreview(), null, "workItemPreview", null, 1, -1, PreviewWorkItemFromListResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(previewWorkItemFromListTypeEClass, PreviewWorkItemFromListType.class, "PreviewWorkItemFromListType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPreviewWorkItemFromListType_EntityID(), theOrganisationPackage.getXmlModelEntityId(), null, "entityID", null, 1, 1, PreviewWorkItemFromListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPreviewWorkItemFromListType_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, -1, PreviewWorkItemFromListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPreviewWorkItemFromListType_RequiredField(), theXMLTypePackage.getString(), "requiredField", null, 0, -1, PreviewWorkItemFromListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPreviewWorkItemFromListType_RequireWorkType(), theXMLTypePackage.getBoolean(), "requireWorkType", null, 0, 1, PreviewWorkItemFromListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(privilegeEClass, Privilege.class, "Privilege", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPrivilege_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Privilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPrivilege_Qualifier(), theXMLTypePackage.getString(), "qualifier", null, 0, 1, Privilege.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(pushNotificationTypeEClass, PushNotificationType.class, "PushNotificationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPushNotificationType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, 1, PushNotificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPushNotificationType_WorkTypeID(), theDatamodelPackage.getWorkTypeSpec(), null, "workTypeID", null, 1, 1, PushNotificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPushNotificationType_ResourceIDs(), theOrganisationPackage.getXmlModelEntityId(), null, "resourceIDs", null, 0, -1, PushNotificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(reallocateWorkItemDataEClass, ReallocateWorkItemData.class, "ReallocateWorkItemData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getReallocateWorkItemData_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, ReallocateWorkItemData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getReallocateWorkItemData_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, ReallocateWorkItemData.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getReallocateWorkItemData_Resource(), theXMLTypePackage.getString(), "resource", null, 1, -1, ReallocateWorkItemData.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getReallocateWorkItemData_WorkItemPayload(), this.getWorkItemBody(), null, "workItemPayload", null, 1, -1, ReallocateWorkItemData.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(reallocateWorkItemDataResponseTypeEClass, ReallocateWorkItemDataResponseType.class, "ReallocateWorkItemDataResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getReallocateWorkItemDataResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, ReallocateWorkItemDataResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getReallocateWorkItemDataResponseType_WorkItem(), this.getWorkItem(), null, "workItem", null, 1, -1, ReallocateWorkItemDataResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(reallocateWorkItemResponseTypeEClass, ReallocateWorkItemResponseType.class, "ReallocateWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getReallocateWorkItemResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, ReallocateWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getReallocateWorkItemResponseType_WorkItem(), this.getWorkItem(), null, "workItem", null, 1, -1, ReallocateWorkItemResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(reallocateWorkItemTypeEClass, ReallocateWorkItemType.class, "ReallocateWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getReallocateWorkItemType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, ReallocateWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getReallocateWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, ReallocateWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getReallocateWorkItemType_Resource(), theXMLTypePackage.getString(), "resource", null, 1, -1, ReallocateWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getReallocateWorkItemType_RevertData(), theXMLTypePackage.getBoolean(), "revertData", null, 1, -1, ReallocateWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(rescheduleWorkItemEClass, RescheduleWorkItem.class, "RescheduleWorkItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRescheduleWorkItem_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, 1, RescheduleWorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getRescheduleWorkItem_ItemSchedule(), this.getItemSchedule(), null, "itemSchedule", null, 0, 1, RescheduleWorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getRescheduleWorkItem_ItemBody(), this.getItemBody(), null, "itemBody", null, 0, 1, RescheduleWorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(rescheduleWorkItemResponseTypeEClass, RescheduleWorkItemResponseType.class, "RescheduleWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRescheduleWorkItemResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, RescheduleWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(resumeWorkItemResponseTypeEClass, ResumeWorkItemResponseType.class, "ResumeWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getResumeWorkItemResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, ResumeWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(resumeWorkItemTypeEClass, ResumeWorkItemType.class, "ResumeWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getResumeWorkItemType_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, 1, ResumeWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(saveOpenWorkItemResponseTypeEClass, SaveOpenWorkItemResponseType.class, "SaveOpenWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSaveOpenWorkItemResponseType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, 1, SaveOpenWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(saveOpenWorkItemTypeEClass, SaveOpenWorkItemType.class, "SaveOpenWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSaveOpenWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, 1, SaveOpenWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSaveOpenWorkItemType_WorkItemPayload(), this.getWorkItemBody(), null, "workItemPayload", null, 1, 1, SaveOpenWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(scheduleWorkItemResponseTypeEClass, ScheduleWorkItemResponseType.class, "ScheduleWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getScheduleWorkItemResponseType_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, 1, ScheduleWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(scheduleWorkItemTypeEClass, ScheduleWorkItemType.class, "ScheduleWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getScheduleWorkItemType_Item(), this.getItem(), null, "item", null, 1, 1, ScheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScheduleWorkItemType_ItemSchedule(), this.getItemSchedule(), null, "itemSchedule", null, 0, 1, ScheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScheduleWorkItemType_ItemContext(), this.getItemContext(), null, "itemContext", null, 1, 1, ScheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScheduleWorkItemType_ItemBody(), this.getItemBody(), null, "itemBody", null, 1, 1, ScheduleWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(scheduleWorkItemWithModelResponseTypeEClass, ScheduleWorkItemWithModelResponseType.class, "ScheduleWorkItemWithModelResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getScheduleWorkItemWithModelResponseType_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, 1, ScheduleWorkItemWithModelResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(scheduleWorkItemWithModelTypeEClass, ScheduleWorkItemWithModelType.class, "ScheduleWorkItemWithModelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getScheduleWorkItemWithModelType_ItemSchedule(), this.getItemSchedule(), null, "itemSchedule", null, 0, 1, ScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScheduleWorkItemWithModelType_ItemContext(), this.getItemContext(), null, "itemContext", null, 1, 1, ScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScheduleWorkItemWithModelType_ItemBody(), this.getItemBody(), null, "itemBody", null, 1, 1, ScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScheduleWorkItemWithModelType_EntityQuery(), theOrganisationPackage.getXmlResourceQuery(), null, "entityQuery", null, 0, 1, ScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getScheduleWorkItemWithModelType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 0, 1, ScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getScheduleWorkItemWithModelType_WorkModelUID(), theXMLTypePackage.getString(), "workModelUID", null, 1, 1, ScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getScheduleWorkItemWithModelType_WorkModelVersion(), theXMLTypePackage.getString(), "workModelVersion", null, 0, 1, ScheduleWorkItemWithModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(setOrgEntityConfigAttributesResponseTypeEClass, SetOrgEntityConfigAttributesResponseType.class, "SetOrgEntityConfigAttributesResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSetOrgEntityConfigAttributesResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, SetOrgEntityConfigAttributesResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(setOrgEntityConfigAttributesTypeEClass, SetOrgEntityConfigAttributesType.class, "SetOrgEntityConfigAttributesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSetOrgEntityConfigAttributesType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, SetOrgEntityConfigAttributesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSetOrgEntityConfigAttributesType_OrgEntityConfigAttributeSet(), this.getOrgEntityConfigAttributeSet(), null, "orgEntityConfigAttributeSet", null, 1, -1, SetOrgEntityConfigAttributesType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getSetOrgEntityConfigAttributesType_Resource(), theXMLTypePackage.getString(), "resource", null, 1, 1, SetOrgEntityConfigAttributesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(setResourceOrderFilterCriteriaResponseTypeEClass, SetResourceOrderFilterCriteriaResponseType.class, "SetResourceOrderFilterCriteriaResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSetResourceOrderFilterCriteriaResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, SetResourceOrderFilterCriteriaResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(setResourceOrderFilterCriteriaTypeEClass, SetResourceOrderFilterCriteriaType.class, "SetResourceOrderFilterCriteriaType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSetResourceOrderFilterCriteriaType_OrderFilterCriteria(), this.getOrderFilterCriteria(), null, "orderFilterCriteria", null, 1, 1, SetResourceOrderFilterCriteriaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSetResourceOrderFilterCriteriaType_ResourceID(), theXMLTypePackage.getString(), "resourceID", null, 0, 1, SetResourceOrderFilterCriteriaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(setWorkItemPriorityEClass, SetWorkItemPriority.class, "SetWorkItemPriority", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSetWorkItemPriority_WorkItemIDandPriority(), this.getWorkItemIDandPriorityType(), null, "workItemIDandPriority", null, 1, -1, SetWorkItemPriority.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(setWorkItemPriorityResponseTypeEClass, SetWorkItemPriorityResponseType.class, "SetWorkItemPriorityResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSetWorkItemPriorityResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, SetWorkItemPriorityResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSetWorkItemPriorityResponseType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, SetWorkItemPriorityResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(skipWorkItemResponseTypeEClass, SkipWorkItemResponseType.class, "SkipWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSkipWorkItemResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, SkipWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(skipWorkItemTypeEClass, SkipWorkItemType.class, "SkipWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSkipWorkItemType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, SkipWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSkipWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, SkipWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(startGroupResponseTypeEClass, StartGroupResponseType.class, "StartGroupResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getStartGroupResponseType_GroupID(), theXMLTypePackage.getLong(), "groupID", null, 1, 1, StartGroupResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(startGroupTypeEClass, StartGroupType.class, "StartGroupType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getStartGroupType_GroupType(), this.getWorkGroupType(), "groupType", null, 1, 1, StartGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStartGroupType_Description(), theXMLTypePackage.getString(), "description", null, 1, 1, StartGroupType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(suspendWorkItemResponseTypeEClass, SuspendWorkItemResponseType.class, "SuspendWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSuspendWorkItemResponseType_Success(), theXMLTypePackage.getBoolean(), "success", null, 1, 1, SuspendWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(suspendWorkItemTypeEClass, SuspendWorkItemType.class, "SuspendWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSuspendWorkItemType_WorkItemID(), this.getObjectID(), null, "workItemID", null, 1, 1, SuspendWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSuspendWorkItemType_ForceSuspend(), theXMLTypePackage.getBoolean(), "forceSuspend", "false", 0, 1, SuspendWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(unallocateWorkItemResponseTypeEClass, UnallocateWorkItemResponseType.class, "UnallocateWorkItemResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUnallocateWorkItemResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, UnallocateWorkItemResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getUnallocateWorkItemResponseType_WorkItem(), this.getWorkItem(), null, "workItem", null, 1, -1, UnallocateWorkItemResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(unallocateWorkItemTypeEClass, UnallocateWorkItemType.class, "UnallocateWorkItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUnallocateWorkItemType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, UnallocateWorkItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getUnallocateWorkItemType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, -1, UnallocateWorkItemType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(unlockWorkListViewResponseTypeEClass, UnlockWorkListViewResponseType.class, "UnlockWorkListViewResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUnlockWorkListViewResponseType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, UnlockWorkListViewResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(unlockWorkListViewTypeEClass, UnlockWorkListViewType.class, "UnlockWorkListViewType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUnlockWorkListViewType_WorkListViewID(), theXMLTypePackage.getLong(), "workListViewID", null, 1, 1, UnlockWorkListViewType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workItemEClass, WorkItem.class, "WorkItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkItem_Id(), this.getManagedObjectID(), null, "id", null, 1, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkItem_Header(), this.getWorkItemHeader(), null, "header", null, 1, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkItem_Attributes(), this.getWorkItemAttributes(), null, "attributes", null, 0, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkItem_Body(), this.getWorkItemBody(), null, "body", null, 0, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkItem_WorkType(), theDatamodelPackage.getWorkTypeSpec(), null, "workType", null, 0, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItem_State(), this.getWorkItemState(), "state", null, 1, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItem_Visible(), theXMLTypePackage.getBoolean(), "visible", null, 1, 1, WorkItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workItemAttributesEClass, WorkItemAttributes.class, "WorkItemAttributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkItemAttributes_Attribute1(), theXMLTypePackage.getLong(), "attribute1", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute10(), this.getAttribute10Type(), "attribute10", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute11(), this.getAttribute11Type(), "attribute11", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute12(), this.getAttribute12Type(), "attribute12", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute13(), this.getAttribute13Type(), "attribute13", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute14(), this.getAttribute14Type(), "attribute14", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute15(), theXMLTypePackage.getLong(), "attribute15", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute16(), theXMLTypePackage.getDecimal(), "attribute16", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute17(), theXMLTypePackage.getDecimal(), "attribute17", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute18(), theXMLTypePackage.getDecimal(), "attribute18", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute19(), theXMLTypePackage.getDateTime(), "attribute19", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute2(), this.getAttribute2Type(), "attribute2", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute20(), theXMLTypePackage.getDateTime(), "attribute20", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute21(), this.getAttribute21Type(), "attribute21", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute22(), this.getAttribute22Type(), "attribute22", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute23(), this.getAttribute23Type(), "attribute23", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute24(), this.getAttribute24Type(), "attribute24", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute25(), this.getAttribute25Type(), "attribute25", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute26(), this.getAttribute26Type(), "attribute26", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute27(), this.getAttribute27Type(), "attribute27", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute28(), this.getAttribute28Type(), "attribute28", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute29(), this.getAttribute29Type(), "attribute29", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute3(), this.getAttribute3Type(), "attribute3", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute30(), this.getAttribute30Type(), "attribute30", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute31(), this.getAttribute31Type(), "attribute31", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute32(), this.getAttribute32Type(), "attribute32", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute33(), this.getAttribute33Type(), "attribute33", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute34(), this.getAttribute34Type(), "attribute34", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute35(), this.getAttribute35Type(), "attribute35", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute36(), this.getAttribute36Type(), "attribute36", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute37(), this.getAttribute37Type(), "attribute37", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute38(), this.getAttribute38Type(), "attribute38", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute39(), this.getAttribute39Type(), "attribute39", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute4(), this.getAttribute4Type(), "attribute4", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute40(), this.getAttribute40Type(), "attribute40", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute5(), theXMLTypePackage.getDecimal(), "attribute5", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute6(), theXMLTypePackage.getDateTime(), "attribute6", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute7(), theXMLTypePackage.getDateTime(), "attribute7", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute8(), this.getAttribute8Type(), "attribute8", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemAttributes_Attribute9(), this.getAttribute9Type(), "attribute9", null, 0, 1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workItemBodyEClass, WorkItemBody.class, "WorkItemBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkItemBody_DataModel(), theDatamodelPackage.getDataModel(), null, "dataModel", null, 1, 1, WorkItemBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workItemFlagsEClass, WorkItemFlags.class, "WorkItemFlags", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkItemFlags_ScheduleStatus(), this.getScheduleStatus(), "scheduleStatus", null, 1, 1, WorkItemFlags.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workItemHeaderEClass, WorkItemHeader.class, "WorkItemHeader", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkItemHeader_Flags(), this.getWorkItemFlags(), null, "flags", null, 1, 1, WorkItemHeader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkItemHeader_ItemContext(), this.getItemContext(), null, "itemContext", null, 1, 1, WorkItemHeader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemHeader_EndDate(), theXMLTypePackage.getDateTime(), "endDate", null, 0, 1, WorkItemHeader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemHeader_StartDate(), theXMLTypePackage.getDateTime(), "startDate", null, 0, 1, WorkItemHeader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workItemIDandPriorityTypeEClass, WorkItemIDandPriorityType.class, "WorkItemIDandPriorityType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkItemIDandPriorityType_WorkItemID(), this.getManagedObjectID(), null, "workItemID", null, 1, 1, WorkItemIDandPriorityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkItemIDandPriorityType_WorkItemPriority(), this.getWorkItemPriorityType(), null, "workItemPriority", null, 1, 1, WorkItemIDandPriorityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workItemPreviewEClass, WorkItemPreview.class, "WorkItemPreview", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkItemPreview_FieldPreview(), theDatamodelPackage.getFieldType(), null, "fieldPreview", null, 0, -1, WorkItemPreview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkItemPreview_WorkType(), theDatamodelPackage.getWorkTypeSpec(), null, "workType", null, 0, 1, WorkItemPreview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workItemPriorityTypeEClass, WorkItemPriorityType.class, "WorkItemPriorityType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkItemPriorityType_AbsPriority(), theXMLTypePackage.getInt(), "absPriority", null, 0, 1, WorkItemPriorityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkItemPriorityType_OffsetPriority(), theXMLTypePackage.getInt(), "offsetPriority", null, 0, 1, WorkItemPriorityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workListViewEClass, WorkListView.class, "WorkListView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkListView_CreationDate(), theXMLTypePackage.getDateTime(), "creationDate", null, 1, 1, WorkListView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListView_Locker(), this.getLockerType(), "locker", null, 0, 1, WorkListView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListView_ModificationDate(), theXMLTypePackage.getDateTime(), "modificationDate", null, 1, 1, WorkListView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListView_WorkViewID(), theXMLTypePackage.getLong(), "workViewID", null, 1, 1, WorkListView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workListViewCommonEClass, WorkListViewCommon.class, "WorkListViewCommon", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkListViewCommon_EntityID(), theOrganisationPackage.getXmlModelEntityId(), null, "entityID", null, 0, 1, WorkListViewCommon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewCommon_ResourcesRequired(), this.getResourcesRequiredType(), "resourcesRequired", null, 0, 1, WorkListViewCommon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkListViewCommon_OrderFilterCriteria(), this.getOrderFilterCriteria(), null, "orderFilterCriteria", null, 0, 1, WorkListViewCommon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewCommon_Description(), this.getDescriptionType(), "description", null, 0, 1, WorkListViewCommon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewCommon_GetAllocatedItems(), theXMLTypePackage.getBoolean(), "getAllocatedItems", null, 0, 1, WorkListViewCommon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewCommon_Name(), this.getNameType(), "name", null, 1, 1, WorkListViewCommon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewCommon_Owner(), this.getOwnerType(), "owner", null, 0, 1, WorkListViewCommon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewCommon_Public(), theXMLTypePackage.getBoolean(), "public", null, 0, 1, WorkListViewCommon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workListViewEditEClass, WorkListViewEdit.class, "WorkListViewEdit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkListViewEdit_Authors(), theOrganisationPackage.getXmlModelEntityId(), null, "authors", null, 0, -1, WorkListViewEdit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkListViewEdit_Users(), theOrganisationPackage.getXmlModelEntityId(), null, "users", null, 0, -1, WorkListViewEdit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewEdit_CustomData(), theXMLTypePackage.getString(), "customData", null, 0, 1, WorkListViewEdit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workListViewPageItemEClass, WorkListViewPageItem.class, "WorkListViewPageItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkListViewPageItem_CreationDate(), theXMLTypePackage.getDateTime(), "creationDate", null, 1, 1, WorkListViewPageItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewPageItem_ModificationDate(), theXMLTypePackage.getDateTime(), "modificationDate", null, 1, 1, WorkListViewPageItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkListViewPageItem_WorkViewID(), theXMLTypePackage.getLong(), "workViewID", null, 1, 1, WorkListViewPageItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelEClass, WorkModel.class, "WorkModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkModel_BaseModelInfo(), this.getBaseModelInfo(), null, "baseModelInfo", null, 1, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkModel_WorkModelSpecification(), this.getWorkModelSpecification(), null, "workModelSpecification", null, 1, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkModel_WorkModelEntities(), this.getWorkModelEntities(), null, "workModelEntities", null, 1, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkModel_WorkModelTypes(), this.getWorkModelTypes(), null, "workModelTypes", null, 1, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkModel_ItemPrivileges(), this.getItemPrivilege(), null, "itemPrivileges", null, 0, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkModel_WorkModelScripts(), this.getWorkModelScripts(), null, "workModelScripts", null, 0, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkModel_AttributeAliasList(), this.getAttributeAliasListType(), null, "attributeAliasList", null, 0, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModel_WorkModelUID(), theXMLTypePackage.getString(), "workModelUID", null, 1, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelEntitiesEClass, WorkModelEntities.class, "WorkModelEntities", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkModelEntities_WorkModelEntity(), this.getWorkModelEntity(), null, "workModelEntity", null, 1, -1, WorkModelEntities.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelEntities_Expression(), theXMLTypePackage.getString(), "expression", null, 0, 1, WorkModelEntities.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelEntityEClass, WorkModelEntity.class, "WorkModelEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkModelEntity_EntityQuery(), theOrganisationPackage.getXmlResourceQuery(), null, "entityQuery", null, 0, 1, WorkModelEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkModelEntity_Entities(), theOrganisationPackage.getXmlModelEntityId(), null, "entities", null, 0, -1, WorkModelEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelEntity_DistributionStrategy(), this.getDistributionStrategy(), "distributionStrategy", null, 1, 1, WorkModelEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelListEClass, WorkModelList.class, "WorkModelList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkModelList_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, WorkModelList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelList_EndPosition(), theXMLTypePackage.getLong(), "endPosition", null, 1, 1, WorkModelList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkModelList_Type(), this.getWorkModel(), null, "type", null, 0, -1, WorkModelList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelMappingEClass, WorkModelMapping.class, "WorkModelMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkModelMapping_TypeParamName(), theXMLTypePackage.getString(), "typeParamName", null, 1, 1, WorkModelMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelMapping_ModelParamName(), theXMLTypePackage.getString(), "modelParamName", null, 0, 1, WorkModelMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelMapping_DefaultValue(), theXMLTypePackage.getString(), "defaultValue", null, 0, 1, WorkModelMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelScriptEClass, WorkModelScript.class, "WorkModelScript", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkModelScript_ScriptBody(), theXMLTypePackage.getString(), "scriptBody", null, 1, 1, WorkModelScript.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelScript_ScriptLanguage(), this.getWorkItemScriptType(), "scriptLanguage", null, 1, 1, WorkModelScript.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelScript_ScriptLanguageExtension(), theXMLTypePackage.getString(), "scriptLanguageExtension", null, 0, 1, WorkModelScript.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelScript_ScriptLanguageVersion(), theXMLTypePackage.getString(), "scriptLanguageVersion", null, 0, 1, WorkModelScript.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelScript_ScriptOperation(), this.getScriptOperationType(), "scriptOperation", null, 1, 1, WorkModelScript.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelScript_ScriptTypeID(), theXMLTypePackage.getString(), "scriptTypeID", null, 1, 1, WorkModelScript.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelScriptsEClass, WorkModelScripts.class, "WorkModelScripts", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkModelScripts_WorkModelScript(), this.getWorkModelScript(), null, "workModelScript", null, 1, -1, WorkModelScripts.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelSpecificationEClass, WorkModelSpecification.class, "WorkModelSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(workModelTypeEClass, WorkModelType.class, "WorkModelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkModelType_WorkModelMapping(), this.getWorkModelMapping(), null, "workModelMapping", null, 0, -1, WorkModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelType_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, WorkModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelType_WorkTypeID(), theXMLTypePackage.getString(), "workTypeID", null, 1, 1, WorkModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workModelTypesEClass, WorkModelTypes.class, "WorkModelTypes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkModelTypes_WorkModelType(), this.getWorkModelType(), null, "workModelType", null, 1, -1, WorkModelTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModelTypes_Expression(), theXMLTypePackage.getString(), "expression", null, 0, 1, WorkModelTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workTypeListEClass, WorkTypeList.class, "WorkTypeList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkTypeList_StartPosition(), theXMLTypePackage.getLong(), "startPosition", null, 1, 1, WorkTypeList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkTypeList_EndPosition(), theXMLTypePackage.getLong(), "endPosition", null, 1, 1, WorkTypeList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkTypeList_Types(), theDatamodelPackage.getWorkType(), null, "types", null, 0, -1, WorkTypeList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(columnCapabilityEEnum, ColumnCapability.class, "ColumnCapability");
        addEEnumLiteral(columnCapabilityEEnum, ColumnCapability.NONE);
        addEEnumLiteral(columnCapabilityEEnum, ColumnCapability.ORDER);
        addEEnumLiteral(columnCapabilityEEnum, ColumnCapability.FILTER);
        addEEnumLiteral(columnCapabilityEEnum, ColumnCapability.BOTHORDERFILTER);

        initEEnum(columnTypeEEnum, ColumnType.class, "ColumnType");
        addEEnumLiteral(columnTypeEEnum, ColumnType.COLUNSPECIFIED);
        addEEnumLiteral(columnTypeEEnum, ColumnType.COLNUMERIC);
        addEEnumLiteral(columnTypeEEnum, ColumnType.COLSTRING);
        addEEnumLiteral(columnTypeEEnum, ColumnType.COLDATETIME);
        addEEnumLiteral(columnTypeEEnum, ColumnType.COLBOOLEAN);
        addEEnumLiteral(columnTypeEEnum, ColumnType.COLSTATE);
        addEEnumLiteral(columnTypeEEnum, ColumnType.COLDISTSTRATEGY);

        initEEnum(distributionStrategyEEnum, DistributionStrategy.class, "DistributionStrategy");
        addEEnumLiteral(distributionStrategyEEnum, DistributionStrategy.OFFER);
        addEEnumLiteral(distributionStrategyEEnum, DistributionStrategy.ALLOCATE);

        initEEnum(methodAuthorisationActionEEnum, MethodAuthorisationAction.class, "MethodAuthorisationAction");
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.SCHEDULE_WORK_ITEM);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.CANCEL_WORK_ITEM);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.SUSPEND_WORK_ITEM);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.VIEW_WORK_LIST);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.SET_RESOURCE_ORDER_FILTER_CRITERIA);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.OPEN_OTHER_RESOURCES_ITEMS);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.CLOSE_OTHER_RESOURCES_ITEMS);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.SKIP_WORK_ITEM);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.REALLOCATE_WORK_ITEM_TO_WORLD);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.REALLOCATE_TO_OFFER_SET);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.PEND_WORK_ITEM);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.WORK_ITEM_ALLOCATION);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.AUTO_OPEN_NEXT_WORK_ITEM);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.RESCHEDULE_WORK_ITEM);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.ENABLE_WORK_ITEM);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.CHANGE_ALLOCATED_WORK_ITEM_PRIORITY);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.CHANGE_ANY_WORK_ITEM_PRIORITY);
        addEEnumLiteral(methodAuthorisationActionEEnum, MethodAuthorisationAction.VIEW_GLOBAL_WORK_LIST);

        initEEnum(methodAuthorisationComponentEEnum, MethodAuthorisationComponent.class, "MethodAuthorisationComponent");
        addEEnumLiteral(methodAuthorisationComponentEEnum, MethodAuthorisationComponent.BRM);

        initEEnum(resourcesRequiredTypeEEnum, ResourcesRequiredType.class, "ResourcesRequiredType");
        addEEnumLiteral(resourcesRequiredTypeEEnum, ResourcesRequiredType.ALL);
        addEEnumLiteral(resourcesRequiredTypeEEnum, ResourcesRequiredType.MINE);

        initEEnum(scheduleStatusEEnum, ScheduleStatus.class, "ScheduleStatus");
        addEEnumLiteral(scheduleStatusEEnum, ScheduleStatus.NOSCHEDULE);
        addEEnumLiteral(scheduleStatusEEnum, ScheduleStatus.BEFORE);
        addEEnumLiteral(scheduleStatusEEnum, ScheduleStatus.DURING);
        addEEnumLiteral(scheduleStatusEEnum, ScheduleStatus.AFTER);

        initEEnum(workGroupTypeEEnum, WorkGroupType.class, "WorkGroupType");
        addEEnumLiteral(workGroupTypeEEnum, WorkGroupType.CHAINING);
        addEEnumLiteral(workGroupTypeEEnum, WorkGroupType.SEPARATIONOFCONCERNS);
        addEEnumLiteral(workGroupTypeEEnum, WorkGroupType.RETAINMOSTFAMILIAR);
        addEEnumLiteral(workGroupTypeEEnum, WorkGroupType.RETAINFAMILIAR);
        addEEnumLiteral(workGroupTypeEEnum, WorkGroupType.CASEHANDLING);
        addEEnumLiteral(workGroupTypeEEnum, WorkGroupType.NOGROUP);

        initEEnum(workItemScriptOperationEEnum, WorkItemScriptOperation.class, "WorkItemScriptOperation");
        addEEnumLiteral(workItemScriptOperationEEnum, WorkItemScriptOperation.OPEN);
        addEEnumLiteral(workItemScriptOperationEEnum, WorkItemScriptOperation.CLOSE);
        addEEnumLiteral(workItemScriptOperationEEnum, WorkItemScriptOperation.COMPLETE);
        addEEnumLiteral(workItemScriptOperationEEnum, WorkItemScriptOperation.PEND);
        addEEnumLiteral(workItemScriptOperationEEnum, WorkItemScriptOperation.SCHEDULE);
        addEEnumLiteral(workItemScriptOperationEEnum, WorkItemScriptOperation.RESCHEDULE);
        addEEnumLiteral(workItemScriptOperationEEnum, WorkItemScriptOperation.SYSAPPEND);

        initEEnum(workItemScriptTypeEEnum, WorkItemScriptType.class, "WorkItemScriptType");
        addEEnumLiteral(workItemScriptTypeEEnum, WorkItemScriptType.JSCRIPT);
        addEEnumLiteral(workItemScriptTypeEEnum, WorkItemScriptType.JYTHON);

        initEEnum(workItemStateEEnum, WorkItemState.class, "WorkItemState");
        addEEnumLiteral(workItemStateEEnum, WorkItemState.CREATED);
        addEEnumLiteral(workItemStateEEnum, WorkItemState.OFFERED);
        addEEnumLiteral(workItemStateEEnum, WorkItemState.ALLOCATED);
        addEEnumLiteral(workItemStateEEnum, WorkItemState.OPENED);
        addEEnumLiteral(workItemStateEEnum, WorkItemState.PENDED);
        addEEnumLiteral(workItemStateEEnum, WorkItemState.PENDHIDDEN);
        addEEnumLiteral(workItemStateEEnum, WorkItemState.SUSPENDED);
        addEEnumLiteral(workItemStateEEnum, WorkItemState.COMPLETED);
        addEEnumLiteral(workItemStateEEnum, WorkItemState.CANCELLED);

        // Initialize data types
        initEDataType(attribute10TypeEDataType, String.class, "Attribute10Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute11TypeEDataType, String.class, "Attribute11Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute12TypeEDataType, String.class, "Attribute12Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute13TypeEDataType, String.class, "Attribute13Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute14TypeEDataType, String.class, "Attribute14Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute21TypeEDataType, String.class, "Attribute21Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute22TypeEDataType, String.class, "Attribute22Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute23TypeEDataType, String.class, "Attribute23Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute24TypeEDataType, String.class, "Attribute24Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute25TypeEDataType, String.class, "Attribute25Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute26TypeEDataType, String.class, "Attribute26Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute27TypeEDataType, String.class, "Attribute27Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute28TypeEDataType, String.class, "Attribute28Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute29TypeEDataType, String.class, "Attribute29Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute2TypeEDataType, String.class, "Attribute2Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute30TypeEDataType, String.class, "Attribute30Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute31TypeEDataType, String.class, "Attribute31Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute32TypeEDataType, String.class, "Attribute32Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute33TypeEDataType, String.class, "Attribute33Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute34TypeEDataType, String.class, "Attribute34Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute35TypeEDataType, String.class, "Attribute35Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute36TypeEDataType, String.class, "Attribute36Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute37TypeEDataType, String.class, "Attribute37Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute38TypeEDataType, String.class, "Attribute38Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute39TypeEDataType, String.class, "Attribute39Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute3TypeEDataType, String.class, "Attribute3Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute40TypeEDataType, String.class, "Attribute40Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute4TypeEDataType, String.class, "Attribute4Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute8TypeEDataType, String.class, "Attribute8Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(attribute9TypeEDataType, String.class, "Attribute9Type", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(columnCapabilityObjectEDataType, ColumnCapability.class, "ColumnCapabilityObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(columnTypeObjectEDataType, ColumnType.class, "ColumnTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(descriptionTypeEDataType, String.class, "DescriptionType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(distributionStrategyObjectEDataType, DistributionStrategy.class, "DistributionStrategyObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(lockerTypeEDataType, String.class, "LockerType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(methodAuthorisationActionObjectEDataType, MethodAuthorisationAction.class, "MethodAuthorisationActionObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(methodAuthorisationComponentObjectEDataType, MethodAuthorisationComponent.class, "MethodAuthorisationComponentObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(nameTypeEDataType, String.class, "NameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(ownerTypeEDataType, String.class, "OwnerType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(resourcesRequiredTypeObjectEDataType, ResourcesRequiredType.class, "ResourcesRequiredTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(scheduleStatusObjectEDataType, ScheduleStatus.class, "ScheduleStatusObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(scriptOperationTypeEDataType, WorkItemScriptOperation.class, "ScriptOperationType", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(workGroupTypeObjectEDataType, WorkGroupType.class, "WorkGroupTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(workItemScriptOperationObjectEDataType, WorkItemScriptOperation.class, "WorkItemScriptOperationObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(workItemScriptTypeObjectEDataType, WorkItemScriptType.class, "WorkItemScriptTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(workItemStateObjectEDataType, WorkItemState.class, "WorkItemStateObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
        addAnnotation
          (addCurrentResourceToViewResponseTypeEClass, 
           source, 
           new String[] {
             "name", "addCurrentResourceToViewResponse_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getAddCurrentResourceToViewResponseType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (addCurrentResourceToViewTypeEClass, 
           source, 
           new String[] {
             "name", "addCurrentResourceToView_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getAddCurrentResourceToViewType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (allocateAndOpenNextWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "allocateAndOpenNextWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAllocateAndOpenNextWorkItemResponseType_WorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItem"
           });		
        addAnnotation
          (allocateAndOpenNextWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "allocateAndOpenNextWorkItem_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAllocateAndOpenNextWorkItemType_Resource(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resource"
           });			
        addAnnotation
          (getAllocateAndOpenNextWorkItemType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workListViewID"
           });		
        addAnnotation
          (allocateAndOpenWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "allocateAndOpenWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAllocateAndOpenWorkItemResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getAllocateAndOpenWorkItemResponseType_WorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItem",
             "group", "#group:0"
           });		
        addAnnotation
          (allocateAndOpenWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "allocateAndOpenWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAllocateAndOpenWorkItemType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getAllocateAndOpenWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });			
        addAnnotation
          (getAllocateAndOpenWorkItemType_Resource(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resource",
             "group", "#group:0"
           });		
        addAnnotation
          (allocateWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "allocateWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAllocateWorkItemResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getAllocateWorkItemResponseType_WorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItem",
             "group", "#group:0"
           });		
        addAnnotation
          (allocateWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "allocateWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAllocateWorkItemType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getAllocateWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });			
        addAnnotation
          (getAllocateWorkItemType_Resource(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resource",
             "group", "#group:0"
           });			
        addAnnotation
          (allocationHistoryEClass, 
           source, 
           new String[] {
             "name", "AllocationHistory",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAllocationHistory_ResourceID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resourceID"
           });		
        addAnnotation
          (getAllocationHistory_AllocationDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "allocationDate"
           });		
        addAnnotation
          (getAllocationHistory_AllocationID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "allocationID"
           });		
        addAnnotation
          (asyncCancelWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "asyncCancelWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncCancelWorkItemResponseType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });			
        addAnnotation
          (getAsyncCancelWorkItemResponseType_ErrorMessage(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "errorMessage"
           });		
        addAnnotation
          (asyncCancelWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "asyncCancelWorkItem_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncCancelWorkItemType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });		
        addAnnotation
          (asyncEndGroupResponseTypeEClass, 
           source, 
           new String[] {
             "name", "asyncEndGroupResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncEndGroupResponseType_ActivityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activityID"
           });			
        addAnnotation
          (getAsyncEndGroupResponseType_GroupID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupID"
           });			
        addAnnotation
          (getAsyncEndGroupResponseType_ErrorMessage(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "errorMessage"
           });		
        addAnnotation
          (asyncEndGroupTypeEClass, 
           source, 
           new String[] {
             "name", "asyncEndGroup_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncEndGroupType_ActivityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activityID"
           });			
        addAnnotation
          (getAsyncEndGroupType_EndGroup(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endGroup",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (asyncRescheduleWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "asyncRescheduleWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncRescheduleWorkItemResponseType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });			
        addAnnotation
          (getAsyncRescheduleWorkItemResponseType_ErrorMessage(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "errorMessage"
           });		
        addAnnotation
          (asyncRescheduleWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "asyncRescheduleWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAsyncRescheduleWorkItemType_ItemSchedule(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemSchedule"
           });			
        addAnnotation
          (getAsyncRescheduleWorkItemType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });			
        addAnnotation
          (getAsyncRescheduleWorkItemType_ItemBody(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemBody"
           });		
        addAnnotation
          (asyncResumeWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "asyncResumeWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncResumeWorkItemResponseType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });			
        addAnnotation
          (getAsyncResumeWorkItemResponseType_ErrorMessage(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "errorMessage"
           });		
        addAnnotation
          (asyncResumeWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "asyncResumeWorkItem_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncResumeWorkItemType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });		
        addAnnotation
          (asyncScheduleWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "asyncScheduleWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncScheduleWorkItemResponseType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });			
        addAnnotation
          (getAsyncScheduleWorkItemResponseType_ErrorMessage(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "errorMessage"
           });		
        addAnnotation
          (asyncScheduleWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "asyncScheduleWorkItem_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncScheduleWorkItemType_ScheduleWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scheduleWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getAsyncScheduleWorkItemType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });		
        addAnnotation
          (asyncScheduleWorkItemWithModelResponseTypeEClass, 
           source, 
           new String[] {
             "name", "asyncScheduleWorkItemWithModelResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncScheduleWorkItemWithModelResponseType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });			
        addAnnotation
          (getAsyncScheduleWorkItemWithModelResponseType_ErrorMessage(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "errorMessage"
           });		
        addAnnotation
          (asyncScheduleWorkItemWithModelTypeEClass, 
           source, 
           new String[] {
             "name", "asyncScheduleWorkItemWithModel_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncScheduleWorkItemWithModelType_ScheduleWorkItemWithModel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scheduleWorkItemWithModel",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getAsyncScheduleWorkItemWithModelType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });		
        addAnnotation
          (asyncStartGroupResponseTypeEClass, 
           source, 
           new String[] {
             "name", "asyncStartGroupResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncStartGroupResponseType_ActivityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activityID"
           });			
        addAnnotation
          (getAsyncStartGroupResponseType_GroupID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupID"
           });			
        addAnnotation
          (getAsyncStartGroupResponseType_ErrorMessage(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "errorMessage"
           });		
        addAnnotation
          (asyncStartGroupTypeEClass, 
           source, 
           new String[] {
             "name", "asyncStartGroup_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncStartGroupType_ActivityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activityID"
           });			
        addAnnotation
          (getAsyncStartGroupType_GroupID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupID"
           });			
        addAnnotation
          (getAsyncStartGroupType_StartGroup(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startGroup",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (asyncSuspendWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "asyncSuspendWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncSuspendWorkItemResponseType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });			
        addAnnotation
          (getAsyncSuspendWorkItemResponseType_ErrorMessage(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "errorMessage"
           });		
        addAnnotation
          (asyncSuspendWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "asyncSuspendWorkItem_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getAsyncSuspendWorkItemType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });			
        addAnnotation
          (getAsyncSuspendWorkItemType_ForceSuspend(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "forceSuspend"
           });			
        addAnnotation
          (asyncWorkItemDetailsEClass, 
           source, 
           new String[] {
             "name", "AsyncWorkItemDetails",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAsyncWorkItemDetails_WorkItemId(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemId"
           });			
        addAnnotation
          (getAsyncWorkItemDetails_ActivityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activityID"
           });			
        addAnnotation
          (getAsyncWorkItemDetails_UaSequenceId(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "uaSequenceId"
           });			
        addAnnotation
          (getAsyncWorkItemDetails_BrmSequenceId(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "brmSequenceId"
           });		
        addAnnotation
          (attribute10TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute10_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute11TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute11_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute12TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute12_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute13TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute13_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute14TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute14_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute21TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute21_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute22TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute22_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute23TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute23_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute24TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute24_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute25TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute25_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute26TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute26_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute27TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute27_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute28TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute28_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute29TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute29_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute2TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute2_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute30TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute30_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute31TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute31_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute32TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute32_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute33TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute33_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute34TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute34_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute35TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute35_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute36TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute36_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute37TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute37_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute38TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute38_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute39TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute39_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "255"
           });		
        addAnnotation
          (attribute3TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute3_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute40TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute40_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "255"
           });		
        addAnnotation
          (attribute4TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute4_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });		
        addAnnotation
          (attribute8TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute8_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attribute9TypeEDataType, 
           source, 
           new String[] {
             "name", "attribute9_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "20"
           });		
        addAnnotation
          (attributeAliasListTypeEClass, 
           source, 
           new String[] {
             "name", "AttributeAliasList_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAttributeAliasListType_AttributeAlias(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "AttributeAlias"
           });			
        addAnnotation
          (baseItemInfoEClass, 
           source, 
           new String[] {
             "name", "BaseItemInfo",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getBaseItemInfo_Name(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "name"
           });			
        addAnnotation
          (getBaseItemInfo_Description(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "description"
           });			
        addAnnotation
          (getBaseItemInfo_DistributionStrategy(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "distributionStrategy"
           });			
        addAnnotation
          (getBaseItemInfo_GroupID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "groupID"
           });			
        addAnnotation
          (getBaseItemInfo_Priority(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "priority"
           });			
        addAnnotation
          (baseModelInfoEClass, 
           source, 
           new String[] {
             "name", "BaseModelInfo",
             "kind", "empty"
           });			
        addAnnotation
          (getBaseModelInfo_Description(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "description"
           });			
        addAnnotation
          (getBaseModelInfo_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });			
        addAnnotation
          (getBaseModelInfo_Priority(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "priority"
           });		
        addAnnotation
          (cancelWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "cancelWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getCancelWorkItemResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (cancelWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "cancelWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getCancelWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (chainedWorkItemNotificationTypeEClass, 
           source, 
           new String[] {
             "name", "chainedWorkItemNotification_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getChainedWorkItemNotificationType_GroupID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupID"
           });			
        addAnnotation
          (getChainedWorkItemNotificationType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (closeWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "closeWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getCloseWorkItemResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getCloseWorkItemResponseType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });		
        addAnnotation
          (closeWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "closeWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getCloseWorkItemType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getCloseWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });			
        addAnnotation
          (getCloseWorkItemType_WorkItemPayload(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemPayload",
             "group", "#group:0"
           });			
        addAnnotation
          (getCloseWorkItemType_HiddenPeriod(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "hiddenPeriod",
             "group", "#group:0"
           });			
        addAnnotation
          (columnCapabilityEEnum, 
           source, 
           new String[] {
             "name", "ColumnCapability"
           });		
        addAnnotation
          (columnCapabilityObjectEDataType, 
           source, 
           new String[] {
             "name", "ColumnCapability:Object",
             "baseType", "ColumnCapability"
           });			
        addAnnotation
          (columnDefinitionEClass, 
           source, 
           new String[] {
             "name", "ColumnDefinition",
             "kind", "empty"
           });			
        addAnnotation
          (getColumnDefinition_Capability(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "capability"
           });			
        addAnnotation
          (getColumnDefinition_Description(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "description"
           });			
        addAnnotation
          (getColumnDefinition_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });			
        addAnnotation
          (getColumnDefinition_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });			
        addAnnotation
          (getColumnDefinition_Type(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "type"
           });			
        addAnnotation
          (columnOrderEClass, 
           source, 
           new String[] {
             "name", "ColumnOrder",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getColumnOrder_ColumnDef(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "columnDef"
           });			
        addAnnotation
          (getColumnOrder_Ascending(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "ascending"
           });			
        addAnnotation
          (columnTypeEEnum, 
           source, 
           new String[] {
             "name", "ColumnType"
           });		
        addAnnotation
          (columnTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "ColumnType:Object",
             "baseType", "ColumnType"
           });		
        addAnnotation
          (completeWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "completeWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getCompleteWorkItemResponseType_GroupID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupID"
           });			
        addAnnotation
          (getCompleteWorkItemResponseType_NextWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "nextWorkItem"
           });		
        addAnnotation
          (completeWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "completeWorkItem_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getCompleteWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });			
        addAnnotation
          (getCompleteWorkItemType_WorkItemPayload(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemPayload"
           });			
        addAnnotation
          (getCompleteWorkItemType_GetNextPiledItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getNextPiledItem"
           });		
        addAnnotation
          (createWorkListViewResponseTypeEClass, 
           source, 
           new String[] {
             "name", "createWorkListViewResponse_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getCreateWorkListViewResponseType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (deleteCurrentResourceFromViewResponseTypeEClass, 
           source, 
           new String[] {
             "name", "deleteCurrentResourceFromViewResponse_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getDeleteCurrentResourceFromViewResponseType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (deleteCurrentResourceFromViewTypeEClass, 
           source, 
           new String[] {
             "name", "deleteCurrentResourceFromView_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getDeleteCurrentResourceFromViewType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (deleteOrgEntityConfigAttributesResponseTypeEClass, 
           source, 
           new String[] {
             "name", "deleteOrgEntityConfigAttributesResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getDeleteOrgEntityConfigAttributesResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (deleteOrgEntityConfigAttributesTypeEClass, 
           source, 
           new String[] {
             "name", "deleteOrgEntityConfigAttributes_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getDeleteOrgEntityConfigAttributesType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getDeleteOrgEntityConfigAttributesType_AttributeName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attributeName",
             "group", "#group:0"
           });			
        addAnnotation
          (getDeleteOrgEntityConfigAttributesType_Resource(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "resource"
           });		
        addAnnotation
          (deleteWorkListViewResponseTypeEClass, 
           source, 
           new String[] {
             "name", "deleteWorkListViewResponse_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getDeleteWorkListViewResponseType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (deleteWorkListViewTypeEClass, 
           source, 
           new String[] {
             "name", "deleteWorkListView_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getDeleteWorkListViewType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (descriptionTypeEDataType, 
           source, 
           new String[] {
             "name", "description_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "255"
           });			
        addAnnotation
          (distributionStrategyEEnum, 
           source, 
           new String[] {
             "name", "DistributionStrategy"
           });		
        addAnnotation
          (distributionStrategyObjectEDataType, 
           source, 
           new String[] {
             "name", "DistributionStrategy:Object",
             "baseType", "DistributionStrategy"
           });		
        addAnnotation
          (documentRootEClass, 
           source, 
           new String[] {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getDocumentRoot_Mixed(), 
           source, 
           new String[] {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getDocumentRoot_XMLNSPrefixMap(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getDocumentRoot_XSISchemaLocation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });			
        addAnnotation
          (getDocumentRoot_AddCurrentResourceToView(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "addCurrentResourceToView",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AddCurrentResourceToViewResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "addCurrentResourceToViewResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AllocateAndOpenNextWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "allocateAndOpenNextWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AllocateAndOpenNextWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "allocateAndOpenNextWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AllocateAndOpenWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "allocateAndOpenWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AllocateAndOpenWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "allocateAndOpenWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AllocateWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "allocateWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AllocateWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "allocateWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncCancelWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncCancelWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncCancelWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncCancelWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncEndGroup(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncEndGroup",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncEndGroupResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncEndGroupResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncRescheduleWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncRescheduleWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncRescheduleWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncRescheduleWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncResumeWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncResumeWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncResumeWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncResumeWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncScheduleWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncScheduleWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncScheduleWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncScheduleWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncScheduleWorkItemWithModel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncScheduleWorkItemWithModel",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncScheduleWorkItemWithModelResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncScheduleWorkItemWithModelResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncStartGroup(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncStartGroup",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncStartGroupResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncStartGroupResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncSuspendWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncSuspendWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_AsyncSuspendWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "asyncSuspendWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_CancelWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "cancelWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_CancelWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "cancelWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ChainedWorkItemNotification(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "chainedWorkItemNotification",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_CloseWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "closeWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_CloseWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "closeWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_CompleteWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "completeWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_CompleteWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "completeWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_CreateWorkListView(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "createWorkListView",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_CreateWorkListViewResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "createWorkListViewResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_DeleteCurrentResourceFromView(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "deleteCurrentResourceFromView",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_DeleteCurrentResourceFromViewResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "deleteCurrentResourceFromViewResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_DeleteOrgEntityConfigAttributes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "deleteOrgEntityConfigAttributes",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_DeleteOrgEntityConfigAttributesResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "deleteOrgEntityConfigAttributesResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_DeleteWorkListView(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "deleteWorkListView",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_DeleteWorkListViewResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "deleteWorkListViewResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_EditWorkListView(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "editWorkListView",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_EditWorkListViewResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "editWorkListViewResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_EnableWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enableWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_EnableWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enableWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_EndGroup(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endGroup",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_EndGroupResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endGroupResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetAllocatedWorkListItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getAllocatedWorkListItems",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetBatchGroupIds(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getBatchGroupIds",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetBatchGroupIdsResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getBatchGroupIdsResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetBatchWorkItemIds(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getBatchWorkItemIds",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetBatchWorkItemIdsResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getBatchWorkItemIdsResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetEditableWorkListViews(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getEditableWorkListViews",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetEditableWorkListViewsResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getEditableWorkListViewsResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetOfferSet(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getOfferSet",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetOfferSetResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getOfferSetResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetOrgEntityConfigAttributes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getOrgEntityConfigAttributes",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetOrgEntityConfigAttributesAvailable(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getOrgEntityConfigAttributesAvailable",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetOrgEntityConfigAttributesAvailableResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getOrgEntityConfigAttributesAvailableResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetOrgEntityConfigAttributesResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getOrgEntityConfigAttributesResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetPublicWorkListViews(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getPublicWorkListViews",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetPublicWorkListViewsResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getPublicWorkListViewsResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetResourceOrderFilterCriteria(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getResourceOrderFilterCriteria",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetResourceOrderFilterCriteriaResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getResourceOrderFilterCriteriaResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetViewsForResource(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getViewsForResource",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetViewsForResourceResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getViewsForResourceResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkItemHeader(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkItemHeader",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkItemHeaderResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkItemHeaderResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkItemOrderFilter(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkItemOrderFilter",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkItemOrderFilterResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkItemOrderFilterResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkListItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkListItems",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkListItemsForGlobalData(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkListItemsForGlobalData",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkListItemsForGlobalDataResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkListItemsForGlobalDataResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkListItemsForView(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkListItemsForView",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkListItemsForViewResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkListItemsForViewResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkListItemsResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkListItemsResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkListViewDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkListViewDetails",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkListViewDetailsResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkListViewDetailsResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkModel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkModel",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkModelResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkModelResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkModels(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkModels",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkModelsResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkModelsResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkType",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkTypeResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkTypeResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkTypes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkTypes",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_GetWorkTypesResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "getWorkTypesResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_OnNotification(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "onNotification",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_OnNotificationResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "onNotificationResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_OpenWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "openWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_OpenWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "openWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_PendWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "pendWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_PendWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "pendWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_PreviewWorkItemFromList(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "previewWorkItemFromList",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_PreviewWorkItemFromListResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "previewWorkItemFromListResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_PushNotification(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "pushNotification",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ReallocateWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "reallocateWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ReallocateWorkItemData(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "reallocateWorkItemData",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ReallocateWorkItemDataResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "reallocateWorkItemDataResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ReallocateWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "reallocateWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_RescheduleWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "rescheduleWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_RescheduleWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "rescheduleWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ResumeWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resumeWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ResumeWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resumeWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SaveOpenWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "saveOpenWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SaveOpenWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "saveOpenWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ScheduleWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scheduleWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ScheduleWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scheduleWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ScheduleWorkItemWithModel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scheduleWorkItemWithModel",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_ScheduleWorkItemWithModelResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scheduleWorkItemWithModelResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SetOrgEntityConfigAttributes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "setOrgEntityConfigAttributes",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SetOrgEntityConfigAttributesResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "setOrgEntityConfigAttributesResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SetResourceOrderFilterCriteria(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "setResourceOrderFilterCriteria",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SetResourceOrderFilterCriteriaResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "setResourceOrderFilterCriteriaResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SetWorkItemPriority(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "setWorkItemPriority",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SetWorkItemPriorityResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "setWorkItemPriorityResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SkipWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "skipWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SkipWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "skipWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_StartGroup(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startGroup",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_StartGroupResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startGroupResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SuspendWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "suspendWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_SuspendWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "suspendWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_UnallocateWorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "unallocateWorkItem",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_UnallocateWorkItemResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "unallocateWorkItemResponse",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_UnlockWorkListView(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "unlockWorkListView",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getDocumentRoot_UnlockWorkListViewResponse(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "unlockWorkListViewResponse",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (editWorkListViewResponseTypeEClass, 
           source, 
           new String[] {
             "name", "editWorkListViewResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getEditWorkListViewResponseType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workListViewID"
           });		
        addAnnotation
          (editWorkListViewTypeEClass, 
           source, 
           new String[] {
             "name", "editWorkListView_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getEditWorkListViewType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (enableWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "enableWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getEnableWorkItemResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (enableWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "enableWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getEnableWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (getEnableWorkItemType_ItemBody(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemBody"
           });		
        addAnnotation
          (endGroupResponseTypeEClass, 
           source, 
           new String[] {
             "name", "endGroupResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getEndGroupResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (endGroupTypeEClass, 
           source, 
           new String[] {
             "name", "endGroup_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getEndGroupType_GroupID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupID"
           });		
        addAnnotation
          (getAllocatedWorkListItemsTypeEClass, 
           source, 
           new String[] {
             "name", "getAllocatedWorkListItems_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetAllocatedWorkListItemsType_EntityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entityID"
           });			
        addAnnotation
          (getGetAllocatedWorkListItemsType_OrderFilterCriteria(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orderFilterCriteria"
           });			
        addAnnotation
          (getGetAllocatedWorkListItemsType_GetTotalCount(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "getTotalCount"
           });			
        addAnnotation
          (getGetAllocatedWorkListItemsType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "numberOfItems"
           });			
        addAnnotation
          (getGetAllocatedWorkListItemsType_StartPosition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "startPosition"
           });		
        addAnnotation
          (getBatchGroupIdsResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getBatchGroupIdsResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetBatchGroupIdsResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getGetBatchGroupIdsResponseType_GroupID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupID",
             "group", "#group:0"
           });		
        addAnnotation
          (getBatchWorkItemIdsResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getBatchWorkItemIdsResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetBatchWorkItemIdsResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });		
        addAnnotation
          (getGetBatchWorkItemIdsResponseType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });		
        addAnnotation
          (getEditableWorkListViewsResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getEditableWorkListViewsResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetEditableWorkListViewsResponseType_WorkListViews(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workListViews"
           });		
        addAnnotation
          (getEditableWorkListViewsTypeEClass, 
           source, 
           new String[] {
             "name", "getEditableWorkListViews_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getGetEditableWorkListViewsType_ApiVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "apiVersion"
           });			
        addAnnotation
          (getGetEditableWorkListViewsType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "numberOfItems"
           });			
        addAnnotation
          (getGetEditableWorkListViewsType_StartPosition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "startPosition"
           });		
        addAnnotation
          (getOfferSetResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getOfferSetResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetOfferSetResponseType_EntityGuid(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entityGuid"
           });			
        addAnnotation
          (getGetOfferSetResponseType_EntityId(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entityId"
           });		
        addAnnotation
          (getOfferSetTypeEClass, 
           source, 
           new String[] {
             "name", "getOfferSet_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetOfferSetType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });			
        addAnnotation
          (getGetOfferSetType_ApiVersion(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "apiVersion"
           });		
        addAnnotation
          (getOrgEntityConfigAttributesAvailableResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getOrgEntityConfigAttributesAvailableResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetOrgEntityConfigAttributesAvailableResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getGetOrgEntityConfigAttributesAvailableResponseType_OrgEntityConfigAttributesAvailable(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orgEntityConfigAttributesAvailable",
             "group", "#group:0"
           });		
        addAnnotation
          (getOrgEntityConfigAttributesAvailableTypeEClass, 
           source, 
           new String[] {
             "name", "getOrgEntityConfigAttributesAvailable_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetOrgEntityConfigAttributesAvailableType_StartAt(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startAt"
           });			
        addAnnotation
          (getGetOrgEntityConfigAttributesAvailableType_NumToReturn(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "numToReturn"
           });		
        addAnnotation
          (getOrgEntityConfigAttributesResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getOrgEntityConfigAttributesResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetOrgEntityConfigAttributesResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getGetOrgEntityConfigAttributesResponseType_OrgEntityConfigAttribute(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orgEntityConfigAttribute",
             "group", "#group:0"
           });		
        addAnnotation
          (getOrgEntityConfigAttributesTypeEClass, 
           source, 
           new String[] {
             "name", "getOrgEntityConfigAttributes_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetOrgEntityConfigAttributesType_Resource(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resource"
           });		
        addAnnotation
          (getPublicWorkListViewsResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getPublicWorkListViewsResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetPublicWorkListViewsResponseType_WorkListViews(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workListViews"
           });		
        addAnnotation
          (getPublicWorkListViewsTypeEClass, 
           source, 
           new String[] {
             "name", "getPublicWorkListViews_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getGetPublicWorkListViewsType_ApiVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "apiVersion"
           });			
        addAnnotation
          (getGetPublicWorkListViewsType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "numberOfItems"
           });			
        addAnnotation
          (getGetPublicWorkListViewsType_StartPosition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "startPosition"
           });		
        addAnnotation
          (getResourceOrderFilterCriteriaResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getResourceOrderFilterCriteriaResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetResourceOrderFilterCriteriaResponseType_OrderFilterCriteria(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orderFilterCriteria"
           });		
        addAnnotation
          (getResourceOrderFilterCriteriaTypeEClass, 
           source, 
           new String[] {
             "name", "getResourceOrderFilterCriteria_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetResourceOrderFilterCriteriaType_ResourceID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resourceID"
           });		
        addAnnotation
          (getViewsForResourceResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getViewsForResourceResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetViewsForResourceResponseType_WorkListViews(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workListViews"
           });		
        addAnnotation
          (getViewsForResourceTypeEClass, 
           source, 
           new String[] {
             "name", "getViewsForResource_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getGetViewsForResourceType_ApiVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "apiVersion"
           });			
        addAnnotation
          (getGetViewsForResourceType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "numberOfItems"
           });			
        addAnnotation
          (getGetViewsForResourceType_StartPosition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "startPosition"
           });		
        addAnnotation
          (getWorkItemHeaderResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkItemHeaderResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetWorkItemHeaderResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getGetWorkItemHeaderResponseType_WorkItemHeader(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemHeader",
             "group", "#group:0"
           });		
        addAnnotation
          (getWorkItemHeaderTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkItemHeader_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetWorkItemHeaderType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getGetWorkItemHeaderType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });		
        addAnnotation
          (getWorkItemOrderFilterResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkItemOrderFilterResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkItemOrderFilterResponseType_ColumnData(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "columnData"
           });		
        addAnnotation
          (getWorkItemOrderFilterTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkItemOrderFilter_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkItemOrderFilterType_LimitColumns(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "limitColumns"
           });			
        addAnnotation
          (getWorkListItemsForGlobalDataResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkListItemsForGlobalDataResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkListItemsForGlobalDataResponseType_StartPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startPosition"
           });			
        addAnnotation
          (getGetWorkListItemsForGlobalDataResponseType_EndPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endPosition"
           });			
        addAnnotation
          (getGetWorkListItemsForGlobalDataResponseType_WorkItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItems"
           });		
        addAnnotation
          (getWorkListItemsForGlobalDataTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkListItemsForGlobalData_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkListItemsForGlobalDataType_GlobalDataRef(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "globalDataRef"
           });			
        addAnnotation
          (getGetWorkListItemsForGlobalDataType_OrderFilterCriteria(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orderFilterCriteria"
           });			
        addAnnotation
          (getGetWorkListItemsForGlobalDataType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "numberOfItems"
           });			
        addAnnotation
          (getGetWorkListItemsForGlobalDataType_StartPosition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "startPosition"
           });			
        addAnnotation
          (getWorkListItemsForViewResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkListItemsForViewResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkListItemsForViewResponseType_StartPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startPosition"
           });			
        addAnnotation
          (getGetWorkListItemsForViewResponseType_EndPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endPosition"
           });			
        addAnnotation
          (getGetWorkListItemsForViewResponseType_TotalItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "totalItems"
           });			
        addAnnotation
          (getGetWorkListItemsForViewResponseType_WorkItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItems"
           });			
        addAnnotation
          (getGetWorkListItemsForViewResponseType_CustomData(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "customData"
           });		
        addAnnotation
          (getWorkListItemsForViewTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkListItemsForView_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getGetWorkListItemsForViewType_GetAllocatedItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "getAllocatedItems"
           });			
        addAnnotation
          (getGetWorkListItemsForViewType_GetTotalCount(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "getTotalCount"
           });			
        addAnnotation
          (getGetWorkListItemsForViewType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "numberOfItems"
           });			
        addAnnotation
          (getGetWorkListItemsForViewType_StartPosition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "startPosition"
           });			
        addAnnotation
          (getGetWorkListItemsForViewType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });			
        addAnnotation
          (getWorkListItemsResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkListItemsResponseType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkListItemsResponseType_StartPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startPosition"
           });			
        addAnnotation
          (getGetWorkListItemsResponseType_EndPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endPosition"
           });			
        addAnnotation
          (getGetWorkListItemsResponseType_TotalItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "totalItems"
           });			
        addAnnotation
          (getGetWorkListItemsResponseType_WorkItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItems"
           });			
        addAnnotation
          (getWorkListItemsResponseType1EClass, 
           source, 
           new String[] {
             "name", "getWorkListItemsResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkListItemsResponseType1_StartPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startPosition"
           });			
        addAnnotation
          (getGetWorkListItemsResponseType1_EndPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endPosition"
           });			
        addAnnotation
          (getGetWorkListItemsResponseType1_TotalItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "totalItems"
           });			
        addAnnotation
          (getGetWorkListItemsResponseType1_WorkItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItems"
           });		
        addAnnotation
          (getWorkListItemsTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkListItems_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkListItemsType_ResourcesRequired(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resourcesRequired"
           });			
        addAnnotation
          (getGetWorkListItemsType_EntityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entityID"
           });			
        addAnnotation
          (getGetWorkListItemsType_OrderFilterCriteria(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orderFilterCriteria"
           });			
        addAnnotation
          (getGetWorkListItemsType_GetTotalCount(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "getTotalCount"
           });			
        addAnnotation
          (getGetWorkListItemsType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "numberOfItems"
           });			
        addAnnotation
          (getGetWorkListItemsType_StartPosition(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "startPosition"
           });		
        addAnnotation
          (getWorkListViewDetailsTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkListViewDetails_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getGetWorkListViewDetailsType_ApiVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "apiVersion"
           });			
        addAnnotation
          (getGetWorkListViewDetailsType_LockView(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "lockView"
           });			
        addAnnotation
          (getGetWorkListViewDetailsType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (getWorkModelResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkModelResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetWorkModelResponseType_WorkModel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workModel"
           });		
        addAnnotation
          (getWorkModelsResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkModelsResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetWorkModelsResponseType_WorkModelList(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workModelList"
           });		
        addAnnotation
          (getWorkModelsTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkModels_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetWorkModelsType_StartPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startPosition"
           });		
        addAnnotation
          (getGetWorkModelsType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "numberOfItems"
           });		
        addAnnotation
          (getWorkModelTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkModel_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetWorkModelType_WorkModelID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workModelID"
           });		
        addAnnotation
          (getWorkTypeResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkTypeResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetWorkTypeResponseType_WorkType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workType"
           });		
        addAnnotation
          (getWorkTypesResponseTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkTypesResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkTypesResponseType_WorkTypelList(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workTypelList"
           });		
        addAnnotation
          (getWorkTypesTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkTypes_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getGetWorkTypesType_StartPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startPosition"
           });			
        addAnnotation
          (getGetWorkTypesType_NumberOfItems(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "numberOfItems"
           });		
        addAnnotation
          (getWorkTypeTypeEClass, 
           source, 
           new String[] {
             "name", "getWorkType_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGetWorkTypeType_WorkTypeID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workTypeID"
           });			
        addAnnotation
          (hiddenPeriodEClass, 
           source, 
           new String[] {
             "name", "HiddenPeriod",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getHiddenPeriod_HiddenDuration(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "hiddenDuration"
           });			
        addAnnotation
          (getHiddenPeriod_VisibleDate(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "visibleDate"
           });			
        addAnnotation
          (itemEClass, 
           source, 
           new String[] {
             "name", "Item",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getItem_Entities(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entities"
           });			
        addAnnotation
          (getItem_EntityQuery(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entityQuery"
           });			
        addAnnotation
          (getItem_WorkTypeUID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workTypeUID"
           });			
        addAnnotation
          (getItem_WorkTypeVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workTypeVersion"
           });			
        addAnnotation
          (itemBodyEClass, 
           source, 
           new String[] {
             "name", "ItemBody",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getItemBody_Parameter(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "parameter"
           });			
        addAnnotation
          (itemContextEClass, 
           source, 
           new String[] {
             "name", "ItemContext",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getItemContext_ActivityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activityID"
           });			
        addAnnotation
          (getItemContext_ActivityName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activityName"
           });			
        addAnnotation
          (getItemContext_AppInstance(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "appInstance"
           });			
        addAnnotation
          (getItemContext_AppName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "appName"
           });			
        addAnnotation
          (getItemContext_AppID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "appID"
           });			
        addAnnotation
          (getItemContext_AppInstanceDescription(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "appInstanceDescription"
           });			
        addAnnotation
          (itemDurationEClass, 
           source, 
           new String[] {
             "name", "ItemDuration",
             "kind", "empty"
           });			
        addAnnotation
          (getItemDuration_Days(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "days"
           });			
        addAnnotation
          (getItemDuration_Hours(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "hours"
           });			
        addAnnotation
          (getItemDuration_Minutes(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "minutes"
           });			
        addAnnotation
          (getItemDuration_Months(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "months"
           });			
        addAnnotation
          (getItemDuration_Weeks(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "weeks"
           });			
        addAnnotation
          (getItemDuration_Years(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "years"
           });			
        addAnnotation
          (itemPrivilegeEClass, 
           source, 
           new String[] {
             "name", "ItemPrivilege",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getItemPrivilege_Suspend(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Suspend"
           });			
        addAnnotation
          (getItemPrivilege_StatelessRealloc(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "StatelessRealloc"
           });			
        addAnnotation
          (getItemPrivilege_StatefulRealloc(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "StatefulRealloc"
           });			
        addAnnotation
          (getItemPrivilege_Dellocate(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Dellocate"
           });			
        addAnnotation
          (getItemPrivilege_Delegate(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Delegate"
           });			
        addAnnotation
          (getItemPrivilege_Skip(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Skip"
           });			
        addAnnotation
          (getItemPrivilege_Piling(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Piling"
           });			
        addAnnotation
          (itemScheduleEClass, 
           source, 
           new String[] {
             "name", "ItemSchedule",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getItemSchedule_StartDate(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startDate"
           });			
        addAnnotation
          (getItemSchedule_MaxDuration(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "maxDuration"
           });			
        addAnnotation
          (getItemSchedule_TargetDate(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "targetDate"
           });		
        addAnnotation
          (lockerTypeEDataType, 
           source, 
           new String[] {
             "name", "locker_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "36"
           });			
        addAnnotation
          (managedObjectIDEClass, 
           source, 
           new String[] {
             "name", "ManagedObjectID",
             "kind", "empty"
           });			
        addAnnotation
          (getManagedObjectID_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });		
        addAnnotation
          (methodAuthorisationActionEEnum, 
           source, 
           new String[] {
             "name", "MethodAuthorisationAction"
           });		
        addAnnotation
          (methodAuthorisationActionObjectEDataType, 
           source, 
           new String[] {
             "name", "MethodAuthorisationAction:Object",
             "baseType", "MethodAuthorisationAction"
           });		
        addAnnotation
          (methodAuthorisationComponentEEnum, 
           source, 
           new String[] {
             "name", "MethodAuthorisationComponent"
           });		
        addAnnotation
          (methodAuthorisationComponentObjectEDataType, 
           source, 
           new String[] {
             "name", "MethodAuthorisationComponent:Object",
             "baseType", "MethodAuthorisationComponent"
           });		
        addAnnotation
          (nameTypeEDataType, 
           source, 
           new String[] {
             "name", "name_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "64"
           });			
        addAnnotation
          (nextWorkItemEClass, 
           source, 
           new String[] {
             "name", "NextWorkItem",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getNextWorkItem_NextItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "nextItem"
           });			
        addAnnotation
          (objectIDEClass, 
           source, 
           new String[] {
             "name", "ObjectID",
             "kind", "empty"
           });			
        addAnnotation
          (getObjectID_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (onNotificationResponseTypeEClass, 
           source, 
           new String[] {
             "name", "onNotificationResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOnNotificationResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (onNotificationTypeEClass, 
           source, 
           new String[] {
             "name", "onNotification_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOnNotificationType_MessageDetails(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messageDetails"
           });		
        addAnnotation
          (getOnNotificationType_ItemBody(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemBody"
           });		
        addAnnotation
          (getOnNotificationType_AllocationHistory(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "allocationHistory"
           });		
        addAnnotation
          (openWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "openWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOpenWorkItemResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getOpenWorkItemResponseType_WorkItemBody(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemBody",
             "group", "#group:0"
           });		
        addAnnotation
          (openWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "openWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getOpenWorkItemType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getOpenWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });			
        addAnnotation
          (orderFilterCriteriaEClass, 
           source, 
           new String[] {
             "name", "OrderFilterCriteria",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getOrderFilterCriteria_Order(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "order"
           });			
        addAnnotation
          (getOrderFilterCriteria_Filter(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "filter"
           });			
        addAnnotation
          (orgEntityConfigAttributeEClass, 
           source, 
           new String[] {
             "name", "OrgEntityConfigAttribute",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getOrgEntityConfigAttribute_AttributeName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attributeName"
           });			
        addAnnotation
          (getOrgEntityConfigAttribute_AttributeValue(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attributeValue"
           });			
        addAnnotation
          (getOrgEntityConfigAttribute_ReadOnly(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "readOnly"
           });			
        addAnnotation
          (orgEntityConfigAttributesAvailableEClass, 
           source, 
           new String[] {
             "name", "OrgEntityConfigAttributesAvailable",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getOrgEntityConfigAttributesAvailable_AttributeName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attributeName"
           });			
        addAnnotation
          (getOrgEntityConfigAttributesAvailable_ReadOnly(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "readOnly"
           });			
        addAnnotation
          (orgEntityConfigAttributeSetEClass, 
           source, 
           new String[] {
             "name", "OrgEntityConfigAttributeSet",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getOrgEntityConfigAttributeSet_AttributeName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attributeName"
           });			
        addAnnotation
          (getOrgEntityConfigAttributeSet_AttributeValue(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attributeValue"
           });		
        addAnnotation
          (ownerTypeEDataType, 
           source, 
           new String[] {
             "name", "owner_._type",
             "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
             "maxLength", "36"
           });			
        addAnnotation
          (parameterTypeEClass, 
           source, 
           new String[] {
             "name", "ParameterType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getParameterType_ComplexValue(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "ComplexValue"
           });			
        addAnnotation
          (getParameterType_Value(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Value"
           });			
        addAnnotation
          (getParameterType_Array(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "Array"
           });			
        addAnnotation
          (getParameterType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "Name"
           });		
        addAnnotation
          (pendWorkItemEClass, 
           source, 
           new String[] {
             "name", "pendWorkItem",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPendWorkItem_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getPendWorkItem_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });			
        addAnnotation
          (getPendWorkItem_HiddenPeriod(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "hiddenPeriod",
             "group", "#group:0"
           });		
        addAnnotation
          (pendWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "pendWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPendWorkItemResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getPendWorkItemResponseType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });		
        addAnnotation
          (previewWorkItemFromListResponseTypeEClass, 
           source, 
           new String[] {
             "name", "previewWorkItemFromListResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPreviewWorkItemFromListResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getPreviewWorkItemFromListResponseType_WorkItemPreview(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemPreview",
             "group", "#group:0"
           });		
        addAnnotation
          (previewWorkItemFromListTypeEClass, 
           source, 
           new String[] {
             "name", "previewWorkItemFromList_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getPreviewWorkItemFromListType_EntityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entityID"
           });			
        addAnnotation
          (getPreviewWorkItemFromListType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });			
        addAnnotation
          (getPreviewWorkItemFromListType_RequiredField(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "requiredField"
           });			
        addAnnotation
          (getPreviewWorkItemFromListType_RequireWorkType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "requireWorkType"
           });			
        addAnnotation
          (privilegeEClass, 
           source, 
           new String[] {
             "name", "Privilege",
             "kind", "empty"
           });			
        addAnnotation
          (getPrivilege_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });			
        addAnnotation
          (getPrivilege_Qualifier(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "qualifier"
           });		
        addAnnotation
          (pushNotificationTypeEClass, 
           source, 
           new String[] {
             "name", "pushNotification_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getPushNotificationType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });			
        addAnnotation
          (getPushNotificationType_WorkTypeID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workTypeID"
           });			
        addAnnotation
          (getPushNotificationType_ResourceIDs(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resourceIDs"
           });		
        addAnnotation
          (reallocateWorkItemDataEClass, 
           source, 
           new String[] {
             "name", "reallocateWorkItemData",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getReallocateWorkItemData_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getReallocateWorkItemData_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });			
        addAnnotation
          (getReallocateWorkItemData_Resource(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resource",
             "group", "#group:0"
           });			
        addAnnotation
          (getReallocateWorkItemData_WorkItemPayload(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemPayload",
             "group", "#group:0"
           });		
        addAnnotation
          (reallocateWorkItemDataResponseTypeEClass, 
           source, 
           new String[] {
             "name", "reallocateWorkItemDataResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getReallocateWorkItemDataResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getReallocateWorkItemDataResponseType_WorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItem",
             "group", "#group:0"
           });		
        addAnnotation
          (reallocateWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "reallocateWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getReallocateWorkItemResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getReallocateWorkItemResponseType_WorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItem",
             "group", "#group:0"
           });		
        addAnnotation
          (reallocateWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "reallocateWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getReallocateWorkItemType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getReallocateWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });			
        addAnnotation
          (getReallocateWorkItemType_Resource(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resource",
             "group", "#group:0"
           });			
        addAnnotation
          (getReallocateWorkItemType_RevertData(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "revertData",
             "group", "#group:0"
           });		
        addAnnotation
          (rescheduleWorkItemEClass, 
           source, 
           new String[] {
             "name", "rescheduleWorkItem",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getRescheduleWorkItem_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });			
        addAnnotation
          (getRescheduleWorkItem_ItemSchedule(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemSchedule"
           });			
        addAnnotation
          (getRescheduleWorkItem_ItemBody(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemBody"
           });		
        addAnnotation
          (rescheduleWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "rescheduleWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getRescheduleWorkItemResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });			
        addAnnotation
          (resourcesRequiredTypeEEnum, 
           source, 
           new String[] {
             "name", "ResourcesRequiredType"
           });		
        addAnnotation
          (resourcesRequiredTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "ResourcesRequiredType:Object",
             "baseType", "ResourcesRequiredType"
           });		
        addAnnotation
          (resumeWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "resumeWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getResumeWorkItemResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (resumeWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "resumeWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getResumeWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (saveOpenWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "saveOpenWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getSaveOpenWorkItemResponseType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (saveOpenWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "saveOpenWorkItem_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getSaveOpenWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });			
        addAnnotation
          (getSaveOpenWorkItemType_WorkItemPayload(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemPayload"
           });			
        addAnnotation
          (scheduleStatusEEnum, 
           source, 
           new String[] {
             "name", "ScheduleStatus"
           });		
        addAnnotation
          (scheduleStatusObjectEDataType, 
           source, 
           new String[] {
             "name", "ScheduleStatus:Object",
             "baseType", "ScheduleStatus"
           });		
        addAnnotation
          (scheduleWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "scheduleWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getScheduleWorkItemResponseType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (scheduleWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "scheduleWorkItem_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getScheduleWorkItemType_Item(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "item"
           });			
        addAnnotation
          (getScheduleWorkItemType_ItemSchedule(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemSchedule"
           });			
        addAnnotation
          (getScheduleWorkItemType_ItemContext(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemContext"
           });			
        addAnnotation
          (getScheduleWorkItemType_ItemBody(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemBody"
           });		
        addAnnotation
          (scheduleWorkItemWithModelResponseTypeEClass, 
           source, 
           new String[] {
             "name", "scheduleWorkItemWithModelResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getScheduleWorkItemWithModelResponseType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (scheduleWorkItemWithModelTypeEClass, 
           source, 
           new String[] {
             "name", "scheduleWorkItemWithModel_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getScheduleWorkItemWithModelType_ItemSchedule(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemSchedule"
           });			
        addAnnotation
          (getScheduleWorkItemWithModelType_ItemContext(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemContext"
           });			
        addAnnotation
          (getScheduleWorkItemWithModelType_ItemBody(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemBody"
           });			
        addAnnotation
          (getScheduleWorkItemWithModelType_EntityQuery(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entityQuery"
           });			
        addAnnotation
          (getScheduleWorkItemWithModelType_GroupID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "groupID"
           });			
        addAnnotation
          (getScheduleWorkItemWithModelType_WorkModelUID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workModelUID"
           });			
        addAnnotation
          (getScheduleWorkItemWithModelType_WorkModelVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workModelVersion"
           });		
        addAnnotation
          (scriptOperationTypeEDataType, 
           source, 
           new String[] {
             "name", "scriptOperation_._type",
             "baseType", "WorkItemScriptOperation"
           });		
        addAnnotation
          (setOrgEntityConfigAttributesResponseTypeEClass, 
           source, 
           new String[] {
             "name", "setOrgEntityConfigAttributesResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getSetOrgEntityConfigAttributesResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (setOrgEntityConfigAttributesTypeEClass, 
           source, 
           new String[] {
             "name", "setOrgEntityConfigAttributes_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSetOrgEntityConfigAttributesType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getSetOrgEntityConfigAttributesType_OrgEntityConfigAttributeSet(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orgEntityConfigAttributeSet",
             "group", "#group:0"
           });			
        addAnnotation
          (getSetOrgEntityConfigAttributesType_Resource(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "resource"
           });		
        addAnnotation
          (setResourceOrderFilterCriteriaResponseTypeEClass, 
           source, 
           new String[] {
             "name", "setResourceOrderFilterCriteriaResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getSetResourceOrderFilterCriteriaResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (setResourceOrderFilterCriteriaTypeEClass, 
           source, 
           new String[] {
             "name", "setResourceOrderFilterCriteria_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getSetResourceOrderFilterCriteriaType_OrderFilterCriteria(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orderFilterCriteria"
           });			
        addAnnotation
          (getSetResourceOrderFilterCriteriaType_ResourceID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "resourceID"
           });		
        addAnnotation
          (setWorkItemPriorityEClass, 
           source, 
           new String[] {
             "name", "setWorkItemPriority",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSetWorkItemPriority_WorkItemIDandPriority(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemIDandPriority"
           });		
        addAnnotation
          (setWorkItemPriorityResponseTypeEClass, 
           source, 
           new String[] {
             "name", "setWorkItemPriorityResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSetWorkItemPriorityResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getSetWorkItemPriorityResponseType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });		
        addAnnotation
          (skipWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "skipWorkItemResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getSkipWorkItemResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (skipWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "skipWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSkipWorkItemType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getSkipWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });		
        addAnnotation
          (startGroupResponseTypeEClass, 
           source, 
           new String[] {
             "name", "startGroupResponse_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getStartGroupResponseType_GroupID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupID"
           });		
        addAnnotation
          (startGroupTypeEClass, 
           source, 
           new String[] {
             "name", "startGroup_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getStartGroupType_GroupType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groupType"
           });			
        addAnnotation
          (getStartGroupType_Description(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "description"
           });		
        addAnnotation
          (suspendWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "suspendWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSuspendWorkItemResponseType_Success(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "success"
           });		
        addAnnotation
          (suspendWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "suspendWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSuspendWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (getSuspendWorkItemType_ForceSuspend(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "forceSuspend"
           });		
        addAnnotation
          (unallocateWorkItemResponseTypeEClass, 
           source, 
           new String[] {
             "name", "unallocateWorkItemResponse_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getUnallocateWorkItemResponseType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getUnallocateWorkItemResponseType_WorkItem(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItem",
             "group", "#group:0"
           });		
        addAnnotation
          (unallocateWorkItemTypeEClass, 
           source, 
           new String[] {
             "name", "unallocateWorkItem_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getUnallocateWorkItemType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });			
        addAnnotation
          (getUnallocateWorkItemType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID",
             "group", "#group:0"
           });		
        addAnnotation
          (unlockWorkListViewResponseTypeEClass, 
           source, 
           new String[] {
             "name", "unlockWorkListViewResponse_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getUnlockWorkListViewResponseType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });		
        addAnnotation
          (unlockWorkListViewTypeEClass, 
           source, 
           new String[] {
             "name", "unlockWorkListView_._type",
             "kind", "empty"
           });			
        addAnnotation
          (getUnlockWorkListViewType_WorkListViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workListViewID"
           });			
        addAnnotation
          (workGroupTypeEEnum, 
           source, 
           new String[] {
             "name", "WorkGroupType"
           });		
        addAnnotation
          (workGroupTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "WorkGroupType:Object",
             "baseType", "WorkGroupType"
           });			
        addAnnotation
          (workItemEClass, 
           source, 
           new String[] {
             "name", "WorkItem",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkItem_Id(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "id"
           });			
        addAnnotation
          (getWorkItem_Header(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "header"
           });			
        addAnnotation
          (getWorkItem_Attributes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "attributes"
           });			
        addAnnotation
          (getWorkItem_Body(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "body"
           });			
        addAnnotation
          (getWorkItem_WorkType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workType"
           });			
        addAnnotation
          (getWorkItem_State(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "state"
           });			
        addAnnotation
          (getWorkItem_Visible(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "visible"
           });			
        addAnnotation
          (workItemAttributesEClass, 
           source, 
           new String[] {
             "name", "WorkItemAttributes",
             "kind", "empty"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute1(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute1"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute10(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute10"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute11(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute11"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute12(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute12"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute13(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute13"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute14(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute14"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute15(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute15"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute16(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute16"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute17(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute17"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute18(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute18"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute19(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute19"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute2(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute2"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute20(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute20"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute21(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute21"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute22(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute22"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute23(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute23"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute24(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute24"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute25(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute25"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute26(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute26"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute27(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute27"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute28(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute28"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute29(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute29"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute3(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute3"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute30(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute30"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute31(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute31"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute32(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute32"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute33(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute33"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute34(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute34"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute35(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute35"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute36(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute36"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute37(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute37"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute38(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute38"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute39(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute39"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute4(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute4"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute40(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute40"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute5(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute5"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute6(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute6"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute7(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute7"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute8(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute8"
           });			
        addAnnotation
          (getWorkItemAttributes_Attribute9(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "attribute9"
           });			
        addAnnotation
          (workItemBodyEClass, 
           source, 
           new String[] {
             "name", "WorkItemBody",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkItemBody_DataModel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "dataModel"
           });			
        addAnnotation
          (workItemFlagsEClass, 
           source, 
           new String[] {
             "name", "WorkItemFlags",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkItemFlags_ScheduleStatus(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scheduleStatus"
           });			
        addAnnotation
          (workItemHeaderEClass, 
           source, 
           new String[] {
             "name", "WorkItemHeader",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkItemHeader_Flags(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "flags"
           });			
        addAnnotation
          (getWorkItemHeader_ItemContext(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "itemContext"
           });			
        addAnnotation
          (getWorkItemHeader_EndDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "endDate"
           });			
        addAnnotation
          (getWorkItemHeader_StartDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "startDate"
           });		
        addAnnotation
          (workItemIDandPriorityTypeEClass, 
           source, 
           new String[] {
             "name", "workItemIDandPriority_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkItemIDandPriorityType_WorkItemID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemID"
           });		
        addAnnotation
          (getWorkItemIDandPriorityType_WorkItemPriority(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workItemPriority"
           });			
        addAnnotation
          (workItemPreviewEClass, 
           source, 
           new String[] {
             "name", "WorkItemPreview",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkItemPreview_FieldPreview(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "FieldPreview"
           });			
        addAnnotation
          (getWorkItemPreview_WorkType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "workType"
           });		
        addAnnotation
          (workItemPriorityTypeEClass, 
           source, 
           new String[] {
             "name", "workItemPriority_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkItemPriorityType_AbsPriority(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "absPriority"
           });		
        addAnnotation
          (getWorkItemPriorityType_OffsetPriority(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "offsetPriority"
           });			
        addAnnotation
          (workItemScriptOperationEEnum, 
           source, 
           new String[] {
             "name", "WorkItemScriptOperation"
           });		
        addAnnotation
          (workItemScriptOperationObjectEDataType, 
           source, 
           new String[] {
             "name", "WorkItemScriptOperation:Object",
             "baseType", "WorkItemScriptOperation"
           });			
        addAnnotation
          (workItemScriptTypeEEnum, 
           source, 
           new String[] {
             "name", "WorkItemScriptType"
           });		
        addAnnotation
          (workItemScriptTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "WorkItemScriptType:Object",
             "baseType", "WorkItemScriptType"
           });			
        addAnnotation
          (workItemStateEEnum, 
           source, 
           new String[] {
             "name", "WorkItemState"
           });		
        addAnnotation
          (workItemStateObjectEDataType, 
           source, 
           new String[] {
             "name", "WorkItemState:Object",
             "baseType", "WorkItemState"
           });			
        addAnnotation
          (workListViewEClass, 
           source, 
           new String[] {
             "name", "WorkListView",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkListView_CreationDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "creationDate"
           });			
        addAnnotation
          (getWorkListView_Locker(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "locker"
           });			
        addAnnotation
          (getWorkListView_ModificationDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "modificationDate"
           });			
        addAnnotation
          (getWorkListView_WorkViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workViewID"
           });			
        addAnnotation
          (workListViewCommonEClass, 
           source, 
           new String[] {
             "name", "WorkListViewCommon",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkListViewCommon_EntityID(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "entityID"
           });			
        addAnnotation
          (getWorkListViewCommon_ResourcesRequired(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "resourcesRequired"
           });			
        addAnnotation
          (getWorkListViewCommon_OrderFilterCriteria(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "orderFilterCriteria"
           });			
        addAnnotation
          (getWorkListViewCommon_Description(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "description"
           });			
        addAnnotation
          (getWorkListViewCommon_GetAllocatedItems(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "getAllocatedItems"
           });			
        addAnnotation
          (getWorkListViewCommon_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });			
        addAnnotation
          (getWorkListViewCommon_Owner(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "owner"
           });			
        addAnnotation
          (getWorkListViewCommon_Public(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "public"
           });			
        addAnnotation
          (workListViewEditEClass, 
           source, 
           new String[] {
             "name", "WorkListViewEdit",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkListViewEdit_Authors(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "authors"
           });			
        addAnnotation
          (getWorkListViewEdit_Users(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "users"
           });			
        addAnnotation
          (getWorkListViewEdit_CustomData(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "customData"
           });			
        addAnnotation
          (workListViewPageItemEClass, 
           source, 
           new String[] {
             "name", "WorkListViewPageItem",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkListViewPageItem_CreationDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "creationDate"
           });			
        addAnnotation
          (getWorkListViewPageItem_ModificationDate(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "modificationDate"
           });			
        addAnnotation
          (getWorkListViewPageItem_WorkViewID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workViewID"
           });			
        addAnnotation
          (workModelEClass, 
           source, 
           new String[] {
             "name", "WorkModel",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkModel_BaseModelInfo(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "BaseModelInfo"
           });		
        addAnnotation
          (getWorkModel_WorkModelSpecification(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModelSpecification"
           });		
        addAnnotation
          (getWorkModel_WorkModelEntities(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModelEntities"
           });		
        addAnnotation
          (getWorkModel_WorkModelTypes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModelTypes"
           });		
        addAnnotation
          (getWorkModel_ItemPrivileges(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "ItemPrivileges"
           });		
        addAnnotation
          (getWorkModel_WorkModelScripts(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModelScripts"
           });			
        addAnnotation
          (getWorkModel_AttributeAliasList(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "AttributeAliasList"
           });			
        addAnnotation
          (getWorkModel_WorkModelUID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workModelUID"
           });			
        addAnnotation
          (workModelEntitiesEClass, 
           source, 
           new String[] {
             "name", "WorkModelEntities",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkModelEntities_WorkModelEntity(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModelEntity"
           });			
        addAnnotation
          (getWorkModelEntities_Expression(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "expression"
           });			
        addAnnotation
          (workModelEntityEClass, 
           source, 
           new String[] {
             "name", "WorkModelEntity",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkModelEntity_EntityQuery(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "EntityQuery"
           });			
        addAnnotation
          (getWorkModelEntity_Entities(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Entities"
           });			
        addAnnotation
          (getWorkModelEntity_DistributionStrategy(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "distributionStrategy"
           });			
        addAnnotation
          (workModelListEClass, 
           source, 
           new String[] {
             "name", "WorkModelList",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkModelList_StartPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startPosition"
           });			
        addAnnotation
          (getWorkModelList_EndPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endPosition"
           });			
        addAnnotation
          (getWorkModelList_Type(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "type"
           });			
        addAnnotation
          (workModelMappingEClass, 
           source, 
           new String[] {
             "name", "WorkModelMapping",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkModelMapping_TypeParamName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "TypeParamName"
           });			
        addAnnotation
          (getWorkModelMapping_ModelParamName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "ModelParamName"
           });			
        addAnnotation
          (getWorkModelMapping_DefaultValue(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "DefaultValue"
           });			
        addAnnotation
          (workModelScriptEClass, 
           source, 
           new String[] {
             "name", "WorkModelScript",
             "kind", "empty"
           });			
        addAnnotation
          (getWorkModelScript_ScriptBody(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "scriptBody"
           });			
        addAnnotation
          (getWorkModelScript_ScriptLanguage(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "scriptLanguage"
           });			
        addAnnotation
          (getWorkModelScript_ScriptLanguageExtension(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "scriptLanguageExtension"
           });			
        addAnnotation
          (getWorkModelScript_ScriptLanguageVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "scriptLanguageVersion"
           });			
        addAnnotation
          (getWorkModelScript_ScriptOperation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "scriptOperation"
           });			
        addAnnotation
          (getWorkModelScript_ScriptTypeID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "scriptTypeID"
           });			
        addAnnotation
          (workModelScriptsEClass, 
           source, 
           new String[] {
             "name", "WorkModelScripts",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkModelScripts_WorkModelScript(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModelScript"
           });			
        addAnnotation
          (workModelSpecificationEClass, 
           source, 
           new String[] {
             "name", "WorkModelSpecification",
             "kind", "elementOnly"
           });			
        addAnnotation
          (workModelTypeEClass, 
           source, 
           new String[] {
             "name", "WorkModelType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkModelType_WorkModelMapping(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModelMapping"
           });			
        addAnnotation
          (getWorkModelType_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });			
        addAnnotation
          (getWorkModelType_WorkTypeID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "workTypeID"
           });			
        addAnnotation
          (workModelTypesEClass, 
           source, 
           new String[] {
             "name", "WorkModelTypes",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkModelTypes_WorkModelType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModelType"
           });			
        addAnnotation
          (getWorkModelTypes_Expression(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "expression"
           });			
        addAnnotation
          (workTypeListEClass, 
           source, 
           new String[] {
             "name", "WorkTypeList",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkTypeList_StartPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "startPosition"
           });			
        addAnnotation
          (getWorkTypeList_EndPosition(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "endPosition"
           });			
        addAnnotation
          (getWorkTypeList_Types(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "types"
           });
    }

} //N2BRMPackageImpl
