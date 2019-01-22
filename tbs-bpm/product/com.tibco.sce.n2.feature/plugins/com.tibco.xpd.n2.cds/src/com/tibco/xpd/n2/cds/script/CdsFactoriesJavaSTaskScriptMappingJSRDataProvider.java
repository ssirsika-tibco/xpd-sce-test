/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.cds.script;

import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

/**
 * For JavaService task we want to have the CDS factory classes appear on the
 * user defined scripts for both input & output.
 * 
 * @author kupadhya
 * 
 */
public class CdsFactoriesJavaSTaskScriptMappingJSRDataProvider extends
        CdsFactoriesJavaScriptRelevantDataProvider {
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        return super.getScriptRelevantDataList();
    }
}
