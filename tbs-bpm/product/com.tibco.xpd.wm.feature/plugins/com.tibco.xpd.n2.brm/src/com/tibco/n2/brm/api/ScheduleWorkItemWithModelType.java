/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.organisation.api.XmlResourceQuery;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Schedule Work Item With Model Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemSchedule <em>Item Schedule</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemContext <em>Item Context</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemBody <em>Item Body</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getEntityQuery <em>Entity Query</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getWorkModelUID <em>Work Model UID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getWorkModelVersion <em>Work Model Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelType()
 * @model extendedMetaData="name='scheduleWorkItemWithModel_._type' kind='elementOnly'"
 * @generated
 */
public interface ScheduleWorkItemWithModelType extends EObject {
    /**
     * Returns the value of the '<em><b>Item Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The schedule period to be associated with the work item.   If no object is passed then the item with have no schedule and cannot be worked on.   If an empty object is passed BRM will default this to immediately available with no target date.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Schedule</em>' containment reference.
     * @see #setItemSchedule(ItemSchedule)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelType_ItemSchedule()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='itemSchedule'"
     * @generated
     */
    ItemSchedule getItemSchedule();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemSchedule <em>Item Schedule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Schedule</em>' containment reference.
     * @see #getItemSchedule()
     * @generated
     */
    void setItemSchedule(ItemSchedule value);

    /**
     * Returns the value of the '<em><b>Item Context</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The work item's context, as supplied by the application that scheduled the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Context</em>' containment reference.
     * @see #setItemContext(ItemContext)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelType_ItemContext()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='itemContext'"
     * @generated
     */
    ItemContext getItemContext();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemContext <em>Item Context</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Context</em>' containment reference.
     * @see #getItemContext()
     * @generated
     */
    void setItemContext(ItemContext value);

    /**
     * Returns the value of the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The work item body, containing each data field (as a name/value pair) required by the data model in the work item's work type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Body</em>' containment reference.
     * @see #setItemBody(ItemBody)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelType_ItemBody()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='itemBody'"
     * @generated
     */
    ItemBody getItemBody();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getItemBody <em>Item Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Body</em>' containment reference.
     * @see #getItemBody()
     * @generated
     */
    void setItemBody(ItemBody value);

    /**
     * Returns the value of the '<em><b>Entity Query</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * If specified, this query will be used to determine the entity that is used to schedule the work item. 
     * 
     * It will override any settings associated with the work model being used.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Query</em>' containment reference.
     * @see #setEntityQuery(XmlResourceQuery)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelType_EntityQuery()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='entityQuery'"
     * @generated
     */
    XmlResourceQuery getEntityQuery();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getEntityQuery <em>Entity Query</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity Query</em>' containment reference.
     * @see #getEntityQuery()
     * @generated
     */
    void setEntityQuery(XmlResourceQuery value);

    /**
     * Returns the value of the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The ID of the group associated with this operation, if there is one.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #setGroupID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelType_GroupID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='attribute' name='groupID'"
     * @generated
     */
    long getGroupID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #getGroupID()
     * @generated
     */
    void setGroupID(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    void unsetGroupID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getGroupID <em>Group ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Group ID</em>' attribute is set.
     * @see #unsetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    boolean isSetGroupID();

    /**
     * Returns the value of the '<em><b>Work Model UID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The UID of the work model to scehdule this item against.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Model UID</em>' attribute.
     * @see #setWorkModelUID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelType_WorkModelUID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='workModelUID'"
     * @generated
     */
    String getWorkModelUID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getWorkModelUID <em>Work Model UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model UID</em>' attribute.
     * @see #getWorkModelUID()
     * @generated
     */
    void setWorkModelUID(String value);

    /**
     * Returns the value of the '<em><b>Work Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The OSGi version range string of the work model to use.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Model Version</em>' attribute.
     * @see #setWorkModelVersion(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelType_WorkModelVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='workModelVersion'"
     * @generated
     */
    String getWorkModelVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelType#getWorkModelVersion <em>Work Model Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model Version</em>' attribute.
     * @see #getWorkModelVersion()
     * @generated
     */
    void setWorkModelVersion(String value);

} // ScheduleWorkItemWithModelType
