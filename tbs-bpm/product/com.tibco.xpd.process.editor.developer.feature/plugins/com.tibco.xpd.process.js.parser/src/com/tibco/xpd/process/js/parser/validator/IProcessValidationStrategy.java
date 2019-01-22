/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.js.parser.validator;

import com.tibco.xpd.script.parser.validator.IValidationStrategy;

/**
 * 
 * <p>
 * <i>Created: 3 Oct 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public interface IProcessValidationStrategy extends IValidationStrategy {

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
