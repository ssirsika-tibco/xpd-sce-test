/**
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.Privilege;
import com.tibco.n2.directory.model.de.PrivilegeCategory;

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
 * An implementation of the model object '<em><b>Privilege Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PrivilegeCategoryImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PrivilegeCategoryImpl#getPrivilegeCategory <em>Privilege Category</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PrivilegeCategoryImpl#getPrivilege <em>Privilege</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrivilegeCategoryImpl extends NamedEntityImpl implements PrivilegeCategory {
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
    protected PrivilegeCategoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.PRIVILEGE_CATEGORY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        if (group == null) {
            group = new BasicFeatureMap(this, DePackage.PRIVILEGE_CATEGORY__GROUP);
        }
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PrivilegeCategory> getPrivilegeCategory() {
        return getGroup().list(DePackage.Literals.PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getPrivilege() {
        return getGroup().list(DePackage.Literals.PRIVILEGE_CATEGORY__PRIVILEGE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.PRIVILEGE_CATEGORY__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY:
                return ((InternalEList<?>)getPrivilegeCategory()).basicRemove(otherEnd, msgs);
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE:
                return ((InternalEList<?>)getPrivilege()).basicRemove(otherEnd, msgs);
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
            case DePackage.PRIVILEGE_CATEGORY__GROUP:
                if (coreType) return getGroup();
                return ((FeatureMap.Internal)getGroup()).getWrapper();
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY:
                return getPrivilegeCategory();
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE:
                return getPrivilege();
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
            case DePackage.PRIVILEGE_CATEGORY__GROUP:
                ((FeatureMap.Internal)getGroup()).set(newValue);
                return;
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY:
                getPrivilegeCategory().clear();
                getPrivilegeCategory().addAll((Collection<? extends PrivilegeCategory>)newValue);
                return;
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE:
                getPrivilege().clear();
                getPrivilege().addAll((Collection<? extends Privilege>)newValue);
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
            case DePackage.PRIVILEGE_CATEGORY__GROUP:
                getGroup().clear();
                return;
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY:
                getPrivilegeCategory().clear();
                return;
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE:
                getPrivilege().clear();
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
            case DePackage.PRIVILEGE_CATEGORY__GROUP:
                return group != null && !group.isEmpty();
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY:
                return !getPrivilegeCategory().isEmpty();
            case DePackage.PRIVILEGE_CATEGORY__PRIVILEGE:
                return !getPrivilege().isEmpty();
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

} //PrivilegeCategoryImpl
