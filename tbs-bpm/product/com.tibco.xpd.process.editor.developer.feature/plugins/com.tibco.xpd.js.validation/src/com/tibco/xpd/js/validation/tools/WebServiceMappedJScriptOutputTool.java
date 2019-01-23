/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.script.ResolverUMLScriptRelevantData;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 5 Dec 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class WebServiceMappedJScriptOutputTool extends ScriptTool {

    private DataMapping mapping = null;

    public WebServiceMappedJScriptOutputTool(DataMapping mapping) {
        super(ProcessScriptUtil.getScriptInformationFromDataMapping(mapping));
        this.mapping = mapping;
    }

    @Override
    protected Process getProcess() {
        Process process = null;
        if (mapping != null) {
            process = Xpdl2ModelUtil.getProcess(mapping);
        }
        return process;
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getDataMappingScript(mapping);
    }

    @Override
    protected String getScriptType() {
        return ProcessJsConsts.WEBSERVICE_TASK;
    }

    @Override
    protected Activity getActivity() {
        Activity activity = null;
        if (mapping != null) {
            activity = Xpdl2ModelUtil.getParentActivity(mapping);
        }
        return activity;
    }

}
