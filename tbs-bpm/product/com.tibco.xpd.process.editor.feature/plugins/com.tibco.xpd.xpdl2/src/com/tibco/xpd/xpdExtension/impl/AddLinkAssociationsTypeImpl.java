/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.AddLinkAssociationsType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Add Link Associations Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AddLinkAssociationsTypeImpl#getAddCaseRefField <em>Add Case Ref Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AddLinkAssociationsTypeImpl#getAssociationName <em>Association Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AddLinkAssociationsTypeImpl extends EObjectImpl
        implements AddLinkAssociationsType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getAddCaseRefField() <em>Add Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAddCaseRefField()
     * @generated
     * @ordered
     */
    protected static final String ADD_CASE_REF_FIELD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAddCaseRefField() <em>Add Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAddCaseRefField()
     * @generated
     * @ordered
     */
    protected String addCaseRefField = ADD_CASE_REF_FIELD_EDEFAULT;

    /**
     * The default value of the '{@link #getAssociationName() <em>Association Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAssociationName()
     * @generated
     * @ordered
     */
    protected static final String ASSOCIATION_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAssociationName() <em>Association Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAssociationName()
     * @generated
     * @ordered
     */
    protected String associationName = ASSOCIATION_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AddLinkAssociationsTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.ADD_LINK_ASSOCIATIONS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAddCaseRefField() {
        return addCaseRefField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAddCaseRefField(String newAddCaseRefField) {
        String oldAddCaseRefField = addCaseRefField;
        addCaseRefField = newAddCaseRefField;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ADD_CASE_REF_FIELD,
                    oldAddCaseRefField, addCaseRefField));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAssociationName() {
        return associationName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAssociationName(String newAssociationName) {
        String oldAssociationName = associationName;
        associationName = newAssociationName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME,
                    oldAssociationName, associationName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ADD_CASE_REF_FIELD:
            return getAddCaseRefField();
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME:
            return getAssociationName();
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
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ADD_CASE_REF_FIELD:
            setAddCaseRefField((String) newValue);
            return;
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME:
            setAssociationName((String) newValue);
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
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ADD_CASE_REF_FIELD:
            setAddCaseRefField(ADD_CASE_REF_FIELD_EDEFAULT);
            return;
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME:
            setAssociationName(ASSOCIATION_NAME_EDEFAULT);
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
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ADD_CASE_REF_FIELD:
            return ADD_CASE_REF_FIELD_EDEFAULT == null ? addCaseRefField != null
                    : !ADD_CASE_REF_FIELD_EDEFAULT.equals(addCaseRefField);
        case XpdExtensionPackage.ADD_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME:
            return ASSOCIATION_NAME_EDEFAULT == null ? associationName != null
                    : !ASSOCIATION_NAME_EDEFAULT.equals(associationName);
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
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (addCaseRefField: "); //$NON-NLS-1$
        result.append(addCaseRefField);
        result.append(", associationName: "); //$NON-NLS-1$
        result.append(associationName);
        result.append(')');
        return result.toString();
    }

} //AddLinkAssociationsTypeImpl
