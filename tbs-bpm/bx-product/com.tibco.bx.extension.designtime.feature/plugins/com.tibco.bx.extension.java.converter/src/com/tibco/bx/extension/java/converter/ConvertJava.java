package com.tibco.bx.extension.java.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.uml2.uml.Property;

import com.tibco.bx.extensions.java.JavaExtensionModel.FactoryClassType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaClassType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaExtensionModelFactory;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaMethodType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaParameterType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaParametersType;
import com.tibco.bx.extensions.java.JavaExtensionModel.JavaProjectType;
import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder2;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.CDSUtils;
import com.tibco.bx.xpdl2bpel.util.DataTypeUtil;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

public class ConvertJava implements IActivityConfigurationModelBuilder, IActivityConfigurationModelBuilder2 {

    /*
     * Sid ACE-194:  Currently we do not support 'Publish as REST service' for individual activities.
     * Something rather than deleting as we may want to introduce something similar later.
     */
    
//	private static final String DEFAULT_VERSION = "1.0.0.qualifier";
//
//	private static final String RESPONSE_RV = JavaConstants.RESPONSE_PREFIX + "." + JavaConstants.RETURN_VALUE_ID; //$NON-NLS-1$
//	private static final String RETURN_VALUE = "RETURN_VALUE"; //$NON-NLS-1$
//	private static final String INTERNAL_VAR_PREFIX = N2PEConstants.NAME_PREFIX;
//
//	private Activity xpdlActivity;
//	private EAIJava xpdJava;
//	private IJavaProject javaProject;
//
//	private Map<String, org.eclipse.bpel.model.Variable> variables = new HashMap<String, org.eclipse.bpel.model.Variable>();
//	private List<org.eclipse.bpel.model.Copy> inputMappings;
//	private List<org.eclipse.bpel.model.Copy> outputMappings;

