/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Formal Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.FormalParameter#getMode <em>Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.FormalParameter#isRequired <em>Required</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFormalParameter()
 * @model extendedMetaData="name='FormalParameter_._type' kind='elementOnly' features-order='dataType description length otherAttributes otherElements'"
 * @generated
 */
public interface FormalParameter extends ProcessRelevantData {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Mode</b></em>' attribute.
     * The default value is <code>"IN"</code>.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFormalParameter_Mode()
     * @model default="IN" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Mode'"
     * @generated
     */
    ModeType getMode();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.FormalParameter#getMode <em>Mode</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.FormalParameter#getMode <em>Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMode()
     * @see #getMode()
     * @see #setMode(ModeType)
     * @generated
     */
    void unsetMode();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.FormalParameter#getMode <em>Mode</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Required</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Required</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Required</em>' attribute.
     * @see #isSetRequired()
     * @see #unsetRequired()
     * @see #setRequired(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFormalParameter_Required()
     * @model default="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Required'"
     * @generated
     */
    boolean isRequired();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.FormalParameter#isRequired <em>Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Required</em>' attribute.
     * @see #isSetRequired()
     * @see #unsetRequired()
     * @see #isRequired()
     * @generated
     */
    void setRequired(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.FormalParameter#isRequired <em>Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetRequired()
     * @see #isRequired()
     * @see #setRequired(boolean)
     * @generated
     */
    void unsetRequired();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.FormalParameter#isRequired <em>Required</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Required</em>' attribute is set.
     * @see #unsetRequired()
     * @see #isRequired()
     * @see #setRequired(boolean)
     * @generated
     */
    boolean isSetRequired();

} // FormalParameter
