/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.tools;

import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.xpdl2.Activity;

/**
 * 
 * <p>
 * <i>Created: 9 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class MILoopExprToolFactory extends JavaScriptToolFactory {

    public IPreProcessor createPreProcessor(IValidationScope scope, Object input) {
        IPreProcessor processor = null;
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            boolean b = ProcessScriptUtil.isActivityMILoopExprHasGrammar(
                    activity, getScriptGrammar());
            if (b) {
                processor = new MILoopExprTool(activity);
            }
        }
        return processor;
    }

    public Class<? extends IPreProcessor> getToolClass() {
        return MILoopExprTool.class;
    }

}
