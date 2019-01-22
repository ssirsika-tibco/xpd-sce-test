/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.jccvalidator;

import com.tibco.xpd.process.js.parser.validator.IProcessValidationStrategy;

/**
 * 
 * @author Miguel Torres
 * 
 */
public interface IJCCProcessValidationStrategy extends IJCCValidationStrategy , IProcessValidationStrategy {

    /**
     * Returns the destination name for this Strategy
     * 
     * @return
     */
    String getDestinationName();

    /**
     * Sets the destination name for this Strategy
     * 
     * @return
     */
    void setDestinationName(String destinationName);

    /**
     * Returns the script type.
     * 
     * @return
     */
    String getScriptType();

    /**
     * Sets the script Type.
     */
    void setScriptType(String scriptType);

    /**
     * This method is useful to specify the data type the script should resolve
     * into e.g. in the case of scripts in the mapper, what we would like to
     * ensure that the data type matches
     * 
     * @param dataType
     */
    void setExpectedResultantDataType(String dataType);

    /**
     * This method provides sub context to the current script context into e.g.
     * in the case of mapping scripts, we would like to distinguish between
     * 'Input to Service' and 'Output from Service'
     * 
     * @param scriptSubContext
     */
    void setScriptSubContext(String scriptSubContext);

}
