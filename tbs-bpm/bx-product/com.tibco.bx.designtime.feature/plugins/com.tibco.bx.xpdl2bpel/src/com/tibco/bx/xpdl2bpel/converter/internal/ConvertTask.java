package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.Tracing;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskManual;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

public class ConvertTask {
	
    /**
     * Convert the task XPDL Activity types to BPEL Activities.
     * @param context
     * @param xpdlActivity The XPDL Activity to convert to BPEL
     * @param processLevel true when processing process level activities versus activity sets
     * @param createInstance
     * @return The converted BPEL Activity
     */
    public static org.eclipse.bpel.model.Activity convertTaskActivityToBPELActivity(
            ConverterContext context, final Activity xpdlActivity, boolean processLevel, boolean createInstance) throws ConversionException {
    	
        if (XPDLUtils.isLoopActivity(xpdlActivity)) {
            context.addLoopActivity(xpdlActivity.getId());
        }
        Implementation impl = xpdlActivity.getImplementation();
        if (impl instanceof Task) {
            Task t = (Task) impl;
            if (t.getTaskManual() != null) {
                return convertTaskManual(context, xpdlActivity, t.getTaskManual());
            } else if (t.getTaskReceive() != null) {
                return convertTaskReceiveToBPELReceive(context, xpdlActivity, t.getTaskReceive(), createInstance);
            } else if (t.getTaskSend() != null) {
                try {
                    TaskSend taskSend = t.getTaskSend();
                    if (ReplyActivityUtil.getRequestActivityIdForReplyActivity(xpdlActivity) != null) {
                        // reply
                        return convertTaskSendToBPELReply(context, xpdlActivity, taskSend);
                    } else {
                        return convertTaskSendToBPELInvoke(context, xpdlActivity, taskSend);
                    }
                } catch (ConversionException e) {
                    context.logError(Messages.getString("ConvertActivity.cannotConvertTaskSend") + e.getMessage(), e); //$NON-NLS-1$
                    return null;
                }
            } else if (t.getTaskService() != null) {
                try {
                    return convertTaskServiceToBPEL(context, xpdlActivity, t.getTaskService());
                } catch (ConversionException e) {
                    context.logError(Messages.getString("ConvertActivity.cannotConvertTaskService") + e.getMessage(), e); //$NON-NLS-1$
                    return null;
                }
            } else if (t.getTaskUser() != null) {
                return convertTaskUser(context, xpdlActivity, t.getTaskUser());
            } else if (t.getTaskReference() != null) {
                //TODO
            } else if (t.getTaskScript() != null) {
                return convertTaskScript(context, xpdlActivity, t.getTaskScript());
            }
        } else if (impl instanceof SubFlow) {
            return convertSubFlowToBPEL(context, xpdlActivity, (SubFlow) impl);
        } else if (impl instanceof Reference) {
            //TODO
        }

        return null;
    }

