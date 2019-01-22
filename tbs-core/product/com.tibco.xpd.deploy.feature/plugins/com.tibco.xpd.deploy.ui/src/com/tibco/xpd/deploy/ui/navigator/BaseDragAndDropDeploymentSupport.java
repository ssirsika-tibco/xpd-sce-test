/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.navigator;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.DeploymentInterceptor;
import com.tibco.xpd.deploy.ui.util.DragAndDropDeploymentUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * @author mtorres
 * 
 */
public class BaseDragAndDropDeploymentSupport implements DeploymentInterceptor {

    private Server server;

    private DragAndDropDeployment parentDragAndDropDeployment;
    
    private IProgressMonitor monitor;

    public IProgressMonitor getMonitor() {
    	if (monitor == null){
    		monitor = new NullProgressMonitor();
    	}
		return monitor;
	}

	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	public DragAndDropDeployment getParentDragAndDropDeployment() {
        return parentDragAndDropDeployment;
    }

    public void setParentDragAndDropDeployment(
            DragAndDropDeployment parentDragAndDropDeployment) {
        this.parentDragAndDropDeployment = parentDragAndDropDeployment;
    }

    public void afterDeployment(Server server, List<URL> moduleUrls,
            List<WorkspaceModule> modulesList) {
        if (server != null) {
            Connection connection = server.getConnection();
            if (connection != null) {
                Object obj = connection.getAdapter(DeploymentInterceptor.class);
                if (obj instanceof DeploymentInterceptor) {
                    DeploymentInterceptor di = (DeploymentInterceptor) obj;
                    di.afterDeployment(server, moduleUrls, modulesList);
                }
            }
        }
    }

    public void beforeDeployment(Server server, List<URL> moduleUrls,
            List<WorkspaceModule> modulesList, List<Object> draggedObjects) {
        if (server != null) {
            Connection connection = server.getConnection();
            if (connection != null) {
                Object obj = connection.getAdapter(DeploymentInterceptor.class);
                if (obj instanceof DeploymentInterceptor) {
                    DeploymentInterceptor di = (DeploymentInterceptor) obj;
                    di.beforeDeployment(server, moduleUrls, modulesList);
                }
            }
        }
    }

    /**
     * Should return a list of resources to deploy.
     * 
     * The default implementation returns all the files inside the special
     * folder for the given valid source extensions
     * 
     * @param draggedResources
     *            the resources that have been dragged.
     * 
     * @return list of resources
     */
    public List<IResource> getSourceModules(List<Object> draggedResources) {
        if (getParentDragAndDropDeployment() != null) {
            return getModules(draggedResources,
                    getParentDragAndDropDeployment()
                            .getSourceSpecialFolderKinds(),
                    getParentDragAndDropDeployment().getSourceValidExtensions());
        }
        return new ArrayList<IResource>();
    }

    /**
     * Should return a list of resources that can be deployed for a given
     * dragged and dropped resource.
     * 
     * The default implementation returns all the files inside the special
     * folder for the given valid output extensions
     * 
     * @param draggedResources
     *            the resources that have been dragged.
     * 
     * @return list of resources that can be deployable
     */
    public List<IResource> getOutputModules(List<Object> draggedResources) {
        if (getParentDragAndDropDeployment() != null) {
            return getModules(draggedResources,
                    new String[] { getParentDragAndDropDeployment()
                            .getOutputSpecialFolderKind() },
                    getParentDragAndDropDeployment().getOutputValidExtensions());
        }
        return new ArrayList<IResource>();
    }

    private List<IResource> getModules(List<Object> draggedResources,
            String[] specialFolderKinds, String validExtensionsStr) {
        Set<IResource> modules = new HashSet<IResource>();
        List<String> validExtensions =
                DragAndDropDeploymentUtil
                        .getValidModulesExtensions(validExtensionsStr);
        for (Object obj : draggedResources) {
            if (obj instanceof IResource) {
                IResource draggedResource = (IResource) obj;
                IProject project = draggedResource.getProject();
                if (project != null) {
                    if (validExtensions == null || validExtensions.isEmpty()) {
                        for (int index = 0; index < specialFolderKinds.length; index++) {
                            String specialFolderKind =
                                    specialFolderKinds[index].trim();
                            if (specialFolderKind.length() > 0) {
                                List<IResource> deployableModulesForExt =
                                        SpecialFolderUtil
                                                .getAllDeepResourcesInSpecialFolderOfKind(project,
                                                        specialFolderKind,
                                                        null,
                                                        false);
                                if (deployableModulesForExt != null) {
                                    modules.addAll(deployableModulesForExt);
                                }
                            }
                        }
                    } else {
                        for (String validExtension : validExtensions) {
                            for (int index = 0; index < specialFolderKinds.length; index++) {
                                String specialFolderKind =
                                        specialFolderKinds[index].trim();
                                if (specialFolderKind.length() > 0) {
                                    if (validExtension.startsWith(".")
                                            && validExtension.length() > 1) {
                                        validExtension =
                                                validExtension.substring(1);
                                    }
                                    List<IResource> modulesForExt =
                                            SpecialFolderUtil
                                                    .getAllDeepResourcesInSpecialFolderOfKind(project,
                                                            specialFolderKind,
                                                            validExtension,
                                                            false);
                                    if (modulesForExt != null) {
                                        modules.addAll(modulesForExt);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<IResource>(modules);
    }

    public List<String> getValidSourceModulesExtensions() {
        if (getParentDragAndDropDeployment() != null
                && getParentDragAndDropDeployment().getSourceValidExtensions() != null) {
            return DragAndDropDeploymentUtil
                    .getValidModulesExtensions(getParentDragAndDropDeployment()
                            .getSourceValidExtensions());
        }
        return null;
    }

    /**
     * This method should be overridden to provide further validation to the
     * dragged module.
     * 
     * The default implementation of this module returns true
     * 
     * @param draggedResourceList
     *            the dragged resource, in a List form
     * 
     * @return true if the predeployable module is valid to deploy
     */
    public boolean validateDraggedModules(List<Object> draggedResourceList) {
        return true;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void beforeDeployment(Server server, List<URL> moduleUrls,
            List<WorkspaceModule> modulesList) {
        beforeDeployment(server,
                moduleUrls,
                modulesList,
                new ArrayList<Object>());

    }

    public void handleEmptyResources() {
    }
}
