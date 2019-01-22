package com.tibco.xpd.script.parser.internal.validator;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

public interface ISymbolTableExt {	
    
    /**
     * This method will return the return type of the script
     *  
     * @return
     */
    public IScriptRelevantData getReturnType();
    
    /**
     * Sets the return type to the Symbol Table
     * 
     * @param type
     */
    public void setReturnType(IScriptRelevantData type);
    
}