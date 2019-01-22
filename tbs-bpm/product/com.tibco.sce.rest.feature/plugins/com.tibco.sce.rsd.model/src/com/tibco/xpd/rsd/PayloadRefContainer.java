/**
 */
package com.tibco.xpd.rsd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Payload Ref Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.PayloadRefContainer#getPayloadReference <em>Payload Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getPayloadRefContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface PayloadRefContainer extends EObject {
    /**
     * Returns the value of the '<em><b>Payload Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Payload Reference</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Payload Reference</em>' containment reference.
     * @see #setPayloadReference(PayloadReference)
     * @see com.tibco.xpd.rsd.RsdPackage#getPayloadRefContainer_PayloadReference()
     * @model containment="true"
     * @generated
     */
    PayloadReference getPayloadReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.PayloadRefContainer#getPayloadReference <em>Payload Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Payload Reference</em>' containment reference.
     * @see #getPayloadReference()
     * @generated
     */
    void setPayloadReference(PayloadReference value);

} // PayloadRefContainer
