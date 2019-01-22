package com.tibco.bx.xpdl2bpel.converter.internal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.CompensateScope;
import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.framework.Version;

import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.GlobalSignal;
import com.tibco.bx.bpelExtension.extensions.GlobalSignalMappings;
import com.tibco.bx.bpelExtension.extensions.ParameterDescription;
import com.tibco.bx.bpelExtension.extensions.ParameterMode;
import com.tibco.bx.bpelExtension.extensions.Parameters;
import com.tibco.bx.bpelExtension.extensions.ReceiveEvent;
import com.tibco.bx.bpelExtension.extensions.ReplyImmediate;
import com.tibco.bx.bpelExtension.extensions.SignalEvent;
import com.tibco.bx.bpelExtension.extensions.StartEventType;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.converter.internal.ConvertDataMapping.DataMappingInfo;
import com.tibco.bx.xpdl2bpel.converter.internal.ConvertDataMapping.PartMappings;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

public class ConvertEvent {

    //private static final String GRAMMAR_JAVA_SCRIPT = "JavaScript"; //$NON-NLS-1$
	public static final String GRAMMAR_CONSTANT_PERIOD = "ConstantPeriod"; //$NON-NLS-1$

	/**
     * Convert XPDL Event Activities to BPEL Activities.
     * @param xpdlActivity The XPDL activity whose event is to be converted.
     * @param processLevel true when processing process level activities versus activity sets
     * @return The converted BPEL Activity. */
    public static org.eclipse.bpel.model.Activity convertEventToBPELActivity(
            ConverterContext context, final Activity xpdlActivity, boolean processLevel) throws ConversionException {
		org.eclipse.bpel.model.Activity bpelActivity = null;

		Event event = xpdlActivity.getEvent();
		if (event instanceof StartEvent) {
            // BPMN Spec comments - This will map to the receive element. The
            // createInstance attribute of the receive element will be set to "yes".
            StartEvent startEvent = (StartEvent) event;
			TriggerType triggerType = startEvent.getTrigger();
			String eventType = N2PEConstants.START_EVENT_TYPE;
			switch (triggerType.getValue()) {
			case TriggerType.NONE:
                if (processLevel) {
                    bpelActivity = convertStartEvent(context, xpdlActivity);
                }
                break;
			case TriggerType.MESSAGE:
				// This will map to the receive element.
				bpelActivity = convertToBPELReceive(
						context, xpdlActivity, startEvent.getTriggerResultMessage(), true);
				eventType = N2PEConstants.MESSAGE_START_EVENT_TYPE;
				break;
			case TriggerType.TIMER:
				bpelActivity = convertStartTimerEvent(context, xpdlActivity, startEvent.getTriggerTimer());
				eventType = N2PEConstants.TIMER_START_EVENT_TYPE;
				break;
			case TriggerType.SIGNAL:
				bpelActivity = convertCatchSignalEvent(xpdlActivity, startEvent.getTriggerResultSignal());
				eventType = N2PEConstants.SIGNAL_START_EVENT_TYPE;
				break;
			case TriggerType.MULTIPLE:
				// This will map to a BPEL4WS pick which will be required to
				// process
				// the messages with a separate onMessage for each defined
				// Trigger.
				break;
			}
			if (processLevel && bpelActivity instanceof ReceiveEvent) {
				((ReceiveEvent)bpelActivity).setCreateInstance(true);
			}
			if (bpelActivity==null) {
                bpelActivity = BPELFactory.eINSTANCE.createEmpty();
            }
            BPELUtils.setType(bpelActivity, eventType);
            BPELUtils.setLabel(bpelActivity, xpdlActivity);
        } else if (event instanceof IntermediateEvent) {
            String target = null;
            IntermediateEvent intermediateEvent = (IntermediateEvent) event;
			String eventType = N2PEConstants.CATCH_INTERMEDIATE_EVENT_TYPE;
			TriggerType triggerType = intermediateEvent.getTrigger();
			switch (triggerType.getValue()) {
			case TriggerType.NONE:
				// generate null activity
				break;
			case TriggerType.MESSAGE:
                target = intermediateEvent.getTarget();
                if (target==null) {
                    CatchThrow catchThrow = intermediateEvent.getTriggerResultMessage().getCatchThrow();
                    if (catchThrow == CatchThrow.CATCH) {
                    	// catch intermediate event not on boundary with no incoming links is treated as event handler so just use place holder here
                    	List<Transition> incomingLinks = xpdlActivity.getIncomingTransitions();
                    	if (incomingLinks!=null && incomingLinks.size()>0) {
                    		bpelActivity = convertToBPELReceive(context, xpdlActivity, intermediateEvent.getTriggerResultMessage(), false);
                    	}
                    	eventType = N2PEConstants.CATCH_MESSAGE_INTERMEDIATE_EVENT_TYPE;
                    } else {
                    	RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
                    	if (rsta.isRestServiceImplementation(xpdlActivity)) {
                    		bpelActivity = ConvertRestService.convertRestTask(context, xpdlActivity, DirectionType.IN_LITERAL);
                    	} else {
                        	bpelActivity = convertTriggerResultMessageToReplyOrInvoke(context, xpdlActivity, intermediateEvent.getTriggerResultMessage());
                    	}
                    	eventType = N2PEConstants.THROW_MESSAGE_INTERMEDIATE_EVENT_TYPE;
                    }
                } else {
                    // on the boundary, it will map to OnEvent
                    // use Null activity for placeholder, will finish setup later
                    context.addBoundaryEvent(target, xpdlActivity.getId());
                    eventType = N2PEConstants.BOUNDARY_MESSAGE_EVENT_TYPE;
                }
                break;
			case TriggerType.TIMER:
                target = intermediateEvent.getTarget();
                if (target==null) {
                    // in the main flow, it is a Wait activity
                    bpelActivity = convertTimeEvent(xpdlActivity, intermediateEvent.getTriggerTimer());
                    eventType = N2PEConstants.CATCH_TIMER_INTERMEDIATE_EVENT_TYPE;
                } else{
                    // on the boundary, it is an OnAlarm
                    // use Null activity for placeholder, will finish setup later
                    context.addBoundaryEvent(target, xpdlActivity.getId());
                    eventType = N2PEConstants.BOUNDARY_TIMER_EVENT_TYPE;
                }
                break;
			case TriggerType.LINK:
				CatchThrow catchThrow = intermediateEvent.getTriggerResultLink().getCatchThrow();
				if (catchThrow == CatchThrow.CATCH) {
                    eventType = N2PEConstants.CATCH_LINK_INTERMEDIATE_EVENT_TYPE;
				} else {
                	eventType = N2PEConstants.THROW_LINK_INTERMEDIATE_EVENT_TYPE;
				}
				break;
			case TriggerType.MULTIPLE:
				// This will map to a BPEL4WS pick which will be required to
				// process
				// the messages with a separate onMessage for each defined
				// Trigger.
				break;
			case TriggerType.CANCEL:
				eventType = N2PEConstants.CATCH_CANCEL_INTERMEDIATE_EVENT_TYPE;
                break;
            case TriggerType.COMPENSATION:
                target = intermediateEvent.getTarget(); //id of host activity
                if (target==null) {
                    bpelActivity = convertCompensate(context, intermediateEvent.getTriggerResultCompensation());
                    eventType = N2PEConstants.THROW_COMPENSATION_INTERMEDIATE_EVENT_TYPE;
                } else {
                    //on the boundary, it goes into a compensation handler
                    //use Null activity for placeholder, will finish setup later
                    context.addBoundaryEvent(target, xpdlActivity.getId());
                    eventType = N2PEConstants.BOUNDARY_COMPENSATION_EVENT_TYPE;
                }
                break;
            case TriggerType.ERROR:
                target = intermediateEvent.getTarget();
                if (target==null) {
                    //in the main flow, it is a throw, or at least a signal event (send with event=yes extended attribute)
                    //but may be it is a throw if inside a subflow
                    bpelActivity = ConvertThrow.convertResultErrorToThrow(
                    		context, xpdlActivity, intermediateEvent.getResultError());  //todo signal event instead
                    //todo not supporting intermediate throw error, would be error end event instead.
                    eventType = N2PEConstants.THROW_ERROR_INTERMEDIATE_EVENT_TYPE;
                } else {
                    //on the boundary, it is a catch.
                    //use Null activity for place holder, will finish setup later
                    context.addBoundaryEvent(target, xpdlActivity.getId());
                    eventType = N2PEConstants.BOUNDARY_ERROR_EVENT_TYPE;
                }
                break;
            case TriggerType.SIGNAL:
                target = intermediateEvent.getTarget();
                if (target==null) {
                	TriggerResultSignal signalTrigger = intermediateEvent.getTriggerResultSignal();
                    if (signalTrigger.getCatchThrow().getValue()==CatchThrow.THROW_VALUE) {
                        // send (throw) signal
	                    bpelActivity = convertSignalEvent(signalTrigger, xpdlActivity);
                    	eventType = N2PEConstants.THROW_SIGNAL_INTERMEDIATE_EVENT_TYPE;
                    } else {
                        // receive (catch) signal
	                    bpelActivity = convertCatchSignalEvent(xpdlActivity, signalTrigger);
                    	eventType = N2PEConstants.CATCH_SIGNAL_INTERMEDIATE_EVENT_TYPE;
                    }
                } else {
                    //on the boundary, it is a onSignal.
                    //use Null activity for place holder, will finish setup later
                    context.addBoundaryEvent(target, xpdlActivity.getId());
                    eventType = N2PEConstants.BOUNDARY_SIGNAL_EVENT_TYPE;
                }
                break;
            }// endswitch
            if (bpelActivity==null) {
                bpelActivity = BPELFactory.eINSTANCE.createEmpty();
            }
            BPELUtils.setType(bpelActivity, eventType);
            BPELUtils.setLabel(bpelActivity, xpdlActivity);
        } else if (event instanceof EndEvent) {
            EndEvent endEvent = (EndEvent) event;
			String eventType = N2PEConstants.END_EVENT_TYPE;
			ResultType resultType = endEvent.getResult();
			switch (resultType.getValue()) {
			case ResultType.NONE:
				// generate null activity
				break;
			case ResultType.MESSAGE:
				/*
                 * BX-3735: Sid. Handle REST Service Invoke End Event.
                 */
                RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
                if (rsta.isRestServiceImplementation(xpdlActivity)) {
                    bpelActivity =
                            ConvertRestService.convertRestTask(
                            		context, xpdlActivity, DirectionType.IN_LITERAL);
                } else {				
                	// Reply if the operation is one-way. Invoke if the operation is two-way
					bpelActivity = convertTriggerResultMessageToReplyOrInvoke(
	                        context, xpdlActivity, endEvent.getTriggerResultMessage());
                }
				eventType = N2PEConstants.MESSAGE_END_EVENT_TYPE;
				break;
			case ResultType.MULTIPLE:
				// This will map to a BPEL4WS pick which will be required to
				// process
				// the messages with a separate onMessage for each defined
				// Trigger.
				break;
			case ResultType.CANCEL:
                //todo
                break;
            case ResultType.COMPENSATION:
                bpelActivity = convertCompensate(context, endEvent.getTriggerResultCompensation());
                eventType = N2PEConstants.COMPENSATION_END_EVENT_TYPE;
                break;
            case ResultType.ERROR:
                bpelActivity = ConvertThrow.convertResultErrorToThrow(
                		context, xpdlActivity, endEvent.getResultError());
                eventType = N2PEConstants.ERROR_END_EVENT_TYPE;
				break;
            case ResultType.TERMINATE:
                // generate Exit activity
                bpelActivity = BPELFactory.eINSTANCE.createExit();
                eventType = N2PEConstants.TERMINATE_END_EVENT_TYPE;
                break;
            case ResultType.SIGNAL:
                bpelActivity = convertSignalEvent(endEvent.getTriggerResultSignal(), xpdlActivity);
                eventType = N2PEConstants.SIGNAL_END_EVENT_TYPE;
                break;
            }
            if (bpelActivity==null) {
                bpelActivity = BPELFactory.eINSTANCE.createEmpty();
            }
            BPELUtils.setType(bpelActivity, eventType);
            BPELUtils.setLabel(bpelActivity, xpdlActivity);
        } else {
			context.logError(Messages.getString("ConvertEvent.unknownEventType") + String.valueOf(event), null); //$NON-NLS-1$
        }
        return bpelActivity;
    }
    
