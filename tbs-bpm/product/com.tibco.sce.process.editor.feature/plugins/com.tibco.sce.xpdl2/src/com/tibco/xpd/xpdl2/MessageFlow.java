/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.MessageFlow#getMessage <em>Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.MessageFlow#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.MessageFlow#getSource <em>Source</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.MessageFlow#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.MessageFlow#getPackage <em>Package</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessageFlow()
 * @model extendedMetaData="name='MessageFlow_._type' kind='elementOnly' features-order='message object connectorGraphicsInfos'"
 * @generated
 */
public interface MessageFlow extends NamedElement, GraphicalConnector,
        OtherElementsContainer {
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessageFlow_Message()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Message' namespace='##targetNamespace'"
     * @generated
     */
    Message getMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.MessageFlow#getMessage <em>Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message</em>' containment reference.
     * @see #getMessage()
     * @generated
     */
    void setMessage(Message value);

    /**
     * Returns the value of the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object</em>' containment reference.
     * @see #setObject(com.tibco.xpd.xpdl2.Object)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessageFlow_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.MessageFlow#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Source</em>' attribute.
     * @see #setSource(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessageFlow_Source()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='Source'"
     * @generated
     */
    String getSource();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.MessageFlow#getSource <em>Source</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source</em>' attribute.
     * @see #getSource()
     * @generated
     */
    void setSource(String value);

    /**
     * Returns the value of the '<em><b>Target</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Target</em>' attribute.
     * @see #setTarget(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessageFlow_Target()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='Target'"
     * @generated
     */
    String getTarget();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.MessageFlow#getTarget <em>Target</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target</em>' attribute.
     * @see #getTarget()
     * @generated
     */
    void setTarget(String value);

    /**
     * Returns the value of the '<em><b>Package</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Package#getMessageFlows <em>Message Flows</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package</em>' container reference.
     * @see #setPackage(com.tibco.xpd.xpdl2.Package)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getMessageFlow_Package()
     * @see com.tibco.xpd.xpdl2.Package#getMessageFlows
     * @model opposite="messageFlows" transient="false"
     * @generated
     */
    com.tibco.xpd.xpdl2.Package getPackage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.MessageFlow#getPackage <em>Package</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package</em>' container reference.
     * @see #getPackage()
     * @generated
     */
    void setPackage(com.tibco.xpd.xpdl2.Package value);

} // MessageFlow
