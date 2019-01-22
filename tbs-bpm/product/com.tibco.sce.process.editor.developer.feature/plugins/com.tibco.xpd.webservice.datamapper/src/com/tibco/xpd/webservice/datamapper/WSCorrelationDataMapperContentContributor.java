/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.processeditor.xpdl2.properties.IncomingRequestActivityTargetDataProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Content contributor for Correlation content for WS activities' InputToProcess
 * context.
 * 
 * @author sajain
 * @since Feb 9, 2016
 */
public class WSCorrelationDataMapperContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * @param direction
     */
    public WSCorrelationDataMapperContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return WebServiceDataMapperConstants.CORRELATION_DATAMAPPER_CONTENT_CONTRIBUTOR_ID;
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {

        return new IncomingRequestActivityTargetDataProvider(
                MappingDirection.OUT) {

            /**
             * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
             *      .lang.Object)
             */
            @Override
            public Object[] getElements(Object inputElement) {

                List<Object> elements = new ArrayList<Object>();

                if (inputElement instanceof Activity) {

                    Activity activity = (Activity) inputElement;

                    setActivity(activity);

                    if (activity != null) {
                        Process process = activity.getProcess();
                        if (process != null) {
                            if (ProcessInterfaceUtil
                                    .getAssociatedCorrelationDataForActivity(activity)
                                    .size() != 0) {

                                CorrelationDataFolder container =
                                        new CorrelationDataFolder(process);

                                elements.add(container);
                            }
                        }
                    }
                }
                return elements.toArray();
            }

        };
    }

    /**
     * 
     * @param contentProvider
     * @param labelProvider
     * @return
     */
    @Override
    protected ProcessDataMapperInfoProvider createProcessDataInfoProvider(
            ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
        return new ProcessDataMapperInfoProvider(contentProvider, labelProvider) {

            /**
             * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider#setBaseInfoProvider(org.eclipse.jface.viewers.ITreeContentProvider)
             * 
             * @param contentProvider
             */
            @Override
            protected ProcessDataMappingRuleInfoProvider createBaseInfoProvider(
                    ITreeContentProvider contentProvider) {

                return new ProcessDataMappingRuleInfoProvider(contentProvider) {

                    /**
                     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#getMinimumInstances(java.lang.Object)
                     * 
                     * @param objectInTree
                     * @return
                     */
                    @Override
                    public int getMinimumInstances(Object objectInTree) {

                        if (objectInTree != null) {
                            if (objectInTree instanceof ConceptPath) {
                                ConceptPath conceptPath =
                                        (ConceptPath) objectInTree;

                                Object item = conceptPath.getItem();
                                if (item instanceof ProcessRelevantData) {
                                    ProcessRelevantData prd =
                                            (ProcessRelevantData) item;

                                    if (prd instanceof DataField) {
                                        DataField df = (DataField) prd;

                                        if (df.isCorrelation()) {
                                            return 1;
                                        }
                                    }
                                }

                            }
                        }

                        return super.getMinimumInstances(objectInTree);
                    }

                    /**
                     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#isReadOnlyTarget(java.lang.Object)
                     * 
                     * @param objectInTree
                     * @return
                     */
                    @Override
                    public boolean isReadOnlyTarget(Object objectInTree) {
                        /*
                         * This will make sure that CorrelationFolder will not
                         * be shown in grayed color on RHS of the mapper
                         */
                        if (objectInTree instanceof CorrelationDataFolder) {
                            return false;
                        }
                        return super.isReadOnlyTarget(objectInTree);
                    }

                    /**
                     * @see com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider#shouldIgnoreReadOnlyTarget(com.tibco.xpd.xpdl2.ProcessRelevantData)
                     * 
                     * @param data
                     * @return
                     */
                    @Override
                    protected boolean shouldIgnoreReadOnlyTarget(
                            ProcessRelevantData data) {
                        /**
                         * Correlation data target should not be treated as
                         * Read-Only
                         */
                        if (data instanceof DataField) {
                            return ((DataField) data).isCorrelation();
                        }
                        return super.shouldIgnoreReadOnlyTarget(data);
                    }
                };
            }

        };

    }
}