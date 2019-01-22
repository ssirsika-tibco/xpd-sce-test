/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.script;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.DefaultVarNameResolver;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension2;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class PerfomerScriptDataResolver extends ScriptProcessFieldResolver
        implements IFieldResolverExtension2 {

    /**
     * @see com.tibco.xpd.implementer.nativeservices.script.ScriptProcessFieldResolver#getDataReferenceContext(org.eclipse.emf.ecore.EObject)
     * 
     * @param activityOrTransition
     * @return
     */
    @Override
    protected DataReferenceContext getDataReferenceContext(
            EObject activityOrTransition) {
        return DataReferenceContext.CONTEXT_UNKNOWN;
    }

    @Override
    public Set<ProcessRelevantData> getProcessRelevantDataReferences(
            ProcessRelevantData processRelevantData,
            Set<ProcessRelevantData> dataSet) {

        /*
         * Really really should check that the field is a performer type before
         * parsing the script!
         */
        if (processRelevantData.getDataType() instanceof BasicType) {
            if (BasicTypeType.PERFORMER_LITERAL
                    .equals(((BasicType) processRelevantData.getDataType())
                            .getType())) {
                Process process = null;
                Package pckg = null;
                Map<String, IScriptRelevantData> dataMap =
                        new HashMap<String, IScriptRelevantData>();

                EObject container = processRelevantData.eContainer();
                if (container instanceof Process) {
                    process = (Process) container;
                }

                pckg = Xpdl2ModelUtil.getPackage(processRelevantData);

                String performerScript =
                        getPerformerScript(processRelevantData);

                dataMap = getProcessDataMap(dataSet, pckg);

                List<String> variablesInUse =
                        getVariablesInUse(process,
                                performerScript,
                                dataMap,
                                ProcessJsConsts.PERFORMER_SCRIPT);
                if (variablesInUse == null) {
                    return null;
                }
                Set<ProcessRelevantData> dataSetInUse =
                        createProcessDataInUse(variablesInUse, dataSet);
                return dataSetInUse;
            }
        }

        return null;
    }

    @Override
    public Command getSwapProcessRelevantDataNameReferencesCommand(
            EditingDomain editingDomain,
            ProcessRelevantData processRelevantData, Map<String, String> nameMap) {
        String performerScript = getPerformerScript(processRelevantData);

        String translatedScript =
                getTranslatedScript(null,
                        performerScript,
                        nameMap,
                        ProcessJsConsts.PERFORMER_SCRIPT);

        if (translatedScript == null || translatedScript.trim().length() < 1) {
            return null;
        }
        Command setPerformerScriptCommand =
                ProcessScriptUtil
                        .getSetScriptPerfomerScriptCommand(editingDomain,
                                translatedScript,
                                processRelevantData,
                                getJavaScriptGrammar());

        return setPerformerScriptCommand;
    }

    @Override
    public Command getSwapProcessRelevantDataIdReferencesCommand(
            EditingDomain editingDomain,
            ProcessRelevantData processRelevantData, Map<String, String> nameMap) {
        return UnexecutableCommand.INSTANCE;
    }

    protected String getPerformerScript(ProcessRelevantData processRelevantData) {
        String performerScript =
                ProcessScriptUtil.getScriptPerformerScript(processRelevantData);
        return performerScript;
    }

    @Override
    protected Command getSetTransitionScriptCommand(
            EditingDomain editingDomain, String string, Transition transition) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected String getTransitionScript(Transition transition) {
        return null;
    }

    @Override
    protected IVarNameResolver getVarNameResolver() {
        IVarNameResolver nameResolver = new DefaultVarNameResolver();
        return nameResolver;
    }

    @Override
    protected boolean isInterestingActivity(Activity activity) {
        return false;
    }

    @Override
    protected boolean isInterestingTransition(Transition transition) {
        return false;
    }

    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected String getActivityScript(Activity activity) {
        return ""; //$NON-NLS-1$
    }

    @Override
    protected Command getSetActivityScriptCommand(EditingDomain editingDomain,
            String strScript, Activity activity) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Set<Participant> getParticipantDataReferences(
            ProcessRelevantData processRelevantData,
            Set<Participant> participantSet) {
        Process process = null;
        Map<String, IScriptRelevantData> dataMap =
                new HashMap<String, IScriptRelevantData>();

        EObject container = processRelevantData.eContainer();
        if (container instanceof Process) {
            process = (Process) container;
        }
        String performerScript = getPerformerScript(processRelevantData);
        if (null != process) {
            dataMap =
                    getParticipantProcessDataMap(processRelevantData,
                            process,
                            participantSet);
        }
        List<String> variablesInUse =
                getVariablesInUse(process,
                        performerScript,
                        dataMap,
                        ProcessJsConsts.PERFORMER_SCRIPT);
        if (variablesInUse == null) {
            return null;
        }
        Set<Participant> dataSetInUse =
                createParticipantDataInUse(variablesInUse,
                        processRelevantData,
                        participantSet);
        return dataSetInUse;
    }

}
