/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.Collection;

import com.tibco.xpd.script.parser.validator.IValidationStrategy;

/**
 * @author nwilson
 */
public interface IExpressionParser {

    /**
     * @param script
     *            The script to parse.
     * @param strategy
     *            The validatino strategy.
     * @param scriptType
     *            The scrip type.
     * @return The collection of DataField names.
     * @throws FieldParserException
     *             If there was a problem parsing the script.
     */
    Collection<String> getParameterNames(String script,
            IValidationStrategy strategy, String scriptType)
            throws FieldParserException;

}
