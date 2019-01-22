/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Doc Find Operations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getFindByFileNameOperation <em>Find By File Name Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getFindByQueryOperation <em>Find By Query Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getReturnCaseDocRefsField <em>Return Case Doc Refs Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getCaseRefField <em>Case Ref Field</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocFindOperations()
 * @model extendedMetaData="name='CaseDocFindOperations' kind='elementOnly'"
 * @generated
 */
public interface CaseDocFindOperations extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Find By File Name Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operation to find the document with given name, associated with the Case Object identified by the Case Reference field. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Find By File Name Operation</em>' containment reference.
     * @see #setFindByFileNameOperation(FindByFileNameOperation)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocFindOperations_FindByFileNameOperation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='FindByFileNameOperation' namespace='##targetNamespace'"
     * @generated
     */
    FindByFileNameOperation getFindByFileNameOperation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getFindByFileNameOperation <em>Find By File Name Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Find By File Name Operation</em>' containment reference.
     * @see #getFindByFileNameOperation()
     * @generated
     */
    void setFindByFileNameOperation(FindByFileNameOperation value);

    /**
     * Returns the value of the '<em><b>Find By Query Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operation based on given query to find the documents , associated with the Case Object identified by the Case Reference field.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Find By Query Operation</em>' containment reference.
     * @see #setFindByQueryOperation(FindByQueryOperation)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocFindOperations_FindByQueryOperation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='FindByQueryOperation' namespace='##targetNamespace'"
     * @generated
     */
    FindByQueryOperation getFindByQueryOperation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getFindByQueryOperation <em>Find By Query Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Find By Query Operation</em>' containment reference.
     * @see #getFindByQueryOperation()
     * @generated
     */
    void setFindByQueryOperation(FindByQueryOperation value);

    /**
     * Returns the value of the '<em><b>Return Case Doc Refs Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Document Reference(s) field to hold the return from the find operation.Type String.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Return Case Doc Refs Field</em>' attribute.
     * @see #setReturnCaseDocRefsField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocFindOperations_ReturnCaseDocRefsField()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ReturnCaseDocRefsField'"
     * @generated
     */
    String getReturnCaseDocRefsField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getReturnCaseDocRefsField <em>Return Case Doc Refs Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Return Case Doc Refs Field</em>' attribute.
     * @see #getReturnCaseDocRefsField()
     * @generated
     */
    void setReturnCaseDocRefsField(String value);

    /**
     * Returns the value of the '<em><b>Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Case Reference field identifying the Case Object to run the Find operation on.Type String.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Ref Field</em>' attribute.
     * @see #setCaseRefField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocFindOperations_CaseRefField()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='CaseRefField'"
     * @generated
     */
    String getCaseRefField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocFindOperations#getCaseRefField <em>Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Ref Field</em>' attribute.
     * @see #getCaseRefField()
     * @generated
     */
    void setCaseRefField(String value);

} // CaseDocFindOperations
