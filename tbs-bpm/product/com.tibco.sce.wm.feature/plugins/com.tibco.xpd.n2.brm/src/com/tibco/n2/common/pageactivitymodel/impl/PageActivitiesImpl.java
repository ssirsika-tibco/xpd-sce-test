/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.pageactivitymodel.impl;

import com.tibco.n2.common.pageactivitymodel.PageActivities;
import com.tibco.n2.common.pageactivitymodel.PageActivity;
import com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page Activities</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivitiesImpl#getPageActivity <em>Page Activity</em>}</li>
 *   <li>{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivitiesImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PageActivitiesImpl extends EObjectImpl implements PageActivities {
    /**
     * The cached value of the '{@link #getPageActivity() <em>Page Activity</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPageActivity()
     * @generated
     * @ordered
     */
    protected EList<PageActivity> pageActivity;

    /**
     * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected static final String VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected String version = VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PageActivitiesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PageactivitymodelPackage.Literals.PAGE_ACTIVITIES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PageActivity> getPageActivity() {
        if (pageActivity == null) {
            pageActivity = new EObjectContainmentEList<PageActivity>(PageActivity.class, this, PageactivitymodelPackage.PAGE_ACTIVITIES__PAGE_ACTIVITY);
        }
        return pageActivity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(String newVersion) {
        String oldVersion = version;
        version = newVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PageactivitymodelPackage.PAGE_ACTIVITIES__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PageactivitymodelPackage.PAGE_ACTIVITIES__PAGE_ACTIVITY:
                return ((InternalEList<?>)getPageActivity()).basicRemove(otherEnd, msgs);
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
            case PageactivitymodelPackage.PAGE_ACTIVITIES__PAGE_ACTIVITY:
                return getPageActivity();
            case PageactivitymodelPackage.PAGE_ACTIVITIES__VERSION:
                return getVersion();
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
            case PageactivitymodelPackage.PAGE_ACTIVITIES__PAGE_ACTIVITY:
                getPageActivity().clear();
                getPageActivity().addAll((Collection<? extends PageActivity>)newValue);
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITIES__VERSION:
                setVersion((String)newValue);
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
            case PageactivitymodelPackage.PAGE_ACTIVITIES__PAGE_ACTIVITY:
                getPageActivity().clear();
                return;
            case PageactivitymodelPackage.PAGE_ACTIVITIES__VERSION:
                setVersion(VERSION_EDEFAULT);
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
            case PageactivitymodelPackage.PAGE_ACTIVITIES__PAGE_ACTIVITY:
                return pageActivity != null && !pageActivity.isEmpty();
            case PageactivitymodelPackage.PAGE_ACTIVITIES__VERSION:
                return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
        result.append(" (version: ");
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //PageActivitiesImpl
