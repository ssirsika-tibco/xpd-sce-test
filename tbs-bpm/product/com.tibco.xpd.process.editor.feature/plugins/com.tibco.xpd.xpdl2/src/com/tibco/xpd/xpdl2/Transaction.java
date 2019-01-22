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
 * A representation of the model object '<em><b>Transaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Transaction#getTransactionId <em>Transaction Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Transaction#getTransactionMethod <em>Transaction Method</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Transaction#getTransactionProtocol <em>Transaction Protocol</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransaction()
 * @model extendedMetaData="name='Transaction_._type' kind='elementOnly'"
 * @generated
 */
public interface Transaction extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Transaction Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transaction Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transaction Id</em>' attribute.
     * @see #setTransactionId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransaction_TransactionId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='TransactionId'"
     * @generated
     */
    String getTransactionId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transaction#getTransactionId <em>Transaction Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Transaction Id</em>' attribute.
     * @see #getTransactionId()
     * @generated
     */
    void setTransactionId(String value);

    /**
     * Returns the value of the '<em><b>Transaction Method</b></em>' attribute.
     * The default value is <code>"Compensate"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.TransactionMethodType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transaction Method</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transaction Method</em>' attribute.
     * @see com.tibco.xpd.xpdl2.TransactionMethodType
     * @see #isSetTransactionMethod()
     * @see #unsetTransactionMethod()
     * @see #setTransactionMethod(TransactionMethodType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransaction_TransactionMethod()
     * @model default="Compensate" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='TransactionMethod'"
     * @generated
     */
    TransactionMethodType getTransactionMethod();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transaction#getTransactionMethod <em>Transaction Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Transaction Method</em>' attribute.
     * @see com.tibco.xpd.xpdl2.TransactionMethodType
     * @see #isSetTransactionMethod()
     * @see #unsetTransactionMethod()
     * @see #getTransactionMethod()
     * @generated
     */
    void setTransactionMethod(TransactionMethodType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Transaction#getTransactionMethod <em>Transaction Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetTransactionMethod()
     * @see #getTransactionMethod()
     * @see #setTransactionMethod(TransactionMethodType)
     * @generated
     */
    void unsetTransactionMethod();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Transaction#getTransactionMethod <em>Transaction Method</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Transaction Method</em>' attribute is set.
     * @see #unsetTransactionMethod()
     * @see #getTransactionMethod()
     * @see #setTransactionMethod(TransactionMethodType)
     * @generated
     */
    boolean isSetTransactionMethod();

    /**
     * Returns the value of the '<em><b>Transaction Protocol</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transaction Protocol</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transaction Protocol</em>' attribute.
     * @see #setTransactionProtocol(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransaction_TransactionProtocol()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='TransactionProtocol'"
     * @generated
     */
    String getTransactionProtocol();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Transaction#getTransactionProtocol <em>Transaction Protocol</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Transaction Protocol</em>' attribute.
     * @see #getTransactionProtocol()
     * @generated
     */
    void setTransactionProtocol(String value);

} // Transaction
