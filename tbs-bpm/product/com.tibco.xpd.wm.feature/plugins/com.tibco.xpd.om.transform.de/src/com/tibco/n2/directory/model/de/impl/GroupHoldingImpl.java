/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.Group;
import com.tibco.n2.directory.model.de.GroupHolding;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group Holding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupHoldingImpl#getGroup <em>Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GroupHoldingImpl extends EObjectImpl implements GroupHolding {
    /**
     * The cached value of the '{@link #getGroup() <em>Group</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected Group group;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GroupHoldingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.GROUP_HOLDING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Group getGroup() {
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGroup(Group newGroup) {
        Group oldGroup = group;
        group = newGroup;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.GROUP_HOLDING__GROUP, oldGroup, group));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DePackage.GROUP_HOLDING__GROUP:
                return getGroup();
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
            case DePackage.GROUP_HOLDING__GROUP:
                setGroup((Group)newValue);
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
            case DePackage.GROUP_HOLDING__GROUP:
                setGroup((Group)null);
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
            case DePackage.GROUP_HOLDING__GROUP:
                return group != null;
        }
        return super.eIsSet(featureID);
    }

} //GroupHoldingImpl
