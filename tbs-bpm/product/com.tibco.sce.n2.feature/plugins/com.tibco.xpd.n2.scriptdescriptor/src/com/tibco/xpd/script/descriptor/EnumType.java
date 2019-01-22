/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of an EnumType.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.EnumType#getScriptingName <em>Scripting Name</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.EnumType#getCanonicalClassName <em>Canonical Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getEnumType()
 * @model extendedMetaData="name='EnumType' kind='elementOnly'"
 * @generated
 */
public interface EnumType extends EObject {
    /**
     * Returns the value of the '<em><b>Scripting Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name that will be used to refer to the EMF factory class from within the script.
     * 
     * For example: com_example_customer_CustomerFactory. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Scripting Name</em>' attribute.
     * @see #setScriptingName(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getEnumType_ScriptingName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='scriptingName'"
     * @generated
     */
    String getScriptingName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.EnumType#getScriptingName <em>Scripting Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Scripting Name</em>' attribute.
     * @see #getScriptingName()
     * @generated
     */
    void setScriptingName(String value);

    /**
     * Returns the value of the '<em><b>Canonical Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Fully qualified class name of the class derived from a BOM enumeration. 
     * 
     * For example, an enum named Title in a BOM whose name is com.example.customer would be: com.example.customer.TitleType. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Canonical Class Name</em>' attribute.
     * @see #setCanonicalClassName(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getEnumType_CanonicalClassName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='canonicalClassName'"
     * @generated
     */
    String getCanonicalClassName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.EnumType#getCanonicalClassName <em>Canonical Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Canonical Class Name</em>' attribute.
     * @see #getCanonicalClassName()
     * @generated
     */
    void setCanonicalClassName(String value);

} // EnumType
