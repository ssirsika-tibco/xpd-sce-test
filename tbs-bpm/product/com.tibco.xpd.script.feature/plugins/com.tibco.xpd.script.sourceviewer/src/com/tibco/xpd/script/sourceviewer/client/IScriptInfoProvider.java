/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.sourceviewer.client;

import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

/**
 * Provides the relevant script data that the script can use.
 * 
 * @author rsomayaj
 * 
 */
public interface IScriptInfoProvider {

    /**
     * The relevant script data that the script can use. This is used to display
     * the data in the content assist.
     * 
     * @return a list of IScriptRelevantData
     */
    public List<IScriptRelevantData> getScriptRelevantData();

    /**
     * The relevant script data that the script can use.
     * 
     * @return a list of Nodes
     */
    public List getComplexScriptRelevantData();

}
