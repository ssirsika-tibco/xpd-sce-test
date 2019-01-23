/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.bx.validation.rules.mapping;

/**
 * Interface defining type compatibility testing.
 * 
 * @author rsomayaj
 * 
 */
public interface WebServiceJavaScriptMappingTypeCompatibility {

    public WebServiceJavaScriptMappingIssue[] check(Entity source, Entity target);

    public WebServiceJavaScriptMappingIssue[] validate(Entity source,
            Entity target);

    public int getLength(Entity entity);

    /*
     * Start visitor methods
     */
    public WebServiceJavaScriptMappingIssue[] fromBigInteger(Entity from,
            Entity to);

    public WebServiceJavaScriptMappingIssue[] fromInteger(Entity from, Entity to);

    public WebServiceJavaScriptMappingIssue[] fromBigDecimal(Entity from,
            Entity to);

    public WebServiceJavaScriptMappingIssue[] fromDecimal(Entity from, Entity to);

    public WebServiceJavaScriptMappingIssue[] fromText(Entity from, Entity to);

    public WebServiceJavaScriptMappingIssue[] fromBoolean(Entity from, Entity to);

    public WebServiceJavaScriptMappingIssue[] fromDate(Entity from, Entity to);

    public WebServiceJavaScriptMappingIssue[] fromTime(Entity from, Entity to);

    public WebServiceJavaScriptMappingIssue[] fromDateTime(Entity from,
            Entity to);

    public WebServiceJavaScriptMappingIssue[] fromDateTimeTimeZone(Entity from,
            Entity to);

    public WebServiceJavaScriptMappingIssue[] fromDuration(Entity from,
            Entity to);

    /*
     * End visitor methods
     */

}
