/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class PageflowUserTaskRule extends ProcessValidationRule {

    private static final String UNRESOLVED_FORM =
            "bpmn.unresolvedUserTaskFormReference"; //$NON-NLS-1$

    private static final String UNRESOLVED_PAGEFLOW =
            "bpmn.unresolvedUserTaskPageflowReference"; //$NON-NLS-1$

    private static final String CANT_REF_ANY_OTHER_PROCESS =
            "bpmn.referencedProcessCannotBeAnyOtherProcess"; //$NON-NLS-1$

    private static final String OLD_PAGEFLOW_REFERENCE =
            "bpmn.oldAbsoluteUserTaskPageflowReference"; //$NON-NLS-1$

    private static final String UNDEFINED_FORM =
            "bpmn.undefinedUserTaskFormReference"; //$NON-NLS-1$

    private static final String UNDEFINED_PAGEFLOW =
            "bpmn.undefinedUserTaskPageflowReference"; //$NON-NLS-1$

    private static final String OTHER_PROJECT_FORM_REFERENCE =
            "bpmn.otherProjectUserTaskFormReference"; //$NON-NLS-1$

    public static final String EXTRA_INFO_PARAMNAME = "EXTRA_INFO_PARAMNAME"; //$NON-NLS-1$

    public static final String EXTRA_INFO_PAGEFLOWID = "EXTRA_INFO_PAGEFLOWID"; //$NON-NLS-1$

    /**
     * Pageflow start has no parameter that matches data
     * 'NewlyAssociatedTaskData' associated in user task interface
     */
    private static final String MISSING_PAGEFLOW_PROCESS_PARAMETER =
            "bpmn.missingPageflowProcessParameter"; //$NON-NLS-1$

    /**
     * Pageflow start interface should include parameter '%1$s' (to match User
     * Task interface).
     */
    private static final String MISSING_PAGEFLOW_PROCESS_PARAMETER_ASSOCIATION =
            "bpmn.missingAssociatedPageflowProcessParameter"; //$NON-NLS-1$

    /**
     * User Task interface has no data that matches parameter 'NewPageflowData'
     * in the referenced Pageflow
     */
    private static final String MISSING_PAGEFLOW_USERTASK_DATA =
            "bpmn.missingPageflowUserTaskData"; //$NON-NLS-1$

    /**
     * User Task interface should include data '%1$s' (to match referenced
     * Pageflow start interface).
     */
    private static final String MISSING_PAGEFLOW_USERTASK_DATA_ASSOCIATION =
            "bpmn.missingAssociatedPageflowUserTaskData"; //$NON-NLS-1$

    /**
     * User task data type
     */
    private static final String PAGEFLOW_PARAMETER_DIFFERENT_TYPE =
            "bpmn.pageflowParameterWrongType"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {

            /* Check the the selected form implementation type is resolvable. */
            FormImplementation formImpl =
                    TaskObjectUtil.getUserTaskFormImplementation(activity);
            if (formImpl != null) {
                if (FormImplementationType.FORM.equals(formImpl.getFormType())) {
                    checkUserTaskForm(activity, formImpl);

                } else if (FormImplementationType.PAGEFLOW.equals(formImpl
                        .getFormType())) {
                    checkUserTaskPageflow(activity, formImpl);

                }
            }
        }

        return;
    }

    private void checkUserTaskPageflow(Activity activity,
            FormImplementation formImpl) {
        /*
         * Activity in pageflow cannot invoke pageflow. Don't bother complaining
         * about anything else because pageflow rule will say you can't do this.
         */
        if (!Xpdl2ModelUtil.isPageflow(activity.getProcess())) {

            /* Check pageflow defined. */
            if (formImpl.getFormURI() == null
                    || formImpl.getFormURI().length() == 0) {
                addIssue(UNDEFINED_PAGEFLOW, activity);

            } else {
                /* Check pageflow exists. */
                Process pageflowProcess =
                        TaskObjectUtil.getUserTaskPageflowProcess(activity);
                if (pageflowProcess == null) {
                    /*
                     * MR 41967 - check whether not found because its an old
                     * complete uri reference.
                     */
                    boolean foundById = false;
                    if (formImpl.getFormURI().startsWith("platform:/")) { //$NON-NLS-1$
                        URI uri = URI.createURI(formImpl.getFormURI());
                        if (uri != null) {
                            String id = uri.fragment();
                            if (id != null && id.length() > 0) {
                                Process pageflow =
                                        Xpdl2WorkingCopyImpl.locateProcess(id);
                                if (pageflow != null) {
                                    foundById = true;
                                }
                            }
                        }
                    }

                    if (foundById) {
                        addIssue(OLD_PAGEFLOW_REFERENCE, activity);
                    } else {
                        addIssue(UNRESOLVED_PAGEFLOW, activity);
                    }

                } else {
                    /*
                     * Can only reference a pageflow process.
                     */
                    if (Xpdl2ModelUtil.isCaseService(pageflowProcess)
                            || Xpdl2ModelUtil
                                    .isBusinessProcess(pageflowProcess)
                            || Xpdl2ModelUtil
                                    .isPageflowBusinessService(pageflowProcess)
                            || Xpdl2ModelUtil.isServiceProcess(pageflowProcess)) {

                        addIssue(CANT_REF_ANY_OTHER_PROCESS, activity);
                    } else {

                        /*
                         * Match data on pageflow against calling process
                         * activity.
                         */
                        checkPageflowParameters(activity, pageflowProcess);
                    }
                }
            }
        }
        return;
    }

    /**
     * Check that the pageflow parameters and activity associated data match.
     * 
     * @param activity
     * @param pageflowProcess
     */
    private void checkPageflowParameters(Activity activity,
            Process pageflowProcess) {

        /*
         * Compare the user task interface (the params explicitly/implicitly
         * associated with it) with the pageflow process interface (the params
         * explicitly/implicitly associated with start event (or all if no start
         * event).
         */
        List<FormalParameter> pageflowParamsToAdd =
                new ArrayList<FormalParameter>();
        List<FormalParameter> pageflowParamsToRemove =
                new ArrayList<FormalParameter>();
        List<FormalParameter> pageflowParamsToModify =
                new ArrayList<FormalParameter>();

        List<?> allPageflowParams =
                ProcessInterfaceUtil.getAllFormalParameters(pageflowProcess);

        Map<String, ProcessRelevantData> inScopeTaskDataMap =
                ProcessDataUtil.getDataMap(ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(activity));

        if (!PageflowUtil
                .compareUserTaskInterfaceWithPageflowInterface(activity,
                        pageflowProcess,
                        pageflowParamsToAdd,
                        pageflowParamsToRemove,
                        pageflowParamsToModify)) {
            /*
             * They're different - first deal with parameters that are in user
             * task interface but not in pageflow interface.
             */
            for (FormalParameter p : pageflowParamsToAdd) {
                /*
                 * Check whether 'missing' parameter actually exists in
                 * pageflow, if so then it means that it has been explicitly
                 * unassociated (because other params have been explicitly
                 * associated).
                 */
                List<String> messages = new ArrayList<String>();
                messages.add(Xpdl2ModelUtil.getDisplayNameOrName(p));

                if (allPageflowParams.contains(p)) {
                    /* Param exists but is not associated with start event. */
                    addIssue(MISSING_PAGEFLOW_PROCESS_PARAMETER_ASSOCIATION,
                            activity,
                            messages,
                            createExtraParamInfo(p,
                                    pageflowProcess,
                                    activity.getProcess()));
                } else {
                    /* Param does not exist. */
                    addIssue(MISSING_PAGEFLOW_PROCESS_PARAMETER,
                            activity,
                            messages,
                            createExtraParamInfo(p,
                                    pageflowProcess,
                                    activity.getProcess()));
                }
            }

            /*
             * Deal with params that exist in pageflow but not on user task
             * interface.
             */
            for (FormalParameter p : pageflowParamsToRemove) {
                List<String> messages = new ArrayList<String>();
                messages.add(Xpdl2ModelUtil.getDisplayNameOrName(p));

                /*
                 * Check whether 'missing' user task data actually exists in
                 * pageflow, if so then it means that it has been explicitly
                 * unassociated (because other data has been explicitly
                 * associated).
                 */
                if (inScopeTaskDataMap.containsKey(p.getName())) {
                    /* Param exists but is not associated with start event. */
                    addIssue(MISSING_PAGEFLOW_USERTASK_DATA_ASSOCIATION,
                            activity,
                            messages,
                            createExtraParamInfo(p,
                                    pageflowProcess,
                                    activity.getProcess()));
                } else {
                    /* Param does not exist. */
                    addIssue(MISSING_PAGEFLOW_USERTASK_DATA,
                            activity,
                            messages,
                            createExtraParamInfo(p,
                                    pageflowProcess,
                                    activity.getProcess()));
                }
            }

            /* Then deal with the modified parameters. */
            for (FormalParameter p : pageflowParamsToModify) {
                List<String> messages = new ArrayList<String>();
                messages.add(Xpdl2ModelUtil.getDisplayNameOrName(p));

                addIssue(PAGEFLOW_PARAMETER_DIFFERENT_TYPE,
                        activity,
                        messages,
                        createExtraParamInfo(p,
                                pageflowProcess,
                                activity.getProcess()));
            }
        }

        return;
    }

    private Map<String, String> createExtraParamInfo(ProcessRelevantData param,
            Process pageflowProcess, Process businessProcess) {
        HashMap<String, String> info = new HashMap<String, String>();
        info.put(EXTRA_INFO_PARAMNAME, param.getName());
        info.put(EXTRA_INFO_PAGEFLOWID, pageflowProcess.getId());

        /*
         * If the referenced pageflow is in a different package then we have to
         * link the 2 resources so that they get revalidated.
         */
        IFile pageflowFile = WorkingCopyUtil.getFile(pageflowProcess);
        IFile businessFile = WorkingCopyUtil.getFile(businessProcess);
        if (businessFile != null && !businessFile.equals(pageflowFile)) {
            String resURI =
                    URI.createPlatformResourceURI(pageflowFile.getFullPath()
                            .toPortableString(),
                            true).toString();
            info.put(ValidationActivator.LINKED_RESOURCE, resURI);
        }

        return info;
    }

    private void checkUserTaskForm(Activity activity,
            FormImplementation formImpl) {
        /* First off, check it exists. */
        if (formImpl.getFormURI() == null
                || formImpl.getFormURI().length() == 0) {
            addIssue(UNDEFINED_FORM, activity);

        } else {
            IFile formFile = TaskObjectUtil.getUserTaskFormFile(activity);

            if (formFile == null || !formFile.exists()) {
                addIssue(UNRESOLVED_FORM,
                        activity,
                        Collections.singletonList(formImpl.getFormURI()));

            } else {
                /*
                 * XPD-7461: Problems with DAA generation (and runtime
                 * execution) when the referenced form is from another project.
                 */
                IProject formProject = formFile.getProject();
                IProject activityProject =
                        WorkingCopyUtil.getProjectFor(activity);
                if (formProject != null && !formProject.equals(activityProject)) {
                    addIssue(OTHER_PROJECT_FORM_REFERENCE,
                            activity,
                            Collections.singletonList(formImpl.getFormURI()));
                }

                /*
                 * Checking params has to be the prerogative of the forms
                 * feature. Nothing we can do here.
                 */
            }
        }
        return;
    }
}
