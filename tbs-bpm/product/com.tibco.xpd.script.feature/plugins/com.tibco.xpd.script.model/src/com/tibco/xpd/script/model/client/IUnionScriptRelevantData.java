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
public interface IUnionScriptRelevantData extends IScriptRelevantData {

    public void setSrdList(List<IScriptRelevantData> srdList);

    public List<IScriptRelevantData> getSrdList();
}
