/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.process.xpath.model;

import java.util.HashSet;
import java.util.Set;

import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * @author mtorres
 * 
 */
public abstract class AbstractXPathMappingTypeResolver {

    private Set<String> integerProcessTypes = null;

    private Set<String> decimalProcessTypes = null;

    private Set<String> dateTimeProcessTypes = null;

    private Set<String> dateProcessTypes = null;

    private Set<String> timeProcessTypes = null;

    private Set<String> stringProcessTypes = null;

    // Process to Ws mapping types ============================================

    private Set<String> validIntegerProcessToWSMappingTypes = null;

    private Set<String> validDecimalProcessToWSMappingTypes = null;

    private Set<String> validStringProcessToWSMappingTypes = null;

    private Set<String> validDateTimeProcessToWSMappingTypes = null;

    private Set<String> validDateProcessToWSMappingTypes = null;

    private Set<String> validTimeProcessToWSMappingTypes = null;

    private Set<String> getIntegerProcessTypes() {
        if (integerProcessTypes == null) {
            integerProcessTypes = new HashSet<String>();
            integerProcessTypes.add(BasicTypeType.BOOLEAN_LITERAL.getName()
                    .toUpperCase());
            integerProcessTypes.add(BasicTypeType.INTEGER_LITERAL.getName()
                    .toUpperCase());
            integerProcessTypes.add(XPathConsts.INT.toUpperCase());
        }
        return integerProcessTypes;
    }

    protected Set<String> getDecimalProcessTypes() {
        if (decimalProcessTypes == null) {
            decimalProcessTypes = new HashSet<String>();
            decimalProcessTypes.add(BasicTypeType.FLOAT_LITERAL.getName()
                    .toUpperCase());
            decimalProcessTypes.add(XPathConsts.DOUBLE.toUpperCase());
            decimalProcessTypes.add(XPathConsts.DECIMAL.toUpperCase());
            decimalProcessTypes
                    .add(XPathConsts.XPATH_TYPE_NUMBER.toUpperCase());
        }
        return decimalProcessTypes;
    }

    protected Set<String> getDateTimeProcessTypes() {
        if (dateTimeProcessTypes == null) {
            dateTimeProcessTypes = new HashSet<String>();
            dateTimeProcessTypes.add(BasicTypeType.DATETIME_LITERAL.getName()
                    .toUpperCase());
        }
        return dateTimeProcessTypes;
    }

    protected Set<String> getDateProcessTypes() {
        if (dateProcessTypes == null) {
            dateProcessTypes = new HashSet<String>();
            dateProcessTypes.add(XPathConsts.DATE.toUpperCase());
        }
        return dateProcessTypes;
    }

    protected Set<String> getTimeProcessTypes() {
        if (timeProcessTypes == null) {
            timeProcessTypes = new HashSet<String>();
            timeProcessTypes.add(XPathConsts.TIME.toUpperCase());
        }
        return timeProcessTypes;
    }

    protected Set<String> getStringProcessTypes() {
        if (stringProcessTypes == null) {
            stringProcessTypes = new HashSet<String>();
            stringProcessTypes.add(BasicTypeType.STRING_LITERAL.getName()
                    .toUpperCase());
            stringProcessTypes.add(BasicTypeType.PERFORMER_LITERAL.getName()
                    .toUpperCase());
            stringProcessTypes.add(XPathConsts.TEXT.toUpperCase());
        }
        return stringProcessTypes;
    }

