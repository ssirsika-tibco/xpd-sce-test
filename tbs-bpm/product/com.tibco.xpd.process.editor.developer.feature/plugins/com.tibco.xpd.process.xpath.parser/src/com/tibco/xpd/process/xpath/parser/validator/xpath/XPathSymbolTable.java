/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.xpath.parser.validator.xpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
/**
 * 
 * 
 * <p>
 * <i>Created: 20 Feb 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class XPathSymbolTable implements ISymbolTable {
	/**
	 * This hashmap stores the variable name and its type as defined in the
	 * script
	 */
	protected Map<String, IScriptRelevantData> localVariableMap = new HashMap<String, IScriptRelevantData>();
	
	/**
	 * This hashmap stores the standard and local method names that can be used in the scripts.
	 */
	protected Map<String, IScriptRelevantData> localMethodMap = new HashMap<String, IScriptRelevantData>();

	/**
	 * This hashmap stores the variable name and its type as defined in the
	 * relevant data
	 */
	protected Map<String, IScriptRelevantData> scriptRelevantDataTypeMap = null;

	protected EObject input;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#isLocalVariable(java.lang.String)
	 */
	public boolean isLocalVariable(String varName) {
		return localVariableMap.containsKey(varName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#getLocalVariableType(java.lang.String)
	 */
	 //@ComplexTypeChange -->
	//@ComplexTypeChange -->
	public IScriptRelevantData getLocalVariableType(String varName) {
	    IScriptRelevantData varType = localVariableMap.get(varName);
		return varType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#getLocalVariableNames()
	 */
	public Set<String> getLocalVariableNames() {
		return localVariableMap.keySet();
	}

	/** {@inheritDoc}. */
	public Set<String> getLocalMethodNames() {
		return localMethodMap.keySet();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#setProcessData(java.util.Map)
	 */
	public void setScriptRelevantDataTypeMap(Map<String, IScriptRelevantData> scriptRelevantDataTypeMap) {		
		this.scriptRelevantDataTypeMap = scriptRelevantDataTypeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#getProcessData()
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public Map<String, IScriptRelevantData> getScriptRelevantDataTypeMap() {
		if (this.scriptRelevantDataTypeMap == null) {
			this.scriptRelevantDataTypeMap = Collections.EMPTY_MAP;
		}
		return this.scriptRelevantDataTypeMap;
	}

	public Map<String, IScriptRelevantData> getLocalVariableMap() {
		return localVariableMap;
	}
	
	/** {@inheritDoc}. */
	public Map<String, IScriptRelevantData> getLocalMethodMap() {
		return localMethodMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#isScriptRelevantData(java.lang.String)
	 */
	public boolean isScriptRelevantData(String scriptVarId) {
		boolean isPresent = getScriptRelevantDataTypeMap().containsKey(scriptVarId);
		return isPresent;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.xpd.js.parser.util.ISymbolTable#getProcessDataType(java.lang.String)
	 */
	public IScriptRelevantData getScriptRelevantDataType(String scriptRelevantDataVarName) {
		return scriptRelevantDataTypeMap.get(scriptRelevantDataVarName);
	}

	public void setLocalVariable(String varName, IScriptRelevantData varType) {
		localVariableMap.put(varName, varType);

	}
	
	/**
	 * Adds a method to the supported list.
	 * @param methodName The method name.
	 * @param type The return type of the method.
	 * @since 3.1.0
	 */
	public void setLocalMethod(String methodName, IScriptRelevantData type) {
		localMethodMap.put(methodName, type);
	}

	protected List<String> inUseVarList = new ArrayList<String>();

	public List<String> getVariablesInUse() {
		return inUseVarList;
	}

	public void recordVariableInUse(String varName) {
		boolean b = inUseVarList.contains(varName);
		if (!b) {
			inUseVarList.add(varName);
		}
	}


    public EObject getInput() {
        return input;
    }

    public void setInput(EObject input) {
        this.input = input;
    }

}
