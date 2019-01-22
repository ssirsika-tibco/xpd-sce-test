/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextPackage#getDomainValue()
 * @model
 * @generated
 */
public interface DomainValue extends EObject {
    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Value</em>' attribute.
     * @see #setValue(String)
     * @see com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextPackage#getDomainValue_Value()
     * @model required="true"
     * @generated
     */
    String getValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #getValue()
     * @generated
     */
    void setValue(String value);

} // DomainValue
