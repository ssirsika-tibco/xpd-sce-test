/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.DependencyProxyResource;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Xpdl2 working copies' dependency provider to Organization Models.
 * 
 * @author njpatel
 * 
 */
public class Xpdl2OMDependencyProvider implements
        IWorkingCopyDependencyProvider {

    private static final String OM_SPECIALFOLDER_KIND = "om"; //$NON-NLS-1$

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
                // Get all package participants with external references
                Set<String> pkgLocations =
                        getLocations(((Package) element).getParticipants());
                if (!pkgLocations.isEmpty()) {
                    locations.addAll(pkgLocations);
                }

                // Get all process participants with external references
                EList<Process> processes = ((Package) element).getProcesses();
                if (processes != null) {
                    for (Process process : processes) {
                        Set<String> processParticipants =
                                getLocations(process.getParticipants());
                        if (!processParticipants.isEmpty()) {
                            locations.addAll(processParticipants);
                        }
                    }
                }
            }

            if (!locations.isEmpty()) {
                for (String location : locations) {
                    IFile file =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(project,
                                            OM_SPECIALFOLDER_KIND,
                                            location,
                                            true);
                    if (file != null) {
                        resources.add(file);
                    } else {
                        // Add proxy resource
                        resources.add(new DependencyProxyResource(new Path(
                                location), OM_SPECIALFOLDER_KIND));
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
     * Get all external locations from the {@link Participant}s, if available.
     * 
     * @param participants
     * @return
     */
    private Set<String> getLocations(EList<Participant> participants) {
        Set<String> locations = new HashSet<String>();

        if (participants != null) {
            for (Participant participant : participants) {
                ExternalReference ref = participant.getExternalReference();
                if (ref != null && ProcessUIUtil.isValidExternalReference(ref)) {
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
