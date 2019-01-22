package com.tibco.xpd.processeditor.xpdl2.script;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Wrapper class for the Script Information This class is needed to just
 * parse the script once
 * 
 * @author mtorres
 * 
 */
public class ScriptInformationParsed {
    
    private ScriptInformation scriptInformation;

    private IScriptRelevantData returnType;

    public ScriptInformationParsed(ScriptInformation scriptInformation,
            IScriptRelevantData returnType) {
        this.scriptInformation = scriptInformation;
        this.returnType = returnType;
    }
    
    public ScriptInformation getScriptInformation() {
        return scriptInformation;
    }
    
    public IScriptRelevantData getReturnType() {
        return returnType;
    }
}
