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
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Content contributor for Sub-Process Data mapper's 'SubProcess to Process'
 * mapping use case.
 * 
 * @author sajain
 * @since Oct 30, 2015
 */
public class SubProcessDataMapperOutContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * @param direction
     */
    public SubProcessDataMapperOutContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "SubProcessToProcess.DataMapperContent"; //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new SubProcessDataMapperContentProvider(MappingDirection.OUT);
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

                            if (Xpdl2ModelUtil.REPLY_IMMEDIATE_PROCESS_ID_PARAMETER_NAME
                                    .equals(objectPath)) {

                                return new ConceptPath(
                                        StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER,
                                        null);

                            } else {

                                return ConceptUtil
                                        .resolveConceptPath((Process) subProc,
                                                objectPath,
                                                true);
                            }

                        } else if (subProc instanceof ProcessInterface) {
                            return ConceptUtil
                                    .resolveConceptPath((ProcessInterface) subProc,
                                            objectPath);
                        }

                    }
                }

                return super.getObjectForPath(objectPath, mapperInput);
            }
        };

    }
}