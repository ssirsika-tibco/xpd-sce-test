/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Groups define a job description, which can be further refined by sub-groups. For example,
 *         the group "developer" may be refined by sub-groups "support" and "product".
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Group#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Group#getReqCapability <em>Req Capability</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Group#getPrivilegeHeld <em>Privilege Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Group#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Group#getSystemAction <em>System Action</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Group#getAllocMethod <em>Alloc Method</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Group#getPlugin <em>Plugin</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Group#isUndeliveredQueue <em>Undelivered Queue</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getGroup()
 * @model extendedMetaData="name='Group' kind='elementOnly'"
 * @generated
 */
public interface Group extends NamedEntity {
    /**
     * Returns the value of the '<em><b>Choice Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Choice Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Choice Group</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getGroup_ChoiceGroup()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='choiceGroup:4'"
     * @generated
     */
    FeatureMap getChoiceGroup();

    /**
     * Returns the value of the '<em><b>Req Capability</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.CapabilityHolding}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Identifies a Capability that Resources should hold in order to be a member of this Group.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Req Capability</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getGroup_ReqCapability()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='req-capability' namespace='##targetNamespace' group='#choiceGroup:4'"
     * @generated
     */
    EList<CapabilityHolding> getReqCapability();

    /**
     * Returns the value of the '<em><b>Privilege Held</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.PrivilegeHolding}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Identifies a Privilege that member's of this Group will inherit. Member's will also
     *                 inherit those Privileges assigned to a Group's parent Groups.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Privilege Held</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getGroup_PrivilegeHeld()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='privilege-held' namespace='##targetNamespace' group='#choiceGroup:4'"
     * @generated
     */
    EList<PrivilegeHolding> getPrivilegeHeld();

    /**
     * Returns the value of the '<em><b>Group</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.Group}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Defines sub-groups to this Group; creating a hierarchical structure.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Group</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getGroup_Group()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='group' namespace='##targetNamespace' group='#choiceGroup:4'"
     * @generated
     */
    EList<Group> getGroup();

    /**
     * Returns the value of the '<em><b>System Action</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.SystemAction}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Identifies a System Action that might be performed on this Group, and the
     *                 Privilege (optionally qualified) the caller must hold in order to perform it.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>System Action</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getGroup_SystemAction()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='system-action' namespace='##targetNamespace' group='#choiceGroup:4'"
     * @generated
     */
    EList<SystemAction> getSystemAction();

    /**
     * Returns the value of the '<em><b>Alloc Method</b></em>' attribute.
     * The default value is <code>"ANY"</code>.
     * The literals are from the enumeration {@link com.tibco.n2.directory.model.de.AllocationMethod}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Alloc Method</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Alloc Method</em>' attribute.
     * @see com.tibco.n2.directory.model.de.AllocationMethod
     * @see #isSetAllocMethod()
     * @see #unsetAllocMethod()
     * @see #setAllocMethod(AllocationMethod)
     * @see com.tibco.n2.directory.model.de.DePackage#getGroup_AllocMethod()
     * @model default="ANY" unsettable="true"
     *        extendedMetaData="kind='attribute' name='alloc-method'"
     * @generated
     */
    AllocationMethod getAllocMethod();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Group#getAllocMethod <em>Alloc Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Alloc Method</em>' attribute.
     * @see com.tibco.n2.directory.model.de.AllocationMethod
     * @see #isSetAllocMethod()
     * @see #unsetAllocMethod()
     * @see #getAllocMethod()
     * @generated
     */
    void setAllocMethod(AllocationMethod value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Group#getAllocMethod <em>Alloc Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAllocMethod()
     * @see #getAllocMethod()
     * @see #setAllocMethod(AllocationMethod)
     * @generated
     */
    void unsetAllocMethod();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Group#getAllocMethod <em>Alloc Method</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Alloc Method</em>' attribute is set.
     * @see #unsetAllocMethod()
     * @see #getAllocMethod()
     * @see #setAllocMethod(AllocationMethod)
     * @generated
     */
    boolean isSetAllocMethod();

    /**
     * Returns the value of the '<em><b>Plugin</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Plugin</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Plugin</em>' attribute.
     * @see #setPlugin(String)
     * @see com.tibco.n2.directory.model.de.DePackage#getGroup_Plugin()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='plugin'"
     * @generated
     */
    String getPlugin();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Group#getPlugin <em>Plugin</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Plugin</em>' attribute.
     * @see #getPlugin()
     * @generated
     */
    void setPlugin(String value);

    /**
     * Returns the value of the '<em><b>Undelivered Queue</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *               Only to be used by the 'system' org-model (version 0), to identify the Group to which
     *               'undelivered' work-items should be assigned.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Undelivered Queue</em>' attribute.
     * @see #isSetUndeliveredQueue()
     * @see #unsetUndeliveredQueue()
     * @see #setUndeliveredQueue(boolean)
     * @see com.tibco.n2.directory.model.de.DePackage#getGroup_UndeliveredQueue()
     * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='undelivered-queue'"
     * @generated
     */
    boolean isUndeliveredQueue();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Group#isUndeliveredQueue <em>Undelivered Queue</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Undelivered Queue</em>' attribute.
     * @see #isSetUndeliveredQueue()
     * @see #unsetUndeliveredQueue()
     * @see #isUndeliveredQueue()
     * @generated
     */
    void setUndeliveredQueue(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Group#isUndeliveredQueue <em>Undelivered Queue</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetUndeliveredQueue()
     * @see #isUndeliveredQueue()
     * @see #setUndeliveredQueue(boolean)
     * @generated
     */
    void unsetUndeliveredQueue();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Group#isUndeliveredQueue <em>Undelivered Queue</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Undelivered Queue</em>' attribute is set.
     * @see #unsetUndeliveredQueue()
     * @see #isUndeliveredQueue()
     * @see #setUndeliveredQueue(boolean)
     * @generated
     */
    boolean isSetUndeliveredQueue();

} // Group
