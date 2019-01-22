/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.database.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseFactory;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DocumentRoot;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DatabasePackageImpl extends EPackageImpl implements
        DatabasePackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass databaseTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass operationTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parametersTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parameterTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum sqlTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType sqlTypeObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private DatabasePackageImpl() {
        super(eNS_URI, DatabaseFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this
     * model, and for any others upon which it depends.  Simple
     * dependencies are satisfied by calling this method on all
     * dependent packages before doing anything else.  This method drives
     * initialization for interdependent packages directly, in parallel
     * with this package, itself.
     * <p>Of this package and its interdependencies, all packages which
     * have not yet been registered by their URI values are first created
     * and registered.  The packages are then initialized in two steps:
     * meta-model objects for all of the packages are created before any
     * are initialized, since one package's meta-model objects may refer to
     * those of another.
     * <p>Invocation of this method will not affect any packages that have
     * already been initialized.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static DatabasePackage init() {
        if (isInited)
            return (DatabasePackage) EPackage.Registry.INSTANCE
                    .getEPackage(DatabasePackage.eNS_URI);

        // Obtain or create and register package
        DatabasePackageImpl theDatabasePackage =
                (DatabasePackageImpl) (EPackage.Registry.INSTANCE
                        .getEPackage(eNS_URI) instanceof DatabasePackageImpl ? EPackage.Registry.INSTANCE
                        .getEPackage(eNS_URI)
                        : new DatabasePackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theDatabasePackage.createPackageContents();

        // Initialize created meta-data
        theDatabasePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theDatabasePackage.freeze();

        return theDatabasePackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDatabaseType() {
        return databaseTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDatabaseType_Operation() {
        return (EReference) databaseTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_Database() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOperationType() {
        return operationTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOperationType_Sql() {
        return (EAttribute) operationTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getOperationType_Parameters() {
        return (EReference) operationTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOperationType_Type() {
        return (EAttribute) operationTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParametersType() {
        return parametersTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParametersType_Parameter() {
        return (EReference) parametersTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterType() {
        return parameterTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterType_Name() {
        return (EAttribute) parameterTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSqlType() {
        return sqlTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSqlTypeObject() {
        return sqlTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DatabaseFactory getDatabaseFactory() {
        return (DatabaseFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated)
            return;
        isCreated = true;

        // Create classes and their features
        databaseTypeEClass = createEClass(DATABASE_TYPE);
        createEReference(databaseTypeEClass, DATABASE_TYPE__OPERATION);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DATABASE);

        operationTypeEClass = createEClass(OPERATION_TYPE);
        createEAttribute(operationTypeEClass, OPERATION_TYPE__SQL);
        createEReference(operationTypeEClass, OPERATION_TYPE__PARAMETERS);
        createEAttribute(operationTypeEClass, OPERATION_TYPE__TYPE);

        parametersTypeEClass = createEClass(PARAMETERS_TYPE);
        createEReference(parametersTypeEClass, PARAMETERS_TYPE__PARAMETER);

        parameterTypeEClass = createEClass(PARAMETER_TYPE);
        createEAttribute(parameterTypeEClass, PARAMETER_TYPE__NAME);

        // Create enums
        sqlTypeEEnum = createEEnum(SQL_TYPE);

        // Create data types
        sqlTypeObjectEDataType = createEDataType(SQL_TYPE_OBJECT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized)
            return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage =
                (XMLTypePackage) EPackage.Registry.INSTANCE
                        .getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(databaseTypeEClass,
                DatabaseType.class,
                "DatabaseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getDatabaseType_Operation(),
                this.getOperationType(),
                null,
                "operation", null, 1, 1, DatabaseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(documentRootEClass,
                DocumentRoot.class,
                "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getDocumentRoot_Mixed(),
                ecorePackage.getEFeatureMapEntry(),
                "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XMLNSPrefixMap(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XSISchemaLocation(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_Database(),
                this.getDatabaseType(),
                null,
                "database", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(operationTypeEClass,
                OperationType.class,
                "OperationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getOperationType_Sql(),
                theXMLTypePackage.getString(),
                "sql", null, 1, 1, OperationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getOperationType_Parameters(),
                this.getParametersType(),
                null,
                "parameters", null, 0, 1, OperationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getOperationType_Type(),
                this.getSqlType(),
                "type", "StoredProc", 0, 1, OperationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

        initEClass(parametersTypeEClass,
                ParametersType.class,
                "ParametersType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getParametersType_Parameter(),
                this.getParameterType(),
                null,
                "parameter", null, 0, -1, ParametersType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(parameterTypeEClass,
                ParameterType.class,
                "ParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getParameterType_Name(),
                theXMLTypePackage.getNMTOKEN(),
                "name", null, 0, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        // Initialize enums and add enum literals
        initEEnum(sqlTypeEEnum, SqlType.class, "SqlType"); //$NON-NLS-1$
        addEEnumLiteral(sqlTypeEEnum, SqlType.STORED_PROC_LITERAL);
        addEEnumLiteral(sqlTypeEEnum, SqlType.SELECT_LITERAL);
        addEEnumLiteral(sqlTypeEEnum, SqlType.UPDATE_LITERAL);

        // Initialize data types
        initEDataType(sqlTypeObjectEDataType,
                SqlType.class,
                "SqlTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$		
        addAnnotation(this, source, new String[] { "qualified", "true" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(databaseTypeEClass, source, new String[] {
                "name", "DatabaseType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDatabaseType_Operation(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Operation", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(documentRootEClass, source, new String[] { "name", "", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_Mixed(), source, new String[] {
                "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
                "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_XMLNSPrefixMap(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_XSISchemaLocation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Database(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Database", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(operationTypeEClass, source, new String[] {
                "name", "OperationType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getOperationType_Sql(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Sql", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getOperationType_Parameters(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Parameters", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getOperationType_Type(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Type", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(parametersTypeEClass, source, new String[] {
                "name", "ParametersType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getParametersType_Parameter(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Parameter", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(parameterTypeEClass, source, new String[] {
                "name", "ParameterType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getParameterType_Name(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(sqlTypeEEnum, source, new String[] { "name", "SqlType" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(sqlTypeObjectEDataType, source, new String[] {
                "name", "SqlType:Object", //$NON-NLS-1$ //$NON-NLS-2$
                "baseType", "SqlType" //$NON-NLS-1$ //$NON-NLS-2$
        });
    }

} //DatabasePackageImpl
