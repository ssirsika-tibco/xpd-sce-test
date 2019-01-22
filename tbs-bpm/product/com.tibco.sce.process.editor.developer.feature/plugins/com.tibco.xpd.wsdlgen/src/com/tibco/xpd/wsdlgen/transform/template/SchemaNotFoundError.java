/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.wsdlgen.transform.template;

import org.openarchitectureware.xtend.parser.SyntaxError;

import com.tibco.xpd.wsdlgen.internal.Messages;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;

/**
 * Specialized (OAW) syntax error to report missing schemas during XPDL to WSDL
 * generation. This allows for a more meaningful error to be reported to the
 * user (via the error log).
 * 
 * @author njpatel
 * @since 3.6.0
 */
/* public */class SchemaNotFoundError extends SyntaxError {

    private final Object procOrProcIfc;

    private final String dataTypeName;

    private final String externalRef;

    private final String bomLocation;

    /**
     * 
     */
    public SchemaNotFoundError(Object procOrProcIfc, String dataTypeName,
            String externalRef, String bomLocation) {
        super(0, 0, 0, null);
        this.procOrProcIfc = procOrProcIfc;
        this.dataTypeName = dataTypeName;
        this.externalRef = externalRef;
        this.bomLocation = bomLocation;
    }

    /**
     * @see org.openarchitectureware.xtend.parser.OawError#getMessage()
     * 
     * @return
     */
    @Override
    public String getMessage() {

        if (procOrProcIfc instanceof Process) {
            return String
                    .format(Messages.SchemaNotFoundError_cannotFindSchemaForProcess_error_longdesc,
                            dataTypeName,
                            externalRef,
                            bomLocation,
                            ((Process) procOrProcIfc).getName());

        } else if (procOrProcIfc instanceof ProcessInterface) {
            return String
                    .format(Messages.SchemaNotFoundError_cannotFindSchemaForProcessInterface_error_longdesc,
                            dataTypeName,
                            externalRef,
                            bomLocation,
                            ((ProcessInterface) procOrProcIfc).getName());
        }

        return String
                .format(Messages.SchemaNotFoundError_cannotFindSchema_error_longdesc,
                        dataTypeName,
                        externalRef,
                        bomLocation);
    }

    /**
     * @see org.openarchitectureware.xtend.parser.SyntaxError#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return getMessage();
    }
}