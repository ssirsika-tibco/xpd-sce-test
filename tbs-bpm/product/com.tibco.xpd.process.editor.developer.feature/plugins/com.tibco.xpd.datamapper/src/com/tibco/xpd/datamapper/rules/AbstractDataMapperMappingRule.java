/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.DataMapperMappingContentProvider;
import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.datamapper.api.VirtualLikeMapping;
import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.DataMapperContentContributionHelper;
import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.datamapper.resolutions.RemoveDataMapperConfigurationResolution;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.validation.bpmn.developer.baserules.AbstractDeveloperActivityMappingJavaScriptRule;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract data mapper rule class to provide common rules applicable to all
 * data mappers.
 * 
 * @author nwilson
 * @since 13 Apr 2015
 */
public abstract class AbstractDataMapperMappingRule extends
        AbstractDeveloperActivityMappingJavaScriptRule {

    /**
     * This is the key for problem marker additional info. Added to all data
     * mapper related issues.
     * 
     * This info states the script context wich the rule was raised for (e.g.
     * the initiate, complete, script etc etc). This can then be used to relate
     * problem markers to the specific data mapper script context (so that
     * markers on source / starget elements don't appear in ALL context's
     * sections.
     */
    public static final String MARKER_INFO_DATA_MAPPER_SCRIPT_CONTEXT =
            "com.tubco.xpd.datamapper.scriptContext"; //$NON-NLS-1$

    /**
     * Added to like mapping issues. In cases where we need to add the host
     * Activity as the target element for the problem marker we may need to
     * still be able to resolve the data mapping in order to run quick fixes
     * (which is the case when the problem is related to content not a specific
     * mapping and we want markers to appear on content not mapping)
     */
    public static final String MARKER_INFO_LIKE_MAPPING_URI =
            "com.tubco.xpd.datamapper.likeMappingURI"; //$NON-NLS-1$

    private ScriptDataMapper sdm;

    private ContributableDataMapperInfoProvider targetInfoProvider;

    private ContributableDataMapperInfoProvider sourceInfoProvider;

    private DataMapperMappingContentProvider mappingContentProvider;

    /**
     * Check for ScriptDataMapper element.
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSInputMappingRule#doActivityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param eo
     *            The EObject being validated (expected to be Activity).
     * @return true if the Activity contains a ScriptDataMapper section.
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {
        boolean isApplicable = false;
        if (eo instanceof Activity) {
            Activity activity = (Activity) eo;
            // Check if ScriptGrammer is DataMapper
            IScriptDataMapperProvider provider = getScriptDataMapperProvider();
            if (provider != null) {
                isApplicable = provider.usesDataMapperGrammer(activity);

                /**
                 * Sid XPD-7834. Regression caused by XPD-7834 (removed the
                 * assignment of field sdm, after getting provider.
                 */
                sdm = provider.getScriptDataMapper(activity);
            }
        }
        return isApplicable;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#objectValidateDone(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     */
    @Override
    protected void objectValidateDone(EObject objectToValidate) {
        /*
         * Sid XPD-7598: Finally validate against empty scripts / unmapped
         * scripts
         */
        if (sdm != null) {
            validateUnmappedScripts(sdm);
            validateEmptyScripts(sdm);
        }

        sdm = null;
        super.objectValidateDone(objectToValidate);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingTypeDescription(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.AbstractDataMapperMappingRule_MappingTypeDesc;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createMappingsContentProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected DataMapperMappingContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        /*
         * Put in a field for use in other methods (lifecycle of base rule
         * createsd this first.
         * 
         * NOTE that we do NOT re-use existing field if set BECAUSE the same
         * rule instacne will run for all validations
         */
        IScriptDataMapperProvider provider = getScriptDataMapperProvider();
        mappingContentProvider = new DataMapperMappingContentProvider(provider);

        return mappingContentProvider;
    }

    /**
     * @return The script context specific provider that lets us know where
     *         xpdExt:ScrtipDataMapper is.
     */
    protected abstract IScriptDataMapperProvider getScriptDataMapperProvider();

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected ContributableDataMapperInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        /*
         * Put in a field for use in other methods (lifecycle of base rule
         * createsd this first.
         * 
         * NOTE that we do NOT re-use existing field if set BECAUSE the same
         * rule instacne will run for all validations
         */
        targetInfoProvider =
                new ContributableDataMapperInfoProvider(
                        getScriptDataMapperProvider(), getDataMapperContext(),
                        true, true, isInputMapping());

        return targetInfoProvider;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSInputMappingRule#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected ContributableDataMapperInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        /*
         * Put in a field for use in other methods (lifecycle of base rule
         * createsd this first.
         * 
         * NOTE that we do NOT re-use existing field if set BECAUSE the same
         * rule instacne will run for all validations
         */
        sourceInfoProvider =
                new ContributableDataMapperInfoProvider(
                        getScriptDataMapperProvider(), getDataMapperContext(),
                        false, true, isInputMapping());
        return sourceInfoProvider;
    }

    /**
     * @return true if this is for an input mapping.
     */
    private boolean isInputMapping() {
        return MappingDirection.IN.equals(getMappingDirection());
    }

    /**
     * Get the data mapper context for the given mapping scenario. The data
     * mapper context is what the data mapper info provider (source / target
     * content provision) contributions for a given mapping scenario will hook
     * into.
     * <p>
     * A data mapper context if for a given mapping scenario, for example
     * 'Sub-Process Input' would be one scenario and 'Sub process output' would
     * be another.
     * 
     * @return The data mapper context for the sub-classes specific mapping
     *         scenario.
     */
    protected abstract String getDataMapperContext();

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcess2ProcessJSInputMappingRule#performAdditionalMappingsValidation(java.util.Collection)
     * 
     * @param mappings
     */
    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {

        /*
         * For array inflation mappings only this maps the root parent of the
         * target to the root parent of the source.
         */
        Map<Object, Set<Object>> targetRootToSourceRoots =
                new HashMap<Object, Set<Object>>();

        Map<Object, List<Mapping>> targetRootToMapping =
                new HashMap<Object, List<Mapping>>();

        for (Object mappingObject : mappings) {

            /*
             * Sid XPD-7676 Ignore the virtual like mappings (virtual mappings
             * for child content of like mapped objects)
             */
            if (mappingObject instanceof VirtualLikeMapping) {
                continue;
            }

            if (mappingObject instanceof Mapping) {
                Mapping mapping = (Mapping) mappingObject;

                Object source = mapping.getSource();
                Object target = mapping.getTarget();
                Object mappingModel = mapping.getMappingModel();

                /* Sid: Don't carry on if have a broken mapping. */
                if (mappingModel instanceof DataMapping && source != null
                        && target != null) {

                    /**
                     * Problem:
                     * "Mapping from a data field / parameter to itself is not supported. "
                     */
                    DataMapping dm = (DataMapping) mappingModel;

                    validateSameSourceAndTarget(mapping, dm);

                    /**
                     * Validate like mappings
                     */
                    validateLikeMappings(mappings, mapping, target, dm);

                    /**
                     * Array inflation from appropriate source levels (for
                     * nested array inflation etc)
                     * 
                     * Also fills in targetRootToSourceRoots
                     */
                    validateArrayInflation(mapping,
                            dm,
                            sourceInfoProvider,
                            targetInfoProvider,
                            targetRootToSourceRoots,
                            targetRootToMapping);

                }
            }
        }

        /**
         * Problem:
         * "Creating a target array (%1$s) from contents of multiple source root elements is not supported."
         */
        validateInflateArrayFromMultiSource(targetRootToSourceRoots,
                targetRootToMapping);

        /**
         * Problem:
         * "There are mapping configurations for target content that no longer exists (%1$s)."
         * 
         * Check for array inflation type configurations for which the target
         * object no longer exists.
         */
        validateMappingConfigForOldElements();

        /**
         * Problem: Cannot perform 'like' mapping as objects '%1$s' and '%2$s'
         * have different multiplicity.
         */
        validateLikeMappingMultiplicity();

    }

    /**
     * Raise problem marker if source and target element is the same.
     * <p>
     * Problem:
     * "Mapping from a data field / parameter to itself is not supported. "
     * 
     * @param mapping
     * @param dm
     */
    private void validateSameSourceAndTarget(Mapping mapping, DataMapping dm) {
        Object source = mapping.getSource();
        Object target = mapping.getTarget();

        if (source.equals(target)) {
            Map<String, String> additionalInfo =
                    getAdditionalInfo(targetInfoProvider,
                            sourceInfoProvider,
                            mapping);
            List<String> messages =
                    createMessageList(getSourcePathDescription(sourceInfoProvider,
                            mapping),
                            getTargetPathDescription(targetInfoProvider,
                                    mapping));

            addIssue("datamapper.arrayMapping.sameSourceAndTarget", //$NON-NLS-1$
                    dm,
                    messages,
                    additionalInfo);
        }
    }

    /**
     * Validate various array inflation criteria for the given mapping.
     * <p>
     * Problem:
     * "Creating a target array (%1$s) from contents of multiple source root elements is not supported."
     * 
     * @param mapping
     * @param dm
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param targetRootToSourceRoots
     *            This is populated (incrementally) by this method. For array
     *            inflation mappings only this maps the root parent of the
     *            target to the root parent of the source.
     * 
     * @param targetRootToMapping
     *            This is populated (incrementally) by this method. This is a
     *            target root element to 'list of mappings into child content of
     *            the element'
     */
    private void validateArrayInflation(Mapping mapping, DataMapping dm,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            Map<Object, Set<Object>> targetRootToSourceRoots,
            Map<Object, List<Mapping>> targetRootToMapping) {

        Object source = mapping.getSource();
        Object target = mapping.getTarget();

        int sourceLevel = getArrayLevel(sourceInfoProvider, source);
        int targetLevel = getArrayLevel(targetInfoProvider, target);

        boolean toTopLevelArray =
                targetLevel == 1 && targetInfoProvider.isMultiInstance(target);

        if (!toTopLevelArray || !(source instanceof ScriptInformation)) {

            if (!toTopLevelArray && source instanceof ScriptInformation
                    && targetLevel > 0) {
                /**
                 * Problem:
                 * "Mapping of user defined script (%1$s) into array content (%2$s) is not supported."
                 */
                Map<String, String> additionalInfo =
                        getAdditionalInfo(targetInfoProvider,
                                sourceInfoProvider,
                                mapping);
                List<String> messages =
                        createMessageList(getSourcePathDescription(sourceInfoProvider,
                                mapping),
                                getTargetPathDescription(targetInfoProvider,
                                        mapping));

                addIssue("datamapper.arrayMapping.scriptSource", //$NON-NLS-1$
                        dm,
                        messages,
                        additionalInfo);

            } else if (sourceLevel > 0 || targetLevel > 0) {
                /**
                 * Problem: "Nested target arrays (%1$s) must be mapped from the same array nesting level in the source
                 * element."
                 * 
                 * Sid ACE-2088 Should allow use-case of level 1 -> 0 when multi->single is supported and level 0 -> 1
                 * when single->multi is supported (for multi-instance task mappings)
                 */
                boolean isIllegalMixedArrayLevelMapping = false;

                if (sourceLevel != targetLevel) {
                    isIllegalMixedArrayLevelMapping = true; // by default we'll say it's not allowed, unless...
                    
                    if (sourceLevel == 1 && targetLevel == 0 && sourceInfoProvider.isMultiInstance(source)
                            && !targetInfoProvider.isMultiInstance(target)
                            && isMultiToSingleSupported(source, target)) {
                        /*
                         * It's a Multi->Single from a 1st level array to a 1st level single and multi-> single is
                         * supported by this scenario - so we're ok
                         */
                        isIllegalMixedArrayLevelMapping = false;

                    } else if (sourceLevel == 0 && targetLevel == 1 && !sourceInfoProvider.isMultiInstance(source)
                            && targetInfoProvider.isMultiInstance(target) && isSingleToMultiSupported(source, target)) {
                        /*
                         * It's a Single->Multi from a 1st level single to a 1st level array and multi->single is
                         * supported by this scenario - so we're ok
                         */
                        isIllegalMixedArrayLevelMapping = false;
                    }
                }

                if (isIllegalMixedArrayLevelMapping) {
                    Map<String, String> additionalInfo =
                            getAdditionalInfo(targetInfoProvider,
                                    sourceInfoProvider,
                                    mapping);
                    List<String> messages =
                            createMessageList(getTargetPathDescription(targetInfoProvider,
                                    mapping));
                    addIssue("datamapper.arrayMapping.levelMismatch", //$NON-NLS-1$
                            dm,
                            messages,
                            additionalInfo);
                }

                // Group array inflation mappings by target root
                // parent.
                Object sourceRoot = getRootParent(sourceInfoProvider, source);
                Object targetRoot = getRootParent(targetInfoProvider, target);

                /*
                 * Sid ACE-2088 Prevent
                 * "Creating a target array (%1$s) from contents of multiple source root elements is not supported."
                 * being raised for Multi->Single and Single->Multi (that rule only matters when we're inflating a
                 * target array from a source array and ensures that all mappings are from the same array)
                 */
                if (sourceRoot != null && targetRoot != null) {
                    Set<Object> sources = targetRootToSourceRoots.get(targetRoot);
                    if (sources == null) {
                        sources = new HashSet<Object>();
                        targetRootToSourceRoots.put(targetRoot, sources);
                    }
                    sources.add(sourceRoot);
                    List<Mapping> rootMappings = targetRootToMapping.get(targetRoot);
                    if (rootMappings == null) {
                        rootMappings = new ArrayList<Mapping>();
                        targetRootToMapping.put(targetRoot, rootMappings);
                    }
                    rootMappings.add(mapping);
                }
            }
        }
    }

    /**
     * Validate against inflating arrays from multiple source arrays.
     * <p>
     * Problem:
     * "Creating a target array (%1$s) from contents of multiple source root elements is not supported."
     * 
     * @param targetRootToSourceRoots
     *            This must have been previously populated by
     *            {@link #validateArrayInflation(Mapping, DataMapping, ContributableDataMapperInfoProvider, ContributableDataMapperInfoProvider, Map, Map)}
     *            . This is for array inflation mappings only this maps the root
     *            parent of the target to the root parent of the source.
     * 
     * @param targetRootToMapping
     *            This must have been previously populated by
     *            {@link #validateArrayInflation(Mapping, DataMapping, ContributableDataMapperInfoProvider, ContributableDataMapperInfoProvider, Map, Map)}
     *            . This is a target root element to 'list of mappings into
     *            child content of the element'
     */
    private void validateInflateArrayFromMultiSource(
            Map<Object, Set<Object>> targetRootToSourceRoots,
            Map<Object, List<Mapping>> targetRootToMapping) {
        for (Entry<Object, Set<Object>> entry : targetRootToSourceRoots
                .entrySet()) {
            Set<Object> sources = entry.getValue();
            if (sources != null && sources.size() > 1) {
                Object targetRoot = entry.getKey();

                List<String> messages =
                        createMessageList(targetInfoProvider
                                .getObjectPathDescription(targetRoot));

                List<Mapping> rootMappings =
                        targetRootToMapping.get(targetRoot);

                for (Mapping mapping : rootMappings) {
                    Object mappingModel = mapping.getMappingModel();
                    if (mappingModel instanceof DataMapping) {
                        DataMapping dm = (DataMapping) mappingModel;
                        Map<String, String> additionalInfo =
                                getAdditionalInfo(targetInfoProvider,
                                        sourceInfoProvider,
                                        mapping);

                        addIssue("datamapper.arrayMapping.parentMismatch", //$NON-NLS-1$
                                dm,
                                messages,
                                additionalInfo);
                    }
                }
            }
        }
    }

    /**
     * Check for array inflation type configurations for which the target object
     * no longer exists.
     * <p>
     * Problem:
     * "There are mapping configurations for target content that no longer exists (%1$s)."
     * 
     */
    private void validateMappingConfigForOldElements() {
        if (sdm != null) {
            EList<DataMapperArrayInflation> arrayInflationTypes =
                    sdm.getArrayInflationType();
            if (arrayInflationTypes != null && arrayInflationTypes.size() > 0) {

                for (DataMapperArrayInflation arrayInflationType : arrayInflationTypes) {
                    String path = arrayInflationType.getPath();

                    String contributorId =
                            arrayInflationType.getContributorId();

                    AbstractDataMapperContentContributor contributor =
                            DataMapperContentContributionHelper
                                    .getContributor(contributorId);

                    if (contributor != null) {
                        Activity activity =
                                Xpdl2ModelUtil
                                        .getParentActivity(arrayInflationType);

                        Object found =
                                contributor.getInfoProvider()
                                        .getObjectForPath(path, activity);

                        if (found == null) {
                            List<String> messages = createMessageList(path);
                            Map<String, String> additionalInfo =
                                    new HashMap<String, String>();
                            additionalInfo
                                    .put(RemoveDataMapperConfigurationResolution.CONFIG_ID,
                                            EcoreUtil
                                                    .getURI(arrayInflationType)
                                                    .toString());
                            addIssue("datamapper.configuration.invalidReference", //$NON-NLS-1$
                                    activity,
                                    messages,
                                    additionalInfo);
                        }
                    }
                }
            }
        }
    }

    /**
     * Find the root array element of the mapping.
     * 
     * @param infoProvider
     *            the info provider.
     * @param element
     *            The element to find the root array element of.
     * @return the root array element or null.
     */
    private Object getRootParent(
            ContributableDataMapperInfoProvider infoProvider, Object element) {
        ITreeContentProvider contentProvider =
                infoProvider.getContentProvider();
        Object root = null;
        Object current = element;
        while (current != null) {
            if (infoProvider.isMultiInstance(current)) {
                root = current;
            }
            current = contentProvider.getParent(current);
        }
        return root;
    }

    /**
     * @param sourceInfoProvider
     * @param source
     * @return
     */
    private int getArrayLevel(ContributableDataMapperInfoProvider infoProvider,
            Object source) {
        int level = 0;
        ITreeContentProvider contentProvider =
                infoProvider.getContentProvider();
        Object current = source;
        while (current != null) {
            if (infoProvider.isMultiInstance(current)) {
                level++;
            }
            current = contentProvider.getParent(current);
        }
        return level;
    }

    /**
     * Validate the like mapping specific problems.
     * 
     * @param mappings
     * @param mapping
     * @param target
     * @param dataMapping
     */
    private void validateLikeMappings(Collection<Object> mappings,
            Mapping mapping, Object target, DataMapping dataMapping) {

        if (mappingContentProvider.isLikeMappingSet(dataMapping)) {
            /**
             * Sid XPD-7601: missing validation (cannot like map to simple type)
             * and rearranged so that only most pertinent problem raised.
             */

            if (mapping.getTarget() != null
                    && targetInfoProvider.isSimpleTypeContent(mapping
                            .getTarget())) {
                /**
                 * "Cannot perform 'like' mapping on simple type object mappings"
                 */
                Map<String, String> additionalInfo =
                        getLikeMappingAdditionalInfo(mapping, dataMapping);

                List<String> messages = Collections.emptyList();
                addIssue("datamapper.likeMapping.notForSimple", //$NON-NLS-1$
                        dataMapping,
                        messages,
                        additionalInfo);

            } else if (mapping.getSource() instanceof ScriptInformation) {
                /**
                 * Problem: "Cannot perform 'like' mapping on script mappings."
                 */
                Map<String, String> additionalInfo =
                        getLikeMappingAdditionalInfo(mapping, dataMapping);

                List<String> messages = Collections.emptyList();
                addIssue("datamapper.likeMapping.scriptType", //$NON-NLS-1$
                        dataMapping,
                        messages,
                        additionalInfo);

            } else {
                /**
                 * Problem :
                 * "Cannot perform 'like' mapping on mappings between same-typed complex objects."
                 */
                String sourceType =
                        sourceInfoProvider.getObjectType(mapping.getSource());
                String targetType =
                        targetInfoProvider.getObjectType(mapping.getTarget());

                if (Objects.equals(sourceType, targetType)) {
                    Map<String, String> additionalInfo =
                            getLikeMappingAdditionalInfo(mapping, dataMapping);

                    List<String> messages = Collections.emptyList();
                    addIssue("datamapper.likeMapping.sameType", //$NON-NLS-1$
                            dataMapping,
                            messages,
                            additionalInfo);

                } else {
                    /**
                     * Problems: "The mapping ('%1$s' to '%2$s') does not have
                     * any unexcluded matching content to be used as a like
                     * mapping."
                     * 
                     * As the mapping content provider creates
                     * VirtualLikeMapping mappings with same Datamapping model
                     * as like mapping for the implied child content mappings of
                     * like mappings, all we need to do is ensure that there is
                     * at least one of these virtual for the like-mapping to
                     * tell whether there is any unexcluded matching targets
                     * mapped.
                     */
                    boolean hasLikeMappedChildren = false;

                    for (Object otherMapping : mappings) {
                        if (otherMapping instanceof VirtualLikeMapping) {
                            if (dataMapping
                                    .equals(((VirtualLikeMapping) otherMapping)
                                            .getMappingModel())) {
                                hasLikeMappedChildren = true;
                                break;
                            }
                        }
                    }

                    if (!hasLikeMappedChildren) {
                        Map<String, String> additionalInfo =
                                getLikeMappingAdditionalInfo(mapping,
                                        dataMapping);

                        List<String> messages =
                                createMessageList(getSourcePathDescription(sourceInfoProvider,
                                        mapping),
                                        getTargetPathDescription(targetInfoProvider,
                                                mapping));

                        addIssue("datamapper.likeMapping.noMatchingContent", //$NON-NLS-1$
                                dataMapping,
                                messages,
                                additionalInfo);
                    }

                    /**
                     * Note that the final like mapping related error
                     * "Cannot perform 'like' mapping as objects '%1$s' and '%2$s' are not type compatible."
                     * will actually be handled by coercing the standard
                     * "types are not compatible" to the above error when
                     * createIssue() is called for that virtual mapping. See the
                     * override of createIssue() below.
                     */
                }
            }
        }

    }

    /**
     * @param mapping
     * @param dataMapping
     * 
     * @return The standard marker additional info for like mappings.
     */
    private Map<String, String> getLikeMappingAdditionalInfo(Mapping mapping,
            DataMapping dataMapping) {
        Map<String, String> additionalInfo =
                getAdditionalInfo(targetInfoProvider,
                        sourceInfoProvider,
                        mapping);

        additionalInfo.put(MARKER_INFO_LIKE_MAPPING_URI, dataMapping
                .eResource().getURIFragment(dataMapping));

        return additionalInfo;
    }

    /**
     * Check for Problem: Cannot perform 'like' mapping as objects '%1$s' and
     * '%2$s' have different multiplicity.
     */
    private void validateLikeMappingMultiplicity() {
        /*
         * Mapping content provider returns VirtualLikeMappings for leafs nodes
         * only (not the complex children of the like mapped content).
         * 
         * However, we do need to ensure that the same named complex objects in
         * source and target have the same multiplicity.
         */
        Collection<VirtualLikeMapping> equivalentLikeMappingComplexChildren =
                mappingContentProvider
                        .getEquivalentLikeMappingComplexChildren();

        for (VirtualLikeMapping equivComplexChildMapping : equivalentLikeMappingComplexChildren) {
            if (equivComplexChildMapping.getMappingModel() instanceof DataMapping) {
                DataMapping dataMapping =
                        (DataMapping) equivComplexChildMapping
                                .getMappingModel();

                Object source = equivComplexChildMapping.getSource();
                Object target = equivComplexChildMapping.getTarget();

                if (sourceInfoProvider.isMultiInstance(source) != targetInfoProvider
                        .isMultiInstance(target)) {

                    Activity activity =
                            (Activity) Xpdl2ModelUtil.getAncestor(dataMapping,
                                    Activity.class);

                    if (activity != null) {
                        Map<String, String> additionalInfo =
                                new HashMap<String, String>();
                        additionalInfo
                                .put(MapperContentProvider.SOURCE_URI_ISSUEINFO,
                                        getSourcePath(sourceInfoProvider,
                                                equivComplexChildMapping));

                        additionalInfo
                                .put(MapperContentProvider.TARGET_URI_ISSUEINFO,
                                        getTargetPath(targetInfoProvider,
                                                equivComplexChildMapping));

                        additionalInfo.put(MARKER_INFO_LIKE_MAPPING_URI,
                                dataMapping.eResource()
                                        .getURIFragment(dataMapping));

                        List<String> messages =
                                createMessageList(getSourcePathDescription(sourceInfoProvider,
                                        equivComplexChildMapping),
                                        getTargetPathDescription(targetInfoProvider,
                                                equivComplexChildMapping));

                        addIssue("datamapper.likeMapping.notCompatibleMultiplicity", //$NON-NLS-1$
                                activity,
                                messages,
                                additionalInfo);
                    }
                }
            }
        }
    }

    /**
     * Overridden to ensure {@link #MARKER_INFO_DATA_MAPPER_SCRIPT_CONTEXT} is
     * always added to problems.
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#addIssue(java.lang.String,
     *      org.eclipse.emf.ecore.EObject, java.util.List, java.util.Map)
     * 
     * @param id
     * @param o
     * @param messages
     * @param additionalInfo
     */
    @Override
    protected void addIssue(String id, EObject o, List<String> messages,
            Map<String, String> additionalInfo) {

        /*
         * Add the data mapper script context to the additional info in order
         * that we can correctly distinguish between source/target content
         * errors and so on.
         */
        if (additionalInfo == null) {
            additionalInfo = new HashMap<>();
        }
        additionalInfo.put(MARKER_INFO_DATA_MAPPER_SCRIPT_CONTEXT,
                getDataMapperContext());

        super.addIssue(id, o, messages, additionalInfo);
    }

    /**
     * This method overridden to coerce the standard "Incompatible source/target
     * type problem to the like mapping related error... <li>
     * "Cannot perform 'like' mapping as objects '%1$s' and '%2$s' are not type compatible."
     * </li>
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createIssue(com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue,
     *      org.eclipse.emf.ecore.EObject, java.util.List, java.util.Map,
     *      java.lang.Object, java.lang.Object, java.lang.Object)
     * 
     * @param issue
     * @param issueTarget
     * @param messages
     * @param additionalInfo
     * @param source
     * @param target
     * @param mapping
     */
    @Override
    protected void createIssue(MappingIssue issue, EObject issueTarget,
            List<String> messages, Map<String, String> additionalInfo,
            Object source, Object target, Object mapping) {

        /**
         * This method overridden to coerce the standard "Incompatible
         * source/target type problem to the like mapping related error... <li>
         * "Cannot perform 'like' mapping as objects '%1$s' and '%2$s' are not type compatible."
         * </li>
         * */
        if (MappingIssue.INCOMPATIBLE_TYPES.equals(issue)
                && mapping instanceof VirtualLikeMapping) {

            addIssue("datamapper.likeMapping.notCompatible", //$NON-NLS-1$
                    issueTarget,
                    createMessageList(getSourcePathDescription(sourceInfoProvider,
                            mapping),
                            getTargetPathDescription(targetInfoProvider,
                                    mapping)),
                    additionalInfo);

        } else {
            super.createIssue(issue,
                    issueTarget,
                    messages,
                    additionalInfo,
                    source,
                    target,
                    mapping);
        }
    }

    /**
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param mapping
     * @return
     */
    private Map<String, String> getAdditionalInfo(
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            Mapping mapping) {
        Map<String, String> additionalInfo = new HashMap<String, String>();
        additionalInfo
                .put(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                        getSourcePath(sourceInfoProvider, mapping));
        additionalInfo
                .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                        getTargetPath(targetInfoProvider, mapping));
        return additionalInfo;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#isUntypedScriptListToArrayMappingAllowed(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    public boolean isUntypedScriptListToArrayMappingAllowed(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isConcatenationMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isConcatenationMappingSupported() {
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isConcatenationMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isConcatenationMappingSupportedForTarget(
            Object targetObject) {
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isScriptMappingSupported() {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingFromSourceLevelSupported(java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingToTargetLevelSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isSingleToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param singleInstanceSource
     * @param multiInstanceTarget
     * @return
     */
    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMultiToSingleSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        
        /**
         * Sid XPD-7950. XPD-7459 changed this to return true for ANY
         * multi-single mapping, with the intention (I believe) to allow a
         * top-level LHS array to same-type single-instance-child of array
         * complex type on RHS.
         *
         * Although that is a nice use case, when I investigated I found that
         * the script generation does not always work correctly in all such
         * scenarios, validation doesn't cope with all scenarios correctly
         * (nested mapping) and validation prevents with multiple LHS arrays
         * (which would allow inflation of mutli inst complex RHS from multiple
         * simple type LHS arrays).
         * 
         * So decided to withdraw this ability (at least for now, until it can
         * be done properly. So now we never allow multi-to-single.
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMultiToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToMultiSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#performAdditionalMappingValidation(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptGrammar(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getScriptGrammar(EObject objectToValidate) {
        return ScriptGrammarFactory.JAVASCRIPT;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getDefaultScriptDestination()
     * 
     * @return
     */
    @Override
    protected String getDefaultScriptDestination() {
        return JsConsts.JSCRIPT_DESTINATION;
    }

    /**
     * Validate against unmapped user defined mapping scripts.
     * 
     * @param scriptDataMapper
     */
    private void validateUnmappedScripts(ScriptDataMapper scriptDataMapper) {
        for (ScriptInformation scriptInformation : scriptDataMapper
                .getUnmappedScripts()) {
            /**
             * Problem:
             * "Unmapped user defined mapping scripts (%1$s) are not supported"
             */
            addIssue("dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$
                    scriptInformation,
                    Collections.singletonList(scriptInformation.getName()));
        }

    }

    /**
     * Validate against empty user defined scripts
     * 
     * @param scriptDataMapper
     */
    private void validateEmptyScripts(ScriptDataMapper scriptDataMapper) {
        /*
         * NOTE: we will only validate against empty script for MAPPED scripts
         * (we'll let the 'unmapped scripts are not supported' problem take
         * precedence).
         */
        for (DataMapping dataMapping : scriptDataMapper.getDataMappings()) {
            Object oe =
                    Xpdl2ModelUtil.getOtherElement(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            if (oe instanceof ScriptInformation) {
                ScriptInformation scriptInformation = (ScriptInformation) oe;

                Expression expression = scriptInformation.getExpression();

                /**
                 * Problem:
                 * "Empty user defined mapping scripts (%1$s) are not supported"
                 */
                if (expression == null
                        || expression.getText() == null
                        || ScriptParserUtil.isEmptyScript(expression.getText(),
                                expression.getScriptGrammar())) {
                    Map<String, String> additionalInfo =
                            new HashMap<String, String>();
                    additionalInfo
                            .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    dataMapping.getFormal());

                    addIssue("dataMapper.emptyUserDefinedScript", //$NON-NLS-1$
                            scriptInformation,
                            Collections.singletonList(scriptInformation
                                    .getName()),
                            additionalInfo);
                }
            }
        }

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isAbstractMapping(java.lang.Object)
     * 
     * @param mapping
     *            The mapping to check.
     * @return true if this is a 'Like' mapping.
     */
    @Override
    protected boolean isAbstractMapping(Object mapping) {
        if (mapping instanceof Mapping) {
            Mapping m = (Mapping) mapping;
            return DataMapperUtils.isLikeMapping(m);
        }
        return super.isAbstractMapping(mapping);
    }
}
