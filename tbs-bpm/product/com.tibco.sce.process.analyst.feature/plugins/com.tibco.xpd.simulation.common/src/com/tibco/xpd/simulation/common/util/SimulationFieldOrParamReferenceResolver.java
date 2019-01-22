package com.tibco.xpd.simulation.common.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

public class SimulationFieldOrParamReferenceResolver implements
        IFieldContextResolverExtension {

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
                        data, DataReferenceContext.CONTEXT_SIMULATION_DATA));
            }

            return dataRefAndContexts;
        }
        return null;
    }

    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();

        SplitSimulationDataType splitSimulationDataType =
                SimulationXpdlUtils.getSplitSimulationData(activity);
        if (null != splitSimulationDataType
                && null != splitSimulationDataType.getSplitParameter()) {
            String parameterId =
                    splitSimulationDataType.getSplitParameter()
                            .getParameterId();
            for (ProcessRelevantData processRelevantData : dataSet) {
                if (null != parameterId
                        && null != processRelevantData.getName()) {
                    if (processRelevantData.getName().equals(parameterId)) {
                        referencedData.add(processRelevantData);
                    }
                }
            }
        }

        return referencedData;
    }

    /*
     * Delete TransitionSimulationData and Rename Simulation parameter are
     * handled in DeleteFormalParameterContribution and
     * RenameFormalParameterContribution classes respectively
     */
    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        return null;
    }

    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        return null;
    }

    @Override
    public Command getSwapActivityDataIdReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> idMap) {
        return null;
    }

    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        return null;
    }

    @Override
    public Command getSwapTransitionDataIdReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> idMap) {
        return null;
    }

    @Override
    public Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap) {
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
                        data, DataReferenceContext.CONTEXT_SIMULATION_DATA));
            }

            return dataRefAndContexts;
        }
        return null;
    }

    @Override
    public Set<ProcessRelevantData> getTransitionDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();
        for (ProcessRelevantData processRelevantData : dataSet) {
            if (null != transition && null != transition.getCondition()) {
                if (null != transition.getExtendedAttributes()) {
                    // preparing the TransitionSimulationData extended
                    // attribute's
                    // parameter id for deletion
                    ExtendedAttribute extendedAttribute =
                            TaskObjectUtil
                                    .getExtendedAttributeByName(transition,
                                            "TransitionSimulationData");//$NON-NLS-1$;
                    if (null != extendedAttribute) {

                        TransitionSimulationDataType transitionSimulationData =
                                SimulationXpdlUtils
                                        .getTransitionSimulationData(transition);
                        if (null != transitionSimulationData) {
                            if (transitionSimulationData
                                    .isParameterDeterminedCondition()) {
                                StructuredConditionType structuredConditionType =
                                        transitionSimulationData
                                                .getStructuredCondition();

                                if (null != structuredConditionType
                                        .getParameterId()
                                        && processRelevantData
                                                .getName()
                                                .equals(structuredConditionType.getParameterId())) {
                                    referencedData.add(processRelevantData);
                                }
                            }
                        }
                    }
                }
            }
        }
        return referencedData;
    }
}
