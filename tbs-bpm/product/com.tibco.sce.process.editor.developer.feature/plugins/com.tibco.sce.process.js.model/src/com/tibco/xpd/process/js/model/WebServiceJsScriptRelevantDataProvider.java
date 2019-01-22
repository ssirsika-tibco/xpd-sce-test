/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
/**
 * @author mtorres
 */
public class WebServiceJsScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (isMappedScript()) {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return Collections.emptyList();
            }
        } else {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return Collections.emptyList();
            }
        }
    }

}