    private static org.eclipse.bpel.model.Wait convertTimeEvent(Activity xpdlActivity, TriggerTimer timer) {
        org.eclipse.bpel.model.Wait wait = BPELFactory.eINSTANCE.createWait();

        if (xpdlActivity.getDeadline() != null && !xpdlActivity.getDeadline().isEmpty()) {
    	    Deadline deadline = xpdlActivity.getDeadline().get(0);
    	    Expression deadlineDuration = deadline.getDeadlineDuration();
            org.eclipse.bpel.model.Expression expr;
    	    String scriptGrammar = deadlineDuration.getScriptGrammar();
    	    if (GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
    	        String durationISO = convertConstantPeriod(deadlineDuration);
    	        expr = createBPELDurationExpression(durationISO);
    			wait.setFor(expr);
    	    } else {
    	        expr = createBPELDurationExpression(deadlineDuration.getText());
    	        if (timer.getTimeCycle() != null) {
    				wait.setFor(expr);
    	        } else { //assume TimeDate
    				wait.setUntil(expr);
    	        }
    	    }
		}

        XPDLUtils.configureCalendarReference(wait, timer, ProcessInterfaceUtil.getAllAvailableRelevantDataForActivity(xpdlActivity));

        return wait;
    }

    private static org.eclipse.bpel.model.Activity convertToBPELReceive(
    		ConverterContext context, final Activity xpdlActivity, TriggerResultMessage triggerResultMessage, boolean instantiate) {
		if (triggerResultMessage != null) {
			Message message = triggerResultMessage.getMessage();
			WebServiceOperation webServiceOperation = triggerResultMessage.getWebServiceOperation();
			if (message != null && webServiceOperation != null) {
				try {
		            WebServiceOperationInfo wsoInfo = context.getWebServiceOperationInfo(webServiceOperation);

		            org.eclipse.bpel.model.Scope scope = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createScope();
		            org.eclipse.bpel.model.Sequence sequence = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createSequence();
		            sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$
		            
		            // Correlate immediate
		            boolean correlateImmediate = XPDLUtils.getCorrelateImmediately(triggerResultMessage);
	                
	                org.eclipse.bpel.model.Activity activity = 
		            		ConvertWebService.convertWebServiceOperationToBPELReceive(context, wsoInfo, instantiate, correlateImmediate , xpdlActivity, message);
					sequence.getActivities().add(activity);
					
					ConvertDataMapping convertDataMapping=new ConvertDataMapping(
                            context, xpdlActivity, wsoInfo);
					
					/* XPD-8010: DataMapper - mapping activity is not necessarily an assign anymore, maybe a sequence for Datamapper. */
					org.eclipse.bpel.model.Activity theMappingActivity =
                            convertDataMapping.convertDataMappingsToActivity(message, true);
					
		            if (theMappingActivity != null) {
		            	sequence.getActivities().add(theMappingActivity);
		            }
		            
		            if (XPDLUtils.isReplyImmediately(triggerResultMessage)) {
		            	ReplyImmediate replyImmediate = createReplyImmediate(context, message, wsoInfo);
	            		activity.addExtensibilityElement(replyImmediate);
		            }

					org.eclipse.bpel.model.Variables variables = context.getVariables(xpdlActivity);
					scope.setVariables(variables);
					scope.setActivity(sequence);
		            return scope;
				} catch (ConversionException e) {
					context.logError(Messages.getString("ConvertEvent.cannotConvertStartEvent") + e.getMessage(), e); //$NON-NLS-1$
					return null;
				}
			}
		}

		return null;
    }

