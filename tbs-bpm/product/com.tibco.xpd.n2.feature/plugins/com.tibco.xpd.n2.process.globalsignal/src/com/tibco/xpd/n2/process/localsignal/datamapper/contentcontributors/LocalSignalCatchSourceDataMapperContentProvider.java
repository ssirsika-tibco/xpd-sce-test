/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.contentcontributors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Content provider implementation for Local Signal CATCH Data Mapper (source).
 *
 * @author sajain
 * @since Jul 16, 2019
 */
public class LocalSignalCatchSourceDataMapperContentProvider extends ProcessDataMapperConceptPathProvider {

    /**
     * ID given to dummy parameter created on problem accessing payload.
     */
    public static final String CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID = "$$_CANT_ACCESS_PAYLOAD"; //$NON-NLS-1$


    /**
     * Content provider implementation for Local Signal CATCH Data Mapper
     * (source).
     * 
     * @param direction
     */
    public LocalSignalCatchSourceDataMapperContentProvider() {
    }

    /**
     * @see com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperConceptPathProvider#getInScopeProcessData(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getInScopeProcessData(Activity activity) {
        /*
         * The input element will be the catch signal event, so we need to
         * redirect this to the throw signal event to pick up its associated
         * data.
         */
        if (activity.getProcess() != null) {

            if (activity.getEvent() != null
                    && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

                try {
                    Collection<ActivityInterfaceData> signalPayload = EventObjectUtil.getSignalPayload(activity);

                    if (!signalPayload.isEmpty()) {
                        List<ProcessRelevantData> inScopeProcessData = new ArrayList<ProcessRelevantData>();

                        for (ActivityInterfaceData signalData : signalPayload) {
                            ProcessRelevantData processData = signalData.getData();
                            inScopeProcessData.add(processData);
                        }

                        return inScopeProcessData;

                    } else {
                        /*
                         * Create a dummy formal parameter for
                         * "no data associated with signal"
                         */
                        FormalParameter param = Xpdl2Factory.eINSTANCE.createFormalParameter();
                        param.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID);
                        param.setName(
                                Messages.CatchSignalMapperSourceContentProvider_NoDataAssociatedWithThrowSignal_message);
                        param.setMode(ModeType.OUT_LITERAL);

                        List<ProcessRelevantData> dummyParamList = new ArrayList<ProcessRelevantData>();
                        dummyParamList.add(param);

                        return dummyParamList;
                    }

                } catch (GetSignalPayloadException e) {
                    /*
                     * Create a dummy formal parameter with the payload access
                     * problem description in.
                     */
                    FormalParameter param = Xpdl2Factory.eINSTANCE.createFormalParameter();
                    param.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID);

                    param.setName(e.getMessage());
                    param.setMode(ModeType.OUT_LITERAL);

                    List<ProcessRelevantData> dummyParamList = new ArrayList<ProcessRelevantData>();
                    dummyParamList.add(param);

                    return dummyParamList;
                }

            }
        }
        return new ArrayList<ProcessRelevantData>();
    }
}