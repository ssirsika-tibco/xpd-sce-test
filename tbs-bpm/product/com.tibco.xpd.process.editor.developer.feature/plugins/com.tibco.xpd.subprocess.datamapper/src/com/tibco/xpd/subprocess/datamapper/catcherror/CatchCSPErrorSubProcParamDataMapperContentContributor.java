/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper.catcherror;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.catcherror.datamapper.AbstractCatchErrorDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;

/**
 * Content Contributor for Catch error for SubProcess. This content contributor
 * takes care of process data (parameters, fields) coming from Sub Process
 * Implementation and show on the LHS side to mapper.
 * 
 * @author ssirsika
 * @since 04-Mar-2016
 */
public class CatchCSPErrorSubProcParamDataMapperContentContributor extends
        AbstractCatchErrorDataMapperContentContributor {

    /**
     * @see com.tibco.xpd.n2.pe.catcherror.datamapper.AbstractCatchErrorDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new SubProcessCatchErrorDataMapperContentProvider();
    }

    /**
     * @see com.tibco.xpd.n2.pe.catcherror.datamapper.AbstractCatchErrorDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "CatchSubProcessErrorProcessData.DataMapperContent"; //$NON-NLS-1$
    }

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

                    Activity activity = (Activity) mapperInput;

                    if (BpmnCatchableErrorUtil
                            .isCatchSubProcessErrorEvent(activity)) {

                        Activity act =
                                BpmnCatchableErrorUtil
                                        .getAttachedToTask((Activity) mapperInput);

                        if (act != null
                                && act.getImplementation() instanceof SubFlow) {

                            EObject subProc =
                                    TaskObjectUtil
                                            .getSubProcessOrInterface(act);

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
                }

                return super.getObjectForPath(objectPath, mapperInput);
            }
        };

    }

}
