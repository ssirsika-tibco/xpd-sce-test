/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.n2.process.globalsignal.mapping.AbstractCdsSignalScriptRelevantDataProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Script Relevant Data Provider for Catch Local Signal Event Map to Signal
 * Script.
 *
 * @author sajain
 * @since Jul 17, 2019
 */
public class CdsCatchLocalSignalScriptRelevantDataProvider extends AbstractCdsSignalScriptRelevantDataProvider {

    /**
     * ID given to dummy parameter created on problem accessing payload.
     */
    public static final String CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID = "$$_CANT_ACCESS_PAYLOAD"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
     * 
     * @return
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {

        List<IScriptRelevantData> relevantData = new ArrayList<IScriptRelevantData>();

        Activity activity = getActivity();

        if (activity != null) {
            /*
             * Get all the payload data for the local signal event.
             */
            Collection<ActivityInterfaceData> referencedLocalSignalPayloadConceptPath = null;
            try {
                referencedLocalSignalPayloadConceptPath = EventObjectUtil.getSignalPayload(activity);
            } catch (GetSignalPayloadException e) {
                /*
                 * Create a dummy formal parameter with the payload access
                 * problem description in
                 */
                FormalParameter param = Xpdl2Factory.eINSTANCE.createFormalParameter();
                param.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID);

                param.setName(e.getMessage());
                param.setMode(ModeType.OUT_LITERAL);

                List<ProcessRelevantData> dummyProcessRelevantData = new ArrayList<ProcessRelevantData>();
                dummyProcessRelevantData.add(param);

                /*
                 * Convert to script relevant data.
                 */
                List<IScriptRelevantData> scriptRelevantDataList =
                        convertToScriptRelevantData(dummyProcessRelevantData);

                return scriptRelevantDataList;
            }

            if (null != referencedLocalSignalPayloadConceptPath && !referencedLocalSignalPayloadConceptPath.isEmpty()) {
                List<ProcessRelevantData> allProcessRelevantData = new ArrayList<ProcessRelevantData>();

                for (ActivityInterfaceData signalData : referencedLocalSignalPayloadConceptPath) {
                    ProcessRelevantData processData = signalData.getData();
                    allProcessRelevantData.add(processData);
                }


                /*
                 * Convert to script relevant data.
                 */
                List<IScriptRelevantData> scriptRelevantDataList = convertToScriptRelevantData(allProcessRelevantData);

                for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
                    if (scriptRelevantData != null) {
                        /*
                         * add the "SIGNAL_" prefix.
                         */
                        scriptRelevantData.setName(getSignalPayloadDataPrefix() + scriptRelevantData.getName());
                        relevantData.add(scriptRelevantData);
                    }
                }
            }
        }
        return relevantData;
    }
}
