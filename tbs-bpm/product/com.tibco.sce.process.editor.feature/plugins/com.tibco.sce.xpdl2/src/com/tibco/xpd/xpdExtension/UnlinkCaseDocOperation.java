/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unlink Case Doc Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation#getSourceCaseRefField <em>Source Case Ref Field</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUnlinkCaseDocOperation()
 * @model extendedMetaData="name='UnlinkCaseDocOperation' kind='empty'"
 * @generated
 */
public interface UnlinkCaseDocOperation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Source Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Case Reference field identifying the Case Object from which the document has to be unlinked.Type String.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Source Case Ref Field</em>' attribute.
     * @see #setSourceCaseRefField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getUnlinkCaseDocOperation_SourceCaseRefField()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='SourceCaseRefField'"
     * @generated
     */
    String getSourceCaseRefField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation#getSourceCaseRefField <em>Source Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source Case Ref Field</em>' attribute.
     * @see #getSourceCaseRefField()
     * @generated
     */
    void setSourceCaseRefField(String value);

} // UnlinkCaseDocOperation
