/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Capability Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.CapabilityCategoryImpl#getSubCategories <em>Sub Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.CapabilityCategoryImpl#getMembers <em>Members</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CapabilityCategoryImpl extends NamedElementImpl implements
        CapabilityCategory {
    /**
     * The cached value of the '{@link #getSubCategories() <em>Sub Categories</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSubCategories()
     * @generated
     * @ordered
     */
    protected EList<CapabilityCategory> subCategories;

    /**
     * The cached value of the '{@link #getMembers() <em>Members</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMembers()
     * @generated
     * @ordered
     */
    protected EList<Capability> members;

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
        return OMPackage.Literals.CAPABILITY_CATEGORY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityCategory> getSubCategories() {
        if (subCategories == null) {
            subCategories =
                    new EObjectContainmentEList<CapabilityCategory>(
                            CapabilityCategory.class, this,
                            OMPackage.CAPABILITY_CATEGORY__SUB_CATEGORIES);
        }
        return subCategories;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Capability> getMembers() {
        if (members == null) {
            members =
                    new EObjectWithInverseResolvingEList<Capability>(
                            Capability.class, this,
                            OMPackage.CAPABILITY_CATEGORY__MEMBERS,
                            OMPackage.CAPABILITY__CATEGORY);
        }
        return members;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.CAPABILITY_CATEGORY__MEMBERS:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getMembers())
                    .basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
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
        case OMPackage.CAPABILITY_CATEGORY__SUB_CATEGORIES:
            return ((InternalEList<?>) getSubCategories())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.CAPABILITY_CATEGORY__MEMBERS:
            return ((InternalEList<?>) getMembers())
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
        case OMPackage.CAPABILITY_CATEGORY__SUB_CATEGORIES:
            return getSubCategories();
        case OMPackage.CAPABILITY_CATEGORY__MEMBERS:
            return getMembers();
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
        case OMPackage.CAPABILITY_CATEGORY__SUB_CATEGORIES:
            getSubCategories().clear();
            getSubCategories()
                    .addAll((Collection<? extends CapabilityCategory>) newValue);
            return;
        case OMPackage.CAPABILITY_CATEGORY__MEMBERS:
            getMembers().clear();
            getMembers().addAll((Collection<? extends Capability>) newValue);
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
        case OMPackage.CAPABILITY_CATEGORY__SUB_CATEGORIES:
            getSubCategories().clear();
            return;
        case OMPackage.CAPABILITY_CATEGORY__MEMBERS:
            getMembers().clear();
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
        case OMPackage.CAPABILITY_CATEGORY__SUB_CATEGORIES:
            return subCategories != null && !subCategories.isEmpty();
        case OMPackage.CAPABILITY_CATEGORY__MEMBERS:
            return members != null && !members.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //CapabilityCategoryImpl
