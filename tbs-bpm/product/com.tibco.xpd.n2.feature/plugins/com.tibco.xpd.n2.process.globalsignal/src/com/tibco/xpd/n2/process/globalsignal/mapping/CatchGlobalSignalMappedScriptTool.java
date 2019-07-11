/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Script Tool specific to Catch Global Signal mapped events.
 * 
 * @author ssirsika
 * @since 02-May-2016
 */
public class CatchGlobalSignalMappedScriptTool extends
        AbstractGlobalSignalMappingScriptTool {

    private DataMapping dataMapping = null;

    /**
     * @param mappingScript
     */
    public CatchGlobalSignalMappedScriptTool(DataMapping dataMapping) {
        super(ProcessScriptUtil
                .getScriptInformationFromDataMapping(dataMapping));
        this.dataMapping = dataMapping;

    }

    @Override
    protected Process getProcess() {
        Process process = null;
        if (dataMapping != null) {
            process = Xpdl2ModelUtil.getProcess(dataMapping);
        }
        return process;
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getDataMappingScript(dataMapping);
    }

    @Override
    protected Activity getActivity() {
        Activity activity = null;
        if (dataMapping != null) {
            activity = Xpdl2ModelUtil.getParentActivity(dataMapping);
        }
        return activity;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
    }

}