	static ReplyImmediate createReplyImmediate(ConverterContext context, Message message, WebServiceOperationInfo wsoInfo) throws ConversionException {
		ReplyImmediate replyImmediate = ExtensionsFactory.eINSTANCE.createReplyImmediate(); 
		org.eclipse.bpel.model.Variable outputVar = wsoInfo.createOutputVariable();
		context.addVariable(outputVar);
		replyImmediate.setVariable(EcoreUtil.copy(outputVar));

		ReplyImmediateDataMappings replyImmediateDataMappings = XPDLUtils.getReplyImmediateDataMappings(message);
		EList<DataMapping> dataMappings = replyImmediateDataMappings.getDataMappings();
		PartMappings toPartMappings = new PartMappings();
		
		for (DataMapping dataMapping : dataMappings) {
			org.eclipse.bpel.model.From from = BPELUtils.createFromVariable(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER.getName());
			String xPath = WsdlUtil.wsdlPathToXPath(DataMappingUtil.getTarget(dataMapping));
			int pos = xPath.indexOf("/"); //$NON-NLS-1$
			String partName = pos < 0 ? xPath : xPath.substring(0, pos);
			String query = pos < 0 ? null : xPath.substring(pos+1);
			if (query != null) {
				javax.wsdl.Part part = wsoInfo.getInput().getMessage().getPart(partName);
				query = BPELUtils.searchAndReplacePrefixes(context, wsoInfo.getWsdlDefinition(), part, query);
				toPartMappings.addMapping(partName, query);
			}
			org.eclipse.bpel.model.To to = BPELUtils.createToVariableWithPart(outputVar.getName(), partName, query);

			org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
			copy.setTo(to);
			copy.setFrom(from);
			replyImmediate.getCopy().add(copy);
		}
		
		if (!toPartMappings.isEmpty()) {
			Map<String, javax.wsdl.Part> parts = wsoInfo.getOutput().getMessage().getParts();
			for (javax.wsdl.Part part : parts.values()) {
				if (part.getElementName() == null && part.getTypeName() == null) {
					continue;
				}
				List<String> paths = toPartMappings.getPaths(part.getName());
				if (paths != null) {
					String partLiteral = wsoInfo.createDummyPartLiteral((org.eclipse.wst.wsdl.Part)part, paths.toArray(new String[0]), true);
					org.eclipse.bpel.model.Copy copy = ConvertDataMapping.createXmlLiteralToToPartMapping(outputVar.getName(), part.getName(), partLiteral);			        				
					replyImmediate.getCopy().add(0, copy);
				}
			}
		}
		
		return replyImmediate;
	}

