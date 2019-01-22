/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.manager;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

import com.tibco.xpd.deploy.ServerContainer;

/**
 * This interface provides management of the deployment model.
 * <p>
 * This is provisional API. This interface and accompanying implementation will
 * be adapted in the future to WrokingCopy.
 * </p>
 * <p>
 * <i>Created: 27 February 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public interface ServerManager extends IEditingDomainProvider {

    public static final String DEFAULT_SERVER_TYPE_ID = "com.tibco.xpd.deploy.server.ipe.iProcess"; //$NON-NLS-1$

    public static final String DEFAULT_REPOSITORY_TYPE_ID = "com.tibco.xpd.deploy.ui.WorkspaceRpository"; //$NON-NLS-1$

    public static final String DEFAULT_RUNTIME_TYPE_ID = ""; //$NON-NLS-1$

    /**
     * @return deployment model adapter factory.
     */
    public AdapterFactory getAdapterFactory();

    /**
     * Returns model root element.
     * 
     * @return deploy model root element.
     */
    public ServerContainer getServerContainer();

    /**
     * Loads deployment model to memory.
     */
    public void loadServerContainer();

    /**
     * Saves deployment model.
     */
    public void saveServerContainer();

}