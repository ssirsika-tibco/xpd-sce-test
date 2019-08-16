/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.MultipleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

/**
 * 
 * <p>
 * <i>Created: 04 March 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractMappingScriptProcessFieldResolver implements
        IFieldContextResolverExtension {

    private static final String MULTIPLE_MAPPINGS_SEPARATOR = " + "; //$NON-NLS-1$

    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        boolean interestedActivity = isInterestingActivity(activity);
        if (!interestedActivity) {
            return null;
        }
        Process process = activity.getProcess();
        // Make changes to scripts
        List<ScriptInformation> scriptInformations =
                getActivityScriptInformationList(activity);
        CompoundCommand setActivityScriptCommand =
                new CompoundCommand(
                        Messages.AbstractMappingScriptProcessFieldResolver_ChangeNameReferences);
        if (scriptInformations != null) {
            for (ScriptInformation scriptInformation : scriptInformations) {
                String script = getScript(scriptInformation);
                String translatedScript =
                        getTranslatedScript(process,
                                script,
                                nameMap,
                                getTaskName());
                if (translatedScript == null
                        || translatedScript.trim().length() < 1) {
                    continue;
                }
                setActivityScriptCommand
                        .append(getSetScriptInformationScriptCommand(editingDomain,
                                translatedScript,
                                scriptInformation));
            }
        }

        /*
         * Sid XPD-7078: This old default handling messed up ScriptDataMapper
         * sub-class which handles src/tgt of mapping itself.
         */
        if (shouldHandleDataMappingSrcTgt(activity)) {
            // Make changes to mappings
            List<DataMapping> dataMappingList =
                    getActivityDataMappingList(activity);
            if (dataMappingList != null) {
                for (DataMapping dataMapping : dataMappingList) {

                    // MR 35294
                    // DO NOT use the composite mapping compositor (i.e. where 2
                    // fields are linked to one param or visa versa for SCRIPT
                    // mappings - the compositor will do nasty things to the
                    // script
                    // if it finds the field name in the script (like poutting a
                    // "+"
                    // in between every word in the script)!!!
                    if (ProcessScriptUtil.isAScriptMapping(dataMapping)
                            && ignoreScriptComposite()) {
                        continue;
                    }

                    String parameterRef = getParameterRef(dataMapping);
                    if (parameterRef != null) {
                        String translatedRef =
                                getTranslatedParameterRef(activity,
                                        parameterRef,
                                        nameMap);

                        if (translatedRef != null && !translatedRef.equals("")) { //$NON-NLS-1$
                            Expression oldExpression = dataMapping.getActual();
                            Expression newExpression =
                                    Xpdl2Factory.eINSTANCE
                                            .createExpression(translatedRef);
                            newExpression.setScriptGrammar(oldExpression
                                    .getScriptGrammar());
                            setActivityScriptCommand.append(SetCommand
                                    .create(editingDomain,
                                            dataMapping,
                                            Xpdl2Package.eINSTANCE
                                                    .getDataMapping_Actual(),
                                            newExpression));

                        }
                    }
                }
            }
        }

        if (setActivityScriptCommand.isEmpty()) {
            return null;
        }
        return setActivityScriptCommand;
    }

    /**
     * @param activity
     * @return <code>true</code> if the sub-class wishes this base class to deal
     *         with renaming source/target of data mappings as well as script
     *         mappings.
     */
    protected boolean shouldHandleDataMappingSrcTgt(Activity activity) {
        return true;
    }

    @Override
    public Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap) {
        return null;
    }

    /**
     * 
     * @param activity
     * @param dataSet
     * @param optimize
     *            If <code>true</code> then this function will stop looking for
     *            references when all data in the data set has been found in ANY
     *            script regardless of context (so if a data field exists in the
     *            MAPPING_IN context it will not also have the mapping_OUT
     *            context set. If <code>false</code> then all output mappings
     *            are checked as well as input mappings even if a field is
     *            already found in the inptu mapping.
     * 
     * @return
     */
    private Collection<ProcessDataReferenceAndContexts> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet,
            boolean optimize) {
        boolean interestedActivity = isInterestingActivity(activity);
        if (!interestedActivity) {
            return null;
        }

        Map<ProcessRelevantData, ProcessDataReferenceAndContexts> dataSetInUse =
                new HashMap<ProcessRelevantData, ProcessDataReferenceAndContexts>();

        List<ScriptInformation> scriptInformations =
                getActivityScriptInformationList(activity);

        if (scriptInformations != null) {
            Process process = activity.getProcess();

            Map<String, IScriptRelevantData> dataMap =
                    getProcessDataMap(dataSet, process);

            if (scriptInformations != null) {
                for (ScriptInformation scriptInformation : scriptInformations) {
                    String script = getScript(scriptInformation);

                    /*
                     * Do a very simple quick and dirty check to see if any
                     * field we are looking for appears in the script. This
                     * should save us having to actually parse scripts that
                     * don't have any mention of the given field(s)
                     */
                    boolean dataNamesAppearInScript = false;
                    for (ProcessRelevantData data : dataSet) {
                        if (ProcessUtil.isDataNameInScript(data.getName(),
                                script)) {
                            dataNamesAppearInScript = true;
                            break;
                        }
                    }

                    /*
                     * Only bother doing a full parse if there are occurrences
                     * of the name in the script.
                     */
                    if (dataNamesAppearInScript) {
                        Collection<String> variablesInUse =
                                getVariablesInUse(process,
                                        script,
                                        dataMap,
                                        getTaskName());

                        if (variablesInUse != null && !variablesInUse.isEmpty()) {
                            /*
                             * Sid XPD-7078 allow sub-class to override the data
                             * reference context to use.
                             */
                            DataReferenceContext dataReferenceContext =
                                    getDataReferenceContext(activity,
                                            scriptInformation);

                            addProcessDataInUse(dataSetInUse,
                                    variablesInUse,
                                    dataSet,
                                    dataReferenceContext);
                        }
                    }

                    /*
                     * If the dataSetInUse is the same size as the set are
                     * looking for then we have found references to everything
                     * we've been asked to look for and so can quit now to save
                     * parsing more scripts.
                     */
                    if (optimize && dataSetInUse.size() == dataSet.size()) {
                        break;
                    }
                }
            }
        }

        /*
         * If the dataSetInUse is the same size as the set are looking for then
         * we have found references to everything we've been asked to look for
         * and so can quit now to save parsing more scripts.
         */
        if (!optimize || dataSetInUse.size() != dataSet.size()) {

            List<DataMapping> dataMappingList =
                    getActivityDataMappingList(activity);
            if (dataMappingList != null) {
                for (DataMapping dataMapping : dataMappingList) {
                    /*
                     * If the dataSetInUse is the same size as the set are
                     * looking for then we have found references to everything
                     * we've been asked to look for and so can quit now to save
                     * parsing more scripts.
                     */
                    if (optimize && dataSetInUse.size() == dataSet.size()) {
                        break;
                    }

                    /*
                     * Sid: We still have to check TARGET of script mappings so
                     * can't just ignore them here!!
                     * 
                     * Moved check for isAScriptMapping() to just ignore source
                     * of mappings.
                     */

                    boolean isOutMapping =
                            DirectionType.OUT_LITERAL.equals(dataMapping
                                    .getDirection());

                    String target = DataMappingUtil.getTarget(dataMapping);
                    String script = DataMappingUtil.getScript(dataMapping);
                    String grammar = DataMappingUtil.getGrammar(dataMapping);

                    if (target != null && script != null && grammar != null) {
                        ScriptMappingCompositorFactory factory =
                                ScriptMappingCompositorFactory
                                        .getCompositorFactory(grammar,
                                                dataMapping.getDirection(),
                                                getTaskName());
                        if (factory == null) {
                            factory =
                                    ScriptMappingCompositorFactory
                                            .getCompositorFactory(grammar,
                                                    dataMapping.getDirection());
                        }

                        if (factory != null) {

                            ScriptMappingCompositor compositor =
                                    factory.getCompositor(activity,
                                            target,
                                            script);
                            if (compositor instanceof MultipleMappingCompositor) {
                                MultipleMappingCompositor multiple =
                                        (MultipleMappingCompositor) compositor;
                                Collection<Object> paths =
                                        multiple.getTargetItems();
                                for (Object path : paths) {
                                    Collection<Object> allRefs =
                                            new ArrayList<Object>();

                                    // MR 35294
                                    // DO NOT use the composite mapping
                                    // compositor (i.e. where 2
                                    // fields are linked to one param or visa
                                    // versa for SCRIPT
                                    // mappings - the compositor will do nasty
                                    // things to the
                                    // script
                                    // if it finds the field name in the script
                                    // (like poutting a
                                    // "+"
                                    // in between every word in the script)!!!

                                    /*
                                     * So only include source items if it is not
                                     * a script mapping
                                     */
                                    boolean ignoreSourceOfScriptMappings =
                                            (ProcessScriptUtil
                                                    .isAScriptMapping(dataMapping) && ignoreScriptComposite());

                                    if (!ignoreSourceOfScriptMappings) {
                                        allRefs.addAll(getRootConceptPaths(activity,
                                                multiple.getSourceItems(path)));
                                    }

                                    allRefs.addAll(getRootConceptPaths(activity,
                                            multiple.getTargetItems()));

                                    for (Object object : allRefs) {
                                        if (object instanceof ConceptPath) {
                                            ConceptPath cp =
                                                    (ConceptPath) object;
                                            if (cp.getItem() instanceof ProcessRelevantData) {
                                                ProcessRelevantData prd =
                                                        (ProcessRelevantData) cp
                                                                .getItem();
                                                if (dataSet.contains(prd)) {
                                                    addDataContextReference(dataSetInUse,
                                                            prd,
                                                            (isOutMapping ? new DataReferenceContext.MappingOutReferenceContext(
                                                                    getMappingOutReferenceContextLabel(activity))
                                                                    : new DataReferenceContext.MappingInReferenceContext(
                                                                            getMappingInReferenceContextLabel(activity))));
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (compositor instanceof SingleMappingCompositor) {
                                SingleMappingCompositor single =
                                        (SingleMappingCompositor) compositor;

                                Collection<Object> allRefs =
                                        new ArrayList<Object>();

                                // MR 35294
                                // DO NOT use the composite mapping
                                // compositor (i.e. where 2
                                // fields are linked to one param or visa
                                // versa for SCRIPT
                                // mappings - the compositor will do nasty
                                // things to the
                                // script
                                // if it finds the field name in the script
                                // (like poutting a
                                // "+"
                                // in between every word in the script)!!!

                                /*
                                 * So only include source items if it is not a
                                 * script mapping
                                 */
                                boolean ignoreSourceOfScriptMappings =
                                        (ProcessScriptUtil
                                                .isAScriptMapping(dataMapping) && ignoreScriptComposite());

                                if (!ignoreSourceOfScriptMappings) {
                                    allRefs.addAll(getRootConceptPaths(activity,
                                            single.getSourceItems(DirectionType.IN_LITERAL
                                                    .equals(dataMapping
                                                            .getDirection()))));
                                }

                                allRefs.add(getRootConceptPath(activity,
                                        single.getTarget()));

                                for (Object object : allRefs) {
                                    if (object instanceof ConceptPath) {
                                        ConceptPath cp = (ConceptPath) object;
                                        if (cp.getItem() instanceof ProcessRelevantData) {
                                            ProcessRelevantData prd =
                                                    (ProcessRelevantData) cp
                                                            .getItem();
                                            if (dataSet.contains(prd)) {
                                                addDataContextReference(dataSetInUse,
                                                        prd,
                                                        (isOutMapping ? new DataReferenceContext.MappingOutReferenceContext(
                                                                getMappingOutReferenceContextLabel(activity))
                                                                : new DataReferenceContext.MappingInReferenceContext(
                                                                        getMappingInReferenceContextLabel(activity))));
                                            }
                                        }
                                    } else if (object instanceof String) {
                                        String name = (String) object;
                                        for (Object dataSetObjects : dataSet) {
                                            if (dataSetObjects instanceof ProcessRelevantData) {
                                                ProcessRelevantData prd =
                                                        (ProcessRelevantData) dataSetObjects;
                                                if (name.equals(prd.getName())) {
                                                    addDataContextReference(dataSetInUse,
                                                            prd,
                                                            (isOutMapping ? new DataReferenceContext.MappingOutReferenceContext(
                                                                    getMappingOutReferenceContextLabel(activity))
                                                                    : new DataReferenceContext.MappingInReferenceContext(
                                                                            getMappingInReferenceContextLabel(activity))));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return dataSetInUse.values();
    }

    /**
     * Sid XPD-7078 allow sub-class to override the data reference context to
     * use.
     * 
     * @param activity
     * @param scriptInformation
     * 
     * @return The {@link DataReferenceContext} to use for data referenced by
     *         the given mapping script.
     */
    protected DataReferenceContext getDataReferenceContext(Activity activity,
            ScriptInformation scriptInformation) {
        boolean isOutMapping =
                DirectionType.OUT_LITERAL.equals(scriptInformation
                        .getDirection());

        DataReferenceContext dataReferenceContext =
                isOutMapping ? new DataReferenceContext.MappingOutReferenceContext(
                        getMappingOutReferenceContextLabel(activity))
                        : new DataReferenceContext.MappingInReferenceContext(
                                getMappingInReferenceContextLabel(activity));

        return dataReferenceContext;
    }

    /**
     * @param activity
     * @return The label to use for context-specific data reference for mappings
     *         with Direction="IN"
     */
    protected abstract String getMappingInReferenceContextLabel(
            Activity activity);

    /**
     * @param activity
     * @return The label to use for context-specific data reference for mappings
     *         with Direction="IN"
     */
    protected abstract String getMappingOutReferenceContextLabel(
            Activity activity);

    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {

        Collection<ProcessDataReferenceAndContexts> dataReferences =
                getActivityDataReferences(activity, dataSet, true);

        if (dataReferences == null) {
            return null;
        }

        Set<ProcessRelevantData> dataInUse = new HashSet<ProcessRelevantData>();

        for (ProcessDataReferenceAndContexts dataRef : dataReferences) {
            dataInUse.add(dataRef.getReferencedData());
        }

        return dataInUse;
    }

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

        Collection<ProcessDataReferenceAndContexts> dataReferences =
                getActivityDataReferences(activity, dataSet, false);

        if (dataReferences == null) {
            return null;
        }

        return new HashSet<ProcessDataReferenceAndContexts>(dataReferences);
    }

    /**
     * @param activity
     * @param mappedItems
     * @return Same as {@link #getRootConceptPath(Activity, Object)} but for
     *         list.
     */
    private Collection<Object> getRootConceptPaths(Activity activity,
            Collection<Object> mappedItems) {
        List<Object> roots = new ArrayList<Object>();

        if (mappedItems != null) {
            for (Object mappedItem : mappedItems) {
                roots.add(getRootConceptPath(activity, mappedItem));
            }
        }
        return roots;
    }

    /**
     * @param mappedItem
     * 
     * @return If the mappedItem is a process data concept path or string path
     *         for one (or content of complex one) then return the concept path
     *         of the root data field / parameter for it. Otherwise (foir wsdl
     *         path etc), returns the original mappedItem.
     */
    private Object getRootConceptPath(Activity activity, Object mappedItem) {
        if (mappedItem instanceof ConceptPath) {
            ConceptPath targetRoot = ((ConceptPath) mappedItem).getRoot();

            if (targetRoot != null) {
                return targetRoot;
            }

        } else if (mappedItem instanceof String) {
            ConceptPath targetConceptPath =
                    ConceptUtil.resolveConceptPath(activity,
                            (String) mappedItem);
            if (targetConceptPath != null) {
                ConceptPath targetRoot = targetConceptPath.getRoot();

                if (targetRoot != null) {
                    return targetRoot;
                }
            }
        }
        return mappedItem;
    }

    protected List<DataMapping> getActivityDataMappingList(Activity activity) {
        List<DataMapping> activityDataMappings = new ArrayList<DataMapping>();

        activityDataMappings.addAll(ProcessScriptUtil
                .getActivityDataMappingList(activity, getGrammarType()));

        /*
         * Sid XPD-6750 noticed an exception when deleting correlation data
         * mappings. Was caused by this method returning correlation mappigns
         * twice. (getActivityDataMappingList90 returns all mappings including
         * correlation.
         */
        return activityDataMappings;
    }

    @Override
    public Set<ProcessRelevantData> getTransitionDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        return null;
    }

    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        boolean interestedActivity = isInterestingActivity(activity);
        if (!interestedActivity) {
            return null;
        }
        CompoundCommand removeMappingCommand =
                new CompoundCommand(
                        Messages.AbstractMappingScriptProcessFieldResolver_DeleteNameReferences);
        List<DataMapping> dataMappingList =
                getActivityDataMappingList(activity);
        if (dataMappingList != null) {

            for (DataMapping dataMapping : dataMappingList) {

                // MR 35294
                // DO NOT use the composite mapping compositor (i.e. where 2
                // fields are linked to one param or visa versa for SCRIPT
                // mappings - the compositor will do nasty things to the script
                // if it finds the field name in the script (like poutting a "+"
                // in between every word in the script)!!!
                if (ProcessScriptUtil.isAScriptMapping(dataMapping)
                        && ignoreScriptComposite()) {
                    continue;
                }

                // Datamapping list may contain inherited INTERFFACE method
                // defined data mappings so DON'T delete those!
                EObject eo = dataMapping;
                while (eo != null && eo != activity) {
                    eo = eo.eContainer();
                }

                if (eo != activity) {
                    continue;
                }

                String parameterRef = getParameterRef(dataMapping);
                if (parameterRef != null) {
                    if (hasMultipleMappings(parameterRef)) {
                        String removedRef =
                                getRemovedParameterRef(parameterRef,
                                        data.getName());
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
                        if (data.getName().equals(parameterRef)) {
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

    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(
                        DestinationUtil
                                .getEnabledValidationDestinations(process));

        if (processDestList == null) {
            processDestList = new ArrayList<String>();
            processDestList.add(getDefaultDestination());
        }
        return processDestList;
    }

    protected List<IValidationStrategy> getValidationStrategyList(
            Process process, String scriptType) {
        List<String> processDestList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategy = Collections.EMPTY_LIST;
        try {
            validationStrategy =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getValidationStrategy(processDestList,
                                    scriptType,
                                    getGrammarType(),
                                    getDefaultDestination());
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return validationStrategy;
    }

    private Map<String, IScriptRelevantData> getProcessDataMap(
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

    /**
     * Add data references in for the given set of variables in use (along with
     * the given reference context) - if the dataReference is already in map
     * then add teh given reference context to its list of contexts.
     * 
     * @param dataSetInUse
     * @param variablesInUse
     * @param dataSet
     * @param dataReferenceContext
     */
    private void addProcessDataInUse(
            Map<ProcessRelevantData, ProcessDataReferenceAndContexts> dataSetInUse,
            Collection<String> variablesInUse, Set<ProcessRelevantData> dataSet,
            DataReferenceContext dataReferenceContext) {

        for (ProcessRelevantData processRelevantData : dataSet) {
            String dataName = processRelevantData.getName();
            boolean inUse = variablesInUse.contains(dataName);

            if (inUse) {

                addDataContextReference(dataSetInUse,
                        processRelevantData,
                        dataReferenceContext);
            }
        }
    }

    /**
     * Add data references in for the given data (along with the given reference
     * context) - if the dataReference is already in map then add teh given
     * reference context to its list of contexts.
     * 
     * @param dataSetInUse
     * @param processRelevantData
     * @param dataReferenceContext
     */
    public void addDataContextReference(
            Map<ProcessRelevantData, ProcessDataReferenceAndContexts> dataSetInUse,
            ProcessRelevantData processRelevantData,
            DataReferenceContext dataReferenceContext) {

        ProcessDataReferenceAndContexts dataRef =
                dataSetInUse.get(processRelevantData);

        if (dataRef != null) {
            /*
             * If we have already added the data to the dataInUse set then add
             * the extra dataReferenceContext to it.
             */
            dataRef.addContext(dataReferenceContext);
        } else {
            /* Else Add a new entry. */
            dataSetInUse.put(processRelevantData,
                    new ProcessDataReferenceAndContexts(processRelevantData,
                            dataReferenceContext));
        }
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

    /**
     * 
     * @param scriptInformation
     * @return the Script present in the passed '{@link ScriptInformation}'
     */
    protected String getScript(ScriptInformation scriptInformation) {
        String script = null;
        if (scriptInformation != null) {
            if (scriptInformation.eContainer() instanceof Activity) {
                script = ProcessScriptUtil.getTaskScript(scriptInformation);
            } else if (scriptInformation.eContainer() instanceof DataMapping) {
                DataMapping dataMapping =
                        (DataMapping) scriptInformation.eContainer();
                script = ProcessScriptUtil.getDataMappingScript(dataMapping);
            }
        }
        return script;
    }

    protected String getTranslatedParameterRef(Activity activity,
            String parameterRef, Map<String, String> nameMap) {
        String translatedParameterRef = null;
        if (parameterRef != null && nameMap != null) {
            if (hasMultipleMappings(parameterRef)) {
                StringBuffer translatedBuffer = new StringBuffer();
                StringTokenizer stringTokenizer =
                        new StringTokenizer(parameterRef,
                                MULTIPLE_MAPPINGS_SEPARATOR);
                if (stringTokenizer != null) {
                    boolean isDirty = false;
                    while (stringTokenizer.hasMoreTokens()) {
                        String parameter = stringTokenizer.nextToken();
                        if (parameter != null) {
                            StringBuffer concatString = new StringBuffer();
                            StringTokenizer tokenizer =
                                    new StringTokenizer(parameter, "."); //$NON-NLS-1$
                            String token = tokenizer.nextToken();

                            if (token != null && nameMap.containsKey(token)) {
                                concatString.append(nameMap.get(token));
                                while (tokenizer.hasMoreTokens()) {
                                    concatString.append("." //$NON-NLS-1$
                                            + tokenizer.nextToken());
                                }
                            }
                            String tempTranslatedParam =
                                    concatString.toString();

                            if (tempTranslatedParam != null) {
                                translatedBuffer.append(tempTranslatedParam);
                                isDirty = true;
                            } else {
                                translatedBuffer.append(parameter);
                            }
                            if (stringTokenizer.hasMoreTokens()) {
                                translatedBuffer
                                        .append(MULTIPLE_MAPPINGS_SEPARATOR);
                            }
                        }
                    }
                    if (isDirty) {
                        translatedParameterRef = translatedBuffer.toString();
                    }
                }
            } else {
                StringBuffer concatString = new StringBuffer();
                StringTokenizer tokenizer =
                        new StringTokenizer(parameterRef, "."); //$NON-NLS-1$
                String token = tokenizer.nextToken();
                if (token != null && nameMap.containsKey(token)) {
                    concatString.append(nameMap.get(token));
                    while (tokenizer.hasMoreTokens()) {
                        concatString.append("." + tokenizer.nextToken()); //$NON-NLS-1$
                    }
                }
                translatedParameterRef = concatString.toString();
            }
        }
        return translatedParameterRef;
    }

    protected String getParameterRef(DataMapping dataMapping) {
        String parameterRef = null;
        if (DirectionType.OUT_LITERAL.equals(dataMapping.getDirection())) {
            parameterRef = DataMappingUtil.getTarget(dataMapping);
        } else if (DirectionType.IN_LITERAL.equals(dataMapping.getDirection())) {
            parameterRef = DataMappingUtil.getScript(dataMapping);
        }
        return parameterRef;
    }

    protected String getRemovedParameterRef(String parameterRef,
            String removedParameter) {
        String removedParameterRef = null;
        List<String> existingParams = new ArrayList<String>();
        StringBuffer translatedBuffer = new StringBuffer();
        StringTokenizer stringTokenizer =
                new StringTokenizer(parameterRef, MULTIPLE_MAPPINGS_SEPARATOR);
        if (stringTokenizer != null) {
            boolean isDirty = false;
            while (stringTokenizer.hasMoreTokens()) {
                String parameter = stringTokenizer.nextToken();
                if (parameter != null) {
                    if (parameter.equals(removedParameter)) {
                        isDirty = true;
                    } else {
                        existingParams.add(parameter);
                    }
                }
            }
            if (isDirty) {
                for (int i = 0; i < existingParams.size(); i++) {
                    String param = existingParams.get(i);
                    translatedBuffer.append(param);
                    if (i < (existingParams.size() - 1)) {
                        translatedBuffer.append(MULTIPLE_MAPPINGS_SEPARATOR);
                    }
                }
                removedParameterRef = translatedBuffer.toString();
            }
        }
        return removedParameterRef;
    }

    protected boolean hasMultipleMappings(String parameterRef) {
        if (parameterRef.contains(MULTIPLE_MAPPINGS_SEPARATOR)) {
            return true;
        }
        return false;
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
        return null;
    }

    protected abstract boolean isInterestingTransition(Transition transition);

    protected abstract boolean isInterestingActivity(Activity activity);

    protected abstract Command getSetTransitionScriptCommand(
            EditingDomain editingDomain, String string, Transition transition);

    protected abstract Command getSetScriptInformationScriptCommand(
            EditingDomain editingDomain, String strScript,
            ScriptInformation scriptInformation);

    protected abstract List<ScriptInformation> getActivityScriptInformationList(
            Activity activity);

    protected abstract String getTransitionScript(Transition transition);

    protected abstract String getTaskName();

    protected abstract String getGrammarType();

    protected abstract String getDefaultDestination();

    protected abstract String getTranslatedScript(Process process,
            String strScript, Map<String, String> nameMap, String scriptType);

    protected abstract Collection<String> getVariablesInUse(Process process,
            String strScript, Map<String, IScriptRelevantData> dataMap,
            String scriptType);

    protected abstract boolean ignoreScriptComposite();

}
