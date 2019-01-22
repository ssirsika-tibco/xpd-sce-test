package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Part;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.To;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.Script;
import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.CDSUtils;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ConvertDataMapping {
	
	private static final String FAULT_MARKER = "/fault:"; //$NON-NLS-1$
	
	private final ConverterContext context;
	private final Activity xpdlActivity; 
	private final WebServiceOperationInfo wso;
	private PartMappings toPartMappings;
	private boolean hasJavaScriptMapping;
	private Map<String, CorrelationMode> correlationDataFields;
	private Set<String> optionalVariables;
	private javax.wsdl.Message wsdlMessage;
	private Set<String> partsInScript;

    /**
     * XPD-8010: This is a reference to the assign activity (which may or may not be _the_
     * mapping activity or may be part of a _sequence_ activity that is
     * _the_mapping activity
     */
	private org.eclipse.bpel.model.Assign assign;

	
	public ConvertDataMapping(ConverterContext context, Activity xpdlActivity, WebServiceOperationInfo wso) {
		this.context = context;
		this.xpdlActivity = xpdlActivity;
		this.wso = wso;
		correlationDataFields = XPDLUtils.getCorrelationDataFields(xpdlActivity);
		optionalVariables = XPDLUtils.findOptionalVariables(xpdlActivity);
	}

    /**
     * XPD-8010: Create the BPEL activity(s) required for handling datamappings for the
     * given input/output message - for either DataMapper grammar (new) of
     * JavaScript/XPath grammar (old)
     * 
     * @param message
     * @param useWSDLInput
     * 
     * @return Returns the root activity that performs the mapping (either an
     *         {assign} for JavaScript or {scope} for Datamapper
     * 
     * @throws ConversionException
     */
    public synchronized org.eclipse.bpel.model.Activity convertDataMappingsToActivity(
            final Message message, boolean useWSDLInput)
            throws ConversionException {
        String varName =
                useWSDLInput ? wso.getInputVariableName() : wso
                        .getOutputVariableName();
        return this.convertDataMappingsToActivity(message,
                useWSDLInput,
                varName);
    }

    /**
     * XPD-8010: Create the BPEL activity(s) required for handling datamappings for the
     * given input/output message - for either DataMapper grammar (new) of
     * JavaScript/XPath grammar (old)
     * 
     * Use the given varName as the variable used in request/response activity.
     * 
     * @param message
     * @param useWSDLInput
     * @param varName
     * 
     * @return Returns the root activity that performs the mapping (either an
     *         {assign} for JavaScript or {scope} for Datamapper
     * 
     * @throws ConversionException
     */
    public synchronized org.eclipse.bpel.model.Activity convertDataMappingsToActivity(
            final Message message, boolean useWSDLInput, String varName)
            throws ConversionException {

        javax.wsdl.Message wsdlMessage=null;

        if (useWSDLInput) {
            if (null != wso.getInput()) {
                wsdlMessage = wso.getInput().getMessage();
            }

        } else {
            if (null != wso.getOutput()) {
                wsdlMessage = wso.getOutput().getMessage();
            }
        }
        
        return convertMappingsToActivity(message, varName, wsdlMessage);
    }

    /**
     * XPD-8010: Create the BPEL activity(s) required for handling datamappings
     * for the given input/output message - for either DataMapper grammar (new)
     * of JavaScript/XPath grammar (old)
     * 
     * Use the given varName as the variable used in request/response activity.
     * 
     * @param message
     * @param varName
     * @param wsdlMessage
     * @return
     * @throws ConversionException
     */
    synchronized org.eclipse.bpel.model.Activity convertMappingsToActivity(
            final Message message, String varName,
            javax.wsdl.Message wsdlMessage) throws ConversionException {

        this.wsdlMessage = wsdlMessage;

        /*
         * Check for Datamapper InputToService mappings.
         */
        Object dataMapperInputMappings =
                Xpdl2ModelUtil.getOtherElement(message,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InputMappings());

        if (dataMapperInputMappings instanceof ScriptDataMapper) {
            return convertProcessToServiceMappingsForDataMapper(message,
                    (ScriptDataMapper) dataMapperInputMappings,
                    varName,
                    wsdlMessage);
        }

        /*
         * Check for Datamapper InputToService mappings.
         */
        Object dataMapperOutputMappings =
                Xpdl2ModelUtil.getOtherElement(message,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_OutputMappings());

        if (dataMapperOutputMappings instanceof ScriptDataMapper) {
            return convertServiceToProcessMappingsForDataMapper(message,
                    (ScriptDataMapper) dataMapperOutputMappings,
                    varName,
                    wsdlMessage);
        }

        /*
         * It`s not DataMapper so default to JavaScript / Xpath handling.
         */
        return this.convertDatamappingsForJSAndXPath(message,
                varName,
                wsdlMessage);
    }

    /**
     * Convert data mappings for JavaScript / XPath grammar
     * 
     * XPD-8010: This is basically the original convertDataMappingsToAssigen
     * method. But rather than create more of a tangle by adding DataMapper
     * grammar directly to it, we have kept it as original and have separate
     * methods for handling DataMapper grammar.
     * 
     * @param message
     * @param useWSDLInput
     * @param varName
     * @param wsdlMessage
     * 
     * @return Returns the root activity that performs the mapping (an
     *         {assign} for JavaScript/Xpath()
     *         
     * @throws ConversionException
     */
    private synchronized org.eclipse.bpel.model.Assign convertDatamappingsForJSAndXPath(
            final Message message, String varName,
            javax.wsdl.Message wsdlMessage) throws ConversionException {
		List<DataMapping> dataMappings = message.getDataMappings();
		if (dataMappings == null || dataMappings.isEmpty()) {
			return null;
		}
		
		boolean mappingInbound = DirectionType.OUT_LITERAL.equals(dataMappings.get(0).getDirection());
		
		List<DataMappingInfo> dataMappingInfos = new ArrayList<DataMappingInfo>(dataMappings.size());
		for (DataMapping dataMapping : dataMappings) {
			dataMappingInfos.add(new DataMappingInfo(dataMapping));
		}
		

		assign = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createAssign();
		assign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$
		
		if (!mappingInbound) {
			XPDLUtils.findReferencedData(context, xpdlActivity, assign.getName(), DataReferenceContext.CONTEXT_MAPPING_IN);
		}

		if (wso == null) {
			for (DataMappingInfo dataMappingInfo : dataMappingInfos) {
				org.eclipse.bpel.model.Copy copy = convertSimpleDataMappingToCopy(dataMappingInfo);
				assign.getCopy().add(copy);
			}
			return assign;
		}

		this.toPartMappings = new PartMappings();
		this.hasJavaScriptMapping = false;

		Map<String, javax.wsdl.Part> parts = wsdlMessage.getParts();
		partsInScript = new HashSet<String>();
		
		Map<String, org.eclipse.bpel.model.Copy> complexPartToTempVarMappings = new HashMap<String, org.eclipse.bpel.model.Copy>();
		for (javax.wsdl.Part part : parts.values()) {
			XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(wso.getWsdlDefinition(), part);
			if (typeDefinition instanceof XSDComplexTypeDefinition) {
				/*
    	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
    	         */
				org.eclipse.bpel.model.Copy copy = mappingInbound ?
						createComplexPartToTempVarMapping(varName, part, N2PEConstants.MESSAGE_PREFIX + NameUtil.sanitizeMessagePartVariableName(part.getName()), typeDefinition, mappingInbound, true) :
							createTempVarToComplexPartMapping(varName, part, typeDefinition);
				complexPartToTempVarMappings.put(part.getName(), copy);
			}
		}
		
		List<org.eclipse.bpel.model.Copy> complexMappings = new LinkedList<org.eclipse.bpel.model.Copy>();
		if (mappingInbound) {
			boolean allSimpleMapping = true;
			for (DataMappingInfo dataMappingInfo : dataMappingInfos) {
				org.eclipse.bpel.model.Copy copy = convertDataMappingToCopy(dataMappingInfo, varName);
				if (copy.getFrom().getPart() != null && copy.getTo().getVariable() != null) {
					//it's a simple mapping from a part
					assign.getCopy().add(copy);
				} else {
					complexMappings.add(copy);
					allSimpleMapping = false;
				}
			}

			if (allSimpleMapping) {
				complexPartToTempVarMappings.clear();
			}
			
			//copy the part(s) to the temp var(s) first
			assign.getCopy().addAll(complexPartToTempVarMappings.values());
			//now add the complex mappings
			assign.getCopy().addAll(complexMappings);
		} else { // mapping outbound
			boolean allSimpleMapping = true;
			for (DataMappingInfo dataMappingInfo : dataMappingInfos) {
				org.eclipse.bpel.model.Copy copy = convertDataMappingToCopy(dataMappingInfo, varName);
				if (copy.getTo().getPart() != null) {
					//it's a simple mapping to a part
					assign.getCopy().add(copy);
				} else {
					complexMappings.add(copy);
					allSimpleMapping = false;
				}
			}
			
			if (allSimpleMapping) {
				complexPartToTempVarMappings.clear();
			}

			//now add the complex mappings
			assign.getCopy().addAll(complexMappings);
			//copy the temp var(s) to the part(s) at the last step
			String[] partNames = complexPartToTempVarMappings.keySet().toArray(new String[0]);
			for (String partName : partNames) {
				if (!toPartMappings.hasPart(partName)) {
					//the temp var for the part is not needed; so remove the mapping from XML literal to the temp var
					complexPartToTempVarMappings.remove(partName);
				}
			}
			assign.getCopy().addAll(complexPartToTempVarMappings.values());
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
    				copy = createXmlLiteralToToPartMapping(varName, part.getName(), partLiteral);
    			} else {
        			String creationScript = CDSUtils.getCDSClassCreateStatementsForPart(context, wsdlMessage, part.getName());
        			/*
        	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
        	         */
    				copy = createFactoryScriptToTempVarMapping(N2PEConstants.MESSAGE_PREFIX + NameUtil.sanitizeMessagePartVariableName(part.getName()), creationScript);
    			}
				
				assign.getCopy().add(0, copy);
    		}
		}
		
		for (String partName : partsInScript) {
			Part part = wsdlMessage.getPart(partName);
			XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(wso.getWsdlDefinition(), part);
			/*
	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
	         */
			String tempVarName = N2PEConstants.MESSAGE_PREFIX+NameUtil.sanitizeMessagePartVariableName(partName);
			org.eclipse.bpel.model.Copy copy = null;
			if (typeDefinition instanceof XSDComplexTypeDefinition) {
				/*
				//don't do this as it's already done earlier
				copy = createComplexPartToTempVarMapping(
						varName, part, tempVarName, typeDefinition, mappingInbound);
				 */
			} else {
				org.eclipse.bpel.model.Variable tempVariable = 
		        	org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
				tempVariable.setName(tempVarName);
				tempVariable.setType(typeDefinition);
				context.addVariable(tempVariable);

				org.eclipse.bpel.model.To to = BPELUtils.createToVariable(tempVarName);
				org.eclipse.bpel.model.From from = createFromVariableWithPart(varName, partName, ScriptGrammarFactory.XPATH, null);

				copy = BPELFactory.eINSTANCE.createCopy();
				copy.setTo(to);
				copy.setFrom(from);
			}
			
			if (copy != null) {
				assign.getCopy().add(0, copy);
			}
		}
        
		return assign;
	}

	private static void setXmlToObjectTransformAttributes(org.eclipse.bpel.model.Copy copy, javax.wsdl.Part part, XSDTypeDefinition typeDefinition) {
		BPELUtils.addExtensionAttribute(copy, "transform", "XmlToObject"); //$NON-NLS-1$ //$NON-NLS-2$
		String objectName;
		String objectNamespace;
		if (N2PEConstants.XSD_NAMESPACE_URI.equals(typeDefinition.getTargetNamespace()) && "anyType".equals(typeDefinition.getName())) { //$NON-NLS-1$
			objectName = part.getElementName().getLocalPart();
			objectNamespace = part.getElementName().getNamespaceURI();
		} else {
			objectName = typeDefinition.getName();
			objectNamespace = typeDefinition.getTargetNamespace();
		}
		BPELUtils.addExtensionAttribute(copy, "objectName", objectName); //$NON-NLS-1$
		BPELUtils.addExtensionAttribute(copy, "objectNamespace", objectNamespace); //$NON-NLS-1$
	}
	
	static org.eclipse.bpel.model.Copy createXmlLiteralToToPartMapping(
			String varName, String partName, String partLiteral) {
		org.eclipse.bpel.model.From from = BPELUtils.createFromLiteral(partLiteral);
		org.eclipse.bpel.model.To to = BPELUtils.createToVariableWithPart(varName, partName, null);
		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		copy.setTo(to);
		copy.setFrom(from);
		return copy;
	}

	/**
	 * Create {copy} statement for copying from (for instance) $v_XXXXX.OutputPart to (for instance) MESSSAGE_OutputPart.
	 * 
	 * Sid XPD-8010: added addTempVariableToCopy to control whether tempVarName variable is added directly to {copy} element.
	 *                 (as is the case for javaScript mappings. Whereas for DataMapper the temp variable goes on parent {scope}  
	 *  
	 * @param varName
	 * @param part
	 * @param tempVarName
	 * @param typeDefinition
	 * @param mappingInbound
	 * @param addTempVariableToCopy
	 * 
	 * @return {copy} element.
	 */
	private org.eclipse.bpel.model.Copy createComplexPartToTempVarMapping(
			String varName, javax.wsdl.Part part, String tempVarName, XSDTypeDefinition typeDefinition, boolean mappingInbound, boolean addTempVariableToCopy) {
		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		setXmlToObjectTransformAttributes(copy, part, typeDefinition);
		
		/* Sid XPD-8010: added addTempVariableToCopy to control whether tempVarName variable is added directly to {copy} element.
	     *                 (as is the case for javaScript mappings. Whereas for DataMapper the temp variable goes on parent {scope}  */
        if (addTempVariableToCopy) {
            BPELUtils.addExtensionAttribute(copy, "tempVariable", tempVarName); //$NON-NLS-1$
        }

    	String expr = String.format("$%s.%s", new Object[]{varName, part.getName()}); //$NON-NLS-1$
		org.eclipse.bpel.model.From from = BPELUtils.createFromScript(expr, ScriptGrammarFactory.XPATH);
		org.eclipse.bpel.model.To to = BPELUtils.createToVariable(tempVarName);

		copy.setFrom(from);
		copy.setTo(to);
		if (optionalVariables.contains(part.getName())) {
			copy.setIgnoreMissingFromData(true);			
		}
		return copy;
	}

	static org.eclipse.bpel.model.Copy createFactoryScriptToTempVarMapping(String tempVarName, String script) {
		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		BPELUtils.addExtensionAttribute(copy, "tempVariable", tempVarName); //$NON-NLS-1$

		org.eclipse.bpel.model.From from = BPELUtils.createFromScript(script, ScriptGrammarFactory.JAVASCRIPT);
		org.eclipse.bpel.model.To to = BPELUtils.createToVariable(tempVarName);
		copy.setFrom(from);
		copy.setTo(to);

		return copy;
	}
	
	private static org.eclipse.bpel.model.Copy createTempVarToComplexPartMapping(
			String varName, javax.wsdl.Part part, XSDTypeDefinition typeDefinition) {
		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		BPELUtils.addExtensionAttribute(copy, "transform", "ObjectToXml"); //$NON-NLS-1$ //$NON-NLS-2$ 
		BPELUtils.addExtensionAttribute(copy, "objectName", typeDefinition.getName()); //$NON-NLS-1$
		BPELUtils.addExtensionAttribute(copy, "objectNamespace", typeDefinition.getTargetNamespace()); //$NON-NLS-1$
		if (part.getElementName() != null) {
			BPELUtils.addExtensionAttribute(copy, "elementName", part.getElementName().toString()); //$NON-NLS-1$
		}

		/*
         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
         */
		org.eclipse.bpel.model.From from = BPELUtils.createFromVariable(N2PEConstants.MESSAGE_PREFIX + NameUtil.sanitizeMessagePartVariableName(part.getName()));
		org.eclipse.bpel.model.To to = BPELUtils.createToVariableWithPart(varName, part.getName(), null);
		copy.setFrom(from);
		copy.setTo(to);
		copy.setIgnoreMissingFromData(true);			

		return copy;
	}
	
	private org.eclipse.bpel.model.Copy convertSimpleDataMappingToCopy(final DataMappingInfo dataMappingInfo) {
		if (!dataMappingInfo.isValid()) {
			return null;
		}
		
		org.eclipse.bpel.model.To to = BPELUtils.createToVariable(dataMappingInfo.getToExpression());
		org.eclipse.bpel.model.From from = BPELUtils.createFromVariable(dataMappingInfo.getFromExpression());

		org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
		copy.setTo(to);
		copy.setFrom(from);
		configureOptionalCopy(copy, dataMappingInfo);
		
		return copy;
	}

	private org.eclipse.bpel.model.Copy convertDataMappingToCopy(
			final DataMappingInfo dataMappingInfo, String varName) throws ConversionException {
		/* Example:
	      <xpdl2:DataMappings>
	        <xpdl2:DataMapping Direction="OUT" Formal="wso:GetBalance/part:AccountNumber">
	          <xpdl2:Actual ScriptGrammar="XPath">AccountNumber</xpdl2:Actual>
	        </xpdl2:DataMapping>
	      </xpdl2:DataMappings>
		 */
		if (!dataMappingInfo.isValid()) {
			return null;
		}		
		
        String target = dataMappingInfo.getToExpression();
        String script = dataMappingInfo.getFromExpression();
    	CorrelationMode mode = correlationDataFields.get(target);
    	if (mode != null && mode == CorrelationMode.CORRELATE) {
    		// do nothing - the data mapping is meant to be used as a correlation expression
    		return null;
    	}

    	boolean mappingInbound = dataMappingInfo.isMappingInbound();
    	
        ScriptMappingCompositorFactory factory = dataMappingInfo.getScriptMappingCompositorFactory();
        if (factory != null) {
            ScriptMappingCompositor compositor = factory.getCompositor(xpdlActivity, target, script);
            if (compositor instanceof SingleMappingCompositor) {
                SingleMappingCompositor single = (SingleMappingCompositor) compositor;
                String expression = single.getScript();
                if (expression != null) {
					org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();

                	org.eclipse.bpel.model.From from;
					org.eclipse.bpel.model.To to;
					if (mappingInbound) {
						from = dataMappingInfo.isScript() ? 
							createFromScript(expression, dataMappingInfo, false) : 
							createFromVariableWithPart(varName, expression, dataMappingInfo.getGrammar(), dataMappingInfo);

						boolean mappingToVariable = dataMappingInfo.isXPath() || target.indexOf(".") < 0; //$NON-NLS-1$
						if (mappingToVariable) {
							to = BPELUtils.createToVariable(target);
						} else {
							to = createToExpressionWithCDS(target, mappingInbound);
							BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					} else {
						from = dataMappingInfo.isScript() || !dataMappingInfo.isXPath() ? createFromScript(expression, dataMappingInfo, false) : 
							BPELUtils.createFromVariable(expression);
						boolean mappingToPart = dataMappingInfo.isXPath() || target.indexOf(".") < 0; //$NON-NLS-1$
						if (mappingToPart) {
							to = createToVariableWithPart(varName, target, dataMappingInfo.getGrammar());
						} else {
							to = createToExpressionWithCDS(target, mappingInbound);
							BPELUtils.addExtensionAttribute(from, "returnVar", "fromReturn"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}
						
					copy.setTo(to);
					copy.setFrom(from);
					configureOptionalCopy(copy, dataMappingInfo);

					if (!dataMappingInfo.isXPath() && from.getPart() != null) {
						javax.wsdl.Part part = wsdlMessage.getPart(from.getPart().getName());
						XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(
								wso.getWsdlDefinition(), part);
						if (typeDefinition instanceof XSDComplexTypeDefinition) {
							setXmlToObjectTransformAttributes(copy, part, typeDefinition);
						}
					}

					if (!dataMappingInfo.isXPath() && to.getPart() != null) {
						javax.wsdl.Part part = wsdlMessage.getPart(to.getPart().getName());
						XSDTypeDefinition typeDefinition = WSDLUtils.getTypeDefinitionForPart(
								wso.getWsdlDefinition(), part);
						if (typeDefinition instanceof XSDComplexTypeDefinition) {
							BPELUtils.addExtensionAttribute(copy, "transform", "ObjectToXml"); //$NON-NLS-1$ //$NON-NLS-2$
							if (part.getElementName() != null) {
								BPELUtils.addExtensionAttribute(copy, "elementName", part.getElementName().toString()); //$NON-NLS-1$
							}
						}
					}
					
					return copy;
                }
            }
		} else {
			//assume the entire expression is the variable name
			org.eclipse.bpel.model.To to = BPELUtils.createToVariable(varName);
			org.eclipse.bpel.model.From from = BPELUtils.createFromVariable(dataMappingInfo.getFromExpression());

			org.eclipse.bpel.model.Copy copy = BPELFactory.eINSTANCE.createCopy();
			copy.setTo(to);
			copy.setFrom(from);
			configureOptionalCopy(copy, dataMappingInfo);
			
			return copy;
		}
        
        return null;
	}

	/**
	 * Add the appropriate 'setIgnoreMissingFromData' attribute value to the given copy activity.
	 * 
	 * @param copy
	 * @param dataMappingInfo
	 */
	private void configureOptionalCopy(org.eclipse.bpel.model.Copy copy, DataMappingInfo dataMappingInfo) {
        /*
         * Sid XPD-8010 - changed this method to wrap a method that doesn't need
         * DataMappingInfos.
         */
        configureOptionalCopy(copy,
                dataMappingInfo.getFromExpression(),
                dataMappingInfo.isMappingInbound(),
                dataMappingInfo.getGrammar());
	}
	
	/**
	 * Add the appropriate 'setIgnoreMissingFromData' attribute value to the given copy activity.
     *
	 * @param copy
	 * @param fromExpression
	 * @param isMappingInBoundToProcess
	 * @param grammar
	 */
	private void configureOptionalCopy(org.eclipse.bpel.model.Copy copy, String fromExpression, boolean isMappingInBoundToProcess, String grammar) {

	    From from = copy.getFrom();
		if (from.getLiteral() != null) {
			return;
		}
		if (from.getVariable() != null && optionalVariables.contains(from.getVariable().getName())) {
			copy.setIgnoreMissingFromData(true);
			return;
		}
		
		To to = copy.getTo();
		if (to.getVariable() != null && optionalVariables.contains(to.getVariable().getName())) {
			copy.setIgnoreMissingFromData(true);
			return;
		}
		
		if (!ScriptGrammarFactory.XPATH.equals(grammar) && from.getExpression() != null) {
			String expr = fromExpression;

			if (optionalVariables.contains(expr)) {
				copy.setIgnoreMissingFromData(true);
				return;
			}
			
			Property property = isMappingInBoundToProcess ? 
					CDSUtils.getProperty(context, wsdlMessage, expr) : CDSUtils.getProperty(xpdlActivity, expr);
			if (property != null && property.getLower() == 0) {
				copy.setIgnoreMissingFromData(true);
				return;
	    	}			
		}
	}
	
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
	    		enumTypeName = enumTypeName.replace(".", "_").replace("::", "_"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
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
    			String remainingPathJsVarAlias = expression.substring(pos);
    	        expression = NameUtil.sanitizeMessagePartVariableName(partName) + remainingPathJsVarAlias;
    			expression = N2PEConstants.MESSAGE_PREFIX + expression;
    		}			
    	}
		
    	if (isArray) {
        	script.append(expression).append(".clear();\n"); //$NON-NLS-1$
        	if (enumTypeName == null) {
        		script.append(expression).append(".addAll(fromReturn);"); //$NON-NLS-1$
        	} else {
        		script.append("for (var i = 0; i < fromReturn.size(); i++) {\n"); //$NON-NLS-1$
        		script.append("var val = fromReturn.get(i);\n"); //$NON-NLS-1$
        		script.append(expression).append(".add(").append(enumTypeName).append(".get(val));\n"); //$NON-NLS-1$ //$NON-NLS-2$
        		script.append("}\n"); //$NON-NLS-1$
        	}
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
			query = BPELUtils.searchAndReplacePrefixes(context, wso.getWsdlDefinition(), part, query);
    	}

		org.eclipse.bpel.model.To to = BPELUtils.createToVariableWithPart(varName, partName, query);
    	return to;
	}

	private org.eclipse.bpel.model.From createFromVariableWithPart(String varName, String script, String grammar, DataMappingInfo dataMappingInfo) {
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
    	
		int faultPos = script.indexOf(FAULT_MARKER);
		if (faultPos <= 0) {
	    	part = wsdlMessage.getPart(partName);
    	} else {
			int faultStartPos = faultPos + FAULT_MARKER.length();
			int faultEndPos = script.indexOf("/", faultStartPos); //$NON-NLS-1$
			varName = script.substring(faultStartPos, faultEndPos);
			javax.wsdl.Fault fault = wso.getFault(varName);
			part = fault.getMessage().getPart(partName);
    	}

		org.eclipse.bpel.model.From from;
		
		if (part != null) {
			if (query == null) { //simple part
				from = BPELUtils.createFromVariableWithPart(varName, partName, null);
			} else { //complex part
		    	if (isXPath) {
					query = BPELUtils.searchAndReplacePrefixes(context, wso.getWsdlDefinition(), part, query);
					from = BPELUtils.createFromVariableWithPart(varName, partName, query);
		    	} else {
					from = createFromScript(expression, dataMappingInfo, true);
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

	private From createFromScript(String expression, DataMappingInfo dataMappingInfo, boolean usePrefix) {
		boolean isXPath = ScriptGrammarFactory.XPATH.equals(dataMappingInfo.getGrammar());
		boolean isScript = dataMappingInfo.isScript();
    	boolean isCDS = false;
		boolean isArray = false;		
		boolean isSingleInstOverridingMultiInstProperty = false;

		if (!isXPath && !isScript) {
    		URI bomClassURI = dataMappingInfo.isMappingInbound() ? 
    				CDSUtils.getBOMClassURI(context, wsdlMessage, expression) :
    				CDSUtils.getBOMClassURI(xpdlActivity, expression);
			isCDS = bomClassURI != null;

    		if (dataMappingInfo.isMappingInbound()) {
    			Property property = CDSUtils.getProperty(context, wsdlMessage, expression);
        		if (property != null) {
        	    	isArray = property.getUpper() > 1 || property.getUpper() == -1;
        	    	isSingleInstOverridingMultiInstProperty =  XSDUtil.isMultiInstancePropertyBasedOnXSDRestriction(property) 
    	    			&& XSDUtil.isClassXsdComplexTypeRestriction(property.getClass_());
        		}	
    		} else {
        		ProcessRelevantData processData = CDSUtils.getActivityInterfaceData(xpdlActivity, expression);
        		if (processData != null) {
        	    	isArray = processData.isIsArray();
        		} else {
        			Property property = CDSUtils.getProperty(xpdlActivity, expression);
            		if (property != null) {
            	    	isArray = property.getUpper() > 1 || property.getUpper() == -1;
            	    	isSingleInstOverridingMultiInstProperty = XSDUtil.isMultiInstancePropertyBasedOnXSDRestriction(property)
            	    		&& XSDUtil.isClassXsdComplexTypeRestriction(property.getClass_());
            		}	
        		}
    		}
    		
    		if (usePrefix) {
    			/*
    	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
    	         */
    	        if (expression.indexOf('.') > 0) {
    	        	String messagePart = expression.substring(0, expression.indexOf("."));
    	        	String remainingPathJsVarAlias = expression.substring(expression.indexOf("."));
    	        	expression = NameUtil.sanitizeMessagePartVariableName(messagePart) + remainingPathJsVarAlias;
    	        } else {
    	        	expression = NameUtil.sanitizeMessagePartVariableName(expression);
    	        }
        		expression = N2PEConstants.MESSAGE_PREFIX + expression;
    		}
    		
    		if (isSingleInstOverridingMultiInstProperty) {
        		expression = isCDS ?
        				"ScriptUtil.copy(" + expression + ".get(0))" : //$NON-NLS-1$ //$NON-NLS-2$
            			expression + ".get(0)"; //$NON-NLS-1$
	    	} else if (isCDS) {
	        	expression = isArray ? 
	        			"ScriptUtil.copyAll(" + expression + ")" : //$NON-NLS-1$ //$NON-NLS-2$
	        			"ScriptUtil.copy(" + expression + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	    	}
    	}

		org.eclipse.bpel.model.From from = BPELUtils.createFromScript(expression, dataMappingInfo.getGrammar());
		
		if (!isXPath) {
			hasJavaScriptMapping = true;
		}
		
		if (!isXPath && isScript) {
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

	/**
	 * DataMappingInfo is info only used for JavaScript / XPath mappings.
	 *
	 *
	 * @author aallway
	 * @since 22 Feb 2016
	 */
	/*package*/ static class DataMappingInfo {
		
		private final boolean valid;
		private final String target;
		private final String script;
		private final String grammar;
		private final DirectionType mappingDirection;
		private final boolean isScript;

		DataMappingInfo(DataMapping dataMapping) {
			Expression actual = dataMapping.getActual();
			valid = actual != null;
	        target = DataMappingUtil.getTarget(dataMapping);
	        script = DataMappingUtil.getScript(dataMapping);
	        grammar = DataMappingUtil.getGrammar(dataMapping);
	    	mappingDirection = dataMapping.getDirection();
        	ScriptInformation scriptInformation = XPDLUtils.getScriptInformation(dataMapping);
        	isScript = scriptInformation != null;
		}
		
		public boolean isValid() {
			return valid;
		}
		
		public String getToExpression() {
			return this.target;
		}

		public String getFromExpression() {
			return this.script;
		}

		public String getGrammar() {
			return this.grammar;
		}

		public boolean isMappingInbound() {
			return DirectionType.OUT_LITERAL.equals(mappingDirection);
		}

		public boolean isScript() {
			return this.isScript;
		}

		boolean isXPath() {
			return ScriptGrammarFactory.XPATH.equals(grammar);
		}
		
		ScriptMappingCompositorFactory getScriptMappingCompositorFactory() {
	        return ScriptMappingCompositorFactory.getCompositorFactory(grammar, mappingDirection);
		}
	}
	
	static class PartMappings {
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
		
		public boolean hasPart(String partName) {
			return partToPaths.containsKey(partName);
		}
	}
	
	

    /**
     * XPD-8010: Convert mappings from process data to service data for Datamapper Grammar.
     * 
     * <pre>
     * Use case 1: Input To Service
     *   Target BPEL Construct:
     *      {!-- First we add a new scope element; we add all of the MESSAGE_partname 
     *           variables that will be created by the data mapper generated script activity. --}
     *      {scope - handle the input to service mappings (this is NOT the main scope for invoke activity it is nested replaced for original {assign} }
     *          {!-- Add All MESSAGE_partname variables to new scope --}
     *      
     *          {sequence}
     *          
     *              {extensionActivity for generated DataMapper Script - process data to MESSAGE_partname variables}
     *              
     *              {assign}
     *                 {!-- for each COMPLEX part --}
     *                 {copy using ObjectToXml mode from MESSAGE_partname to v_XXXXX part=partname}
     *              
     *                 {!-- for each SIMPLE Input Part --}
     *                 {copy using straight JAvaScrtipt mode from MESSAGE_partname to v_XXXXX part=partname   (transform type not set.)}
     *              
     *              
     *      {invoke the service}
     * 
     * </pre>
     * <p>
     * Use case 2: 
     * 
     * Output From Process - <b>This is exactly the same as Input To
     * Service as per comment above (just gets passed WSDL OutputMessage instead)</b>
     * 
     * <p>
     * Use case 3: 
     * 
     * Output Fault From Process - <b>This is exactly the same as Input To
     * Service as per comment above  (just gets passed WSDL OutputMessage instead)</b>
     * 
     * @param message
     * @param dataMapperInputMappings
     * @param varName
     * @param wsdlMessage
     * 
     * @return activity to process the input to service mappings or
     *         <code>null</code> if no data mappings.
     */
    private org.eclipse.bpel.model.Activity convertProcessToServiceMappingsForDataMapper(
            Message message, ScriptDataMapper dataMapperInputMappings,
            String varName, javax.wsdl.Message wsdlMessage) {

        if (dataMapperInputMappings.getDataMappings().isEmpty()) {
            return null;
        }

        /**
         * <pre>
         * Use case 1: Input To Service
         *   Target BPEL Construct:
         *      {!-- First we add a new scope element; we add all of the MESSAGE_partname 
         *           variables that will be created by the data mapper generated script activity. --}
         *      {scope}
         * </pre>
         */
        Scope mapIntoServiceScope = BPELFactory.eINSTANCE.createScope();
        mapIntoServiceScope.setName(context
                .genUniqueActivityName("ToSvcDataMapping")); //$NON-NLS-1$

        /**
         * <pre>
         * {!-- Add All MESSAGE_partname variables to scope --}
         * </pre>
         */
        Map<String, javax.wsdl.Part> parts = wsdlMessage.getParts();

        addDataMapperScopeVariables(mapIntoServiceScope, parts);

        /**
         * <pre>
         *      {!-- The sequence that will contain the data-mapper script to 
         *               create MESSAGE_xxx variables from process data. --}
         *      {sequence}
         * </pre>
         */
        Sequence sequence = BPELFactory.eINSTANCE.createSequence();
        sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$

        mapIntoServiceScope.setActivity(sequence);

        /**
         * <pre>
         *          {extensionActivity for generated DataMapper Script}
         * </pre>
         */
        ExtensionActivity scriptActivity =
                createDataMapperMappingScript(dataMapperInputMappings);

        if (scriptActivity != null) {
            sequence.getActivities().add(scriptActivity);
        }

        /**
         * <pre>
         * {!-- Now the assign statement that will copy from MESSAGE_xxxx to the 
         * v_XXXXX XML input variable (declared on parent scope before we got here) --}
         * 
         *          { assign }
         * </pre>
         */
        assign = BPELFactory.eINSTANCE.createAssign();
        assign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$

        sequence.getActivities().add(assign);

        /*
         * For Mapping Into Service, we can set up the variableDescriptor
         * element (Steve says this is for batch loading from dbase.
         */
        XPDLUtils.findReferencedData(context,
                xpdlActivity,
                sequence.getName(),
                DataReferenceContext.CONTEXT_MAPPING_IN);

        /**
         * <pre>
         *              {!-- for each COMPLEX Input Part --}
         *              {copy using ObjectToXml mode from MESSAGE_partname to v_XXXXX part=partname}
         *              
         *              {!-- for each SIMPLE Input Part --}
         *              {copy using straight JAvaScrtipt mode from MESSAGE_partname to v_XXXX part=partname   (transform type not set.)}
         * </pre>
         */
        for (Part messagePart : parts.values()) {
            String msgPartName = messagePart.getName();

            org.eclipse.bpel.model.Copy copy =
                    BPELFactory.eINSTANCE.createCopy();

            /*
	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
	         */
            String fromExpression = N2PEConstants.MESSAGE_PREFIX + NameUtil.sanitizeMessagePartVariableName(msgPartName);
            From from =
                    BPELUtils.createFromScript(fromExpression,
                            ScriptGrammarFactory.JAVASCRIPT);

            To to =
                    createToVariableWithPart(varName,
                            msgPartName,
                            ScriptGrammarFactory.JAVASCRIPT);

            copy.setFrom(from);
            copy.setTo(to);

            configureOptionalCopy(copy,
                    fromExpression,
                    false,
                    ScriptGrammarFactory.JAVASCRIPT);

            XSDTypeDefinition typeDefinition =
                    WSDLUtils.getTypeDefinitionForPart(wso.getWsdlDefinition(),
                            messagePart);

            if (typeDefinition instanceof XSDComplexTypeDefinition) {
                /*
                 * The variable representing the part is a complex type so we
                 * need the ObjectToXML style of copy.
                 */
                BPELUtils.addExtensionAttribute(copy,
                        "transform", "ObjectToXml"); //$NON-NLS-1$ //$NON-NLS-2$
                if (messagePart.getElementName() != null) {
                    BPELUtils
                            .addExtensionAttribute(copy,
                                    "elementName", messagePart.getElementName().toString()); //$NON-NLS-1$
                }

            } else {
                /*
                 * The variable representing the part will be simple type so we
                 * need the straight JavaScript primitive type copy. (the target
                 * is the XML representation of the source, which for simple
                 * type is the same).
                 * 
                 * In this case there's nothing else we need to do.
                 */
            }

            assign.getCopy().add(copy);
        }

        /**
         * That's it, all done!
         */
        return mapIntoServiceScope;
    }

    /**
     * XPD-8010: Convert mappings from service data to process data for Datamapper Grammar.
     * 
     * <pre>
     * Use case 1: Output From Service
     *   Target BPEL Construct:
     *      {!-- First we add a new scope element; we add all of the MESSAGE_partname 
     *           variables that will be created by the data mapper generated script activity. --}
     *           
     *      {scope - handle the output from service mappings (this is NOT the main scope for invoke activity it is nested replaced for original {assign} }
     *          {!-- Add All MESSAGE_partname variables to new scope --}
     *      
     *          {sequence}
     *              {assign}
     *                 {!-- for each COMPLEX part --}
     *                 {copy using XmlToObject mode from MESSAGE_partname to v_XXXXX part=partname}
     *              
     *                 {!-- for each SIMPLE Input Part --}
     *                 {copy no specific grammar mode from MESSAGE_partname to v_XXXXX part=partname (transform type not set.)}
     *              
     *          
     *              {extensionActivity for generated DataMapper Script - process data to MESSAGE_partname variables}
     *              
     *              
     *      {invoke the service}
     * 
     * </pre>
     * <p>
     * Use case 2: 
     * 
     * ??? TBD Input To Process - <b>This is exactly the same as Input To
     * Service as per comment above (just gets passed WSDL OutputMessage instead)</b>
     * 
     * <p>
     * Use case 3: 
     * 
     * ??? TBD  Catch Web Service Fault - <b>This is exactly the same as Input To
     * Service as per comment above  (just gets passed WSDL OutputMessage instead)</b>
     * 
     * @param message
     * @param dataMapperOutputMappings
     * @param varName
     * @param wsdlMessage
     * 
     * @return activity to process the input to service mappings or
     *         <code>null</code> if no data mappings.    */
    private org.eclipse.bpel.model.Activity convertServiceToProcessMappingsForDataMapper(
            Message message, ScriptDataMapper dataMapperOutputMappings,
            String varName, javax.wsdl.Message wsdlMessage) {

        if (dataMapperOutputMappings.getDataMappings().isEmpty()) {
            return null;
        }

        /**
         * <pre>
         * Use case 1: Input To Service
         *   Target BPEL Construct:
         *      {!-- First we add a new scope element; we add all of the MESSAGE_partname 
         *           variables that will be created by the data mapper generated script activity. --}
         *      {scope}
         * </pre>
         */
        Scope mapIntoServiceScope = BPELFactory.eINSTANCE.createScope();
        mapIntoServiceScope.setName(context
                .genUniqueActivityName("FromSvcDataMapping")); //$NON-NLS-1$

        /**
         * <pre>
         * {!-- Add All MESSAGE_partname variables to scope --}
         * </pre>
         */
        
        Map<String, javax.wsdl.Part> parts = new HashMap<>();
        
        if(wsdlMessage!=null)
        {
            parts = wsdlMessage.getParts();

            addDataMapperScopeVariables(mapIntoServiceScope, parts);
        }

        /**
         * <pre>
         *      {!-- The sequence that will contain the data-mapper script to 
         *               create MESSAGE_xxx variables from process data. --}
         *      {sequence}
         * </pre>
         */
        Sequence sequence = BPELFactory.eINSTANCE.createSequence();
        sequence.setName(context.genUniqueActivityName("sequence")); //$NON-NLS-1$

        mapIntoServiceScope.setActivity(sequence);


        /**
         * <pre>
         * {!-- Now the assign statement that will copy from v_XXXX.partname XML output variable to the 
         * MESSAGE_xxxx process data variable (declared on parent scope before we got here) --}
         * 
         *          { assign }
         * </pre>
         */
        assign = BPELFactory.eINSTANCE.createAssign();
        assign.setName(context.genUniqueActivityName("assign")); //$NON-NLS-1$

        sequence.getActivities().add(assign);

        /**
         * <pre>
         *                 {!-- for each COMPLEX part --}
         *                 {copy using XmlToObject mode from MESSAGE_partname to v_XXXXX part=partname}
         *              
         *                 {!-- for each SIMPLE Input Part --}
         *                 {copy no specific grammar mode from MESSAGE_partname to v_XXXXX part=partname (transform type not set.)}
         * </pre>
         */
        for (Part messagePart : parts.values()) {
        	/*
	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
	         */
            String msgPartName = NameUtil.sanitizeMessagePartVariableName(messagePart.getName());

            XSDTypeDefinition typeDefinition =
                    WSDLUtils.getTypeDefinitionForPart(wso.getWsdlDefinition(),
                            messagePart);

            Copy copy = null;
            
            if (typeDefinition instanceof XSDComplexTypeDefinition) {
                /*
                 * The variable representing the part is a complex type so we
                 * need the XmlToObject style of copy.
                 */
                copy =
                        createComplexPartToTempVarMapping(varName,
                                messagePart,
                                N2PEConstants.MESSAGE_PREFIX + msgPartName,
                                typeDefinition,
                                true,
                                false);                

           } else {
                /*
                 * The variable representing the part will be simple type so we
                 * need the straight JavaScript primitive type copy. (the target
                 * is the XML representation of the source, which for simple
                 * type is the same).
                 * 
                 * In this case there's nothing else we need to do.
                 */
                copy = BPELFactory.eINSTANCE.createCopy();
                From from = BPELUtils.createFromVariableWithPart(varName, msgPartName, null);
                To to = BPELUtils.createToVariable(N2PEConstants.MESSAGE_PREFIX + msgPartName);
                
                copy.setFrom(from);
                copy.setTo(to);

                configureOptionalCopy(copy,
                        msgPartName,
                        false,
                        ScriptGrammarFactory.JAVASCRIPT);
           }

            assign.getCopy().add(copy);
        }

        
        /**
         * <pre>
         *          {extensionActivity for generated DataMapper Script}
         * </pre>
         */
        ExtensionActivity scriptActivity =
                createDataMapperMappingScript(dataMapperOutputMappings);

        if (scriptActivity != null) {
            sequence.getActivities().add(scriptActivity);
        }

        /**
         * That's it, all done!
         */
        return mapIntoServiceScope;

    }

    /**
     * XPD-8010: Add MESSAGE_xxxx variables to the given scope for each of the given
     * message parts.
     * 
     * @param scope
     * @param parts
     */
    private void addDataMapperScopeVariables(Scope scope,
            Map<String, javax.wsdl.Part> parts) {

        if (scope.getVariables() == null) {
            org.eclipse.bpel.model.Variables variables =
                    org.eclipse.bpel.model.BPELFactory.eINSTANCE
                            .createVariables();
            scope.setVariables(variables);
        }

        for (Part messagePart : parts.values()) {
            /*
             * Create the variable
             */
            org.eclipse.bpel.model.Variable variable =
                    org.eclipse.bpel.model.BPELFactory.eINSTANCE
                            .createVariable();

            /*
	         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
	         */
            variable.setName(N2PEConstants.MESSAGE_PREFIX
                    + NameUtil.sanitizeMessagePartVariableName(messagePart.getName()));

            /*
             * Get the BOM class (or null if not a complex type or complex
             * element
             */
            Class bomClass =
                    CDSUtils.getBOMClassForPartOrPath(context,
                            wsdlMessage,
                            messagePart.getName());

            if (bomClass != null) {
                /*
                 * If it's a complex type backed by a class type then add the
                 * tibex:class attribute
                 */
                BPELUtils
                        .addExtensionAttribute(variable,
                                "class", bomClass.getPackage().getName() + "." + bomClass.getName()); //$NON-NLS-1$ //$NON-NLS-2$

            } else {
                /*
                 * Else check for simple type definition and set the type
                 * attribute to the simple type definition.
                 */
                XSDTypeDefinition typeDefinition =
                        WSDLUtils.getTypeDefinitionForPart(wso
                                .getWsdlDefinition(), messagePart);

                if (typeDefinition instanceof XSDSimpleTypeDefinition) {
                    /**
                     * Sid XPD-8125. When creating the MESSAGE_xxx variables for
                     * simple types we MUST use the base XSD schema type instead
                     * of any user defined simple-type from a schema.
                     * 
                     * See getMessagePartVariableSimpleType() for more details.
                     */
                    variable.setType(getMessagePartVariableSimpleType((XSDSimpleTypeDefinition) typeDefinition));

                } else {
                    String msg =
                            String.format("Could not ascertain type for message part '%s' in activity %s/%s", //$NON-NLS-1$
                                    messagePart.getName(),
                                    xpdlActivity.getProcess().getName(),
                                    xpdlActivity.getName());
                    ConverterActivator.logError(msg, new RuntimeException(msg));
                }
            }

            if (scope != null) {
                scope.getVariables().getChildren().add(variable);
            }
        }
    }

    /**
     * Sid XPD-8125. When creating the MESSAGE_xxx variables for simple types we
     * MUST use the base XSD schema type instead of any user defined simple-type
     * from a schema.
     * 
     * The problem is VERY complex, but basically ended up in NPE's when doing
     * the downstream DAA creation. The reason for this was that in BPELWriter
     * it keeps a WSDL to Namespace map and IF we refer to a simple type in our
     * variable definition AND this cross-reference is found before the
     * Port-Type cross-reference when deciding what import element sto output in
     * teh BPEL, then it will register the message part simple type's namespace
     * against the WSDL INSTEAD of the WSDL's own TARGET NAMESPACE.
     * 
     * This then meant that attempts to resolve the port-type or
     * partnerlink-role from an invoek activity for an operation in same WSDL
     * would fail because the import element have the schema namespace not the
     * target namespace.
     * 
     * OK this is probably more down to the poor handling of namespaces in
     * BPELWriter, but we are where we are. The fact is that for the purpose of
     * assigning the temporary MESSAGE_xxx variable, the specifics of the simple
     * type definition do not really matter, becuase the assignment to/from this
     * in the DataMapper script IS JUST AN "MESSAGE_xxx = proecssData" (or visa
     * versa) assignment and nothing special is done for the additional
     * restrictions etc that may have been defined on the schema's SimpleType
     * definition.
     * 
     * @param simpleTypeDefinition
     * 
     * @return The base XSD simple type that this type is based on.
     */
    protected XSDSimpleTypeDefinition getMessagePartVariableSimpleType(
            XSDSimpleTypeDefinition simpleTypeDefinition) {
        if (WSDLConstants.XSD_NAMESPACE_URI
                                .equals(simpleTypeDefinition.getTargetNamespace())) {
            return simpleTypeDefinition;
        }

        /* Recurs with the base type. */
        XSDSimpleTypeDefinition baseType = simpleTypeDefinition.getBaseTypeDefinition();
        if (baseType != null) {
            return getMessagePartVariableSimpleType(baseType);
        }
        
        return simpleTypeDefinition;
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

    /**
     * XPD-8010: This is a reference to the assign activity (which may or may not be _the_
     * mapping activity or may be part of a _scope_ activity that is
     * _the_mapping activity
     * 
     * @return the mappingAssignActivity
     */
    public Assign getMappingAssignActivity() {
        return assign;
    }
}
