/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Position</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Position#getIdealNumber <em>Ideal Number</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Position#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Position#getPositionType <em>Position Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getPosition()
 * @model
 * @generated
 */
public interface Position extends OrgTypedElement, Authorizable, Capable,
        Allocable, AssociableWithResources, Locatable, OrgNode {
    /**
     * Returns the value of the '<em><b>Ideal Number</b></em>' attribute.
     * The default value is <code>"1"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Ideal Number</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Ideal Number</em>' attribute.
     * @see #setIdealNumber(int)
     * @see com.tibco.xpd.om.core.om.OMPackage#getPosition_IdealNumber()
     * @model default="1" required="true"
     * @generated
     */
    int getIdealNumber();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Position#getIdealNumber <em>Ideal Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ideal Number</em>' attribute.
     * @see #getIdealNumber()
     * @generated
     */
    void setIdealNumber(int value);

    /**
     * Returns the value of the '<em><b>Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Feature</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Feature</em>' reference.
     * @see #setFeature(PositionFeature)
     * @see com.tibco.xpd.om.core.om.OMPackage#getPosition_Feature()
     * @model
     * @generated
     */
    PositionFeature getFeature();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Position#getFeature <em>Feature</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Feature</em>' reference.
     * @see #getFeature()
     * @generated
     */
    void setFeature(PositionFeature value);

    /**
     * Returns the value of the '<em><b>Position Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Position Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Position Type</em>' reference.
     * @see com.tibco.xpd.om.core.om.OMPackage#getPosition_PositionType()
     * @model resolveProxies="false" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    PositionType getPositionType();

} // Position
