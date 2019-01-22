package com.tibco.xpd.n2.process.globalsignal.mapping;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Script Relevant Data Provider for Catch Global Signal Event Map to Signal
 * Script.
 * 
 * 
 * @author kthombar
 * @since Feb 12, 2015
 */
public class CdsCatchGlobalSignalScriptRelevantDataProvider extends
        AbstractCdsGlobalSignalScriptRelevantDataProvider {

    private static final String SIGNAL_PAYLOAD_DATA_PREFIX = "SIGNAL_"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
     * 
     * @return
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {

        List<IScriptRelevantData> relevantData =
                new ArrayList<IScriptRelevantData>();

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
                        data.add(payloadDataFieldCP.getPayloadDataField());
                    }
                }
                /*
                 * convert to script relevant data.
                 */
                List<IScriptRelevantData> scriptRelevantDataList =
                        convertToScriptRelevantData(data);

                for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
                    if (scriptRelevantData != null) {
                        /*
                         * add the "SIGNAL_" prefix.
                         */
                        scriptRelevantData.setName(SIGNAL_PAYLOAD_DATA_PREFIX
                                + scriptRelevantData.getName());
                        relevantData.add(scriptRelevantData);
                    }
                }
            }
        }
        return relevantData;
    }
}
