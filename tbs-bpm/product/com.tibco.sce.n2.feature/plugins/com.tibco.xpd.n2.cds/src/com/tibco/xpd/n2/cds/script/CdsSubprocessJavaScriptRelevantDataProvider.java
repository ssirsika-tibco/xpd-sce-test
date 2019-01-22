/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.SubProcessScriptRelevantDataProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author mtorres
 */
public class CdsSubprocessJavaScriptRelevantDataProvider extends
    SubProcessScriptRelevantDataProvider {
    
    
    @Override
    protected List<JsClassDefinitionReader> readContributedDefinitionReaders(
            List<String> processDestList) {
        List<JsClassDefinitionReader> jsClassProvider = Collections.EMPTY_LIST;
        try {
            jsClassProvider =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(processDestList,
                                    getScriptType(),
                                    ProcessJsConsts.JAVASCRIPT_GRAMMAR,
                                    CDSUtils.N2PE_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return jsClassProvider;
    }
    
    @Override
    protected List<IScriptRelevantData> convertToScriptRelevantData(
            List<ProcessRelevantData> processDataList) {
        return CDSUtils
        .convertToScriptRelevantData(processDataList,
                getProject(),
                readContributedDefinitionReaders(getProcessDestinationList(getProcess())));
    }


}
