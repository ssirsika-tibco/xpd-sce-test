/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper.catcherror;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.catcherror.datamapper.AbstractCatchErrorDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

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

                        /*
                         * Sid ACE-3395 for catch sub-process error we need the process that actually throws the error.
                         * This is NOT necessarily the process of the sub-process task we're attached to. So changed to
                         * get the error-thrower (which in the case of catch sub-proc error will be the throw error
                         * event in the sub-proc or process-interface
                         */
                        Object errorThrower = BpmnCatchableErrorUtil.getErrorThrower((Activity) mapperInput);

                        if (errorThrower instanceof EObject) {
                            EObject subProc = null;
                            
                            if (errorThrower instanceof Activity) {
                                subProc = ((Activity) errorThrower).getProcess();
                                
                            } else if (errorThrower instanceof InterfaceMethod) {
                                subProc =
                                        ProcessInterfaceUtil.getProcessInterface((InterfaceMethod) errorThrower);
                            }
                            
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
