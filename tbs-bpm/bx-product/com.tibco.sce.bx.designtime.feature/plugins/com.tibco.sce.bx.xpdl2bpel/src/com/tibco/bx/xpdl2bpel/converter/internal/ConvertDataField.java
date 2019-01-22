package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xsd.XSDSimpleTypeDefinition;

import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.DataTypeUtil;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataFieldsContainer;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;


public class ConvertDataField {

    private static final String ARRAY_EXTENSION = "array"; //$NON-NLS-1$
    public static final String PARAMETER_EXTENSION = "parameter"; //$NON-NLS-1$
	
	/** 
     * Convert the XPDL dataFields to BPEL.
     * @param context the converter context
     * @param dataFieldsContainer parent object of the List of DataFields.
     */
    public static void convertDataFieldsToBPEL(
    		final ConverterContext context, final DataFieldsContainer dataFieldsContainer) {
    	org.eclipse.bpel.model.Variables variables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
    	Map<String, org.eclipse.bpel.model.messageproperties.Property> properties = new HashMap<String, org.eclipse.bpel.model.messageproperties.Property>();
        List<DataField> dataFields = dataFieldsContainer.getDataFields();
		if (dataFields != null) {
            for (DataField dataField : dataFields) {
                try {                	
                	if (dataField.isCorrelation()) {                		
                		org.eclipse.bpel.model.messageproperties.Property property = convertCorrelationDataFieldToBPEL(dataFieldsContainer, dataField);
                		properties.put(dataField.getName(), property);                		                		
                	}
                    org.eclipse.bpel.model.Variable variable = convertDataFieldToBPELVariable(dataFieldsContainer, dataField);
                    variables.getChildren().add(variable);                	
        		} catch (ConversionException e) {
        			context.logError("Error occurred while converting the data field " + dataField.getName(), e);
        		}
            }
        }
        context.setVariables(dataFieldsContainer, variables);
        context.setProperties(dataFieldsContainer, properties);
    }

    /** 
     * Convert the XPDL dataField to BPEL.
     * @param dataFieldsContainer 
     * @param dataField the DataField to convert
     * @throws ConversionException 
     */
	public static org.eclipse.bpel.model.Variable convertDataFieldToBPELVariable(
			DataFieldsContainer dataFieldsContainer, DataField dataField) throws ConversionException {
		org.eclipse.bpel.model.Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		variable.setName(dataField.getName());
        BPELUtils.setXpdlId(variable, dataField.getId());

        setDataTypeForVariable(dataFieldsContainer, variable, dataField.getDataType(), dataField.isIsArray());
        
		if (!(dataField.getDataType() instanceof BasicType)) {
			return variable;
		}

		BasicType basicType = (BasicType) dataField.getDataType();
		
		if (basicType.getType() == BasicTypeType.PERFORMER_LITERAL) {
			Expression participantQuery = XPDLUtils.getParticipantQuery(basicType);
			if (participantQuery != null && "RQL".equals(participantQuery.getScriptGrammar())) { //$NON-NLS-1$
				String text = participantQuery.getText();
				if (text != null && text.length() > 0) {
					org.eclipse.bpel.model.From from = BPELUtils.createFromLiteral("<![CDATA[" + text + "]]>"); //$NON-NLS-1$  //$NON-NLS-2$
					variable.setFrom(from);
				}
			}
			return variable;
		}
		
        convertInitialValues(dataField, variable);
		
		return variable;
	}

