/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Expression;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Find By Query Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.FindByQueryOperation#getCaseDocumentQueryExpression <em>Case Document Query Expression</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFindByQueryOperation()
 * @model extendedMetaData="name='FindByQueryOperation' kind='elementOnly'"
 * @generated
 */
public interface FindByQueryOperation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Case Document Query Expression</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Find query containing single where clause expression.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Document Query Expression</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFindByQueryOperation_CaseDocumentQueryExpression()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='CaseDocumentQueryExpression' namespace='##targetNamespace'"
     * @generated
     */
    EList<CaseDocumentQueryExpression> getCaseDocumentQueryExpression();

} // FindByQueryOperation
