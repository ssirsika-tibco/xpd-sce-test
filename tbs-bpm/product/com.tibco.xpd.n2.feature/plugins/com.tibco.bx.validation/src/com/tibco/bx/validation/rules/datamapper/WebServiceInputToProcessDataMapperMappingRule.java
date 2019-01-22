/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue;
import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;
import com.tibco.xpd.webservice.datamapper.WebServiceScriptDataMapperProvider;
import com.tibco.xpd.webservice.datamapper.section.filter.WebServiceDataMapperInputToProcessSectionFilter;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * DataMapper validation rule for WebService InputToProcess
 * 
 * @author ssirsika
 * @since 01-Feb-2016
 */
public class WebServiceInputToProcessDataMapperMappingRule extends
        AbstractWebSvcToProcessDataMapperRule {

    private static final String UNMAPPED_START_CORRELATION =
            "bpmn.dev.unmappedStartCorrelationData"; //$NON-NLS-1$

    private static final String UNMAPPED_EXPLICIT_CORRELATION =
            "bpmn.dev.unmappedCorrelationData"; //$NON-NLS-1$

    private static final String UNMAPPED_IMPLICIT_CORRELATION =
            "bpmn.dev.unmappedCorrelationDataImplicit"; //$NON-NLS-1$

    /**
     * Class level field to track the activity being validated.
     */
    Activity activity;

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#objectIsApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {

        activity = null;

        if (eo instanceof Activity) {
            activity = (Activity) eo;

            WebServiceDataMapperInputToProcessSectionFilter filer =
                    new WebServiceDataMapperInputToProcessSectionFilter();

            return filer.select(activity) && super.objectIsApplicable(eo);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected IScriptDataMapperProvider getScriptDataMapperProvider() {
        return new WebServiceScriptDataMapperProvider(getDataMapperContext(),
                getMappingDirection());
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.INPUT_TO_PROCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.WebServiceInputToProcessDataMapperMappingRule_MappingTypeDescription;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
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
        if (targetObjectInTree instanceof WrappedContributedContent) {
            Object contributedObject =
                    ((WrappedContributedContent) targetObjectInTree)
                            .getContributedObject();

            if (contributedObject instanceof CorrelationDataFolder) {
                return false;
            }
        }

        // Partial mapping completion should be disallowed ONLY for `start of
        // process` activities.
        return !Xpdl2ModelUtil.isStartProcessActivity(activity);
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isMultiToSingleSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return super.isMultiToSingleSupported(multiInstanceSource,
                singleInstanceTarget);
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#createIssue(com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue,
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
        /*
         * If we are about to raise an unmapped target object issue and it's a
         * correlation data field, then create different issues.
         */
        if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
            if (MappingIssue.UNMAPPED_INCLUDED_SIMPLE_CONTENT.equals(issue)
                    || MappingIssue.UNMAPPED_REQUIRED_CHOICE.equals(issue)
                    || MappingIssue.UNMAPPED_REQUIRED_TARGET.equals(issue)) {
                ConceptPath conceptPath = getConceptPath(target);
                if (conceptPath != null) {
                    if (isImplicitCorrelationAssociations(activity)) {

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
                                .isMessageProcessStartActivity(activity)) {
                            addIssue(UNMAPPED_START_CORRELATION,
                                    activity,
                                    createMessageList(conceptPath.getName()),
                                    additionalInfo);
                        } else {
                            addIssue(UNMAPPED_IMPLICIT_CORRELATION,
                                    activity,
                                    createMessageList(conceptPath.getName()),
                                    additionalInfo);
                        }

                    } else {
                        addIssue(UNMAPPED_EXPLICIT_CORRELATION,
                                activity,
                                createMessageList(conceptPath.getName()),
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
     * Return the {@link ConceptPath} either from
     * {@link WrappedContributedContent} or if passed 'obj' is instance of
     * {@link ConceptPath}
     * 
     * @param obj
     * @return return {@link ConceptPath} or null if can not find
     *         {@link ConceptPath} from passed 'obj'.
     */
    private ConceptPath getConceptPath(Object obj) {
        if (obj instanceof WrappedContributedContent) {
            Object contributedObject =
                    ((WrappedContributedContent) obj).getContributedObject();
            if (contributedObject instanceof ConceptPath) {
                return (ConceptPath) contributedObject;
            }
        } else if (obj instanceof ConceptPath) {
            return (ConceptPath) obj;
        }
        return null;
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

}