	public EObject transformConfigModel(Activity xpdlActivity,
			Map<String, Participant> participantMap) throws ConversionException {
        /*
         * Sid ACE-194:  Currently we do not support 'Publish as REST service' for individual activities.
         * Something rather than deleting as we may want to introduce something similar later.
         */
        
	    throw new ConversionException("Unexpected inclusion of Java service tasks in source process.");
	    
//		this.xpdlActivity = xpdlActivity;
//		
//		Implementation implementation = xpdlActivity.getImplementation();
//		if (implementation instanceof Task) {
//			Task t = (Task) implementation;
//			if (t.getTaskService() != null) {
//				TaskService taskService = t.getTaskService();
//				this.xpdJava = EAIJavaModelUtil.getEAIJavaObj(taskService);
//    			if (this.xpdJava != null) {
//    				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(xpdJava.getProject());
//    				javaProject = JavaCore.create(project);
//
//    				return createJavaProjectType(taskService.getMessageIn(),
//							taskService.getMessageOut());
//				}
//			}
//		}
//		
//		return null;
	}

//
//	private JavaProjectType createJavaProjectType(Message messageIn, Message messageOut) {
//		JavaProjectType javaProjectType = JavaExtensionModelFactory.eINSTANCE.createJavaProjectType();
//
//		// transform correspondents
//		String projectName = xpdJava.getProject();
//		if (!projectName.equals("")) {
//			javaProjectType.setName(projectName);
//		}
//		IPluginModelBase[] workspaceModels = PluginRegistry.getWorkspaceModels();
//		IPluginModelBase referencedPlugin = null;
//		for (IPluginModelBase base : workspaceModels) {
//			String tmp = base.getUnderlyingResource().getProject().getName();
//			if (tmp.equals(projectName)) {
//				referencedPlugin = base;
//				break;
//			}
//		}
//
//		if(referencedPlugin!=null){
//			javaProjectType.setPluginId(referencedPlugin.getBundleDescription().getSymbolicName());
//			javaProjectType.setVersion(referencedPlugin.getBundleDescription().getVersion().toString());
//		}else{
//			javaProjectType.setPluginId(projectName);
//			javaProjectType.setVersion(DEFAULT_VERSION);
//		}
//		
//		JavaClassType javaClassType = createJavaClassType(messageIn, messageOut);
//		javaProjectType.setJavaClass(javaClassType);
//
//		FactoryClassType factoryClassType = createFactoryClassType();
//		javaProjectType.setFactoryClass(factoryClassType);
//
//		return javaProjectType;
//	}
//
//	private FactoryClassType createFactoryClassType() {
//		ClassType factory = xpdJava.getFactory();
//		if (factory == null) {
//			return null;
//		}
//		
//		FactoryClassType factoryClassType = JavaExtensionModelFactory.eINSTANCE
//				.createFactoryClassType();
//		factoryClassType.setQualifyName(factory.getName());
//
//		MethodType method = factory.getMethod();
//		if (method != null) {
//			JavaMethodType javaMethodType = createJavaMethodType(
//					factory.getName(), method, null, null);
//			factoryClassType.setJavaMethod(javaMethodType);
//		}
//		return factoryClassType;
//	}
//
//	private JavaClassType createJavaClassType(Message messageIn, Message messageOut) {
//		ClassType pojo = xpdJava.getPojo();
//		if (pojo == null) {
//			return null;
//		}
//		
//		JavaClassType javaClassType = JavaExtensionModelFactory.eINSTANCE.createJavaClassType();
//		javaClassType.setQualifyName(pojo.getName());
//
//		MethodType method = pojo.getMethod();
//		if (method != null) {
//			JavaMethodType javaMethodType = createJavaMethodType(
//					pojo.getName(), method, messageIn, messageOut);
//			javaClassType.setJavaMethod(javaMethodType);
//			
//			//add a local variable "RETURN_VALUE" only when there are a return type and post activity mappings
//			ParameterType returnType = method.getReturn();
//			if (returnType != null && getPostActivityMappings().length > 0) {
//				try {
//					String typeName = returnType.getClassName() != null ? returnType.getClassName() : getPojoClass(MappingDirection.OUT, RESPONSE_RV).getName();
//					org.eclipse.bpel.model.Variable returnVariable = createPojoVariable(RETURN_VALUE, typeName);
//					addVariable(returnVariable);
//				} catch (JavaModelException e) {
//					ConverterActivator.logError("Error occurred while converting the return value.", e);
//				}
//			}
//		}
//		return javaClassType;
//	}
//
//	private JavaMethodType createJavaMethodType(String qualifyClassName, MethodType method,
//			Message inputParameters, Message returnOutput) {
//
//		JavaMethodType javaMethodType = JavaExtensionModelFactory.eINSTANCE
//				.createJavaMethodType();
//		javaMethodType.setName(method.getName());
//
//		JavaParameterType returnType = createReturnType(method.getReturn(), returnOutput);
//		javaMethodType.setJavaReturn(returnType);
//
//		if (method.getParameters() != null) {
//			String[] parametersName = getParameterNames(qualifyClassName);
//			JavaParametersType parametersTypeType = createParameters(
//					inputParameters, method.getParameters(), parametersName);
//			javaMethodType.setJavaParameters(parametersTypeType);
//		}
//
//		return javaMethodType;
//	}
//
//	private JavaParametersType createParameters(Message inputParameters,
//			ParametersType parameters, String[] parametersName) {
//		JavaParametersType parametersTypeType = JavaExtensionModelFactory.eINSTANCE
//				.createJavaParametersType();
//		Iterator iterator = parameters.getParameter().iterator();
//
//		int index = 0;
//		while (iterator.hasNext()) {
//			ParameterType parameter = (ParameterType) iterator.next();
//			JavaParameterType parameterTypeType = JavaExtensionModelFactory.eINSTANCE
//					.createJavaParameterType();
//			parameterTypeType.setSignature(parameter.getSignature());
//			if (parameter.getClassName() != null) {
//				parameterTypeType.setQualifyClassName(parameter.getClassName());
//			} else {
//				parameterTypeType.setQualifyClassName(ParameterTypeEnum
//						.getType(parameter.getSignature()).getLabel());
//			}
//			if (inputParameters.getDataMappings() != null) {
//				String parameterName = parametersName[index];
//				for (DataMapping dm : inputParameters.getDataMappings()) {
//					if (parameterName.equals(getParameterName(dm))) {
//						String variableName = "%" + getVariableName(dm) + "%";
//						parameterTypeType.setVariableName(variableName);
//						break;
//					}
//				}
//			}
//			parametersTypeType.getJavaParameter().add(parameterTypeType);
//			index++;
//		}
//
//		if (inputParameters.getDataMappings() != null) {
//			inputMappings = new LinkedList<org.eclipse.bpel.model.Copy>();
//			for (DataMapping dm : inputParameters.getDataMappings()) {
//				try {
//					org.eclipse.bpel.model.Copy copy = convertDataMappingToCopy(dm);
//					if (copy != null) {
//						inputMappings.add(copy);
//					}
//				} catch (JavaModelException e) {
//					ConverterActivator.logError("Error occurred while converting the data mapping for the Java inputs.", e);
//				}
//			}
//		}
//		
//		return parametersTypeType;
//	}
//
//	private JavaParameterType createReturnType(ParameterType parameter, Message returnOutput) {
//		if (parameter == null) {
//			return null;
//		}
//		
//		JavaParameterType returnType = JavaExtensionModelFactory.eINSTANCE
//				.createJavaParameterType();
//		returnType.setSignature(parameter.getSignature());
//		if (parameter.getClassName() != null) {
//			returnType.setQualifyClassName(parameter.getClassName());
//		} else {
//			returnType.setQualifyClassName(ParameterTypeEnum.getType(
//					parameter.getSignature()).getLabel());
//		}
//		if (returnOutput != null && !returnOutput.getDataMappings().isEmpty()) {
//			outputMappings = new LinkedList<org.eclipse.bpel.model.Copy>();
//			Set<String> inlineVars = new HashSet<String>();
//			for (DataMapping dm : returnOutput.getDataMappings()) {
//				try {
//					org.eclipse.bpel.model.Copy copy = convertDataMappingToCopy(dm);
//					if (copy != null) {
//						outputMappings.add(copy);
//					}
//				} catch (JavaModelException e) {
//					ConverterActivator.logError("Error occurred while converting the data mapping for the Java method return.", e);
//				}
//				String variableName = getReturnVariableName(dm);
//				if (variableName != null) {
//					inlineVars.add(variableName);
//				}
//			}
//
//			String variableName = "";
//			for (String var : inlineVars) {
//				variableName += "%" + var + "% ";
//			}
//			if (!variableName.equals("")) {
//				returnType.setVariableName(variableName.trim());
//			}
//		}
//
//		return returnType;
//	}
//
//	private String getReturnVariableName(DataMapping dm) {
//    	boolean isScript = XPDLUtils.getScriptInformation(dm) != null;
//    	if (isScript) {
//			return RETURN_VALUE;
//    	}
//
//        String target = DataMappingUtil.getTarget(dm); // this is "to"
//        String script = DataMappingUtil.getScript(dm); // this is "from"
//
//        if (script.startsWith(RESPONSE_RV + ".")) { //$NON-NLS-1$
//			//this is a field under the special variable "returnValue", 
//			//so don't create a separate one 
//			return RETURN_VALUE;
//		} else if (script.startsWith(JavaConstants.PARAMETER_PREFIX)) {
//        	//the mapping is from the INOUT parameter, which already has a 
//        	//corresponding variable for the IN mapping
//        	return null;
//        } else { //script is RESPONSE_RV
//            if (target.indexOf(".") < 0) { //$NON-NLS-1$
//            	//we are mapping from the return value to a single field; 
//            	//make the target an inline return variable
//            	return target;
//            } else {
//    			return RETURN_VALUE;
//            }
//        }        
//	}
//
//	/**
//	 * name of process parameter
//	 * 
//	 * @param data
//	 * @return
//	 */
//	private String getVariableName(DataMapping dm) {
//        String target = removeParameterPrefix(DataMappingUtil.getTarget(dm)); // this is "to"
//        String script = DataMappingUtil.getScript(dm); // this is "from"
//    	boolean isScript = XPDLUtils.getScriptInformation(dm) != null;
//		boolean toSimpleVar = DataTypeUtil.isJavaIdentifier(target);
//		boolean fromSimpleVar = DataTypeUtil.isJavaIdentifier(script);
//    	
//		if (isScript) {
//    		if (!fromSimpleVar || !toSimpleVar) {
//    			// mapping from a script to a field of CDS "var"; return "_var"
//    			int tindex = target.indexOf(("."));
//    			String toVar = target;
//    			if (tindex >= 0) {
//    				toVar = target.substring(0, target.indexOf(".")); //$NON-NLS-1$
//    			}
//    			return INTERNAL_VAR_PREFIX + toVar;
//    		}
//    		int pos = target.lastIndexOf("[");
//    		return pos < 0 ? target : target.substring(0, pos);
//    	}
//    	
//		if (fromSimpleVar) {
//			if (toSimpleVar) {
//				//from simpe var to simple var
//				return script;
//			} else {
//				//from simpe var to a field in CDS
//    			String toVar = target.substring(0, target.indexOf(".")); //$NON-NLS-1$
//    			return INTERNAL_VAR_PREFIX + toVar;
//			}
//		} else {
//			if (toSimpleVar) {
//				//from a field in CDS to a simple var
//				return INTERNAL_VAR_PREFIX + target;
//			} else {
//				//from a field in CDS to a field in CDS
//    			String toVar = target.substring(0, target.indexOf(".")); //$NON-NLS-1$
//				return INTERNAL_VAR_PREFIX + toVar;
//			}			
//		}
//	}
//
//	/**
//	 * name of method parameter
//	 * 
//	 * @param data
//	 * @return
//	 */
//	private String getParameterName(DataMapping data) {
//		String expr = removeParameterPrefix(data.getFormal());
//		int pos = expr.indexOf("."); //$NON-NLS-1$
//		if (pos > 0) {
//			return expr.substring(0, pos);
//		}
//		pos = expr.lastIndexOf("[");
//		return pos < 0 ? expr : expr.substring(0, pos);
//	}
//
//	private String[] getParameterNames(String qualifyClassName) {
//		try {
//			IType type = javaProject.findType(qualifyClassName);
//			IMethod method = getMethodFromType(type);
//			if (method != null) {
//				return method.getParameterNames();
//			}
//		} catch (JavaModelException e) {
//			ConverterActivator.logError(
//					"Failed to look up the Java type for " + this.xpdJava.getPojo().getName(), e);
//		}
//		return null;
//	}
//
//	private IMethod getMethodFromType(IType type) {
//		try {
//			IMethod[] methods = type.getMethods();
//			for (IMethod method : methods) {
//				if (isThisMethod(method)) {
//					return method;
//				}
//			}
//		} catch (JavaModelException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private boolean isThisMethod(IMethod method) {
//		MethodType pojoMethod = xpdJava.getPojo().getMethod();
//		if (!pojoMethod.getName().equals(method.getElementName())) {
//			return false;
//		}
//		String[] types = method.getParameterTypes();
//		ParametersType parameters = pojoMethod.getParameters();
//		if (types.length == 0
//				&& (parameters == null || parameters.getParameter().size() == 0)) {
//			return true;
//		} else if (types.length != 0
//				&& (parameters == null || parameters.getParameter().size() == 0)) {
//			return false;
//		}
//		List<ParameterType> parameter = parameters.getParameter();
//		if (types.length != parameter.size()) {
//			return false;
//		}
//		for (int i = 0; i < types.length; i++) {
//			if (!types[i].equals(parameter.get(i).getSignature())) {
//				return false;
//			}
//		}
//		return true;
//	}
//
	@Override
	public org.eclipse.bpel.model.Variable[] getVariables() {
        /*
         * Sid ACE-194:  Currently we do not support 'Publish as REST service' for individual activities.
         * Something rather than deleting as we may want to introduce something similar later.
         */
        
        throw new RuntimeException("Unexpected inclusion of Java service tasks in source process.");
//		return variables.values().toArray(new org.eclipse.bpel.model.Variable[variables.size()]);
	}

