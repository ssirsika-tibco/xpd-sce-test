/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ClassTypeImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ClassTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassTypeImpl extends EObjectImpl implements ClassType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getMethod() <em>Method</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMethod()
     * @generated
     * @ordered
     */
    protected MethodType method = null;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ClassTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EClass eStaticClass() {
        return EaijavaPackage.Literals.CLASS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MethodType getMethod() {
        return method;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMethod(MethodType newMethod, NotificationChain msgs) {
        MethodType oldMethod = method;
        method = newMethod;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EaijavaPackage.CLASS_TYPE__METHOD, oldMethod, newMethod);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMethod(MethodType newMethod) {
        if (newMethod != method) {
            NotificationChain msgs = null;
            if (method != null)
                msgs = ((InternalEObject)method).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.CLASS_TYPE__METHOD, null, msgs);
            if (newMethod != null)
                msgs = ((InternalEObject)newMethod).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.CLASS_TYPE__METHOD, null, msgs);
            msgs = basicSetMethod(newMethod, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EaijavaPackage.CLASS_TYPE__METHOD, newMethod, newMethod));
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
            eNotify(new ENotificationImpl(this, Notification.SET, EaijavaPackage.CLASS_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case EaijavaPackage.CLASS_TYPE__METHOD:
                return basicSetMethod(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case EaijavaPackage.CLASS_TYPE__METHOD:
                return getMethod();
            case EaijavaPackage.CLASS_TYPE__NAME:
                return getName();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case EaijavaPackage.CLASS_TYPE__METHOD:
                setMethod((MethodType)newValue);
                return;
            case EaijavaPackage.CLASS_TYPE__NAME:
                setName((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eUnset(int featureID) {
        switch (featureID) {
            case EaijavaPackage.CLASS_TYPE__METHOD:
                setMethod((MethodType)null);
                return;
            case EaijavaPackage.CLASS_TYPE__NAME:
                setName(NAME_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case EaijavaPackage.CLASS_TYPE__METHOD:
                return method != null;
            case EaijavaPackage.CLASS_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //ClassTypeImpl