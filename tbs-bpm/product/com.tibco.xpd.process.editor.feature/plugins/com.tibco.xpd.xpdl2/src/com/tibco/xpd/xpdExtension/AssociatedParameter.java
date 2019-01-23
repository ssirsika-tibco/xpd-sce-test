/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.ModeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Associated Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedParameter#getFormalParam <em>Formal Param</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedParameter#getMode <em>Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedParameter#isMandatory <em>Mandatory</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParameter()
 * @model extendedMetaData="name='AssociatedParameter' kind='elementOnly'"
 * @generated
 */
public interface AssociatedParameter extends DescribedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Formal Param</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Formal Param</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Formal Param</em>' attribute.
     * @see #setFormalParam(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParameter_FormalParam()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='FormalParam'"
     * @generated
     */
    String getFormalParam();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#getFormalParam <em>Formal Param</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Formal Param</em>' attribute.
     * @see #getFormalParam()
     * @generated
     */
    void setFormalParam(String value);

    /**
     * Returns the value of the '<em><b>Mode</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ModeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mode</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ModeType
     * @see #isSetMode()
     * @see #unsetMode()
     * @see #setMode(ModeType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParameter_Mode()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='Mode'"
     * @generated
     */
    ModeType getMode();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#getMode <em>Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mode</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ModeType
     * @see #isSetMode()
     * @see #unsetMode()
     * @see #getMode()
     * @generated
     */
    void setMode(ModeType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#getMode <em>Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMode()
     * @see #getMode()
     * @see #setMode(ModeType)
     * @generated
     */
    void unsetMode();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#getMode <em>Mode</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Mode</em>' attribute is set.
     * @see #unsetMode()
     * @see #getMode()
     * @see #setMode(ModeType)
     * @generated
     */
    boolean isSetMode();

    /**
     * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mandatory</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mandatory</em>' attribute.
     * @see #isSetMandatory()
     * @see #unsetMandatory()
     * @see #setMandatory(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParameter_Mandatory()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='Mandatory'"
     * @generated
     */
    boolean isMandatory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#isMandatory <em>Mandatory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mandatory</em>' attribute.
     * @see #isSetMandatory()
     * @see #unsetMandatory()
     * @see #isMandatory()
     * @generated
     */
    void setMandatory(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#isMandatory <em>Mandatory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMandatory()
     * @see #isMandatory()
     * @see #setMandatory(boolean)
     * @generated
     */
    void unsetMandatory();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParameter#isMandatory <em>Mandatory</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Mandatory</em>' attribute is set.
     * @see #unsetMandatory()
     * @see #isMandatory()
     * @see #setMandatory(boolean)
     * @generated
     */
    boolean isSetMandatory();

} // AssociatedParameter