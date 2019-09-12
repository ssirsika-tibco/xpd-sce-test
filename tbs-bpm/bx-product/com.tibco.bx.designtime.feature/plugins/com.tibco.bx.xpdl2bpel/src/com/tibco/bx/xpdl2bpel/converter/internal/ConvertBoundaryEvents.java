/*******************************************************************************
 * Copyright 2006 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */
package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Empty;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.OnAlarm;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.ExtensionsPackage;
import com.tibco.bx.bpelExtension.extensions.OnReceiveEvent;
import com.tibco.bx.bpelExtension.extensions.SignalEvent;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.Tracing;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerTask;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerUtil;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.converter.internal.ConvertDataMapping.DataMappingInfo;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.DataTypeUtil;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException;
import com.tibco.xpd.xpdExtension.RescheduleDurationType;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * Created by IntelliJ IDEA. User: goldberg Date: Sep 1, 2007 Time: 9:15:10 AM
 */
public class ConvertBoundaryEvents {

	private static final String CANCEL_EXTENSION = "cancel"; //$NON-NLS-1$
	private static final String RESCHEDULE_EXTENSION = "reschedule"; //$NON-NLS-1$
	private static final String EXPRESSION_LANGAGE = "expressionLanguage"; //$NON-NLS-1$
	private static final String ADD_TO_CURRENT_ATTRIBUTE = "addToCurrent"; //$NON-NLS-1$

	public static void convertBoundaryEvents(ConverterContext context, Flow bpelFlow, AnalyzerTask boundaryHost)
			throws ConversionException {
		// prepare fan variable. one per scope is needed
		Variable fanVar = makeFanVariable(context, true);
		Scope scope = scopeIt(context, bpelFlow, fanVar, boundaryHost);
		boundaryHost.setBpelReference(scope);
		int fanNumber = 1;
		for (AnalyzerTask boundaryTask : boundaryHost.getBoundaryTasks()) {
			com.tibco.xpd.xpdl2.Activity eventAct = boundaryTask.getXpdlActivity();
			Activity placeHolder = (Activity) boundaryTask.getBpelReference();
			com.tibco.xpd.xpdl2.Event event = eventAct.getEvent();

			TriggerType triggerType = ((IntermediateEvent) event).getTrigger();
			boolean intersect = true; // default to worse case for now (it
										// always works, just not always
										// optimal)
			switch (triggerType.getValue()) {
			case TriggerType.COMPENSATION:
				CompensationHandler ch = BPELFactory.eINSTANCE.createCompensationHandler();
				boundaryTask.getAssociation().getBpelReference();
				Activity compensationActivity = (Activity) (boundaryTask.getAssociation().getBpelReference());
				BPELUtils.addExtensionAttribute(ch, N2PEConstants.NAME_TAG, boundaryTask.getName());
				BPELUtils.addExtensionAttribute(ch, N2PEConstants.XPDL_ID_TAG, boundaryTask.getXpdlActivity().getId());
				BPELUtils.addExtensionAttribute(ch, N2PEConstants.TYPE_TAG,
						N2PEConstants.BOUNDARY_COMPENSATION_EVENT_TYPE);
				ch.setActivity(compensationActivity);
				scope.setCompensationHandler(ch);
				context.setUseExplicitCompensation(true);
				break;
			case TriggerType.ERROR:
				if (intersect) {
					// turn error event into catch with assign to fan variable

					/*
					 * XPD-8018: First just create the fan out <assign>
					 * activity. This now may be replaced by a different assign
					 * (or something else) once datamappings are taken into
					 * account. So now we separate the creation of the assign,
					 * with its configuration.
					 */

					Assign fanOutAssign = ConvertControlFlow.makeFanAssignActivity(fanVar, Integer.toString(fanNumber));

					Activity theMappingActivity = new ConvertCatch(context, eventAct).convertErrorTriggerToCatch(scope,
							fanOutAssign);

					/*
					 * XPD-8010: Create a new setupForFanOut that is passed the
					 * activity
					 */
					configureFanOutActivity(context, fanVar, scope, fanNumber++, eventAct, placeHolder,
							N2PEConstants.BOUNDARY_ERROR_EVENT_TYPE, theMappingActivity);

					RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
					com.tibco.xpd.xpdl2.Activity attachedToTask = BpmnCatchableErrorUtil.getAttachedToTask(eventAct);
					if (rsta.isRestServiceImplementation(attachedToTask)) {
						ConvertRestService.createScript(eventAct, fanOutAssign, "restResponseScript", //$NON-NLS-1$
								MappingDirection.OUT);
					}

				} else {
					// move error path into fault handler
					// todo future optimization if error path and normal path do
					// not intersect, fan-out not needed
				}
				break;
			case TriggerType.TIMER:
				boolean continueOn = AnalyzerUtil.isTimerContinueEvent(boundaryTask);
				if (intersect && !continueOn) {
					// turn timer event into OnAlarm with cancel option. Assign
					// fanNumber variable in body of OnAlarm
					Assign assignAct = setupForFanOut(context, fanVar, scope, fanNumber++, eventAct, placeHolder,
							N2PEConstants.BOUNDARY_TIMER_EVENT_TYPE);
					Scope scopeBody = wrapInScope(context, assignAct, eventAct);
					makeOnAlarm(context, scope, scopeBody, eventAct, boundaryHost.getXpdlActivity(), continueOn);
				} else if (continueOn) {
					// continue on case requires link directly from OnAlarm body
					// to task following boundary event outside scope
					// Target needs to be PathStarter (Uncontrolled Merge)
					Scope body = setupForContinue(context, eventAct, placeHolder,
							N2PEConstants.BOUNDARY_TIMER_EVENT_TYPE);
					makeOnAlarm(context, scope, body, eventAct, boundaryHost.getXpdlActivity(), continueOn);
				} else {
					// move error path into event handler
					// todo future optimization if error path and normal path do
					// not intersect, fan-out not needed
				}
				break;
			case TriggerType.MESSAGE:
				if (intersect) {
					Assign assignAct = setupForFanOut(context, fanVar, scope, fanNumber++, eventAct, placeHolder,
							N2PEConstants.BOUNDARY_MESSAGE_EVENT_TYPE);
					makeOnEvent(context, scope, assignAct, eventAct);
				} else {
					// move error path into fault handler
					// todo future optimization if error path and normal path do
					// not intersect, fan-out not needed
				}
				break;
			case TriggerType.NONE:
			    // Incoming request boundary event.
			    if (intersect && !context.isPageFlowEngineTarget()) {
			        Assign assignAct = setupForFanOut(context, fanVar, scope, fanNumber++, eventAct, placeHolder,
			                N2PEConstants.BOUNDARY_MESSAGE_EVENT_TYPE);
			        makeOnEventInRequest(context, scope, assignAct, eventAct);
			    } else {
			        // move error path into fault handler
			        // todo future optimization if error path and normal path do
			        // not intersect, fan-out not needed
			    }
			    break;
			case TriggerType.SIGNAL:
				if (intersect) {
					Assign assignAct = setupForFanOut(context, fanVar, scope, fanNumber++, eventAct, placeHolder,
							N2PEConstants.BOUNDARY_SIGNAL_EVENT_TYPE);
					makeOnSignal(context, scope, assignAct, eventAct);
				} else {
					// move error path into fault handler
					// todo future optimization if error path and normal path do
					// not intersect, fan-out not needed
				}
				break;
			}
			// remove placeholder from flow
			List<Activity> activities = bpelFlow.getActivities();
			activities.remove(placeHolder);
		}
	}

