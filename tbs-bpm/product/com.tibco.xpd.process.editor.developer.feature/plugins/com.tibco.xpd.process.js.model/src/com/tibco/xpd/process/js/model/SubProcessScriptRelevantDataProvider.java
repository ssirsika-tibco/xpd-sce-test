/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
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
 * Sub-process script mapping data provider
 * 
 * @author mtorres
 */
public class SubProcessScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {


    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getAssociatedProcessRelevantData()
     *
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getAssociatedProcessRelevantData() {
        /*
         * Sid ACE-2565 user getAssociatedProcessRelevantData() instead of getScriptRelevantData() as this allows
         * sub-class to do some magic and wrap up the data in a data-field-descriptor object
         */
        if (isInputScript()) {
            /* For input scripts it's just the calling process data (which is what the superclass does. */
            return super.getAssociatedProcessRelevantData();
        } else {
            /* For output scripts it's the sub-process parameters. */
            return getOutputSubProcessRelevantData();
        }
    }

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
     *
     * @return
     */
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        List<IScriptRelevantData> data = new ArrayList<IScriptRelevantData>();

        data.addAll(super.getScriptRelevantDataList());

        /*
         * For output mapping script Add the additional data (Error_code et) for scripts special cases (the stuff that's
         * not wrapped in a "parameters" object
         */
        if (!isInputScript()) {
            data.addAll(getAdditionalScriptData());
        }

        return data;
    }
    
    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getDataWrapperObjectName()
     *
     * @return
     */
    @Override
    protected String getDataWrapperObjectName() {
        /*
         * The data object wrapper for input scripts is the standard "data" one. For output scripts it is "parameters".
         */
        if (isInputScript()) {
            return super.getDataWrapperObjectName();

        } else {
            return ReservedWords.SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME;
        }

    }

    private List<ProcessRelevantData> getOutputSubProcessRelevantData() {
        if (getActivity() != null) {
            List<ProcessRelevantData> data = new ArrayList<ProcessRelevantData>();
            data.addAll(SubProcUtil.getSubProcessFormalParameters(getActivity(), MappingDirection.OUT));
            
            /*
             * ACE-2565 converted to return processRelevantData and the additional script data is now added in
             * getScriptRelevantData()
             */

            return data;
        }
        return Collections.emptyList();
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
