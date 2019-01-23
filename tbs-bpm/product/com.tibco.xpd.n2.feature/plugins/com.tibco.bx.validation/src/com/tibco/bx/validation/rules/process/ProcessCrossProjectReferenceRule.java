package com.tibco.bx.validation.rules.process;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check for cross-project references in a {@link Process}.
 * This will check the Process' data fields and formal parameters, embedded
 * process data, implemented interfaces, sub-processes and reference task for
 * external references to other projects.
 * 
 * @author njpatel
 * 
 */
public class ProcessCrossProjectReferenceRule extends ProcessValidationRule {

    private static final String ISSUE_ID = "bx.crossProjectReference.issue"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {
        IProject srcProject = WorkingCopyUtil.getProjectFor(process);
        if (srcProject != null) {

            /* Check implemented interface. */
            ProcessInterface processInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);
            if (processInterface != null
                    && isCrossProjectReference(srcProject, processInterface)) {
                /*
                 * Allow cross project invocations of Sub process & Dynamic Sub
                 * Process
                 */
                // addIssue(ISSUE_ID, process);
            }

            /*
             * Check Sub-Process Task Process/Inteface References/Reference
             * Task/Page flow from user task
             */
            Collection<Activity> activitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : activitiesInProc) {
                if (TaskType.REFERENCE_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))) {
                    if (ReferenceTaskUtil.isTaskLibraryTaskReference(activity)) {
                        Activity referencedTask =
                                ReferenceTaskUtil
                                        .getReferencedLibraryTask(activity);
                        if (referencedTask != null
                                && isCrossProjectReference(srcProject,
                                        referencedTask)) {
                            addIssue(ISSUE_ID, activity);
                        }
                    }
                } else if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))) {
                    Process pageFlowProcess =
                            TaskObjectUtil.getUserTaskPageflowProcess(activity);
                    if (pageFlowProcess != null
                            && isCrossProjectReference(srcProject,
                                    pageFlowProcess)) {
                        // addIssue(ISSUE_ID, activity);
                    }
                }
            }
        }
    }

    /**
     * Check if the given {@link EObject} is in the srcProject. If not then this
     * is a cross-project reference.
     * 
     * @param srcProject
     * @param ref
     * @return
     */
    private boolean isCrossProjectReference(IProject srcProject, EObject ref) {
        IProject project = WorkingCopyUtil.getProjectFor(ref);
        return project != null && !project.equals(srcProject);
    }

    /**
     * Check if this external reference is to another project.
     * 
     * @param srcProject
     * @param reference
     * @return
     */
    private boolean isCrossProjectReference(IProject srcProject,
            ExternalReference reference) {
        String location = reference.getLocation();
        if (location != null) {
            IFile file =
                    SpecialFolderUtil
                            .resolveSpecialFolderRelativePath(srcProject,
                                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                                    location,
                                    true);

            return file != null && !srcProject.equals(file.getProject());
        }

        return false;
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Do nothing
        return;
    }
}
