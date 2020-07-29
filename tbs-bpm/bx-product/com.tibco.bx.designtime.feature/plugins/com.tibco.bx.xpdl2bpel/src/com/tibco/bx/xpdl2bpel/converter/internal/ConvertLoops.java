/*******************************************************************************
 * Copyright 2006 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */
package com.tibco.bx.xpdl2bpel.converter.internal;


import java.util.List;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.RepeatUntil;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.w3c.dom.Element;

import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerTask;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.DataTypeUtil;
import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.StandardLoopScript;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopStandard;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIFlowConditionType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.TestTimeType;

/**
 * Created by IntelliJ IDEA.
 * User: goldberg
 * Date: Sep 1, 2007
 * Time: 9:15:10 AM
 */
public class ConvertLoops {

    private static final String FLOW_CONDITION_EXTENSION = "flowCondition";  //todo maybe change this to afterOne since that is only flow condition with an extension //$NON-NLS-1$
    private static final String LOOP_COUNTER_EXTENSION = "counterName"; //$NON-NLS-1$
    private static final String LOOP_MAXIMUM_EXTENSION = "loopMaximum"; //$NON-NLS-1$
    private static final String ADDITIONAL_COUNTER_VALUE_EXTENSION = "additionalCounterValue"; //$NON-NLS-1$
    private static final String EXPRESSION_LANGAGE = "expressionLanguage"; //$NON-NLS-1$

