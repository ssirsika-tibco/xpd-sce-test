/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.impl;

import com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextPackage;
import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.RangeValueImpl#getLower <em>Lower</em>}</li>
 *   <li>{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.RangeValueImpl#getUpper <em>Upper</em>}</li>
 *   <li>{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.RangeValueImpl#isLowerInclusive <em>Lower Inclusive</em>}</li>
 *   <li>{@link com.tibco.xpd.bom.modeler.custom.enumlitext.impl.RangeValueImpl#isUpperInclusive <em>Upper Inclusive</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangeValueImpl extends DomainValueImpl implements RangeValue {
    /**
     * The default value of the '{@link #getLower() <em>Lower</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLower()
     * @generated
     * @ordered
     */
    protected static final String LOWER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLower() <em>Lower</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLower()
     * @generated
     * @ordered
     */
    protected String lower = LOWER_EDEFAULT;

    /**
     * The default value of the '{@link #getUpper() <em>Upper</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpper()
     * @generated
     * @ordered
     */
    protected static final String UPPER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUpper() <em>Upper</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpper()
     * @generated
     * @ordered
     */
    protected String upper = UPPER_EDEFAULT;

    /**
     * The default value of the '{@link #isLowerInclusive() <em>Lower Inclusive</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLowerInclusive()
     * @generated
     * @ordered
     */
    protected static final boolean LOWER_INCLUSIVE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isLowerInclusive() <em>Lower Inclusive</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLowerInclusive()
     * @generated
     * @ordered
     */
    protected boolean lowerInclusive = LOWER_INCLUSIVE_EDEFAULT;

    /**
     * The default value of the '{@link #isUpperInclusive() <em>Upper Inclusive</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUpperInclusive()
     * @generated
     * @ordered
     */
    protected static final boolean UPPER_INCLUSIVE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUpperInclusive() <em>Upper Inclusive</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUpperInclusive()
     * @generated
     * @ordered
     */
    protected boolean upperInclusive = UPPER_INCLUSIVE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RangeValueImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return EnumlitextPackage.Literals.RANGE_VALUE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLower() {
        return lower;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLower(String newLower) {
        String oldLower = lower;
        lower = newLower;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EnumlitextPackage.RANGE_VALUE__LOWER, oldLower, lower));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getUpper() {
        return upper;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUpper(String newUpper) {
        String oldUpper = upper;
        upper = newUpper;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EnumlitextPackage.RANGE_VALUE__UPPER, oldUpper, upper));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isLowerInclusive() {
        return lowerInclusive;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLowerInclusive(boolean newLowerInclusive) {
        boolean oldLowerInclusive = lowerInclusive;
        lowerInclusive = newLowerInclusive;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EnumlitextPackage.RANGE_VALUE__LOWER_INCLUSIVE, oldLowerInclusive, lowerInclusive));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUpperInclusive() {
        return upperInclusive;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUpperInclusive(boolean newUpperInclusive) {
        boolean oldUpperInclusive = upperInclusive;
        upperInclusive = newUpperInclusive;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EnumlitextPackage.RANGE_VALUE__UPPER_INCLUSIVE, oldUpperInclusive, upperInclusive));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case EnumlitextPackage.RANGE_VALUE__LOWER:
                return getLower();
            case EnumlitextPackage.RANGE_VALUE__UPPER:
                return getUpper();
            case EnumlitextPackage.RANGE_VALUE__LOWER_INCLUSIVE:
                return isLowerInclusive();
            case EnumlitextPackage.RANGE_VALUE__UPPER_INCLUSIVE:
                return isUpperInclusive();
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
            case EnumlitextPackage.RANGE_VALUE__LOWER:
                setLower((String)newValue);
                return;
            case EnumlitextPackage.RANGE_VALUE__UPPER:
                setUpper((String)newValue);
                return;
            case EnumlitextPackage.RANGE_VALUE__LOWER_INCLUSIVE:
                setLowerInclusive((Boolean)newValue);
                return;
            case EnumlitextPackage.RANGE_VALUE__UPPER_INCLUSIVE:
                setUpperInclusive((Boolean)newValue);
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
            case EnumlitextPackage.RANGE_VALUE__LOWER:
                setLower(LOWER_EDEFAULT);
                return;
            case EnumlitextPackage.RANGE_VALUE__UPPER:
                setUpper(UPPER_EDEFAULT);
                return;
            case EnumlitextPackage.RANGE_VALUE__LOWER_INCLUSIVE:
                setLowerInclusive(LOWER_INCLUSIVE_EDEFAULT);
                return;
            case EnumlitextPackage.RANGE_VALUE__UPPER_INCLUSIVE:
                setUpperInclusive(UPPER_INCLUSIVE_EDEFAULT);
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
            case EnumlitextPackage.RANGE_VALUE__LOWER:
                return LOWER_EDEFAULT == null ? lower != null : !LOWER_EDEFAULT.equals(lower);
            case EnumlitextPackage.RANGE_VALUE__UPPER:
                return UPPER_EDEFAULT == null ? upper != null : !UPPER_EDEFAULT.equals(upper);
            case EnumlitextPackage.RANGE_VALUE__LOWER_INCLUSIVE:
                return lowerInclusive != LOWER_INCLUSIVE_EDEFAULT;
            case EnumlitextPackage.RANGE_VALUE__UPPER_INCLUSIVE:
                return upperInclusive != UPPER_INCLUSIVE_EDEFAULT;
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
        result.append(" (lower: ");
        result.append(lower);
        result.append(", upper: ");
        result.append(upper);
        result.append(", lowerInclusive: ");
        result.append(lowerInclusive);
        result.append(", upperInclusive: ");
        result.append(upperInclusive);
        result.append(')');
        return result.toString();
    }

} //RangeValueImpl
