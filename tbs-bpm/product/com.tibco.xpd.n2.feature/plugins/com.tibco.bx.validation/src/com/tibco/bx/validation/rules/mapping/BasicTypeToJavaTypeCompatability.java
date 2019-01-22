package com.tibco.bx.validation.rules.mapping;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Compares process data types with java Method parameter data types.
 * 
 * @author aallway
 * @since 3.3 (14 Jun 2010)
 */
public class BasicTypeToJavaTypeCompatability {

    public static BasicTypeToJavaTypeCompatability[] basicTypeToJavaTypeCompatibility =
            new BasicTypeToJavaTypeCompatability[] {
                    /*
                     * BOOLEAN process data
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.BOOLEAN_LITERAL,
                            ParameterTypeEnum.BOOLEAN),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.BOOLEAN_LITERAL,
                            ParameterTypeEnum.PBOOLEAN),

                    /*
                     * DATES / TIMES process data
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATE_LITERAL, ParameterTypeEnum.DATE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.TIME_LITERAL, ParameterTypeEnum.DATE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATETIME_LITERAL,
                            ParameterTypeEnum.DATE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATE_LITERAL,
                            ParameterTypeEnum.CLASS, Date.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.TIME_LITERAL,
                            ParameterTypeEnum.CLASS, Date.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATETIME_LITERAL,
                            ParameterTypeEnum.CLASS, Date.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATE_LITERAL,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.TIME_LITERAL,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATETIME_LITERAL,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    /*
                     * DECIMAL process data
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.PFLOAT),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.FLOAT),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.PDOUBLE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.DOUBLE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.BIGDECIMAL),

                    /*
                     * INTEGER process data
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.SHORT),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.PSHORT),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.INTEGER),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.PINTEGER),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.LONG),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.PLONG),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.PBYTE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.BYTE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.BIGINTEGER),

                    /*
                     * STRING
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.STRING_LITERAL,
                            ParameterTypeEnum.STRING),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.STRING_LITERAL,
                            ParameterTypeEnum.CHAR),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.STRING_LITERAL,
                            ParameterTypeEnum.PCHAR),

                    /*
                     * PERFORMER
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.PERFORMER_LITERAL,
                            ParameterTypeEnum.STRING),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.PERFORMER_LITERAL,
                            ParameterTypeEnum.CHAR),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.PERFORMER_LITERAL,
                            ParameterTypeEnum.PCHAR),
            /* Any others... */
            };

