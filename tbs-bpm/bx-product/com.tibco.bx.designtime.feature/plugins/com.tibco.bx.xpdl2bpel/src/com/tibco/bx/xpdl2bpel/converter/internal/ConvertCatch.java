package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.wsdl.Part;
import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Catch;
import org.eclipse.bpel.model.CatchAll;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.Script;
import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.converter.internal.ConvertThrow.FaultNamingConvention;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.CDSUtils;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchBpmnErrorMapperSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ConvertCatch {
	
	private final ConverterContext context;
	private final com.tibco.xpd.xpdl2.Activity eventAct;
	private final ResultError resultError;

	private Variable faultVariable;

	public ConvertCatch(ConverterContext context, com.tibco.xpd.xpdl2.Activity eventAct) {
		this.context = context;
		this.eventAct = eventAct;
		this.resultError = ((IntermediateEvent)eventAct.getEvent()).getResultError();		
	}

    public org.eclipse.bpel.model.Activity convertErrorTriggerToCatch(Scope scope, Assign fanOutAssign) throws ConversionException {
		String errorCode = resultError.getErrorCode();
		CatchErrorMappings catchErrorMappings = XPDLUtils.getCatchErrorMappings(resultError);
        Object errorThrower = BpmnCatchableErrorUtil.getErrorThrower(eventAct);
        Activity attachedToTask = BpmnCatchableErrorUtil.getAttachedToTask(eventAct);
        boolean isAttachedToGlobalData = XPDLUtils.isGlobalDataTask(attachedToTask);
        boolean isDeclaredFault = false;
        boolean isDataMapperForCatchError=false;
        
       org.eclipse.bpel.model.Activity theMappingActivity = null;

       
       /*
        * Sid ACE-194: We don't do web-service any more in ACE - removed/commented WS related code
        */
       WebServiceOperationInfo wsoInfo = null; // Preserve to fail some of the conditions below.
//
//		WebServiceOperationInfo wsoInfo = getWebServiceOperationInfo();
//		if (wsoInfo != null) {
//			//this is a web service fault
//			javax.wsdl.Fault fault = errorCode != null ? wsoInfo.getFault(errorCode) : null;
//			if (fault != null) {
//				//error code is a declared fault
//				isDeclaredFault = true;
//		 				
//				/*
//	             * Sid XPD-8010. ConvertFaultDataMapping now only returns 'some'
//	             * kind of activity that does the mapping, which can differ
//	             * depending on the grammar. So now we get the acutal assign
//	             * afterwards (for adding the fanVariable copy statement to later.
//	             */
//                ConvertFaultDataMapping mappingConverter =
//                        new ConvertFaultDataMapping(context, eventAct, wsoInfo,
//                                errorCode);
//
//                theMappingActivity =
//                        mappingConverter
//                                .convertDataMappingsToAssign(catchErrorMappings
//                                        .getMessage());
//                
//                if (theMappingActivity != null) {
//                    Assign errDataAssign =
//                            mappingConverter.getMappingAssignActivity();
//
//                    if (errDataAssign != null) {
//                        faultVariable = null;
//                        EList<Copy> copies = errDataAssign.getCopy();
//                        for (Copy copy : copies) {
//                            From from = copy.getFrom();
//                            if (from.getVariable() != null) {
//                                faultVariable =
//                                        wsoInfo.createFaultVariable(from
//                                                .getVariable().getName());
//                                break;
//                            }
//                        }
//                        if (faultVariable == null) {
//                            faultVariable =
//                                    wsoInfo.createFaultVariable(errorCode);
//                        }
//
//                        /*
//                         * Sid XPD-8010 - Add fanVariable copies to mapping assign instead of visa versa.
//                         * (as the mapping one might be part of a scope/sequence that forms theMappingActivity)
//                         */
//                        errDataAssign.getCopy().addAll(fanOutAssign.getCopy());
//                    }
//                }
//			} else {
//				//this mapping is for an undeclared error (e.g. timeout exception)
//				
//			}
//		} 
		
		if (!isDeclaredFault && catchErrorMappings != null && catchErrorMappings.getMessage() != null) {

		    /*
		     * XPD-8006: Saket: Handle Data Mappings for CATCH ALL and CATCH SUB-PROCESS ERROR.
		     */
		    Message cemMessage = catchErrorMappings.getMessage();
		    
		    Object sdmObj=Xpdl2ModelUtil.getOtherElement(cemMessage, XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings());
		    
		    if(sdmObj instanceof ScriptDataMapper)
		    {
		        /*
		         * DataMapper for Catch-All/Catch Sub-process error.
		         */
		        isDataMapperForCatchError=true;
		        
		        String faultVariableName = FaultNamingConvention.getFaultVariableName(errorCode);
		        ScriptDataMapper sdm=(ScriptDataMapper)sdmObj;
		        
		        ExtensionActivity scriptActivity =
		                createDataMapperMappingScript(sdm);

		        if (scriptActivity != null) {
		            
		            Sequence sequence = BPELFactory.eINSTANCE.createSequence();
		            sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$
		            
		            fanOutAssign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$
		            sequence.getActivities().add(fanOutAssign);
		            sequence.getActivities().add(scriptActivity);
		            
		            /*
		             * XPD-8006: Saket: Here we need to decide if we have Catch-All or Catch Sub-Process Error
		             * because if we have Catch-All, then the mapping activity would be a sequence, whereas, 
		             * if we have catch error, then the mapping activity is going to be a scope 
		             * which'd have the sub-process variables defined in it.
		             */
		            
		            /*
		             * XPD-8174: Saket: Catch SOAP over JMS Service TimeOutException should also be modelled like CatchAll.
		             */
		            /*
		             * Sid ACE-194: We don't do web-service any more in ACE - removed/commented WS related code
		             */

		            if (errorCode == null || errorCode.length() == 0 || isAttachedToGlobalData /* || CatchWsdlErrorEventUtil
		                    .isTimeoutExceptionSelectedForSoapJMSConsumer(eventAct) */) {
		                
		                /*
		                 * Catch-All.
		                 */
		                
		                theMappingActivity=sequence;
		            
		            }else if(BpmnCatchableErrorUtil.isCatchSubProcessErrorEvent(eventAct)){
		                
		                /*
		                 * Catch Sub-Process error.
		                 */
		                
		                /*
		                 * Create scope to enclose the sequence and the variables equivalent to each sub-process parameter.
		                 */
		                Scope catchSubProcErrorScope = BPELFactory.eINSTANCE.createScope();
		                catchSubProcErrorScope.setName(context
		                        .genUniqueActivityName("scope")); //$NON-NLS-1$
		                
		                Activity subProcessActivity=BpmnCatchableErrorUtil.getAttachedToTask(eventAct);
		                
		                if(subProcessActivity!=null && subProcessActivity.getImplementation() instanceof SubFlow)
		                {

		                    Object catchTypeOrEndError =
		                           getCatchTypeOrSpecificErrorEndEvent(eventAct);

		                    if (catchTypeOrEndError instanceof Activity
		                            || catchTypeOrEndError instanceof InterfaceMethod) {
		                        
		                        errorThrower = catchTypeOrEndError;

		                        List<FormalParameter> errorThrowerActivityParameters =
                                        fetchThrownParameters(errorCode,
                                                errorThrower);
		                        
		                        org.eclipse.bpel.model.Variables variables =
                                        populateBpelVariablesForThrownParameters(fanOutAssign,
                                                errorCode,
                                                subProcessActivity,
                                                errorThrowerActivityParameters);

		                        catchSubProcErrorScope.setVariables(variables);
		                        catchSubProcErrorScope.setActivity(sequence);
		                        theMappingActivity=catchSubProcErrorScope;

		                    }
		                }
		            }
		        }

		        if (errorThrower instanceof Activity) {
		            faultVariable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		            faultVariable.setName(faultVariableName);
		            Fault fault = ConvertThrow.getFault(context, (Activity) errorThrower, resultError);
		            faultVariable.setMessageType((org.eclipse.wst.wsdl.Message) fault.getMessage());
		        } else if (errorThrower instanceof InterfaceMethod) {
		            ErrorThrowerInfo errorThrowerInfo = BpmnCatchableErrorUtil.getExtendedErrorThrowerInfo(eventAct);
		            ProcessInterface throwingProcessInterface = Xpdl2WorkingCopyImpl.locateProcessInterface(errorThrowerInfo.getThrowerContainerId());
		            faultVariable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		            faultVariable.setName(faultVariableName);
		            Fault fault = ConvertThrow.getFault(context, throwingProcessInterface, (InterfaceMethod) errorThrower, resultError);
		            faultVariable.setMessageType((org.eclipse.wst.wsdl.Message) fault.getMessage());
		        }

		    }else if(!cemMessage.getDataMappings().isEmpty()) {

		        String faultVariableName = FaultNamingConvention.getFaultVariableName(errorCode);

		        EList<DataMapping> dataMappings = catchErrorMappings.getMessage().getDataMappings();
		        if (dataMappings != null) {
		            Assign errDataAssign = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
		            for (DataMapping dataMapping : dataMappings) {
		                boolean isXPath = ScriptGrammarFactory.XPATH.equals(DataMappingUtil.getGrammar(dataMapping));
		                String target = DataMappingUtil.getTarget(dataMapping);
		                String script = DataMappingUtil.getScript(dataMapping);
		                ScriptInformation scriptInformation = XPDLUtils.getScriptInformation(dataMapping);
		                boolean isScript = scriptInformation != null;

		                org.eclipse.bpel.model.From from;
		                if (isScript) {
		                    String grammar = DataMappingUtil.getGrammar(dataMapping);
		                    String faultNameVar = FaultNamingConvention.getFaultVariableName(ConvertThrow.ERROR_CODE_TOKEN);
		                    String faultDetailVar = FaultNamingConvention.getFaultVariableName(ConvertThrow.ERROR_DETAIL_TOKEN);
		                    script = script.replaceAll(Matcher.quoteReplacement(ConvertThrow.ERROR_CODE_TOKEN), faultNameVar);
		                    script = script.replaceAll(Matcher.quoteReplacement(ConvertThrow.ERROR_DETAIL_TOKEN), faultDetailVar);
		                    from = BPELUtils.createFromScript(script, grammar);
		                } else {
		                    if ((wsoInfo != null && !isDeclaredFault) || isAttachedToGlobalData || errorCode == null || errorCode.length() == 0) {
		                        //no error code means it's CatchAll
		                        String fromVar = FaultNamingConvention.getFaultVariableName(script);
		                        from = BPELUtils.createFromVariable(fromVar);
		                    } else {
		                        from = BPELUtils.createFromVariable(faultVariableName);
		                        org.eclipse.wst.wsdl.Part fromPart = org.eclipse.wst.wsdl.WSDLFactory.eINSTANCE.createPart();
		                        fromPart.setName(ConvertThrow.ERROR_CODE_TOKEN.equals(script) ? "errorCode" : script); //$NON-NLS-1$
		                        from.setPart(fromPart);
		                    }
		                }

		                org.eclipse.bpel.model.To to;
		                boolean mappingToVariable = isXPath || target.indexOf(".") < 0; //$NON-NLS-1$
		                if (mappingToVariable) {
		                    to = BPELUtils.createToVariable(target);
		                } else {
		                    to = CDSUtils.createToExpressionWithCDS(eventAct, target, false);
		                    BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
		                }

		                org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		                copy.setTo(to);
		                copy.setFrom(from);

		                errDataAssign.getCopy().add(copy);
		            }
		            fanOutAssign.getCopy().addAll(errDataAssign.getCopy());

		        }

		        if (wsoInfo == null) {
		            if (errorThrower instanceof Activity) {
		                faultVariable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		                faultVariable.setName(faultVariableName);
		                Fault fault = ConvertThrow.getFault(context, (Activity) errorThrower, resultError);
		                faultVariable.setMessageType((org.eclipse.wst.wsdl.Message) fault.getMessage());
		            } else if (errorThrower instanceof InterfaceMethod) {
		                ErrorThrowerInfo errorThrowerInfo = BpmnCatchableErrorUtil.getExtendedErrorThrowerInfo(eventAct);
		                ProcessInterface throwingProcessInterface = Xpdl2WorkingCopyImpl.locateProcessInterface(errorThrowerInfo.getThrowerContainerId());
		                faultVariable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		                faultVariable.setName(faultVariableName);
		                Fault fault = ConvertThrow.getFault(context, throwingProcessInterface, (InterfaceMethod) errorThrower, resultError);
		                faultVariable.setMessageType((org.eclipse.wst.wsdl.Message) fault.getMessage());
		            }
		        }
		    }
		}

		/*
		 * Sid XPD-8010 - if there were no mappings then make sure we incldue the fanOutVariable assign still
		 */
		if (theMappingActivity == null) {
		    theMappingActivity = fanOutAssign;
		}
		
        FaultHandler faultHandler = scope.getFaultHandlers();
        if (faultHandler==null) {
            faultHandler = BPELFactory.eINSTANCE.createFaultHandler();
            scope.setFaultHandlers(faultHandler);
        }
		if (errorCode == null || errorCode.length() == 0) {
            CatchAll catchAll = BPELFactory.eINSTANCE.createCatchAll();
			String faultNameVar = FaultNamingConvention.getFaultVariableName(ConvertThrow.ERROR_CODE_TOKEN);
            BPELUtils.addExtensionAttribute(catchAll, "faultNameVar", faultNameVar); //$NON-NLS-1$
			String faultDetailVar = FaultNamingConvention.getFaultVariableName(ConvertThrow.ERROR_DETAIL_TOKEN);
            BPELUtils.addExtensionAttribute(catchAll, "faultDetailsVar", faultDetailVar); //$NON-NLS-1$
            catchAll.setActivity(theMappingActivity);
            faultHandler.setCatchAll(catchAll);
		} else if ((wsoInfo != null && !isDeclaredFault) || isAttachedToGlobalData) {
            Catch bpelCatch = BPELFactory.eINSTANCE.createCatch();
			bpelCatch.setFaultName(new QName(errorCode));
			String faultNameVar = FaultNamingConvention.getFaultVariableName(ConvertThrow.ERROR_CODE_TOKEN);
            BPELUtils.addExtensionAttribute(bpelCatch, "faultNameVar", faultNameVar); //$NON-NLS-1$
			String faultDetailVar = FaultNamingConvention.getFaultVariableName(ConvertThrow.ERROR_DETAIL_TOKEN);
            BPELUtils.addExtensionAttribute(bpelCatch, "faultDetailsVar", faultDetailVar); //$NON-NLS-1$
            bpelCatch.setActivity(theMappingActivity);
            faultHandler.getCatch().add(bpelCatch);
        } else {
            Catch bpelCatch = BPELFactory.eINSTANCE.createCatch();
            String tns = wsoInfo != null && faultVariable != null && faultVariable.getMessageType() != null ? 
            		faultVariable.getMessageType().getEnclosingDefinition().getTargetNamespace() : null;
			QName faultName = new QName(tns, errorCode);
			bpelCatch.setFaultName(faultName);
            bpelCatch.setActivity(theMappingActivity);
            if (faultVariable != null) {
                bpelCatch.setFaultVariable(faultVariable);
                bpelCatch.setFaultMessageType(faultVariable.getMessageType());
            }
            if(isDataMapperForCatchError)
            {
                /*
                 * XPD-8006 Add var_errorCode and var_errorDetail variables for catch error Data Mapper.
                 */
                
                String faultNameVar = FaultNamingConvention.getFaultVariableName(ConvertThrow.ERROR_CODE_TOKEN);
                BPELUtils.addExtensionAttribute(bpelCatch, "faultNameVar", faultNameVar); //$NON-NLS-1$
                String faultDetailVar = FaultNamingConvention.getFaultVariableName(ConvertThrow.ERROR_DETAIL_TOKEN);
                BPELUtils.addExtensionAttribute(bpelCatch, "faultDetailsVar", faultDetailVar); //$NON-NLS-1$
            }
            faultHandler.getCatch().add(bpelCatch);
        }
		
		/*
		 * XPD-8018: return the mapping activity (so that caller can configure it.
		 */
		return theMappingActivity;
    }

    /**
     * Work out scope variables for each parameter thrown by the sub-process' throw error event.
     * 
     * @param fanOutAssign
     * @param errorCode
     * @param subProcessActivity
     * @param errorThrowerActivityParameters
     * 
     * @return Scope variables for each parameter thrown by the sub-process' throw error event.
     * 
     * @throws ConversionException
     */
    private org.eclipse.bpel.model.Variables populateBpelVariablesForThrownParameters(
            Assign fanOutAssign, String errorCode, Activity subProcessActivity,
            List<FormalParameter> errorThrowerActivityParameters)
            throws ConversionException {
        org.eclipse.bpel.model.Variables variables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();

        for (FormalParameter eachParam : errorThrowerActivityParameters) {

            String bxVariableName = N2PEConstants.NAME_PREFIX + eachParam.getName();

            /*
             * Add local variable to the list of variable to be added to the scope.
             */
            addLocalVariable(variables, eachParam, bxVariableName, subProcessActivity); 

            /*
             * Add copy to the fanOutAssign which which configure the BX version of each sub-process parameter.
             */
            org.eclipse.bpel.model.From from = BPELUtils.createFromVariableWithPart(FaultNamingConvention.getFaultVariableName(errorCode), eachParam.getName(), null);
            org.eclipse.bpel.model.To to = BPELUtils.createToVariable(bxVariableName);

            org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
            copy.setTo(to);
            copy.setFrom(from);

            if (copy != null) {
                fanOutAssign.getCopy().add(copy);
            }
        }
        return variables;
    }

    /**
     * Get the parameters implicitly/explicitly added to the throw error event of the called sub-process.
     * 
     * @param errorCode
     * @param errorThrower
     * 
     * @return The parameters implicitly/explicitly added to the throw error event of the called sub-process.
     */
    private List<FormalParameter> fetchThrownParameters(String errorCode,
            Object errorThrower) {
        List<FormalParameter> errorThrowerActivityParameters = new ArrayList<FormalParameter>();
        
        if (errorThrower instanceof Activity) {
            
            List<FormalParameter> thrownParams =
                    ProcessInterfaceUtil
                            .getAssociatedFormalParameters((Activity) errorThrower);
            
            if (thrownParams != null && thrownParams.size() > 0) {
                for (FormalParameter eachThrownParam : thrownParams) {
                
                    // Only allow map from OUT/INOUT error thrower params.
                    if (!ModeType.IN_LITERAL.equals(eachThrownParam.getMode())) {
                        errorThrowerActivityParameters.add(eachThrownParam);
                    }
                }
            }
            
        } else if (errorThrower instanceof InterfaceMethod) {
            InterfaceMethod method = (InterfaceMethod) errorThrower;
            ErrorMethod errorMethod = null;

            for (ErrorMethod em : method.getErrorMethods()) {
                if (errorCode.equals(em.getErrorCode())) {
                    errorMethod = em;
                }
            }

            if (errorMethod != null) {

                List<FormalParameter> thrownParams =
                        ProcessInterfaceUtil
                                .getErrorMethodAssociatedFormalParameters(errorMethod);
                
                if (thrownParams != null && thrownParams.size() > 0) {
                    for (FormalParameter tp : thrownParams) {
                
                        // Only allow map from OUT/INOUT error thrower params.
                        if (!ModeType.IN_LITERAL.equals(tp.getMode())) {
                            errorThrowerActivityParameters.add(tp);
                        }
                    }
                }
            }
        }
        return errorThrowerActivityParameters;
    }
    
    /**
     * @param catchErrorEvent
     * @return Either ErrorCatchType.CATCH_ALL/CATCH_BY_NAME or EndErrorEvent
     *         activity (if error thrown by such) or null catch specific but
     *         thrower cannot be found.
     */
    private Object getCatchTypeOrSpecificErrorEndEvent(
            Activity catchErrorEvent) {
        //
        // Check if it's a catch all, catch by name or catch
        // specific error thrown by end error event.
        //
        ErrorCatchType catchType =
                BpmnCatchableErrorUtil.getCatchType(catchErrorEvent);
        if (ErrorCatchType.CATCH_ALL.equals(catchType)
                || ErrorCatchType.CATCH_BY_NAME.equals(catchType)) {
            return catchType;

        } else if (ErrorCatchType.CATCH_SPECIFIC.equals(catchType)) {
            //
            // If it's catch specific check thrower is end
            // error.
            Object thrower =
                    BpmnCatchableErrorUtil.getErrorThrower(catchErrorEvent);
            if (thrower instanceof Activity) {
                Activity throwAct = (Activity) thrower;

                if (throwAct.getEvent() instanceof EndEvent) {
                    if (ResultType.ERROR_LITERAL.equals(((EndEvent) throwAct
                            .getEvent()).getResult())) {
                        return throwAct;
                    }
                }
            } else if (thrower instanceof InterfaceMethod) {
                return thrower;
            }
        }

        return null;
    }
    
    /**
     * Add local variable pertaining to the specified process relevant data to the list of variables to be added in the scope.
     * 
     * @param variables
     * @param param
     * @param varName
     * @param xpdlActivity
     * @throws ConversionException
     */
    private void addLocalVariable(org.eclipse.bpel.model.Variables variables,
            ProcessRelevantData param, String varName, Activity xpdlActivity) throws ConversionException {
        if (param != null) {
            org.eclipse.bpel.model.Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
            variable.setName(varName);
            ConvertDataField.setDataTypeForVariable(xpdlActivity.getProcess(), variable, param.getDataType(), param.isIsArray());
            variables.getChildren().add(variable);
        }
    }
    
    /**
     * XPD-8010: Convert input mappings in the script data mapper to get a BPEL extension
     * activity.
     * <p>
     * Basic structure of the extension activity would look like..
     * <p>
     * {bpws:extensionActivity}
     * <p>
     * {tibex:extActivity name="DataMapperScript"}
     * <p>
     * Contains the script representation of the web-service input mappings.
     * <p>
     * {/tibex:extActivity}
     * <p>
     * {/bpws:extensionActivity}
     * <p>
     * 
     * @return BPEL extension activity with converted input mappings in the
     *         script data mapper or <code>null</code> if there are no data
     *         mappings.
     */
    private ExtensionActivity createDataMapperMappingScript(
            ScriptDataMapper dataMapperInputMappings) {

        org.eclipse.bpel.model.ExtensionActivity extensionAct = null;

        String script = null;

        if (dataMapperInputMappings instanceof ScriptDataMapper) {
            ScriptDataMapper sdmInp = dataMapperInputMappings;

            script =
                    new DataMapperJavascriptGenerator()
                            .convertMappingsToJavascript(sdmInp);
        }

        if (script != null && !script.trim().isEmpty()) {
            Script scriptForText = createScriptForText(script);

            extensionAct =
                    BPELUtils
                            .createExtensionActivityFromEmfObject(scriptForText,
                                    false);

            extensionAct.setName(context
                    .genUniqueActivityName("DataMapperScript")); //$NON-NLS-1$
        }

        return extensionAct;
    }
    
    /**
    * XPD-8010: Construct a script for the specified script text.
    * 
    * @param text
    * 
    * @return a script for the specified script text.
    */
   private Script createScriptForText(String text) {
       Script script = ExtensionsFactory.eINSTANCE.createScript();
       script.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
       script.setExpression(text);
       return script;
   }

   /*
    * Sid ACE-194: We don't do web-service any more in ACE - removed/commented WS related code
    */
//
//    private WebServiceOperationInfo getWebServiceOperationInfo() throws ConversionException {
//        Object errorThrower = BpmnCatchableErrorUtil.getErrorThrower(eventAct);
//		if (!(errorThrower instanceof Activity)) {
//    		return null;
//		}
//    	com.tibco.xpd.xpdl2.Activity targetActivity = (Activity) errorThrower;
//		ActivityMessageProvider messageAdapter = ActivityMessageProviderFactory.INSTANCE.getMessageProvider(targetActivity);
//		if (messageAdapter != null) {
//			WebServiceOperation wso = messageAdapter.getWebServiceOperation(targetActivity);
//			if (wso != null) {
//				try {
//					return context.getWebServiceOperationInfo(wso);
//				} catch (ConversionException e) {
//					context.logError(
//							"Cannot retrieve web service operation from <" //$NON-NLS-1$
//									+ targetActivity.getName() + ">: " //$NON-NLS-1$
//									+ e.getMessage(), e);
//					throw e;
//				}
//			}
//		}
//		
//		return null;
//    }

}
