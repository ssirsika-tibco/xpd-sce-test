/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpath.validation.tools.BwServiceMapperScriptTool;
import com.tibco.xpd.xpath.validation.tools.XPathScriptTool;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * 
 * <p>
 * <i>Created: 5 Dec 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractBwServiceMapperScriptRule extends
    AbstractXpathScriptRule {
    

    @Override
    protected List<String> getSubstitutionList(ErrorMessage errorMessage) {
        List<String> tempMsgList = new ArrayList<String>();
        tempMsgList.add(Integer.toString(errorMessage.getLineNumber()));
        tempMsgList.add(Integer.toString(errorMessage.getColumnNumber()));
        tempMsgList.add(errorMessage.getErrorMessage());
        return tempMsgList;
    }
    
    @Override
    public void validate(FlowContainer flowContainer) {
        List<ScriptInformation> scriptInformationList = getScriptInformationFromBwServiceList(flowContainer);
        if (scriptInformationList == null) {
            return;
        }
        IValidationScope validationScope = getScope();
        for (ScriptInformation scriptInformation : scriptInformationList) {
            XPathScriptTool tool = validationScope.getTool(
                    BwServiceMapperScriptTool.class, scriptInformation);
           if (tool != null && scriptInformation != null) {
                List<ErrorMessage> errorList = tool.getErrorList(
                        scriptInformation.getId(), validationScope
                                .getCurrentDestination());
                if (errorList == null) {
                    continue;
                }
                // See if it has to be in the ScriptInformation Section
                reportError(scriptInformation, errorList);
                List<ErrorMessage> warningList = tool.getWarningList(
                        scriptInformation.getId(), validationScope
                                .getCurrentDestination());
                if (warningList == null) {
                    continue;
                }
                reportWarning(scriptInformation, warningList);
            }
        }
    }

    protected List<ScriptInformation> getScriptInformationFromBwServiceList(
            FlowContainer flowContainer) {
        List<Activity> bwServiceList = null;
        if (flowContainer instanceof Process) {
            process = (Process) flowContainer;
            bwServiceList = ProcessScriptUtil.getBwServiceTasks(process);
        } else if (flowContainer instanceof ActivitySet) {
            ActivitySet activitySet = (ActivitySet) flowContainer;
            process = ((ActivitySet) flowContainer).getProcess();
            bwServiceList = ProcessScriptUtil.getBwServiceTasks(activitySet);
        }
        List<ScriptInformation> scriptInformationList = new ArrayList<ScriptInformation>();
        if (bwServiceList != null) {
            for (Iterator<Activity> iterator = bwServiceList.iterator(); iterator
                    .hasNext();) {
                Activity activity = (Activity) iterator.next();
                if (activity != null) {
                    // Get the mapped and unmapped script informations
                    List<ScriptInformation> allScriptInformationList =
                            ProcessScriptUtil
                                    .getAllServiceScriptInformations(activity,
                                            getScriptGrammar());
                    if (allScriptInformationList != null) {
                        scriptInformationList
                                .addAll(allScriptInformationList);
                    }
                }
            }
        }
        return scriptInformationList;
    }

    private Process process = null;

    protected Process getProcess() {
        return this.process;
    }

    @Override
    protected String getScriptContext() {
        return XPathScriptParserUtil.BWSERVICE_TASK;
    }
}
