/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Service Fault Catch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getMessage <em>Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getBlockActivity <em>Block Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getTransitionRef <em>Transition Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getFaultName <em>Fault Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceFaultCatch()
 * @model extendedMetaData="name='WebServiceFaultCatch_._type' kind='elementOnly'"
 * @generated
 */
public interface WebServiceFaultCatch extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Message</em>' containment reference.
     * @see #setMessage(Message)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceFaultCatch_Message()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Message' namespace='##targetNamespace'"
     * @generated
     */
    Message getMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getMessage <em>Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message</em>' containment reference.
     * @see #getMessage()
     * @generated
     */
    void setMessage(Message value);

    /**
     * Returns the value of the '<em><b>Block Activity</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Block Activity</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Block Activity</em>' containment reference.
     * @see #setBlockActivity(BlockActivity)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceFaultCatch_BlockActivity()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='BlockActivity' namespace='##targetNamespace'"
     * @generated
     */
    BlockActivity getBlockActivity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getBlockActivity <em>Block Activity</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Block Activity</em>' containment reference.
     * @see #getBlockActivity()
     * @generated
     */
    void setBlockActivity(BlockActivity value);

    /**
     * Returns the value of the '<em><b>Transition Ref</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transition Ref</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transition Ref</em>' containment reference.
     * @see #setTransitionRef(TransitionRef)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceFaultCatch_TransitionRef()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TransitionRef' namespace='##targetNamespace'"
     * @generated
     */
    TransitionRef getTransitionRef();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getTransitionRef <em>Transition Ref</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Transition Ref</em>' containment reference.
     * @see #getTransitionRef()
     * @generated
     */
    void setTransitionRef(TransitionRef value);

    /**
     * Returns the value of the '<em><b>Fault Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Fault Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Fault Name</em>' attribute.
     * @see #setFaultName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceFaultCatch_FaultName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
     *        extendedMetaData="kind='attribute' name='FaultName'"
     * @generated
     */
    String getFaultName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceFaultCatch#getFaultName <em>Fault Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Fault Name</em>' attribute.
     * @see #getFaultName()
     * @generated
     */
    void setFaultName(String value);

} // WebServiceFaultCatch
