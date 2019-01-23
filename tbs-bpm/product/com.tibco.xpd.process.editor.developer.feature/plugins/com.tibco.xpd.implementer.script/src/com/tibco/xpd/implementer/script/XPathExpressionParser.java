/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.Collection;

import com.tibco.xpd.script.parser.validator.IValidationStrategy;

/**
 * @author nwilson
 */
public class XPathExpressionParser implements IExpressionParser {

    /** XPath grammar identifier. */
    public static final String XPATH = "XPath"; //$NON-NLS-1$

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
    public Collection<String> getParameterNames(String script,
            IValidationStrategy strategy, String scriptType)
            throws FieldParserException {
        Collection<String> names = new ArrayList<String>();
        String[] params = script.split("\\$"); //$NON-NLS-1$
        for (int i = 1; i < params.length; i++) {
            String param = params[i];
            String[] params2 = param.split("[^a-zA-Z0-9/@:\\]\\[\\_]"); //$NON-NLS-1$
            if (params2.length > 0) {
                names.add("$" + params2[0]); //$NON-NLS-1$
            }

        }

        return names;
    }

}
