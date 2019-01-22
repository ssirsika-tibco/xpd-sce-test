/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.process.xpath.parser.antlr.XPathParser;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ScriptContentsRule extends ActivityValidationRule {

    /** Error id for missing script content. */
    private static final String MISSING_SCRIPT = "bpmn.dev.missingScript"; //$NON-NLS-1$

    private static final String JAVASCRIPT_GRAMMAR = "JavaScript"; //$NON-NLS-1$

    /**
     * @param activity
     *            The activity to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#
     *      validate(com.tibco.xpd.xpdl2.Activity)
     */
    @Override
    public void validate(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            Task task = (Task) implementation;
            if (task.getTaskService() != null) {
                TaskService service = task.getTaskService();
                validateMessage(service.getMessageIn());
                validateMessage(service.getMessageOut());
                /* SDA-102: consider decision service task for validation */
                EObject decisionFlow =
                        DecisionTaskObjectUtil.getDecisionFlow(activity);
                if (decisionFlow != null) {
                    SubFlow decisionSubFlow =
                            DecisionTaskObjectUtil
                                    .getDecisionFlowReference(activity);
                    for (Object next : decisionSubFlow.getDataMappings()) {
                        if (next instanceof DataMapping) {
                            validateDataMapping((DataMapping) next);
                        }
                    }
                }
            } else if (task.getTaskSend() != null) {
                TaskSend send = task.getTaskSend();
                validateMessage(send.getMessage());
            } else if (task.getTaskReceive() != null) {
                TaskReceive receive = task.getTaskReceive();
                validateMessage(receive.getMessage());
            }
        } else if (implementation instanceof SubFlow) {
            SubFlow subflow = (SubFlow) implementation;
            for (Object next : subflow.getDataMappings()) {
                if (next instanceof DataMapping) {
                    validateDataMapping((DataMapping) next);
                }
            }
        } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                .equals(EventObjectUtil.getEventTriggerType(activity))) {
            List<DataMapping> dataMappings =
                    Xpdl2ModelUtil.getDataMappings(activity,
                            DirectionType.OUT_LITERAL);

            for (DataMapping dataMapping : dataMappings) {
                validateDataMapping(dataMapping);
            }
        }

        Event event = activity.getEvent();
        if (event instanceof StartEvent) {
            TriggerResultMessage result =
                    ((StartEvent) event).getTriggerResultMessage();
            if (result != null) {
                validateMessage(result.getMessage());
            }
        } else if (event instanceof EndEvent) {
            TriggerResultMessage result =
                    ((EndEvent) event).getTriggerResultMessage();
            if (result != null) {
                validateMessage(result.getMessage());
            }
        } else if (event instanceof IntermediateEvent) {
            TriggerResultMessage result =
                    ((IntermediateEvent) event).getTriggerResultMessage();
            if (result != null) {
                validateMessage(result.getMessage());
            }
        }
    }

    /**
     * @param message
     *            The message to validate.
     */
    private void validateMessage(Message message) {
        if (message != null) {
            for (Object next : message.getDataMappings()) {
                if (next instanceof DataMapping) {
                    validateDataMapping((DataMapping) next);
                }
            }
        }
    }

    /**
     * @param mapping
     *            The data mapping to validate.
     */
    private void validateDataMapping(DataMapping mapping) {
        Object other =
                Xpdl2ModelUtil.getOtherElement(mapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
        if (other instanceof ScriptInformation) {
            String target = DataMappingUtil.getTarget(mapping);
            String script = DataMappingUtil.getScript(mapping);
            String grammar = DataMappingUtil.getGrammar(mapping);
            validateScript(mapping, script, target, grammar);
        }
    }

    /**
     * @param container
     *            The expression container.
     * @param script
     *            The script.
     * @param target
     *            The target path.
     */
    private void validateScript(EObject container, String script,
            String target, String grammar) {
        boolean hasScript = false;
        List destinationList = Collections.EMPTY_LIST;
        List validationStrategyList = Collections.EMPTY_LIST;
        ISymbolTable symbolTable = null;
        Map validationErrorMap = Collections.EMPTY_MAP;
        Map validationWarningMap = Collections.EMPTY_MAP;
        if (grammar != null && script != null) {
            if (grammar.equals(XPathScriptParserUtil.XPATH_GRAMMAR)) {
                XPathParser parser =
                        XPathScriptParserUtil.validateXPathScript(script,
                                destinationList,
                                symbolTable,
                                validationStrategyList,
                                validationErrorMap,
                                validationWarningMap,
                                null,
                                false,
                                null);
                if (parser != null) {
                    hasScript = true;
                }
            } else if (grammar.equals(JAVASCRIPT_GRAMMAR)) {
                hasScript = !ScriptParserUtil.isEmptyScript(script, grammar);
            } else {
                /*
                 * SIA-1 Regardless of whether we recognise the grammar, we
                 * should NOT complain about empty script when it is not empty!
                 */
                if (script.trim().length() != 0) {
                    hasScript = true;
                }
            }
        }

        if (!hasScript) {
            String key = MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO;
            List<String> messages = Collections.emptyList();
            addIssue(MISSING_SCRIPT,
                    container,
                    messages,
                    Collections.singletonMap(key, target));
        }
    }

}
