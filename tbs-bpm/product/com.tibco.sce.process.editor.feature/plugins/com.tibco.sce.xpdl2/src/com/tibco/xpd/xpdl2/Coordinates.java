/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Coordinates</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Coordinates#getXCoordinate <em>XCoordinate</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Coordinates#getYCoordinate <em>YCoordinate</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getCoordinates()
 * @model extendedMetaData="name='Coordinates_._type' kind='elementOnly'"
 * @generated
 */
public interface Coordinates extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>XCoordinate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XCoordinate</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>XCoordinate</em>' attribute.
     * @see #isSetXCoordinate()
     * @see #unsetXCoordinate()
     * @see #setXCoordinate(double)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getCoordinates_XCoordinate()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
     *        extendedMetaData="kind='attribute' name='XCoordinate'"
     * @generated
     */
    double getXCoordinate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Coordinates#getXCoordinate <em>XCoordinate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>XCoordinate</em>' attribute.
     * @see #isSetXCoordinate()
     * @see #unsetXCoordinate()
     * @see #getXCoordinate()
     * @generated
     */
    void setXCoordinate(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Coordinates#getXCoordinate <em>XCoordinate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetXCoordinate()
     * @see #getXCoordinate()
     * @see #setXCoordinate(double)
     * @generated
     */
    void unsetXCoordinate();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Coordinates#getXCoordinate <em>XCoordinate</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>XCoordinate</em>' attribute is set.
     * @see #unsetXCoordinate()
     * @see #getXCoordinate()
     * @see #setXCoordinate(double)
     * @generated
     */
    boolean isSetXCoordinate();

    /**
     * Returns the value of the '<em><b>YCoordinate</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>YCoordinate</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>YCoordinate</em>' attribute.
     * @see #isSetYCoordinate()
     * @see #unsetYCoordinate()
     * @see #setYCoordinate(double)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getCoordinates_YCoordinate()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
     *        extendedMetaData="kind='attribute' name='YCoordinate'"
     * @generated
     */
    double getYCoordinate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Coordinates#getYCoordinate <em>YCoordinate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>YCoordinate</em>' attribute.
     * @see #isSetYCoordinate()
     * @see #unsetYCoordinate()
     * @see #getYCoordinate()
     * @generated
     */
    void setYCoordinate(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Coordinates#getYCoordinate <em>YCoordinate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetYCoordinate()
     * @see #getYCoordinate()
     * @see #setYCoordinate(double)
     * @generated
     */
    void unsetYCoordinate();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Coordinates#getYCoordinate <em>YCoordinate</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>YCoordinate</em>' attribute is set.
     * @see #unsetYCoordinate()
     * @see #getYCoordinate()
     * @see #setYCoordinate(double)
     * @generated
     */
    boolean isSetYCoordinate();

} // Coordinates
