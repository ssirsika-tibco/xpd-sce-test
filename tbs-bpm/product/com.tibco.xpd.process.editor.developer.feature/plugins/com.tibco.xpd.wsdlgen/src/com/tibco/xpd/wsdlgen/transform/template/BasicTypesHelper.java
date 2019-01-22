/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wsdlgen.transform.template;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.xsd.XSDConstrainingFacet;
import org.eclipse.xsd.XSDEnumerationFacet;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDMaxLengthFacet;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTotalDigitsFacet;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * Helper class to build WSDL types for the XPDL to WSDL transformation. This
 * helper class only caters to basic types of XPDL.
 * 
 * @author rsomayaj
 * 
 */
public class BasicTypesHelper {
    /**
     * 
     */
    private static final String UNDERSCORE_DELIMITER = "_"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String BLANK_STRING = ""; //$NON-NLS-1$

    /**
     * 
     */
    private static final String IDREF_TYPE = "IDREF"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String STRING_TYPE = "string"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String INTEGER_TYPE = "integer"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String DECIMAL_TYPE = "decimal"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String TIME_TYPE = "time"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String DATE_TYPE = "date"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String DATETIME_TYPE = "dateTime"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String BOOLEAN_TYPE = "boolean"; //$NON-NLS-1$

    private static final String XSD_NAMESPACE = WSDLConstants.XSD_NAMESPACE_URI;

    public enum BasicTypeEnum {

        STRING(BasicTypeType.STRING) {

            @Override
            public String getFormattedName() {
                if (bt.getLength() != null) {
                    return String.format("%1$s%2$s%3$s", //$NON-NLS-1$
                            convertDataType2WSDL(bt),
                            UNDERSCORE_DELIMITER, //$NON-NLS-1$
                            bt.getLength().getValue());
                } else {
                    return String.format("%1$s", convertDataType2WSDL(bt)); //$NON-NLS-1$
                }

            }

            @Override
            public void addFacetContents(XSDSimpleTypeDefinition typeDef) {
                XSDMaxLengthFacet maxLengthFacet =
                        XSDFactory.eINSTANCE.createXSDMaxLengthFacet();
                if (bt.getLength() != null) {
                    maxLengthFacet.setLexicalValue(bt.getLength().getValue());
                    typeDef.getFacetContents().add(maxLengthFacet);
                }
            }
        },

        INTEGER(BasicTypeType.INTEGER) {
            @Override
            public String getFormattedName() {
                return String.format("%1$s%2$s%3$s", //$NON-NLS-1$
                        convertDataType2WSDL(bt),
                        UNDERSCORE_DELIMITER, //$NON-NLS-1$
                        bt.getPrecision().getValue());
            }

            @Override
            public void addFacetContents(XSDSimpleTypeDefinition typeDef) {
                XSDTotalDigitsFacet intTotalDigitsFacet =
                        XSDFactory.eINSTANCE.createXSDTotalDigitsFacet();
                intTotalDigitsFacet.setLexicalValue(String.format("%1$s", bt //$NON-NLS-1$
                        .getPrecision().getValue()));
                typeDef.getFacetContents().add(intTotalDigitsFacet);
            }
        },

        FLOAT(BasicTypeType.FLOAT) {
            @Override
            public String getFormattedName() {
                return String.format("%1$s%2$s%3$s", //$NON-NLS-1$
                        convertDataType2WSDL(bt),
                        UNDERSCORE_DELIMITER, //$NON-NLS-1$                    
                        UNDERSCORE_DELIMITER);
            }

        },

        BOOLEAN(BasicTypeType.BOOLEAN) {

        },
        PERFORMER(BasicTypeType.PERFORMER) {

        },
        DATE(BasicTypeType.DATE) {

        },
        TIME(BasicTypeType.TIME) {
            public String getFormattedEnumValue(String initVal) {
                if (initVal.matches("\\d\\d:\\d\\d")) { //$NON-NLS-1$

                    String formattedVal = initVal + ":00"; //$NON-NLS-1$
                    return formattedVal;
                }
                return initVal;
            }
        },
        DATETIME(BasicTypeType.DATETIME) {

        };

