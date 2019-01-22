/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import java.util.List;

/**
 * @author mtorres
 * 
 */
public interface IDataTypeMapper {

    public List<String> getSymbolTableKeyWords();
    
    public List<String> getSymbolTableFutureKeyWords();
    
}
