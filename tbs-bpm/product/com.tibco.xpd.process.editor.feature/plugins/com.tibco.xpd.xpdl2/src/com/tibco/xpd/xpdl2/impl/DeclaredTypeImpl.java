/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Declared Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DeclaredTypeImpl#getTypeDeclarationId <em>Type Declaration Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DeclaredTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeclaredTypeImpl extends DataTypeImpl implements DeclaredType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getTypeDeclarationId() <em>Type Declaration Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypeDeclarationId()
     * @generated
     * @ordered
     */
    protected static final String TYPE_DECLARATION_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTypeDeclarationId() <em>Type Declaration Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypeDeclarationId()
     * @generated
     * @ordered
     */
    protected String typeDeclarationId = TYPE_DECLARATION_ID_EDEFAULT;

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
    protected DeclaredTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.DECLARED_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTypeDeclarationId() {
        return typeDeclarationId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTypeDeclarationId(String newTypeDeclarationId) {
        String oldTypeDeclarationId = typeDeclarationId;
        typeDeclarationId = newTypeDeclarationId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DECLARED_TYPE__TYPE_DECLARATION_ID,
                    oldTypeDeclarationId, typeDeclarationId));
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
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DECLARED_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.DECLARED_TYPE__TYPE_DECLARATION_ID:
            return getTypeDeclarationId();
        case Xpdl2Package.DECLARED_TYPE__NAME:
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
        case Xpdl2Package.DECLARED_TYPE__TYPE_DECLARATION_ID:
            setTypeDeclarationId((String) newValue);
            return;
        case Xpdl2Package.DECLARED_TYPE__NAME:
            setName((String) newValue);
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
        case Xpdl2Package.DECLARED_TYPE__TYPE_DECLARATION_ID:
            setTypeDeclarationId(TYPE_DECLARATION_ID_EDEFAULT);
            return;
        case Xpdl2Package.DECLARED_TYPE__NAME:
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
        case Xpdl2Package.DECLARED_TYPE__TYPE_DECLARATION_ID:
            return TYPE_DECLARATION_ID_EDEFAULT == null ? typeDeclarationId != null
                    : !TYPE_DECLARATION_ID_EDEFAULT.equals(typeDeclarationId);
        case Xpdl2Package.DECLARED_TYPE__NAME:
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
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (typeDeclarationId: "); //$NON-NLS-1$
        result.append(typeDeclarationId);
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //DeclaredTypeImpl
