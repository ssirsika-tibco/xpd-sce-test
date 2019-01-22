/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ParameterType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ParameterTypeImpl#getComplexValue <em>Complex Value</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ParameterTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ParameterTypeImpl#isArray <em>Array</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ParameterTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParameterTypeImpl extends EObjectImpl implements ParameterType {
    /**
     * The cached value of the '{@link #getComplexValue() <em>Complex Value</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getComplexValue()
     * @generated
     * @ordered
     */
    protected EList<EObject> complexValue;

    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected EList<String> value;

    /**
     * The default value of the '{@link #isArray() <em>Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isArray()
     * @generated
     * @ordered
     */
    protected static final boolean ARRAY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isArray() <em>Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isArray()
     * @generated
     * @ordered
     */
    protected boolean array = ARRAY_EDEFAULT;

    /**
     * This is true if the Array attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean arrayESet;

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
    protected ParameterTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.PARAMETER_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<EObject> getComplexValue() {
        if (complexValue == null) {
            complexValue = new EObjectContainmentEList<EObject>(EObject.class, this, N2BRMPackage.PARAMETER_TYPE__COMPLEX_VALUE);
        }
        return complexValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getValue() {
        if (value == null) {
            value = new EDataTypeEList<String>(String.class, this, N2BRMPackage.PARAMETER_TYPE__VALUE);
        }
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isArray() {
        return array;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setArray(boolean newArray) {
        boolean oldArray = array;
        array = newArray;
        boolean oldArrayESet = arrayESet;
        arrayESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.PARAMETER_TYPE__ARRAY, oldArray, array, !oldArrayESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetArray() {
        boolean oldArray = array;
        boolean oldArrayESet = arrayESet;
        array = ARRAY_EDEFAULT;
        arrayESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.PARAMETER_TYPE__ARRAY, oldArray, ARRAY_EDEFAULT, oldArrayESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetArray() {
        return arrayESet;
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
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.PARAMETER_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.PARAMETER_TYPE__COMPLEX_VALUE:
                return ((InternalEList<?>)getComplexValue()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.PARAMETER_TYPE__COMPLEX_VALUE:
                return getComplexValue();
            case N2BRMPackage.PARAMETER_TYPE__VALUE:
                return getValue();
            case N2BRMPackage.PARAMETER_TYPE__ARRAY:
                return isArray();
            case N2BRMPackage.PARAMETER_TYPE__NAME:
                return getName();
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
            case N2BRMPackage.PARAMETER_TYPE__COMPLEX_VALUE:
                getComplexValue().clear();
                getComplexValue().addAll((Collection<? extends EObject>)newValue);
                return;
            case N2BRMPackage.PARAMETER_TYPE__VALUE:
                getValue().clear();
                getValue().addAll((Collection<? extends String>)newValue);
                return;
            case N2BRMPackage.PARAMETER_TYPE__ARRAY:
                setArray((Boolean)newValue);
                return;
            case N2BRMPackage.PARAMETER_TYPE__NAME:
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
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case N2BRMPackage.PARAMETER_TYPE__COMPLEX_VALUE:
                getComplexValue().clear();
                return;
            case N2BRMPackage.PARAMETER_TYPE__VALUE:
                getValue().clear();
                return;
            case N2BRMPackage.PARAMETER_TYPE__ARRAY:
                unsetArray();
                return;
            case N2BRMPackage.PARAMETER_TYPE__NAME:
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
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case N2BRMPackage.PARAMETER_TYPE__COMPLEX_VALUE:
                return complexValue != null && !complexValue.isEmpty();
            case N2BRMPackage.PARAMETER_TYPE__VALUE:
                return value != null && !value.isEmpty();
            case N2BRMPackage.PARAMETER_TYPE__ARRAY:
                return isSetArray();
            case N2BRMPackage.PARAMETER_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
        result.append(" (value: ");
        result.append(value);
        result.append(", array: ");
        if (arrayESet) result.append(array); else result.append("<unset>");
        result.append(", name: ");
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //ParameterTypeImpl
