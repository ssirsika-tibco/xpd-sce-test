/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.handlers;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;

/**
 * 
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public interface IntfSimpleMappingsIssuesHandler {

    /***
     * 
     * @return the array of {@link WebServiceJavaScriptMappingIssue} for a web
     *         service task java script concept path to concept path mappings <br>
     *         or <code>null</code> if no issues are found
     */
    public WebServiceJavaScriptMappingIssue[] getIssues();

}
