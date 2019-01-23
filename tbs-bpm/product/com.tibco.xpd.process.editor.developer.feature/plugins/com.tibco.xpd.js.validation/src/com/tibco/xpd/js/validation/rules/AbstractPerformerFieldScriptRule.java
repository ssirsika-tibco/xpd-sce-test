/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.js.validation.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.js.validation.tools.PerformerScriptTool;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author bharge
 * 
 */
public abstract class AbstractPerformerFieldScriptRule extends
        AbstractProcessRelevantDataScriptRule {

    @Override
    public void validate(Package pckg) {
        List<ProcessRelevantData> performerScriptList =
                getPerformerScriptList(pckg);
        IValidationScope validationScope = getScope();
        for (ProcessRelevantData processRelevantData : performerScriptList) {
            /*
             * XPD-4936 :Performance improvement for scripts validations, use
             * getScriptTool() method, which returns a Tool initialised with the
             * cache from the validation scope.
             */
            ScriptTool tool =
                    getScriptTool(PerformerScriptTool.class,
                            processRelevantData);
            if (null != tool) {
                List<ErrorMessage> errorList =
                        tool.getErrorList(processRelevantData.getId(),
                                validationScope.getCurrentDestination());
                if (null == errorList) {
                    continue;
                }
                reportError(processRelevantData, errorList);
                List<ErrorMessage> warningList =
                        tool.getWarningList(processRelevantData.getId(),
                                validationScope.getCurrentDestination());
                if (warningList == null) {
                    continue;
                }
                reportWarning(processRelevantData, warningList);
            }
        }
    }

    protected List<ProcessRelevantData> getPerformerScriptList(Package pckg) {
        List<ProcessRelevantData> performerScriptList =
                new ArrayList<ProcessRelevantData>();
        if (null != pckg) {
            /**
             * getting all the data fields in a package
             * */
            EList<DataField> dataFields = pckg.getDataFields();
            for (DataField dataField : dataFields) {
                getDataFieldList(performerScriptList, dataField);
            }
            /**
             * getting all the process interfaces in a package; and then get all
             * the formal parameters in each process interface
             * */
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pckg);
            if (null != processInterfaces) {
                EList<ProcessInterface> processInterface =
                        processInterfaces.getProcessInterface();
                for (ProcessInterface processInterface2 : processInterface) {
                    EList<FormalParameter> formalParameters =
                            processInterface2.getFormalParameters();
                    for (FormalParameter formalParameter : formalParameters) {
                        getFormalParamList(performerScriptList, formalParameter);
                    }
                }
            }
            /**
             * getting all the processes in a package and then get all the data
             * fields in each process
             * */
            EList<Process> processes = pckg.getProcesses();
            for (Process process : processes) {
                EList<DataField> dataFields2 = process.getDataFields();
                for (DataField dataField : dataFields2) {
                    getDataFieldList(performerScriptList, dataField);
                }
                EList<FormalParameter> formalParameters =
                        process.getFormalParameters();
                for (FormalParameter formalParameter : formalParameters) {
                    getFormalParamList(performerScriptList, formalParameter);
                }
            }
        }
        return performerScriptList;
    }

    private void getDataFieldList(List<ProcessRelevantData> performerScriptList,
            DataField dataField) {
        if (dataField.getDataType() instanceof BasicType) {
            BasicType basicType = (BasicType) dataField.getDataType();
            if (basicType.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                performerScriptList.add((ProcessRelevantData) dataField);
            }
        }
    }

    private void getFormalParamList(
            List<ProcessRelevantData> performerScriptList,
            FormalParameter formalParameter) {
        if (formalParameter.getDataType() instanceof BasicType) {
            BasicType basicType = (BasicType) formalParameter.getDataType();
            if (basicType.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                performerScriptList.add((ProcessRelevantData) formalParameter);
            }
        }
    }

    @Override
    protected String getScriptContext() {
        return ProcessJsConsts.PERFORMER_SCRIPT;
    }

}
