/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor.impl;

import com.tibco.xpd.script.descriptor.CacType;
import com.tibco.xpd.script.descriptor.DescriptorPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cac Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.CacTypeImpl#getScriptingName <em>Scripting Name</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.CacTypeImpl#getCanonicalClassName <em>Canonical Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CacTypeImpl extends EObjectImpl implements CacType {
    /**
     * The default value of the '{@link #getScriptingName() <em>Scripting Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptingName()
     * @generated
     * @ordered
     */
    protected static final String SCRIPTING_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptingName() <em>Scripting Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptingName()
     * @generated
     * @ordered
     */
    protected String scriptingName = SCRIPTING_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getCanonicalClassName() <em>Canonical Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCanonicalClassName()
     * @generated
     * @ordered
     */
    protected static final String CANONICAL_CLASS_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCanonicalClassName() <em>Canonical Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCanonicalClassName()
     * @generated
     * @ordered
     */
    protected String canonicalClassName = CANONICAL_CLASS_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CacTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DescriptorPackage.Literals.CAC_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getScriptingName() {
        return scriptingName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptingName(String newScriptingName) {
        String oldScriptingName = scriptingName;
        scriptingName = newScriptingName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.CAC_TYPE__SCRIPTING_NAME, oldScriptingName, scriptingName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCanonicalClassName() {
        return canonicalClassName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCanonicalClassName(String newCanonicalClassName) {
        String oldCanonicalClassName = canonicalClassName;
        canonicalClassName = newCanonicalClassName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.CAC_TYPE__CANONICAL_CLASS_NAME, oldCanonicalClassName, canonicalClassName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DescriptorPackage.CAC_TYPE__SCRIPTING_NAME:
                return getScriptingName();
            case DescriptorPackage.CAC_TYPE__CANONICAL_CLASS_NAME:
                return getCanonicalClassName();
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
            case DescriptorPackage.CAC_TYPE__SCRIPTING_NAME:
                setScriptingName((String)newValue);
                return;
            case DescriptorPackage.CAC_TYPE__CANONICAL_CLASS_NAME:
                setCanonicalClassName((String)newValue);
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
            case DescriptorPackage.CAC_TYPE__SCRIPTING_NAME:
                setScriptingName(SCRIPTING_NAME_EDEFAULT);
                return;
            case DescriptorPackage.CAC_TYPE__CANONICAL_CLASS_NAME:
                setCanonicalClassName(CANONICAL_CLASS_NAME_EDEFAULT);
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
            case DescriptorPackage.CAC_TYPE__SCRIPTING_NAME:
                return SCRIPTING_NAME_EDEFAULT == null ? scriptingName != null : !SCRIPTING_NAME_EDEFAULT.equals(scriptingName);
            case DescriptorPackage.CAC_TYPE__CANONICAL_CLASS_NAME:
                return CANONICAL_CLASS_NAME_EDEFAULT == null ? canonicalClassName != null : !CANONICAL_CLASS_NAME_EDEFAULT.equals(canonicalClassName);
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
        result.append(" (scriptingName: ");
        result.append(scriptingName);
        result.append(", canonicalClassName: ");
        result.append(canonicalClassName);
        result.append(')');
        return result.toString();
    }

} //CacTypeImpl
