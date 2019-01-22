/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.internal;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.WorkingCopyDependencyProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Dependency provider for process to pageflow dependencies.Resolves and returns
 * dependencies for external pageflow references from the processes in the given
 * working copy .
 * 
 * @author aprasad
 * @since 12 Sep 2012
 */
public class Xpdl2PageFlowWorkingCopyDependencyProvider extends
        WorkingCopyDependencyProvider {

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass()
     * 
     * @return
     */
    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return Xpdl2WorkingCopyImpl.class;
    }

    /**
     * @see com.tibco.xpd.resources.wc.WorkingCopyDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     * @return
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Collection<IResource> dependencies = super.getDependencies(wc);
        if (wc != null && wc.getEclipseResources() != null
                && !wc.getEclipseResources().isEmpty()) {
            dependencies = new HashSet<IResource>();

            EObject element = wc.getRootElement();
            if (element instanceof Package) {

                IFile sourceResource = WorkingCopyUtil.getFile(element);
                if (sourceResource != null) {
                    EList<Process> processes =
                            ((Package) element).getProcesses();
                    for (Process process : processes) {
                        Collection<Activity> activities =
                                Xpdl2ModelUtil.getAllActivitiesInProc(process);
                        for (Activity activity : activities) {
                            Process pageFlow =
                                    TaskObjectUtil
                                            .getUserTaskPageflowProcess(activity);
                            /*
                             * if referenced pageflow is in another xpdl file,
                             * and thus a candidate for external dependency
                             */
                            if (pageFlow != null) {
                                IFile targetResource =
                                        WorkingCopyUtil.getFile(pageFlow);
                                if (targetResource != null
                                        && !sourceResource
                                                .equals(targetResource)) {
                                    dependencies.add(targetResource);
                                }
                            }
                        }

                    }
                }
            }

        }
        return dependencies;
    }
}
