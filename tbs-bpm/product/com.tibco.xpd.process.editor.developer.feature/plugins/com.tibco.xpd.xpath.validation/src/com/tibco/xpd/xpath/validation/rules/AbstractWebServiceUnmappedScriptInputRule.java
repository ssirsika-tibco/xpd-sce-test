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
import com.tibco.xpd.xpath.validation.tools.WebServiceUnmappedScriptInputTool;
import com.tibco.xpd.xpath.validation.tools.XPathScriptTool;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * 
 * <p>
 * <i>Created: 07 February 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractWebServiceUnmappedScriptInputRule extends AbstractXpathScriptRule {

	@Override
	public void validate(FlowContainer flowContainer) {
		List<ScriptInformation> scriptInformationList = getScriptInformationFromWebServiceTaskList(flowContainer);
		if (scriptInformationList == null) {
			return;
		}
		IValidationScope validationScope = getScope();
		for (ScriptInformation scriptInformation : scriptInformationList) {
			XPathScriptTool tool = validationScope.getTool(WebServiceUnmappedScriptInputTool.class,
			        scriptInformation);
			List<ErrorMessage> errorList = tool
					.getErrorList(scriptInformation.getId(), validationScope
							.getCurrentDestination());
			if (errorList == null) {
				continue;
			}
			reportError(scriptInformation, errorList);
			List<ErrorMessage> warningList = tool.getWarningList(scriptInformation
					.getId(), validationScope.getCurrentDestination());
			if (warningList == null) {
				continue;
			}
			reportWarning(scriptInformation, warningList);
		}
	}

	protected List<ScriptInformation> getScriptInformationFromWebServiceTaskList(FlowContainer flowContainer) {
		List<Activity> taskScriptList = null;
		if (flowContainer instanceof Process) {
			process = (Process) flowContainer;
			taskScriptList = ProcessScriptUtil.getWebServiceTasks(process);
		} else if (flowContainer instanceof ActivitySet) {
			ActivitySet activitySet = (ActivitySet) flowContainer;
			process = ((ActivitySet) flowContainer).getProcess();
			taskScriptList = ProcessScriptUtil.getWebServiceTasks(activitySet);
		}
		List<ScriptInformation> scriptInformationList = new ArrayList<ScriptInformation>();
		if(taskScriptList != null){
		    for (Iterator<Activity> iterator = taskScriptList.iterator(); iterator
                    .hasNext();) {
		        Activity activity = (Activity) iterator.next();
                if (activity != null) {
                    List<ScriptInformation> tempScriptInformationList =
                            ProcessScriptUtil
                                    .getScriptInformationTasksWithScriptType(activity,
                                            getScriptGrammar(), DirectionType.IN_LITERAL.getLiteral());
                    if (tempScriptInformationList != null) {
                        scriptInformationList.addAll(tempScriptInformationList);
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