	//throw
    private static org.eclipse.bpel.model.Activity convertSignalEvent(TriggerResultSignal signalTrigger, Activity xpdlActivity) throws ConversionException {
    	ExtensionActivity activity;
    	if (XPDLUtils.isGlobalSignalType(signalTrigger)) {
    		com.tibco.xpd.globalSignalDefinition.GlobalSignal gSignal = GlobalSignalUtil.getReferencedGlobalSignal(xpdlActivity);
    		GlobalSignal globalSignal = createGlobalSignal(gSignal, signalTrigger);
    		activity = BPELUtils.createExtensionActivityFromEmfObject(globalSignal, false);
    		attachGlobalSignalMappings(gSignal, activity, signalTrigger, xpdlActivity);
    	} else {
    		com.tibco.bx.bpelExtension.extensions.Signal signal = ExtensionsFactory.eINSTANCE.createSignal();
	        signal.setEvent(signalTrigger.getName());
	       	// temporarily, use extended attribute "data" to specify data field containing pay load 
	        List<ExtendedAttribute> list = xpdlActivity.getExtendedAttributes();
	        if (list!=null && list.size()>0) {
	            for (ExtendedAttribute attr: list) {
	                if ("data".equals(attr.getName())) { //$NON-NLS-1$
	                    signal.setVariable(attr.getValue());
	                }
	            }
	        }       
			Collection<ActivityInterfaceData> activityInterfaceData = ActivityInterfaceDataUtil.getActivityInterfaceData(xpdlActivity);
			for (ActivityInterfaceData activityData : activityInterfaceData) {
	    		signal.getVariables().add(activityData.getName());
			}
			activity = BPELUtils.createExtensionActivityFromEmfObject(signal, false);
    	}
    	return activity;
    }
    
