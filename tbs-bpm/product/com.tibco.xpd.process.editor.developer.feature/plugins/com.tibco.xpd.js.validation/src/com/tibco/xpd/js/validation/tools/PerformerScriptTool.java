/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.js.validation.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class PerformerScriptTool extends ScriptTool {

    private ProcessRelevantData processRelevantData = null;

    public PerformerScriptTool(ProcessRelevantData processRelevantData) {
        super(processRelevantData);
        this.processRelevantData = processRelevantData;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getActivity()
     * 
     * @return
     */
    @Override
    protected Activity getActivity() {
        return null;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getProcess()
     * 
     * @return
     */
    @Override
    protected Process getProcess() {
        Process process = Xpdl2ModelUtil.getProcess(processRelevantData);
        return process;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScript()
     * 
     * @return
     */
    @Override
    protected String getScript() {
        return ProcessScriptUtil.getScriptPerformerScript(processRelevantData);
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessJsConsts.PERFORMER_SCRIPT;
    }


    @Override
    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(DestinationUtil
                        .getEnabledValidationDestinations(process));

        if (processDestList == null || processDestList.isEmpty()) {
            processDestList = new ArrayList<String>();
            processDestList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        }
        if (!processDestList.contains(ProcessJsConsts.JSCRIPT_DESTINATION)) {
            processDestList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        }
        return processDestList;
    }
}
