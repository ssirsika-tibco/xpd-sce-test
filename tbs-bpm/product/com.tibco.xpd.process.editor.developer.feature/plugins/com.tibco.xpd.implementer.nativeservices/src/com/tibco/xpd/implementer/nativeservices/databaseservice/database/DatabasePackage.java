/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.database;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseFactory
 * @model kind="package"
 *        extendedMetaData="qualified='true'"
 * @generated
 */
public interface DatabasePackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "database"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/XPD/database1.0.0"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "database"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DatabasePackage eINSTANCE =
            com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl
                    .init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabaseTypeImpl <em>Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabaseTypeImpl
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getDatabaseType()
     * @generated
     */
    int DATABASE_TYPE = 0;

    /**
     * The feature id for the '<em><b>Operation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATABASE_TYPE__OPERATION = 0;

    /**
     * The number of structural features of the '<em>Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATABASE_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DocumentRootImpl
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 1;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MIXED = 0;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
     * The feature id for the '<em><b>Database</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DATABASE = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.OperationTypeImpl <em>Operation Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.OperationTypeImpl
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getOperationType()
     * @generated
     */
    int OPERATION_TYPE = 2;

    /**
     * The feature id for the '<em><b>Sql</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPERATION_TYPE__SQL = 0;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPERATION_TYPE__PARAMETERS = 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPERATION_TYPE__TYPE = 2;

    /**
     * The number of structural features of the '<em>Operation Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPERATION_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParametersTypeImpl <em>Parameters Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParametersTypeImpl
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getParametersType()
     * @generated
     */
    int PARAMETERS_TYPE = 3;

    /**
     * The feature id for the '<em><b>Parameter</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETERS_TYPE__PARAMETER = 0;

    /**
     * The number of structural features of the '<em>Parameters Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETERS_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParameterTypeImpl
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getParameterType()
     * @generated
     */
    int PARAMETER_TYPE = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE__NAME = 0;

    /**
     * The number of structural features of the '<em>Parameter Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType <em>Sql Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getSqlType()
     * @generated
     */
    int SQL_TYPE = 5;

    /**
     * The meta object id for the '<em>Sql Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getSqlTypeObject()
     * @generated
     */
    int SQL_TYPE_OBJECT = 6;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType
     * @generated
     */
    EClass getDatabaseType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType#getOperation <em>Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Operation</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType#getOperation()
     * @see #getDatabaseType()
     * @generated
     */
    EReference getDatabaseType_Operation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot#getDatabase <em>Database</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Database</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot#getDatabase()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Database();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType <em>Operation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Operation Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType
     * @generated
     */
    EClass getOperationType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getSql <em>Sql</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Sql</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getSql()
     * @see #getOperationType()
     * @generated
     */
    EAttribute getOperationType_Sql();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getParameters <em>Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Parameters</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getParameters()
     * @see #getOperationType()
     * @generated
     */
    EReference getOperationType_Parameters();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType#getType()
     * @see #getOperationType()
     * @generated
     */
    EAttribute getOperationType_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType <em>Parameters Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameters Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType
     * @generated
     */
    EClass getParametersType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType#getParameter <em>Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Parameter</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType#getParameter()
     * @see #getParametersType()
     * @generated
     */
    EReference getParametersType_Parameter();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType <em>Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType
     * @generated
     */
    EClass getParameterType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType#getName()
     * @see #getParameterType()
     * @generated
     */
    EAttribute getParameterType_Name();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType <em>Sql Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Sql Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType
     * @generated
     */
    EEnum getSqlType();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType <em>Sql Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Sql Type Object</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType
     * @model instanceClass="com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType"
     *        extendedMetaData="name='SqlType:Object' baseType='SqlType'"
     * @generated
     */
    EDataType getSqlTypeObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    DatabaseFactory getDatabaseFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabaseTypeImpl <em>Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabaseTypeImpl
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getDatabaseType()
         * @generated
         */
        EClass DATABASE_TYPE = eINSTANCE.getDatabaseType();

        /**
         * The meta object literal for the '<em><b>Operation</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATABASE_TYPE__OPERATION =
                eINSTANCE.getDatabaseType_Operation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DocumentRootImpl
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP =
                eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION =
                eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Database</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DATABASE =
                eINSTANCE.getDocumentRoot_Database();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.OperationTypeImpl <em>Operation Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.OperationTypeImpl
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getOperationType()
         * @generated
         */
        EClass OPERATION_TYPE = eINSTANCE.getOperationType();

        /**
         * The meta object literal for the '<em><b>Sql</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OPERATION_TYPE__SQL = eINSTANCE.getOperationType_Sql();

        /**
         * The meta object literal for the '<em><b>Parameters</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OPERATION_TYPE__PARAMETERS =
                eINSTANCE.getOperationType_Parameters();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OPERATION_TYPE__TYPE = eINSTANCE.getOperationType_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParametersTypeImpl <em>Parameters Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParametersTypeImpl
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getParametersType()
         * @generated
         */
        EClass PARAMETERS_TYPE = eINSTANCE.getParametersType();

        /**
         * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARAMETERS_TYPE__PARAMETER =
                eINSTANCE.getParametersType_Parameter();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.ParameterTypeImpl
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getParameterType()
         * @generated
         */
        EClass PARAMETER_TYPE = eINSTANCE.getParameterType();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_TYPE__NAME = eINSTANCE.getParameterType_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType <em>Sql Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getSqlType()
         * @generated
         */
        EEnum SQL_TYPE = eINSTANCE.getSqlType();

        /**
         * The meta object literal for the '<em>Sql Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType
         * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl.DatabasePackageImpl#getSqlTypeObject()
         * @generated
         */
        EDataType SQL_TYPE_OBJECT = eINSTANCE.getSqlTypeObject();

    }

} //DatabasePackage
