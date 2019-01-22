/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.EnumerationType;
import com.tibco.xpd.xpdl2.EnumerationValue;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enumeration Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EnumerationTypeImpl#getEnumerationValue <em>Enumeration Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumerationTypeImpl extends DataTypeImpl implements
        EnumerationType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getEnumerationValue() <em>Enumeration Value</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEnumerationValue()
     * @generated
     * @ordered
     */
    protected EList<EnumerationValue> enumerationValue;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EnumerationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ENUMERATION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<EnumerationValue> getEnumerationValue() {
        if (enumerationValue == null) {
            enumerationValue =
                    new EObjectContainmentEList<EnumerationValue>(
                            EnumerationValue.class, this,
                            Xpdl2Package.ENUMERATION_TYPE__ENUMERATION_VALUE);
        }
        return enumerationValue;
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
        case Xpdl2Package.ENUMERATION_TYPE__ENUMERATION_VALUE:
            return ((InternalEList<?>) getEnumerationValue())
                    .basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.ENUMERATION_TYPE__ENUMERATION_VALUE:
            return getEnumerationValue();
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
        case Xpdl2Package.ENUMERATION_TYPE__ENUMERATION_VALUE:
            getEnumerationValue().clear();
            getEnumerationValue()
                    .addAll((Collection<? extends EnumerationValue>) newValue);
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
        case Xpdl2Package.ENUMERATION_TYPE__ENUMERATION_VALUE:
            getEnumerationValue().clear();
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
        case Xpdl2Package.ENUMERATION_TYPE__ENUMERATION_VALUE:
            return enumerationValue != null && !enumerationValue.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //EnumerationTypeImpl