	private static Scope wrapInScope(ConverterContext context, Activity activity,
			com.tibco.xpd.xpdl2.Activity xpdlActivity) {
		// BX-2538: create a scope to wrap the assign
		String scopeName = context.generateActivityName("scope", xpdlActivity.getId(), xpdlActivity.getId()); //$NON-NLS-1$
		Scope scopeBody = BPELUtils.wrapInScope(activity, scopeName);
		return scopeBody;
	}

	private static Scope scopeIt(ConverterContext context, Flow bpelFlow, Variable fanVar, AnalyzerTask boundaryHost)
			throws ConversionException {
		List<Activity> activities = bpelFlow.getActivities();
		Activity host = (Activity) boundaryHost.getBpelReference();
		if (activities.contains(host)) {
			context.addVariable(fanVar);

			// if host already scope, just add fanVar and use it
			// if (host instanceof Scope) {
			// return ((Scope)host);
			// }

			Scope scope = BPELFactory.eINSTANCE.createScope();
			// scope.setName(context.makeUniqueActivityName("scope_"+host.getName()));
			// //$NON-NLS-1$
			// scope.setName(context.generateActivityName("ScopeB",
			// host.getName(), boundaryHost.getXpdlActivity().getName()));
			// //$NON-NLS-1$
			scope.setName(context.generateActivityName("scope", boundaryHost.getXpdlActivity().getName(), //$NON-NLS-1$
					boundaryHost.getXpdlActivity().getId()));
			// todo what to do about xpdlId for scope?
			if (boundaryHost.isMigrationAllowed()) {
				// move migrationAllowed attribute to outside of loop
				BPELUtils.addExtensionAttribute(host, N2PEConstants.MIGRATION_ALLOWED_TAG, null);
				BPELUtils.addExtensionAttribute(scope, N2PEConstants.MIGRATION_ALLOWED_TAG, "yes"); //$NON-NLS-1$
			}
			if (boundaryHost.isUncontrolledMerge()) {
				// move unconrolledMerge attribute up to scope
				BPELUtils.addExtensionAttribute(host, N2PEConstants.UNCONTROLLED_MERGE_EXTENSION, null); // $NON-NLS-1$
				BPELUtils.addExtensionAttribute(scope, N2PEConstants.UNCONTROLLED_MERGE_EXTENSION, "yes"); //$NON-NLS-1$
			}

			if (host instanceof ForEach) {
				// MI loop - skip adding local data again, has already been
				// added in ConvertLoop
			} else {
				// add local data
				Variables variables = context.getVariables(boundaryHost.getXpdlActivity());
				if (variables != null) {
					scope.setVariables(variables);
				}
				EList<DataField> dataFields = boundaryHost.getXpdlActivity().getDataFields();
				if (dataFields != null && !dataFields.isEmpty()) { // has local
																	// data
					if (scope.getVariables() == null) {
						scope.setVariables(org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables());
					}
					for (DataField dataField : dataFields) {
						Variable variable = ConvertDataField
								.convertDataFieldToBPELVariable(boundaryHost.getXpdlActivity().getProcess(), dataField);
						scope.getVariables().getChildren().add(variable);
					}
				}
			}

			// remove host from flow (it will be placed in scope)
			activities.remove(host);

			// add scope to host's old flow
			activities.add(scope);
			// add host to scope
			scope.setActivity(host);

			// make trailing empty activity for target of normal flow
			Empty empty = BPELFactory.eINSTANCE.createEmpty();
			empty.setName(context.genUniqueActivityName("normalFlow")); //$NON-NLS-1$
			// add empty to flow
			activities.add(empty);

			// reposition out links of host from empty
			List<Link> links = BPELUtils.getLinksFromActivity(host);
			for (Link link : links) {
				ConvertControlFlow.replaceLinkSource(context, link, empty);
			}

			// create link from host to empty
			Condition condition = BPELFactory.eINSTANCE.createCondition();
			condition.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
			condition.setBody(fanVar.getName() + "=0; true"); //$NON-NLS-1$
			ConvertControlFlow.createLink(context, host, empty, condition, bpelFlow);

			// reposition in links of host to scope
			links = BPELUtils.getLinksToActivity(host);
			for (Link link : links) {
				ConvertControlFlow.replaceLinkTarget(context, link, scope);
			}
			return scope;
		} else {
			// host not in this flow
			return null;
		}
	}

