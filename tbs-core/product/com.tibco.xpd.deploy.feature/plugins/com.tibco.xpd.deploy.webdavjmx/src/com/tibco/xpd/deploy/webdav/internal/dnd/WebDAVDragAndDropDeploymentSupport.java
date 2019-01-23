/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.deploy.webdav.internal.dnd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.ui.navigator.BaseDragAndDropDeploymentSupport;
import com.tibco.xpd.deploy.webdav.Utils;
import com.tibco.xpd.deploy.webdav.WebDAVPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Support for WebDAV deployment server.
 * 
 * @author Jan Arciuchiewicz
 */
public class WebDAVDragAndDropDeploymentSupport extends
        BaseDragAndDropDeploymentSupport {

    private final Logger LOG = WebDAVPlugin.getDefault().getLogger();

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeDeployment(Server server, List<URL> moduleUrls,
            List<WorkspaceModule> modulesList, List<Object> draggedObjects) {
        moduleUrls.clear();
        modulesList.clear();
        try {
            moduleUrls.addAll(getSelectedDavModules(draggedObjects));
        } catch (CoreException e) {
            LOG.error(e);
        } catch (MalformedURLException e) {
            LOG.error(e);
        }
    }

    private List<URL> getSelectedDavModules(List<Object> draggedObjects)
            throws CoreException, MalformedURLException {
        HashSet<IResource> selectedResources = new HashSet<IResource>();
        for (Object o : draggedObjects) {
            if (o instanceof IResource) {
                IResource resource = (IResource) o;
                if (!resource.isAccessible()
                        || !ProjectUtil.isStudioProject(resource.getProject())) {
                    continue;
                }
                if (resource instanceof IProject) {
                    SpecialFolders specialFolders =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(resource.getProject())
                                    .getSpecialFolders();
                    if (specialFolders != null) {
                        EList<IFolder> davSpecialFolders =
                                specialFolders
                                        .getEclipseIFoldersOfKind(WebDAVPlugin.WEBDAV_DEPLOYABLE_KIND);
                        for (IFolder sf : davSpecialFolders) {
                            addAllChildrenResources(selectedResources, sf);
                        }
                    }
                } else if (resource instanceof IFolder
                        && Utils.isInDAVSpecialFolder(resource)) {
                    selectedResources.add(resource);
                    addAllChildrenResources(selectedResources,
                            (IFolder) resource);
                } else if (resource instanceof IFile
                        && Utils.isInDAVSpecialFolder(resource)) {
                    selectedResources.add(resource);
                }
            } else if (o instanceof SpecialFolder) {
                SpecialFolder sf = (SpecialFolder) o;
                if (WebDAVPlugin.WEBDAV_DEPLOYABLE_KIND.equals(sf.getKind())
                        && sf.getFolder() != null
                        && sf.getFolder().isAccessible()) {
                    addAllChildrenResources(selectedResources, sf.getFolder());
                }
            }
        }
        List<URL> moduleUrls = new ArrayList<URL>();
        for (IResource resource : selectedResources) {
            moduleUrls.add(resource.getLocationURI().toURL());
        }
        return moduleUrls;
    }

    private void addAllChildrenResources(HashSet<IResource> selectedResources,
            IFolder folder) throws CoreException {
        for (IResource member : folder.members()) {
            selectedResources.add(member);
            if (member instanceof IFolder) {
                addAllChildrenResources(selectedResources, (IFolder) member);
            }
        }
    }
}
