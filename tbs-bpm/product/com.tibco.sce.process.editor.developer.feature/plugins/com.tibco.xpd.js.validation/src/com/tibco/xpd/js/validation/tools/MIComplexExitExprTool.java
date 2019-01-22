/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * <p>
 * <i>Created: 9 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class MIComplexExitExprTool extends ScriptTool {

    private Activity activity = null;

    public MIComplexExitExprTool(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected Process getProcess() {
        return activity.getProcess();
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getMIComplexExitExpressionScript(activity);
    }

    @Override
    protected String getScriptType() {
        return ProcessJsConsts.MI_COMPLEX_EXIT_EXPR;
    }
    
    @Override
    protected Activity getActivity() {        
        return this.activity;
    }

}
