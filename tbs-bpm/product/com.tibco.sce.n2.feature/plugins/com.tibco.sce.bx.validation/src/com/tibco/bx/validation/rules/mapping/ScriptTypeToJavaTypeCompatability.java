package com.tibco.bx.validation.rules.mapping;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.script.model.JsConsts;

/**
 * Compares script data types with java Method parameter data types.
 * 
 * @author mtorres
 * @since 3.5
 */
public class ScriptTypeToJavaTypeCompatability {

    public static ScriptTypeToJavaTypeCompatability[] scriptTypeToJavaTypeCompatibility =
            new ScriptTypeToJavaTypeCompatability[] {
                    /*
                     * BOOLEAN process data
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.BOOLEAN,
                            ParameterTypeEnum.BOOLEAN),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.BOOLEAN,
                            ParameterTypeEnum.PBOOLEAN),

                    /*
                     * DATES / TIMES process data
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATE,
                            ParameterTypeEnum.DATE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TIME,
                            ParameterTypeEnum.DATE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATETIME,
                            ParameterTypeEnum.DATE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATE,
                            ParameterTypeEnum.CLASS, Date.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TIME,
                            ParameterTypeEnum.CLASS, Date.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATETIME,
                            ParameterTypeEnum.CLASS, Date.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATE,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TIME,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATETIME,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    /*
                     * DECIMAL process data
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.PFLOAT),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.FLOAT),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.PDOUBLE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.DOUBLE),

                    /*
                     * INTEGER process data
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.SHORT),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.PSHORT),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.INTEGER),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.PINTEGER),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.LONG),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.PLONG),

                    /*
                     * STRING
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TEXT,
                            ParameterTypeEnum.STRING),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TEXT,
                            ParameterTypeEnum.CHAR),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TEXT,
                            ParameterTypeEnum.PCHAR),

            /* Any others... */
            };

    public static ScriptTypeToJavaTypeCompatability[] javaTypeToBasicTypeCompatibility =
            new ScriptTypeToJavaTypeCompatability[] {
                    /*
                     * BOOLEAN process data
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.BOOLEAN,
                            ParameterTypeEnum.BOOLEAN),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.BOOLEAN,
                            ParameterTypeEnum.PBOOLEAN),

                    /*
                     * DATES / TIMES process data
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATE,
                            ParameterTypeEnum.DATE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TIME,
                            ParameterTypeEnum.DATE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATETIME,
                            ParameterTypeEnum.DATE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATE,
                            ParameterTypeEnum.CLASS, Date.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TIME,
                            ParameterTypeEnum.CLASS, Date.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATETIME,
                            ParameterTypeEnum.CLASS, Date.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATE,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TIME,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.DATETIME,
                            ParameterTypeEnum.CLASS, XMLGregorianCalendar.class),

                    /*
                     * DECIMAL process data
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.PFLOAT),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.FLOAT),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.PDOUBLE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.DOUBLE),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.PLONG),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.FLOAT,
                            ParameterTypeEnum.LONG),

                    /*
                     * INTEGER process data
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.SHORT),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.PSHORT),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.INTEGER),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.PINTEGER),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.LONG),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.INTEGER,
                            ParameterTypeEnum.PLONG),

                    /*
                     * STRING
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TEXT,
                            ParameterTypeEnum.STRING),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TEXT,
                            ParameterTypeEnum.CHAR),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.TEXT,
                            ParameterTypeEnum.PCHAR),

                    /*
                     * XPD-7317: added to support String returned from Scripts.
                     */
                    new ScriptTypeToJavaTypeCompatability(JsConsts.STRING,
                            ParameterTypeEnum.STRING),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.STRING,
                            ParameterTypeEnum.CHAR),
                    new ScriptTypeToJavaTypeCompatability(JsConsts.STRING,
                            ParameterTypeEnum.PCHAR),

            /* Any others... */
            };

    /**
     * Check if the data type of the script can be assigned to the java method
     * parameter type.
     * 
     * @param processDataType
     * @param javaMethodParameter
     * 
     * @return <code>true</code> if compatible else false.
     */
    public static boolean isScriptTypeAssignableToJavaType(String type,
            JavaMethodParameter javaMethodParameter) {
        ParameterTypeEnum javaType = javaMethodParameter.getType();

        for (ScriptTypeToJavaTypeCompatability btc : scriptTypeToJavaTypeCompatibility) {
            if (btc.basicType.equals(type)) {
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
    public static boolean isJavaScriptTypeAssignableToBasicType(
            JavaMethodParameter javaMethodParameter, String basicType) {
        ParameterTypeEnum javaType = javaMethodParameter.getType();

        for (ScriptTypeToJavaTypeCompatability btc : javaTypeToBasicTypeCompatibility) {
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
     * Check if the data type of the java method return parameter can be
     * assigned to the process data type
     * 
     * @param javaMethodReturnParameter
     * @param processDataType
     * 
     * @return <code>true</code> if compatible else false.
     */
    public static boolean isJavaScriptTypeAssignableToBasicType(
            String javaType, String basicType) {

        for (ScriptTypeToJavaTypeCompatability btc : javaTypeToBasicTypeCompatibility) {
            if (btc.basicType.equals(basicType)) {
                if (btc.compatibleJavaType.getLabel().equals(javaType)) {
                    /*
                     * Found a potential match, if it's a class then we need to
                     * make sure correct class on method param.
                     */
                    if (!ParameterTypeEnum.CLASS.equals(btc.compatibleJavaType)) {
                        return true;
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

    /** Type. */
    String basicType;

    /** compatible java parameter type. */
    ParameterTypeEnum compatibleJavaType;

    /** Class compatyibility if ParameterTypeEnum.CLASS */
    Class<?> compatibleJavaClass = null;

    /**
     * @param basicType
     * @param compatibleJavaType
     */
    private ScriptTypeToJavaTypeCompatability(String basicType,
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
    private ScriptTypeToJavaTypeCompatability(String basicType,
            ParameterTypeEnum compatibleJavaType, Class<?> compatibleJavaClass) {
        this(basicType, compatibleJavaType);

        this.compatibleJavaClass = compatibleJavaClass;
    }

}