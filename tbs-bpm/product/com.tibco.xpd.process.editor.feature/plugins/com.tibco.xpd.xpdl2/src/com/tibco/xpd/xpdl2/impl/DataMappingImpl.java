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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataMappingImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataMappingImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataMappingImpl#getActual <em>Actual</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataMappingImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataMappingImpl#getFormal <em>Formal</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataMappingImpl#getTestValue <em>Test Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataMappingImpl extends EObjectImpl implements DataMapping {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

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
     * The cached value of the '{@link #getActual() <em>Actual</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActual()
     * @generated
     * @ordered
     */
    protected Expression actual;

    /**
     * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDirection()
     * @generated
     * @ordered
     */
    protected static final DirectionType DIRECTION_EDEFAULT =
            DirectionType.IN_LITERAL;

    /**
     * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDirection()
     * @generated
     * @ordered
     */
    protected DirectionType direction = DIRECTION_EDEFAULT;

    /**
     * This is true if the Direction attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean directionESet;

    /**
     * The default value of the '{@link #getFormal() <em>Formal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormal()
     * @generated
     * @ordered
     */
    protected static final String FORMAL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFormal() <em>Formal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormal()
     * @generated
     * @ordered
     */
    protected String formal = FORMAL_EDEFAULT;

    /**
     * The cached value of the '{@link #getTestValue() <em>Test Value</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTestValue()
     * @generated
     * @ordered
     */
    protected Expression testValue;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DataMappingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.DATA_MAPPING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements =
                    new BasicFeatureMap(this,
                            Xpdl2Package.DATA_MAPPING__OTHER_ELEMENTS);
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
            otherAttributes =
                    new BasicFeatureMap(this,
                            Xpdl2Package.DATA_MAPPING__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getActual() {
        return actual;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetActual(Expression newActual,
            NotificationChain msgs) {
        Expression oldActual = actual;
        actual = newActual;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.DATA_MAPPING__ACTUAL, oldActual,
                            newActual);
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
    public void setActual(Expression newActual) {
        if (newActual != actual) {
            NotificationChain msgs = null;
            if (actual != null)
                msgs =
                        ((InternalEObject) actual).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.DATA_MAPPING__ACTUAL,
                                null,
                                msgs);
            if (newActual != null)
                msgs =
                        ((InternalEObject) newActual).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.DATA_MAPPING__ACTUAL,
                                null,
                                msgs);
            msgs = basicSetActual(newActual, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DATA_MAPPING__ACTUAL, newActual, newActual));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DirectionType getDirection() {
        return direction;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDirection(DirectionType newDirection) {
        DirectionType oldDirection = direction;
        direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
        boolean oldDirectionESet = directionESet;
        directionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DATA_MAPPING__DIRECTION, oldDirection,
                    direction, !oldDirectionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDirection() {
        DirectionType oldDirection = direction;
        boolean oldDirectionESet = directionESet;
        direction = DIRECTION_EDEFAULT;
        directionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.DATA_MAPPING__DIRECTION, oldDirection,
                    DIRECTION_EDEFAULT, oldDirectionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDirection() {
        return directionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFormal() {
        return formal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFormal(String newFormal) {
        String oldFormal = formal;
        formal = newFormal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DATA_MAPPING__FORMAL, oldFormal, formal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getTestValue() {
        return testValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTestValue(Expression newTestValue,
            NotificationChain msgs) {
        Expression oldTestValue = testValue;
        testValue = newTestValue;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.DATA_MAPPING__TEST_VALUE,
                            oldTestValue, newTestValue);
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
    public void setTestValue(Expression newTestValue) {
        if (newTestValue != testValue) {
            NotificationChain msgs = null;
            if (testValue != null)
                msgs =
                        ((InternalEObject) testValue)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.DATA_MAPPING__TEST_VALUE,
                                        null,
                                        msgs);
            if (newTestValue != null)
                msgs =
                        ((InternalEObject) newTestValue)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.DATA_MAPPING__TEST_VALUE,
                                        null,
                                        msgs);
            msgs = basicSetTestValue(newTestValue, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DATA_MAPPING__TEST_VALUE, newTestValue,
                    newTestValue));
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
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.DATA_MAPPING__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.DATA_MAPPING__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.DATA_MAPPING__ACTUAL:
            return basicSetActual(null, msgs);
        case Xpdl2Package.DATA_MAPPING__TEST_VALUE:
            return basicSetTestValue(null, msgs);
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
        case Xpdl2Package.DATA_MAPPING__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.DATA_MAPPING__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.DATA_MAPPING__ACTUAL:
            return getActual();
        case Xpdl2Package.DATA_MAPPING__DIRECTION:
            return getDirection();
        case Xpdl2Package.DATA_MAPPING__FORMAL:
            return getFormal();
        case Xpdl2Package.DATA_MAPPING__TEST_VALUE:
            return getTestValue();
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
        case Xpdl2Package.DATA_MAPPING__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.DATA_MAPPING__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.DATA_MAPPING__ACTUAL:
            setActual((Expression) newValue);
            return;
        case Xpdl2Package.DATA_MAPPING__DIRECTION:
            setDirection((DirectionType) newValue);
            return;
        case Xpdl2Package.DATA_MAPPING__FORMAL:
            setFormal((String) newValue);
            return;
        case Xpdl2Package.DATA_MAPPING__TEST_VALUE:
            setTestValue((Expression) newValue);
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
        case Xpdl2Package.DATA_MAPPING__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.DATA_MAPPING__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.DATA_MAPPING__ACTUAL:
            setActual((Expression) null);
            return;
        case Xpdl2Package.DATA_MAPPING__DIRECTION:
            unsetDirection();
            return;
        case Xpdl2Package.DATA_MAPPING__FORMAL:
            setFormal(FORMAL_EDEFAULT);
            return;
        case Xpdl2Package.DATA_MAPPING__TEST_VALUE:
            setTestValue((Expression) null);
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
        case Xpdl2Package.DATA_MAPPING__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.DATA_MAPPING__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.DATA_MAPPING__ACTUAL:
            return actual != null;
        case Xpdl2Package.DATA_MAPPING__DIRECTION:
            return isSetDirection();
        case Xpdl2Package.DATA_MAPPING__FORMAL:
            return FORMAL_EDEFAULT == null ? formal != null : !FORMAL_EDEFAULT
                    .equals(formal);
        case Xpdl2Package.DATA_MAPPING__TEST_VALUE:
            return testValue != null;
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
        if (baseClass == OtherAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.DATA_MAPPING__OTHER_ATTRIBUTES:
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
        if (baseClass == OtherAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES:
                return Xpdl2Package.DATA_MAPPING__OTHER_ATTRIBUTES;
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", direction: "); //$NON-NLS-1$
        if (directionESet)
            result.append(direction);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", formal: "); //$NON-NLS-1$
        result.append(formal);
        result.append(')');
        return result.toString();
    }

} //DataMappingImpl
