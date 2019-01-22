/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Workspace Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.WorkspaceModule#getWorkspaceSrcPath <em>Workspace Src Path</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.WorkspaceModule#getModuleKind <em>Module Kind</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.WorkspaceModule#isDirty <em>Dirty</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getWorkspaceModule()
 * @model
 * @generated
 */
public interface WorkspaceModule extends Module {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Workspace Src Path</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Workspace Src Path</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Workspace Src Path</em>' attribute.
     * @see #setWorkspaceSrcPath(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getWorkspaceModule_WorkspaceSrcPath()
     * @model default="" required="true"
     * @generated
     */
    String getWorkspaceSrcPath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.WorkspaceModule#getWorkspaceSrcPath <em>Workspace Src Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Workspace Src Path</em>' attribute.
     * @see #getWorkspaceSrcPath()
     * @generated
     */
    void setWorkspaceSrcPath(String value);

    /**
     * Returns the value of the '<em><b>Module Kind</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Module Kind</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Module Kind</em>' attribute.
     * @see #setModuleKind(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getWorkspaceModule_ModuleKind()
     * @model default=""
     * @generated
     */
    String getModuleKind();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.WorkspaceModule#getModuleKind <em>Module Kind</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Module Kind</em>' attribute.
     * @see #getModuleKind()
     * @generated
     */
    void setModuleKind(String value);

    /**
     * Returns the value of the '<em><b>Dirty</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dirty</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Dirty</em>' attribute.
     * @see #setDirty(boolean)
     * @see com.tibco.xpd.deploy.DeployPackage#getWorkspaceModule_Dirty()
     * @model
     * @generated
     */
    boolean isDirty();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.WorkspaceModule#isDirty <em>Dirty</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Dirty</em>' attribute.
     * @see #isDirty()
     * @generated
     */
    void setDirty(boolean value);

} // WorkspaceModule
