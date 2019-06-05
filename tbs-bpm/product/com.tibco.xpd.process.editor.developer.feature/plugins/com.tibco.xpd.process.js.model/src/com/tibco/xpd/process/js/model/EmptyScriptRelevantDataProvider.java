/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
/**
 * @author mtorres
 */
public class EmptyScriptRelevantDataProvider extends
        AbstractScriptRelevantDataProvider {

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        return Collections.emptyList();
    }
    
    /*
     * Sid ACE-1317 Removed getComplexScriptRelevantDataList() as it was
     * redundant.
     */


}