    /**
     * Convert the TaskReceive type Activity to a BPEL Invoke.
     * @param context the converter context
     * @param xpdlActivity
     * @param taskReceive The XPDL TaskReceive to convert to BPEL.
     * @param createInstance
     * @return A BPEL Receive command converted from the XPDL Activity parameter.
     * @throws ConversionException
     */
    private static org.eclipse.bpel.model.Activity convertTaskReceiveToBPELReceive(
            final ConverterContext context, Activity xpdlActivity, final TaskReceive taskReceive, boolean createInstance) throws ConversionException {
        // Tags in the BPEL receive command ("?" indicates optional)
        // partnerLink="NCName" operation="NCName" portType="QName"? variable="BPELVariableName"?
        // createInstance="yes|no"? messageExchange="NCName"? standard-attributes
        // The messageExchange attribute is used to associate a <reply> activity with a <receive> activity
        ImplementationType implementationType = taskReceive.getImplementation();
        switch (implementationType.getValue()) {
        case ImplementationType.WEB_SERVICE:
            if (taskReceive.getWebServiceOperation() != null) {
                WebServiceOperationInfo wsoInfo = context.getWebServiceOperationInfo(taskReceive.getWebServiceOperation());

                org.eclipse.bpel.model.Scope scope = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createScope();
                org.eclipse.bpel.model.Sequence sequence = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createSequence();
                sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$

                // Correlate immediate
                boolean correlateImmediate = XPDLUtils.getCorrelateImmediately(taskReceive);
                
				org.eclipse.bpel.model.Receive receive = ConvertWebService.convertWebServiceOperationToBPELReceive(
                        context, wsoInfo, createInstance, correlateImmediate , xpdlActivity, taskReceive.getMessage());
                
                sequence.getActivities().add(receive);
                
                ConvertDataMapping convertDataMapping=new ConvertDataMapping(context, xpdlActivity, wsoInfo);
                
                /*
                 * XPD-8010 DataMapper - mapping activity is not necessarily an
                 * assign anymore, maybe a sequence for Datamapper.
                 */
                org.eclipse.bpel.model.Activity theMappingActivity =
                        convertDataMapping
                                .convertDataMappingsToActivity(taskReceive
                                        .getMessage(), true);

                if (theMappingActivity != null) {
                    sequence.getActivities().add(theMappingActivity);
                }

                org.eclipse.bpel.model.Variables variables = context.getVariables(xpdlActivity);
                scope.setVariables(variables);
                scope.setActivity(sequence);
                return scope;
            } else {
                String msg = String.format(Messages.getString("ConvertTask.taskReceiveMsgAndOp"), new Object[]{((Task)taskReceive.eContainer()).getActivity().getName()}); //$NON-NLS-1$
                context.logError(msg, null);
                return null;
            }
        case ImplementationType.UNSPECIFIED:
            if (!context.isPageFlowEngineTarget()) {
                //This is new ReceiveTask implementation for incoming request in SCE.
                org.eclipse.bpel.model.Scope scope = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createScope();
                org.eclipse.bpel.model.Sequence sequence = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createSequence();
                sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$
    
                ////
                org.eclipse.bpel.model.Receive receive = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createReceive();
                receive.setCreateInstance(createInstance);
                receive.setName(context.genUniqueActivityName("receive")); //$NON-NLS-1$
                // SCE: Default message correlation timeout is no longer configurable by the user.
                // See: XPDLUtils.getMessageTimeout(xpdlActivity);
                BPELUtils.addExtensionAttribute(receive, "messageTimeout", context.getDefaultIncomingRequestTimeout()); //$NON-NLS-1$
                ////
                sequence.getActivities().add(receive);
    
                org.eclipse.bpel.model.Activity theMappingActivity =
                        org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
                theMappingActivity.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$
    
                if (theMappingActivity != null) {
                    sequence.getActivities().add(theMappingActivity);
                }
    
                org.eclipse.bpel.model.Variables variables = context.getVariables(xpdlActivity);
                scope.setVariables(variables);
                scope.setActivity(sequence);
                return scope;
            } else {
                String msg = String.format(Messages.getString("ConvertTask.taskReceiveMsgAndOp"), new Object[]{((Task)taskReceive.eContainer()).getActivity().getName()}); //$NON-NLS-1$
                context.logError(msg, null);
                return null;
            }
        case ImplementationType.OTHER:
            String msg = String.format(Messages.getString("ConvertTask.taskReceiveImpl"), //$NON-NLS-1$
                    new Object[]{((Task)taskReceive.eContainer()).getActivity().getName(), implementationType.getName()});
            context.logError(msg, null);
            return null;
        }

        return null;
    }// end convertTaskReceiveToBPELReceive

    /** Convert the TaskSend type Activity to a BPEL Invoke.
     * @param context the converter context
     * @param xpdlActivity The XPDL TaskSend to convert to BPEL.
     * @return A BPEL Send command converted from the XPDL Activity parameter. 
     * @throws ConversionException
     */
    private static org.eclipse.bpel.model.Activity convertTaskSendToBPELInvoke (
            final ConverterContext context, final Activity xpdlActivity, TaskSend taskSend) throws ConversionException {
        Task task = (Task) xpdlActivity.getImplementation();
        ImplementationType implementationType = taskSend.getImplementation();
        switch (implementationType.getValue()) {
        case ImplementationType.WEB_SERVICE:
            if (taskSend.getMessage() != null && taskSend.getWebServiceOperation() != null) {
                Message message = taskSend.getMessage();                
                return ConvertEvent.convertToBPELInvoke(context, taskSend.getWebServiceOperation(), xpdlActivity, message);
            } else {
                String msg = String.format(Messages.getString("ConvertTask.taskSendMsgAndOp"), new Object[]{xpdlActivity.getName()}); //$NON-NLS-1$
                context.logError(msg, null);
                return null;
            }
        case ImplementationType.OTHER:
        	RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        	if (rsta.isRestServiceImplementation(xpdlActivity)) {
        		return ConvertRestService.convertRestTask(context, xpdlActivity, DirectionType.IN_LITERAL);
        	}
        case ImplementationType.UNSPECIFIED:
            String msg = String.format(Messages.getString("ConvertTask.taskSendImpl"), //$NON-NLS-1$
                    new Object[]{xpdlActivity.getName(), implementationType.getName()});
            context.logError(msg, null);
            return null;
        }
        // TODO
// int loopType = ConvertUtil.getLoopType (xpdlActivity);
//        switch (loopType) {
//            case LoopType.STANDARD:
//            break;
//            case LoopType.MULTI_INSTANCE:
//                // TODO This seems to be an error (or at least a warning that it won't be multi-instance)
//            break;
//            case -1:
//            break;
//        }//endswitch
        return null;
    }

