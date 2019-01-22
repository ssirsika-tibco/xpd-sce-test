/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link Case Doc Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.LinkCaseDocOperation#getTargetCaseRefField <em>Target Case Ref Field</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLinkCaseDocOperation()
 * @model extendedMetaData="name='LinkCaseDocOperation' kind='empty'"
 * @generated
 */
public interface LinkCaseDocOperation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Target Case Ref Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Case Reference field identifying the Case Object to which the document has to be linked.Type String.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Target Case Ref Field</em>' attribute.
     * @see #setTargetCaseRefField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getLinkCaseDocOperation_TargetCaseRefField()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='TargetCaseRefField'"
     * @generated
     */
    String getTargetCaseRefField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.LinkCaseDocOperation#getTargetCaseRefField <em>Target Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target Case Ref Field</em>' attribute.
     * @see #getTargetCaseRefField()
     * @generated
     */
    void setTargetCaseRefField(String value);

} // LinkCaseDocOperation
