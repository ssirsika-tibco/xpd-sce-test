/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

/**
 * 
 * <p>
 * <i>Created: 12 Apr 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public abstract class ScriptProcessFieldResolver implements
        IFieldContextResolverExtension {

    /**
     * @param activityOrTransition
     * @return The data reference context for the sub-classes implementation
     *         (i.e. the thing that the sub-class is looking for data references
     *         in, in relation to the given input activity or transition)
     */
    protected abstract DataReferenceContext getDataReferenceContext(
            EObject activityOrTransition);

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {

        Set<ProcessRelevantData> dataReferences =
                getActivityDataReferences(activity, dataSet);

        if (dataReferences != null) {
            Set<ProcessDataReferenceAndContexts> dataRefAndContexts =
                    new HashSet<ProcessDataReferenceAndContexts>();

            for (ProcessRelevantData data : dataReferences) {
                dataRefAndContexts.add(new ProcessDataReferenceAndContexts(
                        data, getDataReferenceContext(activity)));
            }

            return dataRefAndContexts;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Transition,
     *      java.util.Set)
     * 
     * @param transition
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> dataReferences =
                getTransitionDataReferences(transition, dataSet);

        if (dataReferences != null) {
            Set<ProcessDataReferenceAndContexts> dataRefAndContexts =
                    new HashSet<ProcessDataReferenceAndContexts>();

            for (ProcessRelevantData data : dataReferences) {
                dataRefAndContexts.add(new ProcessDataReferenceAndContexts(
                        data, getDataReferenceContext(transition)));
            }

            return dataRefAndContexts;
        }
        return null;
    }

    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        boolean interestedActivity = isInterestingActivity(activity);
        if (!interestedActivity) {
            return null;
        }
        String activityScript = getActivityScript(activity);
        Process process = activity.getProcess();
        String translatedScript =
                getTranslatedScript(process,
                        activityScript,
                        nameMap,
                        ProcessJsConsts.SCRIPT_TASK);
        if (translatedScript == null || translatedScript.trim().length() < 1) {
            return null;
        }
        Command setActivityScriptCommand =
                getSetActivityScriptCommand(editingDomain,
                        translatedScript,
                        activity);

        return setActivityScriptCommand;
    }

    @Override
    public Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap) {
        boolean conditionalTransition = isInterestingTransition(transition);
        if (!conditionalTransition) {
            return null;
        }
        String transitionScript = getTransitionScript(transition);
        Process process = transition.getProcess();
        String translatedScript =
                getTranslatedScript(process,
                        transitionScript,
                        nameMap,
                        ProcessJsConsts.SEQUENCE_FLOW);
        if (translatedScript == null || translatedScript.trim().length() < 1) {
            return null;
        }
        Command setTranScriptCommand =
                getSetTransitionScriptCommand(editingDomain,
                        translatedScript,
                        transition);
        return setTranScriptCommand;
    }

    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {
        boolean interestedActivity = isInterestingActivity(activity);
        if (!interestedActivity) {
            return null;
        }
        Process process = activity.getProcess();
        String activityScript = getActivityScript(activity);
        if (activityScript != null && activityScript.length() > 0) {
            /*
             * Do a very simple quick and dirty check to see if any field we are
             * looking for appears in the script. This should save us having to
             * actually parse scripts that don't have any mention of the given
             * field(s)
             */
            boolean dataNamesAppearInScript = false;
            for (ProcessRelevantData data : dataSet) {
                if (ProcessUtil.isDataNameInScript(data.getName(),
                        activityScript)) {
                    dataNamesAppearInScript = true;
                    break;
                }
            }

            if (dataNamesAppearInScript) {

                Map<String, IScriptRelevantData> dataMap =
                        getProcessDataMap(dataSet, process);
                List<String> variablesInUse =
                        getVariablesInUse(process,
                                activityScript,
                                dataMap,
                                ProcessJsConsts.SCRIPT_TASK);
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
    public Set<ProcessRelevantData> getTransitionDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        boolean conditionalTransition = isInterestingTransition(transition);
        if (!conditionalTransition) {
            return null;
        }
        Process process = transition.getProcess();
        String transitionScript = getTransitionScript(transition);

        if (transitionScript != null && transitionScript.length() > 0) {
            /*
             * Do a very simple quick and dirty check to see if any field we are
             * looking for appears in the script. This should save us having to
             * actually parse scripts that don't have any mention of the given
             * field(s)
             */
            boolean dataNamesAppearInScript = false;
            for (ProcessRelevantData data : dataSet) {
                if (ProcessUtil.isDataNameInScript(data.getName(),
                        transitionScript)) {
                    dataNamesAppearInScript = true;
                    break;
                }
            }

            if (dataNamesAppearInScript) {
                Map<String, IScriptRelevantData> dataMap =
                        getProcessDataMap(dataSet, process);
                List<String> variablesInUse =
                        getVariablesInUse(process,
                                transitionScript,
                                dataMap,
                                ProcessJsConsts.SEQUENCE_FLOW);
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

    protected String getTranslatedScript(Process process, String strScript,
            Map<String, String> nameMap, String scriptType) {

        String newScript =
                ScriptParserUtil.replaceDataRefByName(strScript, nameMap);

        if (newScript == null || newScript.equals(strScript)) {
            return null;
        }

        return newScript;
    }

    private List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(
                        DestinationUtil
                                .getEnabledValidationDestinations(process));

        if (processDestList == null) {
            processDestList = new ArrayList<String>();
            processDestList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        }
        return processDestList;
    }

    private List<IValidationStrategy> getValidationStrategyList(
            Process process, String scriptType) {
        List<String> processDestList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategy = Collections.EMPTY_LIST;
        try {
            validationStrategy =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getValidationStrategy(processDestList,
                                    scriptType,
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    ProcessJsConsts.JSCRIPT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return validationStrategy;
    }

    protected Map<String, IScriptRelevantData> getProcessDataMap(
            Set<ProcessRelevantData> dataSet, Process process) {
        Map<String, IScriptRelevantData> processData =
                new HashMap<String, IScriptRelevantData>();
        IProject project = WorkingCopyUtil.getProjectFor(process);
        for (ProcessRelevantData processRelevantData : dataSet) {
            if (processRelevantData != null) {
                IScriptRelevantData scriptRelevantData =
                        ProcessUtil
                                .convertToScriptRelevantData(processRelevantData,
                                        project);
                if (scriptRelevantData != null) {
                    processData.put(scriptRelevantData.getName(),
                            scriptRelevantData);
                }
            }
        }
        return processData;
    }

    protected Map<String, IScriptRelevantData> getParticipantProcessDataMap(
            ProcessRelevantData processRelevantData, Process process,
            Set<Participant> participantSet) {
        Map<String, IScriptRelevantData> processData =
                new HashMap<String, IScriptRelevantData>();
        IProject project = WorkingCopyUtil.getProjectFor(process);
        if (processRelevantData != null) {
            IScriptRelevantData scriptRelevantData =
                    ProcessUtil
                            .convertToScriptRelevantData(processRelevantData,
                                    project);
            if (scriptRelevantData != null) {
                processData.put(scriptRelevantData.getName(),
                        scriptRelevantData);
            }
        }
        return processData;
    }

    protected Map<String, IScriptRelevantData> getProcessDataMap(
            Set<ProcessRelevantData> dataSet, Package pckg) {
        Map<String, IScriptRelevantData> packageData =
                new HashMap<String, IScriptRelevantData>();
        IProject project = WorkingCopyUtil.getProjectFor(pckg);
        for (ProcessRelevantData processRelevantData : dataSet) {
            if (processRelevantData != null) {
                IScriptRelevantData scriptRelevantData =
                        ProcessUtil
                                .convertToScriptRelevantData(processRelevantData,
                                        project);
                if (scriptRelevantData != null) {
                    packageData.put(scriptRelevantData.getName(),
                            scriptRelevantData);
                }
            }
        }
        return packageData;
    }

    protected Set<ProcessRelevantData> createProcessDataInUse(
            List<String> variablesInUse, Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> dataSetInUse =
                new HashSet<ProcessRelevantData>();
        for (ProcessRelevantData processRelevantData : dataSet) {
            String dataName = processRelevantData.getName();
            boolean inUse = variablesInUse.contains(dataName);
            if (inUse) {
                dataSetInUse.add(processRelevantData);
            }
        }
        return dataSetInUse;
    }

    protected Set<Participant> createParticipantDataInUse(
            List<String> variablesInUse, ProcessRelevantData dataSet,
            Set<Participant> participantSet) {
        Set<Participant> dataSetInUse = new HashSet<Participant>();
        for (Participant participant : participantSet) {
            String dataName = participant.getName();
            boolean inUse = variablesInUse.contains(dataName);
            if (inUse) {
                dataSetInUse.add(participant);
            }
        }
        return dataSetInUse;
    }

    protected List<String> getVariablesInUse(Process process, String strScript,
            Map<String, IScriptRelevantData> dataMap, String scriptType) {

        /* Don't parse the script if it's empty! */
        if (strScript == null || strScript.length() == 0) {
            return null;
        }

        /*
         * SID SIA-106: Create symbol table here so we dispose a table that we
         * created.
         */
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.setScriptRelevantDataTypeMap(dataMap);

        JScriptParser parser =
                getScriptParser(process, strScript, symbolTable, scriptType);

        List<String> variablesInUse = symbolTable.getVariablesInUse();

        symbolTable.dispose();

        return variablesInUse;
    }

    private JScriptParser getScriptParser(Process process, String strScript,
            SymbolTable symbolTable, String scriptType) {
        List<String> destinationList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategyList =
                getValidationStrategyList(process, scriptType);
        IVarNameResolver varNameResolver = getVarNameResolver();
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();

        /*
         * SID SIA-106: Create the symbol table in calling method so that the
         * calling method so that it can be disposed at same level it was
         * created.
         */
        // ISymbolTable symbolTable = new SymbolTable();
        // symbolTable.setScriptRelevantDataTypeMap(dataMap);

        // Parsing the script and get the list of variable in use
        JScriptParser parser =
                ScriptParserUtil.validateScript(strScript,
                        destinationList,
                        validationStrategyList,
                        symbolTable,
                        varNameResolver,
                        validationErrorMap,
                        validationWarningMap,
                        scriptType);
        return parser;
    }

    private static final String SCRIPT_GRAMMAR = "JavaScript"; //$NON-NLS-1$

    protected String getJavaScriptGrammar() {
        return SCRIPT_GRAMMAR;
    }

    @Override
    public Command getSwapActivityDataIdReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> idMap) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Command getSwapTransitionDataIdReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> idMap) {
        // TODO Auto-generated method stub
        return null;
    }

    protected abstract IVarNameResolver getVarNameResolver();

    protected abstract boolean isInterestingTransition(Transition transition);

    protected abstract boolean isInterestingActivity(Activity activity);

    protected abstract Command getSetTransitionScriptCommand(
            EditingDomain editingDomain, String string, Transition transition);

    protected abstract Command getSetActivityScriptCommand(
            EditingDomain editingDomain, String strScript, Activity activity);

    protected abstract String getActivityScript(Activity activity);

    protected abstract String getTransitionScript(Transition transition);
}