    /**
     * Each loop activity needs to be embedded in the appropriate structured activity.
     * - While
     * - RepeatUntil
     * - ForEach
     * @throws ConversionException 
     */
    public static org.eclipse.bpel.model.Activity convertLoop(ConverterContext context, Flow bpelFlow, AnalyzerTask task, String activitySetId) throws ConversionException {
    	com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
    	org.eclipse.bpel.model.Activity bpelActivity = (org.eclipse.bpel.model.Activity)task.getBpelReference();
    	org.eclipse.bpel.model.Activity result = null;
        boolean standardWhileLoop = false;
        boolean standardUntilLoop = false;
        boolean parallelMILoop = false;
        boolean serialMILoop = false;
        String loopCondition = null;
        Integer loopMax = null;
        String loopCounter = null;
        int miFlowConditionType = -1;
        // MIFlowConditionType.NONE - uncontrolled flow, loop and subsequent activities wrapped in ForEach
        // MIFlowConditionType.ONE - as above plus next activities gets MaxWait=1 as per discriminator
        // MIFlowConditionType.ALL - controlled flow, only loop activity is wrapped in ForEach
        // MIFlowConditionType.COMPLEX - not supported

        String miCondition = null;
        String additionalCondition = null;
        String complexExitCondition = null;

        Loop loop = xpdlActivity.getLoop();
        if (loop!=null) {
            LoopType loopType = loop.getLoopType();
            if (loopType.getValue() == LoopType.STANDARD) {
                LoopStandard loopStandard = loop.getLoopStandard();
                if (loopStandard!=null) {
                    loopCondition = loopStandard.getLoopCondition().getText();
                    if (loopStandard.getLoopMaximum()!=null) {
                        loopMax = loopStandard.getLoopMaximum().intValue();
                    }
                    if (loopStandard.getTestTime().getValue() == TestTimeType.AFTER) {
                        standardUntilLoop = true;
                    } else {
                        standardWhileLoop = true;
                    }
                    FeatureMap otherElements = loopStandard.getOtherElements();
                    if (otherElements!=null) {
                        FeatureMap.ValueListIterator it = otherElements.valueListIterator();
                        while (it.hasNext()) {
                            EObject obj = (EObject)it.next();
                            if (obj instanceof StandardLoopScript) {
                                Expression loopExpression = ((StandardLoopScript)obj).getLoopExpression();
                                if (loopExpression!=null) {
                                    loopCondition = loopExpression.getText();
                                }
                            }
                        }
                    }
                } else {
                    //Studio 2.x used extended attributes for loop parameters
                    List<ExtendedAttribute> list = xpdlActivity.getExtendedAttributes();
                    if (list!=null && list.size()>0) {
                        for (ExtendedAttribute attr: list) {
                            if ("LoopCondition".equals(attr.getName())) { //$NON-NLS-1$
                                loopCondition = attr.getValue();
                            } else if ("LoopMaximum".equals(attr.getName())) { //$NON-NLS-1$
                                loopMax = new Integer(attr.getValue());
                            } else if ("TestTime".equals(attr.getName())) { //$NON-NLS-1$
                                String testType = attr.getValue();
                                if ("After".equals(testType)) { //$NON-NLS-1$
                                    standardUntilLoop = true;
                                } else {
                                    standardWhileLoop = true;
                                }
                            }
                        }
                    }
                }
            } else if (loopType.getValue() == LoopType.MULTI_INSTANCE) {
                LoopMultiInstance loopMI = loop.getLoopMultiInstance();
                if (loopMI!=null) {
                    miCondition = loopMI.getMICondition().getText();
                    if (loopMI.getMIOrdering().getValue() == MIOrderingType.PARALLEL) {
                        parallelMILoop = true;
                    } else {
                        serialMILoop = true;
                    }
                    miFlowConditionType = loopMI.getMIFlowCondition().getValue();
                    FeatureMap otherElements = loopMI.getOtherElements();
                    if (otherElements!=null) {
                        FeatureMap.ValueListIterator it = otherElements.valueListIterator();
                        while (it.hasNext()) {
                            EObject obj = (EObject)it.next();
                            if (obj instanceof MultiInstanceScripts) {
                                MultiInstanceScripts miScripts = (MultiInstanceScripts)obj;
                                //TODO
//                                	Expression loopExpression = miScripts.getLoopExpression();
//                                	if (loopExpression!=null) {
//                                		miCondition = loopExpression.getText();
//                                	}
                                Expression additionalExpression = miScripts.getAdditionalInstances();
                                if (additionalExpression!=null) {
                                    additionalCondition = additionalExpression.getText();
                                }
                                //TODO
//                                	Expression complexExitExpression = miScripts.getComplexExitExpression();
//                                    if (complexExitExpression!=null) {
//                                        complexExitCondition = complexExitExpression.getText();
//                                    }
                            }
                        }
                    }
                }
            }
        } else if (xpdlActivity.getStartQuantity()!=null && xpdlActivity.getStartQuantity().intValue()==-1) {
            //Studio 2.x used extended attrbiutes to set loop parameters
            List<ExtendedAttribute> list = xpdlActivity.getExtendedAttributes();
            if (list!=null && list.size()>0) {
                for (ExtendedAttribute attr: list) {
                    if ("MI_Condition".equals(attr.getName())) { //$NON-NLS-1$
                        miCondition = attr.getValue();
                    } else if ("MI_Ordering".equals(attr.getName())) { //$NON-NLS-1$
                        if ("Parallel".equals(attr.getValue())) { //$NON-NLS-1$
                            parallelMILoop = true;
                        } else {
                            serialMILoop = true;
                        }
                    } else if ("MI_FlowCondition".equals(attr.getName())) { //$NON-NLS-1$
                        String type = attr.getValue();
                        if ("All".equals(type)) miFlowConditionType = MIFlowConditionType.ALL; //$NON-NLS-1$
                        if ("One".equals(type)) miFlowConditionType = MIFlowConditionType.ONE; //$NON-NLS-1$
                        if ("None".equals(type)) miFlowConditionType = MIFlowConditionType.NONE; //$NON-NLS-1$
                        if ("Complex".equals(type)) miFlowConditionType = MIFlowConditionType.COMPLEX; //$NON-NLS-1$
                        if ("Immediate".equals(type)) miFlowConditionType = MIFlowConditionType.COMPLEX+10; //$NON-NLS-1$
                    }
                }
            }
        }

        if (parallelMILoop || serialMILoop) {
            org.eclipse.bpel.model.ForEach forEach;
            String flowCondition = null;
            switch (miFlowConditionType) {
                case MIFlowConditionType.COMPLEX+10: // immediate. matches work pattern WCP12
                case MIFlowConditionType.ONE:
                    if (miFlowConditionType==MIFlowConditionType.ONE) {
                        // wrap looping activity with ForEach and set to enter successors after first child done
                        flowCondition = "one"; //$NON-NLS-1$
                    } else {
                        // Immediate - wrap looping activity with ForEach ast set to enter successors without waiting for any child to complete
                        flowCondition = "immediate"; //$NON-NLS-1$
                    }
                case MIFlowConditionType.ALL:
                    // wrap looping activity with ForEach
                    forEach = wrapItWithForEach(context, task, bpelActivity, xpdlActivity, bpelFlow, miCondition, additionalCondition, complexExitCondition);
                    forEach.setParallel(parallelMILoop);
                    if (flowCondition!=null) {
                    	BPELUtils.addExtensionAttribute(forEach, FLOW_CONDITION_EXTENSION, flowCondition);
                    }
                    result = forEach;
                    break;
                case MIFlowConditionType.NONE:
                    // this is equavilent to wrapping looping activity and all that follows with forEach
                    // can be achieved by putting everything into an embedded subprocess that is marked MIFlowConditionType.ALL
                    context.logError(xpdlActivity.getId(), Messages.getString("ConvertLoops.MI_FlowCondition_NoneNotSupported"), null); //$NON-NLS-1$
                    break;
                case MIFlowConditionType.COMPLEX:
                    context.logError(xpdlActivity.getId(), Messages.getString("ConvertLoops.MI_FlowCondition_ComplexNotSupported"), null); //$NON-NLS-1$
                    break;
                default:
                    context.logError(xpdlActivity.getId(), Messages.getString("ConvertLoops.MI_FlowCondition_Illegal"), null); //$NON-NLS-1$
            }
        } else if (standardWhileLoop) {
            // wrap loop activity with While
            result = wrapStandardLoop(context, task, bpelActivity, xpdlActivity, bpelFlow, loopCondition, loopMax, true);
        } else if (standardUntilLoop) {
            // wrap loop activity with RepeatUntil
            result = wrapStandardLoop(context, task, bpelActivity, xpdlActivity, bpelFlow, loopCondition, loopMax, false);
        } else {
            // todo error, malformed loop activity
        }

        return result;
    }


