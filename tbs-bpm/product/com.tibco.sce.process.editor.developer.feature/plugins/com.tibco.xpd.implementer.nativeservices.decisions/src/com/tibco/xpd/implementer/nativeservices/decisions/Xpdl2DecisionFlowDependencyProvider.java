package com.tibco.xpd.implementer.nativeservices.decisions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Computes dependencies of xpdl files on decision flow files
 * 
 * 
 * @author aallway
 * @since 3 Aug 2011
 */
public class Xpdl2DecisionFlowDependencyProvider implements
        IWorkingCopyDependencyProvider {

    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return Xpdl2WorkingCopyImpl.class;
    }

    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Set<IResource> dependencies = new HashSet<IResource>();

        if (wc.getRootElement() instanceof Package) {
            EList<Process> processes =
                    ((Package) wc.getRootElement()).getProcesses();

            for (Process process : processes) {
                Collection<Activity> allActivities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                for (Activity activity : allActivities) {
                    if (DecisionTaskObjectUtil.isDecisionServiceTask(activity)) {
                        EObject decisionFlow =
                                DecisionTaskObjectUtil
                                        .getDecisionFlow(activity);
                        if (decisionFlow != null) {
                            IFile file = WorkingCopyUtil.getFile(decisionFlow);
                            if (file != null) {
                                dependencies.add(file);
                            }
                        }
                    }
                }
            }
        }

        /*
         * Just in case remove the file for this wortking copy (i.e. don't let
         * it be self-dependent just in case that confues things)
         */
        dependencies.remove(wc.getEclipseResources().get(0));
        return dependencies;
    }
}