	private static void convertInitialValues(DataField dataField, org.eclipse.bpel.model.Variable variable) throws ConversionException {
		BasicType basicType = (BasicType) dataField.getDataType();
		boolean isArray = dataField.isIsArray();
        InitialValues initialValues = XPDLUtils.getInitialValues(dataField);
        org.eclipse.bpel.model.From from;

        if (isArray) {
        	if (initialValues == null || initialValues.getValue().isEmpty()) {
        		return;
        	}
        	
        	StringBuffer script = new StringBuffer();
			script.append("importClass(Packages.java.util.ArrayList);\n");
        	script.append("var ret = new ArrayList();\n");
        	for (String val : initialValues.getValue()) {
				script.append("ret.add(");
				
				switch (basicType.getType()) {
				case BOOLEAN_LITERAL:
					script.append(val);
					break;
				case DATE_LITERAL:
					script.append("DateTimeUtil.createDate(\"").append(val).append("\")");
					break;
				case DATETIME_LITERAL:
					script.append("DateTimeUtil.createDatetime(\"").append(val).append("\")");
					break;
				case FLOAT_LITERAL:
					script.append(val);
					break;
				case INTEGER_LITERAL:
					script.append(val);
					break;
				case PERFORMER_LITERAL:
					throw new ConversionException("Performer fields cannot have initial values.");
				case REFERENCE_LITERAL:
					throw new ConversionException("Reference fields cannot have initial values.");
				case STRING_LITERAL:
					script.append("\"").append(val).append("\"");
					break;
				case TIME_LITERAL:
					script.append("DateTimeUtil.createTime(\"").append(val).append("\")");
					break;
				default:
					script.append(val);
					break;
				}
				
				script.append(");\n");
			}
        	script.append("ret;\n");
    		from = BPELUtils.createFromScript(script.toString(), ScriptGrammarFactory.JAVASCRIPT);
        } else {
    		String initVal = null;
    		if (initialValues != null && !initialValues.getValue().isEmpty()) {
    			initVal = initialValues.getValue().get(0);
    		} else {
    			Expression initialValue = dataField.getInitialValue();
    			if (initialValue != null) {
    				initVal = initialValue.getText();
    			}
    		}
    		
    		if (initVal == null || initVal.length() == 0) {
    			return;
    		}

			if (basicType.getType() == BasicTypeType.STRING_LITERAL ||
					basicType.getType() == BasicTypeType.DATE_LITERAL || 
					basicType.getType() == BasicTypeType.DATETIME_LITERAL ||
					basicType.getType() == BasicTypeType.TIME_LITERAL) {
				from = BPELUtils.createFromLiteral("<![CDATA[" + initVal + "]]>"); //$NON-NLS-1$  //$NON-NLS-2$
			} else {
				from = BPELUtils.createFromScript(initVal, ScriptGrammarFactory.JAVASCRIPT);
			}
			
			if ((basicType.getType() == BasicTypeType.INTEGER_LITERAL) && (initVal.length() > 10)) {
				//Integer.MAX_VALUE is 2147483647; if the text is longer than 10 digits, 
				//change the data type to long
				XSDSimpleTypeDefinition longType = DataTypeUtil.getXSDPrimitive("long");
				variable.setType(longType);
			}
		}

        variable.setFrom(from);        	
	}

    /** 
     * Convert the XPDL FormalParameters to BPEL.
     * @param context the converter context
     * @param xpdlProcess the XPDL process.
     */
    public static void convertFormalParametersToBPEL(
    		final ConverterContext context, final com.tibco.xpd.xpdl2.Process xpdlProcess) {
    	org.eclipse.bpel.model.Variables variables = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariables();
        List<FormalParameter> parameters = ProcessInterfaceUtil.getAllFormalParameters(xpdlProcess);
		if (parameters != null) {
            for (FormalParameter parameter : parameters) {
                try {
                	org.eclipse.bpel.model.Variable variable = convertFormalParameterToBPELVariable(xpdlProcess, parameter);
                	variables.getChildren().add(variable);
        		} catch (ConversionException e) {
        			context.logError("Error occurred while converting the parameter " + parameter.getName(), e);
        		}
            }
        }
        context.setVariables(xpdlProcess, variables);
    }

    /** 
     * Convert the XPDL FormalParameter to BPEL variable.
     * @param xpdlProcess 
     * @param parameter the FormalParameter to convert
     * @throws ConversionException 
     */
	private static org.eclipse.bpel.model.Variable convertFormalParameterToBPELVariable(
			Process xpdlProcess, FormalParameter parameter) throws ConversionException {
		org.eclipse.bpel.model.Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		variable.setName(parameter.getName());
        BPELUtils.setXpdlId(variable, parameter.getId());

		setDataTypeForVariable(xpdlProcess, variable, parameter.getDataType(), parameter.isIsArray());
        
        int mode = parameter.getMode().getValue();
        if (mode==ModeType.IN) {
        	BPELUtils.addExtensionAttribute(variable, PARAMETER_EXTENSION, "in"); //$NON-NLS-1$
        } else if (mode==ModeType.INOUT) {
        	BPELUtils.addExtensionAttribute(variable, PARAMETER_EXTENSION, "inout"); //$NON-NLS-1$        	
        } else if (mode==ModeType.OUT) {
        	BPELUtils.addExtensionAttribute(variable, PARAMETER_EXTENSION, "out"); //$NON-NLS-1$        	
        }
        return variable;
	}

