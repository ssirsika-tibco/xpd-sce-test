/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DatabaseFactoryImpl extends EFactoryImpl implements
        DatabaseFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DatabaseFactory init() {
        try {
            DatabaseFactory theDatabaseFactory =
                    (DatabaseFactory) EPackage.Registry.INSTANCE
                            .getEFactory("http://www.tibco.com/XPD/database1.0.0"); //$NON-NLS-1$ 
            if (theDatabaseFactory != null) {
                return theDatabaseFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new DatabaseFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatabaseFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case DatabasePackage.DATABASE_TYPE:
            return createDatabaseType();
        case DatabasePackage.DOCUMENT_ROOT:
            return createDocumentRoot();
        case DatabasePackage.OPERATION_TYPE:
            return createOperationType();
        case DatabasePackage.PARAMETERS_TYPE:
            return createParametersType();
        case DatabasePackage.PARAMETER_TYPE:
            return createParameterType();
        default:
            throw new IllegalArgumentException(
                    "The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
        case DatabasePackage.SQL_TYPE:
            return createSqlTypeFromString(eDataType, initialValue);
        case DatabasePackage.SQL_TYPE_OBJECT:
            return createSqlTypeObjectFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException(
                    "The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case DatabasePackage.SQL_TYPE:
            return convertSqlTypeToString(eDataType, instanceValue);
        case DatabasePackage.SQL_TYPE_OBJECT:
            return convertSqlTypeObjectToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException(
                    "The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatabaseType createDatabaseType() {
        DatabaseTypeImpl databaseType = new DatabaseTypeImpl();
        return databaseType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OperationType createOperationType() {
        OperationTypeImpl operationType = new OperationTypeImpl();
        return operationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParametersType createParametersType() {
        ParametersTypeImpl parametersType = new ParametersTypeImpl();
        return parametersType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterType createParameterType() {
        ParameterTypeImpl parameterType = new ParameterTypeImpl();
        return parameterType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SqlType createSqlTypeFromString(EDataType eDataType,
            String initialValue) {
        SqlType result = SqlType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSqlTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SqlType createSqlTypeObjectFromString(EDataType eDataType,
            String initialValue) {
        return createSqlTypeFromString(DatabasePackage.Literals.SQL_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSqlTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertSqlTypeToString(DatabasePackage.Literals.SQL_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatabasePackage getDatabasePackage() {
        return (DatabasePackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static DatabasePackage getPackage() {
        return DatabasePackage.eINSTANCE;
    }

} //DatabaseFactoryImpl
