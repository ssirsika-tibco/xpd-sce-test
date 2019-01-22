/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.tools;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Message;

import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.WsdlPathFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.WebServiceOperation;
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
public class WebServiceUnmappedScriptOutputTool extends XPathScriptTool {

	private ScriptInformation scriptInformation = null;

	public WebServiceUnmappedScriptOutputTool(ScriptInformation scriptInformation) {
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
	
    protected Part getWsdlPart() {
        Part wsldPart = null;
        ActivityMessageProvider messageAdapter =
                ActivityMessageProviderFactory.INSTANCE
                        .getMessageProvider(getActivity());
        if (messageAdapter != null) {
            WebServiceOperation wso =
                    messageAdapter.getWebServiceOperation(getActivity());
            if (wso != null) {
                Message message = Xpdl2WsdlUtil.getMessage(getActivity());
                if (message != null) {
                    Map parts = message.getParts();
                    if (parts != null && !parts.isEmpty()) {
                        String partName = null;
                        Set keys = parts.keySet();
                        for (Object key : keys) {
                            Object obj = parts.get(key);
                            if (obj instanceof Part) {
                                Part part = (Part) obj;
                                if(part.getElementName() != null){
                                    partName = part.getElementName().getLocalPart();
                                } else {
                                    partName = part.getName();
                                }
                                break;
                            }
                        }
                        if (partName != null) {
                            wsldPart =
                                    WsdlPathFactory.getPart(wso,
                                            partName,
                                            false);
                        }
                    }
                }
            }
        }
        return wsldPart;
    }

    @Override
    boolean isWsdlSupported() {
        return true;
    }
    
    @Override
    IScriptRelevantData getMappingType() {
        // TODO Auto-generated method stub
        return null;
    }
	    
}
