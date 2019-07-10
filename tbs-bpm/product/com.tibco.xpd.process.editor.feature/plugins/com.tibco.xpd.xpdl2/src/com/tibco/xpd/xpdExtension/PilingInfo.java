/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Piling Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element is added under Activity Resource Patterns for piling information
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.PilingInfo#isPilingAllowed <em>Piling Allowed</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.PilingInfo#getMaxPiliableItems <em>Max Piliable Items</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPilingInfo()
 * @model extendedMetaData="name='PilingInformation' kind='empty'"
 * @generated
 */
public interface PilingInfo extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Piling Allowed</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Piling Allowed</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Piling Allowed</em>' attribute.
     * @see #isSetPilingAllowed()
     * @see #unsetPilingAllowed()
     * @see #setPilingAllowed(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPilingInfo_PilingAllowed()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='PilingAllowed'"
     * @generated
     */
    boolean isPilingAllowed();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.PilingInfo#isPilingAllowed <em>Piling Allowed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Piling Allowed</em>' attribute.
     * @see #isSetPilingAllowed()
     * @see #unsetPilingAllowed()
     * @see #isPilingAllowed()
     * @generated
     */
    void setPilingAllowed(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.PilingInfo#isPilingAllowed <em>Piling Allowed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPilingAllowed()
     * @see #isPilingAllowed()
     * @see #setPilingAllowed(boolean)
     * @generated
     */
    void unsetPilingAllowed();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.PilingInfo#isPilingAllowed <em>Piling Allowed</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Piling Allowed</em>' attribute is set.
     * @see #unsetPilingAllowed()
     * @see #isPilingAllowed()
     * @see #setPilingAllowed(boolean)
     * @generated
     */
    boolean isSetPilingAllowed();

    /**
     * Returns the value of the '<em><b>Max Piliable Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max Piliable Items</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Max Piliable Items</em>' attribute.
     * @see #setMaxPiliableItems(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPilingInfo_MaxPiliableItems()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='MaxPilableItems'"
     * @generated
     */
    String getMaxPiliableItems();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.PilingInfo#getMaxPiliableItems <em>Max Piliable Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Piliable Items</em>' attribute.
     * @see #getMaxPiliableItems()
     * @generated
     */
    void setMaxPiliableItems(String value);

} // PilingInfo
