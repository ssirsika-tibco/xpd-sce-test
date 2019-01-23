/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItemPreview;

import com.tibco.n2.common.datamodel.FieldType;
import com.tibco.n2.common.datamodel.WorkTypeSpec;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item Preview</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemPreviewImpl#getFieldPreview <em>Field Preview</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemPreviewImpl#getWorkType <em>Work Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemPreviewImpl extends ManagedObjectIDImpl implements WorkItemPreview {
    /**
     * The cached value of the '{@link #getFieldPreview() <em>Field Preview</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFieldPreview()
     * @generated
     * @ordered
     */
    protected EList<FieldType> fieldPreview;

    /**
     * The cached value of the '{@link #getWorkType() <em>Work Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkType()
     * @generated
     * @ordered
     */
    protected WorkTypeSpec workType;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkItemPreviewImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_ITEM_PREVIEW;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FieldType> getFieldPreview() {
        if (fieldPreview == null) {
            fieldPreview = new EObjectContainmentEList<FieldType>(FieldType.class, this, N2BRMPackage.WORK_ITEM_PREVIEW__FIELD_PREVIEW);
        }
        return fieldPreview;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeSpec getWorkType() {
        return workType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkType(WorkTypeSpec newWorkType, NotificationChain msgs) {
        WorkTypeSpec oldWorkType = workType;
        workType = newWorkType;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE, oldWorkType, newWorkType);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkType(WorkTypeSpec newWorkType) {
        if (newWorkType != workType) {
            NotificationChain msgs = null;
            if (workType != null)
                msgs = ((InternalEObject)workType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE, null, msgs);
            if (newWorkType != null)
                msgs = ((InternalEObject)newWorkType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE, null, msgs);
            msgs = basicSetWorkType(newWorkType, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE, newWorkType, newWorkType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_ITEM_PREVIEW__FIELD_PREVIEW:
                return ((InternalEList<?>)getFieldPreview()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE:
                return basicSetWorkType(null, msgs);
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
            case N2BRMPackage.WORK_ITEM_PREVIEW__FIELD_PREVIEW:
                return getFieldPreview();
            case N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE:
                return getWorkType();
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
            case N2BRMPackage.WORK_ITEM_PREVIEW__FIELD_PREVIEW:
                getFieldPreview().clear();
                getFieldPreview().addAll((Collection<? extends FieldType>)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE:
                setWorkType((WorkTypeSpec)newValue);
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
            case N2BRMPackage.WORK_ITEM_PREVIEW__FIELD_PREVIEW:
                getFieldPreview().clear();
                return;
            case N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE:
                setWorkType((WorkTypeSpec)null);
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
            case N2BRMPackage.WORK_ITEM_PREVIEW__FIELD_PREVIEW:
                return fieldPreview != null && !fieldPreview.isEmpty();
            case N2BRMPackage.WORK_ITEM_PREVIEW__WORK_TYPE:
                return workType != null;
        }
        return super.eIsSet(featureID);
    }

} //WorkItemPreviewImpl
