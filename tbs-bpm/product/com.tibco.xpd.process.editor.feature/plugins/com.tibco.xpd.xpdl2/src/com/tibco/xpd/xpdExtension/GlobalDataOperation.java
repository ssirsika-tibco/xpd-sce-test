/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Data Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Element for the Global Data operations configuration to be used in the Service Task of type Global Data Operation.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.GlobalDataOperation#getCaseAccessOperations <em>Case Access Operations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.GlobalDataOperation#getCaseReferenceOperations <em>Case Reference Operations</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getGlobalDataOperation()
 * @model extendedMetaData="name='GlobalDataOperation' kind='elementOnly'"
 * @generated
 */
public interface GlobalDataOperation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Case Access Operations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operations performed via case access classes that do not require case reference field.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Access Operations</em>' containment reference.
     * @see #setCaseAccessOperations(CaseAccessOperationsType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getGlobalDataOperation_CaseAccessOperations()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='CaseAccessOperations' namespace='##targetNamespace'"
     * @generated
     */
    CaseAccessOperationsType getCaseAccessOperations();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.GlobalDataOperation#getCaseAccessOperations <em>Case Access Operations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Access Operations</em>' containment reference.
     * @see #getCaseAccessOperations()
     * @generated
     */
    void setCaseAccessOperations(CaseAccessOperationsType value);

    /**
     * Returns the value of the '<em><b>Case Reference Operations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operatoins performed on  cae objects identiifed by already populated case reference(s) field
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Reference Operations</em>' containment reference.
     * @see #setCaseReferenceOperations(CaseReferenceOperationsType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getGlobalDataOperation_CaseReferenceOperations()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='CaseReferenceOperations' namespace='##targetNamespace'"
     * @generated
     */
    CaseReferenceOperationsType getCaseReferenceOperations();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.GlobalDataOperation#getCaseReferenceOperations <em>Case Reference Operations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Reference Operations</em>' containment reference.
     * @see #getCaseReferenceOperations()
     * @generated
     */
    void setCaseReferenceOperations(CaseReferenceOperationsType value);

} // GlobalDataOperation
