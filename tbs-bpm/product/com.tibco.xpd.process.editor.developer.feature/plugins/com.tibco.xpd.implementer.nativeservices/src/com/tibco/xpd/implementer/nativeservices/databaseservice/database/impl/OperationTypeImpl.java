/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.OperationTypeImpl#getSql <em>Sql</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.OperationTypeImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.OperationTypeImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperationTypeImpl extends EObjectImpl implements OperationType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getSql() <em>Sql</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSql()
     * @generated
     * @ordered
     */
    protected static final String SQL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSql() <em>Sql</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSql()
     * @generated
     * @ordered
     */
    protected String sql = SQL_EDEFAULT;

    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected ParametersType parameters;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final SqlType TYPE_EDEFAULT = SqlType.STORED_PROC_LITERAL;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected SqlType type = TYPE_EDEFAULT;

    /**
     * This is true if the Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean typeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OperationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatabasePackage.Literals.OPERATION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getSql() {
        return sql;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSql(String newSql) {
        String oldSql = sql;
        sql = newSql;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DatabasePackage.OPERATION_TYPE__SQL, oldSql, sql));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParametersType getParameters() {
        return parameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParameters(ParametersType newParameters,
            NotificationChain msgs) {
        ParametersType oldParameters = parameters;
        parameters = newParameters;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            DatabasePackage.OPERATION_TYPE__PARAMETERS,
                            oldParameters, newParameters);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameters(ParametersType newParameters) {
        if (newParameters != parameters) {
            NotificationChain msgs = null;
            if (parameters != null)
                msgs =
                        ((InternalEObject) parameters)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - DatabasePackage.OPERATION_TYPE__PARAMETERS,
                                        null,
                                        msgs);
            if (newParameters != null)
                msgs =
                        ((InternalEObject) newParameters)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - DatabasePackage.OPERATION_TYPE__PARAMETERS,
                                        null,
                                        msgs);
            msgs = basicSetParameters(newParameters, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DatabasePackage.OPERATION_TYPE__PARAMETERS, newParameters,
                    newParameters));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SqlType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(SqlType newType) {
        SqlType oldType = type;
        type = newType == null ? TYPE_EDEFAULT : newType;
        boolean oldTypeESet = typeESet;
        typeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    DatabasePackage.OPERATION_TYPE__TYPE, oldType, type,
                    !oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetType() {
        SqlType oldType = type;
        boolean oldTypeESet = typeESet;
        type = TYPE_EDEFAULT;
        typeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    DatabasePackage.OPERATION_TYPE__TYPE, oldType,
                    TYPE_EDEFAULT, oldTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetType() {
        return typeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DatabasePackage.OPERATION_TYPE__PARAMETERS:
            return basicSetParameters(null, msgs);
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
        case DatabasePackage.OPERATION_TYPE__SQL:
            return getSql();
        case DatabasePackage.OPERATION_TYPE__PARAMETERS:
            return getParameters();
        case DatabasePackage.OPERATION_TYPE__TYPE:
            return getType();
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
        case DatabasePackage.OPERATION_TYPE__SQL:
            setSql((String) newValue);
            return;
        case DatabasePackage.OPERATION_TYPE__PARAMETERS:
            setParameters((ParametersType) newValue);
            return;
        case DatabasePackage.OPERATION_TYPE__TYPE:
            setType((SqlType) newValue);
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
        case DatabasePackage.OPERATION_TYPE__SQL:
            setSql(SQL_EDEFAULT);
            return;
        case DatabasePackage.OPERATION_TYPE__PARAMETERS:
            setParameters((ParametersType) null);
            return;
        case DatabasePackage.OPERATION_TYPE__TYPE:
            unsetType();
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
        case DatabasePackage.OPERATION_TYPE__SQL:
            return SQL_EDEFAULT == null ? sql != null : !SQL_EDEFAULT
                    .equals(sql);
        case DatabasePackage.OPERATION_TYPE__PARAMETERS:
            return parameters != null;
        case DatabasePackage.OPERATION_TYPE__TYPE:
            return isSetType();
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
        result.append(" (sql: "); //$NON-NLS-1$
        result.append(sql);
        result.append(", type: "); //$NON-NLS-1$
        if (typeESet)
            result.append(type);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //OperationTypeImpl