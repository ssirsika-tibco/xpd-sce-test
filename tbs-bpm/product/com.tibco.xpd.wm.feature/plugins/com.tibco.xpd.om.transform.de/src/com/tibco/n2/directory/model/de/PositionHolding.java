/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Position Holding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         A sub-element of Resource (only for human resources), this element records the assignment
 *         of a human resource to a Position.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.PositionHolding#getPosition <em>Position</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getPositionHolding()
 * @model extendedMetaData="name='PositionHolding' kind='empty'"
 * @generated
 */
public interface PositionHolding extends EObject {
    /**
     * Returns the value of the '<em><b>Position</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Position</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Position</em>' reference.
     * @see #setPosition(Position)
     * @see com.tibco.n2.directory.model.de.DePackage#getPositionHolding_Position()
     * @model resolveProxies="false" required="true"
     *        extendedMetaData="kind='attribute' name='position'"
     * @generated
     */
    Position getPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.PositionHolding#getPosition <em>Position</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Position</em>' reference.
     * @see #getPosition()
     * @generated
     */
    void setPosition(Position value);

} // PositionHolding
