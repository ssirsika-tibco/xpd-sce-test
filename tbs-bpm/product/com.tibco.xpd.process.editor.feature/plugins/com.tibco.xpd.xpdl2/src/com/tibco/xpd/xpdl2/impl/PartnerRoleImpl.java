/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.PartnerRole;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Partner Role</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerRoleImpl#getEndPoint <em>End Point</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerRoleImpl#getPortName <em>Port Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerRoleImpl#getRoleName <em>Role Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PartnerRoleImpl#getServiceName <em>Service Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PartnerRoleImpl extends EObjectImpl implements PartnerRole {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getEndPoint() <em>End Point</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndPoint()
     * @generated
     * @ordered
     */
    protected EndPoint endPoint;

    /**
     * The default value of the '{@link #getPortName() <em>Port Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPortName()
     * @generated
     * @ordered
     */
    protected static final String PORT_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPortName() <em>Port Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPortName()
     * @generated
     * @ordered
     */
    protected String portName = PORT_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getRoleName() <em>Role Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoleName()
     * @generated
     * @ordered
     */
    protected static final String ROLE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRoleName() <em>Role Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoleName()
     * @generated
     * @ordered
     */
    protected String roleName = ROLE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getServiceName() <em>Service Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServiceName()
     * @generated
     * @ordered
     */
    protected static final String SERVICE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getServiceName() <em>Service Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServiceName()
     * @generated
     * @ordered
     */
    protected String serviceName = SERVICE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PartnerRoleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.PARTNER_ROLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EndPoint getEndPoint() {
        return endPoint;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEndPoint(EndPoint newEndPoint,
            NotificationChain msgs) {
        EndPoint oldEndPoint = endPoint;
        endPoint = newEndPoint;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.PARTNER_ROLE__END_POINT, oldEndPoint,
                            newEndPoint);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndPoint(EndPoint newEndPoint) {
        if (newEndPoint != endPoint) {
            NotificationChain msgs = null;
            if (endPoint != null)
                msgs =
                        ((InternalEObject) endPoint).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PARTNER_ROLE__END_POINT,
                                null,
                                msgs);
            if (newEndPoint != null)
                msgs =
                        ((InternalEObject) newEndPoint).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.PARTNER_ROLE__END_POINT,
                                null,
                                msgs);
            msgs = basicSetEndPoint(newEndPoint, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTNER_ROLE__END_POINT, newEndPoint,
                    newEndPoint));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPortName() {
        return portName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPortName(String newPortName) {
        String oldPortName = portName;
        portName = newPortName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTNER_ROLE__PORT_NAME, oldPortName, portName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRoleName(String newRoleName) {
        String oldRoleName = roleName;
        roleName = newRoleName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTNER_ROLE__ROLE_NAME, oldRoleName, roleName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setServiceName(String newServiceName) {
        String oldServiceName = serviceName;
        serviceName = newServiceName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PARTNER_ROLE__SERVICE_NAME, oldServiceName,
                    serviceName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.PARTNER_ROLE__END_POINT:
            return basicSetEndPoint(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.PARTNER_ROLE__END_POINT:
            return getEndPoint();
        case Xpdl2Package.PARTNER_ROLE__PORT_NAME:
            return getPortName();
        case Xpdl2Package.PARTNER_ROLE__ROLE_NAME:
            return getRoleName();
        case Xpdl2Package.PARTNER_ROLE__SERVICE_NAME:
            return getServiceName();
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
        case Xpdl2Package.PARTNER_ROLE__END_POINT:
            setEndPoint((EndPoint) newValue);
            return;
        case Xpdl2Package.PARTNER_ROLE__PORT_NAME:
            setPortName((String) newValue);
            return;
        case Xpdl2Package.PARTNER_ROLE__ROLE_NAME:
            setRoleName((String) newValue);
            return;
        case Xpdl2Package.PARTNER_ROLE__SERVICE_NAME:
            setServiceName((String) newValue);
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
        case Xpdl2Package.PARTNER_ROLE__END_POINT:
            setEndPoint((EndPoint) null);
            return;
        case Xpdl2Package.PARTNER_ROLE__PORT_NAME:
            setPortName(PORT_NAME_EDEFAULT);
            return;
        case Xpdl2Package.PARTNER_ROLE__ROLE_NAME:
            setRoleName(ROLE_NAME_EDEFAULT);
            return;
        case Xpdl2Package.PARTNER_ROLE__SERVICE_NAME:
            setServiceName(SERVICE_NAME_EDEFAULT);
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
        case Xpdl2Package.PARTNER_ROLE__END_POINT:
            return endPoint != null;
        case Xpdl2Package.PARTNER_ROLE__PORT_NAME:
            return PORT_NAME_EDEFAULT == null ? portName != null
                    : !PORT_NAME_EDEFAULT.equals(portName);
        case Xpdl2Package.PARTNER_ROLE__ROLE_NAME:
            return ROLE_NAME_EDEFAULT == null ? roleName != null
                    : !ROLE_NAME_EDEFAULT.equals(roleName);
        case Xpdl2Package.PARTNER_ROLE__SERVICE_NAME:
            return SERVICE_NAME_EDEFAULT == null ? serviceName != null
                    : !SERVICE_NAME_EDEFAULT.equals(serviceName);
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
        result.append(" (portName: "); //$NON-NLS-1$
        result.append(portName);
        result.append(", roleName: "); //$NON-NLS-1$
        result.append(roleName);
        result.append(", serviceName: "); //$NON-NLS-1$
        result.append(serviceName);
        result.append(')');
        return result.toString();
    }

} //PartnerRoleImpl