	/**
	 * Set up the Fan Out activity.
	 * 
	 * @param context
	 * @param fanVar
	 * @param scope
	 * @param fanNumber
	 * @param eventAct
	 * @param placeHolder
	 * @param type
	 * 
	 * @return Fan out activity.
	 */
	private static Assign setupForFanOut(ConverterContext context, Variable fanVar, Scope scope, int fanNumber,
			com.tibco.xpd.xpdl2.Activity eventAct, Activity placeHolder, String type) {
		Assign assignAct = ConvertControlFlow.makeFanAssignActivity(fanVar, Integer.toString(fanNumber));

		configureFanOutActivity(context, fanVar, scope, fanNumber, eventAct, placeHolder, type, assignAct);
		// for each link out of placeholder (should only be 1)
		// ConvertControlFlow.replaceLinkSource(context, link, placeHolder);
		return assignAct;
	}

	/**
	 * Configure the fan out assign activity.
	 * 
	 * @param context
	 * @param fanVar
	 * @param scope
	 * @param fanNumber
	 * @param eventAct
	 * @param placeHolder
	 * @param type
	 */
	protected static void configureFanOutActivity(ConverterContext context, Variable fanVar, Scope scope, int fanNumber,
			com.tibco.xpd.xpdl2.Activity eventAct, Activity placeHolder, String type, Activity bpelActivity) {
		context.setActivityName(eventAct, bpelActivity, "fanSetUp" + fanNumber); //$NON-NLS-1$
		context.syncXpdlId(bpelActivity, eventAct);
		BPELUtils.setType(bpelActivity, type);
		BPELUtils.setLabel(bpelActivity, eventAct);
		ConvertProcess.convertTaskScripts(context, bpelActivity, eventAct, 2);

		List<Link> outgoingLinks = BPELUtils.getLinksFromActivity(placeHolder);
		for (Link link : outgoingLinks) {
			// should only be one
			Activity oldTarget = BPELUtils.getTargetActivity(link);
			// redirect the out link source to be the scope
			ConvertControlFlow.replaceLinkSource(context, link, scope);
			// set link condition for fan number (todo what if link already had
			// a condition
			Condition condition = BPELFactory.eINSTANCE.createCondition();
			
			/* Sid ACE-2822 ACE runtime does not support XPath scripting. The condition we used to create wasn't JavaScript so it fell back on XPath.
			 * 
			 * So we need to declare the transitionConditioin for the fan-out as JavaScript instead (like for other fan variables "_BX_fan_variable1=1; true;"
			 */
			condition.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
			condition.setBody(fanVar.getName() + "==" + fanNumber + ";"); //$NON-NLS-1$ //$NON-NLS-2$
			
			List<Source> sources = scope.getSources().getChildren();
			for (Source source : sources) {
				if (link.equals(source.getLink())) {
					source.setTransitionCondition(condition);
					break;
				}
			}
		}
	}

