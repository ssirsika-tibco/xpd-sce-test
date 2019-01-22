/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.parser.validator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.rql.parser.jccvalidator.IJCCSymbolTable;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class RQLSymbolTable implements IJCCSymbolTable {

	/**
	 * This hashmap stores the variable name and its type as defined in the
	 * relevant data, the type is a list because we can have 2 types for the same
	 * name. ie: 2 organizations with the same name in different OM files
	 */
	protected Map<String, List<IScriptRelevantData>> scriptRelevantDataTypeMap = null;

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#setProcessData(java.util.Map)
	 */
	public void setScriptRelevantDataTypeMap(Map<String, List<IScriptRelevantData>> scriptRelevantDataTypeMap) {		
		this.scriptRelevantDataTypeMap = scriptRelevantDataTypeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#getProcessData()
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public Map<String, List<IScriptRelevantData>> getScriptRelevantDataTypeMap() {
		if (this.scriptRelevantDataTypeMap == null) {
			this.scriptRelevantDataTypeMap = Collections.EMPTY_MAP;
		}
		return this.scriptRelevantDataTypeMap;
	}	

}
