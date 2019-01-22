/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capability</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Capabilities are a property of groups and define what tasks the resources
 *         associated with a group is able to perform; although, the interpretation
 *         of tasks and ability is specific to the client's environment. Capabilities
 *         may also be assigned directly to a resource.
 * 
 *         Positions and Groups may also have capabilities associated with them to
 *         restrict the human resources that may be assigned to them; i.e., only those
 *         human resources holding the required capabilities can hold the position, or
 *         group.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Capability#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getCapability()
 * @model extendedMetaData="name='Capability' kind='elementOnly'"
 * @generated
 */
public interface Capability extends NamedEntity {
    /**
     * Returns the value of the '<em><b>Qualifier</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Describes the qualifier attribute that can be used to refine any Capability
     *                 assignment. The CapabilityHolding will provide any value(s) for this qualifier.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Qualifier</em>' containment reference.
     * @see #setQualifier(Qualifier)
     * @see com.tibco.n2.directory.model.de.DePackage#getCapability_Qualifier()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='qualifier' namespace='##targetNamespace'"
     * @generated
     */
    Qualifier getQualifier();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Capability#getQualifier <em>Qualifier</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Qualifier</em>' containment reference.
     * @see #getQualifier()
     * @generated
     */
    void setQualifier(Qualifier value);

} // Capability