    public static BasicTypeToJavaTypeCompatability[] javaTypeToBasicTypeCompatibility =
            new BasicTypeToJavaTypeCompatability[] {
                    /*
                     * BOOLEAN process data
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.BOOLEAN_LITERAL,
                            ParameterTypeEnum.BOOLEAN),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.BOOLEAN_LITERAL,
                            ParameterTypeEnum.PBOOLEAN),

                    /*
                     * DATES / TIMES process data
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATE_LITERAL, ParameterTypeEnum.DATE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.TIME_LITERAL, ParameterTypeEnum.DATE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATETIME_LITERAL,
                            ParameterTypeEnum.DATE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATE_LITERAL,
                            ParameterTypeEnum.CLASS, Date.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.TIME_LITERAL,
                            ParameterTypeEnum.CLASS, Date.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATETIME_LITERAL,
                            ParameterTypeEnum.CLASS, Date.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATE_LITERAL,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.TIME_LITERAL,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.DATETIME_LITERAL,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),

                    /*
                     * DECIMAL process data
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.PFLOAT),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.FLOAT),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.PDOUBLE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.DOUBLE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.PLONG),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL, ParameterTypeEnum.LONG),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.FLOAT_LITERAL,
                            ParameterTypeEnum.BIGDECIMAL),

                    /*
                     * INTEGER process data
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.SHORT),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.PSHORT),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.INTEGER),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.PINTEGER),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.LONG),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.PLONG),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.PBYTE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.BYTE),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.INTEGER_LITERAL,
                            ParameterTypeEnum.BIGINTEGER),

                    /*
                     * STRING
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.STRING_LITERAL,
                            ParameterTypeEnum.STRING),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.STRING_LITERAL,
                            ParameterTypeEnum.CHAR),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.STRING_LITERAL,
                            ParameterTypeEnum.PCHAR),

                    /*
                     * PERFORMER
                     */
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.PERFORMER_LITERAL,
                            ParameterTypeEnum.STRING),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.PERFORMER_LITERAL,
                            ParameterTypeEnum.CHAR),
                    new BasicTypeToJavaTypeCompatability(
                            BasicTypeType.PERFORMER_LITERAL,
                            ParameterTypeEnum.PCHAR),
            /* Any others... */
            };

    /**
     * Check if the data type of the process data can be assigned to the java
     * method parameter type.
     * 
     * @param processDataType
     * @param javaMethodParameter
     * 
     * @return <code>true</code> if compatible else false.
     */
    public static boolean isBasicTypeAssignableToJavaType(
            BasicTypeType basicType, JavaMethodParameter javaMethodParameter) {
        ParameterTypeEnum javaType = javaMethodParameter.getType();

        for (BasicTypeToJavaTypeCompatability btc : basicTypeToJavaTypeCompatibility) {
            if (btc.basicType.equals(basicType)) {
                if (btc.compatibleJavaType.equals(javaType)) {
                    /*
                     * Found a potential match, if it's a class then we need to
                     * make sure correct class on method param.
                     */
                    if (!ParameterTypeEnum.CLASS.equals(btc.compatibleJavaType)) {
                        return true;
                    }

                    /*
                     * Check that class that BasicType can be converted to is
                     * assignment compatible with method parameter.
                     */
                    if (btc.compatibleJavaClass != null) {
                        IType javaIType = javaMethodParameter.getIType();
                        if (javaIType != null) {
                            if (isAssignableToJavaType(btc.compatibleJavaClass,
                                    javaIType)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check if the data type of the java method return parameter can be
     * assigned to the process data type
     * 
     * @param javaMethodReturnParameter
     * @param processDataType
     * 
     * @return <code>true</code> if compatible else false.
     */
    public static boolean isJavaTypeAssignableToBasicType(
            JavaMethodParameter javaMethodParameter, BasicTypeType basicType) {
        ParameterTypeEnum javaType = javaMethodParameter.getType();

        for (BasicTypeToJavaTypeCompatability btc : javaTypeToBasicTypeCompatibility) {
            if (btc.basicType.equals(basicType)) {
                if (btc.compatibleJavaType.equals(javaType)) {
                    /*
                     * Found a potential match, if it's a class then we need to
                     * make sure correct class on method param.
                     */
                    if (!ParameterTypeEnum.CLASS.equals(btc.compatibleJavaType)) {
                        return true;
                    }

                    /*
                     * Check that class that BasicType can be converted to is
                     * assignment compatible with method parameter.
                     */
                    if (btc.compatibleJavaClass != null) {
                        IType javaIType = javaMethodParameter.getIType();
                        if (javaIType != null) {
                            if (isAssignableFromJavaType(javaIType,
                                    btc.compatibleJavaClass)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check that the java return data is assignment compatible with the class
     * that process data is stated as compatible with
     * 
     * @param fromType
     * @param toClass
     * @return <code>true</code> if compatible.
     */
    private static boolean isAssignableFromJavaType(IType fromType,
            Class<?> toClass) {
        String toClassFullyQualifiedName = toClass.getName();

        IType foundType = null;

        try {
            IType checkType = fromType;
            while (checkType != null && foundType == null) {
                if (toClassFullyQualifiedName.equals(checkType
                        .getFullyQualifiedName())) {
                    foundType = fromType;

                } else {
                    /*
                     * Check interfaces implemented by this class.
                     */
                    String[] interfaces =
                            checkType.getSuperInterfaceTypeSignatures();
                    if (interfaces != null) {
                        for (String ifcSig : interfaces) {
                            IType ifc =
                                    JavaModelUtil
                                            .resolveIType(fromType, ifcSig);
                            if (ifc != null) {
                                if (toClassFullyQualifiedName.equals(ifc
                                        .getFullyQualifiedName())) {
                                    foundType = ifc;
                                    break;
                                }
                            }
                        }
                    }

                    if (foundType == null) {
                        /* drill down into super class. */
                        String superclassTypeSignature =
                                checkType.getSuperclassTypeSignature();
                        if (superclassTypeSignature != null
                                && superclassTypeSignature.length() > 0) {
                            checkType =
                                    JavaModelUtil.resolveIType(fromType,
                                            superclassTypeSignature);
                        } else {
                            checkType = null;
                        }
                    }
                }
            } /* Next super class if necessary. */

        } catch (JavaModelException e) {

        }

        if (foundType != null) {
            /*
             * Found that method parameter is the class (or one of it's super
             * classes/interfaces) stated as compatible with process data type
             */
            return true;
        }

        return false;
    }

    /**
     * Check whether the given class is assignment compatible with the given
     * java method parameter IType (i.e. is it same type as OR has a super-class
     * hierarchy member of same type OR implements an interface the same as the
     * method parameter.
     * 
     * @param fromClass
     * @param toType
     * 
     * @return <code>true</code> if compatible.
     */
    private static boolean isAssignableToJavaType(Class<?> fromClass,
            IType toType) {
        String toTypeFullyQualifiedName = toType.getFullyQualifiedName();
        Class<?> foundClass = null;
        while (fromClass != null && foundClass == null) {
            if (toTypeFullyQualifiedName.equals(fromClass.getName())) {
                foundClass = fromClass;

            } else {
                /*
                 * Check interfaces implemented by this class.
                 */
                Class<?>[] interfaces = fromClass.getInterfaces();
                if (interfaces != null) {
                    for (Class<?> ifc : interfaces) {
                        if (toTypeFullyQualifiedName.equals(ifc.getName())) {
                            foundClass = ifc;
                            break;
                        }
                    }
                }

                if (foundClass == null) {
                    /* drill down into super class. */
                    fromClass = fromClass.getSuperclass();
                }
            }
        } /* Next super class if necessary. */

        if (foundClass != null) {
            /*
             * Found that method parameter is the class (or one of it's super
             * classes/interfaces) stated as compatible with process data type
             */
            return true;
        }

        return false;
    }

    /** Process data simple type. */
    BasicTypeType basicType;

    /** compatible java parameter type. */
    ParameterTypeEnum compatibleJavaType;

    /** Class compatyibility if ParameterTypeEnum.CLASS */
    Class<?> compatibleJavaClass = null;

    /**
     * @param basicType
     * @param compatibleJavaType
     */
    private BasicTypeToJavaTypeCompatability(BasicTypeType basicType,
            ParameterTypeEnum compatibleJavaType) {
        super();
        this.basicType = basicType;
        this.compatibleJavaType = compatibleJavaType;
    }

    /**
     * @param basicType
     * @param compatibleJavaType
     * @param compatibleJavaClass
     */
    private BasicTypeToJavaTypeCompatability(BasicTypeType basicType,
            ParameterTypeEnum compatibleJavaType, Class<?> compatibleJavaClass) {
        this(basicType, compatibleJavaType);

        this.compatibleJavaClass = compatibleJavaClass;
    }

}