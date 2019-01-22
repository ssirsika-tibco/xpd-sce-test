/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Method Authorisation Action</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.brm.api.N2BRMPackage#getMethodAuthorisationAction()
 * @model extendedMetaData="name='MethodAuthorisationAction'"
 * @generated
 */
public enum MethodAuthorisationAction implements Enumerator {
    /**
     * The '<em><b>Schedule Work Item</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SCHEDULE_WORK_ITEM_VALUE
     * @generated
     * @ordered
     */
    SCHEDULE_WORK_ITEM(0, "scheduleWorkItem", "scheduleWorkItem"),

    /**
     * The '<em><b>Cancel Work Item</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CANCEL_WORK_ITEM_VALUE
     * @generated
     * @ordered
     */
    CANCEL_WORK_ITEM(1, "cancelWorkItem", "cancelWorkItem"),

    /**
     * The '<em><b>Suspend Work Item</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SUSPEND_WORK_ITEM_VALUE
     * @generated
     * @ordered
     */
    SUSPEND_WORK_ITEM(2, "suspendWorkItem", "suspendWorkItem"),

    /**
     * The '<em><b>View Work List</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #VIEW_WORK_LIST_VALUE
     * @generated
     * @ordered
     */
    VIEW_WORK_LIST(3, "viewWorkList", "viewWorkList"),

    /**
     * The '<em><b>Set Resource Order Filter Criteria</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SET_RESOURCE_ORDER_FILTER_CRITERIA_VALUE
     * @generated
     * @ordered
     */
    SET_RESOURCE_ORDER_FILTER_CRITERIA(4, "setResourceOrderFilterCriteria", "setResourceOrderFilterCriteria"),

    /**
     * The '<em><b>Open Other Resources Items</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OPEN_OTHER_RESOURCES_ITEMS_VALUE
     * @generated
     * @ordered
     */
    OPEN_OTHER_RESOURCES_ITEMS(5, "openOtherResourcesItems", "openOtherResourcesItems"),

    /**
     * The '<em><b>Close Other Resources Items</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CLOSE_OTHER_RESOURCES_ITEMS_VALUE
     * @generated
     * @ordered
     */
    CLOSE_OTHER_RESOURCES_ITEMS(6, "closeOtherResourcesItems", "closeOtherResourcesItems"),

    /**
     * The '<em><b>Skip Work Item</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SKIP_WORK_ITEM_VALUE
     * @generated
     * @ordered
     */
    SKIP_WORK_ITEM(7, "skipWorkItem", "skipWorkItem"),

    /**
     * The '<em><b>Reallocate Work Item To World</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REALLOCATE_WORK_ITEM_TO_WORLD_VALUE
     * @generated
     * @ordered
     */
    REALLOCATE_WORK_ITEM_TO_WORLD(8, "reallocateWorkItemToWorld", "reallocateWorkItemToWorld"),

    /**
     * The '<em><b>Reallocate To Offer Set</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REALLOCATE_TO_OFFER_SET_VALUE
     * @generated
     * @ordered
     */
    REALLOCATE_TO_OFFER_SET(9, "reallocateToOfferSet", "reallocateToOfferSet"),

    /**
     * The '<em><b>Pend Work Item</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PEND_WORK_ITEM_VALUE
     * @generated
     * @ordered
     */
    PEND_WORK_ITEM(10, "pendWorkItem", "pendWorkItem"),

    /**
     * The '<em><b>Work Item Allocation</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #WORK_ITEM_ALLOCATION_VALUE
     * @generated
     * @ordered
     */
    WORK_ITEM_ALLOCATION(11, "workItemAllocation", "workItemAllocation"),

    /**
     * The '<em><b>Auto Open Next Work Item</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #AUTO_OPEN_NEXT_WORK_ITEM_VALUE
     * @generated
     * @ordered
     */
    AUTO_OPEN_NEXT_WORK_ITEM(12, "autoOpenNextWorkItem", "autoOpenNextWorkItem"),

    /**
     * The '<em><b>Reschedule Work Item</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RESCHEDULE_WORK_ITEM_VALUE
     * @generated
     * @ordered
     */
    RESCHEDULE_WORK_ITEM(13, "rescheduleWorkItem", "rescheduleWorkItem"),

    /**
     * The '<em><b>Enable Work Item</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ENABLE_WORK_ITEM_VALUE
     * @generated
     * @ordered
     */
    ENABLE_WORK_ITEM(14, "enableWorkItem", "enableWorkItem"),

    /**
     * The '<em><b>Change Allocated Work Item Priority</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CHANGE_ALLOCATED_WORK_ITEM_PRIORITY_VALUE
     * @generated
     * @ordered
     */
    CHANGE_ALLOCATED_WORK_ITEM_PRIORITY(15, "changeAllocatedWorkItemPriority", "changeAllocatedWorkItemPriority"),

