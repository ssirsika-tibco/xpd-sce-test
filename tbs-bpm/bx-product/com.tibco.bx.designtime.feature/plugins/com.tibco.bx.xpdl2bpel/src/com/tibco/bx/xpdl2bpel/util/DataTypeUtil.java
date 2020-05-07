package com.tibco.bx.xpdl2bpel.util;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.util.XSDConstants;
import org.eclipse.xsd.util.XSDUtil;

import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.Tracing;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;

public class DataTypeUtil {

	// Get the schema for schemas instance to use when resolving primitives
	private static XSDSchema schemaForSchemas = XSDUtil.getSchemaForSchema(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);

    public static XSDSimpleTypeDefinition getXSDPrimitive(String xsdTypeName) {
    	return schemaForSchemas.resolveSimpleTypeDefinition(xsdTypeName);
    }

    /** 
     * Convert the basic type parameter to a string based xsd type.
     * @param basicType The type to convert.
     * @return The string based xsd type matching the BasicType passed in (e.g. "xsd:boolean" for BOOLEAN.
     * <element name="BasicType">
     *    <complexType>
     *     <sequence>
     *       <element minOccurs="0" ref="xpdl:Length", "xpdl:Precision", "xpdl:Scale"/>
     *       <any namespace="##other" processContents="lax" minOccurs="0" maxOccurs="unbounded"/>
     *     </sequence>
     *     <attribute name="Type" use="required">
     *       <simpleType> <restriction base="NMTOKEN"> <enumeration values="STRING", "FLOAT", "INTEGER", "REFERENCE", "DATETIME", "BOOLEAN", "PERFORMER"/> </restriction> </simpleType>
     *     </attribute>
     *     <anyAttribute namespace="##other" processContents="lax"/>
     *   </complexType>
     * </element> 
     */
    public static XSDSimpleTypeDefinition getXsdForBasicType(BasicType basicType) throws ConversionException {
        String xsdType = ""; //$NON-NLS-1$

        if (basicType != null) {
            BasicTypeType basicTypeType = basicType.getType();
            if (basicTypeType != null) {
                int basicTypeTypeValue = basicTypeType.getValue();
                // These String constants should not be translated
                switch (basicTypeTypeValue) {
                case BasicTypeType.BOOLEAN:
                    xsdType = "boolean"; //$NON-NLS-1$
                    break;
                case BasicTypeType.DATETIME:
                    xsdType = "dateTime"; //$NON-NLS-1$
                    break;
                case BasicTypeType.DATE:
                    xsdType = "date"; //$NON-NLS-1$
                    break;
                case BasicTypeType.TIME:
                    xsdType = "time"; //$NON-NLS-1$
                    break;
                    
                case BasicTypeType.FLOAT:
                    /* Sid ACE-3585 for fixed point zero decimal place numbers, create xsd:integer variables in BPEL. */
                    if (basicType.getScale() != null && basicType.getScale().getValue() == 0) {
                        xsdType = "integer";

                    } else {
                        xsdType = "double"; //$NON-NLS-1$
                    }

                    break;
                case BasicTypeType.INTEGER:
                    xsdType = basicType.getPrecision() == null
                            || Integer.valueOf(basicType.getPrecision().getValue()) <= 9 ? "integer" : "long"; //$NON-NLS-1$ //$NON-NLS-2$
                    break;
                case BasicTypeType.PERFORMER:
                    // performer is a text field
                    xsdType = "string"; //$NON-NLS-1$
                    break;
                case BasicTypeType.REFERENCE:
                    throw new ConversionException(Messages.getString("ConvertBasicType.cannotCovertReference")); //$NON-NLS-1$
                case BasicTypeType.STRING:
                    xsdType = "string"; //$NON-NLS-1$
                    break;
                }
            }
        }

        return getXSDPrimitive(xsdType);
    }

    public static XSDSimpleTypeDefinition getXsdForClass(Class<?> clazz) {
        if (String.class.equals(clazz)) {
    		return getXSDPrimitive("string"); //$NON-NLS-1$
        }

        if (!clazz.isPrimitive()) {
    		return null;
    	}
    	
		String xsdType;

        if (Boolean.TYPE.equals(clazz)) {
        	xsdType = "boolean"; //$NON-NLS-1$
        } else if (Byte.TYPE.equals(clazz)) {
        	xsdType = "byte"; //$NON-NLS-1$
        } else if (Double.TYPE.equals(clazz)) {
        	xsdType = "double"; //$NON-NLS-1$
        } else if (Float.TYPE.equals(clazz)) {
        	xsdType = "float"; //$NON-NLS-1$
        } else if (Integer.TYPE.equals(clazz)) {
        	xsdType = "int"; //$NON-NLS-1$
        } else if (Long.TYPE.equals(clazz)) {
        	xsdType = "long"; //$NON-NLS-1$
        } else if (Short.TYPE.equals(clazz)) {
        	xsdType = "short"; //$NON-NLS-1$
        } else {
        	//default to string type
        	xsdType = "string"; //$NON-NLS-1$
        }
        
		return getXSDPrimitive(xsdType);
    }

