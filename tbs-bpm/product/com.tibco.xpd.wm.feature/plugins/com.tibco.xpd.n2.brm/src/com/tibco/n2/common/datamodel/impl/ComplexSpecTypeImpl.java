/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.common.datamodel.ComplexSpecType;
import com.tibco.n2.common.datamodel.DatamodelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Complex Spec Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.ComplexSpecTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.ComplexSpecTypeImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.ComplexSpecTypeImpl#getGoRefId <em>Go Ref Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComplexSpecTypeImpl extends EObjectImpl implements ComplexSpecType {
    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected EList<EObject> value;

    /**
     * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClassName()
     * @generated
     * @ordered
     */
    protected static final String CLASS_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClassName()
     * @generated
     * @ordered
     */
    protected String className = CLASS_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getGoRefId() <em>Go Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGoRefId()
     * @generated
     * @ordered
     */
    protected static final String GO_REF_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getGoRefId() <em>Go Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGoRefId()
     * @generated
     * @ordered
     */
    protected String goRefId = GO_REF_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ComplexSpecTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatamodelPackage.Literals.COMPLEX_SPEC_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<EObject> getValue() {
        if (value == null) {
            value = new EObjectContainmentEList<EObject>(EObject.class, this, DatamodelPackage.COMPLEX_SPEC_TYPE__VALUE);
        }
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getClassName() {
        return className;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setClassName(String newClassName) {
        String oldClassName = className;
        className = newClassName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.COMPLEX_SPEC_TYPE__CLASS_NAME, oldClassName, className));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getGoRefId() {
        return goRefId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGoRefId(String newGoRefId) {
        String oldGoRefId = goRefId;
        goRefId = newGoRefId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.COMPLEX_SPEC_TYPE__GO_REF_ID, oldGoRefId, goRefId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DatamodelPackage.COMPLEX_SPEC_TYPE__VALUE:
                return ((InternalEList<?>)getValue()).basicRemove(otherEnd, msgs);
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
            case DatamodelPackage.COMPLEX_SPEC_TYPE__VALUE:
                return getValue();
            case DatamodelPackage.COMPLEX_SPEC_TYPE__CLASS_NAME:
                return getClassName();
            case DatamodelPackage.COMPLEX_SPEC_TYPE__GO_REF_ID:
                return getGoRefId();
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
            case DatamodelPackage.COMPLEX_SPEC_TYPE__VALUE:
                getValue().clear();
                getValue().addAll((Collection<? extends EObject>)newValue);
                return;
            case DatamodelPackage.COMPLEX_SPEC_TYPE__CLASS_NAME:
                setClassName((String)newValue);
                return;
            case DatamodelPackage.COMPLEX_SPEC_TYPE__GO_REF_ID:
                setGoRefId((String)newValue);
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
            case DatamodelPackage.COMPLEX_SPEC_TYPE__VALUE:
                getValue().clear();
                return;
            case DatamodelPackage.COMPLEX_SPEC_TYPE__CLASS_NAME:
                setClassName(CLASS_NAME_EDEFAULT);
                return;
            case DatamodelPackage.COMPLEX_SPEC_TYPE__GO_REF_ID:
                setGoRefId(GO_REF_ID_EDEFAULT);
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
            case DatamodelPackage.COMPLEX_SPEC_TYPE__VALUE:
                return value != null && !value.isEmpty();
            case DatamodelPackage.COMPLEX_SPEC_TYPE__CLASS_NAME:
                return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
            case DatamodelPackage.COMPLEX_SPEC_TYPE__GO_REF_ID:
                return GO_REF_ID_EDEFAULT == null ? goRefId != null : !GO_REF_ID_EDEFAULT.equals(goRefId);
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
        result.append(" (className: ");
        result.append(className);
        result.append(", goRefId: ");
        result.append(goRefId);
        result.append(')');
        return result.toString();
    }

} //ComplexSpecTypeImpl
