/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.XpdExtProperty;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ws Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsBindingImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsBindingImpl#getExtendedProperties <em>Extended Properties</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsBindingImpl extends EObjectImpl implements WsBinding {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getExtendedProperties() <em>Extended Properties</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExtendedProperties()
     * @generated
     * @ordered
     */
    protected EList<XpdExtProperty> extendedProperties;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WsBindingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.WS_BINDING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_BINDING__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<XpdExtProperty> getExtendedProperties() {
        if (extendedProperties == null) {
            extendedProperties = new EObjectResolvingEList<XpdExtProperty>(
                    XpdExtProperty.class, this,
                    XpdExtensionPackage.WS_BINDING__EXTENDED_PROPERTIES);
        }
        return extendedProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.WS_BINDING__NAME:
            return getName();
        case XpdExtensionPackage.WS_BINDING__EXTENDED_PROPERTIES:
            return getExtendedProperties();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.WS_BINDING__NAME:
            setName((String) newValue);
            return;
        case XpdExtensionPackage.WS_BINDING__EXTENDED_PROPERTIES:
            getExtendedProperties().clear();
            getExtendedProperties()
                    .addAll((Collection<? extends XpdExtProperty>) newValue);
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
        case XpdExtensionPackage.WS_BINDING__NAME:
            setName(NAME_EDEFAULT);
            return;
        case XpdExtensionPackage.WS_BINDING__EXTENDED_PROPERTIES:
            getExtendedProperties().clear();
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
        case XpdExtensionPackage.WS_BINDING__NAME:
            return NAME_EDEFAULT == null ? name != null
                    : !NAME_EDEFAULT.equals(name);
        case XpdExtensionPackage.WS_BINDING__EXTENDED_PROPERTIES:
            return extendedProperties != null && !extendedProperties.isEmpty();
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
        result.append(" (name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //WsBindingImpl
