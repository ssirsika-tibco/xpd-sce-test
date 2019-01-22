/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Process</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Used in TaskSend to specify the Receive Task Activity of a given Process which is being invoked.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.BusinessProcess#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.BusinessProcess#getPackageRefId <em>Package Ref Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.BusinessProcess#getActivityId <em>Activity Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getBusinessProcess()
 * @model extendedMetaData="name='BusinessProcess' kind='elementOnly'"
 * @generated
 */
public interface BusinessProcess extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Id</em>' attribute.
     * @see #setProcessId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getBusinessProcess_ProcessId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='BusinessProcessId'"
     * @generated
     */
    String getProcessId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.BusinessProcess#getProcessId <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Id</em>' attribute.
     * @see #getProcessId()
     * @generated
     */
    void setProcessId(String value);

    /**
     * Returns the value of the '<em><b>Package Ref Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package Ref Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package Ref Id</em>' attribute.
     * @see #setPackageRefId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getBusinessProcess_PackageRefId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='PackageRef'"
     * @generated
     */
    String getPackageRefId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.BusinessProcess#getPackageRefId <em>Package Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package Ref Id</em>' attribute.
     * @see #getPackageRefId()
     * @generated
     */
    void setPackageRefId(String value);

    /**
     * Returns the value of the '<em><b>Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activity Id</em>' attribute.
     * @see #setActivityId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getBusinessProcess_ActivityId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='ActivityId'"
     * @generated
     */
    String getActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.BusinessProcess#getActivityId <em>Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Id</em>' attribute.
     * @see #getActivityId()
     * @generated
     */
    void setActivityId(String value);

} // BusinessProcess
