/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Script Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.ScriptDescriptorType#getScript <em>Script</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.ScriptDescriptorType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptDescriptorType()
 * @model extendedMetaData="name='ScriptDescriptorType' kind='elementOnly'"
 * @generated
 */
public interface ScriptDescriptorType extends EObject {
    /**
     * Returns the value of the '<em><b>Script</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.script.descriptor.ScriptType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Script</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Script</em>' containment reference list.
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptDescriptorType_Script()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='script'"
     * @generated
     */
    EList<ScriptType> getScript();

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptDescriptorType_Version()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.ScriptDescriptorType#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

} // ScriptDescriptorType
