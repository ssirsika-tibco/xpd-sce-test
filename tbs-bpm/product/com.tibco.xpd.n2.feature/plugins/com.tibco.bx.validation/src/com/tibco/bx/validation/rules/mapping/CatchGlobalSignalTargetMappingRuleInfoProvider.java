/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Mapping rule info provider for Catch Global Signals. Extending
 * {@link ProcessDataMappingRuleInfoProvider} as most of the good stuff is done
 * in that class.
 * 
 * @author kthombar
 * @since Feb 16, 2015
 */
public class CatchGlobalSignalTargetMappingRuleInfoProvider extends
        ProcessDataMappingRuleInfoProvider {

    /**
     * @param contentProvider
     */
    public CatchGlobalSignalTargetMappingRuleInfoProvider(
            ITreeContentProvider contentProvider) {
        super(contentProvider);

    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#shouldIgnoreReadOnlyTarget(com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param data
     * @return
     */
    @Override
    protected boolean shouldIgnoreReadOnlyTarget(ProcessRelevantData data) {
        if (data instanceof DataField) {
            return ((DataField) data).isCorrelation();
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

        int minimumInstances = super.getMinimumInstances(objectInTree);

        if (minimumInstances != 1) {
            /*
             * Mapping to correlation data and required params is mandatory.
             */
            if (objectInTree instanceof ConceptPath) {
                Object item = ((ConceptPath) objectInTree).getItem();
                if (item instanceof DataField) {
                    if (((DataField) item).isCorrelation()) {
                        minimumInstances = 1;
                    }
                }
            }
        }
        return minimumInstances;
    }
}
