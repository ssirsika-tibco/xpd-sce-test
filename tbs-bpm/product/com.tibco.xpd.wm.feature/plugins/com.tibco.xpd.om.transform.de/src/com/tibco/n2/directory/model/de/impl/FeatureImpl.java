/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.EntityType;
import com.tibco.n2.directory.model.de.Feature;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.FeatureImpl#getDefinition <em>Definition</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.FeatureImpl#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.FeatureImpl#getUpperBound <em>Upper Bound</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureImpl extends NamedEntityImpl implements Feature {
    /**
     * The cached value of the '{@link #getDefinition() <em>Definition</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefinition()
     * @generated
     * @ordered
     */
    protected EntityType definition;

    /**
     * The default value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLowerBound()
     * @generated
     * @ordered
     */
    protected static final int LOWER_BOUND_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLowerBound()
     * @generated
     * @ordered
     */
    protected int lowerBound = LOWER_BOUND_EDEFAULT;

    /**
     * This is true if the Lower Bound attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean lowerBoundESet;

    /**
     * The default value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpperBound()
     * @generated
     * @ordered
     */
    protected static final int UPPER_BOUND_EDEFAULT = 1;

    /**
     * The cached value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpperBound()
     * @generated
     * @ordered
     */
    protected int upperBound = UPPER_BOUND_EDEFAULT;

    /**
     * This is true if the Upper Bound attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean upperBoundESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FeatureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.FEATURE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EntityType getDefinition() {
        return definition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDefinition(EntityType newDefinition) {
        EntityType oldDefinition = definition;
        definition = newDefinition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.FEATURE__DEFINITION, oldDefinition, definition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLowerBound(int newLowerBound) {
        int oldLowerBound = lowerBound;
        lowerBound = newLowerBound;
        boolean oldLowerBoundESet = lowerBoundESet;
        lowerBoundESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.FEATURE__LOWER_BOUND, oldLowerBound, lowerBound, !oldLowerBoundESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetLowerBound() {
        int oldLowerBound = lowerBound;
        boolean oldLowerBoundESet = lowerBoundESet;
        lowerBound = LOWER_BOUND_EDEFAULT;
        lowerBoundESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.FEATURE__LOWER_BOUND, oldLowerBound, LOWER_BOUND_EDEFAULT, oldLowerBoundESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetLowerBound() {
        return lowerBoundESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getUpperBound() {
        return upperBound;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUpperBound(int newUpperBound) {
        int oldUpperBound = upperBound;
        upperBound = newUpperBound;
        boolean oldUpperBoundESet = upperBoundESet;
        upperBoundESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.FEATURE__UPPER_BOUND, oldUpperBound, upperBound, !oldUpperBoundESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetUpperBound() {
        int oldUpperBound = upperBound;
        boolean oldUpperBoundESet = upperBoundESet;
        upperBound = UPPER_BOUND_EDEFAULT;
        upperBoundESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.FEATURE__UPPER_BOUND, oldUpperBound, UPPER_BOUND_EDEFAULT, oldUpperBoundESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetUpperBound() {
        return upperBoundESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DePackage.FEATURE__DEFINITION:
                return getDefinition();
            case DePackage.FEATURE__LOWER_BOUND:
                return getLowerBound();
            case DePackage.FEATURE__UPPER_BOUND:
                return getUpperBound();
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
            case DePackage.FEATURE__DEFINITION:
                setDefinition((EntityType)newValue);
                return;
            case DePackage.FEATURE__LOWER_BOUND:
                setLowerBound((Integer)newValue);
                return;
            case DePackage.FEATURE__UPPER_BOUND:
                setUpperBound((Integer)newValue);
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
            case DePackage.FEATURE__DEFINITION:
                setDefinition((EntityType)null);
                return;
            case DePackage.FEATURE__LOWER_BOUND:
                unsetLowerBound();
                return;
            case DePackage.FEATURE__UPPER_BOUND:
                unsetUpperBound();
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
            case DePackage.FEATURE__DEFINITION:
                return definition != null;
            case DePackage.FEATURE__LOWER_BOUND:
                return isSetLowerBound();
            case DePackage.FEATURE__UPPER_BOUND:
                return isSetUpperBound();
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
        result.append(" (lowerBound: ");
        if (lowerBoundESet) result.append(lowerBound); else result.append("<unset>");
        result.append(", upperBound: ");
        if (upperBoundESet) result.append(upperBound); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //FeatureImpl