    private static org.eclipse.bpel.model.Activity convertTaskSendToBPELReply(ConverterContext context, Activity xpdlActivity, TaskSend send) {
        
        WebServiceOperation webServiceOperation = send.getWebServiceOperation();                
        return ConvertEvent.convertToBPELReply(context, webServiceOperation, xpdlActivity, send.getMessage());                                  
    }

    /**
     * Convert the TaskService type Activity to a BPEL Invoke.
     * @param context the converter context
     * @param taskService The XPDL TaskService to convert to BPEL.
     * @param xpdlActivity
     * @return A BPEL Invoke command converted from the XPDL Activity parameter.
     * @throws ConversionException
     */
    private static org.eclipse.bpel.model.Activity convertTaskServiceToBPEL(
            final ConverterContext context, Activity xpdlActivity, final TaskService taskService) throws ConversionException {
        ImplementationType implementationType = taskService.getImplementation();
        if (implementationType != null) {
            int implementationTypeValue = implementationType.getValue();
            switch (implementationTypeValue) {
            case ImplementationType.WEB_SERVICE:
                org.eclipse.bpel.model.Scope scope = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createScope();
                org.eclipse.bpel.model.Sequence sequence = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createSequence();
                sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$

                WebServiceOperationInfo wsoInfo = context.getWebServiceOperationInfo(taskService.getWebServiceOperation());

                //convert the invoke before the assign, so that the latter conversion has access to the 
                //variable in the context (required for the CDS since we need to know the variable type) 
                org.eclipse.bpel.model.Invoke invoke = ConvertWebService.convertWebServiceOperationToBPELInvoke(
                        context, wsoInfo, xpdlActivity, taskService.getMessageIn());
                XPDLUtils.configureRetry(invoke, xpdlActivity);

                /* XPD-8010 DataMapper - mapping activity is not necessarily an assign anymore, maybe a sequence for Datamapper. */
                ConvertDataMapping dataMappingConverter = new ConvertDataMapping(context, xpdlActivity, wsoInfo);
                org.eclipse.bpel.model.Activity mappingActivity = dataMappingConverter.convertDataMappingsToActivity(taskService.getMessageIn(), true);
                
                if (mappingActivity != null) {
                    sequence.getActivities().add(mappingActivity);
                }
                
                sequence.getActivities().add(invoke);
                
                /*
                 * XPD-8010 DataMapper - mapping activity is not necessarily an
                 * assign anymore, maybe a sequence for Datamapper.
                 */
                org.eclipse.bpel.model.Activity outMappingActivity =
                        dataMappingConverter
                                .convertDataMappingsToActivity(taskService
                                        .getMessageOut(),
                                        false);
                if (outMappingActivity != null) {
                    sequence.getActivities().add(outMappingActivity);
                }
                

                org.eclipse.bpel.model.Variables variables = context.getVariables(xpdlActivity);
                scope.setVariables(variables);
                scope.setActivity(sequence);

                XPDLUtils.configureHaltOnError(scope, xpdlActivity);
                
                return scope;
            case ImplementationType.OTHER:
            	RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
            	if (rsta.isRestServiceImplementation(xpdlActivity)) {
            		return ConvertRestService.convertRestTask(context, xpdlActivity, DirectionType.INOUT_LITERAL);
            	} else {
                    FeatureMap featureMap = taskService.getOtherElements();
                    FeatureMap.ValueListIterator it =featureMap.valueListIterator();
                    if (it.hasNext()) {
                        EObject obj = (EObject)it.next();
                        return ConverterExtensions.convertTaskServiceToExtensionActivity(context, xpdlActivity, obj);
                    } else {
                        context.logError(Messages.getString("ConvertTask.taskServiceImpl") + taskService, null); //$NON-NLS-1$
                        return null;
                    }
            	}
            case ImplementationType.UNSPECIFIED:
                  context.logError(Messages.getString("ConvertTask.taskServiceImplType") + taskService, null); //$NON-NLS-1$
                  return null;
            }
        }

        return null;
    }

