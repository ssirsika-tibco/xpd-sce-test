package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Throw;
import org.eclipse.bpel.model.To;
import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.WSDLFactory;

import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

public class ConvertThrow {
	
	public static final String ERROR_CODE_VAR = "errorCode"; //$NON-NLS-1$
	public static final String ERROR_DETAIL_VAR = "errorDetail"; //$NON-NLS-1$
	public static final String ERROR_CODE_TOKEN = "$ERRORCODE"; //$NON-NLS-1$
	public static final String ERROR_DETAIL_TOKEN = "$ERRORDETAIL"; //$NON-NLS-1$	
	
	public static class FaultNamingConvention {
		public static String getFaultTypeName(NamedElement xpdlActivity) {
			String typeName = xpdlActivity.getName() != null && xpdlActivity.getName().length() > 0 ?
					xpdlActivity.getName() : xpdlActivity.getId();
			return typeName + "Fault"; //$NON-NLS-1$
		}
		public static String getFaultName(String errorCode) {
			return "fault_" + errorCode; //$NON-NLS-1$
		}
		public static String getFaultVariableName(String errorCode) {
			if (errorCode == null || errorCode.length() == 0) {
				return "var_" + ERROR_CODE_VAR; //$NON-NLS-1$ 
			}
			if (ERROR_CODE_TOKEN.equals(errorCode)) {
				return "var_" + ERROR_CODE_VAR; //$NON-NLS-1$ 
			}
			if (ERROR_DETAIL_TOKEN.equals(errorCode)) {
				return "var_" + ERROR_DETAIL_VAR; //$NON-NLS-1$ 
			}
			return "var_" + errorCode; //$NON-NLS-1$ 
		}
	}

	public static org.eclipse.bpel.model.Activity convertResultErrorToThrow(
			ConverterContext context, Activity xpdlActivity, ResultError resultError) throws ConversionException {
		String errorCode = resultError.getErrorCode();
		org.eclipse.bpel.model.Activity bpelActivity;
        org.eclipse.bpel.model.Activity theMappingActivity = null;
        
		com.tibco.xpd.xpdl2.Message faultMessage = XPDLUtils.getFaultMessage(resultError);
		if (faultMessage != null) {
			//the fault has been defined in the WSDL
			String faultName = faultMessage.getFaultName();
			WebServiceOperationInfo wsoInfo = getWebServiceOperationInfo(context, xpdlActivity, resultError);
			
            /*
             * Sid XPD-8010. ConvertFaultDataMapping now only returns 'some'
             * kind of activity that does the mapping, which can differ
             * depending on the grammar. 
             */
            ConvertFaultDataMapping mappingConverter =
                    new ConvertFaultDataMapping(context, xpdlActivity, wsoInfo,
                            faultName);
            theMappingActivity =
                    mappingConverter.convertDataMappingsToAssign(faultMessage);

 			bpelActivity = ConvertWebService.convertWebServiceOperationToBPELReplyWithFault(
					context, wsoInfo, xpdlActivity, faultMessage);
			
    	} else {
    	    
    		Set<String> optionalParameters = XPDLUtils.findOptionalVariables(xpdlActivity);
    		Fault fault = getFault(context, xpdlActivity, resultError);
    		String faultVariableName = FaultNamingConvention.getFaultVariableName(errorCode);
            
            // TODO Sid XPD-8010 - NEED TO HANDLE THROW Sub-Process Error for Datamapper as well as JavaScript PROPERLY
            // for now am just making work for JavaScript (and code below) by seting theMappingActivity to the assignAct 
    		// (which is now made local to this use case.
			Assign assignAct = createFaultAssign(fault, faultVariableName, errorCode, optionalParameters);
			theMappingActivity = assignAct;
			
			XPDLUtils.findReferencedData(context, xpdlActivity, assignAct.getName(), DataReferenceContext.CONTEXT_MAPPING_IN);
			
	        Throw throwAct = BPELFactory.eINSTANCE.createThrow();
	        throwAct.setName("throw_" + errorCode); //$NON-NLS-1$
			throwAct.setFaultName(new QName(errorCode));
			if (assignAct != null && !assignAct.getCopy().isEmpty()) {
				org.eclipse.bpel.model.Variable faultVariable = 
		        	org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
				faultVariable.setName(faultVariableName);
				faultVariable.setMessageType((org.eclipse.wst.wsdl.Message) fault.getMessage());

				throwAct.setFaultVariable(faultVariable);
				context.addVariable(faultVariable);
			}
			bpelActivity = throwAct;
    	}
		
        Scope scope = BPELFactory.eINSTANCE.createScope();
        scope.setName(xpdlActivity.getName());      
		if (theMappingActivity != null) {
		    
	        Sequence seq = BPELFactory.eINSTANCE.createSequence();
	        seq.setName("sequence_" + errorCode); //$NON-NLS-1$
	        seq.getActivities().add(theMappingActivity);
	        seq.getActivities().add(bpelActivity);
	        scope.setActivity(seq);
		} else {
	        scope.setActivity(bpelActivity);
		}
		
        org.eclipse.bpel.model.Variables variables = context.getVariables(xpdlActivity);
        scope.setVariables(variables);
        return scope;
	}

