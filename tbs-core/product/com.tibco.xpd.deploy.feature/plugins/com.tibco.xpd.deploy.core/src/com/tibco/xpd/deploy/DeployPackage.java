/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

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
 * @see com.tibco.xpd.deploy.DeployFactory
 * @model kind="package"
 * @generated
 */
public interface DeployPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "deploy";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http:///com/tibco/xpd/deploy/model.ecore";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "com.tibco.xpd.deploy.model";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DeployPackage eINSTANCE = com.tibco.xpd.deploy.impl.DeployPackageImpl
            .init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.NamedElement <em>Named Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.NamedElement
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getNamedElement()
     * @generated
     */
    int NAMED_ELEMENT = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__NAME = 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__DESCRIPTION = 1;

    /**
     * The number of structural features of the '<em>Named Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerConfigImpl <em>Server Config</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerConfigImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerConfig()
     * @generated
     */
    int SERVER_CONFIG = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONFIG__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONFIG__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Config Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONFIG__CONFIG_PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Server Config</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONFIG_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerTypeImpl <em>Server Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerTypeImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerType()
     * @generated
     */
    int SERVER_TYPE = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerConfigInfoImpl <em>Server Config Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerConfigInfoImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerConfigInfo()
     * @generated
     */
    int SERVER_CONFIG_INFO = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl <em>Config Parameter Info</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getConfigParameterInfo()
     * @generated
     */
    int CONFIG_PARAMETER_INFO = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.Module <em>Module</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.Module
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getModule()
     * @generated
     */
    int MODULE = 6;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerModuleImpl <em>Server Module</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerModuleImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerModule()
     * @generated
     */
    int SERVER_MODULE = 7;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerImpl <em>Server</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServer()
     * @generated
     */
    int SERVER = 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerContainerImpl <em>Server Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerContainerImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerContainer()
     * @generated
     */
    int SERVER_CONTAINER = 10;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ConfigParameterImpl <em>Config Parameter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ConfigParameterImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getConfigParameter()
     * @generated
     */
    int CONFIG_PARAMETER = 1;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER__KEY = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER__VALUE = 1;

    /**
     * The feature id for the '<em><b>Config Parameter Info</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER__CONFIG_PARAMETER_INFO = 2;

    /**
     * The number of structural features of the '<em>Config Parameter</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Valid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__VALID = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Last Extension Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__LAST_EXTENSION_ID = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__ID = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Server Config Info</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__SERVER_CONFIG_INFO = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Connection Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__CONNECTION_FACTORY = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Runtime Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__RUNTIME_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Supported Repository Types</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__SUPPORTED_REPOSITORY_TYPES = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Suppress Conectivity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE__SUPPRESS_CONECTIVITY = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Server Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_TYPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONFIG_INFO__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONFIG_INFO__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Config Parameter Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONFIG_INFO__CONFIG_PARAMETER_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Server Config Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONFIG_INFO_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__KEY = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__LABEL = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Icon</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__ICON = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Parameter Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__PARAMETER_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Default Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__DEFAULT_VALUE = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__REQUIRED = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Automatic</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__AUTOMATIC = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Facets</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO__FACETS = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Config Parameter Info</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONFIG_PARAMETER_INFO_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The number of structural features of the '<em>Module</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_MODULE__NAME = MODULE__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_MODULE__DESCRIPTION = MODULE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Server Element Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_MODULE__SERVER_ELEMENT_TYPE = MODULE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Server Element State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_MODULE__SERVER_ELEMENT_STATE = MODULE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_MODULE__PARAMETERS = MODULE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Server Module</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_MODULE_FEATURE_COUNT = MODULE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl <em>Repository Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.RepositoryTypeImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRepositoryType()
     * @generated
     */
    int REPOSITORY_TYPE = 11;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.RepositoryImpl <em>Repository</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.RepositoryImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRepository()
     * @generated
     */
    int REPOSITORY = 12;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.RepositoryConfigImpl <em>Repository Config</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.RepositoryConfigImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRepositoryConfig()
     * @generated
     */
    int REPOSITORY_CONFIG = 13;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.RuntimeImpl <em>Runtime</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.RuntimeImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRuntime()
     * @generated
     */
    int RUNTIME = 14;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.RuntimeTypeImpl <em>Runtime Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.RuntimeTypeImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRuntimeType()
     * @generated
     */
    int RUNTIME_TYPE = 15;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.RuntimeConfigImpl <em>Runtime Config</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.RuntimeConfigImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRuntimeConfig()
     * @generated
     */
    int RUNTIME_CONFIG = 16;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.ServerElement <em>Server Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.ServerElement
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerElement()
     * @generated
     */
    int SERVER_ELEMENT = 9;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.ContainerElement <em>Container Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.ContainerElement
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getContainerElement()
     * @generated
     */
    int CONTAINER_ELEMENT = 17;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.LeafElement <em>Leaf Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.LeafElement
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getLeafElement()
     * @generated
     */
    int LEAF_ELEMENT = 18;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ModuleContainerImpl <em>Module Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ModuleContainerImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getModuleContainer()
     * @generated
     */
    int MODULE_CONTAINER = 19;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerElementTypeImpl <em>Server Element Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerElementTypeImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerElementType()
     * @generated
     */
    int SERVER_ELEMENT_TYPE = 20;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.OperationImpl <em>Operation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.OperationImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getOperation()
     * @generated
     */
    int OPERATION = 21;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerElementStateImpl <em>Server Element State</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerElementStateImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerElementState()
     * @generated
     */
    int SERVER_ELEMENT_STATE = 22;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.WorkspaceModuleImpl <em>Workspace Module</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.WorkspaceModuleImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getWorkspaceModule()
     * @generated
     */
    int WORKSPACE_MODULE = 23;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.UniqueIdElementImpl <em>Unique Id Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.UniqueIdElementImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getUniqueIdElement()
     * @generated
     */
    int UNIQUE_ID_ELEMENT = 24;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIQUE_ID_ELEMENT__ID = 0;

    /**
     * The number of structural features of the '<em>Unique Id Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIQUE_ID_ELEMENT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__ID = UNIQUE_ID_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__NAME = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__DESCRIPTION = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Server Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__SERVER_TYPE = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Server Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__SERVER_CONFIG = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Server State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__SERVER_STATE = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Connection</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__CONNECTION = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Repository</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__REPOSITORY = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Runtime</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__RUNTIME = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Server Elements</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__SERVER_ELEMENTS = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Workspace Modules</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__WORKSPACE_MODULES = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Server Group</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER__SERVER_GROUP = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Server</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_FEATURE_COUNT = UNIQUE_ID_ELEMENT_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Server Element Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT__SERVER_ELEMENT_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Server Element State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT__SERVER_ELEMENT_STATE = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT__PARAMETERS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Server Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Servers</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONTAINER__SERVERS = 0;

    /**
     * The feature id for the '<em><b>Runtimes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONTAINER__RUNTIMES = 1;

    /**
     * The feature id for the '<em><b>Server Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONTAINER__SERVER_TYPES = 2;

    /**
     * The feature id for the '<em><b>Repository Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONTAINER__REPOSITORY_TYPES = 3;

    /**
     * The feature id for the '<em><b>Runtime Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONTAINER__RUNTIME_TYPES = 4;

    /**
     * The feature id for the '<em><b>Server Group Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONTAINER__SERVER_GROUP_TYPES = 5;

    /**
     * The feature id for the '<em><b>Server Groups</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONTAINER__SERVER_GROUPS = 6;

    /**
     * The number of structural features of the '<em>Server Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_CONTAINER_FEATURE_COUNT = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_TYPE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_TYPE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Valid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_TYPE__VALID = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Last Extension Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_TYPE__LAST_EXTENSION_ID = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_TYPE__ID = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Repository Parameter Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Repository Publisher</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_TYPE__REPOSITORY_PUBLISHER = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Repository Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_TYPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Repository Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY__REPOSITORY_TYPE = 0;

    /**
     * The feature id for the '<em><b>Repository Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY__REPOSITORY_CONFIG = 1;

    /**
     * The number of structural features of the '<em>Repository</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Config Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_CONFIG__CONFIG_PARAMETERS = 0;

    /**
     * The number of structural features of the '<em>Repository Config</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_CONFIG_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Runtime Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME__RUNTIME_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Runtime Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME__RUNTIME_CONFIG = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Runtime</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_TYPE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_TYPE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Valid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_TYPE__VALID = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Last Extension Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_TYPE__LAST_EXTENSION_ID = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_TYPE__ID = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Runtime Parameter Infos</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_TYPE__RUNTIME_PARAMETER_INFOS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Runtime Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_TYPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Config Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_CONFIG__CONFIG_PARAMETERS = 0;

    /**
     * The number of structural features of the '<em>Runtime Config</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RUNTIME_CONFIG_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONTAINER_ELEMENT__NAME = SERVER_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONTAINER_ELEMENT__DESCRIPTION = SERVER_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Server Element Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONTAINER_ELEMENT__SERVER_ELEMENT_TYPE = SERVER_ELEMENT__SERVER_ELEMENT_TYPE;

    /**
     * The feature id for the '<em><b>Server Element State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONTAINER_ELEMENT__SERVER_ELEMENT_STATE = SERVER_ELEMENT__SERVER_ELEMENT_STATE;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONTAINER_ELEMENT__PARAMETERS = SERVER_ELEMENT__PARAMETERS;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONTAINER_ELEMENT__CHILDREN = SERVER_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Container Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONTAINER_ELEMENT_FEATURE_COUNT = SERVER_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LEAF_ELEMENT__NAME = SERVER_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LEAF_ELEMENT__DESCRIPTION = SERVER_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Server Element Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LEAF_ELEMENT__SERVER_ELEMENT_TYPE = SERVER_ELEMENT__SERVER_ELEMENT_TYPE;

    /**
     * The feature id for the '<em><b>Server Element State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LEAF_ELEMENT__SERVER_ELEMENT_STATE = SERVER_ELEMENT__SERVER_ELEMENT_STATE;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LEAF_ELEMENT__PARAMETERS = SERVER_ELEMENT__PARAMETERS;

    /**
     * The number of structural features of the '<em>Leaf Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LEAF_ELEMENT_FEATURE_COUNT = SERVER_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE_CONTAINER__NAME = CONTAINER_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE_CONTAINER__DESCRIPTION = CONTAINER_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Server Element Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE_CONTAINER__SERVER_ELEMENT_TYPE = CONTAINER_ELEMENT__SERVER_ELEMENT_TYPE;

    /**
     * The feature id for the '<em><b>Server Element State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE_CONTAINER__SERVER_ELEMENT_STATE = CONTAINER_ELEMENT__SERVER_ELEMENT_STATE;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE_CONTAINER__PARAMETERS = CONTAINER_ELEMENT__PARAMETERS;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE_CONTAINER__CHILDREN = CONTAINER_ELEMENT__CHILDREN;

    /**
     * The number of structural features of the '<em>Module Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODULE_CONTAINER_FEATURE_COUNT = CONTAINER_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Operations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT_TYPE__OPERATIONS = 0;

    /**
     * The feature id for the '<em><b>States</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT_TYPE__STATES = 1;

    /**
     * The number of structural features of the '<em>Server Element Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT_TYPE_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPERATION__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPERATION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>To State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPERATION__TO_STATE = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Operation</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OPERATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT_STATE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT_STATE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Possible Operations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT_STATE__POSSIBLE_OPERATIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Server Element State</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_ELEMENT_STATE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKSPACE_MODULE__NAME = MODULE__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKSPACE_MODULE__DESCRIPTION = MODULE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Workspace Src Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKSPACE_MODULE__WORKSPACE_SRC_PATH = MODULE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Module Kind</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKSPACE_MODULE__MODULE_KIND = MODULE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Dirty</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKSPACE_MODULE__DIRTY = MODULE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Workspace Module</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKSPACE_MODULE_FEATURE_COUNT = MODULE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerGroupImpl <em>Server Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerGroupImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerGroup()
     * @generated
     */
    int SERVER_GROUP = 25;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Members</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP__MEMBERS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Server Group Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP__SERVER_GROUP_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Server Group</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ServerGroupTypeImpl <em>Server Group Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ServerGroupTypeImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerGroupType()
     * @generated
     */
    int SERVER_GROUP_TYPE = 26;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP_TYPE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP_TYPE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Valid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP_TYPE__VALID = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Last Extension Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP_TYPE__LAST_EXTENSION_ID = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Server Types</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP_TYPE__SERVER_TYPES = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP_TYPE__ID = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Server Group Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVER_GROUP_TYPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.Loadable <em>Loadable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.Loadable
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getLoadable()
     * @generated
     */
    int LOADABLE = 27;

    /**
     * The feature id for the '<em><b>Valid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOADABLE__VALID = 0;

    /**
     * The feature id for the '<em><b>Last Extension Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOADABLE__LAST_EXTENSION_ID = 1;

    /**
     * The number of structural features of the '<em>Loadable</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOADABLE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.impl.ParameterFacetImpl <em>Parameter Facet</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.impl.ParameterFacetImpl
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getParameterFacet()
     * @generated
     */
    int PARAMETER_FACET = 28;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_FACET__KEY = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_FACET__VALUE = 1;

    /**
     * The number of structural features of the '<em>Parameter Facet</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_FACET_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.ServerState <em>Server State</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.ServerState
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerState()
     * @generated
     */
    int SERVER_STATE = 29;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.ConfigParameterType <em>Config Parameter Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.ConfigParameterType
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getConfigParameterType()
     * @generated
     */
    int CONFIG_PARAMETER_TYPE = 30;

    /**
     * The meta object id for the '{@link com.tibco.xpd.deploy.DeploymentType <em>Deployment Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.DeploymentType
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getDeploymentType()
     * @generated
     */
    int DEPLOYMENT_TYPE = 31;

    /**
     * The meta object id for the '<em>EConnection</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.model.extension.Connection
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getEConnection()
     * @generated
     */
    int ECONNECTION = 32;

    /**
     * The meta object id for the '<em>EConnection Factory</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.model.extension.ConnectionFactory
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getEConnectionFactory()
     * @generated
     */
    int ECONNECTION_FACTORY = 33;

    /**
     * The meta object id for the '<em>ERepository Publisher</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.model.extension.RepositoryPublisher
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getERepositoryPublisher()
     * @generated
     */
    int EREPOSITORY_PUBLISHER = 34;

    /**
     * The meta object id for the '<em>EDeployment Exception</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.deploy.model.extension.DeploymentException
     * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getEDeploymentException()
     * @generated
     */
    int EDEPLOYMENT_EXCEPTION = 35;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerConfig <em>Server Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Config</em>'.
     * @see com.tibco.xpd.deploy.ServerConfig
     * @generated
     */
    EClass getServerConfig();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerConfig#getConfigParameters <em>Config Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Config Parameters</em>'.
     * @see com.tibco.xpd.deploy.ServerConfig#getConfigParameters()
     * @see #getServerConfig()
     * @generated
     */
    EReference getServerConfig_ConfigParameters();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Named Element</em>'.
     * @see com.tibco.xpd.deploy.NamedElement
     * @generated
     */
    EClass getNamedElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.NamedElement#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.deploy.NamedElement#getName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.NamedElement#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.deploy.NamedElement#getDescription()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Description();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerType <em>Server Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Type</em>'.
     * @see com.tibco.xpd.deploy.ServerType
     * @generated
     */
    EClass getServerType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ServerType#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.deploy.ServerType#getId()
     * @see #getServerType()
     * @generated
     */
    EAttribute getServerType_Id();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.deploy.ServerType#getServerConfigInfo <em>Server Config Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Server Config Info</em>'.
     * @see com.tibco.xpd.deploy.ServerType#getServerConfigInfo()
     * @see #getServerType()
     * @generated
     */
    EReference getServerType_ServerConfigInfo();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ServerType#getConnectionFactory <em>Connection Factory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Connection Factory</em>'.
     * @see com.tibco.xpd.deploy.ServerType#getConnectionFactory()
     * @see #getServerType()
     * @generated
     */
    EAttribute getServerType_ConnectionFactory();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.ServerType#getRuntimeType <em>Runtime Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Runtime Type</em>'.
     * @see com.tibco.xpd.deploy.ServerType#getRuntimeType()
     * @see #getServerType()
     * @generated
     */
    EReference getServerType_RuntimeType();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.deploy.ServerType#getSupportedRepositoryTypes <em>Supported Repository Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Supported Repository Types</em>'.
     * @see com.tibco.xpd.deploy.ServerType#getSupportedRepositoryTypes()
     * @see #getServerType()
     * @generated
     */
    EReference getServerType_SupportedRepositoryTypes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ServerType#isSuppressConectivity <em>Suppress Conectivity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Suppress Conectivity</em>'.
     * @see com.tibco.xpd.deploy.ServerType#isSuppressConectivity()
     * @see #getServerType()
     * @generated
     */
    EAttribute getServerType_SuppressConectivity();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerConfigInfo <em>Server Config Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Config Info</em>'.
     * @see com.tibco.xpd.deploy.ServerConfigInfo
     * @generated
     */
    EClass getServerConfigInfo();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerConfigInfo#getConfigParameterInfos <em>Config Parameter Infos</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Config Parameter Infos</em>'.
     * @see com.tibco.xpd.deploy.ServerConfigInfo#getConfigParameterInfos()
     * @see #getServerConfigInfo()
     * @generated
     */
    EReference getServerConfigInfo_ConfigParameterInfos();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ConfigParameterInfo <em>Config Parameter Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Config Parameter Info</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo
     * @generated
     */
    EClass getConfigParameterInfo();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getKey <em>Key</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Key</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo#getKey()
     * @see #getConfigParameterInfo()
     * @generated
     */
    EAttribute getConfigParameterInfo_Key();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getLabel <em>Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo#getLabel()
     * @see #getConfigParameterInfo()
     * @generated
     */
    EAttribute getConfigParameterInfo_Label();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getIcon <em>Icon</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Icon</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo#getIcon()
     * @see #getConfigParameterInfo()
     * @generated
     */
    EAttribute getConfigParameterInfo_Icon();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getParameterType <em>Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter Type</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo#getParameterType()
     * @see #getConfigParameterInfo()
     * @generated
     */
    EAttribute getConfigParameterInfo_ParameterType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getDefaultValue <em>Default Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Value</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo#getDefaultValue()
     * @see #getConfigParameterInfo()
     * @generated
     */
    EAttribute getConfigParameterInfo_DefaultValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameterInfo#isRequired <em>Required</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Required</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo#isRequired()
     * @see #getConfigParameterInfo()
     * @generated
     */
    EAttribute getConfigParameterInfo_Required();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameterInfo#isAutomatic <em>Automatic</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Automatic</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo#isAutomatic()
     * @see #getConfigParameterInfo()
     * @generated
     */
    EAttribute getConfigParameterInfo_Automatic();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ConfigParameterInfo#getFacets <em>Facets</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Facets</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo#getFacets()
     * @see #getConfigParameterInfo()
     * @generated
     */
    EReference getConfigParameterInfo_Facets();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.Module <em>Module</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Module</em>'.
     * @see com.tibco.xpd.deploy.Module
     * @generated
     */
    EClass getModule();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerModule <em>Server Module</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Module</em>'.
     * @see com.tibco.xpd.deploy.ServerModule
     * @generated
     */
    EClass getServerModule();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.Server <em>Server</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server</em>'.
     * @see com.tibco.xpd.deploy.Server
     * @generated
     */
    EClass getServer();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.Server#getServerType <em>Server Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Server Type</em>'.
     * @see com.tibco.xpd.deploy.Server#getServerType()
     * @see #getServer()
     * @generated
     */
    EReference getServer_ServerType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.deploy.Server#getServerConfig <em>Server Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Server Config</em>'.
     * @see com.tibco.xpd.deploy.Server#getServerConfig()
     * @see #getServer()
     * @generated
     */
    EReference getServer_ServerConfig();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.Server#getServerState <em>Server State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Server State</em>'.
     * @see com.tibco.xpd.deploy.Server#getServerState()
     * @see #getServer()
     * @generated
     */
    EAttribute getServer_ServerState();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.Server#getConnection <em>Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Connection</em>'.
     * @see com.tibco.xpd.deploy.Server#getConnection()
     * @see #getServer()
     * @generated
     */
    EAttribute getServer_Connection();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.deploy.Server#getRepository <em>Repository</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Repository</em>'.
     * @see com.tibco.xpd.deploy.Server#getRepository()
     * @see #getServer()
     * @generated
     */
    EReference getServer_Repository();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.Server#getRuntime <em>Runtime</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Runtime</em>'.
     * @see com.tibco.xpd.deploy.Server#getRuntime()
     * @see #getServer()
     * @generated
     */
    EReference getServer_Runtime();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.Server#getServerElements <em>Server Elements</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Server Elements</em>'.
     * @see com.tibco.xpd.deploy.Server#getServerElements()
     * @see #getServer()
     * @generated
     */
    EReference getServer_ServerElements();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.Server#getWorkspaceModules <em>Workspace Modules</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Workspace Modules</em>'.
     * @see com.tibco.xpd.deploy.Server#getWorkspaceModules()
     * @see #getServer()
     * @generated
     */
    EReference getServer_WorkspaceModules();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.Server#getServerGroup <em>Server Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Server Group</em>'.
     * @see com.tibco.xpd.deploy.Server#getServerGroup()
     * @see #getServer()
     * @generated
     */
    EReference getServer_ServerGroup();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerContainer <em>Server Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Container</em>'.
     * @see com.tibco.xpd.deploy.ServerContainer
     * @generated
     */
    EClass getServerContainer();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerContainer#getServers <em>Servers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Servers</em>'.
     * @see com.tibco.xpd.deploy.ServerContainer#getServers()
     * @see #getServerContainer()
     * @generated
     */
    EReference getServerContainer_Servers();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerContainer#getRuntimes <em>Runtimes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Runtimes</em>'.
     * @see com.tibco.xpd.deploy.ServerContainer#getRuntimes()
     * @see #getServerContainer()
     * @generated
     */
    EReference getServerContainer_Runtimes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerContainer#getServerTypes <em>Server Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Server Types</em>'.
     * @see com.tibco.xpd.deploy.ServerContainer#getServerTypes()
     * @see #getServerContainer()
     * @generated
     */
    EReference getServerContainer_ServerTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerContainer#getRepositoryTypes <em>Repository Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Repository Types</em>'.
     * @see com.tibco.xpd.deploy.ServerContainer#getRepositoryTypes()
     * @see #getServerContainer()
     * @generated
     */
    EReference getServerContainer_RepositoryTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerContainer#getRuntimeTypes <em>Runtime Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Runtime Types</em>'.
     * @see com.tibco.xpd.deploy.ServerContainer#getRuntimeTypes()
     * @see #getServerContainer()
     * @generated
     */
    EReference getServerContainer_RuntimeTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerContainer#getServerGroupTypes <em>Server Group Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Server Group Types</em>'.
     * @see com.tibco.xpd.deploy.ServerContainer#getServerGroupTypes()
     * @see #getServerContainer()
     * @generated
     */
    EReference getServerContainer_ServerGroupTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerContainer#getServerGroups <em>Server Groups</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Server Groups</em>'.
     * @see com.tibco.xpd.deploy.ServerContainer#getServerGroups()
     * @see #getServerContainer()
     * @generated
     */
    EReference getServerContainer_ServerGroups();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ConfigParameter <em>Config Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Config Parameter</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameter
     * @generated
     */
    EClass getConfigParameter();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameter#getKey <em>Key</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Key</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameter#getKey()
     * @see #getConfigParameter()
     * @generated
     */
    EAttribute getConfigParameter_Key();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ConfigParameter#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameter#getValue()
     * @see #getConfigParameter()
     * @generated
     */
    EAttribute getConfigParameter_Value();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.ConfigParameter#getConfigParameterInfo <em>Config Parameter Info</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Config Parameter Info</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameter#getConfigParameterInfo()
     * @see #getConfigParameter()
     * @generated
     */
    EReference getConfigParameter_ConfigParameterInfo();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.RepositoryType <em>Repository Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Repository Type</em>'.
     * @see com.tibco.xpd.deploy.RepositoryType
     * @generated
     */
    EClass getRepositoryType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.RepositoryType#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.deploy.RepositoryType#getId()
     * @see #getRepositoryType()
     * @generated
     */
    EAttribute getRepositoryType_Id();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.RepositoryType#getRepositoryParameterInfos <em>Repository Parameter Infos</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Repository Parameter Infos</em>'.
     * @see com.tibco.xpd.deploy.RepositoryType#getRepositoryParameterInfos()
     * @see #getRepositoryType()
     * @generated
     */
    EReference getRepositoryType_RepositoryParameterInfos();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.RepositoryType#getRepositoryPublisher <em>Repository Publisher</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Repository Publisher</em>'.
     * @see com.tibco.xpd.deploy.RepositoryType#getRepositoryPublisher()
     * @see #getRepositoryType()
     * @generated
     */
    EAttribute getRepositoryType_RepositoryPublisher();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.Repository <em>Repository</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Repository</em>'.
     * @see com.tibco.xpd.deploy.Repository
     * @generated
     */
    EClass getRepository();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.Repository#getRepositoryType <em>Repository Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Repository Type</em>'.
     * @see com.tibco.xpd.deploy.Repository#getRepositoryType()
     * @see #getRepository()
     * @generated
     */
    EReference getRepository_RepositoryType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.deploy.Repository#getRepositoryConfig <em>Repository Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Repository Config</em>'.
     * @see com.tibco.xpd.deploy.Repository#getRepositoryConfig()
     * @see #getRepository()
     * @generated
     */
    EReference getRepository_RepositoryConfig();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.RepositoryConfig <em>Repository Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Repository Config</em>'.
     * @see com.tibco.xpd.deploy.RepositoryConfig
     * @generated
     */
    EClass getRepositoryConfig();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.RepositoryConfig#getConfigParameters <em>Config Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Config Parameters</em>'.
     * @see com.tibco.xpd.deploy.RepositoryConfig#getConfigParameters()
     * @see #getRepositoryConfig()
     * @generated
     */
    EReference getRepositoryConfig_ConfigParameters();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.Runtime <em>Runtime</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Runtime</em>'.
     * @see com.tibco.xpd.deploy.Runtime
     * @generated
     */
    EClass getRuntime();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.Runtime#getRuntimeType <em>Runtime Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Runtime Type</em>'.
     * @see com.tibco.xpd.deploy.Runtime#getRuntimeType()
     * @see #getRuntime()
     * @generated
     */
    EReference getRuntime_RuntimeType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.deploy.Runtime#getRuntimeConfig <em>Runtime Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Runtime Config</em>'.
     * @see com.tibco.xpd.deploy.Runtime#getRuntimeConfig()
     * @see #getRuntime()
     * @generated
     */
    EReference getRuntime_RuntimeConfig();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.RuntimeType <em>Runtime Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Runtime Type</em>'.
     * @see com.tibco.xpd.deploy.RuntimeType
     * @generated
     */
    EClass getRuntimeType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.RuntimeType#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.deploy.RuntimeType#getId()
     * @see #getRuntimeType()
     * @generated
     */
    EAttribute getRuntimeType_Id();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.RuntimeType#getRuntimeParameterInfos <em>Runtime Parameter Infos</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Runtime Parameter Infos</em>'.
     * @see com.tibco.xpd.deploy.RuntimeType#getRuntimeParameterInfos()
     * @see #getRuntimeType()
     * @generated
     */
    EReference getRuntimeType_RuntimeParameterInfos();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.RuntimeConfig <em>Runtime Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Runtime Config</em>'.
     * @see com.tibco.xpd.deploy.RuntimeConfig
     * @generated
     */
    EClass getRuntimeConfig();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.RuntimeConfig#getConfigParameters <em>Config Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Config Parameters</em>'.
     * @see com.tibco.xpd.deploy.RuntimeConfig#getConfigParameters()
     * @see #getRuntimeConfig()
     * @generated
     */
    EReference getRuntimeConfig_ConfigParameters();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerElement <em>Server Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Element</em>'.
     * @see com.tibco.xpd.deploy.ServerElement
     * @generated
     */
    EClass getServerElement();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.ServerElement#getServerElementType <em>Server Element Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Server Element Type</em>'.
     * @see com.tibco.xpd.deploy.ServerElement#getServerElementType()
     * @see #getServerElement()
     * @generated
     */
    EReference getServerElement_ServerElementType();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.ServerElement#getServerElementState <em>Server Element State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Server Element State</em>'.
     * @see com.tibco.xpd.deploy.ServerElement#getServerElementState()
     * @see #getServerElement()
     * @generated
     */
    EReference getServerElement_ServerElementState();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ServerElement#getParameters <em>Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Parameters</em>'.
     * @see com.tibco.xpd.deploy.ServerElement#getParameters()
     * @see #getServerElement()
     * @generated
     */
    EReference getServerElement_Parameters();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ContainerElement <em>Container Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Container Element</em>'.
     * @see com.tibco.xpd.deploy.ContainerElement
     * @generated
     */
    EClass getContainerElement();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.deploy.ContainerElement#getChildren <em>Children</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Children</em>'.
     * @see com.tibco.xpd.deploy.ContainerElement#getChildren()
     * @see #getContainerElement()
     * @generated
     */
    EReference getContainerElement_Children();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.LeafElement <em>Leaf Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Leaf Element</em>'.
     * @see com.tibco.xpd.deploy.LeafElement
     * @generated
     */
    EClass getLeafElement();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ModuleContainer <em>Module Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Module Container</em>'.
     * @see com.tibco.xpd.deploy.ModuleContainer
     * @generated
     */
    EClass getModuleContainer();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerElementType <em>Server Element Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Element Type</em>'.
     * @see com.tibco.xpd.deploy.ServerElementType
     * @generated
     */
    EClass getServerElementType();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.deploy.ServerElementType#getOperations <em>Operations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Operations</em>'.
     * @see com.tibco.xpd.deploy.ServerElementType#getOperations()
     * @see #getServerElementType()
     * @generated
     */
    EReference getServerElementType_Operations();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.deploy.ServerElementType#getStates <em>States</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>States</em>'.
     * @see com.tibco.xpd.deploy.ServerElementType#getStates()
     * @see #getServerElementType()
     * @generated
     */
    EReference getServerElementType_States();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.Operation <em>Operation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Operation</em>'.
     * @see com.tibco.xpd.deploy.Operation
     * @generated
     */
    EClass getOperation();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.Operation#getToState <em>To State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>To State</em>'.
     * @see com.tibco.xpd.deploy.Operation#getToState()
     * @see #getOperation()
     * @generated
     */
    EReference getOperation_ToState();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerElementState <em>Server Element State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Element State</em>'.
     * @see com.tibco.xpd.deploy.ServerElementState
     * @generated
     */
    EClass getServerElementState();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.deploy.ServerElementState#getPossibleOperations <em>Possible Operations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Possible Operations</em>'.
     * @see com.tibco.xpd.deploy.ServerElementState#getPossibleOperations()
     * @see #getServerElementState()
     * @generated
     */
    EReference getServerElementState_PossibleOperations();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.WorkspaceModule <em>Workspace Module</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Workspace Module</em>'.
     * @see com.tibco.xpd.deploy.WorkspaceModule
     * @generated
     */
    EClass getWorkspaceModule();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.WorkspaceModule#getWorkspaceSrcPath <em>Workspace Src Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Workspace Src Path</em>'.
     * @see com.tibco.xpd.deploy.WorkspaceModule#getWorkspaceSrcPath()
     * @see #getWorkspaceModule()
     * @generated
     */
    EAttribute getWorkspaceModule_WorkspaceSrcPath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.WorkspaceModule#getModuleKind <em>Module Kind</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Module Kind</em>'.
     * @see com.tibco.xpd.deploy.WorkspaceModule#getModuleKind()
     * @see #getWorkspaceModule()
     * @generated
     */
    EAttribute getWorkspaceModule_ModuleKind();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.WorkspaceModule#isDirty <em>Dirty</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Dirty</em>'.
     * @see com.tibco.xpd.deploy.WorkspaceModule#isDirty()
     * @see #getWorkspaceModule()
     * @generated
     */
    EAttribute getWorkspaceModule_Dirty();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.UniqueIdElement <em>Unique Id Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unique Id Element</em>'.
     * @see com.tibco.xpd.deploy.UniqueIdElement
     * @generated
     */
    EClass getUniqueIdElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.UniqueIdElement#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.deploy.UniqueIdElement#getId()
     * @see #getUniqueIdElement()
     * @generated
     */
    EAttribute getUniqueIdElement_Id();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerGroup <em>Server Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Group</em>'.
     * @see com.tibco.xpd.deploy.ServerGroup
     * @generated
     */
    EClass getServerGroup();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.deploy.ServerGroup#getMembers <em>Members</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Members</em>'.
     * @see com.tibco.xpd.deploy.ServerGroup#getMembers()
     * @see #getServerGroup()
     * @generated
     */
    EReference getServerGroup_Members();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.deploy.ServerGroup#getServerGroupType <em>Server Group Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Server Group Type</em>'.
     * @see com.tibco.xpd.deploy.ServerGroup#getServerGroupType()
     * @see #getServerGroup()
     * @generated
     */
    EReference getServerGroup_ServerGroupType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ServerGroupType <em>Server Group Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Server Group Type</em>'.
     * @see com.tibco.xpd.deploy.ServerGroupType
     * @generated
     */
    EClass getServerGroupType();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.deploy.ServerGroupType#getServerTypes <em>Server Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Server Types</em>'.
     * @see com.tibco.xpd.deploy.ServerGroupType#getServerTypes()
     * @see #getServerGroupType()
     * @generated
     */
    EReference getServerGroupType_ServerTypes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ServerGroupType#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.deploy.ServerGroupType#getId()
     * @see #getServerGroupType()
     * @generated
     */
    EAttribute getServerGroupType_Id();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.Loadable <em>Loadable</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Loadable</em>'.
     * @see com.tibco.xpd.deploy.Loadable
     * @generated
     */
    EClass getLoadable();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.Loadable#isValid <em>Valid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Valid</em>'.
     * @see com.tibco.xpd.deploy.Loadable#isValid()
     * @see #getLoadable()
     * @generated
     */
    EAttribute getLoadable_Valid();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.Loadable#getLastExtensionId <em>Last Extension Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Last Extension Id</em>'.
     * @see com.tibco.xpd.deploy.Loadable#getLastExtensionId()
     * @see #getLoadable()
     * @generated
     */
    EAttribute getLoadable_LastExtensionId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.deploy.ParameterFacet <em>Parameter Facet</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter Facet</em>'.
     * @see com.tibco.xpd.deploy.ParameterFacet
     * @generated
     */
    EClass getParameterFacet();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ParameterFacet#getKey <em>Key</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Key</em>'.
     * @see com.tibco.xpd.deploy.ParameterFacet#getKey()
     * @see #getParameterFacet()
     * @generated
     */
    EAttribute getParameterFacet_Key();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.deploy.ParameterFacet#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.deploy.ParameterFacet#getValue()
     * @see #getParameterFacet()
     * @generated
     */
    EAttribute getParameterFacet_Value();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.deploy.ServerState <em>Server State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Server State</em>'.
     * @see com.tibco.xpd.deploy.ServerState
     * @generated
     */
    EEnum getServerState();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.deploy.ConfigParameterType <em>Config Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Config Parameter Type</em>'.
     * @see com.tibco.xpd.deploy.ConfigParameterType
     * @generated
     */
    EEnum getConfigParameterType();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.deploy.DeploymentType <em>Deployment Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Deployment Type</em>'.
     * @see com.tibco.xpd.deploy.DeploymentType
     * @generated
     */
    EEnum getDeploymentType();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.deploy.model.extension.Connection <em>EConnection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>EConnection</em>'.
     * @see com.tibco.xpd.deploy.model.extension.Connection
     * @model instanceClass="com.tibco.xpd.deploy.model.extension.Connection" serializeable="false"
     * @generated
     */
    EDataType getEConnection();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.deploy.model.extension.ConnectionFactory <em>EConnection Factory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>EConnection Factory</em>'.
     * @see com.tibco.xpd.deploy.model.extension.ConnectionFactory
     * @model instanceClass="com.tibco.xpd.deploy.model.extension.ConnectionFactory" serializeable="false"
     * @generated
     */
    EDataType getEConnectionFactory();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.deploy.model.extension.RepositoryPublisher <em>ERepository Publisher</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>ERepository Publisher</em>'.
     * @see com.tibco.xpd.deploy.model.extension.RepositoryPublisher
     * @model instanceClass="com.tibco.xpd.deploy.model.extension.RepositoryPublisher"
     * @generated
     */
    EDataType getERepositoryPublisher();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.deploy.model.extension.DeploymentException <em>EDeployment Exception</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>EDeployment Exception</em>'.
     * @see com.tibco.xpd.deploy.model.extension.DeploymentException
     * @model instanceClass="com.tibco.xpd.deploy.model.extension.DeploymentException"
     * @generated
     */
    EDataType getEDeploymentException();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    DeployFactory getDeployFactory();

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
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerConfigImpl <em>Server Config</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerConfigImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerConfig()
         * @generated
         */
        EClass SERVER_CONFIG = eINSTANCE.getServerConfig();

        /**
         * The meta object literal for the '<em><b>Config Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONFIG__CONFIG_PARAMETERS = eINSTANCE
                .getServerConfig_ConfigParameters();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.NamedElement <em>Named Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.NamedElement
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getNamedElement()
         * @generated
         */
        EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__DESCRIPTION = eINSTANCE
                .getNamedElement_Description();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerTypeImpl <em>Server Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerTypeImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerType()
         * @generated
         */
        EClass SERVER_TYPE = eINSTANCE.getServerType();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVER_TYPE__ID = eINSTANCE.getServerType_Id();

        /**
         * The meta object literal for the '<em><b>Server Config Info</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_TYPE__SERVER_CONFIG_INFO = eINSTANCE
                .getServerType_ServerConfigInfo();

        /**
         * The meta object literal for the '<em><b>Connection Factory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVER_TYPE__CONNECTION_FACTORY = eINSTANCE
                .getServerType_ConnectionFactory();

        /**
         * The meta object literal for the '<em><b>Runtime Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_TYPE__RUNTIME_TYPE = eINSTANCE
                .getServerType_RuntimeType();

        /**
         * The meta object literal for the '<em><b>Supported Repository Types</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_TYPE__SUPPORTED_REPOSITORY_TYPES = eINSTANCE
                .getServerType_SupportedRepositoryTypes();

        /**
         * The meta object literal for the '<em><b>Suppress Conectivity</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVER_TYPE__SUPPRESS_CONECTIVITY = eINSTANCE
                .getServerType_SuppressConectivity();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerConfigInfoImpl <em>Server Config Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerConfigInfoImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerConfigInfo()
         * @generated
         */
        EClass SERVER_CONFIG_INFO = eINSTANCE.getServerConfigInfo();

        /**
         * The meta object literal for the '<em><b>Config Parameter Infos</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONFIG_INFO__CONFIG_PARAMETER_INFOS = eINSTANCE
                .getServerConfigInfo_ConfigParameterInfos();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl <em>Config Parameter Info</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getConfigParameterInfo()
         * @generated
         */
        EClass CONFIG_PARAMETER_INFO = eINSTANCE.getConfigParameterInfo();

        /**
         * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER_INFO__KEY = eINSTANCE
                .getConfigParameterInfo_Key();

        /**
         * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER_INFO__LABEL = eINSTANCE
                .getConfigParameterInfo_Label();

        /**
         * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER_INFO__ICON = eINSTANCE
                .getConfigParameterInfo_Icon();

        /**
         * The meta object literal for the '<em><b>Parameter Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER_INFO__PARAMETER_TYPE = eINSTANCE
                .getConfigParameterInfo_ParameterType();

        /**
         * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER_INFO__DEFAULT_VALUE = eINSTANCE
                .getConfigParameterInfo_DefaultValue();

        /**
         * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER_INFO__REQUIRED = eINSTANCE
                .getConfigParameterInfo_Required();

        /**
         * The meta object literal for the '<em><b>Automatic</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER_INFO__AUTOMATIC = eINSTANCE
                .getConfigParameterInfo_Automatic();

        /**
         * The meta object literal for the '<em><b>Facets</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CONFIG_PARAMETER_INFO__FACETS = eINSTANCE
                .getConfigParameterInfo_Facets();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.Module <em>Module</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.Module
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getModule()
         * @generated
         */
        EClass MODULE = eINSTANCE.getModule();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerModuleImpl <em>Server Module</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerModuleImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerModule()
         * @generated
         */
        EClass SERVER_MODULE = eINSTANCE.getServerModule();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerImpl <em>Server</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServer()
         * @generated
         */
        EClass SERVER = eINSTANCE.getServer();

        /**
         * The meta object literal for the '<em><b>Server Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER__SERVER_TYPE = eINSTANCE.getServer_ServerType();

        /**
         * The meta object literal for the '<em><b>Server Config</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER__SERVER_CONFIG = eINSTANCE.getServer_ServerConfig();

        /**
         * The meta object literal for the '<em><b>Server State</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVER__SERVER_STATE = eINSTANCE.getServer_ServerState();

        /**
         * The meta object literal for the '<em><b>Connection</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVER__CONNECTION = eINSTANCE.getServer_Connection();

        /**
         * The meta object literal for the '<em><b>Repository</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER__REPOSITORY = eINSTANCE.getServer_Repository();

        /**
         * The meta object literal for the '<em><b>Runtime</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER__RUNTIME = eINSTANCE.getServer_Runtime();

        /**
         * The meta object literal for the '<em><b>Server Elements</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER__SERVER_ELEMENTS = eINSTANCE
                .getServer_ServerElements();

        /**
         * The meta object literal for the '<em><b>Workspace Modules</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER__WORKSPACE_MODULES = eINSTANCE
                .getServer_WorkspaceModules();

        /**
         * The meta object literal for the '<em><b>Server Group</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER__SERVER_GROUP = eINSTANCE.getServer_ServerGroup();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerContainerImpl <em>Server Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerContainerImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerContainer()
         * @generated
         */
        EClass SERVER_CONTAINER = eINSTANCE.getServerContainer();

        /**
         * The meta object literal for the '<em><b>Servers</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONTAINER__SERVERS = eINSTANCE
                .getServerContainer_Servers();

        /**
         * The meta object literal for the '<em><b>Runtimes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONTAINER__RUNTIMES = eINSTANCE
                .getServerContainer_Runtimes();

        /**
         * The meta object literal for the '<em><b>Server Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONTAINER__SERVER_TYPES = eINSTANCE
                .getServerContainer_ServerTypes();

        /**
         * The meta object literal for the '<em><b>Repository Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONTAINER__REPOSITORY_TYPES = eINSTANCE
                .getServerContainer_RepositoryTypes();

        /**
         * The meta object literal for the '<em><b>Runtime Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONTAINER__RUNTIME_TYPES = eINSTANCE
                .getServerContainer_RuntimeTypes();

        /**
         * The meta object literal for the '<em><b>Server Group Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONTAINER__SERVER_GROUP_TYPES = eINSTANCE
                .getServerContainer_ServerGroupTypes();

        /**
         * The meta object literal for the '<em><b>Server Groups</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_CONTAINER__SERVER_GROUPS = eINSTANCE
                .getServerContainer_ServerGroups();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ConfigParameterImpl <em>Config Parameter</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ConfigParameterImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getConfigParameter()
         * @generated
         */
        EClass CONFIG_PARAMETER = eINSTANCE.getConfigParameter();

        /**
         * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER__KEY = eINSTANCE.getConfigParameter_Key();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONFIG_PARAMETER__VALUE = eINSTANCE
                .getConfigParameter_Value();

        /**
         * The meta object literal for the '<em><b>Config Parameter Info</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CONFIG_PARAMETER__CONFIG_PARAMETER_INFO = eINSTANCE
                .getConfigParameter_ConfigParameterInfo();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.RepositoryTypeImpl <em>Repository Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.RepositoryTypeImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRepositoryType()
         * @generated
         */
        EClass REPOSITORY_TYPE = eINSTANCE.getRepositoryType();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REPOSITORY_TYPE__ID = eINSTANCE.getRepositoryType_Id();

        /**
         * The meta object literal for the '<em><b>Repository Parameter Infos</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS = eINSTANCE
                .getRepositoryType_RepositoryParameterInfos();

        /**
         * The meta object literal for the '<em><b>Repository Publisher</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REPOSITORY_TYPE__REPOSITORY_PUBLISHER = eINSTANCE
                .getRepositoryType_RepositoryPublisher();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.RepositoryImpl <em>Repository</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.RepositoryImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRepository()
         * @generated
         */
        EClass REPOSITORY = eINSTANCE.getRepository();

        /**
         * The meta object literal for the '<em><b>Repository Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REPOSITORY__REPOSITORY_TYPE = eINSTANCE
                .getRepository_RepositoryType();

        /**
         * The meta object literal for the '<em><b>Repository Config</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REPOSITORY__REPOSITORY_CONFIG = eINSTANCE
                .getRepository_RepositoryConfig();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.RepositoryConfigImpl <em>Repository Config</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.RepositoryConfigImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRepositoryConfig()
         * @generated
         */
        EClass REPOSITORY_CONFIG = eINSTANCE.getRepositoryConfig();

        /**
         * The meta object literal for the '<em><b>Config Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REPOSITORY_CONFIG__CONFIG_PARAMETERS = eINSTANCE
                .getRepositoryConfig_ConfigParameters();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.RuntimeImpl <em>Runtime</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.RuntimeImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRuntime()
         * @generated
         */
        EClass RUNTIME = eINSTANCE.getRuntime();

        /**
         * The meta object literal for the '<em><b>Runtime Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RUNTIME__RUNTIME_TYPE = eINSTANCE.getRuntime_RuntimeType();

        /**
         * The meta object literal for the '<em><b>Runtime Config</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RUNTIME__RUNTIME_CONFIG = eINSTANCE
                .getRuntime_RuntimeConfig();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.RuntimeTypeImpl <em>Runtime Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.RuntimeTypeImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRuntimeType()
         * @generated
         */
        EClass RUNTIME_TYPE = eINSTANCE.getRuntimeType();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RUNTIME_TYPE__ID = eINSTANCE.getRuntimeType_Id();

        /**
         * The meta object literal for the '<em><b>Runtime Parameter Infos</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RUNTIME_TYPE__RUNTIME_PARAMETER_INFOS = eINSTANCE
                .getRuntimeType_RuntimeParameterInfos();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.RuntimeConfigImpl <em>Runtime Config</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.RuntimeConfigImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getRuntimeConfig()
         * @generated
         */
        EClass RUNTIME_CONFIG = eINSTANCE.getRuntimeConfig();

        /**
         * The meta object literal for the '<em><b>Config Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RUNTIME_CONFIG__CONFIG_PARAMETERS = eINSTANCE
                .getRuntimeConfig_ConfigParameters();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.ServerElement <em>Server Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.ServerElement
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerElement()
         * @generated
         */
        EClass SERVER_ELEMENT = eINSTANCE.getServerElement();

        /**
         * The meta object literal for the '<em><b>Server Element Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_ELEMENT__SERVER_ELEMENT_TYPE = eINSTANCE
                .getServerElement_ServerElementType();

        /**
         * The meta object literal for the '<em><b>Server Element State</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_ELEMENT__SERVER_ELEMENT_STATE = eINSTANCE
                .getServerElement_ServerElementState();

        /**
         * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_ELEMENT__PARAMETERS = eINSTANCE
                .getServerElement_Parameters();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.ContainerElement <em>Container Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.ContainerElement
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getContainerElement()
         * @generated
         */
        EClass CONTAINER_ELEMENT = eINSTANCE.getContainerElement();

        /**
         * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CONTAINER_ELEMENT__CHILDREN = eINSTANCE
                .getContainerElement_Children();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.LeafElement <em>Leaf Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.LeafElement
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getLeafElement()
         * @generated
         */
        EClass LEAF_ELEMENT = eINSTANCE.getLeafElement();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ModuleContainerImpl <em>Module Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ModuleContainerImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getModuleContainer()
         * @generated
         */
        EClass MODULE_CONTAINER = eINSTANCE.getModuleContainer();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerElementTypeImpl <em>Server Element Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerElementTypeImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerElementType()
         * @generated
         */
        EClass SERVER_ELEMENT_TYPE = eINSTANCE.getServerElementType();

        /**
         * The meta object literal for the '<em><b>Operations</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_ELEMENT_TYPE__OPERATIONS = eINSTANCE
                .getServerElementType_Operations();

        /**
         * The meta object literal for the '<em><b>States</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_ELEMENT_TYPE__STATES = eINSTANCE
                .getServerElementType_States();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.OperationImpl <em>Operation</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.OperationImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getOperation()
         * @generated
         */
        EClass OPERATION = eINSTANCE.getOperation();

        /**
         * The meta object literal for the '<em><b>To State</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OPERATION__TO_STATE = eINSTANCE.getOperation_ToState();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerElementStateImpl <em>Server Element State</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerElementStateImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerElementState()
         * @generated
         */
        EClass SERVER_ELEMENT_STATE = eINSTANCE.getServerElementState();

        /**
         * The meta object literal for the '<em><b>Possible Operations</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_ELEMENT_STATE__POSSIBLE_OPERATIONS = eINSTANCE
                .getServerElementState_PossibleOperations();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.WorkspaceModuleImpl <em>Workspace Module</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.WorkspaceModuleImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getWorkspaceModule()
         * @generated
         */
        EClass WORKSPACE_MODULE = eINSTANCE.getWorkspaceModule();

        /**
         * The meta object literal for the '<em><b>Workspace Src Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORKSPACE_MODULE__WORKSPACE_SRC_PATH = eINSTANCE
                .getWorkspaceModule_WorkspaceSrcPath();

        /**
         * The meta object literal for the '<em><b>Module Kind</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORKSPACE_MODULE__MODULE_KIND = eINSTANCE
                .getWorkspaceModule_ModuleKind();

        /**
         * The meta object literal for the '<em><b>Dirty</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORKSPACE_MODULE__DIRTY = eINSTANCE
                .getWorkspaceModule_Dirty();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.UniqueIdElementImpl <em>Unique Id Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.UniqueIdElementImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getUniqueIdElement()
         * @generated
         */
        EClass UNIQUE_ID_ELEMENT = eINSTANCE.getUniqueIdElement();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNIQUE_ID_ELEMENT__ID = eINSTANCE.getUniqueIdElement_Id();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerGroupImpl <em>Server Group</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerGroupImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerGroup()
         * @generated
         */
        EClass SERVER_GROUP = eINSTANCE.getServerGroup();

        /**
         * The meta object literal for the '<em><b>Members</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_GROUP__MEMBERS = eINSTANCE.getServerGroup_Members();

        /**
         * The meta object literal for the '<em><b>Server Group Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_GROUP__SERVER_GROUP_TYPE = eINSTANCE
                .getServerGroup_ServerGroupType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ServerGroupTypeImpl <em>Server Group Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ServerGroupTypeImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerGroupType()
         * @generated
         */
        EClass SERVER_GROUP_TYPE = eINSTANCE.getServerGroupType();

        /**
         * The meta object literal for the '<em><b>Server Types</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVER_GROUP_TYPE__SERVER_TYPES = eINSTANCE
                .getServerGroupType_ServerTypes();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SERVER_GROUP_TYPE__ID = eINSTANCE.getServerGroupType_Id();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.Loadable <em>Loadable</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.Loadable
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getLoadable()
         * @generated
         */
        EClass LOADABLE = eINSTANCE.getLoadable();

        /**
         * The meta object literal for the '<em><b>Valid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOADABLE__VALID = eINSTANCE.getLoadable_Valid();

        /**
         * The meta object literal for the '<em><b>Last Extension Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOADABLE__LAST_EXTENSION_ID = eINSTANCE
                .getLoadable_LastExtensionId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.impl.ParameterFacetImpl <em>Parameter Facet</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.impl.ParameterFacetImpl
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getParameterFacet()
         * @generated
         */
        EClass PARAMETER_FACET = eINSTANCE.getParameterFacet();

        /**
         * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_FACET__KEY = eINSTANCE.getParameterFacet_Key();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_FACET__VALUE = eINSTANCE.getParameterFacet_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.ServerState <em>Server State</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.ServerState
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getServerState()
         * @generated
         */
        EEnum SERVER_STATE = eINSTANCE.getServerState();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.ConfigParameterType <em>Config Parameter Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.ConfigParameterType
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getConfigParameterType()
         * @generated
         */
        EEnum CONFIG_PARAMETER_TYPE = eINSTANCE.getConfigParameterType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.deploy.DeploymentType <em>Deployment Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.DeploymentType
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getDeploymentType()
         * @generated
         */
        EEnum DEPLOYMENT_TYPE = eINSTANCE.getDeploymentType();

        /**
         * The meta object literal for the '<em>EConnection</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.model.extension.Connection
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getEConnection()
         * @generated
         */
        EDataType ECONNECTION = eINSTANCE.getEConnection();

        /**
         * The meta object literal for the '<em>EConnection Factory</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.model.extension.ConnectionFactory
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getEConnectionFactory()
         * @generated
         */
        EDataType ECONNECTION_FACTORY = eINSTANCE.getEConnectionFactory();

        /**
         * The meta object literal for the '<em>ERepository Publisher</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.model.extension.RepositoryPublisher
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getERepositoryPublisher()
         * @generated
         */
        EDataType EREPOSITORY_PUBLISHER = eINSTANCE.getERepositoryPublisher();

        /**
         * The meta object literal for the '<em>EDeployment Exception</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.deploy.model.extension.DeploymentException
         * @see com.tibco.xpd.deploy.impl.DeployPackageImpl#getEDeploymentException()
         * @generated
         */
        EDataType EDEPLOYMENT_EXCEPTION = eINSTANCE.getEDeploymentException();

    }

} //DeployPackage
