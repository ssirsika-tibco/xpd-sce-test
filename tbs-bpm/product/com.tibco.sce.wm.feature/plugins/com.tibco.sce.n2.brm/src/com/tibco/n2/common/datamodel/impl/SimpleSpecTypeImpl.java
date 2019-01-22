/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.common.datamodel.DatamodelPackage;
import com.tibco.n2.common.datamodel.SimpleSpecType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Spec Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.SimpleSpecTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.SimpleSpecTypeImpl#getDecimal <em>Decimal</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.SimpleSpecTypeImpl#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimpleSpecTypeImpl extends EObjectImpl implements SimpleSpecType {
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
     * The default value of the '{@link #getDecimal() <em>Decimal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDecimal()
     * @generated
     * @ordered
     */
    protected static final int DECIMAL_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getDecimal() <em>Decimal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDecimal()
     * @generated
     * @ordered
     */
    protected int decimal = DECIMAL_EDEFAULT;

    /**
     * This is true if the Decimal attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean decimalESet;

    /**
     * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLength()
     * @generated
     * @ordered
     */
    protected static final int LENGTH_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLength()
     * @generated
     * @ordered
     */
    protected int length = LENGTH_EDEFAULT;

    /**
     * This is true if the Length attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean lengthESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimpleSpecTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatamodelPackage.Literals.SIMPLE_SPEC_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getValue() {
        if (value == null) {
            value = new EDataTypeEList<String>(String.class, this, DatamodelPackage.SIMPLE_SPEC_TYPE__VALUE);
        }
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getDecimal() {
        return decimal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDecimal(int newDecimal) {
        int oldDecimal = decimal;
        decimal = newDecimal;
        boolean oldDecimalESet = decimalESet;
        decimalESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.SIMPLE_SPEC_TYPE__DECIMAL, oldDecimal, decimal, !oldDecimalESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDecimal() {
        int oldDecimal = decimal;
        boolean oldDecimalESet = decimalESet;
        decimal = DECIMAL_EDEFAULT;
        decimalESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.SIMPLE_SPEC_TYPE__DECIMAL, oldDecimal, DECIMAL_EDEFAULT, oldDecimalESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDecimal() {
        return decimalESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getLength() {
        return length;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLength(int newLength) {
        int oldLength = length;
        length = newLength;
        boolean oldLengthESet = lengthESet;
        lengthESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.SIMPLE_SPEC_TYPE__LENGTH, oldLength, length, !oldLengthESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetLength() {
        int oldLength = length;
        boolean oldLengthESet = lengthESet;
        length = LENGTH_EDEFAULT;
        lengthESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.SIMPLE_SPEC_TYPE__LENGTH, oldLength, LENGTH_EDEFAULT, oldLengthESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetLength() {
        return lengthESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DatamodelPackage.SIMPLE_SPEC_TYPE__VALUE:
                return getValue();
            case DatamodelPackage.SIMPLE_SPEC_TYPE__DECIMAL:
                return getDecimal();
            case DatamodelPackage.SIMPLE_SPEC_TYPE__LENGTH:
                return getLength();
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
            case DatamodelPackage.SIMPLE_SPEC_TYPE__VALUE:
                getValue().clear();
                getValue().addAll((Collection<? extends String>)newValue);
                return;
            case DatamodelPackage.SIMPLE_SPEC_TYPE__DECIMAL:
                setDecimal((Integer)newValue);
                return;
            case DatamodelPackage.SIMPLE_SPEC_TYPE__LENGTH:
                setLength((Integer)newValue);
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
            case DatamodelPackage.SIMPLE_SPEC_TYPE__VALUE:
                getValue().clear();
                return;
            case DatamodelPackage.SIMPLE_SPEC_TYPE__DECIMAL:
                unsetDecimal();
                return;
            case DatamodelPackage.SIMPLE_SPEC_TYPE__LENGTH:
                unsetLength();
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
            case DatamodelPackage.SIMPLE_SPEC_TYPE__VALUE:
                return value != null && !value.isEmpty();
            case DatamodelPackage.SIMPLE_SPEC_TYPE__DECIMAL:
                return isSetDecimal();
            case DatamodelPackage.SIMPLE_SPEC_TYPE__LENGTH:
                return isSetLength();
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
        result.append(", decimal: ");
        if (decimalESet) result.append(decimal); else result.append("<unset>");
        result.append(", length: ");
        if (lengthESet) result.append(length); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //SimpleSpecTypeImpl
