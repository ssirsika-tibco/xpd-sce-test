/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation Rules for Global-Data-Service-Task {@link GlobalDataOperation}.
 * 
 * @author kthombar
 * @since 22-Jul-2014
 */
public class GlobalDataServiceTaskOperationsRule extends ProcessValidationRule {

    /**
     * It is recommended that you use delete by non-array case reference field
     * as this will ensure that the case object is not in use by other process
     * instances (which can cause those instances to halt).
     */
    private static String ISSUE_USE_DELETE_BY_CASE_REF_FIELD_OPERATION =
            "bx.useDeleteByCaseRefField"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param container
     */
    @Override
    public void validate(Process process) {

        if (Xpdl2ModelUtil.isBusinessProcess(process)
                || ProcessInterfaceUtil.isProcessEngineServiceProcess(process)) {

            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {

                TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

                if (TaskType.SERVICE_LITERAL.equals(taskType)) {

                    String extensionId =
                            TaskObjectUtil
                                    .getTaskImplementationExtensionId(activity);

                    if (TaskImplementationTypeDefinitions.GLOBAL_DATA
                            .equals(extensionId)) {

                        Implementation impl = activity.getImplementation();

                        if (impl instanceof Task) {

                            TaskService taskService =
                                    ((Task) impl).getTaskService();

                            Object otherElement =
                                    Xpdl2ModelUtil
                                            .getOtherElement(taskService,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_GlobalDataOperation());

                            if (otherElement instanceof GlobalDataOperation) {
                                /*
                                 * we are interested only in Global Data
                                 * Operations
                                 */
                                validateGlobalDataOperation((GlobalDataOperation) otherElement,
                                        activity);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Validates the {@link GlobalDataOperation}
     * 
     * @param globalDataOperation
     *            the Global data Operation
     * @param globalDataServiceTask
     *            the Global Data Service Task
     */
    private void validateGlobalDataOperation(
            GlobalDataOperation globalDataOperation,
            Activity globalDataServiceTask) {

        CaseReferenceOperationsType caseReferenceOperations =
                globalDataOperation.getCaseReferenceOperations();

        if (caseReferenceOperations != null) {

            if (caseReferenceOperations.getDelete() != null) {

                ProcessRelevantData processRelevantData =
                        getProcessRelevantData(caseReferenceOperations.getCaseRefField(),
                                globalDataServiceTask);

                if (processRelevantData != null
                        && processRelevantData.isIsArray()) {
                    /*
                     * Add warning if we have a Delete Case Ref Operation and
                     * the Process Data referencing the Case Class is an array
                     * (not single instance)
                     */
                    addIssue(ISSUE_USE_DELETE_BY_CASE_REF_FIELD_OPERATION,
                            globalDataServiceTask);
                }
            }
        } else if (globalDataOperation.getCaseAccessOperations() != null) {

            CaseAccessOperationsType caseAccessOperation =
                    globalDataOperation.getCaseAccessOperations();

            if (caseAccessOperation.getDeleteByCaseIdentifier() != null
                    || caseAccessOperation.getDeleteByCompositeIdentifiers() != null) {
                /*
                 * Add warning if we have a Case Access Operation of type Delete
                 * by case identifier or Delete by composite identifier.
                 */
                addIssue(ISSUE_USE_DELETE_BY_CASE_REF_FIELD_OPERATION,
                        globalDataServiceTask);
            }
        }
    }

    /**
     * Find and return the process data field with the given name within the
     * scope if this activity.
     * 
     * @param name
     * @param activity
     * @return the process data field with the given name within the scope if
     *         the passed activity.
     */
    private ProcessRelevantData getProcessRelevantData(String name,
            Activity activity) {
        if (name != null) {
            List<ProcessRelevantData> relevantData =
                    ProcessDataUtil.getProcessRelevantData(activity);
            for (ProcessRelevantData data : relevantData) {
                if (name.equals(data.getName())) {
                    return data;
                }
            }
        }
        return null;
    }
}
