/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.impl;

import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;

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
 * An implementation of the model object '<em><b>Work Item Attributes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.worklistfacade.impl.WorkItemAttributesImpl#getWorkItemAttribute <em>Work Item Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemAttributesImpl extends EObjectImpl implements
        WorkItemAttributes {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getWorkItemAttribute() <em>Work Item Attribute</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkItemAttribute()
     * @generated
     * @ordered
     */
    protected EList<WorkItemAttribute> workItemAttribute;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkItemAttributesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WorkListFacadePackage.Literals.WORK_ITEM_ATTRIBUTES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkItemAttribute> getWorkItemAttribute() {
        if (workItemAttribute == null) {
            workItemAttribute =
                    new EObjectContainmentEList<WorkItemAttribute>(
                            WorkItemAttribute.class,
                            this,
                            WorkListFacadePackage.WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE);
        }
        return workItemAttribute;
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
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE:
            return ((InternalEList<?>) getWorkItemAttribute())
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
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE:
            return getWorkItemAttribute();
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
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE:
            getWorkItemAttribute().clear();
            getWorkItemAttribute()
                    .addAll((Collection<? extends WorkItemAttribute>) newValue);
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
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE:
            getWorkItemAttribute().clear();
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
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE:
            return workItemAttribute != null && !workItemAttribute.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //WorkItemAttributesImpl
