/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.script;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 05 March 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class MessageEventXPathScriptFieldResolver extends
        AbstractMappingXPathScriptProcessFieldResolver {

    @Override
    protected Command getSetScriptInformationScriptCommand(
            EditingDomain editingDomain, String strScript,
            ScriptInformation scriptInformation) {
        Command cmd =
                Xpdl2WsdlUtil.getSetWebServiceTaskScriptCommand(editingDomain,
                        strScript,
                        scriptInformation,
                        getGrammarType());
        return cmd;
    }

    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        return ProcessScriptUtil.getAllEventScriptInformations(activity,
                getGrammarType());
    }

    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return ProcessScriptUtil.isMessageEventWithScriptType(activity,
                getGrammarType());
    }

    @Override
    protected String getTaskName() {
        return ProcessJsConsts.MESSAGE_EVENT;
    }

    @Override
    protected String getGrammarType() {
        return ProcessXPathConsts.XPATH_GRAMMAR;
    }

    @Override
    protected String getDefaultDestination() {
        return ProcessXPathConsts.XPATH_DESTINATION;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {
        if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
            /*
             * There is only one situation for message event with IN direction
             * and that's input to WSDL Output from process.
             */
            return Messages.MappingFieldResolver_OutputFromProcess_label;

        } else {
            /*
             * If it's not a process api activity then must be a throw message
             * so it's "Input to Service"
             */
            return Messages.MappingFieldResolver_InputToService_label;
        }
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {
        /*
         * There is only one situation for message event with OUT direction and
         * that's output from WSDL into process.
         */
        return Messages.MappingFieldResolver_InputToProcess_label;
    }

}
