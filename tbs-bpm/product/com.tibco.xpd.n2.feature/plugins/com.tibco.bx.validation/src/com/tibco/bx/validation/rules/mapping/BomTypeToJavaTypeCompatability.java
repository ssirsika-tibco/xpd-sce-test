package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;

/**
 * Compares process data types with java Method parameter data types.
 * 
 * @author aallway
 * @since 3.3 (14 Jun 2010)
 */
public class BomTypeToJavaTypeCompatability {

    public static BomTypeToJavaTypeCompatability[] bomTypeToJavaTypeCompatibility =
            new BomTypeToJavaTypeCompatability[] {
                    /*
                     * OBJECT
                     */
                    new BomTypeToJavaTypeCompatability(
                            PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME,
                            ParameterTypeEnum.CLASS, FeatureMap.class),
                    /*
                     * ENUMERATION
                     * 
                     * 
                     * XPD-7014: Allow BOM.Enum Attribute <-> Java Text mappings
                     */
                    new BomTypeToJavaTypeCompatability(
                            PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME,
                            ParameterTypeEnum.STRING, FeatureMap.class) };

    public static BomTypeToJavaTypeCompatability[] javaTypeToBomTypeCompatibility =
            new BomTypeToJavaTypeCompatability[] {
            /*
             * BOM OBJECT primitive type.
             */
            new BomTypeToJavaTypeCompatability(
                    PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME,
                    ParameterTypeEnum.CLASS, FeatureMap.class),
            /* Any others... */

                    /*
                     * ENUMERATION
                     * 
                     * XPD-7014: Allow BOM.Enum Attribute<-> Java Text mappings
                     */
                    new BomTypeToJavaTypeCompatability(
                            PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME,
                            ParameterTypeEnum.STRING, FeatureMap.class)
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
    public static boolean isBomTypeAssignableToJavaType(
            String primitiveTypeName, JavaMethodParameter javaMethodParameter) {
        ParameterTypeEnum javaType = javaMethodParameter.getType();

        for (BomTypeToJavaTypeCompatability btc : bomTypeToJavaTypeCompatibility) {
            if (btc.primitivesName.equals(primitiveTypeName)) {
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
    public static boolean isJavaTypeAssignableToBomType(
            JavaMethodParameter javaMethodParameter, String primitiveTypeName) {
        ParameterTypeEnum javaType = javaMethodParameter.getType();

        for (BomTypeToJavaTypeCompatability btc : javaTypeToBomTypeCompatibility) {
            if (btc.primitivesName.equals(primitiveTypeName)) {
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
    public static boolean isJavaTypeAssignableToBomType(String javaType,
            String primitiveTypeName) {

        for (BomTypeToJavaTypeCompatability btc : javaTypeToBomTypeCompatibility) {
            if (btc.primitivesName.equals(primitiveTypeName)) {
                if (btc.compatibleJavaType.equals(javaType)) {
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

    /** Process data simple type. */
    String primitivesName;

    /** compatible java parameter type. */
    ParameterTypeEnum compatibleJavaType;

    /** Class compatyibility if ParameterTypeEnum.CLASS */
    Class<?> compatibleJavaClass = null;

    /**
     * @param primitivesName
     * @param compatibleJavaType
     */
    private BomTypeToJavaTypeCompatability(String primitivesName,
            ParameterTypeEnum compatibleJavaType) {
        super();
        this.primitivesName = primitivesName;
        this.compatibleJavaType = compatibleJavaType;
    }

    /**
     * @param primitivesName
     * @param compatibleJavaType
     * @param compatibleJavaClass
     */
    private BomTypeToJavaTypeCompatability(String primitivesName,
            ParameterTypeEnum compatibleJavaType, Class<?> compatibleJavaClass) {
        this(primitivesName, compatibleJavaType);

        this.compatibleJavaClass = compatibleJavaClass;
    }

}