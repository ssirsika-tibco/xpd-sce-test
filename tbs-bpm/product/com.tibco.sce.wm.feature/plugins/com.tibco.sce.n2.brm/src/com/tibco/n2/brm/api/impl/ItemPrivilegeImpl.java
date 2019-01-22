/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ItemPrivilege;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.Privilege;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item Privilege</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl#getSuspend <em>Suspend</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl#getStatelessRealloc <em>Stateless Realloc</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl#getStatefulRealloc <em>Stateful Realloc</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl#getDellocate <em>Dellocate</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl#getDelegate <em>Delegate</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl#getSkip <em>Skip</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemPrivilegeImpl#getPiling <em>Piling</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ItemPrivilegeImpl extends EObjectImpl implements ItemPrivilege {
    /**
     * The cached value of the '{@link #getSuspend() <em>Suspend</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSuspend()
     * @generated
     * @ordered
     */
    protected EList<Privilege> suspend;

    /**
     * The cached value of the '{@link #getStatelessRealloc() <em>Stateless Realloc</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStatelessRealloc()
     * @generated
     * @ordered
     */
    protected EList<Privilege> statelessRealloc;

    /**
     * The cached value of the '{@link #getStatefulRealloc() <em>Stateful Realloc</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStatefulRealloc()
     * @generated
     * @ordered
     */
    protected EList<Privilege> statefulRealloc;

    /**
     * The cached value of the '{@link #getDellocate() <em>Dellocate</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDellocate()
     * @generated
     * @ordered
     */
    protected EList<Privilege> dellocate;

    /**
     * The cached value of the '{@link #getDelegate() <em>Delegate</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDelegate()
     * @generated
     * @ordered
     */
    protected EList<Privilege> delegate;

    /**
     * The cached value of the '{@link #getSkip() <em>Skip</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSkip()
     * @generated
     * @ordered
     */
    protected EList<Privilege> skip;

    /**
     * The cached value of the '{@link #getPiling() <em>Piling</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPiling()
     * @generated
     * @ordered
     */
    protected EList<Privilege> piling;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ItemPrivilegeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ITEM_PRIVILEGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getSuspend() {
        if (suspend == null) {
            suspend = new EObjectContainmentEList<Privilege>(Privilege.class, this, N2BRMPackage.ITEM_PRIVILEGE__SUSPEND);
        }
        return suspend;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getStatelessRealloc() {
        if (statelessRealloc == null) {
            statelessRealloc = new EObjectContainmentEList<Privilege>(Privilege.class, this, N2BRMPackage.ITEM_PRIVILEGE__STATELESS_REALLOC);
        }
        return statelessRealloc;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getStatefulRealloc() {
        if (statefulRealloc == null) {
            statefulRealloc = new EObjectContainmentEList<Privilege>(Privilege.class, this, N2BRMPackage.ITEM_PRIVILEGE__STATEFUL_REALLOC);
        }
        return statefulRealloc;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getDellocate() {
        if (dellocate == null) {
            dellocate = new EObjectContainmentEList<Privilege>(Privilege.class, this, N2BRMPackage.ITEM_PRIVILEGE__DELLOCATE);
        }
        return dellocate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getDelegate() {
        if (delegate == null) {
            delegate = new EObjectContainmentEList<Privilege>(Privilege.class, this, N2BRMPackage.ITEM_PRIVILEGE__DELEGATE);
        }
        return delegate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getSkip() {
        if (skip == null) {
            skip = new EObjectContainmentEList<Privilege>(Privilege.class, this, N2BRMPackage.ITEM_PRIVILEGE__SKIP);
        }
        return skip;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getPiling() {
        if (piling == null) {
            piling = new EObjectContainmentEList<Privilege>(Privilege.class, this, N2BRMPackage.ITEM_PRIVILEGE__PILING);
        }
        return piling;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ITEM_PRIVILEGE__SUSPEND:
                return ((InternalEList<?>)getSuspend()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.ITEM_PRIVILEGE__STATELESS_REALLOC:
                return ((InternalEList<?>)getStatelessRealloc()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.ITEM_PRIVILEGE__STATEFUL_REALLOC:
                return ((InternalEList<?>)getStatefulRealloc()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.ITEM_PRIVILEGE__DELLOCATE:
                return ((InternalEList<?>)getDellocate()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.ITEM_PRIVILEGE__DELEGATE:
                return ((InternalEList<?>)getDelegate()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.ITEM_PRIVILEGE__SKIP:
                return ((InternalEList<?>)getSkip()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.ITEM_PRIVILEGE__PILING:
                return ((InternalEList<?>)getPiling()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.ITEM_PRIVILEGE__SUSPEND:
                return getSuspend();
            case N2BRMPackage.ITEM_PRIVILEGE__STATELESS_REALLOC:
                return getStatelessRealloc();
            case N2BRMPackage.ITEM_PRIVILEGE__STATEFUL_REALLOC:
                return getStatefulRealloc();
            case N2BRMPackage.ITEM_PRIVILEGE__DELLOCATE:
                return getDellocate();
            case N2BRMPackage.ITEM_PRIVILEGE__DELEGATE:
                return getDelegate();
            case N2BRMPackage.ITEM_PRIVILEGE__SKIP:
                return getSkip();
            case N2BRMPackage.ITEM_PRIVILEGE__PILING:
                return getPiling();
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
            case N2BRMPackage.ITEM_PRIVILEGE__SUSPEND:
                getSuspend().clear();
                getSuspend().addAll((Collection<? extends Privilege>)newValue);
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__STATELESS_REALLOC:
                getStatelessRealloc().clear();
                getStatelessRealloc().addAll((Collection<? extends Privilege>)newValue);
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__STATEFUL_REALLOC:
                getStatefulRealloc().clear();
                getStatefulRealloc().addAll((Collection<? extends Privilege>)newValue);
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__DELLOCATE:
                getDellocate().clear();
                getDellocate().addAll((Collection<? extends Privilege>)newValue);
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__DELEGATE:
                getDelegate().clear();
                getDelegate().addAll((Collection<? extends Privilege>)newValue);
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__SKIP:
                getSkip().clear();
                getSkip().addAll((Collection<? extends Privilege>)newValue);
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__PILING:
                getPiling().clear();
                getPiling().addAll((Collection<? extends Privilege>)newValue);
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
            case N2BRMPackage.ITEM_PRIVILEGE__SUSPEND:
                getSuspend().clear();
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__STATELESS_REALLOC:
                getStatelessRealloc().clear();
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__STATEFUL_REALLOC:
                getStatefulRealloc().clear();
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__DELLOCATE:
                getDellocate().clear();
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__DELEGATE:
                getDelegate().clear();
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__SKIP:
                getSkip().clear();
                return;
            case N2BRMPackage.ITEM_PRIVILEGE__PILING:
                getPiling().clear();
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
            case N2BRMPackage.ITEM_PRIVILEGE__SUSPEND:
                return suspend != null && !suspend.isEmpty();
            case N2BRMPackage.ITEM_PRIVILEGE__STATELESS_REALLOC:
                return statelessRealloc != null && !statelessRealloc.isEmpty();
            case N2BRMPackage.ITEM_PRIVILEGE__STATEFUL_REALLOC:
                return statefulRealloc != null && !statefulRealloc.isEmpty();
            case N2BRMPackage.ITEM_PRIVILEGE__DELLOCATE:
                return dellocate != null && !dellocate.isEmpty();
            case N2BRMPackage.ITEM_PRIVILEGE__DELEGATE:
                return delegate != null && !delegate.isEmpty();
            case N2BRMPackage.ITEM_PRIVILEGE__SKIP:
                return skip != null && !skip.isEmpty();
            case N2BRMPackage.ITEM_PRIVILEGE__PILING:
                return piling != null && !piling.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ItemPrivilegeImpl
