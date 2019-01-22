/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Service</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The element used to contain the details like Case Class Type and states for a Case Action.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseService#getCaseClassType <em>Case Class Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseService#getVisibleForCaseStates <em>Visible For Case States</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseService()
 * @model extendedMetaData="name='CaseService' kind='elementOnly'"
 * @generated
 */
public interface CaseService extends OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Case Class Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Standard xpdl2 ExternalReference to CaseClass.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Case Class Type</em>' containment reference.
     * @see #setCaseClassType(ExternalReference)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseService_CaseClassType()
     * @model containment="true" ordered="false"
     *        extendedMetaData="kind='element' name='CaseClassType' namespace='##targetNamespace'"
     * @generated
     */
    ExternalReference getCaseClassType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseService#getCaseClassType <em>Case Class Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Case Class Type</em>' containment reference.
     * @see #getCaseClassType()
     * @generated
     */
    void setCaseClassType(ExternalReference value);

    /**
     * Returns the value of the '<em><b>Visible For Case States</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Visible For Case States</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * References to case class case states attribute enumeration values applicable for the case service.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Visible For Case States</em>' containment reference.
     * @see #setVisibleForCaseStates(VisibleForCaseStates)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseService_VisibleForCaseStates()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='VisibleForCaseStates'"
     * @generated
     */
    VisibleForCaseStates getVisibleForCaseStates();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseService#getVisibleForCaseStates <em>Visible For Case States</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Visible For Case States</em>' containment reference.
     * @see #getVisibleForCaseStates()
     * @generated
     */
    void setVisibleForCaseStates(VisibleForCaseStates value);

} // CaseService
