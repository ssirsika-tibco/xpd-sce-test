/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Abstract Cds Script Relevant Data Provider for Global Signal Events
 * 
 * @author kthombar
 * @since Feb 12, 2015
 */
public abstract class AbstractCdsGlobalSignalScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    /**
     * 
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#readContributedDefinitionReaders(java.util.List)
     * 
     * @param processDestList
     * @return
     */
    @Override
    protected final List<JsClassDefinitionReader> readContributedDefinitionReaders(
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

    /**
     * 
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#convertToScriptRelevantData(java.util.List)
     * 
     * @param processDataList
     * @return
     */
    @Override
    protected List<IScriptRelevantData> convertToScriptRelevantData(
            List<ProcessRelevantData> processDataList) {
        /*
         * Convert to script relavent data.
         */
        return CDSUtils
                .convertToScriptRelevantData(processDataList,
                        getProject(),
                        readContributedDefinitionReaders(getProcessDestinationList(getProcess())));
    }

}