	private static Scope setupForContinue(ConverterContext context, com.tibco.xpd.xpdl2.Activity eventAct,
			Activity placeHolder, String type) {
		Empty body = BPELFactory.eINSTANCE.createEmpty();
		context.setActivityName(eventAct, body, "continue"); //$NON-NLS-1$
		context.syncXpdlId(body, eventAct);
		BPELUtils.setType(body, type);
		BPELUtils.setLabel(body, eventAct);
		ConvertProcess.convertTaskScripts(context, body, eventAct, 2);

		List<Link> outgoingLinks = BPELUtils.getLinksFromActivity(placeHolder);
		for (Link link : outgoingLinks) {
			// should only be one
			Activity oldTarget = BPELUtils.getTargetActivity(link);
			// redirect the out link source to be the scope
			ConvertControlFlow.replaceLinkSource(context, link, body);
		}
		return wrapInScope(context, body, eventAct);
	}

	private static void makeOnAlarm(ConverterContext context, Scope scope, Scope body,
			com.tibco.xpd.xpdl2.Activity xpdlActivity, com.tibco.xpd.xpdl2.Activity boundaryActivity,
			boolean continueOn) {
		EventHandler eventHandler = scope.getEventHandlers();
		if (eventHandler == null) {
			eventHandler = BPELFactory.eINSTANCE.createEventHandler();
		}
		List<OnAlarm> list = eventHandler.getAlarm();
		OnAlarm onAlarm = BPELFactory.eINSTANCE.createOnAlarm();
		TriggerTimer timer = ((IntermediateEvent) xpdlActivity.getEvent()).getTriggerTimer();

		if (xpdlActivity.getDeadline() != null && !xpdlActivity.getDeadline().isEmpty()) {
			Deadline deadline = xpdlActivity.getDeadline().get(0);
			com.tibco.xpd.xpdl2.Expression deadlineDuration = deadline.getDeadlineDuration();
			String scriptGrammar = deadlineDuration.getScriptGrammar();
			org.eclipse.bpel.model.Expression expr;
			if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
				String durationISO = ConvertEvent.convertConstantPeriod(deadlineDuration);
				expr = ConvertEvent.createBPELDurationExpression(durationISO);
			} else {
				expr = ConvertEvent.createBPELDurationExpression(deadlineDuration.getText());
			}

			if (timer.getTimeCycle() != null) {
				if (continueOn) {
					onAlarm.setRepeatEvery(expr);
				} else {
					onAlarm.setFor(expr);
				}
			} else { // assume TimeDate
				if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
					onAlarm.setFor(expr);
				} else {
					onAlarm.setUntil(expr);
				}
			}
		}

		XPDLUtils.configureCalendarReference(onAlarm, timer,
				ProcessInterfaceUtil.getAllAvailableRelevantDataForActivity(xpdlActivity));

		onAlarm.setActivity(body);
		if (!continueOn) {
			// set up cancel extension attribute
			BPELUtils.addExtensionAttribute(onAlarm, CANCEL_EXTENSION, "yes"); //$NON-NLS-1$
		}

		// Create the user participant element if the host is a user task
		if (boundaryActivity != null && (boundaryActivity.getImplementation() instanceof Task)
				&& (((Task) boundaryActivity.getImplementation()).getTaskUser() != null)) {
			Element extElement = XPDLUtils.addUserTaskParticipant(onAlarm, boundaryActivity);
			EObject configModel = null;
			try {
				configModel = lookupExtension(context, timer, boundaryActivity);
				if (configModel != null) {
					Element dynamicOrgAttributesDom = BPELUtils.toDOMElement(configModel);
					Node importedOrgAttributes = extElement.getOwnerDocument().importNode(dynamicOrgAttributesDom,
							true);
					extElement.appendChild(importedOrgAttributes);
				}
			} catch (ConversionException e) {
				context.logError(Messages.getString("ConvertActivity.cannotConvertTimerEvent") + e.getMessage(), e); //$NON-NLS-1$
			}
		}
		// Add reschedule expression, if used
		RescheduleTimerScript rescheduleScript = EventObjectUtil.getRescheduleTimerScript(xpdlActivity);
		if (rescheduleScript != null) {
			Element extElement = BPELUtils.makeExtensionElement(onAlarm, RESCHEDULE_EXTENSION);
			extElement.setAttribute(EXPRESSION_LANGAGE, N2PEConstants.JSCRIPT_LANGUAGE);
			String expr;
			if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(rescheduleScript.getScriptGrammar())) {
				expr = ConvertEvent.convertConstantPeriod(rescheduleScript);
			} else {
				expr = rescheduleScript.getText();
			}
			extElement.setTextContent(expr);
			if (RescheduleDurationType.EXISTING_TIMEOUT.equals(rescheduleScript.getDurationRelativeTo())) {
				extElement.setAttribute(ADD_TO_CURRENT_ATTRIBUTE, "yes"); //$NON-NLS-1$
			} else {
				extElement.setAttribute(ADD_TO_CURRENT_ATTRIBUTE, "no"); //$NON-NLS-1$
			}
		}

		String deadlineEventId = XPDLUtils.getActivityDeadlineEventId(boundaryActivity);
		if (xpdlActivity.getId().equals(deadlineEventId)) {
			BPELUtils.addExtensionAttribute(onAlarm, "deadline", "yes"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		list.add(onAlarm);
		scope.setEventHandlers(eventHandler);
	}

	private static void makeOnSignal(ConverterContext context, Scope scope, Activity body,
			com.tibco.xpd.xpdl2.Activity xpdlActivity) {
		OnReceiveEvent onReceiveEvent = ExtensionsFactory.eINSTANCE.createOnReceiveEvent();
		onReceiveEvent.setElementType(new QName(ExtensionsPackage.eNS_URI, "OnReceiveEvent")); //$NON-NLS-1$
		onReceiveEvent.setName(xpdlActivity.getName());
		onReceiveEvent.setActivity(body);
		onReceiveEvent.setCancel(true);
		SignalEvent signalEvent = ExtensionsFactory.eINSTANCE.createSignalEvent();
		signalEvent.setEvent(((IntermediateEvent) xpdlActivity.getEvent()).getTriggerResultSignal().getName());
		onReceiveEvent.setEventSource(signalEvent);

		// temporarily, use extended attribute "data" to specify data field
		// containing pay load
		List<ExtendedAttribute> list = xpdlActivity.getExtendedAttributes();
		if (list != null && list.size() > 0) {
			for (ExtendedAttribute attr : list) {
				if ("data".equals(attr.getName())) { //$NON-NLS-1$
					signalEvent.setVariable(attr.getValue());
				}
			}
		}

		// todo should go on eventHandlers element, but for some unknown reason
		// it doesn't work
		EventHandler eventHandler = scope.getEventHandlers();
		if (eventHandler == null) {
			eventHandler = BPELFactory.eINSTANCE.createEventHandler();
			scope.setEventHandlers(eventHandler);
		}
		eventHandler.addExtensibilityElement(onReceiveEvent);
	}

	public static void makeOnSignalUpdate(ConverterContext context, com.tibco.xpd.xpdl2.Activity eventAct,
			Activity bpelActivity, AnalyzerTask hostTask) throws ConversionException {
		TriggerResultSignal triggerResultSignal = ((IntermediateEvent) eventAct.getEvent()).getTriggerResultSignal();
		SignalData signalData = XPDLUtils.getSignalData(triggerResultSignal);

		com.tibco.bx.bpelExtension.extensions.SignalUpdateEvent signalUpdateEvent = ExtensionsFactory.eINSTANCE
				.createSignalUpdateEvent();
		signalUpdateEvent.setEvent(triggerResultSignal.getName());

		/*
		 * Gather the info for the <tibex:signalVariables> elements
		 * These are the data incoming from the thrown signal.
		 */
		Set<String> optionalSignalVariables = new HashSet<String>();
		Variables signalVariables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
		signalUpdateEvent.setSignalVariables(signalVariables);
		try {
			Collection<ActivityInterfaceData> signalPayload = EventObjectUtil.getSignalPayload(eventAct);
			for (ActivityInterfaceData activityData : signalPayload) {
				Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
				variable.setName(activityData.getName());
				ConvertDataField.setDataTypeForVariable(eventAct.getProcess(), variable,
						activityData.getData().getDataType(), activityData.getData().isIsArray());
				signalVariables.getChildren().add(variable);

				if (!activityData.isMandatory()) {
					optionalSignalVariables.add(activityData.getName());
				}
			}
		} catch (GetSignalPayloadException e) {
			throw new ConversionException("Failed to get the signal payload for " + eventAct.getName(), e); //$NON-NLS-1$
		}
		
        /*
         * Sid ACE-1119 For dataMapper grammar we don't output the <bpws:copy>
         * elements anymore, these are replaced with the new DataMapper mapping
         * script.
         * 
         * However, we do need to gather all of the set of the top level target
         * fields that need to be resent to the user task for the
         * <tibex:updateVariables> elements later.
         * 
         * If it's old JavaScript mappings then we still need to create
         * <bpws:copy> statements for each.
         */
		Set<String> toVars = new HashSet<String>();
		
        if (signalData != null) {
            /*
             * Check if it's DataMapper scripting (in which case it'll have one
             * of these...
             */
            ScriptDataMapper outputScriptDataMapper = signalData.getOutputScriptDataMapper();

            if (outputScriptDataMapper == null && signalData.getDataMappings() != null) {
                /*
                 * JavaScript mappings (in case we ever want to support both -
                 * maybe on merge back to XPD)
                 */
                for (DataMapping dataMapping : signalData.getDataMappings()) {
                    DataMappingInfo dataMappingInfo = new DataMappingInfo(dataMapping);
                    org.eclipse.bpel.model.To to = BPELUtils.createToVariable(dataMappingInfo.getToExpression());
                    toVars.add(dataMappingInfo.getToExpression());

                    org.eclipse.bpel.model.From from = dataMappingInfo.isScript()
                            ? BPELUtils.createFromScript(dataMappingInfo.getFromExpression(),
                                    dataMappingInfo.getGrammar())
                            : BPELUtils.createFromVariable("SIGNAL_" + dataMappingInfo.getFromExpression()); //$NON-NLS-1$

                    org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
                    copy.setTo(to);
                    copy.setFrom(from);
                    if (from.getVariable() != null
                            && optionalSignalVariables.contains(dataMappingInfo.getFromExpression())) {
                        copy.setIgnoreMissingFromData(true);
                    }
                    signalUpdateEvent.getCopy().add(copy);
                }

            } else if (outputScriptDataMapper != null) {
                /*
                 * Sid ACE-1119 DataMapper mappings
                 * 
                 * Gather the toVars to be used for <tibex:updateVariables>
                 */
                for (DataMapping dataMapping : outputScriptDataMapper.getDataMappings()) {
                    String toField = dataMapping.getFormal();

                    if (toField != null && !toField.isEmpty()) {
                        String rootField;

                        int endRootIdx = toField.indexOf("."); //$NON-NLS-1$
                        if (endRootIdx != -1) {
                            rootField = toField.substring(0, endRootIdx);
                        } else {
                            rootField = toField;
                        }

                        toVars.add(rootField);
                    }
                }

                /* Generate and add the datamapper script. */
                String dataMapperScript =
                        new DataMapperJavascriptGenerator().convertMappingsToJavascript(outputScriptDataMapper);

                if (dataMapperScript != null && !dataMapperScript.isEmpty()) {
                    Expression mappingScript = BPELFactory.eINSTANCE.createExpression();
                    mappingScript.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
                    mappingScript.setBody(dataMapperScript);

                    signalUpdateEvent.setMappingScript(mappingScript);
                }
            }
        }
		
		/*
		 * Gather the data for the <tibex:updateVariables> element.
		 * These are the data that is resent the user task. 
		 */
		Variables updateVariables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
		signalUpdateEvent.setUpdateVariables(updateVariables);
		Collection<ProcessRelevantData> allData = ProcessInterfaceUtil
				.getAllDataDefinedInProcess(eventAct.getProcess());
		for (ProcessRelevantData prd : allData) {
			if (toVars.contains(prd.getName())) {
				Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
				variable.setName(prd.getName());
				ConvertDataField.setDataTypeForVariable(eventAct.getProcess(), variable, prd.getDataType(),
						prd.isIsArray());
				updateVariables.getChildren().add(variable);
			}
		}

		Collection<com.tibco.xpd.xpdl2.Activity> timersToReschedule = EventObjectUtil
				.getRescheduleTimerEvents(eventAct);
		if (timersToReschedule != null && !timersToReschedule.isEmpty()) {
			List<Short> timers = signalUpdateEvent.getTimersToReschedule();
			for (com.tibco.xpd.xpdl2.Activity timer : timersToReschedule) {
				String timerId = timer.getId();
				short i = 0;
				for (AnalyzerTask boundaryTask : hostTask.getBoundaryTasks()) {
					IntermediateEvent ievent = (IntermediateEvent) (boundaryTask.getXpdlActivity().getEvent());
					if (ievent.getTrigger().getValue() == TriggerType.TIMER) {
						i++;
						if (timerId.equals(boundaryTask.getXpdlActivity().getId())) {
							timers.add(i);
							break;
						}
					}
				}
			}
		}

		// set task complete script
		signalUpdateEvent.setCompletedScript(ConvertProcess.getCompletedTaskScript(eventAct));
		bpelActivity.addExtensibilityElement(signalUpdateEvent);
	}

	private static void makeOnEvent(ConverterContext context, Scope scope, Assign fanAssign,
			com.tibco.xpd.xpdl2.Activity xpdlActivity) throws ConversionException {
		// - add variable to scope
		// - add OnEvent to scope
		// - make Assign for receive and set as body of OnEvent
		// - add copy statement from fanAssign to other assign
		// - set extension attribute "cancel" = "yes" for OnEvent

		EventHandler eventHandler = scope.getEventHandlers();
		if (eventHandler == null) {
			eventHandler = BPELFactory.eINSTANCE.createEventHandler();
			scope.setEventHandlers(eventHandler);
		}
		EList list = eventHandler.getEvents();
		OnEvent onEvent = BPELFactory.eINSTANCE.createOnEvent();

		list.add(onEvent);

		TriggerResultMessage triggerResultMessage = ((IntermediateEvent) xpdlActivity.getEvent())
				.getTriggerResultMessage();
		Message message = triggerResultMessage.getMessage();
		WebServiceOperation webServiceOperation = triggerResultMessage.getWebServiceOperation();

		WebServiceOperationInfo wsoInfo = null;
		try {
			wsoInfo = context.getWebServiceOperationInfo(webServiceOperation);
		} catch (ConversionException e) {
			context.logError(Messages.getString("ConvertActivity.cannotConvertTaskReceive") + e.getMessage(), e); //$NON-NLS-1$
			return;
		}

		Long seconds = XPDLUtils.getMessageTimeout(xpdlActivity);
		if (seconds > 0) {
			BPELUtils.addExtensionAttribute(onEvent, "messageTimeout", seconds.toString()); //$NON-NLS-1$
		}

		// Correlate immediate
		boolean correlateImmediate = XPDLUtils.getCorrelateImmediately(triggerResultMessage);
		if (correlateImmediate) {
			BPELUtils.addExtensionAttribute(onEvent, N2PEConstants.CORRELATE_IMMEDIATE, "yes"); //$NON-NLS-1$
		}

		ConvertCorrelations.convert(context, xpdlActivity, onEvent, wsoInfo, message);

		org.eclipse.bpel.model.PartnerLink partnerLink = wsoInfo.createPartnerLinkForWebService();
		onEvent.setPartnerLink(partnerLink);
		context.addPartnerLink(partnerLink);
		onEvent.setPortType(wsoInfo.createPortType());
		onEvent.setOperation(wsoInfo.createOperation());
		BPELUtils.addExtensionAttribute(onEvent, CANCEL_EXTENSION, "yes"); //$NON-NLS-1$
		context.syncXpdlId(onEvent, xpdlActivity);

		ConvertDataMapping convertDataMapping = new ConvertDataMapping(context, xpdlActivity, wsoInfo);

		org.eclipse.bpel.model.Assign assign = null;

		Activity theMappingActivity = convertDataMapping.convertDataMappingsToActivity(message, true);

		if (theMappingActivity != null) {
			assign = convertDataMapping.getMappingAssignActivity();
			if (assign != null) {
				// add copy from fanAssign onto assign
				EList fanCopyList = fanAssign.getCopy();
				EList copyList = assign.getCopy();
				copyList.addAll(fanCopyList);
				// copy all the extension elements, including the task scripts
				Object[] extensibilityElements = fanAssign.getEExtensibilityElements().toArray();
				for (Object object : extensibilityElements) {
					if (object instanceof ExtensibilityElement) {
						ExtensibilityElement extensibilityElement = (ExtensibilityElement) object;

						/*
						 * Do not transfer the xpdlId and type attributes to the
						 * assign, we set the attributes on the mapping activity
						 * later. if the assign is the mapping activity after
						 * all (JavaScript mapping use case), then it will get
						 * assigned then. If the assign not the mapping activity
						 * this will prevent us from specifying the type and id
						 * at multiple levels.
						 */
						Element extElement = extensibilityElement.getElement();

						if (extElement != null) {
							if (extElement.getAttributeNode(
									BPELUtils.EXTENSION_PREFIX + ":" + N2PEConstants.TYPE_TAG) != null) { //$NON-NLS-1$
								continue;
							}
							if (extElement.getAttributeNode(
									BPELUtils.EXTENSION_PREFIX + ":" + N2PEConstants.XPDL_ID_TAG) != null) { //$NON-NLS-1$
								continue;
							}
						}

						assign.addExtensibilityElement(extensibilityElement);
					}
				}
			} else {
				throw new RuntimeException(String.format(
						"Error Converting Boundary Event '%s.%s'.\nConversion of data mappings did not produce an <assign> activity.", //$NON-NLS-1$
						xpdlActivity.getProcess().getName(), xpdlActivity.getName()));
			}

		} else {
			// just use fanAssign
			theMappingActivity = fanAssign;
		}

		context.syncXpdlId(theMappingActivity, xpdlActivity);
		BPELUtils.setType(theMappingActivity, N2PEConstants.BOUNDARY_MESSAGE_EVENT_TYPE);

		theMappingActivity.setName(xpdlActivity.getName());

		if (theMappingActivity instanceof Scope) {
			onEvent.setActivity(theMappingActivity);
		} else {
			Scope scope4OnEvent = wrapInScope(context, theMappingActivity, xpdlActivity);
			onEvent.setActivity(scope4OnEvent);
		}

		org.eclipse.bpel.model.Variable inputVar = wsoInfo.createInputVariable();
		if (inputVar != null) {
			onEvent.setVariable(inputVar);
		}
	}
	
	/**
	 * Create onEvent event handler for incoming request boundary event.
	 *  
	 * @param context
	 * @param scope
	 * @param fanAssign
	 * @param xpdlActivity
	 * @throws ConversionException
	 */
	private static void makeOnEventInRequest(ConverterContext context, Scope scope, Assign fanAssign,
	        com.tibco.xpd.xpdl2.Activity xpdlActivity) throws ConversionException {
	    // - add variable to scope
	    // - add OnEvent to scope
	    // - make Assign for receive and set as body of OnEvent
	    // - add copy statement from fanAssign to other assign
	    // - set extension attribute "cancel" = "yes" for OnEvent
	    
	    EventHandler eventHandler = scope.getEventHandlers();
	    if (eventHandler == null) {
	        eventHandler = BPELFactory.eINSTANCE.createEventHandler();
	        scope.setEventHandlers(eventHandler);
	    }
	    OnEvent onEvent = BPELFactory.eINSTANCE.createOnEvent();
	    eventHandler.getEvents().add(onEvent);
	    
	    // SCE: Default message correlation timeout is no longer configurable by the user.
        // See: XPDLUtils.getMessageTimeout(xpdlActivity);
        BPELUtils.addExtensionAttribute(onEvent, "messageTimeout", context.getDefaultIncomingRequestTimeout()); //$NON-NLS-1$
	    
        // Sid ACE-2388 - Correlate immediate
        boolean correlateImmediate = XPDLUtils.getCorrelateImmediately(xpdlActivity.getEvent());
        if (correlateImmediate) {
            BPELUtils.addExtensionAttribute(onEvent, N2PEConstants.CORRELATE_IMMEDIATE, "yes"); //$NON-NLS-1$
        }

	    BPELUtils.addExtensionAttribute(onEvent, CANCEL_EXTENSION, "yes"); //$NON-NLS-1$
	    context.syncXpdlId(onEvent, xpdlActivity);
	    
	    org.eclipse.bpel.model.Activity theMappingActivity = fanAssign;;
	    context.syncXpdlId(theMappingActivity, xpdlActivity);
	    BPELUtils.setType(theMappingActivity, N2PEConstants.BOUNDARY_MESSAGE_EVENT_TYPE);
	    theMappingActivity.setName(xpdlActivity.getName());
	    
	    Scope scope4OnEvent = wrapInScope(context, theMappingActivity, xpdlActivity);
	    onEvent.setActivity(scope4OnEvent);
	    

	}

	public static Variable makeFanVariable(ConverterContext context, boolean setInitValue) {
		Variable fanVar = BPELFactory.eINSTANCE.createVariable();
		fanVar.setName(context.makeUniqueVariableName(N2PEConstants.NAME_PREFIX + "fanVariable")); //$NON-NLS-1$
		XSDSimpleTypeDefinition varType = DataTypeUtil.getXSDPrimitive("int"); //$NON-NLS-1$
		fanVar.setType(varType);
		if (setInitValue) {
			org.eclipse.bpel.model.From from = BPELUtils.createFromLiteral("<![CDATA[0]]>"); //$NON-NLS-1$
			fanVar.setFrom(from);
		}
		// context.addVariable(fanVar);
		return fanVar;
	}

	private static EObject lookupExtension(ConverterContext context, EObject defaultConfig,
			com.tibco.xpd.xpdl2.Activity xpdlActivity) throws ConversionException {
		EObject configModel = null;
		IActivityConfigurationModelBuilder modelBuilder = null;
		try {
			modelBuilder = ConverterExtensions.getModelBuilder(defaultConfig);
		} catch (CoreException e) {
			throw new ConversionException(Messages.getString("ConvertUtil.cannotLoadBuilderExtension") + defaultConfig, //$NON-NLS-1$
					e);
		}
		if (modelBuilder != null) {
			configModel = modelBuilder.transformConfigModel(xpdlActivity, context.getParticipantMap());
			if (Tracing.ENABLED)
				Tracing.trace("Config model is " + configModel); //$NON-NLS-1$
		}
		return configModel;
	}

}
