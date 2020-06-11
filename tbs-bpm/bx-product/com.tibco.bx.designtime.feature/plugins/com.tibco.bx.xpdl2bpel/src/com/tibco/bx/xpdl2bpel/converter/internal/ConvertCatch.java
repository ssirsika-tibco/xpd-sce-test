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
		
		//  Sid ACE-3834 no longer requried...
		// Object errorThrower = BpmnCatchableErrorUtil.getErrorThrower(eventAct);
        
		Activity attachedToTask = BpmnCatchableErrorUtil.getAttachedToTask(eventAct);
        boolean isAttachedToGlobalData = XPDLUtils.isGlobalDataTask(attachedToTask);
        boolean isDeclaredFault = false;
        boolean isDataMapperForCatchError=false;
        
       org.eclipse.bpel.model.Activity theMappingActivity = null;

       
	
		if (!isDeclaredFault && catchErrorMappings != null && catchErrorMappings.getMessage() != null) {

		    /*
		     * XPD-8006: Saket: Handle Data Mappings for CATCH ALL and CATCH SUB-PROCESS ERROR, 
		     * Sid : (always was) Case-operation and Email service tasks too (although these have no out params so are effectively treated same as catch-all).
		     */
		    Message cemMessage = catchErrorMappings.getMessage();
		    
		    Object sdmObj=Xpdl2ModelUtil.getOtherElement(cemMessage, XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings());
		    
		    if(sdmObj instanceof ScriptDataMapper)
		    {
		        /*
		         * DataMapper mappings (only thing supported in ACE V5 
		         */
		        isDataMapperForCatchError=true;
		        
	            /*
	             * Sid ACE-3834 faultNameVar and faultDetailsVar are now supplied via the PE-generated "parameters"
	             * object as $ERROR_CODE and $ERROR_DETAIL properties.
	             */

		        ScriptDataMapper sdm=(ScriptDataMapper)sdmObj;
		        
		        ExtensionActivity scriptActivity = createDataMapperMappingScript(sdm);

		        if (scriptActivity != null) {
		            
		            Sequence sequence = BPELFactory.eINSTANCE.createSequence();
		            sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$
		            
		            fanOutAssign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$
		            sequence.getActivities().add(fanOutAssign);
		            sequence.getActivities().add(scriptActivity);

                    /*
                     * Sid ACE-3834There is no difference here between catch-all, catch specific sub-proc error and
                     * catch specific case operation task error.
                     * 
                     * Since PE now scopes a JSON "parameters" object with all required properties ($ERROR_CODE,
                     * $ERROR_DETAIL and any other sub-proc output parameters, then we no longer need an extra 'scope'
                     * to contain temporary _BX_variables as these aren't required anymore (parameters object contains
                     * these).
                     * 
                     * So the following code has been rationalised down to simply this...
                     */
                    theMappingActivity = sequence;
		            
		        }

                /* Sid ACE-3834 fault handling is no longer piggy backed on WSDL fault handling - faultVariableName/Type attribute no longer required. */

		    } else if(!cemMessage.getDataMappings().isEmpty()) {
		        /*
		         * Sid ACE-3838 - commented code to save confusion, non-datamapper mappings no longer supported.
		         */
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
            /*
             * Catch all on any task type.
             */
            CatchAll catchAll = BPELFactory.eINSTANCE.createCatchAll();
			
            /*
             * Sid ACE-3834 faultNameVar and faultDetailsVar are now supplied via the PE-generated "parameters"
             * object as $ERROR_CODE and $ERROR_DETAIL properties.
             */
            
            catchAll.setActivity(theMappingActivity);
            faultHandler.setCatchAll(catchAll);
            
		}  else {
            /*
             * Catch specific (any type)
             * 
             * Sid ACE-3834 after removal of all (now) extraneous code, the old handling for WSDL catch-all and
             * Case-operation service task was exactly the same as the generic catch specific. So there was no need for
             * a separate else-if for this in the above code.
             */
            Catch bpelCatch = BPELFactory.eINSTANCE.createCatch();
            
            /*
             * Sid ACE-3834 we no longer need faultVariableName/Type as we don't piggyback on WSDL Catch approach for
             * catch error. So there can't be any tns anymore.
             */

	        QName faultName = new QName(errorCode);

			bpelCatch.setFaultName(faultName);
            bpelCatch.setActivity(theMappingActivity);
            
            /*
             * Sid ACE-3834 fault handling is no longer piggy backed on WSDL fault handling -
             * faultVariable/faultMessageType/faultDetailsVar attribute no longer required.
             */
            
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
    /*
     * Sid ACE-3834 No longer required. Subproc Thrown error params are scoped in a "parameters" object by PE for
     * mapping script purposes.
     */
//    private org.eclipse.bpel.model.Variables populateBpelVariablesForThrownParameters(
//            Assign fanOutAssign, String errorCode, Activity subProcessActivity,
//            List<FormalParameter> errorThrowerActivityParameters)
//            throws ConversionException {
//        org.eclipse.bpel.model.Variables variables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
//
//        for (FormalParameter eachParam : errorThrowerActivityParameters) {
//
//            String bxVariableName = N2PEConstants.NAME_PREFIX + eachParam.getName();
//
//            /*
//             * Add local variable to the list of variable to be added to the scope.
//             */
//            addLocalVariable(variables, eachParam, bxVariableName, subProcessActivity); 
//
//            /*
//             * Add copy to the fanOutAssign which which configure the BX version of each sub-process parameter.
//             */
//            org.eclipse.bpel.model.From from = BPELUtils.createFromVariableWithPart(FaultNamingConvention.getFaultVariableName(errorCode), eachParam.getName(), null);
//            org.eclipse.bpel.model.To to = BPELUtils.createToVariable(bxVariableName);
//
//            org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
//            copy.setTo(to);
//            copy.setFrom(from);
//
//            if (copy != null) {
//                fanOutAssign.getCopy().add(copy);
//            }
//        }
//        return variables;
//    }

    /**
     * Get the parameters implicitly/explicitly added to the throw error event of the called sub-process.
     * 
     * @param errorCode
     * @param errorThrower
     * 
     * @return The parameters implicitly/explicitly added to the throw error event of the called sub-process.
     */
    /*
     * Sid ACE-3834 No longer required. Subproc Thrown error params are scoped in a "parameters" object by PE for
     * mapping script purposes.
     */
//    private List<FormalParameter> fetchThrownParameters(String errorCode,
//            Object errorThrower) {
//        List<FormalParameter> errorThrowerActivityParameters = new ArrayList<FormalParameter>();
//        
//        if (errorThrower instanceof Activity) {
//            
//            List<FormalParameter> thrownParams =
//                    ProcessInterfaceUtil
//                            .getAssociatedFormalParameters((Activity) errorThrower);
//            
//            if (thrownParams != null && thrownParams.size() > 0) {
//                for (FormalParameter eachThrownParam : thrownParams) {
//                
//                    // Only allow map from OUT/INOUT error thrower params.
//                    if (!ModeType.IN_LITERAL.equals(eachThrownParam.getMode())) {
//                        errorThrowerActivityParameters.add(eachThrownParam);
//                    }
//                }
//            }
//            
//        } else if (errorThrower instanceof InterfaceMethod) {
//            InterfaceMethod method = (InterfaceMethod) errorThrower;
//            ErrorMethod errorMethod = null;
//
//            for (ErrorMethod em : method.getErrorMethods()) {
//                if (errorCode.equals(em.getErrorCode())) {
//                    errorMethod = em;
//                }
//            }
//
//            if (errorMethod != null) {
//
//                List<FormalParameter> thrownParams =
//                        ProcessInterfaceUtil
//                                .getErrorMethodAssociatedFormalParameters(errorMethod);
//                
//                if (thrownParams != null && thrownParams.size() > 0) {
//                    for (FormalParameter tp : thrownParams) {
//                
//                        // Only allow map from OUT/INOUT error thrower params.
//                        if (!ModeType.IN_LITERAL.equals(tp.getMode())) {
//                            errorThrowerActivityParameters.add(tp);
//                        }
//                    }
//                }
//            }
//        }
//        return errorThrowerActivityParameters;
//    }
    
    /**
     * @param catchErrorEvent
     * @return Either ErrorCatchType.CATCH_ALL/CATCH_BY_NAME or EndErrorEvent
     *         activity (if error thrown by such) or null catch specific but
     *         thrower cannot be found.
     */
    /*
     * Sid ACE-3834 No longer required. Subproc Thrown error params are scoped in a "parameters" object by PE for
     * mapping script purposes.
     */
//    private Object getCatchTypeOrSpecificErrorEndEvent(
//            Activity catchErrorEvent) {
//        //
//        // Check if it's a catch all, catch by name or catch
//        // specific error thrown by end error event.
//        //
//        ErrorCatchType catchType =
//                BpmnCatchableErrorUtil.getCatchType(catchErrorEvent);
//        if (ErrorCatchType.CATCH_ALL.equals(catchType)
//                || ErrorCatchType.CATCH_BY_NAME.equals(catchType)) {
//            return catchType;
//
//        } else if (ErrorCatchType.CATCH_SPECIFIC.equals(catchType)) {
//            //
//            // If it's catch specific check thrower is end
//            // error.
//            Object thrower =
//                    BpmnCatchableErrorUtil.getErrorThrower(catchErrorEvent);
//            if (thrower instanceof Activity) {
//                Activity throwAct = (Activity) thrower;
//
//                if (throwAct.getEvent() instanceof EndEvent) {
//                    if (ResultType.ERROR_LITERAL.equals(((EndEvent) throwAct
//                            .getEvent()).getResult())) {
//                        return throwAct;
//                    }
//                }
//            } else if (thrower instanceof InterfaceMethod) {
//                return thrower;
//            }
//        }
//
//        return null;
//    }
    
    /**
     * Add local variable pertaining to the specified process relevant data to the list of variables to be added in the scope.
     * 
     * @param variables
     * @param param
     * @param varName
     * @param xpdlActivity
     * @throws ConversionException
     */
    /*
     * Sid ACE-3834 No longer required. Subproc Thrown error params are scoped in a "parameters" object by PE for
     * mapping script purposes.
     */
//    private void addLocalVariable(org.eclipse.bpel.model.Variables variables,
//            ProcessRelevantData param, String varName, Activity xpdlActivity) throws ConversionException {
//        if (param != null) {
//            org.eclipse.bpel.model.Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
//            variable.setName(varName);
//            ConvertDataField.setDataTypeForVariable(xpdlActivity.getProcess(), variable, param.getDataType(), param.isIsArray());
//            variables.getChildren().add(variable);
//        }
//    }
    
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
