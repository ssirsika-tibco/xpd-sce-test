/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Script Relevant Data Provider for Throw Global Signal Event Map to Signal
 * Script.
 * 
 * @author kthombar
 * @since Feb 4, 2015
 */
public class CdsThrowGlobalSignalScriptRelevantDataProvider extends
        AbstractCdsGlobalSignalScriptRelevantDataProvider {

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getAssociatedProcessRelevantData()
     * 
     *      Sid ACE-1317 - cannot allow getScriptRelevantData() to be overridden
     *      anymore as it wraps the data up in a data Object and we need to do
     *      that here. So instead we supply the list of appropriate asociated
     *      process data as was intended.
     * 
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getAssociatedProcessRelevantData() {
        Activity activity = getActivity();

        if (activity != null) {
            /*
             * get the activity interface data.
             */
            Collection<ActivityInterfaceData> signalPayload =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(activity);

            List<ProcessRelevantData> data =
                    new ArrayList<ProcessRelevantData>();

            for (ActivityInterfaceData payloadData : signalPayload) {
                data.add(payloadData.getData());
            }

            return data;
        }

        return Collections.emptyList();
    }
}