    private static WebServiceOperationInfo getWebServiceOperationInfo(
    		ConverterContext context, Activity xpdlActivity, ResultError resultError) throws ConversionException {
		String requestActivityId = XPDLUtils.getRequestActivityId(resultError);

        if (requestActivityId != null) {
        	com.tibco.xpd.xpdl2.Activity targetActivity = xpdlActivity.getProcess().getActivity(requestActivityId);
			if (targetActivity == null) {
    			throw new ConversionException("request activity cannot be found: " + requestActivityId);
			}
			ActivityMessageProvider messageAdapter = ActivityMessageProviderFactory.INSTANCE
					.getMessageProvider(targetActivity);
			if (messageAdapter != null) {
				WebServiceOperation wso = messageAdapter.getWebServiceOperation(targetActivity);
				if (wso != null) {
					try {
						return context.getWebServiceOperationInfo(wso);
					} catch (ConversionException e) {
						context.logError(
								"Cannot retrieve web service operation from <"
										+ targetActivity.getName() + ">: "
										+ e.getMessage(), e);
						throw e;
					}
				}
			}
        }

        return null;
    }

	private static Assign createFaultAssign(Fault fault, String faultVariableName, String errorCode, Set<String> optionalParameters) {
		Assign assignAct = BPELFactory.eINSTANCE.createAssign();
		assignAct.setName("assign_" + errorCode.toLowerCase()); //$NON-NLS-1$
		Collection<Part> parts = fault.getMessage().getParts().values();
		for (Part part : parts) {
			From from = ConvertThrow.ERROR_CODE_VAR.equals(part.getName()) ?
				BPELUtils.createFromLiteral(errorCode) :
				BPELUtils.createFromVariable(part.getName());
			To to = BPELUtils.createToVariableWithPart(faultVariableName, part.getName(), null);

			Copy assignCopy = BPELFactory.eINSTANCE.createCopy();
			assignCopy.setFrom(from);
			assignCopy.setTo(to);
			if (optionalParameters.contains(part.getName())) {
				assignCopy.setIgnoreMissingFromData(true);
			}
			assignAct.getCopy().add(assignCopy);
		}
		return assignAct;
	}
	
