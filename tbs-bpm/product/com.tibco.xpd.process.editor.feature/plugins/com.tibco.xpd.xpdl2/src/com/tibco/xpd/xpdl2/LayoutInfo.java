/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Layout Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.LayoutInfo#getPixelsPerMillimeter <em>Pixels Per Millimeter</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLayoutInfo()
 * @model extendedMetaData="name='LayoutInfo_._type' kind='elementOnly'"
 * @generated
 */
public interface LayoutInfo extends OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Pixels Per Millimeter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Pixels Per Millimeter</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Pixels Per Millimeter</em>' attribute.
     * @see #setPixelsPerMillimeter(float)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLayoutInfo_PixelsPerMillimeter()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Float"
     *        extendedMetaData="kind='attribute' name='PixelsPerMillimeter'"
     * @generated
     */
    float getPixelsPerMillimeter();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.LayoutInfo#getPixelsPerMillimeter <em>Pixels Per Millimeter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Pixels Per Millimeter</em>' attribute.
     * @see #getPixelsPerMillimeter()
     * @generated
     */
    void setPixelsPerMillimeter(float value);

} // LayoutInfo
