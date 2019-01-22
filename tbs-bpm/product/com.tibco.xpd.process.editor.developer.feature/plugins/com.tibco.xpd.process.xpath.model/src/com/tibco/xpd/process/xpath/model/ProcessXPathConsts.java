package com.tibco.xpd.process.xpath.model;

import java.util.HashSet;
import java.util.Set;

import com.tibco.xpd.xpdl2.BasicTypeType;

public class ProcessXPathConsts {

    public static final String XPATH_IMG_CLASS = "icons/full/obj16/xpath.gif"; //$NON-NLS-1$

    public static final String[] IMAGES = new String[] { XPATH_IMG_CLASS };

    public static final String XPATH_GRAMMAR = "XPath"; //$NON-NLS-1$

    public static final String XPATH_DESTINATION = "xPath1.x"; //$NON-NLS-1$

    private static Set<String> validObjectParamTypes = new HashSet<String>();

    private static Set<String> validNodeSetParamTypes = new HashSet<String>();

    private static Set<String> validStringParamTypes = new HashSet<String>();

    private static Set<String> validNumberParamTypes = new HashSet<String>();

    private static Set<String> validBooleanParamTypes = new HashSet<String>();

    static {
        validObjectParamTypes.add(BasicTypeType.BOOLEAN_LITERAL.getName()
                .toUpperCase());
        validObjectParamTypes.add(BasicTypeType.STRING_LITERAL.getName()
                .toUpperCase());
        validObjectParamTypes.add(BasicTypeType.FLOAT_LITERAL.getName()
                .toUpperCase());
        validObjectParamTypes.add(BasicTypeType.INTEGER_LITERAL.getName()
                .toUpperCase());
        validObjectParamTypes.add(BasicTypeType.PERFORMER_LITERAL.getName()
                .toUpperCase());
        validObjectParamTypes.add(XPathConsts.TEXT.toUpperCase());
        validObjectParamTypes.add(XPathConsts.INT.toUpperCase());
        validObjectParamTypes.add(XPathConsts.DECIMAL.toUpperCase());
        validObjectParamTypes.add(XPathConsts.INTEGER.toUpperCase());
        validObjectParamTypes.add(XPathConsts.DOUBLE.toUpperCase());
        validObjectParamTypes.add(XPathConsts.XPATH_TYPE_NUMBER.toUpperCase());

        validObjectParamTypes.add(XPathConsts.QNAME.toUpperCase());
        validObjectParamTypes.add(XPathConsts.NOTATION.toUpperCase());
        validObjectParamTypes.add(XPathConsts.NORMALIZEDSTRING.toUpperCase());
        validObjectParamTypes.add(XPathConsts.TOKEN.toUpperCase());
        validObjectParamTypes.add(XPathConsts.LANGUAGE.toUpperCase());
        validObjectParamTypes.add(XPathConsts.IDREFS.toUpperCase());
        validObjectParamTypes.add(XPathConsts.ENTITIES.toUpperCase());
        validObjectParamTypes.add(XPathConsts.NMTOKEN.toUpperCase());
        validObjectParamTypes.add(XPathConsts.NMTOKENS.toUpperCase());
        validObjectParamTypes.add(XPathConsts.NAME.toUpperCase());
        validObjectParamTypes.add(XPathConsts.NCNAME.toUpperCase());
        validObjectParamTypes.add(XPathConsts.ID.toUpperCase());
        validObjectParamTypes.add(XPathConsts.IDREF.toUpperCase());
        validObjectParamTypes.add(XPathConsts.ENTITY.toUpperCase());

        validObjectParamTypes.add(XPathConsts.NONPOSITIVEINTEGER.toUpperCase());
        validObjectParamTypes.add(XPathConsts.NEGATIVEINTEGER.toUpperCase());
        validObjectParamTypes.add(XPathConsts.LONG.toUpperCase());
        validObjectParamTypes.add(XPathConsts.SHORT.toUpperCase());
        validObjectParamTypes.add(XPathConsts.BYTE.toUpperCase());
        validObjectParamTypes.add(XPathConsts.NONNEGATIVEINTEGER.toUpperCase());
        validObjectParamTypes.add(XPathConsts.UNSIGNEDLONG.toUpperCase());
        validObjectParamTypes.add(XPathConsts.UNSIGNEDINT.toUpperCase());
        validObjectParamTypes.add(XPathConsts.UNSIGNEDSHORT.toUpperCase());
        validObjectParamTypes.add(XPathConsts.UNSIGNEDBYTE.toUpperCase());
        validObjectParamTypes.add(XPathConsts.POSITIVEINTEGER.toUpperCase());
        validObjectParamTypes.add(XPathConsts.PRECISIONDECIMAL.toUpperCase());

        validNodeSetParamTypes
                .add(XPathConsts.XPATH_TYPE_NODESET.toUpperCase());
        validNodeSetParamTypes.add(BasicTypeType.BOOLEAN_LITERAL.getName()
                .toUpperCase());
        validNodeSetParamTypes.add(BasicTypeType.STRING_LITERAL.getName()
                .toUpperCase());
        validNodeSetParamTypes.add(BasicTypeType.FLOAT_LITERAL.getName()
                .toUpperCase());
        validNodeSetParamTypes.add(BasicTypeType.INTEGER_LITERAL.getName()
                .toUpperCase());
        validNodeSetParamTypes.add(BasicTypeType.PERFORMER_LITERAL.getName()
                .toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.TEXT.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.INT.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.DECIMAL.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.DATE.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.DATETIME.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.HEXBINARY.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.TIME.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.INTEGER.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.DOUBLE.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.XPATH_TYPE_NUMBER.toUpperCase());

        validNodeSetParamTypes.add(XPathConsts.QNAME.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.NOTATION.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.NORMALIZEDSTRING.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.TOKEN.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.LANGUAGE.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.IDREFS.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.ENTITIES.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.NMTOKEN.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.NMTOKENS.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.NAME.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.NCNAME.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.ID.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.IDREF.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.ENTITY.toUpperCase());

        validNodeSetParamTypes
                .add(XPathConsts.NONPOSITIVEINTEGER.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.NEGATIVEINTEGER.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.LONG.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.SHORT.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.BYTE.toUpperCase());
        validNodeSetParamTypes
                .add(XPathConsts.NONNEGATIVEINTEGER.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.UNSIGNEDLONG.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.UNSIGNEDINT.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.UNSIGNEDSHORT.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.UNSIGNEDBYTE.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.POSITIVEINTEGER.toUpperCase());
        validNodeSetParamTypes.add(XPathConsts.PRECISIONDECIMAL.toUpperCase());

        validStringParamTypes.add(BasicTypeType.BOOLEAN_LITERAL.getName()
                .toUpperCase());
        validStringParamTypes.add(BasicTypeType.STRING_LITERAL.getName()
                .toUpperCase());
        validStringParamTypes.add(BasicTypeType.FLOAT_LITERAL.getName()
                .toUpperCase());
        validStringParamTypes.add(BasicTypeType.INTEGER_LITERAL.getName()
                .toUpperCase());
        validStringParamTypes.add(BasicTypeType.PERFORMER_LITERAL.getName()
                .toUpperCase());
        validStringParamTypes.add(XPathConsts.TEXT.toUpperCase());
        validStringParamTypes.add(XPathConsts.INT.toUpperCase());
        validStringParamTypes.add(XPathConsts.DECIMAL.toUpperCase());
        validStringParamTypes.add(XPathConsts.DOUBLE.toUpperCase());
        validStringParamTypes.add(XPathConsts.INTEGER.toUpperCase());
        validStringParamTypes.add(XPathConsts.XPATH_TYPE_NUMBER.toUpperCase());

        validStringParamTypes.add(XPathConsts.QNAME.toUpperCase());
        validStringParamTypes.add(XPathConsts.NOTATION.toUpperCase());
        validStringParamTypes.add(XPathConsts.NORMALIZEDSTRING.toUpperCase());
        validStringParamTypes.add(XPathConsts.TOKEN.toUpperCase());
        validStringParamTypes.add(XPathConsts.LANGUAGE.toUpperCase());
        validStringParamTypes.add(XPathConsts.IDREFS.toUpperCase());
        validStringParamTypes.add(XPathConsts.ENTITIES.toUpperCase());
        validStringParamTypes.add(XPathConsts.NMTOKEN.toUpperCase());
        validStringParamTypes.add(XPathConsts.NMTOKENS.toUpperCase());
        validStringParamTypes.add(XPathConsts.NAME.toUpperCase());
        validStringParamTypes.add(XPathConsts.NCNAME.toUpperCase());
        validStringParamTypes.add(XPathConsts.ID.toUpperCase());
        validStringParamTypes.add(XPathConsts.IDREF.toUpperCase());
        validStringParamTypes.add(XPathConsts.ENTITY.toUpperCase());

        validBooleanParamTypes
                .add(XPathConsts.XPATH_TYPE_BOOLEAN.toUpperCase());

        validNumberParamTypes.add(BasicTypeType.BOOLEAN_LITERAL.getName()
                .toUpperCase());
        validNumberParamTypes.add(BasicTypeType.STRING_LITERAL.getName()
                .toUpperCase());
        validNumberParamTypes.add(BasicTypeType.FLOAT_LITERAL.getName()
                .toUpperCase());
        validNumberParamTypes.add(BasicTypeType.INTEGER_LITERAL.getName()
                .toUpperCase());
        validNumberParamTypes.add(BasicTypeType.PERFORMER_LITERAL.getName()
                .toUpperCase());
        validNumberParamTypes.add(XPathConsts.TEXT.toUpperCase());
        validNumberParamTypes.add(XPathConsts.INT.toUpperCase());
        validNumberParamTypes.add(XPathConsts.DECIMAL.toUpperCase());
        validNumberParamTypes.add(XPathConsts.DOUBLE.toUpperCase());
        validNumberParamTypes.add(XPathConsts.INTEGER.toUpperCase());
        validNumberParamTypes.add(XPathConsts.XPATH_TYPE_NUMBER.toUpperCase());

        validNumberParamTypes.add(XPathConsts.NONPOSITIVEINTEGER.toUpperCase());
        validNumberParamTypes.add(XPathConsts.NEGATIVEINTEGER.toUpperCase());
        validNumberParamTypes.add(XPathConsts.LONG.toUpperCase());
        validNumberParamTypes.add(XPathConsts.SHORT.toUpperCase());
        validNumberParamTypes.add(XPathConsts.BYTE.toUpperCase());
        validNumberParamTypes.add(XPathConsts.NONNEGATIVEINTEGER.toUpperCase());
        validNumberParamTypes.add(XPathConsts.UNSIGNEDLONG.toUpperCase());
        validNumberParamTypes.add(XPathConsts.UNSIGNEDINT.toUpperCase());
        validNumberParamTypes.add(XPathConsts.UNSIGNEDSHORT.toUpperCase());
        validNumberParamTypes.add(XPathConsts.UNSIGNEDBYTE.toUpperCase());
        validNumberParamTypes.add(XPathConsts.POSITIVEINTEGER.toUpperCase());
        validNumberParamTypes.add(XPathConsts.PRECISIONDECIMAL.toUpperCase());

    }

    public static boolean isValidParameterType(String expectedParameterType,
            String passedParameterType) {
        boolean isValid = false;
        Set<String> validParamTypes = new HashSet<String>();
        if (expectedParameterType != null) {
            if (expectedParameterType.equals(XPathConsts.XPATH_TYPE_OBJECT)) {
                validParamTypes = validObjectParamTypes;
            } else if (expectedParameterType
                    .equals(XPathConsts.XPATH_TYPE_NODESET)) {
                validParamTypes = validNodeSetParamTypes;
            } else if (expectedParameterType
                    .equals(XPathConsts.XPATH_TYPE_STRING)) {
                validParamTypes = validStringParamTypes;
            } else if (expectedParameterType
                    .equals(XPathConsts.XPATH_TYPE_NUMBER)) {
                validParamTypes = validNumberParamTypes;
            } else if (expectedParameterType
                    .equals(XPathConsts.XPATH_TYPE_BOOLEAN)) {
                validParamTypes = validBooleanParamTypes;
            }
            if (validParamTypes.contains(passedParameterType.toUpperCase()) || expectedParameterType
                    .equals(XPathConsts.XPATH_TYPE_NODESET)) {
                isValid = true;
            }
        }
        return isValid;
    }

}
