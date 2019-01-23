/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Service Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceApplication#getWebServiceOperation <em>Web Service Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceApplication#getWebServiceFaultCatch <em>Web Service Fault Catch</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceApplication#getInputMsgName <em>Input Msg Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceApplication#getOutputMsgName <em>Output Msg Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceApplication()
 * @model extendedMetaData="name='WebService_._type' kind='elementOnly'"
 * @generated
 */
public interface WebServiceApplication extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Web Service Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Web Service Operation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Web Service Operation</em>' containment reference.
     * @see #setWebServiceOperation(WebServiceOperation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceApplication_WebServiceOperation()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WebServiceOperation' namespace='##targetNamespace'"
     * @generated
     */
    WebServiceOperation getWebServiceOperation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceApplication#getWebServiceOperation <em>Web Service Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Web Service Operation</em>' containment reference.
     * @see #getWebServiceOperation()
     * @generated
     */
    void setWebServiceOperation(WebServiceOperation value);

    /**
     * Returns the value of the '<em><b>Web Service Fault Catch</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.WebServiceFaultCatch}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Web Service Fault Catch</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Web Service Fault Catch</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceApplication_WebServiceFaultCatch()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WebServiceFaultCatch' namespace='##targetNamespace'"
     * @generated
     */
    EList<WebServiceFaultCatch> getWebServiceFaultCatch();

    /**
     * Returns the value of the '<em><b>Input Msg Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The name of inputMessage as defined in the WSDL which will help in uniquely identifying the operation to be invoked
     * <!-- end-model-doc -->
     * @return the value of the '<em>Input Msg Name</em>' attribute.
     * @see #setInputMsgName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceApplication_InputMsgName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='InputMsgName'"
     * @generated
     */
    String getInputMsgName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceApplication#getInputMsgName <em>Input Msg Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Input Msg Name</em>' attribute.
     * @see #getInputMsgName()
     * @generated
     */
    void setInputMsgName(String value);

    /**
     * Returns the value of the '<em><b>Output Msg Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The name of inputMessage as defined in the WSDL which will help in uniquely identifying the operation to be invoked
     * <!-- end-model-doc -->
     * @return the value of the '<em>Output Msg Name</em>' attribute.
     * @see #setOutputMsgName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceApplication_OutputMsgName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='OutputMsgName'"
     * @generated
     */
    String getOutputMsgName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceApplication#getOutputMsgName <em>Output Msg Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Output Msg Name</em>' attribute.
     * @see #getOutputMsgName()
     * @generated
     */
    void setOutputMsgName(String value);

} // WebServiceApplication
