/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class RestServiceJScriptTool extends ScriptTool {

    private EObject element = null;

    public RestServiceJScriptTool(ScriptInformation element) {
        super(element);
        this.element = element;
    }

    public RestServiceJScriptTool(DataMapping mapping) {
        super(ProcessScriptUtil.getScriptInformationFromDataMapping(mapping));
        this.element = mapping;
    }

    @Override
    protected Process getProcess() {
        Process process = null;
        if (element != null) {
            process = Xpdl2ModelUtil.getProcess(element);
        }
        return process;
    }

    @Override
    protected String getScript() {
        String script = null;
        if (element instanceof ScriptInformation) {
            script =
                    ProcessScriptUtil
                            .getTaskScript((ScriptInformation) element);
        } else if (element instanceof DataMapping) {
            script =
                    ProcessScriptUtil
                            .getDataMappingScript((DataMapping) element);
        }
        return script;
    }

    @Override
    protected String getScriptType() {
        return ProcessJsConsts.RESTSERVICE_TASK;
    }

    @Override
    protected Activity getActivity() {
        if (element != null) {
            return Xpdl2ModelUtil.getParentActivity(element);
        }
        return null;
    }

}
