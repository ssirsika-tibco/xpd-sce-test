/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * 
 * <p>
 * <i>Created: 4 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class MIComplexExpressionSection extends BaseMIScriptSection {

    @Override
    public String getCurrentSetScriptGrammarId() {
        String currentScriptGrammarId = null;
        Activity act = getActivity();
        if (act != null) {
            currentScriptGrammarId =
                    TaskObjectUtil
                            .getExistingSetComplexExitScriptGrammarId(act);
        }
        return currentScriptGrammarId;

    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.MI_COMPLEX_EXIT_EXPR;
    }

}