        private static BasicType bt;

        private BasicTypeEnum(int basicType) {
        }

        public String getFormattedName() {
            return convertDataType2WSDL(bt);
        }

        public static BasicTypeEnum getType(BasicType bt) {
            BasicTypeEnum.bt = bt;
            switch (bt.getType().getValue()) {
            case BasicTypeType.STRING:
                return BasicTypeEnum.STRING;
            case BasicTypeType.INTEGER:
                return BasicTypeEnum.INTEGER;
            case BasicTypeType.FLOAT:
                return BasicTypeEnum.FLOAT;
            case BasicTypeType.DATE:
                return BasicTypeEnum.DATE;
            case BasicTypeType.TIME:
                return BasicTypeEnum.TIME;
            case BasicTypeType.DATETIME:
                return BasicTypeEnum.DATETIME;
            case BasicTypeType.PERFORMER:
                return BasicTypeEnum.PERFORMER;
            case BasicTypeType.BOOLEAN:
                return BasicTypeEnum.BOOLEAN;
            default:
                return null;
            }
        }

        /**
         * @param initVal
         * @return
         */
        public String getFormattedEnumValue(String initVal) {
            return initVal;
        }

        /**
         * @param typeDef
         */
        public void addFacetContents(XSDSimpleTypeDefinition typeDef) {
            // Do nothing by default
        }
    }

    /**
     * Creates an XSD type definition in the given schema with the namespace and
     * basicType name and specifics(STRING- Length, Float - Precision)
     * 
     * @param schema
     * @param paramContainerName
     * @param basicType
     * @param targetNamespace
     * @param isArray
     * @param initialValues
     * @return {@link XSDTypeDefinition}
     */
    public static XSDTypeDefinition createNewTypeDefinition(XSDSchema schema,
            String paramContainerName, String paramName, BasicType basicType,
            String targetNamespace, boolean isArray, InitialValues initialValues) {
        XSDSimpleTypeDefinition typeDef =
                createSimpleTypeDefinition(basicType, targetNamespace);

        BasicTypeEnum.getType(basicType).addFacetContents(typeDef);

        typeDef.setName(BasicTypeEnum.getType(basicType).getFormattedName());
        addTypeDefToSchema(schema, typeDef);

        if (null != initialValues
                && !(BasicTypeType.BOOLEAN_LITERAL.equals(basicType.getType()))) {
            // According to the XSD spec, BOOLEAN type definitions cannot have
            // enumerations.
            // Create enumeration for the values specified as initial values.
            String newTypeDefName =
                    typeDef.getName() + UNDERSCORE_DELIMITER
                            + paramContainerName + UNDERSCORE_DELIMITER
                            + paramName;
            typeDef.setName(newTypeDefName);
            List<String> initialValueList = initialValues.getValue();
            for (String initVal : initialValueList) {
                XSDEnumerationFacet enumerationFacet =
                        XSDFactory.eINSTANCE.createXSDEnumerationFacet();
                enumerationFacet.setLexicalValue(BasicTypeEnum
                        .getType(basicType).getFormattedEnumValue(initVal));
                typeDef.getFacetContents().add(enumerationFacet);
            }
        }

        return typeDef;
    }

    protected static void addTypeDefToSchema(XSDSchema schema,
            XSDSimpleTypeDefinition typeDef) {
        if (schema != null) {
            schema.getContents().add(typeDef);
        }
    }

    protected static XSDSimpleTypeDefinition createSimpleTypeDefinition(
            BasicType basicType, String targetNamespace) {
        XSDSimpleTypeDefinition typeDef =
                XSDFactory.eINSTANCE.createXSDSimpleTypeDefinition();
        XSDSimpleTypeDefinition baseDef =
                XSDFactory.eINSTANCE.createXSDSimpleTypeDefinition();
        typeDef.setTargetNamespace(targetNamespace);
        baseDef.setName(convertDataType2WSDL(basicType));
        baseDef.setTargetNamespace(XSD_NAMESPACE);
        typeDef.setBaseTypeDefinition(baseDef);
        return typeDef;
    }

