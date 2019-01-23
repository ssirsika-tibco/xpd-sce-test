/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger Result Message</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getMessage <em>Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getWebServiceOperation <em>Web Service Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getCatchThrow <em>Catch Throw</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultMessage()
 * @model extendedMetaData="name='TriggerResultMessage_._type' kind='elementOnly' features-order='message webServiceOperation otherElements'"
 * @generated
 */
public interface TriggerResultMessage extends OtherAttributesContainer,
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultMessage_Message()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Message' namespace='##targetNamespace'"
     * @generated
     */
    Message getMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getMessage <em>Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message</em>' containment reference.
     * @see #getMessage()
     * @generated
     */
    void setMessage(Message value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultMessage_WebServiceOperation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WebServiceOperation' namespace='##targetNamespace'"
     * @generated
     */
    WebServiceOperation getWebServiceOperation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getWebServiceOperation <em>Web Service Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Web Service Operation</em>' containment reference.
     * @see #getWebServiceOperation()
     * @generated
     */
    void setWebServiceOperation(WebServiceOperation value);

    /**
     * Returns the value of the '<em><b>Catch Throw</b></em>' attribute.
     * The default value is <code>"CATCH"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.CatchThrow}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Catch Throw</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Catch Throw</em>' attribute.
     * @see com.tibco.xpd.xpdl2.CatchThrow
     * @see #isSetCatchThrow()
     * @see #unsetCatchThrow()
     * @see #setCatchThrow(CatchThrow)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultMessage_CatchThrow()
     * @model default="CATCH" unsettable="true"
     *        extendedMetaData="kind='attribute' name='CatchThrow'"
     * @generated
     */
    CatchThrow getCatchThrow();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getCatchThrow <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Catch Throw</em>' attribute.
     * @see com.tibco.xpd.xpdl2.CatchThrow
     * @see #isSetCatchThrow()
     * @see #unsetCatchThrow()
     * @see #getCatchThrow()
     * @generated
     */
    void setCatchThrow(CatchThrow value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getCatchThrow <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCatchThrow()
     * @see #getCatchThrow()
     * @see #setCatchThrow(CatchThrow)
     * @generated
     */
    void unsetCatchThrow();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultMessage#getCatchThrow <em>Catch Throw</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Catch Throw</em>' attribute is set.
     * @see #unsetCatchThrow()
     * @see #getCatchThrow()
     * @see #setCatchThrow(CatchThrow)
     * @generated
     */
    boolean isSetCatchThrow();

} // TriggerResultMessage
