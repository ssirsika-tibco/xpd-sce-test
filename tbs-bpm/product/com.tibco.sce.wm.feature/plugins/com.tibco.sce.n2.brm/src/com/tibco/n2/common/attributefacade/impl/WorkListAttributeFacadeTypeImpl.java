/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.attributefacade.impl;

import com.tibco.n2.common.attributefacade.AttributeAliasType;
import com.tibco.n2.common.attributefacade.AttributefacadePackage;
import com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work List Attribute Facade Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.attributefacade.impl.WorkListAttributeFacadeTypeImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.n2.common.attributefacade.impl.WorkListAttributeFacadeTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkListAttributeFacadeTypeImpl extends EObjectImpl implements WorkListAttributeFacadeType {
    /**
     * The cached value of the '{@link #getAlias() <em>Alias</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAlias()
     * @generated
     * @ordered
     */
    protected EList<AttributeAliasType> alias;

    /**
     * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected static final String VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected String version = VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkListAttributeFacadeTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return AttributefacadePackage.Literals.WORK_LIST_ATTRIBUTE_FACADE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<AttributeAliasType> getAlias() {
        if (alias == null) {
            alias = new EObjectContainmentEList<AttributeAliasType>(AttributeAliasType.class, this, AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__ALIAS);
        }
        return alias;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(String newVersion) {
        String oldVersion = version;
        version = newVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__ALIAS:
                return ((InternalEList<?>)getAlias()).basicRemove(otherEnd, msgs);
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
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__ALIAS:
                return getAlias();
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__VERSION:
                return getVersion();
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
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__ALIAS:
                getAlias().clear();
                getAlias().addAll((Collection<? extends AttributeAliasType>)newValue);
                return;
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__VERSION:
                setVersion((String)newValue);
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
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__ALIAS:
                getAlias().clear();
                return;
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__VERSION:
                setVersion(VERSION_EDEFAULT);
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
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__ALIAS:
                return alias != null && !alias.isEmpty();
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE__VERSION:
                return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
        result.append(" (version: ");
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //WorkListAttributeFacadeTypeImpl
