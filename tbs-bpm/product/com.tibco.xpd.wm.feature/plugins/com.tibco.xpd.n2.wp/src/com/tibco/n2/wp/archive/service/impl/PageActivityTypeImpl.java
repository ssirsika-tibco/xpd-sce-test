/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.wp.archive.service.FormType;
import com.tibco.n2.wp.archive.service.PageActivityType;
import com.tibco.n2.wp.archive.service.WPPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page Activity Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageActivityTypeImpl#getPageReference <em>Page Reference</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageActivityTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.impl.PageActivityTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PageActivityTypeImpl extends EObjectImpl implements PageActivityType {
    /**
     * The cached value of the '{@link #getPageReference() <em>Page Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPageReference()
     * @generated
     * @ordered
     */
    protected FormType pageReference;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PageActivityTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return WPPackage.Literals.PAGE_ACTIVITY_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FormType getPageReference() {
        return pageReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPageReference(FormType newPageReference, NotificationChain msgs) {
        FormType oldPageReference = pageReference;
        pageReference = newPageReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE, oldPageReference, newPageReference);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPageReference(FormType newPageReference) {
        if (newPageReference != pageReference) {
            NotificationChain msgs = null;
            if (pageReference != null)
                msgs = ((InternalEObject)pageReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE, null, msgs);
            if (newPageReference != null)
                msgs = ((InternalEObject)newPageReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE, null, msgs);
            msgs = basicSetPageReference(newPageReference, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE, newPageReference, newPageReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_ACTIVITY_TYPE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, WPPackage.PAGE_ACTIVITY_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE:
                return basicSetPageReference(null, msgs);
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
            case WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE:
                return getPageReference();
            case WPPackage.PAGE_ACTIVITY_TYPE__ID:
                return getId();
            case WPPackage.PAGE_ACTIVITY_TYPE__NAME:
                return getName();
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
            case WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE:
                setPageReference((FormType)newValue);
                return;
            case WPPackage.PAGE_ACTIVITY_TYPE__ID:
                setId((String)newValue);
                return;
            case WPPackage.PAGE_ACTIVITY_TYPE__NAME:
                setName((String)newValue);
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
            case WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE:
                setPageReference((FormType)null);
                return;
            case WPPackage.PAGE_ACTIVITY_TYPE__ID:
                setId(ID_EDEFAULT);
                return;
            case WPPackage.PAGE_ACTIVITY_TYPE__NAME:
                setName(NAME_EDEFAULT);
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
            case WPPackage.PAGE_ACTIVITY_TYPE__PAGE_REFERENCE:
                return pageReference != null;
            case WPPackage.PAGE_ACTIVITY_TYPE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case WPPackage.PAGE_ACTIVITY_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
        result.append(" (id: ");
        result.append(id);
        result.append(", name: ");
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //PageActivityTypeImpl
