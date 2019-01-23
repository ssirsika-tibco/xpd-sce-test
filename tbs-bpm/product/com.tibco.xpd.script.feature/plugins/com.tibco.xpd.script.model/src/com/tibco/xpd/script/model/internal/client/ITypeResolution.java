/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

/**
 * This interface provides the content of the type resolved 
 * 
 * @author mtorres
 * 
 */
public interface ITypeResolution {

    void setTypesResolution(List<IScriptRelevantData> resolutionTypes);
    
    List<IScriptRelevantData> getTypesResolution();

    void setGenericContextType(IScriptRelevantData genericContextType);
    
    IScriptRelevantData getGenericContextType();
    
    boolean isReadOnly();
    
    void setReadOnly(boolean isReadOnly);
    
    boolean isContextless();
    
    void setExtendedInfo(Object extendedInfo);
    
    Object getExtendedInfo();
}