    public static GlobalSignal createGlobalSignal(com.tibco.xpd.globalSignalDefinition.GlobalSignal gSignal, TriggerResultSignal signal) {
    	IProject project = WorkingCopyUtil.getProjectFor(gSignal);
    	ProjectDetails details = ProjectUtil.getProjectDetails(project);
        GlobalSignal globalSignal = ExtensionsFactory.eINSTANCE.createGlobalSignal();
        String fullName = signal.getName();//<projectId>/<special folder relative file path>#<signalName>
        globalSignal.setName(fullName.substring(fullName.indexOf('/') + 1)); //<special folder relative file path>#<signalName>
        globalSignal.setAppName(details.getId()); //<project id>
        Version version = new Version(details.getVersion());
        globalSignal.setAppVersion(String.valueOf(version.getMajor())); //<major project version>
		return globalSignal;
    }
    
    public static GlobalSignal createCaseDataSignal(TriggerResultSignal signal) {
        GlobalSignal globalSignal = ExtensionsFactory.eINSTANCE.createGlobalSignal();
        globalSignal.setAppName("$CaseRefSignal$");
        globalSignal.setAppVersion("1");
        globalSignal.setName(signal.getName());
		return globalSignal;
    }
    
    //because ExtensionActivity enforces the child to be serialized on its creation - add signal mappings as a separate 
    //extensibility element to delay its serialization until the actual BPEL save to allow the access to BPELWriter
    public static void attachGlobalSignalMappings(com.tibco.xpd.globalSignalDefinition.GlobalSignal globalSignal, 
    		ExtensibleElement extensibleElement, TriggerResultSignal signal, com.tibco.xpd.xpdl2.Activity xpdlActivity) 
    				throws ConversionException {
    	
        GlobalSignalMappings globalSignalMappings = ExtensionsFactory.eINSTANCE.createGlobalSignalMappings();
        
    	SignalData signalData = XPDLUtils.getSignalData(signal);
    	
        Variables signalVariables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
        
        Set<String> optional = new HashSet<String>();
        for (PayloadDataField payloadDataField:globalSignal.getPayloadDataFields()) {
    		Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
    		variable.setName("SIGNAL_"+payloadDataField.getName());
    		ConvertDataField.setDataTypeForVariable(xpdlActivity.getProcess(), variable, payloadDataField.getDataType(), 
    				payloadDataField.isIsArray());
    		signalVariables.getChildren().add(variable);
    		if (!payloadDataField.isMandatory() && !payloadDataField.isCorrelation()) {
    			optional.add(variable.getName());
    		}
        }
        globalSignalMappings.setSignalVariables(signalVariables);
        
        if (signal.getCatchThrow().getValue()==CatchThrow.THROW_VALUE) {
        	if (signalData.getCorrelationMappings()!=null) {
        		addCopies(signalData.getCorrelationMappings().getDataMappings(), globalSignalMappings.getVariableCopy(), null, "", "SIGNAL_");
        	}
        	addCopies(signalData.getDataMappings(), globalSignalMappings.getVariableCopy(), optional, "", "SIGNAL_");
        } else {
        	addCopies(signalData.getCorrelationMappings().getDataMappings(), globalSignalMappings.getCorrelationCopy(), null, "SIGNAL_", "");
        	addCopies(signalData.getDataMappings(), globalSignalMappings.getVariableCopy(), optional, "SIGNAL_", "");
        }
		extensibleElement.addExtensibilityElement(globalSignalMappings);
    }
    