    protected Set<String> getValidIntegerProcessToWSMappingTypes() {
        if (validIntegerProcessToWSMappingTypes == null) {
            validIntegerProcessToWSMappingTypes = new HashSet<String>();
            validIntegerProcessToWSMappingTypes.add(XPathConsts.INT
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.FLOAT
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.DOUBLE
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.TEXT
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes
                    .add(XPathConsts.XPATH_TYPE_STRING.toUpperCase());
            validIntegerProcessToWSMappingTypes
                    .add(XPathConsts.XPATH_TYPE_BOOLEAN.toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.ANYURL
                    .toUpperCase());
            // validIntegerProcessToWSMappingTypes.add(XPathConsts.ANYSIMPLETYPE.toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.BYTE
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.SHORT
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.INTEGER
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.DECIMAL
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.ID
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.GDAY
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.DURATION
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.GMONTH
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.GMONTHDAY
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.GYEAR
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.GYEARMONTH
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.QNAME
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.TOKEN
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.NMTOKEN
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.IDREF
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.ENTITY
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.LANGUAGE
                    .toUpperCase());

            validIntegerProcessToWSMappingTypes.add(XPathConsts.NOTATION
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes
                    .add(XPathConsts.NORMALIZEDSTRING.toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.IDREFS
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.ENTITIES
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.NMTOKENS
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.NAME
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.NCNAME
                    .toUpperCase());

            validIntegerProcessToWSMappingTypes
                    .add(XPathConsts.NONPOSITIVEINTEGER.toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.NEGATIVEINTEGER
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.LONG
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes
                    .add(XPathConsts.NONNEGATIVEINTEGER.toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDINT
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDSHORT
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDBYTE
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDLONG
                    .toUpperCase());
            validIntegerProcessToWSMappingTypes.add(XPathConsts.POSITIVEINTEGER
                    .toUpperCase());

            validIntegerProcessToWSMappingTypes
                    .add(XPathConsts.PRECISIONDECIMAL.toUpperCase());

            validIntegerProcessToWSMappingTypes
                    .add(XPathConsts.YEARMONTHDURATION.toUpperCase());
            validIntegerProcessToWSMappingTypes
                    .add(XPathConsts.DATETIMEDURATION.toUpperCase());

        }
        return validIntegerProcessToWSMappingTypes;
    }

    protected Set<String> getValidDecimalProcessToWSMappingTypes() {
        if (validDecimalProcessToWSMappingTypes == null) {
            validDecimalProcessToWSMappingTypes = new HashSet<String>();
            validDecimalProcessToWSMappingTypes.add(XPathConsts.FLOAT
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.DOUBLE
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.TEXT
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes
                    .add(XPathConsts.XPATH_TYPE_STRING.toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.ANYURL
                    .toUpperCase());
            // validDecimalProcessToWSMappingTypes.add(XPathConsts.ANYSIMPLETYPE.toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.DECIMAL
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.ID
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.GDAY
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.DURATION
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.GMONTH
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.GMONTHDAY
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.GYEAR
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.GYEARMONTH
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.QNAME
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.TOKEN
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.NMTOKEN
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.IDREF
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.ENTITY
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.LANGUAGE
                    .toUpperCase());

            validDecimalProcessToWSMappingTypes.add(XPathConsts.NOTATION
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes
                    .add(XPathConsts.NORMALIZEDSTRING.toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.IDREFS
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.ENTITIES
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.NMTOKENS
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.NAME
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes.add(XPathConsts.NCNAME
                    .toUpperCase());
            validDecimalProcessToWSMappingTypes
                    .add(XPathConsts.PRECISIONDECIMAL.toUpperCase());

            validDecimalProcessToWSMappingTypes
                    .add(XPathConsts.YEARMONTHDURATION.toUpperCase());
            validDecimalProcessToWSMappingTypes
                    .add(XPathConsts.DATETIMEDURATION.toUpperCase());

        }
        return validDecimalProcessToWSMappingTypes;
    }

    protected Set<String> getValidStringProcessToWSMappingTypes() {
        if (validStringProcessToWSMappingTypes == null) {
            validStringProcessToWSMappingTypes = new HashSet<String>();
            validStringProcessToWSMappingTypes.add(XPathConsts.INT
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.FLOAT
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.DOUBLE
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.DATE
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.TIME
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.DATETIME
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.TEXT
                    .toUpperCase());
            validStringProcessToWSMappingTypes
                    .add(XPathConsts.XPATH_TYPE_STRING.toUpperCase());
            validStringProcessToWSMappingTypes
                    .add(XPathConsts.XPATH_TYPE_BOOLEAN.toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.HEXBINARY
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.ANYURL
                    .toUpperCase());
            // validStringProcessToWSMappingTypes.add(XPathConsts.ANYSIMPLETYPE.toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.BYTE
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.SHORT
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.INTEGER
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.DECIMAL
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.BASE64BINARY
                    .toUpperCase());
            validStringProcessToWSMappingTypes
                    .add(XPathConsts.ID.toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.GDAY
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.DURATION
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.GMONTH
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.GMONTHDAY
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.GYEAR
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.GYEARMONTH
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.QNAME
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.TOKEN
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.NMTOKEN
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.IDREF
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.ENTITY
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.LANGUAGE
                    .toUpperCase());

            validStringProcessToWSMappingTypes.add(XPathConsts.NOTATION
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.NORMALIZEDSTRING
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.IDREFS
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.ENTITIES
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.NMTOKENS
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.NAME
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.NCNAME
                    .toUpperCase());

            validStringProcessToWSMappingTypes
                    .add(XPathConsts.NONPOSITIVEINTEGER.toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.NEGATIVEINTEGER
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.LONG
                    .toUpperCase());
            validStringProcessToWSMappingTypes
                    .add(XPathConsts.NONNEGATIVEINTEGER.toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDINT
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDSHORT
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDBYTE
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDLONG
                    .toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.POSITIVEINTEGER
                    .toUpperCase());

            validStringProcessToWSMappingTypes.add(XPathConsts.PRECISIONDECIMAL
                    .toUpperCase());

            validStringProcessToWSMappingTypes
                    .add(XPathConsts.YEARMONTHDURATION.toUpperCase());
            validStringProcessToWSMappingTypes.add(XPathConsts.DATETIMEDURATION
                    .toUpperCase());
        }
        return validStringProcessToWSMappingTypes;
    }

    protected Set<String> getValidDateTimeProcessToWSMappingTypes() {
        if (validDateTimeProcessToWSMappingTypes == null) {
            validDateTimeProcessToWSMappingTypes = new HashSet<String>();
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.DATE
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.TIME
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.DATETIME
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.TEXT
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes
                    .add(XPathConsts.XPATH_TYPE_STRING.toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.ANYURL
                    .toUpperCase());
            // validDateTimeProcessToWSMappingTypes.add(XPathConsts.ANYSIMPLETYPE.toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.BYTE
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.SHORT
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.INTEGER
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.DECIMAL
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.ID
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.GDAY
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.DURATION
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.GMONTH
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.GMONTHDAY
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.GYEAR
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.GYEARMONTH
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.QNAME
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.TOKEN
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.NMTOKEN
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.IDREF
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.ENTITY
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.LANGUAGE
                    .toUpperCase());

            validDateTimeProcessToWSMappingTypes.add(XPathConsts.NOTATION
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes
                    .add(XPathConsts.NORMALIZEDSTRING.toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.IDREFS
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.ENTITIES
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.NMTOKENS
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.NAME
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.NCNAME
                    .toUpperCase());

            validDateTimeProcessToWSMappingTypes
                    .add(XPathConsts.NONPOSITIVEINTEGER.toUpperCase());
            validDateTimeProcessToWSMappingTypes
                    .add(XPathConsts.NEGATIVEINTEGER.toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.LONG
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes
                    .add(XPathConsts.NONNEGATIVEINTEGER.toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDINT
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDSHORT
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDBYTE
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDLONG
                    .toUpperCase());
            validDateTimeProcessToWSMappingTypes
                    .add(XPathConsts.POSITIVEINTEGER.toUpperCase());

            validDateTimeProcessToWSMappingTypes
                    .add(XPathConsts.YEARMONTHDURATION.toUpperCase());
            validDateTimeProcessToWSMappingTypes
                    .add(XPathConsts.DATETIMEDURATION.toUpperCase());

        }
        return validDateTimeProcessToWSMappingTypes;
    }

    protected Set<String> getValidDateProcessToWSMappingTypes() {
        if (validDateProcessToWSMappingTypes == null) {
            validDateProcessToWSMappingTypes = new HashSet<String>();
            validDateProcessToWSMappingTypes
                    .add(XPathConsts.DATE.toUpperCase());
            // validDateProcessToWSMappingTypes.add(XPathConsts.TIME.toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.DATETIME
                    .toUpperCase());
            validDateProcessToWSMappingTypes
                    .add(XPathConsts.TEXT.toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.XPATH_TYPE_STRING
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.ANYURL
                    .toUpperCase());
            // validDateProcessToWSMappingTypes.add(XPathConsts.ANYSIMPLETYPE.toUpperCase());
            validDateProcessToWSMappingTypes
                    .add(XPathConsts.BYTE.toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.SHORT
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.INTEGER
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.DECIMAL
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.ID.toUpperCase());
            validDateProcessToWSMappingTypes
                    .add(XPathConsts.GDAY.toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.DURATION
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.GMONTH
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.GMONTHDAY
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.GYEAR
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.GYEARMONTH
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.QNAME
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.TOKEN
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.NMTOKEN
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.IDREF
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.ENTITY
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.LANGUAGE
                    .toUpperCase());

            validDateProcessToWSMappingTypes.add(XPathConsts.NOTATION
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.NORMALIZEDSTRING
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.IDREFS
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.ENTITIES
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.NMTOKENS
                    .toUpperCase());
            validDateProcessToWSMappingTypes
                    .add(XPathConsts.NAME.toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.NCNAME
                    .toUpperCase());

            validDateProcessToWSMappingTypes.add(XPathConsts.NONPOSITIVEINTEGER
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.NEGATIVEINTEGER
                    .toUpperCase());
            validDateProcessToWSMappingTypes
                    .add(XPathConsts.LONG.toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.NONNEGATIVEINTEGER
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDINT
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDSHORT
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDBYTE
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDLONG
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.POSITIVEINTEGER
                    .toUpperCase());

            validDateProcessToWSMappingTypes.add(XPathConsts.YEARMONTHDURATION
                    .toUpperCase());
            validDateProcessToWSMappingTypes.add(XPathConsts.DATETIMEDURATION
                    .toUpperCase());
        }
        return validDateProcessToWSMappingTypes;
    }

    protected Set<String> getValidTimeProcessToWSMappingTypes() {
        if (validTimeProcessToWSMappingTypes == null) {
            validTimeProcessToWSMappingTypes = new HashSet<String>();
            // validTimeProcessToWSMappingTypes.add(XPathConsts.DATE.toUpperCase());
            validTimeProcessToWSMappingTypes
                    .add(XPathConsts.TIME.toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.DATETIME
                    .toUpperCase());
            validTimeProcessToWSMappingTypes
                    .add(XPathConsts.TEXT.toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.XPATH_TYPE_STRING
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.ANYURL
                    .toUpperCase());
            // validTimeProcessToWSMappingTypes.add(XPathConsts.ANYSIMPLETYPE.toUpperCase());
            validTimeProcessToWSMappingTypes
                    .add(XPathConsts.BYTE.toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.SHORT
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.INTEGER
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.DECIMAL
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.ID.toUpperCase());
            validTimeProcessToWSMappingTypes
                    .add(XPathConsts.GDAY.toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.DURATION
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.GMONTH
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.GMONTHDAY
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.GYEAR
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.GYEARMONTH
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.QNAME
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.TOKEN
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.NMTOKEN
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.IDREF
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.ENTITY
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.LANGUAGE
                    .toUpperCase());

            validTimeProcessToWSMappingTypes.add(XPathConsts.NOTATION
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.NORMALIZEDSTRING
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.IDREFS
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.ENTITIES
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.NMTOKENS
                    .toUpperCase());
            validTimeProcessToWSMappingTypes
                    .add(XPathConsts.NAME.toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.NCNAME
                    .toUpperCase());

            validTimeProcessToWSMappingTypes.add(XPathConsts.NONPOSITIVEINTEGER
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.NEGATIVEINTEGER
                    .toUpperCase());
            validTimeProcessToWSMappingTypes
                    .add(XPathConsts.LONG.toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.NONNEGATIVEINTEGER
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDINT
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDSHORT
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDBYTE
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.UNSIGNEDLONG
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.POSITIVEINTEGER
                    .toUpperCase());

            validTimeProcessToWSMappingTypes.add(XPathConsts.YEARMONTHDURATION
                    .toUpperCase());
            validTimeProcessToWSMappingTypes.add(XPathConsts.DATETIMEDURATION
                    .toUpperCase());
        }
        return validTimeProcessToWSMappingTypes;
    }

    protected Set<String> getValidIntWsToProcessMappingTypes() {
        Set<String> validIntWSToProcesssMappingTypes = new HashSet<String>();
        validIntWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validIntWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validIntWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validIntWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validIntWSToProcesssMappingTypes;
    }

    protected Set<String> getValidFloatWsToProcessMappingTypes() {
        Set<String> validFloatWSToProcesssMappingTypes = new HashSet<String>();
        validFloatWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validFloatWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validFloatWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validFloatWSToProcesssMappingTypes;
    }

    protected Set<String> getValidPrecisionDecimalWsToProcessMappingTypes() {
        Set<String> validPrecisionDecimalWSToProcesssMappingTypes =
                new HashSet<String>();
        validPrecisionDecimalWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validPrecisionDecimalWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validPrecisionDecimalWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validPrecisionDecimalWSToProcesssMappingTypes;
    }

    protected Set<String> getValidDoubleWsToProcessMappingTypes() {
        Set<String> validDoubleWSToProcesssMappingTypes = new HashSet<String>();
        validDoubleWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validDoubleWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validDoubleWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validDoubleWSToProcesssMappingTypes;
    }

    protected Set<String> getValidDateWsToProcessMappingTypes() {
        Set<String> validDateWSToProcesssMappingTypes = new HashSet<String>();
        validDateWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        // validDateWSToProcesssMappingTypes.addAll(getDateTimeProcessTypes());
        validDateWSToProcesssMappingTypes.addAll(getDateProcessTypes());
        // validDateWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validDateWSToProcesssMappingTypes;
    }

    protected Set<String> getValidTimeWsToProcessMappingTypes() {
        Set<String> validTimeWSToProcesssMappingTypes = new HashSet<String>();
        validTimeWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        // validTimeWSToProcesssMappingTypes.addAll(getDateTimeProcessTypes());
        validTimeWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validTimeWSToProcesssMappingTypes;
    }

    protected Set<String> getValidDateTimeWsToProcessMappingTypes() {
        Set<String> validDateTimeWSToProcesssMappingTypes =
                new HashSet<String>();
        validDateTimeWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        // validDateTimeWSToProcesssMappingTypes.addAll(getDateTimeProcessTypes());
        validDateTimeWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validDateTimeWSToProcesssMappingTypes;
    }

    protected Set<String> getValidStringWsToProcessMappingTypes() {
        Set<String> validStringWSToProcesssMappingTypes = new HashSet<String>();
        validStringWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validStringWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validStringWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        // validStringWSToProcesssMappingTypes.addAll(getDateTimeProcessTypes());
        validStringWSToProcesssMappingTypes.addAll(getDateProcessTypes());
        validStringWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validStringWSToProcesssMappingTypes;
    }

    protected Set<String> getValidBooleanWsToProcessMappingTypes() {
        Set<String> validBooleanWSToProcesssMappingTypes =
                new HashSet<String>();
        validBooleanWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validBooleanWSToProcesssMappingTypes.addAll(getDateProcessTypes());
        validBooleanWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validBooleanWSToProcesssMappingTypes;
    }

    protected Set<String> getValidHexBinaryWsToProcessMappingTypes() {
        Set<String> validHexBinaryWSToProcesssMappingTypes =
                new HashSet<String>();
        validHexBinaryWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validHexBinaryWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validHexBinaryWSToProcesssMappingTypes;
    }

    protected Set<String> getValidAnyUriWsToProcessMappingTypes() {
        Set<String> validAnyUriWSToProcesssMappingTypes = new HashSet<String>();
        validAnyUriWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validAnyUriWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validAnyUriWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validAnyUriWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validAnyUriWSToProcesssMappingTypes;
    }

    protected Set<String> getValidAnySimpleTypeWsToProcessMappingTypes() {
        Set<String> validAnySimpleTypeWSToProcesssMappingTypes =
                new HashSet<String>();
        /*
         * validAnySimpleTypeWSToProcesssMappingTypes.addAll(getIntegerProcessTypes
         * ());
         * validAnySimpleTypeWSToProcesssMappingTypes.addAll(getDecimalProcessTypes
         * ());
         * validAnySimpleTypeWSToProcesssMappingTypes.addAll(getStringProcessTypes
         * ());
         * validAnySimpleTypeWSToProcesssMappingTypes.addAll(getDateProcessTypes
         * ());
         * validAnySimpleTypeWSToProcesssMappingTypes.addAll(getTimeProcessTypes
         * ());
         */
        return validAnySimpleTypeWSToProcesssMappingTypes;
    }

    protected Set<String> getValidByteWsToProcessMappingTypes() {
        Set<String> validByteWSToProcesssMappingTypes = new HashSet<String>();
        validByteWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validByteWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validByteWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validByteWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validByteWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNonPositiveIntegerWsToProcessMappingTypes() {
        Set<String> validNonPositiveIntegerWSToProcesssMappingTypes =
                new HashSet<String>();
        validNonPositiveIntegerWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validNonPositiveIntegerWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validNonPositiveIntegerWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validNonPositiveIntegerWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validNonPositiveIntegerWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNegativeIntegerWsToProcessMappingTypes() {
        Set<String> validNegativeIntegerWSToProcesssMappingTypes =
                new HashSet<String>();
        validNegativeIntegerWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validNegativeIntegerWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validNegativeIntegerWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validNegativeIntegerWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validNegativeIntegerWSToProcesssMappingTypes;
    }

    protected Set<String> getValidLongWsToProcessMappingTypes() {
        Set<String> validLongWSToProcesssMappingTypes = new HashSet<String>();
        validLongWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validLongWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validLongWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validLongWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validLongWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNonNegativeIntegerWsToProcessMappingTypes() {
        Set<String> validNonNegativeIntegerWSToProcesssMappingTypes =
                new HashSet<String>();
        validNonNegativeIntegerWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validNonNegativeIntegerWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validNonNegativeIntegerWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validNonNegativeIntegerWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validNonNegativeIntegerWSToProcesssMappingTypes;
    }

    protected Set<String> getValidUnsignedIntWsToProcessMappingTypes() {
        Set<String> validUnsignedIntWSToProcesssMappingTypes =
                new HashSet<String>();
        validUnsignedIntWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validUnsignedIntWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validUnsignedIntWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validUnsignedIntWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validUnsignedIntWSToProcesssMappingTypes;
    }

    protected Set<String> getValidUnsignedShortWsToProcessMappingTypes() {
        Set<String> validUnsignedShortWSToProcesssMappingTypes =
                new HashSet<String>();
        validUnsignedShortWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validUnsignedShortWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validUnsignedShortWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validUnsignedShortWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validUnsignedShortWSToProcesssMappingTypes;
    }

    protected Set<String> getValidUnsignedByteWsToProcessMappingTypes() {
        Set<String> validUnsignedByteWSToProcesssMappingTypes =
                new HashSet<String>();
        validUnsignedByteWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validUnsignedByteWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validUnsignedByteWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validUnsignedByteWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validUnsignedByteWSToProcesssMappingTypes;
    }

    protected Set<String> getValidUnsignedLongWsToProcessMappingTypes() {
        Set<String> validUnsignedLongWSToProcesssMappingTypes =
                new HashSet<String>();
        validUnsignedLongWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validUnsignedLongWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validUnsignedLongWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validUnsignedLongWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validUnsignedLongWSToProcesssMappingTypes;
    }

    protected Set<String> getValidPositiveIntegerWsToProcessMappingTypes() {
        Set<String> validPositiveIntegerWSToProcesssMappingTypes =
                new HashSet<String>();
        validPositiveIntegerWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validPositiveIntegerWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validPositiveIntegerWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validPositiveIntegerWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validPositiveIntegerWSToProcesssMappingTypes;
    }

    protected Set<String> getValidShortWsToProcessMappingTypes() {
        Set<String> validShortWSToProcesssMappingTypes = new HashSet<String>();
        validShortWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validShortWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validShortWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validShortWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validShortWSToProcesssMappingTypes;
    }

    protected Set<String> getValidIntegerWsToProcessMappingTypes() {
        Set<String> validIntegerWSToProcesssMappingTypes =
                new HashSet<String>();
        validIntegerWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validIntegerWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validIntegerWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validIntegerWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validIntegerWSToProcesssMappingTypes;
    }

    protected Set<String> getValidDecimalWsToProcessMappingTypes() {
        Set<String> validDecimalWSToProcesssMappingTypes =
                new HashSet<String>();
        validDecimalWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validDecimalWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validDecimalWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validDecimalWSToProcesssMappingTypes;
    }

    protected Set<String> getValidBase64BinaryWsToProcessMappingTypes() {
        Set<String> validBase64BinaryWSToProcesssMappingTypes =
                new HashSet<String>();
        validBase64BinaryWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validBase64BinaryWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validBase64BinaryWSToProcesssMappingTypes;
    }

    protected Set<String> getValidIdWsToProcessMappingTypes() {
        Set<String> validIdWSToProcesssMappingTypes = new HashSet<String>();
        validIdWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validIdWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validIdWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validIdWSToProcesssMappingTypes.addAll(getDateProcessTypes());
        validIdWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validIdWSToProcesssMappingTypes;
    }

    protected Set<String> getValidGDayWsToProcessMappingTypes() {
        Set<String> validGDayWSToProcesssMappingTypes = new HashSet<String>();
        validGDayWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validGDayWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validGDayWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validGDayWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validGDayWSToProcesssMappingTypes;
    }

    protected Set<String> getValidDurationWsToProcessMappingTypes() {
        Set<String> validDurationWSToProcesssMappingTypes =
                new HashSet<String>();
        validDurationWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validDurationWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validDurationWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validDurationWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validDurationWSToProcesssMappingTypes;
    }

    protected Set<String> getValidYearMonthDurationWsToProcessMappingTypes() {
        Set<String> validYearMonthDurationWSToProcesssMappingTypes =
                new HashSet<String>();
        validYearMonthDurationWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validYearMonthDurationWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validYearMonthDurationWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validYearMonthDurationWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validYearMonthDurationWSToProcesssMappingTypes;
    }

    protected Set<String> getValidDateTimeDurationWsToProcessMappingTypes() {
        Set<String> validDateTimeDurationWSToProcesssMappingTypes =
                new HashSet<String>();
        validDateTimeDurationWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validDateTimeDurationWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validDateTimeDurationWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validDateTimeDurationWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validDateTimeDurationWSToProcesssMappingTypes;
    }

    protected Set<String> getValidGMonthWsToProcessMappingTypes() {
        Set<String> validGmonthWSToProcesssMappingTypes = new HashSet<String>();
        validGmonthWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validGmonthWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validGmonthWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validGmonthWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validGmonthWSToProcesssMappingTypes;
    }

    protected Set<String> getValidGMonthDayWsToProcessMappingTypes() {
        Set<String> validGMonthDayWSToProcesssMappingTypes =
                new HashSet<String>();
        validGMonthDayWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validGMonthDayWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validGMonthDayWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validGMonthDayWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validGMonthDayWSToProcesssMappingTypes;
    }

    protected Set<String> getValidGYearWsToProcessMappingTypes() {
        Set<String> validGyearWSToProcesssMappingTypes = new HashSet<String>();
        validGyearWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validGyearWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validGyearWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validGyearWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validGyearWSToProcesssMappingTypes;
    }

    protected Set<String> getValidGYearMonthWsToProcessMappingTypes() {
        Set<String> validGYearMonthWSToProcesssMappingTypes =
                new HashSet<String>();
        validGYearMonthWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validGYearMonthWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validGYearMonthWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validGYearMonthWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validGYearMonthWSToProcesssMappingTypes;
    }

    protected Set<String> getValidQnameWsToProcessMappingTypes() {
        Set<String> validQnameWSToProcesssMappingTypes = new HashSet<String>();
        validQnameWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validQnameWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validQnameWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validQnameWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validQnameWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNotationWsToProcessMappingTypes() {
        Set<String> validNotationWSToProcesssMappingTypes =
                new HashSet<String>();
        validNotationWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validNotationWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validNotationWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validNotationWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validNotationWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNormalizedStringWsToProcessMappingTypes() {
        Set<String> validNormalizedStringWSToProcesssMappingTypes =
                new HashSet<String>();
        validNormalizedStringWSToProcesssMappingTypes
                .addAll(getIntegerProcessTypes());
        validNormalizedStringWSToProcesssMappingTypes
                .addAll(getDecimalProcessTypes());
        validNormalizedStringWSToProcesssMappingTypes
                .addAll(getStringProcessTypes());
        validNormalizedStringWSToProcesssMappingTypes
                .addAll(getTimeProcessTypes());
        return validNormalizedStringWSToProcesssMappingTypes;
    }

    protected Set<String> getValidIdRefsWsToProcessMappingTypes() {
        Set<String> validIdRefsWSToProcesssMappingTypes = new HashSet<String>();
        validIdRefsWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validIdRefsWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validIdRefsWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validIdRefsWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validIdRefsWSToProcesssMappingTypes;
    }

    protected Set<String> getValidEntitiesWsToProcessMappingTypes() {
        Set<String> validEntitiesWSToProcesssMappingTypes =
                new HashSet<String>();
        validEntitiesWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validEntitiesWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validEntitiesWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validEntitiesWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validEntitiesWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNMTokensWsToProcessMappingTypes() {
        Set<String> validNMTokensWSToProcesssMappingTypes =
                new HashSet<String>();
        validNMTokensWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validNMTokensWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validNMTokensWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validNMTokensWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validNMTokensWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNameWsToProcessMappingTypes() {
        Set<String> validNameWSToProcesssMappingTypes = new HashSet<String>();
        validNameWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validNameWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validNameWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validNameWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validNameWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNCNameWsToProcessMappingTypes() {
        Set<String> validNCNameWSToProcesssMappingTypes = new HashSet<String>();
        validNCNameWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validNCNameWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validNCNameWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validNCNameWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validNCNameWSToProcesssMappingTypes;
    }

    protected Set<String> getValidTokenWsToProcessMappingTypes() {
        Set<String> validTokenWSToProcesssMappingTypes = new HashSet<String>();
        validTokenWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validTokenWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validTokenWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validTokenWSToProcesssMappingTypes.addAll(getDateProcessTypes());
        validTokenWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validTokenWSToProcesssMappingTypes;
    }

    protected Set<String> getValidNMTokenWsToProcessMappingTypes() {
        Set<String> validNMTokenWSToProcesssMappingTypes =
                new HashSet<String>();
        validNMTokenWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validNMTokenWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validNMTokenWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validNMTokenWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validNMTokenWSToProcesssMappingTypes;
    }

    protected Set<String> getValidIDRefWsToProcessMappingTypes() {
        Set<String> validIDRefWSToProcesssMappingTypes = new HashSet<String>();
        validIDRefWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validIDRefWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validIDRefWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validIDRefWSToProcesssMappingTypes.addAll(getDateProcessTypes());
        validIDRefWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validIDRefWSToProcesssMappingTypes;
    }

    protected Set<String> getValidEntityWsToProcessMappingTypes() {
        Set<String> validEntityWSToProcesssMappingTypes = new HashSet<String>();
        validEntityWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validEntityWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validEntityWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validEntityWSToProcesssMappingTypes.addAll(getDateProcessTypes());
        validEntityWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validEntityWSToProcesssMappingTypes;
    }

    protected Set<String> getValidLanguageWsToProcessMappingTypes() {
        Set<String> validLanguageWSToProcesssMappingTypes =
                new HashSet<String>();
        validLanguageWSToProcesssMappingTypes.addAll(getIntegerProcessTypes());
        validLanguageWSToProcesssMappingTypes.addAll(getDecimalProcessTypes());
        validLanguageWSToProcesssMappingTypes.addAll(getStringProcessTypes());
        validLanguageWSToProcesssMappingTypes.addAll(getDateProcessTypes());
        validLanguageWSToProcesssMappingTypes.addAll(getTimeProcessTypes());
        return validLanguageWSToProcesssMappingTypes;
    }

    public boolean isValidProcessToWSMappingType(String sourceType,
            String targetType) {
        boolean isValid = false;
        String[] targetTypes = targetType.split(","); //$NON-NLS-1$
        for (String nextTargetType : targetTypes) {
            if (sourceType != null && nextTargetType != null) {
                if (isIntProcessType(sourceType)) {
                    if (getValidIntegerProcessToWSMappingTypes()
                            .contains(nextTargetType.toUpperCase())) {
                        if (!(sourceType
                                .toUpperCase()
                                .equals(XPathConsts.XPATH_TYPE_BOOLEAN.toUpperCase()) && (nextTargetType
                                .toUpperCase()
                                .equals(XPathConsts.INT.toUpperCase())
                                || nextTargetType
                                        .toUpperCase()
                                        .equals(XPathConsts.INTEGER.toUpperCase())
                                || nextTargetType
                                        .toUpperCase()
                                        .equals(XPathConsts.FLOAT.toUpperCase())
                                || nextTargetType
                                        .toUpperCase()
                                        .equals(XPathConsts.DOUBLE.toUpperCase())
                                || nextTargetType
                                        .toUpperCase()
                                        .equals(XPathConsts.PRECISIONDECIMAL.toUpperCase()) || nextTargetType
                                .toUpperCase()
                                .equals(XPathConsts.XPATH_TYPE_BOOLEAN.toUpperCase())))) {
                            isValid = true;
                        }
                    }
                } else if (isDecimalProcessType(sourceType)) {
                    if (getValidDecimalProcessToWSMappingTypes()
                            .contains(nextTargetType.toUpperCase())) {
                        isValid = true;
                    }
                } else if (isStringProcessType(sourceType)) {
                    if (getValidStringProcessToWSMappingTypes()
                            .contains(nextTargetType.toUpperCase())) {
                        isValid = true;
                    }
                } else if (isDateTimeProcessType(sourceType)) {
                    if (getValidDateTimeProcessToWSMappingTypes()
                            .contains(nextTargetType.toUpperCase())) {
                        isValid = true;
                    }
                } else if (isDateProcessType(sourceType)) {
                    if (getValidDateProcessToWSMappingTypes()
                            .contains(nextTargetType.toUpperCase())) {
                        isValid = true;
                    }
                } else if (isTimeProcessType(sourceType)) {
                    if (getValidTimeProcessToWSMappingTypes()
                            .contains(nextTargetType.toUpperCase())) {
                        isValid = true;
                    }
                }
            }
        }
        return isValid;
    }

    public boolean isValidWSToProcessMappingType(String sourceType,
            String targetType) {
        boolean isValid = false;
        String[] sourceTypes = sourceType.split(","); //$NON-NLS-1$
        for (String nextSourceType : sourceTypes) {
            if (nextSourceType != null && targetType != null) {
                nextSourceType = nextSourceType.toUpperCase();
                targetType = targetType.toUpperCase();
                if (nextSourceType.equals(XPathConsts.INT.toUpperCase())
                        && getValidIntWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.FLOAT
                        .toUpperCase())
                        && getValidFloatWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.PRECISIONDECIMAL
                        .toUpperCase())
                        && getValidPrecisionDecimalWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.DOUBLE
                        .toUpperCase())
                        && getValidDoubleWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType
                        .equals(XPathConsts.DATE.toUpperCase())
                        && getValidDateWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType
                        .equals(XPathConsts.TIME.toUpperCase())
                        && getValidTimeWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.DATETIME
                        .toUpperCase())
                        && getValidDateTimeWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.XPATH_TYPE_STRING
                        .toUpperCase())
                        && getValidStringWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.XPATH_TYPE_BOOLEAN
                        .toUpperCase())
                        && getValidBooleanWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.HEXBINARY
                        .toUpperCase())
                        && getValidHexBinaryWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.ANYURL
                        .toUpperCase())
                        && getValidAnyUriWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.ANYSIMPLETYPE
                        .toUpperCase())
                        && getValidAnySimpleTypeWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType
                        .equals(XPathConsts.BYTE.toUpperCase())
                        && getValidByteWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.NONPOSITIVEINTEGER
                        .toUpperCase())
                        && getValidNonPositiveIntegerWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.NEGATIVEINTEGER
                        .toUpperCase())
                        && getValidNegativeIntegerWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType
                        .equals(XPathConsts.LONG.toUpperCase())
                        && getValidLongWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.NONNEGATIVEINTEGER
                        .toUpperCase())
                        && getValidNonNegativeIntegerWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.UNSIGNEDINT
                        .toUpperCase())
                        && getValidUnsignedIntWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.UNSIGNEDSHORT
                        .toUpperCase())
                        && getValidUnsignedShortWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.UNSIGNEDBYTE
                        .toUpperCase())
                        && getValidUnsignedByteWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.UNSIGNEDLONG
                        .toUpperCase())
                        && getValidUnsignedLongWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.POSITIVEINTEGER
                        .toUpperCase())
                        && getValidPositiveIntegerWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.SHORT
                        .toUpperCase())
                        && getValidShortWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.INTEGER
                        .toUpperCase())
                        && getValidIntegerWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.DECIMAL
                        .toUpperCase())
                        && getValidDecimalWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.BASE64BINARY
                        .toUpperCase())
                        && getValidBase64BinaryWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.ID.toUpperCase())
                        && getValidIdWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType
                        .equals(XPathConsts.GDAY.toUpperCase())
                        && getValidGDayWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.DURATION
                        .toUpperCase())
                        && getValidDurationWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.DATETIMEDURATION
                        .toUpperCase())
                        && getValidDateTimeDurationWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.YEARMONTHDURATION
                        .toUpperCase())
                        && getValidYearMonthDurationWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.GMONTH
                        .toUpperCase())
                        && getValidGMonthWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.GMONTHDAY
                        .toUpperCase())
                        && getValidGMonthDayWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.GYEAR
                        .toUpperCase())
                        && getValidGYearWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.GYEARMONTH
                        .toUpperCase())
                        && getValidGYearMonthWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.QNAME
                        .toUpperCase())
                        && getValidQnameWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.NOTATION
                        .toUpperCase())
                        && getValidNotationWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.NORMALIZEDSTRING
                        .toUpperCase())
                        && getValidNormalizedStringWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.IDREFS
                        .toUpperCase())
                        && getValidIdRefsWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.ENTITIES
                        .toUpperCase())
                        && getValidEntitiesWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.NMTOKENS
                        .toUpperCase())
                        && getValidNMTokensWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType
                        .equals(XPathConsts.NAME.toUpperCase())
                        && getValidNameWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.NCNAME
                        .toUpperCase())
                        && getValidNCNameWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.TOKEN
                        .toUpperCase())
                        && getValidTokenWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.NMTOKEN
                        .toUpperCase())
                        && getValidNMTokenWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.IDREF
                        .toUpperCase())
                        && getValidIDRefWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.ENTITY
                        .toUpperCase())
                        && getValidEntityWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.LANGUAGE
                        .toUpperCase())
                        && getValidLanguageWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                } else if (nextSourceType.equals(XPathConsts.XPATH_TYPE_NUMBER
                        .toUpperCase())
                        && getValidDecimalWsToProcessMappingTypes()
                                .contains(targetType.toUpperCase())) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    protected boolean isIntProcessType(String type) {
        if (type != null
                && getIntegerProcessTypes().contains(type.toUpperCase())) {
            return true;
        }
        return false;
    }

    protected boolean isDecimalProcessType(String type) {
        if (type != null
                && getDecimalProcessTypes().contains(type.toUpperCase())) {
            return true;
        }
        return false;
    }

    protected boolean isStringProcessType(String type) {
        if (type != null
                && getStringProcessTypes().contains(type.toUpperCase())) {
            return true;
        }
        return false;
    }

    protected boolean isDateTimeProcessType(String type) {
        if (type != null
                && getDateTimeProcessTypes().contains(type.toUpperCase())) {
            return true;
        }
        return false;
    }

    protected boolean isDateProcessType(String type) {
        if (type != null && getDateProcessTypes().contains(type.toUpperCase())) {
            return true;
        }
        return false;
    }

    protected boolean isTimeProcessType(String type) {
        if (type != null && getTimeProcessTypes().contains(type.toUpperCase())) {
            return true;
        }
        return false;
    }
}
