/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Allocable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Allocable#getAllocationMethod <em>Allocation Method</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getAllocable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Allocable extends EObject {
    /**
     * Returns the value of the '<em><b>Allocation Method</b></em>' attribute.
     * The default value is <code>"ANY"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Allocation Method</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Allocation Method</em>' attribute.
     * @see #isSetAllocationMethod()
     * @see #unsetAllocationMethod()
     * @see #setAllocationMethod(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getAllocable_AllocationMethod()
     * @model default="ANY" unique="false" unsettable="true" required="true"
     * @generated
     */
    String getAllocationMethod();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Allocable#getAllocationMethod <em>Allocation Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocation Method</em>' attribute.
     * @see #isSetAllocationMethod()
     * @see #unsetAllocationMethod()
     * @see #getAllocationMethod()
     * @generated
     */
    void setAllocationMethod(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.om.core.om.Allocable#getAllocationMethod <em>Allocation Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAllocationMethod()
     * @see #getAllocationMethod()
     * @see #setAllocationMethod(String)
     * @generated
     */
    void unsetAllocationMethod();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.om.core.om.Allocable#getAllocationMethod <em>Allocation Method</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Allocation Method</em>' attribute is set.
     * @see #unsetAllocationMethod()
     * @see #getAllocationMethod()
     * @see #setAllocationMethod(String)
     * @generated
     */
    boolean isSetAllocationMethod();

} // Allocable
