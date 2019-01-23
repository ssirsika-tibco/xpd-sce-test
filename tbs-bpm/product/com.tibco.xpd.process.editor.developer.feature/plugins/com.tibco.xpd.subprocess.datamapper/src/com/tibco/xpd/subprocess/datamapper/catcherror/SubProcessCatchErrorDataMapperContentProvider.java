/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper.catcherror;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchBpmnErrorMapperSection;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;

/**
 * Content contributor for Sub-Process Catch Error Data mapper's mappings.
 * 
 * @author sajain
 * @since Oct 28, 2015
 */
public class SubProcessCatchErrorDataMapperContentProvider extends
        ProcessDataMapperConceptPathProvider {

    /**
     * Content contributor for Sub-Process Data mapper's 'SubProcess to Process'
     * mapping use case.
     * 
     * @param direction
     */
    public SubProcessCatchErrorDataMapperContentProvider() {
    }

    /**
     * @see com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperConceptPathProvider#getInScopeProcessData(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getInScopeProcessData(
            Activity activityObj) {

        if (activityObj != null) {

            Object errorThrower = null;

            Object catchTypeOrEndError =
                    CatchBpmnErrorMapperSection
                            .getCatchTypeOrSpecificErrorEndEvent(activityObj);

            if (activityObj.getEvent() instanceof IntermediateEvent) {

                ResultError resultError =
                        ((IntermediateEvent) activityObj.getEvent())
                                .getResultError();

                String errorCode = resultError.getErrorCode();

                if (errorCode != null && !errorCode.isEmpty()) {

                    if (catchTypeOrEndError instanceof Activity
                            || catchTypeOrEndError instanceof InterfaceMethod) {

                        errorThrower = catchTypeOrEndError;

                        List<ProcessRelevantData> errorThrowerActivityParameters =
                                new ArrayList<ProcessRelevantData>();

                        if (errorThrower instanceof Activity) {

                            List<FormalParameter> thrownParams =
                                    ProcessInterfaceUtil
                                            .getAssociatedFormalParameters((Activity) errorThrower);

                            if (thrownParams != null && thrownParams.size() > 0) {
                                for (FormalParameter eachThrownParam : thrownParams) {

                                    // Only allow map from OUT/INOUT error
                                    // thrower
                                    // params.
                                    if (!ModeType.IN_LITERAL
                                            .equals(eachThrownParam.getMode())) {
                                        errorThrowerActivityParameters
                                                .add(eachThrownParam);
                                    }
                                }
                            }

                        } else if (errorThrower instanceof InterfaceMethod) {
                            InterfaceMethod method =
                                    (InterfaceMethod) errorThrower;
                            ErrorMethod errorMethod = null;

                            for (ErrorMethod em : method.getErrorMethods()) {
                                if (errorCode.equals(em.getErrorCode())) {
                                    errorMethod = em;
                                }
                            }

                            if (errorMethod != null) {

                                List<FormalParameter> thrownParams =
                                        ProcessInterfaceUtil
                                                .getErrorMethodAssociatedFormalParameters(errorMethod);

                                if (thrownParams != null
                                        && thrownParams.size() > 0) {
                                    for (FormalParameter tp : thrownParams) {

                                        // Only allow map from OUT/INOUT error
                                        // thrower params.
                                        if (!ModeType.IN_LITERAL.equals(tp
                                                .getMode())) {
                                            errorThrowerActivityParameters
                                                    .add(tp);
                                        }
                                    }
                                }
                            }
                        }

                        return errorThrowerActivityParameters;
                    }
                }
            }
        }

        return new ArrayList<ProcessRelevantData>();
    }

}
