/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchBpmnErrorMapperSection;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author mtorres
 */
public class SubProcessScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (isMappedScript()) {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getMappedOutputSubProcessScriptRelevantData();
            }
        } else {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getUnMappedOutputSubProcessScriptRelevantData();
            }
        }
    }

    private List<IScriptRelevantData> getMappedOutputSubProcessScriptRelevantData() {
        if (getActivity() != null) {
            List<FormalParameter> subFlowOutModeParams =
                    SubProcUtil.getSubProcessFormalParameters(getActivity(),
                            MappingDirection.OUT);
            List<IScriptRelevantData> scriptRelevantData =
                    convertToScriptRelevantData(new ArrayList<ProcessRelevantData>(
                            subFlowOutModeParams));
            if (scriptRelevantData == null) {
                scriptRelevantData = new ArrayList<IScriptRelevantData>();
            }
            scriptRelevantData.addAll(getAdditionalScriptData());
            return scriptRelevantData;
        }
        return Collections.emptyList();
    }

    private List<IScriptRelevantData> getUnMappedOutputSubProcessScriptRelevantData() {
        List<IScriptRelevantData> scriptRelevantData =
                new ArrayList<IScriptRelevantData>();
        Activity activity = getActivity();
        if (activity != null) {
            List<FormalParameter> subFlowOutModeParams =
                    SubProcUtil.getSubProcessFormalParameters(getActivity(),
                            MappingDirection.OUT);
            List<IScriptRelevantData> tempAdditionalData =
                    convertToScriptRelevantData(new ArrayList<ProcessRelevantData>(
                            subFlowOutModeParams));
            if (tempAdditionalData != null) {
                scriptRelevantData.addAll(tempAdditionalData);
            }

            // adding process data available for independent sub process task
            // (Main process)

            /*
             * Sid ACE-1317 Use the super-class' relevant data list (which will
             * now be the "data" object that wraps all the data in the main
             * process.
             */
            scriptRelevantData.addAll(super.getScriptRelevantDataList());

            scriptRelevantData.addAll(getAdditionalScriptData());
        }
        return scriptRelevantData;
    }

    protected List<IScriptRelevantData> getAdditionalScriptData() {
        List<IScriptRelevantData> additionalData = Collections.emptyList();

        if (getActivity() != null) {
            Activity catchErrorEvent = getActivity();

            //
            // Get the catch type or specific end error event we catch.
            //
            Object catchTypeOrEndErrorEvent =
                    CatchBpmnErrorMapperSection
                            .getCatchTypeOrSpecificErrorEndEvent(catchErrorEvent);

            if (catchTypeOrEndErrorEvent instanceof Activity) {
                // Catching specific end error event, so it's data should be
                // available in source param script.

                /*
                 * XPD-8183: User defined mapping Scripts for catch specific
                 * sub-process error event HAVE NEVER worked in runtime AMX BPM
                 * BPEL process. Therefore we should not allow the user to use
                 * them.
                 */
                if (!ProcessDestinationUtil
                        .isBPMDestinationSelected(catchErrorEvent.getProcess())) {
                    List<FormalParameter> thrownParams =
                            ProcessInterfaceUtil
                                    .getAssociatedFormalParameters((Activity) catchTypeOrEndErrorEvent);
                    List<FormalParameter> filteredListParams =
                            new ArrayList<FormalParameter>(thrownParams);
                    if (thrownParams != null && thrownParams.size() > 0) {
                        for (Iterator iterator = thrownParams.iterator(); iterator
                                .hasNext();) {
                            FormalParameter formalParameter =
                                    (FormalParameter) iterator.next();

                            // Only allow map from OUT/INOUT error thrower
                            // params.
                            if (ModeType.IN_LITERAL.equals(formalParameter
                                    .getMode())) {
                                // iterator.remove();
                                /*
                                 * ProcessInterfaceUtil.
                                 * getAssociatedFormalParameters () returns
                                 * unmodifiable List, iterator.remove() runs
                                 * into java.lang.UnsupportedOperationException
                                 */
                                filteredListParams.remove(formalParameter);
                            }
                        }

                        if (filteredListParams.size() > 0) {
                            additionalData =
                                    ProcessUtil
                                            .convertToScriptRelevantData(filteredListParams,
                                                    getProject());

                        }
                    }
                }
            }
        }
        return additionalData;
    }
}
