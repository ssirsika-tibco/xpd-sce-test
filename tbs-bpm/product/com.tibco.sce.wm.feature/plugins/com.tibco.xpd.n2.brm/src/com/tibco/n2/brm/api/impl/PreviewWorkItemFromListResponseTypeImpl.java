/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.PreviewWorkItemFromListResponseType;
import com.tibco.n2.brm.api.WorkItemPreview;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Preview Work Item From List Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListResponseTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.PreviewWorkItemFromListResponseTypeImpl#getWorkItemPreview <em>Work Item Preview</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PreviewWorkItemFromListResponseTypeImpl extends EObjectImpl implements PreviewWorkItemFromListResponseType {
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
    protected PreviewWorkItemFromListResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        if (group == null) {
            group = new BasicFeatureMap(this, N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP);
        }
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkItemPreview> getWorkItemPreview() {
        return getGroup().list(N2BRMPackage.Literals.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW:
                return ((InternalEList<?>)getWorkItemPreview()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP:
                if (coreType) return getGroup();
                return ((FeatureMap.Internal)getGroup()).getWrapper();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW:
                return getWorkItemPreview();
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
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP:
                ((FeatureMap.Internal)getGroup()).set(newValue);
                return;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW:
                getWorkItemPreview().clear();
                getWorkItemPreview().addAll((Collection<? extends WorkItemPreview>)newValue);
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
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP:
                getGroup().clear();
                return;
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW:
                getWorkItemPreview().clear();
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
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__GROUP:
                return group != null && !group.isEmpty();
            case N2BRMPackage.PREVIEW_WORK_ITEM_FROM_LIST_RESPONSE_TYPE__WORK_ITEM_PREVIEW:
                return !getWorkItemPreview().isEmpty();
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

} //PreviewWorkItemFromListResponseTypeImpl
