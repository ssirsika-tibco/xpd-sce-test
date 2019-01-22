/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import java.util.List;

/**
 * 
 * 
 * @author bharge
 * @since 29 Nov 2011
 */
public class DefaultUnionScriptRelevantData extends DefaultScriptRelevantData
        implements IUnionScriptRelevantData {

    List<IScriptRelevantData> srdList;

    /**
     * @see com.tibco.xpd.script.model.client.IUnionScriptRelevantData#setSrdList(java.util.List)
     * 
     * @param srdList
     */
    @Override
    public void setSrdList(List<IScriptRelevantData> srdList) {
        this.srdList = srdList;
    }

    /**
     * @return the srdList
     */
    @Override
    public List<IScriptRelevantData> getSrdList() {
        return this.srdList;
    }

    public DefaultUnionScriptRelevantData(String name, String type,
            boolean isArray) {
        super(name, type, isArray);
    }

}
