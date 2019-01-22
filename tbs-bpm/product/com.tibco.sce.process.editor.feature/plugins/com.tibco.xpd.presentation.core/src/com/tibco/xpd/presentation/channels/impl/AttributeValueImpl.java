/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.xpd.presentation.channels.AttributeValue;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.channeltypes.EnumLiteral;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Attribute Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.AttributeValueImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.AttributeValueImpl#getEnumValues <em>Enum Values</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.AttributeValueImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.impl.AttributeValueImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeValueImpl extends EObjectImpl implements AttributeValue {
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
     * The cached value of the '{@link #getEnumValues() <em>Enum Values</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEnumValues()
     * @generated
     * @ordered
     */
    protected EList<EnumLiteral> enumValues;

    /**
     * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAttribute()
     * @generated
     * @ordered
     */
    protected Attribute attribute;

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
        return ChannelsPackage.Literals.ATTRIBUTE_VALUE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelsPackage.ATTRIBUTE_VALUE__VALUE, oldValue, value));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<EnumLiteral> getEnumValues() {
        if (enumValues == null) {
            enumValues = new EObjectResolvingEList<EnumLiteral>(EnumLiteral.class, this, ChannelsPackage.ATTRIBUTE_VALUE__ENUM_VALUES);
        }
        return enumValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Attribute getAttribute() {
        if (attribute != null && attribute.eIsProxy()) {
            InternalEObject oldAttribute = (InternalEObject)attribute;
            attribute = (Attribute)eResolveProxy(oldAttribute);
            if (attribute != oldAttribute) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelsPackage.ATTRIBUTE_VALUE__ATTRIBUTE, oldAttribute, attribute));
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
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelsPackage.ATTRIBUTE_VALUE__ATTRIBUTE, oldAttribute, attribute));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public AttributeType getType() {
        if (attribute != null) {
            return attribute.getType();
        }
        return null;
    }

    /**
     * Returns value of the attribute. If value is ENUM_SET then it will return
     * String with comma separated names of enumerations.
     * 
     * @param useDefault
     *            it <code>true</code> then default value from attribute will be
     *            returned if it's not set for this object.
     * 
     * @generated NOT
     */
    public String getResolvedValue(boolean useDefault) {
        if (attribute != null) {
            AttributeType type = getType();
            if (type == AttributeType.ENUM || type == AttributeType.ENUM_SET) {
                List<EnumLiteral> enums = enumValues;
                if (enumValues == null || enumValues.isEmpty()) {
                    enums = attribute.getDefaultEnumSet();
                }
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
                if (value == null) {
                    return attribute.getDefaultValue();
                }
                return value;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getAttributeName() {
        if (attribute != null) {
            return attribute.getName();
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
            case ChannelsPackage.ATTRIBUTE_VALUE__VALUE:
                return getValue();
            case ChannelsPackage.ATTRIBUTE_VALUE__ENUM_VALUES:
                return getEnumValues();
            case ChannelsPackage.ATTRIBUTE_VALUE__ATTRIBUTE:
                if (resolve) return getAttribute();
                return basicGetAttribute();
            case ChannelsPackage.ATTRIBUTE_VALUE__TYPE:
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
            case ChannelsPackage.ATTRIBUTE_VALUE__VALUE:
                setValue((String)newValue);
                return;
            case ChannelsPackage.ATTRIBUTE_VALUE__ENUM_VALUES:
                getEnumValues().clear();
                getEnumValues().addAll((Collection<? extends EnumLiteral>)newValue);
                return;
            case ChannelsPackage.ATTRIBUTE_VALUE__ATTRIBUTE:
                setAttribute((Attribute)newValue);
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
            case ChannelsPackage.ATTRIBUTE_VALUE__VALUE:
                setValue(VALUE_EDEFAULT);
                return;
            case ChannelsPackage.ATTRIBUTE_VALUE__ENUM_VALUES:
                getEnumValues().clear();
                return;
            case ChannelsPackage.ATTRIBUTE_VALUE__ATTRIBUTE:
                setAttribute((Attribute)null);
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
            case ChannelsPackage.ATTRIBUTE_VALUE__VALUE:
                return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
            case ChannelsPackage.ATTRIBUTE_VALUE__ENUM_VALUES:
                return enumValues != null && !enumValues.isEmpty();
            case ChannelsPackage.ATTRIBUTE_VALUE__ATTRIBUTE:
                return attribute != null;
            case ChannelsPackage.ATTRIBUTE_VALUE__TYPE:
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (value: ");
        result.append(value);
        result.append(')');
        return result.toString();
    }

} // AttributeValueImpl
