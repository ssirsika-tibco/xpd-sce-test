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
 * A representation of the model object '<em><b>Position</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Defines an organizational Position. Organizational Positions are composite elements
 *         of OrgUnits. A sub-class of TypedEntity, it can take its type from the meta-model
 *         element PositionType.
 *         A Position can be filled by any number of human resources, but an ideal number can be
 *         specified. This ideal number is merely an indication, and a lower or higher
 *         number of human resource assignments can be made.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getReqCapability <em>Req Capability</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getPrivilegeHeld <em>Privilege Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getSystemAction <em>System Action</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getAllocMethod <em>Alloc Method</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getIdealNumber <em>Ideal Number</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Position#getPlugin <em>Plugin</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getPosition()
 * @model extendedMetaData="name='Position' kind='elementOnly'"
 * @generated
 */
public interface Position extends TypedEntity {
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
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_ChoiceGroup()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='choiceGroup:6'"
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
     *                 Identifies a Capability that Resources should hold in order to be a member of this Position.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Req Capability</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_ReqCapability()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='req-capability' namespace='##targetNamespace' group='#choiceGroup:6'"
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
     *                 Identifies a Privilege that member's of this Position will inherit. Member's will also
     *                 inherit those Privileges assigned to the Position's OrgUnit.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Privilege Held</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_PrivilegeHeld()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='privilege-held' namespace='##targetNamespace' group='#choiceGroup:6'"
     * @generated
     */
    EList<PrivilegeHolding> getPrivilegeHeld();

    /**
     * Returns the value of the '<em><b>System Action</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.SystemAction}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Identifies a System Action that might be performed on this Position, and the
     *                 Privilege (optionally qualified) the caller must hold in order to perform it.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>System Action</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_SystemAction()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='system-action' namespace='##targetNamespace' group='#choiceGroup:6'"
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
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_AllocMethod()
     * @model default="ANY" unsettable="true"
     *        extendedMetaData="kind='attribute' name='alloc-method'"
     * @generated
     */
    AllocationMethod getAllocMethod();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Position#getAllocMethod <em>Alloc Method</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Position#getAllocMethod <em>Alloc Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAllocMethod()
     * @see #getAllocMethod()
     * @see #setAllocMethod(AllocationMethod)
     * @generated
     */
    void unsetAllocMethod();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Position#getAllocMethod <em>Alloc Method</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *               Identifies the Position Feature reference from which this Position is derived, if any.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Feature</em>' reference.
     * @see #setFeature(Feature)
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_Feature()
     * @model resolveProxies="false"
     *        extendedMetaData="kind='attribute' name='feature'"
     * @generated
     */
    Feature getFeature();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Position#getFeature <em>Feature</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Feature</em>' reference.
     * @see #getFeature()
     * @generated
     */
    void setFeature(Feature value);

    /**
     * Returns the value of the '<em><b>Ideal Number</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Ideal Number</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Ideal Number</em>' attribute.
     * @see #isSetIdealNumber()
     * @see #unsetIdealNumber()
     * @see #setIdealNumber(long)
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_IdealNumber()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt"
     *        extendedMetaData="kind='attribute' name='ideal-number'"
     * @generated
     */
    long getIdealNumber();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Position#getIdealNumber <em>Ideal Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ideal Number</em>' attribute.
     * @see #isSetIdealNumber()
     * @see #unsetIdealNumber()
     * @see #getIdealNumber()
     * @generated
     */
    void setIdealNumber(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Position#getIdealNumber <em>Ideal Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIdealNumber()
     * @see #getIdealNumber()
     * @see #setIdealNumber(long)
     * @generated
     */
    void unsetIdealNumber();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Position#getIdealNumber <em>Ideal Number</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Ideal Number</em>' attribute is set.
     * @see #unsetIdealNumber()
     * @see #getIdealNumber()
     * @see #setIdealNumber(long)
     * @generated
     */
    boolean isSetIdealNumber();

    /**
     * Returns the value of the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location</em>' reference.
     * @see #setLocation(Location)
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_Location()
     * @model resolveProxies="false"
     *        extendedMetaData="kind='attribute' name='location'"
     * @generated
     */
    Location getLocation();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Position#getLocation <em>Location</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location</em>' reference.
     * @see #getLocation()
     * @generated
     */
    void setLocation(Location value);

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
     * @see com.tibco.n2.directory.model.de.DePackage#getPosition_Plugin()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='plugin'"
     * @generated
     */
    String getPlugin();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Position#getPlugin <em>Plugin</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Plugin</em>' attribute.
     * @see #getPlugin()
     * @generated
     */
    void setPlugin(String value);

} // Position
