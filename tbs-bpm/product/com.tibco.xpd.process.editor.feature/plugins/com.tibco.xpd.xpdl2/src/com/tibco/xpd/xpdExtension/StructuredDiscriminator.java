/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structured Discriminator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.StructuredDiscriminator#getWaitForIncomingPath <em>Wait For Incoming Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.StructuredDiscriminator#getUpStreamParallelSplit <em>Up Stream Parallel Split</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getStructuredDiscriminator()
 * @model extendedMetaData="name='StructuredDiscriminator' kind='Element' namespace='##targetNamespace'"
 * @generated
 */
public interface StructuredDiscriminator extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Wait For Incoming Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Wait For Incoming Path</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Wait For Incoming Path</em>' attribute.
     * @see #setWaitForIncomingPath(BigInteger)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getStructuredDiscriminator_WaitForIncomingPath()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Integer" required="true"
     *        extendedMetaData="kind='element' name='WaitForIncomingPath' namespace='##targetNamespace'"
     * @generated
     */
    BigInteger getWaitForIncomingPath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.StructuredDiscriminator#getWaitForIncomingPath <em>Wait For Incoming Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Wait For Incoming Path</em>' attribute.
     * @see #getWaitForIncomingPath()
     * @generated
     */
    void setWaitForIncomingPath(BigInteger value);

    /**
     * Returns the value of the '<em><b>Up Stream Parallel Split</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Up Stream Parallel Split</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Up Stream Parallel Split</em>' attribute.
     * @see #setUpStreamParallelSplit(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getStructuredDiscriminator_UpStreamParallelSplit()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='element' name='UpStreamParallelSplit' namespace='##targetNamespace'"
     * @generated
     */
    String getUpStreamParallelSplit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.StructuredDiscriminator#getUpStreamParallelSplit <em>Up Stream Parallel Split</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Up Stream Parallel Split</em>' attribute.
     * @see #getUpStreamParallelSplit()
     * @generated
     */
    void setUpStreamParallelSplit(String value);

} // StructuredDiscriminator
