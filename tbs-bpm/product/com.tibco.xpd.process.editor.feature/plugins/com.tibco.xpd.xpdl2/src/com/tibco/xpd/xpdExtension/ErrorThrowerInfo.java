/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Message;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error Thrower Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element is added to the xpdl:ResultError element of an intermediate
 * catch error event when the error has been set (in Business Studio) to
 * catch an error thrown by a specific activity.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerId <em>Thrower Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerContainerId <em>Thrower Container Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerType <em>Thrower Type</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getErrorThrowerInfo()
 * @model extendedMetaData="name='ErrorThrowerInfo' kind='elementOnly'"
 * @generated
 */
public interface ErrorThrowerInfo extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Thrower Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Id of object (nominally Activity / Process Interface Method that throws the error.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Thrower Id</em>' attribute.
     * @see #isSetThrowerId()
     * @see #unsetThrowerId()
     * @see #setThrowerId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getErrorThrowerInfo_ThrowerId()
     * @model unsettable="true" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ThrowerId'"
     * @generated
     */
    String getThrowerId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerId <em>Thrower Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Thrower Id</em>' attribute.
     * @see #isSetThrowerId()
     * @see #unsetThrowerId()
     * @see #getThrowerId()
     * @generated
     */
    void setThrowerId(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerId <em>Thrower Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetThrowerId()
     * @see #getThrowerId()
     * @see #setThrowerId(String)
     * @generated
     */
    void unsetThrowerId();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerId <em>Thrower Id</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Thrower Id</em>' attribute is set.
     * @see #unsetThrowerId()
     * @see #getThrowerId()
     * @see #setThrowerId(String)
     * @generated
     */
    boolean isSetThrowerId();

    /**
     * Returns the value of the '<em><b>Thrower Container Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Id of the container of the object (nominally Process / Process Interface that throws the error.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Thrower Container Id</em>' attribute.
     * @see #isSetThrowerContainerId()
     * @see #unsetThrowerContainerId()
     * @see #setThrowerContainerId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getErrorThrowerInfo_ThrowerContainerId()
     * @model unsettable="true" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ThrowerContainerId'"
     * @generated
     */
    String getThrowerContainerId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerContainerId <em>Thrower Container Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Thrower Container Id</em>' attribute.
     * @see #isSetThrowerContainerId()
     * @see #unsetThrowerContainerId()
     * @see #getThrowerContainerId()
     * @generated
     */
    void setThrowerContainerId(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerContainerId <em>Thrower Container Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetThrowerContainerId()
     * @see #getThrowerContainerId()
     * @see #setThrowerContainerId(String)
     * @generated
     */
    void unsetThrowerContainerId();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerContainerId <em>Thrower Container Id</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Thrower Container Id</em>' attribute is set.
     * @see #unsetThrowerContainerId()
     * @see #getThrowerContainerId()
     * @see #setThrowerContainerId(String)
     * @generated
     */
    boolean isSetThrowerContainerId();

    /**
     * Returns the value of the '<em><b>Thrower Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.ErrorThrowerType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The type of error thrower specified by the throwerId and throweerContainerId.
     * This allows the model reader to interpret the throwerId and throwerContainerId
     * accurartely (i.e. ProcessActivity means that these are an XPDL Activity Id and Process Id, 
     * InterfaceEvent means that these are a xpdExt ProcessInterface Event Id and ProcessInteface Id).
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Thrower Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.ErrorThrowerType
     * @see #isSetThrowerType()
     * @see #unsetThrowerType()
     * @see #setThrowerType(ErrorThrowerType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getErrorThrowerInfo_ThrowerType()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='ThrowerType'"
     * @generated
     */
    ErrorThrowerType getThrowerType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerType <em>Thrower Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Thrower Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.ErrorThrowerType
     * @see #isSetThrowerType()
     * @see #unsetThrowerType()
     * @see #getThrowerType()
     * @generated
     */
    void setThrowerType(ErrorThrowerType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerType <em>Thrower Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetThrowerType()
     * @see #getThrowerType()
     * @see #setThrowerType(ErrorThrowerType)
     * @generated
     */
    void unsetThrowerType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ErrorThrowerInfo#getThrowerType <em>Thrower Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Thrower Type</em>' attribute is set.
     * @see #unsetThrowerType()
     * @see #getThrowerType()
     * @see #setThrowerType(ErrorThrowerType)
     * @generated
     */
    boolean isSetThrowerType();

} // ErrorThrowerInfo
