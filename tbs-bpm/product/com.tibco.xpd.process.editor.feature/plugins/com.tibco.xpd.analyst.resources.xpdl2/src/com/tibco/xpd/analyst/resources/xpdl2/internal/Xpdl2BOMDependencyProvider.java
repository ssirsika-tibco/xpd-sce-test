/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.DependencyProxyResource;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Xpdl2 working copies' dependency provider to Business Object Models.
 * 
 * @author njpatel
 * 
 */
public class Xpdl2BOMDependencyProvider implements
        IWorkingCopyDependencyProvider {

    private static final String BOM_SPECIALFOLDER_KIND =
            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies
     * (com.tibco.xpd.resources.WorkingCopy)
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Set<IResource> resources = new HashSet<IResource>();

        if (wc != null && wc.getEclipseResources() != null
                && !wc.getEclipseResources().isEmpty()) {
            IProject project = wc.getEclipseResources().get(0).getProject();
            EObject element = wc.getRootElement();
            Set<String> locations = new HashSet<String>();
            if (element instanceof Package) {
                Package pkg = (Package) element;
                // Get all BOM external references from the package
                Set<String> theLocations = getLocations(pkg);
                if (!theLocations.isEmpty()) {
                    locations.addAll(theLocations);
                }

                // Get all BOM external references from each process
                EList<Process> processes = pkg.getProcesses();
                if (processes != null) {
                    for (Process process : processes) {
                        theLocations = getLocations(process);
                        if (!theLocations.isEmpty()) {
                            locations.addAll(theLocations);
                        }
                    }
                }

                // Get all BOM external references from each process interface
                ProcessInterfaces interfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);
                if (interfaces != null
                        && interfaces.getProcessInterface() != null) {
                    for (ProcessInterface pInterface : interfaces
                            .getProcessInterface()) {
                        theLocations = getLocations(pInterface);
                        if (!theLocations.isEmpty()) {
                            locations.addAll(theLocations);
                        }
                    }
                }
            }

            if (!locations.isEmpty()) {
                for (String location : locations) {
                    IFile file =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(project,
                                            BOM_SPECIALFOLDER_KIND,
                                            location,
                                            true);
                    if (file != null) {
                        resources.add(file);
                    } else {
                        // Add proxy resource
                        resources.add(new DependencyProxyResource(new Path(
                                location), BOM_SPECIALFOLDER_KIND));
                    }
                }
            }
        }

        return resources;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass
     * ()
     */
    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return Xpdl2WorkingCopyImpl.class;
    }

    /**
     * Get all external references paths in the given element's data fields.
     * 
     * @param eo
     * @return
     */
    private Set<String> getLocations(EObject eo) {
        Set<String> locations = new HashSet<String>();

        if (eo != null) {
            List<ExternalReference> refs =
                    ProcessUIUtil.getBOMExternalReferences(eo);
            if (refs != null && !refs.isEmpty()) {
                for (ExternalReference ref : refs) {
                    String location = ref.getLocation();
                    if (location != null) {
                        locations.add(location);
                    }
                }
            }
        }

        return locations;
    }

}
