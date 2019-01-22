/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import org.eclipse.jdt.core.Signature;

/**
 * Java method parameter type enum.
 * 
 * @author njpatel
 * 
 */
public enum ParameterTypeEnum {
    BYTE("Byte"), //$NON-NLS-1$ 
    PBYTE("byte"), //$NON-NLS-1$ 
    INTEGER("Integer"), //$NON-NLS-1$
    PINTEGER("int"), //$NON-NLS-1$
    SHORT("Short"), //$NON-NLS-1$
    PSHORT("short"), //$NON-NLS-1$
    LONG("Long"), //$NON-NLS-1$
    PLONG("long"), //$NON-NLS-1$
    FLOAT("Float"), //$NON-NLS-1$
    PFLOAT("float"), //$NON-NLS-1$
    DOUBLE("Double"), //$NON-NLS-1$
    PDOUBLE("double"), //$NON-NLS-1$
    BOOLEAN("Boolean"), //$NON-NLS-1$
    PBOOLEAN("boolean"), //$NON-NLS-1$
    CHAR("Character"), //$NON-NLS-1$
    PCHAR("char"), //$NON-NLS-1$
    BIGDECIMAL("BigDecimal"), //$NON-NLS-1$
    BIGINTEGER("BigInteger"), //$NON-NLS-1$
    DATE("Date"), //$NON-NLS-1$
    STRING("String"), //$NON-NLS-1$
    LIST("List"), //$NON-NLS-1$
    VOID("void"), //$NON-NLS-1$ 
    CLASS("Class"); //$NON-NLS-1$

    private String label;

    /**
     * Constructor
     * 
     * @param label
     */
    ParameterTypeEnum(String label) {
        this.label = label;
    }

    /**
     * Get the label of the parameter type.
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the ParameterType that corresponds to the type signature returned by
     * JDT.
     * 
     * @param signatureType
     * @return
     */
    public static ParameterTypeEnum getType(String signatureType) {
        String elementType = Signature.getElementType(signatureType);
        int kind = Signature.getTypeSignatureKind(elementType);
        ParameterTypeEnum paramType = null;

        if (kind == Signature.CLASS_TYPE_SIGNATURE) {
            String simpleName = Signature.getSignatureSimpleName(elementType);

            if (simpleName.equals("Byte")) { //$NON-NLS-1$
                paramType = ParameterTypeEnum.BYTE;
            } else if (simpleName.equals("Integer")) { //$NON-NLS-1$
                paramType = INTEGER;
            } else if (simpleName.equals("Short")) { //$NON-NLS-1$
                paramType = SHORT;
            } else if (simpleName.equals("Long")) { //$NON-NLS-1$
                paramType = LONG;
            } else if (simpleName.equals("Float")) { //$NON-NLS-1$
                paramType = FLOAT;
            } else if (simpleName.equals("Double")) { //$NON-NLS-1$
                paramType = DOUBLE;
            } else if (simpleName.equals("Boolean")) { //$NON-NLS-1$
                paramType = BOOLEAN;
            } else if (simpleName.equals("Character")) { //$NON-NLS-1$
                paramType = CHAR;
            } else if (simpleName.equals("BigDecimal")) { //$NON-NLS-1$
                paramType = BIGDECIMAL;
            } else if (simpleName.equals("BigInteger")) { //$NON-NLS-1$
                paramType = BIGINTEGER;
            } else if (simpleName.equals("Date")) { //$NON-NLS-1$
                paramType = DATE;
            } else if (simpleName.equals("String")) { //$NON-NLS-1$
                paramType = STRING;
            } else if (simpleName.equals("List")) { //$NON-NLS-1$
                paramType = LIST;
            } else {
                paramType = ParameterTypeEnum.CLASS;
            }

        } else if (kind == Signature.BASE_TYPE_SIGNATURE) {

            if (elementType != null) {
                if (elementType.equals(Signature.SIG_CHAR)) {
                    paramType = PCHAR;

                } else if (elementType.equals(Signature.SIG_BOOLEAN)) {
                    paramType = PBOOLEAN;

                } else if (elementType.equals(Signature.SIG_INT)) {
                    paramType = PINTEGER;
                } else if (elementType.equals(Signature.SIG_LONG)) {
                    paramType = PLONG;
                } else if (elementType.equals(Signature.SIG_SHORT)) {
                    paramType = PSHORT;
                } else if (elementType.equals(Signature.SIG_DOUBLE)) {
                    paramType = PDOUBLE;
                } else if (elementType.equals(Signature.SIG_FLOAT)) {
                    paramType = PFLOAT;
                } else if (elementType.equals(Signature.SIG_BYTE)) {
                    paramType = PBYTE;
                } else if (elementType.equals(Signature.SIG_VOID)) {
                    paramType = VOID;
                }
            }
        }

        return paramType;
    }
}
