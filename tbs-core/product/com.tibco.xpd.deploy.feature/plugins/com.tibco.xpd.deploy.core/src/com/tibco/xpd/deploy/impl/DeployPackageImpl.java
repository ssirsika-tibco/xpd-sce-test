/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.ContainerElement;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.DeploymentType;
import com.tibco.xpd.deploy.LeafElement;
import com.tibco.xpd.deploy.Loadable;
import com.tibco.xpd.deploy.Module;
import com.tibco.xpd.deploy.ModuleContainer;
import com.tibco.xpd.deploy.NamedElement;
import com.tibco.xpd.deploy.Operation;
import com.tibco.xpd.deploy.ParameterFacet;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.RuntimeConfig;
import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerConfigInfo;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.ServerElementState;
import com.tibco.xpd.deploy.ServerElementType;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ServerModule;
import com.tibco.xpd.deploy.ServerState;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.UniqueIdElement;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionFactory;
import com.tibco.xpd.deploy.model.extension.DeploymentException;
import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class DeployPackageImpl extends EPackageImpl implements DeployPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverConfigEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass namedElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverConfigInfoEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass configParameterInfoEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass moduleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverModuleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass configParameterEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass repositoryTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass repositoryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass repositoryConfigEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass runtimeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass runtimeTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass runtimeConfigEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass containerElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass leafElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass moduleContainerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverElementTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass operationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass serverElementStateEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass workspaceModuleEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass uniqueIdElementEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass serverGroupEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass serverGroupTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass loadableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parameterFacetEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum serverStateEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum configParameterTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum deploymentTypeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType eConnectionEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType eConnectionFactoryEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType eRepositoryPublisherEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType eDeploymentExceptionEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
     * package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory
     * method {@link #init init()}, which also performs initialization of the
     * package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.deploy.model.DeployPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private DeployPackageImpl() {
        super(eNS_URI, DeployFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static DeployPackage init() {
        if (isInited)
            return (DeployPackage) EPackage.Registry.INSTANCE
                    .getEPackage(DeployPackage.eNS_URI);

        // Obtain or create and register package
        DeployPackageImpl theDeployPackage = (DeployPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(eNS_URI) instanceof DeployPackageImpl ? EPackage.Registry.INSTANCE
                .getEPackage(eNS_URI)
                : new DeployPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theDeployPackage.createPackageContents();

        // Initialize created meta-data
        theDeployPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theDeployPackage.freeze();

        return theDeployPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerConfig() {
        return serverConfigEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerConfig_ConfigParameters() {
        return (EReference) serverConfigEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getNamedElement() {
        return namedElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_Name() {
        return (EAttribute) namedElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedElement_Description() {
        return (EAttribute) namedElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerType() {
        return serverTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getServerType_Id() {
        return (EAttribute) serverTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerType_ServerConfigInfo() {
        return (EReference) serverTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getServerType_ConnectionFactory() {
        return (EAttribute) serverTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerType_RuntimeType() {
        return (EReference) serverTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerType_SupportedRepositoryTypes() {
        return (EReference) serverTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getServerType_SuppressConectivity() {
        return (EAttribute) serverTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerConfigInfo() {
        return serverConfigInfoEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerConfigInfo_ConfigParameterInfos() {
        return (EReference) serverConfigInfoEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getConfigParameterInfo() {
        return configParameterInfoEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameterInfo_Key() {
        return (EAttribute) configParameterInfoEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameterInfo_Label() {
        return (EAttribute) configParameterInfoEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameterInfo_Icon() {
        return (EAttribute) configParameterInfoEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameterInfo_ParameterType() {
        return (EAttribute) configParameterInfoEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameterInfo_DefaultValue() {
        return (EAttribute) configParameterInfoEClass.getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameterInfo_Required() {
        return (EAttribute) configParameterInfoEClass.getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameterInfo_Automatic() {
        return (EAttribute) configParameterInfoEClass.getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getConfigParameterInfo_Facets() {
        return (EReference) configParameterInfoEClass.getEStructuralFeatures()
                .get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getModule() {
        return moduleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerModule() {
        return serverModuleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServer() {
        return serverEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServer_ServerType() {
        return (EReference) serverEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServer_ServerConfig() {
        return (EReference) serverEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getServer_ServerState() {
        return (EAttribute) serverEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getServer_Connection() {
        return (EAttribute) serverEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServer_Repository() {
        return (EReference) serverEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServer_Runtime() {
        return (EReference) serverEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServer_ServerElements() {
        return (EReference) serverEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServer_WorkspaceModules() {
        return (EReference) serverEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getServer_ServerGroup() {
        return (EReference) serverEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerContainer() {
        return serverContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerContainer_Servers() {
        return (EReference) serverContainerEClass.getEStructuralFeatures().get(
                0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerContainer_Runtimes() {
        return (EReference) serverContainerEClass.getEStructuralFeatures().get(
                1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerContainer_ServerTypes() {
        return (EReference) serverContainerEClass.getEStructuralFeatures().get(
                2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerContainer_RepositoryTypes() {
        return (EReference) serverContainerEClass.getEStructuralFeatures().get(
                3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerContainer_RuntimeTypes() {
        return (EReference) serverContainerEClass.getEStructuralFeatures().get(
                4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerContainer_ServerGroupTypes() {
        return (EReference) serverContainerEClass.getEStructuralFeatures().get(
                5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerContainer_ServerGroups() {
        return (EReference) serverContainerEClass.getEStructuralFeatures().get(
                6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getConfigParameter() {
        return configParameterEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameter_Key() {
        return (EAttribute) configParameterEClass.getEStructuralFeatures().get(
                0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getConfigParameter_Value() {
        return (EAttribute) configParameterEClass.getEStructuralFeatures().get(
                1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getConfigParameter_ConfigParameterInfo() {
        return (EReference) configParameterEClass.getEStructuralFeatures().get(
                2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRepositoryType() {
        return repositoryTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRepositoryType_Id() {
        return (EAttribute) repositoryTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRepositoryType_RepositoryParameterInfos() {
        return (EReference) repositoryTypeEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRepositoryType_RepositoryPublisher() {
        return (EAttribute) repositoryTypeEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRepository() {
        return repositoryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRepository_RepositoryType() {
        return (EReference) repositoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRepository_RepositoryConfig() {
        return (EReference) repositoryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRepositoryConfig() {
        return repositoryConfigEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRepositoryConfig_ConfigParameters() {
        return (EReference) repositoryConfigEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRuntime() {
        return runtimeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRuntime_RuntimeType() {
        return (EReference) runtimeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRuntime_RuntimeConfig() {
        return (EReference) runtimeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRuntimeType() {
        return runtimeTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRuntimeType_Id() {
        return (EAttribute) runtimeTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRuntimeType_RuntimeParameterInfos() {
        return (EReference) runtimeTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRuntimeConfig() {
        return runtimeConfigEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRuntimeConfig_ConfigParameters() {
        return (EReference) runtimeConfigEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerElement() {
        return serverElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerElement_ServerElementType() {
        return (EReference) serverElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerElement_ServerElementState() {
        return (EReference) serverElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerElement_Parameters() {
        return (EReference) serverElementEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getContainerElement() {
        return containerElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getContainerElement_Children() {
        return (EReference) containerElementEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLeafElement() {
        return leafElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getModuleContainer() {
        return moduleContainerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerElementType() {
        return serverElementTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerElementType_Operations() {
        return (EReference) serverElementTypeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerElementType_States() {
        return (EReference) serverElementTypeEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getOperation() {
        return operationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getOperation_ToState() {
        return (EReference) operationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerElementState() {
        return serverElementStateEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerElementState_PossibleOperations() {
        return (EReference) serverElementStateEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkspaceModule() {
        return workspaceModuleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkspaceModule_WorkspaceSrcPath() {
        return (EAttribute) workspaceModuleEClass.getEStructuralFeatures().get(
                0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkspaceModule_ModuleKind() {
        return (EAttribute) workspaceModuleEClass.getEStructuralFeatures().get(
                1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkspaceModule_Dirty() {
        return (EAttribute) workspaceModuleEClass.getEStructuralFeatures().get(
                2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUniqueIdElement() {
        return uniqueIdElementEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUniqueIdElement_Id() {
        return (EAttribute) uniqueIdElementEClass.getEStructuralFeatures().get(
                0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerGroup() {
        return serverGroupEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerGroup_Members() {
        return (EReference) serverGroupEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerGroup_ServerGroupType() {
        return (EReference) serverGroupEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getServerGroupType() {
        return serverGroupTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getServerGroupType_ServerTypes() {
        return (EReference) serverGroupTypeEClass.getEStructuralFeatures().get(
                0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getServerGroupType_Id() {
        return (EAttribute) serverGroupTypeEClass.getEStructuralFeatures().get(
                1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLoadable() {
        return loadableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoadable_Valid() {
        return (EAttribute) loadableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLoadable_LastExtensionId() {
        return (EAttribute) loadableEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterFacet() {
        return parameterFacetEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterFacet_Key() {
        return (EAttribute) parameterFacetEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterFacet_Value() {
        return (EAttribute) parameterFacetEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getServerState() {
        return serverStateEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getConfigParameterType() {
        return configParameterTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getDeploymentType() {
        return deploymentTypeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getEConnection() {
        return eConnectionEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getEConnectionFactory() {
        return eConnectionFactoryEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getERepositoryPublisher() {
        return eRepositoryPublisherEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getEDeploymentException() {
        return eDeploymentExceptionEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DeployFactory getDeployFactory() {
        return (DeployFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
        serverConfigEClass = createEClass(SERVER_CONFIG);
        createEReference(serverConfigEClass, SERVER_CONFIG__CONFIG_PARAMETERS);

        configParameterEClass = createEClass(CONFIG_PARAMETER);
        createEAttribute(configParameterEClass, CONFIG_PARAMETER__KEY);
        createEAttribute(configParameterEClass, CONFIG_PARAMETER__VALUE);
        createEReference(configParameterEClass,
                CONFIG_PARAMETER__CONFIG_PARAMETER_INFO);

        namedElementEClass = createEClass(NAMED_ELEMENT);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);
        createEAttribute(namedElementEClass, NAMED_ELEMENT__DESCRIPTION);

        serverTypeEClass = createEClass(SERVER_TYPE);
        createEAttribute(serverTypeEClass, SERVER_TYPE__ID);
        createEReference(serverTypeEClass, SERVER_TYPE__SERVER_CONFIG_INFO);
        createEAttribute(serverTypeEClass, SERVER_TYPE__CONNECTION_FACTORY);
        createEReference(serverTypeEClass, SERVER_TYPE__RUNTIME_TYPE);
        createEReference(serverTypeEClass,
                SERVER_TYPE__SUPPORTED_REPOSITORY_TYPES);
        createEAttribute(serverTypeEClass, SERVER_TYPE__SUPPRESS_CONECTIVITY);

        serverConfigInfoEClass = createEClass(SERVER_CONFIG_INFO);
        createEReference(serverConfigInfoEClass,
                SERVER_CONFIG_INFO__CONFIG_PARAMETER_INFOS);

        configParameterInfoEClass = createEClass(CONFIG_PARAMETER_INFO);
        createEAttribute(configParameterInfoEClass, CONFIG_PARAMETER_INFO__KEY);
        createEAttribute(configParameterInfoEClass,
                CONFIG_PARAMETER_INFO__LABEL);
        createEAttribute(configParameterInfoEClass, CONFIG_PARAMETER_INFO__ICON);
        createEAttribute(configParameterInfoEClass,
                CONFIG_PARAMETER_INFO__PARAMETER_TYPE);
        createEAttribute(configParameterInfoEClass,
                CONFIG_PARAMETER_INFO__DEFAULT_VALUE);
        createEAttribute(configParameterInfoEClass,
                CONFIG_PARAMETER_INFO__REQUIRED);
        createEAttribute(configParameterInfoEClass,
                CONFIG_PARAMETER_INFO__AUTOMATIC);
        createEReference(configParameterInfoEClass,
                CONFIG_PARAMETER_INFO__FACETS);

        moduleEClass = createEClass(MODULE);

        serverModuleEClass = createEClass(SERVER_MODULE);

        serverEClass = createEClass(SERVER);
        createEReference(serverEClass, SERVER__SERVER_TYPE);
        createEReference(serverEClass, SERVER__SERVER_CONFIG);
        createEAttribute(serverEClass, SERVER__SERVER_STATE);
        createEAttribute(serverEClass, SERVER__CONNECTION);
        createEReference(serverEClass, SERVER__REPOSITORY);
        createEReference(serverEClass, SERVER__RUNTIME);
        createEReference(serverEClass, SERVER__SERVER_ELEMENTS);
        createEReference(serverEClass, SERVER__WORKSPACE_MODULES);
        createEReference(serverEClass, SERVER__SERVER_GROUP);

        serverElementEClass = createEClass(SERVER_ELEMENT);
        createEReference(serverElementEClass,
                SERVER_ELEMENT__SERVER_ELEMENT_TYPE);
        createEReference(serverElementEClass,
                SERVER_ELEMENT__SERVER_ELEMENT_STATE);
        createEReference(serverElementEClass, SERVER_ELEMENT__PARAMETERS);

        serverContainerEClass = createEClass(SERVER_CONTAINER);
        createEReference(serverContainerEClass, SERVER_CONTAINER__SERVERS);
        createEReference(serverContainerEClass, SERVER_CONTAINER__RUNTIMES);
        createEReference(serverContainerEClass, SERVER_CONTAINER__SERVER_TYPES);
        createEReference(serverContainerEClass,
                SERVER_CONTAINER__REPOSITORY_TYPES);
        createEReference(serverContainerEClass, SERVER_CONTAINER__RUNTIME_TYPES);
        createEReference(serverContainerEClass,
                SERVER_CONTAINER__SERVER_GROUP_TYPES);
        createEReference(serverContainerEClass, SERVER_CONTAINER__SERVER_GROUPS);

        repositoryTypeEClass = createEClass(REPOSITORY_TYPE);
        createEAttribute(repositoryTypeEClass, REPOSITORY_TYPE__ID);
        createEReference(repositoryTypeEClass,
                REPOSITORY_TYPE__REPOSITORY_PARAMETER_INFOS);
        createEAttribute(repositoryTypeEClass,
                REPOSITORY_TYPE__REPOSITORY_PUBLISHER);

        repositoryEClass = createEClass(REPOSITORY);
        createEReference(repositoryEClass, REPOSITORY__REPOSITORY_TYPE);
        createEReference(repositoryEClass, REPOSITORY__REPOSITORY_CONFIG);

        repositoryConfigEClass = createEClass(REPOSITORY_CONFIG);
        createEReference(repositoryConfigEClass,
                REPOSITORY_CONFIG__CONFIG_PARAMETERS);

        runtimeEClass = createEClass(RUNTIME);
        createEReference(runtimeEClass, RUNTIME__RUNTIME_TYPE);
        createEReference(runtimeEClass, RUNTIME__RUNTIME_CONFIG);

        runtimeTypeEClass = createEClass(RUNTIME_TYPE);
        createEAttribute(runtimeTypeEClass, RUNTIME_TYPE__ID);
        createEReference(runtimeTypeEClass,
                RUNTIME_TYPE__RUNTIME_PARAMETER_INFOS);

        runtimeConfigEClass = createEClass(RUNTIME_CONFIG);
        createEReference(runtimeConfigEClass, RUNTIME_CONFIG__CONFIG_PARAMETERS);

        containerElementEClass = createEClass(CONTAINER_ELEMENT);
        createEReference(containerElementEClass, CONTAINER_ELEMENT__CHILDREN);

        leafElementEClass = createEClass(LEAF_ELEMENT);

        moduleContainerEClass = createEClass(MODULE_CONTAINER);

        serverElementTypeEClass = createEClass(SERVER_ELEMENT_TYPE);
        createEReference(serverElementTypeEClass,
                SERVER_ELEMENT_TYPE__OPERATIONS);
        createEReference(serverElementTypeEClass, SERVER_ELEMENT_TYPE__STATES);

        operationEClass = createEClass(OPERATION);
        createEReference(operationEClass, OPERATION__TO_STATE);

        serverElementStateEClass = createEClass(SERVER_ELEMENT_STATE);
        createEReference(serverElementStateEClass,
                SERVER_ELEMENT_STATE__POSSIBLE_OPERATIONS);

        workspaceModuleEClass = createEClass(WORKSPACE_MODULE);
        createEAttribute(workspaceModuleEClass,
                WORKSPACE_MODULE__WORKSPACE_SRC_PATH);
        createEAttribute(workspaceModuleEClass, WORKSPACE_MODULE__MODULE_KIND);
        createEAttribute(workspaceModuleEClass, WORKSPACE_MODULE__DIRTY);

        uniqueIdElementEClass = createEClass(UNIQUE_ID_ELEMENT);
        createEAttribute(uniqueIdElementEClass, UNIQUE_ID_ELEMENT__ID);

        serverGroupEClass = createEClass(SERVER_GROUP);
        createEReference(serverGroupEClass, SERVER_GROUP__MEMBERS);
        createEReference(serverGroupEClass, SERVER_GROUP__SERVER_GROUP_TYPE);

        serverGroupTypeEClass = createEClass(SERVER_GROUP_TYPE);
        createEReference(serverGroupTypeEClass, SERVER_GROUP_TYPE__SERVER_TYPES);
        createEAttribute(serverGroupTypeEClass, SERVER_GROUP_TYPE__ID);

        loadableEClass = createEClass(LOADABLE);
        createEAttribute(loadableEClass, LOADABLE__VALID);
        createEAttribute(loadableEClass, LOADABLE__LAST_EXTENSION_ID);

        parameterFacetEClass = createEClass(PARAMETER_FACET);
        createEAttribute(parameterFacetEClass, PARAMETER_FACET__KEY);
        createEAttribute(parameterFacetEClass, PARAMETER_FACET__VALUE);

        // Create enums
        serverStateEEnum = createEEnum(SERVER_STATE);
        configParameterTypeEEnum = createEEnum(CONFIG_PARAMETER_TYPE);
        deploymentTypeEEnum = createEEnum(DEPLOYMENT_TYPE);

        // Create data types
        eConnectionEDataType = createEDataType(ECONNECTION);
        eConnectionFactoryEDataType = createEDataType(ECONNECTION_FACTORY);
        eRepositoryPublisherEDataType = createEDataType(EREPOSITORY_PUBLISHER);
        eDeploymentExceptionEDataType = createEDataType(EDEPLOYMENT_EXCEPTION);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This
     * method is guarded to have no affect on any invocation but its first. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
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

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        serverConfigEClass.getESuperTypes().add(this.getNamedElement());
        serverTypeEClass.getESuperTypes().add(this.getNamedElement());
        serverTypeEClass.getESuperTypes().add(this.getLoadable());
        serverConfigInfoEClass.getESuperTypes().add(this.getNamedElement());
        configParameterInfoEClass.getESuperTypes().add(this.getNamedElement());
        moduleEClass.getESuperTypes().add(this.getNamedElement());
        serverModuleEClass.getESuperTypes().add(this.getModule());
        serverModuleEClass.getESuperTypes().add(this.getLeafElement());
        serverEClass.getESuperTypes().add(this.getUniqueIdElement());
        serverEClass.getESuperTypes().add(this.getNamedElement());
        serverElementEClass.getESuperTypes().add(this.getNamedElement());
        repositoryTypeEClass.getESuperTypes().add(this.getNamedElement());
        repositoryTypeEClass.getESuperTypes().add(this.getLoadable());
        runtimeEClass.getESuperTypes().add(this.getNamedElement());
        runtimeTypeEClass.getESuperTypes().add(this.getNamedElement());
        runtimeTypeEClass.getESuperTypes().add(this.getLoadable());
        containerElementEClass.getESuperTypes().add(this.getServerElement());
        leafElementEClass.getESuperTypes().add(this.getServerElement());
        moduleContainerEClass.getESuperTypes().add(this.getContainerElement());
        operationEClass.getESuperTypes().add(this.getNamedElement());
        serverElementStateEClass.getESuperTypes().add(this.getNamedElement());
        workspaceModuleEClass.getESuperTypes().add(this.getModule());
        serverGroupEClass.getESuperTypes().add(this.getNamedElement());
        serverGroupTypeEClass.getESuperTypes().add(this.getNamedElement());
        serverGroupTypeEClass.getESuperTypes().add(this.getLoadable());

        // Initialize classes and features; add operations and parameters
        initEClass(serverConfigEClass, ServerConfig.class, "ServerConfig",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServerConfig_ConfigParameters(), this
                .getConfigParameter(), null, "configParameters", null, 0, -1,
                ServerConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        EOperation op = addEOperation(serverConfigEClass, this
                .getConfigParameter(), "getConfigParameter", 0, 1, IS_UNIQUE,
                IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        initEClass(configParameterEClass, ConfigParameter.class,
                "ConfigParameter", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getConfigParameter_Key(), ecorePackage.getEString(),
                "key", null, 0, 1, ConfigParameter.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfigParameter_Value(), ecorePackage.getEString(),
                "value", "", 0, 1, ConfigParameter.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEReference(getConfigParameter_ConfigParameterInfo(), this
                .getConfigParameterInfo(), null, "configParameterInfo", null,
                0, 1, ConfigParameter.class, IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(namedElementEClass, NamedElement.class, "NamedElement",
                IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getNamedElement_Name(), ecorePackage.getEString(),
                "name", "", 0, 1, NamedElement.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNamedElement_Description(),
                ecorePackage.getEString(), "description", null, 0, 1,
                NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(serverTypeEClass, ServerType.class, "ServerType",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getServerType_Id(), ecorePackage.getEString(), "id",
                null, 0, 1, ServerType.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getServerType_ServerConfigInfo(), this
                .getServerConfigInfo(), null, "serverConfigInfo", null, 0, 1,
                ServerType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEAttribute(getServerType_ConnectionFactory(), this
                .getEConnectionFactory(), "connectionFactory", null, 0, 1,
                ServerType.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getServerType_RuntimeType(), this.getRuntimeType(),
                null, "runtimeType", null, 0, 1, ServerType.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getServerType_SupportedRepositoryTypes(), this
                .getRepositoryType(), null, "supportedRepositoryTypes", null,
                0, -1, ServerType.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getServerType_SuppressConectivity(), ecorePackage
                .getEBoolean(), "suppressConectivity", "false", 1, 1,
                ServerType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(serverConfigInfoEClass, ServerConfigInfo.class,
                "ServerConfigInfo", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServerConfigInfo_ConfigParameterInfos(), this
                .getConfigParameterInfo(), null, "configParameterInfos", null,
                0, -1, ServerConfigInfo.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

        initEClass(configParameterInfoEClass, ConfigParameterInfo.class,
                "ConfigParameterInfo", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getConfigParameterInfo_Key(), ecorePackage.getEString(),
                "key", "", 1, 1, ConfigParameterInfo.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfigParameterInfo_Label(), ecorePackage
                .getEString(), "label", "", 0, 1, ConfigParameterInfo.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getConfigParameterInfo_Icon(),
                ecorePackage.getEString(), "icon", "", 0, 1,
                ConfigParameterInfo.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConfigParameterInfo_ParameterType(), this
                .getConfigParameterType(), "parameterType", "", 0, 1,
                ConfigParameterInfo.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConfigParameterInfo_DefaultValue(), ecorePackage
                .getEString(), "defaultValue", "", 0, 1,
                ConfigParameterInfo.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConfigParameterInfo_Required(), ecorePackage
                .getEBoolean(), "required", "false", 1, 1,
                ConfigParameterInfo.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getConfigParameterInfo_Automatic(), ecorePackage
                .getEBoolean(), "automatic", "true", 1, 1,
                ConfigParameterInfo.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getConfigParameterInfo_Facets(), this
                .getParameterFacet(), null, "facets", null, 0, -1,
                ConfigParameterInfo.class, IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        op = addEOperation(configParameterInfoEClass,
                ecorePackage.getEString(), "getFacet", 0, 1, IS_UNIQUE,
                IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        op = addEOperation(configParameterInfoEClass, null, "getFacetsMap", 0,
                1, IS_UNIQUE, IS_ORDERED);
        EGenericType g1 = createEGenericType(ecorePackage.getEMap());
        EGenericType g2 = createEGenericType(ecorePackage.getEString());
        g1.getETypeArguments().add(g2);
        g2 = createEGenericType(ecorePackage.getEString());
        g1.getETypeArguments().add(g2);
        initEOperation(op, g1);

        initEClass(moduleEClass, Module.class, "Module", IS_ABSTRACT,
                IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(serverModuleEClass, ServerModule.class, "ServerModule",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(serverEClass, Server.class, "Server", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServer_ServerType(), this.getServerType(), null,
                "serverType", null, 0, 1, Server.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServer_ServerConfig(), this.getServerConfig(), null,
                "serverConfig", null, 0, 1, Server.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getServer_ServerState(), this.getServerState(),
                "serverState", null, 0, 1, Server.class, IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEAttribute(getServer_Connection(), this.getEConnection(),
                "connection", null, 0, 1, Server.class, IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                IS_DERIVED, IS_ORDERED);
        initEReference(getServer_Repository(), this.getRepository(), null,
                "repository", null, 0, 1, Server.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServer_Runtime(), this.getRuntime(), null, "runtime",
                null, 0, 1, Server.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServer_ServerElements(), this.getServerElement(),
                null, "serverElements", null, 0, -1, Server.class,
                IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED,
                IS_ORDERED);
        initEReference(getServer_WorkspaceModules(), this.getWorkspaceModule(),
                null, "workspaceModules", null, 0, -1, Server.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getServer_ServerGroup(), this.getServerGroup(), this
                .getServerGroup_Members(), "serverGroup", null, 0, 1,
                Server.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        initEClass(serverElementEClass, ServerElement.class, "ServerElement",
                IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServerElement_ServerElementType(), this
                .getServerElementType(), null, "serverElementType", null, 0, 1,
                ServerElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEReference(getServerElement_ServerElementState(), this
                .getServerElementState(), null, "serverElementState", null, 0,
                1, ServerElement.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServerElement_Parameters(),
                this.getConfigParameter(), null, "parameters", null, 0, -1,
                ServerElement.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        op = addEOperation(serverElementEClass, this.getConfigParameter(),
                "getConfigParameter", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        initEClass(serverContainerEClass, ServerContainer.class,
                "ServerContainer", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServerContainer_Servers(), this.getServer(), null,
                "servers", null, 0, -1, ServerContainer.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServerContainer_Runtimes(), this.getRuntime(), null,
                "runtimes", null, 0, -1, ServerContainer.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServerContainer_ServerTypes(), this.getServerType(),
                null, "serverTypes", null, 0, -1, ServerContainer.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getServerContainer_RepositoryTypes(), this
                .getRepositoryType(), null, "repositoryTypes", null, 0, -1,
                ServerContainer.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServerContainer_RuntimeTypes(),
                this.getRuntimeType(), null, "runtimeTypes", null, 0, -1,
                ServerContainer.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServerContainer_ServerGroupTypes(), this
                .getServerGroupType(), null, "serverGroupTypes", null, 0, -1,
                ServerContainer.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServerContainer_ServerGroups(),
                this.getServerGroup(), null, "serverGroups", null, 0, -1,
                ServerContainer.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        op = addEOperation(serverContainerEClass, this.getRepositoryType(),
                "getRepositoryTypeById", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "repositoryTypeId", 0, 1,
                IS_UNIQUE, IS_ORDERED);

        op = addEOperation(serverContainerEClass, this.getServerType(),
                "getServerTypeById", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "serverTypeId", 0, 1,
                IS_UNIQUE, IS_ORDERED);

        op = addEOperation(serverContainerEClass, this.getRuntimeType(),
                "getRuntimeTypeById", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "runtimeTypeId", 0, 1,
                IS_UNIQUE, IS_ORDERED);

        op = addEOperation(serverContainerEClass, ecorePackage.getEString(),
                "getDefaultNewRuntimeName", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "prefix", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        op = addEOperation(serverContainerEClass, ecorePackage.getEString(),
                "getDefaultNewServerName", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "prefix", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        op = addEOperation(serverContainerEClass, ecorePackage.getEBoolean(),
                "isValidNewRuntimeName", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        op = addEOperation(serverContainerEClass, ecorePackage.getEBoolean(),
                "isValidNewServerName", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        op = addEOperation(serverContainerEClass, this.getServerGroupType(),
                "getServerGroupTypeById", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "serverGroupTypeId", 0, 1,
                IS_UNIQUE, IS_ORDERED);

        initEClass(repositoryTypeEClass, RepositoryType.class,
                "RepositoryType", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRepositoryType_Id(), ecorePackage.getEString(), "id",
                null, 0, 1, RepositoryType.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getRepositoryType_RepositoryParameterInfos(), this
                .getConfigParameterInfo(), null, "repositoryParameterInfos",
                null, 0, -1, RepositoryType.class, IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRepositoryType_RepositoryPublisher(), this
                .getERepositoryPublisher(), "repositoryPublisher", null, 0, 1,
                RepositoryType.class, IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);

        initEClass(repositoryEClass, Repository.class, "Repository",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRepository_RepositoryType(),
                this.getRepositoryType(), null, "repositoryType", null, 0, 1,
                Repository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEReference(getRepository_RepositoryConfig(), this
                .getRepositoryConfig(), null, "repositoryConfig", null, 0, 1,
                Repository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        initEClass(repositoryConfigEClass, RepositoryConfig.class,
                "RepositoryConfig", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRepositoryConfig_ConfigParameters(), this
                .getConfigParameter(), null, "configParameters", null, 0, -1,
                RepositoryConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        op = addEOperation(repositoryConfigEClass, this.getConfigParameter(),
                "getConfigParameter", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        initEClass(runtimeEClass, com.tibco.xpd.deploy.Runtime.class,
                "Runtime", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRuntime_RuntimeType(), this.getRuntimeType(), null,
                "runtimeType", null, 0, 1, com.tibco.xpd.deploy.Runtime.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getRuntime_RuntimeConfig(), this.getRuntimeConfig(),
                null, "runtimeConfig", null, 0, 1,
                com.tibco.xpd.deploy.Runtime.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(runtimeTypeEClass, RuntimeType.class, "RuntimeType",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRuntimeType_Id(), ecorePackage.getEString(), "id",
                null, 0, 1, RuntimeType.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getRuntimeType_RuntimeParameterInfos(), this
                .getConfigParameterInfo(), null, "runtimeParameterInfos", null,
                0, -1, RuntimeType.class, IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(runtimeConfigEClass, RuntimeConfig.class, "RuntimeConfig",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getRuntimeConfig_ConfigParameters(), this
                .getConfigParameter(), null, "configParameters", null, 0, -1,
                RuntimeConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        op = addEOperation(runtimeConfigEClass, this.getConfigParameter(),
                "getConfigParameter", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE,
                IS_ORDERED);

        initEClass(containerElementEClass, ContainerElement.class,
                "ContainerElement", IS_ABSTRACT, IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getContainerElement_Children(), this.getServerElement(),
                null, "children", null, 0, -1, ContainerElement.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);

        initEClass(leafElementEClass, LeafElement.class, "LeafElement",
                IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(moduleContainerEClass, ModuleContainer.class,
                "ModuleContainer", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        initEClass(serverElementTypeEClass, ServerElementType.class,
                "ServerElementType", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServerElementType_Operations(), this.getOperation(),
                null, "operations", null, 0, -1, ServerElementType.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEReference(getServerElementType_States(), this
                .getServerElementState(), null, "states", null, 0, -1,
                ServerElementType.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(operationEClass, Operation.class, "Operation", IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getOperation_ToState(), this.getServerElementState(),
                null, "toState", null, 0, 1, Operation.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        op = addEOperation(operationEClass, ecorePackage.getEBoolean(),
                "canExecute", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getServerElement(), "serverElement", 0, 1,
                IS_UNIQUE, IS_ORDERED);

        op = addEOperation(operationEClass, ecorePackage.getEJavaObject(),
                "execute", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getServerElement(), "serverElement", 0, 1,
                IS_UNIQUE, IS_ORDERED);
        addEException(op, this.getEDeploymentException());

        initEClass(serverElementStateEClass, ServerElementState.class,
                "ServerElementState", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServerElementState_PossibleOperations(), this
                .getOperation(), null, "possibleOperations", null, 0, -1,
                ServerElementState.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workspaceModuleEClass, WorkspaceModule.class,
                "WorkspaceModule", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkspaceModule_WorkspaceSrcPath(), ecorePackage
                .getEString(), "workspaceSrcPath", "", 1, 1,
                WorkspaceModule.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getWorkspaceModule_ModuleKind(), ecorePackage
                .getEString(), "moduleKind", "", 0, 1, WorkspaceModule.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkspaceModule_Dirty(), ecorePackage.getEBoolean(),
                "dirty", null, 0, 1, WorkspaceModule.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        initEClass(uniqueIdElementEClass, UniqueIdElement.class,
                "UniqueIdElement", IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUniqueIdElement_Id(), ecorePackage.getEString(),
                "id", "", 1, 1, UniqueIdElement.class, !IS_TRANSIENT,
                !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        initEClass(serverGroupEClass, ServerGroup.class, "ServerGroup",
                !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServerGroup_Members(), this.getServer(), this
                .getServer_ServerGroup(), "members", null, 0, -1,
                ServerGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEReference(getServerGroup_ServerGroupType(), this
                .getServerGroupType(), null, "serverGroupType", null, 1, 1,
                ServerGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        initEClass(serverGroupTypeEClass, ServerGroupType.class,
                "ServerGroupType", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServerGroupType_ServerTypes(), this.getServerType(),
                null, "serverTypes", null, 0, -1, ServerGroupType.class,
                IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getServerGroupType_Id(), ecorePackage.getEString(),
                "id", null, 0, 1, ServerGroupType.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        initEClass(loadableEClass, Loadable.class, "Loadable", IS_ABSTRACT,
                IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getLoadable_Valid(), ecorePackage.getEBoolean(),
                "valid", "false", 1, 1, Loadable.class, IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEAttribute(getLoadable_LastExtensionId(),
                ecorePackage.getEString(), "lastExtensionId", null, 0, 1,
                Loadable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(parameterFacetEClass, ParameterFacet.class,
                "ParameterFacet", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getParameterFacet_Key(), ecorePackage.getEString(),
                "key", null, 0, 1, ParameterFacet.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        initEAttribute(getParameterFacet_Value(), ecorePackage.getEString(),
                "value", null, 0, 1, ParameterFacet.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(serverStateEEnum, ServerState.class, "ServerState");
        addEEnumLiteral(serverStateEEnum, ServerState.DISCONNECTED_LITERAL);
        addEEnumLiteral(serverStateEEnum, ServerState.CONNECTED_LITERAL);

        initEEnum(configParameterTypeEEnum, ConfigParameterType.class,
                "ConfigParameterType");
        addEEnumLiteral(configParameterTypeEEnum,
                ConfigParameterType.STRING_LITERAL);
        addEEnumLiteral(configParameterTypeEEnum,
                ConfigParameterType.BOOLEAN_LITERAL);
        addEEnumLiteral(configParameterTypeEEnum,
                ConfigParameterType.INTEGER_LITERAL);
        addEEnumLiteral(configParameterTypeEEnum,
                ConfigParameterType.PASSWORD_LITERAL);
        addEEnumLiteral(configParameterTypeEEnum,
                ConfigParameterType.FILE_LITERAL);

        initEEnum(deploymentTypeEEnum, DeploymentType.class, "DeploymentType");
        addEEnumLiteral(deploymentTypeEEnum, DeploymentType.ON_REQUEST_LITERAL);
        addEEnumLiteral(deploymentTypeEEnum,
                DeploymentType.AUTOMATICALLY_LITERAL);

        // Initialize data types
        initEDataType(eConnectionEDataType, Connection.class, "EConnection",
                !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(eConnectionFactoryEDataType, ConnectionFactory.class,
                "EConnectionFactory", !IS_SERIALIZABLE,
                !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(eRepositoryPublisherEDataType, RepositoryPublisher.class,
                "ERepositoryPublisher", IS_SERIALIZABLE,
                !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(eDeploymentExceptionEDataType, DeploymentException.class,
                "EDeploymentException", IS_SERIALIZABLE,
                !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // null
        createNullAnnotations();
    }

    /**
     * Initializes the annotations for <b>null</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createNullAnnotations() {
        String source = null;
        addAnnotation(repositoryConfigEClass, source, new String[] {});
    }

} // DeployPackageImpl
