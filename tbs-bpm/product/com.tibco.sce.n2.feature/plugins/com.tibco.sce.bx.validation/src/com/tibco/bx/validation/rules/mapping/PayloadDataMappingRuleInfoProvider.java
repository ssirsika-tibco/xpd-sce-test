/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Mapping Info provider for Payload Data.
 * 
 * @author kthombar
 * @since Feb 14, 2015
 */
public class PayloadDataMappingRuleInfoProvider extends
        MappingRuleContentInfoProvider {

    /**
     * @param contentProvider
     */
    protected PayloadDataMappingRuleInfoProvider(
            ITreeContentProvider contentProvider) {
        super(contentProvider);

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getObjectPath(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public String getObjectPath(Object objectFromMappingOrContent) {
        /*
         * return the same object path what we have returned from the mapper
         * section.
         */
        String path = null;
        if (objectFromMappingOrContent instanceof PayloadConceptPath) {
            path = ((PayloadConceptPath) objectFromMappingOrContent).getPath();

        } else if (objectFromMappingOrContent instanceof ScriptInformation) {
            path = ((ScriptInformation) objectFromMappingOrContent).getName();
        }
        return path;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getObjectPathDescription(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public String getObjectPathDescription(Object objectFromMappingOrContent) {
        if (objectFromMappingOrContent instanceof PayloadConceptPath) {
            PayloadDataField payloadDataField =
                    ((PayloadConceptPath) objectFromMappingOrContent)
                            .getPayloadDataField();
            if (payloadDataField != null) {
                /*
                 * return the payload name as this will be user readable name.
                 */
                return payloadDataField.getName();
            }
        } else if (objectFromMappingOrContent instanceof ScriptInformation) {
            return ((ScriptInformation) objectFromMappingOrContent).getName();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#isReadOnlyTarget(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    public boolean isReadOnlyTarget(Object targetObjectInTree) {

        if (targetObjectInTree instanceof PayloadConceptPath) {

            PayloadDataField payloadDataField =
                    ((PayloadConceptPath) targetObjectInTree)
                            .getPayloadDataField();
            if (payloadDataField != null) {

                return payloadDataField.isReadOnly();
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#isMultiInstance(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isMultiInstance(Object objectInTree) {

        if (objectInTree instanceof PayloadConceptPath) {

            return ((PayloadConceptPath) objectInTree).isArray();

        } else if (objectInTree instanceof IScriptRelevantData) {
            return ((IScriptRelevantData) objectInTree).isArray();
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getMinimumInstances(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public int getMinimumInstances(Object objectInTree) {
        /*
         * Mapping to correlation payload and non-correlation payloads marked as
         * mandatory are mandatory
         */
        if (objectInTree instanceof PayloadConceptPath) {
            PayloadConceptPath payloadCP = (PayloadConceptPath) objectInTree;
            if (payloadCP.isMandatoryPayloadData()
                    || payloadCP.isCorrelationPayloadData()) {
                return 1;
            }
        } else if (objectInTree instanceof CorrelationDataFolder) {
            /*
             * We want to map the descendants of Correlation Data Folder.
             */
            return 1;
        }
        return 0;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getMaximumInstances(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public int getMaximumInstances(Object objectInTree) {
        // no max instances specified.
        return -1;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getInstanceIndex(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public int getInstanceIndex(Object objectInTree) {
        // no Instance index specified
        return -1;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#isSimpleTypeContent(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isSimpleTypeContent(Object objectInTree) {

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#isNullSimpleContentAllowed(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isNullSimpleContentAllowed(Object objectInTree) {

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#isArtificialObject(java.lang.Object)
     * 
     * @param objectInTree
     * @return
     */
    @Override
    public boolean isArtificialObject(Object objectInTree) {
        if (objectInTree instanceof PayloadConceptPath
                || objectInTree instanceof ScriptInformation) {

            return false;
        }
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#isChoiceObject(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public boolean isChoiceObject(Object objectFromMappingOrContent) {

        return false;
    }
}
