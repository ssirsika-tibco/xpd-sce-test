/**
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.Capability;
import com.tibco.n2.directory.model.de.CapabilityCategory;
import com.tibco.n2.directory.model.de.DePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Capability Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.CapabilityCategoryImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.CapabilityCategoryImpl#getCapabilityCategory <em>Capability Category</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.CapabilityCategoryImpl#getCapability <em>Capability</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CapabilityCategoryImpl extends NamedEntityImpl implements CapabilityCategory {
    /**
     * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected FeatureMap group;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CapabilityCategoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.CAPABILITY_CATEGORY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        if (group == null) {
            group = new BasicFeatureMap(this, DePackage.CAPABILITY_CATEGORY__GROUP);
        }
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityCategory> getCapabilityCategory() {
        return getGroup().list(DePackage.Literals.CAPABILITY_CATEGORY__CAPABILITY_CATEGORY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Capability> getCapability() {
        return getGroup().list(DePackage.Literals.CAPABILITY_CATEGORY__CAPABILITY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.CAPABILITY_CATEGORY__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY_CATEGORY:
                return ((InternalEList<?>)getCapabilityCategory()).basicRemove(otherEnd, msgs);
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY:
                return ((InternalEList<?>)getCapability()).basicRemove(otherEnd, msgs);
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
            case DePackage.CAPABILITY_CATEGORY__GROUP:
                if (coreType) return getGroup();
                return ((FeatureMap.Internal)getGroup()).getWrapper();
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY_CATEGORY:
                return getCapabilityCategory();
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY:
                return getCapability();
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
            case DePackage.CAPABILITY_CATEGORY__GROUP:
                ((FeatureMap.Internal)getGroup()).set(newValue);
                return;
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY_CATEGORY:
                getCapabilityCategory().clear();
                getCapabilityCategory().addAll((Collection<? extends CapabilityCategory>)newValue);
                return;
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY:
                getCapability().clear();
                getCapability().addAll((Collection<? extends Capability>)newValue);
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
            case DePackage.CAPABILITY_CATEGORY__GROUP:
                getGroup().clear();
                return;
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY_CATEGORY:
                getCapabilityCategory().clear();
                return;
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY:
                getCapability().clear();
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
            case DePackage.CAPABILITY_CATEGORY__GROUP:
                return group != null && !group.isEmpty();
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY_CATEGORY:
                return !getCapabilityCategory().isEmpty();
            case DePackage.CAPABILITY_CATEGORY__CAPABILITY:
                return !getCapability().isEmpty();
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
        result.append(" (group: ");
        result.append(group);
        result.append(')');
        return result.toString();
    }

} //CapabilityCategoryImpl
