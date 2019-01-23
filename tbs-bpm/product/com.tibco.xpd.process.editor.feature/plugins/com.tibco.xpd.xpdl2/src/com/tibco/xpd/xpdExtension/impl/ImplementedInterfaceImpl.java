/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Implemented Interface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ImplementedInterfaceImpl#getPackageRef <em>Package Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ImplementedInterfaceImpl#getProcessInterfaceId <em>Process Interface Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImplementedInterfaceImpl extends EObjectImpl
        implements ImplementedInterface {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getPackageRef() <em>Package Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageRef()
     * @generated
     * @ordered
     */
    protected static final String PACKAGE_REF_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPackageRef() <em>Package Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageRef()
     * @generated
     * @ordered
     */
    protected String packageRef = PACKAGE_REF_EDEFAULT;

    /**
     * The default value of the '{@link #getProcessInterfaceId() <em>Process Interface Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessInterfaceId()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_INTERFACE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessInterfaceId() <em>Process Interface Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessInterfaceId()
     * @generated
     * @ordered
     */
    protected String processInterfaceId = PROCESS_INTERFACE_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ImplementedInterfaceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.IMPLEMENTED_INTERFACE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPackageRef() {
        return packageRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackageRef(String newPackageRef) {
        String oldPackageRef = packageRef;
        packageRef = newPackageRef;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.IMPLEMENTED_INTERFACE__PACKAGE_REF,
                    oldPackageRef, packageRef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessInterfaceId() {
        return processInterfaceId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessInterfaceId(String newProcessInterfaceId) {
        String oldProcessInterfaceId = processInterfaceId;
        processInterfaceId = newProcessInterfaceId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.IMPLEMENTED_INTERFACE__PROCESS_INTERFACE_ID,
                    oldProcessInterfaceId, processInterfaceId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE__PACKAGE_REF:
            return getPackageRef();
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE__PROCESS_INTERFACE_ID:
            return getProcessInterfaceId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE__PACKAGE_REF:
            setPackageRef((String) newValue);
            return;
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE__PROCESS_INTERFACE_ID:
            setProcessInterfaceId((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE__PACKAGE_REF:
            setPackageRef(PACKAGE_REF_EDEFAULT);
            return;
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE__PROCESS_INTERFACE_ID:
            setProcessInterfaceId(PROCESS_INTERFACE_ID_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE__PACKAGE_REF:
            return PACKAGE_REF_EDEFAULT == null ? packageRef != null
                    : !PACKAGE_REF_EDEFAULT.equals(packageRef);
        case XpdExtensionPackage.IMPLEMENTED_INTERFACE__PROCESS_INTERFACE_ID:
            return PROCESS_INTERFACE_ID_EDEFAULT == null
                    ? processInterfaceId != null
                    : !PROCESS_INTERFACE_ID_EDEFAULT.equals(processInterfaceId);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (packageRef: "); //$NON-NLS-1$
        result.append(packageRef);
        result.append(", processInterfaceId: "); //$NON-NLS-1$
        result.append(processInterfaceId);
        result.append(')');
        return result.toString();
    }

} //ImplementedInterfaceImpl