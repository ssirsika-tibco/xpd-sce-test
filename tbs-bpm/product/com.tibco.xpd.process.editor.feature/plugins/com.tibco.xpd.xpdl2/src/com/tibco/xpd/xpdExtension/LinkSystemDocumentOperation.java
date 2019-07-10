/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link System Document Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getDocumentId <em>Document Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getReturnCaseDocRefField <em>Return Case Doc Ref Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getCaseRefField <em>Case Ref Field</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLinkSystemDocumentOperation()
 * @model
 * @generated
 */
public interface LinkSystemDocumentOperation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Document Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * CMS System Id of the Document to be Linked.Type String.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Document Id</em>' attribute.
     * @see #setDocumentId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLinkSystemDocumentOperation_DocumentId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='DocumentId'"
     * @generated
     */
    String getDocumentId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getDocumentId <em>Document Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Document Id</em>' attribute.
     * @see #getDocumentId()
     * @generated
     */
    void setDocumentId(String value);

    /**
     * Returns the value of the '<em><b>Return Case Doc Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Document Reference field to hold the return of the linked Document Reference from the link operation.Type String.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Return Case Doc Ref Field</em>' attribute.
     * @see #setReturnCaseDocRefField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLinkSystemDocumentOperation_ReturnCaseDocRefField()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ReturnCaseDocRefField'"
     * @generated
     */
    String getReturnCaseDocRefField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getReturnCaseDocRefField <em>Return Case Doc Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Return Case Doc Ref Field</em>' attribute.
     * @see #getReturnCaseDocRefField()
     * @generated
     */
    void setReturnCaseDocRefField(String value);

    /**
     * Returns the value of the '<em><b>Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Case Reference field identifying the Case Object to Link the Document to.Type String.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Ref Field</em>' attribute.
     * @see #setCaseRefField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLinkSystemDocumentOperation_CaseRefField()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='CaseRefField'"
     * @generated
     */
    String getCaseRefField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation#getCaseRefField <em>Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Ref Field</em>' attribute.
     * @see #getCaseRefField()
     * @generated
     */
    void setCaseRefField(String value);

} // LinkSystemDocumentOperation