	@Override
	public org.eclipse.bpel.model.Copy[] getPreActivityMappings() {
        /*
         * Sid ACE-194:  Currently we do not support 'Publish as REST service' for individual activities.
         * Something rather than deleting as we may want to introduce something similar later.
         */
        
        throw new RuntimeException("Unexpected inclusion of Java service tasks in source process.");
//		return inputMappings != null ?
//				inputMappings.toArray(new org.eclipse.bpel.model.Copy[inputMappings.size()]) :
//					new org.eclipse.bpel.model.Copy[0];
	}

	@Override
	public org.eclipse.bpel.model.Copy[] getPostActivityMappings() {
        /*
         * Sid ACE-194:  Currently we do not support 'Publish as REST service' for individual activities.
         * Something rather than deleting as we may want to introduce something similar later.
         */
        
        throw new RuntimeException("Unexpected inclusion of Java service tasks in source process.");
//		return outputMappings != null ?
//				outputMappings.toArray(new org.eclipse.bpel.model.Copy[outputMappings.size()]) :
//					new org.eclipse.bpel.model.Copy[0];
	}
//
//	private org.eclipse.bpel.model.Copy convertDataMappingToCopy(DataMapping dataMapping) throws JavaModelException {
//		/* Example:
//                <xpdl2:MessageIn Id="_g51HwPi3Ed6Z_pAOIJHuCA">
//                  <xpdl2:DataMappings>
//                    <xpdl2:DataMapping Direction="IN" Formal="parameter.postalCode">
//                      <xpdl2:Actual ScriptGrammar="JavaScript">myClaim.witness.address.postCode</xpdl2:Actual>
//                    </xpdl2:DataMapping>
//                  </xpdl2:DataMappings>
//                </xpdl2:MessageIn>
//                <xpdl2:MessageOut Id="_g51Hwfi3Ed6Z_pAOIJHuCA">
//                  <xpdl2:DataMappings>
//                    <xpdl2:DataMapping Direction="OUT" Formal="response.RV.postalCode">
//                      <xpdl2:Actual ScriptGrammar="JavaScript">myClaim.witness.address.postCode</xpdl2:Actual>
//                    </xpdl2:DataMapping>
//                    <xpdl2:DataMapping Direction="OUT" Formal="response.RV.townOrCity">
//                      <xpdl2:Actual ScriptGrammar="JavaScript">myClaim.witness.address.city</xpdl2:Actual>
//                    </xpdl2:DataMapping>
//                    <xpdl2:DataMapping Direction="OUT" Formal="response.RV.line1">
//                      <xpdl2:Actual ScriptGrammar="JavaScript">myClaim.witness.address.firstLine</xpdl2:Actual>
//                    </xpdl2:DataMapping>
//                    <xpdl2:DataMapping Direction="OUT" Formal="response.RV.line2">
//                      <xpdl2:Actual ScriptGrammar="JavaScript">myClaim.witness.address.secondLine</xpdl2:Actual>
//                    </xpdl2:DataMapping>
//                  </xpdl2:DataMappings>
//                </xpdl2:MessageOut>
//		 */
//		Expression actual = dataMapping.getActual();
//		if (actual == null) {
//			return null;
//		}		
//		
//        String target = DataMappingUtil.getTarget(dataMapping); // this is "to"
//        String script = DataMappingUtil.getScript(dataMapping); // this is "from"
//        String grammar = DataMappingUtil.getGrammar(dataMapping);
//    	boolean isScript = XPDLUtils.getScriptInformation(dataMapping) != null;
//    	    	
//		boolean mappingInbound = DirectionType.OUT_LITERAL.equals(dataMapping.getDirection());
//		org.eclipse.bpel.model.From from;
//		org.eclipse.bpel.model.To to;
//		Class<?> fromClass = null;
//		Class<?> toClass = null;
//		String toUmlType = null;
//		boolean toSimpleVar = false;
//		boolean fromSimpleVar = false;
//		boolean needTypeConversion = false;
//		boolean optional = false;
//		
//		if (mappingInbound) {
//			if (isScript) {
//				script = script.replaceAll(RESPONSE_RV, RETURN_VALUE);
//				from = BPELUtils.createFromScript(script, grammar);
//			} else {
//				fromClass = getPojoClass(MappingDirection.OUT, script);
//				if (script.equals(RESPONSE_RV)) {
//					//mapping from the return value directly
//					fromSimpleVar = true;
//					script = RETURN_VALUE;
//					from = BPELUtils.createFromVariable(script);
//				} else if (script.startsWith(RESPONSE_RV)) {
//					//mapping from a field of the return value
//					fromSimpleVar = false;
//					script = script.replaceFirst(RESPONSE_RV, RETURN_VALUE);				
//					from = BPELUtils.createFromScript(script, grammar);
//				} else {
//					//mapping from the OUT parameter
//					script = script.replaceAll(RESPONSE_RV, RETURN_VALUE);
//					script = removeParameterPrefix(script);
//					fromSimpleVar = DataTypeUtil.isJavaIdentifier(script);
//					script = INTERNAL_VAR_PREFIX + script;
//					if (fromSimpleVar) {
//						//We are mapping from the OUT parameter directly
//						from = BPELUtils.createFromVariable(script);
//					} else {
//						//We are mapping a sub-field of the OUT parameter (a CDS instance)
//						from = BPELUtils.createFromScript(script, grammar);
//					}
//				}
//			}
//			
//			toSimpleVar = DataTypeUtil.isJavaIdentifier(target);
//			if (toSimpleVar) {
//				//mapping to a single value
//				ProcessRelevantData prd = CDSUtils.getActivityInterfaceData(xpdlActivity, target);
//				if (prd.getDataType() instanceof BasicType) {
//					toClass = DataTypeUtil.getClassForBasicType((BasicType) prd.getDataType());
//				}
//				if (prd instanceof FormalParameter) {
//					optional = !((FormalParameter) prd).isRequired();
//				}
//				to = BPELUtils.createToVariable(target);
//			} else {	
//				//mapping to a sub-field of a CDS parameter/data field
//				Property property = CDSUtils.getProperty(xpdlActivity, target);
//				optional = property.getLower() == 0;
//				toUmlType = property.getType().getName();
//				BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
//				to = CDSUtils.createToCDSScript(xpdlActivity, target);
//			}
//		} else {
//			//mapping for invoking the POJO method
//			if (isScript) {
//				from = BPELUtils.createFromScript(script, grammar);
//			} else {
//				fromSimpleVar = DataTypeUtil.isJavaIdentifier(script);
//
//				if (fromSimpleVar) {
//					ProcessRelevantData prd = CDSUtils.getActivityInterfaceData(xpdlActivity, script);
//					if (prd.getDataType() instanceof BasicType) {
//						fromClass = DataTypeUtil.getClassForBasicType((BasicType) prd.getDataType());
//					}
//					if (prd instanceof FormalParameter) {
//						optional = !((FormalParameter) prd).isRequired();
//					}
//					from = BPELUtils.createFromVariable(script);
//				} else {
//					Property property = CDSUtils.getProperty(xpdlActivity, script);
//					optional = property.getLower() == 0;
//					fromClass = DataTypeUtil.getClassForUmlType(property.getType());
//					from = BPELUtils.createFromScript(script, grammar);
//				}
//			}
//			
//			String toExpr = removeParameterPrefix(target);
//			toSimpleVar = DataTypeUtil.isJavaIdentifier(toExpr);
//
//			if (toSimpleVar) {
//				String toVarName = fromSimpleVar ? toExpr : INTERNAL_VAR_PREFIX + toExpr;
//				to = BPELUtils.createToVariable(toVarName);
//				toClass = getPojoClass(MappingDirection.IN, target);
//				if (!fromSimpleVar && toClass != null) {
//					org.eclipse.bpel.model.Variable toVar = createVariable(toVarName, toClass);
//					addVariable(toVar);
//				}
//			} else {
//				int pos = toExpr.indexOf("."); //$NON-NLS-1$
//				String toVarName = toExpr.substring(0, pos);
//				JavaMethodParameter paramType = JavaServiceMappingUtil.resolveParameter(xpdlActivity, MappingDirection.IN, toVarName);
//				toClass = getPojoClass(MappingDirection.IN, target);
//				org.eclipse.bpel.model.Variable toVar = createPojoVariable(INTERNAL_VAR_PREFIX + toVarName, paramType.getIType().getFullyQualifiedName());
//				addVariable(toVar);
//
//				to = createToExpressionWithCDS(toExpr);
//				BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
//			}
//		}
//		
//		if (!fromSimpleVar || !toSimpleVar) {
//			org.eclipse.bpel.model.From newFrom = addTypeConversionScript(script, grammar, isScript, fromClass, toClass, toUmlType, optional);
//			if (newFrom != null) {
//				from = newFrom;
//				BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
//				needTypeConversion = true;
//			}
//		}
//		
//		if (!needTypeConversion && toSimpleVar && fromSimpleVar) {
//			//this is a mapping between simple variables that require no type conversion; 
//			//we can handle this inline and won't need the the copy
//			return null;
//		}
//
//		org.eclipse.bpel.model.Copy copy = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createCopy();
//		copy.setTo(to);
//		copy.setFrom(from);
//		if (optional) {
//			copy.setIgnoreMissingFromData(true);
//		}
//		
//		return copy;
//	}
//
//	private org.eclipse.bpel.model.From addTypeConversionScript(String script,
//			String grammar, boolean isScript, Class<?> fromClass, Class<?> toClass, String toUmlType, boolean optional) {
//		if (!isScript && fromClass != null && (toClass != null || toUmlType != null)) {
//			StringBuffer buf = new StringBuffer();
//			buf.append("var _returnVal;\n");
//			if (optional) {
//				buf.append("if (").append(script).append(" == null) {\n");
//				buf.append("_returnVal = null;\n");
//				buf.append("} else {\n");
//			}
//			if (Date.class.isAssignableFrom(fromClass)) {
//				if (toUmlType != null) {
//					if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(toUmlType)) {
//						buf.append("_returnVal = DateTimeUtil.createDate(").append(script).append(");\n");
//					} else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(toUmlType)) {
//						buf.append("_returnVal = DateTimeUtil.createTime(").append(script).append(");\n");
//					} else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME.equals(toUmlType)) {
//						buf.append("_returnVal = DateTimeUtil.createDatetime(").append(script).append(");\n");
//					} else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME.equals(toUmlType)) {
//						buf.append("_returnVal = DateTimeUtil.createDatetimetz(").append(script).append(");\n");
//					} else {
//						return null;
//					}
//				} else if (toClass != null && XMLGregorianCalendar.class.isAssignableFrom(toClass)) {
//					buf.append("_returnVal = DateTimeUtil.createDatetime(").append(script).append(");\n");
//				} else if (toClass != null && Calendar.class.isAssignableFrom(toClass)) {
//					buf.append("_returnVal = DateTimeUtil.createDatetime(").append(script).append(").toGregorianCalendar();\n");
//				} else {
//					return null;
//				}
//			} else if (Calendar.class.isAssignableFrom(fromClass)) {
//				if (toUmlType != null) {
//					if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(toUmlType)) {
//						buf.append("_returnVal = DateTimeUtil.createDate(").append(script).append(".getTime());\n");
//					} else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(toUmlType)) {
//						buf.append("_returnVal = DateTimeUtil.createTime(").append(script).append(".getTime());\n");
//					} else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME.equals(toUmlType)) {
//						buf.append("_returnVal = DateTimeUtil.createDatetime(").append(script).append(".getTime());\n");
//					} else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME.equals(toUmlType)) {
//						buf.append("_returnVal = DateTimeUtil.createDatetimetz(").append(script).append(".getTime());\n");
//					} else {
//						buf.append("_returnVal = ").append(script).append(".getTime();\n");
//					}
//				} else if (toClass != null && Date.class.isAssignableFrom(toClass)) {
//					buf.append("_returnVal = ").append(script).append(".getTime();\n");
//				} else if (toClass != null && XMLGregorianCalendar.class.isAssignableFrom(toClass)) {
//					buf.append("_returnVal = DateTimeUtil.createDatetime(").append(script).append(".getTime());\n");
//				} else {
//					return null;
//				}
//			} else if (XMLGregorianCalendar.class.isAssignableFrom(fromClass) && 
//					toClass != null && Date.class.isAssignableFrom(toClass)) {
//				buf.append("_returnVal = ").append(script).append(".toGregorianCalendar().getTime();\n");
//			} else if (XMLGregorianCalendar.class.isAssignableFrom(fromClass) && 
//					(toClass != null && Calendar.class.isAssignableFrom(toClass) || "Calendar".equals(toUmlType))) {
//				buf.append("_returnVal = ").append(script).append(".toGregorianCalendar();\n");
//			} else {
//				return null;
//			}
//			if (optional) {
//				buf.append("}\n");
//			}
//			buf.append("_returnVal;\n");
//			return BPELUtils.createFromScript(buf.toString(), grammar);
//		}
//		return null;
//	}
//
//	private Class<?> getPojoClass(MappingDirection mappingDirection, String expression) throws JavaModelException {
//		JavaMethodParameter javaParameter = JavaServiceMappingUtil.resolveParameter(this.xpdlActivity, mappingDirection, expression);
//		if (javaParameter == null) {
//			return null;
//		}
//		
//		switch (javaParameter.getType()) {
//		case BYTE:
//		case PBYTE:
//			return Byte.TYPE;
//		case INTEGER:
//		case PINTEGER:
//			return Integer.TYPE;
//		case SHORT:
//		case PSHORT:
//			return Short.TYPE;
//		case LONG:
//		case PLONG:
//			return Long.TYPE;
//		case FLOAT:
//		case PFLOAT:
//			return Float.TYPE;
//		case DOUBLE:
//		case PDOUBLE:
//			return Double.TYPE;
//		case BOOLEAN:
//		case PBOOLEAN:
//			return Boolean.TYPE;
//		case CHAR:
//		case PCHAR:
//			return Character.TYPE;
//		case BIGDECIMAL:
//			return BigDecimal.class;
//		case BIGINTEGER:
//			return BigInteger.class;
//		case DATE:
//			return Date.class;
//		case STRING:
//			return String.class;
//		case LIST:
//			return List.class;
//		case VOID:
//			return null;
//		case CLASS:
//			try {
//				String type = javaParameter.getQualifiedType();
//				Class<?> clz = Class.forName(type);
//				return clz;
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			break;
//		default:
//			break;
//		}
//
//		return null;
//	}
//
//	private org.eclipse.bpel.model.To createToExpressionWithCDS(String expression) throws JavaModelException {
//		StringBuffer script = new StringBuffer();
//
//		String[] segments = expression.split("\\.");
//		String currPath = null;
//		for (int i = 0; i < segments.length-1; i++) {
//			currPath = currPath == null ? segments[i] : currPath + "." + segments[i];
//			JavaMethodParameter javaParameter = JavaServiceMappingUtil.resolveParameter(this.xpdlActivity, MappingDirection.IN, currPath);
//			buildInstantiationScript(script, INTERNAL_VAR_PREFIX+currPath, javaParameter.getIType());
//		}
//		
//    	script.append(INTERNAL_VAR_PREFIX+expression).append(" = fromReturn;"); //$NON-NLS-1$
//		
//		org.eclipse.bpel.model.Expression expr = BPELFactory.eINSTANCE.createExpression();
//    	expr.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
//    	expr.setBody(script.toString());
//    	
//    	org.eclipse.bpel.model.To to = BPELFactory.eINSTANCE.createTo();
//		to.setExpression(expr);
//    	return to;
//	}
//
//	private void buildInstantiationScript(StringBuffer script, String currPath, IType type) {
//		script.append("if (").append(currPath).append(" == null) {\n");
//		script.append("importClass(Packages.").append(type.getFullyQualifiedName()).append(");\n");
//		script.append(currPath).append(" = new ").append(type.getElementName()).append("();\n");
//		script.append("}\n");
//	}
//
//	private org.eclipse.bpel.model.Variable createPojoVariable(String varName, String className) {
//		org.eclipse.bpel.model.Variable var = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
//		var.setName(varName);
//		BPELUtils.addExtensionAttribute(var, "pojoclass", className); //$NON-NLS-1
//		return var;
//	}
//
//	private org.eclipse.bpel.model.Variable createVariable(String varName, Class<?> clazz) {
//		XSDSimpleTypeDefinition xsdType = DataTypeUtil.getXsdForClass(clazz);
//		if (xsdType == null) {
//			return createPojoVariable(varName, clazz.getName());
//		}
//
//		org.eclipse.bpel.model.Variable var = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
//		var.setName(varName);
//		var.setType(xsdType);
//		return var;
//	}
//
//	private String removeParameterPrefix(String expression) {
//		return expression.substring(JavaConstants.PARAMETER_PREFIX.length() + JavaConstants.PARAMETER_SEPARATOR.length());
//	}
//
//	private void addVariable(org.eclipse.bpel.model.Variable var) {
//		variables.put(var.getName(), var);
//	}

}
