/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
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
     * Sid ACE-4024 only set after initial getElements() from contentProvider. Map of concept path to mandatory flag for
     * sub-process start event associated parameters.
     */
    private Map<ConceptPath, Boolean> paramCPToMandatoryMap;

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
        return ProcessEditorConstants.DATAMAPPER_PROCESS_TO_SUBPROCESS_CONTRIBUTOR_ID;
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new SubProcessDataMapperContentProvider(MappingDirection.IN) {
            /**
             * Sid ACE-4024 Preserve the map of concept path to 'is mandatory' once when the validation framework asks
             * us for the top level elements at the start of mapping validation.
             * 
             * This can then be used by our info provider's 'getMinimumInstances()' method to determine if a sub-process
             * parameter is mandatory or not **depending on whether it is implcitily mandatory OR has bee explicitly
             * promoted to mandatory in the sub-process start-event's data associations.
             * 
             * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider#getElements(java.lang.Object)
             *
             * @param inputElement
             * @return
             */
            @Override
            public Object[] getElements(Object inputElement) {
                paramCPToMandatoryMap = null;

                if (inputElement instanceof Activity) {
                    paramCPToMandatoryMap =
                            SubProcUtil.getSubProcessParametersAndMandatory(
                                    TaskObjectUtil.getSubProcessOrInterface((Activity) inputElement),
                                    MappingDirection.IN);
                }

                return super.getElements(inputElement);
            }
        };

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

                                /*
                                 * Sid ACE-4024 We need to use the overriding explicit data association on the start
                                 * event if there is one (else if param is optional but associated as mandatory then we
                                 * must treat it as mandatory.
                                 * 
                                 * Only interested in top level parameter elements though, otherwise we should fall thru
                                 * and use the default for custom type child content
                                 */
                                if (conceptPath.getParent() == null && paramCPToMandatoryMap != null) {
                                    Boolean mandatory = paramCPToMandatoryMap.get(conceptPath);

                                    if (mandatory != null && mandatory) {
                                        return 1;
                                    }
                                    return 0;
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