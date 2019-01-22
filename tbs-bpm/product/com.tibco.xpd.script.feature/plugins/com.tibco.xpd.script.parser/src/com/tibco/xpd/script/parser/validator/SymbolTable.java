package com.tibco.xpd.script.parser.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.services.IDisposable;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.validator.ISymbolTableExt;
import com.tibco.xpd.script.parser.util.ParseUtil;

public class SymbolTable implements ISymbolTable, ISymbolTableExt, IDisposable {
    
    private IScriptRelevantData returnType;
    
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
		if (scriptRelevantDataTypeMap == null) {
		    scriptRelevantDataTypeMap = new HashMap<String, IScriptRelevantData>();
		}
		Map<String, IScriptRelevantData> newDataMap = changeDataType(scriptRelevantDataTypeMap);		
		this.scriptRelevantDataTypeMap = newDataMap;
	}

	/**
	 * The script relevant data fields will have data type as STRING. We need to make
	 * them as String.
	 * 
	 * @param scriptRelevantDataTypeMap
	 * @return
	 */
	private Map<String, IScriptRelevantData> changeDataType(
			Map<String, IScriptRelevantData> scriptRelevantDataTypeMap) {
		Map<String, IScriptRelevantData> dataMap = new HashMap<String, IScriptRelevantData>();
		Set<Entry<String, IScriptRelevantData>> entrySet = scriptRelevantDataTypeMap.entrySet();
		for (Entry<String, IScriptRelevantData> entry : entrySet) {
		    IScriptRelevantData srdDataType = entry.getValue();
		    if(srdDataType != null){
    		    String strDataType = srdDataType.getType();
    			String newDataType = mappedDataTypes.get(strDataType);
    			if (newDataType == null) {
    				newDataType = strDataType;
    			}
    			srdDataType.setType(newDataType);
    			dataMap.put(entry.getKey(), srdDataType);
		    }
		}
		return dataMap;
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

	private static Map<String, String> mappedDataTypes = new HashMap<String, String>();
	static {
		mappedDataTypes.put("STRING", "String"); //$NON-NLS-1$ //$NON-NLS-2$
		mappedDataTypes.put("FLOAT", "Float"); //$NON-NLS-1$ //$NON-NLS-2$
		mappedDataTypes.put("INTEGER", "Integer"); //$NON-NLS-1$ //$NON-NLS-2$
		mappedDataTypes.put("REFERENCE", "Reference"); //$NON-NLS-1$ //$NON-NLS-2$
		mappedDataTypes.put("DATETIME", "DateTime"); //$NON-NLS-1$ //$NON-NLS-2$
		mappedDataTypes.put("BOOLEAN", "Boolean"); //$NON-NLS-1$ //$NON-NLS-2$
		mappedDataTypes.put("PERFORMER", "Performer"); //$NON-NLS-1$ //$NON-NLS-2$		
	}

    public EObject getInput() {
        return input;
    }

    public void setInput(EObject input) {
        this.input = input;
    }

    @Override
    public IScriptRelevantData getReturnType() {
        return returnType;
    }

    @Override
    public void setReturnType(IScriptRelevantData type) {
        this.returnType = type;        
    }

	@Override
	public void dispose() {
	    if (this.scriptRelevantDataTypeMap != null){
//	        ParseUtil.nulifySRDReferences(this.scriptRelevantDataTypeMap.values());
	        this.scriptRelevantDataTypeMap = null;
	    }
        if (this.localMethodMap != null){
//            ParseUtil.nulifySRDReferences(this.localMethodMap.values());
            this.localMethodMap = null;
        }
        if (this.localVariableMap != null){
//            ParseUtil.nulifySRDReferences(this.localVariableMap.values());
            this.localVariableMap = null;
        }
	}	

    


}