    /**
     * The '<em><b>Change Any Work Item Priority</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #CHANGE_ANY_WORK_ITEM_PRIORITY_VALUE
     * @generated
     * @ordered
     */
    CHANGE_ANY_WORK_ITEM_PRIORITY(16, "changeAnyWorkItemPriority", "changeAnyWorkItemPriority"),

    /**
     * The '<em><b>View Global Work List</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #VIEW_GLOBAL_WORK_LIST_VALUE
     * @generated
     * @ordered
     */
    VIEW_GLOBAL_WORK_LIST(17, "viewGlobalWorkList", "viewGlobalWorkList");

    /**
     * The '<em><b>Schedule Work Item</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Schedule Work Item</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SCHEDULE_WORK_ITEM
     * @model name="scheduleWorkItem"
     * @generated
     * @ordered
     */
    public static final int SCHEDULE_WORK_ITEM_VALUE = 0;

    /**
     * The '<em><b>Cancel Work Item</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Cancel Work Item</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CANCEL_WORK_ITEM
     * @model name="cancelWorkItem"
     * @generated
     * @ordered
     */
    public static final int CANCEL_WORK_ITEM_VALUE = 1;

    /**
     * The '<em><b>Suspend Work Item</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Suspend Work Item</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SUSPEND_WORK_ITEM
     * @model name="suspendWorkItem"
     * @generated
     * @ordered
     */
    public static final int SUSPEND_WORK_ITEM_VALUE = 2;

    /**
     * The '<em><b>View Work List</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>View Work List</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #VIEW_WORK_LIST
     * @model name="viewWorkList"
     * @generated
     * @ordered
     */
    public static final int VIEW_WORK_LIST_VALUE = 3;

    /**
     * The '<em><b>Set Resource Order Filter Criteria</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Set Resource Order Filter Criteria</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SET_RESOURCE_ORDER_FILTER_CRITERIA
     * @model name="setResourceOrderFilterCriteria"
     * @generated
     * @ordered
     */
    public static final int SET_RESOURCE_ORDER_FILTER_CRITERIA_VALUE = 4;

    /**
     * The '<em><b>Open Other Resources Items</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Open Other Resources Items</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OPEN_OTHER_RESOURCES_ITEMS
     * @model name="openOtherResourcesItems"
     * @generated
     * @ordered
     */
    public static final int OPEN_OTHER_RESOURCES_ITEMS_VALUE = 5;

    /**
     * The '<em><b>Close Other Resources Items</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Close Other Resources Items</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CLOSE_OTHER_RESOURCES_ITEMS
     * @model name="closeOtherResourcesItems"
     * @generated
     * @ordered
     */
    public static final int CLOSE_OTHER_RESOURCES_ITEMS_VALUE = 6;

    /**
     * The '<em><b>Skip Work Item</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Skip Work Item</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SKIP_WORK_ITEM
     * @model name="skipWorkItem"
     * @generated
     * @ordered
     */
    public static final int SKIP_WORK_ITEM_VALUE = 7;

    /**
     * The '<em><b>Reallocate Work Item To World</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Reallocate Work Item To World</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REALLOCATE_WORK_ITEM_TO_WORLD
     * @model name="reallocateWorkItemToWorld"
     * @generated
     * @ordered
     */
    public static final int REALLOCATE_WORK_ITEM_TO_WORLD_VALUE = 8;

    /**
     * The '<em><b>Reallocate To Offer Set</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Reallocate To Offer Set</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REALLOCATE_TO_OFFER_SET
     * @model name="reallocateToOfferSet"
     * @generated
     * @ordered
     */
    public static final int REALLOCATE_TO_OFFER_SET_VALUE = 9;

    /**
     * The '<em><b>Pend Work Item</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Pend Work Item</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PEND_WORK_ITEM
     * @model name="pendWorkItem"
     * @generated
     * @ordered
     */
    public static final int PEND_WORK_ITEM_VALUE = 10;

    /**
     * The '<em><b>Work Item Allocation</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Work Item Allocation</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #WORK_ITEM_ALLOCATION
     * @model name="workItemAllocation"
     * @generated
     * @ordered
     */
    public static final int WORK_ITEM_ALLOCATION_VALUE = 11;

    /**
     * The '<em><b>Auto Open Next Work Item</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Auto Open Next Work Item</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #AUTO_OPEN_NEXT_WORK_ITEM
     * @model name="autoOpenNextWorkItem"
     * @generated
     * @ordered
     */
    public static final int AUTO_OPEN_NEXT_WORK_ITEM_VALUE = 12;

    /**
     * The '<em><b>Reschedule Work Item</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Reschedule Work Item</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESCHEDULE_WORK_ITEM
     * @model name="rescheduleWorkItem"
     * @generated
     * @ordered
     */
    public static final int RESCHEDULE_WORK_ITEM_VALUE = 13;

    /**
     * The '<em><b>Enable Work Item</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Enable Work Item</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ENABLE_WORK_ITEM
     * @model name="enableWorkItem"
     * @generated
     * @ordered
     */
    public static final int ENABLE_WORK_ITEM_VALUE = 14;

