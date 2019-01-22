/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeValueImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeValueImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeValueImpl#getEnumSetValues <em>Enum Set Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeValueImpl#getSetValues <em>Set Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeValueImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeValueImpl extends EObjectImpl implements AttributeValue {
    /**
     * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAttribute()
     * @generated
     * @ordered
     */
    protected Attribute attribute;

    /**
     * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected String value = VALUE_EDEFAULT;

    /**
     * The cached value of the '{@link #getEnumSetValues() <em>Enum Set Values</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEnumSetValues()
     * @generated
     * @ordered
     */
    protected EList<EnumValue> enumSetValues;

    /**
     * The cached value of the '{@link #getSetValues() <em>Set Values</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSetValues()
     * @generated
     * @ordered
     */
    protected EList<String> setValues;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final AttributeType TYPE_EDEFAULT = AttributeType.TEXT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected AttributeValueImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ATTRIBUTE_VALUE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Attribute getAttribute() {
        if (attribute != null && attribute.eIsProxy()) {
            InternalEObject oldAttribute = (InternalEObject) attribute;
            attribute = (Attribute) eResolveProxy(oldAttribute);
            if (attribute != oldAttribute) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ATTRIBUTE_VALUE__ATTRIBUTE, oldAttribute,
                            attribute));
            }
        }
        return attribute;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Attribute basicGetAttribute() {
        return attribute;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAttribute(Attribute newAttribute) {
        Attribute oldAttribute = attribute;
        attribute = newAttribute;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ATTRIBUTE_VALUE__ATTRIBUTE, oldAttribute,
                    attribute));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setValue(String newValue) {
        String oldValue = value;
        value = newValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ATTRIBUTE_VALUE__VALUE, oldValue, value));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<EnumValue> getEnumSetValues() {
        if (enumSetValues == null) {
            enumSetValues =
                    new EObjectResolvingEList<EnumValue>(EnumValue.class, this,
                            OMPackage.ATTRIBUTE_VALUE__ENUM_SET_VALUES);
        }
        return enumSetValues;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getSetValues() {
        if (setValues == null) {
            setValues =
                    new EDataTypeUniqueEList<String>(String.class, this,
                            OMPackage.ATTRIBUTE_VALUE__SET_VALUES);
        }
        return setValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public AttributeType getType() {
        Attribute attribute = getAttribute();
        if (attribute != null) {
            return attribute.getType();
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.ATTRIBUTE_VALUE__ATTRIBUTE:
            if (resolve)
                return getAttribute();
            return basicGetAttribute();
        case OMPackage.ATTRIBUTE_VALUE__VALUE:
            return getValue();
        case OMPackage.ATTRIBUTE_VALUE__ENUM_SET_VALUES:
            return getEnumSetValues();
        case OMPackage.ATTRIBUTE_VALUE__SET_VALUES:
            return getSetValues();
        case OMPackage.ATTRIBUTE_VALUE__TYPE:
            return getType();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case OMPackage.ATTRIBUTE_VALUE__ATTRIBUTE:
            setAttribute((Attribute) newValue);
            return;
        case OMPackage.ATTRIBUTE_VALUE__VALUE:
            setValue((String) newValue);
            return;
        case OMPackage.ATTRIBUTE_VALUE__ENUM_SET_VALUES:
            getEnumSetValues().clear();
            getEnumSetValues()
                    .addAll((Collection<? extends EnumValue>) newValue);
            return;
        case OMPackage.ATTRIBUTE_VALUE__SET_VALUES:
            getSetValues().clear();
            getSetValues().addAll((Collection<? extends String>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case OMPackage.ATTRIBUTE_VALUE__ATTRIBUTE:
            setAttribute((Attribute) null);
            return;
        case OMPackage.ATTRIBUTE_VALUE__VALUE:
            setValue(VALUE_EDEFAULT);
            return;
        case OMPackage.ATTRIBUTE_VALUE__ENUM_SET_VALUES:
            getEnumSetValues().clear();
            return;
        case OMPackage.ATTRIBUTE_VALUE__SET_VALUES:
            getSetValues().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case OMPackage.ATTRIBUTE_VALUE__ATTRIBUTE:
            return attribute != null;
        case OMPackage.ATTRIBUTE_VALUE__VALUE:
            return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT
                    .equals(value);
        case OMPackage.ATTRIBUTE_VALUE__ENUM_SET_VALUES:
            return enumSetValues != null && !enumSetValues.isEmpty();
        case OMPackage.ATTRIBUTE_VALUE__SET_VALUES:
            return setValues != null && !setValues.isEmpty();
        case OMPackage.ATTRIBUTE_VALUE__TYPE:
            return getType() != TYPE_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (value: "); //$NON-NLS-1$
        result.append(value);
        result.append(", setValues: "); //$NON-NLS-1$
        result.append(setValues);
        result.append(')');
        return result.toString();
    }

} // AttributeValueImpl
