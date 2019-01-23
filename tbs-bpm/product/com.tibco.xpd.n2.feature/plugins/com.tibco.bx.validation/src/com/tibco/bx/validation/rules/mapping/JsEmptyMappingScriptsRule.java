/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author mtorres
 */
public class JsEmptyMappingScriptsRule extends FlowContainerValidationRule {
    public static final String EMPTY_SCRIPT_DATA_NOT_SUPPORTED =
            "bx.emptyScriptNotSupported"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            // Get the mapped and unmapped script informations
            Collection<ScriptInformation> allScriptInformationList =
                    getMappingScripts(activity);

            if (allScriptInformationList != null
                    && !allScriptInformationList.isEmpty()) {
                for (ScriptInformation scriptInformation : allScriptInformationList) {
                    String grammar =
                            ProcessScriptUtil
                                    .getScriptGrammar(scriptInformation);
                    if (grammar != null && scriptInformation.getName() != null
                            && grammar.equals(JsConsts.JAVASCRIPT_GRAMMAR)) {
                        String text =
                                ProcessScriptUtil
                                        .getTaskScript(scriptInformation);
                        boolean emptyScript =
                                ScriptParserUtil.isEmptyScript(text,
                                        ProcessJsConsts.WEBSERVICE_TASK);
                        if (emptyScript) {
                            if (scriptInformation.eContainer() instanceof DataMapping) {
                                Map<String, String> additionalInfoMap =
                                        new HashMap<String, String>();
                                if (scriptInformation.eContainer() instanceof DataMapping) {
                                    DataMapping mapping =
                                            (DataMapping) scriptInformation
                                                    .eContainer();
                                    String target =
                                            DataMappingUtil.getTarget(mapping);
                                    additionalInfoMap
                                            .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                                    target);
                                }
                                addIssue(EMPTY_SCRIPT_DATA_NOT_SUPPORTED,
                                        scriptInformation.eContainer(),
                                        new ArrayList<String>(),
                                        additionalInfoMap);
                            } else {
                                addIssue(EMPTY_SCRIPT_DATA_NOT_SUPPORTED,
                                        scriptInformation);
                            }
                        }
                    }
                }
            }

            /* SDA-102: consider decision service task for validation */
            EObject decisionFlow =
                    DecisionTaskObjectUtil.getDecisionFlow(activity);
            if (decisionFlow != null) {
                SubFlow decisionSubFlow =
                        DecisionTaskObjectUtil
                                .getDecisionFlowReference(activity);
                for (Object next : decisionSubFlow.getDataMappings()) {
                    if (next instanceof DataMapping) {
                        boolean isEmpty =
                                validateDataMapping((DataMapping) next);
                        if (isEmpty) {
                            Map<String, String> additionalInfoMap =
                                    new HashMap<String, String>();
                            String target =
                                    DataMappingUtil
                                            .getTarget((DataMapping) next);
                            additionalInfoMap
                                    .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                            target);
                            addIssue(EMPTY_SCRIPT_DATA_NOT_SUPPORTED,
                                    (DataMapping) next,
                                    new ArrayList<String>(),
                                    additionalInfoMap);
                        }
                    }
                }
            }

        }
    }

    /**
     * 
     * @param activity
     * @return All mapped and unmapped mapping scripts for activity.
     */
    private Collection<ScriptInformation> getMappingScripts(Activity activity) {
        List<ScriptInformation> scriptInformations =
                new ArrayList<ScriptInformation>();

        scriptInformations.addAll(ProcessScriptUtil
                .getAllMappedScriptInformation(ProcessScriptUtil
                        .getActivityDataMappingList(activity,
                                JsConsts.JAVASCRIPT_GRAMMAR)));

        scriptInformations.addAll(ProcessScriptUtil
                .getActivityUnmappedScriptInformations(activity,
                        JsConsts.JAVASCRIPT_GRAMMAR));
        return scriptInformations;
    }

    /**
     * @param next
     */
    private boolean validateDataMapping(DataMapping mapping) {
        Object other =
                Xpdl2ModelUtil.getOtherElement(mapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        if (other instanceof ScriptInformation) {
            String script = DataMappingUtil.getScript(mapping);
            String grammar = DataMappingUtil.getGrammar(mapping);
            return ScriptParserUtil.isEmptyScript(script, grammar);
        }
        return false;
    }

}