    private static org.eclipse.bpel.model.ForEach wrapItWithForEach(ConverterContext context,
    																AnalyzerTask task,
                                                                    org.eclipse.bpel.model.Activity bpelActivity,
                                                                    com.tibco.xpd.xpdl2.Activity xpdlActivity,
                                                                    org.eclipse.bpel.model.Flow bpelFlow,
                                                                    String condition,
                                                                    String additionalCondition,
                                                                    String complexExitCondition) throws ConversionException {
        List<org.eclipse.bpel.model.Activity>activities = null;
        if (bpelFlow!=null) activities = bpelFlow.getActivities();
        org.eclipse.bpel.model.ForEach forEach = BPELFactory.eINSTANCE.createForEach();
        context.syncXpdlId(forEach, xpdlActivity);
        if (task.isMigrationAllowed()) {
        	// move migrationAllowed attribute to outside of loop
        	BPELUtils.addExtensionAttribute(bpelActivity, N2PEConstants.MIGRATION_ALLOWED_TAG, null);
        	BPELUtils.addExtensionAttribute(forEach, N2PEConstants.MIGRATION_ALLOWED_TAG, "yes");
        }
//        forEach.setName(bpelActivity.getName());
        forEach.setName(context.generateActivityName("forEach", bpelActivity.getName(), xpdlActivity.getId()));
        org.eclipse.bpel.model.Scope scope = BPELFactory.eINSTANCE.createScope();
//        scope.setName("scope"+xpdlActivity.getId()); //$NON-NLS-1$
        scope.setName(context.generateActivityName("scopeMI", bpelActivity.getName(), xpdlActivity.getId()));
        Variables variables = context.getVariables(xpdlActivity);
        if (variables != null) {
        	scope.setVariables(variables);
        }
        
		EList<DataField> dataFields = xpdlActivity.getDataFields();
		if (dataFields != null && !dataFields.isEmpty()) { // has local data
		    
            /* Sid ACE-2936 Add the dataField descriptor. */
            BPELUtils.addActivityDataFieldDescriptorInfo(scope, xpdlActivity);

            if (scope.getVariables() == null) {
            	scope.setVariables(org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables());
            }
			for (DataField dataField : dataFields) {
				Variable variable = ConvertDataField.convertDataFieldToBPELVariable(xpdlActivity.getProcess(), dataField);
				scope.getVariables().getChildren().add(variable);
			}
		}
		
        org.eclipse.bpel.model.Variable var = createLoopCounterVariable(scope);

        forEach.setCounterName(var);
        forEach.setActivity(scope);
        
        org.eclipse.bpel.model.Expression startCounterValue = BPELFactory.eINSTANCE.createExpression();
        
        // Sid ACE-4344 runtime engine now requires JavaScript grammar on all expressions.
        startCounterValue.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
        startCounterValue.setBody(1);
        
        org.eclipse.bpel.model.Expression finalCounterValue = BPELFactory.eINSTANCE.createExpression();
        finalCounterValue.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
        finalCounterValue.setBody(condition);
        forEach.setStartCounterValue(startCounterValue);
        forEach.setFinalCounterValue(finalCounterValue);

        // complex exit condition not currently supported. This would require an extension since it is a boolean expression
        //  on when to pass on token and not the same as bpel completion condition which is an unsigned integer expression
        //  on when to exit the loop
//        if (complexExitCondition!=null && complexExitCondition.length()>0) {
//            org.eclipse.bpel.model.CompletionCondition completionCondition = BPELFactory.eINSTANCE.createCompletionCondition();
//            org.eclipse.bpel.model.Branches branches = BPELFactory.eINSTANCE.createBranches();
//            branches.setExpressionLanguage(ConvertUtil.JSCRIPT_LANGUAGE);
//            branches.setBody(complexExitCondition);
//            completionCondition.setBranches(branches);
//            forEach.setCompletionCondition(completionCondition);
//        }

        if (additionalCondition!=null && additionalCondition.length()>0) {
        	Element extElement = BPELUtils.makeExtensionElement(forEach, ADDITIONAL_COUNTER_VALUE_EXTENSION);
        	extElement.setAttribute(EXPRESSION_LANGAGE, N2PEConstants.JSCRIPT_LANGUAGE);
        	extElement.setTextContent(additionalCondition);
        }
        
        //remove bpelActivity from its flow and add to scope
        //add forEach to host's old flow
        //reposition in/out links of bpelActivity to forEach
        if (activities!=null) {
        	activities.remove(bpelActivity);
        	activities.add(forEach);
        }
        scope.setActivity(bpelActivity);
        context.syncXpdlId(bpelActivity, xpdlActivity);
        List<org.eclipse.bpel.model.Link> links = BPELUtils.getLinksFromActivity(bpelActivity);
        for (org.eclipse.bpel.model.Link link: links) {
            ConvertControlFlow.replaceLinkSource(context, link, forEach);
        }
        links = BPELUtils.getLinksToActivity(bpelActivity);
        for (org.eclipse.bpel.model.Link link: links) {
            ConvertControlFlow.replaceLinkTarget(context, link, forEach);
        }
        return forEach;
    }

