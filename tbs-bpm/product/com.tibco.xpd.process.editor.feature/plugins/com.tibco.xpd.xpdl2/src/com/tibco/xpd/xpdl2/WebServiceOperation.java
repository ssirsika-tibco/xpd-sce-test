/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Service Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceOperation#getPartner <em>Partner</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceOperation#getService <em>Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.WebServiceOperation#getOperationName <em>Operation Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceOperation()
 * @model extendedMetaData="name='WebServiceOperation_._type' kind='elementOnly'"
 * @generated
 */
public interface WebServiceOperation extends OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Partner</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Partner</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Partner</em>' containment reference.
     * @see #setPartner(Partner)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceOperation_Partner()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Partner' namespace='##targetNamespace'"
     * @generated
     */
    Partner getPartner();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceOperation#getPartner <em>Partner</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Partner</em>' containment reference.
     * @see #getPartner()
     * @generated
     */
    void setPartner(Partner value);

    /**
     * Returns the value of the '<em><b>Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Service</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Service</em>' containment reference.
     * @see #setService(Service)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceOperation_Service()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Service' namespace='##targetNamespace'"
     * @generated
     */
    Service getService();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceOperation#getService <em>Service</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Service</em>' containment reference.
     * @see #getService()
     * @generated
     */
    void setService(Service value);

    /**
     * Returns the value of the '<em><b>Operation Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Operation Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Operation Name</em>' attribute.
     * @see #setOperationName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getWebServiceOperation_OperationName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='OperationName'"
     * @generated
     */
    String getOperationName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.WebServiceOperation#getOperationName <em>Operation Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Operation Name</em>' attribute.
     * @see #getOperationName()
     * @generated
     */
    void setOperationName(String value);

} // WebServiceOperation
