/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper.contentcontributors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ActivityInterfaceDataMapperConceptPathProvider;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Correlation content contributor for Global Signal Catch Target.
 * 
 * Sid ACE-2982 - renamed to something less generic as is very specific to Catch Global
 * 
 * @author sajain
 * @since Apr 29, 2016
 */
public class CatchGlobalSignalCorrelationDataContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * @param direction
     */
    public CatchGlobalSignalCorrelationDataContentContributor() {
    }


    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return SignalDataMapperConstants.GS_CATCH_CORRELATION_DATAMAPPER_CONTENT_CONTRIBUTOR_ID;
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        /*
         * Sid ACE-2982 Catch global signal doesn't use the specific correlation data association element that incoming
         * request activities do (even in BPM 4.3.0) so the correlation data content needs to be as the normal data
         * associations (but just the correlation ones.
         * 
         * So it's like standard associated data provider except we add content into a Correlation Data folder
         */
        return new ActivityInterfaceDataMapperConceptPathProvider() {
            private Activity inputActivity = null;

            private CorrelationDataFolder correlationDataFolder = new CorrelationDataFolder(null);
            
            /**
             * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider#getElements(java.lang.Object)
             *
             * @param inputElement
             * @return
             */
            @Override
            public Object[] getElements(Object inputElement) {
                if (inputElement instanceof Activity) {
                    inputActivity = (Activity) inputElement;

                    return new Object[] { correlationDataFolder };
                }
                return new Object[0];
            }
            
            /**
             * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider#hasChildren(java.lang.Object)
             *
             * @param element
             * @return
             */
            @Override
            public boolean hasChildren(Object element) {
                if (element instanceof CorrelationDataFolder) {
                    Object[] children = getChildren(element);
                    return children != null && children.length > 0;
                }
                return super.hasChildren(element);
            }

            /**
             * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider#getParent(java.lang.Object)
             *
             * @param element
             * @return
             */
            @Override
            public Object getParent(Object element) {
                if (element instanceof CorrelationDataFolder) {
                    return null;
                } else if (element instanceof ConceptPath && ((ConceptPath) element).getParent() == null) {
                    return correlationDataFolder;
                }
                return super.getParent(element);
            }

            /**
             * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider#getChildren(java.lang.Object)
             *
             * @param parentElement
             * @return
             */
            @Override
            public Object[] getChildren(Object parentElement) {
                if (parentElement instanceof CorrelationDataFolder) {
                    if (inputActivity != null) {
                        List<ConceptPath> associatedCorrelationData = new ArrayList<ConceptPath>();
                        
                        Object[] children = super.getElements(inputActivity);
                         
                         if (children != null) {
                             for (Object child : children) {
                                if (child instanceof ConceptPath) {
                                    ConceptPath cp = (ConceptPath) child;
                                    
                                    if (cp.getItem() instanceof DataField) {
                                        DataField dataField = (DataField)cp.getItem();
                                        
                                        if (dataField.isCorrelation()) {
                                            associatedCorrelationData.add(cp);
                                        }
                                    }
                                }
                            }
                         }
                         
                         return associatedCorrelationData.toArray();
                    }
                    
                    return new Object[0];
                }
                
                return super.getChildren(parentElement);
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