/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.utils;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;
import com.tibco.xpd.implementer.nativeservices.java.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
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
public class JavaServiceTaskJavaScriptFieldResolver extends
        AbstractMappingJavaScriptProcessFieldResolver {

    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        boolean interestedActivity = isInterestingActivity(activity);
        if (!interestedActivity) {
            return null;
        }
        CompoundCommand removeMappingCommand = new CompoundCommand();
        List<DataMapping> dataMappingList =
                getActivityDataMappingList(activity);
        if (dataMappingList != null) {
            for (DataMapping dataMapping : dataMappingList) {
                String parameterRef = getParameterRef(dataMapping);
                if (parameterRef != null) {
                    if (hasMultipleMappings(parameterRef)) {
                        String removedRef =
                                getRemovedParameterRef(parameterRef,
                                        JavaServiceMappingUtil
                                                .getScriptParameterName(data
                                                        .getName()));
                        if (removedRef != null) {
                            Expression oldExpression = dataMapping.getActual();
                            Expression newExpression =
                                    Xpdl2Factory.eINSTANCE
                                            .createExpression(removedRef);
                            newExpression.setScriptGrammar(oldExpression
                                    .getScriptGrammar());
                            removeMappingCommand.append(SetCommand
                                    .create(editingDomain,
                                            dataMapping,
                                            Xpdl2Package.eINSTANCE
                                                    .getDataMapping_Actual(),
                                            newExpression));
                        }
                    } else {
                        if (JavaServiceMappingUtil
                                .getScriptParameterName(data.getName())
                                .equals(parameterRef)) {
                            removeMappingCommand.append(RemoveCommand
                                    .create(editingDomain,
                                            dataMapping.eContainer(),
                                            Xpdl2Package.eINSTANCE
                                                    .getDataMapping(),
                                            dataMapping));
                        }
                    }
                }
            }
        }
        if (removeMappingCommand.isEmpty()) {
            return null;
        }
        return removeMappingCommand;
    }

    @Override
    protected List<ScriptInformation> getActivityScriptInformationList(
            Activity activity) {
        return ProcessScriptUtil.getAllServiceScriptInformations(activity,
                getGrammarType());
    }

    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return ProcessScriptUtil.isJavaServiceTaskWithScriptType(activity,
                getGrammarType());
    }

    @Override
    protected String getTaskName() {
        return ProcessJsConsts.JAVASERVICE_TASK;
    }

    @Override
    protected String getGrammarType() {
        return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
    }

    @Override
    protected String getDefaultDestination() {
        return ProcessJsConsts.JSCRIPT_DESTINATION;
    }

    @Override
    protected String getTranslatedParameterRef(Activity activity,
            String parameterRef, Map<String, String> nameMap) {
        String translatedParamRef = null;
        if (nameMap != null && parameterRef != null) {
            boolean prefixParamWithProcess = false;

            // java service process data ref used to have "process." prefix
            String processPrefix = JavaConstants.PROCESS_PREFIX + "."; //$NON-NLS-1$
            if (parameterRef.startsWith(processPrefix)) {
                prefixParamWithProcess = true;
                parameterRef = parameterRef.substring(processPrefix.length());
            }

            String newName = nameMap.get(parameterRef);
            if (newName != null) {
                if (prefixParamWithProcess) {
                    translatedParamRef = processPrefix + newName;
                } else {
                    translatedParamRef = newName;
                }
            }

        }
        return translatedParamRef;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingInReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingInReferenceContextLabel(Activity activity) {
        return Messages.MappingFieldResolver_InputToService_label;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.AbstractMappingScriptProcessFieldResolver#getMappingOutReferenceContextLabel(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected String getMappingOutReferenceContextLabel(Activity activity) {
        return Messages.MappingFieldResolver_OutputFromService_label;
    }
}
