/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui;

import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Constants used in Deploy UI plugin.
 * <p>
 * <i>Created: 1 Nov 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployUIConstants {

    /**
     * The constructor.
     */
    private DeployUIConstants() {
    }

    /** Deployment on request policy label */
    public static final String ON_REQUEST_DEPLOY_POLICY_LABEL =
            Messages.DeployUIConstants_DeployOnRequestPolicy_label;

    /** Deployment on save policy label */
    public static final String ON_SAVE_DEPLOY_POLICY_LABEL =
            Messages.DeployUIConstants_DeployOnSavePolicy_label;

    /** Deployment result dialog status */
    public static final String SHOW_DEPLOY_RESULT_DIALOG =
            "deployErrorShowDialog"; //$NON-NLS-1$

    public static final String SHOW_NOTHING_TO_DEPLOY_DIALOG =
            "nothingToDeployDialog"; //$NON-NLS-1$

    public static final String SHOW_DELETE_NODE_DIALOG = "deleteNodeDialog"; //$NON-NLS-1$

    /** Deploy module wizard banner. */
    public static final String IMG_DEPLOY_MODULE_WIZARD =
            "icons/wizban/DeployModuleWizard.png"; //$NON-NLS-1$

    /**
     * Image for connecting server
     */
    public static final String IMG_CONNECT_SERVER =
            "icons/elcl16/connect_server.png"; //$NON-NLS-1$

    /**
     * Image for disconnecting server.
     */
    public static final String IMG_DISCONNECT_SERVER =
            "icons/elcl16/disconnect_server.png"; //$NON-NLS-1$

    /** New Server/Runtime wizard banner. */
    public static final String IMG_SERVER_WIZARD =
            "icons/wizban/ServerWizard.png"; //$NON-NLS-1$

    /** New Server/Runtime wizard banner. */
    public static final String IMG_REFRESH = "icons/elcl16/refresh.gif"; //$NON-NLS-1$

    public static final String IMG_SYSTEM_PICK = "icons/obj16/SystemPick.gif"; //$NON-NLS-1$

    public static final String IMG_WORKSPACE_PICK =
            "icons/obj16/WorkspacePick.gif"; //$NON-NLS-1$

    /**
     * Images loaded by plug-in and served by registry.
     * 
     * @see DeployUIActivator#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     */
    public static final String[] IMAGES = new String[] {
            IMG_DEPLOY_MODULE_WIZARD, IMG_SERVER_WIZARD, IMG_REFRESH,
            IMG_SYSTEM_PICK, IMG_WORKSPACE_PICK, IMG_CONNECT_SERVER,
            IMG_DISCONNECT_SERVER };
}
