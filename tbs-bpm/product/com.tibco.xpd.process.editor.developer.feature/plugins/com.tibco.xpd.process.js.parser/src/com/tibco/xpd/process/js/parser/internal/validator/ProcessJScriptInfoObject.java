/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.process.js.parser.internal.validator;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptInfoObject;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;

/**
 * @author mtorres
 * 
 */
public class ProcessJScriptInfoObject extends JScriptInfoObject {


    public List<JsClassDefinitionReader> getClassDefinitionReaders() {
        if (classDefinitionReaders == null) {
            try {
                classDefinitionReaders =
                        ScriptGrammarContributionsUtil.INSTANCE
                                .getJsClassDefinitionReader(Collections
                                        .singletonList(getDestinationName()),
                                        getScriptType(),
                                        ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                        ProcessJsConsts.JSCRIPT_DESTINATION);
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
            if (classDefinitionReaders == null) {
                classDefinitionReaders = Collections.emptyList();
            }
        }
        return classDefinitionReaders;
    }

}
