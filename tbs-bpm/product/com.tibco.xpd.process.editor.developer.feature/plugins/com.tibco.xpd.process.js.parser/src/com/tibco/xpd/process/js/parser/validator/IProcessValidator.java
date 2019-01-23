/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.js.parser.validator;

import com.tibco.xpd.script.parser.internal.validator.IValidator;

/**
 * 
 * <p>
 * <i>Created: 3 Oct 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public interface IProcessValidator extends IValidator {
    /**
     * Returns the destination name for this Strategy
     * 
     * @return
     */
    void setProcessDestination(String processDestination);

    /**
     * Returns whether the script is for Script Task or the Conditional
     * transition.
     * 
     * @return
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
}
