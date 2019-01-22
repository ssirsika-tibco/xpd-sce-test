/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Type to represent the document related operations. It is used as the details of Service Task of type ‘Document Operations’.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentOperation#getCaseDocRefOperation <em>Case Doc Ref Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentOperation#getCaseDocFindOperations <em>Case Doc Find Operations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DocumentOperation#getLinkSystemDocumentOperation <em>Link System Document Operation</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentOperation()
 * @model extendedMetaData="name='DocumentOperation' kind='elementOnly'"
 * @generated
 */
public interface DocumentOperation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Case Doc Ref Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operations performed on a Case Document identified by the Document Reference.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Doc Ref Operation</em>' containment reference.
     * @see #setCaseDocRefOperation(CaseDocRefOperations)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentOperation_CaseDocRefOperation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='CaseDocRefOperations' namespace='##targetNamespace'"
     * @generated
     */
    CaseDocRefOperations getCaseDocRefOperation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentOperation#getCaseDocRefOperation <em>Case Doc Ref Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Doc Ref Operation</em>' containment reference.
     * @see #getCaseDocRefOperation()
     * @generated
     */
    void setCaseDocRefOperation(CaseDocRefOperations value);

    /**
     * Returns the value of the '<em><b>Case Doc Find Operations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operations performed to find Case Document  by name or query.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Doc Find Operations</em>' containment reference.
     * @see #setCaseDocFindOperations(CaseDocFindOperations)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentOperation_CaseDocFindOperations()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='CaseDocFindOperations' namespace='##targetNamespace'"
     * @generated
     */
    CaseDocFindOperations getCaseDocFindOperations();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentOperation#getCaseDocFindOperations <em>Case Doc Find Operations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Doc Find Operations</em>' containment reference.
     * @see #getCaseDocFindOperations()
     * @generated
     */
    void setCaseDocFindOperations(CaseDocFindOperations value);

    /**
     * Returns the value of the '<em><b>Link System Document Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operations performed to link a Document identified by the Document Id to a Case Object identified by the Case Reference.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Link System Document Operation</em>' containment reference.
     * @see #setLinkSystemDocumentOperation(LinkSystemDocumentOperation)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDocumentOperation_LinkSystemDocumentOperation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='LinkSystemDocumentOperation' namespace='##targetNamespace'"
     * @generated
     */
    LinkSystemDocumentOperation getLinkSystemDocumentOperation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DocumentOperation#getLinkSystemDocumentOperation <em>Link System Document Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Link System Document Operation</em>' containment reference.
     * @see #getLinkSystemDocumentOperation()
     * @generated
     */
    void setLinkSystemDocumentOperation(LinkSystemDocumentOperation value);

} // DocumentOperation
