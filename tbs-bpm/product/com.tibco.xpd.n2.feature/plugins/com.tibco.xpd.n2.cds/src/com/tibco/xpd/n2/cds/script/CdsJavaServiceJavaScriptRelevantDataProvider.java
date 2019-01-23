/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethod;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaMethodParameter;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.script.JavaServiceScriptRelevantDataProvider;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.script.ResolverUMLScriptRelevantData;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Default implementation for javascript of the Abstract class
 * {@link AbstractScriptRelevantDataProvider}.
 * 
 * @author mtorres
 */
public class CdsJavaServiceJavaScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

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

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (isMappedScript()) {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getOutputJavaServiceScriptRelevantData();
            }
        } else {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getOutputJavaServiceScriptRelevantData();
            }
        }
    }

    private List<IScriptRelevantData> getOutputJavaServiceScriptRelevantData() {
        List<IScriptRelevantData> scriptRelevantDataList =
                super.getScriptRelevantDataList();
        Activity activity = getActivity();
        if (activity != null) {
            // Get the Java Method
            JavaMethod javaMethod =
                    JavaServiceMappingUtil.getJavaMethod(activity);
            if (javaMethod != null) {
                try {
                    JavaMethodParameter returnParam =
                            javaMethod.getReturnParam();
                    if (returnParam != null && returnParam.getType() != null) {
                        IScriptRelevantData resolver =
                                new DefaultScriptRelevantData();
                        resolver.setName("RETURN_VALUE"); //$NON-NLS-1$
                        resolver =
                                JavaModelUtil.setResolverReturnValue(resolver,
                                        activity);
                        addResolutionTypes(resolver, false, null);
                        if (resolver != null) {
                            scriptRelevantDataList.add(resolver);
                        }
                    }
                } catch (JavaModelException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }
        return scriptRelevantDataList;
    }

}
