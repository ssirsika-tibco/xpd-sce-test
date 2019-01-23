/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils.DatabaseUtil;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class DatabaseStoredProcedureRule extends ActivityValidationRule {

    /** The issue ID. */
    private static final String ID = "bpmn.dev.invalidStoredProcedureName"; //$NON-NLS-1$

    /** Regex for stored proc name validation. */
    private static final String CHARS = "[^ \\.~\\?:@\u0000\t\n]*\\.?[^ \\.~\\?:@\u0000\t\n]*"; //$NON-NLS-1$

    /** Pattern for checking stored procedures. */
    private static final Pattern pattern = Pattern.compile(CHARS);

    private static final String NOSQL_ID = "bpmn.dev.dbNoSQLProvided"; //$NON-NLS-1$

    /**
     * @param activity
     *            The activity to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#
     *      validate(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public void validate(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            TaskService service = task.getTaskService();
            if (service != null) {
                String type = (String) Xpdl2ModelUtil.getOtherAttribute(
                        service, XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
                if ("Database".equals(type)) { //$NON-NLS-1$
                    checkTaskService(activity, service);
                }
            }
        }

    }

    /**
     * @param activity
     *            The activity to check.
     * @param service
     *            The task service to check.
     */
    private void checkTaskService(Activity activity, TaskService service) {
        DatabaseType db = DatabaseUtil.getDatabaseObj(service);
        if (db != null) {
            OperationType op = db.getOperation();
            if (op != null && SqlType.STORED_PROC_LITERAL.equals(op.getType())) {
                String sql = op.getSql();

                if (sql != null && sql.length() > 0) {
                    Matcher matcher = pattern.matcher(sql);
                    if (!matcher.matches()) {
                        addIssue(ID, activity);
                    }
                } else {

                    String missing = "SQL"; //$NON-NLS-1$
                    if (SqlType.STORED_PROC_LITERAL.equals(op.getType())) {
                        missing = "Stored Procedure"; //$NON-NLS-1$
                    }

                    addIssue(NOSQL_ID, activity, Collections
                            .singletonList(missing));
                }
            }
        }
    }
}
