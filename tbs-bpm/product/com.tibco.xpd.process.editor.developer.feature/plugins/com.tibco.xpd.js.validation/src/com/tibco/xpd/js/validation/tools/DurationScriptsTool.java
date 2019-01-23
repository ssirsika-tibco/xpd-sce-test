/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ErrorType;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 26 Mar 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class DurationScriptsTool extends ScriptTool {

    private Activity activity = null;

    public DurationScriptsTool(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected Process getProcess() {
        return activity.getProcess();
    }

    protected String getScript() {
        return null;
    }

    protected String getDaysScript() {
        String script = null;
        if (getDurationCalculation() != null) {
            DurationCalculation durationCalculation = getDurationCalculation();
            Expression expression = durationCalculation.getDays();
            if (expression != null) {
                script = expression.getText();
            }
        }
        return script;
    }

    protected String getHoursScript() {
        String script = null;
        if (getDurationCalculation() != null) {
            DurationCalculation durationCalculation = getDurationCalculation();
            Expression expression = durationCalculation.getHours();
            if (expression != null) {
                script = expression.getText();
            }
        }
        return script;
    }

    protected String getMinutesScript() {
        String script = null;
        if (getDurationCalculation() != null) {
            DurationCalculation durationCalculation = getDurationCalculation();
            Expression expression = durationCalculation.getMinutes();
            if (expression != null) {
                script = expression.getText();
            }
        }
        return script;
    }

    protected String getSecondsScript() {
        String script = null;
        if (getDurationCalculation() != null) {
            DurationCalculation durationCalculation = getDurationCalculation();
            Expression expression = durationCalculation.getSeconds();
            if (expression != null) {
                script = expression.getText();
            }
        }
        return script;
    }

    protected String getMicroSecondsScript() {
        String script = null;
        if (getDurationCalculation() != null) {
            DurationCalculation durationCalculation = getDurationCalculation();
            Expression expression = durationCalculation.getMicroseconds();
            if (expression != null) {
                script = expression.getText();
            }
        }
        return script;
    }

    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.DURATION_CALCULATION;
    }

    @Override
    protected Activity getActivity() {
        return this.activity;
    }

    private DurationCalculation getDurationCalculation() {
        DurationCalculation durationCalculation = null;
        if (getActivity() != null) {
            durationCalculation =
                    (DurationCalculation) Xpdl2ModelUtil
                            .getOtherElement(getActivity(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DurationCalculation());
        }
        return durationCalculation;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#validateScript()
     * 
     */
    @Override
    protected void validateScript() {
        Process process = getProcess();
        DurationCalculation durationCalculation = getDurationCalculation();
        if (durationCalculation != null && areConditionsOk()) {
            List<IValidationStrategy> validationStrategyList =
                    getValidationStrategyList(process);
            List<String> processDestinationList =
                    getProcessDestinationList(process);
            SymbolTable symbolTable = new SymbolTable();
            symbolTable.setInput(getEObject());
            symbolTable
                    .setScriptRelevantDataTypeMap(getScriptRelevantDataTypeMap());
            IVarNameResolver varNameResolver = new DefaultVarNameResolver();
            Map<String, List<ErrorMessage>> validationErrorMap =
                    new HashMap<String, List<ErrorMessage>>();
            Map<String, List<ErrorMessage>> validationWarningMap =
                    new HashMap<String, List<ErrorMessage>>();
            JScriptParser parser = null;
            // Validate days script
            String daysScript = getDaysScript();
            if (daysScript != null && daysScript.trim().length() > 0) {
                daysScript = addSemiColon(daysScript);
                parser = ScriptParserUtil.validateScript(daysScript,
                        processDestinationList,
                        validationStrategyList,
                        symbolTable,
                        varNameResolver,
                        validationErrorMap,
                        validationWarningMap,
                        getScriptType());
                addAdvancedPropertyName(validationErrorMap,
                        Messages.property_duration_days);
                errorHashMap.put(eObject.getId(), validationErrorMap);
                addAdvancedPropertyName(validationWarningMap,
                        Messages.property_duration_days);
                warningHashMap.put(eObject.getId(), validationWarningMap);
                
            }
            // Validate hours script
            String hoursScript = getHoursScript();
            if (hoursScript != null && hoursScript.trim().length() > 0) {
                hoursScript = addSemiColon(hoursScript);
                parser = ScriptParserUtil.validateScript(hoursScript,
                        processDestinationList,
                        validationStrategyList,
                        symbolTable,
                        varNameResolver,
                        validationErrorMap,
                        validationWarningMap,
                        getScriptType());
                addAdvancedPropertyName(validationErrorMap,
                        Messages.property_duration_hours);
                errorHashMap.put(eObject.getId(), validationErrorMap);
                addAdvancedPropertyName(validationWarningMap,
                        Messages.property_duration_hours);
                warningHashMap.put(eObject.getId(), validationWarningMap);
            }
            // Validate minutes script
            String minutesScript = getMinutesScript();
            if (minutesScript != null && minutesScript.trim().length() > 0) {
                minutesScript = addSemiColon(minutesScript);
                parser = ScriptParserUtil.validateScript(minutesScript,
                        processDestinationList,
                        validationStrategyList,
                        symbolTable,
                        varNameResolver,
                        validationErrorMap,
                        validationWarningMap,
                        getScriptType());
                addAdvancedPropertyName(validationErrorMap,
                        Messages.property_duration_minutes);
                errorHashMap.put(eObject.getId(), validationErrorMap);
                addAdvancedPropertyName(validationWarningMap,
                        Messages.property_duration_minutes);
                warningHashMap.put(eObject.getId(), validationWarningMap);
            }
            // Validate seconds script
            String secondsScript = getSecondsScript();
            if (secondsScript != null && secondsScript.trim().length() > 0) {
                secondsScript = addSemiColon(secondsScript);
                parser = ScriptParserUtil.validateScript(secondsScript,
                        processDestinationList,
                        validationStrategyList,
                        symbolTable,
                        varNameResolver,
                        validationErrorMap,
                        validationWarningMap,
                        getScriptType());
                addAdvancedPropertyName(validationErrorMap,
                        Messages.property_duration_seconds);
                errorHashMap.put(eObject.getId(), validationErrorMap);
                addAdvancedPropertyName(validationWarningMap,
                        Messages.property_duration_seconds);
                warningHashMap.put(eObject.getId(), validationWarningMap);
            }
            // Validate microseconds script
            String microSecondsScript = getMicroSecondsScript();
            if (microSecondsScript != null
                    && microSecondsScript.trim().length() > 0) {
                microSecondsScript = addSemiColon(microSecondsScript);
                parser = ScriptParserUtil.validateScript(microSecondsScript,
                        processDestinationList,
                        validationStrategyList,
                        symbolTable,
                        varNameResolver,
                        validationErrorMap,
                        validationWarningMap,
                        getScriptType());
                addAdvancedPropertyName(validationErrorMap,
                        Messages.property_duration_microseconds);
                errorHashMap.put(eObject.getId(), validationErrorMap);
                addAdvancedPropertyName(validationWarningMap,
                        Messages.property_duration_microseconds);
                warningHashMap.put(eObject.getId(), validationWarningMap);
            }

            validationStrategyList = null;
            symbolTable.dispose();
            symbolTable = null;
            parser = null;
        }
    }

    private boolean areConditionsOk() {
        // Years|Months|Weeks script should not have a value
        DurationCalculation durationCalculation = getDurationCalculation();
        if (durationCalculation != null) {
            Expression years = durationCalculation.getYears();
            if (years != null) {
                String yearsScript = years.getText();
                if (yearsScript != null && !yearsScript.equals("")) { //$NON-NLS-1$
                    return false;
                }
            }
            Expression months = durationCalculation.getMonths();
            if (months != null) {
                String monthsScript = months.getText();
                if (monthsScript != null && !monthsScript.equals("")) { //$NON-NLS-1$
                    return false;
                }
            }
            Expression weeks = durationCalculation.getWeeks();
            if (weeks != null) {
                String weeksScript = weeks.getText();
                if (weeksScript != null && !weeksScript.equals("")) { //$NON-NLS-1$
                    return false;
                }
            }
        }
        return true;
    }

    private String addSemiColon(String script) {
        if (isAutoTerminateSingleLineStatement()) {
            return super.autoTerminateSingleLineStatement(script);
        }

        return script;
    }

    private void addAdvancedPropertyName(
            Map<String, List<ErrorMessage>> validationMap, String propertyName) {
        if (validationMap != null) {
            Set<String> keySet = validationMap.keySet();
            if (keySet != null) {
                for (String key : keySet) {
                    List<ErrorMessage> errorMessageList =
                            validationMap.get(key);
                    if (errorMessageList != null) {
                        for (ErrorMessage errorMessage : errorMessageList) {
                            ErrorType errorType = errorMessage.getErrorType();
                            if (errorType == null) {
                                errorType =
                                        new ErrorType(
                                                ProcessScriptContextConstants.ADVANCED_PROPERTY__VALIDATION_KEY,
                                                null);
                            }
                            Map<String, String> resolutionInformation =
                                    errorType.getResolutionInformation();
                            String existingName =
                                    resolutionInformation
                                            .get(ProcessScriptContextConstants.ADVANCED_PROPERTY_NAME_VALIDATION_KEY);
                            if (existingName == null) {
                                resolutionInformation
                                        .put(ProcessScriptContextConstants.ADVANCED_PROPERTY_NAME_VALIDATION_KEY,
                                                propertyName);
                            }
                        }
                    }
                }
            }
        }
    }
}
