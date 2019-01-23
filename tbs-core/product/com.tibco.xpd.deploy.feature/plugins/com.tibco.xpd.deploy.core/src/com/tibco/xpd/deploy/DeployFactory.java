/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.deploy.DeployPackage
 * @generated
 */
public interface DeployFactory extends EFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DeployFactory eINSTANCE = com.tibco.xpd.deploy.impl.DeployFactoryImpl
            .init();

    /**
     * Returns a new object of class '<em>Server Config</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Config</em>'.
     * @generated
     */
    ServerConfig createServerConfig();

    /**
     * Returns a new object of class '<em>Server Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Type</em>'.
     * @generated
     */
    ServerType createServerType();

    /**
     * Returns a new object of class '<em>Server Config Info</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Config Info</em>'.
     * @generated
     */
    ServerConfigInfo createServerConfigInfo();

    /**
     * Returns a new object of class '<em>Config Parameter Info</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Config Parameter Info</em>'.
     * @generated
     */
    ConfigParameterInfo createConfigParameterInfo();

    /**
     * Returns a new object of class '<em>Server Module</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Module</em>'.
     * @generated
     */
    ServerModule createServerModule();

    /**
     * Returns a new object of class '<em>Server</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server</em>'.
     * @generated
     */
    Server createServer();

    /**
     * Returns a new object of class '<em>Server Container</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Container</em>'.
     * @generated
     */
    ServerContainer createServerContainer();

    /**
     * Returns a new object of class '<em>Config Parameter</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Config Parameter</em>'.
     * @generated
     */
    ConfigParameter createConfigParameter();

    /**
     * Returns a new object of class '<em>Repository Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Repository Type</em>'.
     * @generated
     */
    RepositoryType createRepositoryType();

    /**
     * Returns a new object of class '<em>Repository</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Repository</em>'.
     * @generated
     */
    Repository createRepository();

    /**
     * Returns a new object of class '<em>Repository Config</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Repository Config</em>'.
     * @generated
     */
    RepositoryConfig createRepositoryConfig();

    /**
     * Returns a new object of class '<em>Runtime</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Runtime</em>'.
     * @generated
     */
    com.tibco.xpd.deploy.Runtime createRuntime();

    /**
     * Returns a new object of class '<em>Runtime Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Runtime Type</em>'.
     * @generated
     */
    RuntimeType createRuntimeType();

    /**
     * Returns a new object of class '<em>Runtime Config</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Runtime Config</em>'.
     * @generated
     */
    RuntimeConfig createRuntimeConfig();

    /**
     * Returns a new object of class '<em>Module Container</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Module Container</em>'.
     * @generated
     */
    ModuleContainer createModuleContainer();

    /**
     * Returns a new object of class '<em>Server Element Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Element Type</em>'.
     * @generated
     */
    ServerElementType createServerElementType();

    /**
     * Returns a new object of class '<em>Server Element State</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Element State</em>'.
     * @generated
     */
    ServerElementState createServerElementState();

    /**
     * Returns a new object of class '<em>Workspace Module</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Workspace Module</em>'.
     * @generated
     */
    WorkspaceModule createWorkspaceModule();

    /**
     * Returns a new object of class '<em>Server Group</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Group</em>'.
     * @generated
     */
    ServerGroup createServerGroup();

    /**
     * Returns a new object of class '<em>Server Group Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Server Group Type</em>'.
     * @generated
     */
    ServerGroupType createServerGroupType();

    /**
     * Returns a new object of class '<em>Parameter Facet</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Parameter Facet</em>'.
     * @generated
     */
    ParameterFacet createParameterFacet();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    DeployPackage getDeployPackage();

} //DeployFactory