	public static Fault getFault(ConverterContext context, Activity xpdlActivity, ResultError resultError) {
		String implementsId = XPDLUtils.getImplements(xpdlActivity);
		if (implementsId != null) {
            ProcessInterface throwingProcessInterface = ProcessInterfaceUtil.getImplementedProcessInterface(xpdlActivity.getProcess());
			for (StartMethod method : throwingProcessInterface.getStartMethods()) {
				for (ErrorMethod errorMethod: method.getErrorMethods()) {
					if (implementsId.equals(errorMethod.getId())) {
						return getFault(context, throwingProcessInterface, method, resultError);
					}
				}
			}
			for (IntermediateMethod method : throwingProcessInterface.getIntermediateMethods()) {
				for (ErrorMethod errorMethod: method.getErrorMethods()) {
					if (implementsId.equals(errorMethod.getId())) {
						return getFault(context, throwingProcessInterface, method, resultError);
					}
				}
			}
		}
		
    	Process process = xpdlActivity.getProcess();

		Definition definition = context.getFaultDefinition(process);
		PortType portType = (PortType) definition.getPortType(definition.getQName());
		Operation operation = (Operation) portType.getOperation(process.getName(), null, null);
		if (operation == null) {
			operation = createOperation(portType, process.getName());
		}
		
		String faultName = FaultNamingConvention.getFaultName(resultError.getErrorCode());
		Fault fault = (Fault) operation.getFault(faultName);
		if (fault == null) {
			String faultTypeName = FaultNamingConvention.getFaultTypeName(xpdlActivity);
			QName faultMessageName = new QName(portType.getEnclosingDefinition().getTargetNamespace(), faultTypeName);
			Message faultMessage = (Message) portType.getEnclosingDefinition().getMessage(faultMessageName);
			if (faultMessage == null) {
				faultMessage = createFaultMessage(portType.getEnclosingDefinition().getTargetNamespace(), xpdlActivity);
				portType.getEnclosingDefinition().addMessage(faultMessage);
			}

			fault = createFault(operation, faultMessage, faultName);
		}
		return fault;
	}

	public static Fault getFault(ConverterContext context, ProcessInterface processInterface, InterfaceMethod interfaceMethod, ResultError resultError) {
		Definition definition = context.getFaultDefinition(processInterface);
		PortType portType = (PortType) definition.getPortType(definition.getQName());
		Operation operation = (Operation) portType.getOperation(processInterface.getName(), null, null);
		if (operation == null) {
			operation = createOperation(portType, processInterface.getName());
		}
		
		String faultName = FaultNamingConvention.getFaultName(resultError.getErrorCode());
		Fault fault = (Fault) operation.getFault(faultName);
		if (fault == null) {
			String faultTypeName = FaultNamingConvention.getFaultTypeName(interfaceMethod);
			QName faultMessageName = new QName(portType.getEnclosingDefinition().getTargetNamespace(), faultTypeName);
			Message faultMessage = (Message) portType.getEnclosingDefinition().getMessage(faultMessageName);
			if (faultMessage == null) {
				faultMessage = createFaultMessage(portType.getEnclosingDefinition().getTargetNamespace(), interfaceMethod);
				portType.getEnclosingDefinition().addMessage(faultMessage);
			}

			fault = createFault(operation, faultMessage, faultName);
		}
		return fault;
	}

	private static Operation createOperation(PortType portType, String operationName) {
		Operation operation = WSDLFactory.eINSTANCE.createOperation();
		operation.setName(operationName);
		portType.addOperation(operation);
		return operation;
	}

	private static Message createFaultMessage(String targetNamespace, Activity xpdlActivity) {
		Message faultMessage = WSDLFactory.eINSTANCE.createMessage();
		String faultTypeName = FaultNamingConvention.getFaultTypeName(xpdlActivity);
		faultMessage.setQName(new QName(targetNamespace, faultTypeName));

		Part part = WSDLFactory.eINSTANCE.createPart();
		part.setName(ERROR_CODE_VAR);
		part.setTypeName(new QName(N2PEConstants.XSD_NAMESPACE_URI, "string")); //$NON-NLS-1$
		faultMessage.addPart(part);

    	Collection<ActivityInterfaceData> aids = ActivityInterfaceDataUtil.getActivityInterfaceData(xpdlActivity);
		for (ActivityInterfaceData aid : aids) {
			ProcessRelevantData prd = aid.getData();
			if (prd instanceof FormalParameter) {
				FormalParameter parameter = (FormalParameter) prd;
				switch (parameter.getMode().getValue()) {
				case ModeType.OUT:
				case ModeType.INOUT:
					String dataType = convertDataType2WSDL(parameter.getDataType());
					if (dataType != null) {
						part = WSDLFactory.eINSTANCE.createPart();
						part.setName(parameter.getName().replace(" ", "_")); //$NON-NLS-1$ //$NON-NLS-2$
						part.setTypeName(new QName(N2PEConstants.XSD_NAMESPACE_URI, dataType));
						faultMessage.addPart(part);
					}
					break;
				default:
					break;
				}
			}
		}
		
		return faultMessage;
	}

