/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Part;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.tibco.xpd.datamapper.DataMapperMappingContentProvider;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlMappingItemProvider;
import com.tibco.xpd.implementer.script.XsdPath;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.WsdlPartConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;
import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperUtil;
import com.tibco.xpd.webservice.datamapper.WebServiceScriptDataMapperProvider;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * For each correlation data mapping (in incoming request activity) a
 * <propertyAlias> entry is added by xpdl2bpel to the copy of the referenced
 * WSDL that is place in the .processOut folder for the BPEL. Each
 * <propertyAlias> is uniquely identified by 3 things: wsdl message type, part
 * name and correlation-data-field-name (the property-name) and contains the
 * XPath expression equivalent of the data mapping.
 * <p>
 * Therefore all correlation data mappings from the same-message-part to the
 * same-named-correlationfield must be identical i.e. the data mapping must be
 * from exactly the same input part content element.
 * <p>
 * Note that after the the related BX JIRA is implemented, the propertyAlias
 * property-name is will be prefixed with the process-name and therefore only
 * needs to be consistently used for correlation mappings within a given
 * process.
 * 
 * @author aallway
 * @since 28 Sep 2012
 */
public class ConsistentCorrelationUseForMsgPartRule extends
        ProcessValidationRule {

    public static final String ISSUE_INCONSISTENT_CORRELATION_FROM_SAME_PART =
            "bx.inconsistentUseOfCorrelationForSameMessage"; //$NON-NLS-1$

    /**
     * Flag to specify if grammar type is DataMapper
     */
    private boolean isDataMapperScriptGrammar = false;

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        List<CorrelationMappingRecord> correlationDataMappings =
                getCorrelationMappingRecords(process);

        while (!correlationDataMappings.isEmpty()) {
            correlationDataMappings = checkMappings(correlationDataMappings);
        }

    }

    /**
     * Looking at the first mapping in list, find all the mappings that have
     * same source message part and target correlation field.
     * <p>
     * If multiple mappings for same message-part and correlation field have
     * different mapping source path then add an issue.
     * <p>
     * The set of mappings with same message-part and correlation field are
     * REMOVED from the list and it is returned.
     * 
     * @param correlationDataMappings
     * @return same list with already processed contents filtered.
     */
    private List<CorrelationMappingRecord> checkMappings(
            List<CorrelationMappingRecord> correlationDataMappings) {

        if (!correlationDataMappings.isEmpty()) {
            /*
             * Track list of mappings with the same message-part and correlation
             * data name as first element in list.
             */
            List<CorrelationMappingRecord> mappingsWithSamePartAndName =
                    new ArrayList<ConsistentCorrelationUseForMsgPartRule.CorrelationMappingRecord>();

            /* Get the first entry from the list. */
            Iterator<CorrelationMappingRecord> iterator =
                    correlationDataMappings.iterator();

            CorrelationMappingRecord firstMapping = iterator.next();
            mappingsWithSamePartAndName.add(firstMapping);
            iterator.remove();

            /* Get other mappings from same part to same message name. */
            boolean hasDifferentMappingPath = false;

            while (iterator.hasNext()) {
                CorrelationMappingRecord nextMapping = iterator.next();

                if (firstMapping.isSameMessagePartAndCorrelation(nextMapping)) {
                    /*
                     * Remove this entry from list as we've already processed
                     * it.
                     */
                    mappingsWithSamePartAndName.add(nextMapping);
                    iterator.remove();

                    /*
                     * If we haven't already spotted a different in the mapping
                     * paths of others then check this one (no point keep
                     * re-checking all if one is different.)
                     */
                    if (!hasDifferentMappingPath) {
                        if (!firstMapping.isSameMappingPath(nextMapping)) {
                            /*
                             * There are multiple mappings from the same message
                             * part to the same correlation data field with
                             * source-of-mapping.
                             */
                            hasDifferentMappingPath = true;
                        }
                    }

                }
            }

            /*
             * Add issues if we any mapping path was different in set.
             */
            if (hasDifferentMappingPath) {
                for (CorrelationMappingRecord issueMapping : mappingsWithSamePartAndName) {
                    List<String> params = new ArrayList<String>();
                    params.add(issueMapping.messagePart.getName());
                    params.add(issueMapping.correlationName);

                    Map<String, String> addInfo = new HashMap<String, String>();
                    addInfo.put(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                            isDataMapperScriptGrammar ? issueMapping.correlationMapping
                                    .getActual().getText()
                                    : issueMapping.correlationMapping
                                            .getFormal());
                    addInfo.put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                            isDataMapperScriptGrammar ? issueMapping.correlationMapping
                                    .getFormal()
                                    : issueMapping.correlationMapping
                                            .getActual().getText());

                    addIssue(ISSUE_INCONSISTENT_CORRELATION_FROM_SAME_PART,
                            issueMapping.correlationMapping,
                            params,
                            addInfo);
                }
            }

        }

        return correlationDataMappings;
    }

    /**
     * @param process
     * @return correlation mapping record info list for all incoming request
     *         activities in process
     */
    private List<CorrelationMappingRecord> getCorrelationMappingRecords(
            Process process) {
        List<CorrelationMappingRecord> correlationDataMappings =
                new ArrayList<CorrelationMappingRecord>();

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            /**
             * NOTE: We'll will completely ignore request activities that
             * generate WSDL operations.
             * 
             * This is because (a) WSDL gen always creates UNIQUE messages for
             * each operation and (b) it makes life easier because it's not
             * always easy to get hold of the message part for generated
             * operations.
             */
            if (ReplyActivityUtil.isIncomingRequestActivity(activity)
                    && !Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                /*
                 * MappingDirection.OUT because it is OUT from wsdl input data
                 * IN to process data
                 */
                IStructuredContentProvider mappingContentProvider;

                String scriptGrammar =
                        ScriptGrammarFactory.getGrammarToUse(activity,
                                DirectionType.get(MappingDirection.OUT
                                        .toString()));

                if (ScriptGrammarFactory.DATAMAPPER.equals(scriptGrammar)) {
                    isDataMapperScriptGrammar = true;
                    mappingContentProvider =
                            new DataMapperMappingContentProvider(
                                    new WebServiceScriptDataMapperProvider(
                                            WebServiceDataMapperConstants.INPUT_TO_PROCESS,
                                            MappingDirection.OUT));
                } else {
                    isDataMapperScriptGrammar = false;
                    mappingContentProvider =
                            new ActivityMessageWsdlMappingItemProvider(
                                    MappingDirection.OUT, true);
                }

                if (mappingContentProvider != null) {
                    /* Set input as is reqired by contentProvider API contract */
                    mappingContentProvider.inputChanged(null, null, activity);

                    Object[] mappingElements =
                            mappingContentProvider.getElements(activity);

                    if (mappingElements != null) {
                        for (Object mappingElement : mappingElements) {
                            CorrelationMappingRecord cmr =
                                    CorrelationMappingRecord
                                            .createCorrelationMappingRecord(activity,
                                                    mappingElement,
                                                    isDataMapperScriptGrammar);

                            if (cmr != null) {
                                correlationDataMappings.add(cmr);
                            }
                        }
                    }
                    mappingContentProvider = null;
                }
            }
        }

        return correlationDataMappings;
    }

    /**
     * Record of an instance of a correlation mapping including the containing
     * incoming request activity and the source message part.
     * 
     * @author aallway
     * @since 28 Sep 2012
     */
    private static class CorrelationMappingRecord {
        /* Mapping contentProvider object representing mapping. */
        Mapping mapping;

        /* Source WSDL message part. */
        Part messagePart;

        /* Actual correlation mapping model */
        DataMapping correlationMapping;

        /* COrrelation field name. */
        String correlationName;

        /* Host activity */
        Activity activity;

        /**
         * Flag to specify if grammar type is DataMapper
         */

        private boolean isDataMapperScriptGrammarInternal;

        /**
         * @param activity
         * @param mappingContentProviderElement
         * @param isDataMapperScriptGrammar
         * @return {@link CorrelationMappingRecord} for given
         *         {@link ActivityMessageWsdlMappingItemProvider} content object
         *         or <code>null</code> if not a correlation mapping OR possibly
         *         that the mapping is broken (in which case we don't need to
         *         worry about it because other rules will pick it up.)
         */
        static CorrelationMappingRecord createCorrelationMappingRecord(
                Activity activity, Object mappingContentProviderElement,
                boolean isDataMapperScriptGrammar) {
            if ((mappingContentProviderElement instanceof Mapping)) {
                Mapping mapping = (Mapping) mappingContentProviderElement;

                if ((mapping.getMappingModel() instanceof DataMapping)) {

                    DataMapping dataMapping =
                            (DataMapping) mapping.getMappingModel();

                    if ((dataMapping.eContainer() instanceof CorrelationDataMappings)) {
                        /* Is a correlation mapping. */
                        String correlationName = null;

                        if (mapping.getTarget() instanceof ConceptPath) {
                            ConceptPath target =
                                    (ConceptPath) mapping.getTarget();
                            correlationName = target.getRoot().getName();
                        }

                        if (correlationName != null) {
                            /*
                             * Get the message part (handles XPath and
                             * JavaScript grammars.
                             */
                            Part messagePart = null;

                            if (mapping.getSource() instanceof ConceptPath) {
                                ConceptPath source =
                                        (ConceptPath) mapping.getSource();

                                if (source.getRoot() instanceof WsdlPartConceptPath) {
                                    messagePart =
                                            ((WsdlPartConceptPath) source
                                                    .getRoot()).getPart();
                                }

                            } else if (mapping.getSource() instanceof XsdPath) {
                                XsdPath source = (XsdPath) mapping.getSource();

                                messagePart = source.getPart();
                            }

                            if (messagePart != null) {
                                return new CorrelationMappingRecord(activity,
                                        mapping, messagePart, dataMapping,
                                        correlationName,
                                        isDataMapperScriptGrammar);
                            }
                        }
                    } else if (dataMapping.eContainer() instanceof ScriptDataMapper
                            && WebServiceDataMapperUtil
                                    .isMappedToCorrelationData(dataMapping)) {
                        // this part handles DataMapper script grammar

                        String correlationName = null;
                        Object targetObject = mapping.getTarget();
                        if (targetObject instanceof WrappedContributedContent) {
                            targetObject =
                                    ((WrappedContributedContent) targetObject)
                                            .getContributedObject();
                        }
                        if (targetObject instanceof ConceptPath) {
                            ConceptPath target = (ConceptPath) targetObject;
                            correlationName = target.getRoot().getName();
                        }

                        if (correlationName != null) {

                            Part messagePart = null;
                            Object sourceObject = mapping.getSource();
                            if (sourceObject instanceof WrappedContributedContent) {
                                sourceObject =
                                        ((WrappedContributedContent) sourceObject)
                                                .getContributedObject();
                            }
                            if (sourceObject instanceof ConceptPath) {
                                ConceptPath source = (ConceptPath) sourceObject;

                                if (source.getRoot() instanceof WsdlPartConceptPath) {
                                    messagePart =
                                            ((WsdlPartConceptPath) source
                                                    .getRoot()).getPart();
                                }

                            }

                            if (messagePart != null) {
                                return new CorrelationMappingRecord(activity,
                                        mapping, messagePart, dataMapping,
                                        correlationName,
                                        isDataMapperScriptGrammar);
                            }
                        }
                    }
                }
            }

            return null;
        }

        /**
         * @param activity
         * @param mapping
         * @param messagePart
         * @param correlationMapping
         * @param correlationName
         * @param isDataMapperScriptGrammar
         */
        public CorrelationMappingRecord(Activity activity, Mapping mapping,
                Part messagePart, DataMapping correlationMapping,
                String correlationName, boolean isDataMapperScriptGrammar) {
            super();
            this.activity = activity;
            this.mapping = mapping;
            this.messagePart = messagePart;
            this.correlationMapping = correlationMapping;
            this.correlationName = correlationName;
            this.isDataMapperScriptGrammarInternal = isDataMapperScriptGrammar;
        }

        /**
         * @param other
         * @return <code>true</code> if the referenced message part and
         *         correlation data field name are the same.
         */
        boolean isSameMessagePartAndCorrelation(CorrelationMappingRecord other) {

            if (correlationName.equals(other.correlationName)
                    && messagePart.equals(other.messagePart)) {
                return true;
            }

            return false;
        }

        /**
         * @param other
         * @return <code>true</code> if the correlation mapping mapth is same in
         *         this record that the passed one.
         */
        boolean isSameMappingPath(CorrelationMappingRecord other) {

            String thisPath =
                    isDataMapperScriptGrammarInternal ? correlationMapping
                            .getActual().getText() : correlationMapping
                            .getFormal();
            if (thisPath == null) {
                thisPath = ""; //$NON-NLS-1$
            }
            String otherPath =
                    isDataMapperScriptGrammarInternal ? other.correlationMapping
                            .getActual().getText() : other.correlationMapping
                            .getFormal();
            if (otherPath == null) {
                otherPath = ""; //$NON-NLS-1$
            }

            return thisPath.equals(otherPath);
        }
    }

}
