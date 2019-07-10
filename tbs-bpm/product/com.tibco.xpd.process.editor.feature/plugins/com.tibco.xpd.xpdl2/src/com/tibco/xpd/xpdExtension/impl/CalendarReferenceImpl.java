/**
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Calendar Reference</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CalendarReferenceImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CalendarReferenceImpl#getDataFieldId <em>Data Field Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CalendarReferenceImpl extends EObjectImpl implements CalendarReference {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAlias()
     * @generated
     * @ordered
     */
    protected static final String ALIAS_EDEFAULT = null; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAlias()
     * @generated
     * @ordered
     */
    protected String alias = ALIAS_EDEFAULT;

    /**
     * This is true if the Alias attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean aliasESet;

    /**
     * The default value of the '{@link #getDataFieldId() <em>Data Field Id</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getDataFieldId()
     * @generated
     * @ordered
     */
    protected static final String DATA_FIELD_ID_EDEFAULT = null; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDataFieldId() <em>Data Field Id</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDataFieldId()
     * @generated
     * @ordered
     */
    protected String dataFieldId = DATA_FIELD_ID_EDEFAULT;

    /**
     * This is true if the Data Field Id attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean dataFieldIdESet;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected CalendarReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CALENDAR_REFERENCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getAlias() {
        return alias;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setAlias(String newAlias) {
        String oldAlias = alias;
        alias = newAlias;
        boolean oldAliasESet = aliasESet;
        aliasESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CALENDAR_REFERENCE__ALIAS,
                    oldAlias, alias, !oldAliasESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetAlias() {
        String oldAlias = alias;
        boolean oldAliasESet = aliasESet;
        alias = ALIAS_EDEFAULT;
        aliasESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.CALENDAR_REFERENCE__ALIAS,
                    oldAlias, ALIAS_EDEFAULT, oldAliasESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetAlias() {
        return aliasESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getDataFieldId() {
        return dataFieldId;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setDataFieldId(String newDataFieldId) {
        String oldDataFieldId = dataFieldId;
        dataFieldId = newDataFieldId;
        boolean oldDataFieldIdESet = dataFieldIdESet;
        dataFieldIdESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CALENDAR_REFERENCE__DATA_FIELD_ID,
                    oldDataFieldId, dataFieldId, !oldDataFieldIdESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetDataFieldId() {
        String oldDataFieldId = dataFieldId;
        boolean oldDataFieldIdESet = dataFieldIdESet;
        dataFieldId = DATA_FIELD_ID_EDEFAULT;
        dataFieldIdESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.CALENDAR_REFERENCE__DATA_FIELD_ID, oldDataFieldId, DATA_FIELD_ID_EDEFAULT,
                    oldDataFieldIdESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetDataFieldId() {
        return dataFieldIdESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.CALENDAR_REFERENCE__ALIAS:
            return getAlias();
        case XpdExtensionPackage.CALENDAR_REFERENCE__DATA_FIELD_ID:
            return getDataFieldId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.CALENDAR_REFERENCE__ALIAS:
            setAlias((String) newValue);
            return;
        case XpdExtensionPackage.CALENDAR_REFERENCE__DATA_FIELD_ID:
            setDataFieldId((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.CALENDAR_REFERENCE__ALIAS:
            unsetAlias();
            return;
        case XpdExtensionPackage.CALENDAR_REFERENCE__DATA_FIELD_ID:
            unsetDataFieldId();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.CALENDAR_REFERENCE__ALIAS:
            return isSetAlias();
        case XpdExtensionPackage.CALENDAR_REFERENCE__DATA_FIELD_ID:
            return isSetDataFieldId();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (alias: "); //$NON-NLS-1$
        if (aliasESet)
            result.append(alias);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", dataFieldId: "); //$NON-NLS-1$
        if (dataFieldIdESet)
            result.append(dataFieldId);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} // CalendarReferenceImpl