    private static void addCopies(List<DataMapping> dataMappings, List<org.eclipse.bpel.model.Copy> copies, 
    		 Set<String> optional, String fromPrefix, String toPrefix) {
    	if (dataMappings!=null) {
	    	for (DataMapping dataMapping : dataMappings) {
				DataMappingInfo dataMappingInfo = new DataMappingInfo(dataMapping);
				org.eclipse.bpel.model.To to = BPELUtils.createToVariable(toPrefix+dataMappingInfo.getToExpression());
				org.eclipse.bpel.model.From from = dataMappingInfo.isScript() ? 
						BPELUtils.createFromScript(dataMappingInfo.getFromExpression(), dataMappingInfo.getGrammar()) :
						BPELUtils.createFromVariable(fromPrefix + dataMappingInfo.getFromExpression());
				org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
				copy.setTo(to);
				copy.setFrom(from);
				if (optional!=null) {
					if (from.getVariable() != null && optional.contains(from.getVariable().getName())) {
						copy.setIgnoreMissingFromData(true);
					}
					if (to.getVariable() != null && optional.contains(to.getVariable().getName())) {
						copy.setIgnoreMissingFromData(true);
					}
				}
				copies.add(copy);
			}
    	}
    }

    private static org.eclipse.bpel.model.Activity convertCompensate(ConverterContext context, TriggerResultCompensation compensateTrigger) {
        org.eclipse.bpel.model.Activity result;
        String activityId = compensateTrigger.getActivityId();  // id of compensation activity?
        if (activityId==null || activityId.length()<1) {
            // convert to compensate activity
            result = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createCompensate();
        } else {
            // convert to compensateScope activity
            result = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createCompensateScope();
        	Activity targetActivity = context.getXpdlActivityWithID(activityId);
            if (targetActivity!=null) {
            	// use "scope"+target for scope name
            	String targetName = N2PEConstants.NAME_PREFIX + "scope_" + targetActivity.getName(); //$NON-NLS-1$
            	((CompensateScope)result).setTarget(targetName);
            	// by-pass to set target attribute on BPEL model which does work here
            	BPELUtils.addExtensionAttribute(result, "target", targetName); //$NON-NLS-1$
            }
        }
        context.setUseExplicitCompensation(true);
        return result;
    }


    private static org.eclipse.bpel.model.Activity convertStartEvent(ConverterContext context, Activity xpdlActivity) {
        com.tibco.bx.bpelExtension.extensions.ReceiveEvent receiveEvent = ExtensionsFactory.eINSTANCE.createReceiveEvent();
        com.tibco.bx.bpelExtension.extensions.StartEvent startEvent = ExtensionsFactory.eINSTANCE.createStartEvent();
        startEvent.setEventType(StartEventType.DEFAULT_LITERAL); //$NON-NLS-1$
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
        	receiveEvent.getExtensibilityElements().add(parameters);
        }
        