	private static Message createFaultMessage(String targetNamespace, InterfaceMethod interfaceMethod) {
		Message faultMessage = WSDLFactory.eINSTANCE.createMessage();
		String faultTypeName = FaultNamingConvention.getFaultTypeName(interfaceMethod);
		faultMessage.setQName(new QName(targetNamespace, faultTypeName));

		Part part = WSDLFactory.eINSTANCE.createPart();
		part.setName(ERROR_CODE_VAR);
		part.setTypeName(new QName(N2PEConstants.XSD_NAMESPACE_URI, "string")); //$NON-NLS-1$
		faultMessage.addPart(part);

		EList<ErrorMethod> errorMethods = interfaceMethod.getErrorMethods();
		if (errorMethods != null) {
			Map<String, ActivityInterfaceData> interfaceDataMap = new HashMap<String, ActivityInterfaceData>();
			for (ErrorMethod errorMethod : errorMethods) {
				Collection<ActivityInterfaceData> interfaceEventInterfaceData = ActivityInterfaceDataUtil.getInterfaceEventInterfaceData(errorMethod);
		    	if (interfaceEventInterfaceData != null) {
		    		for (ActivityInterfaceData aif : interfaceEventInterfaceData) {
		    			interfaceDataMap.put(aif.getName(), aif);
		    		}
		    	}				
			}
			for (ActivityInterfaceData aif: interfaceDataMap.values()) {
    			ModeType mode = aif.getMode();
    			switch (mode.getValue()) {
    			case ModeType.OUT:
    			case ModeType.INOUT:
					String dataType = convertDataType2WSDL(aif.getData().getDataType());
					if (dataType != null) {
	    				part = WSDLFactory.eINSTANCE.createPart();
	    				part.setName(aif.getName().replace(" ", "_")); //$NON-NLS-1$ //$NON-NLS-2$
	    				part.setTypeName(new QName(N2PEConstants.XSD_NAMESPACE_URI, dataType));
	    				faultMessage.addPart(part);
					}
    				break;
    			default:
    				break;
    			}
			}
		}
		
		return faultMessage;
	}

	private static Fault createFault(Operation operation, Message faultMessage, String faultName) {
		Fault fault = WSDLFactory.eINSTANCE.createFault();
		fault.setName(faultName); 
		fault.setMessage(faultMessage);
		operation.addFault(fault);
		return fault;
	}

	/**
	 * convert dataType to wsdl type the result is simple type name,like:
	 * <code>string</code>
	 * 			<code>boolean</code> .................... if you
	 * use the result as a type of element you should set the prefix
	 * namespace,such as: <code>*.setQname(new Qname(XSD,result));</code>
	 * 
	 * @param dataType
	 * @return
	 */
	private static String convertDataType2WSDL(DataType dataType) {
		String result = null;
		if (dataType != null) {
			if (dataType instanceof BasicType) {
				BasicTypeType basicTypeType = ((BasicType) dataType).getType();
				if (basicTypeType != null) {
					int basicTypeTypeValue = basicTypeType.getValue();
					// These String constants should not be translated
					switch (basicTypeTypeValue) {
					case BasicTypeType.BOOLEAN:
						result = "boolean"; //$NON-NLS-1$
						break;
					case BasicTypeType.DATETIME:
						// TODO Becomes dateTime (how does dateTime data map?)
						result = "dateTime"; //$NON-NLS-1$
						break;
					case BasicTypeType.FLOAT:
						result = "double"; //$NON-NLS-1$
						break;
					case BasicTypeType.INTEGER:
						result = "integer"; //$NON-NLS-1$
						break;
					case BasicTypeType.PERFORMER:
						// TODO this type may be changed
						result = "string"; //$NON-NLS-1$
						break;
					case BasicTypeType.REFERENCE:
						// throw new
						// UnsupportedConversionException(ConverterActivator.createWarningStatus(
						// "Basic type of 'Reference' cannot be converted to
						// BPEL", null));
						// TODO this type may be changed
						result = "IDREF"; //$NON-NLS-1$
						break;
					case BasicTypeType.STRING:
						result = "string"; //$NON-NLS-1$
						break;
					}
				}
			}
		}
		return result;
	}
	
}
