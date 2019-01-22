/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor.impl;

import com.tibco.xpd.script.descriptor.DescriptorPackage;
import com.tibco.xpd.script.descriptor.FactoryType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Factory Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.FactoryTypeImpl#getScriptingName <em>Scripting Name</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.FactoryTypeImpl#getCanonicalClassName <em>Canonical Class Name</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.impl.FactoryTypeImpl#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FactoryTypeImpl extends EObjectImpl implements FactoryType {
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
     * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNamespace()
     * @generated
     * @ordered
     */
    protected static final String NAMESPACE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNamespace()
     * @generated
     * @ordered
     */
    protected String namespace = NAMESPACE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FactoryTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DescriptorPackage.Literals.FACTORY_TYPE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.FACTORY_TYPE__SCRIPTING_NAME, oldScriptingName, scriptingName));
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
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.FACTORY_TYPE__CANONICAL_CLASS_NAME, oldCanonicalClassName, canonicalClassName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNamespace(String newNamespace) {
        String oldNamespace = namespace;
        namespace = newNamespace;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptorPackage.FACTORY_TYPE__NAMESPACE, oldNamespace, namespace));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DescriptorPackage.FACTORY_TYPE__SCRIPTING_NAME:
                return getScriptingName();
            case DescriptorPackage.FACTORY_TYPE__CANONICAL_CLASS_NAME:
                return getCanonicalClassName();
            case DescriptorPackage.FACTORY_TYPE__NAMESPACE:
                return getNamespace();
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
            case DescriptorPackage.FACTORY_TYPE__SCRIPTING_NAME:
                setScriptingName((String)newValue);
                return;
            case DescriptorPackage.FACTORY_TYPE__CANONICAL_CLASS_NAME:
                setCanonicalClassName((String)newValue);
                return;
            case DescriptorPackage.FACTORY_TYPE__NAMESPACE:
                setNamespace((String)newValue);
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
            case DescriptorPackage.FACTORY_TYPE__SCRIPTING_NAME:
                setScriptingName(SCRIPTING_NAME_EDEFAULT);
                return;
            case DescriptorPackage.FACTORY_TYPE__CANONICAL_CLASS_NAME:
                setCanonicalClassName(CANONICAL_CLASS_NAME_EDEFAULT);
                return;
            case DescriptorPackage.FACTORY_TYPE__NAMESPACE:
                setNamespace(NAMESPACE_EDEFAULT);
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
            case DescriptorPackage.FACTORY_TYPE__SCRIPTING_NAME:
                return SCRIPTING_NAME_EDEFAULT == null ? scriptingName != null : !SCRIPTING_NAME_EDEFAULT.equals(scriptingName);
            case DescriptorPackage.FACTORY_TYPE__CANONICAL_CLASS_NAME:
                return CANONICAL_CLASS_NAME_EDEFAULT == null ? canonicalClassName != null : !CANONICAL_CLASS_NAME_EDEFAULT.equals(canonicalClassName);
            case DescriptorPackage.FACTORY_TYPE__NAMESPACE:
                return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
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
        result.append(", namespace: ");
        result.append(namespace);
        result.append(')');
        return result.toString();
    }

} //FactoryTypeImpl
