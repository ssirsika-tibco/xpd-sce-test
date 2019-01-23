/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Service</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskService#getMessageIn <em>Message In</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskService#getMessageOut <em>Message Out</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskService#getWebServiceOperation <em>Web Service Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskService#getWebServiceFaultCatch <em>Web Service Fault Catch</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskService#getImplementation <em>Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskService()
 * @model extendedMetaData="name='TaskService_._type' kind='elementOnly' features-order='messageIn messageOut webServiceOperation webServiceFaultCatch otherElements'"
 * @generated
 */
public interface TaskService extends OtherAttributesContainer,
        OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Message In</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message In</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Message In</em>' containment reference.
     * @see #setMessageIn(Message)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskService_MessageIn()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='MessageIn' namespace='##targetNamespace'"
     * @generated
     */
    Message getMessageIn();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TaskService#getMessageIn <em>Message In</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message In</em>' containment reference.
     * @see #getMessageIn()
     * @generated
     */
    void setMessageIn(Message value);

    /**
     * Returns the value of the '<em><b>Message Out</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message Out</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Message Out</em>' containment reference.
     * @see #setMessageOut(Message)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskService_MessageOut()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='MessageOut' namespace='##targetNamespace'"
     * @generated
     */
    Message getMessageOut();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TaskService#getMessageOut <em>Message Out</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Out</em>' containment reference.
     * @see #getMessageOut()
     * @generated
     */
    void setMessageOut(Message value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskService_WebServiceOperation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WebServiceOperation' namespace='##targetNamespace'"
     * @generated
     */
    WebServiceOperation getWebServiceOperation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TaskService#getWebServiceOperation <em>Web Service Operation</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskService_WebServiceFaultCatch()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WebServiceFaultCatch' namespace='##targetNamespace'"
     * @generated
     */
    EList<WebServiceFaultCatch> getWebServiceFaultCatch();

    /**
     * Returns the value of the '<em><b>Implementation</b></em>' attribute.
     * The default value is <code>"WebService"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ImplementationType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Required if the Task is Service
     * <!-- end-model-doc -->
     * @return the value of the '<em>Implementation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ImplementationType
     * @see #isSetImplementation()
     * @see #unsetImplementation()
     * @see #setImplementation(ImplementationType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskService_Implementation()
     * @model default="WebService" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Implementation'"
     * @generated
     */
    ImplementationType getImplementation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TaskService#getImplementation <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Implementation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ImplementationType
     * @see #isSetImplementation()
     * @see #unsetImplementation()
     * @see #getImplementation()
     * @generated
     */
    void setImplementation(ImplementationType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.TaskService#getImplementation <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetImplementation()
     * @see #getImplementation()
     * @see #setImplementation(ImplementationType)
     * @generated
     */
    void unsetImplementation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.TaskService#getImplementation <em>Implementation</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Implementation</em>' attribute is set.
     * @see #unsetImplementation()
     * @see #getImplementation()
     * @see #setImplementation(ImplementationType)
     * @generated
     */
    boolean isSetImplementation();

} // TaskService
