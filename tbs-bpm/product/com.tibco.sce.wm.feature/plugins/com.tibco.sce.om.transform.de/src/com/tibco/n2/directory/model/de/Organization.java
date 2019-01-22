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
 * A representation of the model object '<em><b>Organization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Defines an organization as a legal entity in which organizational units reside.
 *         A meta-model can define several types of organization, and references between
 *         the organizational units can span organizations.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Organization#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Organization#getOrgUnit <em>Org Unit</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Organization#getSystemAction <em>System Action</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Organization#getAllocMethod <em>Alloc Method</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Organization#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Organization#getPlugin <em>Plugin</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getOrganization()
 * @model extendedMetaData="name='Organization' kind='elementOnly'"
 * @generated
 */
public interface Organization extends TypedEntity {
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
     * @see com.tibco.n2.directory.model.de.DePackage#getOrganization_ChoiceGroup()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='choiceGroup:6'"
     * @generated
     */
    FeatureMap getChoiceGroup();

    /**
     * Returns the value of the '<em><b>Org Unit</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.OrgUnit}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Defines an OrgUnit contained within this Organization. A heirarchy of
     *                 OrgUnits can be created by nesting org-unit elements.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Org Unit</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getOrganization_OrgUnit()
     * @model containment="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='org-unit' namespace='##targetNamespace' group='#choiceGroup:6'"
     * @generated
     */
    EList<OrgUnit> getOrgUnit();

    /**
     * Returns the value of the '<em><b>System Action</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.SystemAction}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Identifies a System Action that might be performed on this Organization,
     *                 and the Privilege (optionally qualified) the caller must hold in order
     *                 to perform it.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>System Action</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getOrganization_SystemAction()
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
     * @see com.tibco.n2.directory.model.de.DePackage#getOrganization_AllocMethod()
     * @model default="ANY" unsettable="true"
     *        extendedMetaData="kind='attribute' name='alloc-method'"
     * @generated
     */
    AllocationMethod getAllocMethod();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Organization#getAllocMethod <em>Alloc Method</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Organization#getAllocMethod <em>Alloc Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAllocMethod()
     * @see #getAllocMethod()
     * @see #setAllocMethod(AllocationMethod)
     * @generated
     */
    void unsetAllocMethod();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Organization#getAllocMethod <em>Alloc Method</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *               Identifies the Location from which this Organization will take its locality.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Location</em>' reference.
     * @see #setLocation(Location)
     * @see com.tibco.n2.directory.model.de.DePackage#getOrganization_Location()
     * @model resolveProxies="false"
     *        extendedMetaData="kind='attribute' name='location'"
     * @generated
     */
    Location getLocation();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Organization#getLocation <em>Location</em>}' reference.
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
     * @see com.tibco.n2.directory.model.de.DePackage#getOrganization_Plugin()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='plugin'"
     * @generated
     */
    String getPlugin();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Organization#getPlugin <em>Plugin</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Plugin</em>' attribute.
     * @see #getPlugin()
     * @generated
     */
    void setPlugin(String value);

} // Organization
