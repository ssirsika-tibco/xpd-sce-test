/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage;
import com.tibco.xpd.presentation.channeltypes.EnumLiteral;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Attribute</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl#getEnumLiterals <em>Enum Literals</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl#getDefaultEnumSet <em>Default Enum Set</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.AttributeImpl#isRequired <em>Required</em>}</li>
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
     * The cached value of the '{@link #getEnumLiterals() <em>Enum Literals</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getEnumLiterals()
     * @generated
     * @ordered
     */
    protected EList<EnumLiteral> enumLiterals;

    /**
     * The cached value of the '{@link #getDefaultEnumSet() <em>Default Enum Set</em>}' reference list.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getDefaultEnumSet()
     * @generated
     * @ordered
     */
    protected EList<EnumLiteral> defaultEnumSet;

    /**
     * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getDefaultValue()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getDefaultValue()
     * @generated
     * @ordered
     */
    protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

    /**
     * This is true if the Default Value attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean defaultValueESet;

    /**
     * The default value of the '{@link #isRequired() <em>Required</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isRequired()
     * @generated
     * @ordered
     */
    protected static final boolean REQUIRED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isRequired() <em>Required</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isRequired()
     * @generated
     * @ordered
     */
    protected boolean required = REQUIRED_EDEFAULT;

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
        return ChannelTypesPackage.Literals.ATTRIBUTE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.ATTRIBUTE__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<EnumLiteral> getEnumLiterals() {
        if (enumLiterals == null) {
            enumLiterals = new EObjectContainmentEList<EnumLiteral>(EnumLiteral.class, this, ChannelTypesPackage.ATTRIBUTE__ENUM_LITERALS);
        }
        return enumLiterals;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<EnumLiteral> getDefaultEnumSet() {
        if (defaultEnumSet == null) {
            defaultEnumSet = new EObjectResolvingEList<EnumLiteral>(EnumLiteral.class, this, ChannelTypesPackage.ATTRIBUTE__DEFAULT_ENUM_SET);
        }
        return defaultEnumSet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDefaultValue(String newDefaultValue) {
        String oldDefaultValue = defaultValue;
        defaultValue = newDefaultValue;
        boolean oldDefaultValueESet = defaultValueESet;
        defaultValueESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.ATTRIBUTE__DEFAULT_VALUE, oldDefaultValue, defaultValue, !oldDefaultValueESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetDefaultValue() {
        String oldDefaultValue = defaultValue;
        boolean oldDefaultValueESet = defaultValueESet;
        defaultValue = DEFAULT_VALUE_EDEFAULT;
        defaultValueESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, ChannelTypesPackage.ATTRIBUTE__DEFAULT_VALUE, oldDefaultValue, DEFAULT_VALUE_EDEFAULT, oldDefaultValueESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDefaultValue() {
        return defaultValueESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRequired(boolean newRequired) {
        boolean oldRequired = required;
        required = newRequired;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.ATTRIBUTE__REQUIRED, oldRequired, required));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getResolvedDefaultValue() {
        if (type == AttributeType.ENUM || type == AttributeType.ENUM_SET) {
            List<EnumLiteral> enums = defaultEnumSet;
            if (enums == null || enums.isEmpty()) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            String DELIMITER = ","; //$NON-NLS-1$
            for (Iterator<EnumLiteral> i = enums.iterator(); i.hasNext();) {
                EnumLiteral enumLiteral = i.next();
                sb.append(enumLiteral.getName());
                if (i.hasNext()) {
                    sb.append(DELIMITER);
                }
            }
            return sb.toString();
        } else {
            return defaultValue;
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ChannelTypesPackage.ATTRIBUTE__ENUM_LITERALS:
                return ((InternalEList<?>)getEnumLiterals()).basicRemove(otherEnd, msgs);
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
            case ChannelTypesPackage.ATTRIBUTE__TYPE:
                return getType();
            case ChannelTypesPackage.ATTRIBUTE__ENUM_LITERALS:
                return getEnumLiterals();
            case ChannelTypesPackage.ATTRIBUTE__DEFAULT_ENUM_SET:
                return getDefaultEnumSet();
            case ChannelTypesPackage.ATTRIBUTE__DEFAULT_VALUE:
                return getDefaultValue();
            case ChannelTypesPackage.ATTRIBUTE__REQUIRED:
                return isRequired();
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
            case ChannelTypesPackage.ATTRIBUTE__TYPE:
                setType((AttributeType)newValue);
                return;
            case ChannelTypesPackage.ATTRIBUTE__ENUM_LITERALS:
                getEnumLiterals().clear();
                getEnumLiterals().addAll((Collection<? extends EnumLiteral>)newValue);
                return;
            case ChannelTypesPackage.ATTRIBUTE__DEFAULT_ENUM_SET:
                getDefaultEnumSet().clear();
                getDefaultEnumSet().addAll((Collection<? extends EnumLiteral>)newValue);
                return;
            case ChannelTypesPackage.ATTRIBUTE__DEFAULT_VALUE:
                setDefaultValue((String)newValue);
                return;
            case ChannelTypesPackage.ATTRIBUTE__REQUIRED:
                setRequired((Boolean)newValue);
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
            case ChannelTypesPackage.ATTRIBUTE__TYPE:
                setType(TYPE_EDEFAULT);
                return;
            case ChannelTypesPackage.ATTRIBUTE__ENUM_LITERALS:
                getEnumLiterals().clear();
                return;
            case ChannelTypesPackage.ATTRIBUTE__DEFAULT_ENUM_SET:
                getDefaultEnumSet().clear();
                return;
            case ChannelTypesPackage.ATTRIBUTE__DEFAULT_VALUE:
                unsetDefaultValue();
                return;
            case ChannelTypesPackage.ATTRIBUTE__REQUIRED:
                setRequired(REQUIRED_EDEFAULT);
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
            case ChannelTypesPackage.ATTRIBUTE__TYPE:
                return type != TYPE_EDEFAULT;
            case ChannelTypesPackage.ATTRIBUTE__ENUM_LITERALS:
                return enumLiterals != null && !enumLiterals.isEmpty();
            case ChannelTypesPackage.ATTRIBUTE__DEFAULT_ENUM_SET:
                return defaultEnumSet != null && !defaultEnumSet.isEmpty();
            case ChannelTypesPackage.ATTRIBUTE__DEFAULT_VALUE:
                return isSetDefaultValue();
            case ChannelTypesPackage.ATTRIBUTE__REQUIRED:
                return required != REQUIRED_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (type: ");
        result.append(type);
        result.append(", defaultValue: ");
        if (defaultValueESet) result.append(defaultValue); else result.append("<unset>");
        result.append(", required: ");
        result.append(required);
        result.append(')');
        return result.toString();
    }

} // AttributeImpl
