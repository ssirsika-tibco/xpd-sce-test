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
 * A representation of the model object '<em><b>Factory Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a FactoryType.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.FactoryType#getScriptingName <em>Scripting Name</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.FactoryType#getCanonicalClassName <em>Canonical Class Name</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.FactoryType#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getFactoryType()
 * @model extendedMetaData="name='FactoryType' kind='elementOnly'"
 * @generated
 */
public interface FactoryType extends EObject {
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
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getFactoryType_ScriptingName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='scriptingName'"
     * @generated
     */
    String getScriptingName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.FactoryType#getScriptingName <em>Scripting Name</em>}' attribute.
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
     * Fully qualified class name.
     * 
     * For example: com.example.customer.CustomerFactory. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Canonical Class Name</em>' attribute.
     * @see #setCanonicalClassName(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getFactoryType_CanonicalClassName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='canonicalClassName'"
     * @generated
     */
    String getCanonicalClassName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.FactoryType#getCanonicalClassName <em>Canonical Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Canonical Class Name</em>' attribute.
     * @see #getCanonicalClassName()
     * @generated
     */
    void setCanonicalClassName(String value);

    /**
     * Returns the value of the '<em><b>Namespace</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Namespace of the XSD representation of the BOM. 
     * 
     * For example, for a BOM whose model package is com.example.customer the namespace will be http://example.com/customer/. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Namespace</em>' attribute.
     * @see #setNamespace(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getFactoryType_Namespace()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='namespace'"
     * @generated
     */
    String getNamespace();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.FactoryType#getNamespace <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Namespace</em>' attribute.
     * @see #getNamespace()
     * @generated
     */
    void setNamespace(String value);

} // FactoryType
