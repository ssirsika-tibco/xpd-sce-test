/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.ExternalReference;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Class Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseClassType#getCaseClassExternalReference <em>Case Class External Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseClassType()
 * @model extendedMetaData="name='CaseClassType' kind='elementOnly'"
 * @generated
 */
public interface CaseClassType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Case Class External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Standard xpdl2 ExternalReference to CaseClass concerned.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Class External Reference</em>' containment reference.
     * @see #setCaseClassExternalReference(ExternalReference)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseClassType_CaseClassExternalReference()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='CaseClassExternalReference'"
     * @generated
     */
    ExternalReference getCaseClassExternalReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseClassType#getCaseClassExternalReference <em>Case Class External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Class External Reference</em>' containment reference.
     * @see #getCaseClassExternalReference()
     * @generated
     */
    void setCaseClassExternalReference(ExternalReference value);

} // CaseClassType
