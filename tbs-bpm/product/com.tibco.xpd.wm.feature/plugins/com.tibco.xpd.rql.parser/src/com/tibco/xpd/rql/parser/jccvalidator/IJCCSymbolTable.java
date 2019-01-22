/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.jccvalidator;

import java.util.List;
import java.util.Map;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

public interface IJCCSymbolTable {	
       
    /**
     * 
     * @return Map<String, List<IScriptRelevantData>>
     */
    Map<String, List<IScriptRelevantData>> getScriptRelevantDataTypeMap();
    
    /**
     * Set the script relevant data
     * 
     * @param scriptRelevantDataTypeMap
     */
    void setScriptRelevantDataTypeMap(Map<String, List<IScriptRelevantData>> scriptRelevantDataTypeMap);   

}