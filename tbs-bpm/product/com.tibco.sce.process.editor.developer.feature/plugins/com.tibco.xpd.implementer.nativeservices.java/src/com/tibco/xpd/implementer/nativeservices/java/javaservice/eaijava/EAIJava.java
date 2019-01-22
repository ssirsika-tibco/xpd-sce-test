/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAI Java</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getPojo <em>Pojo</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getFactory <em>Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getEAIJava()
 * @model extendedMetaData="name='EAIJava' kind='elementOnly'"
 * @generated
 */
public interface EAIJava extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Pojo</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Pojo</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Pojo</em>' containment reference.
     * @see #setPojo(ClassType)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getEAIJava_Pojo()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Pojo' namespace='##targetNamespace'"
     * @generated
     */
    ClassType getPojo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getPojo <em>Pojo</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Pojo</em>' containment reference.
     * @see #getPojo()
     * @generated
     */
    void setPojo(ClassType value);

    /**
     * Returns the value of the '<em><b>Factory</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Factory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Factory</em>' containment reference.
     * @see #setFactory(ClassType)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getEAIJava_Factory()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Factory' namespace='##targetNamespace'"
     * @generated
     */
	ClassType getFactory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getFactory <em>Factory</em>}' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Factory</em>' containment reference.
     * @see #getFactory()
     * @generated
     */
	void setFactory(ClassType value);

    /**
     * Returns the value of the '<em><b>Project</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Project the pojo and factory belong to.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Project</em>' attribute.
     * @see #setProject(String)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getEAIJava_Project()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='project'"
     * @generated
     */
    String getProject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getProject <em>Project</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Project</em>' attribute.
     * @see #getProject()
     * @generated
     */
    void setProject(String value);

} // EAIJava