	public static void setDataTypeForVariable(DataFieldsContainer dataFieldsContainer, 
			org.eclipse.bpel.model.Variable variable, DataType dataType, boolean isArray) throws ConversionException {
		if (dataType instanceof RecordType) {
			EList<Member> memberList = ((RecordType) dataType).getMember();
            Member member = memberList.get(0);

            ExternalReference dataTypeExternalReference = null;
            if (null != member.getExternalReference()) {
            	dataTypeExternalReference = member.getExternalReference();
            }

			String bomClassName = XPDLUtils.getBomClassName((ExternalReference) dataTypeExternalReference);
			BPELUtils.addExtensionAttribute(variable, "classRef", bomClassName); //$NON-NLS-1
		} else if (dataType instanceof ExternalReference) {
			String bomClassName = XPDLUtils.getBomClassName((ExternalReference) dataType);
			BPELUtils.addExtensionAttribute(variable, "class", bomClassName); //$NON-NLS-1
		} else if (dataType instanceof BasicType) {
			BasicType basicType = (BasicType) dataType;
			XSDSimpleTypeDefinition xsdSimpleTypeDefinition = DataTypeUtil.getXsdForBasicType(basicType);		
			variable.setType(xsdSimpleTypeDefinition);
		} else if (dataType instanceof DeclaredType) {
			DeclaredType dt = (DeclaredType) dataType;
    		String typeId = dt.getTypeDeclarationId();
            com.tibco.xpd.xpdl2.Package pckg = Xpdl2ModelUtil.getPackage(dataFieldsContainer);
            if (pckg != null && typeId != null) {
                TypeDeclaration typeDecl = pckg.getTypeDeclaration(typeId);
                if (typeDecl != null) {
                    if (typeDecl.getBasicType() != null) {
                		setDataTypeForVariable(dataFieldsContainer, variable, typeDecl.getBasicType(), isArray);
                    } else if (typeDecl.getExternalReference() != null) {
                		setDataTypeForVariable(dataFieldsContainer, variable, typeDecl.getExternalReference(), isArray);
                    }
                }
            }

            return;
		}

        if (isArray) {
        	BPELUtils.addExtensionAttribute(variable, ARRAY_EXTENSION, "yes");
        }
	}

	private static org.eclipse.bpel.model.messageproperties.Property convertCorrelationDataFieldToBPEL(DataFieldsContainer dataFieldsContainer, DataField dataField) throws ConversionException {
		DataType dataType = dataField.getDataType();
		// XPDL allows other types here (Arrays, etc.), but BusinessStudio seems to use only BasicType
		if (!(dataType instanceof BasicType)) {
			throw new ConversionException(Messages.getString("ConvertDataField.dataFieldNotBasicType") + dataType.toString()); //$NON-NLS-1$
		}

		XSDSimpleTypeDefinition xsdSimpleTypeDefinition = DataTypeUtil.getXsdForBasicType((BasicType)dataType);
		boolean useUnqualifiedCorrelationPropertyNames = XPDLUtils.useUnqualifiedCorrelationPropertyNames(dataFieldsContainer);
		String prefix = !useUnqualifiedCorrelationPropertyNames && dataFieldsContainer instanceof NamedElement ? 
				((NamedElement) dataFieldsContainer).getName() + "_" : ""; //$NON-NLS-1$ //$NON-NLS-2$
		
		org.eclipse.bpel.model.messageproperties.Property property = org.eclipse.bpel.model.messageproperties.MessagepropertiesFactory.eINSTANCE.createProperty();
		property.setName(prefix + dataField.getName());
		property.setType(xsdSimpleTypeDefinition);
        //ConvertUtil.setXpdlId(property, dataField.getId());
        
		return property;
	}
}
