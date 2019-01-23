/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.script;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.JavaModelUtil;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.model.script.ResolverUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author mtorres
 */
public class JavaServiceScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

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
                new ArrayList<IScriptRelevantData>();
        Activity activity = getActivity();
        if (activity != null) {
            IScriptRelevantData resolver =
                    new DefaultUMLScriptRelevantData();
            ((DefaultUMLScriptRelevantData)resolver).setClassName(ProcessJsConsts.RESOLVER_CLASSNAME);
            resolver.setName(ProcessJsConsts.RESOLVER_CLASSNAME);
            resolver = JavaModelUtil.setResolverReturnValue(resolver, activity);
            if (resolver != null) {
                scriptRelevantDataList.add(resolver);
            }
        }
        return scriptRelevantDataList;
    }

}
