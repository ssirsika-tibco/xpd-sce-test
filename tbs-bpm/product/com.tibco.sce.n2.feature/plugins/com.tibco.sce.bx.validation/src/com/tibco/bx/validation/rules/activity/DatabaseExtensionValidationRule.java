/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author bharge
 * @since 12 Jul 2011
 */
public class DatabaseExtensionValidationRule extends ActivityValidationRule {

    public static final String ISSUE_REQUIRED_FIELD =
            "n2pe.ext.database.missingRequiredField"; //$NON-NLS-1$

    public static final String ISSUE_NOT_SUPPORTED_FIELD =
            "n2pe.ext.database.notSupportedField"; //$NON-NLS-1$

    public static final String LINE_SEPARATOR = System
            .getProperty("line.separator"); //$NON-NLS-1$

    Map xpdMsgMap = new HashMap();

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    public void validate(Activity activity) {
        Implementation impl = activity.getImplementation();
        EObject databaseObj = null;
        if (impl instanceof Task) {
            Task t = (Task) impl;
            if (t.getTaskService() != null) {
                TaskService taskService = t.getTaskService();
                ImplementationType implementationType =
                        taskService.getImplementation();
                if (implementationType != null) {
                    int implementationTypeValue = implementationType.getValue();
                    switch (implementationTypeValue) {
                    case ImplementationType.OTHER:
                        databaseObj =
                                (EObject) Xpdl2ModelUtil
                                        .getOtherElement(taskService,
                                                DatabasePackage.eINSTANCE
                                                        .getDocumentRoot_Database());

                        validateExtension(activity, databaseObj, taskService);

                        break;

                    case ImplementationType.WEB_SERVICE:
                    case ImplementationType.UNSPECIFIED:
                        break;
                    }// endswitch
                }
            }
        }
    }

    private void validateExtension(EObject container, EObject model,
            TaskService taskService) {
        if (!(model instanceof DatabaseType)) {
            return;
        }

        Activity activity = (Activity) container;

        String className = model.eClass().getInstanceClass().getName();

        DatabaseType xpdDatabase = (DatabaseType) model;

        com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType xpdOperationType =
                xpdDatabase.getOperation();

        SqlType operation = xpdOperationType.getType();

        String operationName = operation.getLiteral();
        /*
         * select is back in if (operationName.equals("Select")) {
         * addError(ISSUE_NOT_SUPPORTED_FIELD, "Select Operation", container); }
         */

        String sql = xpdOperationType.getSql();

        if (sql == null || sql.length() <= 0) {
            addError(ISSUE_REQUIRED_FIELD, "sql", container);} //$NON-NLS-1$

        // parameters validation
        int numInParameters = 0;
        int numOutParameters = 0;
        String outField = null;
        com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType xpdParametersType =
                xpdOperationType.getParameters();
        if (xpdParametersType != null) {

            Message xpdMsgIn = taskService.getMessageIn();
            Iterator xpdMsgItr = xpdMsgIn.getDataMappings().iterator();
            while (xpdMsgItr.hasNext()) {
                DataMapping mapping = (DataMapping) xpdMsgItr.next();
                xpdMsgMap.put(mapping.getFormal(), mapping);
                numInParameters++;
            }

            Message xpdMsgOut = taskService.getMessageOut();
            xpdMsgItr = xpdMsgOut.getDataMappings().iterator();
            if (xpdMsgItr != null) {
                while (xpdMsgItr.hasNext()) {
                    DataMapping mapping = (DataMapping) xpdMsgItr.next();
                    if (outField == null && numOutParameters <= 0) {
                        if (null != mapping.getActual()
                                && null != mapping.getActual().getText()) {
                            outField = mapping.getActual().getText();
                        }
                    }
                    numOutParameters++;
                }
            }

        } else {
            Message xpdMsgIn = taskService.getMessageIn();
            Iterator xpdMsgItr = xpdMsgIn.getDataMappings().iterator();
            while (xpdMsgItr.hasNext()) {
                DataMapping mapping = (DataMapping) xpdMsgItr.next();
                xpdMsgMap.put(mapping.getFormal(), mapping);
                numInParameters++;
            }

            Message xpdMsgOut = taskService.getMessageOut();
            xpdMsgItr = xpdMsgOut.getDataMappings().iterator();
            while (xpdMsgItr.hasNext()) {
                DataMapping mapping = (DataMapping) xpdMsgItr.next();
                if (outField == null && numOutParameters <= 0) {
                    if (null != mapping.getActual()
                            && null != mapping.getActual().getText()) {
                        outField = mapping.getActual().getText();
                    }
                }
                numOutParameters++;
            }

        }

        if (operationName.equals("Select") && sql != null && sql.length() != 0) { //$NON-NLS-1$

            String statement = sql.trim();
            statement = statement.toLowerCase();

            if (isSingleStatement(statement)) {
                if (numOutParameters <= 0) {
                    addError("n2pe.ext.database.outParameterWrongNumber", //$NON-NLS-1$
                            null,
                            container);
                }

                if (isPreparedStatement(statement)) {
                    int noOfParams = getNoOfParams(statement);
                    if (numInParameters != noOfParams) {
                        addError("n2pe.ext.database.wrongNumberOfInputParameters", //$NON-NLS-1$
                                new Integer(noOfParams).toString(),
                                container);
                    }
                }
            }
        }

        if (numOutParameters > 0) {

            // DataField xpdDataField =
            // activity.getProcess().getPackage().getDataField(outField);
            EList xpdDataFields =
                    activity.getProcess().getPackage().getDataFields();
            Iterator xpdDataFieldsItr = xpdDataFields.listIterator();
            while (xpdDataFieldsItr.hasNext()) {
                DataField xpdDataField = (DataField) xpdDataFieldsItr.next();
                if (xpdDataField.getName().equals(outField)) {
                    DataType dataType = xpdDataField.getDataType();
                    if (!(dataType instanceof BasicType)) {
                        addError("n2pe.ext.database.outParameterWrongType", //$NON-NLS-1$
                                outField,
                                container);
                        break;
                    }
                    BasicType basicType = (BasicType) dataType;

                    String type = basicType.getType().getName();

                    if (!type.equalsIgnoreCase("INTEGER")) { //$NON-NLS-1$
                        addError("n2pe.ext.database.outParameterWrongType", //$NON-NLS-1$
                                outField,
                                container);
                    }
                }
            }

        }

        if (operationName.equals("Update") && sql != null && sql.length() != 0) { //$NON-NLS-1$
            String statement = sql.trim();
            statement = statement.toLowerCase();

            if (isSingleStatement(statement)) {
                if (!statement.startsWith("insert") //$NON-NLS-1$
                        && !statement.startsWith("delete") //$NON-NLS-1$
                        && !statement.startsWith("update")) { //$NON-NLS-1$
                    addError("n2pe.ext.database.wrongUpdateStatement", //$NON-NLS-1$
                            null,
                            container);
                }

                if (isPreparedStatement(statement)) {
                    int noOfParams = getNoOfParams(statement);
                    if (numInParameters != noOfParams) {
                        addError("n2pe.ext.database.wrongNumberOfInputParameters", //$NON-NLS-1$
                                new Integer(noOfParams).toString(),
                                container);
                    }
                }
            }
        }

    }

