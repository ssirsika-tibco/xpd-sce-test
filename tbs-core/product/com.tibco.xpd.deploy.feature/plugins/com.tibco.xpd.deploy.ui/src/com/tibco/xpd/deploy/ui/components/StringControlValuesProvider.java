package com.tibco.xpd.deploy.ui.components;

import java.util.Collection;

/**
 * Callback base class to dynamically provide values for string control. The
 * implementation subclasses are supposed to be referenced from
 * "StringValuesProvider" facet of server parameter.
 * 
 * @author jarciuch
 * @since 26 Apr 2013
 */
public abstract class StringControlValuesProvider {

    /**
     * Returns a collection of dynamically established string values or 'null'.
     * 
     * @param serverProvider
     *            provides deployment server. It can be 'null' or it can provide
     *            a 'null' server via
     *            {@link TestableServerProvider#getTestableServer()} method.
     * @return Returns collection of strings (could be empty) or 'null'. If not
     *         'null' value is returned then static list "StringValues" is taken
     *         into consideration (otherwise is ignored).
     */
    public Collection<String> getStringValues(
            TestableServerProvider serverProvider) {
        return null;
    }

}
