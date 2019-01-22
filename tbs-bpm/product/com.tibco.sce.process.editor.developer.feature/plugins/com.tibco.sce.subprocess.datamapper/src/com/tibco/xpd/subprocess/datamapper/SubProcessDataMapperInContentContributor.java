/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;

/**
 * Content contributor for Sub-Process Data mapper's 'Process to SubProcess'
 * mapping use case.
 * 
 * @author sajain
 * @since Oct 30, 2015
 */
public class SubProcessDataMapperInContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * @param direction
     */
    public SubProcessDataMapperInContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "ProcessToSubProcess.DataMapperContent"; //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new SubProcessDataMapperContentProvider(MappingDirection.IN);
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createProcessDataInfoProvider(org.eclipse.jface.viewers.ITreeContentProvider,
     *      org.eclipse.jface.viewers.ILabelProvider)
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
             * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider#getObjectForPath(java.lang.String,
             *      java.lang.Object)
             * 
             * @param objectPath
             * @param mapperInput
             * @return
             */
            @Override
            public Object getObjectForPath(String objectPath, Object mapperInput) {

                if (mapperInput instanceof Activity) {
                    Activity act = (Activity) mapperInput;

                    if (act.getImplementation() instanceof SubFlow) {

                        EObject subProc =
                                TaskObjectUtil.getSubProcessOrInterface(act);

                        if (subProc instanceof Process) {
                            return ConceptUtil
                                    .resolveConceptPath((Process) subProc,
                                            objectPath,
                                            true);
                        } else if (subProc instanceof ProcessInterface) {
                            return ConceptUtil
                                    .resolveConceptPath((ProcessInterface) subProc,
                                            objectPath);
                        }

                    }
                }

                return super.getObjectForPath(objectPath, mapperInput);
            }

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

                                    if (prd instanceof FormalParameter) {
                                        FormalParameter fp =
                                                (FormalParameter) prd;

                                        if (fp.isRequired()) {
                                            return 1;
                                        }
                                    }
                                }

                            }
                        }

                        return super.getMinimumInstances(objectInTree);
                    }

                };
            }

        };

    }
}