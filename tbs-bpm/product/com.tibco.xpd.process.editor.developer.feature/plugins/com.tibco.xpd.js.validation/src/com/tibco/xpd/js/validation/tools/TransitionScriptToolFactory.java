/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Transition;

/**
 * 
 * <p>
 * <i>Created: 27 Mar 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class TransitionScriptToolFactory extends JavaScriptToolFactory {

	public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
		IPreProcessor processor = null;
		if (input instanceof Transition) {
			Transition transition = (Transition) input;
			boolean b = ProcessScriptUtil.isSeqFlowWithScriptType(transition,
					getScriptGrammar());
			if (b) {
				processor = new TransitionScriptTool(transition);
			}
		}
		return processor;
	}

	public Class<? extends IPreProcessor> getToolClass() {
		return TransitionScriptTool.class;
	}

}
