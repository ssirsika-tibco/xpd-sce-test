/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.refactor;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.process.datamapper.common.ActivityInterfaceDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.internal.Messages;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.LikeMappingExclusion;
import com.tibco.xpd.xpdExtension.LikeMappingExclusions;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.util.ResetExpressionContentCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Field reference resolver contribution for process data mapper mappings that
 * contain process data field references.
 * <p>
 * Because all ScriptDataMapper have a source and target contributor id, we can
 * generically process all process data related mapping source/target REGARDLESS
 * of the mapping scenario (Process dta mapper, REST svc etc) because we know
 * the id's of the process data related data mapper contributors.
 * <p>
 * Currently these are {@link ActivityInterfaceDataMapperContentContributor} and
 * {@link ProcessDataMapperContentContributor}
 * 
 * @author aallway
 * @since 11 Jun 2015
 */
public class ProcessDataMapperFieldRefResolver implements
        IFieldContextResolverExtension {

    public ProcessDataMapperFieldRefResolver() {
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

        Map<String, ProcessRelevantData> dataMap =
                new HashMap<String, ProcessRelevantData>();
        for (ProcessRelevantData data : dataSet) {
            dataMap.put(data.getName(), data);
        }

        Set<DataMapperFieldReference> mapperRefs =
                getDataMapperFieldReferences(activity, dataMap.keySet());

        HashSet<ProcessDataReferenceAndContexts> dataRefs =
                new HashSet<ProcessDataReferenceAndContexts>();

        for (DataMapperFieldReference mapperRef : mapperRefs) {
            dataRefs.add(new ProcessDataReferenceAndContexts(dataMap
                    .get(mapperRef.referencedDataName),
                    mapperRef.dataReferenceContext));
        }

        return dataRefs;
    }

    /**
     * @param activity
     * @param dataNames
     *            The set of names to check references of.
     * 
     * @return The data mapper field references to the given data from the given
     *         activity.
     */
    private Set<DataMapperFieldReference> getDataMapperFieldReferences(
            Activity activity, Collection<String> dataNames) {
        Set<DataMapperFieldReference> dataRefs =
                new HashSet<DataMapperFieldReference>();

        /*
         * Find any ScriptDataMapper elements, these are what we are interested
         * in
         */
        TreeIterator<EObject> eAllContents = activity.eAllContents();

        while (eAllContents.hasNext()) {
            EObject eo = eAllContents.next();

            if (eo instanceof ScriptDataMapper) {
                ScriptDataMapper scriptDataMapper = (ScriptDataMapper) eo;

                /*
                 * Check if source or target of mapping content is process data
                 * related .
                 */
                DataReferenceContext dataRefContext =
                        ProcessDataMapperDataReferenceUtil
                                .getDataReferenceContext(scriptDataMapper,
                                        false);

                if (dataRefContext != null) {
                    /*
                     * Add any references to given data field.
                     */
                    addDataRefs(activity,
                            scriptDataMapper,
                            dataRefContext,
                            dataNames,
                            dataRefs);
                }
            }
        }
        return dataRefs;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getActivityDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();

        Set<ProcessDataReferenceAndContexts> dataReferences =
                getDataReferences(activity, dataSet);

        for (ProcessDataReferenceAndContexts dataRef : dataReferences) {
            ProcessRelevantData data = dataRef.getReferencedData();
            if (data != null) {
                referencedData.add(data);
            }
        }
        return referencedData;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapActivityDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.util.Map)
     * 
     * @param editingDomain
     * @param activity
     * @param nameMap
     * @return
     */
    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        CompoundCommand cmd = new CompoundCommand();

        Set<DataMapperFieldReference> dataReferences =
                getDataMapperFieldReferences(activity, nameMap.keySet());

        for (DataMapperFieldReference fieldReference : dataReferences) {

            Command swapCmd =
                    fieldReference.getSwapDataReferenceCommand(editingDomain,
                            nameMap.get(fieldReference.referencedDataName));

            if (swapCmd != null) {
                cmd.append(swapCmd);
            }

        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Add any references to given data field.
     * <p>
     * 
     * @param activity
     * 
     * @param scriptDataMapper
     *            the data mapper element to process.
     * @param dataRefContext
     * @param dataNames
     *            The set of names to check references of.
     * @param dataRefs
     *            add new data references here.
     */
    private void addDataRefs(Activity activity,
            ScriptDataMapper scriptDataMapper,
            DataReferenceContext dataRefContext, Collection<String> dataNames,
            Set<DataMapperFieldReference> dataRefs) {

        addDataMappingDataRefs(scriptDataMapper,
                dataRefContext,
                dataNames,
                dataRefs);

        addArrayInflationDataRefs(scriptDataMapper,
                dataRefContext,
                dataNames,
                dataRefs);

    }

    /**
     * Get data references from the data mappings themselves.
     * 
     * @param scriptDataMapper
     * @param dataRefContext
     * @param dataNames
     * @param dataRefs
     */
    private void addDataMappingDataRefs(ScriptDataMapper scriptDataMapper,
            DataReferenceContext dataRefContext, Collection<String> dataNames,
            Set<DataMapperFieldReference> dataRefs) {
        for (DataMapping dataMapping : scriptDataMapper.getDataMappings()) {
            /*
             * Check for reference from LHS of mapping.
             */
            String srcContributorId =
                    (String) Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SourceContributorId());

            if (isProcessDataContributionId(srcContributorId)) {
                for (String dataName : dataNames) {
                    if (dataMapping.getActual() != null
                            && isReferenced(dataName, dataMapping.getActual()
                                    .getText())) {

                        DataMapperFieldReference dmRef =
                                new DataMapperFieldReference(dataName,
                                        dataRefContext, scriptDataMapper,
                                        dataMapping, true);
                        dataRefs.add(dmRef);
                    }
                }

            }

            /*
             * Check for reference from RHS of mapping.
             */
            String tgtContributorId =
                    (String) Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_TargetContributorId());

            if (isProcessDataContributionId(tgtContributorId)) {
                for (String dataName : dataNames) {
                    if (dataMapping.getFormal() != null
                            && isReferenced(dataName, dataMapping.getFormal())) {

                        DataMapperFieldReference dmRef =
                                new DataMapperFieldReference(dataName,
                                        dataRefContext, scriptDataMapper,
                                        dataMapping, false);
                        dataRefs.add(dmRef);
                    }
                }

                /*
                 * For process data target also handle references from Like
                 * Mapping Exclusions list.
                 */
                addLikeExclusionsDataRefs(scriptDataMapper,
                        dataRefContext,
                        dataMapping,
                        dataNames,
                        dataRefs);

            }

        }
    }

    /**
     * Get data references from Array inflation configurations.
     * 
     * @param scriptDataMapper
     * @param dataRefContext
     * @param dataNames
     * @param dataRefs
     */
    private void addLikeExclusionsDataRefs(ScriptDataMapper scriptDataMapper,
            DataReferenceContext dataRefContext, DataMapping dataMapping,
            Collection<String> dataNames, Set<DataMapperFieldReference> dataRefs) {

        LikeMappingExclusions likeMappingExclusions =
                (LikeMappingExclusions) Xpdl2ModelUtil
                        .getOtherElement(dataMapping,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_LikeMappingExclusions());

        if (likeMappingExclusions != null) {
            for (LikeMappingExclusion exclusion : likeMappingExclusions
                    .getExclusions()) {

                for (String dataName : dataNames) {
                    if (exclusion.getPath() != null
                            && isReferenced(dataName, exclusion.getPath())) {

                        DataReferenceContext arrayInflContext =
                                new DataReferenceContext(
                                        dataRefContext.getContextId(),
                                        dataRefContext.getLabel()
                                                + " (" //$NON-NLS-1$
                                                + Messages.ProcessDataMapperFieldRefResolver_LiekMappingExclusion_label
                                                + ")", //$NON-NLS-1$
                                        dataRefContext.getSortingKey());

                        DataMapperFieldReference dmRef =
                                new DataMapperFieldReference(dataName,
                                        arrayInflContext, scriptDataMapper,
                                        exclusion);
                        dataRefs.add(dmRef);
                    }
                }
            }
        }
    }

    /**
     * Get data references from Array inflation configurations.
     * 
     * @param scriptDataMapper
     * @param dataRefContext
     * @param dataNames
     * @param dataRefs
     */
    private void addArrayInflationDataRefs(ScriptDataMapper scriptDataMapper,
            DataReferenceContext dataRefContext, Collection<String> dataNames,
            Set<DataMapperFieldReference> dataRefs) {
        for (DataMapperArrayInflation arrayInflation : scriptDataMapper
                .getArrayInflationType()) {

            for (String dataName : dataNames) {

                if (arrayInflation.getPath() != null
                        && isProcessDataContributionId(arrayInflation
                                .getContributorId())) {

                    if (isReferenced(dataName, arrayInflation.getPath())) {

                        DataReferenceContext arrayInflContext =
                                new DataReferenceContext(
                                        dataRefContext.getContextId(),
                                        dataRefContext.getLabel()
                                                + " (" //$NON-NLS-1$
                                                + Messages.ProcessDataMapperFieldRefResolver_ArrayInflation_label
                                                + ")", //$NON-NLS-1$
                                        dataRefContext.getSortingKey());

                        DataMapperFieldReference dmRef =
                                new DataMapperFieldReference(dataName,
                                        arrayInflContext, scriptDataMapper,
                                        arrayInflation);
                        dataRefs.add(dmRef);
                    }
                }
            }
        }
    }

    /**
     * @param dataName
     * @param text
     * 
     * @return <code>true</code> if the given string contains a LHS/RHS mapping
     *         reference to the given data field.
     */
    private boolean isReferenced(String dataName, String text) {
        if (text != null) {

            if (text.equals(dataName) || text.startsWith(dataName + ".")) { //$NON-NLS-1$
                return true;
            }
        }

        return false;
    }

    /**
     * @param contributorId
     * 
     * @return <code>true</code> if the data mapper contributor idis known to
     *         represent process data in a data mapping
     */
    private boolean isProcessDataContributionId(String contributorId) {

        if (ActivityInterfaceDataMapperContentContributor.ACTIVITY_INTERFACE_CONTRIBUTOR_ID
                .equals(contributorId)
                || ProcessDataMapperContentContributor.PROCESS_DATA_CONTRIBUTOR_ID
                        .equals(contributorId)
                || SignalDataMapperConstants.LOCAL_SIGNAL_CATCH_SOURCE_CONTRIBUTOR_ID.equals(contributorId)
                || SignalDataMapperConstants.LOCAL_SIGNAL_CATCH_TARGET_CONTRIBUTOR_ID.equals(contributorId)
                || SignalDataMapperConstants.GLOBAL_SIGNAL_CATCH_TARGET_CONTRIBUTOR_ID.equals(contributorId)
                || SignalDataMapperConstants.GS_CATCH_CORRELATION_DATAMAPPER_CONTENT_CONTRIBUTOR_ID
                        .equals(contributorId)) {
            return true;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getTransitionDataReferences(com.tibco.xpd.xpdl2.Transition,
     *      java.util.Set)
     * 
     * @param transition
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessRelevantData> getTransitionDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapActivityDataIdReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.util.Map)
     * 
     * @param editingDomain
     * @param activity
     * @param idMap
     * @return
     */
    @Override
    public Command getSwapActivityDataIdReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> idMap) {
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapTransitionDataIdReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition, java.util.Map)
     * 
     * @param editingDomain
     * @param transition
     * @param idMap
     * @return
     */
    @Override
    public Command getSwapTransitionDataIdReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> idMap) {
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapTransitionDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition, java.util.Map)
     * 
     * @param editingDomain
     * @param transition
     * @param nameMap
     * @return
     */
    @Override
    public Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap) {
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getDeleteDataFromActivityCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param activity
     * @param data
     * @return
     */
    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getDeleteDataFromTransitionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param transition
     * @param data
     * @return
     */
    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
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
        return null;
    }

    /**
     * Data class for to allow us to create appropriate
     * {@link ProcessDataReferenceAndContexts} when looking for references and
     * that is also useful for renaming data references
     * <p>
     * This info can then be used for fixing data references on rename.
     * 
     * @author aallway
     * @since 13 Jun 2015
     */
    private static class DataMapperFieldReference {

        DataReferenceContext dataReferenceContext;

        ScriptDataMapper scriptDataMapper;

        ContextType contextType;

        Object contextObject;

        private enum ContextType {
            MAPPING_SOURCE, MAPPING_TARGET, ARRAY_INFLATION, LIKE_MAPPING_EXCLUSION
        }

        String referencedDataName;

        /**
         * Construct a reference from a mapping source / target
         * 
         * @param referencedDataName
         * @param scriptDataMapper
         * @param dataMapping
         * @param isMappingSource
         *            Data Mapper DataMappings always Formal attribute for
         *            target and xpdl2:Actual element for source - so this
         *            allows us to know what we are changing on rename
         * @param dataReferenceContext
         */
        public DataMapperFieldReference(String referencedDataName,
                DataReferenceContext dataReferenceContext,
                ScriptDataMapper scriptDataMapper, DataMapping dataMapping,
                boolean isMappingSource) {
            super();
            this.referencedDataName = referencedDataName;
            this.scriptDataMapper = scriptDataMapper;
            this.contextObject = dataMapping;
            this.contextType =
                    isMappingSource ? ContextType.MAPPING_SOURCE
                            : ContextType.MAPPING_TARGET;
            this.dataReferenceContext = dataReferenceContext;
        }

        /**
         * Construct a reference from Array Inflation configuration
         * 
         * @param referencedDataName
         * @param scriptDataMapper
         * @param arrayInflation
         * @param dataReferenceContext
         */
        public DataMapperFieldReference(String referencedDataName,
                DataReferenceContext dataReferenceContext,
                ScriptDataMapper scriptDataMapper,
                DataMapperArrayInflation arrayInflation) {
            super();
            this.referencedDataName = referencedDataName;
            this.scriptDataMapper = scriptDataMapper;
            this.contextObject = arrayInflation;
            this.contextType = ContextType.ARRAY_INFLATION;
            this.dataReferenceContext = dataReferenceContext;
        }

        /**
         * Construct a reference from a Like Mapping Exclusion entry
         * 
         * @param referencedDataName
         * @param scriptDataMapper
         * @param likeMappingExclusion
         * @param dataReferenceContext
         */
        public DataMapperFieldReference(String referencedDataName,
                DataReferenceContext dataReferenceContext,
                ScriptDataMapper scriptDataMapper,
                LikeMappingExclusion likeMappingExclusion) {
            super();
            this.referencedDataName = referencedDataName;
            this.scriptDataMapper = scriptDataMapper;
            this.contextObject = likeMappingExclusion;
            this.contextType = ContextType.LIKE_MAPPING_EXCLUSION;
            this.dataReferenceContext = dataReferenceContext;
        }

        /**
         * Get the command to swap this data referenced to the given new name.
         * 
         * @param editingDomain
         * @param newDataName
         * @return the command to swap this data referenced to the given new
         *         name or <code>null</code> if no change is required.
         */
        public Command getSwapDataReferenceCommand(EditingDomain editingDomain,
                String newDataName) {
            Command cmd = null;
            if (ContextType.MAPPING_SOURCE.equals(contextType)) {
                DataMapping dataMapping = (DataMapping) contextObject;

                Expression mappingSource = dataMapping.getActual();

                String newValue =
                        swapDataReference(mappingSource.getText(),
                                referencedDataName,
                                newDataName);

                if (newValue != null && !newValue.equals(mappingSource)) {
                    cmd =
                            new ResetExpressionContentCommand(
                                    (TransactionalEditingDomain) editingDomain,
                                    "", //$NON-NLS-1$
                                    dataMapping.getActual(), newValue);
                }

            } else if (ContextType.MAPPING_TARGET.equals(contextType)) {
                DataMapping dataMapping = (DataMapping) contextObject;

                String mappingTarget = dataMapping.getFormal();

                String newValue =
                        swapDataReference(mappingTarget,
                                referencedDataName,
                                newDataName);

                if (newValue != null && !newValue.equals(mappingTarget)) {
                    cmd =
                            SetCommand.create(editingDomain,
                                    dataMapping,
                                    Xpdl2Package.eINSTANCE
                                            .getDataMapping_Formal(),
                                    newValue);
                }

            } else if (ContextType.ARRAY_INFLATION.equals(contextType)) {
                DataMapperArrayInflation arrayInflation =
                        (DataMapperArrayInflation) contextObject;

                String mappingTarget = arrayInflation.getPath();

                String newValue =
                        swapDataReference(mappingTarget,
                                referencedDataName,
                                newDataName);

                if (newValue != null && !newValue.equals(mappingTarget)) {
                    cmd =
                            SetCommand
                                    .create(editingDomain,
                                            arrayInflation,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDataMapperArrayInflation_Path(),
                                            newValue);
                }

            } else if (ContextType.LIKE_MAPPING_EXCLUSION.equals(contextType)) {
                LikeMappingExclusion exclusion =
                        (LikeMappingExclusion) contextObject;

                String mappingTarget = exclusion.getPath();

                String newValue =
                        swapDataReference(mappingTarget,
                                referencedDataName,
                                newDataName);

                if (newValue != null && !newValue.equals(mappingTarget)) {
                    cmd =
                            SetCommand.create(editingDomain,
                                    exclusion,
                                    XpdExtensionPackage.eINSTANCE
                                            .getLikeMappingExclusion_Path(),
                                    newValue);
                }
            }

            return cmd;
        }

        /**
         * Switch the given data name for new one in the given mapping text.
         * 
         * @param mappingText
         * @param oldDataName
         * @param newDataName
         * 
         * @return The new mapping text.
         */
        private String swapDataReference(String mappingText,
                String oldDataName, String newDataName) {
            if (mappingText != null) {
                if (mappingText.equals(oldDataName)) {
                    return newDataName;

                } else if (mappingText.startsWith(oldDataName + ".")) { //$NON-NLS-1$
                    return mappingText
                            .replaceFirst(oldDataName + "\\.", newDataName + "."); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
            return null;
        }
    }

}
