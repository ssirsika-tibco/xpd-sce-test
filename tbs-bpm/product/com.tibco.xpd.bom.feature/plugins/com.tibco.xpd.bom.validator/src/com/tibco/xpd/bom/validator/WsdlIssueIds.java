/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.validator;

/**
 * Interface to centralise Issue Ids
 * 
 * @author glewis
 * 
 */
public final class WsdlIssueIds {
    public static final String MULTIPLICITY_NOT_SUPPORTED_OUTPUT_PARAM =
            "wsdl.multiplicity.not.supported.output.param.issue"; //$NON-NLS-1$  

    public static final String MULTIPLICITY_NOT_SUPPORTED_INPUT_PARAM =
            "wsdl.multiplicity.not.supported.input.param.issue"; //$NON-NLS-1$

    public static final String NO_OPERATION_INPUT_PARAM =
            "wsdl.no.operation.param.issue"; //$NON-NLS-1$

    public static final String BOM_REF_NOT_ALLOWED =
            "wsdl.bom.ref.not.allowed.issue"; //$NON-NLS-1$

    public static final String PROPERTY_BOM_REF_NOT_ALLOWED =
            "wsdl.property.bom.ref.not.allowed.issue"; //$NON-NLS-1$

    public static final String OPERATION_PARAMETER_BOM_REF_NOT_ALLOWED =
            "wsdl.operation.parameter.bom.ref.not.allowed.issue"; //$NON-NLS-1$
}