    /**
     * @param schema
     * @param string
     * @param paramsContainerName
     * @param basicType
     * @param initialValues
     * @param b
     * @param containsInitialValues
     * @return
     */
    protected static XSDSimpleTypeDefinition checkExistingDataTypes(
            XSDSchema schema, String paramsContainerName, String paramName,
            BasicType basicType, boolean isArray, InitialValues initialValues) {

        String typeName = BasicTypeEnum.getType(basicType).getFormattedName();
        EObject typeObj =
                EMFSearchUtil.findInList(schema.getTypeDefinitions(),
                        XSDPackage.eINSTANCE.getXSDNamedComponent_Name(),
                        typeName);
        if (typeObj instanceof XSDSimpleTypeDefinition) {
            return (XSDSimpleTypeDefinition) typeObj;
        }
        return null;
    }

    /**
     * convert dataType to wsdl type the result is simple type name,like:
     * <code>string</code> <code>boolean</code> .................... if you use
     * the result as a type of element you should set the prefix namespace,such
     * as: <code>*.setQname(new Qname(XSD,result));</code>
     * 
     * @param dataType
     * @return
     */
    protected static String convertDataType2WSDL(DataType dataType) {
        String result = BLANK_STRING;
        if (dataType != null) {
            if (dataType instanceof BasicType) {
                BasicTypeType basicTypeType = ((BasicType) dataType).getType();
                if (basicTypeType != null) {
                    int basicTypeTypeValue = basicTypeType.getValue();
                    // These String constants should not be translated
                    switch (basicTypeTypeValue) {
                    case BasicTypeType.BOOLEAN:
                        result = BOOLEAN_TYPE;
                        break;
                    case BasicTypeType.DATETIME:
                        result = DATETIME_TYPE;
                        break;
                    case BasicTypeType.DATE:
                        result = DATE_TYPE;
                        break;
                    case BasicTypeType.TIME:
                        result = TIME_TYPE;
                        break;
                    case BasicTypeType.FLOAT:
                        result = DECIMAL_TYPE;
                        break;
                    case BasicTypeType.INTEGER:
                        result = INTEGER_TYPE;
                        break;
                    case BasicTypeType.PERFORMER:
                        result = STRING_TYPE;
                        break;

                    case BasicTypeType.REFERENCE:
                        // throw new
                        // UnsupportedConversionException(ConverterActivator.
                        // createWarningStatus(
                        // "Basic type of 'Reference' cannot be converted to
                        // BPEL", null));
                        // TODO this type may be changed
                        result = IDREF_TYPE;
                        break;
                    case BasicTypeType.STRING:
                        result = STRING_TYPE;
                        break;
                    }// endswitch
                }
            } else {
                result = dataType.toString();
            }
        }
        return result;
    }

    /**
     * @param typeDef
     * @param basicType
     * @param initialValues
     */
    protected static void updateTypeDefWithEnumValues(
            XSDTypeDefinition typeDef, BasicType basicType,
            InitialValues initialValues) {
        if (typeDef instanceof XSDSimpleTypeDefinition) {
            XSDSimpleTypeDefinition simpleTypeDef =
                    (XSDSimpleTypeDefinition) typeDef;

            // Clear the enumeration contents.
            List<XSDEnumerationFacet> enumerationFacets =
                    new ArrayList<XSDEnumerationFacet>();

            for (XSDConstrainingFacet facet : simpleTypeDef.getFacetContents()) {
                if (facet instanceof XSDEnumerationFacet) {
                    enumerationFacets.add((XSDEnumerationFacet) facet);
                }
            }
            simpleTypeDef.getFacetContents().removeAll(enumerationFacets);

            // update them everytime -
            for (String initVal : initialValues.getValue()) {
                XSDEnumerationFacet enumerationFacet =
                        XSDFactory.eINSTANCE.createXSDEnumerationFacet();
                enumerationFacet.setLexicalValue(BasicTypeEnum
                        .getType(basicType).getFormattedEnumValue(initVal));
                simpleTypeDef.getFacetContents().add(enumerationFacet);
            }
        }
    }

}