    /**
     * The '<em><b>Change Allocated Work Item Priority</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Change Allocated Work Item Priority</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CHANGE_ALLOCATED_WORK_ITEM_PRIORITY
     * @model name="changeAllocatedWorkItemPriority"
     * @generated
     * @ordered
     */
    public static final int CHANGE_ALLOCATED_WORK_ITEM_PRIORITY_VALUE = 15;

    /**
     * The '<em><b>Change Any Work Item Priority</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Change Any Work Item Priority</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #CHANGE_ANY_WORK_ITEM_PRIORITY
     * @model name="changeAnyWorkItemPriority"
     * @generated
     * @ordered
     */
    public static final int CHANGE_ANY_WORK_ITEM_PRIORITY_VALUE = 16;

    /**
     * The '<em><b>View Global Work List</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>View Global Work List</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #VIEW_GLOBAL_WORK_LIST
     * @model name="viewGlobalWorkList"
     * @generated
     * @ordered
     */
    public static final int VIEW_GLOBAL_WORK_LIST_VALUE = 17;

    /**
     * An array of all the '<em><b>Method Authorisation Action</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final MethodAuthorisationAction[] VALUES_ARRAY =
        new MethodAuthorisationAction[] {
            SCHEDULE_WORK_ITEM,
            CANCEL_WORK_ITEM,
            SUSPEND_WORK_ITEM,
            VIEW_WORK_LIST,
            SET_RESOURCE_ORDER_FILTER_CRITERIA,
            OPEN_OTHER_RESOURCES_ITEMS,
            CLOSE_OTHER_RESOURCES_ITEMS,
            SKIP_WORK_ITEM,
            REALLOCATE_WORK_ITEM_TO_WORLD,
            REALLOCATE_TO_OFFER_SET,
            PEND_WORK_ITEM,
            WORK_ITEM_ALLOCATION,
            AUTO_OPEN_NEXT_WORK_ITEM,
            RESCHEDULE_WORK_ITEM,
            ENABLE_WORK_ITEM,
            CHANGE_ALLOCATED_WORK_ITEM_PRIORITY,
            CHANGE_ANY_WORK_ITEM_PRIORITY,
            VIEW_GLOBAL_WORK_LIST,
        };

    /**
     * A public read-only list of all the '<em><b>Method Authorisation Action</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<MethodAuthorisationAction> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Method Authorisation Action</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static MethodAuthorisationAction get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            MethodAuthorisationAction result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Method Authorisation Action</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static MethodAuthorisationAction getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            MethodAuthorisationAction result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Method Authorisation Action</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static MethodAuthorisationAction get(int value) {
        switch (value) {
            case SCHEDULE_WORK_ITEM_VALUE: return SCHEDULE_WORK_ITEM;
            case CANCEL_WORK_ITEM_VALUE: return CANCEL_WORK_ITEM;
            case SUSPEND_WORK_ITEM_VALUE: return SUSPEND_WORK_ITEM;
            case VIEW_WORK_LIST_VALUE: return VIEW_WORK_LIST;
            case SET_RESOURCE_ORDER_FILTER_CRITERIA_VALUE: return SET_RESOURCE_ORDER_FILTER_CRITERIA;
            case OPEN_OTHER_RESOURCES_ITEMS_VALUE: return OPEN_OTHER_RESOURCES_ITEMS;
            case CLOSE_OTHER_RESOURCES_ITEMS_VALUE: return CLOSE_OTHER_RESOURCES_ITEMS;
            case SKIP_WORK_ITEM_VALUE: return SKIP_WORK_ITEM;
            case REALLOCATE_WORK_ITEM_TO_WORLD_VALUE: return REALLOCATE_WORK_ITEM_TO_WORLD;
            case REALLOCATE_TO_OFFER_SET_VALUE: return REALLOCATE_TO_OFFER_SET;
            case PEND_WORK_ITEM_VALUE: return PEND_WORK_ITEM;
            case WORK_ITEM_ALLOCATION_VALUE: return WORK_ITEM_ALLOCATION;
            case AUTO_OPEN_NEXT_WORK_ITEM_VALUE: return AUTO_OPEN_NEXT_WORK_ITEM;
            case RESCHEDULE_WORK_ITEM_VALUE: return RESCHEDULE_WORK_ITEM;
            case ENABLE_WORK_ITEM_VALUE: return ENABLE_WORK_ITEM;
            case CHANGE_ALLOCATED_WORK_ITEM_PRIORITY_VALUE: return CHANGE_ALLOCATED_WORK_ITEM_PRIORITY;
            case CHANGE_ANY_WORK_ITEM_PRIORITY_VALUE: return CHANGE_ANY_WORK_ITEM_PRIORITY;
            case VIEW_GLOBAL_WORK_LIST_VALUE: return VIEW_GLOBAL_WORK_LIST;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private MethodAuthorisationAction(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
      return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
      return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral() {
      return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }
    
} //MethodAuthorisationAction
