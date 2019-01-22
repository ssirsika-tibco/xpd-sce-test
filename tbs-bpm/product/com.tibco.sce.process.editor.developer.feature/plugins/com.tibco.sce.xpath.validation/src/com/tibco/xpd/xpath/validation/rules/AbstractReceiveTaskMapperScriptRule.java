/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpath.validation.tools.ReceiveTaskMapperScriptTool;
import com.tibco.xpd.xpath.validation.tools.XPathScriptTool;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

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
public abstract class AbstractReceiveTaskMapperScriptRule extends
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
        List<ScriptInformation> scriptInformationList =
                getScriptInformationFromReceiveTaskList(flowContainer);
        if (scriptInformationList == null) {
            return;
        }
        IValidationScope validationScope = getScope();
        for (ScriptInformation scriptInformation : scriptInformationList) {
            XPathScriptTool tool = validationScope.getTool(
                    ReceiveTaskMapperScriptTool.class, scriptInformation);
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

    protected List<ScriptInformation> getScriptInformationFromReceiveTaskList(
            FlowContainer flowContainer) {
        List<TaskReceive> receiveTaskList = null;
        if (flowContainer instanceof Process) {
            process = (Process) flowContainer;
            receiveTaskList = ProcessScriptUtil.getReceiveTask(process);
        } else if (flowContainer instanceof ActivitySet) {
            ActivitySet activitySet = (ActivitySet) flowContainer;
            process = ((ActivitySet) flowContainer).getProcess();
            receiveTaskList = ProcessScriptUtil.getReceiveTask(activitySet);
        }
        List<ScriptInformation> scriptInformationList = new ArrayList<ScriptInformation>();
        if (receiveTaskList != null) {
            for (Iterator<TaskReceive> iterator = receiveTaskList.iterator(); iterator
                    .hasNext();) {
                TaskReceive taskReceive = (TaskReceive) iterator.next();
                if (taskReceive != null) {
                    // Get the mapped and unmapped script informations
                    List<ScriptInformation> allScriptInformationList =
                            ProcessScriptUtil
                                    .getAllReceiveTaskScriptInformations(Xpdl2ModelUtil
                                            .getParentActivity(taskReceive),
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
        return XPathScriptParserUtil.WEBSERVICE_TASK;
    }
}
