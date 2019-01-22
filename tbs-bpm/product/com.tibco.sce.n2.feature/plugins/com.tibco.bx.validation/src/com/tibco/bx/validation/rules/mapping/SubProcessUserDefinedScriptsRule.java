/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author mtorres
 * 
 */
public class SubProcessUserDefinedScriptsRule extends ProcessValidationRule {

    /**
     * 
     */
    private static final String USERDEFINEDSCRIPT_MAP_FROM_SUBPROC_NOT_ALLOWED =
            "bx.userDefinedScriptsMapFromSubprocNotAllowed"; //$NON-NLS-1$

    private static final String JSCRIPT_GRAMMAR = "JavaScript"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        List<Activity> subProcTasks =
                ProcessScriptUtil.getSubProcTasks(process);
        for (Activity subProcess : subProcTasks) {
            List<DataMapping> outputMapping =
                    ProcessScriptUtil
                            .getScriptDataOutputMappingForSubProcWithScriptType(subProcess,
                                    JSCRIPT_GRAMMAR);
            if (outputMapping != null && !outputMapping.isEmpty()) {
                for (DataMapping dataMapping : outputMapping) {
                    String key =
                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
                    String target = DataMappingUtil.getTarget(dataMapping);
                    addIssue(USERDEFINEDSCRIPT_MAP_FROM_SUBPROC_NOT_ALLOWED,
                            dataMapping,
                            new ArrayList<String>(),
                            Collections.singletonMap(key, target));
                }
            }
            List<ScriptInformation> scriptInformations =
                    ProcessScriptUtil
                            .getScriptInformationTasksWithScriptType(subProcess,
                                    JSCRIPT_GRAMMAR,
                                    DirectionType.OUT_LITERAL.getLiteral());
            if (scriptInformations != null && !scriptInformations.isEmpty()) {
                for (ScriptInformation scriptInformation : scriptInformations) {
                    addIssue(USERDEFINEDSCRIPT_MAP_FROM_SUBPROC_NOT_ALLOWED,
                            scriptInformation);
                }
            }
        }
    }
}