        receiveEvent.setEventSource(startEvent);
        return receiveEvent;
    }

    private static org.eclipse.bpel.model.Activity convertStartTimerEvent(ConverterContext context, Activity xpdlActivity, TriggerTimer timer) {
        com.tibco.bx.bpelExtension.extensions.ReceiveEvent receiveEvent = ExtensionsFactory.eINSTANCE.createReceiveEvent();
        com.tibco.bx.bpelExtension.extensions.TimerEvent timerEvent = ExtensionsFactory.eINSTANCE.createTimerEvent();
        receiveEvent.setEventSource(timerEvent);

		if (xpdlActivity.getDeadline() != null && !xpdlActivity.getDeadline().isEmpty()) {
		    Deadline deadline = xpdlActivity.getDeadline().get(0);
		    Expression deadlineDuration = deadline.getDeadlineDuration();
		    String scriptGrammar = deadlineDuration.getScriptGrammar();
	        com.tibco.bx.bpelExtension.extensions.Expression expr;
		    if (GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
		        String durationISO = convertConstantPeriod(deadlineDuration);
		        expr = createDurationExpression(durationISO);
		    } else {
		        expr = createDurationExpression(deadlineDuration.getText());
		    }

		    if (timer.getTimeCycle() != null) {
	            timerEvent.setRepeatEveryDuration(expr);
	        } else { //assume TimeDate
				if (GRAMMAR_CONSTANT_PERIOD.equals(scriptGrammar)) {
					timerEvent.setDuration(expr);
				} else {
		        	timerEvent.setDeadline(expr);
				}
	        }
		}		
        
        XPDLUtils.configureCalendarReference(receiveEvent, timer, ProcessInterfaceUtil.getAllAvailableRelevantDataForActivity(xpdlActivity));

        return receiveEvent;
        //return ConvertUtil.createExtensionActivityFromEmfObject(receiveEvent, ConvertUtil.EXTENSION_ACTIVITY_LOCALNAME, true);
    }

    private static org.eclipse.bpel.model.Activity convertCatchSignalEvent(Activity xpdlActivity, TriggerResultSignal signal) throws ConversionException {
    	boolean isEventHandler = XPDLUtils.isEventHandlerActivity(xpdlActivity);
    	if (isEventHandler) {
    		if (signal != null && XPDLUtils.isGlobalSignalType(signal)) {
        		Assign assign = BPELFactory.eINSTANCE.createAssign();
        		BPELUtils.addExtensionAttribute(assign, "useGlobalSignalMapping", "yes"); //$NON-NLS-1$  //$NON-NLS-2$
        		return assign;
    		}
    		return BPELFactory.eINSTANCE.createEmpty();
    	} else {
            com.tibco.bx.bpelExtension.extensions.ReceiveEvent receiveEvent = ExtensionsFactory.eINSTANCE.createReceiveEvent();
            EObject eventSource;
//            if (XPDLUtils.isGlobalSignalType(signal)) {
            	//not supported
//            	eventSource = createGlobalSignal(signal);
//        		attachGlobalSignalMappings(receiveEvent, signal, xpdlActivity);
//    		} else {
    			eventSource = ExtensionsFactory.eINSTANCE.createSignalEvent();
    			((SignalEvent) eventSource).setEvent(signal.getName());
//    		}
            receiveEvent.setEventSource(eventSource);
            return receiveEvent;
    	}
    }

	protected static String convertConstantPeriod(Expression deadlineDuration) {
		Object cp = deadlineDuration.getMixed().get(
		        XpdExtensionPackage.eINSTANCE.getDocumentRoot_ConstantPeriod(), false);
        if (cp instanceof List) {
            List list = (List) cp;
            if (list.size() > 0) {
                cp = list.get(0);
            }
        }
		if (!(cp instanceof ConstantPeriod)) {
			return null;
		}

		ConstantPeriod constPeriod = (ConstantPeriod) cp;
	    BigInteger iYears = constPeriod.getYears();
	    BigInteger iMonths = constPeriod.getMonths();
	    BigInteger iWeeks = constPeriod.getWeeks();
	    BigInteger iDays = constPeriod.getDays();
	    BigInteger iHours = constPeriod.getHours();
	    BigInteger iMinutes = constPeriod.getMinutes();
	    BigInteger iSecs = constPeriod.getSeconds();
	    BigInteger iMicroSecs = constPeriod.getMicroSeconds();

	    int years = (iYears != null) ? iYears.intValue() : 0;
	    int months = (iMonths != null) ? iMonths.intValue() : 0;
	    int weeks = (iWeeks != null) ? iWeeks.intValue() : 0;
	    int days = (iDays != null) ? iDays.intValue() : 0;
	    int hours = (iHours != null) ? iHours.intValue() : 0;
	    int minutes = (iMinutes != null) ? iMinutes.intValue() : 0;
	    int secs = (iSecs != null) ? iSecs.intValue() : 0;
	    long microSecs = (iMicroSecs != null) ? iMicroSecs.longValue() : 0;
	    
	    StringBuffer buf = new StringBuffer();
	    buf.append("\"P"); //$NON-NLS-1$
	    if (years > 0) {
	    	buf.append(years).append("Y"); //$NON-NLS-1$
	    }
	    if (months > 0) {
	    	buf.append(months).append("M"); //$NON-NLS-1$
	    }
	    if (weeks > 0 || days > 0) {
	    	buf.append(weeks*7+days).append("D"); //$NON-NLS-1$
	    }
	    if (hours > 0 || minutes > 0 || secs > 0 || microSecs>0) {
		    buf.append("T"); //$NON-NLS-1$
	    }
	    if (hours > 0) {
	    	buf.append(hours).append("H"); //$NON-NLS-1$
	    }
	    if (minutes > 0) {
	    	buf.append(minutes).append("M"); //$NON-NLS-1$
	    }
	    if (secs > 0 || microSecs > 0) {
	    	if (secs > 0 && microSecs<1000000) {
	    		buf.append(secs);
	    	}
	    	if (microSecs > 0) {
	    		BigDecimal bd = new BigDecimal(iMicroSecs).divide(new BigDecimal("1000000"));
	    		String x = bd.toString();
	    		if (microSecs>=1000000) {
	    			x = bd.add(new BigDecimal(iSecs)).toString();
	    			buf.append(x);
	    		} else {
	    			x = x.substring(x.indexOf('.')+1);
	    			buf.append(".").append(x); //$NON-NLS-1$
	    		}
	    	}
    		buf.append("S"); //$NON-NLS-1$
		}

	    buf.append("\""); //$NON-NLS-1$
	    return buf.toString();
	}
    
    protected static com.tibco.bx.bpelExtension.extensions.Expression createDurationExpression(String duration) {
        com.tibco.bx.bpelExtension.extensions.Expression expr = ExtensionsFactory.eINSTANCE.createExpression();
        expr.setLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
        expr.setText(duration);
        return expr;
    }

    protected static org.eclipse.bpel.model.Expression createBPELDurationExpression(String duration) {
    	org.eclipse.bpel.model.Expression expr = BPELFactory.eINSTANCE.createExpression();
        expr.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
        expr.setBody(duration);
        return expr;
    }

    private static org.eclipse.bpel.model.Activity convertTriggerResultMessageToReplyOrInvoke(
            ConverterContext context, final Activity xpdlActivity, TriggerResultMessage triggerResultMessage) 
            throws ConversionException {
		if (triggerResultMessage != null) {
			Message message = triggerResultMessage.getMessage();
			WebServiceOperation webServiceOperation = triggerResultMessage.getWebServiceOperation();
			if (message != null && webServiceOperation != null) {
                if (ReplyActivityUtil.isReplyActivity(xpdlActivity)) {
                    return convertToBPELReply(context, webServiceOperation, xpdlActivity, message);
                } else {
                    // invoke
                    return convertToBPELInvoke(context, webServiceOperation, xpdlActivity, message);
                }
            }
		}
		
		return null;
    }

    public static org.eclipse.bpel.model.Activity convertToBPELReply(ConverterContext context
            , WebServiceOperation webServiceOperation, Activity xpdlActivity, Message message ) {
        try {
            WebServiceOperationInfo wsoInfo = context.getWebServiceOperationInfo(webServiceOperation);

            org.eclipse.bpel.model.Scope scope = BPELFactory.eINSTANCE.createScope();
            org.eclipse.bpel.model.Sequence sequence = BPELFactory.eINSTANCE.createSequence();
            sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$

            //convert the reply before the assign, so that the latter conversion has access to the 
            //variable in the context (required for the CDS since we need to know the variable type) 
            org.eclipse.bpel.model.Reply reply = ConvertWebService.convertWebServiceOperationToBPELReply(context,
					wsoInfo, xpdlActivity, message);

            ConvertDataMapping convertDataMapping=new ConvertDataMapping(context, xpdlActivity, wsoInfo);
            
            /*
             * XPD-8010 DataMapper - mapping activity is not necessarily an
             * assign anymore, maybe a sequence for Datamapper.
             */
            org.eclipse.bpel.model.Activity theMappingActivity =
                    convertDataMapping.convertDataMappingsToActivity(message,
                            false);
            
            if (theMappingActivity != null) {
                sequence.getActivities().add(theMappingActivity);
            }

            sequence.getActivities().add(reply);

            org.eclipse.bpel.model.Variables variables = context.getVariables(xpdlActivity);
            scope.setVariables(variables);
            scope.setActivity(sequence);

    		return scope;
        } catch (ConversionException e) {
            context.logError(Messages.getString("ConvertEvent.cannotConvertStartEvent") + e.getMessage(), e); //$NON-NLS-1$
            return null;
        }
    }

    public static org.eclipse.bpel.model.Activity convertToBPELInvoke(ConverterContext context
            , WebServiceOperation webServiceOperation, Activity xpdlActivity, Message message) throws ConversionException {
        
        org.eclipse.bpel.model.Scope scope = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createScope();
        org.eclipse.bpel.model.Sequence sequence = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createSequence();
        sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$

        WebServiceOperationInfo wsoInfo = context.getWebServiceOperationInfo(webServiceOperation);

        //we need to convert the invoke activity first, 
        //so that the message variable will be available for the data mapping conversion
        org.eclipse.bpel.model.Invoke invoke = ConvertWebService.convertWebServiceOperationToBPELInvoke(
        		context, wsoInfo, xpdlActivity, message);
    	XPDLUtils.configureRetry(invoke, xpdlActivity);
        sequence.getActivities().add(invoke);

        ConvertDataMapping convertDataMapping=new ConvertDataMapping(context,xpdlActivity, wsoInfo);
        
        /*
         * XPD-8010: DataMapper - mapping activity is not necessarily an assign
         * anymore, maybe a sequence for Datamapper.
         */
        org.eclipse.bpel.model.Activity theMappingActivity =
                convertDataMapping.convertDataMappingsToActivity(message,
                        true);
       
        if (theMappingActivity != null) {
        	sequence.getActivities().add(0, theMappingActivity);
        }
        
        org.eclipse.bpel.model.Variables variables = context.getVariables(xpdlActivity);
        scope.setVariables(variables);
        scope.setActivity(sequence);
    	XPDLUtils.configureHaltOnError(scope, xpdlActivity);
        return scope;
    }

}
