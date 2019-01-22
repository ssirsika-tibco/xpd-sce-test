/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Method Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.MethodTypeImpl#getReturn <em>Return</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.MethodTypeImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.MethodTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MethodTypeImpl extends EObjectImpl implements MethodType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getReturn() <em>Return</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReturn()
     * @generated
     * @ordered
     */
    protected ParameterType return_ = null;

    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected ParametersType parameters = null;

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
    protected MethodTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EClass eStaticClass() {
        return EaijavaPackage.Literals.METHOD_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterType getReturn() {
        return return_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReturn(ParameterType newReturn, NotificationChain msgs) {
        ParameterType oldReturn = return_;
        return_ = newReturn;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EaijavaPackage.METHOD_TYPE__RETURN, oldReturn, newReturn);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReturn(ParameterType newReturn) {
        if (newReturn != return_) {
            NotificationChain msgs = null;
            if (return_ != null)
                msgs = ((InternalEObject)return_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.METHOD_TYPE__RETURN, null, msgs);
            if (newReturn != null)
                msgs = ((InternalEObject)newReturn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.METHOD_TYPE__RETURN, null, msgs);
            msgs = basicSetReturn(newReturn, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EaijavaPackage.METHOD_TYPE__RETURN, newReturn, newReturn));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParametersType getParameters() {
        return parameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParameters(ParametersType newParameters, NotificationChain msgs) {
        ParametersType oldParameters = parameters;
        parameters = newParameters;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EaijavaPackage.METHOD_TYPE__PARAMETERS, oldParameters, newParameters);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameters(ParametersType newParameters) {
        if (newParameters != parameters) {
            NotificationChain msgs = null;
            if (parameters != null)
                msgs = ((InternalEObject)parameters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.METHOD_TYPE__PARAMETERS, null, msgs);
            if (newParameters != null)
                msgs = ((InternalEObject)newParameters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.METHOD_TYPE__PARAMETERS, null, msgs);
            msgs = basicSetParameters(newParameters, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EaijavaPackage.METHOD_TYPE__PARAMETERS, newParameters, newParameters));
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
            eNotify(new ENotificationImpl(this, Notification.SET, EaijavaPackage.METHOD_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case EaijavaPackage.METHOD_TYPE__RETURN:
                return basicSetReturn(null, msgs);
            case EaijavaPackage.METHOD_TYPE__PARAMETERS:
                return basicSetParameters(null, msgs);
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
            case EaijavaPackage.METHOD_TYPE__RETURN:
                return getReturn();
            case EaijavaPackage.METHOD_TYPE__PARAMETERS:
                return getParameters();
            case EaijavaPackage.METHOD_TYPE__NAME:
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
            case EaijavaPackage.METHOD_TYPE__RETURN:
                setReturn((ParameterType)newValue);
                return;
            case EaijavaPackage.METHOD_TYPE__PARAMETERS:
                setParameters((ParametersType)newValue);
                return;
            case EaijavaPackage.METHOD_TYPE__NAME:
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
            case EaijavaPackage.METHOD_TYPE__RETURN:
                setReturn((ParameterType)null);
                return;
            case EaijavaPackage.METHOD_TYPE__PARAMETERS:
                setParameters((ParametersType)null);
                return;
            case EaijavaPackage.METHOD_TYPE__NAME:
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
            case EaijavaPackage.METHOD_TYPE__RETURN:
                return return_ != null;
            case EaijavaPackage.METHOD_TYPE__PARAMETERS:
                return parameters != null;
            case EaijavaPackage.METHOD_TYPE__NAME:
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

} //MethodTypeImpl