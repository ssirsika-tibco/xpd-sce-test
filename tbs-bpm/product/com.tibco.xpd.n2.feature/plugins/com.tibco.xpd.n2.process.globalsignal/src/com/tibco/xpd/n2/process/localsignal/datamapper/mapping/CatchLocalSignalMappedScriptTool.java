/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.mapping;

import com.tibco.xpd.js.validation.tools.CatchSignalMappingScriptTool;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapped script tool for Catch Local Signal Events.
 *
 * @author sajain
 * @since Jul 18, 2019
 */
public class CatchLocalSignalMappedScriptTool extends CatchSignalMappingScriptTool {

    private DataMapping dataMapping = null;

    /**
     * 
     * @param dataMapping
     */
    public CatchLocalSignalMappedScriptTool(DataMapping dataMapping) {
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
}
