/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper.validation;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;
import com.tibco.xpd.worklistfacade.resource.mapper.WorkItemAttributeConceptPath;

/**
 * Mapping Rule Info provider for Work Item Attribute ..
 * 
 * @author aprasad
 * @since 14-Nov-2013
 */
public class WorkItemAttributeMappingRuleInfoProvider extends
        MappingRuleContentInfoProviderBase {

    /**
     * 
     * 
     * @param contentProvider
     */
    public WorkItemAttributeMappingRuleInfoProvider(
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
         * Note: this path must be identical to the path returned by the content
         * provider data, else problem markers will not be raised at appropriate
         * places.
         */
        if (objectFromMappingOrContent instanceof WorkItemAttributeConceptPath) {
            return ((WorkItemAttributeConceptPath) objectFromMappingOrContent)
                    .getPath();

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase#getObjectPathDescription(java.lang.Object)
     * 
     * @param objectFromMappingOrContent
     * @return
     */
    @Override
    public String getObjectPathDescription(Object objectFromMappingOrContent) {
        if (objectFromMappingOrContent instanceof WorkItemAttributeConceptPath) {

            WorkItemAttributeConceptPath workItemAliasItemPath =
                    (WorkItemAttributeConceptPath) objectFromMappingOrContent;

            return (workItemAliasItemPath.getPhysicalAttribute() != null) ? workItemAliasItemPath
                    .getPhysicalAttribute().getName() : null;

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
        // All Mappings are optional.
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

        // no maximum instances specified
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

        return false;
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
