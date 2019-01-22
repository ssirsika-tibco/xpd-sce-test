/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules.baserules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * A generic mapping validation rule whereby the majority of complication of
 * mapping validation is rolled up in this class leaving only specifics of
 * type-to-type validation and so on.
 * <p>
 * <b>This class replaces the deprecated base class {@link AbstractMappingRule}
 * which was specific to {@link EObject} being the container for mappings.</b>
 * As this is no longer necessarily the case, this class allows a more generic
 * Eobject nominal containment.
 * <p>
 * Further information can be found in the specification:
 * https://emea-swi-svn.emea.tibco.com/svn/ProductDocumentation/studio/
 * Components/Process%20Modeler/PM-ConsistentMappingValidaton.doc
 * </p>
 * <p>
 * In simple terms...
 * <li>The sub-class will be asked for a set of objects within a process that
 * applicable.</li>
 * <li>Then it will be asked to create source, target and mapping content
 * providers which wrap different types of mapping source/target content objects
 * in order to provide AbstractMappingRule with a consistent interface to that
 * data (createSourceInfoProvider(), createTargetInfoProvider(),
 * createMappingContentProvider().</li>
 * <li>The AbstractMappingRule will then use the simple issue confirmation
 * methods (isConcatenationSupported(),checkTypeCompatibility() and so on) and
 * other methods in the content info providers to raise the appropriate issues
 * when necessary.</li>
 * </p>
 * <p>
 * This class makes some assumptions regarding the way in which data mappings
 * are represented (i.e. the mapping content provider is assumed to return a
 * list of {@link Mapping} objects) and the Mapping model object contained
 * within is assumed to be a {@link DataMapping} object). However, this class
 * will throw exceptions should this not be the case and the necessary methods
 * can be overriden in order to remove the data type assumptions.
 * </p>
 * 
 * @author aallway
 * @since 8 Oct 2013
 */
public abstract class AbstractMappingRuleBase extends ProcessValidationRule {

    /**
     * Maps Mapping to ScriptInformationParsed to just parse a script once
     */
    private Map<Object, ScriptInformationParsed> mappingToScriptInfoMap = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(Process process) {
        preValidate(process); // Just for lifecycle if the sub-class needs it.

        Collection<? extends EObject> objectsToValidate =
                getObjectsToValidate(process);

        for (EObject objectToValidate : objectsToValidate) {
            checkEObject(objectToValidate);
        }

        postValidate(process); // Just for lifecycle if the sub-class needs it.

        return;
    }

    /**
     * Get the applicable objects to validate, each will be the context object
     * that is passed to methods such as
     * {@link #genCreateSourceInfoProvider(EObject)} and
     * {@link #genCreateMappingsContentProvider(EObject)} and so on.
     * <p>
     * You can choose either to filter the list of objects here OR to filter by
     * overriding {@link #objectIsApplicable(EObject)}
     * 
     * @param process
     * 
     * @return The set of applicable to validate
     */
    protected abstract Collection<? extends EObject> getObjectsToValidate(
            Process process);

    /**
     * Check if objectToValidate validate is required and if so do the
     * validation
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     */
    private void checkEObject(EObject objectToValidate) {
        if (objectIsApplicable(objectToValidate)) {
            try {
                validateObject(objectToValidate);
            } finally {
                objectValidateDone(objectToValidate);
            }
        }
        return;
    }

    /**
     * Optional lifecycle method, called just prior to validating the
     * objectsToValidate in a given container.
     * 
     * @param container
     */
    protected void preValidate(Process process) {
        return;
    }

    /**
     * Optional lifecycle method, called just after to validating the
     * objectsToValidate in a given container.
     * 
     * @param container
     */
    protected void postValidate(Process process) {
        return;
    }

    /**
     * Validate the given objectToValidate (that has been confirmed to be
     * interesting.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     */
    protected void validateObject(EObject objectToValidate) {
        /*
         * Get the source and target info providers and the mappings themselves.
         */
        long start = System.currentTimeMillis();

        MappingRuleContentInfoProviderBase sourceInfoProvider =
                createSourceInfoProvider(objectToValidate);
        sourceInfoProvider.setContextObject(objectToValidate);

        MappingRuleContentInfoProviderBase targetInfoProvider =
                createTargetInfoProvider(objectToValidate);
        targetInfoProvider.setContextObject(objectToValidate);

        List<Object> mappings = getMappings(objectToValidate);

        clearScriptInformationCache();

        /*
         * Check for concatenation mappings unsupported (completely or for
         * specific targets).
         * 
         * No point checking unless the caller has supplied an issue id.
         */
        checkConcatenationMappings(objectToValidate,
                mappings,
                sourceInfoProvider,
                targetInfoProvider);

        /*
         * Check for script mappings unsupported (completely or for specific
         * targets).
         * 
         * No point checking unless the caller has supplied an issue id.
         */
        checkScriptMappings(objectToValidate,
                mappings,
                sourceInfoProvider,
                targetInfoProvider);

        /*
         * Check the mappings themselves (types, source and target found and so
         * on.
         */
        checkMappingData(objectToValidate,
                mappings,
                sourceInfoProvider,
                targetInfoProvider);

        /*
         * Check for target data that are not mapped.
         */
        checkMappingsAreComplete(objectToValidate,
                sourceInfoProvider,
                targetInfoProvider,
                mappings);

        /*
         * Give a chance to the sub classes to perform additional mapping
         * validations.NOTE: XPD-7075: the call to
         * "performAdditionalMappingsValidation" is now removed from
         * "checkMappingData" method and added over here.
         */
        performAdditionalMappingsValidation(objectToValidate, mappings);

        // System.out
        // .println(String
        //                        .format("  %s: %f", objectToValidate.getName(), ((float) (System.currentTimeMillis() - start) / 1000))); //$NON-NLS-1$

        return;
    }

    /**
     * Return true if the given objectToValidate is of a type that should be
     * validated. If the sub-class has pre-filtered the objects to validate in
     * the return to {@link #getObjectsToValidate(Process)} then there is no
     * need to override this method unless you wish to use it as a
     * pre-validate-each-object lifecycle method as descirbed below.
     * <p>
     * This method is called prior to the validation of each individual
     * objectToValidate therefore allowing the sub-class to load an cache
     * (temporarily) any state data that it needs to store for any other
     * abstract method called during the validation of the objectToValidate. In
     * general though this class will ask for things like content providers only
     * once per objectToValidate.
     * <p>
     * After validation of the objectToValidate the
     * {@link #objectValidateDone(EObject)} method is called allowing any cached
     * info to be freed as required.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return true if the given objectToValidate is of a type that should be
     *         validated.
     */
    protected boolean objectIsApplicable(EObject objectToValidate) {
        return true;
    }

    /**
     * This method is guaranteed to be called just after each objectToValidate
     * is validate and allows the sub-class to clean up any state data stored by
     * {@link #objectIsApplicable(EObject)}.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     */
    protected void objectValidateDone(EObject objectToValidate) {
    }

    /**
     * Get the mapping type description that should be used as a leader for the
     * issue messages. Nominally this should be similar or the same as ther
     * title of the mapping tab for the objectToValidate service type and the
     * direction.
     * <p>
     * For instance returning "Input To Service" will generate problem text
     * messages such as
     * <li>Input To Service: mapping target data 'xxxx' is no longer available.</li>
     * <p>
     * This will aid the user in identifying the location of the problem in UI.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return Mapping type description.
     */
    protected abstract String getMappingTypeDescription(EObject objectToValidate);

    /**
     * Get the source info provider, this provides access to the source content
     * provider (which should be exactly the same as the UI mapping section for
     * the context being validated). It also abstract knowledge of the types of
     * object used for source content from {@link AbstractMappingRule}.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * 
     * @return The source info provider.
     */
    protected abstract MappingRuleContentInfoProviderBase createSourceInfoProvider(
            EObject objectToValidate);

    /**
     * Get the target info provider, this provides access to the target content
     * provider (which should be exactly the same as the UI mapping section for
     * the context being validated). It also abstracts knowledge of the types of
     * object used for target content from {@link AbstractMappingRule}.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * 
     * @return The target info provider.
     */
    protected abstract MappingRuleContentInfoProviderBase createTargetInfoProvider(
            EObject objectToValidate);

