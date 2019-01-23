/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Port Type Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.PortTypeOperationImpl#getPortTypeName <em>Port Type Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.PortTypeOperationImpl#getOperationName <em>Operation Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.PortTypeOperationImpl#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.PortTypeOperationImpl#getTransport <em>Transport</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PortTypeOperationImpl extends EObjectImpl
        implements PortTypeOperation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getPortTypeName() <em>Port Type Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPortTypeName()
     * @generated
     * @ordered
     */
    protected static final String PORT_TYPE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPortTypeName() <em>Port Type Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPortTypeName()
     * @generated
     * @ordered
     */
    protected String portTypeName = PORT_TYPE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getOperationName() <em>Operation Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOperationName()
     * @generated
     * @ordered
     */
    protected static final String OPERATION_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOperationName() <em>Operation Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOperationName()
     * @generated
     * @ordered
     */
    protected String operationName = OPERATION_NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getExternalReference() <em>External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExternalReference()
     * @generated
     * @ordered
     */
    protected ExternalReference externalReference;

    /**
     * The default value of the '{@link #getTransport() <em>Transport</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransport()
     * @generated
     * @ordered
     */
    protected static final String TRANSPORT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTransport() <em>Transport</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTransport()
     * @generated
     * @ordered
     */
    protected String transport = TRANSPORT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PortTypeOperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.PORT_TYPE_OPERATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPortTypeName() {
        return portTypeName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPortTypeName(String newPortTypeName) {
        String oldPortTypeName = portTypeName;
        portTypeName = newPortTypeName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PORT_TYPE_OPERATION__PORT_TYPE_NAME,
                    oldPortTypeName, portTypeName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOperationName(String newOperationName) {
        String oldOperationName = operationName;
        operationName = newOperationName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PORT_TYPE_OPERATION__OPERATION_NAME,
                    oldOperationName, operationName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExternalReference getExternalReference() {
        return externalReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExternalReference(
            ExternalReference newExternalReference, NotificationChain msgs) {
        ExternalReference oldExternalReference = externalReference;
        externalReference = newExternalReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE,
                    oldExternalReference, newExternalReference);
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
    public void setExternalReference(ExternalReference newExternalReference) {
        if (newExternalReference != externalReference) {
            NotificationChain msgs = null;
            if (externalReference != null)
                msgs = ((InternalEObject) externalReference).eInverseRemove(
                        this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE,
                        null,
                        msgs);
            if (newExternalReference != null)
                msgs = ((InternalEObject) newExternalReference).eInverseAdd(
                        this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE,
                        null,
                        msgs);
            msgs = basicSetExternalReference(newExternalReference, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE,
                    newExternalReference, newExternalReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTransport() {
        return transport;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTransport(String newTransport) {
        String oldTransport = transport;
        transport = newTransport;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.PORT_TYPE_OPERATION__TRANSPORT,
                    oldTransport, transport));
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
        case XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE:
            return basicSetExternalReference(null, msgs);
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
        case XpdExtensionPackage.PORT_TYPE_OPERATION__PORT_TYPE_NAME:
            return getPortTypeName();
        case XpdExtensionPackage.PORT_TYPE_OPERATION__OPERATION_NAME:
            return getOperationName();
        case XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE:
            return getExternalReference();
        case XpdExtensionPackage.PORT_TYPE_OPERATION__TRANSPORT:
            return getTransport();
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
        case XpdExtensionPackage.PORT_TYPE_OPERATION__PORT_TYPE_NAME:
            setPortTypeName((String) newValue);
            return;
        case XpdExtensionPackage.PORT_TYPE_OPERATION__OPERATION_NAME:
            setOperationName((String) newValue);
            return;
        case XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE:
            setExternalReference((ExternalReference) newValue);
            return;
        case XpdExtensionPackage.PORT_TYPE_OPERATION__TRANSPORT:
            setTransport((String) newValue);
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
        case XpdExtensionPackage.PORT_TYPE_OPERATION__PORT_TYPE_NAME:
            setPortTypeName(PORT_TYPE_NAME_EDEFAULT);
            return;
        case XpdExtensionPackage.PORT_TYPE_OPERATION__OPERATION_NAME:
            setOperationName(OPERATION_NAME_EDEFAULT);
            return;
        case XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE:
            setExternalReference((ExternalReference) null);
            return;
        case XpdExtensionPackage.PORT_TYPE_OPERATION__TRANSPORT:
            setTransport(TRANSPORT_EDEFAULT);
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
        case XpdExtensionPackage.PORT_TYPE_OPERATION__PORT_TYPE_NAME:
            return PORT_TYPE_NAME_EDEFAULT == null ? portTypeName != null
                    : !PORT_TYPE_NAME_EDEFAULT.equals(portTypeName);
        case XpdExtensionPackage.PORT_TYPE_OPERATION__OPERATION_NAME:
            return OPERATION_NAME_EDEFAULT == null ? operationName != null
                    : !OPERATION_NAME_EDEFAULT.equals(operationName);
        case XpdExtensionPackage.PORT_TYPE_OPERATION__EXTERNAL_REFERENCE:
            return externalReference != null;
        case XpdExtensionPackage.PORT_TYPE_OPERATION__TRANSPORT:
            return TRANSPORT_EDEFAULT == null ? transport != null
                    : !TRANSPORT_EDEFAULT.equals(transport);
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
        result.append(" (portTypeName: "); //$NON-NLS-1$
        result.append(portTypeName);
        result.append(", operationName: "); //$NON-NLS-1$
        result.append(operationName);
        result.append(", transport: "); //$NON-NLS-1$
        result.append(transport);
        result.append(')');
        return result.toString();
    }

} //PortTypeOperationImpl
