/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.resolutions.SetReferencedProjectResolution;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SubProcessReferenceRule
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Dec 2009)
 */
public class SubProcessReferenceRule extends ProcessValidationRule {

    /**
     * Referenced sub-process %1$s resides in an unreferenced project not
     * referenced.
     */
    private static final String ISSUE_CANT_ACCESS_SUB_PROCESS_IN_UNREFERENCED_PROJECT =
            "bpmn.cantAccessSubProcessInUnreferencedProject"; //$NON-NLS-1$

    // private static final String
    // ISSUE_CANT_ACCESS_PAGEFLOW_SUB_PROCESS_IN_UNREFERENCED_PROJECT =
    //            "bpmn.cantAccessPageflowSubProcessInUnreferencedProject"; //$NON-NLS-1$

    /**
     * The referenced sub-process cannot be found.
     */
    private static final String ISSUE_CANT_ACCESS_SUB_PROCESS =
            "bpmn.cantAccessSubProcess"; //$NON-NLS-1$

    // private static final String ISSUE_CANT_ACCESS_PAGEFLOW_SUB_PROCESS =
    //            "bpmn.cantAccessPageflowSubProcess"; //$NON-NLS-1$

    /**
     * Sub-process task requires a reference to a sub-process or process
     * interface.
     */
    private static final String ISSUE_NO_SUB_PROCESS_SELECTED =
            "bpmn.noSubProcessSelected"; //$NON-NLS-1$

    // private static final String ISSUE_NO_PAGEFLOWSUB_PROCESS_SELECTED =
    //            "bpmn.noPageflowSubProcessSelected"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {
                checkSubProcessReference(activity);
            }
        }

        return;
    }

    /**
     * Check the reference to the sub-process from the given sub-process task.
     * 
     * @param activity
     */
    private void checkSubProcessReference(Activity activity) {
        EObject subProcessOrInterface =
                TaskObjectUtil.getSubProcessOrInterface(activity);
        if (subProcessOrInterface == null) {
            /*
             * Can't find the sub-process or interface (at least in anything
             * that's referencible from our activity).
             * 
             * This might be that the other process/interface simply does not
             * exist anymore OR could be that it that the referenced process is
             * in another unreferenced project. In this case the process's id
             * may well be found in the index so we can give a different error
             * that will allow a quick fix that can fix things up nicely.
             */
            String subProcessId = null;
            if (activity.getImplementation() instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) activity.getImplementation();

                subProcessId = subFlow.getProcessId();
                if (subProcessId != null && subProcessId.length() > 0) {
                    subProcessOrInterface =
                            Xpdl2WorkingCopyImpl.locateProcess(subProcessId);
                    if (subProcessOrInterface == null) {
                        subProcessOrInterface =
                                Xpdl2WorkingCopyImpl
                                        .locateProcessInterface(subProcessId);
                    }
                }
            }

            IProject refdProject = null;
            if (subProcessOrInterface != null) {
                refdProject =
                        WorkingCopyUtil.getProjectFor(subProcessOrInterface);
            }

            if (subProcessOrInterface != null
                    && !isInSameProjectSet(activity, subProcessOrInterface)
                    && refdProject != null) {
                /*
                 * Found the sub-process / interface but its not reachable from
                 * referencing activity's project.
                 */
                String issueId = null;

                if (subProcessOrInterface instanceof Process) {

                    issueId =
                            ISSUE_CANT_ACCESS_SUB_PROCESS_IN_UNREFERENCED_PROJECT;
                }

                Map<String, String> additional = new HashMap<String, String>();
                additional
                        .put(SetReferencedProjectResolution.PROJECTNAME_ADDITIONAL_INFO,
                                refdProject.getName());
                addIssue(issueId,
                        activity,
                        Collections.singletonList(Xpdl2ModelUtil
                                .getDisplayNameOrName((NamedElement) subProcessOrInterface)),
                        additional);

            } else if (subProcessId != null && subProcessId.length() > 0
                    && !TaskObjectUtil.UNKNOWN_REF_ID.equals(subProcessId)) {
                /*
                 * Cannot access sub-process even though there is a reference in
                 * model.
                 */
                addIssue(ISSUE_CANT_ACCESS_SUB_PROCESS, activity);

            } else {
                /*
                 * Sub-process reference not even defined.
                 */
                addIssue(ISSUE_NO_SUB_PROCESS_SELECTED, activity);
            }
        }

        return;
    }

    /**
     * @param activity
     * @param subProcessOrInterface
     * 
     * @return true if the two objects are in the same cross-referencing set of
     *         projects or false if not.
     */
    private boolean isInSameProjectSet(EObject referencer, EObject referencee) {

        IProject referencerProject = WorkingCopyUtil.getProjectFor(referencer);
        IProject referenceeProject = WorkingCopyUtil.getProjectFor(referencee);

        if (referencerProject != null && referenceeProject != null) {
            try {
                return ProjectUtil.isProjectReferenced(referencerProject,
                        referenceeProject);
            } catch (CoreException e) {
            }
        }

        return false;
    }
}