    /**
     * Get the mappings content provider. In order that the mapping validations
     * exactly agree with what the user sees when viewing the mappings in the
     * UI, the mapping content provider should be exactly the same class as that
     * used by the relevant mapping section.
     * <p>
     * Although this is an IStructuredContentProvider, it is nominally expected
     * (as it is with the MapperViewer) that the content is of type
     * {@link Mapping}. Thus allowing the same content provider to be used for
     * UI and validation.
     * <p>
     * If not, it will be necessary to overwrite the methods
     * {@link #getSourceObject(Object)} and {@link #getTargetObject(Object)} and
     * returned by the sub-class's mapping content provider.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return Mapping content provider
     */
    protected abstract IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate);

    /**
     * Override to provide a MappingDirection that is added to any mapping
     * problem marker issue's additional info in order that the
     * {@link AbstractMappingSection} can more accurately identify whether a
     * particular problem marker applies too a given source or target tree item.
     * <p>
     * This is optional but prior to this, whether a problem marker was shown on
     * source/target content was identified by the EObject-URI and objectPath
     * (i.e. conceptPath name etc) alone.
     * <p>
     * That means if Mapping-In section on a task has a target with exactly the
     * same "path" (i.e. parameter name) as a target in the Mapping-Out section
     * then both sections will show a problem marker even though it only belongs
     * on one of them.
     * 
     * @return MappingDirection
     */
    protected abstract MappingDirection getMappingDirection();

    /**
     * Are concatenation mappings ever supported for this objectToValidate.
     * 
     * @return True if concatenation is supported at all (if this method returns
     *         true then individual concatenation targets are checked with
     *         {@link #isConcatenationMappingSupportedForTarget(EObject, Object)}
     *         ).
     */
    protected abstract boolean isConcatenationMappingSupported();

    /**
     * This method is used only if {@link #isConcatenationMappingSupported()}
     * returns true (i.e. concat mapping is supported in some circumstances,
     * hence check that it is supported in this specific target's circumstance).
     * 
     * @param targetObject
     * @return True if concatenation is supported for the given target object.
     *         Note that it will be assumed that if concatenation is valid for a
     *         given target then it means that as long as all source params pass
     *         the 'is compatible type' then it will be valid to concatenate
     *         those source parameters and assign the result to the given
     *         target.
     */
    protected abstract boolean isConcatenationMappingSupportedForTarget(
            Object targetObject);

    /**
     * Are script mappings ever supported for this objectToValidate?
     * 
     * @return True if script mapping is supported at all (if this method
     *         returns true then individual script mapping targets are checked
     *         with {@link #isScriptMappingSupportedForTarget(EObject, Object)}
     *         ).
     */
    protected abstract boolean isScriptMappingSupported();

    /**
     * This method is used only if {@link #isScriptMappingSupported()} returns
     * true (i.e. script mapping is supported in some circumstances, hence check
     * that it is supported in this specific target's circumstance).
     * 
     * @param targetObject
     * @return True if script mapping is supported for the given target object.
     */
    protected abstract boolean isScriptMappingSupportedForTarget(
            Object targetObject);

    /**
     * This method is used only if {@link #isScriptMappingSupported()} returns
     * true need to check if the script return type is resolved in order to
     * validate it correctly.
     * 
     * @param mapping
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return True if script mapping is supported for the given target object.
     */
    protected boolean isScriptMappingTypeResolved(Object mapping,
            EObject objectToValidate) {
        if (mapping instanceof Mapping) {
            IScriptRelevantData returnType = getCachedScriptReturnType(mapping);
            return returnType != null
                    && returnType.getType() != null
                    && !returnType.getType()
                            .equals(JsConsts.UNDEFINED_DATA_TYPE);
        }
        return false;
    }

    /**
     * Return true if the given mapping is a script mapping.
     * <p>
     * Nominally this method assumes that the model object for the mapping is an
     * xpdl2 {@link DataMapping}. If not then it will be necessary to override
     * this method.
     * 
     * @param mapping
     * @return true if the given mapping is a script mapping.
     */
    protected boolean isScriptMapping(Object mapping) {
        EObject modelObjectForMapping = getModelObjectForMapping(mapping);
        if (!(modelObjectForMapping instanceof DataMapping)) {
            /*
             * The sub-class's mapping content provider should be returning
             * Mapping class objects with DataMapping as the model ibject for
             * the mapping OR should have overwritten this method.
             */
            throw new IllegalStateException(
                    "Expected mapping content model object to be of class" //$NON-NLS-1$
                            + DataMapping.class.getName());
        }

        if (DataMappingUtil.isScripted((DataMapping) modelObjectForMapping)) {
            return true;
        }

        return false;
    }

    /**
     * Check whether the tree level of the given source object (which has been
     * located from the sourceContentProvider's tree of data) is valida to be
     * mapped from.
     * <p>
     * The sourceObjectInTree will have been located by using
     * mappingObjectEqualsContentObject to compare mapping source object with
     * source content
     * 
     * @param sourceObjectInTree
     * 
     * @return true if mapping from the level of the given source object (from
     *         source content provider's content) is supported.
     */
    protected abstract boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree);

    /**
     * Check whether the tree level of the given target object (which has been
     * located from the sourceContentProvider's tree of data) is valid to be
     * mapped to.
     * <p>
     * The targetObjectInTree will have been located by using
     * mappingObjectEqualsContentObject to compare mapping source object with
     * source content.
     * 
     * @param targetObjectInTree
     * 
     * @return true if mapping to the level of the given source object (from
     *         target content provider's content) is supported.
     */
    protected abstract boolean isMappingToTargetLevelSupported(
            Object targetObjectInTree);

    /**
     * Get the source object from the mapping returned by the sub-class's
     * mapping content provider.
     * <p>
     * The source object should nominally be directly comparable with the
     * content returned by the {@link #getSourceInfoProvider(EObject)}. If this
     * is not the case then override
     * {@link #mappingObjectEqualsContentObject(Object, Object)}.
     * <p>
     * Nominally the sub-class provided mapping content provider is expected to
     * return {@link Mapping} objects, if not the sub-class will need to
     * override this method.
     * 
     * @param mapping
     * @return The source object.
     */
    protected Object getSourceObject(Object mapping) {
        if (!(mapping instanceof Mapping)) {
            /*
             * The sub-class's mapping content provider should be returning
             * Mapping class objects or should have overwritten this method.
             */
            throw new IllegalStateException(
                    "Expected mapping content object to be of class" //$NON-NLS-1$
                            + Mapping.class.getName());
        }

        return ((Mapping) mapping).getSource();
    }

    /**
     * Get the target object from the mapping returned by the sub-class's
     * mapping content provider.
     * <p>
     * The target object should nominally be directly comparable with the
     * content returned by the {@link #getTargetInfoProvider(EObject)}. If this
     * is not the case then override {@link #targetObjectEquals(Object, Object)}.
     * <p>
     * Nominally the sub-class provided mapping content provider is expected to
     * return {@link Mapping} objects, if not the sub-class will need to
     * override this method.
     * 
     * @param mapping
     * @return The source object.
     */
    protected Object getTargetObject(Object mapping) {
        if (!(mapping instanceof Mapping)) {
            /*
             * The sub-class's mapping content provider should be returning
             * Mapping class objects or should have overwritten this method.
             */
            throw new IllegalStateException(
                    "Expected mapping content object to be of class" //$NON-NLS-1$
                            + Mapping.class.getName());
        }
        return ((Mapping) mapping).getTarget();
    }

    /**
     * Get the model object for the mapping (this is used when a mapping needs
     * to be added as the target object for a problem marker).
     * <p>
     * Nominally the sub-class provided mapping content provider is expected to
     * return {@link Mapping} objects, if not the sub-class will need to
     * override this method.
     * 
     * @param mapping
     * 
     * @return The model object for the mapping (this is used when a mapping
     *         needs to be added as the target object for a problem marker).
     */
    protected EObject getModelObjectForMapping(Object mapping) {
        if (!(mapping instanceof Mapping)) {
            /*
             * The sub-class's mapping content provider should be returning
             * Mapping class objects or should have overwritten this method.
             */
            throw new IllegalStateException(
                    "Expected mapping content object to be of class" //$NON-NLS-1$
                            + Mapping.class.getName());
        }

        /* Get the mapping model as an EObject if possible. */
        Object mappingModel = ((Mapping) mapping).getMappingModel();
        if (!(mappingModel instanceof EObject)) {
            if (mappingModel instanceof IAdaptable) {
                mappingModel =
                        ((IAdaptable) mappingModel).getAdapter(EObject.class);
            }
        }

        if (!(mappingModel instanceof EObject)) {
            /*
             * The sub-class's mapping content provider should be returning
             * Mapping class objects constructed with mappingModel or should
             * have overwritten this method.
             */
            throw new IllegalStateException(
                    "Expected Mapping object to be constructed with EObject (or adaptable to EObject) mappingModel. " //$NON-NLS-1$
                            + "Change MappingContentProvider to construct Mapping object's with mapping model or override this method."); //$NON-NLS-1$
        }

        return (EObject) mappingModel;
    }

    /**
     * If concatenation mappings are done then check they are supported.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mappings
     * @param sourceInfoProvider
     * @param targetInfoProvider
     */
    private void checkConcatenationMappings(EObject objectToValidate,
            List<Object> mappings,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider) {

        boolean isConcatenationSupported = isConcatenationMappingSupported();

        /* Map of targetObject and first mapping it was seen in. */
        Map<Object, Object> targetAndFirstMapping =
                new HashMap<Object, Object>();

        /*
         * Set of mappings used incorrectly for concatenation (we create list
         * then complain so that we can complain once about first as well as
         * subsequent mappings to same target)
         */
        Set<Object> badConcatMappings = new HashSet<Object>();

        for (Object mapping : mappings) {
            /*
             * If any object is the target of 2 or more mappings this is a
             * concatenation mapping.
             */
            Object targetObject = getTargetObject(mapping);
            if (targetObject != null) {

                /*
                 * Have we already previously found first mapping for this
                 * target object.
                 */
                Object firstMappingForTarget =
                        targetAndFirstMapping.get(targetObject);
                if (firstMappingForTarget == null) {
                    targetAndFirstMapping.put(targetObject, mapping);

                } else {
                    /*
                     * Found second or nth mapping to same target so it's a
                     * concat.
                     */
                    if (!isConcatenationSupported
                            || !isConcatenationMappingSupportedForTarget(targetObject)) {
                        /*
                         * Keep track of both the bad concat mappings (i.e. the
                         * one that's already there and the duplicate one we've
                         * just found).
                         */
                        badConcatMappings.add(firstMappingForTarget);
                        badConcatMappings.add(mapping);
                    }
                }
            }
        }

        /*
         * Now we've gathered all the mappings used for invalid concats, report
         * the errors.
         */

        if (!isConcatenationSupported) {
            /*
             * If concatenation is not supported at all then all complaints are
             * "not supported".
             */
            for (Object badMapping : badConcatMappings) {
                createIssue(MappingIssue.CONCATENATION_UNSUPPORTED,
                        getModelObjectForMapping(badMapping),
                        createMessageList(getMappingTypeDescription(objectToValidate)),
                        createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                getTargetPath(targetInfoProvider, badMapping)),
                        badMapping);
            }

        } else {
            /*
             * Otherwise it must be a bad concat for a particular type of
             * target.
             */
            for (Object badMapping : badConcatMappings) {
                createIssue(MappingIssue.CONCATENATION_UNSUPPORTED_FOR_TYPE,
                        getModelObjectForMapping(badMapping),
                        createMessageList(getMappingTypeDescription(objectToValidate),
                                getTargetPathDescription(targetInfoProvider,
                                        badMapping)),
                        createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                getTargetPath(targetInfoProvider, badMapping)),
                        badMapping);
            }

        }

        return;
    }

    /**
     * If script mapping is done then check it is supported.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mappings
     * @param sourceInfoProvider
     * @param targetInfoProvider
     */
    private void checkScriptMappings(EObject objectToValidate,
            List<Object> mappings,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        /*
         * Is script mapping supported in any circumstance?
         */
        boolean scriptMappingSupported = isScriptMappingSupported();

        for (Object mapping : mappings) {
            if (isScriptMapping(mapping)) {
                if (!scriptMappingSupported) {
                    /* Script mapping never supported. */
                    createIssue(MappingIssue.SCRIPT_UNSUPPORTED,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(objectToValidate)),
                            createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    getTargetPath(targetInfoProvider, mapping)),
                            mapping);
                } else {
                    if (!isScriptMappingSupportedForTarget(getTargetObject(mapping))) {
                        /* Script mapping not supported for particular target. */
                        createIssue(MappingIssue.SCRIPT_UNSUPPORTED_FOR_TYPE,
                                getModelObjectForMapping(mapping),
                                createMessageList(getMappingTypeDescription(objectToValidate),
                                        getTargetPathDescription(targetInfoProvider,
                                                mapping)),
                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        getTargetPath(targetInfoProvider,
                                                mapping)),
                                mapping);
                    } else if (!isScriptMappingTypeResolved(mapping,
                            objectToValidate)) {
                        /* Script mapping not supported for particular target. */
                        createIssue(MappingIssue.SCRIPT_RETURN_TYPE_UNRESOLVED,
                                getModelObjectForMapping(mapping),
                                createMessageList(getMappingTypeDescription(objectToValidate),
                                        getTargetPathDescription(targetInfoProvider,
                                                mapping)),
                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        getTargetPath(targetInfoProvider,
                                                mapping)),
                                mapping);
                    }
                }
            }
        }

        return;
    }

    /**
     * Check the mapping data itself (that source and target are available, that
     * they have compatible types and that they are to / from target supported
     * levels in tree.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mappings
     * @param sourceInfoProvider
     * @param targetInfoProvider
     */
    private void checkMappingData(EObject objectToValidate,
            List<Object> mappings,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        for (Object mapping : mappings) {
            /*
             * Check that source object exists
             */
            Object sourceObject = getSourceObject(mapping);
            Object sourceObjectInTree =
                    checkAndGetSourceTreeItem(objectToValidate,
                            mapping,
                            sourceObject,
                            sourceInfoProvider,
                            targetInfoProvider);

            /*
             * Check that target object exists
             */
            Object targetObject = getTargetObject(mapping);
            Object targetObjectInTree =
                    checkAndGetTargetTreeItem(objectToValidate,
                            mapping,
                            targetObject,
                            sourceInfoProvider,
                            targetInfoProvider);

            /*
             * If we've found source and target then check other things (if not
             * then the user already has bigger problems to sort out :o)
             */
            if (sourceObjectInTree != null && targetObjectInTree != null) {
                /*
                 * mapping to level of Source object not supported.
                 */
                boolean sourceLevelSupported =
                        isMappingFromSourceLevelSupported(sourceObjectInTree);
                if (!sourceLevelSupported) {
                    createIssue(MappingIssue.UNSUPPORTED_SOURCE_LEVEL,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(objectToValidate),
                                    getSourcePathDescription(sourceInfoProvider,
                                            mapping)),
                            createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                    getSourcePath(sourceInfoProvider, mapping)),
                            sourceObjectInTree,
                            targetObjectInTree,
                            mapping);

                }

                /*
                 * mapping to level of target object not supported.
                 */
                boolean targetLevelsupported =
                        isMappingToTargetLevelSupported(targetObjectInTree);
                if (!targetLevelsupported) {
                    createIssue(MappingIssue.UNSUPPORTED_TARGET_LEVEL,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(objectToValidate),
                                    getTargetPathDescription(targetInfoProvider,
                                            mapping)),
                            createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    getTargetPath(targetInfoProvider, mapping)),
                            sourceObjectInTree,
                            targetObjectInTree,
                            mapping);

                }

                if (sourceLevelSupported && targetLevelsupported) {
                    /*
                     * Cannot map to read only target.
                     */
                    if (targetInfoProvider.isReadOnlyTarget(targetObjectInTree)) {
                        createIssue(MappingIssue.MAPPING_TO_READONLY,
                                getModelObjectForMapping(mapping),
                                createMessageList(getMappingTypeDescription(objectToValidate),
                                        getTargetPathDescription(targetInfoProvider,
                                                mapping)),
                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        getTargetPath(targetInfoProvider,
                                                mapping)),
                                sourceObjectInTree,
                                targetObjectInTree,
                                mapping);

                    }
                    /*
                     * Check that source and target types are compatible
                     */
                    else if (!checkTypeCompatibility(sourceObjectInTree,
                            targetObjectInTree,
                            mapping)) {

                        checkSingleToMultiInstance(objectToValidate,
                                mapping,
                                sourceObjectInTree,
                                targetObjectInTree,
                                sourceInfoProvider,
                                targetInfoProvider);

                        createIssue(MappingIssue.INCOMPATIBLE_TYPES,
                                getModelObjectForMapping(mapping),
                                createMessageList(getMappingTypeDescription(objectToValidate),
                                        getSourcePathDescription(sourceInfoProvider,
                                                mapping),
                                        getTargetPathDescription(targetInfoProvider,
                                                mapping)),
                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                        getSourcePath(sourceInfoProvider,
                                                mapping),
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        getTargetPath(targetInfoProvider,
                                                mapping)),
                                sourceObjectInTree,
                                targetObjectInTree,
                                mapping);

                    }
                    /*
                     * If source and target are compatible types, then check for
                     * single<->multi instance compatibility.
                     */
                    else if (checkSingleToMultiInstance(objectToValidate,
                            mapping,
                            sourceObjectInTree,
                            targetObjectInTree,
                            sourceInfoProvider,
                            targetInfoProvider)) {

                        /*
                         * Check for any additional sub-class specific issues.
                         */
                        performAdditionalMappingValidation(mapping,
                                sourceObjectInTree,
                                targetObjectInTree);
                    }
                }

            }
        }
        return;
    }

    /**
     * Check and raise issues related to single<->multi instance mappings.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * 
     * @return <code>false</code> if there was a problem else <code>true</code>
     */
    private boolean checkSingleToMultiInstance(EObject objectToValidate,
            Object mapping, Object sourceObjectInTree,
            Object targetObjectInTree,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        boolean ret = true;

        boolean sourceIsMulti = false;
        if (isScriptMapping(mapping)) {
            sourceIsMulti =
                    sourceInfoProvider
                            .isMultiInstance(getCachedScriptReturnType(mapping));
        } else {
            sourceIsMulti =
                    sourceInfoProvider.isMultiInstance(sourceObjectInTree);
        }
        boolean targetIsMulti =
                targetInfoProvider.isMultiInstance(targetObjectInTree);

        if (sourceIsMulti && !targetIsMulti) {
            if (!isMultiToSingleSupported(sourceObjectInTree,
                    targetObjectInTree)) {
                createIssue(MappingIssue.MULTI_TO_SINGLE_UNSUPPORTED,
                        getModelObjectForMapping(mapping),
                        createMessageList(getMappingTypeDescription(objectToValidate),
                                getSourcePathDescription(sourceInfoProvider,
                                        mapping),
                                getTargetPathDescription(targetInfoProvider,
                                        mapping)),
                        createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                getSourcePath(sourceInfoProvider, mapping),
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                getTargetPath(targetInfoProvider, mapping)),
                        sourceObjectInTree,
                        targetObjectInTree,
                        mapping);
                ret = false;
            }

        } else if (!sourceIsMulti && targetIsMulti) {
            /*
             * XPD-7090: Script Mapping Single to Multi should be checked and
             * error raised for incompatibility, removed the condition which
             * avoided raising error when the source is a Script, do not know
             * why it was there , seems has been here since, the script return
             * was not completely supported, which is not the case now.
             */
            if (!isSingleToMultiSupported(sourceObjectInTree,
                    targetObjectInTree)) {
                createIssue(MappingIssue.SINGLE_TO_MULTI_UNSUPPORTED,
                        getModelObjectForMapping(mapping),
                        createMessageList(getMappingTypeDescription(objectToValidate),
                                getSourcePathDescription(sourceInfoProvider,
                                        mapping),
                                getTargetPathDescription(targetInfoProvider,
                                        mapping)),
                        createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                getSourcePath(sourceInfoProvider, mapping),
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                getTargetPath(targetInfoProvider, mapping)),
                        sourceObjectInTree,
                        targetObjectInTree,
                        mapping);
                ret = false;
            }

        } else if (sourceIsMulti && targetIsMulti) {
            if (!isMultiToMultiSupported(sourceObjectInTree, targetObjectInTree)) {
                createIssue(MappingIssue.MULTI_TO_MULTI_UNSUPPORTED,
                        getModelObjectForMapping(mapping),
                        createMessageList(getMappingTypeDescription(objectToValidate),
                                getSourcePathDescription(sourceInfoProvider,
                                        mapping),
                                getTargetPathDescription(targetInfoProvider,
                                        mapping)),
                        createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                getSourcePath(sourceInfoProvider, mapping),
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                getTargetPath(targetInfoProvider, mapping)),
                        sourceObjectInTree,
                        targetObjectInTree,
                        mapping);
                ret = false;
            }
        }

        return ret;
    }

    /**
     * Search for the given sourceObject (as returned by
     * {@link #getSourceObject(Object mapping)}) if it cannot be found raise and
     * issue otherwise return it.
     * <p>
     * {@link #mappingObjectEqualsContentObject(Object, Object)} is used to
     * compare the sourceObject with the sourceInfoProvider's content.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mapping
     * @param sourceObject
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * 
     * @return object from source content provider that matches sourceObject or
     *         <code>null</code> if not found.
     */
    private Object checkAndGetSourceTreeItem(EObject objectToValidate,
            Object mapping, Object sourceObject,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        Object sourceInTree = null;

        if (sourceObject != null) {
            sourceInTree =
                    findSourceMappingObjectInTree(objectToValidate,
                            sourceObject,
                            sourceInfoProvider);
        }

        if (sourceInTree == null) {
            createIssue(MappingIssue.SOURCE_MISSING,
                    getModelObjectForMapping(mapping),
                    createMessageList(getMappingTypeDescription(objectToValidate),
                            getSourcePathDescription(sourceInfoProvider,
                                    mapping)),
                    createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                            getTargetPath(targetInfoProvider, mapping)),
                    sourceObject,
                    null,
                    null);
        }
        return sourceInTree;
    }

    /**
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param sourceObject
     * @param sourceInfoProvider
     * @return The source tree content object that matches the given source
     *         object from mapping.
     */
    private Object findSourceMappingObjectInTree(EObject objectToValidate,
            Object sourceObject,
            MappingRuleContentInfoProviderBase sourceInfoProvider) {
        Object sourceInTree;
        /*
         * We will find the object in the tree by looking for it's root ancestor
         * element in the top level of objects, then it's next element in the
         * next level down and so on.
         * 
         * First get a reverse list of ancestors (root element is last).
         */
        ArrayList<Object> reverseAncestry = new ArrayList<Object>();
        Object next = sourceObject;
        while (next != null) {
            reverseAncestry.add(next);
            next = sourceInfoProvider.getContentProvider().getParent(next);
        }

        sourceInTree =
                recursiveFindObjectInTree(sourceInfoProvider
                        .getContentProvider()
                        .getElements(getSourceContentProviderInput(objectToValidate)),
                        sourceInfoProvider,
                        reverseAncestry);
        return sourceInTree;
    }

    /**
     * Search for the given targetObject (as returned by
     * {@link #getTargetObject(Object mapping)}) if it cannot be found raise an
     * issue otherwise return it.
     * <p>
     * {@link #targetObjectEquals(Object, Object)} is used to compare the
     * targetObject with the targetProvider's content.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mapping
     * @param targetObject
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * 
     * @return object from target content provider that matches targetObject or
     *         <code>null</code> if not found.
     */
    private Object checkAndGetTargetTreeItem(EObject objectToValidate,
            Object mapping, Object targetObject,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        Object targetInTree = null;
        if (targetObject != null) {
            targetInTree =
                    findTargetMappingObjectInTree(objectToValidate,
                            targetObject,
                            targetInfoProvider);
        }

        if (targetInTree == null) {
            createIssue(MappingIssue.TARGET_MISSING,
                    getModelObjectForMapping(mapping),
                    createMessageList(getMappingTypeDescription(objectToValidate),
                            getTargetPathDescription(targetInfoProvider,
                                    mapping)),
                    createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                            getSourcePath(sourceInfoProvider, mapping)),
                    null,
                    targetObject,
                    null);
        }
        return targetInTree;
    }

    /**
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param targetObject
     * @param targetInfoProvider
     * @return The target tree content object that matches the given target
     *         object from mapping.
     */
    private Object findTargetMappingObjectInTree(EObject objectToValidate,
            Object targetObject,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        Object targetInTree;
        /*
         * We will find the object in the tree by looking for it's root ancestor
         * element in the top level of objects, then it's next element in the
         * next level down and so on.
         * 
         * First get a reverse list of ancestors (root element is last).
         */
        ArrayList<Object> reverseAncestry = new ArrayList<Object>();
        Object next = targetObject;
        while (next != null) {
            reverseAncestry.add(next);
            next = targetInfoProvider.getContentProvider().getParent(next);
        }

        targetInTree =
                recursiveFindObjectInTree(targetInfoProvider
                        .getContentProvider()
                        .getElements(getTargetContentProviderInput(objectToValidate)),
                        targetInfoProvider,
                        reverseAncestry);
        return targetInTree;
    }

    /**
     * Recursive method to find the given object (1st element in
     * sourceObjectAndAncestors) in the given content.
     * <p>
     * Each time we enter this method we will expect that the last element in
     * the list will appear in the list of elements passed (so the elements are
     * passed in from top down in the tree and we compare with the list back to
     * front (root->object).
     * 
     * @param sourceObject
     * @param elements
     *            elements at this level.
     * @param infoProvider
     *            info provider for this content tree
     * @param mappingObjectAndReverseAncestors
     *            a reverse list of ancestors (root element is last).
     * 
     * @return Object found in tree.
     */
    private Object recursiveFindObjectInTree(Object[] elements,
            MappingRuleContentInfoProviderBase infoProvider,
            ArrayList<Object> mappingObjectAndReverseAncestors) {
        Object objectInTree = null;

        /*
         * The last item in the list is the object that should appear at this
         * level of elements.
         */
        int size = mappingObjectAndReverseAncestors.size();
        if (size > 0 && elements != null) {
            Object nextObjectToFind =
                    mappingObjectAndReverseAncestors.get(size - 1);

            for (Object element : elements) {
                if (infoProvider
                        .mappingObjectEqualsContentObject(nextObjectToFind,
                                element)) {
                    /*
                     * Found the next element at this level, if this is the
                     * source element we were searching for then it will be the
                     * last thing in the list so we should get out.
                     */
                    if (size == 1) {
                        /* Found it! */
                        objectInTree = element;
                        break;
                    } else {
                        /*
                         * We've found the object at this level, so now we
                         * should remove it from the end of the list and recurs
                         * to find the next level object within this level's
                         * children.
                         */
                        mappingObjectAndReverseAncestors.remove(size - 1);

                        objectInTree =
                                recursiveFindObjectInTree(infoProvider
                                        .getContentProvider()
                                        .getChildren(element),
                                        infoProvider,
                                        mappingObjectAndReverseAncestors);

                        /*
                         * If we found element at this level then we need look
                         * nop futher than the result of recurs down thru other
                         * levels.
                         */
                        break;
                    }
                }
            }
        }

        return objectInTree;
    }

    /**
     * Check that the necessary mappings are present to 'complete' the mapping
     * to target.
     * <p>
     * Overall, the mappings can be 'incomplete' for several reasons - such as a
     * required top level element to mapped (or all of its required descendants
     * are not mapped).
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param mappings
     */
    private void checkMappingsAreComplete(EObject objectToValidate,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider,
            List<Object> mappings) {
        /*
         * Create a list of the already mapped target objects as they appear in
         * the target content provider's tree.
         */
        Set<Object> mappedTargetTreeObjects = new HashSet<Object>();
        for (Object mapping : mappings) {
            // XPD-7703: Exclude 'abstract' mappings, such as 'like' mappings,
            // from the list of mapped target objects.
            if (!isAbstractMapping(mapping)) {
                Object targetObject = getTargetObject(mapping);
                if (targetObject != null) {
                    Object targetObjectInTree =
                            findTargetMappingObjectInTree(objectToValidate,
                                    targetObject,
                                    targetInfoProvider);
                    if (targetObjectInTree != null) {
                        mappedTargetTreeObjects.add(targetObjectInTree);
                    }
                }
            }
        }

        /*
         * Build a map of target objects that have any descendant mapped.
         */
        Set<Object> targetsWithMappedDescendants =
                getTargetsWithMappedDescendants(mappedTargetTreeObjects,
                        targetInfoProvider);

        Object[] topLevelElements =
                targetInfoProvider
                        .getContentProvider()
                        .getElements(getTargetContentProviderInput(objectToValidate));

        recursCheckMappingsComplete(objectToValidate,
                mappedTargetTreeObjects,
                targetsWithMappedDescendants,
                topLevelElements,
                targetInfoProvider,
                null);

        /**
         * Sid XPD-7735 - see method header for details...
         */
        checkNestedTargetMappings(objectToValidate,
                mappings,
                sourceInfoProvider,
                targetInfoProvider,
                targetsWithMappedDescendants);

        return;
    }

    /**
     * Checks whether the mapping represents data that is actually mapped
     * (returning false) or if it is an abstract container that implies other
     * mappings (such as a 'like' mapping, returning true). Defaults to false.
     * 
     * @param mapping
     *            The mapping to check.
     * @return true if it is abstract.
     */
    protected boolean isAbstractMapping(Object mapping) {
        return false;
    }

    /**
     * Sid XPD-7735. Moved check for nested target mappings from
     * recursiveCheckMappingsComplete() because that method does not recurse
     * into children once it has found a mapping (else it would complain about
     * mandatory children not mapped etc.
     * <p>
     * So all in all, it is easier to check the rule
     * "mapping to both the target 'xxx' and its descendants is not permitted."
     * by simply looking thru all mapped targets check if they have descendant
     * mappings and then check if that's allowed.
     * <p>
     * This is to allow for the fact that a mapping may itself allow nested
     * mappings (because it is of a certain type such as a 'like' mapping in
     * datamapper) BUT if it has nested mappings they may be normal ones and
     * therefore not allow nested mappings themselves.So to handle this we
     * cannot stop looking at target child content under a mapped target.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mappings
     * @param targetInfoProvider
     * @param targetsWithMappedDescendants
     *            Targets that have descendants that are mapped.
     */
    private void checkNestedTargetMappings(EObject objectToValidate,
            List<Object> mappings,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider,
            Set<Object> targetsWithMappedDescendants) {
        for (Object mapping : mappings) {
            Object targetObject = getTargetObject(mapping);

            if (targetsWithMappedDescendants.contains(targetObject)) {
                /*
                 * This mapped target has mapped descendants, check if that is
                 * allowed.
                 */
                if (mapping instanceof Mapping
                        && !allowDescendantMappings((Mapping) mapping,
                                sourceInfoProvider,
                                targetInfoProvider)) {
                    createIssue(MappingIssue.INVALID_NESTED_MAPPING,
                            objectToValidate,
                            createMessageList(getMappingTypeDescription(objectToValidate),
                                    targetInfoProvider
                                            .getObjectPathDescription(targetObject)),
                            createAdditionalInfo(MapperContentProvider.TARGET_URI_ISSUEINFO,
                                    targetInfoProvider
                                            .getObjectPath(targetObject)),
                            null,
                            targetObject,
                            null);
                }
            }
        }
    }

    /**
     * 
     * Recursively check that mappings are complete.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mappedTargetTreeObjects
     *            The tree objects that are mapped (from target provider's tree
     *            data).
     * @param targetsWithMappedDescendants
     *            Targets that have descendants that are mapped.
     * @param targeObjects
     *            The target objects to check at this level of recursion.
     * @param targetInfoProvider
     * @param parentTargetObject
     *            Parent of the targetObjects or <code>null</code> for top level
     *            targets.
     */
    private void recursCheckMappingsComplete(EObject objectToValidate,
            Set<Object> mappedTargetTreeObjects,
            Set<Object> targetsWithMappedDescendants, Object[] targetObjects,
            MappingRuleContentInfoProviderBase targetInfoProvider,
            Object parentTargetObject) {

        if (targetObjects != null) {
            /*
             * Get some info from parent.
             */
            /* true if not all required objects need to be mapped in target. */
            boolean partialCompletionPermitted = false;
            /* true if each target is an individual element in an array */
            boolean isInstanceElement = false;
            /*
             * true if each targeObject is mutually exclusive (i.e. member of a
             * choice).
             */
            boolean isChoice = false;

            if (parentTargetObject != null) {
                partialCompletionPermitted =
                        isPartialMappingCompletionSupported(parentTargetObject);
                isChoice =
                        targetInfoProvider.isChoiceObject(parentTargetObject);
            }

            /*
             * To check for bad use of choice elements we need to know number of
             * siblings mapped in advance.
             */
            int numberMapped = 0;
            if (isChoice) {
                for (Object targetObject : targetObjects) {
                    boolean isMapped =
                            mappedTargetTreeObjects.contains(targetObject);
                    if (isMapped) {
                        numberMapped++;
                    } else {
                        /*
                         * IF not directly mapped, then count this choice
                         * element as mapped IF any descendent is mapped (as
                         * that will force this choice element to be included in
                         * output)
                         */
                        boolean descendantsMapped =
                                targetsWithMappedDescendants
                                        .contains(targetObject);
                        if (descendantsMapped) {
                            numberMapped++;
                        }

                    }
                }
            }

            for (Object targetObject : targetObjects) {
                boolean checkChildren = false;

                boolean isMapped =
                        mappedTargetTreeObjects.contains(targetObject);
                boolean descendantsMapped =
                        targetsWithMappedDescendants.contains(targetObject);
                boolean isSimpleContent =
                        targetInfoProvider.isSimpleTypeContent(targetObject);
                boolean isMultiInstance =
                        targetInfoProvider.isMultiInstance(targetObject);

                isInstanceElement =
                        targetInfoProvider.getInstanceIndex(targetObject) != -1;

                /**
                 * Sid XPD-7735. Moved check for nested target mappings to
                 * checkNestedTargetMappings() because this method does not
                 * recurse into children once it has found a mapping (else it
                 * would complain about mandatory children not mapped etc).
                 */
                /*
                 * Inclusion of multiple items within a choice type group is not
                 * permitted.
                 */
                if (isChoice && numberMapped > 1
                        && (isMapped || descendantsMapped)) {
                    createIssue(MappingIssue.MULTIPLE_CHOICES_MAPPED,
                            objectToValidate,
                            createMessageList(getMappingTypeDescription(objectToValidate),
                                    targetInfoProvider
                                            .getObjectPathDescription(targetObject)),
                            createAdditionalInfo(MapperContentProvider.TARGET_URI_ISSUEINFO,
                                    targetInfoProvider
                                            .getObjectPath(targetObject)),
                            null,
                            targetObject,
                            null);
                }
                /*
                 * A simple content type object that has child attributes mapped
                 * must itself be mapped (because it will be included in the
                 * output).
                 */
                else if (!isMapped && isSimpleContent && descendantsMapped) {
                    /**
                     * On certain occasions, the parent element needn't be
                     * mapped. These circumstances are particularly when
                     * 
                     * 1. you have a type of base restriction String but with no
                     * restrictions specified
                     * 
                     * 2. element refers to type with base restriction but
                     * element has specified default value.
                     */

                    if (!targetInfoProvider
                            .isNullSimpleContentAllowed(targetObject)) {
                        if (!partialCompletionPermitted) {
                            createIssue(MappingIssue.UNMAPPED_INCLUDED_SIMPLE_CONTENT,
                                    objectToValidate,
                                    createMessageList(getMappingTypeDescription(objectToValidate),
                                            targetInfoProvider
                                                    .getObjectPathDescription(targetObject)),
                                    createAdditionalInfo(MapperContentProvider.TARGET_URI_ISSUEINFO,
                                            targetInfoProvider
                                                    .getObjectPath(targetObject)),
                                    null,
                                    targetObject,
                                    null);
                        } else {
                            createIssue(MappingIssue.UNMAPPED_INCLUDED_SIMPLE_CONTENT_WARN,
                                    objectToValidate,
                                    createMessageList(getMappingTypeDescription(objectToValidate),
                                            targetInfoProvider
                                                    .getObjectPathDescription(targetObject)),
                                    createAdditionalInfo(MapperContentProvider.TARGET_URI_ISSUEINFO,
                                            targetInfoProvider
                                                    .getObjectPath(targetObject)),
                                    null,
                                    targetObject,
                                    null);
                        }
                    }
                    /*
                     * Need to check children to make sure all required
                     * attributes are mapped if any one or more are.
                     */
                    checkChildren = true;
                }
                /*
                 * Any object that is not mapped and has no descendant mapped is
                 * a problem if it is required.
                 */
                else if (!isMapped && !descendantsMapped) {
                    boolean isMandatory = false;

                    int minimumInstances =
                            targetInfoProvider
                                    .getMinimumInstances(targetObject);

                    if (isInstanceElement) {
                        /*
                         * An element in an array must be mapped if it's element
                         * index is less than the minimum required instances.
                         */
                        int instanceIndex =
                                targetInfoProvider
                                        .getInstanceIndex(targetObject);
                        if (minimumInstances > instanceIndex) {
                            isMandatory = true;
                        }

                    } else {
                        /*
                         * It's either a single instance element/attribute or an
                         * array elements header. In either case it is mandatory
                         * if minimum number of instances > 0
                         */
                        if (minimumInstances > 0) {
                            isMandatory = true;
                        }
                    }
                    boolean isArtificial =
                            targetInfoProvider.isArtificialObject(targetObject);
                    if (isMandatory || isArtificial) {
                        if (targetInfoProvider.isArtificialObject(targetObject)) {
                            /*
                             * If this is an artificial object in tree then it's
                             * just there to group it's children together and
                             * shouldn't itself be mapped, therefore it's not
                             * right to complain about _it_ being mapped, we
                             * should complain about each of it's mandatory
                             * children instead.
                             */
                            checkChildren = true;

                        } else if (isChoice && numberMapped != 0) {
                            /*
                             * If this is an element in a choice group and at
                             * least one is mapped then the others are not
                             * required.
                             */
                        } else {
                            /*
                             * This is a bone-fide target that's mandatory and
                             * not mapped so mark it as require target not
                             * mapped.
                             */
                            if (!partialCompletionPermitted) {

                                createIssue(isChoice ? MappingIssue.UNMAPPED_REQUIRED_CHOICE
                                        : MappingIssue.UNMAPPED_REQUIRED_TARGET,
                                        objectToValidate,
                                        createMessageList(getMappingTypeDescription(objectToValidate),
                                                targetInfoProvider
                                                        .getObjectPathDescription(targetObject)),
                                        createAdditionalInfo(MapperContentProvider.TARGET_URI_ISSUEINFO,
                                                targetInfoProvider
                                                        .getObjectPath(targetObject)),
                                        null,
                                        targetObject,
                                        null);
                            } else {

                                createIssue(isChoice ? MappingIssue.UNMAPPED_REQUIRED_CHOICE_WARN
                                        : MappingIssue.UNMAPPED_REQUIRED_TARGET_WARN,
                                        objectToValidate,
                                        createMessageList(getMappingTypeDescription(objectToValidate),
                                                targetInfoProvider
                                                        .getObjectPathDescription(targetObject)),
                                        createAdditionalInfo(MapperContentProvider.TARGET_URI_ISSUEINFO,
                                                targetInfoProvider
                                                        .getObjectPath(targetObject)),
                                        null,
                                        targetObject,
                                        null);
                            }
                        }
                    }
                }
                /*
                 * If this object is not mapped but will be included because any
                 * descendant is mapped, then we must check that all required
                 * descendants are mapped.
                 */
                else if (!isMapped && descendantsMapped) {
                    checkChildren = true;
                }
                /*
                 * If this object is mapped but is a simple content type, then
                 * need to check children (because you can map the simple
                 * content and it's child attributes in this case.
                 */
                else if (isMapped && isSimpleContent) {
                    checkChildren = true;
                }

                /*
                 * If this object is an array element (parent is multi-instance)
                 * and it's to be included because its mapped or one or more
                 * descendants are mapped then check that the maximum number of
                 * elements has not be exceeded.
                 * 
                 * Checked outside of main if statement because it applies to
                 * mapped and !mapped&& descendentsMapped
                 */
                if (isInstanceElement && (isMapped || descendantsMapped)) {
                    int maximumInstances =
                            targetInfoProvider
                                    .getMaximumInstances(targetObject);

                    if (maximumInstances != -1 && maximumInstances > 1) {
                        /*
                         * Specific maximum number of element instance can be
                         * mapped, check that this one hasn't exceeded the
                         * maximum.
                         */
                        int thisInstanceIndex =
                                targetInfoProvider
                                        .getInstanceIndex(targetObject);
                        if (thisInstanceIndex >= maximumInstances) {
                            createIssue(MappingIssue.MAXIMUM_ELEMENTS_EXCEEDED,
                                    objectToValidate,
                                    createMessageList(getMappingTypeDescription(objectToValidate),
                                            maximumInstances,
                                            targetInfoProvider
                                                    .getObjectPathDescription(targetObject)),
                                    createAdditionalInfo(MapperContentProvider.TARGET_URI_ISSUEINFO,
                                            targetInfoProvider
                                                    .getObjectPath(targetObject)),
                                    null,
                                    targetObject,
                                    null);

                            /*
                             * If this instance element exceeds the max allowed
                             * then there is little point checking its child
                             * mappings!
                             */
                            checkChildren = false;
                        }
                    }
                }

                if (checkChildren) {
                    Object[] children =
                            targetInfoProvider.getContentProvider()
                                    .getChildren(targetObject);
                    recursCheckMappingsComplete(objectToValidate,
                            mappedTargetTreeObjects,
                            targetsWithMappedDescendants,
                            children,
                            targetInfoProvider,
                            targetObject);
                }
            } /* Next target. */
        }

        return;
    }

    /**
     * Allows subclasses to permit mappings to descendants of a mapped object.
     * <p>
     * By default, we will allow mapping to parent and a descendant IF the
     * parent is simple type content (normally this implies the mapping to some
     * additional property of the simple type object.
     * 
     * @param mapping
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * 
     * @return true to allow descendant mapping, false (default) to disallow.
     */
    protected boolean allowDescendantMappings(Mapping mapping,
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        boolean isSimpleContent =
                targetInfoProvider.isSimpleTypeContent(mapping.getTarget());

        if (isSimpleContent) {
            return true;
        }
        return false;
    }

    /**
     * Build a map of target objects that have any descendant mapped.
     * <p>
     * Any ancestor of a target that's mapped is added to the set of targets
     * with mapped descendants.
     * <p>
     * We do this only once because we need to check every element in tree, so
     * we can do so quickly just by looking in this set.
     * 
     * @param mappedTargetTreeObjects
     * @param targetInfoProvider
     * 
     * @return Set of target objects that have descendants that are mapped.
     */
    private Set<Object> getTargetsWithMappedDescendants(
            Set<Object> mappedTargetTreeObjects,
            MappingRuleContentInfoProviderBase targetInfoProvider) {
        Set<Object> targetsWithMappedDescendants = new HashSet<Object>();

        ITreeContentProvider contentProvider =
                targetInfoProvider.getContentProvider();

        /* For each object that's mapped. */
        for (Object mappedTargetObject : mappedTargetTreeObjects) {
            /* Go backwards thru ascendents adding to list */
            Object target = mappedTargetObject;
            while (target != null) {
                target = contentProvider.getParent(target);
                if (target != null) {
                    targetsWithMappedDescendants.add(target);
                }
            }
        }

        return targetsWithMappedDescendants;
    }

    /**
     * Check that the types of the source and target object are compatible.
     * 
     * @param sourceObjectInTree
     *            located by using mappingObjectEqualsContentObject to compare
     *            mapping source object with source tree content
     * @param targetObjectInTree
     *            located by using targetObjectEquals to compare mapping target
     *            object with target tree content
     * @param mapping
     *            the mapping object from mappings content provider.
     * 
     * @return true if the types are compatible else false.
     */
    protected abstract boolean checkTypeCompatibility(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping);

    /**
     * @param singleInstanceSource
     *            located by using mappingObjectEqualsContentObject to compare
     *            mapping source object with source tree content
     * @param multiInstanceTarget
     *            located by using targetObjectEquals to compare mapping target
     *            object with target tree content
     * 
     * @return <code>true</code> if single to multi instance mapping is
     *         supported.
     */
    protected abstract boolean isSingleToMultiSupported(
            Object singleInstanceSource, Object multiInstanceTarget);

    /**
     * @param multiInstanceSource
     *            located by using mappingObjectEqualsContentObject to compare
     *            mapping source object with source tree content
     * @param singleInstanceTarget
     *            located by using targetObjectEquals to compare mapping target
     *            object with target tree content
     * 
     * @return <code>true</code> if single to multi instance mapping is
     *         supported.
     */
    protected abstract boolean isMultiToSingleSupported(
            Object multiInstanceSource, Object singleInstanceTarget);

    /**
     * @param multiInstanceSource
     *            located by using mappingObjectEqualsContentObject to compare
     *            mapping source object with source tree content
     * @param singleInstanceTarget
     *            located by using targetObjectEquals to compare mapping target
     *            object with target tree content
     * 
     * @return <code>true</code> if multiple to multiple instance mapping is
     *         supported.
     */
    protected abstract boolean isMultiToMultiSupported(
            Object multiInstanceSource, Object singleInstanceTarget);

    /**
     * The given target object is not mapped but will be included in mappings
     * because descendants are mapped.
     * <p>
     * This method should check whether it is permitted for only some of the
     * mandatory content to be assigned.
     * <p>
     * This is for situations where complex target data already exists before
     * mapping additional data into it.
     * 
     * @param targetObjectInTree
     * 
     * @return <code>true</code> if it is permitted to have only some of the
     *         mandatory descendants of this object mapped.
     */
    protected abstract boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree);

    /**
     * The sub-class should (if required) perform any additional validation on
     * the mapping.
     * <p>
     * If the extra validation involves analysis of all mappings as a whole then
     * use {@link #performAdditionalMappingsValidation(Collection)} in
     * preference to this method.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @param mapping
     *            mapping for soruce and target (for info)
     * @param sourceObjectInTree
     *            located by using targetObjectEquals to compare mapping source
     *            object with sourcey tree content
     * @param targetObjectInTree
     *            located by using targetObjectEquals to compare mapping source
     *            object with target tree content
     */
    protected abstract void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree);

    /**
     * Perform any additional validations on all mappings in the
     * objectToValidate being validated.
     * <p>
     * Note that the source and content providers are available via
     * {@link #getSourceInfoProvider(EObject)} {@link And}
     * {@link #getTargetInfoProvider(EObject)} if needed.
     * <p>
     * You may also use {@link #getSourceObject(Object)} and
     * {@link #getTargetObject(Object)} and other data access methods in this
     * class.
     * <p>
     * If extra validation is performed on an individual mapping then override
     * {@link #performAdditionalMappingValidation(Object, Object, Object)} in
     * preference to this method.
     * 
     * @param mappings
     */
    protected abstract void performAdditionalMappingsValidation(
            Object objectToValidate, Collection<Object> mappings);

    /**
     * @return The mappings according to sub-class's mapping content provider.
     */
    private List<Object> getMappings(EObject objectToValidate) {
        List<Object> list = null;
        IStructuredContentProvider mappingProvider =
                createMappingsContentProvider(objectToValidate);
        if (mappingProvider != null) {
            Object[] mappings =
                    mappingProvider
                            .getElements(getMappingContentProviderInput(objectToValidate));
            if (mappings != null && mappings.length > 0) {
                list = new ArrayList<Object>();
                for (Object object : mappings) {
                    list.add(object);
                }

            }
        }

        return list != null ? list : Collections.emptyList();
    }

    /**
     * Given the objectToValidate being validated return the appropriate top
     * level element input for the source content provider.
     * <p>
     * By default this is the objectToValidate.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * 
     * @return The input object required by the source content provider to
     *         return correct content
     */
    protected Object getSourceContentProviderInput(EObject objectToValidate) {
        return objectToValidate;
    }

    /**
     * Given the objectToValidate being validated return the appropriate top
     * level element input for the target content provider.
     * <p>
     * By default this is the objectToValidate.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * 
     * @return The input object required by the target content provider to
     *         return correct content
     */
    protected Object getTargetContentProviderInput(EObject objectToValidate) {
        return objectToValidate;
    }

    /**
     * Given the objectToValidate being validated return the appropriate top
     * level element input for the mapping content provider.
     * <p>
     * By default this is the objectToValidate.
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * 
     * @return The input object required by the mapping content provider to
     *         return correct content
     */
    protected Object getMappingContentProviderInput(EObject objectToValidate) {
        return objectToValidate;
    }

    /**
     * Get the source content path for the source object in the mapping.
     * <p>
     * This may be added to problem marker
     * {@link MapperContentProvider#SOURCE_URI_ISSUEINFO} or
     * {@link MapperContentProvider#DATAMAPPING_SOURCE_URI_ISSUEINFO} additional
     * info for issues that wish to put markers on source tree content of
     * mapper.
     * 
     * @param sourceInfoProvider
     * @param mapping
     * 
     * @return Source content path
     */
    protected final String getSourcePath(
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            Object mapping) {
        String path = null;

        Object sourceObjectFromMapping = getSourceObject(mapping);
        if (sourceObjectFromMapping != null) {
            path = sourceInfoProvider.getObjectPath(sourceObjectFromMapping);
        }

        return path != null ? path : ""; //$NON-NLS-1$
    }

    /**
     * Get the human readable content path for the source object (for use in
     * validation messages)
     * <p>
     * Wrapper for
     * {@link MappingRuleContentInfoProviderBase#getObjectPathDescription(Object)}
     * that, if sub-class cannot ascertain path description (maybe source
     * parameter is missing etc) then will try to ascertain the raw path from
     * the mapping model object (if it's a DataMapping).
     * 
     * @param sourceInfoProvider
     * @param mapping
     * 
     * @return source object path description
     */
    protected String getSourcePathDescription(
            MappingRuleContentInfoProviderBase sourceInfoProvider,
            Object mapping) {
        String path = null;

        Object sourceObjectFromMapping = getSourceObject(mapping);
        if (sourceObjectFromMapping != null) {
            path =
                    sourceInfoProvider
                            .getObjectPathDescription(sourceObjectFromMapping);
        }

        if (path == null || path.length() == 0) {
            /*
             * If the infoProvider didn't find a description then it maybe
             * because it couldn't find the source content for the given object
             * - so we'll try and pull out the raw data which might provide a
             * hint at least.
             * 
             * It's ok to assume DataMapping because there's no bad effect if
             * it's not - just after a descriptive label anyway.
             */
            EObject eo = getModelObjectForMapping(mapping);
            if (eo instanceof DataMapping) {
                DataMapping dataMapping = (DataMapping) eo;
                if (DirectionType.IN_LITERAL.equals(dataMapping.getDirection())) {
                    Expression actual = dataMapping.getActual();
                    if (actual != null) {
                        path = actual.getText();
                    }

                } else if (DirectionType.OUT_LITERAL.equals(dataMapping
                        .getDirection())) {
                    path = dataMapping.getFormal();
                }
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$
    }

    /**
     * Get the target content path for the target object in the mapping.
     * <p>
     * This may be added to problem marker
     * {@link MapperContentProvider#TARGET_URI_ISSUEINFO} or
     * {@link MapperContentProvider#DATAMAPPING_TARGET_URI_ISSUEINFO} additional
     * info for issues that wish to put markers on source tree content of
     * mapper.
     * 
     * @param targetInfoProvider
     * @param mapping
     * 
     * @return Target content path
     */
    protected final String getTargetPath(
            MappingRuleContentInfoProviderBase targetInfoProvider,
            Object mapping) {
        String path = null;

        Object targetObjectFromMapping = getTargetObject(mapping);
        if (targetObjectFromMapping != null) {
            path = targetInfoProvider.getObjectPath(targetObjectFromMapping);
        }

        return path != null ? path : ""; //$NON-NLS-1$
    }

    /**
     * Get the human readable content path for the target object (for use in
     * validation messages)
     * <p>
     * Wrapper for
     * {@link MappingRuleContentInfoProviderBase#getObjectPathDescription(Object)}
     * that, if sub-class cannot ascertain path description (maybe source
     * parameter is missing etc) then will try to ascertain the raw path from
     * the mapping model object (if it's a DataMapping).
     * 
     * @param targetInfoProvider
     * @param mapping
     * 
     * @return target object path description
     */
    protected String getTargetPathDescription(
            MappingRuleContentInfoProviderBase targetInfoProvider,
            Object mapping) {
        String path = null;

        Object targetObjectFromMapping = getTargetObject(mapping);
        if (targetObjectFromMapping != null) {
            path =
                    targetInfoProvider
                            .getObjectPathDescription(targetObjectFromMapping);
        }

        if (path == null || path.length() == 0) {
            /*
             * If the infoProvider didn't find a description then it maybe
             * because it couldn't find the source content for the given object
             * - so we'll try and pull out the raw data which might provide a
             * hint at least.
             * 
             * It's ok to assume DataMapping because there's no bad effect if
             * it's not - just after a descriptive label anyway.
             */
            EObject eo = getModelObjectForMapping(mapping);
            if (eo instanceof DataMapping) {
                DataMapping dataMapping = (DataMapping) eo;
                if (DirectionType.IN_LITERAL.equals(dataMapping.getDirection())) {
                    path = dataMapping.getFormal();

                } else if (DirectionType.OUT_LITERAL.equals(dataMapping
                        .getDirection())) {
                    Expression actual = dataMapping.getActual();
                    if (actual != null) {
                        path = actual.getText();
                    }
                }
            }
        }

        return path != null ? path : ""; //$NON-NLS-1$
    }

    /**
     * Add the given issue but only if the subclass does not want to ignore it.
     * <p>
     * If desired, this method can be overwritten to
     * <li>Ignore certain issues (for instance in cases where there is a BPMN
     * rule AND a separate destination specific rule that is stricter about some
     * things but does not want to duplicate others).</i>
     * <li>To swap certain issues for others in certain circumstances.</li>
     * 
     * @param issue
     *            The issue being raised.
     * @param issueTarget
     *            The object that the error is issued for (either
     *            objectToValidate or mapping object).
     * @param messages
     *            Extra message info for issue.
     * @param additionalInfo
     *            Additional info map for issue.
     * @param mapping
     *            The mapping object (if available else <code>null</code>).
     */
    protected void createIssue(MappingIssue issue, EObject issueTarget,
            List<String> messages, Map<String, String> additionalInfo,
            Object mapping) {

        Object source = null;
        Object target = null;
        if (mapping != null) {
            source = getSourceObject(mapping);
            target = getTargetObject(mapping);
        }

        createIssue(issue,
                issueTarget,
                messages,
                additionalInfo,
                source,
                target,
                mapping);

        return;
    }

    /**
     * Add the given issue but only if the subclass does not want to ignore it.
     * <p>
     * If desired, this method can be overwritten to
     * <li>Ignore certain issues (for instance in cases where there is a BPMN
     * rule AND a separate destination specific rule that is stricter about some
     * things but does not want to duplicate others).</i>
     * <li>To swap certain issues for others in certain circumstances.</li>
     * 
     * @param issue
     * @param issueTarget
     *            The object that the error is issued for (either
     *            objectToValidate or mapping object).
     * @param messages
     *            Extra message info for issue.
     * @param additionalInfo
     *            Additional info map for issue.
     * @param source
     *            The mapping source object if available else <code>null</code>
     * @param target
     *            The mapping target object if available else <code>null</code>
     * @param mapping
     *            The mapping object if available else <code>null</code>
     */
    protected void createIssue(MappingIssue issue, EObject issueTarget,
            List<String> messages, Map<String, String> additionalInfo,
            Object source, Object target, Object mapping) {
        addIssue(issue.getIssueId(), issueTarget, messages, additionalInfo);

        return;
    }

    /**
     * This method overirdes super class so that when issue is added with some
     * additional data (such as TARGET_URI_ISSUEINFO) we can add
     * MappingDirection to additional info too so that the SOURCE/TARGET URI can
     * be accurately interpreted.
     * 
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#addIssue(java.lang.String,
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
        MappingDirection mappingDirection = getMappingDirection();

        if (mappingDirection != null) {
            /*
             * Add mapping direction to additional info, taking care to create
             * it if it doesn't exist and copy to a new one if it does (because
             * it may be an immutable singleton map etc.
             */
            if (additionalInfo == null) {
                additionalInfo = new HashMap<String, String>();
            } else {
                additionalInfo = new HashMap<String, String>(additionalInfo);
            }

            /*
             * Don't overwrite if caller has taken care to explicitly set
             * direction for some reason.
             */
            if (!additionalInfo
                    .containsKey(MapperContentProvider.MAPPING_DIRECTION_ISSUEINFO)) {
                additionalInfo
                        .put(MapperContentProvider.MAPPING_DIRECTION_ISSUEINFO,
                                mappingDirection.name());
            }
        }

        super.addIssue(id, o, messages, additionalInfo);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected final void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        /* everything handled in main validate method. */
    }

    protected abstract ScriptInformationParsed parseScript(Object mapping);

    /**
     * Returns all the validation strategies for a given process
     * 
     * @param process
     * @return the list of validation strategies
     */
    protected List<IValidationStrategy> getValidationStrategyList(
            Process process, EObject objectToValidate) {
        List<String> processDestList = getProcessDestinationList(process);
        List<IValidationStrategy> validationStrategy = Collections.emptyList();
        try {
            validationStrategy =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getValidationStrategy(processDestList,
                                    getScriptType(),
                                    getScriptGrammar(objectToValidate),
                                    getDefaultScriptDestination());
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return validationStrategy;
    }

    /**
     * Returns the process destination list
     * 
     * @param process
     * @return list of process destinations
     */
    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(
                        DestinationUtil
                                .getEnabledValidationDestinations(process));

        if (processDestList == null || processDestList.isEmpty()) {
            processDestList = new ArrayList<String>();
            processDestList.add(getDefaultScriptDestination());
        }
        return processDestList;
    }

    /**
     * @return The script type
     */
    protected abstract String getScriptType();

    /**
     * @return The script grammar
     */
    protected abstract String getScriptGrammar(EObject objectToValidate);

    /**
     * @return The default script destination
     */
    protected abstract String getDefaultScriptDestination();

    /**
     * XPD-8115 : Changed the visibility from protected to public to support
     * delegation pattern Clears the Cache of {@link ScriptInformation}
     */
    public void clearScriptInformationCache() {
        mappingToScriptInfoMap = null;
    }

    /**
     * Method to get the cached script return type, it it cannot find one it
     * parses the script and caches the result
     * 
     * @param mapping
     * @return the script return type
     */
    protected IScriptRelevantData getCachedScriptReturnType(Object mapping) {
        ScriptInformationParsed cachedScriptInformationParsed =
                getCachedScriptInformationParsed(mapping);
        if (cachedScriptInformationParsed != null) {
            return cachedScriptInformationParsed.getReturnType();
        }
        return null;
    }

    /**
     * Method to get the cached script information parsed, it it cannot find one
     * it parses the script and caches the result
     * 
     * @param mapping
     * @return the script return type
     */
    protected ScriptInformationParsed getCachedScriptInformationParsed(
            Object mapping) {
        if (mappingToScriptInfoMap == null) {
            mappingToScriptInfoMap =
                    new HashMap<Object, ScriptInformationParsed>();
        }
        ScriptInformationParsed scriptInformationParsed =
                mappingToScriptInfoMap.get(mapping);
        if (scriptInformationParsed == null) {
            // Parse script and add to cache
            scriptInformationParsed = parseScript(mapping);
            mappingToScriptInfoMap.put(mapping, scriptInformationParsed);
        }
        return scriptInformationParsed;
    }

}
