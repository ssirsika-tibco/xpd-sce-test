/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadConceptPath;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProviderBase;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping info provider for Global Signal Payload Data.
 * 
 * @author sajain
 * @since Apr 28, 2016
 */
public class PayloadDataMapperInfoProvider extends
        ProcessDataMapperInfoProvider {

    /**
     * @param contentProvider
     */
    public PayloadDataMapperInfoProvider(
            ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
        super(contentProvider, labelProvider);

    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider#createBaseInfoProvider(org.eclipse.jface.viewers.ITreeContentProvider)
     * 
     * @param contentProvider
     * @return
     */
    @Override
    protected MappingRuleContentInfoProviderBase createBaseInfoProvider(
            ITreeContentProvider contentProvider) {
        return new PayloadDataMappingRuleInfoProvider(contentProvider);
    }

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

        if (mapperInput instanceof Activity && null != objectPath
                && !objectPath.isEmpty()) {

            Activity activity = (Activity) mapperInput;

            GlobalSignal referencedGlobalSignal =
                    GlobalSignalUtil.getReferencedGlobalSignal(activity);

            if (referencedGlobalSignal != null) {
                EList<PayloadDataField> payloadDataFields =
                        referencedGlobalSignal.getPayloadDataFields();

                for (PayloadDataField payloadDataField : payloadDataFields) {
                    /*
                     * Wrap the payload in payload concept path.
                     */
                    PayloadConceptPath pcp =
                            new PayloadConceptPath(payloadDataField);

                    if (objectPath.equals(pcp.getPath())) {
                        return pcp;
                    }
                }
            }
        }
        return null;
    }
}