    // create variable on scope to hold loop counter
    private static org.eclipse.bpel.model.Variable createLoopCounterVariable(org.eclipse.bpel.model.Scope scope) {
        org.eclipse.bpel.model.Variable var = BPELFactory.eINSTANCE.createVariable();
        var.setName(N2PEConstants.NAME_PREFIX+"LoopCounter"); //$NON-NLS-1$
        XSDSimpleTypeDefinition varType = DataTypeUtil.getXSDPrimitive("int"); //$NON-NLS-1$
        var.setType(varType);
        org.eclipse.bpel.model.Variables variables = scope.getVariables();
        if (variables == null) {
        	variables = BPELFactory.eINSTANCE.createVariables();
            scope.setVariables(variables);
        }
        variables.getChildren().add(var);
        return var;
    }


    private static org.eclipse.bpel.model.Scope wrapStandardLoop(ConverterContext context,
    									 AnalyzerTask task,
                                         org.eclipse.bpel.model.Activity bpelActivity,
                                         com.tibco.xpd.xpdl2.Activity xpdlActivity,
                                         org.eclipse.bpel.model.Flow bpelFlow,
                                         String condition,
                                         Integer loopMax,
                                         boolean before) throws ConversionException {

        List<org.eclipse.bpel.model.Activity>activities = null;
        if (bpelFlow!=null) activities = bpelFlow.getActivities();
        org.eclipse.bpel.model.Scope scope = BPELFactory.eINSTANCE.createScope();
        context.syncXpdlId(scope, xpdlActivity);
        if (task.isMigrationAllowed()) {
        	// move migrationAllowed attribute to outside of loop
        	BPELUtils.addExtensionAttribute(bpelActivity, N2PEConstants.MIGRATION_ALLOWED_TAG, null);
        	BPELUtils.addExtensionAttribute(scope, N2PEConstants.MIGRATION_ALLOWED_TAG, "yes");
        }
//        scope.setName(bpelActivity.getName());
        scope.setName(context.generateActivityName("scope", bpelActivity.getName(), xpdlActivity.getId()));

        Variables variables = context.getVariables(xpdlActivity);
        if (variables != null) {
        	scope.setVariables(variables);
        }
        
		EList<DataField> dataFields = xpdlActivity.getDataFields();
		if (dataFields != null && !dataFields.isEmpty()) { // has local data
		    
            /* Sid ACE-2936 Add the dataField descriptor. */
            BPELUtils.addActivityDataFieldDescriptorInfo(scope, xpdlActivity);

            if (scope.getVariables() == null) {
            	scope.setVariables(org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables());
            }
			for (DataField dataField : dataFields) {
				Variable variable = ConvertDataField.convertDataFieldToBPELVariable(xpdlActivity.getProcess(), dataField);
				scope.getVariables().getChildren().add(variable);
			}
		}
        
        org.eclipse.bpel.model.Variable var = createLoopCounterVariable(scope);

        org.eclipse.bpel.model.Condition bpelCondition = BPELFactory.eINSTANCE.createCondition();
        bpelCondition.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
        bpelCondition.setBody(condition);

        context.syncXpdlId(bpelActivity, xpdlActivity);
        if (activities!=null)
        	activities.remove(bpelActivity); // remove bpel activity from its old flow
        org.eclipse.bpel.model.Activity structuredActivity;
        if (before) {
            structuredActivity = BPELFactory.eINSTANCE.createWhile();
            ((org.eclipse.bpel.model.While)structuredActivity).setActivity(bpelActivity);
            ((org.eclipse.bpel.model.While)structuredActivity).setCondition(bpelCondition);
            structuredActivity.setName(context.generateActivityName("while", bpelActivity.getName(), xpdlActivity.getId()));
        } else {
            structuredActivity = BPELFactory.eINSTANCE.createRepeatUntil();
            ((RepeatUntil)structuredActivity).setActivity(bpelActivity);
            ((RepeatUntil)structuredActivity).setCondition(bpelCondition);
            structuredActivity.setName(context.generateActivityName("until", bpelActivity.getName(), xpdlActivity.getId()));
        }

        BPELUtils.addExtensionAttribute(structuredActivity, LOOP_COUNTER_EXTENSION, var.getName());
        if (loopMax!=null) {
        	BPELUtils.addExtensionAttribute(structuredActivity, LOOP_MAXIMUM_EXTENSION, loopMax.toString());
        }

        if (activities!=null)
        	activities.add(scope);  // add new scope to flow
        scope.setActivity(structuredActivity);
        //reposition in/out links of bpelActivity to new scope
        List<org.eclipse.bpel.model.Link> links = BPELUtils.getLinksFromActivity(bpelActivity);
        for (org.eclipse.bpel.model.Link link: links) {
            ConvertControlFlow.replaceLinkSource(context, link, scope);
        }
        links = BPELUtils.getLinksToActivity(bpelActivity);
        for (org.eclipse.bpel.model.Link link: links) {
            ConvertControlFlow.replaceLinkTarget(context, link, scope);
        }

        return scope;
   }
}
