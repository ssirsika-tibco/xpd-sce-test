/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ModelElement#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getModelElement()
 * @model abstract="true"
 * @generated
 */
public interface ModelElement extends EObject {
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getModelElement_Id()
     * @model id="true" required="true"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channeltypes.ModelElement#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

} // ModelElement
