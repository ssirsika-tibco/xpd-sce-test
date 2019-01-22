/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation {@link ScriptTool} for catch signal event mappings.
 * 
 * @author aallway
 * @since 9 May 2012
 */
public class CatchSignalMappingScriptTool extends ScriptTool {

    private ScriptInformation mappingScript = null;

    /**
     * @param eObject
     * @param unmappedScript
     */
    public CatchSignalMappingScriptTool(ScriptInformation mappingScript) {
        super(mappingScript);
        this.mappingScript = mappingScript;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScript()
     * 
     * @return
     */
    @Override
    protected String getScript() {
        if (mappingScript != null) {
            return ProcessScriptUtil.getTaskScript(mappingScript);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getProcess()
     * 
     * @return
     */
    @Override
    protected Process getProcess() {
        if (mappingScript != null) {
            return Xpdl2ModelUtil.getProcess(mappingScript);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getActivity()
     * 
     * @return
     */
    @Override
    protected Activity getActivity() {
        if (mappingScript != null) {
            return (Activity) Xpdl2ModelUtil.getAncestor(mappingScript,
                    Activity.class);
        }
        return null;
    }

}
