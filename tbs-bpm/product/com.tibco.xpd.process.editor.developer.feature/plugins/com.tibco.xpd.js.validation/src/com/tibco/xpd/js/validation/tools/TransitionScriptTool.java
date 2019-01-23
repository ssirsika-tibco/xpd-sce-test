/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;


import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 27 Mar 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class TransitionScriptTool extends ScriptTool {

	private Transition transition;

	public TransitionScriptTool(Transition transition) {
		super(transition);
		this.transition = transition;
	}

	@Override
	protected Process getProcess() {
		return transition.getProcess();
	}

	@Override
	protected String getScript() {
		return ProcessScriptUtil.getSeqFlowScript(transition);
	}

	@Override
	protected String getScriptType() {
		return ProcessJsConsts.SEQUENCE_FLOW;
	}
	
	@Override
    protected Activity getActivity() {
	    Activity activity = null;
	    if(transition != null){
	        activity = Xpdl2ModelUtil.getParentActivity(transition);
	    }
	    return activity;
    }

}
