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
import com.tibco.xpd.xpath.validation.tools.WebServiceMappedScriptInputTool;
import com.tibco.xpd.xpath.validation.tools.XPathScriptTool;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
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
public abstract class AbstractWebServiceMappedScriptInputRule extends
    AbstractXpathScriptRule {

    @Override
    public void validate(FlowContainer flowContainer) {
        List<DataMapping> dataMappingList = getScriptDataMappingFromWebServiceTaskList(flowContainer);
        if (dataMappingList == null) {
            return;
        }
        IValidationScope validationScope = getScope();
        for (DataMapping dataMapping : dataMappingList) {
            XPathScriptTool tool = validationScope.getTool(
                    WebServiceMappedScriptInputTool.class, dataMapping);
            ScriptInformation scriptInformation = ProcessScriptUtil
                    .getScriptInformationFromDataMapping(dataMapping);
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

    protected List<DataMapping> getScriptDataMappingFromWebServiceTaskList(
            FlowContainer flowContainer) {
        List<Activity> taskScriptList = null;
        if (flowContainer instanceof Process) {
            process = (Process) flowContainer;
            taskScriptList = ProcessScriptUtil.getWebServiceTasks(process);
        } else if (flowContainer instanceof ActivitySet) {
            ActivitySet activitySet = (ActivitySet) flowContainer;
            process = ((ActivitySet) flowContainer).getProcess();
            taskScriptList = ProcessScriptUtil.getWebServiceTasks(activitySet);
        }
        List<DataMapping> dataMappingList = new ArrayList<DataMapping>();
        if (taskScriptList != null) {
            for (Iterator<Activity> iterator = taskScriptList.iterator(); iterator
                    .hasNext();) {
                Activity activity = (Activity) iterator.next();
                if (activity != null) {
                    List<DataMapping> tempDataMappingList = ProcessScriptUtil
                            .getScriptDataMappingForServiceWithScriptType(
                                    activity, DirectionType.IN_LITERAL,
                                    getScriptGrammar());
                    if (tempDataMappingList != null) {
                        dataMappingList.addAll(tempDataMappingList);
                    }
                }
            }
        }
        return dataMappingList;

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
