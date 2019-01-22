package com.tibco.xpd.n2.process.globalsignal.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.n2.cds.script.CdsFactoriesJavaScriptRelevantDataProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * BOM Java Script Relavent Data provider for Payload Data in Catch Global
 * Signal Events.
 * 
 * 
 * @author kthombar
 * @since Feb 26, 2015
 */
public class PayloadDataBomFactoriesJavaScriptRelevantDataProvider extends
        CdsFactoriesJavaScriptRelevantDataProvider {

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getAssociatedProcessRelevantData()
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<ProcessRelevantData> getAssociatedProcessRelevantData() {

        Activity activity = getActivity();

        if (activity != null) {
            PayloadDataMapperContentProvider payloadContentProvider =
                    new PayloadDataMapperContentProvider();

            /*
             * get all the payload data for the global signal event.
             */
            PayloadConceptPath[] referencedGlobalSignalPayloadConceptPath =
                    payloadContentProvider
                            .getReferencedGlobalSignalPayloadConceptPath(activity);

            if (referencedGlobalSignalPayloadConceptPath != null
                    && referencedGlobalSignalPayloadConceptPath.length != 0) {

                List<ProcessRelevantData> data =
                        new ArrayList<ProcessRelevantData>();

                for (PayloadConceptPath payloadDataFieldCP : referencedGlobalSignalPayloadConceptPath) {
                    if (payloadDataFieldCP.getPayloadDataField() != null) {
                        /*
                         * Add all the payload data as the process relavent
                         * data.
                         */
                        data.add(payloadDataFieldCP.getPayloadDataField());
                    }
                }
                return data;
            }
        }
        return Collections.EMPTY_LIST;
    }
}
