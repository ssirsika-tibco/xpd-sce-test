/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.EditWorkListViewResponseType;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edit Work List View Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.EditWorkListViewResponseTypeImpl#getWorkListViewID <em>Work List View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EditWorkListViewResponseTypeImpl extends EObjectImpl implements EditWorkListViewResponseType {
    /**
     * The default value of the '{@link #getWorkListViewID() <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkListViewID()
     * @generated
     * @ordered
     */
    protected static final long WORK_LIST_VIEW_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getWorkListViewID() <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkListViewID()
     * @generated
     * @ordered
     */
    protected long workListViewID = WORK_LIST_VIEW_ID_EDEFAULT;

    /**
     * This is true if the Work List View ID attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean workListViewIDESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EditWorkListViewResponseTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getWorkListViewID() {
        return workListViewID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkListViewID(long newWorkListViewID) {
        long oldWorkListViewID = workListViewID;
        workListViewID = newWorkListViewID;
        boolean oldWorkListViewIDESet = workListViewIDESet;
        workListViewIDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID, oldWorkListViewID, workListViewID, !oldWorkListViewIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetWorkListViewID() {
        long oldWorkListViewID = workListViewID;
        boolean oldWorkListViewIDESet = workListViewIDESet;
        workListViewID = WORK_LIST_VIEW_ID_EDEFAULT;
        workListViewIDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID, oldWorkListViewID, WORK_LIST_VIEW_ID_EDEFAULT, oldWorkListViewIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetWorkListViewID() {
        return workListViewIDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID:
                return getWorkListViewID();
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
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID:
                setWorkListViewID((Long)newValue);
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
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID:
                unsetWorkListViewID();
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
            case N2BRMPackage.EDIT_WORK_LIST_VIEW_RESPONSE_TYPE__WORK_LIST_VIEW_ID:
                return isSetWorkListViewID();
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
        result.append(" (workListViewID: ");
        if (workListViewIDESet) result.append(workListViewID); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //EditWorkListViewResponseTypeImpl
