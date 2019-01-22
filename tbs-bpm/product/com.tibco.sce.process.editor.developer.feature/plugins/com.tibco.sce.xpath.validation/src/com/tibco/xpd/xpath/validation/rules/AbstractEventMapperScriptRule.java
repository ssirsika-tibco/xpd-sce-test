/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpath.validation.tools.EventMapperScriptTool;
import com.tibco.xpd.xpath.validation.tools.XPathScriptTool;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
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
public abstract class AbstractEventMapperScriptRule extends
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
                getScriptInformationFromEventList(flowContainer);
        if (scriptInformationList == null) {
            return;
        }
        IValidationScope validationScope = getScope();
        for (ScriptInformation scriptInformation : scriptInformationList) {
            XPathScriptTool tool =
                    validationScope.getTool(EventMapperScriptTool.class,
                            scriptInformation);
            if (tool != null && scriptInformation != null) {
                List<ErrorMessage> errorList =
                        tool.getErrorList(scriptInformation.getId(),
                                validationScope.getCurrentDestination());
                if (errorList == null) {
                    continue;
                }
                // See if it has to be in the ScriptInformation Section
                reportError(scriptInformation, errorList);
                List<ErrorMessage> warningList =
                        tool.getWarningList(scriptInformation.getId(),
                                validationScope.getCurrentDestination());
                if (warningList == null) {
                    continue;
                }
                reportWarning(scriptInformation, warningList);
            }
        }
    }

    protected List<ScriptInformation> getScriptInformationFromEventList(
            FlowContainer flowContainer) {
        List<ScriptInformation> scriptInformationList =
                new ArrayList<ScriptInformation>();

        for (Activity activity : flowContainer.getActivities()) {
            Event event = activity.getEvent();

            if (event != null) {
                /*
                 * Now that Signal's can have data mappings we have to make sure
                 * we don't validate those as this class is really validation
                 * for MESSAGE events!
                 * 
                 * XPD-3055. But it is also handy because previously we did not
                 * used to get to validate Error (Catch or Throw) event
                 * mappings. So now we can.
                 */
                if (EventObjectUtil.isWebServiceRelatedEvent(activity)) {

                    // Get the mapped and unmapped script informations
                    List<ScriptInformation> allScriptInformationList =
                            ProcessScriptUtil
                                    .getAllEventScriptInformations(Xpdl2ModelUtil
                                            .getParentActivity(event),
                                            getScriptGrammar());
                    if (allScriptInformationList != null
                            && !allScriptInformationList.isEmpty()) {
                        scriptInformationList.addAll(allScriptInformationList);
                    }
                }
            }
        }

        return scriptInformationList;
    }

    @Override
    protected String getScriptContext() {
        return XPathScriptParserUtil.WEBSERVICE_TASK;
    }
}
