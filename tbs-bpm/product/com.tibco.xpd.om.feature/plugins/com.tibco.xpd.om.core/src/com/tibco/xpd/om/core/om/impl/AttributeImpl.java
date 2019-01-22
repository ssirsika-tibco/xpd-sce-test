/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeImpl#getValues <em>Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeImpl#getDefaultEnumSetValues <em>Default Enum Set Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.AttributeImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeImpl extends NamedElementImpl implements Attribute {
    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final AttributeType TYPE_EDEFAULT = AttributeType.TEXT;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected AttributeType type = TYPE_EDEFAULT;

    /**
     * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getValues()
     * @generated
     * @ordered
     */
    protected EList<EnumValue> values;

    /**
     * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultValue()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultValue()
     * @generated
     * @ordered
     */
    protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

    /**
     * This is true if the Default Value attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean defaultValueESet;

    /**
     * The cached value of the '{@link #getDefaultEnumSetValues() <em>Default Enum Set Values</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultEnumSetValues()
     * @generated
     * @ordered
     */
    protected EList<EnumValue> defaultEnumSetValues;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected AttributeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ATTRIBUTE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AttributeType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setType(AttributeType newType) {
        AttributeType oldType = type;
        type = newType == null ? TYPE_EDEFAULT : newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ATTRIBUTE__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<EnumValue> getValues() {
        if (values == null) {
            values =
                    new EObjectContainmentEList<EnumValue>(EnumValue.class,
                            this, OMPackage.ATTRIBUTE__VALUES);
        }
        return values;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<EnumValue> getDefaultEnumSetValues() {
        if (defaultEnumSetValues == null) {
            defaultEnumSetValues =
                    new EObjectResolvingEList.Unsettable<EnumValue>(
                            EnumValue.class, this,
                            OMPackage.ATTRIBUTE__DEFAULT_ENUM_SET_VALUES);
        }
        return defaultEnumSetValues;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDefaultEnumSetValues() {
        if (defaultEnumSetValues != null)
            ((InternalEList.Unsettable<?>) defaultEnumSetValues).unset();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDefaultEnumSetValues() {
        return defaultEnumSetValues != null
                && ((InternalEList.Unsettable<?>) defaultEnumSetValues).isSet();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ATTRIBUTE__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDefaultValue(String newDefaultValue) {
        String oldDefaultValue = defaultValue;
        defaultValue = newDefaultValue;
        boolean oldDefaultValueESet = defaultValueESet;
        defaultValueESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ATTRIBUTE__DEFAULT_VALUE, oldDefaultValue,
                    defaultValue, !oldDefaultValueESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDefaultValue() {
        String oldDefaultValue = defaultValue;
        boolean oldDefaultValueESet = defaultValueESet;
        defaultValue = DEFAULT_VALUE_EDEFAULT;
        defaultValueESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    OMPackage.ATTRIBUTE__DEFAULT_VALUE, oldDefaultValue,
                    DEFAULT_VALUE_EDEFAULT, oldDefaultValueESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDefaultValue() {
        return defaultValueESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.ATTRIBUTE__VALUES:
            return ((InternalEList<?>) getValues()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.ATTRIBUTE__TYPE:
            return getType();
        case OMPackage.ATTRIBUTE__VALUES:
            return getValues();
        case OMPackage.ATTRIBUTE__DEFAULT_VALUE:
            return getDefaultValue();
        case OMPackage.ATTRIBUTE__DEFAULT_ENUM_SET_VALUES:
            return getDefaultEnumSetValues();
        case OMPackage.ATTRIBUTE__DESCRIPTION:
            return getDescription();
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
        case OMPackage.ATTRIBUTE__TYPE:
            setType((AttributeType) newValue);
            return;
        case OMPackage.ATTRIBUTE__VALUES:
            getValues().clear();
            getValues().addAll((Collection<? extends EnumValue>) newValue);
            return;
        case OMPackage.ATTRIBUTE__DEFAULT_VALUE:
            setDefaultValue((String) newValue);
            return;
        case OMPackage.ATTRIBUTE__DEFAULT_ENUM_SET_VALUES:
            getDefaultEnumSetValues().clear();
            getDefaultEnumSetValues()
                    .addAll((Collection<? extends EnumValue>) newValue);
            return;
        case OMPackage.ATTRIBUTE__DESCRIPTION:
            setDescription((String) newValue);
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
        case OMPackage.ATTRIBUTE__TYPE:
            setType(TYPE_EDEFAULT);
            return;
        case OMPackage.ATTRIBUTE__VALUES:
            getValues().clear();
            return;
        case OMPackage.ATTRIBUTE__DEFAULT_VALUE:
            unsetDefaultValue();
            return;
        case OMPackage.ATTRIBUTE__DEFAULT_ENUM_SET_VALUES:
            unsetDefaultEnumSetValues();
            return;
        case OMPackage.ATTRIBUTE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
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
        case OMPackage.ATTRIBUTE__TYPE:
            return type != TYPE_EDEFAULT;
        case OMPackage.ATTRIBUTE__VALUES:
            return values != null && !values.isEmpty();
        case OMPackage.ATTRIBUTE__DEFAULT_VALUE:
            return isSetDefaultValue();
        case OMPackage.ATTRIBUTE__DEFAULT_ENUM_SET_VALUES:
            return isSetDefaultEnumSetValues();
        case OMPackage.ATTRIBUTE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
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
        result.append(" (type: "); //$NON-NLS-1$
        result.append(type);
        result.append(", defaultValue: "); //$NON-NLS-1$
        if (defaultValueESet)
            result.append(defaultValue);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", description: "); //$NON-NLS-1$
        result.append(description);
        result.append(')');
        return result.toString();
    }

} // AttributeImpl
