/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Icon</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Icon#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Icon#getHeight <em>Height</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Icon#getShape <em>Shape</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Icon#getWidth <em>Width</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Icon#getXCoord <em>XCoord</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Icon#getYCoord <em>YCoord</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIcon()
 * @model extendedMetaData="name='Icon_._type' kind='simple'"
 * @generated
 */
public interface Icon extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIcon_Value()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="name=':0' kind='simple'"
     * @generated
     */
    String getValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Icon#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #getValue()
     * @generated
     */
    void setValue(String value);

    /**
     * Returns the value of the '<em><b>Height</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Height</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Height</em>' attribute.
     * @see #setHeight(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIcon_Height()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='HEIGHT'"
     * @generated
     */
    BigInteger getHeight();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Icon#getHeight <em>Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Height</em>' attribute.
     * @see #getHeight()
     * @generated
     */
    void setHeight(BigInteger value);

    /**
     * Returns the value of the '<em><b>Shape</b></em>' attribute.
     * The default value is <code>"RoundRectangle"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.SHAPEType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Shape</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Shape</em>' attribute.
     * @see com.tibco.xpd.xpdl2.SHAPEType
     * @see #isSetShape()
     * @see #unsetShape()
     * @see #setShape(SHAPEType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIcon_Shape()
     * @model default="RoundRectangle" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='SHAPE'"
     * @generated
     */
    SHAPEType getShape();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Icon#getShape <em>Shape</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Shape</em>' attribute.
     * @see com.tibco.xpd.xpdl2.SHAPEType
     * @see #isSetShape()
     * @see #unsetShape()
     * @see #getShape()
     * @generated
     */
    void setShape(SHAPEType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Icon#getShape <em>Shape</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetShape()
     * @see #getShape()
     * @see #setShape(SHAPEType)
     * @generated
     */
    void unsetShape();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Icon#getShape <em>Shape</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Shape</em>' attribute is set.
     * @see #unsetShape()
     * @see #getShape()
     * @see #setShape(SHAPEType)
     * @generated
     */
    boolean isSetShape();

    /**
     * Returns the value of the '<em><b>Width</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Width</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Width</em>' attribute.
     * @see #setWidth(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIcon_Width()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='WIDTH'"
     * @generated
     */
    BigInteger getWidth();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Icon#getWidth <em>Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Width</em>' attribute.
     * @see #getWidth()
     * @generated
     */
    void setWidth(BigInteger value);

    /**
     * Returns the value of the '<em><b>XCoord</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>XCoord</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>XCoord</em>' attribute.
     * @see #setXCoord(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIcon_XCoord()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='XCOORD'"
     * @generated
     */
    BigInteger getXCoord();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Icon#getXCoord <em>XCoord</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>XCoord</em>' attribute.
     * @see #getXCoord()
     * @generated
     */
    void setXCoord(BigInteger value);

    /**
     * Returns the value of the '<em><b>YCoord</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>YCoord</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>YCoord</em>' attribute.
     * @see #setYCoord(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIcon_YCoord()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='YCOORD'"
     * @generated
     */
    BigInteger getYCoord();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Icon#getYCoord <em>YCoord</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>YCoord</em>' attribute.
     * @see #getYCoord()
     * @generated
     */
    void setYCoord(BigInteger value);

} // Icon
