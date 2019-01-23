/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor.impl;

import com.tibco.xpd.script.descriptor.DescriptorPackage;
import com.tibco.xpd.script.descriptor.ProcessType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.ProcessTypeImpl#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.ProcessTypeImpl#getPackageId <em>Package Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessTypeImpl extends EObjectImpl implements ProcessType {
    /**
     * The default value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected String processId = PROCESS_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getPackageId() <em>Package Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageId()
     * @generated
     * @ordered
     */
    protected static final String PACKAGE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPackageId() <em>Package Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageId()
     * @generated
     * @ordered
     */
    protected String packageId = PACKAGE_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ProcessTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DescriptorPackage.Literals.PROCESS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessId(String newProcessId) {
        String oldProcessId = processId;
        processId = newProcessId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.PROCESS_TYPE__PROCESS_ID, oldProcessId, processId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackageId(String newPackageId) {
        String oldPackageId = packageId;
        packageId = newPackageId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.PROCESS_TYPE__PACKAGE_ID, oldPackageId, packageId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DescriptorPackage.PROCESS_TYPE__PROCESS_ID:
                return getProcessId();
            case DescriptorPackage.PROCESS_TYPE__PACKAGE_ID:
                return getPackageId();
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
            case DescriptorPackage.PROCESS_TYPE__PROCESS_ID:
                setProcessId((String)newValue);
                return;
            case DescriptorPackage.PROCESS_TYPE__PACKAGE_ID:
                setPackageId((String)newValue);
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
            case DescriptorPackage.PROCESS_TYPE__PROCESS_ID:
                setProcessId(PROCESS_ID_EDEFAULT);
                return;
            case DescriptorPackage.PROCESS_TYPE__PACKAGE_ID:
                setPackageId(PACKAGE_ID_EDEFAULT);
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
            case DescriptorPackage.PROCESS_TYPE__PROCESS_ID:
                return PROCESS_ID_EDEFAULT == null ? processId != null : !PROCESS_ID_EDEFAULT.equals(processId);
            case DescriptorPackage.PROCESS_TYPE__PACKAGE_ID:
                return PACKAGE_ID_EDEFAULT == null ? packageId != null : !PACKAGE_ID_EDEFAULT.equals(packageId);
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (processId: ");
        result.append(processId);
        result.append(", packageId: ");
        result.append(packageId);
        result.append(')');
        return result.toString();
    }

} //ProcessTypeImpl