	/**
     * Performer value is currently set in activity rather than TaskUser or TaskManual.  However, these models
     * do have a spot for performer that currently contains the value "-defined-in-Activity-Performer-".
     * This routine will make a copy of the model and update performer value to name of the performer.
     * @param context converter context
     * @param xpdlActivity current xpdl activity
     * @param task TaskUser or TaskManual
     * @return
     */
    private static EObject updatePerformer(ConverterContext context, Activity xpdlActivity, EObject task) {
        String performerName = null;
        List<Performer> list = xpdlActivity.getPerformerList();
        if (list!=null) {
            for (Performer performer: list) {
                Participant participant = context.getParticipantFromActivityPerformer(performer);
                if (participant!=null) {
                    performerName = participant.getName();
                    break;  // only do one for now
                }
            }
        }
        if (performerName!=null) {
            // set performer name
            EObject copy = EcoreUtil.copy(task);
            List<Performer> performers;
            if (task instanceof TaskUser) {
                performers = ((TaskUser)copy).getPerformers();
            } else {
                performers = ((TaskManual)copy).getPerformers();
            }
            Performer performer = performers.get(0);
            if (performer!=null) {
                performer.setValue(performerName);
            }
            return copy;
        }
        return task;
    }

	private static org.eclipse.bpel.model.Activity convertTaskScript(ConverterContext context, final Activity xpdlActivity, TaskScript taskScript) {
		EObject configModel = null;
		try {
		    configModel = lookupExtension(context, taskScript, xpdlActivity);
		} catch (ConversionException e) {
		    context.logError(Messages.getString("ConvertActivity.cannotConvertTaskScript") + e.getMessage(), e); //$NON-NLS-1$
		}
		if (configModel!=null) {
			XPDLUtils.findReferencedData(context, xpdlActivity, xpdlActivity.getName(), DataReferenceContext.CONTEXT_SCRIPT_TASK_SCRIPT);
		    return BPELUtils.createExtensionActivityFromEmfObject(configModel, false);
		} else {
		    context.logError(Messages.getString("ConvertActivity.noScriptActivityExtension"), null); //$NON-NLS-1$
		    return null;
		}
	}

	private static org.eclipse.bpel.model.Activity convertTaskUser(ConverterContext context, final Activity xpdlActivity, TaskUser taskUser) {
		EObject configModel = null;
		try {
		    configModel = lookupExtension(context, taskUser, xpdlActivity);
    		XPDLUtils.findReferencedData(context, xpdlActivity, xpdlActivity.getName(), DataReferenceContext.CONTEXT_ACTIVITY_IMPLEMENTATION);
		} catch (ConversionException e) {
		    context.logError(Messages.getString("ConvertActivity.cannotConvertTaskUser") + e.getMessage(), e); //$NON-NLS-1$
		    return null;
		}
		if (configModel==null) {
		    configModel = updatePerformer(context, xpdlActivity, taskUser);
		}
		return BPELUtils.createExtensionActivityFromEmfObject(configModel, false);
	}

	private static org.eclipse.bpel.model.Activity convertTaskManual(ConverterContext context, final Activity xpdlActivity, TaskManual taskManual) {
		EObject configModel = null;
		try {
		    configModel = lookupExtension(context, taskManual, xpdlActivity);
		} catch (ConversionException e) {
		    context.logError(Messages.getString("ConvertActivity.cannotConvertTaskManual") + e.getMessage(), e); //$NON-NLS-1$
		}
		if (configModel==null) {
		    configModel = updatePerformer(context, xpdlActivity, taskManual);
		}
		return BPELUtils.createExtensionActivityFromEmfObject(configModel, false);
	}

    private static org.eclipse.bpel.model.Activity convertSubFlowToBPEL(
    		ConverterContext context, final Activity xpdlActivity, final SubFlow subFlow) throws ConversionException {
        return new ConvertCallProcess(context, xpdlActivity).convertSubFlow();
    }

    private static EObject lookupExtension(ConverterContext context, EObject defaultConfig, Activity xpdlActivity) throws ConversionException {
        EObject configModel = null;
        IActivityConfigurationModelBuilder modelBuilder = null;
        try {
            modelBuilder = ConverterExtensions.getModelBuilder(defaultConfig);
        } catch (CoreException e) {
            throw new ConversionException(Messages.getString("ConvertUtil.cannotLoadBuilderExtension") + defaultConfig, e); //$NON-NLS-1$
        }
        if (modelBuilder != null) {
            configModel = modelBuilder.transformConfigModel(xpdlActivity, context.getParticipantMap());
            if (Tracing.ENABLED) Tracing.trace("Config model is " + configModel); //$NON-NLS-1$
        }
        return configModel;
    }

}
