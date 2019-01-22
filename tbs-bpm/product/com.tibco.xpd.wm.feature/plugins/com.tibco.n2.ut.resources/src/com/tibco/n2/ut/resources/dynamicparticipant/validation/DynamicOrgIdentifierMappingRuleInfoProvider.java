/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.validation;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.n2.ut.resources.dynamicparticipant.mapper.DynamicOrgIdentiferPath;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;

/**
 * Info provider for Process data to Dynamic Org identifier mappings.
 * 
 * @author kthombar
 * @since 09-Oct-2013
 */
public class DynamicOrgIdentifierMappingRuleInfoProvider extends
        MappingRuleContentInfoProviderBase {

    /**
     * Info provider for Process data to Dynamic Org identifier mappings.
     * 
     * @param contentProvider
     */
    public DynamicOrgIdentifierMappingRuleInfoProvider(
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
        if (objectFromMappingOrContent instanceof DynamicOrgIdentiferPath) {
            return ((DynamicOrgIdentiferPath) objectFromMappingOrContent)
                    .getPath();
        } else if (objectFromMappingOrContent instanceof Organization) {
            return ((Organization) objectFromMappingOrContent).getId();

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
        if (objectFromMappingOrContent instanceof DynamicOrgIdentiferPath) {
            return ((DynamicOrgIdentiferPath) objectFromMappingOrContent)
                    .getName();
        } else if (objectFromMappingOrContent instanceof Organization) {
            return ((Organization) objectFromMappingOrContent).getName();
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
        /*
         * Targets are mandatory and hence mapping is required. So readonly ==
         * false.
         */
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
        /*
         * All the target Dynamic org identifiers are mandatory
         */
        if (objectInTree instanceof String) {
            /*
             * if objectInTree is a string that implies that no Dynamic
             * participants are present, hence mapping not required.
             */
            return 0;
        }

        return 1;
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
