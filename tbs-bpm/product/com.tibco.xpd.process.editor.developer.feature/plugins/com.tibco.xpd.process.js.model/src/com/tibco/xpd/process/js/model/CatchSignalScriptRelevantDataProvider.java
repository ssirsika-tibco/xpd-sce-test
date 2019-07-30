/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.process.js.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Script relevant data provider (used for content assist / validation etc) for
 * JavaScript mappings on catch signal.
 * 
 * @author aallway
 * @since 3 May 2012
 */
public class CatchSignalScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    public static final String SIGNAL_PAYLOAD_DATA_PREFIX = ReservedWords.BX_SIGNAL_PAYLOAD_PREFIX;

    public static final String WORKITEM_DATA_PREFIX = "WORKITEM_"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
     * 
     * @return
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {

        /*
         * The data for catch signal event is all the data in the scope of the
         * catch signal event
         */
        List<IScriptRelevantData> relevantData =
                new ArrayList<IScriptRelevantData>();

        relevantData.addAll(super.getScriptRelevantDataList());

        /*
         * PLUS all the data in the signal payload prefixed with "SIGNAL_"
         */
        Activity activity = getActivity();

        if (activity != null) {
            try {
                Collection<ActivityInterfaceData> signalPayload =
                        EventObjectUtil.getSignalPayload(activity);

                List<ProcessRelevantData> data =
                        new ArrayList<ProcessRelevantData>();
                for (ActivityInterfaceData payloadData : signalPayload) {
                    data.add(payloadData.getData());
                }

                List<IScriptRelevantData> scriptRelevantDataList =
                        convertToScriptRelevantData(data);

                for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
                    if (scriptRelevantData != null) {
                        scriptRelevantData.setName(SIGNAL_PAYLOAD_DATA_PREFIX
                                + scriptRelevantData.getName());
                        JScriptUtils.setReadOnly(scriptRelevantData, true);
                        relevantData.add(scriptRelevantData);
                    }
                }

            } catch (GetSignalPayloadException e) {
                /*
                 * Nothing we can do about 'no thrower' or 'inconsistent thrower
                 * payloads' so just return nothing.
                 */
            }
            /*
             * XPD-3813- In catch signal script data mapping, there should not
             * be access to the work item (right hand side) data in the script -
             * i.e. do not allow WORKITEM_xxxx in content assist and validation.
             */

        }

        return relevantData;
    }
}
