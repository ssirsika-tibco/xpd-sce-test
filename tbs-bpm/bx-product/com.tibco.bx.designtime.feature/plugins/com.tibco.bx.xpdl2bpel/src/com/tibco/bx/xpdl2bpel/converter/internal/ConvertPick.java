package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.OnAlarm;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Pick;

import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.ExtensionsPackage;
import com.tibco.bx.bpelExtension.extensions.OnReceiveEvent;
import com.tibco.bx.bpelExtension.extensions.ReplyImmediate;
import com.tibco.bx.bpelExtension.extensions.SignalEvent;
import com.tibco.bx.bpelExtension.extensions.StartEventType;
import com.tibco.bx.bpelExtension.extensions.TimerEvent;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerParentTask;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerTask;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;

public class ConvertPick {

	public static final String PICK_PREFIX = "pick"; //$NON-NLS-1$
	public static final String SEQUENCE_PREFIX = "sequence"; //$NON-NLS-1$
	public static final String EMPTY_PREFIX = "empty"; //$NON-NLS-1$
	public static final String SCOPE_PREFIX = "scope"; //$NON-NLS-1$
	
	public static final String CTRL_VAR_PREFIX = "control_"; //$NON-NLS-1$

    public static Pick convertPick(ConverterContext context, AnalyzerParentTask pick) throws ConversionException {
        Flow bpelFlow = (Flow)pick.getParent().getBpelReference();
        List<org.eclipse.bpel.model.Activity> flowActivities = (List<org.eclipse.bpel.model.Activity>) bpelFlow.getActivities();

        Pick bpelPick = BPELFactory.eINSTANCE.createPick();
        bpelPick.setName(context.genUniqueActivityName(PICK_PREFIX)); //$NON-NLS-1$
        bpelPick.setCreateInstance(pick.isCreateInstance());
        flowActivities.add(bpelPick);

        // For each task in the pick, which can be either a start event or receive task,
        // create a corresponding event handler with a body.
        // The body carries the name and xpdl id of the task. 
        // It will be an assign for a message and an empty activity for the rest.
        // The body also carries the single task out link that crosses the pick boundary.
        for (AnalyzerTask task: pick.getChildren()) {
            com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
            // could either be a start event or intermediate event
            Event event = xpdlActivity.getEvent();

            if (event instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) event;
                TriggerType triggerType = startEvent.getTrigger();
                switch (triggerType.getValue()) {
                    case TriggerType.NONE:
                        // use onStartEvent
                        convertStartEvent(context, task, bpelPick, N2PEConstants.START_EVENT_TYPE);
                        break;
                    case TriggerType.MESSAGE:
                        // use OnMessage
                    	TriggerResultMessage triggerResultMessage = startEvent.getTriggerResultMessage();
                        Message message = triggerResultMessage.getMessage();
                        WebServiceOperation webServiceOperation = triggerResultMessage.getWebServiceOperation();
                    	makeOnMessage(context, task, bpelPick, message, webServiceOperation, N2PEConstants.MESSAGE_START_EVENT_TYPE, XPDLUtils.isReplyImmediately(triggerResultMessage));
                        break;
                    case TriggerType.TIMER:
                        // use onReceiveEvent
                    	makeOnTimer(context, task, bpelPick, startEvent.getTriggerTimer(), N2PEConstants.TIMER_START_EVENT_TYPE);
//                        convertTimerStartEvent(context, task, bpelPick, startEvent.getTriggerTimer());
                        break;
                    case TriggerType.SIGNAL:
                        //todo for future support
                    	makeOnSignal(context, task, bpelPick, startEvent.getTriggerResultSignal(), N2PEConstants.SIGNAL_START_EVENT_TYPE);
                        break;
                }
            } else if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediateEvent = (IntermediateEvent) event;
                TriggerType triggerType = intermediateEvent.getTrigger();
                switch (triggerType.getValue()) {
                    case TriggerType.NONE:
                        // ACE-2017: Incoming Request Activity
                        makeOnMessageInRequest(context, task, bpelPick, N2PEConstants.CATCH_MESSAGE_INTERMEDIATE_EVENT_TYPE, /*isReplyImmediately*/ false);
                        break;
                    case TriggerType.MESSAGE:
                        // use OnMessage
                    	TriggerResultMessage triggerResultMessage = intermediateEvent.getTriggerResultMessage();
                        Message message = triggerResultMessage.getMessage();
                        WebServiceOperation webServiceOperation = triggerResultMessage.getWebServiceOperation();
                    	makeOnMessage(context, task, bpelPick, message, webServiceOperation, N2PEConstants.CATCH_MESSAGE_INTERMEDIATE_EVENT_TYPE, false);
                        break;
                    case TriggerType.TIMER:
                        if (pick.isCreateInstance()) {
                            // use onStartEvent
                        	makeOnTimer(context, task, bpelPick, intermediateEvent.getTriggerTimer(), N2PEConstants.CATCH_TIMER_INTERMEDIATE_EVENT_TYPE);
//                            convertTimerStartEvent(context, task, bpelPick, intermediateEvent.getTriggerTimer());
                        } else {
                            // use OnAlarm
                            makeOnAlarm(context, task, bpelPick);
                        }
                        break;
                    case TriggerType.SIGNAL:
                        if (pick.isCreateInstance()) {
                            // use onSignalEvent
                            //todo not supported yet
                            makeOnSignal(context, task, bpelPick, intermediateEvent.getTriggerResultSignal(), N2PEConstants.CATCH_SIGNAL_INTERMEDIATE_EVENT_TYPE);
                        } else {
                            // use onSignalEvent
                            makeOnSignal(context, task, bpelPick, intermediateEvent.getTriggerResultSignal(), N2PEConstants.CATCH_SIGNAL_INTERMEDIATE_EVENT_TYPE);
                        }
                        break;
                }
            } else if (xpdlActivity.getImplementation() instanceof com.tibco.xpd.xpdl2.Task) {
            	//must be receive activity
            	com.tibco.xpd.xpdl2.TaskReceive receive = ((Task)xpdlActivity.getImplementation()).getTaskReceive();
            	if (receive!=null) {
            	    // ACE-2017: Incoming Request Activity
            		makeOnMessageInRequest(context, task, bpelPick, N2PEConstants.RECEIVE_TASK_TYPE, /*isReplyImmediately*/ false);
            	}
            }
        }
        return bpelPick;
    }

    private static OnReceiveEvent convertStartEvent(ConverterContext context, AnalyzerTask task, Pick pick, String type) {
        com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
        Activity body = BPELFactory.eINSTANCE.createEmpty();
        body.setName(context.makeUniqueActivityName(xpdlActivity.getName()));
    	if (body.getName() == null || body.getName().length() == 0) {
            context.setActivityName(xpdlActivity, body, "start"); //$NON-NLS-1$
    	}
        task.setBpelReference(body);
        context.syncXpdlId(body, xpdlActivity);	
        BPELUtils.setType(body, type);
        BPELUtils.setLabel(body, xpdlActivity);
        ConvertProcess.convertTaskScripts(context, body, xpdlActivity, 2);
        OnReceiveEvent onReceiveEvent = ExtensionsFactory.eINSTANCE.createOnReceiveEvent();
        onReceiveEvent.setElementType(new QName(ExtensionsPackage.eNS_URI, "OnReceiveEvent")); //$NON-NLS-1$
        com.tibco.bx.bpelExtension.extensions.StartEvent startEvent = ExtensionsFactory.eINSTANCE.createStartEvent();
        if (pick.getCreateInstance()) {
        	startEvent.setEventType(StartEventType.DEFAULT_LITERAL); //$NON-NLS-1$
        }
        onReceiveEvent.setEventSource(startEvent);
        onReceiveEvent.setActivity(body);
        pick.addExtensibilityElement(onReceiveEvent);
        return onReceiveEvent;
    }

    private static void convertTimerStartEvent(ConverterContext context, AnalyzerTask task, Pick pick, TriggerTimer timer) {
        com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
        OnReceiveEvent onReceiveEvent = convertStartEvent(context, task, pick, N2PEConstants.TIMER_START_EVENT_TYPE);
        TimerEvent timerEvent = ExtensionsFactory.eINSTANCE.createTimerEvent();
        onReceiveEvent.setEventSource(timerEvent);
        if (xpdlActivity.getDeadline() != null && !xpdlActivity.getDeadline().isEmpty()) {
		    Deadline deadline = (Deadline)xpdlActivity.getDeadline().get(0);
		    Expression deadlineDuration = deadline.getDeadlineDuration();
		    String scriptGrammar = deadlineDuration.getScriptGrammar();
	        com.tibco.bx.bpelExtension.extensions.Expression expr;
		    if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
		        String durationISO = ConvertEvent.convertConstantPeriod(deadlineDuration);
		        expr = ConvertEvent.createDurationExpression(durationISO);
		    } else {
		        expr = ConvertEvent.createDurationExpression(deadlineDuration.getText());
		    }

			if (timer.getTimeCycle() != null) {
	            timerEvent.setRepeatEveryDuration(expr);
	        } else {//assume TimeDate
				if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
					timerEvent.setDuration(expr);
				} else {
		        	timerEvent.setDeadline(expr);
				}
	        }
		}
    }
    
    private static void makeOnTimer(ConverterContext context, AnalyzerTask task, Pick pick, TriggerTimer timer, String type) {
        com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
        OnReceiveEvent onReceiveEvent = makeOnReceiveEvent(context, task, xpdlActivity, type);
        TimerEvent timerEvent = ExtensionsFactory.eINSTANCE.createTimerEvent();
        if (xpdlActivity.getDeadline() != null && !xpdlActivity.getDeadline().isEmpty()) {
		    Deadline deadline = (Deadline)xpdlActivity.getDeadline().get(0);
		    Expression deadlineDuration = deadline.getDeadlineDuration();
		    String scriptGrammar = deadlineDuration.getScriptGrammar();
	        com.tibco.bx.bpelExtension.extensions.Expression expr;
		    if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
		        String durationISO = ConvertEvent.convertConstantPeriod(deadlineDuration);
		        expr = ConvertEvent.createDurationExpression(durationISO);
		    } else {
		        expr = ConvertEvent.createDurationExpression(deadlineDuration.getText());
		    }

			if (timer.getTimeCycle() != null) {
	            timerEvent.setRepeatEveryDuration(expr);
	        } else {//assume TimeDate
				if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
					timerEvent.setDuration(expr);
				} else {
		        	timerEvent.setDeadline(expr);
				}
	        }
		}
        
        onReceiveEvent.setEventSource(timerEvent);    
        pick.addExtensibilityElement(onReceiveEvent);
    }

	private static OnReceiveEvent makeOnReceiveEvent(ConverterContext context,
			AnalyzerTask task, com.tibco.xpd.xpdl2.Activity xpdlActivity, String type) {
		Activity body = BPELFactory.eINSTANCE.createEmpty();
        body.setName(context.makeUniqueActivityName(xpdlActivity.getName()));
        task.setBpelReference(body);
        context.syncXpdlId(body, xpdlActivity);
        BPELUtils.setType(body, type);
        BPELUtils.setLabel(body, xpdlActivity);
        ConvertProcess.convertTaskScripts(context, body, xpdlActivity, 2);
        OnReceiveEvent onReceiveEvent = ExtensionsFactory.eINSTANCE.createOnReceiveEvent();
        onReceiveEvent.setElementType(new QName(ExtensionsPackage.eNS_URI, "OnReceiveEvent")); //$NON-NLS-1$
        onReceiveEvent.setName(xpdlActivity.getName());   
        onReceiveEvent.setXpdlId(xpdlActivity.getId());
        onReceiveEvent.setActivity(body);
		return onReceiveEvent;
	}

    private static void makeOnMessage(ConverterContext context, AnalyzerTask task, Pick pick, 
    		Message message, WebServiceOperation webServiceOperation, String type, boolean isReplyImmediately) throws ConversionException {
        /*
         * Sid ACE-194 - we don't support message events in ACE
         */
         throw new RuntimeException("Unexpected unsupported message activity in source process.");

        
//        WebServiceOperationInfo wsoInfo = null;
//    	try {
//    		wsoInfo = context.getWebServiceOperationInfo(webServiceOperation);
//    	} catch (ConversionException e) {
//    		context.logError(Messages.getString("ConvertActivity.cannotConvertTaskReceive") + e.getMessage(), e); //$NON-NLS-1$
//    		return;
//    	}
//        OnMessage onMessage = BPELFactory.eINSTANCE.createOnMessage();
//        PartnerLink partnerLink = wsoInfo.createPartnerLinkForWebService();
//        onMessage.setPartnerLink(partnerLink);
//        context.addPartnerLink(partnerLink);
//        onMessage.setPortType(wsoInfo.createPortType());
//        onMessage.setOperation(wsoInfo.createOperation());
//        
//       	Long seconds = XPDLUtils.getMessageTimeout(task.getXpdlActivity());
//        if (seconds>0) {
//            BPELUtils.addExtensionAttribute(onMessage, "messageTimeout", seconds.toString());
//        }
//        
//        // Correlate immediate
//        boolean correlateImmediate;
//        if (task.getXpdlActivity().getEvent()==null) {
//        	//receive task
//            correlateImmediate = XPDLUtils.getCorrelateImmediately(((Task) task.getXpdlActivity().getImplementation()).getTaskReceive());
//        } else {
//        	//receive event
//        	correlateImmediate = XPDLUtils.getCorrelateImmediately((TriggerResultMessage)task.getXpdlActivity().getEvent().getEventTriggerTypeNode());
//        }
//        if(correlateImmediate) {
//        	BPELUtils.addExtensionAttribute(onMessage, N2PEConstants.CORRELATE_IMMEDIATE, "yes");
//        }
//        
//    	ConvertCorrelations.convert(context, task.getXpdlActivity(), onMessage, wsoInfo, message);
//
//    	//ensure that the var name is unique (the onMessage events for the pick may use the same WSDL message)
//        org.eclipse.bpel.model.Variable inputVar = wsoInfo.createInputVariable();
//    	String uniqueName = context.makeUniqueVariableName(inputVar.getName());
//		inputVar.setName(uniqueName);
//
//    	context.addVariable(inputVar);
//        onMessage.setVariable(inputVar);
//
//        /** XPD-8010: ConvertDataMapping - activity for mapping assignments is no longer always an <assign> */
//        org.eclipse.bpel.model.Activity theMappingActivity = new ConvertDataMapping(
//        		context, task.getXpdlActivity(), wsoInfo).convertDataMappingsToActivity(message, true, inputVar.getName());
//        org.eclipse.bpel.model.Activity messageActivity = theMappingActivity;
//        if (messageActivity == null) {
//        	messageActivity = BPELFactory.eINSTANCE.createEmpty();
//        	messageActivity.setName(task.getXpdlActivity().getName());
//        }
//
//        /*
//         * Sid ACE-194 - we don't support XSD based BOMs in ACE
//         */
//
//        if (isReplyImmediately) {
//
//        	ReplyImmediate replyImmediate = ConvertEvent.createReplyImmediate(context, message, wsoInfo);
//        	onMessage.addExtensibilityElement(replyImmediate);
//        }
//
//        context.syncXpdlId(messageActivity, task.getXpdlActivity());
//        BPELUtils.setType(messageActivity, type);	
//        BPELUtils.setLabel(messageActivity, task.getXpdlActivity());
//        ConvertProcess.convertTaskScripts(context, messageActivity, task.getXpdlActivity(), 2);
//        onMessage.setActivity(messageActivity);
//        task.setBpelReference(messageActivity);
//        pick.getMessages().add(onMessage);
    }
    
    /**
     * Makes onMessage event for intermediate incomming request event or task.
     * 
     * @param context
     * @param task
     * @param pick
     * @param type the type of the task.
     * @param isReplyImmediately
     * @throws ConversionException
     */
    private static void makeOnMessageInRequest(ConverterContext context, AnalyzerTask task, Pick pick, 
            String type, boolean isReplyImmediately) throws ConversionException {

        OnMessage onMessage = BPELFactory.eINSTANCE.createOnMessage();
        // SCE: Default message correlation timeout is no longer configurable by the user.
        // See: XPDLUtils.getMessageTimeout(xpdlActivity);
        BPELUtils.addExtensionAttribute(onMessage, "messageTimeout", context.getDefaultIncomingRequestTimeout()); //$NON-NLS-1$
       
        // Sid ACE-2388 Correlate immediate
        boolean correlateImmediate;
        if (task.getXpdlActivity().getEvent()==null) {
            //receive task
            correlateImmediate = XPDLUtils.getCorrelateImmediately(((Task) task.getXpdlActivity().getImplementation()).getTaskReceive());
        } else {
            //receive event
            correlateImmediate = XPDLUtils.getCorrelateImmediately(task.getXpdlActivity().getEvent());
        }
        
        if(correlateImmediate) {
            BPELUtils.addExtensionAttribute(onMessage, N2PEConstants.CORRELATE_IMMEDIATE, "yes");
        }
        
        org.eclipse.bpel.model.Activity assign =  org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
        assign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$

        context.syncXpdlId(assign, task.getXpdlActivity());
        BPELUtils.setType(assign, type);	
        BPELUtils.setLabel(assign, task.getXpdlActivity());
        
        /*
         * Sid ACE-3332 add parameters definitions to the incoming request activity.
         */
        ConvertEventHandlers.addIncomingRequestParameters(task.getXpdlActivity(), assign);
        
        ConvertProcess.convertTaskScripts(context, assign, task.getXpdlActivity(), 2);
        onMessage.setActivity(assign);
        task.setBpelReference(assign);
        pick.getMessages().add(onMessage);
    }

    private static void makeOnSignal(ConverterContext context, AnalyzerTask task, Pick pick, TriggerResultSignal signalTrigger, String type) {
        com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
        OnReceiveEvent onReceiveEvent = makeOnReceiveEvent(context, task, xpdlActivity, type);
        SignalEvent signalEvent = ExtensionsFactory.eINSTANCE.createSignalEvent();
        signalEvent.setEvent(signalTrigger.getName());
        onReceiveEvent.setEventSource(signalEvent);    
        pick.addExtensibilityElement(onReceiveEvent);
    }

    private static void makeOnAlarm(ConverterContext context, AnalyzerTask task, Pick pick) {
        com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
        Activity body = BPELFactory.eINSTANCE.createEmpty();
        body.setName(context.makeUniqueActivityName(xpdlActivity.getName()));
        task.setBpelReference(body);
        context.syncXpdlId(body, xpdlActivity);
        BPELUtils.setType(body, N2PEConstants.CATCH_TIMER_INTERMEDIATE_EVENT_TYPE);
        BPELUtils.setLabel(body, xpdlActivity);
        ConvertProcess.convertTaskScripts(context, body, xpdlActivity, 2);
        OnAlarm onAlarm = BPELFactory.eINSTANCE.createOnAlarm();
        if (xpdlActivity.getDeadline() != null && !xpdlActivity.getDeadline().isEmpty()) {
            Deadline deadline = (Deadline)xpdlActivity.getDeadline().get(0);
            com.tibco.xpd.xpdl2.Expression deadlineDuration = deadline.getDeadlineDuration();
            String scriptGrammar = deadlineDuration.getScriptGrammar();
            org.eclipse.bpel.model.Expression expr;
            if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
                String durationISO = ConvertEvent.convertConstantPeriod(deadlineDuration);
                expr = ConvertEvent.createBPELDurationExpression(durationISO);
            } else {
                expr = ConvertEvent.createBPELDurationExpression(deadlineDuration.getText());
            }

            TriggerTimer timer = ((IntermediateEvent)xpdlActivity.getEvent()).getTriggerTimer();
            if (timer.getTimeCycle() != null) {
                onAlarm.setRepeatEvery(expr);
            } else { //assume TimeDate
                if (ConvertEvent.GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
                    onAlarm.setFor(expr);
                } else {
                    onAlarm.setUntil(expr);
                }
            }
        }
        onAlarm.setActivity(body);
        pick.getAlarm().add(onAlarm);
    } 

}
