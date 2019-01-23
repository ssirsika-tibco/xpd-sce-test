/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.script;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.implementer.script.WsdlPartPath;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * 
 * <p>
 * <i>Created: 05 March 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class MessageMethodXPathScriptFieldResolver extends
        AbstractMappingXPathScriptProcessFieldResolver {

    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        boolean interestedActivity = isInterestingActivity(activity);
        if (!interestedActivity) {
            return null;
        }
        CompoundCommand setActivityScriptCommand =
                new CompoundCommand(
                        Messages.AbstractMappingScriptProcessFieldResolver_ChangeNameReferences);
        // Make changes to mappings
        List<DataMapping> dataMappingList =
                getActivityDataMappingList(activity);
        if (dataMappingList != null) {
            for (DataMapping dataMapping : dataMappingList) {
                String parameterRef = getParameterRef(dataMapping);
                if (parameterRef != null) {
                    String translatedRef =
                            getTranslatedParameterRef(activity,
                                    parameterRef,
                                    nameMap);
                    if (translatedRef != null) {
                        String oldFormal = dataMapping.getFormal();
                        String newFormal =
                                translateScript(oldFormal,
                                        parameterRef,
                                        nameMap);
                        Expression oldExpression = dataMapping.getActual();
                        Expression newExpression =
                                Xpdl2Factory.eINSTANCE
                                        .createExpression(translatedRef);
                        newExpression.setScriptGrammar(oldExpression
                                .getScriptGrammar());
                        dataMapping.setActual(newExpression);
                        dataMapping.setFormal(newFormal);
                        setActivityScriptCommand
                                .append(SetCommand
                                        .create(editingDomain,
                                                dataMapping.eContainer(),
                                                Xpdl2Package.eINSTANCE
                                                        .getDataMapping(),
                                                dataMapping));
                    }
                }
            }
        }
        if (setActivityScriptCommand.isEmpty()) {
            return null;
        }
        return setActivityScriptCommand;
    }

    private String translateScript(String oldScript, String oldName,
            Map<String, String> nameMap) {
        String newScript = null;
        String newName = nameMap.get(oldName);
        if (newName != null && oldScript != null
                && oldScript.contains(WsdlPartPath.PART_ID + oldName)) {
            newScript =
                    oldScript.replace(WsdlPartPath.PART_ID + oldName,
                            WsdlPartPath.PART_ID + newName);
        }
        return newScript;
    }

    @Override
    protected Command getSetScriptInformationScriptCommand(
            EditingDomain editingDomain, String strScript,
            ScriptInformation scriptInformation) {
        return null;
    }

    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        return null;
    }

    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return ProcessScriptUtil.isMessageMethodWithScriptType(activity,
                getGrammarType());
    }

    @Override
    protected String getTaskName() {
        return ProcessJsConsts.MESSAGE_METHOD;
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
        /*
         * There is only one situation for message event with IN direction and
         * that's input to WSDL Output from process.
         */
        return Messages.MappingFieldResolver_OutputFromProcess_label;
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
