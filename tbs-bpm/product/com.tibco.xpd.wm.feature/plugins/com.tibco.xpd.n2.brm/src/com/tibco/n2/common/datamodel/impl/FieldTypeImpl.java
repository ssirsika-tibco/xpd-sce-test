/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.common.datamodel.ComplexSpecType;
import com.tibco.n2.common.datamodel.DatamodelPackage;
import com.tibco.n2.common.datamodel.FieldType;
import com.tibco.n2.common.datamodel.SimpleSpecType;
import com.tibco.n2.common.datamodel.TypeType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.FieldTypeImpl#getSimpleSpec <em>Simple Spec</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.FieldTypeImpl#getComplexSpec <em>Complex Spec</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.FieldTypeImpl#isArray <em>Array</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.FieldTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.FieldTypeImpl#isOptional <em>Optional</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.FieldTypeImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FieldTypeImpl extends EObjectImpl implements FieldType {
    /**
     * The cached value of the '{@link #getSimpleSpec() <em>Simple Spec</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSimpleSpec()
     * @generated
     * @ordered
     */
    protected SimpleSpecType simpleSpec;

    /**
     * The cached value of the '{@link #getComplexSpec() <em>Complex Spec</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getComplexSpec()
     * @generated
     * @ordered
     */
    protected ComplexSpecType complexSpec;

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
     * The default value of the '{@link #isOptional() <em>Optional</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isOptional()
     * @generated
     * @ordered
     */
    protected static final boolean OPTIONAL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isOptional() <em>Optional</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isOptional()
     * @generated
     * @ordered
     */
    protected boolean optional = OPTIONAL_EDEFAULT;

    /**
     * This is true if the Optional attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean optionalESet;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final TypeType TYPE_EDEFAULT = TypeType.STRING;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected TypeType type = TYPE_EDEFAULT;

    /**
     * This is true if the Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean typeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FieldTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatamodelPackage.Literals.FIELD_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimpleSpecType getSimpleSpec() {
        return simpleSpec;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSimpleSpec(SimpleSpecType newSimpleSpec, NotificationChain msgs) {
        SimpleSpecType oldSimpleSpec = simpleSpec;
        simpleSpec = newSimpleSpec;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC, oldSimpleSpec, newSimpleSpec);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSimpleSpec(SimpleSpecType newSimpleSpec) {
        if (newSimpleSpec != simpleSpec) {
            NotificationChain msgs = null;
            if (simpleSpec != null)
                msgs = ((InternalEObject)simpleSpec).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC, null, msgs);
            if (newSimpleSpec != null)
                msgs = ((InternalEObject)newSimpleSpec).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC, null, msgs);
            msgs = basicSetSimpleSpec(newSimpleSpec, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC, newSimpleSpec, newSimpleSpec));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ComplexSpecType getComplexSpec() {
        return complexSpec;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetComplexSpec(ComplexSpecType newComplexSpec, NotificationChain msgs) {
        ComplexSpecType oldComplexSpec = complexSpec;
        complexSpec = newComplexSpec;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC, oldComplexSpec, newComplexSpec);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setComplexSpec(ComplexSpecType newComplexSpec) {
        if (newComplexSpec != complexSpec) {
            NotificationChain msgs = null;
            if (complexSpec != null)
                msgs = ((InternalEObject)complexSpec).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC, null, msgs);
            if (newComplexSpec != null)
                msgs = ((InternalEObject)newComplexSpec).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC, null, msgs);
            msgs = basicSetComplexSpec(newComplexSpec, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC, newComplexSpec, newComplexSpec));
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
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.FIELD_TYPE__ARRAY, oldArray, array, !oldArrayESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.FIELD_TYPE__ARRAY, oldArray, ARRAY_EDEFAULT, oldArrayESet));
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
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.FIELD_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOptional(boolean newOptional) {
        boolean oldOptional = optional;
        optional = newOptional;
        boolean oldOptionalESet = optionalESet;
        optionalESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.FIELD_TYPE__OPTIONAL, oldOptional, optional, !oldOptionalESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetOptional() {
        boolean oldOptional = optional;
        boolean oldOptionalESet = optionalESet;
        optional = OPTIONAL_EDEFAULT;
        optionalESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.FIELD_TYPE__OPTIONAL, oldOptional, OPTIONAL_EDEFAULT, oldOptionalESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetOptional() {
        return optionalESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TypeType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(TypeType newType) {
        TypeType oldType = type;
        type = newType == null ? TYPE_EDEFAULT : newType;
        boolean oldTypeESet = typeESet;
        typeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.FIELD_TYPE__TYPE, oldType, type, !oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetType() {
        TypeType oldType = type;
        boolean oldTypeESet = typeESet;
        type = TYPE_EDEFAULT;
        typeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.FIELD_TYPE__TYPE, oldType, TYPE_EDEFAULT, oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetType() {
        return typeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC:
                return basicSetSimpleSpec(null, msgs);
            case DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC:
                return basicSetComplexSpec(null, msgs);
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
            case DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC:
                return getSimpleSpec();
            case DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC:
                return getComplexSpec();
            case DatamodelPackage.FIELD_TYPE__ARRAY:
                return isArray();
            case DatamodelPackage.FIELD_TYPE__NAME:
                return getName();
            case DatamodelPackage.FIELD_TYPE__OPTIONAL:
                return isOptional();
            case DatamodelPackage.FIELD_TYPE__TYPE:
                return getType();
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
            case DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC:
                setSimpleSpec((SimpleSpecType)newValue);
                return;
            case DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC:
                setComplexSpec((ComplexSpecType)newValue);
                return;
            case DatamodelPackage.FIELD_TYPE__ARRAY:
                setArray((Boolean)newValue);
                return;
            case DatamodelPackage.FIELD_TYPE__NAME:
                setName((String)newValue);
                return;
            case DatamodelPackage.FIELD_TYPE__OPTIONAL:
                setOptional((Boolean)newValue);
                return;
            case DatamodelPackage.FIELD_TYPE__TYPE:
                setType((TypeType)newValue);
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
            case DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC:
                setSimpleSpec((SimpleSpecType)null);
                return;
            case DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC:
                setComplexSpec((ComplexSpecType)null);
                return;
            case DatamodelPackage.FIELD_TYPE__ARRAY:
                unsetArray();
                return;
            case DatamodelPackage.FIELD_TYPE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case DatamodelPackage.FIELD_TYPE__OPTIONAL:
                unsetOptional();
                return;
            case DatamodelPackage.FIELD_TYPE__TYPE:
                unsetType();
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
            case DatamodelPackage.FIELD_TYPE__SIMPLE_SPEC:
                return simpleSpec != null;
            case DatamodelPackage.FIELD_TYPE__COMPLEX_SPEC:
                return complexSpec != null;
            case DatamodelPackage.FIELD_TYPE__ARRAY:
                return isSetArray();
            case DatamodelPackage.FIELD_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case DatamodelPackage.FIELD_TYPE__OPTIONAL:
                return isSetOptional();
            case DatamodelPackage.FIELD_TYPE__TYPE:
                return isSetType();
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
        result.append(" (array: ");
        if (arrayESet) result.append(array); else result.append("<unset>");
        result.append(", name: ");
        result.append(name);
        result.append(", optional: ");
        if (optionalESet) result.append(optional); else result.append("<unset>");
        result.append(", type: ");
        if (typeESet) result.append(type); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //FieldTypeImpl