    public static XSDSimpleTypeDefinition getXsdForUmlType(Type umlType) {
		if (umlType instanceof PrimitiveType) {
			PrimitiveType  primitiveType = (PrimitiveType) umlType;
            String  primitiveTypeName = primitiveType.getName();
    		String xsdType;

            if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(primitiveTypeName)) {
            	xsdType = "string"; //$NON-NLS-1$
            } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME.equals(primitiveTypeName)) {
            	xsdType = "integer"; //$NON-NLS-1$
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME.equals(primitiveTypeName)) {
            	xsdType = "double"; //$NON-NLS-1$
            } else if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME.equals(primitiveTypeName)) {
            	xsdType = "boolean"; //$NON-NLS-1$
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME.equals(primitiveTypeName)) {
            	xsdType = "dateTime"; //$NON-NLS-1$
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(primitiveTypeName)) {
            	xsdType = "date"; //$NON-NLS-1$
            } else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(primitiveTypeName)) {
            	xsdType = "time"; //$NON-NLS-1$
            } else if (PrimitivesUtil.BOM_PRIMITIVE_URI_NAME.equals(primitiveTypeName)) {
            	xsdType = "anyURI"; //$NON-NLS-1$
            } else {
            	//default to string type
            	xsdType = "string"; //$NON-NLS-1$
            }
    		return getXSDPrimitive(xsdType);
        }
        
		return null;
    }

    public static boolean isJavaIdentifier(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		
		if (!Character.isJavaIdentifierStart(str.charAt(0))) {
			return false;
		}
		
		for (int i = 1; i < str.length(); i++) {
			if (!Character.isJavaIdentifierPart(str.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}

    public static Class<?> getClassForBasicType(BasicType basicType) {
    	if (Tracing.ENABLED) Tracing.trace("getClassForBasicType: " + ConverterUtil.toString(basicType)); //$NON-NLS-1$
    	
		if (basicType == null) {
			return null;
        }
        
        switch (basicType.getType().getValue()) {
            case BasicTypeType.BOOLEAN:
            	return Boolean.class;
            case BasicTypeType.DATE:
            case BasicTypeType.TIME:
            case BasicTypeType.DATETIME:
            	return XMLGregorianCalendar.class;
            case BasicTypeType.FLOAT:
            	return Double.class;
            case BasicTypeType.INTEGER:
            	return basicType.getPrecision() == null || Integer.valueOf(basicType.getPrecision().getValue()) <= 10 ? 
            			Integer.class : Long.class;
            default:
            	return String.class;
        }
    }

    public static Class<?> getClassForUmlType(Type umlType) {
		if (umlType instanceof PrimitiveType) {
			PrimitiveType  primitiveType = (PrimitiveType) umlType;
            String  primitiveTypeName = primitiveType.getName();
            if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(primitiveTypeName)) {
            	return String.class;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME.equals(primitiveTypeName)) {
            	return Integer.class;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME.equals(primitiveTypeName)) {
            	return Double.class;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME.equals(primitiveTypeName)) {
            	return Boolean.class;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME.equals(primitiveTypeName)) {
            	return XMLGregorianCalendar.class;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME.equals(primitiveTypeName)) {
            	return XMLGregorianCalendar.class;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME.equals(primitiveTypeName)) {
            	return XMLGregorianCalendar.class;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME.equals(primitiveTypeName)) {
            	return XMLGregorianCalendar.class;
            } else if (PrimitivesUtil.BOM_PRIMITIVE_URI_NAME.equals(primitiveTypeName)) {
            	return URI.class;
            } else {
            	//default to string type
            	return String.class;
            }
        }
        
		return null;
    }

	public static String getUmlTypeForBasicType(BasicType basicType) {
		if (basicType == null) {
			return null;
        }
        
        switch (basicType.getType().getValue()) {
            case BasicTypeType.BOOLEAN:
            	return PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
            case BasicTypeType.DATE:
            	return PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
            case BasicTypeType.TIME:
            	return PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;
            case BasicTypeType.DATETIME:
            	return PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME;
            case BasicTypeType.FLOAT:
            	return PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
            case BasicTypeType.INTEGER:
            	return PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
            default:
            	return PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
        }
	}
	
}
