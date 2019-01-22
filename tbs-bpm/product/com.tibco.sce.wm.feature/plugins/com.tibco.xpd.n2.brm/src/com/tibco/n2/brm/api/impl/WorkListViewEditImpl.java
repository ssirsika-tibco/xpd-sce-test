/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkListViewEdit;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

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
 * An implementation of the model object '<em><b>Work List View Edit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewEditImpl#getAuthors <em>Authors</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewEditImpl#getUsers <em>Users</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewEditImpl#getCustomData <em>Custom Data</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkListViewEditImpl extends WorkListViewCommonImpl implements WorkListViewEdit {
    /**
     * The cached value of the '{@link #getAuthors() <em>Authors</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAuthors()
     * @generated
     * @ordered
     */
    protected EList<XmlModelEntityId> authors;

    /**
     * The cached value of the '{@link #getUsers() <em>Users</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUsers()
     * @generated
     * @ordered
     */
    protected EList<XmlModelEntityId> users;

    /**
     * The default value of the '{@link #getCustomData() <em>Custom Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCustomData()
     * @generated
     * @ordered
     */
    protected static final String CUSTOM_DATA_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCustomData() <em>Custom Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCustomData()
     * @generated
     * @ordered
     */
    protected String customData = CUSTOM_DATA_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkListViewEditImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_LIST_VIEW_EDIT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<XmlModelEntityId> getAuthors() {
        if (authors == null) {
            authors = new EObjectContainmentEList<XmlModelEntityId>(XmlModelEntityId.class, this, N2BRMPackage.WORK_LIST_VIEW_EDIT__AUTHORS);
        }
        return authors;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<XmlModelEntityId> getUsers() {
        if (users == null) {
            users = new EObjectContainmentEList<XmlModelEntityId>(XmlModelEntityId.class, this, N2BRMPackage.WORK_LIST_VIEW_EDIT__USERS);
        }
        return users;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCustomData() {
        return customData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCustomData(String newCustomData) {
        String oldCustomData = customData;
        customData = newCustomData;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_EDIT__CUSTOM_DATA, oldCustomData, customData));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__AUTHORS:
                return ((InternalEList<?>)getAuthors()).basicRemove(otherEnd, msgs);
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__USERS:
                return ((InternalEList<?>)getUsers()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__AUTHORS:
                return getAuthors();
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__USERS:
                return getUsers();
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__CUSTOM_DATA:
                return getCustomData();
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
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__AUTHORS:
                getAuthors().clear();
                getAuthors().addAll((Collection<? extends XmlModelEntityId>)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__USERS:
                getUsers().clear();
                getUsers().addAll((Collection<? extends XmlModelEntityId>)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__CUSTOM_DATA:
                setCustomData((String)newValue);
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
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__AUTHORS:
                getAuthors().clear();
                return;
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__USERS:
                getUsers().clear();
                return;
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__CUSTOM_DATA:
                setCustomData(CUSTOM_DATA_EDEFAULT);
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
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__AUTHORS:
                return authors != null && !authors.isEmpty();
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__USERS:
                return users != null && !users.isEmpty();
            case N2BRMPackage.WORK_LIST_VIEW_EDIT__CUSTOM_DATA:
                return CUSTOM_DATA_EDEFAULT == null ? customData != null : !CUSTOM_DATA_EDEFAULT.equals(customData);
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
        result.append(" (customData: ");
        result.append(customData);
        result.append(')');
        return result.toString();
    }

} //WorkListViewEditImpl
