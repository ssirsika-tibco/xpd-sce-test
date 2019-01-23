/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.baserules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.implementer.resources.xpdl2.errorEvents.CatchWsdlErrorEventUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlMappingItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.MappingStatus;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProviderWithContributions;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.processeditor.xpdl2.properties.IncomingRequestActivityTargetDataProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.bpmn.developer.providers.WebSvcXPathMappingRuleInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingRule;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate the mappings for Web Service XPath to Process Data content.
 * <p>
 * i.e. This validates service invocation output mappings (ServiceTask output
 * from service) AND non-generated request activities input (input to process)
 * 
 * @author aallway
 * @since 3.4.2 (19 Jul 2010)
 */
public abstract class AbstractWebSvcToProcessDataRuleForXPath extends
        AbstractActivityMappingRule implements IWsdlXpdlNamespaceForXPathRules {

    private static final String UNMAPPED_START_CORRELATION =
            "bpmn.dev.unmappedStartCorrelationData"; //$NON-NLS-1$

    private static final String UNMAPPED_EXPLICIT_CORRELATION =
            "bpmn.dev.unmappedCorrelationData"; //$NON-NLS-1$

    private static final String UNMAPPED_IMPLICIT_CORRELATION =
            "bpmn.dev.unmappedCorrelationDataImplicit"; //$NON-NLS-1$

    private boolean currentIsRequestActivity;

    private boolean currentIsGenerated;

    private boolean currentIsCatchFault;

    private Activity currentActivity;

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#validateObject(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     */
    @Override
    protected void validateObject(EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        MappingStatus mappingStatus =
                NamespacePrefixMapUtil.getMappingStatus(activity);

        if (MappingStatus.INCONSISTENT == mappingStatus) {
            addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_WSDL_XPDL_NAMESPACE_DISCREPANCY,
                    activity,
                    Arrays.asList(new String[] { activity.getName() }));
            return;
        } else if (MappingStatus.WSDL_INACCESSIBLE == mappingStatus) {
            return;
        }

        switch (mappingStatus) {
        case NONE_EXISTS: {
            addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_MISSING_WSDL_XPDL_NAMESPACE_MAP,
                    activity);
            break;
        }
        case SAME_PREFIX_FOR_DIFFERENT_NAMESPACES: {
            addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_SAME_PREFIX_DIFFERENT_NS,
                    activity,
                    Arrays.asList(new String[] { mappingStatus.getMessage() }));
            break;
        }
        case DIFFERENT_PREFIXES_FOR_SAME_NAMESPACE: {
            addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_DIFFERENT_PREFIX_SAME_NS,
                    activity,
                    Arrays.asList(new String[] { mappingStatus.getMessage() }));
            break;
        }
        case ADDITIONAL_XSD_NS_PREFIXES_REQUIRED: {
            addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_REQUIRE_ADDITIONAL_XSD_NS,
                    activity,
                    Arrays.asList(new String[] { mappingStatus.getMessage() }));
            break;
        }
        case NS_MAPPING_DISBALED_BUT_REQUIRED: {
            addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_WSDL_XPDL_NS_MAP_DISABLED_BUT_REQUIRED,
                    activity);
            break;
        }
        }

        super.validateObject(activity);

    }

    /**
     * @param activity
     * @return The mapping grammar that the sub-class is interested in.
     */
    protected String getApplicableGrammar(Activity activity) {
        return ScriptGrammarFactory.XPATH;
    }

    /**
     * @param contentProvider
     * @return The mapping rule info provider for the web service side of the
     *         data.
     */
    protected MappingRuleContentInfoProvider createWebServiceDataInfoProvider(
            CompositeTreeContentProvider contentProvider) {
        WebSvcXPathMappingRuleInfoProvider infoProvider =
                new WebSvcXPathMappingRuleInfoProvider(contentProvider);
        return infoProvider;
    }

    @Override
    protected String getDefaultScriptDestination() {
        return ProcessXPathConsts.XPATH_DESTINATION;
    }

    @Override
    protected boolean isScriptMappingTypeResolved(Object mapping,
            EObject objectToValidate) {
        // This is handled by AbstractXPathExpressionValidator
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#parseScript(java.lang.Object)
     * 
     * @param mapping
     * @return
     */
    @Override
    protected ScriptInformationParsed parseScript(Object mapping) {
        /* Validation of XPath script mapping scriptss is done elsewhere. */
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * activityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected boolean objectIsApplicable(EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        currentIsRequestActivity = false;
        currentIsGenerated = false;
        currentIsCatchFault = false;
        currentActivity = activity;

        if (WebServiceOperationUtil.isWebServiceImplementationType(activity)) {
            if (isApplicableGrammar(activity)) {
                if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))) {
                    /*
                     * Service task invocations can have WebSvc Data to Process
                     * data mappings (output from service)
                     */
                    return true;
                } else if (ReplyActivityUtil
                        .isIncomingRequestActivity(activity)) {
                    /*
                     * Request activity can have WebSvc Data to Process data
                     * mappings (input to process).
                     */
                    currentIsRequestActivity = true;
                    if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                        currentIsGenerated = true;
                    }

                    return true;

                } else if (isCatchWsdlFaultEvent(activity)) {
                    currentIsCatchFault = true;
                    return true;
                }
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * getMappingTypeDescription(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        if (currentIsRequestActivity) {
            return Messages.MappingRule_InputToProcess_label;
        } else if (currentIsCatchFault) {
            return Messages.AbstractWebSvcToProcessDataRule_MapFromError_label;
        }
        return Messages.MappingRule_OuputFromService_label;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * createMappingsContentProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        /*
         * If the activity is a request activity then we need WSDL Input Part ->
         * Process Data.
         * 
         * If it's return from a Web Service Invoke then we need WSDL Output
         * Part -> Process Data.
         */
        boolean isWebSvcInputPart = currentIsRequestActivity;

        /*
         * For Web service data -> process data MappingDirection.OUT always
         * applies because mapping direction is always from perspective of the
         * WSDL
         * 
         * (hence input to process = MappingDirection.OUT from WSDL Input part
         * :: Output From Service = MappingsDirection.OUT from WSDL Output
         * part).
         */
        ActivityMessageWsdlMappingItemProvider mappingContentProvider =
                new ActivityMessageWsdlMappingItemProvider(
                        MappingDirection.OUT, isWebSvcInputPart) {
                    /*
                     * (non-Javadoc)
                     * 
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties
                     * .ActivityMessageWsdlMappingItemProvider
                     * #getElements(java.lang.Object)
                     */
                    @Override
                    public Object[] getElements(Object parent) {
                        Object[] elements = super.getElements(parent);

                        if (currentIsRequestActivity && currentIsGenerated) {
                            /*
                             * For generated request activities then we only
                             * want to validate correlation mappings.
                             */
                            List<Object> correlationMappings =
                                    new ArrayList<Object>();
                            for (Object element : elements) {
                                if (isCorrelationMapping(element)) {
                                    correlationMappings.add(element);
                                }
                            }

                            elements = correlationMappings.toArray();
                        }

                        return elements;
                    }

                    /**
                     * @param mapping
                     * @return true if mapping is to a correlation field.
                     */
                    private boolean isCorrelationMapping(Object mapping) {
                        if (mapping instanceof Mapping) {
                            Object dataMapping =
                                    ((Mapping) mapping).getMappingModel();
                            if (dataMapping instanceof DataMapping) {
                                if (((DataMapping) dataMapping).eContainer() instanceof CorrelationDataMappings) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                };

        mappingContentProvider.inputChanged(null, null, activity);

        return mappingContentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * createSourceInfoProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected final MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        CompositeTreeContentProvider contentProvider;
        if (currentIsRequestActivity) {
            /*
             * Need slightly different providers for incoming request and output
             * from service mappings.
             */
            contentProvider =
                    new CompositeTreeContentProvider(
                            new ActivityMessageWsdlItemProvider(
                                    MappingDirection.OUT,
                                    DirectionType.OUT_LITERAL, WsdlDirection.IN),
                            new ActivityScriptContentProvider(
                                    MappingDirection.OUT));

        } else {
            /* Output from service. */
            contentProvider =
                    new CompositeTreeContentProvider(
                            new ActivityMessageWsdlItemProvider(
                                    MappingDirection.OUT,
                                    DirectionType.OUT_LITERAL,
                                    WsdlDirection.OUT),
                            new ActivityScriptContentProvider(
                                    MappingDirection.OUT));

        }

        MappingRuleContentInfoProvider infoProvider =
                createWebServiceDataInfoProvider(contentProvider);

        infoProvider.getContentProvider().inputChanged(null, null, activity);

        return infoProvider;

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * createTargetInfoProvider(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    protected MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        ITreeContentProvider contentProvider;
        if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
            contentProvider =
                    new IncomingRequestActivityTargetDataProvider(
                            MappingDirection.OUT);
        } else {
            contentProvider =
                    new CompositeTreeContentProvider(
                            new ActivityDataFieldItemProviderWithContributions(
                                    MappingDirection.OUT),
                            new ActivityScriptContentProvider(
                                    MappingDirection.OUT));
        }

        ProcessDataMappingRuleInfoProvider targetInfoProvider =
                new ProcessDataMappingRuleInfoProvider(contentProvider);

        targetInfoProvider.getContentProvider().inputChanged(null,
                null,
                activity);
        return targetInfoProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#
     * isPartialMappingCompletionSupported(java.lang.Object)
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        /*
         * Assume that the process engine will allow partial creation of data,
         * if not then destination specific subclass can override.
         * 
         * Except for correlation data folder whose contents must be considered
         */
        if (targetObjectInTree instanceof CorrelationDataFolder) {
            return false;
        }
        return true;
    }

    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
        return;
    }

    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {
        if (mappings != null && !mappings.isEmpty()) {
            Set<ConceptPath> sourceObjectWithChildrenMapped =
                    new HashSet<ConceptPath>();
            Set<ConceptPath> targetObjectWithChildrenMapped =
                    new HashSet<ConceptPath>();
            Set<Mapping> mappingErrorCandidates = new HashSet<Mapping>();
            Activity activity = null;
            for (Object obj : mappings) {
                if (obj instanceof Mapping) {
                    Mapping mapping = (Mapping) obj;
                    if (activity == null
                            && mapping.getMappingModel() instanceof DataMapping) {
                        DataMapping dataMapping =
                                (DataMapping) mapping.getMappingModel();
                        activity =
                                Xpdl2ModelUtil.getParentActivity(dataMapping);
                    }
                    Object sourceObject = getSourceObject(mapping);
                    Object targetObject = getTargetObject(mapping);
                    if (sourceObject instanceof ConceptPath
                            && !ConceptUtil.isRoot((ConceptPath) sourceObject)) {
                        sourceObjectWithChildrenMapped
                                .add(((ConceptPath) sourceObject).getRoot());
                        mappingErrorCandidates.add(mapping);
                    }
                    if (targetObject instanceof ConceptPath) {
                        targetObjectWithChildrenMapped
                                .add(((ConceptPath) targetObject).getRoot());
                        mappingErrorCandidates.add(mapping);
                    }
                }
            }
            if (activity != null) {
                for (ConceptPath sourceObjectRoot : sourceObjectWithChildrenMapped) {
                    for (ConceptPath targetObjectRoot : targetObjectWithChildrenMapped) {
                        if (sourceObjectRoot.equals(targetObjectRoot)) {
                            for (Mapping mapping : mappingErrorCandidates) {
                                Object sourceObject = getSourceObject(mapping);
                                Object targetObject = getTargetObject(mapping);
                                if (sourceObject instanceof ConceptPath
                                        && targetObject instanceof ConceptPath) {
                                    if (((ConceptPath) sourceObject).getRoot() != null
                                            && ((ConceptPath) targetObject)
                                                    .getRoot() != null
                                            && (((ConceptPath) sourceObject)
                                                    .getRoot()
                                                    .equals(sourceObjectRoot) || ((ConceptPath) targetObject)
                                                    .getRoot()
                                                    .equals(targetObjectRoot))) {
                                        // Wsdl Part and Process Relevant data
                                        // collide
                                        addIssue(MappingIssue.WSDLPART_AND_PROCESSDATA_NAME_COLLISISON
                                                .getIssueId(),
                                                getModelObjectForMapping(mapping),
                                                Collections
                                                        .singletonList(sourceObjectRoot
                                                                .getName()),
                                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                                        ((ConceptPath) sourceObject)
                                                                .getPath(),
                                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                                        ((ConceptPath) targetObject)
                                                                .getPath()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return;
    }

    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        /*
         * In general the mapping command provider will have prevented illegal
         * source/target selections for mappings.
         */
        return true;
    }

    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        /*
         * In general the mapping command provider will have prevented illegal
         * source/target selections for mappings.
         */
        return true;
    }

    /**
     * @param activity
     * @return The mapping grammar that the sub-class is interested in.
     */
    private boolean isApplicableGrammar(Activity activity) {
        /*
         * WebSvcData To ProcessData is always direction OUT because it is
         * always from the perspective of WSDL data. i.e. Service task invoke
         * output data is obviosuly DirectionType.OUT and RequestActivity is
         * DirectionTYpe.OUT as well! (just means
         * "OUT from the input part of WSDL")
         */
        String grammar =
                ScriptGrammarFactory.getScriptGrammar(activity,
                        DirectionType.OUT_LITERAL);
        if (grammar == null) {
            grammar = ScriptGrammarFactory.getDefaultScriptGrammar(activity);
        }

        String applicableGrammar = getApplicableGrammar(activity);
        if (applicableGrammar != null) {
            if (applicableGrammar.equals(grammar)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if the given activity is a catch error event
     *         for a WSDL fault message.
     */
    private boolean isCatchWsdlFaultEvent(Activity activity) {
        if (ErrorCatchType.CATCH_SPECIFIC.equals(BpmnCatchableErrorUtil
                .getCatchType(activity))) {
            Object thrower = BpmnCatchableErrorUtil.getErrorThrower(activity);
            if (thrower instanceof Activity) {

                return CatchWsdlErrorEventUtil
                        .isWsdlFaultThrowingActivityType((Activity) thrower);

            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule#createIssue
     * (com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRule.
     * MappingIssue, org.eclipse.emf.ecore.EObject, java.util.List,
     * java.util.Map, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    @Override
    protected void createIssue(MappingIssue issue, EObject issueTarget,
            List<String> messages, Map<String, String> additionalInfo,
            Object source, Object target, Object mapping) {
        /*
         * If we are about to raise an unmapped target object issue and it's a
         * correlation data field, then create different issues.
         */
        if (currentIsRequestActivity) {
            if (MappingIssue.UNMAPPED_INCLUDED_SIMPLE_CONTENT.equals(issue)
                    || MappingIssue.UNMAPPED_REQUIRED_CHOICE.equals(issue)
                    || MappingIssue.UNMAPPED_REQUIRED_TARGET.equals(issue)) {

                if (isCorrelationDataTarget(target)) {
                    if (isImplicitCorrelationAssociations(currentActivity)) {

                        /*
                         * XPD-6751: Saket: Modified
                         * Xpdl2ModelUtil.isMessageProcessStartActivity
                         * (activity) to return true for only those activities
                         * that are in the main process and therefore we don't
                         * need to have
                         * Xpdl2ModelUtil.isEventSubProcessStartEvent(activity)
                         * alongside it.
                         */
                        if (Xpdl2ModelUtil
                                .isMessageProcessStartActivity(currentActivity)) {
                            addIssue(UNMAPPED_START_CORRELATION,
                                    currentActivity,
                                    createMessageList(((ConceptPath) target)
                                            .getName()),
                                    additionalInfo);
                        } else {
                            addIssue(UNMAPPED_IMPLICIT_CORRELATION,
                                    currentActivity,
                                    createMessageList(((ConceptPath) target)
                                            .getName()),
                                    additionalInfo);
                        }

                    } else {
                        addIssue(UNMAPPED_EXPLICIT_CORRELATION,
                                currentActivity,
                                createMessageList(((ConceptPath) target)
                                        .getName()),
                                additionalInfo);

                    }
                    return;
                }
            }

        }

        super.createIssue(issue,
                issueTarget,
                messages,
                additionalInfo,
                source,
                target,
                mapping);
        return;
    }

    /**
     * @param target
     * @return <code>true</code> if the target represents a correlation data
     *         field.
     */
    private boolean isCorrelationDataTarget(Object target) {
        if (target instanceof DataField) {
            return ((DataField) target).isCorrelation();
        } else if (target instanceof ConceptPath) {
            Object item = ((ConceptPath) target).getItem();
            if (item instanceof DataField) {
                return ((DataField) item).isCorrelation();
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if activity has implicit correlation data
     *         associations rather than explicit.
     */
    private boolean isImplicitCorrelationAssociations(Activity activity) {
        boolean isImplicit = true;
        Object other =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedCorrelationFields());
        if (other instanceof AssociatedCorrelationFields) {
            AssociatedCorrelationFields fieldContainer =
                    (AssociatedCorrelationFields) other;
            isImplicit =
                    fieldContainer.getAssociatedCorrelationField().size() == 0;
        }
        return isImplicit;
    }

    @Override
    protected String getScriptGrammar(EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        return getApplicableGrammar(activity);
    }

    @Override
    protected String getScriptType() {
        return ProcessJsConsts.WEBSERVICE_TASK;
    }

    /*
     * Sid: Moved parseScript() up to JavaScript specific sub-classes because
     * this implementation is javaScript specific!
     */

}
