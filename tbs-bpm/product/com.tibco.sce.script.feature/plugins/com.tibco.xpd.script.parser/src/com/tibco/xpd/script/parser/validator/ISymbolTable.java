package com.tibco.xpd.script.parser.validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

public interface ISymbolTable {	
    
    void setLocalVariable(String varName, IScriptRelevantData varType);
    
    boolean isLocalVariable(String varName);

    IScriptRelevantData getLocalVariableType(String varName);

    Set<String> getLocalVariableNames();
    
    /**
     * Returns the list of standard javascript function names and other local & external method names supported.
     * @return A list of standard javascript function names and other local & external method names supported.
     * @since 3.1.0
     */
    Set<String> getLocalMethodNames();
    
    Map<String, IScriptRelevantData> getLocalVariableMap();
    
    /**
     * Returns a map of standard javascript function and other local & external method names supported mapped to their return type.
     * @return A map of standard javascript function and other local & external method names supported mapped to their return type.
     * @since 3.1.0
     */
    Map<String, IScriptRelevantData> getLocalMethodMap();  
    
    void setScriptRelevantDataTypeMap(Map<String, IScriptRelevantData> scriptRelevantDataTypeMap);   

    boolean isScriptRelevantData(String scriptVarId);    

    IScriptRelevantData getScriptRelevantDataType(String scriptVarName);
       
    Map<String, IScriptRelevantData> getScriptRelevantDataTypeMap();
    
    
    /**
     * This method will return a list of variable names 
     * which are used in the script.
     * @return
     */
    public List<String> getVariablesInUse();
    
    public void recordVariableInUse(String varName);
    
    EObject getInput();

}