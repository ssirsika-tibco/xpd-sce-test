/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Form Implementation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Form Implementation information - 
 * This is added to TaskUser element of user task activities.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.FormImplementation#getFormType <em>Form Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.FormImplementation#getFormURI <em>Form URI</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFormImplementation()
 * @model annotation="ExtendedMetaData kind='element' name='FormImplementation' namespace='##targetNamespace'"
 * @generated
 */
public interface FormImplementation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Form Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.FormImplementationType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Form Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Form Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.FormImplementationType
     * @see #isSetFormType()
     * @see #unsetFormType()
     * @see #setFormType(FormImplementationType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFormImplementation_FormType()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='FormType'"
     * @generated
     */
    FormImplementationType getFormType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.FormImplementation#getFormType <em>Form Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Form Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.FormImplementationType
     * @see #isSetFormType()
     * @see #unsetFormType()
     * @see #getFormType()
     * @generated
     */
    void setFormType(FormImplementationType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.FormImplementation#getFormType <em>Form Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetFormType()
     * @see #getFormType()
     * @see #setFormType(FormImplementationType)
     * @generated
     */
    void unsetFormType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.FormImplementation#getFormType <em>Form Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Form Type</em>' attribute is set.
     * @see #unsetFormType()
     * @see #getFormType()
     * @see #setFormType(FormImplementationType)
     * @generated
     */
    boolean isSetFormType();

    /**
     * Returns the value of the '<em><b>Form URI</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Form URI</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Form URI</em>' attribute.
     * @see #setFormURI(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFormImplementation_FormURI()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='FormURI'"
     * @generated
     */
    String getFormURI();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.FormImplementation#getFormURI <em>Form URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Form URI</em>' attribute.
     * @see #getFormURI()
     * @generated
     */
    void setFormURI(String value);

} // FormImplementation
