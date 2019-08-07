package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.Scope;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.bpelExtension.extensions.AdhocEvent;
import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.ExtensionsPackage;
import com.tibco.bx.bpelExtension.extensions.OnReceiveEvent;
import com.tibco.bx.bpelExtension.extensions.ParameterDescription;
import com.tibco.bx.bpelExtension.extensions.ParameterMode;
import com.tibco.bx.bpelExtension.extensions.Parameters;
import com.tibco.bx.bpelExtension.extensions.SignalEvent;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerParentTask;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerTask;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.bpm.om.BPMProcessOrgModelUtil;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.InitializerActivitiesType;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

public class ConvertEventHandlers {

	private Map<String, Set<String>> eventInitializers = new HashMap<String, Set<String>>();
    
	public void convertEventHandlers(ConverterContext context,
			List<AnalyzerTask> eventHandlerTasks,
			org.eclipse.bpel.model.EventHandler bpelEventHandler) {
		for (AnalyzerTask eventHandlerTask: eventHandlerTasks) {
			if (eventHandlerTask.isAdhocActivity()) {
				try {
					OnReceiveEvent onReceive = convertAdHocActivity(context, eventHandlerTask);
					if (onReceive != null) {
						bpelEventHandler.addExtensibilityElement(onReceive);
					}
				} catch (ConversionException e) {
					context.logError(Messages.getString("ConvertProcess.cannotConvertAdhocActivity") + e.getMessage(), e); //$NON-NLS-1$
				}
				continue;
			}
			
			Activity xpdlActivity = eventHandlerTask.getXpdlActivity();
			TriggerType triggerType;
			IntermediateEvent intermediateEvent = null;
			StartEvent startEvent = null;

			if (eventHandlerTask.isEventSubProcess()) {
				AnalyzerTask starter = ((AnalyzerParentTask)eventHandlerTask).getStartEvent();
				starter.setEventHandlerBody((AnalyzerParentTask)eventHandlerTask);
				eventHandlerTask = starter;
				xpdlActivity = starter.getXpdlActivity();
				if (!(xpdlActivity.getEvent() instanceof StartEvent)) {
					continue;
				}
				startEvent = (StartEvent)xpdlActivity.getEvent();
				triggerType = startEvent.getTrigger();
			} else {
				if (!(xpdlActivity.getEvent() instanceof IntermediateEvent)) {
					continue;
				}
				intermediateEvent = (IntermediateEvent)xpdlActivity.getEvent();
				triggerType = intermediateEvent.getTrigger();	
			}
				
			switch (triggerType.getValue()) {
			case TriggerType.NONE:
				try {
				    //In SCE business process event handlers are incoming request activities.
				    if(!context.isPageFlowEngineTarget()) {
				        OnEvent onEvent = convertIncomingRequestEventHandler(context, eventHandlerTask);
				        if (onEvent!=null) {
				            bpelEventHandler.getEvents().add(onEvent);
				        }				        
				    } else {
				        //For pageflow processes the old way of NONE event handing is used.
				        OnReceiveEvent onReceive = convertNoneEventHandler(context, eventHandlerTask);
				        bpelEventHandler.addExtensibilityElement(onReceive);
				    }
				} catch (ConversionException e) {
					context.logError(Messages.getString("ConvertProcess.cannotConvertEventHandler") + e.getMessage(), e); //$NON-NLS-1$
				}
				break;
			case TriggerType.MESSAGE:
				TriggerResultMessage messageTrigger;
				if (intermediateEvent != null) {
					messageTrigger = intermediateEvent.getTriggerResultMessage();
				} else {
					messageTrigger = startEvent.getTriggerResultMessage();
				}
				try {
					OnEvent onEvent = convertMessageEventHandler(context, eventHandlerTask, messageTrigger);
					if (onEvent!=null) {
						bpelEventHandler.getEvents().add(onEvent);
					}
				} catch (ConversionException e) {
					context.logError(Messages.getString("ConvertProcess.cannotConvertEventHandler") + e.getMessage(), e); //$NON-NLS-1$
			    }
				break;
			case TriggerType.ERROR:
				break;
			case TriggerType.TIMER:
				break;
			case TriggerType.SIGNAL:
				TriggerResultSignal signalTrigger;
				if (intermediateEvent != null) {
					signalTrigger = intermediateEvent.getTriggerResultSignal();
				} else {
					signalTrigger = startEvent.getTriggerResultSignal();
				}
				try {
					OnReceiveEvent catchSignalEvent = convertCatchSignalEvent(context, eventHandlerTask, signalTrigger);
					if (catchSignalEvent != null) {
						bpelEventHandler.addExtensibilityElement(catchSignalEvent);
					}
				} catch (ConversionException e) {
					context.logError(Messages.getString("ConvertProcess.cannotConvertEventHandler") + e.getMessage(), e); //$NON-NLS-1$
			    }
				break;
			case TriggerType.CANCEL:
				try {
					OnReceiveEvent onReceive = convertCancelEventHandler(context, eventHandlerTask);
					bpelEventHandler.addExtensibilityElement(onReceive);
				} catch (ConversionException e) {
					context.logError(Messages.getString("ConvertProcess.cannotConvertEventHandler") + e.getMessage(), e); //$NON-NLS-1$
				}
				break;
			case TriggerType.COMPENSATION:
				break;
			case TriggerType.LINK:
				break;
			case TriggerType.MULTIPLE:
				break;
			case TriggerType.CONDITIONAL:
				break;
			}
		}
		
		Set<String> eventInitializerIds = eventInitializers.keySet();
		for (String xpdlId : eventInitializerIds) {
			String[] eventIds = eventInitializers.get(xpdlId).toArray(new String[0]);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < eventIds.length; i++) {
				sb.append(eventIds[i]);
				if (i < eventIds.length-1) {
					sb.append(","); //$NON-NLS-1$
				}
			}
			ExtensibleElement bpelElement = context.getBpelElementByXpdlId(xpdlId);
			BPELUtils.addExtensionAttribute(bpelElement, "arm", sb.toString()); //$NON-NLS-1$
		}
	}

	/**
     * @param context
     * @param eventHandlerTask
     * @return
     */
    private OnEvent convertIncomingRequestEventHandler(ConverterContext context, AnalyzerTask eventHandlerTask)
            throws ConversionException {
        OnEvent onEvent = BPELFactory.eINSTANCE.createOnEvent();

        Activity xpdlActivity = eventHandlerTask.getXpdlActivity();

        // SCE: Default message correlation timeout is no longer configurable by the user.
        // See: XPDLUtils.getMessageTimeout(xpdlActivity);
        BPELUtils.addExtensionAttribute(onEvent, "messageTimeout", context.getDefaultIncomingRequestTimeout()); //$NON-NLS-1$

        context.syncXpdlId(onEvent, xpdlActivity);
        
        org.eclipse.bpel.model.Activity theMappingActivity = createIncomingRequestStartActivity(context);
        org.eclipse.bpel.model.Activity eventHandlerBody;
        org.eclipse.bpel.model.Flow flow;
        boolean isAScope = false;

        if (eventHandlerTask.getEventHandlerBody().isEventSubProcess()) {
            eventHandlerBody = ConvertProcess.convertTaskToBpel(context, eventHandlerTask.getEventHandlerBody());
            if (eventHandlerBody instanceof org.eclipse.bpel.model.Scope) {
                isAScope = true;
                flow = (Flow) ((org.eclipse.bpel.model.Scope) eventHandlerBody).getActivity();
            } else {
                flow = (org.eclipse.bpel.model.Flow) eventHandlerBody;
            }
            eventSubProcItems(context, flow, eventHandlerTask);

            AnalyzerTask starter = ((AnalyzerParentTask) eventHandlerTask.getEventHandlerBody()).getStartEvent();
            StartEvent startEvent = (StartEvent) (starter.getXpdlActivity()).getEvent();
            boolean nonInterrupting = XPDLUtils.isNonInterruptingEvent(startEvent);
            if (!nonInterrupting) {
                BPELUtils.addExtensionAttribute(onEvent, "pauseMainFlow", "yes"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } else {
            flow = ConvertProcess.convertFlowToBpel(context, eventHandlerTask.getEventHandlerBody());
            eventHandlerBody = flow;
        }

        if (theMappingActivity != null) {
            // replace starting activity if body with this assign
            List<org.eclipse.bpel.model.Activity> rootActivities = BPELUtils.getRootActivities(flow);
            for (org.eclipse.bpel.model.Activity rootActivity : rootActivities) {
                if (xpdlActivity.getId().equals(BPELUtils.getXpdlId(rootActivity))) {
                    // matches. this is the one to replace
                    theMappingActivity.setName(rootActivity.getName());
                    List<org.eclipse.bpel.model.Link> outLinks = BPELUtils.getLinksFromActivity(rootActivity);
                    for (org.eclipse.bpel.model.Link outLink : outLinks) {
                        ConvertControlFlow.replaceLinkSource(context, outLink, theMappingActivity);
                    }
                    List<org.eclipse.bpel.model.Activity> flowActivities =
                            (List<org.eclipse.bpel.model.Activity>) flow.getActivities();
                    flowActivities.remove(rootActivity);

                    /*
                     * Sid BX-3712 Transfer the arm attribute to the assign activity (else goes missing).
                     */
                    String armAttribute = BPELUtils.getExtensionAttribute(rootActivity, "arm"); //$NON-NLS-1$

                    if (armAttribute != null) {
                        BPELUtils.addExtensionAttribute(theMappingActivity, "arm", armAttribute);
                    }

                    flowActivities.add(theMappingActivity);
                }
            }
            
            //TODO ???
            if (eventHandlerTask.getEventHandlerBody().isEventSubProcess()) {
                BPELUtils.setType(theMappingActivity, N2PEConstants.MESSAGE_START_EVENT_TYPE);
            } else {
                BPELUtils.setType(theMappingActivity, N2PEConstants.CATCH_MESSAGE_INTERMEDIATE_EVENT_TYPE);
            }
            BPELUtils.setLabel(theMappingActivity, xpdlActivity);
            context.syncXpdlId(theMappingActivity, xpdlActivity);
            // set task scripts
            final int SCRIPT_COMPLETED_ONLY = 2;
            ConvertProcess.convertTaskScripts(context, theMappingActivity, xpdlActivity, SCRIPT_COMPLETED_ONLY);
        }

        if (!isAScope) {
            String scopeName = context.generateActivityName("scope", xpdlActivity.getId(), xpdlActivity.getId());
            Scope scope = BPELUtils.wrapInScope(flow, scopeName);
            onEvent.setActivity(scope);
        } else {
            onEvent.setActivity(eventHandlerBody);
        }

         EventHandlerFlowStrategy flowStrategy = XPDLUtils.getEventHandlerFlowStrategy(xpdlActivity.getEvent());
         if (EventHandlerFlowStrategy.SERIALIZE_CONCURRENT.equals(flowStrategy)) {
             BPELUtils.addExtensionAttribute(onEvent, N2PEConstants.EVENTHANDLER_BLOCK_UNTIL_COMPLETED, "yes"); //$NON-NLS-1$
         }

        return onEvent;
    }

    /**
     * Creates start activity for the incoming request flows.
     * 
     * @param context the converter context
     * @return starting activity for the incoming request.
     */
    private org.eclipse.bpel.model.Activity createIncomingRequestStartActivity(ConverterContext context) {
        Assign assign = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
        assign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$
        return assign;
    }

    private static OnReceiveEvent convertNoneEventHandler(ConverterContext context, AnalyzerTask eventHandlerTask) throws ConversionException {
		Activity xpdlActivity = eventHandlerTask.getXpdlActivity();
    	OnReceiveEvent onReceiveEvent = ExtensionsFactory.eINSTANCE.createOnReceiveEvent();
        onReceiveEvent.setElementType(new QName(ExtensionsPackage.eNS_URI, "OnReceiveEvent")); //$NON-NLS-1$

        org.eclipse.bpel.model.Activity eventHandlerBody;
        org.eclipse.bpel.model.Flow flow;
        boolean isAScope = false;

		String eventId = xpdlActivity.getName();
		if (eventHandlerTask.getEventHandlerBody().isEventSubProcess()) {
            eventHandlerBody = ConvertProcess.convertTaskToBpel(context, eventHandlerTask.getEventHandlerBody());
            if (eventHandlerBody instanceof org.eclipse.bpel.model.Scope) {
                isAScope = true;
                flow = (Flow) ((org.eclipse.bpel.model.Scope) eventHandlerBody).getActivity();
            } else {
                flow = (org.eclipse.bpel.model.Flow) eventHandlerBody;
            }
            eventSubProcItems(context, flow, eventHandlerTask);
            eventId = eventHandlerTask.getEventHandlerBody().getStartEvent().getName(); //use start event
		} else {
            flow = ConvertProcess.convertFlowToBpel(context, eventHandlerTask.getEventHandlerBody());
            eventHandlerBody = flow;
        }
        onReceiveEvent.setActivity(eventHandlerBody);
		com.tibco.bx.bpelExtension.extensions.IntermediateEvent ie = ExtensionsFactory.eINSTANCE.createIntermediateEvent();	
		ie.setEventId(eventId);
    	onReceiveEvent.setEventSource(ie);
    	
    	// add parameter info
        Collection<ActivityInterfaceData> xx = ActivityInterfaceDataUtil.getActivityInterfaceData(xpdlActivity);
        Parameters parameters = ExtensionsFactory.eINSTANCE.createParameters();
        List parametersList = parameters.getParameters();
        for (ActivityInterfaceData interfaceData: xx) {
            ParameterDescription parmDescription = ExtensionsFactory.eINSTANCE.createParameterDescription();
            parmDescription.setName(interfaceData.getName());
        	ModeType mode = interfaceData.getMode();
            if (mode.equals(ModeType.IN_LITERAL)) parmDescription.setMode(ParameterMode.IN_LITERAL);
            if (mode.equals(ModeType.INOUT_LITERAL)) parmDescription.setMode(ParameterMode.IN_OUT_LITERAL);
            if (mode.equals(ModeType.OUT_LITERAL)) parmDescription.setMode(ParameterMode.OUT_LITERAL);
            parmDescription.setMandatory(interfaceData.isMandatory());
            parametersList.add(parmDescription);
        }
        if (parametersList.size()>0) {
        	onReceiveEvent.getExtensibilityElements().add(parameters);
        }
    	
		return onReceiveEvent;
	}

	private static OnReceiveEvent convertCancelEventHandler(ConverterContext context, AnalyzerTask eventHandlerTask) throws ConversionException {
		Activity xpdlActivity = eventHandlerTask.getXpdlActivity();
    	OnReceiveEvent onReceiveEvent = ExtensionsFactory.eINSTANCE.createOnReceiveEvent();
        onReceiveEvent.setElementType(new QName(ExtensionsPackage.eNS_URI, "OnReceiveEvent")); //$NON-NLS-1$
        
        org.eclipse.bpel.model.Flow flow = ConvertProcess.convertFlowToBpel(context, eventHandlerTask.getEventHandlerBody());       
        onReceiveEvent.setActivity(flow);
		com.tibco.bx.bpelExtension.extensions.IntermediateEvent ie = ExtensionsFactory.eINSTANCE.createIntermediateEvent();	
		ie.setEventId("$onCancel");
    	onReceiveEvent.setEventSource(ie);
		return onReceiveEvent;
	}
	
	private OnEvent convertMessageEventHandler(ConverterContext context, AnalyzerTask eventHandlerTask, TriggerResultMessage triggerResultMessage) throws ConversionException {
		OnEvent onEvent = BPELFactory.eINSTANCE.createOnEvent();

		Activity xpdlActivity = eventHandlerTask.getXpdlActivity();

		Message message = triggerResultMessage.getMessage();
		WebServiceOperation webServiceOperation = triggerResultMessage.getWebServiceOperation();

		WebServiceOperationInfo wsoInfo = null;
		try {
			wsoInfo = context.getWebServiceOperationInfo(webServiceOperation);
		} catch (ConversionException e) {
			context.logError(Messages.getString("ConvertActivity.cannotConvertTaskReceive") + e.getMessage(), e); //$NON-NLS-1$
			return null;
		}

    	Long seconds = XPDLUtils.getMessageTimeout(xpdlActivity);
        if (seconds>0) {
            BPELUtils.addExtensionAttribute(onEvent, "messageTimeout", seconds.toString());
        }
        
        // Correlate immediate
        boolean correlateImmediate = XPDLUtils.getCorrelateImmediately(triggerResultMessage);
        if(correlateImmediate) {
        	BPELUtils.addExtensionAttribute(onEvent, N2PEConstants.CORRELATE_IMMEDIATE, "yes");
        }
        
		ConvertCorrelations.convert(context, xpdlActivity, onEvent, wsoInfo, message);

		org.eclipse.bpel.model.PartnerLink partnerLink = wsoInfo.createPartnerLinkForWebService();
		onEvent.setPartnerLink(partnerLink);
		context.addPartnerLink(partnerLink);
		onEvent.setPortType(wsoInfo.createPortType());
		onEvent.setOperation(wsoInfo.createOperation());
		context.syncXpdlId(onEvent, xpdlActivity);        

		/** XPD-8010: ConvertDataMapping - activity for mapping assignments is no longer always an <assign> */
		org.eclipse.bpel.model.Activity theMappingActivity = new ConvertDataMapping(context, xpdlActivity, wsoInfo).convertDataMappingsToActivity(message, true);
        org.eclipse.bpel.model.Activity eventHandlerBody;
        org.eclipse.bpel.model.Flow flow;
        boolean isAScope = false;

		if (eventHandlerTask.getEventHandlerBody().isEventSubProcess()) {
            eventHandlerBody = ConvertProcess.convertTaskToBpel(context, eventHandlerTask.getEventHandlerBody());
            if (eventHandlerBody instanceof org.eclipse.bpel.model.Scope) {
                isAScope = true;
                flow = (Flow) ((org.eclipse.bpel.model.Scope) eventHandlerBody).getActivity();
            } else {
                flow = (org.eclipse.bpel.model.Flow) eventHandlerBody;
            }
            eventSubProcItems(context, flow, eventHandlerTask);

			AnalyzerTask starter = ((AnalyzerParentTask)eventHandlerTask.getEventHandlerBody()).getStartEvent();
        	StartEvent startEvent = (StartEvent) (starter.getXpdlActivity()).getEvent();
        	boolean nonInterrupting = XPDLUtils.isNonInterruptingEvent(startEvent);
        	if (!nonInterrupting) {
        		BPELUtils.addExtensionAttribute(onEvent, "pauseMainFlow", "yes");  //$NON-NLS-1$
        	}
		} else {
            flow = ConvertProcess.convertFlowToBpel(context, eventHandlerTask.getEventHandlerBody());
            eventHandlerBody = flow;
        }

		if (theMappingActivity != null) {
			// replace starting activity if body with this assign
			List<org.eclipse.bpel.model.Activity> rootActivities = BPELUtils.getRootActivities(flow);
			for (org.eclipse.bpel.model.Activity rootActivity : rootActivities) {
				if (xpdlActivity.getId().equals(BPELUtils.getXpdlId(rootActivity))) {
					// matches. this is the one to replace
					theMappingActivity.setName(rootActivity.getName());
					List<org.eclipse.bpel.model.Link> outLinks = BPELUtils.getLinksFromActivity(rootActivity);
					for (org.eclipse.bpel.model.Link outLink : outLinks) {
						ConvertControlFlow.replaceLinkSource(context, outLink, theMappingActivity);
					}
					List<org.eclipse.bpel.model.Activity> flowActivities = (List<org.eclipse.bpel.model.Activity>) flow.getActivities();
					flowActivities.remove(rootActivity);
					
					/*
					 * Sid BX-3712 Transfer the arm attribute to the assign activity (else goes missing).
					 */
					String armAttribute = BPELUtils.getExtensionAttribute(rootActivity, "arm"); //$NON-NLS-1$
		
					if (armAttribute != null) {
						BPELUtils.addExtensionAttribute(theMappingActivity, "arm", armAttribute);
					}
					
					flowActivities.add(theMappingActivity);
					
					// FIXME ??  shouldn't we break here?
					
					// FIXME ?? I notice below that rather than grabbing the
					// extensions (like xpdlid and more importantly activity
					// scripts etc) that we simply re-convert everything again
					// into the assign activity. Seems a bit wasteful
					// (especially as activity scripts etc may now need
					// conversion etc.  
					// 
					// In my implementation I have transferred the attribute itself across rather than 
					// recalculating the value from scratch - is there any reason this shouldn't be done?
					
				}
			}
			if (eventHandlerTask.getEventHandlerBody().isEventSubProcess()) {
				BPELUtils.setType(theMappingActivity, N2PEConstants.MESSAGE_START_EVENT_TYPE);
			} else {
				BPELUtils.setType(theMappingActivity, N2PEConstants.CATCH_MESSAGE_INTERMEDIATE_EVENT_TYPE);
			}
			BPELUtils.setLabel(theMappingActivity, xpdlActivity);
			context.syncXpdlId(theMappingActivity, xpdlActivity);
			// set task scripts
            ConvertProcess.convertTaskScripts(context, theMappingActivity, xpdlActivity, 2);		
		}

		org.eclipse.bpel.model.Variable inputVar = wsoInfo.createInputVariable();
		if (inputVar != null) {
			//BX-2598: Don't add the variable to the context since it'll be local to the scope
//			context.addVariable(inputVar); 
//			
//			Copier copier = new Copier();
//			org.eclipse.bpel.model.Variable varCopy = (Variable) copier.copy(inputVar);
//			copier.copyReferences();
			onEvent.setVariable(inputVar);
		}

        if (!isAScope) {
            String scopeName = context.generateActivityName("scope", xpdlActivity.getId(), xpdlActivity.getId());
            Scope scope = BPELUtils.wrapInScope(flow, scopeName);
            onEvent.setActivity(scope);
        } else {
            onEvent.setActivity(eventHandlerBody);
        }
		
		EventHandlerFlowStrategy flowStrategy = XPDLUtils.getEventHandlerFlowStrategy(triggerResultMessage);
		if (EventHandlerFlowStrategy.SERIALIZE_CONCURRENT.equals(flowStrategy)) {
			BPELUtils.addExtensionAttribute(onEvent, N2PEConstants.EVENTHANDLER_BLOCK_UNTIL_COMPLETED, "yes"); //$NON-NLS-1$
		}
		
		EventHandlerInitialisers eventHandlerInitialisers = XPDLUtils.getEventHandlerInitialisers(triggerResultMessage);
		if (eventHandlerInitialisers != null) {
			BPELUtils.addExtensionAttribute(onEvent, "delayedStart", "yes"); //$NON-NLS-1$ //$NON-NLS-2$
			EList<ActivityRef> activityRefs = eventHandlerInitialisers.getActivityRef();
			for (ActivityRef activityRef : activityRefs) {
				addEventInitializer(activityRef.getIdRef(), xpdlActivity.getId());
			}
		}
		
		return onEvent;
	}

    private OnReceiveEvent convertCatchSignalEvent(ConverterContext context, AnalyzerTask eventHandlerTask, TriggerResultSignal signalTrigger) throws ConversionException {
    	Activity xpdlActivity = eventHandlerTask.getXpdlActivity(); 
    	OnReceiveEvent onReceiveEvent = ExtensionsFactory.eINSTANCE.createOnReceiveEvent();
        onReceiveEvent.setElementType(new QName(ExtensionsPackage.eNS_URI, "OnReceiveEvent")); //$NON-NLS-1$
        
        context.syncXpdlId(onReceiveEvent, xpdlActivity); 
    	
        org.eclipse.bpel.model.Activity eventHandlerBody;
        
        if (eventHandlerTask.getEventHandlerBody().isEventSubProcess()) {
        	onReceiveEvent.setName(context.generateActivityName(xpdlActivity.getName(), "_", xpdlActivity.getId() )); //$NON-NLS-1$

        	AnalyzerTask starter = ((AnalyzerParentTask)eventHandlerTask.getEventHandlerBody()).getStartEvent();
        	StartEvent startEvent = (StartEvent) (starter.getXpdlActivity()).getEvent();
        	boolean nonInterrupting = XPDLUtils.isNonInterruptingEvent(startEvent);
        	onReceiveEvent.setPauseMainFlow(!nonInterrupting);
        	eventHandlerBody = ConvertProcess.convertTaskToBpel(context, eventHandlerTask.getEventHandlerBody());
    		
        } else {
        	onReceiveEvent.setName(xpdlActivity.getName());
        	boolean async = XPDLUtils.getSignalHandlerAsynchronous(signalTrigger);
        	onReceiveEvent.setWaitForCompletion(!async);
        	eventHandlerBody = ConvertProcess.convertFlowToBpel(context, eventHandlerTask.getEventHandlerBody());
        }
        
        EObject eventSource;
        if (XPDLUtils.isGlobalSignalType(signalTrigger)) {
        	com.tibco.xpd.globalSignalDefinition.GlobalSignal gSignal = GlobalSignalUtil.getReferencedGlobalSignal(xpdlActivity);
        	eventSource = ConvertEvent.createGlobalSignal(gSignal, signalTrigger);
        	ConvertEvent.attachGlobalSignalMappings(gSignal, onReceiveEvent, signalTrigger, xpdlActivity);
        } else if (XPDLUtils.isCaseDataSignalType(signalTrigger)) {
        	eventSource = ConvertEvent.createCaseDataSignal(signalTrigger);
        } else {
        	eventSource = ExtensionsFactory.eINSTANCE.createSignalEvent();
        	((SignalEvent) eventSource).setEvent(signalTrigger.getName());
        }
        onReceiveEvent.setEventSource(eventSource);
    	
    	EventHandlerFlowStrategy flowStrategy = XPDLUtils.getEventHandlerFlowStrategy(signalTrigger);
		if (EventHandlerFlowStrategy.SERIALIZE_CONCURRENT.equals(flowStrategy)) {
			BPELUtils.addExtensionAttribute(onReceiveEvent, N2PEConstants.EVENTHANDLER_BLOCK_UNTIL_COMPLETED, "yes"); //$NON-NLS-1$
		}
		
		EventHandlerInitialisers eventHandlerInitialisers = XPDLUtils.getEventHandlerInitialisers(signalTrigger);
		if (eventHandlerInitialisers != null) {
			BPELUtils.addExtensionAttribute(onReceiveEvent, "delayedStart", "yes"); //$NON-NLS-1$ //$NON-NLS-2$
			EList<ActivityRef> activityRefs = eventHandlerInitialisers.getActivityRef();
			for (ActivityRef activityRef : activityRefs) {
				addEventInitializer(activityRef.getIdRef(), xpdlActivity.getId());
			}
		}
    	
		//org.eclipse.bpel.model.Flow eventHandlerBody = ConvertProcess.convertFlowToBpel(context, eventHandlerTask.getEventHandlerBody());
		boolean isAScope = false;
    	if (eventHandlerTask.getEventHandlerBody().isEventSubProcess()) {
	    	org.eclipse.bpel.model.Flow flow;
			if (eventHandlerBody instanceof org.eclipse.bpel.model.Scope) {
				isAScope = true;
				flow = (Flow) ((org.eclipse.bpel.model.Scope)eventHandlerBody).getActivity();
			}  else {
				flow = (org.eclipse.bpel.model.Flow)eventHandlerBody;
			}
			eventSubProcItems(context, flow, eventHandlerTask);
		}	
		
    	if (!isAScope) {
    		String scopeName = context.generateActivityName("scope", xpdlActivity.getId(), xpdlActivity.getId()); //$NON-NLS-1$
    		Scope scope = BPELUtils.wrapInScope(eventHandlerBody, scopeName);
    		onReceiveEvent.setActivity(scope);
    	} else {
    		onReceiveEvent.setActivity(eventHandlerBody);
    	}
        return onReceiveEvent;
    }
    
    private static void eventSubProcItems(ConverterContext context, org.eclipse.bpel.model.Flow eventHandlerBody, AnalyzerTask eventHandlerTask) {
    	Activity subProcXpdlActivity = eventHandlerTask.getEventHandlerBody().getXpdlActivity();
		BPELUtils.setType(eventHandlerBody, N2PEConstants.EVENT_SUB_PROCESS_TYPE);
        BPELUtils.setLabel(eventHandlerBody, subProcXpdlActivity);
        context.syncXpdlId(eventHandlerBody, subProcXpdlActivity);
        // set description
    	Description description = subProcXpdlActivity.getDescription();
    	if (description!=null) {
    		BPELUtils.setDocumentation(eventHandlerBody, description.getValue());
    	}
    	// set task scripts
    	ConvertProcess.convertTaskScripts(context, eventHandlerBody, subProcXpdlActivity, 2);
    }

	private OnReceiveEvent convertAdHocActivity(ConverterContext context, AnalyzerTask eventHandlerTask) throws ConversionException {
		Activity xpdlActivity = eventHandlerTask.getXpdlActivity(); 
    	OnReceiveEvent onReceiveEvent = ExtensionsFactory.eINSTANCE.createOnReceiveEvent();
        onReceiveEvent.setElementType(new QName(ExtensionsPackage.eNS_URI, "OnReceiveEvent")); //$NON-NLS-1$
		String eventName = context.generateActivityName("AdHoc", xpdlActivity.getName(), xpdlActivity.getId()); //$NON-NLS-1$
        onReceiveEvent.setName(eventName); 
        AdhocEvent adHocEvent = ExtensionsFactory.eINSTANCE.createAdhocEvent();
        AdHocTaskConfigurationType config = XPDLUtils.getAdHocTaskConfiguration(xpdlActivity);   
        AdHocExecutionTypeType adHocExecutionType = config.getAdHocExecutionType();
        EnablementType enablement = config.getEnablement();
        adHocEvent.setAutomatic(AdHocExecutionTypeType.AUTOMATIC.equals(adHocExecutionType));
        if (enablement!=null) {
        	com.tibco.xpd.xpdl2.Expression xpdExpr = enablement.getPreconditionExpression();
        	if (xpdExpr!=null) {
        		com.tibco.bx.bpelExtension.extensions.Expression expr = ExtensionsFactory.eINSTANCE.createExpression();
        		expr.setLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
        		expr.setText(xpdExpr.getText());
        		adHocEvent.setPreConditionExpression(expr);
        	}
        }
       
        if (adHocEvent.isAutomatic()) {
        	adHocEvent.setOnlyOnce(false);
        } else {
        	adHocEvent.setOnlyOnce(!config.isAllowMultipleInvocations());
        }
        
        List privileges = adHocEvent.getPrivileges();
        RequiredAccessPrivileges priv = config.getRequiredAccessPrivileges();
        if (priv!=null) {
        	List<ExternalReference> xRefs = priv.getPrivilegeReference();
        	if (xRefs != null) {
        		for (ExternalReference xRef: xRefs) {
        			privileges.add(xRef.getXref());
        		}
        	}

        	// if not already done, set org model version
        	if (context.getAnalyzer().getOrgModelVersion()==null) {
        		List<ExternalReference> ref = priv.getPrivilegeReference();
        		if (ref!=null && ref.size()>0) {
        			ModelElement model = com.tibco.xpd.bpm.om.BPMProcessOrgModelUtil.getOMModelElement(ref.get(0));
        			if (model!=null) {
        				Integer orgModelVersion = BPMProcessOrgModelUtil.getOMModelVersion(model);
        				context.getAnalyzer().setOrgModelVersion(orgModelVersion);
        			}
        		}
        	}
        }

        onReceiveEvent.setPauseMainFlow(config.isSuspendMainFlow());
    	onReceiveEvent.setEventSource(adHocEvent);

    	if (enablement!=null) {
    		InitializerActivitiesType initializers = enablement.getInitializerActivities();        
    		if (initializers != null) {
    			EList<ActivityRef> activityRefs = initializers.getActivityRef();
    			for (ActivityRef activityRef : activityRefs) {
    				addEventInitializer(activityRef.getIdRef(), xpdlActivity.getId());
    			}
    		}
    	}
    	
		org.eclipse.bpel.model.Activity bpelActivity;
		if (XPDLUtils.isLoopActivity(eventHandlerTask.getXpdlActivity())) {
			bpelActivity = ConvertProcess.convertTaskToBpel(context, eventHandlerTask);
			eventHandlerTask.setBpelReference(bpelActivity);
			bpelActivity = ConvertLoops.convertLoop(context, null, eventHandlerTask, null);
			eventHandlerTask.setBpelReference(bpelActivity);
		} else {
			bpelActivity = ConvertProcess.convertTaskToBpel(context, eventHandlerTask);
		}
    	onReceiveEvent.setActivity(bpelActivity);
    	
		XPDLUtils.findReferencedDataInScript(context, xpdlActivity, xpdlActivity.getName(), DataReferenceContext.CONTEXT_ADHOC_PRECONDITION);

        return onReceiveEvent;			
	}

    public void addEventInitializer(String activityId, String eventId) {
    	Set<String> eventIds = eventInitializers.get(activityId);
    	if (eventIds == null) {
    		eventIds = new HashSet<String>();
    		eventInitializers.put(activityId, eventIds);
    	}
    	eventIds.add(eventId);
    }
    
     
    
}