    private boolean isSingleStatement(String statement) {
        if (statement.startsWith("begin")) { //$NON-NLS-1$
            return false;
        }
        if (countPatternOccurs(statement, COMMMA_PATTERN) >= 2)
            return false;

        return true;
    }

    private static final String COMMA_REGEX = ";"; //$NON-NLS-1$

    private static final Pattern COMMMA_PATTERN = Pattern.compile(COMMA_REGEX);

    private int countPatternOccurs(String input, Pattern pattern) {

        Matcher m = pattern.matcher(input); // get a matcher object
        int count = 0;
        while (m.find()) {
            count++;
        }

        return count;

    }

    private boolean isOutType(String type) {
        boolean retval = false;
        if (type.equals("OUT") || type.equals("INOUT")) //$NON-NLS-1$//$NON-NLS-2$
            retval = true;

        return retval;
    }

    private boolean isInType(String type) {
        boolean retval = false;
        if (type.equals("IN") || type.equals("INOUT")) //$NON-NLS-1$ //$NON-NLS-2$
            retval = true;

        return retval;
    }

    private boolean isPreparedStatement(String statement) {
        boolean retval = false;
        if (statement != null && statement.indexOf('?') != -1) {
            // for preparedStatement only
            retval = true;
        }

        return retval;
    }

    private int getNoOfParams(String statement) {
        if (statement == null || statement.indexOf('?') == -1) {
            return 0;
        }

        int count = 1;
        int pos = statement.indexOf('?');
        while (true) {
            pos = statement.indexOf('?', pos + 1);
            if (pos != -1)
                count++;
            else
                break;
        }

        return count;
    }

    private void addError(String msgCode, String argument, EObject obj) {
        List<String> messages = new ArrayList<String>();
        messages.add(argument);
        this.addIssue(msgCode, obj, messages);
    }

}
