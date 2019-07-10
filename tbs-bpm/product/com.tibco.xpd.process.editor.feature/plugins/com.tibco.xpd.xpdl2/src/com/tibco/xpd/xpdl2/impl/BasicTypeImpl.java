/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Basic Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BasicTypeImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BasicTypeImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BasicTypeImpl#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BasicTypeImpl#getPrecision <em>Precision</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BasicTypeImpl#getScale <em>Scale</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.BasicTypeImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BasicTypeImpl extends DataTypeImpl implements BasicType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherAttributes()
     * @generated
     * @ordered
     */
    protected FeatureMap otherAttributes;

    /**
     * The cached value of the '{@link #getLength() <em>Length</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLength()
     * @generated
     * @ordered
     */
    protected Length length;

    /**
     * The cached value of the '{@link #getPrecision() <em>Precision</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrecision()
     * @generated
     * @ordered
     */
    protected Precision precision;

    /**
     * The cached value of the '{@link #getScale() <em>Scale</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScale()
     * @generated
     * @ordered
     */
    protected Scale scale;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final BasicTypeType TYPE_EDEFAULT = BasicTypeType.STRING_LITERAL;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected BasicTypeType type = TYPE_EDEFAULT;

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
    protected BasicTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.BASIC_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.BASIC_TYPE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes = new BasicFeatureMap(this, Xpdl2Package.BASIC_TYPE__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Length getLength() {
        return length;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLength(Length newLength, NotificationChain msgs) {
        Length oldLength = length;
        length = newLength;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.BASIC_TYPE__LENGTH, oldLength, newLength);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLength(Length newLength) {
        if (newLength != length) {
            NotificationChain msgs = null;
            if (length != null)
                msgs = ((InternalEObject) length)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.BASIC_TYPE__LENGTH, null, msgs);
            if (newLength != null)
                msgs = ((InternalEObject) newLength)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.BASIC_TYPE__LENGTH, null, msgs);
            msgs = basicSetLength(newLength, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.BASIC_TYPE__LENGTH, newLength,
                    newLength));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Precision getPrecision() {
        return precision;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPrecision(Precision newPrecision, NotificationChain msgs) {
        Precision oldPrecision = precision;
        precision = newPrecision;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.BASIC_TYPE__PRECISION, oldPrecision, newPrecision);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPrecision(Precision newPrecision) {
        if (newPrecision != precision) {
            NotificationChain msgs = null;
            if (precision != null)
                msgs = ((InternalEObject) precision)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.BASIC_TYPE__PRECISION, null, msgs);
            if (newPrecision != null)
                msgs = ((InternalEObject) newPrecision)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.BASIC_TYPE__PRECISION, null, msgs);
            msgs = basicSetPrecision(newPrecision, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.BASIC_TYPE__PRECISION, newPrecision,
                    newPrecision));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Scale getScale() {
        return scale;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScale(Scale newScale, NotificationChain msgs) {
        Scale oldScale = scale;
        scale = newScale;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.BASIC_TYPE__SCALE, oldScale, newScale);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScale(Scale newScale) {
        if (newScale != scale) {
            NotificationChain msgs = null;
            if (scale != null)
                msgs = ((InternalEObject) scale)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.BASIC_TYPE__SCALE, null, msgs);
            if (newScale != null)
                msgs = ((InternalEObject) newScale)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.BASIC_TYPE__SCALE, null, msgs);
            msgs = basicSetScale(newScale, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.BASIC_TYPE__SCALE, newScale, newScale));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BasicTypeType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(BasicTypeType newType) {
        BasicTypeType oldType = type;
        type = newType == null ? TYPE_EDEFAULT : newType;
        boolean oldTypeESet = typeESet;
        typeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.BASIC_TYPE__TYPE, oldType, type,
                    !oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetType() {
        BasicTypeType oldType = type;
        boolean oldTypeESet = typeESet;
        type = TYPE_EDEFAULT;
        typeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.BASIC_TYPE__TYPE, oldType,
                    TYPE_EDEFAULT, oldTypeESet));
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
    public EObject getOtherElement(String elementName) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.BASIC_TYPE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.BASIC_TYPE__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.BASIC_TYPE__LENGTH:
            return basicSetLength(null, msgs);
        case Xpdl2Package.BASIC_TYPE__PRECISION:
            return basicSetPrecision(null, msgs);
        case Xpdl2Package.BASIC_TYPE__SCALE:
            return basicSetScale(null, msgs);
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
        case Xpdl2Package.BASIC_TYPE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.BASIC_TYPE__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.BASIC_TYPE__LENGTH:
            return getLength();
        case Xpdl2Package.BASIC_TYPE__PRECISION:
            return getPrecision();
        case Xpdl2Package.BASIC_TYPE__SCALE:
            return getScale();
        case Xpdl2Package.BASIC_TYPE__TYPE:
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
        case Xpdl2Package.BASIC_TYPE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.BASIC_TYPE__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.BASIC_TYPE__LENGTH:
            setLength((Length) newValue);
            return;
        case Xpdl2Package.BASIC_TYPE__PRECISION:
            setPrecision((Precision) newValue);
            return;
        case Xpdl2Package.BASIC_TYPE__SCALE:
            setScale((Scale) newValue);
            return;
        case Xpdl2Package.BASIC_TYPE__TYPE:
            setType((BasicTypeType) newValue);
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
        case Xpdl2Package.BASIC_TYPE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.BASIC_TYPE__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.BASIC_TYPE__LENGTH:
            setLength((Length) null);
            return;
        case Xpdl2Package.BASIC_TYPE__PRECISION:
            setPrecision((Precision) null);
            return;
        case Xpdl2Package.BASIC_TYPE__SCALE:
            setScale((Scale) null);
            return;
        case Xpdl2Package.BASIC_TYPE__TYPE:
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
        case Xpdl2Package.BASIC_TYPE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.BASIC_TYPE__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.BASIC_TYPE__LENGTH:
            return length != null;
        case Xpdl2Package.BASIC_TYPE__PRECISION:
            return precision != null;
        case Xpdl2Package.BASIC_TYPE__SCALE:
            return scale != null;
        case Xpdl2Package.BASIC_TYPE__TYPE:
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
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.BASIC_TYPE__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.BASIC_TYPE__OTHER_ATTRIBUTES:
                return Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.BASIC_TYPE__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES:
                return Xpdl2Package.BASIC_TYPE__OTHER_ATTRIBUTES;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", type: "); //$NON-NLS-1$
        if (typeESet)
            result.append(type);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //BasicTypeImpl
