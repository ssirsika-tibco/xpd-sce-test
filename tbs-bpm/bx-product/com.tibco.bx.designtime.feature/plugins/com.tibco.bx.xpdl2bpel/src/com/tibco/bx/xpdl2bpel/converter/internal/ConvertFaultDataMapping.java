package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Part;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Variables;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.CDSUtils;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ConvertFaultDataMapping {
	
	private static final String FAULT_MARKER = "/fault:"; //$NON-NLS-1$
	
	private final ConverterContext context;
	private final Activity xpdlActivity; 
	private final WebServiceOperationInfo wso;
	private PartMappings toPartMappings;
	private boolean hasJavaScriptMapping;
	private Map<String, CorrelationMode> correlationDataFields;
	private Set<String> optionalParameters;
	private javax.wsdl.Message wsdlMessage;
	private Set<String> partsInScript;

	private final String faultName;
	
	private ConvertDataMapping delegateDataMapperMappingConverter = null;

    private Assign mappingAssignActivity;
	
	public ConvertFaultDataMapping(ConverterContext context, Activity xpdlActivity, WebServiceOperationInfo wso, String faultName) {
		this.context = context;
		this.xpdlActivity = xpdlActivity;
		this.wso = wso;
		this.faultName = faultName;
		
		correlationDataFields = XPDLUtils.getCorrelationDataFields(xpdlActivity);
		optionalParameters = XPDLUtils.findOptionalVariables(xpdlActivity);
	}

    public synchronized org.eclipse.bpel.model.Activity convertDataMappingsToAssign(final Message message) throws ConversionException {
        /**
         * Sid XPD-8010 For new DataMapper script grammar then delegate to
         * ConvertDataMapping which can handle DataMapper grammar
         * 
         * Also changed return to plain on Activity so that caller can be
         * agnostic of tye of activity that actually manages the mapping.
         * 
         * getMappingAssignActivity() allows existing callers to access the
         * specific assign activity.
         */
        wsdlMessage = wso.getFault(faultName).getMessage();
        
//        PROBLEM
//        THIS DOESN'T WORK AT THE MOMENT BECAUSE the ext e;lements are not under the xpdl:FaultMessage element
        if (Xpdl2ModelUtil.getOtherElement(message,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings()) != null
                || Xpdl2ModelUtil.getOtherElement(message,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_OutputMappings()) != null) {
            delegateDataMapperMappingConverter =
                    new ConvertDataMapping(context, xpdlActivity, wso);
            
            return delegateDataMapperMappingConverter.convertMappingsToActivity(message, faultName, wsdlMessage);
        }        
        
		List<DataMapping> dataMappings = message.getDataMappings();
		if (dataMappings == null || dataMappings.isEmpty()) {
			return null;
		}
		
		boolean mappingInbound = DirectionType.OUT_LITERAL.equals(dataMappings.get(0).getDirection());
		mappingAssignActivity = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
		mappingAssignActivity.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$
		
		
		if (!mappingInbound) {
			XPDLUtils.findReferencedData(context, xpdlActivity, mappingAssignActivity.getName(), DataReferenceContext.CONTEXT_MAPPING_IN);
		}

		if (wso == null) {
			for (DataMapping dataMapping : dataMappings) {
				convertSimpleDataMappingToCopy(mappingAssignActivity, dataMapping);
			}
			return mappingAssignActivity;
		}

		this.toPartMappings = new PartMappings();
		this.hasJavaScriptMapping = false;
		partsInScript = new HashSet<String>();

		Map<String, javax.wsdl.Part> parts = wsdlMessage.getParts();
		
		Map<String, org.eclipse.bpel.model.Copy> complexPartToTempVarMappings = new HashMap<String, org.eclipse.bpel.model.Copy>();
		for (javax.wsdl.Part part : parts.values()) {
			XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(wso.getWsdlDefinition(), part);
			if (typeDefinition instanceof XSDComplexTypeDefinition) {
				/*
    	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
    	         */
				org.eclipse.bpel.model.Copy copy = mappingInbound ?
						createComplexPartToTempVarMapping(faultName, part.getName(), N2PEConstants.MESSAGE_PREFIX + NameUtil.sanitizeMessagePartVariableName(part.getName()), typeDefinition, mappingInbound) :
							createTempVarToComplexPartMapping(faultName, part.getName(), typeDefinition);
				complexPartToTempVarMappings.put(part.getName(), copy);
			}
		}
		
		List<org.eclipse.bpel.model.Copy> complexMappings = new LinkedList<org.eclipse.bpel.model.Copy>();
		if (mappingInbound) {
			for (DataMapping dataMapping : dataMappings) {
				org.eclipse.bpel.model.Copy copy = convertDataMappingToCopy(dataMapping, faultName);
				if (copy.getFrom().getPart() != null) {
					//it's a simple mapping from a part
					mappingAssignActivity.getCopy().add(copy);
					String partName = copy.getFrom().getPart().getName();
					if (complexPartToTempVarMappings.containsKey(partName)) {
						complexPartToTempVarMappings.remove(partName);
					}
				} else {
					complexMappings.add(copy);
				}
			}
			
			//copy the part(s) to the temp var(s) first
			mappingAssignActivity.getCopy().addAll(complexPartToTempVarMappings.values());
			//now add the complex mappings
			mappingAssignActivity.getCopy().addAll(complexMappings);
		} else { // mapping outbound
			for (DataMapping dataMapping : dataMappings) {
				org.eclipse.bpel.model.Copy copy = convertDataMappingToCopy(dataMapping, faultName);
				if (copy.getTo().getPart() != null) {
					//it's a simple mapping to a part
					mappingAssignActivity.getCopy().add(copy);
					String partName = copy.getTo().getPart().getName();
					if (complexPartToTempVarMappings.containsKey(partName)) {
						complexPartToTempVarMappings.remove(partName);
					}
				} else {
					complexMappings.add(copy);
				}
			}
			
			//now add the complex mappings
			mappingAssignActivity.getCopy().addAll(complexMappings);
			//copy the temp var(s) to the part(s) at the last step
			mappingAssignActivity.getCopy().addAll(complexPartToTempVarMappings.values());
		}

		if (!toPartMappings.isEmpty()) {
    		for (javax.wsdl.Part part : parts.values()) {
    			if (part.getElementName() == null && part.getTypeName() == null) {
    				continue;
    			}
    			
    			List<String> paths = toPartMappings.getPaths(part.getName());
    			if (paths == null) continue;
    			
    			org.eclipse.bpel.model.Copy copy;
    			if (!hasJavaScriptMapping) {
        			String partLiteral = wso.createDummyPartLiteral((org.eclipse.wst.wsdl.Part)part, paths.toArray(new String[0]), !hasJavaScriptMapping);
    				copy = createXmlLiteralToToPartMapping(faultName, part.getName(), partLiteral);
    			} else {
        			String creationScript = CDSUtils.getCDSClassCreateStatementsForPart(context, wsdlMessage, part.getName());
    				copy = ConvertDataMapping.createFactoryScriptToTempVarMapping(N2PEConstants.MESSAGE_PREFIX + part.getName(), creationScript);
  			}
				
				mappingAssignActivity.getCopy().add(0, copy);
    		}
		}
        
		for (String partName : partsInScript) {
			Part part = wsdlMessage.getPart(partName);
			XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(wso.getWsdlDefinition(), part);
			String tempVarName = N2PEConstants.MESSAGE_PREFIX+partName;
			org.eclipse.bpel.model.Copy copy;
			if (typeDefinition instanceof XSDComplexTypeDefinition) {
				copy = createComplexPartToTempVarMapping(
						faultName, part.getName(), tempVarName, typeDefinition, mappingInbound);
			} else {
				org.eclipse.bpel.model.Variable tempVariable = 
		        	org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
				tempVariable.setName(tempVarName);
				tempVariable.setType(typeDefinition);
				context.addVariable(tempVariable);

				org.eclipse.bpel.model.To to = BPELUtils.createToVariable(tempVarName);
				org.eclipse.bpel.model.From from = createFromVariableWithPart(faultName, partName, ScriptGrammarFactory.XPATH);

				copy = BPELFactory.eINSTANCE.createCopy();
				copy.setTo(to);
				copy.setFrom(from);
			}
			mappingAssignActivity.getCopy().add(0, copy);
		}

		return mappingAssignActivity;
	}
    
    /**
     * SId XPD-8010 Allows the caller to be agnostic as to the actual type of
     * activity that is returned (some uses traditionally need to know exactly
     * the assign activity so that they can add additional copy elements to it.
     * 
     * @return the mappingAssignActivity
     */
    public Assign getMappingAssignActivity() {
        if (delegateDataMapperMappingConverter != null) {
            return delegateDataMapperMappingConverter.getMappingAssignActivity();
        }
        return mappingAssignActivity;
    }

	private org.eclipse.bpel.model.Copy createXmlLiteralToToPartMapping(
			String varName, String partName, String partLiteral) {
		org.eclipse.bpel.model.From from = BPELUtils.createFromLiteral(partLiteral);
		org.eclipse.bpel.model.To to = BPELUtils.createToVariableWithPart(varName, partName, null);
		
		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		copy.setTo(to);
		copy.setFrom(from);
		if (optionalParameters.contains(varName)) {
			copy.setIgnoreMissingFromData(true);			
		}
		return copy;
	}

	private org.eclipse.bpel.model.Copy createComplexPartToTempVarMapping(
			String varName, String partName, String tempVarName, XSDTypeDefinition typeDefinition, boolean mappingInbound) {
		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		BPELUtils.addExtensionAttribute(copy, "transform", "XmlToObject"); //$NON-NLS-1$ //$NON-NLS-2$
		BPELUtils.addExtensionAttribute(copy, "objectName", typeDefinition.getName()); //$NON-NLS-1$
		BPELUtils.addExtensionAttribute(copy, "objectNamespace", typeDefinition.getTargetNamespace()); //$NON-NLS-1$
		BPELUtils.addExtensionAttribute(copy, "tempVariable", tempVarName); //$NON-NLS-1$

    	String expr = String.format("$%s.%s", new Object[]{varName, partName}); //$NON-NLS-1$
		org.eclipse.bpel.model.From from = BPELUtils.createFromScript(expr, ScriptGrammarFactory.XPATH);
		org.eclipse.bpel.model.To to = BPELUtils.createToVariable(tempVarName);

		copy.setFrom(from);
		copy.setTo(to);
		return copy;
	}
	
	private org.eclipse.bpel.model.Copy createTempVarToComplexPartMapping(
			String varName, String partName, XSDTypeDefinition typeDefinition) {
		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		BPELUtils.addExtensionAttribute(copy, "transform", "ObjectToXml"); //$NON-NLS-1$ //$NON-NLS-2$ 
		BPELUtils.addExtensionAttribute(copy, "objectName", typeDefinition.getName()); //$NON-NLS-1$
		BPELUtils.addExtensionAttribute(copy, "objectNamespace", typeDefinition.getTargetNamespace()); //$NON-NLS-1$

		/*
         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
         */
		org.eclipse.bpel.model.From from = BPELUtils.createFromVariable(N2PEConstants.MESSAGE_PREFIX + NameUtil.sanitizeMessagePartVariableName(partName));
		org.eclipse.bpel.model.To to = BPELUtils.createToVariableWithPart(varName, partName, null);
		copy.setFrom(from);
		copy.setTo(to);

		return copy;
	}
	
	private void convertSimpleDataMappingToCopy(final org.eclipse.bpel.model.Assign assign, final DataMapping dataMapping) {
		Expression actual = dataMapping.getActual();
		if (actual == null) {
			return;
		}		
        String target = DataMappingUtil.getTarget(dataMapping);
        String script = DataMappingUtil.getScript(dataMapping);
		org.eclipse.bpel.model.To to = BPELUtils.createToVariable(target);
		org.eclipse.bpel.model.From from = BPELUtils.createFromVariable(script);

		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		copy.setTo(to);
		copy.setFrom(from);
		if (optionalParameters.contains(from.getVariable().getName())) {
			copy.setIgnoreMissingFromData(true);			
		}
		if (optionalParameters.contains(to.getVariable().getName())) {
			copy.setIgnoreMissingFromData(true);			
		}

		assign.getCopy().add(copy);
	}

	private org.eclipse.bpel.model.Copy convertDataMappingToCopy(
			final DataMapping dataMapping, String varName) throws ConversionException {
		/* Example:
	      <xpdl2:DataMappings>
	        <xpdl2:DataMapping Direction="OUT" Formal="wso:GetBalance/part:AccountNumber">
	          <xpdl2:Actual ScriptGrammar="XPath">AccountNumber</xpdl2:Actual>
	        </xpdl2:DataMapping>
	      </xpdl2:DataMappings>
		 */
		Expression actual = dataMapping.getActual();
		if (actual == null) {
			return null;
		}		
		
        String target = DataMappingUtil.getTarget(dataMapping);
        String script = DataMappingUtil.getScript(dataMapping);
        String grammar = DataMappingUtil.getGrammar(dataMapping);
    	CorrelationMode mode = correlationDataFields.get(target);
    	if (mode != null && mode == CorrelationMode.CORRELATE) {
    		// do nothing - the data mapping is meant to be used as a correlation expression
    		return null;
    	}
        ScriptMappingCompositorFactory factory = ScriptMappingCompositorFactory.getCompositorFactory(grammar, dataMapping.getDirection());
        if (factory != null) {
            ScriptMappingCompositor compositor = factory.getCompositor(xpdlActivity, target, script);
            if (compositor instanceof SingleMappingCompositor) {
                SingleMappingCompositor single = (SingleMappingCompositor) compositor;
                String expression = single.getScript();
                if (expression != null) {
					org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();

					boolean mappingInbound = DirectionType.OUT_LITERAL.equals(dataMapping.getDirection());
					boolean isXPath = ScriptGrammarFactory.XPATH.equals(grammar);
                	ScriptInformation scriptInformation = XPDLUtils.getScriptInformation(dataMapping);
                	boolean isScript = scriptInformation != null;
                	org.eclipse.bpel.model.From from;
					org.eclipse.bpel.model.To to;
					if (mappingInbound) {
						from = isScript ? createFromScript(expression, grammar) : 
							createFromVariableWithPart(varName, expression, grammar);
						boolean mappingToVariable = isXPath || target.indexOf(".") < 0; //$NON-NLS-1$
						if (mappingToVariable) {
							to = BPELUtils.createToVariable(target);
						} else {
							to = createToExpressionWithCDS(target, mappingInbound);
							BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					} else {
						from = isScript || !isXPath ? createFromScript(expression, grammar) : 
							BPELUtils.createFromVariable(expression);
						boolean mappingToPart = isXPath || target.indexOf(".") < 0; //$NON-NLS-1$
						if (mappingToPart) {
							to = createToVariableWithPart(varName, target, grammar);
						} else {
							to = createToExpressionWithCDS(target, mappingInbound);
							BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}
						
					Variables variables = context.getLocalVariables(xpdlActivity);

					copy.setTo(to);
					copy.setFrom(from);
					if (!isXPath && from.getPart() != null) {
						javax.wsdl.Part part = wsdlMessage.getPart(from.getPart().getName());
						XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(
								wso.getWsdlDefinition(), part);
						if (typeDefinition != null && typeDefinition.getComplexType() != null) {
							BPELUtils.addExtensionAttribute(copy, "transform", "XmlToObject"); //$NON-NLS-1$ //$NON-NLS-2$
							BPELUtils.addExtensionAttribute(copy, "objectName", typeDefinition.getName()); //$NON-NLS-1$
							BPELUtils.addExtensionAttribute(copy, "objectNamespace", typeDefinition.getTargetNamespace()); //$NON-NLS-1$
						}
					}
					if (from.getVariable() != null && optionalParameters.contains(from.getVariable().getName())) {
						copy.setIgnoreMissingFromData(true);			
					} else if (from.getExpression() != null && optionalParameters.contains(from.getExpression().getBody())) {
						copy.setIgnoreMissingFromData(true);			
					}

					if (!isXPath && to.getPart() != null) {
						javax.wsdl.Part part = wsdlMessage.getPart(to.getPart().getName());
						XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(
								wso.getWsdlDefinition(), part);
						if (typeDefinition != null && typeDefinition.getComplexType() != null) {
							BPELUtils.addExtensionAttribute(copy, "transform", "ObjectToXml"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}
					
					if (to.getVariable() != null && optionalParameters.contains(to.getVariable().getName())) {
						copy.setIgnoreMissingFromData(true);
					}
					
					return copy;
                }
            }
		} else {
			//assume the entire expression is the variable name
			org.eclipse.bpel.model.To to = BPELUtils.createToVariable(varName);
			org.eclipse.bpel.model.From from = BPELUtils.createFromVariable(actual.getText());

			org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
			copy.setTo(to);
			copy.setFrom(from);
			if (optionalParameters.contains(from.getVariable().getName()) || 
					optionalParameters.contains(from.getVariable().getName())) {
				copy.setIgnoreMissingFromData(true);			
			}
			return copy;
		}
        
        return null;
	}

	/*
	private org.eclipse.bpel.model.To createToExpressionWithCDS(String expression, boolean mappingInbound) throws ConversionException {
    	StringBuffer script = new StringBuffer();
    	
    	int pos = expression.indexOf("."); //$NON-NLS-1$
    	if (pos > 0) {
    		if (mappingInbound) {
        		String varName = expression.substring(0, pos);

    			ProcessRelevantData processRelevantData = null;
                for (ProcessRelevantData prd : allPrds) {
                    if (prd.getName().equals(varName)) {
                        processRelevantData = prd;
                        break;
                    }
                }
                if (processRelevantData == null) {
                	throw new ConversionException("Cannot find the corresponding ProcessRelevantData for " + varName);
                }
                
            	String cdsFactoryMethodName = CDSUtils.getCDSFactoryMethodName(processRelevantData);
            	String cdsPackageName = CDSUtils.getCDSPackageName(processRelevantData);
            	String cdsFactoryClassName = cdsPackageName.replaceAll("\\.", "_").concat("_Factory"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    			script.append("if (").append(varName).append(" == null) ");
    			script.append(varName).append(" = ").append(cdsFactoryClassName).append(".").append(cdsFactoryMethodName).append("();\n");
    		} else {
        		String partName = expression.substring(0, pos);
        		String query = expression.substring(pos+1).replace('.', '/'); //$NON-NLS-1$  //$NON-NLS-2
    			toPartMappings.addMapping(partName, query);

    			expression = N2PEConstants.MESSAGE_PREFIX + expression;
    		}
    	}
		
    	script.append(expression);
		script.append(" = fromReturn;");

    	org.eclipse.bpel.model.Expression expr = BPELFactory.eINSTANCE.createExpression();
    	expr.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
    	expr.setBody(script.toString());
    	
    	org.eclipse.bpel.model.To to = BPELFactory.eINSTANCE.createTo();
		to.setExpression(expr);
    	return to;
	}
	*/
	
	private org.eclipse.bpel.model.To createToExpressionWithCDS(String expression, boolean mappingInbound) throws ConversionException {
		String enumTypeName = null; 
		boolean isArray = false;
		boolean isSingleInstOverridingMultiInstProperty = false;

		StringBuffer script = new StringBuffer();
    	
    	int pos = expression.indexOf("."); //$NON-NLS-1$
    	if (pos > 0) {
			Property property = mappingInbound ? CDSUtils.getProperty(xpdlActivity, expression) :
				CDSUtils.getProperty(context, wsdlMessage, expression);
			boolean mapToEnumField = property != null && property.getType() instanceof Enumeration; 
	    	if (mapToEnumField) {
	    		enumTypeName = property.getType().getQualifiedName();
	    		enumTypeName = enumTypeName.replace(".", "_").replace("::", "_");
	    	}
	    	isArray = property != null && (property.getUpper() > 1 || property.getUpper() == -1);
	    	isSingleInstOverridingMultiInstProperty = property != null 
	    		&& XSDUtil.isMultiInstancePropertyBasedOnXSDRestriction(property) 
	    		&& XSDUtil.isClassXsdComplexTypeRestriction(property.getClass_());

	    	if (mappingInbound) {
        		String createStmts = CDSUtils.getCDSClassCreateStatementsForExpression(xpdlActivity, expression);
    			if (createStmts != null) {
    				script.append(createStmts);
    			}
    		} else {
        		String createStmts = CDSUtils.getCDSClassCreateStatementsForExpression(context, wsdlMessage, expression);
    			if (createStmts != null) {
    				script.append(createStmts);
    			}
        		String partName = expression.substring(0, pos);
        		String query = expression.substring(pos+1).replace('.', '/'); //$NON-NLS-1$  //$NON-NLS-2
    			toPartMappings.addMapping(partName, query);
    			
    	        /*
    	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
    	         */
    	        String remainingPathJsVarAlias = expression.substring(expression.indexOf('.'));
    	        expression = NameUtil.sanitizeMessagePartVariableName(partName) + remainingPathJsVarAlias;
    			expression = N2PEConstants.MESSAGE_PREFIX + expression;
    		}			
    	}
		
    	if (isArray) {
        	script.append(expression).append(".clear();\n"); //$NON-NLS-1$
    		script.append(expression).append(".addAll(fromReturn);"); //$NON-NLS-1$
    	} else if (isSingleInstOverridingMultiInstProperty) {
        	script.append(expression).append(".clear();\n"); //$NON-NLS-1$
    		script.append(expression).append(".add(fromReturn);"); //$NON-NLS-1$
    	} else if (enumTypeName != null) {
    		/*
    		script.append("if (fromReturn != null && ").append(enumTypeName).append(".get(fromReturn) == null) {\n");
    		script.append("throw new java.lang.IllegalArgumentException(\"Invalid " + enumTypeName + " value \" + fromReturn);\n");
    		script.append("}\n");
    		*/
        	script.append(expression).append(" = ").append(enumTypeName).append(".get(fromReturn);"); //$NON-NLS-1$ //$NON-NLS-2$
    	} else {
        	script.append(expression).append(" = fromReturn;"); //$NON-NLS-1$
    	}
		
		org.eclipse.bpel.model.Expression expr = BPELFactory.eINSTANCE.createExpression();
    	expr.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
    	expr.setBody(script.toString());
    	
    	org.eclipse.bpel.model.To to = BPELFactory.eINSTANCE.createTo();
		to.setExpression(expr);
    	return to;
	}


	private org.eclipse.bpel.model.To createToVariableWithPart(String varName, String script, String grammar) {
		String partName = null;
		String query = null;
		String expression = script;
		boolean isXPath = ScriptGrammarFactory.XPATH.equals(grammar);
		if (isXPath) {
			expression = WsdlUtil.wsdlPathToXPath(script);
		}
    	int pos = expression.indexOf(isXPath ? "/" : "."); //$NON-NLS-1$  //$NON-NLS-2$
    	if (pos > 0) {
    		query = expression.substring(pos+1).replace('.', '/'); //$NON-NLS-1$  //$NON-NLS-2
        	partName = expression.substring(0, pos);
    	} else {
    		partName = expression;
    	}
    	
 		if (query != null) {
			toPartMappings.addMapping(partName, query);
		}

		int faultPos = script.indexOf(FAULT_MARKER);
		if (faultPos > 0) {
			int faultStartPos = faultPos + FAULT_MARKER.length();
			int faultEndPos = script.indexOf("/", faultStartPos); //$NON-NLS-1$
			varName = script.substring(faultStartPos, faultEndPos);
    	}

    	if (query != null) {
			javax.wsdl.Part part = wsdlMessage.getPart(partName);
			query = searchAndReplacePrefixes(wso.getWsdlDefinition(), part, query);
    	}

		org.eclipse.bpel.model.To to = BPELUtils.createToVariableWithPart(varName, partName, query);
    	return to;
	}

	private org.eclipse.bpel.model.From createFromVariableWithPart(String varName, String script, String grammar) {
		javax.wsdl.Part part = null;
		String partName = null;
		String query = null;
		String expression = script;

		boolean isXPath = ScriptGrammarFactory.XPATH.equals(grammar);
		if (isXPath) {
			expression = WsdlUtil.wsdlPathToXPath(script);
		}
    	int pos = expression.indexOf(isXPath ? "/" : "."); //$NON-NLS-1$  //$NON-NLS-2$
    	if (pos > 0) {
    		query = expression.substring(pos+1).replace('.', '/');
        	partName = expression.substring(0, pos);
    	} else {
    		partName = expression;
    	}
    	
		part = wsdlMessage.getPart(partName);
		
		/*
         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
         */
		partName = NameUtil.sanitizeMessagePartVariableName(partName);

		org.eclipse.bpel.model.From from;
		
		if (part != null) {
			if (query == null) { //simple part
				from = BPELUtils.createFromVariableWithPart(varName, partName, null);
			} else { //complex part
		    	if (isXPath) {
					query = searchAndReplacePrefixes(wso.getWsdlDefinition(), part, query);
					from = BPELUtils.createFromVariableWithPart(varName, partName, query);
		    	} else {
		    		/*
	    	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
	    	         */
	    	        String remainingPathJsVarAlias = expression.substring(pos);
	    	        expression = partName + remainingPathJsVarAlias;
					from = BPELUtils.createFromScript(N2PEConstants.MESSAGE_PREFIX + expression + ";", grammar); //$NON-NLS-1$
		    	}
			}
		} else { //script
			from = BPELUtils.createFromScript(expression, grammar);
		}

		if (!isXPath) {
			hasJavaScriptMapping = true;
		}

		return from;
	}

	private From createFromScript(String expression, String grammar) {
		org.eclipse.bpel.model.From from = BPELUtils.createFromScript(expression, grammar);
		boolean isXPath = ScriptGrammarFactory.XPATH.equals(grammar);
		if (!isXPath) {
			hasJavaScriptMapping = true;
			
			Map<String, javax.wsdl.Part> parts = wsdlMessage.getParts();
			for (javax.wsdl.Part part : parts.values()) {
				/*
    	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
    	         */
				String var = N2PEConstants.MESSAGE_PREFIX + NameUtil.sanitizeMessagePartVariableName(part.getName());
				if (expression.indexOf(var) >= 0) {
					partsInScript.add(part.getName());
				}
			}
		}
		return from;
	}

	private String searchAndReplacePrefixes(Definition definition, Part part, String mappingExpression) {
		Map<String, String> namespaceToPrefixMap = context.getNamespaceToPrefixMap();
		Collection<XSDSchema> allSchemas = WSDLUtils.getSchemaImports(definition, false);

		StringBuffer result = new StringBuffer();
		String[] sections = mappingExpression.split("/"); //$NON-NLS-1$
		for (int i = 0; i < sections.length; i++) {
			String section = sections[i];
			String prefix;
			String elementName;
			int pos = section.indexOf(':'); //$NON-NLS-1$
			if (pos > 0) {
				prefix = section.substring(0, pos);
				elementName = section.substring(pos+1);
			} else {
				prefix = null;
				elementName = section;
			}

			if (prefix != null) {
				String namespace = getNamespace(definition, allSchemas, prefix);
				String newPrefix = namespaceToPrefixMap.get(namespace);
				if (newPrefix == null) newPrefix = prefix; 
				result.append(newPrefix).append(':').append(elementName);
			} else {
				result.append(elementName);
			}

			if (i < sections.length-1) {
				result.append('/'); //$NON-NLS-1$				
			}
		}
		
		return result.toString();
	}

	private static String getNamespace(Definition definition, Collection<XSDSchema> allSchemas, String prefix) {
		String namespace = definition.getNamespace(prefix);
		if (namespace != null) {
			return namespace;
		}
		
		for (XSDSchema schema : allSchemas) {
			Map<String, String> namespaceMap = schema.getQNamePrefixToNamespaceMap();
			namespace = namespaceMap.get(prefix);
			if (namespace != null) {
				return namespace;
			}
		}
		
		return null;
	}
	
	private static class PartMappings {
		Map<String, List<String>> partToPaths = new HashMap<String, List<String>>();
		
		public void addMapping(String part, String path) {
			List<String> paths = partToPaths.get(part);
			if (paths == null) {
				paths = new LinkedList<String>();
				partToPaths.put(part, paths);
			}
			paths.add(path);
		}
		
		public boolean isEmpty() {
			return partToPaths.isEmpty();
		}
		
		public List<String> getPaths(String part) {
			return partToPaths.get(part);
		}
	}
	
}
