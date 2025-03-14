package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.bx.xpdl2bpel.converter.internal.ConverterExtensions;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

public class ExtensionValidationRule extends ActivityValidationRule {

    public static final String ISSUE_NO_CONFIG_DATA =
            "n2pe.noExtensionConfigData"; //$NON-NLS-1$

    public static final String ISSUE_NO_MODEL_BUILDER = "n2pe.noModelBuilder"; //$NON-NLS-1$

    public static final String ISSUE_MANUAL_TASK_NOT_SUPPORTED =
            "n2pe.manualTaskNotSupported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_DECISIONS_TASK_NOT_SUPPORTED =
            "ace.decision.task.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_DATABASE_TASK_NOT_SUPPORTED =
            "ace.database.task.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_JAVA_TASK_NOT_SUPPORTED =
            "ace.java.task.not.supported"; //$NON-NLS-1$

    private static final String ACE_ISSUE_DOCUMENT_TASK_NOT_SUPPORTED =
            "ace.document.task.not.supported"; //$NON-NLS-1$

    @Override
    public void validate(Activity activity) {

        /*
         * XPD_7074 - Allow REST Service tasks (which are not done by plugin
         * extension, they are handled natively inside xpdl2bpel.
         */
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        if (rsta.isRestServiceImplementation(activity)) {
            return;
        }

        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task t = (Task) impl;
            if (t.getTaskManual() != null) {
                validateManualTask(activity, t.getTaskManual());
            } else if (t.getTaskUser() != null) {
                validateExtension(activity, t.getTaskUser());
            } else if (t.getTaskScript() != null) {
                validateExtension(activity, t.getTaskScript());
            } else if (t.getTaskService() != null) {
                TaskService taskService = t.getTaskService();

                if (taskService.getImplementation() == ImplementationType.OTHER_LITERAL) {
                    FeatureMap featureMap = taskService.getOtherElements();
                    if (featureMap.isEmpty()) {
                        validateExtension(activity, null);

                    } else {
                        EObject obj = (EObject) featureMap.getValue(0);
                        validateExtension(activity, obj);
                    }
                }
            }
        }
    }

    private void validateManualTask(EObject container, EObject model) {
        boolean hasExtension = false;
        try {
            hasExtension = ConverterExtensions.hasModelBuilderExtension(model);
        } catch (CoreException e) {
        }
        if (!hasExtension) {
            this.addIssue(ISSUE_MANUAL_TASK_NOT_SUPPORTED, container);
        }
    }

    /**
     * Check that the given activity implementation type is supported.
     * 
     * @param activity
     * @param model
     */
    private void validateExtension(Activity activity, EObject model) {
        /*
         * Sid ACE-475: Suppress old generic task extension validations in
         * favour of new ACE specific rules.
         */
        String implementationId =
                TaskObjectUtil.getTaskImplementationExtensionId(activity);

        // if (TaskImplementationTypeDefinitions.DATABASE_SERVICE
        // .equals(implementationId)) {
        // addIssue(ACE_ISSUE_DATABASE_TASK_NOT_SUPPORTED, activity);
        //
        // } else
        if (TaskImplementationTypeDefinitions.DECISION_SERVICE
                .equals(implementationId)) {
            addIssue(ACE_ISSUE_DECISIONS_TASK_NOT_SUPPORTED, activity);

        } else if (TaskImplementationTypeDefinitions.JAVA_SERVICE
                .equals(implementationId)) {
            addIssue(ACE_ISSUE_JAVA_TASK_NOT_SUPPORTED, activity);

        } else if (TaskImplementationTypeDefinitions.DOCUMENT_OPERATIONS
                .equals(implementationId)) {
            addIssue(ACE_ISSUE_DOCUMENT_TASK_NOT_SUPPORTED, activity);

        } else {

            /*
             * Else fall back on the old 'do we have a BPEL converter extension'
             * generic problem.
             */
            if (model == null) {
                /*
                 * Moved from parent function as we want to consider the known
                 * impl' types first.
                 */
                if (!DecisionTaskObjectUtil.isDecisionServiceTask(activity)) {
                    addNoConfigData(activity.getName(), activity);
                }

            } else {

                boolean hasExtension = false;
                try {
                    hasExtension =
                            ConverterExtensions.hasModelBuilderExtension(model);
                } catch (CoreException e) {
                }
                if (!hasExtension) {
                    String className =
                            model.eClass().getInstanceClass().getName();
                    addNoModelBuilderError(className, activity);
                }
            }
        }

    }

    private void addNoConfigData(String message, EObject obj) {
        List<String> messages = new ArrayList<String>();
        messages.add(message);
        this.addIssue(ISSUE_NO_CONFIG_DATA, obj, messages);
    }

    private void addNoModelBuilderError(String message, EObject obj) {
        List<String> messages = new ArrayList<String>();
        messages.add(message);
        this.addIssue(ISSUE_NO_MODEL_BUILDER, obj, messages);
    }

}
