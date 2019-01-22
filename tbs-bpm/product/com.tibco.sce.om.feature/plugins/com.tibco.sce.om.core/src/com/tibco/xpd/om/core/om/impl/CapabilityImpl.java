/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Capability</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.CapabilityImpl#getCategory <em>Category</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CapabilityImpl extends QualifiedOrgElementImpl implements
        Capability {
    /**
     * The cached value of the '{@link #getCategory() <em>Category</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCategory()
     * @generated
     * @ordered
     */
    protected CapabilityCategory category;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected CapabilityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.CAPABILITY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CapabilityCategory getCategory() {
        if (category != null && category.eIsProxy()) {
            InternalEObject oldCategory = (InternalEObject) category;
            category = (CapabilityCategory) eResolveProxy(oldCategory);
            if (category != oldCategory) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.CAPABILITY__CATEGORY, oldCategory,
                            category));
            }
        }
        return category;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CapabilityCategory basicGetCategory() {
        return category;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCategory(CapabilityCategory newCategory,
            NotificationChain msgs) {
        CapabilityCategory oldCategory = category;
        category = newCategory;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            OMPackage.CAPABILITY__CATEGORY, oldCategory,
                            newCategory);
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
    public void setCategory(CapabilityCategory newCategory) {
        if (newCategory != category) {
            NotificationChain msgs = null;
            if (category != null)
                msgs =
                        ((InternalEObject) category).eInverseRemove(this,
                                OMPackage.CAPABILITY_CATEGORY__MEMBERS,
                                CapabilityCategory.class,
                                msgs);
            if (newCategory != null)
                msgs =
                        ((InternalEObject) newCategory).eInverseAdd(this,
                                OMPackage.CAPABILITY_CATEGORY__MEMBERS,
                                CapabilityCategory.class,
                                msgs);
            msgs = basicSetCategory(newCategory, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.CAPABILITY__CATEGORY, newCategory, newCategory));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.CAPABILITY__CATEGORY:
            if (category != null)
                msgs =
                        ((InternalEObject) category).eInverseRemove(this,
                                OMPackage.CAPABILITY_CATEGORY__MEMBERS,
                                CapabilityCategory.class,
                                msgs);
            return basicSetCategory((CapabilityCategory) otherEnd, msgs);
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
        case OMPackage.CAPABILITY__CATEGORY:
            return basicSetCategory(null, msgs);
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
        case OMPackage.CAPABILITY__CATEGORY:
            if (resolve)
                return getCategory();
            return basicGetCategory();
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
        case OMPackage.CAPABILITY__CATEGORY:
            setCategory((CapabilityCategory) newValue);
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
        case OMPackage.CAPABILITY__CATEGORY:
            setCategory((CapabilityCategory) null);
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
        case OMPackage.CAPABILITY__CATEGORY:
            return category != null;
        }
        return super.eIsSet(featureID);
    }

} // CapabilityImpl
