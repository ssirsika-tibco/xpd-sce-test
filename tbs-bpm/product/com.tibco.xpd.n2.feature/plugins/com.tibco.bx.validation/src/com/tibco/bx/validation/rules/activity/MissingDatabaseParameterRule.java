/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.Collections;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Ensure that all database task parameters have been assigned to process data
 * fields.
 * 
 * @author aallway
 * @since 20 Sep 2011
 */
public class MissingDatabaseParameterRule extends
        ProcessActivitiesValidationRule {

    private static final String ISSUE_DATABASE_PARAMETER_UNASSIGNED =
            "bx.databaseParamUnassigned"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        if (TaskImplementationTypeDefinitions.DATABASE_SERVICE
                .equals(TaskObjectUtil
                        .getTaskImplementationExtensionId(activity))) {

            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();

                TaskService taskService = task.getTaskService();

                if (taskService != null) {
                    DatabaseType databaseObj =
                            (DatabaseType) Xpdl2ModelUtil
                                    .getOtherElement(taskService,
                                            DatabasePackage.eINSTANCE
                                                    .getDocumentRoot_Database());
                    if (databaseObj != null) {
                        if (databaseObj.getOperation() != null
                                && databaseObj.getOperation().getParameters() != null) {

                            Message messageIn = taskService.getMessageIn();
                            if (messageIn != null) {
                                for (DataMapping mapping : messageIn
                                        .getDataMappings()) {
                                    Expression actual = mapping.getActual();

                                    if (actual == null
                                            || actual.getText() == null
                                            || actual.getText().length() == 0) {
                                        addIssue(ISSUE_DATABASE_PARAMETER_UNASSIGNED,
                                                activity,
                                                Collections
                                                        .singletonList(mapping
                                                                .getFormal()));
                                    }
                                }
                            }

                            Message messageOut = taskService.getMessageOut();
                            if (messageOut != null) {
                                for (DataMapping mapping : messageOut
                                        .getDataMappings()) {
                                    Expression actual = mapping.getActual();

                                    if (actual == null
                                            || actual.getText() == null
                                            || actual.getText().length() == 0) {
                                        addIssue(ISSUE_DATABASE_PARAMETER_UNASSIGNED,
                                                activity,
                                                Collections
                                                        .singletonList(mapping
                                                                .getFormal()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return;
    }
}
