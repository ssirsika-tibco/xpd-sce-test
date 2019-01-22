/**
 */
package com.tibco.xpd.globalSignalDefinition;

import com.tibco.xpd.xpdl2.DataField;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Payload Data Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.globalSignalDefinition.PayloadDataField#isMandatory <em>Mandatory</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getPayloadDataField()
 * @model extendedMetaData="name='PayloadDataField' kind='elementOnly'"
 * @generated
 */
public interface PayloadDataField extends DataField {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

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
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#getPayloadDataField_Mandatory()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='Mandatory'"
     * @generated
     */
    boolean isMandatory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.globalSignalDefinition.PayloadDataField#isMandatory <em>Mandatory</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.globalSignalDefinition.PayloadDataField#isMandatory <em>Mandatory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMandatory()
     * @see #isMandatory()
     * @see #setMandatory(boolean)
     * @generated
     */
    void unsetMandatory();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.globalSignalDefinition.PayloadDataField#isMandatory <em>Mandatory</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Mandatory</em>' attribute is set.
     * @see #unsetMandatory()
     * @see #isMandatory()
     * @see #setMandatory(boolean)
     * @generated
     */
    boolean isSetMandatory();

} // PayloadDataField
