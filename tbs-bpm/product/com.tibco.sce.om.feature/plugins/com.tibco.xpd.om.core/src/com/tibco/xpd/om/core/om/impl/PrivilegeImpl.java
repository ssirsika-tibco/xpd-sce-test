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
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeCategory;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Privilege</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PrivilegeImpl#getCategory <em>Category</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrivilegeImpl extends QualifiedOrgElementImpl implements Privilege {
    /**
     * The cached value of the '{@link #getCategory() <em>Category</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCategory()
     * @generated
     * @ordered
     */
    protected PrivilegeCategory category;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PrivilegeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.PRIVILEGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PrivilegeCategory getCategory() {
        if (category != null && category.eIsProxy()) {
            InternalEObject oldCategory = (InternalEObject) category;
            category = (PrivilegeCategory) eResolveProxy(oldCategory);
            if (category != oldCategory) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.PRIVILEGE__CATEGORY, oldCategory,
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
    public PrivilegeCategory basicGetCategory() {
        return category;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCategory(PrivilegeCategory newCategory,
            NotificationChain msgs) {
        PrivilegeCategory oldCategory = category;
        category = newCategory;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            OMPackage.PRIVILEGE__CATEGORY, oldCategory,
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
    public void setCategory(PrivilegeCategory newCategory) {
        if (newCategory != category) {
            NotificationChain msgs = null;
            if (category != null)
                msgs =
                        ((InternalEObject) category).eInverseRemove(this,
                                OMPackage.PRIVILEGE_CATEGORY__MEMBERS,
                                PrivilegeCategory.class,
                                msgs);
            if (newCategory != null)
                msgs =
                        ((InternalEObject) newCategory).eInverseAdd(this,
                                OMPackage.PRIVILEGE_CATEGORY__MEMBERS,
                                PrivilegeCategory.class,
                                msgs);
            msgs = basicSetCategory(newCategory, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.PRIVILEGE__CATEGORY, newCategory, newCategory));
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
        case OMPackage.PRIVILEGE__CATEGORY:
            if (category != null)
                msgs =
                        ((InternalEObject) category).eInverseRemove(this,
                                OMPackage.PRIVILEGE_CATEGORY__MEMBERS,
                                PrivilegeCategory.class,
                                msgs);
            return basicSetCategory((PrivilegeCategory) otherEnd, msgs);
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
        case OMPackage.PRIVILEGE__CATEGORY:
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
        case OMPackage.PRIVILEGE__CATEGORY:
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
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case OMPackage.PRIVILEGE__CATEGORY:
            setCategory((PrivilegeCategory) newValue);
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
        case OMPackage.PRIVILEGE__CATEGORY:
            setCategory((PrivilegeCategory) null);
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
        case OMPackage.PRIVILEGE__CATEGORY:
            return category != null;
        }
        return super.eIsSet(featureID);
    }

} //PrivilegeImpl
