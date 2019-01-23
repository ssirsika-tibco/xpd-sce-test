/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.tools;



import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * <p>
 * <i>Created: 07 February 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class WebServiceUnmappedScriptInputTool extends XPathScriptTool {

	private ScriptInformation scriptInformation = null;

	public WebServiceUnmappedScriptInputTool(ScriptInformation scriptInformation) {
		super(scriptInformation);
		this.scriptInformation = scriptInformation;
	}

	@Override
	protected Process getProcess() {
	    Process process = null;
	    if(scriptInformation != null){
            process = Xpdl2ModelUtil.getProcess(scriptInformation);
	    }
		return process;
	}

	@Override
	protected String getScript() {
		return ProcessScriptUtil.getTaskScript(scriptInformation);
	}
	
	@Override
	protected String getScriptType() {		
		return XPathScriptParserUtil.WEBSERVICE_TASK;
	}
	
	@Override
    protected Activity getActivity() {
        Activity activity = null;
        if (scriptInformation != null) {
            activity = Xpdl2ModelUtil.getParentActivity(scriptInformation);
        }
        return activity;
    }
	
    @Override
    Part getWsdlPart() {
        // TODO Auto-generated method stub
        return null;
    }
	
	@Override
    boolean isWsdlSupported() {
        return false;
    }
	
	@Override
	IScriptRelevantData getMappingType() {
	    return null;
	}
	    
}
