/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.common.datamodel.AliasType;
import com.tibco.n2.common.datamodel.AliasTypeType;
import com.tibco.n2.common.datamodel.DatamodelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alias Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.AliasTypeImpl#getAliasDescription <em>Alias Description</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.AliasTypeImpl#getAliasName <em>Alias Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.AliasTypeImpl#getAliasType <em>Alias Type</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.AliasTypeImpl#getFacadeName <em>Facade Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AliasTypeImpl extends EObjectImpl implements AliasType {
    /**
     * The default value of the '{@link #getAliasDescription() <em>Alias Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAliasDescription()
     * @generated
     * @ordered
     */
    protected static final String ALIAS_DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAliasDescription() <em>Alias Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAliasDescription()
     * @generated
     * @ordered
     */
    protected String aliasDescription = ALIAS_DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getAliasName() <em>Alias Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAliasName()
     * @generated
     * @ordered
     */
    protected static final String ALIAS_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAliasName() <em>Alias Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAliasName()
     * @generated
     * @ordered
     */
    protected String aliasName = ALIAS_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getAliasType() <em>Alias Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAliasType()
     * @generated
     * @ordered
     */
    protected static final AliasTypeType ALIAS_TYPE_EDEFAULT = AliasTypeType.STRING;

    /**
     * The cached value of the '{@link #getAliasType() <em>Alias Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAliasType()
     * @generated
     * @ordered
     */
    protected AliasTypeType aliasType = ALIAS_TYPE_EDEFAULT;

    /**
     * This is true if the Alias Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean aliasTypeESet;

    /**
     * The default value of the '{@link #getFacadeName() <em>Facade Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFacadeName()
     * @generated
     * @ordered
     */
    protected static final String FACADE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFacadeName() <em>Facade Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFacadeName()
     * @generated
     * @ordered
     */
    protected String facadeName = FACADE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AliasTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatamodelPackage.Literals.ALIAS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAliasDescription() {
        return aliasDescription;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAliasDescription(String newAliasDescription) {
        String oldAliasDescription = aliasDescription;
        aliasDescription = newAliasDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ALIAS_TYPE__ALIAS_DESCRIPTION, oldAliasDescription, aliasDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAliasName(String newAliasName) {
        String oldAliasName = aliasName;
        aliasName = newAliasName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ALIAS_TYPE__ALIAS_NAME, oldAliasName, aliasName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AliasTypeType getAliasType() {
        return aliasType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAliasType(AliasTypeType newAliasType) {
        AliasTypeType oldAliasType = aliasType;
        aliasType = newAliasType == null ? ALIAS_TYPE_EDEFAULT : newAliasType;
        boolean oldAliasTypeESet = aliasTypeESet;
        aliasTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ALIAS_TYPE__ALIAS_TYPE, oldAliasType, aliasType, !oldAliasTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAliasType() {
        AliasTypeType oldAliasType = aliasType;
        boolean oldAliasTypeESet = aliasTypeESet;
        aliasType = ALIAS_TYPE_EDEFAULT;
        aliasTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.ALIAS_TYPE__ALIAS_TYPE, oldAliasType, ALIAS_TYPE_EDEFAULT, oldAliasTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAliasType() {
        return aliasTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFacadeName() {
        return facadeName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFacadeName(String newFacadeName) {
        String oldFacadeName = facadeName;
        facadeName = newFacadeName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.ALIAS_TYPE__FACADE_NAME, oldFacadeName, facadeName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DatamodelPackage.ALIAS_TYPE__ALIAS_DESCRIPTION:
                return getAliasDescription();
            case DatamodelPackage.ALIAS_TYPE__ALIAS_NAME:
                return getAliasName();
            case DatamodelPackage.ALIAS_TYPE__ALIAS_TYPE:
                return getAliasType();
            case DatamodelPackage.ALIAS_TYPE__FACADE_NAME:
                return getFacadeName();
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
            case DatamodelPackage.ALIAS_TYPE__ALIAS_DESCRIPTION:
                setAliasDescription((String)newValue);
                return;
            case DatamodelPackage.ALIAS_TYPE__ALIAS_NAME:
                setAliasName((String)newValue);
                return;
            case DatamodelPackage.ALIAS_TYPE__ALIAS_TYPE:
                setAliasType((AliasTypeType)newValue);
                return;
            case DatamodelPackage.ALIAS_TYPE__FACADE_NAME:
                setFacadeName((String)newValue);
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
            case DatamodelPackage.ALIAS_TYPE__ALIAS_DESCRIPTION:
                setAliasDescription(ALIAS_DESCRIPTION_EDEFAULT);
                return;
            case DatamodelPackage.ALIAS_TYPE__ALIAS_NAME:
                setAliasName(ALIAS_NAME_EDEFAULT);
                return;
            case DatamodelPackage.ALIAS_TYPE__ALIAS_TYPE:
                unsetAliasType();
                return;
            case DatamodelPackage.ALIAS_TYPE__FACADE_NAME:
                setFacadeName(FACADE_NAME_EDEFAULT);
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
            case DatamodelPackage.ALIAS_TYPE__ALIAS_DESCRIPTION:
                return ALIAS_DESCRIPTION_EDEFAULT == null ? aliasDescription != null : !ALIAS_DESCRIPTION_EDEFAULT.equals(aliasDescription);
            case DatamodelPackage.ALIAS_TYPE__ALIAS_NAME:
                return ALIAS_NAME_EDEFAULT == null ? aliasName != null : !ALIAS_NAME_EDEFAULT.equals(aliasName);
            case DatamodelPackage.ALIAS_TYPE__ALIAS_TYPE:
                return isSetAliasType();
            case DatamodelPackage.ALIAS_TYPE__FACADE_NAME:
                return FACADE_NAME_EDEFAULT == null ? facadeName != null : !FACADE_NAME_EDEFAULT.equals(facadeName);
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
        result.append(" (aliasDescription: ");
        result.append(aliasDescription);
        result.append(", aliasName: ");
        result.append(aliasName);
        result.append(", aliasType: ");
        if (aliasTypeESet) result.append(aliasType); else result.append("<unset>");
        result.append(", facadeName: ");
        result.append(facadeName);
        result.append(')');
        return result.toString();
    }

} //AliasTypeImpl
